package ds.program.fvhr.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import ds.program.fvhr.domain.N_LIST_DEPENDENT;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import fv.util.Vni2Uni;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscDateField;

public class ListDependent01MDataContent extends DataContent {

	private nextapp.echo2.app.Grid rootLayout;

	private nextapp.echo2.app.Label EMPSN_CaptionLabel;

	private dsc.echo2app.component.DscField EMPSN_DscField1;

	private nextapp.echo2.app.Label DEPSN_CaptionLabel;

	private dsc.echo2app.component.DscField DEPSN_DscField2;

	private nextapp.echo2.app.Label DEPENDENT_CaptionLabel;

	private dsc.echo2app.component.DscField DEPENDENT_DscField3;

	private nextapp.echo2.app.Label USER_UPDATED_CaptionLabel;

	private dsc.echo2app.component.DscField USER_UPDATED_DscField4;

	private nextapp.echo2.app.Label DATE_UPDATED_CaptionLabel;

	private dsc.echo2app.component.DscField DATE_UPDATED_DscField5;

	private nextapp.echo2.app.Label NOTE_CaptionLabel;

	private dsc.echo2app.component.DscField NOTE_DscField6;

	/**
	 * Creates a new <code>ListDependent01MDataContent</code>.
	 */
	public ListDependent01MDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	/*
	 * UI資料欄位屬性更新，一般是資料模式變換時會呼叫此method <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.DataContent#doUIRefresh()
	 */
	@Override
	public void doUIRefresh() {
		super.doUIRefresh();
		if ((this.getProgram().getDataMode() == IProgram.DATAMODE_NONE)
				|| (this.getProgram().getDataMode() == IProgram.DATAMODE_BROWSER)) {
			rootLayout.setEnabled(false);
		} else {
			rootLayout.setEnabled(true);
			// ----------------------------------------------------------------------------------
			// 以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
			USER_UPDATED_DscField4.setEnabled(false);
			USER_UPDATED_DscField4.setStyleName("Default.ReadonlyField");
			DATE_UPDATED_DscField5.setEnabled(false);
			DATE_UPDATED_DscField5.setStyleName("Default.ReadonlyField");
			// 以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
			// ----------------------------------------------------------------------------------
			if (this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				// 新增時，設定哪些元件Enable/Disable
			} else {
				// 修改時，設定哪些元件Enable/Disable
			}
		}

		// 7.<資料權限管控>

		// 自動設定元件風格
		getUIDataObjectBinder().doDataBindUIStyle();
	}

	/*
	 * 取得該畫面的DataObject Class <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.DataContent#getDataObjectClass()
	 */
	@Override
	public Class getDataObjectClass() {
		return N_LIST_DEPENDENT.class;
	}

