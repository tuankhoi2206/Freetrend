package ds.program.fvhr.ui.salary;

import java.util.Calendar;
import java.util.ResourceBundle;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Grid;
import dsc.echo2app.component.DscField;
import nextapp.echo2.app.SelectField;
import fv.components.CustomQueryPane;
import fv.components.SelectItem;
import fv.util.ApplicationHelper;
import fv.util.DbUtils;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.MappingPropertyUtils;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.CheckBox;

public class SalaryQuery extends CustomQueryPane {

	private ResourceBundle resourceBundle;
	private SelectField sfMonth;
	private SelectField sfYear;
	private Grid conditionGrid;
	private RadioButton radEmpsn;
	private DscField txtEmpsn;
	private RadioButton radDept;
	private SelectField sfFact;
	private SelectField sfLean;
	private SelectField sfDept;
	private Grid conditionGrid2;
	private RadioButton radDeptGroup;
	private Grid leanGrid;
	private RadioButton radATM;
	private SelectField sfATM;
	private RadioButton radLeanCBATM;
	private RadioButton radLeanCB0ATM;
	private RadioButton radLeanCNATM;
	private RadioButton radLeanCN0ATM;
	private RadioButton radLeanCNCB0ATM;
	private SelectField sfDeptGroup;
	private CheckBox chkATM;
	private RadioButton radLeanCNCBATM;
	
	/**
	 * Creates a new <code>SalaryQuery</code>.
	 */
	public SalaryQuery() {
		super();

		// Add design-time configured components.
		initComponents();
		initUI();
	}

	
	protected int doInit() {
		int ret = super.doInit();
		windowPane.setTitle("Tìm kiếm dữ liệu lương");
		windowPane.setDefaultCloseOperation(WindowPane.HIDE_ON_CLOSE);
		windowPane.setWidth(new Extent(600));
		windowPane.setHeight(new Extent(370));
		return ret;
	}

	private void initUI(){
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		ListBinder.bindSelectField(sfMonth, MappingPropertyUtils
				.getJavaMonthEditor(), true);
		int index=month-1;
		int yearIndex=1;
		if (index==-1) {
			index=11;
			yearIndex=0;
		}
		sfMonth.setSelectedIndex(index);
		ListBinder.bindSelectField(sfYear, MappingPropertyUtils.getYearEditor(
				8, false), true);
		sfYear.setSelectedIndex(yearIndex);
		ListBinder.bindSelectField(sfFact, FVGenericInfo.getFactories(), false);
		sfFact.setEnabled(false);
		//ListBinder.bindSelectField(sfATM, FVGenericInfo.getATMGroupEditor(), true);
		ListBinder.bindSelectField(sfATM, FVGenericInfo.getATMGroupEditor(), true);
		ListBinder.bindSelectField(sfDeptGroup, FVGenericInfo.getDeptGroupEditor(), true);
		sfATM.setEnabled(false);
		sfDeptGroup.setEnabled(false);
	}

	private void empGroupSelected(ActionEvent e) {
		if (e.getActionCommand().equals("cmd_emp")){
			txtEmpsn.setEnabled(true);
			sfFact.setEnabled(false);
			sfLean.setEnabled(false);
			sfDept.setEnabled(false);
			sfATM.setEnabled(false);
			sfDeptGroup.setEnabled(false);
			chkATM.setEnabled(false);
			chkATM.setSelected(false);
			radLeanCNATM.setSelected(false);
			leanGrid.setEnabled(false);
		} else if (e.getActionCommand().equals("cmd_dept")){
			txtEmpsn.setEnabled(false);
			sfFact.setEnabled(true);
			sfLean.setEnabled(true);
			sfDept.setEnabled(true);
			sfATM.setEnabled(false);
			sfDeptGroup.setEnabled(false);
			chkATM.setEnabled(true);
			chkATM.setSelected(true);
			radLeanCNATM.setSelected(true);
			leanGrid.setEnabled(true);			
		} else if (e.getActionCommand().equals("cmd_atm_group")){
			txtEmpsn.setEnabled(false);
			sfFact.setEnabled(false);
			sfLean.setEnabled(false);
			sfDept.setEnabled(false);
			sfATM.setEnabled(true);
			sfDeptGroup.setEnabled(false);
			chkATM.setEnabled(false);
			chkATM.setSelected(false);
			radLeanCNATM.setSelected(false);
			leanGrid.setEnabled(false);
		} else {
			txtEmpsn.setEnabled(false);
			sfFact.setEnabled(false);
			sfLean.setEnabled(false);
			sfDept.setEnabled(false);
			sfATM.setEnabled(false);
			sfDeptGroup.setEnabled(true);
			chkATM.setEnabled(true);
			chkATM.setSelected(true);
			radLeanCNATM.setSelected(true);
			leanGrid.setEnabled(true);
		}
	}

