package ds.program.fvhr.tien.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import ds.program.fvhr.son.ui.DeptUserControl;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class DSHR7100MQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
	private DeptUserControl dept_ctrl;

	/**
	 * Creates a new <code>DSHR7100MQuery</code>.
	 */
	public DSHR7100MQuery() {
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
	@Override
	protected void doQuery() {
		String sql = "";
		String empsn 	= dept_ctrl.getEmpsn().trim();
		Date   date		= dept_ctrl.getDate();
			
		if (!empsn.equals("") && date == null){
			sql = "o.PNL=? ";
			getProgram().query(sql, new Object[]{empsn});
		}
		else
		{ try
		{
			
			List<Object> params = new ArrayList<Object>();
			sql= sql +  " and  o.PNL IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D WHERE E.EMPSN=o.PNL AND E.DEPSN=D.ID_DEPT" ;
			if(!dept_ctrl.getFact().equals(""))
			{
				sql=sql  + " AND D.NAME_FACT ="+"'"+fv.util.Vni2Uni.convertToVNI(dept_ctrl.getFact())+"'";
				
			}
			if(!dept_ctrl.getGroup().equals(""))
			{
				sql=sql+"	AND D.NAME_GROUP ="+"'"+fv.util.Vni2Uni.convertToVNI( dept_ctrl.getGroup())+"'" ;
							}
			if(!dept_ctrl.getNameDept().equals(""))
			{
				sql=sql+ "  AND D.NAME_DEPT_NAME="+ "'"+fv.util.Vni2Uni.convertToVNI(dept_ctrl.getNameDept())+"'";
				
			}
			sql=sql+")";
			if(date!=null)
			{
				SimpleDateFormat sf=new SimpleDateFormat("dd/MM/yyyy");
				String strDate=sf.format(date);
				sql=sql+"  AND o.VDATE=to_date('"+strDate+"','dd/MM/yyyy')  ";
			
				
			}
			
			sql = StringUtils.substringAfter(sql, " and ");
			ProgramCondition pc = new ProgramCondition(sql, params.toArray());			
			getProgram().setQueryCondition(pc);
			getProgram().refresh();
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.print(e.toString());
		}
					
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
