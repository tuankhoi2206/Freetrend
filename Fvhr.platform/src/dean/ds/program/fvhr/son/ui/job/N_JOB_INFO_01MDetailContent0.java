package ds.program.fvhr.son.ui.job;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.beanutils.BeanUtils;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import ds.program.fvhr.domain.N_BASIC_SALARY;
import ds.program.fvhr.domain.N_EMP_JOB_DETAIL;
import ds.program.fvhr.domain.N_JOB;
import ds.program.fvhr.domain.N_JOB_DETAIL;
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

public class N_JOB_INFO_01MDetailContent0 extends DetailContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label ID_KEY_CaptionLabel;
    private dsc.echo2app.component.DscField ID_KEY_DscField1;
    private nextapp.echo2.app.Label MONEY_CaptionLabel;
    private dsc.echo2app.component.DscField MONEY_DscField2;
    private nextapp.echo2.app.Label DATE_B_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_B_DscDateField1;
    private nextapp.echo2.app.Label DATE_E_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_E_DscDateField2;
    private nextapp.echo2.app.Label DATE_UP_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_UP_DscDateField3;
    private nextapp.echo2.app.Label USER_UP_CaptionLabel;
    private dsc.echo2app.component.DscField USER_UP_DscField3;

	/**
	 * Creates a new <code>N_JOB_INFO_01MDetailContent0</code>.
	 */
    ObjUtility obj_util;
    SimpleDateFormat _sf = new SimpleDateFormat("dd/MM/yyyy");
	public N_JOB_DETAIL _deletedRecord;
	public N_JOB_INFO_01MDetailContent0() {
		super();
		obj_util  = new ObjUtility();
		// Add design-time configured components.
		initComponents();
	}
	
	/* (non-Javadoc)
	 * 建立資料關聯物件
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DetailContent#createRelation()
	 */

	public DataRelation createRelation() {
		return new DataRelation("ID_KEY", "ID_KEY");
	}

	/*
	 * for(int i = 0 ; i < dataSet.getRecordCount() ; i ++){
					N_JOB_DETAIL	jobDetail = (N_JOB_DETAIL) dataSet.getDataObject(i);
					
					
					if(jobDetail!=null && jobDetail.getDATE_E() == null ){
						return false;
						
					}
					
				}
	 * 
	 * 
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */

	public boolean checkDataObject() {
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		N_JOB_DETAIL data = (N_JOB_DETAIL) getDataObject();   // this data has just input dataset, not yet save in DB
		DataObjectSet	ds = this.getBrowserContent().getDataObjectSet();
		boolean ret = super.checkDataObject();
		if (ret) {
			// check when input new data, date_b(input) must to less than date_b(in db)
			System.out.println(ds.getRecordCount());
			if(this.getDataMode() == IProgram.DATAMODE_NEW )	{

				for(int i = 0 ; i < ds.getRecordCount() ; i ++){
					N_JOB_DETAIL	job = (N_JOB_DETAIL) ds.getDataObject(i);
					// only get row which have date_e == null 
					if(	   job!=null 
						&& job.getID_KEY()!=null 
						&& job.getDATE_B()!=data.getDATE_B()
					    && job.getDATE_E() == null 
					    && job.getDATE_B().after(data.getDATE_B()) ){
						
							this.setErrorMessage("Ngày bắt đầu sai quy tắc");
							return false;
						}
					
				}
				
			}
		}
		return ret;
	}

	/*
	 * UI資料欄位屬性更新，一般是資料模式變換時會呼叫此method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doUIRefresh()
	 */

	public void doUIRefresh() {
		
		if ((this.getDataMode() == IProgram.DATAMODE_NONE) || (this.getDataMode() == IProgram.DATAMODE_BROWSER)) {
			rootLayout.setEnabled(false);
		} else {
			rootLayout.setEnabled(true);
			//----------------------------------------------------------------------------------
			//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            ID_KEY_DscField1.setEnabled(false);
            ID_KEY_DscField1.setStyleName("Default.ReadonlyField");
            DATE_E_DscDateField2.setEnabled(false);
            DATE_E_DscDateField2.setStyleName("Default.ReadonlyField");
            DATE_UP_DscDateField3.setEnabled(false);
            DATE_UP_DscDateField3.setStyleName("Default.ReadonlyField");
            USER_UP_DscField3.setEnabled(false);
            USER_UP_DscField3.setStyleName("Default.ReadonlyField");
            //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            //----------------------------------------------------------------------------------
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_EDIT) {
			
			} else {
				//修改時，設定哪些元件Enable/Disable
			}
		}

		//7.<資料權限管控>
		N_JOB_DETAIL data = (N_JOB_DETAIL) this.getBrowserContent().getDataObjectSet().getDataObject();
		String userLog = Application.getApp().getLoginInfo().getUserID();
		if(data!=null && data.getUSER_UP()!=null && data.getUSER_UP().equals(userLog)){
			getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, true);
			getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, true);
			if(data.getDATE_E()!= null){
				getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, false);
				getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, false);
			}
		}else{
			getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, false);
			getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, false);
		}
		
		super.doUIRefresh();
		

		//自動設定元件風格
		getUIDataObjectBinder().doDataBindUIStyle();
	}

	/*
	 * 取得該畫面的DataObject Class
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#getDataObjectClass()
	 */
	public Class getDataObjectClass() {
		return N_JOB_DETAIL.class;
	}

	
	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */

	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值
		//N_JOB_DETAIL data = (N_JOB_DETAIL) getDataObject();
	}


	protected int doInit() {
		int nRet = super.doInit();

		this.getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, false);
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
        getUIDataObjectBinder().registerCustomEditor(N_JOB_DETAIL.class,"DATE_B",PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_JOB_DETAIL.class,"DATE_E",PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_JOB_DETAIL.class,"DATE_UP",PropertyEditors.createDateEditor("dd/MM/yyyy"));


	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("ID_KEY", ID_KEY_DscField1, ID_KEY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("MONEY", MONEY_DscField2, MONEY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_B", DATE_B_DscDateField1, DATE_B_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_E", DATE_E_DscDateField2, DATE_E_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_UP", DATE_UP_DscDateField3, DATE_UP_CaptionLabel);
        getUIDataObjectBinder().addBindMap("USER_UP", USER_UP_DscField3, USER_UP_CaptionLabel);
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
		N_JOB_DETAIL data = (N_JOB_DETAIL) getDataObject();
		//<如果要預先取號則在此加入>
		//autoSequenceKeyValue();
		//使用者可在以下區域填入新增時內定的預設數值
		//<內定值>
		
		//<<填入與單頭關聯值>>
        N_JOB pdata = (N_JOB) getMasterDataContent().getDataObject();
        
        data.setID_KEY(pdata.getID_KEY());
		data.setDATE_B(obj_util.MONTH_NOW("01", new Date()));
		
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */

	public void beforeSaveToDataObjectSet() {
		N_JOB_DETAIL data = (N_JOB_DETAIL) getDataObject();
		data.setUSER_UP(Application.getApp().getLoginInfo().getUserID());
		data.setDATE_UP(ObjUtility.DateFormat_DDMMYYYY(new Date()));
		if(this.getDataMode() == IProgram.DATAMODE_NEW){
			ObjUtility	oUtil = new ObjUtility();		
			
			DataObjectSet ds = getDataObjectSet();		
				
				for (int i=0;i<ds.getRecordCount();i++){
					N_JOB_DETAIL objData = (N_JOB_DETAIL) ds.getDataObject(i);
					if(data.getDATE_B().equals(objData.getDATE_B())){
						ObjUtility.ShowMessageError("Đã Nhập Dữ Liệu Tháng Rồi");
					}
					if(objData.getDATE_E() == null && objData.getDATE_B() != data.getDATE_B()){
						objData.setDATE_E(data.getDATE_B());
						ds.modify(i, objData);//record status modify=>save=>update db
					}
				}
		}
		
		
		
	}


	public boolean doDelete() {
		N_JOB_DETAIL data = (N_JOB_DETAIL) getDataObject();
		DataObjectSet ds = getDataObjectSet();		
			for (int i=0;i<ds.getRecordCount();i++){
				N_JOB_DETAIL objData = (N_JOB_DETAIL) ds.getDataObject(i);
				if(objData.getDATE_E()!= null && objData.getDATE_E().equals(data.getDATE_B())){
					objData.setDATE_E(null);
					ds.modify(i, objData);//record status modify=>save=>update db
				}
			}
		
		return super.doDelete();
	}
	

	protected String[] getBrowserDisplayColumns() {
		//<<從此以下加入使用者程式>>
		//<<資料瀏覽欄位>>
        return new String[] {"ID_KEY", "MONEY", "DATE_B", "DATE_E", "DATE_UP", "USER_UP"};
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
        ID_KEY_CaptionLabel.setText("N_JOB_DETAIL.ID_KEY");
        //rootLayout.add(ID_KEY_CaptionLabel);
        ID_KEY_DscField1 = new dsc.echo2app.component.DscField();
        ID_KEY_DscField1.setId("ID_KEY_DscField1");
        //rootLayout.add(ID_KEY_DscField1);
        
        MONEY_CaptionLabel = new nextapp.echo2.app.Label();
        MONEY_CaptionLabel.setText("N_JOB_DETAIL.MONEY");
        rootLayout.add(MONEY_CaptionLabel);
        MONEY_DscField2 = new dsc.echo2app.component.DscField();
        MONEY_DscField2.setId("MONEY_DscField2");
        rootLayout.add(MONEY_DscField2);
        
        DATE_E_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_E_CaptionLabel.setText("N_JOB_DETAIL.DATE_E");
        //rootLayout.add(DATE_E_CaptionLabel);
        DATE_E_DscDateField2 = new dsc.echo2app.component.DscDateField();
        DATE_E_DscDateField2.setId("DATE_E_DscDateField2");
        DATE_E_DscDateField2.setDateFormat(_sf);
        //rootLayout.add(DATE_E_DscDateField2);
        
        USER_UP_CaptionLabel = new nextapp.echo2.app.Label();
        USER_UP_CaptionLabel.setText("N_JOB_DETAIL.USER_UP");
        rootLayout.add(USER_UP_CaptionLabel);
        USER_UP_DscField3 = new dsc.echo2app.component.DscField();
        USER_UP_DscField3.setId("USER_UP_DscField3");
        rootLayout.add(USER_UP_DscField3);
        
        DATE_B_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_B_CaptionLabel.setText("N_JOB_DETAIL.DATE_B");
        rootLayout.add(DATE_B_CaptionLabel);
        DATE_B_DscDateField1 = new dsc.echo2app.component.DscDateField();
        DATE_B_DscDateField1.setId("DATE_B_DscDateField1");
        DATE_B_DscDateField1.setDateFormat(_sf);
        DATE_B_DscDateField1.getDateChooser().setLocale(Locale.ENGLISH);
        rootLayout.add(DATE_B_DscDateField1);
        
        DATE_UP_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_UP_CaptionLabel.setText("N_JOB_DETAIL.DATE_UP");
        rootLayout.add(DATE_UP_CaptionLabel);
        DATE_UP_DscDateField3 = new dsc.echo2app.component.DscDateField();
        DATE_UP_DscDateField3.setId("DATE_UP_DscDateField3");
        DATE_UP_DscDateField3.setDateFormat(_sf);
        rootLayout.add(DATE_UP_DscDateField3);
        
	}

}
