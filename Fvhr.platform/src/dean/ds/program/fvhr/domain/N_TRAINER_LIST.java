package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_TRAINER_LISTPk;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Id;
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
@IdClass(N_TRAINER_LISTPk.class)
@Entity
@Table(name = "N_TRAINER_LIST")
public class N_TRAINER_LIST {
    private java.lang.String EMPSN_HL;	//LA NGUOI HUAN LUYEN CNV
    private java.lang.String ID_MONHOC;
    private java.lang.String NOTE;
    /**
     * 取得LA NGUOI HUAN LUYEN CNV
     * @return EMPSN_HL LA NGUOI HUAN LUYEN CNV
     */
    @Id
    @NotBlank
    @Column(name="EMPSN_HL")    
    @Config(key = "N_TRAINER_LIST.EMPSN_HL")
    public java.lang.String getEMPSN_HL() {
        return EMPSN_HL;
    }
    /**
     * 設定LA NGUOI HUAN LUYEN CNV
     * @param EMPSN_HL LA NGUOI HUAN LUYEN CNV
     */
    public void setEMPSN_HL(java.lang.String EMPSN_HL) {
        this.EMPSN_HL = EMPSN_HL;
    }
    /**
     * @return ID_MONHOC 
     */
    @Id
    @NotBlank
    @Column(name="ID_MONHOC")
    @Config(key = "N_TRAINER_LIST.ID_MONHOC")
    public java.lang.String getID_MONHOC() {
        return ID_MONHOC;
    }
    /**
     * @param ID_MONHOC 
     */
    public void setID_MONHOC(java.lang.String ID_MONHOC) {
        this.ID_MONHOC = ID_MONHOC;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 150)
    @Column(name = "NOTE")
    @Config(key = "N_TRAINER_LIST.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * @param NOTE 
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
    private N_EMPLOYEE EMPSN_HL_Object;
    /**
     * 取得LA NGUOI HUAN LUYEN CNV
     * @return EMPSN_HL LA NGUOI HUAN LUYEN CNV
     */
    @ManyToOne()
    @JoinColumn(name="EMPSN_HL", referencedColumnName="EMPSN", insertable=false, updatable=false)
    @NotFound(action=NotFoundAction.IGNORE)
    @Config(key = "N_TRAINER_LIST.EMPSN_HL")
    public N_EMPLOYEE getEMPSN_HL_Object() {
        return EMPSN_HL_Object;
    }
    /**
     * 設定LA NGUOI HUAN LUYEN CNV
     * @param EMPSN_HL LA NGUOI HUAN LUYEN CNV
     */
    public void setEMPSN_HL_Object(N_EMPLOYEE EMPSN_HL) {
        this.EMPSN_HL_Object = EMPSN_HL;
    }
    private N_TRAINING_LIST ID_MONHOC_Object;
    /**
     * @return ID_MONHOC 
     */
    @ManyToOne
    @JoinColumn(name="ID_MONHOC", referencedColumnName="MA_MON_HOC", insertable=false, updatable=false)
    @NotFound(action=NotFoundAction.IGNORE)
    @Config(key = "N_TRAINER_LIST.ID_MONHOC")
    public N_TRAINING_LIST getID_MONHOC_Object() {
        return ID_MONHOC_Object;
    }
    /**
     * @param ID_MONHOC 
     */
    public void setID_MONHOC_Object(N_TRAINING_LIST ID_MONHOC) {
        this.ID_MONHOC_Object = ID_MONHOC;
    }
}
