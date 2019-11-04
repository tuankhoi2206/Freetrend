package ds.program.fvhr.ui.hrreport;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import fv.components.MrBeanBrowserContent;
import fv.components.SelectItem;
import fv.components.SimpleReportProgram;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.ReportUtils;
//import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.layout.GridLayoutData;
import ds.program.fvhr.dao.hrreport.ReportDao;
import ds.program.fvhr.domain.N_NIKE_CALENDAR;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.button.ButtonGroup;
import dsc.echo2app.component.DscField;
import nextapp.echo2.app.CheckBox;
import dsc.echo2app.component.DscGroupCheckBox;
import dsc.echo2app.component.table.PageableSortableTableModel;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ActionEvent;
import echopointng.GroupBox;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import echopointng.ComboBox;

public class BaoCaoTongHop extends SimpleReportProgram {

	private ResourceBundle resourceBundle;
	private DscDateField dfFromDate;
	private DscDateField dfToDate;
	private RadioButton radWeek;
	private RadioButton radDate;
	private DscField txtEmpsn;
	private SelectField sfFact;
	private DscGroupCheckBox groupFVL;
	private CheckBox chkFv1;
	private CheckBox chkFv2;
	private CheckBox chkFv3;
	private CheckBox chkFv4;
	private CheckBox chkFv5;
	private CheckBox chkOther;
	private SelectField sfLean;
	private SelectField sfDept;
	private RadioButton radDept;
	private DscField txtParam;
	private RadioButton radEmpsn;
	private CheckBox chkWorkingTime;
	private RadioButton radDayOT;
	private RadioButton radWeekOT;
	private RadioButton radDayToDayOT;
	private RadioButton radVSOT;
	private ComboBox cboWeek;
	private MappingPropertyEditor nikeCalendar;
	private SimpleDateFormat dateFormat;
	private N_NIKE_CALENDAR currentWeek;
	private SplitPane splitPane1;
	private MrBeanBrowserContent browserContent1;
	private MrBeanBrowserContent browserContent2;
	private ReportDao dao;
	private RadioButton radLate15;
	/**
	 * Creates a new <code>BaoCaoTongHop</code>.
	 */
	public BaoCaoTongHop() {
		super();
		initComponents();
		additionInit();
		BCTHBuilder builder = new BCTHTableBuilder();
		browserContent1 = builder.getTable(BCTHBuilder.TYPE1);
		browserContent2 = builder.getTable(BCTHBuilder.TYPE2);
		splitPane1.add(browserContent1);
	}
	
	@Override
	protected int doInit() {
		int ret = super.doInit();
		getToolbar().setSearchButtonEnable(false);
		getToolbar().setConfigButtonEnable(false);
		getToolbar().setPdfButtonEnable(false);
		return ret;
	}
	
	private void additionInit(){
		cboWeek.getTextField().setDisabledBackground(new Color(0xc0c0c0));
		dfFromDate.getTextField().setDisabledBackground(new Color(0xc0c0c0));
		dfToDate.getTextField().setDisabledBackground(new Color(0xc0c0c0));
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dfFromDate.setDateFormat(dateFormat);
		dfToDate.setDateFormat(dateFormat);		
		dfFromDate.getTextField().setText("");
		dfToDate.getTextField().setText("");
		dfFromDate.getDateChooser().setLocale(new Locale("en"));
		dfToDate.getDateChooser().setLocale(new Locale("en"));
		nikeCalendar = getNikeCalendarEditor();
		ListBinder.bindComboBox(cboWeek, nikeCalendar);
		if (currentWeek!=null){
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(currentWeek.getBEGIN_DATE());
			dfFromDate.setSelectedDate(cal1);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(currentWeek.getEND_DATE());
			dfToDate.setSelectedDate(cal2);
			cboWeek.setText(currentWeek.getTIME_NO());
		}
		ListBinder.bindSelectField(sfFact, FVGenericInfo.getFactories(), false);
		sfFact.setEnabled(false);
	}
	
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
	
