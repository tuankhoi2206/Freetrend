package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_PURCHASE_DEPTPk;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@IdClass(N_PURCHASE_DEPTPk.class)
@Entity
@Table(name = "N_PURCHASE_DEPT")
public class N_PURCHASE_DEPT {
    private java.lang.String ID_PURCHASE;
    private java.lang.String ID_DEPT_OLD;
    private java.lang.Long LANCHAY;
    /**
     * @return ID_PURCHASE 
     */
    @Id
    @NotBlank
    @Column(name = "ID_PURCHASE")
    @Config(key = "N_PURCHASE_DEPT.ID_PURCHASE")
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
     * @return ID_DEPT_OLD 
     */
    @Id
    @NotBlank
    @Column(name = "ID_DEPT_OLD")
    @Config(key = "N_PURCHASE_DEPT.ID_DEPT_OLD")
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
     * @return LANCHAY 
     */
    @Id
    @NotBlank
    @Column(name = "LANCHAY")
    @Config(key = "N_PURCHASE_DEPT.LANCHAY")
    public java.lang.Long getLANCHAY() {
        return LANCHAY;
    }
    /**
     * @param LANCHAY 
     */
    public void setLANCHAY(java.lang.Long LANCHAY) {
        this.LANCHAY = LANCHAY;
    }
}
