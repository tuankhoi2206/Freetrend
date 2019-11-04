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
* THAN NHAN: TRO CAP CON NHO <6 TUOI(NSU NHAP) + NGUOI PHU THUOC NEU CO(KTOAN NHAP)
**/
@Entity
@Table(name = "N_EMP_RELATIVE")
public class N_EMP_RELATIVE {
    private java.lang.String IDKEY;	//=EMPSN+XX, VD 0511039601, BD SU DUNG 05/2013. KO DON CHUNG VOI NGUOI LE THUOC DUOC VI CHUA CHAC BEGINDATE GIONG NHAU
    private java.lang.String EMPSN;	//SO THE
    private java.util.Date BEGINDATE;	//THOI GIAN HIEU LUC
    private java.util.Date CONFIRM_DATE;	//NGAY DANG KY, DV TRO CAP CON NHO <=20/06 THI SE AP DUNG CHO T06 ELSE T07
    private java.lang.String NAME_RELATIVE;	//HO TEN NGUOI THAN
    private java.util.Date BIRTHDAY;	//NGAY SINH CUA THAN NHAN
    private java.util.Date ENDDATE;	//THOI GIAN HET HAN ( TRO CAP CON NHO+ NGUOI LE THUOC NEU CO), AUTO HAY NSU VA KTOAN QUAN LY?
    private java.lang.String IDKIND;	//LOAI THAN NHAN (ONG, BA, CHA, ME,ANH, CHI, CON........)
    private java.lang.String SEX;	//GIOI TINH
    private java.lang.String USER_UPDATE;	//LA PB_USERID BEN DSPB02, USER CUOI CUNG THAO TAC
    private java.util.Date DATE_UPDATE;	//THOI GIAN CUOI CUNG THAO TAC
    /**
     * 取得=EMPSN+XX, VD 0511039601, BD SU DUNG 05/2013. KO DON CHUNG VOI NGUOI LE THUOC DUOC VI CHUA CHAC BEGINDATE GIONG NHAU
     * @return IDKEY =EMPSN+XX, VD 0511039601, BD SU DUNG 05/2013. KO DON CHUNG VOI NGUOI LE THUOC DUOC VI CHUA CHAC BEGINDATE GIONG NHAU
     */
    @Id
    @NotBlank
    @Column(name = "IDKEY")
    @Config(key = "N_EMP_RELATIVE.IDKEY")
    public java.lang.String getIDKEY() {
        return IDKEY;
    }
    /**
     * 設定=EMPSN+XX, VD 0511039601, BD SU DUNG 05/2013. KO DON CHUNG VOI NGUOI LE THUOC DUOC VI CHUA CHAC BEGINDATE GIONG NHAU
     * @param IDKEY =EMPSN+XX, VD 0511039601, BD SU DUNG 05/2013. KO DON CHUNG VOI NGUOI LE THUOC DUOC VI CHUA CHAC BEGINDATE GIONG NHAU
     */
    public void setIDKEY(java.lang.String IDKEY) {
        this.IDKEY = IDKEY;
    }
    /**
     * 取得SO THE
     * @return EMPSN SO THE
     */
    @NotBlank
    @Length(max = 8)    
    @Column(name = "EMPSN")    
    @Config(key = "N_EMP_RELATIVE.EMPSN")
    public java.lang.String getEMPSN() {
        return EMPSN;
    }
    /**
     * 設定SO THE
     * @param EMPSN SO THE
     */
    public void setEMPSN(java.lang.String EMPSN) {
        this.EMPSN = EMPSN;
    }
    /**
     * 取得THOI GIAN HIEU LUC
     * @return BEGINDATE THOI GIAN HIEU LUC
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "BEGINDATE")
    @Config(key = "N_EMP_RELATIVE.BEGINDATE")
    public java.util.Date getBEGINDATE() {
        return BEGINDATE;
    }
    /**
     * 設定THOI GIAN HIEU LUC
     * @param BEGINDATE THOI GIAN HIEU LUC
     */
    public void setBEGINDATE(java.util.Date BEGINDATE) {
        this.BEGINDATE = BEGINDATE;
    }
    /**
     * 取得NGAY DANG KY, DV TRO CAP CON NHO <=20/06 THI SE AP DUNG CHO T06 ELSE T07
     * @return CONFIRM_DATE NGAY DANG KY, DV TRO CAP CON NHO <=20/06 THI SE AP DUNG CHO T06 ELSE T07
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "CONFIRM_DATE")
    @Config(key = "N_EMP_RELATIVE.CONFIRM_DATE")
    public java.util.Date getCONFIRM_DATE() {
        return CONFIRM_DATE;
    }
    /**
     * 設定NGAY DANG KY, DV TRO CAP CON NHO <=20/06 THI SE AP DUNG CHO T06 ELSE T07
     * @param CONFIRM_DATE NGAY DANG KY, DV TRO CAP CON NHO <=20/06 THI SE AP DUNG CHO T06 ELSE T07
     */
    public void setCONFIRM_DATE(java.util.Date CONFIRM_DATE) {
        this.CONFIRM_DATE = CONFIRM_DATE;
    }
    /**
     * 取得HO TEN NGUOI THAN
     * @return NAME_RELATIVE HO TEN NGUOI THAN
     */
    @NotBlank
    @Length(max = 60)
    @Column(name = "NAME_RELATIVE")
    @Config(key = "N_EMP_RELATIVE.NAME_RELATIVE")
    public java.lang.String getNAME_RELATIVE() {
        return NAME_RELATIVE;
    }
    /**
     * 設定HO TEN NGUOI THAN
     * @param NAME_RELATIVE HO TEN NGUOI THAN
     */
    public void setNAME_RELATIVE(java.lang.String NAME_RELATIVE) {
        this.NAME_RELATIVE = NAME_RELATIVE;
    }
    /**
     * 取得NGAY SINH CUA THAN NHAN
     * @return BIRTHDAY NGAY SINH CUA THAN NHAN
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTHDAY")
    @Config(key = "N_EMP_RELATIVE.BIRTHDAY")
    public java.util.Date getBIRTHDAY() {
        return BIRTHDAY;
    }
    /**
     * 設定NGAY SINH CUA THAN NHAN
     * @param BIRTHDAY NGAY SINH CUA THAN NHAN
     */
    public void setBIRTHDAY(java.util.Date BIRTHDAY) {
        this.BIRTHDAY = BIRTHDAY;
    }
    /**
     * 取得THOI GIAN HET HAN ( TRO CAP CON NHO+ NGUOI LE THUOC NEU CO), AUTO HAY NSU VA KTOAN QUAN LY?
     * @return ENDDATE THOI GIAN HET HAN ( TRO CAP CON NHO+ NGUOI LE THUOC NEU CO), AUTO HAY NSU VA KTOAN QUAN LY?
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "ENDDATE")
    @Config(key = "N_EMP_RELATIVE.ENDDATE")
    public java.util.Date getENDDATE() {
        return ENDDATE;
    }
    /**
     * 設定THOI GIAN HET HAN ( TRO CAP CON NHO+ NGUOI LE THUOC NEU CO), AUTO HAY NSU VA KTOAN QUAN LY?
     * @param ENDDATE THOI GIAN HET HAN ( TRO CAP CON NHO+ NGUOI LE THUOC NEU CO), AUTO HAY NSU VA KTOAN QUAN LY?
     */
    public void setENDDATE(java.util.Date ENDDATE) {
        this.ENDDATE = ENDDATE;
    }
    /**
     * 取得LOAI THAN NHAN (ONG, BA, CHA, ME,ANH, CHI, CON........)
     * @return IDKIND LOAI THAN NHAN (ONG, BA, CHA, ME,ANH, CHI, CON........)
     */
    @NotBlank
    @Length(max=5)
    @Column(name = "IDKIND")
    @Config(key = "N_EMP_RELATIVE.IDKIND")
    public java.lang.String getIDKIND() {
        return IDKIND;
    }
    /**
     * 設定LOAI THAN NHAN (ONG, BA, CHA, ME,ANH, CHI, CON........)
     * @param IDKIND LOAI THAN NHAN (ONG, BA, CHA, ME,ANH, CHI, CON........)
     */
    public void setIDKIND(java.lang.String IDKIND) {
        this.IDKIND = IDKIND;
    }
    /**
     * 取得GIOI TINH
     * @return SEX GIOI TINH
     */
    @Length(max = 10)
    @Column(name = "SEX")
    @Config(key = "N_EMP_RELATIVE.SEX")
    public java.lang.String getSEX() {
        return SEX;
    }
    /**
     * 設定GIOI TINH
     * @param SEX GIOI TINH
     */
    public void setSEX(java.lang.String SEX) {
        this.SEX = SEX;
    }
    /**
     * 取得LA PB_USERID BEN DSPB02, USER CUOI CUNG THAO TAC
     * @return USER_UPDATE LA PB_USERID BEN DSPB02, USER CUOI CUNG THAO TAC
     */
    @Length(max = 50)
    @Column(name = "USER_UPDATE")        
    @Config(key = "N_EMP_RELATIVE.USER_UPDATE")
    public java.lang.String getUSER_UPDATE() {
        return USER_UPDATE;
    }
    /**
     * 設定LA PB_USERID BEN DSPB02, USER CUOI CUNG THAO TAC
     * @param USER_UPDATE LA PB_USERID BEN DSPB02, USER CUOI CUNG THAO TAC
     */
    public void setUSER_UPDATE(java.lang.String USER_UPDATE) {
        this.USER_UPDATE = USER_UPDATE;
    }
    /**
     * 取得THOI GIAN CUOI CUNG THAO TAC
     * @return DATE_UPDATE THOI GIAN CUOI CUNG THAO TAC
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_UPDATE")
    @Config(key = "N_EMP_RELATIVE.DATE_UPDATE")
    public java.util.Date getDATE_UPDATE() {
        return DATE_UPDATE;
    }
    /**
     * 設定THOI GIAN CUOI CUNG THAO TAC
     * @param DATE_UPDATE THOI GIAN CUOI CUNG THAO TAC
     */
    public void setDATE_UPDATE(java.util.Date DATE_UPDATE) {
        this.DATE_UPDATE = DATE_UPDATE;
    }
}
