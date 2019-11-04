package ds.program.fvhr.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Transient;
import dsc.util.hibernate.validator.NotBlank;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.Length;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import dsc.echo2app.program.Config;

/**
* 
**/
@Entity
@Table(name = "N_EMPLOYEE")
public class N_EMPLOYEE {
	private java.lang.String EMPSN;
	private java.lang.String EMPCN;
	private java.lang.String FNAME; // HO
	private java.lang.String LNAME; // TEN
	private java.lang.String ID_NO; // CMND
	private java.lang.String BIRTHDAY; // NGAY SINH
	private java.lang.String BIRTHPLACE; // NOI SINH
	private java.lang.String PERMANENT_ADDRESS; // DC THUONG TRU
	private java.lang.String CONTACT_ADDRESS; // DC LIEN LAC
	private java.lang.String CONTACT_TEL; // DIEN THOAI LL
	private java.lang.String CONTACT_PER; // NGUOI LIEN LAC
	private java.lang.String RELIGION; // TON GIAO
	private java.lang.String EDUCATION; // TRINH DO
	private java.lang.String SEX; // GIOI TINH
	private java.lang.String MATERIAL_STATUS; // TT GIA DINH
	private java.lang.String HAVE_CHILD; // CON NHO
	private java.util.Date DATE_HIRED; // NGAY NX
	private java.lang.String NOTE; // GHI CHU
	private java.lang.String USER_MANAGE_ID; // VUNG QUAN LY
	private java.lang.String SHIFT; // CA LV
	private java.lang.String WORK_STATUS; // TT LAM VIEC
	private java.lang.String DEPSN; // DON VI
	private java.lang.String ID_BONUS_JOBS; // MA CVIEC
	private java.lang.String CITY; // TP/TINH
	private java.lang.String ID_POSS; // MA CVU
	private java.lang.String ID_JOB; // MA CVIEC
	private java.lang.String ETHNIC; // DAN TOC
	private java.lang.String POSITION; // CHUC VU
	private java.lang.String ID_PLACE; // NOI CAP CMND
	private java.math.BigDecimal EMPNO_IN_LIST; // VI TRI TRONG DS
	private java.lang.String DEPSN_TEMP; // DON VI TEMP_HTRO NSU IN BDDANH
	private java.lang.String CODE; // CODE_HO TRO CHO CT MAY THE
	private java.lang.String DEPSN_TEMP1; // HTRO KTOAN KTRA DKTCA
	private java.util.Date NGAYCAP_ID; // NGAY CAP CMND
	private java.util.Date DATE_LAMLAI; // NGAY CNV DI LAM LAI. IF NSU DA NHAP
										// NGHI VIEC SAU DO CNV DI LAM LAI THI
										// COL NAY SE CO GT
	private java.lang.String NOTE_CNV_MOI; // NOTE DO CNV NVIEC KO HOP LE VA CO
											// SU XAC NHAN CUA CHU QUAN DE QUAY
											// TRO LAI LAM VIEC. PS 19/11/2010
	private java.util.Date NGAYNX_GOC; // NGAY NX CU CUA CNV NEU DD TU KHU VUC
										// KHAC VE LT1
	private java.util.Date NGAYNX_MOI; // NGAY NX MOI CUA CNV NEU DD TU KHU VUC
										// KHAC VE LT1, KO PHAI THI NULL
	private java.lang.String TRUCTIEP_SX; // TRANG THAI CNV TRUC TIEP (Y) SX HAY
											// GTIEP (N), 062013 TT_GT_VP_GM
	private java.lang.String FULL_NAME;

	/**
	 * @return EMPSN
	 */
	@Id
	@NotBlank
	@Column(name = "EMPSN")
	@Config(key = "N_EMPLOYEE.EMPSN")
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
	 * @return EMPCN
	 */
	@Length(max = 10)
	@Column(name = "EMPCN")
	@Config(key = "N_EMPLOYEE.EMPCN")
	public java.lang.String getEMPCN() {
		return EMPCN;
	}

	/**
	 * @param EMPCN
	 */
	public void setEMPCN(java.lang.String EMPCN) {
		this.EMPCN = EMPCN;
	}

