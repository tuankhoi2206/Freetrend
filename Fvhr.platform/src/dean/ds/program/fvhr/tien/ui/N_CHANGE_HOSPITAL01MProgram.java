package ds.program.fvhr.tien.ui;

import java.util.Date;
import java.util.List;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.domain.N_CHANGE_HOSPITAL;
import ds.program.fvhr.domain.N_HEALTH;
import ds.program.fvhr.domain.N_SP_WDAY;
import ds.program.fvhr.domain.pk.N_CHANGE_HOSPITALPk;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;

/**
 * N_CHANGE_HOSPITAL * 
 */
public class N_CHANGE_HOSPITAL01MProgram extends MaintainSProgram {

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#createDataContent()
	 */
	private int mode=0;
	private String user_up = Application.getApp().getLoginInfo().getUserID();
	OBJ_EMPSN objEmpsn=new OBJ_EMPSN();
	@Override
	protected void createDataContent() {
		setMasterDataContent(new N_CHANGE_HOSPITAL01MDataContent());
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected int doInit() {
		int ret = super.doInit();
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, false);
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
		return new N_CHANGE_HOSPITAL01MQuery();
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
						
						N_CHANGE_H_EXCEL objN_CHANGE_H_EXCEL = new N_CHANGE_H_EXCEL();
						Application.getApp().getDefaultWindow().getContent().add(objN_CHANGE_H_EXCEL);
						
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
	public boolean doNew() {
		if (super.doNew()){
			mode=IProgram.DATAMODE_NEW;
			return true;
		}
		return false;
	}
	@Override
	protected void doMasterDataSelectChange() {
		super.doMasterDataSelectChange();
		//6.<功能權限管控>
	}
	
	@Override
	public boolean doSave()
	{
		
			try
			{
				if(super.doSave())
				{
					Date dtNoew=new Date();
					IGenericDAO<N_HEALTH, String> dao=Application.getApp().getDao(N_HEALTH.class);		
					N_CHANGE_HOSPITAL data=(N_CHANGE_HOSPITAL)this.getBrowserContent().getDataObjectSet().getDataObject();
					List<N_HEALTH> listHealth=dao.find(1, "FROM N_HEALTH WHERE EMPSN=? AND CLOCK =1", data.getEMPSN());
					String strnote="THEM MOI N_CHANGE_HOPITAL NGAY_"+dtNoew.toString();
					if(listHealth.size()>0)
					{
						N_HEALTH objHealth=listHealth.get(0);
						objHealth.setID_HOS(data.getIDHOS_NEW());
						objHealth.setID_PRO(data.getIDPRO_NEW());
						dao.update(objHealth);
						 objEmpsn.Insert_N_Action_Daily(user_up, "N_CHANGE_HOPITAL", "INSERT", data.getEMPSN(), strnote);
					}	
				}				
				
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.toString());
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Anh/ Chị đã nhập không  thành công");
				
				return false;						
			
			
			}return true;
	}
	@Override
	protected void doRefresh()
	{
		super.doRefresh();
	}
	

	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected String[] getBrowserDisplayColumns() {
        return new String[]{"EMPSN","DATE_CHANGE","IDHOS_OLD","IDHOS_NEW","IDPRO_OLD","IDPRO_NEW","STATUS"};
       
	}
}
