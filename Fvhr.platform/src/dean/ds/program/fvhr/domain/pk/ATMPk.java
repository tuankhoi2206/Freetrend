package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class ATMPk implements Serializable {
    private java.lang.String CODE;
    private java.lang.String NN_FACT;
    public ATMPk() {
        super();
    }
    public ATMPk(java.lang.String CODE, java.lang.String NN_FACT) {
        super();
        this.CODE = CODE;
        this.NN_FACT = NN_FACT;
    }

    /**
     * @returnCODE 
     */
    @NotBlank
    @Column(name = "CODE", length = 4)
    public java.lang.String getCODE() {
        return CODE;
    }
    /**
     * @param CODE 
     */
    public void setCODE(java.lang.String CODE) {
        this.CODE = CODE;
    }
    /**
     * @returnNN_FACT 
     */
    @NotBlank
    @Column(name = "N_FACT", length = 30)
    public java.lang.String getNN_FACT() {
        return NN_FACT;
    }
    /**
     * @param NN_FACT 
     */
    public void setNN_FACT(java.lang.String NN_FACT) {
        this.NN_FACT = NN_FACT;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ATMPk))
            return false;
        ATMPk castOther = (ATMPk) other;
        return new EqualsBuilder().append(CODE, castOther.CODE).append(NN_FACT, castOther.NN_FACT).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(CODE).append(NN_FACT).toHashCode();
    }
}
