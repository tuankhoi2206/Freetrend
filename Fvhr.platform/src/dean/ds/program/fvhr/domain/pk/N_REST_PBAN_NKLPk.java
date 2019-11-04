package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_REST_PBAN_NKLPk implements Serializable {
    private java.lang.String EMPSN;
    private java.util.Date B_DATES;
    private java.util.Date E_DATES;
    public N_REST_PBAN_NKLPk() {
        super();
    }
    public N_REST_PBAN_NKLPk(java.lang.String EMPSN, java.util.Date BB_DATES, java.util.Date EE_DATES) {
        super();
        this.EMPSN = EMPSN;
        this.B_DATES = B_DATES;
        this.E_DATES = E_DATES;
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
     * @returnBB_DATES 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "B_DATES")
    public java.util.Date getB_DATES() {
        return B_DATES;
    }
    /**
     * @param BB_DATES 
     */
    public void setB_DATES(java.util.Date B_DATES) {
        this.B_DATES = B_DATES;
    }
    /**
     * @returnEE_DATES 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "E_DATES")
    public java.util.Date getE_DATES() {
        return E_DATES;
    }
    /**
     * @param EE_DATES 
     */
    public void setE_DATES(java.util.Date E_DATES) {
        this.E_DATES = E_DATES;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_REST_PBAN_NKLPk))
            return false;
        N_REST_PBAN_NKLPk castOther = (N_REST_PBAN_NKLPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(B_DATES, castOther.B_DATES).append(E_DATES, castOther.E_DATES).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(B_DATES).append(E_DATES).toHashCode();
    }
}
