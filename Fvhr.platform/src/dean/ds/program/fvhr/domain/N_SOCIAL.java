package ds.program.fvhr.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.Length;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@Entity
@Table(name = "N_SOCIAL")
public class N_SOCIAL {
    private java.lang.String EMPSN;
    private java.util.Date DATES;	//NGAY MUA BH
    private java.util.Date EXPIRE;	//NGAY HET HAN
    private java.lang.Long SALARY;	//LUONG KHI MUA
    private java.lang.String NOTE;
    private java.lang.String CLOCK;	//TRANG THAI
    private java.lang.String ID_SOCIAL;	//SO BHXH
    private java.lang.String ID_KEY;	//ID KEY
    private java.util.Date DATE_NEW;	//NGAY CAP NHAT LAI SO BHXH
    private java.lang.String SOCIAL_OLD;
    public java.lang.String getSOCIAL_OLD() {
		return SOCIAL_OLD;
	}
	public void setSOCIAL_OLD(java.lang.String sOCIAL_OLD) {
		SOCIAL_OLD = sOCIAL_OLD;
	}
	/**
     * @return EMPSN 
     */
    @Length(max = 8)
    @Column(name = "EMPSN")
    @Config(key = "N_SOCIAL.EMPSN")
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
     * �?�得NGAY MUA BH
     * @return DATES NGAY MUA BH
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATES")
    @Config(key = "N_SOCIAL.DATES")
    public java.util.Date getDATES() {
        return DATES;
    }
    /**
     * 設定NGAY MUA BH
     * @param DATES NGAY MUA BH
     */
    public void setDATES(java.util.Date DATES) {
        this.DATES = DATES;
    }
    /**
     * �?�得NGAY HET HAN
     * @return EXPIRE NGAY HET HAN
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "EXPIRE")
    @Config(key = "N_SOCIAL.EXPIRE")
    public java.util.Date getEXPIRE() {
        return EXPIRE;
    }
    /**
     * 設定NGAY HET HAN
     * @param EXPIRE NGAY HET HAN
     */
    public void setEXPIRE(java.util.Date EXPIRE) {
        this.EXPIRE = EXPIRE;
    }
    /**
     * �?�得LUONG KHI MUA
     * @return SALARY LUONG KHI MUA
     */
    @Column(name = "SALARY")
    @Config(key = "N_SOCIAL.SALARY")
    public java.lang.Long getSALARY() {
        return SALARY;
    }
    /**
     * 設定LUONG KHI MUA
     * @param SALARY LUONG KHI MUA
     */
    public void setSALARY(java.lang.Long SALARY) {
        this.SALARY = SALARY;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 100)
    @Column(name = "NOTE")
    @Config(key = "N_SOCIAL.NOTE")
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
     * �?�得TRANG THAI
     * @return CLOCK TRANG THAI
     */
    @Length(max = 1)
    @Column(name = "CLOCK")
    @Config(key = "N_SOCIAL.CLOCK")
    public java.lang.String getCLOCK() {
        return CLOCK;
    }
    /**
     * 設定TRANG THAI
     * @param CLOCK TRANG THAI
     */
    public void setCLOCK(java.lang.String CLOCK) {
        this.CLOCK = CLOCK;
    }
    /**
     * �?�得SO BHXH
     * @return ID_SOCIAL SO BHXH
     */
    @Length(max = 16)
    @Column(name = "ID_SOCIAL")
    @Config(key = "N_SOCIAL.ID_SOCIAL")
    public java.lang.String getID_SOCIAL() {
        return ID_SOCIAL;
    }
    /**
     * 設定SO BHXH
     * @param ID_SOCIAL SO BHXH
     */
    public void setID_SOCIAL(java.lang.String ID_SOCIAL) {
        this.ID_SOCIAL = ID_SOCIAL;
    }
    /**
     * �?�得ID KEY
     * @return ID_KEY ID KEY
     */
    @Id
    @NotBlank
    @Column(name = "ID_KEY")
    @Config(key = "N_SOCIAL.ID_KEY")
    public java.lang.String getID_KEY() {
        return ID_KEY;
    }
    /**
     * 設定ID KEY
     * @param ID_KEY ID KEY
     */
    public void setID_KEY(java.lang.String ID_KEY) {
        this.ID_KEY = ID_KEY;
    }
    /**
     * �?�得NGAY CAP NHAT LAI SO BHXH
     * @return DATE_NEW NGAY CAP NHAT LAI SO BHXH
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_NEW")
    @Config(key = "N_SOCIAL.DATE_NEW")
    public java.util.Date getDATE_NEW() {
        return DATE_NEW;
    }
    /**
     * 設定NGAY CAP NHAT LAI SO BHXH
     * @param DATE_NEW NGAY CAP NHAT LAI SO BHXH
     */
    public void setDATE_NEW(java.util.Date DATE_NEW) {
        this.DATE_NEW = DATE_NEW;
    }
    
private N_EMPLOYEE EMPSN_Object;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="EMPSN", referencedColumnName="EMPSN", insertable=false, updatable=false)
    @NotFound(action=NotFoundAction.IGNORE)
    @Config(key = "N_EMPLOYEE.EMPSN")
    public N_EMPLOYEE getEMPSN_Object() {
        return EMPSN_Object;
    }
    
    public void setEMPSN_Object(N_EMPLOYEE empsn) {
        this.EMPSN_Object = empsn;
    }
}
