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
@Table(name = "ATTLOCK")
public class ATTLOCK {
    private java.lang.String TABNAME;
    private java.lang.String TABLOCK;
    /**
     * @return TABNAME 
     */
    @Id
    @NotBlank
    @Column(name = "TABNAME")
    @Config(key = "ATTLOCK.TABNAME")
    public java.lang.String getTABNAME() {
        return TABNAME;
    }
    /**
     * @param TABNAME 
     */
    public void setTABNAME(java.lang.String TABNAME) {
        this.TABNAME = TABNAME;
    }
    /**
     * @return TABLOCK 
     */
    @Length(max = 1)
    @Column(name = "TABLOCK")
    @Config(key = "ATTLOCK.TABLOCK")
    public java.lang.String getTABLOCK() {
        return TABLOCK;
    }
    /**
     * @param TABLOCK 
     */
    public void setTABLOCK(java.lang.String TABLOCK) {
        this.TABLOCK = TABLOCK;
    }
}
