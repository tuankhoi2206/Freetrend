package ds.program.fvhr.ngan.ui.Change_Depsn;

import java.beans.PropertyEditor;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import ds.program.fvhr.domain.N_CHDEPSN;
import ds.program.fvhr.domain.N_EMP_INFO;
import ds.program.fvhr.util.OBJ_UTILITY;

public class N_CHDEPSNMDataContent extends DataContent {

	private nextapp.echo2.app.Grid rootLayout;
	private nextapp.echo2.app.Label EMPSN_CaptionLabel;
	private dsc.echo2app.component.DscField EMPSN_DscField1;
	private nextapp.echo2.app.Label DATES_CaptionLabel;
	private dsc.echo2app.component.DscDateField DATES_DscDateField1;
	private nextapp.echo2.app.Label DEPSN_CaptionLabel;
	private dsc.echo2app.component.DscField DEPSN_DscField2;
	// khoi test
	private dsc.echo2app.component.DscField DEPARTMENT_DscField2;
	private nextapp.echo2.app.Label DEPARTMENT_CaptionLabel;
	// end khoi
	private nextapp.echo2.app.Label DEPSN_OLD_CaptionLabel;
	private dsc.echo2app.component.DscField DEPSN_OLD_DscField3;
	private nextapp.echo2.app.Label MAQL_CaptionLabel;
	private dsc.echo2app.component.DscField MAQL_DscField4;
	private nextapp.echo2.app.Label MAQL_OLD_CaptionLabel;
	private dsc.echo2app.component.DscField MAQL_OLD_DscField5;

	// khoi
	// private nextapp.echo2.app.Label STATUS_CaptionLabel;
	private dsc.echo2app.component.DscField STATUS_DscField6;
	private nextapp.echo2.app.Label NOTE_CaptionLabel;
	private dsc.echo2app.component.DscField NOTE_DscField7;
	// end khoi
	private nextapp.echo2.app.Label TRUCTIEP_SX_OLD_CaptionLabel;
	private dsc.echo2app.component.DscField TRUCTIEP_SX_OLD_DscField8;
	private nextapp.echo2.app.Label TRUCTIEP_SX_NEW_CaptionLabel;
	private dsc.echo2app.component.DscField TRUCTIEP_SX_NEW_DscField9;

