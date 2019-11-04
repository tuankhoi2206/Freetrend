package ds.program.fvhr.khoi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import ds.program.fvhr.khoi.domain.Factory;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import ds.program.fvhr.khoi.domain.EmployeeExport;

import fv.util.JdbcDAO;

public class ExportHealthInsuranceDAO extends JdbcDAO {

	public ExportHealthInsuranceDAO() {
		super();
	}

	public Factory getFactory(String condition, String monthYear, int lanChay) {
		String sql = "select count(e.empsn)as songuoi, nvl(sum(s.bsalary),0) as tongquyluong "
				+ "from n_detail_sub_insurance s, n_employee e, n_department d"
				+ " where s.empsn=e.empsn and e.depsn= d.id_dept "
				+ " and s.purchase_status=1 and s.insurance_money>0 and s.purchase_date='"
				+ monthYear + "'" + condition;
		System.out.println(" sql getFactory :" + sql);
		List<Factory> lst = getSimpleJdbcTemplate().query(sql,
				new ParameterizedRowMapper<Factory>() {
					@Override
					public Factory mapRow(ResultSet rs, int arg1)
							throws SQLException {
						Factory f = new Factory();
						f.setSumEmpsn(rs.getInt("SONGUOI"));
						f.setSumSalary(rs.getLong("TONGQUYLUONG"));
						return f;
					}
				}, new Object[] {});
		return lst.get(0);
	}

	public List<EmployeeExport> getAllEmplSubInsurance(String nameFact,
			String monthYear, int lanChay) throws Exception {

		String sql = "select s.empsn, e.fname, e.lname, s.MONTH_STATUS"
				+ ", d.id_dept, d.name_dept, s.insurance_money, s.TOTAL_SALARY, s.note "
				+ " from N_DETAIL_SUB_INSURANCE s, N_EMPLOYEE e, N_DEPARTMENT d "
				+ " where s.empsn=e.empsn and e.depsn= d.id_dept "
				+ "and  s.purchase_status=1 and s.insurance_money>0"
				+ "and s.purchase_date='" + monthYear + "'" + " and s.lanchay="
				+ lanChay;
		if (nameFact != null && nameFact.trim().length() > 0) {
			sql += "and d.name_fact='" + nameFact + "'";
		}

		System.out.println("getAllEmplSubInsurance sql : " + sql);
		List<EmployeeExport> lstEmpl;
		try {
			lstEmpl = getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<EmployeeExport>() {
						@Override
						public EmployeeExport mapRow(ResultSet rs, int rowNum)
								throws SQLException {

							EmployeeExport empl = new EmployeeExport();
							empl.setEmpsn(rs.getString("EMPSN"));
							empl.setFname(rs.getString("FNAME"));
							empl.setLname(rs.getString("LNAME"));
							empl.setMonthStatus(rs.getString("MONTH_STATUS"));
							empl.setIdDept(rs.getString("ID_DEPT"));
							empl.setNameDept(rs.getString("NAME_DEPT"));
							empl.setInsuranceMoney(rs.getInt("INSURANCE_MONEY"));
							empl.setTotalSalary(rs.getInt("TOTAL_SALARY"));
							empl.setNote(rs.getString("NOTE"));

							return empl;
						}
					}, new Object[] {});

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return lstEmpl;
	}
}
