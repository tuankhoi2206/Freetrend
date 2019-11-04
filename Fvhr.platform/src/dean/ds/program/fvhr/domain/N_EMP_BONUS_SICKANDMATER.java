package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_EMP_BONUS_SICKANDMATERPk;
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
@IdClass(N_EMP_BONUS_SICKANDMATERPk.class)
@Entity
@Table(name = "N_EMP_BONUS_SICKANDMATER")
public class N_EMP_BONUS_SICKANDMATER {
    private java.lang.String EMPSN;
    private java.lang.Double MONEY;
    private java.util.Date THANG;
    private java.lang.String NOTE;
    private java.lang.String USER_UPDATED;
    private java.util.Date DATE_UPDATED;
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_EMP_BONUS_SICKANDMATER.EMPSN")
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
     * @return MONEY 
     */
    @NotBlank
    @Column(name = "MONEY")
    @Config(key = "N_EMP_BONUS_SICKANDMATER.MONEY")
    public java.lang.Double getMONEY() {
        return MONEY;
    }
    /**
     * @param MONEY 
     */
    public void setMONEY(java.lang.Double MONEY) {
        this.MONEY = MONEY;
    }
    /**
     * @return THANG 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "THANG")
    @Config(key = "N_EMP_BONUS_SICKANDMATER.THANG")
    public java.util.Date getTHANG() {
        return THANG;
    }
    /**
     * @param THANG 
     */
    public void setTHANG(java.util.Date THANG) {
        this.THANG = THANG;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 150)
    @Column(name = "NOTE")
    @Config(key = "N_EMP_BONUS_SICKANDMATER.NOTE")
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
     * @return USER_UPDATED 
     */
    @Length(max = 50)
    @Column(name = "USER_UPDATED")
    @Config(key = "N_EMP_BONUS_SICKANDMATER.USER_UPDATED")
    public java.lang.String getUSER_UPDATED() {
        return USER_UPDATED;
    }
    /**
     * @param USER_UPDATED 
     */
    public void setUSER_UPDATED(java.lang.String USER_UPDATED) {
        this.USER_UPDATED = USER_UPDATED;
    }
    /**
     * @return DATE_UPDATED 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_UPDATED")
    @Config(key = "N_EMP_BONUS_SICKANDMATER.DATE_UPDATED")
    public java.util.Date getDATE_UPDATED() {
        return DATE_UPDATED;
    }
    /**
     * @param DATE_UPDATED 
     */
    public void setDATE_UPDATED(java.util.Date DATE_UPDATED) {
        this.DATE_UPDATED = DATE_UPDATED;
    }
}
