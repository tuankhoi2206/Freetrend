package ds.program.fvhr.ngan.ui.Rest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.omg.CORBA.DATA_CONVERSION;

import ds.program.fvhr.domain.N_REST_ANNUAL_TYPE;
import ds.program.fvhr.domain.N_REST_DETAIL;
import ds.program.fvhr.domain.pk.N_REST_DETAILPk;
import ds.program.fvhr.son.ui.ObjUtility;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.dao.DataObjectSet;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;

public class Rest_utility {

	OBJ_UTILITY 		_util;
	N_REST_DETAIL 	_obj_rest_detail;
	
	public Rest_utility() {
		_util = new OBJ_UTILITY();
	}
	
	protected N_REST_ANNUAL_TYPE Get_Rest_Annual(String year){
		IGenericDAO<N_REST_ANNUAL_TYPE, String> obj_dao = Application.getApp().getDao(N_REST_ANNUAL_TYPE.class);
		return obj_dao.findById(year);
	}
	
	protected N_REST_DETAIL Get_Obj_Rest_Detail(N_REST_DETAIL obj_rest_detail){
		N_REST_DETAIL	obj_data = new N_REST_DETAIL();
		
		return obj_data;
	}
	
	protected Date Get_hired_date_by_emp(String empsn){
		Date hired_date = null;
		OBJ_UTILITY util_ = new OBJ_UTILITY();
		SimpleDateFormat sf_ = new SimpleDateFormat("dd/MM/yyyy");
		String sql =  
			"select e.date_hired from n_employee e\n" +
			"where e.empsn ='"+empsn+"'";
		Object obj_rs = util_.Exe_Sql_Obj(sql);
		hired_date	  = OBJ_UTILITY.DateFormat_DDMMYYYY((Date) obj_rs);
		
		return hired_date;
	}
	
	
	protected boolean Check_available_rest_type(String empsn,String rest_type,int year){

		IGenericDAO<N_REST_DETAIL, String> objDao = Application.getApp().getDao(N_REST_DETAIL.class);
		DataObjectSet dataSet = new DataObjectSet(objDao, N_REST_DETAIL.class);
		
		// sum detail rest
		OBJ_UTILITY 		util_ 		= new OBJ_UTILITY();
		Rest_utility	utilRest 	= new Rest_utility();
		String sql = "";
		Object rs_count = utilRest.Count_Rest_Type_Used(empsn, year, rest_type);
		
		// compare with n_rest
		if(rest_type.equals("TET")){
				sql = 
					"select t.hol_lunar from n_rest t\n"  ;
		}
		else if(rest_type.equals("CTY")){
				sql = 
					"select t.co_arr from n_rest t\n"  ;
		}
		else if(rest_type.equals("CNV")){
				sql = 
					"select t.emp_app from n_rest t\n"  ;
		}
		else if(rest_type.equals("TON")){
				sql = 
					"select t.stored from n_rest t\n" ;
		}
		
		sql = sql + " where t.empsn ='"+empsn+"'\n" + 
					" and t.year ='"+year+"'";
		
		Object rs_find	 = util_.Exe_Sql_Obj(sql);
		
		if(Integer.valueOf(rs_find.toString()) != 0 && String.valueOf(rs_count).equals(String.valueOf(rs_find)))
			return false;
		return true;
		
	}
	
	protected long Count_Rest_Type_Used(String empsn,int year,String rest_type){
		long rs_num = 0;
		String sql = 
						"SELECT count(rest_kind)\n" +
						" FROM n_rest_detail t\n" + 
						" WHERE t.empsn ='"+empsn+"'\n" +
						" AND t.year='"+year+"'" +
						" AND t.rest_type='"+rest_type+"'";

		Object	rs = _util.Exe_Sql_Obj(sql);
		
		rs = (rs == null ? 0 : rs);
		BigDecimal a = (BigDecimal) rs;
		return a.longValue();
	}
	
	/**
	 * 	Tong phep nam da su dung : TET & CNV & CTY & TON
	 * 
	 * @param empsn
	 * @param year
	 * @return
	 */
	protected long Count_Total_PN_Rest_Used(String empsn,int year) {
		
		long rs = 0;
		rs 	= this.Count_Rest_Type_Used(empsn, year, "TET");
		rs	= rs + this.Count_Rest_Type_Used(empsn, year, "CNV");
		rs	= rs + this.Count_Rest_Type_Used(empsn, year, "CTY");
		rs	= rs + this.Count_Rest_Type_Used(empsn, year, "TON");
		return rs;
	}
	
	/**
	 *  Tong phep nam da su dung
	 */
	
	protected long Count_Total_PN_Rest_Used(String empsn,String year){
		long rs_num = 0;
		String sql = 
						"SELECT count(rest_kind)\n" +
						" FROM n_rest_detail t\n" + 
						" WHERE t.empsn ='"+empsn+"'\n" +
						" AND t.year='"+year+"'" + 
						" AND t.rest_kind='PN'";

		Object	rs = _util.Exe_Sql_Obj(sql);
		
		rs_num = Long.valueOf(rs.toString());
		
		return rs_num;
	}
	
	/**
	 * 	lay tong phep nam con lai cua nam truoc 
	 * 	ap dung cho viec tinh phep ton cua nam nay
	 * 		(Obtain(year-1) + Stored(year-1)) - Count_PN(year-1)
	 * @param empsn
	 * @param year
	 * @return
	 */
	protected long Get_remain_rest_pre_year(String empsn, Calendar ca_now){
		long rs_num 	= 0;
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(sf.format(ca_now.getTime()));
		int pre_year 	= ca_now.get(Calendar.YEAR)-1;
		
		Long total_PN_pre_year 		= this.Count_Total_PN_Rest_Used(empsn, String.valueOf(pre_year));
			 total_PN_pre_year		= total_PN_pre_year == null ? 0 : total_PN_pre_year;
		
		String sql = 
			"SELECT (t.obtain + t.stored)\n" +
			" FROM n_rest t\n" + 
			" WHERE t.empsn ='"+empsn+"'\n" + 
			" AND t.year ='"+String.valueOf(pre_year)+"'";
		
		Object rs =  _util.Exe_Sql_Obj(sql);
		rs_num	= (rs == null ? 0 : Long.valueOf(rs.toString())) - total_PN_pre_year;
		
		return rs_num;
	}
	
