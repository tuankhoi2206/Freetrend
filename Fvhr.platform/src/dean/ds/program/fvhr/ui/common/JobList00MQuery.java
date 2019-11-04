package ds.program.fvhr.ui.common;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;

import org.apache.commons.lang.StringUtils;

import dsc.echo2app.program.QueryNormal2;
import fv.util.Vni2Uni;

public class JobList00MQuery extends QueryNormal2 {

	private nextapp.echo2.app.Grid rootLayout;

	private nextapp.echo2.app.Label NAME_CaptionLabel;

	private dsc.echo2app.component.DscField NAME_DscField1;

	private nextapp.echo2.app.Label MONEY_CaptionLabel;

	private dsc.echo2app.component.DscField MONEY_DscField2;

	/**
	 * Creates a new <code>JobList00MQuery</code>.
	 */
	public JobList00MQuery() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		// ConditionManager conditionMgr = new ConditionManager();
		// setConditionMgr(conditionMgr);
		//
		//
		// //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
		// //TODO 請修改元件之PropertyEditor
		//
		// //<一般查詢條件定義>
		// conditionMgr.addCondition(NAME_DscField1, new QryCondition("NAME",
		// QryCondition.Operator.EQ, QryCondition.DataType.STRING));
		// conditionMgr.addCondition(MONEY_DscField2, new QryCondition("MONEY",
		// QryCondition.Operator.EQ, QryCondition.DataType.NUMERIC));

		String key;
		key = "N_N_JOB.NAME";
		try {
			key = uicaption.getResourceBundle().getString(key);
		} catch (Exception e) {
		}
		NAME_CaptionLabel.setText(key);
		key = "N_N_JOB.MONEY";
		try {
			key = uicaption.getResourceBundle().getString(key);
		} catch (Exception e) {
		}
		MONEY_CaptionLabel.setText(key);
		return ret;

	}

	@Override
	protected void doQuery() {
		String query = "";
		// if
		// (NAME_DscField1.getText().trim().equals("")&&MONEY_DscField2.getText().trim().equals("")){
		// return;
		// }
		if (!NAME_DscField1.getText().trim().equals("")) {
			query = query
					+ " and lower(o.NAME) like '%"
					+ Vni2Uni.convertToVNI(NAME_DscField1.getText().trim()
							.toLowerCase()) + "%'";
		}
		if (!MONEY_DscField2.getText().trim().equals("")) {
			query = query + " and o.MONEY="
					+ Integer.valueOf(MONEY_DscField2.getText().trim());
		}
		query = StringUtils.substringAfter(query, " and");
		getProgram().query(query, new Object[] {});
	}

	/**
	 * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		rootLayout = new Grid();
		rootLayout.setWidth(new Extent(100, Extent.PERCENT));
		rootLayout.setSize(2);
		add(rootLayout);
		NAME_CaptionLabel = new nextapp.echo2.app.Label();
		NAME_CaptionLabel.setText("");
		rootLayout.add(NAME_CaptionLabel);
		NAME_DscField1 = new dsc.echo2app.component.DscField();
		NAME_DscField1.setId("NAME_DscField1");
		rootLayout.add(NAME_DscField1);
		MONEY_CaptionLabel = new nextapp.echo2.app.Label();
		MONEY_CaptionLabel.setText("");
		rootLayout.add(MONEY_CaptionLabel);
		MONEY_DscField2 = new dsc.echo2app.component.DscField();
		MONEY_DscField2.setId("MONEY_DscField2");
		rootLayout.add(MONEY_DscField2);
	}

}
