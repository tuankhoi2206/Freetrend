package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import ds.program.fvhr.domain.pk.N_EMP_BONUSPk;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@IdClass(N_EMP_BONUSPk.class)
@Entity
@Table(name = "N_EMP_BONUS")
public class N_EMP_BONUS {
    private java.lang.String 	EMPSN;
    private java.util.Date 		DATE_EFFECT;
    private java.lang.Double 	BONUS1;
    private java.lang.Double 	BONUS5;
    private java.lang.Double 	BONUS8;
    private java.lang.String 	USER_UP;
    private java.util.Date 		DATE_UP;
    private java.lang.String 	DEPSN;
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_EMP_BONUS.EMPSN")
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
    @Config(key = "N_EMP_BONUS.DATE_EFFECT")
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
     * @return BONUS1 
     */
    @Column(name = "BONUS1")
    @Config(key = "N_EMP_BONUS.BONUS1")
    public java.lang.Double getBONUS1() {
        return BONUS1;
    }
    /**
     * @param BONUS1 
     */
    public void setBONUS1(java.lang.Double BONUS1) {
        this.BONUS1 = BONUS1;
    }
    /**
     * @return BONUS5 
     */
    @Column(name = "BONUS5")
    @Config(key = "N_EMP_BONUS.BONUS5")
    public java.lang.Double getBONUS5() {
        return BONUS5;
    }
    /**
     * @param BONUS5 
     */
    public void setBONUS5(java.lang.Double BONUS5) {
        this.BONUS5 = BONUS5;
    }
    /**
     * @return BONUS8 
     */
    @Column(name = "BONUS8")
    @Config(key = "N_EMP_BONUS.BONUS8")
    public java.lang.Double getBONUS8() {
        return BONUS8;
    }
    /**
     * @param BONUS8 
     */
    public void setBONUS8(java.lang.Double BONUS8) {
        this.BONUS8 = BONUS8;
    }
    /**
     * @return USER_UP 
     */
    @Length(max = 50)
    @Column(name = "USER_UP")
    @Config(key = "N_EMP_BONUS.USER_UP")
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
    @Config(key = "N_EMP_BONUS.DATE_UP")
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
     * @return DEPSN
     */
    @Id
    @NotBlank
    @Column(name = "DEPSN")
    @Config(key = "N_EMP_BONUS.DEPSN")
    public java.lang.String getDEPSN() {
        return DEPSN;
    }
    /**
     * @param EMPSN 
     */
    public void setDEPSN(java.lang.String DEPSN) {
        this.DEPSN = DEPSN;
    }
    
}
