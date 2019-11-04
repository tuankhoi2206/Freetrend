package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class ATTQUIT200900Pk implements Serializable {
    private java.lang.String EMPSN;
    private java.util.Date DOT_TV;	//PHAT LUONG THOI VIEC THEO TUNG DOT, LA THU 2 MOI TUAN
    public ATTQUIT200900Pk() {
        super();
    }
    public ATTQUIT200900Pk(java.lang.String EMPSN, java.util.Date DOT_TV) {
        super();
        this.EMPSN = EMPSN;
        this.DOT_TV = DOT_TV;
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
     * 取得PHAT LUONG THOI VIEC THEO TUNG DOT, LA THU 2 MOI TUAN
     * @returnDOT_TV PHAT LUONG THOI VIEC THEO TUNG DOT, LA THU 2 MOI TUAN
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DOT_TV")
    public java.util.Date getDOT_TV() {
        return DOT_TV;
    }
    /**
     * 設定PHAT LUONG THOI VIEC THEO TUNG DOT, LA THU 2 MOI TUAN
     * @param DOT_TV PHAT LUONG THOI VIEC THEO TUNG DOT, LA THU 2 MOI TUAN
     */
    public void setDOT_TV(java.util.Date DOT_TV) {
        this.DOT_TV = DOT_TV;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ATTQUIT200900Pk))
            return false;
        ATTQUIT200900Pk castOther = (ATTQUIT200900Pk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(DOT_TV, castOther.DOT_TV).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(DOT_TV).toHashCode();
    }
}
