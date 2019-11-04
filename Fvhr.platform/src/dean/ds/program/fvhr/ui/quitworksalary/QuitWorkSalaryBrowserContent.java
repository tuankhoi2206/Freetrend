package ds.program.fvhr.ui.quitworksalary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ds.program.fvhr.dao.quitsalary.QuitWorkSalaryDAO;
import ds.program.fvhr.domain.ATTQUIT;
import ds.program.fvhr.domain.pk.ATTQUITPk;
import fv.components.JdbcBrowserContent;

public class QuitWorkSalaryBrowserContent extends JdbcBrowserContent {
	private static final long serialVersionUID = 3037869010785194073L;

	// private String[] displayColumns;
	private String month = "";

	private String year = "";

	private String fact = "";

	private String type;

	private Date date;

	private QuitWorkSalaryDAO dao = new QuitWorkSalaryDAO();

	public QuitWorkSalaryBrowserContent() {
		setDataClass(ATTQUIT.class);
	}

	@Override
	protected void doDataContentRefresh() {
		super.doDataContentRefresh();
		dao.setMonth(getMonth());
		dao.setYear(getYear());
		List<ATTQUIT> listData = dao.selectList(getColumnHeaderMap(),
				getFact(), getType(), getDate());
		System.out.println("Data size: " + listData.size());
		setListData(listData);
	}

	public QuitWorkSalaryDAO getDao() {
		return dao;
	}

	// public void setDisplayColumns(String[] displayColumns){
	// this.displayColumns=displayColumns;
	// }

	// public String[] getDisplayColumns(){
	// return this.displayColumns;
	// }

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Bad functions
	 * 
	 * @return
	 */
	public ATTQUITPk getPkValue() {
		try {
			String empsn = (String) getModel()
					.getValueAt(1, getSelectedIndex());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String dateStr = (String) getModel().getValueAt(3,
					getSelectedIndex());
			try {
				return new ATTQUITPk(empsn, sdf.parse(dateStr));
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK
			// + MessageDialog.TYPE_ERROR, "Chọn dữ liệu cần xem");
		}
		return null;
	}

	public void removeICData(String empsn, String type) {
		dao.removeICData(empsn, date, type);
	}

}
