package ds.program.fvhr.domain;
import java.util.Date;

import javax.persistence.IdClass;

import ds.program.fvhr.domain.pk.N_EMP_ICCARDPk;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* QUAN LY THE IC
**/
@IdClass(N_EMP_ICCARDPk.class)
@Entity
@Table(name = "N_EMP_ICCARD")
public class N_EMP_ICCARD {
    private java.lang.String EMPSN;	//THE DEO
    private java.lang.String EMPCN;	//THE IC
    private java.lang.String USE_STATUS;	//TRANG THAI SU DUNG
    private java.util.Date BEGIN_DATE;	//NGAY BAT DAU SU DUNG
    private java.util.Date END_DATE;	//NGAY KET THUC SU DUNG
    
    public N_EMP_ICCARD()
    {
    	this.EMPSN=null;
    	this.EMPCN=null;
    	this.USE_STATUS=null;
    	this.BEGIN_DATE= null;
    	this.END_DATE = null;    	
    }
    public N_EMP_ICCARD(String EMPSN, String EMPCN, String USE_STATUS,
			Date BEGIN_DATE, Date END_DATE) {
		// TODO Auto-generated constructor stub
    	this.EMPSN=EMPSN;
    	this.EMPCN=EMPCN;
    	this.USE_STATUS=USE_STATUS;
    	this.BEGIN_DATE= BEGIN_DATE;
    	this.END_DATE = END_DATE;
	}
	/**
     * 取得THE DEO
     * @return EMPSN THE DEO
     */
    @Id
    @NotBlank
    @Column(name="EMPSN")                      
    @Config(key = "N_EMP_ICCARD.EMPSN")
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
     * 取得THE IC
     * @return EMPCN THE IC
     */
    @Id
    @NotBlank
    @Column(name="EMPCN")
    @Config(key = "N_EMP_ICCARD.EMPCN")
    public java.lang.String getEMPCN() {
        return EMPCN;
    }
    /**
     * 設定THE IC
     * @param EMPCN THE IC
     */
    public void setEMPCN(java.lang.String EMPCN) {
        this.EMPCN = EMPCN;
    }
    /**
     * 取得TRANG THAI SU DUNG
     * @return USE_STATUS TRANG THAI SU DUNG
     */
    @Id
    @NotBlank
    @Column(name = "USE_STATUS")
    @Config(key = "N_EMP_ICCARD.USE_STATUS")
    public java.lang.String getUSE_STATUS() {
        return USE_STATUS;
    }
    /**
     * 設定TRANG THAI SU DUNG
     * @param USE_STATUS TRANG THAI SU DUNG
     */
    public void setUSE_STATUS(java.lang.String USE_STATUS) {
        this.USE_STATUS = USE_STATUS;
    }
    /**
     * 取得NGAY BAT DAU SU DUNG
     * @return BEGIN_DATE NGAY BAT DAU SU DUNG
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "BEGIN_DATE")
    @Config(key = "N_EMP_ICCARD.BEGIN_DATE")
    public java.util.Date getBEGIN_DATE() {
        return BEGIN_DATE;
    }
    /**
     * 設定NGAY BAT DAU SU DUNG
     * @param BEGIN_DATE NGAY BAT DAU SU DUNG
     */
    public void setBEGIN_DATE(java.util.Date BEGIN_DATE) {
        this.BEGIN_DATE = BEGIN_DATE;
    }
    /**
     * 取得NGAY KET THUC SU DUNG
     * @return END_DATE NGAY KET THUC SU DUNG
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "END_DATE")
    @Config(key = "N_EMP_ICCARD.END_DATE")
    public java.util.Date getEND_DATE() {
        return END_DATE;
    }
    /**
     * 設定NGAY KET THUC SU DUNG
     * @param END_DATE NGAY KET THUC SU DUNG
     */
    public void setEND_DATE(java.util.Date END_DATE) {
        this.END_DATE = END_DATE;
    }    
}
