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
@Table(name = "N_ATM_CODE")
public class N_ATM_CODE {
	private java.lang.String ATM_CODE; // ma atm do ke toan dat

	private java.lang.String USER_UPDATED; // nguoi cap nhat

	private java.lang.String DATE_UPDATED; // ngay cap nhat

	private java.lang.String NODE; // nghi chu

	private java.lang.String DEPSN; // don vi

	/**
	 * 取得ma atm do ke toan dat
	 * 
	 * @return ATM_CODE ma atm do ke toan dat
	 */
	@Id
	@NotBlank
	@Column(name = "ATM_CODE")
	@Config(key = "N_ATM_CODE.ATM_CODE")
	public java.lang.String getATM_CODE() {
		return ATM_CODE;
	}

	/**
	 * 設定ma atm do ke toan dat
	 * 
	 * @param ATM_CODE
	 *            ma atm do ke toan dat
	 */
	public void setATM_CODE(java.lang.String ATM_CODE) {
		this.ATM_CODE = ATM_CODE;
	}

	/**
	 * 取得nguoi cap nhat
	 * 
	 * @return USER_UPDATED nguoi cap nhat
	 */
	@Length(max = 20)
	@Column(name = "USER_UPDATED")
	@Config(key = "N_ATM_CODE.USER_UPDATED")
	public java.lang.String getUSER_UPDATED() {
		return USER_UPDATED;
	}

	/**
	 * 設定nguoi cap nhat
	 * 
	 * @param USER_UPDATED
	 *            nguoi cap nhat
	 */
	public void setUSER_UPDATED(java.lang.String USER_UPDATED) {
		this.USER_UPDATED = USER_UPDATED;
	}

	/**
	 * 取得ngay cap nhat
	 * 
	 * @return DATE_UPDATED ngay cap nhat
	 */
	@Length(max = 12)
	@Column(name = "DATE_UPDATED")
	@Config(key = "N_ATM_CODE.DATE_UPDATED")
	public java.lang.String getDATE_UPDATED() {
		return DATE_UPDATED;
	}

	/**
	 * 設定ngay cap nhat
	 * 
	 * @param DATE_UPDATED
	 *            ngay cap nhat
	 */
	public void setDATE_UPDATED(java.lang.String DATE_UPDATED) {

		this.DATE_UPDATED = DATE_UPDATED;
	}

	/**
	 * 取得nghi chu
	 * 
	 * @return NODE nghi chu
	 */
	@Length(max = 100)
	@Column(name = "NODE")
	@Config(key = "N_ATM_CODE.NODE")
	public java.lang.String getNODE() {
		return NODE;
	}

	/**
	 * 設定nghi chu
	 * 
	 * @param NODE
	 *            nghi chu
	 */
	public void setNODE(java.lang.String NODE) {
		this.NODE = NODE;
	}

	/**
	 * 取得don vi
	 * 
	 * @return DEPSN don vi
	 */
	@Length(max = 5)
	@Column(name = "DEPSN")
	@Config(key = "N_ATM_CODE.DEPSN")
	public java.lang.String getDEPSN() {
		return DEPSN;
	}

	/**
	 * 設定don vi
	 * 
	 * @param DEPSN
	 *            don vi
	 */
	public void setDEPSN(java.lang.String DEPSN) {
		this.DEPSN = DEPSN;
	}
}
