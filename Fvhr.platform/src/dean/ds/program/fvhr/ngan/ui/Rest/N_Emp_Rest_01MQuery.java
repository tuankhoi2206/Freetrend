package ds.program.fvhr.ngan.ui.Rest;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jaxen.function.SubstringFunction;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import fv.util.Vni2Uni;
import ds.program.fvhr.ngan.ui.DeptUserControl_FactDetail;
import ds.program.fvhr.son.ui.ObjUtility;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class N_Emp_Rest_01MQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
    DeptUserControl_FactDetail dept_ctrl;
	String user_up = Application.getApp().getLoginInfo().getUserID();
	String ma_user = "";
	OBJ_EMPSN obj_e = new OBJ_EMPSN();
	OBJ_UTILITY obj_util = new OBJ_UTILITY();
	RadioButton rd_f1,rd_f2,rd_f3,rd_f5,rd_f6,rd_khac;

	/**
	 * Creates a new <code>N_Emp_Rest_01MQuery</code>.
	 */
	public N_Emp_Rest_01MQuery() {
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
		SimpleDateFormat sf_ = new SimpleDateFormat(ObjUtility.DATE_FORMAT_STR);

		String empsn 	= dept_ctrl.getEmpsn().trim();
		Date   date		= dept_ctrl.getDate();
		
	    String fact 	= dept_ctrl.getFact().trim();
		String group	= dept_ctrl.getGroup().trim();
		String dept		= dept_ctrl.getNameDept().trim();
		String id_dept  = dept_ctrl.getIDDept();
		
		rd_f1	   = dept_ctrl.rdio_f1;
		rd_f2	   = dept_ctrl.rdio_f2;
		rd_f3	   = dept_ctrl.rdio_f3;
		rd_f5	   = dept_ctrl.rdio_f5;
		rd_f6	   = dept_ctrl.rdio_f6;
		rd_khac    = dept_ctrl.rdio_khac;
		
		if(date == null)
		{   
				OBJ_UTILITY.ShowMessageError("Chọn năm cần tìm phép");
			
		}else// date !=null
		{
			String year		= sf_.format(date).substring(6,10 );
			if(!empsn.equals(""))
			{   
				if(obj_e.Kt_vungqly_khi_nhap_st(empsn, ma_user,"DEPSN"))
				{
					sql = "o.EMPSN=? and o.YEAR=?";
					getProgram().query(sql, new Object[]{empsn,year});
				}
			}else 
			{
		//	  if(obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact, group, dept, ma_user))
			  String sql_maql = obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact, group, dept,"DEPSN",id_dept, ma_user,rd_f1,rd_f2,rd_f3,rd_f5,rd_f6,rd_khac);
			  if(sql_maql.equals("NO"))
			  {
				  OBJ_UTILITY.ShowMessageError("Bạn không có quyền thao tác");
				  return;
			  }else
			  if(!sql_maql.equals("NO"))
			  {
				   if (!fact.equals("") && group.equals("") && dept.equals(""))
				   {
								sql = sql+ "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D " +
										"								WHERE E.EMPSN=o.EMPSN " +
										"									AND E.DEPSN=D.ID_DEPT " +
										"									AND D.NAME_FACT = ? )" +
										" and o.YEAR = ?";
								getProgram().query(sql, new Object[]{fact,year});
					}else if (!fact.equals("") && !group.equals("") && dept.equals(""))
					{
								sql = sql+ "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D " +
										"								WHERE E.EMPSN=o.EMPSN " +
										"									AND E.DEPSN=D.ID_DEPT " +
										"									AND D.NAME_FACT = ? " +
										"									AND D.NAME_GROUP = ? )" +
										" and o.YEAR = ?";
										
								getProgram().query(sql, new Object[]{fact,group,year});
					}else if (!fact.equals("") && !group.equals("") && !dept.equals(""))
					{
								sql = sql+ "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D " +
										"								WHERE E.EMPSN=o.EMPSN " +
										"									AND E.DEPSN=D.ID_DEPT " +
										"									AND D.NAME_FACT = ? " +
										"									AND D.NAME_GROUP = ? " +
										"									AND D.NAME_DEPT_NAME = ? )" +
										" and o.YEAR = ?";
										
								getProgram().query(sql, new Object[]{fact,group,dept,year});
					}
				  }
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
        dept_ctrl	= new DeptUserControl_FactDetail();
        rootLayout.add(dept_ctrl);
        
        IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();
        
        dept_ctrl.getEMPSN_TextField().addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				
				   doQuery();
				   windowPane.userClose();
				   
				
			}
		});
        
        RadioButton rdio_date  = dept_ctrl.rdio_date;
		RadioButton rdio_month = dept_ctrl.rdio_month;
		RadioButton rdio_year  = dept_ctrl.rdio_year;
		
		rdio_date.setVisible(false);
		rdio_month.setVisible(false);
		rdio_year.setVisible(false);
        
	}

}
