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
 * TIEN THUONG, KHAU TRU
 */
@Entity
@Table(name = "N_N_EMP_OTHER_BONUS")
public class N_N_EMP_OTHER_BONUS {
	private java.lang.String EMPSN;

	private java.lang.Long BONUS1; // TIEN THUONG

	private java.lang.Long BONUS5; // BU LUONG THANG TRUOC

	private java.util.Date DATE_B1; // THANG HIEU LUC

	private java.util.Date DATE_B5; // THANG HIEU LUC

	private java.lang.String NOTE;

	/**
	 * @return EMPSN
	 */
	@Id
	@NotBlank
	@Column(name = "EMPSN")
	@Config(key = "N_N_EMP_OTHER_BONUS.EMPSN")
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
	 * 取得TIEN THUONG
	 * 
	 * @return BONUS1 TIEN THUONG
	 */
	@Column(name = "BONUS1")
	@Config(key = "N_N_EMP_OTHER_BONUS.BONUS1")
	public java.lang.Long getBONUS1() {
		return BONUS1;
	}

	/**
	 * 設定TIEN THUONG
	 * 
	 * @param BONUS1
	 *            TIEN THUONG
	 */
	public void setBONUS1(java.lang.Long BONUS1) {
		this.BONUS1 = BONUS1;
	}

	/**
	 * 取得BU LUONG THANG TRUOC
	 * 
	 * @return BONUS5 BU LUONG THANG TRUOC
	 */
	@Column(name = "BONUS5")
	@Config(key = "N_N_EMP_OTHER_BONUS.BONUS5")
	public java.lang.Long getBONUS5() {
		return BONUS5;
	}

	/**
	 * 設定BU LUONG THANG TRUOC
	 * 
	 * @param BONUS5
	 *            BU LUONG THANG TRUOC
	 */
	public void setBONUS5(java.lang.Long BONUS5) {
		this.BONUS5 = BONUS5;
	}

	/**
	 * 取得THANG HIEU LUC
	 * 
	 * @return DATE_IN THANG HIEU LUC
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_B1")
	@Config(key = "N_N_EMP_OTHER_BONUS.DATE_B1")
	public java.util.Date getDATE_B1() {
		return DATE_B1;
	}

	/**
	 * 設定THANG HIEU LUC
	 * 
	 * @param DATE_IN
	 *            THANG HIEU LUC
	 */
	public void setDATE_B1(java.util.Date DATE_B1) {
		this.DATE_B1 = DATE_B1;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_B5")
	@Config(key = "N_N_EMP_OTHER_BONUS.DATE_B5")
	public java.util.Date getDATE_B5() {
		return DATE_B5;
	}

	public void setDATE_B5(java.util.Date DATE_B5) {
		this.DATE_B5 = DATE_B5;
	}

	/**
	 * @return NOTE
	 */
	@Length(max = 200)
	@Column(name = "NOTE")
	@Config(key = "N_N_EMP_OTHER_BONUS.NOTE")
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
