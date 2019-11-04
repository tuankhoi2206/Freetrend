package ds.program.fvhr.tien.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import dsc.dao.IGenericDAO;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import com.sun.org.apache.xerces.internal.impl.dtd.models.DFAContentModel;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ListBox;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.button.AbstractButton;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import ds.program.fvhr.dao.generic.FvGenericDAO;
import ds.program.fvhr.dao.hrreport.ReportDao;
import ds.program.fvhr.domain.N_NIKE_CALENDAR;
import ds.program.fvhr.domain.N_SHIFT;
import ds.program.fvhr.son.ui.DeptUserControl;
import ds.program.fvhr.ui.hrreport.BCTHBuilder;
import ds.program.fvhr.ui.hrreport.BCTHTableBuilder;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscGroupCheckBox;
import dsc.echo2app.component.table.PageableSortableTableModel;
import echopointng.ComboBox;
import echopointng.GroupBox;
import fv.components.MrBeanBrowserContent;
import fv.components.SelectItem;
import fv.components.SimpleReportProgram;
import fv.util.ApplicationHelper;
import fv.util.FVGenericInfo;
import fv.util.HRUtils;
import fv.util.ListBinder;
import fv.util.ReportUtils;

public class BaocaoTonghopOld extends SimpleReportProgram {

	private DscDateField dsDate;
	private Grid rootLayout;
	private SplitPane splitPane1;
	private RadioButton radDitrengay;
	private RadioButton raddiTreThang;
	private RadioButton radDSkhangCong;
	private RadioButton radkhangcongquetthe;
	private DscDateField dfFromDate;
	private DscDateField dfToDate;
	private Label lblPhutTre;
	private DscField txtsophut;
	private RadioButton radDept;
	private SelectField sfFact;
	private SelectField sfLean;
	private SelectField sfDept;
	private RadioButton radSumTangca;
	private RadioButton radNgaynhapvaoHT;
	private RadioButton radHienhanh;
	private GroupBox group2;
	private Grid grid3;
	private MrBeanBrowserContent browserContent1;
	private MrBeanBrowserContent browserContent2;
	Label lblFact;
	private DscDateField dfDate;
	private SimpleDateFormat dateFormat;
	private ReportDao dao;
	private RadioButton radFact;
	private Label lblSum;
	private Grid grid4;
	private RadioButton radDanhsach;
	private ListBox lstEmpsn;
	EMPSNBuilder builder = new EMPSNTableBuilder();
	private MrBeanBrowserContent browserContent3;
	private MrBeanBrowserContent browserContent4;
	private Label lblDate_;
	private Label lblDate;
	private SimpleDateFormat sp = new SimpleDateFormat("dd/MM/yyyy");
	private MrBeanBrowserContent browserContent5;
	private RadioButton radThamnien;
	private MrBeanBrowserContent browserContent6;

