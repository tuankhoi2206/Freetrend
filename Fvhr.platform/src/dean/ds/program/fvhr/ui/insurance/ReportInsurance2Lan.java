package ds.program.fvhr.ui.insurance;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.ListModel;

import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.record.formula.functions.Value;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DefaultProgram;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.info.LoginInfo;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Grid;
import echopointng.GroupBox;
import echopointng.model.CalendarEvent;
import echopointng.model.CalendarSelectionListener;
import fv.components.SelectItem;
import fv.util.DateUtils;
import fv.util.FVGenericInfo;
import fv.util.HSSFUtils;
import fv.util.ListBinder;
import fv.util.MonthYearType;
import fv.util.ReportUtils;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.TextField;

public class ReportInsurance2Lan extends DefaultProgram {

	private ResourceBundle resourceBundle;
	private DscDateField dfNgayBaoCao;
	private SelectField sfThang;
	private SelectField sfNam;
	private DscDateField dfNgayBaoGiam;
	private Label lblBaoGiamTu;
	private Label lblBaoGiamDen;
	private SelectField sfMaDonVi;
	private SelectField sfXuong;
	private SelectField sfNhom;
	private SelectField sfDonVi;
	private RadioButton radTang1Tay;
	private RadioButton radCapSo1Tay;
	private RadioButton radGiamTuan;
	private RadioButton radGiamTrongTangMoi;
	private RadioButton radDieuChinhHoSo;
	private RadioButton radQT201;
	private Button btnExport;
	private Button btnImport;
	private Button btnKhoa;
	private Button btnMoKhoa;
	private Button btnKiemTra;
	private Button btnReset;
	private int demLDTang;
	private int demDCTang;
	private int demBSTang;
	private int demLDGiam;
	private int demDCGiam;
	private int demBSBHYTGiam;
	private int demBSLDGiam;
	private Double tyleBHXH;
	private Double tyleBHTN;
	private InsuranceDAO insDAO;
	private Double tyleBHYT;
	private String thangHienTai;
	private CheckBox cbTheGiaHan;
	private SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
	private SplitPane splitPane1;
	private RadioButton radQT202;
	HSSFRow templateRow; // dong chuan cho bao cao QT20 
	private RadioButton radTang15Tay;
	private int loaiBaoGiam;
	private int dotBaoGiam;
	private Calendar ngayBaoGiam;
	// so lieu cua ky bao cao lien ke truoc do
	private BigDecimal soLDKyTruoc;
	private BigDecimal tongQLKyTruoc;
	private BigDecimal soPhaiDongKyTruoc;
	// so lieu cua ky bao cao hien tai
	private BigDecimal soLD;
	private BigDecimal tongQL;
	private BigDecimal soPhaiDong;
	private TextField txtDotBaoCao;	
	/**
	 * Creates a new <code>ReportInsurance082013</code>.
	 */
	public ReportInsurance2Lan() {
		super();

		// Add design-time configured components.
		initComponents();
		moreInit();
	}

	//Author HA, 072013
	private void GiaTriKhoiTao() {
		demLDTang = 0;
		demDCTang = 0;
		demBSTang = 0;
		demLDGiam = 0;
		demDCGiam = 0;
		demBSBHYTGiam = 0;
		demBSLDGiam=0;
		tyleBHXH = Double.parseDouble(insDAO.GetField("GIATRI3", "N_THAMSO",
				"TENTHAMSO", "", "", "TYLEBHXH", "", ""));
		tyleBHTN = Double.parseDouble(insDAO.GetField("GIATRI3", "N_THAMSO",
				"TENTHAMSO", "", "", "TYLEBHTN", "", ""));
		tyleBHYT = Double.parseDouble(insDAO.GetField("GIATRI3", "N_THAMSO",
				"TENTHAMSO", "", "", "TYLEBHYT", "", ""));
		thangHienTai = "";
		//1: TangMoi1Tay, 2: Giam Trong Tang Moi, 3: Gia Han The, 4: QT20Tay, 5: Giam Tuan
		loaiBaoGiam =0;
		dotBaoGiam	=0;
		ngayBaoGiam = null;
		//03/09/2013
		soLDKyTruoc 		= BigDecimal.valueOf(0);
		tongQLKyTruoc 		= BigDecimal.valueOf(0);
		soPhaiDongKyTruoc	= BigDecimal.valueOf(0);
		soLD 				= BigDecimal.valueOf(0);
		tongQL 				= BigDecimal.valueOf(0);
		soPhaiDong			= BigDecimal.valueOf(0);		
	}	
	
	/**
	 * Month 1-base
	 * 
	 * @return 1-12
	 */
	public int getMonth() {
		return Integer.parseInt(sfThang.getSelectedItem().toString());
	}

	public int getYear() {
		return Integer.parseInt(sfNam.getSelectedItem().toString());
	}
	
	private void optSelected(ActionEvent e) {
		if (e.getActionCommand().equals("ra5")){
			ngayBaoGiamChanged();
		}
		else{
			dfNgayBaoCao.setSelectedDate(dfNgayBaoGiam.getSelectedDate());
		}
	}
	
	private boolean checkReport(){
		boolean kq=true;
		//String dotbc = txtDotBaoCao.getText();
		if(radCapSo1Tay.isSelected()==true || radDieuChinhHoSo.isSelected()==true) {
			kq =true;
		}		
		else{
			if(txtDotBaoCao.getText().trim().isEmpty()){
				Application.getApp().showMessageDialog(
						MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
						"Phai co thong tin cua dot bao giam");
				kq=false;			
			}
		}
		return kq;
	}
	
	private void ngayBaoGiamChanged() {
		
		Calendar ngayBaoGiam = dfNgayBaoGiam.getSelectedDate();
		// if la bao giam tuan thi chon T2 or T5
		if (radGiamTuan.isSelected())
		{
			if (ngayBaoGiam.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY
					&& ngayBaoGiam.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
				Application.getApp().showMessageDialog(
						MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
						"Chỉ được chọn thứ 2 hoặc thứ 5");
				lblBaoGiamTu.setText(null);
				lblBaoGiamDen.setText(null);				
				return;
			}
			else{
				String[] thoiGian = DateUtils.getThoiGianGiamTuan(dfNgayBaoGiam
						.getSelectedDate());
				lblBaoGiamTu.setText(thoiGian[0]);
				lblBaoGiamDen.setText(thoiGian[1]);				
			}
		}
		else{
			lblBaoGiamTu.setText(null);
			lblBaoGiamDen.setText(null);			
		}
		
		dfNgayBaoCao.setSelectedDate(ngayBaoGiam);
	}
	
