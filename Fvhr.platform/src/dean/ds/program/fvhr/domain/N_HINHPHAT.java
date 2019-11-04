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
@Table(name = "N_HINHPHAT")
public class N_HINHPHAT {
    private java.lang.String ID_PHAT;
    private java.lang.String NAME_PHAT;
    private java.lang.String NOTE;
    /**
     * @return ID_PHAT 
     */
    @Id
    @NotBlank
    @Column(name = "ID_PHAT")
    @Config(key = "N_HINHPHAT.ID_PHAT")
    public java.lang.String getID_PHAT() {
        return ID_PHAT;
    }
    /**
     * @param ID_PHAT 
     */
    public void setID_PHAT(java.lang.String ID_PHAT) {
        this.ID_PHAT = ID_PHAT;
    }
    /**
     * @return NAME_PHAT 
     */
    @Length(max = 100)
    @Column(name = "NAME_PHAT")
    @Config(key = "N_HINHPHAT.NAME_PHAT")
    public java.lang.String getNAME_PHAT() {
        return NAME_PHAT;
    }
    /**
     * @param NAME_PHAT 
     */
    public void setNAME_PHAT(java.lang.String NAME_PHAT) {
        this.NAME_PHAT = NAME_PHAT;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 150)
    @Column(name = "NOTE")
    @Config(key = "N_HINHPHAT.NOTE")
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
