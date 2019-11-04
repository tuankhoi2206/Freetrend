package ds.program.fvhr.ui.salary;

import java.util.List;

import ds.program.fvhr.dao.salary.SalaryDAO;
import ds.program.fvhr.obj.salary.SalaryExcelData;
import fv.components.JdbcBrowserContent;

public class ExcelSalaryBrowserContent extends JdbcBrowserContent {

	private static final long serialVersionUID = 1L;

	private String month = "06";

	private String year = "2011";

	private String fact = "TB";

	private SalaryDAO dao;

	public ExcelSalaryBrowserContent() {
		super();
		setDataClass(SalaryExcelData.class);
		dao = new SalaryDAO(month, year);
	}

	@Override
	protected void doDataContentRefresh() {
		super.doDataContentRefresh();
		List<SalaryExcelData> list = dao.getPreAttList(fact);
		setListData(list);
	}
}
