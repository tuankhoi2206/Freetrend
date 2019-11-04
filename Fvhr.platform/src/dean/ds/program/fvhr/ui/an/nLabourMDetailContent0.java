package ds.program.fvhr.ui.an;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.domain.N_LABOUR;
import ds.program.fvhr.domain.N_SUB_LABOUR;
import dsc.dao.DataRelation;
import dsc.echo2app.Application;
import dsc.echo2app.program.BrowserContent;
import dsc.echo2app.program.DetailContent;
import dsc.echo2app.program.DetailToolbar;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.SubDetailContent;
import dsc.echo2app.propertyeditors.PropertyEditors;

import fv.util.VniEditor;
import fv.util.library;

public class nLabourMDetailContent0 extends DetailContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label CHECKED_CaptionLabel;
    private dsc.echo2app.component.DscField CHECKED_DscField1;
    private nextapp.echo2.app.Label CLOCK_CaptionLabel;
    private dsc.echo2app.component.DscField CLOCK_DscField2;
    private nextapp.echo2.app.Label DATES_SIGN_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATES_SIGN_DscDateField1;
    private nextapp.echo2.app.Label DATE_EN_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_EN_DscDateField2;
    private nextapp.echo2.app.Label DELAY_CaptionLabel;
    private dsc.echo2app.component.DscField DELAY_DscField3;
    private nextapp.echo2.app.Label IDSUB_KIND_CaptionLabel;
    private dsc.echo2app.component.DscField IDSUB_KIND_DscField4;
    private nextapp.echo2.app.Label ID_CONTRACT_CaptionLabel;
    private dsc.echo2app.component.DscField ID_CONTRACT_DscField5;
    private nextapp.echo2.app.Label ID_LABOUR_CaptionLabel;
    private dsc.echo2app.component.DscField ID_LABOUR_DscField6;
    private nextapp.echo2.app.Label IS_SUB_CaptionLabel;
    private dsc.echo2app.component.DscField IS_SUB_DscField7;
    private nextapp.echo2.app.Label NEW_JOB_CaptionLabel;
    private dsc.echo2app.component.DscField NEW_JOB_DscField8;
    private nextapp.echo2.app.Label NEW_SAL_CaptionLabel;
    private dsc.echo2app.component.DscField NEW_SAL_DscField9;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField10;
    private ResourceBundle bundle;
    
    library l=new library();

	/**
	 * Creates a new <code>nLabourMDetailContent0</code>.
	 */
	public nLabourMDetailContent0() {
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
		return new DataRelation("ID_LABOUR", "ID_LABOUR");
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		//N_SUB_LABOUR data = (N_SUB_LABOUR) getDataObject();
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
	public Class getDataObjectClass() {
		return N_SUB_LABOUR.class;
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
		//N_SUB_LABOUR data = (N_SUB_LABOUR) getDataObject();
	}

	/*
	 * 自動編號 
	 */
	protected boolean autoSequenceKeyValue() {
		//使用者可以在以下區域撰寫產生新序號的程式碼並將新序號放入dataObject
		//N_SUB_LABOUR data = (N_SUB_LABOUR) getDataObject();
		//int maxSeq = 0;
		//N_SUB_LABOUR maxData = (N_SUB_LABOUR)this.getDataObjectSet().getDataObjectByFieldMax("{ID_FIELD}");
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
		getBrowserContent().getBrowserNav().setBackground(new Color(0xbbddaa));
		
		//1.註冊資料欄位之顯示方式
		registPropertyEditor();

		//2.設定資料欄位與 UI之 Binding資訊
		bindUI();


		return nRet;
	}

	private void registPropertyEditor() {
	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor
        //dsc.echo2app.propertyeditors.MappingPropertyEditor editor = null;
		getUIDataObjectBinder().registerCustomEditor(N_SUB_LABOUR.class, "DATES_SIGN", PropertyEditors.createDateEditor(bundle.getString("LANG_DATEFORMAT")));
		getUIDataObjectBinder().registerCustomEditor(N_SUB_LABOUR.class, "DATE_EN", PropertyEditors.createDateEditor(bundle.getString("LANG_DATEFORMAT")));
		getUIDataObjectBinder().registerCustomEditor(N_SUB_LABOUR.class, "NEW_JOB", new VniEditor());
		getUIDataObjectBinder().registerCustomEditor(N_SUB_LABOUR.class, "NOTE", new VniEditor());
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //getUIDataObjectBinder().addBindMap("CHECKED", CHECKED_DscField1, CHECKED_CaptionLabel);
        getUIDataObjectBinder().addBindMap("CLOCK", CLOCK_DscField2, CLOCK_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATES_SIGN", DATES_SIGN_DscDateField1, DATES_SIGN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_EN", DATE_EN_DscDateField2, DATE_EN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DELAY", DELAY_DscField3, DELAY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("IDSUB_KIND", IDSUB_KIND_DscField4, IDSUB_KIND_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_CONTRACT", ID_CONTRACT_DscField5, ID_CONTRACT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_LABOUR", ID_LABOUR_DscField6, ID_LABOUR_CaptionLabel);
        getUIDataObjectBinder().addBindMap("IS_SUB", IS_SUB_DscField7, IS_SUB_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NEW_JOB", NEW_JOB_DscField8, NEW_JOB_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NEW_SAL", NEW_SAL_DscField9, NEW_SAL_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField10, NOTE_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}

	
	@Override
	public boolean doDelete() {
		// TODO Auto-generated method stub
		N_SUB_LABOUR data = (N_SUB_LABOUR) getDataObject();
		if(l.check_khoaPLHD(data.getID_CONTRACT())){
			l.ShowMessageError("Dữ liệu đã khóa.");
			return false;
		}
		if(!data.getCLOCK().equals("1")){
			l.ShowMessageError("Dữ liệu đã khóa âsasasas.");
			return false;
		}
		return super.doDelete();
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
		getDetailWorkSplitPane().setSeparatorPosition(new Extent(100));
	}

	/*
	 * 做資料新增時內定預設數值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doNewDefaulData()
	 */
	@Override
	protected void doNewDefaulData() {
		N_SUB_LABOUR data = (N_SUB_LABOUR) getDataObject();
		//<如果要預先取號則在此加入>
		//autoSequenceKeyValue();
		//使用者可在以下區域填入新增時內定的預設數值
		//<內定值>
		
		//<<填入與單頭關聯值>>
       N_LABOUR pdata = (N_LABOUR) getMasterDataContent().getDataObject();
        data.setID_LABOUR(pdata.getID_LABOUR());
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {
		//N_SUB_LABOUR data = (N_SUB_LABOUR) getDataObject();
		//使用者可在以下區域做儲存資料物件至DataObjectSet之前處理
		//<儲存前處理>
	}

	public void doDrillDown() {
	}

	@Override
	protected String[] getBrowserDisplayColumns() {
		//<<從此以下加入使用者程式>>
		//<<資料瀏覽欄位>>
        //return null;
		return new String[]{"ID_CONTRACT","ID_LABOUR","IDSUB_KIND","DATES_SIGN","DATE_EN","NEW_SAL","NEW_JOB","DELAY","NOTE","CLOCK","IS_SUB"};
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		bundle = ResourceBundle.getBundle("resource.localization.UICaption", Application.getActive().getLocale());
		this.setBackground(new Color(0xbbddcc));
        rootLayout = new Grid();
        rootLayout.setWidth(new Extent(100, Extent.PERCENT));
        rootLayout.setSize(6);
        add(rootLayout);
        /*CHECKED_CaptionLabel = new nextapp.echo2.app.Label();
        CHECKED_CaptionLabel.setText("N_SUB_LABOUR.CHECKED");
        rootLayout.add(CHECKED_CaptionLabel);
        CHECKED_DscField1 = new dsc.echo2app.component.DscField();
        CHECKED_DscField1.setId("CHECKED_DscField1");
        rootLayout.add(CHECKED_DscField1);*/
        ID_CONTRACT_CaptionLabel = new nextapp.echo2.app.Label();
        ID_CONTRACT_CaptionLabel.setText("N_SUB_LABOUR.ID_CONTRACT");
        rootLayout.add(ID_CONTRACT_CaptionLabel);
        ID_CONTRACT_DscField5 = new dsc.echo2app.component.DscField();
        ID_CONTRACT_DscField5.setId("ID_CONTRACT_DscField5");
        rootLayout.add(ID_CONTRACT_DscField5);
        ID_LABOUR_CaptionLabel = new nextapp.echo2.app.Label();
        ID_LABOUR_CaptionLabel.setText("N_SUB_LABOUR.ID_LABOUR");
        rootLayout.add(ID_LABOUR_CaptionLabel);
        ID_LABOUR_DscField6 = new dsc.echo2app.component.DscField();
        ID_LABOUR_DscField6.setId("ID_LABOUR_DscField6");
        rootLayout.add(ID_LABOUR_DscField6);
        IDSUB_KIND_CaptionLabel = new nextapp.echo2.app.Label();
        IDSUB_KIND_CaptionLabel.setText("N_SUB_LABOUR.IDSUB_KIND");
        rootLayout.add(IDSUB_KIND_CaptionLabel);
        IDSUB_KIND_DscField4 = new dsc.echo2app.component.DscField();
        IDSUB_KIND_DscField4.setId("IDSUB_KIND_DscField4");
        rootLayout.add(IDSUB_KIND_DscField4);
        
        
        DATES_SIGN_CaptionLabel = new nextapp.echo2.app.Label();
        DATES_SIGN_CaptionLabel.setText("N_SUB_LABOUR.DATES_SIGN");
        rootLayout.add(DATES_SIGN_CaptionLabel);
        DATES_SIGN_DscDateField1 = new dsc.echo2app.component.DscDateField();
        DATES_SIGN_DscDateField1.setId("DATES_SIGN_DscDateField1");
        DATES_SIGN_DscDateField1.setDateFormat(new SimpleDateFormat(bundle.getString("LANG_DATEFORMAT")));
        DATES_SIGN_DscDateField1.getDateChooser().setLocale(new Locale("en"));
        rootLayout.add(DATES_SIGN_DscDateField1);
        NEW_SAL_CaptionLabel = new nextapp.echo2.app.Label();
        NEW_SAL_CaptionLabel.setText("N_SUB_LABOUR.NEW_SAL");
        rootLayout.add(NEW_SAL_CaptionLabel);
        NEW_SAL_DscField9 = new dsc.echo2app.component.DscField();
        NEW_SAL_DscField9.setId("NEW_SAL_DscField9");
        rootLayout.add(NEW_SAL_DscField9);
        NEW_JOB_CaptionLabel = new nextapp.echo2.app.Label();
        NEW_JOB_CaptionLabel.setText("N_SUB_LABOUR.NEW_JOB");
        rootLayout.add(NEW_JOB_CaptionLabel);
        NEW_JOB_DscField8 = new dsc.echo2app.component.DscField();
        NEW_JOB_DscField8.setId("NEW_JOB_DscField8");
        rootLayout.add(NEW_JOB_DscField8);
        
        DATE_EN_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_EN_CaptionLabel.setText("N_SUB_LABOUR.DATE_EN");
        rootLayout.add(DATE_EN_CaptionLabel);
        DATE_EN_DscDateField2 = new dsc.echo2app.component.DscDateField();
        DATE_EN_DscDateField2.setId("DATE_EN_DscDateField2");
        DATE_EN_DscDateField2.setDateFormat(new SimpleDateFormat(bundle.getString("LANG_DATEFORMAT")));
        DATE_EN_DscDateField2.getDateChooser().setLocale(new Locale("en"));
        rootLayout.add(DATE_EN_DscDateField2);
        DELAY_CaptionLabel = new nextapp.echo2.app.Label();
        DELAY_CaptionLabel.setText("N_SUB_LABOUR.DELAY");
        rootLayout.add(DELAY_CaptionLabel);
        DELAY_DscField3 = new dsc.echo2app.component.DscField();
        DELAY_DscField3.setId("DELAY_DscField3");
        rootLayout.add(DELAY_DscField3);
        
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_SUB_LABOUR.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField10 = new dsc.echo2app.component.DscField();
        NOTE_DscField10.setId("NOTE_DscField10");
        rootLayout.add(NOTE_DscField10);
        
        CLOCK_CaptionLabel = new nextapp.echo2.app.Label();
        CLOCK_CaptionLabel.setText("N_SUB_LABOUR.CLOCK");
        rootLayout.add(CLOCK_CaptionLabel);
        CLOCK_DscField2 = new dsc.echo2app.component.DscField();
        CLOCK_DscField2.setId("CLOCK_DscField2");
        rootLayout.add(CLOCK_DscField2);
        IS_SUB_CaptionLabel = new nextapp.echo2.app.Label();
        IS_SUB_CaptionLabel.setText("N_SUB_LABOUR.IS_SUB");
        rootLayout.add(IS_SUB_CaptionLabel);
        IS_SUB_DscField7 = new dsc.echo2app.component.DscField();
        IS_SUB_DscField7.setId("IS_SUB_DscField7");
        rootLayout.add(IS_SUB_DscField7);
        
        
       
	}

}
