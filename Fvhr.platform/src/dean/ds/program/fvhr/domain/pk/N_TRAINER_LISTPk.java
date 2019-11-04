package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_TRAINER_LISTPk implements Serializable {
    private java.lang.String EMPSN_HL;	//LA NGUOI HUAN LUYEN CNV
    private java.lang.String ID_MONHOC;
    public N_TRAINER_LISTPk() {
        super();
    }
    public N_TRAINER_LISTPk(java.lang.String EMPSN_HL, java.lang.String ID_MONHOC) {
        super();
        this.EMPSN_HL = EMPSN_HL;
        this.ID_MONHOC = ID_MONHOC;
    }

    /**
     * 取得LA NGUOI HUAN LUYEN CNV
     * @returnEMPSN_HL LA NGUOI HUAN LUYEN CNV
     */
    @NotBlank
    @Column(name = "EMPSN_HL", length = 8)
    public java.lang.String getEMPSN_HL() {
        return EMPSN_HL;
    }
    /**
     * 設定LA NGUOI HUAN LUYEN CNV
     * @param EMPSN_HL LA NGUOI HUAN LUYEN CNV
     */
    public void setEMPSN_HL(java.lang.String EMPSN_HL) {
        this.EMPSN_HL = EMPSN_HL;
    }
    /**
     * @returnID_MONHOC 
     */
    @NotBlank
    @Column(name = "ID_MONHOC", length = 6)
    public java.lang.String getID_MONHOC() {
        return ID_MONHOC;
    }
    /**
     * @param ID_MONHOC 
     */
    public void setID_MONHOC(java.lang.String ID_MONHOC) {
        this.ID_MONHOC = ID_MONHOC;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_TRAINER_LISTPk))
            return false;
        N_TRAINER_LISTPk castOther = (N_TRAINER_LISTPk) other;
        return new EqualsBuilder().append(EMPSN_HL, castOther.EMPSN_HL).append(ID_MONHOC, castOther.ID_MONHOC).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN_HL).append(ID_MONHOC).toHashCode();
    }
}