	/**
	 * Creates a new <code>N_CHDEPSNMDataContent</code>.
	 */
	public N_CHDEPSNMDataContent() {
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
	public Class getDataObjectClass() {
		return N_CHDEPSN.class;
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
		// N_CHDEPSN data = (N_CHDEPSN) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		// N_CHDEPSN data = (N_CHDEPSN) getDataObject();
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
	 */
	/*
	 * @Override protected boolean autoPrimaryKeyValue() {
	 * //使用者可以在以下區域撰寫產生新序號的程式碼並將新序號放入dataObject //N_CHDEPSN data = (N_CHDEPSN)
	 * getDataObject(); //Connection conn =
	 * Application.getApp().getConnection(); //Map keys = new HashMap(); //Map
	 * filters = new HashMap(); //String newValue =
	 * KeyNumberGenerator.generateNumber(conn, // "$Y{2}$M{2}$D{2}$S{4}", // 1,
	 * // "N_CHDEPSN", "{ID_FIELD}", // keys, filters, // true, false);
	 * 
	 * //產生新序號並放入dataObject //data.set{ID_FIELD}(newValue); return true; }
	 */

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

		dsc.echo2app.propertyeditors.MappingPropertyEditor editor = null;
		getUIDataObjectBinder().registerCustomEditor(N_CHDEPSN.class,
				"TRUCTIEP_SX_OLD", statusEditor());
		getUIDataObjectBinder().registerCustomEditor(N_CHDEPSN.class,
				"TRUCTIEP_SX_NEW", statusEditor());
		this.getUIDataObjectBinder().registerCustomEditor(N_CHDEPSN.class,
				"DATES", PropertyEditors.createDateEditor("dd/MM/yyyy"));
	}

	private MappingPropertyEditor statusEditor() {
		MappingPropertyEditor e = new MappingPropertyEditor();
		MappingPropertyEditor e1 = new MappingPropertyEditor();
		/*
		 * e.put("Trực tiếp sản xuất","Y"); e.put("Gián tiếp sản xuất","N");
		 * 
		 * e1.put("Trực tiếp sản xuất","Y"); e1.put("Gián tiếp sản xuất","N");
		 */
		// HA
		e.put("Trực tiếp sản xuất", "TT");
		e.put("Gián tiếp sản xuất", "GT");
		e.put("Giày mẫu", "GM");
		e.put("Văn phòng", "VP");

		e1.put("Trực tiếp sản xuất", "TT");
		e1.put("Gián tiếp sản xuất", "GT");
		e1.put("Giày mẫu", "GM");
		e1.put("Văn phòng", "VP");
		return e;
	}

	private void bindUI() {
		// ----------------------------------------------------------------------------------
		// 以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
		getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1,
				EMPSN_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DATES", DATES_DscDateField1,
				DATES_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DEPSN", DEPSN_DscField2,
				DEPSN_CaptionLabel);

		// khoi test
		//department  DEPARTMENT
		
		getUIDataObjectBinder().addBindMap("DEPSN", DEPARTMENT_DscField2,
				DEPARTMENT_CaptionLabel);
		// end

		getUIDataObjectBinder().addBindMap("DEPSN_OLD", DEPSN_OLD_DscField3,
				DEPSN_OLD_CaptionLabel);
		getUIDataObjectBinder().addBindMap("MAQL", MAQL_DscField4,
				MAQL_CaptionLabel);
		getUIDataObjectBinder().addBindMap("MAQL_OLD", MAQL_OLD_DscField5,
				MAQL_OLD_CaptionLabel);
		// getUIDataObjectBinder().addBindMap("STATUS", STATUS_DscField6,
		// STATUS_CaptionLabel);
		// getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField7,
		// NOTE_CaptionLabel);
		getUIDataObjectBinder().addBindMap("TRUCTIEP_SX_OLD",
				TRUCTIEP_SX_OLD_DscField8, TRUCTIEP_SX_OLD_CaptionLabel);
		getUIDataObjectBinder().addBindMap("TRUCTIEP_SX_NEW",
				TRUCTIEP_SX_NEW_DscField9, TRUCTIEP_SX_NEW_CaptionLabel);
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
		// N_CHDEPSN data = (N_CHDEPSN) getDataObject();
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
		// N_CHDEPSN data = (N_CHDEPSN) getDataObject();
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
		EMPSN_CaptionLabel.setText("N_CHDEPSN.EMPSN");
		rootLayout.add(EMPSN_CaptionLabel);
		EMPSN_DscField1 = new dsc.echo2app.component.DscField();
		EMPSN_DscField1.setId("EMPSN_DscField1");
		rootLayout.add(EMPSN_DscField1);
		DATES_CaptionLabel = new nextapp.echo2.app.Label();
		DATES_CaptionLabel.setText("N_CHDEPSN.DATES");
		rootLayout.add(DATES_CaptionLabel);

		DATES_DscDateField1 = new dsc.echo2app.component.DscDateField();
		DATES_DscDateField1.setId("DATES_DscDateField1");
		DATES_DscDateField1.setDateFormat(OBJ_UTILITY.Get_format_date());
		rootLayout.add(DATES_DscDateField1);

		// ===
		DEPSN_CaptionLabel = new nextapp.echo2.app.Label();
		DEPSN_CaptionLabel.setText("N_CHDEPSN.DEPSN");
		rootLayout.add(DEPSN_CaptionLabel);

		DEPSN_DscField2 = new dsc.echo2app.component.DscField();
		DEPSN_DscField2.setId("DEPSN_DscField2");
		rootLayout.add(DEPSN_DscField2);
		// ===

		// khoi test
		DEPARTMENT_CaptionLabel = new nextapp.echo2.app.Label();
		DEPSN_CaptionLabel.setText("N_CHDEPSN.DEPARTMENT");
		rootLayout.add(DEPARTMENT_CaptionLabel);

		DEPARTMENT_DscField2 = new dsc.echo2app.component.DscField();
		DEPSN_DscField2.setId("DEPARTMENT_DscField2");
		rootLayout.add(DEPARTMENT_DscField2);
		// end

		DEPSN_OLD_CaptionLabel = new nextapp.echo2.app.Label();
		DEPSN_OLD_CaptionLabel.setText("N_CHDEPSN.DEPSN_OLD");
		rootLayout.add(DEPSN_OLD_CaptionLabel);
		DEPSN_OLD_DscField3 = new dsc.echo2app.component.DscField();
		DEPSN_OLD_DscField3.setId("DEPSN_OLD_DscField3");
		rootLayout.add(DEPSN_OLD_DscField3);

		MAQL_CaptionLabel = new nextapp.echo2.app.Label();
		MAQL_CaptionLabel.setText("N_CHDEPSN.MAQL");
		rootLayout.add(MAQL_CaptionLabel);
		MAQL_DscField4 = new dsc.echo2app.component.DscField();
		MAQL_DscField4.setId("MAQL_DscField4");
		rootLayout.add(MAQL_DscField4);
		MAQL_OLD_CaptionLabel = new nextapp.echo2.app.Label();
		MAQL_OLD_CaptionLabel.setText("N_CHDEPSN.MAQL_OLD");
		rootLayout.add(MAQL_OLD_CaptionLabel);
		MAQL_OLD_DscField5 = new dsc.echo2app.component.DscField();
		MAQL_OLD_DscField5.setId("MAQL_OLD_DscField5");
		rootLayout.add(MAQL_OLD_DscField5);

		TRUCTIEP_SX_OLD_CaptionLabel = new nextapp.echo2.app.Label();
		TRUCTIEP_SX_OLD_CaptionLabel.setText("N_CHDEPSN.TRUCTIEP_SX_OLD");
		rootLayout.add(TRUCTIEP_SX_OLD_CaptionLabel);
		TRUCTIEP_SX_OLD_DscField8 = new dsc.echo2app.component.DscField();
		TRUCTIEP_SX_OLD_DscField8.setId("TRUCTIEP_SX_OLD_DscField8");
		rootLayout.add(TRUCTIEP_SX_OLD_DscField8);

		TRUCTIEP_SX_NEW_CaptionLabel = new nextapp.echo2.app.Label();
		TRUCTIEP_SX_NEW_CaptionLabel.setText("N_CHDEPSN.TRUCTIEP_SX_NEW");
		rootLayout.add(TRUCTIEP_SX_NEW_CaptionLabel);
		TRUCTIEP_SX_NEW_DscField9 = new dsc.echo2app.component.DscField();
		TRUCTIEP_SX_NEW_DscField9.setId("TRUCTIEP_SX_NEW_DscField9");
		rootLayout.add(TRUCTIEP_SX_NEW_DscField9);
	}

}
