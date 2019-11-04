package ds.program.fvhr.domain;

import javax.persistence.FetchType;
import javax.persistence.IdClass;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;

import ds.program.fvhr.domain.pk.N_REGISTER_OVERTIMEPk;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@IdClass(N_REGISTER_OVERTIMEPk.class)
@Entity
@Table(name = "N_REGISTER_OVERTIME")
public class N_REGISTER_OVERTIME {
    private java.lang.String EMPSN;
    private java.util.Date DATE_OVER;
    private Float COUNT_TIME;
    private java.lang.String LOCKED;
    private String USER_UP;
    private java.util.Date DATE_UP;
    
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_REGISTER_OVERTIME.EMPSN")
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
     * @return DATE_OVER 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OVER")
    @Config(key = "N_REGISTER_OVERTIME.DATE_OVER")
    public java.util.Date getDATE_OVER() {
        return DATE_OVER;
    }
    /**
     * @param DATE_OVER 
     */
    public void setDATE_OVER(java.util.Date DATE_OVER) {
        this.DATE_OVER = DATE_OVER;
    }
    /**
     * @return COUNT_TIME 
     */
    @Column(name = "COUNT_TIME")
    @Config(key = "N_REGISTER_OVERTIME.COUNT_TIME")
    public java.lang.Float getCOUNT_TIME() {
        return COUNT_TIME;
    }
    /**
     * @param COUNT_TIME 
     */
   // public void setCOUNT_TIME(java.lang.Long COUNT_TIME) { //old
     public void setCOUNT_TIME(java.lang.Float COUNT_TIME) {
        this.COUNT_TIME = COUNT_TIME;
    }
    /**
     * @return LOCKED 
     */
    @Length(max = 1)
    @Column(name = "LOCKED")
    @Config(key = "N_REGISTER_OVERTIME.LOCKED")
    public java.lang.String getLOCKED() {
        return LOCKED;
    }
    /**
     * @param LOCKED 
     */
    public void setLOCKED(java.lang.String LOCKED) {
        this.LOCKED = LOCKED;
    }
    
    /**
     * @return USER_UP 
     */
    @Length(max = 50)
    @Column(name = "USER_UP")
    @Config(key = "N_JOB.USER_UP")
    public java.lang.String getUSER_UP() {
        return USER_UP;
    }
    /**
     * @param USER_UP 
     */
    public void setUSER_UP(java.lang.String USER_UP) {
        this.USER_UP = USER_UP;
    }
    /**
     * @return DATE_UP 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_UP")
    @Config(key = "N_JOB.DATE_UP")
    public java.util.Date getDATE_UP() {
        return DATE_UP;
    }
    /**
     * @param DATE_UP 
     */
    public void setDATE_UP(java.util.Date DATE_UP) {
        this.DATE_UP = DATE_UP;
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

}