	@Override
	public boolean validateUI() {
		try{
			dateFormat.parse(dfFromDate.getText());
			dateFormat.parse(dfToDate.getText());
		}catch(Exception e){
			setErrorMessage("Ngày tháng không hợp lệ");
			return false;
		}
		if (dfFromDate.getSelectedDate().compareTo(dfToDate.getSelectedDate())>0){
			setErrorMessage("Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
			return false;
		}
		if (txtParam.getText().equals("")){
			setErrorMessage("Chọn số giờ cần kiểm tra");
			return false;
		}
		if (radEmpsn.isSelected()&&!txtEmpsn.getText().matches("[0-9]{8}")){
			setErrorMessage("Số thẻ không hợp lệ");
			return false;
		}
		if (radDept.isSelected()&&sfFact.getSelectedIndex()<0){
			setErrorMessage("Chọn xưởng");
			return false;
		}
		return true;
	}
	
	public ReportDao getDao(){
		if (dao==null)
			dao = new ReportDao();
		return dao;
	}
	
	@Override
	protected void doRefresh() {
		if (!validateUI()){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, getErrorMessage());
			return;
		}
		StopWatch sw = new StopWatch();
		sw.start();
		String type;
		if (radDayOT.isSelected()||radVSOT.isSelected()||radLate15.isSelected()){
			type=BCTHBuilder.TYPE1;
			if (splitPane1.getComponent(1) != browserContent1){
				splitPane1.remove(1);
				splitPane1.add(browserContent1);
			}
		}else{
			type=BCTHBuilder.TYPE2;
			if (splitPane1.getComponent(1) != browserContent2){
				splitPane1.remove(1);
				splitPane1.add(browserContent2);
			}
		}
		//apply data
		String sql = buildSql();
		System.out.println("Main query: " + sql);
		java.sql.Date date1 = new java.sql.Date(dfFromDate.getSelectedDate().getTimeInMillis());
		java.sql.Date date2 = new java.sql.Date(dfToDate.getSelectedDate().getTimeInMillis());
		sw.split();
		List<BCTH> list = getDao().getListBCTH(type, sql, new Object[]{date1,date2}, 
				dfFromDate.getSelectedDate().getTime(), dfToDate.getSelectedDate().getTime());
		MrBeanBrowserContent br = (MrBeanBrowserContent) splitPane1.getComponent(1);
		br.setListData(list);
		br.refresh();
		sw.stop();
		System.out.println("Refresh duration: " + (float)sw.getTime()/1000 + " s");
	}
	
	@Override
	protected void doSearch() {
	}
	
