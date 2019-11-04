package ds.program.fvhr.khoi.domain;

import java.util.Date;

public class EmployeeQuit {
	private String empsn;
	private int bSalary;
	private Date realOffDate;

	public String getEmpsn() {
		return empsn;
	}

	public void setEmpsn(String empsn) {
		this.empsn = empsn;
	}

	public int getbSalary() {
		return bSalary;
	}

	public void setbSalary(int bSalary) {
		this.bSalary = bSalary;
	}

	public Date getRealOffDate() {
		return realOffDate;
	}

	public void setRealOffDate(Date realOffDate) {
		this.realOffDate = realOffDate;
	}

}
