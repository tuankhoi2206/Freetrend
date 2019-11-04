package ds.program.fvhr.ui.training;

import java.util.Date;

public class EmpReportData {
	private String FNAME;
	private String LNAME;
	private Date DATE_HIRED;
	private String POSITION;
	private String EMPSN;
	private String DEPSN;
	
	public Date getDATE_HIRED() {
		return DATE_HIRED;
	}
	public void setDATE_HIRED(Date date_hired) {
		DATE_HIRED = date_hired;
	}
	public String getDEPSN() {
		return DEPSN;
	}
	public void setDEPSN(String depsn) {
		DEPSN = depsn;
	}
	public String getEMPSN() {
		return EMPSN;
	}
	public void setEMPSN(String empsn) {
		EMPSN = empsn;
	}
	public String getFNAME() {
		return FNAME;
	}
	public void setFNAME(String fname) {
		FNAME = fname;
	}
	public String getLNAME() {
		return LNAME;
	}
	public void setLNAME(String lname) {
		LNAME = lname;
	}
	public String getPOSITION() {
		return POSITION;
	}
	public void setPOSITION(String position) {
		POSITION = position;
	}
}
