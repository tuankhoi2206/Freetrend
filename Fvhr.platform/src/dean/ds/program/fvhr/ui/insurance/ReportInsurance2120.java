package ds.program.fvhr.ui.insurance;

import it.businesslogic.ireport.Style;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.ParseException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import junitx.util.PrivateAccessor;

import org.apache.commons.lang.time.StopWatch;
//import org.apache.poi.hssf.record.formula.functions.Rows;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.jaxen.function.SubstringAfterFunction;

import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import fv.components.SelectItem;
import fv.util.DateUtils;
import fv.util.DbUtils;
import fv.util.FVGenericInfo;
import fv.util.HSSFUtils;
import fv.util.ListBinder;
import fv.util.MonthYearType;
import fv.util.ReportUtils;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.program.DefaultProgram;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscDateField;
import dsc.util.function.UUID;
import echopointng.GroupBox;
import echopointng.model.CalendarEvent;
import echopointng.model.CalendarSelectionListener;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;
import fv.util.HSSFUtils;

public class ReportInsurance2120 extends DefaultProgram {

	private static final long serialVersionUID = 1L;
	private SelectField sfMaDonVi;
	private SelectField sfXuong;
	private SelectField sfNhom;
	private SelectField sfDonVi;
	private Button btnExport;
	private Button btnImport;
	private Button btnKhoa;
	private Button btnMoKhoa;
	private Button btnKiemTra;
	private Button btnReset;
	private SelectField sfThang;
	private SelectField sfNam;
	private DscDateField dfNgayBaoGiam;
	private DscDateField dfNgayBaoCao;
	private RadioButton radQuy1;
	private RadioButton radQuy2;
	private RadioButton radQuy3;
	private RadioButton radQuy4;
	private RadioButton radTang1Tay;
	
	private RadioButton radCapSo1Tay;
	private RadioButton radTang20Tay;
	private RadioButton radTang15Tay;
	private RadioButton radGiamTuan;
	private RadioButton radGiam20Tay;
	private RadioButton radGiamTrongTangMoi;
	private RadioButton radDieuChinhLuong;
	private RadioButton radDieuChinhHoSo;
	private RadioButton radNghiViecCoTraThe;
	private CheckBox checkBox1;
	private Label lblBaoGiamTu;
	private Label lblBaoGiamDen;
	private InsuranceDAO insDAO;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	// cac tham so chung
	private Double tyleBHXH ; 
	private Double tyleBHTN ;
	private Double tyleBHYT ;
	private String thangHienTai;
	private int demLDTang;
	private int demDCTang;
	private int demBSTang;
	private int demLDGiam;
	private int demDCGiam;
	private int demBSGiam;
	
	/**
	 * Creates a new <code>ReportInsurance</code>.
	 */
	public ReportInsurance2120() {
		super();

		// Add design-time configured components.
		initComponents();
		moreInit();
	}
	
	private void GiaTriKhoiTao(){
		demLDTang=0;
		demDCTang=0;
		demBSTang=0;
		demLDGiam=0;
		demDCGiam=0;
		demBSGiam=0;
		tyleBHXH = Double.parseDouble(insDAO.GetField("GIATRI3", "N_THAMSO", "TENTHAMSO", "", "", "TYLEBHXH", "", ""));
		tyleBHTN = Double.parseDouble(insDAO.GetField("GIATRI3", "N_THAMSO", "TENTHAMSO", "", "", "TYLEBHTN", "", ""));
		tyleBHYT = Double.parseDouble(insDAO.GetField("GIATRI3", "N_THAMSO", "TENTHAMSO", "", "", "TYLEBHYT", "", ""));
		thangHienTai="";		
	}
	
