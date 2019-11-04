package ds.program.fvhr.tien.ui;

import java.util.List;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import ds.program.fvhr.domain.N_DATA_MAIN;
import ds.program.fvhr.domain.N_EMP_ICCARD;
import ds.program.fvhr.domain.N_REGISTER_OVERTIME;

public class N_GET_DATAMAIN00MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label DATES_CaptionLabel;
    private dsc.echo2app.component.DscField DATES_DscField1;
    private nextapp.echo2.app.Label EMPCN_CaptionLabel;
    private nextapp.echo2.app.Label EMPCN_Label1;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private nextapp.echo2.app.Label EMPSN_Label2;
    private nextapp.echo2.app.Label TIME_IN_CaptionLabel;
    private dsc.echo2app.component.DscField TIME_IN_DscField5;
    private nextapp.echo2.app.Label TIME_OUT_CaptionLabel;
    private dsc.echo2app.component.DscField TIME_OUT_DscField6;
    private int intTimein=0;
    private int intTimeout=0;
    public String strTimeint="";
    public String strTimeout="";
	private String strEMPCN;
	private String strEMPSN;
	private N_GET_DATAMAIN00MProgram program=null;

	/**
	 * Creates a new <code>N_GET_DATAMAIN00MDataContent</code>.
	 */
	public N_GET_DATAMAIN00MDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
		doMoreInt();
		program=new N_GET_DATAMAIN00MProgram();
	}
	private void doMoreInt()
	{
		TIME_OUT_CaptionLabel.setText("Giờ Ra:");
		TIME_IN_CaptionLabel.setText("Giờ Vào:");
		rootLayout.remove(EMPSN_CaptionLabel);
		rootLayout.remove(EMPSN_Label2);
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
				N_DATA_MAIN data = (N_DATA_MAIN) getDataObject();
				program.dogetMinMaxtime(data.getTIMES());
		      	TIME_IN_DscField5.setEnabled(true);
		      	TIME_OUT_DscField6.setEnabled(true);
		      	DATES_DscField1.setEnabled(false);		      	
		      	/*strTimeint=String.valueOf( intTimein);
		      	strTimeout=String.valueOf( intTimeout);
		      	if(strTimeint.length()<4)
		      	{
		      		strTimeint="0"+strTimeint;
		      	}
		      	if(strTimeout.length()<4)
		      	{
		      		strTimeout="0"+strTimeout;
		      	}*/
		      		      		
		      	TIME_OUT_DscField6.setText(program.getStrTimeout());
		     	TIME_IN_DscField5.setText(program.getStrTimeint());
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
		return N_DATA_MAIN.class;
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
		//N_DATA_MAIN data = (N_DATA_MAIN) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		N_DATA_MAIN data = (N_DATA_MAIN) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			//使用者可在以下區域撰寫內容合理值的判斷程式碼
			//此單頭為readonly 直接return true 
		
			IGenericDAO<N_REGISTER_OVERTIME, String> objdao=Application.getApp().getDao(N_REGISTER_OVERTIME.class);
			program.doGetempsn(data.getEMPCN());
			strEMPSN=program.getStrEMPSN();
			if(!strEMPSN.equals(""))
			{
				List<N_REGISTER_OVERTIME> listN_Register=objdao.find(1,"from N_REGISTER_OVERTIME where EMPSN=? AND DATE_OVER=to_date('"+data.getDATES()+"','dd/mm/yyyy')", strEMPSN);
				if(listN_Register.size()>0)
				{
					/*N_REGISTER_OVERTIME objN_register=listN_Register.get(0);
					if(objN_register!=null)
					{
						setErrorMessage("Số thẻ "+ strEMPSN +"đã đăng ký tăng ca");
						return false;
					}*/
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
		//N_DATA_MAIN data = (N_DATA_MAIN) getDataObject();
		//Connection conn = Application.getApp().getConnection();
		//Map keys = new HashMap();
		//Map filters = new HashMap();
		//String newValue = KeyNumberGenerator.generateNumber(conn,
		//		"$Y{2}$M{2}$D{2}$S{4}", 
		//		1,
		//		"N_DATA_MAIN", "{ID_FIELD}", 
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
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("DATES", DATES_DscField1, DATES_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPCN", EMPCN_Label1, EMPCN_CaptionLabel);
       // getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_Label2, EMPSN_CaptionLabel);
       // getUIDataObjectBinder().addBindMap("TIME_IN", TIME_IN_DscField5, TIME_IN_CaptionLabel);
        //getUIDataObjectBinder().addBindMap("TIME_OUT", TIME_OUT_DscField6, TIME_OUT_CaptionLabel);
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
		//N_DATA_MAIN data = (N_DATA_MAIN) getDataObject();
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
		
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
		    autoPrimaryKeyValue();
		}
		N_DATA_MAIN data = (N_DATA_MAIN) getDataObject();
		data.setTIMES(TIME_OUT_DscField6.getText()+TIME_IN_DscField5.getText());
		
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
        DATES_CaptionLabel = new nextapp.echo2.app.Label();
        DATES_CaptionLabel.setText("DATES		");
        rootLayout.add(DATES_CaptionLabel);
        DATES_DscField1 = new dsc.echo2app.component.DscField();
        DATES_DscField1.setId("DATES_DscField1");
        rootLayout.add(DATES_DscField1);
        EMPCN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPCN_CaptionLabel.setText("EMPCN			");
        rootLayout.add(EMPCN_CaptionLabel);
        EMPCN_Label1 = new nextapp.echo2.app.Label();
        EMPCN_Label1.setId("EMPCN_Label1");
        rootLayout.add(EMPCN_Label1);
        EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_CaptionLabel.setText("EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_Label2 = new nextapp.echo2.app.Label();
        EMPSN_Label2.setId("EMPSN_Label2");
        rootLayout.add(EMPSN_Label2);
        TIME_IN_CaptionLabel = new nextapp.echo2.app.Label();
        TIME_IN_CaptionLabel.setText("TIME_IN");
        rootLayout.add(TIME_IN_CaptionLabel);
        TIME_IN_DscField5 = new dsc.echo2app.component.DscField();
        TIME_IN_DscField5.setId("TIME_IN_DscField5");
        rootLayout.add(TIME_IN_DscField5);
        TIME_OUT_CaptionLabel = new nextapp.echo2.app.Label();
        TIME_OUT_CaptionLabel.setText("");
        rootLayout.add(TIME_OUT_CaptionLabel);
        TIME_OUT_DscField6 = new dsc.echo2app.component.DscField();
        TIME_OUT_DscField6.setId("TIME_OUT_DscField6");
        rootLayout.add(TIME_OUT_DscField6);
	}

}
