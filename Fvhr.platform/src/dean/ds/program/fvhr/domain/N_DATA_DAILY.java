package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_DATA_DAILYPk;
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
* 
**/
@IdClass(N_DATA_DAILYPk.class)
@Entity
@Table(name = "N_DATA_DAILY")
public class N_DATA_DAILY {
    private java.lang.String EMPSN;	//data tu t03/2009
    private java.util.Date DATES;
    private java.lang.String TT_IN;
    private java.lang.String TT_MID;
    private java.lang.String TT_OUT;
    private java.lang.String TT_OVER;
    private java.lang.String ID_SHIFT;	//CA LAM VIEC
    private java.lang.Double DUCLS;	//NGAY LAM
    private java.lang.Double NUCLS;	//DEM LAM
    private java.lang.Long OTD;	//TCA NGAY
	private java.lang.Long OTN;	//TCA DEM
    private java.lang.Long OTS;	//TCA CHU NHAT
    private java.lang.Long LATE;	//TRE
    private java.lang.Long SIGN_TIME;	//SO LAN KY TEN
    private java.lang.Long ACNM;	//SO DEM HUONG TCOM
    private java.lang.String REST_RS;	//LY DO NGHI PHEP
    private java.lang.Double REST_QTT;	//TS GIO NGHI PHEP/NGAY
    private java.lang.String CHECK_DATA;
    private java.lang.Long NWHOUR;	//KHANG CONG
    private java.lang.String NOTE;
    private java.lang.String LOCKED;
    private java.lang.String UPRINT;
    private java.lang.Long MULTIPLY_WD;	//NGAY DAC BIET
    private java.lang.Long OTH;	//TCA LE
    private java.lang.Long REAL_OT;	//TCA THEO GIO QTHE_ TCA THUC
    private java.lang.Long REG_OT;	//DK TCA VOI NHAN SU
    private java.lang.Long LATE15;	//SO PHUT BI DU RA> 15 PHUT SO VOI GIO CHUAN(BAO GOM TCA NEU CO) CUA SHIFT
    
