package ds.program.fvhr.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* CAC LY DO MAT DL IC
**/
@Entity
@Table(name = "N_LOST_DATA_REASON")
public class N_LOST_DATA_REASON {
    private java.lang.String ID_LOST;	//MA SO
    private java.lang.String NAME_LOST;	//TEN GOI
    private java.lang.String NVALUE;	//GIA TRI (CO TRU HAY KHONG TRU TIEN)
    /**
     * 取得MA SO
     * @return ID_LOST MA SO
     */
    @Id
    @NotBlank
    @Column(name = "ID_LOST")
    @Config(key = "N_LOST_DATA_REASON.ID_LOST")
    public java.lang.String getID_LOST() {
        return ID_LOST;
    }
    /**
     * 設定MA SO
     * @param ID_LOST MA SO
     */
    public void setID_LOST(java.lang.String ID_LOST) {
        this.ID_LOST = ID_LOST;
    }
    /**
     * 取得TEN GOI
     * @return NAME_LOST TEN GOI
     */
    @Length(max = 30)
    @Column(name = "NAME_LOST")
    @Config(key = "N_LOST_DATA_REASON.NAME_LOST")
    public java.lang.String getNAME_LOST() {
        return NAME_LOST;
    }
    /**
     * 設定TEN GOI
     * @param NAME_LOST TEN GOI
     */
    public void setNAME_LOST(java.lang.String NAME_LOST) {
        this.NAME_LOST = NAME_LOST;
    }
    /**
     * 取得GIA TRI (CO TRU HAY KHONG TRU TIEN)
     * @return NVALUE GIA TRI (CO TRU HAY KHONG TRU TIEN)
     */
    @Length(max = 1)
    @Column(name = "NVALUE")
    @Config(key = "N_LOST_DATA_REASON.NVALUE")
    public java.lang.String getNVALUE() {
        return NVALUE;
    }
    /**
     * 設定GIA TRI (CO TRU HAY KHONG TRU TIEN)
     * @param NVALUE GIA TRI (CO TRU HAY KHONG TRU TIEN)
     */
    public void setNVALUE(java.lang.String NVALUE) {
        this.NVALUE = NVALUE;
    }
}
