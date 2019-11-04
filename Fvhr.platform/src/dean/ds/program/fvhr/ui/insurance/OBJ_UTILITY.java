package ds.program.fvhr.ui.insurance;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.list.ListModel;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jfree.data.time.Month;

import fv.util.Vni2Uni;
import ds.program.fvhr.dao.insuranse.ExecuteInputActionDaily;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_GET_DATA;
import ds.program.fvhr.domain.N_N_JOB;
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
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class OBJ_UTILITY {
	
	private Connection con = null;
	
	public OBJ_UTILITY() {
	
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
	
	public  String AutoGenKey(){
		String key = ""; // ID_user_ddMMyy
		String keyAuto ="";
		int num = 0;
		int get_num = 0;
		SimpleDateFormat sf = new SimpleDateFormat("ddMMyy");
		Calendar ca = Calendar.getInstance();
		key = Application.getApp().getLoginInfo().getUserID();
		key = key +"_"+ sf.format(ca.getTime());
		
		IGenericDAO<N_N_JOB, String> obj_dao 	= Application.getApp().getDao(N_N_JOB.class);
		DetachedCriteria obj_dc 				= DetachedCriteria.forClass(N_N_JOB.class);
		obj_dc.addOrder(Order.desc("ID_JOB"));
		
		obj_dc.add(Restrictions.like("ID_JOB","%"+key));
		
		List<N_N_JOB> list = obj_dao.findByCriteria(1, obj_dc);
		
		if(list	!=	null){
			
			for(N_N_JOB obj : list){
				
				get_num = Integer.valueOf(obj.getID_JOB().replace(key, "").trim());
				if(get_num > num){
					num = get_num;
				}
				
			}
			
		}else{
			num = 0;
		}
		
		num = num + 1;
		
		key = String.valueOf(num) + key;
		
		
		System.out.println(key);
		
		return key;
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
	
	public static Date MONTH_NOW(String day,Date date_input){
		SimpleDateFormat sf_ = new SimpleDateFormat("dd/MM/yyyy"); 
		String temp_str = sf_.format(date_input);
		
		String  month 	= temp_str.substring(3, 5);
		String year 	= temp_str.substring(6,10);
		Date date 		= null;
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
		
		return ca.getTime();
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
		ca.roll(Calendar.MONTH, false);
		
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
		ca.roll(Calendar.MONTH, false);
		
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
		ca.roll(Calendar.MONTH, true);
		
		return ca.getTime();
	}
	public static Date MONTH_NEXT(String day,Date date_input){
		Calendar ca = Calendar.getInstance();
		
		ca.setTime(date_input);
		ca.roll(Calendar.MONTH, true);
		
		return ca.getTime();
	}
	
	
	public int GET_DAY_OF_MONTH_NOT_SUN(Date date_input){
		int month 	= date_input.getMonth() + 1;
		int year	= date_input.getYear();
		int rs=0;
		int date=0;
		Calendar ca = Calendar.getInstance();
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
		
		for(int i = 1; i <= date ; i ++){
			ca.set(year, month-1, i);
			
			if(ca.getTime().getDay() == 0){
				rs = rs + 1;
			}
			
		}
		
		return (date-rs);

	}
	
//__________________ List Model
	
	public ListModel Get_Model_Dept(String index1,String index2) {

		DefaultListModel model = new DefaultListModel();
		String sql = "";
		
			sql = " select distinct t.name_dept_name" +
					 " from n_department t " +
					 " where t.name_fact = '" +index1+ "' "+
					 " and t.name_group = '"+index2+"'";
		
		
		
		
		List<String> list = null;
		
		try {
			list = Exe_Sql_String(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.add("");
		for(String str: list){
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
		
		try {
			list = Exe_Sql_String(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		try {
			
			List<String> list = Exe_Sql_String(sql);
			model.add(" ");
			for(String str : list){
				model.add(Vni2Uni.convertToUnicode(str));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return model;
	}
	
	
	
	
//__________________________________________________________________
	
	
//___________________ MAPPING EDITOR 
	
	
	private MappingPropertyEditor factEditor(){
		String sql =  " select distinct t.name_fact from n_department t ";
		List<String> list = null;
		MappingPropertyEditor e = new MappingPropertyEditor();
		try {
			list = Exe_Sql_String(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(String str : list){
			e.put(str,str);
		}
		
		return e;
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
		try {
			list = this.Exe_Sql_String(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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
	
	public ArrayList<Object[]> Exe_Sql_nfield_nrow(String sql,int nfield) throws SQLException{
		
		OpenConSql();
		
		Statement stm 					= null;
		ResultSet rs 					= null;
		ArrayList<Object[]> list_arr 	= new ArrayList<Object[]>();					// 1 list cac obj[];
		Object[] obj_arr 				; // obj[] = 1 mang cac field(empsn,name,depsn...)
		int ii = 0;
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
			
		
				CloseConSql();
				stm.close();
				rs.close();
		
		
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
	public List<String> Exe_Sql_String(String sql) throws SQLException{
		OpenConSql();
		Statement stm			= 	null;
		ResultSet rs			= 	null;
		ArrayList<String> list 	= new ArrayList<String>()	;
	
		
			stm 	= con.createStatement();
			rs 		= stm.executeQuery(sql);
		if(rs!=null){
			while(rs.next()){
					list.add(rs.getString(1));
			}
		}else{
			System.out.println("There is not data return!");
		}//System.out.println("Close Connection");
		
			CloseConSql();
			stm.close();
			rs.close();
		
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
	
	
	public List GenericDao(Object obj){
		return null;
		
	}
	
//_________________________________________________________________
	
	
	
//________________ Show Message 
	
	public void ShowMessageOK(String message){
		Application.getApp().showMessageDialog(MessageDialog.TYPE_INFORMATION + MessageDialog.CONTROLS_OK, message);
	}
	public void ShowMessageError(String message){
		Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK, message);
	}
	public void ShowMessageInfo(String message){
		Application.getApp().showMessageDialog(MessageDialog.TYPE_INFORMATION + MessageDialog.CONTROLS_OK, message);
	}
	
//__________________________________________________________________
	
	
//________________ Report 
	
	private int	execExportType = 1;
	private String taskIDClass ="" ;
		
		public void doExport(String taskIDClass){
			this.taskIDClass = taskIDClass;
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
			reportWrapper.setReportFormat("temp.xls");
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


	
//____________________________________________________________________________________________
	
	
	
	
	
	
	
	
	
	
	/*xBinder = new ListBaseBinder(null, cb_xuong, factEditor());
	xBinder.objectToComponent("");
	cb_xuong.addActionListener(new ActionListener() {
		
		@Override
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
			
			
			
			
//_______________________________________________END NGAN______________
			
				 
//---------ANH COPY cua NGAN mang vao--------------------------------------
			public int Exe_Update_Sql(String sql) {
	            OpenConSql();
	            Statement stm           =     null;
	            int rs                  =     0;
	      
	            try{
	                        stm   = con.createStatement();
	                        rs    = stm.executeUpdate(sql);
	                        
	                        stm.close();	                        
	                              
	            }catch (Exception e) {
	                  
	            }
	            finally{
	                  CloseConSql();
	            }
	            return rs;
	      }
			
//---------HET COPY CUA NGAN ----------------------------------------------
			public void InputActionDaily(String userId, String tableName, String action, String empsn, String note) throws Exception{
				OpenConSql();
				String sql ="{call N_HB_ACTION_DAILY(?,?,?,?,?)}";
				CallableStatement cstmt = con.prepareCall(sql);
				cstmt.setString(1, userId);
				cstmt.setString(2, tableName);
				cstmt.setString(3, action);
				cstmt.setString(4, empsn);
				cstmt.setString(5, note);
/*				
				ResultSet rs =cstmt.executeQuery();
				while(rs.next()){
					System.out.println(rs.getString("aaa"));
				}				
*/				
				try{
					cstmt.execute();
					cstmt.close();
					CloseConSql();
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}	
			}
			
			public void InsertLostDataDetail(String empsn, String ngayMatDuLieu, String gioVao, String gioGiua, String gioRa, String tangCa, String lanKy, String lyDo, String userId) throws Exception{
				OpenConSql();
				String sql ="{call TT_insert_lost_data_detail(?,?,?,?,?,?,?,?,?)}";
				CallableStatement cstmt = con.prepareCall(sql);
				cstmt.setString(1, empsn);
				cstmt.setString(2, ngayMatDuLieu);
				cstmt.setString(3, gioVao);
				cstmt.setString(4, gioGiua);
				cstmt.setString(5, gioRa);
				cstmt.setString(6, tangCa);
				cstmt.setString(7, lanKy);
				cstmt.setString(8, lyDo);
				cstmt.setString(9, userId);
/*				
				ResultSet rs =cstmt.executeQuery();
				while(rs.next()){
					System.out.println(rs.getString("aaa"));
				}
*/
				try{
					cstmt.execute();
					cstmt.close();
					CloseConSql();
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}	
			}
			
			public void UpdateLostDataDetail(String empsn, String ngayMatDuLieu, String gioVao, String gioGiua, String gioRa, String tangCa, String lanKy, String lyDo, String userId) throws Exception{
				OpenConSql();
				String sql ="{call TT_update_lost_data_detail(?,?,?,?,?,?,?,?,?)}";
				CallableStatement cstmt = con.prepareCall(sql);
				cstmt.setString(1, empsn);
				cstmt.setString(2, ngayMatDuLieu);
				cstmt.setString(3, gioVao);
				cstmt.setString(4, gioGiua);
				cstmt.setString(5, gioRa);
				cstmt.setString(6, tangCa);
				cstmt.setString(7, lanKy);
				cstmt.setString(8, lyDo);
				cstmt.setString(9, userId);				
/*
 				ResultSet rs =cstmt.executeQuery();
				while(rs.next()){
					System.out.println(rs.getString("aaa"));
				}
*/				
				try{
					cstmt.execute();
					cstmt.close();
					CloseConSql();
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}	
			}
			
			public String findNameEmployee(String empsn) {
				String kq;
				IGenericDAO<N_EMPLOYEE, String> dao = Application.getApp().getDao(N_EMPLOYEE.class);
				N_EMPLOYEE emp = dao.findById(empsn);
				if (emp!=null){
					kq=(Vni2Uni.convertToUnicode(emp.getFNAME() + " " + emp.getLNAME()));					
				}else{
					kq="";					
				}
				return kq;
			}			
			
			public Integer KiemTraKhoaDuLieu(String thang, String nam, String soThe) {
				Integer check =0;
				InsuranceDAO ins = new InsuranceDAO();
				// =1 la hop le, =0 la ko hop le
				// kiem tra bang luong
			    check = Integer.parseInt(ins.GetField("count(*)", "ATTLOCK", "tabname", "tablock", null, "ATT"+nam+thang, "Y", null));
			    if (check==1){
			    	return check;
			    }
			    else{			    
			    	check = Integer.parseInt(ins.GetField("count(*)", "N_GET_DATA", "locked=1 and empsn", "months", "years", soThe, thang, nam));			    
			    	return check;
			    }
			}			
			
// HET ANH-----------------------------------------------------------------		 

}
