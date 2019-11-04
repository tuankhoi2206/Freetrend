package ds.program.fvhr.ui.quitworksalary;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.WindowPane;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.binder.ListBaseBinder;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import echopointng.DateField;
import fv.components.SearchPane;
import fv.util.BundleUtils;
import fv.util.FVGenericInfo;
import fv.util.MappingPropertyUtils;

public class QuitWorkSearchPane extends SearchPane {
	private static final long serialVersionUID = -4099616834378402791L;

	private Label lblMonth;

	private SelectField sfMonth;

	private Label lblYear;

	private SelectField sfYear;

	private Label lblFact;

	private SelectField sfFact;

	private Label lblQuitDate;

	private DateField dfQuitDate;

	private Label lblType;

	private SelectField sfType;

	private QuitWorkSalaryProgram program;

	private ListBaseBinder mBinder;

	private ListBaseBinder yBinder;

	private ListBaseBinder fBinder;

	public QuitWorkSearchPane(QuitWorkSalaryProgram program) {
		super();
		this.program = program;
		setDefaultCloseOperation(WindowPane.HIDE_ON_CLOSE);
	}

	@Override
	protected void initUI() {
		super.initUI();
		setTitle("Tìm dữ liệu nghỉ việc");
		initComponents();
		MappingPropertyEditor monthEditor = MappingPropertyUtils
				.getJavaMonthEditor();
		int month = Calendar.getInstance().get(Calendar.MONTH);
		mBinder = new ListBaseBinder(null, sfMonth, monthEditor);
		mBinder.objectToComponent(month);
		MappingPropertyEditor yearEditor = MappingPropertyUtils.getYearEditor(
				1, false);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		yBinder = new ListBaseBinder(null, sfYear, yearEditor);
		yBinder.objectToComponent(year);
		ListBaseBinder tBinder = new ListBaseBinder(null, sfType,
				getTypeEditor());
		tBinder.objectToComponent("TV");
		fBinder = new ListBaseBinder(null, sfFact, FVGenericInfo.getFactories());
		fBinder.objectToComponent("FVL");
		Calendar cal = dfQuitDate.getSelectedDate();
		cal.set(2011, 5, 6);
	}

	private MappingPropertyEditor getTypeEditor() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		editor.put("Thôi việc", "TV");
		editor.put("Bỏ việc", "BV");
		return editor;
	}

//	private MappingPropertyEditor getFactEditor() {
//		MappingPropertyEditor editor = new MappingPropertyEditor();
//		editor.put("FVJ", "FVJ");
//		editor.put("FVL", "FVL");
//		editor.put("FVS", "FVS");
//		editor.put("KDAO", "KDAO");
//		editor.put("TB", "TB");
//		return editor;
//	}

	private void initComponents() {
		lblMonth = new Label(BundleUtils.getString("QUITWORK.MONTH"));
		getRootLayout().add(lblMonth);
		Row row = new Row();
		sfMonth = new SelectField();
		sfMonth.setWidth(new Extent(40));
		row.add(sfMonth);
		lblYear = new Label(BundleUtils.getString("QUITWORK.YEAR"));
		row.add(lblYear);
		sfYear = new SelectField();
		sfYear.setWidth(new Extent(60));
		row.add(sfYear);
		getRootLayout().add(row);
		lblQuitDate = new Label(BundleUtils.getString("QUITWORK.QUITDATE"));
		getRootLayout().add(lblQuitDate);
		dfQuitDate = new DateField();
		// dfQuitDate.getTextField().setText("");
		dfQuitDate.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dfQuitDate.getDateChooser().setLocale(new Locale("en"));
		dfQuitDate.setSelectedDate(Calendar.getInstance());
		dfQuitDate.setPopUpAlwaysOnTop(true);
		getRootLayout().add(dfQuitDate);
		lblFact = new Label(BundleUtils.getString("QUITWORK.FACT"));
		getRootLayout().add(lblFact);
		sfFact = new SelectField();
		sfFact.setWidth(new Extent(60));
		getRootLayout().add(sfFact);
		lblType = new Label(BundleUtils.getString("QUITWORK.TYPE"));
		getRootLayout().add(lblType);
		sfType = new SelectField();
		sfType.setWidth(new Extent(80));
		getRootLayout().add(sfType);
	}

	@Override
	protected void doSearch() {
		QuitWorkSalaryBrowserContent dc = program.getBrowserContent();
		dc.setMonth(sfMonth.getSelectedItem().toString());
		dc.setYear(sfYear.getSelectedItem().toString());
		dc.setFact(sfFact.getSelectedItem().toString());
		dc.setDate(dfQuitDate.getSelectedDate().getTime());
		if (sfType.getSelectedIndex() == 1) {
			dc.setType("BV");
		} else {
			dc.setType(null);
		}
		program.refresh();
		if (dc.getListData().size() == 0) {
			Application.getApp().showMessageDialog(
					MessageDialog.CONTROLS_OK + MessageDialog.TYPE_INFORMATION,
					BundleUtils.getString("QUITWORK.NOTFOUNDDATA"));
		}
	}

	public void setMonth(int month) {
		mBinder.objectToComponent(month);
	}

	public void setYear(int year) {
		yBinder.objectToComponent(year);
	}

	public void setFact(String fact) {
		fBinder.objectToComponent(fact);
	}

	public void setQuitDate(Calendar date) {
		dfQuitDate.setSelectedDate(date);
	}

	public void setType(int index) {
		sfType.setSelectedIndex(index);
	}
}
