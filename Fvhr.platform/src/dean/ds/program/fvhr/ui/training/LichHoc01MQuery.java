package ds.program.fvhr.ui.training;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class LichHoc01MQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label ID_KHOA_CaptionLabel;
    private dsc.echo2app.component.DscField ID_KHOA_DscField2;
    private nextapp.echo2.app.Label ID_MON_CaptionLabel;
    private dsc.echo2app.component.DscField ID_MON_DscField3;

	/**
	 * Creates a new <code>LichHoc01MQuery</code>.
	 */
	public LichHoc01MQuery() {
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
        conditionMgr.addCondition(ID_KHOA_DscField2, new QryCondition("ID_KHOA", QryCondition.Operator.EQ, QryCondition.DataType.STRING));
        conditionMgr.addCondition(ID_MON_DscField3, new QryCondition("ID_MON", QryCondition.Operator.EQ, QryCondition.DataType.STRING));

        String key;
        key = "N_TRAINING_ITEM.EMPSN";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        EMPSN_CaptionLabel.setText(key);
        key = "N_TRAINING_ITEM.ID_KHOA";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        ID_KHOA_CaptionLabel.setText(key);
        key = "N_TRAINING_ITEM.ID_MON";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        ID_MON_CaptionLabel.setText(key);
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
        ID_KHOA_CaptionLabel = new nextapp.echo2.app.Label();
        ID_KHOA_CaptionLabel.setText("");
        rootLayout.add(ID_KHOA_CaptionLabel);
        ID_KHOA_DscField2 = new dsc.echo2app.component.DscField();
        ID_KHOA_DscField2.setId("ID_KHOA_DscField2");
        rootLayout.add(ID_KHOA_DscField2);
        ID_MON_CaptionLabel = new nextapp.echo2.app.Label();
        ID_MON_CaptionLabel.setText("");
        rootLayout.add(ID_MON_CaptionLabel);
        ID_MON_DscField3 = new dsc.echo2app.component.DscField();
        ID_MON_DscField3.setId("ID_MON_DscField3");
        rootLayout.add(ID_MON_DscField3);
	}

}
