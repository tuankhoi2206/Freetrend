package ds.program.fvhr.domain.pk;

import java.io.Serializable;
import javax.persistence.Column;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import dsc.util.hibernate.validator.NotBlank;

public class N_BHYT_SYS_NPk implements Serializable{
	private String ID_PKEY;
	private int TYPE;
	
	public N_BHYT_SYS_NPk() {
		super();
		// TODO Auto-generated constructor stub
	}

	public N_BHYT_SYS_NPk(String iD_PKEY, int tYPE) {
		super();
		ID_PKEY = iD_PKEY;
		TYPE = tYPE;
	}
	
	@NotBlank
    @Column(name = "ID_PKEY", length = 8)
    public java.lang.String getID_PKEY() {
        return ID_PKEY;
    }
    public void setID_PKEY(java.lang.String ID_PKEY) {
        this.ID_PKEY = ID_PKEY;
    }
    
    @NotBlank
    @Column(name = "TYPE")
    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }
    
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N_BHYT_SYS_NPk))
            return false;
        N_BHYT_SYS_NPk castOther = (N_BHYT_SYS_NPk) other;
        return new EqualsBuilder().append(ID_PKEY, castOther.ID_PKEY).append(TYPE, castOther.TYPE).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ID_PKEY).append(TYPE).toHashCode();
    }

}
