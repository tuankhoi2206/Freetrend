package ds.program.fvhr.ui.mst;

import java.util.Date;

public class EmpTaxcode {
	private String empsn;
	private String fullName;
	private String dept;
	private String taxCode;
	private Date date;
	private String note;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
}
