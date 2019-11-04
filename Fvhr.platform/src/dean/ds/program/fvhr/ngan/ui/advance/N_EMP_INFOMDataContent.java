package ds.program.fvhr.ngan.ui.advance;

import groovyjarjarasm.asm.Label;

import java.beans.PropertyEditor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.SelectField;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import ds.program.fvhr.domain.N_N_EMP_BORM;
import ds.program.fvhr.domain.N_EMP_INFO;
import ds.program.fvhr.son.ui.ObjUtility;



public class N_EMP_INFOMDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label ADVANCE_CaptionLabel;
 //   private dsc.echo2app.component.DscField ADVANCE_DscField1;
     private SelectField sfStatus;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField5;
    private nextapp.echo2.app.Label UP_DATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField UP_DATE_DscDateField2;
    private nextapp.echo2.app.Label UP_USER_CaptionLabel;
    private dsc.echo2app.component.DscField UP_USER_DscField13;
    SimpleDateFormat _sf = new SimpleDateFormat(ObjUtility.DATE_FORMAT_STR);
  

	/**
	 * Creates a new <code>N_EMP_INFOMDataContent</code>.
	 */
	public N_EMP_INFOMDataContent() {
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
			UP_USER_DscField13.setEnabled(false);
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
		return N_EMP_INFO.class;
	}

	
	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */

	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值
		//N_EMP_INFO data = (N_EMP_INFO) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */

	public boolean checkDataObject() {
		//N_EMP_INFO data = (N_EMP_INFO) getDataObject();
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
		//N_EMP_INFO data = (N_EMP_INFO) getDataObject();
		//Connection conn = Application.getApp().getConnection();
		//Map keys = new HashMap();
		//Map filters = new HashMap();
		//String newValue = KeyNumberGenerator.generateNumber(conn,
		//		"$Y{2}$M{2}$D{2}$S{4}", 
		//		1,
		//		"N_EMP_INFO", "{ID_FIELD}", 
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
        getUIDataObjectBinder().registerCustomEditor(N_EMP_INFO.class, "ADVANCE", statusEditor());
        this.getUIDataObjectBinder().registerCustomEditor(N_EMP_INFO.class,"UP_DATE",PropertyEditors.createDateEditor("dd/MM/yyyy h:mm:ss a"));
       
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("ADVANCE", sfStatus, ADVANCE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField5, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("UP_DATE", UP_DATE_DscDateField2, UP_DATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("UP_USER", UP_USER_DscField13, UP_USER_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	

	private MappingPropertyEditor statusEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		e.put("Có đăng ký ứng lương",1L);
		e.put("Không đăng ký ứng lương",0L);		
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
		
		    N_EMP_INFO data = (N_EMP_INFO) getDataObject();
			String 	user_up = Application.getApp().getLoginInfo().getUserID();
			Date	date = null;
			
			try {
				date 	= _sf.parse(_sf.format(new Date()));	
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			data.setUP_USER(user_up);
			data.setUP_DATE(new Date());
		
		
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */

	public void beforeSaveToDataObjectSet() {
		N_EMP_INFO data = (N_EMP_INFO) getDataObject();
		data.setUP_USER(getProgram().getLoginInfo().getUserID());
		data.setUP_DATE(new Date());
		
		
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
        EMPSN_CaptionLabel.setText("N_EMP_INFO.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        
        EMPSN_DscField5 = new dsc.echo2app.component.DscField();
        EMPSN_DscField5.setId("EMPSN_DscField5");
        rootLayout.add(EMPSN_DscField5);
      
        UP_DATE_CaptionLabel = new nextapp.echo2.app.Label();
        UP_DATE_CaptionLabel.setText("N_EMP_INFO.UP_DATE");
        rootLayout.add(UP_DATE_CaptionLabel);
        
        UP_DATE_DscDateField2 = new dsc.echo2app.component.DscDateField();
        UP_DATE_DscDateField2.setId("UP_DATE_DscDateField2");
        UP_DATE_DscDateField2.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        rootLayout.add(UP_DATE_DscDateField2);
       
        
        ADVANCE_CaptionLabel = new nextapp.echo2.app.Label();
        ADVANCE_CaptionLabel.setText("N_EMP_INFO.ADVANCE");
        rootLayout.add(ADVANCE_CaptionLabel); 
       /* ADVANCE_DscField1 = new dsc.echo2app.component.DscField();
        ADVANCE_DscField1.setId("ADVANCE_DscField1");
        rootLayout.add(ADVANCE_DscField1);*/
        
        sfStatus = new SelectField();
        sfStatus.setWidth(new Extent(200));
        rootLayout.add(sfStatus);
        
        UP_USER_CaptionLabel = new nextapp.echo2.app.Label();
        UP_USER_CaptionLabel.setText("N_EMP_INFO.UP_USER");
        rootLayout.add(UP_USER_CaptionLabel);
        
        UP_USER_DscField13 = new dsc.echo2app.component.DscField();
        UP_USER_DscField13.setId("UP_USER_DscField13");
        rootLayout.add(UP_USER_DscField13);
        
     
	}

}
