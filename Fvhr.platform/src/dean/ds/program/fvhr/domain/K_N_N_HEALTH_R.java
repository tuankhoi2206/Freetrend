package ds.program.fvhr.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Column;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.Length;
import dsc.echo2app.program.Config;

/**
 * THEO DOI KY TRINH BHYT
 **/
@Entity
@Table(name = "K_N_N_HEALTH_R")
public class K_N_N_HEALTH_R {

	private java.lang.String EMPSN;
	private java.util.Date DATE_B; // ngay bat dau tham gia quy BHYT = la
									// ngay
	// bat dau ky HDLD -- INIT VALUE
	private java.lang.Long NUM; // so thang da dong tien BHYT biến đếm số
								// tháng.
	// đóng tiền bảo hiểm sẽ trc trừ vào mỗi tháng.
	private java.lang.Long SALARY_B; // luong hd tai thoi diem mua BHYT
										// -- INIT
										// VALUE , lương thay đổi??
	private java.lang.Long LIMIT_CARD; // thoi han het han the BHYT ???
										// khong
	// dung den
	private java.lang.Long NUM_USED; // So quy tham gia BHYT(3T/lan) ,
										// quan
										// trong de phan biet nguoi tang moi
										// =
										// 0, tổng số quý tham gia bảo hiểm.
	private java.lang.String NOTE = "";
	private java.lang.Long STATUS; // trang thai lam viec. -1 : nghi
									// viec ; 0 :
	// nghi san ; 1 : di lam binh thuong --
	// INITE = 1
	private java.lang.Long DEBT; // so thang chua dong BHYT _NO TRUOC
	private java.lang.Long MONEY; // so tien BHYT cua thang hien tai
	private java.util.Date LOCK_DATE; // khoa lai thang da update, chi
										// cho phep
										// update nhung thang >
										// LOCK_DATE
	private java.lang.String DEPSN; // DON VI KY TRINH
	private java.lang.Long SALARY_M; // LUONG TAI THANG LOCK_DATE
	private java.util.Date BEAR_DATE_E; // NGAY KET THUC NGHI SAN
	private java.lang.Long DEBT_DC; // NO DC LUONG: 1 K DC DUOC,0 dc duoc
									// khi co
	// thay doi luong
	private java.util.Date THANG_TANGMOI; // THANG BAO TANG MOI KHI NGHI
											// VIEC
											// XIN DI LAM LAI
	private java.lang.Long STATUS_TT_TMOI; // 1: NVIEC TRA THE TANG MOI
											// TRONG
	// THANG_TM. 3: K TRA THE TANG 20
	// TAY -->K TRU YT CHO DEN HET GIA
	// TRI THE -->DEN T/HAN GIA HAN THE
	// MOI THI MUA MOI(1->6:t7 MOI MUA
	// YT).Sau khi xet se update ve 2 de
	// ghi nho den ky gia han the tiep
	// theo se mua BHYT,0 or null la
	// binh thuong
	private java.lang.String USER_UP;
	private java.util.Date DATE_UP;
	private java.math.BigDecimal NCCL;// Ngay cong co luong
	private java.lang.Long TONGLUONG;// Tong luong thuc te tru tien ung
	private java.lang.Long TIEN_BHYT;// Tien BHYT thuc te phai tru

	private java.lang.Long LANCHAY;
	private java.lang.Long COMMIT;
	private java.lang.Long STATUS_MONTH;

	// khoi test
	//private N_EMPLOYEE employee;

	public static final int THEM_MOI = 2;
	public static final int NGHI_SAN_VAO = 3;
	public static final int TRONG_THOI_GIAN_NGHI_SAN = 0;

	public static final int NGHI_VIEC_TANG_MOI = 4;
	public static final int NGHI_SAN_RA = 5;
	public static final int KY_HOP_DONG = 2;
	public static final int NGHI_SAN = 0;

	public static final int NGHI_VIEC = -1;
	public static final int LAM_VIEC_BINH_THUONG = 1;

	public static final int TRA_THE_TANGMOI_TRONGTHANG = 1;
	public static final int KHONG_TRA_THE_TANG_20_TAY = 3;

	/**
	 * @return EMPSN
	 */

