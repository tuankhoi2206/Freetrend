package ds.program.fvhr.khoi.domain;

public class Employee {
	private String empsn;
	private String fname;
	private String lname;
	private int status;
	private String idDept;
	private String nameDept;
	private float salary;
	private float money;
	private String note;

	public Employee() {
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getIdDept() {
		return idDept;
	}

	public void setIdDept(String idDept) {
		this.idDept = idDept;
	}

	public String getNameDept() {
		return nameDept;
	}

	public void setNameDept(String nameDept) {
		this.nameDept = nameDept;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empsn == null) ? 0 : empsn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (empsn == null) {
			if (other.empsn != null)
				return false;
		} else if (!empsn.equals(other.empsn))
			return false;
		return true;
	}

}
