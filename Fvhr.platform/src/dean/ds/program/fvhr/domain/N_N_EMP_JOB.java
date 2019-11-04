package ds.program.fvhr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;

import ds.program.fvhr.domain.pk.N_N_EMP_JOBPk;
import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * 
 */
@IdClass(N_N_EMP_JOBPk.class)
@Entity
@Table(name = "N_N_EMP_JOB")
public class N_N_EMP_JOB {
	private java.lang.String EMPSN;

	private java.lang.String ID_JOB;

	private java.util.Date DATE_B;

	// private java.util.Date DATE_E;
	private java.lang.String NOTE;

	private java.lang.String UP_USER;

	private java.util.Date UP_DATE;

	/**
	 * @return EMPSN
	 */
	@Id
	@NotBlank
	@Column(name = "EMPSN")
	@Config(key = "N_N_EMP_JOB.EMPSN")
	public java.lang.String getEMPSN() {
		return EMPSN;
	}

	/**
	 * @param EMPSN
	 */
	public void setEMPSN(java.lang.String EMPSN) {
		this.EMPSN = EMPSN;
	}

	/**
	 * @return ID_JOB
	 */
	@Id
	@NotBlank
	@Column(name = "ID_JOB")
	@Config(key = "N_N_EMP_JOB.ID_JOB")
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
	 * @return DATE_B
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_B")
	@Config(key = "N_N_EMP_JOB.DATE_B")
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
	 * @Temporal(TemporalType.DATE) @Column(name = "DATE_E") @Config(key =
	 * "N_N_EMP_JOB.DATE_E") public java.util.Date getDATE_E() { return DATE_E; }
	 *//**
		 * @param DATE_E
		 */
	/*
	 * public void setDATE_E(java.util.Date DATE_E) { this.DATE_E = DATE_E; }
	 */
	/**
	 * @return NOTE
	 */
	@Length(max = 200)
	@Column(name = "NOTE")
	@Config(key = "N_N_EMP_JOB.NOTE")
	public java.lang.String getNOTE() {
		return NOTE;
	}

	/**
	 * @param NOTE
	 */
	public void setNOTE(java.lang.String NOTE) {
		this.NOTE = NOTE;
	}

	@Length(max = 10)
	@Column(name = "UP_USER")
	@Config(key = "N_N_EMP_JOB.UP_USER")
	public java.lang.String getUP_USER() {
		return UP_USER;
	}

	public void setUP_USER(java.lang.String UP_USER) {
		this.UP_USER = UP_USER;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UP_DATE")
	@Config(key = "N_N_EMP_JOB.UP_DATE")
	public java.util.Date getUP_DATE() {
		return UP_DATE;
	}

	public void setUP_DATE(java.util.Date UP_DATE) {
		this.UP_DATE = UP_DATE;
	}
}
