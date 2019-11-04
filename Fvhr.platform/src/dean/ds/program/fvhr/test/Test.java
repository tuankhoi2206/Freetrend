package ds.program.fvhr.test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ds.program.fvhr.dao.generic.FvGenericDAO;
import fv.util.Empsn;

public class Test {
	public static void main(String[] args) {
//		Calendar cal1 = Calendar.getInstance();
//		cal1.set(2011, 8, 1);
//		Date from = cal1.getTime();
//		Calendar cal2 = Calendar.getInstance();
//		cal2.set(2011, 9, 1);
//		Date to = cal2.getTime();
//		List list = FvGenericDAO.getInstanse().get7HList(from, to, "FVL", "", "", "");
//		System.out.println(list.size());
		System.out.println(Empsn.correct("4123"));
	}
}
