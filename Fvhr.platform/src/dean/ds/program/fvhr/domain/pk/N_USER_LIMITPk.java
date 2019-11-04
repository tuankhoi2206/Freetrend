package ds.program.fvhr.domain.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import dsc.util.hibernate.validator.NotBlank;

@Embeddable
public class N_USER_LIMITPk implements Serializable {
	private java.lang.String MA_USER;

	private java.lang.String MA_QL;

	public N_USER_LIMITPk() {
		super();
	}

	public N_USER_LIMITPk(java.lang.String MA_USER, java.lang.String MA_QL) {
		super();
		this.MA_USER = MA_USER;
		this.MA_QL = MA_QL;
	}

	/**
	 * @returnMA_USER
	 */
	@NotBlank
	@Column(name = "MA_USER", length = 3)
	public java.lang.String getMA_USER() {
		return MA_USER;
	}

	/**
	 * @param MA_USER
	 */
	public void setMA_USER(java.lang.String MA_USER) {
		this.MA_USER = MA_USER;
	}

	/**
	 * @returnMA_QL
	 */
	@NotBlank
	@Column(name = "MA_QL", length = 3)
	public java.lang.String getMA_QL() {
		return MA_QL;
	}

	/**
	 * @param MA_QL
	 */
	public void setMA_QL(java.lang.String MA_QL) {
		this.MA_QL = MA_QL;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof N_USER_LIMITPk))
			return false;
		N_USER_LIMITPk castOther = (N_USER_LIMITPk) other;
		return new EqualsBuilder().append(MA_USER, castOther.MA_USER).append(
				MA_QL, castOther.MA_QL).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(MA_USER).append(MA_QL).toHashCode();
	}
}
