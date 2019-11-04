package ds.program.fvhr.ui.insurance;

import java.text.SimpleDateFormat;
import java.util.Locale;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.PropertyEditors;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_KYLUAT;
import ds.program.fvhr.domain.N_TIME_BEAR;
import fv.util.Vni2Uni;

public class N_TIME_BEAR01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label BB_DATES_CaptionLabel;
    private dsc.echo2app.component.DscDateField BB_DATES_DscDateField1;
    private nextapp.echo2.app.Label EE_DATES_CaptionLabel;
    private dsc.echo2app.component.DscDateField EE_DATES_DscDateField2;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField2;
    private nextapp.echo2.app.Label DATES_BEAR_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATES_BEAR_DscDateField3;
    private nextapp.echo2.app.Label DATES_GKS_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATES_GKS_DscDateField4;
	protected nextapp.echo2.app.Label FNAME_Label;

	/**
	 * Creates a new <code>N_TIME_BEAR01MDataContent</code>.
	 */
	public N_TIME_BEAR01MDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
		//add other init	
		 
		FNAME_Label = new nextapp.echo2.app.Label();		
        rootLayout.add(FNAME_Label);
        
		EMPSN_DscField1.setWidth(new Extent(5, Extent.CM));
		NOTE_DscField2.setWidth(new Extent(5, Extent.CM));
		EMPSN_DscField1.addActionListener(new ActionListener() {
			//xem lai cho nay ngay mai 22/11/2012
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String fname;
				OBJ_UTILITY obj = new OBJ_UTILITY();
				fname=obj.findNameEmployee(EMPSN_DscField1.getText().toString());
				FNAME_Label.setText(fname);				
			}
		});
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		BB_DATES_DscDateField1.getDateChooser().setLocale(Locale.ENGLISH);
		BB_DATES_DscDateField1.setDateFormat(sdf);
		BB_DATES_DscDateField1.setWidth(new Extent(5, Extent.CM));
		
		EE_DATES_DscDateField2.getDateChooser().setLocale(Locale.ENGLISH);
		EE_DATES_DscDateField2.setDateFormat(sdf);
		EE_DATES_DscDateField2.setWidth(new Extent(5, Extent.CM));
		
		DATES_BEAR_DscDateField3.getDateChooser().setLocale(Locale.ENGLISH);
		DATES_BEAR_DscDateField3.setDateFormat(sdf);
		DATES_BEAR_DscDateField3.setWidth(new Extent(5, Extent.CM));
		
		DATES_GKS_DscDateField4.getDateChooser().setLocale(Locale.ENGLISH);
		DATES_GKS_DscDateField4.setDateFormat(sdf);
		DATES_GKS_DscDateField4.setWidth(new Extent(5, Extent.CM));				
		
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
		return N_TIME_BEAR.class;
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
		//N_TIME_BEAR data = (N_TIME_BEAR) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		//N_TIME_BEAR data = (N_TIME_BEAR) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			//使用者可在以下區域撰寫內容合理值的判斷程式碼
			//此單頭為readonly 直接return true 
		}
		return ret;
	}
	
	/*
	 * 自動編號 
	 * <b>必須繼承改寫</b>
	 * @see dsc.echo2app.program.DataContent#autoPrimaryKeyValue()
	 */
	/*
	@Override
	protected boolean autoPrimaryKeyValue() {
		//使用者可以在以下區域撰寫產生新序號的程式碼並將新序號放入dataObject
		//N_TIME_BEAR data = (N_TIME_BEAR) getDataObject();
		//Connection conn = Application.getApp().getConnection();
		//Map keys = new HashMap();
		//Map filters = new HashMap();
		//String newValue = KeyNumberGenerator.generateNumber(conn,
		//		"$Y{2}$M{2}$D{2}$S{4}", 
		//		1,
		//		"N_TIME_BEAR", "{ID_FIELD}", 
		//		keys, filters, 
		//		true, false);
		
		//產生新序號並放入dataObject
		//data.set{ID_FIELD}(newValue);
		return true;
	}
*/
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
        
        // sua data khi hien thi len grid
        getUIDataObjectBinder().registerCustomEditor(N_TIME_BEAR.class, "BB_DATES", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_TIME_BEAR.class, "EE_DATES", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_TIME_BEAR.class, "DATES_BEAR", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_TIME_BEAR.class, "DATES_GKS", PropertyEditors.createDateEditor("dd/MM/yyyy"));        
        
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BB_DATES", BB_DATES_DscDateField1, BB_DATES_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EE_DATES", EE_DATES_DscDateField2, EE_DATES_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField2, NOTE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATES_BEAR", DATES_BEAR_DscDateField3, DATES_BEAR_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATES_GKS", DATES_GKS_DscDateField4, DATES_GKS_CaptionLabel);
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
		//N_TIME_BEAR data = (N_TIME_BEAR) getDataObject();
		//<如果要預先取號則在此加入>
		//autoPrimaryKeyValue();
		//使用者可在以下區域填入新增時內定的預設數值
		//<內定值>
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {
		//N_TIME_BEAR data = (N_TIME_BEAR) getDataObject();
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
		    autoPrimaryKeyValue();
		}
		//使用者可在以下區域做儲存資料物件至DataObjectSet之前處理
		//<儲存前處理>
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
        EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_CaptionLabel.setText("N_TIME_BEAR.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField1 = new dsc.echo2app.component.DscField();
        EMPSN_DscField1.setId("EMPSN_DscField1");
        rootLayout.add(EMPSN_DscField1);
        BB_DATES_CaptionLabel = new nextapp.echo2.app.Label();
        BB_DATES_CaptionLabel.setText("N_TIME_BEAR.B_DATES");
        rootLayout.add(BB_DATES_CaptionLabel);
        BB_DATES_DscDateField1 = new dsc.echo2app.component.DscDateField();
        BB_DATES_DscDateField1.setId("BB_DATES_DscDateField1");
        rootLayout.add(BB_DATES_DscDateField1);
        EE_DATES_CaptionLabel = new nextapp.echo2.app.Label();
        EE_DATES_CaptionLabel.setText("N_TIME_BEAR.E_DATES");
        rootLayout.add(EE_DATES_CaptionLabel);
        EE_DATES_DscDateField2 = new dsc.echo2app.component.DscDateField();
        EE_DATES_DscDateField2.setId("EE_DATES_DscDateField2");
        rootLayout.add(EE_DATES_DscDateField2);
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_TIME_BEAR.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField2 = new dsc.echo2app.component.DscField();
        NOTE_DscField2.setId("NOTE_DscField2");
        rootLayout.add(NOTE_DscField2);
        DATES_BEAR_CaptionLabel = new nextapp.echo2.app.Label();
        DATES_BEAR_CaptionLabel.setText("N_TIME_BEAR.DATES_BEAR");
        rootLayout.add(DATES_BEAR_CaptionLabel);
        DATES_BEAR_DscDateField3 = new dsc.echo2app.component.DscDateField();
        DATES_BEAR_DscDateField3.setId("DATES_BEAR_DscDateField3");
        rootLayout.add(DATES_BEAR_DscDateField3);
        DATES_GKS_CaptionLabel = new nextapp.echo2.app.Label();
        DATES_GKS_CaptionLabel.setText("N_TIME_BEAR.DATES_GKS");
        rootLayout.add(DATES_GKS_CaptionLabel);
        DATES_GKS_DscDateField4 = new dsc.echo2app.component.DscDateField();
        DATES_GKS_DscDateField4.setId("DATES_GKS_DscDateField4");
        rootLayout.add(DATES_GKS_DscDateField4);
	}

}
