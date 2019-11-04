package ds.program.fvhr.ngan.ui.reg_overtime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import ds.program.fvhr.domain.N_REGISTER_OVERTIME;
import ds.program.fvhr.ngan.ui.DeptUserControl_FactDetail;
import ds.program.fvhr.ngan.ui.retain_salary.N_Emp_Retain_Salary_Import;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import echopointng.DateField;

/**
 * N_REGISTER_OVERTIME * 
 */
public class N_REGISTER_OVERTIMEMProgram extends MaintainSProgram {
	
	private String factCondition = " AND 1<>1";
	
	public Button 	btn_reg_ot;
	private N_REGISTER_OVERTIMEMProgram _main;
	OBJ_EMPSN obj_e   = new OBJ_EMPSN();
	OBJ_UTILITY OUtil = new OBJ_UTILITY();
	SimpleDateFormat sf_ 		= new SimpleDateFormat(OBJ_UTILITY.DATE_FORMAT_STR);
	RadioButton rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac;
	 DeptUserControl_FactDetail dept_detail;
	
	
	String user_up 				= Application.getApp().getLoginInfo().getUserID();
	String ma_user 				= "";
	
	Label lbel_record_count;
	int record_count = 0;

	private N_REGISTER_OVERTIMEMQuery queryPane;
	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#createDataContent()
	 */
	@Override
	protected void createDataContent() {
		setMasterDataContent(new N_REGISTER_OVERTIMEMDataContent());
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected int doInit() {
		int ret = super.doInit();
		//1.<進階可查詢欄位定義>

		//2.<固定條件>
		
		//3.<預設查詢條件>

		//4.<作業功能>
		//getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, true);

		//5.設定table的最大筆數及每頁筆數
		dept_detail = new DeptUserControl_FactDetail();
		
		String condStr = "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D WHERE E.EMPSN=o.EMPSN AND E.DEPSN=D.ID_DEPT " + factCondition  + ")";
		ProgramCondition pc = new ProgramCondition("1<>1", new Object[]{});
		setQueryCondition(pc);
				
		return ret;
	}

	/* (non-Javadoc)
	 * @see dsc.echo2app.program.DefaultProgram#doInitProgramOK()
	 */
	@Override
	protected void doInitProgramOK() {
		//<初始時是否撈取資料>
		//如果要一執行程式時就取出資料，則執行下行程式
		//this.refresh();	//取出資料必更新畫面
	}

	@Override
	protected QueryPane createNormalQuery() {
		if (queryPane==null) queryPane = new N_REGISTER_OVERTIMEMQuery();
		return queryPane;
	}

	/* 
	 * 調整UI Layout
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
		this.getBrowserContent().setPageSize(20);
		this.getBrowserContent().setMaxSize(100000);
		this.getMasterToolbar().setbtnNewVisible(false);
		this.getMasterToolbar().setbtnEditVisible(false);
		this.getMasterToolbar().setbtnSaveVisible(false);
		
		IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();
		
		btn_reg_ot = new Button();
		btn_reg_ot.setHeight(new Extent(12));
		btn_reg_ot.setStyleName("Default.ToolbarButton");
		btn_reg_ot.setBorder(new Border(2, Color.LIGHTGRAY, Border.STYLE_OUTSET));
		btn_reg_ot.setForeground(Color.WHITE);
		btn_reg_ot.setText("Nhap Tang Ca");
		btn_reg_ot.setToolTipText("Nhập ĐKTC");
		btn_reg_ot.setBackground(Color.DARKGRAY);
		btn_reg_ot.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				N_Registry_Ot_Form	reg_ot = new N_Registry_Ot_Form(_main);
				Application.getApp().getDefaultWindow().getContent().add(reg_ot);
			}
		});
		
		Button 	btn_imp 	= new Button();
		btn_imp.setHeight(new Extent(12));
		btn_imp.setStyleName("Default.ToolbarButton");
		btn_imp.setBorder(new Border(2, Color.LIGHTGRAY, Border.STYLE_OUTSET));
		btn_imp.setToolTipText("Cập nhật từ file excel");
		btn_imp.setText("Cap nhat tu excel");
		btn_imp.setBackground(Color.DARKGRAY);
		btn_imp.setForeground(Color.WHITE);
		btn_imp.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				N_Emp_Reg_OT_Import win_imp = new N_Emp_Reg_OT_Import();
				Application.getApp().getDefaultWindow().getContent().add(win_imp);
				
			}
		});
		
		Button 	btn_kt_tca 	= new Button();
		btn_kt_tca.setHeight(new Extent(12));
		btn_kt_tca.setStyleName("Default.ToolbarButton");
		btn_kt_tca.setBorder(new Border(2, Color.LIGHTGRAY, Border.STYLE_OUTSET));
		btn_kt_tca.setToolTipText("Kiem tra ĐKTC khac voi du lieu thuc te");
		btn_kt_tca.setText("Kiem tra ĐKTC");
		btn_kt_tca.setBackground(Color.DARKGRAY);
		btn_kt_tca.setForeground(Color.WHITE);
		btn_kt_tca.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				N_Export_RegOt_And_RealData_Form win_exp = new N_Export_RegOt_And_RealData_Form(_main);
				Application.getApp().getDefaultWindow().getContent().add(win_exp);
				
			}
		});
		
		this.getMasterToolbar().add(btn_reg_ot);
		this.getMasterToolbar().add(btn_imp);
		this.getMasterToolbar().add(btn_kt_tca);
		
		 if(obj_e.check_user_KToan(user_up))
	     {
			 this.getMasterToolbar().setbtnDeleteVisible(false);
			 
			 btn_reg_ot.setEnabled(false);
			 btn_reg_ot.setBorder(new Border(2, Color.LIGHTGRAY, Border.STYLE_SOLID));
			 btn_reg_ot.setBackground(Color.LIGHTGRAY);
			 
			 btn_imp.setEnabled(false);
			 btn_imp.setBorder(new Border(2, Color.LIGHTGRAY, Border.STYLE_SOLID));
			 btn_imp.setBackground(Color.LIGHTGRAY);
			 
			 btn_kt_tca.setEnabled(false);
			 btn_kt_tca.setBorder(new Border(2, Color.LIGHTGRAY, Border.STYLE_SOLID));
			 btn_kt_tca.setBackground(Color.LIGHTGRAY);
	     }else
	     {
	    	 btn_reg_ot.setEnabled(true);
	    	 btn_imp.setEnabled(true);
	    	 btn_kt_tca.setEnabled(true);
	     }
		 
		 lbel_record_count = new Label();
		 lbel_record_count.setBackground(Color.GREEN);
		 this.getMasterToolbar().add(lbel_record_count);
		 
	}
	
	public boolean doDelete()
	{
		doDataContentRefresh(getBrowserContent().getCurrentSelectRowNo());
		rdio_f1	   = dept_detail.rdio_f1;
		rdio_f2	   = dept_detail.rdio_f2;
		rdio_f3	   = dept_detail.rdio_f3;
		rdio_f5	   = dept_detail.rdio_f5;
		rdio_f6	   = dept_detail.rdio_f6;
		rdio_khac  = dept_detail.rdio_khac;
		
		N_REGISTER_OVERTIME data_ot = (N_REGISTER_OVERTIME) this.getBrowserContent().getDataObjectSet().getDataObject();
		String sothe 	= data_ot.getEMPSN();
		Date   date_	= data_ot.getDATE_OVER();
		ArrayList<Date> list_date_ot	= new ArrayList<Date>();
		Date	date_to	= date_;
		
		list_date_ot = OUtil.getAllListDate_input(date_, date_to);
		
		if(!obj_e.check_lock(user_up))
		{
			return false;
		}else if(!obj_e.check_lock_month(sothe, "","", "","", date_,"DEPSN_TEMP",ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac))
		{
			return false;
		}else if(!obj_e.check_lock_overtime(sothe,"", "","","", list_date_ot,ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac))
		{
			return false;
		}else
		{
			String Note = "Xoa tca ngay: "+sf_.format(date_)+", gio dktc: "+data_ot.getCOUNT_TIME();
			obj_e.Insert_N_Action_Daily(ma_user, "N_REGISTER_OVERTIME", "DELETE", sothe, Note);
		}
		OBJ_UTILITY.ShowMessageError("Xóa thành công");
		
		
		return super.doDelete();
	}

	/*
	 * 使用者資料主檔Select change
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doMasterDataSelectChange()
	 */
	@Override
	protected void doMasterDataSelectChange() {
		super.doMasterDataSelectChange();
		//6.<功能權限管控>
	}

	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected String[] getBrowserDisplayColumns() {
		//4.<資料瀏覽欄位>
        return new String[] {"EMPSN","EMPSN_Object.FNAME","EMPSN_Object.LNAME","EMPSN_Object.DEPSN", "DATE_OVER", "COUNT_TIME", "LOCKED"};
	}
	
	@Override
	protected void doDataRefresh() {
		// TODO Auto-generated method stub
		super.doDataRefresh();
		record_count = this.getBrowserContent().getDataObjectSet().getRecordCount();
		lbel_record_count.setText("Tổng Số : " + record_count);
	}
	
}
