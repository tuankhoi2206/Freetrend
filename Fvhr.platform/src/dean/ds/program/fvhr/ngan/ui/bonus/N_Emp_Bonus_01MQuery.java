package ds.program.fvhr.ngan.ui.bonus;

import java.text.SimpleDateFormat;
import java.util.Date;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.ngan.ui.DeptUserControl_FactDetail;
import ds.program.fvhr.son.ui.DeptUserControl;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;
import echopointng.DateField;
import fv.util.Vni2Uni;

public class N_Emp_Bonus_01MQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
   // DeptUserControl	dept_ctrl;
    DeptUserControl_FactDetail dept_ctrl;
    OBJ_EMPSN obj_e 	 = new OBJ_EMPSN();
    String user_up = Application.getApp().getLoginInfo().getUserID();
	String ma_user = "";
	RadioButton rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac;
	SimpleDateFormat sf_ = new SimpleDateFormat(OBJ_UTILITY.DATE_FORMAT_STR);
	private DscField 			tf_date;
	private DateField			dateF_to_date;
    
	/**
	 * Creates a new <code>N_Emp_Bonus_01MQuery</code>.
	 */
	public N_Emp_Bonus_01MQuery() {
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
		String sql1= "";
		String sql2= "";
		String sql3= "";
		String empsn 	= dept_ctrl.getEmpsn().trim();
		Date   date		= dept_ctrl.getDate();
		

		rdio_f1	   = dept_ctrl.rdio_f1;
		rdio_f2	   = dept_ctrl.rdio_f2;
		rdio_f3	   = dept_ctrl.rdio_f3;
		rdio_f5	   = dept_ctrl.rdio_f5;
		rdio_f6	   = dept_ctrl.rdio_f6;
		rdio_khac  = dept_ctrl.rdio_khac;
	
		
		RadioButton rdio_date  = dept_ctrl.rdio_date;
		RadioButton rdio_month = dept_ctrl.rdio_month;
		RadioButton rdio_year  = dept_ctrl.rdio_year;
		
		String	dmy_str		= sf_.format(date);
		String  my_str		= sf_.format(date).substring(3, 10); 
		String  y_str		= sf_.format(date).substring(6, 10); 
	//	String dmy_str_to	= tf_date.getText().toString();
		String dmy_str_to	= dmy_str;
		String  month_str	= sf_.format(date).substring(3,5);
	//	String  year_str	= sf_.format(date).substring(6,10);
		
		if(!empsn.equals("") && date != null){
			if(!obj_e.Kt_vungqly_khi_nhap_st(empsn, ma_user,"DEPSN"))
			{
				return;
			}
			
		//	sql = "o.EMPSN=? and o.DATE_EFFECT = ?";
			sql = "o.EMPSN IN (SELECT EM.EMPSN FROM N_EMPLOYEE EM "+
			      "			  WHERE EM.EMPSN = o.EMPSN ";
			//*Tim theo xuong nhom don vi ma chi thuoc quyen quan ly cua user do
			sql1 = obj_e.find_fact_emp(empsn, "", "","", "", "D", "EM", ma_user,rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac)+")";
		 
		  //*Xuat theo ngay,thang,or nam	
		  sql1 = sql1+ obj_e.select_ngay_thang_nam(rdio_date, rdio_month, rdio_year, dmy_str,dmy_str_to, my_str, y_str, "o", "DATE_EFFECT");
		
		  sql = sql+sql1;
		  getProgram().query(sql, new Object[]{});
		}else
		if (!empsn.equals("") && date == null){
			if(!obj_e.Kt_vungqly_khi_nhap_st(empsn, ma_user,"DEPSN"))
			{
				return;
			}
			sql = "o.EMPSN=? ";
			getProgram().query(sql, new Object[]{empsn});
		}else
		if(empsn.equals("")&& date != null){
			
			String fact 	= dept_ctrl.getFact().trim();
			String group	= dept_ctrl.getGroup().trim();
			String dept		= dept_ctrl.getNameDept().trim();
			String id_dept  = dept_ctrl.getIDDept();
			
			String sql_maql = obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact, group, dept,"DEPSN",id_dept, ma_user,rdio_f1 ,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
			  if(sql_maql.equals("NO"))
			  {
				  OBJ_UTILITY.ShowMessageError("Bạn không có quyền thao tác");
				  return;
			  }
			
				sql = sql + "o.EMPSN IN (SELECT EM.EMPSN FROM N_EMPLOYEE EM , N_DEPARTMENT D" +
						"								WHERE EM.EMPSN = o.EMPSN " +
						"									AND o.DEPSN=D.ID_DEPT " ;
			
						/*"									AND D.NAME_FACT like ? " +
						"									AND D.NAME_GROUP like ? " +
						"									AND D.NAME_DEPT_NAME like ? )" +*/
					/*	" 	and o.DATE_EFFECT = ?";*/
				//*Tim theo xuong nhom don vi ma chi thuoc quyen quan ly cua user do
				sql1 = obj_e.find_fact_emp(empsn, fact, group,dept, id_dept, "D", "EM", ma_user,rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac)+")";
			 
			  //*Xuat theo ngay,thang,or nam	
			  sql1 = sql1+ obj_e.select_ngay_thang_nam(rdio_date, rdio_month, rdio_year, dmy_str,dmy_str_to, my_str, y_str, "o", "DATE_EFFECT");
			
			  sql = sql+sql1;
			  
			  getProgram().query(sql, new Object[]{});
			
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
		
		IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();
		
        rootLayout = new Grid();
        rootLayout.setWidth(new Extent(100, Extent.PERCENT));
        rootLayout.setSize(2);
        rootLayout.setInsets(new Insets(20));
        add(rootLayout);
        dept_ctrl	= new DeptUserControl_FactDetail();
        rootLayout.add(dept_ctrl);
        
       /* Row row_den_ngay = new Row();
        
        tf_date	= new DscField();
		tf_date.setAlignment(new Alignment(Alignment.LEFT, Alignment.LEFT));
		
		
		dateF_to_date	= new DateField();
		dateF_to_date.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dateF_to_date.setTextField(tf_date);
		dateF_to_date.setWidth(new Extent(165));
		row_den_ngay.setInsets(new Insets(5));
		row_den_ngay.setCellSpacing(new Extent(11));
		row_den_ngay.add(new Label("Đến ngày: "));
		row_den_ngay.add(dateF_to_date);
		row_den_ngay.setAlignment(new Alignment(Alignment.CENTER, Alignment.LEFT));
		rootLayout.add(new Label(""));
		rootLayout.add(row_den_ngay);*/
		
		RadioButton rdio_date  = dept_ctrl.rdio_date;
		rdio_date.setVisible(false);
		RadioButton rdio_month = dept_ctrl.rdio_month;
		rdio_month.setSelected(true);
		
		dept_ctrl.getEMPSN_TextField().addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				doQuery();
				
			}
		});
		
		
        
	}

}
