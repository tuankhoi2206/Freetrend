package ds.program.fvhr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.Length;

import ds.program.fvhr.domain.pk.ATTQUITPk;
import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

/**
 * 
 */
@IdClass(ATTQUITPk.class)
@Entity
@Table(name = "ATTQUIT")
public class ATTQUIT {
	private java.lang.String EMPSN;

	private java.lang.String EMPNA;

	private java.lang.String DEPSN;

	private java.lang.String POSSN;

	private java.util.Date HIRED;

	private java.util.Date OFFD;

	private java.lang.String CLASS;

	private java.math.BigDecimal BSALY;

	private java.math.BigDecimal COMBSALY;

	private java.math.BigDecimal BSALY_N;

	private java.math.BigDecimal BONUS1;
	
	private java.math.BigDecimal BONUS1_HOL;

	private java.math.BigDecimal BONUS2;

	private java.math.BigDecimal BONUS3;

	private java.math.BigDecimal BONUS4;

	private java.math.BigDecimal BONUS5;

	private java.math.BigDecimal BONUS6;

	private java.math.BigDecimal BONUS7;

	private java.math.BigDecimal BONUS_ACN;

	private java.math.BigDecimal JOININSU;

	private java.math.BigDecimal JOINLUM;

	private java.math.BigDecimal BORM;

	private java.math.BigDecimal YLBX;

	private java.math.BigDecimal PAYTAX;

	private java.math.BigDecimal KQT;

	private java.math.BigDecimal DUCLS_S;

	private java.math.BigDecimal NUCLS_S;

	private java.math.BigDecimal REST_PAY_S;

	private java.math.BigDecimal ADDCLS1_S;

	private java.math.BigDecimal ADDCLS2_S;

	private java.math.BigDecimal NADDCLS_S;

	private java.math.BigDecimal ADDHOL_S;

	private java.math.BigDecimal ADDHOLN_S;

	private java.math.BigDecimal LATE;

	private java.math.BigDecimal REST;

	private java.math.BigDecimal NWHOUR;

	private java.math.BigDecimal DUCLS;

	private java.math.BigDecimal NUCLS;

	private java.math.BigDecimal REST_PAY;

	private java.math.BigDecimal TDAY;

	private java.math.BigDecimal ADDCLS1;

	private java.math.BigDecimal ADDCLS2;

	private java.math.BigDecimal NADDCLS;

	private java.math.BigDecimal ADDHOL;

	private java.math.BigDecimal ADDHOLN;

	private java.lang.String NOTE;

	private java.math.BigDecimal TADDCLS;

	private java.math.BigDecimal TADDCLS2;

	private java.math.BigDecimal TBASIC;

	private java.math.BigDecimal TADDS;

	private java.math.BigDecimal TBONUS;

	private java.math.BigDecimal TINCOME;

	private java.math.BigDecimal TKQ;

	private java.math.BigDecimal TTS;

	private java.math.BigDecimal REST_SICK;

	private java.math.BigDecimal TYADDCLS;

	private java.math.BigDecimal BONUS9;

	private java.math.BigDecimal TS1;

	private java.math.BigDecimal OTHER;

	private java.math.BigDecimal PHEP_NS;

	private java.math.BigDecimal SIGN;

	private java.math.BigDecimal BAC;

	private java.math.BigDecimal TADDS_NOTAX;

	private java.math.BigDecimal BH_TNGHIEP;

	private java.math.BigDecimal PAYTAX1;

	private java.math.BigDecimal TY_PAYTAX1;

	private java.math.BigDecimal QT_PAYTAX1;

	private java.math.BigDecimal PN_CONLAI;

	private java.math.BigDecimal PN_CONLAI_S;

	private java.math.BigDecimal MM_TROCAP;

	private java.math.BigDecimal MM_TROCAP_S;

	private java.math.BigDecimal DD_NGHITRUOC;

	private java.math.BigDecimal DD_NGHITRUOC_S;

	private java.math.BigDecimal TT_TCTVIEC;

	private java.util.Date DATE_OFF;

	private java.math.BigDecimal BSAL_AVG;

	private java.math.BigDecimal TS_HIENTAI;

	private java.math.BigDecimal BONUS2_AVG;

	private java.math.BigDecimal STNV;

	private java.math.BigDecimal BH_QUY;

	private java.math.BigDecimal BU_BHYT;

	private java.math.BigDecimal THU_BHYT;

	private java.math.BigDecimal TC_BSALY;

	private java.math.BigDecimal TC_BONUS2;

	private java.math.BigDecimal BUTHU_BHYT;

	private java.math.BigDecimal BONUS2_GOC;

	private java.util.Date DOT_TV;

	private java.math.BigDecimal MM_DENBU;

	private java.math.BigDecimal MM_DENBU_S;

	private java.lang.String DOT_TV1;

	private java.math.BigDecimal TS_HIENTAI1;

	private java.math.BigDecimal TT_TCTVIEC1;

	private java.math.BigDecimal TS_HIENTAI2;

	private java.lang.String DEPT_KT;

	private java.math.BigDecimal STT;

	private java.math.BigDecimal ADDCLS1_O;

	private java.math.BigDecimal NADDCLS_O;

	private java.math.BigDecimal ADDHOL_O;

	private java.math.BigDecimal ADDHOLN_O;

	private java.math.BigDecimal ADDCLS1_O_S;

	private java.math.BigDecimal NADDCLS_O_S;

	private java.math.BigDecimal ADDHOL_O_S;

	private java.math.BigDecimal ADDHOLN_O_S;

	private java.math.BigDecimal ACNM_O;

	private java.lang.String DEPSN_BHYT;

	private java.lang.String NOTE_NV;

	/**
	 * @return EMPSN
	 */
	@Id
	@NotBlank
	@Column(name="EMPSN")
	@Config(key = "ATTQUIT.EMPSN")
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
	 * @return EMPNA
	 */
	@Length(max = 50)
	@Column(name = "EMPNA")
	@Config(key = "ATTQUIT.EMPNA")
	public java.lang.String getEMPNA() {
		return EMPNA;
	}

	/**
	 * @param EMPNA
	 */
	public void setEMPNA(java.lang.String EMPNA) {
		this.EMPNA = EMPNA;
	}

