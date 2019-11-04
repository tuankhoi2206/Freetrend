package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_POSS_DETAILPk implements Serializable {
    private java.lang.String ID_KEY;	//id in N_POSS
    private java.util.Date DATE_B;
    public N_POSS_DETAILPk() {
        super();
    }
    public N_POSS_DETAILPk(java.lang.String ID_KEY, java.util.Date DATE_B) {
        super();
        this.ID_KEY = ID_KEY;
        this.DATE_B = DATE_B;
    }

    /**
     * id in N_POSS
     * @returnID_KEY id in N_POSS
     */
    @NotBlank
    @Column(name = "ID_KEY", length = 50)
    public java.lang.String getID_KEY() {
        return ID_KEY;
    }
    /**
     * 設定id in N_POSS
     * @param ID_KEY id in N_POSS
     */
    public void setID_KEY(java.lang.String ID_KEY) {
        this.ID_KEY = ID_KEY;
    }
    /**
     * @returnDATE_B 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_B")
    public java.util.Date getDATE_B() {
        return DATE_B;
    }
    /**
     * @param DATE_B 
     */
    public void setDATE_B(java.util.Date DATE_B) {
        this.DATE_B = DATE_B;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_POSS_DETAILPk))
            return false;
        N_POSS_DETAILPk castOther = (N_POSS_DETAILPk) other;
        return new EqualsBuilder().append(ID_KEY, castOther.ID_KEY).append(DATE_B, castOther.DATE_B).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ID_KEY).append(DATE_B).toHashCode();
    }
}
