package ds.program.fvhr.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* CAC LOAI HOP DONG
**/
@Entity
@Table(name = "N_LABOURKIND")
public class N_LABOURKIND {
    private java.lang.String ID;
    private java.lang.String LIMIT;	//THOI HAN
    private java.lang.String NOTE;	//GHI CHU
    /**
     * @return ID 
     */
    @Id
    @NotBlank
    @Column(name = "ID")
    @Config(key = "N_LABOURKIND.ID")
    public java.lang.String getID() {
        return ID;
    }
    /**
     * @param IID 
     */
    public void setID(java.lang.String ID) {
        this.ID = ID;
    }
    /**
     * 取得THOI HAN
     * @return LIMIT THOI HAN
     */
    @NotBlank
    @Length(max = 30)
    @Column(name = "LIMIT")
    @Config(key = "N_LABOURKIND.LIMIT")
    public java.lang.String getLIMIT() {
        return LIMIT;
    }
    /**
     * 設定THOI HAN
     * @param LIMIT THOI HAN
     */
    public void setLIMIT(java.lang.String LIMIT) {
        this.LIMIT = LIMIT;
    }
    /**
     * 取得GHI CHU
     * @return NOTE GHI CHU
     */
    @Length(max = 100)
    @Column(name = "NOTE")
    @Config(key = "N_LABOURKIND.NOTE")
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
