package fv.util;

import java.util.Date;
import java.util.List;

public interface IWPValidator {
	boolean isWPLocked(String empsn, String months, String years);
	
	int countWPLocked(List<String> listEmpsn, String months, String years);
	
	int countDeptWPLocked(String deptId, String months, String years);
	
	int countWPLocked(Object factId, Object groupId, Object deptName, String months, String years, String fvlGroupCondition);
	
	int countGroupWPLocked(String factId, String groupId, String months, String years);
	
	int countFactWPLocked(String factId, String months, String years);
	
	boolean isDailyWPCompleted(String empsn, Date date);
	
	boolean isMonthlyWPCompleted(String empsn, String months, String years);
}
