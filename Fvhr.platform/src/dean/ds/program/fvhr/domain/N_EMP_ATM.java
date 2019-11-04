package ds.program.fvhr.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@Entity
@Table(name = "N_EMP_ATM")
public class N_EMP_ATM {
    private java.lang.String EMPSN;
    private java.lang.String ATM_CODE;
    private java.util.Date NGAY;
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_EMP_ATM.EMPSN")
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
     * @return ATM_CODE 
     */
    @NotBlank
    @Column(name = "ATM_CODE")
    @Config(key = "N_EMP_ATM.ATM_CODE")
    public java.lang.String getATM_CODE() {
        return ATM_CODE;
    }
    /**
     * @param ATM_CODE 
     */
    public void setATM_CODE(java.lang.String ATM_CODE) {
        this.ATM_CODE = ATM_CODE;
    }
    /**
     * @return NGAY 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "NGAY")
    @Config(key = "N_EMP_ATM.NGAY")
    public java.util.Date getNGAY() {
        return NGAY;
    }
    /**
     * @param NGAY 
     */
    public void setNGAY(java.util.Date NGAY) {
        this.NGAY = NGAY;
    }
}
