package ds.program.fvhr.ui.quitworksalary;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Troi oi tum lum het
 * 
 * @author Hieu
 * 
 */
public class AttQuitInitData {
	// EMPSN,EMPNA,DEPSN,POSSN,HIRED,DATE_OFF,DOT_TV,DEPT_KT
	private String empsn;

	private String empna;

	private String depsn;

	private String possn;

	private Date hired;

	private Date dateOff;

	private Date dotTv;

	private String deptKt;

	private String note_bh;
	
	private BigDecimal stt;
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNoteBh() {
		return note_bh;
	}

	public void setNoteBh(String note_bh) {
		this.note_bh = note_bh;
	}

	public String getEmpsn() {
		return empsn;
	}

	public void setEmpsn(String empsn) {
		this.empsn = empsn;
	}

	public String getEmpna() {
		return empna;
	}

	public void setEmpna(String empna) {
		this.empna = empna;
	}

	public String getDepsn() {
		return depsn;
	}

	public void setDepsn(String depsn) {
		this.depsn = depsn;
	}

	public String getPossn() {
		return possn;
	}

	public void setPossn(String possn) {
		this.possn = possn;
	}

	public Date getHired() {
		return hired;
	}

	public void setHired(Date hired) {
		this.hired = hired;
	}

	public Date getDateOff() {
		return dateOff;
	}

	public void setDateOff(Date dateOff) {
		this.dateOff = dateOff;
	}

	public Date getDotTv() {
		return dotTv;
	}

	public void setDotTv(Date dotTv) {
		this.dotTv = dotTv;
	}

	public String getDeptKt() {
		return deptKt;
	}

	public void setDeptKt(String deptKt) {
		this.deptKt = deptKt;
	}

	public BigDecimal getStt() {
		return stt;
	}
	
	public void setStt(BigDecimal stt) {
		this.stt = stt;
	}
}
