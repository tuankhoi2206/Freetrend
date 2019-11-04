package ds.program.fvhr.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* TRANG THAI XUAT HAY CHUA XUAT BAO GIAM HANG TUAN
**/
@Entity
@Table(name = "N_BAOGIAM_STATUS")
public class N_BAOGIAM_STATUS {
    private java.util.Date NGAY_BG;
    private java.util.Date FROM_DATE;
    private java.util.Date TO_DATE;
    private java.lang.Long STATUS;	//1 la lock, o la unlock
    private java.lang.String NOTE;
    private java.lang.String THE_GH_QUY;	//N: Bao giam nhung chua lay the gia han quy ve,null la da lay the GH ve rui
    /**
     * @return NGAY_BG 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "NGAY_BG")
    @Config(key = "N_BAOGIAM_STATUS.NGAY_BG")
    public java.util.Date getNGAY_BG() {
        return NGAY_BG;
    }
    /**
     * @param NGAY_BG 
     */
    public void setNGAY_BG(java.util.Date NGAY_BG) {
        this.NGAY_BG = NGAY_BG;
    }
    /**
     * @return FROM_DATE 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FROM_DATE")
    @Config(key = "N_BAOGIAM_STATUS.FROM_DATE")
    public java.util.Date getFROM_DATE() {
        return FROM_DATE;
    }
    /**
     * @param FROM_DATE 
     */
    public void setFROM_DATE(java.util.Date FROM_DATE) {
        this.FROM_DATE = FROM_DATE;
    }
    /**
     * @return TO_DATE 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "TO_DATE")
    @Config(key = "N_BAOGIAM_STATUS.TO_DATE")
    public java.util.Date getTO_DATE() {
        return TO_DATE;
    }
    /**
     * @param TO_DATE 
     */
    public void setTO_DATE(java.util.Date TO_DATE) {
        this.TO_DATE = TO_DATE;
    }
    /**
     * 取得1 la lock, o la unlock
     * @return STATUS 1 la lock, o la unlock
     */
    @Column(name = "STATUS")
    @Config(key = "N_BAOGIAM_STATUS.STATUS")
    public java.lang.Long getSTATUS() {
        return STATUS;
    }
    /**
     * 設定1 la lock, o la unlock
     * @param STATUS 1 la lock, o la unlock
     */
    public void setSTATUS(java.lang.Long STATUS) {
        this.STATUS = STATUS;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 80)
    @Column(name = "NOTE")
    @Config(key = "N_BAOGIAM_STATUS.NOTE")
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
     * 取得N: Bao giam nhung chua lay the gia han quy ve,null la da lay the GH ve rui
     * @return THE_GH_QUY N: Bao giam nhung chua lay the gia han quy ve,null la da lay the GH ve rui
     */
    @Length(max = 1)
    @Column(name = "THE_GH_QUY")
    @Config(key = "N_BAOGIAM_STATUS.THE_GH_QUY")
    public java.lang.String getTHE_GH_QUY() {
        return THE_GH_QUY;
    }
    /**
     * 設定N: Bao giam nhung chua lay the gia han quy ve,null la da lay the GH ve rui
     * @param THE_GH_QUY N: Bao giam nhung chua lay the gia han quy ve,null la da lay the GH ve rui
     */
    public void setTHE_GH_QUY(java.lang.String THE_GH_QUY) {
        this.THE_GH_QUY = THE_GH_QUY;
    }
}
