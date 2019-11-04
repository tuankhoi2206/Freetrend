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
@Table(name = "N_TRAINING_LIST")
public class N_TRAINING_LIST {
    private java.lang.String MA_MON_HOC;
    private java.lang.String TEN_MON_HOC;
    private java.lang.String NOTE;
    private java.lang.String ID_KHOA;	//KO SD VI LA QH N,N NEN PSINH BANG CON
    private java.lang.String STATUS;	//tam thoi ko sd . la trang thai hl 1 lan hay nhieu lan
    /**
     * @return MA_MON_HOC 
     */
    @Id
    @NotBlank
    @Column(name = "MA_MON_HOC")
    @Config(key = "N_TRAINING_LIST.MA_MON_HOC")
    public java.lang.String getMA_MON_HOC() {
        return MA_MON_HOC;
    }
    /**
     * @param MA_MON_HOC 
     */
    public void setMA_MON_HOC(java.lang.String MA_MON_HOC) {
        this.MA_MON_HOC = MA_MON_HOC;
    }
    /**
     * @return TEN_MON_HOC 
     */
    @NotBlank
    @Length(max = 100)
    @Column(name = "TEN_MON_HOC")
    @Config(key = "N_TRAINING_LIST.TEN_MON_HOC")
    public java.lang.String getTEN_MON_HOC() {
        return TEN_MON_HOC;
    }
    /**
     * @param TEN_MON_HOC 
     */
    public void setTEN_MON_HOC(java.lang.String TEN_MON_HOC) {
        this.TEN_MON_HOC = TEN_MON_HOC;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 200)
    @Column(name = "NOTE")
    @Config(key = "N_TRAINING_LIST.NOTE")
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
     * 取得KO SD VI LA QH N,N NEN PSINH BANG CON
     * @return ID_KHOA KO SD VI LA QH N,N NEN PSINH BANG CON
     */
    @Length(max = 5)
    @Column(name = "ID_KHOA")
    @Config(key = "N_TRAINING_LIST.ID_KHOA")
    public java.lang.String getID_KHOA() {
        return ID_KHOA;
    }
    /**
     * 設定KO SD VI LA QH N,N NEN PSINH BANG CON
     * @param ID_KHOA KO SD VI LA QH N,N NEN PSINH BANG CON
     */
    public void setID_KHOA(java.lang.String ID_KHOA) {
        this.ID_KHOA = ID_KHOA;
    }
    /**
     * 取得tam thoi ko sd . la trang thai hl 1 lan hay nhieu lan
     * @return STATUS tam thoi ko sd . la trang thai hl 1 lan hay nhieu lan
     */
    @Length(max = 20)
    @Column(name = "STATUS")
    @Config(key = "N_TRAINING_LIST.STATUS")
    public java.lang.String getSTATUS() {
        return STATUS;
    }
    /**
     * 設定tam thoi ko sd . la trang thai hl 1 lan hay nhieu lan
     * @param STATUS tam thoi ko sd . la trang thai hl 1 lan hay nhieu lan
     */
    public void setSTATUS(java.lang.String STATUS) {
        this.STATUS = STATUS;
    }
}
