package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_EMP_BONUS_SICKANDMATERPk implements Serializable {
    private java.lang.String EMPSN;
    private java.util.Date THANG;
    public N_EMP_BONUS_SICKANDMATERPk() {
        super();
    }
    public N_EMP_BONUS_SICKANDMATERPk(java.lang.String EMPSN, java.util.Date THANG) {
        super();
        this.EMPSN = EMPSN;
        this.THANG = THANG;
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
     * @returnTHANG 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "THANG")
    public java.util.Date getTHANG() {
        return THANG;
    }
    /**
     * @param THANG 
     */
    public void setTHANG(java.util.Date THANG) {
        this.THANG = THANG;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_EMP_BONUS_SICKANDMATERPk))
            return false;
        N_EMP_BONUS_SICKANDMATERPk castOther = (N_EMP_BONUS_SICKANDMATERPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(THANG, castOther.THANG).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(THANG).toHashCode();
    }
}
