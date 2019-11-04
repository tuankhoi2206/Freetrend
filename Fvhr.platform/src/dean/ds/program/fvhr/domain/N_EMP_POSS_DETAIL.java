package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import ds.program.fvhr.domain.pk.N_EMP_POSS_DETAILPk;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@IdClass(N_EMP_POSS_DETAILPk.class)
@Entity
@Table(name = "N_EMP_POSS_DETAIL")
public class N_EMP_POSS_DETAIL {
    private java.lang.String EMPSN;
    private java.lang.String ID_POSS;
    private java.util.Date DATE_B;
    private java.util.Date DATE_E;
    private java.lang.String USER_UP;
    private java.util.Date DATE_UP;
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_EMP_POSS_DETAIL.EMPSN")
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
     * @return ID_POSS 
     */
    @Id
    @NotBlank
    @Column(name = "ID_POSS")
    @Config(key = "N_EMP_POSS_DETAIL.ID_POSS")
    public java.lang.String getID_POSS() {
        return ID_POSS;
    }
    /**
     * @param ID_POSS 
     */
    public void setID_POSS(java.lang.String ID_POSS) {
        this.ID_POSS = ID_POSS;
    }
    /**
     * @return DATE_B 
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_B")
    @Config(key = "N_EMP_POSS_DETAIL.DATE_B")
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
    @Config(key = "N_EMP_POSS_DETAIL.DATE_E")
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
     * @return USER_UP 
     */
    @Length(max = 20)
    @Column(name = "USER_UP")
    @Config(key = "N_EMP_POSS_DETAIL.USER_UP")
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
    @Config(key = "N_EMP_POSS_DETAIL.DATE_UP")
    public java.util.Date getDATE_UP() {
        return DATE_UP;
    }
    /**
     * @param DATE_UP 
     */
    public void setDATE_UP(java.util.Date DATE_UP) {
        this.DATE_UP = DATE_UP;
    }
}
