package ds.program.fvhr.ngan.ui.Rest;

import java.beans.PropertyEditor;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.jfree.data.time.Minute;

import com.sun.star.geometry.RealRectangle2D;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.TextArea;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.list.ListModel;
import ds.program.fvhr.baby.tools.CheckRule;
import ds.program.fvhr.baby.tools.DateTools;
import ds.program.fvhr.baby.tools.babyMath;
import ds.program.fvhr.domain.N_CHDEPSN;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import fv.util.Vni2Uni;
import ds.program.fvhr.domain.N_EMP_BONUS;
import ds.program.fvhr.domain.N_JOB_DETAIL;
import ds.program.fvhr.domain.N_REGISTER_SHIFT;
import ds.program.fvhr.domain.N_REST;
import ds.program.fvhr.domain.N_REST_DETAIL;
import ds.program.fvhr.domain.N_REST_KIND;
import ds.program.fvhr.domain.N_SHIFT;
import ds.program.fvhr.domain.pk.N_REST_DETAILPk;
import ds.program.fvhr.son.ui.ObjUtility;
import ds.program.users.domain.DSPB02;
import dsc.dao.DataObjectSet;
import dsc.dao.DataRelation;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.program.DetailContent;
import dsc.echo2app.program.DetailToolbar;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.SubDetailContent;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import echopointng.DateField;
import echopointng.GroupBox;

public class N_Emp_Rest_01MDetailContent0 extends DetailContent {

	private nextapp.echo2.app.Grid rootLayout;
	private nextapp.echo2.app.Label EMPSN_CaptionLabel;
	private dsc.echo2app.component.DscField EMPSN_DscField1;
	private nextapp.echo2.app.Label DATE_OFF_CaptionLabel;
	private dsc.echo2app.component.DscDateField DATE_OFF_DscDateField1;
	private nextapp.echo2.app.Label TOTAL_CaptionLabel;
	private dsc.echo2app.component.DscField TOTAL_DscField2;
	private nextapp.echo2.app.Label REST_KIND_CaptionLabel;
	private SelectField REST_KIND_DscField3;
	private nextapp.echo2.app.Label REST_SAL_CaptionLabel;
	private dsc.echo2app.component.DscField REST_SAL_DscField4;
	private nextapp.echo2.app.Label REST_TYPE_CaptionLabel;
	private SelectField REST_TYPE_DscField5;
	private nextapp.echo2.app.Label TIN_CaptionLabel;
	private dsc.echo2app.component.DscField TIN_DscField6;
	private nextapp.echo2.app.Label TOUT_CaptionLabel;
	private dsc.echo2app.component.DscField TOUT_DscField7;
	private nextapp.echo2.app.Label MID_TIME_CaptionLabel;
	private dsc.echo2app.component.DscField MID_TIME_DscField8;
	private nextapp.echo2.app.Label NOTE_CaptionLabel;
	private dsc.echo2app.component.DscField NOTE_DscField9;
	private nextapp.echo2.app.Label NOTE1_CaptionLabel;
	private dsc.echo2app.component.DscField NOTE1_DscField10;
	private nextapp.echo2.app.Label LOCKED_CaptionLabel;
	private dsc.echo2app.component.DscField LOCKED_DscField11;
	private nextapp.echo2.app.Label YEAR_CaptionLabel;
	private dsc.echo2app.component.DscField YEAR_DscField1;
	/*
	 * private nextapp.echo2.app.Label HOUR_CaptionLabel; private
	 * dsc.echo2app.component.DscField HOUR_DscField2;
	 */
	private TextField txt_tu_gio;
	private TextField txt_den_gio;
	private SelectField sfGioNghiTrua;
	private SelectField sfGhiChu;
	private SelectField sfGhiChu1; // Ghi chu cho: Ngan ngay or dai ngay

	/*
	 * private GroupBox gb_ngan_dai_ngay; private ButtonGroup
	 * bt_group_ngan_dai_ngay; private RadioButton
	 * radio_ngan_ngay,radio_dai_ngay;
	 */

	RadioButton f1, f2, f3, f5, f6, khac;

	private TextField USER_UP_TextField;
	private Label USER_UP_CaptionLabel;
	private DateField DATE_UP_DateField;
	private Label DATE_UP_CaptionLabel;

	private TextArea NOTE_TIME_TextArea;
	private Label NOTE_TIME_Label;

	SimpleDateFormat _sf = new SimpleDateFormat(ObjUtility.DATE_FORMAT_STR);
	private DateField DATE_TO_DscDateField1;
	private Label DATE_TO_CaptionLabel;

	private boolean isExistTet = true;
	private boolean isExistCty = true;
	private boolean isExistCNV = true;
	private boolean isExistTon = true;

	public long numRestCNV = 0;
	public long numRestCty = 0;
	public long numRestTet = 0;
	public long numRestTon = 0;
	public List<N_REST_DETAIL> listSave;
	String user_up = Application.getApp().getLoginInfo().getUserID();
	SimpleDateFormat sf_ = new SimpleDateFormat(OBJ_UTILITY.DATE_FORMAT_STR);
	String ma_user = "";
	/**
	 * Creates a new <code>N_Emp_Rest_01MDetailContent0</code>.
	 */

	// baby support be' BAu`
	private MappingPropertyEditor map, mapRestType;
	private IGenericDAO<N_REST_DETAIL, String> daoRestDetail;
	private IGenericDAO<N_REGISTER_SHIFT, String> daoRegShift;
	private IGenericDAO<N_SHIFT, String> daoShift;
	private IGenericDAO<N_EMPLOYEE, String> daoEmployee;

