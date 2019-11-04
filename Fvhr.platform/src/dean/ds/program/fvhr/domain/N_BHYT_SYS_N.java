package ds.program.fvhr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import ds.program.fvhr.domain.pk.N_BHYT_SYS_NPk;
import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

@IdClass(N_BHYT_SYS_NPk.class)
@Entity
@Table(name = "N_BHYT_SYS_N")
public class N_BHYT_SYS_N {
	private String ID_PKEY;
	private int TYPE;
	private long SO_TIEN;
	
	@Id
    @NotBlank
    @Column(name = "TYPE")
    @Config(key = "N_BHYT_SYS_N.TYPE")
	public int getTYPE() {
		return TYPE;
	}
	public void setTYPE(int tYPE) {
		TYPE = tYPE;
	}
	
    @Column(name = "SO_TIEN")
    @Config(key = "N_BHYT_SYS_N.SO_TIEN")
	public long getSO_TIEN() {
		return SO_TIEN;
	}
	public void setSO_TIEN(long sO_TIEN) {
		SO_TIEN = sO_TIEN;
	}
	
	@Id
    @NotBlank
    @Column(name = "ID_PKEY")
    @Config(key = "N_BHYT_SYS_N.ID_PKEY")
	public String getID_PKEY() {
		return ID_PKEY;
	}
	public void setID_PKEY(String iD_PKEY) {
		ID_PKEY = iD_PKEY;
	}
	
}
