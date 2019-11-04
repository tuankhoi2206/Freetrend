package ds.program.fvhr.son.ui.job;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;


import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.TextArea;
import nextapp.echo2.app.list.DefaultListModel;
import fv.util.Vni2Uni;
import ds.program.fvhr.domain.N_EMP_INFO;
import ds.program.fvhr.domain.N_EMP_JOB_DETAIL;
import ds.program.fvhr.domain.N_EMP_POSS_DETAIL;
import ds.program.fvhr.domain.N_JOB;
import ds.program.fvhr.domain.N_POSS;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.dao.DataObjectSet;
import dsc.dao.DataRelation;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.DetailContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import echopointng.ComboBox;

public class N_EMP_JOB_01MDetailContent0 extends DetailContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label DATE_B_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_B_DscDateField1;
    private nextapp.echo2.app.Label DATE_E_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_E_DscDateField2;
    private nextapp.echo2.app.Label DATE_UPDATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_UPDATE_DscDateField3;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label ID_JOB_CaptionLabel;
    private SelectField ID_JOB_DscField2;
    private nextapp.echo2.app.Label USER_UPDATE_CaptionLabel;
    private dsc.echo2app.component.DscField USER_UPDATE_DscField3;
    private Label	NOTE_CaptionLabel;
    private TextArea	NOTE_TextArea;
    
    private ComboBox txt_code_job;
//    private SelectField txt_kind_job;
//    private SelectField txt_infact;
    
    private String _user_up = "";
    private Date _date_up  ;
    
    Calendar _ca = Calendar.getInstance();
    SimpleDateFormat _sf = new SimpleDateFormat("dd/MM/yyyy");
    ObjUtility		_obj_util;
	public N_EMP_JOB_DETAIL _deletedRecord;
	/**
	 * Creates a new <code>N_EMP_JOB_01MDetailContent0</code>.
	 */
	public N_EMP_JOB_01MDetailContent0() {
		super();
		
		_obj_util	= new ObjUtility();
		InitInfo();
		// Add design-time configured components.
		initComponents();
	}
	
	/* (non-Javadoc)
	 * 建立資料關聯物件
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DetailContent#createRelation()
	 */

	public DataRelation createRelation() {
		return new DataRelation("EMPSN", "EMPSN");
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	

	public boolean checkDataObject() {
		
		N_EMP_JOB_DETAIL data = (N_EMP_JOB_DETAIL) getDataObject();
		
		String code_job_ 	= txt_code_job.getText() == null ? "" : txt_code_job.getText();
		String id_job		= "";
		HashMap	map_ 		= Map_name_job();
		
		if(data.getID_JOB() == null || data.getID_JOB().equals("")){
			
			id_job	= 	(String) map_.get(code_job_);
			data.setID_JOB(id_job);
			
		}
		
		boolean ret = super.checkDataObject();
		if (ret) {
			
			ObjUtility obj_util_ = new ObjUtility();
			// kiem tra ngay moi nhap vao phai la ngay lon nhat 
			String sql_ = 
				"select max(t.date_b) from n_emp_job_detail t where t.empsn = '" +data.getEMPSN()+"'";
			Date date_e_max_ = null;	// ngay lon nhat trong csdl
										// so sanh voi ngay data.getDATE_B : ngay nhap vao
			try{
				date_e_max_ = obj_util_.Exe_Sql_Obj(sql_)== null ? null : (Date)obj_util_.Exe_Sql_Obj(sql_); 
				// = null la khong can kiem tra
				
			}catch (Exception e) {  // truong hop chua co ngay thang trong co so du lieu
				date_e_max_ = new Date();
			}
			
													// <=
			if((this.getDataMode() != IProgram.DATAMODE_EDIT) && date_e_max_!=null && (data.getDATE_B().before(date_e_max_) 
										|| data.getDATE_B().equals(date_e_max_)) ){  // ngay moi nhap vao ma nho hon ngay da co trong csdl la sai
				setErrorMessage("Nhập ngày bắt đầu sai quy tắc");
				return false;
			}
			
		}
		return ret;
		
	}

	/*
	 * UI資料欄位屬性更新，一般是資料模式變換時會呼叫此method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doUIRefresh()
	 */

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
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW || this.getProgram().getDataMode() == IProgram.DATAMODE_EDIT) {
				ID_JOB_DscField2.setEnabled(false);
				DATE_E_DscDateField2.setEnabled(false);
				DATE_UPDATE_DscDateField3.setEnabled(false);
				USER_UPDATE_DscField3.setEnabled(false);
				
			} else {
				//修改時，設定哪些元件Enable/Disable
			}
		}

		//7.<資料權限管控>
		N_EMP_JOB_DETAIL data = (N_EMP_JOB_DETAIL) getDataObject();
		String id_key = "";
		if(data !=null 
				&& (this.getDataMode() == IProgram.DATAMODE_NONE || this.getDataMode() == IProgram.DATAMODE_BROWSER) 
		  ){
				
			id_key = data.getID_JOB();
			if(id_key != null ){
				IGenericDAO<N_JOB, String> obj_dao = Application.getApp().getDao(N_JOB.class);
				N_JOB obj_find = obj_dao.findById(id_key);
				txt_code_job.setText(Vni2Uni.convertToUnicode(obj_find.getCODE_JOB()));
			}
			
		}
		
		/*if(data !=null && data.getNOTE() != null)
			NOTE_TextArea.setText(Vni2Uni.convertToUnicode(data.getNOTE()));*/
		
		//自動設定元件風格
		getUIDataObjectBinder().doDataBindUIStyle();
	}

	/*
	 * 取得該畫面的DataObject Class
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#getDataObjectClass()
	 */
	public Class getDataObjectClass() {
		return N_EMP_JOB_DETAIL.class;
	}

	
	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */

	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值
		//N_EMP_JOB_DETAIL data = (N_EMP_JOB_DETAIL) getDataObject();
	}


	/*
	 * 元件初始化Method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doInit()
	 */

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
        ObjUtility obj_util_ = new ObjUtility();
