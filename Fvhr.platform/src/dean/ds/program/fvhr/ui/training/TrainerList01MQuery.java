package ds.program.fvhr.ui.training;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscField;

public class TrainerList01MQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_HL_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_HL_DscField1;

	/**
	 * Creates a new <code>TrainerList01MQuery</code>.
	 */
	public TrainerList01MQuery() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		ConditionManager conditionMgr = new ConditionManager();
		setConditionMgr(conditionMgr);


	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor

		//<一般查詢條件定義>
        conditionMgr.addCondition(EMPSN_HL_DscField1, new QryCondition("EMPSN_HL", QryCondition.Operator.LIKE, QryCondition.DataType.STRING));

        String key;
        key = "N_TRAINER_LIST.EMPSN_HL";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        EMPSN_HL_CaptionLabel.setText(key);
		return ret;

	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		rootLayout = new Grid();
		rootLayout.setSize(2);
		add(rootLayout);
		EMPSN_HL_CaptionLabel = new Label();
		EMPSN_HL_CaptionLabel.setText("EMPSN");
		rootLayout.add(EMPSN_HL_CaptionLabel);
		EMPSN_HL_DscField1 = new DscField();
		EMPSN_HL_DscField1.setId("EMPSN_HL_DscField1");
		rootLayout.add(EMPSN_HL_DscField1);
	}

}
