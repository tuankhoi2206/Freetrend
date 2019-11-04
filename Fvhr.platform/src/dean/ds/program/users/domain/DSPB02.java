package ds.program.users.domain;

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
* ¿¿¿¿¿¿
**/
@Entity
@Table(name = "DSPB02")
public class DSPB02 {
    private java.lang.String PB_USERID;	//¿¿¿¿¿
    private java.lang.String PB_CNAME;	//¿¿¿¿¿
    private java.lang.String PB_NAME;	//¿¿¿¿¿
    private java.lang.String PB_DPNO;	//¿¿¿¿
    private java.lang.String PB_PASS;	//¿¿
    private java.lang.String UP_USER;	//¿¿¿¿
    private java.util.Date UP_DATE;	//¿¿¿¿
    private java.lang.String PB_EMAIL;	//E-Mail
    private java.lang.String PB_EMUSER;	//E-Mail ¿¿
    private java.lang.String PB_EMPASSWD;	//E-Mail ¿¿
    private java.lang.String PB_FANO;	//¿¿¿¿¿¿
    private java.lang.String PB_PASSENCRY;
    private java.lang.String PB_LOCK;
    private java.lang.String PB_CHANGEYN;
    private java.util.Date PB_VALIDDATE;
    private java.lang.String PB_USERNO;
    /**
     * 取得¿¿¿¿¿
     * @return PB_USERID ¿¿¿¿¿
     */
    @Id
    @NotBlank
    @Column(name = "PB_USERID")
    @Config(key = "DSPB02.PB_USERID")
    public java.lang.String getPB_USERID() {
        return PB_USERID;
    }
    /**
     * 設定¿¿¿¿¿
     * @param PB_USERID ¿¿¿¿¿
     */
    public void setPB_USERID(java.lang.String PB_USERID) {
        this.PB_USERID = PB_USERID;
    }
    /**
     * 取得¿¿¿¿¿
     * @return PB_CNAME ¿¿¿¿¿
     */
    @Length(max = 10)
    @Column(name = "PB_CNAME")
    @Config(key = "DSPB02.PB_CNAME")
    public java.lang.String getPB_CNAME() {
        return PB_CNAME;
    }
    /**
     * 設定¿¿¿¿¿
     * @param PB_CNAME ¿¿¿¿¿
     */
    public void setPB_CNAME(java.lang.String PB_CNAME) {
        this.PB_CNAME = PB_CNAME;
    }
    /**
     * 取得¿¿¿¿¿
     * @return PB_NAME ¿¿¿¿¿
     */
    @Length(max = 20)
    @Column(name = "PB_NAME")
    @Config(key = "DSPB02.PB_NAME")
    public java.lang.String getPB_NAME() {
        return PB_NAME;
    }
    /**
     * 設定¿¿¿¿¿
     * @param PB_NAME ¿¿¿¿¿
     */
    public void setPB_NAME(java.lang.String PB_NAME) {
        this.PB_NAME = PB_NAME;
    }
    /**
     * 取得¿¿¿¿
     * @return PB_DPNO ¿¿¿¿
     */
    @Length(max = 6)
    @Column(name = "PB_DPNO")
    @Config(key = "DSPB02.PB_DPNO")
    public java.lang.String getPB_DPNO() {
        return PB_DPNO;
    }
    /**
     * 設定¿¿¿¿
     * @param PB_DPNO ¿¿¿¿
     */
    public void setPB_DPNO(java.lang.String PB_DPNO) {
        this.PB_DPNO = PB_DPNO;
    }
    /**
     * 取得¿¿
     * @return PB_PASS ¿¿
     */
    @Length(max = 10)
    @Column(name = "PB_PASS")
    @Config(key = "DSPB02.PB_PASS")
    public java.lang.String getPB_PASS() {
        return PB_PASS;
    }
    /**
     * 設定¿¿
     * @param PB_PASS ¿¿
     */
    public void setPB_PASS(java.lang.String PB_PASS) {
        this.PB_PASS = PB_PASS;
    }
    /**
     * 取得¿¿¿¿
     * @return UP_USER ¿¿¿¿
     */
    @Length(max = 10)
    @Column(name = "UP_USER")
    @Config(key = "DSPB02.UP_USER")
    public java.lang.String getUP_USER() {
        return UP_USER;
    }
    /**
     * 設定¿¿¿¿
     * @param UP_USER ¿¿¿¿
     */
    public void setUP_USER(java.lang.String UP_USER) {
        this.UP_USER = UP_USER;
    }
    /**
     * 取得¿¿¿¿
     * @return UP_DATE ¿¿¿¿
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "UP_DATE")
    @Config(key = "DSPB02.UP_DATE")
    public java.util.Date getUP_DATE() {
        return UP_DATE;
    }
    /**
     * 設定¿¿¿¿
     * @param UP_DATE ¿¿¿¿
     */
    public void setUP_DATE(java.util.Date UP_DATE) {
        this.UP_DATE = UP_DATE;
    }
    /**
     * 取得E-Mail
     * @return PB_EMAIL E-Mail
     */
    @Length(max = 30)
    @Column(name = "PB_EMAIL")
    @Config(key = "DSPB02.PB_EMAIL")
    public java.lang.String getPB_EMAIL() {
        return PB_EMAIL;
    }
    /**
     * 設定E-Mail
     * @param PB_EMAIL E-Mail
     */
    public void setPB_EMAIL(java.lang.String PB_EMAIL) {
        this.PB_EMAIL = PB_EMAIL;
    }
    /**
     * 取得E-Mail ¿¿
     * @return PB_EMUSER E-Mail ¿¿
     */
    @Length(max = 10)
    @Column(name = "PB_EMUSER")
    @Config(key = "DSPB02.PB_EMUSER")
    public java.lang.String getPB_EMUSER() {
        return PB_EMUSER;
    }
    /**
     * 設定E-Mail ¿¿
     * @param PB_EMUSER E-Mail ¿¿
     */
    public void setPB_EMUSER(java.lang.String PB_EMUSER) {
        this.PB_EMUSER = PB_EMUSER;
    }
    /**
     * 取得E-Mail ¿¿
     * @return PB_EMPASSWD E-Mail ¿¿
     */
    @Length(max = 10)
    @Column(name = "PB_EMPASSWD")
    @Config(key = "DSPB02.PB_EMPASSWD")
    public java.lang.String getPB_EMPASSWD() {
        return PB_EMPASSWD;
    }
    /**
     * 設定E-Mail ¿¿
     * @param PB_EMPASSWD E-Mail ¿¿
     */
    public void setPB_EMPASSWD(java.lang.String PB_EMPASSWD) {
        this.PB_EMPASSWD = PB_EMPASSWD;
    }
    /**
     * 取得¿¿¿¿¿¿
     * @return PB_FANO ¿¿¿¿¿¿
     */
    @Length(max = 3)
    @Column(name = "PB_FANO")
    @Config(key = "DSPB02.PB_FANO")
    public java.lang.String getPB_FANO() {
        return PB_FANO;
    }
    /**
     * 設定¿¿¿¿¿¿
     * @param PB_FANO ¿¿¿¿¿¿
     */
    public void setPB_FANO(java.lang.String PB_FANO) {
        this.PB_FANO = PB_FANO;
    }
    /**
     * @return PB_PASSENCRY 
     */
    @Length(max = 8)
    @Column(name = "PB_PASSENCRY")
    @Config(key = "DSPB02.PB_PASSENCRY")
    public java.lang.String getPB_PASSENCRY() {
        return PB_PASSENCRY;
    }
    /**
     * @param PB_PASSENCRY 
     */
    public void setPB_PASSENCRY(java.lang.String PB_PASSENCRY) {
        this.PB_PASSENCRY = PB_PASSENCRY;
    }
    /**
     * @return PB_LOCK 
     */
    @Length(max = 1)
    @Column(name = "PB_LOCK")
    @Config(key = "DSPB02.PB_LOCK")
    public java.lang.String getPB_LOCK() {
        return PB_LOCK;
    }
    /**
     * @param PB_LOCK 
     */
    public void setPB_LOCK(java.lang.String PB_LOCK) {
        this.PB_LOCK = PB_LOCK;
    }
    /**
     * @return PB_CHANGEYN 
     */
    @Length(max = 1)
    @Column(name = "PB_CHANGEYN")
    @Config(key = "DSPB02.PB_CHANGEYN")
    public java.lang.String getPB_CHANGEYN() {
        return PB_CHANGEYN;
    }
    /**
     * @param PB_CHANGEYN 
     */
    public void setPB_CHANGEYN(java.lang.String PB_CHANGEYN) {
        this.PB_CHANGEYN = PB_CHANGEYN;
    }
    /**
     * @return PB_VALIDDATE 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "PB_VALIDDATE")
    @Config(key = "DSPB02.PB_VALIDDATE")
    public java.util.Date getPB_VALIDDATE() {
        return PB_VALIDDATE;
    }
    /**
     * @param PB_VALIDDATE 
     */
    public void setPB_VALIDDATE(java.util.Date PB_VALIDDATE) {
        this.PB_VALIDDATE = PB_VALIDDATE;
    }
    /**
     * @return PB_USERNO 
     */
    @Length(max = 10)
    @Column(name = "PB_USERNO")
    @Config(key = "DSPB02.PB_USERNO")
    public java.lang.String getPB_USERNO() {
        return PB_USERNO;
    }
    /**
     * @param PB_USERNO 
     */
    public void setPB_USERNO(java.lang.String PB_USERNO) {
        this.PB_USERNO = PB_USERNO;
    }
}
