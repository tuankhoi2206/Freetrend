package ds.program.fvhr.ui.quitworksalary.task;

import java.math.BigDecimal;
import java.util.Date;

public class AttQuitExcelData {

	private static AttQuitExcelData INSTANSE;

	private String soThe;

	// dep_kt
	private String donVi;

	// bsaly
	private BigDecimal luongCoBan;

	// combsaly
	private BigDecimal luongHopDong;

	// bonus1
	private BigDecimal tienThuong;

	// bonus2
	private BigDecimal phuCapChucVu;

	// bonus3
	private BigDecimal phuCapCongViec;

	// bonus4
	private BigDecimal phuCapSinhHoat;

	// bonus8
	private BigDecimal phuCapKhac;

	// bonus5
	private BigDecimal buLuongThangTruoc;

	// borm
	private BigDecimal tamUng;

	// kqt
	private BigDecimal khauTruKhac;

	// joinlum
	private BigDecimal phiCongDoan;

	// ylbx
	private BigDecimal baoHiemYT;

	// joininsu
	private BigDecimal baoHiemXH;

	// bh_tnghiep
	private BigDecimal baoHiemTN;

	// bonus9
	private BigDecimal buBH19;

	// temp1
	private BigDecimal luongNuaThangDau;

	// temp2
	private BigDecimal luongNuaThangCuoi;

	// bac
	private BigDecimal soNguoiPhuThuoc;

	// bsaly_avg
	private BigDecimal luongCBBinhQuan;

	// bonus2_avg
	private BigDecimal phuCapChucVuBinhQuan;

	// m_denbu
	private BigDecimal soThangDenBu;

	// pn_conlai
	private BigDecimal phepNamConLai;

	// m_trocap
	private BigDecimal soThangTroCap;

	// d_nghitruoc
	private BigDecimal nghiTruocThoiHan;

	// Bu_bh
	private BigDecimal buBaoHiem;

	// thu_bh
	private BigDecimal thuBaoHiem;

	// buthu_bh
	private BigDecimal buThuBaoHiem;

	// note note_bh
	private String ghiChu;

	// dot_tv
	private Date dotThoiViec;

	// addcls1_o
	private BigDecimal tangCaNgayNgoai;

	// naddcls_o
	private BigDecimal tangCaDemNgoai;

	// addhol_o
	private BigDecimal tangCaChuNhatNgoai;

	// addholn_o
	private BigDecimal tangCaLeNgoai;

	// acnm_o
	private BigDecimal soDemTienComNgoai;

	// depsn_bhyt
	private String depsnBHYT;

	private AttQuitExcelData() {
	}

	public static AttQuitExcelData getInstanse() {
		if (INSTANSE == null)
			INSTANSE = new AttQuitExcelData();
		return INSTANSE;
	}

	public String getSoThe() {
		return soThe;
	}

	public void setSoThe(String soThe) {
		this.soThe = soThe;
	}