	@Id
	@NotBlank
	@Column(name = "EMPSN")
	@Config(key = "K_N_N_HEALTH_R.EMPSN")
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
	 * 取得ngay bat dau tham gia quy BHYT = la ngay bat dau ky HDLD -- INIT VALUE
	 * 
	 * @return DATE_B ngay bat dau tham gia quy BHYT = la ngay bat dau ky HDLD
	 *         -- INIT VALUE
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_B")
	@Config(key = "K_N_N_HEALTH_R.DATE_B")
	public java.util.Date getDATE_B() {
		return DATE_B;
	}

	/**
	 * 設定ngay bat dau tham gia quy BHYT = la ngay bat dau ky HDLD -- INIT VALUE
	 * 
	 * @param DATE_B
	 *            ngay bat dau tham gia quy BHYT = la ngay bat dau ky HDLD --
	 *            INIT VALUE
	 */
	public void setDATE_B(java.util.Date DATE_B) {
		this.DATE_B = DATE_B;
	}

	/**
	 * 取得so thang da dong tien BHYT
	 * 
	 * @return NUM so thang da dong tien BHYT
	 */
	@Column(name = "NUM")
	@Config(key = "K_N_N_HEALTH_R.NUM")
	public java.lang.Long getNUM() {
		return NUM;
	}

	/**
	 * 設定so thang da dong tien BHYT
	 * 
	 * @param NUM
	 *            so thang da dong tien BHYT
	 */
	public void setNUM(java.lang.Long NUM) {
		this.NUM = NUM;
	}

	/**
	 * 取得luong hd bai thoi diem mua BHYT -- INIT VALUE
	 * 
	 * @return SALARY_B luong hd bai thoi diem mua BHYT -- INIT VALUE
	 */
	@Column(name = "SALARY_B")
	@Config(key = "K_N_N_HEALTH_R.SALARY_B")
	public java.lang.Long getSALARY_B() {
		return SALARY_B;
	}

	/**
	 * 設定luong hd bai thoi diem mua BHYT -- INIT VALUE
	 * 
	 * @param SALARY_B
	 *            luong hd bai thoi diem mua BHYT -- INIT VALUE
	 */
	public void setSALARY_B(java.lang.Long SALARY_B) {
		this.SALARY_B = SALARY_B;
	}

	/**
	 * 取得thoi han the BHYT
	 * 
	 * @return LIMIT_CARD thoi han the BHYT
	 */
	@Column(name = "LIMIT_CARD")
	@Config(key = "K_N_N_HEALTH_R.LIMIT_CARD")
	public java.lang.Long getLIMIT_CARD() {
		return LIMIT_CARD;
	}

	/**
	 * 設定thoi han the BHYT
	 * 
	 * @param LIMIT_CARD
	 *            thoi han the BHYT
	 */
	public void setLIMIT_CARD(java.lang.Long LIMIT_CARD) {
		this.LIMIT_CARD = LIMIT_CARD;
	}

	/**
	 * 取得So quy tham gia BHYT(3T/lan) , quan trong de phan biet nguoi tang moi =
	 * 0
	 * 
	 * @return NUM_USED So quy tham gia BHYT(3T/lan) , quan trong de phan biet
	 *         nguoi tang moi = 0
	 */
	@Column(name = "NUM_USED")
	@Config(key = "K_N_N_HEALTH_R.NUM_USED")
	public java.lang.Long getNUM_USED() {
		return NUM_USED;
	}

	/**
	 * 設定So quy tham gia BHYT(3T/lan) , quan trong de phan biet nguoi tang moi =
	 * 0
	 * 
	 * @param NUM_USED
	 *            So quy tham gia BHYT(3T/lan) , quan trong de phan biet nguoi
	 *            tang moi = 0
	 */
	public void setNUM_USED(java.lang.Long NUM_USED) {
		this.NUM_USED = NUM_USED;
	}

	/**
	 * @return NOTE
	 */
	@Length(max = 2000)
	@Column(name = "NOTE")
	@Config(key = "K_N_N_HEALTH_R.NOTE")
	public java.lang.String getNOTE() {
		return NOTE;
	}

	/**
	 * @param NOTE
	 */
	public void setNOTE(java.lang.String NOTE) {
		this.NOTE = NOTE;
	}

