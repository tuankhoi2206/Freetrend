package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.DSHR71Pk;
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
* TCA NGOAI: CHO CNV CO NHO < 1 TUOI, KE TOAN GOI FILE, BD 06/2013
**/
@IdClass(DSHR71Pk.class)
@Entity
@Table(name = "DSHR71")
public class DSHR71 {
    private java.util.Date VDATE;	//THOI GIAN, AP DUNG FVL va FVLS
    private java.lang.String PNL;	//SO THE
    private java.lang.Double JJ_HOUR;
    private java.lang.Double JJ_WADD;
    private java.lang.Double JJ_JADD;
    private java.lang.String UP_USER;
    private java.util.Date UP_DATE;
    private java.lang.String SEC;	//MA DON VI
    private java.lang.String FACTNAME;	//TEN XUONG
    private java.lang.String IS_SOLE;	//Trang thai :0: ko thuoc de, 1: Trung De, 2: Dai De
    /**
     * 取得THOI GIAN, AP DUNG FVL va FVLS
     * @return VDATE THOI GIAN, AP DUNG FVL va FVLS
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "VDATE")
    @Config(key = "DSHR71.VDATE")
    public java.util.Date getVDATE() {
        return VDATE;
    }
    /**
     * 設定THOI GIAN, AP DUNG FVL va FVLS
     * @param VDATE THOI GIAN, AP DUNG FVL va FVLS
     */
    public void setVDATE(java.util.Date VDATE) {
        this.VDATE = VDATE;
    }
    /**
     * 取得SO THE
     * @return PNL SO THE
     */
    @Id
    @NotBlank
    @Column(name = "PNL")
    @Config(key = "DSHR71.PNL")
    public java.lang.String getPNL() {
        return PNL;
    }
    /**
     * 設定SO THE
     * @param PNL SO THE
     */
    public void setPNL(java.lang.String PNL) {
        this.PNL = PNL;
    }
    /**
     * @return JJ_HOUR 
     */
    @Column(name = "J_HOUR")
    @Config(key = "DSHR71.J_HOUR")
    public java.lang.Double getJJ_HOUR() {
        return JJ_HOUR;
    }
    /**
     * @param JJ_HOUR 
     */
    public void setJJ_HOUR(java.lang.Double JJ_HOUR) {
        this.JJ_HOUR = JJ_HOUR;
    }
    /**
     * @return JJ_WADD 
     */
    @Column(name = "J_WADD")
    @Config(key = "DSHR71.J_WADD")
    public java.lang.Double getJJ_WADD() {
        return JJ_WADD;
    }
    /**
     * @param JJ_WADD 
     */
    public void setJJ_WADD(java.lang.Double JJ_WADD) {
        this.JJ_WADD = JJ_WADD;
    }
    /**
     * @return JJ_JADD 
     */
    @Column(name = "J_JADD")
    @Config(key = "DSHR71.J_JADD")
    public java.lang.Double getJJ_JADD() {
        return JJ_JADD;
    }
    /**
     * @param JJ_JADD 
     */
    public void setJJ_JADD(java.lang.Double JJ_JADD) {
        this.JJ_JADD = JJ_JADD;
    }
    /**
     * @return UP_USER 
     */
    @Length(max = 30)
    @Column(name = "UP_USER")
    @Config(key = "DSHR71.UP_USER")
    public java.lang.String getUP_USER() {
        return UP_USER;
    }
    /**
     * @param UP_USER 
     */
    public void setUP_USER(java.lang.String UP_USER) {
        this.UP_USER = UP_USER;
    }
    /**
     * @return UP_DATE 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "UP_DATE")
    @Config(key = "DSHR71.UP_DATE")
    public java.util.Date getUP_DATE() {
        return UP_DATE;
    }
    /**
     * @param UP_DATE 
     */
    public void setUP_DATE(java.util.Date UP_DATE) {
        this.UP_DATE = UP_DATE;
    }
    /**
     * 取得MA DON VI
     * @return SEC MA DON VI
     */
    @Length(max = 5)
    @Column(name = "SEC")
    @Config(key = "DSHR71.SEC")
    public java.lang.String getSEC() {
        return SEC;
    }
    /**
     * 設定MA DON VI
     * @param SEC MA DON VI
     */
    public void setSEC(java.lang.String SEC) {
        this.SEC = SEC;
    }
    /**
     * 取得TEN XUONG
     * @return FACTNAME TEN XUONG
     */
    @Length(max = 20)
    @Column(name = "FACTNAME")
    @Config(key = "DSHR71.FACTNAME")
    public java.lang.String getFACTNAME() {
        return FACTNAME;
    }
    /**
     * 設定TEN XUONG
     * @param FACTNAME TEN XUONG
     */
    public void setFACTNAME(java.lang.String FACTNAME) {
        this.FACTNAME = FACTNAME;
    }
    /**
     * 取得Trang thai :0: ko thuoc de, 1: Trung De, 2: Dai De
     * @return IS_SOLE Trang thai :0: ko thuoc de, 1: Trung De, 2: Dai De
     */
    @Length(max = 1)
    @Column(name = "IS_SOLE")
    @Config(key = "DSHR71.IS_SOLE")
    public java.lang.String getIS_SOLE() {
        return IS_SOLE;
    }
    /**
     * 設定Trang thai :0: ko thuoc de, 1: Trung De, 2: Dai De
     * @param IS_SOLE Trang thai :0: ko thuoc de, 1: Trung De, 2: Dai De
     */
    public void setIS_SOLE(java.lang.String IS_SOLE) {
        this.IS_SOLE = IS_SOLE;
    }
}
