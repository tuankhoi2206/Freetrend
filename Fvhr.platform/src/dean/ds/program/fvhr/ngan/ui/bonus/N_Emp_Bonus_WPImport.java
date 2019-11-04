package ds.program.fvhr.ngan.ui.bonus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.io.Resource;

import fv.util.Vni2Uni;
import ds.program.fvhr.domain.N_EMP_BONUS;
import ds.program.fvhr.domain.N_EMP_JOB_DETAIL;
import ds.program.fvhr.son.ui.ObjUtility;
import ds.program.fvhr.util.OBJ_EMPSN;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.fv.app.filetransfer.UploadEvent;
import nextapp.echo2.fv.app.filetransfer.UploadListener;
import nextapp.echo2.fv.app.filetransfer.UploadSelect;

public class N_Emp_Bonus_WPImport extends WindowPane {

	private ResourceBundle bundle;
	
	Grid 	rootLayout ;
	
	public N_Emp_Bonus_WPImport() {
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
						userClose();
						outputFile = null;
						sw.stop();
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
	
	
	private String importProperties(File fileExcel){
		String str_error 		= "";
		InputStream in ;
		IGenericDAO<N_EMP_BONUS, String> obj_dao 	= Application.getApp().getDao(N_EMP_JOB_DETAIL.class);
		N_EMP_BONUS data_save ;
		SimpleDateFormat sf_ 	= new SimpleDateFormat(ObjUtility.DATE_FORMAT_STR);
		String  user_up_		= Application.getApp().getLoginInfo().getUserID();
		Object  value_cell_ 	= "";
		Double	value_double	= (double) 0;
		boolean flag_data_ok_ 	= true;
		Date	date_up 		= null;
		int 	n_row			= 0;
		String  depsn 		    = "";
		String  empsn			= "";
		OBJ_EMPSN Obj_e		    = new OBJ_EMPSN();
		
		try{
			
			date_up = sf_.parse(sf_.format(new Date()));
			
			in = new FileInputStream(fileExcel);
			HSSFWorkbook wb = new HSSFWorkbook(in);
			HSSFSheet sheet = wb.getSheetAt(0) ;
			HSSFRow row 	;
			n_row = sheet.getPhysicalNumberOfRows();
			if(n_row <=1){
				return "Tập tin không có dữ liệu để cập nhật vào chương trình";
			}
			else{
				System.out.println("Begin Import");
				for (int i = 1; i < n_row; i++) {
					System.out.println("========= Row : " + i);
					 data_save 		= new N_EMP_BONUS();
					 flag_data_ok_ 	= true;
					 value_cell_	=	"";
					 data_save.setUSER_UP(user_up_);
					 data_save.setDATE_UP(date_up);
					 
					 row = sheet.getRow(i);
					
					// row(i)	:  EMPSN (String)		||		Bonus1 (number)		|| 		Bonus5 (number)		|| 		Bonus8 (number)		||		Date_effect (String) || User_up(String) || Date_up(Date) || Depsn(String)
					
					for (int j = 0; j < 5; j++) {
						
							switch (j) {
							case 0: // empsn  <-- KIEM TRA QUYEN THAO TAC
								
									if(row.getCell(j).getCellType() == HSSFCell.CELL_TYPE_STRING){
										value_cell_ = row.getCell(j).getStringCellValue();
										data_save.setEMPSN(value_cell_.toString());
										empsn   = value_cell_.toString();
										
									}else{
										flag_data_ok_	= false;
									}
								break;
								
							case 1 : // bonus1
									
									if(row.getCell(j).getCellType()== HSSFCell.CELL_TYPE_NUMERIC){
											value_double	= row.getCell(j) == null ? (double)0 : row.getCell(j).getNumericCellValue();
											data_save.setBONUS1(value_double);
									}else{
										flag_data_ok_	= false;
									}
								
								break;
								
							case 2 : // bonus5
								if(row.getCell(j).getCellType()== HSSFCell.CELL_TYPE_NUMERIC){
										value_double	= row.getCell(j) == null ? (double)0 : row.getCell(j).getNumericCellValue();
										data_save.setBONUS5(value_double);
								}else{
									flag_data_ok_	= false;
								}
								break;
							
							case 3 : // bonus8
								if(row.getCell(j).getCellType()== HSSFCell.CELL_TYPE_NUMERIC){
										value_double	= row.getCell(j) == null ? (double)0 : row.getCell(j).getNumericCellValue();
										data_save.setBONUS8(value_double);
								}else{
									flag_data_ok_	= false;
								}
								break;

							case 4 : // date_effect
								
									Date date_temp = null;
									try{
										data_save.setDATE_EFFECT(row.getCell(j).getDateCellValue());
										date_temp = row.getCell(j).getDateCellValue();
										depsn     = Obj_e.Get_depsn(empsn, date_temp);
										data_save.setDEPSN(depsn);
									}catch (Exception e) {
										flag_data_ok_	= false;
									}
								break;
								
							default:
								break;
							}
					}
					if(flag_data_ok_){
						
						System.out.println(" ==>> : dữ liệu OK ");
						try{
						obj_dao.save(data_save); // update row(i) into database
						}catch (Exception e) {
							str_error = str_error + "Kiểm tra lại vì số thẻ " +data_save.getEMPSN() +" này đã được cập nhật rồi !!!???" ;
						}
						
					}else{
							str_error = "Kiểm tra lại thông tin tại các số thẻ không thể cập nhật sau : " + str_error + " - "+ value_cell_ + " ; ";
					}
				}
				
				
				
			}
		}catch (Exception e) {
			str_error = "Kiểm tra lại thông tin tại các số thẻ không thể cập nhật sau : " + str_error + " - "+ value_cell_ + " ; ";
		}
		
		if(str_error.equals("")){
			str_error = " Cập nhật thành công " + (n_row -1) + " số thẻ";
		}
		
		return str_error;
	}
	
	
	private void initComponent(){
		this.setTitle("IMPORT");
		this.setStyleName("Default.Window");
		this.setWidth(new Extent(450));
		this.setHeight(new Extent(300));
		rootLayout = new Grid();
		rootLayout.setSize(1);
		rootLayout.setForeground(Color.RED);
		
		rootLayout.add(new  Label("."));
		rootLayout.add(getFileUploadExcel());
		
		rootLayout.add(new Label(" * GHI CHÚ : "));
		rootLayout.add(new Label(" * Chỉ cập nhật file excel 2003 (*.xls) theo định dạng :"));
		rootLayout.add(new Label(" -	Hàng đầu tiên là tiêu đề bao gồm các cột : "));
		rootLayout.add(new Label(" - 	Column A : Số thẻ (định dạng : Text)  "));
		rootLayout.add(new Label(" - 	Column B : Tiền thưởng (định dạng : Number) "));
		rootLayout.add(new Label(" - 	Column C : Bù lương (định dạng : Number) "));
		rootLayout.add(new Label(" - 	Column D : Phụ cấp xăng dầu (định dạng : Number) "));
		rootLayout.add(new Label(" - 	Column E : Ngày hiệu lực (định dạng : Date - dd/MM/yyyy) "));
		rootLayout.add(new  Label("."));
		rootLayout.add(new Label(" * Sau khi cập nhật, nên export lại dữ liệu để kiểm tra "));
		this.add(rootLayout);
	}

}
