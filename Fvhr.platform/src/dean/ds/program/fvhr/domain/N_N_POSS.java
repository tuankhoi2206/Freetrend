package ds.program.fvhr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;

import ds.program.fvhr.domain.pk.N_N_POSSPk;
import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * 
 */
@IdClass(N_N_POSSPk.class)
@Entity
@Table(name = "N_N_POSS")
public class N_N_POSS {
	private java.lang.String ID_POSS;

	private java.lang.String NAME_POSS;

	private java.lang.String ID_POSS_LEVEL;

	private java.lang.Long MONEY;

	private java.lang.String NOTE;

	private java.util.Date DATE_B;

	private java.util.Date DATE_E;

	/**
	 * @return ID_POSS
	 */
	@Id
	@NotBlank
	@Column(name = "ID_POSS")
	@Config(key = "N_N_POSS.ID_POSS")
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
	 * @return NAME_POSS
	 */
	@Length(max = 50)
	@Column(name = "NAME_POSS")
	@Config(key = "N_N_POSS.NAME_POSS")
	public java.lang.String getNAME_POSS() {
		return NAME_POSS;
	}

	/**
	 * @param NAME_POSS
	 */
	public void setNAME_POSS(java.lang.String NAME_POSS) {
		this.NAME_POSS = NAME_POSS;
	}

	/**
	 * @return ID_POSS_LEVEL
	 */
	@Id
	@NotBlank
	@Column(name = "ID_POSS_LEVEL")
	@Config(key = "N_N_POSS.ID_POSS_LEVEL")
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
	 * @return MONEY
	 */
	@Column(name = "MONEY")
	@Config(key = "N_N_POSS.MONEY")
	public java.lang.Long getMONEY() {
		return MONEY;
	}

	/**
	 * @param MONEY
	 */
	public void setMONEY(java.lang.Long MONEY) {
		this.MONEY = MONEY;
	}

	/**
	 * @return NOTE
	 */
	@Length(max = 100)
	@Column(name = "NOTE")
	@Config(key = "N_N_POSS.NOTE")
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
	 * @return DATE_B
	 */
	@Id
	@NotBlank
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_B")
	@Config(key = "N_N_POSS.DATE_B")
	public java.util.Date getDATE_B() {
		return DATE_B;
	}

	/**
	 * @param DATE_B
	 */
	public void setDATE_B(java.util.Date DATE_B) {
		this.DATE_B = DATE_B;
	}

	/**
	 * @return DATE_E
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_E")
	@Config(key = "N_N_POSS.DATE_E")
	public java.util.Date getDATE_E() {
		return DATE_E;
	}

	/**
	 * @param DATE_E
	 */
	public void setDATE_E(java.util.Date DATE_E) {
		this.DATE_E = DATE_E;
	}
}
