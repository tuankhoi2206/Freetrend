package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_CHANGE_ICCARDPk implements Serializable {
    private java.lang.String EMPSN;	//THE DEO
    private java.util.Date DATE_CHANGE;	//NGAY DOI
    private java.lang.String EMPCN_NEW;	//THE IC MOI
    public N_CHANGE_ICCARDPk() {
        super();
    }
    public N_CHANGE_ICCARDPk(java.lang.String EMPSN, java.util.Date DATE_CHANGE, java.lang.String EMPCN_NEW) {
        super();
        this.EMPSN = EMPSN;
        this.DATE_CHANGE = DATE_CHANGE;
        this.EMPCN_NEW = EMPCN_NEW;
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
     * 取得NGAY DOI
     * @returnDATE_CHANGE NGAY DOI
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_CHANGE")
    public java.util.Date getDATE_CHANGE() {
        return DATE_CHANGE;
    }
    /**
     * 設定NGAY DOI
     * @param DATE_CHANGE NGAY DOI
     */
    public void setDATE_CHANGE(java.util.Date DATE_CHANGE) {
        this.DATE_CHANGE = DATE_CHANGE;
    }
    /**
     * 取得THE IC MOI
     * @returnEMPCN_NEW THE IC MOI
     */
    @NotBlank
    @Column(name = "EMPCN_NEW", length = 10)
    public java.lang.String getEMPCN_NEW() {
        return EMPCN_NEW;
    }
    /**
     * 設定THE IC MOI
     * @param EMPCN_NEW THE IC MOI
     */
    public void setEMPCN_NEW(java.lang.String EMPCN_NEW) {
        this.EMPCN_NEW = EMPCN_NEW;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_CHANGE_ICCARDPk))
            return false;
        N_CHANGE_ICCARDPk castOther = (N_CHANGE_ICCARDPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(DATE_CHANGE, castOther.DATE_CHANGE).append(EMPCN_NEW, castOther.EMPCN_NEW).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(DATE_CHANGE).append(EMPCN_NEW).toHashCode();
    }
}
