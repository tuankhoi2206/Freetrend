package ds.program.fvhr.ngan.ui.wdata;

import java.util.Calendar;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Row;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_N_TCA_NGOAI_NV;
import fv.util.MappingPropertyUtils;

public class TangCaNgoai00MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label NAM_CaptionLabel;
    private nextapp.echo2.app.SelectField NAM_SelectField1;
    private nextapp.echo2.app.Label THANG_CaptionLabel;
    private nextapp.echo2.app.SelectField THANG_SelectField2;
    private nextapp.echo2.app.Label TCA_NGAY_CaptionLabel;
    private dsc.echo2app.component.DscField TCA_NGAY_DscField2;
    private nextapp.echo2.app.Label TCA_DEM_CaptionLabel;
    private dsc.echo2app.component.DscField TCA_DEM_DscField3;
    private nextapp.echo2.app.Label TCA_CN_CaptionLabel;
    private dsc.echo2app.component.DscField TCA_CN_DscField4;
    private nextapp.echo2.app.Label TCA_LE_CaptionLabel;
    private dsc.echo2app.component.DscField TCA_LE_DscField5;
    private nextapp.echo2.app.Label SODEM_TIENCOM_CaptionLabel;
    private dsc.echo2app.component.DscField SODEM_TIENCOM_DscField6;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private nextapp.echo2.app.TextArea NOTE_TextArea1;

	/**
	 * Creates a new <code>TangCaNgoai00MDataContent</code>.
	 */
	public TangCaNgoai00MDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
	}


	/*
	 * UI資料欄位屬性更新，一般是資料模式變換時會呼叫此method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doUIRefresh()
	 */
	@Override
	public void doUIRefresh() {
		super.doUIRefresh();
		if((this.getProgram().getDataMode() == IProgram.DATAMODE_NONE) || 
				(this.getProgram().getDataMode() == IProgram.DATAMODE_BROWSER)) {
			rootLayout.setEnabled(false);
		} else {
			rootLayout.setEnabled(true);
            //----------------------------------------------------------------------------------
			//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            //----------------------------------------------------------------------------------
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				//新增時，設定哪些元件Enable/Disable
			} else {
				//修改時，設定哪些元件Enable/Disable
			}
		}

		//7.<資料權限管控>


		//自動設定元件風格
		getUIDataObjectBinder().doDataBindUIStyle();
	}

	/*
	 * 取得該畫面的DataObject Class
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#getDataObjectClass()
	 */
	public Class getDataObjectClass() {
		return N_N_TCA_NGOAI_NV.class;
	}

	
	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */
	@Override
	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值
		//N_N_TCA_NGOAI_NV data = (N_N_TCA_NGOAI_NV) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		//N_N_TCA_NGOAI_NV data = (N_N_TCA_NGOAI_NV) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			//使用者可在以下區域撰寫內容合理值的判斷程式碼
			//此單頭為readonly 直接return true 
			return validateEmpsn();
		}
		return ret;
	}

	/*
	 * 元件初始化Method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doInit()
	 */
	@Override
	protected int doInit() {
		int nRet = super.doInit();

		//1.註冊資料欄位之顯示方式
		registPropertyEditor();

		//2.設定資料欄位與 UI之 Binding資訊
		bindUI();
		return nRet;
	}
	
	private void registPropertyEditor() {
	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor
        getUIDataObjectBinder().registerCustomEditor(N_N_TCA_NGOAI_NV.class, "THANG", MappingPropertyUtils.getStringMonthEditor());
        getUIDataObjectBinder().registerCustomEditor(N_N_TCA_NGOAI_NV.class, "NAM", MappingPropertyUtils.getStringYearEditor(1, false));
        
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NAM", NAM_SelectField1, NAM_CaptionLabel);
        getUIDataObjectBinder().addBindMap("THANG", THANG_SelectField2, THANG_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TCA_NGAY", TCA_NGAY_DscField2, TCA_NGAY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TCA_DEM", TCA_DEM_DscField3, TCA_DEM_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TCA_CN", TCA_CN_DscField4, TCA_CN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TCA_LE", TCA_LE_DscField5, TCA_LE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("SODEM_TIENCOM", SODEM_TIENCOM_DscField6, SODEM_TIENCOM_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_TextArea1, NOTE_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	

	/*
	 * 畫面Layout初始化Method
	 * 
	 * @see dsc.echo2app.program.DataContent#doLayout()
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
		//<<從此以下加入使用者程式>>
	}

	/*
	 * 做資料新增時內定預設數值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doNewDefaulData()
	 */
	@Override
	protected void doNewDefaulData() {
		N_N_TCA_NGOAI_NV data = (N_N_TCA_NGOAI_NV) getDataObject();
		//<如果要預先取號則在此加入>
		//autoPrimaryKeyValue();
		//使用者可在以下區域填入新增時內定的預設數值
		//<內定值>
		Calendar c = Calendar.getInstance();
		String month = c.get(Calendar.MONTH)+1<10?"0"+(c.get(Calendar.MONTH)+1):""+(c.get(Calendar.MONTH)+1);
		String year = String.valueOf(c.get(Calendar.YEAR));
		System.out.println(month + year);
		data.setTHANG(month);
		data.setNAM(year);
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {
		//N_N_TCA_NGOAI_NV data = (N_N_TCA_NGOAI_NV) getDataObject();
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
		    autoPrimaryKeyValue();
		    
		}
		//使用者可在以下區域做儲存資料物件至DataObjectSet之前處理
		//<儲存前處理>
	}
	
	public boolean validateEmpsn(){
		String empsn=EMPSN_DscField1.getText();
		if (!empsn.matches("[0-9]{8}")){
			setErrorMessage("Số thẻ không hợp lệ");
			return false;
		}
		IGenericDAO<N_EMPLOYEE, String> dao = Application.getApp().getDao(N_EMPLOYEE.class);
		N_EMPLOYEE emp = dao.findById(empsn);
		if (emp==null){
			setErrorMessage("Số thẻ không tồn tại");
			return false;
		}
		return true;
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
        rootLayout = new Grid();
//        rootLayout.setWidth(new Extent(100, Extent.PERCENT));
        rootLayout.setSize(4);
        add(rootLayout);
        EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_CaptionLabel.setText("N_N_TCA_NGOAI_NV.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField1 = new dsc.echo2app.component.DscField();
        EMPSN_DscField1.setId("EMPSN_DscField1");
        EMPSN_DscField1.setMaximumLength(8);
        rootLayout.add(EMPSN_DscField1);
        Row row = new Row();
        THANG_CaptionLabel = new nextapp.echo2.app.Label();
        THANG_CaptionLabel.setText("N_N_TCA_NGOAI_NV.THANG");
        rootLayout.add(THANG_CaptionLabel);
        THANG_SelectField2 = new nextapp.echo2.app.SelectField();
        THANG_SelectField2.setId("THANG_SelectField2");
        THANG_SelectField2.setWidth(new Extent(40));
        row.add(THANG_SelectField2);
        NAM_CaptionLabel = new nextapp.echo2.app.Label();
        NAM_CaptionLabel.setText("N_N_TCA_NGOAI_NV.NAM");
        row.add(NAM_CaptionLabel);
        NAM_SelectField1 = new nextapp.echo2.app.SelectField();
        NAM_SelectField1.setId("NAM_SelectField1");
        NAM_SelectField1.setWidth(new Extent(60));
        row.add(NAM_SelectField1);
        rootLayout.add(row);
        TCA_NGAY_CaptionLabel = new nextapp.echo2.app.Label();
        TCA_NGAY_CaptionLabel.setText("N_N_TCA_NGOAI_NV.TCA_NGAY");
        rootLayout.add(TCA_NGAY_CaptionLabel);
        TCA_NGAY_DscField2 = new dsc.echo2app.component.DscField();
        TCA_NGAY_DscField2.setId("TCA_NGAY_DscField2");
        rootLayout.add(TCA_NGAY_DscField2);
        TCA_DEM_CaptionLabel = new nextapp.echo2.app.Label();
        TCA_DEM_CaptionLabel.setText("N_N_TCA_NGOAI_NV.TCA_DEM");
        rootLayout.add(TCA_DEM_CaptionLabel);
        TCA_DEM_DscField3 = new dsc.echo2app.component.DscField();
        TCA_DEM_DscField3.setId("TCA_DEM_DscField3");
        rootLayout.add(TCA_DEM_DscField3);
        TCA_CN_CaptionLabel = new nextapp.echo2.app.Label();
        TCA_CN_CaptionLabel.setText("N_N_TCA_NGOAI_NV.TCA_CN");
        rootLayout.add(TCA_CN_CaptionLabel);
        TCA_CN_DscField4 = new dsc.echo2app.component.DscField();
        TCA_CN_DscField4.setId("TCA_CN_DscField4");
        rootLayout.add(TCA_CN_DscField4);
        TCA_LE_CaptionLabel = new nextapp.echo2.app.Label();
        TCA_LE_CaptionLabel.setText("N_N_TCA_NGOAI_NV.TCA_LE");
        rootLayout.add(TCA_LE_CaptionLabel);
        TCA_LE_DscField5 = new dsc.echo2app.component.DscField();
        TCA_LE_DscField5.setId("TCA_LE_DscField5");
        rootLayout.add(TCA_LE_DscField5);
        SODEM_TIENCOM_CaptionLabel = new nextapp.echo2.app.Label();
        SODEM_TIENCOM_CaptionLabel.setText("N_N_TCA_NGOAI_NV.SODEM_TIENCOM");
        rootLayout.add(SODEM_TIENCOM_CaptionLabel);
        SODEM_TIENCOM_DscField6 = new dsc.echo2app.component.DscField();
        SODEM_TIENCOM_DscField6.setId("SODEM_TIENCOM_DscField6");
        rootLayout.add(SODEM_TIENCOM_DscField6);
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_N_TCA_NGOAI_NV.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_TextArea1 = new nextapp.echo2.app.TextArea();
        NOTE_TextArea1.setId("NOTE_TextArea1");
        rootLayout.add(NOTE_TextArea1);
	}

}
