package ds.program.fvhr.son.ui;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import ds.program.fvhr.domain.N_EMPLOYEE;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;

public class ObjEmpsn {
	
	public static int empsn_NV = -1 	;// "Nghi viec";
	public static int empsn_NS = 0		;// "Nghi san";
	public static int empsn_BT = 1		;// "Binh thuong";
	
	
	private String 		_empsn ;
	private String 		_month;
	private Date   		_date;
	private String 		_year;
	private float  		_workday;			// so ngay cong trong thang
	private int 		_status_work ;		// trang thai lam viec : binh thuong - nghi san - nghi viec
	private boolean 	_flag_health;		// trang thai tham gia bao hiem yte
	
	private Date		_real_off_date;
	private boolean		_status_card;
	private Date		_return_card_date;
	
	private int			_n_bear;
	private Date		_bear_date_b;
	private Date		_bear_date_e;
	
	ObjUtility obj_util;
	private ArrayList<Object> _list_empn_info_basic;
	SimpleDateFormat sf = obj_util.Get_format_date();
	
	/**
	 * So thang dang ki NS
	 * DK : thuc thi ham CHECK_EMP_BEAR() 
	 */
	
	public int Get_N_Bear(){
		return _n_bear;
	}
	
	public Date Get_Bear_Date_Begin(){
		return _bear_date_b;
	}
	public Date Get_Bear_Date_End(){
		return _bear_date_e;
	}
	
	/**
	 * Trang thai tra the BHYT khi nghi viec
	 * @return
	 */
	public boolean Get_Status_Card(){
		return _status_card;
	}
	/**
	 * Ngay tra the BHYT
	 * DK : thuc thi CHECK_EMP_QUIT()
	 * @return
	 */
	public Date Get_Return_Card_Date(){
		return _return_card_date;
	}
	
	/**
	 * Ngay thuc nghi viec 
	 * @return
	 */
	public Date Get_Real_Off_Date(){
		return _real_off_date;
	}
	
	public boolean Get_Flag_Health(){
		return _flag_health;
	}
	/**
	 * so ngay cong trong thang
	 * @return
	 */
	public float Get_WORK_DAY(){
		return _workday;
	}
	
	/**
	 * 
	 * 	empsn : 0 
	 *  empcn : 1 
	 *	fname : 2 
	 * 	lname : 3 
	 *	name_dept_name : 4 
	 *  name_group : 5 
	 * 	name_fact : 6
	 */
	
	public ArrayList<Object> List_EMP_INFO(){
		return _list_empn_info_basic;
	}
	
	public ObjEmpsn(){
		obj_util = new ObjUtility();
	}
	
	public int Get_Status_Work(){
		return _status_work;
	}
	
	/**
	 * Find_Emp_Info_Basic()
	 * @param empsn
	 */
	public ObjEmpsn(String empsn){
		obj_util = new ObjUtility();
		this._empsn = empsn;
		_list_empn_info_basic = Find_Emp_Info_Basic();
	}
	
	
	/**
	 * COUNT_WORKDAY()
	 * 
	 * Check_Work_Status()
	 * @param empsn
	 * @param date
	 */
	public ObjEmpsn(String empsn,Date date_input_){
		obj_util = new ObjUtility();
		String date_str = "" ;
		date_str = sf.format(date_input_);
		
		this._empsn 	= empsn;
		this._date 		= date_input_;
		_month 			= date_str.substring(3, 5);
		_year			= date_str.substring(6,10);
		CHECK_WORK_STATUS();
		_workday 		= COUNT_WORKDAY();
		
		
		
	}
	
	
	private void Check_N_HEALTH_R() {/*
		IGenericDAO<N_N_HEALTH_R, String> obj_health = Application.getApp().getDao(N_N_HEALTH_R.class);
		DetachedCriteria obj_dc = DetachedCriteria.forClass(N_N_HEALTH_R.class);
		obj_dc.add(Restrictions.eq("EMPSN", _empsn));
		
		List<N_N_HEALTH_R> list = obj_health.findByCriteria(1, obj_dc);
		if(list.size() > 0){
			_flag_health = true;
		}else{
			_flag_health = false;
		}
	*/}