	public N_Emp_Rest_01MDetailContent0() {
		super();
		// Add design-time configured components.
		initComponents();
	}

	/*
	 * (non-Javadoc) 建立資料關聯物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.DetailContent#createRelation()
	 */
	@Override
	public DataRelation createRelation() {
		return new DataRelation("EMPSN", "EMPSN");
	}

	@Override
	protected boolean doDataRefresh() {
		// TODO Auto-generated method stub
		return super.doDataRefresh();
	}

	private boolean checkRule(String empsn) {
		if (!CheckRule.checkRuleManager(empsn)) {
			setErrorMessage("bạn không được quyển thao tác trên số thẻ này !");
			return false;
		}
		if (!CheckRule.checkLockedMonth(empsn, DATE_OFF_DscDateField1
				.getSelectedDate().getTime())) {
			setErrorMessage("Đã khoá dữ liệu tháng ");
			return false;
		}

		if (DATE_OFF_DscDateField1.getSelectedDate().after(
				DATE_TO_DscDateField1.getSelectedDate())
				&& !DATE_TO_DscDateField1.getTextField().getText().trim()
						.equals("")) {
			setErrorMessage("Ngày bắt đầu phải lớn hơn ngày kết thúc ");
			return false;
		}
		
		if(Integer.valueOf(txt_tu_gio.getText()) > Integer.valueOf(txt_den_gio.getText()))
		{
			setErrorMessage("Giờ nghỉ bắt đầu phải lớn hơn Giờ nghỉ kết thúc ");
			return false;
		}

		if (REST_KIND_DscField3.getSelectedIndex() <= 0) {
			setErrorMessage("Bạn cần chọn lý do !");
			return false;
		} else {
			if (REST_KIND_DscField3.getSelectedItem().toString()
					.equals("PBENH")
					|| REST_KIND_DscField3.getSelectedItem().toString()
							.equals("PBAN")) {

				if (sfGhiChu1.getSelectedIndex() <= 0) {
					setErrorMessage("Nếu bạn chọn phép Bệnh Hoặc  Bận thì phải chọn Kiểu là  Ngắn hay Dài ngày");
					return false;
				}
			}
			if (REST_TYPE_DscField5.isEnabled()
					&& REST_TYPE_DscField5.getSelectedIndex() <= 0) {
				setErrorMessage("Bạn cần chọn loại phép !");
				return false;
			}
			if (sfGhiChu.isEnabled() && sfGhiChu.getSelectedIndex() <= 0) {
				setErrorMessage("Bạn cần chọn ghi chú !");
				return false;
			}
		}
		if ((!txt_tu_gio.getText().trim().equals("") && txt_den_gio.getText()
				.trim().equals(""))
				|| (!txt_den_gio.getText().trim().equals("") && txt_tu_gio
						.getText().trim().equals(""))) {
			if (sfGioNghiTrua.getSelectedIndex() <= 0) {
				setErrorMessage("Phải chọn giờ nghỉ trưa nếu như có giờ vào và giờ ra! ");
				return false;
			}
			setErrorMessage("Nhập đủ thông tin giờ vào và giờ ra,  nếu không thì bỏ trống cả 2 ");
			return false;
		}

		return true;
	}

	@Override
	public boolean checkDataObject() {
		N_REST_DETAIL data = (N_REST_DETAIL) getDataObject();
		boolean ret = super.checkDataObject();
		// check ngay chu nhat
		Calendar ca = Calendar.getInstance();
		if (ret) {
			ca.setTime(data.getDATE_OFF());
			// check the sunday
			if (ca.get(ca.DAY_OF_WEEK) == Calendar.SUNDAY) {
				setErrorMessage("Ngày chủ nhật đây rùi");
				return false;
			}

			// check exist rest PN
			if (!checkExistRestType(data))
				return false;
			if (!checkRule(data.getEMPSN()))
				return false;

		}

		return ret;
	}

