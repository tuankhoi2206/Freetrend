package ds.program.fvhr.ui;

import java.util.List;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.SelectField;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import ds.program.fvhr.domain.N_N_JOB;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import fv.util.Vni2Uni;

public class N_JOB_MDataContent extends DataContent {

	private nextapp.echo2.app.Grid rootLayout;

	private nextapp.echo2.app.Label ID_JOB_CaptionLabel;

	private dsc.echo2app.component.DscField ID_JOB_DscField1;

	private nextapp.echo2.app.Label KIND_CaptionLabel;

	private SelectField KIND_SelectField1;

	private nextapp.echo2.app.Label NAME_CaptionLabel;

	private dsc.echo2app.component.DscField NAME_DscField2;

	private nextapp.echo2.app.Label MONEY_CaptionLabel;

	private dsc.echo2app.component.DscField MONEY_DscField3;

	private nextapp.echo2.app.Label DATE_B_CaptionLabel;

	private dsc.echo2app.component.DscDateField DATE_B_DscDateField1;

	private nextapp.echo2.app.Label DATE_E_CaptionLabel;

	private dsc.echo2app.component.DscDateField DATE_E_DscDateField2;

	private OBJ_UTILITY obj_util;

	private String key = "";

	private boolean status_edit = false;

	N_N_JOB data_pre;

	public boolean check_save = true;

	IGenericDAO<N_N_JOB, String> obj_dao;

	/**
	 * Creates a new <code>N_JOB_MDataContent</code>.
	 */
	public N_JOB_MDataContent() {
		super();
		obj_util = new OBJ_UTILITY();
		obj_dao = Application.getApp().getDao(N_N_JOB.class);
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
	public boolean edit(int recNo) {
		status_edit = true;
		return super.edit(recNo);
	}

	@Override
	public boolean checkDataObject() {
		N_N_JOB data = (N_N_JOB) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			String name = data.getNAME();
			String kind = data.getKIND();
			if (CheckExistData(data) == false && status_edit) {
				Application.getApp().showMessageDialog(
						MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
						"Trung Ten");
				return false;

			} else {
				if (data_pre != null) {
					// data_pre.setDATE_E(data.getDATE_B());
					try {
						obj_dao.update(data_pre);
					} catch (Exception e) {
						return false;
					}
				}
			}
		}
		return ret;
	}

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
		MappingPropertyEditor editor_KIND = obj_util.Get_Editor_KIND();
		dsc.echo2app.propertyeditors.MappingPropertyEditor editor = null;
		this.getUIDataObjectBinder().registerCustomEditor(N_N_JOB.class,
				"KIND", editor_KIND);

