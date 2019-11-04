package ds.program.fvhr.baby.ui.register_Shift;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ListBox;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import ds.program.fvhr.dao.generic.FvGenericDAO;
import ds.program.fvhr.domain.N_EMP_INFO;
import ds.program.fvhr.domain.N_REGISTER_SHIFT;
import ds.program.fvhr.ngan.ui.reg_overtime.N_REGISTER_OVERTIMEMProgram;
import ds.program.fvhr.ui.training.TrainingDetail01MProgram;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscGroupCheckBox;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;
import fv.components.SelectItem;
import fv.util.ApplicationHelper;
import fv.util.DbUtils;
import fv.util.FVGenericInfo;
import fv.util.HRUtils;
import fv.util.ListBinder;

public class REGISTER_SHIFTQuery extends QueryNormal2 {
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
		private Button btnReset;

		/**
		 * Creates a new <code>TrainingDetail01MQuery</code>.
		 */
		public REGISTER_SHIFTQuery() {
			super();

			// Add design-time configured components.\
			initComponents();
			ListBinder.bindSelectField(sfFact, FVGenericInfo.getFactories(), false);
			sfFact.setEnabled(false);
			
		}

		@Override
		protected int doInit() {
			int ret = super.doInit();
			
			return ret;

		}
		
		@Override
		public REGISTER_SHIFTProgram getProgram() {
			return (REGISTER_SHIFTProgram) super.getProgram();
		}
		
