package fv.components;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import dsc.echo2app.component.DscDateField;

/**
 * Date Time Field
 * 
 * @author Hieu
 * 
 */
public class FVDateTimeField extends Row {
	private static final long serialVersionUID = 1L;

	private DscDateField dateField;

	private SelectField hourField;

	private SelectField minField;

	public FVDateTimeField() {
		super();
		initComponents();
	}

	public DscDateField getDateField() {
		return dateField;
	}

	public Timestamp getSelectedTimestamp() {
		Calendar c = dateField.getSelectedDate();
		c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourField
				.getSelectedItem().toString()));
		c.set(Calendar.MINUTE, Integer.parseInt(minField.getSelectedItem()
				.toString()));
		// c.set(Calendar.SECOND, 0);
		return new Timestamp(c.getTime().getTime());
	}

	public void setSelectedTimestamp(Timestamp t) {
		Calendar c = Calendar.getInstance();
		Date date = new Date(t.getTime());
		c.setTime(date);
		dateField.setSelectedDate(c);
		hourField.setSelectedIndex(c.get(Calendar.HOUR_OF_DAY));
		minField.setSelectedIndex(c.get(Calendar.MINUTE));
	}

	public void setDefaultNewDate() {
		Calendar c = Calendar.getInstance();
		dateField.setSelectedDate(c);
		hourField.setSelectedIndex(c.get(Calendar.HOUR_OF_DAY));
		minField.setSelectedIndex(c.get(Calendar.MINUTE));
	}

	private void initComponents() {
		dateField = new DscDateField();
		dateField.getDateChooser().setLocale(new Locale("en"));
		this.add(dateField);
		hourField = new SelectField(timeData("h"));
		this.add(hourField);
		Label lbl = new Label(":");
		this.add(lbl);
		minField = new SelectField(timeData("m"));
		this.add(minField);
		dateField.getTextField().setDisabledBackground(new Color(0xC8C8C8));
		hourField.setDisabledBackground(new Color(0xC8C8C8));
		minField.setDisabledBackground(new Color(0xC8C8C8));
	}

	private String[] timeData(String type) {
		if (type.equalsIgnoreCase("h")) {
			String[] data = new String[24];
			for (int i = 0; i < 24; i++) {
				data[i] = String.valueOf(i);
			}
			return data;
		} else {
			String[] data = new String[60];
			for (int i = 0; i < 60; i++) {
				data[i] = String.valueOf(i);
			}
			return data;
		}
	}
}
