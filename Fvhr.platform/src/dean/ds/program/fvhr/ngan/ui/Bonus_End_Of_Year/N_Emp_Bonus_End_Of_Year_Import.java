package ds.program.fvhr.ngan.ui.Bonus_End_Of_Year;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.core.io.Resource;

import ds.program.fvhr.domain.N_EMP_BONUS_END_OF_YEAR;
import ds.program.fvhr.domain.N_EMP_RETAIN_SALARY;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.fv.app.filetransfer.UploadEvent;
import nextapp.echo2.fv.app.filetransfer.UploadListener;
import nextapp.echo2.fv.app.filetransfer.UploadSelect;

public class N_Emp_Bonus_End_Of_Year_Import extends WindowPane {
private ResourceBundle bundle;
	
	Grid 	rootLayout ;
	
	public N_Emp_Bonus_End_Of_Year_Import() {
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
		IGenericDAO<N_EMP_BONUS_END_OF_YEAR, String> obj_dao 	= Application.getApp().getDao(N_EMP_BONUS_END_OF_YEAR.class);
		N_EMP_BONUS_END_OF_YEAR data_save ;
		SimpleDateFormat sf_ 	= new SimpleDateFormat(ObjUtility.DATE_FORMAT_STR);
		String  user_up_		= Application.getApp().getLoginInfo().getUserID();
		Object  value_cell_ 	= "";
		long	value_long	= (long) 0;
		boolean flag_data_ok_ 	= true;
		Date	date_up 		= null;
		int 	n_row			= 0;
		
		
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
					 data_save 		= new N_EMP_BONUS_END_OF_YEAR();
					 flag_data_ok_ 	= true;
					 value_cell_	=	"";
					 data_save.setUP_USER(user_up_);
					 data_save.setUP_DATE(date_up);
					 
					 row = sheet.getRow(i);
					
					// row(i)	:  EMPSN (String)	 ||  Re_salary(Number)    ||		Date_effect (String)
					
					for (int j = 0; j < 5; j++) {
						
							switch (j) {
							case 0: // empsn  <-- KIEM TRA QUYEN THAO TAC
								
									if(row.getCell(j).getCellType() == HSSFCell.CELL_TYPE_STRING){
										value_cell_ = row.getCell(j).getStringCellValue();
										data_save.setEMPSN(value_cell_.toString());
									}else{
										flag_data_ok_	= false;
									}
								break;
								
							case 1 : // RE_SALARY
									
									if(row.getCell(j).getCellType()== HSSFCell.CELL_TYPE_NUMERIC){
											value_long	= (long) (row.getCell(j) == null ? (long)0 : row.getCell(j).getNumericCellValue());
											data_save.setBONUS_M13(value_long);
									}else{
										flag_data_ok_	= false;
									}
								
								break;
							
							case 2: //Tien thuong cua nam nao?
								
								if(row.getCell(j).getCellType() == HSSFCell.CELL_TYPE_STRING){
									value_cell_ = row.getCell(j).getStringCellValue();
									data_save.setYEAR(value_cell_.toString());
								}else{
									flag_data_ok_	= false;
								}
							break;
							
							case 3: //Trang thai
								
								if(row.getCell(j).getCellType() == HSSFCell.CELL_TYPE_STRING){
									value_cell_ = row.getCell(j).getStringCellValue();
									data_save.setSTATUS(value_cell_.toString());
								}else{
									flag_data_ok_	= false;
								}
							break;
							case 4 : // date_effect
								
									Date date_temp = null;
									try{
										data_save.setDATE_EFFECT(row.getCell(j).getDateCellValue());
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
					
							obj_dao.save(data_save); // update row(i) into database
						
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
		rootLayout.add(new Label(" - 	Column B : Tiền thưởng T13 (định dạng : Number) "));
		rootLayout.add(new Label(" - 	Column C : Của năm (định dạng : Text)(Chỉ nhập năm:vd 2011)"));
		rootLayout.add(new Label(" - 	Column D : Trạng thái (định dạng : Text) "));
		rootLayout.add(new Label("                 (Y:Tính chung với tháng tính lương, N:Phát riêng)"));
		rootLayout.add(new Label(" - 	Column E : Tháng tính lương (định dạng : Date - dd/MM/yyyy) "));
		rootLayout.add(new  Label("."));
		rootLayout.add(new Label(" * Sau khi cập nhật, nên export lại dữ liệu để kiểm tra "));
		this.add(rootLayout);
	}


}
