package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_POSS_SALARYPk implements Serializable {
    private java.lang.String ID_POSS;
    private java.lang.String LEVEL_POSS;
    public N_POSS_SALARYPk() {
        super();
    }
    public N_POSS_SALARYPk(java.lang.String ID_POSS, java.lang.String LEVEL_POSS) {
        super();
        this.ID_POSS = ID_POSS;
        this.LEVEL_POSS = LEVEL_POSS;
    }

    /**
     * @returnID_POSS 
     */
    @NotBlank
    @Column(name = "ID_POSS", length = 10)
    public java.lang.String getID_POSS() {
        return ID_POSS;
    }
    /**
     * @param ID_POSS 
     */
    public void setID_POSS(java.lang.String ID_POSS) {
        this.ID_POSS = ID_POSS;
    }
    /**
     * @returnLEVEL_POSS 
     */
    @NotBlank
    @Column(name = "LEVEL_POSS", length = 100)
    public java.lang.String getLEVEL_POSS() {
        return LEVEL_POSS;
    }
    /**
     * @param LEVEL_POSS 
     */
    public void setLEVEL_POSS(java.lang.String LEVEL_POSS) {
        this.LEVEL_POSS = LEVEL_POSS;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_POSS_SALARYPk))
            return false;
        N_POSS_SALARYPk castOther = (N_POSS_SALARYPk) other;
        return new EqualsBuilder().append(ID_POSS, castOther.ID_POSS).append(LEVEL_POSS, castOther.LEVEL_POSS).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ID_POSS).append(LEVEL_POSS).toHashCode();
    }
}
