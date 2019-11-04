package ds.program.fvhr.ui.hrreport;

import java.util.Date;

public class DayOT {
	private String facCode;
	private int deptNo;
	private Date date;
	private int empTot;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}
	public int getEmpTot() {
		return empTot;
	}
	public void setEmpTot(int empTot) {
		this.empTot = empTot;
	}
	public String getFacCode() {
		return facCode;
	}
	public void setFacCode(String facCode) {
		this.facCode = facCode;
	}
}
