package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_FACT_TRANSFER_LOCKPk implements Serializable {
    private java.lang.String DEPSN;
    private java.lang.String YEAR;
    private java.lang.String MONTH;
    public N_FACT_TRANSFER_LOCKPk() {
        super();
    }
    public N_FACT_TRANSFER_LOCKPk(java.lang.String DEPSN, java.lang.String YEAR, java.lang.String MONTH) {
        super();
        this.DEPSN = DEPSN;
        this.YEAR = YEAR;
        this.MONTH = MONTH;
    }

    /**
     * @returnDEPSN 
     */
    @NotBlank
    @Column(name = "DEPSN", length = 7)
    public java.lang.String getDEPSN() {
        return DEPSN;
    }
    /**
     * @param DEPSN 
     */
    public void setDEPSN(java.lang.String DEPSN) {
        this.DEPSN = DEPSN;
    }
    /**
     * @returnYEAR 
     */
    @NotBlank
    @Column(name = "YEAR", length = 4)
    public java.lang.String getYEAR() {
        return YEAR;
    }
    /**
     * @param YEAR 
     */
    public void setYEAR(java.lang.String YEAR) {
        this.YEAR = YEAR;
    }
    /**
     * @returnMONTH 
     */
    @NotBlank
    @Column(name = "MONTH", length = 2)
    public java.lang.String getMONTH() {
        return MONTH;
    }
    /**
     * @param MONTH 
     */
    public void setMONTH(java.lang.String MONTH) {
        this.MONTH = MONTH;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_FACT_TRANSFER_LOCKPk))
            return false;
        N_FACT_TRANSFER_LOCKPk castOther = (N_FACT_TRANSFER_LOCKPk) other;
        return new EqualsBuilder().append(DEPSN, castOther.DEPSN).append(YEAR, castOther.YEAR).append(MONTH, castOther.MONTH).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(DEPSN).append(YEAR).append(MONTH).toHashCode();
    }
}
