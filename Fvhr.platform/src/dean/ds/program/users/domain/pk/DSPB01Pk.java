package ds.program.users.domain.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import dsc.util.hibernate.validator.NotBlank;

@Embeddable
public class DSPB01Pk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private java.lang.String PB_USERID; // 使用者代號

	private java.lang.String PB_ID; // 系統代號

	public DSPB01Pk() {
		super();
	}

	public DSPB01Pk(java.lang.String PB_USERID, java.lang.String PB_ID) {
		super();
		this.PB_USERID = PB_USERID;
		this.PB_ID = PB_ID;
	}

	/**
	 * 取得使用者代號
	 * 
	 * @returnPB_USERID 使用者代號
	 */
	@NotBlank
	@Column(name = "PB_USERID", length = 10)
	public java.lang.String getPB_USERID() {
		return PB_USERID;
	}

	/**
	 * 設定使用者代號
	 * 
	 * @param PB_USERID
	 *            使用者代號
	 */
	public void setPB_USERID(java.lang.String PB_USERID) {
		this.PB_USERID = PB_USERID;
	}

	/**
	 * 取得系統代號
	 * 
	 * @returnPB_ID 系統代號
	 */
	@NotBlank
	@Column(name = "PB_ID", length = 4)
	public java.lang.String getPB_ID() {
		return PB_ID;
	}

	/**
	 * 設定系統代號
	 * 
	 * @param PB_ID
	 *            系統代號
	 */
	public void setPB_ID(java.lang.String PB_ID) {
		this.PB_ID = PB_ID;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof DSPB01Pk))
			return false;
		DSPB01Pk castOther = (DSPB01Pk) other;
		return new EqualsBuilder().append(PB_USERID, castOther.PB_USERID)
				.append(PB_ID, castOther.PB_ID).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(PB_USERID).append(PB_ID)
				.toHashCode();
	}
}