	private String buildSql(){
		String shift = " and t.id_shift in (select s.id_shift from n_shift s where s.id_shift=t.id_shift and s.note='7H')";
		Double param = Double.parseDouble(txtParam.getText());
		String sql1 = 
			"select t.empsn, e.fname||' '||e.lname as empna, t.dates, t.id_shift as shift, " +
			"(select d.name_dept from n_department d where d.id_dept=e.depsn) as depsn, " + 
			"t.real_ot+t.ots+t.oth as real_ot, t.otd+t.otn+t.ots+t.oth as pay_ot " + 
			"from n_data_daily t, n_employee e " + 
			"where t.empsn=e.empsn" + buildCondition() + 
			//tu >param , sang >= , 28/08/2013
			" and t.dates>=? and t.dates<=? and t.real_ot+t.ots+t.oth>=" + param;
		String r1="sum(t.real_ot+t.ots+t.oth) as real_ot, sum(t.otd+t.otn+t.ots+t.oth) as pay_ot ";
		String r2="sum(t.real_ot+t.ots+t.oth)+sum((t.ducls+t.nucls)*8) as real_ot, sum(t.otd+t.otn+t.ots+t.oth)+sum((t.ducls+t.nucls)*8) as pay_ot ";
		
		//bo sung 28/08/2013, HA
		String sql11="select t.empsn, e.fname||' '||e.lname as empna, t.dates, t.id_shift as shift, " +
		"(select d.name_dept from n_department d where d.id_dept=e.depsn) as depsn, " +
		"t.t_in tIn,t.t_mid tMid,t.t_out tOut,t.t_over tOver,"+
		"t.real_ot+t.ots+t.oth as real_ot, t.otd+t.otn+t.ots+t.oth as pay_ot,t.note "+
		",(select d.id_group_direct from n_department d where d.id_dept=t.depsn) as ttgt "+
		"from n_data_daily t, n_employee e " + 
		"where t.empsn=e.empsn" + buildCondition() + 
		" and t.dates>=? and t.dates<=? ";
		String sql11Cond1= " and t.real_ot+t.ots+t.oth>=" + param;
		String sql11Cond2=" and t.late15>="+param;
		//end 28/08/2013
		
		String sql21 = 
			//"select empsn, fname||' '||lname as empna, (select d.name_dept from n_department d where d.id_dept=depsn) as name_dept, " +
			"select empsn, fname||' '||lname as empna, (select d.name_dept from n_department d where d.id_dept=depsn) as depsn, " +
			"real_ot, pay_ot from ( " + 
			"select t.empsn,e.fname,e.lname,e.depsn, ";
		String sql22 = 
			"from n_data_daily t, n_employee e " + 
			"where t.empsn=e.empsn" + buildCondition() + 
			" and t.dates>=? and t.dates<=?" + 
			" group by t.empsn,e.fname,e.lname,e.depsn";
		//tu >param , sang >= , 28/08/2013
		String sql2Cond1 =  " having sum(t.real_ot+t.ots+t.oth)>="  + param +")";
		String sql2Cond2 =  " having sum(t.real_ot+t.ots+t.oth)+sum((t.ducls+t.nucls)*8)>="  + param +")";
		//if (radDayOT.isSelected()) return sql1+sql11Cond1;
		if (radDayOT.isSelected()) return sql11+sql11Cond1; 
		else if (radWeekOT.isSelected()) {
			if (chkWorkingTime.isSelected())
				return sql21 + r2 + sql22 + sql2Cond2;
			else
				return sql21 + r1 + sql22 + sql2Cond1;
		}
		else if (radDayToDayOT.isSelected()) return sql21 + r1 + sql22 + ")";
		//else if (radVSOT.isSelected()) return sql1+sql11Cond1 + shift;
		else if (radVSOT.isSelected()) return sql11+sql11Cond1 + shift;
		else if (radLate15.isSelected()) return sql11+sql11Cond2 ;
		return "";
	}
	
	private String buildCondition(){
		String str;
		if (radEmpsn.isSelected()){			
			str = " and e.empsn = '" + txtEmpsn.getText() + "'";
		}else{
			str = " and e.depsn in (select d.id_dept from n_department d where d.id_dept=e.depsn ";
			String fact = ListBinder.get(sfFact).toString();
			String infvl = getGroupFVLConditionString();			
			str = str + " and d.name_fact='" + fact + "' ";
			if (infvl.equals("")){
				if (sfLean.getSelectedIndex()>=0){
					str = str + "and d.name_group='" + ListBinder.get(sfLean).toString() + "'";
				}
				if (sfDept.getSelectedIndex()>=0){
					str = str + "and d.name_dept_name='" + ListBinder.get(sfDept).toString() + "'";
				}
			}else{
				str = str + infvl;
			}
			str = str + ")";
		}
		return str;
	}
	
