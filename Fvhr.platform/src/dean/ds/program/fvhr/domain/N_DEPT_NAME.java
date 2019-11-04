package ds.program.fvhr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * TEN GOI CAC DON VI
 */
@Entity
@Table(name = "N_DEPT_NAME")
public class N_DEPT_NAME {
	private java.lang.Long ID_DEPT_NAME; // MA

	private java.lang.String DEPT_NAME; // TEN DON VI

	/**
	 * 取得MA
	 * 
	 * @return ID_DEPT_NAME MA
	 */
	@Column(name = "ID_DEPT_NAME")
	@Config(key = "N_DEPT_NAME.ID_DEPT_NAME")
	public java.lang.Long getID_DEPT_NAME() {
		return ID_DEPT_NAME;
	}

	/**
	 * 設定MA
	 * 
	 * @param ID_DEPT_NAME
	 *            MA
	 */
	public void setID_DEPT_NAME(java.lang.Long ID_DEPT_NAME) {
		this.ID_DEPT_NAME = ID_DEPT_NAME;
	}

	/**
	 * 取得TEN DON VI
	 * 
	 * @return DEPT_NAME TEN DON VI
	 */
	@Id
	@NotBlank
	@Column(name = "DEPT_NAME")
	@Config(key = "N_DEPT_NAME.DEPT_NAME")
	public java.lang.String getDEPT_NAME() {
		return DEPT_NAME;
	}

	/**
	 * 設定TEN DON VI
	 * 
	 * @param DEPT_NAME
	 *            TEN DON VI
	 */
	public void setDEPT_NAME(java.lang.String DEPT_NAME) {
		this.DEPT_NAME = DEPT_NAME;
	}
}
