package ds.program.fvhr.son.ui.bsalary;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_BASIC_SALARY;
import ds.program.fvhr.domain.N_BONUS_POSS;
import ds.program.fvhr.domain.N_EMP_BONUS;
import ds.program.fvhr.domain.N_SOCIAL;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.dao.DataObjectSet;
import dsc.dao.DataRelation;
import dsc.echo2app.Application;
import dsc.echo2app.program.DetailContent;
import dsc.echo2app.program.DetailToolbar;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.SubDetailContent;
import dsc.echo2app.propertyeditors.PropertyEditors;

public class BasicSalary01MDetailContent0 extends DetailContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label BSALARY_CaptionLabel;
    private dsc.echo2app.component.DscField BSALARY_DscField1;
    private nextapp.echo2.app.Label DATES_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATES_DscDateField1;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField2;
    private nextapp.echo2.app.Label END_DATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField END_DATE_DscDateField2;
    private nextapp.echo2.app.Label KEYS_CaptionLabel;
    private dsc.echo2app.component.DscField KEYS_DscField3;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField4;
    private nextapp.echo2.app.Label USER_UP_CaptionLabel;
    private dsc.echo2app.component.DscField USER_UP_DscField5;
    private nextapp.echo2.app.Label DATE_UP_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_UP_DscDateField1;

	/**
	 * Creates a new <code>BasicSalary01MDetailContent0</code>.
	 */
	public BasicSalary01MDetailContent0() {
		super();

		// Add design-time configured components.
		initComponents();
	}
	
	/* (non-Javadoc)
	 * 建立資料關聯物件
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DetailContent#createRelation()
	 */

	public DataRelation createRelation() {
		return new DataRelation("EMPSN", "EMPSN");
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */

	public boolean checkDataObject() {
		//N_BASIC_SALARY data = (N_BASIC_SALARY) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			//使用者可在以下區域撰寫內容合理值的判斷程式碼
		}
		return ret;
	}

	/*
	 * UI資料欄位屬性更新，一般是資料模式變換時會呼叫此method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doUIRefresh()
	 */

	public void doUIRefresh() {
		super.doUIRefresh();
		if ((this.getDataMode() == IProgram.DATAMODE_NONE) || (this.getDataMode() == IProgram.DATAMODE_BROWSER)) {
			rootLayout.setEnabled(false);
		} else {
			rootLayout.setEnabled(true);
			//----------------------------------------------------------------------------------
			//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            EMPSN_DscField2.setEnabled(false);
            EMPSN_DscField2.setStyleName("Default.ReadonlyField");
            USER_UP_DscField5.setEnabled(false);
            USER_UP_DscField5.setStyleName("Default.ReadonlyField");
            DATE_UP_DscDateField1.setEnabled(false);
            DATE_UP_DscDateField1.setStyleName("Default.ReadonlyField");
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
		return N_BASIC_SALARY.class;
	}

	
	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */

	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值
		//N_BASIC_SALARY data = (N_BASIC_SALARY) getDataObject();
	}

	/*
	 * 自動編號 
	 */
	protected boolean autoSequenceKeyValue() {
		//使用者可以在以下區域撰寫產生新序號的程式碼並將新序號放入dataObject
		//N_BASIC_SALARY data = (N_BASIC_SALARY) getDataObject();
		//int maxSeq = 0;
		//N_BASIC_SALARY maxData = (N_BASIC_SALARY)this.getDataObjectSet().getDataObjectByFieldMax("{ID_FIELD}");
		//if(maxData != null && maxData.get{ID_FIELD}() != null) {
		//	try {
		//		maxSeq = Integer.parseInt(maxData.get{ID_FIELD}());
		//	} catch (Exception e) {
		//		maxSeq = 0;
		//	}
		//}
		//data.set{ID_FIELD}( MessageFormat.format("{0,number,0000}",

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
        dsc.echo2app.propertyeditors.MappingPropertyEditor editor = null;
        this.getUIDataObjectBinder().registerCustomEditor(N_BASIC_SALARY.class,"DATES",PropertyEditors.createDateEditor("dd/MM/yyyy"));
        this.getUIDataObjectBinder().registerCustomEditor(N_BASIC_SALARY.class,"END_DATE",PropertyEditors.createDateEditor("dd/MM/yyyy"));
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("BSALARY", BSALARY_DscField1, BSALARY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATES", DATES_DscDateField1, DATES_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField2, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("END_DATE", END_DATE_DscDateField2, END_DATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("KEYS", KEYS_DscField3, KEYS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField4, NOTE_CaptionLabel);
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
	

	protected void doDataSelectChange() {
		// TODO Auto-generated method stub
		super.doDataSelectChange();
		
		String userLog= Application.getApp().getLoginInfo().getUserID();
		N_BASIC_SALARY data = (N_BASIC_SALARY) this.getBrowserContent().getDataObjectSet().getDataObject();
		
		if(data != null && (data.getEND_DATE()!=null || data.getUSER_UP()==null || !data.getUSER_UP().equals(userLog))){
			this.getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, false);
		}else{
			this.getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, true);
		}
		
		if(data.getUSER_UP()==null || !data.getUSER_UP().equals(userLog)){
			this.getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, false);
		}else{
			this.getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, true);
		}
		
		doUIRefresh();
	}
	
	/*
	 * 做資料新增時內定預設數值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doNewDefaulData()
	 */
	

	public boolean doSave() {
		
		
		return super.doSave();
	}
	

	protected void doNewDefaulData() {
		N_BASIC_SALARY data = (N_BASIC_SALARY) getDataObject();
		
		N_EMPLOYEE pdata = (N_EMPLOYEE) getMasterDataContent().getDataObject();
        data.setEMPSN(pdata.getEMPSN());
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */

	public void beforeSaveToDataObjectSet() {
		N_BASIC_SALARY data = (N_BASIC_SALARY) getDataObject();
		data.setUSER_UP(Application.getApp().getLoginInfo().getUserID());
		data.setDATE_UP(ObjUtility.DateFormat_DDMMYYYY(new Date()));
		if(this.getDataMode() == IProgram.DATAMODE_NEW){
			ObjUtility	oUtil = new ObjUtility();		
			
			data.setDATES(oUtil.MONTH_NOW("01", data.getDATES()));
			DataObjectSet ds = getDataObjectSet();		
				
				for (int i=0;i<ds.getRecordCount();i++){
					N_BASIC_SALARY objData = (N_BASIC_SALARY) ds.getDataObject(i);
					if(data.getDATES().equals(objData.getDATES())){
						ObjUtility.ShowMessageError("Đã Nhập Dữ Liệu Tháng Rồi");
					}
					if(objData.getEND_DATE() == null && objData.getDATES() != data.getDATES()){
						objData.setEND_DATE(data.getDATES());
						ds.modify(i, objData);//record status modify=>save=>update db
					}
				}
		}
		
		
		
	}


	public boolean doDelete() {
		N_BASIC_SALARY data = (N_BASIC_SALARY) getDataObject();
		DataObjectSet ds = getDataObjectSet();		
			for (int i=0;i<ds.getRecordCount();i++){
				N_BASIC_SALARY objData = (N_BASIC_SALARY) ds.getDataObject(i);
				if(objData.getEND_DATE()!= null && objData.getEND_DATE().equals(data.getDATES())){
					objData.setEND_DATE(null);
					ds.modify(i, objData);//record status modify=>save=>update db
				}
			}
		
		return super.doDelete();
	}
	
	public void doDrillDown() {
		
	}


	protected String[] getBrowserDisplayColumns() {
		//<<從此以下加入使用者程式>>
        return new String[] {"EMPSN", "BSALARY", "DATES", "END_DATE", "NOTE","USER_UP"};
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
        BSALARY_CaptionLabel = new nextapp.echo2.app.Label();
        BSALARY_CaptionLabel.setText("N_BASIC_SALARY.BSALARY");
        rootLayout.add(BSALARY_CaptionLabel);
        BSALARY_DscField1 = new dsc.echo2app.component.DscField();
        BSALARY_DscField1.setId("BSALARY_DscField1");
        rootLayout.add(BSALARY_DscField1);
       
        
        USER_UP_CaptionLabel = new nextapp.echo2.app.Label();
        USER_UP_CaptionLabel.setText("N_JOB.USER_UP");
        rootLayout.add(USER_UP_CaptionLabel);
        USER_UP_DscField5 = new dsc.echo2app.component.DscField();
        USER_UP_DscField5.setId("USER_UP_DscField5");
        rootLayout.add(USER_UP_DscField5);
        
        
        
        DATES_CaptionLabel = new nextapp.echo2.app.Label();
        DATES_CaptionLabel.setText("N_BASIC_SALARY.DATES");
        rootLayout.add(DATES_CaptionLabel);
        DATES_DscDateField1 = new dsc.echo2app.component.DscDateField();
        DATES_DscDateField1.setId("DATES_DscDateField1");
        DATES_DscDateField1.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        DATES_DscDateField1.getDateChooser().setLocale(Locale.ENGLISH);
        rootLayout.add(DATES_DscDateField1);
        
        DATE_UP_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_UP_CaptionLabel.setText("N_JOB.DATE_UP");
        rootLayout.add(DATE_UP_CaptionLabel);
        DATE_UP_DscDateField1 = new dsc.echo2app.component.DscDateField();
        DATE_UP_DscDateField1.setId("DATE_UP_DscDateField1");
        DATE_UP_DscDateField1.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        rootLayout.add(DATE_UP_DscDateField1);
        
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_BASIC_SALARY.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField4 = new dsc.echo2app.component.DscField();
        NOTE_DscField4.setId("NOTE_DscField4");
        rootLayout.add(NOTE_DscField4);
        
        
        
        KEYS_CaptionLabel = new nextapp.echo2.app.Label();
        KEYS_CaptionLabel.setText("N_BASIC_SALARY.KEYS");
//        rootLayout.add(KEYS_CaptionLabel);
        KEYS_DscField3 = new dsc.echo2app.component.DscField();
        KEYS_DscField3.setId("KEYS_DscField3");
//        rootLayout.add(KEYS_DscField3);
        
        END_DATE_CaptionLabel = new nextapp.echo2.app.Label();
        END_DATE_CaptionLabel.setText("N_BASIC_SALARY.END_DATE");
//        rootLayout.add(END_DATE_CaptionLabel);
        END_DATE_DscDateField2 = new dsc.echo2app.component.DscDateField();
        END_DATE_DscDateField2.setId("END_DATE_DscDateField2");
//        rootLayout.add(END_DATE_DscDateField2);
        
        EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_CaptionLabel.setText("N_BASIC_SALARY.EMPSN");
//        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField2 = new dsc.echo2app.component.DscField();
        EMPSN_DscField2.setId("EMPSN_DscField2");
//        rootLayout.add(EMPSN_DscField2);
	}

}
