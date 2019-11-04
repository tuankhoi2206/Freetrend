package ds.program.users.ui;

import java.util.Date;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;

public class DSPB02MDataContent extends DataContent {

	private nextapp.echo2.app.Grid rootLayout;

	private nextapp.echo2.app.Label PB_USERID_CaptionLabel;

	private dsc.echo2app.component.DscField PB_USERID_DscField1;

	private nextapp.echo2.app.Label PB_NAME_CaptionLabel;

	private dsc.echo2app.component.DscField PB_NAME_DscField2;

	private nextapp.echo2.app.Label PB_CNAME_CaptionLabel;

	private dsc.echo2app.component.DscField PB_CNAME_DscField3;

	private nextapp.echo2.app.Label PB_PASS_CaptionLabel;

	private dsc.echo2app.component.DscField PB_PASS_DscField4;

	private nextapp.echo2.app.Label PB_DPNO_CaptionLabel;

	private dsc.echo2app.component.DscField PB_DPNO_DscField5;

	private nextapp.echo2.app.Label PB_EMAIL_CaptionLabel;

	private dsc.echo2app.component.DscField PB_EMAIL_DscField6;

	private nextapp.echo2.app.Label PB_EMPASSWD_CaptionLabel;

	private dsc.echo2app.component.DscField PB_EMPASSWD_DscField7;

	private nextapp.echo2.app.Label PB_EMUSER_CaptionLabel;

	private dsc.echo2app.component.DscField PB_EMUSER_DscField8;

	private nextapp.echo2.app.Label PB_FANO_CaptionLabel;

	private dsc.echo2app.component.DscField PB_FANO_DscField9;

	private nextapp.echo2.app.Label UP_DATE_CaptionLabel;

	private dsc.echo2app.component.DscDateField UP_DATE_DscDateField1;

	private nextapp.echo2.app.Label UP_USER_CaptionLabel;

	private dsc.echo2app.component.DscField UP_USER_DscField10;

