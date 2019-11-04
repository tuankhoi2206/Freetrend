package ds.program.fvhr.ui.insurance;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import fv.util.DateUtils;



/**
 * Tach rieng cau query de DAO ngan gon hon (co the bo vao cai khac nhu xml, properties,... thay vi class)
 * @author LeHieu
 *
 */
public class CauQuery {
	private static final Log logger = LogFactory.getLog(CauQuery.class);
	
	/**
	 * 
	 * @param month 1-12, thang bao cao
	 * @param year 201x
	 * @return NSVao, RoVao = thang bao cao, TangLai =thang bao cao+1
	 */
	public static String getTang20TayQuery(String dkFact, int month, int year){
		String m1 = (month<10?"0"+month:month) + "/" + year;
		String m2;
		if (month==12) {
			m2 = "01/"+(year+1);
		}else{
			m2 = (month+1<10?"0"+(month+1):(month+1))+"/"+year;
		}
		String ngay1  = " to_date('"+"01/"+m1+"','dd/mm/yyyy')";
		String ngay20 = " to_date('"+"20/"+m2+"','dd/mm/yyyy')";
		
		String sql = "select a.Fname,a.lname,a.birthday,a.sex,a.position,A.birthplace ,c.luonghd bsalary,b.id_labour\n" +
				", b.date_s,a.empsn,dt.name_fact||' '||dt.name_group as name_group,dt.name_dept_name,a.date_hired , a.id_no\n" + 
				", b.date_s+1 as e_Dates ,(select e.id_social from n_social e where e.empsn=a.empsn and e.clock=1) id_social\n" + 
				" ,'" + m1 + "' month_bc\n" + 
				" ,(case when c.ghichu_trangthai='NSVao' then 'NS vao'\n" + 
				" when c.ghichu_trangthai='RoVao' then 'Ro vao'    else ''  end ) as note_tang\n" + 
				"From n_Employee a, n_labour b,n_department dt, n_social_infor_report c\n" + 
				"Where a.depsn=dt.id_dept  and a.empsn=b.empsn and a.empsn=c.empsn and b.clock=1\n" + 
				"and c.ghichu_trangthai in ('NSVao','RoVao') and c.trangthai=1\n" + 
				"and c.empsn not in (select d.empsn from n_emp_quit d\n" + 
				"where d.empsn=a.empsn  and d.to_date+1<"+ngay20+"\n" + 
				"and (d.thang_tanglai is null\n" + 
				"or (months_between(d.thang_tanglai,to_Date('"+m1+"','mm/yyyy'))>=0)) )\n" + 
				"and c.thoigian="+ngay1 + dkFact+ "\n"+
				"   union\n" + 
				"select c.Fname,c.lname,c.birthday,c.sex,c.position,c.birthplace ,b.salary salary ,b.id_labour\n" + 
				",b.date_s,c.empsn,dt.name_fact||' '||dt.name_group as name_group ,dt.name_dept_name,c.date_hired, c.id_no\n" + 
				",a.date_again e_dates,(select id_social from n_social where empsn=c.empsn and clock=1) id_social\n" + 
				" ,'" + m2 + "' month_bc ,'Di lam lai' as Note_Tang\n" + 
				" From n_Employee c, n_labour b,n_department dt,n_emp_quit a\n" + 
				" Where a.depsn=dt.id_dept  and a.empsn=b.empsn and a.empsn=c.empsn\n" + 
				" and b.clock=1 and ( (b.times >1)  or (b.times=1 and b.date_s<="+ngay1+" ))\n" + 
				" and to_char(a.thang_tanglai,'mm/yyyy')='" + m2 + "' and a.tyle_tanglai=20\n" + 
				" and c.empsn not in (select q.empsn from n_emp_quit q   Where a.empsn=q.empsn and q.date_again\n" + 
				" is null  and (q.from_date is null and q.to_date is null\n" + 
				" AND(Q.NOTE_GIAM_BHYT IS NULL OR q.note_giam_bhyt='GIAM TANG MOI')))"  + dkFact ;
		logger.debug(sql);
		return sql;
	}
	
