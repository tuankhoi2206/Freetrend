package ds.program.fvhr.baby.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.jaxen.expr.DefaultAbsoluteLocationPath;

import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.list.ListModel;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.ListBox;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Row;
import ds.program.fvhr.baby.tools.BinderSourceToComponent;
import ds.program.fvhr.baby.tools.DepartmentTool;
import ds.program.fvhr.baby.tools.EmployeeTool;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.ui.GetEmployeeInfo;
import dsc.dao.IGenericDAO;
import dsc.dao.IQuery;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscValuedCheckBox;
import nextapp.echo2.app.SelectField;
import dsc.echo2app.component.DscGroupCheckBox;
import fv.util.Vni2Uni;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import echopointng.GroupBox;

public class Search_EmployeeProgram extends WindowPane {

	private static final long serialVersionUID = 1L;
	private ResourceBundle resourceBundle;
	private ListBox list_Employee;
	private RadioButton rad_Empsn;
	private TextField txt_Empsn;
	private RadioButton rad_Dept;
	private SelectField sf_factory;
	private SelectField sf_Group;
	private DscGroupCheckBox G_cbk;
	private DscValuedCheckBox cbk_FV1;
	private DscValuedCheckBox cbk_FV2;
	private DscValuedCheckBox cbk_FV3;
	private DscValuedCheckBox cbk_FV5;
	private DscValuedCheckBox cbk_FV6;
	private SelectField sf_Dept;
	private Button btn_finding;
	private Button btn_reset;

	private List_EmployeeProgram listEmpProgram;

	public Search_EmployeeProgram() {
		super();

		// Add design-time configured components.
		initComponents();
		BinderSourceToComponent.putSourceToSelectField(
				DepartmentTool.GetAllFactory(), sf_factory);
	}

	private void doRefresh(ActionEvent e) {
		txt_Empsn.setText("");
		sf_factory.setSelectedIndex(-1);
		sf_Group.setSelectedIndex(-1);
		sf_Dept.setSelectedIndex(-1);
		list_Employee.setModel(new DefaultListModel());
	}

	private void RadioSelected(ActionEvent e) {
		if (e.getActionCommand().equals("employee") || rad_Empsn.isSelected())
		// ActionCommand được set ở trong phần tạo dao diện Radio
		{
			txt_Empsn.setEnabled(true);
			list_Employee.setEnabled(true);
			sf_factory.setEnabled(false);
			sf_Group.setEnabled(false);
			sf_Dept.setEnabled(false);
			G_cbk.setEnabled(false);
		} else {
			txt_Empsn.setEnabled(false);
			list_Employee.setEnabled(false);
			sf_factory.setEnabled(true);
			sf_Group.setEnabled(true);
			sf_Dept.setEnabled(true);
			G_cbk.setEnabled(false);
		}
	}

