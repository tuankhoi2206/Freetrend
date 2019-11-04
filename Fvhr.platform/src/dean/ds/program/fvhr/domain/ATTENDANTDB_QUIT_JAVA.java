package ds.program.fvhr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;

import ds.program.fvhr.domain.pk.ATTENDANTDB_QUIT_JAVAPk;
import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * 
 */
@IdClass(ATTENDANTDB_QUIT_JAVAPk.class)
@Entity
@Table(name = "ATTENDANTDB_QUIT_JAVA")
public class ATTENDANTDB_QUIT_JAVA {
	private java.lang.String EMPSN; // SO THE

	private java.lang.String EMPNA; // HO TEN

	private java.lang.String DEPSN; // DON VI

	private java.lang.String POSSN;

	private java.util.Date HIRED;

	private java.util.Date OFFD;

	private java.lang.String CLASS; // BO VIEC (BV) HAY NGHI VIEC (NULL)

	private java.lang.Long BSALY;

	private java.lang.Long COMBSALY;

	private java.lang.Long BSALY_N;

	private java.lang.Long BONUS1;

	private java.lang.Long BONUS2;

	private java.lang.Long BONUS3;

	private java.lang.Long BONUS4;

	private java.lang.Long BONUS5;

	private java.lang.Long BONUS6;

	private java.lang.Long BONUS_ACN;

	private java.lang.Long JOININSU;

	private java.lang.Long JOINLUM;

	private java.lang.Long BORM;

	private java.lang.Long YLBX;

	private java.lang.Long PAYTAX;

	private java.lang.Long KQT;

	private java.lang.Double DUCLS;

	private java.lang.Double NUCLS;

	private java.lang.Double ADDCLS1;

	private java.lang.Double ADDCLS2;

	private java.lang.Double NADDCLS;

	private java.lang.Long ACN;

	private java.lang.Double ADDHOL;

	private java.lang.Double REST;

	private java.lang.Double REST_PAY;

	private java.lang.Double NWHOUR;

	private java.lang.Double LATE;

	private java.util.Date INSUR_DATE;

	private java.util.Date YLBX_DATE;

	private java.lang.String TIME_IN;

	private java.lang.String TIME_OUT;

	private java.lang.String FACT;

	private java.lang.String ACC;

	private java.lang.Double ADDHOLN;

	private java.lang.Long SIGN_TIME;

	private java.lang.String NOTE;

	private java.lang.Long ACNM;

	private java.lang.Double REST_SICK;

	private java.lang.Double OTHER;

	private java.lang.String EMPCN;

	private java.lang.Long BONUS7;

	private java.lang.Long BONUS8;

	private java.lang.Double PHEP_NS;

	private java.lang.Long TEMP;

	private java.lang.Long TEMP1;

	private java.lang.Long TEMP2;

	private java.lang.Long BONUS1_HOL;

	private java.lang.Long BONUS9;

	private java.lang.Long BAC;

	private java.lang.Long BH_TNGHIEP;

	private java.lang.Long QT_PAYTAX1;

	private java.lang.Double MM_BONUS;

	private java.lang.Double DAY_BEFORE;

	private java.lang.Double REST_REMAIN;

	private java.lang.Long BSAL_AVG;

	private java.util.Date DATE_OFF;

	private java.lang.Long BONUS2_AVG;

	private java.lang.Long STNV;

	private java.lang.Long BH_QUY;

	private java.lang.Long BU_BHYT;

	private java.lang.Long THU_BHYT;

	private java.lang.Long BUTHU_BHYT;

	private java.util.Date DOT_TV;

	private java.lang.Double MM_DENBU;

	private java.lang.String DOT_TV1;

	private java.lang.String DEPT_KT;

	private java.lang.String NOTE_BH;

	private java.lang.Double ADDCLS1_O;

	private java.lang.Double NADDCLS_O;

	private java.lang.Double ADDHOL_O;

	private java.lang.Double ADDHOLN_O;

	private java.lang.Long ADDHOLN_O_S;

	private java.lang.Long ADDHOL_O_S;

	private java.lang.Long NADDCLS_O_S;

	private java.lang.Long ADDCLS1_O_S;

	private java.lang.Long ACNM_O;

	private java.lang.String DEPSN_BHYT;

