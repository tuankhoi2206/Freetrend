package ds.program.fvhr.tien.ui;

import java.util.List;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import ds.program.fvhr.domain.N_DEPT_NAME;
import ds.program.fvhr.domain.N_FACTORY;
import ds.program.fvhr.domain.N_GROUP_DEPT;
import ds.program.fvhr.domain.N_USERS_LIST;
import fv.util.VniEditor;

public class N_DEPT_NAME00MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label DEPT_NAME_CaptionLabel;
    private dsc.echo2app.component.DscField DEPT_NAME_DscField1;
    private String strUser_login="";

	/**
	 * Creates a new <code>N_DEPT_NAME00MDataContent</code>.
	 */
	public N_DEPT_NAME00MDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
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
			}
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_EDIT) {
				//新增時，設定哪些元件Enable/Disable
				DEPT_NAME_DscField1.setEnabled(false);
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
		return N_DEPT_NAME.class;
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
		//N_DEPT_NAME data = (N_DEPT_NAME) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		boolean ret=true;
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
			N_DEPT_NAME data = (N_DEPT_NAME) getDataObject();
			IGenericDAO<N_DEPT_NAME,String> objdaodeptname=Application.getApp().getDao(N_DEPT_NAME.class);
			List<N_DEPT_NAME> listdeptname=objdaodeptname.find(1," from N_DEPT_NAME WHERE ID_DEPT_NAME IN(SELECT MAX(ID_DEPT_NAME) FROM N_DEPT_NAME)");
			if(listdeptname.size()>0)
			{
				N_DEPT_NAME objdeptname_=listdeptname.get(0);
				long  intid_dept=objdeptname_.getID_DEPT_NAME();
				data.setID_DEPT_NAME(intid_dept+1);
			}		
			 ret = super.checkDataObject();
			if (ret) {
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
				
				List<N_DEPT_NAME> listdeptname_=objdaodeptname.find(1,"  from N_DEPT_NAME where DEPT_NAME=? ",data.getDEPT_NAME());
				if(listdeptname_.size()>0)
				{
					N_DEPT_NAME objdeptname=listdeptname_.get(0);
					if(objdeptname!=null)
					{
						Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Tên Đơn vị đã tồn tại");
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
	/*@Override
	protected boolean autoPrimaryKeyValue() {
		//使用者可以在以下區域撰寫產生新序號的程式碼並將新序號放入dataObject
		//N_DEPT_NAME data = (N_DEPT_NAME) getDataObject();
		//Connection conn = Application.getApp().getConnection();
		//Map keys = new HashMap();
		//Map filters = new HashMap();
		//String newValue = KeyNumberGenerator.generateNumber(conn,
		//		"$Y{2}$M{2}$D{2}$S{4}", 
		//		1,
		//		"N_DEPT_NAME", "{ID_FIELD}", 
		//		keys, filters, 
		//		true, false);
		
		//產生新序號並放入dataObject
		//data.set{ID_FIELD}(newValue);
		return true;
	}*/

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
        getUIDataObjectBinder().registerCustomEditor(N_DEPT_NAME.class, "DEPT_NAME",new VniEditor());
       
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("DEPT_NAME", DEPT_NAME_DscField1, DEPT_NAME_CaptionLabel);
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
		//N_DEPT_NAME data = (N_DEPT_NAME) getDataObject();
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
		//N_DEPT_NAME data = (N_DEPT_NAME) getDataObject();
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
        DEPT_NAME_CaptionLabel = new nextapp.echo2.app.Label();
        DEPT_NAME_CaptionLabel.setText("N_DEPT_NAME.DEPT_NAME");
        rootLayout.add(DEPT_NAME_CaptionLabel);
        DEPT_NAME_DscField1 = new dsc.echo2app.component.DscField();
        DEPT_NAME_DscField1.setId("DEPT_NAME_DscField1");
        rootLayout.add(DEPT_NAME_DscField1);
	}

}
