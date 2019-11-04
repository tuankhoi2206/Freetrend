package ds.program.fvhr.ui.quitworksalary;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import fv.components.AnyRowNavigator;
import fv.components.JDataContent;
import fv.util.BundleUtils;
import fv.util.FVGenericInfo;
import fv.util.GenericJdbcDAO;
import fv.util.ListBinder;
import fv.util.MappingPropertyUtils;
import fv.util.Vni2Uni;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.extras.app.TabPane;
import nextapp.echo2.extras.app.layout.TabPaneLayoutData;
import ds.program.fvhr.domain.ATTENDANTDB_QUIT;
import ds.program.fvhr.domain.ATTQUIT200900;
import ds.program.fvhr.domain.N_DEPARTMENT;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import echopointng.GroupBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Insets;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.binder.UIDataObjectBinder;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.ResourceImageReference;
/**
 * Luong nghi viec DataContent
 * @author Hieu
 *
 */
public class AttQuitDataContent extends JDataContent {

	private static final long serialVersionUID = 1L;

	private ResourceBundle resourceBundle;
	private UIDataObjectBinder binder;
	private ATTQUIT200900 userDataObject;
	private List<Component> mutableComponents;
	private List<Component> immutableComponents;
	private boolean bb;
	private SplitPane mainSplitPane;
	private TabPane tabPane;
	private ContentPane luongNghiViecContent;
	private Grid rootLayout1;
	private Label lblSoThe;
	private DscField txtSoThe;
	private Label lblHoTen;
	private DscField txtHoTen;
	private Label lblDonVi;
	private DscField txtDonVi;
	private Label lblChucVu;
	private DscField txtChucVu;
	private Label lblNNX;
	private DscDateField dfNNX;
	private Label lblDonViKT;
	private DscField txtDonViKT;
	private Label lblLCB;
	private DscField txtLCB;
	private Label lblLHD;
	private DscField txtLHD;
	private Label lblLCBBQ;
	private DscField txtLCBBQ;
	private Label lblPCCVBQ;
	private DscField txtPCCVBQ;
	private Label lblLuongThangTruoc;
	private DscField txtLuongThangTruoc;
	private Label lblGhiChu;
	private DscField txtGhiChu;
	private SelectField sfMonth;
	private SelectField sfYear;
	private Label lblDotTV;
	private DscDateField dfDotTV;
	private Label lblLoai;
	private SelectField sfLoai;
	private Label lblSTTC;
	private DscField txtSTTC;
	private DscField txtTTSTTC;
	private Label lblSNPN;
	private DscField txtSNPN;
	private DscField txtTTSNPN;
	private Label lblSTDB;
	private DscField txtSTDB;
	private DscField txtTTSTDB;
	private Label lblNTTH;
	private DscField txtNTTH;
	private DscField txtTTNTTH;
	private Label lblTongLCB;
	private Label lblTienTangCa;
	private Label lblTongPC;
	private DscField txtTongLCB;
	private DscField txtTienTC;
	private DscField txtTongPC;
	private Label lblTong;
	private Label lblKhauTru;
	private Label lblLuongThangHT;
	private DscField txtTong;
	private DscField txtTongKhauTru;
	private DscField txtLuongThangHT;
	private Label lblTongTroCap;
	private Label lblThucNhan;
	private DscField txtTongTroCap;
	private DscField txtThucNhan;
	private ContentPane chiTietThoiGianContent;
	private Grid rootLayout2;
	private Label lblSoNgayLam;
	private DscField txtSoNgayLam;
	private Label lblSoDemLam;
	private DscField txtSoDemLam;
	private Label lblTangCaNgay;
	private DscField txtTangCaNgay;
	private DscField txtTangCaNgayNgoai;
	private Label lblTangCaDem;
	private DscField txtTangCaDem;
	private DscField txtTangCaDemNgoai;
	private Label lblTangCaCN;
	private DscField txtTangCaCN;
	private DscField txtTangCaCNNgoai;
	private Label lblTangCaLe;
	private DscField txtTangCaLe;
	private DscField txtTangCaLeNgoai;
	private Label lblSoDemTangCa;
	private DscField txtSoDemTC;
	private DscField txtSoDemTCNgoai;
	private Label lblKyTen;
	private DscField txtKyTen;
	private Label lblTreSom;
	private DscField txtTreSom;
	private Button btnBuLuong;
	private Label lblCoPhep;
	private DscField txtCoPhep;
	private Label lblCoLuong;
	private DscField txtCoLuong;
	private Label lblPhepBenh;
	private DscField txtPhepBenh;
	private Label lblKhangCong;
	private DscField txtKhangCong;
	private Label lblDuongSuc;
	private DscField txtDuongSuc;
	private Label lblNghiSan;
	private DscField txtNghiSan;
	private Label lblTienThuong;
	private DscField txtTienThuong;
	private DscField txtTienThuongTT;
	private Label lblChuyenCan;
	private DscField txtChuyenCan;
	private Label lblPCCV1;
	private DscField txtPCCVu;
	private DscField txtPCCVuTT;
	private Label lblPCTC;
	private DscField txtPCTC;
	private Label lblPCCViec;
	private DscField txtPCCViec;
	private DscField txtPCCViecTT;
	private Label lblChuyenCanTC;
	private DscField txtChuyenCanTC;
	private Label lblPCSH;
	private DscField txtPCSH;
	private DscField txtPCSHTT;
	private Label lblCLPCTC;
	private DscField txtCLPCTC;
	private Label lblPC8H;
	private DscField txtPC8H;
	private Label lblBuBH;
	private DscField txtBuBH;
	private Label lblBuLuong;
	private DscField txtBuLuong;
	private Label lblSoNguoiPhuThuoc;
	private DscField txtNguoiPhuThuoc;
	private Label lblBuBHNuaThangDau;
	private DscField txtBuBHNuaThangDau;
	private Label lblBuBHNuaThangSau;
	private DscField txtBuBHNuaThangSau;
	private Label lblBHXH;
	private DscField txtBHXH;
	private DscField txtBHXHTT;
	private Label lblTamUng;
	private DscField txtTamUng;
	private Label lblBHYT;
	private DscField txtBHYT;
	private DscField txtBHYTTT;
	private Label lblDoanPhi;
	private DscField txtDoanPhi;
	private Label lblBHTN;
	private DscField txtBHTN;
	private DscField txtBHTNTT;
	private Label lblSTNV;
	private DscField txtSTNV;
	private Label lblBuBHYT;
	private DscField txtBuBHYT;
	private Label lblThuBHYT;
	private DscField txtThuBHYT;
	private Label lblThueTN;
	private DscField txtThueTN;
	private Label lblBuThuBHYT;
	private DscField txtBuThuBHYT;
	private Label lblKhauTruKhac;
	private DscField txtKhauTruKhac;
	private ContentPane toolbarPane;

	private AnyRowNavigator navigator;

