package ds.program.fvhr.ui.training;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class KhoaHoc00DQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label MA_KHOA_CaptionLabel;
    private dsc.echo2app.component.DscField MA_KHOA_DscField1;
    private nextapp.echo2.app.Label TEN_KHOA_CaptionLabel;
    private dsc.echo2app.component.DscField TEN_KHOA_DscField2;
    private nextapp.echo2.app.Label TRAINING_TYPE_CaptionLabel;
    private nextapp.echo2.app.SelectField TRAINING_TYPE_SelectField1;
    private nextapp.echo2.app.Label DEPSN_CaptionLabel;
    private dsc.echo2app.component.DscField DEPSN_DscField3;

	/**
	 * Creates a new <code>KhoaHoc00DQuery</code>.
	 */
	public KhoaHoc00DQuery() {
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
        conditionMgr.addCondition(MA_KHOA_DscField1, new QryCondition("MA_KHOA", QryCondition.Operator.EQ, QryCondition.DataType.STRING));
        conditionMgr.addCondition(TEN_KHOA_DscField2, new QryCondition("TEN_KHOA", QryCondition.Operator.EQ, QryCondition.DataType.STRING));
        conditionMgr.addCondition(TRAINING_TYPE_SelectField1, new QryCondition("TRAINING_TYPE", QryCondition.Operator.EQ, QryCondition.DataType.STRING));
        conditionMgr.addCondition(DEPSN_DscField3, new QryCondition("DEPSN", QryCondition.Operator.EQ, QryCondition.DataType.STRING));

        String key;
        key = "N_KHOA_HOC.MA_KHOA";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        MA_KHOA_CaptionLabel.setText(key);
        key = "N_KHOA_HOC.TEN_KHOA";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        TEN_KHOA_CaptionLabel.setText(key);
        key = "N_KHOA_HOC.TRAINING_TYPE";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        TRAINING_TYPE_CaptionLabel.setText(key);
        key = "N_KHOA_HOC.DEPSN";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        DEPSN_CaptionLabel.setText(key);
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
        MA_KHOA_CaptionLabel = new nextapp.echo2.app.Label();
        MA_KHOA_CaptionLabel.setText("");
        rootLayout.add(MA_KHOA_CaptionLabel);
        MA_KHOA_DscField1 = new dsc.echo2app.component.DscField();
        MA_KHOA_DscField1.setId("MA_KHOA_DscField1");
        rootLayout.add(MA_KHOA_DscField1);
        TEN_KHOA_CaptionLabel = new nextapp.echo2.app.Label();
        TEN_KHOA_CaptionLabel.setText("");
        rootLayout.add(TEN_KHOA_CaptionLabel);
        TEN_KHOA_DscField2 = new dsc.echo2app.component.DscField();
        TEN_KHOA_DscField2.setId("TEN_KHOA_DscField2");
        rootLayout.add(TEN_KHOA_DscField2);
        TRAINING_TYPE_CaptionLabel = new nextapp.echo2.app.Label();
        TRAINING_TYPE_CaptionLabel.setText("");
        rootLayout.add(TRAINING_TYPE_CaptionLabel);
        TRAINING_TYPE_SelectField1 = new nextapp.echo2.app.SelectField();
        TRAINING_TYPE_SelectField1.setId("TRAINING_TYPE_SelectField1");
        rootLayout.add(TRAINING_TYPE_SelectField1);
        DEPSN_CaptionLabel = new nextapp.echo2.app.Label();
        DEPSN_CaptionLabel.setText("");
        rootLayout.add(DEPSN_CaptionLabel);
        DEPSN_DscField3 = new dsc.echo2app.component.DscField();
        DEPSN_DscField3.setId("DEPSN_DscField3");
        rootLayout.add(DEPSN_DscField3);
	}

}