	/**
	 * 取得HO
	 * 
	 * @return FNAME HO
	 */
	@NotBlank
	@Length(max = 40)
	@Column(name = "FNAME")
	@Config(key = "N_EMPLOYEE.FNAME")
	public java.lang.String getFNAME() {
		return FNAME;
	}

	/**
	 * 設定HO
	 * 
	 * @param FNAME
	 *            HO
	 */
	public void setFNAME(java.lang.String FNAME) {
		this.FNAME = FNAME;
	}

	/**
	 * 取得TEN
	 * 
	 * @return LNAME TEN
	 */
	@NotBlank
	@Length(max = 10)
	@Column(name = "LNAME")
	@Config(key = "N_EMPLOYEE.LNAME")
	public java.lang.String getLNAME() {
		return LNAME;
	}

	/**
	 * 設定TEN
	 * 
	 * @param LNAME
	 *            TEN
	 */
	public void setLNAME(java.lang.String LNAME) {
		this.LNAME = LNAME;
	}

	/**
	 * 取得CMND
	 * 
	 * @return ID_NO CMND
	 */
	@Length(max = 15)
	@Column(name = "ID_NO")
	@Config(key = "N_EMPLOYEE.ID_NO")
	public java.lang.String getID_NO() {
		return ID_NO;
	}

	/**
	 * 設定CMND
	 * 
	 * @param ID_NO
	 *            CMND
	 */
	public void setID_NO(java.lang.String ID_NO) {
		this.ID_NO = ID_NO;
	}

	/**
	 * 取得NGAY SINH
	 * 
	 * @return BIRTHDAY NGAY SINH
	 */
	@NotBlank
	@Length(max = 12)
	@Column(name = "BIRTHDAY")
	@Config(key = "N_EMPLOYEE.BIRTHDAY")
	public java.lang.String getBIRTHDAY() {
		return BIRTHDAY;
	}

	/**
	 * 設定NGAY SINH
	 * 
	 * @param BIRTHDAY
	 *            NGAY SINH
	 */
	public void setBIRTHDAY(java.lang.String BIRTHDAY) {
		this.BIRTHDAY = BIRTHDAY;
	}

	/**
	 * 取得NOI SINH
	 * 
	 * @return BIRTHPLACE NOI SINH
	 */
	@Length(max = 250)
	@Column(name = "BIRTHPLACE")
	@Config(key = "N_EMPLOYEE.BIRTHPLACE")
	public java.lang.String getBIRTHPLACE() {
		return BIRTHPLACE;
	}

	/**
	 * 設定NOI SINH
	 * 
	 * @param BIRTHPLACE
	 *            NOI SINH
	 */
	public void setBIRTHPLACE(java.lang.String BIRTHPLACE) {
		this.BIRTHPLACE = BIRTHPLACE;
	}

	/**
	 * 取得DC THUONG TRU
	 * 
	 * @return PERMANENT_ADDRESS DC THUONG TRU
	 */
	@Length(max = 250)
	@Column(name = "PERMANENT_ADDRESS")
	@Config(key = "N_EMPLOYEE.PERMANENT_ADDRESS")
	public java.lang.String getPERMANENT_ADDRESS() {
		return PERMANENT_ADDRESS;
	}

	/**
	 * 設定DC THUONG TRU
	 * 
	 * @param PERMANENT_ADDRESS
	 *            DC THUONG TRU
	 */
	public void setPERMANENT_ADDRESS(java.lang.String PERMANENT_ADDRESS) {
		this.PERMANENT_ADDRESS = PERMANENT_ADDRESS;
	}

	/**
	 * 取得DC LIEN LAC
	 * 
	 * @return CONTACT_ADDRESS DC LIEN LAC
	 */
	@Length(max = 250)
	@Column(name = "CONTACT_ADDRESS")
	@Config(key = "N_EMPLOYEE.CONTACT_ADDRESS")
	public java.lang.String getCONTACT_ADDRESS() {
		return CONTACT_ADDRESS;
	}

	/**
	 * 設定DC LIEN LAC
	 * 
	 * @param CONTACT_ADDRESS
	 *            DC LIEN LAC
	 */
	public void setCONTACT_ADDRESS(java.lang.String CONTACT_ADDRESS) {
		this.CONTACT_ADDRESS = CONTACT_ADDRESS;
	}