	public static String getGiam20TayQuery(String dkFact, int month, int year){
		String m1 = (month<10?"0"+month:month) + "/" + year;
		String m2;
		if (month==12) {
			m2 = "01/"+(year+1);
		}else{
			m2 = (month+1<10?"0"+(month+1):(month+1))+"/"+year;
		}
		String sql= "select a.Fname,a.lname,a.birthday,a.sex,a.position,a.birthplace \n "+
		  ",c.luonghd salary,b.id_labour,b.date_s,a.empsn ,dt.name_fact||' '||dt.name_group as name_group\n"+
		  ",dt.name_dept_name \n"+
		  ",(select e.id_social from n_social e where e.empsn=a.empsn and e.clock='1') id_social  \n"+
		  ",'"+m1+"' month_bc,c.ghichu_trangthai Note_Tang \n"+
		  ",0 sal_old \n"+
		  " From n_Employee a, n_labour b,n_department dt, n_social_infor_report c \n"+
		  " where a.depsn=dt.id_dept "+dkFact+"\n"+
		  " and a.empsn=b.empsn and a.empsn=c.empsn and b.clock='1'\n"+		  
		  " and c.trangthai='-1'\n"+
		  //--Loai tru nghi viec da bao giam va chua bao tang lai, or thang tang lai>= thang bao cao
		  " and c.empsn not in (select d.empsn from n_emp_quit d where d.empsn=a.empsn \n"+
		  " and d.to_date+1<to_date('20/" + m2 + "','dd/mm/yyyy')\n" + 
		  " and ((d.thang_tanglai is null) \n"+
/*		  { sua lai 01/03/2013
		--ko duoc bang 0 vi if tang lai T01, dong thoi RoRa T1 luon thi CT se ko bao RoRa hixx
		-- VD 11031776 tang lai T01 va RoRa T01 luon, QT20 T01/2013, bao giam 21/02/2013
		-- vi co truong hop tang lai 20 tay, thang tang lai la 20/01/2013 > 01/01/2013
		-- months_between se tra ve gia tri >0 cung sai. Vi vay chi so sanh mm/yyyy ma thoi
		   }*/
		  " or (months_between(to_date(to_char(d.thang_tanglai,'mm/yyyy'),'mm/yyyy')\n" +
		  ",to_Date('"+m1+"','mm/yyyy'))>0)\n"+
		  " ) ) \n"+
		  //--Loai tru giam trong tang moi cua thang do, va chua co bao tang lai
		  " and c.empsn not in (select d.empsn from n_emp_quit d where d.empsn=a.empsn \n"+
		  " and d.real_off_date<to_date('20/" + m2 + "','dd/mm/yyyy')\n" +
		  " and d.thang_tanglai is null and d.note_giam_bhyt='"+"GIAM TANG MOI"+"' )\n"+
		  " and c.thoigian=to_date('01/" + m1 + "','dd/mm/yyyy')";
		
		logger.debug(sql);
		return sql;
	}	
	
