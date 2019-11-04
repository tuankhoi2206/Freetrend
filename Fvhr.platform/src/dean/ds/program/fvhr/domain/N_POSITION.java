package ds.program.fvhr.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* CHUC VU
**/
@Entity
@Table(name = "N_POSITION")
public class N_POSITION {
    private java.lang.String ID_POSITION;	//MA CHUC VU
    private java.lang.String NAME_POSITION;	//TEN CHUC VU
    private java.lang.Double BONUS_POSITION;	//PHU CAP CHUC VU
    private java.lang.Long BASIC_POSS;
    private java.lang.String NOTE;
    /**
     * �?�得MA CHUC VU
     * @return ID_POSITION MA CHUC VU
     */
    @Id
    @NotBlank
    @Column(name = "ID_POSITION")
    @Config(key = "N_POSITION.ID_POSITION")
    public java.lang.String getID_POSITION() {
        return ID_POSITION;
    }
    /**
     * 設定MA CHUC VU
     * @param ID_POSITION MA CHUC VU
     */
    public void setID_POSITION(java.lang.String ID_POSITION) {
        this.ID_POSITION = ID_POSITION;
    }
    /**
     * TEN CHUC VU
     * @return NAME_POSITION TEN CHUC VU
     */
    @Length(max = 50)
    @Column(name = "NAME_POSITION")
    @Config(key = "N_POSITION.NAME_POSITION")
    public java.lang.String getNAME_POSITION() {
        return NAME_POSITION;
    }
    /**
     * 設定TEN CHUC VU
     * @param NAME_POSITION TEN CHUC VU
     */
    public void setNAME_POSITION(java.lang.String NAME_POSITION) {
        this.NAME_POSITION = NAME_POSITION;
    }
    /**
     * �?�得PHU CAP CHUC VU
     * @return BONUS_POSITION PHU CAP CHUC VU
     */
    @Column(name = "BONUS_POSITION")
    @Config(key = "N_POSITION.BONUS_POSITION")
    public java.lang.Double getBONUS_POSITION() {
        return BONUS_POSITION;
    }
    /**
     * 設定PHU CAP CHUC VU
     * @param BONUS_POSITION PHU CAP CHUC VU
     */
    public void setBONUS_POSITION(java.lang.Double BONUS_POSITION) {
        this.BONUS_POSITION = BONUS_POSITION;
    }
    /**
     * @return BASIC_POSS 
     */
    @Column(name = "BASIC_POSS")
    @Config(key = "N_POSITION.BASIC_POSS")
    public java.lang.Long getBASIC_POSS() {
        return BASIC_POSS;
    }
    /**
     * @param BASIC_POSS 
     */
    public void setBASIC_POSS(java.lang.Long BASIC_POSS) {
        this.BASIC_POSS = BASIC_POSS;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 30)
    @Column(name = "NOTE")
    @Config(key = "N_POSITION.NOTE")
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
