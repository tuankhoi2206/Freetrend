package ds.program.fvhr.domain;

import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_EMP_ADVANCEPk;
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
@IdClass(N_EMP_ADVANCEPk.class)
@Entity
@Table(name = "N_EMP_ADVANCE")
public class N_EMP_ADVANCE {
    private java.lang.String EMPSN;
    private java.util.Date DATE_EFFECT;
    private java.lang.Long ADVANCE_MONEY;
    private java.lang.String NOTE;
    private java.lang.String DEPSN;
    private java.lang.String USER_UP;
    private java.util.Date DATE_UP;
 
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_EMP_ADVANCE.EMPSN")
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
     * @return DATE_EFFECT 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_EFFECT")
    @Config(key = "N_EMP_ADVANCE.DATE_EFFECT")
    public java.util.Date getDATE_EFFECT() {
        return DATE_EFFECT;
    }
    /**
     * @param DATE_EFFECT 
     */
    public void setDATE_EFFECT(java.util.Date DATE_EFFECT) {
        this.DATE_EFFECT = DATE_EFFECT;
    }
    /**
     * @return ADVANCE_MONEY 
     */
    @Column(name = "ADVANCE_MONEY")
    @Config(key = "N_EMP_ADVANCE.ADVANCE_MONEY")
    public java.lang.Long getADVANCE_MONEY() {
        return ADVANCE_MONEY;
    }
    /**
     * @param ADVANCE_MONEY 
     */
    public void setADVANCE_MONEY(java.lang.Long ADVANCE_MONEY) {
        this.ADVANCE_MONEY = ADVANCE_MONEY;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 500)
    @Column(name = "NOTE")
    @Config(key = "N_EMP_ADVANCE.NOTE")
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
     * @return DEPSN 
     */
    @Length(max = 5)
    @Column(name = "DEPSN")
    @Config(key = "N_EMP_ADVANCE.DEPSN")
    public java.lang.String getDEPSN() {
        return DEPSN;
    }
    /**
     * @param DEPSN 
     */
    public void setDEPSN(java.lang.String DEPSN) {
        this.DEPSN = DEPSN;
    }
    /**
     * @return USER_UP 
     */
    @Length(max = 50)
    @Column(name = "USER_UP")
    @Config(key = "N_EMP_ADVANCE.USER_UP")
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
    @Config(key = "N_EMP_ADVANCE.DATE_UP")
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
