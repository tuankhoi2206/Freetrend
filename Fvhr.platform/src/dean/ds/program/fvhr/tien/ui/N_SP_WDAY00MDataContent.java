package ds.program.fvhr.tien.ui;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.RadioButton;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_GET_DATA;
import ds.program.fvhr.domain.N_SP_WDAY;
import ds.program.fvhr.domain.pk.N_GET_DATAPk;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import ds.program.users.domain.DSPB02;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscDateField;
import fv.components.SelectItem;
import fv.util.ApplicationHelper;
import fv.util.FVGenericInfo;
import fv.util.HRUtils;
import fv.util.ListBinder;
import fv.util.Vni2Uni;
import fv.util.VniEditor;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.button.AbstractButton;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.app.Row;

public class N_SP_WDAY00MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private nextapp.echo2.app.Label DATE_SP_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_SP_DscDateField1;
    private nextapp.echo2.app.Label MULTIPLY_WD_CaptionLabel;
    private nextapp.echo2.app.SelectField MULTIPLY_WD_SelectField1;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField2;
	private DscField EMPSN_DscField1;
	private Label lblHovaten_;
	private N_SP_WDAY dataSpWay;
	private RadioButton radEmpsn;
	private Label labTest;
	private Label lblTest;
	private SelectField sfDept;
	private Label label3;
	private RadioButton radioButton1;
	private Label lblEmpsn;
	private DscField txtempsn;
	private RadioButton radioButton2;
	private SelectField sfFactory;
	private SelectField sfGroup_dept;
	private ResourceBundle resourceBundle;
	private String user_up = Application.getApp().getLoginInfo().getUserID();
	private String ma_user;
	N_SP_WDAY data =null;
	OBJ_UTILITY OUtil	= new OBJ_UTILITY();
	OBJ_EMPSN Obj_e		= new OBJ_EMPSN();
	String strfact="";	
	

	String strGroup="";
	String strDeptName="";
	Date date_Wday=null;	
	
	String stridDept="";
	SimpleDateFormat sp=new SimpleDateFormat("dd/MM/yyyy");
	/**
	 * Creates a new <code>N_SP_WDAY00MDataContent</code>.
	 */
	public N_SP_WDAY00MDataContent() {
		super();
		
		// Add design-time configured components.
		IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();
		initComponents();		
		DATE_SP_DscDateField1.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
	
	}
	public N_SP_WDAY getN_Sp_Wday()
	{
		return this.dataSpWay;
	}
	
	


	/*
	 * UI資料欄位屬性更新，一般是資料模式變換時會呼叫此method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doUIRefresh()
	 */
	@Override
	public void doUIRefresh() {
		super.doUIRefresh();
		lblHovaten_.setText("");
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
			}
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_EDIT) {
				EMPSN_DscField1.setEnabled(false);
				DATE_SP_DscDateField1.setEnabled(false);
			}
				
			else {
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
		return N_SP_WDAY.class;
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
		//N_SP_WDAY data = (N_SP_WDAY) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		N_SP_WDAY data = (N_SP_WDAY) getDataObject();	
		HRUtils util = ApplicationHelper.getHRUtils();
		boolean ret = true;
		if (ret) {
			
					if (!util.getPermissionValidator().hasEmpsnPermission(data.getEMPSN())){
						setErrorMessage("Anh/chị không có quyền thao tác số thẻ này");
						return false;
					}			
					Date date = data.getDATE_SP();
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(date.getTime());
					int month=cal.get(Calendar.MONTH)+1;
					int year = cal.get(Calendar.YEAR);
					IGenericDAO<N_GET_DATA, N_GET_DATAPk> dao=Application.getApp().getDao(N_GET_DATA.class);
					N_GET_DATA  EmpsData=dao.findById(new N_GET_DATAPk(data.getEMPSN(), month<10?"0"+month:""+month, year+""));
					if(EmpsData!=null)
					{
						if(Integer.valueOf(EmpsData.getLOCKED().toString())==1)
						{
							setErrorMessage("Dữ liệu nhân viên đã bị khóa");
							return false;
						}
					}	
			
			
			
		}
		return ret;
	}
	
	
	/*
	 * 自動編號 
	 * <b>必須繼承改寫</b>
	 * @see dsc.echo2app.program.DataContent#autoPrimaryKeyValue()
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
		getUIDataObjectBinder().registerCustomEditor(N_SP_WDAY.class, "MULTIPLY_WD", editorMultiply());
		getUIDataObjectBinder().registerCustomEditor(N_SP_WDAY.class, "DATE_SP", PropertyEditors.createDateEditor("dd/MM/yyyy"));
		getUIDataObjectBinder().registerCustomEditor(N_SP_WDAY.class, "NOTE",new VniEditor());
		getUIDataObjectBinder().registerCustomEditor(N_SP_WDAY.class, "DATE_UPDATED", PropertyEditors.createDateEditor("dd/MM/yyyy"));
		
	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor
       // dsc.echo2app.propertyeditors.MappingPropertyEditor editor = null;
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_SP", DATE_SP_DscDateField1, DATE_SP_CaptionLabel);
        getUIDataObjectBinder().addBindMap("MULTIPLY_WD", MULTIPLY_WD_SelectField1, MULTIPLY_WD_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField2, NOTE_CaptionLabel);
        
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	private MappingPropertyEditor editorMultiply()
	{
		MappingPropertyEditor e=new MappingPropertyEditor();
		e.put("1. Ngày CN tính công như ngày bình thường", 1L);
		e.put("2. Ngày bình thường tính như ngày CN ", 2L);
		e.put("3. Làm ngày lễ", 3L);
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
		//N_SP_WDAY data = (N_SP_WDAY) getDataObject();
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
	
	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	/*public void doSave()
	{
		N_SP_WDAY data = (N_SP_WDAY) getDataObject();
		OBJ_UTILITY OUtil	= new OBJ_UTILITY();
		OBJ_EMPSN Obj_e		= new OBJ_EMPSN();
		String strfact="";
	
			strfact = ListBinder.get(sfFactory).toString();	
	
		String strGroup=ListBinder.get(sfGroup_dept).toString();
		String strDeptName=ListBinder.get(sfDept).toString();
		Date date_Wday=null;
			
		date_Wday=data.getDATE_SP();
		String stridDept=sfDept.getId();
		if(checkDataObject())
		{
			ArrayList<String> listEmpsn=new ArrayList<String>();
			String user_up = Application.getApp().getLoginInfo().getUserID();
		
			if (!Obj_e.check_lock(user_up))//KIEM TRA KHOA CHUC NANG XLY DLIEU
			{
				return ;
			}
			if( !Obj_e.check_lock_month("",strfact,strGroup,strDeptName,"" ,date_Wday,"DEPSN",ma_user,null,null,null,null,null,null))
			{
				return;
			}else
			{
				listEmpsn = OUtil.getListEmpsn("",strfact,strGroup,strDeptName,stridDept,ma_user,"DEPSN");
			}
			
			
		}
	}*/
	@Override
	public void beforeSaveToDataObjectSet() {
		N_SP_WDAY data = (N_SP_WDAY) getDataObject();
		data.setUSER_UPDATED(Application.getApp().getLoginInfo().getUserID());
		data.setDATE_UPDATED(new Date());
		sp.format(data.getDATE_SP());
	}
	private void doEmployee(ActionEvent e) {
		//TODO Implement.
		//N_SP_WDAY data = (N_SP_WDAY) getDataObject();
		IGenericDAO<N_EMPLOYEE, String> dao=Application.getApp().getDao(N_EMPLOYEE.class);
		N_EMPLOYEE emsp=dao.findById(EMPSN_DscField1.getText());
		if(emsp==null)
		{
			lblHovaten_.setText("Số thẻ không tồn tại");			
		}
		else
		{
			String dept=emsp.getDEPSN();
			IGenericDAO<N_DEPARTMENT,String> objDep_dao=Application.getApp().getDao(N_DEPARTMENT.class);
			N_DEPARTMENT obj_Department=objDep_dao.findById(dept);
			if(obj_Department!=null)
			{
				lblHovaten_.setText(Vni2Uni.convertToUnicode(emsp.getFULL_NAME()+"."+obj_Department.getNAME_FACT()+"."+obj_Department.getNAME_DEPT_NAME()));
			}
			
		}				
	}


	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		resourceBundle = ResourceBundle.getBundle(
				"resource.localization.UICaption", ApplicationInstance
						.getActive().getLocale());
		rootLayout = new Grid();
		rootLayout.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(
				10, Extent.PX), new Extent(10, Extent.PX), new Extent(10,
				Extent.PX)));
		rootLayout.setWidth(new Extent(100, Extent.PERCENT));
		rootLayout.setSize(1);
		add(rootLayout);	
		Row row2 = new Row();
		rootLayout.add(row2);
		EMPSN_CaptionLabel = new Label();
		EMPSN_CaptionLabel.setText("N_SP_WDAY.EMPSN");
		rootLayout.add(EMPSN_CaptionLabel);		
		
		EMPSN_DscField1 = new DscField();
		EMPSN_DscField1.setId("EMPSN_DscField1");
		EMPSN_DscField1.setDisabledBackground(new Color(0xc0c0c0));
		EMPSN_DscField1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doEmployee(e);
			}
		});
		RowLayoutData lblEmpsnLayoutData = new RowLayoutData();
		lblEmpsnLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX)));
		EMPSN_CaptionLabel.setLayoutData(lblEmpsnLayoutData);
		row2.add(EMPSN_CaptionLabel);
		lblHovaten_ = new Label();
		lblHovaten_.setVisible(true);
		
		row2.add(EMPSN_DscField1);
		row2.add(lblHovaten_);		
		Row row7=new Row();
		rootLayout.add(row7);		
		DATE_SP_CaptionLabel = new Label();
		RowLayoutData labelDATE_SPLayoutData = new RowLayoutData();
		labelDATE_SPLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX)));
		DATE_SP_CaptionLabel.setLayoutData(labelDATE_SPLayoutData);
		DATE_SP_CaptionLabel.setText("N_SP_WDAY.DATE_SP");
		DATE_SP_DscDateField1 = new DscDateField();
		DATE_SP_DscDateField1.setId("DATE_SP_DscDateField1");
		row7.add(DATE_SP_CaptionLabel);		
		row7.add(DATE_SP_DscDateField1);
		Row row8=new Row();
		rootLayout.add(row8);
		MULTIPLY_WD_CaptionLabel = new Label();
		RowLayoutData labelMULTIPLY_WDLayoutData = new RowLayoutData();
		labelMULTIPLY_WDLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX)));
		MULTIPLY_WD_CaptionLabel.setLayoutData(labelMULTIPLY_WDLayoutData);
		MULTIPLY_WD_CaptionLabel.setText("N_SP_WDAY.MULTIPLY_WD");
		MULTIPLY_WD_SelectField1 = new SelectField();
		MULTIPLY_WD_SelectField1.setId("MULTIPLY_WD_SelectField1");
		MULTIPLY_WD_SelectField1.setWidth(new Extent(200, Extent.PX));
		row8.add(MULTIPLY_WD_CaptionLabel);		
		row8.add(MULTIPLY_WD_SelectField1);
		Row row9=new Row();
		rootLayout.add(row9);
		NOTE_CaptionLabel = new Label();
		RowLayoutData labelNOTELayoutData = new RowLayoutData();
		labelNOTELayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX)));
		NOTE_CaptionLabel.setLayoutData(labelNOTELayoutData);
		NOTE_CaptionLabel.setText("N_SP_WDAY.NOTE");
		NOTE_DscField2 = new DscField();
		NOTE_DscField2.setId("NOTE_DscField2");
		row9.add(NOTE_CaptionLabel);		
		row9.add(NOTE_DscField2);
		
		
	
		
		
		
	}

}
