package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_SP_WDAYPk;
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
@IdClass(N_SP_WDAYPk.class)
@Entity
@Table(name = "N_SP_WDAY")
public class N_SP_WDAY {
    private java.lang.String EMPSN;
    private java.util.Date DATE_SP;
    private java.lang.Long MULTIPLY_WD;
    private java.lang.String NOTE;
    private java.lang.String USER_UPDATED;	//User  thao tac
    private java.util.Date DATE_UPDATED;	//Ngay thao tac
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_SP_WDAY.EMPSN")
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
     * @return DATE_SP 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_SP")
    @Config(key = "N_SP_WDAY.DATE_SP")
    public java.util.Date getDATE_SP() {
        return DATE_SP;
    }
    /**
     * @param DATE_SP 
     */
    public void setDATE_SP(java.util.Date DATE_SP) {
        this.DATE_SP = DATE_SP;
    }
    /**
     * @return MULTIPLY_WD 
     */
    @Column(name = "MULTIPLY_WD")
    @Config(key = "N_SP_WDAY.MULTIPLY_WD")
    public java.lang.Long getMULTIPLY_WD() {
        return MULTIPLY_WD;
    }
    /**
     * @param MULTIPLY_WD 
     */
    public void setMULTIPLY_WD(java.lang.Long MULTIPLY_WD) {
        this.MULTIPLY_WD = MULTIPLY_WD;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 50)
    @Column(name = "NOTE")
    @Config(key = "N_SP_WDAY.NOTE")
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
     * 取得User  thao tac
     * @return USER_UPDATED User  thao tac
     */
    @Length(max = 50)
    @Column(name = "USER_UPDATED")
    @Config(key = "N_SP_WDAY.USER_UPDATED")
    public java.lang.String getUSER_UPDATED() {
        return USER_UPDATED;
    }
    /**
     * 設定User  thao tac
     * @param USER_UPDATED User  thao tac
     */
    public void setUSER_UPDATED(java.lang.String USER_UPDATED) {
        this.USER_UPDATED = USER_UPDATED;
    }
    /**
     * 取得Ngay thao tac
     * @return DATE_UPDATED Ngay thao tac
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_UPDATED")
    @Config(key = "N_SP_WDAY.DATE_UPDATED")
    public java.util.Date getDATE_UPDATED() {
        return DATE_UPDATED;
    }
    /**
     * 設定Ngay thao tac
     * @param DATE_UPDATED Ngay thao tac
     */
    public void setDATE_UPDATED(java.util.Date DATE_UPDATED) {
        this.DATE_UPDATED = DATE_UPDATED;
    }
}
