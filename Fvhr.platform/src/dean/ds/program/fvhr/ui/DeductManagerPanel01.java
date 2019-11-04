package ds.program.fvhr.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TooManyListenersException;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.fv.app.filetransfer.UploadEvent;
import nextapp.echo2.fv.app.filetransfer.UploadListener;
import nextapp.echo2.fv.app.filetransfer.UploadSelect;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.core.io.Resource;

import ds.program.fvhr.domain.N_DEDUCT_OTHER;
import ds.program.fvhr.domain.N_EMPLOYEE;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;

public class DeductManagerPanel01 extends WindowPane {
	
	private String USER_UPDATED = Application.getApp().getLoginInfo().getUserID();
	//private nextapp.echo2.app.TextArea GhiChu1_CaptionLabel;
	private nextapp.echo2.app.Label GhiChu_CaptionLabel;
	private nextapp.echo2.app.Label GhiChu1_CaptionLabel;
	private nextapp.echo2.app.Label GhiChu2_CaptionLabel;
	private nextapp.echo2.app.Label GhiChu3_CaptionLabel;
	private nextapp.echo2.app.Label GhiChu4_CaptionLabel;
	private nextapp.echo2.app.Label GhiChu5_CaptionLabel;
	private nextapp.echo2.app.Label GhiChu6_CaptionLabel;

	public DeductManagerPanel01(){
		super();
		initComportnent();
	}

