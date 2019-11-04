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
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_NIKE_CALENDAR;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import echopointng.ComboBox;

import fv.components.SelectItem;
import fv.components.SimpleReportProgram;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.MappingPropertyUtils;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.button.ButtonGroup;

public class BaoCaoTangCa extends SimpleReportProgram {

	private ResourceBundle resourceBundle;
	private SplitPane splitPane;
	private SplitPane conditionSplitPane;
	private SelectField sfFact;
	private SelectField sfLean;
	private SelectField sfDept;
	private RadioButton radBC;
	private RadioButton radCR;
	private RadioButton radRS;
	private RadioButton radBCThang;
	private RadioButton radQTQuaGio;
	private RadioButton radTTGT;
	private int optIndex=0;
	public static final int BCTH_INDEX=0;
	public static final int OTTRACK_INDEX=1;
	public static final int DAILYOT_INDEX=2;
	public static final int MONTHLYOT_INDEX=3;
	public static final int OTIC_INDEX=4;
	public static final int TTGT_INDEX=5;
	private SelectField sfMonth;
	private SelectField sfYear;
	private DscDateField dfDate1, dfDate2, dfDate;
	private ComboBox cboWeek;
	private RadioButton radCB, radCN, radCBCN, radTTGTByFac, radTTGTByDept;
	private DscField txtHour1, txtHour2;
	private RadioButton radSundayOT;
	private N_NIKE_CALENDAR currentWeek;
	private MappingPropertyEditor nikeCalendarEditor;
	private Row rowCBCN;
	private Grid grid1;
	private SplitPane paramSplitPane;
	private Grid grid2;
	private Grid grid4;
	private Grid grid3;
	private Label lblDate1;
	private Label lblDate2;
	private Label lblSpace;
	private SelectField sfNikeGroupID;
	private ReportDao dao;
	private RadioButton radHour;
	private ReportDataTable<?> controller;
	private OTSalaryDataTable controller1;
	private OTTrackDataTable controller2;
	private OTTrackDataTable controller3;
	private MonthOTDataTable controller4;
	private ICWeekOTDataTable controller5;
	private TTGTDataTable controller6;
	
