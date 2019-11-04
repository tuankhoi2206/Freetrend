package ds.program.fvhr.ui.an;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import ds.program.fvhr.domain.N_LABOUR;
import dsc.echo2app.Application;
import dsc.echo2app.program.BrowserContent;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.PropertyEditors;

import fv.util.VniEditor;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscDateField;

public class nLabourMDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label CHECKED_CaptionLabel;
    private dsc.echo2app.component.DscField CHECKED_DscField1;
    private nextapp.echo2.app.Label CLOCK_CaptionLabel;
    private dsc.echo2app.component.DscField CLOCK_DscField2;
    private nextapp.echo2.app.Label DATE_S_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_S_DscDateField1;
    private nextapp.echo2.app.Label DEPT_CaptionLabel;
    private dsc.echo2app.component.DscField DEPT_DscField3;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField4;
    private nextapp.echo2.app.Label EXPIRE_CaptionLabel;
    private dsc.echo2app.component.DscDateField EXPIRE_DscDateField2;
    private nextapp.echo2.app.Label ID_LABOUR_CaptionLabel;
    private dsc.echo2app.component.DscField ID_LABOUR_DscField5;
    private nextapp.echo2.app.Label JOBS_CaptionLabel;
    private dsc.echo2app.component.DscField JOBS_DscField6;
    private nextapp.echo2.app.Label LIMIT_CaptionLabel;
    private dsc.echo2app.component.DscField LIMIT_DscField7;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField8;
    private nextapp.echo2.app.Label POSS_CaptionLabel;
    private dsc.echo2app.component.DscField POSS_DscField9;
    private nextapp.echo2.app.Label SALARY_CaptionLabel;
    private dsc.echo2app.component.DscField SALARY_DscField10;
    private nextapp.echo2.app.Label TIMES_CaptionLabel;
    private dsc.echo2app.component.DscField TIMES_DscField11;
    
    private ResourceBundle bundle;

	/**
	 * Creates a new <code>nLabourMDataContent</code>.
	 */
	public nLabourMDataContent() {
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
		return N_LABOUR.class;
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
		//N_LABOUR data = (N_LABOUR) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		//N_LABOUR data = (N_LABOUR) getDataObject();
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
        //dsc.echo2app.propertyeditors.MappingPropertyEditor editor = null;
		getUIDataObjectBinder().registerCustomEditor(N_LABOUR.class, "DATE_S", PropertyEditors.createDateEditor(bundle.getString("LANG_DATEFORMAT")));
		getUIDataObjectBinder().registerCustomEditor(N_LABOUR.class, "EXPIRE", PropertyEditors.createDateEditor(bundle.getString("LANG_DATEFORMAT")));
		getUIDataObjectBinder().registerCustomEditor(N_LABOUR.class, "DEPT", new VniEditor());
		getUIDataObjectBinder().registerCustomEditor(N_LABOUR.class, "POSS", new VniEditor());
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //getUIDataObjectBinder().addBindMap("CHECKED", CHECKED_DscField1, CHECKED_CaptionLabel);
        getUIDataObjectBinder().addBindMap("CLOCK", CLOCK_DscField2, CLOCK_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_S", DATE_S_DscDateField1, DATE_S_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DEPT", DEPT_DscField3, DEPT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField4, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EXPIRE", EXPIRE_DscDateField2, EXPIRE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_LABOUR", ID_LABOUR_DscField5, ID_LABOUR_CaptionLabel);
        getUIDataObjectBinder().addBindMap("JOBS", JOBS_DscField6, JOBS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("LIMIT", LIMIT_DscField7, LIMIT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField8, NOTE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("POSS", POSS_DscField9, POSS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("SALARY", SALARY_DscField10, SALARY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TIMES", TIMES_DscField11, TIMES_CaptionLabel);
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
		N_LABOUR data = (N_LABOUR) getDataObject();
		data.setCLOCK("1");
		
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {
		//N_LABOUR data = (N_LABOUR) getDataObject();
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
		    autoPrimaryKeyValue();
		}
		//使用者可在以下區域做儲存資料物件至DataObjectSet之前處理
		//<儲存前處理>
	}
	public String getEMPSN(String empsn){
		return EMPSN_DscField4.getText().toString();
	}
	
	
	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		bundle = ResourceBundle.getBundle("resource.localization.UICaption", Application.getActive().getLocale());
		this.setBackground(new Color(0xbbddff));
		rootLayout = new Grid();
		rootLayout.setWidth(new Extent(100, Extent.PERCENT));
		rootLayout.setSize(6);
		add(rootLayout);
		/*CHECKED_CaptionLabel = new Label();
		CHECKED_CaptionLabel.setText("N_LABOUR.CHECKED");
		rootLayout.add(CHECKED_CaptionLabel);
		CHECKED_DscField1 = new DscField();
		CHECKED_DscField1.setId("CHECKED_DscField1");
		rootLayout.add(CHECKED_DscField1);*/
		EMPSN_CaptionLabel = new Label();
		EMPSN_CaptionLabel.setText("N_LABOUR.EMPSN");
		rootLayout.add(EMPSN_CaptionLabel);
		EMPSN_DscField4 = new DscField();
		EMPSN_DscField4.setId("EMPSN_DscField4");
		EMPSN_DscField4.setMaximumLength(8);
		rootLayout.add(EMPSN_DscField4);
		ID_LABOUR_CaptionLabel = new Label();
		ID_LABOUR_CaptionLabel.setText("N_LABOUR.ID_LABOUR");
		rootLayout.add(ID_LABOUR_CaptionLabel);
		ID_LABOUR_DscField5 = new DscField();
		ID_LABOUR_DscField5.setId("ID_LABOUR_DscField5");
		ID_LABOUR_DscField5.setMaximumLength(10);
		rootLayout.add(ID_LABOUR_DscField5);
		DEPT_CaptionLabel = new Label();
		DEPT_CaptionLabel.setText("N_LABOUR.DEPT");
		rootLayout.add(DEPT_CaptionLabel);
		DEPT_DscField3 = new DscField();
		DEPT_DscField3.setId("DEPT_DscField3");
		rootLayout.add(DEPT_DscField3);
		
		DATE_S_CaptionLabel = new Label();
		DATE_S_CaptionLabel.setText("N_LABOUR.DATE_S");
		rootLayout.add(DATE_S_CaptionLabel);
		DATE_S_DscDateField1 = new DscDateField();
		DATE_S_DscDateField1.setId("DATE_S_DscDateField1");
		DATE_S_DscDateField1.setDateFormat(new SimpleDateFormat(bundle.getString("LANG_DATEFORMAT")));
		DATE_S_DscDateField1.getDateChooser().setLocale(new Locale("en"));
		rootLayout.add(DATE_S_DscDateField1);
		LIMIT_CaptionLabel = new Label();
		LIMIT_CaptionLabel.setText("N_LABOUR.LIMIT");
		rootLayout.add(LIMIT_CaptionLabel);
		LIMIT_DscField7 = new DscField();
		LIMIT_DscField7.setId("LIMIT_DscField7");
		rootLayout.add(LIMIT_DscField7);
		
		CLOCK_CaptionLabel = new Label();
		CLOCK_CaptionLabel.setText("N_LABOUR.CLOCK");
		rootLayout.add(CLOCK_CaptionLabel);
		CLOCK_DscField2 = new DscField();
		CLOCK_DscField2.setId("CLOCK_DscField2");
		rootLayout.add(CLOCK_DscField2);
		
		
		EXPIRE_CaptionLabel = new Label();
		EXPIRE_CaptionLabel.setText("N_LABOUR.EXPIRE");
		rootLayout.add(EXPIRE_CaptionLabel);
		EXPIRE_DscDateField2 = new DscDateField();
		EXPIRE_DscDateField2.setId("EXPIRE_DscDateField2");
		EXPIRE_DscDateField2.setDateFormat(new SimpleDateFormat(bundle.getString("LANG_DATEFORMAT")));
		EXPIRE_DscDateField2.getDateChooser().setLocale(new Locale("en"));
		rootLayout.add(EXPIRE_DscDateField2);
		
		
		SALARY_CaptionLabel = new Label();
		SALARY_CaptionLabel.setText("N_LABOUR.SALARY");
		rootLayout.add(SALARY_CaptionLabel);
		SALARY_DscField10 = new DscField();
		SALARY_DscField10.setId("SALARY_DscField10");
		rootLayout.add(SALARY_DscField10);

		JOBS_CaptionLabel = new Label();
		JOBS_CaptionLabel.setText("N_LABOUR.JOBS");
		rootLayout.add(JOBS_CaptionLabel);
		JOBS_DscField6 = new DscField();
		JOBS_DscField6.setId("JOBS_DscField6");
		rootLayout.add(JOBS_DscField6);
		TIMES_CaptionLabel = new Label();
		TIMES_CaptionLabel.setText("N_LABOUR.TIMES");
		rootLayout.add(TIMES_CaptionLabel);
		TIMES_DscField11 = new DscField();
		TIMES_DscField11.setId("TIMES_DscField11");
		rootLayout.add(TIMES_DscField11);
		POSS_CaptionLabel = new Label();
		POSS_CaptionLabel.setText("N_LABOUR.POSS");
		rootLayout.add(POSS_CaptionLabel);
		POSS_DscField9 = new DscField();
		POSS_DscField9.setId("POSS_DscField9");
		rootLayout.add(POSS_DscField9);
		NOTE_CaptionLabel = new Label();
		NOTE_CaptionLabel.setText("N_LABOUR.NOTE");
		rootLayout.add(NOTE_CaptionLabel);
		NOTE_DscField8 = new DscField();
		NOTE_DscField8.setId("NOTE_DscField8");
		rootLayout.add(NOTE_DscField8);
		
		
		
	}

}
