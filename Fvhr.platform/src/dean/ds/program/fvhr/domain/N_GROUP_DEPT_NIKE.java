package ds.program.fvhr.domain;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* GOM NHOM DON VI THEO DV NIKE
**/
@Entity
@Table(name = "N_GROUP_DEPT_NIKE")
public class N_GROUP_DEPT_NIKE {
    private BigDecimal ID_GROUPNI;
    private java.lang.String NAME_NIKE;
    private java.lang.String NAME;
    private java.lang.String NOTE;
    private java.lang.String PRODUCTION;
    /**
     * @return ID_GROUPNI 
     */
    @Id
    @NotBlank
    @Column(name = "ID_GROUPNI")
    @Config(key = "N_GROUP_DEPT_NIKE.ID_GROUPNI")
    public BigDecimal getID_GROUPNI() {
        return ID_GROUPNI;
    }
    /**
     * @param ID_GROUPNI 
     */
    public void setID_GROUPNI(BigDecimal ID_GROUPNI) {
        this.ID_GROUPNI = ID_GROUPNI;
    }
    /**
     * @return NAME_NIKE 
     */
    @Length(max = 200)
    @Column(name = "NAME_NIKE")
    @Config(key = "N_GROUP_DEPT_NIKE.NAME_NIKE")
    public java.lang.String getNAME_NIKE() {
        return NAME_NIKE;
    }
    /**
     * @param NAME_NIKE 
     */
    public void setNAME_NIKE(java.lang.String NAME_NIKE) {
        this.NAME_NIKE = NAME_NIKE;
    }
    /**
     * @return NAME 
     */
    @Length(max = 200)
    @Column(name = "NAME")
    @Config(key = "N_GROUP_DEPT_NIKE.NAME")
    public java.lang.String getNAME() {
        return NAME;
    }
    /**
     * @param NAME 
     */
    public void setNAME(java.lang.String NAME) {
        this.NAME = NAME;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 200)
    @Column(name = "NOTE")
    @Config(key = "N_GROUP_DEPT_NIKE.NOTE")
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
     * @return PRODUCTION 
     */
    @Length(max = 1)
    @Column(name = "PRODUCTION")
    @Config(key = "N_GROUP_DEPT_NIKE.PRODUCTION")
    public java.lang.String getPRODUCTION() {
        return PRODUCTION;
    }
    /**
     * @param PRODUCTION 
     */
    public void setPRODUCTION(java.lang.String PRODUCTION) {
        this.PRODUCTION = PRODUCTION;
    }
}