	/**
	 * 取得trang thai lam viec : -1 : nghi viec ; 0 : nghi san ; 1 : di lam binh
	 * thuong -- INITE = 1
	 * 
	 * @return STATUS trang thai lam viec : -1 : nghi viec ; 0 : nghi san ; 1 :
	 *         di lam binh thuong -- INITE = 1
	 */
	@Column(name = "STATUS")
	@Config(key = "K_N_N_HEALTH_R.STATUS")
	public java.lang.Long getSTATUS() {
		return STATUS;
	}

	/**
	 * 設定trang thai lam viec : -1 : nghi viec ; 0 : nghi san ; 1 : di lam binh
	 * thuong -- INITE = 1
	 * 
	 * @param STATUS
	 *            trang thai lam viec : -1 : nghi viec ; 0 : nghi san ; 1 : di
	 *            lam binh thuong -- INITE = 1
	 */
	public void setSTATUS(java.lang.Long STATUS) {
		this.STATUS = STATUS;
	}

	/**
	 * 取得so thang chua dong BHYT _NO TRUOC
	 * 
	 * @return DEBT so thang chua dong BHYT _NO TRUOC
	 */
	@Column(name = "DEBT")
	@Config(key = "K_N_N_HEALTH_R.DEBT")
	public java.lang.Long getDEBT() {
		return DEBT;
	}

	/**
	 * 設定so thang chua dong BHYT _NO TRUOC
	 * 
	 * @param DEBT
	 *            so thang chua dong BHYT _NO TRUOC
	 */
	public void setDEBT(java.lang.Long DEBT) {
		this.DEBT = DEBT;
	}

	/**
	 * 取得so tien BHYT cua thang hien tai
	 * 
	 * @return MONEY so tien BHYT cua thang hien tai
	 */
	@Column(name = "MONEY")
	@Config(key = "K_N_N_HEALTH_R.MONEY")
	public java.lang.Long getMONEY() {
		return MONEY;
	}

	/**
	 * 設定so tien BHYT cua thang hien tai
	 * 
	 * @param MONEY
	 *            so tien BHYT cua thang hien tai
	 */
	public void setMONEY(java.lang.Long MONEY) {
		this.MONEY = MONEY;
	}

	/**
	 * 取得khoa lai thang da update, chi cho phep update nhung thang > LOCK_DATE
	 * 
	 * @return LOCK_DATE khoa lai thang da update, chi cho phep update nhung
	 *         thang > LOCK_DATE
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "LOCK_DATE")
	@Config(key = "K_N_N_HEALTH_R.LOCK_DATE")
	public java.util.Date getLOCK_DATE() {
		return LOCK_DATE;
	}

	/**
	 * 設定khoa lai thang da update, chi cho phep update nhung thang > LOCK_DATE
	 * 
	 * @param LOCK_DATE
	 *            khoa lai thang da update, chi cho phep update nhung thang >
	 *            LOCK_DATE
	 */
	public void setLOCK_DATE(java.util.Date LOCK_DATE) {
		this.LOCK_DATE = LOCK_DATE;
	}

	/**
	 * 取得DON VI KY TRINH
	 * 
	 * @return DEPSN DON VI KY TRINH
	 */
	@Length(max = 5)
	@Column(name = "DEPSN")
	@Config(key = "K_N_N_HEALTH_R.DEPSN")
	public java.lang.String getDEPSN() {
		return DEPSN;
	}

	/**
	 * 設定DON VI KY TRINH
	 * 
	 * @param DEPSN
	 *            DON VI KY TRINH
	 */
	public void setDEPSN(java.lang.String DEPSN) {
		this.DEPSN = DEPSN;
	}

	/**
	 * 取得LUONG TAI THANG LOCK_DATE
	 * 
	 * @return SALARY_M LUONG TAI THANG LOCK_DATE
	 */
	@Column(name = "SALARY_M")
	@Config(key = "K_N_N_HEALTH_R.SALARY_M")
	public java.lang.Long getSALARY_M() {
		return SALARY_M;
	}

	/**
	 * 設定LUONG TAI THANG LOCK_DATE
	 * 
	 * @param SALARY_M
	 *            LUONG TAI THANG LOCK_DATE
	 */
	public void setSALARY_M(java.lang.Long SALARY_M) {
		this.SALARY_M = SALARY_M;
	}

