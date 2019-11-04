package ds.program.fvhr.obj.salary;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import dsc.echo2app.program.Config;

@Entity(name = "ATT")
public class SalaryExcelData {
	private String EMPSN;

	private String EMPNA;

	private BigDecimal BSALY;

	private BigDecimal COMSALY;

	private BigDecimal BONUS1;

	private BigDecimal BONUS2;

	private BigDecimal BONUS3;

	private BigDecimal BONUS4;

	private BigDecimal BONUS8;

	private BigDecimal BONUS5;

	private BigDecimal BORM;

	private BigDecimal KQT;

	private BigDecimal JOINLUM;

	private BigDecimal YLBX;

	private BigDecimal JOININSU;

	private BigDecimal BHTN;

	private BigDecimal BONUS9;

	private BigDecimal TEMP1;

	private BigDecimal TEMP2;

	private BigDecimal BAC;

	@Column(name = "EMPSN")
	@Config(key = "ATT.EMPSN")
	public String getEMPSN() {
		return EMPSN;
	}

	public void setEMPSN(String EMPSN) {
		this.EMPSN = EMPSN;
	}

	@Column(name = "EMPNA")
	@Config(key = "ATT.EMPNA")
	public String getEMPNA() {
		return EMPNA;
	}

	public void setEMPNA(String EMPNA) {
		this.EMPNA = EMPNA;
	}

	@Column(name = "BSALY")
	@Config(key = "ATT.BSALY")
	public BigDecimal getBSALY() {
		return BSALY;
	}

	public void setBSALY(BigDecimal BSALY) {
		this.BSALY = BSALY;
	}

	@Column(name = "COMSALY")
	@Config(key = "ATT.COMSALY")
	public BigDecimal getCOMSALY() {
		return COMSALY;
	}

	public void setCOMSALY(BigDecimal COMSALY) {
		this.COMSALY = COMSALY;
	}

	@Column(name = "BONUS1")
	@Config(key = "ATT.BONUS1")
	public BigDecimal getBONUS1() {
		return BONUS1;
	}

	public void setBONUS1(BigDecimal BONUS1) {
		this.BONUS1 = BONUS1;
	}

	@Column(name = "BONUS2")
	@Config(key = "ATT.BONUS2")
	public BigDecimal getBONUS2() {
		return BONUS2;
	}

	public void setBONUS2(BigDecimal BONUS2) {
		this.BONUS2 = BONUS2;
	}

	@Column(name = "BONUS3")
	@Config(key = "ATT.BONUS3")
	public BigDecimal getBONUS3() {
		return BONUS3;
	}

	public void setBONUS3(BigDecimal BONUS3) {
		this.BONUS3 = BONUS3;
	}

	@Column(name = "BONUS4")
	@Config(key = "ATT.BONUS4")
	public BigDecimal getBONUS4() {
		return BONUS4;
	}

	public void setBONUS4(BigDecimal BONUS4) {
		this.BONUS4 = BONUS4;
	}

	@Column(name = "BONUS8")
	@Config(key = "ATT.BONUS8")
	public BigDecimal getBONUS8() {
		return BONUS8;
	}

	public void setBONUS8(BigDecimal BONUS8) {
		this.BONUS8 = BONUS8;
	}

	@Column(name = "BONUS5")
	@Config(key = "ATT.BONUS5")
	public BigDecimal getBONUS5() {
		return BONUS5;
	}

	public void setBONUS5(BigDecimal BONUS5) {
		this.BONUS5 = BONUS5;
	}

	@Column(name = "BORM")
	@Config(key = "ATT.BORM")
	public BigDecimal getBORM() {
		return BORM;
	}

	public void setBORM(BigDecimal BORM) {
		this.BORM = BORM;
	}

	@Column(name = "KQT")
	@Config(key = "ATT.KQT")
	public BigDecimal getKQT() {
		return KQT;
	}

	public void setKQT(BigDecimal KQT) {
		this.KQT = KQT;
	}

	@Column(name = "JOINLUM")
	@Config(key = "ATT.JOINLUM")
	public BigDecimal getJOINLUM() {
		return JOINLUM;
	}

	public void setJOINLUM(BigDecimal JOINLUM) {
		this.JOINLUM = JOINLUM;
	}

	@Column(name = "YLBX")
	@Config(key = "ATT.YLBX")
	public BigDecimal getYLBX() {
		return YLBX;
	}

	public void setYLBX(BigDecimal YLBX) {
		this.YLBX = YLBX;
	}

	@Column(name = "JOININSU")
	@Config(key = "ATT.JOININSU")
	public BigDecimal getJOININSU() {
		return JOININSU;
	}

	public void setJOININSU(BigDecimal JOININSU) {
		this.JOININSU = JOININSU;
	}

	@Column(name = "BHTN")
	@Config(key = "ATT.BHTN")
	public BigDecimal getBHTN() {
		return BHTN;
	}

	public void setBHTN(BigDecimal BHTN) {
		this.BHTN = BHTN;
	}

	@Column(name = "BONUS9")
	@Config(key = "ATT.BONUS9")
	public BigDecimal getBONUS9() {
		return BONUS9;
	}

	public void setBONUS9(BigDecimal BONUS9) {
		this.BONUS9 = BONUS9;
	}

	@Column(name = "TEMP1")
	@Config(key = "ATT.TEMP1")
	public BigDecimal getTEMP1() {
		return TEMP1;
	}

	public void setTEMP1(BigDecimal TEMP1) {
		this.TEMP1 = TEMP1;
	}

	@Column(name = "TEMP2")
	@Config(key = "ATT.TEMP2")
	public BigDecimal getTEMP2() {
		return TEMP2;
	}

	public void setTEMP2(BigDecimal TEMP2) {
		this.TEMP2 = TEMP2;
	}

	@Column(name = "BAC")
	@Config(key = "ATT.BAC")
	public BigDecimal getBAC() {
		return BAC;
	}

	public void setBAC(BigDecimal BAC) {
		this.BAC = BAC;
	}
}
