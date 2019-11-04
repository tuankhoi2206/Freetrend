package ds.program.fvhr.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.SelectField;
import ds.program.fvhr.domain.N_ATM_MANAGER;
import ds.program.fvhr.domain.N_EMPLOYEE;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import fv.util.Vni2Uni;

public class AtmManager01MDataContent extends DataContent {

	private nextapp.echo2.app.Grid rootLayout;

	private nextapp.echo2.app.Label EMPSN_CaptionLabel;

	private dsc.echo2app.component.DscField EMPSN_DscField1;

	private nextapp.echo2.app.Label CMND_CaptionLabel;

	private dsc.echo2app.component.DscField CMND_DscField2;

	private nextapp.echo2.app.Label ATM_CODE_CaptionLabel;

	private dsc.echo2app.component.DscField ATM_CODE_DscField3;

	private nextapp.echo2.app.Label NO_ACC_CaptionLabel;

	private dsc.echo2app.component.DscField NO_ACC_DscField4;

	private nextapp.echo2.app.Label BANK_NAME_CaptionLabel;

	private SelectField BANK_NAME_DscField5;

	private nextapp.echo2.app.Label USER_UPDATED_CaptionLabel;

	private dsc.echo2app.component.DscField USER_UPDATED_DscField6;

	private nextapp.echo2.app.Label DATE_UPDATE_CaptionLabel;

	private dsc.echo2app.component.DscField DATE_UPDATE_DscField7;

	private nextapp.echo2.app.Label NOTE_CaptionLabel;

	private dsc.echo2app.component.DscField NOTE_DscField8;

	private GetEmployeeInfo getEmployeeInfo;

	/**
	 * Creates a new <code>AtmManager01MDataContent</code>.
	 */
	public AtmManager01MDataContent() {
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
			USER_UPDATED_DscField6.setEnabled(false);
			USER_UPDATED_DscField6.setStyleName("Default.ReadonlyField");
			DATE_UPDATE_DscField7.setEnabled(false);
			DATE_UPDATE_DscField7.setStyleName("Default.ReadonlyField");
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
		return N_ATM_MANAGER.class;
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
		// N_ATM_MANAGER data = (N_ATM_MANAGER) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		// N_ATM_MANAGER data = (N_ATM_MANAGER) getDataObject();
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
		getUIDataObjectBinder().registerCustomEditor(N_ATM_MANAGER.class,
				"BANK_NAME", bankEditor());
	}

