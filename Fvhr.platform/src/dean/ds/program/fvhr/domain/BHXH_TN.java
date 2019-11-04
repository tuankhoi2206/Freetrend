package ds.program.fvhr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="n_bhxh_tn")
public class BHXH_TN {
	private String id_bhxh_tn;
	private int id_dk;
	private int so_nguoi;
	private long tong_luong;
	
	@Id
	public String getId_bhxh_tn() {
		return id_bhxh_tn;
	}
	public void setId_bhxh_tn(String id_bhxh_tn) {
		this.id_bhxh_tn = id_bhxh_tn;
	}
	
	@Id
	public int getId_dk() {
		return id_dk;
	}
	public void setId_dk(int id_dk) {
		this.id_dk = id_dk;
	}
	
	@Column
	public int getSo_nguoi() {
		return so_nguoi;
	}
	public void setSo_nguoi(int so_nguoi) {
		this.so_nguoi = so_nguoi;
	}
	
	@Column
	public long getTong_luong() {
		return tong_luong;
	}
	public void setTong_luong(long tong_luong) {
		this.tong_luong = tong_luong;
	}
}
