package ds.program.fvhr.domain;

import java.util.Date;

import javax.persistence.IdClass;

import ds.program.fvhr.domain.pk.N_DETAIL_SUB_INSURANCEPk;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;

import org.hibernate.validator.Length;

import dsc.echo2app.program.Config;

/**
* 
**/
@IdClass(N_DETAIL_SUB_INSURANCEPk.class)
@Entity
@Table(name = "N_DETAIL_SUB_INSURANCE")
public class N_DETAIL_SUB_INSURANCE {
	
	private String EMPSN;
	private int BSALARY;
	private Long INSURANCE_MONEY; // tien tru
	private int PURCHASE_STATUS; // 1 la da tru, -1 la no
	private int LANCHAY;
	private String PURCHASE_DATE; // thang dong tien bao hiem
	private Date PURCHASE_REAL_DATE; // ngay thanh toan thuc
	private String NOTE;
	private java.lang.String USER_UP;
	private java.lang.Long TOTAL_SALARY; // luong tai lan chay
	private java.lang.String MONTH_STATUS; // NSR: nghi san ra, NSV: nghi san
											// vao, NV: nghi viec, DNS: dang
											// nghi san, HH: hienhanh
	private String ID_DETAIL;
	
	/**
	 * @return EMPSN
	 */
	@Id
	@Column(name = "EMPSN")
	@Config(key = "N_DETAIL_SUB_INSURANCE.EMPSN")
	public String getEMPSN() {
		return EMPSN;
	}

	/**
	 * @param EMPSN
	 */
	public void setEMPSN(String EMPSN) {
		this.EMPSN = EMPSN;
	}

	/**
	 * @return BSALARY
	 */
	@NotBlank
	@Column(name = "BSALARY")
	@Config(key = "N_DETAIL_SUB_INSURANCE.BSALARY")
	public int getBSALARY() {
		return BSALARY;
	}

	/**
	 * @param BSALARY
	 */
	public void setBSALARY(int BSALARY) {
		this.BSALARY = BSALARY;
	}

	/**
	 * 取得tien tru
	 * 
	 * @return INSURANCE_MONEY tien tru
	 */
	@NotBlank
	@Column(name = "INSURANCE_MONEY")
	@Config(key = "N_DETAIL_SUB_INSURANCE.INSURANCE_MONEY")
	public Long getINSURANCE_MONEY() {
		return INSURANCE_MONEY;
	}

	/**
	 * 設定tien tru
	 * 
	 * @param INSURANCE_MONEY
	 *            tien tru
	 */
	public void setINSURANCE_MONEY(Long INSURANCE_MONEY) {
		this.INSURANCE_MONEY = INSURANCE_MONEY;
	}

	/**
	 * 取得1 la da tru, -1 la no
	 * 
	 * @return PURCHASE_STATUS 1 la da tru, -1 la no
	 */
	@NotBlank
	@Column(name = "PURCHASE_STATUS")
	@Config(key = "N_DETAIL_SUB_INSURANCE.PURCHASE_STATUS")
	public int getPURCHASE_STATUS() {
		return PURCHASE_STATUS;
	}

	/**
	 * 設定1 la da tru, -1 la no
	 * 
	 * @param PURCHASE_STATUS
	 *            1 la da tru, -1 la no
	 */
	public void setPURCHASE_STATUS(int PURCHASE_STATUS) {
		this.PURCHASE_STATUS = PURCHASE_STATUS;
	}

	/**
	 * @return LANCHAY
	 */
	@NotBlank
	@Column(name = "LANCHAY")
	@Config(key = "N_DETAIL_SUB_INSURANCE.LANCHAY")
	public int getLANCHAY() {
		return LANCHAY;
	}

	/**
	 * @param LANCHAY
	 */
	public void setLANCHAY(int LANCHAY) {
		this.LANCHAY = LANCHAY;
	}

	/**
	 * 取得thang dong tien bao hiem
	 * 
	 * @return PURCHASE_DATE thang dong tien bao hiem
	 */
	@Id
	@NotBlank
	@Column(name = "PURCHASE_DATE")
	@Config(key = "N_DETAIL_SUB_INSURANCE.PURCHASE_DATE")
	public String getPURCHASE_DATE() {
		return PURCHASE_DATE;
	}

