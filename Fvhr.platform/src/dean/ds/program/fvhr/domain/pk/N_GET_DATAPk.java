package ds.program.fvhr.domain.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import dsc.util.hibernate.validator.NotBlank;

@Embeddable
public class N_GET_DATAPk implements Serializable {
	private static final long serialVersionUID = -6563819047868556566L;

	private java.lang.String EMPSN; // SO THE

	private java.lang.String MONTHS; // THANG

	private java.lang.String YEARS; // NAM

	public N_GET_DATAPk() {
		super();
	}

	public N_GET_DATAPk(java.lang.String EMPSN, java.lang.String MONTHS,
			java.lang.String YEARS) {
		super();
		this.EMPSN = EMPSN;
		this.MONTHS = MONTHS;
		this.YEARS = YEARS;
	}

	/**
	 * 取得SO THE
	 * 
	 * @returnEMPSN SO THE
	 */
	@NotBlank
	@Column(name = "EMPSN", length = 8)
	public java.lang.String getEMPSN() {
		return EMPSN;
	}

	/**
	 * 設定SO THE
	 * 
	 * @param EMPSN
	 *            SO THE
	 */
	public void setEMPSN(java.lang.String EMPSN) {
		this.EMPSN = EMPSN;
	}

	/**
	 * 取得THANG
	 * 
	 * @returnMONTHS THANG
	 */
	@NotBlank
	@Column(name = "MONTHS", length = 2)
	public java.lang.String getMONTHS() {
		return MONTHS;
	}

	/**
	 * 設定THANG
	 * 
	 * @param MONTHS
	 *            THANG
	 */
	public void setMONTHS(java.lang.String MONTHS) {
		this.MONTHS = MONTHS;
	}

	/**
	 * 取得NAM
	 * 
	 * @returnYEARS NAM
	 */
	@NotBlank
	@Column(name = "YEARS", length = 4)
	public java.lang.String getYEARS() {
		return YEARS;
	}

	/**
	 * 設定NAM
	 * 
	 * @param YEARS
	 *            NAM
	 */
	public void setYEARS(java.lang.String YEARS) {
		this.YEARS = YEARS;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof N_GET_DATAPk))
			return false;
		N_GET_DATAPk castOther = (N_GET_DATAPk) other;
		return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(
				MONTHS, castOther.MONTHS).append(YEARS, castOther.YEARS)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(EMPSN).append(MONTHS).append(YEARS)
				.toHashCode();
	}
}
