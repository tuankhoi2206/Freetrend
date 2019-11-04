package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_CHANGE_HOSPITALPk implements Serializable {
    private java.lang.String EMPSN;
    private java.util.Date DATE_CHANGE;
    private java.lang.String IDHOS_NEW;
    private java.lang.String STATUS;
    private java.lang.String IDPRO_NEW;
    public N_CHANGE_HOSPITALPk() {
        super();
    }
    public N_CHANGE_HOSPITALPk(java.lang.String EMPSN, java.util.Date DATE_CHANGE, java.lang.String IDHOS_NEW, java.lang.String STATUS, java.lang.String IDPRO_NEW) {
        super();
        this.EMPSN = EMPSN;
        this.DATE_CHANGE = DATE_CHANGE;
        this.IDHOS_NEW = IDHOS_NEW;
        this.STATUS = STATUS;
        this.IDPRO_NEW = IDPRO_NEW;
    }

    public N_CHANGE_HOSPITALPk(String stridHos, String strIdPro) {
		// TODO Auto-generated constructor stub
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
     * @returnDATE_CHANGE 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_CHANGE")
    public java.util.Date getDATE_CHANGE() {
        return DATE_CHANGE;
    }
    /**
     * @param DATE_CHANGE 
     */
    public void setDATE_CHANGE(java.util.Date DATE_CHANGE) {
        this.DATE_CHANGE = DATE_CHANGE;
    }
    /**
     * @returnIDHOS_NEW 
     */
    @NotBlank
    @Column(name = "IDHOS_NEW", length = 5)
    public java.lang.String getIDHOS_NEW() {
        return IDHOS_NEW;
    }
    /**
     * @param IDHOS_NEW 
     */
    public void setIDHOS_NEW(java.lang.String IDHOS_NEW) {
        this.IDHOS_NEW = IDHOS_NEW;
    }
    /**
     * @returnSTATUS 
     */
    @NotBlank
    @Column(name = "STATUS", length = 1)
    public java.lang.String getSTATUS() {
        return STATUS;
    }
    /**
     * @param STATUS 
     */
    public void setSTATUS(java.lang.String STATUS) {
        this.STATUS = STATUS;
    }
    /**
     * @returnIDPRO_NEW 
     */
    @NotBlank
    @Column(name = "IDPRO_NEW", length = 5)
    public java.lang.String getIDPRO_NEW() {
        return IDPRO_NEW;
    }
    /**
     * @param IDPRO_NEW 
     */
    public void setIDPRO_NEW(java.lang.String IDPRO_NEW) {
        this.IDPRO_NEW = IDPRO_NEW;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_CHANGE_HOSPITALPk))
            return false;
        N_CHANGE_HOSPITALPk castOther = (N_CHANGE_HOSPITALPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(DATE_CHANGE, castOther.DATE_CHANGE).append(IDHOS_NEW, castOther.IDHOS_NEW).append(STATUS, castOther.STATUS).append(IDPRO_NEW, castOther.IDPRO_NEW).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(DATE_CHANGE).append(IDHOS_NEW).append(STATUS).append(IDPRO_NEW).toHashCode();
    }
}