	/**
	 * Creates a new <code>BaoCaoTangCa</code>.
	 */
	public BaoCaoTangCa() {
		super();
		initComponents();
		sfMonth = new SelectField();
		sfMonth.setWidth(new Extent(43));
		ListBinder.bindSelectField(sfMonth, MappingPropertyUtils.getStringMonthEditor(), true);
		sfYear = new SelectField();
		sfYear.setWidth(new Extent(60));
		ListBinder.bindSelectField(sfYear, MappingPropertyUtils.getYearEditor(10, false), true);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		sfMonth.setSelectedIndex(cal.get(Calendar.MONTH));
		ListBinder.refreshIndex(sfYear, Integer.valueOf(cal.get(Calendar.YEAR)));
		dfDate = new DscDateField();
		dfDate.getDateChooser().setLocale(new Locale("en"));
		dfDate.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dfDate.setSelectedDate(Calendar.getInstance());
		dfDate1 = new DscDateField();
		dfDate1.getDateChooser().setLocale(new Locale("en"));
		dfDate1.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.DAY_OF_MONTH, 1);
		dfDate1.setSelectedDate(cal1);
		dfDate2 = new DscDateField();
		dfDate2.getDateChooser().setLocale(new Locale("en"));
		dfDate2.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.DAY_OF_MONTH, cal2.getActualMaximum(Calendar.DAY_OF_MONTH));
		dfDate2.setSelectedDate(cal2);
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
		ButtonGroup g1 = new ButtonGroup();
		radCB = new RadioButton("Cán bộ");
		radCB.setGroup(g1);
		radCN = new RadioButton("Công nhân");
		radCN.setGroup(g1);
		radCBCN  = new RadioButton("Cán bộ - công nhân");
		radCBCN.setSelected(true);
		radCBCN.setGroup(g1);
		rowCBCN = new Row();
		rowCBCN.setCellSpacing(new Extent(3));
		rowCBCN.add(radCB);
		rowCBCN.add(radCN);
		rowCBCN.add(radCBCN);
		ButtonGroup g2 = new ButtonGroup();
		radTTGTByFac = new RadioButton("Tổng hợp xưởng");
		radTTGTByFac.setGroup(g2);
		radTTGTByFac.setSelected(true);
		radTTGTByDept = new RadioButton("Chi tiết đơn vị");
		radTTGTByDept.setGroup(g2);
		txtHour1 = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtHour1.setMaximumLength(2);
		txtHour1.setWidth(new Extent(20));
		txtHour1.setText("49");
		txtHour1.setDisabledBackground(Color.LIGHTGRAY);
		txtHour2 = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtHour2.setMaximumLength(2);
		txtHour2.setWidth(new Extent(20));
		txtHour2.setText("70");
		txtHour2.setDisabledBackground(Color.LIGHTGRAY);
		dao = new ReportDao();
		optUIConstruct();
	}
	
	@Override
	protected int doInit() {
		int ret = super.doInit();
		getToolbar().setSearchButtonEnable(false);
		getToolbar().setConfigButtonEnable(false);
		getToolbar().setPdfButtonEnable(false);
		return ret;
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
	
	@SuppressWarnings("unchecked")
	private MappingPropertyEditor getNikeGroupEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(N_DEPARTMENT.class);
		List list = dao.find(500, "select distinct ID_GROUPNI from N_DEPARTMENT where ID_GROUPNI<>null order by ID_GROUPNI");
		for (int i=0;i<list.size();i++){
			e.put(list.get(i));
		}
		return e;
	}
	
	@Override
	protected void doReset() {
		sfFact.setSelectedIndex(-1);
		sfLean.setSelectedIndex(-1);
		((DefaultListModel)sfLean.getModel()).removeAll();
		sfDept.setSelectedIndex(-1);
		((DefaultListModel)sfDept.getModel()).removeAll();
		if (sfNikeGroupID!=null)
			sfNikeGroupID.setSelectedIndex(-1);
	}
	
	private void optUIConstruct(){
		if (optIndex==BCTH_INDEX){
			if (paramSplitPane.getComponentCount()==2) paramSplitPane.remove(1);
			paramSplitPane.add(grid1());
			rowCBCN.setVisible(true);
			if (splitPane.getComponentCount()==2) splitPane.remove(1);
			if (controller1==null) controller1 = new OTSalaryDataTable(dao);
			controller=controller1;
			splitPane.add(controller.getTable());
			firePropertyChange(null, null, null);
		}else if (optIndex==OTTRACK_INDEX){
			if (paramSplitPane.getComponentCount()==2) paramSplitPane.remove(1);
			paramSplitPane.add(grid2());
			if (splitPane.getComponentCount()==2) splitPane.remove(1);
			if (controller2==null) {
				controller2 = new OTTrackDataTable(dao);
				controller2.setCR(true);
			}
			controller=controller2;
			splitPane.add(controller.getTable());
		}else if (optIndex==DAILYOT_INDEX){
			if (paramSplitPane.getComponentCount()==2) paramSplitPane.remove(1);
			paramSplitPane.add(grid2());
			if (splitPane.getComponentCount()==2) splitPane.remove(1);
			if (controller3==null) {
				controller3 = new OTTrackDataTable(dao);
				controller3.setCR(false);
			}
			controller=controller3;
			splitPane.add(controller.getTable());
		}else if (optIndex==MONTHLYOT_INDEX){
			if (paramSplitPane.getComponentCount()==2) paramSplitPane.remove(1);
			paramSplitPane.add(grid1());
			rowCBCN.setVisible(false);
			if (splitPane.getComponentCount()==2) splitPane.remove(1);
			if (controller4==null) controller4 = new MonthOTDataTable(dao);
			controller=controller4;
			splitPane.add(controller.getTable());
			firePropertyChange(null,  null, null);
		}else if (optIndex==OTIC_INDEX){
			if (paramSplitPane.getComponentCount()==2) paramSplitPane.remove(1);
			paramSplitPane.add(grid3());
			if (splitPane.getComponentCount()==2) splitPane.remove(1);
			if (controller5==null) controller5 = new ICWeekOTDataTable(dao);
			controller=controller5;
			splitPane.add(controller.getTable());
		}else if (optIndex==TTGT_INDEX){
			if (paramSplitPane.getComponentCount()==2) paramSplitPane.remove(1);
			paramSplitPane.add(grid4());
			if (splitPane.getComponentCount()==2) splitPane.remove(1);
			if (controller6==null) controller6 = new TTGTDataTable(dao);
			controller=controller6;
			splitPane.add(controller.getTable());
		}
	}
	
	private Map<String, Object> buildParams(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fact", ListBinder.get(sfFact));
		map.put("lean", ListBinder.get(sfLean));
		map.put("dept", ListBinder.get(sfDept));
		if (optIndex==BCTH_INDEX){
			map.put("month", sfMonth.getSelectedItem().toString());
			map.put("year", sfYear.getSelectedItem().toString());
			if (radCB.isSelected()) map.put("CBCN", "CB");
			else if (radCN.isSelected()) map.put("CBCN", "CN");			
		}else if (optIndex==OTTRACK_INDEX){
			map.put("date1", dfDate1.getSelectedDate().getTime());
			map.put("date2", dfDate2.getSelectedDate().getTime());
		}else if (optIndex==DAILYOT_INDEX){
			map.put("date1", dfDate1.getSelectedDate().getTime());
			map.put("date2", dfDate2.getSelectedDate().getTime());
		}else if (optIndex==MONTHLYOT_INDEX){
			map.put("month", sfMonth.getSelectedItem().toString());
			map.put("year", sfYear.getSelectedItem().toString());
		}else if (optIndex==OTIC_INDEX){
			N_NIKE_CALENDAR cal = (N_NIKE_CALENDAR) ListBinder.getCboValue(cboWeek, nikeCalendarEditor);
			map.put("date1", cal.getBEGIN_DATE());
			map.put("date2", cal.getEND_DATE());
			if (sfNikeGroupID.getSelectedIndex()>=0){
				map.put("groupni", ListBinder.get(sfNikeGroupID));
			}
			if (radHour.isSelected()){
				map.put("hour1", Integer.parseInt(txtHour1.getText()));
				map.put("hour2", Integer.parseInt(txtHour2.getText()));
			}
		}else if (optIndex==TTGT_INDEX){
			map.put("date", dfDate.getSelectedDate().getTime());
			if (radTTGTByFac.isSelected()){
				map.put("TT", Boolean.TRUE);
			}else{
				map.put("TT", Boolean.FALSE);
			}
		}
		return map;
	}
	
	private Grid grid1(){
		if (grid1==null) {
			grid1 = new Grid();
			grid1.setInsets(new Insets(3));
			grid1.setSize(4);
			grid1.add(new Label("Tháng"));
			grid1.add(sfMonth);
			grid1.add(new Label("năm"));
			grid1.add(sfYear);
			SplitPaneLayoutData lo = new SplitPaneLayoutData();
			lo.setInsets(new Insets(6));
			grid1.setLayoutData(lo);
			GridLayoutData lo1 = new GridLayoutData();
			lo1.setColumnSpan(4);
			rowCBCN.setLayoutData(lo1);
			grid1.add(rowCBCN);
		}
		return grid1;
	}
	
	private Grid grid2(){
		if (grid2 == null) {
			grid2 = new Grid(4);
			grid2.add(new Label("Từ ngày"));
			grid2.add(dfDate1);
			grid2.add(new Label("Đến ngày"));
			grid2.add(dfDate2);
			grid2.setInsets(new Insets(3));
			SplitPaneLayoutData lo = new SplitPaneLayoutData();
			lo.setInsets(new Insets(12));
			grid2.setLayoutData(lo);
		}
		return grid2;
	}
	
	private Grid grid3(){
		if (grid3==null) {
			grid3 = new Grid(3);
			grid3.setInsets(new Insets(3));
			grid3.add(new Label("Chọn tuần"));
			grid3.add(cboWeek);
			Row r = new Row();
			r.setCellSpacing(new Extent(4));
			lblDate1 = new Label();
			lblDate2 = new Label();
			lblSpace = new Label("~");
			r.add(lblDate1);
			r.add(lblSpace);
			r.add(lblDate2);
			grid3.add(r);
			grid3.add(new Label("Nike group ID"));
			sfNikeGroupID=new SelectField();
			sfNikeGroupID.setWidth(new Extent(158));
			ListBinder.bindSelectField(sfNikeGroupID, getNikeGroupEditor(), false);
			grid3.add(sfNikeGroupID);
			Row row = new Row();
			ButtonGroup g = new ButtonGroup();
			radHour = new RadioButton("Tổng giờ làm việc từ:");
			radHour.setGroup(g);
			radHour.setSelected(true);
			radHour.addActionListener(new ActionListener(){
				private static final long serialVersionUID = 1L;
				@Override
				public void actionPerformed(ActionEvent e) {
					txtHour1.setEnabled(true);
					txtHour2.setEnabled(true);
				}
			});
			row.add(radHour);
			row.add(txtHour1);
			row.add(new Label("đến:"));
			row.add(txtHour2);
			radSundayOT = new RadioButton("Tăng ca chủ nhật");
			radSundayOT.setGroup(g);
			radSundayOT.addActionListener(new ActionListener(){
				private static final long serialVersionUID = 1L;
				@Override
				public void actionPerformed(ActionEvent e) {
					txtHour1.setEnabled(false);
					txtHour2.setEnabled(false);
				}
			});
			row.add(radSundayOT);
			row.setCellSpacing(new Extent(4));
			grid3.add(row);
			SplitPaneLayoutData glo = new SplitPaneLayoutData();
			glo.setInsets(new Insets(6));
			showDate();
		}
		
		return grid3;
	}
	
	private Grid grid4(){
		if (grid4==null){
			grid4 = new Grid();
			grid4.add(new Label("Ngày"));
			grid4.add(dfDate);
			grid4.add(radTTGTByFac);
			grid4.add(radTTGTByDept);
			grid4.setInsets(new Insets(3));
			SplitPaneLayoutData lo = new SplitPaneLayoutData();
			lo.setInsets(new Insets(6));
			grid4.setLayoutData(lo);
		}
		return grid4;
	}
	
	private void showDate(){
		N_NIKE_CALENDAR cal = (N_NIKE_CALENDAR) ListBinder.getCboValue(cboWeek, nikeCalendarEditor);
		if (cal!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			lblDate1.setText(sdf.format(cal.getBEGIN_DATE().getTime()));
			lblDate2.setText(sdf.format(cal.getEND_DATE().getTime()));
		}
	}

	private void rad1selected(ActionEvent e) {
		if (optIndex!=BCTH_INDEX){
			optIndex=BCTH_INDEX;
			optUIConstruct();
			setReportFileName("BaoCao_TCa_BangLuong");
		}
	}

	private void rad2selected(ActionEvent e) {
		if (optIndex!=OTTRACK_INDEX){
			optIndex=OTTRACK_INDEX;
			optUIConstruct();
			setReportFileName("CR_OT_Track_Form");
		}
	}

	private void rad3selected(ActionEvent e) {
		if (optIndex!=DAILYOT_INDEX){
			optIndex=DAILYOT_INDEX;
			optUIConstruct();
			setReportFileName("RS_OT_Track_Form");
		}
	}

	private void rad4selected(ActionEvent e) {
		if (optIndex!=MONTHLYOT_INDEX){
			optIndex=MONTHLYOT_INDEX;
			optUIConstruct();
			setReportFileName("TS_TCa_Thang");
		}
	}

	private void rad5selected(ActionEvent e) {
		if (optIndex!=OTIC_INDEX){
			optIndex=OTIC_INDEX;
			optUIConstruct();
			setReportFileName("Quet_The_Qua_Gio");
		}
	}

	private void rad6selected(ActionEvent e) {
		if (optIndex!=TTGT_INDEX){
			optIndex=TTGT_INDEX;
			optUIConstruct();
			setReportFileName("TrucTiep_GianTiep");
		}
	}
	
	@Override
	public boolean validateUI() {
		if (sfFact.getSelectedIndex()<0){
			setErrorMessage("Chọn xưởng");
			return false;
		}
		if (optIndex==BCTH_INDEX){
			String month = sfMonth.getSelectedItem().toString();
			String year = sfYear.getSelectedItem().toString();
			if (!dao.checkSalaryTable(month, year)){
				setErrorMessage("Không có bảng lương tháng " + month + "/" + year);
				return false;
			}
		}else if (optIndex==OTTRACK_INDEX){
			if (dfDate2.getSelectedDate().compareTo(dfDate1.getSelectedDate())<0){
				setErrorMessage("Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
				return false;
			}
			long days = (dfDate2.getSelectedDate().getTimeInMillis()-dfDate1.getSelectedDate().getTimeInMillis())/(1000*60*60*24);
			if (days>=31){
				setErrorMessage("Khoảng thời gian chọn tối đa là 31 ngày");
				return false;
			}
		}else if (optIndex==DAILYOT_INDEX){
			if (dfDate2.getSelectedDate().compareTo(dfDate1.getSelectedDate())<0){
				setErrorMessage("Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
				return false;
			}
			long days = (dfDate2.getSelectedDate().getTimeInMillis()-dfDate1.getSelectedDate().getTimeInMillis())/(1000*60*60*24);
			if (days>=31){
				setErrorMessage("Khoảng thời gian chọn tối đa là 31 ngày");
				return false;
			}
		}else if (optIndex==MONTHLYOT_INDEX){
			String month = sfMonth.getSelectedItem().toString();
			String year = sfYear.getSelectedItem().toString();
			if (!dao.checkSalaryTable(month, year)){
				setErrorMessage("Không có bảng lương tháng " + month + "/" + year);
				return false;
			}
		}else if (optIndex==OTIC_INDEX){
			if (cboWeek.getText().equals("")){
				setErrorMessage("Chọn tuần");
				return false;
			}
			if (ListBinder.getCboValue(cboWeek, nikeCalendarEditor)==null){
				setErrorMessage("Chọn tuần hợp lệ");
				return false;
			}			
			if (radHour.isSelected()){
				if (txtHour1.getText().trim().equals("")||txtHour2.getText().trim().equals("")){
					setErrorMessage("Chọn giới hạn giờ");
					return false;
				}
				int hour1 = Integer.parseInt(txtHour1.getText());
				int hour2 = Integer.parseInt(txtHour2.getText());
				if (hour1>hour2){
					setErrorMessage("Giới hạn giờ không hợp lệ");
					return false;
				}
			}
		}
		return true;
	}

	@Override
	protected void doRefresh() {
		if (!validateUI()){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, getErrorMessage());
			return;
		}
		controller.refresh(buildParams());
	}

	@Override
	protected void doSearch() {
	}

	@Override
	protected HSSFWorkbook generateWorkbook() throws IOException {
		return controller.export();
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
		conditionSplitPane.setSeparatorPosition(new Extent(265, Extent.PX));
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/SplitHerzBar.png");
		conditionSplitPane.setSeparatorHorizontalImage(new FillImage(
				imageReference2));
		conditionSplitPane.setResizable(true);
		splitPane.add(conditionSplitPane);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(3, Extent.PX)));
		conditionSplitPane.add(grid1);
		Label label1 = new Label();
		label1.setText("Xưởng");
		grid1.add(label1);
		sfFact = new SelectField();
		sfFact.setWidth(new Extent(210, Extent.PX));
		sfFact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				factChanged(e);
			}
		});
		grid1.add(sfFact);
		Label label2 = new Label();
		label2.setText("Nhóm");
		grid1.add(label2);
		sfLean = new SelectField();
		sfLean.setWidth(new Extent(210, Extent.PX));
		sfLean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				leanChanged(e);
			}
		});
		grid1.add(sfLean);
		Label label3 = new Label();
		label3.setText("Đơn vị");
		grid1.add(label3);
		sfDept = new SelectField();
		sfDept.setWidth(new Extent(210, Extent.PX));
		grid1.add(sfDept);
		paramSplitPane = new SplitPane();
		paramSplitPane.setSeparatorPosition(new Extent(35, Extent.PX));
		paramSplitPane
				.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		conditionSplitPane.add(paramSplitPane);
		Row row1 = new Row();
		row1.setBackground(new Color(0xc0c0c0));
		row1.setCellSpacing(new Extent(1, Extent.PX));
		paramSplitPane.add(row1);
		radBC = new RadioButton();
		radBC.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		radBC.setSelected(true);
		radBC.setText("Báo cáo tổng hợp tháng");
		ButtonGroup opt = new ButtonGroup();
		radBC.setGroup(opt);
		radBC.setWidth(new Extent(120, Extent.PX));
		radBC.setForeground(new Color(0x80ffff));
		RowLayoutData radBCLayoutData = new RowLayoutData();
		radBCLayoutData.setBackground(new Color(0x0080c0));
		radBC.setLayoutData(radBCLayoutData);
		radBC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rad1selected(e);
			}
		});
		row1.add(radBC);
		radCR = new RadioButton();
		radCR.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		radCR.setText("OT Tracking form");
		radCR.setGroup(opt);
		radCR.setWidth(new Extent(120, Extent.PX));
		radCR.setForeground(new Color(0x80ffff));
		RowLayoutData radCRLayoutData = new RowLayoutData();
		radCRLayoutData.setBackground(new Color(0x008080));
		radCR.setLayoutData(radCRLayoutData);
		radCR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rad2selected(e);
			}
		});
		row1.add(radCR);
		radRS = new RadioButton();
		radRS.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		radRS.setText("TCa theo ngày");
		radRS.setGroup(opt);
		radRS.setWidth(new Extent(120, Extent.PX));
		radRS.setForeground(new Color(0x80ffff));
		RowLayoutData radRSLayoutData = new RowLayoutData();
		radRSLayoutData.setBackground(new Color(0x8080c0));
		radRS.setLayoutData(radRSLayoutData);
		radRS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rad3selected(e);
			}
		});
		row1.add(radRS);
		radBCThang = new RadioButton();
		radBCThang.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		radBCThang.setText("TS giờ TCa tháng");
		radBCThang.setGroup(opt);
		radBCThang.setWidth(new Extent(120, Extent.PX));
		radBCThang.setForeground(new Color(0x80ffff));
		RowLayoutData radBCThangLayoutData = new RowLayoutData();
		radBCThangLayoutData.setBackground(new Color(0x804040));
		radBCThang.setLayoutData(radBCThangLayoutData);
		radBCThang.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rad4selected(e);
			}
		});
		row1.add(radBCThang);
		radQTQuaGio = new RadioButton();
		radQTQuaGio.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		radQTQuaGio.setText("Quét thẻ quá giờ");
		radQTQuaGio.setGroup(opt);
		radQTQuaGio.setWidth(new Extent(120, Extent.PX));
		radQTQuaGio.setForeground(new Color(0x80ffff));
		RowLayoutData radQTQuaGioLayoutData = new RowLayoutData();
		radQTQuaGioLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		radQTQuaGioLayoutData.setBackground(new Color(0xff8000));
		radQTQuaGio.setLayoutData(radQTQuaGioLayoutData);
		radQTQuaGio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rad5selected(e);
			}
		});
		row1.add(radQTQuaGio);
		radTTGT = new RadioButton();
		radTTGT
				.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		radTTGT.setText("Trực tiếp - Gián tiếp");
		radTTGT.setGroup(opt);
		radTTGT.setWidth(new Extent(120, Extent.PX));
		radTTGT.setForeground(new Color(0x80ffff));
		RowLayoutData radTTGTLayoutData = new RowLayoutData();
		radTTGTLayoutData.setBackground(new Color(0x008040));
		radTTGT.setLayoutData(radTTGTLayoutData);
		radTTGT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rad6selected(e);
			}
		});
		row1.add(radTTGT);
	}
}
