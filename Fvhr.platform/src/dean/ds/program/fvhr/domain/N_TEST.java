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
@Table(name = "N_TEST")
public class N_TEST {
    private java.lang.String IID;
    private java.lang.String NAME;
    private java.lang.String NOTE;
    /**
     * @return IID 
     */
    @Id
    @NotBlank
    @Column(name = "ID")
    @Config(key = "N_TEST.ID")
    public java.lang.String getIID() {
        return IID;
    }
    /**
     * @param IID 
     */
    public void setIID(java.lang.String IID) {
        this.IID = IID;
    }
    /**
     * @return NAME 
     */
    @Length(max = 20)
    @Column(name = "NAME")
    @Config(key = "N_TEST.NAME")
    public java.lang.String getNAME() {
        return NAME;
    }
    /**
     * @param NAME 
     */
    public void setNAME(java.lang.String NAME) {
        this.NAME = NAME;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 20)
    @Column(name = "NOTE")
    @Config(key = "N_TEST.NOTE")
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
