package ds.program.fvhr.ui.insurance;

import java.text.SimpleDateFormat;

import org.apache.poi.hssf.record.ProtectRecord;

import com.sun.star.installation.protocols;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.dao.insuranse.N_EMP_QUITDAO;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_EMP_BONUS_SICKANDMATER;
import ds.program.fvhr.domain.N_EMP_QUIT;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;
import fv.util.BundleUtils;
import fv.util.FvLogger;

/**
 * N_EMP_BONUS_SICKANDMATER01M * 
 */
public class N_EMP_BONUS_SICKANDMATER01MProgram extends MaintainSProgram {
	// them button import tu file excel
	//private MasterToolbar masterToolBar;
	private int dataMode;
	private Button btnImportExcel;
	

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
		setMasterDataContent(new N_EMP_BONUS_SICKANDMATER01MDataContent());
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
				
		btnImportExcel = new Button();
		btnImportExcel.setDisabledIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnExcel.gif"));
		btnImportExcel.setIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnExcel.gif"));
		btnImportExcel.setToolTipText("Tải từ file Excel/Insert from Excel");
		btnImportExcel.setStyleName("Default.ToolbarButton");
		btnImportExcel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BonusSickAndMaterImport pane = new BonusSickAndMaterImport(N_EMP_BONUS_SICKANDMATER01MProgram.this);
				Application.getApp().getDefaultWindow().getContent().add(pane);				
			}
		});
		
		this.getMasterToolbar().add(btnImportExcel);
		
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
		return new N_EMP_BONUS_SICKANDMATER01MQuery();
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
	public boolean doSave(){
		boolean ret = super.doSave();
		if (ret) {
			N_EMP_BONUS_SICKANDMATER data = (N_EMP_BONUS_SICKANDMATER) getMasterDataContent()
					.getDataObject();
			N_ACTION_DAILY action = new N_ACTION_DAILY();
			IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
			DSPB02 Data_DSPB02 = Dao_DSPB02.findById(Application.getApp().getLoginInfo().getUserID());
			String ma_user = Data_DSPB02.getPB_USERNO();			
			action.setIDUSER(ma_user);			
			action.setACTIONNAME(dataMode==IProgram.DATAMODE_NEW?"INSERT":"UPDATE");
			action.setEMPSN(data.getEMPSN());
			action.setNOTE(BundleUtils.getDateFormat().format(
					data.getTHANG()));
			action.setTABLENAME("N_EMP_BONUS_SICKANDMATER");
			FvLogger.log(action);
			return ret;
		}
		return ret;		
	}
	@Override
	public boolean doDelete(){
		boolean retVal = true;
		//Lay tu data dang thao tac
		N_EMP_BONUS_SICKANDMATER empSick = (N_EMP_BONUS_SICKANDMATER) this.getBrowserContent().getDataObjectSet().getDataObject();		
		InsuranceDAO ins = new InsuranceDAO();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");		
		String thoiGian = sf.format(empSick.getTHANG());
		String thang	= thoiGian.substring(3, 6);
		String nam		= thoiGian.substring(6, 10);
		String note 	= sf.format(empSick.getTHANG())+", "+empSick.getMONEY().toString()+", "+empSick.getNOTE();
		// neu da chuyen du lieu roi thi ko cho xoa
		retVal	= ins.CheckKhoaDataMonth(empSick.getEMPSN(), thang, nam);		
		
	    if(retVal){	    	
	    	super.doDelete();
			// luu vao action_daily	
			N_ACTION_DAILY action=new N_ACTION_DAILY();
			IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
			DSPB02 Data_DSPB02 = Dao_DSPB02.findById(Application.getApp().getLoginInfo().getUserID());
			String ma_user = Data_DSPB02.getPB_USERNO();			
			action.setIDUSER(ma_user);
			action.setTABLENAME("N_EMP_BONUS_SICKANDMATER");
			action.setEMPSN(empSick.getEMPSN());
			action.setACTIONNAME("DELETE");
			action.setNOTE("Xoa om dau thai san "+note);
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

	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected String[] getBrowserDisplayColumns() {
		//4.<資料瀏覽欄位>
        //return null;
		return new String[] { "EMPSN", "THANG","MONEY", "USER_UPDATED",
				"DATE_UPDATED", "NOTE" };
	}
}
