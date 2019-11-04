package ds.program.fvhr.ui;

// packed lay thong tin cua nhan vien trong co so du lieu
import java.io.Serializable;

import dsc.dao.XmlDAO;
import dsc.echo2app.Application;

public class GetEmployeeInfo implements Serializable {
	private static final String VID_GetEmployee = "QLNS_FIND_EMPLOYEE";

	private String EMPSN;

	private String FullName;

	private String DEPSN;

	private String CMND;

	private String BIRTHDAY;

	public GetEmployeeInfo(String EMPSN) throws Exception {
		super();
		this.EMPSN = EMPSN;
		XmlDAO dao = XmlDAO.doit(Application.getApp().getConnection(),
				VID_GetEmployee, " EMPSN = '" + EMPSN + "'");

		if (dao == null) {
			throw new Exception("Get User Data error!");
			// return;
		}
		System.out.println("Show sql getEmployee : ********** "
				+ dao.getSqlQueryString());
		this.EMPSN = (String) dao.getRowFieldData(0, "EMPSN");
		this.CMND = (String) dao.getRowFieldData(0, "CMND");
		this.DEPSN = (String) dao.getRowFieldData(0, "DEPSN");
		this.FullName = (String) dao.getRowFieldData(0, "FULLNAME");
		this.BIRTHDAY = (String) dao.getRowFieldData(0, "BIRTHDAY");
	}

	public String getEMPSN() {
		return EMPSN;
	}

	public String getCMND() {
		return CMND;
	}

	public String getDEPSN() {
		return DEPSN;
	}

	public String getFullName() {
		return FullName;
	}

	public String getBIRTHDAY() {
		return BIRTHDAY;
	}
}
