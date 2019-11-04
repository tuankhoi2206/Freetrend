package ds.program.fvhr.khoi;

import java.util.Date;

public class RecordHDLD {
	// employee
	private String empsn;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date_s == null) ? 0 : date_s.hashCode());
		result = prime * result + ((empsn == null) ? 0 : empsn.hashCode());
		result = prime * result
				+ ((id_labour == null) ? 0 : id_labour.hashCode());
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
		RecordHDLD other = (RecordHDLD) obj;
		if (date_s == null) {
			if (other.date_s != null)
				return false;
		} else if (!date_s.equals(other.date_s))
			return false;
		if (empsn == null) {
			if (other.empsn != null)
				return false;
		} else if (!empsn.equals(other.empsn))
			return false;
		if (id_labour == null) {
			if (other.id_labour != null)
				return false;
		} else if (!id_labour.equals(other.id_labour))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return empsn;
	}

	private String fname;
	private String lname;

	// n_department
	private String depsn;// tên bộ phận
	private String id_dept;// mã đơn vị
	private String name_dept;// tên bộ phận
	private String name_group;// tên nhóm
	private String name_fact; // tên xưởng
	private String name_dept_name; // tên đơn vị

	// n_labour bảng hợp đồng
	private double salary;// chức vụ
	private String date_s;// ngay ky
	private String id_labour;// id
	private String times;// lan ky
	private String lock;// đã kiểm tra hay chưa
	private String jobs;// chức vụ
	private String poss;
	private String checked;
	private String tableName;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPoss() {
		return poss;
	}

	public void setPoss(String poss) {
		this.poss = poss;
	}

	public RecordHDLD(String empsn, String fname, String lname, String depsn,
			String id_dept, String name_dept, String name_group,
			String name_fact, String name_dept_name, String jobs, String poss,
			double salary, String date_s, String id_labour, String times,
			String lock, String checked) {
		super();
		this.empsn = empsn;
		this.fname = fname;
		this.lname = lname;
		this.depsn = depsn;
		this.id_dept = id_dept;
		this.name_dept = name_dept;
		this.name_group = name_group;
		this.name_fact = name_fact;
		this.name_dept_name = name_dept_name;
		this.jobs = jobs;
		this.salary = salary;
		this.date_s = date_s;
		this.id_labour = id_labour;
		this.times = times;
		this.lock = lock;
		this.poss = poss;
		this.checked = checked;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	// contructor
	public RecordHDLD() {

	}

	public RecordHDLD(String empsn, String id_labour) {
		this.empsn = empsn;
		this.id_labour = id_labour;
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

	public String getDepsn() {
		return depsn;
	}

	public void setDepsn(String depsn) {
		this.depsn = depsn;
	}

	public String getId_dept() {
		return id_dept;
	}

	public void setId_dept(String id_dept) {
		this.id_dept = id_dept;
	}

	public String getName_dept() {
		return name_dept;
	}

	public void setName_dept(String name_dept) {
		this.name_dept = name_dept;
	}

	public String getName_group() {
		return name_group;
	}

	public void setName_group(String name_group) {
		this.name_group = name_group;
	}

	public String getName_fact() {
		return name_fact;
	}

	public void setName_fact(String name_fact) {
		this.name_fact = name_fact;
	}

	public String getName_dept_name() {
		return name_dept_name;
	}

	public void setName_dept_name(String name_dept_name) {
		this.name_dept_name = name_dept_name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getDate_s() {
		return date_s;
	}

	public void setData_s(String date_s) {
		this.date_s = date_s;
	}

	public String getId_labour() {
		return id_labour;
	}

	public void setId_labour(String id_labour) {
		this.id_labour = id_labour;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String isLock() {
		return lock;
	}

	public void setLock(String lock) {
		this.lock = lock;
	}

	public String getLock() {
		return lock;
	}

	public String getJobs() {
		return jobs;
	}

	public void setJobs(String jobs) {
		this.jobs = jobs;
	}

}
