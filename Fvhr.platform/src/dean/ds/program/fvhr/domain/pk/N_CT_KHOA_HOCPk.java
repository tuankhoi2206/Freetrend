package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_CT_KHOA_HOCPk implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.lang.String ID_KHOA;
    private java.lang.String ID_MONHOC;
    public N_CT_KHOA_HOCPk() {
        super();
    }
    public N_CT_KHOA_HOCPk(java.lang.String ID_KHOA, java.lang.String ID_MONHOC) {
        super();
        this.ID_KHOA = ID_KHOA;
        this.ID_MONHOC = ID_MONHOC;
    }

    /**
     * @returnID_KHOA 
     */
    @NotBlank
    @Column(name = "ID_KHOA", length = 5)
    public java.lang.String getID_KHOA() {
        return ID_KHOA;
    }
    /**
     * @param ID_KHOA 
     */
    public void setID_KHOA(java.lang.String ID_KHOA) {
        this.ID_KHOA = ID_KHOA;
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
        if (!(other instanceof N_CT_KHOA_HOCPk))
            return false;
        N_CT_KHOA_HOCPk castOther = (N_CT_KHOA_HOCPk) other;
        return new EqualsBuilder().append(ID_KHOA, castOther.ID_KHOA).append(ID_MONHOC, castOther.ID_MONHOC).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ID_KHOA).append(ID_MONHOC).toHashCode();
    }
}
