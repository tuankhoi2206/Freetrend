package ds.program.fvhr.domain.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import dsc.util.hibernate.validator.NotBlank;

@Embeddable
public class N_N_EMP_POSSPk implements Serializable {
	private java.lang.String EMPSN;

	private java.lang.String ID_POSS;

	private java.lang.String ID_POSS_LEVEL;

	public N_N_EMP_POSSPk() {
		super();
	}

	public N_N_EMP_POSSPk(java.lang.String EMPSN, java.lang.String ID_POSS,
			java.lang.String ID_POSS_LEVEL) {
		super();
		this.EMPSN = EMPSN;
		this.ID_POSS = ID_POSS;
		this.ID_POSS_LEVEL = ID_POSS_LEVEL;
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
	 * @returnID_POSS
	 */
	@NotBlank
	@Column(name = "ID_POSS", length = 10)
	public java.lang.String getID_POSS() {
		return ID_POSS;
	}

	/**
	 * @param ID_POSS
	 */
	public void setID_POSS(java.lang.String ID_POSS) {
		this.ID_POSS = ID_POSS;
	}

	/**
	 * @returnID_POSS_LEVEL
	 */
	@NotBlank
	@Column(name = "ID_POSS_LEVEL", length = 10)
	public java.lang.String getID_POSS_LEVEL() {
		return ID_POSS_LEVEL;
	}

	/**
	 * @param ID_POSS_LEVEL
	 */
	public void setID_POSS_LEVEL(java.lang.String ID_POSS_LEVEL) {
		this.ID_POSS_LEVEL = ID_POSS_LEVEL;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof N_N_EMP_POSSPk))
			return false;
		N_N_EMP_POSSPk castOther = (N_N_EMP_POSSPk) other;
		return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(
				ID_POSS, castOther.ID_POSS).append(ID_POSS_LEVEL,
				castOther.ID_POSS_LEVEL).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(EMPSN).append(ID_POSS).append(
				ID_POSS_LEVEL).toHashCode();
	}
}
