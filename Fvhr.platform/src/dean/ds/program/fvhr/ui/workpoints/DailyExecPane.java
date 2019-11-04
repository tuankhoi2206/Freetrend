package ds.program.fvhr.ui.workpoints;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.RadioButton;
import ds.program.fvhr.dao.generic.FvGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscGroupCheckBox;
import fv.components.SelectItem;
import fv.util.ApplicationHelper;
import fv.util.DbUtils;
import fv.util.FVGenericInfo;
import fv.util.HRUtils;
import fv.util.ListBinder;
import fv.util.MappingPropertyUtils;
import fv.util.ReportUtils;
import fv.util.Vni2Uni;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.ListBox;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.layout.RowLayoutData;

public class DailyExecPane extends WindowPane{
	private static final long serialVersionUID = 1L;

	public static final int TYPE_SEARCH=0;
	public static final int TYPE_EXCEC=1;
	public static final int TYPE_PRINT=2;
	private int type;
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
	private Button btnSearch;
	private Button btnExcec;
	private Button btnReset;
	private SelectField sfMonth;
	private SelectField sfYear;
	private SelectField sfFrom;
	private SelectField sfTo;
	private CheckBox chkTo;
	private NDataDailyPMProgram program;

	private SelectField sfFrom2;

	private SelectField sfTo2;

	private Object params[];

	private boolean isCurrent;

	private CheckBox chkAllData;

	private CheckBox chkSunday;

	private Button btnPrintDept;

