package ds.program.fvhr.ui;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.SelectField;
import ds.program.fvhr.domain.N_N_POSS;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.PropertyEditors;

public class N_POSSMDataContent extends DataContent {

	private nextapp.echo2.app.Grid rootLayout;

	private nextapp.echo2.app.Label ID_POSS_CaptionLabel;

	private dsc.echo2app.component.DscField ID_POSS_DscField1;

	private nextapp.echo2.app.Label ID_POSS_LEVEL_CaptionLabel;

	private SelectField ID_POSS_LEVEL_DscField2;

	private nextapp.echo2.app.Label DATE_B_CaptionLabel;

	private dsc.echo2app.component.DscDateField DATE_B_DscDateField1;

	private nextapp.echo2.app.Label DATE_E_CaptionLabel;

	private dsc.echo2app.component.DscDateField DATE_E_DscDateField2;

	private nextapp.echo2.app.Label MONEY_CaptionLabel;

	private dsc.echo2app.component.DscField MONEY_DscField3;

	private nextapp.echo2.app.Label NAME_POSS_CaptionLabel;

	private dsc.echo2app.component.DscField NAME_POSS_DscField4;

	private nextapp.echo2.app.Label NOTE_CaptionLabel;

	private dsc.echo2app.component.DscField NOTE_DscField5;

	OBJ_UTILITY objUtil;

