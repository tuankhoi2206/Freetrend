package ds.program.fvhr.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import org.hibernate.validator.Length;
import org.hibernate.validator.Min;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@Entity
@Table(name = "N_TRAINING_DETAILS")
public class N_TRAINING_DETAILS {
    private java.lang.String ID_TRAINING;
    private java.lang.String EMPSN;
    private java.lang.String COURSE_ID;
    private java.lang.String SUBJECT_ID;
    private java.util.Date BDATE;
    private java.util.Date EDATE;
    private java.lang.String TRAINING_TYPE;
    private java.lang.String GUIDE_EMPSN;
    private java.util.Date EXAM_DATE;
    private java.math.BigDecimal MARK;
    private java.lang.String RANK;
    private java.math.BigDecimal EXAM_TIME;
    private java.lang.String NOTE;
    /**
     * @return ID_TRAINING 
     */
    @Id
    @NotBlank
    @Column(name = "ID_TRAINING")
    @Config(key = "N_TRAINING_DETAILS.ID_TRAINING")
    public java.lang.String getID_TRAINING() {
        return ID_TRAINING;
    }
    /**
     * @param ID_TRAINING 
     */
    public void setID_TRAINING(java.lang.String ID_TRAINING) {
        this.ID_TRAINING = ID_TRAINING;
    }
    /**
     * @return EMPSN 
     */
    @NotBlank
    @Length(max = 8)
    @Column(name = "EMPSN")
    @Config(key = "N_TRAINING_DETAILS.EMPSN")
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
     * @return COURSE_ID 
     */
    @NotBlank
    @Length(max = 5)
    @Column(name = "COURSE_ID")
    @Config(key = "N_TRAINING_DETAILS.COURSE_ID")
    public java.lang.String getCOURSE_ID() {
        return COURSE_ID;
    }
    /**
     * @param COURSE_ID 
     */
    public void setCOURSE_ID(java.lang.String COURSE_ID) {
        this.COURSE_ID = COURSE_ID;
    }
    /**
     * @return SUBJECT_ID 
     */
    @NotBlank
    @Length(max = 6)
    @Column(name = "SUBJECT_ID")
    @Config(key = "N_TRAINING_DETAILS.SUBJECT_ID")
    public java.lang.String getSUBJECT_ID() {
        return SUBJECT_ID;
    }
    /**
     * @param SUBJECT_ID 
     */
    public void setSUBJECT_ID(java.lang.String SUBJECT_ID) {
        this.SUBJECT_ID = SUBJECT_ID;
    }
    /**
     * @return BDATE 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "BDATE")
    @Config(key = "N_TRAINING_DETAILS.BDATE")
    public java.util.Date getBDATE() {
        return BDATE;
    }
    /**
     * @param BDATE 
     */
    public void setBDATE(java.util.Date BDATE) {
        this.BDATE = BDATE;
    }
    /**
     * @return EDATE 
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "EDATE")
    @Config(key = "N_TRAINING_DETAILS.EDATE")
    public java.util.Date getEDATE() {
        return EDATE;
    }
    /**
     * @param EDATE 
     */
    public void setEDATE(java.util.Date EDATE) {
        this.EDATE = EDATE;
    }
    /**
     * @return TRAINING_TYPE 
     */
    @NotBlank
    @Length(max = 20)
    @Column(name = "TRAINING_TYPE")
    @Config(key = "N_TRAINING_DETAILS.TRAINING_TYPE")
    public java.lang.String getTRAINING_TYPE() {
        return TRAINING_TYPE;
    }
    /**
     * @param TRAINING_TYPE 
     */
    public void setTRAINING_TYPE(java.lang.String TRAINING_TYPE) {
        this.TRAINING_TYPE = TRAINING_TYPE;
    }
    /**
     * @return GUIDE_EMPSN 
     */
    @NotBlank
    @Length(max = 8)
    @Column(name = "GUIDE_EMPSN")
    @Config(key = "N_TRAINING_DETAILS.GUIDE_EMPSN")
    public java.lang.String getGUIDE_EMPSN() {
        return GUIDE_EMPSN;
    }
    /**
     * @param GUIDE_EMPSN 
     */
    public void setGUIDE_EMPSN(java.lang.String GUIDE_EMPSN) {
        this.GUIDE_EMPSN = GUIDE_EMPSN;
    }
    /**
     * @return EXAM_DATE 
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "EXAM_DATE")
    @Config(key = "N_TRAINING_DETAILS.EXAM_DATE")
    public java.util.Date getEXAM_DATE() {
        return EXAM_DATE;
    }
    /**
     * @param EXAM_DATE 
     */
    public void setEXAM_DATE(java.util.Date EXAM_DATE) {
        this.EXAM_DATE = EXAM_DATE;
    }
    /**
     * @return MARK 
     */
    @Column(name = "MARK")
    @Config(key = "N_TRAINING_DETAILS.MARK")
    public java.math.BigDecimal getMARK() {
        return MARK;
    }
    /**
     * @param MARK 
     */
    public void setMARK(java.math.BigDecimal MARK) {
        this.MARK = MARK;
    }
    /**
     * @return RANK 
     */
    @Length(max = 15)
    @Column(name = "RANK")
    @Config(key = "N_TRAINING_DETAILS.RANK")
    public java.lang.String getRANK() {
        return RANK;
    }
    /**
     * @param RANK 
     */
    public void setRANK(java.lang.String RANK) {
        this.RANK = RANK;
    }
    /**
     * @return EXAM_TIME 
     */
    @Min(value=1)
    @Column(name = "EXAM_TIME")
    @Config(key = "N_TRAINING_DETAILS.EXAM_TIME")
    public java.math.BigDecimal getEXAM_TIME() {
        return EXAM_TIME;
    }
    /**
     * @param EXAM_TIME 
     */
    public void setEXAM_TIME(java.math.BigDecimal EXAM_TIME) {
        this.EXAM_TIME = EXAM_TIME;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 200)
    @Column(name = "NOTE")
    @Config(key = "N_TRAINING_DETAILS.NOTE")
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
