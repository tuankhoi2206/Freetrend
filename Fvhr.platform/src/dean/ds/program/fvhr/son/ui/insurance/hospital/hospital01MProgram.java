package ds.program.fvhr.son.ui.insurance.hospital;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import fv.util.Vni2Uni;
import ds.program.fvhr.domain.N_HOSPITAL;
import ds.program.fvhr.domain.N_JOB;
import ds.program.fvhr.domain.N_POSS;
import dsc.dao.DataObjectSet;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;

/**
 * a1 * 
 */
public class hospital01MProgram extends MaintainSProgram {

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#createDataContent()
	 */

	protected void createDataContent() {
		setMasterDataContent(new hospital01MDataContent());
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
		return new hospital01MQuery();
	}

	/* 
	 * 調整UI Layout
	 */

	protected void doLayout() {
		super.doLayout();
		this.getBrowserContent().setPageSize(20);
		this.getBrowserContent().setMaxSize(100000);
		
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
        return new String[]{"ID_HOS","NAME_HOSPITAL","ADDRESS","ID_PROVINCE","NOTE"};
	}
	

	protected boolean doDataContentRefresh(int recNo) {
		if (super.doDataContentRefresh(recNo)){
			DataObjectSet ds = getMasterDataContent().getDataObjectSet();
			N_HOSPITAL data = (N_HOSPITAL) ds.getDataObject();
			data.setNAME_HOSPITAL(Vni2Uni.convertToUnicode(data.getNAME_HOSPITAL()));
			data.setADDRESS(Vni2Uni.convertToUnicode(data.getADDRESS()));
			data.setNOTE(Vni2Uni.convertToUnicode(data.getNOTE()));
			ds.modify(data);
			ds.rollback();
			getMasterDataContent().getUIDataObjectBinder().bindDataToFields(data);
			return true;
		}
		return false;
	}
	

	protected boolean doBrowserContentRefresh() {
		String queryString = "";
		List paramList = new ArrayList();

		if (getBaseCondition() != null) {
			if (getBaseCondition().condition.trim().length() > 0) {
				queryString += "(" + getBaseCondition().condition + ")";
			}
			for (Object element : getBaseCondition().parameters) {
				paramList.add(element);
			}
		}
	
		if (getQueryCondition() != null) {
			if ((queryString.length() > 0) && (getQueryCondition().condition.length() > 0)) {
				queryString += " and ";
			}
			if (getQueryCondition().condition.length() > 0) {
				queryString += "(" + getQueryCondition().condition + ")";
			}
			for (Object element : getQueryCondition().parameters) {
				paramList.add(element);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.info("QUERY:" + queryString + ", [" + Arrays.toString(paramList.toArray()) + "]");
		}
	
		
		if (getMasterDataContent().getDataObjectSet().query(queryString,
				paramList.toArray(new Object[paramList.size()])) < 0) {
			MessageDialog dlg = new MessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					getMasterDataContent().getDataObjectSet().getErrorMessage());
			return false;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("fetch record count:" + getMasterDataContent().getDataObjectSet().getRecordCount());
		}
		DataObjectSet ds = getMasterDataContent().getDataObjectSet();		
		for (int i=0;i<ds.getRecordCount();i++){
			N_HOSPITAL data = (N_HOSPITAL) ds.getDataObject(i);
			data.setNAME_HOSPITAL(Vni2Uni.convertToUnicode(data.getNAME_HOSPITAL()));
			data.setADDRESS(Vni2Uni.convertToUnicode(data.getADDRESS()));
			data.setNOTE(Vni2Uni.convertToUnicode(data.getNOTE()));
			ds.modify(i, data);//record status modify=>save=>update db
		}
		ds.rollback();
		getBrowserContent().refresh();
		// 設定目前資料瀏覽顯示第0頁
		getBrowserContent().setCurrentPage(0);
	
		if (getMasterDataContent().getDataObjectSet().getRecordCount() > 0) {
	
			// change by kent 2007/7/30
			// 原來的code refalsh會回到第一筆，但應該是要保持在recno的位置
			//browserContent.setSelectedIndex(0, true);
			if (getBrowserContent().getCurrentSelectRowNo() != -1 && getBrowserContent().getCurrentSelectRowNo() < getBrowserContent().getDataObjectSet().getRecordCount()) {
				getBrowserContent().setSelectedIndex(getBrowserContent().getCurrentSelectRowNo(), true);
			} else {
				getBrowserContent().setSelectedIndex(0, true);
			}
			doMasterDataSelectChange();
		}
		else {
			//查無資料
			getBrowserContent().getBrowserTable().getSelectionModel().clearSelection();
			//tigereye..20090826...查無資料, 不要顯示訊息.
//			MessageDialog dlg = new MessageDialog(MessageDialog.TYPE_INFORMATION + MessageDialog.CONTROLS_OK,
//					getCommMsgRes().getString("Generic.MSG.QueryNoData")
//			);
			this.getMasterDataContent().clearAllComponentValue();
		}
		return true;
	}
	
}
