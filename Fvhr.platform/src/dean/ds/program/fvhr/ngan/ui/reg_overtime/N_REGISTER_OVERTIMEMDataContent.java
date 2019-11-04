package ds.program.fvhr.ngan.ui.reg_overtime;

import java.beans.PropertyEditor;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.PropertyEditors;
import ds.program.fvhr.util.OBJ_UTILITY;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_REGISTER_OVERTIME;
import fv.util.VniEditor;

public class N_REGISTER_OVERTIMEMDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label DATE_OVER_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_OVER_DscDateField1;
    private nextapp.echo2.app.Label COUNT_TIME_CaptionLabel;
    private dsc.echo2app.component.DscField COUNT_TIME_DscField2;
    private nextapp.echo2.app.Label LOCKED_CaptionLabel;
    private dsc.echo2app.component.DscField LOCKED_DscField3;
    private nextapp.echo2.app.Label DATE_UP_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_UP_DscDateField2;
    private nextapp.echo2.app.Label USER_UP_CaptionLabel;
    private dsc.echo2app.component.DscField USER_UP_DscField4;

	/**
	 * Creates a new <code>N_REGISTER_OVERTIMEMDataContent</code>.
	 */
	public N_REGISTER_OVERTIMEMDataContent() {
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
		return N_REGISTER_OVERTIME.class;
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
		//N_REGISTER_OVERTIME data = (N_REGISTER_OVERTIME) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		//N_REGISTER_OVERTIME data = (N_REGISTER_OVERTIME) getDataObject();
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
		//N_REGISTER_OVERTIME data = (N_REGISTER_OVERTIME) getDataObject();
		//Connection conn = Application.getApp().getConnection();
		//Map keys = new HashMap();
		//Map filters = new HashMap();
		//String newValue = KeyNumberGenerator.generateNumber(conn,
		//		"$Y{2}$M{2}$D{2}$S{4}", 
		//		1,
		//		"N_REGISTER_OVERTIME", "{ID_FIELD}", 
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
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "FNAME", new VniEditor());
	    getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "LNAME", new VniEditor());
		this.getUIDataObjectBinder().registerCustomEditor(N_REGISTER_OVERTIME.class,"DATE_OVER",PropertyEditors.createDateEditor("dd/MM/yyyy"));
        dsc.echo2app.propertyeditors.MappingPropertyEditor editor = null;
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_OVER", DATE_OVER_DscDateField1, DATE_OVER_CaptionLabel);
        getUIDataObjectBinder().addBindMap("COUNT_TIME", COUNT_TIME_DscField2, COUNT_TIME_CaptionLabel);
        getUIDataObjectBinder().addBindMap("LOCKED", LOCKED_DscField3, LOCKED_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_UP", DATE_UP_DscDateField2, DATE_UP_CaptionLabel);
        getUIDataObjectBinder().addBindMap("USER_UP", USER_UP_DscField4, USER_UP_CaptionLabel);
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
		//N_REGISTER_OVERTIME data = (N_REGISTER_OVERTIME) getDataObject();
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
		//N_REGISTER_OVERTIME data = (N_REGISTER_OVERTIME) getDataObject();
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
        EMPSN_CaptionLabel.setText("N_REGISTER_OVERTIME.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField1 = new dsc.echo2app.component.DscField();
        EMPSN_DscField1.setId("EMPSN_DscField1");
        rootLayout.add(EMPSN_DscField1);
        DATE_OVER_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_OVER_CaptionLabel.setText("N_REGISTER_OVERTIME.DATE_OVER");
        rootLayout.add(DATE_OVER_CaptionLabel);
        DATE_OVER_DscDateField1 = new dsc.echo2app.component.DscDateField();
        DATE_OVER_DscDateField1.setId("DATE_OVER_DscDateField1");
        DATE_OVER_DscDateField1.setDateFormat(OBJ_UTILITY.Get_format_date());
        rootLayout.add(DATE_OVER_DscDateField1);
        COUNT_TIME_CaptionLabel = new nextapp.echo2.app.Label();
        COUNT_TIME_CaptionLabel.setText("N_REGISTER_OVERTIME.COUNT_TIME");
        rootLayout.add(COUNT_TIME_CaptionLabel);
        COUNT_TIME_DscField2 = new dsc.echo2app.component.DscField();
        COUNT_TIME_DscField2.setId("COUNT_TIME_DscField2");
        rootLayout.add(COUNT_TIME_DscField2);
        LOCKED_CaptionLabel = new nextapp.echo2.app.Label();
        LOCKED_CaptionLabel.setText("N_REGISTER_OVERTIME.LOCKED");
        rootLayout.add(LOCKED_CaptionLabel);
        LOCKED_DscField3 = new dsc.echo2app.component.DscField();
        LOCKED_DscField3.setId("LOCKED_DscField3");
        rootLayout.add(LOCKED_DscField3);
        DATE_UP_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_UP_CaptionLabel.setText("N_JOB.DATE_UP");
        rootLayout.add(DATE_UP_CaptionLabel);
        DATE_UP_DscDateField2 = new dsc.echo2app.component.DscDateField();
        DATE_UP_DscDateField2.setId("DATE_UP_DscDateField2");
        rootLayout.add(DATE_UP_DscDateField2);
        USER_UP_CaptionLabel = new nextapp.echo2.app.Label();
        USER_UP_CaptionLabel.setText("N_JOB.USER_UP");
        rootLayout.add(USER_UP_CaptionLabel);
        USER_UP_DscField4 = new dsc.echo2app.component.DscField();
        USER_UP_DscField4.setId("USER_UP_DscField4");
        rootLayout.add(USER_UP_DscField4);
	}

}