//        MappingPropertyEditor editor_job_ = new MappingPropertyEditor();
        MappingPropertyEditor editor_job_ = obj_util_.Get_MapEditor_JOB_NAME();
        this.getUIDataObjectBinder().registerCustomEditor(N_EMP_JOB_DETAIL.class,"ID_JOB",editor_job_);
        this.getUIDataObjectBinder().registerCustomEditor(N_EMP_JOB_DETAIL.class,"DATE_B",PropertyEditors.createDateEditor("dd/MM/yyyy"));
        this.getUIDataObjectBinder().registerCustomEditor(N_EMP_JOB_DETAIL.class,"DATE_E",PropertyEditors.createDateEditor("dd/MM/yyyy"));
        this.getUIDataObjectBinder().registerCustomEditor(N_EMP_JOB_DETAIL.class,"DATE_UPDATE",PropertyEditors.createDateEditor("dd/MM/yyyy"));
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("DATE_B", DATE_B_DscDateField1, DATE_B_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_E", DATE_E_DscDateField2, DATE_E_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_UPDATE", DATE_UPDATE_DscDateField3, DATE_UPDATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_JOB", ID_JOB_DscField2, ID_JOB_CaptionLabel);
        getUIDataObjectBinder().addBindMap("USER_UPDATE", USER_UPDATE_DscField3, USER_UPDATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_TextArea, NOTE_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}

	/*
	 * 畫面Layout初始化Method
	 * 
	 * @see dsc.echo2app.program.DataContent#doLayout()
	 */

	protected void doLayout() {
		super.doLayout();
		//<<從此以下加入使用者程式>>
		
		//DATE_B_DscDateField1.getTextField().setText(_sf.format(new Date()));
	}

	/*
	 * 做資料新增時內定預設數值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doNewDefaulData()
	 */

	protected void doNewDefaulData() {
		
		N_EMP_JOB_DETAIL data = (N_EMP_JOB_DETAIL) getDataObject();
		//<如果要預先取號則在此加入>
		//autoSequenceKeyValue();
		//使用者可在以下區域填入新增時內定的預設數值
		//<內定值>
		
		//<<填入與單頭關聯值>>
        
		Date date_now_01	= ObjUtility.MONTH_NOW("01", _date_up);
		
		N_EMP_INFO pdata = (N_EMP_INFO) getMasterDataContent().getDataObject();
        data.setEMPSN(pdata.getEMPSN());
        
        
        data.setDATE_B(date_now_01);
		data.setUSER_UPDATE(_user_up);
		data.setDATE_UPDATE(_date_up);
		
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */

	public void beforeSaveToDataObjectSet() {
		
		N_EMP_JOB_DETAIL data = (N_EMP_JOB_DETAIL) getDataObject();
		
		String code_job_ 	= txt_code_job.getText() == null ? "" : txt_code_job.getText();
		String id_job		= "";
		HashMap	map_ 		= Map_name_job();
		
		if(data.getID_JOB() == null || data.getID_JOB().equals("")){
			
			id_job	= 	(String) map_.get(code_job_);
			data.setID_JOB(id_job);
			
		}
		data.setNOTE(Vni2Uni.convertToVNI(data.getNOTE()));
		System.out.println(data.getNOTE());
		data.setUSER_UPDATE(_user_up);
		data.setDATE_UPDATE(_date_up);
		
		
	}

	

	public boolean doDelete() {
		// TODO Auto-generated method stub
		boolean flag_del = false;
		N_EMP_JOB_DETAIL data = (N_EMP_JOB_DETAIL) getDataObject();
		if(data.getDATE_E()!=null){
			ObjUtility.ShowMessageError("Không thể xóa dữ liệu vì đã có ngày kết thúc");
			flag_del = false;
		}else{
			 // goi procedure thu thi ngay ket thuc cong viec gan nhat ve null
			
			if (super.doDelete()){
				try {
					//store origin delete data
					this._deletedRecord = (N_EMP_JOB_DETAIL) BeanUtils.cloneBean(data);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				flag_del = true;
			}
			
		}
		return flag_del;
		
	}

	
	

	protected String[] getBrowserDisplayColumns() {
		//<<從此以下加入使用者程式>>
		//<<資料瀏覽欄位>>
        return new String[]{"EMPSN","ID_JOB","DATE_B","DATE_E","NOTE","USER_UPDATE","DATE_UPDATE"};
	}

	private DefaultListModel model_code_job(){
		DefaultListModel model_ = new DefaultListModel();
		IGenericDAO<N_JOB, String> objDao = Application.getApp().getDao(N_JOB.class);
		List<N_JOB> listJob = objDao.findAll(5000);
		
		model_.add("");
		
	/*	for(N_JOB obj : listJob){
			System.out.println(obj.getID_KEY());
			model_.add(obj.getID_KEY());
		}
		*/
		HashMap<String,String> map_ = Map_name_job();
		
		Set	set_	= map_.entrySet();
		
		Iterator i  = set_.iterator();
		while(i.hasNext()){
			Map.Entry me_ = (Entry) i.next();
			model_.add(me_.getKey());
			
		}
		
		return model_;		
	}
	
	private HashMap<String, String> Map_name_job() {
		HashMap<String,  String> map_ = new HashMap<String, String>();
		String sql = " select t.id_key,t.code_job,t.kind_job,t.in_fact,t.money from n_job t ";
		
		List<Object[]> list_arr = _obj_util.Exe_Sql_nfield_nrow(sql, 5);
		String key_ 	= "";
		String values_ 	= "";
		for(Object[] arr : list_arr){
			arr[4]		= arr[4]==null?"0":arr[4];
			key_		= (String) arr[1] +"_" + arr[2] + "_" + arr[3] + " (" + arr[4]+ ")";
			values_		= (String) arr[0];
			map_.put(Vni2Uni.convertToUnicode(key_), values_);
//			map_.put(key_, values_);
		}
		
		return map_;
	}

	DefaultListModel model_kind_job(){
		DefaultListModel model_	= new DefaultListModel();
		model_.add("A");
		model_.add("B");
		model_.add("C");
		model_.add("D");
		return model_;
	}
	
	DefaultListModel model_infact_job(){
		DefaultListModel model_	= new DefaultListModel();
		
		model_.add("");
		model_ = (DefaultListModel) _obj_util.Get_Model_Fact();
		
		return model_;
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
        
        ID_JOB_CaptionLabel = new nextapp.echo2.app.Label();
        ID_JOB_CaptionLabel.setText("N_EMP_JOB_DETAIL.ID_JOB");
        rootLayout.add(ID_JOB_CaptionLabel);
        ID_JOB_DscField2 = new SelectField();
        ID_JOB_DscField2.setId("ID_JOB_DscField2");
        ID_JOB_DscField2.setWidth(new Extent(260));
        rootLayout.add(ID_JOB_DscField2);

        rootLayout.add(new Label());
        rootLayout.add(new Label());
        
        //-----------------------------------
        
        rootLayout.add(new Label("CODE_JOB"));
        
        txt_code_job	= new ComboBox();
        txt_code_job.setWidth(new Extent(260));
        txt_code_job.setListModel(model_code_job());
        rootLayout.add(txt_code_job);
        
       
        
        
        DATE_UPDATE_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_UPDATE_CaptionLabel.setText("N_EMP_JOB_DETAIL.DATE_UPDATE");
        rootLayout.add(DATE_UPDATE_CaptionLabel);
        DATE_UPDATE_DscDateField3 = new dsc.echo2app.component.DscDateField();
        DATE_UPDATE_DscDateField3.setId("DATE_UPDATE_DscDateField3");
        DATE_UPDATE_DscDateField3.setDateFormat(_sf);
        DATE_UPDATE_DscDateField3.getDateChooser().setLocale(Locale.ENGLISH);
        DATE_UPDATE_DscDateField3.setDisplayedDate(_ca);
        rootLayout.add(DATE_UPDATE_DscDateField3);
        
        //------------------------------------
       
        //rootLayout.add(new Label("KIND"));
       /* txt_kind_job = new SelectField();
        txt_kind_job.setWidth(new Extent(160));
        txt_kind_job.setModel(model_kind_job());*/
        //rootLayout.add(txt_kind_job);
        
        rootLayout.add(new Label());
        rootLayout.add(new Label());
 
        
        DATE_E_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_E_CaptionLabel.setText("N_EMP_JOB_DETAIL.DATE_E");
        rootLayout.add(DATE_E_CaptionLabel);
        DATE_E_DscDateField2 = new dsc.echo2app.component.DscDateField();
        DATE_E_DscDateField2.setId("DATE_E_DscDateField2");
        DATE_E_DscDateField2.setDateFormat(_sf);
        DATE_E_DscDateField2.getDateChooser().setLocale(Locale.ENGLISH);
        DATE_E_DscDateField2.setDisplayedDate(_ca);
        rootLayout.add(DATE_E_DscDateField2);
        
       //---------------------------------------
        
        //rootLayout.add(new Label("IN FACT"));
       /* txt_infact = new SelectField();
        txt_infact.setWidth(new Extent(160));
        txt_infact.setModel(model_infact_job());*/
        //rootLayout.add(txt_infact);
        
        DATE_B_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_B_CaptionLabel.setText("N_EMP_JOB_DETAIL.DATE_B");
        rootLayout.add(DATE_B_CaptionLabel);
        DATE_B_DscDateField1 = new dsc.echo2app.component.DscDateField();
        DATE_B_DscDateField1.setId("DATE_B_DscDateField1");
        DATE_B_DscDateField1.setDateFormat(_sf);
        DATE_B_DscDateField1.getDateChooser().setLocale(Locale.ENGLISH);
        rootLayout.add(DATE_B_DscDateField1);
        
        
        USER_UPDATE_CaptionLabel = new nextapp.echo2.app.Label();
        USER_UPDATE_CaptionLabel.setText("N_EMP_JOB_DETAIL.USER_UPDATE");
        rootLayout.add(USER_UPDATE_CaptionLabel);
        USER_UPDATE_DscField3 = new dsc.echo2app.component.DscField();
        USER_UPDATE_DscField3.setId("USER_UPDATE_DscField3");
        USER_UPDATE_DscField3.setWidth(new Extent(160));
        rootLayout.add(USER_UPDATE_DscField3);
        
        
        //-----------------------------
        
        NOTE_CaptionLabel	= new Label();
        NOTE_CaptionLabel.setText("N_EMP_JOB_DETAIL.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_TextArea	= new TextArea();
        NOTE_TextArea.setWidth(new Extent(260));
        rootLayout.add(NOTE_TextArea);
        
        EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_CaptionLabel.setText("N_EMP_JOB_DETAIL.EMPSN");
        //rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField1 = new dsc.echo2app.component.DscField();
        EMPSN_DscField1.setId("EMPSN_DscField1");
        //rootLayout.add(EMPSN_DscField1);
	}
	
	private void InitInfo(){
		
		Date date_b_ 		= _ca.getTime();
        Date date_b_01_	= ObjUtility.MONTH_NOW("01", date_b_);

		
		_user_up 	= Application.getApp().getLoginInfo().getUserID();
		_date_up	= null;
		try {
			_date_up = _sf.parse(_sf.format(_ca.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
