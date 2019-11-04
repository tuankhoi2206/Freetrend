package ds.program.fvhr.minh.domain;

import java.util.Date;

public class EmployeeBear {
	private String EMPSN;
	private String NAME;
	private String DEPSN;
	private String NAME_DEPT;
	private Date B_DATES;
	private Date E_DATES;
	private String NUM;
	public void setEMPSN(String eMPSN) {
		EMPSN = eMPSN;
	}
	public String getEMPSN() {
		return EMPSN;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getNAME() {
		return NAME;
	}
	public void setDEPSN(String dEPSN) {
		DEPSN = dEPSN;
	}
	public String getDEPSN() {
		return DEPSN;
	}
	public void setNAME_DEPT(String nAME_DEPT) {
		NAME_DEPT = nAME_DEPT;
	}
	public String getNAME_DEPT() {
		return NAME_DEPT;
	}
	public void setB_DATES(Date b_DATES) {
		B_DATES = b_DATES;
	}
	public Date getB_DATES() {
		return B_DATES;
	}
	public void setE_DATES(Date e_DATES) {
		E_DATES = e_DATES;
	}
	public Date getE_DATES() {
		return E_DATES;
	}
	public void setNUM(String nUM) {
		NUM = nUM;
	}
	public String getNUM() {
		return NUM;
	}

}
