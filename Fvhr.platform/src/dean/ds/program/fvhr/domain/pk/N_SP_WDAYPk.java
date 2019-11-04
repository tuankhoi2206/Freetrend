package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_SP_WDAYPk implements Serializable {
    private java.lang.String EMPSN;
    private java.util.Date DATE_SP;
    public N_SP_WDAYPk() {
        super();
    }
    public N_SP_WDAYPk(java.lang.String EMPSN, java.util.Date DATE_SP) {
        super();
        this.EMPSN = EMPSN;
        this.DATE_SP = DATE_SP;
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
     * @returnDATE_SP 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_SP")
    public java.util.Date getDATE_SP() {
        return DATE_SP;
    }
    /**
     * @param DATE_SP 
     */
    public void setDATE_SP(java.util.Date DATE_SP) {
        this.DATE_SP = DATE_SP;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_SP_WDAYPk))
            return false;
        N_SP_WDAYPk castOther = (N_SP_WDAYPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(DATE_SP, castOther.DATE_SP).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(DATE_SP).toHashCode();
    }
}
