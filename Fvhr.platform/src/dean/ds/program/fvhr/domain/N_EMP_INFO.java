package ds.program.fvhr.domain;

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
@Entity
@Table(name = "N_EMP_INFO")
public class N_EMP_INFO {
    private java.lang.String EMPSN;	//so the
    private java.lang.String EMPCN;	//so IC_Card
    private java.lang.String LNAME;	//ten
    private java.lang.String ID_NO;	//CMND
    private java.util.Date DATE_HIRED;
    private java.lang.String NOTE;
    private java.lang.String USER_MANAGE_ID;
    private java.lang.String SHIFT;
    private java.lang.String WORK_STATUS;
    private java.lang.String DEPSN;
    private java.lang.String ID_POSS;
    private java.lang.String ID_JOB;
    private java.lang.String CODE;
    private java.lang.Long SALARY_B;
    private java.lang.String UP_USER;	
    private java.util.Date UP_DATE;
    private java.lang.Long ADVANCE;
    /**
     * �?�得so the
     * @return EMPSN so the
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_EMP_INFO.EMPSN")
    public java.lang.String getEMPSN() {
        return EMPSN;
    }
    /**
     * 設定so the
     * @param EMPSN so the
     */
    public void setEMPSN(java.lang.String EMPSN) {
        this.EMPSN = EMPSN;
    }
    /**
     * �?�得so IC_Card
     * @return EMPCN so IC_Card
     */
    @Length(max = 10)
    @Column(name = "EMPCN")
    @Config(key = "N_EMP_INFO.EMPCN")
    public java.lang.String getEMPCN() {
        return EMPCN;
    }
    /**
     * 設定so IC_Card
     * @param EMPCN so IC_Card
     */
    public void setEMPCN(java.lang.String EMPCN) {
        this.EMPCN = EMPCN;
    }
    /**
     * �?�得ten
     * @return LNAME ten
     */
    @Length(max = 10)
    @Column(name = "LNAME")
    @Config(key = "N_EMP_INFO.LNAME")
    public java.lang.String getLNAME() {
        return LNAME;
    }
    /**
     * 設定ten
     * @param LNAME ten
     */
    public void setLNAME(java.lang.String LNAME) {
        this.LNAME = LNAME;
    }
    /**
     * �?�得CMND
     * @return ID_NO CMND
     */
    @Length(max = 15)
    @Column(name = "ID_NO")
    @Config(key = "N_EMP_INFO.ID_NO")
    public java.lang.String getID_NO() {
        return ID_NO;
    }
    /**
     * 設定CMND
     * @param ID_NO CMND
     */
    public void setID_NO(java.lang.String ID_NO) {
        this.ID_NO = ID_NO;
    }
    /**
     * @return DATE_HIRED 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_HIRED")
    @Config(key = "N_EMP_INFO.DATE_HIRED")
    public java.util.Date getDATE_HIRED() {
        return DATE_HIRED;
    }
    /**
     * @param DATE_HIRED 
     */
    public void setDATE_HIRED(java.util.Date DATE_HIRED) {
        this.DATE_HIRED = DATE_HIRED;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 50)
    @Column(name = "NOTE")
    @Config(key = "N_EMP_INFO.NOTE")
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
     * @return USER_MANAGE_ID 
     */
    @Length(max = 3)
    @Column(name = "USER_MANAGE_ID")
    @Config(key = "N_EMP_INFO.USER_MANAGE_ID")
    public java.lang.String getUSER_MANAGE_ID() {
        return USER_MANAGE_ID;
    }
    /**
     * @param USER_MANAGE_ID 
     */
    public void setUSER_MANAGE_ID(java.lang.String USER_MANAGE_ID) {
        this.USER_MANAGE_ID = USER_MANAGE_ID;
    }
    /**
     * @return SHIFT 
     */
   
    @Length(max = 30)
    @Column(name = "SHIFT")
    @Config(key = "N_EMP_INFO.SHIFT")
    public java.lang.String getSHIFT() {
        return SHIFT;
    }
    /**
     * @param SHIFT 
     */
    public void setSHIFT(java.lang.String SHIFT) {
        this.SHIFT = SHIFT;
    }
    /**
     * @return WORK_STATUS 
     */
    @Length(max = 20)
    @Column(name = "WORK_STATUS")
    @Config(key = "N_EMP_INFO.WORK_STATUS")
    public java.lang.String getWORK_STATUS() {
        return WORK_STATUS;
    }
    /**
     * @param WORK_STATUS 
     */
    public void setWORK_STATUS(java.lang.String WORK_STATUS) {
        this.WORK_STATUS = WORK_STATUS;
    }
    /**
     * @return DEPSN 
     */

    @Length(max = 5)
    @Column(name = "DEPSN")
    @Config(key = "N_EMP_INFO.DEPSN")
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
     * @return ID_POSS 
     */
    @Length(max = 10)
    @Column(name = "ID_POSS")
    @Config(key = "N_EMP_INFO.ID_POSS")
    public java.lang.String getID_POSS() {
        return ID_POSS;
    }
    /**
     * @param ID_POSS 
     */
    public void setID_POSS(java.lang.String ID_POSS) {
        this.ID_POSS = ID_POSS;
    }
    /**
     * @return ID_JOB 
     */
    @Length(max = 50)
    @Column(name = "ID_JOB")
    @Config(key = "N_EMP_INFO.ID_JOB")
    public java.lang.String getID_JOB() {
        return ID_JOB;
    }
    /**
     * @param ID_JOB 
     */
    public void setID_JOB(java.lang.String ID_JOB) {
        this.ID_JOB = ID_JOB;
    }
    /**
     * @return CODE 
     */
    @Length(max = 8)
    @Column(name = "CODE")
    @Config(key = "N_EMP_INFO.CODE")
    public java.lang.String getCODE() {
        return CODE;
    }
    /**
     * @param CODE 
     */
    public void setCODE(java.lang.String CODE) {
        this.CODE = CODE;
    }
    /**
     * @return SALARY_B 
     */
    @Column(name = "SALARY_B")
    @Config(key = "N_EMP_INFO.SALARY_B")
    public java.lang.Long getSALARY_B() {
        return SALARY_B;
    }
    /**
     * @param SALARY_B 
     */
    public void setSALARY_B(java.lang.Long SALARY_B) {
        this.SALARY_B = SALARY_B;
    }
    
    @Length(max = 15)
    @Column(name = "UP_USER")
    @Config(key = "N_EMP_INFO.UP_USER")
    public java.lang.String getUP_USER() {
        return UP_USER;
    }
    /**
       UP_USER
     */
    public void setUP_USER(java.lang.String UP_USER) {
        this.UP_USER = UP_USER;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UP_DATE")
    @Config(key = "N_EMP_INFO.UP_DATE")
    public java.util.Date getUP_DATE() {
        return UP_DATE;
    }
    /**
     * @param UP_DATE 
     */
    public void setUP_DATE(java.util.Date UP_DATE) {
        this.UP_DATE = UP_DATE;
    }
    
    /**
     * @return ADVANCE 
     */
    @Column(name = "ADVANCE")
    @Config(key = "N_EMP_INFO.ADVANCE")
    public java.lang.Long getADVANCE() {
        return ADVANCE;
    }
    /**
     * @param ADVANCE 
     */
    public void setADVANCE(java.lang.Long ADVANCE) {
        this.ADVANCE = ADVANCE;
    }
}