	/**
	 * 取得SO THE
	 * 
	 * @return EMPSN SO THE
	 */
	@Id
	@NotBlank
	@Column(name = "EMPSN")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.EMPSN")
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
	 * 取得HO TEN
	 * 
	 * @return EMPNA HO TEN
	 */
	@Length(max = 80)
	@Column(name = "EMPNA")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.EMPNA")
	public java.lang.String getEMPNA() {
		return EMPNA;
	}

	/**
	 * 設定HO TEN
	 * 
	 * @param EMPNA
	 *            HO TEN
	 */
	public void setEMPNA(java.lang.String EMPNA) {
		this.EMPNA = EMPNA;
	}

	/**
	 * 取得DON VI
	 * 
	 * @return DEPSN DON VI
	 */
	@Length(max = 5)
	@Column(name = "DEPSN")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.DEPSN")
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
	 * @return POSSN
	 */
	@Length(max = 25)
	@Column(name = "POSSN")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.POSSN")
	public java.lang.String getPOSSN() {
		return POSSN;
	}

	/**
	 * @param POSSN
	 */
	public void setPOSSN(java.lang.String POSSN) {
		this.POSSN = POSSN;
	}

	/**
	 * @return HIRED
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "HIRED")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.HIRED")
	public java.util.Date getHIRED() {
		return HIRED;
	}

	/**
	 * @param HIRED
	 */
	public void setHIRED(java.util.Date HIRED) {
		this.HIRED = HIRED;
	}

	/**
	 * @return OFFD
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "OFFD")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.OFFD")
	public java.util.Date getOFFD() {
		return OFFD;
	}

	/**
	 * @param OFFD
	 */
	public void setOFFD(java.util.Date OFFD) {
		this.OFFD = OFFD;
	}

	/**
	 * 取得BO VIEC (BV) HAY NGHI VIEC (NULL)
	 * 
	 * @return CLASS BO VIEC (BV) HAY NGHI VIEC (NULL)
	 */
	@Length(max = 2)
	@Column(name = "CLASS")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.CLASS")
	public java.lang.String getCLASS() {
		return CLASS;
	}

	/**
	 * 設定BO VIEC (BV) HAY NGHI VIEC (NULL)
	 * 
	 * @param CLASS
	 *            BO VIEC (BV) HAY NGHI VIEC (NULL)
	 */
	public void setCLASS(java.lang.String CLASS) {
		this.CLASS = CLASS;
	}

	/**
	 * @return BSALY
	 */
	@Column(name = "BSALY")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BSALY")
	public java.lang.Long getBSALY() {
		return BSALY;
	}

	/**
	 * @param BSALY
	 */
	public void setBSALY(java.lang.Long BSALY) {
		this.BSALY = BSALY;
	}

	/**
	 * @return COMBSALY
	 */
	@Column(name = "COMBSALY")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.COMBSALY")
	public java.lang.Long getCOMBSALY() {
		return COMBSALY;
	}

	/**
	 * @param COMBSALY
	 */
	public void setCOMBSALY(java.lang.Long COMBSALY) {
		this.COMBSALY = COMBSALY;
	}

	/**
	 * @return BSALY_N
	 */
	@Column(name = "BSALY_N")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BSALY_N")
	public java.lang.Long getBSALY_N() {
		return BSALY_N;
	}

	/**
	 * @param BSALY_N
	 */
	public void setBSALY_N(java.lang.Long BSALY_N) {
		this.BSALY_N = BSALY_N;
	}

	/**
	 * @return BONUS1
	 */
	@Column(name = "BONUS1")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BONUS1")
	public java.lang.Long getBONUS1() {
		return BONUS1;
	}

	/**
	 * @param BONUS1
	 */
	public void setBONUS1(java.lang.Long BONUS1) {
		this.BONUS1 = BONUS1;
	}

	/**
	 * @return BONUS2
	 */
	@Column(name = "BONUS2")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BONUS2")
	public java.lang.Long getBONUS2() {
		return BONUS2;
	}

	/**
	 * @param BONUS2
	 */
	public void setBONUS2(java.lang.Long BONUS2) {
		this.BONUS2 = BONUS2;
	}

	/**
	 * @return BONUS3
	 */
	@Column(name = "BONUS3")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BONUS3")
	public java.lang.Long getBONUS3() {
		return BONUS3;
	}

	/**
	 * @param BONUS3
	 */
	public void setBONUS3(java.lang.Long BONUS3) {
		this.BONUS3 = BONUS3;
	}

