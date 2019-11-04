package ds.program.users.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;

import ds.program.users.domain.pk.DSPB01Pk;
import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * 使用者權限檔
 */
@IdClass(DSPB01Pk.class)
@Entity
@Table(name = "DSPB01")
public class DSPB01 {
	private java.lang.String PB_USERID; // 使用者代號

	private java.lang.String PB_ID; // 系統代號

	private java.lang.String PB_PRG; // 程式名稱

	private java.lang.String PB_RH01; // 權限-新增

	private java.lang.String PB_RH02; // 權限-查詢

	private java.lang.String PB_RH03; // 權限-刪除

	private java.lang.String PB_RH04; // 權限-修改

	private java.lang.String PB_RH05; // 權限-確認

	private java.lang.String PB_RH06; // 權限-取消確認

	private java.lang.String PB_RH07; // 權限-成本

	private java.lang.String UP_USER; // 異動人員

	private java.util.Date UP_DATE; // 異動日期

	private java.lang.String PB_RH08; // 權限E-Mail

	/**
	 * 取得使用者代號
	 * 
	 * @return PB_USERID 使用者代號
	 */
	@Id
	@NotBlank
	@Column(name = "PB_USERID")
	@Config(key = "DSPB01.PB_USERID")
	public java.lang.String getPB_USERID() {
		return PB_USERID;
	}

	/**
	 * 設定使用者代號
	 * 
	 * @param PB_USERID
	 *            使用者代號
	 */
	public void setPB_USERID(java.lang.String PB_USERID) {
		this.PB_USERID = PB_USERID;
	}

	/**
	 * 取得系統代號
	 * 
	 * @return PB_ID 系統代號
	 */
	@Id
	@NotBlank
	@Column(name = "PB_ID")
	@Config(key = "DSPB01.PB_ID")
	public java.lang.String getPB_ID() {
		return PB_ID;
	}

	/**
	 * 設定系統代號
	 * 
	 * @param PB_ID
	 *            系統代號
	 */
	public void setPB_ID(java.lang.String PB_ID) {
		this.PB_ID = PB_ID;
	}

	/**
	 * 取得程式名稱
	 * 
	 * @return PB_PRG 程式名稱
	 */
	@Length(max = 15)
	@Column(name = "PB_PRG")
	@Config(key = "DSPB01.PB_PRG")
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
	 * 取得權限-新增
	 * 
	 * @return PB_RH01 權限-新增
	 */
	@Length(max = 1)
	@Column(name = "PB_RH01")
	@Config(key = "DSPB01.PB_RH01")
	public java.lang.String getPB_RH01() {
		return PB_RH01;
	}

	/**
	 * 設定權限-新增
	 * 
	 * @param PB_RH01
	 *            權限-新增
	 */
	public void setPB_RH01(java.lang.String PB_RH01) {
		this.PB_RH01 = PB_RH01;
	}

	/**
	 * 取得權限-查詢
	 * 
	 * @return PB_RH02 權限-查詢
	 */
	@Length(max = 1)
	@Column(name = "PB_RH02")
	@Config(key = "DSPB01.PB_RH02")
	public java.lang.String getPB_RH02() {
		return PB_RH02;
	}

	/**
	 * 設定權限-查詢
	 * 
	 * @param PB_RH02
	 *            權限-查詢
	 */
	public void setPB_RH02(java.lang.String PB_RH02) {
		this.PB_RH02 = PB_RH02;
	}

	/**
	 * 取得權限-刪除
	 * 
	 * @return PB_RH03 權限-刪除
	 */
	@Length(max = 1)
	@Column(name = "PB_RH03")
	@Config(key = "DSPB01.PB_RH03")
	public java.lang.String getPB_RH03() {
		return PB_RH03;
	}

	/**
	 * 設定權限-刪除
	 * 
	 * @param PB_RH03
	 *            權限-刪除
	 */
	public void setPB_RH03(java.lang.String PB_RH03) {
		this.PB_RH03 = PB_RH03;
	}

	/**
	 * 取得權限-修改
	 * 
	 * @return PB_RH04 權限-修改
	 */
	@Length(max = 1)
	@Column(name = "PB_RH04")
	@Config(key = "DSPB01.PB_RH04")
	public java.lang.String getPB_RH04() {
		return PB_RH04;
	}

	/**
	 * 設定權限-修改
	 * 
	 * @param PB_RH04
	 *            權限-修改
	 */
	public void setPB_RH04(java.lang.String PB_RH04) {
		this.PB_RH04 = PB_RH04;
	}

	/**
	 * 取得權限-確認
	 * 
	 * @return PB_RH05 權限-確認
	 */
	@Length(max = 1)
	@Column(name = "PB_RH05")
	@Config(key = "DSPB01.PB_RH05")
	public java.lang.String getPB_RH05() {
		return PB_RH05;
	}

	/**
	 * 設定權限-確認
	 * 
	 * @param PB_RH05
	 *            權限-確認
	 */
	public void setPB_RH05(java.lang.String PB_RH05) {
		this.PB_RH05 = PB_RH05;
	}

	/**
	 * 取得權限-取消確認
	 * 
	 * @return PB_RH06 權限-取消確認
	 */
	@Length(max = 1)
	@Column(name = "PB_RH06")
	@Config(key = "DSPB01.PB_RH06")
	public java.lang.String getPB_RH06() {
		return PB_RH06;
	}

	/**
	 * 設定權限-取消確認
	 * 
	 * @param PB_RH06
	 *            權限-取消確認
	 */
	public void setPB_RH06(java.lang.String PB_RH06) {
		this.PB_RH06 = PB_RH06;
	}

	/**
	 * 取得權限-成本
	 * 
	 * @return PB_RH07 權限-成本
	 */
	@Length(max = 1)
	@Column(name = "PB_RH07")
	@Config(key = "DSPB01.PB_RH07")
	public java.lang.String getPB_RH07() {
		return PB_RH07;
	}

	/**
	 * 設定權限-成本
	 * 
	 * @param PB_RH07
	 *            權限-成本
	 */
	public void setPB_RH07(java.lang.String PB_RH07) {
		this.PB_RH07 = PB_RH07;
	}

	/**
	 * 取得異動人員
	 * 
	 * @return UP_USER 異動人員
	 */
	@Length(max = 10)
	@Column(name = "UP_USER")
	@Config(key = "DSPB01.UP_USER")
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
	@Config(key = "DSPB01.UP_DATE")
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
	 * 取得權限E-Mail
	 * 
	 * @return PB_RH08 權限E-Mail
	 */
	@Length(max = 1)
	@Column(name = "PB_RH08")
	@Config(key = "DSPB01.PB_RH08")
	public java.lang.String getPB_RH08() {
		return PB_RH08;
	}

	/**
	 * 設定權限E-Mail
	 * 
	 * @param PB_RH08
	 *            權限E-Mail
	 */
	public void setPB_RH08(java.lang.String PB_RH08) {
		this.PB_RH08 = PB_RH08;
	}
}
