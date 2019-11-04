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

import ds.program.fvhr.son.ui.ObjUtility;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;


@Entity
@Table(name="N_BHYT_BUT")
@DiscriminatorValue("YT")
public class BaoHiemYT_BUT_yt extends BaoHiemYT_BUT{
	
	private Double no_1;
	private Double no_2;
	private Double no_3;
	private Double no_4;
	private Double no_5;
	public BaoHiemYT_BUT_yt() {
		// TODO Auto-generated constructor stub
	}
	public BaoHiemYT_BUT_yt(Date date, double du_tru,double n1,double n2,double n3,double n4,double n5,String up_user) {
		super(2, date, du_tru,up_user);
		no_1 = n1;
		no_2 = n2;
		no_3 = n3;
		no_4 = n4;
		no_5 = n5;
	}
	/**
	 * BHYT giữ lại	
	 * @return
	 */
	public Double getNo_1() {
		return no_1;
	}

	/**
	 * Không đủ công
	 * @return
	 */
	public Double getNo_2() {
		return no_2;
	}
	/**
	 * KT trừ được
	 * @return
	 */
	public Double getNo_3() {
		return no_3;
	}
	
	/**
	 * Cty trả tiền
	 * @return
	 */
	public Double getNo_4() {
		return no_4;
	}
	/**
	 * Thu tiền mặt
	 * @return
	 */
	public Double getNo_5() {
		return no_5;
	}
	
	public Double getMoney_of_pre_Month(){
		double ret = 0;
		// constrains that the Day of D_Apply column alway begin "01" 
		Date date_tmp = ObjUtility.MONTH_PRE("01", getD_apply());
		IGenericDAO<BaoHiemYT_BUT_yt, String> dao  = Application.getApp().getDao(BaoHiemYT_BUT_yt.class);
//		List oYT = dao.findByQuery("from BaoHiemYT_BUT_tn_formular a where a.d_apply = ?  ",new Object[]{date_tmp });
		List oYT = dao.find(1," from BaoHiemYT_BUT_yt a where a.d_apply = ?  ",new Object[]{date_tmp });
		Iterator it = oYT.iterator();
		BaoHiemYT_BUT_yt oPre = null ;
		while(it.hasNext()){
			oPre  = (BaoHiemYT_BUT_yt) it.next();
		}
		if(oPre == null) return ret;
		ret = oPre.getDu_tru();
		return ret ;
	}
		
}
