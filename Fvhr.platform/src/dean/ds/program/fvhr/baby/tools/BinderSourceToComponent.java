package ds.program.fvhr.baby.tools;

/**
 * author : baby
 */

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
//import org.apache.poi.hssf.model.Model;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import dsc.echo2app.propertyeditors.MappingPropertyEditor;

import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.list.ListModel;

public class BinderSourceToComponent {
	private static DefaultListModel listModel ;
	private Map<Object, String> map = new LinkedMap();
	private class Item{
	private String display;
	private Object value;
	public Item()
	{super();}
	public Item(String disp,Object value){
		this.display = disp;
		this.value = value;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	}
	
	public static void putSourceToSelectField(List listSource,SelectField sf )
	{
		listModel = (DefaultListModel) sf.getModel();
		listModel.removeAll();
		for (Object object : listSource) {
			listModel.add(object);
		}
		sf.setModel(listModel);
	}
	public static void putSourceToSelectField(MappingPropertyEditor map, SelectField sf)
	{
		listModel = (DefaultListModel) sf.getModel();
		listModel.removeAll();
		String[] Display = map.getDisplays();
		for (String string : Display) {
			listModel.add(string);			
		}
		sf.setModel(listModel);
	}
	public static CustomDateEditor createDateEditor(String pattern){
		SimpleDateFormat sp = new SimpleDateFormat(pattern);
		sp.setLenient(false);
		return new CustomDateEditor(sp, true); 
	}
	public Map<Object, String> createMappingEditor(String[] display,Object[] value){
		for(int i = 0; i<display.length;i++){
			map.put(value[i], display[i]);
		}
		return map;
	}
}
