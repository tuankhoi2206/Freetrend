package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_REGISTER_OVERTIMEPk implements Serializable {
    private java.lang.String EMPSN;
    private java.util.Date DATE_OVER;
    public N_REGISTER_OVERTIMEPk() {
        super();
    }
    public N_REGISTER_OVERTIMEPk(java.lang.String EMPSN, java.util.Date DATE_OVER) {
        super();
        this.EMPSN = EMPSN;
        this.DATE_OVER = DATE_OVER;
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
     * @returnDATE_OVER 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OVER")
    public java.util.Date getDATE_OVER() {
        return DATE_OVER;
    }
    /**
     * @param DATE_OVER 
     */
    public void setDATE_OVER(java.util.Date DATE_OVER) {
        this.DATE_OVER = DATE_OVER;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_REGISTER_OVERTIMEPk))
            return false;
        N_REGISTER_OVERTIMEPk castOther = (N_REGISTER_OVERTIMEPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(DATE_OVER, castOther.DATE_OVER).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(DATE_OVER).toHashCode();
    }
}
