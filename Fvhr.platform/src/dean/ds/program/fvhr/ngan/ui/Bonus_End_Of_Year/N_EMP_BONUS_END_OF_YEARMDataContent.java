package ds.program.fvhr.ngan.ui.Bonus_End_Of_Year;

import java.beans.PropertyEditor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.SelectField;
import dsc.echo2app.Application;
import dsc.echo2app.component.binder.ListBaseBinder;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import fv.util.MappingPropertyUtils;
import ds.program.fvhr.domain.N_EMP_BONUS_END_OF_YEAR;
import ds.program.fvhr.son.ui.ObjUtility;

public class N_EMP_BONUS_END_OF_YEARMDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label BONUS_M13_CaptionLabel;
    private dsc.echo2app.component.DscField BONUS_M13_DscField2;
    private nextapp.echo2.app.Label DATE_EFFECT_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_EFFECT_DscDateField1;
    private nextapp.echo2.app.Label STATUS_CaptionLabel;
  //  private dsc.echo2app.component.DscField STATUS_DscField3;
    private SelectField sfStatus;
    private nextapp.echo2.app.Label UP_DATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField UP_DATE_DscDateField2;
    private nextapp.echo2.app.Label UP_USER_CaptionLabel;
    private dsc.echo2app.component.DscField UP_USER_DscField4;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField5;
    private nextapp.echo2.app.Label YEAR_CaptionLabel;
    private SelectField YEAR_DscField5;
    
    
    SimpleDateFormat _sf = new SimpleDateFormat(ObjUtility.DATE_FORMAT_STR);

	/**
	 * Creates a new <code>N_EMP_BONUS_END_OF_YEARMDataContent</code>.
	 */
	public N_EMP_BONUS_END_OF_YEARMDataContent() {
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
			UP_DATE_DscDateField2.setEnabled(false);
			UP_USER_DscField4.setEnabled(false);
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
		return N_EMP_BONUS_END_OF_YEAR.class;
	}

	
	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */

	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值
		//N_EMP_BONUS_END_OF_YEAR data = (N_EMP_BONUS_END_OF_YEAR) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */

	public boolean checkDataObject() {
		//N_EMP_BONUS_END_OF_YEAR data = (N_EMP_BONUS_END_OF_YEAR) getDataObject();
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
	protected boolean autoPrimaryKeyValue() {
		//使用者可以在以下區域撰寫產生新序號的程式碼並將新序號放入dataObject
		//N_EMP_BONUS_END_OF_YEAR data = (N_EMP_BONUS_END_OF_YEAR) getDataObject();
		//Connection conn = Application.getApp().getConnection();
		//Map keys = new HashMap();
		//Map filters = new HashMap();
		//String newValue = KeyNumberGenerator.generateNumber(conn,
		//		"$Y{2}$M{2}$D{2}$S{4}", 
		//		1,
		//		"N_EMP_BONUS_END_OF_YEAR", "{ID_FIELD}", 
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
        getUIDataObjectBinder().registerCustomEditor(N_EMP_BONUS_END_OF_YEAR.class,"STATUS",statusEditor());
        this.getUIDataObjectBinder().registerCustomEditor(N_EMP_BONUS_END_OF_YEAR.class,"DATE_EFFECT",PropertyEditors.createDateEditor("dd/MM/yyyy"));
        this.getUIDataObjectBinder().registerCustomEditor(N_EMP_BONUS_END_OF_YEAR.class,"UP_DATE",PropertyEditors.createDateEditor("dd/MM/yyyy"));
        this.getUIDataObjectBinder().registerCustomEditor(N_EMP_BONUS_END_OF_YEAR.class,"YEAR",MappingPropertyUtils.getStringYearEditor(1, false));

	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BONUS_M13", BONUS_M13_DscField2, BONUS_M13_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_EFFECT", DATE_EFFECT_DscDateField1, DATE_EFFECT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("STATUS", sfStatus, STATUS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("UP_DATE", UP_DATE_DscDateField2, UP_DATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("UP_USER", UP_USER_DscField4, UP_USER_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField5, NOTE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("YEAR", YEAR_DscField5, YEAR_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	
	private MappingPropertyEditor statusEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		e.put("Cộng chung vào tháng tính lương", "Y");
		e.put("Phát riêng ở ngoài ", "N");
		return e;
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
		N_EMP_BONUS_END_OF_YEAR data = (N_EMP_BONUS_END_OF_YEAR) getDataObject();
		String user_up = Application.getApp().getLoginInfo().getUserID();
		
		Date date = null;
		try {
			date = _sf.parse(_sf.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		data.setUP_USER(user_up);
		data.setUP_DATE(date);
		data.setDATE_EFFECT(ObjUtility.MONTH_NOW("01",date));
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */

	public void beforeSaveToDataObjectSet() {
		N_EMP_BONUS_END_OF_YEAR data = (N_EMP_BONUS_END_OF_YEAR) getDataObject();
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
		    autoPrimaryKeyValue();
		}
		data.setDATE_EFFECT(ObjUtility.MONTH_NOW("01", DATE_EFFECT_DscDateField1.getDisplayedDate().getTime()));
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
        EMPSN_CaptionLabel.setText("N_EMP_BONUS_END_OF_YEAR.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        
        EMPSN_DscField1 = new dsc.echo2app.component.DscField();
        EMPSN_DscField1.setId("EMPSN_DscField1");
        rootLayout.add(EMPSN_DscField1);
        
        DATE_EFFECT_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_EFFECT_CaptionLabel.setText("N_EMP_BONUS_END_OF_YEAR.DATE_EFFECT");
        rootLayout.add(DATE_EFFECT_CaptionLabel);
        
        DATE_EFFECT_DscDateField1 = new dsc.echo2app.component.DscDateField();
        DATE_EFFECT_DscDateField1.setId("DATE_EFFECT_DscDateField1");
        DATE_EFFECT_DscDateField1.setDateFormat(ObjUtility.Get_format_date());
        DATE_EFFECT_DscDateField1.getDateChooser().setLocale(Locale.ENGLISH);
        rootLayout.add(DATE_EFFECT_DscDateField1);
        
        
        BONUS_M13_CaptionLabel = new nextapp.echo2.app.Label();
        BONUS_M13_CaptionLabel.setText("N_EMP_BONUS_END_OF_YEAR.BONUS_M13");
        rootLayout.add(BONUS_M13_CaptionLabel);
        
        BONUS_M13_DscField2 = new dsc.echo2app.component.DscField();
        BONUS_M13_DscField2.setId("BONUS_M13_DscField2");
        rootLayout.add(BONUS_M13_DscField2);
        
        UP_DATE_CaptionLabel = new nextapp.echo2.app.Label();
        UP_DATE_CaptionLabel.setText("N_EMP_BONUS_END_OF_YEAR.UP_DATE");
        rootLayout.add(UP_DATE_CaptionLabel);
        
        UP_DATE_DscDateField2 = new dsc.echo2app.component.DscDateField();
        UP_DATE_DscDateField2.setId("UP_DATE_DscDateField2");
        UP_DATE_DscDateField2.setDateFormat(ObjUtility.Get_format_date());
        UP_DATE_DscDateField2.getDateChooser().setLocale(Locale.ENGLISH);
        rootLayout.add(UP_DATE_DscDateField2);
        
        
        YEAR_CaptionLabel = new nextapp.echo2.app.Label();
        YEAR_CaptionLabel.setText("N_EMP_RETAIN_SALARY.YEAR");
        rootLayout.add(YEAR_CaptionLabel);
        
        YEAR_DscField5 = new SelectField();
        YEAR_DscField5.setId("YEAR_DscField5");
        YEAR_DscField5.setWidth(new Extent(145));
        rootLayout.add(YEAR_DscField5);
        
        UP_USER_CaptionLabel = new nextapp.echo2.app.Label();
        UP_USER_CaptionLabel.setText("N_EMP_BONUS_END_OF_YEAR.UP_USER");
        rootLayout.add(UP_USER_CaptionLabel);
        UP_USER_DscField4 = new dsc.echo2app.component.DscField();
        UP_USER_DscField4.setId("UP_USER_DscField4");
        rootLayout.add(UP_USER_DscField4);
       
        STATUS_CaptionLabel = new nextapp.echo2.app.Label();
        STATUS_CaptionLabel.setText("N_EMP_BONUS_END_OF_YEAR.STATUS");
        rootLayout.add(STATUS_CaptionLabel);
        
        sfStatus = new SelectField();
        sfStatus.setWidth(new Extent(145));
        rootLayout.add(sfStatus);
        
        /*STATUS_DscField3 = new dsc.echo2app.component.DscField();
        STATUS_DscField3.setId("STATUS_DscField3");
        rootLayout.add(STATUS_DscField3);*/
                
       
       
        
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_EMP_BONUS_END_OF_YEAR.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField5 = new dsc.echo2app.component.DscField();
        NOTE_DscField5.setId("NOTE_DscField5");
        rootLayout.add(NOTE_DscField5);
        
	}

}
