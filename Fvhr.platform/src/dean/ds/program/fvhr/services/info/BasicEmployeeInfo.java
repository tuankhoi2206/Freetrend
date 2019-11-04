package ds.program.fvhr.services.info;

import ds.program.fvhr.domain.combo.EmployeeDepartment;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;

public class BasicEmployeeInfo {
	public EmployeeDepartment getEmployeeInfo(String empsn) {
		IGenericDAO<?, ?> dao = Application.getApp().getDao(
				Application.getApp().getLoginInfo().getCompanyID());
		EmployeeDepartment e = (EmployeeDepartment) dao
				.findUnique(
						"select new EmployeeDepartment(a.EMPSN, a.EMPCN, a.FNAME, a.LNAME, a.BIRTHDAY, a.PERMANENT_ADDRESS, a.SEX, b.ID_DEPT, b.NAME_DEPT, b.NAME_GROUP, b.NAME_FACT) from N_EMPLOYEE a, N_DEPARTMENT b where a.DEPSN=b.ID_DEPT and a.EMPSN=?",
						new Object[] { empsn });
		return e;
	}

	public static boolean isQuit(String empsn) {
		IGenericDAO<?, ?> dao = Application.getApp().getDao(
				Application.getApp().getLoginInfo().getCompanyID());
		String emp = (String) dao
				.findUnique(
						"select distinct a.EMPSN from N_EMPLOYEE a, N_EMP_QUIT b where a.EMPSN=? and a.EMPSN=b.EMPSN",
						new Object[] { empsn });
		if (emp != null && !empsn.equals(""))
			return true;
		return false;
	}

	public static boolean isQuitAndHasWP(String empsn, String month, String year) {
		IGenericDAO<?, ?> dao = Application.getApp().getDao(
				Application.getApp().getLoginInfo().getCompanyID());
		String emp = (String) dao
				.findUnique(
						"select distinct a.EMPSN from N_EMPLOYEE a, N_GET_DATA b, N_EMP_QUIT c where a.EMPSN=? and b.MONTHS=? and b.YEARS=? and a.EMPSN=b.EMPSN and a.EMPSN=c.EMPSN",
						new Object[] { empsn, month, year });
		if (emp != null && !empsn.equals(""))
			return true;
		return false;
	}

	public static boolean hasWorkData(String empsn, String month, String year) {
		IGenericDAO<?, ?> dao = Application.getApp().getDao(
				Application.getApp().getLoginInfo().getCompanyID());
		String emp = (String) dao
				.findUnique(
						"select distinct a.EMPSN from N_GET_DATA a where a.EMPSN=? and a.MONTHS=? and a.YEARS=?",
						new Object[] { empsn, month, year });
		if (emp != null && !empsn.equals(""))
			return true;
		return false;
	}

	public static boolean isExist(String empsn) {
		if (!empsn.matches("[0-9]{8}")) return false;
		IGenericDAO<?, ?> dao = Application.getApp().getDao(
				Application.getApp().getLoginInfo().getCompanyID());
		String emp = (String) dao
				.findUnique(
						"select a.EMPSN from N_EMPLOYEE a where a.EMPSN=?",
						new Object[] { empsn });
		if (emp != null && !empsn.equals(""))
			return true;
		return false;
	}
}
