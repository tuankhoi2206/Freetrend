package ds.program.fvhr.son.ui.insurance.health;


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
import ds.program.fvhr.domain.N_EMP_INFO;
import ds.program.fvhr.domain.N_EMP_JOB_DETAIL;
import ds.program.fvhr.domain.N_HEALTH;
import ds.program.fvhr.domain.N_HOSPITAL;
import ds.program.fvhr.domain.N_JOB;
import ds.program.fvhr.domain.N_LABOUR;
import ds.program.fvhr.son.ui.ObjUtility;
import ds.program.fvhr.son.ui.ObjError;
import ds.program.fvhr.tien.ui.EMPSN_E;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import nextapp.echo2.app.*;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.fv.app.filetransfer.UploadEvent;
import nextapp.echo2.fv.app.filetransfer.UploadListener;
import nextapp.echo2.fv.app.filetransfer.UploadSelect;

public class healthWPImport extends WindowPane {

	private ResourceBundle bundle;
	
	Grid 		rootLayout ;
	TextArea 	errorTextArea;
	Label 		infoLabel;

	private RadioButton radnotBHYT;

	private RadioButton radBHYT;

	private RadioButton radUpdateBHYT;
	
	
	public healthWPImport() {
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
						if(radUpdateBHYT.isSelected())
						{
							rturn=importRadupdateBHYT(outputFile);
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
	private boolean importRadupdateBHYT(File fileExcel)
	{
	boolean rturn=true;
	InputStream in;
	IGenericDAO<N_HEALTH, String> objDao=Application.getApp().getDao(N_HEALTH.class);
	N_HEALTH objHealth;
	ObjUtility obj_utili=new ObjUtility();
	SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
	ArrayList<ObjError> listError=new ArrayList<ObjError>();
	ObjError error;
	boolean check=true;
	HRUtils util=ApplicationHelper.getHRUtils();
	String empsn 		= "";
	Date dates			= null;
	Date expire			= null;
	double salary			= 0;
	String note			= "CẬP NHẬP LẠI THẺ BHYT_";
	String clock		= "";
	String id_health	= "";
	String id_hos		= "";
	String id_key		= "";
	String id_pro		= "";
	Date date_bhyt		= null;
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
							}
							
							
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
							 check=checkLabour(empsn);
							 if(!check)
							 {
								 error=new ObjError();
								 error.setIdError("Lỗi dòng " +(i+1));
								 error.setErrorStr("Số thẻ "+empsn + " chưa ký hợp đồng");
								 listError.add(error);
								 check=false;
								 continue;
							 }
							// check=checkEmpsn(empsn);																 
							
							break;
							
						case 1 : // check the exist of id_health in n_health
/*							//if(row.getCell(j)==null)
							if((row.getCell(j).getStringCellValue().equals(null))
									|| (row.getCell(j).getStringCellValue().isEmpty()==false))
							{
								error=new ObjError();
								error.setIdError("Lỗi dòng" +(i+1));
								error.setErrorStr("Mã BH không có trên file excel");
								listError.add(error);
								check = false;
								continue;
								
							}
*/							
							if((row.getCell(j).getStringCellValue().equals(null))
									|| (row.getCell(j).getStringCellValue().isEmpty()==false))
							{
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("mã BH không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
							}
							
							
							id_health	= row.getCell(j).getStringCellValue();
							check = checkIDHealth(id_health);
							if(check == false){
								error = new ObjError();
								error.setIdError(id_health);
								error.setErrorStr("mã BHYT đã tồn tại");
								listError.add(error);
								continue;
							}
							break;

						case 2 : // ngay  mua
							if(row.getCell(j)!=null)
							{
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Ngày Mua không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								dates	= row.getCell(j).getDateCellValue();
								System.out.println(df.format(dates));	
							}
							
							
							break;
							
						case 3 :
							/*if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
								error = new ObjError();
								error.setIdError("Lỗi dòng " + (i + 1));
								error.setErrorStr("Ngày Hết Hạn không hợp lệ");
								listError.add(error);
								check = false;
								continue;
							}
							
							expire	= row.getCell(j).getDateCellValue();*/
							break;
							
						case 4: 
							if(row.getCell(j)!=null)
							{
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Lương không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								
								salary	= row.getCell(j).getNumericCellValue();
									
							}
							break;
							
						case 5 : // check exist of id_pro
							
							if(row.getCell(j)!=null)
							{
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("mã Tỉnh không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								
								
								id_pro	= row.getCell(j).getStringCellValue();
								check = checkIDProvince(id_pro);
								
								if(!check){
									error = new ObjError();
									error.setIdError(id_health);
									error.setErrorStr("mã Tỉnh không tồn tại");
									listError.add(error);
									continue;
								}
							}
							
							
							break;

						case 6 : 
							if(row.getCell(j)!=null)
							{
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("mã BV không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								id_hos	= row.getCell(j).getStringCellValue();
								check	= checkIDHospital(id_hos);
								
								if(!check){
									error = new ObjError();
									error.setIdError(id_health);
									error.setErrorStr("mã BV không tồn tại");
									listError.add(error);
									continue;
								}
							}
							
							break;
						
						case 7 :
							if((row.getCell(j).getStringCellValue().equals(null))
									|| (row.getCell(j).getStringCellValue().isEmpty()==false))
							{
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("ghi chú không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								note	= note +row.getCell(j).getStringCellValue();
								note 	= note + "( UserID : "+ Application.getApp().getLoginInfo().getUserID()  +" ) ";
							}
							
                            break;
							
						case 8:
							if(row.getCell(j)!=null)
							{
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Ngày nhận thẻ không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								date_bhyt	= row.getCell(j).getDateCellValue(); 
							}
							
							break;

						default:
							break;
						}
					
				} // the end for (j)
				
				
				if(check){
					boolean exist=checkEmpsn(empsn);
					if(!exist)
					{		// update row old\
						List<N_HEALTH> listHealth=objDao.find(1, "from N_HEALTH where EMPSN=? AND CLOCK='1'", empsn);
						if(listHealth.size()>0)
						{	 objHealth = listHealth.get(0);							
							try
							{	//update
								
								objHealth.setID_HEALTH(id_health);
								objHealth.setNOTE(fv.util.Vni2Uni.convertToVNI(note));
								objDao.update(objHealth);
															 
							}catch (Exception e) {
								System.out.print(e.toString());
							}
						}
					}
				 rturn = true;			
				}	
			
			}  // the end for (i)
		}
	}
		
	catch (Exception e) 
	{
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

	/*private boolean importRadBHYT(File fileExcel)
	{
		boolean rturn=true;
		InputStream in;
		IGenericDAO<N_HEALTH, String> objDao=Application.getApp().getDao(N_HEALTH.class);
		N_HEALTH objHealth;
		ObjUtility obj_utili=new ObjUtility();
		SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<ObjError> listError=new ArrayList<ObjError>();
		ObjError error;
		boolean check=true;
		HRUtils util=ApplicationHelper.getHRUtils();
		String empsn 		= "";
		Date dates			= null;
		Date expire			= null;
		double salary			= 0;
		String note			= "ĐÃ CÓ THẺ BHYT_";
		String clock		= "";
		String id_health	= "";
		String id_hos		= "";
		String id_key		= "";
		String id_pro		= "";
		Date date_bhyt		= null;
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
								 check=checkLabour(empsn);
								 if(!check)
								 {
									 error=new ObjError();
									 error.setIdError("Lỗi dòng " +(i+1));
									 error.setErrorStr("Số thẻ "+empsn + " chưa ký hợp đồng");
									 listError.add(error);
									 check=false;
									 continue;
								 }
								// check=checkEmpsn(empsn);																 
								
								break;
								
							case 1 : // check the exist of id_health in n_health
								if(row.getCell(j)==null)
								{
									error=new ObjError();
									error.setIdError("Lỗi dòng" +(i+1));
									error.setErrorStr("Anh/Chị đã chọn sai trạng thái");
									listError.add(error);
									check = false;
									continue;
									
								}
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("mã BH không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								
								id_health	= row.getCell(j).getStringCellValue();
								check = checkIDHealth(id_health);
								if(check == false){
									error = new ObjError();
									error.setIdError(id_health);
									error.setErrorStr("mã BHYT đã tồn tại");
									listError.add(error);
									continue;
								}
								break;
	
							case 2 : // ngay  mua
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Ngày Mua không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								dates	= row.getCell(j).getDateCellValue();
								System.out.println(df.format(dates));
								
								check=checkEmpsnandDate(empsn, dates);
								if(!check)
								{
									error = new ObjError();
									error.setIdError("Lỗi dòng "+(i+1));
									error.setErrorStr("Anh/Chị đã chọn sai trạng thái");
									listError.add(error);
									continue;
								}
								break;
								
							case 3 :
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Ngày Hết Hạn không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								
								expire	= row.getCell(j).getDateCellValue();
								break;
								
							case 4: 
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Lương không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								
								salary	= row.getCell(j).getNumericCellValue();
								break;
								
							case 5 : // check exist of id_pro
								
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("mã Tỉnh không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								
								
								id_pro	= row.getCell(j).getStringCellValue();
								check = checkIDProvince(id_pro);
								
								if(!check){
									error = new ObjError();
									error.setIdError(id_health);
									error.setErrorStr("mã Tỉnh không tồn tại");
									listError.add(error);
									continue;
								}
								
								break;
	
							case 6 : 
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("mã BV không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								id_hos	= row.getCell(j).getStringCellValue();
								check	= checkIDHospital(id_hos);
								
								if(!check){
									error = new ObjError();
									error.setIdError(id_health);
									error.setErrorStr("mã BV không tồn tại");
									listError.add(error);
									continue;
								}
								break;
							
							case 7 :
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("ghi chú không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								note	= note + row.getCell(j).getStringCellValue();
								note 	= note + "( UserID : "+ Application.getApp().getLoginInfo().getUserID()  +" ) ";
								break;
								
							case 8:
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Ngày nhận thẻ không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								date_bhyt	= row.getCell(j).getDateCellValue();
								break;
	
							default:
								break;
							}
						
					} // the end for (j)
					
					
					if(check){
						boolean exist=checkEmpsn(empsn);
						if(!exist)
						{		// update row old\
							List<N_HEALTH> listHealth=objDao.find(1, "from N_HEALTH where EMPSN=? AND CLOCK='1'", empsn);
							if(listHealth.size()>0)
							{	 objHealth = listHealth.get(0);
								  long intid_keyolb=Long.parseLong("1"+objHealth.getID_KEY());										
								  String strid_key_N =String.valueOf(intid_keyolb+1);
							       String strid_key=strid_key_N.substring(1, 13);
								try
								{	//update
									objHealth.setCLOCK("0");
									objHealth.setEXPIRE(dates);
									objDao.update(objHealth);
									//insert
									 N_HEALTH	objHealth_N =new N_HEALTH();
									 objHealth_N.setEMPSN(empsn);
									 objHealth_N.setID_HEALTH(id_health);
									 objHealth_N.setDATES(dates);
									 objHealth_N.setSALARY((long)salary);
									 objHealth_N.setNOTE(fv.util.Vni2Uni.convertToVNI(note));
									 objHealth_N.setCLOCK("1");
									 objHealth_N.setID_HOS(id_hos);
									 objHealth_N.setID_PRO(id_pro);
									 objHealth_N.setID_KEY(strid_key);
									 objDao.save(objHealth_N);								 
								}catch (Exception e) {
									System.out.print(e.toString());
								}
							}
						}
						if(exist)
						{
							try{
								 N_HEALTH	objHealth_N =new N_HEALTH();
								 objHealth_N.setEMPSN(empsn);
								 objHealth_N. setID_HEALTH(id_health);
								 objHealth_N.setDATES(dates);
								 objHealth_N.setSALARY((long)salary);
								 objHealth_N.setNOTE(fv.util.Vni2Uni.convertToVNI(note));
								 objHealth_N.setCLOCK("1");
								 objHealth_N.setID_HOS(id_hos);
								 objHealth_N.setID_PRO(id_pro);
								 objHealth_N.setID_KEY(empsn+"0001");
								 objHealth_N.setDATE_BHYT(date_bhyt);
								 objDao.save(objHealth_N);	
							}catch (Exception e) {
								// TODO: handle exception
							}
						}
						rturn = true;
					}	
				
				}  // the end for (i)
			}
		}
			
		catch (Exception e) 
		{ 
			
			System.out.print(e.toString());
		  rturn=false;
		  System.out.print(e.toString());
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
	}*/
	
	
	private boolean importRadnotBHYT(File fileExcel)
	{
		boolean rturn=true;
		InputStream in;
		IGenericDAO<N_HEALTH, String> objDao=Application.getApp().getDao(N_HEALTH.class);
		N_HEALTH objHealth;
		ObjUtility obj_utili=new ObjUtility();
		SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<ObjError> listError=new ArrayList<ObjError>();
		ObjError error;
		boolean check=true;
		HRUtils util=ApplicationHelper.getHRUtils();
		String empsn 		= "";
		Date dates			= null;
		Date expire			= null;
		double salary			= 0;
		String note			= "CHƯA CÓ THẺ BHYT_";
		   note 	= note + "( UserID : "+ Application.getApp().getLoginInfo().getUserID()  +" ) ";
		String clock		= "";
		String id_health	= "";
		String id_hos		= "";
		String id_key		= "";
		String id_pro		= "";
		Date date_bhyt		= null;
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
					
					System.out.println(row.getLastCellNum());
					for (int j = 0; j < row.getLastCellNum(); j++) {
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
								 check=checkLabour(empsn);
								 if(!check)
								 {
									 error=new ObjError();
									 error.setIdError("Lỗi dòng " +(i+1));
									 error.setErrorStr("Số thẻ "+empsn + " chưa ký hợp đồng");
									 listError.add(error);
									 check=false;
									 continue;
								 }
								// check=checkEmpsn(empsn);																 
								
								break;
								
							case 1 : // check the exist of id_health in n_health
								//if(row.getCell(j)!=null)
								if((row.getCell(j).getStringCellValue().equals(null))
										|| (row.getCell(j).getStringCellValue().isEmpty()==false))
								{
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Anh/Chị đã chọn sai trang thái ");
									listError.add(error);
									check = false;
									continue;
								}
							
								break;
	
							case 2 : // ngay  mua
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Ngày Mua không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								dates	= row.getCell(j).getDateCellValue();
								System.out.println(df.format(dates));
								check=checkEmpsnandDate(empsn, dates);
								if(!check)
								{
									error = new ObjError();
									error.setIdError("Lỗi dòng "+(i+1));
									error.setErrorStr("Anh/Chị đã chọn sai trạng thái");
									listError.add(error);
									continue;
								}
								break;
								
							case 3 :
								
								break;
								
							case 4: 
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Lương không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								
								salary	= row.getCell(j).getNumericCellValue();
								break;
								
							case 5 : // check exist of id_pro
								
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("mã Tỉnh không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								
								
								id_pro	= row.getCell(j).getStringCellValue();
								check = checkIDProvince(id_pro);
								
								if(!check){
									error = new ObjError();
									error.setIdError(id_health);
									error.setErrorStr("mã Tỉnh không tồn tại");
									listError.add(error);
									continue;
								}
								
								break;
	
							case 6 : 
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("mã BV không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								id_hos	= row.getCell(j).getStringCellValue();
								check	= checkIDHospital(id_hos);
								
								if(!check){
									error = new ObjError();
									error.setIdError(id_health);
									error.setErrorStr("mã BV không tồn tại");
									listError.add(error);
									continue;
								}
								break;
							
							case 7 :
								if((row.getCell(j).getStringCellValue().equals(null))
										|| (row.getCell(j).getStringCellValue().isEmpty()==false))
								{
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("ghi chú không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								note	= note+ row.getCell(j).getStringCellValue();
								
								break;
								
							case 8:
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("Ngày nhận thẻ không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								date_bhyt	= row.getCell(j).getDateCellValue();
								break;
	
							default:
								break;
							}
						
					} // the end for (j)
					
					
					if(check){
						boolean exist=checkEmpsn(empsn);
						if(!exist)
						{		// update row old\
							
							//abc
							List<N_HEALTH> listHealth=objDao.find(1, "from N_HEALTH where EMPSN=? AND CLOCK='1'", empsn);
							if(listHealth.size()>0)
										{	 objHealth = listHealth.get(0);
       									 	long intid_keyolb=Long.parseLong("1"+objHealth.getID_KEY());										
											 String strid_key_N =String.valueOf(intid_keyolb+1);
											String strid_key=strid_key_N.substring(1, 13);
											 System.out.print(strid_key);
											// BigDecimal  bgDe=BigDecimal.
											try
											{	//update
												objHealth.setCLOCK("0");
												objHealth.setEXPIRE(dates);
												objDao.update(objHealth);
												//insert
												 N_HEALTH	objHealth_N =new N_HEALTH();
												 objHealth_N.setEMPSN(empsn);
												 objHealth_N.setDATES(dates);
												 objHealth_N.setSALARY((long)salary);
												 objHealth_N.setNOTE(fv.util.Vni2Uni.convertToVNI(note));
												 objHealth_N.setCLOCK("1");
												 objHealth_N.setID_HOS(id_hos);
												 objHealth_N.setID_PRO(id_pro);
												 objHealth_N.setID_KEY(strid_key);
												 objHealth_N.setDATE_BHYT(date_bhyt);
												 objDao.save(objHealth_N);								 
											}catch (Exception e) {
												System.out.print(e.toString());
											}
										}
						}
						if(exist)
						{
							try{
								 N_HEALTH	objHealth_N =new N_HEALTH();
								 objHealth_N.setEMPSN(empsn);
								 objHealth_N.setDATES(dates);
								 objHealth_N.setSALARY((long)salary);
								 objHealth_N.setNOTE(fv.util.Vni2Uni.convertToVNI(note));
								 objHealth_N.setCLOCK("1");
								 objHealth_N.setID_HOS(id_hos);
								 objHealth_N.setID_PRO(id_pro);
								 objHealth_N.setID_KEY(empsn+"0001");
								 objHealth_N.setDATE_BHYT(date_bhyt);
								 objDao.save(objHealth_N);	
							}catch (Exception e) {
								// TODO: handle exception
							}
						}				
					}
					rturn = true;
				
				}  // the end for (i)
			}
		}
			
		catch (Exception e) 
		{
				System.out.print(e.toString());
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
	
	
	private boolean checkIDHospital(String id_hos) {
		IGenericDAO<N_HOSPITAL, String> objDao = Application.getApp().getDao(N_HOSPITAL.class);
		DetachedCriteria 	objDc = DetachedCriteria.forClass(N_HOSPITAL.class);
							objDc.add(Restrictions.eq("ID_HOS", id_hos));
		List<N_HOSPITAL> listData = objDao.findByCriteria(1, objDc);
		if(listData == null || listData.size() == 0){
			return false;
		}
		return true;
	}
	private boolean checkIDProvince(String id_pro) {
		IGenericDAO<N_HOSPITAL, String> objDao = Application.getApp().getDao(N_HOSPITAL.class);
		DetachedCriteria 	objDc = DetachedCriteria.forClass(N_HOSPITAL.class);
							objDc.add(Restrictions.eq("ID_PROVINCE", id_pro));
		List<N_HOSPITAL> listData = objDao.findByCriteria(1, objDc);
		if(listData == null || listData.size() == 0){
			return false;
		}
		return true;
	}
	
	private boolean checkIDHealth(String id_health) {

		IGenericDAO<N_HEALTH, String> objDao = Application.getApp().getDao(N_HEALTH.class);
		DetachedCriteria	objDC = DetachedCriteria.forClass(N_HEALTH.class);
							objDC.add(Restrictions.eq("ID_HEALTH", id_health));
		List<N_HEALTH> listData = objDao.findByCriteria(1, objDC);
		if (listData.size() >= 1) {
			return false;
		}
		return true;
	}
	private boolean checkLabour(String empsn)
	{
		IGenericDAO<N_LABOUR, String> objdao=Application.getApp().getDao(N_LABOUR.class);
		List<N_LABOUR> list=objdao.find(1, "from N_LABOUR where EMPSN=? AND CLOCK='1'", empsn);
		if(list.size()==0)
		{
			return false;
		}
		return true;
	}
	private boolean checkEmpsnandDate(String empsn, Date date)
	{
		IGenericDAO<N_HEALTH, String> objDao=Application.getApp().getDao(N_HEALTH.class);
		List<N_HEALTH> listHealh=objDao.find(1,"from N_HEALTH where EMPSN=? AND CLOCK='1' AND DATES=?" , empsn,date);
		if(listHealh.size()>0)
		{
			return false;
		}
		return true;
	}
	private boolean checkEmpsn(String empsn) {
		
		if (empsn.length()!=8) {
			return false;
		}
		
		IGenericDAO<N_HEALTH, String> objDao = Application.getApp().getDao(N_HEALTH.class);
		DetachedCriteria	objDC = DetachedCriteria.forClass(N_HEALTH.class);
							objDC.add(Restrictions.eq("EMPSN", empsn));
		List<N_HEALTH> listData = objDao.findByCriteria(1, objDC);
		if (listData.size() >= 1) {
			return false;
		}
		
		
		return true;
	}
	private void initComponent(){
		this.setTitle("IMPORT");
		this.setStyleName("Default.Window");
		this.setHeight(new Extent(270));
		this.setWidth(new Extent(500));
		rootLayout = new Grid();
		rootLayout.setSize(1);
		rootLayout.setForeground(Color.RED);
		rootLayout.add(getFileUploadExcel());
		
		ButtonGroup btGroup=new ButtonGroup();
		radnotBHYT=new RadioButton();
		radnotBHYT.setGroup(btGroup);
		radnotBHYT.setText("Tăng mới chưa có  mã thẻ BHYT");
		rootLayout.add(radnotBHYT);		
		radUpdateBHYT=new RadioButton();
		radUpdateBHYT.setGroup(btGroup);
		radUpdateBHYT.setText("Cập nhập lại  mã thẻ BHYT cho tăng mới");
		rootLayout.add(radUpdateBHYT);		
		rootLayout.add(new Label("	* Ghi chú : tập tin cập nhật phải là file excel (*.xls)"));
		rootLayout.add(new Label("	* File mẫu : X:\\FV1\\IT\\Son\\import excel template\\BaoHiemYT.xls"));
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
