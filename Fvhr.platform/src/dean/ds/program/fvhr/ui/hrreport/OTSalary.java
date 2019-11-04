package ds.program.fvhr.ui.hrreport;

import java.math.BigDecimal;

public class OTSalary {
	private String deptId;
	private String fact;
	private String group;
	private String name;
	private BigDecimal totalWorkingDays;
	private BigDecimal totalEmps;
	private BigDecimal totalOT;
	private BigDecimal totalRestPay;
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getFact() {
		return fact;
	}
	public void setFact(String fact) {
		this.fact = fact;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getTotalEmps() {
		return totalEmps;
	}
	public void setTotalEmps(BigDecimal totalEmps) {
		this.totalEmps = totalEmps;
	}
	public BigDecimal getTotalOT() {
		return totalOT;
	}
	public void setTotalOT(BigDecimal totalOT) {
		this.totalOT = totalOT;
	}
	public BigDecimal getTotalRestPay() {
		return totalRestPay;
	}
	public void setTotalRestPay(BigDecimal totalRestPay) {
		this.totalRestPay = totalRestPay;
	}
	public BigDecimal getTotalWorkingDays() {
		return totalWorkingDays;
	}
	public void setTotalWorkingDays(BigDecimal totalWorkingDays) {
		this.totalWorkingDays = totalWorkingDays;
	}
	
}
