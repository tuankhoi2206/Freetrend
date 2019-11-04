package ds.program.fvhr.ui;

import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.QueryPane;

/**
 * a1 *
 */
public class N_EMP_JOB_01MProgram extends MaintainSProgram {

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
		setMasterDataContent(new N_EMP_JOB_01MDataContent());
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected int doInit() {
		int ret = super.doInit();
		// 1.<進階可查詢欄位定義>

		// 2.<固定條件>

		// 3.<預設查詢條件>

		// 4.<作業功能>
		// getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, true);

		// 5.設定table的最大筆數及每頁筆數

		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dsc.echo2app.program.DefaultProgram#doInitProgramOK()
	 */
	@Override
	protected void doInitProgramOK() {
		// <初始時是否撈取資料>
		// 如果要一執行程式時就取出資料，則執行下行程式
		// this.refresh(); //取出資料必更新畫面
	}

	@Override
	protected QueryPane createNormalQuery() {
		return new N_EMP_JOB_01MQuery();
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
		// 6.<功能權限管控>
	}

	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected String[] getBrowserDisplayColumns() {
		// 4.<資料瀏覽欄位>
		return new String[] { "EMPSN", "ID_JOB", "DATE_B", "DATE_E", "NOTE" };
	}
}
