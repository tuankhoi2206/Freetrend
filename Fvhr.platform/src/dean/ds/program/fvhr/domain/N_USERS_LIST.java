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
 * HE THONG NGUOI DUNG
 */
@Entity
@Table(name = "N_USERS_LIST")
public class N_USERS_LIST {
	private java.lang.String USER_ID; // MA SO USER

	private java.lang.String USER_NAME; // TEN DANG NHAP

	private java.lang.String PASSWORD; // MAT KHAU

	private java.lang.String EMPSN; // SO THE DEO USER

	private java.lang.String ID_LIMIT; // NHUNG QUYEN DUOC SU DUNG

	private java.util.Date DATE_BEGIN; // NGAY BAT DAU DUNG

	private java.lang.String FULLNAME; // HO TEN USER

	private java.lang.String CLOCK; // KHOA SU DUNG

	private java.lang.String LOCKED; // KHOA XL DL

	private java.lang.String STATUS; // LOGIN/ NO LOGIN

	private java.lang.Long COUNT_USERS; // SO NGUOI LOGIN VOI CUNG 1 USER

	private java.lang.String NOTE;

	/**
	 * 取得MA SO USER
	 * 
	 * @return USER_ID MA SO USER
	 */
	@Id
	@NotBlank
	@Column(name = "USER_ID")
	@Config(key = "N_USERS_LIST.USER_ID")
	public java.lang.String getUSER_ID() {
		return USER_ID;
	}

	/**
	 * 設定MA SO USER
	 * 
	 * @param USER_ID
	 *            MA SO USER
	 */
	public void setUSER_ID(java.lang.String USER_ID) {
		this.USER_ID = USER_ID;
	}

	/**
	 * 取得TEN DANG NHAP
	 * 
	 * @return USER_NAME TEN DANG NHAP
	 */
	@Length(max = 30)
	@Column(name = "USER_NAME")
	@Config(key = "N_USERS_LIST.USER_NAME")
	public java.lang.String getUSER_NAME() {
		return USER_NAME;
	}

	/**
	 * 設定TEN DANG NHAP
	 * 
	 * @param USER_NAME
	 *            TEN DANG NHAP
	 */
	public void setUSER_NAME(java.lang.String USER_NAME) {
		this.USER_NAME = USER_NAME;
	}

	/**
	 * 取得MAT KHAU
	 * 
	 * @return PASSWORD MAT KHAU
	 */
	@Length(max = 20)
	@Column(name = "PASSWORD")
	@Config(key = "N_USERS_LIST.PASSWORD")
	public java.lang.String getPASSWORD() {
		return PASSWORD;
	}

	/**
	 * 設定MAT KHAU
	 * 
	 * @param PASSWORD
	 *            MAT KHAU
	 */
	public void setPASSWORD(java.lang.String PASSWORD) {
		this.PASSWORD = PASSWORD;
	}

	/**
	 * 取得SO THE DEO USER
	 * 
	 * @return EMPSN SO THE DEO USER
	 */
	@Length(max = 8)
	@Column(name = "EMPSN")
	@Config(key = "N_USERS_LIST.EMPSN")
	public java.lang.String getEMPSN() {
		return EMPSN;
	}

	/**
	 * 設定SO THE DEO USER
	 * 
	 * @param EMPSN
	 *            SO THE DEO USER
	 */
	public void setEMPSN(java.lang.String EMPSN) {
		this.EMPSN = EMPSN;
	}

	/**
	 * 取得NHUNG QUYEN DUOC SU DUNG
	 * 
	 * @return ID_LIMIT NHUNG QUYEN DUOC SU DUNG
	 */
	@NotBlank
	@Length(max = 3)
	@Column(name = "ID_LIMIT")
	@Config(key = "N_USERS_LIST.ID_LIMIT")
	public java.lang.String getID_LIMIT() {
		return ID_LIMIT;
	}

	/**
	 * 設定NHUNG QUYEN DUOC SU DUNG
	 * 
	 * @param ID_LIMIT
	 *            NHUNG QUYEN DUOC SU DUNG
	 */
	public void setID_LIMIT(java.lang.String ID_LIMIT) {
		this.ID_LIMIT = ID_LIMIT;
	}

