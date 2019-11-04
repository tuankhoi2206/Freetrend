package ds.program.fvhr.ngan.ui.bonus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import ds.program.fvhr.domain.N_EMP_BONUS;
import ds.program.fvhr.domain.N_EMP_JOB_DETAIL;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.PropertyEditors;

public class N_Emp_Bonus_01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label DATE_EFFECT_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_EFFECT_DscDateField1;
    private nextapp.echo2.app.Label BONUS1_CaptionLabel;
    private dsc.echo2app.component.DscField BONUS1_DscField2;
    private nextapp.echo2.app.Label BONUS5_CaptionLabel;
    private dsc.echo2app.component.DscField BONUS5_DscField3;
    private nextapp.echo2.app.Label BONUS8_CaptionLabel;
    private dsc.echo2app.component.DscField BONUS8_DscField4;
    private nextapp.echo2.app.Label USER_UP_CaptionLabel;
    private dsc.echo2app.component.DscField USER_UP_DscField5;
    private nextapp.echo2.app.Label DATE_UP_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_UP_DscDateField2;
    private nextapp.echo2.app.Label DEPSN_CaptionLabel;
    private dsc.echo2app.component.DscField DEPSN_DscField1;

	/**
	 * Creates a new <code>N_Emp_Bonus_01MDataContent</code>.
	 */
    
    SimpleDateFormat _sf = new SimpleDateFormat(ObjUtility.DATE_FORMAT_STR);
    public boolean check_date_effect;
	public N_Emp_Bonus_01MDataContent() {
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
			
			USER_UP_DscField5.setStyleName("Default.ReadonlyField");
			USER_UP_DscField5.setEnabled(false);
			DATE_UP_DscDateField2.setStyleName("Default.ReadonlyField");
			DATE_UP_DscDateField2.setEnabled(false);
			
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
		return N_EMP_BONUS.class;
	}

	
	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */

	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值
		//N_EMP_BONUS data = (N_EMP_BONUS) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */

	public boolean checkDataObject() {
		//N_EMP_BONUS data = (N_EMP_BONUS) getDataObject();
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
        this.getUIDataObjectBinder().registerCustomEditor(N_EMP_BONUS.class,"DATE_UP",PropertyEditors.createDateEditor("dd/MM/yyyy"));
        this.getUIDataObjectBinder().registerCustomEditor(N_EMP_BONUS.class,"DATE_EFFECT",PropertyEditors.createDateEditor("dd/MM/yyyy"));
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_EFFECT", DATE_EFFECT_DscDateField1, DATE_EFFECT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BONUS1", BONUS1_DscField2, BONUS1_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BONUS5", BONUS5_DscField3, BONUS5_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BONUS8", BONUS8_DscField4, BONUS8_CaptionLabel);
        getUIDataObjectBinder().addBindMap("USER_UP", USER_UP_DscField5, USER_UP_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_UP", DATE_UP_DscDateField2, DATE_UP_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DEPSN", DEPSN_DscField1, DEPSN_CaptionLabel);
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
		N_EMP_BONUS data = (N_EMP_BONUS) getDataObject();
		String 	user_up = Application.getApp().getLoginInfo().getUserID();
		Date	date = null;
		
		try {
			date 	= _sf.parse(_sf.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		data.setUSER_UP(user_up);
		data.setDATE_EFFECT(ObjUtility.MONTH_NOW("01", date));
		data.setDATE_UP(date);
	
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */

	public void beforeSaveToDataObjectSet() {
		N_EMP_BONUS data = (N_EMP_BONUS) getDataObject();
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
		    autoPrimaryKeyValue();
		}
		
		String 	user_up = Application.getApp().getLoginInfo().getUserID();
		Date	date = null;
		
		try {
			date 	= _sf.parse(_sf.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		data.setUSER_UP(user_up);
		data.setDATE_UP(date);
		
		// de dam bao tinh chat moi thang chi co 1 row bonus
		// kiem tra ngay hieu luc nhap vao voi nhung ngay hieu luc truoc do
		// neu cung thang hoac be hon thi khong cho nhap;
		// ?????????????/
		
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
        EMPSN_CaptionLabel.setText("N_EMP_BONUS.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField1 = new dsc.echo2app.component.DscField();
        EMPSN_DscField1.setId("EMPSN_DscField1");
        rootLayout.add(EMPSN_DscField1);
        
        
        DATE_EFFECT_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_EFFECT_CaptionLabel.setText("N_EMP_BONUS.DATE_EFFECT");
        rootLayout.add(DATE_EFFECT_CaptionLabel);
        DATE_EFFECT_DscDateField1 = new dsc.echo2app.component.DscDateField();
        DATE_EFFECT_DscDateField1.setId("DATE_EFFECT_DscDateField1");
        DATE_EFFECT_DscDateField1.setDateFormat(_sf);
        DATE_EFFECT_DscDateField1.getDateChooser().setLocale(Locale.ENGLISH);
        rootLayout.add(DATE_EFFECT_DscDateField1);
        
        DEPSN_CaptionLabel = new nextapp.echo2.app.Label();
        DEPSN_CaptionLabel.setText("N_EMP_BONUS.DEPSN");
        rootLayout.add(DEPSN_CaptionLabel);
        DEPSN_DscField1 = new dsc.echo2app.component.DscField();
        DEPSN_DscField1.setId("DEPSN_DscField1");
        rootLayout.add(DEPSN_DscField1);
        
        
        BONUS1_CaptionLabel = new nextapp.echo2.app.Label();
        BONUS1_CaptionLabel.setText("N_EMP_BONUS.BONUS1");
        rootLayout.add(BONUS1_CaptionLabel);
        BONUS1_DscField2 = new dsc.echo2app.component.DscField();
        BONUS1_DscField2.setId("BONUS1_DscField2");
        rootLayout.add(BONUS1_DscField2);
        
        USER_UP_CaptionLabel = new nextapp.echo2.app.Label();
        USER_UP_CaptionLabel.setText("N_EMP_BONUS.USER_UP");
        rootLayout.add(USER_UP_CaptionLabel);
        USER_UP_DscField5 = new dsc.echo2app.component.DscField();
        USER_UP_DscField5.setId("USER_UP_DscField5");
        rootLayout.add(USER_UP_DscField5);
        
        BONUS5_CaptionLabel = new nextapp.echo2.app.Label();
        BONUS5_CaptionLabel.setText("N_EMP_BONUS.BONUS5");
        rootLayout.add(BONUS5_CaptionLabel);
        BONUS5_DscField3 = new dsc.echo2app.component.DscField();
        BONUS5_DscField3.setId("BONUS5_DscField3");
        rootLayout.add(BONUS5_DscField3);
        
        DATE_UP_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_UP_CaptionLabel.setText("N_EMP_BONUS.DATE_UP");
        rootLayout.add(DATE_UP_CaptionLabel);
        DATE_UP_DscDateField2 = new dsc.echo2app.component.DscDateField();
        DATE_UP_DscDateField2.setId("DATE_UP_DscDateField2");
        DATE_UP_DscDateField2.setDateFormat(_sf);
        DATE_UP_DscDateField2.getDateChooser().setLocale(Locale.ENGLISH);
        rootLayout.add(DATE_UP_DscDateField2);
        
        BONUS8_CaptionLabel = new nextapp.echo2.app.Label();
        BONUS8_CaptionLabel.setText("N_EMP_BONUS.BONUS8");
        rootLayout.add(BONUS8_CaptionLabel);
        BONUS8_DscField4 = new dsc.echo2app.component.DscField();
        BONUS8_DscField4.setId("BONUS8_DscField4");
        rootLayout.add(BONUS8_DscField4);
        
        
        
        
	}

}
