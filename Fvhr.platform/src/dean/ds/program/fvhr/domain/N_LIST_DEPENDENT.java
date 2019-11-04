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
@Table(name = "N_LIST_DEPENDENT")
public class N_LIST_DEPENDENT {
	private java.lang.String EMPSN; // so the

	private java.lang.String DEPSN; // Don vi

	private java.lang.Long DEPENDENT; // so nguoi le thuoc

	private java.lang.String USER_UPDATED; // nguoi cap nhat cuoi cung

	private java.lang.String DATE_UPDATED; // ngay cap nhat

	private java.lang.String NOTE; // Nghi chu

	/**
	 * 取得so the
	 * 
	 * @return EMPSN so the
	 */
	@Id
	@NotBlank
	@Length(max = 8)
	@Column(name = "EMPSN")
	@Config(key = "N_LIST_DEPENDENT.EMPSN")
	public java.lang.String getEMPSN() {
		return EMPSN;
	}

	/**
	 * 設定so the
	 * 
	 * @param EMPSN
	 *            so the
	 */
	public void setEMPSN(java.lang.String EMPSN) {
		this.EMPSN = EMPSN;
	}

	/**
	 * 取得Don vi
	 * 
	 * @return DEPSN Don vi
	 */
	@Length(max = 5)
	@Column(name = "DEPSN")
	@Config(key = "N_LIST_DEPENDENT.DEPSN")
	public java.lang.String getDEPSN() {
		return DEPSN;
	}

	/**
	 * 設定Don vi
	 * 
	 * @param DEPSN
	 *            Don vi
	 */
	public void setDEPSN(java.lang.String DEPSN) {
		this.DEPSN = DEPSN;
	}

	/**
	 * 取得so nguoi le thuoc
	 * 
	 * @return DEPENDENT so nguoi le thuoc
	 */
	@Column(name = "DEPENDENT")
	@Config(key = "N_LIST_DEPENDENT.DEPENDENT")
	public java.lang.Long getDEPENDENT() {
		return DEPENDENT;
	}

	/**
	 * 設定so nguoi le thuoc
	 * 
	 * @param DEPENDENT
	 *            so nguoi le thuoc
	 */
	public void setDEPENDENT(java.lang.Long DEPENDENT) {
		this.DEPENDENT = DEPENDENT;
	}

	/**
	 * 取得nguoi cap nhat cuoi cung
	 * 
	 * @return USER_UPDATED nguoi cap nhat cuoi cung
	 */
	@Length(max = 20)
	@Column(name = "USER_UPDATED")
	@Config(key = "N_LIST_DEPENDENT.USER_UPDATED")
	public java.lang.String getUSER_UPDATED() {
		return USER_UPDATED;
	}

	/**
	 * 設定nguoi cap nhat cuoi cung
	 * 
	 * @param USER_UPDATED
	 *            nguoi cap nhat cuoi cung
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
	@Config(key = "N_LIST_DEPENDENT.DATE_UPDATED")
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
	 * 取得Nghi chu
	 * 
	 * @return NOTE Nghi chu
	 */
	@Length(max = 100)
	@Column(name = "NOTE")
	@Config(key = "N_LIST_DEPENDENT.NOTE")
	public java.lang.String getNOTE() {
		return NOTE;
	}

	/**
	 * 設定Nghi chu
	 * 
	 * @param NOTE
	 *            Nghi chu
	 */
	public void setNOTE(java.lang.String NOTE) {
		this.NOTE = NOTE;
	}
}
