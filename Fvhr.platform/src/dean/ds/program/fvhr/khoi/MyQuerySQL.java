package ds.program.fvhr.khoi;

import com.sun.star.uno.Exception;

import fv.util.Vni2Uni;

public class MyQuerySQL {
	private String sql_defaultSelect;
	private String sql_defaultFrom;
	private String sql_defaultWhere;
	private String id_dept_where;
	private String name_fact_where;
	private String name_group_where;
	private String name_dept_name_where;

	/*
	 * mặc định 3 bảng n_employee, n_department, n_labour mặc định hiện hành
	 */
	
	public MyQuerySQL() {
		sql_defaultSelect = "select e.fname , e.lname, e.empsn, "
				+ "d.name_fact, d.name_group, d.name_dept, d.id_dept, d.name_dept_name ";
		sql_defaultFrom = "from n_employee e, n_department d, n_labour l ";
		sql_defaultWhere = "where e.empsn=l.empsn and e.depsn= d.id_dept "
				+ " and l.clock=1 ";
		// xoa cai l.clock=1 o toString
	}

	public void addWhere(String id_dept, String name_fact, String name_group,
			String name_dept_name, String empsn) {
		if (!id_dept.trim().isEmpty()) {
			sql_defaultWhere += " and d.id_dept='" + id_dept + "' ";
		}
		if (!name_fact.trim().isEmpty()) {
			sql_defaultWhere += " and d.name_fact='" + name_fact + "' ";
		}
		if (!name_group.trim().isEmpty()) {
			sql_defaultWhere += " and d.name_group='" + name_group + "' ";
		}
		if (!name_dept_name.trim().isEmpty()) {
			sql_defaultWhere += " and d.name_dept_name='" + name_dept_name
					+ "' ";
		}
		if (!empsn.trim().isEmpty()) {
			sql_defaultWhere += "and e.empsn='" + empsn + "' ";
		}
	}

	public String getSql_defaultSelect() {
		return sql_defaultSelect;
	}

	public void setSql_defaultSelect(String sql_defaultSelect) throws Exception {
		if (this.sql_defaultSelect.contains(sql_defaultSelect))
			throw new Exception(sql_defaultSelect + " đã được thêm");
		this.sql_defaultSelect += sql_defaultSelect;
	}

	public String getSql_defaultFrom() {
		return sql_defaultFrom;
	}

	public void setSql_defaultFrom(String sql_defaultFrom) {
		this.sql_defaultFrom += sql_defaultFrom;
	}

	public String getSql_defaultWhere() {
		return sql_defaultWhere;
	}

	public void setSql_defaultWhere(String sql_defaultWhere) {
		this.sql_defaultWhere += sql_defaultWhere;
	}

	public String getSQL() {
		return sql_defaultSelect + " " + sql_defaultFrom + " "
				+ sql_defaultWhere + " ";
		// + " and  l.clock=1";

	}
}