	/**
	 * @return BONUS4
	 */
	@Column(name = "BONUS4")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BONUS4")
	public java.lang.Long getBONUS4() {
		return BONUS4;
	}

	/**
	 * @param BONUS4
	 */
	public void setBONUS4(java.lang.Long BONUS4) {
		this.BONUS4 = BONUS4;
	}

	/**
	 * @return BONUS5
	 */
	@Column(name = "BONUS5")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BONUS5")
	public java.lang.Long getBONUS5() {
		return BONUS5;
	}

	/**
	 * @param BONUS5
	 */
	public void setBONUS5(java.lang.Long BONUS5) {
		this.BONUS5 = BONUS5;
	}

	/**
	 * @return BONUS6
	 */
	@Column(name = "BONUS6")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BONUS6")
	public java.lang.Long getBONUS6() {
		return BONUS6;
	}

	/**
	 * @param BONUS6
	 */
	public void setBONUS6(java.lang.Long BONUS6) {
		this.BONUS6 = BONUS6;
	}

	/**
	 * @return BONUS_ACN
	 */
	@Column(name = "BONUS_ACN")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BONUS_ACN")
	public java.lang.Long getBONUS_ACN() {
		return BONUS_ACN;
	}

	/**
	 * @param BONUS_ACN
	 */
	public void setBONUS_ACN(java.lang.Long BONUS_ACN) {
		this.BONUS_ACN = BONUS_ACN;
	}

	/**
	 * @return JOININSU
	 */
	@Column(name = "JOININSU")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.JOININSU")
	public java.lang.Long getJOININSU() {
		return JOININSU;
	}

	/**
	 * @param JOININSU
	 */
	public void setJOININSU(java.lang.Long JOININSU) {
		this.JOININSU = JOININSU;
	}

	/**
	 * @return JOINLUM
	 */
	@Column(name = "JOINLUM")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.JOINLUM")
	public java.lang.Long getJOINLUM() {
		return JOINLUM;
	}

	/**
	 * @param JOINLUM
	 */
	public void setJOINLUM(java.lang.Long JOINLUM) {
		this.JOINLUM = JOINLUM;
	}

	/**
	 * @return BORM
	 */
	@Column(name = "BORM")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BORM")
	public java.lang.Long getBORM() {
		return BORM;
	}

	/**
	 * @param BORM
	 */
	public void setBORM(java.lang.Long BORM) {
		this.BORM = BORM;
	}

	/**
	 * @return YLBX
	 */
	@Column(name = "YLBX")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.YLBX")
	public java.lang.Long getYLBX() {
		return YLBX;
	}

	/**
	 * @param YLBX
	 */
	public void setYLBX(java.lang.Long YLBX) {
		this.YLBX = YLBX;
	}

	/**
	 * @return PAYTAX
	 */
	@Column(name = "PAYTAX")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.PAYTAX")
	public java.lang.Long getPAYTAX() {
		return PAYTAX;
	}

	/**
	 * @param PAYTAX
	 */
	public void setPAYTAX(java.lang.Long PAYTAX) {
		this.PAYTAX = PAYTAX;
	}

	/**
	 * @return KQT
	 */
	@Column(name = "KQT")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.KQT")
	public java.lang.Long getKQT() {
		return KQT;
	}

	/**
	 * @param KQT
	 */
	public void setKQT(java.lang.Long KQT) {
		this.KQT = KQT;
	}

	/**
	 * @return DUCLS
	 */
	@Column(name = "DUCLS")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.DUCLS")
	public java.lang.Double getDUCLS() {
		return DUCLS;
	}

	/**
	 * @param DUCLS
	 */
	public void setDUCLS(java.lang.Double DUCLS) {
		this.DUCLS = DUCLS;
	}

	/**
	 * @return NUCLS
	 */
	@Column(name = "NUCLS")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.NUCLS")
	public java.lang.Double getNUCLS() {
		return NUCLS;
	}

	/**
	 * @param NUCLS
	 */
	public void setNUCLS(java.lang.Double NUCLS) {
		this.NUCLS = NUCLS;
	}

	/**
	 * @return ADDCLS1
	 */
	@Column(name = "ADDCLS1")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.ADDCLS1")
	public java.lang.Double getADDCLS1() {
		return ADDCLS1;
	}

	/**
	 * @param ADDCLS1
	 */
	public void setADDCLS1(java.lang.Double ADDCLS1) {
		this.ADDCLS1 = ADDCLS1;
	}

