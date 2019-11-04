package ds.program.fvhr.ngan.ui.wdata;
//Tang ca ngoai nghi viec
import nextapp.echo2.app.Extent;
import ds.program.fvhr.domain.N_N_TCA_NGOAI_NV;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.BrowserContent;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.QueryPane;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;

/**
 * TangCaNgoai * 
 */
public class TangCaNgoai00MProgram extends MaintainSProgram {
	OBJ_EMPSN obj_e		 = new OBJ_EMPSN();
	String user_up = Application.getApp().getLoginInfo().getUserID();
	String ma_user = "";
	private String factCondition = "AND 1 <> 1";

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
		setMasterDataContent(new TangCaNgoai00MDataContent());
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected int doInit() {
		int ret = super.doInit();
		
		IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();
		
		
		String condStr = "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D WHERE E.EMPSN=o.EMPSN AND E.DEPSN=D.ID_DEPT " + factCondition  + ")";
		ProgramCondition pc = new ProgramCondition("1<>1", new Object[]{});
		setQueryCondition(pc);
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
		return new TangCaNgoai00MQuery();
	}

	/* 
	 * 調整UI Layout
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
	}
	
	@Override
	public boolean doDelete() {
		
			N_N_TCA_NGOAI_NV data = (N_N_TCA_NGOAI_NV) this.getBrowserContent().getDataObjectSet().getDataObject();
			
				String empsn = data.getEMPSN();
				String thang = data.getTHANG();
				String nam   = data.getNAM();
				long  tca_ngay = data.getTCA_NGAY()== null ?0 : data.getTCA_NGAY(); 
				long tca_dem   = data.getTCA_DEM() == null ? 0 : data.getTCA_DEM();
				long tca_CN    = data.getTCA_LE() == null ?0 : data.getTCA_CN();
				long sodem_tiencom = data.getSODEM_TIENCOM()==null ? 0: data.getSODEM_TIENCOM();
				String strnote = "Delete st:"+empsn+"_Thang "+thang+"/"+nam+":Tca ngay: "+tca_ngay+""+
								 ", Tca dem: "+tca_dem+", Tca Chu Nhat: "+tca_CN+", so dem tien com: "+sodem_tiencom;
				obj_e.Insert_N_Action_Daily(ma_user, "N_N_TCA_NGOAI_NV", "DELETE", empsn, strnote);
			 	OBJ_UTILITY.ShowMessageOK("Xóa thành công");
				return super.doDelete();
				
	}
	@Override
	public boolean doEdit() {
		// TODO Auto-generated method stub
		return super.doEdit();
	}
	
	@Override
	public boolean doSave() {
		if(super.doSave()){
			N_N_TCA_NGOAI_NV data = (N_N_TCA_NGOAI_NV) this.getBrowserContent().getDataObjectSet().getDataObject();
			if(data!=null){
				String empsn = data.getEMPSN().toString();
				String thang = data.getTHANG().toString();
				String nam   = data.getNAM().toString();
				long  tca_ngay = data.getTCA_NGAY()== null ?0 : data.getTCA_NGAY(); 
				long tca_dem   = data.getTCA_DEM() == null ? 0 : data.getTCA_DEM();
				long tca_CN    = data.getTCA_LE() == null ?0 : data.getTCA_CN();
				long sodem_tiencom = data.getSODEM_TIENCOM()==null ? 0: data.getSODEM_TIENCOM();
				String strnote = "Update st:"+empsn+"_Thang "+thang+"/"+nam+":Tca ngay: "+tca_ngay+""+
								 ", Tca dem: "+tca_dem+", Tca Chu Nhat: "+tca_CN+", so dem tien com: "+sodem_tiencom;
				obj_e.Insert_N_Action_Daily(ma_user, "N_N_TCA_NGOAI_NV", "UPDATE", empsn, strnote);
				
			}
			OBJ_UTILITY.ShowMessageOK("Nhập thành công");
			return true;
		}

		
		return false;
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
        return new String[] {"EMPSN", "NAM", "THANG", "TCA_NGAY", "TCA_DEM", "TCA_CN", "TCA_LE", "SODEM_TIENCOM"};
	}
}
