package ds.program.fvhr.tien.ui;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.io.Resource;

import fv.util.ApplicationHelper;
import fv.util.HRUtils;
import fv.util.Vni2Uni;
import ds.program.fvhr.dao.wp.WorkpointsDAO;
import ds.program.fvhr.domain.ATTLOCK;
import ds.program.fvhr.domain.N_BAOGIAM_STATUS;
import ds.program.fvhr.domain.N_DATA_MAIN;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_EMP_ICCARD;
import ds.program.fvhr.domain.N_EMP_INFO;
import ds.program.fvhr.domain.N_EMP_JOB_DETAIL;
import ds.program.fvhr.domain.N_EMP_QUIT;
import ds.program.fvhr.domain.N_FACT_TRANSFER_LOCK;
import ds.program.fvhr.domain.N_GET_DATA;
import ds.program.fvhr.domain.N_HEALTH;
import ds.program.fvhr.domain.N_HOSPITAL;
import ds.program.fvhr.domain.N_JOB;
import ds.program.fvhr.domain.N_LABOUR;
import ds.program.fvhr.domain.N_REGISTER_OVERTIME;
import ds.program.fvhr.son.ui.ObjUtility;
import ds.program.fvhr.son.ui.ObjError;
import ds.program.fvhr.tien.ui.EMPSN_E;
import ds.program.fvhr.util.OBJ_EMPSN;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import nextapp.echo2.app.*;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.fv.app.filetransfer.UploadEvent;
import nextapp.echo2.fv.app.filetransfer.UploadListener;
import nextapp.echo2.fv.app.filetransfer.UploadSelect;

public class N_Emp_Edit_Data extends WindowPane {

	private ResourceBundle bundle;
	
	Grid 		rootLayout ;
	TextArea 	errorTextArea;
	Label 		infoLabel;

	private RadioButton radnotBHYT;

	private RadioButton radBHYT;
	String user_up = Application.getApp().getLoginInfo().getUserID();
	String note="Update N_emp_Quit";
	 OBJ_EMPSN obj_e = new OBJ_EMPSN();

	private String strTimes;

	private int intTimeout;

	private int intTimein;

	private String StrTimeint;

	private String StrTimeout;
	private String strShift_id="";
	SimpleDateFormat sp=null;
	String dept="";
	String dept_temp="";
	IGenericDAO<N_GET_DATA, String> objdaoN_get_data=null;
	List<N_GET_DATA> listN_get_data=null;
	IGenericDAO<N_EMPLOYEE, String> objdaoEmployee=null;
	List<N_EMPLOYEE> listEmployee=null;
	IGenericDAO<N_FACT_TRANSFER_LOCK,String> objdaoN_fact_trans_lock=null;
	IGenericDAO<ATTLOCK, String>  objdaoAttLock=null;
	List<ATTLOCK> listAttlock =null;
	N_FACT_TRANSFER_LOCK objFact_lock_1=null;
	N_EMPLOYEE objEmployee=null;
	N_GET_DATA obj_N_get_data=null;
	ATTLOCK objAttlock=null;
	N_FACT_TRANSFER_LOCK objFact_lock_2=null;
	List<N_FACT_TRANSFER_LOCK> list_fact_lock=null;
	private WorkpointsDAO dao;
	
	
	