	/**
	 * Creates a new <code>DailyExecPane</code>.
	 */
	public DailyExecPane(NDataDailyPMProgram program) {
		super();
		this.program=program;
		// Add design-time configured components.
		initComponents();
		initUI();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		switch (type){
		case TYPE_SEARCH:
			btnExcec.setVisible(false);
			btnSearch.setVisible(true);
			btnPrintDept.setVisible(true);
			//			chkLossOnly.setVisible(true); 
			break;
		case TYPE_EXCEC:
			btnExcec.setVisible(true);
			btnSearch.setVisible(false);
			btnPrintDept.setVisible(false);
			//			chkLossOnly.setVisible(false);
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
		sfMonth.setEnabled(true);
		sfYear.setEnabled(true);
		chkNV.setSelected(false);
		chkVS.setSelected(false);
	}

	protected void buttonAction(ActionEvent e) {
		if (chkTo.isSelected()&&sfTo.getSelectedIndex()<=sfFrom.getSelectedIndex()){
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

		if (type==TYPE_SEARCH){
			//			program.getBrowserContent().setFactCondition(buildCondition());
			//			program.getBrowserContent().setFrom(from);
			//			program.getBrowserContent().setTo(to);
			doSearch();
		}else{
			int locks = checkLock();
			if (locks>0){
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Đã khóa dữ liệu (" + locks + ")");
				return;
			}
			doProcessData();
		}
		this.userClose();
	}

	private void doSearch(){
		program.getBrowserContent().setSql(buildSql());
		program.getBrowserContent().setParams(params);
		program.refresh();
	}

	private int checkLock(){
		HRUtils util = ApplicationHelper.getHRUtils();
		if (radEmpsn.isSelected()){
			DefaultListModel model = (DefaultListModel) lstEmpsn.getModel();
			List<String> list = new ArrayList<String>();
			for (int i=0;i<model.size();i++){
				list.add(model.get(i).toString());
			}
			return util.getWorkpointsValidator().countWPLocked(list, getMonths(), getYears());
		}else{
			return util.getWorkpointsValidator().countWPLocked(ListBinder.get(sfFact), ListBinder.get(sfLean), ListBinder.get(sfDept), getMonths(), getYears(), getGroupFVLConditionString());
		}
	}

	private void doProcessData(){
		Calendar from = Calendar.getInstance();
		from.set(Calendar.DAY_OF_MONTH, (Integer)ListBinder.get(sfFrom));
		from.set(Calendar.MONTH, (Integer)ListBinder.get(sfMonth));
		from.set(Calendar.YEAR, (Integer)ListBinder.get(sfYear));
		Calendar to = null;
		if (chkTo.isSelected()&&sfFrom.getSelectedIndex()<sfTo.getSelectedIndex()){
			to = Calendar.getInstance();
			to.set(Calendar.DAY_OF_MONTH, (Integer)ListBinder.get(sfTo));
			to.set(Calendar.MONTH, (Integer)ListBinder.get(sfMonth));
			to.set(Calendar.YEAR, (Integer)ListBinder.get(sfYear));
		}
		//util.checklock=>fact,group,dept,month,year
		List<String> list = listEmpsn(from, to);
		int day = from.get(Calendar.DAY_OF_MONTH);	
		String dateStr;
		if (to!=null){
			int end = to.get(Calendar.DAY_OF_MONTH);
			for (int i=day;i<=end;i++){
				if (i<10) dateStr="0"+i;
				else dateStr=""+i;
				dateStr = dateStr + "/"+sfMonth.getSelectedItem().toString()+"/"+sfYear.getSelectedItem().toString();
				program.getDao().processData(list, dateStr, "dd");
			}
		}else{
			if (day<10) dateStr="0"+day;
			else dateStr=""+day;
			dateStr = dateStr + "/"+sfMonth.getSelectedItem().toString()+"/"+sfYear.getSelectedItem().toString();
			program.getDao().processData(list, dateStr, "dd");
		}
		System.out.println("Finish...");
		doSearch();
	}

	private List<String> listEmpsn(Calendar vsf, Calendar vst){
		if (radEmpsn.isSelected()){
			DefaultListModel model = (DefaultListModel) lstEmpsn.getModel();
			List<String> list = new ArrayList<String>();
			for (int i=0;i<model.size();i++){
				SelectItem item = (SelectItem) model.get(i);
				list.add(item.getValue().toString());
			}
			return list;
		}else{
			Calendar cal = Calendar.getInstance();
			isCurrent = ListBinder.get(sfMonth).equals(cal.get(Calendar.MONTH))&&ListBinder.get(sfYear).equals(cal.get(Calendar.YEAR))
					||(Integer)ListBinder.get(sfMonth)==cal.get(Calendar.MONTH)+1&&ListBinder.get(sfYear).equals(cal.get(Calendar.YEAR))
					&&cal.get(Calendar.DAY_OF_MONTH)<5;

			List<String> list;
			String fvlStr = getGroupFVLConditionString();
			if (chkNV.isSelected()){
				Calendar cal1 = Calendar.getInstance();
				cal1.set(Calendar.MONTH, (Integer)ListBinder.get(sfMonth));
				cal1.set(Calendar.YEAR, (Integer)ListBinder.get(sfYear));
				cal1.set(Calendar.DAY_OF_MONTH, (Integer)ListBinder.get(sfFrom2));
				Date from = cal1.getTime();
				Calendar cal2 = Calendar.getInstance();
				cal2.set(Calendar.MONTH, (Integer)ListBinder.get(sfMonth));
				cal2.set(Calendar.YEAR, (Integer)ListBinder.get(sfYear));
				cal2.set(Calendar.DAY_OF_MONTH, (Integer)ListBinder.get(sfTo2));
				Date to = cal2.getTime();
				list = FvGenericDAO.getInstanse().getQuitList(from, to, ListBinder.get(sfFact), ListBinder.get(sfLean), ListBinder.get(sfDept), fvlStr);
			}else if (chkVS.isSelected()){
				Date to;
				if (vst==null) to=vsf.getTime();
				else to=vst.getTime();
				list = FvGenericDAO.getInstanse().get7HList(isCurrent, vsf.getTime(), to, ListBinder.get(sfFact), ListBinder.get(sfLean), ListBinder.get(sfDept), fvlStr);
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

	private String buildCondition(){
		String str;
		if (radEmpsn.isSelected()){
			DefaultListModel model = (DefaultListModel) lstEmpsn.getModel();
			List<String> list = new ArrayList<String>();
			for (int i=0;i<model.size();i++){
				SelectItem item = (SelectItem) model.get(i);
				list.add(item.getValue().toString());
			}
			str = "e.empsn in " + DbUtils.parseInStringParamValues(list);
		}else{
			String pdp="";
			String fact = ListBinder.get(sfFact).toString();
			String infvl = getGroupFVLConditionString();			
			str = "d.name_fact='" + fact + "' ";
			pdp = pdp + fact;
			if (infvl.equals("")){
				if (sfLean.getSelectedIndex()>=0){
					str = str + "and d.name_group='" + ListBinder.get(sfLean).toString() + "'";
					pdp = pdp + "." + sfLean.getSelectedItem();
				}
				if (sfDept.getSelectedIndex()>=0){
					str = str + "and d.name_dept_name='" + ListBinder.get(sfDept).toString() + "'";
					pdp = pdp + "." + sfDept.getSelectedItem();
				}
			}else{
				str = str + infvl;
			}
			program.printParams.put("DEPARTMENT", pdp);
		}
		return str;
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

	private void chkVSChanged(ActionEvent e) {
		if (chkVS.isSelected()) {
			chkNV.setSelected(false);
			sfFrom2.setEnabled(false);
			sfTo2.setEnabled(false);
		}
	}

	private void chkNVChanged(ActionEvent e) {
		if (chkNV.isSelected()){
			chkVS.setSelected(false);
			sfFrom2.setEnabled(true);
			sfTo2.setEnabled(true);
		}else{
			sfFrom2.setEnabled(false);
			sfTo2.setEnabled(false);
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
			chkNV.setEnabled(false);
			chkVS.setEnabled(false);
			sfFrom2.setEnabled(false);
			sfTo2.setEnabled(false);
			groupFVL.setEnabled(false);
		} else {
			txtEmpsn.setEnabled(false);
			lstEmpsn.setEnabled(false);
			sfFact.setEnabled(true);
			sfLean.setEnabled(true);
			sfDept.setEnabled(true);
			chkNV.setEnabled(true);
			chkVS.setEnabled(true);
			if (chkNV.isSelected()){
				sfFrom2.setEnabled(true);
				sfTo2.setEnabled(true);
			}
			if ("FVL".equals(ListBinder.get(sfFact))){
				groupFVL.setEnabled(true);
			}
		}
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

		ListBinder.bindSelectField(sfFrom2, MappingPropertyUtils
				.getDayEditor(month), true);
		sfFrom2.setSelectedIndex(0);
		ListBinder.bindSelectField(sfTo2, MappingPropertyUtils
				.getDayEditor(month), true);
		sfTo2.setSelectedIndex(c.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
		sfFrom2.setEnabled(false);
		sfTo2.setEnabled(false);
		//		sfTo.addActionListener(new ActionListener(){
		//			private static final long serialVersionUID = 1L;
		//			public void actionPerformed(ActionEvent e) {
		//				if (sfFrom.getSelectedItem().equals(sfTo.getSelectedItem())&&type==TYPE_SEARCH){
		//					chkAllData.setVisible(true);
		//				}else{
		//					chkAllData.setVisible(false);
		//				}
		//			}
		//		});
		sfMonth.setSelectedIndex(month);
		ListBinder.bindSelectField(sfYear, MappingPropertyUtils.getYearEditor(
				1, false), true);
		sfYear.setSelectedIndex(1);
		//		ListBinder.bindSelectField(sfLean, FVGenericInfo.getAllGroup(), true);
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
				sfTo.setSelectedIndex(0);
				if (!chkTo.isSelected()) sfTo.setEnabled(false);
				ListBinder.bindSelectField(sfFrom2, MappingPropertyUtils
						.getDayEditor(sfMonth.getSelectedIndex()), true);
				sfFrom2.setSelectedIndex(0);
				ListBinder.bindSelectField(sfTo2, MappingPropertyUtils
						.getDayEditor(sfMonth.getSelectedIndex()), true);
				sfTo2.setSelectedIndex(sfTo2.getModel().size()-1);
				if (radDept.isSelected()&&chkNV.isSelected()){
					sfFrom2.setEnabled(true);
					sfTo2.setEnabled(true);
				}else{
					sfFrom2.setEnabled(false);
					sfTo2.setEnabled(false);
				}
			}
		});
		chkTo.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chkTo.isSelected()){
					sfTo.setEnabled(true);
					chkAllData.setVisible(false);
/*										if (sfFrom.getSelectedItem().equals(sfTo.getSelectedItem())&&type==TYPE_SEARCH){
											chkAllData.setVisible(true);
										}else{
											chkAllData.setVisible(false);
										}*/
				}else{
					sfTo.setEnabled(false);
					chkAllData.setVisible(true);
					//					if (type==TYPE_SEARCH)
					//					chkLossOnly.setEnabled(true);					
				}
			}
		});
		sfTo.setEnabled(false);
	}

