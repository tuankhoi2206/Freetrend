package ds.program.fvhr.son.ui.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.baby.tools.DateTools;
import ds.program.fvhr.baby.tools.babyMath;
import ds.program.fvhr.son.ui.ExportAbstract;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.ReportFileManager;
import dsc.util.function.UUID;
import fv.util.DateUtils;

public class N_Emp_Job_WPExport extends ExportAbstract {



	public HSSFWorkbook data_excel_Performed() {


		HSSFWorkbook wb = null;
		File f = ReportFileManager.getReportFormatFolder("fvhr");
		InputStream in;
		try {
			in = new FileInputStream(new File(f.getPath(), "phan_cong_cong_viec.xls"));
			wb = new HSSFWorkbook(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	
		HSSFSheet s = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		
		String sql_call 		= "";
		String sql				= "";
		Connection	con 		= Application.getApp().getConnection();
		IGenericDAO dao 		= Application.getApp().getDao(Application.getApp().getLoginInfo().getCompanyID());
		CallableStatement stm 	;
		ObjUtility	obj_util	= new ObjUtility();
		List	list		= null;
		List	list1		= null;
		String id_job_		= "";
		String name_job_	= "";
		String note			= "";
		int r=0;
		SimpleDateFormat sf_ = new SimpleDateFormat(ObjUtility.DATE_FORMAT_STR);
		String fact 	= myDeptControl.getFact();
		String group = myDeptControl.getGroup();
		String name_depsn = myDeptControl.getNameDept();
		String date_str	="",date_str1 = "";
		Date date_now	= null;
		
		
		
		try {
			if(myDeptControl.getDate()!=null){
				date_str	= sf_.format(myDeptControl.getDate());
			}else{
				date_str	= sf_.format(new Date());
			}
			date_now 	= sf_.parse(date_str);
			Calendar cal = DateTools.ConvertDateToCalendar(date_now);
			date_str1 = sf_.format(DateUtils.getLastDay(cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR)));
		} catch (ParseException e) {
//			date_now 	= sf_.parse(sf_.format(new Date()));
			e.printStackTrace();
		}
		java.sql.Date date_sql = new java.sql.Date(date_now.getTime());
		sql	= 
				"select e.empsn from n_employee e,n_department d\n" +
				"where         e.depsn        = d.id_dept\n" + 
				"       and    d.name_fact    = '" +fact+"'" ;
			if(!group.equals("")){
				sql+=" d.name_group = '"+group+"'";
			}
			if(!name_depsn.equals("") )
			{
				sql+=" d.name_dept_name = '"+name_depsn+"'";
			}
				sql +=		" and e.empsn in (select distinct(e.empsn) from n_emp_job_detail e where e.date_b >= to_date('"+date_str+"','dd/mm/yyyy')" +
						" or (e.date_e is null and e.date_b <= to_date('"+date_str1+"','dd/mm/yyyy')))";
				
		try {
				List<String> list_emp = obj_util.Exe_Sql_String(sql);
				for ( int i=0; i< list_emp.size() ; i++ ){
					System.out.println(list_emp.size());
					System.out.println(i+1);
					String empsn = (String) list_emp.get(i);
					// STT(0) -- Ho ten (1) -- Xuong(2) -- Nhom (3) -- Don vi (4) -- Ten CV (5) -- MaCV(6) -- Kind (7) -- InFact (8) -- Money (9)
					r++;
					row = s.createRow(i+1);
					cell = row.createCell(0);	
					cell.setCellValue(i+1);	// STT
					
					cell = row.createCell(1);	
					cell.setCellValue(empsn); // EMSPN
						sql_call 		= "{? = call SON_GET_JOB_ID_FOR_EMP(?,?)}";
						stm = con.prepareCall(sql_call);
						stm.setString(2, empsn);
						stm.setDate(3, date_sql);
						stm.registerOutParameter(1, Types.VARCHAR);
						stm.execute();
						id_job_ = stm.getString(1);
						id_job_ = id_job_ == null ? "" : id_job_;
						
						stm.close();
					
					if(!id_job_.equals("")){
						sql	=
							"select nvl(t.note,'') from n_emp_job_detail t\n" +
							"where t.id_job = '"+id_job_+"' \n"  + 
							"      and t.date_e is null" +
							" 	   and t.empsn = '" +empsn+"'" ;
			
						Object	obj_	= obj_util.Exe_Sql_Obj(sql);
						note 			= obj_== null ? "" : obj_.toString();
					}else{
						note			= "";
					}
					
					list 	= dao.find(1, "select a.FNAME, a.LNAME, b.NAME_FACT,b.NAME_GROUP, b.NAME_DEPT_NAME from N_EMPLOYEE a, N_DEPARTMENT b where a.DEPSN=b.ID_DEPT and a.EMPSN=?", new Object[]{empsn});
					list1 	= dao.find(1, "select b.NAME_JOB,b.CODE_JOB,b.KIND_JOB,b.IN_FACT from N_JOB b where b.ID_KEY = ?", new Object[]{id_job_});
					
					if (list.size()>0){
						
						Object[] obj = (Object[]) list.get(0);
						cell = row.createCell(2);	//HO TEN
						cell.setCellValue(obj[0].toString().trim() + " " + obj[1].toString().trim());
						cell = row.createCell(3);	//NAME FACT
						cell.setCellValue(obj[2]==null?"":obj[2].toString());
						cell = row.createCell(4);	//NAME GROUP
						cell.setCellValue(obj[3]==null?"":obj[3].toString());
						cell = row.createCell(5);	//NAME DEPT
						cell.setCellValue(obj[4]==null?"":obj[4].toString());
						
						if (list1.size()>0){
							
							Object[] obj1 = (Object[]) list1.get(0);
						
							cell = row.createCell(6);	// NAME_JOB
							name_job_	=  obj1[0] == null ? "" : obj1[0].toString() ; 
							cell.setCellValue(name_job_);
							
							cell = row.createCell(7);// CODE_JOB
							cell.setCellValue(obj1[1] == null ? "" : obj1[1].toString());
						
							cell = row.createCell(8);// KIND
							cell.setCellValue(obj1[2] == null ? "" : obj1[2].toString());
							
							cell = row.createCell(9);//IN FACT
							cell.setCellValue(obj1[3] == null ? "" : obj1[3].toString());
						}
						cell = row.createCell(10);//NOTE
						cell.setCellValue(note);
						cell = row.createCell(11);
						cell.setCellValue(date_str);
					}else{
						cell = row.createCell(1);
						cell.setCellValue("Không tìm thấy thông tin");
					}
					
					// show money : 
					cell = row.createCell(10);//NOTE
					cell.setCellValue( getMoney(empsn,date_sql));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally{
			try {
				con.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
		return	wb;
	
	}

	private Double getMoney(String empsn, java.sql.Date date_sql) {
		double retVal = 0;
		Connection con=Application.getApp().getConnection();
		  System.out.println("Connection created..............");
		  String call = "{ ? = call SON_GET_JOB_BONUS_FOR_EMP(?,?) }";
		  CallableStatement cstmt = null;
		try {
			cstmt = con.prepareCall(call);
			cstmt.registerOutParameter(1, Types.NUMERIC);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.DATE);
			cstmt.setString(2, empsn);
			cstmt.setDate(3, date_sql);
			  cstmt.execute();
			  retVal = cstmt.getDouble(1);
			  
			  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 finally{
			
			  try {
				  cstmt.close();
				  con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		  return retVal;
		  
	}

}
