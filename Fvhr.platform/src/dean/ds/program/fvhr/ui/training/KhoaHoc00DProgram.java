package ds.program.fvhr.ui.training;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.echo2app.component.binder.UICaptionBinder;
import dsc.echo2app.program.DetailContent;
import dsc.echo2app.program.MaintainDProgram;
import dsc.echo2app.program.QueryPane;

/**
 * KhoaHoc 
 * <br/>Bug bug bug... 
 */
public class KhoaHoc00DProgram extends MaintainDProgram {

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	private SplitPane splitPane;

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainDProgram#createDataContent()
	 */
	@Override
	protected void createDataContent() {
		setMasterDataContent(new KhoaHoc00DDataContent());

		// 建立單身資料UI物件
		UICaptionBinder bb = new UICaptionBinder();

		// 加入單身物件
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        DetailContent dc;
        dc = new KhoaHoc00DDetailContent0();
        this.addDetail(bb.getResourceBundle().getString("N_CT_KHOA_HOC"), null, dc);
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
		getMasterToolbar().setbtnBrowserVisible(false);
		getMasterToolbar().setbtnContentVisible(false);
		getMasterToolbar().setbtnQueryNormalVisible(false);
		getMasterToolbar().setbtnConfirmVisible(false);
		getMasterToolbar().setbtnCancelConfirmVisible(false);
		getMasterToolbar().setbtnEmailVisible(false);
		getMasterToolbar().setbtnExportVisible(false);
		//5.設定table的最大筆數及每頁筆數
		
		getBrowserContent().getBrowserNav().setRowsPerPage(10000);
		
		getBrowserContent().getBrowserTable().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int recNo = getBrowserContent().getCurrentSelectRowNo();
				doDataContentRefresh(recNo);
			}
		});
		
		getBrowserContent().getBrowserNav().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int recNo = getBrowserContent().getCurrentSelectRowNo();				
				doDataContentRefresh(recNo);
			}
			
		});
				
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
		return new KhoaHoc00DQuery();
	}

	/* 
	 * 調整UI Layout
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
		
		removeAll();
		
//		重新調整顯示Pane
		windowPane.removeAll();
		windowPane.add(mainSplitPane);
		mainSplitPane.removeAll();
		mainSplitPane.add(getMasterToolbar());

		splitPane = new SplitPane();
		splitPane.setResizable(true);
		splitPane.setSeparatorPosition(new Extent(400));
		splitPane.setSeparatorHorizontalImage(new FillImage(new ResourceImageReference("/dsc/echo2app/resource/image/SplitHerzBar.png")));
		splitPane.setSeparatorWidth(new Extent(5));
		mainSplitPane.add(splitPane);
		splitPane.add(getBrowserContent());
		getBrowserContent().getBrowserNav().setSearchButtonVisible(false);
		getBrowserContent().getBrowserNav().setSearchTextFieldVisible(false);
		splitPane.add(this);
		this.add(mdSplitPane);
		mdSplitPane.setSeparatorPosition(new Extent(100));
	}
	
//	@Override
//	protected void switchContent(int view) {
//		
//	}
	
	@Override
	protected void doRefresh() {
		this.doBrowserContentRefresh();
		int recNo = getBrowserContent().getCurrentSelectRowNo();
		doDataContentRefresh(recNo);
	}
	

	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected String[] getBrowserDisplayColumns() {
		//4.<資料瀏覽欄位>
        return new String[] {"TEN_KHOA"};
	}

	public void updateChildPK(String ma_khoa) {
	}
	
	@Override
	protected void switchContent(int view) {
		
	}
	
//	@Override
//	public boolean doEdit() {
//		if (super.doEdit()){
//			getBrowserContent().setEnabled(false);
//			return true;
//		}
//		return false;
//	}
	
	@Override
	public void doCancel() {
		super.doCancel();
		getBrowserContent().setEnabled(true);
		int recNo = getBrowserContent().getCurrentSelectRowNo();
		doDataContentRefresh(recNo);
	}
}
