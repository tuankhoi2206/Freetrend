package ds.program.fvhr.domain;
import javax.persistence.IdClass;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import ds.program.fvhr.domain.pk.N_SUB_LABOURPk;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@IdClass(N_SUB_LABOURPk.class)
@Entity
@Table(name = "N_SUB_LABOUR")
public class N_SUB_LABOUR {
    private java.lang.String ID_CONTRACT;	//MA SO PLHD
    private java.util.Date DATES_SIGN;
    private java.lang.Long NEW_SAL;
    private java.lang.String NEW_JOB;
    private java.lang.String CLOCK;
    private java.util.Date DATE_EN;
    private java.lang.String IS_SUB;
    private java.lang.Long DELAY;
    private java.lang.String ID_LABOUR;	//MA SO HD, co the null vi chua ky qua HD ma da duoc len luong? co vo ly ko?
    private java.lang.String IDSUB_KIND;	//LOAI PLHD. 00001 LA TOI HAN MOI NAM MOI KY. 00002 LA KY PLHD KHI CO DIEU LUONG
    private java.lang.String CHECKED;	//KT DA KTRA HAY CHUA. Y LA DA KTRA, N OR NULL LA CHUA KTRA
    private java.lang.String NOTE;	//GHI CHU THAO TAC
    /**
     * 取得MA SO PLHD
     * @return ID_CONTRACT MA SO PLHD
     */
    @Id
    @NotBlank
    @Column(name = "ID_CONTRACT")
    @Config(key = "N_SUB_LABOUR.ID_CONTRACT")
    public java.lang.String getID_CONTRACT() {
        return ID_CONTRACT;
    }
    /**
     * 設定MA SO PLHD
     * @param ID_CONTRACT MA SO PLHD
     */
    public void setID_CONTRACT(java.lang.String ID_CONTRACT) {
        this.ID_CONTRACT = ID_CONTRACT;
    }
    /**
     * @return DATES_SIGN 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATES_SIGN")
    @Config(key = "N_SUB_LABOUR.DATES_SIGN")
    public java.util.Date getDATES_SIGN() {
        return DATES_SIGN;
    }
    /**
     * @param DATES_SIGN 
     */
    public void setDATES_SIGN(java.util.Date DATES_SIGN) {
        this.DATES_SIGN = DATES_SIGN;
    }
    /**
     * @return NEW_SAL 
     */
    @NotBlank
    @Column(name = "NEW_SAL")
    @Config(key = "N_SUB_LABOUR.NEW_SAL")
    public java.lang.Long getNEW_SAL() {
        return NEW_SAL;
    }
    /**
     * @param NEW_SAL 
     */
    public void setNEW_SAL(java.lang.Long NEW_SAL) {
        this.NEW_SAL = NEW_SAL;
    }
    /**
     * @return NEW_JOB 
     */
    @NotBlank
    @Length(max = 25)
    @Column(name = "NEW_JOB")
    @Config(key = "N_SUB_LABOUR.NEW_JOB")
    public java.lang.String getNEW_JOB() {
        return NEW_JOB;
    }
    /**
     * @param NEW_JOB 
     */
    public void setNEW_JOB(java.lang.String NEW_JOB) {
        this.NEW_JOB = NEW_JOB;
    }
    /**
     * @return CLOCK 
     */
    