	/**
	 * @return DEPSN
	 */
	@Length(max = 5)
	@Column(name = "DEPSN")
	@Config(key = "ATTQUIT.DEPSN")
	public java.lang.String getDEPSN() {
		return DEPSN;
	}

	/**
	 * @param DEPSN
	 */
	public void setDEPSN(java.lang.String DEPSN) {
		this.DEPSN = DEPSN;
	}

	/**
	 * @return POSSN
	 */
	@Length(max = 20)
	@Column(name = "POSSN")
	@Config(key = "ATTQUIT.POSSN")
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
	@Config(key = "ATTQUIT.HIRED")
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
	@Config(key = "ATTQUIT.OFFD")
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
	 * @return CLASS
	 */
	@Length(max = 2)
	@Column(name = "CLASS")
	@Config(key = "ATTQUIT.CLASS")
	public java.lang.String getCLASS() {
		return CLASS;
	}

	/**
	 * @param CLASS
	 */
	public void setCLASS(java.lang.String CLASS) {
		this.CLASS = CLASS;
	}

	/**
	 * @return BSALY
	 */
	@Column(name = "BSALY")
	@Config(key = "ATTQUIT.BSALY")
	public java.math.BigDecimal getBSALY() {
		return BSALY;
	}

	/**
	 * @param BSALY
	 */
	public void setBSALY(java.math.BigDecimal BSALY) {
		this.BSALY = BSALY;
	}

	/**
	 * @return COMBSALY
	 */
	@Column(name = "COMBSALY")
	@Config(key = "ATTQUIT.COMBSALY")
	public java.math.BigDecimal getCOMBSALY() {
		return COMBSALY;
	}

	/**
	 * @param COMBSALY
	 */
	public void setCOMBSALY(java.math.BigDecimal COMBSALY) {
		this.COMBSALY = COMBSALY;
	}

	/**
	 * @return BSALY_N
	 */
	@Column(name = "BSALY_N")
	@Config(key = "ATTQUIT.BSALY_N")
	public java.math.BigDecimal getBSALY_N() {
		return BSALY_N;
	}

	/**
	 * @param BSALY_N
	 */
	public void setBSALY_N(java.math.BigDecimal BSALY_N) {
		this.BSALY_N = BSALY_N;
	}

	/**
	 * @return BONUS1 - Tien thuong
	 */
	@Column(name = "BONUS1")
	@Config(key = "ATTQUIT.BONUS1")
	public java.math.BigDecimal getBONUS1() {
		return BONUS1;
	}

	/**
	 * @param BONUS1
	 */
	public void setBONUS1(java.math.BigDecimal BONUS1) {
		this.BONUS1 = BONUS1;
	}

	@Column(name = "BONUS1_HOL")
	@Config(key = "ATTQUIT.BONUS1_HOL")
	public java.math.BigDecimal getBONUS1_HOL() {
		return BONUS1_HOL;
	}

	public void setBONUS1_HOL(java.math.BigDecimal bONUS1_HOL) {
		BONUS1_HOL = bONUS1_HOL;
	}

	/**
	 * @return BONUS2 - PCCV
	 */
	@Column(name = "BONUS2")
	@Config(key = "ATTQUIT.BONUS2")
	public java.math.BigDecimal getBONUS2() {
		return BONUS2;
	}

	/**
	 * @param BONUS2
	 */
	public void setBONUS2(java.math.BigDecimal BONUS2) {
		this.BONUS2 = BONUS2;
	}

	/**
	 * @return BONUS3 - PCCViec
	 */
	@Column(name = "BONUS3")
	@Config(key = "ATTQUIT.BONUS3")
	public java.math.BigDecimal getBONUS3() {
		return BONUS3;
	}

	/**
	 * @param BONUS3
	 */
	public void setBONUS3(java.math.BigDecimal BONUS3) {
		this.BONUS3 = BONUS3;
	}

	/**
	 * @return BONUS4
	 */
	@Column(name = "BONUS4")
	@Config(key = "ATTQUIT.BONUS4")
	public java.math.BigDecimal getBONUS4() {
		return BONUS4;
	}

	/**
	 * @param BONUS4
	 */
	public void setBONUS4(java.math.BigDecimal BONUS4) {
		this.BONUS4 = BONUS4;
	}

	/**
	 * @return BONUS5
	 */
	@Column(name = "BONUS5")
	@Config(key = "ATTQUIT.BONUS5")
	public java.math.BigDecimal getBONUS5() {
		return BONUS5;
	}

	/**
	 * @param BONUS5
	 */
	public void setBONUS5(java.math.BigDecimal BONUS5) {
		this.BONUS5 = BONUS5;
	}

	/**
	 * @return BONUS6
	 */
	@Column(name = "BONUS6")
	@Config(key = "ATTQUIT.BONUS6")
	public java.math.BigDecimal getBONUS6() {
		return BONUS6;
	}

	/**
	 * @param BONUS6
	 */
	public void setBONUS6(java.math.BigDecimal BONUS6) {
		this.BONUS6 = BONUS6;
	}

	/**
	 * @return BONUS7
	 */
	@Column(name = "BONUS7")
	@Config(key = "ATTQUIT.BONUS7")
	public java.math.BigDecimal getBONUS7() {
		return BONUS7;
	}

	/**
	 * @param BONUS7
	 */
	public void setBONUS7(java.math.BigDecimal BONUS7) {
		this.BONUS7 = BONUS7;
	}

	/**
	 * @return BONUS_ACN
	 */
	@Column(name = "BONUS_ACN")
	@Config(key = "ATTQUIT.BONUS_ACN")
	public java.math.BigDecimal getBONUS_ACN() {
		return BONUS_ACN;
	}

	/**
	 * @param BONUS_ACN
	 */
	public void setBONUS_ACN(java.math.BigDecimal BONUS_ACN) {
		this.BONUS_ACN = BONUS_ACN;
	}

	/**
	 * @return JOININSU
	 */
	@Column(name = "JOININSU")
	@Config(key = "ATTQUIT.JOININSU")
	public java.math.BigDecimal getJOININSU() {
		return JOININSU;
	}

