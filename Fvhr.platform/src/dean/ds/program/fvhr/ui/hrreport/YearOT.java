package ds.program.fvhr.ui.hrreport;

import java.util.Date;

public class YearOT {
	private String facCode;
	private Date permitDate;
	private Date reportDate;
	private int deptNo;
	private int empTot1;
	private int empTot2;
	private int empTot3;
	private int empTot4;
	private int empTot5;
	private int empTot6;
	
	public int getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}
	public int getEmpTot1() {
		return empTot1;
	}
	public void setEmpTot1(int empTot1) {
		this.empTot1 = empTot1;
	}
	public int getEmpTot2() {
		return empTot2;
	}
	public void setEmpTot2(int empTot2) {
		this.empTot2 = empTot2;
	}
	public int getEmpTot3() {
		return empTot3;
	}
	public void setEmpTot3(int empTot3) {
		this.empTot3 = empTot3;
	}
	public int getEmpTot4() {
		return empTot4;
	}
	public void setEmpTot4(int empTot4) {
		this.empTot4 = empTot4;
	}
	public int getEmpTot5() {
		return empTot5;
	}
	public void setEmpTot5(int empTot5) {
		this.empTot5 = empTot5;
	}
	public int getEmpTot6() {
		return empTot6;
	}
	public void setEmpTot6(int empTot6) {
		this.empTot6 = empTot6;
	}
	public String getFacCode() {
		return facCode;
	}
	public void setFacCode(String facCode) {
		this.facCode = facCode;
	}
	public Date getPermitDate() {
		return permitDate;
	}
	public void setPermitDate(Date permitDate) {
		this.permitDate = permitDate;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + deptNo;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final YearOT other = (YearOT) obj;
		if (deptNo != other.deptNo)
			return false;
		return true;
	}
}
