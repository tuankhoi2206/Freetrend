package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_EMP_TAXCODEPk implements Serializable {
    private java.lang.String EMPSN;
    private java.lang.String CODE_TAX;	//MA SO THUE TNCN,CO KHI NV NGHI VIEC XONG VO LAM LAI
    private java.lang.String STATUS_CODE;	//0 LA DANG KO CO HD, 1 LA CO
    public N_EMP_TAXCODEPk() {
        super();
    }
    public N_EMP_TAXCODEPk(java.lang.String EMPSN, java.lang.String CODE_TAX, java.lang.String STATUS_CODE) {
        super();
        this.EMPSN = EMPSN;
        this.CODE_TAX = CODE_TAX;
        this.STATUS_CODE = STATUS_CODE;
    }

    /**
     * @returnEMPSN 
     */
    @NotBlank
    @Column(name = "EMPSN", length = 8)
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
     * 取得MA SO THUE TNCN,CO KHI NV NGHI VIEC XONG VO LAM LAI
     * @returnCODE_TAX MA SO THUE TNCN,CO KHI NV NGHI VIEC XONG VO LAM LAI
     */
    @NotBlank
    @Column(name = "CODE_TAX", length = 10)
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
     * 取得0 LA DANG KO CO HD, 1 LA CO
     * @returnSTATUS_CODE 0 LA DANG KO CO HD, 1 LA CO
     */
    @NotBlank
    @Column(name = "STATUS_CODE", length = 1)
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
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_EMP_TAXCODEPk))
            return false;
        N_EMP_TAXCODEPk castOther = (N_EMP_TAXCODEPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(CODE_TAX, castOther.CODE_TAX).append(STATUS_CODE, castOther.STATUS_CODE).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(CODE_TAX).append(STATUS_CODE).toHashCode();
    }
}