	/**
	 * 	Tinh tong phep nam co the co trong nam cua 1 so the <br/>
	 * 	Cu moi 1 thang dc 1 phep nam <br/> 
	 * 
	 * 	obtain = 12 + phep CN may + phep tham nien
	 */
	
	protected long Count_Obtain(String empsn,Calendar ca_now){
		
		long rs = 12;	// mac dinh moi nam co 12 ngay phep
		
		Calendar ca_hired	= Calendar.getInstance();
		
		// tinh ngay nhap xuong cua nhan vien + tham niem
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		Date date_now	= new Date();
		Date date_hired = this.Get_hired_date_by_emp(empsn);
		
		ca_now.setTime(date_now);
		ca_hired.setTime(date_hired);
		
		System.out.println(sf.format(ca_hired.getTime()));
		if(ca_now.get(Calendar.YEAR) == ca_hired.get(Calendar.YEAR)){ // nhap xuong trong nam
			
			rs = 12 - (ca_hired.get(Calendar.MONTH) + 1);
			if(ca_hired.get(Calendar.DATE)<=15){
				rs ++;
			}
			
		}
		else if(ca_now.get(Calendar.YEAR) > ca_hired.get(Calendar.YEAR)){ // da co tham niem
			// cu 5 nam dc 1 phep tham niem
			rs = 12 + (int)((ca_now.get(Calendar.YEAR)-ca_hired.get(Calendar.YEAR))/5);
			
		}else{
			return 0;
		}
		
		
		
		rs = rs + (isDvMAY(empsn)?2:0);
		
		return rs;
	}
	
	@Deprecated
	protected boolean isDvMAY(String empsn){
		// CAI NAY TAM THOI CO THE SAI : them 2 ngay phep neu o dvi MAY
		// kiem tra empsn co nam trong n_department.note = 'MAY'
		
		String sql = 
						"SELECT count(e.empsn) from n_employee e,n_department t\n" +
						"WHERE e.depsn = t.id_dept\n" 		+ 
						"      AND t.id_dept <> '00000'\n" 	+ 
						"      AND t.note ='MAY'" 			+ 
						"	   AND e.empsn ='"+empsn+"'"	+
						"" ;
		Object obj_rs = _util.Exe_Sql_Obj(sql);
		return Integer.valueOf(obj_rs.toString())==0?false:true;
	}
	
	
	protected N_REST_ANNUAL_TYPE Obj_Rest_Annual(String year){
		N_REST_ANNUAL_TYPE obj_data = null;
		IGenericDAO<N_REST_ANNUAL_TYPE, String> obj_dao = Application.getApp().getDao(N_REST_ANNUAL_TYPE.class);
		obj_data	= obj_dao.findById(year);
		return obj_data;
	}
	
	
	
//*Begin 16/04/2013 viet may ham nhap phep o day de sdung cho 2 form n_emp_Rest_01MDetailContent0 & n_Registry_Rest_Form
	public ArrayList<Date> getListDateOff(Date dateFrom, Date dateTo,String sothe, String kt_ngay_CN) {//co kt neu la CN k hop le (neu la BV thi CN hop le cho nhap phep)
		
		ArrayList<Date> listDate = new ArrayList<Date>();
		
		if((Check_is_Sunday(dateFrom)== false) || (Check_is_Sunday(dateFrom) && Kt_Donvi_Baove(sothe))){//27/03/2012 Ngan them k can kiem tra CN cho dv = "BV" 
			listDate.add(dateFrom);
		}else{
			//ObjUtility.ShowMessageError("Chu Nhat không cần đăng ký phép");
			kt_ngay_CN = "CN";
			return listDate;
		}
		
			dateFrom = ObjUtility.DAY_NEXT(dateFrom);
		
			while(dateFrom.before(dateTo) || dateFrom.equals(dateTo)){
				
				if( (!Check_is_Sunday(dateFrom)) || (Check_is_Sunday(dateFrom) && Kt_Donvi_Baove(sothe)) ){
					
					listDate.add(dateFrom);
				}
				
				dateFrom = ObjUtility.DAY_NEXT(dateFrom);
			}
		return listDate;
	}	
	
//  loai bo ngay CN
	private boolean Check_is_Sunday(Date date_in){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date_in);
		if(ca.get(ca.DAY_OF_WEEK) == Calendar.SUNDAY){
			return true;
		}
		
		return false;
	}
	
	 public boolean Kt_Donvi_Baove(String sothe)
	 {   //Neu note trong N_department la 'BV'-->Bao ve thi van cho nhap phep ngay CNhat binh thuong
		 String sql = 
			 "select t.note  from n_department t\n" +
			 "where t.id_dept = (select e.depsn  from n_employee e where e.empsn = '"+sothe+"')";
		 
		 OBJ_UTILITY obj_util = new OBJ_UTILITY();
		 Object obj			 = obj_util.Exe_Sql_Obj(sql); 
		 if(obj == null || !obj.equals("BV"))
		 {
			 return false;
		 }
		 return true;
	 }
	
	
//*End 16/04/2013 viet may ham nhap phep o day de sdung cho 2 form n_emp_Rest_01MDetailContent0 & n_Registry_Rest_Form
	
	
}
