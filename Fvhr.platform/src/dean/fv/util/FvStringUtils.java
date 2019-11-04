package fv.util;

/**
 * String utilities
 * 
 * @author Hieu
 * 
 */
public class FvStringUtils {
	//clean double space string
	public static String cleanSpaceChar(String text) {
		return text.trim().replaceAll("\\s{2,}", " ");
	}

	public static String cleanAndUpper(String text) {
		return cleanSpaceChar(text).toUpperCase();
	}
	
	public static String removeSpaceCharAndUpper(String text){
		return text.replace(" ", "").toUpperCase();
	}

	public static String fixEmpsn(int empsn) {
		String emp = String.valueOf(empsn);
		while (emp.length()<8){
			emp = "0"+emp;
		}
		return emp;
	}
	
	public static String fixEmpsn(String emp){
		while (emp.length()<8){
			emp = "0"+emp;
		}
		return emp;
	}
}
