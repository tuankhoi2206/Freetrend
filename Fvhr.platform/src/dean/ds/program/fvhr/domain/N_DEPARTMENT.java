package ds.program.fvhr.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.Length;
import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * 
 */
@Entity
@Table(name = "N_DEPARTMENT")
public class N_DEPARTMENT {
	
	private java.lang.String ID_DEPT;

	private java.lang.String NAME_DEPT;

	private java.lang.String ID_SPDEPT; // NHOM TINH TANG CA

	private java.lang.String NAME_FACT;

	private java.lang.String NAME_GROUP;

	private java.lang.String NAME_DEPT_NAME;

	private java.lang.String NOTE;

	private java.lang.String DEPGB; // MA DV KE TOAN

	private java.lang.String DD_BONUS;

	private java.lang.String CR_DEPT;

	private java.lang.String MG_DEPT;

	private java.lang.String ATM; // CO SD ATM

	private BigDecimal ID_GROUPNI; // MA DV DUA THEO NIKE

	private java.lang.String ID_GROUP_DIRECT;

	// private java .lang.String ID_GROUP_DIRECT_KT;

	/**
	 * @return ID_GROUP_DIRECT
	 */
	@Length(max = 15)
	@Column(name = "ID_GROUP_DIRECT")
	@Config(key = "N_DEPARTMENT.ID_GROUP_DIRECT")
	public java.lang.String getID_GROUP_DIRECT() {
		return ID_GROUP_DIRECT;
	}

	/**
	 * @param ID_GROUP_DIRECT
	 */
	public void setID_GROUP_DIRECT(java.lang.String ID_GROUP_DIRECT) {
		this.ID_GROUP_DIRECT = ID_GROUP_DIRECT;
	}

	/*	*//**
			 * @return ID_GROUP_DIRECT_KT
			 */
	/*
	 * @Length(max = 15)
	 * 
	 * @Column(name = "ID_GROUP_DIRECT_KT")
	 * 
	 * @Config(key = "N_DEPARTMENT.ID_GROUP_DIRECT_KT") public java.lang.String
	 * getID_GROUP_DIRECT_KT() { return ID_GROUP_DIRECT_KT; }
	 *//**
		 * @param ID_GROUP_DIRECT
		 */

	/*
	 * public void setID_GROUP_DIRECT_KT(java.lang.String ID_GROUP_DIRECT_KT) {
	 * this.ID_GROUP_DIRECT_KT = ID_GROUP_DIRECT_KT; }
	 */
	/**
	 * @return ID_DEPT
	 */
	@Id
	@NotBlank
	@Column(name = "ID_DEPT")
	@Config(key = "N_DEPARTMENT.ID_DEPT")
	public java.lang.String getID_DEPT() {
		return ID_DEPT;
	}

	/**
	 * @param ID_DEPT
	 */
	public void setID_DEPT(java.lang.String ID_DEPT) {
		this.ID_DEPT = ID_DEPT;
	}

	/**
	 * @return NAME_DEPT
	 */
	@Length(max = 100)
	@Column(name = "NAME_DEPT")
	@Config(key = "N_DEPARTMENT.NAME_DEPT")
	public java.lang.String getNAME_DEPT() {
		return NAME_DEPT;
	}

	/**
	 * @param NAME_DEPT
	 */
	public void setNAME_DEPT(java.lang.String NAME_DEPT) {
		this.NAME_DEPT = NAME_DEPT;
	}

	/**
	 * 取得NHOM TINH TANG CA
	 * 
	 * @return ID_SPDEPT NHOM TINH TANG CA
	 */
	@NotBlank
	@Length(max = 3)
	@Column(name = "ID_SPDEPT")
	@Config(key = "N_DEPARTMENT.ID_SPDEPT")
	public java.lang.String getID_SPDEPT() {
		return ID_SPDEPT;
	}

	/**
	 * 設定NHOM TINH TANG CA
	 * 
	 * @param ID_SPDEPT NHOM TINH TANG CA
	 */
	public void setID_SPDEPT(java.lang.String ID_SPDEPT) {
		this.ID_SPDEPT = ID_SPDEPT;
	}

	/**
	 * @return NAME_FACT
	 */
	@Length(max = 20)
	@Column(name = "NAME_FACT")
	@Config(key = "N_DEPARTMENT.NAME_FACT")
	public java.lang.String getNAME_FACT() {
		return NAME_FACT;
	}

	/**
	 * @param NAME_FACT
	 */
	public void setNAME_FACT(java.lang.String NAME_FACT) {
		this.NAME_FACT = NAME_FACT;
	}

