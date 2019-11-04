package ds.program.fvhr.ui.insurance;

import it.businesslogic.ireport.gui.MessageBox;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.RadioButton;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.PropertyEditors;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.domain.N_EMP_BONUS_SICKANDMATER;
import ds.program.fvhr.domain.N_EMP_QUIT;
import ds.program.fvhr.ui.workpoints.WorkPointExecPane;
import ds.program.fvhr.util.OBJ_EMPSN;

public class N_EMP_BONUS_SICKANDMATER01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label DATE_UPDATED_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_UPDATED_DscDateField1;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label MONEY_CaptionLabel;
    private dsc.echo2app.component.DscField MONEY_DscField2;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField3;
    private nextapp.echo2.app.Label THANG_CaptionLabel;
    private dsc.echo2app.component.DscDateField THANG_DscDateField2;
    private nextapp.echo2.app.Label USER_UPDATED_CaptionLabel;
    private dsc.echo2app.component.DscField USER_UPDATED_DscField4;
    
    //private SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private WorkPointExecPane pane;
    private RadioButton f1,f2,f3,f5,f6,khac;
	/**
	 * Creates a new <code>N_EMP_BONUS_SICKANDMATER01MDataContent</code>.
	 */
	public N_EMP_BONUS_SICKANDMATER01MDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
	}


	/*
	 * UI資料欄位屬性更新，一般是資料模式變換時會呼叫此method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doUIRefresh()
	 */
	@Override
	public void doUIRefresh() {
		super.doUIRefresh();
		if((this.getProgram().getDataMode() == IProgram.DATAMODE_NONE) || 
				(this.getProgram().getDataMode() == IProgram.DATAMODE_BROWSER)) {
			rootLayout.setEnabled(false);
		} else {
			rootLayout.setEnabled(true);
            //----------------------------------------------------------------------------------
			//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            //----------------------------------------------------------------------------------
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				//新增時，設定哪些元件Enable/Disable
			} else {
				//修改時，設定哪些元件Enable/Disable
			}
		}

		//7.<資料權限管控>


		//自動設定元件風格
		getUIDataObjectBinder().doDataBindUIStyle();
	}

	/*
	 * 取得該畫面的DataObject Class
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#getDataObjectClass()
	 */
	public Class getDataObjectClass() {
		return N_EMP_BONUS_SICKANDMATER.class;
	}

	
	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */
	@Override
	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值
		//N_EMP_BONUS_SICKANDMATER data = (N_EMP_BONUS_SICKANDMATER) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		N_EMP_BONUS_SICKANDMATER data = (N_EMP_BONUS_SICKANDMATER) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			//使用者可在以下區域撰寫內容合理值的判斷程式碼
			//此單頭為readonly 直接return true
			
			String userId = Application.getApp().getLoginInfo().getUserID();
			
			InsuranceDAO insDao = new InsuranceDAO();			
			String empsn = data.getEMPSN();
			String chuoiTB = insDao.checkEmpsn(empsn);
			String soQDNV	="";		
			
			if (chuoiTB!=null) {
				setErrorMessage(chuoiTB);
				return false;
			}
						
			OBJ_EMPSN obj_e = new OBJ_EMPSN();		

			// Kiem tra User co xu ly data duoc hay ko
			if(!obj_e.check_lock(userId)){
				setErrorMessage("Bạn không có chức năng xử lý dữ liệu");				
				ret=false;
			}
			else if (insDao.checkQLyEmpsn(data.getEMPSN())==false){
				setErrorMessage("Bạn không có quyền thao tác trên dữ liệu này.");				
				ret=false;				
			}
			// Kiem tra viec khoa du lieu trong thang
			else{
				Date thoiGian=data.getTHANG();
				String tgian = sdf.format(data.getTHANG());
				String thang = tgian.substring(3, 5);
				String nam = tgian.substring(6, 10);					
				boolean checkData= insDao.CheckKhoaDataMonth(data.getEMPSN(), thang, nam);
	    		if(checkData==false)
	    		{	    			
	    			setErrorMessage("Đã khóa xử lý dữ liệu.");
	    			ret=false;
	    		}				
			}
		}
		
		return ret;
	}
	

	/*
	 * 元件初始化Method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doInit()
	 */
	@Override
	protected int doInit() {
		int nRet = super.doInit();

		//1.註冊資料欄位之顯示方式
		registPropertyEditor();

		//2.設定資料欄位與 UI之 Binding資訊
		bindUI();

		return nRet;
	}
	
	private void registPropertyEditor() {
	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor
        //dsc.echo2app.propertyeditors.MappingPropertyEditor editor = null;
		getUIDataObjectBinder().registerCustomEditor(N_EMP_BONUS_SICKANDMATER.class, "THANG", PropertyEditors.createDateEditor("dd/MM/yyyy"));
		getUIDataObjectBinder().registerCustomEditor(N_EMP_BONUS_SICKANDMATER.class, "DATE_UPDATED", PropertyEditors.createDateEditor("dd/MM/yyyy"));
		
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------   
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);        
        getUIDataObjectBinder().addBindMap("THANG", THANG_DscDateField2, THANG_CaptionLabel);
        getUIDataObjectBinder().addBindMap("MONEY", MONEY_DscField2, MONEY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField3, NOTE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_UPDATED", DATE_UPDATED_DscDateField1, DATE_UPDATED_CaptionLabel);
        getUIDataObjectBinder().addBindMap("USER_UPDATED", USER_UPDATED_DscField4, USER_UPDATED_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	

	/*
	 * 畫面Layout初始化Method
	 * 
	 * @see dsc.echo2app.program.DataContent#doLayout()
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
		//<<從此以下加入使用者程式>>
	}
	
	public void doDataDefaultBeforeSave(){
		N_EMP_BONUS_SICKANDMATER data = (N_EMP_BONUS_SICKANDMATER) getDataObject();	
		String userId = Application.getApp().getLoginInfo().getUserID();
		Date NgayHH = new Date();		
		sdf.format(NgayHH);
		
		data.setUSER_UPDATED(userId);
		data.setDATE_UPDATED(NgayHH);	
		
		THANG_DscDateField2.getDateChooser().setLocale(Locale.ENGLISH);
		DATE_UPDATED_DscDateField1.getDateChooser().setLocale(Locale.ENGLISH);
		
	}

	/*
	 * 做資料新增時內定預設數值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doNewDefaulData()
	 */
	@Override
	protected void doNewDefaulData() {
		//N_EMP_BONUS_SICKANDMATER data = (N_EMP_BONUS_SICKANDMATER) getDataObject();
		doDataDefaultBeforeSave();
		//<如果要預先取號則在此加入>
		//autoPrimaryKeyValue();
		//使用者可在以下區域填入新增時內定的預設數值
		//<內定值>
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {
		N_EMP_BONUS_SICKANDMATER data = (N_EMP_BONUS_SICKANDMATER) getDataObject();		
		doDataDefaultBeforeSave();
		
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
		    autoPrimaryKeyValue();
		}
		//使用者可在以下區域做儲存資料物件至DataObjectSet之前處理
		//<儲存前處理>
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {	
		
        rootLayout = new Grid();
        rootLayout.setWidth(new Extent(100, Extent.PERCENT));
        rootLayout.setSize(4);
        add(rootLayout);
        EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_CaptionLabel.setText("N_EMP_BONUS_SICKANDMATER.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField1 = new dsc.echo2app.component.DscField();
        EMPSN_DscField1.setId("EMPSN_DscField1");
        rootLayout.add(EMPSN_DscField1);
        THANG_CaptionLabel = new nextapp.echo2.app.Label();
        THANG_CaptionLabel.setText("N_EMP_BONUS_SICKANDMATER.THANG");
        rootLayout.add(THANG_CaptionLabel);
        THANG_DscDateField2 = new dsc.echo2app.component.DscDateField();
        THANG_DscDateField2.setId("THANG_DscDateField2");
        THANG_DscDateField2.setDateFormat(sdf);        
        THANG_DscDateField2.getDateChooser().setLocale(Locale.ENGLISH);
        rootLayout.add(THANG_DscDateField2);        
        MONEY_CaptionLabel = new nextapp.echo2.app.Label();
        MONEY_CaptionLabel.setText("N_EMP_BONUS_SICKANDMATER.MONEY");
        rootLayout.add(MONEY_CaptionLabel);
        MONEY_DscField2 = new dsc.echo2app.component.DscField();
        MONEY_DscField2.setId("MONEY_DscField2");
        rootLayout.add(MONEY_DscField2);
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_EMP_BONUS_SICKANDMATER.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField3 = new dsc.echo2app.component.DscField();
        NOTE_DscField3.setId("NOTE_DscField3");
        rootLayout.add(NOTE_DscField3);        
        DATE_UPDATED_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_UPDATED_CaptionLabel.setText("N_EMP_BONUS_SICKANDMATER.DATE_UPDATED");
        rootLayout.add(DATE_UPDATED_CaptionLabel);
        DATE_UPDATED_DscDateField1 = new dsc.echo2app.component.DscDateField();
        DATE_UPDATED_DscDateField1.setId("DATE_UPDATED_DscDateField1");
        DATE_UPDATED_DscDateField1.setDateFormat(sdf);
        DATE_UPDATED_DscDateField1.getDateChooser().setLocale(Locale.ENGLISH);
        DATE_UPDATED_DscDateField1.setEnabled(false);        
        rootLayout.add(DATE_UPDATED_DscDateField1);        
        USER_UPDATED_CaptionLabel = new nextapp.echo2.app.Label();
        USER_UPDATED_CaptionLabel.setText("N_EMP_BONUS_SICKANDMATER.USER_UPDATED");        
        rootLayout.add(USER_UPDATED_CaptionLabel);
        USER_UPDATED_DscField4 = new dsc.echo2app.component.DscField();
        USER_UPDATED_DscField4.setId("USER_UPDATED_DscField4");
        USER_UPDATED_DscField4.setEnabled(false);
        rootLayout.add(USER_UPDATED_DscField4);
	}

}