	public BaocaoTonghopOld() {
		initComponents();
		additionInit();
		browserContent1 = builder.getTable(EMPSNBuilder.TYPE3);
		browserContent2 = builder.getTable(EMPSNBuilder.TYPE4);
		browserContent3 = builder.getTable(EMPSNBuilder.TYPE5);
		browserContent4 = builder.getTable(EMPSNBuilder.TYPE6);
		browserContent5 = builder.getTable(EMPSNBuilder.TYPE7);
		browserContent6 = builder.getTable(EMPSNBuilder.TYPE8);
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

	public ReportDao getDao() {
		if (dao == null)
			dao = new ReportDao();
		return dao;
	}

	@Override
	protected void doReset() {
		dfDate.getTextField().setText("");
		dfFromDate.getTextField().setText("");
		dfToDate.getTextField().setText("");
		sfFact.setSelectedIndex(-1);
		sfDept.setSelectedIndex(-1);
		sfLean.setSelectedIndex(-1);
		txtsophut.setText("");

	}

	@Override
	protected void doRefresh() {
		if (!validateUI()) {
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK + MessageDialog.TYPE_ERROR,
					getErrorMessage());
			return;
		}
		StopWatch sw = new StopWatch();
		sw.start();
		String sql = buildSql();
		String type = "";
		if (radDitrengay.isSelected()) {
			type = EMPSNBuilder.TYPE3;
			if (splitPane1.getComponent(1) != browserContent1) {
				splitPane1.remove(1);
				splitPane1.add(browserContent1);
			}
		}
		if (radDSkhangCong.isSelected()) {
			type = EMPSNBuilder.TYPE4;
			if (splitPane1.getComponent(1) != browserContent2) {
				splitPane1.remove(1);
				splitPane1.add(browserContent2);
			}
		}
		if (radkhangcongquetthe.isSelected()) {
			type = EMPSNBuilder.TYPE5;
			if (splitPane1.getComponent(1) != browserContent3) {
				splitPane1.remove(1);
				splitPane1.add(browserContent3);
			}
		}
		if (radSumTangca.isSelected()) {
			type = EMPSNBuilder.TYPE6;
			if (splitPane1.getComponent(1) != browserContent4) {
				splitPane1.remove(1);
				splitPane1.add(browserContent4);
			}
		}
		if (raddiTreThang.isSelected()) {
			type = EMPSNBuilder.TYPE7;
			if (splitPane1.getComponent(1) != browserContent5) {
				splitPane1.remove(1);
				splitPane1.add(browserContent5);
			}
		}
		if (radThamnien.isSelected()) {
			type = EMPSNBuilder.TYPE8;
			if (splitPane1.getComponent(1) != browserContent6) {
				splitPane1.remove(1);
				splitPane1.add(browserContent6);
			}
		}

		System.out.println("Main query: " + sql);
		sw.split();
		List<EMPSN_E> list = null;
		java.sql.Date date1 = new java.sql.Date(dfFromDate.getSelectedDate().getTimeInMillis());
		java.sql.Date date2 = new java.sql.Date(dfToDate.getSelectedDate().getTimeInMillis());
		if (raddiTreThang.isSelected() || radThamnien.isSelected()) {
			list = getDao().getListempsnditre(type, sql, new Object[] { 1, 1 });
		} else {
			list = getDao().getListempsnditre(type, sql, new Object[] { date1, date2 });
		}
		MrBeanBrowserContent br = (MrBeanBrowserContent) splitPane1.getComponent(1);
		br.setListData(list);
		br.refresh();
		sw.stop();
		lblSum.setText("Tổng Cộng có :" + list.size() + "CNV");

	}