	public void Set_Month(String month){
		_month 	= month;
	}
	
	public void Set_Year(String year){
		_year	= year;
	}
	
	/**
	 * 
	 * 	empsn : 0 
	 *  empcn : 1 
	 *	fname : 2 
	 * 	lname : 3 
	 *	name_dept_name : 4 
	 *  name_group : 5 
	 * 	name_fact : 6
	 */
	
	private ArrayList<Object> Find_Emp_Info_Basic(){
		ArrayList<Object> list = new ArrayList<Object>();
		String sql = 	"";
		
		sql = 	" select 	e.empsn," +
				"			e.empcn," +
				"			e.fname," +
				"			e.lname," +
				"			d.name_dept_name," +
				"			d.name_group," +
				"			d.name_fact" + 
				" from n_employee e,n_department d " +
				" where e.depsn = d.id_dept " + 
				" and e.empsn = '" + _empsn +  "'" ;
		
		
			list =  obj_util.Exe_sql_nfield_1row(sql, 7);
			
			
		
		return list;
	}
	
	private void CHECK_WORK_STATUS(){
		
			_status_work = this.empsn_BT;
			CHECK_EMP_BEAR(_empsn, _date);
			CHECK_EMP_QUIT(_date);
		
	}
	/**
	 * Tinh so ngay cong trong 1 thang
	 * @return
	 */
	
	private float COUNT_WORKDAY(){
		
		String str_date = 	_month + "/" + _year;
		String sql		=	"";

		float workday = 0 ;
		
		//		empsn ="05020911";
		/// 	tong so ngay cong trong thang
					
		sql = "select " 
				+ 		" (select nvl(sum(g.ducls+g.nucls+ "
	            +                        			" (case when  mod(g.oth,8)=0     then  g.oth/8 "
	            +                        			"		 when  mod(g.oth,10.4)=0  then  g.oth/(10.4) "                                        
	            +                                   "       else 0 "                                    
	            +                                   " end) ),0) "  
	            +           " From n_Data_daily g "  
	            +  			" Where g.empsn=a.empsn and to_char(g.dates,'mm/yyyy')= " + "'" + str_date + "'" +") + " 
	            
	            + 		" (select nvl(sum(g.rest_qtt),0) " 
	            + 			" From n_Data_daily g , n_rest_kind aa "  
	            +   		" Where g.empsn=a.empsn " 
	            +           " and g.rest_rs=aa.name_rest " 
	            +           " and aa.id_rest_sal='C01' " 
	            +			" and to_char(g.dates,'mm/yyyy')= " + "'" + str_date + "'" +") " 
	      
	            + " From n_employee a "
	            + " Where a.empsn = " + "'" + _empsn + "'"
	            + " and  a.empsn not in " 
	            +           			" (select b.empsn " 
	            +           						" from n_emp_quit b "  
	            +           						" where b.real_off_date<to_date('01/"+str_date + "','dd/mm/yyyy') " 
	            +                 					" AND b.DATE_AGAIN IS NULL) ";

		
		
		
		Object obj = obj_util.Exe_Sql_Obj(sql);
		
		if(obj == null){
			workday = 0;
		}
		else{
			try{
				System.out.println(obj.toString());
				workday = Float.valueOf(obj.toString());
			}catch (Exception e) {
				workday = 0;
				System.out.println("THANG NAY CHUA CO NGAY CONG");
				
			}
		}
		
		return workday;
	
	}
	
	
	/**
	 * 
	 * Kiem tra nghi viec + trang thai tra the BHYT + ngay tra the BHYT
	 * 
	 * @return
	 */
	
