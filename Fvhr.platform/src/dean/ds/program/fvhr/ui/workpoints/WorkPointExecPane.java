package ds.program.fvhr.ui.workpoints;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.commons.lang.StringUtils;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ListBox;
import ds.program.fvhr.dao.generic.FvGenericDAO;
import ds.program.fvhr.dao.wp.WorkpointsDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.DefaultProgram;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import fv.components.SelectItem;
import fv.util.ApplicationHelper;
import fv.util.DateUtils;
import fv.util.DbUtils;
import fv.util.FVGenericInfo;
import fv.util.HRUtils;
import fv.util.ListBinder;
import fv.util.MappingPropertyUtils;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.Font;
import dsc.echo2app.component.DscGroupCheckBox;
import nextapp.echo2.app.CheckBox;

public class WorkPointExecPane extends WindowPane {
	private static final long serialVersionUID = 1L;
	
	public static final int TYPE_SEARCH=0;
	public static final int TYPE_EXCEC=1;
	public static final int TYPE_PRINT=2;
	
	private ResourceBundle resourceBundle;
	private SelectField sfMonth;
	private SelectField sfYear;
	private ListBox lstEmpsn;
	private DscField txtEmpsn;
	private RadioButton radDept;
	private SelectField sfFact;
	private SelectField sfLean;
	private SelectField sfDept;
	private RadioButton radEmpsn;
	private int type;
	private Button btnSearch;
	private Button btnExcec;
	private DefaultProgram program;
	private Button btnReset;
	
	private WorkpointsDAO dao;

	private boolean isCurrent;

	private CheckBox chkFv1;

	private CheckBox chkFv2;

	private CheckBox chkFv3;

	private CheckBox chkFv4;

	private CheckBox chkFv5;

	private CheckBox chkOther;

	private DscGroupCheckBox groupFVL;

	private CheckBox chkNV;

	private CheckBox chkVS;

	private CheckBox chkLost;

	/**
	 * Creates a new <code>WorkPointExecPane</code>.
	 */
	public WorkPointExecPane(DefaultProgram program) {
		super();
		this.program=program;
		// Add design-time configured components.
		initComponents();
		initUI();
	}
	
	public WorkpointsDAO getDao() {
		NGetData00MProgram program = (NGetData00MProgram) this.program;
		dao = program.getJdbcDAO();
		dao.setMonth(getMonths());
		dao.setYear(getYears());
		return dao;
	}
	
	private void initUI(){
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		ListBinder.bindSelectField(sfMonth, MappingPropertyUtils
				.getJavaMonthEditor(), true);
		sfMonth.setSelectedIndex(month);		
		int year=c.get(Calendar.YEAR);
		ListBinder.bindSelectField(sfYear, MappingPropertyUtils.getYearEditor(1, false), true);
	    ListBinder.refreshIndex(sfYear,c.get(Calendar.YEAR));
		ListBinder.bindSelectField(sfLean, FVGenericInfo.getAllGroup(), true);
		ListBinder.bindSelectField(sfFact, FVGenericInfo.getFactories(), false);
		sfFact.setEnabled(false);
	}
	