	/**
	 * @return ADDCLS2
	 */
	@Column(name = "ADDCLS2")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.ADDCLS2")
	public java.lang.Double getADDCLS2() {
		return ADDCLS2;
	}

	/**
	 * @param ADDCLS2
	 */
	public void setADDCLS2(java.lang.Double ADDCLS2) {
		this.ADDCLS2 = ADDCLS2;
	}

	/**
	 * @return NADDCLS
	 */
	@Column(name = "NADDCLS")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.NADDCLS")
	public java.lang.Double getNADDCLS() {
		return NADDCLS;
	}

	/**
	 * @param NADDCLS
	 */
	public void setNADDCLS(java.lang.Double NADDCLS) {
		this.NADDCLS = NADDCLS;
	}

	/**
	 * @return ACN
	 */
	@Column(name = "ACN")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.ACN")
	public java.lang.Long getACN() {
		return ACN;
	}

	/**
	 * @param ACN
	 */
	public void setACN(java.lang.Long ACN) {
		this.ACN = ACN;
	}

	/**
	 * @return ADDHOL
	 */
	@Column(name = "ADDHOL")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.ADDHOL")
	public java.lang.Double getADDHOL() {
		return ADDHOL;
	}

	/**
	 * @param ADDHOL
	 */
	public void setADDHOL(java.lang.Double ADDHOL) {
		this.ADDHOL = ADDHOL;
	}

	/**
	 * @return REST
	 */
	@Column(name = "REST")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.REST")
	public java.lang.Double getREST() {
		return REST;
	}

	/**
	 * @param REST
	 */
	public void setREST(java.lang.Double REST) {
		this.REST = REST;
	}

	/**
	 * @return REST_PAY
	 */
	@Column(name = "REST_PAY")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.REST_PAY")
	public java.lang.Double getREST_PAY() {
		return REST_PAY;
	}

	/**
	 * @param REST_PAY
	 */
	public void setREST_PAY(java.lang.Double REST_PAY) {
		this.REST_PAY = REST_PAY;
	}

	/**
	 * @return NWHOUR
	 */
	@Column(name = "NWHOUR")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.NWHOUR")
	public java.lang.Double getNWHOUR() {
		return NWHOUR;
	}

	/**
	 * @param NWHOUR
	 */
	public void setNWHOUR(java.lang.Double NWHOUR) {
		this.NWHOUR = NWHOUR;
	}

	/**
	 * @return LATE
	 */
	@Column(name = "LATE")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.LATE")
	public java.lang.Double getLATE() {
		return LATE;
	}

	/**
	 * @param data
	 */
	public void setLATE(Double data) {
		this.LATE = data;
	}

	/**
	 * @return INSUR_DATE
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INSUR_DATE")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.INSUR_DATE")
	public java.util.Date getINSUR_DATE() {
		return INSUR_DATE;
	}

	/**
	 * @param INSUR_DATE
	 */
	public void setINSUR_DATE(java.util.Date INSUR_DATE) {
		this.INSUR_DATE = INSUR_DATE;
	}

	/**
	 * @return YLBX_DATE
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "YLBX_DATE")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.YLBX_DATE")
	public java.util.Date getYLBX_DATE() {
		return YLBX_DATE;
	}

	/**
	 * @param YLBX_DATE
	 */
	public void setYLBX_DATE(java.util.Date YLBX_DATE) {
		this.YLBX_DATE = YLBX_DATE;
	}

	/**
	 * @return TIME_IN
	 */
	@Length(max = 5)
	@Column(name = "TIME_IN")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.TIME_IN")
	public java.lang.String getTIME_IN() {
		return TIME_IN;
	}

	/**
	 * @param TIME_IN
	 */
	public void setTIME_IN(java.lang.String TIME_IN) {
		this.TIME_IN = TIME_IN;
	}

	/**
	 * @return TIME_OUT
	 */
	@Length(max = 5)
	@Column(name = "TIME_OUT")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.TIME_OUT")
	public java.lang.String getTIME_OUT() {
		return TIME_OUT;
	}

	/**
	 * @param TIME_OUT
	 */
	public void setTIME_OUT(java.lang.String TIME_OUT) {
		this.TIME_OUT = TIME_OUT;
	}

	/**
	 * @return FACT
	 */
	@Length(max = 5)
	@Column(name = "FACT")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.FACT")
	public java.lang.String getFACT() {
		return FACT;
	}

