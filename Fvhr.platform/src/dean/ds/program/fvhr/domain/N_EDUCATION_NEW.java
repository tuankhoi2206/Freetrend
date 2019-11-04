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
@Table(name = "N_EDUCATION_NEW")
public class N_EDUCATION_NEW {
    private java.lang.String NAME_EDUCATION;
    private java.lang.String NOTE;
    private java.lang.String ID_EDUCATION;
    private java.lang.String EE_GROUP;
    /**
     * @return NAME_EDUCATION 
     */
    @Length(max = 150)
    @Column(name = "NAME_EDUCATION")
    @Config(key = "N_EDUCATION_NEW.NAME_EDUCATION")
    public java.lang.String getNAME_EDUCATION() {
        return NAME_EDUCATION;
    }
    /**
     * @param NAME_EDUCATION 
     */
    public void setNAME_EDUCATION(java.lang.String NAME_EDUCATION) {
        this.NAME_EDUCATION = NAME_EDUCATION;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 150)
    @Column(name = "NOTE")
    @Config(key = "N_EDUCATION_NEW.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * @param NOTE 
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
    /**
     * @return ID_EDUCATION 
     */
    @Id
    @NotBlank
    @Column(name = "ID_EDUCATION")
    @Config(key = "N_EDUCATION_NEW.ID_EDUCATION")
    public java.lang.String getID_EDUCATION() {
        return ID_EDUCATION;
    }
    /**
     * @param ID_EDUCATION 
     */
    public void setID_EDUCATION(java.lang.String ID_EDUCATION) {
        this.ID_EDUCATION = ID_EDUCATION;
    }
    /**
     * @return EE_GROUP 
     */
    @Length(max = 6)
    @Column(name = "E_GROUP")
    @Config(key = "N_EDUCATION_NEW.E_GROUP")
    public String getEE_GROUP() {
        return EE_GROUP;
    }
    /**
     * @param EE_GROUP 
     */
    public void setEE_GROUP(String EE_GROUP) {
        this.EE_GROUP = EE_GROUP;
    }
}