	/**
	 * 取得DIEN THOAI LL
	 * 
	 * @return CONTACT_TEL DIEN THOAI LL
	 */
	@Length(max = 20)
	@Column(name = "CONTACT_TEL")
	@Config(key = "N_EMPLOYEE.CONTACT_TEL")
	public java.lang.String getCONTACT_TEL() {
		return CONTACT_TEL;
	}

	/**
	 * 設定DIEN THOAI LL
	 * 
	 * @param CONTACT_TEL
	 *            DIEN THOAI LL
	 */
	public void setCONTACT_TEL(java.lang.String CONTACT_TEL) {
		this.CONTACT_TEL = CONTACT_TEL;
	}

	/**
	 * 取得NGUOI LIEN LAC
	 * 
	 * @return CONTACT_PER NGUOI LIEN LAC
	 */
	@Length(max = 60)
	@Column(name = "CONTACT_PER")
	@Config(key = "N_EMPLOYEE.CONTACT_PER")
	public java.lang.String getCONTACT_PER() {
		return CONTACT_PER;
	}

	/**
	 * 設定NGUOI LIEN LAC
	 * 
	 * @param CONTACT_PER
	 *            NGUOI LIEN LAC
	 */
	public void setCONTACT_PER(java.lang.String CONTACT_PER) {
		this.CONTACT_PER = CONTACT_PER;
	}

	/**
	 * 取得TON GIAO
	 * 
	 * @return RELIGION TON GIAO
	 */
	@Length(max = 30)
	@Column(name = "RELIGION")
	@Config(key = "N_EMPLOYEE.RELIGION")
	public java.lang.String getRELIGION() {
		return RELIGION;
	}

	/**
	 * 設定TON GIAO
	 * 
	 * @param RELIGION
	 *            TON GIAO
	 */
	public void setRELIGION(java.lang.String RELIGION) {
		this.RELIGION = RELIGION;
	}

	/**
	 * 取得TRINH DO
	 * 
	 * @return EDUCATION TRINH DO
	 */
	@NotBlank
	@Length(max = 30)
	@Column(name = "EDUCATION")
	@Config(key = "N_EMPLOYEE.EDUCATION")
	public java.lang.String getEDUCATION() {
		return EDUCATION;
	}

	/**
	 * 設定TRINH DO
	 * 
	 * @param EDUCATION
	 *            TRINH DO
	 */
	public void setEDUCATION(java.lang.String EDUCATION) {
		this.EDUCATION = EDUCATION;
	}

	/**
	 * 取得GIOI TINH
	 * 
	 * @return SEX GIOI TINH
	 */
	@NotBlank
	@Length(max = 7)
	@Column(name = "SEX")
	@Config(key = "N_EMPLOYEE.SEX")
	public java.lang.String getSEX() {
		return SEX;
	}

	/**
	 * 設定GIOI TINH
	 * 
	 * @param SEX
	 *            GIOI TINH
	 */
	public void setSEX(java.lang.String SEX) {
		this.SEX = SEX;
	}

	/**
	 * 取得TT GIA DINH
	 * 
	 * @return MATERIAL_STATUS TT GIA DINH
	 */
	@Length(max = 1)
	@Column(name = "MATERIAL_STATUS")
	@Config(key = "N_EMPLOYEE.MATERIAL_STATUS")
	public java.lang.String getMATERIAL_STATUS() {
		return MATERIAL_STATUS;
	}

	/**
	 * 設定TT GIA DINH
	 * 
	 * @param MATERIAL_STATUS
	 *            TT GIA DINH
	 */
	public void setMATERIAL_STATUS(java.lang.String MATERIAL_STATUS) {
		this.MATERIAL_STATUS = MATERIAL_STATUS;
	}

	/**
	 * 取得CON NHO
	 * 
	 * @return HAVE_CHILD CON NHO
	 */
	@Length(max = 5)
	@Column(name = "HAVE_CHILD")
	@Config(key = "N_EMPLOYEE.HAVE_CHILD")
	public java.lang.String getHAVE_CHILD() {
		return HAVE_CHILD;
	}

	/**
	 * 設定CON NHO
	 * 
	 * @param HAVE_CHILD
	 *            CON NHO
	 */
	public void setHAVE_CHILD(java.lang.String HAVE_CHILD) {
		this.HAVE_CHILD = HAVE_CHILD;
	}

