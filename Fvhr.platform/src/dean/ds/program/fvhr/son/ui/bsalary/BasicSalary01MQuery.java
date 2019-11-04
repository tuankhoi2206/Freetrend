package ds.program.fvhr.son.ui.bsalary;

import java.util.Date;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import ds.program.fvhr.son.ui.DeptUserControl;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class BasicSalary01MQuery extends QueryNormal2 {

	private nextapp.echo2.app.Grid rootLayout;
    DeptUserControl	dept_ctrl;
	/**
	 * Creates a new <code>BasicSalary01MQuery</code>.
	 */
	public BasicSalary01MQuery() {
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
		String empsn 	= dept_ctrl.getEmpsn().trim();
		Date   date		= dept_ctrl.getDate();
		
		if(!empsn.equals("") && date != null){
			sql = "o.EMPSN=? and o.DATE_EFFECT = ?";
			getProgram().query(sql, new Object[]{empsn,date});
		}else
		if (!empsn.equals("") && date == null){
			sql = "o.EMPSN=? ";
			getProgram().query(sql, new Object[]{empsn});
		}else
		if(empsn.equals("")&& date != null){
			
			String fact 	= dept_ctrl.getFact().trim()+"%";
			String group	= dept_ctrl.getGroup().trim()+"%";
			String dept		= dept_ctrl.getNameDept().trim()+"%";
			
				sql = sql + "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D " +
						"								WHERE E.EMPSN=o.EMPSN " +
						"									AND E.DEPSN=D.ID_DEPT " +
						"									AND D.NAME_FACT like ? " +
						"									AND D.NAME_GROUP like ? " +
						"									AND D.NAME_DEPT like ? )" +
						" 	and o.DATE_EFFECT = ?";
				getProgram().query(sql, new Object[]{fact,group,dept,date});
			
		}else{
			
			sql = "o.EMPSN=? ";
			getProgram().query(sql, new Object[]{"0"});
			
		}
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
        rootLayout.setInsets(new Insets(20));
        add(rootLayout);
        dept_ctrl	= new DeptUserControl();
        rootLayout.add(dept_ctrl);
        
	}

}
