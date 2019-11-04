package ds.program.fvhr.domain;

import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_REST_PBAN_NKLPk;
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
@IdClass(N_REST_PBAN_NKLPk.class)
@Entity
@Table(name = "N_REST_PBAN_NKL")
public class N_REST_PBAN_NKL {
    private java.lang.String EMPSN;
    private java.util.Date B_DATES;
    private java.util.Date E_DATES;
    private java.lang.String NOTE;	//IF TS SO NGAY NGHI PBAN >26 THI SE INSERT THEM VAO TABLE NAY CUNG VOI N_REST_DETAIL
    private java.lang.String REST_KIND;
    private java.lang.Long TS_NGAY;	//TS SO NGAY NGHI
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_REST_PBAN_NKL.EMPSN")
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
     * @return B_DATES 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "B_DATES")
    @Config(key = "N_REST_PBAN_NKL.B_DATES")
    public java.util.Date getB_DATES() {
        return B_DATES;
    }
    /**
     * @param B_DATES 
     */
    public void setB_DATES(java.util.Date B_DATES) {
        this.B_DATES = B_DATES;
    }
    /**
     * @return E_DATES 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "E_DATES")
    @Config(key = "N_REST_PBAN_NKL.E_DATES")
    public java.util.Date getE_DATES() {
        return E_DATES;
    }
    /**
     * @param E_DATES 
     */
    public void setE_DATES(java.util.Date E_DATES) {
        this.E_DATES = E_DATES;
    }
    /**
     * IF TS SO NGAY NGHI PBAN >26 THI SE INSERT THEM VAO TABLE NAY CUNG VOI N_REST_DETAIL
     * @return NOTE IF TS SO NGAY NGHI PBAN >26 THI SE INSERT THEM VAO TABLE NAY CUNG VOI N_REST_DETAIL
     */
    @Length(max = 150)
    @Column(name = "NOTE")
    @Config(key = "N_REST_PBAN_NKL.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * 設定IF TS SO NGAY NGHI PBAN >26 THI SE INSERT THEM VAO TABLE NAY CUNG VOI N_REST_DETAIL
     * @param NOTE IF TS SO NGAY NGHI PBAN >26 THI SE INSERT THEM VAO TABLE NAY CUNG VOI N_REST_DETAIL
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
    /**
     * @return REST_KIND 
     */
    @Length(max = 30)
    @Column(name = "REST_KIND")
    @Config(key = "N_REST_PBAN_NKL.REST_KIND")
    public java.lang.String getREST_KIND() {
        return REST_KIND;
    }
    /**
     * @param REST_KIND 
     */
    public void setREST_KIND(java.lang.String REST_KIND) {
        this.REST_KIND = REST_KIND;
    }
    /**
     * �?�得TS SO NGAY NGHI
     * @return TS_NGAY TS SO NGAY NGHI
     */
    @Column(name = "TS_NGAY")
    @Config(key = "N_REST_PBAN_NKL.TS_NGAY")
    public java.lang.Long getTS_NGAY() {
        return TS_NGAY;
    }
    /**
     * 設定TS SO NGAY NGHI
     * @param TS_NGAY TS SO NGAY NGHI
     */
    public void setTS_NGAY(java.lang.Long TS_NGAY) {
        this.TS_NGAY = TS_NGAY;
    }
}