	@Override
	public boolean validateUI() {
		if (raddiTreThang.isSelected()) {
			try {
				dateFormat.parse(dfDate.getText());
			} catch (ParseException e) {
				setErrorMessage("Ngày tháng không hợp lệ");
				return false;
			}
			if (txtsophut.getText().equals("")) {
				setErrorMessage("Số phút trể bắt buộc phải nhập");
				return false;
			}

		}
		if (!radThamnien.isSelected()) {
			try {
				dateFormat.parse(dfFromDate.getText());
				dateFormat.parse(dfToDate.getText());
			} catch (Exception e) {
				setErrorMessage("Ngày tháng không hợp lệ");
				return false;
			}
			if (dfFromDate.getSelectedDate().compareTo(dfToDate.getSelectedDate()) > 0) {
				setErrorMessage("Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
				return false;
			}
			if (radDitrengay.isSelected()) {
				if (txtsophut.getText().equals("")) {
					setErrorMessage("Số phút trể bắt buộc phải nhập");
					return false;
				}
			}
		}
		if (sfFact.getSelectedIndex() < 0) {
			setErrorMessage("Mời bạn chọn xưởng cần thao tác!");
			return false;
		}
		return true;
	}

	private String buildSql() {

		String sql1 = "";
		String sql2 = "";
		String sql3 = txtsophut.getText();
		if (radDitrengay.isSelected()) {

			sql1 = " and t.dates>=? and t.dates<=? and late>0 group by t.empsn,e.fname||' '||e.lname,d.name_dept, t.dates having sum(t.late)>"
					+ sql3;
			sql2 = "select t.empsn,e.fname||' '||e.lname as empna,d.name_dept  as depsn_new ,t.dates"
					+ ",sum(t.late) AS late from n_data_daily t, n_department d, n_employee e  Where t.empsn=e.empsn and e.depsn=d.id_dept";

			sql2 = sql2 + buildCondition() + sql1;
		}

		if (raddiTreThang.isSelected()) {
			Date date = dfDate.getSelectedDate().getTime();
			String strDate = sp.format(date);
			String strDate_ = strDate.substring(3, 10);
			System.out.println(strDate_);
			sql1 = "  and late>0 and to_char(t.dates,'MM/YYYY') like '%" + strDate_
					+ "%' group by t.empsn,e.fname||' '||e.lname,d.name_dept  having sum(t.late)>" + sql3
					+ " and 1=? and 1=?";
			sql2 = "select t.empsn,e.fname||' '||e.lname as empna,d.name_dept  as depsn_new"
					+ ",sum(t.late) AS late from n_data_daily t, n_department d, n_employee e  Where t.empsn=e.empsn and e.depsn=d.id_dept";

			sql2 = sql2 + buildCondition() + sql1;
		}
		if (radDSkhangCong.isSelected()) {
			sql1 = " and t.dates>=? and t.dates<=? and t.rest_rs='KC' group by t.empsn,e.fname||' '||e.lname,d.name_dept ";
			sql2 = "select t.empsn,e.fname||' '||e.lname as empna,d.name_dept  as depsn_new ,count(t.dates) as rest_rs"
					+ " from n_data_daily t, n_department d, n_employee e  Where t.empsn=e.empsn and e.depsn=d.id_dept";

			sql2 = sql2 + buildCondition() + sql1;
		}
		if (radkhangcongquetthe.isSelected()) {

			sql1 = " and t.dates>=? and t.dates<=? and t.rest_rs='KC' and (t.t_in is not null or t.t_mid is not null or t.t_out is not null or t.t_over is not null) ";
			sql2 = "select t.empsn,e.fname||' '||e.lname as empna,d.name_dept  as depsn_new ,t.dates"
					+ " from n_data_daily t, n_department d, n_employee e  Where t.empsn=e.empsn and e.depsn=d.id_dept";

			sql2 = sql2 + buildCondition() + sql1;

		}
		if (radSumTangca.isSelected()) {

			sql1 = " and t.dates>=? and t.dates<=? group by t.empsn,e.fname||' '||e.lname,d.name_dept ";
			sql2 = "select t.empsn,e.fname||' '||e.lname as empna,d.name_dept  as depsn_new ,sum(t.real_ot) as real_ot"
					+ " from n_data_daily t, n_department d, n_employee e  Where t.empsn=e.empsn and e.depsn=d.id_dept";

			sql2 = sql2 + buildCondition() + sql1;
		}
		if (radThamnien.isSelected()) {

			sql1 = "and lb.clock='1' and 1=? and 1=?  order by thamnien ";
			sql2 = "select e.empsn,e.fname||' '||e.lname as empna,d.name_dept  as depsn_new ,e.date_hired, round((SYSDATE-e.date_hired)/365,2) as thamnien"
					+ " from n_labour lb , n_department d, n_employee e  Where lb.empsn=e.empsn and e.depsn=d.id_dept";

			sql2 = sql2 + buildCondition() + sql1;
		}

		return sql2;
	}

	private String buildCondition() {
		String str = null;
		str = " and e.depsn in (select d.id_dept from n_department d where d.id_dept=e.depsn ";
		String fact = "";
		if (sfFact.getSelectedIndex() >= 0) {
			fact = ListBinder.get(sfFact).toString();
			str = str + " and d.name_fact='" + fact + "' ";
		}
		if (sfLean.getSelectedIndex() >= 0) {
			str = str + "and d.name_group='" + ListBinder.get(sfLean).toString() + "'";
		}
		if (sfDept.getSelectedIndex() >= 0) {
			str = str + "and d.name_dept_name='" + ListBinder.get(sfDept).toString() + "'";
		}
		str = str + ")";

		return str;
	}

	@Override
	protected HSSFWorkbook generateWorkbook() throws IOException {
		MrBeanBrowserContent br = (MrBeanBrowserContent) splitPane1.getComponent(1);
		PageableSortableTableModel model = (PageableSortableTableModel) br.getDataTable().getModel();
		if (model.getTotalRows() <= 0)
			return null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet;
		HSSFRow row;
		HSSFCell cell;
		if (br == browserContent1) {
			wb = ReportUtils.loadTemplate("fvhr", "DANHSACH_TRE_NGAY.xls");
			sheet = wb.getSheetAt(0);
			row = sheet.getRow(1);
			for (int i = 0; i < model.getTotalRows(); i++) {
				row = sheet.createRow(i + 2);
				for (int j = 0; j < 5; j++) {
					cell = row.createCell(j);
					Object obj = model.getValueAtAbsolute(j, i);
					if (obj != null) {
						cell.setCellValue(String.valueOf(obj));
					}
				}
			}
			setReportFileName("CNV");
		}
		if (br == browserContent5) {
			wb = ReportUtils.loadTemplate("fvhr", "DANHSACH_TRE_THANG.xls");
			sheet = wb.getSheetAt(0);
			row = sheet.getRow(1);
			for (int i = 0; i < model.getTotalRows(); i++) {
				row = sheet.createRow(i + 2);
				for (int j = 0; j < 4; j++) {
					cell = row.createCell(j);
					Object obj = model.getValueAtAbsolute(j, i);
					if (obj != null) {
						cell.setCellValue(String.valueOf(obj));
					}
				}
			}
			setReportFileName("CNV");
		}
		if (br == browserContent2) {
			wb = ReportUtils.loadTemplate("fvhr", "DANHSACH_KHANGCONG.xls");
			sheet = wb.getSheetAt(0);
			row = sheet.getRow(1);
			for (int i = 0; i < model.getTotalRows(); i++) {
				row = sheet.createRow(i + 2);
				for (int j = 0; j < 4; j++) {
					cell = row.createCell(j);
					Object obj = model.getValueAtAbsolute(j, i);
					if (obj != null) {
						cell.setCellValue(String.valueOf(obj));
					}
				}
			}
			setReportFileName("CNV");
		}
		if (br == browserContent3) {
			wb = ReportUtils.loadTemplate("fvhr", "DANHSACH_KHANGCONG_Q.xls");
			sheet = wb.getSheetAt(0);
			row = sheet.getRow(1);
			for (int i = 0; i < model.getTotalRows(); i++) {
				row = sheet.createRow(i + 2);
				for (int j = 0; j < 4; j++) {
					cell = row.createCell(j);
					Object obj = model.getValueAtAbsolute(j, i);
					if (obj != null) {
						cell.setCellValue(String.valueOf(obj));
					}
				}
			}
			setReportFileName("CNV");
		}
		if (br == browserContent4) {
			wb = ReportUtils.loadTemplate("fvhr", "Tongsogiotangca.xls");
			sheet = wb.getSheetAt(0);
			row = sheet.getRow(1);
			for (int i = 0; i < model.getTotalRows(); i++) {
				row = sheet.createRow(i + 2);
				for (int j = 0; j < 4; j++) {
					cell = row.createCell(j);
					Object obj = model.getValueAtAbsolute(j, i);
					if (obj != null) {
						cell.setCellValue(String.valueOf(obj));
					}
				}
			}
			setReportFileName("CNV");
		}
		if (br == browserContent6) {
			wb = ReportUtils.loadTemplate("fvhr", "Thamnien.xls");
			sheet = wb.getSheetAt(0);
			row = sheet.getRow(1);
			for (int i = 0; i < model.getTotalRows(); i++) {
				row = sheet.createRow(i + 2);
				for (int j = 0; j < 5; j++) {
					cell = row.createCell(j);
					Object obj = model.getValueAtAbsolute(j, i);
					if (obj != null) {
						cell.setCellValue(String.valueOf(obj));
					}
				}
			}
			setReportFileName("CNV");
		}
		return wb;
	}

	private void additionInit() {
		dfDate.getTextField().setDisabledBackground(new Color(0xc0c0c0));
		dfFromDate.getTextField().setDisabledBackground(new Color(0xc0c0c0));
		dfToDate.getTextField().setDisabledBackground(new Color(0xc0c0c0));
		txtsophut.setDisabledBackground(new Color(0xc0c0c0));

		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dfFromDate.setDateFormat(dateFormat);
		dfToDate.setDateFormat(dateFormat);
		dfDate.setDateFormat(dateFormat);
		dfFromDate.getTextField().setText("");
		dfToDate.getTextField().setText("");
		dfDate.getTextField().setText("");
		dfFromDate.getDateChooser().setLocale(new Locale("en"));
		dfToDate.getDateChooser().setLocale(new Locale("en"));
		dfDate.getDateChooser().setLocale(new Locale("en"));
		ListBinder.bindSelectField(sfFact, FVGenericInfo.getFactories(), false);

		sfFact.setEnabled(true);
	}

	private void sfFactChanged(ActionEvent e) {
		SelectItem item = (SelectItem) sfFact.getSelectedItem();
		ListBinder.bindSelectField(sfLean, FVGenericInfo.getGroup(item.getValue()), true);
		ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue()), true);
	}

	private void sfLeanChanged(ActionEvent e) {
		SelectItem item = (SelectItem) sfFact.getSelectedItem();
		SelectItem litem = (SelectItem) sfLean.getSelectedItem();
		ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue(), litem.getValue()), true);
	}

	private void optSelected(ActionEvent e) {
		// TODO Auto-generated method stub
		if (raddiTreThang.isSelected()) {
			dfDate.setEnabled(true);
			dfFromDate.setEnabled(false);
			dfToDate.setEnabled(false);
		} else {
			dfDate.setEnabled(false);
			dfFromDate.setEnabled(true);
			dfToDate.setEnabled(true);
		}

	}

	private List<String> listEmpsn() {

		DefaultListModel model = (DefaultListModel) lstEmpsn.getModel();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < model.size(); i++) {
			SelectItem item = (SelectItem) model.get(i);
			list.add(item.getValue().toString());
		}
		return list;
	}

	private void initComponents() {
		splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(250, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/SplitVertBar.png");
		splitPane1.setSeparatorVerticalImage(new FillImage(imageReference1));
		splitPane1.setResizable(true);
		add(splitPane1);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(2, Extent.PX)));
		SplitPaneLayoutData grid1LayoutData = new SplitPaneLayoutData();
		grid1LayoutData.setBackground(new Color(0x87bad6));
		grid1.setLayoutData(grid1LayoutData);
		grid1.setSize(3);
		splitPane1.add(grid1);
		grid3 = new Grid();
		grid3.setSize(2);
		grid3.setInsets(new Insets(0, 3, 3, 3));
		grid3.setWidth(new Extent(100, Extent.PERCENT));

		GroupBox groupBox1 = new GroupBox();
		ButtonGroup date_group = new ButtonGroup();
		lblDate_ = new Label();
		lblDate_.setText("Tháng");

		grid3.add(lblDate_);
		dfDate = new DscDateField();
		dfDate.setEnabled(false);
		grid3.add(dfDate);
		lblDate = new Label();
		lblDate.setText("Thời gian từ");

		grid3.add(lblDate);
		dfFromDate = new DscDateField();
		dfFromDate.setEnabled(true);
		grid3.add(dfFromDate);
		Label label4 = new Label();
		label4.setText("đến");
		GridLayoutData Label4LayoutData = new GridLayoutData();
		Label4LayoutData.setInsets(new Insets(new Extent(24, Extent.PX), new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		label4.setLayoutData(Label4LayoutData);
		grid3.add(label4);
		dfToDate = new DscDateField();
		dfToDate.setEnabled(true);
		grid3.add(dfToDate);
		lblPhutTre = new Label();
		lblPhutTre.setText("Số Phút Trể / Ngày");
		grid3.add(lblPhutTre);
		txtsophut = new DscField();
		txtsophut.setInputType(DscField.INPUT_TYPE_TEXT);
		txtsophut.setWidth(new Extent(180, Extent.PX));
		txtsophut.setEnabled(true);
		grid3.add(txtsophut);
		lblFact = new Label();
		lblFact.setText("Xưởng");
		grid3.add(lblFact);
		sfFact = new SelectField();
		sfFact.setEnabled(true);
		sfFact.setWidth(new Extent(180, Extent.PX));
		sfFact.setDisabledBackground(new Color(0xc0c0c0));
		sfFact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sfFactChanged(e);
			}
		});
		grid3.add(sfFact);

		Label label6 = new Label();
		label6.setText("Lean");
		GridLayoutData label6LayoutData = new GridLayoutData();
		label6LayoutData.setInsets(new Insets(new Extent(24, Extent.PX), new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		label6.setLayoutData(label6LayoutData);
		grid3.add(label6);
		sfLean = new SelectField();
		sfLean.setEnabled(true);
		sfLean.setWidth(new Extent(180, Extent.PX));
		sfLean.setDisabledBackground(new Color(0xc0c0c0));
		sfLean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sfLeanChanged(e);
			}
		});
		grid3.add(sfLean);
		Label label8 = new Label();
		label8.setText("Đơn vị");
		GridLayoutData label8LayoutData = new GridLayoutData();
		label8LayoutData.setInsets(new Insets(new Extent(24, Extent.PX), new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		label8.setLayoutData(label8LayoutData);
		grid3.add(label8);
		sfDept = new SelectField();
		sfDept.setEnabled(true);
		sfDept.setWidth(new Extent(180, Extent.PX));
		sfDept.setDisabledBackground(new Color(0xc0c0c0));
		grid3.add(sfDept);
		groupBox1.setTitle("Chọn lựa danh sách");
		GridLayoutData groupBox1LayoutData = new GridLayoutData();
		groupBox1LayoutData.setAlignment(new Alignment(Alignment.DEFAULT, Alignment.TOP));
		groupBox1LayoutData.setRowSpan(7);
		groupBox1.setLayoutData(groupBox1LayoutData);
		grid1.add(grid3);
		grid1.add(groupBox1);
		Grid grid2 = new Grid();
		grid2.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5, Extent.PX)));
		grid2.setSize(1);
		groupBox1.add(grid2);
		radDitrengay = new RadioButton();
		radDitrengay.setSelected(true);
		radDitrengay.setText("DS đi trể > 3'/ngày");
		ButtonGroup opt = new ButtonGroup();
		radDitrengay.setGroup(opt);
		radDitrengay.setActionCommand("op1");
		radDitrengay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		grid2.add(radDitrengay);
		raddiTreThang = new RadioButton();
		raddiTreThang.setText("DS đi trể > 10'/1 tháng");
		raddiTreThang.setGroup(opt);
		raddiTreThang.setActionCommand("op2");
		raddiTreThang.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		grid2.add(raddiTreThang);
		radDSkhangCong = new RadioButton();
		radDSkhangCong.setText("Danh Sách Kháng Công");
		radDSkhangCong.setGroup(opt);
		radDSkhangCong.setActionCommand("op3");
		radDSkhangCong.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		grid2.add(radDSkhangCong);
		radkhangcongquetthe = new RadioButton();
		radkhangcongquetthe.setText("Danh Sách Kháng Công Có Quét Thẻ ");
		radkhangcongquetthe.setGroup(opt);
		radkhangcongquetthe.setActionCommand("op4");
		radkhangcongquetthe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		grid2.add(radkhangcongquetthe);
		radSumTangca = new RadioButton();
		radSumTangca.setText("Tổng Giờ tăng ca từng Người");
		radSumTangca.setGroup(opt);
		radSumTangca.setActionCommand("op5");
		radSumTangca.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		grid2.add(radSumTangca);
		radThamnien = new RadioButton();
		radThamnien.setText("Thâm Niên Của Từng Nhân Viên");
		radThamnien.setGroup(opt);
		radThamnien.setActionCommand("op5");
		radThamnien.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		grid2.add(radThamnien);
		lblSum = new Label();
		lblSum.setText("");
		grid1.add(lblSum);

	}

	@Override
	protected void doSearch() {
		// TODO Auto-generated method stub

	}

}
