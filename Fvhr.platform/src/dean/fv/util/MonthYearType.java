package fv.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MonthYearType {
	private int month;// 1-12

	private int year;

	public MonthYearType(int month, int year) {
		this.month = month;
		this.year = year;
	}

	public MonthYearType(Calendar calendar) {
		this.month = calendar.get(Calendar.MONTH) + 1;
		this.year = calendar.get(Calendar.YEAR);
	}

	public MonthYearType(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		this.month = cal.get(Calendar.MONTH) + 1;
		this.year = cal.get(Calendar.YEAR);
	}

	public MonthYearType(String dateString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = sdf.parse(dateString);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		this.month = cal.get(Calendar.MONTH) + 1;
		this.year = cal.get(Calendar.YEAR);
	}

	public Calendar getDate(int day) {
		if (day < 1 || day > 31)
			throw new IllegalArgumentException("Date must be between 1 and 31");
		Calendar cal = Calendar.getInstance();
		cal.set(this.year, this.month - 1, day);
		return cal;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMonthString() {
		return month < 10 ? "0" + month : "" + month;
	}

	public String getYearString() {
		return "" + year;
	}

	public MonthYearType add(int month) {
		MonthYearType r = get();
		r.month += month;
		return r;
	}

	public MonthYearType subtract(int month) {
		MonthYearType r = get();
		r.month -= month;
		return r;
	}

	public MonthYearType addYear(int year) {
		MonthYearType r = get();
		r.year += year;
		return r;
	}

	public MonthYearType subtractYear(int year) {
		MonthYearType r = get();
		r.year -= year;
		return r;
	}

	public int compareTo(MonthYearType obj) {
		if (this.month > obj.month && this.year >= obj.year)
			return 1;
		else if (this.month == obj.month && this.year == obj.year)
			return 0;
		else
			return -1;
	}

	public MonthYearType get() {
		MonthYearType type = new MonthYearType(month, year);
		return type;
	}

	@Override
	public String toString() {
		return getMonthString() + "/" + getYearString();
	}

	public boolean equals(MonthYearType type) {
		return compareTo(type) == 0;
	}
}
