package ds.program.fvhr.son.ui.job;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class N_JOB_INFO_01MQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
    private DscField 	codeJobDscField;
    private DscField	factDscField;
	/**
	 * Creates a new <code>N_JOB_INFO_01MQuery</code>.
	 */
	public N_JOB_INFO_01MQuery() {
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
		String sql = "";
			
				sql = sql + "    o.CODE_JOB = ? " +
							" OR o.IN_FACT 	= ? " ;
//				getProgram().query(sql, new Object[]{fact,group,dept,date});
					
			getProgram().query(sql, new Object[]{codeJobDscField.getText().trim(),factDscField.getText().trim()});
			
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
        
        codeJobDscField	= new DscField();
        rootLayout.add(new Label("Mã Công Việc"));
        rootLayout.add(codeJobDscField);
        
        factDscField	= new DscField();
        rootLayout.add(new Label("Xưởng"));
        rootLayout.add(factDscField);
        
	}

}
