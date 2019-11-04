package ds.program.fvhr.son.ui.insurance.social;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import fv.util.Vni2Uni;
import fv.util.VniEditor;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_HEALTH;
import ds.program.fvhr.domain.N_SOCIAL;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.PropertyEditors;

public class social01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label ID_KEY_CaptionLabel;
    private dsc.echo2app.component.DscField ID_KEY_DscField1;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField2;
    private nextapp.echo2.app.Label DATES_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATES_DscDateField1;
    private nextapp.echo2.app.Label EXPIRE_CaptionLabel;
    private dsc.echo2app.component.DscDateField EXPIRE_DscDateField2;
    private nextapp.echo2.app.Label DATE_NEW_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_NEW_DscDateField3;
    private nextapp.echo2.app.Label ID_SOCIAL_CaptionLabel;
    private dsc.echo2app.component.DscField ID_SOCIAL_DscField3;
    private nextapp.echo2.app.Label SALARY_CaptionLabel;
    private dsc.echo2app.component.DscField SALARY_DscField4;
    private nextapp.echo2.app.Label CLOCK_CaptionLabel;
    private dsc.echo2app.component.DscField CLOCK_DscField5;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField6;
	private Label lblName;

	/**
	 * Creates a new <code>social01MDataContent</code>.
	 */
	public social01MDataContent() {
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
				EMPSN_DscField2.setEnabled(false);
				DATES_DscDateField1.setEnabled(false);
				EXPIRE_DscDateField2.setEnabled(false);
				DATE_NEW_DscDateField3.setEnabled(true);
				SALARY_DscField4.setEnabled(false);
				CLOCK_DscField5.setEnabled(false);
				NOTE_DscField6.setEnabled(false);
				
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
		return N_SOCIAL.class;
	}

	
	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */

	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值	
		N_SOCIAL data = (N_SOCIAL) getDataObject();
		IGenericDAO<N_SOCIAL, String> daoSocial=Application.getApp().getDao(N_SOCIAL.class);
		List<N_SOCIAL> listSocial=daoSocial.find(1, "from N_SOCIAL where EMPSN=? and CLOCK='1'", data.getEMPSN());
		if(listSocial.size()>0)
		{
			N_SOCIAL objn_social=listSocial.get(0);
			String strID_SOCIALOLD=objn_social.getID_SOCIAL();
			String strNote=strID_SOCIALOLD+"_"+data.getNOTE();
			data.setNOTE(strNote);			
		}		
		
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */

	public boolean checkDataObject() {
		N_SOCIAL data = (N_SOCIAL) getDataObject();
		
		if(!data.getEMPSN().equals("")){
			data.setID_KEY(data.getEMPSN() + "0001");
		}
		boolean ret = super.checkDataObject();
		if (ret) {
			
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
        this.getUIDataObjectBinder().registerCustomEditor(N_SOCIAL.class, "DATES",PropertyEditors.createDateEditor("dd/MM/yyyy"));        
        this.getUIDataObjectBinder().registerCustomEditor(N_SOCIAL.class, "EXPIRE",PropertyEditors.createDateEditor("dd/MM/yyyy"));        
        this.getUIDataObjectBinder().registerCustomEditor(N_SOCIAL.class, "DATE_NEW",PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "FNAME", new VniEditor());
	    getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "LNAME", new VniEditor());
	    getUIDataObjectBinder().registerCustomEditor(N_SOCIAL.class, "NOTE", new VniEditor());
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("ID_KEY", ID_KEY_DscField1, ID_KEY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField2, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATES", DATES_DscDateField1, DATES_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EXPIRE", EXPIRE_DscDateField2, EXPIRE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_NEW", DATE_NEW_DscDateField3, DATE_NEW_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_SOCIAL", ID_SOCIAL_DscField3, ID_SOCIAL_CaptionLabel);
        getUIDataObjectBinder().addBindMap("SALARY", SALARY_DscField4, SALARY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("CLOCK", CLOCK_DscField5, CLOCK_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField6, NOTE_CaptionLabel);
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
	public void setName(String name){
		lblName.setText(name);
	}
	protected void doNewDefaulData() {
		//N_SOCIAL data = (N_SOCIAL) getDataObject();
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
/*
	public void beforeSaveToDataObjectSet() {
		N_SOCIAL data = (N_SOCIAL) getDataObject();
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
		    autoPrimaryKeyValue();
		}
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		data.setID_KEY(data.getEMPSN() + "0001");
		data.setCLOCK("1");   
		data.setNOTE(Vni2Uni.convertToVNI(data.getNOTE()+ "-- ( Cập nhật : "
														+Application.getApp().getLoginInfo().getUserID() 
														+" - " + sf.format(new Date())
														+" )"));
	}*/

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
        ID_KEY_CaptionLabel.setText("N_SOCIAL.ID_KEY");
        //rootLayout.add(ID_KEY_CaptionLabel);
        ID_KEY_DscField1 = new dsc.echo2app.component.DscField();
        ID_KEY_DscField1.setId("ID_KEY_DscField1");
        //rootLayout.add(ID_KEY_DscField1);
        EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_CaptionLabel.setText("N_SOCIAL.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField2 = new dsc.echo2app.component.DscField();
        EMPSN_DscField2.setId("EMPSN_DscField2");       
        lblName = new Label();
        lblName.setForeground(Color.BLUE);
        Row row = new Row();
        row.add(EMPSN_DscField2);
        row.add(lblName);
        rootLayout.add(row);
        rootLayout.add(new Label());
        rootLayout.add(new Label());
        
        DATES_CaptionLabel = new nextapp.echo2.app.Label();
        DATES_CaptionLabel.setText("N_SOCIAL.DATES");
        rootLayout.add(DATES_CaptionLabel);
        DATES_DscDateField1 = new dsc.echo2app.component.DscDateField();
        DATES_DscDateField1.setId("DATES_DscDateField1");
        DATES_DscDateField1.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        DATES_DscDateField1.getDateChooser().setLocale(Locale.ENGLISH);
        rootLayout.add(DATES_DscDateField1);
        
        EXPIRE_CaptionLabel = new nextapp.echo2.app.Label();
        EXPIRE_CaptionLabel.setText("N_SOCIAL.EXPIRE");
        rootLayout.add(EXPIRE_CaptionLabel);
        EXPIRE_DscDateField2 = new dsc.echo2app.component.DscDateField();
        EXPIRE_DscDateField2.setId("EXPIRE_DscDateField2");
        EXPIRE_DscDateField2.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        EXPIRE_DscDateField2.getDateChooser().setLocale(Locale.ENGLISH);
        rootLayout.add(EXPIRE_DscDateField2);
        
        ID_SOCIAL_CaptionLabel = new nextapp.echo2.app.Label();
        ID_SOCIAL_CaptionLabel.setText("N_SOCIAL.ID_SOCIAL");
        rootLayout.add(ID_SOCIAL_CaptionLabel);
        ID_SOCIAL_DscField3 = new dsc.echo2app.component.DscField();
        ID_SOCIAL_DscField3.setId("ID_SOCIAL_DscField3");
        rootLayout.add(ID_SOCIAL_DscField3);
        
        DATE_NEW_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_NEW_CaptionLabel.setText("N_SOCIAL.DATE_NEW");
        rootLayout.add(DATE_NEW_CaptionLabel);
        DATE_NEW_DscDateField3 = new dsc.echo2app.component.DscDateField();
        DATE_NEW_DscDateField3.setId("DATE_NEW_DscDateField3");
        DATE_NEW_DscDateField3.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        DATE_NEW_DscDateField3.getDateChooser().setLocale(Locale.ENGLISH);
        rootLayout.add(DATE_NEW_DscDateField3);
        
        
        SALARY_CaptionLabel = new nextapp.echo2.app.Label();
        SALARY_CaptionLabel.setText("N_SOCIAL.SALARY");
        rootLayout.add(SALARY_CaptionLabel);
        SALARY_DscField4 = new dsc.echo2app.component.DscField();
        SALARY_DscField4.setId("SALARY_DscField4");
        rootLayout.add(SALARY_DscField4);
        
        CLOCK_CaptionLabel = new nextapp.echo2.app.Label();
        CLOCK_CaptionLabel.setText("N_SOCIAL.CLOCK");
        //rootLayout.add(CLOCK_CaptionLabel);
        CLOCK_DscField5 = new dsc.echo2app.component.DscField();
        CLOCK_DscField5.setId("CLOCK_DscField5");
       // rootLayout.add(CLOCK_DscField5);
        
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_SOCIAL.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField6 = new dsc.echo2app.component.DscField();
        NOTE_DscField6.setId("NOTE_DscField6");
        rootLayout.add(NOTE_DscField6);
	}

}
