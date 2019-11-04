package ds.program.fvhr.son.ui.data_main;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class N_DATA_MAIN_01MQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;

	/**
	 * Creates a new <code>N_DATA_MAIN_01MQuery</code>.
	 */
	public N_DATA_MAIN_01MQuery() {
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
	

	protected void doQuery() {
		// TODO Auto-generated method stub
		String sql = "";
		/*sql = "o.EMPCN in (select t.EMPCN from N_DATA_MAIN t, N_EMPLOYEE ei" +
				"							where t.EMPCN	= ei.EMPCN" +
				"								and ei.EMPSN = '" +"11111111" +"')" ;*/
		sql = "o.EMPCN in (select t.EMPCN from N_DATA_MAIN t " +
		"							where t.EMPCN	= '" +"111" +"')" ;
		getProgram().query(sql, new Object[]{});
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