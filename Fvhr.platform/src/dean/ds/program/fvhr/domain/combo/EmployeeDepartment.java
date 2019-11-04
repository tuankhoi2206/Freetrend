package ds.program.fvhr.domain.combo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
@Table(name = "N_DEPARTMENT")
@SecondaryTable(name = "N_EMPLOYEE", pkJoinColumns = @PrimaryKeyJoinColumn(name = "DEPSN"))
public class EmployeeDepartment {
	// a.fname, a.lname, a.depsn, a.empsn, a.empcn, a.birthday,
	// a.permanent_address, a.sex, b.name_dept
	private java.lang.String ID_DEPT;

	private java.lang.String NAME_DEPT;

	private java.lang.String NAME_GROUP;

	private java.lang.String NAME_FACT;

	private java.lang.String EMPSN;

	private java.lang.String EMPCN;

	private java.lang.String FNAME;

	private java.lang.String LNAME;

	private java.lang.String BIRTHDAY;

	private java.lang.String PERMANENT_ADDRESS;

	private java.lang.String SEX;

	public EmployeeDepartment() {
	}

	public EmployeeDepartment(String EMPSN, String EMPCN, String FNAME,
			String LNAME, String BIRTHDAY, String PERMANENT_ADDRESS,
			String SEX, String ID_DEPT, String NAME_DEPT, String NAME_GROUP,
			String NAME_FACT) {
		super();
		this.EMPSN = EMPSN;
		this.EMPCN = EMPCN;
		this.FNAME = FNAME;
		this.LNAME = LNAME;
		this.BIRTHDAY = BIRTHDAY;
		this.PERMANENT_ADDRESS = PERMANENT_ADDRESS;
		this.SEX = SEX;
		this.ID_DEPT = ID_DEPT;
		this.NAME_DEPT = NAME_DEPT;
		this.NAME_GROUP = NAME_GROUP;
		this.NAME_FACT = NAME_FACT;
	}

	@Column(name = "BIRTHDAY", table = "N_EMPLOYEE")
	public java.lang.String getBIRTHDAY() {
		return BIRTHDAY;
	}

	public void setBIRTHDAY(java.lang.String birthday) {
		BIRTHDAY = birthday;
	}

	@Column(name = "PERMANENT_ADDRESS", table = "N_EMPLOYEE")
	public java.lang.String getPERMANENT_ADDRESS() {
		return PERMANENT_ADDRESS;
	}

	public void setPERMANENT_ADDRESS(java.lang.String permanent_address) {
		PERMANENT_ADDRESS = permanent_address;
	}

	@Column(name = "SEX", table = "N_EMPLOYEE")
	public java.lang.String getSEX() {
		return SEX;
	}

	public void setSEX(java.lang.String sex) {
		SEX = sex;
	}

	@Column(name = "EMPCN", table = "N_EMPLOYEE")
	public java.lang.String getEMPCN() {
		return EMPCN;
	}

	public void setEMPCN(java.lang.String empcn) {
		EMPCN = empcn;
	}

	@Column(name = "EMPSN", table = "N_EMPLOYEE")
	public java.lang.String getEMPSN() {
		return EMPSN;
	}

	public void setEMPSN(java.lang.String empsn) {
		EMPSN = empsn;
	}

	@Column(name = "FNAME", table = "N_EMPLOYEE")
	public java.lang.String getFNAME() {
		return FNAME;
	}

	public void setFNAME(java.lang.String fname) {
		FNAME = fname;
	}

	@Id
	public java.lang.String getID_DEPT() {
		return ID_DEPT;
	}

	public void setID_DEPT(java.lang.String id_dept) {
		ID_DEPT = id_dept;
	}

	@Column(name = "LNAME", table = "N_EMPLOYEE")
	public java.lang.String getLNAME() {
		return LNAME;
	}

	public void setLNAME(java.lang.String lname) {
		LNAME = lname;
	}

	@Column(name = "NAME_DEPT")
	public java.lang.String getNAME_DEPT() {
		return NAME_DEPT;
	}

	public void setNAME_DEPT(java.lang.String name_dept) {
		NAME_DEPT = name_dept;
	}

	public java.lang.String getNAME_FACT() {
		return NAME_FACT;
	}

	public void setNAME_FACT(java.lang.String name_fact) {
		NAME_FACT = name_fact;
	}

	public java.lang.String getNAME_GROUP() {
		return NAME_GROUP;
	}

	public void setNAME_GROUP(java.lang.String name_group) {
		NAME_GROUP = name_group;
	}

}
