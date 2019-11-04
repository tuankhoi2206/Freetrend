package ds.program.fvhr.ui.quitworksalary;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.layout.GridLayoutData;
import ds.program.fvhr.dao.quitsalary.QuitWorkSalaryDAO;
import ds.program.fvhr.domain.ATTENDANTDB_QUIT;
import dsc.echo2app.component.DscField;

public class QuitWorkExcelInputPane extends ContentPane {
	private static final long serialVersionUID = 1L;

	// empsn
	private DscField txtSoThe;

	// dep_kt
	private DscField txtDonVi;

	// bsaly
	private DscField txtLuongCoBan;

	// combsaly
	private DscField txtLuongHopDong;

	// bonus1
	private DscField txtTienThuong;

	// bonus2
	private DscField txtPhuCapChucVu;

	// bonus3
	private DscField txtPhuCapCongViec;

	// bonus4
	private DscField txtPhuCapSinhHoat;

	// bonus8
	private DscField txtPhuCapKhac;

	// bonus5
	private DscField txtBuLuongThangTruoc;

	// borm
	private DscField txtTamUng;

	// kqt
	private DscField txtKhauTruKhac;

	// joinlum
	private DscField txtPhiCongDoan;

	// ylbx
	private DscField txtBaoHiemYT;

	// joininsu
	private DscField txtBaoHiemXH;

	// bh_tnghiep
	private DscField txtBaoHiemTN;

	// bonus9
	private DscField txtBuBH19;

	// temp1
	private DscField txtLuongNuaThangDau;

	// temp2
	private DscField txtLuongNuaThangCuoi;

	// bac
	private DscField txtSoNguoiPhuThuoc;

	// bsaly_avg
	private DscField txtLuongCBBinhQuan;

	// bonus2_avg
	private DscField txtPhuCapChucVuBinhQuan;

	// m_denbu
	private DscField txtSoThangDenBu;

	// pn_conlai
	private DscField txtPhepNamConLai;

	// m_trocap
	private DscField txtSoThangTroCap;

	// d_nghitruoc
	private DscField txtNghiTruocThoiHan;

	// Bu_bh
	private DscField txtBuBaoHiem;

	// thu_bh
	private DscField txtThuBaoHiem;

	// buthu_bh
	private DscField txtBuThuBaoHiem;

	// noteprivate DscField note_bh
	private DscField txtGhiChu;

	// dot_tv
	private DscField txtDotThoiViec;

	// addcls1_o
	private DscField txtTangCaNgayNgoai;

	// naddcls_o
	private DscField txtTangCaDemNgoai;

	// addhol_o
	private DscField txtTangCaChuNhatNgoai;

	// addholn_o
	private DscField txtTangCaLeNgoai;

	// acnm_o
	private DscField txtSoDemTienComNgoai;

	// depsn_bhyt
	private DscField txtDepsnBHYT;

	private SelectField sfType;

	private QuitWorkSalaryProgram program;

	private QuitWorkSalaryBrowserContent browserContent;

	private Grid rootLayout;

	public QuitWorkExcelInputPane(QuitWorkSalaryProgram program) {
		super();
		this.program = program;
		this.browserContent = program.getBrowserContent();
		initComponents();
	}

	/**
	 * Tải thông tin đã import từ file excel trong bảng ATTENDANTDB_QUIT (bảng
	 * nào đây)<br/>
	 */
	public void getImportedExcelData() {
		QuitWorkSalaryDAO dao = browserContent.getDao();
		ATTENDANTDB_QUIT att = dao.getAttQuitData("");
	}