	public N_Emp_Edit_Data() {
		
		initComponent();
		sp=new SimpleDateFormat("dd/MM/yyyy");
	}
	public WorkpointsDAO getJdbcDAO(){
		if (dao == null)
			dao = new WorkpointsDAO();
		return this.dao;
	}
	public UploadSelect getFileUploadExcel(){
		
		final Resource res = Application.getSpringContext().getResource("classpath:/conf/format");
		UploadSelect fileUploadSelect = new UploadSelect();
		fileUploadSelect.setEnabledSendButtonText("CẬP NHẬT");
		fileUploadSelect.setHeight(new Extent(50));
		fileUploadSelect.setWidth(new Extent(350));
		try{
			fileUploadSelect.addUploadListener(new UploadListener(){
				public void fileUpload(UploadEvent e) {
					if (!(e.getFileName().endsWith(".xls".toLowerCase()))) {
						Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK, bundle.getString("C_PROPERTIES.XLSIMPORT"));
						return;
					}
					
					try {

						assert res.getFile().exists() && res.getFile().isDirectory() : "";
						StopWatch sw = new StopWatch();
						//sw.start();
						File outputFile = new File(res.getFile(), "temp.xls");
						long unwrite = e.getSize();
						OutputStream out = new FileOutputStream(outputFile);
						//Transfer bytes from in to out
						byte[] buf = new byte[1024];
						int len;
						while ((len = e.getInputStream().read(buf)) > 0) {
							// when number of remain bytes is greater than
							// buffer size
							if (unwrite >= len)
								out.write(buf, 0, len);
							else {
								out.write(buf, 0, (int) unwrite);
								break;
							}
							unwrite -= len;
						}
						out.flush();
						out.close();
						boolean rturn=true; 	
						rturn=doEditDataEmployee(outputFile);						
						outputFile = null;
						//sw.stop();
					} catch (IOException e1) {
						
					}
				}

				public void invalidFileUpload(UploadEvent e) {
					Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK, bundle.getString("C_PROPERTIES.XLSIMPORT"));
				}	
					
			});
		}
		catch (TooManyListenersException ex) {
			// TODO: handle exception
			throw new RuntimeException(ex.toString());
		}
		return fileUploadSelect;
	}

	private boolean doEditDataEmployee(File fileExcel)
	{
		boolean rturn=true;
		InputStream in;
		ObjUtility obj_utili=new ObjUtility();
		ArrayList<ObjError> listError=new ArrayList<ObjError>();
		ObjError error;
		boolean check=true;
		HRUtils util=ApplicationHelper.getHRUtils();
		String empsn 		= "";
		Date  dateLamviec					= null;
		String strDateLamviec="";		
		int n_row			= 0;
		try{
			in = new FileInputStream(fileExcel);
			HSSFWorkbook wb = new HSSFWorkbook(in);
 			HSSFSheet sheet = wb.getSheetAt(0) ;
			HSSFRow row 	;
			n_row = sheet.getPhysicalNumberOfRows();
			if(n_row <=1){
				rturn = false;
			}
			else{
				System.out.println("Begin Import");
				for (int i = 1; i < n_row; i++) {
					System.out.println("========= Row : " + i);
					check	= true;
					row = sheet.getRow(i);
					
					System.out.println(row.getPhysicalNumberOfCells());
					System.out.println( row.getLastCellNum());
					for (int j = 0; j <  row.getLastCellNum(); j++) {
						if(check == false){
							continue;
						}
							switch (j) {
							case 0:	// so the
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("số thẻ không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								empsn 	= row.getCell(j).getStringCellValue();
								
								
								// kiem tra quyen thao tac
								
								 if(!util.getPermissionValidator().hasEmpsnPermission(empsn))
								 {
									 error=new ObjError();
									 error.setIdError("Lỗi dòng" +(i+1));
									 error.setErrorStr("Anh/Chị không có quyền thao tác trên số thẻ này");
									 listError.add(error);
									 check=false;
									 continue;
								 }
								 break;
								
							case 1 : 
								
								if(row.getCell(j+1).getCellType() != HSSFCell.CELL_TYPE_STRING){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Ngày làm việc");
									listError.add(error);
									check = false;
									continue;
								}								
								strDateLamviec	= row.getCell(j+1).getStringCellValue()	;
								//strDateLamviec=sp.format(dateLamviec);
							//	getNthWeekDay();								
								//check=checkRegisterOverTime(empsn, strDateLamviec);
								/*if(check == false){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr(" Số Thẻ " +empsn +" đã đăng ký tăng ca ngày " +strDateLamviec);
									listError.add(error);
									continue;
								}*/
								//StrTimeint=getMinTimeday(empsn, strDateLamviec);
								break;
	
							case 2 : 
								if(row.getCell(j+1).getCellType() != HSSFCell.CELL_TYPE_STRING){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Giá trị ca làm việc không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								strShift_id=row.getCell(j+1).getStringCellValue();
								

								/*if(strShift_id.equals("CA3")||strShift_id.equals("C32")||strShift_id.equals("C35"))
								{
									StrTimeint=getTimeChange(strShift_id);
									StrTimeout=this.getStrTimeout();									
									strTimes=StrTimeout+StrTimeint;
								}	*/
								
								if(!strShift_id.equals("CA3"))
								{
									StrTimeint=getTimeChange(strShift_id);
									strTimes=StrTimeint+StrTimeout;
								}	
								
								break;
														
							default:
								break;
							}
						
					} // the end for (j)
					
					
					if(check){
						try
						{
							//1: update du lieu trong bang N_DATA_MAIN
							IGenericDAO<N_DATA_MAIN, String> objdaoData_main=Application.getApp().getDao(N_DATA_MAIN.class);
							String strEmpcn=doGetempsn(empsn);
							List<N_DATA_MAIN> listdata_main=objdaoData_main.find(1, "from N_DATA_MAIN where EMPCN=? and DATES LIKE '%"+strDateLamviec+"%' ", strEmpcn);
							if(listdata_main.size()>0)
							{
								N_DATA_MAIN objN_data_main=listdata_main.get(0);
								String strTimeOlds=objN_data_main.getTIMES();
								String strNote=objN_data_main.getNOTE();
								objN_data_main.setTIMES(strTimes);
								objN_data_main.setNOTE(strTimeOlds+"_"+strNote); 
								objdaoData_main.update(objN_data_main);
							}													
							//2: mo khoa cho tat ca cac bang
							boolean blcheck=openClock(strEmpcn, strDateLamviec);
							if(!blcheck)
							{
								error = new ObjError();
								error.setIdError("Lỗi dòng " + (i + 1));
								error.setErrorStr("Lỗi khi mở khóa dữ liệu");
								listError.add(error);
								check = false;
							}
							//3: xu ly du lieu
							getJdbcDAO().processData(empsn,strDateLamviec, "mm");							
							//4: khoa lai cho tat ca cac bang
						//	clockData();
							
						}catch (Exception e) {
							// TODO: handle exception
							error=new ObjError();
							error.setErrorStr(e.toString());
							listError.add(error);
						    rturn=false;
						}
						
						rturn = true;
					}	
				
				}  // the end for (i)
			}
		}
			
		catch (Exception e) 
		{ 
			error=new ObjError();
			error.setErrorStr(e.toString());
			listError.add(error);
		    rturn=false;
		 
		}
		if(listError!= null && listError.size()>0){
			
			String strobj="";
			for(int i=0;i<listError.size();i++)
			{
			
			    ObjError  obj=listError.get(i);
				strobj = strobj+ obj.getIdError() +  " : " +  obj.getErrorStr()+".";
				errorTextArea.setText(strobj);
			}
			
		}else{
			errorTextArea.setText("CẬP NHẬT THÀNH CÔNG (" + (n_row - 1 ) +" dòng dữ liệu ) " ) ;
		}
		return rturn;
	}
	public String getStrTimeout() {
		return StrTimeout;
	}
	public void setStrTimeout(String strTimeout) {
		StrTimeout = strTimeout;
	}
	// mo khoa
	private boolean openClock(String empsn, String date)
	{
		boolean checkopen=true;
		Date dtNow=new Date();
		String strdtNow=sp.format(dtNow).substring(3, 10);
		String strdtDate=date.substring(3, 10);
		String strMonth=strdtDate.substring(0, 2);
		String strYear=strdtDate.substring(3, 7);
		if(!strdtNow.equals("")&& !strdtDate.equals(""))
		{
			if(strdtNow.equals(strdtDate))
			{
				objdaoN_get_data=Application.getApp().getDao(N_GET_DATA.class);
				listN_get_data=objdaoN_get_data.find(1, "from N_GET_DATA where EMPSN=? and MONTHS=? and YEARS=? and LOCKED=1",new Object[]{empsn,strMonth,strYear});
				objdaoEmployee =Application.getApp().getDao(N_EMPLOYEE.class);
				listEmployee =objdaoEmployee.find(1,"from N_EMPLOYEE where EMPSN=?" , empsn);
				objdaoN_fact_trans_lock=Application.getApp().getDao(N_FACT_TRANSFER_LOCK.class);				
				objdaoAttLock=Application.getApp().getDao(ATTLOCK.class);
				listAttlock=objdaoAttLock.find(1, "from ATTLOCK where TABNAME=? and TABLOCK='Y'","ATT"+strYear+strMonth);				
				if(listN_get_data.size()>0)
				{
					obj_N_get_data=listN_get_data.get(0);
					obj_N_get_data.setLOCKED(BigDecimal.valueOf(0));
					objdaoN_get_data.update(obj_N_get_data);
				}
				if(listEmployee.size()>0)
				{
					 objEmployee=listEmployee.get(0);
					 dept=objEmployee.getDEPSN();
					 dept_temp=objEmployee.getDEPSN_TEMP();					
				}
				list_fact_lock=objdaoN_fact_trans_lock.find(2, " from N_FACT_TRANSFER_LOCK where DEPSN in ('"+dept+"','"+dept_temp+"') and YEAR=? and MONTH=? and LOCKED=1", new Object[]{strYear,strMonth});
				if(list_fact_lock.size()>0)
				{	
					objFact_lock_1=list_fact_lock.get(0);
					objFact_lock_1.setLOCKED(0L);
					objdaoN_fact_trans_lock.update(objFact_lock_1);	
					if(!dept.equals(dept_temp))
					{
					    objFact_lock_2=list_fact_lock.get(1);						
						objFact_lock_2.setLOCKED(0L);											
						objdaoN_fact_trans_lock.update(objFact_lock_2);
					}
				}				
				// mo khoa bang luong				
				if(listAttlock.size()>0)
				{
					objAttlock=listAttlock.get(0);
					objAttlock.setTABLOCK("N");
					objdaoAttLock.update(objAttlock);
				}
			}
			checkopen=true;
		}
		else
			checkopen=false;
		
		
		return checkopen;
		
		
	}
	// khoa du lieu
	private void clockData()
	{
		if(obj_N_get_data!=null)
		{
			obj_N_get_data.setLOCKED(BigDecimal.valueOf(1));
			objdaoN_get_data.update(obj_N_get_data);	
		}			
		// khoa du lieu bang N_FACT_TRANSFER_LOCK
		if(objFact_lock_1!=null)
		{
			objFact_lock_1.setLOCKED(1L);
			objdaoN_fact_trans_lock.update(objFact_lock_1);						
		}				
		if(objFact_lock_2!=null)
		{
			objFact_lock_2.setLOCKED(1L);											
			objdaoN_fact_trans_lock.update(objFact_lock_2);
			
		}
		if(objAttlock!=null)
		{
			objAttlock.setTABLOCK("Y");
			objdaoAttLock.update(objAttlock);
		}
	}
	
	private int randomRanger(int from,int to){
		Random ran = new Random();
		int random = ran.nextInt(to-from+1)+from;		
		return random;
	}
	private String getTimeOut(String strtimeOut_)
	{
		return strtimeOut_;
	}
	 
	private String  getTimeChange(String strID_SHIFT)
	{
		// KHONG TANG CA
		
	if(strID_SHIFT.equals("VS1"))
		{
			
			if(intTimein<730&& intTimeout>1530)
			{
				intTimein=730;
				intTimeout=1530;
			}
			if(intTimein<730&& intTimeout<1530)
			{
				intTimein=730;
				intTimeout=1530;
			}
			if(intTimein==730&& intTimeout>1530)
			{
				intTimein=730;
				intTimeout=1530;
			}
			if(intTimein<730&& intTimeout==1530)
			{
				intTimein=730;
				intTimeout=1530;
			}
			if(intTimein==intTimeout)
			{
				intTimein=730;
				intTimeout=1530;
			}
			if(intTimein>730&& intTimeout>1530)
			{
				intTimein=730;
				intTimeout=1530;
			}
			if(intTimein>730&& intTimeout>1530)
			{
				intTimein=730;
				intTimeout=1530;
			}
			
			
			
		}	
		
		if(strID_SHIFT.equals("SPK"))
		{
			
			if(intTimein<700&& intTimeout>1600)
			{
				intTimein=700;
				intTimeout=1600;
			}
			if(intTimein<700&& intTimeout<1600)
			{
				intTimein=700;
				intTimeout=1600;
			}
			if(intTimein==700&& intTimeout>1600)
			{
				intTimein=700;
				intTimeout=1600;
			}
			if(intTimein<700&& intTimeout==1600)
			{
				intTimein=700;
				intTimeout=1600;
			}
			if(intTimein==intTimeout)
			{
				intTimein=700;
				intTimeout=1600;
			}
			if(intTimein>700&& intTimeout>1600)
			{
				intTimein=700;
				intTimeout=1600;
			}
			if(intTimein>700&& intTimeout>1600)
			{
				intTimein=700;
				intTimeout=1600;
			}
			
			
			
		}	
		
		if(strID_SHIFT.equals("DB2"))
		{
			
			if(intTimein<700&& intTimeout>1400)
			{
				intTimein=700;
				intTimeout=1400;
			}
			if(intTimein<700&& intTimeout<1400)
			{
				intTimein=700;
				intTimeout=1400;
			}
			if(intTimein==700&& intTimeout>1400)
			{
				intTimein=700;
				intTimeout=1400;
			}
			if(intTimein<700&& intTimeout==1400)
			{
				intTimein=700;
				intTimeout=1400;
			}
			if(intTimein==intTimeout)
			{
				intTimein=700;
				intTimeout=1400;
			}
			if(intTimein>700&& intTimeout>1400)
			{
				intTimein=700;
				intTimeout=1400;
			}
			if(intTimein>700&& intTimeout>1400)
			{
				intTimein=700;
				intTimeout=1400;
			}
			
			
			
		}	
		if(strID_SHIFT.equals("DB1"))
		{
			
			if(intTimein<600&& intTimeout>1300)
			{
				intTimein=600;
				intTimeout=1300;
			}
			if(intTimein<600&& intTimeout<1300)
			{
				intTimein=600;
				intTimeout=1300;
			}
			if(intTimein==600&& intTimeout>1300)
			{
				intTimein=600;
				intTimeout=1300;
			}
			if(intTimein<600&& intTimeout==1300)
			{
				intTimein=600;
				intTimeout=1300;
			}
			if(intTimein==intTimeout)
			{
				intTimein=600;
				intTimeout=1300;
			}
			if(intTimein>600&& intTimeout>1300)
			{
				intTimein=600;
				intTimeout=1300;
			}
			if(intTimein>600&& intTimeout>1300)
			{
				intTimein=600;
				intTimeout=1300;
			}
			
			
			
		}	
		if(strID_SHIFT.equals("CA1"))
		{
			
			if(intTimein<600&& intTimeout>1400)
			{
				intTimein=600;
				intTimeout=1400;
			}
			if(intTimein<600&& intTimeout<1400)
			{
				intTimein=600;
				intTimeout=1400;
			}
			if(intTimein==600&& intTimeout>1400)
			{
				intTimein=600;
				intTimeout=1400;
			}
			if(intTimein<600&& intTimeout==1400)
			{
				intTimein=600;
				intTimeout=1400;
			}
			if(intTimein==intTimeout)
			{
				intTimein=600;
				intTimeout=1400;
			}
			if(intTimein>600&& intTimeout>1400)
			{
				intTimein=600;
				intTimeout=1400;
			}
			if(intTimein>600&& intTimeout>1400)
			{
				intTimein=600;
				intTimeout=1400;
			}
			
			
			
		}	
		if(strID_SHIFT.equals("CA2"))
		{
			
			if(intTimein<1400&& intTimeout>2200)
			{
				intTimein=1400;
				intTimeout=2200;
			}
			if(intTimein<1400&& intTimeout<2200)
			{
				intTimein=1400;
				intTimeout=2200;
			}
			if(intTimein==1400&& intTimeout>2200)
			{
				intTimein=1400;
				intTimeout=2200;
			}
			if(intTimein<1400&& intTimeout==2200)
			{
				intTimein=1400;
				intTimeout=2200;
			}
			if(intTimein==intTimeout)
			{
				intTimein=1400;
				intTimeout=2200;
			}
			if(intTimein>1400&& intTimeout>2200)
			{
				intTimein=1400;
				intTimeout=2200;
			}
			if(intTimein>1400&& intTimeout>2200)
			{
				intTimein=1400;
				intTimeout=2200;
			}
			
			
			
		}	
		if(strID_SHIFT.equals("CA3"))
		{
			
			if(intTimein<630&& intTimeout>2200)
			{
				intTimein=630;
				intTimeout=2200;
			}
			if(intTimein<630&& intTimeout<2200)
			{
				intTimein=630;
				intTimeout=2200;
			}
			if(intTimein==630&& intTimeout>2200)
			{
				intTimein=630;
				intTimeout=2200;
			}
			if(intTimein<630&& intTimeout==2200)
			{
				intTimein=630;
				intTimeout=2200;
			}
			if(intTimein==intTimeout)
			{
				intTimein=630;
				intTimeout=2200;
			}
			if(intTimein>630&& intTimeout>2200)
			{
				intTimein=630;
				intTimeout=2200;
			}
			if(intTimein>630&& intTimeout==2200)
			{
				intTimein=630;
				intTimeout=2200;
			}
			
			
		}	

		
		if(strID_SHIFT.equals("C25"))
		{
			
			if(intTimein<1345&& intTimeout>2145)
			{
				intTimein=1345;
				intTimeout=2145;
			}
			if(intTimein<1345&& intTimeout<2145)
			{
				intTimein=1345;
				intTimeout=2145;
			}
			if(intTimein==1345&& intTimeout>2145)
			{
				intTimein=1345;
				intTimeout=2145;
			}
			if(intTimein<1345&& intTimeout==2145)
			{
				intTimein=1345;
				intTimeout=2145;
			}
			if(intTimein==intTimeout)
			{
				intTimein=1345;
				intTimeout=2145;
			}
			if(intTimein>1345&& intTimeout>2145)
			{
				intTimein=1345;
				intTimeout=2145;
			}
			
			
		}	

		if(strID_SHIFT.equals("C22"))
		{
			
			if(intTimein<1300&& intTimeout>2100)
			{
				intTimein=1300;
				intTimeout=2100;
			}
			if(intTimein<1300&& intTimeout<2100)
			{
				intTimein=1300;
				intTimeout=2100;
			}
			if(intTimein==1300&& intTimeout>2100)
			{
				intTimein=1300;
				intTimeout=2100;
			}
			if(intTimein<1300&& intTimeout==2100)
			{
				intTimein=1300;
				intTimeout=2100;
			}
			if(intTimein==intTimeout)
			{
				intTimein=1300;
				intTimeout=2100;
			}
			if(intTimein>1300&& intTimeout>2100)
			{
				intTimein=1300;
				intTimeout=2100;
			}
			if(intTimein>1300&& intTimeout>2100)
			{
				intTimein=1300;
				intTimeout=2100;
			}
			
			
			
		}	
		 
		if(strID_SHIFT.equals("C15"))
		{
			
			if(intTimein<545&& intTimeout>1345)
			{
				intTimein=545;
				intTimeout=1345;
			}
			if(intTimein<545&& intTimeout<1345)
			{
				intTimein=545;
				intTimeout=1345;
			}
			if(intTimein==545&& intTimeout>1345)
			{
				intTimein=545;
				intTimeout=1345;
			}
			if(intTimein<545&& intTimeout==1345)
			{
				intTimein=545;
				intTimeout=1345;
			}
			if(intTimein==intTimeout)
			{
				intTimein=545;
				intTimeout=1345;
			}
			if(intTimein>545&& intTimeout>1345)
			{
				intTimein=545;
				intTimeout=1345;
			}
			
		}	
		if(strID_SHIFT.equals("C12"))
			{
				
				if(intTimein<500&& intTimeout>1300)
				{
					intTimein=500;
					intTimeout=1300;
				}
				if(intTimein<500&& intTimeout<1300)
				{
					intTimein=500;
					intTimeout=1300;
				}
				if(intTimein==500&& intTimeout>1300)
				{
					intTimein=500;
					intTimeout=1300;
				}
				if(intTimein<500&& intTimeout==1300)
				{
					intTimein=500;
					intTimeout=1300;
				}
				if(intTimein==intTimeout)
				{
					intTimein=500;
					intTimeout=1300;
				}
				if(intTimein>500&& intTimeout>1300)
				{
					intTimein=500;
					intTimeout=1300;
				}
			}	
		if(strID_SHIFT.equals("C13"))
		{
			
			if(intTimein<645&& intTimeout>1345)
			{
				intTimein=645;
				intTimeout=1345;
			}
			if(intTimein<645&& intTimeout<1345)
			{
				intTimein=645;
				intTimeout=1345;
			}
			if(intTimein==645&& intTimeout>1345)
			{
				intTimein=645;
				intTimeout=1345;
			}
			if(intTimein<645&& intTimeout==1345)
			{
				intTimein=645;
				intTimeout=1345;
			}
			if(intTimein==intTimeout)
			{
				intTimein=645;
				intTimeout=1345;
			}
			if(intTimein>645&& intTimeout>1345)
			{
				intTimein=645;
				intTimeout=1345;
			}
		}	
		
			if(strID_SHIFT.equals("C32"))
			{
				if(intTimeout==2130&&intTimein>530)
				{

					intTimein=530;
					intTimeout=2130;
				}
				if(intTimeout==2130&&intTimein==530)
				{

					intTimein=530;
					intTimeout=2130;
				}

				if(intTimeout<2130&& intTimein==530)
				{
					intTimein=530;
					intTimeout=2130;
				}
				
				if(intTimeout<=2130&& intTimein>530)
				{
					intTimein=530;
					intTimeout=2130;
				}
				if(intTimeout<2130&& intTimein<530)
				{
					intTimein=530;
					intTimeout=2130;
				}
				if(intTimeout>2130&& intTimein>530)
				{
					intTimein=530;
					intTimeout=2130;
				}
			}	
			if(strID_SHIFT.equals("C35"))
			{
				
				if(intTimeout<2215&& intTimein>615)
				{
					intTimein=615;
					intTimeout=2215;
				}
				if(intTimeout<2215&& intTimein<615)
				{
					intTimein=615;
					intTimeout=2215;
				}
				if(intTimeout==2215&&intTimein>615)
				{

					intTimein=615;
					intTimeout=2215;
				}
				if(intTimeout==2215&&intTimein==615)
				{

					intTimein=615;
					intTimeout=2215;
				}

				if(intTimeout<2215&& intTimein==530)
				{
					intTimein=615;
					intTimeout=2215;
				}
				if(intTimeout>2215&& intTimein>530)
				{
					intTimein=615;
					intTimeout=2215;
				}
				
			}
			
			if(strID_SHIFT.equals("HC1"))
			{
				
				if(intTimein<730&& intTimeout>1630)
				{
					intTimein=730;
					intTimeout=1630;
				}
				if(intTimein<730&& intTimeout<1630)
				{

					intTimein=730;
					intTimeout=1630;
				}
				if(intTimeout<730&& intTimein==1630)
				{
					intTimein=730;
					intTimeout=1630;
				}
				if(intTimeout>730&& intTimein>1630)
				{
					intTimein=730;
					intTimeout=1630;
				}
				if(intTimeout==intTimein)
				{

					intTimein=730;
					intTimeout=1630;
				}
				if(intTimeout>1630&&intTimein==730)
				{

					intTimein=730;
					intTimeout=1630;
				}
				if(intTimeout<1630&&intTimein==730)
				{

					intTimein=730;
					intTimeout=1630;
				}
				
			}	
		
		// TANG CA
/*		
			if(strID_SHIFT.equals("VS1"))
			{
				
				if(intTimein<730&& intTimeout>1600)
				{
					intTimein=730;
					intTimeout=1600;
				}
				if(intTimein<730&& intTimeout<1600)
				{
					intTimein=730;
					intTimeout=1600;
				}
				if(intTimein==730&& intTimeout>1600)
				{
					intTimein=730;
					intTimeout=1600;
				}
				if(intTimein<730&& intTimeout==1600)
				{
					intTimein=730;
					intTimeout=1600;
				}
				if(intTimein==intTimeout)
				{
					intTimein=730;
					intTimeout=1600;
				}
				if(intTimein>730&& intTimeout>1600)
				{
					intTimein=730;
					intTimeout=1600;
				}
				if(intTimein>730&& intTimeout>1600)
				{
					intTimein=730;
					intTimeout=1600;
				}
				
				
				
			}	
			
			if(strID_SHIFT.equals("SPK"))
			{
				
				if(intTimein<700&& intTimeout>1630)
				{
					intTimein=700;
					intTimeout=1630;
				}
				if(intTimein<700&& intTimeout<1630)
				{
					intTimein=700;
					intTimeout=1630;
				}
				if(intTimein==700&& intTimeout>1630)
				{
					intTimein=700;
					intTimeout=1630;
				}
				if(intTimein<700&& intTimeout==1630)
				{
					intTimein=700;
					intTimeout=1630;
				}
				if(intTimein==intTimeout)
				{
					intTimein=700;
					intTimeout=1630;
				}
				if(intTimein>700&& intTimeout>1630)
				{
					intTimein=700;
					intTimeout=1630;
				}
				if(intTimein>700&& intTimeout>1630)
				{
					intTimein=700;
					intTimeout=1630;
				}
				
				
				
			}	
			
			if(strID_SHIFT.equals("DB2"))
			{
				
				if(intTimein<700&& intTimeout>1430)
				{
					intTimein=700;
					intTimeout=1430;
				}
				if(intTimein<700&& intTimeout<1430)
				{
					intTimein=700;
					intTimeout=1430;
				}
				if(intTimein==700&& intTimeout>1430)
				{
					intTimein=700;
					intTimeout=1430;
				}
				if(intTimein<700&& intTimeout==1430)
				{
					intTimein=700;
					intTimeout=1430;
				}
				if(intTimein==intTimeout)
				{
					intTimein=700;
					intTimeout=1430;
				}
				if(intTimein>700&& intTimeout>1430)
				{
					intTimein=700;
					intTimeout=1430;
				}
				if(intTimein>700&& intTimeout>1430)
				{
					intTimein=700;
					intTimeout=1430;
				}
				
				
				
			}	
			if(strID_SHIFT.equals("DB1"))
			{
				
				if(intTimein<600&& intTimeout>1330)
				{
					intTimein=600;
					intTimeout=1330;
				}
				if(intTimein<600&& intTimeout<1330)
				{
					intTimein=600;
					intTimeout=1330;
				}
				if(intTimein==600&& intTimeout>1330)
				{
					intTimein=600;
					intTimeout=1330;
				}
				if(intTimein<600&& intTimeout==1330)
				{
					intTimein=600;
					intTimeout=1330;
				}
				if(intTimein==intTimeout)
				{
					intTimein=600;
					intTimeout=1330;
				}
				if(intTimein>600&& intTimeout>1330)
				{
					intTimein=600;
					intTimeout=1330;
				}
				if(intTimein>600&& intTimeout>1330)
				{
					intTimein=600;
					intTimeout=1330;
				}
				
				
				
			}	
			if(strID_SHIFT.equals("CA1"))
			{
				
				if(intTimein<600&& intTimeout>1430)
				{
					intTimein=600;
					intTimeout=1430;
				}
				if(intTimein<600&& intTimeout<1430)
				{
					intTimein=600;
					intTimeout=1430;
				}
				if(intTimein==600&& intTimeout>1430)
				{
					intTimein=600;
					intTimeout=1430;
				}
				if(intTimein<600&& intTimeout==1430)
				{
					intTimein=600;
					intTimeout=1430;
				}
				if(intTimein==intTimeout)
				{
					intTimein=600;
					intTimeout=1430;
				}
				if(intTimein>600&& intTimeout>1430)
				{
					intTimein=600;
					intTimeout=1430;
				}
				if(intTimein>600&& intTimeout>1430)
				{
					intTimein=600;
					intTimeout=1430;
				}
				
				
				
			}	
			if(strID_SHIFT.equals("CA2"))
			{
				
				if(intTimein<1430&& intTimeout>2230)
				{
					intTimein=1400;
					intTimeout=2230;
				}
				if(intTimein<1400&& intTimeout<2230)
				{
					intTimein=1400;
					intTimeout=2230;
				}
				if(intTimein==1400&& intTimeout>2230)
				{
					intTimein=1400;
					intTimeout=2230;
				}
				if(intTimein<1400&& intTimeout==2230)
				{
					intTimein=1400;
					intTimeout=2230;
				}
				if(intTimein==intTimeout)
				{
					intTimein=1400;
					intTimeout=2230;
				}
				if(intTimein>1400&& intTimeout>2230)
				{
					intTimein=1400;
					intTimeout=2230;
				}
				if(intTimein>1400&& intTimeout>2230)
				{
					intTimein=1400;
					intTimeout=2230;
				}
				
				
				
			}	
			if(strID_SHIFT.equals("CA3"))
			{
				
				if(intTimein<630&& intTimeout>2200)
				{
					intTimein=630;
					intTimeout=2200;
				}
				if(intTimein<630&& intTimeout<2200)
				{
					intTimein=630;
					intTimeout=2200;
				}
				if(intTimein==630&& intTimeout>2200)
				{
					intTimein=630;
					intTimeout=2200;
				}
				if(intTimein<630&& intTimeout==2200)
				{
					intTimein=630;
					intTimeout=2200;
				}
				if(intTimein==intTimeout)
				{
					intTimein=630;
					intTimeout=2200;
				}
				if(intTimein>630&& intTimeout>2200)
				{
					intTimein=630;
					intTimeout=2200;
				}
				if(intTimein>630&& intTimeout==2200)
				{
					intTimein=630;
					intTimeout=2200;
				}
				
				
			}	

			
			if(strID_SHIFT.equals("C25"))
			{
				
				if(intTimein<1345&& intTimeout>2215)
				{
					intTimein=1345;
					intTimeout=2215;
				}
				if(intTimein<1345&& intTimeout<2215)
				{
					intTimein=1345;
					intTimeout=2215;
				}
				if(intTimein==1345&& intTimeout>2215)
				{
					intTimein=1345;
					intTimeout=2215;
				}
				if(intTimein<1345&& intTimeout==2215)
				{
					intTimein=1345;
					intTimeout=2215;
				}
				if(intTimein==intTimeout)
				{
					intTimein=1345;
					intTimeout=2215;
				}
				if(intTimein>1345&& intTimeout>2215)
				{
					intTimein=1345;
					intTimeout=2215;
				}
				
				
			}	

			if(strID_SHIFT.equals("C22"))
			{
				
				if(intTimein<1300&& intTimeout>2130)
				{
					intTimein=1300;
					intTimeout=2130;
				}
				if(intTimein<1300&& intTimeout<2130)
				{
					intTimein=1300;
					intTimeout=2130;
				}
				if(intTimein==1300&& intTimeout>2130)
				{
					intTimein=1300;
					intTimeout=2130;
				}
				if(intTimein<1300&& intTimeout==2130)
				{
					intTimein=1300;
					intTimeout=2130;
				}
				if(intTimein==intTimeout)
				{
					intTimein=1300;
					intTimeout=2130;
				}
				if(intTimein>1300&& intTimeout>2130)
				{
					intTimein=1300;
					intTimeout=2130;
				}
				if(intTimein>1300&& intTimeout>2130)
				{
					intTimein=1300;
					intTimeout=2130;
				}
				
				
				
			}	
			 
			if(strID_SHIFT.equals("C15"))
			{
				
				if(intTimein<545&& intTimeout>1415)
				{
					intTimein=545;
					intTimeout=1415;
				}
				if(intTimein<545&& intTimeout<1415)
				{
					intTimein=545;
					intTimeout=1415;
				}
				if(intTimein==545&& intTimeout>1415)
				{
					intTimein=545;
					intTimeout=1415;
				}
				if(intTimein<545&& intTimeout==1415)
				{
					intTimein=545;
					intTimeout=1415;
				}
				if(intTimein==intTimeout)
				{
					intTimein=545;
					intTimeout=1415;
				}
				if(intTimein>545&& intTimeout>1415)
				{
					intTimein=545;
					intTimeout=1415;
				}
				
				
			}	
			if(strID_SHIFT.equals("C12"))
				{
					
					if(intTimein<500&& intTimeout>1330)
					{
						intTimein=500;
						intTimeout=1330;
					}
					if(intTimein<500&& intTimeout<1330)
					{
						intTimein=500;
						intTimeout=1330;
					}
					if(intTimein==500&& intTimeout>1330)
					{
						intTimein=500;
						intTimeout=1330;
					}
					if(intTimein<500&& intTimeout==1330)
					{
						intTimein=500;
						intTimeout=1330;
					}
					if(intTimein==intTimeout)
					{
						intTimein=500;
						intTimeout=1330;
					}
					if(intTimein>500&& intTimeout>1330)
					{
						intTimein=500;
						intTimeout=1330;
					}
					
					
				}	
			if(strID_SHIFT.equals("C13"))
			{
				
				if(intTimein<645&& intTimeout>1415)
				{
					intTimein=645;
					intTimeout=1415;
				}
				if(intTimein<645&& intTimeout<1415)
				{
					intTimein=645;
					intTimeout=1415;
				}
				if(intTimein==645&& intTimeout>1415)
				{
					intTimein=645;
					intTimeout=1415;
				}
				if(intTimein<645&& intTimeout==1415)
				{
					intTimein=645;
					intTimeout=1415;
				}
				if(intTimein==intTimeout)
				{
					intTimein=645;
					intTimeout=1415;
				}
				if(intTimein>645&& intTimeout>1415)
				{
					intTimein=645;
					intTimeout=1415;
				}
				
				
			}	
			
				if(strID_SHIFT.equals("C32"))
				{
					if(intTimeout==2100&&intTimein>530)
					{

						intTimein=530;
						intTimeout=2100;
					}
					if(intTimeout==2100&&intTimein==530)
					{

						intTimein=530;
						intTimeout=2100;
					}

					if(intTimeout<2100&& intTimein==530)
					{
						intTimein=530;
						intTimeout=2100;
					}
					
					if(intTimeout<=2100&& intTimein>530)
					{
						intTimein=530;
						intTimeout=2100;
					}
					if(intTimeout<2100&& intTimein<530)
					{
						intTimein=530;
						intTimeout=2100;
					}
					if(intTimeout>2100&& intTimein>530)
					{
						intTimein=530;
						intTimeout=2100;
					}
					
				}	
				if(strID_SHIFT.equals("C35"))
				{
					
					if(intTimeout<2245&& intTimein>615)
					{
						intTimein=615;
						intTimeout=2245;
					}
					if(intTimeout<2245&& intTimein<615)
					{
						intTimein=615;
						intTimeout=2245;
					}
					if(intTimeout==2245&&intTimein>615)
					{

						intTimein=615;
						intTimeout=2245;
					}
					if(intTimeout==2245&&intTimein==615)
					{

						intTimein=615;
						intTimeout=2245;
					}

					if(intTimeout<2245&& intTimein==530)
					{
						intTimein=615;
						intTimeout=2245;
					}
					if(intTimeout>2245&& intTimein>530)
					{
						intTimein=615;
						intTimeout=2245;
					}
					
				}
				
				if(strID_SHIFT.equals("HC1"))
				{
					
					if(intTimein<730&& intTimeout>1700)
					{
						intTimein=730;
						intTimeout=1700;
					}
					if(intTimein<730&& intTimeout<1700)
					{

						intTimein=730;
						intTimeout=1700;
					}
					if(intTimeout<730&& intTimein==1700)
					{
						intTimein=730;
						intTimeout=1700;
					}
					if(intTimeout>730&& intTimein>1700)
					{
						intTimein=730;
						intTimeout=1700;
					}
					if(intTimeout==intTimein)
					{

						intTimein=730;
						intTimeout=1700;
					}
					if(intTimeout>1700&&intTimein==730)
					{

						intTimein=730;
						intTimeout=1700;
					}
					if(intTimeout<1700&&intTimein==730)
					{

						intTimein=730;
						intTimeout=1700;
					}
					
				}	*/

		
			String StrTimeint_=String.valueOf(intTimein);
			String strTimeOut_=String.valueOf(intTimeout);
			if(StrTimeint_.length()<4)
			{
				StrTimeint_="0"+StrTimeint_;
			}
			if(strTimeOut_.length()<4)
			{
				strTimeOut_="0"+strTimeOut_;
			}
			this.setStrTimeout(strTimeOut_);
			
			
			
			return StrTimeint_;
	}
	
	public void getNthWeekDay () {
		  Calendar c = Calendar.getInstance();
		  DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		  String strdate="01/06/2013";
		   Calendar cal=Calendar.getInstance();	
		   try {
			Date startDate = df.parse(strdate);
			cal.setTimeInMillis(startDate.getTime());
		    c.setTimeInMillis(cal.getTimeInMillis());
			c.set(Calendar.DAY_OF_MONTH, -1);
			  int mondaysCount = 0;

			    while ( mondaysCount != 5 ) {
			        c.add( Calendar.DAY_OF_MONTH, 1 );
			        if ( c.get( Calendar.DAY_OF_WEEK ) == Calendar.MONDAY ) {
			            mondaysCount++; 
			            String str=sp.format(c.getTime());					    
					    System.out.println( str );
			        }       
			    }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  
	  
	}

	//	
	private String getMinTimeday(String strEmpsn, String  strDates)
	{
		
			
		IGenericDAO<N_DATA_MAIN, String> objdaoN_Data_main=Application.getApp().getDao(N_DATA_MAIN.class);
		String strEmpcn=doGetempsn(strEmpsn);
		List<N_DATA_MAIN> listData_main =objdaoN_Data_main.find(1, "from N_DATA_MAIN where EMPCN=? and DATES=?", new Object[]{strEmpcn,strDates});
		N_DATA_MAIN objData_main=null;
		if(listData_main.size()>0)
		{
			objData_main=listData_main.get(0);
			strTimes=objData_main.getTIMES();
			int[] inttemp=new int[100];
			int i,j,cdai,tem;
			 String so="";
			cdai=strTimes.length();
			for(i=0;i<cdai/4;i++)
			{   
				 so=strTimes.substring(i*4,(i+1)*4);
				 
				 inttemp[i]=Integer.parseInt(so);
				
			}
			// sap xep theo kieu tang dan
		   for(int a=0;a<cdai-1;a++)
			    {
			    	for(int b=0;b<i-1;b++)
			    	{
			    		if(inttemp[a]>inttemp[b])
			    		{
			    			  tem=inttemp[a];
				    	      inttemp[a]=inttemp[b];
				    	      inttemp[b]=tem;
			    		}
			    		  
			    	}
			    }
		   
		   intTimeout =inttemp[0];
		   intTimein=inttemp[(cdai/4)-1];
		   StrTimeint=String.valueOf( intTimein);
		   StrTimeout=String.valueOf( intTimeout);
		   intTimein=Integer.parseInt(StrTimeint);
		   if(StrTimeint.length()<4)
	     	{
			   StrTimeint="0"+StrTimeint;
			   intTimein=Integer.parseInt(StrTimeint);
	     	}
		   
	     	if(StrTimeout.length()<4)
	     	{
	     		StrTimeout="0"+StrTimeout;    
	     	}
		}
		
		return StrTimeint;
	}
	public  String  doGetempsn(String empsn)
	{
		IGenericDAO<N_EMP_ICCARD, String> objDao=Application.getApp().getDao(N_EMP_ICCARD.class);
		List<N_EMP_ICCARD> listIcCard=objDao.find(1," from N_EMP_ICCARD where EMPSN=? AND USE_STATUS='1' AND  END_DATE IS  NULL", empsn);
		N_EMP_ICCARD objN_EMP_ICCARD=null;		
		if(listIcCard.size()>0)
		{
			objN_EMP_ICCARD=listIcCard.get(0);					
		}
		return objN_EMP_ICCARD.getEMPCN();	
	}
	
	private boolean checkRegisterOverTime(String empsn, String strdate_Over)
	{
		IGenericDAO<N_REGISTER_OVERTIME, String> objdao=Application.getApp().getDao(N_REGISTER_OVERTIME.class);		
	    List<N_REGISTER_OVERTIME> listN_Register=objdao.find(1,"from N_REGISTER_OVERTIME where EMPSN=? AND DATE_OVER=to_date('"+strdate_Over+"','dd/mm/yyyy')", empsn);
			if(listN_Register.size()>0)
			{
				N_REGISTER_OVERTIME objN_register=listN_Register.get(0);
				if(objN_register!=null)
				{				
					return false;
				}
			}
		return true;
	}
	
	
	private void initComponent(){
		this.setTitle("Thay Đổi Dữ Liệu Quét Thẻ");
		this.setStyleName("Default.Window");
		this.setHeight(new Extent(320));
		this.setWidth(new Extent(500));
		rootLayout = new Grid();
		rootLayout.setSize(1);
		rootLayout.setForeground(Color.RED);
		rootLayout.add(getFileUploadExcel());			
		errorTextArea = new TextArea();
		errorTextArea.setEnabled(false);
		errorTextArea.setWidth(new Extent(450));
		errorTextArea.setHeight(new Extent(100));
		rootLayout.add(errorTextArea);
		
		infoLabel = new Label("CẬP NHẬT THÀNH CÔNG !");
		infoLabel.setVisible(false);
//		rootLayout.add(infoLabel);
		
		this.add(rootLayout);
	}

}