	private String getGroupFVLConditionString(){
		String infvl = "";
		String fact = ListBinder.get(sfFact).toString();
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
						infvl = infvl + "and d.name_group not like 'F" + (i + 1) + "%' ";
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
						infvl = infvl + " or d.name_group like 'F" + (i + 1) + "%'";
					}
				}
				if (!infvl.equals("")) {
					infvl = " and ("
						+ StringUtils.substringAfter(infvl, "or ")
						+ ")";
				}
			}
		}
		return infvl;
	}
	
	@Override
	protected HSSFWorkbook generateWorkbook() throws IOException {
		MrBeanBrowserContent br = (MrBeanBrowserContent) splitPane1.getComponent(1);
		PageableSortableTableModel model = (PageableSortableTableModel) br.getDataTable().getModel();
		if (model.getTotalRows()<=0) return null;
		//HSSFWorkbook wb = ReportUtils.loadTemplate("fvhr", "BCTH.xls");
		HSSFWorkbook wb = ReportUtils.loadTemplate("fvhr", "BCTH092013.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		row = sheet.getRow(1);//header
		if (br==browserContent1){
			cell = row.getCell(3);//tu ngay
			cell.setCellValue("Ngày");
			cell = row.getCell(4);//den ngay
			cell.setCellValue("Ca");
		}else{
			cell = row.getCell(3);//tu ngay
			cell.setCellValue("Từ ngày");
			cell = row.getCell(4);//den ngay
			cell.setCellValue("Đến ngày");
		}
		for (int i=0;i<model.getTotalRows();i++){
			row = sheet.createRow(i+2);
			//for (int j=0;j<7;j++){
			// vi tang column
			for (int j=0;j<13;j++){
				cell = row.createCell(j);
				Object obj = model.getValueAtAbsolute(j, i);
				if (obj!=null){
					if (obj instanceof Number)
						cell.setCellValue(((BigDecimal)obj).doubleValue());
					else
						cell.setCellValue(String.valueOf(obj));
				}
			}
		}
		setReportFileName("BaoCaoTongHop");
		return wb;
	}

	protected void sfLeanChanged(ActionEvent e) {
		SelectItem item = (SelectItem) sfFact.getSelectedItem();
		SelectItem litem = (SelectItem) sfLean.getSelectedItem();
		ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue(),litem.getValue()), true);
	}

	protected void sfFactChanged(ActionEvent e) {
		SelectItem item = (SelectItem) sfFact.getSelectedItem();
		if (item.getValue().equals("FVL")){
			groupFVL.setEnabled(true);
			groupFVL.setBackground(null);
		}else{
			groupFVL.setEnabled(false);
			groupFVL.setBackground(new Color(0xc0c0c0));
			for (int i=0;i<groupFVL.getButtons().length;i++){
				CheckBox chk = groupFVL.getButtons()[i];
				chk.setSelected(false);
			}
		}
		ListBinder.bindSelectField(sfLean, FVGenericInfo.getGroup(item.getValue()), true);
		ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue()), true);
	}

	private void calendarSelected(ActionEvent e) {
		N_NIKE_CALENDAR cal = (N_NIKE_CALENDAR) ListBinder.getCboValue(cboWeek, nikeCalendar);
		if (cal!=null){
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(cal.getBEGIN_DATE());
			dfFromDate.setSelectedDate(cal1);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(cal.getEND_DATE());
			dfToDate.setSelectedDate(cal2);
		}else{
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Chỉ chọn tuần có trong danh sách");
		}
	}

	private void optWeekTimeChanged(ActionEvent e) {
		if (e.getActionCommand().equals("w1")){
			cboWeek.setEnabled(true);
			dfFromDate.setEnabled(false);
			dfToDate.setEnabled(false);
		}else{
			cboWeek.setEnabled(false);
			dfFromDate.setEnabled(true);
			dfToDate.setEnabled(true);
		}
	}

	private void empSelected(ActionEvent e) {
		if (e.getActionCommand().equals("emp")){
			txtEmpsn.setEnabled(true);
			sfFact.setEnabled(false);
			sfLean.setEnabled(false);
			sfDept.setEnabled(false);
			groupFVL.setEnabled(false);
		}else{
			txtEmpsn.setEnabled(false);
			sfFact.setEnabled(true);
			sfLean.setEnabled(true);
			sfDept.setEnabled(true);
			if (sfFact.getSelectedIndex()>0&&sfFact.getSelectedItem().toString().equals("FVL"))
				groupFVL.setEnabled(true);
			else
				groupFVL.setEnabled(false);
		}
	}

	private void optSelected(ActionEvent e) {
		if (e.getActionCommand().equals("op2")){
			chkWorkingTime.setEnabled(true);
			txtParam.setEnabled(true);
		}else if (e.getActionCommand().equals("op3")){
			txtParam.setEnabled(false);
			chkWorkingTime.setEnabled(false);
			chkWorkingTime.setSelected(false);
		}else{
			txtParam.setEnabled(true);
			chkWorkingTime.setEnabled(false);
			chkWorkingTime.setSelected(false);
		}
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(225, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/SplitVertBar.png");
		splitPane1.setSeparatorVerticalImage(new FillImage(imageReference1));
		splitPane1.setResizable(true);
		add(splitPane1);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(3, Extent.PX)));
		SplitPaneLayoutData grid1LayoutData = new SplitPaneLayoutData();
		grid1LayoutData.setBackground(new Color(0x87bad6));
		grid1.setLayoutData(grid1LayoutData);
		grid1.setSize(4);
		splitPane1.add(grid1);
		radWeek = new RadioButton();
		radWeek.setSelected(true);
		radWeek.setText("Tuần báo cáo");
		ButtonGroup date_group = new ButtonGroup();
		radWeek.setGroup(date_group);
		radWeek.setActionCommand("w1");
		radWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optWeekTimeChanged(e);
			}
		});
		grid1.add(radWeek);
		cboWeek = new ComboBox();
		cboWeek.setBackground(Color.WHITE);
		cboWeek.setWidth(new Extent(180, Extent.PX));
		cboWeek.setActionOnSelection(true);
		GridLayoutData cboWeekLayoutData = new GridLayoutData();
		cboWeekLayoutData.setColumnSpan(2);
		cboWeek.setLayoutData(cboWeekLayoutData);
		cboWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendarSelected(e);
			}
		});
		grid1.add(cboWeek);
		GroupBox groupBox1 = new GroupBox();
		groupBox1.setTitle("Lựa chọn báo cáo");
		GridLayoutData groupBox1LayoutData = new GridLayoutData();
		groupBox1LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBox1LayoutData.setRowSpan(7);
		groupBox1.setLayoutData(groupBox1LayoutData);
		grid1.add(groupBox1);
		Grid grid2 = new Grid();
		grid2.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(14,
				Extent.PX)));
		grid2.setSize(1);
		groupBox1.add(grid2);
		radDayOT = new RadioButton();
		radDayOT.setSelected(true);
		radDayOT.setText("Kiểm tra tăng ca theo ngày");
		ButtonGroup opt = new ButtonGroup();
		radDayOT.setGroup(opt);
		radDayOT.setActionCommand("op1");
		radDayOT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		grid2.add(radDayOT);
		radWeekOT = new RadioButton();
		radWeekOT.setText("Kiểm tra tăng ca theo tuần");
		radWeekOT.setGroup(opt);
		radWeekOT.setActionCommand("op2");
		radWeekOT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		grid2.add(radWeekOT);
		radDayToDayOT = new RadioButton();
		radDayToDayOT.setText("TS tăng ca từ ngày đến ngày");
		radDayToDayOT.setGroup(opt);
		radDayToDayOT.setActionCommand("op3");
		radDayToDayOT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		grid2.add(radDayToDayOT);
		radVSOT = new RadioButton();
		radVSOT.setText("CNV con nhỏ / bầu 7 tháng VS");
		radVSOT.setGroup(opt);
		radVSOT.setActionCommand("op4");
		radVSOT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		grid2.add(radVSOT);
		radLate15 = new RadioButton();
		radLate15.setText("Sớm/ Trễ 15 phút theo ngày");
		radLate15.setGroup(opt);
		radLate15.setActionCommand("op4");
		radLate15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		grid2.add(radLate15);
		Row row2 = new Row();
		row2.setCellSpacing(new Extent(3, Extent.PX));
		GridLayoutData row2LayoutData = new GridLayoutData();
		row2LayoutData.setColumnSpan(4);
		row2.setLayoutData(row2LayoutData);
		grid1.add(row2);
		radDate = new RadioButton();
		radDate.setText("Thời gian từ");
		radDate.setGroup(date_group);
		radDate.setActionCommand("w2");
		radDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optWeekTimeChanged(e);
			}
		});
		row2.add(radDate);
		dfFromDate = new DscDateField();
		dfFromDate.setEnabled(false);
		row2.add(dfFromDate);
		Label label4 = new Label();
		label4.setText("đến");
		row2.add(label4);
		dfToDate = new DscDateField();
		dfToDate.setEnabled(false);
		row2.add(dfToDate);
		Label label1 = new Label();
		label1.setText("Thông số");
		GridLayoutData label1LayoutData = new GridLayoutData();
		label1LayoutData.setInsets(new Insets(new Extent(24, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		label1.setLayoutData(label1LayoutData);
		grid1.add(label1);
		txtParam = new DscField();
		txtParam.setText("1");
		txtParam.setInputType(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtParam.setWidth(new Extent(180, Extent.PX));
		txtParam.setDisabledBackground(new Color(0xc0c0c0));
		txtParam.setMaximumLength(4);
		grid1.add(txtParam);
		chkWorkingTime = new CheckBox();
		chkWorkingTime.setEnabled(false);
		chkWorkingTime.setText("Giờ làm việc");
		chkWorkingTime.setDisabledBackground(new Color(0xc0c0c0));
		grid1.add(chkWorkingTime);
		radEmpsn = new RadioButton();
		radEmpsn.setSelected(true);
		radEmpsn.setText("Số thẻ");
		ButtonGroup dept_opt = new ButtonGroup();
		radEmpsn.setGroup(dept_opt);
		radEmpsn.setActionCommand("emp");
		radEmpsn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empSelected(e);
			}
		});
		grid1.add(radEmpsn);
		txtEmpsn = new DscField();
		txtEmpsn.setInputType(DscField.INPUT_TYPE_TEXT);
		txtEmpsn.setWidth(new Extent(180, Extent.PX));
		txtEmpsn.setDisabledBackground(new Color(0xc0c0c0));
		txtEmpsn.setMaximumLength(8);
		GridLayoutData txtEmpsnLayoutData = new GridLayoutData();
		txtEmpsnLayoutData.setColumnSpan(2);
		txtEmpsn.setLayoutData(txtEmpsnLayoutData);
		grid1.add(txtEmpsn);
		radDept = new RadioButton();
		radDept.setText("Xưởng");
		radDept.setGroup(dept_opt);
		radDept.setActionCommand("fac");
		radDept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empSelected(e);
			}
		});
		grid1.add(radDept);
		sfFact = new SelectField();
		sfFact.setEnabled(false);
		sfFact.setWidth(new Extent(180, Extent.PX));
		sfFact.setDisabledBackground(new Color(0xc0c0c0));
		sfFact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sfFactChanged(e);
			}
		});
		grid1.add(sfFact);
		groupFVL = new DscGroupCheckBox();
		groupFVL.setEnabled(false);
		groupFVL.setBackground(new Color(0xc0c0c0));
		groupFVL.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5,
				Extent.PX)));
		groupFVL.setSize(2);
		GridLayoutData groupFVLLayoutData = new GridLayoutData();
		groupFVLLayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupFVLLayoutData.setRowSpan(3);
		groupFVL.setLayoutData(groupFVLLayoutData);
		grid1.add(groupFVL);
		chkFv1 = new CheckBox();
		chkFv1.setText("FV1");
		groupFVL.add(chkFv1);
		chkFv2 = new CheckBox();
		chkFv2.setText("FV2");
		groupFVL.add(chkFv2);
		chkFv3 = new CheckBox();
		chkFv3.setText("FV3");
		groupFVL.add(chkFv3);
		chkFv4 = new CheckBox();
		chkFv4.setText("FV5");
		groupFVL.add(chkFv4);
		chkFv5 = new CheckBox();
		chkFv5.setText("FV6");
		groupFVL.add(chkFv5);
		chkOther = new CheckBox();
		chkOther.setText("Khác");
		groupFVL.add(chkOther);
		Label label6 = new Label();
		label6.setText("Lean");
		GridLayoutData label6LayoutData = new GridLayoutData();
		label6LayoutData.setInsets(new Insets(new Extent(24, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		label6.setLayoutData(label6LayoutData);
		grid1.add(label6);
		sfLean = new SelectField();
		sfLean.setEnabled(false);
		sfLean.setWidth(new Extent(180, Extent.PX));
		sfLean.setDisabledBackground(new Color(0xc0c0c0));
		sfLean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sfLeanChanged(e);
			}
		});
		grid1.add(sfLean);
		Label label8 = new Label();
		label8.setText("Đơn vị");
		GridLayoutData label8LayoutData = new GridLayoutData();
		label8LayoutData.setInsets(new Insets(new Extent(24, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		label8.setLayoutData(label8LayoutData);
		grid1.add(label8);
		sfDept = new SelectField();
		sfDept.setEnabled(false);
		sfDept.setWidth(new Extent(180, Extent.PX));
		sfDept.setDisabledBackground(new Color(0xc0c0c0));
		grid1.add(sfDept);
	}
}
