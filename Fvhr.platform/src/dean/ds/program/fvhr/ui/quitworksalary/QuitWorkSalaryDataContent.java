package ds.program.fvhr.ui.quitworksalary;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.extras.app.TabPane;
import nextapp.echo2.extras.app.layout.TabPaneLayoutData;
import ds.program.fvhr.domain.ATTQUIT;
import ds.program.fvhr.domain.pk.ATTQUITPk;
import dsc.echo2app.component.DscField;
import echopointng.GroupBox;
import fv.components.JdbcDataContent;
import fv.components.RowNavigator;
import fv.util.Vni2Uni;

public class QuitWorkSalaryDataContent extends JdbcDataContent {
	private static final long serialVersionUID = 1L;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	// Thong tin co ban
	private DscField txtSoThe;

	private DscField txtHoTen;

	private DscField txtChucVu;

	private DscField txtDonVi;

	private DscField txtTenDonVi;

	private DscField txtNgayNhapXuong;

	private DscField txtLuongCBBinhQuan;

	private DscField txtPhuCapCBBinhQuan;

	// Tro cap nghi viec
	private DscField txtDotThoiViec;

	private DscField txtSoThangHuongTroCap;

	private DscField txtThanhTienHuongTroCap;

	private DscField txtSoNgayPhepNamConLai;

	private DscField txtThanhTienPhepNamConLai;

	private DscField txtNghiTruocThoiHan;

	private DscField txtThanhTienNghiTruocThoiHan;

	private DscField txtSoThangDenBu;

	private DscField txtThanhTienSoThangDenBu;

	private DscField txtSoTayNhanVien;

	private DscField txtTongTroCapNghiViec;

	private DscField txtThucNhan;

	private DscField txtKhauTruKhac;

	private DscField txtLuongCoBan;

	private DscField txtLuongHopDong;

	private DscField txtPhuCapChucVu;

	// Bieu thoi gian

	private DscField txtSoNgayLam;

	private DscField txtSoDemLam;

	private DscField txtTangCaNgay;

	private DscField txtTangCaDem;

	private DscField txtTangCaChuNhat;

	private DscField txtTangCaLe;

	private DscField txtSoDemTangCa;

	private DscField txtKyTen;

	private DscField txtTreSom;

	// Phep
	private DscField txtCoPhep;

	private DscField txtCoLuong;

	private DscField txtPhepBenh;

	private DscField txtKhangCong;

	private DscField txtDuongSuc;

	private DscField txtNghiSan;

	private DscField txtTangCaNgayNgoai;

	private DscField txtTangCaDemNgoai;

	private DscField txtTangCaChuNhatNgoai;

	private DscField txtTangCaLeNgoai;

	private DscField txtSoDemTangCaNgoai;

	// Thuong/phu cap
	private DscField txtTienThuong;

	// private DscField txtPhuCapChucVu;
	private DscField txtPhuCapCongViec;

	private DscField txtPhuCapSinhHoat;

	private DscField txtPhuCapLam8H;

	private DscField txtChuyenCan;

	private DscField txtPhuCapTienCom;

	private DscField txtChenhLechPhuCapTangCa;

	private DscField txtChuyenCanTangCa;

	private DscField txtBuLuong;

	private DscField txtBuBaoHiem;

	// Khau tru
	private DscField txtBaoHiemXaHoi;

	private DscField txtBuBaoHiemQuy;

	private DscField txtBuBaoHiemYTe;

	private DscField txtThuBaoHiemYTe;

	private DscField txtBaoHiemYTe;

	private DscField txtBaoHiemThatNghiep;

	private DscField txtDoanPhi;

	private DscField txtTamUng;

	// private DscField txtKhauTruKhac;
	private DscField txtThueThuNhap;

	private DscField txtTongLuongCoBan;

	private DscField txtTienTangCa;

	private DscField txtTongPhuCap;

	private DscField txtKhauTru;

	private DscField txtLuongThangHienTai;

	private DscField txtBonusAll;

	private GridLayoutData beautyLayout;

	private DscField txtPhuCapChucVu1;

	private DscField txtTienThuong1;

	private DscField txtPhuCapChucVu11;

	private DscField txtPhuCapCongViec1;

	private DscField txtPhuCapSinhHoat1;

	// private DscField txtKhauTruKhac1;

	private QuitWorkSalaryProgram program;

	private RowNavigator navigator;

	public QuitWorkSalaryDataContent(QuitWorkSalaryProgram program) {
		super();
		this.program = program;
		initComponents();
		navigator.setDataContent(this);
	}

	public RowNavigator getNavigator() {
		return navigator;
	}

