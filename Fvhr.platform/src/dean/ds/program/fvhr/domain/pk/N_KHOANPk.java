package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_KHOANPk implements Serializable {
    private java.lang.String ID_KHOAN;
    private java.lang.String ID_DIEU;
    public N_KHOANPk() {
        super();
    }
    public N_KHOANPk(java.lang.String ID_KHOAN, java.lang.String ID_DIEU) {
        super();
        this.ID_KHOAN = ID_KHOAN;
        this.ID_DIEU = ID_DIEU;
    }

    /**
     * @returnID_KHOAN 
     */
    @NotBlank
    @Column(name = "ID_KHOAN", length = 10)
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
     * @returnID_DIEU 
     */
    @NotBlank
    @Column(name = "ID_DIEU", length = 10)
    public java.lang.String getID_DIEU() {
        return ID_DIEU;
    }
    /**
     * @param ID_DIEU 
     */
    public void setID_DIEU(java.lang.String ID_DIEU) {
        this.ID_DIEU = ID_DIEU;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_KHOANPk))
            return false;
        N_KHOANPk castOther = (N_KHOANPk) other;
        return new EqualsBuilder().append(ID_KHOAN, castOther.ID_KHOAN).append(ID_DIEU, castOther.ID_DIEU).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ID_KHOAN).append(ID_DIEU).toHashCode();
    }
}
