package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_EMP_ADVANCEPk implements Serializable {
    private java.lang.String EMPSN;
    private java.util.Date DATE_EFFECT;
    public N_EMP_ADVANCEPk() {
        super();
    }
    public N_EMP_ADVANCEPk(java.lang.String EMPSN, java.util.Date DATE_EFFECT) {
        super();
        this.EMPSN = EMPSN;
        this.DATE_EFFECT = DATE_EFFECT;
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
     * @returnDATE_EFFECT 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_EFFECT")
    public java.util.Date getDATE_EFFECT() {
        return DATE_EFFECT;
    }
    /**
     * @param DATE_EFFECT 
     */
    public void setDATE_EFFECT(java.util.Date DATE_EFFECT) {
        this.DATE_EFFECT = DATE_EFFECT;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_EMP_ADVANCEPk))
            return false;
        N_EMP_ADVANCEPk castOther = (N_EMP_ADVANCEPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(DATE_EFFECT, castOther.DATE_EFFECT).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(DATE_EFFECT).toHashCode();
    }
}
