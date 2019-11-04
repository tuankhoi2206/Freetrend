package ds.program.fvhr.ui;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import ds.program.fvhr.domain.ATT200000;

public class ATT00MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label EMPNA_CaptionLabel;
    private dsc.echo2app.component.DscField EMPNA_DscField2;
    private nextapp.echo2.app.Label DEPSN_CaptionLabel;
    private dsc.echo2app.component.DscField DEPSN_DscField3;
    private nextapp.echo2.app.Label ADDCLS1_CaptionLabel;
    private dsc.echo2app.component.DscField ADDCLS1_DscField4;
    private nextapp.echo2.app.Label ADDCLS1_S_CaptionLabel;
    private dsc.echo2app.component.DscField ADDCLS1_S_DscField5;
    private nextapp.echo2.app.Label ADDCLS2_CaptionLabel;
    private dsc.echo2app.component.DscField ADDCLS2_DscField6;
    private nextapp.echo2.app.Label ADDCLS2_S_CaptionLabel;
    private dsc.echo2app.component.DscField ADDCLS2_S_DscField7;
    private nextapp.echo2.app.Label ADDHOL_CaptionLabel;
    private dsc.echo2app.component.DscField ADDHOL_DscField8;
    private nextapp.echo2.app.Label ADDHOLN_CaptionLabel;
    private dsc.echo2app.component.DscField ADDHOLN_DscField9;
    private nextapp.echo2.app.Label ADDHOLN_S_CaptionLabel;
    private dsc.echo2app.component.DscField ADDHOLN_S_DscField10;
    private nextapp.echo2.app.Label ADDHOL_S_CaptionLabel;
    private dsc.echo2app.component.DscField ADDHOL_S_DscField11;
    private nextapp.echo2.app.Label BAC_CaptionLabel;
    private dsc.echo2app.component.DscField BAC_DscField12;
    private nextapp.echo2.app.Label BH_TNGHIEP_CaptionLabel;
    private dsc.echo2app.component.DscField BH_TNGHIEP_DscField13;
    private nextapp.echo2.app.Label BONUS1_CaptionLabel;
    private dsc.echo2app.component.DscField BONUS1_DscField14;
    private nextapp.echo2.app.Label BONUS1_HOL_CaptionLabel;
    private dsc.echo2app.component.DscField BONUS1_HOL_DscField15;
    private nextapp.echo2.app.Label BONUS2_CaptionLabel;
    private dsc.echo2app.component.DscField BONUS2_DscField16;
    private nextapp.echo2.app.Label BONUS2_GOC_CaptionLabel;
    private dsc.echo2app.component.DscField BONUS2_GOC_DscField17;
    private nextapp.echo2.app.Label BONUS3_CaptionLabel;
    private dsc.echo2app.component.DscField BONUS3_DscField18;
    private nextapp.echo2.app.Label BONUS4_CaptionLabel;
    private dsc.echo2app.component.DscField BONUS4_DscField19;
    private nextapp.echo2.app.Label BONUS5_CaptionLabel;
    private dsc.echo2app.component.DscField BONUS5_DscField20;
    private nextapp.echo2.app.Label BONUS6_CaptionLabel;
    private dsc.echo2app.component.DscField BONUS6_DscField21;
    private nextapp.echo2.app.Label BONUS7_CaptionLabel;
    private dsc.echo2app.component.DscField BONUS7_DscField22;
    private nextapp.echo2.app.Label BONUS9_CaptionLabel;
    private dsc.echo2app.component.DscField BONUS9_DscField23;
    private nextapp.echo2.app.Label BONUS_ACN_CaptionLabel;
    private dsc.echo2app.component.DscField BONUS_ACN_DscField24;
    private nextapp.echo2.app.Label BORM_CaptionLabel;
    private dsc.echo2app.component.DscField BORM_DscField25;
    private nextapp.echo2.app.Label BSALY_CaptionLabel;
    private dsc.echo2app.component.DscField BSALY_DscField26;
    private nextapp.echo2.app.Label BSALY1_CaptionLabel;
    private dsc.echo2app.component.DscField BSALY1_DscField27;
    private nextapp.echo2.app.Label BSALY2_CaptionLabel;
    private dsc.echo2app.component.DscField BSALY2_DscField28;
    private nextapp.echo2.app.Label BSALY_N_CaptionLabel;
    private dsc.echo2app.component.DscField BSALY_N_DscField29;
    private nextapp.echo2.app.Label CLASS_CaptionLabel;
    private dsc.echo2app.component.DscField CLASS_DscField30;
    private nextapp.echo2.app.Label CODE_TAX_CaptionLabel;
    private dsc.echo2app.component.DscField CODE_TAX_DscField31;
    private nextapp.echo2.app.Label COMBSALY_CaptionLabel;
    private dsc.echo2app.component.DscField COMBSALY_DscField32;
    private nextapp.echo2.app.Label DEPSN_BHYT_CaptionLabel;
    private dsc.echo2app.component.DscField DEPSN_BHYT_DscField33;
    private nextapp.echo2.app.Label DUCLS_CaptionLabel;
    private dsc.echo2app.component.DscField DUCLS_DscField34;
    private nextapp.echo2.app.Label DUCLS_S_CaptionLabel;
    private dsc.echo2app.component.DscField DUCLS_S_DscField35;
    private nextapp.echo2.app.Label HIRED_CaptionLabel;
    private dsc.echo2app.component.DscDateField HIRED_DscDateField1;
    private nextapp.echo2.app.Label JOININSU_CaptionLabel;
    private dsc.echo2app.component.DscField JOININSU_DscField36;
    private nextapp.echo2.app.Label JOINLUM_CaptionLabel;
    private dsc.echo2app.component.DscField JOINLUM_DscField37;
    private nextapp.echo2.app.Label KQT_CaptionLabel;
    private dsc.echo2app.component.DscField KQT_DscField38;
    private nextapp.echo2.app.Label LATE_CaptionLabel;
    private dsc.echo2app.component.DscField LATE_DscField39;
    private nextapp.echo2.app.Label NADDCLS_CaptionLabel;
    private dsc.echo2app.component.DscField NADDCLS_DscField40;
    private nextapp.echo2.app.Label NADDCLS_S_CaptionLabel;
    private dsc.echo2app.component.DscField NADDCLS_S_DscField41;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField42;
    private nextapp.echo2.app.Label NOTE_REST_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_REST_DscField43;
    private nextapp.echo2.app.Label NUCLS_CaptionLabel;
    private dsc.echo2app.component.DscField NUCLS_DscField44;
    private nextapp.echo2.app.Label NUCLS_S_CaptionLabel;
    private dsc.echo2app.component.DscField NUCLS_S_DscField45;
    private nextapp.echo2.app.Label NWHOUR_CaptionLabel;
    private dsc.echo2app.component.DscField NWHOUR_DscField46;
    private nextapp.echo2.app.Label OFFD_CaptionLabel;
    private dsc.echo2app.component.DscDateField OFFD_DscDateField2;
    private nextapp.echo2.app.Label OTHER_CaptionLabel;
    private dsc.echo2app.component.DscField OTHER_DscField47;
    private nextapp.echo2.app.Label PAYTAX_CaptionLabel;
    private dsc.echo2app.component.DscField PAYTAX_DscField48;
    private nextapp.echo2.app.Label PAYTAX1_CaptionLabel;
    private dsc.echo2app.component.DscField PAYTAX1_DscField49;
    private nextapp.echo2.app.Label PHEP_NS_CaptionLabel;
    private dsc.echo2app.component.DscField PHEP_NS_DscField50;
    private nextapp.echo2.app.Label POSSN_CaptionLabel;
    private dsc.echo2app.component.DscField POSSN_DscField51;
    private nextapp.echo2.app.Label QT_PAYTAX1_CaptionLabel;
    private dsc.echo2app.component.DscField QT_PAYTAX1_DscField52;
    private nextapp.echo2.app.Label REST_CaptionLabel;
    private dsc.echo2app.component.DscField REST_DscField53;
    private nextapp.echo2.app.Label REST_PAY_CaptionLabel;
    private dsc.echo2app.component.DscField REST_PAY_DscField54;
    private nextapp.echo2.app.Label REST_PAY_S_CaptionLabel;
    private dsc.echo2app.component.DscField REST_PAY_S_DscField55;
    private nextapp.echo2.app.Label REST_SICK_CaptionLabel;
    private dsc.echo2app.component.DscField REST_SICK_DscField56;
    private nextapp.echo2.app.Label SIGN_CaptionLabel;
    private dsc.echo2app.component.DscField SIGN_DscField57;
    private nextapp.echo2.app.Label STT_CaptionLabel;
    private dsc.echo2app.component.DscField STT_DscField58;
    private nextapp.echo2.app.Label TADDCLS_CaptionLabel;
    private dsc.echo2app.component.DscField TADDCLS_DscField59;
    private nextapp.echo2.app.Label TADDCLS2_CaptionLabel;
    private dsc.echo2app.component.DscField TADDCLS2_DscField60;
    private nextapp.echo2.app.Label TADDS_CaptionLabel;
    private dsc.echo2app.component.DscField TADDS_DscField61;
    private nextapp.echo2.app.Label TADDS_NOTAX_CaptionLabel;
    private dsc.echo2app.component.DscField TADDS_NOTAX_DscField62;
    private nextapp.echo2.app.Label TBASIC_CaptionLabel;
    private dsc.echo2app.component.DscField TBASIC_DscField63;
    private nextapp.echo2.app.Label TBONUS_CaptionLabel;
    private dsc.echo2app.component.DscField TBONUS_DscField64;
    private nextapp.echo2.app.Label TDAY_CaptionLabel;
    private dsc.echo2app.component.DscField TDAY_DscField65;
    private nextapp.echo2.app.Label TINCOME_CaptionLabel;
    private dsc.echo2app.component.DscField TINCOME_DscField66;
    private nextapp.echo2.app.Label TKQ_CaptionLabel;
    private dsc.echo2app.component.DscField TKQ_DscField67;
    private nextapp.echo2.app.Label TS1_CaptionLabel;
    private dsc.echo2app.component.DscField TS1_DscField68;
    private nextapp.echo2.app.Label TSAL_PAYTAX_CaptionLabel;
    private dsc.echo2app.component.DscField TSAL_PAYTAX_DscField69;
    private nextapp.echo2.app.Label TTS_CaptionLabel;
    private dsc.echo2app.component.DscField TTS_DscField70;
    private nextapp.echo2.app.Label TYADDCLS_CaptionLabel;
    private dsc.echo2app.component.DscField TYADDCLS_DscField71;
    private nextapp.echo2.app.Label TY_PAYTAX1_CaptionLabel;
    private dsc.echo2app.component.DscField TY_PAYTAX1_DscField72;
    private nextapp.echo2.app.Label YLBX_CaptionLabel;
    private dsc.echo2app.component.DscField YLBX_DscField73;

	/**
	 * Creates a new <code>ATT00MDataContent</code>.
	 */
	public ATT00MDataContent() {
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
	@Override
	public Class getDataObjectClass() {
		return ATT200000.class;
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
		//ATT data = (ATT) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		//ATT data = (ATT) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			//使用者可在以下區域撰寫內容合理值的判斷程式碼
			//此單頭為readonly 直接return true 
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
        dsc.echo2app.propertyeditors.MappingPropertyEditor editor = null;
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPNA", EMPNA_DscField2, EMPNA_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DEPSN", DEPSN_DscField3, DEPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ADDCLS1", ADDCLS1_DscField4, ADDCLS1_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ADDCLS1_S", ADDCLS1_S_DscField5, ADDCLS1_S_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ADDCLS2", ADDCLS2_DscField6, ADDCLS2_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ADDCLS2_S", ADDCLS2_S_DscField7, ADDCLS2_S_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ADDHOL", ADDHOL_DscField8, ADDHOL_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ADDHOLN", ADDHOLN_DscField9, ADDHOLN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ADDHOLN_S", ADDHOLN_S_DscField10, ADDHOLN_S_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ADDHOL_S", ADDHOL_S_DscField11, ADDHOL_S_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BAC", BAC_DscField12, BAC_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BH_TNGHIEP", BH_TNGHIEP_DscField13, BH_TNGHIEP_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BONUS1", BONUS1_DscField14, BONUS1_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BONUS1_HOL", BONUS1_HOL_DscField15, BONUS1_HOL_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BONUS2", BONUS2_DscField16, BONUS2_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BONUS2_GOC", BONUS2_GOC_DscField17, BONUS2_GOC_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BONUS3", BONUS3_DscField18, BONUS3_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BONUS4", BONUS4_DscField19, BONUS4_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BONUS5", BONUS5_DscField20, BONUS5_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BONUS6", BONUS6_DscField21, BONUS6_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BONUS7", BONUS7_DscField22, BONUS7_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BONUS9", BONUS9_DscField23, BONUS9_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BONUS_ACN", BONUS_ACN_DscField24, BONUS_ACN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BORM", BORM_DscField25, BORM_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BSALY", BSALY_DscField26, BSALY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BSALY1", BSALY1_DscField27, BSALY1_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BSALY2", BSALY2_DscField28, BSALY2_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BSALY_N", BSALY_N_DscField29, BSALY_N_CaptionLabel);
        getUIDataObjectBinder().addBindMap("CLASS", CLASS_DscField30, CLASS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("CODE_TAX", CODE_TAX_DscField31, CODE_TAX_CaptionLabel);
        getUIDataObjectBinder().addBindMap("COMBSALY", COMBSALY_DscField32, COMBSALY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DEPSN_BHYT", DEPSN_BHYT_DscField33, DEPSN_BHYT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DUCLS", DUCLS_DscField34, DUCLS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DUCLS_S", DUCLS_S_DscField35, DUCLS_S_CaptionLabel);
        getUIDataObjectBinder().addBindMap("HIRED", HIRED_DscDateField1, HIRED_CaptionLabel);
        getUIDataObjectBinder().addBindMap("JOININSU", JOININSU_DscField36, JOININSU_CaptionLabel);
        getUIDataObjectBinder().addBindMap("JOINLUM", JOINLUM_DscField37, JOINLUM_CaptionLabel);
        getUIDataObjectBinder().addBindMap("KQT", KQT_DscField38, KQT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("LATE", LATE_DscField39, LATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NADDCLS", NADDCLS_DscField40, NADDCLS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NADDCLS_S", NADDCLS_S_DscField41, NADDCLS_S_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField42, NOTE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE_REST", NOTE_REST_DscField43, NOTE_REST_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NUCLS", NUCLS_DscField44, NUCLS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NUCLS_S", NUCLS_S_DscField45, NUCLS_S_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NWHOUR", NWHOUR_DscField46, NWHOUR_CaptionLabel);
        getUIDataObjectBinder().addBindMap("OFFD", OFFD_DscDateField2, OFFD_CaptionLabel);
        getUIDataObjectBinder().addBindMap("OTHER", OTHER_DscField47, OTHER_CaptionLabel);
        getUIDataObjectBinder().addBindMap("PAYTAX", PAYTAX_DscField48, PAYTAX_CaptionLabel);
        getUIDataObjectBinder().addBindMap("PAYTAX1", PAYTAX1_DscField49, PAYTAX1_CaptionLabel);
        getUIDataObjectBinder().addBindMap("PHEP_NS", PHEP_NS_DscField50, PHEP_NS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("POSSN", POSSN_DscField51, POSSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("QT_PAYTAX1", QT_PAYTAX1_DscField52, QT_PAYTAX1_CaptionLabel);
        getUIDataObjectBinder().addBindMap("REST", REST_DscField53, REST_CaptionLabel);
        getUIDataObjectBinder().addBindMap("REST_PAY", REST_PAY_DscField54, REST_PAY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("REST_PAY_S", REST_PAY_S_DscField55, REST_PAY_S_CaptionLabel);
        getUIDataObjectBinder().addBindMap("REST_SICK", REST_SICK_DscField56, REST_SICK_CaptionLabel);
        getUIDataObjectBinder().addBindMap("SIGN", SIGN_DscField57, SIGN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("STT", STT_DscField58, STT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TADDCLS", TADDCLS_DscField59, TADDCLS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TADDCLS2", TADDCLS2_DscField60, TADDCLS2_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TADDS", TADDS_DscField61, TADDS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TADDS_NOTAX", TADDS_NOTAX_DscField62, TADDS_NOTAX_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TBASIC", TBASIC_DscField63, TBASIC_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TBONUS", TBONUS_DscField64, TBONUS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TDAY", TDAY_DscField65, TDAY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TINCOME", TINCOME_DscField66, TINCOME_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TKQ", TKQ_DscField67, TKQ_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TS1", TS1_DscField68, TS1_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TSAL_PAYTAX", TSAL_PAYTAX_DscField69, TSAL_PAYTAX_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TTS", TTS_DscField70, TTS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TYADDCLS", TYADDCLS_DscField71, TYADDCLS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TY_PAYTAX1", TY_PAYTAX1_DscField72, TY_PAYTAX1_CaptionLabel);
        getUIDataObjectBinder().addBindMap("YLBX", YLBX_DscField73, YLBX_CaptionLabel);
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

	/*
	 * 做資料新增時內定預設數值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doNewDefaulData()
	 */
	@Override
	protected void doNewDefaulData() {
		//ATT data = (ATT) getDataObject();
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
		//ATT data = (ATT) getDataObject();
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
        EMPSN_CaptionLabel.setText("ATT.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField1 = new dsc.echo2app.component.DscField();
        EMPSN_DscField1.setId("EMPSN_DscField1");
        rootLayout.add(EMPSN_DscField1);
        EMPNA_CaptionLabel = new nextapp.echo2.app.Label();
        EMPNA_CaptionLabel.setText("ATT.EMPNA");
        rootLayout.add(EMPNA_CaptionLabel);
        EMPNA_DscField2 = new dsc.echo2app.component.DscField();
        EMPNA_DscField2.setId("EMPNA_DscField2");
        rootLayout.add(EMPNA_DscField2);
        DEPSN_CaptionLabel = new nextapp.echo2.app.Label();
        DEPSN_CaptionLabel.setText("ATT.DEPSN");
        rootLayout.add(DEPSN_CaptionLabel);
        DEPSN_DscField3 = new dsc.echo2app.component.DscField();
        DEPSN_DscField3.setId("DEPSN_DscField3");
        rootLayout.add(DEPSN_DscField3);
        ADDCLS1_CaptionLabel = new nextapp.echo2.app.Label();
        ADDCLS1_CaptionLabel.setText("ATT.ADDCLS1");
        rootLayout.add(ADDCLS1_CaptionLabel);
        ADDCLS1_DscField4 = new dsc.echo2app.component.DscField();
        ADDCLS1_DscField4.setId("ADDCLS1_DscField4");
        rootLayout.add(ADDCLS1_DscField4);
        ADDCLS1_S_CaptionLabel = new nextapp.echo2.app.Label();
        ADDCLS1_S_CaptionLabel.setText("ATT.ADDCLS1_S");
        rootLayout.add(ADDCLS1_S_CaptionLabel);
        ADDCLS1_S_DscField5 = new dsc.echo2app.component.DscField();
        ADDCLS1_S_DscField5.setId("ADDCLS1_S_DscField5");
        rootLayout.add(ADDCLS1_S_DscField5);
        ADDCLS2_CaptionLabel = new nextapp.echo2.app.Label();
        ADDCLS2_CaptionLabel.setText("ATT.ADDCLS2");
        rootLayout.add(ADDCLS2_CaptionLabel);
        ADDCLS2_DscField6 = new dsc.echo2app.component.DscField();
        ADDCLS2_DscField6.setId("ADDCLS2_DscField6");
        rootLayout.add(ADDCLS2_DscField6);
        ADDCLS2_S_CaptionLabel = new nextapp.echo2.app.Label();
        ADDCLS2_S_CaptionLabel.setText("ATT.ADDCLS2_S");
        rootLayout.add(ADDCLS2_S_CaptionLabel);
        ADDCLS2_S_DscField7 = new dsc.echo2app.component.DscField();
        ADDCLS2_S_DscField7.setId("ADDCLS2_S_DscField7");
        rootLayout.add(ADDCLS2_S_DscField7);
        ADDHOL_CaptionLabel = new nextapp.echo2.app.Label();
        ADDHOL_CaptionLabel.setText("ATT.ADDHOL");
        rootLayout.add(ADDHOL_CaptionLabel);
        ADDHOL_DscField8 = new dsc.echo2app.component.DscField();
        ADDHOL_DscField8.setId("ADDHOL_DscField8");
        rootLayout.add(ADDHOL_DscField8);
        ADDHOLN_CaptionLabel = new nextapp.echo2.app.Label();
        ADDHOLN_CaptionLabel.setText("ATT.ADDHOLN");
        rootLayout.add(ADDHOLN_CaptionLabel);
        ADDHOLN_DscField9 = new dsc.echo2app.component.DscField();
        ADDHOLN_DscField9.setId("ADDHOLN_DscField9");
        rootLayout.add(ADDHOLN_DscField9);
        ADDHOLN_S_CaptionLabel = new nextapp.echo2.app.Label();
        ADDHOLN_S_CaptionLabel.setText("ATT.ADDHOLN_S");
        rootLayout.add(ADDHOLN_S_CaptionLabel);
        ADDHOLN_S_DscField10 = new dsc.echo2app.component.DscField();
        ADDHOLN_S_DscField10.setId("ADDHOLN_S_DscField10");
        rootLayout.add(ADDHOLN_S_DscField10);
        ADDHOL_S_CaptionLabel = new nextapp.echo2.app.Label();
        ADDHOL_S_CaptionLabel.setText("ATT.ADDHOL_S");
        rootLayout.add(ADDHOL_S_CaptionLabel);
        ADDHOL_S_DscField11 = new dsc.echo2app.component.DscField();
        ADDHOL_S_DscField11.setId("ADDHOL_S_DscField11");
        rootLayout.add(ADDHOL_S_DscField11);
        BAC_CaptionLabel = new nextapp.echo2.app.Label();
        BAC_CaptionLabel.setText("ATT.BAC");
        rootLayout.add(BAC_CaptionLabel);
        BAC_DscField12 = new dsc.echo2app.component.DscField();
        BAC_DscField12.setId("BAC_DscField12");
        rootLayout.add(BAC_DscField12);
        BH_TNGHIEP_CaptionLabel = new nextapp.echo2.app.Label();
        BH_TNGHIEP_CaptionLabel.setText("ATT.BH_TNGHIEP");
        rootLayout.add(BH_TNGHIEP_CaptionLabel);
        BH_TNGHIEP_DscField13 = new dsc.echo2app.component.DscField();
        BH_TNGHIEP_DscField13.setId("BH_TNGHIEP_DscField13");
        rootLayout.add(BH_TNGHIEP_DscField13);
        BONUS1_CaptionLabel = new nextapp.echo2.app.Label();
        BONUS1_CaptionLabel.setText("ATT.BONUS1");
        rootLayout.add(BONUS1_CaptionLabel);
        BONUS1_DscField14 = new dsc.echo2app.component.DscField();
        BONUS1_DscField14.setId("BONUS1_DscField14");
        rootLayout.add(BONUS1_DscField14);
        BONUS1_HOL_CaptionLabel = new nextapp.echo2.app.Label();
        BONUS1_HOL_CaptionLabel.setText("ATT.BONUS1_HOL");
        rootLayout.add(BONUS1_HOL_CaptionLabel);
        BONUS1_HOL_DscField15 = new dsc.echo2app.component.DscField();
        BONUS1_HOL_DscField15.setId("BONUS1_HOL_DscField15");
        rootLayout.add(BONUS1_HOL_DscField15);
        BONUS2_CaptionLabel = new nextapp.echo2.app.Label();
        BONUS2_CaptionLabel.setText("ATT.BONUS2");
        rootLayout.add(BONUS2_CaptionLabel);
        BONUS2_DscField16 = new dsc.echo2app.component.DscField();
        BONUS2_DscField16.setId("BONUS2_DscField16");
        rootLayout.add(BONUS2_DscField16);
        BONUS2_GOC_CaptionLabel = new nextapp.echo2.app.Label();
        BONUS2_GOC_CaptionLabel.setText("ATT.BONUS2_GOC");
        rootLayout.add(BONUS2_GOC_CaptionLabel);
        BONUS2_GOC_DscField17 = new dsc.echo2app.component.DscField();
        BONUS2_GOC_DscField17.setId("BONUS2_GOC_DscField17");
        rootLayout.add(BONUS2_GOC_DscField17);
        BONUS3_CaptionLabel = new nextapp.echo2.app.Label();
        BONUS3_CaptionLabel.setText("ATT.BONUS3");
        rootLayout.add(BONUS3_CaptionLabel);
        BONUS3_DscField18 = new dsc.echo2app.component.DscField();
        BONUS3_DscField18.setId("BONUS3_DscField18");
        rootLayout.add(BONUS3_DscField18);
        BONUS4_CaptionLabel = new nextapp.echo2.app.Label();
        BONUS4_CaptionLabel.setText("ATT.BONUS4");
        rootLayout.add(BONUS4_CaptionLabel);
        BONUS4_DscField19 = new dsc.echo2app.component.DscField();
        BONUS4_DscField19.setId("BONUS4_DscField19");
        rootLayout.add(BONUS4_DscField19);
        BONUS5_CaptionLabel = new nextapp.echo2.app.Label();
        BONUS5_CaptionLabel.setText("ATT.BONUS5");
        rootLayout.add(BONUS5_CaptionLabel);
        BONUS5_DscField20 = new dsc.echo2app.component.DscField();
        BONUS5_DscField20.setId("BONUS5_DscField20");
        rootLayout.add(BONUS5_DscField20);
        BONUS6_CaptionLabel = new nextapp.echo2.app.Label();
        BONUS6_CaptionLabel.setText("ATT.BONUS6");
        rootLayout.add(BONUS6_CaptionLabel);
        BONUS6_DscField21 = new dsc.echo2app.component.DscField();
        BONUS6_DscField21.setId("BONUS6_DscField21");
        rootLayout.add(BONUS6_DscField21);
        BONUS7_CaptionLabel = new nextapp.echo2.app.Label();
        BONUS7_CaptionLabel.setText("ATT.BONUS7");
        rootLayout.add(BONUS7_CaptionLabel);
        BONUS7_DscField22 = new dsc.echo2app.component.DscField();
        BONUS7_DscField22.setId("BONUS7_DscField22");
        rootLayout.add(BONUS7_DscField22);
        BONUS9_CaptionLabel = new nextapp.echo2.app.Label();
        BONUS9_CaptionLabel.setText("ATT.BONUS9");
        rootLayout.add(BONUS9_CaptionLabel);
        BONUS9_DscField23 = new dsc.echo2app.component.DscField();
        BONUS9_DscField23.setId("BONUS9_DscField23");
        rootLayout.add(BONUS9_DscField23);
        BONUS_ACN_CaptionLabel = new nextapp.echo2.app.Label();
        BONUS_ACN_CaptionLabel.setText("ATT.BONUS_ACN");
        rootLayout.add(BONUS_ACN_CaptionLabel);
        BONUS_ACN_DscField24 = new dsc.echo2app.component.DscField();
        BONUS_ACN_DscField24.setId("BONUS_ACN_DscField24");
        rootLayout.add(BONUS_ACN_DscField24);
        BORM_CaptionLabel = new nextapp.echo2.app.Label();
        BORM_CaptionLabel.setText("ATT.BORM");
        rootLayout.add(BORM_CaptionLabel);
        BORM_DscField25 = new dsc.echo2app.component.DscField();
        BORM_DscField25.setId("BORM_DscField25");
        rootLayout.add(BORM_DscField25);
        BSALY_CaptionLabel = new nextapp.echo2.app.Label();
        BSALY_CaptionLabel.setText("ATT.BSALY");
        rootLayout.add(BSALY_CaptionLabel);
        BSALY_DscField26 = new dsc.echo2app.component.DscField();
        BSALY_DscField26.setId("BSALY_DscField26");
        rootLayout.add(BSALY_DscField26);
        BSALY1_CaptionLabel = new nextapp.echo2.app.Label();
        BSALY1_CaptionLabel.setText("ATT.BSALY1");
        rootLayout.add(BSALY1_CaptionLabel);
        BSALY1_DscField27 = new dsc.echo2app.component.DscField();
        BSALY1_DscField27.setId("BSALY1_DscField27");
        rootLayout.add(BSALY1_DscField27);
        BSALY2_CaptionLabel = new nextapp.echo2.app.Label();
        BSALY2_CaptionLabel.setText("ATT.BSALY2");
        rootLayout.add(BSALY2_CaptionLabel);
        BSALY2_DscField28 = new dsc.echo2app.component.DscField();
        BSALY2_DscField28.setId("BSALY2_DscField28");
        rootLayout.add(BSALY2_DscField28);
        BSALY_N_CaptionLabel = new nextapp.echo2.app.Label();
        BSALY_N_CaptionLabel.setText("ATT.BSALY_N");
        rootLayout.add(BSALY_N_CaptionLabel);
        BSALY_N_DscField29 = new dsc.echo2app.component.DscField();
        BSALY_N_DscField29.setId("BSALY_N_DscField29");
        rootLayout.add(BSALY_N_DscField29);
        CLASS_CaptionLabel = new nextapp.echo2.app.Label();
        CLASS_CaptionLabel.setText("ATT.CLASS");
        rootLayout.add(CLASS_CaptionLabel);
        CLASS_DscField30 = new dsc.echo2app.component.DscField();
        CLASS_DscField30.setId("CLASS_DscField30");
        rootLayout.add(CLASS_DscField30);
        CODE_TAX_CaptionLabel = new nextapp.echo2.app.Label();
        CODE_TAX_CaptionLabel.setText("ATT.CODE_TAX");
        rootLayout.add(CODE_TAX_CaptionLabel);
        CODE_TAX_DscField31 = new dsc.echo2app.component.DscField();
        CODE_TAX_DscField31.setId("CODE_TAX_DscField31");
        rootLayout.add(CODE_TAX_DscField31);
        COMBSALY_CaptionLabel = new nextapp.echo2.app.Label();
        COMBSALY_CaptionLabel.setText("ATT.COMBSALY");
        rootLayout.add(COMBSALY_CaptionLabel);
        COMBSALY_DscField32 = new dsc.echo2app.component.DscField();
        COMBSALY_DscField32.setId("COMBSALY_DscField32");
        rootLayout.add(COMBSALY_DscField32);
        DEPSN_BHYT_CaptionLabel = new nextapp.echo2.app.Label();
        DEPSN_BHYT_CaptionLabel.setText("ATT.DEPSN_BHYT");
        rootLayout.add(DEPSN_BHYT_CaptionLabel);
        DEPSN_BHYT_DscField33 = new dsc.echo2app.component.DscField();
        DEPSN_BHYT_DscField33.setId("DEPSN_BHYT_DscField33");
        rootLayout.add(DEPSN_BHYT_DscField33);
        DUCLS_CaptionLabel = new nextapp.echo2.app.Label();
        DUCLS_CaptionLabel.setText("ATT.DUCLS");
        rootLayout.add(DUCLS_CaptionLabel);
        DUCLS_DscField34 = new dsc.echo2app.component.DscField();
        DUCLS_DscField34.setId("DUCLS_DscField34");
        rootLayout.add(DUCLS_DscField34);
        DUCLS_S_CaptionLabel = new nextapp.echo2.app.Label();
        DUCLS_S_CaptionLabel.setText("ATT.DUCLS_S");
        rootLayout.add(DUCLS_S_CaptionLabel);
        DUCLS_S_DscField35 = new dsc.echo2app.component.DscField();
        DUCLS_S_DscField35.setId("DUCLS_S_DscField35");
        rootLayout.add(DUCLS_S_DscField35);
        HIRED_CaptionLabel = new nextapp.echo2.app.Label();
        HIRED_CaptionLabel.setText("ATT.HIRED");
        rootLayout.add(HIRED_CaptionLabel);
        HIRED_DscDateField1 = new dsc.echo2app.component.DscDateField();
        HIRED_DscDateField1.setId("HIRED_DscDateField1");
        rootLayout.add(HIRED_DscDateField1);
        JOININSU_CaptionLabel = new nextapp.echo2.app.Label();
        JOININSU_CaptionLabel.setText("ATT.JOININSU");
        rootLayout.add(JOININSU_CaptionLabel);
        JOININSU_DscField36 = new dsc.echo2app.component.DscField();
        JOININSU_DscField36.setId("JOININSU_DscField36");
        rootLayout.add(JOININSU_DscField36);
        JOINLUM_CaptionLabel = new nextapp.echo2.app.Label();
        JOINLUM_CaptionLabel.setText("ATT.JOINLUM");
        rootLayout.add(JOINLUM_CaptionLabel);
        JOINLUM_DscField37 = new dsc.echo2app.component.DscField();
        JOINLUM_DscField37.setId("JOINLUM_DscField37");
        rootLayout.add(JOINLUM_DscField37);
        KQT_CaptionLabel = new nextapp.echo2.app.Label();
        KQT_CaptionLabel.setText("ATT.KQT");
        rootLayout.add(KQT_CaptionLabel);
        KQT_DscField38 = new dsc.echo2app.component.DscField();
        KQT_DscField38.setId("KQT_DscField38");
        rootLayout.add(KQT_DscField38);
        LATE_CaptionLabel = new nextapp.echo2.app.Label();
        LATE_CaptionLabel.setText("ATT.LATE");
        rootLayout.add(LATE_CaptionLabel);
        LATE_DscField39 = new dsc.echo2app.component.DscField();
        LATE_DscField39.setId("LATE_DscField39");
        rootLayout.add(LATE_DscField39);
        NADDCLS_CaptionLabel = new nextapp.echo2.app.Label();
        NADDCLS_CaptionLabel.setText("ATT.NADDCLS");
        rootLayout.add(NADDCLS_CaptionLabel);
        NADDCLS_DscField40 = new dsc.echo2app.component.DscField();
        NADDCLS_DscField40.setId("NADDCLS_DscField40");
        rootLayout.add(NADDCLS_DscField40);
        NADDCLS_S_CaptionLabel = new nextapp.echo2.app.Label();
        NADDCLS_S_CaptionLabel.setText("ATT.NADDCLS_S");
        rootLayout.add(NADDCLS_S_CaptionLabel);
        NADDCLS_S_DscField41 = new dsc.echo2app.component.DscField();
        NADDCLS_S_DscField41.setId("NADDCLS_S_DscField41");
        rootLayout.add(NADDCLS_S_DscField41);
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("ATT.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField42 = new dsc.echo2app.component.DscField();
        NOTE_DscField42.setId("NOTE_DscField42");
        rootLayout.add(NOTE_DscField42);
        NOTE_REST_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_REST_CaptionLabel.setText("ATT.NOTE_REST");
        rootLayout.add(NOTE_REST_CaptionLabel);
        NOTE_REST_DscField43 = new dsc.echo2app.component.DscField();
        NOTE_REST_DscField43.setId("NOTE_REST_DscField43");
        rootLayout.add(NOTE_REST_DscField43);
        NUCLS_CaptionLabel = new nextapp.echo2.app.Label();
        NUCLS_CaptionLabel.setText("ATT.NUCLS");
        rootLayout.add(NUCLS_CaptionLabel);
        NUCLS_DscField44 = new dsc.echo2app.component.DscField();
        NUCLS_DscField44.setId("NUCLS_DscField44");
        rootLayout.add(NUCLS_DscField44);
        NUCLS_S_CaptionLabel = new nextapp.echo2.app.Label();
        NUCLS_S_CaptionLabel.setText("ATT.NUCLS_S");
        rootLayout.add(NUCLS_S_CaptionLabel);
        NUCLS_S_DscField45 = new dsc.echo2app.component.DscField();
        NUCLS_S_DscField45.setId("NUCLS_S_DscField45");
        rootLayout.add(NUCLS_S_DscField45);
        NWHOUR_CaptionLabel = new nextapp.echo2.app.Label();
        NWHOUR_CaptionLabel.setText("ATT.NWHOUR");
        rootLayout.add(NWHOUR_CaptionLabel);
        NWHOUR_DscField46 = new dsc.echo2app.component.DscField();
        NWHOUR_DscField46.setId("NWHOUR_DscField46");
        rootLayout.add(NWHOUR_DscField46);
        OFFD_CaptionLabel = new nextapp.echo2.app.Label();
        OFFD_CaptionLabel.setText("ATT.OFFD");
        rootLayout.add(OFFD_CaptionLabel);
        OFFD_DscDateField2 = new dsc.echo2app.component.DscDateField();
        OFFD_DscDateField2.setId("OFFD_DscDateField2");
        rootLayout.add(OFFD_DscDateField2);
        OTHER_CaptionLabel = new nextapp.echo2.app.Label();
        OTHER_CaptionLabel.setText("ATT.OTHER");
        rootLayout.add(OTHER_CaptionLabel);
        OTHER_DscField47 = new dsc.echo2app.component.DscField();
        OTHER_DscField47.setId("OTHER_DscField47");
        rootLayout.add(OTHER_DscField47);
        PAYTAX_CaptionLabel = new nextapp.echo2.app.Label();
        PAYTAX_CaptionLabel.setText("ATT.PAYTAX");
        rootLayout.add(PAYTAX_CaptionLabel);
        PAYTAX_DscField48 = new dsc.echo2app.component.DscField();
        PAYTAX_DscField48.setId("PAYTAX_DscField48");
        rootLayout.add(PAYTAX_DscField48);
        PAYTAX1_CaptionLabel = new nextapp.echo2.app.Label();
        PAYTAX1_CaptionLabel.setText("ATT.PAYTAX1");
        rootLayout.add(PAYTAX1_CaptionLabel);
        PAYTAX1_DscField49 = new dsc.echo2app.component.DscField();
        PAYTAX1_DscField49.setId("PAYTAX1_DscField49");
        rootLayout.add(PAYTAX1_DscField49);
        PHEP_NS_CaptionLabel = new nextapp.echo2.app.Label();
        PHEP_NS_CaptionLabel.setText("ATT.PHEP_NS");
        rootLayout.add(PHEP_NS_CaptionLabel);
        PHEP_NS_DscField50 = new dsc.echo2app.component.DscField();
        PHEP_NS_DscField50.setId("PHEP_NS_DscField50");
        rootLayout.add(PHEP_NS_DscField50);
        POSSN_CaptionLabel = new nextapp.echo2.app.Label();
        POSSN_CaptionLabel.setText("ATT.POSSN");
        rootLayout.add(POSSN_CaptionLabel);
        POSSN_DscField51 = new dsc.echo2app.component.DscField();
        POSSN_DscField51.setId("POSSN_DscField51");
        rootLayout.add(POSSN_DscField51);
        QT_PAYTAX1_CaptionLabel = new nextapp.echo2.app.Label();
        QT_PAYTAX1_CaptionLabel.setText("ATT.QT_PAYTAX1");
        rootLayout.add(QT_PAYTAX1_CaptionLabel);
        QT_PAYTAX1_DscField52 = new dsc.echo2app.component.DscField();
        QT_PAYTAX1_DscField52.setId("QT_PAYTAX1_DscField52");
        rootLayout.add(QT_PAYTAX1_DscField52);
        REST_CaptionLabel = new nextapp.echo2.app.Label();
        REST_CaptionLabel.setText("ATT.REST");
        rootLayout.add(REST_CaptionLabel);
        REST_DscField53 = new dsc.echo2app.component.DscField();
        REST_DscField53.setId("REST_DscField53");
        rootLayout.add(REST_DscField53);
        REST_PAY_CaptionLabel = new nextapp.echo2.app.Label();
        REST_PAY_CaptionLabel.setText("ATT.REST_PAY");
        rootLayout.add(REST_PAY_CaptionLabel);
        REST_PAY_DscField54 = new dsc.echo2app.component.DscField();
        REST_PAY_DscField54.setId("REST_PAY_DscField54");
        rootLayout.add(REST_PAY_DscField54);
        REST_PAY_S_CaptionLabel = new nextapp.echo2.app.Label();
        REST_PAY_S_CaptionLabel.setText("ATT.REST_PAY_S");
        rootLayout.add(REST_PAY_S_CaptionLabel);
        REST_PAY_S_DscField55 = new dsc.echo2app.component.DscField();
        REST_PAY_S_DscField55.setId("REST_PAY_S_DscField55");
        rootLayout.add(REST_PAY_S_DscField55);
        REST_SICK_CaptionLabel = new nextapp.echo2.app.Label();
        REST_SICK_CaptionLabel.setText("ATT.REST_SICK");
        rootLayout.add(REST_SICK_CaptionLabel);
        REST_SICK_DscField56 = new dsc.echo2app.component.DscField();
        REST_SICK_DscField56.setId("REST_SICK_DscField56");
        rootLayout.add(REST_SICK_DscField56);
        SIGN_CaptionLabel = new nextapp.echo2.app.Label();
        SIGN_CaptionLabel.setText("ATT.SIGN");
        rootLayout.add(SIGN_CaptionLabel);
        SIGN_DscField57 = new dsc.echo2app.component.DscField();
        SIGN_DscField57.setId("SIGN_DscField57");
        rootLayout.add(SIGN_DscField57);
        STT_CaptionLabel = new nextapp.echo2.app.Label();
        STT_CaptionLabel.setText("ATT.STT");
        rootLayout.add(STT_CaptionLabel);
        STT_DscField58 = new dsc.echo2app.component.DscField();
        STT_DscField58.setId("STT_DscField58");
        rootLayout.add(STT_DscField58);
        TADDCLS_CaptionLabel = new nextapp.echo2.app.Label();
        TADDCLS_CaptionLabel.setText("ATT.TADDCLS");
        rootLayout.add(TADDCLS_CaptionLabel);
        TADDCLS_DscField59 = new dsc.echo2app.component.DscField();
        TADDCLS_DscField59.setId("TADDCLS_DscField59");
        rootLayout.add(TADDCLS_DscField59);
        TADDCLS2_CaptionLabel = new nextapp.echo2.app.Label();
        TADDCLS2_CaptionLabel.setText("ATT.TADDCLS2");
        rootLayout.add(TADDCLS2_CaptionLabel);
        TADDCLS2_DscField60 = new dsc.echo2app.component.DscField();
        TADDCLS2_DscField60.setId("TADDCLS2_DscField60");
        rootLayout.add(TADDCLS2_DscField60);
        TADDS_CaptionLabel = new nextapp.echo2.app.Label();
        TADDS_CaptionLabel.setText("ATT.TADDS");
        rootLayout.add(TADDS_CaptionLabel);
        TADDS_DscField61 = new dsc.echo2app.component.DscField();
        TADDS_DscField61.setId("TADDS_DscField61");
        rootLayout.add(TADDS_DscField61);
        TADDS_NOTAX_CaptionLabel = new nextapp.echo2.app.Label();
        TADDS_NOTAX_CaptionLabel.setText("ATT.TADDS_NOTAX");
        rootLayout.add(TADDS_NOTAX_CaptionLabel);
        TADDS_NOTAX_DscField62 = new dsc.echo2app.component.DscField();
        TADDS_NOTAX_DscField62.setId("TADDS_NOTAX_DscField62");
        rootLayout.add(TADDS_NOTAX_DscField62);
        TBASIC_CaptionLabel = new nextapp.echo2.app.Label();
        TBASIC_CaptionLabel.setText("ATT.TBASIC");
        rootLayout.add(TBASIC_CaptionLabel);
        TBASIC_DscField63 = new dsc.echo2app.component.DscField();
        TBASIC_DscField63.setId("TBASIC_DscField63");
        rootLayout.add(TBASIC_DscField63);
        TBONUS_CaptionLabel = new nextapp.echo2.app.Label();
        TBONUS_CaptionLabel.setText("ATT.TBONUS");
        rootLayout.add(TBONUS_CaptionLabel);
        TBONUS_DscField64 = new dsc.echo2app.component.DscField();
        TBONUS_DscField64.setId("TBONUS_DscField64");
        rootLayout.add(TBONUS_DscField64);
        TDAY_CaptionLabel = new nextapp.echo2.app.Label();
        TDAY_CaptionLabel.setText("ATT.TDAY");
        rootLayout.add(TDAY_CaptionLabel);
        TDAY_DscField65 = new dsc.echo2app.component.DscField();
        TDAY_DscField65.setId("TDAY_DscField65");
        rootLayout.add(TDAY_DscField65);
        TINCOME_CaptionLabel = new nextapp.echo2.app.Label();
        TINCOME_CaptionLabel.setText("ATT.TINCOME");
        rootLayout.add(TINCOME_CaptionLabel);
        TINCOME_DscField66 = new dsc.echo2app.component.DscField();
        TINCOME_DscField66.setId("TINCOME_DscField66");
        rootLayout.add(TINCOME_DscField66);
        TKQ_CaptionLabel = new nextapp.echo2.app.Label();
        TKQ_CaptionLabel.setText("ATT.TKQ");
        rootLayout.add(TKQ_CaptionLabel);
        TKQ_DscField67 = new dsc.echo2app.component.DscField();
        TKQ_DscField67.setId("TKQ_DscField67");
        rootLayout.add(TKQ_DscField67);
        TS1_CaptionLabel = new nextapp.echo2.app.Label();
        TS1_CaptionLabel.setText("ATT.TS1");
        rootLayout.add(TS1_CaptionLabel);
        TS1_DscField68 = new dsc.echo2app.component.DscField();
        TS1_DscField68.setId("TS1_DscField68");
        rootLayout.add(TS1_DscField68);
        TSAL_PAYTAX_CaptionLabel = new nextapp.echo2.app.Label();
        TSAL_PAYTAX_CaptionLabel.setText("ATT.TSAL_PAYTAX");
        rootLayout.add(TSAL_PAYTAX_CaptionLabel);
        TSAL_PAYTAX_DscField69 = new dsc.echo2app.component.DscField();
        TSAL_PAYTAX_DscField69.setId("TSAL_PAYTAX_DscField69");
        rootLayout.add(TSAL_PAYTAX_DscField69);
        TTS_CaptionLabel = new nextapp.echo2.app.Label();
        TTS_CaptionLabel.setText("ATT.TS");
        rootLayout.add(TTS_CaptionLabel);
        TTS_DscField70 = new dsc.echo2app.component.DscField();
        TTS_DscField70.setId("TTS_DscField70");
        rootLayout.add(TTS_DscField70);
        TYADDCLS_CaptionLabel = new nextapp.echo2.app.Label();
        TYADDCLS_CaptionLabel.setText("ATT.TYADDCLS");
        rootLayout.add(TYADDCLS_CaptionLabel);
        TYADDCLS_DscField71 = new dsc.echo2app.component.DscField();
        TYADDCLS_DscField71.setId("TYADDCLS_DscField71");
        rootLayout.add(TYADDCLS_DscField71);
        TY_PAYTAX1_CaptionLabel = new nextapp.echo2.app.Label();
        TY_PAYTAX1_CaptionLabel.setText("ATT.TY_PAYTAX1");
        rootLayout.add(TY_PAYTAX1_CaptionLabel);
        TY_PAYTAX1_DscField72 = new dsc.echo2app.component.DscField();
        TY_PAYTAX1_DscField72.setId("TY_PAYTAX1_DscField72");
        rootLayout.add(TY_PAYTAX1_DscField72);
        YLBX_CaptionLabel = new nextapp.echo2.app.Label();
        YLBX_CaptionLabel.setText("ATT.YLBX");
        rootLayout.add(YLBX_CaptionLabel);
        YLBX_DscField73 = new dsc.echo2app.component.DscField();
        YLBX_DscField73.setId("YLBX_DscField73");
        rootLayout.add(YLBX_DscField73);
	}

}
