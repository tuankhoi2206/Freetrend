package ds.program.fvhr.ngan.ui.retain_salary;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class N_EMP_RETAIN_SALARYMQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;

	/**
	 * Creates a new <code>N_EMP_RETAIN_SALARYMQuery</code>.
	 */
	public N_EMP_RETAIN_SALARYMQuery() {
		super();

		// Add design-time configured components.
		initComponents();
	}


	protected int doInit() {
		int ret = super.doInit();
		ConditionManager conditionMgr = new ConditionManager();
		setConditionMgr(conditionMgr);


	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor

		//<一般查詢條件定義>

        String key;
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
	}

}