	/**
	 * @param FACT
	 */
	public void setFACT(java.lang.String FACT) {
		this.FACT = FACT;
	}

	/**
	 * @return ACC
	 */
	@Length(max = 1)
	@Column(name = "ACC")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.ACC")
	public java.lang.String getACC() {
		return ACC;
	}

	/**
	 * @param ACC
	 */
	public void setACC(java.lang.String ACC) {
		this.ACC = ACC;
	}

	/**
	 * @return ADDHOLN
	 */
	@Column(name = "ADDHOLN")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.ADDHOLN")
	public java.lang.Double getADDHOLN() {
		return ADDHOLN;
	}

	/**
	 * @param ADDHOLN
	 */
	public void setADDHOLN(java.lang.Double ADDHOLN) {
		this.ADDHOLN = ADDHOLN;
	}

	/**
	 * @return SIGN_TIME
	 */
	@Column(name = "SIGN_TIME")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.SIGN_TIME")
	public java.lang.Long getSIGN_TIME() {
		return SIGN_TIME;
	}

	/**
	 * @param SIGN_TIME
	 */
	public void setSIGN_TIME(java.lang.Long SIGN_TIME) {
		this.SIGN_TIME = SIGN_TIME;
	}

	/**
	 * @return NOTE
	 */
	@Length(max = 25)
	@Column(name = "NOTE")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.NOTE")
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
	 * @return ACNM
	 */
	@Column(name = "ACNM")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.ACNM")
	public java.lang.Long getACNM() {
		return ACNM;
	}

	/**
	 * @param ACNM
	 */
	public void setACNM(java.lang.Long ACNM) {
		this.ACNM = ACNM;
	}

	/**
	 * @return REST_SICK
	 */
	@Column(name = "REST_SICK")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.REST_SICK")
	public java.lang.Double getREST_SICK() {
		return REST_SICK;
	}

	/**
	 * @param REST_SICK
	 */
	public void setREST_SICK(java.lang.Double REST_SICK) {
		this.REST_SICK = REST_SICK;
	}

	/**
	 * @return OTHER
	 */
	@Column(name = "OTHER")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.OTHER")
	public java.lang.Double getOTHER() {
		return OTHER;
	}

	/**
	 * @param OTHER
	 */
	public void setOTHER(java.lang.Double OTHER) {
		this.OTHER = OTHER;
	}

	/**
	 * @return EMPCN
	 */
	@Length(max = 10)
	@Column(name = "EMPCN")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.EMPCN")
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
	 * @return BONUS7
	 */
	@Column(name = "BONUS7")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BONUS7")
	public java.lang.Long getBONUS7() {
		return BONUS7;
	}

	/**
	 * @param BONUS7
	 */
	public void setBONUS7(java.lang.Long BONUS7) {
		this.BONUS7 = BONUS7;
	}

	/**
	 * @return BONUS8
	 */
	@Column(name = "BONUS8")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BONUS8")
	public java.lang.Long getBONUS8() {
		return BONUS8;
	}

	/**
	 * @param BONUS8
	 */
	public void setBONUS8(java.lang.Long BONUS8) {
		this.BONUS8 = BONUS8;
	}

	/**
	 * @return PHEP_NS
	 */
	@Column(name = "PHEP_NS")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.PHEP_NS")
	public java.lang.Double getPHEP_NS() {
		return PHEP_NS;
	}

	/**
	 * @param PHEP_NS
	 */
	public void setPHEP_NS(java.lang.Double PHEP_NS) {
		this.PHEP_NS = PHEP_NS;
	}

	/**
	 * @return TEMP
	 */
	@Column(name = "TEMP")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.TEMP")
	public java.lang.Long getTEMP() {
		return TEMP;
	}

	/**
	 * @param TEMP
	 */
	public void setTEMP(java.lang.Long TEMP) {
		this.TEMP = TEMP;
	}

	/**
	 * @return TEMP1
	 */
	@Column(name = "TEMP1")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.TEMP1")
	public java.lang.Long getTEMP1() {
		return TEMP1;
	}

	/**
	 * @param TEMP1
	 */
	public void setTEMP1(java.lang.Long TEMP1) {
		this.TEMP1 = TEMP1;
	}

	/**
	 * @return TEMP2
	 */
	@Column(name = "TEMP2")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.TEMP2")
	public java.lang.Long getTEMP2() {
		return TEMP2;
	}

