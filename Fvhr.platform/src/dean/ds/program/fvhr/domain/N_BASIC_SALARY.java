package ds.program.fvhr.domain;

import java.util.Date;

import javax.persistence.IdClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import ds.program.fvhr.domain.pk.N_BASIC_SALARYPk;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* LUONG CAN BAN
**/
@IdClass(N_BASIC_SALARYPk.class)
@Entity
@Table(name = "N_BASIC_SALARY")
public class N_BASIC_SALARY {
    private java.lang.String EMPSN;	//SO THE DEO
    private java.util.Date DATES;	//NGAY THAY DOI
    private java.lang.Long BSALARY;	//LUONG CB
    private java.lang.Long KEYS;	//KHOA
    private java.lang.String NOTE;	//GHI CHU
    private java.util.Date END_DATE;	//NGAY KET THUC
    private String USER_UP;
    private Date	DATE_UP;
    /**
     * SO THE DEO
     * @return EMPSN SO THE DEO
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_BASIC_SALARY.EMPSN")
    public java.lang.String getEMPSN() {
        return EMPSN;
    }
    /**
     * 設定SO THE DEO
     * @param EMPSN SO THE DEO
     */
    public void setEMPSN(java.lang.String EMPSN) {
        this.EMPSN = EMPSN;
    }
    /**
     * NGAY THAY DOI
     * @return DATES NGAY THAY DOI
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATES")
    @Config(key = "N_BASIC_SALARY.DATES")
    public java.util.Date getDATES() {
        return DATES;
    }
    /**
     * 設定NGAY THAY DOI
     * @param DATES NGAY THAY DOI
     */
    public void setDATES(java.util.Date DATES) {
        this.DATES = DATES;
    }
    /**
     * LUONG CB
     * @return BSALARY LUONG CB
     */
    @Column(name = "BSALARY")
    @Config(key = "N_BASIC_SALARY.BSALARY")
    public java.lang.Long getBSALARY() {
        return BSALARY;
    }
    /**
     * 設定LUONG CB
     * @param BSALARY LUONG CB
     */
    public void setBSALARY(java.lang.Long BSALARY) {
        this.BSALARY = BSALARY;
    }
    /**
     * KHOA
     * @return KEYS KHOA
     */
    @Column(name = "KEYS")
    @Config(key = "N_BASIC_SALARY.KEYS")
    public java.lang.Long getKEYS() {
        return KEYS;
    }
    /**
     * 設定KHOA
     * @param KEYS KHOA
     */
    public void setKEYS(java.lang.Long KEYS) {
        this.KEYS = KEYS;
    }
    /**
     * GHI CHU
     * @return NOTE GHI CHU
     */
    @Length(max = 150)
    @Column(name = "NOTE")
    @Config(key = "N_BASIC_SALARY.NOTE")
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
     * NGAY KET THUC
     * @return END_DATE NGAY KET THUC
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "END_DATE")
    @Config(key = "N_BASIC_SALARY.END_DATE")
    public java.util.Date getEND_DATE() {
        return END_DATE;
    }
    /**
     * 設定NGAY KET THUC
     * @param END_DATE NGAY KET THUC
     */
    public void setEND_DATE(java.util.Date END_DATE) {
        this.END_DATE = END_DATE;
    }
    
    /**
     * @return USER_UP 
     */
    @Length(max = 50)
    @Column(name = "USER_UP")
    @Config(key = "N_BASIC_SALARY.USER_UP")
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
    @Config(key = "N_BASIC_SALARY.DATE_UP")
    public java.util.Date getDATE_UP() {
        return DATE_UP;
    }
    /**
     * @param DATE_UP 
     */
    public void setDATE_UP(java.util.Date DATE_UP) {
        this.DATE_UP = DATE_UP;
    }
    
    
}
