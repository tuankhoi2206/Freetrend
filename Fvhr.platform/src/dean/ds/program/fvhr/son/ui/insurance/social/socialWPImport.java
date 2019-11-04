package ds.program.fvhr.son.ui.insurance.social;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import sun.net.www.http.Hurryable;

import fv.util.ApplicationHelper;
import fv.util.HRUtils;
import fv.util.Vni2Uni;
import ds.program.fvhr.domain.N_EMP_INFO;
import ds.program.fvhr.domain.N_EMP_JOB_DETAIL;
import ds.program.fvhr.domain.N_HEALTH;
import ds.program.fvhr.domain.N_HOSPITAL;
import ds.program.fvhr.domain.N_JOB;
import ds.program.fvhr.domain.N_LABOUR;
import ds.program.fvhr.domain.N_SOCIAL;
import ds.program.fvhr.son.ui.ObjUtility;
import ds.program.fvhr.son.ui.ObjError;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import nextapp.echo2.app.*;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.fv.app.filetransfer.UploadEvent;
import nextapp.echo2.fv.app.filetransfer.UploadListener;
import nextapp.echo2.fv.app.filetransfer.UploadSelect;

public class socialWPImport extends WindowPane {

	private ResourceBundle bundle;
	
	Grid 		rootLayout ;
	TextArea 	errorTextArea;
	Label 		infoLabel;

	private RadioButton radSocialNew;

