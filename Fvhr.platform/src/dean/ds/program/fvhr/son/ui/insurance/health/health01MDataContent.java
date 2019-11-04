package ds.program.fvhr.son.ui.insurance.health;

import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.list.ListModel;
import fv.util.Vni2Uni;
import fv.util.VniEditor;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_HEALTH;
import ds.program.fvhr.domain.N_HOSPITAL;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;

public class health01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label ID_KEY_CaptionLabel;
    private dsc.echo2app.component.DscField ID_KEY_DscField1;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField2;
    private nextapp.echo2.app.Label CLOCK_CaptionLabel;
    private dsc.echo2app.component.DscField CLOCK_DscField3;
    private nextapp.echo2.app.Label DATES_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATES_DscDateField1;
    private nextapp.echo2.app.Label EXPIRE_CaptionLabel;
    private dsc.echo2app.component.DscDateField EXPIRE_DscDateField2;
    private nextapp.echo2.app.Label DATE_BHYT_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_BHYT_DscDateField3;
    private nextapp.echo2.app.Label ID_HEALTH_CaptionLabel;
    private dsc.echo2app.component.DscField ID_HEALTH_DscField4;
    private nextapp.echo2.app.Label SALARY_CaptionLabel;
    private dsc.echo2app.component.DscField SALARY_DscField5;
    private nextapp.echo2.app.Label ID_HOS_CaptionLabel;
    private SelectField ID_HOS_DscField6;
    private nextapp.echo2.app.Label ID_PRO_CaptionLabel;
    private SelectField ID_PRO_DscField7;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField8;
    Map<String, N_HOSPITAL> map;
	private Label lblName;
	private Component row;
    
	/**
	 * Creates a new <code>health01MDataContent</code>.
	 */
	public health01MDataContent() {
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
            ID_KEY_DscField1.setEnabled(false);
            ID_KEY_DscField1.setStyleName("Default.ReadonlyField");
            //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            //----------------------------------------------------------------------------------
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				//新增時，設定哪些元件Enable/Disable
			}
			if(this.getProgram().getDataMode()==IProgram.DATAMODE_EDIT)
			{
				ID_KEY_DscField1.setEnabled(false);
				EMPSN_DscField2.setEnabled(false);
				//CLOCK_DscField3.setEnabled(false);
				//DATES_DscDateField1.setEnabled(false);
				///EXPIRE_DscDateField2.setEnabled(false);
				//DATE_BHYT_DscDateField3.setEnabled(false);
				//SALARY_DscField5.setEnabled(false);
				//ID_HOS_DscField6.setEnabled(false);
				//ID_PRO_DscField7.setEnabled(false);
				//NOTE_DscField8.setEnabled(false);
			}
			else {
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
		return N_HEALTH.class;
	}

	
	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */

	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值
		//N_HEALTH data = (N_HEALTH) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */

	public boolean checkDataObject() {
		/*N_HEALTH data = (N_HEALTH) getDataObject();
		data.setCLOCK("1");
	    data.setID_KEY(data.getEMPSN()+"0001");*/
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
       this.getUIDataObjectBinder().registerCustomEditor(N_HEALTH.class, "DATES",PropertyEditors.createDateEditor("dd/MM/yyyy"));        
       this.getUIDataObjectBinder().registerCustomEditor(N_HEALTH.class, "EXPIRE",PropertyEditors.createDateEditor("dd/MM/yyyy"));        
       this.getUIDataObjectBinder().registerCustomEditor(N_HEALTH.class, "DATE_BHYT",PropertyEditors.createDateEditor("dd/MM/yyyy"));        
      // this.getUIDataObjectBinder().registerCustomEditor(N_HEALTH.class, "ID_PRO",this.getProviceEditor());        
     //  this.getUIDataObjectBinder().registerCustomEditor(N_HEALTH.class, "ID_HOS",this.getHospitalEditor()); 
        getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "FNAME", new VniEditor());
	    getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "LNAME", new VniEditor());
	    getUIDataObjectBinder().registerCustomEditor(N_HOSPITAL.class, "NAME_HOSPITAL", new VniEditor());
	}
	
	
	/*private PropertyEditor getHospitalEditor() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		String strHopital_Pro=ID_PRO_DscField7.getSelectedItem().toString();
		N_HOSPITAL obj=map.get(strHopital_Pro);
		
			editor.put(Vni2Uni.convertToUnicode(obj.getID_HOS()), obj.getID_HOS());
			
	
		return editor;
	}
*/

