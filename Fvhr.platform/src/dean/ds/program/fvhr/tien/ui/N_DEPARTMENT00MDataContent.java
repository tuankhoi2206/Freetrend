package ds.program.fvhr.tien.ui;

import java.beans.PropertyEditor;
import java.math.BigDecimal;
import java.util.List;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.DetailContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import ds.program.fvhr.domain.N_CHANGE_HOSPITAL;
import ds.program.fvhr.domain.N_DEPT_NAME;
import ds.program.fvhr.domain.N_FACTORY;
import ds.program.fvhr.domain.N_GROUPDIRECT_INDIRECT;
import ds.program.fvhr.domain.N_GROUP_DEPT_NIKE;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_GROUP_DEPT;
import ds.program.fvhr.domain.N_SHIFT;
import ds.program.fvhr.domain.N_SPDEPT_LIST;
import ds.program.fvhr.domain.N_USERS_LIST;
import fv.components.SelectItem;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.VniEditor;

public class N_DEPARTMENT00MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label ID_DEPT_CaptionLabel;
    private dsc.echo2app.component.DscField ID_DEPT_DscField1;
    private nextapp.echo2.app.Label NAME_DEPT_NAME_CaptionLabel;
    private nextapp.echo2.app.SelectField NAME_DEPT_NAME_SelectField1;
    private nextapp.echo2.app.Label NAME_FACT_CaptionLabel;
    private nextapp.echo2.app.SelectField NAME_FACT_SelectField2;
    private nextapp.echo2.app.Label NAME_GROUP_CaptionLabel;
    private nextapp.echo2.app.SelectField NAME_GROUP_SelectField3;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField2;
    private nextapp.echo2.app.Label ID_SPDEPT_CaptionLabel;
    private nextapp.echo2.app.SelectField ID_SPDEPT_SelectField4;
    private nextapp.echo2.app.Label ID_GROUPNI_CaptionLabel;
    private nextapp.echo2.app.SelectField ID_GROUPNI_SelectField5;
    private nextapp.echo2.app.Label ID_GROUP_DIRECT_CaptionLabel;
    private nextapp.echo2.app.SelectField ID_GROUP_DIRECT_SelectField6;
	private Label lblGhiChu;
	private dsc.echo2app.component.DscField txtGhiChu;
    private String strUser_login="";

	/**
	 * Creates a new <code>N_DEPARTMENT00MDataContent</code>.
	 */
	public N_DEPARTMENT00MDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
		moreInt();
		strUser_login=Application.getApp().getLoginInfo().getUserID();
		
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
				ID_DEPT_DscField1.setEnabled(true);
			}
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_EDIT) {
				//新增時，設定哪些元件Enable/Disable
				ID_DEPT_DscField1.setEnabled(false);
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
		return N_DEPARTMENT.class;
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
		//N_DEPARTMENT data = (N_DEPARTMENT) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		N_DEPARTMENT data = (N_DEPARTMENT) getDataObject();
		String name_dept=fv.util.Vni2Uni.convertToVNI(data.getNAME_FACT()+"."+data.getNAME_GROUP()+"."+data.getNAME_DEPT_NAME());
		data.setNAME_DEPT(name_dept);
		boolean ret = super.checkDataObject();
		if (ret) {
			// kiem tra quyen thao tac
			IGenericDAO<N_USERS_LIST,String> objdao=Application.getApp().getDao(N_USERS_LIST.class);
			List<N_USERS_LIST> listuser_limit=objdao.find(1,"from N_USERS_LIST where USER_ID=?", strUser_login);
			if(listuser_limit.size()>0)
			{
				N_USERS_LIST objN_user_list=listuser_limit.get(0);
				if(objN_user_list.getCLOCK().equals("Y"))
				{
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Bạn đã bị khóa chức năng thay đổi dữ liệu");
					return false;
				}
			}
			// kiem tra su ton tai
			if(this.getProgram().getDataMode() != IProgram.DATAMODE_EDIT) {
				IGenericDAO<N_DEPARTMENT, String> objdaodept=Application.getApp().getDao(N_DEPARTMENT.class);
				
				List<N_DEPARTMENT> listdep=objdaodept.find(1,"  from N_DEPARTMENT where ID_DEPT=? ",data.getID_DEPT());
				if(listdep.size()>0)
				{
					N_DEPARTMENT objdept=listdep.get(0);
					if(objdept!=null)
					{
						Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, " Đơn vị đã tồn tại");
						return false;	
					}
				}
				List<N_DEPARTMENT> listdeptname=objdaodept.find(1, "from N_DEPARTMENT where NAME_DEPT like '%"+data.getNAME_DEPT()+"%'");
				if(listdeptname.size()>0)
				{
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, " Tên đơn vị đã tồn tại");
					return false;
				}
				
				
			} 
			
			// KIEM TRA NOTE
			if(data.getNOTE()!=null)
			{
				if(!data.getNOTE().equals("MAY")&&!data.getNOTE().equals("BV")&&!data.getNOTE().equals("null"))
				{
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Xem lại phần ghi chú");
					return false;
				}	
			}
			
			// kiem tra ma don vi
			if(data.getID_DEPT().equals("")||data.getID_DEPT().length()!=5)
			{
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Mã Đơn vị phải 5 ký tự");
				return false;

			}
			// kiem tra xuong
			if(data.getNAME_FACT()==null)
			{
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Vui lòng lựa chọn tên xưởng");
				return false;

			}
			if(data.getNAME_GROUP()==null)
			{
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Vui lòng lựa chọn tên nhóm đơn vị");
				return false;

			}
			if(data.getNAME_DEPT_NAME()==null)
			{
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Vui lòng lựa chọn tên đơn vị");
				return false;

			}
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
	/*@Override
	protected boolean autoPrimaryKeyValue() {
		//使用者可以在以下區域撰寫產生新序號的程式碼並將新序號放入dataObject
		//N_DEPARTMENT data = (N_DEPARTMENT) getDataObject();
		//Connection conn = Application.getApp().getConnection();
		//Map keys = new HashMap();
		//Map filters = new HashMap();
		//String newValue = KeyNumberGenerator.generateNumber(conn,
		//		"$Y{2}$M{2}$D{2}$S{4}", 
		//		1,
		//		"N_DEPARTMENT", "{ID_FIELD}", 
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
        this.getUIDataObjectBinder().registerCustomEditor(N_DEPARTMENT.class,"NAME_DEPT_NAME",this.getdept_name());
        this.getUIDataObjectBinder().registerCustomEditor(N_DEPARTMENT.class,"NAME_FACT",this.getfactory_name());
        this.getUIDataObjectBinder().registerCustomEditor(N_DEPARTMENT.class,"NAME_GROUP",this.getGroupdept_name());
        this.getUIDataObjectBinder().registerCustomEditor(N_DEPARTMENT.class,"ID_SPDEPT",this.getID_SpDept());
        this.getUIDataObjectBinder().registerCustomEditor(N_DEPARTMENT.class,"ID_GROUPNI",this.getGroupdept_nike());
        this.getUIDataObjectBinder().registerCustomEditor(N_DEPARTMENT.class,"ID_GROUP_DIRECT",this.getGroupdirect());        
        this.getUIDataObjectBinder().registerCustomEditor(N_DEPARTMENT.class, "NAME_DEPT",new VniEditor());
        
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("ID_DEPT", ID_DEPT_DscField1, ID_DEPT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NAME_DEPT_NAME", NAME_DEPT_NAME_SelectField1, NAME_DEPT_NAME_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NAME_FACT", NAME_FACT_SelectField2, NAME_FACT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NAME_GROUP", NAME_GROUP_SelectField3, NAME_GROUP_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField2, NOTE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_SPDEPT", ID_SPDEPT_SelectField4, ID_SPDEPT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_GROUPNI", ID_GROUPNI_SelectField5, ID_GROUPNI_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_GROUP_DIRECT", ID_GROUP_DIRECT_SelectField6, ID_GROUP_DIRECT_CaptionLabel);
      //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	private PropertyEditor getID_SpDept()
	{
		MappingPropertyEditor editor=new MappingPropertyEditor();
		IGenericDAO<N_SPDEPT_LIST, String> objdaospList=Application.getApp().getDao(N_SPDEPT_LIST.class);
		List<N_SPDEPT_LIST> listspdept=objdaospList.findAll(100);
		editor.put("");
		for(N_SPDEPT_LIST objspdept : listspdept)
		{
			editor.put(fv.util.Vni2Uni.convertToUnicode(objspdept.getNAME_SPDEPT()), objspdept.getID_SPDEPT());
		}
		
		return editor;
	}
	private PropertyEditor getGroupdept_nike()
	{
		MappingPropertyEditor editor=new MappingPropertyEditor();
		IGenericDAO<ds.program.fvhr.domain.N_GROUP_DEPT_NIKE, String> objdaoGroupdept=Application.getApp().getDao(ds.program.fvhr.domain.N_GROUP_DEPT_NIKE.class);
		List<ds.program.fvhr.domain.N_GROUP_DEPT_NIKE> listgroupdeptnike =objdaoGroupdept.find(50, "from N_GROUP_DEPT_NIKE ORDER BY NOTE ");
		editor.put("");
		for(N_GROUP_DEPT_NIKE objGroupdeptnike :listgroupdeptnike)
		{
			editor.put(fv.util.Vni2Uni.convertToUnicode(objGroupdeptnike.getNOTE()), objGroupdeptnike.getID_GROUPNI());
		}
			
		
		return editor;
	}
	private PropertyEditor getGroupdirect()
	{
		MappingPropertyEditor editor=new MappingPropertyEditor();
		IGenericDAO<N_GROUPDIRECT_INDIRECT, String> objdaoGROUPDIRECT=Application.getApp().getDao(N_GROUPDIRECT_INDIRECT.class);
		List<N_GROUPDIRECT_INDIRECT> listGROUPDIRECT =objdaoGROUPDIRECT.find(50, "from N_GROUPDIRECT_INDIRECT order by ID_GROUPKIND ");
		editor.put("");
		for(N_GROUPDIRECT_INDIRECT objGROUPDIRECT_INDIRECT :listGROUPDIRECT)
		{
			editor.put(fv.util.Vni2Uni.convertToUnicode(objGROUPDIRECT_INDIRECT.getMO_TA()), objGROUPDIRECT_INDIRECT.getID_DETAIL());
		}
			
		
		return editor;
	}
	private PropertyEditor getdept_name()
	{
		MappingPropertyEditor editor=new MappingPropertyEditor();
		
		IGenericDAO<N_DEPT_NAME, String> objdaoN_DEPT_NAME=Application.getApp().getDao(N_DEPT_NAME.class);
		List<N_DEPT_NAME> listN_DEPT_NAME =objdaoN_DEPT_NAME.findAll(500);
		for(N_DEPT_NAME objN_DEPT_NAME:listN_DEPT_NAME)
		{
			editor.put(fv.util.Vni2Uni.convertToUnicode(objN_DEPT_NAME.getDEPT_NAME()),objN_DEPT_NAME.getDEPT_NAME());
		}
			
		
		return editor;
	}
	private PropertyEditor getfactory_name()
	{
		MappingPropertyEditor editor=new MappingPropertyEditor();
		
		IGenericDAO<N_FACTORY, String> objdaoN_FACTORY=Application.getApp().getDao(N_FACTORY.class);
		List<N_FACTORY> listN_FACTORY =objdaoN_FACTORY.findAll(50);
		for(N_FACTORY objN_FACTORY:listN_FACTORY)
		{
			editor.put(fv.util.Vni2Uni.convertToUnicode(objN_FACTORY.getNAME_FACT()),objN_FACTORY.getNAME_FACT());
		}
			
		
		return editor;
	}
	private PropertyEditor getGroupdept_name()
	{
		MappingPropertyEditor editor=new MappingPropertyEditor();
		
		IGenericDAO<N_GROUP_DEPT, String> objdaoN_GROUP_DEPT=Application.getApp().getDao(N_GROUP_DEPT.class);
		List<N_GROUP_DEPT> listN_GROUP_DEPT =objdaoN_GROUP_DEPT.find(100, "from N_GROUP_DEPT ");
		for(N_GROUP_DEPT objN_GROUP_DEPT:listN_GROUP_DEPT)
		{
			editor.put(fv.util.Vni2Uni.convertToUnicode(objN_GROUP_DEPT.getNAME_GROUP()),objN_GROUP_DEPT.getNAME_GROUP());
		}
		return editor;
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
		//N_DEPARTMENT data = (N_DEPARTMENT) getDataObject();
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
		N_DEPARTMENT data = (N_DEPARTMENT) getDataObject();
		
		//使用者可在以下區域做儲存資料物件至DataObjectSet之前處理
		//<儲存前處理>
	}
	

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	
	
	protected void doID_SPDEPT(ActionEvent e) {
		
		txtGhiChu.setText("");
		if(ID_SPDEPT_SelectField4.getSelectedIndex()>0)
		{
			String id_spdept="";
			id_spdept=ID_SPDEPT_SelectField4.getSelectedItem().toString();
			
			IGenericDAO<N_SPDEPT_LIST, String> objdaoidSPDept=Application.getApp().getDao(N_SPDEPT_LIST.class);
			List<N_SPDEPT_LIST> listspdept=objdaoidSPDept.find(1,"from N_SPDEPT_LIST where NAME_SPDEPT like '%"+id_spdept+"%'");
			String strGhichu="";
			if(listspdept.size()>0)
			{
				N_SPDEPT_LIST objspdept=listspdept.get(0);
				String ROT=objspdept.getROT();
				String AOT =objspdept.getAOT();
				String FOT=objspdept.getFOT();
				
				if(ROT.equals("1"))
				{
					strGhichu=strGhichu+"Có đăng ký tăng ca. ";
				}
				if(AOT.equals("1"))
				{
					strGhichu=strGhichu+"Có tính tăng ca sau. ";
				}
				if(AOT.equals("0"))
				{
					strGhichu=strGhichu+"Không tính tăng ca sau. ";
				}	
				if(FOT.equals("1"))
				{
					strGhichu=strGhichu+"Có tính tăng ca trước. ";
				}
				if(FOT.equals("0"))
				{
					strGhichu=strGhichu+"Không tính tăng ca trước";
				}	
			}
			txtGhiChu.setText(strGhichu);
		}
		
	}
	private void moreInt()
	{
		
			  rootLayout.add(ID_DEPT_CaptionLabel);
			  rootLayout.add(ID_DEPT_DscField1);
			  rootLayout.add(ID_SPDEPT_CaptionLabel);	      
		      rootLayout.add(ID_SPDEPT_SelectField4);
		      rootLayout.add(NAME_FACT_CaptionLabel);		       
		      rootLayout.add(NAME_FACT_SelectField2);
		      rootLayout.add(ID_GROUPNI_CaptionLabel);		       
		      rootLayout.add(ID_GROUPNI_SelectField5);
		      rootLayout.add(NAME_GROUP_CaptionLabel);		       
		      rootLayout.add(NAME_GROUP_SelectField3);
		      rootLayout.add(ID_GROUP_DIRECT_CaptionLabel);
		      rootLayout.add(ID_GROUP_DIRECT_SelectField6);
		      rootLayout.add(NAME_DEPT_NAME_CaptionLabel);		       
		      rootLayout.add(NAME_DEPT_NAME_SelectField1);
		      rootLayout.add(NOTE_CaptionLabel);		       
		      rootLayout.add(NOTE_DscField2);
		       
		       
			NAME_DEPT_NAME_SelectField1.setWidth(new Extent(200));
			NAME_FACT_SelectField2.setWidth(new Extent(200));
			NAME_GROUP_SelectField3.setWidth(new Extent(200));
			ID_SPDEPT_SelectField4.setWidth(new Extent(200));
			ID_GROUPNI_SelectField5.setWidth(new Extent(200));
			ID_GROUP_DIRECT_SelectField6.setWidth(new Extent(200));
		    lblGhiChu = new nextapp.echo2.app.Label();
		    lblGhiChu.setText("Ghi Chú");
	        rootLayout.add(lblGhiChu);
	        txtGhiChu = new DscField();
	        txtGhiChu.setWidth(new Extent(300));
	        txtGhiChu.setHeight(new Extent(60));
	        rootLayout.add(txtGhiChu);
	        ID_SPDEPT_SelectField4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doID_SPDEPT(e);
				}
			});
	        
	}
	private void initComponents() {
        rootLayout = new Grid();
        rootLayout.setWidth(new Extent(100, Extent.PERCENT));
        rootLayout.setSize(4);
        add(rootLayout);
        ID_DEPT_CaptionLabel = new nextapp.echo2.app.Label();
        ID_DEPT_CaptionLabel.setText("ID_DEPT");
        rootLayout.add(ID_DEPT_CaptionLabel);
        ID_DEPT_DscField1 = new dsc.echo2app.component.DscField();
        ID_DEPT_DscField1.setId("ID_DEPT_DscField1");
        rootLayout.add(ID_DEPT_DscField1);
        NAME_DEPT_NAME_CaptionLabel = new nextapp.echo2.app.Label();
        NAME_DEPT_NAME_CaptionLabel.setText("NAME_DEPT_NAME");
        rootLayout.add(NAME_DEPT_NAME_CaptionLabel);
        NAME_DEPT_NAME_SelectField1 = new nextapp.echo2.app.SelectField();
        NAME_DEPT_NAME_SelectField1.setId("NAME_DEPT_NAME_SelectField1");
        rootLayout.add(NAME_DEPT_NAME_SelectField1);
        NAME_FACT_CaptionLabel = new nextapp.echo2.app.Label();
        NAME_FACT_CaptionLabel.setText("NAME_FACT");
        rootLayout.add(NAME_FACT_CaptionLabel);
        NAME_FACT_SelectField2 = new nextapp.echo2.app.SelectField();
        NAME_FACT_SelectField2.setId("NAME_FACT_SelectField2");
        rootLayout.add(NAME_FACT_SelectField2);
        NAME_GROUP_CaptionLabel = new nextapp.echo2.app.Label();
        NAME_GROUP_CaptionLabel.setText("NAME_GROUP");
        rootLayout.add(NAME_GROUP_CaptionLabel);
        NAME_GROUP_SelectField3 = new nextapp.echo2.app.SelectField();
        NAME_GROUP_SelectField3.setId("NAME_GROUP_SelectField3");
        rootLayout.add(NAME_GROUP_SelectField3);
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField2 = new dsc.echo2app.component.DscField();
        NOTE_DscField2.setId("NOTE_DscField2");
        rootLayout.add(NOTE_DscField2);
        ID_SPDEPT_CaptionLabel = new nextapp.echo2.app.Label();
        ID_SPDEPT_CaptionLabel.setText("ID_SPDEPT");
        rootLayout.add(ID_SPDEPT_CaptionLabel);
        ID_SPDEPT_SelectField4 = new nextapp.echo2.app.SelectField();
        ID_SPDEPT_SelectField4.setId("ID_SPDEPT_SelectField4");
        rootLayout.add(ID_SPDEPT_SelectField4);
        ID_GROUPNI_CaptionLabel = new nextapp.echo2.app.Label();
        ID_GROUPNI_CaptionLabel.setText("ID_GROUPNI");
        rootLayout.add(ID_GROUPNI_CaptionLabel);
        ID_GROUPNI_SelectField5 = new nextapp.echo2.app.SelectField();
        ID_GROUPNI_SelectField5.setId("ID_GROUPNI_SelectField5");
        rootLayout.add(ID_GROUPNI_SelectField5);
        ID_GROUP_DIRECT_CaptionLabel = new nextapp.echo2.app.Label();
        ID_GROUP_DIRECT_CaptionLabel.setText("N_DEPARTMENT.ID_GROUP_DIRECT");
        rootLayout.add(ID_GROUP_DIRECT_CaptionLabel);
        ID_GROUP_DIRECT_SelectField6 = new nextapp.echo2.app.SelectField();
        ID_GROUP_DIRECT_SelectField6.setId("ID_GROUP_DIRECT_SelectField6");
        rootLayout.add(ID_GROUP_DIRECT_SelectField6);
	}

}
