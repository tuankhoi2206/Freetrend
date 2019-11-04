package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_EMP_DEPT_DETAILPk implements Serializable {
    private java.lang.String EMPSN;
    private java.lang.String ID_DEPT;
    private java.util.Date DATE_B;
    public N_EMP_DEPT_DETAILPk() {
        super();
    }
    public N_EMP_DEPT_DETAILPk(java.lang.String EMPSN, java.lang.String ID_DEPT, java.util.Date DATE_B) {
        super();
        this.EMPSN = EMPSN;
        this.ID_DEPT = ID_DEPT;
        this.DATE_B = DATE_B;
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
     * @returnID_DEPT 
     */
    @NotBlank
    @Column(name = "ID_DEPT", length = 5)
    public java.lang.String getID_DEPT() {
        return ID_DEPT;
    }
    /**
     * @param ID_DEPT 
     */
    public void setID_DEPT(java.lang.String ID_DEPT) {
        this.ID_DEPT = ID_DEPT;
    }
    /**
     * @returnDATE_B 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_B")
    public java.util.Date getDATE_B() {
        return DATE_B;
    }
    /**
     * @param DATE_B 
     */
    public void setDATE_B(java.util.Date DATE_B) {
        this.DATE_B = DATE_B;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_EMP_DEPT_DETAILPk))
            return false;
        N_EMP_DEPT_DETAILPk castOther = (N_EMP_DEPT_DETAILPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(ID_DEPT, castOther.ID_DEPT).append(DATE_B, castOther.DATE_B).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(ID_DEPT).append(DATE_B).toHashCode();
    }
}
