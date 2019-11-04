package ds.program.fvhr.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* LOAI THAN NHAN CHO NGUOI LAO DONG
**/
@Entity
@Table(name = "N_RELATIVE_KIND")
public class N_RELATIVE_KIND {
    private java.lang.String IDKIND;	//MA LOAI THAN NHAN, BEGIN 05/2013
    private java.lang.String NAMEKIND;	//TEN LOAI THAN NHAN
    private java.lang.String NOTE;	//GHI CHU
    /**
     * 取得MA LOAI THAN NHAN, BEGIN 05/2013
     * @return IDKIND MA LOAI THAN NHAN, BEGIN 05/2013
     */
    @Id
    @NotBlank
    @Column(name = "IDKIND")
    @Config(key = "N_RELATIVE_KIND.IDKIND")
    public java.lang.String getIDKIND() {
        return IDKIND;
    }
    /**
     * 設定MA LOAI THAN NHAN, BEGIN 05/2013
     * @param IDKIND MA LOAI THAN NHAN, BEGIN 05/2013
     */
    public void setIDKIND(java.lang.String IDKIND) {
        this.IDKIND = IDKIND;
    }
    /**
     * 取得TEN LOAI THAN NHAN
     * @return NAMEKIND TEN LOAI THAN NHAN
     */
    @Length(max = 50)
    @Column(name = "NAMEKIND")
    @Config(key = "N_RELATIVE_KIND.NAMEKIND")
    public java.lang.String getNAMEKIND() {
        return NAMEKIND;
    }
    /**
     * 設定TEN LOAI THAN NHAN
     * @param NAMEKIND TEN LOAI THAN NHAN
     */
    public void setNAMEKIND(java.lang.String NAMEKIND) {
        this.NAMEKIND = NAMEKIND;
    }
    /**
     * 取得GHI CHU
     * @return NOTE GHI CHU
     */
    @Length(max = 250)
    @Column(name = "NOTE")
    @Config(key = "N_RELATIVE_KIND.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * 設定GHI CHU
     * @param NOTE GHI CHU
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
}