	private void doSearch(ActionEvent e) {
		String  whereQuery, orderQuery;

		/*
		 * khi ta khởi tạo from List_EmployeeProgram thì đã tạo mặc định câu
		 * lệnh cho BrowserContent và câu lệnh ấy được chuyển thành dạng 
		 * Select o.[COLUMN] from [CLASS Entity] as o  
		 * cho nên ở đây ta chỉ sử dụng từ
		 * phần where ..... trở đi
		 */

		whereQuery = "o.EMPSN in (Select t.EMPSN from N_EMPLOYEE t,N_DEPARTMENT dt where t.EMPSN = o.EMPSN and dt.ID_DEPT = t.DEPSN ";
		orderQuery = " order by t.EMPSN";
		if (rad_Dept.isSelected()) {
			if (sf_factory.getSelectedIndex() < 0) {
				Application.getApp().showMessageDialog("Lỗi Dữ Liệu",
						"Chọn Xưởng Cần Thao Tác", 1);
				return;
			} else {
				if (sf_factory.getSelectedIndex() > 0) {
					String nameFact = sf_factory.getSelectedItem().toString();
					whereQuery += "and dt.NAME_FACT =  '" + nameFact + "' ";
				}
				if (sf_Group.getSelectedIndex() > 0) {
					String nameGroup = Vni2Uni.convertToVNI(sf_Group
							.getSelectedItem().toString());
					StringBuilder temp = new StringBuilder();
					whereQuery += "and dt.NAME_GROUP  ";
					whereQuery += (nameGroup.equals("OTHER") ? temp
							.append("is null") : temp.append("= '") + nameGroup
							+ "' ").toString();
				}
				if (sf_Dept.getSelectedIndex() > 0) {
					String name_dept = Vni2Uni.convertToVNI(sf_Dept
							.getSelectedItem().toString());
					whereQuery += "and dt.NAME_DEPT_NAME = '" + name_dept + "'";
				}
			}
		}
		if (rad_Empsn.isSelected()) {
			if (list_Employee.getModel().size() <= 0) {
				Application.getApp().showMessageDialog("Lỗi Dữ Liệu",
						"Thêm số thẻ vào danh sách để xử lý", 1);
				return;
			} else {
				StringBuilder list = new StringBuilder("(");
				for (int i = 0; i < list_Employee.getModel().size(); i++) {
					list.append("'").append((list_Employee.getModel().get(i).toString().substring(0, 8)))
							.append("',");
				}
				list.deleteCharAt(list.length() - 1);
				list.append(")");
				whereQuery += " And t.EMPSN in " + list;
			}
		}
		whereQuery+=")";
		N_CHANGE_ICCARD01MProgram.loadbrowserEmployee(whereQuery);

	}

	private void setCheckedGroupSelectBox(boolean bool) {
		for (Object obj : G_cbk.getComponents()) {
			if (obj instanceof CheckBox)
				((CheckBox) obj).setSelected(bool);
		}

	}

	private void radioChangeItem(ActionEvent e) {
		String nameFact = "", nameGroup = "";

		if (e.getActionCommand().equals("factoryChange")) {
			nameFact = sf_factory.getSelectedItem().toString();
			G_cbk.setEnabled(true ? nameFact.equals("FVL") : false);
			setCheckedGroupSelectBox(true ? nameFact.equals("FVL") : false);
			sf_Group.setSelectedIndex(-1);
			sf_Dept.setSelectedIndex(-1);
			BinderSourceToComponent.putSourceToSelectField(
					DepartmentTool.GetGroupByNameFact(nameFact), sf_Group);
		}

		if (e.getActionCommand().equals("groupChange")) {
			nameFact = sf_factory.getSelectedItem().toString();
			nameGroup = sf_Group.getSelectedItem().toString();
			sf_Dept.setSelectedIndex(-1);
			BinderSourceToComponent.putSourceToSelectField(DepartmentTool
					.GetDeptByNameGroupAndNameFact(nameFact, nameGroup),
					sf_Dept);
		}
	}

