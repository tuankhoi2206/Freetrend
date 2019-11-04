package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_TIME_BEARPk;
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
@Entity
@Table(name = "N_VIEW_TIME_BEAR")
public class N_VIEW_TIME_BEAR {
    private java.lang.String EMPSN;
    private java.lang.String HOTEN;
    private java.util.Date BB_DATES;
    private java.util.Date EE_DATES;
    private java.util.Date DATES_BEAR;	//NGAY SINH E BE
    private java.lang.String NOTE;	//SO THANG NS
    private java.lang.String TGBHXHTN;
    private java.lang.Integer DK_NS;
    
    
    
    /**
     * @return EMPSN 
     */
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_VIEW_TIME_BEAR.EMPSN")
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
     * @return BB_DATES 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "B_DATES")
    @Config(key = "N_VIEW_TIME_BEAR.BB_DATES")
    public java.util.Date getBB_DATES() {
        return BB_DATES;
    }
    /**
     * @param BB_DATES 
     */
    public void setBB_DATES(java.util.Date BB_DATES) {
        this.BB_DATES = BB_DATES;
    }
    /**
     * @return EE_DATES 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "E_DATES")
    @Config(key = "N_VIEW_TIME_BEAR.EE_DATES")
    public java.util.Date getEE_DATES() {
        return EE_DATES;
    }
    /**
     * @param EE_DATES 
     */
    public void setEE_DATES(java.util.Date EE_DATES) {
        this.EE_DATES = EE_DATES;
    }
    /**
     * 取得NGAY SINH E BE
     * @return DATES_BEAR NGAY SINH E BE
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATES_BEAR")
    @Config(key = "N_VIEW_TIME_BEAR.DATES_BEAR")
    public java.util.Date getDATES_BEAR() {
        return DATES_BEAR;
    }
    /**
     * 設定NGAY SINH E BE
     * @param DATES_BEAR NGAY SINH E BE
     */
    public void setDATES_BEAR(java.util.Date DATES_BEAR) {
        this.DATES_BEAR = DATES_BEAR;
    }
    /**
     * 取得SO THANG NS
     * @return NOTE SO THANG NS
     */
    @Column(name = "NOTE")
    @Config(key = "N_VIEW_TIME_BEAR.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * 設定SO THANG NS
     * @param NOTE SO THANG NS
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
    /**
     * 取得NGAY NOP GIAY KHAI SINH, ko dung nua. SD table N_BIRTH_CERTIFICATE
     * @return DATES_GKS NGAY NOP GIAY KHAI SINH, ko dung nua. SD table N_BIRTH_CERTIFICATE
     */

    @Column(name = "HOTEN")
    @Config(key = "N_VIEW_TIME_BEAR.HOTEN")
	public java.lang.String getHOTEN() {
		return HOTEN;
	}
	public void setHOTEN(java.lang.String hOTEN) {
		HOTEN = hOTEN;
	}
	
	@Column(name = "TGBHXHTN")
    @Config(key = "N_VIEW_TIME_BEAR.TGBHXHTN")
	public java.lang.String getTGBHXHTN() {
		return TGBHXHTN;
	}
	public void setTGBHXHTN(java.lang.String tGBHXHTN) {
		TGBHXHTN = tGBHXHTN;
	}
	
	@Column(name = "DK_NS")
    @Config(key = "N_VIEW_TIME_BEAR.DK_NS")
	public java.lang.Integer getDK_NS() {
		return DK_NS;
	}
	public void setDK_NS(java.lang.Integer dK_NS) {
		DK_NS = dK_NS;
	}
}