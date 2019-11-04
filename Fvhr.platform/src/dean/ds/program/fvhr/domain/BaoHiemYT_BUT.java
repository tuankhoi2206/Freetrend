package ds.program.fvhr.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;

@Entity
@Table(name="N_BHYT_BUT")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",
					discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="UK")

public class BaoHiemYT_BUT {
	@Id
	@Column(name="ID_PKEY")
	private String _id;
	private Double du_tru;
	private Date d_apply;
	private Date up_date;
	private String up_user;
	
	@Transient
	private BaoHiemYT_SYS obj_sys;
	
	public BaoHiemYT_BUT() {
	}
	
	public BaoHiemYT_BUT(int type,Date date_apply,Double du_tru,String up_user) {
		SimpleDateFormat sf = new SimpleDateFormat();
		sf.applyPattern("MMyyyy");
		this._id = sf.format(date_apply) + type;
		this.du_tru = du_tru;
		this.d_apply = date_apply;
		this.up_user = up_user;
		up_date = new Date();
	}
	
	public String get_id() {
		return _id;
	}
	public Double getDu_tru() {
		return du_tru;
	}
	public Date getD_apply(){
		return d_apply;
	}
	
	public String getUp_user(){
		return up_user;
	}
	public Date getUp_date(){
		return up_date;
	}
	
	public BaoHiemYT_SYS getBaoHiemYT_SYS(){
		if(get_id() != null && !get_id().equals("")){
			IGenericDAO<BaoHiemYT_SYS, String> dao = Application.getApp().getDao(BaoHiemYT_SYS.class);
			obj_sys = dao.findById(get_id());
		}
		if(obj_sys == null){
			obj_sys = new BaoHiemYT_SYS(-1, new Date(), (double) 0);
		}
		return obj_sys;
	}
	
}
