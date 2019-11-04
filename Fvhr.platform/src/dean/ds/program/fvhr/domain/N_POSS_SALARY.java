package ds.program.fvhr.domain;
import javax.persistence.IdClass;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ds.program.fvhr.domain.pk.N_POSS_SALARYPk;
import dsc.util.hibernate.validator.NotBlank;
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
@IdClass(N_POSS_SALARYPk.class)
@Entity
@Table(name = "N_POSS_SALARY")
public class N_POSS_SALARY {
    private java.lang.String ID_POSS;
    private java.lang.String LEVEL_POSS;
    private java.lang.Long MONEY;
    private java.lang.String NOTE;
    private java.util.Date DATE_HL;
    
    /**
     * @return ID_POSS 
     */
    @Id
    @NotBlank
    @Config(key = "N_POSS_SALARY.ID_POSS")
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
     * @return LEVEL_POSS 
     */
    @Id
    @NotBlank
    @Column(name = "LEVEL_POSS")
    @Config(key = "N_POSS_SALARY.LEVEL_POSS")
    public java.lang.String getLEVEL_POSS() {
        return LEVEL_POSS;
    }
    /**
     * @param LEVEL_POSS 
     */
    public void setLEVEL_POSS(java.lang.String LEVEL_POSS) {
        this.LEVEL_POSS = LEVEL_POSS;
    }
    /**
     * @return MONEY 
     */
    @NotBlank
    @Column(name = "MONEY")
    @Config(key = "N_POSS_SALARY.MONEY")
    public java.lang.Long getMONEY() {
        return MONEY;
    }
    /**
     * @param MONEY 
     */
    public void setMONEY(java.lang.Long MONEY) {
        this.MONEY = MONEY;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 500)
    @Column(name = "NOTE")
    @Config(key = "N_POSS_SALARY.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * @param NOTE 
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
    private N_POSITION ID_POSS_Object;
    /**
     * @return ID_POSS 
     */
    @ManyToOne
    @JoinColumn(name="ID_POSS", referencedColumnName="ID_POSITION", insertable=false, updatable=false)
    @NotFound(action=NotFoundAction.IGNORE)
    @Config(key = "N_POSS_SALARY.ID_POSS")
    public N_POSITION getID_POSS_Object() {
        return ID_POSS_Object;
    }
    /**
     * @param ID_POSS 
     */
    public void setID_POSS_Object(N_POSITION ID_POSS) {
        this.ID_POSS_Object = ID_POSS;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_HL")
    @Config(key = "N_POSS_SALARY.DATE_HL")
    public java.util.Date getDATE_HL() {
        return DATE_HL;
    }
    /**
     * @param DATE_UP 
     */
    public void setDATE_HL(java.util.Date DATE_HL) {
        this.DATE_HL = DATE_HL;
    }
    
    
}
