package ds.program.fvhr.domain.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import dsc.util.hibernate.validator.NotBlank;

@Embeddable
public class N_N_TCA_NGOAI_NVPk implements Serializable {
	private java.lang.String EMPSN;

	private java.lang.String THANG;

	private java.lang.String NAM;

	public N_N_TCA_NGOAI_NVPk() {
		super();
	}

	public N_N_TCA_NGOAI_NVPk(java.lang.String EMPSN, java.lang.String THANG,
			java.lang.String NAM) {
		super();
		this.EMPSN = EMPSN;
		this.THANG = THANG;
		this.NAM = NAM;
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
	 * @returnTHANG
	 */
	@NotBlank
	@Column(name = "THANG", length = 2)
	public java.lang.String getTHANG() {
		return THANG;
	}

	/**
	 * @param THANG
	 */
	public void setTHANG(java.lang.String THANG) {
		this.THANG = THANG;
	}

	/**
	 * @returnNAM
	 */
	@NotBlank
	@Column(name = "NAM", length = 4)
	public java.lang.String getNAM() {
		return NAM;
	}

	/**
	 * @param NAM
	 */
	public void setNAM(java.lang.String NAM) {
		this.NAM = NAM;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof N_N_TCA_NGOAI_NVPk))
			return false;
		N_N_TCA_NGOAI_NVPk castOther = (N_N_TCA_NGOAI_NVPk) other;
		return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(THANG,
				castOther.THANG).append(NAM, castOther.NAM).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(EMPSN).append(THANG).append(NAM)
				.toHashCode();
	}
}
