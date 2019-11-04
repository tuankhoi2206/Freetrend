package ds.program.fvhr.tien.ui;

import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import ds.program.fvhr.domain.N_CHANGE_HOSPITAL;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_HEALTH;
import ds.program.fvhr.domain.N_HOSPITAL;
import ds.program.fvhr.domain.pk.N_CHANGE_HOSPITALPk;
import ds.program.fvhr.domain.pk.N_HOSPITALPk;
import ds.program.fvhr.util.OBJ_UTILITY;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscDateField;
import echopointng.DateField;
import fv.util.ApplicationHelper;
import fv.util.HRUtils;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.list.ListModel;
import nextapp.echo2.app.Color;

public class N_CHANGE_HOSPITAL01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label DATE_CHANGE_CaptionLabel;
    DscDateField DATE_CHANGE_DscDateField1;
    private nextapp.echo2.app.SelectField IDHOS_NEW_SelectField1;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField5;
    private nextapp.echo2.app.Label STATUS_CaptionLabel;
    private dsc.echo2app.component.DscField STATUS_DscField6;
	private Map<String, N_HOSPITAL> map;
	private SelectField IDPRO_NEW_SelectField2;
	private Label IDHOS_NEW_CaptionLabel;
	private Label IDPRO_NEW_CaptionLabel;
	private Label lblNameHos;
	private Label label1;
	private Label lblHosNameold;
	private Label label4;
	N_HEALTH objHealth=null;
	SimpleDateFormat simpeDate=new SimpleDateFormat("dd/MM/yyyy");
	N_CHANGE_HOSPITAL data = null;
	N_HOSPITAL objHos=null;
	private Label LablNameHos_=null;
	/**
	 * Creates a new <code>N_CHANGE_HOSPITAL01MDataContent</code>.
	 */
	public N_CHANGE_HOSPITAL01MDataContent() {
		super();

		// Add design-time configured components.
	
		moreInt();
		initComponents();		
		lblNameHos.setForeground(Color.RED);
		lblHosNameold.setForeground(Color.RED);
		DATE_CHANGE_DscDateField1.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		IDPRO_NEW_SelectField2.setSelectedIndex(0);
		
	}
	public void moreInt()
	{
		LablNameHos_=new Label();
		LablNameHos_.setForeground(Color.RED);
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
				lblHosNameold.setText("");
				//objHealth=null;
				lblNameHos.setText("");
				
				
			} 
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_EDIT) {
				//新增時，設定哪些元件Enable/Disable
				
				lblHosNameold.setText("");
				//objHealth=null;
				LablNameHos_.setText("");
				N_CHANGE_HOSPITAL data = (N_CHANGE_HOSPITAL) getDataObject();
				
				if(data.getSTATUS().equals("0"))
				{
					EMPSN_DscField1.setEnabled(false);
					DATE_CHANGE_DscDateField1.setEnabled(false);
					IDHOS_NEW_SelectField1.setEnabled(false);
					IDPRO_NEW_CaptionLabel.setEnabled(false);
					NOTE_DscField5.setEnabled(false);					 
				}				
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
		return N_CHANGE_HOSPITAL.class;
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
		//N_CHANGE_HOSPITAL data = (N_CHANGE_HOSPITAL) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		data=(N_CHANGE_HOSPITAL) getDataObject();
		data.setSTATUS("1");	
		boolean ret = super.checkDataObject();
		if (ret) {
			// kiem tra quyen thao tac
			HRUtils util = ApplicationHelper.getHRUtils();
			if (!util.getPermissionValidator().hasEmpsnPermission(data.getEMPSN())){
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Anh/chị không có quyền thao tác số thẻ này");
				return false;						
			}	
			IGenericDAO<N_HEALTH, String> dao=Application.getApp().getDao(N_HEALTH.class);			
			List<N_HEALTH> listHealth=dao.find(1, "FROM N_HEALTH WHERE EMPSN=? AND CLOCK =1", data.getEMPSN());
			if(listHealth.size()==0)
			{
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ này chưa tham gia BHYT");
				return false;
				
			}			
			IGenericDAO<N_CHANGE_HOSPITAL, N_CHANGE_HOSPITALPk> daoN_change_H=Application.getApp().getDao(N_CHANGE_HOSPITAL.class);
			N_CHANGE_HOSPITALPk objpk=new N_CHANGE_HOSPITALPk(data.getEMPSN(),data.getDATE_CHANGE(), data.getIDHOS_NEW(), data.getSTATUS(), data.getIDPRO_NEW());
			N_CHANGE_HOSPITAL objNChange_h=daoN_change_H.findById(objpk);
			if(objNChange_h!=null)
			{
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Thông tin số thẻ này đã cập nhập trùng thông tin");
				return false;
			}
			List<N_CHANGE_HOSPITAL> listNChange=daoN_change_H.find(1,"FROM N_CHANGE_HOSPITAL WHERE EMPSN=? AND STATUS='1'", data.getEMPSN());
			if(listNChange.size()>0)
			{
				N_CHANGE_HOSPITAL objData=listNChange.get(0);
				String sql="UPDATE N_CHANGE_HOSPITAL SET STATUS='0' WHERE EMPSN='"+objData.getEMPSN()+"' AND STATUS='1' ";
				
				 OBJ_UTILITY obj_util = new OBJ_UTILITY();
			     obj_util.Exe_Update_Sql(sql);
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
	//	IDPRO_NEW_SelectField2.setSelectedIndex(0);
		registPropertyEditor();

		//2.設定資料欄位與 UI之 Binding資訊
		bindUI();
	

		return nRet;
	}
	
	private void registPropertyEditor() {
	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor
        dsc.echo2app.propertyeditors.MappingPropertyEditor editor = null;
        this.getUIDataObjectBinder().registerCustomEditor(N_CHANGE_HOSPITAL.class,"DATE_CHANGE",PropertyEditors.createDateEditor("dd/MM/yyyy"));
        this.getUIDataObjectBinder().registerCustomEditor(N_CHANGE_HOSPITAL.class,"IDPRO_NEW",this.getProviceEditor());       
        this.getUIDataObjectBinder().registerCustomEditor(N_CHANGE_HOSPITAL.class,"IDHOS_NEW",this.getHospitalEditor());
       
        
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_CHANGE", DATE_CHANGE_DscDateField1, DATE_CHANGE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("IDPRO_NEW", IDPRO_NEW_SelectField2, IDPRO_NEW_CaptionLabel);
        getUIDataObjectBinder().addBindMap("IDHOS_NEW", IDHOS_NEW_SelectField1, IDHOS_NEW_CaptionLabel);     
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField5, NOTE_CaptionLabel);
       // getUIDataObjectBinder().addBindMap("STATUS", STATUS_DscField6, STATUS_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	private PropertyEditor getHospitalEditor()
	{
		
		String strIdpro="";
		if(IDPRO_NEW_SelectField2.getSelectedIndex()==0)
		{
			strIdpro="079";
			
		}
	  if(IDPRO_NEW_SelectField2.getSelectedIndex()==1)
	    {
		strIdpro="074";
		
		}
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_HOSPITAL, String> dao=Application.getApp().getDao(N_HOSPITAL.class);
		List<N_HOSPITAL> listHos=dao.find(1000,"FROM N_HOSPITAL WHERE ID_PROVINCE=?", strIdpro);
		for(N_HOSPITAL obj:listHos)
		{
			editor.put(fv.util.Vni2Uni.convertToUnicode(obj.getID_HOS()), obj.getID_HOS());
			
		}
		return editor;
		
	}
	private PropertyEditor getProviceEditor() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		
		N_HOSPITAL obj=new  N_HOSPITAL();
		map=new HashMap<String, N_HOSPITAL>();
		
		editor.put("TPHCM_079", "079");
		editor.put("BÌNH DƯƠNG_074","074");
		map.put("079", obj);
		map.put("074", obj);
		
		return editor;
	}
	

	/*
	 * 畫面Layout初始化Method
	 * 
	 * @see dsc.echo2app.program.DataContent#doLayout()
	 */
	@Override
	protected void doLayout() {
		lblHosNameold.setText("");
		objHealth=null;
		
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
		//N_CHANGE_HOSPITAL data = (N_CHANGE_HOSPITAL) getDataObject();
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
		
		int test=IDHOS_NEW_SelectField1.getSelectedIndex();
		int test1=IDPRO_NEW_SelectField2.getSelectedIndex();
		data.setIDHOS_NEW("");
		data.setIDHOS_NEW( objHos.getID_HOS());
		//data.setIDHOS_NEW(IDHOS_NEW_SelectField1.getSelectedItem().toString());
		
		Calendar cal=Calendar.getInstance();
		Date dtChange=data.getDATE_CHANGE();
		cal.setTimeInMillis(dtChange.getTime());		
		Calendar dateM=Calendar.getInstance();
		dateM.setTimeInMillis(cal.getTimeInMillis());
		dateM.set(Calendar.DAY_OF_MONTH, 1);		
		Date dateM_=dateM.getTime();	
		data.setDATE_CHANGE(dateM_);
		data.setIDHOS_OLD(objHealth.getID_HOS());
		data.setIDPRO_OLD(objHealth.getID_PRO());
		
	
		
		    
		
		//使用者可在以下區域做儲存資料物件至DataObjectSet之前處理
		//<儲存前處理>
	}

	private void changePro_N(ActionEvent e) {
		//TODO Implement.
		String strIdpro="";
	
		if(IDPRO_NEW_SelectField2.getSelectedIndex()==0)
		{
			strIdpro="079";
			
		}
		if(IDPRO_NEW_SelectField2.getSelectedIndex()==1)
		{
			strIdpro="074";
			
		}
		DefaultListModel listmodel=new DefaultListModel();
		IGenericDAO< N_HOSPITAL, String> dao=Application.getApp().getDao(N_HOSPITAL.class);
		List<N_HOSPITAL> listHospitals=dao.find(1000,"FROM N_HOSPITAL WHERE ID_PROVINCE=?", strIdpro);
		if(listHospitals.size()>0)
		{
			for(N_HOSPITAL obj: listHospitals)
			{
				listmodel.add(obj.getID_HOS());
			}	
			IDHOS_NEW_SelectField1.setModel(listmodel);
			loadLable();
		
			
			
		}
		
		
	}


	private void doChange_Hos(ActionEvent e) {
		loadLable();
		//TODO Implement.
	}
	private void loadLable()
	{
		LablNameHos_.setText("");
		if(IDHOS_NEW_SelectField1.getSelectedIndex()>=0)
		{
			String stridPro="";
			String stridHos="";
			if(IDHOS_NEW_SelectField1.getSelectedIndex()>=0)
			{
				stridHos=IDHOS_NEW_SelectField1.getSelectedItem().toString();
			}
		
			if(IDPRO_NEW_SelectField2.getSelectedIndex()==0)
			{
				stridPro="079";
			}
			if(IDPRO_NEW_SelectField2.getSelectedIndex()==1)
			{
				stridPro="074";
			}
			IGenericDAO< N_HOSPITAL, N_HOSPITALPk> dao=Application.getApp().getDao(N_HOSPITAL.class);
			N_HOSPITALPk objHospk=new N_HOSPITALPk(stridHos,stridPro);
			objHos=dao.findById(objHospk);
			if(objHos!=null)
			{
				LablNameHos_.setText(fv.util.Vni2Uni.convertToUnicode(objHos.getNAME_HOSPITAL()));			
			}
		}
	}


	private void doEmloyee(ActionEvent e) {
		String strEmpsn=EMPSN_DscField1.getText();
		
		if(!strEmpsn.equals(""))
		{
			
			HRUtils util = ApplicationHelper.getHRUtils();
			if (!util.getPermissionValidator().hasEmpsnPermission(strEmpsn)){
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Anh/chị không có quyền thao tác số thẻ này");
				return;						
			}	
			IGenericDAO<N_HEALTH, String> dao=Application.getApp().getDao(N_HEALTH.class);			
			List<N_HEALTH> listHealth=dao.find(1, "FROM N_HEALTH WHERE EMPSN=? AND CLOCK =1", strEmpsn);
			if(listHealth.size()==0)
			{
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ này chưa tham gia BHYT");
				return;
				
			}
			else
				
			{	
				objHealth=listHealth.get(0);	
				IGenericDAO<N_EMPLOYEE, String> daoEmpsn=Application.getApp().getDao(N_EMPLOYEE.class);
				N_EMPLOYEE objEmpsn=daoEmpsn.findById(strEmpsn);
				if(objEmpsn!=null)
				{
					String strHealthOld=fv.util.Vni2Uni.convertToUnicode((objEmpsn.getFULL_NAME()+" Mã BV cũ: "+objHealth.getID_HOS()+" Mã TP cũ:" +objHealth.getID_PRO()));
					lblHosNameold.setText(strHealthOld);
				}
				
				
				
			}
			loadLable();
			
		}
		//TODO Implement.
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
		EMPSN_CaptionLabel.setText("N_CHANGE_HOSPITAL.EMPSN");
		rootLayout.add(EMPSN_CaptionLabel);
		EMPSN_DscField1 = new DscField();
		EMPSN_DscField1.setId("EMPSN_DscField1");
		EMPSN_DscField1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doEmloyee(e);
			}
		});
		rootLayout.add(EMPSN_DscField1);
		DATE_CHANGE_CaptionLabel = new Label();
		DATE_CHANGE_CaptionLabel.setText("N_CHANGE_HOSPITAL.DATE_CHANGE");
		rootLayout.add(DATE_CHANGE_CaptionLabel);
		DATE_CHANGE_DscDateField1 = new DscDateField();
		DscField 			tf_date=new DscField();		
		DATE_CHANGE_DscDateField1.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		DATE_CHANGE_DscDateField1.setTextField(tf_date);
		DATE_CHANGE_DscDateField1.setId("DATE_CHANGE_DscDateField1");
		rootLayout.add(DATE_CHANGE_DscDateField1);
		Label label4 = new Label();
		label4.setText("Nơi DKKCB cũ");
		rootLayout.add(label4);
		 lblHosNameold = new Label();
		rootLayout.add(lblHosNameold);
		IDPRO_NEW_CaptionLabel = new Label();
		IDPRO_NEW_CaptionLabel.setText("N_CHANGE_HOSPITAL.IDPRO_NEW");
		rootLayout.add(IDPRO_NEW_CaptionLabel);
		IDPRO_NEW_SelectField2 = new SelectField();
		IDPRO_NEW_SelectField2.setId("IDPRO_NEW_SelectField2");
		IDPRO_NEW_SelectField2.setWidth(new Extent(100, Extent.PX));
		IDPRO_NEW_SelectField2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePro_N(e);
			}
		});
		rootLayout.add(IDPRO_NEW_SelectField2);
		IDHOS_NEW_CaptionLabel = new Label();
		IDHOS_NEW_CaptionLabel.setText("N_CHANGE_HOSPITAL.IDHOS_NEW");
		rootLayout.add(IDHOS_NEW_CaptionLabel);
		IDHOS_NEW_SelectField1 = new SelectField();
		IDHOS_NEW_SelectField1.setId("IDHOS_NEW_SelectField1");
		IDHOS_NEW_SelectField1.setWidth(new Extent(100, Extent.PX));
		IDHOS_NEW_SelectField1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doChange_Hos(e);
			}
		});
		rootLayout.add(IDHOS_NEW_SelectField1);
		NOTE_CaptionLabel = new Label();
		NOTE_CaptionLabel.setText("N_CHANGE_HOSPITAL.NOTE");
		rootLayout.add(NOTE_CaptionLabel);
		NOTE_DscField5 = new DscField();
		NOTE_DscField5.setId("NOTE_DscField5");
		rootLayout.add(NOTE_DscField5);
		Label label1 = new Label();
		rootLayout.add(label1);
		lblNameHos = new Label();		
		lblNameHos.setForeground(Color.RED);
		rootLayout.add(LablNameHos_);
		/*STATUS_CaptionLabel = new Label();
		STATUS_CaptionLabel.setText("N_CHANGE_HOSPITAL.STATUS");
		rootLayout.add(STATUS_CaptionLabel);
		STATUS_DscField6 = new DscField();
		STATUS_DscField6.setId("STATUS_DscField6");
		rootLayout.add(STATUS_DscField6);*/
	}

}
