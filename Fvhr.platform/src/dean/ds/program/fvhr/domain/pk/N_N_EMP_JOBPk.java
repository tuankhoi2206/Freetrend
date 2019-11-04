package ds.program.fvhr.domain.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import dsc.util.hibernate.validator.NotBlank;

@Embeddable
public class N_N_EMP_JOBPk implements Serializable {
	private java.lang.String EMPSN;

	private java.lang.String ID_JOB;

	public N_N_EMP_JOBPk() {
		super();
	}

	public N_N_EMP_JOBPk(java.lang.String EMPSN, java.lang.String ID_JOB) {
		super();
		this.EMPSN = EMPSN;
		this.ID_JOB = ID_JOB;
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
	 * @returnID_JOB
	 */
	@NotBlank
	@Column(name = "ID_JOB", length = 50)
	public java.lang.String getID_JOB() {
		return ID_JOB;
	}

	/**
	 * @param ID_JOB
	 */
	public void setID_JOB(java.lang.String ID_JOB) {
		this.ID_JOB = ID_JOB;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof N_N_EMP_JOBPk))
			return false;
		N_N_EMP_JOBPk castOther = (N_N_EMP_JOBPk) other;
		return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(
				ID_JOB, castOther.ID_JOB).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(EMPSN).append(ID_JOB).toHashCode();
	}
}