	public void applyProgramCondition(){
		//tim theo so the => kiem tra so the khi them vao listbox
		String lost = "(o.DUCLS+o.NDUCLS+o.REST+o.REST_PAY+o.REST_SICK+o.OTHER+o.LMATER+o.NWHOUR)<>"+DateUtils.fvWorkingDays(sfMonth.getSelectedItem().toString(), sfYear.getSelectedItem().toString());
		if (radEmpsn.isSelected()){
			DefaultListModel model = (DefaultListModel) lstEmpsn.getModel();
			List<String> list = new ArrayList<String>();
			for (int i=0;i<model.size();i++){
				SelectItem item = (SelectItem) model.get(i);
				list.add(item.getValue().toString());
			}
			String fc = "o.EMPSN in " + DbUtils.parseInStringParamValues(list) + " and o.MONTHS=? and o.YEARS=?";
			if (chkLost.isSelected()) fc = fc + " and " + lost;
			ProgramCondition pc = new ProgramCondition(fc, new Object[]{sfMonth.getSelectedItem().toString(), sfYear.getSelectedItem().toString()});
			program.setQueryCondition(pc);
		}else{//tim theo don vi => khong kiem tra => tra ve so the da thao tac
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
//			String fc = "o.MONTHS=? and o.YEARS=? and o.DEPSN in (select d.ID_DEPT FROM N_DEPARTMENT d WHERE d.ID_DEPT=o.DEPSN and " + q + ")";
			String sql = 
				"o.MONTHS=? AND o.YEARS=? and o.DEPSN in " +
				"(SELECT D.ID_DEPT FROM N_DEPARTMENT D WHERE o.DEPSN=D.ID_DEPT AND " + q + ")\n"+
				//" and o.EMPSN not in (select a.EMPSN from N_EMP_QUIT a where to_char(a.THANG_TRUBH,'mm/yyyy')='"+getMonths()+"/"+getYears()+"')";
			//Loai tru CNV nghi viec trong thang, ko chuyen du lieu vao bang luong
			//ko dua theo thang tru BH duoc vi neu CN moi chua tham gia BH thi thang tru BH la null
			//29/10/2013
					" and o.EMPSN not in (select c.EMPSN from N_EMP_QUIT c where c.EMPSN=o.EMPSN \n"+
					" and c.REAL_OFF_DATE<= last_day_of_month("+getMonths()+","+getYears()+") and c.DATE_AGAIN is null )";
			// end 29/10/2013					
			String sql1 = "o.MONTHS=? AND o.YEARS=? and o.EMPSN IN (SELECT Q.EMPSN FROM N_EMP_QUIT Q, N_DEPARTMENT D WHERE o.EMPSN=Q.EMPSN AND Q.DEPSN=D.ID_DEPT AND TO_CHAR(Q.REAL_OFF_DATE,'MM/YYYY')=? AND " + q + ")";// and o.DEPSN=D.ID_DEPT
			String sql2 = "o.MONTHS=? AND o.YEARS=? and o.EMPSN IN (SELECT R.EMPSN FROM N_REGISTER_SHIFT R WHERE o.EMPSN=R.EMPSN AND R.NOTE LIKE '7H%' AND TO_CHAR(R.SHIFT_DATE,'MM/YYYY')=?) AND o.DEPSN IN (SELECT D.ID_DEPT FROM N_DEPARTMENT D WHERE o.DEPSN=D.ID_DEPT AND " + q + ")";
			if (chkLost.isSelected()){
				sql = sql + " and " + lost;
				sql1 = sql1 + " and " + lost;
				sql2 = sql2 + " and " + lost;
			}
			ProgramCondition pc;
			if (!chkNV.isSelected()&&!chkVS.isSelected())
				pc = new ProgramCondition(sql, new Object[]{sfMonth.getSelectedItem().toString(), sfYear.getSelectedItem().toString()});
			else{
				if (chkNV.isSelected())
					pc = new ProgramCondition(sql1, new Object[]{getMonths(), getYears(), getMonths()+"/"+getYears()});
				else
					pc = new ProgramCondition(sql2, new Object[]{getMonths(), getYears(), getMonths()+"/"+getYears()});
			}
			program.setQueryCondition(pc);
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
						int f=i+1;
						if (i>=3)f=i+2;
						infvl = infvl + "AND D.NAME_GROUP NOT LIKE 'F" + f + "%' ";
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
			} else {
				CheckBox chk1 = (CheckBox) groupFVL.getComponent(0);
				if (chk1.isSelected()) {
					infvl = "OR (D.NAME_GROUP LIKE 'F1%' AND D.NAME_GROUP NOT LIKE 'F12%')";
				}
				for (int i = 1; i < groupFVL.getComponentCount() - 1; i++) {
					CheckBox chk = (CheckBox) groupFVL.getComponent(i);
					if (chk.isSelected()) {
						int f=i+1;
						if (i>=3)f=i+2;
						infvl = infvl + " OR D.NAME_GROUP LIKE 'F" + f + "%'";
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
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Số thẻ không có trong hệ thống hoặc đã nghỉ việc");
			return;
		}
		//check permission
		if (!util.getPermissionValidator().hasEmpsnPermission(empsn)){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Anh/chị không có quyền thao tác số thẻ này");
			return;
		}
		//emp = ValidEmpService.get(String empsn);
		String empna = FvGenericDAO.getInstanse().getFullName(empsn);
		SelectItem item = new SelectItem(empsn + "_" + empna, empsn);
		model.add(item);
//		if (model.size()>0) {
//			sfMonth.setEnabled(false);
//			sfYear.setEnabled(false);
//		}
		txtEmpsn.requestFocus();
	}

	private void empGroupSelected(ActionEvent e) {
		if (e.getActionCommand().equals("cmd_emp")){
			txtEmpsn.setEnabled(true);
			lstEmpsn.setEnabled(true);
			sfFact.setEnabled(false);
			sfLean.setEnabled(false);
			sfDept.setEnabled(false);
			chkNV.setEnabled(false);
			chkVS.setEnabled(false);
		} else {
			txtEmpsn.setEnabled(false);
			lstEmpsn.setEnabled(false);
			sfFact.setEnabled(true);
			sfLean.setEnabled(true);
			sfDept.setEnabled(true);
			chkNV.setEnabled(true);
			chkVS.setEnabled(true);
		}
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

//	private void sfDeptChanged(ActionEvent e) {
//		if (sfLean.getSelectedIndex() < 0) {
//			String fact = sfFact.getSelectedItem().toString();
//			SelectItem item = (SelectItem) sfDept.getSelectedItem();
//			List<String> groups = FVGenericInfo.findGroupByDeptName(fact, item
//					.getValue().toString());
//			if(groups.size()==1) {
//				ListBinder.refreshIndex(sfLean, groups.get(0));
//				ListBinder.bindSelectField(sfDept, FVGenericInfo
//						.getDeptName(fact, groups.get(0)), true);
//				ListBinder.refreshIndex(sfDept, item.getValue().toString());
//			}
//		}
//	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		switch (type){
		case TYPE_SEARCH:
			btnExcec.setVisible(false);
			btnSearch.setVisible(true);
			chkLost.setVisible(true);
			chkLost.setSelected(false);
			break;
		case TYPE_EXCEC:
			btnExcec.setVisible(true);
			btnSearch.setVisible(false);
			chkLost.setVisible(false);
			chkLost.setSelected(false);
		}
	}
	
	public String getMonths(){
		return sfMonth.getSelectedItem().toString();
	}
	
	public String getYears(){
		return sfYear.getSelectedItem().toString();
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
//		sfMonth.setEnabled(true);
//		sfYear.setEnabled(true);
		chkNV.setSelected(false);
		chkVS.setSelected(false);
		chkLost.setSelected(false);
		ProgramCondition pc = new ProgramCondition("1<>1");
		program.setQueryCondition(pc);
		program.refresh();
	}

	private void buttonAction(ActionEvent e) {
		//validate form
		//Them vao 09/10
		BigDecimal wd = new BigDecimal(DateUtils.fvWorkingDays(getMonths(), getYears()));
		((NGetData00MProgram)program).setInfo("Tháng "+getMonths()+"/"+getYears() + "(" + wd + ")");		
		((NGetData00MProgram)program).setWorkDaysOfMonth(wd);
		
		if (radDept.isSelected()&&sfFact.getSelectedIndex()<0){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Chọn xưởng cần thao tác");
			return;
		}
		if (radEmpsn.isSelected()&&lstEmpsn.getModel().size()<=0){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Nhập số thẻ");
			return;
		}
		if (e.getActionCommand().equals("find")){
			getDao();
			applyProgramCondition();
			program.refresh();
		}else if(e.getActionCommand().equals("exec")) {

			//int locks = checkLock();
			//09/10
			int locks = checkLock("zzz");
			
			if (locks>0){
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Đã khóa dữ liệu (" + locks + ")");
				return;
			}
			//xu ly du lieu thang
			doMonthlyDataProcess();
			//show result
			applyProgramCondition();
			program.refresh();
		}
		
		this.userClose();
	}
	
	public int checkLock(String trangThai){
		HRUtils util = ApplicationHelper.getHRUtils();
		if (radEmpsn.isSelected()){
			DefaultListModel model = (DefaultListModel) lstEmpsn.getModel();
			List<String> list = new ArrayList<String>();
			for (int i=0;i<model.size();i++){
				SelectItem item = (SelectItem) model.get(i);
				list.add(item.getValue().toString());
			}
			//old 
			//return checkBHYT(list)+util.getWorkpointsValidator().countWPLocked(list, getMonths(), getYears());
			//them  vao 09/10/2012  HA
			if (trangThai.equals("trans"))
				return checkBHYT(list)+util.getWorkpointsValidator().countWPLocked(list, getMonths(), getYears());			
			else
				return util.getWorkpointsValidator().countWPLocked(list, getMonths(), getYears());
		}else{
			//old
			//return checkBHYT(ListBinder.get(sfFact).toString())+util.getWorkpointsValidator().countWPLocked(ListBinder.get(sfFact), ListBinder.get(sfLean), ListBinder.get(sfDept), getMonths(), getYears(), getGroupFVLConditionString());
			//09/10
			if (trangThai.equals("trans"))
				return checkBHYT(ListBinder.get(sfFact).toString())+util.getWorkpointsValidator().countWPLocked(ListBinder.get(sfFact), ListBinder.get(sfLean), ListBinder.get(sfDept), getMonths(), getYears(), getGroupFVLConditionString());
			else 
				return util.getWorkpointsValidator().countWPLocked(ListBinder.get(sfFact), ListBinder.get(sfLean), ListBinder.get(sfDept), getMonths(), getYears(), getGroupFVLConditionString());		
		}
	}
	
	public int checkBHYT(String fact){		
		String sql=
				"SELECT COUNT(*) FROM N_N_HEALTH_R_STATUS T" +
				" WHERE T.NAME_FACT = '" + fact + "'" +
				" AND TO_CHAR(T.MONTH_REPORT,'MM/YYYY') = '" + getMonths()+"/" + getYears()+"'" +
				" AND T.STATUS = 1";
		try{
			Integer n = getDao().getJdbcTemplate().queryForInt(sql);
			if (n>=1) return 0;
			else return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 1;
		}
	}
	
	public int checkBHYT(List<String> listEmp){
		String sql=
			"SELECT COUNT(*) FROM N_N_HEALTH_R_STATUS T " +
			"WHERE T.NAME_FACT IN (SELECT D.NAME_FACT FROM N_EMPLOYEE E, N_DEPARTMENT D WHERE D.ID_DEPT=E.DEPSN AND T.NAME_FACT=D.NAME_FACT " + 
			"AND E.EMPSN IN " + DbUtils.parseInStringParamValues(listEmp) + ") " + 
			"AND TO_CHAR(T.MONTH_REPORT,'MM/YYYY') = '" + getMonths()+"/" + getYears()+"'" +
			" AND T.STATUS = 1";

		try{
			Integer n = getDao().getJdbcTemplate().queryForInt(sql);
			if (n>=1) return 0;
			else return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 1;
		}
	}
	
	private void doMonthlyDataProcess(){
		//check locked
		
		//current month=>update current depsn;
		//previous month=>check condition
		
		List<String> list = listEmpsn();
		getDao().processData(list, "01/"+getMonths()+"/"+getYears(), "mm");
		if (isCurrent&&!chkNV.isSelected()&&!chkVS.isSelected()){
			//update current depsn
			getDao().saveCurrentDepsn(list, getMonths(), getYears());
		}
	}
	
	private List<String> listEmpsn(){
		Calendar cal = Calendar.getInstance();
		isCurrent = ListBinder.get(sfMonth).equals(cal.get(Calendar.MONTH))&&ListBinder.get(sfYear).equals(cal.get(Calendar.YEAR));
		if (radEmpsn.isSelected()){
			DefaultListModel model = (DefaultListModel) lstEmpsn.getModel();
			List<String> list = new ArrayList<String>();
			for (int i=0;i<model.size();i++){
				SelectItem item = (SelectItem) model.get(i);
				list.add(item.getValue().toString());
			}
			return list;
		}else{
			List<String> list;
			String fvlStr = getGroupFVLConditionString();
			if (chkNV.isSelected()||chkVS.isSelected()){
				Calendar cal1 = Calendar.getInstance();
				cal1.set(Calendar.MONTH, (Integer)ListBinder.get(sfMonth));
				cal1.set(Calendar.YEAR, (Integer)ListBinder.get(sfYear));
				cal1.set(Calendar.DAY_OF_MONTH, 1);
				Date from = cal1.getTime();
				Calendar cal2 = Calendar.getInstance();
				cal2.set(Calendar.MONTH, (Integer)ListBinder.get(sfMonth));
				cal2.set(Calendar.YEAR, (Integer)ListBinder.get(sfYear));
				cal2.set(Calendar.DAY_OF_MONTH, cal2.getActualMaximum(Calendar.DAY_OF_MONTH));
				Date to = cal2.getTime();
				if (chkNV.isSelected())
					list = FvGenericDAO.getInstanse().getQuitList(from, to, ListBinder.get(sfFact), ListBinder.get(sfLean), ListBinder.get(sfDept), fvlStr);
				else
					list = FvGenericDAO.getInstanse().get7HList(isCurrent, from, to, ListBinder.get(sfFact), ListBinder.get(sfLean), ListBinder.get(sfDept), fvlStr);
			}else{
				if (isCurrent)
					list = FvGenericDAO.getInstanse().getCurrentEmpsnList(ListBinder.get(sfFact), ListBinder.get(sfLean), ListBinder.get(sfDept), fvlStr);
				else
					list = FvGenericDAO.getInstanse().getEmpsnList(getMonths(), getYears(), ListBinder.get(sfFact), ListBinder.get(sfLean), ListBinder.get(sfDept), fvlStr);
			}
			System.out.println("List----: " + list.size());
			return list;
		}
	}
	
	private boolean validateDept(){
		if (sfFact.getSelectedIndex()<0){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Chọn xưởng cần thao tác");
			return false;
		}
		boolean p;
		HRUtils util = ApplicationHelper.getHRUtils();
		String factId, groupId, deptId, deptName;
		factId = ListBinder.get(sfFact).toString();
		if (sfDept.getSelectedIndex()>0){
			deptName = ListBinder.get(sfDept).toString();
			if (sfLean.getSelectedIndex()>=0){
				groupId = ListBinder.get(sfLean).toString();
				p = util.getPermissionValidator().hasDeptNamePermission(factId, groupId, deptName);
			}else{
				//khong kiem tra
			}
		}else{
			if (sfLean.getSelectedIndex()>=0){
				groupId = ListBinder.get(sfLean).toString();
				p = util.getPermissionValidator().hasGroupPermission(factId, groupId);
			}else{
				p = util.getPermissionValidator().hasFactoryPermission(factId);
			}
		}
		return false;
	}

	private void chkVSChanged(ActionEvent e) {
		if (chkVS.isSelected()) chkNV.setSelected(false);
	}

	private void chkNVChanged(ActionEvent e) {
		if (chkNV.isSelected()) chkVS.setSelected(false);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Default.Window");
		this.setTitle("Pane");
		this.setHeight(new Extent(395, Extent.PX));
		this.setDefaultCloseOperation(WindowPane.HIDE_ON_CLOSE);
		this.setWidth(new Extent(510, Extent.PX));
		this.setModal(true);
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(310, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		add(splitPane1);
		ContentPane contentPane3 = new ContentPane();
		splitPane1.add(contentPane3);
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
		label1.setText("Tháng: ");
		row3.add(label1);
		sfMonth = new SelectField();
		row3.add(sfMonth);
		Label label5 = new Label();
		label5.setText("Năm: ");
		row3.add(label5);
		sfYear = new SelectField();
		row3.add(sfYear);
		chkLost = new CheckBox();
		chkLost.setText("Chỉ tìm DL không hoàn chỉnh");
		row3.add(chkLost);
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
		chkNV.setEnabled(false);
		chkNV.setText("Nghỉ việc");
		chkNV.setDisabledBackground(new Color(0xc0c0c0));
		chkNV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkNVChanged(e);
			}
		});
		grid2.add(chkNV);
		Label label9 = new Label();
		grid2.add(label9);
		chkVS = new CheckBox();
		chkVS.setEnabled(false);
		chkVS.setText("Về sớm - con nhỏ");
		chkVS.setDisabledBackground(new Color(0xc0c0c0));
		chkVS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkVSChanged(e);
			}
		});
		grid2.add(chkVS);
		lstEmpsn = new ListBox();
		lstEmpsn.setHeight(new Extent(250, Extent.PX));
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
			public void actionPerformed(ActionEvent e) {
				buttonAction(e);
			}
		});
		row1.add(btnSearch);
		btnExcec = new Button();
		btnExcec.setText("Xử lý dữ liệu tháng");
		btnExcec.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnExcec.setActionCommand("exec");
		btnExcec.setRolloverBackground(new Color(0x0080ff));
		btnExcec.setBorder(new Border(new Extent(1, Extent.PX), Color.BLACK,
				Border.STYLE_SOLID));
		btnExcec.setRolloverForeground(new Color(0xff8000));
		btnExcec.setRolloverEnabled(true);
		btnExcec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonAction(e);
			}
		});
		row1.add(btnExcec);
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
			public void actionPerformed(ActionEvent e) {
				resetForm(e);
			}
		});
		row1.add(btnReset);
	}
}
