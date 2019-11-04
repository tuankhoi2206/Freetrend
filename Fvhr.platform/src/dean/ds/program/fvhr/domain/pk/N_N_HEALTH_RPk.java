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
public class N_N_HEALTH_RPk implements Serializable {
	private java.lang.String EMPSN;

	private java.util.Date DATE_B; // ngay bat dau tham gia quy BHYT

	public N_N_HEALTH_RPk() {
		super();
	}

	public N_N_HEALTH_RPk(java.lang.String EMPSN, java.util.Date DATE_B) {
		super();
		this.EMPSN = EMPSN;
		this.DATE_B = DATE_B;
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
	 * 取得ngay bat dau tham gia quy BHYT
	 * 
	 * @returnDATE_B ngay bat dau tham gia quy BHYT
	 */
	@NotBlank
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_B")
	public java.util.Date getDATE_B() {
		return DATE_B;
	}

	/**
	 * 設定ngay bat dau tham gia quy BHYT
	 * 
	 * @param DATE_B
	 *            ngay bat dau tham gia quy BHYT
	 */
	public void setDATE_B(java.util.Date DATE_B) {
		this.DATE_B = DATE_B;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof N_N_HEALTH_RPk))
			return false;
		N_N_HEALTH_RPk castOther = (N_N_HEALTH_RPk) other;
		return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(
				DATE_B, castOther.DATE_B).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(EMPSN).append(DATE_B).toHashCode();
	}
}
