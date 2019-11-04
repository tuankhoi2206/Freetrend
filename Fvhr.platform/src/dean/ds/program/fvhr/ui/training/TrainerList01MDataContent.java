package ds.program.fvhr.ui.training;

import java.util.List;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import ds.program.fvhr.dao.generic.FvGenericDAO;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_TRAINER_LIST;
import ds.program.fvhr.domain.N_TRAINING_LIST;
import ds.program.fvhr.domain.pk.N_TRAINER_LISTPk;
import fv.util.Vni2Uni;
import fv.util.VniEditor;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscField;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

public class TrainerList01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_HL_CaptionLabel;
    private nextapp.echo2.app.Label ID_MONHOC_CaptionLabel;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField5;
	private SelectField sfMonHoc;
	private DscField EMPSN_HL_DscField1;
	private Label lblInfo;
	/**
	 * Creates a new <code>TrainerList01MDataContent</code>.
	 */
	public TrainerList01MDataContent() {
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
				lblInfo.setText("");
				EMPSN_HL_DscField1.setEnabled(true);
				sfMonHoc.setEnabled(true);
			} else {
				EMPSN_HL_DscField1.setEnabled(false);
				sfMonHoc.setEnabled(false);
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
		return N_TRAINER_LIST.class;
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
		//N_TRAINER_LIST data = (N_TRAINER_LIST) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		N_TRAINER_LIST data = (N_TRAINER_LIST) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			IGenericDAO<N_TRAINER_LIST, N_TRAINER_LISTPk> dao = Application.getApp().getDao(N_TRAINER_LIST.class);
			if (getProgram().getDataMode()==IProgram.DATAMODE_NEW){
				N_TRAINER_LISTPk pk = new N_TRAINER_LISTPk(data.getEMPSN_HL(),data.getID_MONHOC());
				if (dao.findById(pk)!=null){
					setErrorMessage("Đã có cán bộ huấn luyện với môn học đã chọn");
					return false;
				}
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
        getUIDataObjectBinder().registerCustomEditor(N_TRAINER_LIST.class, "NOTE", new VniEditor());
        getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "FNAME", new VniEditor());
        getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "LNAME", new VniEditor());
        getUIDataObjectBinder().registerCustomEditor(N_TRAINING_LIST.class, "TEN_MON_HOC", new VniEditor());
        getUIDataObjectBinder().registerCustomEditor(N_TRAINER_LIST.class, "ID_MONHOC", subjectEditor());
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("EMPSN_HL", EMPSN_HL_DscField1, EMPSN_HL_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_MONHOC", sfMonHoc, ID_MONHOC_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField5, NOTE_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	
	private MappingPropertyEditor subjectEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_TRAINING_LIST, String> dao = Application.getApp().getDao(N_TRAINING_LIST.class);
		List<N_TRAINING_LIST> list = dao.findAll(10000);
		for (N_TRAINING_LIST data:list){
			e.put(Vni2Uni.convertToUnicode(data.getTEN_MON_HOC()), data.getMA_MON_HOC());
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
		//N_TRAINER_LIST data = (N_TRAINER_LIST) getDataObject();
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
		//N_TRAINER_LIST data = (N_TRAINER_LIST) getDataObject();
		//使用者可在以下區域做儲存資料物件至DataObjectSet之前處理
		//<儲存前處理>
	}
	
	public void setInfo(String info){
		lblInfo.setText(info);
	}


	private void fetchInfo(ActionEvent e) {
		String empsn=EMPSN_HL_DscField1.getText();
		if (!empsn.matches("[0-9]{8}")){
			return;
		}
		String info = FvGenericDAO.getInstanse().getEmployeeInfo(empsn);
		lblInfo.setText(info);
	}


	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		rootLayout = new Grid();
		rootLayout.setSize(2);
		add(rootLayout);
		EMPSN_HL_CaptionLabel = new Label();
		EMPSN_HL_CaptionLabel.setText("N_TRAINER_LIST.EMPSN_HL");
		rootLayout.add(EMPSN_HL_CaptionLabel);
		Row row1 = new Row();
		rootLayout.add(row1);
		EMPSN_HL_DscField1 = new DscField();
		EMPSN_HL_DscField1.setId("EMPSN_HL_DscField1");
		EMPSN_HL_DscField1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fetchInfo(e);
			}
		});
		row1.add(EMPSN_HL_DscField1);
		lblInfo = new Label();
		row1.add(lblInfo);
		ID_MONHOC_CaptionLabel = new Label();
		ID_MONHOC_CaptionLabel.setText("N_TRAINER_LIST.ID_MONHOC");
		rootLayout.add(ID_MONHOC_CaptionLabel);
		sfMonHoc = new SelectField();
		rootLayout.add(sfMonHoc);
		NOTE_CaptionLabel = new Label();
		NOTE_CaptionLabel.setText("N_TRAINER_LIST.NOTE");
		rootLayout.add(NOTE_CaptionLabel);
		NOTE_DscField5 = new DscField();
		NOTE_DscField5.setId("NOTE_DscField5");
		NOTE_DscField5.setWidth(new Extent(250, Extent.PX));
		rootLayout.add(NOTE_DscField5);
	}

}
