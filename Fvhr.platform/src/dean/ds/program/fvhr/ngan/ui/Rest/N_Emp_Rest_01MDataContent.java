package ds.program.fvhr.ngan.ui.Rest;


import java.util.List;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_EMP_BONUS;
import fv.components.AnyRowNavigator;
import fv.util.Vni2Uni;
import fv.util.VniEditor;
import fv.util.VniSorter;
import ds.program.fvhr.domain.N_REST;
import ds.program.fvhr.ui.workpoints.DataDailyBrowserContent;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.PropertyEditors;
import echopointng.DirectHtml;

public class N_Emp_Rest_01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label YEAR_CaptionLabel;
    private dsc.echo2app.component.DscField YEAR_DscField2;
    private nextapp.echo2.app.Label STORED_CaptionLabel;
    private dsc.echo2app.component.DscField STORED_DscField3;
    private nextapp.echo2.app.Label CO_ARR_CaptionLabel;
    private dsc.echo2app.component.DscField CO_ARR_DscField4;
    private nextapp.echo2.app.Label OBTAIN_CaptionLabel;
    private dsc.echo2app.component.DscField OBTAIN_DscField5;
    private nextapp.echo2.app.Label REMAIN_CaptionLabel;
    private dsc.echo2app.component.DscField REMAIN_DscField6;
    private nextapp.echo2.app.Label USED_CaptionLabel;
    private dsc.echo2app.component.DscField USED_DscField7;
    private nextapp.echo2.app.Label DEBT_CaptionLabel;
    private dsc.echo2app.component.DscField DEBT_DscField8;
    private nextapp.echo2.app.Label EMP_APP_CaptionLabel;
    private dsc.echo2app.component.DscField EMP_APP_DscField9;
    private nextapp.echo2.app.Label HOL_LUNAR_CaptionLabel;
    private dsc.echo2app.component.DscField HOL_LUNAR_DscField10;
    private nextapp.echo2.app.Label STITCHING1_CaptionLabel;
    private dsc.echo2app.component.DscField STITCHING1_DscField11;
    private nextapp.echo2.app.Label STITCHING2_CaptionLabel;
    private dsc.echo2app.component.DscField STITCHING2_DscField12;
    private nextapp.echo2.app.Label SENIORITY_CaptionLabel;
    private dsc.echo2app.component.DscField SENIORITY_DscField13;
    private nextapp.echo2.app.Label USER_ID_CaptionLabel;
    private dsc.echo2app.component.DscField USER_ID_DscField14;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField15;


	/**
	 * Creates a new <code>N_Emp_Rest_01MDataContent</code>.
	 */
    
    private N_Emp_Rest_01MDetailContent0 dc0;
	private Label lblName;
	private Label lblNameDepsn;
    
	public N_Emp_Rest_01MDataContent(N_Emp_Rest_01MDetailContent0 dc0) {
		super();
		this.dc0	= dc0;
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

		
		EMPSN_DscField1.setEnabled(false);
		YEAR_DscField2.setEnabled(false);
		STORED_DscField3.setEnabled(false);
		CO_ARR_DscField4.setEnabled(false);
		OBTAIN_DscField5.setEnabled(false);
		REMAIN_DscField6.setEnabled(false);
		USED_DscField7.setEnabled(false);
		DEBT_DscField8.setEnabled(false);
		EMP_APP_DscField9.setEnabled(false);
		HOL_LUNAR_DscField10.setEnabled(false);
		STITCHING1_DscField11.setEnabled(false);
		STITCHING2_DscField12.setEnabled(false);
		SENIORITY_DscField13.setEnabled(false);
		USER_ID_DscField14.setEnabled(false);
		NOTE_DscField15.setEnabled(false);

		//自動設定元件風格
		getUIDataObjectBinder().doDataBindUIStyle();
	}

	/*
	 * 取得該畫面的DataObject Class
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#getDataObjectClass()
	 */
	public Class getDataObjectClass() {
		return N_REST.class;
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
		//N_REST data = (N_REST) getDataObject();
	}
	
	
	

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		//N_REST data = (N_REST) getDataObject();
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
//		EMPSN_DscField1.setEnabled(false);
		
		return nRet;
	}
	
	private void registPropertyEditor() {
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "FNAME", new VniEditor());
	    getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "LNAME", new VniEditor());
	    getUIDataObjectBinder().registerCustomEditor(N_REST.class, "NOTE", new VniEditor());
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("YEAR", YEAR_DscField2, YEAR_CaptionLabel);
        getUIDataObjectBinder().addBindMap("STORED", STORED_DscField3, STORED_CaptionLabel);
        getUIDataObjectBinder().addBindMap("CO_ARR", CO_ARR_DscField4, CO_ARR_CaptionLabel);
        getUIDataObjectBinder().addBindMap("OBTAIN", OBTAIN_DscField5, OBTAIN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("REMAIN", REMAIN_DscField6, REMAIN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("USED", USED_DscField7, USED_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DEBT", DEBT_DscField8, DEBT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMP_APP", EMP_APP_DscField9, EMP_APP_CaptionLabel);
        getUIDataObjectBinder().addBindMap("HOL_LUNAR", HOL_LUNAR_DscField10, HOL_LUNAR_CaptionLabel);
        getUIDataObjectBinder().addBindMap("STITCHING1", STITCHING1_DscField11, STITCHING1_CaptionLabel);
        getUIDataObjectBinder().addBindMap("STITCHING2", STITCHING2_DscField12, STITCHING2_CaptionLabel);
        getUIDataObjectBinder().addBindMap("SENIORITY", SENIORITY_DscField13, SENIORITY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("USER_ID", USER_ID_DscField14, USER_ID_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField15, NOTE_CaptionLabel);
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
		//N_REST data = (N_REST) getDataObject();
		//<如果要預先取號則在此加入>
		//autoPrimaryKeyValue();
		//使用者可在以下區域填入新增時內定的預設數值
		//<內定值>
	}
	
	public void setName(String name){
		lblName.setText(name);
	}
	
	public void setNameDepsn(String nameDepsn){
		lblNameDepsn.setText(nameDepsn);
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
/*	@Override
	public void beforeSaveToDataObjectSet() {
		N_REST data = (N_REST) getDataObject();
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
		    autoPrimaryKeyValue();
		}
	//	dc0.countRestType();
		data.setUSED(dc0.numRestCNV + dc0.numRestCty + dc0.numRestTet);
		data.setREMAIN((data.getCO_ARR() + data.getEMP_APP() + data.getHOL_LUNAR())-data.getUSED() );
				
		//使用者可在以下區域做儲存資料物件至DataObjectSet之前處理
		//<儲存前處理>
	}
*/
	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
        rootLayout = new Grid();
        rootLayout.setWidth(new Extent(100, Extent.PERCENT));
        rootLayout.setSize(6);
        
        add(rootLayout);
        EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_CaptionLabel.setText("N_REST.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        Row row = new Row();
        EMPSN_DscField1 = new dsc.echo2app.component.DscField();
        EMPSN_DscField1.setId("EMPSN_DscField1");
        row.add(EMPSN_DscField1);
        
        lblName = new Label();
        lblName.setForeground(Color.BLUE);
        row.add(lblName);
        rootLayout.add(row);
        
        YEAR_CaptionLabel = new nextapp.echo2.app.Label();
        YEAR_CaptionLabel.setText("N_REST.YEAR");
        rootLayout.add(YEAR_CaptionLabel);
        Row row1 = new Row();
        YEAR_DscField2 = new dsc.echo2app.component.DscField();
        YEAR_DscField2.setId("YEAR_DscField2");
       // rootLayout.add(YEAR_DscField2);
        row1.add(YEAR_DscField2);
        
        rootLayout.add(new Label());
        rootLayout.add(new Label());
        
        lblNameDepsn = new Label();
        lblNameDepsn.setForeground(Color.BLUE);
        row.add(lblNameDepsn);
        rootLayout.add(row1);
        
        //--------
        
        REMAIN_CaptionLabel = new nextapp.echo2.app.Label();
        REMAIN_CaptionLabel.setText("N_REST.REMAIN");
        rootLayout.add(REMAIN_CaptionLabel);
        REMAIN_DscField6 = new dsc.echo2app.component.DscField();
        REMAIN_DscField6.setId("REMAIN_DscField6");
        rootLayout.add(REMAIN_DscField6);
        
        
        STORED_CaptionLabel = new nextapp.echo2.app.Label();
        STORED_CaptionLabel.setText("N_REST.STORED");
        rootLayout.add(STORED_CaptionLabel);
        STORED_DscField3 = new dsc.echo2app.component.DscField();
        STORED_DscField3.setId("STORED_DscField3");
        rootLayout.add(STORED_DscField3);
        
        rootLayout.add(new Label());
        rootLayout.add(new Label());
        
        
        //--------
        
        HOL_LUNAR_CaptionLabel = new nextapp.echo2.app.Label();
        HOL_LUNAR_CaptionLabel.setText("N_REST.HOL_LUNAR");
//        rootLayout.add(HOL_LUNAR_CaptionLabel);
        HOL_LUNAR_DscField10 = new dsc.echo2app.component.DscField();
        HOL_LUNAR_DscField10.setId("HOL_LUNAR_DscField10");
//        rootLayout.add(HOL_LUNAR_DscField10);
        
        CO_ARR_CaptionLabel = new nextapp.echo2.app.Label();
        CO_ARR_CaptionLabel.setText("N_REST.CO_ARR");
//        rootLayout.add(CO_ARR_CaptionLabel);
        CO_ARR_DscField4 = new dsc.echo2app.component.DscField();
        CO_ARR_DscField4.setId("CO_ARR_DscField4");
//        rootLayout.add(CO_ARR_DscField4);
        
        EMP_APP_CaptionLabel = new nextapp.echo2.app.Label();
        EMP_APP_CaptionLabel.setText("N_REST.EMP_APP");
//        rootLayout.add(EMP_APP_CaptionLabel);
        EMP_APP_DscField9 = new dsc.echo2app.component.DscField();
        EMP_APP_DscField9.setId("EMP_APP_DscField9");
//        rootLayout.add(EMP_APP_DscField9);
        
        //----
        
        USED_CaptionLabel = new nextapp.echo2.app.Label();
        USED_CaptionLabel.setText("N_REST.USED");
//        rootLayout.add(USED_CaptionLabel);
        USED_DscField7 = new dsc.echo2app.component.DscField();
        USED_DscField7.setId("USED_DscField7");
//        rootLayout.add(USED_DscField7);
        
               
        //-----the end
        //==========================
        
        OBTAIN_CaptionLabel = new nextapp.echo2.app.Label();
        OBTAIN_CaptionLabel.setText("N_REST.OBTAIN");
//        rootLayout.add(OBTAIN_CaptionLabel);
        OBTAIN_DscField5 = new dsc.echo2app.component.DscField();
        OBTAIN_DscField5.setId("OBTAIN_DscField5");
//        rootLayout.add(OBTAIN_DscField5);
        
        DEBT_CaptionLabel = new nextapp.echo2.app.Label();
        DEBT_CaptionLabel.setText("N_REST.DEBT");
        //rootLayout.add(DEBT_CaptionLabel);
        DEBT_DscField8 = new dsc.echo2app.component.DscField();
        DEBT_DscField8.setId("DEBT_DscField8");
        //rootLayout.add(DEBT_DscField8);
        
        
        STITCHING1_CaptionLabel = new nextapp.echo2.app.Label();
        STITCHING1_CaptionLabel.setText("N_REST.STITCHING1");
        //rootLayout.add(STITCHING1_CaptionLabel);
        STITCHING1_DscField11 = new dsc.echo2app.component.DscField();
        STITCHING1_DscField11.setId("STITCHING1_DscField11");
        //rootLayout.add(STITCHING1_DscField11);
        STITCHING2_CaptionLabel = new nextapp.echo2.app.Label();
        STITCHING2_CaptionLabel.setText("N_REST.STITCHING2");
        //rootLayout.add(STITCHING2_CaptionLabel);
        STITCHING2_DscField12 = new dsc.echo2app.component.DscField();
        STITCHING2_DscField12.setId("STITCHING2_DscField12");
        //rootLayout.add(STITCHING2_DscField12);
        SENIORITY_CaptionLabel = new nextapp.echo2.app.Label();
        SENIORITY_CaptionLabel.setText("N_REST.SENIORITY");
        //rootLayout.add(SENIORITY_CaptionLabel);
        SENIORITY_DscField13 = new dsc.echo2app.component.DscField();
        SENIORITY_DscField13.setId("SENIORITY_DscField13");
        //rootLayout.add(SENIORITY_DscField13);
        USER_ID_CaptionLabel = new nextapp.echo2.app.Label();
        USER_ID_CaptionLabel.setText("N_REST.USER_ID");
        //rootLayout.add(USER_ID_CaptionLabel);
        USER_ID_DscField14 = new dsc.echo2app.component.DscField();
        USER_ID_DscField14.setId("USER_ID_DscField14");
        //rootLayout.add(USER_ID_DscField14);
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_REST.NOTE");
        //rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField15 = new dsc.echo2app.component.DscField();
        NOTE_DscField15.setId("NOTE_DscField15");
        //rootLayout.add(NOTE_DscField15);
	}

}
