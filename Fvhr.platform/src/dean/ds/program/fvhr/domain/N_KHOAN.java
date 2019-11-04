package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_KHOANPk;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@IdClass(N_KHOANPk.class)
@Entity
@Table(name = "N_KHOAN")
public class N_KHOAN {
    private java.lang.Long IID;
    private java.lang.String ID_KHOAN;
    private java.lang.String ID_DIEU;
    private java.lang.String NAME_KHOAN;
    private java.lang.String NOTE;
    /**
     * @return IID 
     */
    @Column(name = "ID")
    @Config(key = "N_KHOAN.ID")
    public java.lang.Long getIID() {
        return IID;
    }
    /**
     * @param IID 
     */
    public void setIID(java.lang.Long IID) {
        this.IID = IID;
    }
    /**
     * @return ID_KHOAN 
     */
    @Id
    @NotBlank
    @Column(name = "ID_KHOAN")
    @Config(key = "N_KHOAN.ID_KHOAN")
    public java.lang.String getID_KHOAN() {
        return ID_KHOAN;
    }
    /**
     * @param ID_KHOAN 
     */
    public void setID_KHOAN(java.lang.String ID_KHOAN) {
        this.ID_KHOAN = ID_KHOAN;
    }
    /**
     * @return ID_DIEU 
     */
    @Id
    @NotBlank
    @Config(key = "N_KHOAN.ID_DIEU")
    public java.lang.String getID_DIEU() {
        return ID_DIEU;
    }
    /**
     * @param ID_DIEU 
     */
    public void setID_DIEU(java.lang.String ID_DIEU) {
        this.ID_DIEU = ID_DIEU;
    }
    /**
     * @return NAME_KHOAN 
     */
    @Length(max = 100)
    @Column(name = "NAME_KHOAN")
    @Config(key = "N_KHOAN.NAME_KHOAN")
    public java.lang.String getNAME_KHOAN() {
        return NAME_KHOAN;
    }
    /**
     * @param NAME_KHOAN 
     */
    public void setNAME_KHOAN(java.lang.String NAME_KHOAN) {
        this.NAME_KHOAN = NAME_KHOAN;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 150)
    @Column(name = "NOTE")
    @Config(key = "N_KHOAN.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * @param NOTE 
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
    private N_DIEULUAT ID_DIEU_Object;
    /**
     * @return ID_DIEU 
     */
    @ManyToOne
    @JoinColumn(name="ID_DIEU", referencedColumnName="ID_DIEU", insertable=false, updatable=false)
    @NotFound(action=NotFoundAction.IGNORE)
    @Config(key = "N_KHOAN.ID_DIEU")
    public N_DIEULUAT getID_DIEU_Object() {
        return ID_DIEU_Object;
    }
    /**
     * @param ID_DIEU 
     */
    public void setID_DIEU_Object(N_DIEULUAT ID_DIEU) {
        this.ID_DIEU_Object = ID_DIEU;
    }
}
