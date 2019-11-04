package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_TIME_BEARPk implements Serializable {
    private java.lang.String EMPSN;
    private java.util.Date BB_DATES;
    public N_TIME_BEARPk() {
        super();
    }
    public N_TIME_BEARPk(java.lang.String EMPSN, java.util.Date BB_DATES) {
        super();
        this.EMPSN = EMPSN;
        this.BB_DATES = BB_DATES;
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
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_TIME_BEARPk))
            return false;
        N_TIME_BEARPk castOther = (N_TIME_BEARPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(BB_DATES, castOther.BB_DATES).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(BB_DATES).toHashCode();
    }
}
