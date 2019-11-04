package ds.program.fvhr.ui;

import java.util.ResourceBundle;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import echopointng.ComboBox;

public class my_Ctrl_Dept extends Grid {

	private ResourceBundle resourceBundle;

	ComboBox cbx_fact;

	ComboBox cbx_group;

	ComboBox cbx_dept;

	/**
	 * Creates a new <code>my_Ctrl_Dept</code>.
	 */
	public my_Ctrl_Dept() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	/**
	 * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setInsets(new Insets(new Extent(5, Extent.PX)));
		this.setSize(1);
		ComboBox cbx_fact2 = new ComboBox();
		add(cbx_fact2);
		ComboBox comboBox1 = new ComboBox();
		add(comboBox1);
		ComboBox comboBox2 = new ComboBox();
		add(comboBox2);
	}
}
