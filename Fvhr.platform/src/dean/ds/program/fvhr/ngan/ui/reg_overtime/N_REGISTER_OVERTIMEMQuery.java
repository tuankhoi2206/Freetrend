package ds.program.fvhr.ngan.ui.reg_overtime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.layout.CenterLayout;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import nextapp.echo2.app.WindowPane;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import fv.util.Vni2Uni;
import ds.program.fvhr.domain.N_REGISTER_OVERTIME;
import ds.program.fvhr.domain.pk.N_REGISTER_OVERTIMEPk;
import ds.program.fvhr.ngan.ui.DeptUserControl_FactDetail;
import ds.program.fvhr.domain.N_DEPT_LOCK_OT;
import ds.program.fvhr.domain.pk.N_DEPT_LOCK_OTPk;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;
import dsc.echo2app.task.ATask;
import dsc.util.function.UUID;
import echopointng.DateField;
import echopointng.GroupBox;

public class N_REGISTER_OVERTIMEMQuery extends QueryNormal2 {

    Column rootLayout;
    DeptUserControl_FactDetail dept_detail;
    OBJ_EMPSN obj_e 	 = new OBJ_EMPSN();
    OBJ_UTILITY obj_util = new OBJ_UTILITY();
    private DscField 			tf_date;
	private DateField			dateF_to_date;
    Button  btn_xuat_excel,btn_khoa_tca,btn_mokhoa_tca ;
    HSSFWorkbook wb;
    Label   errLabel;
    WindowPane winPane;
    SimpleDateFormat sf_ = new SimpleDateFormat(OBJ_UTILITY.DATE_FORMAT_STR);
	
    String user_up = Application.getApp().getLoginInfo().getUserID();
	String ma_user = "";
	
	String empsn 	= "";
	Date   date;
	String fact_ 	= "";
	String group_ 	= "";
	String dept_ 	= "";
	String id_dept	= "";
	
	RadioButton rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac;
	
	
	CheckBox cb_ds_dakhoa,cb_ds_chuakhoa;
	

	/**
	 * Creates a new <code>N_REGISTER_OVERTIMEMQuery</code>.
	 */
	public N_REGISTER_OVERTIMEMQuery() {
		super();

		// Add design-time configured components.
		initComponents();
	}
	
