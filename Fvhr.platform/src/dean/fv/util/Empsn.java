package fv.util;

public class Empsn {
	public synchronized static String correct(String empsn){
		if (empsn.length()==8) return empsn;
		String s="";
		if (empsn.length()<8){
			int n = 8-empsn.length();
			for (int i=0;i<n;i++){
				s=s+"0";
			}
		}
		return s+empsn;
	}
}
