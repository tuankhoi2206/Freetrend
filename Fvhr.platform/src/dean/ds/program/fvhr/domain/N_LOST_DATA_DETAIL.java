package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_LOST_DATA_DETAILPk;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* XAC NHAN DL QUET THE KHAC THUONG
**/
@IdClass(N_LOST_DATA_DETAILPk.class)
@Entity
@Table(name = "N_LOST_DATA_DETAIL")
public class N_LOST_DATA_DETAIL {
    private java.lang.String EMPSN;	//THE DEO
    private java.util.Date DATE_LOST;	//NGAY BI MAT DU LIEU
    private java.lang.Long SIGN_TIME;	//SO LAN KY TEN(CO TRU TIEN)
    private java.lang.String REASONS;	//LY DO MAT DL
    private java.lang.String TIN;	//GIO VAO
    private java.lang.String TMID;	//GIO GIUA
    private java.lang.String TOUT;	//GIO TA
    private java.lang.String TOVER;	//GIO TANG CA
    private java.lang.String TTEMP;	//GIO RA CA CUA CA 3 (NGAY TRUOC)
    /**
     * 取得THE DEO
     * @return EMPSN THE DEO
     */
    @Id
    @NotBlank
    @Config(key = "N_LOST_DATA_DETAIL.EMPSN")
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
     * 取得NGAY BI MAT DU LIEU
     * @return DATE_LOST NGAY BI MAT DU LIEU
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_LOST")
    @Config(key = "N_LOST_DATA_DETAIL.DATE_LOST")
    public java.util.Date getDATE_LOST() {
        return DATE_LOST;
    }
    /**
     * 設定NGAY BI MAT DU LIEU
     * @param DATE_LOST NGAY BI MAT DU LIEU
     */
    public void setDATE_LOST(java.util.Date DATE_LOST) {
        this.DATE_LOST = DATE_LOST;
    }
    /**
     * 取得SO LAN KY TEN(CO TRU TIEN)
     * @return SIGN_TIME SO LAN KY TEN(CO TRU TIEN)
     */
    @Column(name = "SIGN_TIME")
    @Config(key = "N_LOST_DATA_DETAIL.SIGN_TIME")
    public java.lang.Long getSIGN_TIME() {
        return SIGN_TIME;
    }
    /**
     * 設定SO LAN KY TEN(CO TRU TIEN)
     * @param SIGN_TIME SO LAN KY TEN(CO TRU TIEN)
     */
    public void setSIGN_TIME(java.lang.Long SIGN_TIME) {
        this.SIGN_TIME = SIGN_TIME;
    }
    /**
     * 取得LY DO MAT DL
     * @return REASONS LY DO MAT DL
     */
    @Length(max = 15)
    @Column(name = "REASONS")
    @Config(key = "N_LOST_DATA_DETAIL.REASONS")
    public java.lang.String getREASONS() {
        return REASONS;
    }
    /**
     * 設定LY DO MAT DL
     * @param REASONS LY DO MAT DL
     */
    public void setREASONS(java.lang.String REASONS) {
        this.REASONS = REASONS;
    }
    /**
     * 取得GIO VAO
     * @return TIN GIO VAO
     */
    @Length(max = 4)
    @Column(name = "TIN")
    @Config(key = "N_LOST_DATA_DETAIL.TIN")
    public java.lang.String getTIN() {
        return TIN;
    }
    /**
     * 設定GIO VAO
     * @param TIN GIO VAO
     */
    public void setTIN(java.lang.String TIN) {
        this.TIN = TIN;
    }
    /**
     * 取得GIO GIUA
     * @return TMID GIO GIUA
     */
    @Length(max = 4)
    @Column(name = "TMID")
    @Config(key = "N_LOST_DATA_DETAIL.TMID")
    public java.lang.String getTMID() {
        return TMID;
    }
    /**
     * 設定GIO GIUA
     * @param TMID GIO GIUA
     */
    public void setTMID(java.lang.String TMID) {
        this.TMID = TMID;
    }
    /**
     * 取得GIO TA
     * @return TOUT GIO TA
     */
    @Length(max = 4)
    @Column(name = "TOUT")
    @Config(key = "N_LOST_DATA_DETAIL.TOUT")
    public java.lang.String getTOUT() {
        return TOUT;
    }
    /**
     * 設定GIO TA
     * @param TOUT GIO TA
     */
    public void setTOUT(java.lang.String TOUT) {
        this.TOUT = TOUT;
    }
    /**
     * 取得GIO TANG CA
     * @return TOVER GIO TANG CA
     */
    @Length(max = 4)
    @Column(name = "TOVER")
    @Config(key = "N_LOST_DATA_DETAIL.TOVER")
    public java.lang.String getTOVER() {
        return TOVER;
    }
    /**
     * 設定GIO TANG CA
     * @param TOVER GIO TANG CA
     */
    public void setTOVER(java.lang.String TOVER) {
        this.TOVER = TOVER;
    }
    /**
     * 取得GIO RA CA CUA CA 3 (NGAY TRUOC)
     * @return TTEMP GIO RA CA CUA CA 3 (NGAY TRUOC)
     */
    @Length(max = 4)
    @Column(name = "TTEMP")
    @Config(key = "N_LOST_DATA_DETAIL.TTEMP")
    public java.lang.String getTTEMP() {
        return TTEMP;
    }
    /**
     * 設定GIO RA CA CUA CA 3 (NGAY TRUOC)
     * @param TTEMP GIO RA CA CUA CA 3 (NGAY TRUOC)
     */
    public void setTTEMP(java.lang.String TTEMP) {
        this.TTEMP = TTEMP;
    }
    private N_EMPLOYEE EMPSN_Object;
    /**
     * 取得THE DEO
     * @return EMPSN THE DEO
     */
    @ManyToOne
    @JoinColumn(name="EMPSN", referencedColumnName="EMPSN", insertable=false, updatable=false)
    @NotFound(action=NotFoundAction.IGNORE)
    @Config(key = "N_LOST_DATA_DETAIL.EMPSN")
    public N_EMPLOYEE getEMPSN_Object() {
        return EMPSN_Object;
    }
    /**
     * 設定THE DEO
     * @param EMPSN THE DEO
     */
    public void setEMPSN_Object(N_EMPLOYEE EMPSN) {
        this.EMPSN_Object = EMPSN;
    }
}
