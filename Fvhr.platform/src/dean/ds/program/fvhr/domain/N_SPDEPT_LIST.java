package ds.program.fvhr.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Length;

import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

@Entity
@Table(name="N_SPDEPT_LIST")
public class N_SPDEPT_LIST {
	private java.lang.String ID_SPDEPT;
	private java.lang.String NAME_SPDEPT;
	private java.lang.String NOTE;
	private java.lang.String ROT;
	private java.lang.String AOT;
	private java.lang.String FOT;
	
    @Id
    @NotBlank
    @Column(name = "ID_SPDEPT")
    @Config(key = "N_SPDEPT_LIST.ID_SPDEPT")
	public java.lang.String getID_SPDEPT() {
		return ID_SPDEPT;
	}
	public void setID_SPDEPT(java.lang.String iD_SPDEPT) {
		ID_SPDEPT = iD_SPDEPT;
	}
	
	@Length(max=30)
	@Column(name="NAME_SPDEPT")
	@Config(key="N_SPDEPT_LIST.NAME_SPDEPT")
	public java.lang.String getNAME_SPDEPT() {
		return NAME_SPDEPT;
	}
	public void setNAME_SPDEPT(java.lang.String nAME_SPDEPT) {
		NAME_SPDEPT = nAME_SPDEPT;
	}
	
	@Length(max=100)
	@Column(name="NOTE")
	@Config(key="N_SPDEPT_LIST.NOTE")
	public java.lang.String getNOTE() {
		return NOTE;
	}
	public void setNOTE(java.lang.String nOTE) {
		NOTE = nOTE;
	}
	
	@Length(max=1)
	@Column(name="ROT")
	@Config(key="N_SPDEPT_LIST.ROT")
	public java.lang.String getROT() {
		return ROT;
	}
	public void setROT(java.lang.String rOT) {
		ROT = rOT;
	}
	
	
	@Length(max=1)
	@Column(name="AOT")
	@Config(key="N_SPDEPT_LIST.AOT")
	public java.lang.String getAOT() {
		return AOT;
	}
	public void setAOT(java.lang.String aOT) {
		AOT = aOT;
	}
	
	@Length(max=1)
	@Column(name="FOT")
	@Config(key="N_SPDEPT_LIST.FOT")
	public java.lang.String getFOT() {
		return FOT;
	}
	public void setFOT(java.lang.String fOT) {
		FOT = fOT;
	}

}
