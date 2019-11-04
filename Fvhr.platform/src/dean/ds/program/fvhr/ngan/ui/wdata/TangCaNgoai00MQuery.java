package ds.program.fvhr.ngan.ui.wdata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.ngan.ui.DeptUserControl_FactDetail;
import ds.program.fvhr.son.ui.ObjUtility;
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

public class TangCaNgoai00MQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
    DeptUserControl_FactDetail dept_detail;
    OBJ_EMPSN obj_e 	 = new OBJ_EMPSN();
    OBJ_UTILITY obj_util = new OBJ_UTILITY();
    private DscField 			tf_date;
	private DateField			dateF_to_date;
    HSSFWorkbook wb;
    Label   errLabel;
    WindowPane winPane;
    SimpleDateFormat sf_ = new SimpleDateFormat(OBJ_UTILITY.DATE_FORMAT_STR);
	
    String user_up = Application.getApp().getLoginInfo().getUserID();
	String ma_user = "";
	
	String empsn 	= "";
	Date   date;
	String fact_ 	= "";
	String group_ 	= "";
	String dept_ 	= "";
	String id_dept	= "";
	
	RadioButton rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac;

	/**
	 * Creates a new <code>TangCaNgoai00MQuery</code>.
	 */
	public TangCaNgoai00MQuery() {
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
	
	private boolean kt_dk_thaotac()
	{
		empsn 			= dept_detail.getEmpsn().trim();
		date			= dept_detail.getDate();
		
	    fact_ 	= dept_detail.getFact().trim();
		group_	= dept_detail.getGroup().trim();
		dept_		= Vni2Uni.convertToVNI(dept_detail.getNameDept().trim());
		id_dept  = dept_detail.getIDDept();
		
		if(empsn.equals("") && fact_.equals(""))
		{
			OBJ_UTILITY.ShowMessageError("Lỗi.!Chưa nhập số thẻ hoặc xưởng");
			return false;
		}
		
		if(date == null)
		{   
				OBJ_UTILITY.ShowMessageError("Lỗi.!Chọn năm cần tìm thông tin('dd/MM/yyyy')'");
				return false;
			
		}else
		{
			
		//	String dmy_str_to	= tf_date.getText().toString();
			String dmy_str_to	= sf_.format(date);
			Date dmy_date_to 	= null;
			
			if(dmy_str_to.equals(""))
			{
				dmy_date_to = date;
			}else
			{
				try {
					dmy_date_to 	= sf_.parse(dmy_str_to);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(dmy_date_to == null)
				{
					OBJ_UTILITY.ShowMessageError("Lỗi..!Định dạng đến ngày phải là: 'dd/MM/yyyy'");
					return false;
				}else if(dmy_date_to.before(date))
				{
					OBJ_UTILITY.ShowMessageError("Lỗi..!Đến ngày phải lớn hơn từ ngày ");
					return false;
				}
			}
		}
		return true;
	}
	
	protected void doQuery() {

		String sql = "";
		String sql1= "";
		String sql2= "";
		String sql3= "";
		SimpleDateFormat sf_ = new SimpleDateFormat(OBJ_UTILITY.DATE_FORMAT_STR);

		String empsn 	= dept_detail.getEmpsn().trim();
		Date   date		= dept_detail.getDate();
		
	    String fact 	= dept_detail.getFact().trim();
		String group	= dept_detail.getGroup().trim();
		String dept		= Vni2Uni.convertToVNI(dept_detail.getNameDept().trim());
		String id_dept  = dept_detail.getIDDept();
	
		
		RadioButton rdio_date  = dept_detail.rdio_date;
		RadioButton rdio_month = dept_detail.rdio_month;
		RadioButton rdio_year  = dept_detail.rdio_year;
		
		RadioButton rdio_f1	   = dept_detail.rdio_f1;
		RadioButton rdio_f2	   = dept_detail.rdio_f2;
		RadioButton rdio_f3	   = dept_detail.rdio_f3;
		RadioButton rdio_f5	   = dept_detail.rdio_f5;
		RadioButton rdio_f6	   = dept_detail.rdio_f6;
		RadioButton rdio_khac  = dept_detail.rdio_khac;
		
		
		if(!kt_dk_thaotac())
		{
			return;
		}
		    
		String	dmy_str		= sf_.format(date);
		String  my_str		= sf_.format(date).substring(3, 10); 
		String  m_str		= sf_.format(date).substring(3, 5);
		String  y_str		= sf_.format(date).substring(6, 10); 
		String dmy_str_to	= dmy_str; //tf_date.getText().toString();
		
			if(!empsn.equals(""))
			{   
				if(obj_e.Kt_vungqly_khi_nhap_st(empsn, ma_user,"DEPSN"))
				{
					sql = "o.EMPSN=? AND o.THANG=? AND o.NAM=?";
					
					getProgram().query(sql, new Object[]{empsn,m_str,y_str});
				}
			}else
				{
					String sql_maql = obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact, group, dept,"DEPSN",id_dept, ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
					  if(sql_maql.equals("NO"))
					  {
						  OBJ_UTILITY.ShowMessageError("Bạn không có quyền thao tác");
						  return;
					  }else
					  if(!sql_maql.equals("NO"))
					  {
						  if (!fact.equals("") && group.equals("") && dept.equals(""))
						  {
							  sql = sql+ " o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D " +
								"								WHERE E.EMPSN=o.EMPSN " +
								"									AND E.DEPSN=D.ID_DEPT " ;
							//*Tim theo xuong nhom don vi ma chi thuoc quyen quan ly cua user do
								  sql1 = obj_e.find_fact_emp(empsn, fact, group,dept, id_dept, "D", "E", ma_user,rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac)+")";
								
							//*Xuat theo chi tiet tung xuong neu chon f1,f2...khac(chi co xuong FVL)
							  sql2 = obj_e.check_fact_FVL_rb(rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac, "D");
				              sql3 = " AND o.THANG = ? AND o.NAM = ?";
							  
							  sql = sql+sql1+sql2+sql3;
							  getProgram().query(sql, new Object[]{m_str,y_str});
						  }else if (!fact.equals("") && !group.equals("") && dept.equals(""))
						  {
								sql = sql+ "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D " +
										"								WHERE E.EMPSN=o.EMPSN " +
										"									AND E.DEPSN=D.ID_DEPT " ;
								//*Tim theo xuong nhom don vi ma chi thuoc quyen quan ly cua user do
								  sql1 = obj_e.find_fact_emp(empsn, fact, group,dept, id_dept, "D", "E", ma_user,rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac)+")";
								
							//*Xuat theo chi tiet tung xuong neu chon f1,f2...khac(chi co xuong FVL)
							  sql2 = obj_e.check_fact_FVL_rb(rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac, "D");
				              sql3 = " AND o.THANG = ? AND o.NAM = ?";
				              
							  sql = sql+sql1+sql2+sql3;	
							  getProgram().query(sql, new Object[]{m_str,y_str});
					      }else if (!fact.equals("") && (!group.equals("") ||group.equals(""))  && !dept.equals(""))
					      {
								sql = sql+ "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D " +
										"								WHERE E.EMPSN=o.EMPSN " +
										"									AND E.DEPSN=D.ID_DEPT " ;
								//*Tim theo xuong nhom don vi ma chi thuoc quyen quan ly cua user do
								  sql1 = obj_e.find_fact_emp(empsn, fact, group,dept, id_dept, "D", "E", ma_user,rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac)+")";
								
							//*Xuat theo chi tiet tung xuong neu chon f1,f2...khac(chi co xuong FVL)
							  sql2 = obj_e.check_fact_FVL_rb(rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac, "D");
				              sql3 = " AND o.THANG = ? AND o.NAM = ?";
				              
							  sql = sql+sql1+sql2+sql3;		
							  getProgram().query(sql, new Object[]{m_str,y_str});
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
        add(rootLayout);
        
        dept_detail = new DeptUserControl_FactDetail();
        rootLayout.add(dept_detail);
        
        IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();
        
        dept_detail.getEMPSN_TextField().addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				
				   doQuery();
				
				
			}
		});
        
        RadioButton rdio_date  = dept_detail.rdio_date;
		RadioButton rdio_month = dept_detail.rdio_month;
		RadioButton rdio_year  = dept_detail.rdio_year;
		
		rdio_date.setVisible(false);
		rdio_month.setVisible(false);
		rdio_year.setVisible(false);
	}

}
