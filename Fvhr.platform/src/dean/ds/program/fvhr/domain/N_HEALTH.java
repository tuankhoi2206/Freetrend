package ds.program.fvhr.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.Length;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@Entity
@Table(name = "N_HEALTH")
public class N_HEALTH {
    private java.lang.String EMPSN;
    private java.util.Date DATES;
    private java.util.Date EXPIRE;
    private java.lang.Long SALARY;
    private java.lang.String NOTE;
    private java.lang.String CLOCK;
    private java.lang.String ID_HEALTH;
    private java.lang.String ID_HOS;
    private java.lang.String ID_KEY;
    private java.lang.String ID_PRO;
    private java.util.Date DATE_BHYT;
    /**
     * @return EMPSN 
     */
    @NotBlank
    @Length(max = 8)
    @Column(name = "EMPSN")
    @Config(key = "N_HEALTH.EMPSN")
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
     * @return DATES 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATES")
    @Config(key = "N_HEALTH.DATES")
    public java.util.Date getDATES() {
        return DATES;
    }
    /**
     * @param DATES 
     */
    public void setDATES(java.util.Date DATES) {
        this.DATES = DATES;
    }
    /**
     * @return EXPIRE 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "EXPIRE")
    @Config(key = "N_HEALTH.EXPIRE")
    public java.util.Date getEXPIRE() {
        return EXPIRE;
    }
    /**
     * @param EXPIRE 
     */
    public void setEXPIRE(java.util.Date EXPIRE) {
        this.EXPIRE = EXPIRE;
    }
    /**
     * @return SALARY 
     */
    @NotBlank
    @Column(name = "SALARY")
    @Config(key = "N_HEALTH.SALARY")
    public java.lang.Long getSALARY() {
        return SALARY;
    }
    /**
     * @param SALARY 
     */
    public void setSALARY(java.lang.Long SALARY) {
        this.SALARY = SALARY;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 100)
    @Column(name = "NOTE")
    @Config(key = "N_HEALTH.NOTE")
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
     * @return CLOCK 
     */
    @NotBlank
    @Length(max = 1)
    @Column(name = "CLOCK")
    @Config(key = "N_HEALTH.CLOCK")
    public java.lang.String getCLOCK() {
        return CLOCK;
    }
    /**
     * @param CLOCK 
     */
    public void setCLOCK(java.lang.String CLOCK) {
        this.CLOCK = CLOCK;
    }
    /**
     * @return ID_HEALTH 
     */
    @Length(max = 16)
    @Column(name = "ID_HEALTH")
    @Config(key = "N_HEALTH.ID_HEALTH")
    public java.lang.String getID_HEALTH() {
        return ID_HEALTH;
    }
    /**
     * @param ID_HEALTH 
     */
    public void setID_HEALTH(java.lang.String ID_HEALTH) {
        this.ID_HEALTH = ID_HEALTH;
    }
    /**
     * @return ID_HOS 
     */
    @NotBlank
    @Length(max = 5)
    @Column(name = "ID_HOS")
    @Config(key = "N_HEALTH.ID_HOS")
    public java.lang.String getID_HOS() {
        return ID_HOS;
    }
    /**
     * @param ID_HOS 
     */
    public void setID_HOS(java.lang.String ID_HOS) {
        this.ID_HOS = ID_HOS;
    }
    /**
     * @return ID_KEY 
     */
    @Id
    @NotBlank
    @Column(name = "ID_KEY")
    @Config(key = "N_HEALTH.ID_KEY")
    public java.lang.String getID_KEY() {
        return ID_KEY;
    }
    /**
     * @param ID_KEY 
     */
    public void setID_KEY(java.lang.String ID_KEY) {
        this.ID_KEY = ID_KEY;
    }
    /**
     * @return ID_PRO 
     */
    @Length(max = 5)
    @Column(name = "ID_PRO")
    @Config(key = "N_HEALTH.ID_PRO")
    public java.lang.String getID_PRO() {
        return ID_PRO;
    }
    /**
     * @param ID_PRO 
     */
    public void setID_PRO(java.lang.String ID_PRO) {
        this.ID_PRO = ID_PRO;
    }
    /**
     * @return DATE_BHYT 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_BHYT")
    @Config(key = "N_HEALTH.DATE_BHYT")
    public java.util.Date getDATE_BHYT() {
        return DATE_BHYT;
    }
    /**
     * @param DATE_BHYT 
     */
    public void setDATE_BHYT(java.util.Date DATE_BHYT) {
        this.DATE_BHYT = DATE_BHYT;
    }
    
private N_EMPLOYEE EMPSN_Object;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="EMPSN", referencedColumnName="EMPSN", insertable=false, updatable=false)
    @NotFound(action=NotFoundAction.IGNORE)
    @Config(key = "N_EMPLOYEE.EMPSN")
    public N_EMPLOYEE getEMPSN_Object() {
        return EMPSN_Object;
    }
    
    public void setEMPSN_Object(N_EMPLOYEE empsn) {
        this.EMPSN_Object = empsn;
    }
    
    
   private N_HOSPITAL ID_HOS_Object;
    
 	
 
    @ManyToOne(fetch=FetchType.EAGER)
    
    @JoinColumns( {
        @JoinColumn(name = "ID_HOS", referencedColumnName = "ID_HOS",  insertable=false, updatable=false),
        @JoinColumn(name = "ID_PRO", referencedColumnName = "ID_PROVINCE", insertable=false, updatable=false )
    	})
    @NotFound(action=NotFoundAction.IGNORE)
    
   
    public N_HOSPITAL getID_HOS_Object() {
        return ID_HOS_Object;
    }
   
    public void setID_HOS_Object(N_HOSPITAL ID_HOS) {
        this.ID_HOS_Object = ID_HOS;
    }
    
 /*  @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ID_PRO", referencedColumnName="ID_PROVINCE", insertable=false, updatable=false) 
    @NotFound(action=NotFoundAction.IGNORE)
    @Config(key="N_HOSPITAL.ID_PROVINCE")
   
    public N_HOSPITAL getID_PROVINCE_Object() {
        return ID_HOS_Object;
    }
   
    public void setID_PROVINCE_Object(N_HOSPITAL ID_HOS) {
        this.ID_HOS_Object = ID_HOS;
    }
    */
    
    
   
    
   
 
    
}
