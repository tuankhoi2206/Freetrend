package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_LOST_DATA_DETAILPk implements Serializable {
    private java.lang.String EMPSN;	//THE DEO
    private java.util.Date DATE_LOST;	//NGAY BI MAT DU LIEU
    public N_LOST_DATA_DETAILPk() {
        super();
    }
    public N_LOST_DATA_DETAILPk(java.lang.String EMPSN, java.util.Date DATE_LOST) {
        super();
        this.EMPSN = EMPSN;
        this.DATE_LOST = DATE_LOST;
    }

    /**
     * 取得THE DEO
     * @returnEMPSN THE DEO
     */
    @NotBlank
    @Column(name = "EMPSN", length = 8)
    public java.lang.String getEMPSN() {
        return EMPSN;
    }
    /**
     * 設定THE DEO
     * @param EMPSN THE DEO
     */
    public void setEMPSN(java.lang.String EMPSN) {
        this.EMPSN = EMPSN;
    }
    /**
     * 取得NGAY BI MAT DU LIEU
     * @returnDATE_LOST NGAY BI MAT DU LIEU
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_LOST")
    public java.util.Date getDATE_LOST() {
        return DATE_LOST;
    }
    /**
     * 設定NGAY BI MAT DU LIEU
     * @param DATE_LOST NGAY BI MAT DU LIEU
     */
    public void setDATE_LOST(java.util.Date DATE_LOST) {
        this.DATE_LOST = DATE_LOST;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_LOST_DATA_DETAILPk))
            return false;
        N_LOST_DATA_DETAILPk castOther = (N_LOST_DATA_DETAILPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(DATE_LOST, castOther.DATE_LOST).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(DATE_LOST).toHashCode();
    }
}
