package ds.program.fvhr.ui.training;

import java.util.List;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.SelectField;
import dsc.dao.DataObjectSet;
import dsc.dao.DataRelation;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DetailContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import ds.program.fvhr.domain.N_CT_KHOA_HOC;
import ds.program.fvhr.domain.N_TRAINING_LIST;
import fv.util.Vni2Uni;

public class KhoaHoc00DDetailContent0 extends DetailContent {

    private nextapp.echo2.app.Grid rootLayout;
//    private nextapp.echo2.app.Label ID_KHOA_CaptionLabel;
//    private dsc.echo2app.component.DscField ID_KHOA_DscField1;
    private nextapp.echo2.app.Label ID_MONHOC_CaptionLabel;
//    private dsc.echo2app.component.DscField ID_MONHOC_DscField2;
//    private nextapp.echo2.app.Label ID_KHOA_Object_CaptionLabel;
//    private dsc.echo2app.component.DscField ID_KHOA_Object_DscField3;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField4;
	private SelectField sfMonhoc;

	/**
	 * Creates a new <code>KhoaHoc00DDetailContent0</code>.
	 */
	public KhoaHoc00DDetailContent0() {
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
		return new DataRelation("MA_KHOA", "ID_KHOA");
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		//N_CT_KHOA_HOC data = (N_CT_KHOA_HOC) getDataObject();
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
		return N_CT_KHOA_HOC.class;
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
		//N_CT_KHOA_HOC data = (N_CT_KHOA_HOC) getDataObject();
	}

	/*
	 * 自動編號 
	 */
	protected boolean autoSequenceKeyValue() {
		//使用者可以在以下區域撰寫產生新序號的程式碼並將新序號放入dataObject
		//N_CT_KHOA_HOC data = (N_CT_KHOA_HOC) getDataObject();
		//int maxSeq = 0;
		//N_CT_KHOA_HOC maxData = (N_CT_KHOA_HOC)this.getDataObjectSet().getDataObjectByFieldMax("{ID_FIELD}");
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
        getUIDataObjectBinder().registerCustomEditor(N_CT_KHOA_HOC.class, "ID_MONHOC", getMonhocEditor());
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
//        getUIDataObjectBinder().addBindMap("ID_KHOA", ID_KHOA_DscField1, ID_KHOA_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_MONHOC", sfMonhoc, ID_MONHOC_CaptionLabel);
//        getUIDataObjectBinder().addBindMap("ID_KHOA_Object", ID_KHOA_Object_DscField3, ID_KHOA_Object_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField4, NOTE_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	
	private MappingPropertyEditor getMonhocEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_TRAINING_LIST, String> dao = Application.getApp().getDao(N_TRAINING_LIST.class);
		List<N_TRAINING_LIST> list = dao.findAll(5000);
		for (N_TRAINING_LIST data:list){
			e.put(Vni2Uni.convertToUnicode(data.getTEN_MON_HOC()), data.getMA_MON_HOC());
		}
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
		//<<從此以下加入使用者程式>>
	}

	/*
	 * 做資料新增時內定預設數值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doNewDefaulData()
	 */
	@Override
	protected void doNewDefaulData() {
		N_CT_KHOA_HOC data = (N_CT_KHOA_HOC) getDataObject();
		//<如果要預先取號則在此加入>
		//autoSequenceKeyValue();
		//使用者可在以下區域填入新增時內定的預設數值
		//<內定值>
		
		//<<填入與單頭關聯值>>
        ds.program.fvhr.domain.N_KHOA_HOC pdata = (ds.program.fvhr.domain.N_KHOA_HOC) getMasterDataContent().getDataObject();
        data.setID_KHOA(pdata.getMA_KHOA());
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {
		//N_CT_KHOA_HOC data = (N_CT_KHOA_HOC) getDataObject();
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
        return new String[]{"ID_MONHOC"};
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
        rootLayout = new Grid();
//        rootLayout.setWidth(new Extent(100, Extent.PERCENT));
        rootLayout.setSize(2);
        rootLayout.setInsets(new Insets(2));
        add(rootLayout);
//        ID_KHOA_CaptionLabel = new nextapp.echo2.app.Label();
//        ID_KHOA_CaptionLabel.setText("N_CT_KHOA_HOC.ID_KHOA");
//        rootLayout.add(ID_KHOA_CaptionLabel);
//        ID_KHOA_DscField1 = new dsc.echo2app.component.DscField();
//        ID_KHOA_DscField1.setId("ID_KHOA_DscField1");
//        rootLayout.add(ID_KHOA_DscField1);
        ID_MONHOC_CaptionLabel = new nextapp.echo2.app.Label();
        ID_MONHOC_CaptionLabel.setText("N_CT_KHOA_HOC.ID_MONHOC");
        rootLayout.add(ID_MONHOC_CaptionLabel);
//        ID_MONHOC_DscField2 = new dsc.echo2app.component.DscField();
//        ID_MONHOC_DscField2.setId("ID_MONHOC_DscField2");
//        ID_MONHOC_DscField2.setWidth(new Extent(400));
//        rootLayout.add(ID_MONHOC_DscField2);
        sfMonhoc = new SelectField();
        sfMonhoc.setWidth(new Extent(400));
        rootLayout.add(sfMonhoc);
//        ID_KHOA_Object_CaptionLabel = new nextapp.echo2.app.Label();
//        ID_KHOA_Object_CaptionLabel.setText("N_CT_KHOA_HOC.ID_KHOA");
//        rootLayout.add(ID_KHOA_Object_CaptionLabel);
//        ID_KHOA_Object_DscField3 = new dsc.echo2app.component.DscField();
//        ID_KHOA_Object_DscField3.setId("ID_KHOA_Object_DscField3");
//        rootLayout.add(ID_KHOA_Object_DscField3);
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_CT_KHOA_HOC.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField4 = new dsc.echo2app.component.DscField();
        NOTE_DscField4.setId("NOTE_DscField4");
        NOTE_DscField4.setWidth(new Extent(400));
        rootLayout.add(NOTE_DscField4);
	}

	public void updateChildPK(String ma_khoa) {
		DataObjectSet ds = getDataObjectSet();
		for (int i=0;i<ds.getRecordCount();i++){
			N_CT_KHOA_HOC data = (N_CT_KHOA_HOC) ds.getDataObject(i);
			data.setID_KHOA(ma_khoa);
			ds.modify(i, data);
		}
	}

}