	/**
	 * 設定thang dong tien bao hiem
	 * 
	 * @param PURCHASE_DATE
	 *            thang dong tien bao hiem
	 */
	public void setPURCHASE_DATE(java.lang.String PURCHASE_DATE) {
		this.PURCHASE_DATE = PURCHASE_DATE;
	}

	/**
	 * 取得ngay thanh toan thuc
	 * 
	 * @return PURCHASE_REAL_DATE ngay thanh toan thuc
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PURCHASE_REAL_DATE")
	@Config(key = "N_DETAIL_SUB_INSURANCE.PURCHASE_REAL_DATE")
	public Date getPURCHASE_REAL_DATE() {
		return PURCHASE_REAL_DATE;
	}

	/**
	 * 設定ngay thanh toan thuc
	 * 
	 * @param PURCHASE_REAL_DATE
	 *            ngay thanh toan thuc
	 */
	public void setPURCHASE_REAL_DATE(Date PURCHASE_REAL_DATE) {
		this.PURCHASE_REAL_DATE = PURCHASE_REAL_DATE;
	}

	/**
	 * @return NOTE
	 */
	@Column(name = "NOTE")
	@Config(key = "N_DETAIL_SUB_INSURANCE.NOTE")
	public java.lang.String getNOTE() {
		return NOTE;
	}

	/**
	 * @param NOTE
	 */
	public void setNOTE(String NOTE) {
		this.NOTE = NOTE;
	}

	/**
	 * @return USER_UP
	 */
	@Length(max = 15)
	@Column(name = "USER_UP")
	@Config(key = "N_DETAIL_SUB_INSURANCE.USER_UP")
	public java.lang.String getUSER_UP() {
		return USER_UP;
	}

	/**
	 * @param USER_UP
	 */
	public void setUSER_UP(java.lang.String USER_UP) {
		this.USER_UP = USER_UP;
	}

	/**
	 * 取得luong tai lan chay
	 * 
	 * @return TOTAL_SALARY luong tai lan chay
	 */
	@Column(name = "TOTAL_SALARY")
	@Config(key = "N_DETAIL_SUB_INSURANCE.TOTAL_SALARY")
	public java.lang.Long getTOTAL_SALARY() {
		return TOTAL_SALARY;
	}

	/**
	 * 設定luong tai lan chay
	 * 
	 * @param TOTAL_SALARY
	 *            luong tai lan chay
	 */
	public void setTOTAL_SALARY(java.lang.Long TOTAL_SALARY) {
		this.TOTAL_SALARY = TOTAL_SALARY;
	}

	/**
	 * 取得NSR: nghi san ra, NSV: nghi san vao, NV: nghi viec
	 * 
	 * @return MONTH_STATUS NSR: nghi san ra, NSV: nghi san vao, NV: nghi viec
	 */
	@Length(max = 20)
	@Column(name = "MONTH_STATUS")
	@Config(key = "N_DETAIL_SUB_INSURANCE.MONTH_STATUS")
	public java.lang.String getMONTH_STATUS() {
		return MONTH_STATUS;
	}

	/**
	 * 設定NSR: nghi san ra, NSV: nghi san vao, NV: nghi viec
	 * 
	 * @param MONTH_STATUS
	 *            NSR: nghi san ra, NSV: nghi san vao, NV: nghi viec
	 */
	public void setMONTH_STATUS(java.lang.String MONTH_STATUS) {
		this.MONTH_STATUS = MONTH_STATUS;
	}
	
	 /**
     * @return ID_DETAIL 
     */
    @NotBlank
    @Column(name = "ID_DETAIL")
    @Config(key = "N_DETAIL_SUB_INSURANCE.ID_DETAIL")
    public String getID_DETAIL() {
        return ID_DETAIL;
    }
    /**
     * @param ID_DETAIL 
     */
    public void setID_DETAIL(String ID_DETAIL) {
        this.ID_DETAIL = ID_DETAIL;
    }
}
