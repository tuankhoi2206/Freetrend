package ds.program.fvhr.ui.insurance;

import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.ui.insurance.DSNgayCongBaoHiem;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import dsc.echo2app.Application;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.program.DefaultProgram;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscDateField;
import dsc.util.function.UUID;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;

public class HienHanh_NgayCong extends DefaultProgram  {

	private ResourceBundle resourceBundle;
	private Grid grid1;
	private Label label1;
	private DscDateField dscDFThoiGian;
	private Column column1;
	private Column column2;
	private Label label2;
	private SelectField sfIdDept;
	private SelectField sfFact;
	private Label label3;
	private SelectField sfLean;
	private Label label4;
	private Label label5;
	private SelectField sfGroupDept;
	private Row row1;
	private Row row2;
	private Row row3;
	private Row row4;
	private Row row5;
	private Label label6;
	private RadioButton rDuCong;
	private RadioButton rKoDuCong;
	private RadioButton rKoPhanBietNgayCong;
	private RadioButton rThangTruBH;
	private RadioButton rNghiSan;
	private Button bttExcel;
	private Row row6;
	private Button bttCancel;	
	private InsuranceDAO insDAO;	
	private SimpleDateFormat sdf;
	private CheckBox cbNghiViec;
	private Button btnCapNhatKyTrinh;
	private Button btnXuatKyTrinh;	

	/**
	 * Creates a new <code>HienHanh_NgayCong</code>.
	 */
	public HienHanh_NgayCong() {
		super();

		// Add design-time configured components.
		initComponents();		
		moreInit();
		
	}

	private void moreInit() {
		// TODO Auto-generated method stub
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		insDAO = new InsuranceDAO();
		dscDFThoiGian.getDateChooser().setLocale(Locale.ENGLISH);
		dscDFThoiGian.setDateFormat(sdf);
		// gan don vi
		insDAO.ganDeptFactGroupCombobox(sfIdDept, sfFact, sfLean, sfGroupDept);
		// filter
		sfFact.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				ListBinder.bindSelectField(sfIdDept, FVGenericInfo.getDept(sfFact.getSelectedItem().toString()), false);
				ListBinder.bindSelectField(sfLean, FVGenericInfo.getGroup(sfFact.getSelectedItem().toString()), true);
				ListBinder.bindSelectField(sfGroupDept, FVGenericInfo.getDeptName(sfFact.getSelectedItem().toString()), true);	
			}
		});
		
		sfLean.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				ListBinder.bindSelectField(sfIdDept, FVGenericInfo.getDept(sfFact.getSelectedItem().toString(), sfLean.getSelectedItem().toString()), false);
				ListBinder.bindSelectField(sfGroupDept, FVGenericInfo.getDeptName(sfFact.getSelectedItem().toString(), sfLean.getSelectedItem().toString()), true);
			}
		});		
		
