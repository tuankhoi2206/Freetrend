package ds.program.fvhr.ui.insurance;

import java.util.Calendar;

import fv.util.MonthYearType;
/**
 * Insurance Data wrapper
 * @author Hieu
 *
 */
public class InsuranceLogicData {
	private String empsn;
	private MonthYearType thang;//month required to get data
	private float tongNgayCong;
	//private float phanNuaCong;
	private float tongSoNgayCuaThang;
	private float tongRo;
	private float tongNghiSan;
	private float luong;
	
	public Calendar getNgay(int day){
		return thang.getDate(day);
	}

	public String getEmpsn() {
		return empsn;
	}

	public void setEmpsn(String empsn) {
		this.empsn = empsn;
	}

	public MonthYearType getThang() {
		return thang;
	}

	public void setThang(MonthYearType thang) {
		this.thang = thang;
	}

	public float getTongNgayCong() {
		return tongNgayCong;
	}

	public void setTongNgayCong(float tongNgayCong) {
		this.tongNgayCong = tongNgayCong;
	}

	public float getTongRo() {
		return tongRo;
	}

	public void setTongRo(float tongRo) {
		this.tongRo = tongRo;
	}

	public float getTongNghiSan() {
		return tongNghiSan;
	}

	public void setTongNghiSan(float tongNghiSan) {
		this.tongNghiSan = tongNghiSan;
	}

	public float getLuong() {
		return luong;
	}

	public void setLuong(float luong) {
		this.luong = luong;
	}

/*	public void setPhanNuaCong(float phanNuaCong) {
		this.phanNuaCong = phanNuaCong;
	}

	public float getPhanNuaCong() {
		return phanNuaCong;
	}*/
	
	public void setTongSoNgayCuaThang(float tongSoNgayCuaThang) {
		this.tongSoNgayCuaThang = tongSoNgayCuaThang;
	}

	public float getTongSoNgayCuaThang() {
		return tongSoNgayCuaThang;
	}	
}
