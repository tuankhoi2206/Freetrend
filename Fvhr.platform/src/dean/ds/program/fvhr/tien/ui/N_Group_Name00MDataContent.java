package ds.program.fvhr.tien.ui;

import java.util.List;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import ds.program.fvhr.domain.N_GROUP_DEPT;
import ds.program.fvhr.domain.N_SP_WDAY;
import ds.program.fvhr.domain.N_USERS_LIST;
import fv.util.VniEditor;

public class N_Group_Name00MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label NAME_GROUP_CaptionLabel;
    private dsc.echo2app.component.DscField NAME_GROUP_DscField2;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField3;
    String strUser_login;
	/**
	 * Creates a new <code>N_Group_Name00MDataContent</code>.
	 */
	public N_Group_Name00MDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
		strUser_login = Application.getApp().getLoginInfo().getUserID();
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
			}
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_EDIT) {
				//新增時，設定哪些元件Enable/Disable
				NAME_GROUP_DscField2.setEnabled(false);
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
		return N_GROUP_DEPT.class;
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
		//N_GROUP_DEPT data = (N_GROUP_DEPT) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		boolean ret=true;
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW)
		{
			N_GROUP_DEPT data = (N_GROUP_DEPT) getDataObject();
			IGenericDAO<N_GROUP_DEPT,String> objN_GROUP_DEPT=Application.getApp().getDao(N_GROUP_DEPT.class);
			List<N_GROUP_DEPT> listN_GROUP_DEPT=objN_GROUP_DEPT.find(1," from N_GROUP_DEPT WHERE ID_GROUP IN(SELECT MAX(ID_GROUP) FROM N_GROUP_DEPT)");
			if(listN_GROUP_DEPT.size()>0)
			{
				N_GROUP_DEPT objN_GROUP_DEPT_=listN_GROUP_DEPT.get(0);
				long  GROUP_DEPT=objN_GROUP_DEPT_.getID_GROUP();
				data.setID_GROUP(GROUP_DEPT+1);
			}
			
			 ret = super.checkDataObject();
			if (ret) {
				//使用者可在以下區域撰寫內容合理值的判斷程式碼
				//此單頭為readonly 直接return true 
				// KIEM TRA QUYEN THAO TAC
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
				// KIEM TRA DU TON TAI
				
				List<N_GROUP_DEPT> listGROUP_DEPT_=objN_GROUP_DEPT.find(1,"  from N_GROUP_DEPT where NAME_GROUP=? ",data.getNAME_GROUP());
				
				if(listGROUP_DEPT_.size()>0)
				{
					N_GROUP_DEPT objGROUP_DEPT=listGROUP_DEPT_.get(0);
					if(objGROUP_DEPT!=null)
					{
						Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Tên Xưởng đã tồn tại");
						return false;	
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
        getUIDataObjectBinder().registerCustomEditor(N_GROUP_DEPT.class, "NAME_GROUP",new VniEditor());
        getUIDataObjectBinder().registerCustomEditor(N_GROUP_DEPT.class, "NOTE",new VniEditor());
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("NAME_GROUP", NAME_GROUP_DscField2, NAME_GROUP_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField3, NOTE_CaptionLabel);
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
		//N_GROUP_DEPT data = (N_GROUP_DEPT) getDataObject();
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
		//N_GROUP_DEPT data = (N_GROUP_DEPT) getDataObject();
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
        NAME_GROUP_CaptionLabel = new nextapp.echo2.app.Label();
        NAME_GROUP_CaptionLabel.setText("N_GROUP_DEPT.NAME_GROUP");
        rootLayout.add(NAME_GROUP_CaptionLabel);
        NAME_GROUP_DscField2 = new dsc.echo2app.component.DscField();
        NAME_GROUP_DscField2.setId("NAME_GROUP_DscField2");
        rootLayout.add(NAME_GROUP_DscField2);
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_GROUP_DEPT.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField3 = new dsc.echo2app.component.DscField();
        NOTE_DscField3.setId("NOTE_DscField3");
        rootLayout.add(NOTE_DscField3);
	}

}
