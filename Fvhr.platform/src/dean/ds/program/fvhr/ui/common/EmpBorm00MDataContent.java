package ds.program.fvhr.ui.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.SelectField;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_N_EMP_BORM;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import fv.util.BundleUtils;

public class EmpBorm00MDataContent extends DataContent {

	private static final long serialVersionUID = 1L;

	private nextapp.echo2.app.Grid rootLayout;

	private nextapp.echo2.app.Label EMPSN_CaptionLabel;

	private dsc.echo2app.component.DscField EMPSN_DscField1;

	private nextapp.echo2.app.Label STATUS_CaptionLabel;

	// private dsc.echo2app.component.DscField STATUS_DscField2;
	private SelectField sfStatus;

	private nextapp.echo2.app.Label UP_DATE_CaptionLabel;

	private dsc.echo2app.component.DscDateField UP_DATE_DscDateField1;

	private nextapp.echo2.app.Label UP_USER_CaptionLabel;

	private dsc.echo2app.component.DscField UP_USER_DscField3;

	/**
	 * Creates a new <code>EmpBorm00MDataContent</code>.
	 */
	public EmpBorm00MDataContent() {
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
			UP_USER_DscField3.setEnabled(false);
			UP_USER_DscField3.setStyleName("Default.ReadonlyField");
			// 以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
			// ----------------------------------------------------------------------------------
			if (this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				EMPSN_DscField1.setEnabled(true);
			} else {
				EMPSN_DscField1.setEnabled(false);
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
		return N_N_EMP_BORM.class;
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
		// N_N_EMP_BORM data = (N_N_EMP_BORM) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		N_N_EMP_BORM data = (N_N_EMP_BORM) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			if (getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				if (!data.getEMPSN().matches("[0-9]{8}")) {
					setErrorMessage("Số thẻ không hợp lệ");
					return false;
				}
				IGenericDAO<N_EMPLOYEE, String> edao = Application.getApp()
						.getDao(N_EMPLOYEE.class);
				N_EMPLOYEE emp = edao.findById(data.getEMPSN());
				if (emp == null || emp.getDEPSN().equals("00000")) {
					setErrorMessage("Số thẻ không có trong hệ thống hoặc đã nghỉ việc");
					return false;
				}
				IGenericDAO<N_N_EMP_BORM, String> dao = Application.getApp()
						.getDao(N_N_EMP_BORM.class);
				if (dao.findById(data.getEMPSN()) != null) {
					setErrorMessage("Số thẻ đã có trong danh sách. Chọn chỉnh sửa để cập nhật trạng thái ứng lương");
					return false;
				}
			} else if (getProgram().getDataMode() == IProgram.DATAMODE_EDIT) {
				if (data.getSTATUS().equalsIgnoreCase("y")) {
					IGenericDAO<N_EMPLOYEE, String> edao = Application.getApp()
							.getDao(N_EMPLOYEE.class);
					N_EMPLOYEE emp = edao.findById(data.getEMPSN());
					if (emp == null || emp.getDEPSN().equals("00000")) {
						setErrorMessage("Nhân viên đã nghỉ việc");
						return false;
					}
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
				N_N_EMP_BORM.class,
				"UP_DATE",
				PropertyEditors.createDateEditor(BundleUtils
						.getString("LANG_DATEFORMAT")));
		getUIDataObjectBinder().registerCustomEditor(N_N_EMP_BORM.class,
				"STATUS", statusEditor());
	}

	private void bindUI() {
		// ----------------------------------------------------------------------------------
		// 以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
		getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1,
				EMPSN_CaptionLabel);
		getUIDataObjectBinder().addBindMap("STATUS", sfStatus,
				STATUS_CaptionLabel);
		getUIDataObjectBinder().addBindMap("UP_DATE", UP_DATE_DscDateField1,
				UP_DATE_CaptionLabel);
		getUIDataObjectBinder().addBindMap("UP_USER", UP_USER_DscField3,
				UP_USER_CaptionLabel);
		// 以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
		// ----------------------------------------------------------------------------------
	}

	private MappingPropertyEditor statusEditor() {
		MappingPropertyEditor e = new MappingPropertyEditor();
		e.put("Có ứng lương", "Y");
		e.put("Không ứng lương", "N");
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
		// N_N_EMP_BORM data = (N_N_EMP_BORM) getDataObject();
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
		N_N_EMP_BORM data = (N_N_EMP_BORM) getDataObject();
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
		EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
		EMPSN_CaptionLabel.setText("N_N_EMP_BORM.EMPSN");
		rootLayout.add(EMPSN_CaptionLabel);
		EMPSN_DscField1 = new dsc.echo2app.component.DscField();
		EMPSN_DscField1.setId("EMPSN_DscField1");
		EMPSN_DscField1.setMaximumLength(8);
		rootLayout.add(EMPSN_DscField1);
		STATUS_CaptionLabel = new nextapp.echo2.app.Label();
		STATUS_CaptionLabel.setText("N_N_EMP_BORM.STATUS");
		rootLayout.add(STATUS_CaptionLabel);
		// STATUS_DscField2 = new dsc.echo2app.component.DscField();
		// STATUS_DscField2.setId("STATUS_DscField2");
		// rootLayout.add(STATUS_DscField2);
		sfStatus = new SelectField();
		sfStatus.setWidth(new Extent(150));
		rootLayout.add(sfStatus);
		UP_USER_CaptionLabel = new nextapp.echo2.app.Label();
		UP_USER_CaptionLabel.setText("N_N_EMP_BORM.UP_USER");
		rootLayout.add(UP_USER_CaptionLabel);
		UP_USER_DscField3 = new dsc.echo2app.component.DscField();
		UP_USER_DscField3.setId("UP_USER_DscField3");
		rootLayout.add(UP_USER_DscField3);
		UP_DATE_CaptionLabel = new nextapp.echo2.app.Label();
		UP_DATE_CaptionLabel.setText("N_N_EMP_BORM.UP_DATE");
		rootLayout.add(UP_DATE_CaptionLabel);
		UP_DATE_DscDateField1 = new dsc.echo2app.component.DscDateField();
		UP_DATE_DscDateField1.setDateFormat(new SimpleDateFormat(BundleUtils
				.getString("LANG_DATEFORMAT")));
		UP_DATE_DscDateField1.getDateChooser().setLocale(new Locale("en"));
		UP_DATE_DscDateField1.setId("UP_DATE_DscDateField1");
		rootLayout.add(UP_DATE_DscDateField1);
	}

}
