package ds.program.fvhr.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import org.hibernate.validator.Length;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@Entity
@Table(name = "ATT200000")
public class ATT200000 {
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
    private java.math.BigDecimal BONUS2;
    private java.math.BigDecimal BONUS3;
    private java.math.BigDecimal BONUS4;	//HIEN TAI=BONUS4+BONUS8, T062011
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
    private java.math.BigDecimal BONUS2_GOC;
    private java.lang.String CODE_TAX;
    private java.math.BigDecimal BONUS1_HOL;
    private java.lang.String NOTE_REST;
    private java.math.BigDecimal STT;
    private java.math.BigDecimal TSAL_PAYTAX;
    private java.lang.String DEPSN_BHYT;
    private java.math.BigDecimal BSALY1;
    private java.math.BigDecimal BSALY2;
    private java.math.BigDecimal BONUS8;	//CHI CHUA RIENG 1 BONUS8, T062011
    private java.math.BigDecimal BONUS10;	//T102012
 
	
	/**
     * @return EMPSN 
     */
    @Id
    @Length(max = 8)
    @Column(name = "EMPSN")
    @Config(key = "ATT200000.EMPSN")
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
    @Config(key = "ATT200000.EMPNA")
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
    @Config(key = "ATT200000.DEPSN")
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
    @Config(key = "ATT200000.POSSN")
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
    @Config(key = "ATT200000.HIRED")
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
    @Config(key = "ATT200000.OFFD")
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
    @Config(key = "ATT200000.CLASS")
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
    @Config(key = "ATT200000.BSALY")
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
    @Config(key = "ATT200000.COMBSALY")
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
    @Config(key = "ATT200000.BSALY_N")
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
     * @return BONUS1 
     */
    @Column(name = "BONUS1")
    @Config(key = "ATT200000.BONUS1")
    public java.math.BigDecimal getBONUS1() {
        return BONUS1;
    }
    /**
     * @param BONUS1 
     */
    public void setBONUS1(java.math.BigDecimal BONUS1) {
        this.BONUS1 = BONUS1;
    }
    /**
     * @return BONUS2 
     */
    @Column(name = "BONUS2")
    @Config(key = "ATT200000.BONUS2")
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
     * @return BONUS3 
     */
    @Column(name = "BONUS3")
    @Config(key = "ATT200000.BONUS3")
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
     * 取得HIEN TAI=BONUS4+BONUS8, T062011
     * @return BONUS4 HIEN TAI=BONUS4+BONUS8, T062011
     */
    @Column(name = "BONUS4")
    @Config(key = "ATT200000.BONUS4")
    public java.math.BigDecimal getBONUS4() {
        return BONUS4;
    }
    /**
     * 設定HIEN TAI=BONUS4+BONUS8, T062011
     * @param BONUS4 HIEN TAI=BONUS4+BONUS8, T062011
     */
    public void setBONUS4(java.math.BigDecimal BONUS4) {
        this.BONUS4 = BONUS4;
    }
    /**
     * @return BONUS5 
     */
    @Column(name = "BONUS5")
    @Config(key = "ATT200000.BONUS5")
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
    @Config(key = "ATT200000.BONUS6")
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
    @Config(key = "ATT200000.BONUS7")
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
    @Config(key = "ATT200000.BONUS_ACN")
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
    @Config(key = "ATT200000.JOININSU")
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
    @Config(key = "ATT200000.JOINLUM")
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
    @Config(key = "ATT200000.BORM")
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
    @Config(key = "ATT200000.YLBX")
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
    @Config(key = "ATT200000.PAYTAX")
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
    @Config(key = "ATT200000.KQT")
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
    @Config(key = "ATT200000.DUCLS_S")
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
    @Config(key = "ATT200000.NUCLS_S")
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
    @Config(key = "ATT200000.REST_PAY_S")
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
    @Config(key = "ATT200000.ADDCLS1_S")
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
    @Config(key = "ATT200000.ADDCLS2_S")
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
    @Config(key = "ATT200000.NADDCLS_S")
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
    @Config(key = "ATT200000.ADDHOL_S")
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
    @Config(key = "ATT200000.ADDHOLN_S")
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
    @Config(key = "ATT200000.LATE")
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
    @Config(key = "ATT200000.REST")
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
    @Config(key = "ATT200000.NWHOUR")
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
    @Config(key = "ATT200000.DUCLS")
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
    @Config(key = "ATT200000.NUCLS")
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
    @Config(key = "ATT200000.REST_PAY")
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
    @Config(key = "ATT200000.TDAY")
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
    @Config(key = "ATT200000.ADDCLS1")
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
    @Config(key = "ATT200000.ADDCLS2")
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
    @Config(key = "ATT200000.NADDCLS")
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
    @Config(key = "ATT200000.ADDHOL")
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
    @Config(key = "ATT200000.ADDHOLN")
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
    @Length(max = 25)
    @Column(name = "NOTE")
    @Config(key = "ATT200000.NOTE")
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
    @Config(key = "ATT200000.TADDCLS")
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
    @Config(key = "ATT200000.TADDCLS2")
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
    @Config(key = "ATT200000.TBASIC")
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
    @Config(key = "ATT200000.TADDS")
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
    @Config(key = "ATT200000.TBONUS")
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
    @Config(key = "ATT200000.TINCOME")
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
    @Config(key = "ATT200000.TKQ")
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
    @Config(key = "ATT200000.TS")
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
    @Config(key = "ATT200000.REST_SICK")
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
    @Config(key = "ATT200000.TYADDCLS")
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
    @Config(key = "ATT200000.BONUS9")
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
    @Config(key = "ATT200000.TS1")
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
    @Config(key = "ATT200000.OTHER")
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
    @Config(key = "ATT200000.PHEP_NS")
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
    @Config(key = "ATT200000.SIGN")
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
    @Config(key = "ATT200000.BAC")
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
    @Config(key = "ATT200000.TADDS_NOTAX")
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
    @Config(key = "ATT200000.BH_TNGHIEP")
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
    @Config(key = "ATT200000.PAYTAX1")
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
    @Config(key = "ATT200000.TY_PAYTAX1")
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
    @Config(key = "ATT200000.QT_PAYTAX1")
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
     * @return BONUS2_GOC 
     */
    @Column(name = "BONUS2_GOC")
    @Config(key = "ATT200000.BONUS2_GOC")
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
     * @return CODE_TAX 
     */
    @Length(max = 10)
    @Column(name = "CODE_TAX")
    @Config(key = "ATT200000.CODE_TAX")
    public java.lang.String getCODE_TAX() {
        return CODE_TAX;
    }
    /**
     * @param CODE_TAX 
     */
    public void setCODE_TAX(java.lang.String CODE_TAX) {
        this.CODE_TAX = CODE_TAX;
    }
    /**
     * @return BONUS1_HOL 
     */
    @Column(name = "BONUS1_HOL")
    @Config(key = "ATT200000.BONUS1_HOL")
    public java.math.BigDecimal getBONUS1_HOL() {
        return BONUS1_HOL;
    }
    /**
     * @param BONUS1_HOL 
     */
    public void setBONUS1_HOL(java.math.BigDecimal BONUS1_HOL) {
        this.BONUS1_HOL = BONUS1_HOL;
    }
    /**
     * @return NOTE_REST 
     */
    @Length(max = 500)
    @Column(name = "NOTE_REST")
    @Config(key = "ATT200000.NOTE_REST")
    public java.lang.String getNOTE_REST() {
        return NOTE_REST;
    }
    /**
     * @param NOTE_REST 
     */
    public void setNOTE_REST(java.lang.String NOTE_REST) {
        this.NOTE_REST = NOTE_REST;
    }
    /**
     * @return STT 
     */
    @Column(name = "STT")
    @Config(key = "ATT200000.STT")
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
     * @return TSAL_PAYTAX 
     */
    @Column(name = "TSAL_PAYTAX")
    @Config(key = "ATT200000.TSAL_PAYTAX")
    public java.math.BigDecimal getTSAL_PAYTAX() {
        return TSAL_PAYTAX;
    }
    /**
     * @param TSAL_PAYTAX 
     */
    public void setTSAL_PAYTAX(java.math.BigDecimal TSAL_PAYTAX) {
        this.TSAL_PAYTAX = TSAL_PAYTAX;
    }
    /**
     * @return DEPSN_BHYT 
     */
    @Length(max = 5)
    @Column(name = "DEPSN_BHYT")
    @Config(key = "ATT200000.DEPSN_BHYT")
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
     * @return BSALY1 
     */
    @Column(name = "BSALY1")
    @Config(key = "ATT200000.BSALY1")
    public java.math.BigDecimal getBSALY1() {
        return BSALY1;
    }
    /**
     * @param BSALY1 
     */
    public void setBSALY1(java.math.BigDecimal BSALY1) {
        this.BSALY1 = BSALY1;
    }
    /**
     * @return BSALY2 
     */
    @Column(name = "BSALY2")
    @Config(key = "ATT200000.BSALY2")
    public java.math.BigDecimal getBSALY2() {
        return BSALY2;
    }
    /**
     * @param BSALY2 
     */
    public void setBSALY2(java.math.BigDecimal BSALY2) {
        this.BSALY2 = BSALY2;
    }
    /**
     * 取得CHI CHUA RIENG 1 BONUS8, T062011
     * @return BONUS8 CHI CHUA RIENG 1 BONUS8, T062011
     */
    @Column(name = "BONUS8")
    @Config(key = "ATT200000.BONUS8")
    public java.math.BigDecimal getBONUS8() {
        return BONUS8;
    }
    /**
     * 設定CHI CHUA RIENG 1 BONUS8, T062011
     * @param BONUS8 CHI CHUA RIENG 1 BONUS8, T062011
     */
    public void setBONUS8(java.math.BigDecimal BONUS8) {
        this.BONUS8 = BONUS8;
    }
    
    @Column(name = "BONUS10")
    @Config(key = "ATT200000.BONUS10")
    public java.math.BigDecimal getBONUS10() {
		return BONUS10;
	}
    
	public void setBONUS10(java.math.BigDecimal bONUS10) {
		BONUS10 = bONUS10;
	}
}
