package ds.program.fvhr.tien.ui;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.PropertyEditors;
import ds.program.fvhr.domain.DSHR71;
import ds.program.fvhr.domain.N_CHANGE_HOSPITAL;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.pk.DSHR71Pk;
import ds.program.fvhr.domain.pk.N_CHANGE_HOSPITALPk;
import fv.util.ApplicationHelper;
import fv.util.HRUtils;

public class DSHR7100MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label PNL_CaptionLabel;
    private dsc.echo2app.component.DscField PNL_DscField1;
    private nextapp.echo2.app.Label VDATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField VDATE_DscDateField1;
    private nextapp.echo2.app.Label JJ_HOUR_CaptionLabel;
    private dsc.echo2app.component.DscField JJ_HOUR_DscField2;
    private nextapp.echo2.app.Label JJ_JADD_CaptionLabel;
    private dsc.echo2app.component.DscField JJ_JADD_DscField3;
    private nextapp.echo2.app.Label JJ_WADD_CaptionLabel;
    private dsc.echo2app.component.DscField JJ_WADD_DscField4;
    Date dtNow= new Date();
	String useLogin;
	private SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
	Connection con 	= Application.getApp().getConnection();
	CallableStatement stm_call;
	Calendar cal=Calendar.getInstance();
	/**
	 * Creates a new <code>DSHR7100MDataContent</code>.
	 */
	public DSHR7100MDataContent() {
		super();

		// Add design-time configured components.
		
		initComponents();
	 intMore();
	}
	private void intMore()
	{ 
		VDATE_DscDateField1.getTextField().setText("");
		VDATE_DscDateField1.setDateFormat(sdf);
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
		} 
		
		else {
			rootLayout.setEnabled(true);
            //----------------------------------------------------------------------------------
			//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            //----------------------------------------------------------------------------------
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				//新增時，設定哪些元件Enable/Disable
				this.PNL_DscField1.setEnabled(true);
				this.VDATE_DscDateField1.setEnabled(true);

			} 
			if((this.getProgram().getDataMode() == IProgram.DATAMODE_EDIT))
			{
				this.PNL_DscField1.setEnabled(false);
				this.VDATE_DscDateField1.setEnabled(false);

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
		return DSHR71.class;
	}
	@Override
	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值
		//DSHR71 data = (DSHR71) getDataObject();
	}	
	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		DSHR71 data = (DSHR71) getDataObject();		
		Date dtChange=data.getVDATE();
		cal.setTimeInMillis(dtChange.getTime());		
		Calendar dateM=Calendar.getInstance();
		dateM.setTimeInMillis(cal.getTimeInMillis());
		dateM.set(Calendar.DAY_OF_MONTH, 1);		
		Date dateM_=dateM.getTime();	
		data.setVDATE(dateM_);
		boolean ret = super.checkDataObject();
		HRUtils util = ApplicationHelper.getHRUtils();
		if (ret) {
			// kiem tra quyen thao tac
			if (!util.getPermissionValidator().hasEmpsnPermission(data.getPNL())){
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Anh/chị không có quyền thao tác số thẻ này");
				return false;
			}		
			if((this.getProgram().getDataMode() != IProgram.DATAMODE_EDIT))
			{	
				// kiem tra su ton tai
				IGenericDAO<DSHR71, DSHR71Pk> daoDSHR71=Application.getApp().getDao(DSHR71.class);
				DSHR71Pk objpk=new DSHR71Pk(data.getVDATE(),data.getPNL());
				DSHR71 objDSHR71=daoDSHR71.findById(objpk);
				if(objDSHR71!=null)
				{
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Thông tin số thẻ này đã cập nhập");
					return false;
				}
				
			}
			// kiem tra truc tiep
			IGenericDAO<N_EMPLOYEE, String> emp_dao=Application.getApp().getDao(N_EMPLOYEE.class);
			List<N_EMPLOYEE> listEmp=emp_dao.find(1, "from N_EMPLOYEE where EMPSN=?", data.getPNL());
			if(listEmp.size()>0)
			{
				N_EMPLOYEE emp=listEmp.get(0);
				if(!emp.getTRUCTIEP_SX().equals("TT"))
				{
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Số thẻ này không trực tiếp sản xuất");
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
	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor
        dsc.echo2app.propertyeditors.MappingPropertyEditor editor = null;
        this.getUIDataObjectBinder().registerCustomEditor(DSHR71.class,"VDATE",PropertyEditors.createDateEditor("dd/MM/yyyy"));
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("PNL", PNL_DscField1, PNL_CaptionLabel);
        getUIDataObjectBinder().addBindMap("VDATE", VDATE_DscDateField1, VDATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("JJ_HOUR", JJ_HOUR_DscField2, JJ_HOUR_CaptionLabel);
        getUIDataObjectBinder().addBindMap("JJ_JADD", JJ_JADD_DscField3, JJ_JADD_CaptionLabel);
        getUIDataObjectBinder().addBindMap("JJ_WADD", JJ_WADD_DscField4, JJ_WADD_CaptionLabel);
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
		
	}
	@Override
	public void beforeSaveToDataObjectSet() {
		DSHR71 data = (DSHR71) getDataObject();
	    String 	IS_SOLE="";
	    N_DEPARTMENT objDepartment=null;
		IGenericDAO<N_DEPARTMENT, String> objdaoDepartment=Application.getApp().getDao(N_DEPARTMENT.class);
		List<N_DEPARTMENT> listdept=null;
		useLogin=Application.getApp().getLoginInfo().getUserID().toString();		
		data.setUP_DATE(dtNow);
		data.setUP_USER(useLogin);		
		// ma don vi, fact...
		
		Calendar dateMax = Calendar.getInstance();
		dateMax.setTimeInMillis(cal.getTimeInMillis());
		dateMax.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date dateMax_=dateMax.getTime();
		String strDateMax=sdf.format(dateMax_);	
		String strSql="{?=call lay_donvi_theo_ngay(?,?)}";
		String strID_Dept="";
		
		try {
			stm_call = con.prepareCall(strSql);
			stm_call.registerOutParameter(1, Types.VARCHAR);
			stm_call.setString(2, data.getPNL());
			stm_call.setString(3,strDateMax);
			stm_call.execute();
			strID_Dept=	stm_call.getString(1);
			data.setSEC(strID_Dept);
			listdept=objdaoDepartment.find(1, "from N_DEPARTMENT where ID_DEPT=?", strID_Dept);
			if(listdept.size()>0)
			{
				 objDepartment=listdept.get(0);
				data.setFACTNAME(objDepartment.getNAME_FACT());
				
			}
			if(objDepartment!=null)
			{
				if(objDepartment.getNAME_GROUP()!=null)
				{
					if(objDepartment.getNAME_GROUP().equals("FV3")&&objDepartment.getNAME_FACT().equals("FVLS"))
					{
						IS_SOLE="2";
					}
					if(objDepartment.getNAME_GROUP().equals("PU")&&objDepartment.getNAME_FACT().equals("FVLS"))
					{
						IS_SOLE="3";
					}
				}	
				
				if((objDepartment.getNAME_GROUP()==null||!objDepartment.getNAME_GROUP().equals("FV3")||!objDepartment.getNAME_GROUP().equals("PU"))&&objDepartment.getNAME_FACT().equals("FVLS"))
				{
					IS_SOLE="1";
				}
				else
					IS_SOLE="0";
			}
			data.setIS_SOLE(IS_SOLE);
					
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		
		
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
        PNL_CaptionLabel = new nextapp.echo2.app.Label();
        PNL_CaptionLabel.setText("DSHR71.PNL");
        rootLayout.add(PNL_CaptionLabel);
        PNL_DscField1 = new dsc.echo2app.component.DscField();
        PNL_DscField1.setId("PNL_DscField1");
        rootLayout.add(PNL_DscField1);
        VDATE_CaptionLabel = new nextapp.echo2.app.Label();
        VDATE_CaptionLabel.setText("DSHR71.VDATE");
        rootLayout.add(VDATE_CaptionLabel);
        VDATE_DscDateField1 = new dsc.echo2app.component.DscDateField();
        VDATE_DscDateField1.setId("VDATE_DscDateField1");       
        /*VDATE_DscDateField1.getDateChooser().setLocale(Locale.ENGLISH);
        VDATE_DscDateField1.setDateFormat(sdf);*/
        rootLayout.add(VDATE_DscDateField1);
        JJ_HOUR_CaptionLabel = new nextapp.echo2.app.Label();
        JJ_HOUR_CaptionLabel.setText("DSHR71.J_HOUR");
        rootLayout.add(JJ_HOUR_CaptionLabel);
        JJ_HOUR_DscField2 = new dsc.echo2app.component.DscField();
        JJ_HOUR_DscField2.setId("JJ_HOUR_DscField2");
        rootLayout.add(JJ_HOUR_DscField2);
        JJ_JADD_CaptionLabel = new nextapp.echo2.app.Label();
        JJ_JADD_CaptionLabel.setText("DSHR71.J_JADD");
        rootLayout.add(JJ_JADD_CaptionLabel);
        JJ_JADD_DscField3 = new dsc.echo2app.component.DscField();
        JJ_JADD_DscField3.setId("JJ_JADD_DscField3");
        rootLayout.add(JJ_JADD_DscField3);
        JJ_WADD_CaptionLabel = new nextapp.echo2.app.Label();
        JJ_WADD_CaptionLabel.setText("DSHR71.J_WADD");
        rootLayout.add(JJ_WADD_CaptionLabel);
        JJ_WADD_DscField4 = new dsc.echo2app.component.DscField();
        JJ_WADD_DscField4.setId("JJ_WADD_DscField4");
        rootLayout.add(JJ_WADD_DscField4);
	}

}
