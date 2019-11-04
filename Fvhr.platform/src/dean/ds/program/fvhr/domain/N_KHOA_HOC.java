package ds.program.fvhr.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.Length;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* 
**/
@Entity
@Table(name = "N_KHOA_HOC")
public class N_KHOA_HOC {
    private java.lang.String MA_KHOA;
    private java.lang.String TEN_KHOA;
    private java.lang.String DEPSN;	//DV PHU TRACH
    private java.lang.String NOTE;
    private java.lang.String TRAINING_TYPE;
    /**
     * @return MA_KHOA 
     */
    @Id
    @NotBlank
    @Column(name = "MA_KHOA")
    @Config(key = "N_KHOA_HOC.MA_KHOA")
    public java.lang.String getMA_KHOA() {
        return MA_KHOA;
    }
    /**
     * @param MA_KHOA 
     */
    public void setMA_KHOA(java.lang.String MA_KHOA) {
        this.MA_KHOA = MA_KHOA;
    }
    /**
     * @return TEN_KHOA 
     */
    @Length(max = 200)
    @Column(name = "TEN_KHOA")
    @Config(key = "N_KHOA_HOC.TEN_KHOA")
    public java.lang.String getTEN_KHOA() {
        return TEN_KHOA;
    }
    /**
     * @param TEN_KHOA 
     */
    public void setTEN_KHOA(java.lang.String TEN_KHOA) {
        this.TEN_KHOA = TEN_KHOA;
    }
    /**
     * 取得DV PHU TRACH
     * @return DEPSN DV PHU TRACH
     */
    @Length(max = 10)
    @Column(name = "DEPSN")
    @Config(key = "N_KHOA_HOC.DEPSN")
    public java.lang.String getDEPSN() {
        return DEPSN;
    }
    /**
     * 設定DV PHU TRACH
     * @param DEPSN DV PHU TRACH
     */
    public void setDEPSN(java.lang.String DEPSN) {
        this.DEPSN = DEPSN;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 200)
    @Column(name = "NOTE")
    @Config(key = "N_KHOA_HOC.NOTE")
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
     * @return TRAINING_TYPE 
     */
    @Length(max = 15)
    @Column(name = "TRAINING_TYPE")
    @Config(key = "N_KHOA_HOC.TRAINING_TYPE")
    public java.lang.String getTRAINING_TYPE() {
        return TRAINING_TYPE;
    }
    /**
     * @param TRAINING_TYPE 
     */
    public void setTRAINING_TYPE(java.lang.String TRAINING_TYPE) {
        this.TRAINING_TYPE = TRAINING_TYPE;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof N_KHOA_HOC){
    		N_KHOA_HOC other = (N_KHOA_HOC) obj;
    		return new EqualsBuilder().append(this.MA_KHOA, other.getMA_KHOA()).isEquals();
    	}else return false;
    }
    
    @Override
    public int hashCode() {
    	return new HashCodeBuilder().append(this.MA_KHOA).hashCode();
    }
}
