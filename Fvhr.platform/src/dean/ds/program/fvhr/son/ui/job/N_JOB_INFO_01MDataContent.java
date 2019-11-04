package ds.program.fvhr.son.ui.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import fv.util.Vni2Uni;
import fv.util.VniEditor;
import ds.program.fvhr.domain.N_JOB;
import ds.program.fvhr.domain.N_JOB_DETAIL;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.PropertyEditors;

public class N_JOB_INFO_01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label ID_KEY_CaptionLabel;
    private dsc.echo2app.component.DscField ID_KEY_DscField1;
    private nextapp.echo2.app.Label NAME_JOB_CaptionLabel;
    private dsc.echo2app.component.DscField NAME_JOB_DscField2;
    private nextapp.echo2.app.Label CODE_JOB_CaptionLabel;
    private dsc.echo2app.component.DscField CODE_JOB_DscField3;
    private nextapp.echo2.app.Label KIND_JOB_CaptionLabel;
    private dsc.echo2app.component.DscField KIND_JOB_DscField4;
    private nextapp.echo2.app.Label IN_FACT_CaptionLabel;
    private dsc.echo2app.component.DscField IN_FACT_DscField5;
    private nextapp.echo2.app.Label MONEY_CaptionLabel;
    private dsc.echo2app.component.DscField MONEY_DscField6;
    private nextapp.echo2.app.Label DATE_UP_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_UP_DscDateField1;
    private nextapp.echo2.app.Label USER_UP_CaptionLabel;
    private dsc.echo2app.component.DscField USER_UP_DscField7;

	/**
	 * Creates a new <code>N_JOB_INFO_01MDataContent</code>.
	 */
    SimpleDateFormat _sf ;
	public N_JOB_INFO_01MDataContent() {
		super();
		_sf	= new SimpleDateFormat("dd/MM/yyyy");
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
            ID_KEY_DscField1.setEnabled(false);
            ID_KEY_DscField1.setStyleName("Default.ReadonlyField");
            NAME_JOB_DscField2.setEnabled(false);
            NAME_JOB_DscField2.setStyleName("Default.ReadonlyField");
            CODE_JOB_DscField3.setEnabled(false);
            CODE_JOB_DscField3.setStyleName("Default.ReadonlyField");
            KIND_JOB_DscField4.setEnabled(false);
            KIND_JOB_DscField4.setStyleName("Default.ReadonlyField");
            IN_FACT_DscField5.setEnabled(false);
            IN_FACT_DscField5.setStyleName("Default.ReadonlyField");
            MONEY_DscField6.setEnabled(false);
            MONEY_DscField6.setStyleName("Default.ReadonlyField");
            DATE_UP_DscDateField1.setEnabled(false);
            DATE_UP_DscDateField1.setStyleName("Default.ReadonlyField");
            USER_UP_DscField7.setEnabled(false);
            USER_UP_DscField7.setStyleName("Default.ReadonlyField");
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
		return N_JOB.class;
	}

	
	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */

	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值
		//N_JOB data = (N_JOB) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */

	public boolean checkDataObject() {
		//N_JOB data = (N_JOB) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			//使用者可在以下區域撰寫內容合理值的判斷程式碼
			//此單頭為readonly 直接return true 
		}
		return ret;
	}
	

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
        getUIDataObjectBinder().registerCustomEditor(N_JOB.class,"DATE_UP",PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_JOB.class,"NAME_JOB",new VniEditor());
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("ID_KEY", ID_KEY_DscField1, ID_KEY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NAME_JOB", NAME_JOB_DscField2, NAME_JOB_CaptionLabel);
        getUIDataObjectBinder().addBindMap("CODE_JOB", CODE_JOB_DscField3, CODE_JOB_CaptionLabel);
        getUIDataObjectBinder().addBindMap("KIND_JOB", KIND_JOB_DscField4, KIND_JOB_CaptionLabel);
        getUIDataObjectBinder().addBindMap("IN_FACT", IN_FACT_DscField5, IN_FACT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("MONEY", MONEY_DscField6, MONEY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_UP", DATE_UP_DscDateField1, DATE_UP_CaptionLabel);
        getUIDataObjectBinder().addBindMap("USER_UP", USER_UP_DscField7, USER_UP_CaptionLabel);
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
		//N_JOB data = (N_JOB) getDataObject();
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
		
		N_JOB data = (N_JOB) getDataObject();
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
		    autoPrimaryKeyValue();
		}
		
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
        
        ID_KEY_CaptionLabel = new nextapp.echo2.app.Label();
        ID_KEY_CaptionLabel.setText("N_JOB.ID_KEY");
        //rootLayout.add(ID_KEY_CaptionLabel);
        ID_KEY_DscField1 = new dsc.echo2app.component.DscField();
        ID_KEY_DscField1.setId("ID_KEY_DscField1");
        //rootLayout.add(ID_KEY_DscField1);
        
        NAME_JOB_CaptionLabel = new nextapp.echo2.app.Label();
        NAME_JOB_CaptionLabel.setText("N_JOB.NAME_JOB");
        rootLayout.add(NAME_JOB_CaptionLabel);
        NAME_JOB_DscField2 = new dsc.echo2app.component.DscField();
        NAME_JOB_DscField2.setId("NAME_JOB_DscField2");
        rootLayout.add(NAME_JOB_DscField2);
        
        CODE_JOB_CaptionLabel = new nextapp.echo2.app.Label();
        CODE_JOB_CaptionLabel.setText("N_JOB.CODE_JOB");
        rootLayout.add(CODE_JOB_CaptionLabel);
        CODE_JOB_DscField3 = new dsc.echo2app.component.DscField();
        CODE_JOB_DscField3.setId("CODE_JOB_DscField3");
        rootLayout.add(CODE_JOB_DscField3);
        
        KIND_JOB_CaptionLabel = new nextapp.echo2.app.Label();
        KIND_JOB_CaptionLabel.setText("N_JOB.KIND_JOB");
        rootLayout.add(KIND_JOB_CaptionLabel);
        KIND_JOB_DscField4 = new dsc.echo2app.component.DscField();
        KIND_JOB_DscField4.setId("KIND_JOB_DscField4");
        rootLayout.add(KIND_JOB_DscField4);
        
        IN_FACT_CaptionLabel = new nextapp.echo2.app.Label();
        IN_FACT_CaptionLabel.setText("N_JOB.IN_FACT");
        rootLayout.add(IN_FACT_CaptionLabel);
        IN_FACT_DscField5 = new dsc.echo2app.component.DscField();
        IN_FACT_DscField5.setId("IN_FACT_DscField5");
        rootLayout.add(IN_FACT_DscField5);
        
        MONEY_CaptionLabel = new nextapp.echo2.app.Label();
        MONEY_CaptionLabel.setText("N_JOB.MONEY");
        rootLayout.add(MONEY_CaptionLabel);
        MONEY_DscField6 = new dsc.echo2app.component.DscField();
        MONEY_DscField6.setId("MONEY_DscField6");
        rootLayout.add(MONEY_DscField6);
        
        DATE_UP_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_UP_CaptionLabel.setText("N_JOB.DATE_UP");
        //rootLayout.add(DATE_UP_CaptionLabel);
        DATE_UP_DscDateField1 = new dsc.echo2app.component.DscDateField();
        DATE_UP_DscDateField1.setId("DATE_UP_DscDateField1");
        DATE_UP_DscDateField1.setDateFormat(_sf);
        //rootLayout.add(DATE_UP_DscDateField1);
        
        USER_UP_CaptionLabel = new nextapp.echo2.app.Label();
        USER_UP_CaptionLabel.setText("N_JOB.USER_UP");
        //rootLayout.add(USER_UP_CaptionLabel);
        USER_UP_DscField7 = new dsc.echo2app.component.DscField();
        USER_UP_DscField7.setId("USER_UP_DscField7");
        //rootLayout.add(USER_UP_DscField7);
	}

}
