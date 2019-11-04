package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_DATA_MAINPk implements Serializable {
    private java.lang.String EMPCN;
    private java.lang.String DATES;
    public N_DATA_MAINPk() {
        super();
    }
    public N_DATA_MAINPk(java.lang.String EMPCN, java.lang.String DATES) {
        super();
        this.EMPCN = EMPCN;
        this.DATES = DATES;
    }

    /**
     * @returnEMPCN 
     */
    @NotBlank
    @Column(name = "EMPCN", length = 10)
    public java.lang.String getEMPCN() {
        return EMPCN;
    }
    /**
     * @param EMPCN 
     */
    public void setEMPCN(java.lang.String EMPCN) {
        this.EMPCN = EMPCN;
    }
    /**
     * @returnDATES 
     */
    @NotBlank
    @Column(name = "DATES", length = 10)
    public java.lang.String getDATES() {
        return DATES;
    }
    /**
     * @param DATES 
     */
    public void setDATES(java.lang.String DATES) {
        this.DATES = DATES;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_DATA_MAINPk))
            return false;
        N_DATA_MAINPk castOther = (N_DATA_MAINPk) other;
        return new EqualsBuilder().append(EMPCN, castOther.EMPCN).append(DATES, castOther.DATES).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPCN).append(DATES).toHashCode();
    }
}
