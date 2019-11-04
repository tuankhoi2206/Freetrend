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
* PHAN LOAI DON VI THEO NHOM : T.TIEP_G.TIEP_G.MAU_V.PHONG, 07/2013
**/
@Entity
@Table(name = "N_GROUPDIRECT_INDIRECT")
public class N_GROUPDIRECT_INDIRECT {
    private java.lang.String ID_DETAIL;	//BD 072013
    private java.lang.String MO_TA;
    private java.lang.String NAME_FACT;
    private java.util.Date DATES;
    private java.lang.String NOTE;
    private java.lang.String ID_GROUPKIND;	//THAM CHIEU DEN ID_GROUP TRONG N_GROUP_KIND
    private java.lang.String ID_GROUP;	//BD 072013, link toi ID_GROUP cua N_GROUP_TTGT
    /**
     * 取得BD 072013
     * @return ID_GROUP BD 072013
     */
    @Id
    @NotBlank
    @Column(name = "ID_DETAIL")
    @Config(key = "N_GROUPDIRECT_INDIRECT.ID_DETAIL")
    public java.lang.String getID_DETAIL() {
        return ID_DETAIL;
    }
    /**
     * 設定BD 072013
     * @param ID_GROUP BD 072013
     */
    public void setID_DETAIL(java.lang.String ID_DETAIL) {
        this.ID_DETAIL = ID_DETAIL;
    }
    /**
     * @return MO_TA 
     */
    @Length(max = 300)
    @Column(name = "MO_TA")
    @Config(key = "N_GROUPDIRECT_INDIRECT.MO_TA")
    public java.lang.String getMO_TA() {
        return MO_TA;
    }
    /**
     * @param DESCRIPTION_GROUP 
     */
    public void setMO_TA(java.lang.String MO_TA) {
        this.MO_TA = MO_TA;
    }
    /**
     * @return NAME_FACT 
     */
    @Length(max = 30)
    @Column(name = "NAME_FACT")
    @Config(key = "N_GROUPDIRECT_INDIRECT.NAME_FACT")
    public java.lang.String getNAME_FACT() {
        return NAME_FACT;
    }
    /**
     * @param NAME_FACT 
     */
    public void setNAME_FACT(java.lang.String NAME_FACT) {
        this.NAME_FACT = NAME_FACT;
    }
    /**
     * @return DATES 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATES")
    @Config(key = "N_GROUPDIRECT_INDIRECT.DATES")
    public java.util.Date getDATES() {
        return DATES;
    }
    /**
     * @param DATES 
     */
    public void setDATES(java.util.Date DATES) {
        this.DATES = DATES;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 250)
    @Column(name = "NOTE")
    @Config(key = "N_GROUPDIRECT_INDIRECT.NOTE")
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
     * 取得THAM CHIEU DEN ID_GROUP TRONG N_GROUP_KIND
     * @return ID_GROUPKIND THAM CHIEU DEN ID_GROUP TRONG N_GROUP_KIND
     */
    @Length(max = 5)
    @Column(name = "ID_GROUPKIND")
    @Config(key = "N_GROUPDIRECT_INDIRECT.ID_GROUPKIND")
    public java.lang.String getID_GROUPKIND() {
        return ID_GROUPKIND;
    }
    /**
     * 設定THAM CHIEU DEN ID_GROUP TRONG N_GROUP_KIND
     * @param ID_GROUPKIND THAM CHIEU DEN ID_GROUP TRONG N_GROUP_KIND
     */
    public void setID_GROUPKIND(java.lang.String ID_GROUPKIND) {
        this.ID_GROUPKIND = ID_GROUPKIND;
    }
    
    @NotBlank
    @Column(name = "ID_GROUP")
    @Config(key = "N_GROUPDIRECT_INDIRECT.ID_GROUP")
    public java.lang.String getID_GROUP() {
        return ID_GROUP;
    }
    /**
     * 設定BD 072013
     * @param ID_GROUP BD 072013
     */
    public void setID_GROUP(java.lang.String ID_GROUP) {
        this.ID_GROUP = ID_GROUP;
    }    
}
