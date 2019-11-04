package ds.program.fvhr.services.validator;

import java.security.InvalidParameterException;

import ds.program.fvhr.services.info.BasicEmployeeInfo;

public class EmpsnValidateCondition {
	private String condition;

	private String month;

	private String year;

	public EmpsnValidateCondition(String condition) {
		this.condition = condition;
	}

	public boolean validate(String empsn) {
		if (condition.equals(Validator.QUIT)) {
			return BasicEmployeeInfo.isQuit(empsn);
		}else if (condition.equals(Validator.NOTFOUND)){
			return BasicEmployeeInfo.isExist(empsn);
		}else if (condition.equals(Validator.NOTWORK)) {
			if (month == null || year == null)
				throw new InvalidParameterException(
						"Must set month and year value");
			return BasicEmployeeInfo.hasWorkData(empsn, month, year);
		} else if (condition.equals(Validator.UNKNOWN)) {
			if (month == null || year == null)
				throw new InvalidParameterException(
						"Must set month and year value");
			return BasicEmployeeInfo.isQuitAndHasWP(empsn, month, year);
		} else {
			throw new InvalidParameterException(
					"Invalid empsn validate condition");
		}
	}

	public String getMessage() {
		return condition;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
