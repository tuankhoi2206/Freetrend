package ds.program.fvhr.ui.workpoints;

import ds.program.fvhr.domain.N_DATA_DAILY;

public class N_DATA_DAILY_EX extends N_DATA_DAILY {
	private String EMPNA;
	private String DEPT_NAME;
	private String EMPCN;
	
	public String getDEPT_NAME() {
		return DEPT_NAME;
	}
	
	public void setDEPT_NAME(String dept_name) {
		DEPT_NAME = dept_name;
	}
	
	public String getEMPCN() {
		return EMPCN;
	}
	
	public void setEMPCN(String empcn) {
		EMPCN = empcn;
	}
	
	public String getEMPNA() {
		return EMPNA;
	}
	
	public void setEMPNA(String empna) {
		EMPNA = empna;
	}
}
