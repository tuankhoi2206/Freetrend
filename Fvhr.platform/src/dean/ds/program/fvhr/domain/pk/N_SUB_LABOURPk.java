package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_SUB_LABOURPk implements Serializable {
    private java.lang.String ID_CONTRACT;	//MA SO PLHD
    private java.util.Date DATES_SIGN;
    private java.lang.String CLOCK;
    public N_SUB_LABOURPk() {
        super();
    }
    public N_SUB_LABOURPk(java.lang.String ID_CONTRACT, java.util.Date DATES_SIGN, java.lang.String CLOCK) {
        super();
        this.ID_CONTRACT = ID_CONTRACT;
        this.DATES_SIGN = DATES_SIGN;
        this.CLOCK = CLOCK;
    }

    /**
     * 取得MA SO PLHD
     * @returnID_CONTRACT MA SO PLHD
     */
    @NotBlank
    @Column(name = "ID_CONTRACT", length = 10)
    public java.lang.String getID_CONTRACT() {
        return ID_CONTRACT;
    }
    /**
     * 設定MA SO PLHD
     * @param ID_CONTRACT MA SO PLHD
     */
    public void setID_CONTRACT(java.lang.String ID_CONTRACT) {
        this.ID_CONTRACT = ID_CONTRACT;
    }
    /**
     * @returnDATES_SIGN 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATES_SIGN")
    public java.util.Date getDATES_SIGN() {
        return DATES_SIGN;
    }
    /**
     * @param DATES_SIGN 
     */
    public void setDATES_SIGN(java.util.Date DATES_SIGN) {
        this.DATES_SIGN = DATES_SIGN;
    }
    /**
     * @returnCLOCK 
     */
    @NotBlank
    @Column(name = "CLOCK", length = 1)
    public java.lang.String getCLOCK() {
        return CLOCK;
    }
    /**
     * @param CLOCK 
     */
    public void setCLOCK(java.lang.String CLOCK) {
        this.CLOCK = CLOCK;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_SUB_LABOURPk))
            return false;
        N_SUB_LABOURPk castOther = (N_SUB_LABOURPk) other;
        return new EqualsBuilder().append(ID_CONTRACT, castOther.ID_CONTRACT).append(DATES_SIGN, castOther.DATES_SIGN).append(CLOCK, castOther.CLOCK).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ID_CONTRACT).append(DATES_SIGN).append(CLOCK).toHashCode();
    }
}
