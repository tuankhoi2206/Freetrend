package ds.program.fvhr.domain.pk;

import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.Length;

import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

@Embeddable
public class N_DETAIL_SUB_INSURANCEPk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.lang.String EMPSN;
	private int LANCHAY;
	private java.lang.String PURCHASE_DATE; // thang dong tien bao hiem
	

	public N_DETAIL_SUB_INSURANCEPk() {
		super();
	}

	public N_DETAIL_SUB_INSURANCEPk(java.lang.String EMPSN,
			int LANCHAY, java.lang.String PURCHASE_DATE) {
		super();
		this.EMPSN = EMPSN;
		this.LANCHAY = LANCHAY;
		this.PURCHASE_DATE = PURCHASE_DATE;
	}

	/**
	 * @returnEMPSN
	 */
	@NotBlank
	@Column(name = "EMPSN", length = 8)
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
	 * @returnLANCHAY
	 */
	@NotBlank
	@Column(name = "LANCHAY")
	public int getLANCHAY() {
		return LANCHAY;
	}

	/**
	 * @param LANCHAY
	 */
	public void setLANCHAY(int LANCHAY) {
		this.LANCHAY = LANCHAY;
	}

	/**
	 * 取得thang dong tien bao hiem
	 * 
	 * @returnPURCHASE_DATE thang dong tien bao hiem
	 */
	@NotBlank
	@Column(name = "PURCHASE_DATE", length = 8)
	public java.lang.String getPURCHASE_DATE() {
		return PURCHASE_DATE;
	}

	/**
	 * 設定thang dong tien bao hiem
	 * 
	 * @param PURCHASE_DATE
	 *            thang dong tien bao hiem
	 */
	public void setPURCHASE_DATE(java.lang.String PURCHASE_DATE) {
		this.PURCHASE_DATE = PURCHASE_DATE;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof N_DETAIL_SUB_INSURANCEPk))
			return false;
		N_DETAIL_SUB_INSURANCEPk castOther = (N_DETAIL_SUB_INSURANCEPk) other;
		return new EqualsBuilder().append(EMPSN, castOther.EMPSN)
				.append(LANCHAY, castOther.LANCHAY)
				.append(PURCHASE_DATE, castOther.PURCHASE_DATE).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(EMPSN).append(LANCHAY)
				.append(PURCHASE_DATE).toHashCode();
	}
}