	private void initComportnent() {
		this.setTitle("Cập nhật khấu trừ khác");		
		Grid rootLayoutGrid = new Grid();		
		rootLayoutGrid.setSize(2);
		add(rootLayoutGrid);

		GhiChu_CaptionLabel = new nextapp.echo2.app.Label();
		GhiChu_CaptionLabel.setText("Chọn file ");
		rootLayoutGrid.add(GhiChu_CaptionLabel);
		
		rootLayoutGrid.add(getFileUploadSelect());		
		
		GhiChu1_CaptionLabel = new nextapp.echo2.app.Label();
		GhiChu1_CaptionLabel.setForeground(Color.RED);
		GhiChu1_CaptionLabel.setText("Ghi chú ");
		rootLayoutGrid.add(GhiChu1_CaptionLabel);
		
		GhiChu2_CaptionLabel = new nextapp.echo2.app.Label();
		GhiChu2_CaptionLabel.setForeground(Color.RED);
		GhiChu2_CaptionLabel.setText("SoThe(text), TongKhauTru(number)");
		rootLayoutGrid.add(GhiChu2_CaptionLabel);
		
		GhiChu3_CaptionLabel = new nextapp.echo2.app.Label();
		GhiChu3_CaptionLabel.setText("");
		rootLayoutGrid.add(GhiChu3_CaptionLabel);	
		
		GhiChu4_CaptionLabel = new nextapp.echo2.app.Label();
		GhiChu4_CaptionLabel.setForeground(Color.RED);
		GhiChu4_CaptionLabel.setText("ThangKhauTru(date), Ghichu (text)");
		rootLayoutGrid.add(GhiChu4_CaptionLabel);

		GhiChu6_CaptionLabel = new nextapp.echo2.app.Label();
		GhiChu6_CaptionLabel.setText("");
		rootLayoutGrid.add(GhiChu6_CaptionLabel);
		
		GhiChu5_CaptionLabel = new nextapp.echo2.app.Label();
		GhiChu5_CaptionLabel.setForeground(Color.RED);
		GhiChu5_CaptionLabel.setText("QT thue TNCN neu co (number)");
		rootLayoutGrid.add(GhiChu5_CaptionLabel);		
	}
	public UploadSelect getFileUploadSelect(){
		final Resource res = Application.getSpringContext().getResource("classpath:/conf/format/");
		UploadSelect fileUploadSelect = new UploadSelect();
		fileUploadSelect.setEnabledSendButtonText("Cập nhật");
		fileUploadSelect.setWidth(new Extent(350,Extent.PX));
		
		try {
			fileUploadSelect.addUploadListener(new UploadListener() {
				@Override
				public void fileUpload(UploadEvent e) {
					if (!(e.getFileName().endsWith(".xls") || (e.getFileName().endsWith(".XLS")))) {
						Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK, "File phải có định dạng Excel");
						return;
					}					
					try {
						assert res.getFile().exists() && res.getFile().isDirectory() : "";
						File outputFile = new File(res.getFile(), "temp.xls");
						long unwrite = e.getSize();
						OutputStream out = new FileOutputStream(outputFile);
						// Transfer bytes from in to out
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
						importFromExcel(outputFile);
						outputFile = null;
					} catch (IOException e1) {
						// TODO: handle exception
						e1.printStackTrace();
					}
				}

				@Override
				public void invalidFileUpload(UploadEvent e) {
					// strange things happened...
				}
			});
		} catch (TooManyListenersException ex) {
			// TODO: handle exception
			throw new RuntimeException(ex.toString());
		}
		return fileUploadSelect;
	}
	public void importFromExcel(File outputFile){
		InputStream input = null;
		// BOMID =
		// BOMID_ValidateField.getReturnResultSet().get(0).toString().substring(1,
		// BOMID_ValidateField.getReturnResultSet().get(0).toString().length()-1);
		try {
			input = new FileInputStream(outputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			HSSFWorkbook wb = new HSSFWorkbook(input);
			importFromSheet(wb.getSheetAt(0));
		} catch (FileNotFoundException e) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK ,"Không tìm thấy File!");
			e.printStackTrace();
		} catch (Exception e) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK ,"Excel lỗi!");
			e.printStackTrace();
		}
		
	}
	public void importFromSheet(HSSFSheet sheet){
		int n = sheet.getPhysicalNumberOfRows();
		System.out.println(" "+ n);
		HSSFRow row;
		HSSFCell cell;
		IGenericDAO<N_DEDUCT_OTHER, String> dao = Application.getApp().getDao(N_DEDUCT_OTHER.class);
		N_DEDUCT_OTHER data = null;
		for (int i = 1; i < n ; i++) {
			row = sheet.getRow(i);
			if (row==null) continue;
			data = new N_DEDUCT_OTHER();
			cell = row.getCell(0);			
			// kiem tra xem co so the trong bang chua
			String empsn = cell.getStringCellValue();
			if (!empsn.matches("[0-9]{8}"))
			{
				
			}				
			IGenericDAO<N_EMPLOYEE, String> dao_employee = Application.getApp().getDao(N_EMPLOYEE.class);
			N_EMPLOYEE emp = dao_employee.findById(empsn);
			if (emp!=null)
			{
				// so the
				data.setEMPSN(cell.getStringCellValue().toString());
				System.out.println(cell.getStringCellValue().toString());
				// tong khau tru
				cell = row.getCell(1);
				
				//data.setTOTAL_DEDUCT(BigDecimal.valueOf(Long.valueOf(cell.getStringCellValue())));
				data.setTOTAL_DEDUCT(BigDecimal.valueOf(cell.getNumericCellValue()));
				//data.setTOTAL_DEDUCT(Long.valueOf(cell.getStringCellValue()));
				// thang khau tru
				cell = row.getCell(2);	
				//System.out.println(cell.getStringCellValue());
				//String str_date = cell.getStringCellValue();
				SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
				Date date = cell.getDateCellValue();
			    sf.format(date);		
				
				data.setMONTH_DEDUCT(date);
				// ghi chu
				cell = row.getCell(3);
				String strNote=cell.getStringCellValue();
				if (cell.getStringCellValue().equals(null)) {
					data.setNOTE("");
				}
				else{
					data.setNOTE(cell.getStringCellValue());
				}
				
				
				//QT thue TNCN neu co
				cell = row.getCell(4);
				data.setQT_PAYTAX(BigDecimal.valueOf(cell.getNumericCellValue()));				
				
				
				// ngay cap nhat cuoi
				Date date_update= new Date();
				data.setDATE_UPDATED(date_update);
				// nguoi cap nhat cuoi
				data.setUSER_UPDATED(USER_UPDATED);
				// insert into database
				try{
					dao.saveOrUpdate(data);
				}
				catch (Exception e) {
					System.out.println(cell.getStringCellValue());
				}
			}
		}
	}

}
