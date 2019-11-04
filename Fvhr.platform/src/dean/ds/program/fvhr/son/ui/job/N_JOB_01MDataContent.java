package ds.program.fvhr.son.ui.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import fv.util.Vni2Uni;
import fv.util.VniEditor;
import ds.program.fvhr.domain.N_JOB;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.dao.DataObjectSet;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;

public class N_JOB_01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label ID_KEY_CaptionLabel;
    private dsc.echo2app.component.DscField ID_KEY_DscField1;
    private nextapp.echo2.app.Label NAME_JOB_CaptionLabel;
    private dsc.echo2app.component.DscField NAME_JOB_DscField2;
    private nextapp.echo2.app.Label CODE_JOB_CaptionLabel;
    private dsc.echo2app.component.DscField CODE_JOB_DscField3;
    private nextapp.echo2.app.Label KIND_JOB_CaptionLabel;
    private nextapp.echo2.app.SelectField KIND_JOB_SelectField1;
    private nextapp.echo2.app.Label IN_FACT_CaptionLabel;
    private nextapp.echo2.app.SelectField IN_FACT_SelectField2;
    private nextapp.echo2.app.Label MONEY_CaptionLabel;
    private dsc.echo2app.component.DscField MONEY_DscField4;
    private nextapp.echo2.app.Label USER_UP_CaptionLabel;
    private dsc.echo2app.component.DscField USER_UP_DscField5;
    private nextapp.echo2.app.Label DATE_UP_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_UP_DscDateField1;

	/**
	 * Creates a new <code>N_JOB_01MDataContent</code>.
	 */
    ObjUtility _obj_util ;
    SimpleDateFormat _sf ;
    
    private String _name_job 	= "";
    private String _code_job		= "";
    private String _infact		= "";
    private N_JOB	jobDT;
    private boolean flag_new	= false;
	public N_JOB_01MDataContent() {
		super();
		_obj_util 	= new ObjUtility();
		_sf			= new SimpleDateFormat("dd/MM/yyyy");
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
            KIND_JOB_SelectField1.setEnabled(false);
            KIND_JOB_SelectField1.setStyleName("Default.ReadonlyField");
            
            
            MONEY_DscField4.setEnabled(false);
            MONEY_DscField4.setStyleName("Default.ReadonlyField");
            USER_UP_DscField5.setEnabled(false);
            USER_UP_DscField5.setStyleName("Default.ReadonlyField");
            DATE_UP_DscDateField1.setEnabled(false);
            DATE_UP_DscDateField1.setStyleName("Default.ReadonlyField");
            KIND_JOB_SelectField1.setEnabled(flag_new);
            KIND_JOB_SelectField1.setStyleName("Default.ReadonlyField");
            //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            //----------------------------------------------------------------------------------
			
            if(this.getProgram().getDataMode() == IProgram.DATAMODE_EDIT) {
				
            	CODE_JOB_DscField3.setEnabled(false);
				IN_FACT_SelectField2.setEnabled(false);
				
			} else {
				CODE_JOB_DscField3.setEnabled(true);
				IN_FACT_SelectField2.setEnabled(true);
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
		
		
		N_JOB data 		= (N_JOB) getDataObject();
		
		String code_ 	= (data.getCODE_JOB() == null || data.getCODE_JOB().equals(""))? "NO_CODE" : data.getCODE_JOB();
		
		if(ObjUtility.CheckSpecialCharacter(code_)){
			this.setErrorMessage("Mã công việc không hợp lệ");
			return false;
		}
		
		String kind_	= data.getKIND_JOB()  == null ? "" : data.getKIND_JOB();
		String in_fact_ = data.getIN_FACT()   == null ? "" : data.getIN_FACT();	
			
		data.setID_KEY(code_ +"_"+ kind_ + "_" + in_fact_);
		
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
	
	private MappingPropertyEditor Get_MapEditor_NameFact(){
		
		MappingPropertyEditor editor_	= new MappingPropertyEditor();
		editor_.put("", "" );
		String sql = 
			" select name_fact from n_factory ";

		List<String> list_arr_ = _obj_util.Exe_Sql_String(sql);
		if(list_arr_ != null){
			for(String  str: list_arr_){
				
				editor_.put(str, str );
			}
		}
		return editor_;
		
	}
	
	private void registPropertyEditor() {
	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor
        MappingPropertyEditor editor = null;
        editor = new MappingPropertyEditor();
        //editor.put("", "");
        editor.put("A", "A");
        editor.put("B", "B");
        editor.put("C", "C");
        editor.put("D", "D");
        getUIDataObjectBinder().registerCustomEditor(N_JOB.class, "KIND_JOB", editor);
        getUIDataObjectBinder().registerCustomEditor(N_JOB.class, "IN_FACT", Get_MapEditor_NameFact());
        getUIDataObjectBinder().registerCustomEditor(N_JOB.class, "DATE_UP",PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_JOB.class, "NAME_JOB",new VniEditor());
        
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("ID_KEY", ID_KEY_DscField1, ID_KEY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NAME_JOB", NAME_JOB_DscField2, NAME_JOB_CaptionLabel);
        getUIDataObjectBinder().addBindMap("CODE_JOB", CODE_JOB_DscField3, CODE_JOB_CaptionLabel);
        getUIDataObjectBinder().addBindMap("KIND_JOB", KIND_JOB_SelectField1, KIND_JOB_CaptionLabel);
        getUIDataObjectBinder().addBindMap("IN_FACT", IN_FACT_SelectField2, IN_FACT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("MONEY", MONEY_DscField4, MONEY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("USER_UP", USER_UP_DscField5, USER_UP_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_UP", DATE_UP_DscDateField1, DATE_UP_CaptionLabel);
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
		N_JOB data = (N_JOB) getDataObject();
		String user_up = Application.getApp().getLoginInfo().getUserID();
		data.setKIND_JOB("A");
		data.setUSER_UP(user_up);
		try {
			data.setDATE_UP(_sf.parse(_sf.format(new Date())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */

	public void beforeSaveToDataObjectSet() {
		N_JOB data = (N_JOB) getDataObject();
		String user_up = Application.getApp().getLoginInfo().getUserID();
		
		
		try {
			data.setDATE_UP(_sf.parse(_sf.format(new Date())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		_name_job	= data.getNAME_JOB() == null ? "" : Vni2Uni.convertToVNI(data.getNAME_JOB());
		_code_job 	= (data.getCODE_JOB() == null || data.getCODE_JOB().equals(""))? "NO_CODE" : data.getCODE_JOB();
		_infact 	= data.getIN_FACT()   == null ? "" : data.getIN_FACT();	
		
		data.setUSER_UP(user_up);	
		data.setID_KEY(_code_job +"_"+ "A" + "_" + _infact);
//		data.setNAME_JOB(_name_job);
		
		// get dataObjSet then add more dataObj(B,C,D) to take the place of doSave() that call procedure INS_JOB
		
		jobDT	= data;
		flag_new = false;
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW){
			flag_new = true;
		}else{
			flag_new = false;
		}
	}
	
	
	public N_JOB getJob(){
		return jobDT;
	}

	public boolean getFlagNew(){
		return flag_new;
	}
	
	public boolean saveToDataObjectSet() {
		// TODO Auto-generated method stub
		return super.saveToDataObjectSet();
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
        rootLayout.add(ID_KEY_CaptionLabel);
        ID_KEY_DscField1 = new dsc.echo2app.component.DscField();
        ID_KEY_DscField1.setId("ID_KEY_DscField1");
        rootLayout.add(ID_KEY_DscField1);
        
        rootLayout.add(new Label());
        rootLayout.add(new Label());
        
        NAME_JOB_CaptionLabel = new nextapp.echo2.app.Label();
        NAME_JOB_CaptionLabel.setText("N_JOB.NAME_JOB");
        rootLayout.add(NAME_JOB_CaptionLabel);
        NAME_JOB_DscField2 = new dsc.echo2app.component.DscField();
        NAME_JOB_DscField2.setId("NAME_JOB_DscField2");
        rootLayout.add(NAME_JOB_DscField2);

        rootLayout.add(new Label());
        rootLayout.add(new Label());
        
        CODE_JOB_CaptionLabel = new nextapp.echo2.app.Label();
        CODE_JOB_CaptionLabel.setText("N_JOB.CODE_JOB");
        rootLayout.add(CODE_JOB_CaptionLabel);
        CODE_JOB_DscField3 = new dsc.echo2app.component.DscField();
        CODE_JOB_DscField3.setId("CODE_JOB_DscField3");
        rootLayout.add(CODE_JOB_DscField3);
        
        MONEY_CaptionLabel = new nextapp.echo2.app.Label();
        MONEY_CaptionLabel.setText("N_JOB.MONEY");
        rootLayout.add(MONEY_CaptionLabel);
        MONEY_DscField4 = new dsc.echo2app.component.DscField();
        MONEY_DscField4.setId("MONEY_DscField4");
        rootLayout.add(MONEY_DscField4);
        IN_FACT_CaptionLabel = new nextapp.echo2.app.Label();
        IN_FACT_CaptionLabel.setText("N_JOB.IN_FACT");
        rootLayout.add(IN_FACT_CaptionLabel);
        IN_FACT_SelectField2 = new nextapp.echo2.app.SelectField();
        IN_FACT_SelectField2.setId("IN_FACT_SelectField2");
        IN_FACT_SelectField2.setWidth(new Extent(160));
        rootLayout.add(IN_FACT_SelectField2);
        
        
        USER_UP_CaptionLabel = new nextapp.echo2.app.Label();
        USER_UP_CaptionLabel.setText("N_JOB.USER_UP");
        rootLayout.add(USER_UP_CaptionLabel);
        USER_UP_DscField5 = new dsc.echo2app.component.DscField();
        USER_UP_DscField5.setId("USER_UP_DscField5");
        rootLayout.add(USER_UP_DscField5);
        
        KIND_JOB_CaptionLabel = new nextapp.echo2.app.Label();
        KIND_JOB_CaptionLabel.setText("N_JOB.KIND_JOB");
        rootLayout.add(KIND_JOB_CaptionLabel);
        KIND_JOB_SelectField1 = new nextapp.echo2.app.SelectField();
        KIND_JOB_SelectField1.setId("KIND_JOB_SelectField1");
        KIND_JOB_SelectField1.setWidth(new Extent(160));
        rootLayout.add(KIND_JOB_SelectField1);
        
        DATE_UP_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_UP_CaptionLabel.setText("N_JOB.DATE_UP");
        rootLayout.add(DATE_UP_CaptionLabel);
        DATE_UP_DscDateField1 = new dsc.echo2app.component.DscDateField();
        DATE_UP_DscDateField1.setId("DATE_UP_DscDateField1");
        DATE_UP_DscDateField1.setDateFormat(_sf);
        rootLayout.add(DATE_UP_DscDateField1);
        
       
        
        
	}

}