	/**
	 * 取得NGAY NX
	 * 
	 * @return DATE_HIRED NGAY NX
	 */
	@NotBlank
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_HIRED")
	@Config(key = "N_EMPLOYEE.DATE_HIRED")
	public java.util.Date getDATE_HIRED() {
		return DATE_HIRED;
	}

	/**
	 * 設定NGAY NX
	 * 
	 * @param DATE_HIRED
	 *            NGAY NX
	 */
	public void setDATE_HIRED(java.util.Date DATE_HIRED) {
		this.DATE_HIRED = DATE_HIRED;
	}

	/**
	 * 取得GHI CHU
	 * 
	 * @return NOTE GHI CHU
	 */
	@Length(max = 50)
	@Column(name = "NOTE")
	@Config(key = "N_EMPLOYEE.NOTE")
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
	 * 取得VUNG QUAN LY
	 * 
	 * @return USER_MANAGE_ID VUNG QUAN LY
	 */
	@Length(max = 3)
	@NotBlank
	@Column(name = "USER_MANAGE_ID")
	@Config(key = "N_EMPLOYEE.USER_MANAGE_ID")
	public java.lang.String getUSER_MANAGE_ID() {
		return USER_MANAGE_ID;
	}

	/**
	 * 設定VUNG QUAN LY
	 * 
	 * @param USER_MANAGE_ID
	 *            VUNG QUAN LY
	 */
	public void setUSER_MANAGE_ID(java.lang.String USER_MANAGE_ID) {
		this.USER_MANAGE_ID = USER_MANAGE_ID;
	}

	/**
	 * 取得CA LV
	 * 
	 * @return SHIFT CA LV
	 */
	@NotBlank
	@Length(max = 30)
	@Column(name = "SHIFT")
	@Config(key = "N_EMPLOYEE.SHIFT")
	public java.lang.String getSHIFT() {
		return SHIFT;
	}

	/**
	 * 設定CA LV
	 * 
	 * @param SHIFT
	 *            CA LV
	 */
	public void setSHIFT(java.lang.String SHIFT) {
		this.SHIFT = SHIFT;
	}

	/**
	 * 取得TT LAM VIEC
	 * 
	 * @return WORK_STATUS TT LAM VIEC
	 */
	@Length(max = 20)
	@Column(name = "WORK_STATUS")
	@Config(key = "N_EMPLOYEE.WORK_STATUS")
	public java.lang.String getWORK_STATUS() {
		return WORK_STATUS;
	}

	/**
	 * 設定TT LAM VIEC
	 * 
	 * @param WORK_STATUS
	 *            TT LAM VIEC
	 */
	public void setWORK_STATUS(java.lang.String WORK_STATUS) {
		this.WORK_STATUS = WORK_STATUS;
	}

	/**
	 * 取得DON VI
	 * 
	 * @return DEPSN DON VI
	 */
	@NotBlank
	@Length(max = 5)
	@Column(name = "DEPSN")
	@Config(key = "N_EMPLOYEE.DEPSN")
	public java.lang.String getDEPSN() {
		return DEPSN;
	}

	/**
	 * 設定DON VI
	 * 
	 * @param DEPSN
	 *            DON VI
	 */
	public void setDEPSN(java.lang.String DEPSN) {
		this.DEPSN = DEPSN;
	}

	/**
	 * 取得MA CVIEC
	 * 
	 * @return ID_BONUS_JOBS MA CVIEC
	 */
	@Length(max = 10)
	@Column(name = "ID_BONUS_JOBS")
	@Config(key = "N_EMPLOYEE.ID_BONUS_JOBS")
	public java.lang.String getID_BONUS_JOBS() {
		return ID_BONUS_JOBS;
	}

	/**
	 * 設定MA CVIEC
	 * 
	 * @param ID_BONUS_JOBS
	 *            MA CVIEC
	 */
	public void setID_BONUS_JOBS(java.lang.String ID_BONUS_JOBS) {
		this.ID_BONUS_JOBS = ID_BONUS_JOBS;
	}

	/**
	 * 取得TP/TINH
	 * 
	 * @return CITY TP/TINH
	 */
	@Length(max = 50)
	@NotBlank
	@Column(name = "CITY")
	@Config(key = "N_EMPLOYEE.CITY")
	public java.lang.String getCITY() {
		return CITY;
	}