	protected void sfLeanChanged(ActionEvent e) {
		SelectItem item = (SelectItem) sfFact.getSelectedItem();
		SelectItem litem = (SelectItem) sfLean.getSelectedItem();
		ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue(),litem.getValue()), true);
	}

	protected void sfFactChanged(ActionEvent e) {
		SelectItem item = (SelectItem) sfFact.getSelectedItem();
		ListBinder.bindSelectField(sfLean, FVGenericInfo.getGroup(item.getValue()), true);
		ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue()), true);
	}

	private boolean validateForm(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);

		int month = sfMonth.getSelectedIndex();
		int year = Integer.parseInt(sfYear.getSelectedItem().toString());
		if (month>cal.get(Calendar.MONTH)&&year>=cal.get(Calendar.YEAR)){
			setErrorMessage("Không có bảng lương tháng " + sfMonth.getSelectedItem().toString() + "/" + year);
			return false;
		}
		if (radDept.isSelected()){
			if (sfFact.getSelectedIndex()<0){
				setErrorMessage("Chọn xưởng");
				return false;
			}
			if (sfFact.getSelectedItem().toString().equals("FVL")){
				if (sfLean.getSelectedIndex()<0&&sfDept.getSelectedIndex()<0){
					setErrorMessage("Chọn Lean hoặc đơn vị");
					return false;
				}
			}
		}else if (radEmpsn.isSelected()){
			String empsn = txtEmpsn.getText();
			if (!empsn.matches("[0-9]{8}")){
				setErrorMessage("Số thẻ không hợp lệ");
				return false;
			}
			if (!ApplicationHelper.getHRUtils().isWorkingOrQuit(empsn)){
				setErrorMessage("Không có số thẻ " + empsn + " trong hệ thống");
				return false;
			}
		}else{
			if (radATM.isSelected()&&sfATM.getSelectedIndex()<0){
				setErrorMessage("Chọn nhóm ATM");
				return false;
			}else if (radDeptGroup.isSelected()&&sfDeptGroup.getSelectedIndex()<0){
				setErrorMessage("Chọn nhóm đơn vị");
				return false;
			}
		}
		return true;
	}

	
	protected void doQuery() {
		if (!validateForm()){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, getErrorMessage());
			return;
		}
		String[] printParams = new String[3];
		SalaryMainProgram program = (SalaryMainProgram) getProgram();
		program.setMonth(sfMonth.getSelectedItem().toString());
		program.setYear(sfYear.getSelectedItem().toString());
		program.updateTable(sfMonth.getSelectedItem().toString(),sfYear.getSelectedItem().toString());
		String sql="";
		if (radEmpsn.isSelected()){
			sql = sql + "o.EMPSN='" + txtEmpsn.getText()+"'";
			printParams=null;
		}else if (radDept.isSelected()){
			String f=null,l=null,d=null;
			sql = sql + "o.DEPSN IN (SELECT D.ID_DEPT FROM N_DEPARTMENT D WHERE o.DEPSN=D.ID_DEPT AND D.NAME_FACT='"+ListBinder.get(sfFact)+"'";
			printParams[0]=f=sfFact.getSelectedItem().toString();			
			if (sfLean.getSelectedIndex()>=0){
				l=ListBinder.get(sfLean).toString();
				sql = sql + " AND D.NAME_GROUP='" + l + "'";
				printParams[0]=printParams[0]+"."+sfLean.getSelectedItem().toString();
			}
			if (sfDept.getSelectedIndex()>=0){
				d=ListBinder.get(sfDept).toString();
				sql = sql + " AND D.NAME_DEPT_NAME='" + d + "'";
				
				printParams[0]=printParams[0]+"."+sfDept.getSelectedItem().toString();
				//get id dept
				String idDept=FVGenericInfo.findDept(f,l,d);
				printParams[2]=idDept;
			}
			sql = sql + ")";
			if (chkATM.isSelected()&&chkATM.isEnabled()){
				if (radLeanCNATM.isSelected()){
					sql = sql + " AND INSTR(o.POSSN,'CN_')>0";
					printParams[1]="CN_ATM";
				}else if (radLeanCN0ATM.isSelected()){
					sql = sql + " AND o.POSSN='CN'";
					printParams[1]="CN";
				}else if (radLeanCBATM.isSelected()){
					sql = sql + " AND INSTR(o.POSSN,'CN_')=0 AND INSTR(o.POSSN,'_')>0";
					printParams[1]="CB_ATM";
				}else if (radLeanCB0ATM.isSelected()){
					sql = sql + " AND o.POSSN <> 'CN' AND INSTR(o.POSSN,'_')=0";
					printParams[1]="CB";
				}else if (radLeanCNCB0ATM.isSelected()){
					sql = sql + " AND INSTR(o.POSSN,'_')=0";
					printParams[1]="CBCN";
				}else if (radLeanCNCBATM.isSelected()){
					sql = sql + " AND INSTR(o.POSSN,'_')>0";
					printParams[1]="CBCN_ATM";
				}
			}
		}else if (radATM.isSelected()){
			String atm = (String) ListBinder.get(sfATM);
			sql = sql + "o.POSSN LIKE '%_" + atm + "'";
			printParams[0]=sfATM.getSelectedItem().toString();
			printParams[1]=atm;
		}else{
			String depgb = (String) ListBinder.get(sfDeptGroup);
			printParams[0]=sfDeptGroup.getSelectedItem().toString();
			sql = sql + "o.DEPSN IN (SELECT D.ID_DEPT FROM N_DEPARTMENT D WHERE o.DEPSN=D.ID_DEPT AND D.DEPGB='" + depgb + "')";
			if (chkATM.isSelected()&&chkATM.isEnabled()){
				if (radLeanCNATM.isSelected()){
					sql = sql + " AND INSTR(o.POSSN,'CN_')>0";
					printParams[1]="CN_ATM";
				}else if (radLeanCN0ATM.isSelected()){
					sql = sql + " AND o.POSSN='CN'";
					printParams[1]="CN";
				}else if (radLeanCBATM.isSelected()){
					sql = sql + " AND INSTR(o.POSSN,'CN_')=0 AND INSTR(o.POSSN,'_')>0";
					printParams[1]="CB_ATM";
				}else if (radLeanCB0ATM.isSelected()){
					sql = sql + " AND o.POSSN <> 'CN' AND INSTR(o.POSSN,'_')=0";
					printParams[1]="CB";
				}else if (radLeanCNCB0ATM.isSelected()){
					sql = sql + " AND INSTR(o.POSSN,'_')=0";
					printParams[1]="";
				}else if (radLeanCNCBATM.isSelected()){
					sql = sql + " AND INSTR(o.POSSN,'_')>0";
					printParams[1]="CB_CN";
				}
			}
		}
		sql = sql + " AND o.EMPSN in (select e.EMPSN from N_EMPLOYEE e where o.EMPSN=e.EMPSN and e.USER_MANAGE_ID in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + ")";
		program.setQueryCondition(new ProgramCondition(sql));
		program.setPrintParams(printParams);
		program.refresh();
		windowPane.userClose();
	}

	private void atmOption(ActionEvent e) {
		if (chkATM.isSelected()){
			leanGrid.setEnabled(true);
		}else{
			leanGrid.setEnabled(false);
		}
	}

	private void doSearch(ActionEvent e) {
		doQuery();
	}


	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(26, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		add(splitPane1);
		Row row3 = new Row();
		splitPane1.add(row3);
		Label label1 = new Label();
		label1.setText("Tháng: ");
		row3.add(label1);
		sfMonth = new SelectField();
		row3.add(sfMonth);
		Label label5 = new Label();
		label5.setText("Năm: ");
		row3.add(label5);
		sfYear = new SelectField();
		row3.add(sfYear);
		SplitPane splitPane2 = new SplitPane();
		splitPane2.setSeparatorPosition(new Extent(250, Extent.PX));
		splitPane1.add(splitPane2);
		conditionGrid = new Grid();
		conditionGrid.setInsets(new Insets(new Extent(4, Extent.PX)));
		splitPane2.add(conditionGrid);
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
		txtEmpsn.setDisabledBackground(new Color(0xc0c0c0));
		txtEmpsn.setMaximumLength(8);
		txtEmpsn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSearch(e);
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
			public void actionPerformed(ActionEvent e) {
				sfFactChanged(e);
			}
		});
		conditionGrid.add(sfFact);
		Label label4 = new Label();
		label4.setText("Lean");
		conditionGrid.add(label4);
		sfLean = new SelectField();
		sfLean.setEnabled(false);
		sfLean.setWidth(new Extent(140, Extent.PX));
		sfLean.setDisabledBackground(new Color(0xc0c0c0));
		sfLean.addActionListener(new ActionListener() {
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
		conditionGrid2 = new Grid();
		conditionGrid2.setInsets(new Insets(new Extent(4, Extent.PX)));
		conditionGrid2.setSize(1);
		splitPane2.add(conditionGrid2);
		radATM = new RadioButton();
		radATM.setText("Nhóm ATM");
		radATM.setGroup(group_emp);
		radATM.setFont(new Font(null, Font.BOLD | Font.UNDERLINE, new Extent(
				10, Extent.PT)));
		radATM.setActionCommand("cmd_atm_group");
		radATM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empGroupSelected(e);
			}
		});
		conditionGrid2.add(radATM);
		sfATM = new SelectField();
		sfATM.setEnabled(false);
		sfATM.setDisabledBackground(new Color(0xc0c0c0));
		conditionGrid2.add(sfATM);
		radDeptGroup = new RadioButton();
		radDeptGroup.setText("Chọn theo nhóm đơn vị ");
		radDeptGroup.setGroup(group_emp);
		radDeptGroup.setFont(new Font(null, Font.BOLD | Font.UNDERLINE,
				new Extent(10, Extent.PT)));
		radDeptGroup.setActionCommand("cmd_dept_group");
		radDeptGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empGroupSelected(e);
			}
		});
		conditionGrid2.add(radDeptGroup);
		sfDeptGroup = new SelectField();
		sfDeptGroup.setEnabled(false);
		sfDeptGroup.setDisabledBackground(new Color(0xc0c0c0));
		conditionGrid2.add(sfDeptGroup);
		chkATM = new CheckBox();
		chkATM.setEnabled(false);
		chkATM.setText("Phân nhóm theo");
		chkATM.setDisabledBackground(new Color(0xc0c0c0));
		GridLayoutData chkATMLayoutData = new GridLayoutData();
		chkATMLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(5, Extent.PX)));
		chkATM.setLayoutData(chkATMLayoutData);
		chkATM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atmOption(e);
			}
		});
		conditionGrid2.add(chkATM);
		leanGrid = new Grid();
		leanGrid.setEnabled(false);
		leanGrid.setSize(1);
		conditionGrid2.add(leanGrid);
		radLeanCBATM = new RadioButton();
		radLeanCBATM.setText("CB sử dụng ATM");
		ButtonGroup rad1 = new ButtonGroup();
		radLeanCBATM.setGroup(rad1);
		radLeanCBATM.setDisabledBackground(new Color(0xc0c0c0));
		leanGrid.add(radLeanCBATM);
		radLeanCB0ATM = new RadioButton();
		radLeanCB0ATM.setText("CB không sử dụng ATM");
		radLeanCB0ATM.setGroup(rad1);
		radLeanCB0ATM.setDisabledBackground(new Color(0xc0c0c0));
		leanGrid.add(radLeanCB0ATM);
		radLeanCNATM = new RadioButton();
		radLeanCNATM.setText("CN sử dụng ATM");
		radLeanCNATM.setGroup(rad1);
		radLeanCNATM.setDisabledBackground(new Color(0xc0c0c0));
		leanGrid.add(radLeanCNATM);
		radLeanCN0ATM = new RadioButton();
		radLeanCN0ATM.setText("CN không sử dụng ATM");
		radLeanCN0ATM.setGroup(rad1);
		radLeanCN0ATM.setDisabledBackground(new Color(0xc0c0c0));
		leanGrid.add(radLeanCN0ATM);
		radLeanCNCB0ATM = new RadioButton();
		radLeanCNCB0ATM.setText("CN_CB không theo nhóm ATM");
		radLeanCNCB0ATM.setGroup(rad1);
		radLeanCNCB0ATM.setDisabledBackground(new Color(0xc0c0c0));
		leanGrid.add(radLeanCNCB0ATM);
		radLeanCNCBATM = new RadioButton();
		radLeanCNCBATM.setText("CN_CB theo nhóm ATM");
		radLeanCNCBATM.setGroup(rad1);
		radLeanCNCBATM.setDisabledBackground(new Color(0xc0c0c0));
		leanGrid.add(radLeanCNCBATM);
	}
}
