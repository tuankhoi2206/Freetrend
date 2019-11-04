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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
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
import ds.program.fvhr.domain.DSHR71;
import ds.program.fvhr.domain.N_BAOGIAM_STATUS;
import ds.program.fvhr.domain.N_DATA_MAIN;
import ds.program.fvhr.domain.N_DEPARTMENT;
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
import ds.program.fvhr.domain.pk.DSHR71Pk;
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

public class DSHR71_EXCCEL extends WindowPane {

	private ResourceBundle bundle;	
    private 	Grid 		rootLayout ;
    private TextArea 	errorTextArea;
    private Label 		infoLabel;
    private Connection con 	= Application.getApp().getConnection();
    private CallableStatement stm_call;
	private RadioButton radnotBHYT;
	private RadioButton radBHYT;
	private String useLogin = Application.getApp().getLoginInfo().getUserID();	
	private SimpleDateFormat sp=null;
	private Date dtNow=new Date();	
    private 	IGenericDAO<DSHR71, String> objDao=Application.getApp().getDao(DSHR71.class);
	public DSHR71_EXCCEL() {
		
		initComponent();
		sp=new SimpleDateFormat("dd/MM/yyyy");
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
						rturn=doInserDSHR71(outputFile);						
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

	private boolean doInserDSHR71(File fileExcel)
	{
		boolean rturn=true;
		InputStream in;
		ObjUtility obj_utili=new ObjUtility();
		ArrayList<ObjError> listError=new ArrayList<ObjError>();
		ObjError error;
		boolean check=true;
		HRUtils util=ApplicationHelper.getHRUtils();
		String empsn 		= "";		
		Date 	dtDateApdung=null;
		   java.lang.Double JJ_HOUR = null;
		    java.lang.Double JJ_WADD = null;
		    java.lang.Double JJ_JADD;
		    java.lang.String UP_USER;
		    java.util.Date UP_DATE;
		    java.lang.String SEC;	//MA DON VI
		    java.lang.String FACTNAME;	//TEN XUONG
		    java.lang.String IS_SOLE = null;
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
								if(row.getCell(j)!=null)
								{
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
								}							
								 break;
								
							case 1 : 
								if(row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Ngày Áp Dụng Không Hợp Lệ");
										listError.add(error);
										check = false;
										continue;
									}		
									String  strDateApdung=row.getCell(j).getStringCellValue();
									dtDateApdung=sp.parse(strDateApdung);															
									check=checkExitsEmpAndDate(empsn, dtDateApdung);
									if(!check)
									{
										error = new ObjError();
										error.setIdError("Lỗi dòng "+(i+1));
										error.setErrorStr("Thông tin số thẻ trong tháng đã tồn tại");
										listError.add(error);
										continue;	
									}
		
								}	
								break;
	
							case 2 : 
								if(row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Giá trị tổng số tăng ca ngày và đêm không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									}
									JJ_HOUR=row.getCell(j).getNumericCellValue();										
								}								
								break;
							case 3 : 
								if(row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Giá trị tổng số tăng ca ngày chủ nhật không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									}
									JJ_WADD=row.getCell(j).getNumericCellValue();
										
								}
								
								break;	
							case 4: 
								if(row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Giá trị tổng số tăng ca ngày lễ không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									}
									JJ_JADD=row.getCell(j).getNumericCellValue();
										
								}
								
								break;	
							default:
								break;
							}
						
					} // the end for (j)
					
					
					if(check){
						try
						{
							    N_DEPARTMENT objDepartment=null;
								IGenericDAO<N_DEPARTMENT, String> objdaoDepartment=Application.getApp().getDao(N_DEPARTMENT.class);
								List<N_DEPARTMENT> listdept=null;
								useLogin=Application.getApp().getLoginInfo().getUserID().toString();								
								// ma don vi, fact...
								Calendar cal=Calendar.getInstance();
								cal.setTimeInMillis(dtDateApdung.getTime());
								Calendar dateMax = Calendar.getInstance();
								dateMax.setTimeInMillis(cal.getTimeInMillis());
								dateMax.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
								Date dateMax_=dateMax.getTime();
								String strDateMax=sp.format(dateMax_);	
								String strSql="{?=call lay_donvi_theo_ngay(?,?)}";
								String strID_Dept="";
								
								try {
									stm_call = con.prepareCall(strSql);
									stm_call.registerOutParameter(1, Types.VARCHAR);
									stm_call.setString(2, empsn);
									stm_call.setString(3,strDateMax);
									stm_call.execute();
									strID_Dept=	stm_call.getString(1);									
									listdept=objdaoDepartment.find(1, "from N_DEPARTMENT where ID_DEPT=?", strID_Dept);
									if(listdept.size()>0)
									{
										 objDepartment=listdept.get(0);
										// data.setFACTNAME(objDepartment.getNAME_FACT());
										
									}
									if(objDepartment!=null)
									{
										if(objDepartment.getNAME_GROUP()!=null)
										{
											if(objDepartment.getNAME_GROUP().equals("FV3")&&objDepartment.getNAME_FACT().equals("FVLS"))
											{
												IS_SOLE="2";
											}
											if(objDepartment.getNAME_GROUP().equals("PU")&&objDepartment.getNAME_FACT().equals("FVLS"))
											{
												IS_SOLE="3";
											}
										}	
										
										if((objDepartment.getNAME_GROUP()==null||!objDepartment.getNAME_GROUP().equals("FV3")||!objDepartment.getNAME_GROUP().equals("PU"))&&objDepartment.getNAME_FACT().equals("FVLS"))
										{
											IS_SOLE="1";
										}
										else
											IS_SOLE="0";
									}
									DSHR71 data=new DSHR71();
									data.setPNL(empsn);
									data.setVDATE(dtDateApdung);
									data.setJJ_HOUR(JJ_HOUR);
									data.setJJ_WADD(JJ_WADD);
									data.setJJ_JADD(JJ_WADD);
									data.setSEC(strID_Dept);
									data.setFACTNAME(objDepartment.getNAME_FACT());
									data.setIS_SOLE(IS_SOLE);
									data.setUP_DATE(dtNow);
									data.setUP_USER(useLogin);
									objDao.save(data);
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
									
							
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
	private boolean checkExitsEmpAndDate(String empsn, Date date_)
	{
		IGenericDAO<DSHR71, DSHR71Pk> daoDSHR71=Application.getApp().getDao(DSHR71.class);
		DSHR71Pk objpk=new DSHR71Pk(date_,empsn);
		DSHR71 objDSHR71=daoDSHR71.findById(objpk);
		if(objDSHR71!=null)
		{
			return false;
		}
		
		return true;
	}
	private void initComponent(){
		this.setTitle("Cập Nhập Tăng Ca Con Nhỏ");
		this.setStyleName("Default.Window");
		this.setHeight(new Extent(320));
		this.setWidth(new Extent(500));
		rootLayout = new Grid();
		rootLayout.setSize(1);
		rootLayout.setForeground(Color.RED);
		rootLayout.add(getFileUploadExcel());			
		rootLayout.add(new Label("	* Ghi chú : tập tin cập nhật phải là file excel (*.xls)"));
		rootLayout.add(new Label("	* Tập tin được định dạng 5 cột : Số thẻ - Ngày Áp Dụng  Định Dạng ngày tháng ('DD/MM/YYYY') TS Tăng Ca Ngày + Đêm  " +
				"-TS Tăng Ca Chủ Nhật - TS Tăng Ca Lễ . Định Dạng number "));
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
