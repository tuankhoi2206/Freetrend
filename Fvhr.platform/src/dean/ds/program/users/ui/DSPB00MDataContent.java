package ds.program.users.ui;

import java.util.Date;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import ds.program.users.domain.DSPB00;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;

public class DSPB00MDataContent extends DataContent {
	private static final long serialVersionUID = 1L;

	private nextapp.echo2.app.Grid rootLayout;

	private nextapp.echo2.app.Label PB_ID_CaptionLabel;

	private dsc.echo2app.component.DscField PB_ID_DscField1;

	private nextapp.echo2.app.Label PB_PRG_CaptionLabel;

	private dsc.echo2app.component.DscField PB_PRG_DscField2;

	private nextapp.echo2.app.Label PB_NA_CaptionLabel;

	private dsc.echo2app.component.DscField PB_NA_DscField3;

	private nextapp.echo2.app.Label MENUID_CaptionLabel;

	private dsc.echo2app.component.DscField MENUID_DscField4;

	private nextapp.echo2.app.Label PB_SYSID_CaptionLabel;

	private dsc.echo2app.component.DscField PB_SYSID_DscField5;

	private nextapp.echo2.app.Label PB_SUBID_CaptionLabel;

	private dsc.echo2app.component.DscField PB_SUBID_DscField6;

	private nextapp.echo2.app.Label ISOK_CaptionLabel;

	private dsc.echo2app.component.DscField ISOK_DscField7;

	private nextapp.echo2.app.Label UP_DATE_CaptionLabel;

	private dsc.echo2app.component.DscDateField UP_DATE_DscDateField1;

	private nextapp.echo2.app.Label UP_USER_CaptionLabel;

	private dsc.echo2app.component.DscField UP_USER_DscField8;

	/**
	 * Creates a new <code>DSPB00MDataContent</code>.
	 */
	public DSPB00MDataContent() {
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
			UP_DATE_DscDateField1.setEnabled(false);
			UP_DATE_DscDateField1.setStyleName("Default.ReadonlyField");
			UP_USER_DscField8.setEnabled(false);
			UP_USER_DscField8.setStyleName("Default.ReadonlyField");
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
		return DSPB00.class;
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
		// DSPB00 data = (DSPB00) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		// DSPB00 data = (DSPB00) getDataObject();
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
	}

	private void bindUI() {
		// ----------------------------------------------------------------------------------
		// 以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
		getUIDataObjectBinder().addBindMap("PB_ID", PB_ID_DscField1,
				PB_ID_CaptionLabel);
		getUIDataObjectBinder().addBindMap("PB_PRG", PB_PRG_DscField2,
				PB_PRG_CaptionLabel);
		getUIDataObjectBinder().addBindMap("PB_NA", PB_NA_DscField3,
				PB_NA_CaptionLabel);
		getUIDataObjectBinder().addBindMap("MENUID", MENUID_DscField4,
				MENUID_CaptionLabel);
		getUIDataObjectBinder().addBindMap("PB_SYSID", PB_SYSID_DscField5,
				PB_SYSID_CaptionLabel);
		getUIDataObjectBinder().addBindMap("PB_SUBID", PB_SUBID_DscField6,
				PB_SUBID_CaptionLabel);
		getUIDataObjectBinder().addBindMap("ISOK", ISOK_DscField7,
				ISOK_CaptionLabel);
		getUIDataObjectBinder().addBindMap("UP_DATE", UP_DATE_DscDateField1,
				UP_DATE_CaptionLabel);
		getUIDataObjectBinder().addBindMap("UP_USER", UP_USER_DscField8,
				UP_USER_CaptionLabel);
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
		// DSPB00 data = (DSPB00) getDataObject();
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
		DSPB00 data = (DSPB00) getDataObject();
		data.setUP_USER(getProgram().getLoginInfo().getUserID());
		data.setUP_DATE(new Date());
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
		PB_ID_CaptionLabel = new nextapp.echo2.app.Label();
		PB_ID_CaptionLabel.setText("SYSID");
		rootLayout.add(PB_ID_CaptionLabel);
		PB_ID_DscField1 = new dsc.echo2app.component.DscField();
		PB_ID_DscField1.setId("PB_ID_DscField1");
		rootLayout.add(PB_ID_DscField1);
		PB_PRG_CaptionLabel = new nextapp.echo2.app.Label();
		PB_PRG_CaptionLabel.setText("PB_PRG");
		rootLayout.add(PB_PRG_CaptionLabel);
		PB_PRG_DscField2 = new dsc.echo2app.component.DscField();
		PB_PRG_DscField2.setId("PB_PRG_DscField2");
		rootLayout.add(PB_PRG_DscField2);
		PB_NA_CaptionLabel = new nextapp.echo2.app.Label();
		PB_NA_CaptionLabel.setText("PB_NA");
		rootLayout.add(PB_NA_CaptionLabel);
		PB_NA_DscField3 = new dsc.echo2app.component.DscField();
		PB_NA_DscField3.setId("PB_NA_DscField3");
		rootLayout.add(PB_NA_DscField3);
		MENUID_CaptionLabel = new nextapp.echo2.app.Label();
		MENUID_CaptionLabel.setText("MENUID");
		rootLayout.add(MENUID_CaptionLabel);
		MENUID_DscField4 = new dsc.echo2app.component.DscField();
		MENUID_DscField4.setId("MENUID_DscField4");
		rootLayout.add(MENUID_DscField4);
		PB_SYSID_CaptionLabel = new nextapp.echo2.app.Label();
		PB_SYSID_CaptionLabel.setText("PB_SYSID");
		rootLayout.add(PB_SYSID_CaptionLabel);
		PB_SYSID_DscField5 = new dsc.echo2app.component.DscField();
		PB_SYSID_DscField5.setId("PB_SYSID_DscField5");
		rootLayout.add(PB_SYSID_DscField5);
		PB_SUBID_CaptionLabel = new nextapp.echo2.app.Label();
		PB_SUBID_CaptionLabel.setText("DSPB00.PB_SUBID");
		rootLayout.add(PB_SUBID_CaptionLabel);
		PB_SUBID_DscField6 = new dsc.echo2app.component.DscField();
		PB_SUBID_DscField6.setId("PB_SUBID_DscField6");
		rootLayout.add(PB_SUBID_DscField6);
		ISOK_CaptionLabel = new nextapp.echo2.app.Label();
		ISOK_CaptionLabel.setText("DSPB00.ISOK");
		rootLayout.add(ISOK_CaptionLabel);
		ISOK_DscField7 = new dsc.echo2app.component.DscField();
		ISOK_DscField7.setId("ISOK_DscField7");
		rootLayout.add(ISOK_DscField7);
		UP_DATE_CaptionLabel = new nextapp.echo2.app.Label();
		UP_DATE_CaptionLabel.setText("UP_DATE");
		rootLayout.add(UP_DATE_CaptionLabel);
		UP_DATE_DscDateField1 = new dsc.echo2app.component.DscDateField();
		UP_DATE_DscDateField1.setId("UP_DATE_DscDateField1");
		rootLayout.add(UP_DATE_DscDateField1);
		UP_USER_CaptionLabel = new nextapp.echo2.app.Label();
		UP_USER_CaptionLabel.setText("UP_USER");
		rootLayout.add(UP_USER_CaptionLabel);
		UP_USER_DscField8 = new dsc.echo2app.component.DscField();
		UP_USER_DscField8.setId("UP_USER_DscField8");
		rootLayout.add(UP_USER_DscField8);
	}

}
