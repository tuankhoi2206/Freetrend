package ds.program.fvhr.ui.workpoints;

import java.math.BigDecimal;

public class CmpData {
	private String empsn;
	private String name;
	private String dept;
	private BigDecimal days;
	
	public BigDecimal getDays() {
		return days;
	}
	public void setDays(BigDecimal days) {
		this.days = days;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getEmpsn() {
		return empsn;
	}
	public void setEmpsn(String empsn) {
		this.empsn = empsn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
