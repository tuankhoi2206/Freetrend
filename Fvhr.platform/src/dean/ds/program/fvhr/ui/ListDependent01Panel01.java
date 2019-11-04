package ds.program.fvhr.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TooManyListenersException;

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

import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_LIST_DEPENDENT;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;

public class ListDependent01Panel01 extends WindowPane {
	
	private String USER_UPDATED = Application.getApp().getLoginInfo().getUserID();
	private Grid rootLayoutGrid;
	private GetEmployeeInfo getEmployeeInfo; 
	private String depsn;
	public ListDependent01Panel01(){
		super();
		initComportnent();
	}

	private void initComportnent() {
		this.setTitle("Import Deduct");
		this.setHeight(new Extent(150,Extent.PX));
		rootLayoutGrid = new Grid();
		add(rootLayoutGrid);
		Label ExcelImportFileName = new Label();
		ExcelImportFileName.setText("File Name: ");
		rootLayoutGrid.add(ExcelImportFileName);
		rootLayoutGrid.add(getFileUploadSelect());
		
	}
	public UploadSelect getFileUploadSelect(){
		final Resource res = Application.getSpringContext().getResource("classpath:/conf/format/dscb");
		UploadSelect fileUploadSelect = new UploadSelect();
		fileUploadSelect.setEnabledSendButtonText("LoadFile");
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
	public void importFromSheet(HSSFSheet sheet) throws Exception{
		int n = sheet.getPhysicalNumberOfRows();
		System.out.println(n);
		HSSFRow row;
		HSSFCell cell;
		IGenericDAO<N_LIST_DEPENDENT, String> dao = Application.getApp().getDao(N_LIST_DEPENDENT.class);
		
		for (int i = 1; i < n ; i++) {
			row = sheet.getRow(i);
			if (row==null) continue;
			N_LIST_DEPENDENT data = new N_LIST_DEPENDENT();
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
				data.setEMPSN(cell.getStringCellValue());
				// tong khau tru
				cell = row.getCell(1);
				data.setDEPENDENT(Long.parseLong(cell.getStringCellValue()));
				// don vi
				getEmployeeInfo = new GetEmployeeInfo(empsn);
				depsn = getEmployeeInfo.getDEPSN();				
				data.setDEPSN(depsn);
				// ghi chu
				cell = row.getCell(2);
				data.setNOTE(cell.getStringCellValue());
				// ngay cap nhat cuoi
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				data.setDATE_UPDATED(sdf.format(new Date()));
				// nguoi cap nhat cuoi
				data.setUSER_UPDATED(USER_UPDATED);
				// insert into database
				dao.saveOrUpdate(data);
			}
		}
	}

}