	private void CHECK_EMP_QUIT(Date date_intput_){  // da nghi viec  MONTH/YEAR  => ngay thuc nghi && ngay tra the
		
		String date_str 			= sf.format(date_intput_).substring(3, 10);
		
		String sql =  


			"SELECT\n" +
			"    nvl(eq.REAL_OFF_DATE,to_date('01/01/0001','dd/mm/yyyy')),\n" + 
			"       nvl(eq.THE_BHYT,-1),\n" + 
			"       nvl(eq.DATE_BHYT,to_date('01/01/0001','dd/mm/yyyy'))\n" + 
			"FROM n_emp_quit eq\n" + 
			"WHERE\n" + 
			"         to_char(eq.thang_trubh,'mm/yyyy') = '"+date_str+"'"+
 
			"     AND eq.empsn ='"+_empsn+"'";

		
		ArrayList<Object> list_obj = new ArrayList<Object>();
		
			list_obj = obj_util.Exe_sql_nfield_1row(sql, 3);
		
		if(list_obj.size() > 0){ // TRANG THAI NGHI VIEC
				 _status_work = this.empsn_NV;
				
				 _real_off_date = (Date) list_obj.get(0);
				int t = Integer.valueOf(list_obj.get(1).toString());
				
				if(t==1)
					_status_card = true;
				else
					_status_card = false;
				
				_return_card_date = (Date) list_obj.get(2);
			
		}
	}
	
	
	/**
	 * <P> Kiem tra trang thai NS tai thang hien tai
	 * <P> Neu ngay ket thuc NS > 15 cua thang thi khong con o trang thai NS
	 * 
	 */
	private void CHECK_EMP_BEAR(String empsn_,Date date_input_){

		String date_now_01 			= sf.format(ObjUtility.MONTH_NOW("01", date_input_));
		String date_now_str			= date_now_01.substring(3,10);
		ArrayList<Object> list = new ArrayList<Object>();
		
		String sql = "";
			

		sql = " SELECT t.b_dates,    t.note\n" +
			  " FROM n_time_bear t\n" + 
		      " WHERE   ((to_date('"+date_now_01+"','dd/mm/yyyy') >= t.b_dates\n" + 
		      "         and  t.e_dates >= to_date('"+date_now_01+"','dd/mm/yyyy')   )\n" + 
		      "         or\n" + 
		      "         (to_char(t.b_dates,'mm/yyyy') = '"+date_now_str+"') )\n" + 
		      "  AND   t.empsn ='"+empsn_+"'";

		
		
		
			list = obj_util.Exe_sql_nfield_1row(sql, 2);
		
		if(list != null && list.size() > 0){
			
			
			_status_work 	= this.empsn_NS;
			try{
				_bear_date_b	= (Date) list.get(0);
			}catch (Exception e) {
				_bear_date_b	= new Date();
			}
			try{
				_n_bear 		= Integer.valueOf(list.get(1).toString());
			}catch (Exception e) {
				_n_bear = 0;
			}
		}
		
	}
	//_______________________________________________________________________
	//	Function Utility for Empsn
		
		
	
		public List<String> GetListEmpsnByDept(String dept){

			List<String> listEmpsn = new ArrayList<String>();
			String sql = 
						"select e.empsn from n_employee e,n_department d\n" +
						"where e.depsn = d.id_dept\n" + 
						"and d.id_dept ='"+dept+"'";

			listEmpsn	= obj_util.Exe_Sql_String(sql);
			return listEmpsn;
			
		}
		
		
		public Date Get_Date_Labor_First(String empsn){
			Date date = null;
			String sql = " SELECT t.date_s,t.* FROM n_labour t " +
						 " WHERE t.empsn = '" +empsn+ "' " +
						 " AND t.times = 1" ;
			
				date = (Date) obj_util.Exe_Sql_Obj(sql);
			
			return date;
		}
		