	/**
	 * @param JOININSU
	 */
	public void setJOININSU(java.math.BigDecimal JOININSU) {
		this.JOININSU = JOININSU;
	}

	/**
	 * @return JOINLUM
	 */
	@Column(name = "JOINLUM")
	@Config(key = "ATTQUIT.JOINLUM")
	public java.math.BigDecimal getJOINLUM() {
		return JOINLUM;
	}

	/**
	 * @param JOINLUM
	 */
	public void setJOINLUM(java.math.BigDecimal JOINLUM) {
		this.JOINLUM = JOINLUM;
	}

	/**
	 * @return BORM
	 */
	@Column(name = "BORM")
	@Config(key = "ATTQUIT.BORM")
	public java.math.BigDecimal getBORM() {
		return BORM;
	}

	/**
	 * @param BORM
	 */
	public void setBORM(java.math.BigDecimal BORM) {
		this.BORM = BORM;
	}

	/**
	 * @return YLBX
	 */
	@Column(name = "YLBX")
	@Config(key = "ATTQUIT.YLBX")
	public java.math.BigDecimal getYLBX() {
		return YLBX;
	}

	/**
	 * @param YLBX
	 */
	public void setYLBX(java.math.BigDecimal YLBX) {
		this.YLBX = YLBX;
	}

	/**
	 * @return PAYTAX
	 */
	@Column(name = "PAYTAX")
	@Config(key = "ATTQUIT.PAYTAX")
	public java.math.BigDecimal getPAYTAX() {
		return PAYTAX;
	}

	/**
	 * @param PAYTAX
	 */
	public void setPAYTAX(java.math.BigDecimal PAYTAX) {
		this.PAYTAX = PAYTAX;
	}

	/**
	 * @return KQT
	 */
	@Column(name = "KQT")
	@Config(key = "ATTQUIT.KQT")
	public java.math.BigDecimal getKQT() {
		return KQT;
	}

	/**
	 * @param KQT
	 */
	public void setKQT(java.math.BigDecimal KQT) {
		this.KQT = KQT;
	}

	/**
	 * @return DUCLS_S
	 */
	@Column(name = "DUCLS_S")
	@Config(key = "ATTQUIT.DUCLS_S")
	public java.math.BigDecimal getDUCLS_S() {
		return DUCLS_S;
	}

	/**
	 * @param DUCLS_S
	 */
	public void setDUCLS_S(java.math.BigDecimal DUCLS_S) {
		this.DUCLS_S = DUCLS_S;
	}

	/**
	 * @return NUCLS_S
	 */
	@Column(name = "NUCLS_S")
	@Config(key = "ATTQUIT.NUCLS_S")
	public java.math.BigDecimal getNUCLS_S() {
		return NUCLS_S;
	}

	/**
	 * @param NUCLS_S
	 */
	public void setNUCLS_S(java.math.BigDecimal NUCLS_S) {
		this.NUCLS_S = NUCLS_S;
	}

	/**
	 * @return REST_PAY_S
	 */
	@Column(name = "REST_PAY_S")
	@Config(key = "ATTQUIT.REST_PAY_S")
	public java.math.BigDecimal getREST_PAY_S() {
		return REST_PAY_S;
	}

	/**
	 * @param REST_PAY_S
	 */
	public void setREST_PAY_S(java.math.BigDecimal REST_PAY_S) {
		this.REST_PAY_S = REST_PAY_S;
	}

	/**
	 * @return ADDCLS1_S
	 */
	@Column(name = "ADDCLS1_S")
	@Config(key = "ATTQUIT.ADDCLS1_S")
	public java.math.BigDecimal getADDCLS1_S() {
		return ADDCLS1_S;
	}

	/**
	 * @param ADDCLS1_S
	 */
	public void setADDCLS1_S(java.math.BigDecimal ADDCLS1_S) {
		this.ADDCLS1_S = ADDCLS1_S;
	}

	/**
	 * @return ADDCLS2_S
	 */
	@Column(name = "ADDCLS2_S")
	@Config(key = "ATTQUIT.ADDCLS2_S")
	public java.math.BigDecimal getADDCLS2_S() {
		return ADDCLS2_S;
	}

	/**
	 * @param ADDCLS2_S
	 */
	public void setADDCLS2_S(java.math.BigDecimal ADDCLS2_S) {
		this.ADDCLS2_S = ADDCLS2_S;
	}

	/**
	 * @return NADDCLS_S
	 */
	@Column(name = "NADDCLS_S")
	@Config(key = "ATTQUIT.NADDCLS_S")
	public java.math.BigDecimal getNADDCLS_S() {
		return NADDCLS_S;
	}

	/**
	 * @param NADDCLS_S
	 */
	public void setNADDCLS_S(java.math.BigDecimal NADDCLS_S) {
		this.NADDCLS_S = NADDCLS_S;
	}

	/**
	 * @return ADDHOL_S
	 */
	@Column(name = "ADDHOL_S")
	@Config(key = "ATTQUIT.ADDHOL_S")
	public java.math.BigDecimal getADDHOL_S() {
		return ADDHOL_S;
	}

	/**
	 * @param ADDHOL_S
	 */
	public void setADDHOL_S(java.math.BigDecimal ADDHOL_S) {
		this.ADDHOL_S = ADDHOL_S;
	}

	/**
	 * @return ADDHOLN_S
	 */
	@Column(name = "ADDHOLN_S")
	@Config(key = "ATTQUIT.ADDHOLN_S")
	public java.math.BigDecimal getADDHOLN_S() {
		return ADDHOLN_S;
	}

	/**
	 * @param ADDHOLN_S
	 */
	public void setADDHOLN_S(java.math.BigDecimal ADDHOLN_S) {
		this.ADDHOLN_S = ADDHOLN_S;
	}

	/**
	 * @return LATE
	 */
	@Column(name = "LATE")
	@Config(key = "ATTQUIT.LATE")
	public java.math.BigDecimal getLATE() {
		return LATE;
	}

	/**
	 * @param LATE
	 */
	public void setLATE(java.math.BigDecimal LATE) {
		this.LATE = LATE;
	}

