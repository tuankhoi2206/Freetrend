package ds.program.fvhr.domain.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import dsc.util.hibernate.validator.NotBlank;

public class N_BIRTH_CERTIFICATEPk  implements Serializable{
	private java.lang.String EMPSN;
    private java.util.Date DATE_BEAR;
    
    public N_BIRTH_CERTIFICATEPk() {
        super();
    }
    
    public N_BIRTH_CERTIFICATEPk(java.lang.String EMPSN, java.util.Date DATE_BEAR) {
        super();
        this.EMPSN = EMPSN;
        this.DATE_BEAR = DATE_BEAR;
    }
    
    @NotBlank
    @Column(name = "EMPSN", length = 8)
    public java.lang.String getEMPSN() {
        return EMPSN;
    }
    public void setEMPSN(java.lang.String EMPSN) {
        this.EMPSN = EMPSN;
    }
    
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_BEAR")
    public java.util.Date getDATE_BEAR() {
        return DATE_BEAR;
    }

    public void setDATE_BEAR(java.util.Date DATE_BEAR) {
        this.DATE_BEAR = DATE_BEAR;
    }
    
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_BIRTH_CERTIFICATEPk))
            return false;
        N_BIRTH_CERTIFICATEPk castOther = (N_BIRTH_CERTIFICATEPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(DATE_BEAR, castOther.DATE_BEAR).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(DATE_BEAR).toHashCode();
    }
}
