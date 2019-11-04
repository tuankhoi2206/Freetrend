package ds.program.fvhr.domain;


import javax.persistence.IdClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import ds.program.fvhr.domain.pk.N_DEPT_LOCK_OTPk;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@IdClass(N_DEPT_LOCK_OTPk.class)
@Entity
@Table(name = "N_DEPT_LOCK_OT")
public class N_DEPT_LOCK_OT {
    private java.util.Date DATES;
    private java.lang.String DEPSN;
    private java.lang.String STATUS;
    private java.lang.String NOTE;
    /**
     * @return DATES 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATES")
    @Config(key = "N_DEPT_LOCK_OT.DATES")
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
     * @return DEPSN 
     */
    @Id
    @NotBlank
    @Config(key = "N_DEPT_LOCK_OT.DEPSN")
    public java.lang.String getDEPSN() {
        return DEPSN;
    }
    /**
     * @param DEPSN 
     */
    public void setDEPSN(java.lang.String DEPSN) {
        this.DEPSN = DEPSN;
    }
    /**
     * @return STATUS 
     */
    @Length(max = 1)
    @Column(name = "STATUS")
    @Config(key = "N_DEPT_LOCK_OT.STATUS")
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
     * @return NOTE 
     */
    @Length(max = 200)
    @Column(name = "NOTE")
    @Config(key = "N_DEPT_LOCK_OT.NOTE")
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