	/**
	 * @return REST
	 */
	@Column(name = "REST")
	@Config(key = "ATTQUIT.REST")
	public java.math.BigDecimal getREST() {
		return REST;
	}

	/**
	 * @param REST
	 */
	public void setREST(java.math.BigDecimal REST) {
		this.REST = REST;
	}

	/**
	 * @return NWHOUR
	 */
	@Column(name = "NWHOUR")
	@Config(key = "ATTQUIT.NWHOUR")
	public java.math.BigDecimal getNWHOUR() {
		return NWHOUR;
	}

	/**
	 * @param NWHOUR
	 */
	public void setNWHOUR(java.math.BigDecimal NWHOUR) {
		this.NWHOUR = NWHOUR;
	}

	/**
	 * @return DUCLS
	 */
	@Column(name = "DUCLS")
	@Config(key = "ATTQUIT.DUCLS")
	public java.math.BigDecimal getDUCLS() {
		return DUCLS;
	}

	/**
	 * @param DUCLS
	 */
	public void setDUCLS(java.math.BigDecimal DUCLS) {
		this.DUCLS = DUCLS;
	}

	/**
	 * @return NUCLS
	 */
	@Column(name = "NUCLS")
	@Config(key = "ATTQUIT.NUCLS")
	public java.math.BigDecimal getNUCLS() {
		return NUCLS;
	}

	/**
	 * @param NUCLS
	 */
	public void setNUCLS(java.math.BigDecimal NUCLS) {
		this.NUCLS = NUCLS;
	}

	/**
	 * @return REST_PAY
	 */
	@Column(name = "REST_PAY")
	@Config(key = "ATTQUIT.REST_PAY")
	public java.math.BigDecimal getREST_PAY() {
		return REST_PAY;
	}

	/**
	 * @param REST_PAY
	 */
	public void setREST_PAY(java.math.BigDecimal REST_PAY) {
		this.REST_PAY = REST_PAY;
	}

	/**
	 * @return TDAY
	 */
	@Column(name = "TDAY")
	@Config(key = "ATTQUIT.TDAY")
	public java.math.BigDecimal getTDAY() {
		return TDAY;
	}

	/**
	 * @param TDAY
	 */
	public void setTDAY(java.math.BigDecimal TDAY) {
		this.TDAY = TDAY;
	}

	/**
	 * @return ADDCLS1
	 */
	@Column(name = "ADDCLS1")
	@Config(key = "ATTQUIT.ADDCLS1")
	public java.math.BigDecimal getADDCLS1() {
		return ADDCLS1;
	}

	/**
	 * @param ADDCLS1
	 */
	public void setADDCLS1(java.math.BigDecimal ADDCLS1) {
		this.ADDCLS1 = ADDCLS1;
	}

	/**
	 * @return ADDCLS2
	 */
	@Column(name = "ADDCLS2")
	@Config(key = "ATTQUIT.ADDCLS2")
	public java.math.BigDecimal getADDCLS2() {
		return ADDCLS2;
	}

	/**
	 * @param ADDCLS2
	 */
	public void setADDCLS2(java.math.BigDecimal ADDCLS2) {
		this.ADDCLS2 = ADDCLS2;
	}

	/**
	 * @return NADDCLS
	 */
	@Column(name = "NADDCLS")
	@Config(key = "ATTQUIT.NADDCLS")
	public java.math.BigDecimal getNADDCLS() {
		return NADDCLS;
	}

	/**
	 * @param NADDCLS
	 */
	public void setNADDCLS(java.math.BigDecimal NADDCLS) {
		this.NADDCLS = NADDCLS;
	}

	/**
	 * @return ADDHOL
	 */
	@Column(name = "ADDHOL")
	@Config(key = "ATTQUIT.ADDHOL")
	public java.math.BigDecimal getADDHOL() {
		return ADDHOL;
	}

	/**
	 * @param ADDHOL
	 */
	public void setADDHOL(java.math.BigDecimal ADDHOL) {
		this.ADDHOL = ADDHOL;
	}

	/**
	 * @return ADDHOLN
	 */
	@Column(name = "ADDHOLN")
	@Config(key = "ATTQUIT.ADDHOLN")
	public java.math.BigDecimal getADDHOLN() {
		return ADDHOLN;
	}

	/**
	 * @param ADDHOLN
	 */
	public void setADDHOLN(java.math.BigDecimal ADDHOLN) {
		this.ADDHOLN = ADDHOLN;
	}

	/**
	 * @return NOTE
	 */
	@Length(max = 250)
	@Column(name = "NOTE")
	@Config(key = "ATTQUIT.NOTE")
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
	 * @return TADDCLS
	 */
	@Column(name = "TADDCLS")
	@Config(key = "ATTQUIT.TADDCLS")
	public java.math.BigDecimal getTADDCLS() {
		return TADDCLS;
	}

	/**
	 * @param TADDCLS
	 */
	public void setTADDCLS(java.math.BigDecimal TADDCLS) {
		this.TADDCLS = TADDCLS;
	}

	/**
	 * @return TADDCLS2
	 */
	@Column(name = "TADDCLS2")
	@Config(key = "ATTQUIT.TADDCLS2")
	public java.math.BigDecimal getTADDCLS2() {
		return TADDCLS2;
	}

	/**
	 * @param TADDCLS2
	 */
	public void setTADDCLS2(java.math.BigDecimal TADDCLS2) {
		this.TADDCLS2 = TADDCLS2;
	}

	/**
	 * @return TBASIC
	 */
	@Column(name = "TBASIC")
	@Config(key = "ATTQUIT.TBASIC")
	public java.math.BigDecimal getTBASIC() {
		return TBASIC;
	}

	/**
	 * @param TBASIC
	 */
	public void setTBASIC(java.math.BigDecimal TBASIC) {
		this.TBASIC = TBASIC;
	}

	/**
	 * @return TADDS
	 */
	@Column(name = "TADDS")
	@Config(key = "ATTQUIT.TADDS")
	public java.math.BigDecimal getTADDS() {
		return TADDS;
	}

	/**
	 * @param TADDS
	 */
	public void setTADDS(java.math.BigDecimal TADDS) {
		this.TADDS = TADDS;
	}

