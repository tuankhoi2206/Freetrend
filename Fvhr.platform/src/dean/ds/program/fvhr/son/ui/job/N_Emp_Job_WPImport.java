package ds.program.fvhr.son.ui.job;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.io.Resource;

import fv.util.Vni2Uni;
import ds.program.fvhr.domain.N_EMP_INFO;
import ds.program.fvhr.domain.N_EMP_JOB_DETAIL;
import ds.program.fvhr.domain.N_JOB;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import nextapp.echo2.app.*;
import nextapp.echo2.fv.app.filetransfer.UploadEvent;
import nextapp.echo2.fv.app.filetransfer.UploadListener;
import nextapp.echo2.fv.app.filetransfer.UploadSelect;

public class N_Emp_Job_WPImport extends WindowPane {

	private ResourceBundle bundle;
	
	Grid 	rootLayout ;
	
	
	
	public N_Emp_Job_WPImport() {
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
						sw.start();
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
						String str_error = importProperties(outputFile);		// import emp_job
						//String str_error = Check_ID_Job_From_File(outputFile); // check id_job
						
						outputFile = null;
						sw.stop();
						if(str_error.equals("")){
							str_error	= "Cập nhật thành công";
						}
						ObjUtility.ShowMessageInfo(str_error);
						
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
	
	private String Check_ID_Job_From_File(File fileExcel){
		String str_note = "";
		
		try {
			
			InputStream in 		= new FileInputStream(fileExcel);
			HSSFWorkbook wb		= new HSSFWorkbook(in);
			HSSFSheet	sheet	= wb.getSheetAt(0);
			HSSFRow		row		;
			String	value_cell	= "";
			int 	n_row		= sheet.getPhysicalNumberOfRows();
			
			for (int i = 0; i < n_row; i++) {
				
				row = sheet.getRow(i);
				value_cell = row.getCell(0).toString();
				
				if(Check_exist_job(value_cell) == false){
					str_note = str_note + ";" + value_cell;
				}
				
			}
			
			
		
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		return str_note;
	}
	
	private String importProperties(File fileExcel){
		String str_error = "";
		InputStream in ;
		IGenericDAO<N_EMP_JOB_DETAIL, String> obj_dao 	= Application.getApp().getDao(N_EMP_JOB_DETAIL.class);
		N_EMP_JOB_DETAIL data_save ;
		ObjUtility obj_util_ = new ObjUtility();
		SimpleDateFormat sf_ = new SimpleDateFormat("dd/MM/yyyy");
		String  empsn_	= "";
		String  id_job_	= "";
		Date	date_b_	= null;
		String  user_up_	= Application.getApp().getLoginInfo().getUserID();
		String  value_cell_ = "";
		boolean flag_data_ok_ = true;
		String 	temp_str	= "";
		Date	date_up = null;
		
		try{
			
			date_up = sf_.parse(sf_.format(new Date()));
			
			in = new FileInputStream(fileExcel);
			HSSFWorkbook wb = new HSSFWorkbook(in);
			HSSFSheet sheet = wb.getSheetAt(0) ;
			HSSFRow row 	;
			int n_row = sheet.getPhysicalNumberOfRows();
			if(n_row <=1){
				return "";
				
			}
			else{
				System.out.println("Begin Import");
				for (int i = 1; i < n_row; i++) {
					System.out.println("========= Row : " + i);
					 data_save = new N_EMP_JOB_DETAIL();
					 flag_data_ok_ = true;
					row = sheet.getRow(i);
					
					// row(i)	:  EMPSN(0)		||	ID_JOB(1)(code_kind_fact)	|| DATE_B(2)(dd/mm/yyyy)	|| NOTE(3)
					
					empsn_ 		= "";
					id_job_		= "";
					date_b_		= null;
					
					for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
						
						if(row.getCell(j)!=null){
							value_cell_ = row.getCell(j).toString();
						}else{
							value_cell_	= "";
						}
						
						switch (j) {
						case 0: // empsn  <-- kiem tra quyen thao tac
							empsn_ = value_cell_;
							if(obj_util_.CheckRight(user_up_, empsn_)){
								data_save.setEMPSN(empsn_);
								data_save.setUSER_UPDATE(user_up_);
								data_save.setDATE_UPDATE(date_up);
								
							}else{
								flag_data_ok_ = false;
								str_error = str_error + empsn_ + " ; ";
							}
							
							break;
						case 1 : // id_job <-- kiem tra su ton tai
							id_job_	= value_cell_;
							if(flag_data_ok_ 
								&& Check_exist_job(id_job_)){  
								
								data_save.setID_JOB(id_job_);
								
							}else{
								flag_data_ok_ = false;
								str_error = id_job_ + str_error + empsn_ + " ; ";
							}
							break;

						case 2 : // date_begin	<-- kiem tra voi ngay trong csdl
							temp_str = value_cell_;
							date_b_ = sf_.parse(temp_str);
							if(flag_data_ok_ 
									&& Check_date_in_DB(empsn_,date_b_)){ 
								
								data_save.setDATE_B(date_b_);
								
							}else{
								flag_data_ok_ = false;
								str_error = str_error + empsn_ + " ; ";
							}
							
							break;
						
						case 3 :
							data_save.setNOTE(Vni2Uni.convertToVNI(value_cell_));
							break;
						default:
							break;
						}
						
					}
					if(flag_data_ok_){
						
						System.out.println(" ==>> " + empsn_ + ": dữ liệu OK ");
						try{
							obj_dao.save(data_save); // update row(i) into database
							
						}catch (Exception e) {
							str_error = str_error + " - "+ empsn_ + " ; ";
						}
					}
				}
				
				
				
			}
		}catch (Exception e) {
			str_error = str_error + empsn_ + " ; ";
		}
		
		return str_error;
	}
	
	private boolean Check_date_in_DB(String empsn_, Date date_b_) {
		// Kiem tra ngay nhap vao tu file Excel phai lon hon ngay trong CSDL?
		IGenericDAO<N_EMP_JOB_DETAIL, String> obj_dao = Application.getApp().getDao(N_EMP_JOB_DETAIL.class);
		DetachedCriteria obj_dc = DetachedCriteria.forClass(N_EMP_JOB_DETAIL.class);
		obj_dc.add(Restrictions.eq("EMPSN", empsn_));
		obj_dc.add(Restrictions.isNull("DATE_E"));
		List<N_EMP_JOB_DETAIL> list_data_check = obj_dao.findByCriteria(1, obj_dc);
		
		if(list_data_check != null && list_data_check.size() > 0){
			N_EMP_JOB_DETAIL obj_data = list_data_check.get(0);
			if(date_b_.after(obj_data.getDATE_B())){
				
			}else{
				return false;
			}
		}
		return true;
		
	}
	private boolean Check_exist_job(String id_job_) {
		// kiem tra id_job trong file Excel co ton tai trong CSDL ?
		IGenericDAO<N_JOB, String> obj_dao = Application.getApp().getDao(N_JOB.class);
		N_JOB obj = obj_dao.findById(id_job_);

		if(obj != null)
			return true;
		return false;
	}
	private void initComponent(){
		this.setTitle("IMPORT");
		this.setHeight(new Extent(250));
		this.setWidth(new Extent(500));
		rootLayout = new Grid();
		rootLayout.setSize(1);
		rootLayout.setForeground(Color.RED);
		rootLayout.add(getFileUploadExcel());
		
		
		rootLayout.add(new Label("	* Ghi chú : tập tin cập nhật phải là file excel (*.xls)"));
		rootLayout.add(new Label(" -   Hàng đầu tiên là tiêu đề bao gồm các cột"));
		rootLayout.add(new Label(" -   Column A : Số thẻ (định dạng Text)"));
		rootLayout.add(new Label(" -   Column B : Mã công việc (định dạng Text) ==> maCV_loaiCV_xuong"));
		rootLayout.add(new Label(" -   Column C : Ngày hiệu lực (định dạng Text"));
		rootLayout.add(new Label(" -   Column D : Ghi chú (định dạng Text)"));
		rootLayout.add(new Label("."));
		rootLayout.add(new Label(" * Sau khi cập nên, nên xuất dữ liệu để kiểm tra"));
		this.add(rootLayout);
	}

}
