package ds.program.fvhr.ui.workpoints;

import java.math.BigDecimal;
import java.util.List;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.layout.GridLayoutData;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_GET_DATA;
import echopointng.DirectHtml;
import fv.components.AnyRowNavigator;
import fv.util.BigDecimalEditor;
import fv.util.Vni2Uni;
import fv.util.VniEditor;

public class NGetData00MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label MONTHS_CaptionLabel;
    private dsc.echo2app.component.DscField MONTHS_DscField2;
    private nextapp.echo2.app.Label YEARS_CaptionLabel;
    private dsc.echo2app.component.DscField YEARS_DscField3;
    private nextapp.echo2.app.Label DUCLS_CaptionLabel;
    private dsc.echo2app.component.DscField DUCLS_DscField4;
    private nextapp.echo2.app.Label NDUCLS_CaptionLabel;
    private dsc.echo2app.component.DscField NDUCLS_DscField5;
    private nextapp.echo2.app.Label ADDHOL_CaptionLabel;
    private dsc.echo2app.component.DscField ADDHOL_DscField6;
    private nextapp.echo2.app.Label ADDCLS1_CaptionLabel;
    private dsc.echo2app.component.DscField ADDCLS1_DscField7;
    private nextapp.echo2.app.Label NADDCLS_CaptionLabel;
    private dsc.echo2app.component.DscField NADDCLS_DscField8;
    private nextapp.echo2.app.Label LATE_CaptionLabel;
    private dsc.echo2app.component.DscField LATE_DscField9;
    private nextapp.echo2.app.Label SIGN_CaptionLabel;
    private dsc.echo2app.component.DscField SIGN_DscField10;
    private nextapp.echo2.app.Label ACN_CaptionLabel;
    private dsc.echo2app.component.DscField ACN_DscField11;
    private nextapp.echo2.app.Label ACNM_CaptionLabel;
    private dsc.echo2app.component.DscField ACNM_DscField12;
    private nextapp.echo2.app.Label REST_CaptionLabel;
    private dsc.echo2app.component.DscField REST_DscField13;
    private nextapp.echo2.app.Label REST_PAY_CaptionLabel;
    private dsc.echo2app.component.DscField REST_PAY_DscField14;
    private nextapp.echo2.app.Label REST_SICK_CaptionLabel;
    private dsc.echo2app.component.DscField REST_SICK_DscField15;
    private nextapp.echo2.app.Label OTHER_CaptionLabel;
    private dsc.echo2app.component.DscField OTHER_DscField16;
    private nextapp.echo2.app.Label NWHOUR_CaptionLabel;
    private dsc.echo2app.component.DscField NWHOUR_DscField17;
    private nextapp.echo2.app.Label ADDHOLN_CaptionLabel;
    private dsc.echo2app.component.DscField ADDHOLN_DscField18;
    private nextapp.echo2.app.Label LMATER_CaptionLabel;
    private dsc.echo2app.component.DscField LMATER_DscField19;
    private nextapp.echo2.app.Label LOCKED_CaptionLabel;
    private dsc.echo2app.component.DscField LOCKED_DscField20;
    private nextapp.echo2.app.Label UP_DATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField UP_DATE_DscDateField1;
    private nextapp.echo2.app.Label UP_USER_CaptionLabel;
    private dsc.echo2app.component.DscField UP_USER_DscField22;
	private DataDailyBrowserContent dailyContent;
	private AnyRowNavigator navigator;