	/**
	 * @param TEMP2
	 */
	public void setTEMP2(java.lang.Long TEMP2) {
		this.TEMP2 = TEMP2;
	}

	/**
	 * @return BONUS1_HOL
	 */
	@Column(name = "BONUS1_HOL")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BONUS1_HOL")
	public java.lang.Long getBONUS1_HOL() {
		return BONUS1_HOL;
	}

	/**
	 * @param BONUS1_HOL
	 */
	public void setBONUS1_HOL(java.lang.Long BONUS1_HOL) {
		this.BONUS1_HOL = BONUS1_HOL;
	}

	/**
	 * @return BONUS9
	 */
	@Column(name = "BONUS9")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BONUS9")
	public java.lang.Long getBONUS9() {
		return BONUS9;
	}

	/**
	 * @param BONUS9
	 */
	public void setBONUS9(java.lang.Long BONUS9) {
		this.BONUS9 = BONUS9;
	}

	/**
	 * @return BAC
	 */
	@Column(name = "BAC")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BAC")
	public java.lang.Long getBAC() {
		return BAC;
	}

	/**
	 * @param BAC
	 */
	public void setBAC(java.lang.Long BAC) {
		this.BAC = BAC;
	}

	/**
	 * @return BH_TNGHIEP
	 */
	@Column(name = "BH_TNGHIEP")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BH_TNGHIEP")
	public java.lang.Long getBH_TNGHIEP() {
		return BH_TNGHIEP;
	}

	/**
	 * @param BH_TNGHIEP
	 */
	public void setBH_TNGHIEP(java.lang.Long BH_TNGHIEP) {
		this.BH_TNGHIEP = BH_TNGHIEP;
	}

	/**
	 * @return QT_PAYTAX1
	 */
	@Column(name = "QT_PAYTAX1")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.QT_PAYTAX1")
	public java.lang.Long getQT_PAYTAX1() {
		return QT_PAYTAX1;
	}

	/**
	 * @param QT_PAYTAX1
	 */
	public void setQT_PAYTAX1(java.lang.Long QT_PAYTAX1) {
		this.QT_PAYTAX1 = QT_PAYTAX1;
	}

	/**
	 * @return MM_BONUS
	 */
	@Column(name = "M_BONUS")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.M_BONUS")
	public java.lang.Double getMM_BONUS() {
		return MM_BONUS;
	}

	/**
	 * @param MM_BONUS
	 */
	public void setMM_BONUS(java.lang.Double MM_BONUS) {
		this.MM_BONUS = MM_BONUS;
	}

	/**
	 * @return DAY_BEFORE
	 */
	@Column(name = "DAY_BEFORE")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.DAY_BEFORE")
	public java.lang.Double getDAY_BEFORE() {
		return DAY_BEFORE;
	}

	/**
	 * @param DAY_BEFORE
	 */
	public void setDAY_BEFORE(java.lang.Double DAY_BEFORE) {
		this.DAY_BEFORE = DAY_BEFORE;
	}

	/**
	 * @return REST_REMAIN
	 */
	@Column(name = "REST_REMAIN")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.REST_REMAIN")
	public java.lang.Double getREST_REMAIN() {
		return REST_REMAIN;
	}

	/**
	 * @param REST_REMAIN
	 */
	public void setREST_REMAIN(java.lang.Double REST_REMAIN) {
		this.REST_REMAIN = REST_REMAIN;
	}

	/**
	 * @return BSAL_AVG
	 */
	@Column(name = "BSAL_AVG")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BSAL_AVG")
	public java.lang.Long getBSAL_AVG() {
		return BSAL_AVG;
	}

	/**
	 * @param BSAL_AVG
	 */
	public void setBSAL_AVG(java.lang.Long BSAL_AVG) {
		this.BSAL_AVG = BSAL_AVG;
	}

	/**
	 * @return DATE_OFF
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_OFF")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.DATE_OFF")
	public java.util.Date getDATE_OFF() {
		return DATE_OFF;
	}

	/**
	 * @param DATE_OFF
	 */
	public void setDATE_OFF(java.util.Date DATE_OFF) {
		this.DATE_OFF = DATE_OFF;
	}

	/**
	 * @return BONUS2_AVG
	 */
	@Column(name = "BONUS2_AVG")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BONUS2_AVG")
	public java.lang.Long getBONUS2_AVG() {
		return BONUS2_AVG;
	}

