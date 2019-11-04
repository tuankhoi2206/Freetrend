package ds.program.fvhr.ui.insurance;

import java.beans.PropertyEditor;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.hibernate.validator.Length;

//import oracle.jdbc.ttc7.Oopen;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.SelectField;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.dao.salary.SalaryDAO;
import ds.program.fvhr.domain.N_DIEULUAT;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_HINHPHAT;
import ds.program.fvhr.domain.N_KHOAN;
import ds.program.fvhr.domain.N_KYLUAT;
import ds.program.fvhr.domain.N_QUIT_REASON;
import ds.program.fvhr.domain.pk.N_KHOANPk;
import ds.program.fvhr.domain.pk.N_KYLUATPk;
import ds.program.fvhr.ui.insurance.OBJ_UTILITY;
import fv.util.Vni2Uni;
import dsc.echo2app.program.DefaultProgram;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import nextapp.echo2.app.event.ActionListener;

public class N_KYLUAT01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label DATE_HL_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_HL_DscDateField1;
    private nextapp.echo2.app.Label DATE_P_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_P_DscDateField2;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label ID_DIEU_CaptionLabel;
    
    //private dsc.echo2app.component.DscField ID_DIEU_DscField2;
    
    private nextapp.echo2.app.Label ID_KHOAN_CaptionLabel;
    //private dsc.echo2app.component.DscField ID_KHOAN_DscField3;
    private nextapp.echo2.app.Label ID_PHAT_CaptionLabel;
    //private dsc.echo2app.component.DscField ID_PHAT_DscField4;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField5;
    private nextapp.echo2.app.Label QDKL_CaptionLabel;
    private dsc.echo2app.component.DscField QDKL_DscField6;
    private SelectField sfHinhPhat;
    private SelectField sfDieuLuat;
    private SelectField sfKhoan;
    private Button DieuLuat_Button;
    private nextapp.echo2.app.Label FNAME_label1;
    private nextapp.echo2.app.Label FNAME_Label;

	/**
	 * Creates a new <code>N_KYLUAT01MDataContent</code>.
	 */
	public N_KYLUAT01MDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
		//add other init
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		
		
		EMPSN_DscField1.setWidth(new Extent(5, Extent.CM));
		
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
			
		DATE_P_DscDateField2.getDateChooser().setLocale(Locale.ENGLISH);
		DATE_P_DscDateField2.setDateFormat(sdf);		
		DATE_P_DscDateField2.setWidth(new Extent(5, Extent.CM));
		
		DATE_HL_DscDateField1.getDateChooser().setLocale(Locale.ENGLISH);
		DATE_HL_DscDateField1.setDateFormat(sdf);		
		DATE_HL_DscDateField1.setWidth(new Extent(5, Extent.CM));				
		/*
		sfDieuLuat = new SelectField();
		sfDieuLuat.setWidth(new Extent(5, Extent.CM));
		rootLayout.add(sfDieuLuat);		
		sfKhoan = new SelectField();
		sfKhoan.setWidth(new Extent(5, Extent.CM));
		*/
		/*
		DieuLuat_Button = new Button("Thêm Mới");
		rootLayout.add(DieuLuat_Button);
		*/		
		NOTE_DscField5.setWidth(new Extent(5, Extent.CM));
		QDKL_DscField6.setWidth(new Extent(5, Extent.CM));
		
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
				EMPSN_DscField1.setEnabled(true);				
			} else {
				//修改時，設定哪些元件Enable/Disable
				EMPSN_DscField1.setEnabled(false);
			}
		}

		//HIEN THI TEN KHI TIM KIEM
		OBJ_UTILITY obj = new OBJ_UTILITY();
		String fname=obj.findNameEmployee(EMPSN_DscField1.getText());						
		FNAME_Label.setText(fname);			
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
		return N_KYLUAT.class;
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
		N_KYLUAT data = (N_KYLUAT) getDataObject();
		String action ="";
		String note ="";
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		if (getProgram().getDataMode()==IProgram.DATAMODE_NEW){
			action = "INSERT";
			//sf.format(data.getDATE_P());
			
			note="Them moi phat tu " +sf.format(data.getDATE_P())+" den "+sf.format(data.getDATE_HL());
			note=note+". Vi pham dieu "+data.getID_DIEU()+", khoan "+data.getID_KHOAN()+", hinh phat "+data.getID_PHAT();		
		}
		else if (getProgram().getDataMode()==IProgram.DATAMODE_EDIT){
			action ="UDPATE";
			note="Sua thong tin phat tu " +sf.format(data.getDATE_P())+" den "+sf.format(data.getDATE_HL());
			note=note+". Vi pham dieu "+data.getID_DIEU()+", khoan "+data.getID_KHOAN()+", hinh phat "+data.getID_PHAT();	
		}			
		
		OBJ_UTILITY objU = new OBJ_UTILITY();
		try {
			objU.InputActionDaily(Application.getApp().getLoginInfo().getUserID(), "N_KY_LUAT", action, data.getEMPSN().toString(), note);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	//JPA - Java persistence API 
	//Hibernate
	@Override
	public boolean checkDataObject() {
		N_KYLUAT data = (N_KYLUAT) getDataObject();
		
		boolean ret = super.checkDataObject();
		if (ret) {
			InsuranceDAO ins = new InsuranceDAO();			
			String empsn = data.getEMPSN();
			String chuoiTB = ins.checkEmpsn(empsn);
			if (chuoiTB!=null) {
				setErrorMessage(chuoiTB);
				ret= false;
			}
			//kiem tra vung quan ly			
			else if (ins.checkQLyEmpsn(empsn)==false){
				setErrorMessage("Bạn không có quyền thao tác trên dữ liệu này.");				
				ret= false;			
			}
			else {
				// kiem tra khoa du lieu
				OBJ_UTILITY objU = new OBJ_UTILITY();
				boolean check =true;	
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String ngay	= sdf.format(data.getDATE_P());			
				String thang 	= ngay.substring(3,5);
				String nam		= ngay.substring(6,10);			
				check =ins.CheckKhoaDataMonth(empsn, thang, nam);
				if (check ==false){
					setErrorMessage("Đã khóa xử lý dữ liệu trong tháng");
					ret= false;
				}		
				else{
					IGenericDAO<N_KYLUAT, N_KYLUATPk> dao = Application.getApp().getDao(N_KYLUAT.class);
					if (getProgram().getDataMode()==IProgram.DATAMODE_NEW){
						N_KYLUATPk pk = new N_KYLUATPk(data.getEMPSN(), data.getDATE_P());
						if (dao.findById(pk)!=null){
							setErrorMessage("Dữ liệu đã tồn tại trước đó rồi, nhập liệu không thành công");
							ret= false;
						}
					}
					else{
						IGenericDAO<N_KHOAN, N_KHOANPk> khoanDao = Application.getApp().getDao(N_KHOAN.class);
						N_KHOANPk pkKhoan = new N_KHOANPk(data.getID_KHOAN(), data.getID_DIEU());
						if (khoanDao.findById(pkKhoan)==null){
							setErrorMessage("Khoản vi phạm không hợp lệ.");
							ret= false;
						}
					}
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
		moreInit();
		//1.註冊資料欄位之顯示方式
		registPropertyEditor();

		//2.設定資料欄位與 UI之 Binding資訊
		bindUI();
		
		return nRet;
	}
	
	private void moreInit(){
		FNAME_label1 = new Label();	
		rootLayout.add(FNAME_label1);
		FNAME_Label = new Label();
		FNAME_Label.setText("Ho va Ten");
		FNAME_Label.setForeground(Color.RED);
		rootLayout.add(FNAME_Label);		
	}
	
	private void registPropertyEditor() {
	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor
		//dsc.echo2app.propertyeditors.MappingPropertyEditor editor = null;
        getUIDataObjectBinder().registerCustomEditor(N_KYLUAT.class, "ID_PHAT", reasonKyLuatEditor());
        getUIDataObjectBinder().registerCustomEditor(N_KYLUAT.class, "ID_DIEU", DieuLuatEditor());
        getUIDataObjectBinder().registerCustomEditor(N_KYLUAT.class, "ID_KHOAN", KhoanEditor());
        // sua data khi hien thi len grid
        getUIDataObjectBinder().registerCustomEditor(N_KYLUAT.class, "DATE_P", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_KYLUAT.class, "DATE_HL", PropertyEditors.createDateEditor("dd/MM/yyyy"));
	}
	/*
	private MappingPropertyEditor kyluatEditor() { 
		MappingPropertyEditor e = new MappingPropertyEditor();
		e.put("Display", "value");//DB query -> name_col, value_col
		return e;
	}
	*/


	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
		getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);        
        getUIDataObjectBinder().addBindMap("DATE_P", DATE_P_DscDateField2, DATE_P_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_HL", DATE_HL_DscDateField1, DATE_HL_CaptionLabel);        
        //getUIDataObjectBinder().addBindMap("ID_DIEU", ID_DIEU_DscField2, ID_DIEU_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_DIEU", sfDieuLuat, ID_DIEU_CaptionLabel);
        //getUIDataObjectBinder().addBindMap("ID_KHOAN", ID_KHOAN_DscField3, ID_KHOAN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_KHOAN", sfKhoan, ID_KHOAN_CaptionLabel);
        //getUIDataObjectBinder().addBindMap("ID_PHAT", ID_PHAT_DscField4, ID_PHAT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_PHAT", sfHinhPhat, ID_PHAT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("QDKL", QDKL_DscField6, QDKL_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField5, NOTE_CaptionLabel);        
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	
	public MappingPropertyEditor reasonKyLuatEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_HINHPHAT, String> dao = Application.getApp().getDao(N_HINHPHAT.class);
		List<N_HINHPHAT> list = dao.findAll(1000);
		for (N_HINHPHAT r:list){
			e.put(Vni2Uni.convertToUnicode(r.getNAME_PHAT()), r.getID_PHAT());			
		}
		return e;
	}
	
	public MappingPropertyEditor DieuLuatEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_DIEULUAT, String> dao = Application.getApp().getDao(N_DIEULUAT.class);
		List<N_DIEULUAT> list = dao.findAll(1000);
		for (N_DIEULUAT r:list){
			e.put(Vni2Uni.convertToUnicode(r.getID_DIEU()), r.getID_DIEU());			
		}
		return e;
	}	
	
	public MappingPropertyEditor KhoanEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_KHOAN, String> dao = Application.getApp().getDao(N_KHOAN.class);
		List<N_KHOAN> list = dao.findAll(1000);
		for (N_KHOAN r:list){
			e.put(Vni2Uni.convertToUnicode(r.getID_KHOAN()), r.getID_KHOAN());			
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
		//N_KYLUAT data = (N_KYLUAT) getDataObject();
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
		//N_KYLUAT data = (N_KYLUAT) getDataObject();
		
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
		
		EMPSN_CaptionLabel = new Label();
		EMPSN_CaptionLabel.setText("N_KYLUAT.EMPSN");
		rootLayout.add(EMPSN_CaptionLabel);
		EMPSN_DscField1 = new DscField();
		EMPSN_DscField1.setId("EMPSN_DscField1");		
		rootLayout.add(EMPSN_DscField1);		
		
		ID_PHAT_CaptionLabel = new Label();
		ID_PHAT_CaptionLabel.setText("N_KYLUAT.ID_PHAT");
		rootLayout.add(ID_PHAT_CaptionLabel);		
		sfHinhPhat = new SelectField();
		sfHinhPhat.setWidth(new Extent(5, Extent.CM));
		rootLayout.add(sfHinhPhat);
		
		DATE_P_CaptionLabel = new Label();
		DATE_P_CaptionLabel.setText("N_KYLUAT.DATE_P");
		rootLayout.add(DATE_P_CaptionLabel);
		DATE_P_DscDateField2 = new DscDateField();
		DATE_P_DscDateField2.setId("DATE_P_DscDateField2");
		rootLayout.add(DATE_P_DscDateField2);
		
		DATE_HL_CaptionLabel = new Label();
		DATE_HL_CaptionLabel.setText("N_KYLUAT.DATE_HL");
		rootLayout.add(DATE_HL_CaptionLabel);
		DATE_HL_DscDateField1 = new DscDateField();
		DATE_HL_DscDateField1.setId("DATE_HL_DscDateField1");
		rootLayout.add(DATE_HL_DscDateField1);		

		ID_DIEU_CaptionLabel = new Label();
		ID_DIEU_CaptionLabel.setText("N_KYLUAT.ID_DIEU");
		rootLayout.add(ID_DIEU_CaptionLabel);
		
		sfDieuLuat = new SelectField();
		sfDieuLuat.setWidth(new Extent(5, Extent.CM));
		rootLayout.add(sfDieuLuat);
		
		ID_KHOAN_CaptionLabel = new Label();
		ID_KHOAN_CaptionLabel.setText("N_KYLUAT.ID_KHOAN");
		rootLayout.add(ID_KHOAN_CaptionLabel);
		
		sfKhoan = new SelectField();
		sfKhoan.setWidth(new Extent(5, Extent.CM));		
		rootLayout.add(sfKhoan);
		
		NOTE_CaptionLabel = new Label();
		NOTE_CaptionLabel.setText("N_KYLUAT.NOTE");
		rootLayout.add(NOTE_CaptionLabel);
		
		NOTE_DscField5 = new DscField();
		NOTE_DscField5.setId("NOTE_DscField5");		
		rootLayout.add(NOTE_DscField5);
		
		QDKL_CaptionLabel = new Label();
		QDKL_CaptionLabel.setText("N_KYLUAT.QDKL");
		rootLayout.add(QDKL_CaptionLabel);
		
		QDKL_DscField6 = new DscField();
		QDKL_DscField6.setId("QDKL_DscField6");		
		rootLayout.add(QDKL_DscField6);
	}

}
