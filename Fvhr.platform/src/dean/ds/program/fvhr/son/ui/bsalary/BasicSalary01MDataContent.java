package ds.program.fvhr.son.ui.bsalary;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import ds.program.fvhr.domain.N_EMPLOYEE;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;

public class BasicSalary01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label BIRTHDAY_CaptionLabel;
    private dsc.echo2app.component.DscField BIRTHDAY_DscField1;
    private nextapp.echo2.app.Label BIRTHPLACE_CaptionLabel;
    private dsc.echo2app.component.DscField BIRTHPLACE_DscField2;
    private nextapp.echo2.app.Label CITY_CaptionLabel;
    private dsc.echo2app.component.DscField CITY_DscField3;
    private nextapp.echo2.app.Label CODE_CaptionLabel;
    private dsc.echo2app.component.DscField CODE_DscField4;
    private nextapp.echo2.app.Label CONTACT_ADDRESS_CaptionLabel;
    private dsc.echo2app.component.DscField CONTACT_ADDRESS_DscField5;
    private nextapp.echo2.app.Label CONTACT_PER_CaptionLabel;
    private dsc.echo2app.component.DscField CONTACT_PER_DscField6;
    private nextapp.echo2.app.Label CONTACT_TEL_CaptionLabel;
    private dsc.echo2app.component.DscField CONTACT_TEL_DscField7;
    private nextapp.echo2.app.Label DATE_HIRED_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_HIRED_DscDateField1;
    private nextapp.echo2.app.Label DATE_LAMLAI_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_LAMLAI_DscDateField2;
    private nextapp.echo2.app.Label DEPSN_CaptionLabel;
    private dsc.echo2app.component.DscField DEPSN_DscField8;
    private nextapp.echo2.app.Label DEPSN_TEMP_CaptionLabel;
    private dsc.echo2app.component.DscField DEPSN_TEMP_DscField9;
    private nextapp.echo2.app.Label DEPSN_TEMP1_CaptionLabel;
    private dsc.echo2app.component.DscField DEPSN_TEMP1_DscField10;
    private nextapp.echo2.app.Label EDUCATION_CaptionLabel;
    private dsc.echo2app.component.DscField EDUCATION_DscField11;
    private nextapp.echo2.app.Label EMPCN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPCN_DscField12;
    private nextapp.echo2.app.Label EMPNO_IN_LIST_CaptionLabel;
    private dsc.echo2app.component.DscField EMPNO_IN_LIST_DscField13;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField14;
    private nextapp.echo2.app.Label ETHNIC_CaptionLabel;
    private dsc.echo2app.component.DscField ETHNIC_DscField15;
    private nextapp.echo2.app.Label FNAME_CaptionLabel;
    private dsc.echo2app.component.DscField FNAME_DscField16;
    private nextapp.echo2.app.Label HAVE_CHILD_CaptionLabel;
    private dsc.echo2app.component.DscField HAVE_CHILD_DscField17;
    private nextapp.echo2.app.Label ID_BONUS_JOBS_CaptionLabel;
    private dsc.echo2app.component.DscField ID_BONUS_JOBS_DscField18;
    private nextapp.echo2.app.Label ID_JOB_CaptionLabel;
    private dsc.echo2app.component.DscField ID_JOB_DscField19;
    private nextapp.echo2.app.Label ID_NO_CaptionLabel;
    private dsc.echo2app.component.DscField ID_NO_DscField20;
    private nextapp.echo2.app.Label ID_PLACE_CaptionLabel;
    private dsc.echo2app.component.DscField ID_PLACE_DscField21;
    private nextapp.echo2.app.Label ID_POSS_CaptionLabel;
    private dsc.echo2app.component.DscField ID_POSS_DscField22;
    private nextapp.echo2.app.Label LNAME_CaptionLabel;
    private dsc.echo2app.component.DscField LNAME_DscField23;
    private nextapp.echo2.app.Label MATERIAL_STATUS_CaptionLabel;
    private dsc.echo2app.component.DscField MATERIAL_STATUS_DscField24;
    private nextapp.echo2.app.Label NGAYCAP_ID_CaptionLabel;
    private dsc.echo2app.component.DscDateField NGAYCAP_ID_DscDateField3;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField25;
    private nextapp.echo2.app.Label NOTE_CNV_MOI_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_CNV_MOI_DscField26;
    private nextapp.echo2.app.Label PERMANENT_ADDRESS_CaptionLabel;
    private dsc.echo2app.component.DscField PERMANENT_ADDRESS_DscField27;
    private nextapp.echo2.app.Label POSITION_CaptionLabel;
    private dsc.echo2app.component.DscField POSITION_DscField28;
    private nextapp.echo2.app.Label RELIGION_CaptionLabel;
    private dsc.echo2app.component.DscField RELIGION_DscField29;
    private nextapp.echo2.app.Label SEX_CaptionLabel;
    private dsc.echo2app.component.DscField SEX_DscField30;
    private nextapp.echo2.app.Label SHIFT_CaptionLabel;
    private dsc.echo2app.component.DscField SHIFT_DscField31;
    private nextapp.echo2.app.Label USER_MANAGE_ID_CaptionLabel;
    private dsc.echo2app.component.DscField USER_MANAGE_ID_DscField33;
    private nextapp.echo2.app.Label WORK_STATUS_CaptionLabel;
    private dsc.echo2app.component.DscField WORK_STATUS_DscField34;

	/**
	 * Creates a new <code>BasicSalary01MDataContent</code>.
	 */
	public BasicSalary01MDataContent() {
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
			
			
			BIRTHDAY_DscField1.setEnabled(false);
            BIRTHDAY_DscField1.setStyleName("Default.ReadonlyField");
            BIRTHPLACE_DscField2.setEnabled(false);
            BIRTHPLACE_DscField2.setStyleName("Default.ReadonlyField");
            CITY_DscField3.setEnabled(false);
            CITY_DscField3.setStyleName("Default.ReadonlyField");
            CODE_DscField4.setEnabled(false);
            CODE_DscField4.setStyleName("Default.ReadonlyField");
            CONTACT_ADDRESS_DscField5.setEnabled(false);
            CONTACT_ADDRESS_DscField5.setStyleName("Default.ReadonlyField");
            CONTACT_PER_DscField6.setEnabled(false);
            CONTACT_PER_DscField6.setStyleName("Default.ReadonlyField");
            CONTACT_TEL_DscField7.setEnabled(false);
            CONTACT_TEL_DscField7.setStyleName("Default.ReadonlyField");
            DATE_HIRED_DscDateField1.setEnabled(false);
            DATE_HIRED_DscDateField1.setStyleName("Default.ReadonlyField");
            DATE_LAMLAI_DscDateField2.setEnabled(false);
            DATE_LAMLAI_DscDateField2.setStyleName("Default.ReadonlyField");
            DEPSN_DscField8.setEnabled(false);
            DEPSN_DscField8.setStyleName("Default.ReadonlyField");
            DEPSN_TEMP_DscField9.setEnabled(false);
            DEPSN_TEMP_DscField9.setStyleName("Default.ReadonlyField");
            DEPSN_TEMP1_DscField10.setEnabled(false);
            DEPSN_TEMP1_DscField10.setStyleName("Default.ReadonlyField");
            EDUCATION_DscField11.setEnabled(false);
            EDUCATION_DscField11.setStyleName("Default.ReadonlyField");
            EMPCN_DscField12.setEnabled(false);
            EMPCN_DscField12.setStyleName("Default.ReadonlyField");
            EMPNO_IN_LIST_DscField13.setEnabled(false);
            EMPNO_IN_LIST_DscField13.setStyleName("Default.ReadonlyField");
            EMPSN_DscField14.setEnabled(false);
            EMPSN_DscField14.setStyleName("Default.ReadonlyField");
            ETHNIC_DscField15.setEnabled(false);
            ETHNIC_DscField15.setStyleName("Default.ReadonlyField");
            FNAME_DscField16.setEnabled(false);
            FNAME_DscField16.setStyleName("Default.ReadonlyField");
            HAVE_CHILD_DscField17.setEnabled(false);
            HAVE_CHILD_DscField17.setStyleName("Default.ReadonlyField");
            ID_BONUS_JOBS_DscField18.setEnabled(false);
            ID_BONUS_JOBS_DscField18.setStyleName("Default.ReadonlyField");
            ID_JOB_DscField19.setEnabled(false);
            ID_JOB_DscField19.setStyleName("Default.ReadonlyField");
            ID_NO_DscField20.setEnabled(false);
            ID_NO_DscField20.setStyleName("Default.ReadonlyField");
            ID_PLACE_DscField21.setEnabled(false);
            ID_PLACE_DscField21.setStyleName("Default.ReadonlyField");
            ID_POSS_DscField22.setEnabled(false);
            ID_POSS_DscField22.setStyleName("Default.ReadonlyField");
            LNAME_DscField23.setEnabled(false);
            LNAME_DscField23.setStyleName("Default.ReadonlyField");
            MATERIAL_STATUS_DscField24.setEnabled(false);
            MATERIAL_STATUS_DscField24.setStyleName("Default.ReadonlyField");
            NGAYCAP_ID_DscDateField3.setEnabled(false);
            NGAYCAP_ID_DscDateField3.setStyleName("Default.ReadonlyField");
            NOTE_DscField25.setEnabled(false);
            NOTE_DscField25.setStyleName("Default.ReadonlyField");
            NOTE_CNV_MOI_DscField26.setEnabled(false);
            NOTE_CNV_MOI_DscField26.setStyleName("Default.ReadonlyField");
            PERMANENT_ADDRESS_DscField27.setEnabled(false);
            PERMANENT_ADDRESS_DscField27.setStyleName("Default.ReadonlyField");
            POSITION_DscField28.setEnabled(false);
            POSITION_DscField28.setStyleName("Default.ReadonlyField");
            RELIGION_DscField29.setEnabled(false);
            RELIGION_DscField29.setStyleName("Default.ReadonlyField");
            SEX_DscField30.setEnabled(false);
            SEX_DscField30.setStyleName("Default.ReadonlyField");
            SHIFT_DscField31.setEnabled(false);
            SHIFT_DscField31.setStyleName("Default.ReadonlyField");
            USER_MANAGE_ID_DscField33.setEnabled(false);
            USER_MANAGE_ID_DscField33.setStyleName("Default.ReadonlyField");
            WORK_STATUS_DscField34.setEnabled(false);
            WORK_STATUS_DscField34.setStyleName("Default.ReadonlyField");
			
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
		return N_EMPLOYEE.class;
	}

	
	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */

	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值
		//N_EMPLOYEE data = (N_EMPLOYEE) getDataObject();
	}
	
	
	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */

	public boolean checkDataObject() {
		//N_EMPLOYEE data = (N_EMPLOYEE) getDataObject();
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
        getUIDataObjectBinder().addBindMap("BIRTHDAY", BIRTHDAY_DscField1, BIRTHDAY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BIRTHPLACE", BIRTHPLACE_DscField2, BIRTHPLACE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("CITY", CITY_DscField3, CITY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("CODE", CODE_DscField4, CODE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("CONTACT_ADDRESS", CONTACT_ADDRESS_DscField5, CONTACT_ADDRESS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("CONTACT_PER", CONTACT_PER_DscField6, CONTACT_PER_CaptionLabel);
        getUIDataObjectBinder().addBindMap("CONTACT_TEL", CONTACT_TEL_DscField7, CONTACT_TEL_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_HIRED", DATE_HIRED_DscDateField1, DATE_HIRED_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_LAMLAI", DATE_LAMLAI_DscDateField2, DATE_LAMLAI_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DEPSN", DEPSN_DscField8, DEPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DEPSN_TEMP", DEPSN_TEMP_DscField9, DEPSN_TEMP_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DEPSN_TEMP1", DEPSN_TEMP1_DscField10, DEPSN_TEMP1_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EDUCATION", EDUCATION_DscField11, EDUCATION_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPCN", EMPCN_DscField12, EMPCN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPNO_IN_LIST", EMPNO_IN_LIST_DscField13, EMPNO_IN_LIST_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField14, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ETHNIC", ETHNIC_DscField15, ETHNIC_CaptionLabel);
        getUIDataObjectBinder().addBindMap("FNAME", FNAME_DscField16, FNAME_CaptionLabel);
        getUIDataObjectBinder().addBindMap("HAVE_CHILD", HAVE_CHILD_DscField17, HAVE_CHILD_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_BONUS_JOBS", ID_BONUS_JOBS_DscField18, ID_BONUS_JOBS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_JOB", ID_JOB_DscField19, ID_JOB_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_NO", ID_NO_DscField20, ID_NO_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_PLACE", ID_PLACE_DscField21, ID_PLACE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_POSS", ID_POSS_DscField22, ID_POSS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("LNAME", LNAME_DscField23, LNAME_CaptionLabel);
        getUIDataObjectBinder().addBindMap("MATERIAL_STATUS", MATERIAL_STATUS_DscField24, MATERIAL_STATUS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NGAYCAP_ID", NGAYCAP_ID_DscDateField3, NGAYCAP_ID_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField25, NOTE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE_CNV_MOI", NOTE_CNV_MOI_DscField26, NOTE_CNV_MOI_CaptionLabel);
        getUIDataObjectBinder().addBindMap("PERMANENT_ADDRESS", PERMANENT_ADDRESS_DscField27, PERMANENT_ADDRESS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("POSITION", POSITION_DscField28, POSITION_CaptionLabel);
        getUIDataObjectBinder().addBindMap("RELIGION", RELIGION_DscField29, RELIGION_CaptionLabel);
        getUIDataObjectBinder().addBindMap("SEX", SEX_DscField30, SEX_CaptionLabel);
        getUIDataObjectBinder().addBindMap("SHIFT", SHIFT_DscField31, SHIFT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("USER_MANAGE_ID", USER_MANAGE_ID_DscField33, USER_MANAGE_ID_CaptionLabel);
        getUIDataObjectBinder().addBindMap("WORK_STATUS", WORK_STATUS_DscField34, WORK_STATUS_CaptionLabel);
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
		//N_EMPLOYEE data = (N_EMPLOYEE) getDataObject();
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
		//N_EMPLOYEE data = (N_EMPLOYEE) getDataObject();
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
        rootLayout.setSize(6);
        add(rootLayout);
        
        EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_CaptionLabel.setText("N_EMPLOYEE.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField14 = new dsc.echo2app.component.DscField();
        EMPSN_DscField14.setId("EMPSN_DscField14");
        rootLayout.add(EMPSN_DscField14);
        
        
        FNAME_CaptionLabel = new nextapp.echo2.app.Label();
        FNAME_CaptionLabel.setText("N_EMPLOYEE.FNAME");
        rootLayout.add(FNAME_CaptionLabel);
        FNAME_DscField16 = new dsc.echo2app.component.DscField();
        FNAME_DscField16.setId("FNAME_DscField16");
        rootLayout.add(FNAME_DscField16);
        
        
        
        LNAME_CaptionLabel = new nextapp.echo2.app.Label();
        LNAME_CaptionLabel.setText("N_EMPLOYEE.LNAME");
        rootLayout.add(LNAME_CaptionLabel);
        LNAME_DscField23 = new dsc.echo2app.component.DscField();
        LNAME_DscField23.setId("LNAME_DscField23");
        rootLayout.add(LNAME_DscField23);
        
        
        DEPSN_CaptionLabel = new nextapp.echo2.app.Label();
        DEPSN_CaptionLabel.setText("N_EMPLOYEE.DEPSN");
        rootLayout.add(DEPSN_CaptionLabel);
        DEPSN_DscField8 = new dsc.echo2app.component.DscField();
        DEPSN_DscField8.setId("DEPSN_DscField8");
        rootLayout.add(DEPSN_DscField8);
        
        
        
        USER_MANAGE_ID_CaptionLabel = new nextapp.echo2.app.Label();
        USER_MANAGE_ID_CaptionLabel.setText("N_EMPLOYEE.USER_MANAGE_ID");
        rootLayout.add(USER_MANAGE_ID_CaptionLabel);
        USER_MANAGE_ID_DscField33 = new dsc.echo2app.component.DscField();
        USER_MANAGE_ID_DscField33.setId("USER_MANAGE_ID_DscField33");
        rootLayout.add(USER_MANAGE_ID_DscField33);
        
        WORK_STATUS_CaptionLabel = new nextapp.echo2.app.Label();
        WORK_STATUS_CaptionLabel.setText("N_EMPLOYEE.WORK_STATUS");
        rootLayout.add(WORK_STATUS_CaptionLabel);
        WORK_STATUS_DscField34 = new dsc.echo2app.component.DscField();
        WORK_STATUS_DscField34.setId("WORK_STATUS_DscField34");
        rootLayout.add(WORK_STATUS_DscField34);
        
        
        BIRTHDAY_CaptionLabel = new nextapp.echo2.app.Label();
        BIRTHDAY_CaptionLabel.setText("N_EMPLOYEE.BIRTHDAY");
//        rootLayout.add(BIRTHDAY_CaptionLabel);
        BIRTHDAY_DscField1 = new dsc.echo2app.component.DscField();
        BIRTHDAY_DscField1.setId("BIRTHDAY_DscField1");
//        rootLayout.add(BIRTHDAY_DscField1);
        BIRTHPLACE_CaptionLabel = new nextapp.echo2.app.Label();
        BIRTHPLACE_CaptionLabel.setText("N_EMPLOYEE.BIRTHPLACE");
//        rootLayout.add(BIRTHPLACE_CaptionLabel);
        BIRTHPLACE_DscField2 = new dsc.echo2app.component.DscField();
        BIRTHPLACE_DscField2.setId("BIRTHPLACE_DscField2");
//        rootLayout.add(BIRTHPLACE_DscField2);
        CITY_CaptionLabel = new nextapp.echo2.app.Label();
        CITY_CaptionLabel.setText("N_EMPLOYEE.CITY");
//        rootLayout.add(CITY_CaptionLabel);
        CITY_DscField3 = new dsc.echo2app.component.DscField();
        CITY_DscField3.setId("CITY_DscField3");
//        rootLayout.add(CITY_DscField3);
        CODE_CaptionLabel = new nextapp.echo2.app.Label();
        CODE_CaptionLabel.setText("N_EMPLOYEE.CODE");
//        rootLayout.add(CODE_CaptionLabel);
        CODE_DscField4 = new dsc.echo2app.component.DscField();
        CODE_DscField4.setId("CODE_DscField4");
//        rootLayout.add(CODE_DscField4);
        CONTACT_ADDRESS_CaptionLabel = new nextapp.echo2.app.Label();
        CONTACT_ADDRESS_CaptionLabel.setText("N_EMPLOYEE.CONTACT_ADDRESS");
//        rootLayout.add(CONTACT_ADDRESS_CaptionLabel);
        CONTACT_ADDRESS_DscField5 = new dsc.echo2app.component.DscField();
        CONTACT_ADDRESS_DscField5.setId("CONTACT_ADDRESS_DscField5");
//        rootLayout.add(CONTACT_ADDRESS_DscField5);
        CONTACT_PER_CaptionLabel = new nextapp.echo2.app.Label();
        CONTACT_PER_CaptionLabel.setText("N_EMPLOYEE.CONTACT_PER");
//        rootLayout.add(CONTACT_PER_CaptionLabel);
        CONTACT_PER_DscField6 = new dsc.echo2app.component.DscField();
        CONTACT_PER_DscField6.setId("CONTACT_PER_DscField6");
//        rootLayout.add(CONTACT_PER_DscField6);
        CONTACT_TEL_CaptionLabel = new nextapp.echo2.app.Label();
        CONTACT_TEL_CaptionLabel.setText("N_EMPLOYEE.CONTACT_TEL");
//        rootLayout.add(CONTACT_TEL_CaptionLabel);
        CONTACT_TEL_DscField7 = new dsc.echo2app.component.DscField();
        CONTACT_TEL_DscField7.setId("CONTACT_TEL_DscField7");
//        rootLayout.add(CONTACT_TEL_DscField7);
        DATE_HIRED_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_HIRED_CaptionLabel.setText("N_EMPLOYEE.DATE_HIRED");
//        rootLayout.add(DATE_HIRED_CaptionLabel);
        DATE_HIRED_DscDateField1 = new dsc.echo2app.component.DscDateField();
        DATE_HIRED_DscDateField1.setId("DATE_HIRED_DscDateField1");
//        rootLayout.add(DATE_HIRED_DscDateField1);
        DATE_LAMLAI_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_LAMLAI_CaptionLabel.setText("N_EMPLOYEE.DATE_LAMLAI");
//        rootLayout.add(DATE_LAMLAI_CaptionLabel);
        DATE_LAMLAI_DscDateField2 = new dsc.echo2app.component.DscDateField();
        DATE_LAMLAI_DscDateField2.setId("DATE_LAMLAI_DscDateField2");
//        rootLayout.add(DATE_LAMLAI_DscDateField2);
        
        DEPSN_TEMP_CaptionLabel = new nextapp.echo2.app.Label();
        DEPSN_TEMP_CaptionLabel.setText("N_EMPLOYEE.DEPSN_TEMP");
//        rootLayout.add(DEPSN_TEMP_CaptionLabel);
        DEPSN_TEMP_DscField9 = new dsc.echo2app.component.DscField();
        DEPSN_TEMP_DscField9.setId("DEPSN_TEMP_DscField9");
//        rootLayout.add(DEPSN_TEMP_DscField9);
        DEPSN_TEMP1_CaptionLabel = new nextapp.echo2.app.Label();
        DEPSN_TEMP1_CaptionLabel.setText("N_EMPLOYEE.DEPSN_TEMP1");
//        rootLayout.add(DEPSN_TEMP1_CaptionLabel);
        DEPSN_TEMP1_DscField10 = new dsc.echo2app.component.DscField();
        DEPSN_TEMP1_DscField10.setId("DEPSN_TEMP1_DscField10");
//        rootLayout.add(DEPSN_TEMP1_DscField10);
        EDUCATION_CaptionLabel = new nextapp.echo2.app.Label();
        EDUCATION_CaptionLabel.setText("N_EMPLOYEE.EDUCATION");
//        rootLayout.add(EDUCATION_CaptionLabel);
        EDUCATION_DscField11 = new dsc.echo2app.component.DscField();
        EDUCATION_DscField11.setId("EDUCATION_DscField11");
//        rootLayout.add(EDUCATION_DscField11);
        EMPCN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPCN_CaptionLabel.setText("N_EMPLOYEE.EMPCN");
//        rootLayout.add(EMPCN_CaptionLabel);
        EMPCN_DscField12 = new dsc.echo2app.component.DscField();
        EMPCN_DscField12.setId("EMPCN_DscField12");
//        rootLayout.add(EMPCN_DscField12);
        EMPNO_IN_LIST_CaptionLabel = new nextapp.echo2.app.Label();
        EMPNO_IN_LIST_CaptionLabel.setText("N_EMPLOYEE.EMPNO_IN_LIST");
//        rootLayout.add(EMPNO_IN_LIST_CaptionLabel);
        EMPNO_IN_LIST_DscField13 = new dsc.echo2app.component.DscField();
        EMPNO_IN_LIST_DscField13.setId("EMPNO_IN_LIST_DscField13");
//        rootLayout.add(EMPNO_IN_LIST_DscField13);
        
        ETHNIC_CaptionLabel = new nextapp.echo2.app.Label();
        ETHNIC_CaptionLabel.setText("N_EMPLOYEE.ETHNIC");
//        rootLayout.add(ETHNIC_CaptionLabel);
        ETHNIC_DscField15 = new dsc.echo2app.component.DscField();
        ETHNIC_DscField15.setId("ETHNIC_DscField15");
//        rootLayout.add(ETHNIC_DscField15);
        
       
        HAVE_CHILD_CaptionLabel = new nextapp.echo2.app.Label();
        HAVE_CHILD_CaptionLabel.setText("N_EMPLOYEE.HAVE_CHILD");
//        rootLayout.add(HAVE_CHILD_CaptionLabel);
        HAVE_CHILD_DscField17 = new dsc.echo2app.component.DscField();
        HAVE_CHILD_DscField17.setId("HAVE_CHILD_DscField17");
//        rootLayout.add(HAVE_CHILD_DscField17);
        ID_BONUS_JOBS_CaptionLabel = new nextapp.echo2.app.Label();
        ID_BONUS_JOBS_CaptionLabel.setText("N_EMPLOYEE.ID_BONUS_JOBS");
//        rootLayout.add(ID_BONUS_JOBS_CaptionLabel);
        ID_BONUS_JOBS_DscField18 = new dsc.echo2app.component.DscField();
        ID_BONUS_JOBS_DscField18.setId("ID_BONUS_JOBS_DscField18");
//        rootLayout.add(ID_BONUS_JOBS_DscField18);
        ID_JOB_CaptionLabel = new nextapp.echo2.app.Label();
        ID_JOB_CaptionLabel.setText("N_EMPLOYEE.ID_JOB");
//        rootLayout.add(ID_JOB_CaptionLabel);
        ID_JOB_DscField19 = new dsc.echo2app.component.DscField();
        ID_JOB_DscField19.setId("ID_JOB_DscField19");
//        rootLayout.add(ID_JOB_DscField19);
        ID_NO_CaptionLabel = new nextapp.echo2.app.Label();
        ID_NO_CaptionLabel.setText("N_EMPLOYEE.ID_NO");
//        rootLayout.add(ID_NO_CaptionLabel);
        ID_NO_DscField20 = new dsc.echo2app.component.DscField();
        ID_NO_DscField20.setId("ID_NO_DscField20");
//        rootLayout.add(ID_NO_DscField20);
        ID_PLACE_CaptionLabel = new nextapp.echo2.app.Label();
        ID_PLACE_CaptionLabel.setText("N_EMPLOYEE.ID_PLACE");
//        rootLayout.add(ID_PLACE_CaptionLabel);
        ID_PLACE_DscField21 = new dsc.echo2app.component.DscField();
        ID_PLACE_DscField21.setId("ID_PLACE_DscField21");
//        rootLayout.add(ID_PLACE_DscField21);
        ID_POSS_CaptionLabel = new nextapp.echo2.app.Label();
        ID_POSS_CaptionLabel.setText("N_EMPLOYEE.ID_POSS");
//        rootLayout.add(ID_POSS_CaptionLabel);
        ID_POSS_DscField22 = new dsc.echo2app.component.DscField();
        ID_POSS_DscField22.setId("ID_POSS_DscField22");
//        rootLayout.add(ID_POSS_DscField22);
        
        MATERIAL_STATUS_CaptionLabel = new nextapp.echo2.app.Label();
        MATERIAL_STATUS_CaptionLabel.setText("N_EMPLOYEE.MATERIAL_STATUS");
//        rootLayout.add(MATERIAL_STATUS_CaptionLabel);
        MATERIAL_STATUS_DscField24 = new dsc.echo2app.component.DscField();
        MATERIAL_STATUS_DscField24.setId("MATERIAL_STATUS_DscField24");
//        rootLayout.add(MATERIAL_STATUS_DscField24);
        NGAYCAP_ID_CaptionLabel = new nextapp.echo2.app.Label();
        NGAYCAP_ID_CaptionLabel.setText("N_EMPLOYEE.NGAYCAP_ID");
//        rootLayout.add(NGAYCAP_ID_CaptionLabel);
        NGAYCAP_ID_DscDateField3 = new dsc.echo2app.component.DscDateField();
        NGAYCAP_ID_DscDateField3.setId("NGAYCAP_ID_DscDateField3");
//        rootLayout.add(NGAYCAP_ID_DscDateField3);
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_EMPLOYEE.NOTE");
//        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField25 = new dsc.echo2app.component.DscField();
        NOTE_DscField25.setId("NOTE_DscField25");
//        rootLayout.add(NOTE_DscField25);
        NOTE_CNV_MOI_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CNV_MOI_CaptionLabel.setText("N_EMPLOYEE.NOTE_CNV_MOI");
//        rootLayout.add(NOTE_CNV_MOI_CaptionLabel);
        NOTE_CNV_MOI_DscField26 = new dsc.echo2app.component.DscField();
        NOTE_CNV_MOI_DscField26.setId("NOTE_CNV_MOI_DscField26");
//        rootLayout.add(NOTE_CNV_MOI_DscField26);
        PERMANENT_ADDRESS_CaptionLabel = new nextapp.echo2.app.Label();
        PERMANENT_ADDRESS_CaptionLabel.setText("N_EMPLOYEE.PERMANENT_ADDRESS");
//        rootLayout.add(PERMANENT_ADDRESS_CaptionLabel);
        PERMANENT_ADDRESS_DscField27 = new dsc.echo2app.component.DscField();
        PERMANENT_ADDRESS_DscField27.setId("PERMANENT_ADDRESS_DscField27");
//        rootLayout.add(PERMANENT_ADDRESS_DscField27);
        POSITION_CaptionLabel = new nextapp.echo2.app.Label();
        POSITION_CaptionLabel.setText("N_EMPLOYEE.POSITION");
//        rootLayout.add(POSITION_CaptionLabel);
        POSITION_DscField28 = new dsc.echo2app.component.DscField();
        POSITION_DscField28.setId("POSITION_DscField28");
//        rootLayout.add(POSITION_DscField28);
        RELIGION_CaptionLabel = new nextapp.echo2.app.Label();
        RELIGION_CaptionLabel.setText("N_EMPLOYEE.RELIGION");
//        rootLayout.add(RELIGION_CaptionLabel);
        RELIGION_DscField29 = new dsc.echo2app.component.DscField();
        RELIGION_DscField29.setId("RELIGION_DscField29");
//        rootLayout.add(RELIGION_DscField29);
        SEX_CaptionLabel = new nextapp.echo2.app.Label();
        SEX_CaptionLabel.setText("N_EMPLOYEE.SEX");
//        rootLayout.add(SEX_CaptionLabel);
        SEX_DscField30 = new dsc.echo2app.component.DscField();
        SEX_DscField30.setId("SEX_DscField30");
//        rootLayout.add(SEX_DscField30);
        SHIFT_CaptionLabel = new nextapp.echo2.app.Label();
        SHIFT_CaptionLabel.setText("N_EMPLOYEE.SHIFT");
//        rootLayout.add(SHIFT_CaptionLabel);
        SHIFT_DscField31 = new dsc.echo2app.component.DscField();
        SHIFT_DscField31.setId("SHIFT_DscField31");
//        rootLayout.add(SHIFT_DscField31);
        
	}

}
