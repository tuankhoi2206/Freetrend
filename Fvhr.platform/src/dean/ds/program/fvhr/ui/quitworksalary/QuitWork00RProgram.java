package ds.program.fvhr.ui.quitworksalary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import ds.program.fvhr.dao.generic.FvGenericDAO;
import ds.program.fvhr.dao.quitsalary.QuitWorkGenericObject;
import ds.program.fvhr.dao.quitsalary.QuitWorkSalaryDAO;
import ds.program.fvhr.domain.ATTQUIT;
import ds.program.fvhr.ui.quitworksalary.filler.QTongHopBHTNFiller;
import ds.program.fvhr.ui.quitworksalary.filler.QTongHopBHXHFiller;
import ds.program.fvhr.ui.quitworksalary.filler.QTongHopBHYTFiller;
import ds.program.fvhr.ui.quitworksalary.filler.QTongHopBUBHFiller;
import ds.program.fvhr.ui.quitworksalary.filler.QTongHopFiller;
import ds.program.fvhr.ui.quitworksalary.filler.QexcelKyNhanFiller;
import ds.program.fvhr.ui.quitworksalary.filler.QexcelLuongFiller;
import ds.program.fvhr.ui.quitworksalary.filler.QexcelThanhToanFiller;
import ds.program.fvhr.ui.quitworksalary.filler.QexcelTroCapFiller;
import fv.components.SimpleReportProgram;
import fv.util.BundleUtils;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.MappingPropertyUtils;
import fv.util.Vni2Uni;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscGroupCheckBox;
import dsc.echo2app.component.DscGroupRadioButton;
import dsc.util.function.UUID;
import echopointng.GroupBox;
import echopointng.model.CalendarEvent;
import echopointng.model.CalendarSelectionListener;

/**
 * Báo biểu lương thôi việc
 * 
 * @author Hiếu
 * @since August 05, 2011
 */
public class QuitWork00RProgram extends SimpleReportProgram {
	// SQL INDEX
	public static final int EXCEL_LUONG = 0;

	public static final int EXCEL_TROCAP = 1;

	public static final int EXCEL_THANHTOAN = 2;

	public static final int EXCEL_KYNHAN = 3;

	public static final int TONG_TONG = 4;

	public static final int TONG_BHXH = 5;

	public static final int TONG_BHYT = 6;

	public static final int TONG_BHTN = 7;

	public static final int TONG_BUBH = 8;

	public static final int PDF_PHIEULUONG = 9;
	public static final int PDF_PHIEULUONG_ATM = 10;
	public static final int PDF_PHIEULUONG_0ATM = 11;

	private static final long serialVersionUID = 1L;

	private RadioButton radFact1;

	private RadioButton radFact2;

	private RadioButton radFG1;

	private RadioButton radFG2;

	private SelectField sfMonth;

	private SelectField sfYear;

	private Row rowMonthYear;

	private RadioButton radQuitDate;

	private RadioButton radMonthYear;

	private DscDateField dfDotTV;

	private RadioButton radDot1;

	private RadioButton radDot2;

	private RadioButton radDot3;

	private CheckBox chkTV;

	private CheckBox chkBV;

	private Grid rootLayout;

	private RadioButton radTonghop;

	private RadioButton radBangTong;

	private RadioButton radBHXH;

	private RadioButton radBHYT;

	private RadioButton radBHTN;

	private RadioButton radBuBH;

	private RadioButton radExcel;

	private RadioButton radTongLuongTV;

	private RadioButton radTongTroCap;

	private RadioButton radBangThanhToan;

	private RadioButton radBangKyNhan;

	private SelectField sfFact;

	private DscGroupRadioButton groupTongHop;

	private DscGroupRadioButton groupExcel;

	private DscGroupCheckBox groupFVL;

	private Row rowDotTV;

	// data object variable
	private String factCondition;

	private Calendar dotTV;

	private String month1;

	private String month2;

	private String year1;

	private String year2;

	private String type;

	private QuitWorkSalaryDAO dao;

	private static final Log log = LogFactory.getLog(QuitWork00RProgram.class);
	private String monthyear;
	private String dotThoiViec;
	private String fact;

	public QuitWork00RProgram() {
		super();
		dao = new QuitWorkSalaryDAO();
		initComponents();
		initUI();
	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		getToolbar().setConfigButtonEnable(false);
		// getToolbar().setPdfButtonEnable(false);
		getToolbar().setSearchButtonEnable(false);
		getToolbar().setRefreshButtonEnable(false);
		getToolbar().setPdfButtonEnable(false);
		Button btnPdf = new Button();
		btnPdf.setStyleName("Default.ToolbarButton");
		ResourceImageReference pdfIcon = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnPDF.gif");
		btnPdf.setIcon(pdfIcon);
		ResourceImageReference pdfIconD = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnPDFD.gif");
		btnPdf.setDisabledIcon(pdfIconD);
		btnPdf.setToolTipText("In phiếu lương thu nhỏ");
		btnPdf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!validateUI()){
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, getErrorMessage());
					return;
				}
				AttQuit01PrintPane pane = new AttQuit01PrintPane(QuitWork00RProgram.this);
				pane.show();
			}
		});
		getToolbar().add(btnPdf);
