package ds.program.fvhr.baby.ui;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class N_CHANGE_ICCARD01MQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label EMPCN_NEW_CaptionLabel;
    private dsc.echo2app.component.DscField EMPCN_NEW_DscField2;
    private nextapp.echo2.app.Label EMPCN_OLD_CaptionLabel;
    private dsc.echo2app.component.DscField EMPCN_OLD_DscField3;
    private nextapp.echo2.app.Label DATE_CHANGE_CaptionLabel;
    private dsc.echo2app.component.DscField DATE_CHANGE_DscField4;

	/**
	 * Creates a new <code>N_CHANGE_ICCARD01MQuery</code>.
	 */
	public N_CHANGE_ICCARD01MQuery() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	protected void doQuery() {
		// TODO Auto-generated method stub
		super.doQuery();
	}
	
	
	@Override
	protected int doInit() {
		int ret = super.doInit();
		ConditionManager conditionMgr = new ConditionManager();
		setConditionMgr(conditionMgr);


	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor

		//<一般查詢條件定義>
        conditionMgr.addCondition(EMPSN_DscField1, new QryCondition("EMPSN", QryCondition.Operator.LIKE, QryCondition.DataType.STRING));
        conditionMgr.addCondition(EMPCN_NEW_DscField2, new QryCondition("EMPCN_NEW", QryCondition.Operator.LIKE, QryCondition.DataType.STRING));
        conditionMgr.addCondition(EMPCN_OLD_DscField3, new QryCondition("EMPCN_OLD", QryCondition.Operator.LIKE, QryCondition.DataType.STRING));
        conditionMgr.addCondition(DATE_CHANGE_DscField4, new QryCondition("DATE_CHANGE", QryCondition.Operator.EQ, QryCondition.DataType.DATE));

        String key;
        key = "N_CHANGE_ICCARD.EMPSN";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        EMPSN_CaptionLabel.setText(key);
        key = "N_CHANGE_ICCARD.EMPCN_NEW";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        EMPCN_NEW_CaptionLabel.setText(key);
        key = "N_CHANGE_ICCARD.EMPCN_OLD";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        EMPCN_OLD_CaptionLabel.setText(key);
        key = "N_CHANGE_ICCARD.DATE_CHANGE";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        DATE_CHANGE_CaptionLabel.setText(key);
		return ret;

	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
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
        EMPCN_NEW_CaptionLabel = new nextapp.echo2.app.Label();
        EMPCN_NEW_CaptionLabel.setText("");
        rootLayout.add(EMPCN_NEW_CaptionLabel);
        EMPCN_NEW_DscField2 = new dsc.echo2app.component.DscField();
        EMPCN_NEW_DscField2.setId("EMPCN_NEW_DscField2");
        rootLayout.add(EMPCN_NEW_DscField2);
        EMPCN_OLD_CaptionLabel = new nextapp.echo2.app.Label();
        EMPCN_OLD_CaptionLabel.setText("");
        rootLayout.add(EMPCN_OLD_CaptionLabel);
        EMPCN_OLD_DscField3 = new dsc.echo2app.component.DscField();
        EMPCN_OLD_DscField3.setId("EMPCN_OLD_DscField3");
        rootLayout.add(EMPCN_OLD_DscField3);
        DATE_CHANGE_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_CHANGE_CaptionLabel.setText("");
        rootLayout.add(DATE_CHANGE_CaptionLabel);
        DATE_CHANGE_DscField4 = new dsc.echo2app.component.DscField();
        DATE_CHANGE_DscField4.setId("DATE_CHANGE_DscField4");
        rootLayout.add(DATE_CHANGE_DscField4);
	}

}
