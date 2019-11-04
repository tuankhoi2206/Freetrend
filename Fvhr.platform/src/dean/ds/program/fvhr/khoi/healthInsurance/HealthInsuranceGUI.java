package ds.program.fvhr.khoi.healthInsurance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.FillImageBorder;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import dsc.echo2app.Application;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscWindowPane;
import dsc.echo2app.program.DefaultProgram;
import echopointng.GroupBox;

public class HealthInsuranceGUI extends DefaultProgram implements
		ActionListener {

	/**
	 * @author Tuan Khoi
	 */
	private static final long serialVersionUID = 1L;

	private SplitPane splitPaneMain;
	protected Button btnUpdate;
	protected Button btnExport;
	protected CheckBox chkAllExport;

	protected SelectField sfIdDept;
	protected SelectField sfNameFact;
	protected SelectField sfNameGroup;
	protected SelectField sfNameDeptName;
	protected DscDateField dfNgayChay;
	protected SelectField sfLanChay;

	public HealthInsuranceGUI() {
		InitComponents();

	}

	protected void InitComponents() {

		splitPaneMain = new SplitPane();
		splitPaneMain = new SplitPane(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPaneMain.setSeparatorPosition(new Extent(50, Extent.PX));
		splitPaneMain.setSeparatorColor(new Color(0x0080ff));
		splitPaneMain.setSeparatorHeight(new Extent(2));

		this.add(splitPaneMain);

		createTitle();
		createComponentsCenter();

		loadDataDefaultComponents();
		addActionListerComponents();
		setDisplayDateField(new SimpleDateFormat("dd/MM/yyyy"));
	}

	private void setDisplayDateField(SimpleDateFormat sdf) {
		Calendar ca = Calendar.getInstance();
		dfNgayChay.getDateChooser().setLocale(Locale.ENGLISH);
		dfNgayChay.setDateFormat(sdf);
		dfNgayChay.getTextField().setText(sdf.format(ca.getTime()));
		dfNgayChay.setMouseCursor(DATAMODE_EDIT);
	}

	private void addActionListerComponents() {
		btnUpdate.addActionListener(this);
		btnExport.addActionListener(this);
		sfLanChay.addActionListener(this);
	}

	protected void loadDataDefaultComponents() {
		DefaultListModel lstModel = new DefaultListModel(new String[] {
				"Lần 1", "Lần 2" });
		sfLanChay.setModel(lstModel);

	}

	protected void createTitle() {

		Label lblTitle = new Label("KÝ TRÌNH BẢO HIỂM Y TẾ");
		lblTitle.setFont(new Font(Font.ARIAL, Font.BOLD, new Extent(35)));
		lblTitle.setForeground(new Color(0x0080c0));
		// lblTitle.setForeground(new Color(0x006ddb));
		SplitPaneLayoutData label1LayoutData = new SplitPaneLayoutData();
		label1LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.CENTER));
		lblTitle.setLayoutData(label1LayoutData);

		splitPaneMain.add(lblTitle, 0);

	}

	@Override
	protected int doInit() {
		super.doInit();

		windowPane.setDefaultCloseOperation(DscWindowPane.DISPOSE_ON_CLOSE);
		windowPane.setWidth(new Extent(700, Extent.PX));
		windowPane.setHeight(new Extent(470, Extent.PX));
		windowPane.setMaximizable(false);
		windowPane.setResizable(false);
		windowPane.setPositionX(new Extent(Application.getApp()
				.getScreenHeight() / 3));
		windowPane.setPositionY(new Extent(Application.getApp()
				.getScreenWidth() / 10));
		// windowPane.setInsets(new Insets(new Extent(10, Extent.PX), new
		// Extent(
		// 0, Extent.PX)));

		FillImageBorder borderWinPane = new FillImageBorder(Color.LIGHTGRAY,
				new Insets(new Extent(1, Extent.PX)), new Insets(new Extent(1,
						Extent.PX)));
		windowPane.setBorder(borderWinPane);

		return RET_OK;
	}

	protected void createComponentsCenter() {
		Grid rootLayout = new Grid(1);
		rootLayout.setInsets(new Insets(new Extent(5, Extent.PX)));
		// grid1.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(10,
		// Extent.PX), new Extent(5, Extent.PX), new Extent(10, Extent.PX)));
		// grid1.setRowHeight(0, new Extent(120, Extent.PX));
		// grid1.setSize(2);

		splitPaneMain.add(rootLayout, 1);

		// colum 1
		GroupBox groupBoxMenu = new GroupBox();
		// groupBoxMenu.setTitle("Lựa chọn");
		rootLayout.add(groupBoxMenu);

		GridLayoutData groupBox3LayoutData = new GridLayoutData();
		groupBox3LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBoxMenu.setLayoutData(groupBox3LayoutData);

		rootLayout.add(groupBoxMenu);

		Grid groupComponent = new Grid();
		groupComponent.setInsets(new Insets(new Extent(2, Extent.PX),
				new Extent(5, Extent.PX), new Extent(2, Extent.PX), new Extent(
						5, Extent.PX)));

		// ===================================

		// ===================================

		groupBoxMenu.add(groupComponent);

		Label label8 = new Label();
		label8.setText("Xưởng");
		groupComponent.add(label8);
		sfNameFact = new SelectField();
		sfNameFact.setWidth(new Extent(150, Extent.PX));
		groupComponent.add(sfNameFact);

		Label label6 = new Label();
		label6.setText("Mã đơn vị");
		groupComponent.add(label6);
		sfIdDept = new SelectField();
		sfIdDept.setWidth(new Extent(150, Extent.PX));
		groupComponent.add(sfIdDept);

		Label label9 = new Label();
		label9.setText("Nhóm");
		groupComponent.add(label9);
		sfNameGroup = new SelectField();
		sfNameGroup.setWidth(new Extent(150, Extent.PX));
		groupComponent.add(sfNameGroup);
		Label label10 = new Label();
		label10.setText("Đơn vị");
		groupComponent.add(label10);
		sfNameDeptName = new SelectField();
		sfNameDeptName.setWidth(new Extent(150, Extent.PX));
		groupComponent.add(sfNameDeptName);

		Label label = new Label("Ký trình lần ");
		groupComponent.add(label);
		sfLanChay = new SelectField();
		sfLanChay.setWidth(new Extent(150, Extent.PX));
		groupComponent.add(sfLanChay);

		Label label1 = new Label("Ký trình ngày ");
		groupComponent.add(label1);
		dfNgayChay = new DscDateField();
		dfNgayChay.setWidth(new Extent(150, Extent.PX));
		groupComponent.add(dfNgayChay);

		// end column 1

		Column colBtn = new Column();
		// colBtn.setInsets(new Insets(new Extent(5, Extent.PX)));
		colBtn.setCellSpacing(new Extent(2, Extent.PX));
		rootLayout.add(colBtn);

		btnUpdate = getDefaultButton();
		btnUpdate.setText("Cập nhật ký trình");

		colBtn.add(btnUpdate);

		chkAllExport = new CheckBox("Xuất toàn bộ các xưởng");
		colBtn.add(chkAllExport);

		btnExport = getDefaultButton();
		btnExport.setText("Xuất ký trình");
		colBtn.add(btnExport);

	}

	protected Button getDefaultButton() {

		Button btnDefault = new Button("Default");
		btnDefault.setHeight(new Extent(28, Extent.PX));
		//
		// btnUpdate.setWidth(border.getSize());
		btnDefault.setForeground(Color.WHITE);

		// btnUpdate.setWidth(exbtn);

		// btnDefault.setBorder(new Border(new Extent(2, Extent.PX), new Color(
		// 0x80ffff), Border.STYLE_GROOVE));

		// btnDefault.setPressedBorder(new Border(new Extent(2, Extent.PX),
		// new Color(0x80ffff), Border.STYLE_INSET));

		// btnDefault.setRolloverBorder(new Border(new Extent(2, Extent.PX),
		// new Color(0x80ffff), Border.STYLE_INSET));

		btnDefault.setBackground(new Color(0x0080c0));

		btnDefault.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.CENTER));
		btnDefault.setPressedEnabled(true);
		btnDefault.setRolloverEnabled(true);

		return btnDefault;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
