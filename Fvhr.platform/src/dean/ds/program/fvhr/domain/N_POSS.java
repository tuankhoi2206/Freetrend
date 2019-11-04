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
@Table(name = "N_POSS")
public class N_POSS {
    private java.lang.String ID_KEY;	//CODE_POSS + LEVEL
    private java.lang.String NAME_POSS;
    private java.lang.String CODE_POSS;
    private java.lang.Long LEVEL_POSS;
    private java.lang.Long BONUS1;	//info from N_JOB_DETAIL
    private java.lang.Long BOUNS2;	//info from N_JOB_DETAIL
    private java.lang.String USER_UP;
    private java.util.Date DATE_UP;
    /**
     * CODE_POSS + LEVEL
     * @return ID_KEY CODE_POSS + LEVEL
     */
    @Id
    @NotBlank
    @Column(name = "ID_KEY")
    @Config(key = "N_POSS.ID_KEY")
    public java.lang.String getID_KEY() {
        return ID_KEY;
    }
    /**
     * 設定CODE_POSS + LEVEL
     * @param ID_KEY CODE_POSS + LEVEL
     */
    public void setID_KEY(java.lang.String ID_KEY) {
        this.ID_KEY = ID_KEY;
    }
    /**
     * @return NAME_POSS 
     */
    @Length(max = 50)
    @Column(name = "NAME_POSS")
    @Config(key = "N_POSS.NAME_POSS")
    public java.lang.String getNAME_POSS() {
        return NAME_POSS;
    }
    /**
     * @param NAME_POSS 
     */
    public void setNAME_POSS(java.lang.String NAME_POSS) {
        this.NAME_POSS = NAME_POSS;
    }
    /**
     * @return CODE_POSS 
     */
    @Length(max = 50)
    @Column(name = "CODE_POSS")
    @Config(key = "N_POSS.CODE_POSS")
    public java.lang.String getCODE_POSS() {
        return CODE_POSS;
    }
    /**
     * @param CODE_POSS 
     */
    public void setCODE_POSS(java.lang.String CODE_POSS) {
        this.CODE_POSS = CODE_POSS;
    }
    /**
     * @return LEVEL_POSS 
     */
    @Column(name = "LEVEL_POSS")
    @Config(key = "N_POSS.LEVEL_POSS")
    public java.lang.Long getLEVEL_POSS() {
        return LEVEL_POSS;
    }
    /**
     * @param LEVEL_POSS 
     */
    public void setLEVEL_POSS(java.lang.Long LEVEL_POSS) {
        this.LEVEL_POSS = LEVEL_POSS;
    }
    /**
     * info from N_JOB_DETAIL
     * @return BONUS1 info from N_JOB_DETAIL
     */
    @Column(name = "BONUS1")
    @Config(key = "N_POSS.BONUS1")
    public java.lang.Long getBONUS1() {
        return BONUS1;
    }
    /**
     * 設定info from N_JOB_DETAIL
     * @param BONUS1 info from N_JOB_DETAIL
     */
    public void setBONUS1(java.lang.Long BONUS1) {
        this.BONUS1 = BONUS1;
    }
    /**
     * �?�得info from N_JOB_DETAIL
     * @return BOUNS2 info from N_JOB_DETAIL
     */
    @Column(name = "BOUNS2")
    @Config(key = "N_POSS.BOUNS2")
    public java.lang.Long getBOUNS2() {
        return BOUNS2;
    }
    /**
     * 設定info from N_JOB_DETAIL
     * @param BOUNS2 info from N_JOB_DETAIL
     */
    public void setBOUNS2(java.lang.Long BOUNS2) {
        this.BOUNS2 = BOUNS2;
    }
    /**
     * @return USER_UP 
     */
    @Length(max = 20)
    @Column(name = "USER_UP")
    @Config(key = "N_POSS.USER_UP")
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
    @Config(key = "N_POSS.DATE_UP")
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