	public static String getDCluong20TayQuery(String dkFact, String tableName, int month, int year){
		//dkfact: dk lay thong tin, tableName : ten table de lay du lieu cho QT20 lan 1 hay 2
		//n_social_infor_report 	: ok ds cua toan thang
		//n_social_infor_report_1	: QT20 cho lan 1, 20/06/2013 la cho T06
		//n_social_infor_report_2	: QT20 cho lan 2, 12/07/2013 la cho T06 bo sung
		//20/06 la QT cho T06, ko phai la 20/07 nua. Vi vay ko can dung den m2 hay m2= m1
		String m1 = (month<10?"0"+month:month) + "/" + year;		
		String m2;
		m2 = (month+1<10?"0"+(month+1):(month+1))+"/"+year;
		
/*		//dk old : <01/08/2013
		if (month==12) {
			m2 = "01/"+(year+1);
		}else{
			m2 = (month+1<10?"0"+(month+1):(month+1))+"/"+year;
		}		
*/
		String temp=" select a.Fname,a.lname,a.birthday,a.sex,a.position,A.birthplace \n"+
		  ",c.luonghd bsalary,b.id_labour,'"+m1+"' Month_BC,a.empsn \n"+
		  ",dt.name_fact||' '||dt.name_group as name_group ,dt.name_dept_name,a.date_hired \n"+
		  " ,(select e.id_social from n_social e where e.empsn=a.empsn and e.clock=1) id_social\n"+
		  " , a.id_no, '"+"DCLuong"+"' as note_tang"; 
		
		String sql = temp+", '"+"BT"+"' as DCDLoat \n"+  // DC luong binh thuong  
		  //luong gan nhat co tham gia BH khi xuat ra excel se lay gia tri
		  " ,0  sal_old \n"+
		  " , 'CHUC VU' POS_OLD \n"+
		  " from n_Employee a, n_labour b,n_department dt, "+tableName+" c \n"+
		  " Where a.depsn=dt.id_dept "+ dkFact+"\n"+
		  " and a.empsn=b.empsn and a.empsn=c.empsn and b.clock=1 \n"+
		  " and c.ghichu_trangthai in ('"+"DCLuong"+"')\n"+
		  " and c.empsn not in (select d.empsn from n_emp_quit d where d.empsn=a.empsn \n"+
		  " and d.to_date+1<to_date('20/" + m2 + "','dd/mm/yyyy')\n"+
		  " and (d.thang_tanglai is null \n"+
		  "or (months_between(d.thang_tanglai,to_Date('"+m1+"','mm/yyyy'))>=0)) )\n" +
		  " and c.thoigian=to_date('01/" + m1 + "','dd/mm/yyyy')";

		InsuranceDAO insDAO = new InsuranceDAO();
		int dc_dongloat		= Integer.parseInt(insDAO.GetField("count(*)", "n_dcluong_dongloat", "to_char(ngaydc,'"+"dd/mm/yyyy"+"')", "baodc_tangmoi", "", "01/"+m1, "Y", ""));
		String sql1 =null;
		if (dc_dongloat==1)
		{
			int tienDCDLoat = Integer.parseInt(insDAO.GetField("Sotienthaydoi", "n_dcluong_dongloat", "to_char(ngaydc,'"+"dd/mm/yyyy"+"')", "baodc_tangmoi", "", "01/"+m1, "Y", ""));
			// DC luong cho nhung nguoi tang moi vi co su thay doi luong dong loat sau khi bao tang moi
			sql1 = temp+", '"+"DCDL"+"' as DCDLoat \n"+  	
		    ",c.luonghd- "+tienDCDLoat+" SAL_OLD \n"+	
		    ",'"+"CHUC VU"+"' POS_OLD \n"+
		    "from n_Employee a, n_labour b,n_department dt, "+tableName+" c \n"+
		    "where a.depsn=dt.id_dept "+dkFact+"\n"+
		    "and a.empsn=b.empsn and a.empsn=c.empsn and b.clock=1 \n"+
		    "and c.ghichu_trangthai in ('"+"TangMoi"+"','"+"TangMoiLai"+"')\n"+
		    "and c.empsn not in (select d.empsn from n_emp_quit d where d.empsn=a.empsn \n"+
		    "and d.to_date+1<to_date('20/" + m2 + "','dd/mm/yyyy')\n"+
		    "and (d.thang_tanglai is null \n"+
		    "or (months_between(d.thang_tanglai,to_Date('"+m1+"','mm/yyyy'))>=0)) )\n" + 
		    " and c.thoigian=to_date('01/" + m1 + "','dd/mm/yyyy')";
		    sql= sql+" Union "+sql1;
		}
				
		logger.debug(sql);
		return sql;
	}	
	