	private void moreInit(){
		insDAO = new InsuranceDAO();
		Calendar cal = Calendar.getInstance();
		GiaTriKhoiTao();
		
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
		int month = cal.get(Calendar.MONTH);//bat dau tu 0->11
		sfThang.setSelectedIndex(month);//vo tinh trung voi index cua selectfield cua thang
		DefaultListModel yearModel = new DefaultListModel();
		int year = cal.get(Calendar.YEAR);
		yearModel.add(year-1);
		yearModel.add(year);
		yearModel.add(year+1);
		sfNam.setModel(yearModel);
		sfNam.setSelectedIndex(1);
		
		dfNgayBaoCao.setSelectedDate(cal);
		dfNgayBaoGiam.setSelectedDate(cal);
		dfNgayBaoCao.getDateChooser().setLocale(new Locale("en"));
		dfNgayBaoGiam.getDateChooser().setLocale(new Locale("en"));		
		dfNgayBaoCao.setDateFormat(sdf);
		dfNgayBaoGiam.setDateFormat(sdf);
		Calendar cal1 = Calendar.getInstance();
		while (cal1.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY&&cal1.get(Calendar.DAY_OF_WEEK)!=Calendar.THURSDAY){
			cal1.add(Calendar.DAY_OF_MONTH, -1);
		}
		dfNgayBaoGiam.setSelectedDate(cal1);
		ngayBaoGiamChanged();
		dfNgayBaoGiam.getModel().addListener( new CalendarSelectionListener(){

			public void displayedDateChange(CalendarEvent arg0) {
			}

			public void selectedDateChange(CalendarEvent arg0) {
				ngayBaoGiamChanged();
			}
			
		});	
		
		ButtonGroup group1 = new ButtonGroup();
		radQuy1.setGroup(group1);
		radQuy2.setGroup(group1);
		radQuy3.setGroup(group1);
		radQuy4.setGroup(group1);
		ButtonGroup group2 = new ButtonGroup();
		radTang1Tay.setGroup(group2);
		radCapSo1Tay.setGroup(group2);
		radTang20Tay.setGroup(group2);
		radTang15Tay.setGroup(group2);
		radGiamTuan.setGroup(group2);
		radGiam20Tay.setGroup(group2);
		radGiamTrongTangMoi.setGroup(group2);
		radDieuChinhLuong.setGroup(group2);
		radDieuChinhHoSo.setGroup(group2);
		radNghiViecCoTraThe.setGroup(group2);
		//Dept
		
		ListBinder.bindSelectField(sfMaDonVi, FVGenericInfo.getAllDept(), false);
		ListBinder.bindSelectField(sfXuong, FVGenericInfo.getFactories(), false);
		ListBinder.bindSelectField(sfNhom, FVGenericInfo.getAllGroup(), true);
		ListBinder.bindSelectField(sfDonVi, FVGenericInfo.getAllDeptName(), true);
		//filter
		sfXuong.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				ListBinder.bindSelectField(sfMaDonVi, FVGenericInfo.getDept(sfXuong.getSelectedItem().toString()), false);
				ListBinder.bindSelectField(sfNhom, FVGenericInfo.getGroup(sfXuong.getSelectedItem().toString()), true);
				ListBinder.bindSelectField(sfDonVi, FVGenericInfo.getDeptName(sfXuong.getSelectedItem().toString()), true);	
			}
		});
		sfNhom.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				ListBinder.bindSelectField(sfMaDonVi, FVGenericInfo.getDept(sfXuong.getSelectedItem().toString(), sfNhom.getSelectedItem().toString()), false);
				ListBinder.bindSelectField(sfDonVi, FVGenericInfo.getDeptName(sfXuong.getSelectedItem().toString(), sfNhom.getSelectedItem().toString()), true);
			}
		});
		//Button
		btnExport.addActionListener(new ActionListener() {
			
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				doExport();				
			}
		});
		btnReset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doReset();
			}
		});
	}

	public void doReset() {
		// TODO Auto-generated method stub
		sfDonVi.setSelectedIndex(-1);
		sfMaDonVi.setSelectedIndex(-1);
		sfNhom.setSelectedIndex(-1);
		sfXuong.setSelectedIndex(-1);	
		demLDGiam =0;
		demDCGiam =0;
		demBSGiam =0;
		demLDTang =0;
		demDCTang =0;
		demBSTang =0;
		thangHienTai = null;
		lblBaoGiamTu.setText(null);
		lblBaoGiamDen.setText(null);
		checkBox1.setSelected(false);
	}

	/**
	 * Month 1-base
	 * @return 1-12
	 */
	public int getMonth(){
		return Integer.parseInt(sfThang.getSelectedItem().toString());
	}
	
	public int getYear(){
		return Integer.parseInt(sfNam.getSelectedItem().toString());
	}

	private void ngayBaoGiamChanged(){
		Calendar ngayBaoGiam = dfNgayBaoGiam.getSelectedDate();		
		if (ngayBaoGiam.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY && ngayBaoGiam.get(Calendar.DAY_OF_WEEK)!=Calendar.THURSDAY){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Chỉ được chọn thứ 2 hoặc thứ 5");
			return;
		}				
		String[] thoiGian = DateUtils.getThoiGianGiamTuan(dfNgayBaoGiam.getSelectedDate());
		lblBaoGiamTu.setText(thoiGian[0]);
		lblBaoGiamDen.setText(thoiGian[1]);		
		dfNgayBaoCao.setSelectedDate(ngayBaoGiam);		
	}

	protected void doExport() {
		// lay thong tin thang hien hanh
		// moi lan goi cua lenh Export deu load lai gia tri khoi tao ban dau
		GiaTriKhoiTao();
		int month=0, year=0;		
		Calendar ngayBaoGiam = dfNgayBaoGiam.getSelectedDate();
		Calendar cal = Calendar.getInstance();
		cal.set(ngayBaoGiam.get(Calendar.YEAR), ngayBaoGiam.get(Calendar.MONTH), 20);
		if (ngayBaoGiam.compareTo(cal)>0){
			cal.add(Calendar.MONTH, 1);
		}	
		
		month = cal.get(Calendar.MONTH);//Calendar Month 0->11, trong class InsuranceDAO tu 1-12
		year = cal.get(Calendar.YEAR);
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");		
		//System.out.println("Thang: "+month); // VD month luc nay la 2 va calendar se chay tu 0->11, 2 la Thang 3
		
		cal.set(year, month, 1);
		thangHienTai = sf.format(cal.getTime());// luc nay gia tri tra ve la 01/03/2013
		//System.out.println("Thoi gian hien tai: "+thangHienTai);
		
		StopWatch sw = new StopWatch();
		sw.reset();
		sw.start();
		if (radTang1Tay.isSelected()){
			try {
				doExportTang1Tay();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}else if (radCapSo1Tay.isSelected()){
			try {
				doExportCapSoMoi();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}else if (radTang20Tay.isSelected()){
			doExportTang20Tay();
		} else if (radTang15Tay.isSelected()){
			
		} else if (radGiamTuan.isSelected()){
			try {
				doExportGiamTuanNew();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else if (radGiam20Tay.isSelected()){
			try {
				doExportGiam20Tay();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		} else if (radGiamTrongTangMoi.isSelected()){
			try {
				doExportGiamTangMoi();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		} else if (radDieuChinhLuong.isSelected()){
			try {
				doExportDCLuong20Tay();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		} else if (radDieuChinhHoSo.isSelected()){
			try {
				doExportThayDoiTTBHQuy();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		} else if (radNghiViecCoTraThe.isSelected()){
			
		}
		sw.stop();
		System.out.println("Run in " + (float)sw.getTime()/1000 + " s");
	}

	private void doExportThayDoiTTBHQuy() {
		// TODO Auto-generated method stub
		int month = getMonth();
		int year = getYear();
		Calendar ng = dfNgayBaoCao.getSelectedDate();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(ng.getTimeInMillis());
		String dkFact = insDAO.getFactCondition(sfMaDonVi, sfXuong, sfNhom, sfDonVi);
		List<Map<String, Object>> list = insDAO.getThayDoiTTBHList(dkFact, month, year);		
		Date thangBC = DateUtils.getFirstDay(month, year);
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(ng.getTimeInMillis());
		c.set(Calendar.DAY_OF_MONTH, 1);
		if (ng.get(Calendar.DAY_OF_MONTH)>20){
			c.add(Calendar.MONTH, 1);		
		}
		
		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance", "Mau D07-TS.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
	
		
		int rowToCopy=7;
		int startRow = 8;		
		int stt=1;

		int incRows=list.size()-1;		
		if (incRows>0){
			sheet.shiftRows(startRow, sheet.getLastRowNum(), incRows, false, true);
			startRow+=incRows;
			rowToCopy+=incRows;			
		}

		rowToCopy 	= 7;
		startRow	= 8;
		
		for (int i=startRow;i<startRow+incRows;i++){
			HSSFUtils.copyRow(wb, sheet, rowToCopy++, i);
		}		
		
		rowToCopy 	= 7;
		startRow	= 8;				
		for (int i=0;i<list.size();i++){
			//fill increase
			row = sheet.getRow(rowToCopy);
			Map<String, Object> data = list.get(i);
			String emp = (String) data.get("EMPSN");
			cell = row.getCell(0);//STT
			HSSFUtils.fill(cell, i+1);
			cell = row.getCell(1);//Ho va ten
			HSSFUtils.fill(cell, data.get("FNAME")+" "+data.get("LNAME"));
			cell = row.getCell(2);//Ma so BHXH
			HSSFUtils.fill(cell, data.get("ID_SOCIAL"));
			cell = row.getCell(3);//Noi dung dieu chinh
			HSSFUtils.fill(cell, "Đ/c thông tin");
			cell = row.getCell(4);//Thong tin old			
			HSSFUtils.fill(cell, data.get("TT_OLD"));
			cell = row.getCell(5);//Thong tin new
			HSSFUtils.fill(cell, data.get("TT_NEW"));
			cell = row.getCell(6);//Thang thay doi
			HSSFUtils.fill(cell, data.get("MONTH_BC"));
						
			cell = row.createCell(9);//Xuong+ don vi
			HSSFUtils.fill(cell, data.get("NAME_DEPT"));			
			cell = row.createCell(10);//So the
			HSSFUtils.fill(cell, emp);			
			cell = row.createCell(11);//Thang thay doi
			HSSFUtils.fill(cell, data.get("MONTH_BC"));
			
			rowToCopy++;			
		}
		try {
			ReportUtils.doExportExcel(wb, "ThayDoiTTBH");
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
		String dkFact = insDAO.getFactCondition(sfMaDonVi, sfXuong, sfNhom, sfDonVi);
		List<Map<String, Object>> list = insDAO.getGiamTangMoiList(dkFact, month, year);
		Map<String, BigDecimal> thamso = insDAO.getThamSo();
		Date thangBC = DateUtils.getFirstDay(month, year);
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(ng.getTimeInMillis());
		c.set(Calendar.DAY_OF_MONTH, 1);
		if (ng.get(Calendar.DAY_OF_MONTH)>20){
			c.add(Calendar.MONTH, 1);		
		}
		
		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance", "Mau_D02-TS_cong_thuc_GiamTuan.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		
		//fill header
		row = sheet.getRow(2);
		cell = row.getCell(24);
		cell.setCellValue(c);
		
		int rowToCopy=21;
		int startRow = 22;		
		int stt=1;

		int incRows=list.size()-1;		
		if (incRows>0){
			sheet.shiftRows(startRow, sheet.getLastRowNum(), incRows, false, true);
			startRow+=incRows;
			rowToCopy+=incRows;			
		}

		rowToCopy 	= 21;
		startRow	= 22;
		
		for (int i=startRow;i<startRow+incRows;i++){
			HSSFUtils.copyRow(wb, sheet, rowToCopy++, i);
		}		
		
		rowToCopy 	= 21;
		startRow	= 22;
		int luongCB;
		
		for (int i=0;i<list.size();i++){
			//fill increase
			row = sheet.getRow(rowToCopy);
			Map<String, Object> data = list.get(i);
			String emp = (String) data.get("EMPSN");
			cell = row.getCell(0);//STT
			HSSFUtils.fill(cell, i+1);
			cell = row.getCell(1);//Ho va ten
			HSSFUtils.fill(cell, data.get("FNAME")+" "+data.get("LNAME"));
			cell = row.getCell(2);//Ma so BHXH
			HSSFUtils.fill(cell, data.get("ID_SOCIAL"));
			cell = row.getCell(3);//Ngay sinh
			HSSFUtils.fill(cell, data.get("BIRTHDAY"));
			cell = row.getCell(4);//Gioi tinh
			if ("NU".equals(data.get("SEX")))
				HSSFUtils.fill(cell, "X");
			else
				HSSFUtils.fill(cell, "");
			cell = row.getCell(5);//Chuc vu
			HSSFUtils.fill(cell, data.get("POSITION"));
			
			int dc_TangMoi		= Integer.parseInt(insDAO.GetField("count(*)", "n_dcluong_dongloat", "to_char(ngaydc,'"+"dd/mm/yyyy"+"')", "baodc_tangmoi", "", sdf.format(thangBC), "Y", ""));
			int sal_dcGiam		= 0;		
			if (dc_TangMoi==1){
				sal_dcGiam=Integer.parseInt(insDAO.GetField("sotienthaydoi", "n_dcluong_dongloat", "to_char(ngaydc,'"+"dd/mm/yyyy"+"')", "baodc_tangmoi", "", sdf.format(thangBC), "Y", ""));				
			}
			else{
				sal_dcGiam=0;
			}
			
			luongCB = Integer.parseInt(data.get("BSALARY").toString())-sal_dcGiam;
			
			cell = row.getCell(6);//Tien luong			
			HSSFUtils.fill(cell, luongCB);
			
			cell = row.getCell(16);//Giam tu thang
			HSSFUtils.fill(cell, data.get("MONTH_BC"));
			cell = row.getCell(20);//Ghi chu
			HSSFUtils.fill(cell, data.get("NOTE_TANG"));
			cell = row.getCell(22);//Ti le BHXH
			HSSFUtils.fill(cell, thamso.get("TYLEBHXH"));
			cell = row.getCell(23);//Ti le BHYT
			HSSFUtils.fill(cell, thamso.get("TYLEBHYT"));
			cell = row.getCell(24);//Ti le BHTN
			HSSFUtils.fill(cell, thamso.get("TYLEBHTN"));
			cell = row.createCell(48);//So the
			HSSFUtils.fill(cell, emp);
			cell = row.createCell(49);//Xuong
			HSSFUtils.fill(cell, data.get("NAME_GROUP"));
			cell = row.createCell(50);//Don vi
			HSSFUtils.fill(cell, data.get("NAME_DEPT_NAME"));
			//eval formula
			cell = row.getCell(18);
			HSSFUtils.evalFormula(wb, cell);
			for (int k=25;k<48;k++){
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

	private int Export_TangDC1ThangOrBS(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, HSSFCell cell, BieuMau3A data, int demTang1,int demDC1, int demTangBSBHYT, int demLDGiam, int demThoaiThuBSBHYT, int dongChuan, int sal1, int sal2, String tuThang, String denThang, String noteBS){
		int demNew = 0;
		int rowTangOrDCorBS = 0;// la rowindex cua tang 1 thang/ DC 1 thang/ Tang BS BHYT
		// gan gia tri truoc khi tang bien dem
		int rowStart = dongChuan+1;
		int rowCopy= dongChuan;
		
		HSSFRow templateRow = sheet.getRow(10);
		// them 1 dong tang 1 thang or dc 1 thang khi bao giam CNV
		int demTangOrDCorBS = 0; 
		// tang so luong record cho tung truong hop
		
		//Tang, DC, Tang BSBHYT deu tren ld Giam nen ko can thay doi rowStart va Rowcopy
		// vi khi truyen vao DA LAY DONG CHUAN + SO BIEN DEM
		if (noteBS.compareTo("Taêng 1 Thaùng")==0){
			demTangOrDCorBS = demTang1+1;			
			rowTangOrDCorBS = dongChuan+demTangOrDCorBS;
		}
		else if (noteBS.compareTo("Ñ/C Löông")==0){
			demTangOrDCorBS = demDC1+1;
			rowTangOrDCorBS = dongChuan+demTangOrDCorBS+demTang1;
		}
		else if (noteBS.compareTo("Taêng BSBHYT")==0){
			demTangOrDCorBS = demTangBSBHYT+1;
			rowTangOrDCorBS = dongChuan+demTangOrDCorBS+demTang1+demDC1;
		}	
		else if (noteBS.compareTo("Thoaùi Thu BSBHYT")==0){			
			demTangOrDCorBS = demThoaiThuBSBHYT+1;			
			//rowTangOrDCorBS = dongChuan+demTangOrDCorBS+demTang1+demDC1+demTangBSBHYT;// ok
			// ko can cong dem LD giam o dya nua vi minh da cong toan bo so ldgiam vao dong chuan roi
			rowTangOrDCorBS = dongChuan+demTangOrDCorBS+demTang1+demDC1+demTangBSBHYT;			
			rowCopy= rowTangOrDCorBS;
			dongChuan= rowCopy;
			rowStart= rowCopy+1;
			
		}		
		
/*		// copy va insert them 1 row moi tren file mau tai vi tri dongNew
		sheet.shiftRows(rowTangOrDCorBS, sheet.getLastRowNum(), 1, true, false);
		row = sheet.createRow(rowTangOrDCorBS);
		row = sheet.getRow(rowTangOrDCorBS);		

		//row =sheet.createRow(r); // cai nay la copy de len dong hien tai dang co tren file mau
		for(int j=0;j<templateRow.getPhysicalNumberOfCells();j++){
			copyCell(templateRow.getCell(j),row.createCell(j));
		}		
		*/			
		
		// moi lan dk hop le se them 1 row trong file excel,thay vi la demTangOrDCorBS
		
		if (demTangOrDCorBS>0){			
			sheet.shiftRows(rowStart, sheet.getLastRowNum(), 1, false, true);
		}
		
		for (int i=rowStart;i<rowStart+1;i++){
			HSSFUtils.copyRow(wb, sheet, rowCopy++, i);
		}		
				
		row = sheet.getRow(dongChuan);
		
		// gan data		
		cell = row.getCell(0);
		cell.setCellValue(demTangOrDCorBS);
		cell = row.getCell(1);
		cell.setCellValue(data.getHoTen());
		if (data.getMaSoBHXH()!=null){
			cell = row.getCell(2);
			cell.setCellValue(data.getMaSoBHXH());	
		}
		cell = row.getCell(6); // muc luong old
		cell.setCellValue(sal1);
		cell = row.getCell(11); // muc luong moi
		cell.setCellValue(sal2);
		
		cell = row.getCell(16);	//tuthang
		cell.setCellValue(insDAO.ToDate(tuThang));
		cell = row.getCell(17); //denthang
		cell.setCellValue(insDAO.ToDate(denThang));
		cell = row.getCell(20);
		cell.setCellValue(noteBS);
		cell = row.getCell(21);
		cell.setCellValue(data.getEmpsn());
		
		// if la Xet Tang BS BHYT, chi tang % cua BHYT ma thoi
		if (noteBS.compareTo("Taêng BSBHYT")==0 ||noteBS.compareTo("Thoaùi Thu BSBHYT")==0){
			cell = row.getCell(22);
			cell.setCellValue(0);		
			cell = row.getCell(23);
			cell.setCellValue(tyleBHYT);
			cell = row.getCell(24);
			cell.setCellValue(0);			
		}
		else{
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
		if (data.getDonVi()!=null){
			cell = row.createCell(50);
			cell.setCellValue(data.getDonVi());	
		}		
		// gan cong thuc
		for (int k=25;k<48;k++){
			cell = row.getCell(k);
			HSSFUtils.evalFormula(wb, cell);
		}
		
		demNew = demTangOrDCorBS;
		return demNew;
	}
	
	private String[] GetTTBaoGiam_TangMoiBangThangGiam(int sal_Giam, int sal_dc, int dkMuaBH, Date ngayChayBC, Date ngayQT20, Date thoiGian ) {
		//thoigian dd/mm/yyyy la thang muon so sanh
		int sal_BSBHYT			= 0;	
		int sal_BaoGiam			= 0;
		String tangBSBHYT 		= null;
		String thoaiThuBSBHYT	= null;
		String tang1Thang		= null;
		String dc1Thang			= null;
		String tgianTangOrDC	= null;
		String noteGiam			= null;
		String noteLuongGiam	= null;
		//QT20 cua thang giam van chua goi di
		if (ngayChayBC.compareTo(ngayQT20)<0){
			sal_BSBHYT= sal_Giam-sal_dc;
			if (sal_dc>0) {
				noteLuongGiam="Thang tangmoi="+thoiGian+", co DC dongloat tre, chua QT20 ThangGiam, luong= luong thang "+thoiGian+
					" tru DC dong loat "+String.valueOf(sal_dc);
			}
			else{
				noteLuongGiam="Thang tangmoi="+thoiGian+", ko co DC dongloat tre, chua QT20 ThangGiam "+
					", luong= luong thang "+thoiGian;
			}
		}
		else{
			sal_BSBHYT = sal_Giam;
			noteLuongGiam="Thang tangmoi="+thoiGian+", da QT20 ThangGiam , luong= luong thang "+thoiGian;
		}
		
		sal_BaoGiam	= sal_BSBHYT;
		noteGiam	= "Nghỉ việc";
		tangBSBHYT	= "1";
		thoaiThuBSBHYT ="0";
		tang1Thang	= "0";
		dc1Thang	= "0";
		tgianTangOrDC = null;
		return new String[]{String.valueOf(sal_BSBHYT), String.valueOf(sal_BaoGiam), noteGiam, noteLuongGiam, tangBSBHYT, thoaiThuBSBHYT, tang1Thang, dc1Thang, tgianTangOrDC};
	}
	
	private String[] GetThongTinBaoGiam(String soThe, String thangBaoGiam, String ngayTraTheBHYT) {
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
		int dkbh_Giam1	= 0;
		int dkbh_Giam2	= 0;
		int sal_Giam	= 0;
		int sal_Giam1	= 0;
		int sal_Giam2	= 0;
		int sal_BaoGiam	= 0;
		
		// thong tin bao giam
		int sal_BSBHYT 			= 0;
		int sal_TangOrDC		= 0;
		String noteGiam			= null;
		String noteLuongGiam 	= null;
		String tangBSBHYT		= "0";
		String thoaiThuBSBHYT	= "0";
		String tang1Thang		= "0";
		String dc1Thang			= "0";
		String tgianTang_DC		= null;
		String thangGanNhatCoBH	= null;
		String luongGanNhatCoBH	= "0";
		Calendar ngayBaoGiam= dfNgayBaoGiam.getSelectedDate();
		
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
		
		thangGiam	= insDAO.ToDate(thangBaoGiam);
		dkbh_Giam	= insDAO.getThamGiaBHXHTN2120(soThe, sdf.format(thangGiam));
		sal_Giam	= insDAO.getLuongTheoThang(soThe, sdf.format(thangGiam));
		
		Calendar thangGiamTemp = Calendar.getInstance();
		thangGiamTemp.setTime(thangGiam);
		thangGiamTemp.add(thangGiamTemp.MONTH,1);
		thangGiamCong1 = thangGiamTemp.getTime();
		
		thangGiamTemp.setTime(thangGiam);
		thangGiamTemp.add(thangGiamTemp.MONTH, -1);		
		thangGiamTru1 = thangGiamTemp.getTime();
		dkbh_Giam1	= insDAO.getThamGiaBHXHTN2120(soThe, sdf.format(thangGiamTru1));
		sal_Giam1	= insDAO.getLuongTheoThang(soThe, sdf.format(thangGiamTru1));
				
		thangGiamTemp.add(thangGiamTemp.MONTH, -1);
		thangGiamTru2 = thangGiamTemp.getTime();
		dkbh_Giam2	= insDAO.getThamGiaBHXHTN2120(soThe, sdf.format(thangGiamTru2));
		sal_Giam2	= insDAO.getLuongTheoThang(soThe, sdf.format(thangGiamTru2));		
		
		thangBCGoc 	= insDAO.ToDate(thangHienTai);
		//Date ngayChayBC		= insDAO.ToDate(sdf.format(dfNgayBaoCao.getDisplayedDate().getTime()));
		Date ngayChayBC		= insDAO.ToDate(sdf.format(dfNgayBaoCao.getSelectedDate().getTime()));
		Date ngay20_BCGoc 	= insDAO.GetNgayGioiHanBC2120(thangHienTai);
		Date ngay20_Giam	= insDAO.GetNgayGioiHanBC2120(sdf.format(thangGiam));
		Date ngay20_Giam1	= insDAO.GetNgayGioiHanBC2120(sdf.format(thangGiamTru1));
		Date ngay20_Giam2	= insDAO.GetNgayGioiHanBC2120(sdf.format(thangGiamTru2));
		//System.out.println(soThe+","+ sdf.format(thangGiam)+" "+dkbh_Giam+", "+sdf.format(thangGiamTru1)+" "+dkbh_Giam1+", "+sdf.format(thangGiamTru2)+" "+dkbh_Giam2);
		
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
		
		boolean nsRoRa_CuoiQuy = false;
		boolean	roVaoTuNSRoRa  = false;
		if (nsVao!=null){			
			nsRoRa_CuoiQuy = insDAO.Check_NSRoRa_CuoiQuy(soThe, sdf.format(thangCuoiQuyTruoc));
			//RoVaoTuNSRoRa se duoc xet theo khoang thoi gian muon so sanh voi thang nsvao
		}	
		
		// bat dau xet toi bao giam tuan, dang xet den day 01/06/2013
		String[] data1;
		if (ngayDiLamLai==null || (ngayDiLamLai!=null && thangTangLai.compareTo(thangGiamTru1)<0)){
			if (nsVao==null){
				//tang moi trong thang giam
				if(lanKyHD==1 && (ngayKyHD.compareTo(ngay01_Giam)==0 || ngayKyHD.compareTo(ngay15_Giam)==0)){					
					data1= GetTTBaoGiam_TangMoiBangThangGiam(sal_Giam, sal_dcGiam, dkbh_Giam, ngayChayBC, ngay20_Giam, thangGiam);
					sal_BSBHYT 	= Integer.parseInt(data1[0]);
					sal_BaoGiam	= Integer.parseInt(data1[1]);
					noteGiam	= data1[2];
					noteLuongGiam= data1[3];
					tangBSBHYT	= data1[4];
					thoaiThuBSBHYT= data1[5];
					tang1Thang	= data1[6];//=0
					dc1Thang	= data1[7];//=0
					tgianTang_DC= data1[8];//null
					//vi ko co thon tin tang or dc 1 thang gi ca nen sal_tangOrdc
					sal_TangOrDC= 0;
				}
				
				//tang moi trong thang giam -1
				else if(lanKyHD==1 && (ngayKyHD.compareTo(ngay01_Giam1)==0 || ngayKyHD.compareTo(ngay15_Giam1)==0)){
					
					//QT20 cua thang giam-1 van chua goi di
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
							tgianTang_DC	= sdf.format(thangGiamTru1);
							sal_TangOrDC	= sal_Giam1;
						}
					}
					// da QT20 thang giam -1, chua QT20 thang giam
					else if(ngayChayBC.compareTo(ngay20_Giam1)>=0 && ngayChayBC.compareTo(ngay20_Giam)<0){
						sal_BSBHYT = sal_Giam1;
						noteLuongGiam = "Thang tangmoi=Thanggiam-1, Da QT20 ThangGiam-1, chua QT20 ThangGiam, luong= luong thang "+sdf.format(thangGiamTru1);
					}
					//  da QT20 thang giam-1 va thang giam
					else{
						//lay luong cua thang gan nhat co tham gia bao hiem 
						sal_BSBHYT = sal_Giam;
						noteLuongGiam="Thang tangmoi="+thangBaoGiam+", da QT20 ThangGiam , luong= luong thang "+thangBaoGiam;
					}
					
					sal_BaoGiam	= sal_BSBHYT;
					noteGiam	= "Nviệc";
					tangBSBHYT	= "1";
					thoaiThuBSBHYT ="0";
				}
				// tang moi < thang giam -1
				else{
					//chua QT20 thang giam -1
				  	if (ngayChayBC.compareTo(ngay20_Giam1)<0){
				  		data1 = insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru2),sdf.format(ngayBaoGiam.getTime()));
				  		thangGanNhatCoBH	= data1[0];
				  		luongGanNhatCoBH	= data1[1];
				  		noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiamTru2)+
				  				". Chua QT20 "+sdf.format(thangGiamTru2)+", luong = luong cua thang "+thangGanNhatCoBH;
				  		sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
				  		if(dkbh_Giam1==1 && sal_Giam1!=sal_BSBHYT){
				  			dc1Thang ="1";
				  			tang1Thang="0";
				  			tgianTang_DC = sdf.format(thangGiamTru1);
				  			sal_TangOrDC = sal_Giam1;
				  		}
				  	}
				  	//Da QT20 thang giam -1 , chua QT20 thang giam
				  	else if(ngayChayBC.compareTo(ngay20_Giam1)>=0 && ngayChayBC.compareTo(ngay20_Giam)<0) {
				  		data1 = insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru1),sdf.format(ngayBaoGiam.getTime()));
				  		thangGanNhatCoBH	= data1[0];
				  		luongGanNhatCoBH	= data1[1];
				  		noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiamTru1)+
				  				". Chua QT20 "+sdf.format(thangGiamTru1)+", luong = luong cua thang "+thangGanNhatCoBH;
				  		sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);				  		
				  	}
				  	//Da Qt20 thang giam
				  	else {
				  		data1 = insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiam),sdf.format(ngayBaoGiam.getTime()));
				  		thangGanNhatCoBH	= data1[0];
				  		luongGanNhatCoBH	= data1[1];
				  		noteLuongGiam		= "Luong giam = luong gan nhat co TGia BH, tinh tu "+sdf.format(thangGiam)+
				  				". Chua QT20 "+sdf.format(thangGiam)+", luong = luong cua thang "+thangGanNhatCoBH;
				  		sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);				  					  		
				  	}				  	
					sal_BaoGiam	= sal_BSBHYT;
					noteGiam	= "Nviệc";
					tangBSBHYT	= "1";
					thoaiThuBSBHYT ="0";				  	
				}
			}
			// co thong tin nghi san
			else{
				//if ThangNSVao = thang bao giam
				if(nsVao.compareTo(thangGiam)==0){
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
						tgianTang_DC		= null;
						sal_TangOrDC		=0;
					}
					else{
				        //Da QT roi ma lai giam = thang NS vao nen chac chan thang NSVao co trang thai la NS->RoRa
				        //Neu thang giam = Thang cuoi quy va co bao gia han the cho quy moi roi thi phai thoai thu tro lai
						// vi vay ko can kiem tra roVaoTuNSRoRa nua						
						noteGiam	= "Ro->Nviệc";
						data1		= insDAO.TTThamGiaBHGanNhat(soThe, thangBaoGiam,sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH	= data1[0];
						luongGanNhatCoBH	= data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
						sal_BaoGiam			= 0;
						noteLuongGiam		= "NSan->RoRa->NViec nen luong bao giam =0";
						if (nsRoRa_CuoiQuy) {
							tangBSBHYT	= "0";
							thoaiThuBSBHYT	= "1";
						}
						else{
							tangBSBHYT	="1";
							thoaiThuBSBHYT	= "0";
						}	
					}
				}
				//If NSVao= thang giam -1 
				else if(nsVao.compareTo(thangGiamTru1)==0){
					//nsVao= thang giam -1 thi chua chac thang nsVao du cong, co kha nang la NSVao/ NS-->RoRa
					// if chua QT thang giam -1 thi cho du thnag giam -1 la thang cuoi quy-1 cung ko sao, vi luc nay chua chay gia han the
					// vi vay ko can quan tam nsRoRa_CuoiQuy va roVaoTuNSRoRa					
					//if chua QT thang giam -1
					if (ngayChayBC.compareTo(ngay20_Giam1)<0){						
						noteGiam	= "Nsản->Nviệc";
						data1		= insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru2),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH = data1[0];
						luongGanNhatCoBH = data1[1];
						sal_BaoGiam		 = 0;
						noteLuongGiam		= "Nsan->NViec nen luong giam =0";
						
						tangBSBHYT		= "1";
						thoaiThuBSBHYT	= "0";
						
						if(dkbh_Giam1==1){
							tang1Thang 	= "1";
							dc1Thang	= "0";
							tgianTang_DC= sdf.format(thangGiamTru1); 
							sal_TangOrDC=sal_Giam1;
						}						
					}
					// da QT thang giam -1 nhung chua QT thang giam
					// NSVao =thang giam -1, va da bao len BH la NSVao/ NS-->RoRa
					// vi vay phai ktra nsRoRa_CuoiQuy va roVaoTuNSRoRa
					else if (ngayChayBC.compareTo(ngay20_Giam1)>0 && ngayChayBC.compareTo(ngay20_Giam)<0){
						data1		= insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru1),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH 	= data1[0];
						luongGanNhatCoBH	= data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);

						// NSVao= thangGiam-1 va du cong
						// vi vay ko quan tam nsRoRa_CuoiQuy va roVaoTuNSRoRa						
						if (dkbh_Giam1==1){
							noteGiam 	= "Nviệc";
							sal_BaoGiam			= sal_BSBHYT;
							tangBSBHYT			= "1";
							thoaiThuBSBHYT		= "0";							
						}
						// NVao= thang giam -1 nhung NS-->Rora, bi gio lai nghi viec, chac chan la chua RoVao
						// vi vay ko quan tam roVaoTuNSRoRa 
						else{							
							noteGiam	= "Ro->Nviệc";
							sal_BaoGiam			= 0;
							noteLuongGiam		= "NS->RoRa->NViec nen luong bao giam = 0";							
							// if la NS-RoRa thang cuoi quy -1 thi da tang BS BHYT cho quy moi luc gia han the 
							if(nsRoRa_CuoiQuy){
								tangBSBHYT			= "0";
								thoaiThuBSBHYT		= "1";								
							}
							else{
								tangBSBHYT			= "1";
								thoaiThuBSBHYT		= "0";							
							}							
						}
					}
					// da QT 20 thang giam roi
					else{
						// vi da QT thang giam roi nen se xet RoVao ke tu thang giam, vi vay thong so truyen vao se la thangGiam+1
						roVaoTuNSRoRa	= insDAO.Check_RoVaoTuNSRoRa(soThe, sdf.format(nsVao), sdf.format(thangGiamCong1), sdf.format(ngayChayBC));
						data1		= insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiam),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH 	= data1[0];
						luongGanNhatCoBH	= data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
						
						if(nsRoRa_CuoiQuy){							
							if (roVaoTuNSRoRa){
								noteGiam 	= "Nviệc";
								sal_BaoGiam			= sal_BSBHYT;
								tangBSBHYT			= "1";
								thoaiThuBSBHYT		= "0";
							}
							else{
								noteGiam 	= "Ro->Nviệc";
								noteLuongGiam		= "NS->RoRa->NViec nen luong bao giam = 0";	
								sal_BaoGiam			= 0;
								tangBSBHYT			= "0";
								thoaiThuBSBHYT		= "1";	
							}								
						}
						//ko phai la NS->RoRa cuoi quy truoc -1
						// but co the van chua co RoVao ke tu thoi diem NS-RoRa
						else{							
							if (roVaoTuNSRoRa){
								noteGiam 	= "Nviệc";
								sal_BaoGiam			= sal_BSBHYT;
							}
							else{
								noteGiam 	= "Ro->Nviệc";
								noteLuongGiam		= "NS->RoRa->NViec nen luong bao giam = 0";	
								sal_BaoGiam			= 0;
							}
							tangBSBHYT			= "1";
							thoaiThuBSBHYT		= "0";							
						}
						
						tang1Thang	= "0";
						dc1Thang	= "0";
						tgianTang_DC=null;	
						sal_TangOrDC=0;
						
					}// het QT20 thang giam
				}// end cua NSVao= thang giam -1
				
				//if NSVao < thang giam -1
				else if (nsVao.compareTo(thangGiamTru1)<0){
					// if chua QT20 thang giam -1
					if (ngayChayBC.compareTo(ngay20_Giam1)<0){
						// vi chua QT thang giam-1 nen se xet RoVao ke tu thang giam-2
				        // vi dk so sanh la >thang NSVao and < Thang so sanh 
						// vi vay thoi gian truyen vao la thang giam -1
						roVaoTuNSRoRa	= insDAO.Check_RoVaoTuNSRoRa(soThe, sdf.format(nsVao), sdf.format(thangGiamTru1), sdf.format(ngayChayBC));
						data1		= insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru2),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH 	= data1[0];
						luongGanNhatCoBH	= data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
						
						if (nsRoRa_CuoiQuy){							
							if(roVaoTuNSRoRa){
								noteGiam	= "Nviệc";
								sal_BaoGiam			= sal_BSBHYT;
								noteLuongGiam		= "Nviec , luong giam = luong gan nhat co BH, " + thangGanNhatCoBH;
								tangBSBHYT			= "1";
								thoaiThuBSBHYT		= "0";
								if(dkbh_Giam1==1 && sal_Giam1!= sal_BSBHYT){
									tang1Thang	= "0";
									dc1Thang	= "1";
									tgianTang_DC= sdf.format(thangGiamTru1);
									sal_TangOrDC=sal_Giam1;
								}								
							}
							else{
								noteGiam		= "Ro->Nviệc";								
								sal_BaoGiam			= 0;
								noteLuongGiam		= "NS->RoRa->NViec nen luong bao giam = 0";
								tangBSBHYT			= "0";
								thoaiThuBSBHYT		= "1";
								if(dkbh_Giam1==1){
									tang1Thang	= "1";
									dc1Thang	= "0";
									tgianTang_DC= sdf.format(thangGiamTru1);
									sal_TangOrDC=sal_Giam1;
								}								
							}
						}// end cua NS->RoRa cuoi quy truoc -1
						// if ko phai NS->RoRa cuoi quy truoc -1 but van co the la chua RoVao ke tu khi NS-RoRa
						else{							
							if(roVaoTuNSRoRa){
								noteGiam	= "Nviệc";
								sal_BaoGiam			= sal_BSBHYT;
								noteLuongGiam		= "Nviec , luong giam = luong gan nhat co BH, " + thangGanNhatCoBH;
								tangBSBHYT			= "1";
								thoaiThuBSBHYT		= "0";
								if(dkbh_Giam1==1 && sal_Giam1!= sal_BSBHYT){
									tang1Thang	= "0";
									dc1Thang	= "1";
									tgianTang_DC= sdf.format(thangGiamTru1);
									sal_TangOrDC=sal_Giam1;
								}
							}
							else{
								noteGiam		= "Ro->Nviệc";								
								sal_BaoGiam			= 0;
								noteLuongGiam		= "NS->RoRa->NViec nen luong bao giam = 0";
								tangBSBHYT			= "1";
								thoaiThuBSBHYT		= "0";
								if(dkbh_Giam1==1){
									tang1Thang	= "1";
									dc1Thang	= "0";
									tgianTang_DC= sdf.format(thangGiamTru1);
									sal_TangOrDC=sal_Giam1;
								}								
							}
						}// end cua else NS->RoRa cuoi quy truoc -1
					}// end cua chua QT20 thang giam -1
					//da QT 20 thang giam -1, chua QT20 thang giam
					else if(ngayChayBC.compareTo(ngay20_Giam1)>0 && ngayChayBC.compareTo(ngay20_Giam)<0){
						
				        // vi chua QT thang giam nen se xet RoVao ke tu thang giam-1
				        // vi dk so sanh la >thang NSVao and < Thang so sanh , vi vay thoi gian truyen vao la thang giam
				        roVaoTuNSRoRa	= insDAO.Check_RoVaoTuNSRoRa(soThe, sdf.format(nsVao), sdf.format(thangGiam), sdf.format(ngayChayBC));
						data1			= insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiamTru1),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH 	= data1[0];
						luongGanNhatCoBH	= data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
						tang1Thang		="0";
						dc1Thang		="0";
						tgianTang_DC	=null;
						sal_TangOrDC	=0;
						
				        if(nsRoRa_CuoiQuy){
				        	if(roVaoTuNSRoRa){
				        		noteGiam		=" Nviệc";				        		
								sal_BaoGiam			= sal_BSBHYT;
								noteLuongGiam		= "Nviec , luong giam = luong gan nhat co BH, " + thangGanNhatCoBH;
								tangBSBHYT			= "1";
								thoaiThuBSBHYT		= "0";
				        	}
				        	else{
								noteGiam		= "Ro->Nviệc";								
								sal_BaoGiam			= 0;
								noteLuongGiam		= "NS->RoRa->NViec nen luong bao giam = 0";
								tangBSBHYT			= "0";
								thoaiThuBSBHYT		= "1";
				        	}
				        }
				        else{
							tangBSBHYT			= "1";
							thoaiThuBSBHYT		= "0";				        	
				        	if (roVaoTuNSRoRa){ 
								noteGiam		= "Nviệc";
								sal_BaoGiam			= sal_BSBHYT;
								noteLuongGiam		= "Nviec , luong giam = luong gan nhat co BH, " + thangGanNhatCoBH;
				        	}
				        	else{
								noteGiam		= "Ro->Nviệc";								
								sal_BaoGiam			= 0;
								noteLuongGiam		= "NS->RoRa->NViec nen luong bao giam = 0";
				        	}
				        }						
					}// end cua QT 20 thang giam -1, chua QT20 thang giam
					// da QT20 thang giam
					else {						
				        // vi QT thang giam nen se xet RoVao ke tu thang giam
				        // vi dk so sanh la >thang NSVao and < Thang so sanh , vi vay thoi gian truyen vao la thang giam+1
						// thang giam+1 = thang cua ngay20 giam						
						roVaoTuNSRoRa			= insDAO.Check_RoVaoTuNSRoRa(soThe, sdf.format(nsVao), sdf.format(thangGiamCong1), sdf.format(ngayChayBC));
						data1			= insDAO.TTThamGiaBHGanNhat(soThe, sdf.format(thangGiam),sdf.format(ngayBaoGiam.getTime()));
						thangGanNhatCoBH 	= data1[0];
						luongGanNhatCoBH	= data1[1];
						sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
						tang1Thang		="0";
						dc1Thang		="0";
						tgianTang_DC	=null;	
						sal_TangOrDC	=0;
						
						if(nsRoRa_CuoiQuy){
							if (roVaoTuNSRoRa){
								noteGiam		= "Nviệc";
								sal_BaoGiam			= sal_BSBHYT;
								noteLuongGiam		= "Nviec , luong giam = luong gan nhat co BH, " + thangGanNhatCoBH;
								tangBSBHYT			= "1";
								thoaiThuBSBHYT		= "0";
							}
							else{
								noteGiam		= "Ro->Nviệc";								
								sal_BaoGiam			= 0;
								noteLuongGiam		= "NS->RoRa->NViec nen luong bao giam = 0";
								tangBSBHYT			= "0";
								thoaiThuBSBHYT		= "1";								
							}
						}
						else{
							tangBSBHYT			= "1";
							thoaiThuBSBHYT		= "0";								
							
							if (roVaoTuNSRoRa){
								noteGiam		= "Nviệc";
								sal_BaoGiam			= sal_BSBHYT;
								noteLuongGiam		= "Nviec , luong giam = luong gan nhat co BH, " + thangGanNhatCoBH;
							}
							else{
								noteGiam		= "Ro->Nviệc";								
								sal_BaoGiam			= 0;
								noteLuongGiam		= "NS->RoRa->NViec nen luong bao giam = 0";
							}
						}
					}// end cua da QT20 thang giam
				}//end cua NSVao< thang giam -1
			}
		}	// het if (ngayDiLamLai==null || (ngayDiLamLai!=null && thangTangLai.compareTo(thangGiamTru1)<0))	
		else{
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
					tgianTang_DC= null;
					sal_TangOrDC= 0;	
				}
			}
			else if (thangTangLai.compareTo(thangGiamTru1)==0){
				if (ngayChayBC.compareTo(ngay20_Giam1)<0){
					if(thoiDiemTangLai==1){
						sal_BSBHYT = sal_Giam1 - sal_dcGiam1;					
					}
					else{
						sal_BSBHYT = sal_Giam1;
					}		
					sal_BaoGiam = sal_BSBHYT;					
					noteLuongGiam ="Bao giam = thang bao tang tro lai, luong giam =luong "+sdf.format(thangGiamTru1)+" ( tru DC_dongloat neu co)";
					if(dkbh_Giam1==1 && sal_BSBHYT!=sal_Giam1){
						tang1Thang	="0";
						dc1Thang	="1";
						tgianTang_DC=sdf.format(thangGiamTru1);
						sal_TangOrDC=sal_Giam1;
					}
					else{
						tang1Thang	="0";
						dc1Thang	="0";
						tgianTang_DC=null;
						sal_TangOrDC=0;
					}
				}
				else if(ngayChayBC.compareTo(ngay20_Giam1)>=0 && ngayChayBC.compareTo(ngay20_Giam)<0){
					sal_BSBHYT= sal_Giam1;
					sal_BaoGiam= sal_BSBHYT;				
					noteLuongGiam ="Bao giam = thang bao tang tro lai, luong giam =luong "+sdf.format(thangGiamTru1);
					tang1Thang	="0";
					dc1Thang	="0";
					tgianTang_DC=null;	
					sal_TangOrDC=0;
				}
				else if (ngayChayBC.compareTo(ngay20_Giam)>=0){
					data1	= insDAO.TTThamGiaBHGanNhat(soThe, thangBaoGiam,sdf.format(ngayBaoGiam.getTime()));
					thangGanNhatCoBH 	= data1[0];
					luongGanNhatCoBH	= data1[1];
					sal_BSBHYT			= Integer.parseInt(luongGanNhatCoBH);
					sal_BaoGiam			= sal_BSBHYT;
					tang1Thang	="0";
					dc1Thang	="0";
					tgianTang_DC=null;	
					sal_TangOrDC=0;
				}
				noteGiam		= "Nviệc";
				tangBSBHYT		= "1";
				thoaiThuBSBHYT	= "0";
			}
		}
		return new String[]{String.valueOf(sal_BSBHYT), String.valueOf(sal_BaoGiam), noteGiam, noteLuongGiam, tangBSBHYT, thoaiThuBSBHYT, tang1Thang, dc1Thang, tgianTang_DC, String.valueOf(sal_TangOrDC)};
	}
	
	private void ExportDSLDGiam(int r, int i, int tsLDGiam, HSSFWorkbook wb ,HSSFSheet sheet, HSSFRow row, HSSFCell cell, BieuMau3A data){
		
		//System.out.println(data.getEmpsn());
		
		cell = row.getCell(0);
		cell.setCellValue(i+1);		
		cell = row.getCell(1);		
		cell.setCellValue(data.getHoTen());
		
		if (data.getMaSoBHXH()!=null){
			cell = row.getCell(2);
			cell.setCellValue(data.getMaSoBHXH());			
		}
		cell = row.getCell(11); // muc luong moi
		cell.setCellValue(0);		
		
		cell = row.getCell(16);
		cell.setCellValue(insDAO.ToDate(data.getTuThang()));//thang bao giam
		
		cell = row.getCell(17);
		if (data.getDenThang()!=null){
			cell.setCellValue(insDAO.ToDate(data.getDenThang()));
		}		
		cell = row.getCell(19); // co/ ko co tra lai the BHYT
		if (data.getCoTraThe()==null || data.getCoTraThe().equals("0")){
			cell.setCellValue("X");
		}
		
		cell = row.getCell(21);
		cell.setCellValue(data.getEmpsn()+", NV "+data.getNgayThucNghi()+", Tra the BHYT :"+data.getNgayTraThe());

		cell = row.createCell(48);//So the
		HSSFUtils.fill(cell,data.getEmpsn());
		cell = row.createCell(49);//So the
		HSSFUtils.fill(cell,data.getFactGroup());		
		if (data.getDonVi()!=null){
			cell = row.createCell(50);//So the
			HSSFUtils.fill(cell,data.getDonVi());	
		}	
		
		//System.out.println(data.getEmpsn());
		// gia tri tra e theo thu tu
		// Sal_BSBHYT, SAl_BaoGiam, NoteGiam, noteLuongGiam, tangBSBHYT, ThoaiThuBSBHYT, tang1Thang, dc1Thang, tgianTang_DC, sal_TangorDC
		String[] data1 = GetThongTinBaoGiam(data.getEmpsn(), data.getTuThang(),data.getNgayTraThe());
		
		// luong bao giam
		cell = row.getCell(6);			
		HSSFUtils.fill(cell,Double.parseDouble(data1[1]));
		// ghi chu bao giam
		cell = row.getCell(20);			
		HSSFUtils.fill(cell,data1[2]);

		boolean giahanThe = checkBox1.isSelected();
		Date ngayTraThe	= null;
		if (data.getNgayTraThe()!= null){
			ngayTraThe = insDAO.ToDate(data.getNgayTraThe());
		}
		Date ngayThucNghi	= null;
		if (data.getNgayThucNghi()!= null){
			ngayThucNghi = insDAO.ToDate(data.getNgayThucNghi());
		}
		Date ngayBaoGiam = null;
		Calendar temp = dfNgayBaoGiam.getSelectedDate();
		ngayBaoGiam	= insDAO.ToDate(sdf.format(temp.getTime()));
		//thangHienTai la thang bao cao goc theo thoi gian hien tai, duoc gan khi goi export trong DoExport
		// lay tyledong khi bao giam, tylebs BHYT, bs tuthang-> bs denthang
		// theo thu tu tyleDong, tyleBS, noteBS, bsTuThang, bsDenThang
		String[] data2 = insDAO.GetTyLeGiamTangBSTheoNgayTraTheBHYT(ngayBaoGiam, ngayTraThe, sdf.format(ngayThucNghi), insDAO.ToDate(data.getTuThang()), thangHienTai, giahanThe, data1[5]);
		// tyleBHXH
		cell = row.getCell(22);
		HSSFUtils.fill(cell,tyleBHXH);
		// tyleBHTN
		cell = row.getCell(24);			
		HSSFUtils.fill(cell,tyleBHTN);
		String tyleGiam = data2[0];
		
		if (Double.valueOf(data2[0])==tyleBHXH+tyleBHTN+tyleBHYT){
			// tyleBHYT
			cell = row.getCell(23);			
			HSSFUtils.fill(cell,tyleBHYT);
		}
		else{
			// tyleBHYT
			cell = row.getCell(23);			
			HSSFUtils.fill(cell,0);			
		}
		
		for (int k=25;k<48;k++){
			cell = row.getCell(k);
			HSSFUtils.evalFormula(wb, cell);
		}
			
		
		//bat dau xet tang 1 thang/ DC 1 thang / Tang BS BHYT
		int salBSBHYT 	= Integer.parseInt(data1[0]);
		int salTangOrDC	= Integer.parseInt(data1[9]);
		String noteBS	= null;		
		String noteTangOrDC = null;
		
		//moi lan Tang/DC/Tang BS BHYT thi se thay doi dong chuan
		// dong chuan =dong chuan + bien dem cua tung loai
		// =--> dam bao rowNew luon lien ke rowOld
		// so sanh theo font vni-times
		//if la tang 1 thang
		
		int dongChuanLDTang 	= 11;
		int dongChuanDCTang 	= 13;
		int dongChuanBSTang		= 16;
		// phai cong kem tong so record(sql) cho ld giam o day, vi minh da tu insert row o ban ngoai truoc roi
		int dongChuanBSGiam		= 25+tsLDGiam;
		int dongChuanNew		= 0;
		
		if (data1[6].equals("1")){
			noteTangOrDC ="Taêng 1 Thaùng";			
			demLDTang= Export_TangDC1ThangOrBS(wb, sheet, row, cell, data, demLDTang,demDCTang, demBSTang, demLDGiam,demBSGiam, dongChuanLDTang+demLDTang, 0, salTangOrDC, data1[8],data1[8], noteTangOrDC);
		}
		//if la DC 1 thang
		// chayvo
		if (data1[7].equals("1")){
			noteTangOrDC = "Ñ/C Löông";
			demDCTang= Export_TangDC1ThangOrBS(wb, sheet, row, cell, data,demLDTang, demDCTang, demBSTang, demLDGiam,demBSGiam, dongChuanDCTang+demDCTang, salBSBHYT, salTangOrDC, data1[8],data1[8], noteTangOrDC);
		}	
		
		//tang thi luong o muc new, muc old =0
		if (data1[4].equals("1") && data2[1].equals("0.045")){
			noteBS = "Taêng BSBHYT";
			//System.out.println(data2[3]+","+data2[4]);
			demBSTang= Export_TangDC1ThangOrBS(wb, sheet, row, cell, data, demLDTang,demDCTang, demBSTang, demLDGiam,demBSGiam, dongChuanBSTang+demBSTang, 0, salBSBHYT, data2[3],data2[4], noteBS);			
		}
		
		// giam thi luong o muc old, muc new =0
		if (data1[5].equals("1") && data2[5].equals("0.045")){
			noteBS = "Thoaùi Thu BSBHYT";
			//System.out.println(data2[3]+","+data2[4]);			 
			demBSGiam= Export_TangDC1ThangOrBS(wb, sheet, row, cell, data, demLDTang,demDCTang, demBSTang, demLDGiam,demBSGiam, dongChuanBSGiam+demBSGiam, salBSBHYT, 0, data2[6],data2[7], noteBS);			
		}			
	}
	
	private void doExportDCLuong20Tay() {
		int month = getMonth();
		int year = getYear();
		Calendar ng = dfNgayBaoCao.getSelectedDate();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(ng.getTimeInMillis());		
		if (cal.get(Calendar.DAY_OF_MONTH)!=20||(cal.get(Calendar.MONTH))!=month||cal.get(Calendar.YEAR)!=year){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Ngày chạy báo cáo phải là ngày 20 của tháng báo DC Lương.");
			return;
		}
		
		String dkFact = insDAO.getFactCondition(sfMaDonVi, sfXuong, sfNhom, sfDonVi);
		List<Map<String, Object>> list = insDAO.getDCLuong20TayList(dkFact,"n_social_infor_report", month, year);		
		Map<String, BigDecimal> thamso = insDAO.getThamSo();
		Date thangBC = DateUtils.getFirstDay(month, year);
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(ng.getTimeInMillis());
		c.set(Calendar.DAY_OF_MONTH, 1);
		if (ng.get(Calendar.DAY_OF_MONTH)>20){
			c.add(Calendar.MONTH, 1);		
		}
		
		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance", "Mau_D02-TS_cong_thuc_GiamTuan.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		
		//fill header
		row = sheet.getRow(2);
		cell = row.getCell(24);
		cell.setCellValue(c);
		
		int rowToCopy=13, rowToCopy1=23, rowToCopy2 = 23;
		int startRow = 14;
		int startRow1 = 24;
		int stt=1;
		int demDCGiam =0;
		
		List<Map<String, Object>> copy = new ArrayList<Map<String,Object>>();
		for (int i=0;i<list.size();i++){
			Map<String, Object> data = list.get(i);
			String emp = (String) data.get("EMPSN");
			String note = (String) data.get("NOTE_TANG");
			
			// luong thoai thu = luong cua thang gan nhat co tham gia BHXH-TN
			BigDecimal luongGanNhat = insDAO.getLuongBaoGiamBHYT(emp, thangBC);
			BigDecimal luongThangBC = (BigDecimal) data.get("BSALARY");
			//if la DCLuong theo dang giam, VD tu 3000-> 2000
			if (luongThangBC.compareTo(luongGanNhat)==-1){
				//fetch more info
				copy.add(data);
				data.put("NOTE_TANG", "DCGIAM");
				demDCGiam++;
			}
		}
		
		int incRows=list.size()-1-demDCGiam;
		int desRows=copy.size()-1;
		
		if (incRows>0){
			sheet.shiftRows(startRow, sheet.getLastRowNum(), incRows, false, true);
			startRow1+=incRows;
			rowToCopy1+=incRows;
			rowToCopy2=rowToCopy1;
		}
		
		if (desRows>0){
			sheet.shiftRows(startRow1, sheet.getLastRowNum(), desRows, false, true);
		}
		
		for (int i=startRow;i<startRow+incRows;i++){
			HSSFUtils.copyRow(wb, sheet, rowToCopy++, i);
		}
		
		for (int i=startRow1;i<startRow1+desRows;i++){
			HSSFUtils.copyRow(wb, sheet, rowToCopy1++, i);
		}
		
		rowToCopy=13;
		rowToCopy1=rowToCopy2;
		
		for (int i=0;i<list.size();i++){
			//fill increase			
//			System.out.println("Fill row " + rowToCopy);			
			row = sheet.getRow(rowToCopy);
			Map<String, Object> data = list.get(i);
			if (data.get("NOTE_TANG").equals("DCGIAM")==false)
			{
				String emp = (String) data.get("EMPSN");
				cell = row.getCell(0);//STT
				HSSFUtils.fill(cell, i+1);
				cell = row.getCell(1);//Ho va ten
				HSSFUtils.fill(cell, data.get("FNAME")+" "+data.get("LNAME"));
				cell = row.getCell(2);//Ma so BHXH
				HSSFUtils.fill(cell, data.get("ID_SOCIAL"));
				cell = row.getCell(3);//Ngay sinh
				HSSFUtils.fill(cell, data.get("BIRTHDAY"));
				cell = row.getCell(4);//Gioi tinh
				if ("NU".equals(data.get("SEX")))
					HSSFUtils.fill(cell, "X");
				else
					HSSFUtils.fill(cell, "");
				cell = row.getCell(5);//Chuc vu
				HSSFUtils.fill(cell, data.get("POSITION"));

				cell = row.getCell(6);//Luong old
				if (data.get("DCDLOAT").equals("BT")==true)
				{
					// luong old = luong cua thang gan nhat co tham gia BHXH-TN
					BigDecimal luong = insDAO.getLuongBaoGiamBHYT(emp, thangBC);
					HSSFUtils.fill(cell, luong.doubleValue());
				}
				else
				{
					HSSFUtils.fill(cell, data.get("SAL_OLD"));
				}
				
				cell = row.getCell(11);//Luong new
				HSSFUtils.fill(cell, data.get("BSALARY"));
				cell = row.getCell(16);//Tang tu thang
				HSSFUtils.fill(cell, data.get("MONTH_BC"));
				cell = row.getCell(20);//Ghi chu
				HSSFUtils.fill(cell, data.get("NOTE_TANG"));
				cell = row.getCell(22);//Ti le BHXH
				HSSFUtils.fill(cell, thamso.get("TYLEBHXH"));
				cell = row.getCell(23);//Ti le BHYT
				HSSFUtils.fill(cell, thamso.get("TYLEBHYT"));
				cell = row.getCell(24);//Ti le BHTN
				HSSFUtils.fill(cell, thamso.get("TYLEBHTN"));
				cell = row.createCell(48);//So the
				HSSFUtils.fill(cell, emp);
				cell = row.createCell(49);//Xuong
				HSSFUtils.fill(cell, data.get("NAME_GROUP"));
				cell = row.createCell(50);//Don vi
				HSSFUtils.fill(cell, data.get("NAME_DEPT_NAME"));
				//eval formula
				cell = row.getCell(18);
				HSSFUtils.evalFormula(wb, cell);
				for (int k=25;k<48;k++){
					cell = row.getCell(k);
					HSSFUtils.evalFormula(wb, cell);
				}
				rowToCopy++;	
			}
		}
		
		//DCLuong nhung thuoc dang DC Giam
		for (int i=0;i<copy.size();i++){
			Map<String, Object> data = copy.get(i);
			String emp = (String) data.get("EMPSN");
			row = sheet.getRow(rowToCopy1);
			cell = row.getCell(0);//STT
			HSSFUtils.fill(cell, stt);
			cell = row.getCell(1);//Ho va ten
			HSSFUtils.fill(cell, data.get("FNAME")+" "+data.get("LNAME"));
			cell = row.getCell(2);//Ma so BHXH
			HSSFUtils.fill(cell, data.get("ID_SOCIAL"));
			cell = row.getCell(3);//Ngay sinh
			HSSFUtils.fill(cell, data.get("BIRTHDAY"));
			cell = row.getCell(4);//Gioi tinh
			if ("NU".equals(data.get("SEX")))
				HSSFUtils.fill(cell, "X");
			else
				HSSFUtils.fill(cell, "");
			cell = row.getCell(5);//Chuc vu
			HSSFUtils.fill(cell, data.get("POSITION"));
			
			cell = row.getCell(6);//Luong old
			if (data.get("DCDLOAT").equals("BT")==true)
			{
				// luong old = luong cua thang gan nhat co tham gia BHXH-TN
				BigDecimal luong = insDAO.getLuongBaoGiamBHYT(emp, thangBC);
				HSSFUtils.fill(cell, luong.doubleValue());
			}
			else
			{
				HSSFUtils.fill(cell, data.get("SAL_OLD"));
			}
						
			cell = row.getCell(11);//Luong new
			HSSFUtils.fill(cell, data.get("BSALARY"));
			
			cell = row.getCell(16);//DC tu thang
			HSSFUtils.fill(cell, data.get("MONTH_BC"));

			cell = row.getCell(20);//Ghi chu
			// vi data.get("NOTE_TANG") luc nay la DCGIAM roi, van giu ghi chu la DCLUong
			HSSFUtils.fill(cell, "DCLuong");
			cell = row.getCell(22);//Ti le BHXH
			HSSFUtils.fill(cell, thamso.get("TYLEBHXH"));
			cell = row.getCell(23);//Ti le BHYT
			HSSFUtils.fill(cell, thamso.get("TYLEBHYT"));
			cell = row.getCell(24);//Ti le BHTN
			HSSFUtils.fill(cell, thamso.get("TYLEBHTN"));
			cell = row.createCell(48);//So the
			HSSFUtils.fill(cell, emp);
			cell = row.createCell(49);//Xuong
			HSSFUtils.fill(cell, data.get("NAME_GROUP"));
			cell = row.createCell(50);//Don vi
			HSSFUtils.fill(cell, data.get("NAME_DEPT_NAME"));
			//eval formula
			cell = row.getCell(18);
			HSSFUtils.evalFormula(wb, cell);
			for (int k=25;k<48;k++){
				cell = row.getCell(k);
				HSSFUtils.evalFormula(wb, cell);
			}
			stt++;
			rowToCopy1++;
		}
		
		try {
			ReportUtils.doExportExcel(wb, "DCLuong20Tay");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doExportTang20Tay() {
		int month = getMonth();
		int year = getYear();
		Calendar ng = dfNgayBaoCao.getSelectedDate();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(ng.getTimeInMillis());		
		if (cal.get(Calendar.DAY_OF_MONTH)!=20||(cal.get(Calendar.MONTH))!=month||cal.get(Calendar.YEAR)!=year){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Ngày chạy báo cáo phải là ngày 20 của tháng báo tăng.");
			return;
		}
		String dkFact = insDAO.getFactCondition(sfMaDonVi, sfXuong, sfNhom, sfDonVi);
		List<Map<String, Object>> list = insDAO.getTang20Tay2120List(dkFact,month,year);
		List<String> list1 = insDAO.getNSRoRaT6T12(month, year);
		Map<String, BigDecimal> thamso = insDAO.getThamSo();
		Date thangBC = DateUtils.getFirstDay(month, year);
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(ng.getTimeInMillis());
		c.set(Calendar.DAY_OF_MONTH, 1);
		if (ng.get(Calendar.DAY_OF_MONTH)>20){
			c.add(Calendar.MONTH, 1);		
		}
		
		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance", "Mau_D02-TS_cong_thuc_GiamTuan.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		
		//fill header
		row = sheet.getRow(2);
		cell = row.getCell(24);
		cell.setCellValue(c);
		
		int rowToCopy=11, rowToCopy1=25, rowToCopy2=25;
		int startRow = 12;
		int startRow1 = 26;
		int stt=1;
		
		List<Map<String, Object>> copy = new ArrayList<Map<String,Object>>();
		for (int i=0;i<list.size();i++){
			Map<String, Object> data = list.get(i);
			String emp = (String) data.get("EMPSN");
			String note = (String) data.get("NOTE_TANG");
			//if la NS-RoRa thang cuoi quy truoc va thang bao cao la RoVao / thang bao cao la Di Lam Lai
			// thi se thoai thu BS BHYT tu thang vao -> het quy do			
			if ((list1.contains(emp) && "Ro vao".equals(note)) 
					||"Di lam lai".equals(note)){
				//fetch more info
				copy.add(data);
			}
		}
		
		int incRows=list.size()-1;
		int desRows=copy.size()-1;
		
		if (incRows>0){
			sheet.shiftRows(startRow, sheet.getLastRowNum(), incRows, false, true);
			startRow1+=incRows;
			rowToCopy1+=incRows;
			rowToCopy2=rowToCopy1;
		}
		
		if (desRows>0){
			sheet.shiftRows(startRow1, sheet.getLastRowNum(), desRows, false, true);
		}
		
		for (int i=startRow;i<startRow+incRows;i++){
			HSSFUtils.copyRow(wb, sheet, rowToCopy++, i);
		}
		
		for (int i=startRow1;i<startRow1+desRows;i++){
			HSSFUtils.copyRow(wb, sheet, rowToCopy1++, i);
		}
		
		rowToCopy=11;
		rowToCopy1=rowToCopy2;
		
		for (int i=0;i<list.size();i++){
			//fill increase			
//			System.out.println("Fill row " + rowToCopy);
			row = sheet.getRow(rowToCopy);
			Map<String, Object> data = list.get(i);
			String emp = (String) data.get("EMPSN");
			cell = row.getCell(0);//STT
			HSSFUtils.fill(cell, i+1);
			cell = row.getCell(1);//Ho va ten
			HSSFUtils.fill(cell, data.get("FNAME")+" "+data.get("LNAME"));
			cell = row.getCell(2);//Ma so BHXH
			HSSFUtils.fill(cell, data.get("ID_SOCIAL"));
			cell = row.getCell(3);//Ngay sinh
			HSSFUtils.fill(cell, data.get("BIRTHDAY"));
			cell = row.getCell(4);//Gioi tinh
			if ("NU".equals(data.get("SEX")))
				HSSFUtils.fill(cell, "X");
			else
				HSSFUtils.fill(cell, "");
			cell = row.getCell(5);//Chuc vu
			HSSFUtils.fill(cell, data.get("POSITION"));
			cell = row.getCell(11);//Tien luong
			HSSFUtils.fill(cell, data.get("BSALARY"));
			cell = row.getCell(16);//Tang tu thang
			HSSFUtils.fill(cell, data.get("MONTH_BC"));
			cell = row.getCell(20);//Ghi chu
			HSSFUtils.fill(cell, data.get("NOTE_TANG"));
			cell = row.getCell(22);//Ti le BHXH
			HSSFUtils.fill(cell, thamso.get("TYLEBHXH"));
			cell = row.getCell(23);//Ti le BHYT
			HSSFUtils.fill(cell, thamso.get("TYLEBHYT"));
			cell = row.getCell(24);//Ti le BHTN
			HSSFUtils.fill(cell, thamso.get("TYLEBHTN"));
			cell = row.createCell(48);//So the
			HSSFUtils.fill(cell, emp);
			cell = row.createCell(49);//Xuong
			HSSFUtils.fill(cell, data.get("NAME_GROUP"));
			cell = row.createCell(50);//Don vi
			HSSFUtils.fill(cell, data.get("NAME_DEPT_NAME"));
			//eval formula
			cell = row.getCell(18);
			HSSFUtils.evalFormula(wb, cell);
			for (int k=25;k<48;k++){
				cell = row.getCell(k);
				HSSFUtils.evalFormula(wb, cell);
			}
			rowToCopy++;			
		}
		
		//RoVao tu NS-RoRa cuoi quy truoc or di lam lai
		String tuThang;
		for (int i=0;i<copy.size();i++){
			Map<String, Object> data = copy.get(i);
			String emp = (String) data.get("EMPSN");
			row = sheet.getRow(rowToCopy1);
			cell = row.getCell(0);//STT
			HSSFUtils.fill(cell, stt);
			cell = row.getCell(1);//Ho va ten
			HSSFUtils.fill(cell, data.get("FNAME")+" "+data.get("LNAME"));
			cell = row.getCell(2);//Ma so BHXH
			HSSFUtils.fill(cell, data.get("ID_SOCIAL"));
			cell = row.getCell(3);//Ngay sinh
			HSSFUtils.fill(cell, data.get("BIRTHDAY"));
			cell = row.getCell(4);//Gioi tinh
			if ("NU".equals(data.get("SEX")))
				HSSFUtils.fill(cell, "X");
			else
				HSSFUtils.fill(cell, "");
			cell = row.getCell(5);//Chuc vu
			HSSFUtils.fill(cell, data.get("POSITION"));
			cell = row.getCell(6);//Tien luong
			// luong thoai thu = luong cua thang gan nhat co tham gia BHXH-TN
			BigDecimal luong = insDAO.getLuongBaoGiamBHYT(emp, thangBC);
			HSSFUtils.fill(cell, luong.doubleValue());
			
			tuThang = data.get("MONTH_BC").toString();			
			cell = row.getCell(16);//Thoai thu tu thang
			HSSFUtils.fill(cell, tuThang);
			cell = row.getCell(17);//Thoai thu den thang
			cell.setCellValue(DateUtils.getThangCuoiQuyHT(Integer.parseInt(tuThang.substring(0, 2)), Integer.parseInt(tuThang.substring(3,7))));
			cell = row.getCell(20);//Ghi chu
			HSSFUtils.fill(cell, "Thoai thu do "+data.get("NOTE_TANG"));
			cell = row.getCell(22);//Ti le BHXH
			HSSFUtils.fill(cell, 0);
			cell = row.getCell(23);//Ti le BHYT
			HSSFUtils.fill(cell, thamso.get("TYLEBHYT"));
			cell = row.getCell(24);//Ti le BHTN
			HSSFUtils.fill(cell, 0);
			cell = row.createCell(48);//So the
			HSSFUtils.fill(cell, emp);
			cell = row.createCell(49);//Xuong
			HSSFUtils.fill(cell, data.get("NAME_GROUP"));
			cell = row.createCell(50);//Don vi
			HSSFUtils.fill(cell, data.get("NAME_DEPT_NAME"));
			//eval formula
			cell = row.getCell(18);
			HSSFUtils.evalFormula(wb, cell);
			for (int k=25;k<48;k++){
				cell = row.getCell(k);
				HSSFUtils.evalFormula(wb, cell);
			}
			stt++;
			rowToCopy1++;
		}
		
		try {
			ReportUtils.doExportExcel(wb, "Tang20Tay");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void doExportGiam20Tay() {
		int month = getMonth();
		int year = getYear();
		Calendar ng = dfNgayBaoCao.getSelectedDate();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(ng.getTimeInMillis());		
		if (cal.get(Calendar.DAY_OF_MONTH)!=20||(cal.get(Calendar.MONTH))!=month||cal.get(Calendar.YEAR)!=year){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Ngày chạy báo cáo phải là ngày 20 của tháng báo tăng.");
			return;
		}
		String dkFact = insDAO.getFactCondition(sfMaDonVi, sfXuong, sfNhom, sfDonVi);
		List<Map<String, Object>> list = insDAO.getGiam20Tay2120List(dkFact, month, year);
		List<String> list1 = insDAO.getNSRoRaT6T12(month, year);
		Map<String, BigDecimal> thamso = insDAO.getThamSo();
		Date thangBC = DateUtils.getFirstDay(month, year);
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(ng.getTimeInMillis());
		c.set(Calendar.DAY_OF_MONTH, 1);
		if (ng.get(Calendar.DAY_OF_MONTH)>20){
			c.add(Calendar.MONTH, 1);		
		}
		
		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance", "Mau_D02-TS_cong_thuc_GiamTuan.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		
		//fill header
		row = sheet.getRow(2);
		cell = row.getCell(24);
		cell.setCellValue(c);
		
		int rowToCopy=16, rowToCopy1=21;
		int startRow = 17;
		int startRow1 = 22;
		int stt=1;
		
		List<Map<String, Object>> copy = new ArrayList<Map<String,Object>>();
		for (int i=0;i<list.size();i++){
			Map<String, Object> data = list.get(i);
			String emp = (String) data.get("EMPSN");
			String note = (String) data.get("NOTE_TANG");
			Date thangNSVao = insDAO.getThangNSVao(emp, sdf.format(thangBC));
			//System.out.println(ng.toString());
			boolean roVao;
			if(thangNSVao==null)
				roVao=true;
			else roVao = insDAO.Check_RoVaoTuNSRoRa2120(emp,sdf.format(thangNSVao) , sdf.format(thangBC), ng.toString());
			/*Tang BS BHYT cho nhung nguoi thoa dk sau
			 * 1)%RoRa trong thang
			 * 2)TH1: ko co thong tin nghi san, thangNSVao==null
			 * 	or TH2: co thong tin nghi san, thangNSVao!= null
			 * 			TH2.1: ko ton tai trong ds gia han the cuoi quy (NS-->RoRa thang cuoi quy truoc)
			 * 		or	TH2.2: co ton tai trong ds gia han the cuoi quy va cung da co RoVao< thang RoRa ke tu thangNSVao	
			 */

			if (
					((thangNSVao==null)||
					(thangNSVao!= null&&list1.contains(emp)==false)||
					(thangNSVao!= null&&list1.contains(emp)==true&&roVao==false)
				)&&(note.equals("NSRa")==false)&&(note.equals("Ro-->NSRa")==false)) 
				//&&(note.equals("NSRa")==false)&&(note.equals("Ro-->NSRa")==false)
			{
				//fetch more info
				copy.add(data);
			}
		}
		
		int desRows=list.size()-1;
		int incRows=copy.size()-1;
		
		if (desRows>0){
			sheet.shiftRows(startRow1, sheet.getLastRowNum(), desRows, false, true);			
		}
		
		if (incRows>0){
			sheet.shiftRows(startRow, sheet.getLastRowNum(), incRows, false, true);			
			startRow1 += incRows;
			rowToCopy1 += incRows;			
		}
		
		startRow1=21;
		for (int i=startRow;i<startRow+desRows;i++){
			HSSFUtils.copyRow(wb, sheet, rowToCopy++, i);
		}
		
		for (int i=startRow1;i<startRow1+incRows;i++){
			HSSFUtils.copyRow(wb, sheet, rowToCopy1++, i);
		}
		
		rowToCopy = 17;
		rowToCopy1 = 22 + incRows;
		startRow1 = 21 + incRows;
		
		BigDecimal luong ;		
		for (int i=0;i<list.size();i++){
			//fill increase			
//			System.out.println("Fill row " + rowToCopy);
			row = sheet.getRow(rowToCopy1);
			Map<String, Object> data = list.get(i);
			String emp = (String) data.get("EMPSN");
			//cell = row.getCell(0);//STT
			cell = row.getCell(0);//STT
			HSSFUtils.fill(cell, i+1);
			cell = row.getCell(1);//Ho va ten
			HSSFUtils.fill(cell, data.get("FNAME")+" "+data.get("LNAME"));
			cell = row.getCell(2);//Ma so BHXH
			HSSFUtils.fill(cell, data.get("ID_SOCIAL"));
			cell = row.getCell(3);//Ngay sinh
			HSSFUtils.fill(cell, data.get("BIRTHDAY"));
			cell = row.getCell(4);//Gioi tinh
			if ("NU".equals(data.get("SEX")))
				HSSFUtils.fill(cell, "X");
			else
				HSSFUtils.fill(cell, "");
			cell = row.getCell(5);//Chuc vu
			HSSFUtils.fill(cell, data.get("POSITION"));
			
/*			//lay luogn gan nhat co tham gia bao hiem XH-TN ke tu thang tang moi			
	  		data1 = insDAO.TTThamGiaBHGanNhat(emp, sdf.format(thangBC));
	  		thangGanNhatCoBH	= data1[0];
	  		luongGanNhatCoBH	= data1[1];
*/
			//la luong cua thang gan nhat co tham gia BHXH-TN 
			luong = insDAO.getLuongBaoGiamBHYT(emp, thangBC);
			cell = row.getCell(6);//muc luogn old/ giam			
			//chi co NS->Ro la luong bao giam =0, else deu co luong bao giam			
			if (data.get("NOTE_TANG").equals("NS-->RoRa"))			
				HSSFUtils.fill(cell, 0);			
			else			
				HSSFUtils.fill(cell, luong.doubleValue());			
			
			cell = row.getCell(11);//muc luong moi/ tang
			HSSFUtils.fill(cell, 0);			
			
			cell = row.getCell(16);//Giam tu thang
			HSSFUtils.fill(cell, data.get("MONTH_BC"));

			//if NSRa thi denThang = null else thi giam 1 thang, denThang=tuThang
			cell = row.getCell(17);//Giam den thang
			if (data.get("NOTE_TANG").equals("NSRa"))			
				HSSFUtils.fill(cell,null);
			else
				cell.setCellValue(insDAO.ToDate("01/"+data.get("MONTH_BC").toString()));

			cell = row.getCell(20);//Ghi chu
			HSSFUtils.fill(cell, data.get("NOTE_TANG"));
			cell = row.getCell(22);//Ti le BHXH
			HSSFUtils.fill(cell, thamso.get("TYLEBHXH"));
			cell = row.getCell(23);//Ti le BHYT
			HSSFUtils.fill(cell, thamso.get("TYLEBHYT"));
			cell = row.getCell(24);//Ti le BHTN
			HSSFUtils.fill(cell, thamso.get("TYLEBHTN"));
			cell = row.getCell(48);//So the
			HSSFUtils.fill(cell, emp);
			cell = row.getCell(49);//Xuong
			HSSFUtils.fill(cell, data.get("NAME_GROUP"));
			cell = row.getCell(50);//Don vi
			HSSFUtils.fill(cell, data.get("NAME_DEPT_NAME"));
			//eval formula
			cell = row.getCell(18);
			HSSFUtils.evalFormula(wb, cell);
			for (int k=25;k<48;k++){
				cell = row.getCell(k);
				HSSFUtils.evalFormula(wb, cell);
			}
			rowToCopy1++;			
		}
		
		//Tang BS BHYT if la %RoRa va chua RoVao (neu la NS-->RoRa)
		for (int i=0;i<copy.size();i++){
			Map<String, Object> data = copy.get(i);
			String emp = (String) data.get("EMPSN");
			row = sheet.getRow(rowToCopy);
			cell = row.getCell(0);//STT
			HSSFUtils.fill(cell, stt);
			cell = row.getCell(1);//Ho va ten
			HSSFUtils.fill(cell, data.get("FNAME")+" "+data.get("LNAME"));
			cell = row.getCell(2);//Ma so BHXH
			HSSFUtils.fill(cell, data.get("ID_SOCIAL"));
			cell = row.getCell(3);//Ngay sinh
			HSSFUtils.fill(cell, data.get("BIRTHDAY"));
			cell = row.getCell(4);//Gioi tinh
			if ("NU".equals(data.get("SEX")))
				HSSFUtils.fill(cell, "X");
			else
				HSSFUtils.fill(cell, "");
			cell = row.getCell(5);//Chuc vu
			HSSFUtils.fill(cell, data.get("POSITION"));
			cell = row.getCell(6);//Luong old
			HSSFUtils.fill(cell, 0);			
			cell = row.getCell(11);//Tien luong
			//la luong cua thang gan nhat co tham gia BHXH-TN 
			luong = insDAO.getLuongBaoGiamBHYT(emp, thangBC);
			HSSFUtils.fill(cell, luong.doubleValue());
			
			//bao %RoRa tung thang nen tuthang=denthang
			cell = row.getCell(16);//tang tu thang
			HSSFUtils.fill(cell, data.get("MONTH_BC"));
			cell = row.getCell(17);//Tang den thang
			// da dinh dang date tren file nhung khi xuat ra van ko hieu, setCellValue de gan format cho cell
			cell.setCellValue(insDAO.ToDate("01/"+data.get("MONTH_BC").toString()));
			
			cell = row.getCell(20);//Ghi chu
			HSSFUtils.fill(cell, "Tang BS BHYT do "+data.get("NOTE_TANG"));
			cell = row.getCell(22);//Ti le BHXH
			HSSFUtils.fill(cell, 0);
			cell = row.getCell(23);//Ti le BHYT
			HSSFUtils.fill(cell, thamso.get("TYLEBHYT"));
			cell = row.getCell(24);//Ti le BHTN
			HSSFUtils.fill(cell, 0);
			cell = row.getCell(48);//So the
			HSSFUtils.fill(cell, emp);
			cell = row.getCell(49);//Xuong
			HSSFUtils.fill(cell, data.get("NAME_GROUP"));
			cell = row.getCell(50);//Don vi
			HSSFUtils.fill(cell, data.get("NAME_DEPT_NAME"));
			//eval formula
			cell = row.getCell(18);
			HSSFUtils.evalFormula(wb, cell);
			for (int k=25;k<48;k++){
				cell = row.getCell(k);
				HSSFUtils.evalFormula(wb, cell);
			}
			stt++;
			rowToCopy++;
		}
		
		try {
			ReportUtils.doExportExcel(wb, "Giam20Tay");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void doExportGiamTuanNew() throws IOException {		
		
		Calendar ngayBaoGiam = dfNgayBaoGiam.getSelectedDate();
		if (ngayBaoGiam.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY && ngayBaoGiam.get(Calendar.DAY_OF_WEEK)!=Calendar.THURSDAY){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Chi duco chon Thu 2 hoac Thu 5");
			return;
		}
		int month=0, year=0;		
		Calendar cal = Calendar.getInstance();
		cal.set(ngayBaoGiam.get(Calendar.YEAR), ngayBaoGiam.get(Calendar.MONTH), 20);
		if (ngayBaoGiam.compareTo(cal)>0){
			cal.add(Calendar.MONTH, 1);
		}
		month = cal.get(Calendar.MONTH)+1;//Calendar Month 0->11, trong class InsuranceDAO tu 1-12
		year = cal.get(Calendar.YEAR);
		//System.out.println(month + "  " + year);
		InsuranceDAO dao = new InsuranceDAO();
		dao.setMonth(month);
		dao.setYear(year);		
		
		List<BieuMau3A> list = dao.getDSBaoGiamTuan(insDAO.getFactCondition(sfMaDonVi, sfXuong, sfNhom, sfDonVi), ngayBaoGiam);
		
		HSSFWorkbook wb = insDAO.getWorkbook("insurance", "Mau_D02-TS_cong_thuc_GiamTuan.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFRow templateRow = sheet.getRow(10);
		HSSFCell cell;
		int r = 21;// old 11, la dong chuan cua lao dong giam
		
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();		
				
		//gan thoi gian hien tai cho cell Y3 trong file mau excel
		// java dem tu 0, excel dem tu 1
		HSSFRow rowTGHienHanh = sheet.getRow(2);// la row thu 3 trong excel
		cell = rowTGHienHanh.getCell(24);
		cell.setCellValue(insDAO.ToDate(thangHienTai));
		
		int rowToCopy=11, rowToCopy1=13, rowToCopy2=16, rowToCopy3=21;
		int startRow = 12, startRow1 = 14, startRow2 = 17,startRow3=22;
		int stt=1;		
		
		int incRows=list.size()-1;		
		//int incRows=list.size();
		
		if (incRows>0){
			sheet.shiftRows(startRow3, sheet.getLastRowNum(), incRows, false, true);						
		}
		
		for (int i=startRow3;i<startRow3+incRows;i++){
			HSSFUtils.copyRow(wb, sheet, rowToCopy3++, i);
		}		
		
		rowToCopy=11;
		rowToCopy1=13;
		rowToCopy2=16;
		rowToCopy3=21;
		
		
		for (int i=0;i<list.size();i++){			
			//fill increase			
//			System.out.println("Fill row " + rowToCopy);
			row = sheet.getRow(rowToCopy3);
			
			BieuMau3A data = list.get(i);
			demLDGiam ++;
			InsuranceLogic2120 logic = InsuranceLogic2120.getInstanse(data, dao);
			MonthYearType thangBaoGiam = logic.getThangBaoGiam();			
			data.setTuThang("01/"+thangBaoGiam.toString());
			data.setDenThang(null);			
			ExportDSLDGiam(rowToCopy3, i, incRows, wb, sheet, row, cell, data);
			//rowToCopy3= dong chuan cua ldGiam+demLDTang+demDCTang+demBSTang+demLDGiam
			rowToCopy3= r+demLDTang+demDCTang+demBSTang+demLDGiam;//+ cong DemLdGIam o day duoc			
		}
		save(wb, "GiamTuan");		
		
	}
	
	
	private void doExportTang1Tay() throws IOException{		
		insDAO.setMonth(Integer.parseInt(sfThang.getSelectedItem().toString()));
		insDAO.setYear(Integer.parseInt(sfNam.getSelectedItem().toString()));
		List<Tang1TayObject> list = insDAO.getDSTang1Tay(insDAO.getFactCondition(sfMaDonVi, sfXuong, sfNhom, sfDonVi) ,sfThang.getSelectedItem().toString(), sfNam.getSelectedItem().toString());
		HSSFWorkbook wb = insDAO.getWorkbook("insurance", "Mau_D02-TS_cong_thuc_Tang1Tay.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFRow templateRow = sheet.getRow(10);
		HSSFCell cell;
		int r = 11; 
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		
		//gan thoi gian hien tai cho cell Y3 trong file mau excel
		// java dem tu 0, excel dem tu 1
		HSSFRow rowTGHienHanh = sheet.getRow(2);// la row thu 3 trong excel
		cell = rowTGHienHanh.getCell(24);
		cell.setCellValue(insDAO.ToDate(thangHienTai));
		
//aa
		int startRow =12;
		int rowToCopy=11; 
		int incRows=list.size()-1;
		if (incRows>0){
			sheet.shiftRows(startRow, sheet.getLastRowNum(), incRows, false, true);						
		}
		
		for (int i=startRow;i<startRow+incRows;i++){
			HSSFUtils.copyRow(wb, sheet, rowToCopy++, i);
		}		
		
		rowToCopy=11;				
//aa
		
		for (int i=0;i<list.size();i++){
			Tang1TayObject data = list.get(i);
			
/*			// copy va insert them 1 row moi tren file mau
			sheet.shiftRows(r, sheet.getLastRowNum(), 1, true, false);
			row = sheet.createRow(r);
			row = sheet.getRow(r);			

			//row =sheet.createRow(r); // cai nay la copy de len dong hien tai dang co tren file mau
			
			for (int j=0;j<templateRow.getPhysicalNumberOfCells();j++){
				copyCell(templateRow.getCell(j), row.createCell(j));			
			}
*/
			row = sheet.getRow(rowToCopy);
			cell = row.getCell(0);
			cell.setCellValue(i+1);			
			cell = row.getCell(1);
			cell.setCellValue(data.getHoTen());
			cell = row.getCell(2);
			if (data.getMaSoBHXH()==null){
				cell.setCellValue("");
			}
			else{
				cell.setCellValue(data.getMaSoBHXH());	
			}
			
			//cell = row.getCell(3);
			//cell.setCellValue(data.getNgaySinh());
			
			cell = row.getCell(6); // luong o muc old
			cell.setCellValue(0);
			
			cell = row.getCell(11);// old 9
			cell.setCellValue(data.getLuong().intValue());
			cell = row.getCell(16);// old 14
			cell.setCellValue(insDAO.ToDate(data.getThangTangMoi()));
			cell = row.getCell(20);// old 19
			cell.setCellValue(data.getGhiChu());
			
			//System.out.println(data.getEmpsn());
			
			cell = row.getCell(21); //old 20
			cell.setCellValue(data.getEmpsn());
			
			cell = row.getCell(22); 
			cell.setCellValue(tyleBHXH);
			cell = row.getCell(23);
			cell.setCellValue(tyleBHYT);
			cell = row.getCell(24);
			cell.setCellValue(tyleBHTN);
			
			cell = row.getCell(48); // old 41
			cell.setCellValue(data.getEmpsn());
			cell = row.getCell(49); // old 42
			cell.setCellValue(data.getFactGroup());
			cell = row.getCell(50); // old 43			
			cell.setCellValue(data.getDonVi());
			
			rowToCopy++;
		}
		save(wb, "Tang1Tay");
	}
	
	private void doExportCapSoMoi() throws IOException{		
		insDAO.setMonth(Integer.parseInt(sfThang.getSelectedItem().toString()));
		insDAO.setYear(Integer.parseInt(sfNam.getSelectedItem().toString()));
		List<Tang1TayObject> list = insDAO.getDSTang1Tay(insDAO.getFactCondition(sfMaDonVi, sfXuong, sfNhom, sfDonVi) ,sfThang.getSelectedItem().toString(), sfNam.getSelectedItem().toString());
		HSSFWorkbook wb = insDAO.getWorkbook("insurance", "Mau A01a-TS_CapSoMoi.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int r = 11;		
		
		//gan thoi gian hien tai cho cell Y3 trong file mau excel
		// java dem tu 0, excel dem tu 1
/*		HSSFRow rowTGHienHanh = sheet.getRow(2);// la row thu 3 trong excel
		cell = rowTGHienHanh.getCell(24);
		cell.setCellValue(insDAO.ToDate(thangHienTai));*/
		
		int startRow =12;
		int rowToCopy=11; 
		int incRows=list.size()-1;
		if (incRows>0){
			sheet.shiftRows(startRow, sheet.getLastRowNum(), incRows, false, true);						
		}
		
		for (int i=startRow;i<startRow+incRows;i++){
			HSSFUtils.copyRow(wb, sheet, rowToCopy++, i);
		}		
		
		rowToCopy=11;
		for (int i=0;i<list.size();i++){
			Tang1TayObject data = list.get(i);
			row = sheet.getRow(rowToCopy);
			cell = row.getCell(0);
			cell.setCellValue(i+1);			
			cell = row.getCell(1);
			cell.setCellValue(data.getHoTen());
			//cell 2 mac dinh la null
			cell = row.getCell(3);
			if (data.getMaSoBHXH()==null){
				cell.setCellValue("");
			}
			else{
				cell.setCellValue(data.getMaSoBHXH());	
			}
			
			cell = row.getCell(4);
			cell.setCellValue(data.getNgaySinh());
			
			//if la nu thi danh dau X
			cell = row.getCell(5);
			if (data.getGioiTinh().equals("NAM")){
				cell.setCellValue("");	
			}
			else{
				cell.setCellValue("X");
			}
			cell = row.getCell(6); // dan toc
			cell.setCellValue(data.getDanToc());
			
			cell = row.getCell(7);			
			if (data.getCMND()==null){
				cell.setCellValue("");
			}
			else{
				cell.setCellValue(data.getCMND());	
			}
			
			cell = row.getCell(8);			
			if (data.getNgayCap()==null){
				cell.setCellValue("");
			}
			else{
				cell.setCellValue(data.getNgayCap());	
			}
			
			cell = row.getCell(9);			
			if (data.getNoiCap()==null){
				cell.setCellValue("");
			}
			else{
				cell.setCellValue(data.getNoiCap());	
			}
			
			cell = row.getCell(10);
			cell.setCellValue(data.getHoKhau());	

			cell = row.createCell(15);			
			HSSFUtils.fill(cell,data.getFactGroup()+"."+data.getDonVi());
			cell = row.createCell(16);
			HSSFUtils.fill(cell,data.getEmpsn());			
			
			rowToCopy++;
		}
		save(wb, "CapSoMoi");
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
	
	public void copyCell(HSSFCell cell1, HSSFCell cell2) {
        switch(cell1.getCellType()) {
            case HSSFCell.CELL_TYPE_BLANK:
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                cell2.setCellValue(cell1.getBooleanCellValue());
                cell2.setCellStyle(cell1.getCellStyle());
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                cell2.setCellErrorValue(cell1.getErrorCellValue());
                cell2.setCellStyle(cell1.getCellStyle());
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
            	// them vao 25/02/2013, HA
            	// vi lay duoc cong thuc nhung khong tang duoc thu tu row dang thao tac
            	// ngoai excel thi thu tu la tu 1, trong java thu tu tu 0, vi vay phai +1
            	//System.out.println(cell1.getCellFormula().replace(String.valueOf(cell1.getRowIndex()+1),String.valueOf(cell2.getRowIndex()+1)));
            	//cell2.setCellFormula(cell1.getCellFormula()); // old                
 				cell2.setCellFormula(cell1.getCellFormula().replace(String.valueOf(cell1.getRowIndex()+1),String.valueOf(cell2.getRowIndex()+1)));
                cell2.setCellStyle(cell1.getCellStyle());
             /*
				String formula = cell1.getCellFormula();
				formula = fv.util.HSSFUtils.replaceFormulaRow(formula, (cell2.getRowIndex()+1));
				cell2.setCellFormula(formula);
				*/
                break;
            case HSSFCell.CELL_TYPE_STRING:
                cell2.setCellValue(cell1.getStringCellValue());
                cell2.setCellStyle(cell1.getCellStyle());
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                cell2.setCellValue(cell1.getNumericCellValue());
                cell2.setCellStyle(cell1.getCellStyle());
                break;    
        }
    }
	
	public String getViewerUrl() {
		HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
		String viewerUrl = request.getRequestURL() + "?" +
		WebRenderServlet.SERVICE_ID_PARAMETER + "=" + ReportService.INSTANCE.getId() + "&" + 
		ReportService.PARAM_TYPE + "=" + ReportService.TYPE_TEMP + "&" +
		ReportService.PARAM_KEY + "=";
		return viewerUrl;
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setBackground(new Color(0xece9d8));
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(40, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
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
		groupBox2.setWidth(new Extent(420, Extent.PX));
		GridLayoutData groupBox2LayoutData = new GridLayoutData();
		groupBox2LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBox2.setLayoutData(groupBox2LayoutData);
		rootLayout.add(groupBox2);
		Grid thoiGianGrid = new Grid();
		thoiGianGrid.setSize(3);
		groupBox2.add(thoiGianGrid);
		Label label1 = new Label();
		label1.setText("Ngày chạy báo cáo");
		thoiGianGrid.add(label1);
		dfNgayBaoCao = new DscDateField();
		dfNgayBaoCao.setUpdateFromTextField(true);
		thoiGianGrid.add(dfNgayBaoCao);
		GroupBox groupBox1 = new GroupBox();
		GridLayoutData groupBox1LayoutData = new GridLayoutData();
		groupBox1LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBox1LayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		groupBox1LayoutData.setRowSpan(4);
		groupBox1.setLayoutData(groupBox1LayoutData);
		thoiGianGrid.add(groupBox1);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5,
				Extent.PX), new Extent(0, Extent.PX), new Extent(5, Extent.PX)));
		grid1.setSize(1);
		groupBox1.add(grid1);
		radQuy1 = new RadioButton();
		radQuy1.setText("Quý 1");
		grid1.add(radQuy1);
		radQuy2 = new RadioButton();
		radQuy2.setText("Quý 2");
		grid1.add(radQuy2);
		radQuy3 = new RadioButton();
		radQuy3.setText("Quý 3");
		grid1.add(radQuy3);
		radQuy4 = new RadioButton();
		radQuy4.setText("Quý 4");
		grid1.add(radQuy4);
		Label label2 = new Label();
		label2.setText("Tháng");
		thoiGianGrid.add(label2);
		sfThang = new SelectField();
		sfThang.setWidth(new Extent(40, Extent.PX));
		thoiGianGrid.add(sfThang);
		Label label3 = new Label();
		label3.setText("Năm");
		thoiGianGrid.add(label3);
		sfNam = new SelectField();
		sfNam.setWidth(new Extent(60, Extent.PX));
		thoiGianGrid.add(sfNam);
		Label label4 = new Label();
		label4.setText("Ngày báo giảm");
		thoiGianGrid.add(label4);
		dfNgayBaoGiam = new DscDateField();
		thoiGianGrid.add(dfNgayBaoGiam);
		Grid grid2 = new Grid();
		grid2.setSize(4);
		GridLayoutData grid2LayoutData = new GridLayoutData();
		grid2LayoutData.setInsets(new Insets(new Extent(1, Extent.PX)));
		grid2LayoutData.setColumnSpan(3);
		grid2.setLayoutData(grid2LayoutData);
		thoiGianGrid.add(grid2);
		Label label5 = new Label();
		label5.setText("Đợt báo giảm từ:");
		grid2.add(label5);
		lblBaoGiamTu = new Label();
		lblBaoGiamTu.setText("01/01/2011");
		lblBaoGiamTu.setForeground(new Color(0x008000));
		GridLayoutData lblBaoGiamTuLayoutData = new GridLayoutData();
		lblBaoGiamTuLayoutData.setInsets(new Insets(new Extent(5, Extent.PX),
				new Extent(0, Extent.PX), new Extent(5, Extent.PX), new Extent(
						0, Extent.PX)));
		lblBaoGiamTu.setLayoutData(lblBaoGiamTuLayoutData);
		grid2.add(lblBaoGiamTu);
		Label label7 = new Label();
		label7.setText("đến: ");
		grid2.add(label7);
		lblBaoGiamDen = new Label();
		lblBaoGiamDen.setText("12/12/2012");
		lblBaoGiamDen.setForeground(new Color(0x008000));
		GridLayoutData lblBaoGiamDenLayoutData = new GridLayoutData();
		lblBaoGiamDenLayoutData.setInsets(new Insets(new Extent(5, Extent.PX),
				new Extent(0, Extent.PX)));
		lblBaoGiamDen.setLayoutData(lblBaoGiamDenLayoutData);
		grid2.add(lblBaoGiamDen);
		CheckBox checkBox1 = new CheckBox();
		checkBox1.setText("Chưa có thẻ gia hạn BHYT");
		GridLayoutData checkBox1LayoutData = new GridLayoutData();
		checkBox1LayoutData.setColumnSpan(4);
		checkBox1.setLayoutData(checkBox1LayoutData);
		grid2.add(checkBox1);
		GroupBox groupBox3 = new GroupBox();
		groupBox3.setTitle("Đơn vị");
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
		donViGrid.add(label6);
		sfMaDonVi = new SelectField();
		sfMaDonVi.setWidth(new Extent(150, Extent.PX));
		donViGrid.add(sfMaDonVi);
		Label label8 = new Label();
		label8.setText("Xưởng");
		donViGrid.add(label8);
		sfXuong = new SelectField();
		sfXuong.setWidth(new Extent(150, Extent.PX));
		donViGrid.add(sfXuong);
		Label label9 = new Label();
		label9.setText("Nhóm");
		donViGrid.add(label9);
		sfNhom = new SelectField();
		sfNhom.setWidth(new Extent(150, Extent.PX));
		donViGrid.add(sfNhom);
		Label label10 = new Label();
		label10.setText("Đơn vị");
		donViGrid.add(label10);
		sfDonVi = new SelectField();
		sfDonVi.setWidth(new Extent(150, Extent.PX));
		donViGrid.add(sfDonVi);
		GroupBox groupBox4 = new GroupBox();
		groupBox4.setTitle("Biến động trong tháng");
		groupBox4.setWidth(new Extent(380, Extent.PX));
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
		luaChonGrid.add(radTang1Tay);
		radCapSo1Tay = new RadioButton();
		radCapSo1Tay.setText("Cấp sổ mới trong tháng (1 tây)");
		luaChonGrid.add(radCapSo1Tay);
		radTang20Tay = new RadioButton();
		radTang20Tay
				.setText("Nghỉ sản vào, nghỉ Ro vào, NV đi làm lại (bc 20 tây)");
		luaChonGrid.add(radTang20Tay);
		radTang15Tay = new RadioButton();
		radTang15Tay.setText("Nghỉ sản vào (báo cáo 15 tây)");
		luaChonGrid.add(radTang15Tay);
		radGiamTuan = new RadioButton();
		radGiamTuan.setText("Giảm trong tháng/Từ ngày đến ngày");
		luaChonGrid.add(radGiamTuan);
		radGiam20Tay = new RadioButton();
		radGiam20Tay.setText("Nghỉ sản ra, nghỉ Ro ra");
		luaChonGrid.add(radGiam20Tay);
		radGiamTrongTangMoi = new RadioButton();
		radGiamTrongTangMoi.setText("Giảm trong danh sách báo tăng mới");
		luaChonGrid.add(radGiamTrongTangMoi);
		radDieuChinhLuong = new RadioButton();
		radDieuChinhLuong.setText("Điều chỉnh lương trong tháng");
		luaChonGrid.add(radDieuChinhLuong);
		radDieuChinhHoSo = new RadioButton();
		radDieuChinhHoSo.setText("Điều chỉnh hồ sơ tham gia BHYT");
		luaChonGrid.add(radDieuChinhHoSo);
		radNghiViecCoTraThe = new RadioButton();
		radNghiViecCoTraThe.setText("Nghỉ việc có trả thẻ BHYT");
		luaChonGrid.add(radNghiViecCoTraThe);
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
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_export_excel_press.gif");
		btnExport.setPressedBackgroundImage(new FillImage(imageReference1));
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_export_excel_hover.gif");
		btnExport.setRolloverBackgroundImage(new FillImage(imageReference2));
		btnExport.setHeight(new Extent(30, Extent.PX));
		btnExport.setWidth(new Extent(120, Extent.PX));
		btnExport.setPressedEnabled(true);
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_export_excel.gif");
		btnExport.setBackgroundImage(new FillImage(imageReference3));
		btnExport.setRolloverEnabled(true);
		buttonGrid.add(btnExport);
		btnImport = new Button();
		ResourceImageReference imageReference4 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_import_excel_hover.gif");
		btnImport.setRolloverBackgroundImage(new FillImage(imageReference4));
		ResourceImageReference imageReference5 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_import_excel_press.gif");
		btnImport.setPressedBackgroundImage(new FillImage(imageReference5));
		btnImport.setHeight(new Extent(30, Extent.PX));
		btnImport.setWidth(new Extent(122, Extent.PX));
		btnImport.setPressedEnabled(true);
		ResourceImageReference imageReference6 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_import_excel.gif");
		btnImport.setBackgroundImage(new FillImage(imageReference6));
		btnImport.setRolloverEnabled(true);
		buttonGrid.add(btnImport);
		btnKhoa = new Button();
		ResourceImageReference imageReference7 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_khoa_press.gif");
		btnKhoa.setPressedBackgroundImage(new FillImage(imageReference7));
		ResourceImageReference imageReference8 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_khoa_hover.gif");
		btnKhoa.setRolloverBackgroundImage(new FillImage(imageReference8));
		btnKhoa.setHeight(new Extent(30, Extent.PX));
		btnKhoa.setWidth(new Extent(120, Extent.PX));
		btnKhoa.setPressedEnabled(true);
		ResourceImageReference imageReference9 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_khoa.gif");
		btnKhoa.setBackgroundImage(new FillImage(imageReference9));
		btnKhoa.setRolloverEnabled(true);
		buttonGrid.add(btnKhoa);
		btnMoKhoa = new Button();
		ResourceImageReference imageReference10 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_mokhoa_hover.gif");
		btnMoKhoa.setRolloverBackgroundImage(new FillImage(imageReference10));
		ResourceImageReference imageReference11 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_mokhoa_press.gif");
		btnMoKhoa.setPressedBackgroundImage(new FillImage(imageReference11));
		btnMoKhoa.setHeight(new Extent(30, Extent.PX));
		btnMoKhoa.setWidth(new Extent(122, Extent.PX));
		btnMoKhoa.setPressedEnabled(true);
		ResourceImageReference imageReference12 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_mokhoa.gif");
		btnMoKhoa.setBackgroundImage(new FillImage(imageReference12));
		btnMoKhoa.setRolloverEnabled(true);
		buttonGrid.add(btnMoKhoa);
		btnKiemTra = new Button();
		ResourceImageReference imageReference13 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_kiemtra_hover.gif");
		btnKiemTra.setRolloverBackgroundImage(new FillImage(imageReference13));
		ResourceImageReference imageReference14 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_kiemtra_press.gif");
		btnKiemTra.setPressedBackgroundImage(new FillImage(imageReference14));
		btnKiemTra.setHeight(new Extent(30, Extent.PX));
		btnKiemTra.setWidth(new Extent(250, Extent.PX));
		btnKiemTra.setPressedEnabled(true);
		ResourceImageReference imageReference15 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_kiemtra.gif");
		btnKiemTra.setBackgroundImage(new FillImage(imageReference15));
		btnKiemTra.setRolloverEnabled(true);
		GridLayoutData btnKiemTraLayoutData = new GridLayoutData();
		btnKiemTraLayoutData.setColumnSpan(2);
		btnKiemTra.setLayoutData(btnKiemTraLayoutData);
		buttonGrid.add(btnKiemTra);
		btnReset = new Button();
		ResourceImageReference imageReference16 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_reset_hover.gif");
		btnReset.setRolloverBackgroundImage(new FillImage(imageReference16));
		ResourceImageReference imageReference17 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_reset_press.gif");
		btnReset.setPressedBackgroundImage(new FillImage(imageReference17));
		btnReset.setHeight(new Extent(30, Extent.PX));
		btnReset.setWidth(new Extent(250, Extent.PX));
		btnReset.setPressedEnabled(true);
		ResourceImageReference imageReference18 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/mb_reset.gif");
		btnReset.setBackgroundImage(new FillImage(imageReference18));
		btnReset.setRolloverEnabled(true);
		GridLayoutData btnResetLayoutData = new GridLayoutData();
		btnResetLayoutData.setColumnSpan(2);
		btnReset.setLayoutData(btnResetLayoutData);
		buttonGrid.add(btnReset);
	}
}
