package ds.program.fvhr.ngan.ui.Bonus_End_Of_Year;

import java.util.Calendar;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.layout.GridLayoutData;
import fv.components.DeptSearchPane;
import fv.util.MappingPropertyUtils;
import dsc.echo2app.component.binder.ListBaseBinder;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class N_EMP_BONUS_END_OF_YEARMQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
    private DeptSearchPane dsp;
    private SelectField sfthang;
	private SelectField sfnam;

	/**
	 * Creates a new <code>N_EMP_BONUS_END_OF_YEARMQuery</code>.
	 */
	public N_EMP_BONUS_END_OF_YEARMQuery() {
		super();

		// Add design-time configured components.
		initComponents();
		moreInitUI();
	}
	
	protected void moreInitUI(){
		Calendar cal = Calendar.getInstance();
		ListBaseBinder monthBinder = new ListBaseBinder(null, sfthang, MappingPropertyUtils.getJavaMonthEditor());
		monthBinder.objectToComponent(cal.get(Calendar.MONTH));
		ListBaseBinder yearBinder = new ListBaseBinder(null, sfnam, MappingPropertyUtils.getYearEditor(1, false));
		yearBinder.objectToComponent(cal.get(Calendar.YEAR));
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
		GridLayoutData labelLayout = new GridLayoutData();
        rootLayout = new Grid();
        rootLayout.setWidth(new Extent(100, Extent.PERCENT));
        rootLayout.setSize(2);
        add(rootLayout);
        
        rootLayout.setColumnWidth(0, new Extent(102));
        rootLayout.add(new Label("Thang: "));
        sfthang = new SelectField();
        sfthang.setWidth(new Extent(60, Extent.PX));
        rootLayout.add(sfthang);
        
       
        rootLayout.add(new Label("Nam: "));
        sfnam = new SelectField();
        sfnam.setWidth(new Extent(60, Extent.PX));
        rootLayout.add(sfnam);
        rootLayout.getComponent(0).setLayoutData(labelLayout);
        rootLayout.getComponent(2).setLayoutData(labelLayout);
        
        GridLayoutData dspLayout = new GridLayoutData();
        dspLayout.setColumnSpan(2);
        dsp = new DeptSearchPane();
        dsp.setLayoutData(dspLayout);
        rootLayout.add(dsp); 
	}
	
	protected void doQuery() {
		
		String fc = dsp.getOutputCondition();
		System.out.println(fc);
		if (fc.trim().equals("")){
			fc = " AND 1<>1";
		}
		String condStr = "o.EMPSN IN (SELECT E.EMPSN FROM N_GET_DATA E, N_DEPARTMENT D " +
							" WHERE E.EMPSN=o.EMPSN AND E.DEPSN=D.ID_DEPT " +
							" AND E.MONTHS = '"+sfthang.getSelectedItem().toString()+"' AND E.YEARS = '"+sfnam.getSelectedItem().toString()+
							"' " + fc +")";
		ProgramCondition pc = new ProgramCondition(condStr, new Object[]{});
		getProgram().setBaseCondition(pc);
		super.doQuery();
	}

}
