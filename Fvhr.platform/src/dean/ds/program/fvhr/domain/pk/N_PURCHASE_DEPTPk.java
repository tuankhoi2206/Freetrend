package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_PURCHASE_DEPTPk implements Serializable {
    private java.lang.String ID_PURCHASE;
    private java.lang.String ID_DEPT_OLD;
    private java.lang.Long LANCHAY;
    public N_PURCHASE_DEPTPk() {
        super();
    }
    public N_PURCHASE_DEPTPk(java.lang.String ID_PURCHASE, java.lang.String ID_DEPT_OLD, java.lang.Long LANCHAY) {
        super();
        this.ID_PURCHASE = ID_PURCHASE;
        this.ID_DEPT_OLD = ID_DEPT_OLD;
        this.LANCHAY = LANCHAY;
    }

    /**
     * @returnID_PURCHASE 
     */
    @NotBlank
    @Column(name = "ID_PURCHASE", length = 20)
    public java.lang.String getID_PURCHASE() {
        return ID_PURCHASE;
    }
    /**
     * @param ID_PURCHASE 
     */
    public void setID_PURCHASE(java.lang.String ID_PURCHASE) {
        this.ID_PURCHASE = ID_PURCHASE;
    }
    /**
     * @returnID_DEPT_OLD 
     */
    @NotBlank
    @Column(name = "ID_DEPT_OLD", length = 20)
    public java.lang.String getID_DEPT_OLD() {
        return ID_DEPT_OLD;
    }
    /**
     * @param ID_DEPT_OLD 
     */
    public void setID_DEPT_OLD(java.lang.String ID_DEPT_OLD) {
        this.ID_DEPT_OLD = ID_DEPT_OLD;
    }
    /**
     * @returnLANCHAY 
     */
    @NotBlank
    @Column(name = "LANCHAY")
    public java.lang.Long getLANCHAY() {
        return LANCHAY;
    }
    /**
     * @param LANCHAY 
     */
    public void setLANCHAY(java.lang.Long LANCHAY) {
        this.LANCHAY = LANCHAY;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_PURCHASE_DEPTPk))
            return false;
        N_PURCHASE_DEPTPk castOther = (N_PURCHASE_DEPTPk) other;
        return new EqualsBuilder().append(ID_PURCHASE, castOther.ID_PURCHASE).append(ID_DEPT_OLD, castOther.ID_DEPT_OLD).append(LANCHAY, castOther.LANCHAY).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ID_PURCHASE).append(ID_DEPT_OLD).append(LANCHAY).toHashCode();
    }
}
