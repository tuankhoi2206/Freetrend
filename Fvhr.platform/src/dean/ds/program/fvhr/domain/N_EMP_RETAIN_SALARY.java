package ds.program.fvhr.domain;

import javax.persistence.IdClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import ds.program.fvhr.domain.pk.N_EMP_RETAIN_SALARYPk;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* DANH SACH CO GIU LAI LUONG THANG TRUOC(NGAN)
**/
@IdClass(N_EMP_RETAIN_SALARYPk.class)
@Entity
@Table(name = "N_EMP_RETAIN_SALARY")
public class N_EMP_RETAIN_SALARY {
    private java.lang.String EMPSN;
    private java.lang.Long RETAIN_SALARY;	//1: true ,  0 : flase
    private java.util.Date DATE_EFFECT;	//thang se lay luong thang truoc tinh de tru BHYT
    private java.lang.String NOTE;
    private java.lang.String USER_UP;
    private java.util.Date DATE_UP;
    private java.lang.Long RE_SALARY; 
    private java.lang.String 	DEPSN;
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_EMP_RETAIN_SALARY.EMPSN")
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
     * �?�得1: true ,  0 : flase
     * @return RETAIN_SALARY 1: true ,  0 : flase
     */
    @Column(name = "RETAIN_SALARY")
    @Config(key = "N_EMP_RETAIN_SALARY.RETAIN_SALARY")
    public java.lang.Long getRETAIN_SALARY() {
        return RETAIN_SALARY;
    }
    /**
     * 設定1: true ,  0 : flase
     * @param RETAIN_SALARY 1: true ,  0 : flase
     */
    public void setRETAIN_SALARY(java.lang.Long RETAIN_SALARY) {
        this.RETAIN_SALARY = RETAIN_SALARY;
    }
    /**
     * �?�得thang se lay luong thang truoc tinh de tru BHYT
     * @return DATE_EFFECT thang se lay luong thang truoc tinh de tru BHYT
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_EFFECT")
    @Config(key = "N_EMP_RETAIN_SALARY.DATE_EFFECT")
    public java.util.Date getDATE_EFFECT() {
        return DATE_EFFECT;
    }
    /**
     * 設定thang se lay luong thang truoc tinh de tru BHYT
     * @param DATE_EFFECT thang se lay luong thang truoc tinh de tru BHYT
     */
    public void setDATE_EFFECT(java.util.Date DATE_EFFECT) {
        this.DATE_EFFECT = DATE_EFFECT;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 50)
    @Column(name = "NOTE")
    @Config(key = "N_EMP_RETAIN_SALARY.NOTE")
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
     * @return USER_UP 
     */
    @Length(max = 20)
    @Column(name = "USER_UP")
    @Config(key = "N_EMP_RETAIN_SALARY.USER_UP")
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
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_UP")
    @Config(key = "N_EMP_RETAIN_SALARY.DATE_UP")
    public java.util.Date getDATE_UP() {
        return DATE_UP;
    }
    /**
     * @param DATE_UP 
     */
    public void setDATE_UP(java.util.Date DATE_UP) {
        this.DATE_UP = DATE_UP;
    }
    /** KTOAN TU NHAP GIA TRI VAO*/
    @Column(name = "RE_SALARY")
    @Config(key = "N_EMP_RETAIN_SALARY.RE_SALARY")
    public java.lang.Long getRE_SALARY() {
        return RE_SALARY;
    }
    
    public void setRE_SALARY(java.lang.Long RE_SALARY) {
        this.RE_SALARY = RE_SALARY;
    }
    
    /**
     * @return DEPSN
     */
    @Id
    @NotBlank
    @Column(name = "DEPSN")
    @Config(key = "N_EMP_RETAIN_SALARY.DEPSN")
    public java.lang.String getDEPSN() {
        return DEPSN;
    }
    /**
     * @param EMPSN 
     */
    public void setDEPSN(java.lang.String DEPSN) {
        this.DEPSN = DEPSN;
    }
}
