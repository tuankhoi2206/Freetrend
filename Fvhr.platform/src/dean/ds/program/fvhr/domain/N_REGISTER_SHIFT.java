package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_REGISTER_SHIFTPk;
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
@IdClass(N_REGISTER_SHIFTPk.class)
@Entity
@Table(name = "N_REGISTER_SHIFT")
public class N_REGISTER_SHIFT {
    private java.lang.String EMPSN;
    private java.util.Date SHIFT_DATE;
    private java.lang.String TIME_IN;
    private java.lang.String TIME_OUT;
    private java.lang.String ID_SPDEPT;
    private java.lang.String NOTE;
    private java.lang.Long OT_7H;
    private java.lang.String ID_SHIFT;
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_REGISTER_SHIFT.EMPSN")
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
     * @return SHIFT_DATE 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "SHIFT_DATE")
    @Config(key = "N_REGISTER_SHIFT.SHIFT_DATE")
    public java.util.Date getSHIFT_DATE() {
        return SHIFT_DATE;
    }
    /**
     * @param SHIFT_DATE 
     */
    public void setSHIFT_DATE(java.util.Date SHIFT_DATE) {
        this.SHIFT_DATE = SHIFT_DATE;
    }
    /**
     * @return TIME_IN 
     */
    @Length(max = 5)
    @Column(name = "TIME_IN")
    @Config(key = "N_REGISTER_SHIFT.TIME_IN")
    public java.lang.String getTIME_IN() {
        return TIME_IN;
    }
    /**
     * @param TIME_IN 
     */
    public void setTIME_IN(java.lang.String TIME_IN) {
        this.TIME_IN = TIME_IN;
    }
    /**
     * @return TIME_OUT 
     */
    @Length(max = 5)
    @Column(name = "TIME_OUT")
    @Config(key = "N_REGISTER_SHIFT.TIME_OUT")
    public java.lang.String getTIME_OUT() {
        return TIME_OUT;
    }
    /**
     * @param TIME_OUT 
     */
    public void setTIME_OUT(java.lang.String TIME_OUT) {
        this.TIME_OUT = TIME_OUT;
    }
    /**
     * @return ID_SPDEPT 
     */
    @Length(max = 3)
    @Column(name = "ID_SPDEPT")
    @Config(key = "N_REGISTER_SHIFT.ID_SPDEPT")
    public java.lang.String getID_SPDEPT() {
        return ID_SPDEPT;
    }
    /**
     * @param ID_SPDEPT 
     */
    public void setID_SPDEPT(java.lang.String ID_SPDEPT) {
        this.ID_SPDEPT = ID_SPDEPT;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 50)
    @Column(name = "NOTE")
    @Config(key = "N_REGISTER_SHIFT.NOTE")
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
     * @return OT_7H 
     */
    @Column(name = "OT_7H")
    @Config(key = "N_REGISTER_SHIFT.OT_7H")
    public java.lang.Long getOT_7H() {
        return OT_7H;
    }
    /**
     * @param OT_7H 
     */
    public void setOT_7H(java.lang.Long OT_7H) {
        this.OT_7H = OT_7H;
    }
    /**
     * @return ID_SHIFT 
     */
    @Length(max = 5)
    @Column(name = "ID_SHIFT")
    @Config(key = "N_REGISTER_SHIFT.ID_SHIFT")
    public java.lang.String getID_SHIFT() {
        return ID_SHIFT;
    }
    /**
     * @param ID_SHIFT 
     */
    public void setID_SHIFT(java.lang.String ID_SHIFT) {
        this.ID_SHIFT = ID_SHIFT;
    }
}
