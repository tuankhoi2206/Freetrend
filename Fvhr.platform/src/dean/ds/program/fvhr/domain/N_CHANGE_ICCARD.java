package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.*;
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
* DOI THE IC
**/
@IdClass(N_CHANGE_ICCARDPk.class)
@Entity
@Table(name = "N_CHANGE_ICCARD")
public class N_CHANGE_ICCARD {
    private java.lang.String EMPSN;	//THE DEO
    private java.util.Date DATE_CHANGE;	//NGAY DOI
    private java.lang.String EMPCN_OLD;	//THE IC CU
    private java.lang.String EMPCN_NEW;	//THE IC MOI
    private java.lang.String REASON;	//LY DO DOI
    private java.lang.String TEMP;
    /**
     * 取得THE DEO
     * @return EMPSN THE DEO
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_CHANGE_ICCARD.EMPSN")
    public java.lang.String getEMPSN() {
        return EMPSN;
    }
    /**
     * 設定THE DEO
     * @param EMPSN THE DEO
     */
    public void setEMPSN(java.lang.String EMPSN) {
        this.EMPSN = EMPSN;
    }
    /**
     * 取得NGAY DOI
     * @return DATE_CHANGE NGAY DOI
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_CHANGE")
    @Config(key = "N_CHANGE_ICCARD.DATE_CHANGE")
    public java.util.Date getDATE_CHANGE() {
        return DATE_CHANGE;
    }
    /**
     * 設定NGAY DOI
     * @param DATE_CHANGE NGAY DOI
     */
    public void setDATE_CHANGE(java.util.Date DATE_CHANGE) {
        this.DATE_CHANGE = DATE_CHANGE;
    }
    /**
     * 取得THE IC CU
     * @return EMPCN_OLD THE IC CU
     */
    @Length(max = 10)
    @Column(name = "EMPCN_OLD")
    @Config(key = "N_CHANGE_ICCARD.EMPCN_OLD")
    public java.lang.String getEMPCN_OLD() {
        return EMPCN_OLD;
    }
    /**
     * 設定THE IC CU
     * @param EMPCN_OLD THE IC CU
     */
    public void setEMPCN_OLD(java.lang.String EMPCN_OLD) {
        this.EMPCN_OLD = EMPCN_OLD;
    }
    /**
     * 取得THE IC MOI
     * @return EMPCN_NEW THE IC MOI
     */
    @Id
    @NotBlank
    @Column(name = "EMPCN_NEW")
    @Config(key = "N_CHANGE_ICCARD.EMPCN_NEW")
    public java.lang.String getEMPCN_NEW() {
        return EMPCN_NEW;
    }
    /**
     * 設定THE IC MOI
     * @param EMPCN_NEW THE IC MOI
     */
    public void setEMPCN_NEW(java.lang.String EMPCN_NEW) {
        this.EMPCN_NEW = EMPCN_NEW;
    }
    /**
     * 取得LY DO DOI
     * @return REASON LY DO DOI
     */
    @Length(max = 30)
    @Column(name = "REASON")
    @Config(key = "N_CHANGE_ICCARD.REASON")
    public java.lang.String getREASON() {
        return REASON;
    }
    /**
     * 設定LY DO DOI
     * @param REASON LY DO DOI
     */
    public void setREASON(java.lang.String REASON) {
        this.REASON = REASON;
    }
    /**
     * @return TEMP 
     */
    @Length(max = 10)
    @Column(name = "TEMP")
    @Config(key = "N_CHANGE_ICCARD.TEMP")
    public java.lang.String getTEMP() {
        return TEMP;
    }
    /**
     * @param TEMP 
     */
    public void setTEMP(java.lang.String TEMP) {
        this.TEMP = TEMP;
    }
}