	/*
	 * UI資料欄位屬性更新，一般是資料模式變換時會呼叫此method <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.DataContent#doUIRefresh()
	 */
	// @Override
	public void doUIRefresh() {

		if ((this.getDataMode() == IProgram.DATAMODE_NONE)
				|| (this.getDataMode() == IProgram.DATAMODE_BROWSER)) {
			rootLayout.setEnabled(false);
		} else {
			rootLayout.setEnabled(true);
			// ----------------------------------------------------------------------------------
			// 以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
			// 以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
			// ----------------------------------------------------------------------------------
			if (this.getDataMode() == IProgram.DATAMODE_EDIT) {
				DATE_TO_DscDateField1.setEnabled(false);
			} else {
				DATE_TO_DscDateField1.setEnabled(true);
			}
		}

		// 7.<資料權限管控>
		N_REST_DETAIL data = (N_REST_DETAIL) this.getBrowserContent()
				.getDataObjectSet().getDataObject();
		String userLog = Application.getApp().getLoginInfo().getUserID();

		/*
		 * if(data!=null && data.getUSER_UP()!=null &&
		 * data.getUSER_UP().equals(userLog)){
		 * getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE,
		 * true); getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT,
		 * true); }else{
		 * getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE,
		 * false); getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT,
		 * false); }
		 */// old

		getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, true);
		getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, false);
		getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, false);
		
		DATE_TO_DscDateField1.setTextField(new TextField());
		// getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, true);
		// getDetailToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, false);
		super.doUIRefresh();

		// 自動設定元件風格
		getUIDataObjectBinder().doDataBindUIStyle();
	}

	/*
	 * 取得該畫面的DataObject Class <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.DataContent#getDataObjectClass()
	 */
	public Class getDataObjectClass() {
		return N_REST_DETAIL.class;
	}

	/*
	 * 資料儲存前設定一些欄位內定值 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */
	@Override
	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		// 使用者可在以下區域填入控制欄位數值
		// N_REST_DETAIL data = (N_REST_DETAIL) getDataObject();
	}

	@Override
	protected boolean saveToDataObjectSet() {
		String a = REST_KIND_DscField3.getSelectedItem().toString();
		map.setAsText(a);
		String temp = ((N_REST_KIND) map.getValue()).getID_REST_SAL();
		String rest_sal = temp.equals("C01") ? "REST_PAY"
				: temp.equals("C02") ? "REST"
						: temp.equals("C03") ? "REST_SICK"
								: temp.equals("C04") ? "OTHER" : temp
										.equals("C05") ? "NWHOUR" : temp
										.equals("C07") ? "LMATER" : temp
										.equals("C00") ? "NBU" : temp
										.equals("C06") ? "CNX" : "";
		((N_REST_DETAIL) getDataObject()).setREST_SAL(rest_sal);

		if (!DATE_TO_DscDateField1.getTextField().getText().trim().equals("")) {
			Calendar cal = DATE_TO_DscDateField1.getSelectedDate();
			DATE_TO_DscDateField1.setSelectedDate(cal);
		}

		return super.saveToDataObjectSet();
	}

	@Override
	protected int doInit() {
		int nRet = super.doInit();
		daoRestDetail = Application.getApp().getDao(N_REST_DETAIL.class);
		daoRegShift = Application.getApp().getDao(N_REGISTER_SHIFT.class);
		daoEmployee = Application.getApp().getDao(N_EMPLOYEE.class);
		daoShift = Application.getApp().getDao(N_SHIFT.class);
		registPropertyEditor();
		bindUI();

		return nRet;
	}

	private void registPropertyEditor() {
		this.getUIDataObjectBinder().registerCustomEditor(N_REST_DETAIL.class,
				"DATE_OFF", PropertyEditors.createDateEditor("dd/MM/yyyy"));
		this.getUIDataObjectBinder().registerCustomEditor(N_REST_DETAIL.class,
				"REST_KIND", this.getEditorRestKind());
		this.getUIDataObjectBinder().registerCustomEditor(N_REST_DETAIL.class,
				"REST_TYPE", this.getEditorRestType());
		this.getUIDataObjectBinder().registerCustomEditor(N_REST_DETAIL.class,
				"MID_TIME", this.gioNghiTruaEditor());
		this.getUIDataObjectBinder().registerCustomEditor(N_REST_DETAIL.class,
				"NOTE", this.GhiChuEditor());
		this.getUIDataObjectBinder().registerCustomEditor(N_REST_DETAIL.class,
				"NOTE1", this.GhiChu1Editor());
		map = bindRest_Sal();
		mapRestType = getEditorRestType();
	}

	private MappingPropertyEditor bindRest_Sal() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_REST_KIND, String> objDao = Application.getApp().getDao(
				N_REST_KIND.class);
		List<N_REST_KIND> listData = objDao.findAll(100);
		for (N_REST_KIND data : listData) {
			editor.put(Vni2Uni.convertToUnicode(data.getNAME_REST()), data);
		}
		return editor;

	}

	private MappingPropertyEditor getEditorRestType() {
		// kiem tra so phep con lai, neu con thi cho hien, nguoc lai thi loai ra
		countRestType();

		MappingPropertyEditor editor = new MappingPropertyEditor();
		editor.put("", "");
		editor.put("Công ty", "CTY");
		editor.put("Công nhân viên", "CNV");
		editor.put("Phép tồn", "TON");
		editor.put("Phép tết", "TET");

		return editor;
	}

	private PropertyEditor getEditorRestKind() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_REST_KIND, String> objDao = Application.getApp().getDao(
				N_REST_KIND.class);
		List<N_REST_KIND> listData = objDao.findAll(100);
		editor.put("", "");
		for (N_REST_KIND data : listData) {
			editor.put(Vni2Uni.convertToUnicode(data.getNAME_REST()),
					data.getNAME_REST());
		}
		return editor;
	}

	public boolean Kt_DS_NS(String sothe, Date date_)// Neu la Duong Suc hoac
														// NSan thi khong cho
														// xoa or update
	{ // Kiem tra 2 loai phep nay trong N_REST_DETAIL voi ngay nhap phep
		IGenericDAO<N_REST_DETAIL, N_REST_DETAILPk> Dao_Rest = Application
				.getApp().getDao(N_REST_DETAIL.class);
		N_REST_DETAILPk pk = new N_REST_DETAILPk(sothe, date_);
		N_REST_DETAIL Data_Rest = Dao_Rest.findById(pk);

		if (Data_Rest != null
				&& ((Data_Rest.getREST_KIND().equals("DS") && Data_Rest
						.getTOTAL() > 0) || Data_Rest.getREST_KIND().equals(
						"NS"))) {
			OBJ_UTILITY.ShowMessageError(Data_Rest.getREST_KIND()
					+ " không thể xóa");
			return false;
		}

		return true;
	}

	public boolean doDelete() {

		f1 = new RadioButton();
		f2 = new RadioButton();
		f3 = new RadioButton();
		f5 = new RadioButton();
		f6 = new RadioButton();
		khac = new RadioButton();
		f1.setSelected(false);
		f2.setSelected(false);
		f3.setSelected(false);
		f5.setSelected(false);
		f6.setSelected(false);
		khac.setSelected(false);

		OBJ_UTILITY obj_util = new OBJ_UTILITY();
		OBJ_EMPSN obj_e = new OBJ_EMPSN();
		if (!obj_e.check_lock(user_up)) {
			return false;
		} else {

			String empsn = EMPSN_DscField1.getText();
			N_REST_DETAIL data = (N_REST_DETAIL) this.getBrowserContent()
					.getDataObjectSet().getDataObject();
			if (data != null) {
				Date date_ = data.getDATE_OFF();
				String date_str = sf_.format(date_);
				if (!obj_e.check_lock_month(empsn, "", "", "", "", date_,
						"DEPSN", ma_user, f1, f2, f3, f5, f6, khac)) {
					return false;
				} else if (!Kt_DS_NS(empsn, date_)) {
					return false;
				} else {
					String sql = "DELETE FROM N_REST_DETAIL T\n"
							+ " WHERE T.EMPSN = '" + empsn + "'"
							+ " AND T.DATE_OFF = TO_DATE('" + date_str
							+ "','dd/MM/yyyy')";

					obj_util.Exe_Update_Sql(sql);
					String strnote = "Xoa ngay phep: " + date_str
							+ ",Loai phep: " + data.getREST_KIND()
							+ ", Loai PN: " + data.getREST_TYPE()
							+ ", So ngay phep: " + data.getTOTAL()
							+ ", Nghi tu gio: " + data.getTIN() + " den gio: "
							+ data.getTOUT();
					obj_e.Insert_N_Action_Daily(ma_user, "N_REST_DETAIL",
							"DELETE", empsn, strnote);

					if (data.getREST_KIND().equals("PN")) {
						obj_e.PN_CONLAI(empsn, date_);
					}
				}
			}
		}
		OBJ_UTILITY.ShowMessageOK("Xóa thành công");
		return super.doDelete();// delete

	}

	private void bindUI() {
		// ----------------------------------------------------------------------------------
		// 以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
		getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1,
				EMPSN_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DATE_OFF", DATE_OFF_DscDateField1,
				DATE_OFF_CaptionLabel);
		getUIDataObjectBinder().addBindMap("TOTAL", TOTAL_DscField2,
				TOTAL_CaptionLabel);
		getUIDataObjectBinder().addBindMap("REST_KIND", REST_KIND_DscField3,
				REST_KIND_CaptionLabel);
		getUIDataObjectBinder().addBindMap("REST_SAL", REST_SAL_DscField4,
				REST_SAL_CaptionLabel);
		getUIDataObjectBinder().addBindMap("REST_TYPE", REST_TYPE_DscField5,
				REST_TYPE_CaptionLabel);
		getUIDataObjectBinder().addBindMap("TIN", txt_tu_gio, TIN_CaptionLabel);
		getUIDataObjectBinder().addBindMap("TOUT", txt_den_gio,
				TOUT_CaptionLabel);
		getUIDataObjectBinder().addBindMap("MID_TIME", sfGioNghiTrua,
				MID_TIME_CaptionLabel);
		// getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField9,
		// NOTE_CaptionLabel);
		getUIDataObjectBinder().addBindMap("NOTE", sfGhiChu, NOTE_CaptionLabel);
		getUIDataObjectBinder().addBindMap("NOTE1", sfGhiChu1,
				NOTE1_CaptionLabel);
		getUIDataObjectBinder().addBindMap("LOCKED", LOCKED_DscField11,
				LOCKED_CaptionLabel);
		getUIDataObjectBinder().addBindMap("YEAR", YEAR_DscField1,
				YEAR_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DATE_UP", DATE_UP_DateField,
				DATE_UP_CaptionLabel);
		getUIDataObjectBinder().addBindMap("USER_UP", USER_UP_TextField,
				USER_UP_CaptionLabel);
		// getUIDataObjectBinder().addBindMap("NOTE_TIME", NOTE_TIME_TextArea,
		// NOTE_TIME_Label);
		// getUIDataObjectBinder().addBindMap("HOUR", HOUR_DscField2,
		// HOUR_CaptionLabel);
		// 以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
		// ----------------------------------------------------------------------------------
	}

	private MappingPropertyEditor gioNghiTruaEditor() {
		MappingPropertyEditor e = new MappingPropertyEditor();
		e.put("", null);
		e.put("10:45");
		e.put("11:00");
		e.put("11:30");
		e.put("11:45");
		e.put("12:00");
		e.put("12:15");
		return e;
	}

	private MappingPropertyEditor GhiChuEditor() {
		MappingPropertyEditor e = new MappingPropertyEditor();
		e.put("", null);
		e.put("PBAN");
		e.put("CON_OM_75%");
		e.put("NV_BENH");
		e.put("NV_BENH_75%");
		e.put("KHAM_THAI_100%");
		e.put("SAY_THAI_100%");
		e.put("KE_HH_100%");
		return e;
	}

	private MappingPropertyEditor GhiChu1Editor() {
		MappingPropertyEditor e = new MappingPropertyEditor();
		e.put("", null);
		e.put("NGAN_NGAY");
		e.put("DAI_NGAY");
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
		this.getBrowserContent().setPageSize(30);
		this.getMasterDataContent().setEnabled(true);

		IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(
				DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();

		REST_KIND_DscField3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sfGhiChu.setEnabled(true);
				REST_TYPE_DscField5.setEnabled(true);
				if (REST_KIND_DscField3.getSelectedIndex() == 1) {
					REST_TYPE_DscField5.setEnabled(true);
					REST_TYPE_DscField5.setSelectedIndex(1);
					sfGhiChu.setEnabled(false);
				} else if (REST_KIND_DscField3.getSelectedIndex() == 7) {
					REST_TYPE_DscField5.setSelectedIndex(0);
					REST_TYPE_DscField5.setEnabled(false);
					sfGhiChu.setEnabled(true);
					sfGhiChu.setModel(getModelGhiChuPBenh());
					sfGhiChu.setSelectedIndex(0);
				} else if (REST_KIND_DscField3.getSelectedIndex() == 8) {
					REST_TYPE_DscField5.setSelectedIndex(0);
					REST_TYPE_DscField5.setEnabled(false);
					sfGhiChu.setEnabled(true);
					sfGhiChu.setModel(getModelGhiChuPBan());
					sfGhiChu.setSelectedIndex(0);
				} else {
					REST_TYPE_DscField5.setSelectedIndex(0);
					REST_TYPE_DscField5.setEnabled(false);
					sfGhiChu.setModel(getModelGhiChuPBan());
					sfGhiChu.setSelectedIndex(0);
					sfGhiChu.setEnabled(false);

				}
			}
		});

	}

	@Override
	public String getQueryString() {
		return "o.EMPSN=? and to_char(o.DATE_OFF,'yyyy')=?";
	}

	@Override
	public Object[] getQueryParams() {
		N_REST master = (N_REST) getMasterDataContent().getDataObject();
		if (master == null)
			return new Object[] { "", "" };
		return new Object[] { master.getEMPSN(), master.getYEAR() };
	}

	@Override
	public boolean doSave() {
		saveUIToDataObject();
		if (!this.checkDataObject()) {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					this.getErrorMessage());
			return false;
		}
		beforeSaveToDataObjectSet();
		if (!saveToDataObjectSet()) {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					this.getErrorMessage());
			return false;
		}
		Rest_utility rest_util = new Rest_utility();
		ArrayList<Date> list_date_off = new ArrayList<Date>();
		Date dateFrom = DATE_OFF_DscDateField1.getSelectedDate().getTime();
		Date dateTo = DATE_TO_DscDateField1.getSelectedDate().getTime();
		N_REST obj = (N_REST) getMasterDataContent().getDataObject();
		String kt_ngay_CN = "";
		list_date_off = rest_util.getListDateOff(dateFrom, dateTo,
				obj.getEMPSN(), kt_ngay_CN);
		N_REST_DETAIL data = ((N_REST_DETAIL) getDataObject());
		N_REGISTER_SHIFT regShift = daoRegShift
				.findUnique(
						"select t from N_REGISTER_SHIFT t where t.EMPSN = ? and t.SHIFT_DATE = ?",
						data.getEMPSN(), data.getDATE_OFF());
		N_EMPLOYEE emp = daoEmployee.findById(data.getEMPSN());
		String ID_shift = regShift == null ? emp.getSHIFT() : regShift
				.getID_SHIFT();

		double numRest = 1;
		if ((!txt_den_gio.getText().equals("") && !txt_tu_gio.getText().equals(
				""))
				&& sfGioNghiTrua.getSelectedIndex() >= 0) {
			numRest = calculateTime(data.getDATE_OFF(), ID_shift,
					data.getTIN(), data.getTOUT(), sfGioNghiTrua
							.getSelectedItem().toString());
		}

		data.setTOTAL((float) numRest);

		for (Date date : list_date_off) {
			if (!checkRest_NS_DS(data.getEMPSN(), data.getDATE_OFF()))
				daoRestDetail.saveOrUpdate(data);
		}
		saveOK();
		return true;
	}

	private double calculateTime(Date date, String shift, String timeIn,
			String timOut, String half_Time) {

		N_SHIFT obj = daoShift.findById(shift);

		double work_time_begin = 0; // thời gian làm việc buổi sáng
		double work_time_end = 0; // thời gian bắt đầu làm việc vào
									// buổi chiều

		double time_rest_begin = 0; // thời gian xin phép nghỉ
		double time_rest_end = 0; // thời gian vô làm lại
		double half_time_begin = 0; // thời gian bắt đầu nghỉ trưa
		double half_time_end = 0; // thời gian kết thúc nghỉ trưa

		double real_work_time = 0; // thời gian làm việc của ngày
		double real_rest_time = 0; // thời gian nghỉ thực

		work_time_begin = Integer.valueOf(obj.getTIME_IN().substring(0, 2))
				* 60 + Integer.valueOf(obj.getTIME_IN().substring(3, 5));
		if(!obj.getID_SHIFT().equals(new String[]{"DB7","C32","C31","C35","DB5","CA3","DB6"}))
		{
		work_time_end = Integer.valueOf(obj.getTIME_OUT().substring(0, 2)) * 60
				+ Integer.valueOf(obj.getTIME_OUT().substring(3, 5))+(24*60);
		
		}
		else
		{
		work_time_end = Integer.valueOf(obj.getTIME_OUT().substring(0, 2)) * 60
		+ Integer.valueOf(obj.getTIME_OUT().substring(3, 5));
		half_time_begin = Integer.valueOf(half_Time.substring(0, 2)) * 60
		+ Integer.valueOf(half_Time.substring(3, 5));
		half_time_end = half_time_begin + 60;
		time_rest_begin = Integer.valueOf(timeIn.substring(0, 2)) * 60
		+ Integer.valueOf(timeIn.substring(2, 4));
		}
	
		time_rest_end = Integer.valueOf(timOut.substring(0, 2)) * 60
				+ Integer.valueOf(timOut.substring(2, 4));
		real_work_time = work_time_end - work_time_begin - 60;

		if (time_rest_begin > work_time_begin
				|| time_rest_begin < work_time_end
				|| time_rest_end > work_time_begin
				|| time_rest_end < work_time_end) {
			
			if ((time_rest_begin <= half_time_begin && time_rest_end <= half_time_begin)
					|| (time_rest_begin >= half_time_end && time_rest_end >= half_time_end))
				real_rest_time = time_rest_end = time_rest_begin;
			else  {
					time_rest_begin = half_time_begin - time_rest_begin <=0?0:half_time_begin - time_rest_begin ;
					time_rest_end = time_rest_end - half_time_end<=0?0:time_rest_end - half_time_end;
				real_rest_time = time_rest_begin + time_rest_end;
			}

			real_rest_time = real_rest_time/real_work_time;
			real_rest_time = babyMath.Round(real_rest_time, 1);
		}
		return real_rest_time;
	}

	private boolean checkExistRestType(N_REST_DETAIL data) {
		if (data.getREST_KIND().equals("PN")) {
			if (data.getREST_TYPE() == null || data.getREST_TYPE().equals("")) {
				setErrorMessage("Chưa chọn loại phép năm");
				return false;
			} else if (data.getREST_TYPE().equals("TET") && isExistTet == false) {
				setErrorMessage("Đã sử dụng hết phép nghỉ Tết");
				return false;
			} else if (data.getREST_TYPE().equals("TON") && isExistTon == false) {
				setErrorMessage("Đã sử dụng hết phép Tồn");
				return false;
			} else if (data.getREST_TYPE().equals("CTY") && isExistCty == false) {
				setErrorMessage("Đã sử dụng hết phép Công Ty");
				return false;
			} else if (data.getREST_TYPE().equals("CNV") && isExistCNV == false) {
				setErrorMessage("Đã sử dụng hết phép CNV tự xin");
				return false;
			}
		}
		return true;
	}

	private boolean checkRest_NS_DS(String empsn, Date date) {
		N_REST_DETAIL obj = daoRestDetail
				.findUnique(
						"select t from N_REST_DETAIL t where t.EMPSN = ? and t.DATE_OFF = ?",
						empsn, date);
		if (obj == null)
			return false;
		if (obj.getREST_KIND().equals("DS") || obj.getREST_KIND().equals("NS")) {
			return true;
		}
		return false;
	}

	public void countRestType() {
		numRestCNV = 0;
		numRestCty = 0;
		numRestTet = 0;
		numRestTon = 0;

		N_REST dataMaster = (N_REST) getMasterDataContent().getDataObjectSet()
				.getDataObject();
		if (dataMaster == null)
			return;
		DataObjectSet dataSet = this.getBrowserContent().getDataObjectSet();

		for (int i = 0; i < dataSet.getRecordCount(); i++) {
			N_REST_DETAIL data = (N_REST_DETAIL) dataSet.getDataObject(i);
			if (data.getREST_KIND() != null && data.getREST_KIND().equals("PN")) {

				if (data.getREST_TYPE().equals("TET")) {
					numRestTet++;
					continue;
				}
				if (data.getREST_TYPE().equals("CTY")) {
					numRestCty++;
					continue;
				}
				if (data.getREST_TYPE().equals("CNV")) {
					numRestCNV++;
					continue;
				}
				if (data.getREST_TYPE().equals("TON")) {
					numRestTon++;
					continue;
				}
			}
		}

		isExistCNV = true;
		isExistCty = true;
		isExistTet = true;
		isExistTon = true;

		if (dataMaster.getCO_ARR() <= numRestCty) {
			isExistCty = false;
		}
		if (dataMaster.getEMP_APP() <= numRestCNV) {
			isExistCNV = false;
		}
		if (dataMaster.getHOL_LUNAR() <= numRestTet) {
			isExistTet = false;
		}
		if (dataMaster.getSTORED() <= numRestTon) {
			isExistTon = false;
		}

	}

	/*
	 * 做資料新增時內定預設數值 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.DataContent#doNewDefaulData()
	 */
	@Override
	protected void doNewDefaulData() {
		N_REST_DETAIL data = (N_REST_DETAIL) getDataObject();
		// <如果要預先取號則在此加入>
		// autoSequenceKeyValue();
		// 使用者可在以下區域填入新增時內定的預設數值
		// <內定值>

		// <<填入與單頭關聯值>>
		N_REST pdata = (N_REST) getMasterDataContent().getDataObject();

		data.setHOUR(8L);
		data.setEMPSN(pdata.getEMPSN());
		data.setYEAR(pdata.getYEAR());
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {

		if (this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
			autoPrimaryKeyValue();
		}

	}

	protected final boolean autoPrimaryKeyValue() {
		Connection conn = Application.getApp().getConnection();
		boolean ret = autoPrimaryKeyValue(conn);
		Application.getApp().closeConnection(conn);
		return ret;
	}

	protected boolean autoPrimaryKeyValue(Connection conn) {
		return true;
	}

	public void doDrillDown() {
	}

	@Override
	protected String[] getBrowserDisplayColumns() {
		// <<從此以下加入使用者程式>>
		// <<資料瀏覽欄位>>
		return new String[] { "DATE_OFF", "TOTAL", "REST_KIND", "REST_TYPE",
				"TIN", "TOUT", "MID_TIME", "NOTE", "NOTE1", "REST_SAL" };
	}

	/**
	 * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		rootLayout = new Grid();
		rootLayout.setWidth(new Extent(100, Extent.PERCENT));
		rootLayout.setSize(4);
		add(rootLayout);

		EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
		EMPSN_CaptionLabel.setText("N_REST_DETAIL.EMPSN");
		// rootLayout.add(EMPSN_CaptionLabel);
		EMPSN_DscField1 = new dsc.echo2app.component.DscField();
		EMPSN_DscField1.setId("EMPSN_DscField1");
		// rootLayout.add(EMPSN_DscField1);

		YEAR_CaptionLabel = new nextapp.echo2.app.Label();
		YEAR_CaptionLabel.setText("N_REST_DETAIL.YEAR");
		// rootLayout.add(YEAR_CaptionLabel);
		YEAR_DscField1 = new dsc.echo2app.component.DscField();
		YEAR_DscField1.setId("YEAR_DscField1");
		// rootLayout.add(YEAR_DscField1);

		DATE_OFF_CaptionLabel = new nextapp.echo2.app.Label();
		DATE_OFF_CaptionLabel.setText("N_REST_DETAIL.DATE_OFF");
		rootLayout.add(DATE_OFF_CaptionLabel);
		DATE_OFF_DscDateField1 = new dsc.echo2app.component.DscDateField();
		DATE_OFF_DscDateField1.setId("DATE_OFF_DscDateField1");
		DATE_OFF_DscDateField1.setDateFormat(_sf);
		DATE_OFF_DscDateField1.getDateChooser().setLocale(Locale.ENGLISH);
		rootLayout.add(DATE_OFF_DscDateField1);

		DATE_TO_CaptionLabel = new nextapp.echo2.app.Label();
		DATE_TO_CaptionLabel.setText("Đến ngày");
		rootLayout.add(DATE_TO_CaptionLabel);
		DATE_TO_DscDateField1 = new dsc.echo2app.component.DscDateField();
		DATE_TO_DscDateField1.setId("DATE_TO_DscDateField1");
		DATE_TO_DscDateField1.setDateFormat(_sf);
		DATE_TO_DscDateField1.getDateChooser().setLocale(Locale.ENGLISH);
		rootLayout.add(DATE_TO_DscDateField1);

		// ------------

		REST_KIND_CaptionLabel = new nextapp.echo2.app.Label();
		REST_KIND_CaptionLabel.setText("N_REST_DETAIL.REST_KIND");
		rootLayout.add(REST_KIND_CaptionLabel);
		REST_KIND_DscField3 = new SelectField();
		REST_KIND_DscField3.setId("REST_KIND_DscField3");
		REST_KIND_DscField3.setWidth(new Extent(158));
		rootLayout.add(REST_KIND_DscField3);

		REST_TYPE_CaptionLabel = new nextapp.echo2.app.Label();
		REST_TYPE_CaptionLabel.setText("N_REST_DETAIL.REST_TYPE");
		rootLayout.add(REST_TYPE_CaptionLabel);
		REST_TYPE_DscField5 = new SelectField();
		REST_TYPE_DscField5.setId("REST_TYPE_DscField5");
		REST_TYPE_DscField5.setWidth(new Extent(158));
		rootLayout.add(REST_TYPE_DscField5);

		// -----------
		/*
		 * HOUR_CaptionLabel = new nextapp.echo2.app.Label();
		 * HOUR_CaptionLabel.setText("N_REST_DETAIL.HOUR");
		 * rootLayout.add(HOUR_CaptionLabel); HOUR_DscField2 = new
		 * dsc.echo2app.component.DscField();
		 * HOUR_DscField2.setId("HOUR_DscField2");
		 * rootLayout.add(HOUR_DscField2);
		 */
		rootLayout.add(new Label("Từ giờ "));
		txt_tu_gio = new TextField();
		txt_tu_gio.setMaximumLength(4);
		rootLayout.add(txt_tu_gio);

		/*
		 * NOTE_TIME_Label = new Label("N_REST_DETAIL.NOTE_TIME");
		 * rootLayout.add(NOTE_TIME_Label); NOTE_TIME_TextArea = new TextArea();
		 * rootLayout.add(NOTE_TIME_TextArea);
		 */
		rootLayout.add(new Label("Ghi chú "));
		sfGhiChu = new SelectField();
		sfGhiChu.setWidth(new Extent(170));
		rootLayout.add(sfGhiChu);

		rootLayout.add(new Label("Đến giờ "));
		txt_den_gio = new TextField();
		txt_den_gio.setMaximumLength(4);
		rootLayout.add(txt_den_gio);

		rootLayout.add(new Label(" "));
		sfGhiChu1 = new SelectField();
		sfGhiChu1.setWidth(new Extent(170));
		rootLayout.add(sfGhiChu1);

		rootLayout.add(new Label("Giờ nghỉ trưa "));
		sfGioNghiTrua = new SelectField();
		// sfGioNghiTrua.setModel(getModelGioNghiTrua());
		sfGioNghiTrua.setWidth(new Extent(150));
		rootLayout.add(sfGioNghiTrua);

		/*
		 * radio_dai_ngay = new RadioButton("Dài ngày"); radio_ngan_ngay = new
		 * RadioButton("Ngắn ngày"); gb_ngan_dai_ngay = new GroupBox();
		 * bt_group_ngan_dai_ngay = new ButtonGroup();
		 * gb_ngan_dai_ngay.setWidth(new Extent(100));
		 * gb_ngan_dai_ngay.add(radio_ngan_ngay);
		 * gb_ngan_dai_ngay.add(radio_dai_ngay);
		 * radio_dai_ngay.setGroup(bt_group_ngan_dai_ngay);
		 * radio_ngan_ngay.setGroup(bt_group_ngan_dai_ngay); rootLayout.add(new
		 * Label(" ")); rootLayout.add(gb_ngan_dai_ngay);
		 */

		// -------------

		USER_UP_CaptionLabel = new Label();
		USER_UP_CaptionLabel.setText("N_REST_DETAIL.USER_UP");
		// rootLayout.add(USER_UP_CaptionLabel);
		USER_UP_TextField = new TextField();
		USER_UP_TextField.setEnabled(false);
		// rootLayout.add(USER_UP_TextField);

		DATE_UP_CaptionLabel = new Label();
		DATE_UP_CaptionLabel.setText("N_REST_DETAIL.DATE_UP");
		// rootLayout.add(DATE_UP_CaptionLabel);
		DATE_UP_DateField = new DateField();
		DATE_UP_DateField.setDateFormat(_sf);
		DATE_UP_DateField.getDateChooser().setLocale(Locale.ENGLISH);
		DATE_UP_DateField.setEnabled(false);
		// rootLayout.add(DATE_UP_DateField);

		// -----------the end\
		// =====================

		NOTE_CaptionLabel = new nextapp.echo2.app.Label();
		NOTE_CaptionLabel.setText("N_REST_DETAIL.NOTE");
		// rootLayout.add(NOTE_CaptionLabel);
		NOTE_DscField9 = new dsc.echo2app.component.DscField();
		NOTE_DscField9.setId("NOTE_DscField9");
		// rootLayout.add(NOTE_DscField9);

		TIN_CaptionLabel = new nextapp.echo2.app.Label();
		TIN_CaptionLabel.setText("N_REST_DETAIL.TIN");
		// rootLayout.add(TIN_CaptionLabel);
		TIN_DscField6 = new dsc.echo2app.component.DscField();
		TIN_DscField6.setId("TIN_DscField6");
		// rootLayout.add(TIN_DscField6);
		TOUT_CaptionLabel = new nextapp.echo2.app.Label();
		TOUT_CaptionLabel.setText("N_REST_DETAIL.TOUT");
		// rootLayout.add(TOUT_CaptionLabel);
		TOUT_DscField7 = new dsc.echo2app.component.DscField();
		TOUT_DscField7.setId("TOUT_DscField7");
		// rootLayout.add(TOUT_DscField7);
		MID_TIME_CaptionLabel = new nextapp.echo2.app.Label();
		MID_TIME_CaptionLabel.setText("N_REST_DETAIL.MID_TIME");
		// rootLayout.add(MID_TIME_CaptionLabel);
		MID_TIME_DscField8 = new dsc.echo2app.component.DscField();
		MID_TIME_DscField8.setId("MID_TIME_DscField8");
		// rootLayout.add(MID_TIME_DscField8);

		NOTE1_CaptionLabel = new nextapp.echo2.app.Label();
		NOTE1_CaptionLabel.setText("N_REST_DETAIL.NOTE1");
		// rootLayout.add(NOTE1_CaptionLabel);
		NOTE1_DscField10 = new dsc.echo2app.component.DscField();
		NOTE1_DscField10.setId("NOTE1_DscField10");
		// rootLayout.add(NOTE1_DscField10);
		LOCKED_CaptionLabel = new nextapp.echo2.app.Label();
		LOCKED_CaptionLabel.setText("N_REST_DETAIL.LOCKED");
		// rootLayout.add(LOCKED_CaptionLabel);
		LOCKED_DscField11 = new dsc.echo2app.component.DscField();
		LOCKED_DscField11.setId("LOCKED_DscField11");
		// rootLayout.add(LOCKED_DscField11);

		REST_SAL_CaptionLabel = new nextapp.echo2.app.Label();
		REST_SAL_CaptionLabel.setText("N_REST_DETAIL.REST_SAL");
		// rootLayout.add(REST_SAL_CaptionLabel);
		REST_SAL_DscField4 = new dsc.echo2app.component.DscField();
		REST_SAL_DscField4.setId("REST_SAL_DscField4");
		REST_SAL_DscField4.setEnabled(false);
		// rootLayout.add(REST_SAL_DscField4);

		TOTAL_CaptionLabel = new nextapp.echo2.app.Label();
		TOTAL_CaptionLabel.setText("N_REST_DETAIL.TOTAL");
		// rootLayout.add(TOTAL_CaptionLabel);
		TOTAL_DscField2 = new dsc.echo2app.component.DscField();
		TOTAL_DscField2.setId("TOTAL_DscField2");
		// rootLayout.add(TOTAL_DscField2);

	}

	private ListModel getModelGioNghiTrua() {
		// TODO Auto-generated method stub
		DefaultListModel Model = new DefaultListModel();
		Model.add("");
		Model.add("10:45");
		Model.add("11:00");
		Model.add("11:30");
		Model.add("11:45");
		Model.add("12:00");
		Model.add("12:15");
		return Model;
	}

	private ListModel getModelGhiChuPBan() {
		// TODO Auto-generated method stub
		DefaultListModel Model = new DefaultListModel();
		Model.add("");
		Model.add("PBAN");
		Model.add("CON_OM_75%");
		return Model;
	}

	private ListModel getModelGhiChuPBenh() {
		// TODO Auto-generated method stub
		DefaultListModel Model = new DefaultListModel();
		Model.add("");
		Model.add("NV_BENH");
		Model.add("NV_BENH_75%");
		Model.add("KHAM_THAI_100%");
		Model.add("SAY_THAI_100%");
		Model.add("KE_HH_100%");
		return Model;
	}

	private ListModel getModelGhiChuNgan_DaiNgay() {
		// TODO Auto-generated method stub
		DefaultListModel Model = new DefaultListModel();
		Model.add("");
		Model.add("NGAN_NGAY");
		Model.add("DAI_NGAY");
		return Model;
	}

}
