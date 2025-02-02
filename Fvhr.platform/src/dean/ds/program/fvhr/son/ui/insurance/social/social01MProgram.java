package ds.program.fvhr.son.ui.insurance.social;

import java.util.List;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.domain.N_HEALTH;
import ds.program.fvhr.domain.N_SOCIAL;
import ds.program.fvhr.domain.N_SP_WDAY;
import ds.program.fvhr.son.ui.insurance.health.health01MDataContent;
import ds.program.fvhr.son.ui.insurance.health.healthWPExport;
import ds.program.fvhr.son.ui.insurance.health.healthWPImport;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;
import fv.util.ApplicationHelper;
import fv.util.HRUtils;
import fv.util.Vni2Uni;

/**
 * a3 * 
 */
public class social01MProgram extends MaintainSProgram {

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#createDataContent()
	 */
	private int mode;
	protected void createDataContent() {
		setMasterDataContent(new social01MDataContent());
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
	protected boolean doDataContentRefresh(int recNo) {
		if (super.doDataContentRefresh(recNo)){
			N_SOCIAL data = (N_SOCIAL) getMasterDataContent().getDataObject();
			String name = Vni2Uni.convertToUnicode(data.getEMPSN_Object().getFNAME() + " " + data.getEMPSN_Object().getLNAME());
			((social01MDataContent)getMasterDataContent()).setName(name);
			return true;
		}
			return false;
		
	}
	@Override
	public boolean doSave()
	{
		
		if(mode==IProgram.DATAMODE_EDIT)
		{	HRUtils util=ApplicationHelper.getHRUtils();
			N_SOCIAL data=(N_SOCIAL)this.getBrowserContent().getDataObjectSet().getDataObject();
			if(!util.getPermissionValidator().hasEmpsnPermission(data.getEMPSN()))
			{
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Anh/chị không có quyền thao tác số thẻ này");
				return false;
			}
			String Clock=data.getCLOCK();
			if(Clock.equals("0"))
			{
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Dữ liệu của nhân viên đã bị khóa");
				return false;
			}
			if(super.doSave())
			{
				return true;			
				
			}
			
			
		}
		return false;
		
		
		
	}
	

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */

	protected int doInit() {
		int ret = super.doInit();
		//1.<進階可查詢欄位定義>
        this.setQBEDisplayFields(new String[] {"ID_KEY", "EMPSN","ID_SOCIAL", "DATES", "EXPIRE", "DATE_NEW",  "SALARY", "CLOCK", "NOTE"});

		//2.<固定條件>
		
		//3.<預設查詢條件>

		//4.<作業功能>
        getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, false);
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, false);

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
		return new social01MQuery();
	}

	/* 
	 * 調整UI Layout
	 */

	protected void doLayout() {
		super.doLayout();
		this.getBrowserContent().setPageSize(20);
		this.getBrowserContent().setMaxSize(100000);
		Button 	importButton = new Button();
				importButton.setText("Cập nhật từ Excel");
				importButton.setStyleName("Default.ToolbarButton");
				importButton.addActionListener(new ActionListener() {
					

					public void actionPerformed(ActionEvent e) {
						
						socialWPImport winP = new socialWPImport();
						Application.getApp().getDefaultWindow().getContent().add(winP);
						
					}
				});
		
		Button	exportButton	= new Button();
				exportButton.setText("Xuất Excel");
				exportButton.setStyleName("Default.ToolbarButton");
				exportButton.addActionListener(new ActionListener() {
					

					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						socialWPExport winPexport = new socialWPExport();
						Application.getApp().getDefaultWindow().getContent().add(winPexport);
					}
				});
				
				
				Button	PrintButton	= new Button();	
				PrintButton.setText("In Tờ khai");
				PrintButton.setStyleName("Default.ToolbarButton");
				PrintButton.addActionListener(new ActionListener() {
					

					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						printSocial printSocial = new printSocial();
						Application.getApp().getDefaultWindow().getContent().add(printSocial);
					}
				});
		this.getMasterToolbar().add(importButton);
		this.getMasterToolbar().add(exportButton);

		this.getMasterToolbar().add(PrintButton);

		
		//getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_EXPORT, true);		

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
       // return new String[] {"ID_KEY","EMPSN", "ID_SOCIAL","DATES", "EXPIRE", "DATE_NEW",  "SALARY", "CLOCK", "NOTE"};
		return new String[] {"EMPSN","EMPSN_Object.FNAME","EMPSN_Object.LNAME","EMPSN_Object.DEPSN", "ID_SOCIAL", "SALARY","DATES", "EXPIRE","DATE_NEW", "NOTE"};
	}
}