	/**
	 * 設定TP/TINH
	 * 
	 * @param CITY
	 *            TP/TINH
	 */
	public void setCITY(java.lang.String CITY) {
		this.CITY = CITY;
	}

	/**
	 * 取得MA CVU
	 * 
	 * @return ID_POSS MA CVU
	 */
	@Length(max = 10)
	@Column(name = "ID_POSS")
	@Config(key = "N_EMPLOYEE.ID_POSS")
	public java.lang.String getID_POSS() {
		return ID_POSS;
	}

	/**
	 * 設定MA CVU
	 * 
	 * @param ID_POSS
	 *            MA CVU
	 */
	public void setID_POSS(java.lang.String ID_POSS) {
		this.ID_POSS = ID_POSS;
	}

	/**
	 * 取得MA CVIEC
	 * 
	 * @return ID_JOB MA CVIEC
	 */
	@Length(max = 5)
	@Column(name = "ID_JOB")
	@Config(key = "N_EMPLOYEE.ID_JOB")
	public java.lang.String getID_JOB() {
		return ID_JOB;
	}

	/**
	 * 設定MA CVIEC
	 * 
	 * @param ID_JOB
	 *            MA CVIEC
	 */
	public void setID_JOB(java.lang.String ID_JOB) {
		this.ID_JOB = ID_JOB;
	}

	/**
	 * 取得DAN TOC
	 * 
	 * @return ETHNIC DAN TOC
	 */
	@Length(max = 20)
	@Column(name = "ETHNIC")
	@Config(key = "N_EMPLOYEE.ETHNIC")
	public java.lang.String getETHNIC() {
		return ETHNIC;
	}

	/**
	 * 設定DAN TOC
	 * 
	 * @param ETHNIC
	 *            DAN TOC
	 */
	public void setETHNIC(java.lang.String ETHNIC) {
		this.ETHNIC = ETHNIC;
	}

	/**
	 * 取得CHUC VU
	 * 
	 * @return POSITION CHUC VU
	 */
	@Length(max = 25)
	@Column(name = "POSITION")
	@Config(key = "N_EMPLOYEE.POSITION")
	public java.lang.String getPOSITION() {
		return POSITION;
	}

	/**
	 * 設定CHUC VU
	 * 
	 * @param POSITION
	 *            CHUC VU
	 */
	public void setPOSITION(java.lang.String POSITION) {
		this.POSITION = POSITION;
	}

	/**
	 * 取得NOI CAP CMND
	 * 
	 * @return ID_PLACE NOI CAP CMND
	 */
	@Length(max = 50)
	@Column(name = "ID_PLACE")
	@Config(key = "N_EMPLOYEE.ID_PLACE")
	public java.lang.String getID_PLACE() {
		return ID_PLACE;
	}

	/**
	 * 設定NOI CAP CMND
	 * 
	 * @param ID_PLACE
	 *            NOI CAP CMND
	 */
	public void setID_PLACE(java.lang.String ID_PLACE) {
		this.ID_PLACE = ID_PLACE;
	}

	/**
	 * 取得VI TRI TRONG DS
	 * 
	 * @return EMPNO_IN_LIST VI TRI TRONG DS
	 */
	@Column(name = "EMPNO_IN_LIST")
	@Config(key = "N_EMPLOYEE.EMPNO_IN_LIST")
	public java.math.BigDecimal getEMPNO_IN_LIST() {
		return EMPNO_IN_LIST;
	}

	/**
	 * 設定VI TRI TRONG DS
	 * 
	 * @param EMPNO_IN_LIST
	 *            VI TRI TRONG DS
	 */
	public void setEMPNO_IN_LIST(java.math.BigDecimal EMPNO_IN_LIST) {
		this.EMPNO_IN_LIST = EMPNO_IN_LIST;
	}

	/**
	 * 取得DON VI TEMP_HTRO NSU IN BDDANH
	 * 
	 * @return DEPSN_TEMP DON VI TEMP_HTRO NSU IN BDDANH
	 */
	@Length(max = 10)
	@Column(name = "DEPSN_TEMP")
	@Config(key = "N_EMPLOYEE.DEPSN_TEMP")
	public java.lang.String getDEPSN_TEMP() {
		return DEPSN_TEMP;
	}

