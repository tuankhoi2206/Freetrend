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
@Table(name = "N_NIKE_CALENDAR")
public class N_NIKE_CALENDAR {
    private java.lang.String TIME_NO;
    private java.lang.Long YEAR;
    private java.lang.String MONTH;
    private java.lang.Long WEEK;
    private java.lang.String DATE_RANGE;
    private java.util.Date BEGIN_DATE;
    private java.util.Date END_DATE;
    /**
     * @return TIME_NO 
     */
    @Id
    @NotBlank
    @Column(name = "TIME_NO")
    @Config(key = "N_NIKE_CALENDAR.TIME_NO")
    public java.lang.String getTIME_NO() {
        return TIME_NO;
    }
    /**
     * @param TIME_NO 
     */
    public void setTIME_NO(java.lang.String TIME_NO) {
        this.TIME_NO = TIME_NO;
    }
    /**
     * @return YEAR 
     */
    @Column(name = "YEAR")
    @Config(key = "N_NIKE_CALENDAR.YEAR")
    public java.lang.Long getYEAR() {
        return YEAR;
    }
    /**
     * @param YEAR 
     */
    public void setYEAR(java.lang.Long YEAR) {
        this.YEAR = YEAR;
    }
    /**
     * @return MONTH 
     */
    @Length(max = 8)
    @Column(name = "MONTH")
    @Config(key = "N_NIKE_CALENDAR.MONTH")
    public java.lang.String getMONTH() {
        return MONTH;
    }
    /**
     * @param MONTH 
     */
    public void setMONTH(java.lang.String MONTH) {
        this.MONTH = MONTH;
    }
    /**
     * @return WEEK 
     */
    @Column(name = "WEEK")
    @Config(key = "N_NIKE_CALENDAR.WEEK")
    public java.lang.Long getWEEK() {
        return WEEK;
    }
    /**
     * @param WEEK 
     */
    public void setWEEK(java.lang.Long WEEK) {
        this.WEEK = WEEK;
    }
    /**
     * @return DATE_RANGE 
     */
    @Length(max = 100)
    @Column(name = "DATE_RANGE")
    @Config(key = "N_NIKE_CALENDAR.DATE_RANGE")
    public java.lang.String getDATE_RANGE() {
        return DATE_RANGE;
    }
    /**
     * @param DATE_RANGE 
     */
    public void setDATE_RANGE(java.lang.String DATE_RANGE) {
        this.DATE_RANGE = DATE_RANGE;
    }
    /**
     * @return BEGIN_DATE 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "BEGIN_DATE")
    @Config(key = "N_NIKE_CALENDAR.BEGIN_DATE")
    public java.util.Date getBEGIN_DATE() {
        return BEGIN_DATE;
    }
    /**
     * @param BEGIN_DATE 
     */
    public void setBEGIN_DATE(java.util.Date BEGIN_DATE) {
        this.BEGIN_DATE = BEGIN_DATE;
    }
    /**
     * @return END_DATE 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "END_DATE")
    @Config(key = "N_NIKE_CALENDAR.END_DATE")
    public java.util.Date getEND_DATE() {
        return END_DATE;
    }
    /**
     * @param END_DATE 
     */
    public void setEND_DATE(java.util.Date END_DATE) {
        this.END_DATE = END_DATE;
    }
}
