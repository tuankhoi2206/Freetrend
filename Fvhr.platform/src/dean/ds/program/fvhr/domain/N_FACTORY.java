package ds.program.fvhr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Length;

import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * QL CAC XUONG
 */
@Entity
@Table(name = "N_FACTORY")
public class N_FACTORY {
	private java.lang.Long ID_FACT; // MA XUONG

	private java.lang.String NAME_FACT; // TEN XUONG

	private java.lang.String NOTE; // GHI CHU

	private java.lang.String NFACT_NIKE; // NAME FACT THEO NIKE

	/**
	 * 取得MA XUONG
	 * 
	 * @return ID_FACT MA XUONG
	 */
	@NotBlank
	@Column(name = "ID_FACT")
	@Config(key = "N_FACTORY.ID_FACT")
	public java.lang.Long getID_FACT() {
		return ID_FACT;
	}

	/**
	 * 設定MA XUONG
	 * 
	 * @param ID_FACT
	 *            MA XUONG
	 */
	public void setID_FACT(java.lang.Long ID_FACT) {
		this.ID_FACT = ID_FACT;
	}

	/**
	 * 取得TEN XUONG
	 * 
	 * @return NAME_FACT TEN XUONG
	 */
	@Id
	@NotBlank
	@Column(name = "NAME_FACT")
	@Config(key = "N_FACTORY.NAME_FACT")
	public java.lang.String getNAME_FACT() {
		return NAME_FACT;
	}

	/**
	 * 設定TEN XUONG
	 * 
	 * @param NAME_FACT
	 *            TEN XUONG
	 */
	public void setNAME_FACT(java.lang.String NAME_FACT) {
		this.NAME_FACT = NAME_FACT;
	}

	/**
	 * 取得GHI CHU
	 * 
	 * @return NOTE GHI CHU
	 */
	@Length(max = 30)
	@Column(name = "NOTE")
	@Config(key = "N_FACTORY.NOTE")
	public java.lang.String getNOTE() {
		return NOTE;
	}

	/**
	 * 設定GHI CHU
	 * 
	 * @param NOTE
	 *            GHI CHU
	 */
	public void setNOTE(java.lang.String NOTE) {
		this.NOTE = NOTE;
	}

	/**
	 * 取得NAME FACT THEO NIKE
	 * 
	 * @return NFACT_NIKE NAME FACT THEO NIKE
	 */
	@Length(max = 10)
	@Column(name = "NFACT_NIKE")
	@Config(key = "N_FACTORY.NFACT_NIKE")
	public java.lang.String getNFACT_NIKE() {
		return NFACT_NIKE;
	}

	/**
	 * 設定NAME FACT THEO NIKE
	 * 
	 * @param NFACT_NIKE
	 *            NAME FACT THEO NIKE
	 */
	public void setNFACT_NIKE(java.lang.String NFACT_NIKE) {
		this.NFACT_NIKE = NFACT_NIKE;
	}
}
