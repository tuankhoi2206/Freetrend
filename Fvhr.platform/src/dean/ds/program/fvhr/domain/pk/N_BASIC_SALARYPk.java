package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_BASIC_SALARYPk implements Serializable {
    private java.lang.String EMPSN;	//SO THE DEO
    private java.util.Date DATES;	//NGAY THAY DOI
    public N_BASIC_SALARYPk() {
        super();
    }
    public N_BASIC_SALARYPk(java.lang.String EMPSN, java.util.Date DATES) {
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
     * 設定SO THE DEO
     * @param EMPSN SO THE DEO
     */
    public void setEMPSN(java.lang.String EMPSN) {
        this.EMPSN = EMPSN;
    }
    /**
     * NGAY THAY DOI
     * @returnDATES NGAY THAY DOI
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATES")
    public java.util.Date getDATES() {
        return DATES;
    }
    /**
     * 設定NGAY THAY DOI
     * @param DATES NGAY THAY DOI
     */
    public void setDATES(java.util.Date DATES) {
        this.DATES = DATES;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_BASIC_SALARYPk))
            return false;
        N_BASIC_SALARYPk castOther = (N_BASIC_SALARYPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(DATES, castOther.DATES).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(DATES).toHashCode();
    }
}
