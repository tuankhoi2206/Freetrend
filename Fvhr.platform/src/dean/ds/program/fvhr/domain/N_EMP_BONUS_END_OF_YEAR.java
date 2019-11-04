package ds.program.fvhr.domain;

import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_EMP_BONUS_END_OF_YEARPk;
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
* TIEN THUONG CUOI NAM (THANG 13)_NGAN
**/
@IdClass(N_EMP_BONUS_END_OF_YEARPk.class)
@Entity
@Table(name = "N_EMP_BONUS_END_OF_YEAR")
public class N_EMP_BONUS_END_OF_YEAR {
    private java.lang.String EMPSN;
    private java.lang.Long BONUS_M13;
    private java.lang.String STATUS;	//Y: TIEN THUONG TINH CHUNG VOI LUONG, N: TIEN THUONG PHAT RIENG O NGOAI
    private java.lang.String UP_USER;
    private java.util.Date UP_DATE;
    private java.lang.String NOTE;	//Y:TINH VAO TONG THU NHAP-->TRU YT. N: CHI CONG VAO DE TINH THUE TNCN
    private java.util.Date DATE_EFFECT;	//THANG PHAT TIEN THUONG LUONG T13
    private java.lang.String YEAR;//TIEN THUONG T13 CUA NAM NAO?
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_EMP_BONUS_END_OF_YEAR.EMPSN")
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
     * @return BONUS_M13 
     */
    @Column(name = "BONUS_M13")
    @Config(key = "N_EMP_BONUS_END_OF_YEAR.BONUS_M13")
    public java.lang.Long getBONUS_M13() {
        return BONUS_M13;
    }
    /**
     * @param BONUS_M13 
     */
    public void setBONUS_M13(java.lang.Long BONUS_M13) {
        this.BONUS_M13 = BONUS_M13;
    }
    /**
     * �?�得Y: TIEN THUONG TINH CHUNG VOI LUONG, N: TIEN THUONG PHAT RIENG O NGOAI
     * @return STATUS Y: TIEN THUONG TINH CHUNG VOI LUONG, N: TIEN THUONG PHAT RIENG O NGOAI
     */
    @Length(max = 1)
    @Column(name = "STATUS")
    @Config(key = "N_EMP_BONUS_END_OF_YEAR.STATUS")
    public java.lang.String getSTATUS() {
        return STATUS;
    }
    /**
     * 設定Y: TIEN THUONG TINH CHUNG VOI LUONG, N: TIEN THUONG PHAT RIENG O NGOAI
     * @param STATUS Y: TIEN THUONG TINH CHUNG VOI LUONG, N: TIEN THUONG PHAT RIENG O NGOAI
     */
    public void setSTATUS(java.lang.String STATUS) {
        this.STATUS = STATUS;
    }
    /**
     * @return UP_USER 
     */
    @Length(max = 15)
    @Column(name = "UP_USER")
    @Config(key = "N_EMP_BONUS_END_OF_YEAR.UP_USER")
    public java.lang.String getUP_USER() {
        return UP_USER;
    }
    /**
     * @param UP_USER 
     */
    public void setUP_USER(java.lang.String UP_USER) {
        this.UP_USER = UP_USER;
    }
    /**
     * @return UP_DATE 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "UP_DATE")
    @Config(key = "N_EMP_BONUS_END_OF_YEAR.UP_DATE")
    public java.util.Date getUP_DATE() {
        return UP_DATE;
    }
    /**
     * @param UP_DATE 
     */
    public void setUP_DATE(java.util.Date UP_DATE) {
        this.UP_DATE = UP_DATE;
    }
    /**
     * TINH VAO TONG THU NHAP-->TRU YT. N: CHI CONG VAO DE TINH THUE TNCN
     * @return NOTE Y:TINH VAO TONG THU NHAP-->TRU YT. N: CHI CONG VAO DE TINH THUE TNCN
     */
    @Length(max = 50)
    @Column(name = "NOTE")
    @Config(key = "N_EMP_BONUS_END_OF_YEAR.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * 設定Y:TINH VAO TONG THU NHAP-->TRU YT. N: CHI CONG VAO DE TINH THUE TNCN
     * @param NOTE Y:TINH VAO TONG THU NHAP-->TRU YT. N: CHI CONG VAO DE TINH THUE TNCN
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
    /**
     * THANG PHAT TIEN THUONG LUONG T13
     * @return DATE_EFFECT THANG PHAT TIEN THUONG LUONG T13
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_EFFECT")
    @Config(key = "N_EMP_BONUS_END_OF_YEAR.DATE_EFFECT")
    public java.util.Date getDATE_EFFECT() {
        return DATE_EFFECT;
    }
    /**
     * 設定THANG PHAT TIEN THUONG LUONG T13
     * @param DATE_EFFECT THANG PHAT TIEN THUONG LUONG T13
     */
    public void setDATE_EFFECT(java.util.Date DATE_EFFECT) {
        this.DATE_EFFECT = DATE_EFFECT;
    }
    
    @Id
    @NotBlank
    @Column(name = "YEAR")
    @Config(key = "N_EMP_BONUS_END_OF_YEAR.YEAR")
    public java.lang.String getYEAR() {
        return YEAR;
    }
    /**
     * @param EMPSN 
     */
    public void setYEAR(java.lang.String YEAR) {
        this.YEAR = YEAR;
    }
}
