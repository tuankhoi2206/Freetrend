package ds.program.fvhr.ui.insurance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.dao.insuranse.N_EMP_QUITDAO;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_EMP_QUIT;
import ds.program.fvhr.domain.N_EMP_RELATIVE;
import ds.program.fvhr.son.ui.insurance.social.socialWPExport;
import ds.program.fvhr.tien.ui.N_SP_WDAY00MQuery;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.QueryPane;
import fv.util.BundleUtils;
import fv.util.FvLogger;

/**
 * N_EMP_RELATIVE01M * 
 */
public class N_EMP_RELATIVE01MProgram extends MaintainSProgram {
	private int dataMode;
	private InsuranceDAO ins = new InsuranceDAO();
	private SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
	private N_EMP_RELATIVE01MQuery reQuery;
	
	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#createDataContent()
	 */
	
	@Override
	protected QueryPane createNormalQuery() {
		if (reQuery==null)
			reQuery = new N_EMP_RELATIVE01MQuery();
		return reQuery;
	}
	@Override
	protected void createDataContent() {
		setMasterDataContent(new N_EMP_RELATIVE01MDataContent());
	}
	@Override
	public  String getViewerUrl()
	{
		return super.getViewerUrl();
	}
	@Override
	protected boolean export(int recNo)
	{
		return super.export(recNo);
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

	@Override
	public int refresh() {
		// TODO Auto-generated method stub
		return super.refresh();
	}
	
	@Override
	protected void doRefresh() {
		// TODO Auto-generated method stub
		super.doRefresh();
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
		//6.<功能權限管控>
	}
	@Override
	public void doQuery() {
		
	}

	@Override
	public boolean doNew() {
		// TODO Auto-generated method stub
		boolean ret= super.doNew();
		if (ret) {
			dataMode = DATAMODE_NEW;
			return ret;
		}	
		return ret;
	}
	
	@Override
	public boolean doSave() {
		// TODO Auto-generated method stub		
		boolean ret = super.doSave();
		if (ret) {
			N_EMP_RELATIVE data = (N_EMP_RELATIVE) getMasterDataContent()
					.getDataObject();
			N_ACTION_DAILY action = new N_ACTION_DAILY();
			IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
			DSPB02 Data_DSPB02 = Dao_DSPB02.findById(Application.getApp().getLoginInfo().getUserID());
			String ma_user = Data_DSPB02.getPB_USERNO();			
			action.setIDUSER(ma_user);			
			action.setACTIONNAME(dataMode==IProgram.DATAMODE_NEW?"INSERT":"UPDATE");
			action.setEMPSN(data.getEMPSN());
			String note = data.getNAME_RELATIVE()+", ngay DKy "+sf.format(data.getCONFIRM_DATE())+", NgSinh "+sf.format(data.getBIRTHDAY()) ;
			note=note+", TGBDau "+data.getBEGINDATE();
			action.setNOTE(note);
			action.setTABLENAME("N_EMP_RELATIVE");
			FvLogger.log(action);
			return ret;
		}
		return ret;
	}
	
	@Override
	public boolean doDelete() {
		// TODO Auto-generated method stub
		boolean retVal = true;
		// Lay tu data dang thao tac
		N_EMP_RELATIVE emp = (N_EMP_RELATIVE) this.getBrowserContent()
				.getDataObjectSet().getDataObject();		
		String ngayBatDau = sf.format(emp.getBEGINDATE());
		String ngayDky = sf.format(emp.getCONFIRM_DATE());
		String ngaySinh = sf.format(emp.getBIRTHDAY());
		// neu da chuyen du lieu roi thi ko cho xoa		
		String thang = ngayBatDau.substring(3, 5);
		String nam = ngayBatDau.substring(6, 10);	
		String sothe = emp.getEMPSN();


		if (ins.checkQLyEmpsn(emp.getEMPSN())==false)
		{
			setErrorMessage("Không có quyền thao tác.");
			retVal=false;
		}		
		else if (ins.CheckKhoaDataMonth(emp.getEMPSN(), thang, nam)==false)
		{	
			setErrorMessage("Đã khóa dữ liệu");
			retVal = false;			
		}
		
		if (retVal) {
			super.doDelete();
			//luu trong action daily
			N_ACTION_DAILY action = new N_ACTION_DAILY();
			IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
			DSPB02 Data_DSPB02 = Dao_DSPB02.findById(Application.getApp().getLoginInfo().getUserID());
			String ma_user = Data_DSPB02.getPB_USERNO();			
			action.setIDUSER(ma_user);			
			action.setACTIONNAME("DELETE");
			action.setEMPSN(sothe);
			String note = emp.getNAME_RELATIVE()+", ngay DKy "+ngayDky+", NgSinh "+ngaySinh ;
			note=note+", TGBDau "+ngayBatDau;
			action.setNOTE(note);
			action.setTABLENAME("N_EMP_RELATIVE");
			FvLogger.log(action);
			
		} else {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					getErrorMessage());
			retVal = false;
		}
		return retVal;
	}
	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected String[] getBrowserDisplayColumns() {
		//4.<資料瀏覽欄位>
        return new String[] {"EMPSN", "NAME_RELATIVE", "BIRTHDAY", "SEX","CONFIRM_DATE", "BEGINDATE", "ENDDATE","IDKIND"};
	}
}
