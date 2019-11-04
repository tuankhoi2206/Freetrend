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
@Table(name = "N_JOB")
public class N_JOB {
    private java.lang.String ID_KEY;	//CODE + KIND + IN_FACT
    private java.lang.String NAME_JOB;
    private java.lang.String CODE_JOB;
    private java.lang.String KIND_JOB;
    private java.lang.String IN_FACT;
    private java.lang.String USER_UP;
    private java.util.Date DATE_UP;
    private java.math.BigDecimal MONEY;
    /**
     * 取得CODE + KIND + IN_FACT
     * @return ID_KEY CODE + KIND + IN_FACT
     */
    @Id
    @NotBlank
    @Column(name = "ID_KEY")
    @Config(key = "N_JOB.ID_KEY")
    public java.lang.String getID_KEY() {
        return ID_KEY;
    }
    /**
     * 設定CODE + KIND + IN_FACT
     * @param ID_KEY CODE + KIND + IN_FACT
     */
    public void setID_KEY(java.lang.String ID_KEY) {
        this.ID_KEY = ID_KEY;
    }
    /**
     * @return NAME_JOB 
     */
    @Length(max = 50)
    @Column(name = "NAME_JOB")
    @Config(key = "N_JOB.NAME_JOB")
    public java.lang.String getNAME_JOB() {
        return NAME_JOB;
    }
    /**
     * @param NAME_JOB 
     */
    public void setNAME_JOB(java.lang.String NAME_JOB) {
        this.NAME_JOB = NAME_JOB;
    }
    /**
     * @return CODE_JOB 
     */
    @NotBlank
    @Length(max = 50)
    @Column(name = "CODE_JOB")
    @Config(key = "N_JOB.CODE_JOB")
    public java.lang.String getCODE_JOB() {
        return CODE_JOB;
    }
    /**
     * @param CODE_JOB 
     */
    public void setCODE_JOB(java.lang.String CODE_JOB) {
        this.CODE_JOB = CODE_JOB;
    }
    /**
     * @return KIND_JOB 
     */
    @Length(max = 1)
    @Column(name = "KIND_JOB")
    @Config(key = "N_JOB.KIND_JOB")
    public java.lang.String getKIND_JOB() {
        return KIND_JOB;
    }
    /**
     * @param KIND_JOB 
     */
    public void setKIND_JOB(java.lang.String KIND_JOB) {
        this.KIND_JOB = KIND_JOB;
    }
    /**
     * @return IN_FACT 
     */
    @Length(max = 20)
    @Column(name = "IN_FACT")
    @Config(key = "N_JOB.IN_FACT")
    public java.lang.String getIN_FACT() {
        return IN_FACT;
    }
    /**
     * @param IN_FACT 
     */
    public void setIN_FACT(java.lang.String IN_FACT) {
        this.IN_FACT = IN_FACT;
    }
    /**
     * @return USER_UP 
     */
    @Length(max = 20)
    @Column(name = "USER_UP")
    @Config(key = "N_JOB.USER_UP")
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
    @Config(key = "N_JOB.DATE_UP")
    public java.util.Date getDATE_UP() {
        return DATE_UP;
    }
    /**
     * @param DATE_UP 
     */
    public void setDATE_UP(java.util.Date DATE_UP) {
        this.DATE_UP = DATE_UP;
    }
    /**
     * @return MONEY 
     */
    @Column(name = "MONEY")
    @Config(key = "N_JOB.MONEY")
    public java.math.BigDecimal getMONEY() {
        return MONEY;
    }
    /**
     * @param MONEY 
     */
    public void setMONEY(java.math.BigDecimal MONEY) {
        this.MONEY = MONEY;
    }
}
