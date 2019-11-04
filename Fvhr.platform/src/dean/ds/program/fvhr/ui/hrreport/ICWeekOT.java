package ds.program.fvhr.ui.hrreport;

import java.math.BigDecimal;

public class ICWeekOT {
	private int groupNike;
	private String groupName;
	private String dept;
	private String empsn;
	private BigDecimal totalHours;
	private BigDecimal totalSundayHours;
	public String getEmpsn() {
		return empsn;
	}
	public void setEmpsn(String empsn) {
		this.empsn = empsn;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getGroupNike() {
		return groupNike;
	}
	public void setGroupNike(int groupNike) {
		this.groupNike = groupNike;
	}
	public BigDecimal getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(BigDecimal totalHours) {
		this.totalHours = totalHours;
	}
	public BigDecimal getTotalSundayHours() {
		return totalSundayHours;
	}
	public void setTotalSundayHours(BigDecimal totalSundayHours) {
		this.totalSundayHours = totalSundayHours;
	}
}
