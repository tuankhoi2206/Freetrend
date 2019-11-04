package ds.program.fvhr.ui.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.TextArea;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

import org.apache.commons.lang.StringUtils;

import ds.program.fvhr.domain.N_N_EMP_JOB;
import ds.program.fvhr.domain.N_N_JOB;
import ds.program.fvhr.domain.pk.N_N_EMP_JOBPk;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import fv.util.BundleUtils;
import fv.util.Vni2Uni;

public class EmpJob00MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label ID_JOB_CaptionLabel;
//    private dsc.echo2app.component.DscField ID_JOB_DscField2;
    private SelectField sfJob;
    private nextapp.echo2.app.Label DATE_B_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_B_DscDateField1;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private TextArea NOTE_DscField3;
	private Label UP_USER_CaptionLabel;
	private DscField UP_USER_DscField;
	private Label UP_DATE_CaptionLabel;
	private DscDateField UP_DATE_DscDateField;

	/**
	 * Creates a new <code>EmpJob00MDataContent</code>.
	 */
	public EmpJob00MDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
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
				EMPSN_DscField1.setEnabled(true);
				sfJob.setEnabled(true);
			} else {
				//修改時，設定哪些元件Enable/Disable
				EMPSN_DscField1.setEnabled(false);
				sfJob.setEnabled(false);
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
	@Override
	public Class getDataObjectClass() {
		return N_N_EMP_JOB.class;
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
		//N_N_EMP_JOB data = (N_N_EMP_JOB) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		N_N_EMP_JOB data = (N_N_EMP_JOB) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			IGenericDAO<N_N_EMP_JOB, N_N_EMP_JOBPk> dao = Application.getApp().getDao(N_N_EMP_JOB.class);
			if (getProgram().getDataMode()==IProgram.DATAMODE_NEW){
				N_N_EMP_JOBPk pk = new N_N_EMP_JOBPk(data.getEMPSN(), data.getID_JOB());
				if (dao.findById(pk)!=null){
					setErrorMessage("Số thẻ tương ứng với công việc được chọn đã có sẵn.");
					return false;
				}
			}
			List list = dao.find(10, "from N_N_EMP_JOB t where t.EMPSN=? and t.DATE_B=?", new Object[]{data.getEMPSN(), data.getDATE_B()});
			if (list.size()>0){
				setErrorMessage("Đã có công việc khác bắt đầu từ ngày được chọn rồi");
				return false;
			}
			
		}
		return ret;
	}

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
        getUIDataObjectBinder().registerCustomEditor(N_N_EMP_JOB.class, "ID_JOB", jobEditor());
        getUIDataObjectBinder().registerCustomEditor(N_N_EMP_JOB.class, "DATE_B", PropertyEditors.createDateEditor(BundleUtils.getString("LANG_DATEFORMAT")));
        getUIDataObjectBinder().registerCustomEditor(N_N_EMP_JOB.class, "UP_DATE", PropertyEditors.createDateEditor(BundleUtils.getString("LANG_DATEFORMAT")));
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_JOB", sfJob, ID_JOB_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_B", DATE_B_DscDateField1, DATE_B_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField3, NOTE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("UP_USER", UP_USER_DscField, UP_USER_CaptionLabel);
        getUIDataObjectBinder().addBindMap("UP_DATE", UP_DATE_DscDateField, UP_DATE_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	
	private MappingPropertyEditor jobEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_N_JOB, String> dao = Application.getApp().getDao(N_N_JOB.class);
		List<N_N_JOB> list = dao.findAll(10000);
		for (N_N_JOB job:list){
			e.put(Vni2Uni.convertToUnicode(job.getNAME()) + " - " + job.getKIND(), job.getID_JOB());
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
		N_N_EMP_JOB data = (N_N_EMP_JOB) getDataObject();
		data.setDATE_B(new Date());
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {
		N_N_EMP_JOB data = (N_N_EMP_JOB) getDataObject();
		data.setNOTE(Vni2Uni.convertToVNI(data.getNOTE()));
		data.setUP_USER(getProgram().getLoginInfo().getUserID());
		data.setUP_DATE(new Date());
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
        rootLayout = new Grid();
//        rootLayout.setWidth(new Extent(100, Extent.PERCENT));
        rootLayout.setSize(2);
        add(rootLayout);
        EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_CaptionLabel.setText("N_N_EMP_JOB.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField1 = new dsc.echo2app.component.DscField();
        EMPSN_DscField1.setId("EMPSN_DscField1");
        EMPSN_DscField1.setMaximumLength(8);
        EMPSN_DscField1.addActionListener(new ActionListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
//				NOTE_DscField3.setText(genNote());
				if (NOTE_DscField3.getText().contains("]"))
					NOTE_DscField3.setText(genNote() + StringUtils.substringAfter(NOTE_DscField3.getText(), "]"));
				else
					NOTE_DscField3.setText(genNote() + NOTE_DscField3.getText());
			}
		});
        rootLayout.add(EMPSN_DscField1);
        ID_JOB_CaptionLabel = new nextapp.echo2.app.Label();
        ID_JOB_CaptionLabel.setText("N_N_EMP_JOB.ID_JOB");
        rootLayout.add(ID_JOB_CaptionLabel);
//        ID_JOB_DscField2 = new dsc.echo2app.component.DscField();
//        ID_JOB_DscField2.setId("ID_JOB_DscField2");
//        rootLayout.add(ID_JOB_DscField2);
        sfJob = new SelectField();
        sfJob.setWidth(new Extent(100));
        rootLayout.add(sfJob);
        DATE_B_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_B_CaptionLabel.setText("N_N_EMP_JOB.DATE_B");
        rootLayout.add(DATE_B_CaptionLabel);
        DATE_B_DscDateField1 = new dsc.echo2app.component.DscDateField();
        DATE_B_DscDateField1.setId("DATE_B_DscDateField1");
        DATE_B_DscDateField1.getDateChooser().setLocale(new Locale("en"));
        DATE_B_DscDateField1.setDateFormat(new SimpleDateFormat(BundleUtils.getString("LANG_DATEFORMAT")));
        rootLayout.add(DATE_B_DscDateField1);
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_N_EMP_JOB.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField3 = new TextArea();
        NOTE_DscField3.setId("NOTE_DscField3");
        rootLayout.add(NOTE_DscField3);
        UP_USER_CaptionLabel = new nextapp.echo2.app.Label();
        rootLayout.add(UP_USER_CaptionLabel);
        UP_USER_DscField = new DscField();
        UP_USER_DscField.setEnabled(false);
        rootLayout.add(UP_USER_DscField);
        UP_DATE_CaptionLabel = new nextapp.echo2.app.Label();
        rootLayout.add(UP_DATE_CaptionLabel);
        UP_DATE_DscDateField = new DscDateField();
        UP_DATE_DscDateField.setDateFormat(new SimpleDateFormat(BundleUtils.getString("LANG_DATEFORMAT")));
        UP_DATE_DscDateField.setEnabled(false);
        rootLayout.add(UP_DATE_DscDateField);
	}
	
	public String genNote(){
		if (!EMPSN_DscField1.getText().trim().matches("[0-9]{8}")){
			return "";
		}
		IGenericDAO dao = Application.getApp().getDao(getProgram().getLoginInfo().getCompanyID());
		List list = dao.find(1, "select a.FNAME, a.LNAME, b.NAME_FACT, b.NAME_DEPT_NAME from N_EMPLOYEE a, N_DEPARTMENT b where a.DEPSN=b.ID_DEPT and a.EMPSN=?", new Object[]{EMPSN_DscField1.getText().trim()});
		if (list.size()>0){
			Object[] data = (Object[]) list.get(0);
			return "["+Vni2Uni.convertToUnicode(data[0]+ " " + data[1] + " - " + data[2] + "/" + data[3])+"]";
		}
		return "";
	}
}
