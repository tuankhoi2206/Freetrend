package ds.program.fvhr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;

import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * TAM UNG
 */
@Entity
@Table(name = "N_N_EMP_BORM")
public class N_N_EMP_BORM {
	private java.lang.String EMPSN;

	private java.lang.String STATUS;

	private java.lang.String UP_USER;

	private java.util.Date UP_DATE;

	/**
	 * @return EMPSN
	 */
	@Id
	@NotBlank
	@Column(name = "EMPSN")
	@Config(key = "N_N_EMP_BORM.EMPSN")
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
	 * @return STATUS
	 */
	@Length(max = 1)
	@Column(name = "STATUS")
	@Config(key = "N_N_EMP_BORM.STATUS")
	public java.lang.String getSTATUS() {
		return STATUS;
	}

	/**
	 * @param STATUS
	 */
	public void setSTATUS(java.lang.String STATUS) {
		this.STATUS = STATUS;
	}

	/**
	 * @return UP_USER
	 */
	@Length(max = 10)
	@Column(name = "UP_USER")
	@Config(key = "N_N_EMP_BORM.UP_USER")
	public java.lang.String getUP_USER() {
		return UP_USER;
	}

	/**
	 * @param UP_USER
	 */
	public void setUP_USER(java.lang.String UP_USER) {
		this.UP_USER = UP_USER;
	}

	/**
	 * @return UP_DATE
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UP_DATE")
	@Config(key = "N_N_EMP_BORM.UP_DATE")
	public java.util.Date getUP_DATE() {
		return UP_DATE;
	}

	/**
	 * @param UP_DATE
	 */
	public void setUP_DATE(java.util.Date UP_DATE) {
		this.UP_DATE = UP_DATE;
	}
}
