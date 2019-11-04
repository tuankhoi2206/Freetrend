package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_EMP_ATMPk implements Serializable {
    private java.lang.String EMPSN;
    private java.lang.String ATM_CODE;
    public N_EMP_ATMPk() {
        super();
    }
    public N_EMP_ATMPk(java.lang.String EMPSN, java.lang.String ATM_CODE) {
        super();
        this.EMPSN = EMPSN;
        this.ATM_CODE = ATM_CODE;
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
     * @returnATM_CODE 
     */
    @NotBlank
    @Column(name = "ATM_CODE", length = 4)
    public java.lang.String getATM_CODE() {
        return ATM_CODE;
    }
    /**
     * @param ATM_CODE 
     */
    public void setATM_CODE(java.lang.String ATM_CODE) {
        this.ATM_CODE = ATM_CODE;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_EMP_ATMPk))
            return false;
        N_EMP_ATMPk castOther = (N_EMP_ATMPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(ATM_CODE, castOther.ATM_CODE).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(ATM_CODE).toHashCode();
    }
}
