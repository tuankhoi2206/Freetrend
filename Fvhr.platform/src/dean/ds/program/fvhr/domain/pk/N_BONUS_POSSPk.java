package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_BONUS_POSSPk implements Serializable {
    private java.lang.String EMPSN;	//SO THE DEO
    private java.util.Date DATES;	//NGAY PHU CAP
    public N_BONUS_POSSPk() {
        super();
    }
    public N_BONUS_POSSPk(java.lang.String EMPSN, java.util.Date DATES) {
        super();
        this.EMPSN = EMPSN;
        this.DATES = DATES;
    }

    /**
     * SO THE DEO
     * @returnEMPSN SO THE DEO
     */
    @NotBlank
    @Column(name = "EMPSN", length = 8)
    public java.lang.String getEMPSN() {
        return EMPSN;
    }
    /**
     * SO THE DEO
     * @param EMPSN SO THE DEO
     */
    public void setEMPSN(java.lang.String EMPSN) {
        this.EMPSN = EMPSN;
    }
    /**
     * NGAY PHU CAP
     * @returnDATES NGAY PHU CAP
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATES")
    public java.util.Date getDATES() {
        return DATES;
    }
    /**
     * 設定NGAY PHU CAP
     * @param DATES NGAY PHU CAP
     */
    public void setDATES(java.util.Date DATES) {
        this.DATES = DATES;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_BONUS_POSSPk))
            return false;
        N_BONUS_POSSPk castOther = (N_BONUS_POSSPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(DATES, castOther.DATES).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(DATES).toHashCode();
    }
}
