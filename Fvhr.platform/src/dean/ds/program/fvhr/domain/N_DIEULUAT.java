package ds.program.fvhr.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@Entity
@Table(name = "N_DIEULUAT")
public class N_DIEULUAT {
    private java.lang.String ID_DIEU;
    private java.lang.String NAME_DIEU;
    private java.lang.String NOTE;
    /**
     * @return ID_DIEU 
     */
    @Id
    @NotBlank
    @Column(name = "ID_DIEU")
    @Config(key = "N_DIEULUAT.ID_DIEU")
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
     * @return NAME_DIEU 
     */
    @Length(max = 100)
    @Column(name = "NAME_DIEU")
    @Config(key = "N_DIEULUAT.NAME_DIEU")
    public java.lang.String getNAME_DIEU() {
        return NAME_DIEU;
    }
    /**
     * @param NAME_DIEU 
     */
    public void setNAME_DIEU(java.lang.String NAME_DIEU) {
        this.NAME_DIEU = NAME_DIEU;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 150)
    @Column(name = "NOTE")
    @Config(key = "N_DIEULUAT.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * @param NOTE 
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
}
