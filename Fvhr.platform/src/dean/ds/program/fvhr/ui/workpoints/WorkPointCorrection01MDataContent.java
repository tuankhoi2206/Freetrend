package ds.program.fvhr.ui.workpoints;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import nextapp.echo2.app.Grid;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_LOST_DATA_DETAIL;
import ds.program.fvhr.domain.N_LOST_DATA_REASON;
import ds.program.fvhr.domain.N_REGISTER_SHIFT;
import ds.program.fvhr.domain.N_SHIFT;
import ds.program.fvhr.domain.pk.N_LOST_DATA_DETAILPk;
import ds.program.fvhr.domain.pk.N_REGISTER_SHIFTPk;
import fv.components.SelectItem;
import fv.util.ApplicationHelper;
import fv.util.HRUtils;
import fv.util.ListBinder;
import fv.util.Vni2Uni;
import fv.util.VniEditor;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscDateField;
import echopointng.model.CalendarEvent;
import echopointng.model.CalendarSelectionListener;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;

public class WorkPointCorrection01MDataContent extends DataContent {
	private static final long serialVersionUID = 1L;
	private nextapp.echo2.app.Grid rootLayout;
	private nextapp.echo2.app.Label EMPSN_CaptionLabel;
	private nextapp.echo2.app.Label DATE_LOST_CaptionLabel;
	private dsc.echo2app.component.DscDateField DATE_LOST_DscDateField1;
	private nextapp.echo2.app.Label REASONS_CaptionLabel;
	private nextapp.echo2.app.Label SIGN_TIME_CaptionLabel;
	private dsc.echo2app.component.DscField SIGN_TIME_DscField4;
	private dsc.echo2app.component.DscField TIN_DscField5;
	private dsc.echo2app.component.DscField TOUT_DscField7;
	private nextapp.echo2.app.Label TTEMP_CaptionLabel;
	private dsc.echo2app.component.DscField TTEMP_DscField9;
	private DscField EMPSN_DscField2;
	private Label lblInfo;
	private SelectField sfReason;
	private SelectField sfShift;
	private N_LOST_DATA_DETAIL detailex;
	private Set<String> signsMap = new HashSet<String>();
	private Date crazyDate = new Date();
	private CheckBox chkIn;
	private CheckBox chkOut;
	/**
	 * Creates a new <code>WorkPointCorrection01MDataContent</code>.
	 */
	public WorkPointCorrection01MDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
		DATE_LOST_DscDateField1.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		ListBinder.bindSelectField(sfShift, shiftEditor(), true);
		DATE_LOST_DscDateField1.getModel().addListener(new CalendarSelectionListener(){

			@Override
			public void displayedDateChange(CalendarEvent arg0) {
			}

			@Override
			public void selectedDateChange(CalendarEvent arg0) {
				autoSelectShift();
			}

		});
	}


	protected void autoSelectShift() {
		if (EMPSN_DscField2.getText().matches("[0-9]{8}")){
			String empsn = EMPSN_DscField2.getText();
			Calendar date = DATE_LOST_DscDateField1.getSelectedDate();
			IGenericDAO<N_REGISTER_SHIFT, N_REGISTER_SHIFTPk> dao = Application.getApp().getDao(N_REGISTER_SHIFT.class);
			N_REGISTER_SHIFTPk pk = new N_REGISTER_SHIFTPk(empsn, date.getTime());
			N_REGISTER_SHIFT shift = dao.findById(pk);
			IGenericDAO<N_SHIFT, String> shiftDao = Application.getApp().getDao(N_SHIFT.class);
			if (shift!=null){
				N_SHIFT shift1 = shiftDao.findById(shift.getID_SHIFT());
				ListBinder.refreshIndex(sfShift, shift1);
			}else{
				IGenericDAO<N_EMPLOYEE, String> edao = Application.getApp().getDao(N_EMPLOYEE.class);
				N_EMPLOYEE emp = edao.findById(empsn);
				N_SHIFT shift1 = shiftDao.findById(emp.getSHIFT());
				ListBinder.refreshIndex(sfShift, shift1);
			}
			if (sfShift.getSelectedIndex()>=0)
				shiftHint(null);
		}
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
			//            EMPSN_Object_DscField1.setEnabled(false);
			//            EMPSN_Object_DscField1.setStyleName("Default.ReadonlyField");
			//            DATE_LOST_DscDateField1.setEnabled(false);
			//            DATE_LOST_DscDateField1.setStyleName("Default.ReadonlyField");
			//            TIN_DscField5.setEnabled(false);
			//            TIN_DscField5.setStyleName("Default.ReadonlyField");
			//            TOUT_DscField7.setEnabled(false);
			//            TOUT_DscField7.setStyleName("Default.ReadonlyField");
			SIGN_TIME_DscField4.setEnabled(false);
			SIGN_TIME_DscField4.setStyleName("Default.ReadonlyField");
			sfShift.setSelectedIndex(-1);
			//以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
			//----------------------------------------------------------------------------------
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				EMPSN_DscField2.setEnabled(true);
				DATE_LOST_DscDateField1.setEnabled(true);
			} else {
				EMPSN_DscField2.setEnabled(false);
				DATE_LOST_DscDateField1.setEnabled(false);
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
		return N_LOST_DATA_DETAIL.class;
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
		//N_LOST_DATA_DETAIL data = (N_LOST_DATA_DETAIL) getDataObject();
	}


	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		N_LOST_DATA_DETAIL data = (N_LOST_DATA_DETAIL) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			//checklock
			HRUtils util = ApplicationHelper.getHRUtils();
			Date date = data.getDATE_LOST();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);			
			int mm = cal.get(Calendar.MONTH)+1;
			String months = String.valueOf(mm<10?"0"+mm:""+mm);
			String years = String.valueOf(cal.get(Calendar.YEAR));
			if (!util.getPermissionValidator().hasEmpsnPermission(data.getEMPSN())){
				setErrorMessage("Anh/chị không có quyền thao tác số thẻ này.");
				return false;
			}

			if(util.getWorkpointsValidator().isWPLocked(data.getEMPSN(), months, years)){
				setErrorMessage("Đã khóa dữ liệu số thẻ " + data.getEMPSN() + " tháng " + months + "/" + years);
				return false;
			}

			if (sfShift.getSelectedIndex()<0){
				setErrorMessage("Chọn ca làm việc.");
				return false;
			}
			if ((!chkIn.isSelected()&&!chkOut.isSelected())||StringUtils.isBlank(data.getTIN())&&StringUtils.isBlank(data.getTOUT())){
				setErrorMessage("Nhập thời gian");
				return false;
			}
			if (!StringUtils.isBlank(data.getTIN())){
				if (data.getTIN().matches("[0-9]{4}")){
					int hour = Integer.parseInt(data.getTIN().substring(0,2));
					int minute = Integer.parseInt(data.getTIN().substring(2, 4));
					if (hour>23||hour<0||minute>59||minute<0){
						setErrorMessage("Giờ vào không hợp lệ");
						return false;
					}
				}else{
					setErrorMessage("Giờ vào không hợp lệ");
					return false;
				}
			}
			if(!StringUtils.isBlank(data.getTOUT())){
				if (data.getTOUT().matches("[0-9]{4}")){
					int hour = Integer.parseInt(data.getTOUT().substring(0,2));
					int minute = Integer.parseInt(data.getTOUT().substring(2, 4));
					if (hour>23||hour<0||minute>59||minute<0){
						setErrorMessage("Giờ ra không hợp lệ");
						return false;
					}

				}else{
					setErrorMessage("Giờ ra không hợp lệ");
					return false;
				}
			}
			IGenericDAO<N_EMPLOYEE, String> dao1 = Application.getApp().getDao(N_EMPLOYEE.class);
			N_EMPLOYEE emp = dao1.findById(data.getEMPSN());
			if (emp==null){
				setErrorMessage("Không có số thẻ " + data.getEMPSN());
				return false;
			}
			if (getProgram().getDataMode()==IProgram.DATAMODE_NEW){
				IGenericDAO<N_LOST_DATA_DETAIL, N_LOST_DATA_DETAILPk> dao = Application.getApp().getDao(N_LOST_DATA_DETAIL.class);
				N_LOST_DATA_DETAILPk pk = new N_LOST_DATA_DETAILPk(data.getEMPSN(),data.getDATE_LOST());
				if (dao.findById(pk)!=null){
					//if la XNDLKT cua ca dem thi se update luon cho NS, ko can keu chon update
					//hoanganh
					if ( (data.getTIN()==null && data.getTOUT()==null && data.getSIGN_TIME()==0 && data.getTTEMP()!=null) ) {
						return true;
					}
					else{			
						setErrorMessage("Đã có dữ liệu mất. Chọn chỉnh sửa để thay đổi.");
						return false;
					}
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
		//使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
		//TODO 請修改元件之PropertyEditor
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "FNAME", new VniEditor());
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "LNAME", new VniEditor());
		getUIDataObjectBinder().registerCustomEditor(N_LOST_DATA_DETAIL.class, "DATE_LOST", PropertyEditors.createDateEditor("dd/MM/yyyy"));
		getUIDataObjectBinder().registerCustomEditor(N_LOST_DATA_DETAIL.class, "REASONS", reasonEditor());
	}

	private void bindUI() {
		//----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
		//        getUIDataObjectBinder().addBindMap("EMPSN_Object", EMPSN_Object_DscField1, EMPSN_Object_CaptionLabel);
		getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField2, EMPSN_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DATE_LOST", DATE_LOST_DscDateField1, DATE_LOST_CaptionLabel);
		getUIDataObjectBinder().addBindMap("REASONS", sfReason, REASONS_CaptionLabel);
		getUIDataObjectBinder().addBindMap("SIGN_TIME", SIGN_TIME_DscField4, SIGN_TIME_CaptionLabel);
		getUIDataObjectBinder().addBindMap("TIN", TIN_DscField5, null);
		//        getUIDataObjectBinder().addBindMap("TMID", TMID_DscField6, TMID_CaptionLabel);
		getUIDataObjectBinder().addBindMap("TOUT", TOUT_DscField7, null);
		//        getUIDataObjectBinder().addBindMap("TOVER", TOVER_DscField8, TOVER_CaptionLabel);
		getUIDataObjectBinder().addBindMap("TTEMP", TTEMP_DscField9, TTEMP_CaptionLabel);
		//以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
		//----------------------------------------------------------------------------------
	}

	protected MappingPropertyEditor reasonEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_LOST_DATA_REASON, String> dao = Application.getApp().getDao(N_LOST_DATA_REASON.class);
		List<N_LOST_DATA_REASON> list = dao.findAll(100);
		for (N_LOST_DATA_REASON data:list){
			e.put(data.getNAME_LOST(), data.getID_LOST());
			if (data.getNVALUE().trim().equals("1")){
				signsMap.add(data.getID_LOST());
			}
		}
		return e;
	}

	@SuppressWarnings("unchecked")
	protected MappingPropertyEditor shiftEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_SHIFT, String> dao = Application.getApp().getDao(N_SHIFT.class);
		List<N_SHIFT> list = dao.findAll(100);
		for (N_SHIFT shift:list){
			e.put("(" + shift.getID_SHIFT() + ") " + shift.getNAME_SHIFT(), shift);
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

	public void showInfo(){
		N_LOST_DATA_DETAIL data = (N_LOST_DATA_DETAIL) getDataObject();
		N_EMPLOYEE emp = data.getEMPSN_Object();
		if (emp!=null){
			String name = Vni2Uni.convertToUnicode(emp.getFNAME() + " " + emp.getLNAME());
			lblInfo.setText(name);
		}else{
			lblInfo.setText("");			
		}
	}

	/*
	 * 做資料新增時內定預設數值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doNewDefaulData()
	 */
	@Override
	protected void doNewDefaulData() {
		N_LOST_DATA_DETAIL data = (N_LOST_DATA_DETAIL) getDataObject();
		data.setDATE_LOST(crazyDate);
		lblInfo.setText("");
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {
		N_LOST_DATA_DETAIL data = (N_LOST_DATA_DETAIL) getDataObject();
		crazyDate.setTime(data.getDATE_LOST().getTime());
		String reason = data.getREASONS();
		data.setSIGN_TIME(0l);
		
		if (!chkIn.isSelected()){
			data.setTIN(null);
		}
		if (!chkOut.isSelected()){
			data.setTOUT(null);
		}
		
		if (signsMap.contains(reason)){
			if (!StringUtils.isBlank(data.getTIN())) data.setSIGN_TIME(data.getSIGN_TIME()+1l);
			if (!StringUtils.isBlank(data.getTOUT())||!StringUtils.isBlank(data.getTOVER())) data.setSIGN_TIME(data.getSIGN_TIME()+1l);
		}
		
		SelectItem item = (SelectItem) sfShift.getSelectedItem();
		N_SHIFT shift = (N_SHIFT) item.getValue();
		detailex = ca3(data, shift);
	}

	public N_LOST_DATA_DETAIL ca3(N_LOST_DATA_DETAIL data, N_SHIFT shift){
		int tin = Integer.parseInt(shift.getTIME_IN().replace(":", ""));
		int tout = Integer.parseInt(shift.getTIME_OUT().replace(":", ""));
		int ttin=-2, ttout=-1;
		if (!StringUtils.isBlank(data.getTIN())) ttin=Integer.parseInt(data.getTIN());
		if (!StringUtils.isBlank(data.getTOUT())) ttout = Integer.parseInt(data.getTOUT());
		N_LOST_DATA_DETAIL out = null;
		if (tin>tout){//toi -> sang, begin la toi, end la sang
			if (ttout>=0&&ttin>=0&&ttout<ttin){//ngay hom sau, doi ca khac ngay 
				out = new N_LOST_DATA_DETAIL();
				out.setEMPSN(data.getEMPSN());
				Calendar cal = Calendar.getInstance();
				cal.setTime(data.getDATE_LOST());
				cal.add(Calendar.DAY_OF_MONTH, 1);
				out.setREASONS(data.getREASONS());
				out.setDATE_LOST(cal.getTime());
				out.setTTEMP(data.getTOUT());
				data.setTOUT(null);
			} else if (ttin<0&&ttout>=0&&ttout<1200){//trong ngay, doi ca trong cung 1 ngay VD tu HC sang  CA dem
				Calendar cal = Calendar.getInstance();
				cal.setTime(data.getDATE_LOST());
				cal.add(Calendar.DAY_OF_MONTH, 1);
				data.setDATE_LOST(cal.getTime());
				data.setTTEMP(data.getTOUT());
				data.setTOUT(null);
			}
		}
		return out;
	}

	public N_LOST_DATA_DETAIL getDataEx(){
		return this.detailex;
	}

	public Set<String> getSignsMap(){
		return signsMap;
	}


	private void findEmployee(ActionEvent e) {
		IGenericDAO<N_EMPLOYEE, String> dao = Application.getApp().getDao(N_EMPLOYEE.class);
		N_EMPLOYEE emp = dao.findById(EMPSN_DscField2.getText());
		if (emp!=null){
			lblInfo.setText(Vni2Uni.convertToUnicode(emp.getFNAME() + " " + emp.getLNAME()));
			autoSelectShift();
		}else{
			lblInfo.setText("");
			EMPSN_DscField2.setText("");
		}
	}

	//cmn
	public void focusOnEmpsn() {
		EMPSN_DscField2.requestFocus();
	}

	private void shiftHint(ActionEvent e) {
		SelectItem item = (SelectItem) sfShift.getSelectedItem();
		N_SHIFT shift = (N_SHIFT) item.getValue();
		String tin = shift.getTIME_IN().replace(":", "");
		String tout = shift.getTIME_OUT().replace(":", "");
		TIN_DscField5.setText(tin);
		TOUT_DscField7.setText(tout);
	}

	private void showHideInTime(ActionEvent e) {
		if (chkIn.isSelected()){
			TIN_DscField5.setEnabled(true);
		}else{
			TIN_DscField5.setEnabled(false);
		}
	}

	private void showHideOutTime(ActionEvent e) {
		if (chkOut.isSelected()){
			TOUT_DscField7.setEnabled(true);
		}else{
			TOUT_DscField7.setEnabled(false);
		}
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
		EMPSN_CaptionLabel = new Label();
		EMPSN_CaptionLabel.setText("N_LOST_DATA_DETAIL.EMPSN");
		rootLayout.add(EMPSN_CaptionLabel);
		Row row1 = new Row();
		rootLayout.add(row1);
		EMPSN_DscField2 = new DscField();
		EMPSN_DscField2.setId("EMPSN_DscField2");
		EMPSN_DscField2.setInputType(DscField.INPUT_TYPE_TEXT);
		EMPSN_DscField2.setWidth(new Extent(80, Extent.PX));
		EMPSN_DscField2.setMaximumLength(8);
		EMPSN_DscField2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findEmployee(e);
			}
		});
		row1.add(EMPSN_DscField2);
		lblInfo = new Label();
		row1.add(lblInfo);
		DATE_LOST_CaptionLabel = new Label();
		DATE_LOST_CaptionLabel.setText("N_LOST_DATA_DETAIL.DATE_LOST");
		rootLayout.add(DATE_LOST_CaptionLabel);
		DATE_LOST_DscDateField1 = new DscDateField();
		DATE_LOST_DscDateField1.setId("DATE_LOST_DscDateField1");
		rootLayout.add(DATE_LOST_DscDateField1);
		REASONS_CaptionLabel = new Label();
		REASONS_CaptionLabel.setText("N_LOST_DATA_DETAIL.REASONS");
		rootLayout.add(REASONS_CaptionLabel);
		sfReason = new SelectField();
		sfReason.setWidth(new Extent(200, Extent.PX));
		rootLayout.add(sfReason);
		Label label1 = new Label();
		label1.setText("Ca làm việc");
		rootLayout.add(label1);
		sfShift = new SelectField();
		sfShift.setWidth(new Extent(200, Extent.PX));
		sfShift.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shiftHint(e);
			}
		});
		rootLayout.add(sfShift);
		SIGN_TIME_CaptionLabel = new Label();
		SIGN_TIME_CaptionLabel.setText("N_LOST_DATA_DETAIL.SIGN_TIME");
		rootLayout.add(SIGN_TIME_CaptionLabel);
		SIGN_TIME_DscField4 = new DscField();
		SIGN_TIME_DscField4.setId("SIGN_TIME_DscField4");
		SIGN_TIME_DscField4.setWidth(new Extent(60, Extent.PX));
		rootLayout.add(SIGN_TIME_DscField4);
		chkIn = new CheckBox();
		chkIn.setSelected(true);
		chkIn.setText("Giờ vào");
		chkIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showHideInTime(e);
			}
		});
		rootLayout.add(chkIn);
		TIN_DscField5 = new DscField();
		TIN_DscField5.setId("TIN_DscField5");
		TIN_DscField5.setWidth(new Extent(60, Extent.PX));
		TIN_DscField5.setDisabledBackground(new Color(0x808080));
		TIN_DscField5.setMaximumLength(4);
		rootLayout.add(TIN_DscField5);
		chkOut = new CheckBox();
		chkOut.setSelected(true);
		chkOut.setText("Giờ ra");
		chkOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showHideOutTime(e);
			}
		});
		rootLayout.add(chkOut);
		TOUT_DscField7 = new DscField();
		TOUT_DscField7.setId("TOUT_DscField7");
		TOUT_DscField7.setWidth(new Extent(60, Extent.PX));
		TOUT_DscField7.setDisabledBackground(new Color(0x808080));
		TOUT_DscField7.setMaximumLength(4);
		rootLayout.add(TOUT_DscField7);
		TTEMP_CaptionLabel = new Label();
		TTEMP_CaptionLabel.setText("N_LOST_DATA_DETAIL.TTEMP");
		rootLayout.add(TTEMP_CaptionLabel);
		TTEMP_DscField9 = new DscField();
		TTEMP_DscField9.setId("TTEMP_DscField9");
		TTEMP_DscField9.setEnabled(false);
		TTEMP_DscField9.setWidth(new Extent(60, Extent.PX));
		rootLayout.add(TTEMP_DscField9);
	}

}
