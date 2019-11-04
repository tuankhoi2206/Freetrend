package ds.program.fvhr.minh.domain;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class EmployeeDucls {
	
	private String empsn;//So the
	private String fname;// Ho
	private String lname;//Ten
	private String deptID;//Ma Dvi
	private String deptName;//Ten Dvi
	private double ducls;//Ts ngay cong
	private double nghisan;//Nghi san
	private double nghicophep;//Nghi co phep 
	private double khangcong;//Khang cong
	private double nghiDS;//Nghi duong suc
	private double nghiBU;//Nghi Bu
	private double positionBonus;//Phu cap
	private double basicSalary;//Luong cb
	private double comSalary;//Luong hd
	
	public EmployeeDucls(String empsn, String fname, String lname,
			String deptID, String deptName, double ducls, double nghisan,
			double nghicophep, double khangcong, double nghiDS, double nghiBU,
			double positionBonus, double basicSalary, double comSalary) {
		super();
		this.empsn = empsn;
		this.fname = fname;
		this.lname = lname;
		this.deptID = deptID;
		this.deptName = deptName;
		this.ducls = ducls;
		this.nghisan = nghisan;
		this.nghicophep = nghicophep;
		this.khangcong = khangcong;
		this.nghiDS = nghiDS;
		this.nghiBU = nghiBU;
		this.positionBonus = positionBonus;
		this.basicSalary = basicSalary;
		this.comSalary = comSalary;
	}
	public EmployeeDucls() {
		// TODO Auto-generated constructor stub
	}
	public double getPositionBonus() {
		return positionBonus;
	}
	public void setPositionBonus(double positionBonus) {
		this.positionBonus = positionBonus;
	}
	public double getBasicSalary() {
		return basicSalary;
	}
	public void setBasicSalary(double basicSalary) {
		this.basicSalary = basicSalary;
	}
	public double getComSalary() {
		return comSalary;
	}
	public void setComSalary(double comSalary) {
		this.comSalary = comSalary;
	}
	
	public String getEmpsn() {
		return empsn;
	}
	public void setEmpsn(String empsn) {
		this.empsn = empsn;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getDeptID() {
		return deptID;
	}
	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public double getDucls() {
		return ducls;
	}
	public void setDucls(double ducls) {
		this.ducls = ducls;
	}
	public double getNghisan() {
		return nghisan;
	}
	public void setNghisan(double nghisan) {
		this.nghisan = nghisan;
	}
	public double getNghicophep() {
		return nghicophep;
	}
	public void setNghicophep(double nghicophep) {
		this.nghicophep = nghicophep;
	}
	public double getKhangcong() {
		return khangcong;
	}
	public void setKhangcong(double khangcong) {
		this.khangcong = khangcong;
	}
	public double getNghiDS() {
		return nghiDS;
	}
	public void setNghiDS(double nghiDS) {
		this.nghiDS = nghiDS;
	}
	public double getNghiBU() {
		return nghiBU;
	}
	public void setNghiBU(double nghiBU) {
		this.nghiBU = nghiBU;
	}


}
