package ds.program.fvhr.ui.workpoints;

import java.util.Calendar;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.RadioButton;
import ds.program.fvhr.dao.generic.FvGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscGroupCheckBox;
import fv.components.SelectItem;
import fv.util.ApplicationHelper;
import fv.util.FVGenericInfo;
import fv.util.HRUtils;
import fv.util.ListBinder;
import fv.util.MappingPropertyUtils;
import nextapp.echo2.app.ListBox;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.Border;

public class CheckDataSearchPane extends WindowPane {

	private ResourceBundle resourceBundle;
	private SelectField sfFrom;
	private SelectField sfTo;
	private CheckBox chkAllData;
	private SelectField sfMonth;
	private SelectField sfYear;
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
	private ListBox lstEmpsn;
	private Button btnSearch;
	private Button btnReset;

	/**
	 * Creates a new <code>CheckDataSearchPane</code>.
	 */
	public CheckDataSearchPane() {
		super();

		// Add design-time configured components.
		initComponents();
		initUI();
	}
	
	private void initUI(){
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		ListBinder.bindSelectField(sfFrom, MappingPropertyUtils
				.getDayEditor(month), true);
		sfFrom.setSelectedIndex(c.get(Calendar.DAY_OF_MONTH) - 1);
		ListBinder.bindSelectField(sfTo, MappingPropertyUtils
				.getDayEditor(month), true);
		sfTo.setSelectedIndex(c.get(Calendar.DAY_OF_MONTH) - 1);
		ListBinder.bindSelectField(sfMonth, MappingPropertyUtils
				.getJavaMonthEditor(), true);

		sfMonth.setSelectedIndex(month);
		ListBinder.bindSelectField(sfYear, MappingPropertyUtils.getYearEditor(
				1, false), true);
		sfYear.setSelectedIndex(1);
		ListBinder.bindSelectField(sfFact, FVGenericInfo.getFactories(), false);
		sfFact.setEnabled(false);

		sfMonth.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				ListBinder.bindSelectField(sfFrom, MappingPropertyUtils
						.getDayEditor(sfMonth.getSelectedIndex()), true);
				sfFrom.setSelectedIndex(0);
				ListBinder.bindSelectField(sfTo, MappingPropertyUtils
						.getDayEditor(sfMonth.getSelectedIndex()), true);
				sfTo.setSelectedIndex(sfTo.getModel().size()-1);
			}
		});
		sfTo.setEnabled(false);
	}

	private void resetForm(ActionEvent e) {
		txtEmpsn.setText("");
		lstEmpsn.getSelectionModel().clearSelection();
		if (lstEmpsn.getModel().size()>0)
			((DefaultListModel)lstEmpsn.getModel()).removeAll();
		sfFact.setSelectedIndex(-1);
		for (int i=0;i<groupFVL.getComponentCount();i++){
			CheckBox chk = (CheckBox) groupFVL.getComponent(i);
			chk.setSelected(false);
		}
		groupFVL.setEnabled(false);
		sfLean.setSelectedIndex(-1);
		sfDept.setSelectedIndex(-1);
		((DefaultListModel)sfLean.getModel()).removeAll();
		((DefaultListModel)sfDept.getModel()).removeAll();
		sfMonth.setEnabled(true);
		sfYear.setEnabled(true);
	}

	protected void buttonAction(ActionEvent e) {
		if (sfTo.getSelectedIndex()<=sfFrom.getSelectedIndex()){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
			return;
		}
		if (radDept.isSelected()&&sfFact.getSelectedIndex()<0){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Chọn xưởng cần thao tác");
			return;
		}
		if (radEmpsn.isSelected()&&lstEmpsn.getModel().size()<=0){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Nhập số thẻ");
			return;
		}
		
		doSearch();
		
		this.userClose();
	}

	private void doSearch() {
	}

	private void sfFactChanged(ActionEvent e) {
		SelectItem item = (SelectItem) sfFact.getSelectedItem();
		if (item.getValue().equals("FVL"))
			groupFVL.setEnabled(true);
		else
			groupFVL.setEnabled(false);
		ListBinder.bindSelectField(sfLean, FVGenericInfo.getGroup(item.getValue()), true);
		ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue()), true);
	}

	private void sfLeanChanged(ActionEvent e) {
		SelectItem item = (SelectItem) sfFact.getSelectedItem();
		SelectItem litem = (SelectItem) sfLean.getSelectedItem();
		ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue(),litem.getValue()), true);
	}

	private void addToList(ActionEvent e) {
		String empsn = txtEmpsn.getText();
		if (!empsn.matches("[0-9]{8}")){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ không hợp lệ");
			return;
		}
		DefaultListModel model = (DefaultListModel) lstEmpsn.getModel();
		for (int i=0;i<model.size();i++){
			SelectItem item = (SelectItem) model.get(i);
			if (item.getValue().equals(empsn)){
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ đã có trong danh sách");
				return;
			}
		}
//		if (model.indexOf(empsn)>=0){
//			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ đã có trong danh sách");
//			return;
//		}

		HRUtils util = ApplicationHelper.getHRUtils();
		if (!util.isWorkingOrQuit(empsn)){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Số thẻ không có trong hệ thống");
			return;
		}
		//check permission
		if (!util.getPermissionValidator().hasEmpsnPermission(empsn)){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Anh/chị không có quyền thao tác số thẻ này");
			return;
		}

		String empna = FvGenericDAO.getInstanse().getFullName(empsn);
		SelectItem item = new SelectItem(empsn + "_" + empna, empsn);
		model.add(item);
		
		if (model.size()>0) {
			sfMonth.setEnabled(false);
			sfYear.setEnabled(false);
		}
		txtEmpsn.requestFocus();
	}

	private void empGroupSelected(ActionEvent e) {
		if (e.getActionCommand().equals("cmd_emp")){
			txtEmpsn.setEnabled(true);
			lstEmpsn.setEnabled(true);
			sfFact.setEnabled(false);
			sfLean.setEnabled(false);
			sfDept.setEnabled(false);
			groupFVL.setEnabled(false);
		} else {
			txtEmpsn.setEnabled(false);
			lstEmpsn.setEnabled(false);
			sfFact.setEnabled(true);
			sfLean.setEnabled(true);
			sfDept.setEnabled(true);
			if ("FVL".equals(ListBinder.get(sfFact))){
				groupFVL.setEnabled(true);
			}
		}
	}
	
	private String getGroupFVLConditionString(){
		String infvl = "";
		String fact = ListBinder.get(sfFact).toString();
		if (fact.equals("FVL")) {
			int chkCount = 0;
			CheckBox chkOther = (CheckBox) groupFVL.getComponent(5);
			if (chkOther.isSelected()) {
				chkCount++;
				CheckBox chk1 = (CheckBox) groupFVL.getComponent(0);
				if (!chk1.isSelected()) {
					infvl = "and (d.name_group not like 'F1%' or d.name_group like 'F12%') ";
				} else
					chkCount++;
				for (int i = 1; i < groupFVL.getComponentCount() - 1; i++) {
					CheckBox chk = (CheckBox) groupFVL.getComponent(i);
					if (!chk.isSelected()) {
						int f=i+1;
						if (i>=3)f=i+2;
						infvl = infvl + "and d.name_group not like 'F" + f + "%' ";
					} else {
						chkCount++;
					}
				}
				if (chkCount != 6)
					infvl = "and ("
						+ StringUtils.substringAfter(infvl, "and ")
						+ ")";
				else
					infvl = "";
			} else {
				CheckBox chk1 = (CheckBox) groupFVL.getComponent(0);
				if (chk1.isSelected()) {
					infvl = "or (d.name_group like 'F1%' and d.name_group not like 'F12%')";
				}
				for (int i = 1; i < groupFVL.getComponentCount() - 1; i++) {
					CheckBox chk = (CheckBox) groupFVL.getComponent(i);
					if (chk.isSelected()) {
						int f=i+1;
						if (i>=3)f=i+2;
						infvl = infvl + " or d.name_group like 'F" + f + "%'";
					}
				}
				if (!infvl.equals("")) {
					infvl = " and ("
						+ StringUtils.substringAfter(infvl, "or ")
						+ ")";
				}
			}
		}
		return infvl;
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setTitle("Tìm kiếm tổng ngày công");
		this.setHeight(new Extent(400, Extent.PX));
		this.setWidth(new Extent(550, Extent.PX));
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(310, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		add(splitPane1);
		ContentPane contentPane3 = new ContentPane();
		splitPane1.add(contentPane3);
		SplitPane splitPane3 = new SplitPane();
		splitPane3.setSeparatorPosition(new Extent(50, Extent.PX));
		splitPane3.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		contentPane3.add(splitPane3);
		Grid grid3 = new Grid();
		grid3.setSize(1);
		splitPane3.add(grid3);
		Row row3 = new Row();
		grid3.add(row3);
		Label label10 = new Label();
		label10.setText("Từ ngày");
		RowLayoutData label10LayoutData = new RowLayoutData();
		label10LayoutData.setInsets(new Insets(new Extent(14, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		label10.setLayoutData(label10LayoutData);
		row3.add(label10);
		sfFrom = new SelectField();
		row3.add(sfFrom);
		Label label8 = new Label();
		label8.setText("Đến ngày");
		row3.add(label8);
		sfTo = new SelectField();
		sfTo.setDisabledBackground(new Color(0xc0c0c0));
		row3.add(sfTo);
		chkAllData = new CheckBox();
		chkAllData.setText("Bao gồm dữ liệu hợp lệ");
		row3.add(chkAllData);
		Row row2 = new Row();
		grid3.add(row2);
		Label label1 = new Label();
		label1.setText("Tháng: ");
		row2.add(label1);
		sfMonth = new SelectField();
		row2.add(sfMonth);
		Label label5 = new Label();
		label5.setText("Năm: ");
		row2.add(label5);
		sfYear = new SelectField();
		row2.add(sfYear);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(3, Extent.PX)));
		grid1.setSize(2);
		splitPane3.add(grid1);
		Grid grid2 = new Grid();
		grid2.setInsets(new Insets(new Extent(4, Extent.PX)));
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
		grid2.add(sfFact);
		Label label7 = new Label();
		grid2.add(label7);
		groupFVL = new DscGroupCheckBox();
		groupFVL.setEnabled(false);
		groupFVL.setSize(3);
		grid2.add(groupFVL);
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
		lstEmpsn = new ListBox();
		lstEmpsn.setHeight(new Extent(210, Extent.PX));
		lstEmpsn.setWidth(new Extent(230, Extent.PX));
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
		row1LayoutData.setBackground(new Color(0xc0c0c0));
		row1.setLayoutData(row1LayoutData);
		splitPane1.add(row1);
		btnSearch = new Button();
		btnSearch.setText("Tìm kiếm");
		btnSearch.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnSearch.setActionCommand("find");
		btnSearch.setRolloverBackground(new Color(0x0080ff));
		btnSearch.setBorder(new Border(new Extent(1, Extent.PX), Color.BLACK,
				Border.STYLE_SOLID));
		btnSearch.setRolloverForeground(new Color(0xff8000));
		btnSearch.setRolloverEnabled(true);
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonAction(e);
			}
		});
		row1.add(btnSearch);
		btnReset = new Button();
		btnReset.setText("Reset");
		btnReset.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnReset.setActionCommand("reset");
		btnReset.setRolloverBackground(new Color(0x0080ff));
		btnReset.setBorder(new Border(new Extent(1, Extent.PX), Color.BLACK,
				Border.STYLE_SOLID));
		btnReset.setRolloverForeground(new Color(0xff8000));
		btnReset.setRolloverEnabled(true);
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetForm(e);
			}
		});
		row1.add(btnReset);
	}
}