	/*
	 * 資料儲存前設定一些欄位內定值 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */
	@Override
	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		// 使用者可在以下區域填入控制欄位數值
		// N_LIST_DEPENDENT data = (N_LIST_DEPENDENT) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		// N_LIST_DEPENDENT data = (N_LIST_DEPENDENT) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			// 使用者可在以下區域撰寫內容合理值的判斷程式碼
			// 此單頭為readonly 直接return true
		}
		return ret;
	}

	/*
	 * 元件初始化Method <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.DataContent#doInit()
	 */
	@Override
	protected int doInit() {
		int nRet = super.doInit();

		// 1.註冊資料欄位之顯示方式
		registPropertyEditor();

		// 2.設定資料欄位與 UI之 Binding資訊
		bindUI();

		return nRet;
	}

	private void registPropertyEditor() {
		// 使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
		// TODO 請修改元件之PropertyEditor
		dsc.echo2app.propertyeditors.MappingPropertyEditor editor = null;
	}

	private void bindUI() {
		// ----------------------------------------------------------------------------------
		// 以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
		getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1,
				EMPSN_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DEPSN", DEPSN_DscField2,
				DEPSN_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DEPENDENT", DEPENDENT_DscField3,
				DEPENDENT_CaptionLabel);
		getUIDataObjectBinder().addBindMap("USER_UPDATED",
				USER_UPDATED_DscField4, USER_UPDATED_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DATE_UPDATED",
				DATE_UPDATED_DscField5, DATE_UPDATED_CaptionLabel);
		getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField6,
				NOTE_CaptionLabel);
		// 以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
		// ----------------------------------------------------------------------------------
	}

	/*
	 * 畫面Layout初始化Method
	 * 
	 * @see dsc.echo2app.program.DataContent#doLayout()
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
		// <<從此以下加入使用者程式>>
	}

	/*
	 * 做資料新增時內定預設數值 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.DataContent#doNewDefaulData()
	 */
	@Override
	protected void doNewDefaulData() {
		N_LIST_DEPENDENT data = (N_LIST_DEPENDENT) getDataObject();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		//data.setDATE_UPDATED(sdf.format(new Date()));
		data.setDATE_UPDATED("03/04/2013");
		String user = new String();
		user = getProgram().getLoginInfo().getUserID();
		data.setUSER_UPDATED(user);

	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {
		// N_LIST_DEPENDENT data = (N_LIST_DEPENDENT) getDataObject();
		N_LIST_DEPENDENT data = (N_LIST_DEPENDENT) getDataObject();
		data.setNOTE(Vni2Uni.convertToVNI(data.getNOTE()));
		if (this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
			autoPrimaryKeyValue();
		}
		// 使用者可在以下區域做儲存資料物件至DataObjectSet之前處理
		// <儲存前處理>
	}

	/**
	 * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		rootLayout = new Grid();
		rootLayout.setWidth(new Extent(100, Extent.PERCENT));
		rootLayout.setSize(4);
		add(rootLayout);
		EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
		EMPSN_CaptionLabel.setText("N_LIST_DEPENDENT.EMPSN");
		rootLayout.add(EMPSN_CaptionLabel);
		EMPSN_DscField1 = new dsc.echo2app.component.DscField();
		EMPSN_DscField1.setId("EMPSN_DscField1");
		rootLayout.add(EMPSN_DscField1);
		DEPSN_CaptionLabel = new nextapp.echo2.app.Label();
		DEPSN_CaptionLabel.setText("N_LIST_DEPENDENT.DEPSN");
		rootLayout.add(DEPSN_CaptionLabel);
		DEPSN_DscField2 = new dsc.echo2app.component.DscField();
		DEPSN_DscField2.setId("DEPSN_DscField2");
		rootLayout.add(DEPSN_DscField2);
		DEPENDENT_CaptionLabel = new nextapp.echo2.app.Label();
		DEPENDENT_CaptionLabel.setText("N_LIST_DEPENDENT.DEPENDENT");
		rootLayout.add(DEPENDENT_CaptionLabel);
		DEPENDENT_DscField3 = new dsc.echo2app.component.DscField();
		DEPENDENT_DscField3.setId("DEPENDENT_DscField3");
		rootLayout.add(DEPENDENT_DscField3);
		USER_UPDATED_CaptionLabel = new nextapp.echo2.app.Label();
		USER_UPDATED_CaptionLabel.setText("N_LIST_DEPENDENT.USER_UPDATED");
		rootLayout.add(USER_UPDATED_CaptionLabel);
		USER_UPDATED_DscField4 = new dsc.echo2app.component.DscField();
		USER_UPDATED_DscField4.setId("USER_UPDATED_DscField4");
		rootLayout.add(USER_UPDATED_DscField4);
		DATE_UPDATED_CaptionLabel = new nextapp.echo2.app.Label();
		DATE_UPDATED_CaptionLabel.setText("N_LIST_DEPENDENT.DATE_UPDATED");
		rootLayout.add(DATE_UPDATED_CaptionLabel);
		DATE_UPDATED_DscField5 = new dsc.echo2app.component.DscField();
		DATE_UPDATED_DscField5.setId("DATE_UPDATED_DscField5");
		rootLayout.add(DATE_UPDATED_DscField5);
		NOTE_CaptionLabel = new nextapp.echo2.app.Label();
		NOTE_CaptionLabel.setText("N_LIST_DEPENDENT.NOTE");
		rootLayout.add(NOTE_CaptionLabel);
		NOTE_DscField6 = new dsc.echo2app.component.DscField();
		NOTE_DscField6.setId("NOTE_DscField6");
		rootLayout.add(NOTE_DscField6);
	}

}