	/**
	 * @param BONUS2_AVG
	 */
	public void setBONUS2_AVG(java.lang.Long BONUS2_AVG) {
		this.BONUS2_AVG = BONUS2_AVG;
	}

	/**
	 * @return STNV
	 */
	@Column(name = "STNV")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.STNV")
	public java.lang.Long getSTNV() {
		return STNV;
	}

	/**
	 * @param STNV
	 */
	public void setSTNV(java.lang.Long STNV) {
		this.STNV = STNV;
	}

	/**
	 * @return BH_QUY
	 */
	@Column(name = "BH_QUY")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BH_QUY")
	public java.lang.Long getBH_QUY() {
		return BH_QUY;
	}

	/**
	 * @param BH_QUY
	 */
	public void setBH_QUY(java.lang.Long BH_QUY) {
		this.BH_QUY = BH_QUY;
	}

	/**
	 * @return BU_BHYT
	 */
	@Column(name = "BU_BHYT")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BU_BHYT")
	public java.lang.Long getBU_BHYT() {
		return BU_BHYT;
	}

	/**
	 * @param BU_BHYT
	 */
	public void setBU_BHYT(java.lang.Long BU_BHYT) {
		this.BU_BHYT = BU_BHYT;
	}

	/**
	 * @return THU_BHYT
	 */
	@Column(name = "THU_BHYT")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.THU_BHYT")
	public java.lang.Long getTHU_BHYT() {
		return THU_BHYT;
	}

	/**
	 * @param THU_BHYT
	 */
	public void setTHU_BHYT(java.lang.Long THU_BHYT) {
		this.THU_BHYT = THU_BHYT;
	}

	/**
	 * @return BUTHU_BHYT
	 */
	@Column(name = "BUTHU_BHYT")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.BUTHU_BHYT")
	public java.lang.Long getBUTHU_BHYT() {
		return BUTHU_BHYT;
	}

	/**
	 * @param BUTHU_BHYT
	 */
	public void setBUTHU_BHYT(java.lang.Long BUTHU_BHYT) {
		this.BUTHU_BHYT = BUTHU_BHYT;
	}

	/**
	 * @return DOT_TV
	 */
	@Id
	@NotBlank
	@Temporal(TemporalType.DATE)
	@Column(name = "DOT_TV")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.DOT_TV")
	public java.util.Date getDOT_TV() {
		return DOT_TV;
	}

	/**
	 * @param DOT_TV
	 */
	public void setDOT_TV(java.util.Date DOT_TV) {
		this.DOT_TV = DOT_TV;
	}

	/**
	 * @return MM_DENBU
	 */
	@Column(name = "M_DENBU")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.M_DENBU")
	public java.lang.Double getMM_DENBU() {
		return MM_DENBU;
	}

	/**
	 * @param MM_DENBU
	 */
	public void setMM_DENBU(java.lang.Double MM_DENBU) {
		this.MM_DENBU = MM_DENBU;
	}

	/**
	 * @return DOT_TV1
	 */
	@Length(max = 10)
	@Column(name = "DOT_TV1")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.DOT_TV1")
	public java.lang.String getDOT_TV1() {
		return DOT_TV1;
	}

	/**
	 * @param DOT_TV1
	 */
	public void setDOT_TV1(java.lang.String DOT_TV1) {
		this.DOT_TV1 = DOT_TV1;
	}

	/**
	 * @return DEPT_KT
	 */
	@Length(max = 50)
	@Column(name = "DEPT_KT")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.DEPT_KT")
	public java.lang.String getDEPT_KT() {
		return DEPT_KT;
	}

	/**
	 * @param DEPT_KT
	 */
	public void setDEPT_KT(java.lang.String DEPT_KT) {
		this.DEPT_KT = DEPT_KT;
	}

	/**
	 * @return NOTE_BH
	 */
	@Length(max = 250)
	@Column(name = "NOTE_BH")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.NOTE_BH")
	public java.lang.String getNOTE_BH() {
		return NOTE_BH;
	}

	/**
	 * @param NOTE_BH
	 */
	public void setNOTE_BH(java.lang.String NOTE_BH) {
		this.NOTE_BH = NOTE_BH;
	}

	/**
	 * @return ADDCLS1_O
	 */
	@Column(name = "ADDCLS1_O")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.ADDCLS1_O")
	public java.lang.Double getADDCLS1_O() {
		return ADDCLS1_O;
	}

