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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import ds.program.fvhr.domain.N_BAOGIAM_STATUS;
import ds.program.fvhr.domain.N_EMP_INFO;
import ds.program.fvhr.domain.N_EMP_JOB_DETAIL;
import ds.program.fvhr.domain.N_EMP_QUIT;
import ds.program.fvhr.domain.N_HEALTH;
import ds.program.fvhr.domain.N_HOSPITAL;
import ds.program.fvhr.domain.N_JOB;
import ds.program.fvhr.domain.N_LABOUR;
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

public class N_Emp_Quit_Import extends WindowPane {

	private ResourceBundle bundle;
	
	Grid 		rootLayout ;
	TextArea 	errorTextArea;
	Label 		infoLabel;

	private RadioButton radnotBHYT;

	private RadioButton radBHYT;
	String user_up = Application.getApp().getLoginInfo().getUserID();
	String note="Update N_emp_Quit";
	 OBJ_EMPSN obj_e = new OBJ_EMPSN();
	
	public N_Emp_Quit_Import() {
		initComponent();
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
						boolean rturn; 
						if(radnotBHYT.isSelected())
						{
							rturn=importRadnotBHYT(outputFile);									
						}
						if(radBHYT.isSelected())
						{
							rturn=importRadBHYT(outputFile);
						}				
						
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
	private boolean importRadBHYT(File fileExcel)
	{
		boolean rturn=true;
		InputStream in;
		IGenericDAO<N_EMP_QUIT, String> objDao=Application.getApp().getDao(N_EMP_QUIT.class);
		N_EMP_QUIT objEmp_Quit;
		ObjUtility obj_utili=new ObjUtility();
		SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<ObjError> listError=new ArrayList<ObjError>();
		ObjError error;
		boolean check=true;
		HRUtils util=ApplicationHelper.getHRUtils();
		String empsn 		= "";
		Date dateThucNghi			= null;
		String THE_BHYT="";
		Date date_BHYT=null;// ngay tra the
		Date FROM_DATE =null;// giam tu ngay
		Date To_DATE =null; // giam den ngay
		Date date_Ngaybaogiam=null ; // ngay bao giam
		Date Thang_ThuBH =null;// thang tru bao hiem
		String giamthangmoi=""; // giam tăng mới
		String str_ngaybaogiam="";			
		
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
								
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Ngày thực nghĩ không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								// check exist of n_emp_quit
							
								dateThucNghi	= row.getCell(j).getDateCellValue();
								check=checkEmpsnandDate(empsn, dateThucNghi);
								if(check == false){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Bạn Chưa Nhập Thông Tin NV Cho Số Thẻ " +empsn);
									listError.add(error);
									continue;
								}
								break;
	
							case 2 : //0 la khong tra the, 1 la tra thẻ
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Giá trị trả thẻ không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								if((row.getCell(j).getStringCellValue().equals(null))
										|| (row.getCell(j).getStringCellValue().isEmpty()==false))
								{
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Giá trị trả thẻ không được để trống");
									listError.add(error);
									check = false;
									continue;
								}
								String test1=row.getCell(j).getStringCellValue();
								
								if(test1.equals("0"))
								{								
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Bạn đã chọn sai trạng thái");
									listError.add(error);
									check = false;
									continue;	
								}
								break;
								
								
								
							case 3 :
								if(row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Ngày Trả Thẻ không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									}
								}
								
								if(row.getCell(j)==null)
								{
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Ngày Trả Thẻ không được để trống");
									listError.add(error);
									check = false;
									continue;
								}
								
								date_BHYT	= row.getCell(j).getDateCellValue();
							
								break;
								
							case 4: 
								if(row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Ngày báo giảm không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									}
									
									date_Ngaybaogiam	= row.getCell(j).getDateCellValue();
									str_ngaybaogiam=df.format(date_Ngaybaogiam);
									check=checkBaogiamstatus(str_ngaybaogiam);
									if(!check)
									{
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Ngày báo giảm đã tồn tại");
										listError.add(error);
										check = false;
										continue;
									}
								}
								
								break;
								
							case 5 : // check ngày báo giảm
								if(date_Ngaybaogiam==null&& row.getCell(j)!=null)
								{
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Giảm từ  ngày phải không có dữ liệu");
									listError.add(error);
									check = false;
									continue;
								}
								
								if(date_Ngaybaogiam!=null&&row.getCell(j)==null)
									
