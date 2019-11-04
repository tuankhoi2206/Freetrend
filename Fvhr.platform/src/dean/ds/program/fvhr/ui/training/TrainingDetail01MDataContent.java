package ds.program.fvhr.ui.training;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import ds.program.fvhr.dao.generic.FvGenericDAO;
import ds.program.fvhr.domain.N_KHOA_HOC;
import ds.program.fvhr.domain.N_TRAINER_LIST;
import ds.program.fvhr.domain.N_TRAINING_DETAILS;
import ds.program.fvhr.domain.N_TRAINING_LIST;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscDateField;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.propertyeditors.CustomNumberEditor;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.SelectField;
import echopointng.ComboBox;
import fv.util.ListBinder;
import fv.util.Vni2Uni;
import fv.util.VniEditor;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

public class TrainingDetail01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label ID_TRAINING_CaptionLabel;
    private dsc.echo2app.component.DscField ID_TRAINING_DscField1;
    private nextapp.echo2.app.Label COURSE_ID_CaptionLabel;
    private nextapp.echo2.app.Label SUBJECT_ID_CaptionLabel;
    private nextapp.echo2.app.Label BDATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField BDATE_DscDateField1;
    private nextapp.echo2.app.Label EDATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField EDATE_DscDateField2;
    private nextapp.echo2.app.Label GUIDE_EMPSN_CaptionLabel;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField6;
    private nextapp.echo2.app.Label EXAM_DATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField EXAM_DATE_DscDateField3;
    private nextapp.echo2.app.Label EXAM_TIME_CaptionLabel;
    private dsc.echo2app.component.DscField EXAM_TIME_DscField7;
    private nextapp.echo2.app.Label MARK_CaptionLabel;
    private dsc.echo2app.component.DscField MARK_DscField8;
    private nextapp.echo2.app.Label RANK_CaptionLabel;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField10;
	private ResourceBundle resourceBundle;
	private Label TRAINING_TYPE_CaptionLabel;
	private SelectField sfType;
	private ComboBox cboCourse;
	private ComboBox cboSubject;
	private ComboBox cboRank;
	private SelectField sfGuide;
	private MappingPropertyEditor courseEditor;
	private MappingPropertyEditor subjectEditor;
	private MappingPropertyEditor typeEditor;
	private MappingPropertyEditor guideEditor;
	/**
	 * Creates a new <code>TrainingDetail01MDataContent</code>.
	 */
	public TrainingDetail01MDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
		BDATE_DscDateField1.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		BDATE_DscDateField1.getDateChooser().setLocale(new Locale("en"));
		EDATE_DscDateField2.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		EDATE_DscDateField2.getDateChooser().setLocale(new Locale("en"));
		EXAM_DATE_DscDateField3.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		EXAM_DATE_DscDateField3.getDateChooser().setLocale(new Locale("en"));
		courseEditor = initCourseEditor();
		ListBinder.bindComboBox(cboCourse, courseEditor);
		cboCourse.setPopUpAlwaysOnTop(true);
		cboRank.setPopUpAlwaysOnTop(true);
		cboSubject.setPopUpAlwaysOnTop(true);
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
			} else {
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
	@Override
	public Class getDataObjectClass() {
		return N_TRAINING_DETAILS.class;
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
		//N_TRAINING_DETAILS data = (N_TRAINING_DETAILS) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		N_TRAINING_DETAILS data = (N_TRAINING_DETAILS) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			boolean emp = FvGenericDAO.getInstanse().isWorking(data.getEMPSN(), true);
			if (!emp){
				setErrorMessage("Số thẻ không có trong hệ thống");
				return false;
			}
			//validate course , subject
			if (!validateCourse()){
				return false;
			}
			if (!validateSubject()){
				return false;
			}
			//validate date
			if (data.getEDATE().compareTo(data.getBDATE())<0){
				setErrorMessage("Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
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
		DefaultListModel model = (DefaultListModel) cboRank.getListModel();
		model.add("ĐẠT");
		return nRet;
	}
	
	private void registPropertyEditor() {
	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor
		typeEditor = typeEditor();
		guideEditor = initGuideEditor();
        getUIDataObjectBinder().registerCustomEditor(N_TRAINING_DETAILS.class, "TRAINING_TYPE", typeEditor);
        getUIDataObjectBinder().registerCustomEditor(N_TRAINING_DETAILS.class, "COURSE_ID", courseEditor);
        getUIDataObjectBinder().registerCustomEditor(N_TRAINING_DETAILS.class, "SUBJECT_ID", initSubjectEditor());
        getUIDataObjectBinder().registerCustomEditor(N_TRAINING_DETAILS.class, "RANK", new VniEditor());
        getUIDataObjectBinder().registerCustomEditor(N_TRAINING_DETAILS.class, "NOTE", new VniEditor());
        getUIDataObjectBinder().registerCustomEditor(N_TRAINING_DETAILS.class, "BDATE", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_TRAINING_DETAILS.class, "EDATE", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_TRAINING_DETAILS.class, "EXAM_DATE", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_TRAINING_DETAILS.class, "GUIDE_EMPSN", initGuideEditor());        
        getUIDataObjectBinder().registerCustomEditor(N_TRAINING_DETAILS.class, "MARK", new CustomNumberEditor(BigDecimal.class, true));
        getUIDataObjectBinder().registerCustomEditor(N_TRAINING_DETAILS.class, "EXAM_TIME", new CustomNumberEditor(BigDecimal.class, true));
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("ID_TRAINING", ID_TRAINING_DscField1, ID_TRAINING_CaptionLabel);
        getUIDataObjectBinder().addBindMap("COURSE_ID", cboCourse.getTextField(), COURSE_ID_CaptionLabel);
        getUIDataObjectBinder().addBindMap("SUBJECT_ID", cboSubject.getTextField(), SUBJECT_ID_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TRAINING_TYPE", sfType, TRAINING_TYPE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("BDATE", BDATE_DscDateField1, BDATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EDATE", EDATE_DscDateField2, EDATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("GUIDE_EMPSN", sfGuide, GUIDE_EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField6, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EXAM_DATE", EXAM_DATE_DscDateField3, EXAM_DATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("EXAM_TIME", EXAM_TIME_DscField7, EXAM_TIME_CaptionLabel);
        getUIDataObjectBinder().addBindMap("MARK", MARK_DscField8, MARK_CaptionLabel);
        getUIDataObjectBinder().addBindMap("RANK", cboRank.getTextField(), RANK_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField10, NOTE_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	
	public MappingPropertyEditor typeEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		e.put("CẬP NHẬT", "CAÄP NHAÄT");
		e.put("1 LẦN", "1 LAÀN");
		return e;
	}
	
	private MappingPropertyEditor initCourseEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_KHOA_HOC, String> dao = Application.getApp().getDao(N_KHOA_HOC.class);
		List<N_KHOA_HOC> list = dao.findAll(10000);
		for (N_KHOA_HOC data:list){
			e.put(Vni2Uni.convertToUnicode(data.getTEN_KHOA()), data.getMA_KHOA());
		}
		return e;
	}
	
	private MappingPropertyEditor initSubjectEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_TRAINING_LIST, String> dao = Application.getApp().getDao(N_TRAINING_LIST.class);
		List<N_TRAINING_LIST> list = dao.findAll(10000);
		for (N_TRAINING_LIST data:list){
			e.put(Vni2Uni.convertToUnicode(data.getTEN_MON_HOC()), data.getMA_MON_HOC());
		}
		return e;
	}
	
	public MappingPropertyEditor getSubjectEditor(String course){
		MappingPropertyEditor e = new MappingPropertyEditor();
		String sql = 
			"SELECT A.MA_MON_HOC, A.TEN_MON_HOC " +
			"FROM N_TRAINING_LIST A, N_CT_KHOA_HOC B " + 
			"WHERE A.MA_MON_HOC=B.ID_MONHOC AND B.ID_KHOA=?";
		IGenericDAO<N_TRAINING_LIST, String> dao = Application.getApp().getDao(N_TRAINING_LIST.class);
		List list = dao.find(100, sql, new Object[]{course});
		for (int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			e.put(Vni2Uni.convertToUnicode(obj[1].toString()), obj[0].toString());
		}
		return e;
	}
	
	private MappingPropertyEditor initGuideEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_TRAINER_LIST, String> dao = Application.getApp().getDao(N_TRAINER_LIST.class);
		List list = dao.find(1000, "select distinct e.EMPSN, e.FNAME, e.LNAME from N_EMPLOYEE e, N_TRAINER_LIST t where t.EMPSN_HL=e.EMPSN order by e.FNAME", new Object[]{});
		for (int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			String name = Vni2Uni.convertToUnicode(obj[1].toString() + " " + obj[2].toString());
			e.put(name, obj[0].toString());
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
		N_TRAINING_DETAILS data = (N_TRAINING_DETAILS) getDataObject();
		data.setID_TRAINING("_");
		data.setBDATE(new Date());
		data.setEDATE(new Date());
	}

	public MappingPropertyEditor getCourseEditor() {
		return courseEditor;
	}

	public void setCourseEditor(MappingPropertyEditor courseEditor) {
		this.courseEditor = courseEditor;
	}

	public MappingPropertyEditor getGuideEditor() {
		return guideEditor;
	}

	public void setGuideEditor(MappingPropertyEditor guideEditor) {
		this.guideEditor = guideEditor;
	}

	public MappingPropertyEditor getTypeEditor() {
		return typeEditor;
	}

	public void setTypeEditor(MappingPropertyEditor typeEditor) {
		this.typeEditor = typeEditor;
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {
		N_TRAINING_DETAILS data = (N_TRAINING_DETAILS) getDataObject();
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
		    data.setID_TRAINING(genPK(data.getEMPSN()));
		}
		System.out.println(data.getMARK());
		//使用者可在以下區域做儲存資料物件至DataObjectSet之前處理
		//<儲存前處理>
	}
	
	public String genPK(String empsn){		
		IGenericDAO<N_TRAINING_DETAILS, String> dao = Application.getApp().getDao(N_TRAINING_DETAILS.class);
		String sql = "select max(substr(ID_TRAINING,9,length(t.ID_TRAINING))) from N_TRAINING_DETAILS t where t.EMPSN=?";
		List list = dao.find(1, sql, new Object[]{empsn});
		if (list.size()>0&&list.get(0)!=null){
			int n = Integer.parseInt(list.get(0).toString()) + 1;
			if (n<10) return empsn + "000"+n;
			else if (n<100) return empsn + "00"+n;
			else if (n<1000) return empsn + "0"+n;
			else return empsn+n;
		}
		return empsn+"0001";
	}


	public void courseSelect(ActionEvent e) {
		Object course = ListBinder.getCboValue(cboCourse, courseEditor);
		if (course!=null){
			subjectEditor = getSubjectEditor(course.toString());
			ListBinder.bindComboBox(cboSubject, subjectEditor);			
		}else{
			((DefaultListModel)cboSubject.getListModel()).removeAll();
		}
		cboSubject.getTextField().setText("");
	}
	
	private boolean validateCourse(){
		Object course = ListBinder.getCboValue(cboCourse, courseEditor);
		if (course==null){
			setErrorMessage("Không có khóa học đã chọn");
			return false;
		}
		return true;
	}
	
	private boolean validateSubject(){
		Object subject = ListBinder.getCboValue(cboSubject, subjectEditor);
		if (subject==null){
			setErrorMessage("Khóa học đã chọn không có môn học " + cboSubject.getText());
			return false;
		}
		return true;
	}


	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		resourceBundle = ResourceBundle.getBundle(
				"resource.localization.UICaption", ApplicationInstance
						.getActive().getLocale());
		rootLayout = new Grid();
		rootLayout.setSize(4);
		add(rootLayout);
		ID_TRAINING_CaptionLabel = new Label();
		ID_TRAINING_CaptionLabel.setText(resourceBundle
				.getString("N_TRAINING_DETAILS.ID_TRAINING"));
		rootLayout.add(ID_TRAINING_CaptionLabel);
		ID_TRAINING_DscField1 = new DscField();
		ID_TRAINING_DscField1.setId("ID_TRAINING_DscField1");
		ID_TRAINING_DscField1.setEnabled(false);
		rootLayout.add(ID_TRAINING_DscField1);
		TRAINING_TYPE_CaptionLabel = new Label();
		TRAINING_TYPE_CaptionLabel.setText(resourceBundle
				.getString("N_TRAINING_DETAILS.TRAINING_TYPE"));
		rootLayout.add(TRAINING_TYPE_CaptionLabel);
		sfType = new SelectField();
		sfType.setWidth(new Extent(155, Extent.PX));
		rootLayout.add(sfType);
		COURSE_ID_CaptionLabel = new Label();
		COURSE_ID_CaptionLabel.setText(resourceBundle
				.getString("N_TRAINING_DETAILS.COURSE_ID"));
		rootLayout.add(COURSE_ID_CaptionLabel);
		cboCourse = new ComboBox();
		cboCourse.setWidth(new Extent(360, Extent.PX));
		cboCourse.setActionOnSelection(true);
		GridLayoutData cboCourseLayoutData = new GridLayoutData();
		cboCourseLayoutData.setColumnSpan(3);
		cboCourse.setLayoutData(cboCourseLayoutData);
		cboCourse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				courseSelect(e);
			}
		});
		rootLayout.add(cboCourse);
		SUBJECT_ID_CaptionLabel = new Label();
		SUBJECT_ID_CaptionLabel.setText(resourceBundle
				.getString("N_TRAINING_DETAILS.SUBJECT_ID"));
		rootLayout.add(SUBJECT_ID_CaptionLabel);
		cboSubject = new ComboBox();
		cboSubject.setWidth(new Extent(360, Extent.PX));
		GridLayoutData cboSubjectLayoutData = new GridLayoutData();
		cboSubjectLayoutData.setColumnSpan(3);
		cboSubject.setLayoutData(cboSubjectLayoutData);
		rootLayout.add(cboSubject);
		BDATE_CaptionLabel = new Label();
		BDATE_CaptionLabel.setText(resourceBundle
				.getString("N_TRAINING_DETAILS.BDATE"));
		rootLayout.add(BDATE_CaptionLabel);
		BDATE_DscDateField1 = new DscDateField();
		BDATE_DscDateField1.setId("BDATE_DscDateField1");
		rootLayout.add(BDATE_DscDateField1);
		EDATE_CaptionLabel = new Label();
		EDATE_CaptionLabel.setText(resourceBundle
				.getString("N_TRAINING_DETAILS.EDATE"));
		rootLayout.add(EDATE_CaptionLabel);
		EDATE_DscDateField2 = new DscDateField();
		EDATE_DscDateField2.setId("EDATE_DscDateField2");
		rootLayout.add(EDATE_DscDateField2);
		GUIDE_EMPSN_CaptionLabel = new Label();
		GUIDE_EMPSN_CaptionLabel.setText(resourceBundle
				.getString("N_TRAINING_DETAILS.GUIDE_EMPSN"));
		rootLayout.add(GUIDE_EMPSN_CaptionLabel);
		sfGuide = new SelectField();
		sfGuide.setWidth(new Extent(220, Extent.PX));
		rootLayout.add(sfGuide);
		EMPSN_CaptionLabel = new Label();
		EMPSN_CaptionLabel.setText(resourceBundle
				.getString("N_TRAINING_DETAILS.EMPSN"));
		rootLayout.add(EMPSN_CaptionLabel);
		EMPSN_DscField6 = new DscField();
		EMPSN_DscField6.setId("EMPSN_DscField6");
		rootLayout.add(EMPSN_DscField6);
		EXAM_DATE_CaptionLabel = new Label();
		EXAM_DATE_CaptionLabel.setText(resourceBundle
				.getString("N_TRAINING_DETAILS.EXAM_DATE"));
		rootLayout.add(EXAM_DATE_CaptionLabel);
		EXAM_DATE_DscDateField3 = new DscDateField();
		EXAM_DATE_DscDateField3.setId("EXAM_DATE_DscDateField3");
		rootLayout.add(EXAM_DATE_DscDateField3);
		EXAM_TIME_CaptionLabel = new Label();
		EXAM_TIME_CaptionLabel.setText(resourceBundle
				.getString("N_TRAINING_DETAILS.EXAM_TIME"));
		rootLayout.add(EXAM_TIME_CaptionLabel);
		EXAM_TIME_DscField7 = new DscField();
		EXAM_TIME_DscField7.setId("EXAM_TIME_DscField7");
		EXAM_TIME_DscField7.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		EXAM_TIME_DscField7.setMaximumLength(1);
		rootLayout.add(EXAM_TIME_DscField7);
		MARK_CaptionLabel = new Label();
		MARK_CaptionLabel.setText(resourceBundle
				.getString("N_TRAINING_DETAILS.MARK"));
		rootLayout.add(MARK_CaptionLabel);
		MARK_DscField8 = new DscField();
		MARK_DscField8.setId("MARK_DscField8");
		MARK_DscField8.setInputType(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		MARK_DscField8.setMaximumLength(4);
		rootLayout.add(MARK_DscField8);
		RANK_CaptionLabel = new Label();
		RANK_CaptionLabel.setText(resourceBundle
				.getString("N_TRAINING_DETAILS.RANK"));
		rootLayout.add(RANK_CaptionLabel);
		cboRank = new ComboBox();
		rootLayout.add(cboRank);
		NOTE_CaptionLabel = new Label();
		NOTE_CaptionLabel.setText(resourceBundle
				.getString("N_TRAINING_DETAILS.NOTE"));
		rootLayout.add(NOTE_CaptionLabel);
		NOTE_DscField10 = new DscField();
		NOTE_DscField10.setId("NOTE_DscField10");
		NOTE_DscField10.setWidth(new Extent(360, Extent.PX));
		GridLayoutData NOTE_DscField10LayoutData = new GridLayoutData();
		NOTE_DscField10LayoutData.setColumnSpan(3);
		NOTE_DscField10.setLayoutData(NOTE_DscField10LayoutData);
		rootLayout.add(NOTE_DscField10);
	}

}