	/**
	 * Creates a new <code>AttQuitDataContent</code>.
	 */
	public AttQuitDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
		//layout
		navigator = new AnyRowNavigator();
		toolbarPane.add(navigator);
		//more init
		dfDotTV.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dfDotTV.getDateChooser().setLocale(new Locale("en"));
		dfDotTV.getTextField().setForeground(new Color(0x004080));
		dfNNX.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dfNNX.getDateChooser().setLocale(new Locale("en"));
		dfNNX.getTextField().setForeground(new Color(0x004080));
		ListBinder.bindSelectField(sfMonth, MappingPropertyUtils.getJavaMonthEditor(), true);
		ListBinder.bindSelectField(sfYear, MappingPropertyUtils.getYearEditor(1, false), true);
		Calendar cal = Calendar.getInstance();
		ListBinder.refreshIndex(sfMonth, cal.get(Calendar.MONTH));
		sfYear.setSelectedIndex(1);
		/////other
		setTableName("ATTENDANTDB_QUIT_"+getSelectedMonth()+getSelectedYear());
		binder = new UIDataObjectBinder(ATTQUIT200900.class);
	}
	
	@Override
	public Class getDataObjectClass() {
		return ATTENDANTDB_QUIT.class;
	}
	
	@Override
	public AttQuit01Program getProgram() {
		return (AttQuit01Program) super.getProgram();
	}
	
	public ATTQUIT200900 getUserDataObject() {
		return userDataObject;
	}
	
	public void setUserDataObject(ATTQUIT200900 userDataObject) {
		this.userDataObject = userDataObject;
	}
	
	public AnyRowNavigator getNavigator() {
		return navigator;
	}
	
	@Override
	protected int doInit() {
		int ret = super.doInit();
		registerPropertiesEditor();
		bindUI();
		return ret;
	}
	
	public UIDataObjectBinder getAttQuitBinder(){
		return binder;
	}
	
	@Override
	public boolean checkDataObject() {
		ATTENDANTDB_QUIT data = (ATTENDANTDB_QUIT) getDataObject();
		if (super.checkDataObject()){
			if (getProgram().getDataMode()==IProgram.DATAMODE_NEW){
				String month = sfMonth.getSelectedItem().toString();
				String year = sfYear.getSelectedItem().toString();
				String empsn =data.getEMPSN(); 
				if (empsn.matches("[0-9]{8}")){
					if (!getProgram().getValidator().isQuitStatusValid(empsn,month,year)){
						setErrorMessage("Không tìm thấy thông tin nghỉ việc hoặc chưa xử lý ngày công.");
						return false;						
					}else if (getProgram().getDao().checkAttendantExist(data.getEMPSN(), data.getDOT_TV())){
						setErrorMessage("Đã có thông tin nghỉ việc trong bảng nghỉ việc tháng " + month + " năm " + year + "(" + BundleUtils.getDateFormat().format(data.getDOT_TV()) + ")");
						return false;
					}else{
						if (!bb){
							setErrorMessage("Nhập số thẻ và nhấn enter để lấy thông tin cơ bản và ngày công");
							return false;
						}
					}
				}else{
					setErrorMessage("Số thẻ không hợp lệ");
					return false;
				}
			}
			return true;
			
		}
		return false;
	}
	
	@Override
	public void beforeSaveToDataObjectSet() {
		ATTENDANTDB_QUIT data = (ATTENDANTDB_QUIT) getDataObject();
		data.setEMPNA(Vni2Uni.convertToVNI(data.getEMPNA()));
		data.setNOTE_BH(Vni2Uni.convertToVNI(data.getNOTE_BH()));		
	}
	
	private void registerPropertiesEditor(){
//		binder.registerCustomEditor(ATTQUIT200900.class, "DOT_TV", PropertyEditors.createDateEditor("dd/MM/yyyy"));
		getUIDataObjectBinder().registerCustomEditor(ATTENDANTDB_QUIT.class, "DEPSN", depsnEditor());
		getUIDataObjectBinder().registerCustomEditor(ATTENDANTDB_QUIT.class, "DOT_TV", PropertyEditors.createDateEditor("dd/MM/yyyy"));
		getUIDataObjectBinder().registerCustomEditor(ATTENDANTDB_QUIT.class, "HIRED", PropertyEditors.createDateEditor("dd/MM/yyyy"));
		getUIDataObjectBinder().registerCustomEditor(ATTENDANTDB_QUIT.class, "CLASS", getTypeEditor());
	}
	
	private void bindUI(){
		mutableComponents = new ArrayList<Component>();
		immutableComponents = new ArrayList<Component>();
		
		getUIDataObjectBinder().addBindMap("EMPSN", txtSoThe, lblSoThe);
		mutableComponents.add(txtSoThe);
		getUIDataObjectBinder().addBindMap("DEPT_KT", txtDonViKT, lblDonViKT);
		mutableComponents.add(txtDonViKT);
		getUIDataObjectBinder().addBindMap("DOT_TV", dfDotTV, lblDotTV);
		mutableComponents.add(dfDotTV);
		getUIDataObjectBinder().addBindMap("BSALY", txtLCB, lblLCB);
		mutableComponents.add(txtLCB);
		getUIDataObjectBinder().addBindMap("COMBSALY", txtLHD, lblLHD);
		mutableComponents.add(txtLHD);
		getUIDataObjectBinder().addBindMap("BONUS1", txtTienThuong, lblTienThuong);
		mutableComponents.add(txtTienThuong);
		getUIDataObjectBinder().addBindMap("BONUS2", txtPCCVu, lblPCCV1);
		mutableComponents.add(txtPCCVu);
		getUIDataObjectBinder().addBindMap("BONUS3", txtPCCViec, lblPCCViec);
		mutableComponents.add(txtPCCViec);
		getUIDataObjectBinder().addBindMap("BONUS4", txtPCSH, lblPCSH);
		mutableComponents.add(txtPCSH);
		getUIDataObjectBinder().addBindMap("BONUS8", txtPC8H, lblPC8H);
		mutableComponents.add(txtPC8H);
		getUIDataObjectBinder().addBindMap("BONUS5", txtBuLuong, lblBuLuong);
		mutableComponents.add(txtBuLuong);
		getUIDataObjectBinder().addBindMap("BORM", txtTamUng, lblTamUng);
		mutableComponents.add(txtTamUng);
		getUIDataObjectBinder().addBindMap("KQT", txtKhauTruKhac, lblKhauTruKhac);
		mutableComponents.add(txtKhauTruKhac);
		getUIDataObjectBinder().addBindMap("JOINLUM", txtDoanPhi, lblDoanPhi);
		mutableComponents.add(txtDoanPhi);
		getUIDataObjectBinder().addBindMap("YLBX", txtBHYT, lblBHYT);
		mutableComponents.add(txtBHYT);
		getUIDataObjectBinder().addBindMap("JOININSU", txtBHXH, lblBHXH);
		mutableComponents.add(txtBHXH);
		getUIDataObjectBinder().addBindMap("BH_TNGHIEP", txtBHTN, lblBHTN);
		mutableComponents.add(txtBHTN);
		getUIDataObjectBinder().addBindMap("BONUS9", txtBuBH, lblBuBH);
		mutableComponents.add(txtBuBH);
		getUIDataObjectBinder().addBindMap("BSAL_AVG", txtLCBBQ, lblLCBBQ);
		mutableComponents.add(txtLCBBQ);
		getUIDataObjectBinder().addBindMap("BONUS2_AVG", txtPCCVBQ, lblPCCVBQ);
		mutableComponents.add(txtPCCVBQ);
		getUIDataObjectBinder().addBindMap("REST_REMAIN", txtSNPN, lblSNPN);
		mutableComponents.add(txtSNPN);
		getUIDataObjectBinder().addBindMap("MM_DENBU", txtSTDB, lblSTDB);
		mutableComponents.add(txtSTDB);
		getUIDataObjectBinder().addBindMap("MM_BONUS", txtSTTC, lblSTTC);
		mutableComponents.add(txtSTTC);
		getUIDataObjectBinder().addBindMap("DAY_BEFORE", txtNTTH, lblNTTH);
		mutableComponents.add(txtNTTH);
		getUIDataObjectBinder().addBindMap("BU_BHYT", txtBuBHYT, lblBuBHYT);
		mutableComponents.add(txtBuBHYT);
		getUIDataObjectBinder().addBindMap("THU_BHYT", txtThuBHYT, lblThuBHYT);
		mutableComponents.add(txtThuBHYT);
		getUIDataObjectBinder().addBindMap("BUTHU_BHYT", txtBuThuBHYT, lblBuThuBHYT);
		mutableComponents.add(txtBuThuBHYT);
		getUIDataObjectBinder().addBindMap("NOTE_BH", txtGhiChu, lblGhiChu);
		mutableComponents.add(txtGhiChu);
		getUIDataObjectBinder().addBindMap("ADDCLS1_O", txtTangCaNgayNgoai, null);
		mutableComponents.add(txtTangCaNgayNgoai);
		getUIDataObjectBinder().addBindMap("NADDCLS_O", txtTangCaDemNgoai, null);
		mutableComponents.add(txtTangCaDemNgoai);
		getUIDataObjectBinder().addBindMap("ADDHOL_O", txtTangCaCNNgoai, null);
		mutableComponents.add(txtTangCaCNNgoai);
		getUIDataObjectBinder().addBindMap("ADDHOLN_O", txtTangCaLeNgoai, null);
		mutableComponents.add(txtTangCaLeNgoai);
		getUIDataObjectBinder().addBindMap("ACNM_O", txtSoDemTCNgoai, null);
		mutableComponents.add(txtSoDemTCNgoai);
		getUIDataObjectBinder().addBindMap("BSALY_LAST_MONTH", txtLuongThangTruoc, lblLuongThangTruoc);
		mutableComponents.add(txtLuongThangTruoc);
		getUIDataObjectBinder().addBindMap("BAC", txtNguoiPhuThuoc, lblSoNguoiPhuThuoc);
		mutableComponents.add(txtNguoiPhuThuoc);
		getUIDataObjectBinder().addBindMap("ACNM", txtSoDemTC, lblSoDemTangCa);
		immutableComponents.add(txtSoDemTC);
		getUIDataObjectBinder().addBindMap("TEMP1", txtBuBHNuaThangDau, lblBuBHNuaThangDau);
		mutableComponents.add(txtBuBHNuaThangDau);
		getUIDataObjectBinder().addBindMap("TEMP2", txtBuBHNuaThangSau, lblBuBHNuaThangSau);
		mutableComponents.add(txtBuBHNuaThangSau);
		getUIDataObjectBinder().addBindMap("CLASS", sfLoai, lblLoai);
		mutableComponents.add(sfLoai);

		getUIDataObjectBinder().addBindMap("EMPNA", txtHoTen, lblHoTen);
		immutableComponents.add(txtHoTen);
		getUIDataObjectBinder().addBindMap("DEPSN", txtDonVi, lblDonVi);
		immutableComponents.add(txtDonVi);
		getUIDataObjectBinder().addBindMap("HIRED", dfNNX, lblNNX);
		immutableComponents.add(dfNNX);
		getUIDataObjectBinder().addBindMap("POSSN", txtChucVu, lblChucVu);
		immutableComponents.add(txtChucVu);
		binder.addBindMap("JOININSU", txtBHXHTT, null);
		immutableComponents.add(txtBHXHTT);
		binder.addBindMap("YLBX", txtBHYTTT, null);
		immutableComponents.add(txtBHYTTT);
		binder.addBindMap("BH_TNGHIEP", txtBHTNTT, null);
		immutableComponents.add(txtBHTNTT);
		binder.addBindMap("MM_TROCAP_S", txtTTSTTC, null);
		immutableComponents.add(txtTTSTTC);
		binder.addBindMap("PN_CONLAI_S", txtTTSNPN, null);
		immutableComponents.add(txtTTSNPN);
		binder.addBindMap("MM_DENBU_S", txtTTSTDB, null);
		immutableComponents.add(txtTTSTDB);
		binder.addBindMap("DD_NGHITRUOC_S", txtTTNTTH, null);
		immutableComponents.add(txtTTNTTH);
		binder.addBindMap("BONUS1", txtTienThuongTT, null);
		immutableComponents.add(txtTienThuongTT);
		binder.addBindMap("BONUS2", txtPCCVuTT, null);
		immutableComponents.add(txtPCCVuTT);
		binder.addBindMap("BONUS3", txtPCCViecTT, null);
		immutableComponents.add(txtPCCViecTT);
		binder.addBindMap("BONUS4", txtPCSHTT, null);
		immutableComponents.add(txtPCSHTT);
		binder.addBindMap("STNV", txtSTNV, lblSTNV);
		immutableComponents.add(txtSTNV);
		binder.addBindMap("PAYTAX", txtThueTN, lblThueTN);
		immutableComponents.add(txtThueTN);
		getUIDataObjectBinder().addBindMap("DUCLS", txtSoNgayLam, lblSoNgayLam);
		immutableComponents.add(txtSoNgayLam);
		getUIDataObjectBinder().addBindMap("NUCLS", txtSoDemLam, lblSoDemLam);
		immutableComponents.add(txtSoDemLam);
		getUIDataObjectBinder().addBindMap("ADDCLS1", txtTangCaNgay, lblTangCaNgay);
		immutableComponents.add(txtTangCaNgay);
		getUIDataObjectBinder().addBindMap("NADDCLS", txtTangCaDem, lblTangCaDem);
		immutableComponents.add(txtTangCaDem);
		getUIDataObjectBinder().addBindMap("ADDHOL", txtTangCaCN, lblTangCaCN);
		immutableComponents.add(txtTangCaCN);
		getUIDataObjectBinder().addBindMap("ADDHOLN", txtTangCaLe, lblTangCaLe);
		immutableComponents.add(txtTangCaLe);
		getUIDataObjectBinder().addBindMap("SIGN_TIME", txtKyTen, lblKyTen);
		immutableComponents.add(txtKyTen);
		getUIDataObjectBinder().addBindMap("LATE", txtTreSom, lblTreSom);
		immutableComponents.add(txtTreSom);
		getUIDataObjectBinder().addBindMap("REST", txtCoPhep, lblCoPhep);
		immutableComponents.add(txtCoPhep);
		getUIDataObjectBinder().addBindMap("REST_PAY", txtCoLuong, lblCoLuong);
		immutableComponents.add(txtCoLuong);
		getUIDataObjectBinder().addBindMap("REST_SICK", txtPhepBenh, lblPhepBenh);
		immutableComponents.add(txtPhepBenh);
		getUIDataObjectBinder().addBindMap("NWHOUR", txtKhangCong, lblKhangCong);
		immutableComponents.add(txtKhangCong);
		getUIDataObjectBinder().addBindMap("OTHER", txtDuongSuc, lblDuongSuc);
		immutableComponents.add(txtDuongSuc);
		getUIDataObjectBinder().addBindMap("PHEP_NS", txtNghiSan, lblNghiSan);
		immutableComponents.add(txtNghiSan);
		binder.addBindMap("BSALY_N", txtCLPCTC, lblCLPCTC);
		immutableComponents.add(txtCLPCTC);
		binder.addBindMap("BONUS6", txtChuyenCan, lblChuyenCan);
		immutableComponents.add(txtChuyenCan);
		binder.addBindMap("BONUS7", txtPCTC, lblPCTC);
		immutableComponents.add(txtPCTC);
		binder.addBindMap("BONUS_ACN", txtChuyenCanTC, lblChuyenCanTC);
		immutableComponents.add(txtChuyenCanTC);
		binder.addBindMap("TBASIC", txtTongLCB, null);
		immutableComponents.add(txtTongLCB);
		binder.addBindMap("TADDS", txtTienTC, null);
		immutableComponents.add(txtTienTC);
		binder.addBindMap("TBONUS", txtTongPC, null);
		immutableComponents.add(txtTongPC);
		binder.addBindMap("TINCOME", txtTong, null);
		immutableComponents.add(txtTong);
		binder.addBindMap("TKQ", txtTongKhauTru, null);
		immutableComponents.add(txtTongKhauTru);
		binder.addBindMap("TS_HIENTAI1", txtLuongThangHT, null);
		immutableComponents.add(txtLuongThangHT);
		binder.addBindMap("TTS", txtThucNhan, null);
		immutableComponents.add(txtThucNhan);
		binder.addBindMap("TT_TCTVIEC", txtTongTroCap, null);
		immutableComponents.add(txtTongTroCap);
	}
	
	private MappingPropertyEditor depsnEditor(){
		MappingPropertyEditor de = FVGenericInfo.getDepartmentEditor();
		MappingPropertyEditor e = new MappingPropertyEditor();
		String[] disp = de.getDisplays();
		for (String str:disp){
			de.setAsText(str);
			N_DEPARTMENT d = (N_DEPARTMENT) de.getValue();
			e.put(d.getNAME_DEPT(), str);
		}
		return e;
	}
	
	private MappingPropertyEditor getTypeEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		e.put("Thôi việc", null);
		e.put("Bỏ việc", "BV");
		return e;
	}
	
	
	@Override
	protected void doUIRefresh() {
		super.doUIRefresh();
		
		if (getProgram().getDataMode()==IProgram.DATAMODE_BROWSER||getProgram().getDataMode()==IProgram.DATAMODE_NONE){
			rootLayout1.setEnabled(false);
			rootLayout2.setEnabled(false);
		}else{
			rootLayout1.setEnabled(true);
			rootLayout2.setEnabled(true);
			if (getProgram().getDataMode()==IProgram.DATAMODE_EDIT){
				txtSoThe.setEnabled(false);
				dfDotTV.setEnabled(false);
				sfMonth.setEnabled(false);
				sfYear.setEnabled(false);
			}else{
				txtSoThe.setEnabled(true);
				dfDotTV.setEnabled(true);
				sfMonth.setEnabled(true);
				sfYear.setEnabled(true);
			}
			for (Component c:immutableComponents){
				c.setEnabled(false);
			}
		}
		getUIDataObjectBinder().doDataBindUIStyle();
		binder.doDataBindUIStyle();
	}
	
	@Override
	protected void doNewDefaulData() {
		super.doNewDefaulData();
		bb=false;
		ATTENDANTDB_QUIT data = (ATTENDANTDB_QUIT) getDataObject();
		data.setREST_REMAIN(BigDecimal.ZERO);
		data.setDAY_BEFORE(BigDecimal.ZERO);
		data.setMM_BONUS(BigDecimal.ZERO);
		data.setMM_DENBU(BigDecimal.ZERO);
		data.setBSALY_LAST_MONTH(BigDecimal.ZERO);
		data.setACNM_O(BigDecimal.ZERO);
		data.setADDCLS1_O(BigDecimal.ZERO);
		data.setNADDCLS_O(BigDecimal.ZERO);
		data.setADDHOL_O(BigDecimal.ZERO);
		data.setADDHOLN_O(BigDecimal.ZERO);
		data.setBONUS1(BigDecimal.ZERO);
		data.setBONUS2(BigDecimal.ZERO);
		data.setBONUS3(BigDecimal.ZERO);
		data.setBONUS4(BigDecimal.ZERO);
		data.setBONUS8(BigDecimal.ZERO);
		data.setBONUS5(BigDecimal.ZERO);
		data.setBONUS9(BigDecimal.ZERO);
		data.setBORM(BigDecimal.ZERO);
		data.setKQT(BigDecimal.ZERO);
		data.setJOINLUM(BigDecimal.ZERO);
		data.setJOININSU(BigDecimal.ZERO);
		data.setBH_TNGHIEP(BigDecimal.ZERO);
		data.setTEMP1(BigDecimal.ZERO);
		data.setTEMP2(BigDecimal.ZERO);
		data.setBAC(BigDecimal.ZERO);
		data.setBU_BHYT(BigDecimal.ZERO);
		data.setTHU_BHYT(BigDecimal.ZERO);
		data.setBUTHU_BHYT(BigDecimal.ZERO);
		binder.bindDataToFields(getUserDataObject());
	}

	private void findEmployee(ActionEvent e) {
		String empsn = txtSoThe.getText();
		if (empsn.equals("")) return;
		if (empsn.matches("[0-9]{8}")){
			if (getProgram().getValidator().isQuitStatusValid(empsn,getSelectedMonth(),getSelectedYear())){
				ATTENDANTDB_QUIT data = (ATTENDANTDB_QUIT) getDataObject();
				getProgram().getDao().setMonth(getSelectedMonth());
				getProgram().getDao().setYear(getSelectedYear());
				getProgram().getDao().fillATTENDANTDB_QUIT(data, empsn);
				getUIDataObjectBinder().bindDataToFields(data);
				bb=true;
			}else{
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Không tìm thấy thông tin nghỉ việc hoặc chưa xử lý ngày công");
				ATTENDANTDB_QUIT data = new ATTENDANTDB_QUIT();
				data.setEMPSN(empsn);
				getUIDataObjectBinder().bindDataToFields(data);
				bb=false;
			}
		}else{
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Số thẻ không hợp lệ");
			ATTENDANTDB_QUIT data = new ATTENDANTDB_QUIT();
			data.setEMPSN(empsn);
			getUIDataObjectBinder().bindDataToFields(data);
			bb=false;
		}
	}

	private void removeWPData(ActionEvent e) {
		ATTENDANTDB_QUIT data = (ATTENDANTDB_QUIT) getDataObject();
		data.setDUCLS(BigDecimal.ZERO);
		data.setNUCLS(BigDecimal.ZERO);
		data.setADDCLS1(BigDecimal.ZERO);
		data.setNADDCLS(BigDecimal.ZERO);
		data.setADDHOL(BigDecimal.ZERO);
		data.setADDHOLN(BigDecimal.ZERO);
		data.setACNM(BigDecimal.ZERO);
		data.setSIGN_TIME(BigDecimal.ZERO);
		data.setLATE(BigDecimal.ZERO);
		data.setREST(BigDecimal.ZERO);
		data.setREST_PAY(BigDecimal.ZERO);
		data.setREST_SICK(BigDecimal.ZERO);
		data.setNWHOUR(BigDecimal.ZERO);
		data.setOTHER(BigDecimal.ZERO);
		data.setPHEP_NS(BigDecimal.ZERO);
		getUIDataObjectBinder().bindDataToFields(data);
	}
	
	public void updateDAO(String month, String year){
		GenericJdbcDAO dao = (GenericJdbcDAO) getDataObjectSet().getDao();
		dao.setTable("ATTENDANTDB_QUIT_"+month+year);
		setTableName("ATTENDANTDB_QUIT_"+month+year);
		ListBinder.refreshIndex(sfMonth, Integer.parseInt(month)-1);
		ListBinder.refreshIndex(sfYear, Integer.parseInt(year));
	}
	
	public String getSelectedMonth() {
		return sfMonth.getSelectedItem().toString();
	}

	public String getSelectedYear() {
		return sfYear.getSelectedItem().toString();
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		mainSplitPane = new SplitPane();
		mainSplitPane.setSeparatorPosition(new Extent(34, Extent.PX));
		mainSplitPane.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		add(mainSplitPane);
		toolbarPane = new ContentPane();
		mainSplitPane.add(toolbarPane);
		tabPane = new TabPane();
		tabPane.setTabHeight(new Extent(25, Extent.PX));
		mainSplitPane.add(tabPane);
		luongNghiViecContent = new ContentPane();
		TabPaneLayoutData luongNghiViecContentLayoutData = new TabPaneLayoutData();
		luongNghiViecContentLayoutData.setTitle("Lương nghỉ việc");
		luongNghiViecContent.setLayoutData(luongNghiViecContentLayoutData);
		tabPane.add(luongNghiViecContent);
		rootLayout1 = new Grid();
		rootLayout1.setInsets(new Insets(new Extent(2, Extent.PX)));
		luongNghiViecContent.add(rootLayout1);
		GroupBox groupBox1 = new GroupBox();
		groupBox1.setTitle("Thông tin cơ bản");
		groupBox1.setHeight(new Extent(380, Extent.PX));
		groupBox1.setWidth(new Extent(355, Extent.PX));
		GridLayoutData groupBox1LayoutData = new GridLayoutData();
		groupBox1LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBox1LayoutData.setRowSpan(3);
		groupBox1.setLayoutData(groupBox1LayoutData);
		rootLayout1.add(groupBox1);
		Grid grid2 = new Grid();
		grid2.setInsets(new Insets(new Extent(1, Extent.PX)));
		groupBox1.add(grid2);
		lblSoThe = new Label();
		lblSoThe.setText("Số thẻ");
		GridLayoutData lblSoTheLayoutData = new GridLayoutData();
		lblSoTheLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblSoThe.setLayoutData(lblSoTheLayoutData);
		grid2.add(lblSoThe);
		txtSoThe = new DscField();
		txtSoThe.setForeground(new Color(0x004080));
		txtSoThe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				findEmployee(e);
			}
		});
		grid2.add(txtSoThe);
		lblHoTen = new Label();
		lblHoTen.setText("Họ tên");
		GridLayoutData lblHoTenLayoutData = new GridLayoutData();
		lblHoTenLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblHoTen.setLayoutData(lblHoTenLayoutData);
		grid2.add(lblHoTen);
		txtHoTen = new DscField();
		txtHoTen.setWidth(new Extent(220, Extent.PX));
		txtHoTen.setForeground(new Color(0x004080));
		grid2.add(txtHoTen);
		lblDonVi = new Label();
		lblDonVi.setText("Đơn vị");
		GridLayoutData lblDonViLayoutData = new GridLayoutData();
		lblDonViLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblDonVi.setLayoutData(lblDonViLayoutData);
		grid2.add(lblDonVi);
		txtDonVi = new DscField();
		txtDonVi.setWidth(new Extent(220, Extent.PX));
		txtDonVi.setForeground(new Color(0x004080));
		grid2.add(txtDonVi);
		lblChucVu = new Label();
		lblChucVu.setText("Chức vụ");
		GridLayoutData lblChucVuLayoutData = new GridLayoutData();
		lblChucVuLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblChucVu.setLayoutData(lblChucVuLayoutData);
		grid2.add(lblChucVu);
		txtChucVu = new DscField();
		txtChucVu.setForeground(new Color(0x004080));
		grid2.add(txtChucVu);
		lblNNX = new Label();
		lblNNX.setText("Ngày NX");
		GridLayoutData lblNNXLayoutData = new GridLayoutData();
		lblNNXLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblNNX.setLayoutData(lblNNXLayoutData);
		grid2.add(lblNNX);
		dfNNX = new DscDateField();
		dfNNX.setForeground(new Color(0x004080));
		grid2.add(dfNNX);
		lblDonViKT = new Label();
		lblDonViKT.setText("Mã ĐVKT");
		GridLayoutData lblDonViKTLayoutData = new GridLayoutData();
		lblDonViKTLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblDonViKT.setLayoutData(lblDonViKTLayoutData);
		grid2.add(lblDonViKT);
		txtDonViKT = new DscField();
		txtDonViKT.setForeground(new Color(0x004080));
		grid2.add(txtDonViKT);
		lblLCB = new Label();
		lblLCB.setText("Lương CB");
		GridLayoutData lblLCBLayoutData = new GridLayoutData();
		lblLCBLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblLCB.setLayoutData(lblLCBLayoutData);
		grid2.add(lblLCB);
		txtLCB = new DscField();
		txtLCB.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtLCB.setForeground(new Color(0x004080));
		grid2.add(txtLCB);
		lblLHD = new Label();
		lblLHD.setText("Lương HĐ");
		GridLayoutData lblLHDLayoutData = new GridLayoutData();
		lblLHDLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblLHD.setLayoutData(lblLHDLayoutData);
		grid2.add(lblLHD);
		txtLHD = new DscField();
		txtLHD.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtLHD.setForeground(new Color(0x004080));
		grid2.add(txtLHD);
		lblLCBBQ = new Label();
		lblLCBBQ.setText("Lương CB_BQ");
		GridLayoutData lblLCBBQLayoutData = new GridLayoutData();
		lblLCBBQLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblLCBBQ.setLayoutData(lblLCBBQLayoutData);
		grid2.add(lblLCBBQ);
		txtLCBBQ = new DscField();
		txtLCBBQ.setInputType(DscField.INPUT_TYPE_NUMERIC);
		txtLCBBQ.setForeground(new Color(0x004080));
		grid2.add(txtLCBBQ);
		lblPCCVBQ = new Label();
		lblPCCVBQ.setText("Phụ cấp CV_BQ");
		GridLayoutData lblPCCVBQLayoutData = new GridLayoutData();
		lblPCCVBQLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblPCCVBQ.setLayoutData(lblPCCVBQLayoutData);
		grid2.add(lblPCCVBQ);
		txtPCCVBQ = new DscField();
		txtPCCVBQ.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtPCCVBQ.setForeground(new Color(0x004080));
		grid2.add(txtPCCVBQ);
		lblLuongThangTruoc = new Label();
		lblLuongThangTruoc.setText("Lương tháng trước");
		GridLayoutData lblLuongThangTruocLayoutData = new GridLayoutData();
		lblLuongThangTruocLayoutData.setAlignment(new Alignment(
				Alignment.RIGHT, Alignment.DEFAULT));
		lblLuongThangTruoc.setLayoutData(lblLuongThangTruocLayoutData);
		grid2.add(lblLuongThangTruoc);
		txtLuongThangTruoc = new DscField();
		txtLuongThangTruoc.setInputType(DscField.INPUT_TYPE_INTEGER);
		txtLuongThangTruoc.setForeground(new Color(0x004080));
		grid2.add(txtLuongThangTruoc);
		lblGhiChu = new Label();
		lblGhiChu.setText("Ghi chú");
		GridLayoutData lblGhiChuLayoutData = new GridLayoutData();
		lblGhiChuLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblGhiChu.setLayoutData(lblGhiChuLayoutData);
		grid2.add(lblGhiChu);
		txtGhiChu = new DscField();
		txtGhiChu.setWidth(new Extent(220, Extent.PX));
		txtGhiChu.setForeground(new Color(0x004080));
		grid2.add(txtGhiChu);
		GroupBox groupBox7 = new GroupBox();
		groupBox7.setTitle("Thông tin nghỉ việc");
		rootLayout1.add(groupBox7);
		Grid grid9 = new Grid();
		grid9.setInsets(new Insets(new Extent(1, Extent.PX)));
		groupBox7.add(grid9);
		Label label42 = new Label();
		label42.setText("Tính lương tháng");
		grid9.add(label42);
		Row row1 = new Row();
		grid9.add(row1);
		sfMonth = new SelectField();
		sfMonth.setDisabledBackground(new Color(0xc8c8c8));
		sfMonth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				findEmployee(e);
			}
		});
		row1.add(sfMonth);
		Label label43 = new Label();
		label43.setText("Năm");
		row1.add(label43);
		sfYear = new SelectField();
		sfYear.setDisabledBackground(new Color(0xc8c8c8));
		row1.add(sfYear);
		lblDotTV = new Label();
		lblDotTV.setText("Đợt thôi việc");
		GridLayoutData lblDotTVLayoutData = new GridLayoutData();
		lblDotTVLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblDotTV.setLayoutData(lblDotTVLayoutData);
		grid9.add(lblDotTV);
		dfDotTV = new DscDateField();
		dfDotTV.setForeground(new Color(0x004080));
		grid9.add(dfDotTV);
		lblLoai = new Label();
		lblLoai.setText("Loại");
		GridLayoutData lblLoaiLayoutData = new GridLayoutData();
		lblLoaiLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblLoai.setLayoutData(lblLoaiLayoutData);
		grid9.add(lblLoai);
		sfLoai = new SelectField();
		sfLoai.setWidth(new Extent(155, Extent.PX));
		grid9.add(sfLoai);
		GroupBox groupBox2 = new GroupBox();
		groupBox2.setTitle("Trợ cấp nghỉ việc");
		GridLayoutData groupBox2LayoutData = new GridLayoutData();
		groupBox2LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBox2.setLayoutData(groupBox2LayoutData);
		rootLayout1.add(groupBox2);
		Grid grid3 = new Grid();
		grid3.setInsets(new Insets(new Extent(1, Extent.PX)));
		grid3.setSize(4);
		groupBox2.add(grid3);
		lblSTTC = new Label();
		lblSTTC.setText("Số tháng hưởng trợ cấp");
		grid3.add(lblSTTC);
		txtSTTC = new DscField();
		txtSTTC.setInputType(DscField.INPUT_TYPE_NUMERIC);
		txtSTTC.setWidth(new Extent(50, Extent.PX));
		txtSTTC.setForeground(new Color(0x008080));
		grid3.add(txtSTTC);
		Label label2 = new Label();
		label2.setText("~");
		grid3.add(label2);
		txtTTSTTC = new DscField();
		txtTTSTTC.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		txtTTSTTC.setWidth(new Extent(100, Extent.PX));
		txtTTSTTC.setForeground(new Color(0x800040));
		grid3.add(txtTTSTTC);
		lblSNPN = new Label();
		lblSNPN.setText("Phép năm còn lại");
		grid3.add(lblSNPN);
		txtSNPN = new DscField();
		txtSNPN.setInputType(DscField.INPUT_TYPE_NUMERIC);
		txtSNPN.setWidth(new Extent(50, Extent.PX));
		txtSNPN.setForeground(new Color(0x008080));
		grid3.add(txtSNPN);
		Label label3 = new Label();
		label3.setText("~");
		grid3.add(label3);
		txtTTSNPN = new DscField();
		txtTTSNPN.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		txtTTSNPN.setWidth(new Extent(100, Extent.PX));
		txtTTSNPN.setForeground(new Color(0x800040));
		grid3.add(txtTTSNPN);
		lblSTDB = new Label();
		lblSTDB.setText("Số tháng đền bù");
		grid3.add(lblSTDB);
		txtSTDB = new DscField();
		txtSTDB.setInputType(DscField.INPUT_TYPE_NUMERIC);
		txtSTDB.setWidth(new Extent(50, Extent.PX));
		txtSTDB.setForeground(new Color(0x008080));
		grid3.add(txtSTDB);
		Label label4 = new Label();
		label4.setText("~");
		grid3.add(label4);
		txtTTSTDB = new DscField();
		txtTTSTDB.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		txtTTSTDB.setWidth(new Extent(100, Extent.PX));
		txtTTSTDB.setForeground(new Color(0x800040));
		grid3.add(txtTTSTDB);
		lblNTTH = new Label();
		lblNTTH.setText("Nghỉ trước thời hạn");
		grid3.add(lblNTTH);
		txtNTTH = new DscField();
		txtNTTH.setInputType(DscField.INPUT_TYPE_NUMERIC);
		txtNTTH.setWidth(new Extent(50, Extent.PX));
		txtNTTH.setForeground(new Color(0x008080));
		grid3.add(txtNTTH);
		Label label5 = new Label();
		label5.setText("~");
		grid3.add(label5);
		txtTTNTTH = new DscField();
		txtTTNTTH.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		txtTTNTTH.setWidth(new Extent(100, Extent.PX));
		txtTTNTTH.setForeground(new Color(0x800040));
		grid3.add(txtTTNTTH);
		GroupBox groupBox3 = new GroupBox();
		GridLayoutData groupBox3LayoutData = new GridLayoutData();
		groupBox3LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.BOTTOM));
		groupBox3.setLayoutData(groupBox3LayoutData);
		rootLayout1.add(groupBox3);
		Grid grid4 = new Grid();
		grid4.setInsets(new Insets(new Extent(1, Extent.PX)));
		grid4.setSize(6);
		groupBox3.add(grid4);
		Label label1 = new Label();
		grid4.add(label1);
		lblTongLCB = new Label();
		lblTongLCB.setText("Tổng Lương CB");
		grid4.add(lblTongLCB);
		Label label6 = new Label();
		grid4.add(label6);
		lblTienTangCa = new Label();
		lblTienTangCa.setText("Tiền tăng ca");
		grid4.add(lblTienTangCa);
		Label label7 = new Label();
		grid4.add(label7);
		lblTongPC = new Label();
		lblTongPC.setText("Tổng phụ cấp");
		grid4.add(lblTongPC);
		Label label8 = new Label();
		grid4.add(label8);
		txtTongLCB = new DscField();
		txtTongLCB.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtTongLCB.setWidth(new Extent(100, Extent.PX));
		txtTongLCB.setForeground(new Color(0x800040));
		grid4.add(txtTongLCB);
		Label label9 = new Label();
		label9.setText("+");
		grid4.add(label9);
		txtTienTC = new DscField();
		txtTienTC.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtTienTC.setWidth(new Extent(100, Extent.PX));
		txtTienTC.setForeground(new Color(0x800040));
		grid4.add(txtTienTC);
		Label label10 = new Label();
		label10.setText("+");
		grid4.add(label10);
		txtTongPC = new DscField();
		txtTongPC.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtTongPC.setWidth(new Extent(100, Extent.PX));
		txtTongPC.setForeground(new Color(0x800040));
		grid4.add(txtTongPC);
		Label label11 = new Label();
		grid4.add(label11);
		lblTong = new Label();
		grid4.add(lblTong);
		Label label12 = new Label();
		grid4.add(label12);
		lblKhauTru = new Label();
		lblKhauTru.setText("Tổng khấu trừ");
		grid4.add(lblKhauTru);
		Label label13 = new Label();
		grid4.add(label13);
		lblLuongThangHT = new Label();
		lblLuongThangHT.setText("Lương tháng HT");
		grid4.add(lblLuongThangHT);
		Label label14 = new Label();
		label14.setText("=");
		grid4.add(label14);
		txtTong = new DscField();
		txtTong
				.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		txtTong.setWidth(new Extent(100, Extent.PX));
		txtTong.setForeground(new Color(0x800040));
		grid4.add(txtTong);
		Label label15 = new Label();
		label15.setText("-");
		grid4.add(label15);
		txtTongKhauTru = new DscField();
		txtTongKhauTru.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtTongKhauTru.setWidth(new Extent(100, Extent.PX));
		txtTongKhauTru.setForeground(new Color(0x800040));
		grid4.add(txtTongKhauTru);
		Label label16 = new Label();
		label16.setText("=");
		grid4.add(label16);
		txtLuongThangHT = new DscField();
		txtLuongThangHT.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtLuongThangHT.setFont(new Font(null, Font.BOLD, new Extent(10,
				Extent.PT)));
		txtLuongThangHT.setWidth(new Extent(100, Extent.PX));
		txtLuongThangHT.setForeground(new Color(0x800040));
		grid4.add(txtLuongThangHT);
		Label label17 = new Label();
		grid4.add(label17);
		lblTongTroCap = new Label();
		lblTongTroCap.setText("Tổng trợ cấp");
		GridLayoutData lblTongTroCapLayoutData = new GridLayoutData();
		lblTongTroCapLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		lblTongTroCap.setLayoutData(lblTongTroCapLayoutData);
		grid4.add(lblTongTroCap);
		Label label18 = new Label();
		grid4.add(label18);
		lblThucNhan = new Label();
		lblThucNhan.setText("Thực nhận");
		GridLayoutData lblThucNhanLayoutData = new GridLayoutData();
		lblThucNhanLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		lblThucNhanLayoutData.setColumnSpan(3);
		lblThucNhan.setLayoutData(lblThucNhanLayoutData);
		grid4.add(lblThucNhan);
		Label label20 = new Label();
		label20.setText("+");
		grid4.add(label20);
		txtTongTroCap = new DscField();
		txtTongTroCap.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtTongTroCap.setFont(new Font(null, Font.BOLD, new Extent(10,
				Extent.PT)));
		txtTongTroCap.setWidth(new Extent(100, Extent.PX));
		txtTongTroCap.setForeground(new Color(0x800040));
		grid4.add(txtTongTroCap);
		Label label21 = new Label();
		label21.setText("=");
		grid4.add(label21);
		txtThucNhan = new DscField();
		txtThucNhan.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtThucNhan
				.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		txtThucNhan.setWidth(new Extent(215, Extent.PX));
		txtThucNhan.setForeground(Color.RED);
		GridLayoutData txtThucNhanLayoutData = new GridLayoutData();
		txtThucNhanLayoutData.setColumnSpan(3);
		txtThucNhan.setLayoutData(txtThucNhanLayoutData);
		grid4.add(txtThucNhan);
		chiTietThoiGianContent = new ContentPane();
		TabPaneLayoutData chiTietThoiGianContentLayoutData = new TabPaneLayoutData();
		chiTietThoiGianContentLayoutData
				.setTitle("Chi tiết thời gian làm việc");
		chiTietThoiGianContent.setLayoutData(chiTietThoiGianContentLayoutData);
		tabPane.add(chiTietThoiGianContent);
		rootLayout2 = new Grid();
		rootLayout2.setInsets(new Insets(new Extent(2, Extent.PX)));
		chiTietThoiGianContent.add(rootLayout2);
		GroupBox groupBox4 = new GroupBox();
		groupBox4.setTitle("Biểu thời gian");
		groupBox4.setWidth(new Extent(188, Extent.PX));
		GridLayoutData groupBox4LayoutData = new GridLayoutData();
		groupBox4LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBox4LayoutData.setRowSpan(2);
		groupBox4.setLayoutData(groupBox4LayoutData);
		rootLayout2.add(groupBox4);
		Grid grid6 = new Grid();
		grid6.setColumnWidth(2, new Extent(60, Extent.PX));
		grid6.setSize(3);
		groupBox4.add(grid6);
		lblSoNgayLam = new Label();
		lblSoNgayLam.setText("Số ngày làm");
		GridLayoutData lblSoNgayLamLayoutData = new GridLayoutData();
		lblSoNgayLamLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblSoNgayLam.setLayoutData(lblSoNgayLamLayoutData);
		grid6.add(lblSoNgayLam);
		txtSoNgayLam = new DscField();
		txtSoNgayLam.setWidth(new Extent(30, Extent.PX));
		txtSoNgayLam.setForeground(new Color(0x000080));
		grid6.add(txtSoNgayLam);
		Label label22 = new Label();
		label22.setLineWrap(true);
		label22.setText("Tăng ca ngoài");
		label22.setTextAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		GridLayoutData label22LayoutData = new GridLayoutData();
		label22LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		label22LayoutData.setRowSpan(2);
		label22.setLayoutData(label22LayoutData);
		grid6.add(label22);
		lblSoDemLam = new Label();
		lblSoDemLam.setText("Số đêm làm");
		GridLayoutData lblSoDemLamLayoutData = new GridLayoutData();
		lblSoDemLamLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblSoDemLam.setLayoutData(lblSoDemLamLayoutData);
		grid6.add(lblSoDemLam);
		txtSoDemLam = new DscField();
		txtSoDemLam.setWidth(new Extent(30, Extent.PX));
		txtSoDemLam.setForeground(new Color(0x000080));
		grid6.add(txtSoDemLam);
		lblTangCaNgay = new Label();
		lblTangCaNgay.setText("Tăng ca ngày");
		GridLayoutData lblTangCaNgayLayoutData = new GridLayoutData();
		lblTangCaNgayLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblTangCaNgay.setLayoutData(lblTangCaNgayLayoutData);
		grid6.add(lblTangCaNgay);
		txtTangCaNgay = new DscField();
		txtTangCaNgay.setWidth(new Extent(30, Extent.PX));
		txtTangCaNgay.setForeground(new Color(0x000080));
		grid6.add(txtTangCaNgay);
		txtTangCaNgayNgoai = new DscField();
		txtTangCaNgayNgoai.setInputType(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtTangCaNgayNgoai.setWidth(new Extent(30, Extent.PX));
		txtTangCaNgayNgoai.setForeground(new Color(0x0000a0));
		GridLayoutData txtTangCaNgayNgoaiLayoutData = new GridLayoutData();
		txtTangCaNgayNgoaiLayoutData.setAlignment(new Alignment(
				Alignment.CENTER, Alignment.DEFAULT));
		txtTangCaNgayNgoai.setLayoutData(txtTangCaNgayNgoaiLayoutData);
		grid6.add(txtTangCaNgayNgoai);
		lblTangCaDem = new Label();
		lblTangCaDem.setText("Tăng ca đêm");
		GridLayoutData lblTangCaDemLayoutData = new GridLayoutData();
		lblTangCaDemLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblTangCaDem.setLayoutData(lblTangCaDemLayoutData);
		grid6.add(lblTangCaDem);
		txtTangCaDem = new DscField();
		txtTangCaDem.setWidth(new Extent(30, Extent.PX));
		txtTangCaDem.setForeground(new Color(0x000080));
		grid6.add(txtTangCaDem);
		txtTangCaDemNgoai = new DscField();
		txtTangCaDemNgoai.setInputType(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtTangCaDemNgoai.setWidth(new Extent(30, Extent.PX));
		txtTangCaDemNgoai.setForeground(new Color(0x0000a0));
		GridLayoutData txtTangCaDemNgoaiLayoutData = new GridLayoutData();
		txtTangCaDemNgoaiLayoutData.setAlignment(new Alignment(
				Alignment.CENTER, Alignment.DEFAULT));
		txtTangCaDemNgoai.setLayoutData(txtTangCaDemNgoaiLayoutData);
		grid6.add(txtTangCaDemNgoai);
		lblTangCaCN = new Label();
		lblTangCaCN.setText("Tăng ca CN");
		GridLayoutData lblTangCaCNLayoutData = new GridLayoutData();
		lblTangCaCNLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblTangCaCN.setLayoutData(lblTangCaCNLayoutData);
		grid6.add(lblTangCaCN);
		txtTangCaCN = new DscField();
		txtTangCaCN.setWidth(new Extent(30, Extent.PX));
		txtTangCaCN.setForeground(new Color(0x000080));
		grid6.add(txtTangCaCN);
		txtTangCaCNNgoai = new DscField();
		txtTangCaCNNgoai.setInputType(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtTangCaCNNgoai.setWidth(new Extent(30, Extent.PX));
		txtTangCaCNNgoai.setForeground(new Color(0x0000a0));
		GridLayoutData txtTangCaCNNgoaiLayoutData = new GridLayoutData();
		txtTangCaCNNgoaiLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtTangCaCNNgoai.setLayoutData(txtTangCaCNNgoaiLayoutData);
		grid6.add(txtTangCaCNNgoai);
		lblTangCaLe = new Label();
		lblTangCaLe.setText("Tăng ca lễ");
		GridLayoutData lblTangCaLeLayoutData = new GridLayoutData();
		lblTangCaLeLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblTangCaLe.setLayoutData(lblTangCaLeLayoutData);
		grid6.add(lblTangCaLe);
		txtTangCaLe = new DscField();
		txtTangCaLe.setWidth(new Extent(30, Extent.PX));
		txtTangCaLe.setForeground(new Color(0x000080));
		grid6.add(txtTangCaLe);
		txtTangCaLeNgoai = new DscField();
		txtTangCaLeNgoai.setInputType(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtTangCaLeNgoai.setWidth(new Extent(30, Extent.PX));
		txtTangCaLeNgoai.setForeground(new Color(0x0000a0));
		GridLayoutData txtTangCaLeNgoaiLayoutData = new GridLayoutData();
		txtTangCaLeNgoaiLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtTangCaLeNgoai.setLayoutData(txtTangCaLeNgoaiLayoutData);
		grid6.add(txtTangCaLeNgoai);
		lblSoDemTangCa = new Label();
		lblSoDemTangCa.setText("Số đêm tc");
		GridLayoutData lblSoDemTangCaLayoutData = new GridLayoutData();
		lblSoDemTangCaLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblSoDemTangCa.setLayoutData(lblSoDemTangCaLayoutData);
		grid6.add(lblSoDemTangCa);
		txtSoDemTC = new DscField();
		txtSoDemTC.setWidth(new Extent(30, Extent.PX));
		txtSoDemTC.setForeground(new Color(0x000080));
		grid6.add(txtSoDemTC);
		txtSoDemTCNgoai = new DscField();
		txtSoDemTCNgoai.setInputType(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtSoDemTCNgoai.setWidth(new Extent(30, Extent.PX));
		txtSoDemTCNgoai.setForeground(new Color(0x0000a0));
		GridLayoutData txtSoDemTCNgoaiLayoutData = new GridLayoutData();
		txtSoDemTCNgoaiLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtSoDemTCNgoai.setLayoutData(txtSoDemTCNgoaiLayoutData);
		grid6.add(txtSoDemTCNgoai);
		lblKyTen = new Label();
		lblKyTen.setText("Ký tên");
		GridLayoutData lblKyTenLayoutData = new GridLayoutData();
		lblKyTenLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblKyTen.setLayoutData(lblKyTenLayoutData);
		grid6.add(lblKyTen);
		txtKyTen = new DscField();
		txtKyTen.setWidth(new Extent(30, Extent.PX));
		txtKyTen.setForeground(new Color(0x000080));
		grid6.add(txtKyTen);
		Label label24 = new Label();
		grid6.add(label24);
		lblTreSom = new Label();
		lblTreSom.setText("Trể/sớm");
		GridLayoutData lblTreSomLayoutData = new GridLayoutData();
		lblTreSomLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblTreSom.setLayoutData(lblTreSomLayoutData);
		grid6.add(lblTreSom);
		txtTreSom = new DscField();
		txtTreSom.setWidth(new Extent(30, Extent.PX));
		txtTreSom.setForeground(new Color(0x000080));
		grid6.add(txtTreSom);
		btnBuLuong = new Button();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnDelete.gif");
		btnBuLuong.setIcon(imageReference1);
		btnBuLuong.setHeight(new Extent(16, Extent.PX));
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnDeleteD.gif");
		btnBuLuong.setDisabledIcon(imageReference2);
		btnBuLuong.setWidth(new Extent(16, Extent.PX));
		btnBuLuong.setToolTipText("Loại bỏ ngày công & phép");
		GridLayoutData btnBuLuongLayoutData = new GridLayoutData();
		btnBuLuongLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		btnBuLuong.setLayoutData(btnBuLuongLayoutData);
		btnBuLuong.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeWPData(e);
			}
		});
		grid6.add(btnBuLuong);
		Label label19 = new Label();
		label19.setText("--------Phép---------");
		label19.setForeground(new Color(0x004000));
		GridLayoutData label19LayoutData = new GridLayoutData();
		label19LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		label19LayoutData.setColumnSpan(3);
		label19.setLayoutData(label19LayoutData);
		grid6.add(label19);
		lblCoPhep = new Label();
		lblCoPhep.setText("Có phép");
		GridLayoutData lblCoPhepLayoutData = new GridLayoutData();
		lblCoPhepLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblCoPhep.setLayoutData(lblCoPhepLayoutData);
		grid6.add(lblCoPhep);
		txtCoPhep = new DscField();
		txtCoPhep.setWidth(new Extent(30, Extent.PX));
		txtCoPhep.setForeground(new Color(0x000080));
		grid6.add(txtCoPhep);
		Label label26 = new Label();
		grid6.add(label26);
		lblCoLuong = new Label();
		lblCoLuong.setText("Có lương");
		GridLayoutData lblCoLuongLayoutData = new GridLayoutData();
		lblCoLuongLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblCoLuong.setLayoutData(lblCoLuongLayoutData);
		grid6.add(lblCoLuong);
		txtCoLuong = new DscField();
		txtCoLuong.setWidth(new Extent(30, Extent.PX));
		txtCoLuong.setForeground(new Color(0x000080));
		grid6.add(txtCoLuong);
		Label label27 = new Label();
		grid6.add(label27);
		lblPhepBenh = new Label();
		lblPhepBenh.setText("Phép bệnh");
		GridLayoutData lblPhepBenhLayoutData = new GridLayoutData();
		lblPhepBenhLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblPhepBenh.setLayoutData(lblPhepBenhLayoutData);
		grid6.add(lblPhepBenh);
		txtPhepBenh = new DscField();
		txtPhepBenh.setWidth(new Extent(30, Extent.PX));
		txtPhepBenh.setForeground(new Color(0x000080));
		grid6.add(txtPhepBenh);
		Label label28 = new Label();
		grid6.add(label28);
		lblKhangCong = new Label();
		lblKhangCong.setText("Có phép");
		GridLayoutData lblKhangCongLayoutData = new GridLayoutData();
		lblKhangCongLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblKhangCong.setLayoutData(lblKhangCongLayoutData);
		grid6.add(lblKhangCong);
		txtKhangCong = new DscField();
		txtKhangCong.setWidth(new Extent(30, Extent.PX));
		txtKhangCong.setForeground(new Color(0x000080));
		grid6.add(txtKhangCong);
		Label label29 = new Label();
		grid6.add(label29);
		lblDuongSuc = new Label();
		lblDuongSuc.setText("Dưỡng sức");
		GridLayoutData lblDuongSucLayoutData = new GridLayoutData();
		lblDuongSucLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblDuongSuc.setLayoutData(lblDuongSucLayoutData);
		grid6.add(lblDuongSuc);
		txtDuongSuc = new DscField();
		txtDuongSuc.setWidth(new Extent(30, Extent.PX));
		txtDuongSuc.setForeground(new Color(0x000080));
		grid6.add(txtDuongSuc);
		Label label30 = new Label();
		grid6.add(label30);
		lblNghiSan = new Label();
		lblNghiSan.setText("Nghỉ sản");
		GridLayoutData lblNghiSanLayoutData = new GridLayoutData();
		lblNghiSanLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblNghiSan.setLayoutData(lblNghiSanLayoutData);
		grid6.add(lblNghiSan);
		txtNghiSan = new DscField();
		txtNghiSan.setWidth(new Extent(30, Extent.PX));
		txtNghiSan.setForeground(new Color(0x000080));
		grid6.add(txtNghiSan);
		Label label31 = new Label();
		grid6.add(label31);
		GroupBox groupBox5 = new GroupBox();
		groupBox5.setTitle("Thưởng / Phụ cấp");
		groupBox5.setHeight(new Extent(210, Extent.PX));
		groupBox5.setWidth(new Extent(555, Extent.PX));
		GridLayoutData groupBox5LayoutData = new GridLayoutData();
		groupBox5LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBox5.setLayoutData(groupBox5LayoutData);
		rootLayout2.add(groupBox5);
		Grid grid7 = new Grid();
		grid7.setSize(6);
		groupBox5.add(grid7);
		lblTienThuong = new Label();
		lblTienThuong.setText("Tiền thưởng");
		GridLayoutData lblTienThuongLayoutData = new GridLayoutData();
		lblTienThuongLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblTienThuong.setLayoutData(lblTienThuongLayoutData);
		grid7.add(lblTienThuong);
		txtTienThuong = new DscField();
		txtTienThuong.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtTienThuong.setWidth(new Extent(80, Extent.PX));
		txtTienThuong.setForeground(new Color(0x004080));
		grid7.add(txtTienThuong);
		Label label33 = new Label();
		label33.setText("~");
		grid7.add(label33);
		txtTienThuongTT = new DscField();
		txtTienThuongTT.setWidth(new Extent(80, Extent.PX));
		txtTienThuongTT.setForeground(new Color(0x800040));
		grid7.add(txtTienThuongTT);
		lblChuyenCan = new Label();
		lblChuyenCan.setText("Chuyên cần");
		GridLayoutData lblChuyenCanLayoutData = new GridLayoutData();
		lblChuyenCanLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblChuyenCan.setLayoutData(lblChuyenCanLayoutData);
		grid7.add(lblChuyenCan);
		txtChuyenCan = new DscField();
		txtChuyenCan.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtChuyenCan.setWidth(new Extent(80, Extent.PX));
		txtChuyenCan.setForeground(new Color(0x004080));
		grid7.add(txtChuyenCan);
		lblPCCV1 = new Label();
		lblPCCV1.setText("PC chức vụ");
		GridLayoutData lblPCCV1LayoutData = new GridLayoutData();
		lblPCCV1LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblPCCV1.setLayoutData(lblPCCV1LayoutData);
		grid7.add(lblPCCV1);
		txtPCCVu = new DscField();
		txtPCCVu.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtPCCVu.setWidth(new Extent(80, Extent.PX));
		txtPCCVu.setForeground(new Color(0x004080));
		grid7.add(txtPCCVu);
		Label label34 = new Label();
		label34.setText("~");
		grid7.add(label34);
		txtPCCVuTT = new DscField();
		txtPCCVuTT.setWidth(new Extent(80, Extent.PX));
		txtPCCVuTT.setForeground(new Color(0x800040));
		grid7.add(txtPCCVuTT);
		lblPCTC = new Label();
		lblPCTC.setText("PC tiền cơm");
		GridLayoutData lblPCTCLayoutData = new GridLayoutData();
		lblPCTCLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblPCTC.setLayoutData(lblPCTCLayoutData);
		grid7.add(lblPCTC);
		txtPCTC = new DscField();
		txtPCTC.setInputType(DscField.INPUT_TYPE_TEXT);
		txtPCTC.setWidth(new Extent(80, Extent.PX));
		txtPCTC.setForeground(new Color(0x004080));
		grid7.add(txtPCTC);
		lblPCCViec = new Label();
		lblPCCViec.setText("PC công việc");
		GridLayoutData lblPCCViecLayoutData = new GridLayoutData();
		lblPCCViecLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblPCCViec.setLayoutData(lblPCCViecLayoutData);
		grid7.add(lblPCCViec);
		txtPCCViec = new DscField();
		txtPCCViec.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtPCCViec.setWidth(new Extent(80, Extent.PX));
		txtPCCViec.setForeground(new Color(0x004080));
		grid7.add(txtPCCViec);
		Label label35 = new Label();
		label35.setText("~");
		grid7.add(label35);
		txtPCCViecTT = new DscField();
		txtPCCViecTT.setWidth(new Extent(80, Extent.PX));
		txtPCCViecTT.setForeground(new Color(0x800040));
		grid7.add(txtPCCViecTT);
		lblChuyenCanTC = new Label();
		lblChuyenCanTC.setText("Chuyên cần TC");
		GridLayoutData lblChuyenCanTCLayoutData = new GridLayoutData();
		lblChuyenCanTCLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblChuyenCanTC.setLayoutData(lblChuyenCanTCLayoutData);
		grid7.add(lblChuyenCanTC);
		txtChuyenCanTC = new DscField();
		txtChuyenCanTC.setWidth(new Extent(80, Extent.PX));
		txtChuyenCanTC.setForeground(new Color(0x004080));
		grid7.add(txtChuyenCanTC);
		lblPCSH = new Label();
		lblPCSH.setText("PC sinh hoạt");
		GridLayoutData lblPCSHLayoutData = new GridLayoutData();
		lblPCSHLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblPCSH.setLayoutData(lblPCSHLayoutData);
		grid7.add(lblPCSH);
		txtPCSH = new DscField();
		txtPCSH.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtPCSH.setWidth(new Extent(80, Extent.PX));
		txtPCSH.setForeground(new Color(0x004080));
		grid7.add(txtPCSH);
		Label label36 = new Label();
		label36.setText("+~");
		GridLayoutData label36LayoutData = new GridLayoutData();
		label36LayoutData.setRowSpan(2);
		label36.setLayoutData(label36LayoutData);
		grid7.add(label36);
		txtPCSHTT = new DscField();
		txtPCSHTT.setWidth(new Extent(80, Extent.PX));
		txtPCSHTT.setForeground(new Color(0x800040));
		GridLayoutData txtPCSHTTLayoutData = new GridLayoutData();
		txtPCSHTTLayoutData.setRowSpan(2);
		txtPCSHTT.setLayoutData(txtPCSHTTLayoutData);
		grid7.add(txtPCSHTT);
		lblCLPCTC = new Label();
		lblCLPCTC.setText("Chênh lệch PCTC");
		GridLayoutData lblCLPCTCLayoutData = new GridLayoutData();
		lblCLPCTCLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblCLPCTC.setLayoutData(lblCLPCTCLayoutData);
		grid7.add(lblCLPCTC);
		txtCLPCTC = new DscField();
		txtCLPCTC.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtCLPCTC.setWidth(new Extent(80, Extent.PX));
		txtCLPCTC.setForeground(new Color(0x004080));
		grid7.add(txtCLPCTC);
		lblPC8H = new Label();
		lblPC8H.setText("PC làm 8H");
		GridLayoutData lblPC8HLayoutData = new GridLayoutData();
		lblPC8HLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblPC8H.setLayoutData(lblPC8HLayoutData);
		grid7.add(lblPC8H);
		txtPC8H = new DscField();
		txtPC8H.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtPC8H.setWidth(new Extent(80, Extent.PX));
		txtPC8H.setForeground(new Color(0x004080));
		grid7.add(txtPC8H);
		lblBuBH = new Label();
		lblBuBH.setText("Bù BH 19%");
		GridLayoutData lblBuBHLayoutData = new GridLayoutData();
		lblBuBHLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblBuBH.setLayoutData(lblBuBHLayoutData);
		grid7.add(lblBuBH);
		txtBuBH = new DscField();
		txtBuBH.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtBuBH.setWidth(new Extent(80, Extent.PX));
		txtBuBH.setForeground(new Color(0x004080));
		grid7.add(txtBuBH);
		lblBuLuong = new Label();
		lblBuLuong.setText("Bù lương");
		GridLayoutData lblBuLuongLayoutData = new GridLayoutData();
		lblBuLuongLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblBuLuong.setLayoutData(lblBuLuongLayoutData);
		grid7.add(lblBuLuong);
		txtBuLuong = new DscField();
		txtBuLuong.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtBuLuong.setWidth(new Extent(80, Extent.PX));
		txtBuLuong.setForeground(new Color(0x004080));
		grid7.add(txtBuLuong);
		Label label23 = new Label();
		grid7.add(label23);
		Label label32 = new Label();
		grid7.add(label32);
		lblSoNguoiPhuThuoc = new Label();
		lblSoNguoiPhuThuoc.setText("Người phụ thuộc");
		GridLayoutData lblSoNguoiPhuThuocLayoutData = new GridLayoutData();
		lblSoNguoiPhuThuocLayoutData.setAlignment(new Alignment(
				Alignment.RIGHT, Alignment.DEFAULT));
		lblSoNguoiPhuThuoc.setLayoutData(lblSoNguoiPhuThuocLayoutData);
		grid7.add(lblSoNguoiPhuThuoc);
		txtNguoiPhuThuoc = new DscField();
		txtNguoiPhuThuoc.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtNguoiPhuThuoc.setWidth(new Extent(80, Extent.PX));
		txtNguoiPhuThuoc.setForeground(new Color(0x004080));
		grid7.add(txtNguoiPhuThuoc);
		lblBuBHNuaThangDau = new Label();
		lblBuBHNuaThangDau.setText("Bù BH 1/2 (1)");
		GridLayoutData lblBuBHNuaThangDauLayoutData = new GridLayoutData();
		lblBuBHNuaThangDauLayoutData.setAlignment(new Alignment(
				Alignment.RIGHT, Alignment.DEFAULT));
		lblBuBHNuaThangDau.setLayoutData(lblBuBHNuaThangDauLayoutData);
		grid7.add(lblBuBHNuaThangDau);
		txtBuBHNuaThangDau = new DscField();
		txtBuBHNuaThangDau.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtBuBHNuaThangDau.setWidth(new Extent(80, Extent.PX));
		txtBuBHNuaThangDau.setForeground(new Color(0x004080));
		grid7.add(txtBuBHNuaThangDau);
		Label label37 = new Label();
		grid7.add(label37);
		Label label38 = new Label();
		grid7.add(label38);
		lblBuBHNuaThangSau = new Label();
		lblBuBHNuaThangSau.setText("Bù BH 1/2 (2)");
		GridLayoutData lblBuBHNuaThangSauLayoutData = new GridLayoutData();
		lblBuBHNuaThangSauLayoutData.setAlignment(new Alignment(
				Alignment.RIGHT, Alignment.DEFAULT));
		lblBuBHNuaThangSau.setLayoutData(lblBuBHNuaThangSauLayoutData);
		grid7.add(lblBuBHNuaThangSau);
		txtBuBHNuaThangSau = new DscField();
		txtBuBHNuaThangSau.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtBuBHNuaThangSau.setWidth(new Extent(80, Extent.PX));
		txtBuBHNuaThangSau.setForeground(new Color(0x004080));
		grid7.add(txtBuBHNuaThangSau);
		GroupBox groupBox6 = new GroupBox();
		groupBox6.setTitle("Khấu trừ");
		groupBox6.setHeight(new Extent(100, Extent.PERCENT));
		groupBox6.setWidth(new Extent(555, Extent.PX));
		GridLayoutData groupBox6LayoutData = new GridLayoutData();
		groupBox6LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.BOTTOM));
		groupBox6.setLayoutData(groupBox6LayoutData);
		rootLayout2.add(groupBox6);
		Grid grid8 = new Grid();
		grid8.setSize(6);
		groupBox6.add(grid8);
		lblBHXH = new Label();
		lblBHXH.setText("BHXH");
		GridLayoutData lblBHXHLayoutData = new GridLayoutData();
		lblBHXHLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblBHXH.setLayoutData(lblBHXHLayoutData);
		grid8.add(lblBHXH);
		txtBHXH = new DscField();
		txtBHXH.setInputType(DscField.INPUT_TYPE_INTEGER);
		txtBHXH.setWidth(new Extent(80, Extent.PX));
		txtBHXH.setForeground(new Color(0x004080));
		grid8.add(txtBHXH);
		Label label39 = new Label();
		label39.setText("~");
		GridLayoutData label39LayoutData = new GridLayoutData();
		label39LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		label39.setLayoutData(label39LayoutData);
		grid8.add(label39);
		txtBHXHTT = new DscField();
		txtBHXHTT.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		txtBHXHTT.setInputType(DscField.INPUT_TYPE_INTEGER);
		txtBHXHTT.setWidth(new Extent(80, Extent.PX));
		txtBHXHTT.setForeground(new Color(0x800080));
		grid8.add(txtBHXHTT);
		lblTamUng = new Label();
		lblTamUng.setText("Tạm ứng");
		GridLayoutData lblTamUngLayoutData = new GridLayoutData();
		lblTamUngLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblTamUng.setLayoutData(lblTamUngLayoutData);
		grid8.add(lblTamUng);
		txtTamUng = new DscField();
		txtTamUng.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtTamUng.setWidth(new Extent(80, Extent.PX));
		txtTamUng.setForeground(new Color(0x004080));
		grid8.add(txtTamUng);
		lblBHYT = new Label();
		lblBHYT.setText("BHYT");
		GridLayoutData lblBHYTLayoutData = new GridLayoutData();
		lblBHYTLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblBHYT.setLayoutData(lblBHYTLayoutData);
		grid8.add(lblBHYT);
		txtBHYT = new DscField();
		txtBHYT.setInputType(DscField.INPUT_TYPE_INTEGER);
		txtBHYT.setWidth(new Extent(80, Extent.PX));
		txtBHYT.setForeground(new Color(0x004080));
		grid8.add(txtBHYT);
		Label label40 = new Label();
		label40.setText("~");
		GridLayoutData label40LayoutData = new GridLayoutData();
		label40LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		label40.setLayoutData(label40LayoutData);
		grid8.add(label40);
		txtBHYTTT = new DscField();
		txtBHYTTT.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		txtBHYTTT.setInputType(DscField.INPUT_TYPE_INTEGER);
		txtBHYTTT.setWidth(new Extent(80, Extent.PX));
		txtBHYTTT.setForeground(new Color(0x800080));
		grid8.add(txtBHYTTT);
		lblDoanPhi = new Label();
		lblDoanPhi.setText("Đoàn phí");
		GridLayoutData lblDoanPhiLayoutData = new GridLayoutData();
		lblDoanPhiLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblDoanPhi.setLayoutData(lblDoanPhiLayoutData);
		grid8.add(lblDoanPhi);
		txtDoanPhi = new DscField();
		txtDoanPhi.setInputType(DscField.INPUT_TYPE_INTEGER);
		txtDoanPhi.setWidth(new Extent(80, Extent.PX));
		txtDoanPhi.setForeground(new Color(0x004080));
		grid8.add(txtDoanPhi);
		lblBHTN = new Label();
		lblBHTN.setText("BHTN");
		GridLayoutData lblBHTNLayoutData = new GridLayoutData();
		lblBHTNLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblBHTN.setLayoutData(lblBHTNLayoutData);
		grid8.add(lblBHTN);
		txtBHTN = new DscField();
		txtBHTN.setInputType(DscField.INPUT_TYPE_INTEGER);
		txtBHTN.setWidth(new Extent(80, Extent.PX));
		txtBHTN.setForeground(new Color(0x004080));
		grid8.add(txtBHTN);
		Label label41 = new Label();
		label41.setText("~");
		GridLayoutData label41LayoutData = new GridLayoutData();
		label41LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		label41.setLayoutData(label41LayoutData);
		grid8.add(label41);
		txtBHTNTT = new DscField();
		txtBHTNTT.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		txtBHTNTT.setInputType(DscField.INPUT_TYPE_INTEGER);
		txtBHTNTT.setWidth(new Extent(80, Extent.PX));
		txtBHTNTT.setForeground(new Color(0x800080));
		grid8.add(txtBHTNTT);
		lblSTNV = new Label();
		lblSTNV.setText("STNV");
		GridLayoutData lblSTNVLayoutData = new GridLayoutData();
		lblSTNVLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblSTNV.setLayoutData(lblSTNVLayoutData);
		grid8.add(lblSTNV);
		txtSTNV = new DscField();
		txtSTNV.setInputType(DscField.INPUT_TYPE_INTEGER);
		txtSTNV.setWidth(new Extent(80, Extent.PX));
		txtSTNV.setForeground(new Color(0x004080));
		grid8.add(txtSTNV);
		lblBuBHYT = new Label();
		lblBuBHYT.setText("Bù BHYT");
		GridLayoutData lblBuBHYTLayoutData = new GridLayoutData();
		lblBuBHYTLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblBuBHYT.setLayoutData(lblBuBHYTLayoutData);
		grid8.add(lblBuBHYT);
		txtBuBHYT = new DscField();
		txtBuBHYT.setInputType(DscField.INPUT_TYPE_INTEGER);
		txtBuBHYT.setWidth(new Extent(80, Extent.PX));
		txtBuBHYT.setForeground(new Color(0x004080));
		grid8.add(txtBuBHYT);
		lblThuBHYT = new Label();
		lblThuBHYT.setText("Thu BHYT");
		GridLayoutData lblThuBHYTLayoutData = new GridLayoutData();
		lblThuBHYTLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblThuBHYT.setLayoutData(lblThuBHYTLayoutData);
		grid8.add(lblThuBHYT);
		txtThuBHYT = new DscField();
		txtThuBHYT.setInputType(DscField.INPUT_TYPE_INTEGER);
		txtThuBHYT.setWidth(new Extent(80, Extent.PX));
		txtThuBHYT.setForeground(new Color(0x004080));
		grid8.add(txtThuBHYT);
		lblThueTN = new Label();
		lblThueTN.setText("Thuế thu nhập");
		GridLayoutData lblThueTNLayoutData = new GridLayoutData();
		lblThueTNLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblThueTN.setLayoutData(lblThueTNLayoutData);
		grid8.add(lblThueTN);
		txtThueTN = new DscField();
		txtThueTN.setInputType(DscField.INPUT_TYPE_INTEGER);
		txtThueTN.setWidth(new Extent(80, Extent.PX));
		txtThueTN.setForeground(new Color(0x004080));
		grid8.add(txtThueTN);
		lblBuThuBHYT = new Label();
		lblBuThuBHYT.setText("Bù thu BHYT");
		GridLayoutData lblBuThuBHYTLayoutData = new GridLayoutData();
		lblBuThuBHYTLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblBuThuBHYT.setLayoutData(lblBuThuBHYTLayoutData);
		grid8.add(lblBuThuBHYT);
		txtBuThuBHYT = new DscField();
		txtBuThuBHYT.setInputType(DscField.INPUT_TYPE_INTEGER);
		txtBuThuBHYT.setWidth(new Extent(80, Extent.PX));
		txtBuThuBHYT.setForeground(new Color(0x004080));
		grid8.add(txtBuThuBHYT);
		lblKhauTruKhac = new Label();
		lblKhauTruKhac.setText("KT Khác");
		GridLayoutData lblKhauTruKhacLayoutData = new GridLayoutData();
		lblKhauTruKhacLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblKhauTruKhac.setLayoutData(lblKhauTruKhacLayoutData);
		grid8.add(lblKhauTruKhac);
		txtKhauTruKhac = new DscField();
		txtKhauTruKhac.setInputType(DscField.INPUT_TYPE_INTEGER);
		txtKhauTruKhac.setWidth(new Extent(80, Extent.PX));
		txtKhauTruKhac.setForeground(new Color(0x004080));
		grid8.add(txtKhauTruKhac);
	}

	
}
