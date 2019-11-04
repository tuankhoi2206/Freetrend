package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_CHANGE_HOSPITALPk;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@IdClass(N_CHANGE_HOSPITALPk.class)
@Entity
@Table(name = "N_CHANGE_HOSPITAL")
public class N_CHANGE_HOSPITAL {
    private java.lang.String EMPSN;
    private java.util.Date DATE_CHANGE;
    private String IDHOS_OLD;
    private java.lang.String IDHOS_NEW;
    private java.lang.String NOTE;
    private java.lang.String STATUS;
    private String IDPRO_OLD;
    private java.lang.String IDPRO_NEW;
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_CHANGE_HOSPITAL.EMPSN")
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
     * @return DATE_CHANGE 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_CHANGE")
    @Config(key = "N_CHANGE_HOSPITAL.DATE_CHANGE")
    public java.util.Date getDATE_CHANGE() {
        return DATE_CHANGE;
    }
    /**
     * @param DATE_CHANGE 
     */
    public void setDATE_CHANGE(java.util.Date DATE_CHANGE) {
        this.DATE_CHANGE = DATE_CHANGE;
    }
    /**
     * @return IDHOS_OLD 
     */
    @Length(max = 5)
    @Column(name = "IDHOS_OLD")
    @Config(key = "N_CHANGE_HOSPITAL.IDHOS_OLD")   
    public String getIDHOS_OLD() {
        return IDHOS_OLD;
    }
    /**
     * @param IDHOS_OLD 
     */
    public void setIDHOS_OLD(String IDHOS_OLD) {
        this.IDHOS_OLD = IDHOS_OLD;
    }
    /**
     * @return IDHOS_NEW 
     */
    @Id
    @NotBlank
    @Column(name = "IDHOS_NEW")
    @Config(key = "N_CHANGE_HOSPITAL.IDHOS_NEW")
    public java.lang.String getIDHOS_NEW() {
        return IDHOS_NEW;
    }
    /**
     * @param IDHOS_NEW 
     */
    public void setIDHOS_NEW(java.lang.String IDHOS_NEW) {
        this.IDHOS_NEW = IDHOS_NEW;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 250)
    @Column(name = "NOTE")
    @Config(key = "N_CHANGE_HOSPITAL.NOTE")
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
     * @return STATUS 
     */
    @Id
    @NotBlank
    @Column(name = "STATUS")
    @Config(key = "N_CHANGE_HOSPITAL.STATUS")
    public java.lang.String getSTATUS() {
        return STATUS;
    }
    /**
     * @param STATUS 
     */
    public void setSTATUS(java.lang.String STATUS) {
        this.STATUS = STATUS;
    }
    /**
     * @return IDPRO_OLD 
     */
    @Length(max = 5)
    @Column(name = "IDPRO_OLD")
    @Config(key = "N_CHANGE_HOSPITAL.IDPRO_OLD")      
    public String getIDPRO_OLD() {
        return IDPRO_OLD;
    }
    /**
     * @param IDPRO_OLD 
     */
    public void setIDPRO_OLD(String IDPRO_OLD) {
        this.IDPRO_OLD = IDPRO_OLD;
    }
    /**
     * @return IDPRO_NEW 
     */
    @Id
    @NotBlank
    @Column(name = "IDPRO_NEW")
    @Config(key = "N_CHANGE_HOSPITAL.IDPRO_NEW")
    public java.lang.String getIDPRO_NEW() {
        return IDPRO_NEW;
    }
    /**
     * @param IDPRO_NEW 
     */
    public void setIDPRO_NEW(java.lang.String IDPRO_NEW) {
        this.IDPRO_NEW = IDPRO_NEW;
    }
    private N_EMPLOYEE EMPSN_Object;
    /**
     * @return EMPSN 
     */
    @ManyToOne
    @JoinColumn(name="EMPSN", referencedColumnName="EMPSN", insertable=false, updatable=false)
    @NotFound(action=NotFoundAction.IGNORE)
    @Config(key = "N_CHANGE_HOSPITAL.EMPSN")
    public N_EMPLOYEE getEMPSN_Object() {
        return EMPSN_Object;
    }
    /**
     * @param EMPSN 
     */
    public void setEMPSN_Object(N_EMPLOYEE EMPSN) {
        this.EMPSN_Object = EMPSN;
    }
   
  
}