    /**
     * 取得data tu t03/2009
     * @return EMPSN data tu t03/2009
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_DATA_DAILY.EMPSN")
    public java.lang.String getEMPSN() {
        return EMPSN;
    }
    /**
     * 設定data tu t03/2009
     * @param EMPSN data tu t03/2009
     */
    public void setEMPSN(java.lang.String EMPSN) {
        this.EMPSN = EMPSN;
    }
    /**
     * @return DATES 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATES")
    @Config(key = "N_DATA_DAILY.DATES")
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
     * @return TT_IN 
     */
    @Length(max = 5)
    @Column(name = "T_IN")
    @Config(key = "N_DATA_DAILY.T_IN")
    public java.lang.String getTT_IN() {
        return TT_IN;
    }
    /**
     * @param TT_IN 
     */
    public void setTT_IN(java.lang.String TT_IN) {
        this.TT_IN = TT_IN;
    }
    /**
     * @return TT_MID 
     */
    @Length(max = 5)
    @Column(name = "T_MID")
    @Config(key = "N_DATA_DAILY.T_MID")
    public java.lang.String getTT_MID() {
        return TT_MID;
    }
    /**
     * @param TT_MID 
     */
    public void setTT_MID(java.lang.String TT_MID) {
        this.TT_MID = TT_MID;
    }
    /**
     * @return TT_OUT 
     */
    @Length(max = 5)
    @Column(name = "T_OUT")
    @Config(key = "N_DATA_DAILY.T_OUT")
    public java.lang.String getTT_OUT() {
        return TT_OUT;
    }
    /**
     * @param TT_OUT 
     */
    public void setTT_OUT(java.lang.String TT_OUT) {
        this.TT_OUT = TT_OUT;
    }
    /**
     * @return TT_OVER 
     */
    @Length(max = 5)
    @Column(name = "T_OVER")
    @Config(key = "N_DATA_DAILY.T_OVER")
    public java.lang.String getTT_OVER() {
        return TT_OVER;
    }
    /**
     * @param TT_OVER 
     */
    public void setTT_OVER(java.lang.String TT_OVER) {
        this.TT_OVER = TT_OVER;
    }
    /**
     * 取得CA LAM VIEC
     * @return ID_SHIFT CA LAM VIEC
     */
    @Length(max = 50)
    @Column(name = "ID_SHIFT")
    @Config(key = "N_DATA_DAILY.ID_SHIFT")
    public java.lang.String getID_SHIFT() {
        return ID_SHIFT;
    }
    /**
     * 設定CA LAM VIEC
     * @param ID_SHIFT CA LAM VIEC
     */
    public void setID_SHIFT(java.lang.String ID_SHIFT) {
        this.ID_SHIFT = ID_SHIFT;
    }
    /**
     * 取得NGAY LAM
     * @return DUCLS NGAY LAM
     */
    @Column(name = "DUCLS")
    @Config(key = "N_DATA_DAILY.DUCLS")
    public java.lang.Double getDUCLS() {
        return DUCLS;
    }
    /**
     * 設定NGAY LAM
     * @param DUCLS NGAY LAM
     */
    public void setDUCLS(java.lang.Double DUCLS) {
        this.DUCLS = DUCLS;
    }
    /**
     * 取得DEM LAM
     * @return NUCLS DEM LAM
     */
    @Column(name = "NUCLS")
    @Config(key = "N_DATA_DAILY.NUCLS")
    public java.lang.Double getNUCLS() {
        return NUCLS;
    }
    /**
     * 設定DEM LAM
     * @param NUCLS DEM LAM
     */
    public void setNUCLS(java.lang.Double NUCLS) {
        this.NUCLS = NUCLS;
    }
    /**
     * 取得TCA NGAY
     * @return OTD TCA NGAY
     */
    @Column(name = "OTD")
    @Config(key = "N_DATA_DAILY.OTD")
    public java.lang.Long getOTD() {
        return OTD;
    }
    /**
     * 設定TCA NGAY
     * @param OTD TCA NGAY
     */
    public void setOTD(java.lang.Long OTD) {
        this.OTD = OTD;
    }
    /**
     * 取得TCA DEM
     * @return OTN TCA DEM
     */
    @Column(name = "OTN")
    @Config(key = "N_DATA_DAILY.OTN")
    public java.lang.Long getOTN() {
        return OTN;
    }
    /**
     * 設定TCA DEM
     * @param OTN TCA DEM
     */
    public void setOTN(java.lang.Long OTN) {
        this.OTN = OTN;
    }
    /**
     * 取得TCA CHU NHAT
     * @return OTS TCA CHU NHAT
     */
    @Column(name = "OTS")
    @Config(key = "N_DATA_DAILY.OTS")
    public java.lang.Long getOTS() {
        return OTS;
    }
    /**
     * 設定TCA CHU NHAT
     * @param OTS TCA CHU NHAT
     */
    public void setOTS(java.lang.Long OTS) {
        this.OTS = OTS;
    }
    /**
     * 取得TRE
     * @return LATE TRE
     */
    @Column(name = "LATE")
    @Config(key = "N_DATA_DAILY.LATE")
    public java.lang.Long getLATE() {
        return LATE;
    }
    /**
     * 設定TRE
     * @param LATE TRE
     */
    public void setLATE(java.lang.Long LATE) {
        this.LATE = LATE;
    }
    /**
     * 取得SO LAN KY TEN
     * @return SIGN_TIME SO LAN KY TEN
     */
    @Column(name = "SIGN_TIME")
    @Config(key = "N_DATA_DAILY.SIGN_TIME")
    public java.lang.Long getSIGN_TIME() {
        return SIGN_TIME;
    }
    /**
     * 設定SO LAN KY TEN
     * @param SIGN_TIME SO LAN KY TEN
     */
    public void setSIGN_TIME(java.lang.Long SIGN_TIME) {
        this.SIGN_TIME = SIGN_TIME;
    }
    /**
     * 取得SO DEM HUONG TCOM
     * @return ACNM SO DEM HUONG TCOM
     */
    @Column(name = "ACNM")
    @Config(key = "N_DATA_DAILY.ACNM")
    public java.lang.Long getACNM() {
        return ACNM;
    }
    /**
     * 設定SO DEM HUONG TCOM
     * @param ACNM SO DEM HUONG TCOM
     */
    public void setACNM(java.lang.Long ACNM) {
        this.ACNM = ACNM;
    }
    /**
     * 取得LY DO NGHI PHEP
     * @return REST_RS LY DO NGHI PHEP
     */
    @Length(max = 5)
    @Column(name = "REST_RS")
    @Config(key = "N_DATA_DAILY.REST_RS")
    public java.lang.String getREST_RS() {
        return REST_RS;
    }
    /**
     * 設定LY DO NGHI PHEP
     * @param REST_RS LY DO NGHI PHEP
     */
    public void setREST_RS(java.lang.String REST_RS) {
        this.REST_RS = REST_RS;
    }
    /**
     * 取得TS GIO NGHI PHEP/NGAY
     * @return REST_QTT TS GIO NGHI PHEP/NGAY
     */
    @Column(name = "REST_QTT")
    @Config(key = "N_DATA_DAILY.REST_QTT")
    public java.lang.Double getREST_QTT() {
        return REST_QTT;
    }
    /**
     * 設定TS GIO NGHI PHEP/NGAY
     * @param REST_QTT TS GIO NGHI PHEP/NGAY
     */
    public void setREST_QTT(java.lang.Double REST_QTT) {
        this.REST_QTT = REST_QTT;
    }
    /**
     * @return CHECK_DATA 
     */
    @Length(max = 1)
    @Column(name = "CHECK_DATA")
    @Config(key = "N_DATA_DAILY.CHECK_DATA")
    public java.lang.String getCHECK_DATA() {
        return CHECK_DATA;
    }
    /**
     * @param CHECK_DATA 
     */
    public void setCHECK_DATA(java.lang.String CHECK_DATA) {
        this.CHECK_DATA = CHECK_DATA;
    }
    /**
     * 取得KHANG CONG
     * @return NWHOUR KHANG CONG
     */
    @Column(name = "NWHOUR")
    @Config(key = "N_DATA_DAILY.NWHOUR")
    public java.lang.Long getNWHOUR() {
        return NWHOUR;
    }
    /**
     * 設定KHANG CONG
     * @param NWHOUR KHANG CONG
     */
    public void setNWHOUR(java.lang.Long NWHOUR) {
        this.NWHOUR = NWHOUR;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 50)
    @Column(name = "NOTE")
    @Config(key = "N_DATA_DAILY.NOTE")
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
     * @return LOCKED 
     */
    @Length(max = 1)
    @Column(name = "LOCKED")
    @Config(key = "N_DATA_DAILY.LOCKED")
    public java.lang.String getLOCKED() {
        return LOCKED;
    }
    /**
     * @param LOCKED 
     */
    public void setLOCKED(java.lang.String LOCKED) {
        this.LOCKED = LOCKED;
    }
    /**
     * @return UPRINT 
     */
    @Length(max = 1)
    @Column(name = "UPRINT")
    @Config(key = "N_DATA_DAILY.UPRINT")
    public java.lang.String getUPRINT() {
        return UPRINT;
    }
    /**
     * @param UPRINT 
     */
    public void setUPRINT(java.lang.String UPRINT) {
        this.UPRINT = UPRINT;
    }
    /**
     * 取得NGAY DAC BIET
     * @return MULTIPLY_WD NGAY DAC BIET
     */
    @Column(name = "MULTIPLY_WD")
    @Config(key = "N_DATA_DAILY.MULTIPLY_WD")
    public java.lang.Long getMULTIPLY_WD() {
        return MULTIPLY_WD;
    }
    /**
     * 設定NGAY DAC BIET
     * @param MULTIPLY_WD NGAY DAC BIET
     */
    public void setMULTIPLY_WD(java.lang.Long MULTIPLY_WD) {
        this.MULTIPLY_WD = MULTIPLY_WD;
    }
    /**
     * 取得TCA LE
     * @return OTH TCA LE
     */
    @Column(name = "OTH")
    @Config(key = "N_DATA_DAILY.OTH")
    public java.lang.Long getOTH() {
        return OTH;
    }
    /**
     * 設定TCA LE
     * @param OTH TCA LE
     */
    public void setOTH(java.lang.Long OTH) {
        this.OTH = OTH;
    }
    /**
     * 取得TCA THEO GIO QTHE_ TCA THUC
     * @return REAL_OT TCA THEO GIO QTHE_ TCA THUC
     */
    @Column(name = "REAL_OT")
    @Config(key = "N_DATA_DAILY.REAL_OT")
    public java.lang.Long getREAL_OT() {
        return REAL_OT;
    }
    /**
     * 設定TCA THEO GIO QTHE_ TCA THUC
     * @param REAL_OT TCA THEO GIO QTHE_ TCA THUC
     */
    public void setREAL_OT(java.lang.Long REAL_OT) {
        this.REAL_OT = REAL_OT;
    }
    /**
     * 取得DK TCA VOI NHAN SU
     * @return REG_OT DK TCA VOI NHAN SU
     */
    @Column(name = "REG_OT")
    @Config(key = "N_DATA_DAILY.REG_OT")
    public java.lang.Long getREG_OT() {
        return REG_OT;
    }
    /**
     * 設定DK TCA VOI NHAN SU
     * @param REG_OT DK TCA VOI NHAN SU
     */
    public void setREG_OT(java.lang.Long REG_OT) {
        this.REG_OT = REG_OT;
    }    
    /**
     * 取得SO PHUT BI DU RA > 15 PHUT SO VOI GIO CHUAN( BAO GOM TCA NEU CO) SO VOI ID_SHIFT
     * @return LATE15
     */
    @Column(name = "LATE15")
    @Config(key = "N_DATA_DAILY.LATE15")    
    public java.lang.Long getLATE15() {
		return LATE15;
	}
    /**
     * 設定SO PHUT BI DU RA > 15 PHUT SO VOI GIO CHUAN( BAO GOM TCA NEU CO) SO VOI ID_SHIFT
     * @param LATE15
     */    
	public void setLATE15(java.lang.Long lATE15) {
		LATE15 = lATE15;
	}    
}
