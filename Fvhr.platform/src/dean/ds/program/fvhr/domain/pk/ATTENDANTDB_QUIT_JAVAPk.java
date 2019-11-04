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
public class ATTENDANTDB_QUIT_JAVAPk implements Serializable {
	private static final long serialVersionUID = -8329153814875168659L;

	private java.lang.String EMPSN; // SO THE

	private java.util.Date DOT_TV;

	public ATTENDANTDB_QUIT_JAVAPk() {
		super();
	}

	public ATTENDANTDB_QUIT_JAVAPk(java.lang.String EMPSN, java.util.Date DOT_TV) {
		super();
		this.EMPSN = EMPSN;
		this.DOT_TV = DOT_TV;
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
		if (!(other instanceof ATTENDANTDB_QUIT_JAVAPk))
			return false;
		ATTENDANTDB_QUIT_JAVAPk castOther = (ATTENDANTDB_QUIT_JAVAPk) other;
		return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(
				DOT_TV, castOther.DOT_TV).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(EMPSN).append(DOT_TV).toHashCode();
	}
}
