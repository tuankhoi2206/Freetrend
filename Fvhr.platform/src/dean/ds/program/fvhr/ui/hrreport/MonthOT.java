package ds.program.fvhr.ui.hrreport;

import java.math.BigDecimal;

public class MonthOT {
	private String empsn;
	private String fullName;
	private String fact;
	private String group;
	private String nameDept;
	private String empcn;
	private BigDecimal ot;
	public String getEmpcn() {
		return empcn;
	}
	public void setEmpcn(String empcn) {
		this.empcn = empcn;
	}
	public String getEmpsn() {
		return empsn;
	}
	public void setEmpsn(String empsn) {
		this.empsn = empsn;
	}
	public String getFact() {
		return fact;
	}
	public void setFact(String fact) {
		this.fact = fact;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getNameDept() {
		return nameDept;
	}
	public void setNameDept(String nameDept) {
		this.nameDept = nameDept;
	}
	public BigDecimal getOt() {
		return ot;
	}
	public void setOt(BigDecimal ot) {
		this.ot = ot;
	}
}
