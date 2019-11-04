package ds.program.fvhr.ui.hrreport;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import ds.program.fvhr.dao.hrreport.ReportDao;
import ds.program.fvhr.domain.N_NIKE_CALENDAR;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import echopointng.ComboBox;
import fv.components.SelectItem;
import fv.components.SimpleReportProgram;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.button.ButtonGroup;

public class OvertimeReport extends SimpleReportProgram {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ResourceBundle resourceBundle;
	private SplitPane splitPane;
	private SelectField sfFact;
	private SelectField sfLean;
	private SelectField sfDept;
	private SplitPane conditionSplitPane;
	private RadioButton radDayOTReport;
	private RadioButton radWeekOTReport;
	private RadioButton radYearOTReport;
	private Label lblDate;
	private ReportDataTable<?> browserController;
	private DscDateField dateField;
	private ComboBox cboWeek;
	private N_NIKE_CALENDAR currentWeek;
	private MappingPropertyEditor nikeCalendarEditor;
	private ReportDao dao;
	private DayOTDataTable dayOTBrowserController;
	private Row paramRow;
	private WeekOTDataTable weekOTBrowserController;
	private YearOTDataTable yearOTBrowserController;
	private int currentClick=0;
	private Label lblDate1;
	private Label lblDate2;
	private Label lblSpace;

	/**
	 * Creates a new <code>OvertimeReport</code>.
	 */
	public OvertimeReport() {
		super();

		// Add design-time configured components.
		initComponents();
		dateField = new DscDateField();
		dateField.getDateChooser().setLocale(new Locale("en"));
		dateField.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dateField.setSelectedDate(Calendar.getInstance());
		cboWeek = new ComboBox();
		cboWeek.setPopUpAlwaysOnTop(true);
		cboWeek.setActionOnSelection(true);
		cboWeek.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				showDate();
			}
		});
		nikeCalendarEditor = getNikeCalendarEditor();
		ListBinder.bindSelectField(sfFact, FVGenericInfo.getFactories(), false);
		ListBinder.bindComboBox(cboWeek, nikeCalendarEditor);
		cboWeek.setText(currentWeek.getTIME_NO());
		showDate();
		showDayOTBr(null);
	}
	
	@Override
	protected int doInit() {
		int ret = super.doInit();
		getToolbar().setSearchButtonEnable(false);
		getToolbar().setConfigButtonEnable(false);
		getToolbar().setPdfButtonEnable(false);
		return ret;
	}
	
	@Override
	public boolean validateUI() {
		if (sfFact.getSelectedIndex()<0){
			setErrorMessage("Chọn xưởng");
			return false;
		}
		if (!radDayOTReport.isSelected()){
			if (cboWeek.getText().equals("")){
				setErrorMessage("Chọn tuần");
				return false;
			}
			if (ListBinder.getCboValue(cboWeek, nikeCalendarEditor)==null){
				setErrorMessage("Chọn tuần hợp lệ");
				return false;
			}
		}
		return true;
	}
	
	@Override
	protected void doReset() {
		sfFact.setSelectedIndex(-1);
		sfLean.setSelectedIndex(-1);
		sfDept.setSelectedIndex(-1);
		((DefaultListModel)sfLean.getModel()).removeAll();
		((DefaultListModel)sfDept.getModel()).removeAll();
	}

	@Override
	protected void doRefresh() {
		if (!validateUI()){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, getErrorMessage());
			return;
		}
//		StopWatch sw = new StopWatch();
//		sw.start();
		browserController.refresh(buildParams());
