package ds.program.fvhr.ui.util;

import java.util.ResourceBundle;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.RadioButton;
import dsc.echo2app.component.DscField;
import nextapp.echo2.app.SelectField;
import dsc.echo2app.component.DscGroupCheckBox;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.ListBox;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Color;

public class EmployeeSearch extends ContentPane {

	private ResourceBundle resourceBundle;
	private RadioButton radEmpsn;
	private DscField txtEmpsn;
	private RadioButton radDept;
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
	private CheckBox chkNV;
	private CheckBox chkVS;
	private ListBox lstEmpsn;
	private Grid conditionGrid;

	/**
	 * Creates a new <code>EmployeeSearch</code>.
	 */
	public EmployeeSearch() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	protected void chkVSChanged(ActionEvent e) {
	}

	protected void chkNVChanged(ActionEvent e) {
	}

	protected void sfLeanChanged(ActionEvent e) {
	}

	protected void sfFactChanged(ActionEvent e) {
	}

	protected void addToList(ActionEvent e) {
	}

	protected void empGroupSelected(ActionEvent e) {
	}
	
	public void addComponentToGrid(Component c){
		conditionGrid.add(c);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(3, Extent.PX)));
		grid1.setSize(2);
		add(grid1);
		conditionGrid = new Grid();
		conditionGrid.setInsets(new Insets(new Extent(4, Extent.PX)));
		GridLayoutData conditionGridLayoutData = new GridLayoutData();
		conditionGridLayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		conditionGrid.setLayoutData(conditionGridLayoutData);
		grid1.add(conditionGrid);
		radEmpsn = new RadioButton();
		radEmpsn.setSelected(true);
		radEmpsn.setText("Chọn theo số thẻ");
		ButtonGroup group_emp = new ButtonGroup();
		radEmpsn.setGroup(group_emp);
		radEmpsn.setFont(new Font(null, Font.BOLD | Font.UNDERLINE, new Extent(
				10, Extent.PT)));
		radEmpsn.setActionCommand("cmd_emp");
		GridLayoutData radEmpsnLayoutData = new GridLayoutData();
		radEmpsnLayoutData.setColumnSpan(2);
		radEmpsn.setLayoutData(radEmpsnLayoutData);
		radEmpsn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				empGroupSelected(e);
			}
		});
		conditionGrid.add(radEmpsn);
		Label label2 = new Label();
		label2.setText("Số thẻ");
		conditionGrid.add(label2);
		txtEmpsn = new DscField();
		txtEmpsn.setInputType(DscField.INPUT_TYPE_TEXT);
		txtEmpsn.setWidth(new Extent(100, Extent.PX));
		txtEmpsn.setToolTipText("Nhập số thẻ và nhấn enter");
		txtEmpsn.setDisabledBackground(new Color(0xc0c0c0));
		txtEmpsn.setMaximumLength(8);
		txtEmpsn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToList(e);
			}
		});
		conditionGrid.add(txtEmpsn);
		radDept = new RadioButton();
		radDept.setText("Chọn theo đơn vị");
		radDept.setGroup(group_emp);
		radDept.setFont(new Font(null, Font.BOLD | Font.UNDERLINE, new Extent(
				10, Extent.PT)));
		radDept.setActionCommand("cmd_dept");
		GridLayoutData radDeptLayoutData = new GridLayoutData();
		radDeptLayoutData.setColumnSpan(2);
		radDept.setLayoutData(radDeptLayoutData);
		radDept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				empGroupSelected(e);
			}
		});
		conditionGrid.add(radDept);
		Label label3 = new Label();
		label3.setText("Xưởng");
		conditionGrid.add(label3);
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
		conditionGrid.add(sfFact);
		Label label7 = new Label();
		conditionGrid.add(label7);
		groupFVL = new DscGroupCheckBox();
		groupFVL.setEnabled(false);
		groupFVL.setSize(3);
		conditionGrid.add(groupFVL);
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
		Label label4 = new Label();
		label4.setText("Lean");
		conditionGrid.add(label4);
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
		conditionGrid.add(sfLean);
		Label label6 = new Label();
		label6.setText("Đơn vị");
		conditionGrid.add(label6);
		sfDept = new SelectField();
		sfDept.setEnabled(false);
		sfDept.setWidth(new Extent(180, Extent.PX));
		sfDept.setDisabledBackground(new Color(0xc0c0c0));
		conditionGrid.add(sfDept);
		Label label8 = new Label();
		conditionGrid.add(label8);
		chkNV = new CheckBox();
		chkNV.setEnabled(false);
		chkNV.setText("Nghỉ việc");
		chkNV.setDisabledBackground(new Color(0xc0c0c0));
		chkNV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkNVChanged(e);
			}
		});
		conditionGrid.add(chkNV);
		Label label9 = new Label();
		conditionGrid.add(label9);
		chkVS = new CheckBox();
		chkVS.setEnabled(false);
		chkVS.setText("Về sớm - con nhỏ");
		chkVS.setDisabledBackground(new Color(0xc0c0c0));
		chkVS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkVSChanged(e);
			}
		});
		conditionGrid.add(chkVS);
		lstEmpsn = new ListBox();
		lstEmpsn.setHeight(new Extent(250, Extent.PX));
		lstEmpsn.setWidth(new Extent(150, Extent.PX));
		lstEmpsn.setDisabledBackground(new Color(0xc0c0c0));
		GridLayoutData lstEmpsnLayoutData = new GridLayoutData();
		lstEmpsnLayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		lstEmpsnLayoutData.setRowSpan(8);
		lstEmpsn.setLayoutData(lstEmpsnLayoutData);
		grid1.add(lstEmpsn);
	}
}
