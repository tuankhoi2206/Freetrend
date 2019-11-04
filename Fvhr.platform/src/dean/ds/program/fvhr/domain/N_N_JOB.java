package ds.program.fvhr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;

import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * THANG DIEM XEP LOAI CONG VIEC
 */
@Entity
@Table(name = "N_N_JOB")
public class N_N_JOB {
	private java.lang.String ID_JOB;

	private java.lang.String KIND; // VD: LOAI A,B,C

	private java.lang.Long MONEY;

	private java.util.Date DATE_B;

	// private java.util.Date DATE_E;
	private java.lang.String NAME;

	private java.lang.String UP_USER;

	private java.util.Date UP_DATE;

	/**
	 * @return ID_JOB
	 */
	@Id
	@NotBlank
	@Column(name = "ID_JOB")
	@Config(key = "N_N_JOB.ID_JOB")
	public java.lang.String getID_JOB() {
		return ID_JOB;
	}

	/**
	 * @param ID_JOB
	 */
	public void setID_JOB(java.lang.String ID_JOB) {
		this.ID_JOB = ID_JOB;
	}

	/**
	 * 取得VD: LOAI A,B,C
	 * 
	 * @return KIND VD: LOAI A,B,C
	 */
	@Length(max = 1)
	@Column(name = "KIND")
	@Config(key = "N_N_JOB.KIND")
	public java.lang.String getKIND() {
		return KIND;
	}

	/**
	 * 設定VD: LOAI A,B,C
	 * 
	 * @param KIND
	 *            VD: LOAI A,B,C
	 */
	public void setKIND(java.lang.String KIND) {
		this.KIND = KIND;
	}

	/**
	 * @return MONEY
	 */
	@Column(name = "MONEY")
	@Config(key = "N_N_JOB.MONEY")
	public java.lang.Long getMONEY() {
		return MONEY;
	}

	/**
	 * @param MONEY
	 */
	public void setMONEY(java.lang.Long MONEY) {
		this.MONEY = MONEY;
	}

	/**
	 * @return DATE_B
	 */
	@NotBlank
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_B")
	@Config(key = "N_N_JOB.DATE_B")
	public java.util.Date getDATE_B() {
		return DATE_B;
	}

	/**
	 * @param DATE_B
	 */
	public void setDATE_B(java.util.Date DATE_B) {
		this.DATE_B = DATE_B;
	}

	/**
	 * @return DATE_E
	 */
	/*
	 * @Transient @Temporal(TemporalType.DATE) @Column(name = "DATE_E")
	 * @Config(key = "N_N_JOB.DATE_E") public java.util.Date getDATE_E() {
	 * return DATE_E; }
	 *//**
		 * @param DATE_E
		 */
	/*
	 * public void setDATE_E(java.util.Date DATE_E) { this.DATE_E = DATE_E; }
	 */
	/**
	 * @return NAME
	 */
	@Length(max = 100)
	@Column(name = "NAME")
	@Config(key = "N_N_JOB.NAME")
	public java.lang.String getNAME() {
		return NAME;
	}

	/**
	 * @param NAME
	 */
	public void setNAME(java.lang.String NAME) {
		this.NAME = NAME;
	}

	@Length(max = 10)
	@Column(name = "UP_USER")
	@Config(key = "N_N_JOB.UP_USER")
	public java.lang.String getUP_USER() {
		return UP_USER;
	}

	public void setUP_USER(java.lang.String UP_USER) {
		this.UP_USER = UP_USER;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UP_DATE")
	@Config(key = "N_N_JOB.UP_DATE")
	public java.util.Date getUP_DATE() {
		return UP_DATE;
	}

	public void setUP_DATE(java.util.Date UP_DATE) {
		this.UP_DATE = UP_DATE;
	}
}
