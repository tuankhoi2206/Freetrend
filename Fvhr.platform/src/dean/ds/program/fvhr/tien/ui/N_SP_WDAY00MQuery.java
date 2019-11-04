package ds.program.fvhr.tien.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;

import org.apache.commons.lang.StringUtils;

import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import fv.components.SelectItem;
import fv.util.ApplicationHelper;
import fv.util.FVGenericInfo;
import fv.util.HRUtils;
import fv.util.ListBinder;
import fv.util.Vni2Uni;


public class N_SP_WDAY00MQuery extends QueryNormal2 {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Grid rootLayout2;
	private ResourceBundle resourceBundle;
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	private Label lblEmpsn;
	private DscField txtempsn;
	private SelectField sfDept;
	private SelectField sfGroup_dept;
	private SelectField sfFactory;
	private DscDateField dscDateField1;
	private Label label3;
	private Row row7;
	private CheckBox checkBox1;
	/**
	 * Creates a new <code>N_SP_WDAY00MQuery</code>.
	 */
	public N_SP_WDAY00MQuery() {
		super();
		// Add design-time configured components.
		initComponents();
		ListBinder.bindSelectField(sfFactory, FVGenericInfo.getFactories(), false);
		sfFactory.setEnabled(false);
		dscDateField1.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dscDateField1.setSelectedDate(Calendar.getInstance());	
		
	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		ConditionManager conditionMgr = new ConditionManager();
		setConditionMgr(conditionMgr);

	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor

		//<一般查詢條件定義>

        return ret;
	}	
	@Override
	protected void doQuery()
	
	{	String empsn =txtempsn.getText();
		//HRUtils util = ApplicationHelper.getHRUtils();
		//if (!util.getPermissionValidator().hasEmpsnPermission(empsn)){
			//Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Anh/chị không có quyền thao tác dữ liệu này");
			//return;
		//}
		//else
		//{
		
				List<Object> params = new ArrayList<Object>();
				String hsql = "";		
				if(radioButton1.isSelected())
				{
					if (!empsn.matches("[0-9]{8}")){
						Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ không hợp lệ");
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
					txtempsn.requestFocus();
					hsql = hsql+" and o.EMPSN =?";		
					params.add(empsn);
					hsql = StringUtils.substringAfter(hsql, " and ");
					ProgramCondition pc = new ProgramCondition(hsql, params.toArray());			
					getProgram().setQueryCondition(pc);
					getProgram().refresh();
				}
				if (radioButton2.isSelected()){
					
						if (sfFactory.getSelectedIndex()<0){
						Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Chọn xưởng cần thao tác");
						return;
							}		
						if (sfGroup_dept.getSelectedIndex()<0){
							Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Chọn lean cần thao tác ");
							return;
							}
						hsql= hsql +" and o.EMPSN in (select E.EMPSN from N_EMPLOYEE E,N_DEPARTMENT D  where E.DEPSN=D.ID_DEPT";
						
						if(sfFactory.getSelectedIndex()>=0)
						{
							hsql=hsql+" and D.NAME_FACT=?";
							String fact = ListBinder.get(sfFactory).toString();					
							params.add(fact);
						}
						
						if(sfGroup_dept.getSelectedIndex()>=0)
						{
							hsql=hsql+" and D.NAME_GROUP=?";
							String Group_Dept=Vni2Uni.convertToVNI(ListBinder.get(sfGroup_dept).toString());
							params.add(Group_Dept);
						}
						if(sfDept.getSelectedIndex()>=0)
						{  hsql=hsql+" and D.NAME_DEPT_NAME=?";
							
							String id_Dept=Vni2Uni.convertToVNI(ListBinder.get(sfDept).toString());
							params.add(id_Dept);
						
							
							
						}
						hsql=hsql+")";
						if(dscDateField1.isEnabled()==true)
						{				
							hsql=hsql +" and o.DATE_SP=?";
							Date dataSp=dscDateField1.getSelectedDate().getTime();
							params.add(dataSp);			
							
						}
						hsql = StringUtils.substringAfter(hsql, " and ");
						ProgramCondition pc = new ProgramCondition(hsql, params.toArray());			
						getProgram().setQueryCondition(pc);
						getProgram().refresh();	
				}
		//}
	}

	private void doEmpsn(ActionEvent e) {
		String empsn =txtempsn.getText();
		if (!empsn.matches("[0-9]{8}")){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ không hợp lệ");
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
		txtempsn.requestFocus();
	}

	private void empGroupSelected(ActionEvent e) {
		//TODO Implement.
		if(e.getActionCommand().equals("cmd_emp"))
		{
			txtempsn.setEnabled(true);		
			sfFactory.setEnabled(false);
			sfGroup_dept.setEnabled(false);
			sfDept.setEnabled(false);
			checkBox1.setEnabled(false);	
			checkBox1.setSelected(false);
			dscDateField1.setEnabled(false);
		}
		else
		{
			txtempsn.setEnabled(false);			
			sfFactory.setEnabled(true);
			sfGroup_dept.setEnabled(true);
			sfDept.setEnabled(true);
			checkBox1.setEnabled(true);			
			}			
	}

	private void loadGroupDept(ActionEvent e) {
		//TODO Implement.		
		SelectItem item = (SelectItem) sfFactory.getSelectedItem();		
		ListBinder.bindSelectField(sfGroup_dept, FVGenericInfo.getGroup(item.getValue()), true);
		ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue()), true);
	}

