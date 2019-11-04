package ds.program.fvhr.son.ui.insurance.health;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.domain.N_HEALTH;
import ds.program.fvhr.domain.N_REST;
import ds.program.fvhr.ngan.ui.Rest.N_Emp_Rest_01MDataContent;
import ds.program.fvhr.son.ui.insurance.social.social01MDataContent;
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
 * a2 *
 */
public class health01MProgram extends MaintainSProgram {

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#createDataContent()
	 */
	
	protected void createDataContent() {
		setMasterDataContent(new health01MDataContent());
	}
	@Override
	protected boolean doDataContentRefresh(int recNo) {
		if (super.doDataContentRefresh(recNo)){
			N_HEALTH data = (N_HEALTH) getMasterDataContent().getDataObject();
			String name = Vni2Uni.convertToUnicode(data.getEMPSN_Object().getFNAME() + " " + data.getEMPSN_Object().getLNAME());
			((health01MDataContent)getMasterDataContent()).setName(name);
			return true;
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
		// 1.<進階可查詢欄位定義>
		this.setQBEDisplayFields(new String[] { "ID_KEY", "EMPSN", "CLOCK",
				"DATES", "EXPIRE", "DATE_BHYT", "ID_HEALTH", "SALARY",
				"ID_HOS", "ID_PRO", "NOTE" });

		  getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, false);
		  getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, false);

			//5.設定table的最大筆數及每頁筆數
					
			return ret;
		// 2.<固定條件>

		// 3.<預設查詢條件>

		// 4.<作業功能>
		/*getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, false);
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, false);
		getBrowserContent()
				.getDataObjectSet()
				.getQuery()
				.setSelectClause(
						" select o.EMPSN ," 
								+ " o.ID_HEALTH ,"
								+ " o.ID_PRO , " + "o.SALARY ," + " o.DATES ,"
								+ " o.EXPIRE  ," + "o.ID_KEY ");
		getBrowserContent().getDataObjectSet().getQuery()
				.setFromCaluse(" from N_HEALTH o, N_EMPLOYEE e, N_HOSPITAL s ");
		getBrowserContent()
				.getDataObjectSet()
				.getQuery()
				.setWhereCaluse(
						"where o.EMPSN=e.EMPSN AND"
								+ "o.ID_HOS=s.ID_HOS AND o.ID_PRO=s.ID_PROVINCE ");
*/
		// 5.設定table的最大筆數及每頁筆數

		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dsc.echo2app.program.DefaultProgram#doInitProgramOK()
	 */

	protected void doInitProgramOK() {
		// <初始時是否撈取資料>
		// 如果要一執行程式時就取出資料，則執行下行程式
		// this.refresh(); //取出資料必更新畫面
	}

	protected QueryPane createNormalQuery() {
		return new health01MQuery();
	}
	private int mode=IProgram.DATAMODE_EDIT;
	
	/*@Override
	public boolean doEdit()
	{
	
	 super.doEdit();		
	 return false;
		
	}*/
	@Override
	public boolean doSave()
	{
		// kiem tra quyen quan ly
		
				N_HEALTH objHealth=(N_HEALTH) this.getBrowserContent().getDataObjectSet().getDataObject();
				HRUtils utils=ApplicationHelper.getHRUtils();
				if(!utils.getPermissionValidator().hasEmpsnPermission(objHealth.getEMPSN()))
				{
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Anh/Chị không có quyền thao tác số thẻ này");
					return false;
				}
				// kiem tra du lieu da khoa chua
				if(objHealth.getCLOCK().equals("0"))
				{
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Dữ liệu của nhân viên đã bị khóa");
					return false; 
				}
				if(super.doSave())
				{
					return true;
				}
		
		return false;
	}

	/*
	 * 調整UI Layout
	 */

	protected void doLayout() {
		super.doLayout();
		this.getBrowserContent().setPageSize(20);
		this.getBrowserContent().setMaxSize(100000);
		Button importButton = new Button();
		importButton.setText("Cập nhật từ Excel");
		importButton.setStyleName("Default.ToolbarButton");
		importButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				healthWPImport winP = new healthWPImport();
				Application.getApp().getDefaultWindow().getContent().add(winP);

			}
		});

		Button exportButton = new Button();
		exportButton.setText("Xuất Excel");
		exportButton.setStyleName("Default.ToolbarButton");
		exportButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				healthWPExport winPexport = new healthWPExport();
				Application.getApp().getDefaultWindow().getContent()
						.add(winPexport);
			}
		});

		this.getMasterToolbar().add(importButton);
		this.getMasterToolbar().add(exportButton);
	}

	/*
	 * 使用者資料主檔Select change
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doMasterDataSelectChange()
	 */

	protected void doMasterDataSelectChange() {
		super.doMasterDataSelectChange();
		// 6.<功能權限管控>
	}

	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * ,
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */

	protected String[] getBrowserDisplayColumns() {
		
		return new String[] { "EMPSN", "EMPSN_Object.FNAME",
				"EMPSN_Object.LNAME", "EMPSN_Object.DEPSN", "ID_HEALTH","ID_HOS_Object.NAME_HOSPITAL",
				 "ID_PRO", "SALARY", "DATES",
				"EXPIRE" };
	}
}