		this.getUIDataObjectBinder().registerCustomEditor(N_N_JOB.class,
				"DATE_B", PropertyEditors.createDateEditor("dd/MM/yyyy"));
		// this.getUIDataObjectBinder().registerCustomEditor(N_N_JOB.class,"DATE_E",
		// PropertyEditors.createDateEditor("dd/MM/yyyy"));

	}

	private void bindUI() {
		// ----------------------------------------------------------------------------------
		// 以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
		getUIDataObjectBinder().addBindMap("ID_JOB", ID_JOB_DscField1,
				ID_JOB_CaptionLabel);
		getUIDataObjectBinder().addBindMap("KIND", KIND_SelectField1,
				KIND_CaptionLabel);
		getUIDataObjectBinder().addBindMap("NAME", NAME_DscField2,
				NAME_CaptionLabel);
		getUIDataObjectBinder().addBindMap("MONEY", MONEY_DscField3,
				MONEY_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DATE_B", DATE_B_DscDateField1,
				DATE_B_CaptionLabel);
		// getUIDataObjectBinder().addBindMap("DATE_E", DATE_E_DscDateField2,
		// DATE_E_CaptionLabel);
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

		ID_JOB_DscField1.setEnabled(false);
		// ID_JOB_DscField1.setText(key);
	}

	/*
	 * 做資料新增時內定預設數值 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.DataContent#doNewDefaulData()
	 */
	@Override
	protected void doNewDefaulData() {
		N_N_JOB data = (N_N_JOB) getDataObject();
		data.setID_JOB(obj_util.AutoGenKey());
	}

	@Override
	public boolean saveToDataObjectSet() {
		return super.saveToDataObjectSet();
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */

	@Override
	public void beforeSaveToDataObjectSet() {
		N_N_JOB data = (N_N_JOB) getDataObject();
		String name = Vni2Uni.convertToVNI(data.getNAME());
		data.setNAME(name);
		// kiem tra ten da ton tai hay chua ?

		/*
		 * if(CheckExistName(Vni2Uni.convertToVNI(data.getNAME()))){
		 * 
		 * check_save = true; } else{ check_save = false; }
		 */
	}

	private boolean CheckExistData(N_N_JOB data) {

		String name = Vni2Uni.convertToVNI(data.getNAME());

		DetachedCriteria obj_dc_1 = DetachedCriteria.forClass(N_N_JOB.class);
		DetachedCriteria obj_dc_2 = DetachedCriteria.forClass(N_N_JOB.class);

		obj_dc_1.add(Restrictions.eq("NAME", name));
		obj_dc_1.add(Restrictions.eq("KIND", data.getKIND()));
		obj_dc_1.add(Restrictions.eq("DATE_B", data.getDATE_B()));
		List<N_N_JOB> list = obj_dao.findByCriteria(1, obj_dc_1);

		if (list.size() > 0) {
			System.out.println("Ten nay da ton tai trong co so du lieu");
			return false;
		} else {

			// up date DATE_E for data_pre
			list.clear();
			obj_dc_2.add(Restrictions.eq("NAME", name));
			obj_dc_2.add(Restrictions.eq("KIND", data.getKIND()));
			obj_dc_2.add(Restrictions.isNull("DATE_E"));
			list = obj_dao.findByCriteria(1, obj_dc_2);

			if (list.size() > 0) {
				data_pre = list.get(0);
			} else {
				data_pre = null;
			}

		}

		return true;

	}

	@Override
	protected void doDataContentRefresh() {
		super.doDataContentRefresh();

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

		ID_JOB_CaptionLabel = new nextapp.echo2.app.Label();
		ID_JOB_CaptionLabel.setText("N_N_JOB.ID_JOB");
		// rootLayout.add(ID_JOB_CaptionLabel);
		ID_JOB_DscField1 = new dsc.echo2app.component.DscField();
		ID_JOB_DscField1.setId("ID_JOB_DscField1");
		// rootLayout.add(ID_JOB_DscField1);

		NAME_CaptionLabel = new nextapp.echo2.app.Label();
		NAME_CaptionLabel.setText("N_N_JOB.NAME");
		rootLayout.add(NAME_CaptionLabel);
		NAME_DscField2 = new dsc.echo2app.component.DscField();
		NAME_DscField2.setId("NAME_DscField2");
		rootLayout.add(NAME_DscField2);

		KIND_CaptionLabel = new nextapp.echo2.app.Label();
		KIND_CaptionLabel.setText("N_N_JOB.KIND");
		rootLayout.add(KIND_CaptionLabel);
		KIND_SelectField1 = new SelectField();
		KIND_SelectField1.setWidth(new Extent(160));
		KIND_SelectField1.setId("KIND_SelectField1");
		KIND_SelectField1.setSelectedIndex(0);
		rootLayout.add(KIND_SelectField1);

		MONEY_CaptionLabel = new nextapp.echo2.app.Label();
		MONEY_CaptionLabel.setText("N_N_JOB.MONEY");
		rootLayout.add(MONEY_CaptionLabel);
		MONEY_DscField3 = new dsc.echo2app.component.DscField();
		MONEY_DscField3.setId("MONEY_DscField3");
		rootLayout.add(MONEY_DscField3);

		DATE_B_CaptionLabel = new nextapp.echo2.app.Label();
		DATE_B_CaptionLabel.setText("N_N_JOB.DATE_B");
		rootLayout.add(DATE_B_CaptionLabel);
		DATE_B_DscDateField1 = new dsc.echo2app.component.DscDateField();
		DATE_B_DscDateField1.setId("DATE_B_DscDateField1");
		DATE_B_DscDateField1.setDateFormat(OBJ_UTILITY.Get_format_date());
		rootLayout.add(DATE_B_DscDateField1);

		DATE_E_CaptionLabel = new nextapp.echo2.app.Label();
		DATE_E_CaptionLabel.setText("N_N_JOB.DATE_E");
		// rootLayout.add(DATE_E_CaptionLabel);
		DATE_E_DscDateField2 = new dsc.echo2app.component.DscDateField();
		DATE_E_DscDateField2.setId("DATE_E_DscDateField2");
		DATE_E_DscDateField2.setDateFormat(OBJ_UTILITY.Get_format_date());
		// rootLayout.add(DATE_E_DscDateField2);
	}

}