	/**
	 * Creates a new <code>N_POSSMDataContent</code>.
	 */
	public N_POSSMDataContent() {
		super();
		objUtil = new OBJ_UTILITY();
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
		return N_N_POSS.class;
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
		// N_N_POSS data = (N_N_POSS) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		// N_N_POSS data = (N_N_POSS) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			// 使用者可在以下區域撰寫內容合理值的判斷程式碼
			// 此單頭為readonly 直接return true
		}
		return ret;
	}

	/*
	 * 自動編號 <b>必須繼承改寫</b>
	 * 
	 * @see dsc.echo2app.program.DataContent#autoPrimaryKeyValue()
	 * 
	 * @Override protected boolean autoPrimaryKeyValue() {
	 * //使用者可以在以下區域撰寫產生新序號的程式碼並將新序號放入dataObject //N_N_POSS data = (N_N_POSS)
	 * getDataObject(); //Connection conn =
	 * Application.getApp().getConnection(); //Map keys = new HashMap(); //Map
	 * filters = new HashMap(); //String newValue =
	 * KeyNumberGenerator.generateNumber(conn, // "$Y{2}$M{2}$D{2}$S{4}", // 1, //
	 * "N_N_POSS", "{ID_FIELD}", // keys, filters, // true, false);
	 * 
	 * //產生新序號並放入dataObject //data.set{ID_FIELD}(newValue); return true; }
	 *  /* 元件初始化Method <<必須繼承改寫>>
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
		this.getUIDataObjectBinder().registerCustomEditor(N_N_POSS.class,
				"DATE_B", PropertyEditors.createDateEditor("dd/MM/yyyy"));
		this.getUIDataObjectBinder().registerCustomEditor(N_N_POSS.class,
				"DATE_E", PropertyEditors.createDateEditor("dd/MM/yyyy"));
		this.getUIDataObjectBinder().registerCustomEditor(N_N_POSS.class,
				"ID_POSS_LEVEL", objUtil.Get_Editor_Level());
	}

	private void bindUI() {
		// ----------------------------------------------------------------------------------
		// 以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
		getUIDataObjectBinder().addBindMap("ID_POSS", ID_POSS_DscField1,
				ID_POSS_CaptionLabel);
		getUIDataObjectBinder().addBindMap("ID_POSS_LEVEL",
				ID_POSS_LEVEL_DscField2, ID_POSS_LEVEL_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DATE_B", DATE_B_DscDateField1,
				DATE_B_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DATE_E", DATE_E_DscDateField2,
				DATE_E_CaptionLabel);
		getUIDataObjectBinder().addBindMap("MONEY", MONEY_DscField3,
				MONEY_CaptionLabel);
		getUIDataObjectBinder().addBindMap("NAME_POSS", NAME_POSS_DscField4,
				NAME_POSS_CaptionLabel);
		getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField5,
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
		// N_N_POSS data = (N_N_POSS) getDataObject();
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
		// N_N_POSS data = (N_N_POSS) getDataObject();
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
		ID_POSS_CaptionLabel = new nextapp.echo2.app.Label();
		ID_POSS_CaptionLabel.setText("N_N_POSS.ID_POSS");
		rootLayout.add(ID_POSS_CaptionLabel);
		ID_POSS_DscField1 = new dsc.echo2app.component.DscField();
		ID_POSS_DscField1.setId("ID_POSS_DscField1");
		rootLayout.add(ID_POSS_DscField1);

		ID_POSS_LEVEL_CaptionLabel = new nextapp.echo2.app.Label();
		ID_POSS_LEVEL_CaptionLabel.setText("N_N_POSS.ID_POSS_LEVEL");
		rootLayout.add(ID_POSS_LEVEL_CaptionLabel);
		ID_POSS_LEVEL_DscField2 = new SelectField();
		ID_POSS_LEVEL_DscField2.setId("ID_POSS_LEVEL_DscField2");
		ID_POSS_LEVEL_DscField2.setSelectedIndex(0);
		ID_POSS_LEVEL_DscField2.setWidth(new Extent(160));
		rootLayout.add(ID_POSS_LEVEL_DscField2);

		DATE_B_CaptionLabel = new nextapp.echo2.app.Label();
		DATE_B_CaptionLabel.setText("N_N_POSS.DATE_B");
		rootLayout.add(DATE_B_CaptionLabel);
		DATE_B_DscDateField1 = new dsc.echo2app.component.DscDateField();
		DATE_B_DscDateField1.setId("DATE_B_DscDateField1");
		DATE_B_DscDateField1.setDateFormat(OBJ_UTILITY.Get_format_date());
		rootLayout.add(DATE_B_DscDateField1);

		DATE_E_CaptionLabel = new nextapp.echo2.app.Label();
		DATE_E_CaptionLabel.setText("N_N_POSS.DATE_E");
		rootLayout.add(DATE_E_CaptionLabel);
		DATE_E_DscDateField2 = new dsc.echo2app.component.DscDateField();
		DATE_E_DscDateField2.setDateFormat(OBJ_UTILITY.Get_format_date());
		DATE_E_DscDateField2.setId("DATE_E_DscDateField2");
		rootLayout.add(DATE_E_DscDateField2);

		MONEY_CaptionLabel = new nextapp.echo2.app.Label();
		MONEY_CaptionLabel.setText("N_N_POSS.MONEY");
		rootLayout.add(MONEY_CaptionLabel);
		MONEY_DscField3 = new dsc.echo2app.component.DscField();
		MONEY_DscField3.setId("MONEY_DscField3");
		rootLayout.add(MONEY_DscField3);

		NAME_POSS_CaptionLabel = new nextapp.echo2.app.Label();
		NAME_POSS_CaptionLabel.setText("N_N_POSS.NAME_POSS");
		rootLayout.add(NAME_POSS_CaptionLabel);
		NAME_POSS_DscField4 = new dsc.echo2app.component.DscField();
		NAME_POSS_DscField4.setId("NAME_POSS_DscField4");
		rootLayout.add(NAME_POSS_DscField4);

		NOTE_CaptionLabel = new nextapp.echo2.app.Label();
		NOTE_CaptionLabel.setText("N_N_POSS.NOTE");
		rootLayout.add(NOTE_CaptionLabel);
		NOTE_DscField5 = new dsc.echo2app.component.DscField();
		NOTE_DscField5.setId("NOTE_DscField5");
		rootLayout.add(NOTE_DscField5);
	}

}