//	private Label DEPSN_CaptionLabel;
//	private DscField DEPSN_DscField1;
	private DirectHtml lblInfo;

	/**
	 * Creates a new <code>NGetData00MDataContent</code>.
	 */
	public NGetData00MDataContent() {
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
//            UP_DATE_DscDateField1.setEnabled(false);
//            UP_DATE_DscDateField1.setStyleName("Default.ReadonlyField");
//            UP_USER_DscField22.setEnabled(false);
//            UP_USER_DscField22.setStyleName("Default.ReadonlyField");
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
		return N_GET_DATA.class;
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
		//N_GET_DATA data = (N_GET_DATA) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		//N_GET_DATA data = (N_GET_DATA) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			//使用者可在以下區域撰寫內容合理值的判斷程式碼
			//此單頭為readonly 直接return true 
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
		dailyContent.initTableColumns(new String[]{"DATES","TT_IN","TT_MID","TT_OUT","TT_OVER","NOTE"});
		dailyContent.setRowsPerPage(10);
		
//		navigator.addActionListener(new ActionListener(){
//			private static final long serialVersionUID = 1L;
//			public void actionPerformed(ActionEvent e) {
//				
//			}
//		});
		
		return nRet;
	}
	
	private void registPropertyEditor() {
		BigDecimalEditor editor = new BigDecimalEditor(true, "##.#");
	    getUIDataObjectBinder().registerCustomEditor(N_GET_DATA.class, "DUCLS", editor);
	    getUIDataObjectBinder().registerCustomEditor(N_GET_DATA.class, "NDUCLS", editor);
	    getUIDataObjectBinder().registerCustomEditor(N_GET_DATA.class, "ADDHOL", editor);
	    getUIDataObjectBinder().registerCustomEditor(N_GET_DATA.class, "ADDCLS1", editor);
	    getUIDataObjectBinder().registerCustomEditor(N_GET_DATA.class, "NADDCLS", editor);
	    getUIDataObjectBinder().registerCustomEditor(N_GET_DATA.class, "LATE", editor);
	    getUIDataObjectBinder().registerCustomEditor(N_GET_DATA.class, "SIGN", editor);
	    getUIDataObjectBinder().registerCustomEditor(N_GET_DATA.class, "ACN", editor);
	    getUIDataObjectBinder().registerCustomEditor(N_GET_DATA.class, "ACNM", editor);
	    getUIDataObjectBinder().registerCustomEditor(N_GET_DATA.class, "REST", editor);
	    getUIDataObjectBinder().registerCustomEditor(N_GET_DATA.class, "REST_PAY", editor);
	    getUIDataObjectBinder().registerCustomEditor(N_GET_DATA.class, "REST_SICK", editor);
	    getUIDataObjectBinder().registerCustomEditor(N_GET_DATA.class, "OTHER", editor);
	    getUIDataObjectBinder().registerCustomEditor(N_GET_DATA.class, "NWHOUR", editor);
	    getUIDataObjectBinder().registerCustomEditor(N_GET_DATA.class, "ADDHOLN", editor);
	    getUIDataObjectBinder().registerCustomEditor(N_GET_DATA.class, "LMATER", editor);
	    getUIDataObjectBinder().registerCustomEditor(N_GET_DATA.class, "LOCKED", lockEditor());
	    getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "FNAME", new VniEditor());
	    getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "LNAME", new VniEditor());
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);
//        getUIDataObjectBinder().addBindMap("DEPSN", DEPSN_DscField1, DEPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("MONTHS", MONTHS_DscField2, MONTHS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("YEARS", YEARS_DscField3, YEARS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DUCLS", DUCLS_DscField4, DUCLS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NDUCLS", NDUCLS_DscField5, NDUCLS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ADDHOL", ADDHOL_DscField6, ADDHOL_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ADDCLS1", ADDCLS1_DscField7, ADDCLS1_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NADDCLS", NADDCLS_DscField8, NADDCLS_CaptionLabel);
        getUIDataObjectBinder().addBindMap("LATE", LATE_DscField9, LATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("SIGN", SIGN_DscField10, SIGN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ACN", ACN_DscField11, ACN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ACNM", ACNM_DscField12, ACNM_CaptionLabel);
        getUIDataObjectBinder().addBindMap("REST", REST_DscField13, REST_CaptionLabel);
        getUIDataObjectBinder().addBindMap("REST_PAY", REST_PAY_DscField14, REST_PAY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("REST_SICK", REST_SICK_DscField15, REST_SICK_CaptionLabel);
        getUIDataObjectBinder().addBindMap("OTHER", OTHER_DscField16, OTHER_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NWHOUR", NWHOUR_DscField17, NWHOUR_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ADDHOLN", ADDHOLN_DscField18, ADDHOLN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("LMATER", LMATER_DscField19, LMATER_CaptionLabel);
        getUIDataObjectBinder().addBindMap("LOCKED", LOCKED_DscField20, LOCKED_CaptionLabel);
//        getUIDataObjectBinder().addBindMap("UP_DATE", UP_DATE_DscDateField1, UP_DATE_CaptionLabel);
//        getUIDataObjectBinder().addBindMap("UP_USER", UP_USER_DscField22, UP_USER_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	
	private MappingPropertyEditor lockEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		e.put("Đã khóa", BigDecimal.ONE);
		e.put("Chưa khóa", BigDecimal.ZERO);
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
		//N_GET_DATA data = (N_GET_DATA) getDataObject();
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
		//N_GET_DATA data = (N_GET_DATA) getDataObject();
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
		    autoPrimaryKeyValue();
		}
		//使用者可在以下區域做儲存資料物件至DataObjectSet之前處理
		//<儲存前處理>
	}
	
	public DataDailyBrowserContent getDailyContent(){
		return dailyContent;
	}
	
	public AnyRowNavigator getRowNavigator(){
		return navigator;
	}
	
	//public void bindEmployeeInfo(String empsn){
	public void bindEmployeeInfo(String empsn, String mm, String yyyy){
		IGenericDAO dao = Application.getApp().getDao("VFT");
		//List list = dao.find(1, "select e.FNAME, e.LNAME, d.NAME_DEPT from N_EMPLOYEE e, N_DEPARTMENT d where e.DEPSN=d.ID_DEPT and e.EMPSN=?", new Object[]{empsn});
		//Update 30/08/2013, lay ten don vi theo N_GET_DATA
		List list = dao.find(1, "select e.FNAME, e.LNAME, d.NAME_DEPT from N_EMPLOYEE e, N_DEPARTMENT d, N_GET_DATA g " +
						" where g.DEPSN=d.ID_DEPT and e.EMPSN=g.EMPSN and e.EMPSN=? and g.MONTHS=? and g.YEARS=? "
						, new Object[]{empsn,mm,yyyy});		
		String s = "";
		if (list.size()>0){
			Object[] info = (Object[]) list.get(0);
			s = "Họ tên: <b>" + Vni2Uni.convertToUnicode(info[0]+"") + " " + Vni2Uni.convertToUnicode(info[1]+"") + "</b>. Đơn vị: <b>" + Vni2Uni.convertToUnicode(info[2]+"") + "</b>";
		}else s = "Không có thông tin";
		lblInfo.setText(s);
		firePropertyChange(null, null, null);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		SplitPane mainSplitPane = new SplitPane(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		mainSplitPane.setSeparatorPosition(new Extent(34));
		navigator = new AnyRowNavigator();
		mainSplitPane.add(navigator);
		SplitPane splitPane = new SplitPane();
		splitPane.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane.setSeparatorPosition(new Extent(200));
		splitPane.setResizable(true);
		mainSplitPane.add(splitPane);
		add(mainSplitPane);		
        rootLayout = new Grid();
        rootLayout.setWidth(new Extent(100, Extent.PERCENT));
        rootLayout.setSize(8);
        splitPane.add(rootLayout);
        EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_CaptionLabel.setText("N_GET_DATA.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        GridLayoutData infoLayout = new GridLayoutData();
        infoLayout.setColumnSpan(7);
        Row row = new Row();
        row.setLayoutData(infoLayout);
        EMPSN_DscField1 = new dsc.echo2app.component.DscField();
        EMPSN_DscField1.setWidth(new Extent(60));
        EMPSN_DscField1.setId("EMPSN_DscField1");
        row.add(EMPSN_DscField1);
        lblInfo = new DirectHtml();
        row.add(lblInfo);
        rootLayout.add(row);
//        DEPSN_CaptionLabel = new nextapp.echo2.app.Label();
//        rootLayout.add(DEPSN_CaptionLabel);
//        DEPSN_DscField1 = new dsc.echo2app.component.DscField();
//        DEPSN_DscField1.setWidth(new Extent(60));
//        DEPSN_DscField1.setId("DEPSN_DscField1");
//        rootLayout.add(DEPSN_DscField1);
        MONTHS_CaptionLabel = new nextapp.echo2.app.Label();
        MONTHS_CaptionLabel.setText("N_GET_DATA.MONTHS");
        rootLayout.add(MONTHS_CaptionLabel);
        MONTHS_DscField2 = new dsc.echo2app.component.DscField();
        MONTHS_DscField2.setWidth(new Extent(60));
        MONTHS_DscField2.setId("MONTHS_DscField2");
        rootLayout.add(MONTHS_DscField2);
        YEARS_CaptionLabel = new nextapp.echo2.app.Label();
        YEARS_CaptionLabel.setText("N_GET_DATA.YEARS");
        rootLayout.add(YEARS_CaptionLabel);
        YEARS_DscField3 = new dsc.echo2app.component.DscField();
        YEARS_DscField3.setWidth(new Extent(60));
        YEARS_DscField3.setId("YEARS_DscField3");
        rootLayout.add(YEARS_DscField3);
        DUCLS_CaptionLabel = new nextapp.echo2.app.Label();
        DUCLS_CaptionLabel.setText("N_GET_DATA.DUCLS");
        rootLayout.add(DUCLS_CaptionLabel);
        DUCLS_DscField4 = new dsc.echo2app.component.DscField();
        DUCLS_DscField4.setWidth(new Extent(60));
        DUCLS_DscField4.setDisabledBackground(new Color(0xFBF4D4));
        DUCLS_DscField4.setDisabledForeground(new Color(0x2A7FFF));
        DUCLS_DscField4.setId("DUCLS_DscField4");
        rootLayout.add(DUCLS_DscField4);
        NDUCLS_CaptionLabel = new nextapp.echo2.app.Label();
        NDUCLS_CaptionLabel.setText("N_GET_DATA.NDUCLS");
        rootLayout.add(NDUCLS_CaptionLabel);
        NDUCLS_DscField5 = new dsc.echo2app.component.DscField();
        NDUCLS_DscField5.setWidth(new Extent(60));
        NDUCLS_DscField5.setId("NDUCLS_DscField5");
        rootLayout.add(NDUCLS_DscField5);
        ADDHOL_CaptionLabel = new nextapp.echo2.app.Label();
        ADDHOL_CaptionLabel.setText("N_GET_DATA.ADDHOL");
        rootLayout.add(ADDHOL_CaptionLabel);
        ADDHOL_DscField6 = new dsc.echo2app.component.DscField();
        ADDHOL_DscField6.setWidth(new Extent(60));
        ADDHOL_DscField6.setId("ADDHOL_DscField6");
        rootLayout.add(ADDHOL_DscField6);
        ADDCLS1_CaptionLabel = new nextapp.echo2.app.Label();
        ADDCLS1_CaptionLabel.setText("N_GET_DATA.ADDCLS1");
        rootLayout.add(ADDCLS1_CaptionLabel);
        ADDCLS1_DscField7 = new dsc.echo2app.component.DscField();
        ADDCLS1_DscField7.setWidth(new Extent(60));
        ADDCLS1_DscField7.setId("ADDCLS1_DscField7");
        rootLayout.add(ADDCLS1_DscField7);
        NADDCLS_CaptionLabel = new nextapp.echo2.app.Label();
        NADDCLS_CaptionLabel.setText("N_GET_DATA.NADDCLS");
        rootLayout.add(NADDCLS_CaptionLabel);
        NADDCLS_DscField8 = new dsc.echo2app.component.DscField();
        NADDCLS_DscField8.setWidth(new Extent(60));
        NADDCLS_DscField8.setId("NADDCLS_DscField8");
        rootLayout.add(NADDCLS_DscField8);
        LATE_CaptionLabel = new nextapp.echo2.app.Label();
        LATE_CaptionLabel.setText("N_GET_DATA.LATE");
        rootLayout.add(LATE_CaptionLabel);
        LATE_DscField9 = new dsc.echo2app.component.DscField();
        LATE_DscField9.setWidth(new Extent(60));
        LATE_DscField9.setId("LATE_DscField9");
        rootLayout.add(LATE_DscField9);
        SIGN_CaptionLabel = new nextapp.echo2.app.Label();
        SIGN_CaptionLabel.setText("N_GET_DATA.SIGN");
        rootLayout.add(SIGN_CaptionLabel);
        SIGN_DscField10 = new dsc.echo2app.component.DscField();
        SIGN_DscField10.setWidth(new Extent(60));
        SIGN_DscField10.setId("SIGN_DscField10");
        rootLayout.add(SIGN_DscField10);
        ACN_CaptionLabel = new nextapp.echo2.app.Label();
        ACN_CaptionLabel.setText("N_GET_DATA.ACN");
        rootLayout.add(ACN_CaptionLabel);
        ACN_DscField11 = new dsc.echo2app.component.DscField();
        ACN_DscField11.setWidth(new Extent(60));
        ACN_DscField11.setId("ACN_DscField11");
        rootLayout.add(ACN_DscField11);
        ACNM_CaptionLabel = new nextapp.echo2.app.Label();
        ACNM_CaptionLabel.setText("N_GET_DATA.ACNM");
        rootLayout.add(ACNM_CaptionLabel);
        ACNM_DscField12 = new dsc.echo2app.component.DscField();
        ACNM_DscField12.setWidth(new Extent(60));
        ACNM_DscField12.setId("ACNM_DscField12");
        rootLayout.add(ACNM_DscField12);
        REST_CaptionLabel = new nextapp.echo2.app.Label();
        REST_CaptionLabel.setText("N_GET_DATA.REST");
        rootLayout.add(REST_CaptionLabel);
        REST_DscField13 = new dsc.echo2app.component.DscField();
        REST_DscField13.setWidth(new Extent(60));
        REST_DscField13.setId("REST_DscField13");
        rootLayout.add(REST_DscField13);
        REST_PAY_CaptionLabel = new nextapp.echo2.app.Label();
        REST_PAY_CaptionLabel.setText("N_GET_DATA.REST_PAY");
        rootLayout.add(REST_PAY_CaptionLabel);
        REST_PAY_DscField14 = new dsc.echo2app.component.DscField();
        REST_PAY_DscField14.setWidth(new Extent(60));
        REST_PAY_DscField14.setId("REST_PAY_DscField14");
        rootLayout.add(REST_PAY_DscField14);
        REST_SICK_CaptionLabel = new nextapp.echo2.app.Label();
        REST_SICK_CaptionLabel.setText("N_GET_DATA.REST_SICK");
        rootLayout.add(REST_SICK_CaptionLabel);
        REST_SICK_DscField15 = new dsc.echo2app.component.DscField();
        REST_SICK_DscField15.setWidth(new Extent(60));
        REST_SICK_DscField15.setId("REST_SICK_DscField15");
        rootLayout.add(REST_SICK_DscField15);
        OTHER_CaptionLabel = new nextapp.echo2.app.Label();
        OTHER_CaptionLabel.setText("N_GET_DATA.OTHER");
        rootLayout.add(OTHER_CaptionLabel);
        OTHER_DscField16 = new dsc.echo2app.component.DscField();
        OTHER_DscField16.setWidth(new Extent(60));
        OTHER_DscField16.setId("OTHER_DscField16");
        rootLayout.add(OTHER_DscField16);
        NWHOUR_CaptionLabel = new nextapp.echo2.app.Label();
        NWHOUR_CaptionLabel.setText("N_GET_DATA.NWHOUR");
        rootLayout.add(NWHOUR_CaptionLabel);
        NWHOUR_DscField17 = new dsc.echo2app.component.DscField();
        NWHOUR_DscField17.setWidth(new Extent(60));
        NWHOUR_DscField17.setId("NWHOUR_DscField17");
        rootLayout.add(NWHOUR_DscField17);
        ADDHOLN_CaptionLabel = new nextapp.echo2.app.Label();
        ADDHOLN_CaptionLabel.setText("N_GET_DATA.ADDHOLN");
        rootLayout.add(ADDHOLN_CaptionLabel);
        ADDHOLN_DscField18 = new dsc.echo2app.component.DscField();
        ADDHOLN_DscField18.setWidth(new Extent(60));
        ADDHOLN_DscField18.setId("ADDHOLN_DscField18");
        rootLayout.add(ADDHOLN_DscField18);
        LMATER_CaptionLabel = new nextapp.echo2.app.Label();
        LMATER_CaptionLabel.setText("N_GET_DATA.LMATER");
        rootLayout.add(LMATER_CaptionLabel);
        LMATER_DscField19 = new dsc.echo2app.component.DscField();
        LMATER_DscField19.setWidth(new Extent(60));
        LMATER_DscField19.setId("LMATER_DscField19");
        rootLayout.add(LMATER_DscField19);
        LOCKED_CaptionLabel = new nextapp.echo2.app.Label();
        LOCKED_CaptionLabel.setText("N_GET_DATA.LOCKED");
        rootLayout.add(LOCKED_CaptionLabel);
        LOCKED_DscField20 = new dsc.echo2app.component.DscField();
        LOCKED_DscField20.setWidth(new Extent(60));
        LOCKED_DscField20.setId("LOCKED_DscField20");
        rootLayout.add(LOCKED_DscField20);
//        UP_DATE_CaptionLabel = new nextapp.echo2.app.Label();
//        UP_DATE_CaptionLabel.setText("N_GET_DATA.UP_DATE");
//        rootLayout.add(UP_DATE_CaptionLabel);
//        UP_DATE_DscDateField1 = new dsc.echo2app.component.DscDateField();
//        UP_DATE_DscDateField1.setId("UP_DATE_DscDateField1");
//        rootLayout.add(UP_DATE_DscDateField1);
//        UP_USER_CaptionLabel = new nextapp.echo2.app.Label();
//        UP_USER_CaptionLabel.setText("N_GET_DATA.UP_USER");
//        rootLayout.add(UP_USER_CaptionLabel);
//        UP_USER_DscField22 = new dsc.echo2app.component.DscField();
//        UP_USER_DscField22.setId("UP_USER_DscField22");
//        rootLayout.add(UP_USER_DscField22);
        dailyContent = new DataDailyBrowserContent();
        splitPane.add(dailyContent);
	}

}
