package ds.program.fvhr.tien.ui;

import java.util.List;

import nextapp.echo2.app.Extent;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_SP_WDAY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.QueryPane;

/**
 * N_DEPARTMENT * 
 */
public class N_DEPARTMENT00MProgram extends MaintainSProgram {

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
		setMasterDataContent(new N_DEPARTMENT00MDataContent());
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
				
		return ret;
	}
	@Override
	public boolean doSave(){ 
		
		if (super.doSave())
		{
			return false;
		}
		return true;
	}
	@Override
	public boolean doDelete()
	{	
		N_DEPARTMENT objN_DEPARTMENT=(N_DEPARTMENT)this.getBrowserContent().getDataObjectSet().getDataObject();		
		IGenericDAO<N_EMPLOYEE, String> objdaoEmp=Application.getApp().getDao(N_EMPLOYEE.class);
		List<N_EMPLOYEE> listemp=objdaoEmp.find(100,"from N_EMPLOYEE where DEPSN=?", objN_DEPARTMENT.getID_DEPT());
		if(listemp.size()>0)
		{
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Bạn không thể xóa đơn vị này vì đang có người sử dụng");
				return false;	
		}
		else
			super.doDelete();
		
		return true;
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

	/*@Override
	protected QueryPane createNormalQuery() {
		return new N_DEPARTMENT00MQuery();
	}*/

	/* 
	 * 調整UIUIUI Layout
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
		//<<從此以下加入使用者程式>>
		//<<資料瀏覽欄位>>
        return new String[]{"ID_DEPT","NAME_DEPT","ID_SPDEPT","NOTE"};
	}
}
