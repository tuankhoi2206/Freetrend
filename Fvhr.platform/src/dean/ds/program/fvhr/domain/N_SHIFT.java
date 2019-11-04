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
@Table(name = "N_SHIFT")
public class N_SHIFT {
    private java.lang.String ID_SHIFT;
    private java.lang.String NAME_SHIFT;
    private java.lang.String TIME_IN;
    private java.lang.String TIME_OUT;
    private java.lang.String NOTE;
    private java.lang.String STABLE;
    /**
     * @return ID_SHIFT 
     */
    @Id
    @NotBlank
    @Length(max = 5)
    @Column(name = "ID_SHIFT")
    @Config(key = "N_SHIFT.ID_SHIFT")
    public java.lang.String getID_SHIFT() {
        return ID_SHIFT;
    }
    /**
     * @param ID_SHIFT 
     */
    public void setID_SHIFT(java.lang.String ID_SHIFT) {
        this.ID_SHIFT = ID_SHIFT;
    }
    /**
     * @return NAME_SHIFT 
     */
    @Length(max = 100)
    @Column(name = "NAME_SHIFT")
    @Config(key = "N_SHIFT.NAME_SHIFT")
    public java.lang.String getNAME_SHIFT() {
        return NAME_SHIFT;
    }
    /**
     * @param NAME_SHIFT 
     */
    public void setNAME_SHIFT(java.lang.String NAME_SHIFT) {
        this.NAME_SHIFT = NAME_SHIFT;
    }
    /**
     * @return TIME_IN 
     */
    @Length(max = 5)
    @Column(name = "TIME_IN")
    @Config(key = "N_SHIFT.TIME_IN")
    public java.lang.String getTIME_IN() {
        return TIME_IN;
    }
    /**
     * @param TIME_IN 
     */
    public void setTIME_IN(java.lang.String TIME_IN) {
        this.TIME_IN = TIME_IN;
    }
    /**
     * @return TIME_OUT 
     */
    @Length(max = 5)
    @Column(name = "TIME_OUT")
    @Config(key = "N_SHIFT.TIME_OUT")
    public java.lang.String getTIME_OUT() {
        return TIME_OUT;
    }
    /**
     * @param TIME_OUT 
     */
    public void setTIME_OUT(java.lang.String TIME_OUT) {
        this.TIME_OUT = TIME_OUT;
    }
    /**
     * @return NOTE 
     */
    @Length(max = 50)
    @Column(name = "NOTE")
    @Config(key = "N_SHIFT.NOTE")
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
     * @return STABLE 
     */
    @Length(max = 1)
    @Column(name = "STABLE")
    @Config(key = "N_SHIFT.STABLE")
    public java.lang.String getSTABLE() {
        return STABLE;
    }
    /**
     * @param STABLE 
     */
    public void setSTABLE(java.lang.String STABLE) {
        this.STABLE = STABLE;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof N_SHIFT){
    		N_SHIFT other = (N_SHIFT) obj;
    		return new EqualsBuilder().append(this.ID_SHIFT, other.ID_SHIFT).isEquals();
    	}
    	return false;
    }
    
    @Override
    public int hashCode() {
    	return new HashCodeBuilder().append(this.ID_SHIFT).toHashCode();
    }
}