	private void moreInit() {		
		TextField txtDotBaoCao = new TextField();	
		CheckBox cbTheGiaHan = new CheckBox();
		insDAO = new InsuranceDAO();
		Calendar cal = Calendar.getInstance();
		GiaTriKhoiTao();

		radTang15Tay.setVisible(false);		
		DefaultListModel monthModel = new DefaultListModel();
		monthModel.add("01");
		monthModel.add("02");
		monthModel.add("03");
		monthModel.add("04");
		monthModel.add("05");
		monthModel.add("06");
		monthModel.add("07");
		monthModel.add("08");
		monthModel.add("09");
		monthModel.add("10");
		monthModel.add("11");
		monthModel.add("12");
		sfThang.setModel(monthModel);
		int month = cal.get(Calendar.MONTH);// bat dau tu 0->11
		sfThang.setSelectedIndex(month);// vo tinh trung voi index cua
										// selectfield cua thang
		DefaultListModel yearModel = new DefaultListModel();
		int year = cal.get(Calendar.YEAR);
		yearModel.add(year - 1);
		yearModel.add(year);
		yearModel.add(year + 1);
		sfNam.setModel(yearModel);
		sfNam.setSelectedIndex(1);

		dfNgayBaoCao.setSelectedDate(cal);
		dfNgayBaoGiam.setSelectedDate(cal);
		dfNgayBaoCao.getDateChooser().setLocale(new Locale("en"));
		dfNgayBaoGiam.getDateChooser().setLocale(new Locale("en"));
		dfNgayBaoCao.setDateFormat(sdf);
		dfNgayBaoGiam.setDateFormat(sdf);
		Calendar cal1 = Calendar.getInstance();
		while (cal1.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY
				&& cal1.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
			cal1.add(Calendar.DAY_OF_MONTH, -1);
		}
		dfNgayBaoGiam.setSelectedDate(cal1);
		//ngayBaoGiamChanged();
		dfNgayBaoGiam.getModel().addListener(new CalendarSelectionListener() {

			public void displayedDateChange(CalendarEvent arg0) {
			}

			public void selectedDateChange(CalendarEvent arg0) {
				ngayBaoGiamChanged();
			}

		});

		ButtonGroup group2 = new ButtonGroup();
		radTang1Tay.setGroup(group2);
		radCapSo1Tay.setGroup(group2);		
		radTang15Tay.setGroup(group2);
		radGiamTuan.setGroup(group2);		
		radGiamTrongTangMoi.setGroup(group2);		
		radDieuChinhHoSo.setGroup(group2);
		radQT201.setGroup(group2);
		radQT202.setGroup(group2);
		// Dept

		ListBinder
				.bindSelectField(sfMaDonVi, FVGenericInfo.getAllDept(), false);
		ListBinder
				.bindSelectField(sfXuong, FVGenericInfo.getFactories(), false);
		ListBinder.bindSelectField(sfNhom, FVGenericInfo.getAllGroup(), true);
		ListBinder.bindSelectField(sfDonVi, FVGenericInfo.getAllDeptName(),
				true);
		
		// filter
		
		sfMaDonVi.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub				
				String fact 		= FVGenericInfo.findFactFollowDept(sfMaDonVi.getSelectedItem().toString());	
				String group		= FVGenericInfo.findGroup(sfMaDonVi.getSelectedItem().toString());
				String ndeptName	= FVGenericInfo.findDeptNameFolowDept(sfMaDonVi.getSelectedItem().toString());
				System.out.println(ndeptName);
				SelectItem item = new SelectItem(fact,fact);
				sfXuong.setSelectedIndex(((DefaultListModel)sfXuong.getModel()).indexOf(item));				
				item = new SelectItem(group,group);
				sfNhom.setSelectedIndex(((DefaultListModel)sfNhom.getModel()).indexOf(item));
				// ì la SelectItem thi String se chuyen ve Unicode, con Object thi giu nguyen moi tim duoc
				item = new SelectItem(fv.util.Vni2Uni.convertToUnicode(ndeptName),ndeptName);
				sfDonVi.setSelectedIndex(((DefaultListModel)sfDonVi.getModel()).indexOf(item));
			}
		});
		
		sfXuong.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				ListBinder.bindSelectField(sfMaDonVi, FVGenericInfo
						.getDept(sfXuong.getSelectedItem().toString()), false);
				ListBinder.bindSelectField(sfNhom, FVGenericInfo
						.getGroup(sfXuong.getSelectedItem().toString()), true);
				ListBinder.bindSelectField(sfDonVi, FVGenericInfo
						.getDeptName(sfXuong.getSelectedItem().toString()),
						true);
			}
		});
		sfNhom.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				ListBinder.bindSelectField(sfMaDonVi, FVGenericInfo.getDept(
						sfXuong.getSelectedItem().toString(),
						fv.util.Vni2Uni.convertToVNI(sfNhom.getSelectedItem().toString())), false);
				ListBinder.bindSelectField(sfDonVi, FVGenericInfo.getDeptName(
						sfXuong.getSelectedItem().toString(), 
						fv.util.Vni2Uni.convertToVNI(sfNhom.getSelectedItem().toString())), true);
			}
		});
		
		sfDonVi.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String maDonVi = FVGenericInfo.findDept(sfXuong.getSelectedItem().toString()
												, fv.util.Vni2Uni.convertToVNI(sfNhom.getSelectedItem().toString())
												, fv.util.Vni2Uni.convertToVNI(sfDonVi.getSelectedItem().toString()));
				SelectItem item = new SelectItem(maDonVi,maDonVi);
				sfMaDonVi.setSelectedIndex(((DefaultListModel)sfMaDonVi.getModel()).indexOf(item));
			}
		});
		// Button
		btnExport.addActionListener(new ActionListener() {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				doExport();
			}
		});
		btnReset.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doReset();
			}
		});
		
		// tam thoi an button import excel va kiem tra giam BS BHYT
		
		btnImport.setVisible(false);
		btnKiemTra.setVisible(false);
	}
	
	public void doReset() {
		// TODO Auto-generated method stub
		sfDonVi.setSelectedIndex(-1);
		sfMaDonVi.setSelectedIndex(-1);
		sfNhom.setSelectedIndex(-1);
		sfXuong.setSelectedIndex(-1);
		txtDotBaoCao.setText("");
		GiaTriKhoiTao();
		lblBaoGiamTu.setText(null);
		lblBaoGiamDen.setText(null);
		if (cbTheGiaHan.isSelected()){
			cbTheGiaHan.setSelected(false);
		}
	}
	
	private void doExportTang1Tay() throws IOException {
		insDAO.setMonth(Integer.parseInt(sfThang.getSelectedItem().toString()));
		insDAO.setYear(Integer.parseInt(sfNam.getSelectedItem().toString()));
		List<Tang1TayObject> list = insDAO.getDSTang1Tay(insDAO
				.getFactCondition(sfMaDonVi, sfXuong, sfNhom, sfDonVi), sfThang
				.getSelectedItem().toString(), sfNam.getSelectedItem()
				.toString());
		HSSFWorkbook wb = insDAO.getWorkbook("insurance",
				"Mau_D02-TS_cong_thuc_Tang1Tay.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		templateRow = sheet.getRow(10);
		HSSFCell cell;
		FormulaEvaluator evaluator = wb.getCreationHelper()
				.createFormulaEvaluator();

		// gan thoi gian hien tai cho cell Y3 trong file mau excel
		// java dem tu 0, excel dem tu 1
		HSSFRow rowTGHienHanh = sheet.getRow(2);// la row thu 3 trong excel
		cell = rowTGHienHanh.getCell(24);
		cell.setCellValue(insDAO.ToDate(thangHienTai));

		// aa
		int startRow = 12;
		int rowToCopy = 11;
		int rowTongHop = 42;
		//int incRows = list.size() - 1;
		int incRows = list.size();
		if (incRows > 0) {
			sheet.shiftRows(startRow, sheet.getLastRowNum(), incRows, false,
					true);
		}

		for (int i = startRow; i < startRow + incRows; i++) {
			HSSFUtils.copyRowM(wb, sheet, templateRow, i);
		}

		rowToCopy = 11;
		rowTongHop = rowTongHop+incRows;
		// aa

		for (int i = 0; i < list.size(); i++) {
			Tang1TayObject data = list.get(i);

			/*
			 * // copy va insert them 1 row moi tren file mau sheet.shiftRows(r,
			 * sheet.getLastRowNum(), 1, true, false); row = sheet.createRow(r);
			 * row = sheet.getRow(r);
			 * 
			 * //row =sheet.createRow(r); // cai nay la copy de len dong hien
			 * tai dang co tren file mau
			 * 
			 * for (int j=0;j<templateRow.getPhysicalNumberOfCells();j++){
			 * copyCell(templateRow.getCell(j), row.createCell(j)); }
			 */
			row = sheet.getRow(rowToCopy);
			cell = row.getCell(0);
			cell.setCellValue(i + 1);
			cell = row.getCell(1);
			cell.setCellValue(data.getHoTen());
			cell = row.getCell(2);
			if (data.getMaSoBHXH() == null) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(data.getMaSoBHXH());
			}

			 cell = row.getCell(3);
			 cell.setCellValue(data.getNgaySinh());

			cell = row.getCell(6); // luong o muc old
			cell.setCellValue(0);

			cell = row.getCell(11);//luong muc new old 9
			cell.setCellValue(data.getLuong().intValue());
			cell = row.getCell(16);// old 14
			cell.setCellValue(insDAO.ToDate(data.getThangTangMoi()));
			cell = row.getCell(20);// old 19
			cell.setCellValue(data.getGhiChu());

			// System.out.println(data.getEmpsn());

			cell = row.getCell(21); // old 20
			cell.setCellValue(data.getEmpsn());

			cell = row.getCell(22);
			cell.setCellValue(tyleBHXH);
			cell = row.getCell(23);
			cell.setCellValue(tyleBHYT);
			cell = row.getCell(24);
			cell.setCellValue(tyleBHTN);

			for (int k=25; k<48; k++){
				cell = row.getCell(k);
				// bat dau gan cong thuc			
				evaluator = wb.getCreationHelper().createFormulaEvaluator();			
				evaluator.evaluateFormulaCell(cell);				
				//cell.setCellFormula(cell.getCellFormula());	
			}
			
			
			cell = row.getCell(48); // old 41
			cell.setCellValue(data.getEmpsn());
			cell = row.getCell(49); // old 42
			cell.setCellValue(data.getFactGroup());
			cell = row.getCell(50); // old 43
			cell.setCellValue(data.getDonVi());

			rowToCopy++;
		}
		try {				
			row= sheet.getRow(rowTongHop);

			BigDecimal[] kyNay = insDAO.getSoLieuKyTruocKyNay(wb, sheet, row, rowTongHop, cell, evaluator, soLDKyTruoc, tongQLKyTruoc, thangHienTai, dotBaoGiam, soLD, tongQL, soPhaiDong);
			//if chay toan bo cty moi update
			if (sfXuong.getSelectedIndex()==-1 && sfNhom.getSelectedIndex()==-1 && sfDonVi.getSelectedIndex()==-1)
			{
				soLD = kyNay[0];
				tongQL = kyNay[1];
				soPhaiDong = kyNay[2];
				insDAO.capNhatDotBaoGiam(loaiBaoGiam, dotBaoGiam,ngayBaoGiam.toString() , "", "", thangHienTai, soLD, tongQL, soPhaiDong);
			}
			ReportUtils.doExportExcel(wb, "DSTangMoi");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void doExportCapSoMoi() throws IOException {
		insDAO.setMonth(Integer.parseInt(sfThang.getSelectedItem().toString()));
		insDAO.setYear(Integer.parseInt(sfNam.getSelectedItem().toString()));
		List<Tang1TayObject> list = insDAO.getDSTang1Tay(insDAO
				.getFactCondition(sfMaDonVi, sfXuong, sfNhom, sfDonVi), sfThang
				.getSelectedItem().toString(), sfNam.getSelectedItem()
				.toString());
		HSSFWorkbook wb = insDAO.getWorkbook("insurance",
				"Mau A01a-TS_CapSoMoi.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		templateRow = sheet.getRow(10);
		HSSFCell cell;
		int r = 11;

		// gan thoi gian hien tai cho cell Y3 trong file mau excel
		// java dem tu 0, excel dem tu 1
		/*
		 * HSSFRow rowTGHienHanh = sheet.getRow(2);// la row thu 3 trong excel
		 * cell = rowTGHienHanh.getCell(24);
		 * cell.setCellValue(insDAO.ToDate(thangHienTai));
		 */

		int startRow = 12;
		int rowToCopy = 11;
		int incRows = list.size() - 1;
		if (incRows > 0) {
			sheet.shiftRows(startRow, sheet.getLastRowNum(), incRows, false,
					true);
		}

		for (int i = startRow; i < startRow + incRows; i++) {
			HSSFUtils.copyRowM(wb, sheet, templateRow, i);
		}

		rowToCopy = 11;
		for (int i = 0; i < list.size(); i++) {
			Tang1TayObject data = list.get(i);
			row = sheet.getRow(rowToCopy);
			cell = row.getCell(0);
			cell.setCellValue(i + 1);
			cell = row.getCell(1);
			cell.setCellValue(data.getHoTen());
			// cell 2 mac dinh la null
			cell = row.getCell(3);
			if (data.getMaSoBHXH() == null) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(data.getMaSoBHXH());
			}

			cell = row.getCell(4);
			cell.setCellValue(data.getNgaySinh());

			// if la nu thi danh dau X
			cell = row.getCell(5);
			if (data.getGioiTinh().equals("NAM")) {
				cell.setCellValue("");
			} else {
				cell.setCellValue("X");
			}
			cell = row.getCell(6); // dan toc
			cell.setCellValue(data.getDanToc());

			cell = row.getCell(7);
			if (data.getCMND() == null) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(data.getCMND());
			}

			cell = row.getCell(8);
			if (data.getNgayCap() == null) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(data.getNgayCap());
			}

			cell = row.getCell(9);
			if (data.getNoiCap() == null) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(data.getNoiCap());
			}

			cell = row.getCell(10);
			cell.setCellValue(data.getHoKhau());

			cell = row.getCell(11);
			cell.setCellValue(data.getIdPro());
			cell = row.getCell(12);
			cell.setCellValue(data.getIdHos());
			
			cell = row.createCell(15);
			HSSFUtils.fill(cell, data.getFactGroup() + "." + data.getDonVi());
			cell = row.createCell(16);
			HSSFUtils.fill(cell, data.getEmpsn());

			rowToCopy++;
		}
		
		try {
			ReportUtils.doExportExcel(wb, "DSCapSoMoi");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void doExportGiamTangMoi() {
		// TODO Auto-generated method stub
		int month = getMonth();
		int year = getYear();
		Calendar ng = dfNgayBaoCao.getSelectedDate();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(ng.getTimeInMillis());
		cal.add(Calendar.MONTH, -1);
		String dkFact = insDAO.getFactCondition(sfMaDonVi, sfXuong, sfNhom,
				sfDonVi);
		List<Map<String, Object>> list = insDAO.getGiamTangMoiList(dkFact,
				month, year);
		Map<String, BigDecimal> thamso = insDAO.getThamSo();
		Date thangBC = DateUtils.getFirstDay(month, year);

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(ng.getTimeInMillis());
		c.set(Calendar.DAY_OF_MONTH, 1);
		if (ng.get(Calendar.DAY_OF_MONTH) > 20) {
			c.add(Calendar.MONTH, 1);
		}

		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance",
				"Mau_D02-TS_cong_thuc_GiamTuan.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		templateRow = sheet.getRow(10);
		HSSFCell cell;

		// fill header
		row = sheet.getRow(2);
		cell = row.getCell(24);
		cell.setCellValue(c);

		int rowToCopy = 21;
		int startRow = 22;
		int stt = 1;

		int incRows = list.size() - 1;
		if (incRows > 0) {
			sheet.shiftRows(startRow, sheet.getLastRowNum(), incRows, false,
					true);
			startRow += incRows;
			rowToCopy += incRows;
		}

		rowToCopy = 21;
		startRow = 22;

		for (int i = startRow; i < startRow + incRows; i++) {
			HSSFUtils.copyRowM(wb, sheet, templateRow, i);
		}

		rowToCopy = 21;
		startRow = 22;
		int luongCB;

		for (int i = 0; i < list.size(); i++) {
			// fill increase
			row = sheet.getRow(rowToCopy);
			Map<String, Object> data = list.get(i);
			String emp = (String) data.get("EMPSN");
			cell = row.getCell(0);// STT
			HSSFUtils.fill(cell, i + 1);
			cell = row.getCell(1);// Ho va ten
			HSSFUtils.fill(cell, data.get("FNAME") + " " + data.get("LNAME"));
			cell = row.getCell(2);// Ma so BHXH
			HSSFUtils.fill(cell, data.get("ID_SOCIAL"));
			cell = row.getCell(3);// Ngay sinh
			HSSFUtils.fill(cell, data.get("BIRTHDAY"));
			cell = row.getCell(4);// Gioi tinh
			if ("NU".equals(data.get("SEX")))
				HSSFUtils.fill(cell, "X");
			else
				HSSFUtils.fill(cell, "");
			cell = row.getCell(5);// Chuc vu
			HSSFUtils.fill(cell, data.get("POSITION"));

			int dc_TangMoi = Integer.parseInt(insDAO.GetField("count(*)",
					"n_dcluong_dongloat", "to_char(ngaydc,'" + "dd/mm/yyyy"
							+ "')", "baodc_tangmoi", "", sdf.format(thangBC),
					"Y", ""));
			int sal_dcGiam = 0;
			if (dc_TangMoi == 1) {
				sal_dcGiam = Integer.parseInt(insDAO.GetField("sotienthaydoi",
						"n_dcluong_dongloat", "to_char(ngaydc,'" + "dd/mm/yyyy"
								+ "')", "baodc_tangmoi", "",
						sdf.format(thangBC), "Y", ""));
			} else {
				sal_dcGiam = 0;
			}

			luongCB = Integer.parseInt(data.get("BSALARY").toString())
					- sal_dcGiam;

			cell = row.getCell(6);// Tien luong
			HSSFUtils.fill(cell, luongCB);

			cell = row.getCell(16);// Giam tu thang
			HSSFUtils.fill(cell, data.get("MONTH_BC"));
			cell = row.getCell(20);// Ghi chu
			HSSFUtils.fill(cell, data.get("NOTE_TANG"));
			cell = row.getCell(22);// Ti le BHXH
			HSSFUtils.fill(cell, thamso.get("TYLEBHXH"));
			cell = row.getCell(23);// Ti le BHYT
			HSSFUtils.fill(cell, thamso.get("TYLEBHYT"));
			cell = row.getCell(24);// Ti le BHTN
			HSSFUtils.fill(cell, thamso.get("TYLEBHTN"));
			cell = row.createCell(48);// So the
			HSSFUtils.fill(cell, emp);
			cell = row.createCell(49);// Xuong
			HSSFUtils.fill(cell, data.get("NAME_GROUP"));
			cell = row.createCell(50);// Don vi
			HSSFUtils.fill(cell, data.get("NAME_DEPT_NAME"));
			// eval formula
			cell = row.getCell(18);
			HSSFUtils.evalFormula(wb, cell);
			for (int k = 25; k < 48; k++) {
				cell = row.getCell(k);
				HSSFUtils.evalFormula(wb, cell);
			}
			rowToCopy++;
		}
		try {
			ReportUtils.doExportExcel(wb, "GiamTangMoi");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void doExportThayDoiTTBHQuy() {
		// TODO Auto-generated method stub
		int month = getMonth();
		int year = getYear();
		Calendar ng = dfNgayBaoCao.getSelectedDate();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(ng.getTimeInMillis());
		String dkFact = insDAO.getFactCondition(sfMaDonVi, sfXuong, sfNhom,
				sfDonVi);
		List<Map<String, Object>> list = insDAO.getThayDoiTTBHList(dkFact,
				month, year);
		Date thangBC = DateUtils.getFirstDay(month, year);

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(ng.getTimeInMillis());
		c.set(Calendar.DAY_OF_MONTH, 1);
		if (ng.get(Calendar.DAY_OF_MONTH) > 20) {
			c.add(Calendar.MONTH, 1);
		}

		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance",
				"Mau D07-TS.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		templateRow = sheet.getRow(10);
		HSSFCell cell;

		int rowToCopy = 7;
		int startRow = 8;
		int stt = 1;

		int incRows = list.size() - 1;
		if (incRows > 0) {
			sheet.shiftRows(startRow, sheet.getLastRowNum(), incRows, false,
					true);
			startRow += incRows;
			rowToCopy += incRows;
		}

		rowToCopy = 7;
		startRow = 8;

		for (int i = startRow; i < startRow + incRows; i++) {
			HSSFUtils.copyRowM(wb, sheet, templateRow, i);
		}

		rowToCopy = 7;
		startRow = 8;
		for (int i = 0; i < list.size(); i++) {
			// fill increase
			row = sheet.getRow(rowToCopy);
			Map<String, Object> data = list.get(i);
			String emp = (String) data.get("EMPSN");
			cell = row.getCell(0);// STT
			HSSFUtils.fill(cell, i + 1);
			cell = row.getCell(1);// Ho va ten
			HSSFUtils.fill(cell, data.get("FNAME") + " " + data.get("LNAME"));
			cell = row.getCell(2);// Ma so BHXH
			HSSFUtils.fill(cell, data.get("ID_SOCIAL"));
			cell = row.getCell(3);// Noi dung dieu chinh
			HSSFUtils.fill(cell, "Đ/c thông tin");
			cell = row.getCell(4);// Thong tin old
			HSSFUtils.fill(cell, data.get("TT_OLD"));
			cell = row.getCell(5);// Thong tin new
			HSSFUtils.fill(cell, data.get("TT_NEW"));
			cell = row.getCell(6);// Thang thay doi
			HSSFUtils.fill(cell, data.get("MONTH_BC"));

			cell = row.createCell(9);// Xuong+ don vi
			HSSFUtils.fill(cell, data.get("NAME_DEPT"));
			cell = row.createCell(10);// So the
			HSSFUtils.fill(cell, emp);
			cell = row.createCell(11);// Thang thay doi
			HSSFUtils.fill(cell, data.get("MONTH_BC"));

			rowToCopy++;
		}
		try {
			ReportUtils.doExportExcel(wb, "ThayDoiTTBH");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	private void doExportQT20(){
		int month = getMonth();
		int year = getYear();
		Calendar ng = dfNgayBaoCao.getSelectedDate();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(ng.getTimeInMillis());
		cal.add(Calendar.MONTH, -1);
		String dkFact = insDAO.getFactCondition(sfMaDonVi, sfXuong, sfNhom,
				sfDonVi);
		String tableName =null;
		tableName =radQT201.isSelected()?"N_SOCIAL_INFOR_REPORT":"N_SOCIAL_INFOR_REPORT_2";
		int lanBC =radQT201.isSelected()?1:2;
		StopWatch sw = new StopWatch();
		sw.reset();
		sw.start();
		List<Map<String, Object>> list = insDAO.getQT20TayList(dkFact,tableName,month, year,lanBC);		
		sw.stop();
		System.out.println("Run in sql " + (float) sw.getTime() / 6000 + " s");
		
		sw.reset();
		sw.start();
		List<String> listNSRoRaCuoiQuy = insDAO.getNSRoRaT6T12(month, year);		
		Map<String, BigDecimal> thamso = insDAO.getThamSo();
		Date thangBC = DateUtils.getFirstDay(month, year);
		Date ngayBaoGiam = ng.getTime();
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(ng.getTimeInMillis());
		c.set(Calendar.DAY_OF_MONTH, 1);
		if (ng.get(Calendar.DAY_OF_MONTH) > 20) {
			c.add(Calendar.MONTH, 1);
		}

		//HSSFWorkbook wb = ReportUtils.loadTemplate("insurance",	"Mau_D02-TS_cong_thuc_GiamTuan_2Lan.xls");
		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance","Mau_D02-TS_cong_thuc_GiamTuan.xls");
		
		HSSFSheet sheet = wb.getSheetAt(0);
		
		HSSFRow row;
		templateRow = sheet.getRow(11);	
		
		int rowTongHop = 42;
		
		HSSFCell cell;
		FormulaEvaluator evaluator = wb.getCreationHelper()
				.createFormulaEvaluator();

		// fill header
		row = sheet.getRow(2);
		cell = row.getCell(24);
		cell.setCellValue(c);	
		// Trong java row bat dau tu 0, excel bat dau tu 1
		
		// gan thoi gian hien tai cho cell Y3 trong file mau excel
		// java dem tu 0, excel dem tu 1
		HSSFRow rowTGHienHanh = sheet.getRow(2);// la row thu 3 trong excel
		cell = rowTGHienHanh.getCell(24);
		cell.setCellValue(insDAO.ToDate(thangHienTai));
		
		int rowCopyTangLD = 11, rowCopyTangDC = 13, rowCopyTangBHYT = 16;
		int rowCopyGiamLD = 21, rowCopyGiamDC = 23, rowCopyGiamBHYT = 25;
		int rowBDTangLD = 12, rowBDTangDC = 14, rowBDTangBHYT = 17;
		int rowBDGiamLD = 22, rowBDGiamDC = 24, rowBDGiamBHYT = 26;
		
		// Phat sinh them ds tang BS BHYT khi bao giam %RoRa--> tang BS BHYT
		// Phat sinh them ds thoai thu BHYT khi bao RoVao/DiLamLai va DCLuong giam		
		List<Map<String, Object>> listTangCopy = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listGiamCopy = new ArrayList<Map<String, Object>>();		
		List<Map<String, Object>> listDCCopy = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> data = list.get(i);
			String emp = (String) data.get("EMPSN");
			String note = (String) data.get("NOTE_TANG");
			String loaids = (String) data.get("DCDLOAT");
			//DCDLOAT co 4 ghi chu : Tang, Giam , DCBT va DCDLOAT
			if (loaids.equals("Tang"))		
				demLDTang++;
			else if (loaids.equals("Giam"))
				demLDGiam++;
			else demDCTang++;
			
			Date thangNSvao = null;
			thangNSvao = insDAO.getThangNSVao(emp, sdf.format(thangBC));
			//System.out.println(emp);
			boolean roVaoTuNSRoRa= true;// if ko co thong tin nhi san thi mac dinh RoVaotuNSRoRa la true
			if (thangNSvao!=null)			
				roVaoTuNSRoRa = insDAO.Check_RoVaoTuNSRoRa_2Lan(emp, sdf.format(thangNSvao), sdf.format(thangBC), sdf.format(ngayBaoGiam));
			// if la %RoRa thi se bao tang BS BHYT, NSRa se ko bao tang BS BHYT
			// if ko ton tai trong DS NS-->RoRa cuoi quy truoc ( ds gia han the BHYT) va bi gio la %RoRa thi se bao tang BS BHYT
			// 		or neu co ton tai trong ds NS_RoRa cuoi quy truoc nhung da co RoVao ke tu thoi diem NS_RoRa thi van xet tang BS BHYT binh thuong
			// else la ko tang BS BHYT
			// luong tang BS BHYT = luong cua thang gan nhat co tham gia BHXH-TN
			if( 	( (listNSRoRaCuoiQuy.contains(emp) == false )	|| (listNSRoRaCuoiQuy.contains(emp)== true && roVaoTuNSRoRa==true) )
					&& (note.equals("RoRa") || note.equals("NS-->RoRa"))
			) 
			{
				// fetch more info				
				listTangCopy.add(data);
				demBSTang++;
			}
			// luong thoai thu BHYT = luong cua thang gan nhat co tham gia BHXH-TN
			// if NS-->RoRa thang cuoi quy truoc bi gio RoVao
			// or DiLamLai, co nen thoai thu den nghi viec tang lai trong thang dau quy hay ko ?
			if(  ((listNSRoRaCuoiQuy.contains(emp)== true && roVaoTuNSRoRa==false) && (note.equals("RoVao") ) )
					|| (note.equals("Di lam lai"))
			) 
			{
				// fetch more info
				listGiamCopy.add(data);
				demBSBHYTGiam++;
			}
			
			// DCLuong  giam
			if (note.equals("DCLuong")) 
			{
				BigDecimal luongGanNhat = insDAO.getLuongBaoGiamBHYT(emp, thangBC);
				BigDecimal luongThangBC = (BigDecimal) data.get("BSALARY");
				// if la DCLuong theo dang giam, VD tu 3000-> 2000
				if (luongThangBC.compareTo(luongGanNhat) == -1) {
					// fetch more info
					listDCCopy.add(data);
					data.put("NOTE_TANG", "DCGIAM");
					demDCGiam++;
					demDCTang--;
				}
			}
		}
		
		if (demLDTang > 0) {
			sheet.shiftRows(rowBDTangLD, sheet.getLastRowNum(), demLDTang, false,
					true);
			rowBDTangDC += demLDTang;
			rowBDTangBHYT += demLDTang;
			rowBDGiamLD += demLDTang;
			rowBDGiamDC += demLDTang;
			rowBDGiamBHYT += demLDTang;
			
			rowCopyTangDC += demLDTang;
			rowCopyTangBHYT += demLDTang;
			rowCopyGiamLD += demLDTang;
			rowCopyGiamDC += demLDTang;
			rowCopyGiamBHYT += demLDTang;			
		}
		if (demDCTang > 0) {
			sheet.shiftRows(rowBDTangDC, sheet.getLastRowNum(), demDCTang, false,
					true);
			rowBDTangBHYT += demDCTang;
			rowBDGiamLD += demDCTang;
			rowBDGiamDC += demDCTang;
			rowBDGiamBHYT += demDCTang;
			
			rowCopyTangBHYT += demDCTang;
			rowCopyGiamLD += demDCTang;
			rowCopyGiamDC += demDCTang;
			rowCopyGiamBHYT += demDCTang;				
		}		
		if (demBSTang > 0) {
			sheet.shiftRows(rowBDTangBHYT, sheet.getLastRowNum(), demBSTang, false,
					true);
			rowBDGiamLD += demBSTang;
			rowBDGiamDC += demBSTang;
			rowBDGiamBHYT += demBSTang;
			
			rowCopyGiamLD += demBSTang;
			rowCopyGiamDC += demBSTang;
			rowCopyGiamBHYT += demBSTang;				
		}
		if (demLDGiam > 0) {
			sheet.shiftRows(rowBDGiamLD, sheet.getLastRowNum(), demLDGiam, false,
					true);
			rowBDGiamDC += demLDGiam;
			rowBDGiamBHYT += demLDGiam;
			
			rowCopyGiamDC += demLDGiam;
			rowCopyGiamBHYT += demLDGiam;				
		}
		if (demDCGiam > 0) {
			sheet.shiftRows(rowBDGiamDC, sheet.getLastRowNum(), demDCGiam, false,
					true);
			rowBDGiamBHYT += demDCGiam;
			rowCopyGiamBHYT += demDCGiam;
			
		}		
		if (demBSBHYTGiam > 0) {
			sheet.shiftRows(rowBDTangBHYT, sheet.getLastRowNum(), demBSBHYTGiam, false,
					true);
		}
		
		for (int i = rowBDTangLD; i < rowBDTangLD + demLDTang; i++) {
			//System.out.println("Tang");
			HSSFUtils.copyRowM(wb, sheet, templateRow, i);
		}
		for (int i = rowBDTangDC; i < rowBDTangDC + demDCTang; i++) {
			//System.out.println("TangDC");
			HSSFUtils.copyRowM(wb, sheet, templateRow, i);
		}		
		for (int i = rowBDTangBHYT; i < rowBDTangBHYT + demBSTang; i++) {
			//System.out.println("TangBS");
			HSSFUtils.copyRowM(wb, sheet, templateRow, i);
		}	
		for (int i = rowBDGiamLD; i < rowBDGiamLD + demLDGiam; i++) {
			//System.out.println("Giam");
			HSSFUtils.copyRowM(wb, sheet, templateRow, i);
		}	
		for (int i = rowBDGiamDC; i < rowBDGiamDC + demDCGiam; i++) {
			//System.out.println("GiamDC");
			HSSFUtils.copyRowM(wb, sheet, templateRow, i);
		}		
		for (int i = rowBDGiamBHYT; i < rowBDGiamBHYT + demBSBHYTGiam; i++) {
			//System.out.println("GiamBS");
			HSSFUtils.copyRowM(wb, sheet, templateRow, i);
		}	

		//Dang chuan bi gan du lieu o day, 20/07/2013
		//List : ds all- DCGiam
		//ListTangCopy : la ds nhung nguoi %RoRa co tang BS BHYT
		//ListGiamCopy : la ds nhung nguoi RoVao/DiLamLai co thoai thu BHYT		
		for (int i = 0; i < list.size()-demDCGiam; i++) 
		{
			// fill increase
			//System.out.println(i);
			//row = sheet.getRow(rowToCopy);			
			int rowForm =0;// dong chuan cho tung loai bao cao
			Map<String, Object> data = list.get(i);
			String note = (String) (data.get("DCDLOAT"));
			if (note.equals("Tang"))
			{
				rowForm= rowCopyTangLD;
				rowCopyTangLD++;
			}
			else if (note.equals("DCBT") || note.equals("DCDL") )
			{
				rowForm= rowCopyTangDC;
				rowCopyTangDC++;
			}
			// vi con 1 tinh huong la DCGIAM
			else if (note.equals("Giam"))
			{
				rowForm = rowCopyGiamLD;
				rowCopyGiamLD++;
			}
			
			row= sheet.getRow(rowForm);
			insDAO.ganExcelTuThang(i+1, rowForm, thangBC,insDAO.ToDate(thangHienTai), "", thamso, data, wb, sheet, row, cell);
			
		}	
		
		for (int i = 0; i < listTangCopy.size(); i++) {
			// fill increase			
			row = sheet.getRow(rowCopyTangBHYT);			
			Map<String, Object> data = listTangCopy.get(i);		
			insDAO.ganExcelTuThang(i+1, rowCopyTangBHYT, thangBC,insDAO.ToDate(thangHienTai), "BHYT", thamso, data, wb, sheet, row, cell);
			rowCopyTangBHYT++;
		}
		
		for (int i = 0; i < listGiamCopy.size(); i++) {
			// fill increase
			
			row = sheet.getRow(rowCopyGiamBHYT);			
			Map<String, Object> data = listGiamCopy.get(i);
			insDAO.ganExcelTuThang(i+1, rowCopyGiamBHYT, thangBC,insDAO.ToDate(thangHienTai), "BHYT", thamso, data, wb, sheet, row, cell);
			rowCopyGiamBHYT++;
		}	
		
		for (int i = 0; i < listDCCopy.size(); i++) {
			// fill increase
			
			row = sheet.getRow(rowCopyGiamDC);			
			Map<String, Object> data = listDCCopy.get(i);
			insDAO.ganExcelTuThang(i+1, rowCopyGiamDC, thangBC,insDAO.ToDate(thangHienTai), "", thamso, data, wb, sheet, row, cell);
			rowCopyGiamDC++;			
		}		
				
		
		try {
			rowTongHop = rowTongHop+demLDTang+demDCTang+demBSTang+demLDGiam+demDCGiam+demBSBHYTGiam;					
			row= sheet.getRow(rowTongHop);
			BigDecimal[] kyNay = insDAO.getSoLieuKyTruocKyNay(wb, sheet, row, rowTongHop, cell, evaluator, soLDKyTruoc, tongQLKyTruoc, thangHienTai, dotBaoGiam, soLD, tongQL, soPhaiDong);
			//if chay toan bo cty moi update
			if (sfXuong.getSelectedIndex()==-1 && sfNhom.getSelectedIndex()==-1 && sfDonVi.getSelectedIndex()==-1)
			{
				soLD = kyNay[0];
				tongQL = kyNay[1];
				soPhaiDong = kyNay[2];
				insDAO.capNhatDotBaoGiam(loaiBaoGiam, dotBaoGiam,ngayBaoGiam.toString() , "", "", thangHienTai, soLD, tongQL, soPhaiDong);
			}			
			ReportUtils.doExportExcel(wb, "QT20Tay_"+lanBC);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		sw.stop();
		System.out.println("Run export " + (float) sw.getTime() / 1000 + " s");
	}
	
	private int Export_TangDC1ThangOrBS(HSSFWorkbook wb, HSSFSheet sheet,
			HSSFRow row, HSSFCell cell, BieuMau3A data, int demTang1,
			//int demDC1, int demTangBSBHYT, int demLDGiam,
			int demDC1, int demTangBSBHYT, int demLDGiam,int demBSLDGiam,
			int demThoaiThuBSBHYT, int dongChuan, int sal1, int sal2,
			String tuThang, String denThang, String noteBS) {
		int demNew = 0;
		int rowTangOrDCorBS = 0;// la rowindex cua tang 1 thang/ DC 1 thang/
								// Tang BS BHYT
		// gan gia tri truoc khi tang bien dem
		int rowStart = dongChuan + 1;
		int rowCopy = dongChuan;
		int stt =0;		
		// them 1 dong tang 1 thang or dc 1 thang khi bao giam CNV
		int demTangOrDCorBS = 0;
		// tang so luong record cho tung truong hop

		// Tang, DC, Tang BSBHYT deu tren ld Giam nen ko can thay doi rowStart
		// va Rowcopy
		// vi khi truyen vao DA LAY DONG CHUAN + SO BIEN DEM
		if (noteBS.compareTo("Taêng 1 Thaùng") == 0) {
			demTangOrDCorBS = demTang1 + 1;
			stt=demTangOrDCorBS;
			rowTangOrDCorBS = dongChuan + demTangOrDCorBS;
		} else if (noteBS.compareTo("Ñ/C Löông") == 0) {
			demTangOrDCorBS = demDC1 + 1;
			stt=demTangOrDCorBS;
			rowTangOrDCorBS = dongChuan + demTangOrDCorBS + demTang1;
		} else if (noteBS.compareTo("Taêng BSBHYT") == 0) {
			demTangOrDCorBS = demTangBSBHYT + 1;
			stt=demTangOrDCorBS;
			rowTangOrDCorBS = dongChuan + demTangOrDCorBS + demTang1 + demDC1;
		}
		// BSLDGiam, Thoai thu BSBHYT deu duoi ld Giam nen phai thay doi rowStart
		// va Rowcopy		
		else if (noteBS.compareTo("Ro") == 0) {
			demTangOrDCorBS = demBSLDGiam + 1;
			stt=demLDGiam+demTangOrDCorBS;
			//rowTangOrDCorBS = dongChuan + demTangOrDCorBS + demTang1 + demDC1+demTangBSBHYT;
			//rowTangOrDCorBS = dongChuan;// chi can tang 1 vi da cong demTang1,demDC1,demtangBSBHYT, dem LDGiam, BSLDGiam trong dong chuan roi
			rowTangOrDCorBS = dongChuan+demTang1+demDC1+demTangBSBHYT;
			System.out.println("Dong chuan ban dau: "+dongChuan+"SoLD Giam: "+demLDGiam+",SoBSLDGiam: "+demBSLDGiam+", SoLDTang: "+demTang1+"\n"+
							", SoLDDC: "+demDC1+", SoTangBSBHYT: "+demTangBSBHYT+", DongChuanNew: "+rowTangOrDCorBS+", DemBSLDGiamNew: "+demTangOrDCorBS);
			
			rowCopy = rowTangOrDCorBS;
			dongChuan = rowCopy;
			rowStart = rowCopy + 1;
			
		} else if (noteBS.compareTo("Thoaùi Thu BSBHYT") == 0) {
			demTangOrDCorBS = demThoaiThuBSBHYT + 1;
			stt=demTangOrDCorBS;
			// rowTangOrDCorBS =
			// dongChuan+demTangOrDCorBS+demTang1+demDC1+demTangBSBHYT;// ok
			// ko can cong dem LD giam o dya nua vi minh da cong toan bo so
			// ldgiam vao dong chuan roi
			//rowTangOrDCorBS = dongChuan + demTangOrDCorBS + demTang1 + demDC1
			rowTangOrDCorBS = dongChuan + demTang1 + demDC1
					+ demTangBSBHYT;// ko bao gom LDGiam va BSLDGiam vi da cong ngoai dong chuan ban dau roi
					
			rowCopy = rowTangOrDCorBS;
			dongChuan = rowCopy;
			rowStart = rowCopy + 1;

		}

		/*
		 * // copy va insert them 1 row moi tren file mau tai vi tri dongNew
		 * sheet.shiftRows(rowTangOrDCorBS, sheet.getLastRowNum(), 1, true,
		 * false); row = sheet.createRow(rowTangOrDCorBS); row =
		 * sheet.getRow(rowTangOrDCorBS);
		 * 
		 * //row =sheet.createRow(r); // cai nay la copy de len dong hien tai
		 * dang co tren file mau for(int
		 * j=0;j<templateRow.getPhysicalNumberOfCells();j++){
		 * copyCell(templateRow.getCell(j),row.createCell(j)); }
		 */

		// moi lan dk hop le se them 1 row trong file excel,thay vi la
		// demTangOrDCorBS

		if (demTangOrDCorBS > 0) {
			sheet.shiftRows(rowStart, sheet.getLastRowNum(), 1, false, true);
		}
		// dong chuan tren file mau de lay cong thuc
		HSSFRow rowForm = sheet.getRow(11); 
		for (int i = rowStart; i < rowStart + 1; i++) {
			//HSSFUtils.copyRowM(wb, sheet, rowCopy++, i);
			HSSFUtils.copyRowM(wb, sheet, rowForm, i);
		}

		row = sheet.getRow(dongChuan);

		// gan data
		cell = row.getCell(0);
		//cell.setCellValue(demTangOrDCorBS);
		cell.setCellValue(stt);
		cell = row.getCell(1);
		cell.setCellValue(data.getHoTen());
		if (data.getMaSoBHXH() != null) {
			cell = row.getCell(2);
			cell.setCellValue(data.getMaSoBHXH());
		}
		cell = row.getCell(6); // muc luong old
		cell.setCellValue(sal1);
		cell = row.getCell(11); // muc luong moi
		cell.setCellValue(sal2);

		cell = row.getCell(16); // tuthang
		cell.setCellValue(insDAO.ToDate(tuThang));
		cell = row.getCell(17); // denthang
		cell.setCellValue(insDAO.ToDate(denThang));
		cell = row.getCell(20);
		cell.setCellValue(noteBS);
		cell = row.getCell(21);
		cell.setCellValue(data.getEmpsn());

		// if la Xet Tang BS BHYT, chi tang % cua BHYT ma thoi
		if (noteBS.compareTo("Taêng BSBHYT") == 0
				|| noteBS.compareTo("Thoaùi Thu BSBHYT") == 0) {
			cell = row.getCell(22);
			cell.setCellValue(0);
			cell = row.getCell(23);
			cell.setCellValue(tyleBHYT);
			cell = row.getCell(24);
			cell.setCellValue(0);
		} else {
			cell = row.getCell(22);
			cell.setCellValue(tyleBHXH);
			cell = row.getCell(23);
			cell.setCellValue(tyleBHYT);
			cell = row.getCell(24);
			cell.setCellValue(tyleBHTN);
		}

		cell = row.createCell(48);
		cell.setCellValue(data.getEmpsn());
		cell = row.createCell(49);
		cell.setCellValue(data.getFactGroup());
		if (data.getDonVi() != null) {
			cell = row.createCell(50);
			cell.setCellValue(data.getDonVi());
		}
		// gan cong thuc
		for (int k = 25; k < 48; k++) {			
			//System.out.println("cell thu : "+k+ row.getCell(k));
			
			cell = row.getCell(k);
			HSSFUtils.evalFormula(wb, cell);
		}

		demNew = demTangOrDCorBS;
		return demNew;
	}
		
	private String[] GetTTBaoGiam_TangMoiBangThangGiam(int sal_Giam,
			int sal_dc, int dkMuaBH, int dkMuaBHLan2, Date ngayChayBC, Date ngayQT20, Date ngayQT20Lan2,
			Date thoiGian) {
		// thoigian dd/mm/yyyy la thang muon so sanh
		int sal_BSBHYT = 0;
		int sal_BaoGiam = 0;
		String tangBSBHYT = null;
		String thoaiThuBSBHYT = null;
		String tang1Thang = "0";
		String dc1Thang = "0";
		String giam1Thang = "0";
		String tgianTangOrDCOrGiam = null;
		String noteGiam = null;
		String noteLuongGiam = null;
/*		// QT20 cua thang giam van chua goi di
		if (ngayChayBC.compareTo(ngayQT20) < 0) {
			sal_BSBHYT = sal_Giam - sal_dc;
			if (sal_dc > 0) {
				noteLuongGiam = "Thang tangmoi="
						+ thoiGian
						+ ", co DC dongloat tre, chua QT20 ThangGiam, luong= luong thang "
						+ thoiGian + " tru DC dong loat "
						+ String.valueOf(sal_dc);
			} else {
				noteLuongGiam = "Thang tangmoi=" + thoiGian
						+ ", ko co DC dongloat tre, chua QT20 ThangGiam "
						+ ", luong= luong thang " + thoiGian;
			}
		} else {
			sal_BSBHYT = sal_Giam;
			noteLuongGiam = "Thang tangmoi=" + thoiGian
					+ ", da QT20 ThangGiam , luong= luong thang " + thoiGian;
		}
*/
		// QT20 lan 1 cua thang giam van chua goi di
		if (ngayChayBC.compareTo(ngayQT20) < 0) {
			sal_BSBHYT = sal_Giam - sal_dc;
			if (sal_dc > 0) {
				noteLuongGiam = "Thang tangmoi="
						+ thoiGian
						+ ", co DC dongloat tre, chua QT20 lan 1 ThangGiam, luong= luong thang "
						+ thoiGian + " tru DC dong loat "
						+ String.valueOf(sal_dc);
			} else {
				noteLuongGiam = "Thang tangmoi=" + thoiGian
						+ ", ko co DC dongloat tre, chua QT20 lan 1 ThangGiam "
						+ ", luong= luong thang " + thoiGian;
			}
		}
		//if QT20 lan 1 da goi, chua goi QT20 lan 2
		else if ( (ngayChayBC.compareTo(ngayQT20) >= 0) && (ngayChayBC.compareTo(ngayQT20Lan2) < 0) ) {
			if (dkMuaBH==1 && dkMuaBHLan2==-1){
				noteLuongGiam = "Thang tangmoi=" + thoiGian
						+ ", ko co DC dongloat tre, chua QT20 lan 2 ThangGiam "
						+ ", luong= luong thang " + thoiGian;		
				giam1Thang ="1";
				tgianTangOrDCOrGiam = sdf.format(thoiGian);
			}
			else if(dkMuaBH==-1 && dkMuaBHLan2==1){
				if (sal_dc > 0){
					noteLuongGiam = "Thang tangmoi=" + thoiGian
							+ ", co DC dongloat tre, chua QT20 lan 2 ThangGiam "
							+ ", luong= luong thang " + thoiGian;		
					dc1Thang ="1";
					tgianTangOrDCOrGiam = sdf.format(thoiGian);
				}
				else{
					noteLuongGiam = "Thang tangmoi=" + thoiGian
							+ ", ko co DC dongloat tre, chua QT20 lan 2 ThangGiam "
							+ ", luong= luong thang " + thoiGian;		
					tang1Thang ="1";
					tgianTangOrDCOrGiam = sdf.format(thoiGian);					
				}
			}
		}
		// da goi QT20 lan 1 va 2
		else {
			sal_BSBHYT = sal_Giam;
			noteLuongGiam = "Thang tangmoi=" + thoiGian
					+ ", da QT20 ThangGiam , luong= luong thang " + thoiGian;
		}
		
		sal_BaoGiam = sal_BSBHYT;
		noteGiam = "Nghỉ việc";
		tangBSBHYT = "1";
		thoaiThuBSBHYT = "0";
		return new String[] { String.valueOf(sal_BSBHYT),
				String.valueOf(sal_BaoGiam), noteGiam, noteLuongGiam,
				tangBSBHYT, thoaiThuBSBHYT, tang1Thang, dc1Thang, giam1Thang, tgianTangOrDCOrGiam };
	}	
	
	private String[] GetThongTinBaoGiam( String soThe, String thangBaoGiam, String ngayTraTheBHYT) {
		// TODO Auto-generated method stub
		//thangBaoGiam, ngayTraTheBHYT : dd/mm/yyyy, thangbao giam luon la ngay 01 cua thang		
		Date thangCuoiQuyTruoc=null;
		Date ngayKyHD=null;
		Date thangBCGoc = null;
		Date thangGiamCong1 = null;
		Date thangGiam = null;
		Date thangGiamTru1 = null;
		Date thangGiamTru2 = null;		
		String thoiGianTemp;
		// bao cao so voi bao hiem cung 1 thang, 
		// if thay doi bao cao bao hiem = thoi gian cua bao hiem thi 11->12, 05->06
		String mmCuoiQuyTruoc1 = "12";
		String mmCuoiQuyTruoc2 = "06";
		
		int mm =0;
		int yyyy =0;
		// 0: huu tri/ CNV nghi viec, -1 : ko du cong, 1: du cong
		int dkbh_Giam 	= 0;
		int dkbh_GiamLan2 = 0;
		int dkbh_Giam1	= 0;
		int dkbh_Giam1Lan2	= 0; // dkbh_Giam1 lan 2
		int dkbh_Giam2	= 0;
		int dkbh_Giam2Lan2 = 0;
		int sal_Giam	= 0;
		int sal_Giam1	= 0;		
		int sal_Giam2	= 0;
		int sal_BaoGiam	= 0;
		
		// thong tin bao giam
		int sal_BSBHYT 			= 0;
		int sal_TangOrDCOrGiam		= 0;
		String noteGiam			= null;
		String noteLuongGiam 	= null;
		String tangBSBHYT		= "0";
		String thoaiThuBSBHYT	= "0";
		String tang1Thang		= "0";
		String dc1Thang			= "0";
		String giam1Thang	= "0";
		String tgianTang_DC_Giam	= null;
		String thangGanNhatCoBH	= null;
		String luongGanNhatCoBH	= "0";
		
		mm= Integer.parseInt(thangBaoGiam.substring(3, 5).toString());
		yyyy = Integer.parseInt(thangBaoGiam.substring(6, 10));
		if (mm>=1 && mm<=6) {
			thoiGianTemp = ("01/"+mmCuoiQuyTruoc1+"/"+(yyyy-1));
		}
		else{
			thoiGianTemp = ("01/"+mmCuoiQuyTruoc2+"/"+yyyy);
		}	
		
		thangCuoiQuyTruoc= insDAO.ToDate(thoiGianTemp);
		int lanKyHD = Integer.parseInt(insDAO.GetField("times", "n_labour", "empsn", "clock", "", soThe, "1", ""));
		thoiGianTemp = insDAO.GetField("to_char(date_s,'dd/mm/yyyy')", "n_labour", "empsn", "clock", "", soThe, "1", "").toString();
		ngayKyHD= insDAO.ToDate(thoiGianTemp);		
		//System.out.println(soThe+","+ sdf.format(thangCuoiQuyTruoc)+", "+sdf.format(ngayKyHD));
		
		// lanBC chi co y nghia voi thang giam, cac thang truoc do mac dinh la lan 2
		thangGiam	= insDAO.ToDate(thangBaoGiam);
		dkbh_Giam	= insDAO.getThamGiaBHXHTN2Lan(soThe, sdf.format(thangGiam), sdf.format(ngayBaoGiam.getTime()));
		sal_Giam	= insDAO.getLuongTheoThang(soThe, sdf.format(thangGiam));
		
		Calendar thangGiamTemp = Calendar.getInstance();
		thangGiamTemp.setTime(thangGiam);
		thangGiamTemp.add(thangGiamTemp.MONTH,1);
		thangGiamCong1 = thangGiamTemp.getTime();
		
		thangGiamTemp.setTime(thangGiam);
		thangGiamTemp.add(thangGiamTemp.MONTH, -1);		
		thangGiamTru1 = thangGiamTemp.getTime();
		dkbh_Giam1	= insDAO.getThamGiaBHXHTN2Lan(soThe, sdf.format(thangGiamTru1), sdf.format(ngayBaoGiam.getTime()));
		sal_Giam1	= insDAO.getLuongTheoThang(soThe, sdf.format(thangGiamTru1));		
		/* doi voi thang giam-1 se xet toi 2 lan ngay cong, lan 1-> trang thai bao giam, lan 2 -> xet co tang/ DC/ giam 1 thang cho thang giam -1 hay khong
		 VD: 09031086, dot bao giam 10/10/2013
		 Lan1 (1-16) 7 cong -> mua BHXH-TN T09
		 nghi viec 04/10/2013 --> bao giam 10/10/2013
		 Lan2 (1-30)   6 cong -> ko mua BHXH-TN T09
		 luc nay bao giam se co 2 dong
		 giam Ro T09-T09, luong =luong da mua luc T09
		 giam NViec T10, luong = luong gan nhat co BHXH-TN ( = T9)
		*/
		dkbh_Giam1Lan2	= insDAO.getThamGiaBHXHTN(soThe, sdf.format(thangGiamTru1));
		
		thangGiamTemp.add(thangGiamTemp.MONTH, -1);
		thangGiamTru2 = thangGiamTemp.getTime();
		dkbh_Giam2	= insDAO.getThamGiaBHXHTN2Lan(soThe, sdf.format(thangGiamTru2), sdf.format(ngayBaoGiam.getTime()));
		sal_Giam2	= insDAO.getLuongTheoThang(soThe, sdf.format(thangGiamTru2));		
		dkbh_Giam2Lan2 = insDAO.getThamGiaBHXHTN(soThe, sdf.format(thangGiamTru2));
				
		thangBCGoc 	= insDAO.ToDate(thangHienTai);
		//Date ngayChayBC		= insDAO.ToDate(sdf.format(dfNgayBaoCao.getDisplayedDate().getTime()));		
		Date ngayChayBC		= insDAO.ToDate(sdf.format(dfNgayBaoCao.getSelectedDate().getTime()));
		Date ngay20_BCGoc 	= insDAO.GetNgayGioiHanBC2Lan(thangHienTai, sdf.format(ngayBaoGiam.getTime()));
		
		Date[] ngay20 = insDAO.Get2NgayGioiHanBC2Lan(sdf.format(thangGiam));		
		Date ngay20_Giam	 = ngay20[0];		
		Date ngay20_GiamLan2 = ngay20[1];
		
		ngay20 = insDAO.Get2NgayGioiHanBC2Lan(sdf.format(thangGiamTru1));		
		Date ngay20_Giam1	 = ngay20[0];		
		Date ngay20_Giam1Lan2 = ngay20[1];	
		
		ngay20 = insDAO.Get2NgayGioiHanBC2Lan(sdf.format(thangGiamTru2));		
		Date ngay20_Giam2	 = ngay20[0];		
		Date ngay20_Giam2Lan2 = ngay20[1];	

		//Ngay ky HD tang moi trong thang bao giam
		Date ngay01_Giam	= insDAO.ToDate(sdf.format(thangGiam));
		Date ngay15_Giam	= insDAO.ToDate("15"+sdf.format(thangGiamTru1).toString().substring(2, 10));
		
		//Ngay ky HD tang moi thang bao giam -1
		Date ngay01_Giam1	= insDAO.ToDate(sdf.format(thangGiamTru1));
		Date ngay15_Giam1	= insDAO.ToDate("15"+sdf.format(thangGiamTru2).toString().substring(2, 10));
		
		int dc_TangMoi		= Integer.parseInt(insDAO.GetField("count(*)", "n_dcluong_dongloat", "to_char(ngaydc,'"+"dd/mm/yyyy"+"')", "baodc_tangmoi", "", sdf.format(thangGiam), "Y", ""));
		int sal_dcGiam		= 0;		
		if (dc_TangMoi==1){
			if (lanKyHD==1 && ngayKyHD.compareTo(ngay15_Giam)==0){
				sal_dcGiam=0;			
			}
			else{
				sal_dcGiam=Integer.parseInt(insDAO.GetField("sotienthaydoi", "n_dcluong_dongloat", "to_char(ngaydc,'"+"dd/mm/yyyy"+"')", "baodc_tangmoi", "", sdf.format(thangGiam), "Y", ""));
			}
		}
		else{
			sal_dcGiam=0;
		}
		
		int dc_TangMoi1		= Integer.parseInt(insDAO.GetField("count(*)", "n_dcluong_dongloat", "to_char(ngaydc,'"+"dd/mm/yyyy"+"')", "baodc_tangmoi", "", sdf.format(thangGiamTru1), "Y", ""));
		int sal_dcGiam1		= 0;		
		if (dc_TangMoi1==1){
			if (lanKyHD==1 && ngayKyHD.compareTo(ngay15_Giam1)==0){
				sal_dcGiam1=0;			
			}
			else{
				sal_dcGiam1=Integer.parseInt(insDAO.GetField("sotienthaydoi", "n_dcluong_dongloat", "to_char(ngaydc,'"+"dd/mm/yyyy"+"')", "baodc_tangmoi", "", sdf.format(thangGiamTru1), "Y", ""));
			}
		}
		else{
			sal_dcGiam1=0;
		}	
		
		// thong tin nghi viec di lam lai
		String[] data = insDAO.getThongTinTangLai(soThe, sdf.format(thangGiamTru1).substring(3, 10));
		Date ngayDiLamLai = null;
		Date thangTangLai = null;
		int thoiDiemTangLai = 0;
		
		if (data.length>0){
			//System.out.println(data[0] +" "+data[1]+" "+data[2]);
			
			if (data[0]!=null){
				ngayDiLamLai = insDAO.ToDate(data[0]);
			}
			if (data[1]!=null){
				//neu tang lai 20/04 thi thang tang lai cung la T04 -- chuyen ve ngay 01 cho de so sanh
				if (data[1].substring(0, 2).equals("20")){
					thangTangLai = insDAO.ToDate("01/"+ data[1].substring(3, 10));
				}
				else{
					thangTangLai = insDAO.ToDate(data[1]);
				}
			}
			if (data[2]!=null){
				thoiDiemTangLai = Integer.parseInt(data[2]);
				//System.out.println(sdf.format(ngayDiLamLai) +" "+sdf.format(thangTangLai)+" "+thoiDiemTangLai);
			}			
		}
		Date nsVao 	= null;
		Date nsRa	= null;
		
		String ngayCuoiGiam = insDAO.getNgayCuoi(thangBaoGiam);
		
		// NSRa lay theo thang
		String temp = null;
		temp = insDAO.GetField("to_char(get_thang_ns_ra1('"+soThe+"',to_Date('"+ngayCuoiGiam+"','dd/mm/yyyy')),'"+"dd/mm/yyyy"+"')", "dual", "", "", "", "", "", "");
		if (temp==null){
			nsRa = null;			
		}
		else{
			nsRa = insDAO.ToDate(temp);
		}
		
		// NSVao dua theo ngay thuc te --> thang NS vao
		// ham lay ve dung thang se NSVao, ko lay theo ngay thuc te nua
		temp = insDAO.GetField("to_char(get_thang_ns_vao_empsn1('"+soThe+"',to_Date('"+ngayCuoiGiam+"','dd/mm/yyyy')),'"+"dd/mm/yyyy"+"')", "dual", "", "", "", "", "", "");
		if (temp==null){
			nsVao = null;
		}
		else{
			nsVao = insDAO.ToDate(temp);
		}
		//Bao Ro tu thang tro lai ke tu 09/2013. vi vay se co truong hop bao giam tu Ro->NViec doi voi truong hop Rora va NS->RoRa
		// bat dau xet toi bao giam tuan, dang xet den day 01/06/2013
		String[] data1;
		if (ngayDiLamLai==null || (ngayDiLamLai!=null && thangTangLai.compareTo(thangGiamTru1)<0)){
			if (nsVao==null){
				//tang moi trong thang giam
				if(lanKyHD==1 && (ngayKyHD.compareTo(ngay01_Giam)==0 || ngayKyHD.compareTo(ngay15_Giam)==0)){					
					data1= GetTTBaoGiam_TangMoiBangThangGiam(sal_Giam, sal_dcGiam, dkbh_Giam,dkbh_GiamLan2, ngayChayBC, ngay20_Giam,ngay20_GiamLan2, thangGiam);
					sal_BSBHYT 	= Integer.parseInt(data1[0]);
					sal_BaoGiam	= Integer.parseInt(data1[1]);
					noteGiam	= data1[2];
					noteLuongGiam= data1[3];
					tangBSBHYT	= data1[4];
					thoaiThuBSBHYT= data1[5];
					tang1Thang	= data1[6];//=0
					dc1Thang	= data1[7];//=0
					giam1Thang = data1[8];
					tgianTang_DC_Giam= data1[9];//null
					//vi ko co thong tin tang or dc 1 thang gi ca nen sal_tangOrdc
					sal_TangOrDCOrGiam= 0;
				}
				
				//tang moi trong thang giam -1
				else if(lanKyHD==1 && (ngayKyHD.compareTo(ngay01_Giam1)==0 || ngayKyHD.compareTo(ngay15_Giam1)==0)){
					//QT20 lan 1 cua thang giam-1 van chua goi di
					if (ngayChayBC.compareTo(ngay20_Giam1)<0){
						sal_BSBHYT= sal_Giam1-sal_dcGiam1;
						if (sal_dcGiam1>0) {
							noteLuongGiam="Thang tangmoi="+sdf.format(thangGiamTru1)+", co DC dongloat tre, chua QT20 "+sdf.format(thangGiamTru1)+
										", luong= luong thang "+sdf.format(thangGiamTru1)+" tru DC dong loat: "+String.valueOf(sal_dcGiam1);
						}
						else{
							noteLuongGiam="Thang tangmoi="+sdf.format(thangGiamTru1)+", ko co DC dongloat tre, chua QT20 "+sdf.format(thangGiamTru1)+
									", luong= luong thang "+sdf.format(thangGiamTru1);
						}
						
						if (dkbh_Giam1==1 && sal_Giam1!=sal_BSBHYT ){
							tang1Thang 		= "0";
							dc1Thang		= "1";
							giam1Thang	="0";
							tgianTang_DC_Giam	= sdf.format(thangGiamTru1);
							sal_TangOrDCOrGiam	= sal_Giam1;
						}
						tangBSBHYT	= "1";
						thoaiThuBSBHYT ="0";						
						// ko co truong hop thang giam -1 ko du cong, vi vay ko can else
					}
					//QT20 lan 1 cua thang giam-1 da goi, lan 2 chua goi
					else if (ngayChayBC.compareTo(ngay20_Giam1)>=0 && ngayChayBC.compareTo(ngay20_Giam1Lan2)<0){
						sal_BSBHYT= sal_Giam1-sal_dcGiam1;
						if (sal_dcGiam1>0) {
							noteLuongGiam="Thang tangmoi="+sdf.format(thangGiamTru1)+", co DC dongloat tre, chua QT20 "+sdf.format(thangGiamTru1)+
										", luong= luong thang "+sdf.format(thangGiamTru1)+" tru DC dong loat: "+String.valueOf(sal_dcGiam1);
						}
						else{
							noteLuongGiam="Thang tangmoi="+sdf.format(thangGiamTru1)+", ko co DC dongloat tre, chua QT20 "+sdf.format(thangGiamTru1)+
									", luong= luong thang "+sdf.format(thangGiamTru1);
						}
						
						// lan 1 ko du cong
						if (dkbh_Giam1==-1){
							noteGiam="Ro->Nviệc";
							sal_BaoGiam=0;
							noteLuongGiam="Ro->Nviec, luong =0";
							tangBSBHYT="0";
							thoaiThuBSBHYT="1";
							// vi la RoVao nen bao tang 1 thang, ko bao DC gi ca
							if(dkbh_Giam1Lan2==1){								
								tang1Thang 		= "1";
								dc1Thang		= "0";
								giam1Thang	="0";
								tgianTang_DC_Giam	= sdf.format(thangGiamTru1);
								sal_TangOrDCOrGiam	= sal_Giam1;
							}
						}
						// lan 1 du cong
						else{
							noteGiam="Nviệc";
							sal_BaoGiam=sal_BSBHYT;
							tangBSBHYT	= "1";
							thoaiThuBSBHYT ="0";							
							// vi lan 1 du cong, lan 2 ko du cong nen bao giam 1 thang
							if(dkbh_Giam1Lan2==-1){								
								tang1Thang 		= "0";
								dc1Thang		= "0";
								giam1Thang	="1";
								tgianTang_DC_Giam	= sdf.format(thangGiamTru1);
								sal_TangOrDCOrGiam	= sal_Giam1;
							}							
						}
					}		
					// da QT20 thang giam -1, chua QT20 lan 1 thang giam
					else if(ngayChayBC.compareTo(ngay20_Giam1Lan2)>=0 && ngayChayBC.compareTo(ngay20_Giam)<0){
						sal_BSBHYT = sal_Giam1;// vi tang trong thang giam -1
						//if thang giam -1 ko du cong
						if (dkbh_Giam1Lan2==-1){
							sal_BaoGiam	= 0;
							noteGiam	= "Ro->Nviệc";
							noteLuongGiam = "Da QT20 ThangGiam-1: TangMoi->RoRa, chua QT20 ThangGiam, luong= luong 0 ";
							tangBSBHYT	= "0";
							thoaiThuBSBHYT ="1";							
						}
						// thang giam -1 du cong
						else
						{
							sal_BaoGiam	= sal_BSBHYT;
							noteGiam	= "Nviệc";
							noteLuongGiam = "Da QT20 ThangGiam-1: TangMoi, chua QT20 ThangGiam, luong= luong thang "+sdf.format(thangGiamTru1);
							tangBSBHYT	= "1";
							thoaiThuBSBHYT ="0";							
						}	
					}
					// da QT20 thang giam -1, da QT20 lan 1 thang giam, chua QT20 lan 2 thang giam
					else if(ngayChayBC.compareTo(ngay20_Giam)>=0 && ngayChayBC.compareTo(ngay20_GiamLan2)<0){
				  		data1 = insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiam),sdf.format(ngayBaoGiam.getTime()));
				  		thangGanNhatCoBH	= data1[0];
				  		luongGanNhatCoBH	= data1[1];
				  		noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiam)+
				  				". Chua QT20 lan 2 "+sdf.format(thangGiam)+", luong = luong cua thang "+thangGanNhatCoBH;
				  		sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
						//if thang giam lan 1 %RoRa, lan 2 chac chan ko du cong
						if (dkbh_Giam==-1){
							sal_BaoGiam	= 0;
							noteGiam	= "Ro->Nviệc";
							noteLuongGiam = "Da QT20 lan 1 ThangGiam: Ro->NViec, luong= luong 0 ";
							tangBSBHYT	= "0";
							thoaiThuBSBHYT ="1";							
						}
						else
						{
							sal_BaoGiam	= sal_BSBHYT;
							noteGiam	= "Nviệc";
							noteLuongGiam = "Da QT20 lan 1 ThangGiam: du cong, luong= luong thang "+sdf.format(thangGiam);
							tangBSBHYT	= "1";
							thoaiThuBSBHYT ="0";							
						}	
					}
					//  da QT20 thang giam-1 va thang giam
					else{
						// vi co kha nang lan 1 cua thang giam du cong nen co the luong gan nhat co BH la thang giam
				  		data1 = insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiam),sdf.format(ngayBaoGiam.getTime()));
				  		thangGanNhatCoBH	= data1[0];
				  		luongGanNhatCoBH	= data1[1];
				  		noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiam)+
				  				". Da QT20 lan 2 "+sdf.format(thangGiam)+", luong = luong cua thang "+thangGanNhatCoBH;
				  		sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
				  		
						if (dkbh_GiamLan2==-1){							
							sal_BaoGiam	= 0;
							noteGiam	= "Ro->Nviệc";
							noteLuongGiam = "Da QT20 ThangGiam, ThangGiam: RoRa, luong= luong 0 ";
							tangBSBHYT	= "0";
							thoaiThuBSBHYT ="1";							
						}
						else
						{
							sal_BaoGiam	= sal_BSBHYT;
							noteGiam	= "Nviệc";
							noteLuongGiam = "Da QT20 ThangGiam-1, ThangGiam: du cong, luong= luong thang "+sdf.format(thangGiam);
							tangBSBHYT	= "1";
							thoaiThuBSBHYT ="0";							
						}
					}			
				}
				// tang moi < thang giam -1
				else{
					//chua QT20 lan 1 thang giam -1, 
				  	if (ngayChayBC.compareTo(ngay20_Giam1)<0)
				  	{
				  		data1 = insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru2),sdf.format(ngayBaoGiam.getTime()));
				  		thangGanNhatCoBH	= data1[0];
				  		luongGanNhatCoBH	= data1[1];
				  		noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiamTru2)+
				  				". Chua QT20 lan 1 "+sdf.format(thangGiamTru1)+", luong = luong cua thang "+thangGanNhatCoBH;
				  		sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
				  		// if Giam-2 la %Ro, Giam-1 du cong thi Tang or DC giam-1
				  		if(dkbh_Giam2Lan2==-1)
				  		{
							sal_BaoGiam	= 0;
							noteGiam	= "Ro->Nviệc";		
							tangBSBHYT	= "0";
							thoaiThuBSBHYT ="1";		
							
					  		if(dkbh_Giam1==1){
					  			if(sal_Giam1==sal_BSBHYT)
					  			{
						  			dc1Thang ="0";
						  			tang1Thang="1";
						  			giam1Thang="0";
					  			}
					  			else
					  			{
						  			dc1Thang ="1";
						  			tang1Thang="0";
						  			giam1Thang="0";
					  			}
					  			tgianTang_DC_Giam = sdf.format(thangGiamTru1);
					  			sal_TangOrDCOrGiam = sal_Giam1;					  			
					  		}
				  		}
				  		else// giam-2 du cong
				  		{
							sal_BaoGiam	= sal_BSBHYT;
							noteGiam	= "Nviệc";	
							tangBSBHYT	= "1";
							thoaiThuBSBHYT ="0";	
				  			//if Giam-2 du cong, Giam -1 cun du cong va khac luong gan nhat thi bao DC Giam-1
				  			if(dkbh_Giam1Lan2==1 && sal_Giam1!=sal_BSBHYT)
				  			{
					  			dc1Thang ="1";
					  			tang1Thang="0";	
					  			giam1Thang="0";
					  			tgianTang_DC_Giam = sdf.format(thangGiamTru1);
					  			sal_TangOrDCOrGiam = sal_Giam1;	
				  			}						
				  		}
				  	}
					//thang giam -1: da QT20 lan 1, chua QT20 lan 2 
				  	else if (ngayChayBC.compareTo(ngay20_Giam1)>=0 && (ngayChayBC.compareTo(ngay20_Giam1Lan2)<0))
				  	{
				  		data1 = insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru1),sdf.format(ngayBaoGiam.getTime()));
				  		thangGanNhatCoBH	= data1[0];
				  		luongGanNhatCoBH	= data1[1];
				  		noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiamTru1)+
				  				". Chua QT20 lan 2 "+sdf.format(thangGiamTru1)+", luong = luong cua thang "+thangGanNhatCoBH;
				  		sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
				  		//Giam-1 lan 1 ko du cong, lan 2 du cong thi Tang or DC giam-1
				  		if(dkbh_Giam1==-1)
				  		{
							sal_BaoGiam	= 0;
							noteGiam	= "Ro->Nviệc";		
							tangBSBHYT	= "0";
							thoaiThuBSBHYT ="1";
							
					  		if(dkbh_Giam1Lan2==1){
					  			if(sal_Giam1==sal_BSBHYT)
					  			{
						  			dc1Thang ="0";
						  			tang1Thang="1";
						  			giam1Thang="0";
					  			}
					  			else
					  			{
						  			dc1Thang ="1";
						  			tang1Thang="0";
						  			giam1Thang="0";
					  			}
					  			tgianTang_DC_Giam = sdf.format(thangGiamTru1);
					  			sal_TangOrDCOrGiam = sal_Giam1;					  			
					  		}
				  		}
				  		else// giam-1 lan 1 du cong
				  		{
							sal_BaoGiam	= sal_BSBHYT;
							noteGiam	= "Nviệc";	
							tangBSBHYT	= "1";
							thoaiThuBSBHYT ="0";
							
				  			//if Giam-1 lan 1 du cong, Giam -1 lan 2 cung du cong va khac luong gan nhat thi bao DC Giam-1
				  			if(dkbh_Giam1==1)
				  				//&& dkbh_Giam1Lan2==1 && sal_Giam1!=sal_BSBHYT)
				  			{
				  				// lan 2 du cong thi xet dc neu co
				  				if(dkbh_Giam1Lan2==1){
				  					if(sal_BSBHYT!=sal_Giam1){
							  			dc1Thang ="1";
							  			tang1Thang="0";	
							  			giam1Thang="0";	
							  			tgianTang_DC_Giam = sdf.format(thangGiamTru1);
							  			sal_TangOrDCOrGiam = sal_Giam1;							  			
				  					}					  					
				  				}
				  				//lan 2 ko du cong thi giam Ro 1 thang giam -1
				  				else{
						  			dc1Thang ="0";
						  			tang1Thang="0";	
						  			giam1Thang="1";
						  			tgianTang_DC_Giam = sdf.format(thangGiamTru1);
						  			sal_TangOrDCOrGiam = sal_Giam1;				  					
				  				}
				  			}							
				  		}
				  	}
				  	//Da QT20 thang giam -1 , chua QT20 lan 1 thang giam
				  	else if(ngayChayBC.compareTo(ngay20_Giam1Lan2)>=0 && ngayChayBC.compareTo(ngay20_Giam)<0) {
				  		data1 = insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru1),sdf.format(ngayBaoGiam.getTime()));
				  		thangGanNhatCoBH	= data1[0];
				  		luongGanNhatCoBH	= data1[1];
				  		noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiamTru1)+
				  				". Chua QT20 lan 1 "+sdf.format(thangGiam)+", luong = luong cua thang "+thangGanNhatCoBH;
				  		sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);	
				  		if(dkbh_Giam1Lan2==-1)
				  		{
				  			sal_BaoGiam	= 0;
							noteGiam	= "Ro->Nviệc";	
							tangBSBHYT	= "0";
							thoaiThuBSBHYT ="1";							
				  		}
				  		else
				  		{
				  			sal_BaoGiam	= sal_BSBHYT;
							noteGiam	= "Nviệc";
							tangBSBHYT	= "1";
							thoaiThuBSBHYT ="0";
				  		}						
				  	}
				  	//thang giam : da QT20 lan 1, chua Qt20 lan 2
				  	else if(ngayChayBC.compareTo(ngay20_Giam)>=0 && ngayChayBC.compareTo(ngay20_GiamLan2)<0) {
				  		data1 = insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiam),sdf.format(ngayBaoGiam.getTime()));
				  		thangGanNhatCoBH	= data1[0];
				  		luongGanNhatCoBH	= data1[1];
				  		noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiam)+
				  				". Chua QT20 lan 2 "+sdf.format(thangGiam)+", luong = luong cua thang "+thangGanNhatCoBH;
				  		sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);	
				  		if(dkbh_Giam==-1)
				  		{
				  			sal_BaoGiam	= 0;
							noteGiam	= "Ro->Nviệc";	
							tangBSBHYT	= "0";
							thoaiThuBSBHYT ="1";							
				  		}
				  		else
				  		{
				  			sal_BaoGiam	= sal_BSBHYT;
							noteGiam	= "Nviệc";
							tangBSBHYT	= "1";
							thoaiThuBSBHYT ="0";							
				  		}						
				  	}
				  	//Da Qt20 lan 2 thang giam
				  	else {
				  		data1 = insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiam),sdf.format(ngayBaoGiam.getTime()));
				  		thangGanNhatCoBH	= data1[0];
				  		luongGanNhatCoBH	= data1[1];
				  		noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiam)+
				  				". Da QT20 "+sdf.format(thangGiam)+", luong = luong cua thang "+thangGanNhatCoBH;
				  		sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);	
				  		if(dkbh_GiamLan2==-1)
				  		{
				  			sal_BaoGiam	= 0;
							noteGiam	= "Ro->Nviệc";	
							tangBSBHYT	= "0";
							thoaiThuBSBHYT ="1";
				  		}
				  		else
				  		{
				  			sal_BaoGiam	= sal_BSBHYT;
							noteGiam	= "Nviệc";	
							tangBSBHYT	= "1";
							thoaiThuBSBHYT ="0";
				  		}
				  	}
				}
			} // end ko co thong tin nghi san
			
			// co thong tin nghi san
			else{
				//if ThangNSVao = thang bao giam
				if(nsVao.compareTo(thangGiam)==0){
					// chua QT20 lan 1 thang giam
					if (ngayChayBC.compareTo(ngay20_Giam)<0){
						noteGiam="Nsản-> Nviệc";
						data1=insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru1),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH	= data1[0];
						luongGanNhatCoBH	= data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
						sal_BaoGiam			= 0;
						noteLuongGiam		= "Nsan->NViec nen luong giam =0";
						tangBSBHYT			="1";
						thoaiThuBSBHYT		="0";
						tang1Thang			="0";
						dc1Thang			="0";
						giam1Thang	="0";
						tgianTang_DC_Giam		= null;
						sal_TangOrDCOrGiam		=0;
					}
					// Thang giam : Da Qt20 lan 1, chua QT20 lan 2
					else if (ngayChayBC.compareTo(ngay20_Giam)>=0 && ngayChayBC.compareTo(ngay20_GiamLan2)<0){
						data1=insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiam),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH	= data1[0];
						luongGanNhatCoBH	= data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);						
						// Giam : lan 1 ko du cong : NS->Ro
						if(dkbh_Giam==-1){
							noteGiam="Ro-> Nviệc";
							sal_BaoGiam			= 0;
							noteLuongGiam		= "Ro->NViec nen luong giam =0";							
							// Thang giam : lan 1 ko du cong, lan 2 du cong thi tang / DC 1 thang
							// truong hop nay se ko xay ra vi neu du cong da bao giam thang ke tiep
							// vi vay chi co lan 1 ko du cong, lan 2 ko du cong ma thoi
							tangBSBHYT			="0";
							thoaiThuBSBHYT		="1";
						}
						//Thang giam : lan 1 du cong NSVao, lan 2 ko du cong 
						else{
							noteGiam="Nviệc";
							sal_BaoGiam			= sal_BSBHYT;
							noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiam)+
					  				". Da QT20 lan 1"+sdf.format(thangGiam)+", luong = luong cua thang "+thangGanNhatCoBH;							
							// Thang giam : chi co truong hop lan 1 du cong, lan 2 ko du cong 							
							tangBSBHYT			="1";
							thoaiThuBSBHYT		="0";							
						}
					}
					//Thang giam : Da QT thang giam lan1 va lan 2
					else{
				        //Da QT roi ma lai giam = thang NS vao nen chac chan thang NSVao co trang thai la NS->RoRa/RoRa (vi lan 1 NSVao, lan 2 RoRa)
				        //Neu thang giam = Thang cuoi quy va co bao gia han the cho quy moi roi thi phai thoai thu tro lai
						// vi vay ko can kiem tra roVaoTuNSRoRa nua						
						noteGiam	= "Ro->Nviệc";
						data1		= insDAO.TTThamGiaBHGanNhat(soThe, thangBaoGiam,sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH	= data1[0];
						luongGanNhatCoBH	= data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
						sal_BaoGiam			= 0;
						noteLuongGiam		= "NSan->RoRa->NViec nen luong bao giam =0";
						tangBSBHYT	= "0";
						thoaiThuBSBHYT ="1";		
					}
				}
				
				//If NSVao= thang giam -1 
				else if(nsVao.compareTo(thangGiamTru1)==0){
					//nsVao= thang giam -1 thi chua chac thang nsVao du cong, co kha nang la NSVao/ NS-->RoRa
					// if chua QT thang giam -1 thi cho du thnag giam -1 la thang cuoi quy-1 cung ko sao, vi luc nay chua chay gia han the
					// vi vay ko can quan tam nsRoRa_CuoiQuy va roVaoTuNSRoRa					
					//if chua QT lan 1 thang giam -1
					if (ngayChayBC.compareTo(ngay20_Giam1)<0){						
						noteGiam	= "Nsản->Nviệc";
						data1		= insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru2),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH = data1[0];
						luongGanNhatCoBH = data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
						
						sal_BaoGiam		 = 0;
						noteLuongGiam		= "Nsan->NViec nen luong giam =0";						
						tangBSBHYT		= "1";
						thoaiThuBSBHYT	= "0";
						// chua Qt lan 1 nhung da bao giam thi xet ngay cong all thang luon
						if(dkbh_Giam1Lan2==1){
							tang1Thang 	= "1";
							dc1Thang	= "0";
							giam1Thang="0";
							tgianTang_DC_Giam= sdf.format(thangGiamTru1); 
							sal_TangOrDCOrGiam=sal_Giam1;
						}						
					}
					// Thang giam -1: da Qt lan 1, chua Qt lan 2
					else if (ngayChayBC.compareTo(ngay20_Giam1)>=0 && ngayChayBC.compareTo(ngay20_Giam1Lan2)<0){
						data1		= insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru1),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH = data1[0];
						luongGanNhatCoBH = data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
						
						// giam -1 : lan 1 ko du cong NS->Ro
						if(dkbh_Giam1==-1){
							noteGiam	= "Ro->Nviệc";							
							sal_BaoGiam		 = 0;
							noteLuongGiam		= "Nsan->Ro->NViec nen luong giam =0";	
							tangBSBHYT		= "0";
							thoaiThuBSBHYT	= "1";							
							// lan 1 ko du cong, lan 2 du cong : tang/ dc 1 thang
							// ko can xet lan 2 ko du cong vi ko co gi thay doi
							if (dkbh_Giam1Lan2==1){
								if (sal_BSBHYT!=sal_Giam1){
									tang1Thang="0";
									dc1Thang="1";
									giam1Thang="0";									
								}
								else{
									tang1Thang="1";
									dc1Thang="0";
									giam1Thang="0";								
								}
								tgianTang_DC_Giam= sdf.format(thangGiamTru1); 
								sal_TangOrDCOrGiam=sal_Giam1;	
							}
						}
						//giam -1: lan 1 du cong NSVao
						else{
							noteGiam	= "Nviệc";
							sal_BaoGiam=sal_BSBHYT;
							noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiamTru1)+
					  				". Da QT20 lan 1"+sdf.format(thangGiamTru1)+", luong = luong cua thang "+thangGanNhatCoBH;
							tangBSBHYT		= "1";
							thoaiThuBSBHYT	= "0";
							// lan 1 du cong, lan 2 ko du cong : giam 1 thang
							// ko can xet lan 2 du cong vi ko co gi thay doi
							if (dkbh_Giam1Lan2==-1){
								tang1Thang="0";
								dc1Thang="0";
								giam1Thang="1";
								tgianTang_DC_Giam= sdf.format(thangGiamTru1); 
								sal_TangOrDCOrGiam=sal_Giam1;	
							}
						}
					}
					// da QT thang giam -1 nhung chua QT lan 1 thang giam
					// NSVao =thang giam -1, va da bao len BH la NSVao/ NS-->RoRa/RoRa
					// vi vay phai ktra nsRoRa_CuoiQuy va roVaoTuNSRoRa
					else if (ngayChayBC.compareTo(ngay20_Giam1Lan2)>=0 && ngayChayBC.compareTo(ngay20_Giam)<0){
						data1		= insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru1),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH 	= data1[0];
						luongGanNhatCoBH	= data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);

						// NSVao= thangGiam-1 va du cong
						// vi vay ko quan tam nsRoRa_CuoiQuy va roVaoTuNSRoRa						
						if (dkbh_Giam1Lan2==1){
							noteGiam 			= "Nviệc";
							sal_BaoGiam			= sal_BSBHYT;
							tangBSBHYT			= "1";
							thoaiThuBSBHYT		= "0";							
						}
						// NVao= thang giam -1 nhung NS-->Rora/RoRa, bi gio lai nghi viec, chac chan la chua RoVao
						// vi vay ko quan tam roVaoTuNSRoRa 
						else{							
							noteGiam			= "Ro->Nviệc";
							sal_BaoGiam			= 0;
							noteLuongGiam		= "NS->RoRa->NViec nen luong bao giam = 0";							
							// if la NS-RoRa thang cuoi quy -1 thi da tang BS BHYT cho quy moi luc gia han the
							// or la giam Ro tu nhung thang truoc, 09/2013, HA
							tangBSBHYT	= "0";
							thoaiThuBSBHYT ="1";													
						}
					}
					// Thang giam: Da QT20 lan 1, chua QT lan 2
					else if (ngayChayBC.compareTo(ngay20_Giam)>=0 && ngayChayBC.compareTo(ngay20_GiamLan2)<0){
						data1		= insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiam),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH 	= data1[0];
						luongGanNhatCoBH	= data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
						// NS vao thang giam -1, da QT lan 1 thang giam nen chi quan tam ngay cong thang giam lan 1
						if (dkbh_Giam==-1){
							noteGiam 			= "Ro->Nviệc";
							sal_BaoGiam			= 0;
							noteLuongGiam		= "RoRa->NViec nen luong bao giam = 0";		
							tangBSBHYT	= "0";
							thoaiThuBSBHYT ="1";								
						}
						else{
							noteGiam 			= "Nviệc";
							sal_BaoGiam			= sal_BSBHYT;
							noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiam)+
					  				". Da QT20 lan 1"+sdf.format(thangGiam)+", luong = luong cua thang "+thangGanNhatCoBH;
							tangBSBHYT	= "1";
							thoaiThuBSBHYT ="0";
						}
					}
					// da QT 20 thang giam roi
					else{
						data1				= insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiam),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH 	= data1[0];
						luongGanNhatCoBH	= data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
						// 09/2013 vi bao Ro tu thang nen neu thang giam Ro thi bi gio se thoai thu, ko quan tam NSRoRa cuoi quy nua
						// dieu nay chac chan se ko co vi da bao giam
						if (dkbh_GiamLan2==1){
							noteGiam 			= "Nviệc";
							sal_BaoGiam			= sal_BSBHYT;
							tangBSBHYT			= "1";
							thoaiThuBSBHYT		= "0";							
						}	
						else{
							noteGiam 			= "Ro->Nviệc";
							noteLuongGiam		= "RoRa->NViec nen luong bao giam = 0";	
							sal_BaoGiam			= 0;
							tangBSBHYT			= "0";
							thoaiThuBSBHYT		= "1";								
						}
					}// het QT20 thang giam
				}// end cua NSVao= thang giam -1
				
				//if NSVao < thang giam -1
				else if (nsVao.compareTo(thangGiamTru1)<0){
					// if chua QT20 thang giam -1 lan 1
					if (ngayChayBC.compareTo(ngay20_Giam1)<0){
						data1		= insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru2),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH 	= data1[0];
						luongGanNhatCoBH	= data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
						
						//>=09/2013 bao Ro tu thang
						
						if (dkbh_Giam2Lan2==1){
							noteGiam			= "Nviệc";
							sal_BaoGiam			= sal_BSBHYT;
							noteLuongGiam		= "Nviec , luong giam = luong gan nhat co BH, " + thangGanNhatCoBH;
							tangBSBHYT			= "1";
							thoaiThuBSBHYT		= "0";
							//chua QT lan 1 nhung bi gio bao giam roi thi xet cong tron 1 thang
							if(dkbh_Giam1Lan2==1){
								if(sal_BSBHYT!=sal_Giam1){
									tang1Thang	= "0";
									dc1Thang	= "1";
									giam1Thang="0";
								}
								else{
									tang1Thang	= "1";
									dc1Thang	= "0";
									giam1Thang="0";									
								}
								tgianTang_DC_Giam= sdf.format(thangGiamTru1);
								sal_TangOrDCOrGiam=sal_Giam1;
							}															
						}
						else{
							noteGiam			= "Ro->Nviệc";								
							sal_BaoGiam			= 0;
							noteLuongGiam		= "RoRa->NViec nen luong bao giam = 0";
							tangBSBHYT			= "0";
							thoaiThuBSBHYT		= "1";
							if(dkbh_Giam1Lan2==1){
								tang1Thang	= "1";
								dc1Thang	= "0";
								giam1Thang="0";
								tgianTang_DC_Giam= sdf.format(thangGiamTru1);
								sal_TangOrDCOrGiam=sal_Giam1;
							}								
						}
					}
					// thang giam -1 : da QT lan 1, chua QT lan 2
					else	if (ngayChayBC.compareTo(ngay20_Giam1)>=0 && ngayChayBC.compareTo(ngay20_Giam1Lan2)<0){
						data1		= insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru1),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH 	= data1[0];
						luongGanNhatCoBH	= data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
						
						//>=09/2013 bao Ro tu thang
						//Lan 1 du cong
						if (dkbh_Giam1==1){
							noteGiam			= "Nviệc";
							sal_BaoGiam			= sal_BSBHYT;
							noteLuongGiam		= "Nviec , luong giam = luong gan nhat co BH, " + thangGanNhatCoBH;
							tangBSBHYT			= "1";
							thoaiThuBSBHYT		= "0";
							if(dkbh_Giam1Lan2==-1){
								tang1Thang	= "0";
								dc1Thang	= "0";
								giam1Thang="1";
								tgianTang_DC_Giam= sdf.format(thangGiamTru1);
								sal_TangOrDCOrGiam=sal_Giam1;								
							}
						}
						else{
							noteGiam			= "Ro->Nviệc";								
							sal_BaoGiam			= 0;
							noteLuongGiam		= "RoRa->NViec nen luong bao giam = 0";
							tangBSBHYT			= "0";
							thoaiThuBSBHYT		= "1";
							if(dkbh_Giam1Lan2==1){
								tang1Thang	= "1";
								dc1Thang	= "0";
								giam1Thang="0";
								tgianTang_DC_Giam= sdf.format(thangGiamTru1);
								sal_TangOrDCOrGiam=sal_Giam1;
							}								
						}
					}
					//da QT 20 thang giam -1, chua QT20 thang giam lan 1
					else if(ngayChayBC.compareTo(ngay20_Giam1Lan2)>0 && ngayChayBC.compareTo(ngay20_Giam)<0){						
				        // vi chua QT thang giam lan 1 nen se xet RoVao ke tu thang giam-1
						data1			= insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru1),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH 	= data1[0];
						luongGanNhatCoBH	= data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
						tang1Thang		="0";
						dc1Thang		="0";
						tgianTang_DC_Giam	=null;
						sal_TangOrDCOrGiam	=0;
						//>=09/2013 bao Ro tu thang
						if (dkbh_Giam1Lan2==1){
							noteGiam			= "Nviệc";
							sal_BaoGiam			= sal_BSBHYT;
							noteLuongGiam		= "Nviec , luong giam = luong gan nhat co BH, " + thangGanNhatCoBH;
							tangBSBHYT			= "1";
							thoaiThuBSBHYT		= "0";
						}
						else{
							noteGiam			= "Ro->Nviệc";								
							sal_BaoGiam			= 0;
							noteLuongGiam		= "RoRa->NViec nen luong bao giam = 0";
							tangBSBHYT			= "0";
							thoaiThuBSBHYT		= "1";
						}									
						tang1Thang	= "0";
						dc1Thang	= "0";
						giam1Thang="0";						
					}// end cua QT 20 thang giam -1, chua QT20 thang giam
					//thang giam : lan 1 da QT, lan 2 chua
					else if(ngayChayBC.compareTo(ngay20_Giam)>=0 && ngayChayBC.compareTo(ngay20_GiamLan2)<0){						
				        // vi da QT thang giam lan 1 nen se xet RoVao ke tu thang giam
						data1			= insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiam),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH 	= data1[0];
						luongGanNhatCoBH	= data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
						tang1Thang		="0";
						dc1Thang		="0";
						tgianTang_DC_Giam	=null;
						sal_TangOrDCOrGiam	=0;
						//>=09/2013 bao Ro tu thang
						if (dkbh_Giam==1){
							noteGiam			= "Nviệc";
							sal_BaoGiam			= sal_BSBHYT;
							noteLuongGiam		= "Nviec , luong giam = luong gan nhat co BH, " + thangGanNhatCoBH;
							tangBSBHYT			= "1";
							thoaiThuBSBHYT		= "0";
						}
						else{
							noteGiam			= "Ro->Nviệc";								
							sal_BaoGiam			= 0;
							noteLuongGiam		= "RoRa->NViec nen luong bao giam = 0";
							tangBSBHYT			= "0";
							thoaiThuBSBHYT		= "1";							
						}			
						giam1Thang="0";// vi lan 2 chac chan ko du cong
						tang1Thang	= "0";
						dc1Thang	= "0";
					}// end cua QT 20 thang giam lan 1, chua QT20 lan 2 thang giam
					// da QT20 thang giam
					else {						
				        // vi QT thang giam nen se xet RoVao ke tu thang giam
						data1			= insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiam),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH 	= data1[0];
						luongGanNhatCoBH	= data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
						tgianTang_DC_Giam	=null;	
						sal_TangOrDCOrGiam	=0;
						tang1Thang	= "0";
						dc1Thang	= "0";	
						giam1Thang="0";
						
						//>=09/2013 bao Ro tu thang
						if (dkbh_GiamLan2==1){
							noteGiam			= "Nviệc";
							sal_BaoGiam			= sal_BSBHYT;
							noteLuongGiam		= "Nviec , luong giam = luong gan nhat co BH, " + thangGanNhatCoBH;
							tangBSBHYT			= "1";
							thoaiThuBSBHYT		= "0";							
						}
						else{
							noteGiam			= "Ro->Nviệc";								
							sal_BaoGiam			= 0;
							noteLuongGiam		= "RoRa->NViec nen luong bao giam = 0";
							tangBSBHYT			= "0";
							thoaiThuBSBHYT		= "1";
						}
					}// end cua da QT20 thang giam
				}//end cua NSVao< thang giam -1
			}
		}	// het if (ngayDiLamLai==null || (ngayDiLamLai!=null && thangTangLai.compareTo(thangGiamTru1)<0))	
		else// co thong tin tang lai
		{
			//Thang tang lai = thang giam
			if(thangTangLai.compareTo(thangGiam)==0){
				if(ngayChayBC.compareTo(ngay20_Giam)<0){
					if(thoiDiemTangLai==1){
						sal_BSBHYT = sal_Giam - sal_dcGiam;					
					}
					else{
						sal_BSBHYT = sal_Giam;
					}
					sal_BaoGiam	= sal_BSBHYT;	
					noteGiam    ="Nviệc";
					noteLuongGiam ="Bao giam = thang bao tang tro lai, luong giam =luong "+thangBaoGiam+" ( tru DC_dongloat neu co)";
					tang1Thang	= "0";
					dc1Thang	= "0";
					giam1Thang="0";
					tgianTang_DC_Giam= null;
					sal_TangOrDCOrGiam= 0;	
				}
			}// end cua thang tang lai= thang giam
			//Thang tang lai = thang giam tru 1
			else if (thangTangLai.compareTo(thangGiamTru1)==0){
				//Chua QT20 lan 1 thang giam -1
				if (ngayChayBC.compareTo(ngay20_Giam1)<0){
					if(thoiDiemTangLai==1){
						sal_BSBHYT = sal_Giam1 - sal_dcGiam1;					
					}
					else{
						sal_BSBHYT = sal_Giam1;
					}		
					sal_BaoGiam = sal_BSBHYT;	
					noteGiam    ="Nviệc";
					noteLuongGiam ="Bao giam = thang bao tang tro lai, luong giam =luong "+sdf.format(thangGiamTru1)+" ( tru DC_dongloat neu co)";
					//Thang giam -1 chua QT lan 1 nhung da bao giam luc nay luon roi nen se xet cong trong 1 thang va chac chan se du cong 					 
					if(dkbh_Giam1Lan2==1 && sal_BSBHYT!=sal_Giam1){
						tang1Thang	="0";
						dc1Thang	="1";
						giam1Thang="0";
						tgianTang_DC_Giam=sdf.format(thangGiamTru1);
						sal_TangOrDCOrGiam=sal_Giam1;
					}
					else{
						tang1Thang	="0";
						dc1Thang	="0";
						giam1Thang="0";
						tgianTang_DC_Giam=null;
						sal_TangOrDCOrGiam=0;
					}
				}
				//thang giam -1 da Qt lan 1, Chua QT20 lan 2
				else if (ngayChayBC.compareTo(ngay20_Giam1)>=0 && ngayChayBC.compareTo(ngay20_Giam1Lan2)<0){
					if(thoiDiemTangLai==1){
						sal_BSBHYT = sal_Giam1 - sal_dcGiam1;					
					}
					else{
						sal_BSBHYT = sal_Giam1;
					}		
					//Thang giam -1 QT lan 1 nen se xet theo ngay cong lan 1, lan 2 chac chan du cong 					 
					if(dkbh_Giam1==-1){
						noteGiam    ="Ro->Nviệc";
						sal_BaoGiam = 0;					
						noteLuongGiam ="Ro->NViec, luong giam =0";
						tangBSBHYT="0";
						thoaiThuBSBHYT="1";
						// chi xet lan 2 du cong, lan 2 ko du cong ko co gi thay doi
						if (dkbh_Giam1Lan2==1){
							if (sal_Giam1!=sal_BSBHYT){
								dc1Thang="1";
								tang1Thang="0";
								giam1Thang="0";							
							}
							else{
								dc1Thang="0";
								tang1Thang="1";
								giam1Thang="0";								
							}
							tgianTang_DC_Giam=sdf.format(thangGiamTru1);
							sal_TangOrDCOrGiam=sal_Giam1;	
						}
					}
					else{
						noteGiam    ="Nviệc";
						sal_BaoGiam = sal_BSBHYT;					
						noteLuongGiam ="NViec, luong giam =0";
						tangBSBHYT="1";
						thoaiThuBSBHYT="0";
						if(dkbh_Giam1Lan2==-1){
							tang1Thang	="0";
							dc1Thang	="0";
							giam1Thang="1";
							tgianTang_DC_Giam=sdf.format(thangGiamTru1);
							sal_TangOrDCOrGiam=sal_Giam1;			
						}
					}
				}
				//Da QT20 thang giam -1, chua Qt20 lan 1 thang giam
				else if(ngayChayBC.compareTo(ngay20_Giam1Lan2)>=0 && ngayChayBC.compareTo(ngay20_Giam)<0){					
					sal_BSBHYT = sal_Giam1;
					//if thang giam -1 %RoRa
					if (dkbh_Giam1Lan2==-1){
						sal_BaoGiam	= 0;
						noteGiam	= "Ro->Nviệc";
						noteLuongGiam = "Da QT20 ThangGiam-1: TangMoiLai->RoRa, chua QT20 ThangGiam, luong= luong 0 ";
						tangBSBHYT ="0";
						thoaiThuBSBHYT ="1";
					}
					else
					{
						sal_BaoGiam	= sal_BSBHYT;
						noteGiam	= "Nviệc";
						noteLuongGiam = "Da QT20 ThangGiam-1: TangMoiLai, chua QT20 ThangGiam, luong= luong thang "+sdf.format(thangGiamTru1);
						tangBSBHYT="1";
						thoaiThuBSBHYT ="0";
					}					
					tang1Thang	="0";
					dc1Thang	="0";
					giam1Thang="0";
					tgianTang_DC_Giam=null;	
					sal_TangOrDCOrGiam=0;					
				}
				//thang giam da QT lan 1, chua Qt20 lan 2
				else if(ngayChayBC.compareTo(ngay20_Giam)>=0 && ngayChayBC.compareTo(ngay20_GiamLan2)<0){					
					data1	= insDAO.TTThamGiaBHGanNhat(soThe, thangBaoGiam,sdf.format(ngayBaoGiam.getTime()));
					thangGanNhatCoBH 	= data1[0];
					luongGanNhatCoBH	= data1[1];
					sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
					//if thang giam %RoRa
					if (dkbh_Giam==-1){
						sal_BaoGiam	= 0;
						noteGiam	= "Ro->Nviệc";
						noteLuongGiam = "Ro->NViec, luong= luong 0 ";
						tangBSBHYT ="0";
						thoaiThuBSBHYT ="1";
					}
					else
					{
						sal_BaoGiam	= sal_BSBHYT;
						noteGiam	= "Nviệc";
						noteLuongGiam = "Thang giam lan 1 du cong, luong= luong thang "+sdf.format(thangGiam);
						tangBSBHYT="1";
						thoaiThuBSBHYT ="0";
					}					
					tang1Thang	="0";
					dc1Thang	="0";
					giam1Thang="0";// da giam chinh thang giam roi nen ko can giam 1 thang nua
					tgianTang_DC_Giam=null;	
					sal_TangOrDCOrGiam=0;					
				}
				//Da QT20 lan 2 thang giam
				else if (ngayChayBC.compareTo(ngay20_GiamLan2)>=0){
					data1	= insDAO.TTThamGiaBHGanNhat(soThe, thangBaoGiam,sdf.format(ngayBaoGiam.getTime()));
					thangGanNhatCoBH 	= data1[0];
					luongGanNhatCoBH	= data1[1];
					sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
					sal_BaoGiam			= sal_BSBHYT;
					tang1Thang	="0";
					dc1Thang	="0";
					giam1Thang="0";
					tgianTang_DC_Giam=null;	
					sal_TangOrDCOrGiam=0;
					
			  		if(dkbh_GiamLan2==-1)
			  		{
			  			sal_BaoGiam	= 0;
						noteGiam	= "Ro->Nviệc";	
						noteLuongGiam ="Da QT20 ThangGiam: %RoRa, luong giam =luong "+thangGanNhatCoBH;
						tangBSBHYT ="0";
						thoaiThuBSBHYT="1";
			  		}
			  		else
			  		{
			  			sal_BaoGiam	= sal_BSBHYT;
						noteGiam	= "Nviệc";	
						noteLuongGiam ="Da QT20 ThangGiam: du cong, luong giam =luong "+thangGanNhatCoBH;
						tangBSBHYT="1";
						thoaiThuBSBHYT="0";
			  		}			  		
				}
			}//end thang tang lai = thang giam -1
			//Thang tang lai < thang giam tru 1
			else if (thangTangLai.compareTo(thangGiamTru1)<0){
				//chua QT20 lan 1 thang giam -1
			  	if (ngayChayBC.compareTo(ngay20_Giam1)<0)
			  	{
			  		data1 = insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru2),sdf.format(ngayBaoGiam.getTime()));
			  		thangGanNhatCoBH	= data1[0];
			  		luongGanNhatCoBH	= data1[1];
			  		noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiamTru2)+
			  				". Chua QT20 "+sdf.format(thangGiamTru1)+", luong = luong cua thang "+thangGanNhatCoBH;
			  		sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
			  		// if Giam-2 la %Ro, Giam-1 du cong thi Tang or DC giam-1
			  		// >= 09/2013 bao Ro tu thang: if Giam-2 la %Ro, Giam-1 du cong thi Tang giam-1
			  		if(dkbh_Giam2Lan2==-1)
			  		{	
			  			noteGiam	= "Ro->Nviệc";
			  			sal_BaoGiam=0;
			  			noteLuongGiam		= "Ro->NViec, luong giam =0";
			  			tangBSBHYT="0";
			  			thoaiThuBSBHYT="1";
			  			if(dkbh_Giam1Lan2==1){
			  				if(sal_Giam1!=sal_BSBHYT){
						  		dc1Thang="1";
						  		tang1Thang="0";
						  		giam1Thang="0";
			  				}
			  				else{
						  		dc1Thang="0";
						  		tang1Thang="1";
						  		giam1Thang="0";			  					
			  				}
				  			tgianTang_DC_Giam = sdf.format(thangGiamTru1);
				  			sal_TangOrDCOrGiam = sal_Giam1;
			  			}
			  		}
			  		else
			  		{
			  			noteGiam	= "Nviệc";
			  			sal_BaoGiam	= sal_BSBHYT;
			  			tangBSBHYT="1";
			  			thoaiThuBSBHYT="0";			  			
			  			//if Giam-2 du cong, Giam -1 lan 2 cung du cong va khac luong gan nhat thi bao DC Giam-1
			  			if(dkbh_Giam1Lan2==1){
			  				if(sal_Giam1!=sal_BSBHYT){
						  		dc1Thang="1";
						  		tang1Thang="0";
						  		giam1Thang="0";
			  				}
			  				else{
						  		dc1Thang="0";
						  		tang1Thang="1";
						  		giam1Thang="0";			  					
			  				}
				  			tgianTang_DC_Giam = sdf.format(thangGiamTru1);
				  			sal_TangOrDCOrGiam = sal_Giam1;
			  			}
			  		}
			  	}
			  	//Da QT20 lan 1 thang giam -1 , chua QT20 thang giam-1 lan 2
			  	else if(ngayChayBC.compareTo(ngay20_Giam1)>=0 && ngayChayBC.compareTo(ngay20_Giam1Lan2)<0) {
			  		data1 = insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru1),sdf.format(ngayBaoGiam.getTime()));
			  		thangGanNhatCoBH	= data1[0];
			  		luongGanNhatCoBH	= data1[1];
			  		noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiamTru1)+
			  				". Chua QT20 "+sdf.format(thangGiam)+", luong = luong cua thang "+thangGanNhatCoBH;
			  		sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);	
			  		if(dkbh_Giam1==-1)
			  		{
			  			sal_BaoGiam	= 0;
						noteGiam	= "Ro->Nviệc";	
						tangBSBHYT="0";
						thoaiThuBSBHYT="1";
			  		}
			  		else
			  		{
			  			sal_BaoGiam	= sal_BSBHYT;
						noteGiam	= "Nviệc";
						tangBSBHYT="1";
						thoaiThuBSBHYT="0";
			  		}
			  		if (dkbh_Giam1Lan2==1){
			  			if(sal_BSBHYT!=sal_Giam1){
			  				tang1Thang="0";
			  				dc1Thang="1";
			  				giam1Thang="0";
			  			}
			  			else{
			  				tang1Thang="1";
			  				dc1Thang="0";
			  				giam1Thang="0";			  				
			  			}
			  			tgianTang_DC_Giam = sdf.format(thangGiamTru1);
			  			sal_TangOrDCOrGiam = sal_Giam1;			  			
			  		}
			  	}
			  	//Da QT20 thang giam -1 , chua QT20 thang giam lan 1
			  	else if(ngayChayBC.compareTo(ngay20_Giam1Lan2)>=0 && ngayChayBC.compareTo(ngay20_Giam)<0) {
			  		data1 = insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru1),sdf.format(ngayBaoGiam.getTime()));
			  		thangGanNhatCoBH	= data1[0];
			  		luongGanNhatCoBH	= data1[1];
			  		noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiamTru1)+
			  				". Chua QT20 lan 1 "+sdf.format(thangGiam)+", luong = luong cua thang "+thangGanNhatCoBH;
			  		sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);	
			  		if(dkbh_Giam1Lan2==-1)
			  		{
			  			sal_BaoGiam	= 0;
						noteGiam	= "Ro->Nviệc";	
						tangBSBHYT="0";
						thoaiThuBSBHYT="1";
			  		}
			  		else
			  		{
			  			sal_BaoGiam	= sal_BSBHYT;
						noteGiam	= "Nviệc";
						tangBSBHYT="1";
						thoaiThuBSBHYT="0";
			  		}
	  				tang1Thang="0";
	  				dc1Thang="0";
	  				giam1Thang="0";			  			
		  			tgianTang_DC_Giam = null;
		  			sal_TangOrDCOrGiam = 0;	  		
			  	}
			  	//Da QT20 lan 1 thang giam , chua QT20 thang giam lan 2
			  	else if(ngayChayBC.compareTo(ngay20_Giam)>=0 && ngayChayBC.compareTo(ngay20_GiamLan2)<0) {
			  		data1 = insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiam),sdf.format(ngayBaoGiam.getTime()));
			  		thangGanNhatCoBH	= data1[0];
			  		luongGanNhatCoBH	= data1[1];
			  		noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiam)+
			  				". Chua QT20 lan 2 "+sdf.format(thangGiam)+", luong = luong cua thang "+thangGanNhatCoBH;
			  		sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);	
			  		if(dkbh_Giam==-1)
			  		{
			  			sal_BaoGiam	= 0;
						noteGiam	= "Ro->Nviệc";	
						tangBSBHYT="0";
						thoaiThuBSBHYT="1";
			  		}
			  		else
			  		{
			  			sal_BaoGiam	= sal_BSBHYT;
						noteGiam	= "Nviệc";
						tangBSBHYT="1";
						thoaiThuBSBHYT="0";
			  		}
	  				tang1Thang="0";
	  				dc1Thang="0";
	  				giam1Thang="0";			  			
		  			tgianTang_DC_Giam = null;
		  			sal_TangOrDCOrGiam = 0;	  		
			  	}
			  	//Da Qt20 thang giam
			  	else {
			  		data1 = insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiam),sdf.format(ngayBaoGiam.getTime()));
			  		thangGanNhatCoBH	= data1[0];
			  		luongGanNhatCoBH	= data1[1];
			  		noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiam)+
			  				". Da QT20 "+sdf.format(thangGiam)+", luong = luong cua thang "+thangGanNhatCoBH;
			  		sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);	
			  		if(dkbh_GiamLan2==-1){
			  			sal_BaoGiam	= 0;
						noteGiam	= "Ro->Nviệc";		
						tangBSBHYT="0";
						thoaiThuBSBHYT="1";
			  		}
			  		else{
			  			sal_BaoGiam	= sal_BSBHYT;
						noteGiam	= "Nviệc";	
						tangBSBHYT="1";
						thoaiThuBSBHYT="0";						
			  		}
			  	}		  	
			}// end cua thang tang lai < thang giam -1			
		}// end co thong tin tang lai
		return new String[]{String.valueOf(sal_BSBHYT), String.valueOf(sal_BaoGiam), noteGiam, noteLuongGiam, tangBSBHYT, thoaiThuBSBHYT, tang1Thang, dc1Thang, tgianTang_DC_Giam, String.valueOf(sal_TangOrDCOrGiam),giam1Thang};
	}		
		
	private void ExportDSLDGiam(int r, int i, int tsLDGiam, HSSFWorkbook wb,
			HSSFSheet sheet, HSSFRow row, HSSFCell cell, BieuMau3A data) {

		// System.out.println(data.getEmpsn());

		cell = row.getCell(0);
		//cell.setCellValue(i + 1);
		cell.setCellValue(i+demBSLDGiam + 1);
		cell = row.getCell(1);
		cell.setCellValue(data.getHoTen());

		if (data.getMaSoBHXH() != null) {
			cell = row.getCell(2);
			cell.setCellValue(data.getMaSoBHXH());
		}
		cell = row.getCell(11); // muc luong moi
		cell.setCellValue(0);

		cell = row.getCell(16);
		cell.setCellValue(insDAO.ToDate(data.getTuThang()));// thang bao giam
		cell = row.getCell(17);

		if (data.getDenThang() != null) {
			cell.setCellValue(insDAO.ToDate(data.getDenThang()));
		}
		cell = row.getCell(19); // co/ ko co tra lai the BHYT
		if (data.getCoTraThe() == null || data.getCoTraThe().equals("0")) {
			cell.setCellValue("X");
		}

		cell = row.getCell(21);
		cell.setCellValue(data.getEmpsn() + ", NV " + data.getNgayThucNghi()
				+ ", Tra the BHYT :" + data.getNgayTraThe());

		cell = row.createCell(48);// So the
		HSSFUtils.fill(cell, data.getEmpsn());
		cell = row.createCell(49);// So the
		HSSFUtils.fill(cell, data.getFactGroup());
		if (data.getDonVi() != null) {
			cell = row.createCell(50);// So the
			HSSFUtils.fill(cell, data.getDonVi());
		}

		// System.out.println(data.getEmpsn());
		// gia tri tra ve theo thu tu
		// Sal_BSBHYT, SAl_BaoGiam, NoteGiam, noteLuongGiam, tangBSBHYT,
		// ThoaiThuBSBHYT, tang1Thang, dc1Thang, tgianTang_DC, sal_TangorDC // old < 10/2013
		// ThoaiThuBSBHYT, tang1Thang, dc1Thang, tgianTang_DC, sal_TangorDC, giam1Thang  // new  >= 10/2013
		String[] data1 = GetThongTinBaoGiam(data.getEmpsn(), data.getTuThang(),
				data.getNgayTraThe());

		// luong bao giam
		cell = row.getCell(6);
		HSSFUtils.fill(cell, Double.parseDouble(data1[1]));
		// ghi chu bao giam
		cell = row.getCell(20);
		HSSFUtils.fill(cell, data1[2]);

		boolean giahanThe = false;
		if (cbTheGiaHan.isSelected())
			giahanThe =true;	
		
		Date ngayTraThe = null;
		if (data.getNgayTraThe() != null) {
			ngayTraThe = insDAO.ToDate(data.getNgayTraThe());
		}
		Date ngayThucNghi = null;
		if (data.getNgayThucNghi() != null) {
			ngayThucNghi = insDAO.ToDate(data.getNgayThucNghi());
		}
		Date ngayBaoGiam = null;
		Calendar temp = dfNgayBaoGiam.getSelectedDate();
		ngayBaoGiam = insDAO.ToDate(sdf.format(temp.getTime()));
		// thangHienTai la thang bao cao goc theo thoi gian hien tai, duoc gan
		// khi goi export trong DoExport
		// lay tyledong khi bao giam, tylebs BHYT, bs tuthang-> bs denthang
		// theo thu tu tyleDong, tyleBS, noteBS, bsTuThang, bsDenThang
		String[] data2 = insDAO.GetTyLeGiamTangBSTheoNgayTraTheBHYT(
				ngayBaoGiam, ngayTraThe, sdf.format(ngayThucNghi),
				insDAO.ToDate(data.getTuThang()), thangHienTai, giahanThe,
				data1[5]);
		// tyleBHXH
		cell = row.getCell(22);
		HSSFUtils.fill(cell, tyleBHXH);
		// tyleBHTN
		cell = row.getCell(24);
		HSSFUtils.fill(cell, tyleBHTN);
		//String tyleGiam = data2[0];

		if (Double.valueOf(data2[0]) == tyleBHXH + tyleBHTN + tyleBHYT) {
			// tyleBHYT
			cell = row.getCell(23);
			HSSFUtils.fill(cell, tyleBHYT);
		} else {
			// tyleBHYT
			cell = row.getCell(23);
			HSSFUtils.fill(cell, 0);
		}

		for (int k = 25; k < 48; k++) {
			cell = row.getCell(k);
			HSSFUtils.evalFormula(wb, cell);
		}

		// bat dau xet tang 1 thang/ DC 1 thang/ Giam 1 thang / Tang BS BHYT
		int salBSBHYT = Integer.parseInt(data1[0]);
		int salTangOrDC = Integer.parseInt(data1[9]);
		String noteBS = null;
		String noteTangOrDC = null;

		// moi lan Tang/DC/Tang BS BHYT thi se thay doi dong chuan
		// dong chuan =dong chuan + bien dem cua tung loai
		// =--> dam bao rowNew luon lien ke rowOld
		// so sanh theo font vni-times
		// if la tang 1 thang

		int dongChuanLDTang = 11;
		int dongChuanDCTang = 13;
		int dongChuanBSTang = 16;
		// phai cong kem tong so record(sql) cho ld giam o day, vi minh da tu
		// insert row o ban ngoai truoc roi
		//int dongChuanBSGiam = 25 + tsLDGiam;
		int dongChuanBSGiam = 25 + tsLDGiam+demBSLDGiam;// Thoai thu BHYT		
		//them moi T102013
		int dongChuanBSLDGiam = 21+demLDGiam;// r la dong cua lao dong giam hien tai , da bao gom LDtang, DCTang, TangBSBHYT, LDGiam, BSLDGiam

		if (data1[6].equals("1")) {
			noteTangOrDC = "Taêng 1 Thaùng";
			demLDTang = Export_TangDC1ThangOrBS(wb, sheet, row, cell, data,
					demLDTang, demDCTang, demBSTang, demLDGiam,demBSLDGiam, demBSBHYTGiam,					
					dongChuanLDTang + demLDTang, 0, salTangOrDC, data1[8],
					data1[8], noteTangOrDC);
		}
		// if la DC 1 thang
		// chayvo
		if (data1[7].equals("1")) {
			noteTangOrDC = "Ñ/C Löông";
			demDCTang = Export_TangDC1ThangOrBS(wb, sheet, row, cell, data,
					demLDTang, demDCTang, demBSTang, demLDGiam,demBSLDGiam, demBSBHYTGiam,					
					dongChuanDCTang + demDCTang, salBSBHYT, salTangOrDC,
					data1[8], data1[8], noteTangOrDC);
		}
		//Bo sung them giam 1 thang 10/2013
		if (data1[10].equals("1")) {
			noteTangOrDC = "Ro";
			demBSLDGiam = Export_TangDC1ThangOrBS(wb, sheet, row, cell, data,
					demLDTang, demDCTang, demBSTang, demLDGiam,demBSLDGiam, demBSBHYTGiam,					
					//vi giamBSLDGiam o dong lien ke ldGiam
					dongChuanBSLDGiam+demBSLDGiam, salBSBHYT, 0,
					data1[8], data1[8], noteTangOrDC);
		}
		// tang thi luong o muc new, muc old =0
		if (data1[4].equals("1") && data2[1].equals("0.045")) {
			noteBS = "Taêng BSBHYT";
			// System.out.println(data2[3]+","+data2[4]);
			demBSTang = Export_TangDC1ThangOrBS(wb, sheet, row, cell, data,
					demLDTang, demDCTang, demBSTang, demLDGiam,demBSLDGiam, demBSBHYTGiam,					
					dongChuanBSTang + demBSTang, 0, salBSBHYT, data2[3],
					data2[4], noteBS);
		}

		// giam thi luong o muc old, muc new =0
		if (data1[5].equals("1") && data2[5].equals("0.045")) {
			noteBS = "Thoaùi Thu BSBHYT";
			// System.out.println(data2[3]+","+data2[4]);
			demBSBHYTGiam = Export_TangDC1ThangOrBS(wb, sheet, row, cell, data,
					demLDTang, demDCTang, demBSTang, demLDGiam,demBSLDGiam, demBSBHYTGiam,										
					dongChuanBSGiam + demBSBHYTGiam, salBSBHYT, 0, data2[6],
					data2[7], noteBS);
		}
	}
	
	private void doExportGiamTuanNew() throws IOException {

		Calendar ngayBaoGiam = dfNgayBaoGiam.getSelectedDate();
		if (ngayBaoGiam.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY
				&& ngayBaoGiam.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Chi duoc chon thu 2 or thu 5");
			return;
		}
		int month = 0, year = 0;
		Calendar cal = Calendar.getInstance();
		cal.set(ngayBaoGiam.get(Calendar.YEAR),
				ngayBaoGiam.get(Calendar.MONTH), 20);
		if (ngayBaoGiam.compareTo(cal) > 0) {
			cal.add(Calendar.MONTH, 1);
		}
		month = cal.get(Calendar.MONTH) + 1;// Calendar Month 0->11, trong class
											// InsuranceDAO tu 1-12
		year = cal.get(Calendar.YEAR);
		// System.out.println(month + "  " + year);
		InsuranceDAO dao = new InsuranceDAO();
		dao.setMonth(month);
		dao.setYear(year);

		List<BieuMau3A> list = dao.getDSBaoGiamTuan(
				insDAO.getFactCondition(sfMaDonVi, sfXuong, sfNhom, sfDonVi),
				ngayBaoGiam);

		HSSFWorkbook wb = insDAO.getWorkbook("insurance",
				"Mau_D02-TS_cong_thuc_GiamTuan.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFRow templateRow = sheet.getRow(10);
		HSSFCell cell;
		int r = 21;// old 11, la dong chuan cua lao dong giam

		FormulaEvaluator evaluator = wb.getCreationHelper()
				.createFormulaEvaluator();

		int rowTongHop = 42;
		
		// gan thoi gian hien tai cho cell Y3 trong file mau excel
		// java dem tu 0, excel dem tu 1
		HSSFRow rowTGHienHanh = sheet.getRow(2);// la row thu 3 trong excel
		cell = rowTGHienHanh.getCell(24);
		cell.setCellValue(insDAO.ToDate(thangHienTai));

		int rowToCopy3 = 21;
		int startRow3 = 22;	
		int stt	=0;
		//int incRows = list.size() - 1;
		int incRows=list.size();

		if (incRows > 0) {
			sheet.shiftRows(startRow3, sheet.getLastRowNum(), incRows, false,
					true);
		}

		// dong chuan tren file mau de lay cong thuc
		HSSFRow rowForm = sheet.getRow(11); 
		for (int i = startRow3; i < startRow3 + incRows; i++) {
			//HSSFUtils.copyRow(wb, sheet, rowToCopy3++, i);
			HSSFUtils.copyRowM(wb, sheet, rowForm, i);
		}

		rowToCopy3 = 21;
/*
		int lanBC;
		cal = Calendar.getInstance();
		cal.set(ngayBaoGiam.get(Calendar.YEAR), ngayBaoGiam.get(Calendar.MONTH), 12);
		lanBC= ngayBaoGiam.compareTo(cal)>0?1:2;
		*/
		// for (BieuMau3A data:list){
		for (int i = 0; i < list.size(); i++) {
			// fill increase		
			row = sheet.getRow(rowToCopy3);

			BieuMau3A data = list.get(i);			
			System.out.println(data.getEmpsn()+", Fill row " + rowToCopy3);
			
			demLDGiam++;		
			//stt=demLDGiam+demBSLDGiam;
			InsuranceLogic2Lan logic = InsuranceLogic2Lan.getInstanse(data, dao);
			MonthYearType thangBaoGiam = logic.getThangBaoGiam();
			data.setTuThang("01/" + thangBaoGiam.toString());
			data.setDenThang(null);
			ExportDSLDGiam(rowToCopy3, i, incRows, wb, sheet, row, cell, data);
			//ExportDSLDGiam(rowToCopy3, stt, incRows, wb, sheet, row, cell, data);
			// rowToCopy3= dong chuan cua
			// ldGiam+demLDTang+demDCTang+demBSTang+demLDGiam
			//rowToCopy3 = r + demLDTang + demDCTang + demBSTang + demLDGiam;// +
																			// cong
																			// DemLdGIam
																			// o
																			// day
																			// duoc
			// ldGiam+demLDTang+demDCTang+demBSTang+demLDGiam+ demBSLDGiam
			rowToCopy3 = r + demLDTang + demDCTang + demBSTang + demLDGiam+demBSLDGiam;			
		}
		
		try {
			rowTongHop = rowTongHop+demLDTang+demDCTang+demBSTang+demLDGiam+demBSLDGiam+demDCGiam+demBSBHYTGiam;					
			row= sheet.getRow(rowTongHop);
			BigDecimal[] kyNay = insDAO.getSoLieuKyTruocKyNay(wb, sheet, row, rowTongHop, cell, evaluator, soLDKyTruoc, tongQLKyTruoc, thangHienTai, dotBaoGiam, soLD, tongQL, soPhaiDong);
			//if chay toan bo cty moi update
			if (sfXuong.getSelectedIndex()==-1 && sfNhom.getSelectedIndex()==-1 && sfDonVi.getSelectedIndex()==-1)
			{
				soLD = kyNay[0];
				tongQL = kyNay[1];
				soPhaiDong = kyNay[2];
				insDAO.capNhatDotBaoGiam(loaiBaoGiam, dotBaoGiam,ngayBaoGiam.toString() , "", "", thangHienTai, soLD, tongQL, soPhaiDong);
			}			
			
			ReportUtils.doExportExcel(wb, "BaoGiamTuan");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	protected void doExport() {
		// lay thong tin thang hien hanh
		// moi lan goi cua lenh Export deu load lai gia tri khoi tao ban dau
		GiaTriKhoiTao();
		int month = 0, year = 0;
		ngayBaoGiam = dfNgayBaoGiam.getSelectedDate();
		Calendar cal = Calendar.getInstance();
		cal.set(ngayBaoGiam.get(Calendar.YEAR),
				ngayBaoGiam.get(Calendar.MONTH), 20);
		if (ngayBaoGiam.compareTo(cal) > 0) {
			cal.add(Calendar.MONTH, 1);
		}

		month = cal.get(Calendar.MONTH);// Calendar Month 0->11, trong class
										// InsuranceDAO tu 1-12
		year = cal.get(Calendar.YEAR);		
		// System.out.println("Thang: "+month); // VD month luc nay la 2 va
		// calendar se chay tu 0->11, 2 la Thang 3

		cal.set(year, month, 1);
		thangHienTai = sdf.format(cal.getTime());// luc nay gia tri tra ve la
												// 01/03/2013
		// System.out.println("Thoi gian hien tai: "+thangHienTai);

		if (checkReport()){
			//gan thong tin dot bao giam
			if(radCapSo1Tay.isSelected()==true || radDieuChinhHoSo.isSelected()==true) {
				dotBaoGiam =0;
			}
			else {
				dotBaoGiam= Integer.parseInt(txtDotBaoCao.getText());				
			}
			
			StopWatch sw = new StopWatch();
			sw.reset();
			sw.start();	
			if (radTang1Tay.isSelected()) {
				try {
					loaiBaoGiam =1;
					
					doExportTang1Tay();
					System.out.println("OK");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (radCapSo1Tay.isSelected()) {
				try {
					loaiBaoGiam = 0;
					doExportCapSoMoi();
					System.out.println("OK");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (radTang15Tay.isSelected()) {
				try {
					loaiBaoGiam =3;
					System.out.println("chua lam");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (radGiamTuan.isSelected()) {
				try {
					loaiBaoGiam=5;
					doExportGiamTuanNew();
					System.out.println("OK");
				} catch (Exception e) {
					e.printStackTrace();
				}
	
			} else if (radGiamTrongTangMoi.isSelected()) {
				try {
					loaiBaoGiam = 2;
					doExportGiamTangMoi();
					System.out.println("OK");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (radDieuChinhHoSo.isSelected()) {
				try {
					loaiBaoGiam = 0;
					doExportThayDoiTTBHQuy();
					System.out.println("OK");
				} catch (Exception e) {
					e.printStackTrace();
				}
	
			} else if (radQT201.isSelected()) {
				try {
					loaiBaoGiam = 4;
					doExportQT20();
					System.out.println("OK");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (radQT202.isSelected()) {
				try {
					loaiBaoGiam =4;
					doExportQT20();
					System.out.println("OK");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			sw.stop();
			System.out.println("Run export " + (float) sw.getTime() / 1000 + " s");
		}
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(40, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/SplitVertBar.png");
		splitPane1.setSeparatorVerticalImage(new FillImage(imageReference1));
		add(splitPane1);
		Row row1 = new Row();
		row1.setInsets(new Insets(new Extent(9, Extent.PX)));
		SplitPaneLayoutData row1LayoutData = new SplitPaneLayoutData();
		row1LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		row1LayoutData.setBackground(new Color(0x8080ff));
		row1.setLayoutData(row1LayoutData);
		splitPane1.add(row1);
		Label label11 = new Label();
		label11.setText("BÁO BIỂU BIẾN ĐỘNG TRONG THÁNG");
		label11.setFont(new Font(null, Font.BOLD, new Extent(13, Extent.PT)));
		label11.setForeground(new Color(0x800080));
		row1.add(label11);
		Grid rootLayout = new Grid();
		rootLayout.setInsets(new Insets(new Extent(5, Extent.PX)));
		splitPane1.add(rootLayout);
		GroupBox groupBox2 = new GroupBox();
		groupBox2.setTitle("Thời gian");
		groupBox2.setHeight(new Extent(250, Extent.PX));
		groupBox2.setWidth(new Extent(400, Extent.PX));
		GridLayoutData groupBox2LayoutData = new GridLayoutData();
		groupBox2LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBox2.setLayoutData(groupBox2LayoutData);
		rootLayout.add(groupBox2);
		Grid thoiGianGrid = new Grid();
		thoiGianGrid.setInsets(new Insets(new Extent(0, Extent.PX)));
		thoiGianGrid.setSize(2);
		groupBox2.add(thoiGianGrid);
		Label label1 = new Label();
		label1.setText("Ngày chạy báo cáo");
		GridLayoutData label1LayoutData = new GridLayoutData();
		label1LayoutData.setInsets(new Insets(new Extent(5, Extent.PX),
				new Extent(5, Extent.PX)));
		label1.setLayoutData(label1LayoutData);
		thoiGianGrid.add(label1);
		dfNgayBaoCao = new DscDateField();
		dfNgayBaoCao.setWidth(new Extent(150, Extent.PX));
		dfNgayBaoCao.setUpdateFromTextField(true);
		GridLayoutData dfNgayBaoCaoLayoutData = new GridLayoutData();
		dfNgayBaoCaoLayoutData.setInsets(new Insets(new Extent(5, Extent.PX),
				new Extent(5, Extent.PX)));
		dfNgayBaoCao.setLayoutData(dfNgayBaoCaoLayoutData);
		thoiGianGrid.add(dfNgayBaoCao);
		Label label13 = new Label();
		label13.setText("Đợt báo cáo");
		GridLayoutData label13LayoutData = new GridLayoutData();
		label13LayoutData.setInsets(new Insets(new Extent(5, Extent.PX),
				new Extent(5, Extent.PX)));
		label13.setLayoutData(label13LayoutData);
		thoiGianGrid.add(label13);
		txtDotBaoCao = new TextField();
		txtDotBaoCao.setWidth(new Extent(165, Extent.PX));
		txtDotBaoCao.setInsets(new Insets(new Extent(0, Extent.PX)));
		GridLayoutData txtDotBaoCaoLayoutData = new GridLayoutData();
		txtDotBaoCaoLayoutData.setInsets(new Insets(new Extent(5, Extent.PX),
				new Extent(5, Extent.PX)));
		txtDotBaoCao.setLayoutData(txtDotBaoCaoLayoutData);
		thoiGianGrid.add(txtDotBaoCao);
		Label label2 = new Label();
		label2.setText("Tháng");
		GridLayoutData label2LayoutData = new GridLayoutData();
		label2LayoutData.setInsets(new Insets(new Extent(5, Extent.PX),
				new Extent(5, Extent.PX)));
		label2.setLayoutData(label2LayoutData);
		thoiGianGrid.add(label2);
		sfThang = new SelectField();
		sfThang.setWidth(new Extent(80, Extent.PX));
		GridLayoutData sfThangLayoutData = new GridLayoutData();
		sfThangLayoutData.setInsets(new Insets(new Extent(5, Extent.PX),
				new Extent(5, Extent.PX)));
		sfThang.setLayoutData(sfThangLayoutData);
		thoiGianGrid.add(sfThang);
		Label label3 = new Label();
		label3.setText("Năm");
		GridLayoutData label3LayoutData = new GridLayoutData();
		label3LayoutData.setInsets(new Insets(new Extent(5, Extent.PX),
				new Extent(5, Extent.PX)));
		label3.setLayoutData(label3LayoutData);
		thoiGianGrid.add(label3);
		sfNam = new SelectField();
		sfNam.setWidth(new Extent(80, Extent.PX));
		GridLayoutData sfNamLayoutData = new GridLayoutData();
		sfNamLayoutData.setInsets(new Insets(new Extent(5, Extent.PX),
				new Extent(5, Extent.PX)));
		sfNam.setLayoutData(sfNamLayoutData);
		thoiGianGrid.add(sfNam);
		Label label4 = new Label();
		label4.setText("Ngày báo giảm");
		GridLayoutData label4LayoutData = new GridLayoutData();
		label4LayoutData.setInsets(new Insets(new Extent(5, Extent.PX),
				new Extent(5, Extent.PX)));
		label4.setLayoutData(label4LayoutData);
		thoiGianGrid.add(label4);
		dfNgayBaoGiam = new DscDateField();
		GridLayoutData dfNgayBaoGiamLayoutData = new GridLayoutData();
		dfNgayBaoGiamLayoutData.setInsets(new Insets(new Extent(5, Extent.PX),
				new Extent(5, Extent.PX)));
		dfNgayBaoGiam.setLayoutData(dfNgayBaoGiamLayoutData);
		thoiGianGrid.add(dfNgayBaoGiam);
		Label label5 = new Label();
		label5.setText("Đợt báo giảm từ:");
		GridLayoutData label5LayoutData = new GridLayoutData();
		label5LayoutData.setInsets(new Insets(new Extent(5, Extent.PX),
				new Extent(5, Extent.PX)));
		label5.setLayoutData(label5LayoutData);
		thoiGianGrid.add(label5);
		lblBaoGiamTu = new Label();
		lblBaoGiamTu.setText("01/01/2011");
		lblBaoGiamTu.setForeground(new Color(0x008000));
		GridLayoutData lblBaoGiamTuLayoutData = new GridLayoutData();
		lblBaoGiamTuLayoutData.setInsets(new Insets(new Extent(5, Extent.PX),
				new Extent(5, Extent.PX)));
		lblBaoGiamTu.setLayoutData(lblBaoGiamTuLayoutData);
		thoiGianGrid.add(lblBaoGiamTu);
		Label label7 = new Label();
		label7.setText("đến: ");
		GridLayoutData label7LayoutData = new GridLayoutData();
		label7LayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		label7.setLayoutData(label7LayoutData);
		thoiGianGrid.add(label7);
		lblBaoGiamDen = new Label();
		lblBaoGiamDen.setText("12/12/2012");
		lblBaoGiamDen.setForeground(new Color(0x008000));
		GridLayoutData lblBaoGiamDenLayoutData = new GridLayoutData();
		lblBaoGiamDenLayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		lblBaoGiamDen.setLayoutData(lblBaoGiamDenLayoutData);
		thoiGianGrid.add(lblBaoGiamDen);
		cbTheGiaHan = new CheckBox();
		cbTheGiaHan.setText("Chưa có thẻ gia hạn BHYT");
		GridLayoutData cbTheGiaHanLayoutData = new GridLayoutData();
		cbTheGiaHanLayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		cbTheGiaHanLayoutData.setColumnSpan(4);
		cbTheGiaHan.setLayoutData(cbTheGiaHanLayoutData);
		thoiGianGrid.add(cbTheGiaHan);
		GroupBox groupBox3 = new GroupBox();
		groupBox3.setTitle("Đơn vị");
		groupBox3.setHeight(new Extent(250, Extent.PX));
		GridLayoutData groupBox3LayoutData = new GridLayoutData();
		groupBox3LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBox3.setLayoutData(groupBox3LayoutData);
		rootLayout.add(groupBox3);
		Grid donViGrid = new Grid();
		donViGrid
				.setInsets(new Insets(new Extent(2, Extent.PX), new Extent(5,
						Extent.PX), new Extent(2, Extent.PX), new Extent(5,
						Extent.PX)));
		groupBox3.add(donViGrid);
		Label label6 = new Label();
		label6.setText("Mã đơn vị");
		GridLayoutData label6LayoutData = new GridLayoutData();
		label6LayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		label6.setLayoutData(label6LayoutData);
		donViGrid.add(label6);
		sfMaDonVi = new SelectField();
		sfMaDonVi.setWidth(new Extent(150, Extent.PX));
		GridLayoutData sfMaDonViLayoutData = new GridLayoutData();
		sfMaDonViLayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		sfMaDonVi.setLayoutData(sfMaDonViLayoutData);
		donViGrid.add(sfMaDonVi);
		Label label8 = new Label();
		label8.setText("Xưởng");
		GridLayoutData label8LayoutData = new GridLayoutData();
		label8LayoutData.setInsets(new Insets(new Extent(5, Extent.PX),
				new Extent(5, Extent.PX), new Extent(5, Extent.PX), new Extent(
						5, Extent.PX)));
		label8.setLayoutData(label8LayoutData);
		donViGrid.add(label8);
		sfXuong = new SelectField();
		sfXuong.setWidth(new Extent(150, Extent.PX));
		GridLayoutData sfXuongLayoutData = new GridLayoutData();
		sfXuongLayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		sfXuong.setLayoutData(sfXuongLayoutData);
		donViGrid.add(sfXuong);
		Label label9 = new Label();
		label9.setText("Nhóm");
		GridLayoutData label9LayoutData = new GridLayoutData();
		label9LayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		label9.setLayoutData(label9LayoutData);
		donViGrid.add(label9);
		sfNhom = new SelectField();
		sfNhom.setWidth(new Extent(150, Extent.PX));
		GridLayoutData sfNhomLayoutData = new GridLayoutData();
		sfNhomLayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		sfNhom.setLayoutData(sfNhomLayoutData);
		donViGrid.add(sfNhom);
		Label label10 = new Label();
		label10.setText("Đơn vị");
		GridLayoutData label10LayoutData = new GridLayoutData();
		label10LayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		label10.setLayoutData(label10LayoutData);
		donViGrid.add(label10);
		sfDonVi = new SelectField();
		sfDonVi.setWidth(new Extent(150, Extent.PX));
		GridLayoutData sfDonViLayoutData = new GridLayoutData();
		sfDonViLayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		sfDonVi.setLayoutData(sfDonViLayoutData);
		donViGrid.add(sfDonVi);
		GroupBox groupBox4 = new GroupBox();
		groupBox4.setTitle("Biến động trong tháng");
		groupBox4.setHeight(new Extent(300, Extent.PX));
		groupBox4.setWidth(new Extent(400, Extent.PX));
		GridLayoutData groupBox4LayoutData = new GridLayoutData();
		groupBox4LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBox4.setLayoutData(groupBox4LayoutData);
		rootLayout.add(groupBox4);
		Grid luaChonGrid = new Grid();
		luaChonGrid.setSize(1);
		groupBox4.add(luaChonGrid);
		radTang1Tay = new RadioButton();
		radTang1Tay.setSelected(true);
		radTang1Tay.setText("Tăng trong tháng (báo cáo 1 tây)");
		radTang1Tay.setActionCommand("ra1");
		GridLayoutData radTang1TayLayoutData = new GridLayoutData();
		radTang1TayLayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		radTang1Tay.setLayoutData(radTang1TayLayoutData);
		luaChonGrid.add(radTang1Tay);
		radCapSo1Tay = new RadioButton();
		radCapSo1Tay.setText("Cấp sổ mới trong tháng (1 tây)");
		radCapSo1Tay.setActionCommand("ra2");
		GridLayoutData radCapSo1TayLayoutData = new GridLayoutData();
		radCapSo1TayLayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		radCapSo1Tay.setLayoutData(radCapSo1TayLayoutData);
		luaChonGrid.add(radCapSo1Tay);
		radGiamTuan = new RadioButton();
		radGiamTuan.setText("Giảm tuần (Từ ngày đến ngày)");
		radGiamTuan.setActionCommand("ra5");
		GridLayoutData radGiamTuanLayoutData = new GridLayoutData();
		radGiamTuanLayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		radGiamTuan.setLayoutData(radGiamTuanLayoutData);
		luaChonGrid.add(radGiamTuan);
		radGiamTrongTangMoi = new RadioButton();
		radGiamTrongTangMoi.setText("Giảm trong danh sách báo tăng mới");
		radGiamTrongTangMoi.setActionCommand("ra7");
		GridLayoutData radGiamTrongTangMoiLayoutData = new GridLayoutData();
		radGiamTrongTangMoiLayoutData.setInsets(new Insets(new Extent(5,
				Extent.PX)));
		radGiamTrongTangMoi.setLayoutData(radGiamTrongTangMoiLayoutData);
		luaChonGrid.add(radGiamTrongTangMoi);
		radDieuChinhHoSo = new RadioButton();
		radDieuChinhHoSo.setText("Điều chỉnh hồ sơ tham gia BHYT");
		radDieuChinhHoSo.setActionCommand("ra9");
		GridLayoutData radDieuChinhHoSoLayoutData = new GridLayoutData();
		radDieuChinhHoSoLayoutData.setInsets(new Insets(
				new Extent(5, Extent.PX)));
		radDieuChinhHoSo.setLayoutData(radDieuChinhHoSoLayoutData);
		luaChonGrid.add(radDieuChinhHoSo);
		radQT201 = new RadioButton();
		radQT201.setText("Quyết toán 20 lần 1");
		radQT201.setActionCommand("ra10");
		GridLayoutData radQT201LayoutData = new GridLayoutData();
		radQT201LayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		radQT201.setLayoutData(radQT201LayoutData);
		luaChonGrid.add(radQT201);
		radQT202 = new RadioButton();
		radQT202.setText("Quyết toán 20 lần 2");
		radQT202.setActionCommand("ra10");
		GridLayoutData radQT202LayoutData = new GridLayoutData();
		radQT202LayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		radQT202.setLayoutData(radQT202LayoutData);
		luaChonGrid.add(radQT202);
		radTang15Tay = new RadioButton();
		radTang15Tay.setText("Gia hạn thẻ BHYT");
		GridLayoutData radTang15TayLayoutData = new GridLayoutData();
		radTang15TayLayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		radTang15Tay.setLayoutData(radTang15TayLayoutData);
		luaChonGrid.add(radTang15Tay);
		GroupBox groupBox5 = new GroupBox();
		groupBox5.setTitle("Run");
		GridLayoutData groupBox5LayoutData = new GridLayoutData();
		groupBox5LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBox5.setLayoutData(groupBox5LayoutData);
		rootLayout.add(groupBox5);
		Grid buttonGrid = new Grid();
		buttonGrid.setInsets(new Insets(new Extent(5, Extent.PX)));
		groupBox5.add(buttonGrid);
		btnExport = new Button();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_export_excel_press.gif");
		btnExport.setPressedBackgroundImage(new FillImage(imageReference2));
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_export_excel_hover.gif");
		btnExport.setRolloverBackgroundImage(new FillImage(imageReference3));
		btnExport.setHeight(new Extent(30, Extent.PX));
		btnExport.setWidth(new Extent(120, Extent.PX));
		btnExport.setPressedEnabled(true);
		ResourceImageReference imageReference4 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_export_excel.gif");
		btnExport.setBackgroundImage(new FillImage(imageReference4));
		btnExport.setRolloverEnabled(true);
		GridLayoutData btnExportLayoutData = new GridLayoutData();
		btnExportLayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		btnExport.setLayoutData(btnExportLayoutData);
		buttonGrid.add(btnExport);
		btnImport = new Button();
		ResourceImageReference imageReference5 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_import_excel_hover.gif");
		btnImport.setRolloverBackgroundImage(new FillImage(imageReference5));
		ResourceImageReference imageReference6 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_import_excel_press.gif");
		btnImport.setPressedBackgroundImage(new FillImage(imageReference6));
		btnImport.setHeight(new Extent(30, Extent.PX));
		btnImport.setWidth(new Extent(122, Extent.PX));
		btnImport.setPressedEnabled(true);
		ResourceImageReference imageReference7 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_import_excel.gif");
		btnImport.setBackgroundImage(new FillImage(imageReference7));
		btnImport.setRolloverEnabled(true);
		buttonGrid.add(btnImport);
		btnKhoa = new Button();
		ResourceImageReference imageReference8 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_khoa_press.gif");
		btnKhoa.setPressedBackgroundImage(new FillImage(imageReference8));
		ResourceImageReference imageReference9 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_khoa_hover.gif");
		btnKhoa.setRolloverBackgroundImage(new FillImage(imageReference9));
		btnKhoa.setHeight(new Extent(30, Extent.PX));
		btnKhoa.setWidth(new Extent(120, Extent.PX));
		btnKhoa.setPressedEnabled(true);
		ResourceImageReference imageReference10 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_khoa.gif");
		btnKhoa.setBackgroundImage(new FillImage(imageReference10));
		btnKhoa.setRolloverEnabled(true);
		buttonGrid.add(btnKhoa);
		btnMoKhoa = new Button();
		ResourceImageReference imageReference11 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_mokhoa_hover.gif");
		btnMoKhoa.setRolloverBackgroundImage(new FillImage(imageReference11));
		ResourceImageReference imageReference12 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_mokhoa_press.gif");
		btnMoKhoa.setPressedBackgroundImage(new FillImage(imageReference12));
		btnMoKhoa.setHeight(new Extent(30, Extent.PX));
		btnMoKhoa.setWidth(new Extent(122, Extent.PX));
		btnMoKhoa.setPressedEnabled(true);
		ResourceImageReference imageReference13 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_mokhoa.gif");
		btnMoKhoa.setBackgroundImage(new FillImage(imageReference13));
		btnMoKhoa.setRolloverEnabled(true);
		buttonGrid.add(btnMoKhoa);
		btnKiemTra = new Button();
		ResourceImageReference imageReference14 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_kiemtra_hover.gif");
		btnKiemTra.setRolloverBackgroundImage(new FillImage(imageReference14));
		ResourceImageReference imageReference15 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_kiemtra_press.gif");
		btnKiemTra.setPressedBackgroundImage(new FillImage(imageReference15));
		btnKiemTra.setHeight(new Extent(30, Extent.PX));
		btnKiemTra.setWidth(new Extent(250, Extent.PX));
		btnKiemTra.setPressedEnabled(true);
		ResourceImageReference imageReference16 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_kiemtra.gif");
		btnKiemTra.setBackgroundImage(new FillImage(imageReference16));
		btnKiemTra.setRolloverEnabled(true);
		GridLayoutData btnKiemTraLayoutData = new GridLayoutData();
		btnKiemTraLayoutData.setColumnSpan(2);
		btnKiemTra.setLayoutData(btnKiemTraLayoutData);
		buttonGrid.add(btnKiemTra);
		btnReset = new Button();
		ResourceImageReference imageReference17 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_reset_hover.gif");
		btnReset.setRolloverBackgroundImage(new FillImage(imageReference17));
		ResourceImageReference imageReference18 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_reset_press.gif");
		btnReset.setPressedBackgroundImage(new FillImage(imageReference18));
		btnReset.setHeight(new Extent(30, Extent.PX));
		btnReset.setWidth(new Extent(250, Extent.PX));
		btnReset.setPressedEnabled(true);
		ResourceImageReference imageReference19 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_reset.gif");
		btnReset.setBackgroundImage(new FillImage(imageReference19));
		btnReset.setRolloverEnabled(true);
		GridLayoutData btnResetLayoutData = new GridLayoutData();
		btnResetLayoutData.setColumnSpan(2);
		btnReset.setLayoutData(btnResetLayoutData);
		buttonGrid.add(btnReset);
	}
}
