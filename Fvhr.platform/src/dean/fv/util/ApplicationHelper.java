package fv.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dsc.echo2app.Application;
/**
 * Application Helper
 * @author Hieu
 * @since 17/09/2011
 */
public class ApplicationHelper {
	public static HRUtils getHRUtils(){
		return (HRUtils) Application.getSpringContext().getBean("hrUtils");
	}
	
	public static RightsHolder getRightsHolder(){
		return (RightsHolder) Application.getSpringContext().getBean("rightHolder");
	}
	
	public static String getVftUserId(){
		return getRightsHolder().getRights(Application.getApp().getLoginInfo().getUserID()).getVftUserId();
	}
	
	public static String getMonthString(){
		Calendar cal = Calendar.getInstance();
		return String.valueOf(cal.get(Calendar.MONTH)+1);
	}
	
	public static String getYearString(){
		Calendar cal = Calendar.getInstance();
		return String.valueOf(cal.get(Calendar.YEAR));
	}
	
	public static List<String> getRightList(){
		return getRightsHolder().getRights(Application.getApp().getLoginInfo().getUserID()).getRightList();
	}

	public static Connection getConnection(boolean console){
		if (console){
			ApplicationContext ctx = new ClassPathXmlApplicationContext("/conf/*.xml");
			DataSource ds = (DataSource) ctx.getBean("dataSource_VFT");
			Connection con = null;
			try {
				con = ds.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return con;
		}else{
			return Application.getApp().getConnection();
		}
	}
}
