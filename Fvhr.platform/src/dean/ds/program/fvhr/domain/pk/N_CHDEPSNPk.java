package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_CHDEPSNPk implements Serializable {
    private java.lang.String EMPSN;	//SO THE
    private java.util.Date DATES;	//NGAY DIEU DONG
    public N_CHDEPSNPk() {
        super();
    }
    public N_CHDEPSNPk(java.lang.String EMPSN, java.util.Date DATES) {
        super();
        this.EMPSN = EMPSN;
        this.DATES = DATES;
    }

    /**
     * 取得SO THE
     * @returnEMPSN SO THE
     */
    @NotBlank
    @Column(name = "EMPSN", length = 8)
    public java.lang.String getEMPSN() {
        return EMPSN;
    }
    /**
     * 設定SO THE
     * @param EMPSN SO THE
     */
    public void setEMPSN(java.lang.String EMPSN) {
        this.EMPSN = EMPSN;
    }
    /**
     * 取得NGAY DIEU DONG
     * @returnDATES NGAY DIEU DONG
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATES")
    public java.util.Date getDATES() {
        return DATES;
    }
    /**
     * 設定NGAY DIEU DONG
     * @param DATES NGAY DIEU DONG
     */
    public void setDATES(java.util.Date DATES) {
        this.DATES = DATES;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_CHDEPSNPk))
            return false;
        N_CHDEPSNPk castOther = (N_CHDEPSNPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(DATES, castOther.DATES).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(DATES).toHashCode();
    }
}