/*	private PropertyEditor getProviceEditor() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		
		N_HOSPITAL obj=new  N_HOSPITAL();
		map=new HashMap<String, N_HOSPITAL>();
		editor.put("TPHCM_079", "079");
		editor.put("BÌNH DƯƠNG_074","074");
		map.put("079", obj);
		map.put("074", obj);
		
		return editor;
	}
*/	
	


	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("ID_KEY", ID_KEY_DscField1, ID_KEY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField2, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_HEALTH", ID_HEALTH_DscField4, ID_HEALTH_CaptionLabel);
      //  getUIDataObjectBinder().addBindMap("CLOCK", CLOCK_DscField3, CLOCK_CaptionLabel);
        //getUIDataObjectBinder().addBindMap("DATES", DATES_DscDateField1, DATES_CaptionLabel);
       // getUIDataObjectBinder().addBindMap("EXPIRE", EXPIRE_DscDateField2, EXPIRE_CaptionLabel);
       // getUIDataObjectBinder().addBindMap("DATE_BHYT", DATE_BHYT_DscDateField3, DATE_BHYT_CaptionLabel);
       
       // getUIDataObjectBinder().addBindMap("SALARY", SALARY_DscField5, SALARY_CaptionLabel);
       // getUIDataObjectBinder().addBindMap("ID_HOS", ID_HOS_DscField6, ID_HOS_CaptionLabel);
       // getUIDataObjectBinder().addBindMap("ID_PRO", ID_PRO_DscField7, ID_PRO_CaptionLabel);
       // getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField8, NOTE_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	public void setName(String name){
		lblName.setText(name);
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
		//N_HEALTH data = (N_HEALTH) getDataObject();
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
		N_HEALTH data = (N_HEALTH) getDataObject();
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
        rootLayout.setWidth(new Extent(50, Extent.PERCENT));
        rootLayout.setSize(3);
        add(rootLayout);
        ID_KEY_CaptionLabel = new nextapp.echo2.app.Label();
        ID_KEY_CaptionLabel.setText("N_HEALTH.ID_KEY");
        //rootLayout.add(ID_KEY_CaptionLabel);
        ID_KEY_DscField1 = new dsc.echo2app.component.DscField();
        ID_KEY_DscField1.setId("ID_KEY_DscField1");
        //rootLayout.add(ID_KEY_DscField1);
        Row row = new Row();
        EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_CaptionLabel.setText("N_HEALTH.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField2 = new dsc.echo2app.component.DscField();
        EMPSN_DscField2.setId("EMPSN_DscField2");
        rootLayout.add(EMPSN_DscField2);        
        lblName = new Label();
        lblName.setForeground(Color.BLUE);
        row.add(lblName);
        rootLayout.add(row);
      /*  
        CLOCK_CaptionLabel = new nextapp.echo2.app.Label();
        CLOCK_CaptionLabel.setText("N_HEALTH.CLOCK");
        //rootLayout.add(CLOCK_CaptionLabel);
        CLOCK_DscField3 = new dsc.echo2app.component.DscField();
        CLOCK_DscField3.setId("CLOCK_DscField3");
        //rootLayout.add(CLOCK_DscField3);
        
        rootLayout.add(new Label(""));
        rootLayout.add(new Label(""));
        
        DATES_CaptionLabel = new nextapp.echo2.app.Label();
        DATES_CaptionLabel.setText("N_HEALTH.DATES");
        rootLayout.add(DATES_CaptionLabel);
        DATES_DscDateField1 = new dsc.echo2app.component.DscDateField();
        DATES_DscDateField1.setId("DATES_DscDateField1");
        DATES_DscDateField1.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        DATES_DscDateField1.getDateChooser().setLocale(Locale.ENGLISH);
        rootLayout.add(DATES_DscDateField1);*/
        
       // EXPIRE_CaptionLabel = new nextapp.echo2.app.Label();
       // EXPIRE_CaptionLabel.setText("N_HEALTH.EXPIRE");
       // rootLayout.add(EXPIRE_CaptionLabel);
      //  EXPIRE_DscDateField2 = new dsc.echo2app.component.DscDateField();
       // EXPIRE_DscDateField2.setId("EXPIRE_DscDateField2");
        //EXPIRE_DscDateField2.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
       // EXPIRE_DscDateField2.getDateChooser().setLocale(Locale.ENGLISH);
      //  rootLayout.add(EXPIRE_DscDateField2);
        
        
        ID_HEALTH_CaptionLabel = new nextapp.echo2.app.Label();
        ID_HEALTH_CaptionLabel.setText("N_HEALTH.ID_HEALTH");
        rootLayout.add(ID_HEALTH_CaptionLabel);
        ID_HEALTH_DscField4 = new dsc.echo2app.component.DscField();
        ID_HEALTH_DscField4.setId("ID_HEALTH_DscField4");
        rootLayout.add(ID_HEALTH_DscField4);
        
   /*     
        ID_HOS_CaptionLabel = new nextapp.echo2.app.Label();
       ID_HOS_CaptionLabel.setText("N_HEALTH.ID_HOS");
        rootLayout.add(ID_HOS_CaptionLabel);
       ID_HOS_DscField6 = new SelectField();
        ID_HOS_DscField6.setId("ID_HOS_DscField6");
        ID_HOS_DscField6.setWidth(new Extent(160));
       rootLayout.add(ID_HOS_DscField6);
        
       ID_PRO_CaptionLabel = new nextapp.echo2.app.Label();
       ID_PRO_CaptionLabel.setText("N_HEALTH.ID_PRO");
       rootLayout.add(ID_PRO_CaptionLabel);
       ID_PRO_DscField7 = new SelectField();
        ID_PRO_DscField7.setId("ID_PRO_DscField7");
        ID_PRO_DscField7.setWidth(new Extent(160));
        rootLayout.add(ID_PRO_DscField7);*/
        
        //SALARY_CaptionLabel = new nextapp.echo2.app.Label();
        //SALARY_CaptionLabel.setText("N_HEALTH.SALARY");
        //rootLayout.add(SALARY_CaptionLabel);
       // SALARY_DscField5 = new dsc.echo2app.component.DscField();
       // SALARY_DscField5.setId("SALARY_DscField5");
       // rootLayout.add(SALARY_DscField5);
        
       // DATE_BHYT_CaptionLabel = new nextapp.echo2.app.Label();
       /* DATE_BHYT_CaptionLabel.setText("N_HEALTH.DATE_BHYT");
        rootLayout.add(DATE_BHYT_CaptionLabel);
        DATE_BHYT_DscDateField3 = new dsc.echo2app.component.DscDateField();
        DATE_BHYT_DscDateField3.setId("DATE_BHYT_DscDateField3");
        DATE_BHYT_DscDateField3.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        DATE_BHYT_DscDateField3.getDateChooser().setLocale(Locale.ENGLISH);
        rootLayout.add(DATE_BHYT_DscDateField3);
        
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_HEALTH.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField8 = new dsc.echo2app.component.DscField();
        NOTE_DscField8.setId("NOTE_DscField8");
        rootLayout.add(NOTE_DscField8);
*/	}


	

}
