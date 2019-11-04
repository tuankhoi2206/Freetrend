package ds.program.fvhr.ui.salary;

import java.util.Calendar;
import java.util.ResourceBundle;

import ds.program.fvhr.domain.ATT200000;
import ds.program.fvhr.domain.ATTENDANTDB;
import ds.program.fvhr.domain.N_DEPARTMENT;
import dsc.echo2app.component.binder.UIDataObjectBinder;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import nextapp.echo2.app.Grid;
import echopointng.GroupBox;
import fv.components.JDataContent;
import fv.util.FVGenericInfo;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Border;
import dsc.echo2app.component.DscField;

public class AwesomeSalaryDataContent extends JDataContent {

	private ResourceBundle resourceBundle;
	private Label lblHoTen;
	private TextField txtHoTen;
	private Label lblSoThe;
	private TextField txtSoThe;
	private Label lblChucVu;
	private TextField txtChucVu;
	private Label lblLCB;
	private TextField txtLCB;
	private Label lblLHD;
	private TextField txtLHD;
	private Label lblDonVi;
	private TextField txtDonVi;
	private Label lblSoNgayLam;
	private TextField txtSoNgayLam;
	private Label lblSoDemLam;
	private TextField txtSoDemLam;
	private Label lblTangCaNgay;
	private TextField txtTangCaNgay;
	private Label lblTangCaDem;
	private TextField txtTangCaDem;	
	private Label lblTangCaCN;
	private TextField txtTangCaCN;
	private Label lblTangCaLe;
	private TextField txtTangCaLe;
	private Label lblSoDemTC;
	private TextField txtSoDemTC;
	private Label lblKyTen;
	private TextField txtKyTen;
	private Label lblTreSom;
	private TextField txtTreSom;
	private Label lblCoPhep;
	private TextField txtCoPhep;
	private Label lblCoLuong;
	private TextField txtCoLuong;
	private Label lblPhepBenh;
	private TextField txtPhepBenh;
	private Label lblKhangCong;
	private TextField txtKhangCong;
	private Label lblDuongSuc;
	private TextField txtDuongSuc;
	private Label lblNghiSan;
	private TextField txtNghiSan;
	private Label lblTienThuong;
	private TextField txtTienThuong;
	private TextField txtTienThuongTT;
	private Label lblBuBH;
	private TextField txtBuBH;
	private Label lblPCCV;
	private TextField txtPCCV;
	private TextField txtPCCVTT;
	private Label lblChuyenCan;
	private TextField txtChuyenCan;
	private Label lblPCCViec;
	private TextField txtPCCViec;
	private TextField txtPCCViecTT;
	private Label lblPCTienCom;
	private TextField txtPCTienCom;
	private Label lblPCSinhHoat;
	private TextField txtPCSinhHoat;
	private TextField txtPCSinhHoatTT;
	private Label lblChuyenCanTC;
	private TextField txtChuyenCanTC;
	private Label lblPC8H;
	private TextField txtPC8H;
	private Label lblCLPCTC;
	private TextField txtCLPCTC;
	private Label lblBuLuong;
	private TextField txtBuLuong;
	private Label lblNguoiLeThuoc;
	private TextField txtNguoiLeThuoc;
	private Label lblBHXH;
	private TextField txtBHXH;
	private TextField txtBHXHTT;
	private Label lbTamUng;
	private TextField txtTamUng;
	private Label lblBHTN;
	private TextField txtBHTN;
	private TextField txtBHTNTT;
	private Label lbKhauTruKhac;
	private TextField txtKhauTruKhac;
	private Label lblBHYT;
	private TextField txtBHYT;
	private Label lblThueTN;
	private TextField txtThueTN;
	private Label lblDoanPhi;
	private TextField txtDoanPhi;
	private Label lblTongLCB;
	private Label lblTienTC;
	private Label lblTongPC;
	private TextField txtTongLCB;
	private TextField txtTienTC;
	private TextField txtTongPC;
	private Label lblTong;
	private Label lblKhauTru;
	private Label lblThucNhan;
	private TextField txtTong;
	private TextField txtKhauTru;
	private TextField txtThucNhan;
	private TextField txtLKTC;
	private Label lblLKTC;
	private Label lblLuongThuViec;
	private TextField txtLuongThuViec;
	private Label lblGhiChu;
	private TextField txtGhiChu;
	private UIDataObjectBinder binder;
	private String month;
	private String year;
	private Grid rootLayout;
	private Label lblBHODTS;
	private DscField txtBHODTS;
	/**
	 * Creates a new <code>AwesomeSalaryDataContent</code>.
	 */
	public AwesomeSalaryDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
		Calendar cal = Calendar.getInstance();
		//neu ngay hien tai lon hon ngay 10 cua thang hien tai thi lay bang luong thang hien tai -1 ~ -2
		
