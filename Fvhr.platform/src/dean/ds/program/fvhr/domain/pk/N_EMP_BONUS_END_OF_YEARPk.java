package ds.program.fvhr.domain.pk;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;
import dsc.util.hibernate.validator.NotBlank;
@Embeddable
public class N_EMP_BONUS_END_OF_YEARPk implements Serializable {
    private java.lang.String EMPSN;
    private java.util.Date DATE_EFFECT;	//THANG PHAT TIEN THUONG LUONG T13
    private java.lang.String YEAR;
    public N_EMP_BONUS_END_OF_YEARPk() {
        super();
    }
    public N_EMP_BONUS_END_OF_YEARPk(java.lang.String EMPSN, java.util.Date DATE_EFFECT) {
        super();
        this.EMPSN = EMPSN;
        this.DATE_EFFECT = DATE_EFFECT;
        this.YEAR	= YEAR;
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
     * THANG PHAT TIEN THUONG LUONG T13
     * @returnDATE_EFFECT THANG PHAT TIEN THUONG LUONG T13
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_EFFECT")
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
    /**
     * 設定TIEN THUONG T13 CUA NAM NAO?
     * @param YEAR TIEN THUONG T13 CUA NAM NAO?
     */
    @NotBlank
    @Column(name = "YEAR", length = 4)
    public java.lang.String getYEAR() {
        return YEAR;
    }
    /**
     * 設定TIEN THUONG T13 CUA NAM NAO?
     * @param YEAR TIEN THUONG T13 CUA NAM NAO?
     */
    public void setYEAR(java.lang.String YEAR) {
        this.YEAR = YEAR;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_EMP_BONUS_END_OF_YEARPk))
            return false;
        N_EMP_BONUS_END_OF_YEARPk castOther = (N_EMP_BONUS_END_OF_YEARPk) other;
        return new EqualsBuilder().append(EMPSN, castOther.EMPSN).append(DATE_EFFECT, castOther.DATE_EFFECT).append(YEAR, castOther.YEAR).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(EMPSN).append(DATE_EFFECT).append(YEAR).toHashCode();
    }
}
