package ds.program.fvhr.ui.training;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import ds.program.fvhr.domain.N_TRAINING_ITEM;

public class LichHoc01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label ID_TRAINING_CaptionLabel;
    private dsc.echo2app.component.DscField ID_TRAINING_DscField1;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField2;
    private nextapp.echo2.app.Label ID_KHOA_CaptionLabel;
    private dsc.echo2app.component.DscField ID_KHOA_DscField3;
    private nextapp.echo2.app.Label ID_MON_CaptionLabel;
    private dsc.echo2app.component.DscField ID_MON_DscField4;
    private nextapp.echo2.app.Label TRAINING_TYPE_CaptionLabel;
    private dsc.echo2app.component.DscField TRAINING_TYPE_DscField5;
    private nextapp.echo2.app.Label BDATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField BDATE_DscDateField1;
    private nextapp.echo2.app.Label EDATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField EDATE_DscDateField2;
    private nextapp.echo2.app.Label EMPSN_HL_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_HL_DscField6;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField7;

	/**
	 * Creates a new <code>LichHoc01MDataContent</code>.
	 */
	public LichHoc01MDataContent() {
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
		return N_TRAINING_ITEM.class;
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
		//N_TRAINING_ITEM data = (N_TRAINING_ITEM) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		//N_TRAINING_ITEM data = (N_TRAINING_ITEM) getDataObject();
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
        getUIDataObjectBinder().addBindMap("ID_TRAINING", ID_TRAINING_DscField1, ID_TRAINING_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField2, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_KHOA", ID_KHOA_DscField3, ID_KHOA_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_MON", ID_MON_DscField4, ID_MON_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TRAINING_TYPE", TRAINING_TYPE_DscField5, TRAINING_TYPE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BDATE", BDATE_DscDateField1, BDATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EDATE", EDATE_DscDateField2, EDATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPSN_HL", EMPSN_HL_DscField6, EMPSN_HL_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField7, NOTE_CaptionLabel);
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
		//N_TRAINING_ITEM data = (N_TRAINING_ITEM) getDataObject();
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
		//N_TRAINING_ITEM data = (N_TRAINING_ITEM) getDataObject();
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
        ID_TRAINING_CaptionLabel = new nextapp.echo2.app.Label();
        ID_TRAINING_CaptionLabel.setText("N_TRAINING_ITEM.ID_TRAINING");
        rootLayout.add(ID_TRAINING_CaptionLabel);
        ID_TRAINING_DscField1 = new dsc.echo2app.component.DscField();
        ID_TRAINING_DscField1.setId("ID_TRAINING_DscField1");
        rootLayout.add(ID_TRAINING_DscField1);
        EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_CaptionLabel.setText("N_TRAINING_ITEM.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField2 = new dsc.echo2app.component.DscField();
        EMPSN_DscField2.setId("EMPSN_DscField2");
        rootLayout.add(EMPSN_DscField2);
        ID_KHOA_CaptionLabel = new nextapp.echo2.app.Label();
        ID_KHOA_CaptionLabel.setText("N_TRAINING_ITEM.ID_KHOA");
        rootLayout.add(ID_KHOA_CaptionLabel);
        ID_KHOA_DscField3 = new dsc.echo2app.component.DscField();
        ID_KHOA_DscField3.setId("ID_KHOA_DscField3");
        rootLayout.add(ID_KHOA_DscField3);
        ID_MON_CaptionLabel = new nextapp.echo2.app.Label();
        ID_MON_CaptionLabel.setText("N_TRAINING_ITEM.ID_MON");
        rootLayout.add(ID_MON_CaptionLabel);
        ID_MON_DscField4 = new dsc.echo2app.component.DscField();
        ID_MON_DscField4.setId("ID_MON_DscField4");
        rootLayout.add(ID_MON_DscField4);
        TRAINING_TYPE_CaptionLabel = new nextapp.echo2.app.Label();
        TRAINING_TYPE_CaptionLabel.setText("N_TRAINING_ITEM.TRAINING_TYPE");
        rootLayout.add(TRAINING_TYPE_CaptionLabel);
        TRAINING_TYPE_DscField5 = new dsc.echo2app.component.DscField();
        TRAINING_TYPE_DscField5.setId("TRAINING_TYPE_DscField5");
        rootLayout.add(TRAINING_TYPE_DscField5);
        BDATE_CaptionLabel = new nextapp.echo2.app.Label();
        BDATE_CaptionLabel.setText("N_TRAINING_ITEM.BDATE");
        rootLayout.add(BDATE_CaptionLabel);
        BDATE_DscDateField1 = new dsc.echo2app.component.DscDateField();
        BDATE_DscDateField1.setId("BDATE_DscDateField1");
        rootLayout.add(BDATE_DscDateField1);
        EDATE_CaptionLabel = new nextapp.echo2.app.Label();
        EDATE_CaptionLabel.setText("N_TRAINING_ITEM.EDATE");
        rootLayout.add(EDATE_CaptionLabel);
        EDATE_DscDateField2 = new dsc.echo2app.component.DscDateField();
        EDATE_DscDateField2.setId("EDATE_DscDateField2");
        rootLayout.add(EDATE_DscDateField2);
        EMPSN_HL_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_HL_CaptionLabel.setText("N_TRAINING_ITEM.EMPSN_HL");
        rootLayout.add(EMPSN_HL_CaptionLabel);
        EMPSN_HL_DscField6 = new dsc.echo2app.component.DscField();
        EMPSN_HL_DscField6.setId("EMPSN_HL_DscField6");
        rootLayout.add(EMPSN_HL_DscField6);
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_TRAINING_ITEM.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField7 = new dsc.echo2app.component.DscField();
        NOTE_DscField7.setId("NOTE_DscField7");
        rootLayout.add(NOTE_DscField7);
	}

}
