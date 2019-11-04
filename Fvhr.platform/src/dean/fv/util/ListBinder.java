package fv.util;

import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.list.DefaultListModel;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import echopointng.ComboBox;
import fv.components.SelectItem;

public class ListBinder {
	public static void bindSelectField(SelectField sf,
			MappingPropertyEditor editor, boolean display) {
		DefaultListModel model = (DefaultListModel) sf.getModel();
		model.removeAll();
		sf.setSelectedIndex(-1);
		if (editor == null || editor.filterMap().size() <= 0) {
			sf.setEnabled(false);
		} else {
			sf.setEnabled(true);
			for (int i = 0; i < editor.filterMap().size(); i++) {
				String disp = "";
				if (display) {
					disp = editor.getDisplays()[i];
				} else {
					disp = editor.getValues()[i] == null ? "" : editor
							.getValues()[i].toString();
				}
				SelectItem item = new SelectItem(disp, editor.getValues()[i]);
				model.add(item);
			}
		}
	}

	public static void bindDscSelectField(SelectField sf,
			MappingPropertyEditor mpe) {
		DefaultListModel model = new DefaultListModel();
		String[] displays = mpe.getDisplays();
		for (String display : displays) {
			model.add(display);
		}
		sf.setModel(model);
		sf.setSelectedIndex(-1);
	}

	public static void refreshIndex(SelectField sf, Object value) {
		if (value == null) {
			sf.setSelectedIndex(-1);
			return;
		}
		DefaultListModel model = (DefaultListModel) sf.getModel();
		for (int i = 0; i < model.size(); i++) {
			SelectItem item = (SelectItem) model.get(i);
			if (item.getValue() == null)
				continue;
			if (item.getValue().equals(value)) {
				sf.setSelectedIndex(i);
				return;
			}
		}
		sf.setSelectedIndex(-1);
	}

	public static Object get(SelectField sf) {
		if (sf.getSelectedIndex() >= 0) {
			SelectItem item = (SelectItem) sf.getSelectedItem();
			return item.getValue();
		}
		return null;
	}

	public static void bindComboBox(ComboBox cbo, MappingPropertyEditor mpe) {
		DefaultListModel model = new DefaultListModel();
		String[] displays = mpe.getDisplays();
		for (String display : displays) {
			model.add(display);
		}
		cbo.setListModel(model);
	}

	public static Object getCboValue(ComboBox cbo, MappingPropertyEditor mpe) {
		String text = cbo.getText();
		mpe.setAsText(text);
		return mpe.getValue();
	}
	
	public static void bindComboBox(ComboBox cbo, MappingPropertyEditor mpe, boolean display){
		if (display) bindComboBox(cbo, mpe);
		else{
			DefaultListModel model = new DefaultListModel();
			Object[] values = mpe.getValues();
			for (Object value : values) {
				model.add(value);
			}
			cbo.setListModel(model);
		}
	}
	
	public static Object getCboValue(ComboBox cbo, MappingPropertyEditor mpe, boolean display){
		if (display) return getCboValue(cbo, mpe);
		else return cbo.getText();
	}

	public static Object get(SelectField sf, MappingPropertyEditor propertyEditor) {
		if (sf.getSelectedIndex() >= 0) {
			String item = (String) sf.getSelectedItem();
			propertyEditor.setAsText(item);
			return propertyEditor.getValue();
		}
		return null;
	}
}
