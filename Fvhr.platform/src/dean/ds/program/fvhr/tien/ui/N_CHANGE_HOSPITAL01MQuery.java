package ds.program.fvhr.tien.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
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
import nextapp.echo2.app.layout.RowLayoutData;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_EMPLOYEE;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;
import echopointng.DateField;
import fv.components.SelectItem;
import fv.util.ApplicationHelper;
import fv.util.FVGenericInfo;
import fv.util.HRUtils;
import fv.util.ListBinder;
import fv.util.Vni2Uni;

public class N_CHANGE_HOSPITAL01MQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
	private ResourceBundle resourceBundle;
	private RadioButton radioButton1;
	private Label EMPSN_CaptionLabel;
	private DscField EMPSN_DscField1;
	private Label lblHovaten_;
	private RadioButton radioButton2;
	private SelectField sfFactory;
	private SelectField sfGroup_dept;
	private Label label3;
	private SelectField sfDept;
	private Label DATE_SP_CaptionLabel;
	private DateField DATE_SP_DscDateField1;
	private DscField tf_date;
	private Date dtNoew =new Date();
	private SimpleDateFormat sp=new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * Creates a new <code>N_CHANGE_HOSPITAL01MQuery</code>.
	 */
	public N_CHANGE_HOSPITAL01MQuery() {
		super();

		// Add design-time configured components.
		initComponents();
		ListBinder.bindSelectField(sfFactory, FVGenericInfo.getFactories(), false);
		sfFactory.setEnabled(false);
	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		ConditionManager conditionMgr = new ConditionManager();
		setConditionMgr(conditionMgr);


	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor

		//<一般查詢條件定義>

        String key;
		return ret;

	}
	@Override
	protected void doQuery()
	
	{	String empsn =EMPSN_DscField1.getText();
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
					EMPSN_DscField1.requestFocus();
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
							String Group_Dept=fv.util.Vni2Uni.convertToVNI(ListBinder.get(sfGroup_dept).toString());
							params.add(Group_Dept);
						}
						if(sfDept.getSelectedIndex()>=0)
						{  hsql=hsql+" and D.NAME_DEPT_NAME=?";
							
							String id_Dept=Vni2Uni.convertToVNI(ListBinder.get(sfDept).toString());
							params.add(id_Dept);
						
							
							
						}
						hsql=hsql+")";
						if(! DATE_SP_DscDateField1.getText().equals(""))
						{				
							hsql=hsql +" and o.DATE_CHANGE=?";
							Date dataSp=DATE_SP_DscDateField1.getSelectedDate().getTime();
							params.add(dataSp);			
							
						}
						hsql = StringUtils.substringAfter(hsql, " and ");
						ProgramCondition pc = new ProgramCondition(hsql, params.toArray());			
						getProgram().setQueryCondition(pc);
						getProgram().refresh();	
				}
		//}
	}
	private void loadGroupDept(ActionEvent e) {
		//TODO Implement.		
		SelectItem item = (SelectItem) sfFactory.getSelectedItem();		
		ListBinder.bindSelectField(sfGroup_dept, FVGenericInfo.getGroup(item.getValue()), true);
		ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue()), true);
	}
	private void empGroupSelected(ActionEvent e) {
		//TODO Implement.
		if(e.getActionCommand().equals("cmd_emp"))
		{
			EMPSN_DscField1.setEnabled(true);
			sfFactory.setEnabled(false);
			sfGroup_dept.setEnabled(false);
			sfDept.setEnabled(false);
		}
		else
			
		{
			EMPSN_DscField1.setEnabled(false);
			sfFactory.setEnabled(true);
			sfGroup_dept.setEnabled(true);
			sfDept.setEnabled(true);
		}
	}
	private void doEmployee(ActionEvent e) {
		//TODO Implement.
		//N_SP_WDAY data = (N_SP_WDAY) getDataObject();
		IGenericDAO<N_EMPLOYEE, String> dao=Application.getApp().getDao(N_EMPLOYEE.class);
		N_EMPLOYEE emsp=dao.findById(EMPSN_DscField1.getText());
		if(emsp==null)
		{
			lblHovaten_.setText("Số thẻ không tồn tại");			
		}
		else
		{
			String dept=emsp.getDEPSN();
			IGenericDAO<N_DEPARTMENT,String> objDep_dao=Application.getApp().getDao(N_DEPARTMENT.class);
			N_DEPARTMENT obj_Department=objDep_dao.findById(dept);
			if(obj_Department!=null)
			{
				lblHovaten_.setText(Vni2Uni.convertToUnicode(emsp.getFULL_NAME()+"."+obj_Department.getNAME_FACT()+"."+obj_Department.getNAME_DEPT_NAME()));
			}
			
		}				
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
		rootLayout = new Grid();
		rootLayout.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(
				10, Extent.PX), new Extent(10, Extent.PX), new Extent(10,
				Extent.PX)));
		rootLayout.setWidth(new Extent(1000, Extent.PERCENT));
		
		rootLayout.setSize(1);
		add(rootLayout);
		Row row1 = new Row();
		rootLayout.add(row1);
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
		rootLayout.add(row2);
		EMPSN_CaptionLabel = new Label();
		EMPSN_CaptionLabel.setText(resourceBundle.getString("N_SP_WDAY.EMPSN"));
		rootLayout.add(EMPSN_CaptionLabel);		
		
		EMPSN_DscField1 = new DscField();
		EMPSN_DscField1.setId("EMPSN_DscField1");
		EMPSN_DscField1.setDisabledBackground(new Color(0xc0c0c0));
		EMPSN_DscField1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doEmployee(e);
			}
		});
		RowLayoutData lblEmpsnLayoutData = new RowLayoutData();
		lblEmpsnLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX)));
		EMPSN_CaptionLabel.setLayoutData(lblEmpsnLayoutData);
		row2.add(EMPSN_CaptionLabel);
		lblHovaten_ = new Label();
		lblHovaten_.setVisible(true);
		
		row2.add(EMPSN_DscField1);
		row2.add(lblHovaten_);
		Row row3 = new Row();
		rootLayout.add(row3);
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
		rootLayout.add(row4);
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
				new Extent(0, Extent.PX), new Extent(50, Extent.PX), new Extent(
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
		rootLayout.add(row5);
		Label label2 = new Label();
		label2.setText(resourceBundle.getString("N_DEPARTMENT.NAME_GROUP"));
		RowLayoutData label2LayoutData = new RowLayoutData();
		label2LayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX)));
		label2.setLayoutData(label2LayoutData);
		row5.add(label2);
		sfGroup_dept = new SelectField();
		sfGroup_dept.setEnabled(false);		
		sfGroup_dept.setDisabledBackground(new Color(0xc0c0c0));
		RowLayoutData sfGroup_deptLayoutData = new RowLayoutData();
		sfGroup_deptLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		sfGroup_deptLayoutData.setWidth(new Extent(200, Extent.PX));
		sfGroup_dept.setLayoutData(sfGroup_deptLayoutData);
		row5.add(sfGroup_dept);
		Row row6 = new Row();
		rootLayout.add(row6);
		label3 = new Label();
		label3.setText(resourceBundle.getString("N_DEPARTMENT.NAME_DEPT_NAME"));
		RowLayoutData label3LayoutData = new RowLayoutData();
		label3LayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX)));
		label3.setLayoutData(label3LayoutData);
		row6.add(label3);
		sfDept = new SelectField();
		sfDept.setEnabled(false);		
		sfDept.setDisabledBackground(new Color(0xc0c0c0));
		RowLayoutData sfDeptLayoutData = new RowLayoutData();
		sfDeptLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		sfDeptLayoutData.setWidth(new Extent(200, Extent.PX));
		sfDept.setLayoutData(sfDeptLayoutData);
		row6.add(sfDept);
		Row row7=new Row();
		rootLayout.add(row7);		
		DATE_SP_CaptionLabel = new Label();
		RowLayoutData labelDATE_SPLayoutData = new RowLayoutData();
		labelDATE_SPLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX)));
		DATE_SP_CaptionLabel.setLayoutData(labelDATE_SPLayoutData);
		DATE_SP_CaptionLabel.setText(resourceBundle.getString("N_CHANGE_HOSPITAL.DATE_CHANGE"));
		DATE_SP_DscDateField1 = new DateField();
		tf_date	= new DscField();		
		DATE_SP_DscDateField1.setId("DATE_SP_DscDateField1");
		DATE_SP_DscDateField1.setTextField(tf_date);
		DATE_SP_DscDateField1.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		row7.add(DATE_SP_CaptionLabel);		
		row7.add(DATE_SP_DscDateField1);
		Row row8=new Row();
		rootLayout.add(row8);
	}

}
