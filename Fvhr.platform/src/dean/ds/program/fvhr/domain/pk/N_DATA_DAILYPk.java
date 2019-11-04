package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_DATA_DAILYPk implements Serializable {
    private java.lang.String EMPSN;	//data tu t03/2009
    private java.util.Date DATES;
    public N_DATA_DAILYPk() {
        super();
    }
    public N_DATA_DAILYPk(java.lang.String EMPSN, java.util.Date DATES) {
        super();
        this.EMPSN = EMPSN;
        this.DATES = DATES;
    }

    /**
     * 取得data tu t03/2009
     * @returnEMPSN data tu t03/2009
     */
    @NotBlank
    @Column(name = "EMPSN", length = 8)
    public java.lang.String getEMPSN() {
        return EMPSN;
    }
    /**
     * 設定data tu t03/2009
     * @param EMPSN data tu t03/2009
     */
    public void setEMPSN(java.lang.String EMPSN) {
        this.EMPSN = EMPSN;
    }
    /**
     * @returnDATES 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATES")
    public java.util.Date getDATES() {
        return DATES;
    }
    /**
     * @param DATES 
     */
    public void setDATES(java.util.Date DATES) {
        this.DATES = DATES;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_DATA_DAILYPk))
            return false;
        N_DATA_DAILYPk castOther = (N_DATA_DAILYPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(DATES, castOther.DATES).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(DATES).toHashCode();
    }
}