    @NotBlank
    @Column(name = "CLOCK")
    @Config(key = "N_SUB_LABOUR.CLOCK")
    public java.lang.String getCLOCK() {
        return CLOCK;
    }
    /**
     * @param CLOCK 
     */
    public void setCLOCK(java.lang.String CLOCK) {
        this.CLOCK = CLOCK;
    }
    /**
     * @return DATE_EN 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_EN")
    @Config(key = "N_SUB_LABOUR.DATE_EN")
    public java.util.Date getDATE_EN() {
        return DATE_EN;
    }
    /**
     * @param DATE_EN 
     */
    public void setDATE_EN(java.util.Date DATE_EN) {
        this.DATE_EN = DATE_EN;
    }
    /**
     * @return IS_SUB 
     */
    @NotBlank
    @Length(max = 1)
    @Column(name = "IS_SUB")
    @Config(key = "N_SUB_LABOUR.IS_SUB")
    public java.lang.String getIS_SUB() {
        return IS_SUB;
    }
    /**
     * @param IS_SUB 
     */
    public void setIS_SUB(java.lang.String IS_SUB) {
        this.IS_SUB = IS_SUB;
    }
    /**
     * @return DELAY 
     */
    @Column(name = "DELAY")
    @Config(key = "N_SUB_LABOUR.DELAY")
    public java.lang.Long getDELAY() {
        return DELAY;
    }
    /**
     * @param DELAY 
     */
    public void setDELAY(java.lang.Long DELAY) {
        this.DELAY = DELAY;
    }
    /**
     * 取得MA SO HD, co the null vi chua ky qua HD ma da duoc len luong? co vo ly ko?
     * @return ID_LABOUR MA SO HD, co the null vi chua ky qua HD ma da duoc len luong? co vo ly ko?
     */
    
    @Length(max = 10)
    @Column(name = "ID_LABOUR")
    @Config(key = "N_SUB_LABOUR.ID_LABOUR")
    public java.lang.String getID_LABOUR() {
        return ID_LABOUR;
    }
    /**
     * 設定MA SO HD, co the null vi chua ky qua HD ma da duoc len luong? co vo ly ko?
     * @param ID_LABOUR MA SO HD, co the null vi chua ky qua HD ma da duoc len luong? co vo ly ko?
     */
    public void setID_LABOUR(java.lang.String ID_LABOUR) {
        this.ID_LABOUR = ID_LABOUR;
    }
    /**
     * 取得LOAI PLHD. 00001 LA TOI HAN MOI NAM MOI KY. 00002 LA KY PLHD KHI CO DIEU LUONG
     * @return IDSUB_KIND LOAI PLHD. 00001 LA TOI HAN MOI NAM MOI KY. 00002 LA KY PLHD KHI CO DIEU LUONG
     */
    @NotBlank
    @Length(max = 5)
    @Column(name = "IDSUB_KIND")
    @Config(key = "N_SUB_LABOUR.IDSUB_KIND")
    public java.lang.String getIDSUB_KIND() {
        return IDSUB_KIND;
    }
    /**
     * 設定LOAI PLHD. 00001 LA TOI HAN MOI NAM MOI KY. 00002 LA KY PLHD KHI CO DIEU LUONG
     * @param IDSUB_KIND LOAI PLHD. 00001 LA TOI HAN MOI NAM MOI KY. 00002 LA KY PLHD KHI CO DIEU LUONG
     */
    public void setIDSUB_KIND(java.lang.String IDSUB_KIND) {
        this.IDSUB_KIND = IDSUB_KIND;
    }
    /**
     * 取得KT DA KTRA HAY CHUA. Y LA DA KTRA, N OR NULL LA CHUA KTRA
     * @return CHECKED KT DA KTRA HAY CHUA. Y LA DA KTRA, N OR NULL LA CHUA KTRA
     */
    @Length(max = 1)
    @Column(name = "CHECKED")
    @Config(key = "N_SUB_LABOUR.CHECKED")
    public java.lang.String getCHECKED() {
        return CHECKED;
    }
    /**
     * 設定KT DA KTRA HAY CHUA. Y LA DA KTRA, N OR NULL LA CHUA KTRA
     * @param CHECKED KT DA KTRA HAY CHUA. Y LA DA KTRA, N OR NULL LA CHUA KTRA
     */
    public void setCHECKED(java.lang.String CHECKED) {
        this.CHECKED = CHECKED;
    }
    /**
     * 取得GHI CHU THAO TAC
     * @return NOTE GHI CHU THAO TAC
     */
    @Length(max = 150)
    @Column(name = "NOTE")
    @Config(key = "N_SUB_LABOUR.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * 設定GHI CHU THAO TAC
     * @param NOTE GHI CHU THAO TAC
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
}
