package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_KYLUATPk implements Serializable {
    private java.lang.String EMPSN;
    private java.util.Date DATE_P;	//THOI GIAN PHAT tu ngay. PHAT TU NGAY
    public N_KYLUATPk() {
        super();
    }
    public N_KYLUATPk(java.lang.String EMPSN, java.util.Date DATE_P) {
        super();
        this.EMPSN = EMPSN;
        this.DATE_P = DATE_P;
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
     * 取得THOI GIAN PHAT tu ngay. PHAT TU NGAY
     * @returnDATE_P THOI GIAN PHAT tu ngay. PHAT TU NGAY
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_P")
    public java.util.Date getDATE_P() {
        return DATE_P;
    }
    /**
     * 設定THOI GIAN PHAT tu ngay. PHAT TU NGAY
     * @param DATE_P THOI GIAN PHAT tu ngay. PHAT TU NGAY
     */
    public void setDATE_P(java.util.Date DATE_P) {
        this.DATE_P = DATE_P;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_KYLUATPk))
            return false;
        N_KYLUATPk castOther = (N_KYLUATPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(DATE_P, castOther.DATE_P).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(DATE_P).toHashCode();
    }
}
