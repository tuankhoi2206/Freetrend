package ds.program.fvhr.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.domain.N_ATM_MANAGER;
import dsc.dao.DataObjectSet;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.QueryPane;
import fv.util.Vni2Uni;

/**
 * AtmManager *
 */
public class AtmManager01MProgram extends MaintainSProgram {

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************
	private AtmManager01MDataContent dc;

	private Button insertInfoBtn;

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#createDataContent()
	 */
	@Override
	protected void createDataContent() {
		dc = new AtmManager01MDataContent();
		setMasterDataContent(dc);
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected int doInit() {
		int ret1 = super.doInit();
		return ret1;

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

	private void insertATM() {
		AtmManager01MPane0 atmPanel = new AtmManager01MPane0();
		Application.getApp().getDefaultWindow().getContent().add(atmPanel);

	}

	@Override
	protected QueryPane createNormalQuery() {
		return new AtmManager01MQuery();
	}

	/*
	 * 調整UI Layout
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
		insertInfoBtn = new Button();
		insertInfoBtn.setDisabledIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnExcel.gif"));
		insertInfoBtn.setIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnExcel.gif"));
		insertInfoBtn.setStyleName("Default.ToolbarButton");
		insertInfoBtn.setToolTipText("Tải từ file Excel/Insert from Excel");
		insertInfoBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				insertATM();
			}
		});
		this.getMasterToolbar().add(insertInfoBtn);
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
		return new String[] { "EMPSN", "CMND", "ATM_CODE", "NO_ACC",
				"BANK_NAME", "USER_UPDATED", "DATE_UPDATE", "NOTE" };

	}

	@Override
	public boolean doSave() {
		// TODO Auto-generated method stub
		if (!isValid()) {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Du lieu khong hop le");
			return false;
		}
		return super.doSave();
	}

	private boolean isValid() {
		return dc.isValid();
	}

	@Override
	protected boolean doDataContentRefresh(int recNo) {
		// TODO Auto-generated method stub
		if (super.doDataContentRefresh(recNo)) {
			DataObjectSet ds = getMasterDataContent().getDataObjectSet();
			N_ATM_MANAGER data = (N_ATM_MANAGER) getMasterDataContent()
					.getDataObject();
			data.setNOTE(Vni2Uni.convertToUnicode(data.getNOTE()));
			data.setATM_CODE(Vni2Uni.convertToUnicode(data.getATM_CODE()));
			ds.modify(recNo, data);
			ds.rollback();
			getMasterDataContent().getUIDataObjectBinder().bindDataToFields(
					data);
			return true;
		}
		return false;
	}

	@Override
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
			if ((queryString.length() > 0)
					&& (getQueryCondition().condition.length() > 0)) {
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
			logger.info("QUERY:" + queryString + ", ["
					+ Arrays.toString(paramList.toArray()) + "]");
		}

		if (getMasterDataContent().getDataObjectSet().query(queryString,
				paramList.toArray(new Object[paramList.size()])) < 0) {
			MessageDialog dlg = new MessageDialog(MessageDialog.TYPE_ERROR
					+ MessageDialog.CONTROLS_OK, getMasterDataContent()
					.getDataObjectSet().getErrorMessage());
			return false;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("fetch record count:"
					+ getMasterDataContent().getDataObjectSet()
							.getRecordCount());
		}
		DataObjectSet ds = getMasterDataContent().getDataObjectSet();
		for (int i = 0; i < ds.getRecordCount(); i++) {
			N_ATM_MANAGER data = (N_ATM_MANAGER) ds.getDataObject(i);
			data.setNOTE(Vni2Uni.convertToUnicode(data.getNOTE()));
			data.setATM_CODE(Vni2Uni.convertToUnicode(data.getATM_CODE()));
			ds.modify(i, data);
		}
		ds.rollback();
		getBrowserContent().refresh();
		// 設定目前資料瀏覽顯示第0頁
		getBrowserContent().setCurrentPage(0);

		if (getMasterDataContent().getDataObjectSet().getRecordCount() > 0) {

			// change by kent 2007/7/30
			// 原來的code refalsh會回到第一筆，但應該是要保持在recno的位置
			// getBrowserContent().setSelectedIndex(0, true);
			if (getBrowserContent().getCurrentSelectRowNo() != -1
					&& getBrowserContent().getCurrentSelectRowNo() < getBrowserContent()
							.getDataObjectSet().getRecordCount()) {
				getBrowserContent().setSelectedIndex(
						getBrowserContent().getCurrentSelectRowNo(), true);
			} else {
				getBrowserContent().setSelectedIndex(0, true);
			}
			doMasterDataSelectChange();
		} else {
			// 查無資料
			getBrowserContent().getBrowserTable().getSelectionModel()
					.clearSelection();
			// tigereye..20090826...查無資料, 不要顯示訊息.
			// MessageDialog dlg = new
			// MessageDialog(MessageDialog.TYPE_INFORMATION +
			// MessageDialog.CONTROLS_OK,
			// getCommMsgRes().getString("Generic.MSG.QueryNoData")
			// );
			this.getMasterDataContent().clearAllComponentValue();
		}
		return true;
	}
}