	private void CheckDate(ActionEvent e) {
		if(checkBox1.isSelected()==true)
		{
			dscDateField1.setEnabled(true);
		}
		else
			dscDateField1.setEnabled(false);
	//TODO Implement.
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
		rootLayout2 = new Grid();
		rootLayout2.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(
				10, Extent.PX), new Extent(10, Extent.PX), new Extent(10,
				Extent.PX)));
		rootLayout2.setWidth(new Extent(100, Extent.PERCENT));
		rootLayout2.setSize(1);
		add(rootLayout2);
		Row row1 = new Row();
		rootLayout2.add(row1);
		radioButton1 = new RadioButton();
		radioButton1.setText(resourceBundle.getString("N_EMPLOYEE.EMPSN"));
		radioButton1.setSelected(true);
		ButtonGroup group_emp = new ButtonGroup();
		radioButton1.setGroup(group_emp);
		radioButton1.setFont(new Font(null, Font.BOLD | Font.UNDERLINE,
				new Extent(10, Extent.PT)));
		radioButton1.setActionCommand("cmd_emp");
		radioButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empGroupSelected(e);
			}
		});
		row1.add(radioButton1);
		Row row2 = new Row();
		GridLayoutData row2LayoutData = new GridLayoutData();
		row2LayoutData.setRowSpan(2);
		row2.setLayoutData(row2LayoutData);
		rootLayout2.add(row2);
		lblEmpsn = new Label();
		lblEmpsn.setText(resourceBundle.getString("N_EMPLOYEE.EMPSN"));
		RowLayoutData lblEmpsnLayoutData = new RowLayoutData();
		lblEmpsnLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX)));
		lblEmpsn.setLayoutData(lblEmpsnLayoutData);
		row2.add(lblEmpsn);
		txtempsn = new DscField();
		txtempsn.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(0,
				Extent.PX), new Extent(10, Extent.PX), new Extent(0, Extent.PX)));
		txtempsn.setDisabledBackground(new Color(0xc0c0c0));
		txtempsn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doEmpsn(e);
			}
		});
		row2.add(txtempsn);
		Row row3 = new Row();
		rootLayout2.add(row3);
		radioButton2 = new RadioButton();
		radioButton2.setText(resourceBundle.getString("FACTORY"));
		radioButton2.setGroup(group_emp);
		radioButton2.setFont(new Font(null, Font.BOLD | Font.UNDERLINE,
				new Extent(10, Extent.PT)));
		radioButton2.setActionCommand("cmd_dept");
		radioButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empGroupSelected(e);
			}
		});
		row3.add(radioButton2);
		Row row4 = new Row();
		rootLayout2.add(row4);
		Label label1 = new Label();
		label1.setText(resourceBundle.getString("N_DEPARTMENT.NAME_FACT"));
		RowLayoutData label1LayoutData = new RowLayoutData();
		label1LayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX)));
		label1.setLayoutData(label1LayoutData);
		row4.add(label1);
		sfFactory = new SelectField();
		sfFactory.setEnabled(false);
		sfFactory.setDisabledBackground(new Color(0xc0c0c0));
		RowLayoutData sfFactoryLayoutData = new RowLayoutData();
		sfFactoryLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		sfFactoryLayoutData.setWidth(new Extent(200, Extent.PX));
		sfFactory.setLayoutData(sfFactoryLayoutData);
		sfFactory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadGroupDept(e);
			}
		});
		row4.add(sfFactory);
		Row row5 = new Row();
		rootLayout2.add(row5);
		Label label2 = new Label();
		label2.setText(resourceBundle.getString("N_DEPARTMENT.NAME_GROUP"));
		RowLayoutData label2LayoutData = new RowLayoutData();
		label2LayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(75, Extent.PX),
				new Extent(0, Extent.PX)));
		label2.setLayoutData(label2LayoutData);
		row5.add(label2);
		sfGroup_dept = new SelectField();
		sfGroup_dept.setEnabled(false);
		sfGroup_dept.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(
				0, Extent.PX), new Extent(0, Extent.PX), new Extent(0,
				Extent.PX)));
		sfGroup_dept.setDisabledBackground(new Color(0xc0c0c0));
		RowLayoutData sfGroup_deptLayoutData = new RowLayoutData();
		sfGroup_deptLayoutData.setWidth(new Extent(200, Extent.PX));
		sfGroup_dept.setLayoutData(sfGroup_deptLayoutData);
		row5.add(sfGroup_dept);
		Row row6 = new Row();
		rootLayout2.add(row6);
		label3 = new Label();
		label3.setText(resourceBundle.getString("N_DEPARTMENT.NAME_DEPT_NAME"));
		RowLayoutData label3LayoutData = new RowLayoutData();
		label3LayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(65, Extent.PX),
				new Extent(0, Extent.PX)));
		label3.setLayoutData(label3LayoutData);
		row6.add(label3);
		sfDept = new SelectField();
		sfDept.setEnabled(false);
		sfDept.setInsets(new Insets(new Extent(50, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		sfDept.setDisabledBackground(new Color(0xc0c0c0));
		RowLayoutData sfDeptLayoutData = new RowLayoutData();
		sfDeptLayoutData.setWidth(new Extent(200, Extent.PX));
		sfDept.setLayoutData(sfDeptLayoutData);
		row6.add(sfDept);
		row7 = new Row();
		rootLayout2.add(row7);
		checkBox1 = new CheckBox();
		checkBox1.setEnabled(false);
		checkBox1.setText(resourceBundle.getString("N_SP_WDAY.DATE_SP"));
		checkBox1
				.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(0,
						Extent.PX), new Extent(0, Extent.PX), new Extent(0,
						Extent.PX)));
		checkBox1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CheckDate(e);
			}
		});
		row7.add(checkBox1);
		dscDateField1 = new DscDateField();
		dscDateField1.setEnabled(false);
		dscDateField1.setVisible(true);
		RowLayoutData dscDateField1LayoutData = new RowLayoutData();
		dscDateField1LayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		dscDateField1.setLayoutData(dscDateField1LayoutData);
		row7.add(dscDateField1);
	}

}
