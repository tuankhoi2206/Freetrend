package ds.program.fvhr.tien.ui;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.echo2app.Application;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.QueryPane;

/**
 * DSHR71 * 
 */
public class DSHR7100MProgram extends MaintainSProgram {

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
		setMasterDataContent(new DSHR7100MDataContent());
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
		return new DSHR7100MQuery();
	}

	/* 
	 * 調整UI Layout
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
		this.getBrowserContent().setPageSize(20);
		this.getBrowserContent().setMaxSize(100000);
		Button 	importButton = new Button();
				importButton.setText("Cập Nhập Từ Excel");
				importButton.setStyleName("Default.ToolbarButton");
				importButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						DSHR71_EXCCEL objDSHR71_EXCCEL = new DSHR71_EXCCEL();
						Application.getApp().getDefaultWindow().getContent().add(objDSHR71_EXCCEL);
						
					}
				});
						
		
			Button 	ExportButton = new Button();
						ExportButton.setText("Xuất ra Excel");
						ExportButton.setStyleName("Default.ToolbarButton");
						ExportButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
						
									N_CHDEPT_EXECEL objN_CHDEPT_EXECEL = new N_CHDEPT_EXECEL();
								Application.getApp().getDefaultWindow().getContent().add(objN_CHDEPT_EXECEL);
							
							}
						});
						this.getMasterToolbar().add(importButton);	
						this.getMasterToolbar().add(ExportButton);
	}
	@Override
	public boolean doSave()
	{
		return super.doSave();
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
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected String[] getBrowserDisplayColumns() {
		//4.<資料瀏覽欄位>
		return new String[]{"PNL","JJ_HOUR","JJ_WADD","JJ_JADD","VDATE"};
		//,"J_HOUR","J_WADD","J_JADD","VDATE"
	}
}