/*		bttCancel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				reset();
			}

		});	
		bttExcel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doExport();
			}
		});
		*/
		// gan button cho cap nhat ky trinh, QT20 va xuat ky trinh
		btnCapNhatKyTrinh = new Button();
		btnCapNhatKyTrinh.setText("Cập Nhật Ngày công");
		btnCapNhatKyTrinh.setFont(new Font(null, Font.BOLD, new Extent(12, Extent.PT)));
		btnCapNhatKyTrinh.setInsets(new Insets(new Extent(80, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		btnCapNhatKyTrinh.setBorder(new Border(new Extent(1, Extent.PX), Color.BLACK,
				Border.STYLE_NONE));
		RowLayoutData btnCapNhatKyTrinhLayoutData = new RowLayoutData();
		btnCapNhatKyTrinhLayoutData.setInsets(new Insets(new Extent(50, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		btnCapNhatKyTrinh.setLayoutData(btnCapNhatKyTrinhLayoutData);
		row6.add(btnCapNhatKyTrinh);		
		
		btnXuatKyTrinh = new Button();
		btnXuatKyTrinh.setText("Xuất ký trình Rody");
		btnXuatKyTrinh.setFont(new Font(null, Font.BOLD, new Extent(12, Extent.PT)));
		btnXuatKyTrinh.setInsets(new Insets(new Extent(80, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		btnXuatKyTrinh.setBorder(new Border(new Extent(1, Extent.PX), Color.BLACK,
				Border.STYLE_NONE));
		RowLayoutData btnXuatKyTrinhLayoutData = new RowLayoutData();
		btnXuatKyTrinhLayoutData.setInsets(new Insets(new Extent(50, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		btnCapNhatKyTrinh.setLayoutData(btnXuatKyTrinhLayoutData);
		row6.add(btnXuatKyTrinh);		
	}

	private void doExport()  {
		// TODO Auto-generated method stub
		StopWatch sw = new StopWatch();
		if (rDuCong.isSelected()){
			try {
				doDSNgayCong("1");
				//Application.getApp().showMessageDialog(MessageDialog.TYPE_INFORMATION+MessageDialog.CONTROLS_OK, "OK");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (rKoDuCong.isSelected()){
			try {
				doDSNgayCong("0");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (rKoPhanBietNgayCong.isSelected()){
			try {
				doDSNgayCong("2");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void doDSNgayCong(String trangThai) throws IOException {
		// TODO Auto-generated method stub
		// tham so truyen vao : 0 ko du cong, 1: du cong; 2: ko phan biet ngay cong+ hop dong
		Calendar thangBaoCao = Calendar.getInstance();
		thangBaoCao = dscDFThoiGian.getSelectedDate();
		String dkDonVi = insDAO.getFactCondition(sfIdDept, sfFact, sfLean, sfGroupDept);
		
		boolean flagCheckBox = cbNghiViec.isSelected();
		// true la nghi viec, false la hien hanh
		List<DSNgayCongBaoHiem> list = insDAO.getDSNgayCongBaoHiem(dkDonVi,thangBaoCao.getTime(), trangThai,flagCheckBox);
		
		HSSFWorkbook wb = insDAO.getWorkbook("insurance", "ds_NgayCong.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFRow templateRow = sheet.getRow(0);
		HSSFCell cell;
		int r = 2;
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		System.out.print(list.size());
		for (int i=0; i<list.size();i++){
			DSNgayCongBaoHiem data = list.get(i);
			row = sheet.createRow(r);
			
			for (int j=0;j<templateRow.getPhysicalNumberOfCells();j++){
				copyCell(templateRow.getCell(j), row.createCell(j));
			}				
			
			cell = row.getCell(0);
			cell.setCellValue(i+1);
			cell = row.getCell(1);			
			cell.setCellValue(data.getEmpsn());
			cell = row.getCell(2);						
			cell.setCellValue(data.getHoTen());
			cell = row.getCell(3);
			cell.setCellValue(data.getTenDonVi());
			cell = row.getCell(4);			
			cell.setCellValue(data.getLuongCoBan());
			cell = row.getCell(5);			
			cell.setCellValue(data.getLuongHopDong());
			cell = row.getCell(6);			
			cell.setCellValue(data.getTsNgayLamNghiCoLuong());
			cell = row.getCell(7);			
			cell.setCellValue(data.getTsNghiSan());
			cell = row.getCell(8);			
			cell.setCellValue(data.getTsNghiBuBaoVe());
			cell = row.getCell(9);			
			cell.setCellValue(data.getTsNghiCoPhep());
			cell = row.getCell(10);			
			cell.setCellValue(data.getTsNghiKhangCong());
			cell = row.getCell(11);			
			if (data.getNgayKyHD()!=null){
				cell.setCellValue(data.getNgayKyHD());
			}
			else{
				cell.setCellValue("");
			}
			
			cell = row.getCell(12);		
			if (data.getNgayHetHanHD()!=null){
				cell.setCellValue(data.getNgayHetHanHD());
			}
			else{
				cell.setCellValue("");
			}
			
			cell = row.getCell(13);		
			if (data.getThoiHanHopDong()!=null){
				cell.setCellValue(data.getThoiHanHopDong());
			}
			else{
				cell.setCellValue("");
			}
			
			cell = row.getCell(14);			
			cell.setCellValue(data.getTienDongBHXH());
			cell = row.getCell(15);			
			cell.setCellValue(data.getTienDongBHTN());
			cell = row.getCell(16);			
			cell.setCellValue(data.getNgayNhapXuong());
			cell = row.getCell(17);			
			cell.setCellValue(data.getMaDonVi());
			
			cell = row.getCell(18);
			cell.setCellValue(data.getChucVu());			
			cell = row.getCell(19);			
			cell.setCellValue(data.getPhuCapChucVu());
			cell = row.getCell(20);			
			cell.setCellValue(data.getCongViec());
			cell = row.getCell(21);			
			cell.setCellValue(data.getPhuCapCongViec());
/*			
			cell = row.getCell(22);			
			cell.setCellValue(data.getPhuCapSinhHoat());
			cell = row.getCell(23);			
			cell.setCellValue(data.getPhuCapXangDau());						
			cell = row.getCell(24);			
			cell.setCellValue(data.getPhuCapSanLuong());
			cell = row.getCell(25);			
			cell.setCellValue(data.getBuLuongThangTruoc());
			*/
			
			cell = row.getCell(26);	
			
			if (data.getGhiChuNghiViec()!=null){
				cell.setCellValue(data.getGhiChuNghiViec());
			}
			else{
				cell.setCellValue("");
			}		
			
			r++;			
		}
		save(wb, "ds_NgayCong");
	}
	
	public void copyCell(HSSFCell cell1, HSSFCell cell2) {
        switch(cell1.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cell2.setCellValue(cell1.getBooleanCellValue());
                cell2.setCellStyle(cell1.getCellStyle());
                break;
            case Cell.CELL_TYPE_ERROR:
                cell2.setCellErrorValue(cell1.getErrorCellValue());
                cell2.setCellStyle(cell1.getCellStyle());
                break;
            case Cell.CELL_TYPE_FORMULA:
                cell2.setCellFormula(cell1.getCellFormula());
                cell2.setCellStyle(cell1.getCellStyle());
                break;
            case Cell.CELL_TYPE_STRING:
                cell2.setCellValue(cell1.getStringCellValue());
                cell2.setCellStyle(cell1.getCellStyle());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                cell2.setCellValue(cell1.getNumericCellValue());
                cell2.setCellStyle(cell1.getCellStyle());
                break;    
        }
    }

	private void save(HSSFWorkbook wb, String fileName) throws IOException{
		File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
		FileOutputStream out = new FileOutputStream(f);
		wb.write(out);
		out.flush();
		out.close();
		File saveFile = new File(f.getParentFile(),URLEncoder.encode(getLoginInfo().getUserID() + ";application/vnd.ms-excel;" + fileName + "_" + System.currentTimeMillis() + ".xls", "UTF-8"));			
		saveFile.deleteOnExit();
		ReportFileManager.saveTempReportFile(f, saveFile);
		Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
	}
	
	public String getViewerUrl() {
		HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
		String viewerUrl = request.getRequestURL() + "?" +
		WebRenderServlet.SERVICE_ID_PARAMETER + "=" + ReportService.INSTANCE.getId() + "&" + 
		ReportService.PARAM_TYPE + "=" + ReportService.TYPE_TEMP + "&" +
		ReportService.PARAM_KEY + "=";
		return viewerUrl;
	}
	


	private void reset() {
		// TODO Auto-generated method stub
		sfFact.setSelectedIndex(0);
		sfLean.setSelectedIndex(0);
		sfGroupDept.setSelectedIndex(0);
		sfIdDept.setSelectedIndex(0);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		grid1 = new Grid();
		grid1.setWidth(new Extent(650, Extent.PX));
		grid1.setBorder(new Border(new Extent(1, Extent.PX), Color.BLACK,
				Border.STYLE_SOLID));
		add(grid1);
		column1 = new Column();
		grid1.add(column1);
		row1 = new Row();
		column1.add(row1);
		label1 = new Label();
		label1.setText("Thời Gian ");
		RowLayoutData label1LayoutData = new RowLayoutData();
		label1LayoutData.setInsets(new Insets(new Extent(20, Extent.PX),
				new Extent(5, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		label1.setLayoutData(label1LayoutData);
		row1.add(label1);
		dscDFThoiGian = new DscDateField();
		RowLayoutData dscDFThoiGianLayoutData = new RowLayoutData();
		dscDFThoiGianLayoutData.setInsets(new Insets(new Extent(40, Extent.PX),
				new Extent(5, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		dscDFThoiGian.setLayoutData(dscDFThoiGianLayoutData);
		row1.add(dscDFThoiGian);
		row3 = new Row();
		column1.add(row3);
		label3 = new Label();
		label3.setText("Xưởng ");
		RowLayoutData label3LayoutData = new RowLayoutData();
		label3LayoutData.setInsets(new Insets(new Extent(20, Extent.PX),
				new Extent(5, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		label3.setLayoutData(label3LayoutData);
		row3.add(label3);
		sfFact = new SelectField();
		RowLayoutData sfFactLayoutData = new RowLayoutData();
		sfFactLayoutData.setInsets(new Insets(new Extent(59, Extent.PX),
				new Extent(5, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		sfFactLayoutData.setWidth(new Extent(100, Extent.PX));
		sfFact.setLayoutData(sfFactLayoutData);
		row3.add(sfFact);
		row4 = new Row();
		column1.add(row4);
		label4 = new Label();
		label4.setText("Nhóm ");
		RowLayoutData label4LayoutData = new RowLayoutData();
		label4LayoutData.setInsets(new Insets(new Extent(20, Extent.PX),
				new Extent(5, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		label4.setLayoutData(label4LayoutData);
		row4.add(label4);
		sfLean = new SelectField();
		sfLean.setInsets(new Insets(new Extent(60, Extent.PX), new Extent(5,
				Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		RowLayoutData sfLeanLayoutData = new RowLayoutData();
		sfLeanLayoutData.setInsets(new Insets(new Extent(64, Extent.PX),
				new Extent(5, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		sfLeanLayoutData.setWidth(new Extent(200, Extent.PX));
		sfLean.setLayoutData(sfLeanLayoutData);
		row4.add(sfLean);
		row5 = new Row();
		column1.add(row5);
		label5 = new Label();
		label5.setText("Nhóm Đơn Vị ");
		RowLayoutData label5LayoutData = new RowLayoutData();
		label5LayoutData.setInsets(new Insets(new Extent(20, Extent.PX),
				new Extent(5, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		label5.setLayoutData(label5LayoutData);
		row5.add(label5);
		sfGroupDept = new SelectField();
		RowLayoutData sfGroupDeptLayoutData = new RowLayoutData();
		sfGroupDeptLayoutData.setInsets(new Insets(new Extent(17, Extent.PX),
				new Extent(5, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		sfGroupDeptLayoutData.setWidth(new Extent(200, Extent.PX));
		sfGroupDept.setLayoutData(sfGroupDeptLayoutData);
		row5.add(sfGroupDept);
		row2 = new Row();
		column1.add(row2);
		label2 = new Label();
		label2.setText("Mã Đơn Vị ");
		label2.setTextPosition(new Alignment(Alignment.LEFT, Alignment.BOTTOM));
		RowLayoutData label2LayoutData = new RowLayoutData();
		label2LayoutData.setInsets(new Insets(new Extent(20, Extent.PX),
				new Extent(5, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		label2.setLayoutData(label2LayoutData);
		row2.add(label2);
		sfIdDept = new SelectField();
		RowLayoutData sfIdDeptLayoutData = new RowLayoutData();
		sfIdDeptLayoutData.setWidth(new Extent(100, Extent.PX));
		sfIdDeptLayoutData.setInsets(new Insets(new Extent(35, Extent.PX),
				new Extent(5, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		sfIdDept.setLayoutData(sfIdDeptLayoutData);
		row2.add(sfIdDept);
		Row row7 = new Row();
		column1.add(row7);
		cbNghiViec = new CheckBox();
		cbNghiViec.setText("NV Nghỉ Việc");
		cbNghiViec.setInsets(new Insets(new Extent(120, Extent.PX), new Extent(
				5, Extent.PX), new Extent(0, Extent.PX), new Extent(0,
				Extent.PX)));
		row7.add(cbNghiViec);
		column2 = new Column();
		column2.setInsets(new Insets(new Extent(50, Extent.PX), new Extent(5,
				Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		grid1.add(column2);
		label6 = new Label();
		label6.setText("Thông Tin Lựa Chọn");
		column2.add(label6);
		rDuCong = new RadioButton();
		rDuCong.setSelected(true);
		rDuCong.setText("Danh sách đủ công");
		ButtonGroup gg = new ButtonGroup();
		rDuCong.setGroup(gg);
		column2.add(rDuCong);
		rKoDuCong = new RadioButton();
		rKoDuCong.setText("Danh sách không đủ công");
		rKoDuCong.setGroup(gg);
		column2.add(rKoDuCong);
		rKoPhanBietNgayCong = new RadioButton();
		rKoPhanBietNgayCong.setText("Không phân biệt ngày công");
		rKoPhanBietNgayCong.setGroup(gg);
		column2.add(rKoPhanBietNgayCong);
		rThangTruBH = new RadioButton();
		rThangTruBH.setText("Danh sách trừ bảo hiểm trong tháng");
		rThangTruBH.setGroup(gg);
		column2.add(rThangTruBH);
		rNghiSan = new RadioButton();
		rNghiSan.setText("Danh sách nghỉ sản trong tháng");
		rNghiSan.setGroup(gg);
		column2.add(rNghiSan);
		row6 = new Row();
		row6.setInsets(new Insets(new Extent(20, Extent.PX), new Extent(20,
				Extent.PX), new Extent(20, Extent.PX), new Extent(0, Extent.PX)));
		GridLayoutData row6LayoutData = new GridLayoutData();
		row6LayoutData.setColumnSpan(2);
		row6.setLayoutData(row6LayoutData);
		grid1.add(row6);
		bttExcel = new Button();
		bttExcel.setText("Xuất Excel");
		bttExcel.setFont(new Font(null, Font.BOLD, new Extent(12, Extent.PT)));
		bttExcel.setInsets(new Insets(new Extent(80, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		bttExcel.setBorder(new Border(new Extent(1, Extent.PX), Color.BLACK,
				Border.STYLE_NONE));
		RowLayoutData bttExcelLayoutData = new RowLayoutData();
		bttExcelLayoutData.setInsets(new Insets(new Extent(50, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		bttExcel.setLayoutData(bttExcelLayoutData);
		row6.add(bttExcel);
		bttCancel = new Button();
		bttCancel.setText("Bỏ Qua");
		bttCancel.setFont(new Font(null, Font.BOLD, new Extent(12, Extent.PT)));
		bttCancel.setInsets(new Insets(new Extent(100, Extent.PX), new Extent(
				0, Extent.PX), new Extent(0, Extent.PX), new Extent(0,
				Extent.PX)));
		row6.add(bttCancel);
	}
}
