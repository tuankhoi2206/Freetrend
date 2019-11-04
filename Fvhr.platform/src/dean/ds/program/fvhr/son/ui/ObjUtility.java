package ds.program.fvhr.son.ui;

import java.beans.PropertyEditor;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.list.ListModel;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import fv.util.JdbcDAO;
import fv.util.Vni2Uni;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportService;
import dsc.echo2app.program.TaskExecuter;
import dsc.echo2app.program.TaskExecuterHelper;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.task.ATask;
import dsc.echo2app.task.ReportParameterWrapper;
import dsc.echo2app.task.TaskInfoWrapper;
import dsc.echo2app.task.TaskResultWrapper;

public class ObjUtility {
	
	
	private Connection con = null;
	public ObjUtility() {
	
	}
	
	/**
	 * return true if has special character and else is false;
	 * @param str
	 * @return
	 */
	public static boolean CheckSpecialCharacter(String str){
		
		boolean rsCheck = false;
		char[] arrStr = str.toCharArray();
		int temp;
		
		for(char a : arrStr){
			if(rsCheck){
				continue;
			}
			temp	= (int)a;
			
				// 0-9								A-Z							a-z
			if((temp >=48 && temp <= 57) ||  (temp >= 65 && temp <= 90) || (temp >=97 && temp <= 122) || temp == 45 || temp == 95   ){
				rsCheck = false;
			}else{
				
				rsCheck = true;
			}
			
		}
		return rsCheck;
	}
	
	
	public static SimpleDateFormat Get_format_date(){
		return new SimpleDateFormat("dd/MM/yyyy");
	}
	
	/**
	 * 	
	 * @return String key = USER_ddmmyy_hhmmss
	 */
	