	private RadioButton radSocialOld;
	
	
	public socialWPImport() {
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
						if (radSocialOld.isSelected())
						{
							rturn=importSocialOld(outputFile);
						}
						if(radSocialNew.isSelected())
						{
							rturn = importProperties(outputFile);
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
	
	private boolean importSocialOld(File fileExcel)
	{
		
		boolean rturn = true;
		InputStream in ;
		IGenericDAO<N_SOCIAL, String> objDao 	= Application.getApp().getDao(N_SOCIAL.class);
		N_SOCIAL objData ;
		ObjUtility obj_util_ = new ObjUtility();
		SimpleDateFormat sf_ = new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<ObjError> listError = new ArrayList<ObjError>();
		ObjError error;
		boolean check = true;
		String empsn 		= "";
		Date dates			= null;		
		double salary			= 0;
		String note			= "Trạng thái sổ BHXH củ";
		String clock		= "";
		String id_social	= "";
		String id_key		= "";
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
					for (int j = 0; j < row.getLastCellNum(); j++) {
						if(check == false){
							continue;
						}
							switch (j) {
							case 0:	// so the
								error = new ObjError();															
								empsn 	= row.getCell(j).getStringCellValue();	
								HRUtils utils=ApplicationHelper.getHRUtils();
								if(row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING  || !row.getCell(j).getStringCellValue().matches("[0-9]{8}")){
										
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("số thẻ không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									}	
									if(!utils.getPermissionValidator().hasEmpsnPermission(empsn))
									{
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Anh/chị không có quyền thao tác trên số thẻ"+empsn);
										listError.add(error);
										check=false;
										continue;
								
									}
									check=checkLabour(empsn);
									
									 if(!check)
									 {
										 error=new ObjError();
										 error.setIdError("Lỗi dòng"+(i+1));
										 error.setErrorStr("Số thẻ "+empsn+" chưa ký hợp đồng lao động");
										 listError.add(error);
										 check=false;
										 continue;
									 }
									
								}
								
								break;								
							case 1 : // check the exist of id_health in n_health
								if(row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("mã BH không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									}								
									id_social	= row.getCell(j).getStringCellValue();		
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
									System.out.println(sf_.format(dates));
									check=checkEmpsnandDate(empsn, dates);
									if(!check)
									{
										error = new ObjError();
										error.setIdError("Lỗi dòng "+(i+1));
										error.setErrorStr("Ngày hiệu lực đã tồn tại");
										listError.add(error);
										continue;
									}
								}								
								break;								
					    	case 3:
					    		if(row.getCell(j)!=null)
					    		{
					    			if(row.getCell(j+1).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Lương không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									}							
									salary	= row.getCell(j+1).getNumericCellValue();	
					    		}							
							break;							
							default:
								break;
							}						
					} // the end for (j)
					if(check){						
						note = note+ "Cập nhật : " + Application.getApp().getLoginInfo().getUserID() +" ( " +  sf_.format(new Date()) +" )";
						
						clock = "1";
						List<N_SOCIAL> list=objDao.find(1, "from N_SOCIAL where EMPSN=? AND CLOCK='1' ",empsn );
						
						if(list.size()>0)
						{
							objData=list.get(0);				
							
							long intid_keyolb=Long.parseLong("1"+objData.getID_KEY());										
							 String strid_key_N =String.valueOf(intid_keyolb+1);
							String strid_key=strid_key_N.substring(1, 13);
									try{	
										//update
								objData.setNOTE(Vni2Uni.convertToVNI(note));							
								objData.setEXPIRE(dates);								
								objData.setCLOCK("0");
								objData.setSOCIAL_OLD("Y");	
							
								objDao.update(objData);	
								//insert
								N_SOCIAL objSocial=new N_SOCIAL();
								objSocial.setEMPSN(empsn);
								objSocial.setNOTE(Vni2Uni.convertToVNI(note));
								objSocial.setDATES(dates);															
								objSocial.setSALARY((long) salary);
								objSocial.setCLOCK("1");
								objSocial.setSOCIAL_OLD("");
								objSocial.setID_SOCIAL(id_social);
								objSocial.setDATE_NEW(dates);
								objSocial.setID_KEY(strid_key);
								objDao.save(objSocial);
							}catch (Exception e) {
								error=new ObjError();
								error.setErrorStr(e.toString());
								listError.add(error);
								return false;
							}
							
						}						
						rturn = true;			
					}	
				
				}  // the end for (i)	
			}
			
		}catch (Exception e) {
			error=new ObjError();
			error.setErrorStr(e.toString());
			listError.add(error);
			rturn = false;
		}
		
		if(listError!= null && listError.size()>0){
			
			
			String strobj="";
			for(int i=0;i<listError.size();i++)
			{
			
			    ObjError  obj=listError.get(i);
				strobj = strobj+ obj.getIdError() +  " : " +  obj.getErrorStr()+".";
				errorTextArea.setText(strobj);
			}
			/*for(ObjError obj : listError){
				errorTextArea.setText(obj.getIdError() +  " : " + obj.getErrorStr());
			}*/
			
		}else{
			errorTextArea.setText("CẬP NHẬT THÀNH CÔNG (" + (n_row - 1 ) +" dòng dữ liệu ) " ) ;
		}
		return rturn;
	}
	private boolean importProperties(File fileExcel){
		boolean rturn = true;
		InputStream in ;
		IGenericDAO<N_SOCIAL, String> objDao 	= Application.getApp().getDao(N_SOCIAL.class);
		N_SOCIAL objData ;
		ObjUtility obj_util_ = new ObjUtility();
		SimpleDateFormat sf_ = new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<ObjError> listError = new ArrayList<ObjError>();
		ObjError error;
		boolean check = true;
		String empsn 		= "";
		Date dates			= null;
		Date expire			= null;
		double salary			= 0;
		String note			= "Trạng thái sổ BHXH mới";
		String clock		= "";
		String id_social	= "";
		String id_key		= "";
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
					for (int j = 0; j < row.getLastCellNum(); j++) {
						if(check == false){
							continue;
						}
							switch (j) {
							case 0:	// so the
								 HRUtils utils=ApplicationHelper.getHRUtils();
									error = new ObjError();
							if(row.getCell(j)!=null)
							{
								if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING && !row.getCell(j).getStringCellValue().matches("[0-9]{8}") ){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("số thẻ không hợp lệ");
									listError.add(error);
									check = false;
									continue;
								}
								empsn 	= row.getCell(j).getStringCellValue();
								if(!utils.getPermissionValidator().hasEmpsnPermission(empsn))
								{   error = new ObjError();
									error.setIdError("Lỗi dòng "+ (i+1));
									error.setErrorStr("Anh/Chị không có quyền thao tác trên số thẻ " +empsn);
									listError.add(error);
									check=false;
									continue;
								}
								check=checkLabour(empsn);
								
								 if(!check)
								 {
									 error=new ObjError();
									 error.setIdError("Lỗi dòng"+(i+1));
									 error.setErrorStr("Số thẻ "+empsn+" chưa ký hợp đồng lao động");
									 listError.add(error);
									 check=false;
									 continue;
								 }
								 check 	= checkEmpsn(empsn);								
								if(!check){
									error = new ObjError();
									error.setIdError("Lỗi dòng " + (i + 1));
									error.setErrorStr("số thẻ đã tồn tại hoặc không hợp lệ");
									listError.add(error);
									 check=false;
									continue;
								}
								id_key	= empsn + "0001";
							}
								
								break;
							case 1 : // check the exist of id_health in n_health
								if(row.getCell(j)!=null)
								{
									if(row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_STRING){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("mã BH không hợp lệ");
										listError.add(error);
										check = false;
										continue;
										}
									id_social	= row.getCell(j).getStringCellValue();
									check = checkIDSocial(id_social);
									if(check == false){
										error = new ObjError();
										error.setIdError(id_social);
										error.setErrorStr("mã BH đã tồn tại");
										listError.add(error);
										continue;
										}
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
									System.out.println(sf_.format(dates));	
								}								
								break;								
							case 3 :
								if(row.getCell(j)!=null)
								{
									if(row.getCell(j+1).getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
										error = new ObjError();
										error.setIdError("Lỗi dòng " + (i + 1));
										error.setErrorStr("Lương không hợp lệ");
										listError.add(error);
										check = false;
										continue;
									}								
									salary	= row.getCell(j+1).getNumericCellValue();
								}
								
								break;	
							default:
								break;
							}
					} // the end for (j)	
					if(check){						
						note = note +"Cập nhật : " + Application.getApp().getLoginInfo().getUserID() +" ( " +  sf_.format(new Date()) +" )";
						clock = "1";
						objData = new N_SOCIAL();	
						objData.setEMPSN(empsn);
						objData.setDATES(dates);
						objData.setDATE_NEW(dates);
						//objData.setEXPIRE(expire);
						objData.setSALARY((long) salary);
						objData.setNOTE(Vni2Uni.convertToVNI(note));
						objData.setCLOCK(clock);
						objData.setID_SOCIAL(id_social);
						objData.setID_KEY(id_key);						
						objDao.save(objData);
						rturn = true;			
					}					
				}  // the end for (i)				
			}			
		}catch (Exception e) {
			error=new ObjError();
			error.setErrorStr(e.toString());
			listError.add(error);
			rturn = false;
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
	private boolean checkIDSocial(String id_health) {

		IGenericDAO<N_SOCIAL, String> objDao = Application.getApp().getDao(N_SOCIAL.class);
		DetachedCriteria	objDC = DetachedCriteria.forClass(N_SOCIAL.class);
							objDC.add(Restrictions.eq("ID_SOCIAL", id_health));
		List<N_SOCIAL> listData = objDao.findByCriteria(1, objDC);
		
		if (listData.size() >= 1) {
			return false;
		}
		return true;
	}
	private boolean checkLabour(String empsn)
	{
		// hợp đồng lao động
		IGenericDAO<N_LABOUR, String> objDao=Application.getApp().getDao(N_LABOUR.class);
		List<N_LABOUR> list=objDao.find(1, "from N_LABOUR where EMPSN=? AND CLOCK='1'", empsn);
		if(list.size()==0)
		{
			return false;
		}
		return true;
	}
	
	private boolean checkEmpsn(String empsn) {
		
		if (empsn.length()!=8) {
			return false;
		}
		IGenericDAO<N_SOCIAL, String> objDao = Application.getApp().getDao(N_SOCIAL.class);
		DetachedCriteria	objDC = DetachedCriteria.forClass(N_SOCIAL.class);
							objDC.add(Restrictions.eq("EMPSN", empsn));
		List<N_SOCIAL> listData = objDao.findByCriteria(1, objDC);
		if (listData.size() >= 1) {
			return false;
		}
		return true;
	}
	private boolean checkEmpsnandDate(String empsn, Date date)
	{
		IGenericDAO<N_HEALTH, String> objDao=Application.getApp().getDao(N_HEALTH.class);
		List<N_HEALTH> listHealh=objDao.find(1,"from N_SOCIAL where EMPSN=? AND CLOCK='1' AND DATES=?" , empsn,date);
		if(listHealh.size()>0)
		{
			return false;
		}
		return true;
	}
	private void initComponent(){
		this.setTitle("IMPORT");
		this.setStyleName("Default.Window");
		this.setHeight(new Extent(300));
		this.setWidth(new Extent(500));
		rootLayout = new Grid();
		rootLayout.setSize(1);
		rootLayout.setForeground(Color.RED);
		rootLayout.add(getFileUploadExcel());
		ButtonGroup btGroup=new ButtonGroup();
		radSocialNew =new RadioButton();
		radSocialNew.setGroup(btGroup);
		radSocialNew.setText("Sổ BHXH Mới");
		radSocialNew.setSelected(true);
		rootLayout.add(radSocialNew);
		radSocialOld=new RadioButton();
		radSocialOld.setGroup(btGroup);
		radSocialOld.setText("Sổ BHXH Cũ");
		rootLayout.add(radSocialOld);
		rootLayout.add(new Label("	* Ghi chú : tập tin cập nhật phải là file excel (*.xls)"));
		rootLayout.add(new Label("	* File mẫu : X:\\FV1\\IT\\Son\\import excel template\\BH_XH.xls"));
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
