package ds.program.fvhr.ui.workpoints;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class NGetData00MQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label YEARS_CaptionLabel;
    private nextapp.echo2.app.SelectField YEARS_SelectField1;
    private nextapp.echo2.app.Label MONTHS_CaptionLabel;
    private nextapp.echo2.app.SelectField MONTHS_SelectField2;

	/**
	 * Creates a new <code>NGetData00MQuery</code>.
	 */
	public NGetData00MQuery() {
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
        conditionMgr.addCondition(EMPSN_DscField1, new QryCondition("EMPSN", QryCondition.Operator.EQ, QryCondition.DataType.STRING));
        conditionMgr.addCondition(YEARS_SelectField1, new QryCondition("YEARS", QryCondition.Operator.EQ, QryCondition.DataType.STRING));
        conditionMgr.addCondition(MONTHS_SelectField2, new QryCondition("MONTHS", QryCondition.Operator.EQ, QryCondition.DataType.STRING));

        String key;
        key = "N_GET_DATA.EMPSN";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        EMPSN_CaptionLabel.setText(key);
        key = "N_GET_DATA.YEARS";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        YEARS_CaptionLabel.setText(key);
        key = "N_GET_DATA.MONTHS";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        MONTHS_CaptionLabel.setText(key);
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
        YEARS_CaptionLabel = new nextapp.echo2.app.Label();
        YEARS_CaptionLabel.setText("");
        rootLayout.add(YEARS_CaptionLabel);
        YEARS_SelectField1 = new nextapp.echo2.app.SelectField();
        YEARS_SelectField1.setId("YEARS_SelectField1");
        rootLayout.add(YEARS_SelectField1);
        MONTHS_CaptionLabel = new nextapp.echo2.app.Label();
        MONTHS_CaptionLabel.setText("");
        rootLayout.add(MONTHS_CaptionLabel);
        MONTHS_SelectField2 = new nextapp.echo2.app.SelectField();
        MONTHS_SelectField2.setId("MONTHS_SelectField2");
        rootLayout.add(MONTHS_SelectField2);
	}

}
