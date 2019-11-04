package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.ATTENDANTDB_QUITPk;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import org.hibernate.validator.Length;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@IdClass(ATTENDANTDB_QUITPk.class)
@Entity
@Table(name = "ATTENDANTDB_QUIT")
public class ATTENDANTDB_QUIT {
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
    private java.math.BigDecimal BONUS4;
    private java.math.BigDecimal BONUS5;
    private java.math.BigDecimal BONUS6;
    private java.math.BigDecimal BONUS_ACN;
    private java.math.BigDecimal JOININSU;
    private java.math.BigDecimal JOINLUM;
    private java.math.BigDecimal BORM;
    private java.math.BigDecimal YLBX;
    private java.math.BigDecimal PAYTAX;
    private java.math.BigDecimal KQT;
    private java.math.BigDecimal DUCLS;
    private java.math.BigDecimal NUCLS;
    private java.math.BigDecimal ADDCLS1;
    private java.math.BigDecimal ADDCLS2;
    private java.math.BigDecimal NADDCLS;
    private java.math.BigDecimal ACN;
    private java.math.BigDecimal ADDHOL;
    private java.math.BigDecimal REST;
    private java.math.BigDecimal REST_PAY;
    private java.math.BigDecimal NWHOUR;
    private java.math.BigDecimal LATE;
    private java.util.Date INSUR_DATE;
    private java.util.Date YLBX_DATE;
    private java.lang.String TIME_IN;
    private java.lang.String TIME_OUT;
    private java.lang.String FACT;
    private java.lang.String ACC;
    private java.math.BigDecimal ADDHOLN;
    private java.math.BigDecimal SIGN_TIME;
    private java.lang.String NOTE;
    private java.math.BigDecimal ACNM;
    private java.math.BigDecimal REST_SICK;
    private java.math.BigDecimal OTHER;
    private java.lang.String EMPCN;
    private java.math.BigDecimal BONUS7;
    private java.math.BigDecimal BONUS8;
    private java.math.BigDecimal PHEP_NS;
    private java.math.BigDecimal TEMP;
    private java.math.BigDecimal TEMP1;
    private java.math.BigDecimal TEMP2;
    private java.math.BigDecimal BONUS1_HOL;
    private java.math.BigDecimal BONUS9;
    private java.math.BigDecimal BAC;
    private java.math.BigDecimal BH_TNGHIEP;
    private java.math.BigDecimal QT_PAYTAX1;
    private java.math.BigDecimal MM_BONUS;
    private java.math.BigDecimal DAY_BEFORE;
    private java.math.BigDecimal REST_REMAIN;
    private java.math.BigDecimal BSAL_AVG;
    private java.util.Date DATE_OFF;
    private java.math.BigDecimal BONUS2_AVG;
    private java.math.BigDecimal STNV;
    private java.math.BigDecimal BH_QUY;
    private java.math.BigDecimal BU_BHYT;
    private java.math.BigDecimal THU_BHYT;
    private java.math.BigDecimal BUTHU_BHYT;
    private java.util.Date DOT_TV;
    private java.math.BigDecimal MM_DENBU;
    private java.lang.String DOT_TV1;
    private java.lang.String DEPT_KT;
    private java.lang.String NOTE_BH;
    private java.math.BigDecimal ADDCLS1_O;
    private java.math.BigDecimal NADDCLS_O;
    private java.math.BigDecimal ADDHOL_O;
    private java.math.BigDecimal ADDHOLN_O;
    private java.math.BigDecimal ADDHOLN_O_S;
    private java.math.BigDecimal ADDHOL_O_S;
    private java.math.BigDecimal NADDCLS_O_S;
    private java.math.BigDecimal ADDCLS1_O_S;
    private java.math.BigDecimal ACNM_O;
    private java.lang.String DEPSN_BHYT;
    private java.math.BigDecimal BSALY_LAST_MONTH;	//LAY LUONG THANG TRUOC DE KHAU TRU BH VA NGHI TRUOC THOI HAN NEU CO_ AD DOT TV 14/11/2011
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "ATTENDANTDB_QUIT.EMPSN")
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
    @Length(max = 80)
    @Column(name = "EMPNA")
    @Config(key = "ATTENDANTDB_QUIT.EMPNA")
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
    @Config(key = "ATTENDANTDB_QUIT.DEPSN")
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
    @Length(max = 25)
    @Column(name = "POSSN")
    @Config(key = "ATTENDANTDB_QUIT.POSSN")
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
    @Config(key = "ATTENDANTDB_QUIT.HIRED")
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
    @Config(key = "ATTENDANTDB_QUIT.OFFD")
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
    @Config(key = "ATTENDANTDB_QUIT.CLASS")
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
    @NotBlank
    @Column(name = "BSALY")
    @Config(key = "ATTENDANTDB_QUIT.BSALY")
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
    @NotBlank
    @Column(name = "COMBSALY")
    @Config(key = "ATTENDANTDB_QUIT.COMBSALY")
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
    @Config(key = "ATTENDANTDB_QUIT.BSALY_N")
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
    @Config(key = "ATTENDANTDB_QUIT.BONUS1")
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
    @Config(key = "ATTENDANTDB_QUIT.BONUS2")
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
    @Config(key = "ATTENDANTDB_QUIT.BONUS3")
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
    @Config(key = "ATTENDANTDB_QUIT.BONUS4")
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
    @Config(key = "ATTENDANTDB_QUIT.BONUS5")
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
    @Config(key = "ATTENDANTDB_QUIT.BONUS6")
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
     * @return BONUS_ACN 
     */
    @Column(name = "BONUS_ACN")
    @Config(key = "ATTENDANTDB_QUIT.BONUS_ACN")
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
    @Config(key = "ATTENDANTDB_QUIT.JOININSU")
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
    @Config(key = "ATTENDANTDB_QUIT.JOINLUM")
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
    @Config(key = "ATTENDANTDB_QUIT.BORM")
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
    @NotBlank
    @Column(name = "YLBX")
    @Config(key = "ATTENDANTDB_QUIT.YLBX")
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
    @Config(key = "ATTENDANTDB_QUIT.PAYTAX")
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
    @Config(key = "ATTENDANTDB_QUIT.KQT")
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
     * @return DUCLS 
     */
    @Column(name = "DUCLS")
    @Config(key = "ATTENDANTDB_QUIT.DUCLS")
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
    @Config(key = "ATTENDANTDB_QUIT.NUCLS")
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
     * @return ADDCLS1 
     */
    @Column(name = "ADDCLS1")
    @Config(key = "ATTENDANTDB_QUIT.ADDCLS1")
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
    @Config(key = "ATTENDANTDB_QUIT.ADDCLS2")
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
    @Config(key = "ATTENDANTDB_QUIT.NADDCLS")
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
     * @return ACN 
     */
    @Column(name = "ACN")
    @Config(key = "ATTENDANTDB_QUIT.ACN")
    public java.math.BigDecimal getACN() {
        return ACN;
    }
    /**
     * @param ACN 
     */
    public void setACN(java.math.BigDecimal ACN) {
        this.ACN = ACN;
    }
    /**
     * @return ADDHOL 
     */
    @Column(name = "ADDHOL")
    @Config(key = "ATTENDANTDB_QUIT.ADDHOL")
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
     * @return REST 
     */
    @Column(name = "REST")
    @Config(key = "ATTENDANTDB_QUIT.REST")
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
     * @return REST_PAY 
     */
    @Column(name = "REST_PAY")
    @Config(key = "ATTENDANTDB_QUIT.REST_PAY")
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
     * @return NWHOUR 
     */
    @Column(name = "NWHOUR")
    @Config(key = "ATTENDANTDB_QUIT.NWHOUR")
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
     * @return LATE 
     */
    @Column(name = "LATE")
    @Config(key = "ATTENDANTDB_QUIT.LATE")
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
     * @return INSUR_DATE 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "INSUR_DATE")
    @Config(key = "ATTENDANTDB_QUIT.INSUR_DATE")
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
    @Config(key = "ATTENDANTDB_QUIT.YLBX_DATE")
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
    @Config(key = "ATTENDANTDB_QUIT.TIME_IN")
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
    @Config(key = "ATTENDANTDB_QUIT.TIME_OUT")
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
    @Config(key = "ATTENDANTDB_QUIT.FACT")
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
    @Config(key = "ATTENDANTDB_QUIT.ACC")
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
    @Config(key = "ATTENDANTDB_QUIT.ADDHOLN")
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
     * @return SIGN_TIME 
     */
    @Column(name = "SIGN_TIME")
    @Config(key = "ATTENDANTDB_QUIT.SIGN_TIME")
    public java.math.BigDecimal getSIGN_TIME() {
        return SIGN_TIME;
    }
    /**
     * @param SIGN_TIME 
     */
    public void setSIGN_TIME(java.math.BigDecimal SIGN_TIME) {
        this.SIGN_TIME = SIGN_TIME;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 25)
    @Column(name = "NOTE")
    @Config(key = "ATTENDANTDB_QUIT.NOTE")
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
    @Config(key = "ATTENDANTDB_QUIT.ACNM")
    public java.math.BigDecimal getACNM() {
        return ACNM;
    }
    /**
     * @param ACNM 
     */
    public void setACNM(java.math.BigDecimal ACNM) {
        this.ACNM = ACNM;
    }
    /**
     * @return REST_SICK 
     */
    @Column(name = "REST_SICK")
    @Config(key = "ATTENDANTDB_QUIT.REST_SICK")
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
     * @return OTHER 
     */
    @Column(name = "OTHER")
    @Config(key = "ATTENDANTDB_QUIT.OTHER")
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
     * @return EMPCN 
     */
    @Length(max = 10)
    @Column(name = "EMPCN")
    @Config(key = "ATTENDANTDB_QUIT.EMPCN")
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
    @Config(key = "ATTENDANTDB_QUIT.BONUS7")
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
     * @return BONUS8 
     */
    @Column(name = "BONUS8")
    @Config(key = "ATTENDANTDB_QUIT.BONUS8")
    public java.math.BigDecimal getBONUS8() {
        return BONUS8;
    }
    /**
     * @param BONUS8 
     */
    public void setBONUS8(java.math.BigDecimal BONUS8) {
        this.BONUS8 = BONUS8;
    }
    /**
     * @return PHEP_NS 
     */
    @Column(name = "PHEP_NS")
    @Config(key = "ATTENDANTDB_QUIT.PHEP_NS")
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
     * @return TEMP 
     */
    @Column(name = "TEMP")
    @Config(key = "ATTENDANTDB_QUIT.TEMP")
    public java.math.BigDecimal getTEMP() {
        return TEMP;
    }
    /**
     * @param TEMP 
     */
    public void setTEMP(java.math.BigDecimal TEMP) {
        this.TEMP = TEMP;
    }
    /**
     * @return TEMP1 
     */
    @Column(name = "TEMP1")
    @Config(key = "ATTENDANTDB_QUIT.TEMP1")
    public java.math.BigDecimal getTEMP1() {
        return TEMP1;
    }
    /**
     * @param TEMP1 
     */
    public void setTEMP1(java.math.BigDecimal TEMP1) {
        this.TEMP1 = TEMP1;
    }
    /**
     * @return TEMP2 
     */
    @Column(name = "TEMP2")
    @Config(key = "ATTENDANTDB_QUIT.TEMP2")
    public java.math.BigDecimal getTEMP2() {
        return TEMP2;
    }
    /**
     * @param TEMP2 
     */
    public void setTEMP2(java.math.BigDecimal TEMP2) {
        this.TEMP2 = TEMP2;
    }
    /**
     * @return BONUS1_HOL 
     */
    @Column(name = "BONUS1_HOL")
    @Config(key = "ATTENDANTDB_QUIT.BONUS1_HOL")
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
     * @return BONUS9 
     */
    @Column(name = "BONUS9")
    @Config(key = "ATTENDANTDB_QUIT.BONUS9")
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
     * @return BAC 
     */
    @Column(name = "BAC")
    @Config(key = "ATTENDANTDB_QUIT.BAC")
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
     * @return BH_TNGHIEP 
     */
    @Column(name = "BH_TNGHIEP")
    @Config(key = "ATTENDANTDB_QUIT.BH_TNGHIEP")
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
     * @return QT_PAYTAX1 
     */
    @Column(name = "QT_PAYTAX1")
    @Config(key = "ATTENDANTDB_QUIT.QT_PAYTAX1")
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
     * @return MM_BONUS 
     */
    @Column(name = "M_BONUS")
    @Config(key = "ATTENDANTDB_QUIT.M_BONUS")
    public java.math.BigDecimal getMM_BONUS() {
        return MM_BONUS;
    }
    /**
     * @param MM_BONUS 
     */
    public void setMM_BONUS(java.math.BigDecimal MM_BONUS) {
        this.MM_BONUS = MM_BONUS;
    }
    /**
     * @return DAY_BEFORE 
     */
    @Column(name = "DAY_BEFORE")
    @Config(key = "ATTENDANTDB_QUIT.DAY_BEFORE")
    public java.math.BigDecimal getDAY_BEFORE() {
        return DAY_BEFORE;
    }
    /**
     * @param DAY_BEFORE 
     */
    public void setDAY_BEFORE(java.math.BigDecimal DAY_BEFORE) {
        this.DAY_BEFORE = DAY_BEFORE;
    }
    /**
     * @return REST_REMAIN 
     */
    @Column(name = "REST_REMAIN")
    @Config(key = "ATTENDANTDB_QUIT.REST_REMAIN")
    public java.math.BigDecimal getREST_REMAIN() {
        return REST_REMAIN;
    }
    /**
     * @param REST_REMAIN 
     */
    public void setREST_REMAIN(java.math.BigDecimal REST_REMAIN) {
        this.REST_REMAIN = REST_REMAIN;
    }
    /**
     * @return BSAL_AVG 
     */
    @NotBlank
    @Column(name = "BSAL_AVG")
    @Config(key = "ATTENDANTDB_QUIT.BSAL_AVG")
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
     * @return DATE_OFF 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OFF")
    @Config(key = "ATTENDANTDB_QUIT.DATE_OFF")
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
    @NotBlank
    @Column(name = "BONUS2_AVG")
    @Config(key = "ATTENDANTDB_QUIT.BONUS2_AVG")
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
    @Config(key = "ATTENDANTDB_QUIT.STNV")
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
    @Config(key = "ATTENDANTDB_QUIT.BH_QUY")
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
    @Config(key = "ATTENDANTDB_QUIT.BU_BHYT")
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
    @Config(key = "ATTENDANTDB_QUIT.THU_BHYT")
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
     * @return BUTHU_BHYT 
     */
    @Column(name = "BUTHU_BHYT")
    @Config(key = "ATTENDANTDB_QUIT.BUTHU_BHYT")
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
     * @return DOT_TV 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DOT_TV")
    @Config(key = "ATTENDANTDB_QUIT.DOT_TV")
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
    @Config(key = "ATTENDANTDB_QUIT.M_DENBU")
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
     * @return DOT_TV1 
     */
    @Length(max = 10)
    @Column(name = "DOT_TV1")
    @Config(key = "ATTENDANTDB_QUIT.DOT_TV1")
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
    @Config(key = "ATTENDANTDB_QUIT.DEPT_KT")
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
    @Config(key = "ATTENDANTDB_QUIT.NOTE_BH")
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
    @Config(key = "ATTENDANTDB_QUIT.ADDCLS1_O")
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
    @Config(key = "ATTENDANTDB_QUIT.NADDCLS_O")
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
    @Config(key = "ATTENDANTDB_QUIT.ADDHOL_O")
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
    @Config(key = "ATTENDANTDB_QUIT.ADDHOLN_O")
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
     * @return ADDHOLN_O_S 
     */
    @Column(name = "ADDHOLN_O_S")
    @Config(key = "ATTENDANTDB_QUIT.ADDHOLN_O_S")
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
     * @return ADDHOL_O_S 
     */
    @Column(name = "ADDHOL_O_S")
    @Config(key = "ATTENDANTDB_QUIT.ADDHOL_O_S")
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
     * @return NADDCLS_O_S 
     */
    @Column(name = "NADDCLS_O_S")
    @Config(key = "ATTENDANTDB_QUIT.NADDCLS_O_S")
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
     * @return ADDCLS1_O_S 
     */
    @Column(name = "ADDCLS1_O_S")
    @Config(key = "ATTENDANTDB_QUIT.ADDCLS1_O_S")
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
     * @return ACNM_O 
     */
    @Column(name = "ACNM_O")
    @Config(key = "ATTENDANTDB_QUIT.ACNM_O")
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
    @Config(key = "ATTENDANTDB_QUIT.DEPSN_BHYT")
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
     * 取得LAY LUONG THANG TRUOC DE KHAU TRU BH VA NGHI TRUOC THOI HAN NEU CO_ AD DOT TV 14/11/2011
     * @return BSALY_LAST_MONTH LAY LUONG THANG TRUOC DE KHAU TRU BH VA NGHI TRUOC THOI HAN NEU CO_ AD DOT TV 14/11/2011
     */
    @Column(name = "BSALY_LAST_MONTH")
    @Config(key = "ATTENDANTDB_QUIT.BSALY_LAST_MONTH")
    public java.math.BigDecimal getBSALY_LAST_MONTH() {
        return BSALY_LAST_MONTH;
    }
    /**
     * 設定LAY LUONG THANG TRUOC DE KHAU TRU BH VA NGHI TRUOC THOI HAN NEU CO_ AD DOT TV 14/11/2011
     * @param BSALY_LAST_MONTH LAY LUONG THANG TRUOC DE KHAU TRU BH VA NGHI TRUOC THOI HAN NEU CO_ AD DOT TV 14/11/2011
     */
    public void setBSALY_LAST_MONTH(java.math.BigDecimal BSALY_LAST_MONTH) {
        this.BSALY_LAST_MONTH = BSALY_LAST_MONTH;
    }
}