	/**
	 * 取得NGAY BAT DAU DUNG
	 * 
	 * @return DATE_BEGIN NGAY BAT DAU DUNG
	 */
	@NotBlank
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_BEGIN")
	@Config(key = "N_USERS_LIST.DATE_BEGIN")
	public java.util.Date getDATE_BEGIN() {
		return DATE_BEGIN;
	}

	/**
	 * 設定NGAY BAT DAU DUNG
	 * 
	 * @param DATE_BEGIN
	 *            NGAY BAT DAU DUNG
	 */
	public void setDATE_BEGIN(java.util.Date DATE_BEGIN) {
		this.DATE_BEGIN = DATE_BEGIN;
	}

	/**
	 * 取得HO TEN USER
	 * 
	 * @return FULLNAME HO TEN USER
	 */
	@NotBlank
	@Length(max = 50)
	@Column(name = "FULLNAME")
	@Config(key = "N_USERS_LIST.FULLNAME")
	public java.lang.String getFULLNAME() {
		return FULLNAME;
	}

	/**
	 * 設定HO TEN USER
	 * 
	 * @param FULLNAME
	 *            HO TEN USER
	 */
	public void setFULLNAME(java.lang.String FULLNAME) {
		this.FULLNAME = FULLNAME;
	}

	/**
	 * 取得KHOA SU DUNG
	 * 
	 * @return CLOCK KHOA SU DUNG
	 */
	@NotBlank
	@Length(max = 3)
	@Column(name = "CLOCK")
	@Config(key = "N_USERS_LIST.CLOCK")
	public java.lang.String getCLOCK() {
		return CLOCK;
	}

	/**
	 * 設定KHOA SU DUNG
	 * 
	 * @param CLOCK
	 *            KHOA SU DUNG
	 */
	public void setCLOCK(java.lang.String CLOCK) {
		this.CLOCK = CLOCK;
	}

	/**
	 * 取得KHOA XL DL
	 * 
	 * @return LOCKED KHOA XL DL
	 */
	@Length(max = 1)
	@Column(name = "LOCKED")
	@Config(key = "N_USERS_LIST.LOCKED")
	public java.lang.String getLOCKED() {
		return LOCKED;
	}

	/**
	 * 設定KHOA XL DL
	 * 
	 * @param LOCKED
	 *            KHOA XL DL
	 */
	public void setLOCKED(java.lang.String LOCKED) {
		this.LOCKED = LOCKED;
	}

	/**
	 * 取得LOGIN/ NO LOGIN
	 * 
	 * @return STATUS LOGIN/ NO LOGIN
	 */
	@Length(max = 1)
	@Column(name = "STATUS")
	@Config(key = "N_USERS_LIST.STATUS")
	public java.lang.String getSTATUS() {
		return STATUS;
	}

	/**
	 * 設定LOGIN/ NO LOGIN
	 * 
	 * @param STATUS
	 *            LOGIN/ NO LOGIN
	 */
	public void setSTATUS(java.lang.String STATUS) {
		this.STATUS = STATUS;
	}

	/**
	 * 取得SO NGUOI LOGIN VOI CUNG 1 USER
	 * 
	 * @return COUNT_USERS SO NGUOI LOGIN VOI CUNG 1 USER
	 */
	@Column(name = "COUNT_USERS")
	@Config(key = "N_USERS_LIST.COUNT_USERS")
	public java.lang.Long getCOUNT_USERS() {
		return COUNT_USERS;
	}

	/**
	 * 設定SO NGUOI LOGIN VOI CUNG 1 USER
	 * 
	 * @param COUNT_USERS
	 *            SO NGUOI LOGIN VOI CUNG 1 USER
	 */
	public void setCOUNT_USERS(java.lang.Long COUNT_USERS) {
		this.COUNT_USERS = COUNT_USERS;
	}

	/**
	 * @return NOTE
	 */
	@Length(max = 50)
	@Column(name = "NOTE")
	@Config(key = "N_USERS_LIST.NOTE")
	public java.lang.String getNOTE() {
		return NOTE;
	}

	/**
	 * @param NOTE
	 */
	public void setNOTE(java.lang.String NOTE) {
		this.NOTE = NOTE;
	}
}
