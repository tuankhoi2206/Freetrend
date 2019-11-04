package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_KYLUATPk;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@IdClass(N_KYLUATPk.class)
@Entity
@Table(name = "N_KYLUAT")
public class N_KYLUAT {
    private java.lang.String EMPSN;
    private java.util.Date DATE_HL;	//PHAT DEN NGAY
    private java.util.Date DATE_P;	//THOI GIAN PHAT tu ngay. PHAT TU NGAY
    private java.lang.String ID_KHOAN;
    private java.lang.String ID_DIEU;
    private java.lang.String ID_PHAT;
    private java.lang.String QDKL;	//QUYET DINH KY LUAT
    private java.lang.String NOTE;
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_KYLUAT.EMPSN")
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
     * 取得PHAT DEN NGAY
     * @return DATE_HL PHAT DEN NGAY
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_HL")
    @Config(key = "N_KYLUAT.DATE_HL")
    public java.util.Date getDATE_HL() {

        return DATE_HL;
    }
    /**
     * 設定PHAT DEN NGAY
     * @param DATE_HL PHAT DEN NGAY
     */
    public void setDATE_HL(java.util.Date DATE_HL) {

        this.DATE_HL = DATE_HL;
    }
    /**
     * 取得THOI GIAN PHAT tu ngay. PHAT TU NGAY
     * @return DATE_P THOI GIAN PHAT tu ngay. PHAT TU NGAY
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_P")
    @Config(key = "N_KYLUAT.DATE_P")
    public java.util.Date getDATE_P() {
        return DATE_P;
    }
    /**
     * 設定THOI GIAN PHAT tu ngay. PHAT TU NGAY
     * @param DATE_P THOI GIAN PHAT tu ngay. PHAT TU NGAY
     */
    public void setDATE_P(java.util.Date DATE_P) {
        this.DATE_P = DATE_P;
    }
    /**
     * @return ID_KHOAN 
     */
    @Length(max = 10)
    @Column(name = "ID_KHOAN")
    @Config(key = "N_KYLUAT.ID_KHOAN")
    public java.lang.String getID_KHOAN() {
        return ID_KHOAN;
    }
    /**
     * @param ID_KHOAN 
     */
    public void setID_KHOAN(java.lang.String ID_KHOAN) {
        this.ID_KHOAN = ID_KHOAN;
    }
    /**
     * @return ID_DIEU 
     */
    @Length(max = 10)
    @Column(name = "ID_DIEU")
    @Config(key = "N_KYLUAT.ID_DIEU")
    public java.lang.String getID_DIEU() {
        return ID_DIEU;
    }
    /**
     * @param ID_DIEU 
     */
    public void setID_DIEU(java.lang.String ID_DIEU) {
        this.ID_DIEU = ID_DIEU;
    }
    /**
     * @return ID_PHAT 
     */
    @Length(max = 2)
    //@ManyToOne
    @JoinColumn(name = "ID_PHAT")
    //@NotFound(action=NotFoundAction.IGNORE)
    @Config(key = "N_KYLUAT.ID_PHAT")
    public java.lang.String getID_PHAT() {
        return ID_PHAT;
    }
    /**
     * @param ID_PHAT 
     */
    public void setID_PHAT(java.lang.String ID_PHAT) {
        this.ID_PHAT = ID_PHAT;
    }
    /**
     * 取得QUYET DINH KY LUAT
     * @return QDKL QUYET DINH KY LUAT
     */
    @Length(max = 20)
    @Column(name = "QDKL")
    @Config(key = "N_KYLUAT.QDKL")
    public java.lang.String getQDKL() {
        return QDKL;
    }
    /**
     * 設定QUYET DINH KY LUAT
     * @param QDKL QUYET DINH KY LUAT
     */
    public void setQDKL(java.lang.String QDKL) {
        this.QDKL = QDKL;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 150)
    @Column(name = "NOTE")
    @Config(key = "N_KYLUAT.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * @param NOTE 
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
}
