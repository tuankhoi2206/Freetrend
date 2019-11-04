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
@Table(name = "N_ETHNIC")
public class N_ETHNIC {
    private java.lang.Long ID_ETHNIC;
    private java.lang.String NAME_ETHNIC;
    private java.lang.String NOTE;
    /**
     * @return ID_ETHNIC 
     */
    @NotBlank
    @Column(name = "ID_ETHNIC")
    @Config(key = "N_ETHNIC.ID_ETHNIC")
    public java.lang.Long getID_ETHNIC() {
        return ID_ETHNIC;
    }
    /**
     * @param ID_ETHNIC 
     */
    public void setID_ETHNIC(java.lang.Long ID_ETHNIC) {
        this.ID_ETHNIC = ID_ETHNIC;
    }
    /**
     * @return NAME_ETHNIC 
     */
    @Id
    @NotBlank
    @Column(name = "NAME_ETHNIC")
    @Config(key = "N_ETHNIC.NAME_ETHNIC")
    public java.lang.String getNAME_ETHNIC() {
        return NAME_ETHNIC;
    }
    /**
     * @param NAME_ETHNIC 
     */
    public void setNAME_ETHNIC(java.lang.String NAME_ETHNIC) {
        this.NAME_ETHNIC = NAME_ETHNIC;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 20)
    @Column(name = "NOTE")
    @Config(key = "N_ETHNIC.NOTE")
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
