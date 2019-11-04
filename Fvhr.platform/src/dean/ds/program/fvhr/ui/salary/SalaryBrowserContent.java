package ds.program.fvhr.ui.salary;

import java.util.List;

import ds.program.fvhr.dao.salary.SalaryDAO;
import ds.program.fvhr.domain.ATT200000;
import fv.components.JdbcBrowserContent;

public class SalaryBrowserContent extends JdbcBrowserContent {

	private static final long serialVersionUID = 1L;

	private String month = "";

	private String year = "";

	private String fact = "";

	private SalaryDAO dao = new SalaryDAO();

	public SalaryBrowserContent() {
		setDataClass(ATT200000.class);
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getFact() {
		return fact;
	}

	public void setFact(String fact) {
		this.fact = fact;
	}

	@Override
	protected void doDataContentRefresh() {
		super.doDataContentRefresh();
		dao.setMonth(getMonth());
		dao.setYear(getYear());
		List<ATT200000> listData = dao.getAttList(getColumnHeaderMap(), getFact());
		System.out.println("Data size: " + listData.size());
		setListData(listData);
	}
}
