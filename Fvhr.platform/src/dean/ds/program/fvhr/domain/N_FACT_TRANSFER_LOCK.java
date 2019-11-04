package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_FACT_TRANSFER_LOCKPk;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@IdClass(ds.program.fvhr.domain.pk.N_FACT_TRANSFER_LOCKPk.class)
@Entity
@Table(name = "N_FACT_TRANSFER_LOCK")
public class N_FACT_TRANSFER_LOCK {
    private java.lang.String DEPSN;
    private java.lang.String YEAR;
    private java.lang.String MONTH;
    private java.lang.Long LOCKED;
    /**
     * @return DEPSN 
     */
    @Id
    @NotBlank
    @Column(name = "DEPSN")
    @Config(key = "N_FACT_TRANSFER_LOCK.DEPSN")
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
     * @return YEAR 
     */
    @Id
    @NotBlank
    @Column(name = "YEAR")
    @Config(key = "N_FACT_TRANSFER_LOCK.YEAR")
    public java.lang.String getYEAR() {
        return YEAR;
    }
    /**
     * @param YEAR 
     */
    public void setYEAR(java.lang.String YEAR) {
        this.YEAR = YEAR;
    }
    /**
     * @return MONTH 
     */
    @Id
    @NotBlank
    @Column(name = "MONTH")
    @Config(key = "N_FACT_TRANSFER_LOCK.MONTH")
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
     * @return LOCKED 
     */
    @Column(name = "LOCKED")
    @Config(key = "N_FACT_TRANSFER_LOCK.LOCKED")
    public java.lang.Long getLOCKED() {
        return LOCKED;
    }
    /**
     * @param LOCKED 
     */
    public void setLOCKED(java.lang.Long LOCKED) {
        this.LOCKED = LOCKED;
    }
}
