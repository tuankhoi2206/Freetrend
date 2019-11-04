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
public class ATTQUITPk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 653169441592004490L;

	private java.lang.String EMPSN;

	private java.util.Date DOT_TV;

	public ATTQUITPk() {
		super();
	}

	public ATTQUITPk(java.lang.String EMPSN, java.util.Date DOT_TV) {
		super();
		this.EMPSN = EMPSN;
		this.DOT_TV = DOT_TV;
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
	 * @returnDOT_TV
	 */
	@NotBlank
	@Temporal(TemporalType.DATE)
	@Column(name = "DOT_TV")
	public java.util.Date getDOT_TV() {
		return DOT_TV;
	}

	/**
	 * @param DOT_TV
	 */
	public void setDOT_TV(java.util.Date DOT_TV) {
		this.DOT_TV = DOT_TV;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof ATTQUITPk))
			return false;
		ATTQUITPk castOther = (ATTQUITPk) other;
		return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(
				DOT_TV, castOther.DOT_TV).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(EMPSN).append(DOT_TV).toHashCode();
	}
}
