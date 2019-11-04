package ds.program.fvhr.ui.quitworksalary;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Row;
import dsc.echo2app.component.DscDateField;
import fv.util.ApplicationHelper;
import fv.util.DbUtils;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.MappingPropertyUtils;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Extent;
import dsc.echo2app.component.DscGroupCheckBox;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

public class AttQuitQuery extends QueryNormal2 {

	private ResourceBundle resourceBundle;
	private SelectField sfMonth;
	private SelectField sfYear;
	private SelectField sfFact;
	private RadioButton radTV;
	private RadioButton radBV;
	private DscDateField dfDate;
	private DscGroupCheckBox groupFVL;
	private CheckBox chkFv1;
	private CheckBox chkFv2;
	private CheckBox chkFv3;
	private CheckBox chkFv4;
	private CheckBox chkFv5;
	private CheckBox chkOther;
	/**
	 * Creates a new <code>AttQuitQuery</code>.
	 */
	public AttQuitQuery() {
		super();

		// Add design-time configured components.
		initComponents();
		
		ListBinder.bindSelectField(sfMonth, MappingPropertyUtils.getJavaMonthEditor(), true);
		ListBinder.bindSelectField(sfYear, MappingPropertyUtils.getYearEditor(1, false), true);
		ListBinder.bindSelectField(sfFact, FVGenericInfo.getFactories(), false);
		dfDate.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dfDate.getDateChooser().setLocale(new Locale("en"));
		Calendar cal = Calendar.getInstance();
		ListBinder.refreshIndex(sfMonth, cal.get(Calendar.MONTH));
		ListBinder.refreshIndex(sfYear, cal.get(Calendar.YEAR));
		ListBinder.refreshIndex(sfFact, "FVL");
		dfDate.setSelectedDate(cal);
	}
	
	@Override
	public AttQuit01Program getProgram() {
		return (AttQuit01Program) super.getProgram();
	}
	
	@Override
	protected int doInit() {
		int ret = super.doInit();
		
		return ret;
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
			} else {
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
	
	@Override
	protected void doQuery() {
		getProgram().updateDAO(sfMonth.getSelectedItem().toString(), sfYear.getSelectedItem().toString());
		String sql="";
		
		sql = sql + "o.DOT_TV=? AND o.DEPSN IN (SELECT D.ID_DEPT FROM N_DEPARTMENT D WHERE o.DEPSN=D.ID_DEPT AND D.NAME_FACT=? " + getGroupFVLConditionString() + ")";
		if (radTV.isSelected()){
			sql = sql + " AND o.CLASS IS NULL";
		}else{
			sql = sql + " AND o.CLASS='BV'";
		}
		sql = sql + " AND o.EMPSN in (select e.EMPSN from n_EMPLOYEE e where e.EMPSN=o.EMPSN and e.USER_MANAGE_ID in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + ")";
		ProgramCondition pc = new ProgramCondition(sql, new Object[]{new java.sql.Date(dfDate.getSelectedDate().getTimeInMillis()), ListBinder.get(sfFact)});
		getProgram().setQueryCondition(pc);
		getProgram().refresh();
	}
	
	public void updateModel(int monthIndex, int year, Calendar date, boolean tv) {
		sfMonth.setSelectedIndex(monthIndex);
		ListBinder.refreshIndex(sfYear, year);
		dfDate.setSelectedDate(date);
		if (tv) radTV.setSelected(true); else radBV.setSelected(true);
	}

	private void factChanged(ActionEvent e) {
		if (sfFact.getSelectedItem().toString().equals("FVL")){
			groupFVL.setEnabled(true);
		}else{
			groupFVL.setEnabled(false);
		}
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(3, Extent.PX)));
		add(grid1);
		Label label1 = new Label();
		label1.setText("Lương thôi việc tháng");
		GridLayoutData label1LayoutData = new GridLayoutData();
		label1LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		label1.setLayoutData(label1LayoutData);
		grid1.add(label1);
		Row row1 = new Row();
		grid1.add(row1);
		sfMonth = new SelectField();
		row1.add(sfMonth);
		Label label2 = new Label();
		label2.setText("năm");
		row1.add(label2);
		sfYear = new SelectField();
		row1.add(sfYear);
		Label label3 = new Label();
		label3.setText("Đợt thôi việc");
		GridLayoutData label3LayoutData = new GridLayoutData();
		label3LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		label3.setLayoutData(label3LayoutData);
		grid1.add(label3);
		dfDate = new DscDateField();
		grid1.add(dfDate);
		Label label4 = new Label();
		label4.setText("Xưởng");
		GridLayoutData label4LayoutData = new GridLayoutData();
		label4LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		label4.setLayoutData(label4LayoutData);
		grid1.add(label4);
		sfFact = new SelectField();
		sfFact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				factChanged(e);
			}
		});
		grid1.add(sfFact);
		Label label5 = new Label();
		grid1.add(label5);
		groupFVL = new DscGroupCheckBox();
		groupFVL.setSize(3);
		grid1.add(groupFVL);
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
		Label label6 = new Label();
		grid1.add(label6);
		Row row2 = new Row();
		grid1.add(row2);
		radTV = new RadioButton();
		radTV.setSelected(true);
		radTV.setText("Thôi việc");
		ButtonGroup loai = new ButtonGroup();
		radTV.setGroup(loai);
		row2.add(radTV);
		radBV = new RadioButton();
		radBV.setText("Bỏ việc");
		radBV.setGroup(loai);
		row2.add(radBV);
	}

}
