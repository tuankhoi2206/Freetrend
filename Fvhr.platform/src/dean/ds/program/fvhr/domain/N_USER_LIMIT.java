package ds.program.fvhr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.validator.Length;

import ds.program.fvhr.domain.pk.N_USER_LIMITPk;
import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * 
 */
@IdClass(N_USER_LIMITPk.class)
@Entity
@Table(name = "N_USER_LIMIT")
public class N_USER_LIMIT {
	private java.lang.String MA_USER;

	private java.lang.String MA_QL;

	private java.lang.String GHICHU;

	/**
	 * @return MA_USER
	 */
	@Id
	@NotBlank
	@Config(key = "N_USER_LIMIT.MA_USER")
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
	 * @return MA_QL
	 */
	@Id
	@NotBlank
	@Config(key = "N_USER_LIMIT.MA_QL")
	public java.lang.String getMA_QL() {
		return MA_QL;
	}

	/**
	 * @param MA_QL
	 */
	public void setMA_QL(java.lang.String MA_QL) {
		this.MA_QL = MA_QL;
	}

	/**
	 * @return GHICHU
	 */
	@Length(max = 100)
	@Column(name = "GHICHU")
	@Config(key = "N_USER_LIMIT.GHICHU")
	public java.lang.String getGHICHU() {
		return GHICHU;
	}

	/**
	 * @param GHICHU
	 */
	public void setGHICHU(java.lang.String GHICHU) {
		this.GHICHU = GHICHU;
	}
}
