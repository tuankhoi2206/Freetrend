package ds.program.fvhr.ui.common;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.layout.GridLayoutData;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.query.ConditionManager;
import fv.components.DeptSearchPane;

public class EmpBorm00MQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
	private DeptSearchPane dsp;

	/**
	 * Creates a new <code>EmpBorm00MQuery</code>.
	 */
	public EmpBorm00MQuery() {
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
        GridLayoutData dspLayout = new GridLayoutData();
        dspLayout.setColumnSpan(2);
        dsp = new DeptSearchPane();
        dsp.setLayoutData(dspLayout);
        rootLayout.add(dsp);
	}

	@Override
	protected void doQuery() {
		String fc = dsp.getOutputCondition();
		System.out.println(fc);
		if (fc.trim().equals("")){
			fc = " AND 1<>1";
		}
		String condStr = "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D WHERE E.EMPSN=o.EMPSN AND E.DEPSN=D.ID_DEPT " + fc + ")";
		ProgramCondition pc = new ProgramCondition(condStr, new Object[]{});
		getProgram().setBaseCondition(pc);
		super.doQuery();
	}
}
