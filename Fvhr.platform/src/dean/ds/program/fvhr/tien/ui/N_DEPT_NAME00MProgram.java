package ds.program.fvhr.tien.ui;

import java.util.List;

import nextapp.echo2.app.Extent;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_DEPT_NAME;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.binder.UICaptionBinder;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainDProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.QueryPane;
import fv.util.ApplicationHelper;
import fv.util.FvLogger;
import fv.util.HRUtils;

/**
 * N_DEPT_NAME * 
 */
public class N_DEPT_NAME00MProgram extends MaintainSProgram {

	private N_DEPT_NAME00MDataContent ndeptname;
	private int mode=0;
	private String user_up 				= Application.getApp().getLoginInfo().getUserID();
	private String ma_user 				= "";
	OBJ_EMPSN objEmpsn=new OBJ_EMPSN();

	
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
		ndeptname=new N_DEPT_NAME00MDataContent();	
		 setMasterDataContent(		ndeptname);
		//以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
	

	}
	@Override
	public boolean doNew()
	{
		if(super.doNew())
		{
			mode=IProgram.DATAMODE_NEW;
			return true;
		}
		return false;
	}
	@Override
	public boolean doEdit()
	{
		if(super.doEdit())
		{
			mode=IProgram.DATAMODE_EDIT;
			return true;
		}
		return false;
		
	}
	@Override
	public boolean doSave(){ 
		
		if (super.doSave())
		{
			
			
			if(mode==IProgram.DATAMODE_NEW)
			{
				objEmpsn.Insert_N_Action_Daily(ma_user, "N_DEPT_NAME", "INSERT", "", "");
			}
			else if(mode==IProgram.DATAMODE_EDIT)
			{
				objEmpsn.Insert_N_Action_Daily(ma_user, "N_DEPT_NAME", "UPDATE", "", "");
			}
			return true;
		}
		
		return false;
	}
	public boolean doDelete()
	{
		HRUtils util = ApplicationHelper.getHRUtils();
		N_DEPT_NAME objN_DEPT_NAME=(N_DEPT_NAME)this.getBrowserContent().getDataObjectSet().getDataObject();		
		IGenericDAO<N_DEPARTMENT, String> objdaodeptname=Application.getApp().getDao(N_DEPARTMENT.class);
		List<N_DEPARTMENT> listdeptname=objdaodeptname.find(100,"from N_DEPARTMENT where NAME_DEPT_NAME=?", objN_DEPT_NAME.getDEPT_NAME());
		if(listdeptname.size()>0)
		{
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Bạn không thể xóa tên đơn vị này vì đang có người sử dụng");
				return false;	
		}
		else
		{
			if(super.doDelete())
			{
				objEmpsn.Insert_N_Action_Daily(ma_user, "N_DEPT_NAME", "DELETE", "", "");
			}	
		}

		
		
	return false;
	}


	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected int doInit() {
		int ret = super.doInit();
		IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();
		//1.<進階可查詢欄位定義>

		//2.<固定條件>
		
		//3.<預設查詢條件>

		//4.<作業功能>
		//getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, true);

		//5.設定table的最大筆數及每頁筆數
				 
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
		return new N_DEPT_NAME00MQuery();
	}

	/* 
	 * 調整UI Layout
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
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
		return new String[]{"DEPT_NAME"};
	}
}
