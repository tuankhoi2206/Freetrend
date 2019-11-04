package ds.program.fvhr.domain;

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
@Entity
@Table(name = "N_LIST_EMPSN")
public class N_LIST_EMPSN {
    private java.lang.String EMPSN;
    private java.lang.String JOB;
    /**
     * @return EMPSN 
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_LIST_EMPSN.EMPSN")
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
     * @return JOB 
     */
    @Length(max = 100)
    @Column(name = "JOB")
    @Config(key = "N_LIST_EMPSN.JOB")
    public java.lang.String getJOB() {
        return JOB;
    }
    /**
     * @param JOB 
     */
    public void setJOB(java.lang.String JOB) {
        this.JOB = JOB;
    }
}
