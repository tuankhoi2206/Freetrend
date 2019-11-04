package ds.program.fvhr.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@Entity
@Table(name = "N_EMP_NOW_TEST")
public class N_EMP_NOW_TEST {
    private java.lang.String EMPSN;
    private java.lang.Long STATUS_WORK;
    private java.lang.Long SALARY_BASIC;
    private java.lang.Long SALARY_TOTAL;
    private java.lang.Long WORKDAY;
    private java.util.Date BEAR_DATE_B;
    private java.util.Date REAL_OFF_DATE;
    private java.lang.Long STATUS_CARD;
    private java.util.Date RETURN_CARD_DATE;
    private java.lang.Long NUM_BEAR;
    private java.lang.String NOTE;
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_EMP_NOW_TEST.EMPSN")
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
     * @return STATUS_WORK 
     */
    @Column(name = "STATUS_WORK")
    @Config(key = "N_EMP_NOW_TEST.STATUS_WORK")
    public java.lang.Long getSTATUS_WORK() {
        return STATUS_WORK;
    }
    /**
     * @param STATUS_WORK 
     */
    public void setSTATUS_WORK(java.lang.Long STATUS_WORK) {
        this.STATUS_WORK = STATUS_WORK;
    }
    /**
     * @return SALARY_BASIC 
     */
    @Column(name = "SALARY_BASIC")
    @Config(key = "N_EMP_NOW_TEST.SALARY_BASIC")
    public java.lang.Long getSALARY_BASIC() {
        return SALARY_BASIC;
    }
    /**
     * @param SALARY_BASIC 
     */
    public void setSALARY_BASIC(java.lang.Long SALARY_BASIC) {
        this.SALARY_BASIC = SALARY_BASIC;
    }
    /**
     * @return SALARY_TOTAL 
     */
    @Column(name = "SALARY_TOTAL")
    @Config(key = "N_EMP_NOW_TEST.SALARY_TOTAL")
    public java.lang.Long getSALARY_TOTAL() {
        return SALARY_TOTAL;
    }
    /**
     * @param SALARY_TOTAL 
     */
    public void setSALARY_TOTAL(java.lang.Long SALARY_TOTAL) {
        this.SALARY_TOTAL = SALARY_TOTAL;
    }
    /**
     * @return WORKDAY 
     */
    @Column(name = "WORKDAY")
    @Config(key = "N_EMP_NOW_TEST.WORKDAY")
    public java.lang.Long getWORKDAY() {
        return WORKDAY;
    }
    /**
     * @param WORKDAY 
     */
    public void setWORKDAY(java.lang.Long WORKDAY) {
        this.WORKDAY = WORKDAY;
    }
    /**
     * @return BEAR_DATE_B 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "BEAR_DATE_B")
    @Config(key = "N_EMP_NOW_TEST.BEAR_DATE_B")
    public java.util.Date getBEAR_DATE_B() {
        return BEAR_DATE_B;
    }
    /**
     * @param BEAR_DATE_B 
     */
    public void setBEAR_DATE_B(java.util.Date BEAR_DATE_B) {
        this.BEAR_DATE_B = BEAR_DATE_B;
    }
    /**
     * @return REAL_OFF_DATE 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "REAL_OFF_DATE")
    @Config(key = "N_EMP_NOW_TEST.REAL_OFF_DATE")
    public java.util.Date getREAL_OFF_DATE() {
        return REAL_OFF_DATE;
    }
    /**
     * @param REAL_OFF_DATE 
     */
    public void setREAL_OFF_DATE(java.util.Date REAL_OFF_DATE) {
        this.REAL_OFF_DATE = REAL_OFF_DATE;
    }
    /**
     * @return STATUS_CARD 
     */
    @Column(name = "STATUS_CARD")
    @Config(key = "N_EMP_NOW_TEST.STATUS_CARD")
    public java.lang.Long getSTATUS_CARD() {
        return STATUS_CARD;
    }
    /**
     * @param STATUS_CARD 
     */
    public void setSTATUS_CARD(java.lang.Long STATUS_CARD) {
        this.STATUS_CARD = STATUS_CARD;
    }
    /**
     * @return RETURN_CARD_DATE 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "RETURN_CARD_DATE")
    @Config(key = "N_EMP_NOW_TEST.RETURN_CARD_DATE")
    public java.util.Date getRETURN_CARD_DATE() {
        return RETURN_CARD_DATE;
    }
    /**
     * @param RETURN_CARD_DATE 
     */
    public void setRETURN_CARD_DATE(java.util.Date RETURN_CARD_DATE) {
        this.RETURN_CARD_DATE = RETURN_CARD_DATE;
    }
    /**
     * @return NUM_BEAR 
     */
    @Column(name = "NUM_BEAR")
    @Config(key = "N_EMP_NOW_TEST.NUM_BEAR")
    public java.lang.Long getNUM_BEAR() {
        return NUM_BEAR;
    }
    /**
     * @param NUM_BEAR 
     */
    public void setNUM_BEAR(java.lang.Long NUM_BEAR) {
        this.NUM_BEAR = NUM_BEAR;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 2000)
    @Column(name = "NOTE")
    @Config(key = "N_EMP_NOW_TEST.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * @param NOTE 
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
}
