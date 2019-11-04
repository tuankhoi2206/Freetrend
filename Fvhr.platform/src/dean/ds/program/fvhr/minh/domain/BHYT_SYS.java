package ds.program.fvhr.minh.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vft.n_bhyt_sys")
public class BHYT_SYS {
	private String id_pkey;
	private int type;
	private long so_tien;
	private String month;
	
	@Id
	public String getId_pkey() {
		return id_pkey;
	}
	public void setId_pkey(String id_pkey) {
		this.id_pkey = id_pkey;
	}
	
	@Column(name = "TYPE") 
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	@Column(name = "SO_TIEN") 
	public long getSo_tien() {
		return so_tien;
	}
	public void setSo_tien(long so_tien) {
		this.so_tien = so_tien;
	}
	
	@Column (length=6, name = "MONTH") 
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}

}
