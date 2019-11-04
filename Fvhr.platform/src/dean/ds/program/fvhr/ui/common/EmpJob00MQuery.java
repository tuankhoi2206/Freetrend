package ds.program.fvhr.ui.common;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class EmpJob00MQuery extends QueryNormal2 {

	private nextapp.echo2.app.Grid rootLayout;

	private nextapp.echo2.app.Label EMPSN_CaptionLabel;

	private dsc.echo2app.component.DscField EMPSN_DscField1;

	private nextapp.echo2.app.Label ID_JOB_CaptionLabel;

	private dsc.echo2app.component.DscField ID_JOB_DscField2;

	/**
	 * Creates a new <code>EmpJob00MQuery</code>.
	 */
	public EmpJob00MQuery() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		ConditionManager conditionMgr = new ConditionManager();
		setConditionMgr(conditionMgr);

		// 使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
		// TODO 請修改元件之PropertyEditor

		// <一般查詢條件定義>
		conditionMgr.addCondition(EMPSN_DscField1, new QryCondition("EMPSN",
				QryCondition.Operator.EQ, QryCondition.DataType.STRING));
		conditionMgr.addCondition(ID_JOB_DscField2, new QryCondition("ID_JOB",
				QryCondition.Operator.EQ, QryCondition.DataType.STRING));

		String key;
		key = "N_N_EMP_JOB.EMPSN";
		try {
			key = uicaption.getResourceBundle().getString(key);
		} catch (Exception e) {
		}
		EMPSN_CaptionLabel.setText(key);
		key = "N_N_EMP_JOB.ID_JOB";
		try {
			key = uicaption.getResourceBundle().getString(key);
		} catch (Exception e) {
		}
		ID_JOB_CaptionLabel.setText(key);
		return ret;

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
		EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
		EMPSN_CaptionLabel.setText("");
		rootLayout.add(EMPSN_CaptionLabel);
		EMPSN_DscField1 = new dsc.echo2app.component.DscField();
		EMPSN_DscField1.setId("EMPSN_DscField1");
		rootLayout.add(EMPSN_DscField1);
		ID_JOB_CaptionLabel = new nextapp.echo2.app.Label();
		ID_JOB_CaptionLabel.setText("");
		rootLayout.add(ID_JOB_CaptionLabel);
		ID_JOB_DscField2 = new dsc.echo2app.component.DscField();
		ID_JOB_DscField2.setId("ID_JOB_DscField2");
		rootLayout.add(ID_JOB_DscField2);
	}

}
