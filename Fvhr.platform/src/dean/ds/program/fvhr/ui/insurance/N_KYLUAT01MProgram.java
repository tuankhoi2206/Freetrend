package ds.program.fvhr.ui.insurance;

import java.text.SimpleDateFormat;

import nextapp.echo2.app.Extent;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.domain.N_KYLUAT;
import ds.program.fvhr.domain.N_KYLUAT;
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
 * N_KYLUAT01M * 
 */
public class N_KYLUAT01MProgram extends MaintainSProgram {

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************
	private int dataMode;
	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#createDataContent()
	 */
	@Override
	protected void createDataContent() {
		setMasterDataContent(new N_KYLUAT01MDataContent());
	}

	@Override
	public boolean doDelete() {
		// TODO Auto-generated method stub
		boolean retVal = true;
		//Lay tu data dang thao tac
		N_KYLUAT kyluat = (N_KYLUAT) this.getBrowserContent().getDataObjectSet().getDataObject();		
		InsuranceDAO ins = new InsuranceDAO();				
		String thoiGian = sf.format(kyluat.getDATE_P());
		String thang	= thoiGian.substring(3, 6);
		String nam		= thoiGian.substring(6, 10);
		String note 	= thoiGian +"->"+ sf.format(kyluat.getDATE_HL())+". Hinh thuc phat "+kyluat.getID_PHAT();
		note			= note+", Dieu "+kyluat.getID_DIEU()+", Khoan "+kyluat.getID_KHOAN();
		// neu da chuyen du lieu roi thi ko cho xoa
		retVal	= ins.CheckKhoaDataMonth(kyluat.getEMPSN(), thang, nam);		
		
	    if(retVal){	    	
	    	super.doDelete();	    	
			IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
			DSPB02 Data_DSPB02 = Dao_DSPB02.findById(Application.getApp().getLoginInfo().getUserID());
			String ma_user = Data_DSPB02.getPB_USERNO();
			N_ACTION_DAILY action = new N_ACTION_DAILY();
			action.setIDUSER(ma_user);		
			action.setACTIONNAME("DELETE");
			action.setEMPSN(kyluat.getEMPSN());
			action.setNOTE("Xoa Thuong/Phat "+note);
			action.setTABLENAME("N_KYLUAT");
			FvLogger.log(action);				    	
	    }
	    else
	    {	   
	    	setErrorMessage("Đã khóa dữ liệu.");
	    	Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, getErrorMessage());
	    	retVal = false;	    	
	    }	    
	    return retVal;		
	}
	
	@Override
	public boolean doNew() {
		// TODO Auto-generated method stub
		boolean ret = super.doNew();
		if (ret) {
			dataMode = DATAMODE_NEW;
			return ret;
		}
		return ret;		
	}
	
	@Override
	public boolean doEdit() {
		// TODO Auto-generated method stub
		boolean ret = super.doEdit();
		if (ret) {
			dataMode = DATAMODE_EDIT;
			return ret;
		}
		return ret;	
	}
	
	@Override
	public boolean doSave() {
		// TODO Auto-generated method stub
		boolean ret = super.doSave();
		if (ret) {
			N_KYLUAT data = (N_KYLUAT) getMasterDataContent()
					.getDataObject();
			String note 	= sf.format(data.getDATE_P()) +"->"+ sf.format(data.getDATE_HL())+". Hinh thuc phat "+data.getID_PHAT();
			note			= note+", Dieu "+data.getID_DIEU()+", Khoan "+data.getID_KHOAN();
			IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
			DSPB02 Data_DSPB02 = Dao_DSPB02.findById(Application.getApp().getLoginInfo().getUserID());
			String ma_user = Data_DSPB02.getPB_USERNO();
			N_ACTION_DAILY action = new N_ACTION_DAILY();
			action.setIDUSER(ma_user);
			action.setACTIONNAME(dataMode==IProgram.DATAMODE_NEW?"INSERT":"UPDATE");
			action.setEMPSN(data.getEMPSN());
			action.setNOTE("Them moi Thuong/Phat "+note);
			action.setTABLENAME("N_KYLUAT");
			FvLogger.log(action);
			ret=true;
		}
		return ret;	
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
		return new N_KYLUAT01MQuery();
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

	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected String[] getBrowserDisplayColumns() {
		//4.<資料瀏覽欄位>
        return null;
		//return new String[] { "EMPSN", "DATE_P", "DATE_HL", "ID_DIEU", "ID_KHOAN","ID_PHAT","QDKL","NOTE"};		
	}
}
