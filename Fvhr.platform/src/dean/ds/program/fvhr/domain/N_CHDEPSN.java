package ds.program.fvhr.domain;

import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_CHDEPSNPk;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;

/**
 * DIEU DONG DON VI
 **/
@IdClass(N_CHDEPSNPk.class)
@Entity
@Table(name = "N_CHDEPSN")
public class N_CHDEPSN {
	private java.lang.String EMPSN; // SO THE
	private java.util.Date DATES; // NGAY DIEU DONG
	private java.lang.String DEPSN; // DON VI MOI
	private java.lang.String DEPSN_OLD; // DON VI CU
	private java.lang.String MAQL; // MA QUAN LY MOI
	private java.lang.String MAQL_OLD; // MA QUAN LY CU
	private java.lang.String STATUS; // TRANG THAI
	private java.lang.String NOTE; // ghi chu _ them 12/09/2011, khi nhap ho so
									// la insert row
	private java.lang.String TRUCTIEP_SX_OLD; // Y: TRUC TIEP SX, N: GIAN TIEP
												// SX
	private java.lang.String TRUCTIEP_SX_NEW; // Y: TRUC TIEP SX, N: GIAN TIEP
												// SX

	/**
	 * 取得SO THE
	 * 
	 * @return EMPSN SO THE
	 */
	@Id
	@NotBlank
	@Column(name = "EMPSN")
	@Config(key = "N_CHDEPSN.EMPSN")
	public java.lang.String getEMPSN() {
		return EMPSN;
	}

	/**
	 * 設定SO THE
	 * 
	 * @param EMPSN
	 *            SO THE
	 */
	public void setEMPSN(java.lang.String EMPSN) {
		this.EMPSN = EMPSN;
	}

	/**
	 * 取得NGAY DIEU DONG
	 * 
	 * @return DATES NGAY DIEU DONG
	 */
	@Id
	@NotBlank
	@Temporal(TemporalType.DATE)
	@Column(name = "DATES")
	@Config(key = "N_CHDEPSN.DATES")
	public java.util.Date getDATES() {
		return DATES;
	}

	/**
	 * 設定NGAY DIEU DONG
	 * 
	 * @param DATES
	 *            NGAY DIEU DONG
	 */
	public void setDATES(java.util.Date DATES) {
		this.DATES = DATES;
	}

	/**
	 * 取得DON VI MOI
	 * 
	 * @return DEPSN DON VI MOI
	 */
	@Length(max = 100)
	@Column(name = "DEPSN")
	@Config(key = "N_CHDEPSN.DEPSN")
	public java.lang.String getDEPSN() {
		return DEPSN;
	}

	/**
	 * 設定DON VI MOI
	 * 
	 * @param DEPSN
	 *            DON VI MOI
	 */
	public void setDEPSN(java.lang.String DEPSN) {
		this.DEPSN = DEPSN;
	}

	/**
	 * 取得DON VI CU
	 * 
	 * @return DEPSN_OLD DON VI CU
	 */
	@Length(max = 50)
	@Column(name = "DEPSN_OLD")
	@Config(key = "N_CHDEPSN.DEPSN_OLD")
	public java.lang.String getDEPSN_OLD() {
		return DEPSN_OLD;
	}

	/**
	 * 設定DON VI CU
	 * 
	 * @param DEPSN_OLD
	 *            DON VI CU
	 */
	public void setDEPSN_OLD(java.lang.String DEPSN_OLD) {
		this.DEPSN_OLD = DEPSN_OLD;
	}

	/**
	 * 取得MA QUAN LY MOI
	 * 
	 * @return MAQL MA QUAN LY MOI
	 */
	@Length(max = 10)
	@Column(name = "MAQL")
	@Config(key = "N_CHDEPSN.MAQL")
	public java.lang.String getMAQL() {
		return MAQL;
	}

	/**
	 * 設定MA QUAN LY MOI
	 * 
	 * @param MAQL
	 *            MA QUAN LY MOI
	 */
	public void setMAQL(java.lang.String MAQL) {
		this.MAQL = MAQL;
	}

	/**
	 * 取得MA QUAN LY CU
	 * 
	 * @return MAQL_OLD MA QUAN LY CU
	 */
	@Length(max = 10)
	@Column(name = "MAQL_OLD")
	@Config(key = "N_CHDEPSN.MAQL_OLD")
	public java.lang.String getMAQL_OLD() {
		return MAQL_OLD;
	}

	/**
	 * 設定MA QUAN LY CU
	 * 
	 * @param MAQL_OLD
	 *            MA QUAN LY CU
	 */
	public void setMAQL_OLD(java.lang.String MAQL_OLD) {
		this.MAQL_OLD = MAQL_OLD;
	}

	/**
	 * 取得TRANG THAI
	 * 
	 * @return STATUS TRANG THAI
	 */
	@Length(max = 1)
	@Column(name = "STATUS")
	@Config(key = "N_CHDEPSN.STATUS")
	public java.lang.String getSTATUS() {
		return STATUS;
	}

	/**
	 * 設定TRANG THAI
	 * 
	 * @param STATUS
	 *            TRANG THAI
	 */
	public void setSTATUS(java.lang.String STATUS) {
		this.STATUS = STATUS;
	}

	/**
	 * 取得ghi chu _ them 12/09/2011, khi nhap ho so la insert row
	 * 
	 * @return NOTE ghi chu _ them 12/09/2011, khi nhap ho so la insert row
	 */
	@Length(max = 150)
	@Column(name = "NOTE")
	@Config(key = "N_CHDEPSN.NOTE")
	public java.lang.String getNOTE() {
		return NOTE;
	}

	/**
	 * 設定ghi chu _ them 12/09/2011, khi nhap ho so la insert row
	 * 
	 * @param NOTE
	 *            ghi chu _ them 12/09/2011, khi nhap ho so la insert row
	 */
	public void setNOTE(java.lang.String NOTE) {
		this.NOTE = NOTE;
	}

	/**
	 * TRUC TIEP SX OLD VA NEW LUU LAI TRANG THAI SX
	 * 
	 * @return
	 */
	@Length(max = 1)
	@Column(name = "TRUCTIEP_SX_OLD")
	@Config(key = "N_CHDEPSN.TRUCTIEP_SX_OLD")
	public java.lang.String getTRUCTIEP_SX_OLD() {
		return TRUCTIEP_SX_OLD;
	}

	public void setTRUCTIEP_SX_OLD(java.lang.String TRUCTIEP_SX_OLD) {
		this.TRUCTIEP_SX_OLD = TRUCTIEP_SX_OLD;
	}

	@Length(max = 1)
	@Column(name = "TRUCTIEP_SX_NEW")
	@Config(key = "N_CHDEPSN.TRUCTIEP_SX_NEW")
	public java.lang.String getTRUCTIEP_SX_NEW() {
		return TRUCTIEP_SX_NEW;
	}

	public void setTRUCTIEP_SX_NEW(java.lang.String TRUCTIEP_SX_NEW) {
		this.TRUCTIEP_SX_NEW = TRUCTIEP_SX_NEW;
	}

	// @OneToOne(fetch = FetchType.EAGER)
	// @JoinColumn(name = "EMPSN")
	private N_DEPARTMENT DEPARTMENT;

	@OneToOne
	@JoinColumn(name = "DEPSN", referencedColumnName = "ID_DEPT", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	@Config(key = "N_DEPARTMENT.ID_DEPT")
	public N_DEPARTMENT getN_DEPARTMENT() {
		return DEPARTMENT;
	}

	public void setN_DEPARTMENT(N_DEPARTMENT department) {
		this.DEPARTMENT = department;
	}

}
