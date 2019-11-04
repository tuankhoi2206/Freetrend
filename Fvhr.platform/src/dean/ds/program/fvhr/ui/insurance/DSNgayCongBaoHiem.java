package ds.program.fvhr.ui.insurance;

import java.util.Date;

public class DSNgayCongBaoHiem {
	private String empsn;
	private String hoTen;
	private String tenDonVi;
	private Integer luongCoBan;
	private Integer luongHopDong;
	private double tsNgayLamNghiCoLuong;
	private double tsNghiSan;
	private double tsNghiBuBaoVe;
	private double tsNghiCoPhep;
	private double tsNghiKhangCong;
	private Date ngayKyHD;
	private Date ngayHetHanHD;
	private String thoiHanHopDong;
	private float tienDongBHXH;
	private float tienDongBHTN;
	private Date ngayNhapXuong;
	private String maDonVi;
	private String chucVu;
	private Integer phuCapChucVu; // Bonus2
	private String congViec;
	private Integer phuCapCongViec; //Bonus3
	private Integer phuCapSinhHoat; // Bonus4
	private Integer phuCapXangDau; //Bonus8
	private Integer phuCapSanLuong; // Bonus1
	private Integer buLuongThangTruoc;// Bonus5
	/*
	private Integer buBaoHiem;// Bonus9 _ 19%
	private Integer luongThuViec;// Temp1, temp2= luong HD
*/	
	private String ghiChuNghiViec;// ngay nghi viec max nhat neu co
	
	
	public void setEmpsn(String empsn) {
		this.empsn = empsn;
	}
	public String getEmpsn() {
		return empsn;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}
	public String getHoTen() {
		return hoTen;
	}
	
	public void setTenDonVi(String tenDonVi) {
		this.tenDonVi = tenDonVi;
	}
	
	public String getTenDonVi() {
		return tenDonVi;
	}
	
	public void setLuongCoBan(Integer luongCoBan) {
		this.luongCoBan = luongCoBan;
	}
	
	public Integer getLuongCoBan() {
		return luongCoBan;
	}
	
	public void setLuongHopDong(Integer luongHopDong) {
		this.luongHopDong = luongHopDong;
	}
	
	public Integer getLuongHopDong() {
		return luongHopDong;
	}
	
	public void setTsNgayLamNghiCoLuong(double tsNgayLamNghiCoLuong) {
		this.tsNgayLamNghiCoLuong = tsNgayLamNghiCoLuong;
	}
	
	public double getTsNgayLamNghiCoLuong() {
		return tsNgayLamNghiCoLuong;
	}
	public void setTsNghiSan(double tsNghiSan) {
		this.tsNghiSan = tsNghiSan;
	}
	public double getTsNghiSan() {
		return tsNghiSan;
	}
	public void setTsNghiBuBaoVe(double tsNghiBuBaoVe) {
		this.tsNghiBuBaoVe = tsNghiBuBaoVe;
	}
	public double getTsNghiBuBaoVe() {
		return tsNghiBuBaoVe;
	}
	public void setTsNghiCoPhep(double tsNghiCoPhep) {
		this.tsNghiCoPhep = tsNghiCoPhep;
	}
	public double getTsNghiCoPhep() {
		return tsNghiCoPhep;
	}
	public void setTsNghiKhangCong(double tsNghiKhangCong) {
		this.tsNghiKhangCong = tsNghiKhangCong;
	}
	public double getTsNghiKhangCong() {
		return tsNghiKhangCong;
	}
	public void setNgayKyHD(Date ngayKyHD) {
		this.ngayKyHD = ngayKyHD;
	}
	public Date getNgayKyHD() {
		return ngayKyHD;
	}
	public void setNgayHetHanHD(Date ngayHetHanHD) {
		this.ngayHetHanHD = ngayHetHanHD;
	}
	public Date getNgayHetHanHD() {
		return ngayHetHanHD;
	}
	public void setThoiHanHopDong(String thoiHanHopDong) {
		this.thoiHanHopDong = thoiHanHopDong;
	}
	public String getThoiHanHopDong() {
		return thoiHanHopDong;
	}
	public void setTienDongBHTN(Float tienDongBHTN) {
		this.tienDongBHTN = tienDongBHTN;
	}
	public float getTienDongBHTN() {
		return tienDongBHTN;
	}
	public void setTienDongBHXH(Float TienDongBHXH) {
		this.tienDongBHXH = TienDongBHXH;
	}
	public float getTienDongBHXH() {
		return tienDongBHXH;
	}
	public void setNgayNhapXuong(Date ngayNhapXuong) {
		this.ngayNhapXuong = ngayNhapXuong;
	}
	public Date getNgayNhapXuong() {
		return ngayNhapXuong;
	}
	public void setMaDonVi(String maDonVi) {
		this.maDonVi = maDonVi;
	}
	public String getMaDonVi() {
		return maDonVi;
	}
	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}
	public String getChucVu() {
		return chucVu;
	}
	public void setPhuCapChucVu(Integer phuCapChucVu) {
		this.phuCapChucVu = phuCapChucVu;
	}
	public Integer getPhuCapChucVu() {
		return phuCapChucVu;
	}
	public void setCongViec(String congViec) {
		this.congViec = congViec;
	}
	public String getCongViec() {
		return congViec;
	}
	public void setPhuCapCongViec(Integer phuCapCongViec) {
		this.phuCapCongViec = phuCapCongViec;
	}
	public Integer getPhuCapCongViec() {
		return phuCapCongViec;
	}
	public void setPhuCapSinhHoat(Integer phuCapSinhHoat) {
		this.phuCapSinhHoat = phuCapSinhHoat;
	}
	public Integer getPhuCapSinhHoat() {
		return phuCapSinhHoat;
	}
	public void setPhuCapXangDau(Integer phuCapXangDau) {
		this.phuCapXangDau = phuCapXangDau;
	}
	public Integer getPhuCapXangDau() {
		return phuCapXangDau;
	}
	public void setPhuCapSanLuong(Integer phuCapSanLuong) {
		this.phuCapSanLuong = phuCapSanLuong;
	}
	public Integer getPhuCapSanLuong() {
		return phuCapSanLuong;
	}
	public void setBuLuongThangTruoc(Integer buLuongThangTruoc) {
		this.buLuongThangTruoc = buLuongThangTruoc;
	}
	public Integer getBuLuongThangTruoc() {
		return buLuongThangTruoc;
	}
	public void setGhiChuNghiViec(String ghiChuNghiViec) {
		this.ghiChuNghiViec = ghiChuNghiViec;
	}
	public String getGhiChuNghiViec() {
		return ghiChuNghiViec;
	}	

}
