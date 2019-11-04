package ds.program.fvhr.tien.ui;

import it.businesslogic.ireport.gui.MessageBox;

import java.text.SimpleDateFormat;
import java.util.Date;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.domain.N_SP_WDAY;
import ds.program.fvhr.son.ui.insurance.social.socialWPImport;
import ds.program.fvhr.ui.insurance.OBJ_UTILITY;
import ds.program.users.domain.DSPB02;
import fv.util.ApplicationHelper;
import fv.util.BundleUtils;
import fv.util.FvLogger;
import fv.util.HRUtils;
import fv.util.Vni2Uni;
/**
 * N_SP_WDAY * 
 */
public class N_SP_WDAY00MProgram extends MaintainSProgram {

	
	String user_up 				= Application.getApp().getLoginInfo().getUserID();
	String ma_user 				= "";
	private int mode;
	private N_SP_WDAY00MQuery q;
	private N_SP_WDAY00MDataContent spWdayContent;
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
		setMasterDataContent(new N_SP_WDAY00MDataContent());
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected int doInit() {
	
		int ret = super.doInit();
		 getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, false);
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
		if (q==null)
		q = new N_SP_WDAY00MQuery();
		return q;
	}
/*	
	@Override
	protected void doRefresh() {
		// TODO Auto-generated method stub
		super.doRefresh();
	}
	*/
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
	 * 調整UI Layout 
	 * 
	 */
	
	@Override
	public boolean doSave(){ 
		
		if (super.doSave())
		{
			N_ACTION_DAILY action=new N_ACTION_DAILY();
			IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
			DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
			ma_user = Data_DSPB02.getPB_USERNO();
			N_SP_WDAY obj=(N_SP_WDAY)this.getBrowserContent().getDataObjectSet().getDataObject();
			String EMPSN=obj.getEMPSN();
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
			String Sp_date=sdf.format(obj.getDATE_SP());
			if(mode==IProgram.DATAMODE_NEW)
			{
				action.setACTIONNAME("INSERT");	
				action.setNOTE("INSERT"+"_"+String.valueOf(obj.getMULTIPLY_WD())+"_"+Sp_date);
			}
			else if(mode==IProgram.DATAMODE_EDIT)
			{
				action.setACTIONNAME("UPDATE");
				action.setNOTE("UPDATE"+"_"+String.valueOf(obj.getMULTIPLY_WD())+"_"+Sp_date);
			}
			action.setEMPSN(EMPSN);
			//action.setACTION_DATETIME(new Date());
			action.setTABLENAME("N_SP_WDAY");			
			FvLogger.log(action);
			return true;
		}
		
		return false;
	}
	
	public boolean doDelete()
	{
		HRUtils util = ApplicationHelper.getHRUtils();
		N_SP_WDAY data=(N_SP_WDAY)this.getBrowserContent().getDataObjectSet().getDataObject();
		if (!util.getPermissionValidator().hasEmpsnPermission(data.getEMPSN())){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Anh/chị không có quyền thao tác số thẻ này");
			return false;
		}
		OBJ_UTILITY objU = new OBJ_UTILITY();
		Integer check =0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		
		String SP_date=sdf.format(data.getDATE_SP());
		String thang 	= SP_date.substring(3,5);
		String nam		= SP_date.substring(6,10);
		check =objU.KiemTraKhoaDuLieu(thang, nam, data.getEMPSN());
		if (check ==1){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Đã khóa dữ liệu trong tháng");
			return false;
		
		}
		if(super.doDelete())
		{
			N_ACTION_DAILY action=new N_ACTION_DAILY();
			IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
			DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
			ma_user = Data_DSPB02.getPB_USERNO();			
			String EMPSN=data.getEMPSN();			
			action.setEMPSN(EMPSN);
			action.setACTIONNAME("DELETE");
			action.setTABLENAME("N_SP_WDAY");
			action.setNOTE("DELETE"+"_"+String.valueOf(data.getMULTIPLY_WD())+"_"+SP_date);
			FvLogger.log(action);
		}
		
	return false;
	}
	
	@Override
	protected void doLayout() {		
		super.doLayout();
		this.getBrowserContent().setPageSize(20);
		this.getBrowserContent().setMaxSize(100000);
		Button 	importButton = new Button();
				importButton.setText("Thêm mới");
				importButton.setStyleName("Default.ToolbarButton");
				importButton.addActionListener(new ActionListener() {
					

					public void actionPerformed(ActionEvent e) {
						
						N_SP_WDay_New objWday = new N_SP_WDay_New();
						Application.getApp().getDefaultWindow().getContent().add(objWday);
						
					}
				});
				this.getMasterToolbar().add(importButton);
		
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
 
	private void RegisterBinderDataObject(){
		
	}
	
	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected String[] getBrowserDisplayColumns() {
		//4.<資料瀏覽欄位>
        return new String[]{"EMPSN","DATE_SP","NOTE","MULTIPLY_WD","USER_UPDATED","DATE_UPDATED"};
	}
}
