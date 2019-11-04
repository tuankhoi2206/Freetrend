package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_JOBPk implements Serializable {
    private java.lang.String ID_JOB;
    private java.lang.String IN_FACT;
    public N_JOBPk() {
        super();
    }
    public N_JOBPk(java.lang.String ID_JOB, java.lang.String IN_FACT) {
        super();
        this.ID_JOB = ID_JOB;
        this.IN_FACT = IN_FACT;
    }

    /**
     * @returnID_JOB 
     */
    @NotBlank
    @Column(name = "ID_JOB", length = 20)
    public java.lang.String getID_JOB() {
        return ID_JOB;
    }
    /**
     * @param ID_JOB 
     */
    public void setID_JOB(java.lang.String ID_JOB) {
        this.ID_JOB = ID_JOB;
    }
    /**
     * @returnIN_FACT 
     */
    @NotBlank
    @Column(name = "IN_FACT", length = 20)
    public java.lang.String getIN_FACT() {
        return IN_FACT;
    }
    /**
     * @param IN_FACT 
     */
    public void setIN_FACT(java.lang.String IN_FACT) {
        this.IN_FACT = IN_FACT;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_JOBPk))
            return false;
        N_JOBPk castOther = (N_JOBPk) other;
        return new EqualsBuilder().append(ID_JOB, castOther.ID_JOB).append(IN_FACT, castOther.IN_FACT).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ID_JOB).append(IN_FACT).toHashCode();
    }
}
