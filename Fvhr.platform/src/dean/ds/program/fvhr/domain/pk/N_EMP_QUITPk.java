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
public class N_EMP_QUITPk implements Serializable {
	private static final long serialVersionUID = 7859045010991918462L;

	private java.lang.String EMPSN; // SO THE

	private java.util.Date REAL_OFF_DATE; // NGAY THUC NGHI

	public N_EMP_QUITPk() {
		super();
	}

	public N_EMP_QUITPk(java.lang.String EMPSN, java.util.Date REAL_OFF_DATE) {
		super();
		this.EMPSN = EMPSN;
		this.REAL_OFF_DATE = REAL_OFF_DATE;
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
	 * 取得NGAY THUC NGHI
	 * 
	 * @returnREAL_OFF_DATE NGAY THUC NGHI
	 */
	@NotBlank
	@Temporal(TemporalType.DATE)
	@Column(name = "REAL_OFF_DATE")
	public java.util.Date getREAL_OFF_DATE() {
		return REAL_OFF_DATE;
	}

	/**
	 * 設定NGAY THUC NGHI
	 * 
	 * @param REAL_OFF_DATE
	 *            NGAY THUC NGHI
	 */
	public void setREAL_OFF_DATE(java.util.Date REAL_OFF_DATE) {
		this.REAL_OFF_DATE = REAL_OFF_DATE;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof N_EMP_QUITPk))
			return false;
		N_EMP_QUITPk castOther = (N_EMP_QUITPk) other;
		return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(
				REAL_OFF_DATE, castOther.REAL_OFF_DATE).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(EMPSN).append(REAL_OFF_DATE)
				.toHashCode();
	}
}
