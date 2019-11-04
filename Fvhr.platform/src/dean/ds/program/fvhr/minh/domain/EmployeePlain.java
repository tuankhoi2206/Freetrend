package ds.program.fvhr.minh.domain;

import java.util.Date;

public class EmployeePlain {
	private String empsn;//So the
	private String fname;// Ho
	private String lname;//Ten
	private double restPay;//Ts nghi co luong
	private double positionBonus;//Phu cap
	private String deptID;//Ma Dvi
	private String deptName;//Ten Dvi
	private double basicSalary;//Luong cb
	private double comSalary;//Luong hd
	private String labourKind;//Thoi han hd
	private Date signDate;//ngay ky hop dong gan nhat
	private double ducls;//Ts ngay cong
	private double maternityLeave;//Nghi san
	private double paidVacation;//Nghi co phep ( nghi ko luong co xin phep)
	private double absentWithoutLeave;//Khang cong
	private double dayOFF;//Ngay ko di lam
	private Date quitDay;//Ngay thuc nghi
	private String quitReason;//Ma Dvi
	private int status;// 1: mua, 0: ko mua vi ko du cong, -1/ cac so khac: ko mua vi ly do khac

	public EmployeePlain() {
	}

	public EmployeePlain(String empsn, String fname, String lname,
			double positionBonus, String deptID, String deptName,
			double basicSalary, double comSalary,
			 double ducls, double restPay, double maternityLeave, double paidVacation,
			double absentWithoutLeave, double dayOFF) {
		this.empsn = empsn;
		this.fname = fname;
		this.lname = lname;
		this.positionBonus = positionBonus;
		this.deptID = deptID;
		this.deptName = deptName;
		this.basicSalary = basicSalary;
		this.comSalary = comSalary;
		this.ducls = ducls;
		this.restPay = restPay;
		this.maternityLeave = maternityLeave;
		this.paidVacation = paidVacation;
		this.absentWithoutLeave = absentWithoutLeave;
		this.dayOFF = dayOFF;
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



	public double getPositionBonus() {
		return positionBonus;
	}



	public void setPositionBonus(double positionBonus) {
		this.positionBonus = positionBonus;
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



	public String getLabourKind() {
		return labourKind;
	}



	public void setLabourKind(String labourKind) {
		this.labourKind = labourKind;
	}



	public Date getSignDate() {
		return signDate;
	}



	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}



	public double getDucls() {
		return ducls;
	}



	public void setDucls(double ducls) {
		this.ducls = ducls;
	}



	public double getRestPay() {
		return restPay;
	}



	public void setRestPay(double restPay) {
		this.restPay = restPay;
	}



	public double getMaternityLeave() {
		return maternityLeave;
	}



	public void setMaternityLeave(double maternityLeave) {
		this.maternityLeave = maternityLeave;
	}



	public double getPaidVacation() {
		return paidVacation;
	}



	public void setNCP(double ncp) {
		paidVacation = ncp;
	}



	public double getAbsentWithoutLeave() {
		return absentWithoutLeave;
	}



	public void setAbsentWithoutLeave(double absentWithoutLeave) {
		this.absentWithoutLeave = absentWithoutLeave;
	}



	public double getDayOFF() {
		return dayOFF;
	}



	public void setDayOFF(double dayOFF) {
		this.dayOFF = dayOFF;
	}

	public Date getQuitDay() {
		return quitDay;
	}



	public void setQuitDay(Date quitDay) {
		this.quitDay = quitDay;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setQuitReason(String quitReason) {
		this.quitReason = quitReason;
	}

	public String getQuitReason() {
		return quitReason;
	}

}
