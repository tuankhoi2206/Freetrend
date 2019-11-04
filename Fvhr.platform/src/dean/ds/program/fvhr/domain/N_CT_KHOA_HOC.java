package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_CT_KHOA_HOCPk;
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
@IdClass(N_CT_KHOA_HOCPk.class)
@Entity
@Table(name = "N_CT_KHOA_HOC")
public class N_CT_KHOA_HOC {
    private java.lang.String ID_KHOA;
    private java.lang.String ID_MONHOC;
    private java.lang.String NOTE;
    /**
     * @return ID_KHOA 
     */
    @Id
    @NotBlank
    @Config(key = "N_CT_KHOA_HOC.ID_KHOA")
    public java.lang.String getID_KHOA() {
        return ID_KHOA;
    }
    /**
     * @param ID_KHOA 
     */
    public void setID_KHOA(java.lang.String ID_KHOA) {
        this.ID_KHOA = ID_KHOA;
    }
    /**
     * @return ID_MONHOC 
     */
    @Id
    @NotBlank
    @Column(name = "ID_MONHOC")
    @Config(key = "N_CT_KHOA_HOC.ID_MONHOC")
    public java.lang.String getID_MONHOC() {
        return ID_MONHOC;
    }
    /**
     * @param ID_MONHOC 
     */
    public void setID_MONHOC(java.lang.String ID_MONHOC) {
        this.ID_MONHOC = ID_MONHOC;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 200)
    @Column(name = "NOTE")
    @Config(key = "N_CT_KHOA_HOC.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * @param NOTE 
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
    private N_KHOA_HOC ID_KHOA_Object;
    /**
     * @return ID_KHOA 
     */
    @ManyToOne
    @JoinColumn(name="ID_KHOA", referencedColumnName="MA_KHOA", insertable=false, updatable=false)
    @NotFound(action=NotFoundAction.IGNORE)
    @Config(key = "N_CT_KHOA_HOC.ID_KHOA")
    public N_KHOA_HOC getID_KHOA_Object() {
        return ID_KHOA_Object;
    }
    /**
     * @param ID_KHOA 
     */
    public void setID_KHOA_Object(N_KHOA_HOC ID_KHOA) {
        this.ID_KHOA_Object = ID_KHOA;
    }
}
