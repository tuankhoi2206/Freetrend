package ds.program.fvhr.ui.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SelectField;
import ds.program.fvhr.domain.N_N_EMP_JOB;
import ds.program.fvhr.domain.N_N_JOB;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import fv.util.BundleUtils;
import fv.util.Vni2Uni;

public class JobList00MDataContent extends DataContent {
	private static final long serialVersionUID = 1L;

	private nextapp.echo2.app.Grid rootLayout;

	private nextapp.echo2.app.Label NAME_CaptionLabel;

	private dsc.echo2app.component.DscField NAME_DscField1;

	private nextapp.echo2.app.Label KIND_CaptionLabel;

	// private dsc.echo2app.component.DscField KIND_DscField2;
	private SelectField sfKind;

	private nextapp.echo2.app.Label DATE_B_CaptionLabel;

	private dsc.echo2app.component.DscDateField DATE_B_DscDateField1;

	private nextapp.echo2.app.Label MONEY_CaptionLabel;

	private dsc.echo2app.component.DscField MONEY_DscField3;

	private Label UP_USER_CaptionLabel;

	private DscField UP_USER_DscField;

	private Label UP_DATE_CaptionLabel;

	private DscDateField UP_DATE_DscDateField;

	/**
	 * Creates a new <code>JobList00MDataContent</code>.
	 */
	public JobList00MDataContent() {
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
				NAME_DscField1.setEnabled(true);
				sfKind.setEnabled(true);
			} else {
				// 修改時，設定哪些元件Enable/Disable
				NAME_DscField1.setEnabled(false);
				sfKind.setEnabled(false);
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
		return N_N_JOB.class;
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
		// N_N_JOB data = (N_N_JOB) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		N_N_JOB data = (N_N_JOB) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			IGenericDAO<N_N_JOB, String> dao = Application.getApp().getDao(
					N_N_JOB.class);
			if (getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				List<N_N_JOB> list = dao
						.find(
								10,
								"from N_N_JOB o where trim(lower(o.NAME))=? and o.KIND=?",
								new Object[] {
										Vni2Uni.convertToVNI(data.getNAME())
												.toLowerCase().trim(),
										data.getKIND() });
				if (list.size() > 0) {
					setErrorMessage("Đã tồn tại công việc này rồi. Chọn chỉnh sửa để thay đổi số tiền và ngày áp dụng!!!");
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
		getUIDataObjectBinder().registerCustomEditor(
				N_N_JOB.class,
				"DATE_B",
				PropertyEditors.createDateEditor(BundleUtils
						.getString("LANG_DATEFORMAT")));
		getUIDataObjectBinder().registerCustomEditor(N_N_JOB.class, "KIND",
				kindEditor());
		getUIDataObjectBinder().registerCustomEditor(
				N_N_EMP_JOB.class,
				"UP_DATE",
				PropertyEditors.createDateEditor(BundleUtils
						.getString("LANG_DATEFORMAT")));
	}

	private void bindUI() {
		// ----------------------------------------------------------------------------------
		// 以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
		getUIDataObjectBinder().addBindMap("NAME", NAME_DscField1,
				NAME_CaptionLabel);
		getUIDataObjectBinder().addBindMap("KIND", sfKind, KIND_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DATE_B", DATE_B_DscDateField1,
				DATE_B_CaptionLabel);
		getUIDataObjectBinder().addBindMap("MONEY", MONEY_DscField3,
				MONEY_CaptionLabel);
		getUIDataObjectBinder().addBindMap("UP_USER", UP_USER_DscField,
				UP_USER_CaptionLabel);
		getUIDataObjectBinder().addBindMap("UP_DATE", UP_DATE_DscDateField,
				UP_DATE_CaptionLabel);
		// 以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
		// ----------------------------------------------------------------------------------
	}

	private MappingPropertyEditor kindEditor() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		editor.put("A", "A");
		editor.put("B", "B");
		editor.put("C", "C");
		editor.put("D", "D");
		editor.put("E", "E");
		return editor;
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
	protected synchronized void doNewDefaulData() {
		N_N_JOB data = (N_N_JOB) getDataObject();
		// <如果要預先取號則在此加入>
		// autoPrimaryKeyValue();
		// 使用者可在以下區域填入新增時內定的預設數值
		// <內定值>
		data.setID_JOB("J" + System.currentTimeMillis());
		data.setDATE_B(new Date());
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {
		N_N_JOB data = (N_N_JOB) getDataObject();
		data.setNAME(Vni2Uni.convertToVNI(data.getNAME().trim()));
		data.setUP_USER(getProgram().getLoginInfo().getUserID());
		data.setUP_DATE(new Date());
	}

	/**
	 * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		rootLayout = new Grid();
		// rootLayout.setWidth(new Extent(100, Extent.PERCENT));
		rootLayout.setSize(2);
		add(rootLayout);
		NAME_CaptionLabel = new nextapp.echo2.app.Label();
		NAME_CaptionLabel.setText("N_N_JOB.NAME");
		rootLayout.add(NAME_CaptionLabel);
		NAME_DscField1 = new dsc.echo2app.component.DscField();
		NAME_DscField1.setId("NAME_DscField1");
		rootLayout.add(NAME_DscField1);
		KIND_CaptionLabel = new nextapp.echo2.app.Label();
		KIND_CaptionLabel.setText("N_N_JOB.KIND");
		rootLayout.add(KIND_CaptionLabel);
		// KIND_DscField2 = new dsc.echo2app.component.DscField();
		// KIND_DscField2.setId("KIND_DscField2");
		// rootLayout.add(KIND_DscField2);
		sfKind = new SelectField();
		sfKind.setWidth(new Extent(100));
		rootLayout.add(sfKind);
		DATE_B_CaptionLabel = new nextapp.echo2.app.Label();
		DATE_B_CaptionLabel.setText("N_N_JOB.DATE_B");
		rootLayout.add(DATE_B_CaptionLabel);
		DATE_B_DscDateField1 = new dsc.echo2app.component.DscDateField();
		DATE_B_DscDateField1.getDateChooser().setLocale(new Locale("en"));
		DATE_B_DscDateField1.setDateFormat(new SimpleDateFormat(BundleUtils
				.getString("LANG_DATEFORMAT")));
		DATE_B_DscDateField1.setId("DATE_B_DscDateField1");
		rootLayout.add(DATE_B_DscDateField1);
		MONEY_CaptionLabel = new nextapp.echo2.app.Label();
		MONEY_CaptionLabel.setText("N_N_JOB.MONEY");
		rootLayout.add(MONEY_CaptionLabel);
		MONEY_DscField3 = new dsc.echo2app.component.DscField();
		MONEY_DscField3.setId("MONEY_DscField3");
		rootLayout.add(MONEY_DscField3);
		UP_USER_CaptionLabel = new nextapp.echo2.app.Label();
		rootLayout.add(UP_USER_CaptionLabel);
		UP_USER_DscField = new DscField();
		UP_USER_DscField.setEnabled(false);
		rootLayout.add(UP_USER_DscField);
		UP_DATE_CaptionLabel = new nextapp.echo2.app.Label();
		rootLayout.add(UP_DATE_CaptionLabel);
		UP_DATE_DscDateField = new DscDateField();
		UP_DATE_DscDateField.setDateFormat(new SimpleDateFormat(BundleUtils
				.getString("LANG_DATEFORMAT")));
		UP_DATE_DscDateField.setEnabled(false);
		rootLayout.add(UP_DATE_DscDateField);
	}

	public Date getApplyDate() {
		return DATE_B_DscDateField1.getSelectedDate().getTime();
	}

}
