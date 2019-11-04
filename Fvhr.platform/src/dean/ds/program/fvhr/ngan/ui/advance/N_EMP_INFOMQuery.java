package ds.program.fvhr.ngan.ui.advance;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;
import fv.components.DeptSearchPane;
import fv.util.MappingPropertyUtils;
import ds.program.fvhr.ngan.ui.DeptUserControl_FactDetail;
import ds.program.fvhr.son.ui.ObjUtility;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.binder.ListBaseBinder;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class N_EMP_INFOMQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
    private DeptSearchPane dsp;
	String user_up = Application.getApp().getLoginInfo().getUserID();
	String ma_user = "";
	OBJ_EMPSN Obj_e		= new OBJ_EMPSN();
/*    private SelectField sfthang;
	private SelectField sfnam;*/

	/**
	 * Creates a new <code>N_EMP_INFOMQuery</code>.
	 */
	public N_EMP_INFOMQuery() {
		super();

		// Add design-time configured components.
		initComponents();
	//	moreInitUI();
	}
	
/*	protected void moreInitUI(){
		Calendar cal = Calendar.getInstance();
		ListBaseBinder monthBinder = new ListBaseBinder(null, sfthang, MappingPropertyUtils.getJavaMonthEditor());
		monthBinder.objectToComponent(cal.get(Calendar.MONTH));
		ListBaseBinder yearBinder = new ListBaseBinder(null, sfnam, MappingPropertyUtils.getYearEditor(1, false));
		yearBinder.objectToComponent(cal.get(Calendar.YEAR));
	}*/


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
		labelLayout.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
        rootLayout = new Grid();
        rootLayout.setSize(2);
  //      rootLayout.setBorder(new Border(new Extent(1), Color.RED, Border.STYLE_SOLID));
        this.add(rootLayout);
        IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();
        dsp = new DeptSearchPane();
        rootLayout.add(dsp);
        
        Label lb_madv 			= dsp.lblMaDonVi;
        SelectField sfMaDonVi	= dsp.sfMaDonVi;
        
        lb_madv.setVisible(false);
        sfMaDonVi.setVisible(false);
     
      
	}
	

	protected void doQuery() {
	   
		String fc = dsp.getOutputCondition();
		System.out.println(fc);
		if (fc.trim().equals("")){
			fc = " AND 1<>1";
		}
		
		
		String condStr = "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D " +
							" WHERE E.EMPSN=o.EMPSN AND E.DEPSN=D.ID_DEPT " +
							" " + fc +" " +
							" and E.USER_MANAGE_ID in (SELECT MA_QL FROM N_USER_LIMIT WHERE MA_USER= '"+ 
		    		          ""+ma_user+"' and MA_QL=USER_MANAGE_ID))";
		ProgramCondition pc = new ProgramCondition(condStr, new Object[]{});
		getProgram().setBaseCondition(pc);
		super.doQuery();
		//**************
	

	}
}
