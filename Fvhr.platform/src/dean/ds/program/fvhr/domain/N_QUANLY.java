package ds.program.fvhr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Length;

import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * PHAN VUNG QL DU LIEU
 */
@Entity
@Table(name = "N_QUANLY")
public class N_QUANLY {
	private java.lang.String MAQL; // MA SO

	private java.lang.String MOTA; // TEN VUNG QL

	private java.lang.String NAME_FACT; // TEN XUONG

	/**
	 * 取得MA SO
	 * 
	 * @return MAQL MA SO
	 */
	@Id
	@NotBlank
	@Column(name = "MAQL")
	@Config(key = "N_QUANLY.MAQL")
	public java.lang.String getMAQL() {
		return MAQL;
	}

	/**
	 * 設定MA SO
	 * 
	 * @param MAQL
	 *            MA SO
	 */
	public void setMAQL(java.lang.String MAQL) {
		this.MAQL = MAQL;
	}

	/**
	 * 取得TEN VUNG QL
	 * 
	 * @return MOTA TEN VUNG QL
	 */
	@NotBlank
	@Length(max = 100)
	@Column(name = "MOTA")
	@Config(key = "N_QUANLY.MOTA")
	public java.lang.String getMOTA() {
		return MOTA;
	}

	/**
	 * 設定TEN VUNG QL
	 * 
	 * @param MOTA
	 *            TEN VUNG QL
	 */
	public void setMOTA(java.lang.String MOTA) {
		this.MOTA = MOTA;
	}

	/**
	 * 取得TEN XUONG
	 * 
	 * @return NAME_FACT TEN XUONG
	 */
	@Length(max = 30)
	@Column(name = "NAME_FACT")
	@Config(key = "N_QUANLY.NAME_FACT")
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
}
