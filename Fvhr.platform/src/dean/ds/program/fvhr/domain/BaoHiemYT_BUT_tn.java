package ds.program.fvhr.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import ds.program.fvhr.son.ui.ObjUtility;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;


@Entity
@Table(name="N_BHYT_BUT")
@DiscriminatorValue("TN")
public class BaoHiemYT_BUT_tn extends BaoHiemYT_BUT{

	@Transient
	private double money_add_more = 0;
	
	public BaoHiemYT_BUT_tn() {
		// TODO Auto-generated constructor stub
	}

	public BaoHiemYT_BUT_tn(Date date, double du_tru,String up_user) {
		super(0, date, du_tru,up_user);
	}
		
	/**
	 * Số tiền BHTN tháng trước dự trù
	 * @return
	 */
	public Double getMoney_of_pre_Month(){
		double ret = 0;
		// constrains that the Day of D_Apply column alway begin "01" 
		Date date_tmp = ObjUtility.MONTH_PRE("01", getD_apply());
		IGenericDAO<BaoHiemYT_BUT_tn, String> dao  = Application.getApp().getDao(BaoHiemYT_BUT_tn.class);
		List oYT = dao.find(1," from BaoHiemYT_BUT_tn a where a.d_apply = ?  ",new Object[]{date_tmp });
		Iterator it = oYT.iterator();
		BaoHiemYT_BUT_tn oPre = null ;
		while(it.hasNext()){
			oPre  = (BaoHiemYT_BUT_tn) it.next();
		}
		if(oPre == null) return ret;
		ret = oPre.getDu_tru();
		return ret ;
	}
	
	public Double getMoney_from_System(){
		if(get_id()!=null && !get_id().equals("")){
			return this.getBaoHiemYT_SYS().getSo_tien();
		}
		return (double) 0;
	}
	
	/**
	 *  Số tiền thiếu BHTN phải nộp thêm  = số tiền thực  nộp(SYS.TN) - số tiền dự trù tháng trước(BUT.du_tru(pre_Month))
	 * @return
	 */
	public Double getMoney_add_more() {
		if(money_add_more ==0)	{
			money_add_more = getMoney_from_System() - getMoney_of_pre_Month();
		}
		return money_add_more;
	}
	
	public Double getMoney_total() {
		return getMoney_add_more() + getDu_tru();
	}
}
