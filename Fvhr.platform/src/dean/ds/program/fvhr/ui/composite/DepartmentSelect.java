package ds.program.fvhr.ui.composite;

import java.util.ResourceBundle;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.SelectField;
import dsc.echo2app.component.DscGroupCheckBox;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.ApplicationInstance;

public class DepartmentSelect extends ContentPane {

	private ResourceBundle resourceBundle;
	private SelectField sfFact;
	private DscGroupCheckBox groupFVL;
	private CheckBox chkFv1;
	private CheckBox chkFv2;
	private CheckBox chkFv3;
	private CheckBox chkFv4;
	private CheckBox chkFv5;
	private CheckBox chkOther;
	private SelectField sfLean;
	private SelectField sfDept;
	private Label lblFactory;
	private Label lblLean;
	private Label lblDept;

	/**
	 * Creates a new <code>DepartmentSelect</code>.
	 */
	public DepartmentSelect() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	protected void sfLeanChanged(ActionEvent e) {
	}

	protected void sfFactChanged(ActionEvent e) {
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		resourceBundle = ResourceBundle.getBundle(
				"resource.localization.UICaption", ApplicationInstance
						.getActive().getLocale());
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(2, Extent.PX)));
		add(grid1);
		lblFactory = new Label();
		lblFactory.setText(resourceBundle.getString("N_DEPARTMENT.NAME_FACT"));
		grid1.add(lblFactory);
		sfFact = new SelectField();
		sfFact.setEnabled(false);
		sfFact.setWidth(new Extent(100, Extent.PX));
		sfFact.setDisabledBackground(new Color(0xc0c0c0));
		sfFact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sfFactChanged(e);
			}
		});
		grid1.add(sfFact);
		Label label7 = new Label();
		grid1.add(label7);
		groupFVL = new DscGroupCheckBox();
		groupFVL.setEnabled(false);
		groupFVL.setSize(3);
		grid1.add(groupFVL);
		chkFv1 = new CheckBox();
		chkFv1.setText("FV1");
		chkFv1.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv1);
		chkFv2 = new CheckBox();
		chkFv2.setText("FV2");
		chkFv2.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv2);
		chkFv3 = new CheckBox();
		chkFv3.setText("FV3");
		chkFv3.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv3);
		chkFv4 = new CheckBox();
		chkFv4.setText("FV5");
		chkFv4.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv4);
		chkFv5 = new CheckBox();
		chkFv5.setText("FV6");
		chkFv5.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv5);
		chkOther = new CheckBox();
		chkOther.setText("Khác");
		chkOther.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkOther);
		lblLean = new Label();
		lblLean.setText(resourceBundle.getString("N_DEPARTMENT.NAME_GROUP"));
		grid1.add(lblLean);
		sfLean = new SelectField();
		sfLean.setEnabled(false);
		sfLean.setWidth(new Extent(140, Extent.PX));
		sfLean.setDisabledBackground(new Color(0xc0c0c0));
		sfLean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sfLeanChanged(e);
			}
		});
		grid1.add(sfLean);
		lblDept = new Label();
		lblDept.setText(resourceBundle.getString("N_DEPARTMENT.NAME_DEPT"));
		grid1.add(lblDept);
		sfDept = new SelectField();
		sfDept.setEnabled(false);
		sfDept.setWidth(new Extent(180, Extent.PX));
		sfDept.setDisabledBackground(new Color(0xc0c0c0));
		grid1.add(sfDept);
	}
}
