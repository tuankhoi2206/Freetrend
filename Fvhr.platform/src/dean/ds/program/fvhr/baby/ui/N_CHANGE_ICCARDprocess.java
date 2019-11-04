package ds.program.fvhr.baby.ui;

import java.util.Date;

import ds.program.fvhr.domain.N_CHANGE_ICCARD;
import ds.program.fvhr.domain.N_IC_CARD;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;

public class N_CHANGE_ICCARDprocess {
	public static int checkRule(String empsn,Date date_change,String empcn_new)
	{
		String sql="";
		IGenericDAO<N_CHANGE_ICCARD, String> dao = Application.getApp().getDao(N_CHANGE_ICCARD.class);
		IGenericDAO<N_IC_CARD, String> daoIC = Application.getApp().getDao(N_IC_CARD.class);
		System.out.println(date_change);
		
		 sql = "select t from N_CHANGE_ICCARD t where t.EMPSN = ? " +
			" and (select max(t.DATE_CHANGE) from N_CHANGE_ICCARD t where t.EMPSN = ?) >= ?)";
		if (dao.find(1, sql, empsn,empsn,date_change).size()>0 )
			return 1;
		
		 sql = "select t from N_CHANGE_ICCARD t where t.EMPCN_NEW = ? and t.DATE_CHANGE = ?";
		if(dao.find(1, sql, empcn_new,date_change).size()>0)
			return 2;
		
		sql = "select t from N_IC_CARD t where t.IC_NO = ? and t.USE_STATUS in ('4','3','2','1')";
		if(daoIC.findById(empcn_new) == null)
			return 3;
		if(daoIC.find(1, sql, empcn_new).size()>0)
			return 4;
		return 0;
	}
	
	
}
