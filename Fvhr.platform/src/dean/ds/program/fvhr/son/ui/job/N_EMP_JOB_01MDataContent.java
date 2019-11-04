package ds.program.fvhr.son.ui.job;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import ds.program.fvhr.domain.N_EMP_INFO;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;

public class N_EMP_JOB_01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label LNAME_CaptionLabel;
    private dsc.echo2app.component.DscField LNAME_DscField2;
    private nextapp.echo2.app.Label DEPSN_CaptionLabel;
    private dsc.echo2app.component.DscField DEPSN_DscField3;
    private nextapp.echo2.app.Label ID_JOB_CaptionLabel;
    private dsc.echo2app.component.DscField ID_JOB_DscField4;
    private nextapp.echo2.app.Label USER_MANAGE_ID_CaptionLabel;
    private dsc.echo2app.component.DscField USER_MANAGE_ID_DscField5;

   
	/**
	 * Creates a new <code>N_EMP_JOB_01MDataContent</code>.
	 */
    
    
	public N_EMP_JOB_01MDataContent() {
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
            LNAME_DscField2.setEnabled(false);
            LNAME_DscField2.setStyleName("Default.ReadonlyField");
            DEPSN_DscField3.setEnabled(false);
            DEPSN_DscField3.setStyleName("Default.ReadonlyField");
            ID_JOB_DscField4.setEnabled(false);
            ID_JOB_DscField4.setStyleName("Default.ReadonlyField");
            USER_MANAGE_ID_DscField5.setEnabled(false);
            USER_MANAGE_ID_DscField5.setStyleName("Default.ReadonlyField");
            //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            //----------------------------------------------------------------------------------
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_EDIT) {
				EMPSN_DscField1.setEnabled(false);
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
		return N_EMP_INFO.class;
	}

	
	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */

	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值
		//N_EMP_INFO data = (N_EMP_INFO) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */

	public boolean checkDataObject() {
		//N_EMP_INFO data = (N_EMP_INFO) getDataObject();
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
	 

	protected boolean autoPrimaryKeyValue() {
		//使用者可以在以下區域撰寫產生新序號的程式碼並將新序號放入dataObject
		//N_EMP_INFO data = (N_EMP_INFO) getDataObject();
		//Connection conn = Application.getApp().getConnection();
		//Map keys = new HashMap();
		//Map filters = new HashMap();
		//String newValue = KeyNumberGenerator.generateNumber(conn,
		//		"$Y{2}$M{2}$D{2}$S{4}", 
		//		1,
		//		"N_EMP_INFO", "{ID_FIELD}", 
		//		keys, filters, 
		//		true, false);
		
		//產生新序號並放入dataObject
		//data.set{ID_FIELD}(newValue);
		return true;
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
		ObjUtility obj_util = new ObjUtility();
        dsc.echo2app.propertyeditors.MappingPropertyEditor editor = null;
        this.getUIDataObjectBinder().registerCustomEditor(N_EMP_INFO.class,"LNAME", obj_util.Get_MapEditor_EMPSN_NAME());
        this.getUIDataObjectBinder().registerCustomEditor(N_EMP_INFO.class, "DEPSN", obj_util.Get_MapEditor_DEPSN_NAME());
        this.getUIDataObjectBinder().registerCustomEditor(N_EMP_INFO.class, "ID_JOB", obj_util.Get_MapEditor_JOB_NAME());
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("LNAME", LNAME_DscField2, LNAME_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DEPSN", DEPSN_DscField3, DEPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_JOB", ID_JOB_DscField4, ID_JOB_CaptionLabel);
        getUIDataObjectBinder().addBindMap("USER_MANAGE_ID", USER_MANAGE_ID_DscField5, USER_MANAGE_ID_CaptionLabel);
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
		//N_EMP_INFO data = (N_EMP_INFO) getDataObject();
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
		N_EMP_INFO data = (N_EMP_INFO) getDataObject();
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
		    autoPrimaryKeyValue();
		}
		//使用者可在以下區域做儲存資料物件至DataObjectSet之前處理
		//<儲存前處理>
		/*if(!_id_job.equals("")){
			data.setID_JOB(_id_job);
			_id_job = "";
		}*/
		
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
        EMPSN_CaptionLabel.setText("N_EMP_INFO.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField1 = new dsc.echo2app.component.DscField();
        EMPSN_DscField1.setId("EMPSN_DscField1");
        rootLayout.add(EMPSN_DscField1);
        
        DEPSN_CaptionLabel = new nextapp.echo2.app.Label();
        DEPSN_CaptionLabel.setText("N_EMP_INFO.DEPSN");
        rootLayout.add(DEPSN_CaptionLabel);
        DEPSN_DscField3 = new dsc.echo2app.component.DscField();
        DEPSN_DscField3.setId("DEPSN_DscField3");
        DEPSN_DscField3.setWidth(new Extent(200));
        rootLayout.add(DEPSN_DscField3);
        
        LNAME_CaptionLabel = new nextapp.echo2.app.Label();
        LNAME_CaptionLabel.setText("N_EMP_INFO.LNAME");
        rootLayout.add(LNAME_CaptionLabel);
        LNAME_DscField2 = new dsc.echo2app.component.DscField();
        LNAME_DscField2.setId("LNAME_DscField2");
        LNAME_DscField2.setWidth(new Extent(200));
        rootLayout.add(LNAME_DscField2);
        
        
        
        ID_JOB_CaptionLabel = new nextapp.echo2.app.Label();
        ID_JOB_CaptionLabel.setText("N_EMP_INFO.ID_JOB");
        rootLayout.add(ID_JOB_CaptionLabel);
        ID_JOB_DscField4 = new dsc.echo2app.component.DscField();
        ID_JOB_DscField4.setId("ID_JOB_DscField4");
        ID_JOB_DscField4.setWidth(new Extent(200));
        rootLayout.add(ID_JOB_DscField4);
        
        rootLayout.add(new Label());
        rootLayout.add(new Label());
        
        
        USER_MANAGE_ID_CaptionLabel = new nextapp.echo2.app.Label();
        USER_MANAGE_ID_CaptionLabel.setText("N_EMP_INFO.USER_MANAGE_ID");
        rootLayout.add(USER_MANAGE_ID_CaptionLabel);
        USER_MANAGE_ID_DscField5 = new dsc.echo2app.component.DscField();
        USER_MANAGE_ID_DscField5.setId("USER_MANAGE_ID_DscField5");
        USER_MANAGE_ID_DscField5.setWidth(new Extent(200));
        rootLayout.add(USER_MANAGE_ID_DscField5);
	}

}