		@Override
		protected void doQuery() {
			if (radDept.isSelected()){
				if (sfFact.getSelectedIndex()<0){
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Chọn xưởng cần thao tác");
					return;
				}
				if (sfFact.getSelectedItem().toString().equals("FVL")){
					if (sfLean.getSelectedIndex()<0&&sfDept.getSelectedIndex()<0&&(groupFVL.getSelectedItem()==null||groupFVL.getSelectedItem().length<0)){
						Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Chọn lean hoặc đơn vị");
						return;
					}
				}
			}
			if (radEmpsn.isSelected()&&lstEmpsn.getModel().size()<=0){
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Nhập số thẻ");
				return;
			}
			
			if (radEmpsn.isSelected()){
				DefaultListModel model = (DefaultListModel) lstEmpsn.getModel();
				List<String> list = new ArrayList<String>();
				for (int i=0;i<model.size();i++){
					//list.add(model.get(i).toString());
					SelectItem item = (SelectItem) model.get(i);
					list.add(item.getValue().toString());
				}
				String fc = "o.EMPSN in " + DbUtils.parseInStringParamValues(list);
				ProgramCondition pc = new ProgramCondition(fc);
				getProgram().doMasterQuery(pc);
			}else{
				String fact = ListBinder.get(sfFact).toString();
				String infvl = getGroupFVLConditionString();
				String q;
				q = "D.NAME_FACT='" + fact + "'";
				if (infvl.equals("")){
					if (sfLean.getSelectedIndex()>=0){
						q = q + " AND D.NAME_GROUP='" + ListBinder.get(sfLean).toString() + "'";
					}
					if (sfDept.getSelectedIndex()>=0){
						q = q + " AND D.NAME_DEPT_NAME='" + ListBinder.get(sfDept).toString() + "'";
					}
				}else{
					q = q + infvl;
				}
//				String fc = "o.MONTHS=? and o.YEARS=? and o.DEPSN in (select d.ID_DEPT FROM N_DEPARTMENT d WHERE d.ID_DEPT=o.DEPSN and " + q + ")";
				String sql = 
					"o.DEPSN in " +
					"(SELECT D.ID_DEPT FROM N_DEPARTMENT D WHERE o.DEPSN=D.ID_DEPT AND " + q + ")";
				ProgramCondition pc;
				pc = new ProgramCondition(sql);
				getProgram().doMasterQuery(pc);
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
						infvl = "AND (D.NAME_GROUP NOT LIKE 'F1%' OR D.NAME_GROUP LIKE 'F12%') ";
					} else
						chkCount++;
					
					for (int i = 1; i < groupFVL.getComponentCount() - 1; i++) {
						CheckBox chk = (CheckBox) groupFVL.getComponent(i);
						if (!chk.isSelected()) {
							infvl = infvl + "AND D.NAME_GROUP NOT LIKE 'F" + (i + 1) + "%' ";
						} else {
							chkCount++;
						}
					}
					if (chkCount != 6)
						infvl = "AND ("
								+ StringUtils.substringAfter(infvl, "AND ")
								+ ")";
					else
						infvl = "";
				}
				
				
				else {
					CheckBox chk1 = (CheckBox) groupFVL.getComponent(0);
					if (chk1.isSelected()) {
						infvl = "OR (D.NAME_GROUP LIKE 'F1%' AND D.NAME_GROUP NOT LIKE 'F12%')";
					}
					for (int i = 1; i < groupFVL.getComponentCount() - 1; i++) {
						CheckBox chk = (CheckBox) groupFVL.getComponent(i);
						if (chk.isSelected()) {
							infvl = infvl + " OR D.NAME_GROUP LIKE 'F" + (i + 1) + "%'";
						}
					}
					if (!infvl.equals("")) {
						infvl = "AND ("
								+ StringUtils.substringAfter(infvl, "OR ")
								+ ")";
					}
				}
			}
			return infvl;
		}

		protected void sfLeanChanged(ActionEvent e) {
			SelectItem item = (SelectItem) sfFact.getSelectedItem();
			SelectItem litem = (SelectItem) sfLean.getSelectedItem();
			ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue(),litem.getValue()), true);
		}

		protected void sfFactChanged(ActionEvent e) {
			SelectItem item = (SelectItem) sfFact.getSelectedItem();
			if (item.getValue().equals("FVL"))
				groupFVL.setEnabled(true);
			else
				groupFVL.setEnabled(false);
			ListBinder.bindSelectField(sfLean, FVGenericInfo.getGroup(item.getValue()), true);
			ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue()), true);
		}

		protected void addToList(ActionEvent e) {
			String empsn = txtEmpsn.getText();
			if (!empsn.matches("[0-9]{8}")){
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ không hợp lệ");
				return;
			}
			DefaultListModel model = (DefaultListModel) lstEmpsn.getModel();
			if (model.indexOf(empsn)>=0){
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ đã có trong danh sách");
				return;
			}
			
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
			txtEmpsn.requestFocus();
		}

		protected void empGroupSelected(ActionEvent e) {
			if (e.getActionCommand().equals("cmd_emp")){
				txtEmpsn.setEnabled(true);
				lstEmpsn.setEnabled(true);
				sfFact.setEnabled(false);
				sfLean.setEnabled(false);
				sfDept.setEnabled(false);
			} else {
				txtEmpsn.setEnabled(false);
				lstEmpsn.setEnabled(false);
				sfFact.setEnabled(true);
				sfLean.setEnabled(true);
				sfDept.setEnabled(true);
			}
		}

		private void resetForm(ActionEvent e) {
			txtEmpsn.setText("");
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
			txtEmpsn.setMaximumLength(8);
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
			Label label1 = new Label();
			grid2.add(label1);
			btnReset = new Button();
			ResourceImageReference imageReference1 = new ResourceImageReference(
					"/dsc/echo2app/resource/image/btnRefresh.gif");
			btnReset.setIcon(imageReference1);
			btnReset.setHeight(new Extent(16, Extent.PX));
			btnReset.setWidth(new Extent(16, Extent.PX));
			btnReset.setToolTipText("Reset");
			btnReset.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					resetForm(e);
				}
			});
			grid2.add(btnReset);
			lstEmpsn = new ListBox();
			lstEmpsn.setHeight(new Extent(200, Extent.PX));
			lstEmpsn.setWidth(new Extent(250, Extent.PX));
			lstEmpsn.setDisabledBackground(new Color(0xc0c0c0));
			GridLayoutData lstEmpsnLayoutData = new GridLayoutData();
			lstEmpsnLayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
					Alignment.TOP));
			lstEmpsnLayoutData.setRowSpan(8);
			lstEmpsn.setLayoutData(lstEmpsnLayoutData);
			grid1.add(lstEmpsn);
		}
}
