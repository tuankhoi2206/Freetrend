package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import ds.program.fvhr.domain.pk.N_EMP_JOB_DETAILPk;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@IdClass(N_EMP_JOB_DETAILPk.class)
@Entity
@Table(name = "N_EMP_JOB_DETAIL")
public class N_EMP_JOB_DETAIL {
    private java.lang.String EMPSN;
    private java.lang.String ID_JOB;
    private java.util.Date DATE_B;
    private java.util.Date DATE_E;
    private java.lang.String USER_UPDATE;
    private java.util.Date DATE_UPDATE;
    private String NOTE;
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_EMP_JOB_DETAIL.EMPSN")
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
     * @return ID_JOB 
     */
    @Id
    @NotBlank
    @Column(name = "ID_JOB")
    @Config(key = "N_EMP_JOB_DETAIL.ID_JOB")
    public java.lang.String getID_JOB() {
        return ID_JOB;
    }
    /**
     * @param ID_JOB 
     */
    public void setID_JOB(java.lang.String ID_JOB) {
        this.ID_JOB = ID_JOB;
    }
    /**
     * @return DATE_B 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_B")
    @Config(key = "N_EMP_JOB_DETAIL.DATE_B")
    public java.util.Date getDATE_B() {
        return DATE_B;
    }
    /**
     * @param DATE_B 
     */
    public void setDATE_B(java.util.Date DATE_B) {
        this.DATE_B = DATE_B;
    }
    /**
     * @return DATE_E 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_E")
    @Config(key = "N_EMP_JOB_DETAIL.DATE_E")
    public java.util.Date getDATE_E() {
        return DATE_E;
    }
    /**
     * @param DATE_E 
     */
    public void setDATE_E(java.util.Date DATE_E) {
        this.DATE_E = DATE_E;
    }
    /**
     * @return USER_UPDATE 
     */
    @Length(max = 100)
    @Column(name = "USER_UPDATE")
    @Config(key = "N_EMP_JOB_DETAIL.USER_UPDATE")
    public java.lang.String getUSER_UPDATE() {
        return USER_UPDATE;
    }
    /**
     * @param USER_UPDATE 
     */
    public void setUSER_UPDATE(java.lang.String USER_UPDATE) {
        this.USER_UPDATE = USER_UPDATE;
    }
    /**
     * @return DATE_UPDATE 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_UPDATE")
    @Config(key = "N_EMP_JOB_DETAIL.DATE_UPDATE")
    public java.util.Date getDATE_UPDATE() {
        return DATE_UPDATE;
    }
    /**
     * @param DATE_UPDATE 
     */
    public void setDATE_UPDATE(java.util.Date DATE_UPDATE) {
        this.DATE_UPDATE = DATE_UPDATE;
    }
    
    /**
     * @return USER_UPDATE 
     */
    @Length(max 	= 1000)
    @Column(name 	= "NOTE")
    @Config(key 	= "N_EMP_JOB_DETAIL.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * @param USER_UPDATE 
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
    
}
