package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_TRAINING_DETAILPk;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@IdClass(N_TRAINING_DETAILPk.class)
@Entity
@Table(name = "N_TRAINING_DETAIL")
public class N_TRAINING_DETAIL {
    private java.lang.String EMPSN;
    private java.util.Date DATE_THI;
    private java.lang.Long DIEM;
    private java.lang.String XEPLOAI;
    private java.lang.String GHICHU;
    private java.lang.String ID_TRAINING;	//SO THE CONG VOI SO LAN DUOC HUAN LUYEN
    private java.lang.Long LANTHI;
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_TRAINING_DETAIL.EMPSN")
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
     * @return DATE_THI 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_THI")
    @Config(key = "N_TRAINING_DETAIL.DATE_THI")
    public java.util.Date getDATE_THI() {
        return DATE_THI;
    }
    /**
     * @param DATE_THI 
     */
    public void setDATE_THI(java.util.Date DATE_THI) {
        this.DATE_THI = DATE_THI;
    }
    /**
     * @return DIEM 
     */
    @Column(name = "DIEM")
    @Config(key = "N_TRAINING_DETAIL.DIEM")
    public java.lang.Long getDIEM() {
        return DIEM;
    }
    /**
     * @param DIEM 
     */
    public void setDIEM(java.lang.Long DIEM) {
        this.DIEM = DIEM;
    }
    /**
     * @return XEPLOAI 
     */
    @Length(max = 15)
    @Column(name = "XEPLOAI")
    @Config(key = "N_TRAINING_DETAIL.XEPLOAI")
    public java.lang.String getXEPLOAI() {
        return XEPLOAI;
    }
    /**
     * @param XEPLOAI 
     */
    public void setXEPLOAI(java.lang.String XEPLOAI) {
        this.XEPLOAI = XEPLOAI;
    }
    /**
     * @return GHICHU 
     */
    @Length(max = 50)
    @Column(name = "GHICHU")
    @Config(key = "N_TRAINING_DETAIL.GHICHU")
    public java.lang.String getGHICHU() {
        return GHICHU;
    }
    /**
     * @param GHICHU 
     */
    public void setGHICHU(java.lang.String GHICHU) {
        this.GHICHU = GHICHU;
    }
    /**
     * 取得SO THE CONG VOI SO LAN DUOC HUAN LUYEN
     * @return ID_TRAINING SO THE CONG VOI SO LAN DUOC HUAN LUYEN
     */
    @Id
    @NotBlank
    @Config(key = "N_TRAINING_DETAIL.ID_TRAINING")
    public java.lang.String getID_TRAINING() {
        return ID_TRAINING;
    }
    /**
     * 設定SO THE CONG VOI SO LAN DUOC HUAN LUYEN
     * @param ID_TRAINING SO THE CONG VOI SO LAN DUOC HUAN LUYEN
     */
    public void setID_TRAINING(java.lang.String ID_TRAINING) {
        this.ID_TRAINING = ID_TRAINING;
    }
    /**
     * @return LANTHI 
     */
    @Column(name = "LANTHI")
    @Config(key = "N_TRAINING_DETAIL.LANTHI")
    public java.lang.Long getLANTHI() {
        return LANTHI;
    }
    /**
     * @param LANTHI 
     */
    public void setLANTHI(java.lang.Long LANTHI) {
        this.LANTHI = LANTHI;
    }
//    private N_TRAINING_ITEM ID_TRAINING_Object;
//    /**
//     * 取得SO THE CONG VOI SO LAN DUOC HUAN LUYEN
//     * @return ID_TRAINING SO THE CONG VOI SO LAN DUOC HUAN LUYEN
//     */
//    @ManyToOne
//    @JoinColumn(name="ID_TRAINING", referencedColumnName="ID_TRAINING", insertable=false, updatable=false)
//    @NotFound(action=NotFoundAction.IGNORE)
//    @Config(key = "N_TRAINING_DETAIL.ID_TRAINING")
//    public N_TRAINING_ITEM getID_TRAINING_Object() {
//        return ID_TRAINING_Object;
//    }
//    /**
//     * 設定SO THE CONG VOI SO LAN DUOC HUAN LUYEN
//     * @param ID_TRAINING SO THE CONG VOI SO LAN DUOC HUAN LUYEN
//     */
//    public void setID_TRAINING_Object(N_TRAINING_ITEM ID_TRAINING) {
//        this.ID_TRAINING_Object = ID_TRAINING;
//    }
}
