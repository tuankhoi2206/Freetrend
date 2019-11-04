package ds.program.fvhr.domain;

import javax.persistence.IdClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import ds.program.fvhr.domain.pk.N_EALRY_AFTER_BPk;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@IdClass(N_EALRY_AFTER_BPk.class)
@Entity
@Table(name = "N_EALRY_AFTER_B")
public class N_EALRY_AFTER_B {
    private java.lang.String EMPSN;
    private java.util.Date BB_DATES;
    private java.util.Date EE_DATES;
    private java.lang.String KIND;
    private java.lang.String KEY;
    private java.lang.String GHICHU;
    private java.util.Date DATE_BEARS;	//NGAY SINH E BE
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_EALRY_AFTER_B.EMPSN")
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
     * @return BB_DATES 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "B_DATES")
    @Config(key = "N_EALRY_AFTER_B.B_DATES")
    public java.util.Date getBB_DATES() {
        return BB_DATES;
    }
    /**
     * @param BB_DATES 
     */
    public void setBB_DATES(java.util.Date BB_DATES) {
        this.BB_DATES = BB_DATES;
    }
    /**
     * @return EE_DATES 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "E_DATES")
    @Config(key = "N_EALRY_AFTER_B.E_DATES")
    public java.util.Date getEE_DATES() {
        return EE_DATES;
    }
    /**
     * @param EE_DATES 
     */
    public void setEE_DATES(java.util.Date EE_DATES) {
        this.EE_DATES = EE_DATES;
    }
    /**
     * @return KIND 
     */
    @Length(max = 50)
    @Column(name = "KIND")
    @Config(key = "N_EALRY_AFTER_B.KIND")
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
     * @return KEY 
     */
    @Length(max = 1)
    @Column(name = "KEY")
    @Config(key = "N_EALRY_AFTER_B.KEY")
    public java.lang.String getKEY() {
        return KEY;
    }
    /**
     * @param KEY 
     */
    public void setKEY(java.lang.String KEY) {
        this.KEY = KEY;
    }
    /**
     * @return GHICHU 
     */
    @Length(max = 100)
    @Column(name = "GHICHU")
    @Config(key = "N_EALRY_AFTER_B.GHICHU")
    public java.lang.String getGHICHU() {
        return GHICHU;
    }
    /**
     * @param GHICHU 
     */
    public void setGHICHU(java.lang.String GHICHU) {
        this.GHICHU = GHICHU;
    }
    /**
     * NGAY SINH E BE
     * @return DATE_BEARS NGAY SINH E BE
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_BEARS")
    @Config(key = "N_EALRY_AFTER_B.DATE_BEARS")
    public java.util.Date getDATE_BEARS() {
        return DATE_BEARS;
    }
    /**
     * 設定NGAY SINH E BE
     * @param DATE_BEARS NGAY SINH E BE
     */
    public void setDATE_BEARS(java.util.Date DATE_BEARS) {
        this.DATE_BEARS = DATE_BEARS;
    }
}