	public static String getGiamTrongTangMoiQuery(String dkFact, int month, int year){
		String m1 = (month<10?"0"+month:month) + "/" + year;
		String m2;
		if (month==12) {
			m2 = "01/"+(year+1);
		}else{
			m2 = (month+1<10?"0"+(month+1):(month+1))+"/"+year;
		}		
		InsuranceDAO ins = new InsuranceDAO();
		String ngay1	= ins.get_todate(DateUtils.getFirstDayString(month, year));
		// ngay 15 cua monh-1
		String ngay15	= ins.get_todate(DateUtils.getDay15String(month-1, year));
		String ngayCuoi = ins.get_todate(DateUtils.getLastDayString(month, year));
		
		String sql=" select a.Fname,a.lname,a.birthday,a.sex,a.position,A.birthplace \n"+
		  ",bsaly_by_date(a.empsn,"+ngayCuoi+") bsalary,b.id_labour,'"+m1+"' Month_BC,a.empsn \n"+
		  ",dt.name_fact||' '||dt.name_group as name_group ,dt.name_dept_name,a.date_hired \n"+
		  " ,(select e.id_social from n_social e where e.empsn=a.empsn and e.clock=1) id_social\n"+
		  " , a.id_no, '"+"GiamTangMoi"+"' as note_tang\n"+ 		
		  " from n_Employee a, n_labour b,n_department dt, n_emp_quit c \n"+
		  " Where c.depsn=dt.id_dept "+ dkFact+"\n"+
		  " and a.empsn=b.empsn and a.empsn=c.empsn and b.clock=1 \n"+
		  " and c.from_date is null and c.to_Date is null \n"+
		  //ton tai trong ds tang moi cua thang bao cao
		  " and ( \n"+
		  //1 : Ky HDLD lan 1 vao ngay 15/month-1 va ngay 1/month
		  "((b.date_s ="+ngay1+" or b.date_s ="+ngay15+") and b.times=1) \n"+
		  //2 : Thuoc dang CNV dieu dong khu NGAYNX_MOI trong N_EMPLOYEE= ngay 1/ month 
		  // va dd_khu rong N_NEWWORKER_TEST=1, co the ko can dk nay
		  " or (a.ngaynx_moi="+ngay1+"\n"+
		  //" and exists (select d.* from n_newworker_test d where d.empsn=a.empsn and d.dd_khu=1 )\n"+
		  ")\n"+
		  //3:	CNV nghi viec xin di lam lai, thang tang lai = ngay 1/ month, tyle_tanglai=1
		  " or exists (select * from n_emp_quit e where e.empsn=a.empsn and e.thang_tanglai="+ngay1+" and e.tyle_tanglai=1)\n"+		  
		  ")\n"+
		  // Va chon trang thai bao giam la GIAM TANG MOI + ngay tra the <= 5 ngay ke tu ngay nhan the, co the bo qua dk ngay nhan the
		  " and c.note_giam_bhyt='"+"GIAM TANG MOI"+"'\n"+
		  // co the ko can dk ngay tra the BHYT		  
	      " and c.date_bhyt >=(select f.date_bhyt from n_health f where f.clock=1 and a.empsn=f.empsn )\n"+
	      " and c.date_bhyt <=(select f.date_bhyt+4 from n_health f where f.clock=1 and a.empsn=f.empsn )\n"+	      
	      // kem theo dk loai tru
	      " and a.empsn not in (select h.empsn from n_not_insurance h \n"+
		  " where h.empsn=a.empsn and h.dates<="+ngay1+")";	
		logger.debug(sql);
		return sql;
	}	
	
	public static String getThayDoiTTBHQuery(String dkFact, int month, int year){
		String m1 = (month<10?"0"+month:month) + "/" + year;
		String m2;
		if (month==12) {
			m2 = "01/"+(year+1);
		}else{
			m2 = (month+1<10?"0"+(month+1):(month+1))+"/"+year;
		}
		InsuranceDAO ins = new InsuranceDAO();
		String ngay1 = ins.get_todate(DateUtils.getFirstDayString(month, year));
        String sql=" select dt.id_dept,a.empsn,a.fname,a.lname,b.id_social,c.id_health \n"+
        ",d.idpro_old||'"+"_"+"'||d.idhos_old as TT_old \n"+        
        ",d.idpro_new||'"+"_"+"'||d.idhos_new TT_New ,d.note,dt.name_dept,'"+m1+"' month_bc\n"+
        " From n_employee a,n_department dt,n_social b,n_health c,n_change_hospital d,n_hospital e \n"+        
        " where a.depsn=dt.id_dept "+dkFact+" \n"+
        " and a.empsn=b.empsn and a.empsn=c.empsn and a.empsn=d.empsn \n"+
        " and c.id_pro=e.id_province and c.id_hos=e.id_hos and b.clock=1 and c.clock=1 \n"+        
        " and day_of_month(d.date_change,'"+"01"+"')="+ngay1+"\n"+
        " Union \n"+
        " select dt.id_dept,a.empsn,a.fname,a.lname,b.id_social,c.id_health \n"+
        " ,d.fname_old||'"+" "+"'||d.lname_old ||'"+", "+"'\n"+
        "||d.idno_old||'"+", "+"'||d.address_old as TT_Old \n"+
        ",d.fname_new||'"+" "+"'||d.lname_new ||'"+", "+"'\n"+
        "||d.idno_new||'"+", "+"'||d.address_new as TT_new \n"+        
        ",d.note,dt.name_dept,'"+m1+"' month_bc\n"+
        " From n_employee a,n_department dt,n_social b,n_health c,n_dcnhanthan d \n"+
        " where a.depsn=dt.id_dept "+dkFact+"\n"+
        " and a.empsn=b.empsn and a.empsn=c.empsn\n"+
        " and a.empsn=d.empsn and b.clock=1 and c.clock=1 \n"+
        " and day_of_month(d.date_change,'"+"01"+"')="+ngay1+"\n"+
        // 2 nguoi ben y te ko tham gia BH
	    " and a.empsn not in (select h.empsn from n_not_insurance h \n"+
		" where h.empsn=a.empsn and h.dates<="+ngay1+")";		
		
		logger.debug(sql);
		return sql;
	}	
	
