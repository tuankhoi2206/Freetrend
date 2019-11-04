package ds.program.fvhr.ui.workpoints;

import java.util.ResourceBundle;
import dsc.echo2app.program.DefaultProgram;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscDateField;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.RadioButton;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscGroupCheckBox;
import nextapp.echo2.app.ListBox;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.ResourceImageReference;

public class NDataDailyProgram extends DefaultProgram {

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
	private Row toolbar;
	private Button btnRefresh;
	/**
	 * Creates a new <code>NDataDailyProgram</code>.
	 */
	public NDataDailyProgram() {
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
	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(35, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane1.setResizable(true);
		add(splitPane1);
		toolbar = new Row();
		toolbar.setStyleName("Default.Toolbar");
		splitPane1.add(toolbar);
		btnRefresh = new Button();
		btnRefresh.setStyleName("Default.ToolbarButton");
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnRefresh.gif");
		btnRefresh.setIcon(imageReference1);
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnRefreshD.gif");
		btnRefresh.setDisabledIcon(imageReference2);
		toolbar.add(btnRefresh);
		SplitPane splitPane2 = new SplitPane();
		splitPane2.setSeparatorPosition(new Extent(240, Extent.PX));
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/SplitHerzBar.png");
		splitPane2.setSeparatorHorizontalImage(new FillImage(imageReference3));
		splitPane2.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		ResourceImageReference imageReference4 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/SplitVertBar.png");
		splitPane2.setSeparatorVerticalImage(new FillImage(imageReference4));
		splitPane2.setResizable(true);
		splitPane1.add(splitPane2);
		ContentPane contentPane3 = new ContentPane();
		splitPane2.add(contentPane3);
		SplitPane splitPane3 = new SplitPane();
		splitPane3.setSeparatorPosition(new Extent(30, Extent.PX));
		splitPane3.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		contentPane3.add(splitPane3);
		Row row3 = new Row();
		SplitPaneLayoutData row3LayoutData = new SplitPaneLayoutData();
		row3LayoutData.setInsets(new Insets(new Extent(9, Extent.PX),
				new Extent(3, Extent.PX), new Extent(3, Extent.PX), new Extent(
						3, Extent.PX)));
		row3.setLayoutData(row3LayoutData);
		splitPane3.add(row3);
		Label label1 = new Label();
		label1.setText("Từ ngày");
		row3.add(label1);
		DscDateField dscDateField1 = new DscDateField();
		row3.add(dscDateField1);
		Label label5 = new Label();
		label5.setText("Đến ngày");
		row3.add(label5);
		DscDateField dscDateField2 = new DscDateField();
		row3.add(dscDateField2);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(3, Extent.PX)));
		grid1.setSize(2);
		splitPane3.add(grid1);
		Grid grid2 = new Grid();
		grid2.setInsets(new Insets(new Extent(2, Extent.PX)));
		GridLayoutData grid2LayoutData = new GridLayoutData();
		grid2LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		grid2.setLayoutData(grid2LayoutData);
		grid1.add(grid2);
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
		grid2.add(radEmpsn);
		Label label2 = new Label();
		label2.setText("Số thẻ");
		grid2.add(label2);
		txtEmpsn = new DscField();
		txtEmpsn.setWidth(new Extent(100, Extent.PX));
		txtEmpsn.setDisabledBackground(new Color(0xc0c0c0));
		txtEmpsn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToList(e);
			}
		});
		grid2.add(txtEmpsn);
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
		grid2.add(radDept);
		Label label3 = new Label();
		label3.setText("Xưởng");
		grid2.add(label3);
		Row row2 = new Row();
		row2.setAlignment(new Alignment(Alignment.DEFAULT, Alignment.TOP));
		grid2.add(row2);
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
		row2.add(sfFact);
		groupFVL = new DscGroupCheckBox();
		groupFVL.setEnabled(false);
		groupFVL.setSize(3);
		row2.add(groupFVL);
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
		grid2.add(label4);
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
		grid2.add(sfLean);
		Label label6 = new Label();
		label6.setText("Đơn vị");
		grid2.add(label6);
		sfDept = new SelectField();
		sfDept.setEnabled(false);
		sfDept.setWidth(new Extent(180, Extent.PX));
		sfDept.setDisabledBackground(new Color(0xc0c0c0));
		grid2.add(sfDept);
		Label label8 = new Label();
		grid2.add(label8);
		chkNV = new CheckBox();
		chkNV.setText("Nghỉ việc");
		chkNV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkNVChanged(e);
			}
		});
		grid2.add(chkNV);
		Label label9 = new Label();
		grid2.add(label9);
		chkVS = new CheckBox();
		chkVS.setText("Về sớm - con nhỏ");
		chkVS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkVSChanged(e);
			}
		});
		grid2.add(chkVS);
		lstEmpsn = new ListBox();
		lstEmpsn.setHeight(new Extent(190, Extent.PX));
		lstEmpsn.setWidth(new Extent(150, Extent.PX));
		lstEmpsn.setDisabledBackground(new Color(0xc0c0c0));
		GridLayoutData lstEmpsnLayoutData = new GridLayoutData();
		lstEmpsnLayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		lstEmpsnLayoutData.setRowSpan(8);
		lstEmpsn.setLayoutData(lstEmpsnLayoutData);
		grid1.add(lstEmpsn);
		Row row1 = new Row();
		row1.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		row1.setInsets(new Insets(new Extent(8, Extent.PX), new Extent(5,
				Extent.PX)));
		row1.setCellSpacing(new Extent(5, Extent.PX));
		SplitPaneLayoutData row1LayoutData = new SplitPaneLayoutData();
		row1LayoutData.setOverflow(SplitPaneLayoutData.OVERFLOW_HIDDEN);
		row1.setLayoutData(row1LayoutData);
		splitPane2.add(row1);
	}
}
