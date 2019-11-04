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
import ds.program.fvhr.domain.N_CHANGE_HOSPITAL;
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
import ds.program.fvhr.domain.pk.N_CHANGE_HOSPITALPk;
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

public class N_CHANGE_H_EXCEL extends WindowPane {

	private ResourceBundle bundle;	
    private 	Grid 		rootLayout ;
    private TextArea 	errorTextArea;
    private Label 		infoLabel;
	private String useLogin = Application.getApp().getLoginInfo().getUserID();	
	private SimpleDateFormat sp=null;
	public N_CHANGE_H_EXCEL() {		
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
						rturn=doInserN_Change_Hopital(outputFile);						
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

	private boolean doInserN_Change_Hopital(File fileExcel)
	{
		boolean rturn=true;
		InputStream in;		
		ArrayList<ObjError> listError=new ArrayList<ObjError>();
		List<N_CHANGE_HOSPITAL> listHospital=null;
		ObjError error;
		boolean check=true;
		HRUtils util=ApplicationHelper.getHRUtils();
		 java.lang.String EMPSN = null;
		 java.util.Date DATE_CHANGE;
		 String IDHOS_OLD = null;
		 java.lang.String IDHOS_NEW = null;
		 java.lang.String NOTE=null;
		 java.lang.String STATUS;
		 String IDPRO_OLD = null;
		 java.lang.String IDPRO_NEW = null;
	   	Date dtThayDoi=null;
		String  strDateThayDoi=null;
		
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
									EMPSN 	= row.getCell(j).getStringCellValue();
									// kiem tra quyen thao tac
									
									 if(!util.getPermissionValidator().hasEmpsnPermission(EMPSN))
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
										error.setErrorStr("Ngày Thay Đổi Không Hợp Lệ ");
										listError.add(error);
										check = false;
										continue;
									}		
									  strDateThayDoi=row.getCell(j).getStringCellValue();								
									dtThayDoi=sp.parse(strDateThayDoi);															
									
		
								}	
								break;
	
							case 2 : 
								if(row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Mã Tỉnh Thành Cũ Không Hợp Lệ");
										listError.add(error);
										check = false;
										continue;
									}
									IDPRO_OLD=row.getCell(j).getStringCellValue();										
								}								
								break;
							case 3 : 
								if(row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Mã Bệnh Viện Cũ Không Hợp Lệ");
										listError.add(error);
										check = false;
										continue;
									}
									IDHOS_OLD=row.getCell(j).getStringCellValue();
										
								}
								
								break;	
							case 4: 
								if(row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Mã Tỉnh Thành Mới Không Hợp Lệ");
										listError.add(error);
										check = false;
										continue;
									}
									IDPRO_NEW=row.getCell(j).getStringCellValue();
										
								}
								
								break;	
							case 5: 
								if(row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Mã Bệnh Viện Mới Không Hợp Lệ");
										listError.add(error);
										check = false;
										continue;
									}
									IDHOS_NEW=row.getCell(j).getStringCellValue();
										
								}
								check=checkExitsEmpAndDate(EMPSN, dtThayDoi,IDPRO_NEW,IDHOS_NEW,"1");
								if(!check)
								{
									error = new ObjError();
									error.setIdError("Lỗi dòng "+(i+1));
									error.setErrorStr("Thông tin số thẻ trong tháng đã tồn tại");
									listError.add(error);
									continue;	
								}
								
								break;	
							case 6: 
								if(row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Ghi Chú Không hợp Lệ ");
										listError.add(error);
										check = false;
										continue;
									}
									NOTE=row.getCell(j).getStringCellValue();										
								}
								
								break;	
							default:
								break;
							}						
					} 
					if(check){
						try
						{
							  
							IGenericDAO<N_CHANGE_HOSPITAL, String> daoN_Change_Hospital=Application.getApp().getDao(N_CHANGE_HOSPITAL.class);
							listHospital=daoN_Change_Hospital.find(1,"From N_CHANGE_HOSPITAL where EMPSN ='"+EMPSN+"' AND STATUS='1' ");
							if(listHospital.size()>0)
							{
								N_CHANGE_HOSPITAL objChange_Hos_=listHospital.get(0);
								objChange_Hos_.setSTATUS("0");
								daoN_Change_Hospital.update(objChange_Hos_);
							}
							N_CHANGE_HOSPITAL objChange_Hos=new N_CHANGE_HOSPITAL();
							objChange_Hos.setEMPSN(EMPSN);
							objChange_Hos.setDATE_CHANGE(dtThayDoi);
							objChange_Hos.setIDPRO_OLD(IDPRO_OLD);
							objChange_Hos.setIDHOS_OLD(IDHOS_OLD);
							objChange_Hos.setIDPRO_NEW(IDPRO_NEW);
							objChange_Hos.setIDHOS_NEW(IDHOS_NEW);
							objChange_Hos.setNOTE(NOTE);
							objChange_Hos.setSTATUS("1");
														
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
	private boolean checkExitsEmpAndDate(String empsn, Date date_, String IDPRO_NEW,  String IDHOS_NEW, String STATUS)
	{
		IGenericDAO<N_CHANGE_HOSPITAL, N_CHANGE_HOSPITALPk> daoN_CHANGE_HOSPITAL=Application.getApp().getDao(N_CHANGE_HOSPITAL.class);
		N_CHANGE_HOSPITALPk objpk=new N_CHANGE_HOSPITALPk(empsn,date_,IDPRO_NEW,STATUS,IDHOS_NEW);
		N_CHANGE_HOSPITAL objN_CHANGE_HOSPITAL=daoN_CHANGE_HOSPITAL.findById(objpk);
		if(objN_CHANGE_HOSPITAL!=null)
		{
			return false;
		}
		
		return true;
	}
	private void initComponent(){
		this.setTitle("Cập Nhập Thay Đổi Nơi ĐKKCB Ban Đầu");
		this.setStyleName("Default.Window");
		this.setHeight(new Extent(320));
		this.setWidth(new Extent(500));
		rootLayout = new Grid();
		rootLayout.setSize(1);
		rootLayout.setForeground(Color.RED);
		rootLayout.add(getFileUploadExcel());			
		rootLayout.add(new Label("	* Ghi chú : tập tin cập nhật phải là file excel (*.xls)"));
		rootLayout.add(new Label("	* Tập tin được định dạng 7 cột"));
		rootLayout.add(new Label("	 - Số Thẻ - Ngày Thay Đổi - Mã tỉnh Thành Cũ"));
		rootLayout.add(new Label("	 - Mã Bệnh Viện Cũ  -  Mã Tình Thành Mới - Mã Bệnh Viện Mới "));
		rootLayout.add(new Label("	 - Ghi Chú  - Định Dạng ngày tháng dd/mm/yyyy"));
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