	private void addToList(ActionEvent e) {
		DefaultListModel listModel = (DefaultListModel) list_Employee
				.getModel();
		String empsn = txt_Empsn.getText();

		if (!empsn.matches("[0-9]{8}") || !EmployeeTool.checkEmpsnExist(empsn)
				|| listModel.indexOf(empsn) >= 0) {
			Application.getApp().showMessageDialog("Lỗi Dữ Liệu",
					"Số thẻ không tồn tại \n hoặc đã có trong danh sách", 1);
			return;
		}
		N_EMPLOYEE obj = EmployeeTool.getInformationEmployee(empsn);
		listModel.add(obj.getEMPSN()+"_"+ Vni2Uni.convertToUnicode(obj.getFNAME()+" "+obj.getLNAME()));
		list_Employee.setModel(listModel);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setTitle("Finder");
		this.setHeight(new Extent(360, Extent.PX));
		this.setDefaultCloseOperation(WindowPane.DISPOSE_ON_CLOSE);
		this.setWidth(new Extent(550, Extent.PX));
		this.setBackground(new Color(0xf2fbff));
		this.setResizable(false);
		this.setModal(true);
		this.setTitleFont(new Font(new Font.Typeface("sans-serif"), Font.ITALIC
				| Font.OVERLINE | Font.UNDERLINE, new Extent(13, Extent.PT)));
		Grid grid1 = new Grid();
		grid1.setSize(2);
		add(grid1);
		Grid grid2 = new Grid();
		grid2.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(10,
				Extent.PX), new Extent(15, Extent.PX), new Extent(0, Extent.PX)));
		grid2.setSize(1);
		grid1.add(grid2);
		Row row1 = new Row();
		grid2.add(row1);
		rad_Empsn = new RadioButton();
		rad_Empsn.setText("Tìm theo số thẻ");
		rad_Empsn.setSelected(true);
		ButtonGroup radgroup = new ButtonGroup();
		rad_Empsn.setGroup(radgroup);
		rad_Empsn.setFont(new Font(new Font.Typeface("sans-serif"),
				Font.UNDERLINE, new Extent(13, Extent.PT)));
		rad_Empsn.setActionCommand("employee");
		rad_Empsn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RadioSelected(e);
			}
		});
		row1.add(rad_Empsn);
		Row row2 = new Row();
		row2.setInsets(new Insets(new Extent(20, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		GridLayoutData row2LayoutData = new GridLayoutData();
		row2LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(10, Extent.PX), new Extent(0, Extent.PX),
				new Extent(10, Extent.PX)));
		row2.setLayoutData(row2LayoutData);
		grid2.add(row2);
		Label label1 = new Label();
		label1.setText("Số thẻ: ");
		row2.add(label1);
		txt_Empsn = new TextField();
		txt_Empsn.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txt_Empsn.setWidth(new Extent(80, Extent.PX));
		txt_Empsn.setDisabledBackground(new Color(0xe8f8ff));
		txt_Empsn.setToolTipText("Số thẻ đeo");
		txt_Empsn.setMaximumLength(8);
		txt_Empsn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addToList(e);
			}
		});
		row2.add(txt_Empsn);
		Row row3 = new Row();
		grid2.add(row3);
		rad_Dept = new RadioButton();
		rad_Dept.setText("Tìm theo đơn vị");
		rad_Dept.setGroup(radgroup);
		rad_Dept.setFont(new Font(new Font.Typeface("sans-serif"),
				Font.UNDERLINE, new Extent(13, Extent.PT)));
		rad_Dept.setActionCommand("department");
		rad_Dept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RadioSelected(e);
			}
		});
		row3.add(rad_Dept);
		Row row4 = new Row();
		row4.setInsets(new Insets(new Extent(20, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		GridLayoutData row4LayoutData = new GridLayoutData();
		row4LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(10, Extent.PX)));
		row4.setLayoutData(row4LayoutData);
		grid2.add(row4);
		Label label2 = new Label();
		label2.setLineWrap(false);
		label2.setText("Xưởng: ");
		row4.add(label2);
		sf_factory = new SelectField();
		sf_factory.setWidth(new Extent(80, Extent.PX));
		sf_factory.setActionCommand("factoryChange");
		sf_factory.setDisabledBackground(new Color(0xe6f7ff));
		sf_factory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioChangeItem(e);
			}
		});
		row4.add(sf_factory);
		Row row6 = new Row();
		row6.setInsets(new Insets(new Extent(20, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		grid2.add(row6);
		G_cbk = new DscGroupCheckBox();
		G_cbk.setSize(3);
		row6.add(G_cbk);
		cbk_FV1 = new DscValuedCheckBox();
		cbk_FV1.setText("FV1");
		G_cbk.add(cbk_FV1);
		cbk_FV2 = new DscValuedCheckBox();
		cbk_FV2.setText("FV2");
		G_cbk.add(cbk_FV2);
		cbk_FV3 = new DscValuedCheckBox();
		cbk_FV3.setText("FV3");
		G_cbk.add(cbk_FV3);
		cbk_FV5 = new DscValuedCheckBox();
		cbk_FV5.setText("FV5");
		G_cbk.add(cbk_FV5);
		cbk_FV6 = new DscValuedCheckBox();
		cbk_FV6.setText("FV6");
		G_cbk.add(cbk_FV6);
		DscValuedCheckBox cbk_Other = new DscValuedCheckBox();
		cbk_Other.setText("Khác");
		G_cbk.add(cbk_Other);
		Row row5 = new Row();
		row5.setAlignment(new Alignment(Alignment.LEFT, Alignment.DEFAULT));
		row5.setInsets(new Insets(new Extent(20, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		GridLayoutData row5LayoutData = new GridLayoutData();
		row5LayoutData.setInsets(new Insets(new Extent(5, Extent.PX),
				new Extent(5, Extent.PX)));
		row5.setLayoutData(row5LayoutData);
		grid2.add(row5);
		Label label3 = new Label();
		label3.setText("Nhóm: ");
		row5.add(label3);
		sf_Group = new SelectField();
		sf_Group.setWidth(new Extent(80, Extent.PX));
		sf_Group.setActionCommand("groupChange");
		sf_Group.setDisabledBackground(new Color(0xf0faff));
		sf_Group.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioChangeItem(e);
			}
		});
		row5.add(sf_Group);
		Row row7 = new Row();
		row7.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		grid2.add(row7);
		Label label4 = new Label();
		label4.setLineWrap(false);
		label4.setText("Đơn vị: ");
		row7.add(label4);
		sf_Dept = new SelectField();
		sf_Dept.setWidth(new Extent(120, Extent.PX));
		sf_Dept.setDisabledBackground(new Color(0xe6f7ff));
		row7.add(sf_Dept);
		Row row8 = new Row();
		row8.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(15,
				Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		GridLayoutData row8LayoutData = new GridLayoutData();
		row8LayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		row8.setLayoutData(row8LayoutData);
		grid2.add(row8);
		btn_finding = new Button();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnSearch.png");
		btn_finding.setIcon(imageReference1);
		btn_finding.setText("Search");
		btn_finding.setFont(new Font(new Font.Typeface("monospace"), Font.BOLD
				| Font.UNDERLINE, new Extent(12, Extent.PT)));
		btn_finding.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(
				0, Extent.PX), new Extent(10, Extent.PX), new Extent(0,
				Extent.PX)));
		btn_finding.setActionCommand("finding");
		btn_finding.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSearch(e);
			}
		});
		row8.add(btn_finding);
		btn_reset = new Button();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnReset.png");
		btn_reset.setIcon(imageReference2);
		btn_reset.setText("Reset");
		btn_reset.setFont(new Font(new Font.Typeface("monospace"), Font.BOLD
				| Font.UNDERLINE, new Extent(12, Extent.PT)));
		btn_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doRefresh(e);
			}
		});
		row8.add(btn_reset);
		Grid grid3 = new Grid();
		grid3.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(10,
				Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		grid3.setSize(1);
		grid1.add(grid3);
		list_Employee = new ListBox();
		list_Employee.setHeight(new Extent(300, Extent.PX));
		list_Employee.setWidth(new Extent(300, Extent.PX));
		list_Employee.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		list_Employee.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x8ac5ff), Border.STYLE_SOLID));
		list_Employee.setDisabledBackground(new Color(0xe8f8ff));
		GridLayoutData list_EmployeeLayoutData = new GridLayoutData();
		list_EmployeeLayoutData.setColumnSpan(2);
		list_Employee.setLayoutData(list_EmployeeLayoutData);
		grid3.add(list_Employee);
	}
}
