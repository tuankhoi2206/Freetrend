package ds.program.fvhr.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;

import org.hibernate.validator.Length;

import dsc.echo2app.program.Config;
/**
* 
**/
@Entity
@Table(name = "N_HEALTH_REPORT")
public class N_HEALTH_REPORT {
    private java.lang.String EMPSN;
    private java.lang.Long STATUS;
    private java.util.Date DATE_B;
    private java.util.Date DATE_E;
    private java.lang.Long NUM;
    private java.lang.Long DEBT;
    private java.lang.Long MONEY;
    private java.lang.Long SALARY_B;
    private java.util.Date DATE_E_NS;
    private java.util.Date DATE_LOCK;
    private java.util.Date DATE_LOCK_1;
    private java.lang.Long DEBT_ROLL_MONTH;
   
    private String NOTE;
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_HEALTH_REPORT.EMPSN")
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
     * @return STATUS 
     */
    @Column(name = "STATUS")
    @Config(key = "N_HEALTH_REPORT.STATUS")
    public java.lang.Long getSTATUS() {
        return STATUS;
    }
    /**
     * @param STATUS 
     */
    public void setSTATUS(java.lang.Long STATUS) {
        this.STATUS = STATUS;
    }
    /**
     * @return DATE_B 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_B")
    @Config(key = "N_HEALTH_REPORT.DATE_B")
    public java.util.Date getDATE_B() {
        return DATE_B;
    }
    /**
     * @param DATE_B 
     */
    public void setDATE_B(java.util.Date DATE_B) {
        this.DATE_B = DATE_B;
    }
    /**
     * @return DATE_E 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_E")
    @Config(key = "N_HEALTH_REPORT.DATE_E")
    public java.util.Date getDATE_E() {
        return DATE_E;
    }
    /**
     * @param DATE_E 
     */
    public void setDATE_E(java.util.Date DATE_E) {
        this.DATE_E = DATE_E;
    }
    /**
     * @return NUM 
     */
    @Column(name = "NUM")
    @Config(key = "N_HEALTH_REPORT.NUM")
    public java.lang.Long getNUM() {
        return NUM;
    }
    /**
     * @param NUM 
     */
    public void setNUM(java.lang.Long NUM) {
        this.NUM = NUM;
    }
    /**
     * @return DEBT 
     */
    @Column(name = "DEBT")
    @Config(key = "N_HEALTH_REPORT.DEBT")
    public java.lang.Long getDEBT() {
        return DEBT;
    }
    /**
     * @param DEBT 
     */
    public void setDEBT(java.lang.Long DEBT) {
        this.DEBT = DEBT;
    }
    /**
     * @return MONEY 
     */
    @Column(name = "MONEY")
    @Config(key = "N_HEALTH_REPORT.MONEY")
    public java.lang.Long getMONEY() {
        return MONEY;
    }
    /**
     * @param MONEY 
     */
    public void setMONEY(java.lang.Long MONEY) {
        this.MONEY = MONEY;
    }
    /**
     * @return SALARY_B 
     */
    @Column(name = "SALARY_B")
    @Config(key = "N_HEALTH_REPORT.SALARY_B")
    public java.lang.Long getSALARY_B() {
        return SALARY_B;
    }
    /**
     * @param SALARY_B 
     */
    public void setSALARY_B(java.lang.Long SALARY_B) {
        this.SALARY_B = SALARY_B;
    }
    /**
     * @return DATE_E_NS 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_E_NS")
    @Config(key = "N_HEALTH_REPORT.DATE_E_NS")
    public java.util.Date getDATE_E_NS() {
        return DATE_E_NS;
    }
    /**
     * @param DATE_E_NS 
     */
    public void setDATE_E_NS(java.util.Date DATE_E_NS) {
        this.DATE_E_NS = DATE_E_NS;
    }
    /**
     * @return DATE_LOCK 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_LOCK")
    @Config(key = "N_HEALTH_REPORT.DATE_LOCK")
    public java.util.Date getDATE_LOCK() {
        return DATE_LOCK;
    }
    /**
     * @param DATE_LOCK 
     */
    public void setDATE_LOCK(java.util.Date DATE_LOCK) {
        this.DATE_LOCK = DATE_LOCK;
    }
    
    /**
     * @return NOTE 
     */
    @Length(max = 2000)
    @Column(name = "NOTE")
    @Config(key = "N_HEALTH_REPORT.NOTE")
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
     * @return DATE_LOCK_1 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_LOCK_1")
    @Config(key = "N_HEALTH_REPORT.DATE_LOCK_1")
    public java.util.Date getDATE_LOCK_1() {
        return DATE_LOCK_1;
    }
    /**
     * @param DATE_LOCK_1 
     */
    public void setDATE_LOCK_1(java.util.Date DATE_LOCK_1) {
        this.DATE_LOCK_1 = DATE_LOCK_1;
    }
    
    /**
     * @return DEBT_ROLL_MONTH 
     */
    @Column(name = "DEBT_ROLL_MONTH")
    @Config(key = "N_HEALTH_REPORT.DEBT_ROLL_MONTH")
    public java.lang.Long getDEBT_ROLL_MONTH() {
        return DEBT_ROLL_MONTH;
    }
    /**
     * @param DEBT_ROLL_MONTH 
     */
    public void setDEBT_ROLL_MONTH(java.lang.Long DEBT_ROLL_MONTH) {
        this.DEBT_ROLL_MONTH = DEBT_ROLL_MONTH;
    }
    
}