	// dung cho bao cao neu thay doi thoi gian sang 21/m->20/m+1 la bao cao cua m+1
	/**
	 * 
	 * @param month 1-12, thang bao cao
	 * @param year 201x
	 * @return NSVao, RoVao, TangLai = thang bao cao
	 */
	public static String getTang20Tay2120Query(String dkFact, int month, int year){
		String m1 = (month<10?"0"+month:month) + "/" + year;		
		String ngay1  = " to_date('"+"01/"+m1+"','dd/mm/yyyy')";
		String ngay20 = " to_date('"+"20/"+m1+"','dd/mm/yyyy')";		
		
		String sql = "select a.Fname,a.lname,a.birthday,a.sex,a.position,A.birthplace ,c.luonghd bsalary,b.id_labour\n" +
				", b.date_s,a.empsn,dt.name_fact||' '||dt.name_group as name_group,dt.name_dept_name,a.date_hired , a.id_no\n" + 
				", b.date_s+1 as e_Dates ,(select e.id_social from n_social e where e.empsn=a.empsn and e.clock=1) id_social\n" + 
				" ,'" + m1 + "' month_bc\n" + 
				" ,(case when c.ghichu_trangthai='NSVao' then 'NS vao'\n" + 
				" when c.ghichu_trangthai='RoVao' then 'Ro vao'    else ''  end ) as note_tang\n" + 
				"From n_Employee a, n_labour b,n_department dt, n_social_infor_report c\n" + 
				"Where a.depsn=dt.id_dept  and a.empsn=b.empsn and a.empsn=c.empsn and b.clock=1\n" + 
				"and c.ghichu_trangthai in ('NSVao','RoVao') and c.trangthai=1\n" + 
				"and c.empsn not in (select d.empsn from n_emp_quit d\n" + 
				"where d.empsn=a.empsn  and d.to_date+1<"+ngay20+"\n" + 
				"and (d.thang_tanglai is null\n" + 
				"or (months_between(d.thang_tanglai,to_Date('"+m1+"','mm/yyyy'))>=0)) )\n" + 
				"and c.thoigian="+ngay1 + dkFact+ "\n"+
				"   union\n" + 
				"select c.Fname,c.lname,c.birthday,c.sex,c.position,c.birthplace ,b.salary salary ,b.id_labour\n" + 
				",b.date_s,c.empsn,dt.name_fact||' '||dt.name_group as name_group ,dt.name_dept_name,c.date_hired, c.id_no\n" + 
				",a.date_again e_dates,(select id_social from n_social where empsn=c.empsn and clock=1) id_social\n" + 
				" ,'" + m1 + "' month_bc ,'Di lam lai' as Note_Tang\n" + 
				" From n_Employee c, n_labour b,n_department dt,n_emp_quit a\n" + 
				" Where a.depsn=dt.id_dept  and a.empsn=b.empsn and a.empsn=c.empsn\n" + 
				" and b.clock=1 and ( (b.times >1)  or (b.times=1 and b.date_s<="+ngay1+" ))\n" + 
				" and to_char(a.thang_tanglai,'mm/yyyy')='" + m1 + "' and a.tyle_tanglai=20\n" + 
				" and c.empsn not in (select q.empsn from n_emp_quit q   Where a.empsn=q.empsn and q.date_again\n" + 
				" is null  and (q.from_date is null and q.to_date is null\n" + 
				" AND(Q.NOTE_GIAM_BHYT IS NULL OR q.note_giam_bhyt='GIAM TANG MOI')))"  + dkFact ;
		logger.debug(sql);
		return sql;
	}	
	