	/**
	 * @return TBONUS
	 */
	@Column(name = "TBONUS")
	@Config(key = "ATTQUIT.TBONUS")
	public java.math.BigDecimal getTBONUS() {
		return TBONUS;
	}

	/**
	 * @param TBONUS
	 */
	public void setTBONUS(java.math.BigDecimal TBONUS) {
		this.TBONUS = TBONUS;
	}

	/**
	 * @return TINCOME
	 */
	@Column(name = "TINCOME")
	@Config(key = "ATTQUIT.TINCOME")
	public java.math.BigDecimal getTINCOME() {
		return TINCOME;
	}

	/**
	 * @param TINCOME
	 */
	public void setTINCOME(java.math.BigDecimal TINCOME) {
		this.TINCOME = TINCOME;
	}

	/**
	 * @return TKQ
	 */
	@Column(name = "TKQ")
	@Config(key = "ATTQUIT.TKQ")
	public java.math.BigDecimal getTKQ() {
		return TKQ;
	}

	/**
	 * @param TKQ
	 */
	public void setTKQ(java.math.BigDecimal TKQ) {
		this.TKQ = TKQ;
	}

	/**
	 * @return TTS
	 */
	@Column(name = "TS")
	@Config(key = "ATTQUIT.TS")
	public java.math.BigDecimal getTTS() {
		return TTS;
	}

	/**
	 * @param TTS
	 */
	public void setTTS(java.math.BigDecimal TTS) {
		this.TTS = TTS;
	}

	/**
	 * @return REST_SICK
	 */
	@Column(name = "REST_SICK")
	@Config(key = "ATTQUIT.REST_SICK")
	public java.math.BigDecimal getREST_SICK() {
		return REST_SICK;
	}

	/**
	 * @param REST_SICK
	 */
	public void setREST_SICK(java.math.BigDecimal REST_SICK) {
		this.REST_SICK = REST_SICK;
	}

	/**
	 * @return TYADDCLS
	 */
	@Column(name = "TYADDCLS")
	@Config(key = "ATTQUIT.TYADDCLS")
	public java.math.BigDecimal getTYADDCLS() {
		return TYADDCLS;
	}

	/**
	 * @param TYADDCLS
	 */
	public void setTYADDCLS(java.math.BigDecimal TYADDCLS) {
		this.TYADDCLS = TYADDCLS;
	}

	/**
	 * @return BONUS9
	 */
	@Column(name = "BONUS9")
	@Config(key = "ATTQUIT.BONUS9")
	public java.math.BigDecimal getBONUS9() {
		return BONUS9;
	}

	/**
	 * @param BONUS9
	 */
	public void setBONUS9(java.math.BigDecimal BONUS9) {
		this.BONUS9 = BONUS9;
	}

	/**
	 * @return TS1
	 */
	@Column(name = "TS1")
	@Config(key = "ATTQUIT.TS1")
	public java.math.BigDecimal getTS1() {
		return TS1;
	}

	/**
	 * @param TS1
	 */
	public void setTS1(java.math.BigDecimal TS1) {
		this.TS1 = TS1;
	}

	/**
	 * @return OTHER
	 */
	@Column(name = "OTHER")
	@Config(key = "ATTQUIT.OTHER")
	public java.math.BigDecimal getOTHER() {
		return OTHER;
	}

	/**
	 * @param OTHER
	 */
	public void setOTHER(java.math.BigDecimal OTHER) {
		this.OTHER = OTHER;
	}

	/**
	 * @return PHEP_NS
	 */
	@Column(name = "PHEP_NS")
	@Config(key = "ATTQUIT.PHEP_NS")
	public java.math.BigDecimal getPHEP_NS() {
		return PHEP_NS;
	}

	/**
	 * @param PHEP_NS
	 */
	public void setPHEP_NS(java.math.BigDecimal PHEP_NS) {
		this.PHEP_NS = PHEP_NS;
	}

	/**
	 * @return SIGN
	 */
	@Column(name = "SIGN")
	@Config(key = "ATTQUIT.SIGN")
	public java.math.BigDecimal getSIGN() {
		return SIGN;
	}

	/**
	 * @param SIGN
	 */
	public void setSIGN(java.math.BigDecimal SIGN) {
		this.SIGN = SIGN;
	}

	/**
	 * @return BAC
	 */
	@Column(name = "BAC")
	@Config(key = "ATTQUIT.BAC")
	public java.math.BigDecimal getBAC() {
		return BAC;
	}

	/**
	 * @param BAC
	 */
	public void setBAC(java.math.BigDecimal BAC) {
		this.BAC = BAC;
	}

	/**
	 * @return TADDS_NOTAX
	 */
	@Column(name = "TADDS_NOTAX")
	@Config(key = "ATTQUIT.TADDS_NOTAX")
	public java.math.BigDecimal getTADDS_NOTAX() {
		return TADDS_NOTAX;
	}

	/**
	 * @param TADDS_NOTAX
	 */
	public void setTADDS_NOTAX(java.math.BigDecimal TADDS_NOTAX) {
		this.TADDS_NOTAX = TADDS_NOTAX;
	}

	/**
	 * @return BH_TNGHIEP
	 */
	@Column(name = "BH_TNGHIEP")
	@Config(key = "ATTQUIT.BH_TNGHIEP")
	public java.math.BigDecimal getBH_TNGHIEP() {
		return BH_TNGHIEP;
	}

	/**
	 * @param BH_TNGHIEP
	 */
	public void setBH_TNGHIEP(java.math.BigDecimal BH_TNGHIEP) {
		this.BH_TNGHIEP = BH_TNGHIEP;
	}

	/**
	 * @return PAYTAX1
	 */
	@Column(name = "PAYTAX1")
	@Config(key = "ATTQUIT.PAYTAX1")
	public java.math.BigDecimal getPAYTAX1() {
		return PAYTAX1;
	}

	/**
	 * @param PAYTAX1
	 */
	public void setPAYTAX1(java.math.BigDecimal PAYTAX1) {
		this.PAYTAX1 = PAYTAX1;
	}

	/**
	 * @return TY_PAYTAX1
	 */
	@Column(name = "TY_PAYTAX1")
	@Config(key = "ATTQUIT.TY_PAYTAX1")
	public java.math.BigDecimal getTY_PAYTAX1() {
		return TY_PAYTAX1;
	}

