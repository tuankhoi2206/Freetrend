package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.ATTQUIT200900Pk;
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
@IdClass(ATTQUIT200900Pk.class)
@Entity
@Table(name = "ATTQUIT200900")
public class ATTQUIT200900 {
    private java.lang.String EMPSN;
    private java.lang.String EMPNA;
    private java.lang.String DEPSN;
    private java.lang.String POSSN;
    private java.util.Date HIRED;
    private java.util.Date OFFD;
    private java.lang.String CLASS;	//TRUOC GIO KO SD, PS T112010 CHO BHIEM. BV LA BO VIEC NULL LA THOI VIEC VA CO XUAT RA BANG TONG, BV KO XUAT
    private java.math.BigDecimal BSALY;	//LUONG CB GOC CHUA TINH THEO TY LE NGAY CONG
    private java.math.BigDecimal COMBSALY;
    private java.math.BigDecimal BSALY_N;
    private java.math.BigDecimal BONUS1;
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
    private java.math.BigDecimal TADDCLS;	//TS TCA NGAY+DEM, BAO GOM TCA NGOAI
    private java.math.BigDecimal TADDCLS2;
    private java.math.BigDecimal TBASIC;
    private java.math.BigDecimal TADDS;	//TS TIEN TCA, BAO GOM CA TCA NGOAI
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
    private java.math.BigDecimal PN_CONLAI;	//SO NGAY PN CON LAI
    private java.math.BigDecimal PN_CONLAI_S;	//THANH TIEN PN CON LAI
    private java.math.BigDecimal MM_TROCAP;
    private java.math.BigDecimal MM_TROCAP_S;
    private java.math.BigDecimal DD_NGHITRUOC;
    private java.math.BigDecimal DD_NGHITRUOC_S;
    private java.math.BigDecimal TT_TCTVIEC;
    private java.util.Date DATE_OFF;	//NGAY NGHI VIEC
    private java.math.BigDecimal BSAL_AVG;	//LUONG CB BQ 6 THANG
    private java.math.BigDecimal TS_HIENTAI;	//TONG LUONG CUA THANG HIEN TAI
    private java.math.BigDecimal BONUS2_AVG;	//PCCVU BQ 6 THANG
    private java.math.BigDecimal STNV;	//TRU 2500 TIEN SO TAY NHAN VIEN
    private java.math.BigDecimal BH_QUY;	//MUA BH VAO QUY N TRONG NAM
    private java.math.BigDecimal BU_BHYT;	//SO AM -, BU BHYT , NEU DA MUA 3 THANG MA NGHI VIEC VA KO DUNG DEN NUA, TRA LAI THE BHYT CHO CO QUAN BHYT
    private java.math.BigDecimal THU_BHYT;	//SO DUONG +, THU BHYT, DA MUA BHYT MA CHUA THU TIEN. VD NHU NV NGHI SAN MINH VAN MUA BHYT NHUNG CHUA CO LUONG DE TRU, SAU NGHI SAN NV NAY NGHI LUON MINH SE TRU NHUNG THANG DA MU DO
    private java.math.BigDecimal TC_BSALY;	//TRO CAP LUONG CB=BSALY_AVG*M_TROCAP
    private java.math.BigDecimal TC_BONUS2;	//TRO CAP PCCV=BONUS2_AVG*M_TROCAP
    private java.math.BigDecimal BUTHU_BHYT;	//GOM CHUNG COT
    private java.math.BigDecimal BONUS2_GOC;	//TIEN PCCVU CUA CNV , SO TIEN GOC CHUA TINH THEO TY LE NGAY CONG
    private java.util.Date DOT_TV;	//PHAT LUONG THOI VIEC THEO TUNG DOT, LA THU 2 MOI TUAN
    private java.math.BigDecimal MM_DENBU;
    private java.math.BigDecimal MM_DENBU_S;
    private java.lang.String DOT_TV1;	//PHAT LUONG THOI VIEC THEO TUNG DOT, THU 2 MOI TUAN
    private java.math.BigDecimal TS_HIENTAI1;	//THUC NHAN AO TRUOC KHI TRU D_NGHITRUOC_S
    private java.math.BigDecimal TT_TCTVIEC1;
    private java.math.BigDecimal TS_HIENTAI2;	//TONG LUONG THANG THOI VIEC LAM TRON 500, DE XUAT RA KY NHAN
    private java.lang.String DEPT_KT;	//KTOAN CHIA XUONG THEO TRUC TIPE OR GIAN TIEP
    private java.math.BigDecimal STT;	//STT cua CNV do tren bang TONG KY NHAN VA PLN THOI VIEC PHAI LA 1. BEGIN TV 20/09/2010
    private java.math.BigDecimal ADDCLS1_O;	//TCA NGAY, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
    private java.math.BigDecimal NADDCLS_O;	//TCA DEM, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
    private java.math.BigDecimal ADDHOL_O;	//TCA CHU NHAT, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
    private java.math.BigDecimal ADDHOLN_O;	//TCA LE, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
    private java.math.BigDecimal ADDCLS1_O_S;	//TIEN TCA NGAY, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
    private java.math.BigDecimal NADDCLS_O_S;	//TIEN TCA DEM, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
    private java.math.BigDecimal ADDHOL_O_S;	//TIEN TCA CHU NHAT, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
    private java.math.BigDecimal ADDHOLN_O_S;	//TIEN TCA LE, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
    private java.math.BigDecimal ACNM_O;	//SO DEM TIEN COM TCA TU 1-1.5/ NGAY, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
    private java.lang.String DEPSN_BHYT;
    private java.lang.String NOTE_NV;
    private java.math.BigDecimal BONUS8;	//PC LAM 8H + TIEN XANG
    private java.math.BigDecimal BSALY_LAST_MONTH;	//LAY LUONG THANG TRUOC DE KHAU TRU BH VA NGHI TRUOC THOI HAN NEU CO_ AD DOT TV 14/11/2011
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name="EMPSN")
    @Config(key = "ATTQUIT200900.EMPSN")
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
    @Config(key = "ATTQUIT200900.EMPNA")
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
    @Config(key = "ATTQUIT200900.DEPSN")
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
    @Config(key = "ATTQUIT200900.POSSN")
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
    @Config(key = "ATTQUIT200900.HIRED")
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
    @Config(key = "ATTQUIT200900.OFFD")
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
     * 取得TRUOC GIO KO SD, PS T112010 CHO BHIEM. BV LA BO VIEC NULL LA THOI VIEC VA CO XUAT RA BANG TONG, BV KO XUAT
     * @return CLASS TRUOC GIO KO SD, PS T112010 CHO BHIEM. BV LA BO VIEC NULL LA THOI VIEC VA CO XUAT RA BANG TONG, BV KO XUAT
     */
    @Length(max = 2)
    @Column(name = "CLASS")
    @Config(key = "ATTQUIT200900.CLASS")
    public java.lang.String getCLASS() {
        return CLASS;
    }
    /**
     * 設定TRUOC GIO KO SD, PS T112010 CHO BHIEM. BV LA BO VIEC NULL LA THOI VIEC VA CO XUAT RA BANG TONG, BV KO XUAT
     * @param CLASS TRUOC GIO KO SD, PS T112010 CHO BHIEM. BV LA BO VIEC NULL LA THOI VIEC VA CO XUAT RA BANG TONG, BV KO XUAT
     */
    public void setCLASS(java.lang.String CLASS) {
        this.CLASS = CLASS;
    }
    /**
     * 取得LUONG CB GOC CHUA TINH THEO TY LE NGAY CONG
     * @return BSALY LUONG CB GOC CHUA TINH THEO TY LE NGAY CONG
     */
    @Column(name = "BSALY")
    @Config(key = "ATTQUIT200900.BSALY")
    public java.math.BigDecimal getBSALY() {
        return BSALY;
    }
    /**
     * 設定LUONG CB GOC CHUA TINH THEO TY LE NGAY CONG
     * @param BSALY LUONG CB GOC CHUA TINH THEO TY LE NGAY CONG
     */
    public void setBSALY(java.math.BigDecimal BSALY) {
        this.BSALY = BSALY;
    }
    /**
     * @return COMBSALY 
     */
    @Column(name = "COMBSALY")
    @Config(key = "ATTQUIT200900.COMBSALY")
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
    @Config(key = "ATTQUIT200900.BSALY_N")
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
    @Config(key = "ATTQUIT200900.BONUS1")
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
    @Config(key = "ATTQUIT200900.BONUS2")
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
    @Config(key = "ATTQUIT200900.BONUS3")
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
    @Config(key = "ATTQUIT200900.BONUS4")
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
    @Config(key = "ATTQUIT200900.BONUS5")
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
    @Config(key = "ATTQUIT200900.BONUS6")
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
    @Config(key = "ATTQUIT200900.BONUS7")
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
    @Config(key = "ATTQUIT200900.BONUS_ACN")
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
    @Config(key = "ATTQUIT200900.JOININSU")
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
    @Config(key = "ATTQUIT200900.JOINLUM")
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
    @Config(key = "ATTQUIT200900.BORM")
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
    @Config(key = "ATTQUIT200900.YLBX")
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
    @Config(key = "ATTQUIT200900.PAYTAX")
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
    @Config(key = "ATTQUIT200900.KQT")
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
    @Config(key = "ATTQUIT200900.DUCLS_S")
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
    @Config(key = "ATTQUIT200900.NUCLS_S")
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
    @Config(key = "ATTQUIT200900.REST_PAY_S")
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
    @Config(key = "ATTQUIT200900.ADDCLS1_S")
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
    @Config(key = "ATTQUIT200900.ADDCLS2_S")
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
    @Config(key = "ATTQUIT200900.NADDCLS_S")
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
    @Config(key = "ATTQUIT200900.ADDHOL_S")
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
    @Config(key = "ATTQUIT200900.ADDHOLN_S")
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
    @Config(key = "ATTQUIT200900.LATE")
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
    @Config(key = "ATTQUIT200900.REST")
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
    @Config(key = "ATTQUIT200900.NWHOUR")
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
    @Config(key = "ATTQUIT200900.DUCLS")
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
    @Config(key = "ATTQUIT200900.NUCLS")
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
    @Config(key = "ATTQUIT200900.REST_PAY")
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
    @Config(key = "ATTQUIT200900.TDAY")
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
    @Config(key = "ATTQUIT200900.ADDCLS1")
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
    @Config(key = "ATTQUIT200900.ADDCLS2")
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
    @Config(key = "ATTQUIT200900.NADDCLS")
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
    @Config(key = "ATTQUIT200900.ADDHOL")
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
    @Config(key = "ATTQUIT200900.ADDHOLN")
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
    @Config(key = "ATTQUIT200900.NOTE")
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
     * 取得TS TCA NGAY+DEM, BAO GOM TCA NGOAI
     * @return TADDCLS TS TCA NGAY+DEM, BAO GOM TCA NGOAI
     */
    @Column(name = "TADDCLS")
    @Config(key = "ATTQUIT200900.TADDCLS")
    public java.math.BigDecimal getTADDCLS() {
        return TADDCLS;
    }
    /**
     * 設定TS TCA NGAY+DEM, BAO GOM TCA NGOAI
     * @param TADDCLS TS TCA NGAY+DEM, BAO GOM TCA NGOAI
     */
    public void setTADDCLS(java.math.BigDecimal TADDCLS) {
        this.TADDCLS = TADDCLS;
    }
    /**
     * @return TADDCLS2 
     */
    @Column(name = "TADDCLS2")
    @Config(key = "ATTQUIT200900.TADDCLS2")
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
    @Config(key = "ATTQUIT200900.TBASIC")
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
     * 取得TS TIEN TCA, BAO GOM CA TCA NGOAI
     * @return TADDS TS TIEN TCA, BAO GOM CA TCA NGOAI
     */
    @Column(name = "TADDS")
    @Config(key = "ATTQUIT200900.TADDS")
    public java.math.BigDecimal getTADDS() {
        return TADDS;
    }
    /**
     * 設定TS TIEN TCA, BAO GOM CA TCA NGOAI
     * @param TADDS TS TIEN TCA, BAO GOM CA TCA NGOAI
     */
    public void setTADDS(java.math.BigDecimal TADDS) {
        this.TADDS = TADDS;
    }
    /**
     * @return TBONUS 
     */
    @Column(name = "TBONUS")
    @Config(key = "ATTQUIT200900.TBONUS")
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
    @Config(key = "ATTQUIT200900.TINCOME")
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
    @Config(key = "ATTQUIT200900.TKQ")
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
    @Config(key = "ATTQUIT200900.TS")
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
    @Config(key = "ATTQUIT200900.REST_SICK")
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
    @Config(key = "ATTQUIT200900.TYADDCLS")
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
    @Config(key = "ATTQUIT200900.BONUS9")
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
    @Config(key = "ATTQUIT200900.TS1")
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
    @Config(key = "ATTQUIT200900.OTHER")
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
    @Config(key = "ATTQUIT200900.PHEP_NS")
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
    @Config(key = "ATTQUIT200900.SIGN")
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
    @Config(key = "ATTQUIT200900.BAC")
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
    @Config(key = "ATTQUIT200900.TADDS_NOTAX")
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
    @Config(key = "ATTQUIT200900.BH_TNGHIEP")
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
    @Config(key = "ATTQUIT200900.PAYTAX1")
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
    @Config(key = "ATTQUIT200900.TY_PAYTAX1")
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
    @Config(key = "ATTQUIT200900.QT_PAYTAX1")
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
     * 取得SO NGAY PN CON LAI
     * @return PN_CONLAI SO NGAY PN CON LAI
     */
    @Column(name = "PN_CONLAI")
    @Config(key = "ATTQUIT200900.PN_CONLAI")
    public java.math.BigDecimal getPN_CONLAI() {
        return PN_CONLAI;
    }
    /**
     * 設定SO NGAY PN CON LAI
     * @param PN_CONLAI SO NGAY PN CON LAI
     */
    public void setPN_CONLAI(java.math.BigDecimal PN_CONLAI) {
        this.PN_CONLAI = PN_CONLAI;
    }
    /**
     * 取得THANH TIEN PN CON LAI
     * @return PN_CONLAI_S THANH TIEN PN CON LAI
     */
    @Column(name = "PN_CONLAI_S")
    @Config(key = "ATTQUIT200900.PN_CONLAI_S")
    public java.math.BigDecimal getPN_CONLAI_S() {
        return PN_CONLAI_S;
    }
    /**
     * 設定THANH TIEN PN CON LAI
     * @param PN_CONLAI_S THANH TIEN PN CON LAI
     */
    public void setPN_CONLAI_S(java.math.BigDecimal PN_CONLAI_S) {
        this.PN_CONLAI_S = PN_CONLAI_S;
    }
    /**
     * @return MM_TROCAP 
     */
    @Column(name = "M_TROCAP")
    @Config(key = "ATTQUIT200900.M_TROCAP")
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
    @Config(key = "ATTQUIT200900.M_TROCAP_S")
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
    @Config(key = "ATTQUIT200900.D_NGHITRUOC")
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
    @Config(key = "ATTQUIT200900.D_NGHITRUOC_S")
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
    @Config(key = "ATTQUIT200900.T_TCTVIEC")
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
     * 取得NGAY NGHI VIEC
     * @return DATE_OFF NGAY NGHI VIEC
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OFF")
    @Config(key = "ATTQUIT200900.DATE_OFF")
    public java.util.Date getDATE_OFF() {
        return DATE_OFF;
    }
    /**
     * 設定NGAY NGHI VIEC
     * @param DATE_OFF NGAY NGHI VIEC
     */
    public void setDATE_OFF(java.util.Date DATE_OFF) {
        this.DATE_OFF = DATE_OFF;
    }
    /**
     * 取得LUONG CB BQ 6 THANG
     * @return BSAL_AVG LUONG CB BQ 6 THANG
     */
    @Column(name = "BSAL_AVG")
    @Config(key = "ATTQUIT200900.BSAL_AVG")
    public java.math.BigDecimal getBSAL_AVG() {
        return BSAL_AVG;
    }
    /**
     * 設定LUONG CB BQ 6 THANG
     * @param BSAL_AVG LUONG CB BQ 6 THANG
     */
    public void setBSAL_AVG(java.math.BigDecimal BSAL_AVG) {
        this.BSAL_AVG = BSAL_AVG;
    }
    /**
     * 取得TONG LUONG CUA THANG HIEN TAI
     * @return TS_HIENTAI TONG LUONG CUA THANG HIEN TAI
     */
    @Column(name = "TS_HIENTAI")
    @Config(key = "ATTQUIT200900.TS_HIENTAI")
    public java.math.BigDecimal getTS_HIENTAI() {
        return TS_HIENTAI;
    }
    /**
     * 設定TONG LUONG CUA THANG HIEN TAI
     * @param TS_HIENTAI TONG LUONG CUA THANG HIEN TAI
     */
    public void setTS_HIENTAI(java.math.BigDecimal TS_HIENTAI) {
        this.TS_HIENTAI = TS_HIENTAI;
    }
    /**
     * 取得PCCVU BQ 6 THANG
     * @return BONUS2_AVG PCCVU BQ 6 THANG
     */
    @Column(name = "BONUS2_AVG")
    @Config(key = "ATTQUIT200900.BONUS2_AVG")
    public java.math.BigDecimal getBONUS2_AVG() {
        return BONUS2_AVG;
    }
    /**
     * 設定PCCVU BQ 6 THANG
     * @param BONUS2_AVG PCCVU BQ 6 THANG
     */
    public void setBONUS2_AVG(java.math.BigDecimal BONUS2_AVG) {
        this.BONUS2_AVG = BONUS2_AVG;
    }
    /**
     * 取得TRU 2500 TIEN SO TAY NHAN VIEN
     * @return STNV TRU 2500 TIEN SO TAY NHAN VIEN
     */
    @Column(name = "STNV")
    @Config(key = "ATTQUIT200900.STNV")
    public java.math.BigDecimal getSTNV() {
        return STNV;
    }
    /**
     * 設定TRU 2500 TIEN SO TAY NHAN VIEN
     * @param STNV TRU 2500 TIEN SO TAY NHAN VIEN
     */
    public void setSTNV(java.math.BigDecimal STNV) {
        this.STNV = STNV;
    }
    /**
     * 取得MUA BH VAO QUY N TRONG NAM
     * @return BH_QUY MUA BH VAO QUY N TRONG NAM
     */
    @Column(name = "BH_QUY")
    @Config(key = "ATTQUIT200900.BH_QUY")
    public java.math.BigDecimal getBH_QUY() {
        return BH_QUY;
    }
    /**
     * 設定MUA BH VAO QUY N TRONG NAM
     * @param BH_QUY MUA BH VAO QUY N TRONG NAM
     */
    public void setBH_QUY(java.math.BigDecimal BH_QUY) {
        this.BH_QUY = BH_QUY;
    }
    /**
     * 取得SO AM -, BU BHYT , NEU DA MUA 3 THANG MA NGHI VIEC VA KO DUNG DEN NUA, TRA LAI THE BHYT CHO CO QUAN BHYT
     * @return BU_BHYT SO AM -, BU BHYT , NEU DA MUA 3 THANG MA NGHI VIEC VA KO DUNG DEN NUA, TRA LAI THE BHYT CHO CO QUAN BHYT
     */
    @Column(name = "BU_BHYT")
    @Config(key = "ATTQUIT200900.BU_BHYT")
    public java.math.BigDecimal getBU_BHYT() {
        return BU_BHYT;
    }
    /**
     * 設定SO AM -, BU BHYT , NEU DA MUA 3 THANG MA NGHI VIEC VA KO DUNG DEN NUA, TRA LAI THE BHYT CHO CO QUAN BHYT
     * @param BU_BHYT SO AM -, BU BHYT , NEU DA MUA 3 THANG MA NGHI VIEC VA KO DUNG DEN NUA, TRA LAI THE BHYT CHO CO QUAN BHYT
     */
    public void setBU_BHYT(java.math.BigDecimal BU_BHYT) {
        this.BU_BHYT = BU_BHYT;
    }
    /**
     * 取得SO DUONG +, THU BHYT, DA MUA BHYT MA CHUA THU TIEN. VD NHU NV NGHI SAN MINH VAN MUA BHYT NHUNG CHUA CO LUONG DE TRU, SAU NGHI SAN NV NAY NGHI LUON MINH SE TRU NHUNG THANG DA MU DO
     * @return THU_BHYT SO DUONG +, THU BHYT, DA MUA BHYT MA CHUA THU TIEN. VD NHU NV NGHI SAN MINH VAN MUA BHYT NHUNG CHUA CO LUONG DE TRU, SAU NGHI SAN NV NAY NGHI LUON MINH SE TRU NHUNG THANG DA MU DO
     */
    @Column(name = "THU_BHYT")
    @Config(key = "ATTQUIT200900.THU_BHYT")
    public java.math.BigDecimal getTHU_BHYT() {
        return THU_BHYT;
    }
    /**
     * 設定SO DUONG +, THU BHYT, DA MUA BHYT MA CHUA THU TIEN. VD NHU NV NGHI SAN MINH VAN MUA BHYT NHUNG CHUA CO LUONG DE TRU, SAU NGHI SAN NV NAY NGHI LUON MINH SE TRU NHUNG THANG DA MU DO
     * @param THU_BHYT SO DUONG +, THU BHYT, DA MUA BHYT MA CHUA THU TIEN. VD NHU NV NGHI SAN MINH VAN MUA BHYT NHUNG CHUA CO LUONG DE TRU, SAU NGHI SAN NV NAY NGHI LUON MINH SE TRU NHUNG THANG DA MU DO
     */
    public void setTHU_BHYT(java.math.BigDecimal THU_BHYT) {
        this.THU_BHYT = THU_BHYT;
    }
    /**
     * 取得TRO CAP LUONG CB=BSALY_AVG*M_TROCAP
     * @return TC_BSALY TRO CAP LUONG CB=BSALY_AVG*M_TROCAP
     */
    @Column(name = "TC_BSALY")
    @Config(key = "ATTQUIT200900.TC_BSALY")
    public java.math.BigDecimal getTC_BSALY() {
        return TC_BSALY;
    }
    /**
     * 設定TRO CAP LUONG CB=BSALY_AVG*M_TROCAP
     * @param TC_BSALY TRO CAP LUONG CB=BSALY_AVG*M_TROCAP
     */
    public void setTC_BSALY(java.math.BigDecimal TC_BSALY) {
        this.TC_BSALY = TC_BSALY;
    }
    /**
     * 取得TRO CAP PCCV=BONUS2_AVG*M_TROCAP
     * @return TC_BONUS2 TRO CAP PCCV=BONUS2_AVG*M_TROCAP
     */
    @Column(name = "TC_BONUS2")
    @Config(key = "ATTQUIT200900.TC_BONUS2")
    public java.math.BigDecimal getTC_BONUS2() {
        return TC_BONUS2;
    }
    /**
     * 設定TRO CAP PCCV=BONUS2_AVG*M_TROCAP
     * @param TC_BONUS2 TRO CAP PCCV=BONUS2_AVG*M_TROCAP
     */
    public void setTC_BONUS2(java.math.BigDecimal TC_BONUS2) {
        this.TC_BONUS2 = TC_BONUS2;
    }
    /**
     * 取得GOM CHUNG COT
     * @return BUTHU_BHYT GOM CHUNG COT
     */
    @Column(name = "BUTHU_BHYT")
    @Config(key = "ATTQUIT200900.BUTHU_BHYT")
    public java.math.BigDecimal getBUTHU_BHYT() {
        return BUTHU_BHYT;
    }
    /**
     * 設定GOM CHUNG COT
     * @param BUTHU_BHYT GOM CHUNG COT
     */
    public void setBUTHU_BHYT(java.math.BigDecimal BUTHU_BHYT) {
        this.BUTHU_BHYT = BUTHU_BHYT;
    }
    /**
     * 取得TIEN PCCVU CUA CNV , SO TIEN GOC CHUA TINH THEO TY LE NGAY CONG
     * @return BONUS2_GOC TIEN PCCVU CUA CNV , SO TIEN GOC CHUA TINH THEO TY LE NGAY CONG
     */
    @Column(name = "BONUS2_GOC")
    @Config(key = "ATTQUIT200900.BONUS2_GOC")
    public java.math.BigDecimal getBONUS2_GOC() {
        return BONUS2_GOC;
    }
    /**
     * 設定TIEN PCCVU CUA CNV , SO TIEN GOC CHUA TINH THEO TY LE NGAY CONG
     * @param BONUS2_GOC TIEN PCCVU CUA CNV , SO TIEN GOC CHUA TINH THEO TY LE NGAY CONG
     */
    public void setBONUS2_GOC(java.math.BigDecimal BONUS2_GOC) {
        this.BONUS2_GOC = BONUS2_GOC;
    }
    /**
     * 取得PHAT LUONG THOI VIEC THEO TUNG DOT, LA THU 2 MOI TUAN
     * @return DOT_TV PHAT LUONG THOI VIEC THEO TUNG DOT, LA THU 2 MOI TUAN
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DOT_TV")
    @Config(key = "ATTQUIT200900.DOT_TV")
    public java.util.Date getDOT_TV() {
        return DOT_TV;
    }
    /**
     * 設定PHAT LUONG THOI VIEC THEO TUNG DOT, LA THU 2 MOI TUAN
     * @param DOT_TV PHAT LUONG THOI VIEC THEO TUNG DOT, LA THU 2 MOI TUAN
     */
    public void setDOT_TV(java.util.Date DOT_TV) {
        this.DOT_TV = DOT_TV;
    }
    /**
     * @return MM_DENBU 
     */
    @Column(name = "M_DENBU")
    @Config(key = "ATTQUIT200900.M_DENBU")
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
    @Config(key = "ATTQUIT200900.M_DENBU_S")
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
     * 取得PHAT LUONG THOI VIEC THEO TUNG DOT, THU 2 MOI TUAN
     * @return DOT_TV1 PHAT LUONG THOI VIEC THEO TUNG DOT, THU 2 MOI TUAN
     */
    @Length(max = 10)
    @Column(name = "DOT_TV1")
    @Config(key = "ATTQUIT200900.DOT_TV1")
    public java.lang.String getDOT_TV1() {
        return DOT_TV1;
    }
    /**
     * 設定PHAT LUONG THOI VIEC THEO TUNG DOT, THU 2 MOI TUAN
     * @param DOT_TV1 PHAT LUONG THOI VIEC THEO TUNG DOT, THU 2 MOI TUAN
     */
    public void setDOT_TV1(java.lang.String DOT_TV1) {
        this.DOT_TV1 = DOT_TV1;
    }
    /**
     * 取得THUC NHAN AO TRUOC KHI TRU D_NGHITRUOC_S
     * @return TS_HIENTAI1 THUC NHAN AO TRUOC KHI TRU D_NGHITRUOC_S
     */
    @Column(name = "TS_HIENTAI1")
    @Config(key = "ATTQUIT200900.TS_HIENTAI1")
    public java.math.BigDecimal getTS_HIENTAI1() {
        return TS_HIENTAI1;
    }
    /**
     * 設定THUC NHAN AO TRUOC KHI TRU D_NGHITRUOC_S
     * @param TS_HIENTAI1 THUC NHAN AO TRUOC KHI TRU D_NGHITRUOC_S
     */
    public void setTS_HIENTAI1(java.math.BigDecimal TS_HIENTAI1) {
        this.TS_HIENTAI1 = TS_HIENTAI1;
    }
    /**
     * @return TT_TCTVIEC1 
     */
    @Column(name = "T_TCTVIEC1")
    @Config(key = "ATTQUIT200900.T_TCTVIEC1")
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
     * 取得TONG LUONG THANG THOI VIEC LAM TRON 500, DE XUAT RA KY NHAN
     * @return TS_HIENTAI2 TONG LUONG THANG THOI VIEC LAM TRON 500, DE XUAT RA KY NHAN
     */
    @Column(name = "TS_HIENTAI2")
    @Config(key = "ATTQUIT200900.TS_HIENTAI2")
    public java.math.BigDecimal getTS_HIENTAI2() {
        return TS_HIENTAI2;
    }
    /**
     * 設定TONG LUONG THANG THOI VIEC LAM TRON 500, DE XUAT RA KY NHAN
     * @param TS_HIENTAI2 TONG LUONG THANG THOI VIEC LAM TRON 500, DE XUAT RA KY NHAN
     */
    public void setTS_HIENTAI2(java.math.BigDecimal TS_HIENTAI2) {
        this.TS_HIENTAI2 = TS_HIENTAI2;
    }
    /**
     * 取得KTOAN CHIA XUONG THEO TRUC TIPE OR GIAN TIEP
     * @return DEPT_KT KTOAN CHIA XUONG THEO TRUC TIPE OR GIAN TIEP
     */
    @Length(max = 50)
    @Column(name = "DEPT_KT")
    @Config(key = "ATTQUIT200900.DEPT_KT")
    public java.lang.String getDEPT_KT() {
        return DEPT_KT;
    }
    /**
     * 設定KTOAN CHIA XUONG THEO TRUC TIPE OR GIAN TIEP
     * @param DEPT_KT KTOAN CHIA XUONG THEO TRUC TIPE OR GIAN TIEP
     */
    public void setDEPT_KT(java.lang.String DEPT_KT) {
        this.DEPT_KT = DEPT_KT;
    }
    /**
     * 取得STT cua CNV do tren bang TONG KY NHAN VA PLN THOI VIEC PHAI LA 1. BEGIN TV 20/09/2010
     * @return STT STT cua CNV do tren bang TONG KY NHAN VA PLN THOI VIEC PHAI LA 1. BEGIN TV 20/09/2010
     */
    @Column(name = "STT")
    @Config(key = "ATTQUIT200900.STT")
    public java.math.BigDecimal getSTT() {
        return STT;
    }
    /**
     * 設定STT cua CNV do tren bang TONG KY NHAN VA PLN THOI VIEC PHAI LA 1. BEGIN TV 20/09/2010
     * @param STT STT cua CNV do tren bang TONG KY NHAN VA PLN THOI VIEC PHAI LA 1. BEGIN TV 20/09/2010
     */
    public void setSTT(java.math.BigDecimal STT) {
        this.STT = STT;
    }
    /**
     * 取得TCA NGAY, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @return ADDCLS1_O TCA NGAY, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    @Column(name = "ADDCLS1_O")
    @Config(key = "ATTQUIT200900.ADDCLS1_O")
    public java.math.BigDecimal getADDCLS1_O() {
        return ADDCLS1_O;
    }
    /**
     * 設定TCA NGAY, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @param ADDCLS1_O TCA NGAY, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    public void setADDCLS1_O(java.math.BigDecimal ADDCLS1_O) {
        this.ADDCLS1_O = ADDCLS1_O;
    }
    /**
     * 取得TCA DEM, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @return NADDCLS_O TCA DEM, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    @Column(name = "NADDCLS_O")
    @Config(key = "ATTQUIT200900.NADDCLS_O")
    public java.math.BigDecimal getNADDCLS_O() {
        return NADDCLS_O;
    }
    /**
     * 設定TCA DEM, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @param NADDCLS_O TCA DEM, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    public void setNADDCLS_O(java.math.BigDecimal NADDCLS_O) {
        this.NADDCLS_O = NADDCLS_O;
    }
    /**
     * 取得TCA CHU NHAT, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @return ADDHOL_O TCA CHU NHAT, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    @Column(name = "ADDHOL_O")
    @Config(key = "ATTQUIT200900.ADDHOL_O")
    public java.math.BigDecimal getADDHOL_O() {
        return ADDHOL_O;
    }
    /**
     * 設定TCA CHU NHAT, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @param ADDHOL_O TCA CHU NHAT, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    public void setADDHOL_O(java.math.BigDecimal ADDHOL_O) {
        this.ADDHOL_O = ADDHOL_O;
    }
    /**
     * 取得TCA LE, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @return ADDHOLN_O TCA LE, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    @Column(name = "ADDHOLN_O")
    @Config(key = "ATTQUIT200900.ADDHOLN_O")
    public java.math.BigDecimal getADDHOLN_O() {
        return ADDHOLN_O;
    }
    /**
     * 設定TCA LE, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @param ADDHOLN_O TCA LE, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    public void setADDHOLN_O(java.math.BigDecimal ADDHOLN_O) {
        this.ADDHOLN_O = ADDHOLN_O;
    }
    /**
     * 取得TIEN TCA NGAY, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @return ADDCLS1_O_S TIEN TCA NGAY, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    @Column(name = "ADDCLS1_O_S")
    @Config(key = "ATTQUIT200900.ADDCLS1_O_S")
    public java.math.BigDecimal getADDCLS1_O_S() {
        return ADDCLS1_O_S;
    }
    /**
     * 設定TIEN TCA NGAY, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @param ADDCLS1_O_S TIEN TCA NGAY, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    public void setADDCLS1_O_S(java.math.BigDecimal ADDCLS1_O_S) {
        this.ADDCLS1_O_S = ADDCLS1_O_S;
    }
    /**
     * 取得TIEN TCA DEM, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @return NADDCLS_O_S TIEN TCA DEM, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    @Column(name = "NADDCLS_O_S")
    @Config(key = "ATTQUIT200900.NADDCLS_O_S")
    public java.math.BigDecimal getNADDCLS_O_S() {
        return NADDCLS_O_S;
    }
    /**
     * 設定TIEN TCA DEM, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @param NADDCLS_O_S TIEN TCA DEM, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    public void setNADDCLS_O_S(java.math.BigDecimal NADDCLS_O_S) {
        this.NADDCLS_O_S = NADDCLS_O_S;
    }
    /**
     * 取得TIEN TCA CHU NHAT, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @return ADDHOL_O_S TIEN TCA CHU NHAT, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    @Column(name = "ADDHOL_O_S")
    @Config(key = "ATTQUIT200900.ADDHOL_O_S")
    public java.math.BigDecimal getADDHOL_O_S() {
        return ADDHOL_O_S;
    }
    /**
     * 設定TIEN TCA CHU NHAT, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @param ADDHOL_O_S TIEN TCA CHU NHAT, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    public void setADDHOL_O_S(java.math.BigDecimal ADDHOL_O_S) {
        this.ADDHOL_O_S = ADDHOL_O_S;
    }
    /**
     * 取得TIEN TCA LE, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @return ADDHOLN_O_S TIEN TCA LE, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    @Column(name = "ADDHOLN_O_S")
    @Config(key = "ATTQUIT200900.ADDHOLN_O_S")
    public java.math.BigDecimal getADDHOLN_O_S() {
        return ADDHOLN_O_S;
    }
    /**
     * 設定TIEN TCA LE, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @param ADDHOLN_O_S TIEN TCA LE, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    public void setADDHOLN_O_S(java.math.BigDecimal ADDHOLN_O_S) {
        this.ADDHOLN_O_S = ADDHOLN_O_S;
    }
    /**
     * 取得SO DEM TIEN COM TCA TU 1-1.5/ NGAY, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @return ACNM_O SO DEM TIEN COM TCA TU 1-1.5/ NGAY, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    @Column(name = "ACNM_O")
    @Config(key = "ATTQUIT200900.ACNM_O")
    public java.math.BigDecimal getACNM_O() {
        return ACNM_O;
    }
    /**
     * 設定SO DEM TIEN COM TCA TU 1-1.5/ NGAY, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     * @param ACNM_O SO DEM TIEN COM TCA TU 1-1.5/ NGAY, TCA NGOAI KO C TRONG HE THONG. PS 11/2010_FVS
     */
    public void setACNM_O(java.math.BigDecimal ACNM_O) {
        this.ACNM_O = ACNM_O;
    }
    /**
     * @return DEPSN_BHYT 
     */
    @Length(max = 5)
    @Column(name = "DEPSN_BHYT")
    @Config(key = "ATTQUIT200900.DEPSN_BHYT")
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
    @Config(key = "ATTQUIT200900.NOTE_NV")
    public java.lang.String getNOTE_NV() {
        return NOTE_NV;
    }
    /**
     * @param NOTE_NV 
     */
    public void setNOTE_NV(java.lang.String NOTE_NV) {
        this.NOTE_NV = NOTE_NV;
    }
    /**
     * 取得PC LAM 8H + TIEN XANG
     * @return BONUS8 PC LAM 8H + TIEN XANG
     */
    @Column(name = "BONUS8")
    @Config(key = "ATTQUIT200900.BONUS8")
    public java.math.BigDecimal getBONUS8() {
        return BONUS8;
    }
    /**
     * 設定PC LAM 8H + TIEN XANG
     * @param BONUS8 PC LAM 8H + TIEN XANG
     */
    public void setBONUS8(java.math.BigDecimal BONUS8) {
        this.BONUS8 = BONUS8;
    }
    /**
     * 取得LAY LUONG THANG TRUOC DE KHAU TRU BH VA NGHI TRUOC THOI HAN NEU CO_ AD DOT TV 14/11/2011
     * @return BSALY_LAST_MONTH LAY LUONG THANG TRUOC DE KHAU TRU BH VA NGHI TRUOC THOI HAN NEU CO_ AD DOT TV 14/11/2011
     */
    @Column(name = "BSALY_LAST_MONTH")
    @Config(key = "ATTQUIT200900.BSALY_LAST_MONTH")
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
