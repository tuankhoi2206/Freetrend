package fv.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public DateUtils() {

	}

	/**
	 * 
	 * @param month 1-12
	 * @param year
	 * @return
	 */
	public static Date getFirstDay(int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(year, month - 1, 1,0,0,0);		
		return cal.getTime();
	}
	/**
	 * 
	 * @param month 1-12
	 * @param year
	 * @return
	 */
	public static Date getLastDay(int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_MONTH, cal
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 
	 * @param month 1-12
	 * @param year
	 * @return
	 */
	public static Date getLastDay(String month, String year){
		return getLastDay(Integer.parseInt(month), Integer.parseInt(year));
	}
	
	/**
	 * 
	 * @param month 1-12
	 * @param year
	 * @return
	 */
	public static Date getFirstDay(String month, String year){
		return getFirstDay(Integer.parseInt(month), Integer.parseInt(year));
	}

	/**
	 * 
	 * @param month 1-12
	 * @param year
	 * @return
	 */
	public static Date getDay15(int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(year, month - 1, 15, 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 
	 * @param month 1-12
	 * @param year
	 * @return
	 */
	public static Date getDay16(int month, int year) {
		//Calendar month se chay tu 0-> 11
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(year, month - 1, 16, 0, 0, 0);
		return cal.getTime();
	}

	public static String getFirstDayString(int month, int year) {
		return sdf.format(getFirstDay(month, year));
	}

	public static String getLastDayString(int month, int year) {
		return sdf.format(getLastDay(month, year));
	}

	public static String getDay15String(int month, int year) {
		return sdf.format(getDay15(month, year));
	}

	public static String getDay16String(int month, int year) {
		return sdf.format(getDay16(month, year));
	}

	public static String getThangChayBaoCao(int month, int year) {
		return (month < 10 ? "0" + month : month) + "/" + year;
	}

	public static String getThangChayBCTru1(int month, int year) {
		int m = month - 1;
		int y = year;
		if (m == 0) {
			m = 12;
			y--;
		}
		return (m < 10 ? "0" + m : m) + "/" + y;
	}

	// da kiem tra chon thu 2 hoac thu 5 tren form roi
	public static String[] getThoiGianGiamTuan(Calendar ngayBaoGiam) {
		if (ngayBaoGiam.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			// tru 1 ngay trong thang
			ngayBaoGiam.add(Calendar.DAY_OF_MONTH, -1);
			String denNgay = sdf.format(ngayBaoGiam.getTime());
			// tru them 3 ngay trong thang
			ngayBaoGiam.add(Calendar.DAY_OF_MONTH, -3);
			String tuNgay = sdf.format(ngayBaoGiam.getTime());
			// cong lai 4 ngay trong thang
			ngayBaoGiam.add(Calendar.DAY_OF_MONTH, 4);
			return new String[] { tuNgay, denNgay };
		} else {
			// tru 1 ngay trong thang
			ngayBaoGiam.add(Calendar.DAY_OF_MONTH, -1);
			String denNgay = sdf.format(ngayBaoGiam.getTime());
			// tru them 2 ngay trong thang
			ngayBaoGiam.add(Calendar.DAY_OF_MONTH, -2);
			String tuNgay = sdf.format(ngayBaoGiam.getTime());
			// cong lai 3 ngay trong thang
			ngayBaoGiam.add(Calendar.DAY_OF_MONTH, 3);
			return new String[] { tuNgay, denNgay };
		}
	}

	// Chon thoi gian bao giam tu ngay thuc nghi
	// T2: 07/10/2013 xem lai ham nay
	@SuppressWarnings("deprecation")
	public static String[] getThoiGianGiamByRealOffDate(Date ngayThucNghi) {
		//int ngay = ngayThucNghi.get(Calendar.DAY_OF_WEEK); // bat dau tu 1= CN, vi la stt trong tuan
		int ngay = ngayThucNghi.getDay(); //bat dau tu 0= CN, vi la stt trong tuan
		Date ngayTemp = new Date();
		ngayTemp = ngayThucNghi;
		String tuNgay=null;
		String denNgay=null;
		//Thuc nghi tu T5-> CN se duoc bao giam vao T2 tuan sau
		//Thuc nghi tu T2-> T4 se duoc bao giam vao T5 cua tuan hien tai
		switch (ngay) {
		//CN
		case 0: 
			//T5->CN truoc
			denNgay = sdf.format(ngayTemp);
			ngayTemp.setDate(ngayTemp.getDate()-3);
			tuNgay = sdf.format(ngayTemp);			
			break;
		//T2	
		case 1:
			//T2->T4 nay
			tuNgay = sdf.format(ngayThucNghi);
			ngayTemp.setDate(ngayTemp.getDate()+2);			
			denNgay = sdf.format(ngayTemp);			
			break;
		//T3
		case 2:
			//T2->T4 nay
			ngayTemp.setDate(ngayTemp.getDate()-1);
			tuNgay = sdf.format(ngayTemp);
			ngayTemp.setDate(ngayTemp.getDate()+2);
			denNgay= sdf.format(ngayTemp);						
			break;
		//T4
		case 3:
			//T2->T4 nay
			denNgay = sdf.format(ngayTemp);
			ngayTemp.setDate(ngayTemp.getDate()-2);
			tuNgay = sdf.format(ngayTemp);
			break;
		//T5
		case 4: 
			//T5->CN truoc
			tuNgay= sdf.format(ngayTemp);
			ngayTemp.setDate(ngayTemp.getDate()+3);
			denNgay = sdf.format(ngayTemp);
			break;			
		//T6
		case 5: 
			//T5->CN truoc
			ngayTemp.setDate(ngayTemp.getDate()-1);
			tuNgay = sdf.format(ngayTemp);
			ngayTemp.setDate(ngayTemp.getDate()+3);
			denNgay = sdf.format(ngayTemp);
			break;
		//T7
		case 6: 
			//T5->CN truoc
			ngayTemp.setDate(ngayTemp.getDate()-2);
			tuNgay= sdf.format(ngayTemp);
			ngayTemp.setDate(ngayTemp.getDate()+3);
			denNgay= sdf.format(ngayTemp);
			break;			
		default:
			break;
		}
		return new String[] { tuNgay, denNgay };
	}
	
	public static String getMonth(Calendar cal) {
		int month = cal.get(Calendar.MONTH) + 1;
		return month < 10 ? "0" + month : "" + month;
	}

	public static String getYear(Calendar cal) {
		return "" + cal.get(Calendar.YEAR);
	}
	
	public static String getMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		return getMonth(cal);
	}
	
	public static String getYear(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		return getYear(cal);
	}

	public static Calendar getCalendar(String dateStr) {
		try {
			Date date = sdf.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int fvWorkingDays(int month, int year) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		int days = c.get(Calendar.DAY_OF_MONTH);
		int n = days;
		int s = 1;
		for (int i = 1; i <= days; i += s) {
			c.set(Calendar.DAY_OF_MONTH, i);
			if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				s = 7;
				n--;
			}
		}
		return n;
	}
	
	public static int fvWorkingDays(String month, String year){
		return fvWorkingDays(Integer.parseInt(month), Integer.parseInt(year));
	}
	
	public static int calcYear(Date from){
		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(from);
		long time = cal.getTimeInMillis()-cal2.getTimeInMillis();
		long day = time/86400000;		
		return Long.valueOf(day).intValue();
	}
	
	public static int[] beforeCurrent(Date date){
		Calendar now = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();		
		cal.setTime(date);
		Calendar cal2 = now;
		Calendar cal1 = cal;
		
		int year = cal2.get(Calendar.YEAR)-cal1.get(Calendar.YEAR);
		int month,day;
		if (year>0){
			month = cal2.get(Calendar.MONTH)-cal1.get(Calendar.MONTH);
			day = cal2.get(Calendar.DAY_OF_MONTH)-cal1.get(Calendar.DAY_OF_MONTH);
			if (month<0){
				month = 12+cal2.get(Calendar.MONTH)-cal1.get(Calendar.MONTH);
				year--;
			}
			if (day<0){
				Calendar c3 = Calendar.getInstance();
				c3.setTimeInMillis(cal2.getTimeInMillis());
				c3.add(Calendar.MONTH, -1);
				day = c3.getActualMaximum(Calendar.DAY_OF_MONTH) + cal2.get(Calendar.DAY_OF_MONTH)-cal1.get(Calendar.DAY_OF_MONTH);
				month--;
			}
		}else{
			year=Math.abs(year);
			month = cal1.get(Calendar.MONTH)-cal2.get(Calendar.MONTH);
			day = cal1.get(Calendar.DAY_OF_MONTH)-cal2.get(Calendar.DAY_OF_MONTH);
			if (month<0){
				month = 12+cal1.get(Calendar.MONTH)-cal2.get(Calendar.MONTH);
				year--;
			}
			if (day<0){
				Calendar c3 = Calendar.getInstance();
				c3.setTimeInMillis(cal1.getTimeInMillis());
				c3.add(Calendar.MONTH, -1);
				day = c3.getActualMaximum(Calendar.DAY_OF_MONTH) + cal1.get(Calendar.DAY_OF_MONTH)-cal2.get(Calendar.DAY_OF_MONTH);
				month--;
			}
		}
		return new int[]{day,month,year};
	}
	
	public static int[] beforeCurrent(Date date, String minField, int offset){
		int[] d = beforeCurrent(date);
		int[] ret=null;
		int day = d[0];
		int month = d[1];
		int year = d[2];
		if (minField.equalsIgnoreCase("month")){
			if (day>=offset){
				month++;
				if (month>11){
					month=0;
					year++;
				}
			}
		}else if (minField.equalsIgnoreCase("year")){
			if (month>=offset){
				month=0;
				year++;
			}
		}
		ret = new int[]{month,year};		
		return ret;
	}
	
	/**
	 * Calculate ...
	 * @param from
	 * @param date
	 * @return d , m , y, before-after
	 */
	public static int[] before(Date from, Date date){
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();		
		c2.setTime(date);
		c1.setTime(from);
		
		int d1=c1.get(Calendar.DAY_OF_MONTH);
		int d2=c2.get(Calendar.DAY_OF_MONTH);
		int m1=c1.get(Calendar.MONTH);
		int m2=c2.get(Calendar.MONTH);
		int y1=c1.get(Calendar.YEAR);
		int y2=c2.get(Calendar.YEAR);
		int year=0,month=0,day=0;
		int cp=0;
		if (c2.compareTo(c1)<0){
			cp=1;
			year=y1-y2;
			month=m1-m2;
			day=d1-d2;		
			if (month<0){
				year--;
				month=m1+12-m2;
			}
			if (day<0){
				month--;
				if (month<0){
					year--;
					month=11;
				}
				c1.add(Calendar.MONTH, -1);
				day=d1+c1.getActualMaximum(Calendar.DAY_OF_MONTH)-d2;
			}			
		}else if (c2.compareTo(c1)>0){
			cp=-1;
			year=y2-y1;
			month=m2-m1;
			day=d2-d1;
			if (month<0){
				year--;
				month=m2+12-m1;
			}
			if (day<0){
				month--;
				if (month<0){
					year--;
					month=11;
				}
				c2.add(Calendar.MONTH, -1);
				day=d2+c2.getActualMaximum(Calendar.DAY_OF_MONTH)-d1;
			}
		}
		
		return new int[]{day,month,year,cp};
	}
	/**
	 * Tinh so ngay phep nam ke tu ngay nhap xuong
	 * @param date
	 * @return
	 */
	public static int getMonthsToEndYear(Date date){
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(date.getTime());
		Date day15 = getDay15(cal1.get(Calendar.MONTH)+1, cal1.get(Calendar.YEAR));
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(day15.getTime());
		String endDateString = "31/12/" + cal.get(Calendar.YEAR);
		Date end;
		try {
			end = new SimpleDateFormat("dd/MM/yyyy").parse(endDateString);
			int[] dmy = before(date, end);
			int m = dmy[1];
			int y = dmy[2];
			m = y*12+m;
			if (cal2.compareTo(cal1)>=0){
				m=m+1;
			}
			return m;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return 0;
	}	
	/**
	 * Lấy tháng cuối quý -1 (quý 6 tháng)
	 * @param month : 1-12
	 * @param year
	 * @return
	 */
	public static Date getThangCuoiQuyBHTru1(int month, int year){
		int t=0,n=0;
		if (month>=1&&month<=6) {
			t=11;
			n=year-1;
		}
		if (month>=7&&month<=12) {
			t=5;
			n=year;
		}
		return getFirstDay(t, n);
	}
	
	// thang cuoi cua quy truoc cua thang bao cao
	public static Date getThangCuoiQuyTruocBH(int month, int year){
		int t=0;
		if (month>=1&&month<=6) {
			t=12;
		}
		if (month>=7&&month<=12) {
			t=6;
		}
		return getFirstDay(t, year);
	}
	
	// thang cuoi cua quy hien tai so voi thang bao cao
	public static Date getThangCuoiQuyHT(int month, int year){
		int t=0;
		if (month>=1&&month<=6) {
			t=6;
		}
		if (month>=7&&month<=12) {
			t=12;
		}
		return getFirstDay(t, year);
	}	
	
	public static Date getThangDauQuyHT(int month, int year){
		int t=0;
		if (month>=1&&month<=6) {
			t=1;
		}
		if (month>=7&&month<=12) {
			t=7;
		}
		return getFirstDay(t, year);		
	}

	public static void main(String[] args) {
		try {
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse("30/07/1994");
			Date from = new SimpleDateFormat("dd/MM/yyyy").parse("16/07/2012");
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(date);
			c2.setTime(from);
			int[] d = before(c2.getTime(), date);
//			int[] d = beforeCurrent(date);
			System.out.println(d[0]);
			System.out.println(d[1]);
			System.out.println(d[2]);
			System.out.println(d[3]);
			System.out.println(getMonthsToEndYear(from));
////			Calendar cal = Calendar.getInstance();
////			cal.setTime(date);
////			while (cal.get(Calendar.YEAR)!=2012){
////				System.out.println(cal.get(Calendar.YEAR) + "-" + cal.getActualMaximum(Calendar.DAY_OF_YEAR));
////				cal.add(Calendar.YEAR, 1);
////			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static Date getPreviousDay(Date date) {
		return addDay(date, -1);
	}
	
	public static Date getNextDay(Date date) {
		return addDay(date, 1);
	}
	
	public static Date addDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

}