	/**
	 * @param ADDCLS1_O
	 */
	public void setADDCLS1_O(java.lang.Double ADDCLS1_O) {
		this.ADDCLS1_O = ADDCLS1_O;
	}

	/**
	 * @return NADDCLS_O
	 */
	@Column(name = "NADDCLS_O")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.NADDCLS_O")
	public java.lang.Double getNADDCLS_O() {
		return NADDCLS_O;
	}

	/**
	 * @param NADDCLS_O
	 */
	public void setNADDCLS_O(java.lang.Double NADDCLS_O) {
		this.NADDCLS_O = NADDCLS_O;
	}

	/**
	 * @return ADDHOL_O
	 */
	@Column(name = "ADDHOL_O")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.ADDHOL_O")
	public java.lang.Double getADDHOL_O() {
		return ADDHOL_O;
	}

	/**
	 * @param ADDHOL_O
	 */
	public void setADDHOL_O(java.lang.Double ADDHOL_O) {
		this.ADDHOL_O = ADDHOL_O;
	}

	/**
	 * @return ADDHOLN_O
	 */
	@Column(name = "ADDHOLN_O")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.ADDHOLN_O")
	public java.lang.Double getADDHOLN_O() {
		return ADDHOLN_O;
	}

	/**
	 * @param ADDHOLN_O
	 */
	public void setADDHOLN_O(java.lang.Double ADDHOLN_O) {
		this.ADDHOLN_O = ADDHOLN_O;
	}

	/**
	 * @return ADDHOLN_O_S
	 */
	@Column(name = "ADDHOLN_O_S")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.ADDHOLN_O_S")
	public java.lang.Long getADDHOLN_O_S() {
		return ADDHOLN_O_S;
	}

	/**
	 * @param ADDHOLN_O_S
	 */
	public void setADDHOLN_O_S(java.lang.Long ADDHOLN_O_S) {
		this.ADDHOLN_O_S = ADDHOLN_O_S;
	}

	/**
	 * @return ADDHOL_O_S
	 */
	@Column(name = "ADDHOL_O_S")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.ADDHOL_O_S")
	public java.lang.Long getADDHOL_O_S() {
		return ADDHOL_O_S;
	}

	/**
	 * @param ADDHOL_O_S
	 */
	public void setADDHOL_O_S(java.lang.Long ADDHOL_O_S) {
		this.ADDHOL_O_S = ADDHOL_O_S;
	}

	/**
	 * @return NADDCLS_O_S
	 */
	@Column(name = "NADDCLS_O_S")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.NADDCLS_O_S")
	public java.lang.Long getNADDCLS_O_S() {
		return NADDCLS_O_S;
	}

	/**
	 * @param NADDCLS_O_S
	 */
	public void setNADDCLS_O_S(java.lang.Long NADDCLS_O_S) {
		this.NADDCLS_O_S = NADDCLS_O_S;
	}

	/**
	 * @return ADDCLS1_O_S
	 */
	@Column(name = "ADDCLS1_O_S")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.ADDCLS1_O_S")
	public java.lang.Long getADDCLS1_O_S() {
		return ADDCLS1_O_S;
	}

	/**
	 * @param ADDCLS1_O_S
	 */
	public void setADDCLS1_O_S(java.lang.Long ADDCLS1_O_S) {
		this.ADDCLS1_O_S = ADDCLS1_O_S;
	}

	/**
	 * @return ACNM_O
	 */
	@Column(name = "ACNM_O")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.ACNM_O")
	public java.lang.Long getACNM_O() {
		return ACNM_O;
	}

	/**
	 * @param ACNM_O
	 */
	public void setACNM_O(java.lang.Long ACNM_O) {
		this.ACNM_O = ACNM_O;
	}

	/**
	 * @return DEPSN_BHYT
	 */
	@Length(max = 5)
	@Column(name = "DEPSN_BHYT")
	@Config(key = "ATTENDANTDB_QUIT_JAVA.DEPSN_BHYT")
	public java.lang.String getDEPSN_BHYT() {
		return DEPSN_BHYT;
	}

	/**
	 * @param DEPSN_BHYT
	 */
	public void setDEPSN_BHYT(java.lang.String DEPSN_BHYT) {
		this.DEPSN_BHYT = DEPSN_BHYT;
	}
}
