package ds.program.fvhr.son.ui.data_main;

import java.sql.SQLException;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SplitPane;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.BrowserContent;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;

/**
 * a1 * 
 */
public class N_DATA_MAIN_01MProgram extends MaintainSProgram {

	private MasterToolbar masterToolbar;
	private SplitPane sliptP;
	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#createDataContent()
	 */

	protected void createDataContent() {
		setMasterDataContent(new N_DATA_MAIN_01MDataContent());
		
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	
	
	

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

	/* (non-Javadoc)
	 * @see dsc.echo2app.program.DefaultProgram#doInitProgramOK()
	 */

	protected void doInitProgramOK() {
		//<初始時是否撈取資料>
		//如果要一執行程式時就取出資料，則執行下行程式
		//this.refresh();	//取出資料必更新畫面
	}


	protected QueryPane createNormalQuery() {
		return new N_DATA_MAIN_01MQuery();
	}


	protected void doLayout() {
		super.doLayout();
		sliptP   = new SplitPane();
		sliptP.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		sliptP.setSeparatorPosition(new Extent(80));
		masterToolbar = this.getMasterToolbar();
		mainSplitPane.removeAll();
		mainSplitPane.add(masterToolbar);
		mainSplitPane.add(sliptP);
			sliptP.add(this.getMasterDataContent());
			sliptP.add(this.getBrowserContent());
		
	}


	protected void switchContent(int view) {
		masterToolbar = this.getMasterToolbar();
		switch (view) {
		case 0:
			masterToolbar.addUserDefineRight(MasterToolbar.CMD_BROWSER, false);
			masterToolbar.addUserDefineRight(MasterToolbar.CMD_CONTENT, true);
			masterToolbar.refresh();
			break;
		case 1:
			masterToolbar.addUserDefineRight(MasterToolbar.CMD_BROWSER, true);
			masterToolbar.addUserDefineRight(MasterToolbar.CMD_CONTENT, false);
			masterToolbar.refresh();
			break;
		}
	}
	

	protected void doRefresh() {
			//瀏覽頁面
			this.doBrowserContentRefresh();
			int recNo = this.getBrowserContent().getCurrentSelectRowNo();
			doDataContentRefresh(recNo);
	}
	

	public boolean doNew() {
		// TODO Auto-generated method stub
		return super.doNew();
	}
	

	public boolean doSave() {
		//資料儲存
		BrowserContent browserContent;
		this.saveUIToDataObject();

		//並檢驗資料是否正確
		if (!this.checkDataObject()) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					this.getErrorMessage());
			return false;
		}

		//儲存前處理
		beforeSaveToDataObjectSet();
		
//		tigereye....07/02  為了自訂的jdbc資料庫操作.
		if(Application.connForTrans != null)
			try {
				Application.connForTrans.commit();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;  //如果失敗, return false.
			}	
			finally {
				try {
					Application.connForTrans.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			}
			
			
		//儲存資料
		if (!saveToDataObjectSet()) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					this.getErrorMessage());
			return false;
		}

		//啟動交易管理機制
		if (!beginTranscation()) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					this.getErrorMessage());
			return false;
		}

		if (updateDataObjectSet()) {
			try {
				commit();
			} catch (RuntimeException e) {
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
						getCommMsgRes().getString("Generic.MSG.UnkownErrWhenUpdate") + e.getMessage());
				logger.error("unknow error when update record!", e);
				this.transactionStatus = null;
				return false;
			}
			int oldMode = getDataMode();
			browserContent = this.getBrowserContent();
			browserContent.refresh();
			if (oldMode == DATAMODE_NEW) {
				int recNo = browserContent.getDataObjectSet().getRecordCount() - 1;
				browserContent.setCurrentSelectRowNo(recNo);
			}
//////////////tigereye....21012009////tigereye....21012009
			dataMode_DscQryValidate = DATAMODE_SAVE;
			setDataMode(DATAMODE_BROWSER);//original code..tigereye..21012009
			
			if(mainSplitPane.getComponent(1) == browserContent) {
				//瀏覽頁面
				this.doBrowserContentRefresh();
			}
			else {
				//明細頁面
				//取得目前所選取的資料指標
				int recNo = browserContent.getCurrentSelectRowNo();
				//重查該筆資料，並顯示該資料內容
				doDataContentRefresh(recNo);
			}
			
			dataMode_DscQryValidate = DATAMODE_BROWSER;
//////////////tigereye....21012009////tigereye....21012009
			saveOK();
			return true;
		} else {
			rollBack();
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					this.getErrorMessage());
			return false;
		}
	}
	
	/*
	 * 使用者資料主檔Select change
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doMasterDataSelectChange()
	 */

	protected void doMasterDataSelectChange() {
		super.doMasterDataSelectChange();
		//6.<功能權限管控>
	}

	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */

	protected String[] getBrowserDisplayColumns() {
		//4.<資料瀏覽欄位>
        return null;
	}
}
