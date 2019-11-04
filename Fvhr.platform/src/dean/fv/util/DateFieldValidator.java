package fv.util;

import java.text.DateFormat;
import java.text.ParseException;

import echopointng.DateField;

public class DateFieldValidator {
	public static String validate(DateField df){
		if (df.getText().trim().equals("")){
			return "empty";
		}
		DateFormat format = df.getDateFormat();
		try {
			format.parse(df.getText());
		} catch (ParseException e) {
			e.printStackTrace();
			return "invalid";
		}
		return "";
	}
}
