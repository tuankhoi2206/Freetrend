package ds.program.fvhr.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;

import ds.program.fvhr.domain.pk.N_DEDUCT_OTHERPk;
import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * 
 */
@IdClass(N_DEDUCT_OTHERPk.class)
@Entity
@Table(name = "N_DEDUCT_OTHER")
public class N_DEDUCT_OTHER {
	private java.lang.String EMPSN; // so the

	private java.math.BigDecimal TOTAL_DEDUCT; // tong so khau tru

	private java.util.Date MONTH_DEDUCT; // thang khau tru

	private java.lang.String NOTE; // ghi chu

	private java.lang.String USER_UPDATED; // nguoi cap nhat

	private java.util.Date DATE_UPDATED; // ngay cap nhat

	private java.lang.Long DEDUCTED; // so tien con lai sau khi tru
	
	private java.math.BigDecimal QT_PAYTAX; // QT thue TNCN neu co
	

	/**
	 * 取得so the
	 * 
	 * @return EMPSN so the
	 */
	@Id
	@NotBlank
	@Column(name = "EMPSN")
	@Config(key = "N_DEDUCT_OTHER.EMPSN")
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
	 * 取得tong so khau tru
	 * 
	 * @return TOTAL_DEDUCT tong so khau tru
	 */
	@Column(name = "TOTAL_DEDUCT")
	@Config(key = "N_DEDUCT_OTHER.TOTAL_DEDUCT")
	public BigDecimal getTOTAL_DEDUCT() {
		return TOTAL_DEDUCT;
	}

	/**
	 * 設定tong so khau tru
	 * 
	 * @param TOTAL_DEDUCT
	 *            tong so khau tru
	 */
	public void setTOTAL_DEDUCT(BigDecimal TOTAL_DEDUCT) {
		this.TOTAL_DEDUCT = TOTAL_DEDUCT;
	}

	/**
	 * 取得thang khau tru
	 * 
	 * @return MONTH_DEDUCT thang khau tru
	 */
	@Id
	@NotBlank
	@Temporal(TemporalType.DATE)
	@Column(name = "MONTH_DEDUCT")
	@Config(key = "N_DEDUCT_OTHER.MONTH_DEDUCT")
	public java.util.Date getMONTH_DEDUCT() {
		return MONTH_DEDUCT;
	}

	/**
	 * 設定thang khau tru
	 * 
	 * @param MONTH_DEDUCT
	 *            thang khau tru
	 */
	public void setMONTH_DEDUCT(java.util.Date MONTH_DEDUCT) {
		this.MONTH_DEDUCT = MONTH_DEDUCT;
	}

	/**
	 * 取得ghi chu
	 * 
	 * @return NOTE ghi chu
	 */
	@Length(max = 500)
	@Column(name = "NOTE")
	@Config(key = "N_DEDUCT_OTHER.NOTE")
	public java.lang.String getNOTE() {
		return NOTE;
	}

	/**
	 * 設定ghi chu
	 * 
	 * @param NOTE
	 *            ghi chu
	 */
	public void setNOTE(java.lang.String NOTE) {
		this.NOTE = NOTE;
	}

	/**
	 * 取得nguoi cap nhat
	 * 
	 * @return USER_UPDATED nguoi cap nhat
	 */
	@Length(max = 20)
	@Column(name = "USER_UPDATED")
	@Config(key = "N_DEDUCT_OTHER.USER_UPDATED")
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
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_UPDATED")
	@Config(key = "N_DEDUCT_OTHER.DATE_UPDATED")
	public java.util.Date getDATE_UPDATED() {
		return DATE_UPDATED;
	}

	/**
	 * 設定ngay cap nhat
	 * 
	 * @param DATE_UPDATED
	 *            ngay cap nhat
	 */
	public void setDATE_UPDATED(java.util.Date DATE_UPDATED) {
		this.DATE_UPDATED = DATE_UPDATED;
	}

	/**
	 * 取得so tien con lai sau khi tru
	 * 
	 * @return DEDUCTED so tien con lai sau khi tru
	 */
	@Column(name = "DEDUCTED")
	@Config(key = "N_DEDUCT_OTHER.DEDUCTED")
	public java.lang.Long getDEDUCTED() {
		return DEDUCTED;
	}

	/**
	 * 設定so tien con lai sau khi tru
	 * 
	 * @param DEDUCTED
	 *            so tien con lai sau khi tru
	 */
	public void setDEDUCTED(java.lang.Long DEDUCTED) {
		this.DEDUCTED = DEDUCTED;
	}
	
	@Column(name = "QT_PAYTAX")
	@Config(key = "N_DEDUCT_OTHER.QT_PAYTAX")
	public BigDecimal getQT_PAYTAX() {
		return QT_PAYTAX;
	}

	/**
	 * 設定QT thue TNCN cho nam neu co
	 * 
	 * @param QT_PAYTAX
	 *            QT thue TNCN cho nam neu co
	 */
	public void setQT_PAYTAX(BigDecimal QT_PAYTAX) {
		this.QT_PAYTAX = QT_PAYTAX;
	}	
}