								{
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Giảm từ ngày không có dữ liệu");
									listError.add(error);
									check = false;
									continue;
								}
								if(date_Ngaybaogiam!=null && row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Giảm từ ngày không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									}
									FROM_DATE	= row.getCell(j).getDateCellValue();									
								}
								break;
	
							case 6 : 
								if(date_Ngaybaogiam==null&& row.getCell(j)!=null)
								{
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Giảm đến ngày phải không có dữ liệu");
									listError.add(error);
									check = false;
									continue;
								}
								
								if(date_Ngaybaogiam!=null&&row.getCell(j)==null)
									
								{
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Giảm đến ngày không có dữ liệu");
									listError.add(error);
									check = false;
									continue;
								}
								if(date_Ngaybaogiam!=null && row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Giảm đến ngày không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									}
									To_DATE	= row.getCell(j).getDateCellValue();									
								}
								break;
							
							case 7 :
								if(row.getCell(j)!=null)
								{
									Thang_ThuBH=row.getCell(j).getDateCellValue();
									System.out.println(Thang_ThuBH);
									if(row.getCell(j).getCellType()!=HSSFCell.CELL_TYPE_NUMERIC)
									{
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Tháng trừ Bảo Hiểm không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									
										
									}
									Thang_ThuBH=row.getCell(j).getDateCellValue();
								}			
								break;
								
							case 8:
								if(row.getCell(j)!=null)
								{
									System.out.println(row.getCell(8).getStringCellValue());
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Giảm tăng mới không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									}
									//
									giamthangmoi=row.getCell(j).getStringCellValue();
									if(giamthangmoi.equals("Y"))
									{
										if(date_Ngaybaogiam!=null &&FROM_DATE!=null && To_DATE!=null)
										{
											error = new ObjError();
											error.setIdError("Lỗi dòng " + (i + 1));
											error.setErrorStr("Ngày báo giám, giảm từ ngày, giảm đến ngày phải để trống");
											listError.add(error);
											check = false;
											continue;
										}
									}
									if(giamthangmoi.equals("N"))
									{
										if(date_Ngaybaogiam==null &&FROM_DATE==null && To_DATE==null)
										{
											error = new ObjError();
											error.setIdError("Lỗi dòng " + (i + 1));
											error.setErrorStr("Ngày báo giảm, giảm từ ngày, giảm đến ngày phải có dữ liệu");
											listError.add(error);
											check = false;
											continue;
										}
									}
									
								}
								break;
	
							default:
								break;
							}
						
					} // the end for (j)
					
					
					if(check){
								// update row old\
							List<N_EMP_QUIT> listemp_Quit=objDao.find(1, "from N_EMP_QUIT where EMPSN=? AND REAL_OFF_DATE=?", empsn,dateThucNghi);
							if(listemp_Quit.size()>0)
							{	 objEmp_Quit = listemp_Quit.get(0);								 
								try
								{
									// không có báo giảm thì không trừ tháng BH
									if(FROM_DATE==null && To_DATE==null &&!giamthangmoi.equals("Y"))
									{
										Thang_ThuBH=null;	
									}
									objEmp_Quit.setTHE_BHYT(THE_BHYT.substring(0, 1));
									objEmp_Quit.setDATE_BHYT(date_BHYT);
									objEmp_Quit.setFROM_DATE(FROM_DATE);
									objEmp_Quit.setTO_DATE(To_DATE);
									objEmp_Quit.setTHANG_TRUBH(Thang_ThuBH);
									//update 
									objDao.update(objEmp_Quit);									
									// lưu vào bảng ActionDaily
									   
								 	obj_e.Insert_N_Action_Daily(user_up, "N_EMP_QUIT", "UPDATE", empsn, note);								
																 
								}catch (Exception e) {
									error=new ObjError();
									error.setErrorStr(e.toString());
									listError.add(error);
								    rturn=false;
								}
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
	
	
	private boolean importRadnotBHYT(File fileExcel)
	{
		boolean rturn=true;
		InputStream in;
		IGenericDAO<N_EMP_QUIT, String> objDao=Application.getApp().getDao(N_EMP_QUIT.class);
		N_EMP_QUIT objEmp_Quit;
		ObjUtility obj_utili=new ObjUtility();
		SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<ObjError> listError=new ArrayList<ObjError>();
		ObjError error;
		boolean check=true;
		HRUtils util=ApplicationHelper.getHRUtils();
		String empsn 		= "";
		Date dateThucNghi			= null;
		String THE_BHYT="";
		Date date_BHYT=null;// ngay tra the
		Date FROM_DATE =null;// giam tu ngay
		Date To_DATE =null; // giam den ngay
		Date date_Ngaybaogiam=null ; // ngay bao giam
		Date Thang_ThuBH =null;// thang tru bao hiem
		String giamthangmoi=""; // giam tăng mới
		String str_ngaybaogiam="";
				
		
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
								
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Ngày thực nghĩ không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								// check the exist of n_emp_quit								
								dateThucNghi	= row.getCell(j).getDateCellValue();
								check=checkEmpsnandDate(empsn, dateThucNghi);
								if(check == false){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Bạn Chưa Nhập Thông Tin NV Cho Số Thẻ " +empsn);
									listError.add(error);
									continue;
								}
								break;
	
							case 2 : //0 la khong tra the, 1 la tra thẻ
								
							
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Giá trị trả thẻ không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								String test1=row.getCell(j).getStringCellValue();
								
								if(test1.equals("1"))
								{
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Bạn đã chọn sai trạng thái");
									listError.add(error);
									check = false;
									continue;	
								}
								break;
								
								
								
							case 3 :
								if((row.getCell(j).getStringCellValue().equals(null))
										|| (row.getCell(j).getStringCellValue().isEmpty()==false))
								{
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Ngày trả thẻ không tồn tại");
									listError.add(error);
									check = false;
									continue;
								}								
							
								break;
								
							case 4: 
								if(row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Ngày báo giảm không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									}
									
									date_Ngaybaogiam	= row.getCell(j).getDateCellValue();
									str_ngaybaogiam=df.format(date_Ngaybaogiam);
									check=checkBaogiamstatus(str_ngaybaogiam);
									if(!check)
									{
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Ngày báo giảm đã tồn tại");
										listError.add(error);
										check = false;
										continue;
									}
								}
								
								break;
								
							case 5 : // check ngày báo giảm
								if(date_Ngaybaogiam==null&& row.getCell(j)!=null)
								{
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Giảm từ  ngày phải không có dữ liệu");
									listError.add(error);
									check = false;
									continue;
								}
								
								if(date_Ngaybaogiam!=null&&row.getCell(j)==null)
									
								{
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Giảm từ ngày không có dữ liệu");
									listError.add(error);
									check = false;
									continue;
								}
								if(date_Ngaybaogiam!=null && row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Giảm từ ngày không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									}
									FROM_DATE	= row.getCell(j).getDateCellValue();									
								}
								break;
	
							case 6 : 
								if(date_Ngaybaogiam==null&& row.getCell(j)!=null)
								{
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Giảm đến ngày phải không có dữ liệu");
									listError.add(error);
									check = false;
									continue;
								}								
								if(date_Ngaybaogiam!=null&&row.getCell(j)==null)
								{
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Giảm đến ngày không có dữ liệu");
									listError.add(error);
									check = false;
									continue;
								}
								if(date_Ngaybaogiam!=null && row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Giảm đến ngày không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									}
									To_DATE	= row.getCell(j).getDateCellValue();									
								}
								break;
							
							case 7 :
								if(row.getCell(j)!=null)
								{
									Thang_ThuBH=row.getCell(j).getDateCellValue();
									System.out.println(Thang_ThuBH);
									if(row.getCell(j).getCellType()!=HSSFCell.CELL_TYPE_NUMERIC)
									{
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Tháng trừ Bảo Hiểm không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									
										
									}
									Thang_ThuBH=row.getCell(j).getDateCellValue();
								}			
								break;
								
							case 8:
								if(row.getCell(j)!=null)
								{
									System.out.println(row.getCell(8).getStringCellValue());
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Giảm tăng mới không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									}
									//
									giamthangmoi=row.getCell(j).getStringCellValue();
									if(giamthangmoi.equals("Y"))
									{
										if(date_Ngaybaogiam!=null &&FROM_DATE!=null && To_DATE!=null)
										{
											error = new ObjError();
											error.setIdError("Lỗi dòng " + (i + 1));
											error.setErrorStr("Ngày báo giám, giảm từ ngày, giảm đến ngày phải để trống");
											listError.add(error);
											check = false;
											continue;
										}
									}
									if(giamthangmoi.equals("N"))
									{
										if(date_Ngaybaogiam==null &&FROM_DATE==null && To_DATE==null)
										{
											error = new ObjError();
											error.setIdError("Lỗi dòng " + (i + 1));
											error.setErrorStr("Ngày báo giảm, giảm từ ngày, giảm đến ngày phải có dữ liệu");
											listError.add(error);
											check = false;
											continue;
										}
									}
									
								}
								break;
	
							default:
								break;
							}
						
					} // the end for (j)
					
					
					if(check){
								// update row old\
							List<N_EMP_QUIT> listemp_Quit=objDao.find(1, "from N_EMP_QUIT where EMPSN=? AND REAL_OFF_DATE=?", empsn,dateThucNghi);
							if(listemp_Quit.size()>0)
							{	 objEmp_Quit = listemp_Quit.get(0);								 
								try
								{
									// không có báo giảm thì không trừ tháng BH
									if(FROM_DATE==null && To_DATE==null &&!giamthangmoi.equals("Y"))
									{
										Thang_ThuBH=null;	
									}
									
									objEmp_Quit.setTHE_BHYT(THE_BHYT.substring(0,1));								
									objEmp_Quit.setFROM_DATE(FROM_DATE);
									objEmp_Quit.setTO_DATE(To_DATE);
									objEmp_Quit.setTHANG_TRUBH(Thang_ThuBH);
									//update 
									objDao.update(objEmp_Quit);									
									// lưu vào bảng ActionDaily
									   
								 	obj_e.Insert_N_Action_Daily(user_up, "N_EMP_QUIT", "UPDATE", empsn, note);									
																 
								}catch (Exception e) {
									error=new ObjError();
									error.setErrorStr(e.toString());
									listError.add(error);
								    rturn=false;
								}
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
	
	private boolean checkBaogiamstatus(String strNgaybaogiam)
	{
		
		IGenericDAO<N_BAOGIAM_STATUS, String> objDao=Application.getApp().getDao(N_BAOGIAM_STATUS.class);
		List<N_BAOGIAM_STATUS> listBaogiamStatus=objDao.find(1, "from N_BAOGIAM_STATUS where NGAY_BG=to_date('"+strNgaybaogiam+"','dd/MM/yyyy') and STATUS=1 AND LOAI_BG='5'");
		if(listBaogiamStatus.size()>0)
		{
			return false;
		}
				
		return true;
	}
	private boolean checkEmpsnandDate(String empsn, Date date)
	{
		IGenericDAO<N_EMP_QUIT, String> objDao=Application.getApp().getDao(N_EMP_QUIT.class);
		List<N_EMP_QUIT> listEmpQuit=objDao.find(1,"from N_EMP_QUIT where EMPSN=?  AND REAL_OFF_DATE=?" , empsn,date);
		if(listEmpQuit.size()==0)
		{
			return false;
		}
		return true;
	}
	
	private void initComponent(){
		this.setTitle("IMPORT");
		this.setStyleName("Default.Window");
		this.setHeight(new Extent(320));
		this.setWidth(new Extent(500));
		rootLayout = new Grid();
		rootLayout.setSize(1);
		rootLayout.setForeground(Color.RED);
		rootLayout.add(getFileUploadExcel());
		
		ButtonGroup btGroup=new ButtonGroup();
		radBHYT=new RadioButton();
		radBHYT.setGroup(btGroup);
		radBHYT	.setText("Nghĩ Việc Có Trả Thẻ BHYT");
		rootLayout.add(radBHYT);		
		radnotBHYT=new RadioButton();
		radnotBHYT.setGroup(btGroup);
		radnotBHYT.setText("Nghĩ Việc Không Trả Thẻ BHYT");
		rootLayout.add(radnotBHYT);		
		rootLayout.add(new Label("	* Ghi chú : tập tin cập nhật phải là file excel (*.xls)"));
		rootLayout.add(new Label("	* Tập tin được định dạng 8 cột : Số thẻ - Ngày thực nghỉ - Thẻ BHYT (Trả =1,Không trả =0) Định Dạng Text - Ngày Trả Thẻ - Ngày Báo Giảm " +
				"- Giảm Từ Ngày - Giảm Đến Ngày - Tháng trừ BH - Giảm Tăng Mới (Y là giảm Tăng Mới, N Là Giảm Tuần). Định Dạng ngày tháng kiểu Date ('DD/MM/YYYY') - "));
		//X:\FV1\IT\Son\import excel template
		rootLayout.add(new Label("__________________"));
		
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