	public String getDonVi() {
		return donVi;
	}

	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}

	public BigDecimal getLuongCoBan() {
		return luongCoBan;
	}

	public void setLuongCoBan(BigDecimal luongCoBan) {
		this.luongCoBan = luongCoBan;
	}

	public BigDecimal getLuongHopDong() {
		return luongHopDong;
	}

	public void setLuongHopDong(BigDecimal luongHopDong) {
		this.luongHopDong = luongHopDong;
	}

	public BigDecimal getTienThuong() {
		return tienThuong;
	}

	public void setTienThuong(BigDecimal tienThuong) {
		this.tienThuong = tienThuong;
	}

	public BigDecimal getPhuCapChucVu() {
		return phuCapChucVu;
	}

	public void setPhuCapChucVu(BigDecimal phuCapChucVu) {
		this.phuCapChucVu = phuCapChucVu;
	}

	public BigDecimal getPhuCapCongViec() {
		return phuCapCongViec;
	}

	public void setPhuCapCongViec(BigDecimal phuCapCongViec) {
		this.phuCapCongViec = phuCapCongViec;
	}

	public BigDecimal getPhuCapSinhHoat() {
		return phuCapSinhHoat;
	}

	public void setPhuCapSinhHoat(BigDecimal phuCapSinhHoat) {
		this.phuCapSinhHoat = phuCapSinhHoat;
	}

	public BigDecimal getPhuCapKhac() {
		return phuCapKhac;
	}

	public void setPhuCapKhac(BigDecimal phuCapKhac) {
		this.phuCapKhac = phuCapKhac;
	}

	public BigDecimal getBuLuongThangTruoc() {
		return buLuongThangTruoc;
	}

	public void setBuLuongThangTruoc(BigDecimal buLuongThangTruoc) {
		this.buLuongThangTruoc = buLuongThangTruoc;
	}

	public BigDecimal getTamUng() {
		return tamUng;
	}

	public void setTamUng(BigDecimal tamUng) {
		this.tamUng = tamUng;
	}

	public BigDecimal getKhauTruKhac() {
		return khauTruKhac;
	}

	public void setKhauTruKhac(BigDecimal khauTruKhac) {
		this.khauTruKhac = khauTruKhac;
	}

	public BigDecimal getPhiCongDoan() {
		return phiCongDoan;
	}

	public void setPhiCongDoan(BigDecimal phiCongDoan) {
		this.phiCongDoan = phiCongDoan;
	}

	public BigDecimal getBaoHiemYT() {
		return baoHiemYT;
	}

	public void setBaoHiemYT(BigDecimal baoHiemYT) {
		this.baoHiemYT = baoHiemYT;
	}

	public BigDecimal getBaoHiemXH() {
		return baoHiemXH;
	}

	public void setBaoHiemXH(BigDecimal baoHiemXH) {
		this.baoHiemXH = baoHiemXH;
	}

	public BigDecimal getBaoHiemTN() {
		return baoHiemTN;
	}

	public void setBaoHiemTN(BigDecimal baoHiemTN) {
		this.baoHiemTN = baoHiemTN;
	}

	public BigDecimal getBuBH19() {
		return buBH19;
	}

	public void setBuBH19(BigDecimal buBH19) {
		this.buBH19 = buBH19;
	}

	public BigDecimal getLuongNuaThangDau() {
		return luongNuaThangDau;
	}

	public void setLuongNuaThangDau(BigDecimal luongNuaThangDau) {
		this.luongNuaThangDau = luongNuaThangDau;
	}

	public BigDecimal getLuongNuaThangCuoi() {
		return luongNuaThangCuoi;
	}

	public void setLuongNuaThangCuoi(BigDecimal luongNuaThangCuoi) {
		this.luongNuaThangCuoi = luongNuaThangCuoi;
	}

	public BigDecimal getSoNguoiPhuThuoc() {
		return soNguoiPhuThuoc;
	}

	public void setSoNguoiPhuThuoc(BigDecimal soNguoiPhuThuoc) {
		this.soNguoiPhuThuoc = soNguoiPhuThuoc;
	}

	public BigDecimal getLuongCBBinhQuan() {
		return luongCBBinhQuan;
	}

	public void setLuongCBBinhQuan(BigDecimal luongCBBinhQuan) {
		this.luongCBBinhQuan = luongCBBinhQuan;
	}

	public BigDecimal getPhuCapChucVuBinhQuan() {
		return phuCapChucVuBinhQuan;
	}

	public void setPhuCapChucVuBinhQuan(BigDecimal phuCapChucVuBinhQuan) {
		this.phuCapChucVuBinhQuan = phuCapChucVuBinhQuan;
	}

	public BigDecimal getSoThangDenBu() {
		return soThangDenBu;
	}

	public void setSoThangDenBu(BigDecimal soThangDenBu) {
		this.soThangDenBu = soThangDenBu;
	}

	public BigDecimal getPhepNamConLai() {
		return phepNamConLai;
	}

	public void setPhepNamConLai(BigDecimal phepNamConLai) {
		this.phepNamConLai = phepNamConLai;
	}

	public BigDecimal getSoThangTroCap() {
		return soThangTroCap;
	}

	public void setSoThangTroCap(BigDecimal soThangTroCap) {
		this.soThangTroCap = soThangTroCap;
	}

	public BigDecimal getNghiTruocThoiHan() {
		return nghiTruocThoiHan;
	}

	public void setNghiTruocThoiHan(BigDecimal nghiTruocThoiHan) {
		this.nghiTruocThoiHan = nghiTruocThoiHan;
	}

	public BigDecimal getBuBaoHiem() {
		return buBaoHiem;
	}

	public void setBuBaoHiem(BigDecimal buBaoHiem) {
		this.buBaoHiem = buBaoHiem;
	}

	public BigDecimal getThuBaoHiem() {
		return thuBaoHiem;
	}

	public void setThuBaoHiem(BigDecimal thuBaoHiem) {
		this.thuBaoHiem = thuBaoHiem;
	}

	public BigDecimal getBuThuBaoHiem() {
		return buThuBaoHiem;
	}

	public void setBuThuBaoHiem(BigDecimal buThuBaoHiem) {
		this.buThuBaoHiem = buThuBaoHiem;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public Date getDotThoiViec() {
		return dotThoiViec;
	}

	public void setDotThoiViec(Date dotThoiViec) {
		this.dotThoiViec = dotThoiViec;
	}

	public BigDecimal getTangCaNgayNgoai() {
		return tangCaNgayNgoai;
	}

	public void setTangCaNgayNgoai(BigDecimal tangCaNgayNgoai) {
		this.tangCaNgayNgoai = tangCaNgayNgoai;
	}

	public BigDecimal getTangCaDemNgoai() {
		return tangCaDemNgoai;
	}

	public void setTangCaDemNgoai(BigDecimal tangCaDemNgoai) {
		this.tangCaDemNgoai = tangCaDemNgoai;
	}

	public BigDecimal getTangCaChuNhatNgoai() {
		return tangCaChuNhatNgoai;
	}

	public void setTangCaChuNhatNgoai(BigDecimal tangCaChuNhatNgoai) {
		this.tangCaChuNhatNgoai = tangCaChuNhatNgoai;
	}

	public BigDecimal getTangCaLeNgoai() {
		return tangCaLeNgoai;
	}

	public void setTangCaLeNgoai(BigDecimal tangCaLeNgoai) {
		this.tangCaLeNgoai = tangCaLeNgoai;
	}

	public BigDecimal getSoDemTienComNgoai() {
		return soDemTienComNgoai;
	}

	public void setSoDemTienComNgoai(BigDecimal soDemTienComNgoai) {
		this.soDemTienComNgoai = soDemTienComNgoai;
	}

	public String getDepsnBHYT() {
		return depsnBHYT;
	}

	public void setDepsnBHYT(String depsnBHYT) {
		this.depsnBHYT = depsnBHYT;
	}

}
