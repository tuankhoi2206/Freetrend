package ds.program.fvhr.domain;

import java.util.Date;

import javax.persistence.IdClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import ds.program.fvhr.domain.pk.N_BONUS_POSSPk;
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
* PHU CAP CHUC VU
**/
@IdClass(N_BONUS_POSSPk.class)
@Entity
@Table(name = "N_BONUS_POSS")
public class N_BONUS_POSS {
    private java.lang.String EMPSN;	//SO THE DEO
    private java.util.Date DATES;	//NGAY PHU CAP
    private java.lang.Long MONEY;	//TIEN PHU CAP
    private java.lang.String KEYS;	//KHOA DU LIEU
    private java.lang.String POSS;	//CHUC VU
    private java.lang.String NOTE;	//GHI CHU
    private java.util.Date END_DATE;	//NGAY KET THUC
    private String USER_UP;
    private Date	DATE_UP;
    /**
     * �?�得SO THE DEO
     * @return EMPSN SO THE DEO
     */
    @Id
    @NotBlank
    @Config(key = "N_BONUS_POSS.EMPSN")
    public java.lang.String getEMPSN() {
        return EMPSN;
    }
    /**
     * 設定SO THE DEO
     * @param EMPSN SO THE DEO
     */
    public void setEMPSN(java.lang.String EMPSN) {
        this.EMPSN = EMPSN;
    }
    /**
     * NGAY PHU CAP
     * @return DATES NGAY PHU CAP
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATES")
    @Config(key = "N_BONUS_POSS.DATES")
    public java.util.Date getDATES() {
        return DATES;
    }
    /**
     * 設定NGAY PHU CAP
     * @param DATES NGAY PHU CAP
     */
    public void setDATES(java.util.Date DATES) {
        this.DATES = DATES;
    }
    /**
     * �?�得TIEN PHU CAP
     * @return MONEY TIEN PHU CAP
     */
    @Column(name = "MONEY")
    @Config(key = "N_BONUS_POSS.MONEY")
    public java.lang.Long getMONEY() {
        return MONEY;
    }
    /**
     * 設定TIEN PHU CAP
     * @param MONEY TIEN PHU CAP
     */
    public void setMONEY(java.lang.Long MONEY) {
        this.MONEY = MONEY;
    }
    /**
     * �?�得KHOA DU LIEU
     * @return KEYS KHOA DU LIEU
     */
    @Length(max = 1)
    @Column(name = "KEYS")
    @Config(key = "N_BONUS_POSS.KEYS")
    public java.lang.String getKEYS() {
        return KEYS;
    }
    /**
     * 設定KHOA DU LIEU
     * @param KEYS KHOA DU LIEU
     */
    public void setKEYS(java.lang.String KEYS) {
        this.KEYS = KEYS;
    }
    /**
     * CHUC VU
     * @return POSS CHUC VU
     */
    @Length(max = 20)
    @Column(name = "POSS")
    @Config(key = "N_BONUS_POSS.POSS")
    public java.lang.String getPOSS() {
        return POSS;
    }
    /**
     * 設定CHUC VU
     * @param POSS CHUC VU
     */
    public void setPOSS(java.lang.String POSS) {
        this.POSS = POSS;
    }
    /**
     * GHI CHU
     * @return NOTE GHI CHU
     */
    @Length(max = 150)
    @Column(name = "NOTE")
    @Config(key = "N_BONUS_POSS.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * 設定GHI CHU
     * @param NOTE GHI CHU
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
    /**
     * �?�得NGAY KET THUC
     * @return END_DATE NGAY KET THUC
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "END_DATE")
    @Config(key = "N_BONUS_POSS.END_DATE")
    public java.util.Date getEND_DATE() {
        return END_DATE;
    }
    /**
     * 設定NGAY KET THUC
     * @param END_DATE NGAY KET THUC
     */
    public void setEND_DATE(java.util.Date END_DATE) {
        this.END_DATE = END_DATE;
    }
    
    /**
     * @return USER_UP 
     */
    @Length(max = 50)
    @Column(name = "USER_UP")
    @Config(key = "N_BONUS_POSS.USER_UP")
    public java.lang.String getUSER_UP() {
        return USER_UP;
    }
    /**
     * @param USER_UP 
     */
    public void setUSER_UP(java.lang.String USER_UP) {
        this.USER_UP = USER_UP;
    }
    /**
     * @return DATE_UP 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_UP")
    @Config(key = "N_BONUS_POSS.DATE_UP")
    public java.util.Date getDATE_UP() {
        return DATE_UP;
    }
    /**
     * @param DATE_UP 
     */
    public void setDATE_UP(java.util.Date DATE_UP) {
        this.DATE_UP = DATE_UP;
    }
    
    
}
