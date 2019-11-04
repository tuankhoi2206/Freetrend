package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.ATMPk;
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
@IdClass(ATMPk.class)
@Entity
@Table(name = "ATM")
public class ATM {
    private java.lang.String NAME;
    private java.lang.String CODE;
    private java.lang.String NAMES;
    private java.lang.String NN_FACT;
    /**
     * @return NAME 
     */
    @Length(max = 50)
    @Column(name = "NAME")
    @Config(key = "ATM.NAME")
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
     * @return CODE 
     */
    @Id
    @NotBlank
    @Column(name = "CODE")
    @Config(key = "ATM.CODE")
    public java.lang.String getCODE() {
        return CODE;
    }
    /**
     * @param CODE 
     */
    public void setCODE(java.lang.String CODE) {
        this.CODE = CODE;
    }
    /**
     * @return NAMES 
     */
    @Length(max = 50)
    @Column(name = "NAMES")
    @Config(key = "ATM.NAMES")
    public java.lang.String getNAMES() {
        return NAMES;
    }
    /**
     * @param NAMES 
     */
    public void setNAMES(java.lang.String NAMES) {
        this.NAMES = NAMES;
    }
    /**
     * @return NN_FACT 
     */
    @Id
    @NotBlank
    @Column(name="N_FACT")
    @Config(key = "ATM.N_FACT")
    public java.lang.String getNN_FACT() {
        return NN_FACT;
    }
    /**
     * @param NN_FACT 
     */
    public void setNN_FACT(java.lang.String NN_FACT) {
        this.NN_FACT = NN_FACT;
    }
}