	private void bindUI() {
		// ----------------------------------------------------------------------------------
		// 以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
		getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1,
				EMPSN_CaptionLabel);
		getUIDataObjectBinder().addBindMap("CMND", CMND_DscField2,
				CMND_CaptionLabel);
		getUIDataObjectBinder().addBindMap("ATM_CODE", ATM_CODE_DscField3,
				ATM_CODE_CaptionLabel);
		getUIDataObjectBinder().addBindMap("NO_ACC", NO_ACC_DscField4,
				NO_ACC_CaptionLabel);
		getUIDataObjectBinder().addBindMap("BANK_NAME", BANK_NAME_DscField5,
				BANK_NAME_CaptionLabel);
		getUIDataObjectBinder().addBindMap("USER_UPDATED",
				USER_UPDATED_DscField6, USER_UPDATED_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DATE_UPDATE",
				DATE_UPDATE_DscField7, DATE_UPDATE_CaptionLabel);
		getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField8,
				NOTE_CaptionLabel);
		// 以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
		// ----------------------------------------------------------------------------------
	}

	private MappingPropertyEditor bankEditor() {
		MappingPropertyEditor e = new MappingPropertyEditor();
		e.put("Vietcombank", "VIETCOMBANK");
		e.put("BIDV Bank", "BIDVBANK");
		return e;
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
		N_ATM_MANAGER data = (N_ATM_MANAGER) getDataObject();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		data.setDATE_UPDATE(sdf.format(new Date()));
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
		N_ATM_MANAGER data = (N_ATM_MANAGER) getDataObject();
		data.setNOTE(Vni2Uni.convertToVNI(data.getNOTE()));
		data.setATM_CODE(Vni2Uni.convertToVNI(data.getATM_CODE()));
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
		EMPSN_CaptionLabel.setText("N_ATM_MANAGER.EMPSN");
		rootLayout.add(EMPSN_CaptionLabel);
		EMPSN_DscField1 = new dsc.echo2app.component.DscField();
		EMPSN_DscField1.setId("EMPSN_DscField1");
		rootLayout.add(EMPSN_DscField1);
		CMND_CaptionLabel = new nextapp.echo2.app.Label();
		CMND_CaptionLabel.setText("N_ATM_MANAGER.CMND");
		rootLayout.add(CMND_CaptionLabel);
		CMND_DscField2 = new dsc.echo2app.component.DscField();
		CMND_DscField2.setId("CMND_DscField2");
		rootLayout.add(CMND_DscField2);
		ATM_CODE_CaptionLabel = new nextapp.echo2.app.Label();
		ATM_CODE_CaptionLabel.setText("N_ATM_MANAGER.ATM_CODE");
		rootLayout.add(ATM_CODE_CaptionLabel);
		ATM_CODE_DscField3 = new dsc.echo2app.component.DscField();
		ATM_CODE_DscField3.setId("ATM_CODE_DscField3");
		rootLayout.add(ATM_CODE_DscField3);
		NO_ACC_CaptionLabel = new nextapp.echo2.app.Label();
		NO_ACC_CaptionLabel.setText("N_ATM_MANAGER.NO_ACC");
		rootLayout.add(NO_ACC_CaptionLabel);
		NO_ACC_DscField4 = new dsc.echo2app.component.DscField();
		NO_ACC_DscField4.setId("NO_ACC_DscField4");
		rootLayout.add(NO_ACC_DscField4);
		BANK_NAME_CaptionLabel = new nextapp.echo2.app.Label();
		BANK_NAME_CaptionLabel.setText("N_ATM_MANAGER.BANK_NAME");
		rootLayout.add(BANK_NAME_CaptionLabel);
		BANK_NAME_DscField5 = new SelectField();
		BANK_NAME_DscField5.setId("BANK_NAME_DscField5");
		BANK_NAME_DscField5.setWidth(new Extent(149));
		rootLayout.add(BANK_NAME_DscField5);
		USER_UPDATED_CaptionLabel = new nextapp.echo2.app.Label();
		USER_UPDATED_CaptionLabel.setText("N_ATM_MANAGER.USER_UPDATED");
		rootLayout.add(USER_UPDATED_CaptionLabel);
		USER_UPDATED_DscField6 = new dsc.echo2app.component.DscField();
		USER_UPDATED_DscField6.setId("USER_UPDATED_DscField6");
		rootLayout.add(USER_UPDATED_DscField6);
		DATE_UPDATE_CaptionLabel = new nextapp.echo2.app.Label();
		DATE_UPDATE_CaptionLabel.setText("N_ATM_MANAGER.DATE_UPDATE");
		rootLayout.add(DATE_UPDATE_CaptionLabel);
		DATE_UPDATE_DscField7 = new dsc.echo2app.component.DscField();
		DATE_UPDATE_DscField7.setId("DATE_UPDATE_DscField7");
		rootLayout.add(DATE_UPDATE_DscField7);
		NOTE_CaptionLabel = new nextapp.echo2.app.Label();
		NOTE_CaptionLabel.setText("N_ATM_MANAGER.NOTE");
		rootLayout.add(NOTE_CaptionLabel);
		NOTE_DscField8 = new dsc.echo2app.component.DscField();
		NOTE_DscField8.setId("NOTE_DscField8");
		rootLayout.add(NOTE_DscField8);
	}

	public boolean isValid() {
		String empsn = EMPSN_DscField1.getText();
		if (!empsn.matches("[0-9]{8}"))
			return false;
		IGenericDAO<N_EMPLOYEE, String> dao = Application.getApp().getDao(
				N_EMPLOYEE.class);
		N_EMPLOYEE emp = dao.findById(empsn);
		if (emp == null)
			return false;
		return true;
	}

}
