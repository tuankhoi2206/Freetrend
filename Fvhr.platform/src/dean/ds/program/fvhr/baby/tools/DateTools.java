package ds.program.fvhr.baby.tools;

/**
 * author : baby
 */

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTools {

	public DateTools(){};
	
	public static int subtractDate(Calendar cal1, Calendar cal2) {
		int year1 = 0, year2 = 0;
		int dayofyear1 = 0, dayofyear2 = 0;
		int temp1=0,temp2=0;
		year1 = cal1.get(Calendar.YEAR);
		year2 = cal2.get(Calendar.YEAR);
		dayofyear1 = cal1.get(Calendar.DAY_OF_YEAR);
		dayofyear2 = cal2.get(Calendar.DAY_OF_YEAR);
		for(int i =1; i<= year1-year2;i++)
		{
		temp1 += (year2+i)%4==0?(year2+i)%100==0?(year2+i)%400==0?366:365:366:365;
		}
		temp2 = temp1 - dayofyear2+dayofyear1;
		
		return temp2;
	}
	
	public static Date ConvertCalendarToDate(Calendar cal1)
	{
		Date temp = cal1.getTime();
		return temp;
	}
	public static Calendar ConvertDateToCalendar(Date date1)
	{
		Calendar temp = Calendar.getInstance(Locale.ENGLISH);
		temp.setTime(date1);
		return temp;
	}
	
	public static int subtractBetweenDates(Date date1, Date date2)
	{
		Calendar cal1 = ConvertDateToCalendar(date1);
		Calendar cal2 = ConvertDateToCalendar(date2);
		cal1.setTime(date1);
		cal2.setTime(date2);
		int year1 = 0, year2 = 0;
		int dayofyear1 = 0, dayofyear2 = 0;
		int temp1=0,temp2=0;
		year1 = cal1.get(Calendar.YEAR);
		year2 = cal2.get(Calendar.YEAR);
		dayofyear1 = cal1.get(Calendar.DAY_OF_YEAR);
		dayofyear2 = cal2.get(Calendar.DAY_OF_YEAR);
		for(int i =1; i<= year1-year2;i++)
		{
		temp1 += (year2+i)%4==0?(year2+i)%100==0?(year2+i)%400==0?366:365:366:365;
		}
		temp2 = temp1 - dayofyear2+dayofyear1;
		
		return temp2;
	}
	public static Calendar AddOrMinusDateByCalendar(Calendar cal,int numDate)
	{
		cal.add(Calendar.DATE, numDate);
		return cal;

	}
	public static Calendar AddOrMinusDateByCalendar(Date date, int numDate)
	{
		Calendar temp = ConvertDateToCalendar(date);
		temp.add(Calendar.DATE, numDate);
		return temp;
	}
	public static Date AddOrMinusDateByDate(Calendar cal, int numDate)
	{
		cal.add(Calendar.DATE, numDate);
		Date temp = ConvertCalendarToDate(cal);
		return temp;
	}
	public static Date AddOrMinusDateByDate(Date date, int numDate)
	{
		Calendar cal = ConvertDateToCalendar(date);
		cal.add(Calendar.DATE, numDate);
		Date temp = ConvertCalendarToDate(cal);
		return temp;
	}
	
	public static boolean checkSunday(Date date)
	{
		Calendar cal = ConvertDateToCalendar(date);
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
			return true;
		return false;
	}
	public static boolean checkSunday(Calendar cal)
	{
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
			return true;
		return false;
	}
	public static Calendar addTime(Date date, int hour, int minutes,int second)
	{
		Calendar cal = ConvertDateToCalendar(date);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minutes);
		cal.set(Calendar.SECOND, second);
		
		return cal;
	}
}