//		Button btnPrint1 = new Button("In ATM");
//		getToolbar().add(btnPrint1);
//		btnPrint1.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				try {
//					JasperPrint jp = generateJasperPrint1();
//					File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
//					f.deleteOnExit();
//					try {
//						JasperExportManager.exportReportToPdfFile(jp, f.getPath());
//						File saveFile = new File(f.getParentFile(),URLEncoder.encode(getLoginInfo().getUserID() + ";application/pdf;"  + "PLTV_" + Calendar.getInstance().getTimeInMillis() + ".pdf", "UTF-8"));			
//						ReportFileManager.saveTempReportFile(f, saveFile);
//						saveFile.deleteOnExit();
//						Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
//					} catch (JRException e1) {
//						e1.printStackTrace();
//					} catch (UnsupportedEncodingException e1) {
//						e1.printStackTrace();
//					}
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		});
		return ret;
	}

	private void initUI() {
		dfDotTV.getTextField().setDisabledBackground(new Color(0xc0c0c0));
		dfDotTV.setDateFormat(BundleUtils.getDateFormat());
		dfDotTV.setSelectedDate(Calendar.getInstance());
		dfDotTVChanged(dfDotTV.getSelectedDate());
		dfDotTV.getDateChooser().setLocale(new Locale("en"));
		dfDotTV.getModel().addListener(new CalendarSelectionListener() {

			@Override
			public void selectedDateChange(CalendarEvent calEvent) {
				dfDotTVChanged(calEvent.getCalendar());
			}

			@Override
			public void displayedDateChange(CalendarEvent calEvent) {
				dfDotTVChanged(calEvent.getCalendar());
			}
		});
		ListBinder.bindSelectField(sfFact, FVGenericInfo.getFactories(), false);
		ListBinder.bindSelectField(sfMonth, MappingPropertyUtils
				.getJavaMonthEditor(), true);
		ListBinder.bindSelectField(sfYear, MappingPropertyUtils.getYearEditor(
				2, false), true);
		Calendar cal = Calendar.getInstance();
		ListBinder.refreshIndex(sfMonth, cal.get(Calendar.MONTH));
		ListBinder.refreshIndex(sfYear, cal.get(Calendar.YEAR));
		sfMonth.setEnabled(false);
		sfYear.setEnabled(false);
	}

	@Override
	protected void doRefresh() {
	}

	@Override
	protected void doSearch() {

	}

	private void dfDotTVChanged(Calendar cal) {
		int month = cal.get(Calendar.MONTH);		
		int year = cal.get(Calendar.YEAR)-1;
		radDot1.setText("Đợt 1(T" + (month==0?"12/"+year:month) + ")");
		radDot2.setText("Đợt 2(T" + (month + 1) + ")");
		radDot3.setText("Cả 2(T" + (month==0?"12/"+year:month) + " & T"
				+ (month + 1) + ")");
	}

	private void sfFactoriesChanged(ActionEvent e) {
		if (sfFact.getSelectedIndex() >= 0)
			if (sfFact.getSelectedItem().toString().equals("FVL")) {
				groupFVL.setEnabled(true);
			} else {
				groupFVL.setEnabled(false);
			}
	}

	private void donviChanged(ActionEvent e) {
		if (e.getActionCommand().equals("xuong")) {
			sfFact.setEnabled(true);
			radFG1.setEnabled(false);
			radFG2.setEnabled(false);
			sfFactoriesChanged(e);
		} else {
			sfFact.setEnabled(false);
			radFG1.setEnabled(true);
			radFG2.setEnabled(true);
			groupFVL.setEnabled(false);
		}
	}

	private void bangluongChanged(ActionEvent e) {
		if (e.getActionCommand().equals("thangnam")) {
			sfMonth.setEnabled(true);
			sfYear.setEnabled(true);
			dfDotTV.setEnabled(false);
			rowDotTV.setEnabled(false);
		} else {
			sfMonth.setEnabled(false);
			sfYear.setEnabled(false);
			dfDotTV.setEnabled(true);
			rowDotTV.setEnabled(true);
		}
	}

	private void baocaoChanged(ActionEvent e) {
		if (e.getActionCommand().equals("bctonghop")) {
			groupExcel.setEnabled(false);
			groupTongHop.setEnabled(true);
		} else {
			groupExcel.setEnabled(true);
			groupTongHop.setEnabled(false);
		}
	}

	@Override
	public boolean validateUI() {
		// group 1
		if (radFact1.isSelected()) {
			if (sfFact.getSelectedIndex() < 0) {
				setErrorMessage("Chọn xưởng");
				return false;
			}
		}
		// group 2
		if (!chkTV.isSelected() && !chkBV.isSelected()) {
			setErrorMessage("Chọn trạng thái nghỉ việc");
			return false;
		}
		
		String month="",year="";
		if (radMonthYear.isSelected()){
			month=sfMonth.getSelectedItem().toString();
			year=sfYear.getSelectedItem().toString();
			if (!FvGenericDAO.getInstanse().checkTableExist("ATTQUIT"+year+month)){
				setErrorMessage("Không có bảng lương tháng "+ month+"/"+year);
				return false;
			}
		}else{
			Calendar cal = dfDotTV.getSelectedDate();
			int m = cal.get(Calendar.MONTH);
			int y = cal.get(Calendar.YEAR);
			if (radDot1.isSelected()){				
				if (m==0) {
					m = 12;
					y = y-1;
				}
				month = (m<10?"0"+m:""+m);
				year=y+"";
				if (!FvGenericDAO.getInstanse().checkTableExist("ATTQUIT"+year+month)){
					setErrorMessage("Không có bảng lương tháng "+ month+"/"+year);
					return false;
				}
			}else if (radDot2.isSelected()){
				m=m+1;
				month = (m<10?"0"+m:""+m);
				year=y+"";
				if (!FvGenericDAO.getInstanse().checkTableExist("ATTQUIT"+year+month)){
					setErrorMessage("Không có bảng lương tháng "+ month+"/"+year);
					return false;
				}
			}else if (radDot3.isSelected()){
				if (m==0) {
					m = 12;
					y = y-1;
				}
				month = (m<10?"0"+m:""+m);
				year=y+"";
				if (!FvGenericDAO.getInstanse().checkTableExist("ATTQUIT"+year+month)){
					setErrorMessage("Không có bảng lương tháng "+ month+"/"+year);
					return false;
				}
				//
				m=m+1;
				if (m==13) {
					m=1;
					y = y+1;
				}
				month = (m<10?"0"+m:""+m);
				
				if (!FvGenericDAO.getInstanse().checkTableExist("ATTQUIT"+year+month)){
					setErrorMessage("Không có bảng lương tháng "+ month+"/"+year);
					return false;
				}
			}
		}
		
		return true;
	}

	private HSSFWorkbook loadTemplate(String name) {
		File file = ReportFileManager.getReportFormatFolder("fvhr/" + name);
		try {
			FileInputStream in = new FileInputStream(file);
			HSSFWorkbook wb = new HSSFWorkbook(in);
			return wb;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void initHeaderInfo(){
		if (radMonthYear.isSelected()){
			monthyear = sfMonth.getSelectedItem() + "/" + sfYear.getSelectedItem();
			dotThoiViec="";
		}else{
			Calendar cal = dfDotTV.getSelectedDate();
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);
			if (radDot1.isSelected()){				
				if (month==0) {
					month = 12;
					year = year-1;
				}
				monthyear = (month<10?"0"+month:""+month) + "/" + year;
			}else if (radDot2.isSelected()){
				month=month+1;
				monthyear = (month<10?"0"+month:""+month) + "/" + year;
			}else if (radDot3.isSelected()){
				if (month==0) {
					month = 12;
					year = year-1;
				}
				monthyear = (month<10?"0"+month:""+month) + "/" + year;
				month=month+1;
				if (month==13) {
					month=1;
					year=year+1;
				}
				monthyear = monthyear + " + " + (month<10?"0"+month:""+month) + "/" + year;
			}
			dotThoiViec = new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime());
		}
		fact = sfFact.getSelectedItem().toString();
		if (fact.equals("FVL")){
			if (groupFVL.getSelectedItem()!=null&&groupFVL.getSelectedItem().length>0){
				fact = "";
				for (String s:groupFVL.getSelectedItem()){
					fact = fact + "," + s;
				}
				fact = "FVL-" + StringUtils.substringAfter(fact, ",");
			}
		}
	}

	@Override
	protected HSSFWorkbook generateWorkbook() throws IOException {
		HSSFWorkbook wb = null;
		HSSFSheet sheet;
		HSSFRow row;
		HSSFCell cell;
		int startRow;
		bindParams();
		initHeaderInfo();
		if (radExcel.isSelected()) {
			if (radTongLuongTV.isSelected()) {
				List<ATTQUIT> list = listData(EXCEL_LUONG);
				if (list.size() <= 0)
					return null;
				wb = loadTemplate("Luong.xls");
				sheet = wb.getSheetAt(0);
				/////Fill header
				row = sheet.getRow(0);
				cell = row.getCell(0);
				String s1 = cell.getStringCellValue();
				s1 = s1.replace("$", monthyear);
				cell.setCellValue(s1);
				row = sheet.getRow(1);
				cell = row.getCell(0);
				String s2 = cell.getStringCellValue();				
				s2 = s2 + dotThoiViec + "(" + fact + ")";
				cell.setCellValue(s2);
				////////////////
				startRow = 5;
				QexcelLuongFiller filler = new QexcelLuongFiller();
				filler.setDivideBy(1000);
				for (ATTQUIT data : list) {
					row = sheet.createRow(startRow);
					cell = row.createCell(0);// so thu tu
					cell.setCellValue(startRow - 5);
					String name = data.getEMPNA();
					cell = row.createCell(2);// ho ten lot
					cell.setCellValue(StringUtils
							.substringBeforeLast(name, " "));
					cell = row.createCell(3);// ten
					cell.setCellValue(StringUtils.substringAfterLast(name," "));
					filler.fill(data, row);
					startRow++;
				}
				setReportFileName("Tong_Luong_Thoi_Viec");
				return wb;
			} else if (radTongTroCap.isSelected()) {
				List<ATTQUIT> list = listData(EXCEL_TROCAP);
				if (list.size() <= 0)
					return null;
				wb = loadTemplate("TroCap.xls");
				sheet = wb.getSheetAt(0);
////			/Fill header
				row = sheet.getRow(0);
				cell = row.getCell(0);
				String s1 = cell.getStringCellValue();
				s1 = s1.replace("$", monthyear).replace("!", fact);
				cell.setCellValue(s1);
				row = sheet.getRow(1);
				cell = row.getCell(0);
				String s2 = cell.getStringCellValue();				
				s2 = s2.replace("$", dotThoiViec);
				cell.setCellValue(s2);
				////////////////
				QexcelTroCapFiller filler = new QexcelTroCapFiller();
				filler.setDivideBy(1000);
				startRow = 6;
				for (ATTQUIT data : list) {
					row = sheet.createRow(startRow);
					String name = data.getEMPNA();
					cell = row.createCell(1);// ho ten lot
					cell.setCellValue(StringUtils
							.substringBeforeLast(name, " "));
					cell = row.createCell(2);// ten
					cell
							.setCellValue(StringUtils.substringAfterLast(name,
									" "));
					filler.fill(data, row);
					startRow++;
				}
				setReportFileName("TroCap");
				return wb;

			} else if (radBangThanhToan.isSelected()) {
				List<ATTQUIT> list = listData(EXCEL_THANHTOAN);
				if (list.size() <= 0)
					return null;
				wb = loadTemplate("ThanhToan.xls");
				sheet = wb.getSheetAt(0);
////			/Fill header
//				row = sheet.getRow(0);
//				cell = row.getCell(0);
//				String s1 = cell.getStringCellValue();
//				s1 = s1.replace("$", monthyear);
//				cell.setCellValue(s1);
				row = sheet.getRow(1);
				cell = row.getCell(0);
				String s2 = cell.getStringCellValue();				
				s2 = s2.replace("$", dotThoiViec);
				cell.setCellValue(s2);
				////////////////
				QexcelThanhToanFiller filler = new QexcelThanhToanFiller();
				filler.setDivideBy(1000);
				startRow = 5;
				for (ATTQUIT data : list) {
					row = sheet.createRow(startRow);
					cell = row.createCell(0);// so thu tu
					cell.setCellValue(startRow - 4);
					String name = data.getEMPNA();
					cell = row.createCell(2);// ho ten lot
					cell.setCellValue(StringUtils
							.substringBeforeLast(name, " "));
					cell = row.createCell(3);// ten
					cell
							.setCellValue(StringUtils.substringAfterLast(name,
									" "));
					filler.fill(data, row);
					startRow++;
				}
				setReportFileName("ThanhToan");
				return wb;
			} else if (radBangKyNhan.isSelected()) {
				List<ATTQUIT> list = listData(EXCEL_KYNHAN);
				if (list.size() <= 0)
					return null;
				wb = loadTemplate("KyNhan.xls");
				sheet = wb.getSheetAt(0);
				/////Fill header
				row = sheet.getRow(0);
				cell = row.getCell(0);
				String s1 = cell.getStringCellValue();
				s1 = s1.replace("$", monthyear);
				cell.setCellValue(s1);
				row = sheet.getRow(1);
				cell = row.getCell(0);
				String s2 = cell.getStringCellValue();				
				s2 = s2 + dotThoiViec + "(" + fact + ")";
				cell.setCellValue(s2);
				////////////////
				QexcelKyNhanFiller filler = new QexcelKyNhanFiller();
				filler.setDivideBy(1000);
				startRow = 6;
				for (ATTQUIT data : list) {
					row = sheet.createRow(startRow);
					cell = row.createCell(1);// so thu tu
					cell.setCellValue(startRow - 5);
					String name = data.getEMPNA();
					cell = row.createCell(2);// ho ten lot
					cell.setCellValue(StringUtils
							.substringBeforeLast(name, " "));
					cell = row.createCell(3);// ten
					cell
							.setCellValue(StringUtils.substringAfterLast(name,
									" "));
					filler.fill(data, row);
					startRow++;
				}
				setReportFileName("KyNhan");
				return wb;
			}
		} else if (radTonghop.isSelected()) {
			if (radBangTong.isSelected()) {
				List<QuitWorkGenericObject> list = getGenericList();
				if (list.size() <= 0)
					return null;
				wb = loadTemplate("BaoCao_TongHop.xls");
				sheet = wb.getSheetAt(0);
				startRow = 2;
				QTongHopFiller filler = new QTongHopFiller();
				for (QuitWorkGenericObject data : list) {
					row = sheet.createRow(startRow);
					filler.fill(data, row);
					startRow++;
				}
				setReportFileName("BaoCao_TongHop");
				return wb;
			} else if (radBHXH.isSelected()) {
				List<ATTQUIT> list = listData(TONG_BHXH);
				if (list.size() <= 0)
					return null;
				wb = loadTemplate("BHXH.xls");
				sheet = wb.getSheetAt(0);
				startRow = 3;
				QTongHopBHXHFiller filler = new QTongHopBHXHFiller();
				for (ATTQUIT data : list) {
					row = sheet.createRow(startRow);
					cell = row.createCell(0);
					cell.setCellValue(startRow - 2);
					String name = data.getEMPNA();
					cell = row.createCell(2);// ho ten lot
					cell.setCellValue(StringUtils
							.substringBeforeLast(name, " "));
					cell = row.createCell(3);// ten
					cell
							.setCellValue(StringUtils.substringAfterLast(name,
									" "));
					filler.fill(data, row);
					startRow++;
				}
				setReportFileName("BHXH");
				return wb;
			} else if (radBHYT.isSelected()) {
				List<ATTQUIT> list = listData(TONG_BHYT);
				if (list.size() <= 0)
					return null;
				wb = loadTemplate("BHYT.xls");
				sheet = wb.getSheetAt(0);
				startRow = 3;
				QTongHopBHYTFiller filler = new QTongHopBHYTFiller();
				for (ATTQUIT data : list) {
					row = sheet.createRow(startRow);
					cell = row.createCell(0);
					cell.setCellValue(startRow - 2);
					String name = data.getEMPNA();
					cell = row.createCell(2);// ho ten lot
					cell.setCellValue(StringUtils
							.substringBeforeLast(name, " "));
					cell = row.createCell(3);// ten
					cell
							.setCellValue(StringUtils.substringAfterLast(name,
									" "));
					filler.fill(data, row);
					startRow++;
				}
				setReportFileName("BHYT");
				return wb;
			} else if (radBHTN.isSelected()) {
				List<ATTQUIT> list = listData(TONG_BHTN);
				if (list.size() <= 0)
					return null;
				wb = loadTemplate("BHTN.xls");
				sheet = wb.getSheetAt(0);
				startRow = 3;
				QTongHopBHTNFiller filler = new QTongHopBHTNFiller();
				for (ATTQUIT data : list) {
					row = sheet.createRow(startRow);
					cell = row.createCell(0);
					cell.setCellValue(startRow - 2);
					String name = data.getEMPNA();
					cell = row.createCell(2);// ho ten lot
					cell.setCellValue(StringUtils
							.substringBeforeLast(name, " "));
					cell = row.createCell(3);// ten
					cell
							.setCellValue(StringUtils.substringAfterLast(name,
									" "));
					filler.fill(data, row);
					startRow++;
				}
				setReportFileName("BHTN");
				return wb;
			} else if (radBuBH.isSelected()) {
				List<ATTQUIT> list = listData(TONG_BUBH);
				if (list.size() <= 0)
					return null;
				wb = loadTemplate("BUBH.xls");
				sheet = wb.getSheetAt(0);
				startRow = 3;
				QTongHopBUBHFiller filler = new QTongHopBUBHFiller();
				for (ATTQUIT data : list) {
					row = sheet.createRow(startRow);
					cell = row.createCell(0);
					cell.setCellValue(startRow - 2);
					String name = data.getEMPNA();
					cell = row.createCell(2);// ho ten lot
					cell.setCellValue(StringUtils
							.substringBeforeLast(name, " "));
					cell = row.createCell(3);// ten
					cell
							.setCellValue(StringUtils.substringAfterLast(name,
									" "));
					filler.fill(data, row);
					startRow++;
				}
				setReportFileName("BUBH");
				return wb;
			}
		}
		// Export excel luong

		return null;
	}

	@Override
	protected JasperPrint generateJasperPrint() throws IOException {
		bindParams();
		List<ATTQUIT> list = listData(PDF_PHIEULUONG);
		if (list == null || list.size() == 0)
			return null;
		int i = 1;
		for (ATTQUIT data : list) {
//			data.setEMPNA(Vni2Uni.convertToUnicode(StringUtils
//					.substringBeforeLast(name, " ")));
//			data.setPOSSN(Vni2Uni.convertToUnicode(StringUtils
//					.substringAfterLast(name, " ")));
			data.setSTT(BigDecimal.valueOf(i));
			data.setDEPSN(Vni2Uni.convertToUnicode(data.getDEPSN()));
			data.setNOTE(Vni2Uni.convertToUnicode(data.getNOTE()));
			i++;
		}
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
		try {
			JasperDesign jd = JRXmlLoader.load(ReportFileManager
					.getReportFormatFolder("fvhr/phieuluongtv.jrxml"));
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, null, ds);
			setReportFileName("PhieuLuong");
			return jp;
		} catch (JRException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected JasperPrint generateATMJasperPrint(int type) throws IOException {
		bindParams();
		List<ATTQUIT> list = listData(type);
		if (list == null || list.size() == 0)
			return null;
		int i = 1;
		for (ATTQUIT data : list) {
//			String name = data.getEMPNA();
//			data.setEMPNA(Vni2Uni.convertToUnicode(StringUtils
//					.substringBeforeLast(name, " ")));
//			data.setPOSSN(Vni2Uni.convertToUnicode(StringUtils
//					.substringAfterLast(name, " ")));
			data.setSTT(BigDecimal.valueOf(i));
			data.setDEPSN(Vni2Uni.convertToUnicode(data.getDEPSN()));
			data.setNOTE(Vni2Uni.convertToUnicode(data.getNOTE()));
			i++;
		}
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
		try {
			JasperDesign jd = JRXmlLoader.load(ReportFileManager
					.getReportFormatFolder("fvhr/phieuluongtv.jrxml"));
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, null, ds);
			setReportFileName("PhieuLuong");
			return jp;
		} catch (JRException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected JasperPrint generateJasperPrint1() throws IOException {
		bindParams();
		List<ATTQUIT> list1 = listData(PDF_PHIEULUONG);
//		String[] atm = {"00020078","00050359","00100046","00110909","00110915","00120241",
//				"02030637","02041639","03040046","03080136","04021320","04050505","04090172",
//				"04101051","05020169","05020462","05030422","05030423","05030939","05030975",
//				"05031698","05032137","05032201","05032839","05032965","05040212","05040831",
//				"05050531","06080533","06090270","07031953","07032155","07050315","07080400",
//				"07080733","07100262","07100998","07101219","07101326","07101417","07110166",
//				"08030309","08030639","08030975","08030985","08040644","08040660","08040737",
//				"08050414","08050430","08050551","08061189","08070416","08071382","08080334",
//				"08081087","08081195","08091181","08100233","08100548","08101034","08101404",
//				"08110140","08111061","09022210","09022375","09040345","09040484","09040488",
//				"09050394","09050917","09051649","09060130","09061249","09061356","09061385",
//				"09090458","09090711","09100310","09100983","09101340","09110231","09120423",
//				"10020130","10030078","10030082","10030170","10030737","10030770","10030872",
//				"10030877","10031584","10031593","10031631","10031730","10031782","10032239",
//				"10032306","10032341","10040168","10040170","10040204","10040338","10040541",
//				"10041228","10041528","10050276","10050415","10050739","10050959","10060083",
//				"10060122","10060136","10060295","10060365","10070098","10070292","10070584",
//				"10070850","10070931","10080114","10080312","10080566","10080718","10081019",
//				"10081110","10081377","10090580","10091269","10100217","10120090","10120241",
//				"10120485","11020022","11020214","11020248","11020554","11020601","11020839",
//				"11021117","11021130","11021252","11021801","11030784","11030916","11030991",
//				"11030992","11031080","11031105","11031219","11031467","11032029","11032143",
//				"11032149","11032440","11032562","11032960","11032965","11033075","11033127",
//				"11033129","11040176","11040217","11040594","11040795","11041295","11041476",
//				"11041510","11041523","11041543","11041624","11041683","11050671","11050734",
//				"11050832","11050848","11050865","11050933","11050972","11051158","11051376",
//				"11051461","11060152","11060158","11060359","11060363","11060671","11060979",
//				"11061114","11070690","11070926","11071327","11071520","11071656","11080384",
//				"11080709","11081087","11081322","11081388","11081414","11090269","11100325",
//				"11100541","11110048","12080340","12090202","12090415","12090576","12091044",
//				"12091141","12091189","12091244","12091452","12100335","12100643","12100744",
//				"12100860","12101009","98100163","98110199"};
		String[] atm = {"07040094","08090994","10031145","10101366","11021145","12090706","12110231","12110254","12110286","12110414","12110669","13010070","13010179"};
		List<String> atms = Arrays.asList(atm);
		List<ATTQUIT> list = new ArrayList<ATTQUIT>();
		for (ATTQUIT data:list1){
			if (atms.contains(data.getEMPSN())){
				list.add(data);
			}
		}
		Collections.sort(list, new Comparator<ATTQUIT>() {

			@Override
			public int compare(ATTQUIT o1, ATTQUIT o2) {
				return o1.getEMPSN().compareTo(o2.getEMPSN());
			}
		});
		if (list == null || list.size() == 0)
			return null;
		System.out.println(list.size());
		int i = 1;
		for (ATTQUIT data : list) {
			String name = data.getEMPNA();
			data.setEMPNA(Vni2Uni.convertToUnicode(StringUtils
					.substringBeforeLast(name, " ")));
			data.setPOSSN(Vni2Uni.convertToUnicode(StringUtils
					.substringAfterLast(name, " ")));
			data.setSTT(BigDecimal.valueOf(i));
			data.setDEPSN(Vni2Uni.convertToUnicode(data.getDEPSN()));
			data.setNOTE(Vni2Uni.convertToUnicode(data.getNOTE()));
			i++;
		}
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
		try {
			JasperDesign jd = JRXmlLoader.load(ReportFileManager
					.getReportFormatFolder("fvhr/phieuluongtv.jrxml"));
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, null, ds);
			setReportFileName("PhieuLuong");
			return jp;
		} catch (JRException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<ATTQUIT> listData(int reportType) {
		if (radQuitDate.isSelected()) {
			if (radDot1.isSelected()) {
				return dao.getReportData(reportType, factCondition, dotTV,
						type, month1, year1);
			} else if (radDot2.isSelected()) {
				return dao.getReportData(reportType, factCondition, dotTV,
						type, month2, year2);
			} else if (radDot3.isSelected()) {
				// merge 2 lists
				List<ATTQUIT> list1 = dao.getReportData(reportType,
						factCondition, dotTV, type, month1, year1);
				List<ATTQUIT> list2 = dao.getReportData(reportType,
						factCondition, dotTV, type, month2, year2);
				return merge2List(list1, list2);
				// sort merged list if necessary
			}
		} else if (radMonthYear.isSelected()) {
			return dao.getReportData(reportType, factCondition, dotTV, type,
					month1, year1);// =month2,year2
		}
		return null;
	}

	private List<ATTQUIT> merge2List(List<ATTQUIT> list1, List<ATTQUIT> list2) {
		List<ATTQUIT> list = new ArrayList<ATTQUIT>();
		for (int i = 0; i < list1.size(); i++) {
			ATTQUIT data1 = list1.get(i);
			for (int j = 0; j < list2.size(); j++) {
				ATTQUIT data2 = list2.get(j);
				if (data1.getEMPSN().equals(data2.getEMPSN())&&data1.getDOT_TV().compareTo(data2.getDOT_TV())==0) {
					merge2Data(data1, data2);
					list2.remove(j);
				}
			}
			list.add(data1);
		}
		// add different
		for (int i = 0; i < list2.size(); i++) {
			list.add(list2.get(i));
		}
		return list;
	}

	private ATTQUIT merge2Data(ATTQUIT data1, ATTQUIT data2) {
		String[] exclude = { "BSALY", "COMBSALY", "STT" };
		List<String> list = Arrays.asList(exclude);
		try {
			Field[] fields = ATTQUIT.class.getDeclaredFields();
			for (Field field : fields) {
				Object obj1 = PropertyUtils.getProperty(data1, field.getName());
				if (obj1 instanceof BigDecimal
						&& !list.contains(field.getName())) {
					Object obj2 = PropertyUtils.getProperty(data2, field
							.getName());
					BigDecimal val = ((BigDecimal) obj1).add((BigDecimal) obj2);
					PropertyUtils.setProperty(data1, field.getName(), val);
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return data1;
	}

	private List<QuitWorkGenericObject> getGenericList() {
		if (radQuitDate.isSelected()) {
			if (radDot1.isSelected()) {
				return dao.getGenericReport(factCondition, dotTV, type, month1,
						year1);
			} else if (radDot2.isSelected()) {
				return dao.getGenericReport(factCondition, dotTV, type, month2,
						year2);
			} else if (radDot3.isSelected()) {
				// merge 2 lists
				List<QuitWorkGenericObject> list1 = dao.getGenericReport(
						factCondition, dotTV, type, month1, year1);
				List<QuitWorkGenericObject> list2 = dao.getGenericReport(
						factCondition, dotTV, type, month2, year2);
				return merge2GList(list1, list2);
			}
		} else {
			return dao.getGenericReport(factCondition, dotTV, type, month1,
					year1);// =month2,year2
		}
		return null;
	}

	private List<QuitWorkGenericObject> merge2GList(
			List<QuitWorkGenericObject> list1, List<QuitWorkGenericObject> list2) {
		List<QuitWorkGenericObject> list = new ArrayList<QuitWorkGenericObject>();
		for (int i = 0; i < list1.size(); i++) {
			QuitWorkGenericObject data1 = list1.get(i);
			for (int j = 0; j < list2.size(); j++) {
				QuitWorkGenericObject data2 = list2.get(j);
				if (data1.equals(data2)) {
					merge2Data(data1, data2);
					list2.remove(j);
				}
			}
			list.add(data1);
		}
		// add different
		for (int i = 0; i < list2.size(); i++) {
			list.add(list2.get(i));
		}
		return list;
	}

	private QuitWorkGenericObject merge2Data(QuitWorkGenericObject data1,
			QuitWorkGenericObject data2) {
		try {
			Field[] fields = QuitWorkGenericObject.class.getDeclaredFields();
			for (Field field : fields) {
				Object obj1 = PropertyUtils.getProperty(data1, field.getName());
				if (obj1 instanceof BigDecimal) {
					Object obj2 = PropertyUtils.getProperty(data2, field
							.getName());
					BigDecimal val = ((BigDecimal) obj1).add((BigDecimal) obj2);
					PropertyUtils.setProperty(data1, field.getName(), val);
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return data1;
	}

	private void bindParams() {
		if (radFact1.isSelected()) {
			String fact = ListBinder.get(sfFact).toString();
			String infvl = "";
			if (fact.equals("FVL")) {
				int chkCount = 0;
				CheckBox chkOther = (CheckBox) groupFVL.getComponent(5);
				if (chkOther.isSelected()) {
					chkCount++;
					CheckBox chk1 = (CheckBox) groupFVL.getComponent(0);
					if (!chk1.isSelected()) {
						infvl = "and (d.name_group not like 'F1%' or d.name_group like 'F12%') ";
					} else
						chkCount++;
					for (int i = 1; i < groupFVL.getComponentCount() - 1; i++) {
						CheckBox chk = (CheckBox) groupFVL.getComponent(i);
						if (!chk.isSelected()) {
							infvl = infvl + "and d.name_group not like 'F"
									+ (i + 1) + "%' ";
						} else {
							chkCount++;
						}
					}
					if (chkCount != 6)
						infvl = "and ("
								+ StringUtils.substringAfter(infvl, "and ")
								+ ")";
					else
						infvl = "";
				} else {
					CheckBox chk1 = (CheckBox) groupFVL.getComponent(0);
					if (chk1.isSelected()) {
						infvl = "or (d.name_group like 'F1%' and d.name_group not like 'F12%')";
					}
					for (int i = 1; i < groupFVL.getComponentCount() - 1; i++) {
						CheckBox chk = (CheckBox) groupFVL.getComponent(i);
						if (chk.isSelected()) {
							infvl = infvl + " or d.name_group like 'F"
									+ (i + 1) + "%'";
						}
					}
					if (!infvl.equals("")) {
						infvl = "and ("
								+ StringUtils.substringAfter(infvl, "or ")
								+ ")";
					}
				}
			}
			factCondition = "and d.name_fact='" + fact + "' " + infvl;
		} else {
			if (radFG1.isSelected()) {
				factCondition = "and (d.name_fact in ('FVL','KDAO') or d.id_dept='TB019') ";
			} else {
				factCondition = "and (d.name_fact='FVS' or d.id_dept='TB020') ";
			}
		}
		if (radMonthYear.isSelected()) {
			month1 = sfMonth.getSelectedItem().toString();
			year1 = sfYear.getSelectedItem().toString();
			month2 = month1;
			year2 = year1;
			dotTV = null;
		} else {
			Calendar cal = dfDotTV.getSelectedDate();
			dotTV = cal;
			int m1 = cal.get(Calendar.MONTH);// month - 1
			year1 = String.valueOf(cal.get(Calendar.YEAR));
			if (m1 == 0) {
				m1 = 12;
				year1 = String.valueOf(cal.get(Calendar.YEAR) - 1);
			}
			month1 = m1 < 10 ? "0" + m1 : "" + m1;
			int m2 = cal.get(Calendar.MONTH) + 1;// month
			month2 = m2 < 10 ? "0" + m2 : "" + m2;
			year2 = String.valueOf(cal.get(Calendar.YEAR));
		}
		if (chkTV.isSelected() && chkBV.isSelected()) {
			type = null;
		} else if (chkTV.isSelected() && !chkBV.isSelected()) {
			type = "TV";
		} else if (!chkTV.isSelected() && chkBV.isSelected()) {
			type = "BV";
		}
	}

	private void initComponents() {
		rootLayout = new Grid();
		rootLayout.setInsets(new Insets(new Extent(3, Extent.PX)));
		add(rootLayout);
		GroupBox groupBox1 = new GroupBox();
		groupBox1.setTitle("Đơn vị");
		groupBox1.setHeight(new Extent(130, Extent.PX));
		GridLayoutData groupBox1LayoutData = new GridLayoutData();
		groupBox1LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBox1.setLayoutData(groupBox1LayoutData);
		rootLayout.add(groupBox1);
		Grid grid2 = new Grid();
		grid2.setInsets(new Insets(new Extent(2, Extent.PX)));
		groupBox1.add(grid2);
		radFact1 = new RadioButton();
		radFact1.setSelected(true);
		radFact1.setText("Xưởng");
		ButtonGroup donvi = new ButtonGroup();
		radFact1.setGroup(donvi);
		radFact1.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		radFact1.setActionCommand("xuong");
		radFact1.setForeground(new Color(0x008080));
		radFact1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				donviChanged(e);
			}
		});
		grid2.add(radFact1);
		sfFact = new SelectField();
		sfFact.setDisabledBackground(new Color(0x808080));
		sfFact.addActionListener(new ActionListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				sfFactoriesChanged(e);
			}
		});
		grid2.add(sfFact);
		groupFVL = new DscGroupCheckBox();
		groupFVL.setEnabled(false);
		groupFVL.setSize(3);
		GridLayoutData groupFVLLayoutData = new GridLayoutData();
		groupFVLLayoutData.setInsets(new Insets(new Extent(15, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		groupFVLLayoutData.setColumnSpan(2);
		groupFVL.setLayoutData(groupFVLLayoutData);
		grid2.add(groupFVL);
		CheckBox chkFv1 = new CheckBox();
		chkFv1.setText("FV1");
		chkFv1.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv1);
		CheckBox chkFv2 = new CheckBox();
		chkFv2.setText("FV2");
		chkFv2.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv2);
		CheckBox chkFv3 = new CheckBox();
		chkFv3.setText("FV3");
		chkFv3.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv3);
		CheckBox chkFv4 = new CheckBox();
		chkFv4.setText("FV4");
		chkFv4.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv4);
		CheckBox chkFv5 = new CheckBox();
		chkFv5.setText("FV5");
		chkFv5.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv5);
		CheckBox chkOther = new CheckBox();
		chkOther.setText("Khác");
		chkOther.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkOther);
		radFact2 = new RadioButton();
		radFact2.setText("Nhóm đơn vị");
		radFact2.setGroup(donvi);
		radFact2.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		radFact2.setActionCommand("nhomdv");
		radFact2.setForeground(new Color(0x008080));
		GridLayoutData radFact2LayoutData = new GridLayoutData();
		radFact2LayoutData.setColumnSpan(2);
		radFact2.setLayoutData(radFact2LayoutData);
		radFact2.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				donviChanged(e);
			}
		});
		grid2.add(radFact2);
		radFG1 = new RadioButton();
		radFG1.setEnabled(false);
		radFG1.setSelected(true);
		radFG1.setText("FVL - KDAO - TB019");
		ButtonGroup nhomdv = new ButtonGroup();
		radFG1.setGroup(nhomdv);
		radFG1.setDisabledBackground(new Color(0xc0c0c0));
		GridLayoutData radFG1LayoutData = new GridLayoutData();
		radFG1LayoutData.setInsets(new Insets(new Extent(15, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		radFG1LayoutData.setColumnSpan(2);
		radFG1.setLayoutData(radFG1LayoutData);
		grid2.add(radFG1);
		radFG2 = new RadioButton();
		radFG2.setEnabled(false);
		radFG2.setText("FVS - TB020");
		radFG2.setGroup(nhomdv);
		radFG2.setDisabledBackground(new Color(0xc0c0c0));
		GridLayoutData radFG2LayoutData = new GridLayoutData();
		radFG2LayoutData.setInsets(new Insets(new Extent(15, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		radFG2LayoutData.setColumnSpan(2);
		radFG2.setLayoutData(radFG2LayoutData);
		grid2.add(radFG2);
		GroupBox groupBox2 = new GroupBox();
		groupBox2.setTitle("Bảng lương");
		groupBox2.setHeight(new Extent(130, Extent.PX));
		GridLayoutData groupBox2LayoutData = new GridLayoutData();
		groupBox2LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBox2.setLayoutData(groupBox2LayoutData);
		rootLayout.add(groupBox2);
		Grid grid3 = new Grid();
		grid3.setInsets(new Insets(new Extent(2, Extent.PX)));
		grid3.setSize(2);
		groupBox2.add(grid3);
		rowMonthYear = new Row();
		GridLayoutData rowMonthYearLayoutData = new GridLayoutData();
		rowMonthYearLayoutData.setColumnSpan(2);
		rowMonthYear.setLayoutData(rowMonthYearLayoutData);
		grid3.add(rowMonthYear);
		radMonthYear = new RadioButton();
		radMonthYear.setText("Tháng");
		ButtonGroup bangluong = new ButtonGroup();
		radMonthYear.setGroup(bangluong);
		radMonthYear.setFont(new Font(null, Font.BOLD,
				new Extent(10, Extent.PT)));
		radMonthYear.setActionCommand("thangnam");
		radMonthYear.setForeground(new Color(0x008080));
		radMonthYear.addActionListener(new ActionListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				bangluongChanged(e);
			}
		});
		rowMonthYear.add(radMonthYear);
		sfMonth = new SelectField();
		sfMonth.setEnabled(false);
		sfMonth.setWidth(new Extent(40, Extent.PX));
		sfMonth.setDisabledBackground(new Color(0xc0c0c0));
		rowMonthYear.add(sfMonth);
		Label label1 = new Label();
		label1.setText("Năm");
		rowMonthYear.add(label1);
		sfYear = new SelectField();
		sfYear.setEnabled(false);
		sfYear.setWidth(new Extent(60, Extent.PX));
		sfYear.setDisabledBackground(new Color(0xc0c0c0));
		rowMonthYear.add(sfYear);
		radQuitDate = new RadioButton();
		radQuitDate.setSelected(true);
		radQuitDate.setText("Đợt TV");
		radQuitDate.setGroup(bangluong);
		radQuitDate
				.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		radQuitDate.setActionCommand("dottv");
		radQuitDate.setForeground(new Color(0x008080));
		radQuitDate.addActionListener(new ActionListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				bangluongChanged(e);
			}
		});
		grid3.add(radQuitDate);
		dfDotTV = new DscDateField();
		grid3.add(dfDotTV);
		rowDotTV = new Row();
		GridLayoutData rowDotTVLayoutData = new GridLayoutData();
		rowDotTVLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		rowDotTVLayoutData.setColumnSpan(2);
		rowDotTV.setLayoutData(rowDotTVLayoutData);
		grid3.add(rowDotTV);
		radDot1 = new RadioButton();
		radDot1.setText("Đợt 1");
		ButtonGroup dottv = new ButtonGroup();
		radDot1.setGroup(dottv);
		radDot1.setDisabledBackground(new Color(0xc0c0c0));
		rowDotTV.add(radDot1);
		radDot2 = new RadioButton();
		radDot2.setSelected(true);
		radDot2.setText("Đợt 2");
		radDot2.setGroup(dottv);
		radDot2.setDisabledBackground(new Color(0xc0c0c0));
		rowDotTV.add(radDot2);
		radDot3 = new RadioButton();
		radDot3.setText("Cả 2");
		radDot3.setGroup(dottv);
		radDot3.setDisabledBackground(new Color(0xc0c0c0));
		rowDotTV.add(radDot3);
		Label label2 = new Label();
		label2.setText("---------------------------");
		GridLayoutData label2LayoutData = new GridLayoutData();
		label2LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		label2LayoutData.setColumnSpan(2);
		label2.setLayoutData(label2LayoutData);
		grid3.add(label2);
		Row row2 = new Row();
		GridLayoutData row2LayoutData = new GridLayoutData();
		row2LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		row2LayoutData.setColumnSpan(2);
		row2.setLayoutData(row2LayoutData);
		grid3.add(row2);
		chkTV = new CheckBox();
		chkTV.setSelected(true);
		chkTV.setText("Thôi việc");
		chkTV.setForeground(new Color(0x004000));
		row2.add(chkTV);
		chkBV = new CheckBox();
		chkBV.setText("Bỏ việc");
		chkBV.setForeground(new Color(0x004000));
		row2.add(chkBV);
		GroupBox groupBox3 = new GroupBox();
		groupBox3.setTitle("Loại báo cáo");
		GridLayoutData groupBox3LayoutData = new GridLayoutData();
		groupBox3LayoutData.setColumnSpan(2);
		groupBox3.setLayoutData(groupBox3LayoutData);
		rootLayout.add(groupBox3);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(2, Extent.PX)));
		grid1.setSize(1);
		groupBox3.add(grid1);
		radTonghop = new RadioButton();
		radTonghop.setText("Báo cáo tổng hợp");
		ButtonGroup loaibc = new ButtonGroup();
		radTonghop.setGroup(loaibc);
		radTonghop
				.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		radTonghop.setActionCommand("bctonghop");
		radTonghop.setForeground(new Color(0x008080));
		radTonghop.addActionListener(new ActionListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				baocaoChanged(e);
			}
		});
		grid1.add(radTonghop);
		groupTongHop = new DscGroupRadioButton();
		groupTongHop.setEnabled(false);
		GridLayoutData groupTongHopLayoutData = new GridLayoutData();
		groupTongHopLayoutData.setInsets(new Insets(new Extent(15, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		groupTongHop.setLayoutData(groupTongHopLayoutData);
		grid1.add(groupTongHop);
		radBangTong = new RadioButton();
		radBangTong.setText("Bảng tổng");
		radBangTong.setSelected(true);
		radBangTong.setDisabledBackground(new Color(0xc0c0c0));
		GridLayoutData radBangTongLayoutData = new GridLayoutData();
		radBangTongLayoutData.setColumnSpan(2);
		radBangTong.setLayoutData(radBangTongLayoutData);
		groupTongHop.add(radBangTong);
		radBHXH = new RadioButton();
		radBHXH.setText("DS CNV mua BHXH");
		radBHXH.setDisabledBackground(new Color(0xc0c0c0));
		groupTongHop.add(radBHXH);
		radBHYT = new RadioButton();
		radBHYT.setText("DS CNV mua BHYT");
		radBHYT.setDisabledBackground(new Color(0xc0c0c0));
		groupTongHop.add(radBHYT);
		radBHTN = new RadioButton();
		radBHTN.setText("DS CNV mua BHTN");
		radBHTN.setDisabledBackground(new Color(0xc0c0c0));
		groupTongHop.add(radBHTN);
		radBuBH = new RadioButton();
		radBuBH.setText("DS CNV được bù BH");
		radBuBH.setDisabledBackground(new Color(0xc0c0c0));
		groupTongHop.add(radBuBH);
		radExcel = new RadioButton();
		radExcel.setSelected(true);
		radExcel.setText("Xuất dữ liệu ra excel");
		radExcel.setGroup(loaibc);
		radExcel.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		radExcel.setActionCommand("bcexcel");
		radExcel.setForeground(new Color(0x008080));
		radExcel.addActionListener(new ActionListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				baocaoChanged(e);
			}
		});
		grid1.add(radExcel);
		groupExcel = new DscGroupRadioButton();
		GridLayoutData groupExcelLayoutData = new GridLayoutData();
		groupExcelLayoutData.setInsets(new Insets(new Extent(15, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		groupExcel.setLayoutData(groupExcelLayoutData);
		grid1.add(groupExcel);
		radTongLuongTV = new RadioButton();
		radTongLuongTV.setSelected(true);
		radTongLuongTV.setText("Tổng lương thôi việc");
		radTongLuongTV.setDisabledBackground(new Color(0xc0c0c0));
		groupExcel.add(radTongLuongTV);
		radTongTroCap = new RadioButton();
		radTongTroCap.setText("Tổng trợ cấp");
		radTongTroCap.setDisabledBackground(new Color(0xc0c0c0));
		groupExcel.add(radTongTroCap);
		radBangThanhToan = new RadioButton();
		radBangThanhToan.setText("Bảng thanh toán");
		radBangThanhToan.setDisabledBackground(new Color(0xc0c0c0));
		groupExcel.add(radBangThanhToan);
		radBangKyNhan = new RadioButton();
		radBangKyNhan.setText("Bảng ký nhận");
		radBangKyNhan.setDisabledBackground(new Color(0xc0c0c0));
		groupExcel.add(radBangKyNhan);
	}
}