	/**
	 * 取得NGAY KET THUC NGHI SAN
	 * 
	 * @return BEAR_DATE_E NGAY KET THUC NGHI SAN
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "BEAR_DATE_E")
	@Config(key = "K_N_N_HEALTH_R.BEAR_DATE_E")
	public java.util.Date getBEAR_DATE_E() {
		return BEAR_DATE_E;
	}

	/**
	 * 設定NGAY KET THUC NGHI SAN
	 * 
	 * @param BEAR_DATE_E
	 *            NGAY KET THUC NGHI SAN
	 */
	public void setBEAR_DATE_E(java.util.Date BEAR_DATE_E) {
		this.BEAR_DATE_E = BEAR_DATE_E;
	}

	/**
	 * /** 取得NO DC LUONG: 1 K DC DUOC,0 dc duoc khi co thay doi luong
	 * 
	 * @return DEBT_DC NO DC LUONG: 1 K DC DUOC,0 dc duoc khi co thay doi luong
	 */
	@Column(name = "DEBT_DC")
	@Config(key = "K_N_N_HEALTH_R.DEBT_DC")
	public java.lang.Long getDEBT_DC() {
		return DEBT_DC;
	}

	/**
	 * 設定NO DC LUONG: 1 K DC DUOC,0 dc duoc khi co thay doi luong
	 * 
	 * @param DEBT_DC
	 *            NO DC LUONG: 1 K DC DUOC,0 dc duoc khi co thay doi luong
	 */
	public void setDEBT_DC(java.lang.Long DEBT_DC) {
		this.DEBT_DC = DEBT_DC;
	}

	/**
	 * 取得THANG BAO TANG MOI KHI NGHI VIEC XIN DI LAM LAI
	 * 
	 * @return THANG_TANGMOI THANG BAO TANG MOI KHI NGHI VIEC XIN DI LAM LAI
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "THANG_TANGMOI")
	@Config(key = "K_N_N_HEALTH_R.THANG_TANGMOI")
	public java.util.Date getTHANG_TANGMOI() {
		return THANG_TANGMOI;
	}

	/**
	 * 設定THANG BAO TANG MOI KHI NGHI VIEC XIN DI LAM LAI
	 * 
	 * @param THANG_TANGMOI
	 *            THANG BAO TANG MOI KHI NGHI VIEC XIN DI LAM LAI
	 */
	public void setTHANG_TANGMOI(java.util.Date THANG_TANGMOI) {
		this.THANG_TANGMOI = THANG_TANGMOI;
	}

	/**
	 * 取得1: NVIEC TRA THE TANG MOI TRONG THANG_TM. 3: K TRA THE TANG 20 TAY -->K
	 * TRU YT CHO DEN HET GIA TRI THE -->DEN T/HAN GIA HAN THE MOI THI MUA
	 * MOI(1->6:t7 MOI MUA YT).Sau khi xet se update ve 2 de ghi nho den ky gia
	 * han the tiep theo se mua BHYT,0 or null la binh thuong
	 * 
	 * @return STATUS_TT_TMOI 1: NVIEC TRA THE TANG MOI TRONG THANG_TM. 3: K TRA
	 *         THE TANG 20 TAY -->K TRU YT CHO DEN HET GIA TRI THE -->DEN T/HAN
	 *         GIA HAN THE MOI THI MUA MOI(1->6:t7 MOI MUA YT).Sau khi xet se
	 *         update ve 2 de ghi nho den ky gia han the tiep theo se mua BHYT,0
	 *         or null la binh thuong
	 */
	@Column(name = "STATUS_TT_TMOI")
	@Config(key = "K_N_N_HEALTH_R.STATUS_TT_TMOI")
	public java.lang.Long getSTATUS_TT_TMOI() {
		return STATUS_TT_TMOI;
	}

	/**
	 * 設定1: NVIEC TRA THE TANG MOI TRONG THANG_TM. 3: K TRA THE TANG 20 TAY -->K
	 * TRU YT CHO DEN HET GIA TRI THE -->DEN T/HAN GIA HAN THE MOI THI MUA
	 * MOI(1->6:t7 MOI MUA YT).Sau khi xet se update ve 2 de ghi nho den ky gia
	 * han the tiep theo se mua BHYT,0 or null la binh thuong
	 * 
	 * @param STATUS_TT_TMOI
	 *            1: NVIEC TRA THE TANG MOI TRONG THANG_TM. 3: K TRA THE TANG 20
	 *            TAY -->K TRU YT CHO DEN HET GIA TRI THE -->DEN T/HAN GIA HAN
	 *            THE MOI THI MUA MOI(1->6:t7 MOI MUA YT).Sau khi xet se update
	 *            ve 2 de ghi nho den ky gia han the tiep theo se mua BHYT,0 or
	 *            null la binh thuong
	 */
	public void setSTATUS_TT_TMOI(java.lang.Long STATUS_TT_TMOI) {
		this.STATUS_TT_TMOI = STATUS_TT_TMOI;
	}