	private void initComponents() {
		SplitPane splitPane = new SplitPane(
				SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane.setSeparatorPosition(new Extent(38));
		navigator = program.getBrowserContent().getRowNavigator();
		splitPane.add(navigator);
		beautyLayout = new GridLayoutData();
		beautyLayout.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.CENTER));
		beautyLayout.setInsets(new Insets(0, 3, 2, 3));
		TabPane tabPane = new TabPane();
		tabPane.setStyleName("Default.TabPane");
		ContentPane luongNghiViecContent = luongNghiViecTabContent();
		tabPane.add(luongNghiViecContent);
		// ////////////
		ContentPane chiTietLamViecContent = chiTietThoiGianLamViecTabContent();
		tabPane.add(chiTietLamViecContent);
		splitPane.add(tabPane);
		add(splitPane);
	}

	@Override
	public boolean refetch() {
		ATTQUITPk pk = program.getBrowserContent().getPkValue();

		if (pk != null) {
			ATTQUIT att = program.getBrowserContent().getDao().getAttquitData(
					pk.getEMPSN(), pk.getDOT_TV());
			bindData(att);
			return true;
		} else {
			return false;
		}
	}

	private ContentPane luongNghiViecTabContent() {
		ContentPane content = new ContentPane();
		content.setEnabled(false);
		TabPaneLayoutData tabLayout = new TabPaneLayoutData();
		tabLayout.setTitle("Lương nghỉ việc");
		content.setLayoutData(tabLayout);
		Grid groupBoxGrid = new Grid(2);
		// groupBoxGrid.setWidth(new Extent(800));
		content.add(groupBoxGrid);
		GridLayoutData groupBoxLayout = new GridLayoutData();
		groupBoxLayout
				.setAlignment(new Alignment(Alignment.LEFT, Alignment.TOP));
		GridLayoutData groupBoxLayout1 = new GridLayoutData();
		groupBoxLayout1.setAlignment(new Alignment(Alignment.LEFT,
				Alignment.TOP));
		groupBoxLayout1.setRowSpan(2);
		// Thong tin co ban
		GroupBox firstGroup = new GroupBox("Thông tin cơ bản");
		// firstGroup.setInsets(new Insets(3, 5, 3, 5));
		firstGroup.setLayoutData(groupBoxLayout1);
		firstGroup.setHeight(new Extent(100, Extent.PERCENT));

		Grid basicGrid = new Grid(2);
		firstGroup.add(basicGrid);
		groupBoxGrid.add(firstGroup);
		basicGrid.add(new Label("Số thẻ"));
		txtSoThe = new DscField();
		txtSoThe.setWidth(new Extent(75));
		txtSoThe.setBackground(new Color(0xCCFFCC));
		basicGrid.add(txtSoThe);
		basicGrid.add(new Label("Họ tên"));
		txtHoTen = new DscField();
		txtHoTen.setWidth(new Extent(150));
		basicGrid.add(txtHoTen);
		basicGrid.add(new Label("Chức vụ"));
		txtChucVu = new DscField();
		txtChucVu.setWidth(new Extent(75));
		basicGrid.add(txtChucVu);
		basicGrid.add(new Label("Đơn vị"));
		txtDonVi = new DscField();
		txtDonVi.setWidth(new Extent(75));
		basicGrid.add(txtDonVi);
		basicGrid.add(new Label("Tên đơn vị"));
		txtTenDonVi = new DscField();
		txtTenDonVi.setWidth(new Extent(150));
		basicGrid.add(txtTenDonVi);
		basicGrid.add(new Label("Ngày nhập xưởng"));
		txtNgayNhapXuong = new DscField();
		txtNgayNhapXuong.setWidth(new Extent(75));
		basicGrid.add(txtNgayNhapXuong);
		basicGrid.add(new Label("Lương cơ bản"));
		txtLuongCoBan = new DscField();
		txtLuongCoBan.setWidth(new Extent(75));
		basicGrid.add(txtLuongCoBan);
		basicGrid.add(new Label("Lương hợp đồng"));
		txtLuongHopDong = new DscField();
		txtLuongHopDong.setWidth(new Extent(75));
		basicGrid.add(txtLuongHopDong);
		basicGrid.add(new Label("Phụ cấp chức vụ"));
		txtPhuCapChucVu = new DscField();
		txtPhuCapChucVu.setWidth(new Extent(75));
		basicGrid.add(txtPhuCapChucVu);
		basicGrid.add(new Label("Lương CBBQ"));
		txtLuongCBBinhQuan = new DscField();
		txtLuongCBBinhQuan.setWidth(new Extent(75));
		basicGrid.add(txtLuongCBBinhQuan);
		basicGrid.add(new Label("Phụ cấp CBBQ"));
		txtPhuCapCBBinhQuan = new DscField();
		txtPhuCapCBBinhQuan.setWidth(new Extent(75));
		basicGrid.add(txtPhuCapCBBinhQuan);
		basicGrid.add(new Label("Đợt thôi việc"));
		txtDotThoiViec = new DscField();
		txtDotThoiViec.setWidth(new Extent(75));
		txtDotThoiViec.setBackground(new Color(0xCCFFCC));
		basicGrid.add(txtDotThoiViec);
		for (int i = 0; i < basicGrid.getComponentCount(); i++) {
			if (i % 2 == 0) {
				basicGrid.getComponent(i).setLayoutData(beautyLayout);
			}
		}
		// Grid secondColGrid = new Grid(1);
		// secondColGrid.setBorder(new Border(new Extent(1), Color.BLUE,
		// Border.STYLE_SOLID));
		// GridLayoutData tempLayout = new GridLayoutData();
		// tempLayout.setAlignment(new Alignment(Alignment.DEFAULT,
		// Alignment.TOP));
		// secondColGrid.setLayoutData(tempLayout);
		// groupBoxGrid.add(secondColGrid);
		// Tro cap nghi viec
		GroupBox secondGroup = new GroupBox("Trợ cấp nghỉ việc");
		// secondGroup.setInsets(new Insets(3, 5, 3, 5));
		secondGroup.setLayoutData(groupBoxLayout);
		secondGroup.setHeight(new Extent(160));
		groupBoxGrid.add(secondGroup);
		Grid bonusGrid = new Grid(4);
		secondGroup.add(bonusGrid);

		bonusGrid.add(new Label("Số tháng hưởng trợ cấp"));
		txtSoThangHuongTroCap = new DscField();
		txtSoThangHuongTroCap.setWidth(new Extent(75));
		bonusGrid.add(txtSoThangHuongTroCap);
		bonusGrid.add(new Label("TT st hưởng trợ cấp"));
		txtThanhTienHuongTroCap = new DscField();
		txtThanhTienHuongTroCap.setWidth(new Extent(75));
		bonusGrid.add(txtThanhTienHuongTroCap);
		bonusGrid.add(new Label("Số ngày PN còn lại"));
		txtSoNgayPhepNamConLai = new DscField();
		txtSoNgayPhepNamConLai.setWidth(new Extent(75));
		bonusGrid.add(txtSoNgayPhepNamConLai);
		bonusGrid.add(new Label("TT phép năm còn lại"));
		txtThanhTienPhepNamConLai = new DscField();
		txtThanhTienPhepNamConLai.setWidth(new Extent(75));
		bonusGrid.add(txtThanhTienPhepNamConLai);

		bonusGrid.add(new Label("Số tháng đền bù"));
		txtSoThangDenBu = new DscField();
		txtSoThangDenBu.setWidth(new Extent(75));
		bonusGrid.add(txtSoThangDenBu);
		bonusGrid.add(new Label("TT số tháng đền bù"));
		txtThanhTienSoThangDenBu = new DscField();
		txtThanhTienSoThangDenBu.setWidth(new Extent(75));
		bonusGrid.add(txtThanhTienSoThangDenBu);
		// bonusGrid.add(new Label("Khấu trừ khác"));
		// txtKhauTruKhac = new DscField();
		// txtKhauTruKhac.setWidth(new Extent(75));
		// bonusGrid.add(txtKhauTruKhac);

		// bonusGrid.add(new Label("Tổng lương hiện tại"));
		// txtTongLuongHienTai = new DscField();
		// txtTongLuongHienTai.setWidth(new Extent(75));
		// bonusGrid.add(txtTongLuongHienTai);

		for (int i = 0; i < bonusGrid.getComponentCount(); i++) {
			if (i % 2 == 0) {
				bonusGrid.getComponent(i).setLayoutData(beautyLayout);
			}
		}
		// summary
		GroupBox fourthGroup = new GroupBox("Thống kê");
		fourthGroup.setInsets(new Insets(0, 10, 0, 10));
		GridLayoutData summaryGroupLayout = new GridLayoutData();
		summaryGroupLayout.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.BOTTOM));
		fourthGroup.setLayoutData(summaryGroupLayout);
		groupBoxGrid.add(fourthGroup);
		Grid fourthGrid = new Grid(6);
		fourthGroup.add(fourthGrid);
		// --row1
		fourthGrid.add(new Label());
		fourthGrid.add(new Label("Tổng lương CB"));
		fourthGrid.add(new Label());
		fourthGrid.add(new Label("Tiền tăng ca"));
		fourthGrid.add(new Label());
		fourthGrid.add(new Label("Tổng phụ cấp"));
		// --row2
		fourthGrid.add(new Label());
		txtTongLuongCoBan = new DscField();
		txtTongLuongCoBan.setWidth(new Extent(90));
		fourthGrid.add(txtTongLuongCoBan);
		Label plusLabel = new Label("+");
		plusLabel.setForeground(Color.RED);
		fourthGrid.add(plusLabel);
		txtTienTangCa = new DscField();
		txtTienTangCa.setWidth(new Extent(90));
		fourthGrid.add(txtTienTangCa);
		Label plusLabel1 = new Label("+");
		plusLabel1.setForeground(Color.RED);
		fourthGrid.add(plusLabel1);
		txtTongPhuCap = new DscField();
		txtTongPhuCap.setWidth(new Extent(90));
		fourthGrid.add(txtTongPhuCap);
		// --row3
		fourthGrid.add(new Label());
		fourthGrid.add(new Label());
		fourthGrid.add(new Label());
		fourthGrid.add(new Label("Khấu trừ"));
		fourthGrid.add(new Label());
		fourthGrid.add(new Label("Lương tháng HT"));
		// --row4
		Label equalLabel = new Label("=");
		equalLabel.setForeground(Color.RED);
		fourthGrid.add(equalLabel);
		txtBonusAll = new DscField();
		txtBonusAll.setWidth(new Extent(90));
		fourthGrid.add(txtBonusAll);
		Label minusLabel = new Label("-");
		minusLabel.setForeground(Color.RED);
		fourthGrid.add(minusLabel);
		txtKhauTru = new DscField();
		txtKhauTru.setWidth(new Extent(90));
		fourthGrid.add(txtKhauTru);
		Label equalLabel1 = new Label("=");
		equalLabel1.setForeground(Color.RED);
		fourthGrid.add(equalLabel1);
		txtLuongThangHienTai = new DscField();
		txtLuongThangHienTai.setWidth(new Extent(90));
		txtLuongThangHienTai.setBackground(new Color(0xCCFFFF));
		txtLuongThangHienTai.setDisabledForeground(Color.RED);
		txtLuongThangHienTai.setForeground(Color.RED);
		fourthGrid.add(txtLuongThangHienTai);
		// row 5
		fourthGrid.add(new Label());
		fourthGrid.add(new Label("Tổng trợ cấp"));
		fourthGrid.add(new Label());
		fourthGrid.add(new Label("Thực nhận"));
		fourthGrid.add(new Label());
		fourthGrid.add(new Label());
		// row 6
		Label plusLabel3 = new Label("+");
		plusLabel3.setForeground(Color.RED);
		fourthGrid.add(plusLabel3);
		txtTongTroCapNghiViec = new DscField();
		txtTongTroCapNghiViec.setBackground(new Color(0xCCFFFF));
		txtTongTroCapNghiViec.setDisabledForeground(Color.RED);
		txtTongTroCapNghiViec.setForeground(Color.RED);
		txtTongTroCapNghiViec.setWidth(new Extent(90));
		fourthGrid.add(txtTongTroCapNghiViec);
		Label equalLabel2 = new Label("=");
		equalLabel2.setForeground(Color.RED);
		fourthGrid.add(equalLabel2);
		GridLayoutData thucNhanLayout = new GridLayoutData();
		thucNhanLayout.setColumnSpan(3);
		txtThucNhan = new DscField();
		txtThucNhan.setLayoutData(thucNhanLayout);
		txtThucNhan.setWidth(new Extent(193));
		txtThucNhan.setForeground(Color.RED);
		txtThucNhan.setDisabledForeground(Color.RED);
		txtThucNhan.setBackground(Color.YELLOW);
		txtThucNhan.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.CENTER));
		fourthGrid.add(txtThucNhan);
		return content;
	}

	private ContentPane chiTietThoiGianLamViecTabContent() {
		ContentPane content = new ContentPane();
		content.setEnabled(false);
		TabPaneLayoutData layout = new TabPaneLayoutData();
		layout.setTitle("Chi tiết thời gian làm việc");
		content.setLayoutData(layout);
		// GroupBox Layout
		Grid groupBoxGrid = new Grid(3);
		// groupBoxGrid.setWidth(new Extent(800));
		// groupBoxGrid.setBorder(new Border(1, Color.MAGENTA,
		// Border.STYLE_SOLID));
		content.add(groupBoxGrid);
		// layout
		GridLayoutData groupBoxLayout = new GridLayoutData();
		groupBoxLayout
				.setAlignment(new Alignment(Alignment.LEFT, Alignment.TOP));
		// Bieu thoi gian
		GridLayoutData firstGridLayout = new GridLayoutData();
		firstGridLayout.setAlignment(new Alignment(Alignment.LEFT,
				Alignment.TOP));
		firstGridLayout.setRowSpan(2);
		GroupBox firstGroup = new GroupBox("Biểu thời gian");
		firstGroup.setHeight(new Extent(430));
		firstGroup.setLayoutData(firstGridLayout);
		groupBoxGrid.add(firstGroup);
		Grid firstGrid = new Grid(3);
		firstGroup.add(firstGrid);
		// First grid elements
		firstGrid.add(new Label("Số ngày làm"));
		txtSoNgayLam = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtSoNgayLam.setWidth(new Extent(30));
		firstGrid.add(txtSoNgayLam);
		firstGrid.add(new Label());
		// ---Tang ca ngoai label layout
		// GridLayoutData tangCaNgoaiLabelLayout = new GridLayoutData();
		// tangCaNgoaiLabelLayout.setRowSpan(2);
		// tangCaNgoaiLabelLayout.setAlignment(new Alignment(Alignment.CENTER,
		// Alignment.CENTER));
		// tangCaNgoaiLabel.setLayoutData(tangCaNgoaiLabelLayout);
		firstGrid.add(new Label("Số đêm làm"));
		txtSoDemLam = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtSoDemLam.setWidth(new Extent(30));
		firstGrid.add(txtSoDemLam);
		Label tangCaNgoaiLabel = new Label("Tăng ca ngoài");
		firstGrid.add(tangCaNgoaiLabel);
		firstGrid.add(new Label("Tăng ca ngày"));
		txtTangCaNgay = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtTangCaNgay.setWidth(new Extent(30));
		firstGrid.add(txtTangCaNgay);

		// --Tang ca ngoai text layout
		GridLayoutData tangCaNgoaiTxtLayout = new GridLayoutData();
		tangCaNgoaiTxtLayout.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.CENTER));
		txtTangCaNgayNgoai = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtTangCaNgayNgoai.setWidth(new Extent(30));
		txtTangCaNgayNgoai.setBackground(new Color(0xFFFF99));
		txtTangCaNgayNgoai.setLayoutData(tangCaNgoaiTxtLayout);
		firstGrid.add(txtTangCaNgayNgoai);

		firstGrid.add(new Label("Tăng ca đêm"));
		txtTangCaDem = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtTangCaDem.setWidth(new Extent(30));
		firstGrid.add(txtTangCaDem);
		txtTangCaDemNgoai = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtTangCaDemNgoai.setWidth(new Extent(30));
		txtTangCaDemNgoai.setBackground(new Color(0xFFFF99));
		txtTangCaDemNgoai.setLayoutData(tangCaNgoaiTxtLayout);
		firstGrid.add(txtTangCaDemNgoai);

		firstGrid.add(new Label("Tăng ca chủ nhật"));
		txtTangCaChuNhat = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtTangCaChuNhat.setWidth(new Extent(30));
		firstGrid.add(txtTangCaChuNhat);
		txtTangCaChuNhatNgoai = new DscField(
				DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtTangCaChuNhatNgoai.setWidth(new Extent(30));
		txtTangCaChuNhatNgoai.setBackground(new Color(0xFFFF99));
		txtTangCaChuNhatNgoai.setLayoutData(tangCaNgoaiTxtLayout);
		firstGrid.add(txtTangCaChuNhatNgoai);

		firstGrid.add(new Label("Tăng ca lễ"));
		txtTangCaLe = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtTangCaLe.setWidth(new Extent(30));
		firstGrid.add(txtTangCaLe);
		txtTangCaLeNgoai = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtTangCaLeNgoai.setWidth(new Extent(30));
		txtTangCaLeNgoai.setBackground(new Color(0xFFFF99));
		txtTangCaLeNgoai.setLayoutData(tangCaNgoaiTxtLayout);
		firstGrid.add(txtTangCaLeNgoai);

		firstGrid.add(new Label("Số đêm tăng ca"));
		txtSoDemTangCa = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtSoDemTangCa.setWidth(new Extent(30));
		firstGrid.add(txtSoDemTangCa);
		txtSoDemTangCaNgoai = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtSoDemTangCaNgoai.setWidth(new Extent(30));
		txtSoDemTangCaNgoai.setBackground(new Color(0xFFFF99));
		txtSoDemTangCaNgoai.setLayoutData(tangCaNgoaiTxtLayout);
		firstGrid.add(txtSoDemTangCaNgoai);

		firstGrid.add(new Label("Ký tên"));
		txtKyTen = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtKyTen.setWidth(new Extent(30));
		firstGrid.add(txtKyTen);
		firstGrid.add(new Label());
		firstGrid.add(new Label("Trể/sớm"));
		txtTreSom = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtTreSom.setWidth(new Extent(30));
		firstGrid.add(txtTreSom);
		firstGrid.add(new Label());
		// Phep
		// Label phepLabelSeparator = new Label("-----Phép-----");
		// GridLayoutData phepLabelLayout = new GridLayoutData();
		// phepLabelLayout.setAlignment(new Alignment(Alignment.CENTER,
		// Alignment.CENTER));
		// phepLabelLayout.setColumnSpan(3);
		// phepLabelSeparator.setLayoutData(phepLabelLayout);
		// firstGrid.add(phepLabelSeparator);
		firstGrid.add(new Label("-----"));
		firstGrid.add(new Label("Phép"));
		firstGrid.add(new Label("-----"));

		firstGrid.add(new Label("Có phép"));
		txtCoPhep = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtCoPhep.setWidth(new Extent(30));
		firstGrid.add(txtCoPhep);
		firstGrid.add(new Label());
		firstGrid.add(new Label("Có lương"));
		txtCoLuong = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtCoLuong.setWidth(new Extent(30));
		firstGrid.add(txtCoLuong);
		firstGrid.add(new Label());
		firstGrid.add(new Label("Phép bệnh"));
		txtPhepBenh = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtPhepBenh.setWidth(new Extent(30));
		firstGrid.add(txtPhepBenh);
		firstGrid.add(new Label());
		firstGrid.add(new Label("Kháng công"));
		txtKhangCong = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtKhangCong.setWidth(new Extent(30));
		firstGrid.add(txtKhangCong);
		firstGrid.add(new Label());
		firstGrid.add(new Label("Dưỡng sức"));
		txtDuongSuc = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtDuongSuc.setWidth(new Extent(30));
		firstGrid.add(txtDuongSuc);
		firstGrid.add(new Label());
		firstGrid.add(new Label("Nghỉ sản"));
		txtNghiSan = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtNghiSan.setWidth(new Extent(30));
		firstGrid.add(txtNghiSan);
		firstGrid.add(new Label());
		for (int i = 0; i < firstGrid.getComponentCount(); i++) {
			if (i % 3 == 0) {
				firstGrid.getComponent(i).setLayoutData(beautyLayout);
			}
		}
		// Thuong/phu cap
		GroupBox secondGroup = new GroupBox("Thưởng/phụ cấp");
		secondGroup.setHeight(new Extent(430));
		secondGroup.setLayoutData(groupBoxLayout);
		groupBoxGrid.add(secondGroup);
		Grid secondGrid = new Grid(4);
		secondGroup.add(secondGrid);
		// Second grid elements
		secondGrid.add(new Label("Tiền thưởng"));
		txtTienThuong = new DscField();
		txtTienThuong.setWidth(new Extent(60));
		secondGrid.add(txtTienThuong);
		secondGrid.add(new Label());
		txtTienThuong1 = new DscField();
		txtTienThuong1.setWidth(new Extent(60));
		secondGrid.add(txtTienThuong1);
		secondGrid.add(new Label("Phụ cấp chức vụ"));
		txtPhuCapChucVu1 = new DscField();
		txtPhuCapChucVu1.setWidth(new Extent(60));
		secondGrid.add(txtPhuCapChucVu1);
		secondGrid.add(new Label());
		txtPhuCapChucVu11 = new DscField();
		txtPhuCapChucVu11.setWidth(new Extent(60));
		secondGrid.add(txtPhuCapChucVu11);
		secondGrid.add(new Label("Phụ cấp công việc"));
		txtPhuCapCongViec = new DscField();
		txtPhuCapCongViec.setWidth(new Extent(60));
		secondGrid.add(txtPhuCapCongViec);
		secondGrid.add(new Label());
		txtPhuCapCongViec1 = new DscField();
		txtPhuCapCongViec1.setWidth(new Extent(60));
		secondGrid.add(txtPhuCapCongViec1);
		secondGrid.add(new Label("Phụ cấp sinh hoạt"));
		txtPhuCapSinhHoat = new DscField();
		txtPhuCapSinhHoat.setWidth(new Extent(60));
		secondGrid.add(txtPhuCapSinhHoat);
		// ----
		GridLayoutData phuCapSinhHoatLayout = new GridLayoutData();
		phuCapSinhHoatLayout.setRowSpan(2);
		Label plusLabel0 = new Label("+");
		plusLabel0.setForeground(Color.RED);
		plusLabel0.setLayoutData(phuCapSinhHoatLayout);
		secondGrid.add(plusLabel0);
		txtPhuCapSinhHoat1 = new DscField();
		txtPhuCapSinhHoat1.setWidth(new Extent(60));
		txtPhuCapSinhHoat1.setLayoutData(phuCapSinhHoatLayout);
		secondGrid.add(txtPhuCapSinhHoat1);
		secondGrid.add(new Label("Phụ cấp làm 8h"));
		txtPhuCapLam8H = new DscField();
		txtPhuCapLam8H.setWidth(new Extent(60));
		secondGrid.add(txtPhuCapLam8H);
		secondGrid.add(new Label("Chuyên cần"));
		txtChuyenCan = new DscField();
		txtChuyenCan.setWidth(new Extent(60));
		secondGrid.add(txtChuyenCan);
		secondGrid.add(new Label());
		secondGrid.add(new Label());
		secondGrid.add(new Label("Phụ cấp tiền cơm"));
		txtPhuCapTienCom = new DscField();
		txtPhuCapTienCom.setWidth(new Extent(60));
		secondGrid.add(txtPhuCapTienCom);
		secondGrid.add(new Label());
		secondGrid.add(new Label());
		secondGrid.add(new Label("Chênh lệch PC tăng ca"));
		txtChenhLechPhuCapTangCa = new DscField();
		txtChenhLechPhuCapTangCa.setWidth(new Extent(60));
		secondGrid.add(txtChenhLechPhuCapTangCa);
		secondGrid.add(new Label());
		secondGrid.add(new Label());
		secondGrid.add(new Label("Chuyên cần tăng ca"));
		txtChuyenCanTangCa = new DscField();
		txtChuyenCanTangCa.setWidth(new Extent(60));
		secondGrid.add(txtChuyenCanTangCa);
		secondGrid.add(new Label());
		secondGrid.add(new Label());
		secondGrid.add(new Label("Bù lương"));
		txtBuLuong = new DscField();
		txtBuLuong.setWidth(new Extent(60));
		secondGrid.add(txtBuLuong);
		secondGrid.add(new Label());
		secondGrid.add(new Label());
		secondGrid.add(new Label("Bù bảo hiểm"));
		txtBuBaoHiem = new DscField();
		txtBuBaoHiem.setWidth(new Extent(60));
		secondGrid.add(txtBuBaoHiem);
		secondGrid.add(new Label());
		secondGrid.add(new Label());
		for (int i = 0; i < 18; i++) {
			if (i % 4 == 0) {
				secondGrid.getComponent(i).setLayoutData(beautyLayout);
			}
		}
		for (int i = 18; i < secondGrid.getComponentCount(); i++) {
			if ((i + 2) % 4 == 0) {
				secondGrid.getComponent(i).setLayoutData(beautyLayout);
			}
		}
		// Khau tru
		GroupBox thirdGroup = new GroupBox("Khấu trừ");
		thirdGroup.setHeight(new Extent(430));
		thirdGroup.setLayoutData(groupBoxLayout);
		groupBoxGrid.add(thirdGroup);
		Grid thirdGrid = new Grid();
		thirdGroup.add(thirdGrid);
		// Third grid elements
		thirdGrid.add(new Label("Nghỉ trước thời hạn"));
		txtNghiTruocThoiHan = new DscField();
		txtNghiTruocThoiHan.setWidth(new Extent(60));
		thirdGrid.add(txtNghiTruocThoiHan);
		thirdGrid.add(new Label("TT nghỉ trước thời hạn"));
		txtThanhTienNghiTruocThoiHan = new DscField();
		txtThanhTienNghiTruocThoiHan.setWidth(new Extent(60));
		thirdGrid.add(txtThanhTienNghiTruocThoiHan);
		thirdGrid.add(new Label("Bảo hiểm xã hội"));
		txtBaoHiemXaHoi = new DscField();
		txtBaoHiemXaHoi.setWidth(new Extent(60));
		thirdGrid.add(txtBaoHiemXaHoi);
		thirdGrid.add(new Label("Bù bảo hiểm quý"));
		txtBuBaoHiemQuy = new DscField();
		txtBuBaoHiemQuy.setWidth(new Extent(60));
		thirdGrid.add(txtBuBaoHiemQuy);
		thirdGrid.add(new Label("Bù bảo hiểm y tế"));
		txtBuBaoHiemYTe = new DscField();
		txtBuBaoHiemYTe.setWidth(new Extent(60));
		thirdGrid.add(txtBuBaoHiemYTe);
		thirdGrid.add(new Label("Thu bảo hiểm y tế"));
		txtThuBaoHiemYTe = new DscField();
		txtThuBaoHiemYTe.setWidth(new Extent(60));
		thirdGrid.add(txtThuBaoHiemYTe);
		thirdGrid.add(new Label("Bảo hiểm y tế"));
		txtBaoHiemYTe = new DscField();
		txtBaoHiemYTe.setWidth(new Extent(60));
		thirdGrid.add(txtBaoHiemYTe);
		thirdGrid.add(new Label("Bảo hiểm thất nghiệp"));
		txtBaoHiemThatNghiep = new DscField();
		txtBaoHiemThatNghiep.setWidth(new Extent(60));
		thirdGrid.add(txtBaoHiemThatNghiep);
		thirdGrid.add(new Label("Đoàn phí"));
		txtDoanPhi = new DscField();
		txtDoanPhi.setWidth(new Extent(60));
		thirdGrid.add(txtDoanPhi);
		thirdGrid.add(new Label("Tạm ứng"));
		txtTamUng = new DscField();
		txtTamUng.setWidth(new Extent(60));
		thirdGrid.add(txtTamUng);
		thirdGrid.add(new Label("Khấu trừ khác"));
		txtKhauTruKhac = new DscField();
		txtKhauTruKhac.setWidth(new Extent(60));
		thirdGrid.add(txtKhauTruKhac);
		thirdGrid.add(new Label("Sổ tay nhân viên"));
		txtSoTayNhanVien = new DscField();
		txtSoTayNhanVien.setWidth(new Extent(60));
		thirdGrid.add(txtSoTayNhanVien);
		thirdGrid.add(new Label("Thuế thu nhập"));
		txtThueThuNhap = new DscField();
		txtThueThuNhap.setWidth(new Extent(60));
		thirdGrid.add(txtThueThuNhap);
		for (int i = 0; i < thirdGrid.getComponentCount(); i++) {
			if (i % 2 == 0) {
				thirdGrid.getComponent(i).setLayoutData(beautyLayout);
			}
		}
		// Summary

		return content;
	}

	public void bindData(ATTQUIT att) {
		// Thong tin co ban
		txtSoThe.setText(att.getEMPSN());
		txtHoTen.setText(Vni2Uni.convertToUnicode(att.getEMPNA()));
		txtHoTen.setToolTipText(Vni2Uni.convertToUnicode(att.getEMPNA()));
		txtChucVu.setText(att.getPOSSN());
		txtDonVi.setText(att.getDEPSN());
		txtTenDonVi.setText(att.getDEPSN());
		txtNgayNhapXuong.setText(sdf.format(att.getHIRED()));
		txtLuongCoBan.setText(String.valueOf(att.getBSALY()));
		txtLuongHopDong.setText(String.valueOf(att.getCOMBSALY()));
		txtPhuCapChucVu.setText(String.valueOf(att.getBONUS2()));
		txtLuongCBBinhQuan.setText(String.valueOf(att.getBSAL_AVG()));
		txtPhuCapCBBinhQuan.setText(String.valueOf(att.getBONUS2_AVG()));
		// Tro cap thoi viec
		txtDotThoiViec.setText(sdf.format(att.getDOT_TV()));
		txtSoThangHuongTroCap.setText(String.valueOf(att.getMM_TROCAP()));
		txtThanhTienHuongTroCap.setText(String.valueOf(att.getMM_TROCAP_S()));
		txtSoNgayPhepNamConLai.setText(String.valueOf(att.getPN_CONLAI()));
		txtThanhTienPhepNamConLai.setText(String.valueOf(att.getPN_CONLAI_S()));
		txtNghiTruocThoiHan.setText(String.valueOf(att.getDD_NGHITRUOC()));
		txtThanhTienNghiTruocThoiHan.setText(String.valueOf(att
				.getDD_NGHITRUOC_S()));
		txtSoThangDenBu.setText(String.valueOf(att.getMM_DENBU()));
		txtThanhTienSoThangDenBu.setText(String.valueOf(att.getMM_DENBU_S()));
		txtSoTayNhanVien.setText(String.valueOf(att.getSTNV()));
		txtKhauTruKhac.setText(String.valueOf(att.getKQT()));
		txtTongTroCapNghiViec.setText(String.valueOf(att.getTT_TCTVIEC()));
		// txtTongLuongHienTai.setText(String.valueOf(att.getTS_HIENTAI()));
		DecimalFormat format = new DecimalFormat("###,###.###");
		txtThucNhan.setText(format.format(att.getTTS().doubleValue()));

		// Bieu thoi gian
		txtSoNgayLam.setText(String.valueOf(att.getDUCLS()));
		txtSoDemLam.setText(String.valueOf(att.getNUCLS()));
		txtTangCaNgay.setText(String.valueOf(att.getADDCLS1()));
		txtTangCaDem.setText(String.valueOf(att.getNADDCLS()));
		txtTangCaChuNhat.setText(String.valueOf(att.getADDHOL()));
		txtTangCaLe.setText(String.valueOf(att.getADDHOLN()));
		// txtSoDemTangCa.setText(att.getACN().toString());
		txtKyTen.setText(String.valueOf(att.getSIGN()));
		txtTreSom.setText(String.valueOf(att.getLATE()));
		// Phep
		txtCoPhep.setText(String.valueOf(att.getREST()));
		txtCoLuong.setText(String.valueOf(att.getREST_PAY()));
		txtPhepBenh.setText(String.valueOf(att.getREST_SICK()));
		txtKhangCong.setText(String.valueOf(att.getNWHOUR()));
		txtDuongSuc.setText(String.valueOf(att.getOTHER()));
		txtNghiSan.setText(String.valueOf(att.getPHEP_NS()));
		txtTangCaNgayNgoai.setText(String.valueOf(att.getADDCLS1_O()));
		txtTangCaDemNgoai.setText(String.valueOf(att.getNADDCLS_O()));
		txtTangCaChuNhatNgoai.setText(String.valueOf(att.getADDHOL_O()));
		txtTangCaLeNgoai.setText(String.valueOf(att.getADDHOLN_O()));
		txtSoDemTangCaNgoai.setText(String.valueOf(att.getACNM_O()));
		//
		txtTienThuong.setText(String.valueOf(att.getBONUS1()));
		txtPhuCapChucVu1.setText(String.valueOf(att.getBONUS2()));
		txtPhuCapCongViec.setText(String.valueOf(att.getBONUS3()));
		txtPhuCapSinhHoat.setText(String.valueOf(att.getBONUS4()));

		txtChuyenCan.setText(String.valueOf(att.getBONUS6()));
		txtPhuCapTienCom.setText(String.valueOf(att.getBONUS7()));
		txtChenhLechPhuCapTangCa.setText(String.valueOf(att.getBSALY_N()));
		txtChuyenCanTangCa.setText(String.valueOf(att.getBONUS_ACN()));
		txtBuLuong.setText(String.valueOf(att.getBONUS5()));
		txtBuBaoHiem.setText(String.valueOf(att.getBONUS9()));

		txtBaoHiemXaHoi.setText(String.valueOf(att.getJOININSU()));
		txtBuBaoHiemQuy.setText(att.getBH_QUY() == null ? "" : String
				.valueOf(att.getBH_QUY()));
		txtBuBaoHiemYTe.setText(String.valueOf(att.getBU_BHYT()));
		txtThuBaoHiemYTe.setText(String.valueOf(att.getTHU_BHYT()));
		txtBaoHiemYTe.setText(String.valueOf(att.getYLBX()));
		txtBaoHiemThatNghiep.setText(String.valueOf(att.getBH_TNGHIEP()));
		txtDoanPhi.setText(String.valueOf(att.getJOINLUM()));
		txtTamUng.setText(String.valueOf(att.getBORM()));
		// txtKhauTruKhac1.setText(String.valueOf(att.getKQT()));
		txtThueThuNhap.setText(String.valueOf(att.getPAYTAX()));

		txtTongLuongCoBan.setText(String.valueOf(att.getTBASIC()));
		txtTienTangCa.setText(String.valueOf(att.getTADDS()));
		txtTongPhuCap.setText(String.valueOf(att.getTBONUS()));
		txtBonusAll.setText(String.valueOf(att.getTINCOME()));
		txtKhauTru.setText(String.valueOf(att.getTKQ()));
		txtLuongThangHienTai.setText(String.valueOf(att.getTS_HIENTAI1()));
	}

}
