package ds.program.fvhr.domain;

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
@Table(name = "N_ATM_MANAGER")
public class N_ATM_MANAGER {
	private java.lang.String EMPSN; // So the

	private java.lang.String ATM_CODE; // Ma atm lay ben bang atm_code them vao

	private java.lang.String CMND; // so cmnd

	private java.lang.String NO_ACC; // so tai khoan

	private java.lang.String BANK_NAME; // ten ngan hang

	private java.lang.String USER_UPDATED; // nguoi cap nhat gan nhat

	private java.lang.String DATE_UPDATE; // ngay cap nhat gan nhat

	private java.lang.String NOTE;

	/**
	 * 取得So the
	 * 
	 * @return EMPSN So the
	 */
	@NotBlank
	@Length(max = 8)
	@Column(name = "EMPSN")
	@Config(key = "N_ATM_MANAGER.EMPSN")
	public java.lang.String getEMPSN() {
		return EMPSN;
	}

	/**
	 * 設定So the
	 * 
	 * @param EMPSN
	 *            So the
	 */
	public void setEMPSN(java.lang.String EMPSN) {
		this.EMPSN = EMPSN;
	}

	/**
	 * 取得Ma atm lay ben bang atm_code them vao
	 * 
	 * @return ATM_CODE Ma atm lay ben bang atm_code them vao
	 */
	@Length(max = 10)
	@Column(name = "ATM_CODE")
	@Config(key = "N_ATM_MANAGER.ATM_CODE")
	public java.lang.String getATM_CODE() {
		return ATM_CODE;
	}

	/**
	 * 設定Ma atm lay ben bang atm_code them vao
	 * 
	 * @param ATM_CODE
	 *            Ma atm lay ben bang atm_code them vao
	 */
	public void setATM_CODE(java.lang.String ATM_CODE) {
		this.ATM_CODE = ATM_CODE;
	}

	/**
	 * 取得so cmnd
	 * 
	 * @return CMND so cmnd
	 */
	@Length(max = 9)
	@Column(name = "CMND")
	@Config(key = "N_ATM_MANAGER.CMND")
	public java.lang.String getCMND() {
		return CMND;
	}

	/**
	 * 設定so cmnd
	 * 
	 * @param CMND
	 *            so cmnd
	 */
	public void setCMND(java.lang.String CMND) {
		this.CMND = CMND;
	}

	/**
	 * 取得so tai khoan
	 * 
	 * @return NO_ACC so tai khoan
	 */
	@Id
	@NotBlank
	@Column(name = "NO_ACC")
	@Config(key = "N_ATM_MANAGER.NO_ACC")
	public java.lang.String getNO_ACC() {
		return NO_ACC;
	}

	/**
	 * 設定so tai khoan
	 * 
	 * @param NO_ACC
	 *            so tai khoan
	 */
	public void setNO_ACC(java.lang.String NO_ACC) {
		this.NO_ACC = NO_ACC;
	}

	/**
	 * 取得ten ngan hang
	 * 
	 * @return BANK_NAME ten ngan hang
	 */
	@Length(max = 30)
	@Column(name = "BANK_NAME")
	@Config(key = "N_ATM_MANAGER.BANK_NAME")
	public java.lang.String getBANK_NAME() {
		return BANK_NAME;
	}

	/**
	 * 設定ten ngan hang
	 * 
	 * @param BANK_NAME
	 *            ten ngan hang
	 */
	public void setBANK_NAME(java.lang.String BANK_NAME) {
		this.BANK_NAME = BANK_NAME;
	}

	/**
	 * 取得nguoi cap nhat gan nhat
	 * 
	 * @return USER_UPDATED nguoi cap nhat gan nhat
	 */
	@Length(max = 20)
	@Column(name = "USER_UPDATED")
	@Config(key = "N_ATM_MANAGER.USER_UPDATED")
	public java.lang.String getUSER_UPDATED() {
		return USER_UPDATED;
	}

	/**
	 * 設定nguoi cap nhat gan nhat
	 * 
	 * @param USER_UPDATED
	 *            nguoi cap nhat gan nhat
	 */
	public void setUSER_UPDATED(java.lang.String USER_UPDATED) {
		this.USER_UPDATED = USER_UPDATED;
	}

	/**
	 * 取得ngay cap nhat gan nhat
	 * 
	 * @return DATE_UPDATE ngay cap nhat gan nhat
	 */
	@Length(max = 12)
	@Column(name = "DATE_UPDATE")
	@Config(key = "N_ATM_MANAGER.DATE_UPDATE")
	public java.lang.String getDATE_UPDATE() {
		return DATE_UPDATE;
	}

	/**
	 * 設定ngay cap nhat gan nhat
	 * 
	 * @param DATE_UPDATE
	 *            ngay cap nhat gan nhat
	 */
	public void setDATE_UPDATE(java.lang.String DATE_UPDATE) {
		this.DATE_UPDATE = DATE_UPDATE;
	}

	/**
	 * @return NOTE
	 */
	@Length(max = 100)
	@Column(name = "NOTE")
	@Config(key = "N_ATM_MANAGER.NOTE")
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