	/**
	 * @return USER_UP
	 */
	@Length(max = 50)
	@Column(name = "USER_UP")
	@Config(key = "K_N_N_HEALTH_R.USER_UP")
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
	 * @return DATE_UP
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_UP")
	@Config(key = "K_N_N_HEALTH_R.DATE_UP")
	public java.util.Date getDATE_UP() {
		return DATE_UP;
	}

	/**
	 * @param DATE_UP
	 */
	public void setDATE_UP(java.util.Date DATE_UP) {
		this.DATE_UP = DATE_UP;
	}

	// NCCL
	@Column(name = "NCCL")
	@Config(key = "K_N_N_HEALTH_R.NCCL")
	public java.math.BigDecimal getNCCL() {
		return NCCL;
	}

	public void setNCCL(java.math.BigDecimal NCCL) {
		this.NCCL = NCCL;
	}

	// TONG LUONG
	@Column(name = "TONGLUONG")
	@Config(key = "K_N_N_HEALTH_R.TONGLUONG")
	public java.lang.Long getTONGLUONG() {
		return TONGLUONG;
	}

	public void setTONGLUONG(java.lang.Long TONGLUONG) {
		this.TONGLUONG = TONGLUONG;
	}

	// TIEN BHYT
	@Column(name = "TIEN_BHYT")
	@Config(key = "K_N_N_HEALTH_R.TIEN_BHYT")
	public java.lang.Long getTIEN_BHYT() {
		return TIEN_BHYT;
	}

	public void setTIEN_BHYT(java.lang.Long TIEN_BHYT) {
		this.TIEN_BHYT = TIEN_BHYT;
	}

	@Column(name = "LANCHAY")
	@Config(key = "K_N_N_HEALTH_R.LANCHAY")
	public java.lang.Long getLANCHAY() {
		return LANCHAY;
	}

	public void setLANCHAY(java.lang.Long i) {
		LANCHAY = i;
	}

	@Column(name = "COMMIT")
	@Config(key = "K_N_N_HEALTH.COMMIT")
	public java.lang.Long getCOMMIT() {
		return COMMIT;
	}

	public void setCOMMIT(java.lang.Long cOMMIT) {
		COMMIT = cOMMIT;
	}

	@Column(name = "STATUS_MONTH")
	@Config(key = "K_N_N_HEALTH.STATUS_MONTH")
	public java.lang.Long getSTATUS_MONTH() {
		return STATUS_MONTH;
	}

	public void setSTATUS_MONTH(java.lang.Long sTATUS_MONTH) {
		STATUS_MONTH = sTATUS_MONTH;
	}

	public K_N_N_HEALTH_R() {

	}

	// @OneToOne(fetch = FetchType.EAGER)
	// @JoinColumn(name = "EMPSN")
	// @OneToOne
	// @JoinColumn(name = "EMPSN", referencedColumnName = "EMPSN", insertable =
	// false, updatable = false)
	// @NotFound(action = NotFoundAction.IGNORE)
	// @Config(key = "K_N_N_HEALTH_R.EMPSN")
	// public N_EMPLOYEE getEmployee() {
	// return employee;
	// }
	//
	// public void setEmployee(N_EMPLOYEE employee) {
	// this.employee = employee;
	// }

	public K_N_N_HEALTH_R(String eMPSN, Long nUM, Long sALARY_B, Long nUM_USED,
			Long sTATUS, Date tHANG_TANGMOI, Long sTATUS_TT_TMOI) {
		super();
		EMPSN = eMPSN;
		NUM = nUM;
		SALARY_B = sALARY_B;
		NUM_USED = nUM_USED;
		STATUS = sTATUS;
		THANG_TANGMOI = tHANG_TANGMOI;
		STATUS_TT_TMOI = sTATUS_TT_TMOI;
	}

}
