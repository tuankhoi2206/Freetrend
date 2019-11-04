package ds.program.fvhr.tien.ui;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.PropertyEditors;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.domain.N_SP_WDAY;

public class N_ACTION_DAILY00MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label ACTIONNAME_CaptionLabel;
    private dsc.echo2app.component.DscField ACTIONNAME_DscField1;
    private nextapp.echo2.app.Label ACTION_DATETIME_CaptionLabel;
    private dsc.echo2app.component.DscDateField ACTION_DATETIME_DscDateField1;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField2;
    private nextapp.echo2.app.Label IDUSER_CaptionLabel;
    private dsc.echo2app.component.DscField IDUSER_DscField3;
    private nextapp.echo2.app.Label ID_ACTION_CaptionLabel;
    private dsc.echo2app.component.DscField ID_ACTION_DscField4;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField5;
    private nextapp.echo2.app.Label TABLENAME_CaptionLabel;
    private dsc.echo2app.component.DscField TABLENAME_DscField6;

	/**
	 * Creates a new <code>N_ACTION_DAILY00MDataContent</code>.
	 */
	public N_ACTION_DAILY00MDataContent() {
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
		return N_ACTION_DAILY.class;
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
		//N_ACTION_DAILY data = (N_ACTION_DAILY) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		//N_ACTION_DAILY data = (N_ACTION_DAILY) getDataObject();
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
	/*@Override
	protected boolean autoPrimaryKeyValue() {
		//使用者可以在以下區域撰寫產生新序號的程式碼並將新序號放入dataObject
		//N_ACTION_DAILY data = (N_ACTION_DAILY) getDataObject();
		//Connection conn = Application.getApp().getConnection();
		//Map keys = new HashMap();
		//Map filters = new HashMap();
		//String newValue = KeyNumberGenerator.generateNumber(conn,
		//		"$Y{2}$M{2}$D{2}$S{4}", 
		//		1,
		//		"N_ACTION_DAILY", "{ID_FIELD}", 
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
    	getUIDataObjectBinder().registerCustomEditor(N_ACTION_DAILY.class, "ACTION_DATETIME", PropertyEditors.createDateEditor("dd/MM/yyyy"));
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("ACTIONNAME", ACTIONNAME_DscField1, ACTIONNAME_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ACTION_DATETIME", ACTION_DATETIME_DscDateField1, ACTION_DATETIME_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField2, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("IDUSER", IDUSER_DscField3, IDUSER_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_ACTION", ID_ACTION_DscField4, ID_ACTION_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField5, NOTE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TABLENAME", TABLENAME_DscField6, TABLENAME_CaptionLabel);
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
		//N_ACTION_DAILY data = (N_ACTION_DAILY) getDataObject();
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
		//N_ACTION_DAILY data = (N_ACTION_DAILY) getDataObject();
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
        ACTIONNAME_CaptionLabel = new nextapp.echo2.app.Label();
        ACTIONNAME_CaptionLabel.setText("N_ACTION_DAILY.ACTIONNAME");
        rootLayout.add(ACTIONNAME_CaptionLabel);
        ACTIONNAME_DscField1 = new dsc.echo2app.component.DscField();
        ACTIONNAME_DscField1.setId("ACTIONNAME_DscField1");
        rootLayout.add(ACTIONNAME_DscField1);
        ACTION_DATETIME_CaptionLabel = new nextapp.echo2.app.Label();
        ACTION_DATETIME_CaptionLabel.setText("N_ACTION_DAILY.ACTION_DATETIME");
        rootLayout.add(ACTION_DATETIME_CaptionLabel);
        ACTION_DATETIME_DscDateField1 = new dsc.echo2app.component.DscDateField();
        ACTION_DATETIME_DscDateField1.setId("ACTION_DATETIME_DscDateField1");
        rootLayout.add(ACTION_DATETIME_DscDateField1);
        EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_CaptionLabel.setText("N_ACTION_DAILY.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField2 = new dsc.echo2app.component.DscField();
        EMPSN_DscField2.setId("EMPSN_DscField2");
        rootLayout.add(EMPSN_DscField2);
        IDUSER_CaptionLabel = new nextapp.echo2.app.Label();
        IDUSER_CaptionLabel.setText("N_ACTION_DAILY.IDUSER");
        rootLayout.add(IDUSER_CaptionLabel);
        IDUSER_DscField3 = new dsc.echo2app.component.DscField();
        IDUSER_DscField3.setId("IDUSER_DscField3");
        rootLayout.add(IDUSER_DscField3);
        ID_ACTION_CaptionLabel = new nextapp.echo2.app.Label();
        ID_ACTION_CaptionLabel.setText("N_ACTION_DAILY.ID_ACTION");
        rootLayout.add(ID_ACTION_CaptionLabel);
        ID_ACTION_DscField4 = new dsc.echo2app.component.DscField();
        ID_ACTION_DscField4.setId("ID_ACTION_DscField4");
        rootLayout.add(ID_ACTION_DscField4);
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_ACTION_DAILY.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField5 = new dsc.echo2app.component.DscField();
        NOTE_DscField5.setId("NOTE_DscField5");
        rootLayout.add(NOTE_DscField5);
        TABLENAME_CaptionLabel = new nextapp.echo2.app.Label();
        TABLENAME_CaptionLabel.setText("N_ACTION_DAILY.TABLENAME");
        rootLayout.add(TABLENAME_CaptionLabel);
        TABLENAME_DscField6 = new dsc.echo2app.component.DscField();
        TABLENAME_DscField6.setId("TABLENAME_DscField6");
        rootLayout.add(TABLENAME_DscField6);
	}

}
