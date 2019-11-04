package ds.program.fvhr.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* LOAI NGHI PHEP NAM
**/
@Entity
@Table(name = "N_REST_TYPE")
public class N_REST_TYPE {
    private java.lang.String ID_REST_TYPE;	//MA SO
    private java.lang.String NAME_REST_TYPE;	//TEN GOI
    /**
     * 取得MA SO
     * @return ID_REST_TYPE MA SO
     */
    @Id
    @NotBlank
    @Column(name = "ID_REST_TYPE")
    @Config(key = "N_REST_TYPE.ID_REST_TYPE")
    public java.lang.String getID_REST_TYPE() {
        return ID_REST_TYPE;
    }
    /**
     * 設定MA SO
     * @param ID_REST_TYPE MA SO
     */
    public void setID_REST_TYPE(java.lang.String ID_REST_TYPE) {
        this.ID_REST_TYPE = ID_REST_TYPE;
    }
    /**
     * 取得TEN GOI
     * @return NAME_REST_TYPE TEN GOI
     */
    @Length(max = 30)
    @Column(name = "NAME_REST_TYPE")
    @Config(key = "N_REST_TYPE.NAME_REST_TYPE")
    public java.lang.String getNAME_REST_TYPE() {
        return NAME_REST_TYPE;
    }
    /**
     * 設定TEN GOI
     * @param NAME_REST_TYPE TEN GOI
     */
    public void setNAME_REST_TYPE(java.lang.String NAME_REST_TYPE) {
        this.NAME_REST_TYPE = NAME_REST_TYPE;
    }
}
