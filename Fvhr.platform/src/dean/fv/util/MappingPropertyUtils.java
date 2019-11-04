package fv.util;

import java.util.Calendar;

import dsc.echo2app.propertyeditors.MappingPropertyEditor;

public class MappingPropertyUtils {
	
	public static MappingPropertyEditor getDayEditor(int month){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, month);
		int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		MappingPropertyEditor editor = new MappingPropertyEditor();
		for (int i=1;i<=days;i++){
			if (i<10) editor.put("0"+i, i);
			else editor.put(String.valueOf(i), i);
		}
		return editor;
	}
	
	public static MappingPropertyEditor getStringMonthEditor(){
		MappingPropertyEditor editor = new MappingPropertyEditor();
		editor.put("01", "01");
		editor.put("02", "02");
		editor.put("03", "03");
		editor.put("04", "04");
		editor.put("05", "05");
		editor.put("06", "06");
		editor.put("07", "07");
		editor.put("08", "08");
		editor.put("09", "09");
		editor.put("10", "10");
		editor.put("11", "11");
		editor.put("12", "12");
		return editor;
	}
	public static MappingPropertyEditor getJavaMonthEditor(){
		MappingPropertyEditor editor = new MappingPropertyEditor();
		editor.put("01", 0);
		editor.put("02", 1);
		editor.put("03", 2);
		editor.put("04", 3);
		editor.put("05", 4);
		editor.put("06", 5);
		editor.put("07", 6);
		editor.put("08", 7);
		editor.put("09", 8);
		editor.put("10", 9);
		editor.put("11", 10);
		editor.put("12", 11);
		return editor;
	}
	public static MappingPropertyEditor getMonthEditor(){
		MappingPropertyEditor editor = new MappingPropertyEditor();
		editor.put("01", 1);
		editor.put("02", 2);
		editor.put("03", 3);
		editor.put("04", 4);
		editor.put("05", 5);
		editor.put("06", 6);
		editor.put("07", 7);
		editor.put("08", 8);
		editor.put("09", 9);
		editor.put("10", 10);
		editor.put("11", 11);
		editor.put("12", 12);
		return editor;
	}
	public static MappingPropertyEditor getYearEditor(int width){
		MappingPropertyEditor editor = new MappingPropertyEditor();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		for (int i=0;i<=width*2;i++){
			int y = year-width+i;
			editor.put(String.valueOf(y), y);
		}		
		return editor;
	}
	public static MappingPropertyEditor getYearEditor(int width, boolean forward){
		MappingPropertyEditor editor = new MappingPropertyEditor();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if (forward){
			for (int i=0;i<=width;i++){
				int y = year+i;
				editor.put(String.valueOf(y), y);
			}
		}else{
			for (int i=width;i>=0;i--){
				int y = year-i;				
				editor.put(String.valueOf(y), y);
			}
		}
		return editor;
	}
	
	public static MappingPropertyEditor getStringYearEditor(int width, boolean forward){
		MappingPropertyEditor editor = new MappingPropertyEditor();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if (forward){
			for (int i=0;i<=width;i++){
				int y = year+i;				
				editor.put(String.valueOf(y), String.valueOf(y));
			}
		}else{
			for (int i=width;i>=0;i--){
				int y = year-i;				
				editor.put(String.valueOf(y), String.valueOf(y));
			}
		}
		return editor;
	}
}