	/**
	 * @param TY_PAYTAX1
	 */
	public void setTY_PAYTAX1(java.math.BigDecimal TY_PAYTAX1) {
		this.TY_PAYTAX1 = TY_PAYTAX1;
	}

	/**
	 * @return QT_PAYTAX1
	 */
	@Column(name = "QT_PAYTAX1")
	@Config(key = "ATTQUIT.QT_PAYTAX1")
	public java.math.BigDecimal getQT_PAYTAX1() {
		return QT_PAYTAX1;
	}

	/**
	 * @param QT_PAYTAX1
	 */
	public void setQT_PAYTAX1(java.math.BigDecimal QT_PAYTAX1) {
		this.QT_PAYTAX1 = QT_PAYTAX1;
	}

	/**
	 * @return PN_CONLAI
	 */
	@Column(name = "PN_CONLAI")
	@Config(key = "ATTQUIT.PN_CONLAI")
	public java.math.BigDecimal getPN_CONLAI() {
		return PN_CONLAI;
	}

	/**
	 * @param PN_CONLAI
	 */
	public void setPN_CONLAI(java.math.BigDecimal PN_CONLAI) {
		this.PN_CONLAI = PN_CONLAI;
	}

	/**
	 * @return PN_CONLAI_S
	 */
	@Column(name = "PN_CONLAI_S")
	@Config(key = "ATTQUIT.PN_CONLAI_S")
	public java.math.BigDecimal getPN_CONLAI_S() {
		return PN_CONLAI_S;
	}

	/**
	 * @param PN_CONLAI_S
	 */
	public void setPN_CONLAI_S(java.math.BigDecimal PN_CONLAI_S) {
		this.PN_CONLAI_S = PN_CONLAI_S;
	}

	/**
	 * @return MM_TROCAP
	 */
	@Column(name = "M_TROCAP")
	@Config(key = "ATTQUIT.M_TROCAP")
	public java.math.BigDecimal getMM_TROCAP() {
		return MM_TROCAP;
	}

	/**
	 * @param MM_TROCAP
	 */
	public void setMM_TROCAP(java.math.BigDecimal MM_TROCAP) {
		this.MM_TROCAP = MM_TROCAP;
	}

	/**
	 * @return MM_TROCAP_S
	 */
	@Column(name = "M_TROCAP_S")
	@Config(key = "ATTQUIT.M_TROCAP_S")
	public java.math.BigDecimal getMM_TROCAP_S() {
		return MM_TROCAP_S;
	}

	/**
	 * @param MM_TROCAP_S
	 */
	public void setMM_TROCAP_S(java.math.BigDecimal MM_TROCAP_S) {
		this.MM_TROCAP_S = MM_TROCAP_S;
	}

	/**
	 * @return DD_NGHITRUOC
	 */
	@Column(name = "D_NGHITRUOC")
	@Config(key = "ATTQUIT.D_NGHITRUOC")
	public java.math.BigDecimal getDD_NGHITRUOC() {
		return DD_NGHITRUOC;
	}

	/**
	 * @param DD_NGHITRUOC
	 */
	public void setDD_NGHITRUOC(java.math.BigDecimal DD_NGHITRUOC) {
		this.DD_NGHITRUOC = DD_NGHITRUOC;
	}

	/**
	 * @return DD_NGHITRUOC_S
	 */
	@Column(name = "D_NGHITRUOC_S")
	@Config(key = "ATTQUIT.D_NGHITRUOC_S")
	public java.math.BigDecimal getDD_NGHITRUOC_S() {
		return DD_NGHITRUOC_S;
	}

	/**
	 * @param DD_NGHITRUOC_S
	 */
	public void setDD_NGHITRUOC_S(java.math.BigDecimal DD_NGHITRUOC_S) {
		this.DD_NGHITRUOC_S = DD_NGHITRUOC_S;
	}

	/**
	 * @return TT_TCTVIEC
	 */
	@Column(name = "T_TCTVIEC")
	@Config(key = "ATTQUIT.T_TCTVIEC")
	public java.math.BigDecimal getTT_TCTVIEC() {
		return TT_TCTVIEC;
	}

	/**
	 * @param TT_TCTVIEC
	 */
	public void setTT_TCTVIEC(java.math.BigDecimal TT_TCTVIEC) {
		this.TT_TCTVIEC = TT_TCTVIEC;
	}

	/**
	 * @return DATE_OFF
	 */
	@NotBlank
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_OFF")
	@Config(key = "ATTQUIT.DATE_OFF")
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
	 * @return BSAL_AVG
	 */
	@Column(name = "BSAL_AVG")
	@Config(key = "ATTQUIT.BSAL_AVG")
	public java.math.BigDecimal getBSAL_AVG() {
		return BSAL_AVG;
	}

	/**
	 * @param BSAL_AVG
	 */
	public void setBSAL_AVG(java.math.BigDecimal BSAL_AVG) {
		this.BSAL_AVG = BSAL_AVG;
	}

	/**
	 * @return TS_HIENTAI
	 */
	@Column(name = "TS_HIENTAI")
	@Config(key = "ATTQUIT.TS_HIENTAI")
	public java.math.BigDecimal getTS_HIENTAI() {
		return TS_HIENTAI;
	}

	/**
	 * @param TS_HIENTAI
	 */
	public void setTS_HIENTAI(java.math.BigDecimal TS_HIENTAI) {
		this.TS_HIENTAI = TS_HIENTAI;
	}

	/**
	 * @return BONUS2_AVG
	 */
	@Column(name = "BONUS2_AVG")
	@Config(key = "ATTQUIT.BONUS2_AVG")
	public java.math.BigDecimal getBONUS2_AVG() {
		return BONUS2_AVG;
	}

	/**
	 * @param BONUS2_AVG
	 */
	public void setBONUS2_AVG(java.math.BigDecimal BONUS2_AVG) {
		this.BONUS2_AVG = BONUS2_AVG;
	}

	/**
	 * @return STNV
	 */
	@Column(name = "STNV")
	@Config(key = "ATTQUIT.STNV")
	public java.math.BigDecimal getSTNV() {
		return STNV;
	}

