package ds.program.fvhr.ui;

import java.text.SimpleDateFormat;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.domain.N_DEDUCT_OTHER;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import fv.util.Vni2Uni;

public class DeductOther01MDataContent extends DataContent {

	private nextapp.echo2.app.Grid rootLayout;

	private nextapp.echo2.app.Label DEDUCTED_CaptionLabel;

	private dsc.echo2app.component.DscField DEDUCTED_DscField1;

	private nextapp.echo2.app.Label DATE_UPDATED_CaptionLabel;

	private dsc.echo2app.component.DscDateField DATE_UPDATED_DscDateField1;

	private nextapp.echo2.app.Label EMPSN_CaptionLabel;

	private dsc.echo2app.component.DscField EMPSN_DscField2;

	private nextapp.echo2.app.Label MONTH_DEDUCT_CaptionLabel;

	private dsc.echo2app.component.DscDateField MONTH_DEDUCT_DscDateField2;

	private nextapp.echo2.app.Label NOTE_CaptionLabel;

	private nextapp.echo2.app.TextArea NOTE_DscField3;

	private nextapp.echo2.app.Label TOTAL_DEDUCT_CaptionLabel;

	private dsc.echo2app.component.DscField TOTAL_DEDUCT_DscField4;

	private nextapp.echo2.app.Label USER_UPDATED_CaptionLabel;

	private dsc.echo2app.component.DscField USER_UPDATED_DscField5;
	
	private nextapp.echo2.app.Label QT_PAYTAX_CaptionLabel;

	private dsc.echo2app.component.DscField QT_PAYTAX_DscField6;	

	/**
	 * Creates a new <code>DeductOther01MDataContent</code>.
	 */
	public DeductOther01MDataContent() {
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
			DATE_UPDATED_DscDateField1.setEnabled(false);
			DATE_UPDATED_DscDateField1.setStyleName("Default.ReadonlyField");
			USER_UPDATED_DscField5.setEnabled(false);
			USER_UPDATED_DscField5.setStyleName("Default.ReadonlyField");
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
		return N_DEDUCT_OTHER.class;
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
		// N_DEDUCT_OTHER data = (N_DEDUCT_OTHER) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		N_DEDUCT_OTHER data = (N_DEDUCT_OTHER) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			// 使用者可在以下區域撰寫內容合理值的判斷程式碼
			// 此單頭為readonly 直接return true
			// kiem tra thoi gian da chuyen du lieu chua
			boolean kq = true;
			InsuranceDAO ins = new InsuranceDAO();
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			
			String thangKTru = sf.format(data.getMONTH_DEDUCT());		
			String thang = thangKTru.substring(3, 5);
			String nam = thangKTru.substring(6, 10);
			// neu da chuyen du lieu roi thi ko cho cap nhat lai
			kq = ins.CheckKhoaDataMonth(data.getEMPSN(), thang, nam);
			//kq= true la chua chuyen data, =false la da chuyen roi
			if (!kq){
				setErrorMessage("Đã chuyển dữ liệu của tháng, không thể thêm mới thông tin khấu trừ khác.");				
				ret = false;
			}
			else{
				ret = true;
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
		getUIDataObjectBinder().addBindMap("DEDUCTED",
				DEDUCTED_DscField1, DEDUCTED_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DATE_UPDATED",
				DATE_UPDATED_DscDateField1, DATE_UPDATED_CaptionLabel);
		getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField2,
				EMPSN_CaptionLabel);
		getUIDataObjectBinder().addBindMap("MONTH_DEDUCT",
				MONTH_DEDUCT_DscDateField2, MONTH_DEDUCT_CaptionLabel);
		getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField3,
				NOTE_CaptionLabel);
		getUIDataObjectBinder().addBindMap("TOTAL_DEDUCT",
				TOTAL_DEDUCT_DscField4, TOTAL_DEDUCT_CaptionLabel);
		getUIDataObjectBinder().addBindMap("USER_UPDATED",
				USER_UPDATED_DscField5, USER_UPDATED_CaptionLabel);
		getUIDataObjectBinder().addBindMap("QT_PAYTAX",
				QT_PAYTAX_DscField6, QT_PAYTAX_CaptionLabel);		
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
	private java.util.Date date_update = new java.util.Date();

	private String USER_UPDATED = Application.getApp().getLoginInfo()
			.getUserID();