	/**
	 * 設定DON VI TEMP_HTRO NSU IN BDDANH
	 * 
	 * @param DEPSN_TEMP
	 *            DON VI TEMP_HTRO NSU IN BDDANH
	 */
	public void setDEPSN_TEMP(java.lang.String DEPSN_TEMP) {
		this.DEPSN_TEMP = DEPSN_TEMP;
	}

	/**
	 * 取得CODE_HO TRO CHO CT MAY THE
	 * 
	 * @return CODE CODE_HO TRO CHO CT MAY THE
	 */
	@Length(max = 8)
	@Column(name = "CODE")
	@Config(key = "N_EMPLOYEE.CODE")
	public java.lang.String getCODE() {
		return CODE;
	}

	/**
	 * 設定CODE_HO TRO CHO CT MAY THE
	 * 
	 * @param CODE
	 *            CODE_HO TRO CHO CT MAY THE
	 */
	public void setCODE(java.lang.String CODE) {
		this.CODE = CODE;
	}

	/**
	 * 取得HTRO KTOAN KTRA DKTCA
	 * 
	 * @return DEPSN_TEMP1 HTRO KTOAN KTRA DKTCA
	 */
	@Length(max = 10)
	@Column(name = "DEPSN_TEMP1")
	@Config(key = "N_EMPLOYEE.DEPSN_TEMP1")
	public java.lang.String getDEPSN_TEMP1() {
		return DEPSN_TEMP1;
	}

	/**
	 * 設定HTRO KTOAN KTRA DKTCA
	 * 
	 * @param DEPSN_TEMP1
	 *            HTRO KTOAN KTRA DKTCA
	 */
	public void setDEPSN_TEMP1(java.lang.String DEPSN_TEMP1) {
		this.DEPSN_TEMP1 = DEPSN_TEMP1;
	}

	/**
	 * 取得NGAY CAP CMND
	 * 
	 * @return NGAYCAP_ID NGAY CAP CMND
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "NGAYCAP_ID")
	@Config(key = "N_EMPLOYEE.NGAYCAP_ID")
	public java.util.Date getNGAYCAP_ID() {
		return NGAYCAP_ID;
	}

	/**
	 * 設定NGAY CAP CMND
	 * 
	 * @param NGAYCAP_ID
	 *            NGAY CAP CMND
	 */
	public void setNGAYCAP_ID(java.util.Date NGAYCAP_ID) {
		this.NGAYCAP_ID = NGAYCAP_ID;
	}

	/**
	 * 取得NGAY CNV DI LAM LAI. IF NSU DA NHAP NGHI VIEC SAU DO CNV DI LAM LAI THI
	 * COL NAY SE CO GT
	 * 
	 * @return DATE_LAMLAI NGAY CNV DI LAM LAI. IF NSU DA NHAP NGHI VIEC SAU DO
	 *         CNV DI LAM LAI THI COL NAY SE CO GT
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_LAMLAI")
	@Config(key = "N_EMPLOYEE.DATE_LAMLAI")
	public java.util.Date getDATE_LAMLAI() {
		return DATE_LAMLAI;
	}

	/**
	 * 設定NGAY CNV DI LAM LAI. IF NSU DA NHAP NGHI VIEC SAU DO CNV DI LAM LAI THI
	 * COL NAY SE CO GT
	 * 
	 * @param DATE_LAMLAI
	 *            NGAY CNV DI LAM LAI. IF NSU DA NHAP NGHI VIEC SAU DO CNV DI
	 *            LAM LAI THI COL NAY SE CO GT
	 */
	public void setDATE_LAMLAI(java.util.Date DATE_LAMLAI) {
		this.DATE_LAMLAI = DATE_LAMLAI;
	}

	/**
	 * 取得NOTE DO CNV NVIEC KO HOP LE VA CO SU XAC NHAN CUA CHU QUAN DE QUAY TRO
	 * LAI LAM VIEC. PS 19/11/2010
	 * 
	 * @return NOTE_CNV_MOI NOTE DO CNV NVIEC KO HOP LE VA CO SU XAC NHAN CUA
	 *         CHU QUAN DE QUAY TRO LAI LAM VIEC. PS 19/11/2010
	 */
	@Length(max = 250)
	@Column(name = "NOTE_CNV_MOI")
	@Config(key = "N_EMPLOYEE.NOTE_CNV_MOI")
	public java.lang.String getNOTE_CNV_MOI() {
		return NOTE_CNV_MOI;
	}

