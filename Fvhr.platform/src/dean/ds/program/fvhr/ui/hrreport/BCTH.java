package ds.program.fvhr.ui.hrreport;

import java.math.BigDecimal;
import java.util.Date;

public class BCTH {
	private String empsn;
	private String name;
	private String dept;
	private Date date1;
	private Date date2;
	private String shift;
	//bo sung 28/08/2013, HA
	private String tIn;
	private String tMid;
	private String tOut;
	private String tOver;	
	// end bo sung
	private BigDecimal realOt;
	private BigDecimal payOt;
	//bo sung 28/08/2013, HA
	private String note;
	private String ttgt;
	
	
	public Date getDate1() {
		return date1;
	}
	public void setDate1(Date date1) {
		this.date1 = date1;
	}
	public Date getDate2() {
		return date2;
	}
	public void setDate2(Date date2) {
		this.date2 = date2;
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
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public BigDecimal getRealOt() {
		return realOt;
	}
	public void setRealOt(BigDecimal realOt) {
		this.realOt = realOt;
	}
	public BigDecimal getPayOt() {
		return payOt;
	}
	public void setPayOt(BigDecimal payOt) {
		this.payOt = payOt;
	}
	public String gettIn() {
		return tIn;
	}
	public void settIn(String tIn) {
		this.tIn = tIn;
	}
	public String gettMid() {
		return tMid;
	}
	public void settMid(String tMid) {
		this.tMid = tMid;
	}
	public String gettOut() {
		return tOut;
	}
	public void settOut(String tOut) {
		this.tOut = tOut;
	}
	public String gettOver() {
		return tOver;
	}
	public void settOver(String tOver) {
		this.tOver = tOver;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getttgt() {
		return ttgt;
	}
	public void setttgt(String ttGt) {
		this.ttgt = ttGt;
	}
}
