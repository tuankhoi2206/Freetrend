package ds.program.fvhr.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import org.hibernate.validator.Length;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@Entity
@Table(name = "N_ACTION_DAILY")
public class N_ACTION_DAILY {
    private java.lang.String IDUSER;
    private java.util.Date ACTION_DATETIME;
    private java.lang.String TABLENAME;
    private java.lang.String ACTIONNAME;
    private java.lang.String NOTE;
    private java.lang.String EMPSN;
    private java.lang.String ID_ACTION;
    /**
     * @return IDUSER 
     */
    @Length(max = 5)
    @Column(name = "IDUSER")
    @Config(key = "N_ACTION_DAILY.IDUSER")
    public java.lang.String getIDUSER() {
        return IDUSER;
    }
    /**
     * @param IDUSER 
     */
    public void setIDUSER(java.lang.String IDUSER) {
        this.IDUSER = IDUSER;
    }
    /**
     * @return ACTION_DATETIME 
     */
    @Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ACTION_DATETIME")
    @Config(key = "N_ACTION_DAILY.ACTION_DATETIME")
    public java.util.Date getACTION_DATETIME() {
        return ACTION_DATETIME;
    }
    /**
     * @param ACTION_DATETIME 
     */
    public void setACTION_DATETIME(java.util.Date ACTION_DATETIME) {
        this.ACTION_DATETIME = ACTION_DATETIME;
    }
    /**
     * @return TABLENAME 
     */
    @Length(max = 100)
    @Column(name = "TABLENAME")
    @Config(key = "N_ACTION_DAILY.TABLENAME")
    public java.lang.String getTABLENAME() {
        return TABLENAME;
    }
    /**
     * @param TABLENAME 
     */
    public void setTABLENAME(java.lang.String TABLENAME) {
        this.TABLENAME = TABLENAME;
    }
    /**
     * @return ACTIONNAME 
     */
    @Length(max = 100)
    @Column(name = "ACTIONNAME")
    @Config(key = "N_ACTION_DAILY.ACTIONNAME")
    public java.lang.String getACTIONNAME() {
        return ACTIONNAME;
    }
    /**
     * @param ACTIONNAME 
     */
    public void setACTIONNAME(java.lang.String ACTIONNAME) {
        this.ACTIONNAME = ACTIONNAME;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 4000)
    @Column(name = "NOTE")
    @Config(key = "N_ACTION_DAILY.NOTE")
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
     * @return EMPSN 
     */
    @Length(max = 50)
    @Column(name = "EMPSN")
    @Config(key = "N_ACTION_DAILY.EMPSN")
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
     * @return ID_ACTION 
     */
    @Length(max = 20)
    @Column(name = "ID_ACTION")
    @Config(key = "N_ACTION_DAILY.ID_ACTION")
    public java.lang.String getID_ACTION() {
        return ID_ACTION;
    }
    /**
     * @param ID_ACTION 
     */
    public void setID_ACTION(java.lang.String ID_ACTION) {
        this.ID_ACTION = ID_ACTION;
    }
}
