package ds.program.fvhr.khoi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import ds.program.fvhr.khoi.domain.Employee;
import fv.util.JdbcDAO;

public class ReportHealthDAO extends JdbcDAO {

	private static final Logger logger = Logger
			.getLogger(ReportHealthDAO.class);

	public boolean checkTableHealthExits(String tablename) {
		return false;
	}

	public ReportHealthDAO() {
	}

	public List<Employee> getListEmployeeHealth(String tableNameExport,
			String namefact) {
		String sql = "select * from \n";
		sql += tableNameExport;
		sql += "t, N_EMPLOYEE e, N_DEPARTMENT n\n";
		sql += "\nwhere e.empsn=t.empsn and t.despn=d.id_dept  and t.depsn <>'00000'\n";
		if (namefact != null && !namefact.isEmpty()) {
			sql += namefact;
		}
		List<Employee> list = getSimpleJdbcTemplate().query(sql,
				new ParameterizedRowMapper<Employee>() {
					public Employee mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Employee emp = new Employee();
						emp.setEmpsn(rs.getString("EMPSN"));
						emp.setFname(rs.getString("FNAME"));
						emp.setLname(rs.getString("LNAME"));
						emp.setIdDept(rs.getString("ID_DEPT"));
						emp.setNameDept(rs.getString("NAME_DEPT"));
						emp.setSalary(rs.getFloat("SALARY_M"));
						emp.setMoney(rs.getFloat("MONEY"));
						emp.setNote(rs.getString("NOTE"));
						return emp;
					}
				});
		return list;
	}
	
}