	private boolean kt_dk_thaotac()
	{
		empsn 			= dept_detail.getEmpsn().trim();
		date			= dept_detail.getDate();
		
	    fact_ 	= dept_detail.getFact().trim();
		group_	= dept_detail.getGroup().trim();
		dept_	= dept_detail.getNameDept().trim();
		id_dept  = dept_detail.getIDDept();
		
		if(empsn.equals("") && fact_.equals(""))
		{
			OBJ_UTILITY.ShowMessageError("Lỗi.!Chưa nhập số thẻ hoặc xưởng");
			return false;
		}
		
		if(date == null)
		{   
				OBJ_UTILITY.ShowMessageError("Lỗi.!Chọn năm cần tìm thông tin('dd/MM/yyyy')'");
				return false;
			
		}else
		{
			
			String dmy_str_to	= tf_date.getText().toString();
			Date dmy_date_to 	= null;
			
			if(dmy_str_to.equals(""))
			{
				dmy_date_to = date;
			}else
			{
				try {
					dmy_date_to 	= sf_.parse(dmy_str_to);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
				if(dmy_date_to == null)
				{
					OBJ_UTILITY.ShowMessageError("Lỗi..!Định dạng đến ngày phải là: 'dd/MM/yyyy'");
					return false;
				}else if(dmy_date_to.before(date))
				{
					OBJ_UTILITY.ShowMessageError("Lỗi..!Đến ngày phải lớn hơn từ ngày ");
					return false;
				}
			}
		}
		return true;
	}
	
	protected void doQuery()
	{
		String sql = "";
		String sql1= "";
		String sql2= "";
		String sql3= "";
		SimpleDateFormat sf_ = new SimpleDateFormat(OBJ_UTILITY.DATE_FORMAT_STR);

		String empsn 	= dept_detail.getEmpsn().trim();
		Date   date		= dept_detail.getDate();
		
	    String fact 	= dept_detail.getFact().trim();
		String group	= dept_detail.getGroup().trim();
		String dept		= dept_detail.getNameDept().trim();
		String id_dept  = dept_detail.getIDDept();
	
		
		RadioButton rdio_date  = dept_detail.rdio_date;
		RadioButton rdio_month = dept_detail.rdio_month;
		RadioButton rdio_year  = dept_detail.rdio_year;
		
		rdio_f1	   = dept_detail.rdio_f1;
		rdio_f2	   = dept_detail.rdio_f2;
		rdio_f3	   = dept_detail.rdio_f3;
		rdio_f5	   = dept_detail.rdio_f5;
		rdio_f6	   = dept_detail.rdio_f6;
		rdio_khac  = dept_detail.rdio_khac;
		
		
		if(!kt_dk_thaotac())
		{
			return;
		}
		    
		String	dmy_str		= sf_.format(date);
		String  my_str		= sf_.format(date).substring(3, 10); 
		String  y_str		= sf_.format(date).substring(6, 10); 
		String dmy_str_to	= tf_date.getText().toString();
		
			if(!empsn.equals(""))
			{   
				if(obj_e.Kt_vungqly_khi_nhap_st(empsn, ma_user,"DEPSN_TEMP"))
				{
					sql = "o.EMPSN=? ";
					//*Xuat theo ngay,thang,or nam	
					sql1  = obj_e.select_ngay_thang_nam(rdio_date, rdio_month, rdio_year, dmy_str,dmy_str_to, my_str, y_str, "o", "DATE_OVER");
					//*Xuat theo khoa hay chua khoa
					sql3  = khoa_chuakhoa("o"); 
					sql = sql+sql1+sql3;
					getProgram().query(sql, new Object[]{empsn});
				}
			}else
				{
					String sql_maql = obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact, group, dept,"DEPSN_TEMP",id_dept, ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
					  if(sql_maql.equals("NO"))
					  {
						  OBJ_UTILITY.ShowMessageError("Bạn không có quyền thao tác");
						  return;
					  }else
					  if(!sql_maql.equals("NO"))
					  {
						  if (!fact.equals("") && group.equals("") && dept.equals(""))
						  {
							  sql = sql+ "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D " +
								"								WHERE E.EMPSN=o.EMPSN " +
								"									AND E.DEPSN_TEMP=D.ID_DEPT " ;
								//*Tim theo xuong nhom don vi ma chi thuoc quyen quan ly cua user do
								sql1 = obj_e.find_fact_emp(empsn, fact, group,dept, id_dept, "D", "E", ma_user,rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac)+")";
							 
							  //*Xuat theo ngay,thang,or nam	
							  sql1 = sql1+ obj_e.select_ngay_thang_nam(rdio_date, rdio_month, rdio_year, dmy_str,dmy_str_to, my_str, y_str, "o", "DATE_OVER");
							//*Xuat theo chi tiet tung xuong neu chon f1,f2...khac(chi co xuong FVL)
					//		  sql2 = obj_e.check_fact_FVL_rb(rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac, "D");
							//*Xuat theo khoa hay chua khoa
							  sql3  = khoa_chuakhoa("o"); 
							  sql = sql+sql1+sql2+sql3;
							  getProgram().query(sql, new Object[]{});
						  }else if (!fact.equals("") && !group.equals("") && dept.equals(""))
						  {
								sql = sql+ "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D " +
										"								WHERE E.EMPSN=o.EMPSN " +
										"									AND E.DEPSN_TEMP=D.ID_DEPT " ;
								//*Tim theo xuong nhom don vi ma chi thuoc quyen quan ly cua user do
								sql1 = obj_e.find_fact_emp(empsn, fact, group,dept, id_dept, "D", "E", ma_user,rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac)+")";
									
								sql2 = obj_e.select_ngay_thang_nam(rdio_date, rdio_month, rdio_year, dmy_str,dmy_str_to, my_str, y_str, "o", "DATE_OVER");
								//*Xuat theo khoa hay chua khoa
								sql3  = khoa_chuakhoa("o"); 
								sql = sql+sql1+sql2+sql3;	
								getProgram().query(sql, new Object[]{});
					      }else if (!fact.equals("") && (!group.equals("") ||group.equals("")) && !dept.equals(""))
					      {
								sql = sql+ "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D " +
										"								WHERE E.EMPSN=o.EMPSN " +
										"									AND E.DEPSN_TEMP=D.ID_DEPT " ;
										//*Tim theo xuong nhom don vi ma chi thuoc quyen quan ly cua user do
								sql1 = obj_e.find_fact_emp(empsn, fact, group,dept, id_dept, "D", "E", ma_user,rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac)+")";
										
								sql2 = obj_e.select_ngay_thang_nam(rdio_date, rdio_month, rdio_year, dmy_str,dmy_str_to, my_str, y_str, "o", "DATE_OVER");
								//*Xuat theo khoa hay chua khoa
								sql3  = khoa_chuakhoa("o"); 
								sql = sql+sql1+sql2+sql3;		
								getProgram().query(sql, new Object[]{});
					       }
					  }
				}
			}
			

	
	private String khoa_chuakhoa(String alias_table)
	{
		String list = "";
		
		if(cb_ds_dakhoa.isSelected() && !cb_ds_chuakhoa.isSelected())
		{
			list = " AND "+alias_table+".LOCKED = 'Y'";
		}else if(!cb_ds_dakhoa.isSelected() && cb_ds_chuakhoa.isSelected())
		{
			list = " AND ("+alias_table+".LOCKED = 'N' OR "+alias_table+".LOCKED IS NULL)";
		}else
		{
			list = "";
		}
		return list;
	}
	
	

	@Override
	protected int doInit() {
		int ret = super.doInit();
		ConditionManager conditionMgr = new ConditionManager();
		setConditionMgr(conditionMgr);


	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼

		//<一般查詢條件定義>

        String key;
		return ret;

	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	
	protected HSSFWorkbook action_Export() {
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String sql4 = "";
		
		
		RadioButton rdio_date  = dept_detail.rdio_date;
		RadioButton rdio_month = dept_detail.rdio_month;
		RadioButton rdio_year  = dept_detail.rdio_year;

		
		rdio_f1	   = dept_detail.rdio_f1;
		rdio_f2	   = dept_detail.rdio_f2;
		rdio_f3	   = dept_detail.rdio_f3;
		rdio_f5	   = dept_detail.rdio_f5;
		rdio_f6	   = dept_detail.rdio_f6;
		rdio_khac  = dept_detail.rdio_khac;
		
		String	dmy_str		= sf_.format(date);
		String  my_str		= sf_.format(date).substring(3, 10); 
		String  y_str		= sf_.format(date).substring(6, 10); 
		String dmy_str_to	= tf_date.getText().toString();
		
		String sql = 
			"select a.depsn_temp,a.empsn,a.fname||' '||a.lname as fname,to_char(b.date_over,'dd/MM/yyyy')\n" +
			",b.count_time,b.locked,a.depsn\n" + 
			"from n_employee a,n_register_overtime b,n_department dt\n" + 
			"where a.empsn=b.empsn and a.depsn_temp=dt.id_dept";
		
		//*xuat so the or xuong,nhom,dv	
		sql1 = obj_e.find_fact_emp(empsn, fact_, group_, dept_,id_dept, "dt","a",ma_user,rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac);
	//*Xuat theo chi tiet tung xuong neu chon f1,f2...khac(chi co xuong FVL)
		sql2 = obj_e.check_fact_FVL_rb(rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac, "dt");
	//*Xuat theo ngay,thang,or nam	
		sql3 = obj_e.select_ngay_thang_nam(rdio_date, rdio_month, rdio_year, dmy_str,dmy_str_to, my_str, y_str, "b", "DATE_OVER");
	//*Xuat theo khoa hay chua khoa
		sql4  = khoa_chuakhoa("b"); 	
		
		sql= sql+sql1+sql2+sql3+sql4+ " order by dt.id_dept,a.empsn,a.fname,b.date_over";
		
		List<Object[]> list_arr = obj_util.Exe_Sql_nfield_nrow(sql,7);
    		
		
	try {	// get data and export into Workbook
			
			File f = ReportFileManager.getReportFormatFolder("dshr");
			InputStream in 		= new FileInputStream(new File(f.getPath(), "temp.xls"));
			
			wb = new HSSFWorkbook(in);
			HSSFSheet 	sheet	= wb.getSheetAt(0);
			HSSFRow		row;
			HSSFCell	cell;
			
			row				= sheet.createRow(0);
				cell		= row.createCell(0);
								cell.setCellValue("DON VI");
				cell		= row.createCell(1);
								cell.setCellValue("SO THE");				
				cell		= row.createCell(2);
								cell.setCellValue("HO TEN");	
				cell		= row.createCell(3);				
								cell.setCellValue("NGAY DK TCA");
				cell		= row.createCell(4);
								cell.setCellValue("SO GIO DK TCA");
				cell		= row.createCell(5);
								cell.setCellValue("KHOA TCA");
				cell		= row.createCell(6);
								cell.setCellValue("DON VI HIEN HANH");
				
								
			int n_row	= 1;
			int n_col	= 0;
			for(Object[] arr : list_arr){
				
				row		= sheet.createRow(n_row);
				n_col	= 0;
				for(Object ele : arr){
					cell	= row.createCell(n_col);
					switch (n_col) {
					case 0:	// DON VI
						cell.setCellValue(ele.toString());
						break;
					case 1:	// SO THE
						empsn	= ele.toString();
						cell.setCellValue(ele.toString());
						break;	
					case 2:	// HO TEN
						cell.setCellValue(ele.toString());
						break;
					case 3:	// NGAY DK TCA
						cell.setCellValue(ele.toString());
						break;
					case 4:	//SO GIO DK TCA
						cell.setCellValue(Double.valueOf(ele.toString()));
						break;
					case 5:	//KHOA TCA
						if(ele.toString().equals("0"))
						{
							ele = "";
						}
						cell.setCellValue(ele.toString());
						break;
					case 6:	//DON VI HHANH
						cell.setCellValue(ele.toString());
						break;
					
					default:
						break;
					}
					
					n_col ++ ;
				}
				n_row ++ ;
			}
			
			
		} catch (Exception e) {
			OBJ_UTILITY.ShowMessageError(e.getMessage());
		}
		

		return wb;
	}
	
	private void doExport(){
		if(wb == null){
			errLabel.setText(" !!! ĐIỀU KIỆN XUẤT ???");
			errLabel.setForeground(Color.RED);
			return;
		}
		
		File f1 = new File(System.getProperty("java.io.tmpdir"), UUID.generate());			
		FileOutputStream out;
		try {
			
			out = new FileOutputStream(f1);
			wb.write(out);
			out.flush();
			out.close();
			String name = "DANH_SACH";
			File saveFile = new File(f1.getParentFile(),URLEncoder.encode(Application.getApp().getLoginInfo().getUserID() + ";application/vnd.ms-excel;" + name + Calendar.getInstance().getTimeInMillis() + "" + ".xls", "UTF-8"));			
			ReportFileManager.saveTempReportFile(f1, saveFile);
			Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//userClose();
		//winPane.userClose();
		
	}
	
	private String getViewerUrl() {
		HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
		String viewerUrl = request.getRequestURL() + "?" +
		WebRenderServlet.SERVICE_ID_PARAMETER + "=" + ReportService.INSTANCE.getId() + "&" + 
		ReportService.PARAM_TYPE + "=" + (ATask.EXECTYPE_DIRECT == 0 ? ReportService.TYPE_TEMP : ReportService.TYPE_OUTPUT) + "&" +
		ReportService.PARAM_KEY + "=";
		return viewerUrl;
	}
	
	private void lock_over_time()
	{
		String strnote		   = "";
		String sql1 		   = ""; 
		date				   = dept_detail.getDate();
		RadioButton rdio_date  = dept_detail.rdio_date;
		RadioButton rdio_month = dept_detail.rdio_month;
		RadioButton rdio_year  = dept_detail.rdio_year;
		
		rdio_f1	   = dept_detail.rdio_f1;
		rdio_f2	   = dept_detail.rdio_f2;
		rdio_f3	   = dept_detail.rdio_f3;
		rdio_f5	   = dept_detail.rdio_f5;
		rdio_f6	   = dept_detail.rdio_f6;
		rdio_khac  = dept_detail.rdio_khac;
	    
		empsn 				   = dept_detail.getEmpsn();
		String fact 		   = dept_detail.getFact().trim();
		String group	       = dept_detail.getGroup().trim();
		String dept		       = dept_detail.getNameDept().trim();
		String id_dept         = dept_detail.getIDDept();
		
		ArrayList<String> list_emp		= new ArrayList<String>();
		ArrayList<String> list_depsn	= new ArrayList<String>();
		
		
		
		 if(!obj_e.check_lock(user_up))
		 {
			 return;
		 }else
		 if(!kt_dk_thaotac())
		 {
			 return;
		 }
		 
		 String	dmy_str		= sf_.format(date);
		 String  my_str		= sf_.format(date).substring(3, 10); 
		 String  y_str		= sf_.format(date).substring(6, 10); 
		 String dmy_str_to	= tf_date.getText().toString();
		
	
		 
		 if(!empsn.equals(""))
		 {  
			if(!obj_e.Kt_vungqly_khi_nhap_st(empsn,ma_user,"DEPSN_TEMP"))
			{
				return;
			}else if(!obj_e.check_lock_month(empsn,"", "", "", "", date,"DEPSN_TEMP",ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac))
			{
				return;
			}
			
			list_emp 	= obj_util.getListEmpsn_R(empsn,"","","","",ma_user,"DEPSN_TEMP",rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
			
			In_Up_emp_lock_ot(list_emp, rdio_date, rdio_month, rdio_year, "KHOA TCA","Y");
			
		 }else//fact is not null
		 {
			 String sql_maql = obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact, group, dept,"DEPSN_TEMP",id_dept, ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
				if(sql_maql.equals("NO"))
				{   
					OBJ_UTILITY.ShowMessageError("Bạn không có quyền thao tác");
					return;
				}else
				{
					if( !obj_e.check_lock_month("",fact,group,dept,"" ,date,"DEPSN_TEMP",ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac))
					{
						return;
					}else
					{   
						list_depsn  = obj_util.getListDept("", fact, group, dept, id_dept, "DEPSN_TEMP",ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
						list_emp 	= obj_util.getListEmpsn_R(empsn,fact,group,dept,id_dept,ma_user,"DEPSN_TEMP",rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
						In_Up_dept_lock_ot(list_depsn, rdio_date, rdio_month, rdio_year, "KHOA TCA", "1");
						In_Up_emp_lock_ot(list_emp, rdio_date, rdio_month, rdio_year, "KHOA TCA", "Y");
					}	
				}
			 
		 }
		 OBJ_UTILITY.ShowMessageOK("OK");
	}
	
	private void unlock_over_time()
	{
		String strnote		   = "";
		String sql1 		   = ""; 
		date				   = dept_detail.getDate();
		RadioButton rdio_date  = dept_detail.rdio_date;
		RadioButton rdio_month = dept_detail.rdio_month;
		RadioButton rdio_year  = dept_detail.rdio_year;
		
		rdio_f1	   = dept_detail.rdio_f1;
		rdio_f2	   = dept_detail.rdio_f2;
		rdio_f3	   = dept_detail.rdio_f3;
		rdio_f5	   = dept_detail.rdio_f5;
		rdio_f6	   = dept_detail.rdio_f6;
		rdio_khac  = dept_detail.rdio_khac;
	    
		empsn 				   = dept_detail.getEmpsn();
		String fact 		   = dept_detail.getFact().trim();
		String group	       = dept_detail.getGroup().trim();
		String dept		       = dept_detail.getNameDept().trim();
		String id_dept         = dept_detail.getIDDept();
		
		ArrayList<String> list_emp		= new ArrayList<String>();
		ArrayList<String> list_depsn	= new ArrayList<String>();
		
		
		
		 if(!obj_e.check_lock(user_up))
		 {
			 return;
		 }else
		 if(!kt_dk_thaotac())
		 {
			 return;
		 }
		 
		 String	dmy_str		= sf_.format(date);
		 String  my_str		= sf_.format(date).substring(3, 10); 
		 String  y_str		= sf_.format(date).substring(6, 10); 
		 String dmy_str_to	= tf_date.getText().toString();
		
	
		 
		 if(!empsn.equals(""))
		 {  
			if(!obj_e.Kt_vungqly_khi_nhap_st(empsn,ma_user,"DEPSN_TEMP"))
			{
				return;
			}else if(!obj_e.check_lock_month(empsn,"", "", "", "", date,"DEPSN_TEMP",ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac))
			{
				return;
			}
			
			list_emp 	= obj_util.getListEmpsn_R(empsn,"","","","",ma_user,"DEPSN_TEMP",rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
			
			In_Up_emp_lock_ot(list_emp, rdio_date, rdio_month, rdio_year, "MO KHOA TCA","N");
			
		 }else//fact is not null
		 {
			 String sql_maql = obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact, group, dept,"DEPSN_TEMP",id_dept, ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
				if(sql_maql.equals("NO"))
				{   
					OBJ_UTILITY.ShowMessageError("Bạn không có quyền thao tác");
					return;
				}else
				{
					if( !obj_e.check_lock_month("",fact,group,dept,"" ,date,"DEPSN_TEMP",ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac))
					{
						return;
					}else
					{   
						list_depsn  = obj_util.getListDept("", fact, group, dept, id_dept, "DEPSN_TEMP",ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
						In_Up_dept_lock_ot(list_depsn, rdio_date, rdio_month, rdio_year, "MO KHOA TCA", "0");
						
					}	
				}
			 
		 }
		 OBJ_UTILITY.ShowMessageOK("OK");
	}
	
	private void In_Up_dept_lock_ot(ArrayList<String> list_depsn,RadioButton rd_date,RadioButton rd_month,RadioButton rd_year,String action,String status)
	{   
		 date				   			= dept_detail.getDate();
		 String	dmy_str				    = sf_.format(date);
		 String  my_str					= sf_.format(date).substring(3, 10); 
		 String  y_str					= sf_.format(date).substring(6, 10); 
		 String dmy_str_to				= tf_date.getText().toString();
		 int sum_day_of_month		    = obj_util.GET_NUM_DAY_OF_MONTH(date);//lay ra so ngay cua thang khi kt khoa theo thang
		 ArrayList<Date> list_date_ot	= new ArrayList<Date>();
		 Date date_to 					= null;
		
		 String end_day_of_month_str	= "";
		 
		 if(sum_day_of_month > 10)
		 {
			end_day_of_month_str = String.valueOf(sum_day_of_month)+"/"+my_str;
		 }else
		 {
			end_day_of_month_str = "0"+String.valueOf(sum_day_of_month)+"/"+my_str;	
		 } 
		 
		 String strnote 					= "";
		 
		for(String depsn:list_depsn)
		{
			if(rd_date.isSelected())
			{
				/*try {
					date_to = sf_.parse(dmy_str);
				} catch (ParseException e) {
					e.printStackTrace();
				}*/
				if(dmy_str_to.equals(""))
				{
					dmy_str_to = dmy_str;
					try {
						date_to = sf_.parse(dmy_str_to);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else
				{
					try {
						date_to = sf_.parse(dmy_str_to);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				strnote = "KToan:_"+ma_user+" "+action+" tu:"+dmy_str+" den: "+sf_.format(date_to)+" cua don vi: "+depsn;
			}else if(rd_month.isSelected())
			{
				try {
					date = sf_.parse("01/"+my_str);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				try {
					date_to = sf_.parse(end_day_of_month_str);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				strnote = "KToan:_"+ma_user+" "+action+" thang: "+my_str+" cua don vi: "+depsn;
			}else if(rd_year.isSelected())
			{
				OBJ_UTILITY.ShowMessageError("Khong the "+action+" theo nam");
				return;
			}
			
			list_date_ot = obj_util.getAllListDate_input(date, date_to);
			
			for(Date date_:list_date_ot)
			{
				 String date_str = sf_.format(date_);
				 IGenericDAO<N_DEPT_LOCK_OT, N_DEPT_LOCK_OTPk> Dao_dot = Application.getApp().getDao(N_DEPT_LOCK_OT.class);
				 N_DEPT_LOCK_OTPk pk = new N_DEPT_LOCK_OTPk(date_,depsn);
				 N_DEPT_LOCK_OT Data_dot = Dao_dot.findById(pk);
				 
				 if(Data_dot == null)
				 {
					 String sql = 
						 " INSERT INTO N_DEPT_LOCK_OT(DATES,DEPSN,STATUS,NOTE)\n" +
						 " VALUES(TO_DATE('"+date_str+"','DD/MM/YYYY'),'"+depsn+"','"+status+"','"+strnote+"')";
					 obj_util.Exe_Update_Sql(sql); 
					
					
				 }else
				 {
					 String old_status = "";
					 old_status = Data_dot.getSTATUS();
					 Data_dot.setSTATUS(status);
					 Data_dot.setNOTE(strnote+" "+old_status+" -->"+status);
					 Dao_dot.update(Data_dot);
				 }	
			}
			obj_e.Insert_N_Action_Daily(ma_user, "N_DEPT_LOCK_OT", action, depsn, strnote);
		}
	}
	
	private void In_Up_emp_lock_ot(ArrayList<String> list_empsn,RadioButton rd_date,RadioButton rd_month,RadioButton rd_year,String action,String lock)
	{   
		 String sql1					= ""; 
		 date				   			= dept_detail.getDate();
		 String	dmy_str				    = sf_.format(date);
		 String  my_str					= sf_.format(date).substring(3, 10); 
		 String  y_str					= sf_.format(date).substring(6, 10); 
		 String dmy_str_to				= tf_date.getText().toString();
		
		
		 String strnote 					= "";
		 
		for(String empsn:list_empsn)
		{
			if(rd_date.isSelected())
			{
				if(dmy_str_to.equals(""))
				{
					dmy_str_to = dmy_str;
				}
				strnote = "KToan:_"+ma_user+" "+action+" tu:"+dmy_str+" den: "+dmy_str_to+" cua so the: "+empsn;
			}else if(rd_month.isSelected())
			{
				strnote = "KToan:_"+ma_user+" "+action+" thang: "+my_str+" cua so the: "+empsn;
			}else if(rd_year.isSelected())
			{
				OBJ_UTILITY.ShowMessageError("Khong the "+action+" theo nam");
				return;
			}
			
			String sql =
				" update n_register_overtime t\n" +
				" set t.locked = '"+lock+"'\n" + 
				" where t.empsn = '"+empsn+"' \n" ;
			sql1 = obj_e.select_ngay_thang_nam(rd_date,rd_month,rd_year, dmy_str, dmy_str_to, my_str, y_str, "t","DATE_OVER" );
			sql  = sql+sql1;
			obj_util.Exe_Update_Sql(sql);    
			
		    obj_e.Insert_N_Action_Daily(ma_user, "N_REGISTER_OVERTIME", action, empsn, strnote);
			
		}
	}
	
	@Override
	protected void doLayout() {
		super.doLayout();
		this.windowPane.setHeight(new Extent(430));
		this.windowPane.setResizable(false);
	}
	private void initComponents() {
		
		IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();
			
        rootLayout 	= new Column();
       
    //    rootLayout.setWidth(new Extent(100, Extent.PERCENT));
    //    rootLayout.setSize(3);
        this.add(rootLayout);
        this.setVerticalScroll(new Extent(1000));
       
 
        
        
        dept_detail = new DeptUserControl_FactDetail();
        rootLayout.add(dept_detail);
        
    
        
        
        Row row_den_ngay = new Row();
        
        tf_date	= new DscField();
		tf_date.setAlignment(new Alignment(Alignment.LEFT, Alignment.LEFT));
		
		
		dateF_to_date	= new DateField();
		dateF_to_date.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dateF_to_date.setTextField(tf_date);
		dateF_to_date.setWidth(new Extent(165));
		row_den_ngay.setInsets(new Insets(5));
		row_den_ngay.setCellSpacing(new Extent(11));
		row_den_ngay.add(new Label("Đến ngày: "));
		row_den_ngay.add(dateF_to_date);
		row_den_ngay.setAlignment(new Alignment(Alignment.CENTER, Alignment.LEFT));
		rootLayout.add(row_den_ngay);
		
		cb_ds_dakhoa   = new CheckBox();
		cb_ds_chuakhoa = new CheckBox();
		cb_ds_dakhoa.setText("DS ĐÃ KHÓA");
		cb_ds_chuakhoa.setText("DS CHƯA KHÓA");
		rootLayout.add(cb_ds_dakhoa);
		rootLayout.add(cb_ds_chuakhoa);

		
		 btn_xuat_excel = new Button();
	     btn_xuat_excel.setText("Xuất Excel");
	     btn_xuat_excel.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
	     btn_xuat_excel.setBorder(new Border(2, Color.LIGHTGRAY, Border.STYLE_OUTSET));
	     btn_xuat_excel.setWidth(new Extent(80));
		 rootLayout.add(btn_xuat_excel);
		 btn_xuat_excel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				rdio_f1	   = dept_detail.rdio_f1;
				rdio_f2	   = dept_detail.rdio_f2;
				rdio_f3	   = dept_detail.rdio_f3;
				rdio_f5	   = dept_detail.rdio_f5;
				rdio_f6	   = dept_detail.rdio_f6;
				rdio_khac  = dept_detail.rdio_khac;
				if(!kt_dk_thaotac())
				{
					return;
				}
			    if(fact_.equals("") && !empsn.equals(""))
				{
					if(!obj_e.Kt_vungqly_khi_nhap_st(empsn, ma_user,"DEPSN_TEMP"))
					{
						return;
					}else
					{
						action_Export(); 
						doExport();
					}
				}else 
				{
					String sql_maql = obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact_,group_,dept_,"DEPSN",id_dept, ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
					if(sql_maql.equals("NO"))
					{
						OBJ_UTILITY.ShowMessageError("Bạn không có quyền thao tác");
						return;
					}else
					{
						action_Export(); 
					    doExport();
					}
				}
				
			}
		});
		
      btn_khoa_tca = new Button();
      btn_khoa_tca.setText("KHÓA TCA");
      btn_khoa_tca.setBorder(new Border(2, Color.LIGHTGRAY, Border.STYLE_OUTSET));
      btn_khoa_tca.setWidth(new Extent(80));
	  rootLayout.add(btn_khoa_tca);
	  btn_khoa_tca.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			empsn = dept_detail.getEmpsn();
			
			lock_over_time();
			
		}
	});
	  
	  
	  btn_mokhoa_tca = new Button();
	  btn_mokhoa_tca.setText("MỞ KHÓA TCA");
	  btn_mokhoa_tca.setBorder(new Border(2, Color.LIGHTGRAY, Border.STYLE_OUTSET));
	  btn_mokhoa_tca.setWidth(new Extent(100));
	  rootLayout.add(btn_mokhoa_tca);
	  btn_mokhoa_tca.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			unlock_over_time();
			
		}
	});
		 
        dept_detail.getEMPSN_TextField().addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				boolean flag = dept_detail.flag_empsn;//vi trong dept_ctrl da kt khi nhap so the nen bien flag dc gan de k kt nua, k thoa dk thi thoat thoat luon
				if(flag == false)
				{
					return;
				}else
				{
					doQuery();
				}
				
			}
				
		});
        
        dept_detail.rdio_date.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				dateF_to_date.setEnabled(true);
				
			}
		});
        