	/**
	 * @return NAME_GROUP
	 */
	@Length(max = 20)
	@Column(name = "NAME_GROUP")
	@Config(key = "N_DEPARTMENT.NAME_GROUP")
	public java.lang.String getNAME_GROUP() {
		return NAME_GROUP;
	}

	/**
	 * @param NAME_GROUP
	 */
	public void setNAME_GROUP(java.lang.String NAME_GROUP) {
		this.NAME_GROUP = NAME_GROUP;
	}

	/**
	 * @return NAME_DEPT_NAME
	 */
	@Length(max = 50)
	@Column(name = "NAME_DEPT_NAME")
	@Config(key = "N_DEPARTMENT.NAME_DEPT_NAME")
	public java.lang.String getNAME_DEPT_NAME() {
		return NAME_DEPT_NAME;
	}

	/**
	 * @param NAME_DEPT_NAME
	 */
	public void setNAME_DEPT_NAME(java.lang.String NAME_DEPT_NAME) {
		this.NAME_DEPT_NAME = NAME_DEPT_NAME;
	}

	/**
	 * @return NOTE
	 */
	@Length(max = 100)
	@Column(name = "NOTE")
	@Config(key = "N_DEPARTMENT.NOTE")
	public java.lang.String getNOTE() {
		return NOTE;
	}

	/**
	 * @param NOTE
	 */
	public void setNOTE(java.lang.String NOTE) {
		this.NOTE = NOTE;
	}

	/**
	 * 取得MA DV KE TOAN
	 * 
	 * @return DEPGB MA DV KE TOAN
	 */
	@Length(max = 2)
	@Column(name = "DEPGB")
	@Config(key = "N_DEPARTMENT.DEPGB")
	public java.lang.String getDEPGB() {
		return DEPGB;
	}

	/**
	 * 設定MA DV KE TOAN
	 * 
	 * @param DEPGB MA DV KE TOAN
	 */
	public void setDEPGB(java.lang.String DEPGB) {
		this.DEPGB = DEPGB;
	}

	/**
	 * @return DD_BONUS
	 */
	@Length(max = 3)
	@Column(name = "D_BONUS")
	@Config(key = "N_DEPARTMENT.D_BONUS")
	public java.lang.String getDD_BONUS() {
		return DD_BONUS;
	}

	/**
	 * @param DD_BONUS
	 */
	public void setDD_BONUS(java.lang.String DD_BONUS) {
		this.DD_BONUS = DD_BONUS;
	}

	/**
	 * @return CR_DEPT
	 */
	@Length(max = 20)
	@Column(name = "CR_DEPT")
	@Config(key = "N_DEPARTMENT.CR_DEPT")
	public java.lang.String getCR_DEPT() {
		return CR_DEPT;
	}

	/**
	 * @param CR_DEPT
	 */
	public void setCR_DEPT(java.lang.String CR_DEPT) {
		this.CR_DEPT = CR_DEPT;
	}

	/**
	 * @return MG_DEPT
	 */
	@Length(max = 20)
	@Column(name = "MG_DEPT")
	@Config(key = "N_DEPARTMENT.MG_DEPT")
	public java.lang.String getMG_DEPT() {
		return MG_DEPT;
	}

	/**
	 * @param MG_DEPT
	 */
	public void setMG_DEPT(java.lang.String MG_DEPT) {
		this.MG_DEPT = MG_DEPT;
	}

	/**
	 * 取得CO SD ATM
	 * 
	 * @return ATM CO SD ATM
	 */
	@Length(max = 1)
	@Column(name = "ATM")
	@Config(key = "N_DEPARTMENT.ATM")
	public java.lang.String getATM() {
		return ATM;
	}

	/**
	 * 設定CO SD ATM
	 * 
	 * @param ATM CO SD ATM
	 */
	public void setATM(java.lang.String ATM) {
		this.ATM = ATM;
	}

	/**
	 * 取得MA DV DUA THEO NIKE
	 * 
	 * @return ID_GROUPNI MA DV DUA THEO NIKE
	 */
	/**
	 * 取得MA DV DUA THEO NIKE
	 * 
	 * @return ID_GROUPNI MA DV DUA THEO NIKE
	 */
	@Column(name = "ID_GROUPNI")
	@Config(key = "N_DEPARTMENT.ID_GROUPNI")
	public BigDecimal getID_GROUPNI() {
		return ID_GROUPNI;
	}

	/**
	 * 設定MA DV DUA THEO NIKE
	 * 
	 * @param ID_GROUPNI MA DV DUA THEO NIKE
	 */
	public void setID_GROUPNI(BigDecimal ID_GROUPNI) {
		this.ID_GROUPNI = ID_GROUPNI;
	}

	@Override
	public String toString() {
		return NAME_DEPT;
	}

}
