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
@Table(name = "DEPTCODE")
public class DEPTCODE {
    private java.lang.String NAME;
    private java.lang.String CODE;
    /**
     * @return NAME 
     */
    @Length(max = 46)
    @Column(name = "NAME")
    @Config(key = "DEPTCODE.NAME")
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
     * @return CODE 
     */
    @Id
    @NotBlank
    @Column(name = "CODE")
    @Config(key = "DEPTCODE.CODE")
    public java.lang.String getCODE() {
        return CODE;
    }
    /**
     * @param CODE 
     */
    public void setCODE(java.lang.String CODE) {
        this.CODE = CODE;
    }
}
