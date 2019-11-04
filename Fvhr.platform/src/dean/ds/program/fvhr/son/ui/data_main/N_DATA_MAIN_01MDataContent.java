package ds.program.fvhr.son.ui.data_main;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import ds.program.fvhr.domain.N_DATA_MAIN;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;

public class N_DATA_MAIN_01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label DATES_CaptionLabel;
    private dsc.echo2app.component.DscField DATES_DscField1;
    private nextapp.echo2.app.Label EMPCN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPCN_DscField2;
    private nextapp.echo2.app.Label LOCKED_CaptionLabel;
    private dsc.echo2app.component.DscField LOCKED_DscField3;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField4;
    private nextapp.echo2.app.Label TIMES_CaptionLabel;
    private dsc.echo2app.component.DscField TIMES_DscField5;

	/**
	 * Creates a new <code>N_DATA_MAIN_01MDataContent</code>.
	 */
	public N_DATA_MAIN_01MDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
	}


	/*
	 * UI資料欄位屬性更新，一般是資料模式變換時會呼叫此method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doUIRefresh()
	 */

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
		return N_DATA_MAIN.class;
	}

	
	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */

	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值
		//N_DATA_MAIN data = (N_DATA_MAIN) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */

	public boolean checkDataObject() {
		//N_DATA_MAIN data = (N_DATA_MAIN) getDataObject();
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
        getUIDataObjectBinder().addBindMap("DATES", DATES_DscField1, DATES_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPCN", EMPCN_DscField2, EMPCN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("LOCKED", LOCKED_DscField3, LOCKED_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField4, NOTE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TIMES", TIMES_DscField5, TIMES_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	

	/*
	 * 畫面Layout初始化Method
	 * 
	 * @see dsc.echo2app.program.DataContent#doLayout()
	 */

	protected void doLayout() {
		super.doLayout();
		
		//<<從此以下加入使用者程式>>
	}
	

	/*
	 * 做資料新增時內定預設數值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doNewDefaulData()
	 */

	protected void doNewDefaulData() {
		//N_DATA_MAIN data = (N_DATA_MAIN) getDataObject();
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

	public void beforeSaveToDataObjectSet() {
		//N_DATA_MAIN data = (N_DATA_MAIN) getDataObject();
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
        DATES_CaptionLabel = new nextapp.echo2.app.Label();
        DATES_CaptionLabel.setText("N_DATA_MAIN.DATES");
        rootLayout.add(DATES_CaptionLabel);
        DATES_DscField1 = new dsc.echo2app.component.DscField();
        DATES_DscField1.setId("DATES_DscField1");
        rootLayout.add(DATES_DscField1);
        EMPCN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPCN_CaptionLabel.setText("N_DATA_MAIN.EMPCN");
        rootLayout.add(EMPCN_CaptionLabel);
        EMPCN_DscField2 = new dsc.echo2app.component.DscField();
        EMPCN_DscField2.setId("EMPCN_DscField2");
        rootLayout.add(EMPCN_DscField2);
        LOCKED_CaptionLabel = new nextapp.echo2.app.Label();
        LOCKED_CaptionLabel.setText("N_DATA_MAIN.LOCKED");
        rootLayout.add(LOCKED_CaptionLabel);
        LOCKED_DscField3 = new dsc.echo2app.component.DscField();
        LOCKED_DscField3.setId("LOCKED_DscField3");
        rootLayout.add(LOCKED_DscField3);
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_DATA_MAIN.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField4 = new dsc.echo2app.component.DscField();
        NOTE_DscField4.setId("NOTE_DscField4");
        rootLayout.add(NOTE_DscField4);
        TIMES_CaptionLabel = new nextapp.echo2.app.Label();
        TIMES_CaptionLabel.setText("N_DATA_MAIN.TIMES");
        rootLayout.add(TIMES_CaptionLabel);
        TIMES_DscField5 = new dsc.echo2app.component.DscField();
        TIMES_DscField5.setId("TIMES_DscField5");
        rootLayout.add(TIMES_DscField5);
	}

}