	private String buildSql(){
		//		Calendar cal = Calendar.getInstance();
		//		int month = (Integer)ListBinder.get(sfMonth);
		//		int year = (Integer)ListBinder.get(sfYear);
		//		boolean current = false;
		//		if (cal.get(Calendar.MONTH)>=month&&cal.get(Calendar.YEAR)>=year){
		//		current=true;
		//		}
		Calendar from = Calendar.getInstance();
		from.set(Calendar.DAY_OF_MONTH, (Integer)ListBinder.get(sfFrom));
		from.set(Calendar.MONTH, (Integer)ListBinder.get(sfMonth));
		from.set(Calendar.YEAR, (Integer)ListBinder.get(sfYear));
		Calendar to = null;
		if (chkTo.isSelected()&&sfFrom.getSelectedIndex()<sfTo.getSelectedIndex()){
			to = Calendar.getInstance();
			to.set(Calendar.DAY_OF_MONTH, (Integer)ListBinder.get(sfTo));
			to.set(Calendar.MONTH, (Integer)ListBinder.get(sfMonth));
			to.set(Calendar.YEAR, (Integer)ListBinder.get(sfYear));
		}
		Calendar from2 = Calendar.getInstance();
		from2.set(Calendar.DAY_OF_MONTH, (Integer)ListBinder.get(sfFrom2));
		from2.set(Calendar.MONTH, (Integer)ListBinder.get(sfMonth));
		from2.set(Calendar.YEAR, (Integer)ListBinder.get(sfYear));
		Calendar to2 = Calendar.getInstance();
		to2.set(Calendar.DAY_OF_MONTH, (Integer)ListBinder.get(sfTo2));
		to2.set(Calendar.MONTH, (Integer)ListBinder.get(sfMonth));
		to2.set(Calendar.YEAR, (Integer)ListBinder.get(sfYear));

		String checkData = "";
		String checkDataVS = "";
		String sunday=" and to_char(t.dates,'DY')<>'SUN'";
		program.printable=false;
		Map<String, String> pp = program.printParams = new HashMap<String, String>();
		//		pp.put("month", sfMonth.getSelectedItem().toString());
		//		pp.put("year", sfYear.getSelectedItem().toString());
		program.getDao().setMonth(sfMonth.getSelectedItem().toString());
		program.getDao().setYear(sfYear.getSelectedItem().toString());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (!chkAllData.isVisible()||!chkAllData.isSelected()){
			checkData = " and t.check_data='N'";
			checkDataVS = "and (t.check_data='N' or t.real_ot>0)";
			program.printable=true;
		}
		if (chkSunday.isSelected()){
			sunday = "";
		}

		String sql="";
		String printDate="";
		if (!chkNV.isSelected()&&!chkVS.isSelected()){
			sql = 	"select t.empsn, e.fname||' '||e.lname as empna, d.name_dept as dept_name, e.empcn, t.dates, t.t_in as tt_in, " +
					"t.t_mid as tt_mid, t.t_out as tt_out, t.t_over as tt_over, t.note, t.id_shift " +
					"from n_data_daily t, n_employee e, n_department d where t.empsn=e.empsn and e.depsn=d.id_dept "+
					"and e.user_manage_id in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + " ";
			if (to!=null){
				sql = sql + "and t.dates>=? and t.dates<=? ";
				params = new Object[]{new java.sql.Date(from.getTimeInMillis()), new java.sql.Date(to.getTimeInMillis())};
				printDate = sdf.format(from.getTime()) + "-" + sdf.format(to.getTime());
			}else{
				sql = sql + "and t.dates=? ";
				params = new Object[]{new java.sql.Date(from.getTimeInMillis())};
				printDate = sdf.format(from.getTime());
			}
			sql = sql + checkData + sunday + " and " + buildCondition() + " order by d.name_dept";
		}else if (chkNV.isSelected()){
			sql = 	"select t.empsn, e.fname||' '||e.lname as empna, d.name_dept as dept_name, e.empcn, t.dates, t.t_in as tt_in, " +
					"t.t_mid as tt_mid, t.t_out as tt_out, t.t_over as tt_over, t.note, t.id_shift " +
					"from n_data_daily t, n_employee e, n_department d, n_emp_quit q "+
					"where t.empsn=e.empsn and t.empsn=q.empsn and d.id_dept=q.depsn "+
					"and e.user_manage_id in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + " ";
			java.sql.Date d1 = new java.sql.Date(from.getTimeInMillis());
			java.sql.Date d2 =null;
			java.sql.Date d3= new java.sql.Date(from2.getTimeInMillis());
			java.sql.Date d4= new java.sql.Date(to2.getTimeInMillis());

			if (to!=null){
				sql = sql + "and t.dates>=? and t.dates<=? ";
				d2 =  new java.sql.Date(to.getTimeInMillis());
				printDate = sdf.format(from.getTime()) + "-" + sdf.format(to.getTime()) + "<br/>(NV: " + sdf.format(from2.getTime()) + "-" + sdf.format(to2.getTime()) + ")";
			}else{
				sql = sql + "and t.dates=? ";
				printDate = sdf.format(from.getTime()) + "<br/>(NV: " + sdf.format(from2.getTime()) + "-" + sdf.format(to2.getTime()) + ")";
			}
			sql = sql + " and q.real_off_date>=? and q.real_off_date<=?";
			sql = sql + checkData + sunday + " and " + buildCondition() + " order by d.name_dept";
			if (d2!=null)
				params = new Object[]{d1,d2,d3,d4};
			else
				params = new Object[]{d1,d3,d4};
		}else if(chkVS.isSelected()){
			java.sql.Date d1 = new java.sql.Date(from.getTimeInMillis());
			java.sql.Date d2 = null;
			sql = 	"select t.empsn, e.fname||' '||e.lname as empna, d.name_dept as dept_name, e.empcn, t.dates, t.t_in as tt_in, " +
					"t.t_mid as tt_mid, t.t_out as tt_out, t.t_over as tt_over, t.note, t.id_shift " +
					"from n_data_daily t, n_register_shift r, n_employee e, n_department d "+
					"where t.empsn=r.empsn and t.dates=r.shift_date and t.empsn=e.empsn and d.id_dept=e.depsn " +
					"and e.user_manage_id in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + " ";

			if (to!=null){
				sql = sql + "and t.dates>=? and t.dates<=? ";
				d2 =  new java.sql.Date(to.getTimeInMillis());
				params = new Object[]{d1,d2};
				printDate = sdf.format(from.getTime()) + "-" + sdf.format(to.getTime());
			}else{
				sql = sql + "and t.dates=? ";
				params = new Object[]{d1};
				printDate = sdf.format(from.getTime());
			}
			sql = sql + checkDataVS + sunday + " and r.note like '7H%' and " + buildCondition() + " order by d.name_dept";

		}
		pp.put("DATE", printDate);
		return "select * from (" + sql + ") where rownum<=10000";
	}

