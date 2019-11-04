package ds.program.fvhr.ui.insurance;

import java.math.BigDecimal;

public class Tang1TayObject {
	private String hoTen;
	private String ngaySinh;
	private String maSoBHXH;
	private BigDecimal luong;
	private String thangTangMoi;//dd/mm/yyyy
	private String ghiChu;
	private String empsn;
	private String donVi;
	private String factGroup;
	// cho thong tin cap so
	private String gioiTinh; // NAM or NU
	private String danToc;
	private String cMND;  
	private String ngayCap; // tuy theo CMND cua nguoi lao dong nen ko chac co du thong so dd/mm/yyyy
	private String maTinhCap; // CNV co / ko co CMND nen ko co noi cap, data old ko day du
	private String hoKhau;
	//Bo sung thong tin BHYT:  idPro: Ma tinh thanh noi DKKCB truc thuoc, idHos : Ma noi DKKCB
	private String idPro;
	private String idHos;
	
	
	public String getcMND() {
		return cMND;
	}
	public void setcMND(String cMND) {
		this.cMND = cMND;
	}
	public String getMaTinhCap() {
		return maTinhCap;
	}
	public void setMaTinhCap(String maTinhCap) {
		this.maTinhCap = maTinhCap;
	}
	public String getIdPro() {
		return idPro;
	}
	public void setIdPro(String idPro) {
		this.idPro = idPro;
	}
	public String getIdHos() {
		return idHos;
	}
	public void setIdHos(String idHos) {
		this.idHos = idHos;
	}
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}
	public String getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(String ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getMaSoBHXH() {
		return maSoBHXH;
	}
	public void setMaSoBHXH(String maSoBHXH) {
		this.maSoBHXH = maSoBHXH;
	}
	public BigDecimal getLuong() {
		return luong;
	}
	public void setLuong(BigDecimal luong) {
		this.luong = luong;
	}
	public String getThangTangMoi() {
		return thangTangMoi;
	}
	public void setThangTangMoi(String thangTangMoi) {
		this.thangTangMoi = thangTangMoi;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	public String getEmpsn() {
		return empsn;
	}
	public void setEmpsn(String empsn) {
		this.empsn = empsn;
	}
	public String getDonVi() {
		return donVi;
	}
	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}
	public void setFactGroup(String factGroup) {
		this.factGroup = factGroup;
	}
	public String getFactGroup() {
		return factGroup;
	}
	
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getGioiTinh() {
		return gioiTinh;
	}	
	
	public void setDanToc(String danToc) {
		this.danToc = danToc;
	}
	public String getDanToc() {
		return danToc;
	}	
	
	public void setCMND(String cmnd) {
		this.cMND = cmnd;
	}
	public String getCMND() {
		return cMND;
	}
	public void setNgayCap(String ngayCap) {
		this.ngayCap = ngayCap;
	}
	public String getNgayCap() {
		return ngayCap;
	}	
	public void setNoiCap(String noiCap) {
		this.maTinhCap = noiCap;
	}
	public String getNoiCap() {
		return maTinhCap;
	}
	public void setHoKhau(String hoKhau) {
		this.hoKhau = hoKhau;
	}
	public String getHoKhau() {
		return hoKhau;
	}	
}
