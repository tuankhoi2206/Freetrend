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
@Table(name = "N_LABOUR")
public class N_LABOUR {
    private java.lang.String EMPSN;
    private java.lang.String ID_LABOUR;
    private java.util.Date DATE_S;
    private java.lang.String LIMIT;
    private java.lang.String POSS;
    private java.lang.String DEPT;
    private java.lang.Long SALARY;
    private java.lang.String NOTE;
    private java.util.Date EXPIRE;
    private java.lang.String CLOCK;
    private java.lang.String JOBS;
    private java.lang.Long TIMES;
    private java.lang.String CHECKED;
    /**
     * @return EMPSN 
     */
    @NotBlank
    @Length(max = 8)
    @Column(name = "EMPSN")
    @Config(key = "N_LABOUR.EMPSN")
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
     * @return ID_LABOUR 
     */
    @Id
    @NotBlank
    @Column(name = "ID_LABOUR")
    @Config(key = "N_LABOUR.ID_LABOUR")
    public java.lang.String getID_LABOUR() {
        return ID_LABOUR;
    }
    /**
     * @param ID_LABOUR 
     */
    public void setID_LABOUR(java.lang.String ID_LABOUR) {
        this.ID_LABOUR = ID_LABOUR;
    }
    /**
     * @return DATE_S 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_S")
    @Config(key = "N_LABOUR.DATE_S")
    public java.util.Date getDATE_S() {
        return DATE_S;
    }
    /**
     * @param DATE_S 
     */
    public void setDATE_S(java.util.Date DATE_S) {
        this.DATE_S = DATE_S;
    }
    /**
     * @return LIMIT 
     */
    @Length(max = 30)
    @Column(name = "LIMIT")
    @Config(key = "N_LABOUR.LIMIT")
    public java.lang.String getLIMIT() {
        return LIMIT;
    }
    /**
     * @param LIMIT 
     */
    public void setLIMIT(java.lang.String LIMIT) {
        this.LIMIT = LIMIT;
    }
    /**
     * @return POSS 
     */
    @Length(max = 50)
    @Column(name = "POSS")
    @Config(key = "N_LABOUR.POSS")
    public java.lang.String getPOSS() {
        return POSS;
    }
    /**
     * @param POSS 
     */
    public void setPOSS(java.lang.String POSS) {
        this.POSS = POSS;
    }
    /**
     * @return DEPT 
     */
    @Length(max = 50)
    @Column(name = "DEPT")
    @Config(key = "N_LABOUR.DEPT")
    public java.lang.String getDEPT() {
        return DEPT;
    }
    /**
     * @param DEPT 
     */
    public void setDEPT(java.lang.String DEPT) {
        this.DEPT = DEPT;
    }
    /**
     * @return SALARY 
     */
    @Column(name = "SALARY")
    @Config(key = "N_LABOUR.SALARY")
    public java.lang.Long getSALARY() {
        return SALARY;
    }
    /**
     * @param SALARY 
     */
    public void setSALARY(java.lang.Long SALARY) {
        this.SALARY = SALARY;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 100)
    @Column(name = "NOTE")
    @Config(key = "N_LABOUR.NOTE")
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
     * @return EXPIRE 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "EXPIRE")
    @Config(key = "N_LABOUR.EXPIRE")
    public java.util.Date getEXPIRE() {
        return EXPIRE;
    }
    /**
     * @param EXPIRE 
     */
    public void setEXPIRE(java.util.Date EXPIRE) {
        this.EXPIRE = EXPIRE;
    }
    /**
     * @return CLOCK 
     */
    @Length(max = 1)
    @Column(name = "CLOCK")
    @Config(key = "N_LABOUR.CLOCK")
    public java.lang.String getCLOCK() {
        return CLOCK;
    }
    /**
     * @param CLOCK 
     */
    public void setCLOCK(java.lang.String CLOCK) {
        this.CLOCK = CLOCK;
    }
    /**
     * @return JOBS 
     */
    @Length(max = 50)
    @Column(name = "JOBS")
    @Config(key = "N_LABOUR.JOBS")
    public java.lang.String getJOBS() {
        return JOBS;
    }
    /**
     * @param JOBS 
     */
    public void setJOBS(java.lang.String JOBS) {
        this.JOBS = JOBS;
    }
    /**
     * @return TIMES 
     */
    @Column(name = "TIMES")
    @Config(key = "N_LABOUR.TIMES")
    public java.lang.Long getTIMES() {
        return TIMES;
    }
    /**
     * @param TIMES 
     */
    public void setTIMES(java.lang.Long TIMES) {
        this.TIMES = TIMES;
    }
    /**
     * @return CHECKED 
     */
    @Length(max = 1)
    @Column(name = "CHECKED")
    @Config(key = "N_LABOUR.CHECKED")
    public java.lang.String getCHECKED() {
        return CHECKED;
    }
    /**
     * @param CHECKED 
     */
    public void setCHECKED(java.lang.String CHECKED) {
        this.CHECKED = CHECKED;
    }
}
