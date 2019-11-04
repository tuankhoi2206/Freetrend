package ds.program.fvhr.ngan.ui.Rest_N;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import ds.program.fvhr.domain.N_REST_DETAIL;

public class Rest_Detail_01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label DATE_OFF_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_OFF_DscDateField1;
    private nextapp.echo2.app.Label HOUR_CaptionLabel;
    private dsc.echo2app.component.DscField HOUR_DscField2;
    private nextapp.echo2.app.Label LOCKED_CaptionLabel;
    private dsc.echo2app.component.DscField LOCKED_DscField3;
    private nextapp.echo2.app.Label MID_TIME_CaptionLabel;
    private dsc.echo2app.component.DscField MID_TIME_DscField4;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField5;
    private nextapp.echo2.app.Label NOTE1_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE1_DscField6;
    private nextapp.echo2.app.Label NOTE_TIME_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_TIME_DscField7;
    private nextapp.echo2.app.Label REASON_CaptionLabel;
    private dsc.echo2app.component.DscField REASON_DscField8;
    private nextapp.echo2.app.Label REST_KIND_CaptionLabel;
    private dsc.echo2app.component.DscField REST_KIND_DscField9;
    private nextapp.echo2.app.Label REST_SAL_CaptionLabel;
    private dsc.echo2app.component.DscField REST_SAL_DscField10;
    private nextapp.echo2.app.Label REST_TYPE_CaptionLabel;
    private dsc.echo2app.component.DscField REST_TYPE_DscField11;
    private nextapp.echo2.app.Label TIN_CaptionLabel;
    private dsc.echo2app.component.DscField TIN_DscField12;
    private nextapp.echo2.app.Label TOTAL_CaptionLabel;
    private dsc.echo2app.component.DscField TOTAL_DscField13;
    private nextapp.echo2.app.Label TOUT_CaptionLabel;
    private dsc.echo2app.component.DscField TOUT_DscField14;
    private nextapp.echo2.app.Label YEAR_CaptionLabel;
    private dsc.echo2app.component.DscField YEAR_DscField15;
    private nextapp.echo2.app.Label USER_UP_CaptionLabel;
    private dsc.echo2app.component.DscField USER_UP_DscField16;
    private nextapp.echo2.app.Label DATE_UP_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_UP_DscDateField2;

	/**
	 * Creates a new <code>Rest_Detail_01MDataContent</code>.
	 */
	public Rest_Detail_01MDataContent() {
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
		return N_REST_DETAIL.class;
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
		//N_REST_DETAIL data = (N_REST_DETAIL) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		//N_REST_DETAIL data = (N_REST_DETAIL) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			//使用者可在以下區域撰寫內容合理值的判斷程式碼
			//此單頭為readonly 直接return true 
		}
		return ret;
	}
	
	/*
	 * 自動編號 
	 * <b>必須繼承改寫</b>
	 * @see dsc.echo2app.program.DataContent#autoPrimaryKeyValue()
	 */
