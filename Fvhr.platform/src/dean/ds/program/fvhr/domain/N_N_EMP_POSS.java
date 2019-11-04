package ds.program.fvhr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;

import ds.program.fvhr.domain.pk.N_N_EMP_POSSPk;
import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * 
 */
@IdClass(N_N_EMP_POSSPk.class)
@Entity
@Table(name = "N_N_EMP_POSS")
public class N_N_EMP_POSS {
	private java.lang.String EMPSN;

	private java.lang.String ID_POSS;

	private java.util.Date TIME_B;

	private java.util.Date TIME_E;

	private java.lang.String NOTE;

	private java.lang.String ID_POSS_LEVEL;

	/**
	 * @return EMPSN
	 */
	@Id
	@NotBlank
	@Column(name = "EMPSN")
	@Config(key = "N_N_EMP_POSS.EMPSN")
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
	 * @return ID_POSS
	 */
	@Id
	@NotBlank
	@Column(name = "ID_POSS")
	@Config(key = "N_N_EMP_POSS.ID_POSS")
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
	 * @return TIME_B
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "TIME_B")
	@Config(key = "N_N_EMP_POSS.TIME_B")
	public java.util.Date getTIME_B() {
		return TIME_B;
	}

	/**
	 * @param TIME_B
	 */
	public void setTIME_B(java.util.Date TIME_B) {
		this.TIME_B = TIME_B;
	}

	/**
	 * @return TIME_E
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "TIME_E")
	@Config(key = "N_N_EMP_POSS.TIME_E")
	public java.util.Date getTIME_E() {
		return TIME_E;
	}

	/**
	 * @param TIME_E
	 */
	public void setTIME_E(java.util.Date TIME_E) {
		this.TIME_E = TIME_E;
	}

	/**
	 * @return NOTE
	 */
	@Length(max = 200)
	@Column(name = "NOTE")
	@Config(key = "N_N_EMP_POSS.NOTE")
	public java.lang.String getNOTE() {
		return NOTE;
	}

	/**
	 * @param NOTE
	 */
	public void setNOTE(java.lang.String NOTE) {
		this.NOTE = NOTE;
	}

	/**
	 * @return ID_POSS_LEVEL
	 */
	@Id
	@NotBlank
	@Column(name = "ID_POSS_LEVEL")
	@Config(key = "N_N_EMP_POSS.ID_POSS_LEVEL")
	public java.lang.String getID_POSS_LEVEL() {
		return ID_POSS_LEVEL;
	}

	/**
	 * @param ID_POSS_LEVEL
	 */
	public void setID_POSS_LEVEL(java.lang.String ID_POSS_LEVEL) {
		this.ID_POSS_LEVEL = ID_POSS_LEVEL;
	}
}
