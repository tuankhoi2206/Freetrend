package ds.program.fvhr.tien.ui;

import java.text.SimpleDateFormat;

import nextapp.echo2.app.Extent;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.domain.N_SP_WDAY;
import ds.program.fvhr.ui.insurance.OBJ_UTILITY;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.QueryPane;
import fv.util.ApplicationHelper;
import fv.util.FvLogger;
import fv.util.HRUtils;

/**
 * N_Group_Name * 
 */
public class N_Group_Name00MProgram extends MaintainSProgram {
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
		setMasterDataContent(new N_Group_Name00MDataContent());
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
		return new N_Group_Name00MQuery();
	}
	@Override
	public boolean doSave(){ 
		
		if (super.doSave())
		{
			
			
			if(mode==IProgram.DATAMODE_NEW)
			{
				objEmpsn.Insert_N_Action_Daily(ma_user, "N_GROUP_DEPT", "INSERT", "", "");
			}
			else if(mode==IProgram.DATAMODE_EDIT)
			{
				objEmpsn.Insert_N_Action_Daily(ma_user, "N_GROUP_DEPT", "UPDATE", "", "");
			}
			return true;
		}
		
		return false;
	}
	public boolean doDelete()
	{
		HRUtils util = ApplicationHelper.getHRUtils();
		if(super.doDelete())
		{
			objEmpsn.Insert_N_Action_Daily(ma_user, "N_GROUP_DEPT", "DELETE", "", "");
		}
		
	return false;
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
		return new String[]{"NAME_GROUP","NOTE"};
	}
}
