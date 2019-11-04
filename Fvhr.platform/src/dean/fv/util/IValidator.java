package fv.util;

import java.util.Date;
import java.util.List;

/**
 * Fv Human resource management program common validator interface
 * @author Hieu
 *
 */
public interface IValidator {
	/**
	 * Check employee is working or quit or not available base on serial number (empsn)
	 * @param empsn - employee serial number
	 * @return <b>true</b> - existing in n_employee table with depsn <> '00000'.<br/>
	 * 		   <b>false</b> - quited or not available.
	 */
	boolean isWorking(String empsn);
	/**
	 * Check the employee's workpoints in n_get_data is locked or not.
	 * @param empsn
	 * @param months
	 * @param years
	 * @return
	 */
	boolean isWorkPointsLocked(String empsn, String months, String years);
	
	boolean isDailyWPCompleted(String empsn, Date date);
	
	int countWPLocked(List<String> list, String months, String years);
}
