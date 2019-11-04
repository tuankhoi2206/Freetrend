package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_REST_DETAILPk;
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
@IdClass(N_REST_DETAILPk.class)
@Entity
@Table(name = "N_REST_DETAIL")
public class N_REST_DETAIL {
	
    private java.lang.String EMPSN;
    private java.util.Date DATE_OFF;
    private Float TOTAL;	//= hour/8 (ti le so gio nghi/ngay), (cho nhung procedure vo van truoc kia)
    private java.lang.String LOCKED;	//cai nay de lam cai j vay troi. 02/2012 SD de xet dk thuong chuyen can T02-T03/2012, if Y thi ok, N thi or Null thi ko duoc
    private java.lang.String REST_KIND;	//ly do nghi phep (phep nam, ban, benh, khang cong....)
    private java.lang.String REST_TYPE;	//ly do neu nghi phep nam (tet,cty,cnv,ton)
    private java.lang.String REST_SAL;	//du thua qua
    private java.lang.String TIN;	//son khong can
    private java.lang.String TOUT;	//son khong can
    private java.lang.String MID_TIME;	//son khong can
    private java.lang.String NOTE;	//GHI CHU DE HOTRO XUAT TRO CAP 100% VA 75% CHO CTBH
    private java.lang.String NOTE1;	//BEGIN T07/2009.GHI CHU DE HOTRO XUAT TRO CAP 100% VA 75% CHO CTBH , PBIET PHEP NGAN VA DAI NGAY
    private java.lang.String YEAR;	//add by son : ref to N_Rest.year
    private java.lang.String REASON;	//add by son, but not used
    private java.lang.String USER_UP;	//add by Son
    private java.util.Date DATE_UP;	//add by Son
    private java.lang.String NOTE_TIME;	//add by Son : note time in, time out
    private java.lang.Long HOUR;	//add by Son : so gio nghi/ngay, mac dinh = 8
    
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_REST_DETAIL.EMPSN")
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
     * @return DATE_OFF 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OFF")
    @Config(key = "N_REST_DETAIL.DATE_OFF")
    public java.util.Date getDATE_OFF() {
        return DATE_OFF;
    }
    /**
     * @param DATE_OFF 
     */
    public void setDATE_OFF(java.util.Date DATE_OFF) {
        this.DATE_OFF = DATE_OFF;
    }
    /**
     * 取得= hour/8 (ti le so gio nghi/ngay), (cho nhung procedure vo van truoc kia)
     * @return TOTAL = hour/8 (ti le so gio nghi/ngay), (cho nhung procedure vo van truoc kia)
     */
    @Column(name = "TOTAL")
    @Config(key = "N_REST_DETAIL.TOTAL")
    public java.lang.Float getTOTAL() {
        return TOTAL;
    }
    /**
     * 設定= hour/8 (ti le so gio nghi/ngay), (cho nhung procedure vo van truoc kia)
     * @param TOTAL = hour/8 (ti le so gio nghi/ngay), (cho nhung procedure vo van truoc kia)
     */
    public void setTOTAL(java.lang.Float TOTAL) {
        this.TOTAL = TOTAL;
    }
    /**
     * 取得cai nay de lam cai j vay troi. 02/2012 SD de xet dk thuong chuyen can T02-T03/2012, if Y thi ok, N thi or Null thi ko duoc
     * @return LOCKED cai nay de lam cai j vay troi. 02/2012 SD de xet dk thuong chuyen can T02-T03/2012, if Y thi ok, N thi or Null thi ko duoc
     */
    @Length(max = 1)
    @Column(name = "LOCKED")
    @Config(key = "N_REST_DETAIL.LOCKED")
    public java.lang.String getLOCKED() {
        return LOCKED;
    }
    /**
     * 設定cai nay de lam cai j vay troi. 02/2012 SD de xet dk thuong chuyen can T02-T03/2012, if Y thi ok, N thi or Null thi ko duoc
     * @param LOCKED cai nay de lam cai j vay troi. 02/2012 SD de xet dk thuong chuyen can T02-T03/2012, if Y thi ok, N thi or Null thi ko duoc
     */
    public void setLOCKED(java.lang.String LOCKED) {
        this.LOCKED = LOCKED;
    }
    /**
     * 取得ly do nghi phep (phep nam, ban, benh, khang cong....)
     * @return REST_KIND ly do nghi phep (phep nam, ban, benh, khang cong....)
     */
    @Length(max = 10)
    @Column(name = "REST_KIND")
    @Config(key = "N_REST_DETAIL.REST_KIND")
    public java.lang.String getREST_KIND() {
        return REST_KIND;
    }
    /**
     * 設定ly do nghi phep (phep nam, ban, benh, khang cong....)
     * @param REST_KIND ly do nghi phep (phep nam, ban, benh, khang cong....)
     */
    public void setREST_KIND(java.lang.String REST_KIND) {
        this.REST_KIND = REST_KIND;
    }
    /**
     * 取得ly do neu nghi phep nam (tet,cty,cnv,ton)
     * @return REST_TYPE ly do neu nghi phep nam (tet,cty,cnv,ton)
     */
    @Length(max = 10)
    @Column(name = "REST_TYPE")
    @Config(key = "N_REST_DETAIL.REST_TYPE")
    public java.lang.String getREST_TYPE() {
        return REST_TYPE;
    }
    /**
     * 設定ly do neu nghi phep nam (tet,cty,cnv,ton)
     * @param REST_TYPE ly do neu nghi phep nam (tet,cty,cnv,ton)
     */
    public void setREST_TYPE(java.lang.String REST_TYPE) {
        this.REST_TYPE = REST_TYPE;
    }
    /**
     * 取得du thua qua
     * @return REST_SAL du thua qua
     */
    @Length(max = 10)
    @Column(name = "REST_SAL")
    @Config(key = "N_REST_DETAIL.REST_SAL")
    public java.lang.String getREST_SAL() {
        return REST_SAL;
    }
    /**
     * 設定du thua qua
     * @param REST_SAL du thua qua
     */
    public void setREST_SAL(java.lang.String REST_SAL) {
        this.REST_SAL = REST_SAL;
    }
    /**
     * 取得son khong can
     * @return TIN son khong can
     */
    @Length(max = 4)
    @Column(name = "TIN")
    @Config(key = "N_REST_DETAIL.TIN")
    public java.lang.String getTIN() {
        return TIN;
    }
    /**
     * 設定son khong can
     * @param TIN son khong can
     */
    public void setTIN(java.lang.String TIN) {
        this.TIN = TIN;
    }
    /**
     * 取得son khong can
     * @return TOUT son khong can
     */
    @Length(max = 4)
    @Column(name = "TOUT")
    @Config(key = "N_REST_DETAIL.TOUT")
    public java.lang.String getTOUT() {
        return TOUT;
    }
    /**
     * 設定son khong can
     * @param TOUT son khong can
     */
    public void setTOUT(java.lang.String TOUT) {
        this.TOUT = TOUT;
    }
    /**
     * 取得son khong can
     * @return MID_TIME son khong can
     */
    @Length(max = 5)
    @Column(name = "MID_TIME")
    @Config(key = "N_REST_DETAIL.MID_TIME")
    public java.lang.String getMID_TIME() {
        return MID_TIME;
    }
    /**
     * 設定son khong can
     * @param MID_TIME son khong can
     */
    public void setMID_TIME(java.lang.String MID_TIME) {
        this.MID_TIME = MID_TIME;
    }
    /**
     * 取得GHI CHU DE HOTRO XUAT TRO CAP 100% VA 75% CHO CTBH
     * @return NOTE GHI CHU DE HOTRO XUAT TRO CAP 100% VA 75% CHO CTBH
     */
    @Length(max = 500)
    @Column(name = "NOTE")
    @Config(key = "N_REST_DETAIL.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * 設定GHI CHU DE HOTRO XUAT TRO CAP 100% VA 75% CHO CTBH
     * @param NOTE GHI CHU DE HOTRO XUAT TRO CAP 100% VA 75% CHO CTBH
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
    /**
     * 取得BEGIN T07/2009.GHI CHU DE HOTRO XUAT TRO CAP 100% VA 75% CHO CTBH , PBIET PHEP NGAN VA DAI NGAY
     * @return NOTE1 BEGIN T07/2009.GHI CHU DE HOTRO XUAT TRO CAP 100% VA 75% CHO CTBH , PBIET PHEP NGAN VA DAI NGAY
     */
    @Length(max = 500)
    @Column(name = "NOTE1")
    @Config(key = "N_REST_DETAIL.NOTE1")
    public java.lang.String getNOTE1() {
        return NOTE1;
    }
    /**
     * 設定BEGIN T07/2009.GHI CHU DE HOTRO XUAT TRO CAP 100% VA 75% CHO CTBH , PBIET PHEP NGAN VA DAI NGAY
     * @param NOTE1 BEGIN T07/2009.GHI CHU DE HOTRO XUAT TRO CAP 100% VA 75% CHO CTBH , PBIET PHEP NGAN VA DAI NGAY
     */
    public void setNOTE1(java.lang.String NOTE1) {
        this.NOTE1 = NOTE1;
    }
    /**
     * 取得add by son : ref to N_Rest.year
     * @return YEAR add by son : ref to N_Rest.year
     */
    @Length(max = 4)
    @Column(name = "YEAR")
    @Config(key = "N_REST_DETAIL.YEAR")
    public java.lang.String getYEAR() {
        return YEAR;
    }
    /**
     * 設定add by son : ref to N_Rest.year
     * @param YEAR add by son : ref to N_Rest.year
     */
    public void setYEAR(java.lang.String YEAR) {
        this.YEAR = YEAR;
    }
    /**
     * 取得add by son, but not used
     * @return REASON add by son, but not used
     */
    @Length(max = 5)
    @Column(name = "REASON")
    @Config(key = "N_REST_DETAIL.REASON")
    public java.lang.String getREASON() {
        return REASON;
    }
    /**
     * 設定add by son, but not used
     * @param REASON add by son, but not used
     */
    public void setREASON(java.lang.String REASON) {
        this.REASON = REASON;
    }
    /**
     * 取得add by Son
     * @return USER_UP add by Son
     */
    @Length(max = 50)
    @Column(name = "USER_UP")
    @Config(key = "N_REST_DETAIL.USER_UP")
    public java.lang.String getUSER_UP() {
        return USER_UP;
    }
    /**
     * 設定add by Son
     * @param USER_UP add by Son
     */
    public void setUSER_UP(java.lang.String USER_UP) {
        this.USER_UP = USER_UP;
    }
    /**
     * 取得add by Son
     * @return DATE_UP add by Son
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_UP")
    @Config(key = "N_REST_DETAIL.DATE_UP")
    public java.util.Date getDATE_UP() {
        return DATE_UP;
    }
    /**
     * 設定add by Son
     * @param DATE_UP add by Son
     */
    public void setDATE_UP(java.util.Date DATE_UP) {
        this.DATE_UP = DATE_UP;
    }
    /**
     * 取得add by Son : note time in, time out
     * @return NOTE_TIME add by Son : note time in, time out
     */
    @Length(max = 500)
    @Column(name = "NOTE_TIME")
    @Config(key = "N_REST_DETAIL.NOTE_TIME")
    public java.lang.String getNOTE_TIME() {
        return NOTE_TIME;
    }
    /**
     * 設定add by Son : note time in, time out
     * @param NOTE_TIME add by Son : note time in, time out
     */
    public void setNOTE_TIME(java.lang.String NOTE_TIME) {
        this.NOTE_TIME = NOTE_TIME;
    }
    /**
     * 取得add by Son : so gio nghi/ngay, mac dinh = 8
     * @return HOUR add by Son : so gio nghi/ngay, mac dinh = 8
     */
    @Column(name = "HOUR")
    @Config(key = "N_REST_DETAIL.HOUR")
    public java.lang.Long getHOUR() {
        return HOUR;
    }
    /**
     * 設定add by Son : so gio nghi/ngay, mac dinh = 8
     * @param HOUR add by Son : so gio nghi/ngay, mac dinh = 8
     */
    public void setHOUR(java.lang.Long HOUR) {
        this.HOUR = HOUR;
    }
}
