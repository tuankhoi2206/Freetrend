package ds.program.fvhr.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* CAC LOAI PHEP CUA CTY
**/
@Entity
@Table(name = "N_REST_KIND")
public class N_REST_KIND {
    private java.lang.String ID_REST;	//MA PHEP
    private java.lang.String NAME_REST;	//TEN LOAI PHEP
    private java.lang.String NOTE;	//GHI CHU
    private java.lang.String ID_REST_SAL;	//LOAI TINH LUONG
    /**
     * MA PHEP
     * @return ID_REST MA PHEP
     */
    @NotBlank
    @Length(max = 3)
    @Column(name = "ID_REST")
    @Config(key = "N_REST_KIND.ID_REST")
    public java.lang.String getID_REST() {
        return ID_REST;
    }
    /**
     * 設定MA PHEP
     * @param ID_REST MA PHEP
     */
    public void setID_REST(java.lang.String ID_REST) {
        this.ID_REST = ID_REST;
    }
    /**
     * �?�得TEN LOAI PHEP
     * @return NAME_REST TEN LOAI PHEP
     */
    @Id
    @NotBlank
    @Column(name = "NAME_REST")
    @Config(key = "N_REST_KIND.NAME_REST")
    public java.lang.String getNAME_REST() {
        return NAME_REST;
    }
    /**
     * 設定TEN LOAI PHEP
     * @param NAME_REST TEN LOAI PHEP
     */
    public void setNAME_REST(java.lang.String NAME_REST) {
        this.NAME_REST = NAME_REST;
    }
    /**
     * GHI CHU
     * @return NOTE GHI CHU
     */
    @Length(max = 30)
    @Column(name = "NOTE")
    @Config(key = "N_REST_KIND.NOTE")
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
    /**
     * LOAI TINH LUONG
     * @return ID_REST_SAL LOAI TINH LUONG
     */
    @Length(max = 3)
    @Column(name = "ID_REST_SAL")
    @Config(key = "N_REST_KIND.ID_REST_SAL")
    public java.lang.String getID_REST_SAL() {
        return ID_REST_SAL;
    }
    /**
     * 設定LOAI TINH LUONG
     * @param ID_REST_SAL LOAI TINH LUONG
     */
    public void setID_REST_SAL(java.lang.String ID_REST_SAL) {
        this.ID_REST_SAL = ID_REST_SAL;
    }
}