	public static String getGiam20Tay2120Query(String dkFact, int month, int year){
		String m1 = (month<10?"0"+month:month) + "/" + year;
		String ngay1  = " to_date('"+"01/"+m1+"','dd/mm/yyyy')";
		String ngay20 = " to_date('"+"20/"+m1+"','dd/mm/yyyy')";
		
		String sql= "select a.Fname,a.lname,a.birthday,a.sex,a.position,a.birthplace \n "+
		  ",c.luonghd salary,b.id_labour,b.date_s,a.empsn ,dt.name_fact||' '||dt.name_group as name_group\n"+
		  ",dt.name_dept_name \n"+
		  ",(select e.id_social from n_social e where e.empsn=a.empsn and e.clock='1') id_social  \n"+
		  ",'"+m1+"' month_bc,c.ghichu_trangthai Note_Tang \n"+
		  ",0 sal_old \n"+
		  " From n_Employee a, n_labour b,n_department dt, n_social_infor_report c \n"+
		  " where a.depsn=dt.id_dept "+dkFact+"\n"+
		  " and a.empsn=b.empsn and a.empsn=c.empsn and b.clock='1'\n"+		  
		  " and c.trangthai='-1'\n"+
		  //--Loai tru nghi viec da bao giam va chua bao tang lai, or thang tang lai>= thang bao cao
		  " and c.empsn not in (select d.empsn from n_emp_quit d where d.empsn=a.empsn \n"+
		  " and d.to_date+1<"+ngay20+"\n" + 
		  " and ((d.thang_tanglai is null) \n"+
/*		  { sua lai 01/03/2013
		--ko duoc bang 0 vi if tang lai T01, dong thoi RoRa T1 luon thi CT se ko bao RoRa hixx
		-- VD 11031776 tang lai T01 va RoRa T01 luon, QT20 T01/2013, bao giam 21/02/2013
		-- vi co truong hop tang lai 20 tay, thang tang lai la 20/01/2013 > 01/01/2013
		-- months_between se tra ve gia tri >0 cung sai. Vi vay chi so sanh mm/yyyy ma thoi
		   }*/
		  " or (months_between(to_date(to_char(d.thang_tanglai,'mm/yyyy'),'mm/yyyy')\n" +
		  ",to_Date('"+m1+"','mm/yyyy'))>0)\n"+
		  " ) ) \n"+
		  //--Loai tru giam trong tang moi cua thang do, va chua co bao tang lai
		  " and c.empsn not in (select d.empsn from n_emp_quit d where d.empsn=a.empsn \n"+
		  " and d.real_off_date<"+ngay20+"\n" +
		  " and d.thang_tanglai is null and d.note_giam_bhyt='"+"GIAM TANG MOI"+"' )\n"+
		  " and c.thoigian="+ngay1;
		
		logger.debug(sql);
		return sql;
	}		
	
	
	// dung cho chay bao cao 2 lan trong thang, vd xet bao cao T06/2013
	// QT 20 lan 1 20/06 : tu ngay 1-> ngay 16 cua thang
	// QT 20 lan 2 12/07 : tu ngay 1-> ngay cuoi thang 06, bao cao nay se bo sung hoac thu hoi so voi lan 1
	//n_social_infor_report 	: ok ds cua toan thang
	//n_social_infor_report_1	: QT20 cho lan 1, 20/06/2013 la cho T06
	//n_social_infor_report_2	: QT20 cho lan 2, 12/07/2013 la cho T06 bo sung
	