		if (cal.get(Calendar.DAY_OF_MONTH)<=10){
			cal.add(Calendar.MONTH, -1);
		}
		String month=cal.get(Calendar.MONTH)<10?"0"+cal.get(Calendar.MONTH):""+cal.get(Calendar.MONTH);
		String year = ""+cal.get(Calendar.YEAR);
		setTableName("ATT"+year+month);	
		setMonth(month);
		setYear(year);
	}
	
	@Override
	public Class getDataObjectClass() {
		return ATT200000.class;
	}
	
	@Override
	protected int doInit() {
		int ret = super.doInit();
		
		registerPropertiesEditor();
		
		//bindUI();
		bindUI();
		
		return ret;
	}
	
	private void registerPropertiesEditor(){
		getUIDataObjectBinder().registerCustomEditor(ATT200000.class, "DEPSN", depsnEditor());
	}
	
	private void bindUI(){
		binder = new UIDataObjectBinder(ATTENDANTDB.class);
		binder.addBindMap("BONUS1", txtTienThuong, null);
		binder.addBindMap("BONUS2", txtPCCV, null);
		binder.addBindMap("BONUS3", txtPCCViec, null);
		binder.addBindMap("BONUS4", txtPCSinhHoat, null);
		binder.addBindMap("BONUS8", txtPC8H, null);
		binder.addBindMap("BONUS5", txtBuLuong, null);
		binder.addBindMap("YLBX", txtBHYT, null);
		binder.addBindMap("JOININSU", txtBHXH, null);
		binder.addBindMap("BH_TNGHIEP", txtBHTN, null);
		binder.addBindMap("JOINLUM", txtDoanPhi, null);
		binder.addBindMap("BORM", txtTamUng, null);
		binder.addBindMap("KQT", txtKhauTruKhac, null);
		binder.addBindMap("ACNM", txtSoDemTC, null);
		binder.addBindMap("NOTE", txtGhiChu, lblGhiChu);
		getUIDataObjectBinder().addBindMap("EMPSN", txtSoThe, lblSoThe);
		getUIDataObjectBinder().addBindMap("EMPNA", txtHoTen, lblHoTen);
		getUIDataObjectBinder().addBindMap("BSALY", txtLCB, lblLCB);
		getUIDataObjectBinder().addBindMap("COMBSALY", txtLHD, lblLHD);
		getUIDataObjectBinder().addBindMap("BSALY1", txtLuongThuViec, lblLuongThuViec);
		getUIDataObjectBinder().addBindMap("POSSN", txtChucVu, lblChucVu);
		getUIDataObjectBinder().addBindMap("TYADDCLS", txtLKTC, lblLKTC);
		getUIDataObjectBinder().addBindMap("DEPSN", txtDonVi, lblDonVi);
		getUIDataObjectBinder().addBindMap("DUCLS", txtSoNgayLam, lblSoNgayLam);
		getUIDataObjectBinder().addBindMap("NUCLS", txtSoDemLam, lblSoDemLam);
		getUIDataObjectBinder().addBindMap("ADDCLS1", txtTangCaNgay, lblTangCaNgay);
		getUIDataObjectBinder().addBindMap("NADDCLS", txtTangCaDem, lblTangCaDem);
		getUIDataObjectBinder().addBindMap("ADDHOL", txtTangCaCN, lblTangCaCN);
		getUIDataObjectBinder().addBindMap("ADDHOLN", txtTangCaLe, lblTangCaLe);
		getUIDataObjectBinder().addBindMap("SIGN", txtKyTen, lblKyTen);
		getUIDataObjectBinder().addBindMap("LATE", txtTreSom, lblTreSom);
		getUIDataObjectBinder().addBindMap("REST", txtCoPhep, lblCoPhep);
		getUIDataObjectBinder().addBindMap("REST_PAY", txtCoLuong, lblCoLuong);
		getUIDataObjectBinder().addBindMap("REST_SICK", txtPhepBenh, lblPhepBenh);
		getUIDataObjectBinder().addBindMap("NWHOUR", txtKhangCong, lblKhangCong);
		getUIDataObjectBinder().addBindMap("OTHER", txtDuongSuc, lblDuongSuc);
		getUIDataObjectBinder().addBindMap("PHEP_NS", txtNghiSan, lblNghiSan);
		getUIDataObjectBinder().addBindMap("BONUS1", txtTienThuongTT, lblTienThuong);
		getUIDataObjectBinder().addBindMap("BONUS2", txtPCCVTT, lblPCCV);
		getUIDataObjectBinder().addBindMap("BONUS3", txtPCCViecTT, lblPCCViec);
		getUIDataObjectBinder().addBindMap("BONUS4", txtPCSinhHoatTT, lblPCSinhHoat);
		getUIDataObjectBinder().addBindMap("BAC", txtNguoiLeThuoc, lblNguoiLeThuoc);
		getUIDataObjectBinder().addBindMap("BONUS9", txtBuBH, lblBuBH);
		getUIDataObjectBinder().addBindMap("BONUS6", txtChuyenCan, lblChuyenCan);
		getUIDataObjectBinder().addBindMap("BONUS7", txtPCTienCom, lblPCTienCom);
		getUIDataObjectBinder().addBindMap("BONUS_ACN", txtChuyenCanTC, lblChuyenCanTC);
		getUIDataObjectBinder().addBindMap("BSALY_N", txtCLPCTC, lblCLPCTC);
		getUIDataObjectBinder().addBindMap("JOININSU", txtBHXHTT, lblBHXH);
		getUIDataObjectBinder().addBindMap("BH_TNGHIEP", txtBHTNTT, lblBHTN);
		getUIDataObjectBinder().addBindMap("PAYTAX", txtThueTN, lblThueTN);
		getUIDataObjectBinder().addBindMap("TBASIC", txtTongLCB, lblTongLCB);
		getUIDataObjectBinder().addBindMap("TADDS", txtTienTC, lblTienTC);
		getUIDataObjectBinder().addBindMap("TBONUS", txtTongPC, lblTongPC);
		getUIDataObjectBinder().addBindMap("TINCOME", txtTong, lblTong);
		getUIDataObjectBinder().addBindMap("TKQ", txtKhauTru, lblKhauTru);
		getUIDataObjectBinder().addBindMap("TTS", txtThucNhan, lblThucNhan);
		getUIDataObjectBinder().addBindMap("BONUS10", txtBHODTS, lblBHODTS);
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
	
	public UIDataObjectBinder getAttendantdbBinder(){
		return binder;
	}	
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@Override
	protected void doUIRefresh() {
		super.doUIRefresh();
		if (getProgram().getDataMode()==IProgram.DATAMODE_EDIT){
			rootLayout.setEnabled(true);
		}else{
			rootLayout.setEnabled(false);
		}
	}
	
	@Override
	public void clearAllComponentValue() {
		super.clearAllComponentValue();
		binder.resetAllComponents();
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setBackground(new Color(0xd6d5c0));
		rootLayout = new Grid();
		add(rootLayout);
		GroupBox groupBox1 = new GroupBox();
		groupBox1.setTitle("Thông tin nhân viên");
		groupBox1.setWidth(new Extent(701, Extent.PX));
		GridLayoutData groupBox1LayoutData = new GridLayoutData();
		groupBox1LayoutData.setColumnSpan(2);
		groupBox1.setLayoutData(groupBox1LayoutData);
		rootLayout.add(groupBox1);
		Grid grid2 = new Grid();
		grid2.setSize(6);
		groupBox1.add(grid2);
		lblHoTen = new Label();
		lblHoTen.setText("Họ tên");
		grid2.add(lblHoTen);
		txtHoTen = new TextField();
		txtHoTen.setEnabled(false);
		txtHoTen.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		txtHoTen.setWidth(new Extent(240, Extent.PX));
		txtHoTen.setDisabledBackground(new Color(0xe4e4e4));
		txtHoTen.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x800040), Border.STYLE_SOLID));
		txtHoTen.setForeground(new Color(0x008000));
		grid2.add(txtHoTen);
		lblSoThe = new Label();
		lblSoThe.setText("Số thẻ");
		grid2.add(lblSoThe);
		txtSoThe = new TextField();
		txtSoThe.setEnabled(false);
		txtSoThe.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		txtSoThe.setBackground(new Color(0xb7ffff));
		txtSoThe.setWidth(new Extent(100, Extent.PX));
		txtSoThe.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x800040), Border.STYLE_SOLID));
		txtSoThe.setForeground(Color.BLUE);
		grid2.add(txtSoThe);
		lblLuongThuViec = new Label();
		lblLuongThuViec.setText("Lương thử việc");
		grid2.add(lblLuongThuViec);
		txtLuongThuViec = new TextField();
		txtLuongThuViec.setEnabled(false);
		txtLuongThuViec.setWidth(new Extent(100, Extent.PX));
		txtLuongThuViec.setDisabledBackground(new Color(0xe4e4e4));
		txtLuongThuViec.setForeground(new Color(0x808080));
		grid2.add(txtLuongThuViec);
		lblDonVi = new Label();
		lblDonVi.setText("Đơn vị");
		grid2.add(lblDonVi);
		txtDonVi = new TextField();
		txtDonVi.setEnabled(false);
		txtDonVi.setWidth(new Extent(240, Extent.PX));
		txtDonVi.setDisabledBackground(new Color(0xe4e4e4));
		txtDonVi.setForeground(Color.BLACK);
		grid2.add(txtDonVi);
		lblChucVu = new Label();
		lblChucVu.setText("Chức vụ");
		grid2.add(lblChucVu);
		txtChucVu = new TextField();
		txtChucVu.setEnabled(false);
		txtChucVu.setWidth(new Extent(100, Extent.PX));
		txtChucVu.setDisabledBackground(new Color(0xe4e4e4));
		txtChucVu.setForeground(Color.BLACK);
		grid2.add(txtChucVu);
		lblLKTC = new Label();
		lblLKTC.setText("Lũy kế tc");
		grid2.add(lblLKTC);
		txtLKTC = new TextField();
		txtLKTC.setEnabled(false);
		txtLKTC.setWidth(new Extent(100, Extent.PX));
		txtLKTC.setDisabledBackground(new Color(0xe4e4e4));
		txtLKTC.setForeground(Color.BLACK);
		grid2.add(txtLKTC);
		lblGhiChu = new Label();
		lblGhiChu.setText("Ghi chú");
		grid2.add(lblGhiChu);
		txtGhiChu = new TextField();
		txtGhiChu.setWidth(new Extent(240, Extent.PX));
		txtGhiChu.setDisabledBackground(new Color(0xe4e4e4));
		txtGhiChu.setForeground(Color.BLACK);
		grid2.add(txtGhiChu);
		lblLCB = new Label();
		lblLCB.setText("Lương CB");
		lblLCB.setForeground(new Color(0x0080ff));
		grid2.add(lblLCB);
		txtLCB = new TextField();
		txtLCB.setEnabled(false);
		txtLCB.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		txtLCB.setWidth(new Extent(100, Extent.PX));
		txtLCB.setDisabledBackground(new Color(0xe4e4e4));
		txtLCB.setForeground(new Color(0x800040));
		grid2.add(txtLCB);
		lblLHD = new Label();
		lblLHD.setText("Lương HD");
		lblLHD.setForeground(new Color(0x0080ff));
		grid2.add(lblLHD);
		txtLHD = new TextField();
		txtLHD.setEnabled(false);
		txtLHD.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		txtLHD.setWidth(new Extent(100, Extent.PX));
		txtLHD.setDisabledBackground(new Color(0xe4e4e4));
		txtLHD.setForeground(new Color(0x800040));
		grid2.add(txtLHD);
		GroupBox groupBox2 = new GroupBox();
		groupBox2.setTitle("Biểu thời gian");
		groupBox2.setHeight(new Extent(413, Extent.PX));
		groupBox2.setWidth(new Extent(180, Extent.PX));
		GridLayoutData groupBox2LayoutData = new GridLayoutData();
		groupBox2LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBox2LayoutData.setRowSpan(3);
		groupBox2.setLayoutData(groupBox2LayoutData);
		rootLayout.add(groupBox2);
		Grid grid3 = new Grid();
		groupBox2.add(grid3);
		lblSoNgayLam = new Label();
		lblSoNgayLam.setText("Số ngày làm");
		GridLayoutData lblSoNgayLamLayoutData = new GridLayoutData();
		lblSoNgayLamLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblSoNgayLam.setLayoutData(lblSoNgayLamLayoutData);
		grid3.add(lblSoNgayLam);
		txtSoNgayLam = new TextField();
		txtSoNgayLam.setEnabled(false);
		txtSoNgayLam.setWidth(new Extent(40, Extent.PX));
		txtSoNgayLam.setForeground(Color.BLACK);
		grid3.add(txtSoNgayLam);
		lblSoDemLam = new Label();
		lblSoDemLam.setText("Số đêm làm");
		GridLayoutData lblSoDemLamLayoutData = new GridLayoutData();
		lblSoDemLamLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblSoDemLam.setLayoutData(lblSoDemLamLayoutData);
		grid3.add(lblSoDemLam);
		txtSoDemLam = new TextField();
		txtSoDemLam.setEnabled(false);
		txtSoDemLam.setWidth(new Extent(40, Extent.PX));
		txtSoDemLam.setForeground(Color.BLACK);
		grid3.add(txtSoDemLam);
		lblTangCaNgay = new Label();
		lblTangCaNgay.setText("Tăng ca ngày");
		GridLayoutData lblTangCaNgayLayoutData = new GridLayoutData();
		lblTangCaNgayLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblTangCaNgay.setLayoutData(lblTangCaNgayLayoutData);
		grid3.add(lblTangCaNgay);
		txtTangCaNgay = new TextField();
		txtTangCaNgay.setEnabled(false);
		txtTangCaNgay.setWidth(new Extent(40, Extent.PX));
		txtTangCaNgay.setForeground(Color.BLACK);
		grid3.add(txtTangCaNgay);
		//HA
		lblTangCaDem = new Label();
		lblTangCaDem.setText("Tăng ca đêm");
		GridLayoutData lblTangCaDemLayoutData = new GridLayoutData();
		lblTangCaDemLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblTangCaDem.setLayoutData(lblTangCaDemLayoutData);
		grid3.add(lblTangCaDem);
		txtTangCaDem = new TextField();
		txtTangCaDem.setEnabled(false);
		txtTangCaDem.setWidth(new Extent(40, Extent.PX));
		txtTangCaDem.setForeground(Color.BLACK);
		grid3.add(txtTangCaDem);		
		//end HA
		lblTangCaCN = new Label();
		lblTangCaCN.setText("Tăng ca CN");
		GridLayoutData lblTangCaCNLayoutData = new GridLayoutData();
		lblTangCaCNLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblTangCaCN.setLayoutData(lblTangCaCNLayoutData);
		grid3.add(lblTangCaCN);
		txtTangCaCN = new TextField();
		txtTangCaCN.setEnabled(false);
		txtTangCaCN.setWidth(new Extent(40, Extent.PX));
		txtTangCaCN.setForeground(Color.BLACK);
		grid3.add(txtTangCaCN);
		lblTangCaLe = new Label();
		lblTangCaLe.setText("Tăng ca lễ");
		GridLayoutData lblTangCaLeLayoutData = new GridLayoutData();
		lblTangCaLeLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblTangCaLe.setLayoutData(lblTangCaLeLayoutData);
		grid3.add(lblTangCaLe);
		txtTangCaLe = new TextField();
		txtTangCaLe.setEnabled(false);
		txtTangCaLe.setWidth(new Extent(40, Extent.PX));
		txtTangCaLe.setForeground(Color.BLACK);
		grid3.add(txtTangCaLe);
		lblSoDemTC = new Label();
		lblSoDemTC.setText("Số đêm tc(1-1.5h)");
		GridLayoutData lblSoDemTCLayoutData = new GridLayoutData();
		lblSoDemTCLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblSoDemTC.setLayoutData(lblSoDemTCLayoutData);
		grid3.add(lblSoDemTC);
		txtSoDemTC = new TextField();
		txtSoDemTC.setStyleName("Default.ReadonlyField");
		txtSoDemTC.setEnabled(false);
		txtSoDemTC.setWidth(new Extent(40, Extent.PX));
		txtSoDemTC.setForeground(Color.BLACK);
		grid3.add(txtSoDemTC);
		lblKyTen = new Label();
		lblKyTen.setText("Ký tên");
		GridLayoutData lblKyTenLayoutData = new GridLayoutData();
		lblKyTenLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblKyTen.setLayoutData(lblKyTenLayoutData);
		grid3.add(lblKyTen);
		txtKyTen = new TextField();
		txtKyTen.setEnabled(false);
		txtKyTen.setWidth(new Extent(40, Extent.PX));
		txtKyTen.setForeground(Color.BLACK);
		grid3.add(txtKyTen);
		lblTreSom = new Label();
		lblTreSom.setText("Trễ/sớm");
		GridLayoutData lblTreSomLayoutData = new GridLayoutData();
		lblTreSomLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblTreSom.setLayoutData(lblTreSomLayoutData);
		grid3.add(lblTreSom);
		txtTreSom = new TextField();
		txtTreSom.setEnabled(false);
		txtTreSom.setWidth(new Extent(40, Extent.PX));
		txtTreSom.setForeground(Color.BLACK);
		grid3.add(txtTreSom);
		Label label1 = new Label();
		label1.setText("Phép-------------------");
		label1.setForeground(new Color(0x008040));
		GridLayoutData label1LayoutData = new GridLayoutData();
		label1LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		label1LayoutData.setColumnSpan(2);
		label1.setLayoutData(label1LayoutData);
		grid3.add(label1);
		lblCoPhep = new Label();
		lblCoPhep.setText("Có phép");
		GridLayoutData lblCoPhepLayoutData = new GridLayoutData();
		lblCoPhepLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblCoPhep.setLayoutData(lblCoPhepLayoutData);
		grid3.add(lblCoPhep);
		txtCoPhep = new TextField();
		txtCoPhep.setEnabled(false);
		txtCoPhep.setWidth(new Extent(40, Extent.PX));
		txtCoPhep.setForeground(Color.BLACK);
		grid3.add(txtCoPhep);
		lblCoLuong = new Label();
		lblCoLuong.setText("Có lương");
		GridLayoutData lblCoLuongLayoutData = new GridLayoutData();
		lblCoLuongLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblCoLuong.setLayoutData(lblCoLuongLayoutData);
		grid3.add(lblCoLuong);
		txtCoLuong = new TextField();
		txtCoLuong.setEnabled(false);
		txtCoLuong.setWidth(new Extent(40, Extent.PX));
		txtCoLuong.setForeground(Color.BLACK);
		grid3.add(txtCoLuong);
		lblPhepBenh = new Label();
		lblPhepBenh.setText("Phép bệnh");
		GridLayoutData lblPhepBenhLayoutData = new GridLayoutData();
		lblPhepBenhLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblPhepBenh.setLayoutData(lblPhepBenhLayoutData);
		grid3.add(lblPhepBenh);
		txtPhepBenh = new TextField();
		txtPhepBenh.setEnabled(false);
		txtPhepBenh.setWidth(new Extent(40, Extent.PX));
		txtPhepBenh.setForeground(Color.BLACK);
		grid3.add(txtPhepBenh);
		lblKhangCong = new Label();
		lblKhangCong.setText("Kháng công");
		GridLayoutData lblKhangCongLayoutData = new GridLayoutData();
		lblKhangCongLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblKhangCong.setLayoutData(lblKhangCongLayoutData);
		grid3.add(lblKhangCong);
		txtKhangCong = new TextField();
		txtKhangCong.setEnabled(false);
		txtKhangCong.setWidth(new Extent(40, Extent.PX));
		txtKhangCong.setForeground(Color.BLACK);
		grid3.add(txtKhangCong);
		lblDuongSuc = new Label();
		lblDuongSuc.setText("Dưỡng sức");
		GridLayoutData lblDuongSucLayoutData = new GridLayoutData();
		lblDuongSucLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblDuongSuc.setLayoutData(lblDuongSucLayoutData);
		grid3.add(lblDuongSuc);
		txtDuongSuc = new TextField();
		txtDuongSuc.setEnabled(false);
		txtDuongSuc.setWidth(new Extent(40, Extent.PX));
		txtDuongSuc.setForeground(Color.BLACK);
		grid3.add(txtDuongSuc);
		lblNghiSan = new Label();
		lblNghiSan.setText("Nghỉ sản");
		GridLayoutData lblNghiSanLayoutData = new GridLayoutData();
		lblNghiSanLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblNghiSan.setLayoutData(lblNghiSanLayoutData);
		grid3.add(lblNghiSan);
		txtNghiSan = new TextField();
		txtNghiSan.setEnabled(false);
		txtNghiSan.setWidth(new Extent(40, Extent.PX));
		txtNghiSan.setForeground(Color.BLACK);
		grid3.add(txtNghiSan);
		Column column1 = new Column();
		GridLayoutData column1LayoutData = new GridLayoutData();
		column1LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		column1.setLayoutData(column1LayoutData);
		rootLayout.add(column1);
		GroupBox groupBox3 = new GroupBox();
		groupBox3.setTitle("Khác");
		groupBox3.setHeight(new Extent(175, Extent.PX));
		column1.add(groupBox3);
		Grid grid4 = new Grid();
		grid4.setColumnWidth(0, new Extent(90, Extent.PX));
		grid4.setColumnWidth(1, new Extent(90, Extent.PX));
		grid4.setColumnWidth(2, new Extent(15, Extent.PX));
		grid4.setColumnWidth(3, new Extent(90, Extent.PX));
		grid4.setColumnWidth(4, new Extent(120, Extent.PX));
		grid4.setColumnWidth(5, new Extent(90, Extent.PX));
		grid4.setSize(6);
		groupBox3.add(grid4);
		lblTienThuong = new Label();
		lblTienThuong.setText("Tiền thưởng");
		GridLayoutData lblTienThuongLayoutData = new GridLayoutData();
		lblTienThuongLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblTienThuong.setLayoutData(lblTienThuongLayoutData);
		grid4.add(lblTienThuong);
		txtTienThuong = new TextField();
		txtTienThuong.setEnabled(false);
		txtTienThuong.setWidth(new Extent(80, Extent.PX));
		txtTienThuong.setDisabledBackground(new Color(0xc8c8c8));
		txtTienThuong.setForeground(new Color(0x004080));
		grid4.add(txtTienThuong);
		Label label2 = new Label();
		label2.setText("~");
		grid4.add(label2);
		txtTienThuongTT = new TextField();
		txtTienThuongTT.setEnabled(false);
		txtTienThuongTT.setFont(new Font(null, Font.BOLD, new Extent(10,
				Extent.PT)));
		txtTienThuongTT.setBackground(new Color(0xfffed7));
		txtTienThuongTT.setWidth(new Extent(80, Extent.PX));
		txtTienThuongTT.setForeground(new Color(0x800080));
		grid4.add(txtTienThuongTT);
		lblBuBH = new Label();
		lblBuBH.setText("Bù bảo hiểm");
		GridLayoutData lblBuBHLayoutData = new GridLayoutData();
		lblBuBHLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblBuBH.setLayoutData(lblBuBHLayoutData);
		grid4.add(lblBuBH);
		txtBuBH = new TextField();
		txtBuBH.setEnabled(false);
		txtBuBH.setWidth(new Extent(80, Extent.PX));
		txtBuBH.setForeground(Color.BLACK);
		grid4.add(txtBuBH);
		lblPCCV = new Label();
		lblPCCV.setText("PC chức vụ");
		GridLayoutData lblPCCVLayoutData = new GridLayoutData();
		lblPCCVLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblPCCV.setLayoutData(lblPCCVLayoutData);
		grid4.add(lblPCCV);
		txtPCCV = new TextField();
		txtPCCV.setEnabled(false);
		txtPCCV.setWidth(new Extent(80, Extent.PX));
		txtPCCV.setDisabledBackground(new Color(0xc8c8c8));
		txtPCCV.setForeground(new Color(0x004080));
		grid4.add(txtPCCV);
		Label label3 = new Label();
		label3.setText("~");
		grid4.add(label3);
		txtPCCVTT = new TextField();
		txtPCCVTT.setEnabled(false);
		txtPCCVTT.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		txtPCCVTT.setBackground(new Color(0xfffed7));
		txtPCCVTT.setWidth(new Extent(80, Extent.PX));
		txtPCCVTT.setForeground(new Color(0x800080));
		grid4.add(txtPCCVTT);
		lblChuyenCan = new Label();
		lblChuyenCan.setText("Chuyên cần");
		GridLayoutData lblChuyenCanLayoutData = new GridLayoutData();
		lblChuyenCanLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblChuyenCan.setLayoutData(lblChuyenCanLayoutData);
		grid4.add(lblChuyenCan);
		txtChuyenCan = new TextField();
		txtChuyenCan.setEnabled(false);
		txtChuyenCan.setWidth(new Extent(80, Extent.PX));
		txtChuyenCan.setForeground(Color.BLACK);
		grid4.add(txtChuyenCan);
		lblPCCViec = new Label();
		lblPCCViec.setText("PC công việc");
		GridLayoutData lblPCCViecLayoutData = new GridLayoutData();
		lblPCCViecLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblPCCViec.setLayoutData(lblPCCViecLayoutData);
		grid4.add(lblPCCViec);
		txtPCCViec = new TextField();
		txtPCCViec.setEnabled(false);
		txtPCCViec.setWidth(new Extent(80, Extent.PX));
		txtPCCViec.setDisabledBackground(new Color(0xc8c8c8));
		txtPCCViec.setForeground(new Color(0x004080));
		grid4.add(txtPCCViec);
		Label label4 = new Label();
		label4.setText("~");
		grid4.add(label4);
		txtPCCViecTT = new TextField();
		txtPCCViecTT.setEnabled(false);
		txtPCCViecTT.setFont(new Font(null, Font.BOLD,
				new Extent(10, Extent.PT)));
		txtPCCViecTT.setBackground(new Color(0xfffed7));
		txtPCCViecTT.setWidth(new Extent(80, Extent.PX));
		txtPCCViecTT.setForeground(new Color(0x800080));
		grid4.add(txtPCCViecTT);
		lblPCTienCom = new Label();
		lblPCTienCom.setText("PC tiền cơm");
		GridLayoutData lblPCTienComLayoutData = new GridLayoutData();
		lblPCTienComLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblPCTienCom.setLayoutData(lblPCTienComLayoutData);
		grid4.add(lblPCTienCom);
		txtPCTienCom = new TextField();
		txtPCTienCom.setEnabled(false);
		txtPCTienCom.setWidth(new Extent(80, Extent.PX));
		txtPCTienCom.setForeground(Color.BLACK);
		grid4.add(txtPCTienCom);
		lblPCSinhHoat = new Label();
		lblPCSinhHoat.setText("PC sinh hoạt");
		GridLayoutData lblPCSinhHoatLayoutData = new GridLayoutData();
		lblPCSinhHoatLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblPCSinhHoat.setLayoutData(lblPCSinhHoatLayoutData);
		grid4.add(lblPCSinhHoat);
		txtPCSinhHoat = new TextField();
		txtPCSinhHoat.setEnabled(false);
		txtPCSinhHoat.setWidth(new Extent(80, Extent.PX));
		txtPCSinhHoat.setDisabledBackground(new Color(0xc8c8c8));
		txtPCSinhHoat.setForeground(new Color(0x004080));
		grid4.add(txtPCSinhHoat);
		Label label5 = new Label();
		label5.setText("+=");
		GridLayoutData label5LayoutData = new GridLayoutData();
		label5LayoutData.setRowSpan(2);
		label5.setLayoutData(label5LayoutData);
		grid4.add(label5);
		txtPCSinhHoatTT = new TextField();
		txtPCSinhHoatTT.setEnabled(false);
		txtPCSinhHoatTT.setFont(new Font(null, Font.BOLD, new Extent(10,
				Extent.PT)));
		txtPCSinhHoatTT.setBackground(new Color(0xfffed7));
		txtPCSinhHoatTT.setWidth(new Extent(80, Extent.PX));
		txtPCSinhHoatTT.setForeground(new Color(0x800080));
		GridLayoutData txtPCSinhHoatTTLayoutData = new GridLayoutData();
		txtPCSinhHoatTTLayoutData.setRowSpan(2);
		txtPCSinhHoatTT.setLayoutData(txtPCSinhHoatTTLayoutData);
		grid4.add(txtPCSinhHoatTT);
		lblChuyenCanTC = new Label();
		lblChuyenCanTC.setText("Chuyên cần TC");
		GridLayoutData lblChuyenCanTCLayoutData = new GridLayoutData();
		lblChuyenCanTCLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblChuyenCanTC.setLayoutData(lblChuyenCanTCLayoutData);
		grid4.add(lblChuyenCanTC);
		txtChuyenCanTC = new TextField();
		txtChuyenCanTC.setEnabled(false);
		txtChuyenCanTC.setWidth(new Extent(80, Extent.PX));
		txtChuyenCanTC.setForeground(Color.BLACK);
		grid4.add(txtChuyenCanTC);
		lblPC8H = new Label();
		lblPC8H.setText("PC làm 8H");
		GridLayoutData lblPC8HLayoutData = new GridLayoutData();
		lblPC8HLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblPC8H.setLayoutData(lblPC8HLayoutData);
		grid4.add(lblPC8H);
		txtPC8H = new TextField();
		txtPC8H.setEnabled(false);
		txtPC8H.setWidth(new Extent(80, Extent.PX));
		txtPC8H.setDisabledBackground(new Color(0xc8c8c8));
		txtPC8H.setForeground(new Color(0x004080));
		grid4.add(txtPC8H);
		lblCLPCTC = new Label();
		lblCLPCTC.setText("Chênh lệch PCTC");
		GridLayoutData lblCLPCTCLayoutData = new GridLayoutData();
		lblCLPCTCLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblCLPCTC.setLayoutData(lblCLPCTCLayoutData);
		grid4.add(lblCLPCTC);
		txtCLPCTC = new TextField();
		txtCLPCTC.setEnabled(false);
		txtCLPCTC.setWidth(new Extent(80, Extent.PX));
		txtCLPCTC.setForeground(Color.BLACK);
		grid4.add(txtCLPCTC);
		lblBuLuong = new Label();
		lblBuLuong.setText("Bù lương");
		GridLayoutData lblBuLuongLayoutData = new GridLayoutData();
		lblBuLuongLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblBuLuong.setLayoutData(lblBuLuongLayoutData);
		grid4.add(lblBuLuong);
		txtBuLuong = new TextField();
		txtBuLuong.setEnabled(false);
		txtBuLuong.setWidth(new Extent(80, Extent.PX));
		txtBuLuong.setDisabledBackground(new Color(0xc8c8c8));
		txtBuLuong.setForeground(new Color(0x004080));
		grid4.add(txtBuLuong);
		Label label6 = new Label();
		grid4.add(label6);
		Label label7 = new Label();
		grid4.add(label7);
		lblNguoiLeThuoc = new Label();
		lblNguoiLeThuoc.setText("Người lệ thuộc");
		GridLayoutData lblNguoiLeThuocLayoutData = new GridLayoutData();
		lblNguoiLeThuocLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblNguoiLeThuoc.setLayoutData(lblNguoiLeThuocLayoutData);
		grid4.add(lblNguoiLeThuoc);
		txtNguoiLeThuoc = new TextField();
		txtNguoiLeThuoc.setEnabled(false);
		txtNguoiLeThuoc.setWidth(new Extent(80, Extent.PX));
		txtNguoiLeThuoc.setForeground(Color.BLACK);
		grid4.add(txtNguoiLeThuoc);
		lblBHODTS = new Label();
		lblBHODTS.setText("BHODTS");
		GridLayoutData lblBHODTSLayoutData = new GridLayoutData();
		lblBHODTSLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblBHODTS.setLayoutData(lblBHODTSLayoutData);
		grid4.add(lblBHODTS);
		txtBHODTS = new DscField();
		txtBHODTS.setEnabled(false);
		txtBHODTS.setWidth(new Extent(80, Extent.PX));
		txtBHODTS.setDisabledBackground(new Color(0xc8c8c8));
		txtBHODTS.setForeground(new Color(0x004080));
		grid4.add(txtBHODTS);
		GroupBox groupBox4 = new GroupBox();
		groupBox4.setTitle("Khấu trừ");
		groupBox4.setHeight(new Extent(115, Extent.PX));
		column1.add(groupBox4);
		Grid grid5 = new Grid();
		grid5.setColumnWidth(0, new Extent(90, Extent.PX));
		grid5.setColumnWidth(1, new Extent(90, Extent.PX));
		grid5.setColumnWidth(2, new Extent(15, Extent.PX));
		grid5.setColumnWidth(3, new Extent(90, Extent.PX));
		grid5.setColumnWidth(4, new Extent(120, Extent.PX));
		grid5.setColumnWidth(5, new Extent(90, Extent.PX));
		grid5.setSize(6);
		groupBox4.add(grid5);
		lblBHXH = new Label();
		lblBHXH.setText("BHXH");
		GridLayoutData lblBHXHLayoutData = new GridLayoutData();
		lblBHXHLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblBHXH.setLayoutData(lblBHXHLayoutData);
		grid5.add(lblBHXH);
		txtBHXH = new TextField();
		txtBHXH.setStyleName("Default.ReadonlyField");
		txtBHXH.setEnabled(false);
		txtBHXH.setWidth(new Extent(80, Extent.PX));
		txtBHXH.setForeground(Color.BLACK);
		grid5.add(txtBHXH);
		Label label8 = new Label();
		label8.setText("~");
		grid5.add(label8);
		txtBHXHTT = new TextField();
		txtBHXHTT.setEnabled(false);
		txtBHXHTT.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		txtBHXHTT.setBackground(new Color(0xfffed7));
		txtBHXHTT.setWidth(new Extent(80, Extent.PX));
		txtBHXHTT.setForeground(new Color(0x800080));
		grid5.add(txtBHXHTT);
		lbTamUng = new Label();
		lbTamUng.setText("Tạm ứng");
		GridLayoutData lbTamUngLayoutData = new GridLayoutData();
		lbTamUngLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lbTamUng.setLayoutData(lbTamUngLayoutData);
		grid5.add(lbTamUng);
		txtTamUng = new TextField();
		txtTamUng.setEnabled(false);
		txtTamUng.setWidth(new Extent(80, Extent.PX));
		txtTamUng.setDisabledBackground(new Color(0xc8c8c8));
		txtTamUng.setForeground(new Color(0x004080));
		grid5.add(txtTamUng);
		lblBHTN = new Label();
		lblBHTN.setText("BHTN");
		GridLayoutData lblBHTNLayoutData = new GridLayoutData();
		lblBHTNLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblBHTN.setLayoutData(lblBHTNLayoutData);
		grid5.add(lblBHTN);
		txtBHTN = new TextField();
		txtBHTN.setStyleName("Default.ReadonlyField");
		txtBHTN.setEnabled(false);
		txtBHTN.setWidth(new Extent(80, Extent.PX));
		txtBHTN.setForeground(Color.BLACK);
		grid5.add(txtBHTN);
		Label label9 = new Label();
		label9.setText("~");
		grid5.add(label9);
		txtBHTNTT = new TextField();
		txtBHTNTT.setEnabled(false);
		txtBHTNTT.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		txtBHTNTT.setBackground(new Color(0xfffed7));
		txtBHTNTT.setWidth(new Extent(80, Extent.PX));
		txtBHTNTT.setForeground(new Color(0x800080));
		grid5.add(txtBHTNTT);
		lbKhauTruKhac = new Label();
		lbKhauTruKhac.setText("Khấu trừ khác");
		GridLayoutData lbKhauTruKhacLayoutData = new GridLayoutData();
		lbKhauTruKhacLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lbKhauTruKhac.setLayoutData(lbKhauTruKhacLayoutData);
		grid5.add(lbKhauTruKhac);
		txtKhauTruKhac = new TextField();
		txtKhauTruKhac.setEnabled(false);
		txtKhauTruKhac.setWidth(new Extent(80, Extent.PX));
		txtKhauTruKhac.setDisabledBackground(new Color(0xc8c8c8));
		txtKhauTruKhac.setForeground(new Color(0x004080));
		grid5.add(txtKhauTruKhac);
		lblBHYT = new Label();
		lblBHYT.setText("BHYT");
		GridLayoutData lblBHYTLayoutData = new GridLayoutData();
		lblBHYTLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblBHYT.setLayoutData(lblBHYTLayoutData);
		grid5.add(lblBHYT);
		txtBHYT = new TextField();
		txtBHYT.setEnabled(false);
		txtBHYT.setWidth(new Extent(80, Extent.PX));
		txtBHYT.setDisabledBackground(new Color(0xc8c8c8));
		txtBHYT.setForeground(new Color(0x004080));
		grid5.add(txtBHYT);
		Label label10 = new Label();
		grid5.add(label10);
		Label label11 = new Label();
		grid5.add(label11);
		lblThueTN = new Label();
		lblThueTN.setText("Thuế TN");
		GridLayoutData lblThueTNLayoutData = new GridLayoutData();
		lblThueTNLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblThueTN.setLayoutData(lblThueTNLayoutData);
		grid5.add(lblThueTN);
		txtThueTN = new TextField();
		txtThueTN.setEnabled(false);
		txtThueTN.setWidth(new Extent(80, Extent.PX));
		txtThueTN.setForeground(Color.BLACK);
		grid5.add(txtThueTN);
		lblDoanPhi = new Label();
		lblDoanPhi.setText("Đoàn phí");
		GridLayoutData lblDoanPhiLayoutData = new GridLayoutData();
		lblDoanPhiLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		lblDoanPhi.setLayoutData(lblDoanPhiLayoutData);
		grid5.add(lblDoanPhi);
		txtDoanPhi = new TextField();
		txtDoanPhi.setEnabled(false);
		txtDoanPhi.setWidth(new Extent(80, Extent.PX));
		txtDoanPhi.setDisabledBackground(new Color(0xc8c8c8));
		txtDoanPhi.setForeground(new Color(0x004080));
		grid5.add(txtDoanPhi);
		GroupBox groupBox5 = new GroupBox();
		groupBox5.setHeight(new Extent(90, Extent.PX));
		groupBox5.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5,
				Extent.PX)));
		ColumnLayoutData groupBox5LayoutData = new ColumnLayoutData();
		groupBox5LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		groupBox5.setLayoutData(groupBox5LayoutData);
		column1.add(groupBox5);
		Grid grid6 = new Grid();
		grid6.setSize(6);
		groupBox5.add(grid6);
		Label label12 = new Label();
		grid6.add(label12);
		lblTongLCB = new Label();
		lblTongLCB.setText("Tổng lương cơ bản");
		GridLayoutData lblTongLCBLayoutData = new GridLayoutData();
		lblTongLCBLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		lblTongLCB.setLayoutData(lblTongLCBLayoutData);
		grid6.add(lblTongLCB);
		Label label13 = new Label();
		grid6.add(label13);
		lblTienTC = new Label();
		lblTienTC.setText("Tiền tăng ca");
		GridLayoutData lblTienTCLayoutData = new GridLayoutData();
		lblTienTCLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		lblTienTC.setLayoutData(lblTienTCLayoutData);
		grid6.add(lblTienTC);
		Label label14 = new Label();
		grid6.add(label14);
		lblTongPC = new Label();
		lblTongPC.setText("Tổng phụ cấp");
		GridLayoutData lblTongPCLayoutData = new GridLayoutData();
		lblTongPCLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		lblTongPC.setLayoutData(lblTongPCLayoutData);
		grid6.add(lblTongPC);
		Label label15 = new Label();
		grid6.add(label15);
		txtTongLCB = new TextField();
		txtTongLCB.setEnabled(false);
		txtTongLCB.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtTongLCB.setWidth(new Extent(120, Extent.PX));
		txtTongLCB.setForeground(Color.RED);
		GridLayoutData txtTongLCBLayoutData = new GridLayoutData();
		txtTongLCBLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtTongLCB.setLayoutData(txtTongLCBLayoutData);
		grid6.add(txtTongLCB);
		Label label16 = new Label();
		label16.setText("+");
		grid6.add(label16);
		txtTienTC = new TextField();
		txtTienTC.setEnabled(false);
		txtTienTC.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtTienTC.setWidth(new Extent(120, Extent.PX));
		txtTienTC.setForeground(Color.RED);
		GridLayoutData txtTienTCLayoutData = new GridLayoutData();
		txtTienTCLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtTienTC.setLayoutData(txtTienTCLayoutData);
		grid6.add(txtTienTC);
		Label label17 = new Label();
		label17.setText("+");
		grid6.add(label17);
		txtTongPC = new TextField();
		txtTongPC.setEnabled(false);
		txtTongPC.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtTongPC.setWidth(new Extent(120, Extent.PX));
		txtTongPC.setForeground(Color.RED);
		GridLayoutData txtTongPCLayoutData = new GridLayoutData();
		txtTongPCLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtTongPC.setLayoutData(txtTongPCLayoutData);
		grid6.add(txtTongPC);
		Label label18 = new Label();
		grid6.add(label18);
		lblTong = new Label();
		GridLayoutData lblTongLayoutData = new GridLayoutData();
		lblTongLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		lblTong.setLayoutData(lblTongLayoutData);
		grid6.add(lblTong);
		Label label19 = new Label();
		grid6.add(label19);
		lblKhauTru = new Label();
		lblKhauTru.setText("Khấu trừ");
		GridLayoutData lblKhauTruLayoutData = new GridLayoutData();
		lblKhauTruLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		lblKhauTru.setLayoutData(lblKhauTruLayoutData);
		grid6.add(lblKhauTru);
		Label label20 = new Label();
		grid6.add(label20);
		lblThucNhan = new Label();
		lblThucNhan.setText("Thực nhận");
		GridLayoutData lblThucNhanLayoutData = new GridLayoutData();
		lblThucNhanLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		lblThucNhan.setLayoutData(lblThucNhanLayoutData);
		grid6.add(lblThucNhan);
		Label label21 = new Label();
		label21.setText("=");
		grid6.add(label21);
		txtTong = new TextField();
		txtTong.setEnabled(false);
		txtTong.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		txtTong.setWidth(new Extent(120, Extent.PX));
		txtTong.setForeground(Color.RED);
		GridLayoutData txtTongLayoutData = new GridLayoutData();
		txtTongLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtTong.setLayoutData(txtTongLayoutData);
		grid6.add(txtTong);
		Label label22 = new Label();
		label22.setText("-");
		grid6.add(label22);
		txtKhauTru = new TextField();
		txtKhauTru.setEnabled(false);
		txtKhauTru.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtKhauTru.setWidth(new Extent(120, Extent.PX));
		txtKhauTru.setForeground(Color.RED);
		GridLayoutData txtKhauTruLayoutData = new GridLayoutData();
		txtKhauTruLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtKhauTru.setLayoutData(txtKhauTruLayoutData);
		grid6.add(txtKhauTru);
		Label label23 = new Label();
		label23.setText("=");
		grid6.add(label23);
		txtThucNhan = new TextField();
		txtThucNhan.setEnabled(false);
		txtThucNhan.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtThucNhan
				.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		txtThucNhan.setWidth(new Extent(120, Extent.PX));
		txtThucNhan.setForeground(Color.RED);
		GridLayoutData txtThucNhanLayoutData = new GridLayoutData();
		txtThucNhanLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		txtThucNhan.setLayoutData(txtThucNhanLayoutData);
		grid6.add(txtThucNhan);
	}
}
