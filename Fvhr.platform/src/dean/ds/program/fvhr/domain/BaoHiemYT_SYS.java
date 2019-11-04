package ds.program.fvhr.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="N_BHYT_SYS")
public class BaoHiemYT_SYS {
	@Id
	@Column(name="ID_PKEY")
	private String _id;
	
	private int type;
	private String month;
	private Double so_tien;
	/**
	 * type = 0:BHTN 
	 * 		= 1:BHXH
	 * 		= 2:BHYT
	 *  date : dinh dang java.util.Date
	 *  so_tien : tong so tien cua ky trinh
	 */
	public BaoHiemYT_SYS(int type,Date date,Double so_tien) {
		this.type = type;
		SimpleDateFormat sf = new SimpleDateFormat("MMyyyy");
		this.month = sf.format(date);
		this.so_tien = so_tien;
		 _id  = month+type;
	}
	
	public BaoHiemYT_SYS() {
		// TODO Auto-generated constructor stub
	}
	
	public String get_id() {
		return _id  ;
	}
	public int getType() {
		return type;
	}
	public String getMonth() {
		return month;
	}
	public Double getSo_tien() {
		return so_tien;
	}
	
	//Minh
	public void set_id(String id_pkey) {		
		this._id = id_pkey;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void setSo_tien(Double so_tien) {
		this.so_tien = so_tien;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}	
}
