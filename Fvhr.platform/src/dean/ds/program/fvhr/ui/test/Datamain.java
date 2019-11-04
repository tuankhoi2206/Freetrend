package ds.program.fvhr.ui.test;


import java.util.Calendar;

import ds.program.fvhr.dao.wp.WorkpointsDAO;

public class Datamain {
	public static void main(String[] args) {
		Datamain data = new Datamain();
		String[] d = data.getData("20452145204519450630063006300630");
		System.out.println(d[0] + ">" + d[1]);
		WorkpointsDAO dao = new WorkpointsDAO();
		Calendar cal = Calendar.getInstance();
		cal.set(2011, 7, 24);
		String[] wt = dao.getRealWorkingTime("10101311", cal.getTime());
		System.out.println(wt[0] + ">>" + wt[1]);
	}
	public String[] getData(String in){
		int t,t1=0,t2=0;
		String temp;
		int len = in.length()/4;
		System.out.println(len);
		for (int i=0;i<len;i++){
			temp=in.substring(i*4, i*4+4);
			System.out.println(temp);
			t=Integer.parseInt(temp);
			if (i==0){
				t1=t2=t;
			}else{
				if (t1<t) t1=t;
				if (t2>t) t2=t;
			}
		}
		return new String[]{time(t1),time(t2)};
	}
	
	public String time(int t){
		int t1=t/100;
		int t2=t%100;
		String s1 = t1<10?"0"+t1:""+t1;
		String s2 = t2<10?"0"+t2:""+t2;
		return s1+":"+s2;
	}
}
