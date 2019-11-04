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
@Table(name = "N_JOB_LIST")
public class N_JOB_LIST {
    private java.lang.String ID_KEY;
    private java.lang.String ID_JOB;
    private java.lang.String KIND;
    private java.lang.Long MONEY;
    private java.util.Date DATE_B;
    private java.util.Date DATE_E;
    private java.lang.String USER_UP;
    private java.util.Date DATE_UP;
    /**
     * @return ID_KEY 
     */
    @Id
    @NotBlank
    @Column(name = "ID_KEY")
    @Config(key = "N_JOB_LIST.ID_KEY")
    public java.lang.String getID_KEY() {
        return ID_KEY;
    }
    /**
     * @param ID_KEY 
     */
    public void setID_KEY(java.lang.String ID_KEY) {
        this.ID_KEY = ID_KEY;
    }
    /**
     * @return ID_JOB 
     */
    @NotBlank
    @Length(max = 50)
    @Column(name = "ID_JOB")
    @Config(key = "N_JOB_LIST.ID_JOB")
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
     * @return KIND 
     */
    @NotBlank
    @Length(max = 1)
    @Column(name = "KIND")
    @Config(key = "N_JOB_LIST.KIND")
    public java.lang.String getKIND() {
        return KIND;
    }
    /**
     * @param KIND 
     */
    public void setKIND(java.lang.String KIND) {
        this.KIND = KIND;
    }
    /**
     * @return MONEY 
     */
    @Column(name = "MONEY")
    @Config(key = "N_JOB_LIST.MONEY")
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
     * @return DATE_B 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_B")
    @Config(key = "N_JOB_LIST.DATE_B")
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
    @Config(key = "N_JOB_LIST.DATE_E")
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
     * @return USER_UP 
     */
    @Length(max = 50)
    @Column(name = "USER_UP")
    @Config(key = "N_JOB_LIST.USER_UP")
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
    @Config(key = "N_JOB_LIST.DATE_UP")
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
