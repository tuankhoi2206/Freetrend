package ds.program.fvhr.vinh.report_object;

import java.util.Date;

public class EmployeePlain {
	private String empsn;
	private String fname;
	private String lname;
	private double positionBonus;
	private String deptID;
	private String deptName;
	private double basicSalary;
	private double comSalary;
	private String labourKind;
	private Date signDate;
	private double ducls;
	private double restPay;
	private double maternityLeave;
	private double paidVacation;
	private double absentWithoutLeave;
	private double dayOFF;
	private double unpaidVacation;
	private double socialInsurance;
	private double uempmInsurance;
	private Date quitDay;

	public EmployeePlain() {
		super();
	}

	public EmployeePlain(String empsn, String fname, String lname,
			double positionBonus, String deptID, String deptName,
			double basicSalary, double comSalary, Date signDate, String labourKind,
			 double ducls, double restPay, double maternityLeave, double paidVacation,
			double absentWithoutLeave, double dayOFF, double unpaidVacation, double socialInsurance,
			double uempmInsurance, Date quitDay) {
		this.empsn = empsn;
		this.fname = fname;
		this.lname = lname;
		this.positionBonus = positionBonus;
		this.deptID = deptID;
		this.deptName = deptName;
		this.basicSalary = basicSalary;
		this.comSalary = comSalary;
		this.labourKind = labourKind;
		this.signDate = signDate;
		this.ducls = ducls;
		this.restPay = restPay;
		this.maternityLeave = maternityLeave;
		this.paidVacation = paidVacation;
		this.absentWithoutLeave = absentWithoutLeave;
		this.dayOFF = dayOFF;
		this.unpaidVacation = unpaidVacation;
		this.socialInsurance = socialInsurance;
		this.uempmInsurance = uempmInsurance;
		this.quitDay = quitDay;
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



	public double getUnpaidVacation() {
		return unpaidVacation;
	}



	public void setUnpaidVacation(double unpaidVacation) {
		this.unpaidVacation = unpaidVacation;
	}



	public double getSocialInsurance() {
		return socialInsurance;
	}



	public void setSocialInsurance(double socialInsurance) {
		this.socialInsurance = socialInsurance;
	}



	public double getUempmInsurance() {
		return uempmInsurance;
	}



	public void setUempmInsurance(double uempmInsurance) {
		this.uempmInsurance = uempmInsurance;
	}



	public Date getQuitDay() {
		return quitDay;
	}



	public void setQuitDay(Date quitDay) {
		this.quitDay = quitDay;
	}

}
