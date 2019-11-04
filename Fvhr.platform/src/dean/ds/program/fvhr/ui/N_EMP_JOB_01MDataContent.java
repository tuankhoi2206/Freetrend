package ds.program.fvhr.ui;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import ds.program.fvhr.domain.N_N_EMP_JOB;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;

public class N_EMP_JOB_01MDataContent extends DataContent {

	private nextapp.echo2.app.Grid rootLayout;

	private nextapp.echo2.app.Label EMPSN_CaptionLabel;

	private dsc.echo2app.component.DscField EMPSN_DscField1;

	private nextapp.echo2.app.Label ID_JOB_CaptionLabel;

	private dsc.echo2app.component.DscField ID_JOB_DscField2;

	private nextapp.echo2.app.Label DATE_B_CaptionLabel;

	private dsc.echo2app.component.DscDateField DATE_B_DscDateField1;

	private nextapp.echo2.app.Label DATE_E_CaptionLabel;

	private dsc.echo2app.component.DscDateField DATE_E_DscDateField2;

	private nextapp.echo2.app.Label NOTE_CaptionLabel;

	private dsc.echo2app.component.DscField NOTE_DscField3;

	/**
	 * Creates a new <code>N_EMP_JOB_01MDataContent</code>.
	 */
	public N_EMP_JOB_01MDataContent() {
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
		return N_N_EMP_JOB.class;
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
		// N_N_EMP_JOB data = (N_N_EMP_JOB) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		// N_N_EMP_JOB data = (N_N_EMP_JOB) getDataObject();
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
		getUIDataObjectBinder().addBindMap("ID_JOB", ID_JOB_DscField2,
				ID_JOB_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DATE_B", DATE_B_DscDateField1,
				DATE_B_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DATE_E", DATE_E_DscDateField2,
				DATE_E_CaptionLabel);
		getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField3,
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
		// N_N_EMP_JOB data = (N_N_EMP_JOB) getDataObject();
		// <如果要預先取號則在此加入>
		// autoPrimaryKeyValue();
		// 使用者可在以下區域填入新增時內定的預設數值
		// <內定值>
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {
		// N_N_EMP_JOB data = (N_N_EMP_JOB) getDataObject();
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
		rootLayout = new Grid();
		rootLayout.setWidth(new Extent(100, Extent.PERCENT));
		rootLayout.setSize(4);
		add(rootLayout);
		EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
		EMPSN_CaptionLabel.setText("N_N_EMP_JOB.EMPSN");
		rootLayout.add(EMPSN_CaptionLabel);
		EMPSN_DscField1 = new dsc.echo2app.component.DscField();
		EMPSN_DscField1.setId("EMPSN_DscField1");
		rootLayout.add(EMPSN_DscField1);
		ID_JOB_CaptionLabel = new nextapp.echo2.app.Label();
		ID_JOB_CaptionLabel.setText("N_N_EMP_JOB.ID_JOB");
		rootLayout.add(ID_JOB_CaptionLabel);
		ID_JOB_DscField2 = new dsc.echo2app.component.DscField();
		ID_JOB_DscField2.setId("ID_JOB_DscField2");
		rootLayout.add(ID_JOB_DscField2);
		DATE_B_CaptionLabel = new nextapp.echo2.app.Label();
		DATE_B_CaptionLabel.setText("N_N_EMP_JOB.DATE_B");
		rootLayout.add(DATE_B_CaptionLabel);
		DATE_B_DscDateField1 = new dsc.echo2app.component.DscDateField();
		DATE_B_DscDateField1.setId("DATE_B_DscDateField1");
		rootLayout.add(DATE_B_DscDateField1);
		DATE_E_CaptionLabel = new nextapp.echo2.app.Label();
		DATE_E_CaptionLabel.setText("N_N_EMP_JOB.DATE_E");
		rootLayout.add(DATE_E_CaptionLabel);
		DATE_E_DscDateField2 = new dsc.echo2app.component.DscDateField();
		DATE_E_DscDateField2.setId("DATE_E_DscDateField2");
		rootLayout.add(DATE_E_DscDateField2);
		NOTE_CaptionLabel = new nextapp.echo2.app.Label();
		NOTE_CaptionLabel.setText("N_N_EMP_JOB.NOTE");
		rootLayout.add(NOTE_CaptionLabel);
		NOTE_DscField3 = new dsc.echo2app.component.DscField();
		NOTE_DscField3.setId("NOTE_DscField3");
		rootLayout.add(NOTE_DscField3);
	}

}