	/**
	 * @param STNV
	 */
	public void setSTNV(java.math.BigDecimal STNV) {
		this.STNV = STNV;
	}

	/**
	 * @return BH_QUY
	 */
	@Column(name = "BH_QUY")
	@Config(key = "ATTQUIT.BH_QUY")
	public java.math.BigDecimal getBH_QUY() {
		return BH_QUY;
	}

	/**
	 * @param BH_QUY
	 */
	public void setBH_QUY(java.math.BigDecimal BH_QUY) {
		this.BH_QUY = BH_QUY;
	}

	/**
	 * @return BU_BHYT
	 */
	@Column(name = "BU_BHYT")
	@Config(key = "ATTQUIT.BU_BHYT")
	public java.math.BigDecimal getBU_BHYT() {
		return BU_BHYT;
	}

	/**
	 * @param BU_BHYT
	 */
	public void setBU_BHYT(java.math.BigDecimal BU_BHYT) {
		this.BU_BHYT = BU_BHYT;
	}

	/**
	 * @return THU_BHYT
	 */
	@Column(name = "THU_BHYT")
	@Config(key = "ATTQUIT.THU_BHYT")
	public java.math.BigDecimal getTHU_BHYT() {
		return THU_BHYT;
	}

	/**
	 * @param THU_BHYT
	 */
	public void setTHU_BHYT(java.math.BigDecimal THU_BHYT) {
		this.THU_BHYT = THU_BHYT;
	}

	/**
	 * @return TC_BSALY
	 */
	@Column(name = "TC_BSALY")
	@Config(key = "ATTQUIT.TC_BSALY")
	public java.math.BigDecimal getTC_BSALY() {
		return TC_BSALY;
	}

	/**
	 * @param TC_BSALY
	 */
	public void setTC_BSALY(java.math.BigDecimal TC_BSALY) {
		this.TC_BSALY = TC_BSALY;
	}

	/**
	 * @return TC_BONUS2
	 */
	@Column(name = "TC_BONUS2")
	@Config(key = "ATTQUIT.TC_BONUS2")
	public java.math.BigDecimal getTC_BONUS2() {
		return TC_BONUS2;
	}

	/**
	 * @param TC_BONUS2
	 */
	public void setTC_BONUS2(java.math.BigDecimal TC_BONUS2) {
		this.TC_BONUS2 = TC_BONUS2;
	}

	/**
	 * @return BUTHU_BHYT
	 */
	@Column(name = "BUTHU_BHYT")
	@Config(key = "ATTQUIT.BUTHU_BHYT")
	public java.math.BigDecimal getBUTHU_BHYT() {
		return BUTHU_BHYT;
	}

	/**
	 * @param BUTHU_BHYT
	 */
	public void setBUTHU_BHYT(java.math.BigDecimal BUTHU_BHYT) {
		this.BUTHU_BHYT = BUTHU_BHYT;
	}

	/**
	 * @return BONUS2_GOC
	 */
	@Column(name = "BONUS2_GOC")
	@Config(key = "ATTQUIT.BONUS2_GOC")
	public java.math.BigDecimal getBONUS2_GOC() {
		return BONUS2_GOC;
	}

	/**
	 * @param BONUS2_GOC
	 */
	public void setBONUS2_GOC(java.math.BigDecimal BONUS2_GOC) {
		this.BONUS2_GOC = BONUS2_GOC;
	}

	/**
	 * @return DOT_TV
	 */
	@Id
	@NotBlank
	@Temporal(TemporalType.DATE)
	@Column(name = "DOT_TV")
	@Config(key = "ATTQUIT.DOT_TV")
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
	@Config(key = "ATTQUIT.M_DENBU")
	public java.math.BigDecimal getMM_DENBU() {
		return MM_DENBU;
	}

	/**
	 * @param MM_DENBU
	 */
	public void setMM_DENBU(java.math.BigDecimal MM_DENBU) {
		this.MM_DENBU = MM_DENBU;
	}

	/**
	 * @return MM_DENBU_S
	 */
	@Column(name = "M_DENBU_S")
	@Config(key = "ATTQUIT.M_DENBU_S")
	public java.math.BigDecimal getMM_DENBU_S() {
		return MM_DENBU_S;
	}

	/**
	 * @param MM_DENBU_S
	 */
	public void setMM_DENBU_S(java.math.BigDecimal MM_DENBU_S) {
		this.MM_DENBU_S = MM_DENBU_S;
	}

	/**
	 * @return DOT_TV1
	 */
	@Length(max = 10)
	@Column(name = "DOT_TV1")
	@Config(key = "ATTQUIT.DOT_TV1")
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
	 * @return TS_HIENTAI1
	 */
	@Column(name = "TS_HIENTAI1")
	@Config(key = "ATTQUIT.TS_HIENTAI1")
	public java.math.BigDecimal getTS_HIENTAI1() {
		return TS_HIENTAI1;
	}

	/**
	 * @param TS_HIENTAI1
	 */
	public void setTS_HIENTAI1(java.math.BigDecimal TS_HIENTAI1) {
		this.TS_HIENTAI1 = TS_HIENTAI1;
	}

	/**
	 * @return TT_TCTVIEC1
	 */
	@Column(name = "T_TCTVIEC1")
	@Config(key = "ATTQUIT.T_TCTVIEC1")
	public java.math.BigDecimal getTT_TCTVIEC1() {
		return TT_TCTVIEC1;
	}

	/**
	 * @param TT_TCTVIEC1
	 */
	public void setTT_TCTVIEC1(java.math.BigDecimal TT_TCTVIEC1) {
		this.TT_TCTVIEC1 = TT_TCTVIEC1;
	}

	/**
	 * @return TS_HIENTAI2
	 */
	@Column(name = "TS_HIENTAI2")
	@Config(key = "ATTQUIT.TS_HIENTAI2")
	public java.math.BigDecimal getTS_HIENTAI2() {
		return TS_HIENTAI2;
	}

	/**
	 * @param TS_HIENTAI2
	 */
	public void setTS_HIENTAI2(java.math.BigDecimal TS_HIENTAI2) {
		this.TS_HIENTAI2 = TS_HIENTAI2;
	}

