package ds.program.fvhr.domain;
import javax.persistence.FetchType;
import javax.persistence.IdClass;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;

import ds.program.fvhr.domain.pk.N_RESTPk;
import dsc.util.hibernate.validator.NotBlank;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@IdClass(N_RESTPk.class)
@Entity
@Table(name = "N_REST")
public class N_REST {
    private java.lang.String EMPSN;
    private java.lang.String YEAR;
    private java.lang.Long STORED;	//PHEP TON NAM TRUOC
    private java.lang.Long OBTAIN;	//PN DUOC SD NAM HIEN TAI
    private java.lang.Long USED;	//PN DA SD NAM HIEN TAI
    private java.lang.Long REMAIN;	//PN CON LAI CUA NAM HT
    private java.lang.Long DEBT;	//PNO CUA NAM HIEN TAI
    private java.lang.String NOTE;	//GHI CHU
    private java.lang.String USER_ID;
    private java.lang.Long CO_ARR;	//CTY SX
    private java.lang.Long EMP_APP;	//CNV TXIN
    private java.lang.Long HOL_LUNAR;	//TET LE
    private java.lang.Long STITCHING1;	//CN MAY DAU NAM
    private java.lang.Long STITCHING2;	//CN MAY CUOI NAM
    private java.lang.Long SENIORITY;	//THAM NIEN
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_REST.EMPSN")
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
     * @return YEAR 
     */
    @Id
    @NotBlank
    @Column(name = "YEAR")
    @Config(key = "N_REST.YEAR")
    public java.lang.String getYEAR() {
        return YEAR;
    }
    /**
     * @param YEAR 
     */
    public void setYEAR(java.lang.String YEAR) {
        this.YEAR = YEAR;
    }
    /**
     * �?�得PHEP TON NAM TRUOC
     * @return STORED PHEP TON NAM TRUOC
     */
    @Column(name = "STORED")
    @Config(key = "N_REST.STORED")
    public java.lang.Long getSTORED() {
        return STORED;
    }
    /**
     * 設定PHEP TON NAM TRUOC
     * @param STORED PHEP TON NAM TRUOC
     */
    public void setSTORED(java.lang.Long STORED) {
        this.STORED = STORED;
    }
    /**
     * �?�得PN DUOC SD NAM HIEN TAI
     * @return OBTAIN PN DUOC SD NAM HIEN TAI
     */
    @Column(name = "OBTAIN")
    @Config(key = "N_REST.OBTAIN")
    public java.lang.Long getOBTAIN() {
        return OBTAIN;
    }
    /**
     * 設定PN DUOC SD NAM HIEN TAI
     * @param OBTAIN PN DUOC SD NAM HIEN TAI
     */
    public void setOBTAIN(java.lang.Long OBTAIN) {
        this.OBTAIN = OBTAIN;
    }
    /**
     * �?�得PN DA SD NAM HIEN TAI
     * @return USED PN DA SD NAM HIEN TAI
     */
    @Column(name = "USED")
    @Config(key = "N_REST.USED")
    public java.lang.Long getUSED() {
        return USED;
    }
    /**
     * 設定PN DA SD NAM HIEN TAI
     * @param USED PN DA SD NAM HIEN TAI
     */
    public void setUSED(java.lang.Long USED) {
        this.USED = USED;
    }
    /**
     * �?�得PN CON LAI CUA NAM HT
     * @return REMAIN PN CON LAI CUA NAM HT
     */
    @Column(name = "REMAIN")
    @Config(key = "N_REST.REMAIN")
    public java.lang.Long getREMAIN() {
        return REMAIN;
    }
    /**
     * 設定PN CON LAI CUA NAM HT
     * @param REMAIN PN CON LAI CUA NAM HT
     */
    public void setREMAIN(java.lang.Long REMAIN) {
        this.REMAIN = REMAIN;
    }
    /**
     * �?�得PNO CUA NAM HIEN TAI
     * @return DEBT PNO CUA NAM HIEN TAI
     */
    @Column(name = "DEBT")
    @Config(key = "N_REST.DEBT")
    public java.lang.Long getDEBT() {
        return DEBT;
    }
    /**
     * 設定PNO CUA NAM HIEN TAI
     * @param DEBT PNO CUA NAM HIEN TAI
     */
    public void setDEBT(java.lang.Long DEBT) {
        this.DEBT = DEBT;
    }
    /**
     * �?�得GHI CHU
     * @return NOTE GHI CHU
     */
    @Length(max = 30)
    @Column(name = "NOTE")
    @Config(key = "N_REST.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * 設定GHI CHU
     * @param NOTE GHI CHU
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
    /**
     * @return USER_ID 
     */
    @Length(max = 4)
    @Column(name = "USER_ID")
    @Config(key = "N_REST.USER_ID")
    public java.lang.String getUSER_ID() {
        return USER_ID;
    }
    /**
     * @param USER_ID 
     */
    public void setUSER_ID(java.lang.String USER_ID) {
        this.USER_ID = USER_ID;
    }
    /**
     * �?�得CTY SX
     * @return CO_ARR CTY SX
     */
    @Column(name = "CO_ARR")
    @Config(key = "N_REST.CO_ARR")
    public java.lang.Long getCO_ARR() {
        return CO_ARR;
    }
    /**
     * 設定CTY SX
     * @param CO_ARR CTY SX
     */
    public void setCO_ARR(java.lang.Long CO_ARR) {
        this.CO_ARR = CO_ARR;
    }
    /**
     * �?�得CNV TXIN
     * @return EMP_APP CNV TXIN
     */
    @Column(name = "EMP_APP")
    @Config(key = "N_REST.EMP_APP")
    public java.lang.Long getEMP_APP() {
        return EMP_APP;
    }
    /**
     * 設定CNV TXIN
     * @param EMP_APP CNV TXIN
     */
    public void setEMP_APP(java.lang.Long EMP_APP) {
        this.EMP_APP = EMP_APP;
    }
    /**
     * �?�得TET LE
     * @return HOL_LUNAR TET LE
     */
    @Column(name = "HOL_LUNAR")
    @Config(key = "N_REST.HOL_LUNAR")
    public java.lang.Long getHOL_LUNAR() {
        return HOL_LUNAR;
    }
    /**
     * 設定TET LE
     * @param HOL_LUNAR TET LE
     */
    public void setHOL_LUNAR(java.lang.Long HOL_LUNAR) {
        this.HOL_LUNAR = HOL_LUNAR;
    }
    /**
     * �?�得CN MAY DAU NAM
     * @return STITCHING1 CN MAY DAU NAM
     */
    @Column(name = "STITCHING1")
    @Config(key = "N_REST.STITCHING1")
    public java.lang.Long getSTITCHING1() {
        return STITCHING1;
    }
    /**
     * 設定CN MAY DAU NAM
     * @param STITCHING1 CN MAY DAU NAM
     */
    public void setSTITCHING1(java.lang.Long STITCHING1) {
        this.STITCHING1 = STITCHING1;
    }
    /**
     * �?�得CN MAY CUOI NAM
     * @return STITCHING2 CN MAY CUOI NAM
     */
    @Column(name = "STITCHING2")
    @Config(key = "N_REST.STITCHING2")
    public java.lang.Long getSTITCHING2() {
        return STITCHING2;
    }
    /**
     * 設定CN MAY CUOI NAM
     * @param STITCHING2 CN MAY CUOI NAM
     */
    public void setSTITCHING2(java.lang.Long STITCHING2) {
        this.STITCHING2 = STITCHING2;
    }
    /**
     * �?�得THAM NIEN
     * @return SENIORITY THAM NIEN
     */
    @Column(name = "SENIORITY")
    @Config(key = "N_REST.SENIORITY")
    public java.lang.Long getSENIORITY() {
        return SENIORITY;
    }
    /**
     * 設定THAM NIEN
     * @param SENIORITY THAM NIEN
     */
    public void setSENIORITY(java.lang.Long SENIORITY) {
        this.SENIORITY = SENIORITY;
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

    

}
