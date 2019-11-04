package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_HOSPITALPk implements Serializable {
    private java.lang.String ID_HOS;	//MA BENH VIEN, 3 ky tu
    private java.lang.String ID_PROVINCE;	//MA TINH, THANH
    public N_HOSPITALPk() {
        super();
    }
    public N_HOSPITALPk(java.lang.String ID_HOS, java.lang.String ID_PROVINCE) {
        super();
        this.ID_HOS = ID_HOS;
        this.ID_PROVINCE = ID_PROVINCE;
    }

    /**
     * 取得MA BENH VIEN, 3 ky tu
     * @returnID_HOS MA BENH VIEN, 3 ky tu
     */
    @NotBlank
    @Column(name = "ID_HOS", length = 5)
    public java.lang.String getID_HOS() {
        return ID_HOS;
    }
    /**
     * 設定MA BENH VIEN, 3 ky tu
     * @param ID_HOS MA BENH VIEN, 3 ky tu
     */
    public void setID_HOS(java.lang.String ID_HOS) {
        this.ID_HOS = ID_HOS;
    }
    /**
     * 取得MA TINH, THANH
     * @returnID_PROVINCE MA TINH, THANH
     */
    @NotBlank
    @Column(name = "ID_PROVINCE", length = 5)
    public java.lang.String getID_PROVINCE() {
        return ID_PROVINCE;
    }
    /**
     * 設定MA TINH, THANH
     * @param ID_PROVINCE MA TINH, THANH
     */
    public void setID_PROVINCE(java.lang.String ID_PROVINCE) {
        this.ID_PROVINCE = ID_PROVINCE;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_HOSPITALPk))
            return false;
        N_HOSPITALPk castOther = (N_HOSPITALPk) other;
        return new EqualsBuilder().append(ID_HOS, castOther.ID_HOS).append(ID_PROVINCE, castOther.ID_PROVINCE).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ID_HOS).append(ID_PROVINCE).toHashCode();
    }
}