	/**
	 * 設定NOTE DO CNV NVIEC KO HOP LE VA CO SU XAC NHAN CUA CHU QUAN DE QUAY TRO
	 * LAI LAM VIEC. PS 19/11/2010
	 * 
	 * @param NOTE_CNV_MOI
	 *            NOTE DO CNV NVIEC KO HOP LE VA CO SU XAC NHAN CUA CHU QUAN DE
	 *            QUAY TRO LAI LAM VIEC. PS 19/11/2010
	 */
	public void setNOTE_CNV_MOI(java.lang.String NOTE_CNV_MOI) {
		this.NOTE_CNV_MOI = NOTE_CNV_MOI;
	}

	/**
	 * 取得NGAY NX CU CUA CNV NEU DD TU KHU VUC KHAC VE LT1
	 * 
	 * @return NGAYNX_GOC NGAY NX CU CUA CNV NEU DD TU KHU VUC KHAC VE LT1
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "NGAYNX_GOC")
	@Config(key = "N_EMPLOYEE.NGAYNX_GOC")
	public java.util.Date getNGAYNX_GOC() {
		return NGAYNX_GOC;
	}

	/**
	 * 設定NGAY NX CU CUA CNV NEU DD TU KHU VUC KHAC VE LT1
	 * 
	 * @param NGAYNX_GOC
	 *            NGAY NX CU CUA CNV NEU DD TU KHU VUC KHAC VE LT1
	 */
	public void setNGAYNX_GOC(java.util.Date NGAYNX_GOC) {
		this.NGAYNX_GOC = NGAYNX_GOC;
	}

	/**
	 * 取得NGAY NX MOI CUA CNV NEU DD TU KHU VUC KHAC VE LT1, KO PHAI THI NULL
	 * 
	 * @return NGAYNX_MOI NGAY NX MOI CUA CNV NEU DD TU KHU VUC KHAC VE LT1, KO
	 *         PHAI THI NULL
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "NGAYNX_MOI")
	@Config(key = "N_EMPLOYEE.NGAYNX_MOI")
	public java.util.Date getNGAYNX_MOI() {
		return NGAYNX_MOI;
	}

	/**
	 * 設定NGAY NX MOI CUA CNV NEU DD TU KHU VUC KHAC VE LT1, KO PHAI THI NULL
	 * 
	 * @param NGAYNX_MOI
	 *            NGAY NX MOI CUA CNV NEU DD TU KHU VUC KHAC VE LT1, KO PHAI THI
	 *            NULL
	 */
	public void setNGAYNX_MOI(java.util.Date NGAYNX_MOI) {
		this.NGAYNX_MOI = NGAYNX_MOI;
	}

	/**
	 * 取得TRANG THAI CNV TRUC TIEP (Y) SX HAY GTIEP (N)
	 * 
	 * @return TRUCTIEP_SX TRANG THAI CNV TRUC TIEP (Y) SX HAY GTIEP (N) 062013
	 *         TT-GT-VP-GM
	 */
	@NotBlank
	@Length(max = 2)
	@Column(name = "TRUCTIEP_SX")
	@Config(key = "N_EMPLOYEE.TRUCTIEP_SX")
	public java.lang.String getTRUCTIEP_SX() {
		return TRUCTIEP_SX;
	}

	/**
	 * 設定TRANG THAI CNV TRUC TIEP (Y) SX HAY GTIEP (N)
	 * 
	 * @param TRUCTIEP_SX
	 *            TRANG THAI CNV TRUC TIEP (Y) SX HAY GTIEP (N)
	 */
	public void setTRUCTIEP_SX(java.lang.String TRUCTIEP_SX) {
		this.TRUCTIEP_SX = TRUCTIEP_SX;
	}

	@Transient
	@Config(key = "N_EMPLOYEE.FULL_NAME")
	public String getFULL_NAME() {
		this.FULL_NAME = StringUtils.trim(getFNAME()) + " "
				+ StringUtils.trim(getLNAME());
		return this.FULL_NAME;
	}

	public void setFULL_NAME(String FULL_NAME) {
		this.FULL_NAME = FULL_NAME;
	}

	@Override
	public String toString() {
		return FNAME + " " + LNAME;
	}
}
