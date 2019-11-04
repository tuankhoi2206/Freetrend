package fv.util;

public interface IEmployeeValidator {
	/**
	 * Check employee is working or quit or not available base on serial number (empsn)
	 * @param empsn - employee serial number
	 * @return <b>true</b> - existing in n_employee table with depsn <> '00000'.<br/>
	 * 		   <b>false</b> - quited or not available.
	 */
	boolean isWorking(String empsn);
	boolean isWorkingOrQuit(String empsn);
}
