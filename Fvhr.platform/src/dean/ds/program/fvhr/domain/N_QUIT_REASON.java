package ds.program.fvhr.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* CAC LY DO NGHI VIEC
**/
@Entity
@Table(name = "N_QUIT_REASON")
public class N_QUIT_REASON {
    private java.math.BigDecimal ID_QUIT_REASON;	//SO THE
    private java.lang.String NAME_QR;	//LY DO NGHI VIEC
    private java.lang.String NOTE;	//GHI CHU
    private java.math.BigDecimal KEYS;	//DUNG DE TINH PHU CAP
    private java.lang.String NOTE1;	//0: KHONG DUNG LUAT; 1: NGHI DUNG LUAT
    /**
     * 取得SO THE
     * @return ID_QUIT_REASON SO THE
     */
    @Id
    @NotBlank
    @Column(name = "ID_QUIT_REASON")
    @Config(key = "N_QUIT_REASON.ID_QUIT_REASON")
    public java.math.BigDecimal getID_QUIT_REASON() {
        return ID_QUIT_REASON;
    }
    /**
     * 設定SO THE
     * @param ID_QUIT_REASON SO THE
     */
    public void setID_QUIT_REASON(java.math.BigDecimal ID_QUIT_REASON) {
        this.ID_QUIT_REASON = ID_QUIT_REASON;
    }
    /**
     * 取得LY DO NGHI VIEC
     * @return NAME_QR LY DO NGHI VIEC
     */
    @Length(max = 100)
    @Column(name = "NAME_QR")
    @Config(key = "N_QUIT_REASON.NAME_QR")
    public java.lang.String getNAME_QR() {
        return NAME_QR;
    }
    /**
     * 設定LY DO NGHI VIEC
     * @param NAME_QR LY DO NGHI VIEC
     */
    public void setNAME_QR(java.lang.String NAME_QR) {
        this.NAME_QR = NAME_QR;
    }
    /**
     * 取得GHI CHU
     * @return NOTE GHI CHU
     */
    @Length(max = 100)
    @Column(name = "NOTE")
    @Config(key = "N_QUIT_REASON.NOTE")
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
     * 取得DUNG DE TINH PHU CAP
     * @return KEYS DUNG DE TINH PHU CAP
     */
    @Column(name = "KEYS")
    @Config(key = "N_QUIT_REASON.KEYS")
    public java.math.BigDecimal getKEYS() {
        return KEYS;
    }
    /**
     * 設定DUNG DE TINH PHU CAP
     * @param KEYS DUNG DE TINH PHU CAP
     */
    public void setKEYS(java.math.BigDecimal KEYS) {
        this.KEYS = KEYS;
    }
    /**
     * 取得0: KHONG DUNG LUAT; 1: NGHI DUNG LUAT
     * @return NOTE1 0: KHONG DUNG LUAT; 1: NGHI DUNG LUAT
     */
    @Length(max = 5)
    @Column(name = "NOTE1")
    @Config(key = "N_QUIT_REASON.NOTE1")
    public java.lang.String getNOTE1() {
        return NOTE1;
    }
    /**
     * 設定0: KHONG DUNG LUAT; 1: NGHI DUNG LUAT
     * @param NOTE1 0: KHONG DUNG LUAT; 1: NGHI DUNG LUAT
     */
    public void setNOTE1(java.lang.String NOTE1) {
        this.NOTE1 = NOTE1;
    }
}
