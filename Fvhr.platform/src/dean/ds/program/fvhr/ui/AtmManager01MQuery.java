package ds.program.fvhr.ui;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class AtmManager01MQuery extends QueryNormal2 {

	private nextapp.echo2.app.Grid rootLayout;

	private nextapp.echo2.app.Label BANK_NAME_CaptionLabel;

	private dsc.echo2app.component.DscField BANK_NAME_DscField1;

	/**
	 * Creates a new <code>AtmManager01MQuery</code>.
	 */
	public AtmManager01MQuery() {
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
		conditionMgr.addCondition(BANK_NAME_DscField1, new QryCondition(
				"BANK_NAME", QryCondition.Operator.EQ,
				QryCondition.DataType.STRING));

		String key;
		key = "N_ATM_MANAGER.BANK_NAME";
		try {
			key = uicaption.getResourceBundle().getString(key);
		} catch (Exception e) {
		}
		BANK_NAME_CaptionLabel.setText(key);
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
		BANK_NAME_CaptionLabel = new nextapp.echo2.app.Label();
		BANK_NAME_CaptionLabel.setText("N_ATM_MANAGER.BANK_NAME");
		rootLayout.add(BANK_NAME_CaptionLabel);
		BANK_NAME_DscField1 = new dsc.echo2app.component.DscField();
		BANK_NAME_DscField1.setId("BANK_NAME_DscField1");
		rootLayout.add(BANK_NAME_DscField1);
	}

}
