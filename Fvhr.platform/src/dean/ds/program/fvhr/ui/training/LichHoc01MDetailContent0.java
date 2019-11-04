package ds.program.fvhr.ui.training;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.dao.DataRelation;
import dsc.echo2app.program.DetailContent;
import dsc.echo2app.program.IProgram;
import ds.program.fvhr.domain.N_TRAINING_DETAIL;

public class LichHoc01MDetailContent0 extends DetailContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label ID_TRAINING_CaptionLabel;
    private dsc.echo2app.component.DscField ID_TRAINING_DscField1;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField2;
    private nextapp.echo2.app.Label DATE_THI_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_THI_DscDateField1;
    private nextapp.echo2.app.Label DIEM_CaptionLabel;
    private dsc.echo2app.component.DscField DIEM_DscField3;
    private nextapp.echo2.app.Label XEPLOAI_CaptionLabel;
    private dsc.echo2app.component.DscField XEPLOAI_DscField4;
    private nextapp.echo2.app.Label LANTHI_CaptionLabel;
    private dsc.echo2app.component.DscField LANTHI_DscField5;
    private nextapp.echo2.app.Label GHICHU_CaptionLabel;
    private dsc.echo2app.component.DscField GHICHU_DscField6;

	/**
	 * Creates a new <code>LichHoc01MDetailContent0</code>.
	 */
	public LichHoc01MDetailContent0() {
		super();

		// Add design-time configured components.
		initComponents();
	}
	
	/* (non-Javadoc)
	 * 建立資料關聯物件
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DetailContent#createRelation()
	 */
	@Override
	public DataRelation createRelation() {
		return new DataRelation("ID_TRAINING", "ID_TRAINING");
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		//N_TRAINING_DETAIL data = (N_TRAINING_DETAIL) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			//使用者可在以下區域撰寫內容合理值的判斷程式碼
		}
		return ret;
	}

	/*
	 * UI資料欄位屬性更新，一般是資料模式變換時會呼叫此method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doUIRefresh()
	 */
	@Override
	public void doUIRefresh() {
		super.doUIRefresh();
		if ((this.getDataMode() == IProgram.DATAMODE_NONE) || (this.getDataMode() == IProgram.DATAMODE_BROWSER)) {
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
	@Override
	public Class getDataObjectClass() {
		return N_TRAINING_DETAIL.class;
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
		//N_TRAINING_DETAIL data = (N_TRAINING_DETAIL) getDataObject();
	}

	/*
	 * 自動編號 
	 */
	protected boolean autoSequenceKeyValue() {
		//使用者可以在以下區域撰寫產生新序號的程式碼並將新序號放入dataObject
		//N_TRAINING_DETAIL data = (N_TRAINING_DETAIL) getDataObject();
		//int maxSeq = 0;
		//N_TRAINING_DETAIL maxData = (N_TRAINING_DETAIL)this.getDataObjectSet().getDataObjectByFieldMax("{ID_FIELD}");
		//if(maxData != null && maxData.get{ID_FIELD}() != null) {
		//	try {
		//		maxSeq = Integer.parseInt(maxData.get{ID_FIELD}());
		//	} catch (Exception e) {
		//		maxSeq = 0;
		//	}
		//}
		//data.set{ID_FIELD}( MessageFormat.format("{0,number,0000}",

		//產生新序號並放入dataObject
		//data.set{ID_FIELD}(newValue);
		return true;
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
        dsc.echo2app.propertyeditors.MappingPropertyEditor editor = null;
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("ID_TRAINING", ID_TRAINING_DscField1, ID_TRAINING_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField2, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_THI", DATE_THI_DscDateField1, DATE_THI_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DIEM", DIEM_DscField3, DIEM_CaptionLabel);
        getUIDataObjectBinder().addBindMap("XEPLOAI", XEPLOAI_DscField4, XEPLOAI_CaptionLabel);
        getUIDataObjectBinder().addBindMap("LANTHI", LANTHI_DscField5, LANTHI_CaptionLabel);
        getUIDataObjectBinder().addBindMap("GHICHU", GHICHU_DscField6, GHICHU_CaptionLabel);
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
		N_TRAINING_DETAIL data = (N_TRAINING_DETAIL) getDataObject();
		//<如果要預先取號則在此加入>
		//autoSequenceKeyValue();
		//使用者可在以下區域填入新增時內定的預設數值
		//<內定值>
		
		//<<填入與單頭關聯值>>
        ds.program.fvhr.domain.N_TRAINING_ITEM pdata = (ds.program.fvhr.domain.N_TRAINING_ITEM) getMasterDataContent().getDataObject();
        data.setID_TRAINING(pdata.getID_TRAINING());
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {
		//N_TRAINING_DETAIL data = (N_TRAINING_DETAIL) getDataObject();
		//使用者可在以下區域做儲存資料物件至DataObjectSet之前處理
		//<儲存前處理>
	}

	@Override
	public void doDrillDown() {
	}

	@Override
	protected String[] getBrowserDisplayColumns() {
		//<<從此以下加入使用者程式>>
		//<<資料瀏覽欄位>>
        return new String[]{"EMPSN","DATE_THI","DIEM","XEPLOAI"};
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
        rootLayout = new Grid();
        rootLayout.setWidth(new Extent(100, Extent.PERCENT));
        rootLayout.setSize(4);
        add(rootLayout);
        ID_TRAINING_CaptionLabel = new nextapp.echo2.app.Label();
        ID_TRAINING_CaptionLabel.setText("N_TRAINING_DETAIL.ID_TRAINING");
        rootLayout.add(ID_TRAINING_CaptionLabel);
        ID_TRAINING_DscField1 = new dsc.echo2app.component.DscField();
        ID_TRAINING_DscField1.setId("ID_TRAINING_DscField1");
        rootLayout.add(ID_TRAINING_DscField1);
        EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_CaptionLabel.setText("N_TRAINING_DETAIL.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField2 = new dsc.echo2app.component.DscField();
        EMPSN_DscField2.setId("EMPSN_DscField2");
        rootLayout.add(EMPSN_DscField2);
        DATE_THI_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_THI_CaptionLabel.setText("N_TRAINING_DETAIL.DATE_THI");
        rootLayout.add(DATE_THI_CaptionLabel);
        DATE_THI_DscDateField1 = new dsc.echo2app.component.DscDateField();
        DATE_THI_DscDateField1.setId("DATE_THI_DscDateField1");
        rootLayout.add(DATE_THI_DscDateField1);
        DIEM_CaptionLabel = new nextapp.echo2.app.Label();
        DIEM_CaptionLabel.setText("N_TRAINING_DETAIL.DIEM");
        rootLayout.add(DIEM_CaptionLabel);
        DIEM_DscField3 = new dsc.echo2app.component.DscField();
        DIEM_DscField3.setId("DIEM_DscField3");
        rootLayout.add(DIEM_DscField3);
        XEPLOAI_CaptionLabel = new nextapp.echo2.app.Label();
        XEPLOAI_CaptionLabel.setText("N_TRAINING_DETAIL.XEPLOAI");
        rootLayout.add(XEPLOAI_CaptionLabel);
        XEPLOAI_DscField4 = new dsc.echo2app.component.DscField();
        XEPLOAI_DscField4.setId("XEPLOAI_DscField4");
        rootLayout.add(XEPLOAI_DscField4);
        LANTHI_CaptionLabel = new nextapp.echo2.app.Label();
        LANTHI_CaptionLabel.setText("N_TRAINING_DETAIL.LANTHI");
        rootLayout.add(LANTHI_CaptionLabel);
        LANTHI_DscField5 = new dsc.echo2app.component.DscField();
        LANTHI_DscField5.setId("LANTHI_DscField5");
        rootLayout.add(LANTHI_DscField5);
        GHICHU_CaptionLabel = new nextapp.echo2.app.Label();
        GHICHU_CaptionLabel.setText("N_TRAINING_DETAIL.GHICHU");
        rootLayout.add(GHICHU_CaptionLabel);
        GHICHU_DscField6 = new dsc.echo2app.component.DscField();
        GHICHU_DscField6.setId("GHICHU_DscField6");
        rootLayout.add(GHICHU_DscField6);
	}

}
