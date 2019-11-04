package ds.program.fvhr.domain;
import java.math.BigDecimal;

import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_GET_DATAPk;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Transient;

import dsc.util.hibernate.validator.NotBlank;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.Length;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* LAY THONG TIN CHI TIET DL QUET THE
**/
@IdClass(N_GET_DATAPk.class)
@Entity
@Table(name = "N_GET_DATA")
public class N_GET_DATA {
    private java.lang.String EMPSN;	//SO THE
    private java.lang.String MONTHS;	//THANG
    private java.lang.String YEARS;	//NAM
    private java.math.BigDecimal DUCLS;	//TONG NGAY LAM
    private java.math.BigDecimal NDUCLS;	//TONG DEM LAM
    private java.math.BigDecimal ADDHOL;	//TONG NGAY LAM CN
    private java.math.BigDecimal ADDCLS1;	//TONG GIO TANG CA NGAY
    private java.math.BigDecimal NADDCLS;	//TONG GIO TANG CA DEM
    private java.math.BigDecimal LATE;	//TONG PHUT TRE
    private java.math.BigDecimal SIGN;	//TONG SO LAN KY TEN
    private java.math.BigDecimal ACN;	//TONG SO DEM TANG CA
    private java.math.BigDecimal ACNM;	//TONG SO DEM TIEN COM
    private java.math.BigDecimal REST;	//TONG NGAY NGHI CO PHEP
    private java.math.BigDecimal REST_PAY;	//TONG NGAY NGHI CO LUONG
    private java.math.BigDecimal REST_SICK;	//TONG NGAY NGHI PHEP BENH
    private java.math.BigDecimal OTHER;	//TONG NGAY NGHI DUONG SUC
    private java.math.BigDecimal NWHOUR;	//KHANG CONG
    private java.math.BigDecimal ADDHOLN;	//TANG CA NGAY LE
    private java.math.BigDecimal LMATER;	//TONG NGAY NGHI SAN
    private java.math.BigDecimal LOCKED;
    private java.lang.String DEPSN;	//DON VI HIEU LUC CUA THANG TINH LUONG
    private java.util.Date UP_DATE;	//LAST UPDATE DATE
    private java.lang.String UP_USER;	//LAST UPDATE USER
    private java.lang.String DEPT_STATUS;	//Y-LOCKED; N-UPDATEABLE
    private java.math.BigDecimal TRANS_COUNT;	//SO LAN CHUYEN DU LIEU
    /**
     * 取得SO THE
     * @return EMPSN SO THE
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_GET_DATA.EMPSN")
    public java.lang.String getEMPSN() {
        return EMPSN;
    }
    /**
     * 設定SO THE
     * @param EMPSN SO THE
     */
    public void setEMPSN(java.lang.String EMPSN) {
        this.EMPSN = EMPSN;
    }
    /**
     * 取得THANG
     * @return MONTHS THANG
     */
    @Id
    @NotBlank
    @Column(name = "MONTHS")
    @Config(key = "N_GET_DATA.MONTHS")
    public java.lang.String getMONTHS() {
        return MONTHS;
    }
    /**
     * 設定THANG
     * @param MONTHS THANG
     */
    public void setMONTHS(java.lang.String MONTHS) {
        this.MONTHS = MONTHS;
    }
    /**
     * 取得NAM
     * @return YEARS NAM
     */
    @Id
    @NotBlank
    @Column(name = "YEARS")
    @Config(key = "N_GET_DATA.YEARS")
    public java.lang.String getYEARS() {
        return YEARS;
    }
    /**
     * 設定NAM
     * @param YEARS NAM
     */
    public void setYEARS(java.lang.String YEARS) {
        this.YEARS = YEARS;
    }
    /**
     * 取得TONG NGAY LAM
     * @return DUCLS TONG NGAY LAM
     */
    @Column(name = "DUCLS")
    @Config(key = "N_GET_DATA.DUCLS")
    public java.math.BigDecimal getDUCLS() {
        return DUCLS;
    }
    /**
     * 設定TONG NGAY LAM
     * @param DUCLS TONG NGAY LAM
     */
    public void setDUCLS(java.math.BigDecimal DUCLS) {
        this.DUCLS = DUCLS;
    }
    /**
     * 取得TONG DEM LAM
     * @return NDUCLS TONG DEM LAM
     */
    @Column(name = "NDUCLS")
    @Config(key = "N_GET_DATA.NDUCLS")
    public java.math.BigDecimal getNDUCLS() {
        return NDUCLS;
    }
    /**
     * 設定TONG DEM LAM
     * @param NDUCLS TONG DEM LAM
     */
    public void setNDUCLS(java.math.BigDecimal NDUCLS) {
        this.NDUCLS = NDUCLS;
    }
    /**
     * 取得TONG NGAY LAM CN
     * @return ADDHOL TONG NGAY LAM CN
     */
    @Column(name = "ADDHOL")
    @Config(key = "N_GET_DATA.ADDHOL")
    public java.math.BigDecimal getADDHOL() {
        return ADDHOL;
    }
    /**
     * 設定TONG NGAY LAM CN
     * @param ADDHOL TONG NGAY LAM CN
     */
    public void setADDHOL(java.math.BigDecimal ADDHOL) {
        this.ADDHOL = ADDHOL;
    }
    /**
     * 取得TONG GIO TANG CA NGAY
     * @return ADDCLS1 TONG GIO TANG CA NGAY
     */
    @Column(name = "ADDCLS1")
    @Config(key = "N_GET_DATA.ADDCLS1")
    public java.math.BigDecimal getADDCLS1() {
        return ADDCLS1;
    }
    /**
     * 設定TONG GIO TANG CA NGAY
     * @param ADDCLS1 TONG GIO TANG CA NGAY
     */
    public void setADDCLS1(java.math.BigDecimal ADDCLS1) {
        this.ADDCLS1 = ADDCLS1;
    }
    /**
     * 取得TONG GIO TANG CA DEM
     * @return NADDCLS TONG GIO TANG CA DEM
     */
    @Column(name = "NADDCLS")
    @Config(key = "N_GET_DATA.NADDCLS")
    public java.math.BigDecimal getNADDCLS() {
        return NADDCLS;
    }
    /**
     * 設定TONG GIO TANG CA DEM
     * @param NADDCLS TONG GIO TANG CA DEM
     */
    public void setNADDCLS(java.math.BigDecimal NADDCLS) {
        this.NADDCLS = NADDCLS;
    }
    /**
     * 取得TONG PHUT TRE
     * @return LATE TONG PHUT TRE
     */
    @Column(name = "LATE")
    @Config(key = "N_GET_DATA.LATE")
    public java.math.BigDecimal getLATE() {
        return LATE;
    }
    /**
     * 設定TONG PHUT TRE
     * @param LATE TONG PHUT TRE
     */
    public void setLATE(java.math.BigDecimal LATE) {
        this.LATE = LATE;
    }
    /**
     * 取得TONG SO LAN KY TEN
     * @return SIGN TONG SO LAN KY TEN
     */
    @Column(name = "SIGN")
    @Config(key = "N_GET_DATA.SIGN")
    public java.math.BigDecimal getSIGN() {
        return SIGN;
    }
    /**
     * 設定TONG SO LAN KY TEN
     * @param SIGN TONG SO LAN KY TEN
     */
    public void setSIGN(java.math.BigDecimal SIGN) {
        this.SIGN = SIGN;
    }
    /**
     * 取得TONG SO DEM TANG CA
     * @return ACN TONG SO DEM TANG CA
     */
    @Column(name = "ACN")
    @Config(key = "N_GET_DATA.ACN")
    public java.math.BigDecimal getACN() {
        return ACN;
    }
    /**
     * 設定TONG SO DEM TANG CA
     * @param ACN TONG SO DEM TANG CA
     */
    public void setACN(java.math.BigDecimal ACN) {
        this.ACN = ACN;
    }
    /**
     * 取得TONG SO DEM TIEN COM
     * @return ACNM TONG SO DEM TIEN COM
     */
    @Column(name = "ACNM")
    @Config(key = "N_GET_DATA.ACNM")
    public java.math.BigDecimal getACNM() {
        return ACNM;
    }
    /**
     * 設定TONG SO DEM TIEN COM
     * @param ACNM TONG SO DEM TIEN COM
     */
    public void setACNM(java.math.BigDecimal ACNM) {
        this.ACNM = ACNM;
    }
    /**
     * 取得TONG NGAY NGHI CO PHEP
     * @return REST TONG NGAY NGHI CO PHEP
     */
    @Column(name = "REST")
    @Config(key = "N_GET_DATA.REST")
    public java.math.BigDecimal getREST() {
        return REST;
    }
    /**
     * 設定TONG NGAY NGHI CO PHEP
     * @param REST TONG NGAY NGHI CO PHEP
     */
    public void setREST(java.math.BigDecimal REST) {
        this.REST = REST;
    }
    /**
     * 取得TONG NGAY NGHI CO LUONG
     * @return REST_PAY TONG NGAY NGHI CO LUONG
     */
    @Column(name = "REST_PAY")
    @Config(key = "N_GET_DATA.REST_PAY")
    public java.math.BigDecimal getREST_PAY() {
        return REST_PAY;
    }
    /**
     * 設定TONG NGAY NGHI CO LUONG
     * @param REST_PAY TONG NGAY NGHI CO LUONG
     */
    public void setREST_PAY(java.math.BigDecimal REST_PAY) {
        this.REST_PAY = REST_PAY;
    }
    /**
     * 取得TONG NGAY NGHI PHEP BENH
     * @return REST_SICK TONG NGAY NGHI PHEP BENH
     */
    @Column(name = "REST_SICK")
    @Config(key = "N_GET_DATA.REST_SICK")
    public java.math.BigDecimal getREST_SICK() {
        return REST_SICK;
    }
    /**
     * 設定TONG NGAY NGHI PHEP BENH
     * @param REST_SICK TONG NGAY NGHI PHEP BENH
     */
    public void setREST_SICK(java.math.BigDecimal REST_SICK) {
        this.REST_SICK = REST_SICK;
    }
    /**
     * 取得TONG NGAY NGHI DUONG SUC
     * @return OTHER TONG NGAY NGHI DUONG SUC
     */
    @Column(name = "OTHER")
    @Config(key = "N_GET_DATA.OTHER")
    public java.math.BigDecimal getOTHER() {
        return OTHER;
    }
    /**
     * 設定TONG NGAY NGHI DUONG SUC
     * @param OTHER TONG NGAY NGHI DUONG SUC
     */
    public void setOTHER(java.math.BigDecimal OTHER) {
        this.OTHER = OTHER;
    }
    /**
     * 取得KHANG CONG
     * @return NWHOUR KHANG CONG
     */
    @Column(name = "NWHOUR")
    @Config(key = "N_GET_DATA.NWHOUR")
    public java.math.BigDecimal getNWHOUR() {
        return NWHOUR;
    }
    /**
     * 設定KHANG CONG
     * @param NWHOUR KHANG CONG
     */
    public void setNWHOUR(java.math.BigDecimal NWHOUR) {
        this.NWHOUR = NWHOUR;
    }
    /**
     * 取得TANG CA NGAY LE
     * @return ADDHOLN TANG CA NGAY LE
     */
    @Column(name = "ADDHOLN")
    @Config(key = "N_GET_DATA.ADDHOLN")
    public java.math.BigDecimal getADDHOLN() {
        return ADDHOLN;
    }
    /**
     * 設定TANG CA NGAY LE
     * @param ADDHOLN TANG CA NGAY LE
     */
    public void setADDHOLN(java.math.BigDecimal ADDHOLN) {
        this.ADDHOLN = ADDHOLN;
    }
    /**
     * 取得TONG NGAY NGHI SAN
     * @return LMATER TONG NGAY NGHI SAN
     */
    @Column(name = "LMATER")
    @Config(key = "N_GET_DATA.LMATER")
    public java.math.BigDecimal getLMATER() {
        return LMATER;
    }
    /**
     * 設定TONG NGAY NGHI SAN
     * @param LMATER TONG NGAY NGHI SAN
     */
    public void setLMATER(java.math.BigDecimal LMATER) {
        this.LMATER = LMATER;
    }
    /**
     * @return LOCKED 
     */
    @Column(name = "LOCKED")
    @Config(key = "N_GET_DATA.LOCKED")
    public java.math.BigDecimal getLOCKED() {
        return LOCKED;
    }
    /**
     * @param LOCKED 
     */
    public void setLOCKED(java.math.BigDecimal LOCKED) {
        this.LOCKED = LOCKED;
    }
    /**
     * 取得DON VI HIEU LUC CUA THANG TINH LUONG
     * @return DEPSN DON VI HIEU LUC CUA THANG TINH LUONG
     */
    @Length(max = 5)
//    @ManyToOne
    @Column(name = "DEPSN")
//    @NotFound(action=NotFoundAction.IGNORE)
    @Config(key = "N_GET_DATA.DEPSN")
    public java.lang.String getDEPSN() {
        return DEPSN;
    }
    /**
     * 設定DON VI HIEU LUC CUA THANG TINH LUONG
     * @param DEPSN DON VI HIEU LUC CUA THANG TINH LUONG
     */
    public void setDEPSN(java.lang.String DEPSN) {
        this.DEPSN = DEPSN;
    }
    /**
     * 取得LAST UPDATE DATE
     * @return UP_DATE LAST UPDATE DATE
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "UP_DATE")
    @Config(key = "N_GET_DATA.UP_DATE")
    public java.util.Date getUP_DATE() {
        return UP_DATE;
    }
    /**
     * 設定LAST UPDATE DATE
     * @param UP_DATE LAST UPDATE DATE
     */
    public void setUP_DATE(java.util.Date UP_DATE) {
        this.UP_DATE = UP_DATE;
    }
    /**
     * 取得LAST UPDATE USER
     * @return UP_USER LAST UPDATE USER
     */
    @Length(max = 10)
    @Column(name = "UP_USER")
    @Config(key = "N_GET_DATA.UP_USER")
    public java.lang.String getUP_USER() {
        return UP_USER;
    }
    /**
     * 設定LAST UPDATE USER
     * @param UP_USER LAST UPDATE USER
     */
    public void setUP_USER(java.lang.String UP_USER) {
        this.UP_USER = UP_USER;
    }
    /**
     * 取得Y-LOCKED; N-UPDATEABLE
     * @return DEPT_STATUS Y-LOCKED; N-UPDATEABLE
     */
    @Length(max = 1)
    @Column(name = "DEPT_STATUS")
    @Config(key = "N_GET_DATA.DEPT_STATUS")
    public java.lang.String getDEPT_STATUS() {
        return DEPT_STATUS;
    }
    /**
     * 設定Y-LOCKED; N-UPDATEABLE
     * @param DEPT_STATUS Y-LOCKED; N-UPDATEABLE
     */
    public void setDEPT_STATUS(java.lang.String DEPT_STATUS) {
        this.DEPT_STATUS = DEPT_STATUS;
    }
    /**
     * 取得SO LAN CHUYEN DU LIEU
     * @return TRANS_COUNT SO LAN CHUYEN DU LIEU
     */
    @Column(name = "TRANS_COUNT")
    @Config(key = "N_GET_DATA.TRANS_COUNT")
    public java.math.BigDecimal getTRANS_COUNT() {
        return TRANS_COUNT;
    }
    /**
     * 設定SO LAN CHUYEN DU LIEU
     * @param TRANS_COUNT SO LAN CHUYEN DU LIEU
     */
    public void setTRANS_COUNT(java.math.BigDecimal TRANS_COUNT) {
        this.TRANS_COUNT = TRANS_COUNT;
    }
    
    private N_EMPLOYEE EMPSN_Object;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="EMPSN", referencedColumnName="EMPSN", insertable=false, updatable=false)
    @NotFound(action=NotFoundAction.IGNORE)
    @Config(key = "N_EMPLOYEE.EMPSN")
    public N_EMPLOYEE getEMPSN_Object() {
        return EMPSN_Object;
    }
    
    public void setEMPSN_Object(N_EMPLOYEE empsn) {
        this.EMPSN_Object = empsn;
    }
    
    private BigDecimal TOTAL_DAYS;
    
    @Transient
    @Config(key="N_GET_DATA.TOTAL_DAYS")
    public BigDecimal getTOTAL_DAYS(){
    	try{
    		this.TOTAL_DAYS = this.DUCLS.add(this.NDUCLS).add(this.REST).add(this.REST_PAY).add(this.REST_SICK).add(this.OTHER).add(this.LMATER).add(this.NWHOUR);
    	}catch(Exception e){
    		this.TOTAL_DAYS=BigDecimal.ZERO;
    	}
    	return this.TOTAL_DAYS;
    }
    
    public void setTOTAL_DAYS(BigDecimal TOTAL_DAYS){
    	this.TOTAL_DAYS=TOTAL_DAYS;
    }
}
