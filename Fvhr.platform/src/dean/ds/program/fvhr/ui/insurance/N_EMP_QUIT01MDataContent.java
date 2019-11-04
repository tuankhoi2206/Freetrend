package ds.program.fvhr.ui.insurance;

import it.businesslogic.ireport.gui.MessageBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//import org.apache.poi.hssf.record.formula.eval.ConcatEval;
import org.jaxen.function.SubstringFunction;


import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.dao.insuranse.N_EMP_QUITDAO;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.domain.N_BAOGIAM_STATUS;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_EMP_QUIT;
import ds.program.fvhr.domain.N_KYLUAT;
import ds.program.fvhr.domain.N_QUANLY;
import ds.program.fvhr.domain.N_QUIT_REASON;
import ds.program.fvhr.domain.pk.N_EMP_QUITPk;
import ds.program.fvhr.domain.pk.N_KYLUATPk;
import ds.program.fvhr.ui.insurance.OBJ_UTILITY;
import ds.program.users.domain.DSPB02;
import fv.util.DateUtils;
import fv.util.FvLogger;
import fv.util.Vni2Uni;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscDateField;
import echopointng.model.CalendarEvent;
import echopointng.model.CalendarSelectionListener;

public class N_EMP_QUIT01MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label DEPSN_CaptionLabel;
    private dsc.echo2app.component.DscField DEPSN_DscField2;
    private nextapp.echo2.app.Label ID_QUIT_REASON_CaptionLabel;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField7;
    private nextapp.echo2.app.Label OFF_DATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField OFF_DATE_DscDateField1;
    private nextapp.echo2.app.Label REAL_OFF_DATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField REAL_OFF_DATE_DscDateField2;
    private nextapp.echo2.app.Label DATE_HEN_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_HEN_DscDateField3;
    private nextapp.echo2.app.Label MM_DENBU_CaptionLabel;
    private dsc.echo2app.component.DscField MM_DENBU_DscField8;
    private nextapp.echo2.app.Label SO_QDNV_CaptionLabel;
    private dsc.echo2app.component.DscField SO_QDNV_DscField9;
    private nextapp.echo2.app.Label DOT_TV_CaptionLabel;
    private dsc.echo2app.component.DscDateField DOT_TV_DscDateField4;
    private nextapp.echo2.app.Label THANG_TRUBH_CaptionLabel;
    private dsc.echo2app.component.DscDateField THANG_TRUBH_DscDateField5;
    private nextapp.echo2.app.Label DATE_BHYT_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_BHYT_DscDateField6;
    private nextapp.echo2.app.Label THE_BHYT_CaptionLabel;
    //private dsc.echo2app.component.DscField THE_BHYT_DscField10;
    private nextapp.echo2.app.Label NOTE_GIAM_BHYT_CaptionLabel;
    //private dsc.echo2app.component.DscField NOTE_GIAM_BHYT_DscField11;
    private nextapp.echo2.app.Label FROM_DATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField FROM_DATE_DscDateField7;
    private nextapp.echo2.app.Label TO_DATE_CaptionLabel;
    private dsc.echo2app.component.DscDateField TO_DATE_DscDateField8;
    private nextapp.echo2.app.Label DATE_AGAIN_CaptionLabel;
    private dsc.echo2app.component.DscDateField DATE_AGAIN_DscDateField9;
    private nextapp.echo2.app.Label THANG_TANGLAI_CaptionLabel;
    private dsc.echo2app.component.DscDateField THANG_TANGLAI_DscDateField10;
    private nextapp.echo2.app.Label TYLE_TANGLAI_CaptionLabel;
    private dsc.echo2app.component.DscField TYLE_TANGLAI_DscField12;
    private nextapp.echo2.app.Label MONTH_GIAMBH_CaptionLabel;
    private dsc.echo2app.component.DscField MONTH_GIAMBH_DscField13;
    private nextapp.echo2.app.Label GIAM_BHYT_CaptionLabel;
    private dsc.echo2app.component.DscField GIAM_BHYT_DscField14;
    private nextapp.echo2.app.Label NOTE_BH_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_BH_DscField15;
	private SelectField sfQuitReason;
	private SelectField sfLoaiBaoGiam;
	private SelectField sfTraTheBHYT;
	private SelectField sfLoaiBaoTang;
	private nextapp.echo2.app.CheckBox cbQDNV;
	private nextapp.echo2.app.Label FNAME_Label1;
	private nextapp.echo2.app.Label FNAME_Label;
	
	
	/**
	 * Creates a new <code>N_EMP_QUIT01MDataContent</code>.
	 */
	public N_EMP_QUIT01MDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
		moreInit();
	}

	private void moreInit() {
		// TODO Auto-generated method stub	
				
		sfQuitReason.setWidth(new Extent(8, Extent.CM));
		sfLoaiBaoTang.setWidth(new Extent(8, Extent.CM));
		
		/*FNAME_Label = new Label();
		FNAME_Label.setText("Ho va Ten");
		rootLayout.add(FNAME_Label);*/
		
		cbQDNV = new CheckBox();
		cbQDNV.setText("Có Số QDNV");
		
		cbQDNV.setInsets(new Insets(new Extent(120, Extent.PX), new Extent(
				5, Extent.PX), new Extent(0, Extent.PX), new Extent(0,
						Extent.PX)));
		
		cbQDNV.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if (cbQDNV.isSelected() && EMPSN_DscField1.getText().isEmpty()==false){
					N_EMP_QUITDAO quitDao= new N_EMP_QUITDAO();
					String soQDNV = quitDao.getSoQDNV(EMPSN_DscField1.getText(), REAL_OFF_DATE_DscDateField2.getSelectedDate().getTime());
					if (SO_QDNV_DscField9.getText().isEmpty())
						SO_QDNV_DscField9.setText(soQDNV);
				}
				else{
					if (SO_QDNV_DscField9.getText().equals(null))
						SO_QDNV_DscField9.setText(null);
				}
			}
		});
		rootLayout.add(cbQDNV);		
	
		EMPSN_DscField1.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				String fname;
				InsuranceDAO ins = new InsuranceDAO();
				String soThe =EMPSN_DscField1.getText().toString(); 
				String chuoiTB = ins.checkEmpsn(soThe);
				if (chuoiTB!=null){					
					Application.getApp().showMessageDialog("Thông Báo", chuoiTB, MessageBox.OK);
				}
				else
				{					
					if (ins.checkQLyEmpsn(soThe)==false){
						chuoiTB= "Bạn không có quyền thao tác trên dữ liệu này.";				
						Application.getApp().showMessageDialog("Thông Báo", chuoiTB, MessageBox.OK);			
					}
					else
					{
						OBJ_UTILITY obj = new OBJ_UTILITY();
						fname=obj.findNameEmployee(soThe);
						//FNAME_Label.setBackground(Color.RED);
						FNAME_Label.setForeground(Color.RED);
						FNAME_Label.setText(fname);		
						
						IGenericDAO<N_EMPLOYEE, String> objDao = Application.getApp().getDao(N_EMPLOYEE.class);
						N_EMPLOYEE  objData = objDao.findById(soThe);		
						String depsn = objData.getDEPSN();
						DEPSN_DscField2.setText(depsn);
					}
				}
			}
		});	
		
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
            DEPSN_DscField2.setEnabled(false);
            DEPSN_DscField2.setStyleName("Default.ReadonlyField");
            MM_DENBU_DscField8.setText("0");
            SO_QDNV_DscField9.setEnabled(false);
            SO_QDNV_DscField9.setStyleName("Default.ReadonlyField");
			MONTH_GIAMBH_DscField13.setEnabled(false);
            MONTH_GIAMBH_DscField13.setStyleName("Default.ReadonlyField");
            GIAM_BHYT_DscField14.setEnabled(false);
            GIAM_BHYT_DscField14.setStyleName("Default.ReadonlyField");
            NOTE_BH_DscField15.setEnabled(false);
            NOTE_BH_DscField15.setStyleName("Default.ReadonlyField");
            //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            //----------------------------------------------------------------------------------
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				//新增時，設定哪些元件Enable/Disable
			} else {
				//修改時，設定哪些元件Enable/Disable				
			}
		}

		//HIEN THI TEN KHI TIM KIEM
		OBJ_UTILITY obj = new OBJ_UTILITY();
		String fname=obj.findNameEmployee(EMPSN_DscField1.getText());
		FNAME_Label.setForeground(Color.RED);
		//FNAME_Label.setBackground(Color.BLUE);				
		FNAME_Label.setText(fname);			
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
		return N_EMP_QUIT.class;
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
		
		//N_EMP_QUIT data = (N_EMP_QUIT) getDataObject();
	}

	private String[] checkData(String soThe,Date ngayXinNghi, Date ngayThucNghi, Date thangTruBH
			,Date dotTV,String traTheBHYT, Date ngayTraThe
			,String loaiBaoGiam, Date giamTuNgay, Date giamDenNgay
			,Date ngayDiLamLai, Date thangTangLai, Double thoiGianTangLai) {
		// TODO Auto-generated method stub
		String chuoiTB = null;	
		String tenXuong = null;
		Integer namThucNghi = 0;
		N_EMP_QUITDAO empQuit	= new N_EMP_QUITDAO();
		String soQDNV = empQuit.getSoQDNV(soThe,ngayThucNghi);
		InsuranceDAO insDao = new InsuranceDAO();
		//kiem tra su ton tai cua so the
		chuoiTB = insDao.checkEmpsn(soThe);
		if (chuoiTB!=null)
		{
			setErrorMessage(chuoiTB);				
			return new String[]{chuoiTB,soQDNV};
		}
		
		//kiem tra vung quan ly			
		if (insDao.checkQLyEmpsn(soThe)==false){
			chuoiTB= "Bạn không có quyền thao tác trên dữ liệu này.";				
			return new String[]{chuoiTB,soQDNV};			
		}		
		
		// kiem tra khoa du lieu
		OBJ_UTILITY objU = new OBJ_UTILITY();
		boolean check =true;	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String ngay	= sdf.format(ngayThucNghi);
		
		String thang 	= ngay.substring(3,5);
		String nam		= ngay.substring(6,10);
		check =insDao.CheckKhoaDataMonth(soThe, thang, nam);
		if (check ==false){
			chuoiTB="Đã khóa xử lý dữ liệu trong tháng";
			return new String[]{chuoiTB,soQDNV};
		}
		
		// 0 hop le, 1 ko hop le		
		if ((ngayThucNghi!=null) && (ngayThucNghi.compareTo(ngayXinNghi)<0)){
			chuoiTB = "Ngày thực nghỉ phải >= ngày xin nghỉ.";
			return new String[]{chuoiTB,soQDNV};
		}
		if (dotTV!=null){
			if (dotTV.getDay()!=1){
				System.out.print(dotTV.getDay());
				chuoiTB = "Đợt thôi việc phải là thứ 2 trong tuần.";
				return new String[]{chuoiTB,soQDNV};
			}
		}		
		if (thangTruBH!=null){
			if (thangTruBH.getDate()!=1){
				chuoiTB = "Vui lòng chọn ngày 1 cho tháng trừ bảo hiểm.";
				return new String[]{chuoiTB,soQDNV};
			}
		}
		if ( (traTheBHYT=="1" && ngayTraThe == null)|| (traTheBHYT=="0" && ngayTraThe!=null)) {
			chuoiTB = "Kiểm tra lại ngày trả thẻ BHYT.";
			return new String[]{chuoiTB,soQDNV};
		}
		
		// giam trong tang moi hoac chua bao giam
		if ( (loaiBaoGiam== null || loaiBaoGiam == "GIAM TANG MOI") && (giamTuNgay != null || giamDenNgay != null)) {
			chuoiTB = "Kiểm tra lại thông tin báo giảm";
			return new String[]{chuoiTB,soQDNV};
		}
		//giam tu ngay den ngay
		if  (loaiBaoGiam ==" " && (giamTuNgay == null || giamDenNgay == null)) {
			chuoiTB = "Kiểm tra lại thông tin báo giảm";
			return new String[]{chuoiTB,soQDNV};
		}
		// if co thong tin bao giam tu ngay den ngay ma ko phai la 
		// tuNgay la thu 5 or T2, denNgay la CN or T4
		if  (loaiBaoGiam ==" " && (giamTuNgay != null && giamDenNgay != null)) {
			if((giamTuNgay.getDay()!=Calendar.THURSDAY && giamTuNgay.getDay()!= Calendar.MONDAY)
				||(giamDenNgay.getDay()!=Calendar.SUNDAY && giamDenNgay.getDay()!=Calendar.WEDNESDAY)){
				chuoiTB = "Kiểm tra lại thông tin báo giảm";
				return new String[]{chuoiTB,soQDNV};
			}
		}		
		
		if ( (ngayDiLamLai == null && (thangTangLai != null || thoiGianTangLai !=null))
			|| (ngayDiLamLai !=null && (thangTangLai == null || thoiGianTangLai == null))) {
			chuoiTB = "Kiểm tra thông tin nghỉ việc đi làm lại.";
			return new String[]{chuoiTB,soQDNV};		
		}
		
		return new String[]{chuoiTB,soQDNV};	
		
	}	
	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		N_EMP_QUIT data = (N_EMP_QUIT) getDataObject();
				
		boolean ret = super.checkDataObject();		
		if (ret) {
			//使用者可在以下區域撰寫內容合理值的判斷程式碼
			//此單頭為readonly 直接return true
			InsuranceDAO dao = new InsuranceDAO();			
			String empsn = data.getEMPSN();
			String chuoiTB = dao.checkEmpsn(empsn);
			String soQDNV	="";		
			
			if (chuoiTB!=null) {
				setErrorMessage(chuoiTB);
				return false;
			}
			
			IGenericDAO<N_EMP_QUIT, N_EMP_QUITPk> empQuitDao = Application.getApp().getDao(N_EMP_QUIT.class);
			if (getProgram().getDataMode()==IProgram.DATAMODE_NEW){
				N_EMP_QUITPk empQuitPk = new N_EMP_QUITPk(data.getEMPSN(), data.getREAL_OFF_DATE());
				if (empQuitDao.findById(empQuitPk)!=null){
					setErrorMessage("Dữ liệu đã tồn tại trước đó rồi, nhập liệu không thành công.");
					return false;
				}
			}
			
			if (TO_DATE_DscDateField8.getSelectedDate()!=null){
				Calendar ngayBaoGiam	= Calendar.getInstance();
				ngayBaoGiam = TO_DATE_DscDateField8.getSelectedDate();
				// ngay bao giam bang to_date+1
				ngayBaoGiam.add(Calendar.DAY_OF_MONTH, 1);
				String statusBaoGiam ="0";			
				statusBaoGiam = dao.GetField("status", "n_baogiam_status", "to_char(ngay_bg,'"+"dd/mm/yyyy"+"')", "", "", ngayBaoGiam.toString(), "", "");
				if (statusBaoGiam=="1"){
					setErrorMessage("Đã xuất báo cáo trong đợt báo giảm này. Không thể cập nhật dữ liệu.");
					return false;				
				}
			}
			
			
			String[] check = checkData(data.getEMPSN(),data.getOFF_DATE(), data.getREAL_OFF_DATE(), data.getTHANG_TRUBH()
					,data.getDOT_TV(),data.getTHE_BHYT(),data.getDATE_BHYT()
					,data.getNOTE_GIAM_BHYT(),data.getFROM_DATE(),data.getTO_DATE()
					,data.getDATE_AGAIN(),data.getTHANG_TANGLAI(),data.getTYLE_TANGLAI());
			chuoiTB = check[0];
			soQDNV = check[1];
			if (cbQDNV.isSelected()){
				data.setSO_QDNV(soQDNV);
			}
			else{
				data.setSO_QDNV(null);
			}
			//Application.getApp().showMessageDialog(MessageBox.OK, soQDNV);
			
			if (chuoiTB!=null) {
				setErrorMessage(chuoiTB);
				return false;
			}			
		}						
		
		return ret;
	}
	
	private void ngayBaoGiamChanged() {
			
		// if la bao giam tuan thi chon T2 or T5
		if (sfLoaiBaoGiam.getSelectedIndex()==0)
		{			
			InsuranceDAO ins = new InsuranceDAO();
			String[] thoiGian = DateUtils.getThoiGianGiamByRealOffDate(REAL_OFF_DATE_DscDateField2.getSelectedDate().getTime());
			FROM_DATE_DscDateField7.setSelectedDate(ins.get_toCalendar(thoiGian[0]));
			TO_DATE_DscDateField8.setSelectedDate(ins.get_toCalendar(thoiGian[1]));			
		}
		else{
			FROM_DATE_DscDateField7.setSelectedDate(null);
			TO_DATE_DscDateField8.setSelectedDate(null);	
		}		
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
        getUIDataObjectBinder().registerCustomEditor(N_EMP_QUIT.class, "ID_QUIT_REASON", reasonEditor());
        getUIDataObjectBinder().registerCustomEditor(N_EMP_QUIT.class, "NOTE_GIAM_BHYT", getLoaiBaoGiamEditor());
        getUIDataObjectBinder().registerCustomEditor(N_EMP_QUIT.class, "THE_BHYT", getTraTheBHYTEditor());
        getUIDataObjectBinder().registerCustomEditor(N_EMP_QUIT.class, "TYLE_TANGLAI", getLoaiBaoTangEditor());        
        
        getUIDataObjectBinder().registerCustomEditor(N_EMP_QUIT.class, "OFF_DATE", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMP_QUIT.class, "REAL_OFF_DATE", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMP_QUIT.class, "DATE_HEN", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMP_QUIT.class, "DOT_TV", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMP_QUIT.class, "THANG_TRUBH", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMP_QUIT.class, "DATE_BHYT", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMP_QUIT.class, "FROM_DATE", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMP_QUIT.class, "TO_DATE", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMP_QUIT.class, "DATE_AGAIN", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMP_QUIT.class, "THANG_TANGLAI", PropertyEditors.createDateEditor("dd/MM/yyyy"));        
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DEPSN", DEPSN_DscField2, DEPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_QUIT_REASON", sfQuitReason, ID_QUIT_REASON_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField7, NOTE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("OFF_DATE", OFF_DATE_DscDateField1, OFF_DATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("REAL_OFF_DATE", REAL_OFF_DATE_DscDateField2, REAL_OFF_DATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_HEN", DATE_HEN_DscDateField3, DATE_HEN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("MM_DENBU", MM_DENBU_DscField8, MM_DENBU_CaptionLabel);
        getUIDataObjectBinder().addBindMap("SO_QDNV", SO_QDNV_DscField9, SO_QDNV_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DOT_TV", DOT_TV_DscDateField4, DOT_TV_CaptionLabel);
        getUIDataObjectBinder().addBindMap("THANG_TRUBH", THANG_TRUBH_DscDateField5, THANG_TRUBH_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_BHYT", DATE_BHYT_DscDateField6, DATE_BHYT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("THE_BHYT", sfTraTheBHYT, THE_BHYT_CaptionLabel);
        //getUIDataObjectBinder().addBindMap("THE_BHYT", THE_BHYT_DscField10, THE_BHYT_CaptionLabel);
        //getUIDataObjectBinder().addBindMap("NOTE_GIAM_BHYT", NOTE_GIAM_BHYT_DscField11, NOTE_GIAM_BHYT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE_GIAM_BHYT", sfLoaiBaoGiam, NOTE_GIAM_BHYT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("FROM_DATE", FROM_DATE_DscDateField7, FROM_DATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TO_DATE", TO_DATE_DscDateField8, TO_DATE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_AGAIN", DATE_AGAIN_DscDateField9, DATE_AGAIN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("THANG_TANGLAI", THANG_TANGLAI_DscDateField10, THANG_TANGLAI_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TYLE_TANGLAI", sfLoaiBaoTang, TYLE_TANGLAI_CaptionLabel);
        getUIDataObjectBinder().addBindMap("MONTH_GIAMBH", MONTH_GIAMBH_DscField13, MONTH_GIAMBH_CaptionLabel);
        getUIDataObjectBinder().addBindMap("GIAM_BHYT", GIAM_BHYT_DscField14, GIAM_BHYT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE_BH", NOTE_BH_DscField15, NOTE_BH_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	//
	public MappingPropertyEditor reasonEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_QUIT_REASON, String> dao = Application.getApp().getDao(N_QUIT_REASON.class);
		List<N_QUIT_REASON> list = dao.findAll(1000);
		for (N_QUIT_REASON r:list){
			e.put(Vni2Uni.convertToUnicode(r.getNAME_QR()), r.getID_QUIT_REASON());			
		}
		return e;
	}
	
	private MappingPropertyEditor getLoaiBaoGiamEditor(){
		MappingPropertyEditor editor = new MappingPropertyEditor();
		//edito.put(textDisplay,textSave)				
		editor.put("GIAM TU NGAY DEN NGAY", "1");
		editor.put("GIAM TANG MOI", "GIAM TANG MOI");
		editor.put("CHUA BAO GIAM", "0");
		// vua bao gom giam mac dinh theo ngay nghi viec va bao giam ko theo ngay nghi vic
		// user co the tu thao tac ngay bao giam o day		
		return editor;
	}	
	private MappingPropertyEditor getLoaiBaoTangEditor(){
		MappingPropertyEditor editor = new MappingPropertyEditor();
		//edito.put(textDisplay,textSave)
		editor.put("", null);
		editor.put("1", "1");
		editor.put("20", "20");
		return editor;
	}	
	
	private MappingPropertyEditor getTraTheBHYTEditor(){
		MappingPropertyEditor editor = new MappingPropertyEditor();		
		editor.put("CO", "1");
		editor.put("KHONG", "0");		
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
		N_EMP_QUIT data = (N_EMP_QUIT) getDataObject();
		//<如果要預先取號則在此加入>
		//autoPrimaryKeyValue();
		//使用者可在以下區域填入新增時內定的預設數值
		//<內定值>
		Calendar cal = Calendar.getInstance();
		data.setTHE_BHYT("0");// ko tra the
		data.setNOTE_GIAM_BHYT("");// giam tu ngay den ngay
		data.setREAL_OFF_DATE(cal.getTime());
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {
		N_EMP_QUIT data = (N_EMP_QUIT) getDataObject();
		String depsn = data.getDEPSN();
		String nameDept = data.getDEPT();				
		// if la them moi thi luu depsn theo N_EMPLOYEE, edit thi ko can
		// vi neu NViec 24/10 TB006, 25/10 vao sua luc nay depsn tren N_EMPLOYEE da la 00000 roi--> update tu TB006->00000-> sai 
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
			IGenericDAO<N_EMPLOYEE, String> objDao = Application.getApp().getDao(N_EMPLOYEE.class);
			N_EMPLOYEE  objData = objDao.findById(data.getEMPSN());		
			depsn = objData.getDEPSN();
			data.setDEPSN(depsn);				
			IGenericDAO<N_DEPARTMENT, String> objDao1 = Application.getApp().getDao(N_DEPARTMENT.class);
			N_DEPARTMENT  objData1 = objDao1.findById(depsn);			
			nameDept= objData1.getNAME_DEPT();
			data.setDEPT(nameDept);
		}	
		N_EMP_QUITDAO empQuit = new N_EMP_QUITDAO();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		
		// se auto dua du lieu vao n_lost_data_detail nua vi ngay nghi viec la ngay cuoi cung den cty ke tu 01/05/2012
		// thong nhat LT1
 		empQuit.InsertLostDataDetailAuto(depsn, sf.format(data.getREAL_OFF_DATE()).toString(),data.getID_QUIT_REASON(), data.getEMPSN());		
		// update depsn trong n_employee if ngay thuc nghi<= ngay hien hanh
		Date dateNow	= new Date(); 	
		
		if ((data.getREAL_OFF_DATE().compareTo(dateNow))<=0){
			empQuit.capNhatDuLieuLienQuan(data.getEMPSN(), "00000", "0", sf.format(data.getREAL_OFF_DATE()));
		}
/*		
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				autoPrimaryKeyValue();		
		}	
*/		
			//使用者可在以下區域做儲存資料物件至DataObjectSet之前處理
			//<儲存前處理>	
				
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		rootLayout = new Grid();
		rootLayout.setSize(4);
		add(rootLayout);
		EMPSN_CaptionLabel = new Label();
		EMPSN_CaptionLabel.setText("N_EMP_QUIT.EMPSN");
		rootLayout.add(EMPSN_CaptionLabel);
		EMPSN_DscField1 = new DscField();
		EMPSN_DscField1.setId("EMPSN_DscField1");
		rootLayout.add(EMPSN_DscField1);
		
		DEPSN_CaptionLabel = new Label();
		DEPSN_CaptionLabel.setText("N_EMP_QUIT.DEPSN");
		rootLayout.add(DEPSN_CaptionLabel);
		DEPSN_DscField2 = new DscField();
		DEPSN_DscField2.setId("DEPSN_DscField2");
		rootLayout.add(DEPSN_DscField2);

		FNAME_Label1 = new Label();
		FNAME_Label1.setText("Ho va Ten");
		rootLayout.add(FNAME_Label1);
		
		FNAME_Label = new Label();
		FNAME_Label.setText("Ho va Ten");
		rootLayout.add(FNAME_Label);
		
		
		
		ID_QUIT_REASON_CaptionLabel = new Label();
		ID_QUIT_REASON_CaptionLabel.setText("N_EMP_QUIT.ID_QUIT_REASON");
		rootLayout.add(ID_QUIT_REASON_CaptionLabel);
		
		sfQuitReason = new SelectField();
		rootLayout.add(sfQuitReason);
		
		OFF_DATE_CaptionLabel = new Label();
		OFF_DATE_CaptionLabel.setText("N_EMP_QUIT.OFF_DATE");
		rootLayout.add(OFF_DATE_CaptionLabel);
		
		OFF_DATE_DscDateField1 = new DscDateField();
		OFF_DATE_DscDateField1.setId("OFF_DATE_DscDateField1");
		OFF_DATE_DscDateField1.getDateChooser().setLocale(Locale.ENGLISH);
		OFF_DATE_DscDateField1.setDateFormat(sdf);
		
		rootLayout.add(OFF_DATE_DscDateField1);
		REAL_OFF_DATE_CaptionLabel = new Label();
		REAL_OFF_DATE_CaptionLabel.setText("N_EMP_QUIT.REAL_OFF_DATE");
		rootLayout.add(REAL_OFF_DATE_CaptionLabel);
		
		REAL_OFF_DATE_DscDateField2 = new DscDateField();
		REAL_OFF_DATE_DscDateField2.setId("REAL_OFF_DATE_DscDateField2");
		REAL_OFF_DATE_DscDateField2.getDateChooser().setLocale(Locale.ENGLISH);
		REAL_OFF_DATE_DscDateField2.setDateFormat(sdf);		
		REAL_OFF_DATE_DscDateField2.getModel().addListener(new CalendarSelectionListener() {

			public void displayedDateChange(CalendarEvent arg0) {
			}

			public void selectedDateChange(CalendarEvent arg0) {
				ngayBaoGiamChanged();
			}

		});
		rootLayout.add(REAL_OFF_DATE_DscDateField2);

		NOTE_CaptionLabel = new Label();
		NOTE_CaptionLabel.setText("N_EMP_QUIT.NOTE");
		rootLayout.add(NOTE_CaptionLabel);
		NOTE_DscField7 = new DscField();
		NOTE_DscField7.setId("NOTE_DscField7");
		rootLayout.add(NOTE_DscField7);
		
		DATE_HEN_CaptionLabel = new Label();
		DATE_HEN_CaptionLabel.setText("N_EMP_QUIT.DATE_HEN");
		rootLayout.add(DATE_HEN_CaptionLabel);		
		
		DATE_HEN_DscDateField3 = new DscDateField();
		DATE_HEN_DscDateField3.setId("DATE_HEN_DscDateField3");
		DATE_HEN_DscDateField3.getDateChooser().setLocale(Locale.ENGLISH);
		DATE_HEN_DscDateField3.setDateFormat(sdf);
		rootLayout.add(DATE_HEN_DscDateField3);
		
		MM_DENBU_CaptionLabel = new Label();
		MM_DENBU_CaptionLabel.setText("N_EMP_QUIT.M_DENBU");
		rootLayout.add(MM_DENBU_CaptionLabel);
		MM_DENBU_DscField8 = new DscField();
		MM_DENBU_DscField8.setId("MM_DENBU_DscField8");
		rootLayout.add(MM_DENBU_DscField8);
		SO_QDNV_CaptionLabel = new Label();
		SO_QDNV_CaptionLabel.setText("N_EMP_QUIT.SO_QDNV");
		rootLayout.add(SO_QDNV_CaptionLabel);
		SO_QDNV_DscField9 = new DscField();
		SO_QDNV_DscField9.setId("SO_QDNV_DscField9");
		rootLayout.add(SO_QDNV_DscField9);
		DOT_TV_CaptionLabel = new Label();
		DOT_TV_CaptionLabel.setText("N_EMP_QUIT.DOT_TV");
		rootLayout.add(DOT_TV_CaptionLabel);
		
		DOT_TV_DscDateField4 = new DscDateField();
		DOT_TV_DscDateField4.setId("DOT_TV_DscDateField4");
		DOT_TV_DscDateField4.getDateChooser().setLocale(Locale.ENGLISH);
		DOT_TV_DscDateField4.setDateFormat(sdf);
		rootLayout.add(DOT_TV_DscDateField4);
		
		THANG_TRUBH_CaptionLabel = new Label();
		THANG_TRUBH_CaptionLabel.setText("N_EMP_QUIT.THANG_TRUBH");
		rootLayout.add(THANG_TRUBH_CaptionLabel);
		
		THANG_TRUBH_DscDateField5 = new DscDateField();
		THANG_TRUBH_DscDateField5.setId("THANG_TRUBH_DscDateField5");
		THANG_TRUBH_DscDateField5.getDateChooser().setLocale(Locale.ENGLISH);
		THANG_TRUBH_DscDateField5.setDateFormat(sdf);
		rootLayout.add(THANG_TRUBH_DscDateField5);
		
		THE_BHYT_CaptionLabel = new Label();
		THE_BHYT_CaptionLabel.setText("N_EMP_QUIT.THE_BHYT");
		rootLayout.add(THE_BHYT_CaptionLabel);
		
		sfTraTheBHYT = new SelectField();
		rootLayout.add(sfTraTheBHYT);
		
		DATE_BHYT_CaptionLabel = new Label();
		DATE_BHYT_CaptionLabel.setText("N_EMP_QUIT.DATE_BHYT");
		rootLayout.add(DATE_BHYT_CaptionLabel);
		
		DATE_BHYT_DscDateField6 = new DscDateField();
		DATE_BHYT_DscDateField6.setId("DATE_BHYT_DscDateField6");
		DATE_BHYT_DscDateField6.getDateChooser().setLocale(Locale.ENGLISH);
		DATE_BHYT_DscDateField6.setDateFormat(sdf);
		rootLayout.add(DATE_BHYT_DscDateField6);		

		NOTE_GIAM_BHYT_CaptionLabel = new Label();
		NOTE_GIAM_BHYT_CaptionLabel.setText("N_EMP_QUIT.NOTE_GIAM_BHYT");
		rootLayout.add(NOTE_GIAM_BHYT_CaptionLabel);
		
		sfLoaiBaoGiam = new SelectField();
		sfLoaiBaoGiam.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ngayBaoGiamChanged();
			}
		});
		rootLayout.add(sfLoaiBaoGiam);
		
		FROM_DATE_CaptionLabel = new Label();
		FROM_DATE_CaptionLabel.setText("N_EMP_QUIT.FROM_DATE");
		rootLayout.add(FROM_DATE_CaptionLabel);
		
		FROM_DATE_DscDateField7 = new DscDateField();
		FROM_DATE_DscDateField7.setId("FROM_DATE_DscDateField7");
		FROM_DATE_DscDateField7.getDateChooser().setLocale(Locale.ENGLISH);
		FROM_DATE_DscDateField7.setDateFormat(sdf);
		rootLayout.add(FROM_DATE_DscDateField7);
		
		TO_DATE_CaptionLabel = new Label();
		TO_DATE_CaptionLabel.setText("N_EMP_QUIT.TO_DATE");
		rootLayout.add(TO_DATE_CaptionLabel);		
		TO_DATE_DscDateField8 = new DscDateField();
		TO_DATE_DscDateField8.setId("TO_DATE_DscDateField8");
		TO_DATE_DscDateField8.getDateChooser().setLocale(Locale.ENGLISH);
		TO_DATE_DscDateField8.setDateFormat(sdf);
		rootLayout.add(TO_DATE_DscDateField8);
		
		DATE_AGAIN_CaptionLabel = new Label();
		DATE_AGAIN_CaptionLabel.setText("N_EMP_QUIT.DATE_AGAIN");
		rootLayout.add(DATE_AGAIN_CaptionLabel);
		DATE_AGAIN_DscDateField9 = new DscDateField();
		DATE_AGAIN_DscDateField9.setId("DATE_AGAIN_DscDateField9");
		DATE_AGAIN_DscDateField9.getDateChooser().setLocale(Locale.ENGLISH);
		DATE_AGAIN_DscDateField9.setDateFormat(sdf);
		rootLayout.add(DATE_AGAIN_DscDateField9);
		
		THANG_TANGLAI_CaptionLabel = new Label();
		THANG_TANGLAI_CaptionLabel.setText("N_EMP_QUIT.THANG_TANGLAI");
		rootLayout.add(THANG_TANGLAI_CaptionLabel);
		THANG_TANGLAI_DscDateField10 = new DscDateField();
		THANG_TANGLAI_DscDateField10.setId("THANG_TANGLAI_DscDateField10");
		THANG_TANGLAI_DscDateField10.setDateFormat(sdf);
		THANG_TANGLAI_DscDateField10.getDateChooser().setLocale(Locale.ENGLISH);
		rootLayout.add(THANG_TANGLAI_DscDateField10);
		
		TYLE_TANGLAI_CaptionLabel = new Label();
		TYLE_TANGLAI_CaptionLabel.setText("N_EMP_QUIT.TYLE_TANGLAI");
		rootLayout.add(TYLE_TANGLAI_CaptionLabel);
		
/*		TYLE_TANGLAI_DscField12 = new DscField();
		TYLE_TANGLAI_DscField12.setId("TYLE_TANGLAI_DscField12");
		rootLayout.add(TYLE_TANGLAI_DscField12);
*/		
		
		sfLoaiBaoTang = new SelectField();
		rootLayout.add(sfLoaiBaoTang);
		
		MONTH_GIAMBH_CaptionLabel = new Label();
		MONTH_GIAMBH_CaptionLabel.setText("N_EMP_QUIT.MONTH_GIAMBH");
		rootLayout.add(MONTH_GIAMBH_CaptionLabel);
		MONTH_GIAMBH_DscField13 = new DscField();
		MONTH_GIAMBH_DscField13.setId("MONTH_GIAMBH_DscField13");
		rootLayout.add(MONTH_GIAMBH_DscField13);
		GIAM_BHYT_CaptionLabel = new Label();
		GIAM_BHYT_CaptionLabel.setText("N_EMP_QUIT.GIAM_BHYT");
		rootLayout.add(GIAM_BHYT_CaptionLabel);
		GIAM_BHYT_DscField14 = new DscField();
		GIAM_BHYT_DscField14.setId("GIAM_BHYT_DscField14");
		rootLayout.add(GIAM_BHYT_DscField14);
		NOTE_BH_CaptionLabel = new Label();
		NOTE_BH_CaptionLabel.setText("N_EMP_QUIT.NOTE_BH");
		rootLayout.add(NOTE_BH_CaptionLabel);
		NOTE_BH_DscField15 = new DscField();
		NOTE_BH_DscField15.setId("NOTE_BH_DscField15");
		rootLayout.add(NOTE_BH_DscField15);
	}

}
