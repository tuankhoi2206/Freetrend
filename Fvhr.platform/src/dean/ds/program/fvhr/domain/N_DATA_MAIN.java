package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import ds.program.fvhr.domain.pk.N_DATA_MAINPk;
import dsc.util.hibernate.validator.NotBlank;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@IdClass(N_DATA_MAINPk.class)
@Entity
@Table(name = "N_DATA_MAIN")
public class N_DATA_MAIN {
    private java.lang.String EMPCN;
    private java.lang.String DATES;
    private java.lang.String TIMES;
    private java.lang.String LOCKED;
    private java.lang.String NOTE;
    private java.lang.String EMPSN;
    /**
     * @return EMPCN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPCN")
    @Config(key = "N_DATA_MAIN.EMPCN")
    public java.lang.String getEMPCN() {
        return EMPCN;
    }
    /**
     * @param EMPCN 
     */
    public void setEMPCN(java.lang.String EMPCN) {
        this.EMPCN = EMPCN;
    }
    /**
     * @return DATES 
     */
    @Id
    @NotBlank
    @Column(name = "DATES")
    @Config(key = "N_DATA_MAIN.DATES")
    public java.lang.String getDATES() {
        return DATES;
    }
    /**
     * @param DATES 
     */
    public void setDATES(java.lang.String DATES) {
        this.DATES = DATES;
    }
    /**
     * @return TIMES 
     */
    @Length(max = 400)
    @Column(name = "TIMES")
    @Config(key = "N_DATA_MAIN.TIMES")
    public java.lang.String getTIMES() {
        return TIMES;
    }
    /**
     * @param TIMES 
     */
    public void setTIMES(java.lang.String TIMES) {
        this.TIMES = TIMES;
    }
    /**
     * @return LOCKED 
     */
    @Length(max = 1)
    @Column(name = "LOCKED")
    @Config(key = "N_DATA_MAIN.LOCKED")
    public java.lang.String getLOCKED() {
        return LOCKED;
    }
    /**
     * @param LOCKED 
     */
    public void setLOCKED(java.lang.String LOCKED) {
        this.LOCKED = LOCKED;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 500)
    @Column(name = "NOTE")
    @Config(key = "N_DATA_MAIN.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * @param NOTE 
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
    
    @Length(max = 8)
    @Column(name = "EMPSN")
    @Config(key = "N_DATA_MAIN.EMPSN")
    public java.lang.String getEMPSN() {
        return EMPSN;
    }
    /**
     * @param NOTE 
     */
    public void setEMPSN(java.lang.String EMPSN) {
        this.EMPSN = EMPSN;
    }
}