	private void OpenConSql(){
		try {
			if(con == null || con.isClosed()){
				con = Application.getApp().getConnection();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void CloseConSql(){
		try {
			if(con != null || !con.isClosed()){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//////////////// CUSTOM DATE
	
	public static String DATE_FORMAT_STR = "dd/MM/yyyy";
	
	public static Date DateFormat_DDMMYYYY(Date date_in){
		Date date_out 	= null;
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			date_out	= sf.parse(sf.format(date_in));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return date_out;
		
	}
	
	public static Date DateFormat_DDMMYYYY(String txt_date_in){
		Date date_out 	= null;
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		
		
		try {
			date_out	= sf.parse(txt_date_in);
		} catch (ParseException e) {
			
		}
		
		return date_out;
		
	}
	
	public static Date MONTH_NOW(String day,String month,String year){
		
		month = month.length() == 1? "0" + month : month;
		Date date = null ;
		Calendar ca = Calendar.getInstance();
		if(day.equals("")){
			day = "01";
		}
		String date_str =	day + "/" + month + "/" + year ;
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			
			date = sf.parse(date_str);
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		ca.setTime(date);
		//ca.roll(Calendar.MONTH, false);
		
		return ca.getTime();
	}
	
	public static Date MONTH_NOW(String day_,Date date_input_){
		SimpleDateFormat sf_ = new SimpleDateFormat("dd/MM/yyyy"); 
		String temp_str_ = sf_.format(date_input_);
		
		String  month_ 	= temp_str_.substring(3, 5);
		String year_ 	= temp_str_.substring(6,10);
		Date date_ 		= null;
		Calendar ca_ = Calendar.getInstance();
		ca_.setTime(date_input_);
		if(day_.equals("")){
			day_ = "01";
		}
		if(day_.equals("00")){
			day_ = String.valueOf(GET_NUM_DAY_OF_MONTH(date_input_));
		}
		
		String date_str =	day_ + "/" + month_ + "/" + year_ ;
		
		try {
			
			date_ = sf_.parse(date_str);
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		ca_.setTime(date_);
		
		return ca_.getTime();
	}
	public static Date MONTH_PRE(String day,String month,String year){
		Date date = null ;
		Calendar ca = Calendar.getInstance();
		if(day.equals("")){
			day = "01";
		}
		String date_str =	day + "/" + month + "/" + year ;
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			
			date = sf.parse(date_str);
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		ca.setTime(date);
		ca.add(Calendar.MONTH, -1);
		
		return ca.getTime();
	}
	public static Date MONTH_PRE(String day,Date date_input){
		SimpleDateFormat sf_ = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null ;
		String temp_str	= sf_.format(date_input);
		String month 	= temp_str.substring(3,5);
		String year 	= temp_str.substring(6,10);
		Calendar ca = Calendar.getInstance();
		if(day.equals("")){
			day = "01";
		}
		String date_str =	day + "/" + month + "/" + year ;
		
		try {
			
			date = sf_.parse(date_str);
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		ca.setTime(date);
		ca.add(Calendar.MONTH, -1);
		
		return ca.getTime();
	}
	public static Date MONTH_NEXT(String day,String month,String year){
		Date date = null ;
		Calendar ca = Calendar.getInstance();
		if(day.equals("")){
			day = "01";
		}
		String date_str =	day + "/" + month + "/" + year ;
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			
			date = sf.parse(date_str);
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		ca.setTime(date);
		ca.add(Calendar.MONTH, 1);
		
		return ca.getTime();
	}
	public static Date MONTH_NEXT(String day,Date date_input){
		SimpleDateFormat sf_ = new SimpleDateFormat("dd/MM/yyyy");
		Calendar ca = Calendar.getInstance();
		Date date = null ;
		if(day.equals("")){
			day = "01";
		}
		String temp_str	= sf_.format(date_input);
		String month 	= temp_str.substring(3,5);
		String year 	= temp_str.substring(6,10);
		String date_str =	day + "/" + month + "/" + year ;
		
		try {
			
			date = sf_.parse(date_str);
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		ca.setTime(date);
		ca.add(Calendar.MONTH, 1);
		
		return ca.getTime();
	}
	/**
	 * Tien toi hoac quay lui(true/false) n_roll thang cua date_input
	 * @param date_input : ngay thang can thay doi -- flag_ : tien/lui -- num_roll_ : so thang can thay doi
	 * @return 
	 */
	public static Date MONTH_ROLL(Date date_input_, int num_roll_){
		SimpleDateFormat sf_ = new SimpleDateFormat("dd/MM/yyyy");
		Calendar ca = Calendar.getInstance();
		ca.setTime(date_input_);
		//for (int i = 1; i <= num_roll_; i++) {
			ca.add(Calendar.MONTH, num_roll_);
		//}
		System.out.println("Roll from : "+sf_.format(date_input_) + " to : " + sf_.format(ca.getTime()));
		return ca.getTime();
		
	}
	
	public static Date DAY_NEXT(Date date_input){
		Calendar 	ca = Calendar.getInstance();
					ca.setTime(date_input);
					ca.add(Calendar.DATE, 1);
		return ca.getTime();
	}
	
	
	public static int GET_NUM_DAY_OF_MONTH(Date date_input_){

		SimpleDateFormat sf_ = new SimpleDateFormat("dd/MM/yyyy");
		String str_date = sf_.format(date_input_);
		int month 	= Integer.valueOf(str_date.substring(3, 5));
		int year	= Integer.valueOf(str_date.substring(6, 10));
		int date=0;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			date = 31;
			break;
		case 4:
		case 6:
		case 9 :
		case 11:
			date = 30;
			break;
		case 2 :
			 if ( ((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0) )
                    date = 29;
                else
                    date = 28;
                break;

		default:
			date =0;
			break;
		}
		
		return (date);

	
	}
	
	public static int Distance_two_month(Date date1,Date date2){
		int rs = 0;
		
		
		Calendar	ca1 = Calendar.getInstance();
		Calendar 	ca2	= Calendar.getInstance();
		
		if(date1.compareTo(date2) < 0){
		
			ca1.setTime(date1);
			ca2.setTime(date2);
		}else{
			ca1.setTime(date2);
			ca2.setTime(date1);

		}
		int i = 0;
		while(ca1.compareTo(ca2) != 0){
			
			ca1.add(Calendar.MONTH, 1);
			i++;
			
		}
		rs = i;
		return rs;
	}
	
	
	public static int GET_NUM_DAY_OF_MONTH_NOT_SUN(Date date_input_){
		SimpleDateFormat sf_ = new SimpleDateFormat("dd/MM/yyyy");
		String str_date = sf_.format(date_input_);
		int month 	= Integer.valueOf(str_date.substring(3, 5));
		int year	= Integer.valueOf(str_date.substring(6, 10));
		int rs=0;
		int date=GET_NUM_DAY_OF_MONTH(date_input_)	;
		Calendar ca = Calendar.getInstance();
		
		for(int i = 1; i <= date ; i ++){
			ca.set(year, month-1, i);
			if(ca.getTime().getDay() == 0){
				rs = rs + 1;
			}
			
		}
		
		return (date-rs);

	}
	
	
	public static long Round_Salary(long a){
		
		long rs;
		long	 b = (long) (((float)a/1000 - (int)(a/1000))*1000);
		long     c = (long) (a/1000);
		
		rs = c *1000 + (long)(Math.round((float)b/100)*100);
			return rs;
	}	

	/**
	 * return Date list which haven't Sunday day 
	 * @param fromD
	 * @param toD
	 * @return
	 */
	
	public List<Date> getListDate(Date fromD,Date toD){
		ArrayList<Date> listDate = new ArrayList<Date>();
		Calendar	caTemp	= Calendar.getInstance();
		Calendar 	caFr 	= Calendar.getInstance();
		Calendar	caTo	= Calendar.getInstance();
		
		
		
		caFr.setTime(fromD);
		caTo.setTime(toD);
		SimpleDateFormat sf_ = new SimpleDateFormat("dd/MM/yyyy");
		
		if(caFr.after(caTo)){
			caTemp	= caTo;
			caTo	= caFr;
			caFr	= caTemp;
		}
		
		while(caFr.getTime().before(caTo.getTime()) 
				|| sf_.format(caFr.getTime()).equals(sf_.format(caTo.getTime()))){
			if(caFr.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
				listDate.add(caFr.getTime());
			}
			caFr.add(Calendar.DATE, 1);
			
		}
		
		return listDate;
	}
	
	/**
	 * 
	 * @param cond
	 * @param para
	 * @return
	 */
	//DANG LAM CAI NAY
	public List<String> Get_Data_By_Date(Object[] field_select,Object[] cond_where,Object[] para_){
		ArrayList<String> rs_list = new ArrayList<String>();
		
		String sql = 

			"select t.empsn,t.date_b,t.id_job , j.name,j.kind,j.money\n" +
			"from n_emp_job_detail t\n" + 
			"     ,n_n_job j\n" + 
			"where t.empsn ='04101074'\n" + 
			"and t.id_job  = j.id_job\n" + 
			"and t.date_b  = (\n" + 
			"                  select max(tt.date_b) from n_emp_job_detail tt\n" + 
			"                  where tt.empsn = t.empsn\n" + 
			"                  and tt.date_b <= to_date('07/01/2011','dd/mm/yyyy')\n" + 
			"                 )";
		
		
		return rs_list;
	}
	
	
	

//______________________________________________________________________________________
//__________________ 		LIST MODEL 		___________________________________________
//_____________________________________________________________________________________
	
	
	public ListModel Get_Model_Dept(String n_fact,String n_group) {

		DefaultListModel model = new DefaultListModel();
		String sql = "";
		
			sql = " select distinct t.name_dept_name" +
					 " from n_department t " +
					 " where t.name_fact = '" +n_fact+ "' "+
					 " and t.name_group = '"+n_group+"'";
		
		List<String> list = null;
		
			list = Exe_Sql_String(sql);
		model.add("");
		for(String str: list){
			if(str!=null)
				model.add(Vni2Uni.convertToUnicode(str));
		}
		
		return model;
	}
	
	public ListModel Get_Model_Dept() {

		DefaultListModel model = new DefaultListModel();
		
		MappingPropertyEditor map_editor = this.Get_MapEditor_DEPSN_NAME();
		
		model.add("");
		for(String str: map_editor.getDisplays()){
			if(str!=null)
				model.add(Vni2Uni.convertToUnicode(str));
		}
		
		return model;
	}
	
	public DefaultListModel Get_Model_Group(String indexItem) {
		// TODO Auto-generated method stub
		DefaultListModel model = new DefaultListModel();
		String sql = " select distinct t.name_group" + 
					 " from n_department t " +
					 " where t.name_fact = '" +indexItem+ "' ";
		
		List<String> list = null;
		
			list = Exe_Sql_String(sql);
		
		model.add(" ");
		if(list != null && list.size() > 0){
			for(String str: list){
				if(str!=null)
		
					model.add(Vni2Uni.convertToUnicode(str));
			}
		}
		return model;
		
	}
	
	public ListModel Get_Model_Fact() {
		
		DefaultListModel model = new DefaultListModel();
		String sql = "select distinct t.name_fact from n_department t";
		// 
		
			
			List<String> list = Exe_Sql_String(sql);
			model.add(" ");
			for(String str : list){
				model.add(Vni2Uni.convertToUnicode(str));
			}
			
		return model;
	}
	
	
	
	
//__________________________________________________________________________________________
//___________________ 		MAPPING EDITOR		____________________________________________
//__________________________________________________________________________________________
	
	public PropertyEditor Get_MapEditor_POSS_NAME() {
		
		MappingPropertyEditor editor_	= new MappingPropertyEditor();
		editor_.put("", "");
		String id__ 	 = "";
		String name__	 = "";
		String level__	 = "";

		String sql = 
			" Select t.id_key,t.name_poss,t.level_poss\n" +
			" From n_poss t\n" + 
			" Order by t.id_key";

		List<Object[]> list_arr_ = this.Exe_Sql_nfield_nrow(sql, 3);
		if(list_arr_ != null){
			for(Object[] arr : list_arr_){
				
				id__ 		= arr[0]==null ?"":arr[0].toString();
				name__		= arr[1]==null ?"":arr[1].toString();
				level__		= arr[2]==null ?"":arr[2].toString();
				
				name__  = Vni2Uni.convertToUnicode(name__ + "-" + level__ );
				editor_.put(name__, id__);
			
			}
		}
		
		return editor_;
	}
	
	public MappingPropertyEditor Get_MapEditor_JOB_NAME(){
		MappingPropertyEditor editor_	= new MappingPropertyEditor();
		editor_.put("", "");
		String id__ 	 = "";
		String name__	 = "";
		String kind__	 = "";
		String in_fact__ = "";
		String sql = 

			"SELECT\n" +
			"      j.id_key,j.name_job,j.kind_job,j.in_fact \n" + 
			"FROM n_job j order by j.id_key ";


		List<Object[]> list_arr_ = this.Exe_Sql_nfield_nrow(sql, 4);
		if(list_arr_ != null){
			for(Object[] arr : list_arr_){
				
				id__ 		= arr[0]==null ?"":arr[0].toString();
				name__		= arr[1]==null ?"":arr[1].toString();
				kind__		= arr[2]==null ?"":arr[2].toString();
				in_fact__	= arr[3]==null ?"":arr[3].toString();
				
				name__  = Vni2Uni.convertToUnicode(name__ + "-" + kind__ + "-" + in_fact__);
				editor_.put(name__, id__);
			
			}
		}
		
		return editor_;
	}
	
	public MappingPropertyEditor Get_MapEditor_DEPSN_NAME(){
		
		MappingPropertyEditor editor	= new MappingPropertyEditor();
		String id	= "";
		String name	= "";
		String sql = 
			"SELECT\n" +
			"      d.id_dept,d.name_dept\n" + 
			"FROM n_department d";

		List<Object[]> list_arr = this.Exe_Sql_nfield_nrow(sql, 2);
		if(list_arr != null){
			for(Object[] arr : list_arr){
				id 	= arr[0]==null?"":arr[0].toString();
				name	= arr[1]==null?"":Vni2Uni.convertToUnicode(arr[1].toString());
				editor.put(name, id);
			}
		}
		return editor;
		
	}
	
	public MappingPropertyEditor Get_MapEditor_EMPSN_NAME(){
		MappingPropertyEditor editor_ = new MappingPropertyEditor();
		String fname_ = "";
		String lname_ = "";	
		String name_  = "";	
		String empsn_ = "";
		String sql = 
			" SELECT\n" +
			"      e.empsn,e.fname,e.lname\n" + 
			" FROM n_employee e " +
			" WHERE e.depsn <> '00000'";  // tam thoi cho load du lieu nhanh
		List<Object[]> list_arr_ = this.Exe_Sql_nfield_nrow(sql, 3);
		if(list_arr_ != null){
			for(Object[] arr_ : list_arr_){
				empsn_ 	= arr_[0] == null ? "" : arr_[0].toString();
				fname_	= arr_[1] == null ? "" : arr_[1].toString() ;
				lname_  = arr_[2] == null ? "" : arr_[2].toString();
				name_	= Vni2Uni.convertToUnicode(fname_ + " " + lname_);
				editor_.put(name_, empsn_);
			}
		}
		return editor_;
	}
	

	
	public MappingPropertyEditor Get_Editor_KIND(){
		MappingPropertyEditor editor = new MappingPropertyEditor();
		editor.put("", "");
		editor.put("A","A");
		editor.put("B","B");
		editor.put("C","C");
		editor.put("D","D");
		editor.put("E","E");
		editor.put("F","F");
		
		return editor;
	}
	
	public MappingPropertyEditor Get_Editor_ID_POSS(){
		MappingPropertyEditor editor = new MappingPropertyEditor();
		String sql  = " select distinct t.id_poss from n_n_poss t";
		
		List<String> list = null;
			list = this.Exe_Sql_String(sql);
		editor.put("","");
		if(list!=null){
			for(String str : list){
				editor.put(str,str);
			}
		}
		return editor;
	}
	
	public MappingPropertyEditor Get_Editor_Level(){
		MappingPropertyEditor editor = new MappingPropertyEditor();
		editor.put("", "");
		editor.put("1","1");
		editor.put("2","2");
		editor.put("3","3");
		editor.put("4","4");
		editor.put("5","5");
		editor.put("6","6");
		editor.put("7","7");
		editor.put("8","8");
		editor.put("9","9");
		editor.put("10","10");
		editor.put("0","0");
		return editor;
	}
	
	// ===========================================
	
	
	// ================== SQL DAO
	/**
	 * Select n column -> return n row
	 * 
	 * @param sql
	 * @param nfield
	 * @return
	 * @throws SQLException
	 */
	
	public ArrayList<Object[]> Exe_Sql_nfield_nrow(String sql,int nfield) {
		
		OpenConSql();
		
		Statement stm 					= null;
		ResultSet rs 					= null;
		ArrayList<Object[]> list_arr 	= new ArrayList<Object[]>();					// 1 list cac obj[];
		Object[] obj_arr 				; // obj[] = 1 mang cac field(empsn,name,depsn...)
		int ii = 0;
			try {
				stm = con.createStatement();
			
				rs	= stm.executeQuery(sql);
			
					if(rs!=null){
						while(rs.next()){
							obj_arr 	= new Object[nfield]; 
							for (int i = 0; i < nfield; i++) {
								if(rs.getObject(i+1) == null){
									obj_arr[i]	= "0";
								}else{
									obj_arr[i] = rs.getObject(i+1);
								}
							}
							if(obj_arr != null){
								list_arr.add(obj_arr);
							}
						}
					}else{
						System.out.println("There is not data return");
					}
						stm.close();
						rs.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
				finally{
					CloseConSql();
				}
		return list_arr;
	}
	
	public Object Exe_Sql_Obj(String sql) {
		OpenConSql();
		Statement stm	= null;
		ResultSet rs	= null;
		Object obj 		= new Object();
		
		try {
			stm = con.createStatement();
			rs	= stm.executeQuery(sql);
			if(rs != null){
				while(rs.next()){
					obj = rs.getObject(1);
				}
			}
		
			stm.close();
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			CloseConSql();
		}
		return obj;
	}
	
	
	/**
	 * Select 1 column -> return n row
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	
	
	
	public List<String> Exe_Sql_String(String sql) {
		OpenConSql();
		Statement stm			= 	null;
		ResultSet rs			= 	null;
		ArrayList<String> list 	= new ArrayList<String>()	;
	
		try{
				stm 	= con.createStatement();
				rs 		= stm.executeQuery(sql);
				if(rs!=null){
					while(rs.next()){
						
						
							list.add(rs.getString(1));
					}
					
					
				}else{
					System.out.println("There is not data return!");
				}
					
					//System.out.println("Close Connection");
				
					
					stm.close();
					rs.close();
					
		}catch (Exception e) {
			
		}
		finally{
			CloseConSql();
		}
		return list;
			
			
	}
	
	/**
	 * Select n column -> return 1 row
	 * @param obj
	 * @return
	 * @throws SQLException 
	 */
	public ArrayList<Object> Exe_sql_nfield_1row(String sql,int nfield) {
		ArrayList<Object> list = new ArrayList<Object>();
		OpenConSql();
		Statement stm			= 	null;
		ResultSet rs			= 	null;
	
		try {
					stm 	= con.createStatement();
					rs 		= stm.executeQuery(sql);
					
				if(rs!=null){
					while(rs.next()){
						for (int i = 1; i <= nfield; i++) {
							if(rs.getObject(i)!=null){
								list.add(rs.getObject(i));
							}else{
								list.add("");
							}
						}
					}
				}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally{
			CloseConSql();
		}
		return list;
	}
	
	public boolean Exe_update_sql(String sql){
		boolean rs = true;
		OpenConSql();
		Statement	stm;
		try{
			
			stm	= con.createStatement();
			stm.executeUpdate(sql);
			stm.close();
		}catch (Exception e) {
			rs = false;
		}
		finally{
			CloseConSql();
		}
		
		
		return rs;
	}
	
	public boolean Update_field(String name_table,String field_update,Object value_update,String field_cond,Object value_cond){
		boolean rs = true;
		OpenConSql();
		String sql = 	" Update " + name_table + 
						" Set " + field_update  + " = '" + value_update + "'" +
						" Where " + field_cond	+ " = '" + value_cond 	+ "'";
		Statement stm ;
		try {
			stm = con.createStatement();
			stm.execute(sql);
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rs = false;
		}
		finally{
			
			CloseConSql();
		}
		return rs;
	}
	
	
	
//_________________________________________________________________
	
	
	
//________________ Show Message 
	
	public static void ShowMessageOK(String message){
		Application.getApp().showMessageDialog(MessageDialog.TYPE_INFORMATION + MessageDialog.CONTROLS_OK, message);
	}
	public static void ShowMessageError(String message){
		Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK, message);
	}
	public static void ShowMessageInfo(String message){
		Application.getApp().showMessageDialog(MessageDialog.TYPE_INFORMATION + MessageDialog.CONTROLS_OK, message);
	}
	
	
	
//__________________________________________________________________
	
	
//________________ Report 
	
	private int	execExportType = 1;
	private String taskIDClass ="" ;
	private String _name_temp_field = "";	
		public void doExport(String taskIDClass,String name_temp_field_){
			this.taskIDClass 		= taskIDClass;
			this._name_temp_field	= name_temp_field_;
			if(_name_temp_field.equals("")){
				_name_temp_field = "temp";
			}
			TaskInfoWrapper taskInfoWrapper = new TaskInfoWrapper();
			prepareExportTaskParameter(taskInfoWrapper);
			export(10);
		}
		public void setExportExecuteType(int type) {
		      this.execExportType = type;
		}
		protected boolean export(int recNo) {     
	        TaskExecuter executer = new TaskExecuter(new TaskExecuterHelper() {
	           public void executeSuccessfully(TaskInfoWrapper taskInfoWrapper, TaskResultWrapper taskResultWrapper) {
	              Application.getApp().enqueueCommand(
	                     new BrowserRedirectCommand(getViewerUrl() + taskResultWrapper.getResult())
	              );
	              System.out.println("===============print success!");
	           }
	           public void executeUnsuccessfully(TaskInfoWrapper taskInfoWrapper, TaskResultWrapper taskResultWrapper) {
	              //setErrorMessage(getCommMsgRes().getString("Generic.MSG.ExecFailure") + taskResultWrapper.getResult());
	              System.out.println(taskResultWrapper.getResult());
	              System.out.println("===============print not success!");
	           }
	           public void prepareTaskParameter(TaskInfoWrapper taskInfoWrapper) {
	              prepareExportTaskParameter(taskInfoWrapper);
	           }
	        });
	        
	        ResourceBundle resourceBundle = null;
	        String err = "";
	        
	        	err = executer.execute(taskIDClass, "", "", execExportType, "", Application.getApp().getLoginInfo(), resourceBundle);
	
	        
	        if (err != null && err.length() > 0)  Application.getApp().showMessageDialog(1, err);
	        return null == err;
		}
		protected void prepareExportTaskParameter(TaskInfoWrapper wrapper){
			ReportParameterWrapper reportWrapper = new ReportParameterWrapper();
			reportWrapper.setReportFormat(_name_temp_field+".xls");
	  	  	reportWrapper.setReportType("excel");
		      reportWrapper.setViewerUrl(getViewerUrl());
		      wrapper.setCategory("R");// <--報表
		      setExportExecuteType(ATask.EXECTYPE_DIRECT);
		      wrapper.setParameter(reportWrapper.createParameterDescription());
		      wrapper.setTaskId(taskIDClass);
	    	  
	     
		}
		private String getViewerUrl() {
		      HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
		      String viewerUrl = request.getRequestURL() + "?" +
		      WebRenderServlet.SERVICE_ID_PARAMETER + "=" + ReportService.INSTANCE.getId() + "&" + 
		      ReportService.PARAM_TYPE + "=" + (execExportType == ATask.EXECTYPE_DIRECT ? ReportService.TYPE_TEMP : ReportService.TYPE_OUTPUT) + "&" +
		      ReportService.PARAM_KEY + "=";
		      return viewerUrl;
		}
		
		private boolean Write_Excel_by_Row(List<Object> list_info, HSSFSheet sheet0, int i_row){
			boolean rs = true;
			
			HSSFRow row;
			HSSFCell cell;
			
			row = sheet0.createRow(i_row);
			
			for (int j = 0; j < list_info.size(); j++) {
				cell = row.createCell(j);
				cell.setCellValue(list_info.get(j).toString());
			}
			
			return rs;
		
		}

	
//____________________________________________________________________________________________
	
	
	
	
	
	
	
	
	
	
	/*xBinder = new ListBaseBinder(null, cb_xuong, factEditor());
	xBinder.objectToComponent("");
	cb_xuong.addActionListener(new ActionListener() {
		

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			xBinder = new ListBaseBinder(null, cb_nhom, factEditor());
			xBinder.objectToComponent("1");
		}
	});
	*/
	
//____________NGAN ___
		 
			public boolean check_empsn(String empsn) {
				boolean flag = true;
				
				if(empsn.length() <8){
					flag = false;
				}
				
				if(!check_num_long(empsn)){
					flag = false;
				}
				
				return flag;
				
			}	 
				 
			public  boolean check_num_long(String str){
				boolean flag = true;
				long rs = 0;
				try{
					rs = Long.valueOf(str);
				}catch (Exception e) {
					// TODO: handle exception
					flag = false;
				}
				
				return flag;
			}
			
			public boolean check_num_double(String str){
				boolean flag = true;
				double rs = 0;
				try{
					rs = Double.valueOf(str);
				}catch (Exception e) {
					// TODO: handle exception
					flag = false;
				}
				return flag;
			}
			
			public boolean CheckRight(String user_login, String empsn_) {
				boolean rs = false;
				int get_rs = 0;
				IGenericDAO dao = Application.getApp().getDao("VFT");
				// user_login co 1 danh sach cac ma quan ly
				// empsn_ thuoc 1 ma quan ly
				// => 1 user_login co the quan ly nhiu ma -> nhiu so the (co ma khac nhau)
				// => 2 user_login co cung ma quan ly - > cung quan ly 1 so the nao do
				
				/**
				 * user_login 	-> DSPB02.getPB_USERNO
				 * 				-> List<MA_QL> FROM n_user_limit.MA_USER = DSPB02.PB_USERNO
				 * 					// list<a>
				 * empsn_		-> EMPSN.USER_MANAGE_ID (b)
				 * 
				 * if(b in list<a>) => true/false
				 */
				
				String sql = "select e.EMPSN from N_EMPLOYEE e where e.EMPSN = ? " +
												" and e.USER_MANAGE_ID in " +
														"(select ul.MA_QL from N_USER_LIMIT ul,DSPB02 pb02" +
														"				where ul.MA_USER = pb02.PB_USERNO" +
														"				and pb02.PB_USERID = ?)";
				
				List list = dao.find(1,sql, new Object[]{empsn_,user_login});
				if (list.size()>0){
					return true;
				}
				return false;
			}
			
//_______________________________________________END NGAN______________
				 
//_________________________________________________________________________________________
			
//_______________ GET DATA
			
			public String Get_Id_Job_By_Month(String empsn_,Date date_input_){
				String id_ = "";
				OpenConSql(); 
				String sql = "{?=call GET_ID_JOB_BY_MONTH(?,?)}";
				
				java.sql.Date date_sql_ = new java.sql.Date(date_input_.getTime());
				
				try {
					CallableStatement stm = con.prepareCall(sql);
					stm.registerOutParameter(1, Types.VARCHAR);
					stm.setString(2, empsn_);
					stm.setDate(3, date_sql_);
					
					stm.execute();
					
					id_ = stm.getString(1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return id_ ;
			}


			public BigDecimal Get_Money_Job_By_Month(String id_KEY, Date date_) {
				BigDecimal money_ = BigDecimal.ZERO;
				OpenConSql(); 
				String sql = "{?=call GET_JOB_MONEY_BY_MONTH(?,?)}";
				
				java.sql.Date date_sql_ = new java.sql.Date(date_.getTime());
				
				try {
					CallableStatement stm = con.prepareCall(sql);
					stm.registerOutParameter(1, Types.NUMERIC);
					stm.setString(2, id_KEY);
					stm.setDate(3, date_sql_);
					
					stm.execute();
					
					money_ = stm.getBigDecimal(1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return money_ ;
			}


			public String Get_Id_Dept_For_Empsn(String empsn_, Date date_input_) {
				String id_ = "";
				OpenConSql(); 
				String sql = "{?=call S_GET_DEPT_FOR_EMP(?,?)}";
				
				java.sql.Date date_sql_ = new java.sql.Date(date_input_.getTime());
				
				try {
					CallableStatement stm = con.prepareCall(sql);
					stm.registerOutParameter(1, Types.VARCHAR);
					stm.setString(2, empsn_);
					stm.setDate(3, date_sql_);
					
					stm.execute();
					
					id_ = stm.getString(1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return id_ ;
			}


			public ArrayList<String> getListEmpsnByIDDept(String IDdept, Date date) {
				
				String sql = 
								"select e.empsn from n_employee e\n" +
								"where        e.depsn     = '"+IDdept+"'" +
								" and e.depsn <> '00000' ";

				ArrayList<String> list_emp = (ArrayList<String>) this.Exe_Sql_String(sql);
				
				return list_emp;
			}


			public List<String> Get_List_All_Emp() {
				ArrayList<String> list_ = new ArrayList<String>();
				String sql = "select e.empsn from n_employee e where e.depsn <> '00000'";
				list_	= (ArrayList<String>) this.Exe_Sql_String(sql);
				return list_;
			}


			public String getIDdept(String nameDept,boolean isfullname){
				String sql = "" ;
				if(isfullname){
					sql = " select t.id_dept from n_department t " + 
					 " where t.name_dept = '" +nameDept+"'" ;
				}else{
					sql = " select t.id_dept from n_department t " + 
					 " where t.name_dept_name = '" +nameDept+"'" ;
				}
				
				return this.Exe_Sql_Obj(sql).toString();
			}
			
//_________________________________________________________________________________________			
		 
}
