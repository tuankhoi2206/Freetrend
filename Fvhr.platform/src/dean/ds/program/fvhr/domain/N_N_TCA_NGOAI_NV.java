package ds.program.fvhr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.validator.Length;

import ds.program.fvhr.domain.pk.N_N_TCA_NGOAI_NVPk;
import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * TCA NGOAI TINH CHO NGHI VIEC
 */
@IdClass(N_N_TCA_NGOAI_NVPk.class)
@Entity
@Table(name = "N_N_TCA_NGOAI_NV")
public class N_N_TCA_NGOAI_NV {
	private java.lang.String EMPSN;

	private java.lang.String THANG;

	private java.lang.String NAM;

	private java.lang.Long TCA_NGAY;

	private java.lang.Long TCA_DEM;

	private java.lang.Long TCA_CN;

	private java.lang.Long TCA_LE;

	private java.lang.Long SODEM_TIENCOM;

	private java.lang.String NOTE;

	/**
	 * @return EMPSN
	 */
	@Id
	@NotBlank
	@Column(name = "EMPSN")
	@Config(key = "N_N_TCA_NGOAI_NV.EMPSN")
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
	 * @return THANG
	 */
	@Id
	@NotBlank
	@Column(name = "THANG")
	@Config(key = "N_N_TCA_NGOAI_NV.THANG")
	public java.lang.String getTHANG() {
		return THANG;
	}

	/**
	 * @param THANG
	 */
	public void setTHANG(java.lang.String THANG) {
		this.THANG = THANG;
	}

	/**
	 * @return NAM
	 */
	@Id
	@NotBlank
	@Column(name = "NAM")
	@Config(key = "N_N_TCA_NGOAI_NV.NAM")
	public java.lang.String getNAM() {
		return NAM;
	}

	/**
	 * @param NAM
	 */
	public void setNAM(java.lang.String NAM) {
		this.NAM = NAM;
	}

	/**
	 * @return TCA_NGAY
	 */
	@Column(name = "TCA_NGAY")
	@Config(key = "N_N_TCA_NGOAI_NV.TCA_NGAY")
	public java.lang.Long getTCA_NGAY() {
		return TCA_NGAY;
	}

	/**
	 * @param TCA_NGAY
	 */
	public void setTCA_NGAY(java.lang.Long TCA_NGAY) {
		this.TCA_NGAY = TCA_NGAY;
	}

	/**
	 * @return TCA_DEM
	 */
	@Column(name = "TCA_DEM")
	@Config(key = "N_N_TCA_NGOAI_NV.TCA_DEM")
	public java.lang.Long getTCA_DEM() {
		return TCA_DEM;
	}

	/**
	 * @param TCA_DEM
	 */
	public void setTCA_DEM(java.lang.Long TCA_DEM) {
		this.TCA_DEM = TCA_DEM;
	}

	/**
	 * @return TCA_CN
	 */
	@Column(name = "TCA_CN")
	@Config(key = "N_N_TCA_NGOAI_NV.TCA_CN")
	public java.lang.Long getTCA_CN() {
		return TCA_CN;
	}

	/**
	 * @param TCA_CN
	 */
	public void setTCA_CN(java.lang.Long TCA_CN) {
		this.TCA_CN = TCA_CN;
	}

	/**
	 * @return TCA_LE
	 */
	@Column(name = "TCA_LE")
	@Config(key = "N_N_TCA_NGOAI_NV.TCA_LE")
	public java.lang.Long getTCA_LE() {
		return TCA_LE;
	}

	/**
	 * @param TCA_LE
	 */
	public void setTCA_LE(java.lang.Long TCA_LE) {
		this.TCA_LE = TCA_LE;
	}

	/**
	 * @return SODEM_TIENCOM
	 */
	@Column(name = "SODEM_TIENCOM")
	@Config(key = "N_N_TCA_NGOAI_NV.SODEM_TIENCOM")
	public java.lang.Long getSODEM_TIENCOM() {
		return SODEM_TIENCOM;
	}

	/**
	 * @param SODEM_TIENCOM
	 */
	public void setSODEM_TIENCOM(java.lang.Long SODEM_TIENCOM) {
		this.SODEM_TIENCOM = SODEM_TIENCOM;
	}

	/**
	 * @return NOTE
	 */
	@Length(max = 50)
	@Column(name = "NOTE")
	@Config(key = "N_N_TCA_NGOAI_NV.NOTE")
	public java.lang.String getNOTE() {
		return NOTE;
	}

	/**
	 * @param NOTE
	 */
	public void setNOTE(java.lang.String NOTE) {
		this.NOTE = NOTE;
	}
}