	/**
	 * Creates a new <code>DSPB02MDataContent</code>.
	 */
	public DSPB02MDataContent() {
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
			UP_USER_DscField10.setEnabled(false);
			UP_USER_DscField10.setStyleName("Default.ReadonlyField");
			// 以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
			// ----------------------------------------------------------------------------------
			if (this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				PB_USERID_DscField1.setEnabled(true);
			} else {
				PB_USERID_DscField1.setEnabled(false);
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
		return DSPB02.class;
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
		// DSPB02 data = (DSPB02) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		DSPB02 data = (DSPB02) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			if (getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				IGenericDAO<DSPB02, String> dao = Application.getApp().getDao(
						DSPB02.class);
				if (dao.findById(data.getPB_USERID()) != null) {
					setErrorMessage("Tên này đã đăng ký rồi.");
					return false;
				}
			}
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
		getUIDataObjectBinder().addBindMap("PB_USERID", PB_USERID_DscField1,
				PB_USERID_CaptionLabel);
		getUIDataObjectBinder().addBindMap("PB_NAME", PB_NAME_DscField2,
				PB_NAME_CaptionLabel);
		getUIDataObjectBinder().addBindMap("PB_CNAME", PB_CNAME_DscField3,
				PB_CNAME_CaptionLabel);
		getUIDataObjectBinder().addBindMap("PB_PASS", PB_PASS_DscField4,
				PB_PASS_CaptionLabel);
		getUIDataObjectBinder().addBindMap("PB_DPNO", PB_DPNO_DscField5,
				PB_DPNO_CaptionLabel);
		getUIDataObjectBinder().addBindMap("PB_EMAIL", PB_EMAIL_DscField6,
				PB_EMAIL_CaptionLabel);
		getUIDataObjectBinder().addBindMap("PB_EMPASSWD",
				PB_EMPASSWD_DscField7, PB_EMPASSWD_CaptionLabel);
		getUIDataObjectBinder().addBindMap("PB_EMUSER", PB_EMUSER_DscField8,
				PB_EMUSER_CaptionLabel);
		getUIDataObjectBinder().addBindMap("PB_FANO", PB_FANO_DscField9,
				PB_FANO_CaptionLabel);
		getUIDataObjectBinder().addBindMap("UP_DATE", UP_DATE_DscDateField1,
				UP_DATE_CaptionLabel);
		getUIDataObjectBinder().addBindMap("UP_USER", UP_USER_DscField10,
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
		// DSPB02 data = (DSPB02) getDataObject();
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
		DSPB02 data = (DSPB02) getDataObject();
		data.setUP_DATE(new Date());
		data.setUP_USER(getProgram().getLoginInfo().getUserID());
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
		PB_USERID_CaptionLabel = new nextapp.echo2.app.Label();
		PB_USERID_CaptionLabel.setText("PB_USERID");
		rootLayout.add(PB_USERID_CaptionLabel);
		PB_USERID_DscField1 = new dsc.echo2app.component.DscField();
		PB_USERID_DscField1.setId("PB_USERID_DscField1");
		rootLayout.add(PB_USERID_DscField1);
		PB_NAME_CaptionLabel = new nextapp.echo2.app.Label();
		PB_NAME_CaptionLabel.setText("PB_NAME");
		rootLayout.add(PB_NAME_CaptionLabel);
		PB_NAME_DscField2 = new dsc.echo2app.component.DscField();
		PB_NAME_DscField2.setId("PB_NAME_DscField2");
		rootLayout.add(PB_NAME_DscField2);
		PB_CNAME_CaptionLabel = new nextapp.echo2.app.Label();
		PB_CNAME_CaptionLabel.setText("PB_CNAME");
		rootLayout.add(PB_CNAME_CaptionLabel);
		PB_CNAME_DscField3 = new dsc.echo2app.component.DscField();
		PB_CNAME_DscField3.setId("PB_CNAME_DscField3");
		rootLayout.add(PB_CNAME_DscField3);
		PB_PASS_CaptionLabel = new nextapp.echo2.app.Label();
		PB_PASS_CaptionLabel.setText("PB_PASS");
		rootLayout.add(PB_PASS_CaptionLabel);
		PB_PASS_DscField4 = new dsc.echo2app.component.DscField();
		PB_PASS_DscField4.setId("PB_PASS_DscField4");
		rootLayout.add(PB_PASS_DscField4);
		PB_DPNO_CaptionLabel = new nextapp.echo2.app.Label();
		PB_DPNO_CaptionLabel.setText("PB_DPNO");
		rootLayout.add(PB_DPNO_CaptionLabel);
		PB_DPNO_DscField5 = new dsc.echo2app.component.DscField();
		PB_DPNO_DscField5.setId("PB_DPNO_DscField5");
		rootLayout.add(PB_DPNO_DscField5);
		PB_EMAIL_CaptionLabel = new nextapp.echo2.app.Label();
		PB_EMAIL_CaptionLabel.setText("PB_EMAIL");
		rootLayout.add(PB_EMAIL_CaptionLabel);
		PB_EMAIL_DscField6 = new dsc.echo2app.component.DscField();
		PB_EMAIL_DscField6.setId("PB_EMAIL_DscField6");
		rootLayout.add(PB_EMAIL_DscField6);
		PB_EMPASSWD_CaptionLabel = new nextapp.echo2.app.Label();
		PB_EMPASSWD_CaptionLabel.setText("PB_EMPASSWD");
		rootLayout.add(PB_EMPASSWD_CaptionLabel);
		PB_EMPASSWD_DscField7 = new dsc.echo2app.component.DscField();
		PB_EMPASSWD_DscField7.setId("PB_EMPASSWD_DscField7");
		rootLayout.add(PB_EMPASSWD_DscField7);
		PB_EMUSER_CaptionLabel = new nextapp.echo2.app.Label();
		PB_EMUSER_CaptionLabel.setText("PB_EMUSER");
		rootLayout.add(PB_EMUSER_CaptionLabel);
		PB_EMUSER_DscField8 = new dsc.echo2app.component.DscField();
		PB_EMUSER_DscField8.setId("PB_EMUSER_DscField8");
		rootLayout.add(PB_EMUSER_DscField8);
		PB_FANO_CaptionLabel = new nextapp.echo2.app.Label();
		PB_FANO_CaptionLabel.setText("PB_FANO");
		rootLayout.add(PB_FANO_CaptionLabel);
		PB_FANO_DscField9 = new dsc.echo2app.component.DscField();
		PB_FANO_DscField9.setId("PB_FANO_DscField9");
		rootLayout.add(PB_FANO_DscField9);
		UP_DATE_CaptionLabel = new nextapp.echo2.app.Label();
		UP_DATE_CaptionLabel.setText("UP_DATE");
		rootLayout.add(UP_DATE_CaptionLabel);
		UP_DATE_DscDateField1 = new dsc.echo2app.component.DscDateField();
		UP_DATE_DscDateField1.setId("UP_DATE_DscDateField1");
		rootLayout.add(UP_DATE_DscDateField1);
		UP_USER_CaptionLabel = new nextapp.echo2.app.Label();
		UP_USER_CaptionLabel.setText("UP_USER");
		rootLayout.add(UP_USER_CaptionLabel);
		UP_USER_DscField10 = new dsc.echo2app.component.DscField();
		UP_USER_DscField10.setId("UP_USER_DscField10");
		rootLayout.add(UP_USER_DscField10);
	}

}
