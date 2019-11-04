package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_REST_DETAILPk implements Serializable {
    private java.lang.String EMPSN;
    private java.util.Date DATE_OFF;
    public N_REST_DETAILPk() {
        super();
    }
    public N_REST_DETAILPk(java.lang.String EMPSN, java.util.Date DATE_OFF) {
        super();
        this.EMPSN = EMPSN;
        this.DATE_OFF = DATE_OFF;
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
     * @returnDATE_OFF 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OFF")
    public java.util.Date getDATE_OFF() {
        return DATE_OFF;
    }
    /**
     * @param DATE_OFF 
     */
    public void setDATE_OFF(java.util.Date DATE_OFF) {
        this.DATE_OFF = DATE_OFF;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_REST_DETAILPk))
            return false;
        N_REST_DETAILPk castOther = (N_REST_DETAILPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(DATE_OFF, castOther.DATE_OFF).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(DATE_OFF).toHashCode();
    }
}