	private void doPrintDept(ActionEvent e) {
		if (!radDept.isSelected()){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Chọn đơn vị cần in danh sách");
			return;
		}
		if (sfFact.getSelectedIndex()<0){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Chọn xưởng cần thao tác");
			return;
		}
		if (chkTo.isSelected()){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Chỉ chọn 1 ngày");
			return;
		}
		String sql = buildSql();
		sql = "select * from (select distinct d.name_dept as dept_name from n_data_daily t" + 
		StringUtils.substringAfter(sql, "from n_data_daily t");
		System.out.println(sql);
		List<String> list = program.getDao().getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString(1);
			}
		}, params);
		Collections.sort(list);
		List<OneField> jrlist = new ArrayList<OneField>();
		for (String str:list){
			jrlist.add(new OneField(Vni2Uni.convertToUnicode(str)));
		}
		//TODO fill to report
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(jrlist);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dept", program.printParams.get("DEPARTMENT"));
		params.put("date", sfFrom.getSelectedItem().toString()+"/"+sfMonth.getSelectedItem().toString()+"/"+sfYear.getSelectedItem().toString());
		try {
			JasperDesign jd = JRXmlLoader.load(ReportFileManager.getReportFormatFolder("fvhr/indsdv.jrxml"));
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);
			ReportUtils.doExportPdf(jp, "DSDV");
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	public class OneField{
		private String DEPT_NAME;
		public OneField(){}
		public OneField(String name){
			this.DEPT_NAME=name;
		}
		public String getDEPT_NAME(){
			return this.DEPT_NAME;
		}
		public void setDEPT_NAME(String dept){
			this.DEPT_NAME=dept;
		}
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Default.Window");
		this.setTitle("Dữ liệu quét thẻ hàng ngày");
		this.setHeight(new Extent(445, Extent.PX));
		this.setDefaultCloseOperation(WindowPane.HIDE_ON_CLOSE);
		this.setWidth(new Extent(550, Extent.PX));
		this.setModal(true);
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(360, Extent.PX));
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
		label10.setText("Ngày");
		RowLayoutData label10LayoutData = new RowLayoutData();
		label10LayoutData.setInsets(new Insets(new Extent(14, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		label10.setLayoutData(label10LayoutData);
		row3.add(label10);
		sfFrom = new SelectField();
		row3.add(sfFrom);
		chkTo = new CheckBox();
		chkTo.setText("Đến ngày");
		row3.add(chkTo);
		sfTo = new SelectField();
		sfTo.setDisabledBackground(new Color(0xc0c0c0));
		row3.add(sfTo);
		chkAllData = new CheckBox();
		chkAllData.setText("Bao gồm dữ liệu hoàn chỉnh");
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
		chkSunday = new CheckBox();
		chkSunday.setText("Có đi làm ngày chủ nhật");
		row2.add(chkSunday);
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
		Row row4 = new Row();
		GridLayoutData row4LayoutData = new GridLayoutData();
		row4LayoutData.setColumnSpan(2);
		row4.setLayoutData(row4LayoutData);
		grid2.add(row4);
		Label label11 = new Label();
		label11.setText("Từ ngày");
		RowLayoutData label11LayoutData = new RowLayoutData();
		label11LayoutData.setInsets(new Insets(new Extent(14, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		label11.setLayoutData(label11LayoutData);
		row4.add(label11);
		sfFrom2 = new SelectField();
		sfFrom2.setEnabled(false);
		sfFrom2.setDisabledBackground(new Color(0xc0c0c0));
		row4.add(sfFrom2);
		Label label12 = new Label();
		label12.setText("Đến ngày");
		RowLayoutData label12LayoutData = new RowLayoutData();
		label12LayoutData.setInsets(new Insets(new Extent(14, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		label12.setLayoutData(label12LayoutData);
		row4.add(label12);
		sfTo2 = new SelectField();
		sfTo2.setEnabled(false);
		sfTo2.setDisabledBackground(new Color(0xc0c0c0));
		row4.add(sfTo2);
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
		btnExcec.setText("Xử lý dữ liệu");
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
		btnPrintDept = new Button();
		btnPrintDept.setText("In DS Đơn vị");
		btnPrintDept.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnPrintDept.setActionCommand("dept");
		btnPrintDept.setRolloverBackground(new Color(0x0080ff));
		btnPrintDept.setBorder(new Border(new Extent(1, Extent.PX),
				Color.BLACK, Border.STYLE_SOLID));
		btnPrintDept.setRolloverForeground(new Color(0xff8000));
		btnPrintDept.setRolloverEnabled(true);
		btnPrintDept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doPrintDept(e);
			}
		});
		row1.add(btnPrintDept);
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