	/**
	 * @return DEPT_KT
	 */
	@Length(max = 50)
	@Column(name = "DEPT_KT")
	@Config(key = "ATTQUIT.DEPT_KT")
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
	 * @return STT
	 */
	@Column(name = "STT")
	@Config(key = "ATTQUIT.STT")
	public java.math.BigDecimal getSTT() {
		return STT;
	}

	/**
	 * @param STT
	 */
	public void setSTT(java.math.BigDecimal STT) {
		this.STT = STT;
	}

	/**
	 * @return ADDCLS1_O
	 */
	@Column(name = "ADDCLS1_O")
	@Config(key = "ATTQUIT.ADDCLS1_O")
	public java.math.BigDecimal getADDCLS1_O() {
		return ADDCLS1_O;
	}

	/**
	 * @param ADDCLS1_O
	 */
	public void setADDCLS1_O(java.math.BigDecimal ADDCLS1_O) {
		this.ADDCLS1_O = ADDCLS1_O;
	}

	/**
	 * @return NADDCLS_O
	 */
	@Column(name = "NADDCLS_O")
	@Config(key = "ATTQUIT.NADDCLS_O")
	public java.math.BigDecimal getNADDCLS_O() {
		return NADDCLS_O;
	}

	/**
	 * @param NADDCLS_O
	 */
	public void setNADDCLS_O(java.math.BigDecimal NADDCLS_O) {
		this.NADDCLS_O = NADDCLS_O;
	}

	/**
	 * @return ADDHOL_O
	 */
	@Column(name = "ADDHOL_O")
	@Config(key = "ATTQUIT.ADDHOL_O")
	public java.math.BigDecimal getADDHOL_O() {
		return ADDHOL_O;
	}

	/**
	 * @param ADDHOL_O
	 */
	public void setADDHOL_O(java.math.BigDecimal ADDHOL_O) {
		this.ADDHOL_O = ADDHOL_O;
	}

	/**
	 * @return ADDHOLN_O
	 */
	@Column(name = "ADDHOLN_O")
	@Config(key = "ATTQUIT.ADDHOLN_O")
	public java.math.BigDecimal getADDHOLN_O() {
		return ADDHOLN_O;
	}

	/**
	 * @param ADDHOLN_O
	 */
	public void setADDHOLN_O(java.math.BigDecimal ADDHOLN_O) {
		this.ADDHOLN_O = ADDHOLN_O;
	}

	/**
	 * @return ADDCLS1_O_S
	 */
	@Column(name = "ADDCLS1_O_S")
	@Config(key = "ATTQUIT.ADDCLS1_O_S")
	public java.math.BigDecimal getADDCLS1_O_S() {
		return ADDCLS1_O_S;
	}

	/**
	 * @param ADDCLS1_O_S
	 */
	public void setADDCLS1_O_S(java.math.BigDecimal ADDCLS1_O_S) {
		this.ADDCLS1_O_S = ADDCLS1_O_S;
	}

	/**
	 * @return NADDCLS_O_S
	 */
	@Column(name = "NADDCLS_O_S")
	@Config(key = "ATTQUIT.NADDCLS_O_S")
	public java.math.BigDecimal getNADDCLS_O_S() {
		return NADDCLS_O_S;
	}

	/**
	 * @param NADDCLS_O_S
	 */
	public void setNADDCLS_O_S(java.math.BigDecimal NADDCLS_O_S) {
		this.NADDCLS_O_S = NADDCLS_O_S;
	}

	/**
	 * @return ADDHOL_O_S
	 */
	@Column(name = "ADDHOL_O_S")
	@Config(key = "ATTQUIT.ADDHOL_O_S")
	public java.math.BigDecimal getADDHOL_O_S() {
		return ADDHOL_O_S;
	}

	/**
	 * @param ADDHOL_O_S
	 */
	public void setADDHOL_O_S(java.math.BigDecimal ADDHOL_O_S) {
		this.ADDHOL_O_S = ADDHOL_O_S;
	}

	/**
	 * @return ADDHOLN_O_S
	 */
	@Column(name = "ADDHOLN_O_S")
	@Config(key = "ATTQUIT.ADDHOLN_O_S")
	public java.math.BigDecimal getADDHOLN_O_S() {
		return ADDHOLN_O_S;
	}

	/**
	 * @param ADDHOLN_O_S
	 */
	public void setADDHOLN_O_S(java.math.BigDecimal ADDHOLN_O_S) {
		this.ADDHOLN_O_S = ADDHOLN_O_S;
	}

	/**
	 * @return ACNM_O
	 */
	@Column(name = "ACNM_O")
	@Config(key = "ATTQUIT.ACNM_O")
	public java.math.BigDecimal getACNM_O() {
		return ACNM_O;
	}

	/**
	 * @param ACNM_O
	 */
	public void setACNM_O(java.math.BigDecimal ACNM_O) {
		this.ACNM_O = ACNM_O;
	}

	/**
	 * @return DEPSN_BHYT
	 */
	@Length(max = 5)
	@Column(name = "DEPSN_BHYT")
	@Config(key = "ATTQUIT.DEPSN_BHYT")
	public java.lang.String getDEPSN_BHYT() {
		return DEPSN_BHYT;
	}

	/**
	 * @param DEPSN_BHYT
	 */
	public void setDEPSN_BHYT(java.lang.String DEPSN_BHYT) {
		this.DEPSN_BHYT = DEPSN_BHYT;
	}

	/**
	 * @return NOTE_NV
	 */
	@Length(max = 250)
	@Column(name = "NOTE_NV")
	@Config(key = "ATTQUIT.NOTE_NV")
	public java.lang.String getNOTE_NV() {
		return NOTE_NV;
	}

	/**
	 * @param NOTE_NV
	 */
	public void setNOTE_NV(java.lang.String NOTE_NV) {
		this.NOTE_NV = NOTE_NV;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof ATTQUITPk))
			return false;
		ATTQUIT castOther = (ATTQUIT) other;
		return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(
				DOT_TV, castOther.DOT_TV).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(EMPSN).append(DOT_TV).toHashCode();
	}
}
