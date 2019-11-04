package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_EMP_ICCARDPk implements Serializable {
    private java.lang.String EMPSN;	//THE DEO
    private java.lang.String EMPCN;	//THE IC
    private java.lang.String USE_STATUS;	//TRANG THAI SU DUNG
    private java.util.Date BEGIN_DATE;	//NGAY BAT DAU SU DUNG
    public N_EMP_ICCARDPk() {
        super();
    }
    public N_EMP_ICCARDPk(java.lang.String EMPSN, java.lang.String EMPCN, java.lang.String USE_STATUS, java.util.Date BEGIN_DATE) {
        super();
        this.EMPSN = EMPSN;
        this.EMPCN = EMPCN;
        this.USE_STATUS = USE_STATUS;
        this.BEGIN_DATE = BEGIN_DATE;
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
     * 取得THE IC
     * @returnEMPCN THE IC
     */
    @NotBlank
    @Column(name = "EMPCN", length = 10)
    public java.lang.String getEMPCN() {
        return EMPCN;
    }
    /**
     * 設定THE IC
     * @param EMPCN THE IC
     */
    public void setEMPCN(java.lang.String EMPCN) {
        this.EMPCN = EMPCN;
    }
    /**
     * 取得TRANG THAI SU DUNG
     * @returnUSE_STATUS TRANG THAI SU DUNG
     */
    @NotBlank
    @Column(name = "USE_STATUS", length = 1)
    public java.lang.String getUSE_STATUS() {
        return USE_STATUS;
    }
    /**
     * 設定TRANG THAI SU DUNG
     * @param USE_STATUS TRANG THAI SU DUNG
     */
    public void setUSE_STATUS(java.lang.String USE_STATUS) {
        this.USE_STATUS = USE_STATUS;
    }
    /**
     * 取得NGAY BAT DAU SU DUNG
     * @returnBEGIN_DATE NGAY BAT DAU SU DUNG
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "BEGIN_DATE")
    public java.util.Date getBEGIN_DATE() {
        return BEGIN_DATE;
    }
    /**
     * 設定NGAY BAT DAU SU DUNG
     * @param BEGIN_DATE NGAY BAT DAU SU DUNG
     */
    public void setBEGIN_DATE(java.util.Date BEGIN_DATE) {
        this.BEGIN_DATE = BEGIN_DATE;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_EMP_ICCARDPk))
            return false;
        N_EMP_ICCARDPk castOther = (N_EMP_ICCARDPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(EMPCN, castOther.EMPCN).append(USE_STATUS, castOther.USE_STATUS).append(BEGIN_DATE, castOther.BEGIN_DATE).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(EMPCN).append(USE_STATUS).append(BEGIN_DATE).toHashCode();
    }
}
