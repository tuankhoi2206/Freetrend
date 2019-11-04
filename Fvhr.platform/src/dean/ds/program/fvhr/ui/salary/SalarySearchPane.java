package ds.program.fvhr.ui.salary;

import java.util.Calendar;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.layout.GridLayoutData;
import dsc.echo2app.component.binder.ListBaseBinder;
import fv.components.DeptSearchPane;
import fv.components.SearchPane;
import fv.util.MappingPropertyUtils;

public class SalarySearchPane extends SearchPane {
	private static final long serialVersionUID = 1L;

	private DeptSearchPane deptItems;

	private SelectField sfMonth;

	private SelectField sfYear;

	private SalaryProgram program;

	public SalarySearchPane(SalaryProgram program) {
		super();
		this.program = program;
		initComponents();
		moreInitUI();
	}

	protected void moreInitUI() {
		Calendar cal = Calendar.getInstance();
		ListBaseBinder monthBinder = new ListBaseBinder(null, sfMonth,
				MappingPropertyUtils.getJavaMonthEditor());
		monthBinder.objectToComponent(cal.get(Calendar.MONTH));
		ListBaseBinder yearBinder = new ListBaseBinder(null, sfYear,
				MappingPropertyUtils.getYearEditor(2, false));
		yearBinder.objectToComponent(cal.get(Calendar.YEAR));
	}

	@Override
	protected void doSearch() {
		SalaryBrowserContent browserContent = program.getBrowserContent();
		browserContent.setMonth(sfMonth.getSelectedItem().toString());
		browserContent.setYear(sfYear.getSelectedItem().toString());
		browserContent.setFact(deptItems.getOutputCondition());
		program.refresh();
	}

	private void initComponents() {
		GridLayoutData labelLayout = new GridLayoutData();
		labelLayout.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		getRootLayout().add(new Label("Month: "));
		sfMonth = new SelectField();
		sfMonth.setWidth(new Extent(40));
		getRootLayout().add(sfMonth);
		getRootLayout().add(new Label("Year: "));
		sfYear = new SelectField();
		sfYear.setWidth(new Extent(60));
		getRootLayout().add(sfYear);
		getRootLayout().getComponent(0).setLayoutData(labelLayout);
		getRootLayout().getComponent(2).setLayoutData(labelLayout);
		deptItems = new DeptSearchPane();
		GridLayoutData childTableLayout = new GridLayoutData();
		childTableLayout.setColumnSpan(2);
		deptItems.setLayoutData(childTableLayout);
		getRootLayout().add(deptItems);
	}
}
