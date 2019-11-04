package ds.program.fvhr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Length;

import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * NHOM DON VI
 */
@Entity
@Table(name = "N_GROUP_DEPT")
public class N_GROUP_DEPT {
	private java.lang.String NAME_GROUP; // TEN NHOM

	private java.lang.String NOTE; // GHI CHU

	private java.lang.Long ID_GROUP;

	/**
	 * 取得TEN NHOM
	 * 
	 * @return NAME_GROUP TEN NHOM
	 */
	@Id
	@NotBlank
	@Column(name = "NAME_GROUP")
	@Config(key = "N_GROUP_DEPT.NAME_GROUP")
	public java.lang.String getNAME_GROUP() {
		return NAME_GROUP;
	}

	/**
	 * 設定TEN NHOM
	 * 
	 * @param NAME_GROUP
	 *            TEN NHOM
	 */
	public void setNAME_GROUP(java.lang.String NAME_GROUP) {
		this.NAME_GROUP = NAME_GROUP;
	}

	/**
	 * 取得GHI CHU
	 * 
	 * @return NOTE GHI CHU
	 */
	@Length(max = 30)
	@Column(name = "NOTE")
	@Config(key = "N_GROUP_DEPT.NOTE")
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
	 * @return ID_GROUP
	 */
	@Column(name = "ID_GROUP")
	@Config(key = "N_GROUP_DEPT.ID_GROUP")
	public java.lang.Long getID_GROUP() {
		return ID_GROUP;
	}

	/**
	 * @param ID_GROUP
	 */
	public void setID_GROUP(java.lang.Long ID_GROUP) {
		this.ID_GROUP = ID_GROUP;
	}
}
