package ds.program.fvhr.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@Entity
@Table(name = "N_REST_ANNUAL_TYPE")
public class N_REST_ANNUAL_TYPE {
    private java.lang.String YEAR;
    private java.lang.Long CO_ARR;
    private java.lang.Long EMP_APP;
    private java.lang.Long HOL_LUNAR;
    private java.lang.String NOTE;
    /**
     * @return YEAR 
     */
    @Id
    @NotBlank
    @Column(name = "YEAR")
    @Config(key = "N_REST_ANNUAL_TYPE.YEAR")
    public java.lang.String getYEAR() {
        return YEAR;
    }
    /**
     * @param YEAR 
     */
    public void setYEAR(java.lang.String YEAR) {
        this.YEAR = YEAR;
    }
    /**
     * @return CO_ARR 
     */
    @Column(name = "CO_ARR")
    @Config(key = "N_REST_ANNUAL_TYPE.CO_ARR")
    public java.lang.Long getCO_ARR() {
        return CO_ARR;
    }
    /**
     * @param CO_ARR 
     */
    public void setCO_ARR(java.lang.Long CO_ARR) {
        this.CO_ARR = CO_ARR;
    }
    /**
     * @return EMP_APP 
     */
    @Column(name = "EMP_APP")
    @Config(key = "N_REST_ANNUAL_TYPE.EMP_APP")
    public java.lang.Long getEMP_APP() {
        return EMP_APP;
    }
    /**
     * @param EMP_APP 
     */
    public void setEMP_APP(java.lang.Long EMP_APP) {
        this.EMP_APP = EMP_APP;
    }
    /**
     * @return HOL_LUNAR 
     */
    @Column(name = "HOL_LUNAR")
    @Config(key = "N_REST_ANNUAL_TYPE.HOL_LUNAR")
    public java.lang.Long getHOL_LUNAR() {
        return HOL_LUNAR;
    }
    /**
     * @param HOL_LUNAR 
     */
    public void setHOL_LUNAR(java.lang.Long HOL_LUNAR) {
        this.HOL_LUNAR = HOL_LUNAR;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 50)
    @Column(name = "NOTE")
    @Config(key = "N_REST_ANNUAL_TYPE.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * @param NOTE 
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
}
