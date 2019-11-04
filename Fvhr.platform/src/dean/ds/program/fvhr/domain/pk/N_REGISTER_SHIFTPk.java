package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_REGISTER_SHIFTPk implements Serializable {
    private java.lang.String EMPSN;
    private java.util.Date SHIFT_DATE;
    public N_REGISTER_SHIFTPk() {
        super();
    }
    public N_REGISTER_SHIFTPk(java.lang.String EMPSN, java.util.Date SHIFT_DATE) {
        super();
        this.EMPSN = EMPSN;
        this.SHIFT_DATE = SHIFT_DATE;
    }

    /**
     * @returnEMPSN 
     */
    @NotBlank
    @Column(name = "EMPSN", length = 8)
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
     * @returnSHIFT_DATE 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "SHIFT_DATE")
    public java.util.Date getSHIFT_DATE() {
        return SHIFT_DATE;
    }
    /**
     * @param SHIFT_DATE 
     */
    public void setSHIFT_DATE(java.util.Date SHIFT_DATE) {
        this.SHIFT_DATE = SHIFT_DATE;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_REGISTER_SHIFTPk))
            return false;
        N_REGISTER_SHIFTPk castOther = (N_REGISTER_SHIFTPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(SHIFT_DATE, castOther.SHIFT_DATE).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(SHIFT_DATE).toHashCode();
    }
}
