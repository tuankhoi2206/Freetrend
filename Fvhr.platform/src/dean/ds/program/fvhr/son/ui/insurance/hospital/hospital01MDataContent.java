package ds.program.fvhr.son.ui.insurance.hospital;

import java.beans.PropertyEditor;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.SelectField;
import fv.util.Vni2Uni;
import ds.program.fvhr.domain.N_HOSPITAL;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;

public class hospital01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label ADDRESS_CaptionLabel;
    private dsc.echo2app.component.DscField ADDRESS_DscField1;
    private nextapp.echo2.app.Label ID_HOS_CaptionLabel;
    private dsc.echo2app.component.DscField ID_HOS_DscField2;
    private nextapp.echo2.app.Label ID_PROVINCE_CaptionLabel;
    private SelectField ID_PROVINCE_DscField3;
    private nextapp.echo2.app.Label NAME_HOSPITAL_CaptionLabel;
    private dsc.echo2app.component.DscField NAME_HOSPITAL_DscField4;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField5;

	/**
	 * Creates a new <code>hospital01MDataContent</code>.
	 */
	public hospital01MDataContent() {
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
		return N_HOSPITAL.class;
	}

	
	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */

	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值
		//N_HOSPITAL data = (N_HOSPITAL) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */

	public boolean checkDataObject() {
		//N_HOSPITAL data = (N_HOSPITAL) getDataObject();
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
        this.getUIDataObjectBinder().registerCustomEditor(N_HOSPITAL.class, "ID_PROVINCE",getEditorProvince());
//        getUIDataObjectBinder().registerCustomEditor(propertyPath, propertyEditor)
	}
	
	private MappingPropertyEditor getEditorProvince() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		editor.put("TPHCM_079", "079");
		editor.put("BÌNH DƯƠNG_074","074");
		
		return editor;
	}


	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("ADDRESS", ADDRESS_DscField1, ADDRESS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_HOS", ID_HOS_DscField2, ID_HOS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_PROVINCE", ID_PROVINCE_DscField3, ID_PROVINCE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NAME_HOSPITAL", NAME_HOSPITAL_DscField4, NAME_HOSPITAL_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField5, NOTE_CaptionLabel);
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
		//N_HOSPITAL data = (N_HOSPITAL) getDataObject();
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
		N_HOSPITAL data = (N_HOSPITAL) getDataObject();
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
		    autoPrimaryKeyValue();
		}
		
		data.setADDRESS(Vni2Uni.convertToVNI(data.getADDRESS()));
		data.setNAME_HOSPITAL(Vni2Uni.convertToVNI(data.getNAME_HOSPITAL()));
		data.setNOTE(Vni2Uni.convertToVNI(data.getNOTE()));
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
        
        ID_PROVINCE_CaptionLabel = new nextapp.echo2.app.Label();
        ID_PROVINCE_CaptionLabel.setText("N_HOSPITAL.ID_PROVINCE");
        rootLayout.add(ID_PROVINCE_CaptionLabel);
        ID_PROVINCE_DscField3 = new SelectField();
        ID_PROVINCE_DscField3.setWidth(new Extent(160));
        ID_PROVINCE_DscField3.setId("ID_PROVINCE_DscField3");
        rootLayout.add(ID_PROVINCE_DscField3);
        
        NAME_HOSPITAL_CaptionLabel = new nextapp.echo2.app.Label();
        NAME_HOSPITAL_CaptionLabel.setText("N_HOSPITAL.NAME_HOSPITAL");
        rootLayout.add(NAME_HOSPITAL_CaptionLabel);
        NAME_HOSPITAL_DscField4 = new dsc.echo2app.component.DscField();
        NAME_HOSPITAL_DscField4.setId("NAME_HOSPITAL_DscField4");
        rootLayout.add(NAME_HOSPITAL_DscField4);
        
        ID_HOS_CaptionLabel = new nextapp.echo2.app.Label();
        ID_HOS_CaptionLabel.setText("N_HOSPITAL.ID_HOS");
        rootLayout.add(ID_HOS_CaptionLabel);
        ID_HOS_DscField2 = new dsc.echo2app.component.DscField();
        ID_HOS_DscField2.setId("ID_HOS_DscField2");
        rootLayout.add(ID_HOS_DscField2);
      
        ADDRESS_CaptionLabel = new nextapp.echo2.app.Label();
        ADDRESS_CaptionLabel.setText("N_HOSPITAL.ADDRESS");
        rootLayout.add(ADDRESS_CaptionLabel);
        ADDRESS_DscField1 = new dsc.echo2app.component.DscField();
        ADDRESS_DscField1.setId("ADDRESS_DscField1");
        rootLayout.add(ADDRESS_DscField1);
        
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_HOSPITAL.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField5 = new dsc.echo2app.component.DscField();
        NOTE_DscField5.setId("NOTE_DscField5");
        rootLayout.add(NOTE_DscField5);
	}

}
