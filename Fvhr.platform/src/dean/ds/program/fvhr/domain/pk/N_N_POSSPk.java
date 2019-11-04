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
public class N_N_POSSPk implements Serializable {
	private java.lang.String ID_POSS;

	private java.lang.String ID_POSS_LEVEL;

	private java.util.Date DATE_B;

	public N_N_POSSPk() {
		super();
	}

	public N_N_POSSPk(java.lang.String ID_POSS, java.lang.String ID_POSS_LEVEL,
			java.util.Date DATE_B) {
		super();
		this.ID_POSS = ID_POSS;
		this.ID_POSS_LEVEL = ID_POSS_LEVEL;
		this.DATE_B = DATE_B;
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
		if (!(other instanceof N_N_POSSPk))
			return false;
		N_N_POSSPk castOther = (N_N_POSSPk) other;
		return new EqualsBuilder().append(ID_POSS, castOther.ID_POSS).append(
				ID_POSS_LEVEL, castOther.ID_POSS_LEVEL).append(DATE_B,
				castOther.DATE_B).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(ID_POSS).append(ID_POSS_LEVEL)
				.append(DATE_B).toHashCode();
	}
}
