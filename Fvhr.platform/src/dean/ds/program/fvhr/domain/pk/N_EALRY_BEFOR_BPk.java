package  ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_EALRY_BEFOR_BPk implements Serializable {
    private java.lang.String EMPSN;
    private java.util.Date BB_DATES;
    private java.util.Date EE_DATES;
    public N_EALRY_BEFOR_BPk() {
        super();
    }
    public N_EALRY_BEFOR_BPk(java.lang.String EMPSN, java.util.Date BB_DATES, java.util.Date EE_DATES) {
        super();
        this.EMPSN = EMPSN;
        this.BB_DATES = BB_DATES;
        this.EE_DATES = EE_DATES;
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
     * @returnEE_DATES 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "E_DATES")
    public java.util.Date getEE_DATES() {
        return EE_DATES;
    }
    /**
     * @param EE_DATES 
     */
    public void setEE_DATES(java.util.Date EE_DATES) {
        this.EE_DATES = EE_DATES;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_EALRY_BEFOR_BPk))
            return false;
        N_EALRY_BEFOR_BPk castOther = (N_EALRY_BEFOR_BPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(BB_DATES, castOther.BB_DATES).append(EE_DATES, castOther.EE_DATES).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(BB_DATES).append(EE_DATES).toHashCode();
    }
}
