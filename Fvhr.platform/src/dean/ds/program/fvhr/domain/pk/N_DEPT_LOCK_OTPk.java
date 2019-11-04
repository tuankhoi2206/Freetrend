package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_DEPT_LOCK_OTPk implements Serializable {
    private java.util.Date DATES;
    private java.lang.String DEPSN;
    public N_DEPT_LOCK_OTPk() {
        super();
    }
    public N_DEPT_LOCK_OTPk(java.util.Date DATES, java.lang.String DEPSN) {
        super();
        this.DATES = DATES;
        this.DEPSN = DEPSN;
    }

    /**
     * @returnDATES 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATES")
    public java.util.Date getDATES() {
        return DATES;
    }
    /**
     * @param DATES 
     */
    public void setDATES(java.util.Date DATES) {
        this.DATES = DATES;
    }
    /**
     * @returnDEPSN 
     */
    @NotBlank
    @Column(name = "DEPSN", length = 5)
    public java.lang.String getDEPSN() {
        return DEPSN;
    }
    /**
     * @param DEPSN 
     */
    public void setDEPSN(java.lang.String DEPSN) {
        this.DEPSN = DEPSN;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_DEPT_LOCK_OTPk))
            return false;
        N_DEPT_LOCK_OTPk castOther = (N_DEPT_LOCK_OTPk) other;
        return new EqualsBuilder().append(DATES, castOther.DATES).append(DEPSN, castOther.DEPSN).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(DATES).append(DEPSN).toHashCode();
    }
}
