package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_RESTPk implements Serializable {
    private java.lang.String EMPSN;
    private java.lang.String YEAR;
    public N_RESTPk() {
        super();
    }
    public N_RESTPk(java.lang.String EMPSN, java.lang.String YEAR) {
        super();
        this.EMPSN = EMPSN;
        this.YEAR = YEAR;
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
     * @returnYEAR 
     */
    @NotBlank
    @Column(name = "YEAR", length = 4)
    public java.lang.String getYEAR() {
        return YEAR;
    }
    /**
     * @param YEAR 
     */
    public void setYEAR(java.lang.String YEAR) {
        this.YEAR = YEAR;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_RESTPk))
            return false;
        N_RESTPk castOther = (N_RESTPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(YEAR, castOther.YEAR).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(YEAR).toHashCode();
    }
}
