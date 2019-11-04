package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_EMP_TAXCODEPk;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import org.hibernate.annotations.NotFound;
//import org.hibernate.annotations.NotFoundAction;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@IdClass(N_EMP_TAXCODEPk.class)
@Entity
@Table(name = "N_EMP_TAXCODE")
public class N_EMP_TAXCODE {
    private java.lang.String EMPSN;
    private java.util.Date DATES;
    private java.lang.String CODE_TAX;	//MA SO THUE TNCN,CO KHI NV NGHI VIEC XONG VO LAM LAI
    private java.lang.String NOTE;
    private java.lang.String STATUS_CODE;	//0 LA DANG KO CO HD, 1 LA CO
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Config(key = "N_EMP_TAXCODE.EMPSN")
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
     * @return DATES 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATES")
    @Config(key = "N_EMP_TAXCODE.DATES")
    public java.util.Date getDATES() {
        return DATES;
    }
    /**
     * @param DATES 
     */
    public void setDATES(java.util.Date DATES) {
        this.DATES = DATES;
    }
    /**
     * 取得MA SO THUE TNCN,CO KHI NV NGHI VIEC XONG VO LAM LAI
     * @return CODE_TAX MA SO THUE TNCN,CO KHI NV NGHI VIEC XONG VO LAM LAI
     */
    @Id
    @NotBlank
    @Column(name = "CODE_TAX")
    @Config(key = "N_EMP_TAXCODE.CODE_TAX")
    public java.lang.String getCODE_TAX() {
        return CODE_TAX;
    }
    /**
     * 設定MA SO THUE TNCN,CO KHI NV NGHI VIEC XONG VO LAM LAI
     * @param CODE_TAX MA SO THUE TNCN,CO KHI NV NGHI VIEC XONG VO LAM LAI
     */
    public void setCODE_TAX(java.lang.String CODE_TAX) {
        this.CODE_TAX = CODE_TAX;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 150)
    @Column(name = "NOTE")
    @Config(key = "N_EMP_TAXCODE.NOTE")
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
     * 取得0 LA DANG KO CO HD, 1 LA CO
     * @return STATUS_CODE 0 LA DANG KO CO HD, 1 LA CO
     */
    @Id
    @NotBlank
    @Column(name = "STATUS_CODE")
    @Config(key = "N_EMP_TAXCODE.STATUS_CODE")
    public java.lang.String getSTATUS_CODE() {
        return STATUS_CODE;
    }
    /**
     * 設定0 LA DANG KO CO HD, 1 LA CO
     * @param STATUS_CODE 0 LA DANG KO CO HD, 1 LA CO
     */
    public void setSTATUS_CODE(java.lang.String STATUS_CODE) {
        this.STATUS_CODE = STATUS_CODE;
    }
//    private N_EMPLOYEE EMPSN_Object;
//    /**
//     * @return EMPSN 
//     */
//    @ManyToOne
//    @JoinColumn(name="EMPSN", referencedColumnName="EMPSN", insertable=false, updatable=false)
//    @NotFound(action=NotFoundAction.IGNORE)
//    @Config(key = "N_EMP_TAXCODE.EMPSN")
//    public N_EMPLOYEE getEMPSN_Object() {
//        return EMPSN_Object;
//    }
//    /**
//     * @param EMPSN 
//     */
//    public void setEMPSN_Object(N_EMPLOYEE EMPSN) {
//        this.EMPSN_Object = EMPSN;
//    }
}