        dept_detail.rdio_month.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				tf_date.setText("");
				dateF_to_date.setEnabled(false);
				
			}
		});
        
        dept_detail.rdio_year.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				tf_date.setText("");
				dateF_to_date.setEnabled(false);
			}
		});
        
        if(obj_e.check_user_KToan(user_up) || obj_e.check_user_Admin(user_up))
        {
     	   btn_khoa_tca.setEnabled(true);
     	   btn_khoa_tca.setText("KHÓA TCA");
     	   btn_khoa_tca.setBorder(new Border(2, Color.LIGHTGRAY, Border.STYLE_OUTSET));
     	   
     	   btn_mokhoa_tca.setEnabled(true);
     	   btn_mokhoa_tca.setText("MỞ KHÓA TCA");
     	   btn_mokhoa_tca.setBorder(new Border(2, Color.LIGHTGRAY, Border.STYLE_OUTSET));
     	   
     	   
        }else
        {
     	   btn_khoa_tca.setEnabled(false);
     	   btn_khoa_tca.setForeground(Color.LIGHTGRAY);
     	   btn_khoa_tca.setBorder(new Border(2, Color.LIGHTGRAY, Border.STYLE_INSET));
     	   
     	   btn_mokhoa_tca.setEnabled(false);
     	   btn_mokhoa_tca.setForeground(Color.LIGHTGRAY);
     	   btn_mokhoa_tca.setBorder(new Border(2, Color.LIGHTGRAY, Border.STYLE_INSET));
        }
        
	}

}
