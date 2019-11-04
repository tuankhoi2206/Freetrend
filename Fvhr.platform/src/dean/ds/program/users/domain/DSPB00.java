package ds.program.users.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;

import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * 系統主檔
 */
@Entity
@Table(name = "DSPB00")
public class DSPB00 {
	private java.lang.String PB_ID; // 系統模塊代號

	private java.lang.String PB_NA; // 系統中文名稱

	private java.lang.String PB_PRG; // 程式名稱

	private java.lang.String UP_USER; // 異動人員

	private java.util.Date UP_DATE; // 異動日期

	private java.lang.String MENUID; // 選單代號

	private java.lang.String PB_SYSID; // 系統代號

	private java.lang.String PB_SUBID; // 選單群組號

	private java.lang.String ISOK; // 是否選擇

	/**
	 * 取得系統模塊代號
	 * 
	 * @return PB_ID 系統模塊代號
	 */
	@Id
	@NotBlank
	@Column(name = "PB_ID")
	@Config(key = "DSPB00.PB_ID")
	public java.lang.String getPB_ID() {
		return PB_ID;
	}

	/**
	 * 設定系統模塊代號
	 * 
	 * @param PB_ID
	 *            系統模塊代號
	 */
	public void setPB_ID(java.lang.String PB_ID) {
		this.PB_ID = PB_ID;
	}

	/**
	 * 取得系統中文名稱
	 * 
	 * @return PB_NA 系統中文名稱
	 */
	@Length(max = 40)
	@Column(name = "PB_NA")
	@Config(key = "DSPB00.PB_NA")
	public java.lang.String getPB_NA() {
		return PB_NA;
	}

	/**
	 * 設定系統中文名稱
	 * 
	 * @param PB_NA
	 *            系統中文名稱
	 */
	public void setPB_NA(java.lang.String PB_NA) {
		this.PB_NA = PB_NA;
	}

	/**
	 * 取得程式名稱
	 * 
	 * @return PB_PRG 程式名稱
	 */
	@Length(max = 15)
	@Column(name = "PB_PRG")
	@Config(key = "DSPB00.PB_PRG")
	public java.lang.String getPB_PRG() {
		return PB_PRG;
	}

	/**
	 * 設定程式名稱
	 * 
	 * @param PB_PRG
	 *            程式名稱
	 */
	public void setPB_PRG(java.lang.String PB_PRG) {
		this.PB_PRG = PB_PRG;
	}

	/**
	 * 取得異動人員
	 * 
	 * @return UP_USER 異動人員
	 */
	@Length(max = 10)
	@Column(name = "UP_USER")
	@Config(key = "DSPB00.UP_USER")
	public java.lang.String getUP_USER() {
		return UP_USER;
	}

	/**
	 * 設定異動人員
	 * 
	 * @param UP_USER
	 *            異動人員
	 */
	public void setUP_USER(java.lang.String UP_USER) {
		this.UP_USER = UP_USER;
	}

	/**
	 * 取得異動日期
	 * 
	 * @return UP_DATE 異動日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UP_DATE")
	@Config(key = "DSPB00.UP_DATE")
	public java.util.Date getUP_DATE() {
		return UP_DATE;
	}

	/**
	 * 設定異動日期
	 * 
	 * @param UP_DATE
	 *            異動日期
	 */
	public void setUP_DATE(java.util.Date UP_DATE) {
		this.UP_DATE = UP_DATE;
	}

	/**
	 * 取得選單代號
	 * 
	 * @return MENUID 選單代號
	 */
	@Length(max = 10)
	@Column(name = "MENUID")
	@Config(key = "DSPB00.MENUID")
	public java.lang.String getMENUID() {
		return MENUID;
	}

	/**
	 * 設定選單代號
	 * 
	 * @param MENUID
	 *            選單代號
	 */
	public void setMENUID(java.lang.String MENUID) {
		this.MENUID = MENUID;
	}

	/**
	 * 取得系統代號
	 * 
	 * @return PB_SYSID 系統代號
	 */
	@Length(max = 6)
	@Column(name = "PB_SYSID")
	@Config(key = "DSPB00.PB_SYSID")
	public java.lang.String getPB_SYSID() {
		return PB_SYSID;
	}

	/**
	 * 設定系統代號
	 * 
	 * @param PB_SYSID
	 *            系統代號
	 */
	public void setPB_SYSID(java.lang.String PB_SYSID) {
		this.PB_SYSID = PB_SYSID;
	}

	/**
	 * 取得選單群組號
	 * 
	 * @return PB_SUBID 選單群組號
	 */
	@Length(max = 6)
	@Column(name = "PB_SUBID")
	@Config(key = "DSPB00.PB_SUBID")
	public java.lang.String getPB_SUBID() {
		return PB_SUBID;
	}

	/**
	 * 設定選單群組號
	 * 
	 * @param PB_SUBID
	 *            選單群組號
	 */
	public void setPB_SUBID(java.lang.String PB_SUBID) {
		this.PB_SUBID = PB_SUBID;
	}

	/**
	 * 取得是否選擇
	 * 
	 * @return ISOK 是否選擇
	 */
	@Length(max = 1)
	@Column(name = "ISOK")
	@Config(key = "DSPB00.ISOK")
	public java.lang.String getISOK() {
		return ISOK;
	}

	/**
	 * 設定是否選擇
	 * 
	 * @param ISOK
	 *            是否選擇
	 */
	public void setISOK(java.lang.String ISOK) {
		this.ISOK = ISOK;
	}
}
