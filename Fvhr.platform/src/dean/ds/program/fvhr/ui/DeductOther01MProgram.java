package ds.program.fvhr.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.domain.N_DEDUCT_OTHER;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.ui.insurance.OBJ_UTILITY;
import dsc.dao.DataObjectSet;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.QueryPane;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import fv.util.Vni2Uni;

/**
 * DeductOther01M *
 */
public class DeductOther01MProgram extends MaintainSProgram {
	private String USER_UPDATED = Application.getApp().getLoginInfo()
			.getUserID();
	private int dataMode;
	private Button insertInfoBtn;

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
		setMasterDataContent(new DeductOther01MDataContent());
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
		getBrowserContent().setMaxSize(50);

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
		return new DeductOther01MQuery();
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
				insertDeductOther();
			}
		});
		this.getMasterToolbar().add(insertInfoBtn);
	}

	public boolean doDelete(){
		boolean retVal = true;
		N_DEDUCT_OTHER ktKhac = (N_DEDUCT_OTHER) this.getBrowserContent().getDataObjectSet().getDataObject();
		InsuranceDAO ins = new InsuranceDAO();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		
		String thangKTru = sf.format(ktKhac.getMONTH_DEDUCT());		
		String thang = thangKTru.substring(3, 5);
		String nam = thangKTru.substring(6, 10);
		// neu da chuyen du lieu roi thi ko cho xoa
		retVal = ins.CheckKhoaDataMonth(ktKhac.getEMPSN(), thang, nam);
		if (retVal){
	    	super.doDelete();
	    	OBJ_UTILITY objU = new OBJ_UTILITY();
	    	String userId = Application.getApp().getLoginInfo().getUserID();
	    	String userName = Application.getApp().getLoginInfo().getUserName();
			// luu lai trong action daily
			try {
				objU.InputActionDaily(userId, "N_DEDUCT_OTHER", "DELETE", ktKhac.getEMPSN().toString(), userName+" Xoa khau tru khac");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
	    	Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Đã chuyển dữ liệu của tháng, không thể xóa thông tin khấu trừ khác.");
	    	retVal = false;			
		}
		return retVal;
	}
	
	public boolean doEdit() {
		boolean kq = true;
		N_DEDUCT_OTHER ktKhac = (N_DEDUCT_OTHER) this.getBrowserContent().getDataObjectSet().getDataObject();
		InsuranceDAO ins = new InsuranceDAO();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		
		String thangKTru = sf.format(ktKhac.getMONTH_DEDUCT());		
		String thang = thangKTru.substring(3, 5);
		String nam = thangKTru.substring(6, 10);
		// neu da chuyen du lieu roi thi ko cho cap nhat lai
		kq = ins.CheckKhoaDataMonth(ktKhac.getEMPSN(), thang, nam);
		//kq= true la chua chuyen data, =false la da chuyen roi
		if (!kq){
			//setErrorMessage("Đã chuyển dữ liệu của tháng, không thể cập nhật thông tin khấu trừ khác.");
			//Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, getMasterDataContent().getErrorMessage());
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Đã chuyển dữ liệu của tháng, không thể cập nhật thông tin khấu trừ khác.");
			return false;
		}
		if (super.doEdit()){
			dataMode = IProgram.DATAMODE_EDIT;
			return true;
		}
		
		return false;	
	}
	
	private void insertDeductOther() {
		DeductManagerPanel01 ktKhacPanel = new DeductManagerPanel01();
		Application.getApp().getDefaultWindow().getContent().add(ktKhacPanel);

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

	@Override
	protected boolean doDataContentRefresh(int recNo) {
		if (super.doDataContentRefresh(recNo)) {
			DataObjectSet ds = getMasterDataContent().getDataObjectSet();
			N_DEDUCT_OTHER data = (N_DEDUCT_OTHER) getMasterDataContent()
					.getDataObject();
			data.setNOTE(Vni2Uni.convertToUnicode(data.getNOTE()));
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
			N_DEDUCT_OTHER data = (N_DEDUCT_OTHER) ds.getDataObject(i);
			data.setNOTE(Vni2Uni.convertToUnicode(data.getNOTE()));
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

	@Override
	protected String[] getBrowserDisplayColumns() {
		// 4.<資料瀏覽欄位>
		return new String[] { "EMPSN", "TOTAL_DEDUCT", "DEDUCTED",
				"MONTH_DEDUCT", "NOTE", "USER_UPDATED", "DATE_UPDATED","QT_PAYTAX" };
	}
	
}
