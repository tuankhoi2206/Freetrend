package ds.program.fvhr.ui.insurance;

import it.businesslogic.ireport.gui.MessageBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_EMP_QUIT;
import ds.program.fvhr.domain.N_EMP_RELATIVE;
import ds.program.fvhr.domain.N_HINHPHAT;
import ds.program.fvhr.domain.N_RELATIVE_KIND;
import echopointng.DateChooser;
import echopointng.model.CalendarEvent;
import echopointng.model.CalendarSelectionListener;
import fv.util.Vni2Uni;
import fv.util.DateUtils;
import fv.util.VniEditor;

public class N_EMP_RELATIVE01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label IDKEY_CaptionLabel;
    private dsc.echo2app.component.DscField IDKEY_DscField1;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField2;
    private nextapp.echo2.app.Label NAME_RELATIVE_CaptionLabel;
    private dsc.echo2app.component.DscField NAME_RELATIVE_DscField3;
    private nextapp.echo2.app.Label BIRTHDAY_CaptionLabel;
    private dsc.echo2app.component.DscDateField BIRTHDAY_DscDateField1;
    private nextapp.echo2.app.Label SEX_CaptionLabel;
    //private dsc.echo2app.component.DscField SEX_DscField4;
    private nextapp.echo2.app.Label IDKIND_CaptionLabel;
    //private dsc.echo2app.component.DscField IDKIND_DscField5;
    private nextapp.echo2.app.Label CONFIRM_DATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField CONFIRM_DATE_DscDateField2;
    private nextapp.echo2.app.Label BEGINDATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField BEGINDATE_DscDateField3;
    private nextapp.echo2.app.Label ENDDATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField ENDDATE_DscDateField4;
    private nextapp.echo2.app.Label USER_UPDATE_CaptionLabel;
    private dsc.echo2app.component.DscField USER_UPDATE_DscField6;
    private nextapp.echo2.app.Label DATE_UPDATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_UPDATE_DscDateField5;
    //thay 2 dscField IDKIND va SEX thanh 2 selectField
    private SelectField sfLoaiNguoiThan;
    private SelectField sfGioiTinh;
    //bo sung them Lable the hien ho ten CNV
    private nextapp.echo2.app.Label FnameLabel_CaptionLabel;
    private nextapp.echo2.app.Label HOTEN_CaptionLabel;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private InsuranceDAO ins = new InsuranceDAO();    
    private DateUtils fvDateUtil = new DateUtils();
    
    
    private MappingPropertyEditor mappingID_KIND;
    private String sql;
	public String getSql() {
		return sql;
	}


	public void setSql(String sql) {
		this.sql = sql;
	}

   
	/**
	 * Creates a new <code>N_EMP_RELATIVE01MDataContent</code>.
	 */
	public N_EMP_RELATIVE01MDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
	}
 

	/*
	 * UI資料欄位屬性更新，一般是資料模式變換時會呼叫此method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doUIRefresh()
	 */@Override
	protected void doDataContentRefresh() {
		// TODO Auto-generated method stub
		
		super.doDataContentRefresh();
		 
	}
	
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
            IDKEY_DscField1.setEnabled(false);
            IDKEY_DscField1.setStyleName("Default.ReadonlyField");
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
		return ds.program.fvhr.domain.N_EMP_RELATIVE.class;
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
		//N_EMP_RELATIVE data = (N_EMP_RELATIVE) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		N_EMP_RELATIVE data = (N_EMP_RELATIVE) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			//使用者可在以下區域撰寫內容合理值的判斷程式碼
			//此單頭為readonly 直接return true
			String chuoiTBLoi = checkData(data);
			if (chuoiTBLoi==null) {
				ret = true;
			} else {
				setErrorMessage(chuoiTBLoi);
				ret= false;
			}
		}
 		return ret;
	}
	
	public String checkData(N_EMP_RELATIVE data) {
		// thoiGian : dd/mm/yyyy
		String chuoiTB = null;
		chuoiTB= ins.checkEmpsn(data.getEMPSN());
		if (chuoiTB!= null)
			return chuoiTB;
		else
		{
			if (ins.checkQLyEmpsn(data.getEMPSN())==false)
			{
				chuoiTB="Không có quyền thao tác.";
				return chuoiTB;
			}
			else
			{	
				String thoiGian = sdf.format(data.getBEGINDATE());
				String thang 	= thoiGian.substring(3,5);
				String nam		= thoiGian.substring(6,10);
				Boolean check =ins.CheckKhoaDataMonth(data.getEMPSN(), thang, nam);
				if (check ==false){
					chuoiTB="Đã khóa xử lý dữ liệu trong tháng";
					return chuoiTB;
				}
				else
				{
					check = ins.Check_exit("N_EMP_RELATIVE", "EMPSN", "NAME_RELATIVE", data.getEMPSN(), fv.util.Vni2Uni.convertToVNI(data.getNAME_RELATIVE()));
					if (check){
						chuoiTB="Thông tin đã tồn tại.";
						return chuoiTB;
					}
					else
					{
						if (data.getCONFIRM_DATE().compareTo(data.getBIRTHDAY())<0)
						{
							chuoiTB="Ngày đăng ký >= ngày sinh em bé";
							return chuoiTB;							
						}
					}
				}
			}
		}
		return chuoiTB;
	}


	/*
	 * 元件初始化Method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doInit()
	 */
	@Override
	protected int doInit() {
		int nRet = super.doInit();
		moreInit();
		//1.註冊資料欄位之顯示方式
		registPropertyEditor();

		//2.設定資料欄位與 UI之 Binding資訊
		bindUI();

		return nRet;
	}
	

	
	public void NgayDangKyChange()
	{	
		//System.out.println(CONFIRM_DATE_DscDateField2.getText());
		//System.out.println(BIRTHDAY_DscDateField1.getText());
		if (CONFIRM_DATE_DscDateField2.getText().equals("")==false && BIRTHDAY_DscDateField1.getText().equals("")==false)
		{
			if (CONFIRM_DATE_DscDateField2.getSelectedDate().compareTo(BIRTHDAY_DscDateField1.getSelectedDate())<0)
			{
				String chuoiTB="Ngày đăng ký >= ngày sinh em bé";
				setErrorMessage(chuoiTB);
				Application.getApp().showMessageDialog("Thông Báo", chuoiTB, MessageBox.OK);					
			}
			else
			{
				//if dang ky > ngay 20/m thi begindate = 01/m1, else la 01/m
				String thang = fvDateUtil.getMonth(CONFIRM_DATE_DscDateField2.getSelectedDate());
				String nam = fvDateUtil.getYear(CONFIRM_DATE_DscDateField2.getSelectedDate());
				String chuoiTB, tgKThuc, tgBDau;
				String ngSinh = sdf.format(BIRTHDAY_DscDateField1.getSelectedDate().getTime());				
				Calendar cal20 = Calendar.getInstance(); //bien tam de so sanh
				Calendar cal1 = Calendar.getInstance();// begin date
				cal20 = fvDateUtil.getCalendar("20/"+thang+"/"+nam);
				cal1  = fvDateUtil.getCalendar("01/"+thang+"/"+nam);// thoi gian bat dau luon la ngay 01 cua thang
				//so tuoi e be con duoc huong tro cap 20k/thang/Tre
				String soTuoi = ins.GetField("GIATRITHAMSO", "N_THAMSO", "TENTHAMSO", "", "", "TUOICONNHO", "", "");				
				//sotuoi*12 de ra tong so thang (1 tuoi/1 nam =12 thang nen * 12
				// vd NGAY SINH 01/05/2008
				String fieldGet = "to_char(add_months(to_Date('"+ngSinh+"','"+"dd/mm/yyyy"+"'),"+soTuoi+"*12)-1,'"+"dd/mm/yyyy"+"')";
				tgKThuc = ins.GetField(fieldGet, "DUAL", "", "", "", "", "", "");				

				if (CONFIRM_DATE_DscDateField2.getSelectedDate().compareTo(cal20)>0)
				{
					//tang thang len 1
					cal1.add(cal1.MONTH, 1);
				}
				//else = thang dang ky=thang confirm_date
				
				tgBDau	= sdf.format(cal1.getTime());				
				if (ins.ToDate(tgKThuc).compareTo(ins.ToDate(tgBDau))<0)
				{
					chuoiTB = "Thời gian kết thúc >= thời gian bắt đầu.";
					setErrorMessage(chuoiTB);
					Application.getApp().showMessageDialog("Thông Báo", chuoiTB, MessageBox.OK);
				}
				else
				{
					chuoiTB="Được hưởng trợ cấp kể từ "+sdf.format(cal1.getTime())+" đến "+tgKThuc;
					BEGINDATE_DscDateField3.setSelectedDate(cal1);
					ENDDATE_DscDateField4.setSelectedDate(ins.get_toCalendar(tgKThuc));
					USER_UPDATE_DscField6.setText(Application.getApp().getLoginInfo().getUserID());
					DATE_UPDATE_DscDateField5.setSelectedDate(Calendar.getInstance());
					Application.getApp().showMessageDialog("Thông Báo", chuoiTB, MessageBox.OK);
				}
			}
		}
		
	}
		
	
	private void moreInit(){
		USER_UPDATE_DscField6.setEnabled(false);
		DATE_UPDATE_DscDateField5.setEnabled(false);
		BEGINDATE_DscDateField3.setEnabled(false);
		ENDDATE_DscDateField4.setEnabled(false);
		//Nhan su FVL ko muon cap nhat gioi tinh :(, mai mot co can thong tin thi tu cap nhat vao he thong
		if (Application.getApp().getLoginInfo().getUserID().equals("FV.F2KDUNG")){
			sfGioiTinh.setEnabled(false);
		}
	
		
		
		EMPSN_DscField2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String fname,gTinh;				
				String soThe =EMPSN_DscField2.getText().toString(); 
				String chuoiTB = ins.checkEmpsn(soThe);
				if (chuoiTB!=null){					
					Application.getApp().showMessageDialog("Thông Báo", chuoiTB, MessageBox.OK);
				}
				else
				{					
					if (ins.checkQLyEmpsn(soThe)==false){
						chuoiTB= "Bạn không có quyền thao tác trên dữ liệu này.";	
						HOTEN_CaptionLabel.setText("");
						Application.getApp().showMessageDialog("Thông Báo", chuoiTB, MessageBox.OK);	
					}
					else
					{
						OBJ_UTILITY obj = new OBJ_UTILITY();
						fname=obj.findNameEmployee(soThe);
						gTinh = ins.GetField("SEX", "N_EMPLOYEE", "EMPSN", "", "", soThe, "", "");
						if(gTinh.equals("NU")==false){
							chuoiTB= "Không phải CNV nữ.";	
							HOTEN_CaptionLabel.setText("");
							Application.getApp().showMessageDialog("Thông Báo", chuoiTB, MessageBox.OK);							
						}
						else
						{
							HOTEN_CaptionLabel.setForeground(Color.BLUE);
							//HOTEN_CaptionLabel.setBackground(Color.BLUE);				
							//HOTEN_CaptionLabel.setText(fv.util.Vni2Uni.convertToUnicode(fname));
							HOTEN_CaptionLabel.setText(fname);						
							if(getProgram().getDataMode() == IProgram.DATAMODE_NEW)
							{			
								//auto tang idkey = empsn+xx
								int dem = Integer.parseInt(ins.GetField("nvl(max(substr(idkey,9,2)),0)", "n_emp_relative", "empsn", "", "", soThe, "", ""));
								//tang so lan len 
								dem=dem+1;
								
								String idkey ;
								if(String.valueOf(dem).length()<=1)
									idkey =soThe+"0"+String.valueOf(dem);
								else idkey =soThe+String.valueOf(dem);
								IDKEY_DscField1.setText(idkey);
							}	
						}
					}
				}				
			}
		});

	}
	
	private void registPropertyEditor() {
	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor
        //dsc.echo2app.propertyeditors.MappingPropertyEditor editor = null;
		VniEditor vni = new VniEditor();
		getUIDataObjectBinder().registerCustomEditor(N_EMP_RELATIVE.class, "NAME_RELATIVE", vni);
        getUIDataObjectBinder().registerCustomEditor(N_EMP_RELATIVE.class, "IDKIND", getLoaiNguoiThanEditor());
        getUIDataObjectBinder().registerCustomEditor(N_EMP_RELATIVE.class, "SEX", getGioiTinhEditor());
        
        getUIDataObjectBinder().registerCustomEditor(N_EMP_RELATIVE.class, "BIRTHDAY", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMP_RELATIVE.class, "CONFIRM_DATE", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMP_RELATIVE.class, "BEGINDATE", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMP_RELATIVE.class, "ENDDATE", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMP_RELATIVE.class, "DATE_UPDATE", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        
        mappingID_KIND = getLoaiNguoiThanEditor();
	}
	
	public MappingPropertyEditor getLoaiNguoiThanEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_RELATIVE_KIND, String> dao = Application.getApp().getDao(N_RELATIVE_KIND.class);
		List<N_RELATIVE_KIND> list = dao.findAll(1000);
		//List<N_RELATIVE_KIND> list = dao.find(1, "o.IDKIND", "6");
		for (N_RELATIVE_KIND r:list){
			e.put(fv.util.Vni2Uni.convertToUnicode(r.getNAMEKIND()), r.getIDKIND());			
		}
		return e;
	}	
	
	private MappingPropertyEditor getGioiTinhEditor(){
		MappingPropertyEditor editor = new MappingPropertyEditor();		
		editor.put("NAM", "NAM");
		editor.put("NU", "NU");
		return editor;
	}	
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("IDKEY", IDKEY_DscField1, IDKEY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField2, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NAME_RELATIVE", NAME_RELATIVE_DscField3, NAME_RELATIVE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BIRTHDAY", BIRTHDAY_DscDateField1, BIRTHDAY_CaptionLabel);
        getUIDataObjectBinder().addBindMap("SEX", sfGioiTinh, SEX_CaptionLabel);
        getUIDataObjectBinder().addBindMap("IDKIND", sfLoaiNguoiThan, IDKIND_CaptionLabel);
        getUIDataObjectBinder().addBindMap("CONFIRM_DATE", CONFIRM_DATE_DscDateField2, CONFIRM_DATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BEGINDATE", BEGINDATE_DscDateField3, BEGINDATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ENDDATE", ENDDATE_DscDateField4, ENDDATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("USER_UPDATE", USER_UPDATE_DscField6, USER_UPDATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_UPDATE", DATE_UPDATE_DscDateField5, DATE_UPDATE_CaptionLabel);
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
		//N_EMP_RELATIVE data = (N_EMP_RELATIVE) getDataObject();
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
		//N_EMP_RELATIVE data = (N_EMP_RELATIVE) getDataObject();
		//if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
		//    autoPrimaryKeyValue();
		//}
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
        EMPSN_CaptionLabel.setText("N_EMP_RELATIVE.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField2 = new dsc.echo2app.component.DscField();
        EMPSN_DscField2.setId("EMPSN_DscField2");
        rootLayout.add(EMPSN_DscField2);        
        IDKEY_CaptionLabel = new nextapp.echo2.app.Label();
        IDKEY_CaptionLabel.setText("N_EMP_RELATIVE.IDKEY");
        rootLayout.add(IDKEY_CaptionLabel);
        IDKEY_DscField1 = new dsc.echo2app.component.DscField();
        IDKEY_DscField1.setId("IDKEY_DscField1");
        rootLayout.add(IDKEY_DscField1);
        FnameLabel_CaptionLabel = new nextapp.echo2.app.Label();
        FnameLabel_CaptionLabel.setText("Họ và Tên CNV");
        rootLayout.add(FnameLabel_CaptionLabel);
        HOTEN_CaptionLabel = new nextapp.echo2.app.Label();
        rootLayout.add(HOTEN_CaptionLabel);
        
        NAME_RELATIVE_CaptionLabel = new nextapp.echo2.app.Label();
        NAME_RELATIVE_CaptionLabel.setText("N_EMP_RELATIVE.NAME_RELATIVE");
        rootLayout.add(NAME_RELATIVE_CaptionLabel);
        NAME_RELATIVE_DscField3 = new dsc.echo2app.component.DscField();
        NAME_RELATIVE_DscField3.setId("NAME_RELATIVE_DscField3");
        rootLayout.add(NAME_RELATIVE_DscField3);
        
        BIRTHDAY_CaptionLabel = new nextapp.echo2.app.Label();
        BIRTHDAY_CaptionLabel.setText("N_EMP_RELATIVE.BIRTHDAY");
        rootLayout.add(BIRTHDAY_CaptionLabel);
        
        BIRTHDAY_DscDateField1 = new dsc.echo2app.component.DscDateField();
        BIRTHDAY_DscDateField1.setId("BIRTHDAY_DscDateField1");
        BIRTHDAY_DscDateField1.getDateChooser().setLocale(Locale.ENGLISH);
        BIRTHDAY_DscDateField1.setDateFormat(sdf);        
        rootLayout.add(BIRTHDAY_DscDateField1);
        
        SEX_CaptionLabel = new nextapp.echo2.app.Label();
        SEX_CaptionLabel.setText("N_EMP_RELATIVE.SEX");
        rootLayout.add(SEX_CaptionLabel);
		sfGioiTinh = new SelectField();
		sfGioiTinh.setWidth(new Extent(3, Extent.CM));		
		rootLayout.add(sfGioiTinh);
		
        IDKIND_CaptionLabel = new nextapp.echo2.app.Label();
        IDKIND_CaptionLabel.setText("N_EMP_RELATIVE.IDKIND");
        rootLayout.add(IDKIND_CaptionLabel);		
		sfLoaiNguoiThan = new SelectField();
		sfLoaiNguoiThan.setWidth(new Extent(4, Extent.CM));
		sfLoaiNguoiThan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mappingID_KIND.setAsText(sfLoaiNguoiThan.getSelectedItem().toString());
				//((N_EMP_RELATIVE)getDataObject()).setIDKIND(Integer.valueOf(mappingID_KIND.getValue().toString()));
				((N_EMP_RELATIVE)getDataObject()).setIDKIND(mappingID_KIND.getValue().toString());
			}
		});
		rootLayout.add(sfLoaiNguoiThan);
		
        CONFIRM_DATE_CaptionLabel = new nextapp.echo2.app.Label();
        CONFIRM_DATE_CaptionLabel.setText("N_EMP_RELATIVE.CONFIRM_DATE");
        rootLayout.add(CONFIRM_DATE_CaptionLabel);
        
        CONFIRM_DATE_DscDateField2 = new dsc.echo2app.component.DscDateField();
        CONFIRM_DATE_DscDateField2.setId("CONFIRM_DATE_DscDateField2");
        CONFIRM_DATE_DscDateField2.getDateChooser().setLocale(Locale.ENGLISH);
        CONFIRM_DATE_DscDateField2.setDateFormat(sdf);
        rootLayout.add(CONFIRM_DATE_DscDateField2);
        
        CONFIRM_DATE_DscDateField2.getTextField().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NgayDangKyChange();
			}
		});
        
		CONFIRM_DATE_DscDateField2.getModel().addListener(new CalendarSelectionListener() {

			@Override
			public void displayedDateChange(CalendarEvent arg0) {
				//NgayDangKyChange();
				
			}

			@Override
			public void selectedDateChange(CalendarEvent arg0) {
				NgayDangKyChange();
			}
			
		});

        

        BEGINDATE_CaptionLabel = new nextapp.echo2.app.Label();
        BEGINDATE_CaptionLabel.setText("N_EMP_RELATIVE.BEGINDATE");
        rootLayout.add(BEGINDATE_CaptionLabel);
        BEGINDATE_DscDateField3 = new dsc.echo2app.component.DscDateField();
        BEGINDATE_DscDateField3.setId("BEGINDATE_DscDateField3");
        BEGINDATE_DscDateField3.getDateChooser().setLocale(Locale.ENGLISH);
        BEGINDATE_DscDateField3.setDateFormat(sdf);
        rootLayout.add(BEGINDATE_DscDateField3);
        ENDDATE_CaptionLabel = new nextapp.echo2.app.Label();
        ENDDATE_CaptionLabel.setText("N_EMP_RELATIVE.ENDDATE");
        rootLayout.add(ENDDATE_CaptionLabel);
        ENDDATE_DscDateField4 = new dsc.echo2app.component.DscDateField();
        ENDDATE_DscDateField4.setId("ENDDATE_DscDateField4");
        ENDDATE_DscDateField4.getDateChooser().setLocale(Locale.ENGLISH);
        ENDDATE_DscDateField4.setDateFormat(sdf);
        rootLayout.add(ENDDATE_DscDateField4);
        USER_UPDATE_CaptionLabel = new nextapp.echo2.app.Label();
        USER_UPDATE_CaptionLabel.setText("N_EMP_RELATIVE.USER_UPDATE");
        rootLayout.add(USER_UPDATE_CaptionLabel);
        USER_UPDATE_DscField6 = new dsc.echo2app.component.DscField();
        USER_UPDATE_DscField6.setId("USER_UPDATE_DscField6");
        rootLayout.add(USER_UPDATE_DscField6);
        DATE_UPDATE_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_UPDATE_CaptionLabel.setText("N_EMP_RELATIVE.DATE_UPDATE");
        rootLayout.add(DATE_UPDATE_CaptionLabel);
        DATE_UPDATE_DscDateField5 = new dsc.echo2app.component.DscDateField();
        DATE_UPDATE_DscDateField5.setId("DATE_UPDATE_DscDateField5");
        DATE_UPDATE_DscDateField5.getDateChooser().setLocale(Locale.ENGLISH);
        DATE_UPDATE_DscDateField5.setDateFormat(sdf);
        rootLayout.add(DATE_UPDATE_DscDateField5);
	}

}

