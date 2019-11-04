package ds.program.fvhr.ui.training;

import dsc.echo2app.component.binder.UICaptionBinder;
import dsc.echo2app.program.DetailContent;
import dsc.echo2app.program.MaintainDProgram;
import dsc.echo2app.program.QueryPane;

/**
 * LichHoc * 
 */
public class LichHoc01MProgram extends MaintainDProgram {

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainDProgram#createDataContent()
	 */
	@Override
	protected void createDataContent() {
		setMasterDataContent(new LichHoc01MDataContent());

		// 建立單身資料UI物件
		UICaptionBinder bb = new UICaptionBinder();

		// 加入單身物件
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        DetailContent dc;
        dc = new LichHoc01MDetailContent0();
        this.addDetail(bb.getResourceBundle().getString("N_TRAINING_DETAIL"), null, dc);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
		bb = null;
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainDProgram#doInit()
	 */
	@Override
	protected int doInit() {
		int ret = super.doInit();
		//<從此以下加入使用者程式>

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
		return new LichHoc01MQuery();
	}

	/* 
	 * 調整UI Layout
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
	}


	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected String[] getBrowserDisplayColumns() {
		//4.<資料瀏覽欄位>
        return new String[]{"EMPSN","ID_KHOA","ID_MON","BDATE","EDATE"};
	}
}
