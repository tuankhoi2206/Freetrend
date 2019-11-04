package ds.program.fvhr.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import org.hibernate.validator.Length;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@Entity
@Table(name = "N_TRAINING_ITEM")
public class N_TRAINING_ITEM {
    private java.lang.String EMPSN;
    private String ID_KHOA;
    private String ID_MON;
    private java.util.Date BDATE;	//NGAY BD HOC MH A CUA KHOC B CUA NV, <=NGAY BD CUA KHOA HOC
    private java.util.Date EDATE;	//NGAY KT HOC MH A CUA KHOC B CUA NV <= NGAY KT CUA KHOA HOC
    private java.lang.String TRAINING_TYPE;
    private java.lang.String NOTE;
    private java.lang.String ID_TRAINING;	//ID TU PHAT SINH LA SO THE CONG VOI SO LAN DUOC HUAN LUYEN
    private String EMPSN_HL;	//CB HL MON HOC
    /**
     * @return EMPSN 
     */
    @NotBlank
    @Length(max = 8)
    @Column(name = "EMPSN")
    @Config(key = "N_TRAINING_ITEM.EMPSN")
    public java.lang.String getEMPSN() {
        return EMPSN;
    }
    /**
     * @param EMPSN 
     */
    public void setEMPSN(java.lang.String EMPSN) {
        this.EMPSN = EMPSN;
    }
    /**
     * @return ID_KHOA 
     */
    @NotBlank
    @Length(max = 5)
    @Column(name = "ID_KHOA")
    @Config(key = "N_TRAINING_ITEM.ID_KHOA")
    public String getID_KHOA() {
        return ID_KHOA;
    }
    /**
     * @param ID_KHOA 
     */
    public void setID_KHOA(String ID_KHOA) {
        this.ID_KHOA = ID_KHOA;
    }
    /**
     * @return ID_MON 
     */
    @NotBlank
    @Length(max = 6)
    @Column(name = "ID_MON")
    @Config(key = "N_TRAINING_ITEM.ID_MON")
    public String getID_MON() {
        return ID_MON;
    }
    /**
     * @param ID_MON 
     */
    public void setID_MON(String ID_MON) {
        this.ID_MON = ID_MON;
    }
    /**
     * 取得NGAY BD HOC MH A CUA KHOC B CUA NV, <=NGAY BD CUA KHOA HOC
     * @return BDATE NGAY BD HOC MH A CUA KHOC B CUA NV, <=NGAY BD CUA KHOA HOC
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "BDATE")
    @Config(key = "N_TRAINING_ITEM.BDATE")
    public java.util.Date getBDATE() {
        return BDATE;
    }
    /**
     * 設定NGAY BD HOC MH A CUA KHOC B CUA NV, <=NGAY BD CUA KHOA HOC
     * @param BDATE NGAY BD HOC MH A CUA KHOC B CUA NV, <=NGAY BD CUA KHOA HOC
     */
    public void setBDATE(java.util.Date BDATE) {
        this.BDATE = BDATE;
    }
    /**
     * 取得NGAY KT HOC MH A CUA KHOC B CUA NV <= NGAY KT CUA KHOA HOC
     * @return EDATE NGAY KT HOC MH A CUA KHOC B CUA NV <= NGAY KT CUA KHOA HOC
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "EDATE")
    @Config(key = "N_TRAINING_ITEM.EDATE")
    public java.util.Date getEDATE() {
        return EDATE;
    }
    /**
     * 設定NGAY KT HOC MH A CUA KHOC B CUA NV <= NGAY KT CUA KHOA HOC
     * @param EDATE NGAY KT HOC MH A CUA KHOC B CUA NV <= NGAY KT CUA KHOA HOC
     */
    public void setEDATE(java.util.Date EDATE) {
        this.EDATE = EDATE;
    }
    /**
     * @return TRAINING_TYPE 
     */
    @Length(max = 20)
    @Column(name = "TRAINING_TYPE")
    @Config(key = "N_TRAINING_ITEM.TRAINING_TYPE")
    public java.lang.String getTRAINING_TYPE() {
        return TRAINING_TYPE;
    }
    /**
     * @param TRAINING_TYPE 
     */
    public void setTRAINING_TYPE(java.lang.String TRAINING_TYPE) {
        this.TRAINING_TYPE = TRAINING_TYPE;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 30)
    @Column(name = "NOTE")
    @Config(key = "N_TRAINING_ITEM.NOTE")
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
     * 取得ID TU PHAT SINH LA SO THE CONG VOI SO LAN DUOC HUAN LUYEN
     * @return ID_TRAINING ID TU PHAT SINH LA SO THE CONG VOI SO LAN DUOC HUAN LUYEN
     */
    @Id
    @NotBlank
    @Column(name = "ID_TRAINING")
    @Config(key = "N_TRAINING_ITEM.ID_TRAINING")
    public java.lang.String getID_TRAINING() {
        return ID_TRAINING;
    }
    /**
     * 設定ID TU PHAT SINH LA SO THE CONG VOI SO LAN DUOC HUAN LUYEN
     * @param ID_TRAINING ID TU PHAT SINH LA SO THE CONG VOI SO LAN DUOC HUAN LUYEN
     */
    public void setID_TRAINING(java.lang.String ID_TRAINING) {
        this.ID_TRAINING = ID_TRAINING;
    }
    /**
     * 取得CB HL MON HOC
     * @return EMPSN_HL CB HL MON HOC
     */
    @Length(max = 8)
    @Column(name = "EMPSN_HL")
    @Config(key = "N_TRAINING_ITEM.EMPSN_HL")
    public String getEMPSN_HL() {
        return EMPSN_HL;
    }
    /**
     * 設定CB HL MON HOC
     * @param EMPSN_HL CB HL MON HOC
     */
    public void setEMPSN_HL(String EMPSN_HL) {
        this.EMPSN_HL = EMPSN_HL;
    }
}
