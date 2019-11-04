package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_TRAINING_DETAILPk implements Serializable {
    private java.lang.String EMPSN;
    private java.lang.String ID_TRAINING;	//SO THE CONG VOI SO LAN DUOC HUAN LUYEN
    public N_TRAINING_DETAILPk() {
        super();
    }
    public N_TRAINING_DETAILPk(java.lang.String EMPSN, java.lang.String ID_TRAINING) {
        super();
        this.EMPSN = EMPSN;
        this.ID_TRAINING = ID_TRAINING;
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
     * 取得SO THE CONG VOI SO LAN DUOC HUAN LUYEN
     * @returnID_TRAINING SO THE CONG VOI SO LAN DUOC HUAN LUYEN
     */
    @NotBlank
    @Column(name = "ID_TRAINING", length = 12)
    public java.lang.String getID_TRAINING() {
        return ID_TRAINING;
    }
    /**
     * 設定SO THE CONG VOI SO LAN DUOC HUAN LUYEN
     * @param ID_TRAINING SO THE CONG VOI SO LAN DUOC HUAN LUYEN
     */
    public void setID_TRAINING(java.lang.String ID_TRAINING) {
        this.ID_TRAINING = ID_TRAINING;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_TRAINING_DETAILPk))
            return false;
        N_TRAINING_DETAILPk castOther = (N_TRAINING_DETAILPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(ID_TRAINING, castOther.ID_TRAINING).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(ID_TRAINING).toHashCode();
    }
}