//		sw.stop();
//		System.out.println("Refresh time: " + (float)sw.getTime()/1000);
	}
	
	@SuppressWarnings("unchecked")
	private MappingPropertyEditor getNikeCalendarEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_NIKE_CALENDAR, String> dao = Application.getApp().getDao(N_NIKE_CALENDAR.class);
		List<N_NIKE_CALENDAR> list = dao.findAll(1000);
		for (N_NIKE_CALENDAR data:list){
			e.put(data.getTIME_NO(), data);
		}
		List<N_NIKE_CALENDAR> list2 = dao.find(1, "from N_NIKE_CALENDAR t where t.END_DATE<? order by t.END_DATE desc", new Object[]{new Date()});
		if (list2.size()>0){
			currentWeek = list2.get(0);
		}
		return e;
	}
	

	private Map<String, Object> buildParams() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fact", ListBinder.get(sfFact));
		map.put("lean", ListBinder.get(sfLean));
		map.put("dept", ListBinder.get(sfDept));
		if (radDayOTReport.isSelected()){
			map.put("date", dateField.getSelectedDate().getTime());
		}else if (radWeekOTReport.isSelected()){
			N_NIKE_CALENDAR cal = (N_NIKE_CALENDAR) ListBinder.getCboValue(cboWeek, nikeCalendarEditor);
			map.put("date1", cal.getBEGIN_DATE());
			map.put("date2", cal.getEND_DATE());
			map.put("timeNo", cal.getTIME_NO());
		}else if (radYearOTReport.isSelected()){
			N_NIKE_CALENDAR cal = (N_NIKE_CALENDAR) ListBinder.getCboValue(cboWeek, nikeCalendarEditor);
			Date date = cal.getBEGIN_DATE();
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(date.getTime());
			c.set(c.get(Calendar.YEAR), 0, 1);
			map.put("date", c.getTime());
			map.put("date1", cal.getBEGIN_DATE());
			map.put("date2", cal.getEND_DATE());
			map.put("timeNo", cal.getTIME_NO());
		}
		return map;
	}

	@Override
	protected void doSearch() {
	}
	

	@Override
	protected HSSFWorkbook generateWorkbook() throws IOException {
		if (radDayOTReport.isSelected()) setReportFileName("Daily_OT");
		else if (radWeekOTReport.isSelected()) setReportFileName("Weekly_OT");
		else if (radYearOTReport.isSelected()) setReportFileName("Yearly_OT");
		return browserController.export();
	}

	private void factChanged(ActionEvent e) {
		SelectItem item = (SelectItem) sfFact.getSelectedItem();
		ListBinder.bindSelectField(sfLean, FVGenericInfo.getGroup(item.getValue()), true);
		ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue()), true);
	}

	private void leanChanged(ActionEvent e) {
		SelectItem item = (SelectItem) sfFact.getSelectedItem();
		SelectItem litem = (SelectItem) sfLean.getSelectedItem();
		ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue(),litem.getValue()), true);
	}
	
	private void showDate(){
		N_NIKE_CALENDAR cal = (N_NIKE_CALENDAR) ListBinder.getCboValue(cboWeek, nikeCalendarEditor);
		if (cal!=null){
			paramRow.remove(lblDate1);
			paramRow.remove(lblDate2);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			lblDate1.setText(sdf.format(cal.getBEGIN_DATE().getTime()));
			lblDate2.setText(sdf.format(cal.getEND_DATE().getTime()));
			paramRow.add(lblDate1);
			paramRow.add(lblSpace);
			paramRow.add(lblDate2);
		}
	}
	
	private void clearCurrentClick() {
		if (currentClick==1){
			dayOTBrowserController.getModel().getRows().clear();
		} else if (currentClick==2){
			weekOTBrowserController.getModel().getRows().clear();
		}else if (currentClick==3){
			yearOTBrowserController.getModel().getRows().clear();
		}
	}

	private void showDayOTBr(ActionEvent e) {
		if (currentClick!=1){
//			clearCurrentClick();
			currentClick=1;
			lblDate.setText("Date");
			paramRow.removeAll();
			paramRow.add(lblDate);
			paramRow.add(dateField);
			if (dao==null)
				dao = new ReportDao();
			if (dayOTBrowserController==null) {
				dayOTBrowserController = new DayOTDataTable(dao);
			}
			
			browserController = dayOTBrowserController;//switch
			if (splitPane.getComponentCount()==2)
				splitPane.remove(1);
			splitPane.add(dayOTBrowserController.getTable());
		}
	}

	private void showWeekOTBr(ActionEvent e) {
		if (currentClick!=2){
//			clearCurrentClick();
			currentClick=2;
			lblDate.setText("Change Week");
			paramRow.removeAll();
			paramRow.add(lblDate);
			paramRow.add(cboWeek);
			showDate();
			if (dao==null)
				dao = new ReportDao();
			if (weekOTBrowserController==null) {
				weekOTBrowserController = new WeekOTDataTable(dao);
			}
			browserController = weekOTBrowserController;
			if (splitPane.getComponentCount()==2)
				splitPane.remove(1);
			splitPane.add(weekOTBrowserController.getTable());
		}
	}

	private void showYearOTBr(ActionEvent e) {
		if (currentClick!=3){
//			clearCurrentClick();
			currentClick=3;
			lblDate.setText("Change Week");
			paramRow.removeAll();
			paramRow.add(lblDate);
			paramRow.add(cboWeek);
			showDate();
			if (dao==null)
				dao = new ReportDao();
			if (yearOTBrowserController==null) {
				yearOTBrowserController = new YearOTDataTable(dao);
			}
			browserController = yearOTBrowserController;
			if (splitPane.getComponentCount()==2)
				splitPane.remove(1);
			splitPane.add(yearOTBrowserController.getTable());
		}
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		splitPane = new SplitPane();
		splitPane.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/SplitVertBar.png");
		splitPane.setSeparatorVerticalImage(new FillImage(imageReference1));
		splitPane.setResizable(true);
		add(splitPane);
		conditionSplitPane = new SplitPane();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/SplitHerzBar.png");
		conditionSplitPane.setSeparatorHorizontalImage(new FillImage(
				imageReference2));
		conditionSplitPane.setSeparatorPosition(new Extent(350, Extent.PX));
		conditionSplitPane.setResizable(true);
		splitPane.add(conditionSplitPane);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(3, Extent.PX)));
		conditionSplitPane.add(grid1);
		Label label1 = new Label();
		label1.setText("Factory");
		grid1.add(label1);
		sfFact = new SelectField();
		sfFact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				factChanged(e);
			}
		});
		grid1.add(sfFact);
		Label label2 = new Label();
		label2.setText("Group");
		grid1.add(label2);
		sfLean = new SelectField();
		sfLean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				leanChanged(e);
			}
		});
		grid1.add(sfLean);
		Label label3 = new Label();
		label3.setText("Department");
		grid1.add(label3);
		sfDept = new SelectField();
		grid1.add(sfDept);
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(25, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		conditionSplitPane.add(splitPane1);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(3, Extent.PX));
		SplitPaneLayoutData row1LayoutData = new SplitPaneLayoutData();
		row1LayoutData.setBackground(new Color(0x8080c0));
		row1LayoutData.setInsets(new Insets(new Extent(3, Extent.PX)));
		row1LayoutData.setOverflow(SplitPaneLayoutData.OVERFLOW_HIDDEN);
		row1.setLayoutData(row1LayoutData);
		splitPane1.add(row1);
		radDayOTReport = new RadioButton();
		radDayOTReport.setSelected(true);
		radDayOTReport.setText("Báo cáo ngày");
		ButtonGroup opt = new ButtonGroup();
		radDayOTReport.setGroup(opt);
		radDayOTReport.setForeground(new Color(0x80ffff));
		radDayOTReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showDayOTBr(e);
			}
		});
		row1.add(radDayOTReport);
		radWeekOTReport = new RadioButton();
		radWeekOTReport.setText("Báo cáo tuần");
		radWeekOTReport.setGroup(opt);
		radWeekOTReport.setForeground(new Color(0x80ffff));
		radWeekOTReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showWeekOTBr(e);
			}
		});
		row1.add(radWeekOTReport);
		radYearOTReport = new RadioButton();
		radYearOTReport.setText("Báo cáo năm");
		radYearOTReport.setGroup(opt);
		radYearOTReport.setForeground(new Color(0x80ffff));
		radYearOTReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showYearOTBr(e);
			}
		});
		row1.add(radYearOTReport);
		paramRow = new Row();
		paramRow.setCellSpacing(new Extent(5, Extent.PX));
		SplitPaneLayoutData paramRowLayoutData = new SplitPaneLayoutData();
		paramRowLayoutData.setInsets(new Insets(new Extent(24, Extent.PX)));
		paramRow.setLayoutData(paramRowLayoutData);
		splitPane1.add(paramRow);
		lblDate = new Label();
		lblDate.setText("Date/Week");
		paramRow.add(lblDate);
		lblDate1 = new Label();
		paramRow.add(lblDate1);
		lblSpace = new Label();
		lblSpace.setText("~");
		paramRow.add(lblSpace);
		lblDate2 = new Label();
		paramRow.add(lblDate2);
	}
}