	/**
	 * 
	 * @param month 1-12, thang bao cao
	 * @param year 201x
	 * @return all ds thay doi trong thang
	 */
	public static String getQT20TayQuery(String dkFact, String tableName, int month, int year, int lanBC){
		//dkfact: dk lay thong tin, tableName : ten table de lay du lieu cho QT20 lan 1 hay 2
		//20/06 la QT cho T06, ko phai la 20/07 nua. Vi vay ko can dung den m2 hay m2= m1
		String m1 = (month<10?"0"+month:month) + "/" + year;		
		String m2;
		String ngayGioiHan;
		String sqlAll, sqlTang, sqlGiam, sqlDC ;
		String sqlLamLai;
		String ngay1 = " to_date('"+"01/"+m1+"','dd/mm/yyyy')";
		if (lanBC==1){
			m2 = m1;
			ngayGioiHan ="20/"+m2;
		}
		else{					
			if (month==12) {
				m2 = "01/"+(year+1);
			}else{
				m2 = (month+1<10?"0"+(month+1):(month+1))+"/"+year;
			}
			ngayGioiHan = "12/"+m2;
		}
		ngayGioiHan = " to_date('"+ngayGioiHan+"','dd/mm/yyyy')";
		String sqlSelect=" select a.Fname,a.lname,a.birthday, a.empsn, c.luonghd bsalary \n"+
		  ", dt.name_fact||' '||dt.name_group as name_group ,dt.name_dept_name \n"+
		  ",(select e.id_social from n_social e where e.empsn=a.empsn and e.clock=1) id_social \n"+
		  ", '"+m1 + "' month_bc\n";

		String dkFrom = " from n_Employee a, n_labour b,n_department dt, "+tableName+" c \n"+
				  " Where a.depsn=dt.id_dept "+ dkFact+"\n"+
				  " and a.empsn=b.empsn and a.empsn=c.empsn and b.clock=1 \n"+				  
				  " and c.empsn not in (select d.empsn from n_emp_quit d where d.empsn=a.empsn \n"+		  
				  " and d.to_date+1<"+ngayGioiHan+"\n"+
				  " and (d.thang_tanglai is null \n"+
				  "or (months_between(d.thang_tanglai,to_Date('"+m1+"','mm/yyyy'))>=0)) )\n" +
				  " and c.ThangBaoCao="+ngay1;		
		
		//Lay ds NSVao, RoVao, Di Lam Lai		
		
		sqlTang = sqlSelect + " ,c.ghichu_trangthai as note_tang\n" +
				", 'Tang' DCDLoat, 0 sal_old \n"
				+ dkFrom +"\n"				
				+ " and c.ghichu_trangthai in ('NSVao','RoVao','Tang1Thang','TangTuThang') and c.trangthai=1\n";
		//vi di lam lai ko lay trong n_social_info_report nen se lay luong trong n_labour
		sqlLamLai = " select a.Fname,a.lname,a.birthday, a.empsn, b.salary bsalary \n"+
				  ", dt.name_fact||' '||dt.name_group as name_group ,dt.name_dept_name \n"+
				  ",(select e.id_social from n_social e where e.empsn=a.empsn and e.clock=1) id_social \n"+
				  ", '"+m1 + "' month_bc,'Di lam lai' as Note_Tang, 'Tang' DCDLoat, 0 sal_old \n "+
				" From n_Employee a, n_labour b,n_department dt,n_emp_quit c\n" + 
				" Where c.depsn=dt.id_dept  and a.empsn=b.empsn and a.empsn=c.empsn\n" + 
				" and b.clock=1 and ( (b.times >1)  or (b.times=1 and b.date_s<="+ngay1+" ))\n" + 
				" and to_char(c.thang_tanglai,'mm/yyyy')='" + m1 + "' and c.tyle_tanglai=20\n" + 
				" and c.empsn not in (select q.empsn from n_emp_quit q   Where a.empsn=q.empsn and q.date_again\n" + 
				" is null  and (q.from_date is null and q.to_date is null\n" + 
				" AND(Q.NOTE_GIAM_BHYT IS NULL OR q.note_giam_bhyt='GIAM TANG MOI')))"  + dkFact ;

		sqlTang = sqlTang +" union all " + sqlLamLai;
		
		//Cau lenh Lay ds DCLuong
		sqlDC = sqlSelect+",'"+"DCLuong"+"' as note_tang,'"+"DCBT"+"' as DCDLoat \n"+  // DC luong binh thuong  
		  //luong gan nhat co tham gia BH khi xuat ra excel se lay gia tri
		  " ,0  sal_old \n"+		  
		  dkFrom+"\n"+
		  " and c.ghichu_trangthai in ('"+"DCLuong"+"') and c.trangthai=1" ;

		InsuranceDAO insDAO = new InsuranceDAO();
		int dc_dongloat		= Integer.parseInt(insDAO.GetField("count(*)", "n_dcluong_dongloat", "to_char(ngaydc,'"+"dd/mm/yyyy"+"')", "baodc_tangmoi", "", "01/"+m1, "Y", ""));
		String sql1 =null;
		if (dc_dongloat==1)
		{
			int tienDCDLoat = Integer.parseInt(insDAO.GetField("Sotienthaydoi", "n_dcluong_dongloat", "to_char(ngaydc,'"+"dd/mm/yyyy"+"')", "baodc_tangmoi", "", "01/"+m1, "Y", ""));
			// DC luong cho nhung nguoi tang moi vi co su thay doi luong dong loat sau khi bao tang moi
			sql1 = sqlSelect+",'"+"DCLuong"+"' as note_tang,'"+"DCDL"+"' as DCDLoat \n"+  	
		    ",c.luonghd- "+tienDCDLoat+" SAL_OLD \n"+		    
		    dkFrom+"\n"+			
			"and c.ghichu_trangthai in ('"+"TangMoi"+"','"+"TangMoiLai"+"')";			
		}			
		if (sql1!=null)
				sqlDC= sqlDC+" Union all "+sql1;		
		
		// Lay ds NSRa, RoRa
		// Loai tru nghi viec da bao giam va chua bao tang lai or bao tang lai > thang bao cao
		// khac voi nhung loai bao cao tren la >= thang bao cao vi vay ko dung chung dkFrom chung duoc
		sqlGiam = sqlSelect+ ",c.ghichu_trangthai as note_tang,'Giam' as DCDLoat \n"+  	
			    ",0 SAL_OLD \n"+
		  " From n_Employee a, n_labour b,n_department dt, "+tableName+" c \n"+
		  " where a.depsn=dt.id_dept "+dkFact+"\n"+
		  " and a.empsn=b.empsn and a.empsn=c.empsn and b.clock='1'\n"+		  
		  " and c.trangthai='-1'\n"+
		  //--Loai tru nghi viec da bao giam va chua bao tang lai, or thang tang lai> thang bao cao
		  " and c.empsn not in (select d.empsn from n_emp_quit d where d.empsn=a.empsn \n"+
		  " and d.to_date+1<"+ngayGioiHan+"\n" + 
		  " and ((d.thang_tanglai is null) \n"+
		  /*{ sua lai 01/03/2013
		--ko duoc bang 0 vi if tang lai T01, dong thoi RoRa T1 luon thi CT se ko bao RoRa if >=0 hixx
		-- VD 11031776 tang lai T01 va RoRa T01 luon, QT20 T01/2013, bao giam 21/02/2013
		-- vi co truong hop tang lai 20 tay, thang tang lai la 20/01/2013 > 01/01/2013
		-- months_between se tra ve gia tri >0 cung sai. Vi vay chi so sanh mm/yyyy ma thoi
		   }*/
		  " or (months_between(to_date(to_char(d.thang_tanglai,'mm/yyyy'),'mm/yyyy')\n" +
		  ",to_Date('"+m1+"','mm/yyyy'))>0)\n"+
		  " ) ) \n"+
		  
		  //--Loai tru giam trong tang moi cua thang do, va chua co bao tang lai
		  " and c.empsn not in (select d.empsn from n_emp_quit d where d.empsn=a.empsn \n"+
		  " and d.real_off_date<"+ngayGioiHan+"\n" +
		  " and d.thang_tanglai is null and d.note_giam_bhyt='"+"GIAM TANG MOI"+"' )\n"+
		  " and c.ThangBaoCao="+ngay1;		
		
		sqlAll = sqlTang +" union all "+ sqlDC+ " union all "+ sqlGiam;
		//System.out.println(sqlAll);
		logger.debug(sqlAll);
		return sqlAll;
	}
}