	private void initComponents() {
		rootLayout = new Grid(6);
		add(rootLayout);
		// empsn
		txtSoThe = new DscField();
		txtSoThe.setMaximumLength(8);
		// txtSoThe.setWidth(new Extent(65));
		// dep_kt
		txtDonVi = new DscField();
		txtDonVi.setMaximumLength(30);
		// bsaly
		txtLuongCoBan = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtLuongCoBan.setMaximumLength(8);
		// txtLuongCoBan.setWidth(new Extent(65));
		// combsaly
		txtLuongHopDong = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtLuongHopDong.setMaximumLength(8);
		// txtLuongHopDong.setWidth(new Extent(65));
		// bonus1
		txtTienThuong = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtTienThuong.setMaximumLength(8);
		// txtTienThuong.setWidth(new Extent(65));
		// bonus2
		txtPhuCapChucVu = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtPhuCapChucVu.setMaximumLength(8);
		// txtPhuCapChucVu.setWidth(new Extent(65));
		// bonus3
		txtPhuCapCongViec = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtPhuCapCongViec.setMaximumLength(8);
		// txtPhuCapCongViec.setWidth(new Extent(65));
		// bonus4
		txtPhuCapSinhHoat = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtPhuCapSinhHoat.setMaximumLength(8);
		// txtPhuCapSinhHoat.setWidth(new Extent(65));
		// bonus9
		txtPhuCapKhac = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtPhuCapKhac.setMaximumLength(8);
		// txtPhuCapKhac.setWidth(new Extent(65));
		// bonus5
		txtBuLuongThangTruoc = new DscField(
				DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtBuLuongThangTruoc.setMaximumLength(8);
		// txtBuLuongThangTruoc.setWidth(new Extent(65));
		// borm
		txtTamUng = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtTamUng.setMaximumLength(8);
		// txtTamUng.setWidth(new Extent(65));
		// kqt
		txtKhauTruKhac = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtKhauTruKhac.setMaximumLength(8);
		// txtKhauTruKhac.setWidth(new Extent(65));
		// joinlum
		txtPhiCongDoan = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtPhiCongDoan.setMaximumLength(5);
		// txtPhiCongDoan.setWidth(new Extent(65));
		// ylbx
		txtBaoHiemYT = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtBaoHiemYT.setMaximumLength(8);
		// txtBaoHiemYT.setWidth(new Extent(65));
		// joininsu
		txtBaoHiemXH = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtBaoHiemXH.setMaximumLength(7);
		// txtBaoHiemXH.setWidth(new Extent(65));
		// bh_tnghiep
		txtBaoHiemTN = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtBaoHiemTN.setMaximumLength(8);
		// txtBaoHiemTN.setWidth(new Extent(65));
		// bonus9
		txtBuBH19 = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtBuBH19.setMaximumLength(8);
		// txtBuBH19.setWidth(new Extent(65));
		// temp1
		txtLuongNuaThangDau = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtLuongNuaThangDau.setMaximumLength(8);
		// txtLuongNuaThangDau.setWidth(new Extent(65));
		// temp2
		txtLuongNuaThangCuoi = new DscField(
				DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtLuongNuaThangCuoi.setMaximumLength(8);
		// txtLuongNuaThangCuoi.setWidth(new Extent(65));
		// bac
		txtSoNguoiPhuThuoc = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtSoNguoiPhuThuoc.setMaximumLength(2);
		// txtSoNguoiPhuThuoc.setWidth(new Extent(15));
		// bsaly_avg
		txtLuongCBBinhQuan = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtLuongCBBinhQuan.setMaximumLength(8);
		// txtLuongCBBinhQuan.setWidth(new Extent(65));
		// bonus2_avg
		txtPhuCapChucVuBinhQuan = new DscField(
				DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtPhuCapChucVuBinhQuan.setMaximumLength(8);
		// txtPhuCapChucVuBinhQuan.setWidth(new Extent(65));
		// m_denbu
		txtSoThangDenBu = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtSoThangDenBu.setMaximumLength(9);
		// txtSoThangDenBu.setWidth(new Extent(65));
		// pn_conlai
		txtPhepNamConLai = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtPhepNamConLai.setMaximumLength(3);
		// txtPhepNamConLai.setWidth(new Extent(65));
		// m_trocap
		txtSoThangTroCap = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtSoThangTroCap.setMaximumLength(9);
		// txtSoThangTroCap.setWidth(new Extent(65));
		// d_nghitruoc
		txtNghiTruocThoiHan = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtNghiTruocThoiHan.setMaximumLength(9);
		// txtNghiTruocThoiHan.setWidth(new Extent(65));
		// Bu_bh
		txtBuBaoHiem = new DscField(DscField.INPUT_TYPE_INTEGER);
		txtBuBaoHiem.setMaximumLength(8);
		// txtBuBaoHiem.setWidth(new Extent(65));
		// thu_bh
		txtThuBaoHiem = new DscField(DscField.INPUT_TYPE_INTEGER);
		txtThuBaoHiem.setMaximumLength(8);
		// txtThuBaoHiem.setWidth(new Extent(65));
		// buthu_bh
		txtBuThuBaoHiem = new DscField(DscField.INPUT_TYPE_INTEGER);
		txtBuThuBaoHiem.setMaximumLength(8);
		// txtBuThuBaoHiem.setWidth(new Extent(65));
		// note note_bh
		txtGhiChu = new DscField();
		// dot_tv
		txtDotThoiViec = new DscField();
		txtDotThoiViec.setMaximumLength(10);
		// txtDotThoiViec.setWidth(new Extent(65));
		// addcls1_o
		txtTangCaNgayNgoai = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtTangCaNgayNgoai.setMaximumLength(4);
		// txtTangCaNgayNgoai.setWidth(new Extent(30));
		// naddcls_o
		txtTangCaDemNgoai = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtTangCaDemNgoai.setMaximumLength(4);
		// txtTangCaDemNgoai.setWidth(new Extent(30));
		// addhol_o
		txtTangCaChuNhatNgoai = new DscField(
				DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtTangCaChuNhatNgoai.setMaximumLength(4);
		// txtTangCaChuNhatNgoai.setWidth(new Extent(30));
		// addholn_o
		txtTangCaLeNgoai = new DscField(DscField.INPUT_TYPE_POSITIVE_NUMERIC);
		txtTangCaLeNgoai.setMaximumLength(4);
		// txtTangCaLeNgoai.setWidth(new Extent(30));
		// acnm_o
		txtSoDemTienComNgoai = new DscField(DscField.INPUT_TYPE_INTEGER);
		txtSoDemTienComNgoai.setMaximumLength(2);
		// txtSoDemTienComNgoai.setWidth(new Extent(30));
		// depsn_bhyt
		txtDepsnBHYT = new DscField();
		txtDepsnBHYT.setMaximumLength(5);
		// txtDepsnBHYT.setWidth(new Extent(65));
		sfType = new SelectField(new String[] { "Thôi việc", "Bỏ việc" });
		sfType.setWidth(new Extent(80));
		// /////
		rootLayout.add(new Label("Số thẻ"));
		rootLayout.add(txtSoThe);

		rootLayout.add(new Label("Đơn vị"));
		rootLayout.add(txtDonVi);

		rootLayout.add(new Label("Lương cơ bản"));
		rootLayout.add(txtLuongCoBan);

		rootLayout.add(new Label("Lương hợp đồng"));
		rootLayout.add(txtLuongHopDong);

		rootLayout.add(new Label("Tiền thưởng"));
		rootLayout.add(txtTienThuong);

		rootLayout.add(new Label("Phụ cấp chức vụ"));
		rootLayout.add(txtPhuCapChucVu);

		rootLayout.add(new Label("Phụ cấp công việc"));
		rootLayout.add(txtPhuCapCongViec);

		rootLayout.add(new Label("Phụ cấp sinh hoạt"));
		rootLayout.add(txtPhuCapSinhHoat);

		rootLayout.add(new Label("Phụ cấp khác"));
		rootLayout.add(txtPhuCapKhac);

		rootLayout.add(new Label("Bù lương tháng trước"));
		rootLayout.add(txtBuLuongThangTruoc);

		rootLayout.add(new Label("Tạm ứng"));
		rootLayout.add(txtTamUng);

		rootLayout.add(new Label("Khấu trừ khác"));
		rootLayout.add(txtKhauTruKhac);

		rootLayout.add(new Label("Phí công đoàn"));
		rootLayout.add(txtPhiCongDoan);

		rootLayout.add(new Label("Bảo hiểm y tế"));
		rootLayout.add(txtBaoHiemYT);

		rootLayout.add(new Label("Bảo hiểm xã hội"));
		rootLayout.add(txtBaoHiemXH);

		rootLayout.add(new Label("Bảo hiểm thất nghiệp"));
		rootLayout.add(txtBaoHiemTN);

		rootLayout.add(new Label("Bù bảo hiểm 19%"));
		rootLayout.add(txtBuBH19);

		rootLayout.add(new Label("Lương nữa tháng đầu"));
		rootLayout.add(txtLuongNuaThangDau);

		rootLayout.add(new Label("Lương nữa tháng cuối"));
		rootLayout.add(txtLuongNuaThangCuoi);

		rootLayout.add(new Label("Số người phụ thuộc"));
		rootLayout.add(txtSoNguoiPhuThuoc);

		rootLayout.add(new Label("Lương cơ bản bình quân"));
		rootLayout.add(txtLuongCBBinhQuan);

		rootLayout.add(new Label("Phụ cấp chức vụ bình quân"));
		rootLayout.add(txtPhuCapChucVuBinhQuan);

		rootLayout.add(new Label("Số tháng đền bù"));
		rootLayout.add(txtSoThangDenBu);

		rootLayout.add(new Label("Phép năm còn lại"));
		rootLayout.add(txtPhepNamConLai);

		rootLayout.add(new Label("Số tháng trợ cấp"));
		rootLayout.add(txtSoThangTroCap);

		rootLayout.add(new Label("Nghỉ trước thời hạn"));
		rootLayout.add(txtNghiTruocThoiHan);

		rootLayout.add(new Label("Bù bảo hiểm"));
		rootLayout.add(txtBuBaoHiem);

		rootLayout.add(new Label("Thu bảo hiểm"));
		rootLayout.add(txtThuBaoHiem);

		rootLayout.add(new Label("Bù thu bảo hiểm"));
		rootLayout.add(txtBuThuBaoHiem);

		rootLayout.add(new Label("Đợt thôi việc"));
		rootLayout.add(txtDotThoiViec);

		rootLayout.add(new Label("Tăng ca ngày ngoài"));
		rootLayout.add(txtTangCaNgayNgoai);

		rootLayout.add(new Label("Tăng ca đêm ngoài"));
		rootLayout.add(txtTangCaDemNgoai);

		rootLayout.add(new Label("Tăng ca chủ nhật ngoài"));
		rootLayout.add(txtTangCaChuNhatNgoai);

		rootLayout.add(new Label("Tăng ca lễ ngoài"));
		rootLayout.add(txtTangCaLeNgoai);

		rootLayout.add(new Label("Số đêm tiền cơm ngoài"));
		rootLayout.add(txtSoDemTienComNgoai);

		rootLayout.add(new Label("Đơn vị BHYT"));
		rootLayout.add(txtDepsnBHYT);

		rootLayout.add(new Label("Ghi chú"));
		rootLayout.add(txtGhiChu);

		rootLayout.add(new Label("Loại"));
		rootLayout.add(sfType);

		GridLayoutData gridLayoutData = new GridLayoutData();
		gridLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.CENTER));
		for (int i = 0; i < rootLayout.getComponentCount(); i++) {
			if (i % 2 == 0) {
				rootLayout.getComponent(i).setLayoutData(gridLayoutData);
			}
		}
	}
}