		public Long Get_Salary_Total_By_Month(String empsn,Date date_input){

			long rs = 0;
			String str_date = sf.format(date_input);
			
			String month 	=  str_date.substring(3, 5);
			String year 	=  str_date.substring(6,10);
			Connection con			=	Application.getApp().getConnection();
			String sql 				= "{call C5_CALCULATE_SALARY_N_YT(?,?,?,?)}";
			
			try{
				CallableStatement stm   = con.prepareCall(sql); 
				stm.setString(1, month);
				stm.setString(2, year);
				stm.setString(3, empsn);
		
				stm.registerOutParameter(4, Types.INTEGER);
				
				stm.execute();
				rs=Long.valueOf(stm.getInt(4));
				
				
			}catch (Exception e) {
				System.out.println(e);
			}
			finally{
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return rs;
		}
		
		public long Get_Salary_Total_By_Month_for_NV(String empsn,Date date_input) {
			SimpleDateFormat sf_ = new SimpleDateFormat("yyyy-MM-dd");
			java.sql.Date date_para_sql = null ;
			Date date_dot_tv = null ;
			long rs = 0;
			String str_date =  sf.format(date_input);
			
			
			date_dot_tv		=  Get_Dot_tv(empsn, date_input);
			
			try{
				date_para_sql	=  java.sql.Date.valueOf(sf_.format(date_dot_tv));
			}catch (Exception e) {
				System.out.println("So the " + empsn +" Khong co date_dot_tv");
				
			}
			
			String month 	=  str_date.substring(3, 5);
			String year 	=  str_date.substring(6,10);
			Connection con			=	Application.getApp().getConnection();
			String sql 				= "{call C5_CALCULATE_SALARY_OFF_YT(?,?,?,?,?)}";
			
			try{
				CallableStatement stm   = con.prepareCall(sql); 
				stm.setString(1, month);
				stm.setString(2, year);
				stm.setString(3, empsn);
				stm.setDate(4, date_para_sql);
				stm.registerOutParameter(5, Types.INTEGER);
				
				stm.execute();
				rs=Long.valueOf(stm.getInt(5));
				
				
			}catch (SQLException e) {
				System.out.println(e);
			}
			finally{
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return rs;
		}
		
					
		public long Get_Salary_Basic(String empsn,Date date_input){
			Calendar ca = Calendar.getInstance();
			ca.setTime(date_input);
			String day 		=  String.valueOf(ca.getMaximum(Calendar.DAY_OF_MONTH));
			String date_str = "" ;
			Date date = ObjUtility.MONTH_NOW(day,date_input);
			date_str = sf.format(date);
			
			
			String sql		 ="";
			
			long salary = 0;
			
			

			sql =
					"SELECT t.bsalary\n" +
					"FROM n_basic_salary t\n" + 
					"WHERE\n" + 
					"         t.empsn ='"+empsn+"'\n" + 
					"     AND t.dates < to_Date('"+date_str+"','dd/mm/yyyy')\n" + 
					"     AND t.dates>=(Select Max(tt.dates)\n" + 
					"                          From n_basic_salary tt\n" + 
					"                          Where tt.empsn=t.empsn\n" + 
					"                          And  tt.dates<to_Date('"+date_str+"','dd/mm/yyyy')\n" + 
					"                   )";

			
			Object obj = new Object();
			
				obj = obj_util.Exe_Sql_Obj(sql);
						
				if(obj == null){
					salary = 0;
					System.out.println("Not Salary!");
				}
				else{
					try{
						salary = Long.valueOf(obj.toString());
					}catch (Exception e) {
						salary = 0;
					}
				}
			
			return salary;
			
		}
		
		/**
		 * Because manage id_dept of empsn is not clear
		 * So will return id_dept in n_employee
		 * this is may be not exact when have change dept
		 * 
		 * @param empsn
		 * @return
		 */
		
		public String getDept(String empsn) {
			String id_dept = "";
			IGenericDAO<N_EMPLOYEE, String> objDao = Application.getApp().getDao(N_EMPLOYEE.class);
			N_EMPLOYEE objData = objDao.findById(empsn);

			if(objData != null){
				id_dept	= objData.getDEPSN();
			}
			return id_dept;
		}

		
		
		public Date Get_Dot_tv(String empsn,Date date_input){
			Date date = null;
			String str_date = sf.format(date_input);
			
			String month 	=  str_date.substring(3, 5);
			String year 	=  str_date.substring(6,10);
			String sql = " SELECT t.dot_tv FROM n_emp_quit t " +
						 " WHERE t.empsn = '" +empsn+ "' " +
						 " AND to_char(t.thang_trubh,'mm/yyyy')='"+month+"/"+year+"'" ;
			
				try {
					System.out.println(obj_util.Exe_Sql_Obj(sql));
					date = sf.parse(sf.format(obj_util.Exe_Sql_Obj(sql)));
				} catch (ParseException e) {
					System.out.println(date);
					e.printStackTrace();
				}
			
			return date;
		}
	//////////////////////////
}