	@Override
	protected void doNewDefaulData() {
		N_DEDUCT_OTHER data = (N_DEDUCT_OTHER) getDataObject();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		data.setDATE_UPDATED(date_update);
		data.setUSER_UPDATED(USER_UPDATED);
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */

	@Override
	public void beforeSaveToDataObjectSet() {
		N_DEDUCT_OTHER data = (N_DEDUCT_OTHER) getDataObject();
		data.setNOTE(Vni2Uni.convertToVNI(data.getNOTE()));
		data.setDATE_UPDATED(date_update);
		data.setUSER_UPDATED(USER_UPDATED);
		
/*		if (this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
			autoPrimaryKeyValue();
		}
		*/
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
		// so the
		EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
		EMPSN_CaptionLabel.setText("EMPSN");
		rootLayout.add(EMPSN_CaptionLabel);
		EMPSN_DscField2 = new dsc.echo2app.component.DscField();
		EMPSN_DscField2.setId("EMPSN_DscField2");
		rootLayout.add(EMPSN_DscField2);
		// ghi chu
		NOTE_CaptionLabel = new nextapp.echo2.app.Label();
		NOTE_CaptionLabel.setText("NOTE");
		rootLayout.add(NOTE_CaptionLabel);
		NOTE_DscField3 = new nextapp.echo2.app.TextArea();
		NOTE_DscField3.setId("NOTE_DscField3");
		rootLayout.add(NOTE_DscField3);
		// tong so tien khau tru
		TOTAL_DEDUCT_CaptionLabel = new nextapp.echo2.app.Label();
		TOTAL_DEDUCT_CaptionLabel.setText("TOTAL_DEDUCT");
		rootLayout.add(TOTAL_DEDUCT_CaptionLabel);
		TOTAL_DEDUCT_DscField4 = new dsc.echo2app.component.DscField();
		TOTAL_DEDUCT_DscField4.setId("TOTAL_DEDUCT_DscField4");
		rootLayout.add(TOTAL_DEDUCT_DscField4);
		// ngay cap nhat
		DATE_UPDATED_CaptionLabel = new nextapp.echo2.app.Label();
		DATE_UPDATED_CaptionLabel.setText("DATE_UPDATED");
		rootLayout.add(DATE_UPDATED_CaptionLabel);
		DATE_UPDATED_DscDateField1 = new dsc.echo2app.component.DscDateField();
		DATE_UPDATED_DscDateField1.setId("DATE_UPDATED_DscDateField1");
		rootLayout.add(DATE_UPDATED_DscDateField1);
		// sau khi khau tru
		DEDUCTED_CaptionLabel = new nextapp.echo2.app.Label();
		DEDUCTED_CaptionLabel.setText("DEDUCTED");
		rootLayout.add(DEDUCTED_CaptionLabel);
		DEDUCTED_DscField1 = new dsc.echo2app.component.DscField();
		DEDUCTED_DscField1.setId("DEDUCTED_DscField1");
		rootLayout.add(DEDUCTED_DscField1);
		// nguoi cap nhat
		USER_UPDATED_CaptionLabel = new nextapp.echo2.app.Label();
		USER_UPDATED_CaptionLabel.setText("USER_UPDATED");
		rootLayout.add(USER_UPDATED_CaptionLabel);
		USER_UPDATED_DscField5 = new dsc.echo2app.component.DscField();
		USER_UPDATED_DscField5.setId("USER_UPDATED_DscField5");
		rootLayout.add(USER_UPDATED_DscField5);
		// thang khau tru
		MONTH_DEDUCT_CaptionLabel = new nextapp.echo2.app.Label();
		MONTH_DEDUCT_CaptionLabel.setText("MONTH_DEDUCT");
		rootLayout.add(MONTH_DEDUCT_CaptionLabel);
		MONTH_DEDUCT_DscDateField2 = new dsc.echo2app.component.DscDateField();
		MONTH_DEDUCT_DscDateField2.setId("MONTH_DEDUCT_DscDateField2");
		rootLayout.add(MONTH_DEDUCT_DscDateField2);

		// QT_ thue TNCN neu co
		QT_PAYTAX_CaptionLabel = new nextapp.echo2.app.Label();
		QT_PAYTAX_CaptionLabel.setText("QT THUE TNCN");
		rootLayout.add(QT_PAYTAX_CaptionLabel);
		QT_PAYTAX_DscField6 = new dsc.echo2app.component.DscField();
		QT_PAYTAX_DscField6.setId("QT_PAYTAX_DscField6");
		rootLayout.add(QT_PAYTAX_DscField6);		
		// sap xep thu tu tren FORM de tao giao dien dep cho nguoi su dung

	}

}
