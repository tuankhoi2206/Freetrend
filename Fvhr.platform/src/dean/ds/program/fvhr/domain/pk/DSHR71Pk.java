package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class DSHR71Pk implements Serializable {
    private java.util.Date VDATE;	//THOI GIAN, AP DUNG FVL va FVLS
    private java.lang.String PNL;	//SO THE
    public DSHR71Pk() {
        super();
    }
    public DSHR71Pk(java.util.Date VDATE, java.lang.String PNL) {
        super();
        this.VDATE = VDATE;
        this.PNL = PNL;
    }

    /**
     * 取得THOI GIAN, AP DUNG FVL va FVLS
     * @returnVDATE THOI GIAN, AP DUNG FVL va FVLS
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "VDATE")
    public java.util.Date getVDATE() {
        return VDATE;
    }
    /**
     * 設定THOI GIAN, AP DUNG FVL va FVLS
     * @param VDATE THOI GIAN, AP DUNG FVL va FVLS
     */
    public void setVDATE(java.util.Date VDATE) {
        this.VDATE = VDATE;
    }
    /**
     * 取得SO THE
     * @returnPNL SO THE
     */
    @NotBlank
    @Column(name = "PNL", length = 8)
    public java.lang.String getPNL() {
        return PNL;
    }
    /**
     * 設定SO THE
     * @param PNL SO THE
     */
    public void setPNL(java.lang.String PNL) {
        this.PNL = PNL;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof DSHR71Pk))
            return false;
        DSHR71Pk castOther = (DSHR71Pk) other;
        return new EqualsBuilder().append(VDATE, castOther.VDATE).append(PNL, castOther.PNL).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(VDATE).append(PNL).toHashCode();
    }
}
