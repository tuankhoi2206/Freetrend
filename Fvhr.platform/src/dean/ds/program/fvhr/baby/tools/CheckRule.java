package ds.program.fvhr.baby.tools;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.hibernate.mapping.Array;

import ds.program.fvhr.domain.*;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import fv.util.JdbcDAO;

public class CheckRule {
	public static boolean checkRuleManager(String Empsn)
	{
		String sql = "select t from N_EMPLOYEE t where t.EMPSN = ? and t.USER_MANAGE_ID in " +
				" (select a.MA_QL from N_USER_LIMIT a,DSPB02 e where e.PB_USERNO = a.MA_USER and e.PB_USERID = ?)";
		IGenericDAO<N_EMPLOYEE, String>daoEmpsn = Application.getApp().getDao(N_EMPLOYEE.class);
		String userid = Application.getApp().getLoginInfo().getUserID();
		List<N_EMPLOYEE>listEmp = daoEmpsn.find(20,sql,Empsn,userid);
		
		if(listEmp.size()<=0)
			return false;
		return true;
	}
	
	public static String listManager()
	{
		JdbcDAO dao = new JdbcDAO();
		List<String> listmanager = new ArrayList<String>();
		String userID = Application.getApp().getLoginInfo().getUserID();
		String sql = "select t.MA_QL from N_USER_LIMIT t,DSPB02 e where e.PB_USERNO = t.MA_USER and e.PB_USERID = ?";
		listmanager = dao.getJdbcTemplate().queryForList(sql, new Object[]{userID},new String().getClass());
		String temp="";
		for (String string : listmanager) {
			temp+="'"+string+"',";
		}
		temp = StringUtils.removeEnd(temp, ",");
		System.out.println(temp);
		return temp;
	}
	public static boolean checkLockedMonth(String empsn,Date date)
	{
		Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH)+1;
		int year = calendar.get(Calendar.YEAR);
		String month1 = String.valueOf(month).length()>1?String.valueOf(month):"0"+String.valueOf(month);
		String sql = "select t from N_GET_DATA t where t.EMPSN = ? and t.MONTHS = ? and t.YEARS = ?";
		IGenericDAO<N_GET_DATA, String>daoGetdata = Application.getApp().getDao(N_GET_DATA.class);
		N_GET_DATA obj = daoGetdata.findUnique(sql, empsn,month1,String.valueOf(year));
		if(obj==null)
			return true;
		else
/*			if(obj.getLOCKED()!= BigDecimal.valueOf(1L))			
				return false;
		return true;*/			
			if(obj.getLOCKED().equals(BigDecimal.valueOf(1L)))	
				return false;			
			else return true;			
	}

}
