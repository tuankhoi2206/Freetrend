package ds.program.fvhr.domain.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import dsc.util.hibernate.validator.NotBlank;

@Embeddable
public class N_DEDUCT_OTHERPk implements Serializable {
	private java.lang.String EMPSN; // so the

	private java.util.Date MONTH_DEDUCT; // thang khau tru

	public N_DEDUCT_OTHERPk() {
		super();
	}

	public N_DEDUCT_OTHERPk(java.lang.String EMPSN, java.util.Date MONTH_DEDUCT) {
		super();
		this.EMPSN = EMPSN;
		this.MONTH_DEDUCT = MONTH_DEDUCT;
	}

	/**
	 * 取得so the
	 * 
	 * @returnEMPSN so the
	 */
	@NotBlank
	@Column(name = "EMPSN", length = 8)
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
	 * 取得thang khau tru
	 * 
	 * @returnMONTH_DEDUCT thang khau tru
	 */
	@NotBlank
	@Temporal(TemporalType.DATE)
	@Column(name = "MONTH_DEDUCT")
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

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof N_DEDUCT_OTHERPk))
			return false;
		N_DEDUCT_OTHERPk castOther = (N_DEDUCT_OTHERPk) other;
		return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(
				MONTH_DEDUCT, castOther.MONTH_DEDUCT).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(EMPSN).append(MONTH_DEDUCT)
				.toHashCode();
	}
}
