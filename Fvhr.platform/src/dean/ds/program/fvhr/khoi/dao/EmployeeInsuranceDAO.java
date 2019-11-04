package ds.program.fvhr.khoi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import ds.program.fvhr.domain.N_DETAIL_SUB_INSURANCE;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_EMP_QUIT;
import ds.program.fvhr.khoi.domain.EmployeeBear;

import fv.util.JdbcDAO;

public class EmployeeInsuranceDAO extends JdbcDAO {

	private SimpleDateFormat spleDefaultFormat = new SimpleDateFormat(
			"dd/MM/yyyy");

	public EmployeeInsuranceDAO() {
		super();
	}

	@SuppressWarnings("deprecation")
	private String getMonthYearOfDate(Date date) {
		if (date.getYear() > 1900) {
			return date.getMonth() + "/" + date.getYear();
		}
		return (date.getMonth() + 1) + "/" + (date.getYear() + 1900);
	}

	@SuppressWarnings("deprecation")
	private String convertDateIntoString(Date date) {
		if (date.getYear() > 1900)
			return date.getDate() + "/" + date.getMonth() + "/"
					+ date.getYear();
		else
			return date.getDate() + "/" + (date.getMonth() + 1) + "/"
					+ (date.getYear() + 1900);
	}

	/**
	 * Lấy employee hiện hành.
	 * 
	 * @param nameFact
	 * @throws Exception
	 */
	public List<N_EMPLOYEE> getEmployeeWorking(String nameFact, Date dateWorking)
			throws Exception {

		String strDateWorking = spleDefaultFormat.format(dateWorking);

		String monthYear = strDateWorking.substring(
				strDateWorking.indexOf("/") + 1, strDateWorking.length());
		/*
		 * không nằm trong những empl chưa kí hợp đồng và hưu trí
		 * 
		 * nghỉ việc
		 * 
		 * sản
		 * 
		 * nghỉ việc tăng lại
		 */

		/*
		 * sql lấy những empl thử việc chưa kí hợp đồng và empl hưu trí.
		 * 
		 * emp ky hop dong ngay 15 tai thang
		 */
		String sqlEmplNewTest = "select distinct e.empsn\n"

		+ "from n_newworker_test t, n_employee e, n_department dp\n"

		+ "where t.empsn = e.empsn "

		+ "and e.depsn=dp.id_dept "

		+ "and dp.name_fact='" + nameFact + "'"

				+ " and not exists "

				+ "("

				+ " select lb.empsn from n_labour lb"
				+ " where e.empsn=lb.empsn"

				// chua xem ky cho nay
				// empl ky hop dong < ngay 15
				// + "and cast (to_char(lb.date_s,'dd') as integer) <15 "
				// + "and  to_char(lb.date_s,'mm/yyyy')='"
				// + getMonthYearOfDate(dateWorking) + "'"

				+ ")"

				+ " union  select i.empsn " // empl hưu trí

				+ "from n_not_insurance i  where to_date('" + strDateWorking
				+ "','dd/mm/yyyy') >= i.dates";

		System.out.println("\nsqlEmplNewTest : " + sqlEmplNewTest + "\n");

		/*
		 * Empl đăng ký nghỉ sản
		 */
		// String sqlBear = "select b.empsn from n_time_bear b "
		//
		// + "where  b.b_dates < to_date('16/10/2013', 'dd/mm/yyyy') "
		//
		// + "and b.e_dates > to_date('16/10/2013', 'dd/mm/yyyy')"
		//
		// // không nằm trong những Empl đăng ký nghỉ sản trong tháng
		// + " and b.empsn not in"
		//
		// + " ("
		//
		// + " select bb.empsn from n_time_bear bb "
		//
		// + "where to_char(bb.b_dates,'mm/yyyy')='10/2013')";
		/*
		 * không nằm trong những empl đang nghỉ sản, đăng ký nghỉ sản trong
		 * tháng.
		 */
		String sqlBear = "select b.empsn from n_time_bear b " + "where "
				+ "(b.e_dates > to_date('"
				+ spleDefaultFormat.format(dateWorking) + "','dd/mm/yyyy') "

				+ "and to_char(b.e_dates,'mm/yyyy')!='"
				+ getMonthYearOfDate(dateWorking) + "' "

				+ " and b.b_dates < to_date('"
				+ spleDefaultFormat.format(dateWorking) + "', 'dd/mm/yyyy') "

				+ " and to_char(b.b_dates,'mm/yyyy')!='"
				+ getMonthYearOfDate(dateWorking) + "' "
				+ " or (to_char(b.b_dates,'mm/yyyy')='"
				+ getMonthYearOfDate(dateWorking) + "' "
				+ " or to_char(b.e_dates,'mm/yyyy')='"
				+ getMonthYearOfDate(dateWorking) + "')";

		System.out.println("\n sqlBear : " + sqlBear + "\n");
		// nhan vien nghi viec tang lai

		String sqlEmplQuit = "select q.empsn from n_emp_quit q where to_char(q.thang_trubh, 'mm/yyyy')='"
				+ getMonthYearOfDate(dateWorking) + "')";

		/*
		 * nhân viên tăng lại tháng tăng lại nhỏ hơn tháng hiện tại
		 */
		/*
		 * điều động khu
		 */
		String sqlEmplWorking = "select e.empsn from n_employee e, n_department d "
				+ " where e.depsn= d.id_dept and d.name_fact='"
				+ nameFact
				+ "'"
				// không nằm trong những nhân viên chưa ký hợp đồng và hưu trí.
				+ " \nand e.empsn not in (" + sqlEmplNewTest + ")\n"
				// không nằm trong những nhân viên sản.
				+ " and e.empsn not in (" + sqlBear + ")\n"

				+ " and e.empsn not in (" + sqlEmplQuit + ")\n";
		// không nằm trong những empl nghỉ việc
		// còn tăng lại
		;

		System.out.println("\nsqlEmplWorking " + sqlEmplWorking + "\n");

		List<N_EMPLOYEE> lstEmplWorking = null;

		try {

			lstEmplWorking = getSimpleJdbcTemplate().query(sqlEmplWorking,
					new ParameterizedRowMapper<N_EMPLOYEE>() {
						@Override
						public N_EMPLOYEE mapRow(ResultSet rs, int rowNum)
								throws SQLException {

							N_EMPLOYEE empl = new N_EMPLOYEE();
							empl.setEMPSN(rs.getString("EMPSN"));
							System.out.println("Read record "+empl.getEMPSN());
							return empl;
						}

					}, new Object[] {});

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return lstEmplWorking;
	}

	/**
	 * Lấy employee đang nghỉ sản
	 * 
	 * @param nameFact
	 * @throws Exception
	 */
	public List<EmployeeBear> getEmployeeBeared(String nameFact, Date date)
			throws Exception {

		String sqlEmplBear = "select "

		+ "e.empsn, b.b_dates, b.e_dates, b.note, t.bsalary "

		+ "from"

		+ " n_employee e, n_department d, n_time_bear b, n_basic_salary t "

		+ "where "

		+ "e.depsn= d.id_dept "

		+ "and d.name_fact='"
				+ nameFact
				+ "' "

				+ "and e.empsn= b.empsn "

				// đoạn này vớ vẩn chưa chỉnh lại.
				+ "and b.e_dates > to_date('"
				+ spleDefaultFormat.format(date)
				+ "','dd/mm/yyyy') "

				+ "and to_char(b.e_dates,'mm/yyyy')!='"
				+ getMonthYearOfDate(date)
				+ "' "

				+ " and b.b_dates < to_date('"
				+ spleDefaultFormat.format(date)
				+ "', 'dd/mm/yyyy') "

				+ " and to_char(b.b_dates,'mm/yyyy')!='"
				+ getMonthYearOfDate(date)
				+ "' "
				// end đoạn code vớ vẩn
				// + " and b.e_dates > to_date('01/12/2013', 'dd/mm/yyyy')"

				// + "and to_char(e.b_dates,'mm/yyyy')!='"
				// + getMonthYearOfDate(date)
				// + "' "

				+ "and t.empsn= e.empsn "

				+ "and t.dates= (Select Max(tt.dates) "

				+ "from n_basic_salary tt "
				+ " Where tt.empsn=t.empsn "

				+ "And  tt.dates<=to_Date('"
				+ spleDefaultFormat.format(date)
				+ "','dd/mm/yyyy'))"

				+ " and e.empsn not in "

				+ "(select q.empsn from n_emp_quit q where to_char(q.thang_trubh, 'mm/yyyy')='"

				+ getMonthYearOfDate(date) + "')";

		System.out.println(" getEmployeeBeared sql :" + sqlEmplBear);

		List<EmployeeBear> lstEmplBear = null;
		try {

			lstEmplBear = getSimpleJdbcTemplate().query(sqlEmplBear,
					new ParameterizedRowMapper<EmployeeBear>() {
						@Override
						public EmployeeBear mapRow(ResultSet rs, int rowNum)
								throws SQLException {

							EmployeeBear employeeBear = new EmployeeBear();
							employeeBear.setEMPSN(rs.getString("EMPSN"));
							employeeBear.setB_DATES(rs.getDate("B_DATES"));
							employeeBear.setE_DATES(rs.getDate("E_DATES"));
							employeeBear.setNOTE(rs.getString("NOTE"));
							employeeBear.setBSALARY(rs.getInt("BSALARY"));

							return employeeBear;
						}

					}, new Object[] {});

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return lstEmplBear;
	}

	/**
	 * Lấy employee kết thúc nghỉ sản trong tháng
	 * 
	 * @param nameFact
	 * @param dateEndBear
	 * @return
	 * @throws SQLException
	 */
	public List<EmployeeBear> getEmployeeEndBearInMonth(String nameFact,
			Date dateEndBear) throws SQLException {

		String sql = "select e.empsn, b.e_dates, b.note, t.bsalary "

				+ " from n_employee e, n_department d, n_time_bear b, n_basic_salary t "

				+ " where e.depsn= d.id_dept "

				+ " and d.name_fact='"
				+ nameFact
				+ "' "

				+ " and e.empsn= b.empsn "

				+ " and to_char(b.e_dates,'mm/yyyy')='"

				+ getMonthYearOfDate(dateEndBear)
				+ "' "

				+ " and t.empsn= e.empsn "

				// empl không mua bảo hiểm
				+ " and e.empsn not in( select i.empsn from n_not_insurance i "

				+ " where to_date('"
				+ convertDateIntoString(dateEndBear)
				+ "','dd/mm/yyyy') >= i.dates ) "
				+ " and e.empsn not in "
				// empl nghỉ việc
				+ "(select q.empsn from n_emp_quit q where to_char(q.thang_trubh, 'mm/yyyy')='"
				+ getMonthYearOfDate(dateEndBear) + "')";

		List<EmployeeBear> lstEmployeeEndBear = null;

		try {

			lstEmployeeEndBear = getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<EmployeeBear>() {
						@Override
						public EmployeeBear mapRow(ResultSet arg0, int arg1)
								throws SQLException {
							EmployeeBear empl = new EmployeeBear();
							empl.setEMPSN(arg0.getString("EMPSN"));
							empl.setE_DATES(arg0.getDate("e_dates"));
							empl.setNOTE(arg0.getString("NOTE"));
							return empl;
						}
					});
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		return lstEmployeeEndBear;
	}

	/**
	 * <b>Lấy lương Employee trong khoảng từ đầu tháng ->date
	 * 
	 * @param date
	 * @param empsn
	 * @return salary
	 */
	public double getSalaryInsuranceOfTime1(final Date date, final String empsn) {

		final int outparamIndex = 4;

		double salary = (double) getJdbcTemplate().execute(
				new CallableStatementCreator() {

					@SuppressWarnings("deprecation")
					@Override
					public CallableStatement createCallableStatement(
							Connection connect) throws SQLException {
						String storeProcedure = "{call C5_CALCULATE_SALARY_N_YT_1(?,?,?,?)}";
						CallableStatement callStatement = connect
								.prepareCall(storeProcedure);
						callStatement.setString(1,
								String.valueOf(date.getMonth() + 1));
						callStatement.setString(2,
								String.valueOf(date.getYear() + 1900));
						callStatement.setString(3, empsn);

						callStatement.registerOutParameter(outparamIndex,
								Types.DECIMAL);

						return callStatement;
					}
				}, new CallableStatementCallback() {

					@Override
					public Double doInCallableStatement(CallableStatement cs)
							throws SQLException, DataAccessException {

						cs.execute();
						return cs.getDouble(outparamIndex);
					}
				});
		return salary;

	}

	public double getSalaryEmpQuit1(final Date date, final String empsn,
			Date thangtruBH) throws SQLException {

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		// String date=
		final java.sql.Date date_para_sql = java.sql.Date.valueOf(sf
				.format(thangtruBH));
		System.out.println(date_para_sql.toString());
		final int outparamIndex = 5;

		try {
			double salary = Double.parseDouble(getJdbcTemplate().execute(
					new CallableStatementCreator() {

						@SuppressWarnings("deprecation")
						@Override
						public CallableStatement createCallableStatement(
								Connection connect) throws SQLException {
							String storeProcedure = "{call C5_CALCULATE_SALARY_OFF_YT_1(?,?,?,?,?)}";

							CallableStatement callStatement = connect
									.prepareCall(storeProcedure);

							callStatement.setString(1,
									String.valueOf(date.getMonth()));
							// callStatement.setString(1,
							// String.valueOf(date.getMonth() + 1));
							callStatement.setString(2,
									String.valueOf(date.getYear() + 1900));
							callStatement.setString(3, empsn);
							callStatement.setDate(4, date_para_sql);

							callStatement.registerOutParameter(outparamIndex,
									Types.INTEGER);

							return callStatement;
						}
					}, new CallableStatementCallback() {

						@Override
						public Double doInCallableStatement(CallableStatement cs)
								throws SQLException, DataAccessException {

							cs.execute();
							return cs.getDouble(outparamIndex);
						}
					}).toString());
			return salary;
		} catch (Exception e) {
			throw new SQLException("getSalaryEmpQuit1 : " + e.getMessage());
		}
	}

	public List<N_DETAIL_SUB_INSURANCE> getAllDeptInsuranceOfEmpl(String empsn)
			throws Exception {

		String sqlDept = "select i.empsn, i.insurance_money, i.PURCHASE_DATE, i.NOTE, i.lanchay, i.bsalary, i.ID_DETAIL "

				+ " from N_DETAIL_SUB_INSURANCE i"

				+ " where i.empsn=? and PURCHASE_STATUS=-1";

		List<N_DETAIL_SUB_INSURANCE> lstDept = null;

		try {
			lstDept = getSimpleJdbcTemplate().query(sqlDept,
					new ParameterizedRowMapper<N_DETAIL_SUB_INSURANCE>() {
						@Override
						public N_DETAIL_SUB_INSURANCE mapRow(ResultSet rs,
								int rowNum) throws SQLException {

							N_DETAIL_SUB_INSURANCE empl = new N_DETAIL_SUB_INSURANCE();
							empl.setEMPSN(rs.getString("EMPSN"));
							empl.setPURCHASE_DATE(rs.getString("PURCHASE_DATE"));
							empl.setINSURANCE_MONEY(rs
									.getLong("INSURANCE_MONEY"));
							empl.setBSALARY(rs.getInt("BSALARY"));
							empl.setLANCHAY(rs.getInt("LANCHAY"));
							empl.setNOTE(rs.getString("NOTE"));
							empl.setID_DETAIL(rs.getString("ID_DETAIL"));

							return empl;
						}

					}, new Object[] { empsn });
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return lstDept;
	}

	/**
	 * Danh sách employee nghỉ việc trong tháng.
	 * 
	 * @param nameFact
	 * @param monthYear
	 * @return lstEmployee
	 * @throws SQLException
	 */
	public List<N_EMP_QUIT> getEmplQuitInMonth(String nameFact, String monthYear)
			throws SQLException {

		String sqlEmplQuit = "select "
				+ "eq.empsn, eq.real_off_date, eq.THE_BHYT, eq.DATE_BHYT, eq.THANG_TRUBH "

				+ "from "

				+ "n_department d, n_emp_quit eq "

				+ "where "

				+ "eq.depsn= d.id_dept and d.name_fact= ?"

				+ " and  to_char(eq.thang_trubh, 'mm/yyyy')=?";

		List<N_EMP_QUIT> lstEmpQuit = null;

		try {
			lstEmpQuit = getSimpleJdbcTemplate().query(sqlEmplQuit,
					new ParameterizedRowMapper<N_EMP_QUIT>() {

						@Override
						public N_EMP_QUIT mapRow(ResultSet rs, int rownum)
								throws SQLException {
							N_EMP_QUIT emplQuit = new N_EMP_QUIT();

							emplQuit.setEMPSN(rs.getString("EMPSN"));
							emplQuit.setREAL_OFF_DATE(rs
									.getDate("REAL_OFF_DATE"));
							emplQuit.setTHANG_TRUBH(rs.getDate("THANG_TRUBH"));
							emplQuit.setTHE_BHYT(rs.getString("THE_BHYT"));

							return emplQuit;

						}
					}, new Object[] { nameFact, monthYear });

		} catch (Exception e) {
			// e.printStackTrace();
			throw new SQLException("getEmplQuitInMonth " + e.toString());
		}

		System.out.println("getEmplQuitInMonth " + sqlEmplQuit);

		return lstEmpQuit;
	}

	public int getBSalaryByDate(Date date, String empsn) throws SQLException {

		try {
			String sql = "SELECT t.bsalary FROM n_basic_salary t " +

			"WHERE	t.empsn =? AND t.dates =(Select Max(tt.dates) " +

			"From n_basic_salary tt	" +

			"Where tt.empsn=t.empsn " +

			"And  tt.dates<=to_Date(?,'dd/mm/yyyy'))";

			return getSimpleJdbcTemplate().queryForInt(sql,
					new Object[] { empsn, convertDateIntoString(date) });
		} catch (Exception e) {
			throw new SQLException("getBSalaryByDate " + e.getMessage());
		}

	}

	public Object[] getAllNameFactOfManageUser(String username)
			throws SQLException {
		List<String> result = new ArrayList<String>();
		try {
			String sql = "SELECT DISTINCT dp.name_fact "
					+ " FROM vft.n_department dp, vft.dspb02 ds, vft.n_user_limit ul, vft.n_quanly ql"
					+ " WHERE ds.pb_userid = ? "
					+ " AND ds.pb_userno = ul.ma_user"
					+ " AND ul.ma_ql = ql.maql"
					+ " AND ql.name_fact = dp.name_fact and  dp.name_fact not in ('NV','FVJ')";

			result = getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<String>() {
						@Override
						public String mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return rs.getString("name_fact");
						}

					}, new Object[] { username.toUpperCase() });

		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		return result.toArray();
	}

	public boolean isCheckStatusUpdateOfFactory(String nameFact,
			String monthYear, int lanChay) {

		String sql = "select T.NAME_FACT \n"

		+ " from N_HEALTH_UPDATE_STATUS t\n"

		+ " WHERE T.NAME_FACT = '" + nameFact + "'"

		+ " AND TO_CHAR(T.MONTH_UPDATE,'MM/YYYY') = '" + monthYear + "'"

		+ " AND T.STATUS = 1 AND LANCHAY=" + lanChay;

		return getJdbcTemplate().queryForList(sql).size() > 0;
	}

}