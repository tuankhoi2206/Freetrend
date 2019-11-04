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
public class N_N_JOBPk implements Serializable {
	private java.lang.String ID_JOB;

	private java.util.Date DATE_B;

	public N_N_JOBPk() {
		super();
	}

	public N_N_JOBPk(java.lang.String ID_JOB, java.util.Date DATE_B) {
		super();
		this.ID_JOB = ID_JOB;
		this.DATE_B = DATE_B;
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

	/**
	 * @returnDATE_B
	 */
	@NotBlank
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_B")
	public java.util.Date getDATE_B() {
		return DATE_B;
	}

	/**
	 * @param DATE_B
	 */
	public void setDATE_B(java.util.Date DATE_B) {
		this.DATE_B = DATE_B;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof N_N_JOBPk))
			return false;
		N_N_JOBPk castOther = (N_N_JOBPk) other;
		return new EqualsBuilder().append(ID_JOB, castOther.ID_JOB).append(
				DATE_B, castOther.DATE_B).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(ID_JOB).append(DATE_B).toHashCode();
	}
}
