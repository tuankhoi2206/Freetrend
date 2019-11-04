package ds.program.fvhr.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.list.DefaultListModel;
import ds.program.fvhr.domain.N_N_EMP_BORM;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DefaultProgram;

public class N_EMP_BORMMProgram extends DefaultProgram {

	private Grid rootLayout;

	private TextField txt_sothe;

	private SelectField sf_fact;

	private Button bt_tamung;

	private SelectField sf_group;

	private CheckBox checked_tamung;

	private SelectField sf_dept;

	private Button bt_exportE;

	SimpleDateFormat sf = OBJ_UTILITY.Get_format_date();

	OBJ_UTILITY obj_util;

	public static String name_fact = "";

	public static String name_group = "";

	public static String name_dept = "";

	public static boolean status_advance = true;

	public N_EMP_BORMMProgram() {

		Date date = new Date();
		obj_util = new OBJ_UTILITY();

		InitComponent();

		bt_exportE.setEnabled(false);

		if (date.getDate() >= 11 && date.getDate() < 24) {

			bt_exportE.setEnabled(true);

		}

	}

	private void InitComponent() {
		rootLayout = new Grid();
		rootLayout.setSize(5);
		rootLayout.setColumnWidth(2, new Extent(100));
		rootLayout.setRowHeight(2, new Extent(50));
		rootLayout.setRowHeight(0, new Extent(50));

		this.add(rootLayout);

		// -----------------------------
		Label lb_sothe = new Label();

		lb_sothe.setText("So the:    ");
		rootLayout.add(lb_sothe);

		txt_sothe = new TextField();
		txt_sothe.setMaximumLength(8);
		txt_sothe.setWidth(new Extent(155));
		rootLayout.add(txt_sothe);

		Label lb_text3 = new Label();
		lb_text3.setText(" ");

		rootLayout.add(lb_text3);

		Label lb_xuong = new Label();
		lb_xuong.setText("Xuong: ");
		rootLayout.add(lb_xuong);

		sf_fact = new SelectField();
		sf_fact.setWidth(new Extent(200));
		sf_fact.setModel(obj_util.Get_Model_Fact());
		// sf_fact.setSelectedIndex(0);

		sf_fact.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doAction_fact(e);
			}
		});
		rootLayout.add(sf_fact);

		// --------------------------------
		Label lb_tamung = new Label();
		rootLayout.setRowHeight(1, new Extent(20));
		lb_tamung.setText(" ");
		rootLayout.add(lb_tamung);

		Label lb_text2 = new Label();
		lb_text2.setText(" ");
		rootLayout.add(lb_text2);

		Label lb_text1 = new Label();
		rootLayout.setColumnWidth(1, new Extent(100));
		lb_text1.setText(" ");
		rootLayout.add(lb_text1);

		Label lb_nhom = new Label();
		lb_nhom.setText("Nhom:");
		rootLayout.add(lb_nhom);

		sf_group = new SelectField();
		sf_group.setWidth(new Extent(200));
		// sf_group.setSelectedIndex(0);
		sf_group.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				doAction_group();
			}
		});

		sf_group.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				doAction_group();
			}
		});
		rootLayout.add(sf_group);

		// ------------------------------
		Label lb_text5 = new Label();
		lb_text5.setText(" ");
		rootLayout.add(lb_text5);

		checked_tamung = new CheckBox();
		checked_tamung.setText("Not salary advance");
		checked_tamung.setSelected(true);
		rootLayout.add(checked_tamung);

		Label lb_text7 = new Label();
		lb_text7.setText(" ");
		rootLayout.add(lb_text7);

		Label lb_donvi = new Label();
		rootLayout.add(lb_donvi);
		lb_donvi.setText("Don vi:");

		sf_dept = new SelectField();
		// sf_dept.setSelectedIndex(0);
		sf_dept.setWidth(new Extent(200));
		rootLayout.add(sf_dept);

		rootLayout.setRowHeight(4, new Extent(50));

		// --------------------------------
		Label lb_text6 = new Label();
		lb_text6.setText(" ");
		rootLayout.add(lb_text6);

		bt_tamung = new Button();
		bt_tamung.setText("Save");
		bt_tamung.setStyleName("Default.ToolbarButton");
		bt_tamung.setWidth(new Extent(50));
		bt_tamung.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doAction_btn_tamung();
			}
		});

		rootLayout.add(bt_tamung);

		Label lb_text4 = new Label();
		lb_text4.setText(" ");
		rootLayout.add(lb_text4);

		Label lb_text8 = new Label();
		lb_text8.setText(" ");
		rootLayout.add(lb_text8);

		bt_exportE = new Button();
		bt_exportE.setText("ExportExcel");
		bt_exportE.setStyleName("Default.ToolbarButton");
		bt_exportE.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doAction_bnt_exportExcel();
			}
		});
		rootLayout.add(bt_exportE);

	}

	protected void doAction_bnt_exportExcel() {

		// String taskIDClass = "SalaryAdvance_R";

		if (sf_fact.getSelectedIndex() != 0 && sf_fact.getSelectedIndex() != -1) {
			name_fact = (String) sf_fact.getSelectedItem();
		} else {
			name_fact = "";
		}
		if (sf_group.getSelectedIndex() != 0
				&& sf_group.getSelectedIndex() != -1) {
			name_group = (String) sf_group.getSelectedItem();
		} else {
			name_group = "";
		}
		if (sf_dept.getSelectedIndex() != 0 && sf_dept.getSelectedIndex() != -1) {
			name_dept = (String) sf_dept.getSelectedItem();
		} else {
			name_dept = "";
		}

		status_advance = checked_tamung.isSelected();

		obj_util.doExport("SalaryAdvance_R","SalaryAdvance");

	}

	protected void doAction_btn_tamung() {
		N_N_EMP_BORM obj_update = new N_N_EMP_BORM();
		IGenericDAO<N_N_EMP_BORM, String> obj_dao = Application.getApp()
				.getDao(N_N_EMP_BORM.class);
		if (checked_tamung.isSelected()) {
			obj_update.setEMPSN(txt_sothe.getText());
			obj_update.setSTATUS("N");
		} else {
			obj_update.setEMPSN(txt_sothe.getText());
			obj_update.setSTATUS("Y");
		}
		try {
			obj_dao.saveOrUpdate(obj_update);
			OBJ_UTILITY.ShowMessageOK("Update Complete !");
		} catch (Exception e) {
			// TODO: handle exception
			OBJ_UTILITY.ShowMessageError("Update Fail !");
		}
	}

	protected void doAction_fact(ActionEvent e) {
		String indexItem = "";
		if (sf_fact.getSelectedIndex() != 0 && sf_fact.getSelectedIndex() != -1) {
			indexItem = (String) sf_fact.getSelectedItem(); // lay gia tri index
															// cua selectfield
															// xuong
			DefaultListModel model_group = obj_util.Get_Model_Group(indexItem);

			sf_group.setSelectedIndex(0);
			sf_group.setModel(model_group);

		} else {
			sf_group.setSelectedIndex(0);
			sf_dept.setSelectedIndex(0);
			sf_group.setModel(new DefaultListModel(new String[] { "" }));
			sf_dept.setModel(new DefaultListModel(new String[] { "" }));

		}

	}

	protected void doAction_group() {
		String index1 = "";
		String index2 = "";
		if (sf_fact.getSelectedIndex() != 0 && sf_fact.getSelectedIndex() != -1
				&& sf_group.getSelectedIndex() != 0
				&& sf_group.getSelectedIndex() != -1) {
			index1 = (String) sf_fact.getSelectedItem();
			index2 = (String) sf_group.getSelectedItem();
			if (index1 == null)
				index1 = "";
			if (index2 == null) {
				index2 = "";
			}
			sf_dept.setSelectedIndex(0);
			sf_dept.setModel(obj_util.Get_Model_Dept(index1, index2));

		} else {
			sf_dept.setSelectedIndex(0);
			sf_dept.setModel(new DefaultListModel(new String[] { "" }));
		}

	}
}