/*	@Override
	protected boolean autoPrimaryKeyValue() {
		//使用者可以在以下區域撰寫產生新序號的程式碼並將新序號放入dataObject
		//N_REST_DETAIL data = (N_REST_DETAIL) getDataObject();
		//Connection conn = Application.getApp().getConnection();
		//Map keys = new HashMap();
		//Map filters = new HashMap();
		//String newValue = KeyNumberGenerator.generateNumber(conn,
		//		"$Y{2}$M{2}$D{2}$S{4}", 
		//		1,
		//		"N_REST_DETAIL", "{ID_FIELD}", 
		//		keys, filters, 
		//		true, false);
		
		//產生新序號並放入dataObject
		//data.set{ID_FIELD}(newValue);
		return true;
	}*/

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
        getUIDataObjectBinder().addBindMap("DATE_OFF", DATE_OFF_DscDateField1, DATE_OFF_CaptionLabel);
        getUIDataObjectBinder().addBindMap("HOUR", HOUR_DscField2, HOUR_CaptionLabel);
        getUIDataObjectBinder().addBindMap("LOCKED", LOCKED_DscField3, LOCKED_CaptionLabel);
        getUIDataObjectBinder().addBindMap("MID_TIME", MID_TIME_DscField4, MID_TIME_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField5, NOTE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE1", NOTE1_DscField6, NOTE1_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE_TIME", NOTE_TIME_DscField7, NOTE_TIME_CaptionLabel);
        getUIDataObjectBinder().addBindMap("REASON", REASON_DscField8, REASON_CaptionLabel);
        getUIDataObjectBinder().addBindMap("REST_KIND", REST_KIND_DscField9, REST_KIND_CaptionLabel);
        getUIDataObjectBinder().addBindMap("REST_SAL", REST_SAL_DscField10, REST_SAL_CaptionLabel);
        getUIDataObjectBinder().addBindMap("REST_TYPE", REST_TYPE_DscField11, REST_TYPE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TIN", TIN_DscField12, TIN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TOTAL", TOTAL_DscField13, TOTAL_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TOUT", TOUT_DscField14, TOUT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("YEAR", YEAR_DscField15, YEAR_CaptionLabel);
        getUIDataObjectBinder().addBindMap("USER_UP", USER_UP_DscField16, USER_UP_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_UP", DATE_UP_DscDateField2, DATE_UP_CaptionLabel);
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
		//N_REST_DETAIL data = (N_REST_DETAIL) getDataObject();
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
		//N_REST_DETAIL data = (N_REST_DETAIL) getDataObject();
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
        EMPSN_CaptionLabel.setText("EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField1 = new dsc.echo2app.component.DscField();
        EMPSN_DscField1.setId("EMPSN_DscField1");
        rootLayout.add(EMPSN_DscField1);
        DATE_OFF_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_OFF_CaptionLabel.setText("DATE_OFF");
        rootLayout.add(DATE_OFF_CaptionLabel);
        DATE_OFF_DscDateField1 = new dsc.echo2app.component.DscDateField();
        DATE_OFF_DscDateField1.setId("DATE_OFF_DscDateField1");
        rootLayout.add(DATE_OFF_DscDateField1);
        HOUR_CaptionLabel = new nextapp.echo2.app.Label();
        HOUR_CaptionLabel.setText("HOUR	");
        rootLayout.add(HOUR_CaptionLabel);
        HOUR_DscField2 = new dsc.echo2app.component.DscField();
        HOUR_DscField2.setId("HOUR_DscField2");
        rootLayout.add(HOUR_DscField2);
        LOCKED_CaptionLabel = new nextapp.echo2.app.Label();
        LOCKED_CaptionLabel.setText("LOCKED");
        rootLayout.add(LOCKED_CaptionLabel);
        LOCKED_DscField3 = new dsc.echo2app.component.DscField();
        LOCKED_DscField3.setId("LOCKED_DscField3");
        rootLayout.add(LOCKED_DscField3);
        MID_TIME_CaptionLabel = new nextapp.echo2.app.Label();
        MID_TIME_CaptionLabel.setText("MID_TIME");
        rootLayout.add(MID_TIME_CaptionLabel);
        MID_TIME_DscField4 = new dsc.echo2app.component.DscField();
        MID_TIME_DscField4.setId("MID_TIME_DscField4");
        rootLayout.add(MID_TIME_DscField4);
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField5 = new dsc.echo2app.component.DscField();
        NOTE_DscField5.setId("NOTE_DscField5");
        rootLayout.add(NOTE_DscField5);
        NOTE1_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE1_CaptionLabel.setText("NOTE1");
        rootLayout.add(NOTE1_CaptionLabel);
        NOTE1_DscField6 = new dsc.echo2app.component.DscField();
        NOTE1_DscField6.setId("NOTE1_DscField6");
        rootLayout.add(NOTE1_DscField6);
        NOTE_TIME_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_TIME_CaptionLabel.setText("NOTE_TIME");
        rootLayout.add(NOTE_TIME_CaptionLabel);
        NOTE_TIME_DscField7 = new dsc.echo2app.component.DscField();
        NOTE_TIME_DscField7.setId("NOTE_TIME_DscField7");
        rootLayout.add(NOTE_TIME_DscField7);
        REASON_CaptionLabel = new nextapp.echo2.app.Label();
        REASON_CaptionLabel.setText("N_REST_DETAIL.REASON");
        rootLayout.add(REASON_CaptionLabel);
        REASON_DscField8 = new dsc.echo2app.component.DscField();
        REASON_DscField8.setId("REASON_DscField8");
        rootLayout.add(REASON_DscField8);
        REST_KIND_CaptionLabel = new nextapp.echo2.app.Label();
        REST_KIND_CaptionLabel.setText("REST_KIND");
        rootLayout.add(REST_KIND_CaptionLabel);
        REST_KIND_DscField9 = new dsc.echo2app.component.DscField();
        REST_KIND_DscField9.setId("REST_KIND_DscField9");
        rootLayout.add(REST_KIND_DscField9);
        REST_SAL_CaptionLabel = new nextapp.echo2.app.Label();
        REST_SAL_CaptionLabel.setText("REST_SAL");
        rootLayout.add(REST_SAL_CaptionLabel);
        REST_SAL_DscField10 = new dsc.echo2app.component.DscField();
        REST_SAL_DscField10.setId("REST_SAL_DscField10");
        rootLayout.add(REST_SAL_DscField10);
        REST_TYPE_CaptionLabel = new nextapp.echo2.app.Label();
        REST_TYPE_CaptionLabel.setText("REST_TYPE");
        rootLayout.add(REST_TYPE_CaptionLabel);
        REST_TYPE_DscField11 = new dsc.echo2app.component.DscField();
        REST_TYPE_DscField11.setId("REST_TYPE_DscField11");
        rootLayout.add(REST_TYPE_DscField11);
        TIN_CaptionLabel = new nextapp.echo2.app.Label();
        TIN_CaptionLabel.setText("TIN");
        rootLayout.add(TIN_CaptionLabel);
        TIN_DscField12 = new dsc.echo2app.component.DscField();
        TIN_DscField12.setId("TIN_DscField12");
        rootLayout.add(TIN_DscField12);
        TOTAL_CaptionLabel = new nextapp.echo2.app.Label();
        TOTAL_CaptionLabel.setText("TOTAL");
        rootLayout.add(TOTAL_CaptionLabel);
        TOTAL_DscField13 = new dsc.echo2app.component.DscField();
        TOTAL_DscField13.setId("TOTAL_DscField13");
        rootLayout.add(TOTAL_DscField13);
        TOUT_CaptionLabel = new nextapp.echo2.app.Label();
        TOUT_CaptionLabel.setText("TOUT");
        rootLayout.add(TOUT_CaptionLabel);
        TOUT_DscField14 = new dsc.echo2app.component.DscField();
        TOUT_DscField14.setId("TOUT_DscField14");
        rootLayout.add(TOUT_DscField14);
        YEAR_CaptionLabel = new nextapp.echo2.app.Label();
        YEAR_CaptionLabel.setText("YEAR	");
        rootLayout.add(YEAR_CaptionLabel);
        YEAR_DscField15 = new dsc.echo2app.component.DscField();
        YEAR_DscField15.setId("YEAR_DscField15");
        rootLayout.add(YEAR_DscField15);
        USER_UP_CaptionLabel = new nextapp.echo2.app.Label();
        USER_UP_CaptionLabel.setText("USER_UP");
        rootLayout.add(USER_UP_CaptionLabel);
        USER_UP_DscField16 = new dsc.echo2app.component.DscField();
        USER_UP_DscField16.setId("USER_UP_DscField16");
        rootLayout.add(USER_UP_DscField16);
        DATE_UP_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_UP_CaptionLabel.setText("DATE_UP");
        rootLayout.add(DATE_UP_CaptionLabel);
        DATE_UP_DscDateField2 = new dsc.echo2app.component.DscDateField();
        DATE_UP_DscDateField2.setId("DATE_UP_DscDateField2");
        rootLayout.add(DATE_UP_DscDateField2);
	}

}
