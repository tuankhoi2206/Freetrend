package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import  ds.program.fvhr.domain.pk.N_HOSPITALPk;
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
@IdClass(N_HOSPITALPk.class)
@Entity
@Table(name = "N_HOSPITAL")
public class N_HOSPITAL {
    private java.lang.String ID_HOS;	//MA BENH VIEN, 3 ky tu
    private java.lang.String NAME_HOSPITAL;	//ID_HOS CO THE TRUNG NHAU TUY THUOC VAO ID_PROVINCE
    private java.lang.String ADDRESS;
    private java.lang.String NOTE;
    private java.lang.String ID_PROVINCE;	//MA TINH, THANH
    /**
     * 取得MA BENH VIEN, 3 ky tu
     * @return ID_HOS MA BENH VIEN, 3 ky tu
     */
    @Id
    @NotBlank
    @Column(name = "ID_HOS")
    @Config(key = "N_HOSPITAL.ID_HOS")
    public java.lang.String getID_HOS() {
        return ID_HOS;
    }
    /**
     * 設定MA BENH VIEN, 3 ky tu
     * @param ID_HOS MA BENH VIEN, 3 ky tu
     */
    public void setID_HOS(java.lang.String ID_HOS) {
        this.ID_HOS = ID_HOS;
    }
    /**
     * 取得ID_HOS CO THE TRUNG NHAU TUY THUOC VAO ID_PROVINCE
     * @return NAME_HOSPITAL ID_HOS CO THE TRUNG NHAU TUY THUOC VAO ID_PROVINCE
     */
    @Length(max = 300)
    @Column(name = "NAME_HOSPITAL")
    @Config(key = "N_HOSPITAL.NAME_HOSPITAL")
    public java.lang.String getNAME_HOSPITAL() {
        return NAME_HOSPITAL;
    }
    /**
     * 設定ID_HOS CO THE TRUNG NHAU TUY THUOC VAO ID_PROVINCE
     * @param NAME_HOSPITAL ID_HOS CO THE TRUNG NHAU TUY THUOC VAO ID_PROVINCE
     */
    public void setNAME_HOSPITAL(java.lang.String NAME_HOSPITAL) {
        this.NAME_HOSPITAL = NAME_HOSPITAL;
    }
    /**
     * @return ADDRESS 
     */
    @Length(max = 300)
    @Column(name = "ADDRESS")
    @Config(key = "N_HOSPITAL.ADDRESS")
    public java.lang.String getADDRESS() {
        return ADDRESS;
    }
    /**
     * @param ADDRESS 
     */
    public void setADDRESS(java.lang.String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 150)
    @Column(name = "NOTE")
    @Config(key = "N_HOSPITAL.NOTE")
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
     * 取得MA TINH, THANH
     * @return ID_PROVINCE MA TINH, THANH
     */
    @Id
    @NotBlank
    @Column(name = "ID_PROVINCE")
    @Config(key = "N_HOSPITAL.ID_PROVINCE")
    public java.lang.String getID_PROVINCE() {
        return ID_PROVINCE;
    }
    /**
     * 設定MA TINH, THANH
     * @param ID_PROVINCE MA TINH, THANH
     */
    public void setID_PROVINCE(java.lang.String ID_PROVINCE) {
        this.ID_PROVINCE = ID_PROVINCE;
    }
}
