package ds.program.fvhr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * 
 */
@Entity
@Table(name = "N_EMP_REGISTER")
public class N_EMP_REGISTER {
	private java.lang.String EMPSN;

	private java.lang.Long SALARY_ADVANCE; // trang thai co tam ung luong hay
											// khong

	private java.lang.Long TRADE_UNION; // trang thai co tham gio cong doan hay
										// khong

	/**
	 * @return EMPSN
	 */
	@Id
	@NotBlank
	@Column(name = "EMPSN")
	@Config(key = "N_EMP_REGISTER.EMPSN")
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
	 * 取得trang thai co tam ung luong hay khong
	 * 
	 * @return SALARY_ADVANCE trang thai co tam ung luong hay khong
	 */
	@Column(name = "SALARY_ADVANCE")
	@Config(key = "N_EMP_REGISTER.SALARY_ADVANCE")
	public java.lang.Long getSALARY_ADVANCE() {
		return SALARY_ADVANCE;
	}

	/**
	 * 設定trang thai co tam ung luong hay khong
	 * 
	 * @param SALARY_ADVANCE
	 *            trang thai co tam ung luong hay khong
	 */
	public void setSALARY_ADVANCE(java.lang.Long SALARY_ADVANCE) {
		this.SALARY_ADVANCE = SALARY_ADVANCE;
	}

	/**
	 * 取得trang thai co tham gio cong doan hay khong
	 * 
	 * @return TRADE_UNION trang thai co tham gio cong doan hay khong
	 */
	@Column(name = "TRADE_UNION")
	@Config(key = "N_EMP_REGISTER.TRADE_UNION")
	public java.lang.Long getTRADE_UNION() {
		return TRADE_UNION;
	}

	/**
	 * 設定trang thai co tham gio cong doan hay khong
	 * 
	 * @param TRADE_UNION
	 *            trang thai co tham gio cong doan hay khong
	 */
	public void setTRADE_UNION(java.lang.Long TRADE_UNION) {
		this.TRADE_UNION = TRADE_UNION;
	}
}
