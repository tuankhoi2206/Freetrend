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
@Table(name = "N_IC_CARD")
public class N_IC_CARD {
    private java.lang.String IC_NO;
    private java.lang.String USE_STATUS;	//0: The IC dang trong, 1: The IC dang co nguoi SD, 4: The IC bi hu, bi mat ko con SD
    private java.lang.String NOTE;
    /**
     * @return IC_NO 
     */
    @Id
    @NotBlank
    @Column(name = "IC_NO")
    @Config(key = "N_IC_CARD.IC_NO")
    public java.lang.String getIC_NO() {
        return IC_NO;
    }
    /**
     * @param IC_NO 
     */
    public void setIC_NO(java.lang.String IC_NO) {
        this.IC_NO = IC_NO;
    }
    /**
     * 取得0: The IC dang trong, 1: The IC dang co nguoi SD, 4: The IC bi hu, bi mat ko con SD
     * @return USE_STATUS 0: The IC dang trong, 1: The IC dang co nguoi SD, 4: The IC bi hu, bi mat ko con SD
     */
    @Length(max = 1)
    @Column(name = "USE_STATUS")
    @Config(key = "N_IC_CARD.USE_STATUS")
    public java.lang.String getUSE_STATUS() {
        return USE_STATUS;
    }
    /**
     * 設定0: The IC dang trong, 1: The IC dang co nguoi SD, 4: The IC bi hu, bi mat ko con SD
     * @param USE_STATUS 0: The IC dang trong, 1: The IC dang co nguoi SD, 4: The IC bi hu, bi mat ko con SD
     */
    public void setUSE_STATUS(java.lang.String USE_STATUS) {
        this.USE_STATUS = USE_STATUS;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 50)
    @Column(name = "NOTE")
    @Config(key = "N_IC_CARD.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * @param NOTE 
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
}
