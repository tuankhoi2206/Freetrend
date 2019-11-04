package ds.program.fvhr.vinh.dao_support;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import ds.program.fvhr.khoi.InsuranceProgram;
import ds.program.fvhr.khoi.RecordHDLD;
import ds.program.fvhr.vinh.report_object.EmployeePlain;

import fv.util.JdbcDAO;
import fv.util.Vni2Uni;

public class ExportInsuranceInfoDAO {

	private static JdbcDAO getJdbcDAO() {
		return new JdbcDAO();
	}

	// cập nhật col checked trong bảng n_labour, n_sub_labour
	public static int queryUpdateColumnChecked(String query)
			throws SQLException {

		try {
			return getJdbcDAO().getJdbcTemplate().update(query);
		} catch (Exception e) {
			throw new SQLException(e.toString());
		}
	}

	public static List<String> getGroupUserLimit(String username)
			throws SQLException {
		List<String> result = new ArrayList<String>();
		String sql = "select  n.idgroup from"
				+ " n_users_list u, n_users_list l, n_limits n"
				+ " where  u.user_id=l.user_id "
				+ " and l.id_limit=n.id_limit and n.idgroup in ('NSU','KT') "
				+ " and u.user_name=?";
		try {
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<String>() {

						@Override
						public String mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return rs.getString("idgroup");
						}

					}, new Object[] { username });

			return result;
		} catch (Exception e) {
			throw new SQLException(e.toString());
		}

	}

	public static int checkUserLimit(String userName) throws SQLException {

		try {
			String sql = "select n.idgroup from"
					+ " n_users_list u, n_users_list l, n_limits n"
					+ " where  u.user_id=l.user_id "
					+ " and l.id_limit=n.id_limit and n.idgroup in ('NSU','KT') "
					+ " and u.user_name='" + userName + "'";

			return getJdbcDAO().getJdbcTemplate().update(sql);
		} catch (Exception e) {
			throw new SQLException(e.toString());
		}
	}

	// end khoi

	// khoi
	public static List<RecordHDLD> queryListKemTraHD(final String query,
			boolean isHopDong) throws SQLException {

		List<RecordHDLD> lstRecord = new ArrayList<RecordHDLD>();

		final String colJobs = isHopDong == true ? "poss" : "new_job";
		final String colDate = isHopDong == true ? "date_s" : "dates_sign";
		final String colSalary = isHopDong == true ? "salary" : "new_sal";

		System.out.println("queyListKiemTra " + query);
		try {
			if (!query.trim().isEmpty()) {
				lstRecord = getJdbcDAO().getSimpleJdbcTemplate().query(query,
						new ParameterizedRowMapper<RecordHDLD>() {

							@Override
							public RecordHDLD mapRow(ResultSet rs, int rowNum)
									throws SQLException {

								RecordHDLD aP = new RecordHDLD();
								aP.setFname(rs.getString("fname"));// 1
								aP.setLname(rs.getString("lname"));// 2
								aP.setEmpsn(rs.getString("empsn"));// 3

								aP.setId_dept(rs.getString("id_dept"));// 4;
								aP.setName_group(rs.getString("name_group"));// 5
								aP.setName_fact(rs.getString("name_fact"));// 6
								aP.setName_dept_name(rs
										.getString("name_dept_name"));// 7
								aP.setPoss(rs.getString(colJobs));// 8

								aP.setSalary(Double.parseDouble(rs
										.getString(colSalary)));// 9
								SimpleDateFormat dateFormat = new SimpleDateFormat(
										"dd-MM-yyyy");
								aP.setData_s(dateFormat.format(rs
										.getDate(colDate)));// 10
								aP.setId_labour(rs.getString("id_labour"));// 11
								aP.setTimes(rs.getString("times"));// 12

								String valueChecked = "";
								valueChecked = rs.getString("checked");// 13

								if (query.contains("union")) {
									aP.setTableName(rs.getString(16));
								}

								if (valueChecked != null)
									aP.setChecked(valueChecked);
								else
									aP.setChecked("N");
								return aP;

							}
						}, new Object[] {});
			}
			return lstRecord;
		} catch (Exception e) {
			throw new SQLException(e.toString());
		}
	}

	public ExportInsuranceInfoDAO() {
		super();
	}

	// don vi
	public static List<String> queryAllDeptID(String username) {
		List<String> result = new ArrayList<String>();
		// Connection connect = null;
		// PreparedStatement stm = null;
		// ResultSet rs = null;
		try {
			// connect = ConnectionUtil.getConnection();
			String sql = "SELECT DISTINCT dp.id_dept "
					+ "FROM vft.n_department dp, vft.dspb02 ds, vft.n_user_limit ul, vft.n_quanly ql"
					+ " WHERE ds.pb_userid = ?"
					+ " AND ds.pb_userno = ul.ma_user"
					+ " AND ul.ma_ql = ql.maql"
					+ " AND ql.name_fact = dp.name_fact";
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<String>() {

						@Override
						public String mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return rs.getString("id_dept");
						}

					}, new Object[] { username });

			// stm = connect.prepareStatement(sql);
			// stm.setString(1, username);
			// rs = stm.executeQuery();
			// while (rs.next()) {
			// result.add(rs.getString("id_dept"));
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// try {
			// if (rs != null)
			// rs.close();
			// if (stm != null)
			// stm.close();
			// if (connect != null)
			// connect.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
		return result;
	}

	public static Map<String, String> queryUnitByDeptID(String deptID) {
		Map<String, String> result = new HashMap<String, String>();
		// Connection connect = null;
		// PreparedStatement stm = null;
		// ResultSet rs = null;
		try {
			// connect = ConnectionUtil.getConnection();
			String sql = "SELECT dptm.name_fact, dptm.name_group, dptm.name_dept_name"
					+ " FROM vft.n_department dptm" + " WHERE dptm.id_dept = ?";
			Map<String, Object> rs = getJdbcDAO().getSimpleJdbcTemplate()
					.queryForMap(sql, new Object[] { deptID });
			// stm = connect.prepareStatement(sql);
			// stm.setString(1, deptID);
			// rs = stm.executeQuery();
			// while (rs.next()) {
			result.put("name_fact",
					Vni2Uni.convertToUnicode((String) rs.get("name_fact")));
			result.put("name_group",
					Vni2Uni.convertToUnicode((String) rs.get("name_group")));
			result.put("name_dept_name",
					Vni2Uni.convertToUnicode((String) rs.get("name_dept_name")));
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// try {
			// if (rs != null)
			// rs.close();
			// if (stm != null)
			// stm.close();
			// if (connect != null)
			// connect.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
		return result;
	}

	public static List<String> queryGroupWithFactName(String factName) {
		List<String> result = new ArrayList<String>();
		// Connection connect = null;
		// PreparedStatement stm = null;
		// ResultSet rs = null;
		try {
			// connect = ConnectionUtil.getConnection();
			String sql = "SELECT DISTINCT gr.name_group"
					+ " FROM vft.n_department dptm, vft.n_group_dept gr"
					+ " WHERE dptm.name_fact = ?"
					+ " AND dptm.name_group = gr.name_group";
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<String>() {

						@Override
						public String mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return rs.getString("name_group");
						}

					}, new Object[] { factName });

			// stm = connect.prepareStatement(sql);
			// stm.setString(1, factName);
			// rs = stm.executeQuery();
			// while (rs.next()) {
			// result
			// .add(Vni2Uni.convertToUnicode(rs
			// .getString("name_group")));
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// try {
			// if (rs != null)
			// rs.close();
			// if (stm != null)
			// stm.close();
			// if (connect != null)
			// connect.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
		return result;
	}

	public static List<String> queryUnitsWithGroup(String groupName) {
		List<String> result = new ArrayList<String>();
		// Connection connect = null;
		// PreparedStatement stm = null;
		// ResultSet rs = null;
		try {
			// connect = ConnectionUtil.getConnection();
			String sql = "SELECT DISTINCT dname.dept_name"
					+ " FROM vft.n_department dptm, vft.n_dept_name dname"
					+ " WHERE dptm.name_dept_name = dname.dept_name"
					+ " AND dptm.name_group = ?";
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<String>() {

						@Override
						public String mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return rs.getString("dept_name");
						}

					}, new Object[] { groupName });
			// stm = connect.prepareStatement(sql);
			// stm.setString(1, groupName);
			// rs = stm.executeQuery();
			// while (rs.next()) {
			// result.add(Vni2Uni.convertToUnicode(rs.getString("dept_name")));
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// try {
			// if (rs != null)
			// rs.close();
			// if (stm != null)
			// stm.close();
			// if (connect != null)
			// connect.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
		return result;
	}

	public static List<String> queryAllFactName(String username) {
		List<String> result = new ArrayList<String>();
		// Connection connect = null;
		// PreparedStatement stm = null;
		// ResultSet rs = null;
		try {
			// connect = ConnectionUtil.getConnection();
			String sql = "SELECT DISTINCT dp.name_fact "
					+ " FROM vft.n_department dp, vft.dspb02 ds, vft.n_user_limit ul, vft.n_quanly ql"
					+ " WHERE ds.pb_userid = ? "
					+ " AND ds.pb_userno = ul.ma_user"
					+ " AND ul.ma_ql = ql.maql"
					+ " AND ql.name_fact = dp.name_fact";
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<String>() {

						@Override
						public String mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return rs.getString("name_fact");
						}

					}, new Object[] { username });
			// stm = connect.prepareStatement(sql);
			// stm.setString(1, username);
			// rs = stm.executeQuery();
			// while (rs.next()) {
			// result.add(Vni2Uni.convertToUnicode(rs.getString("name_fact")));
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// try {
			// if (rs != null)
			// rs.close();
			// if (stm != null)
			// stm.close();
			// if (connect != null)
			// connect.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
		return result;
	}

	public static List<String> queryAllGroupName(String username) {
		List<String> result = new ArrayList<String>();
		// Connection connect = null;
		// PreparedStatement stm = null;
		// ResultSet rs = null;
		try {
			// connect = ConnectionUtil.getConnection();
			String sql = "SELECT DISTINCT dp.name_group"
					+ " FROM  vft.n_department dp, vft.dspb02 ds, vft.n_user_limit ul, vft.n_quanly ql"
					+ " WHERE ds.pb_userid = ?"
					+ " AND ds.pb_userno = ul.ma_user"
					+ " AND ul.ma_ql = ql.maql"
					+ " AND ql.name_fact = dp.name_fact";
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<String>() {

						@Override
						public String mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return rs.getString("name_group");
						}

					}, new Object[] { username });

			// stm = connect.prepareStatement(sql);
			// stm.setString(1, username);
			// rs = stm.executeQuery();
			// while (rs.next()) {
			// result
			// .add(Vni2Uni.convertToUnicode(rs
			// .getString("name_group")));
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// try {
			// if (rs != null)
			// rs.close();
			// if (stm != null)
			// stm.close();
			// if (connect != null)
			// connect.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
		return result;
	}

	public static List<String> queryAllUnitName(String username) {
		List<String> result = new ArrayList<String>();
		// Connection connect = null;
		// PreparedStatement stm = null;
		// ResultSet rs = null;
		try {
			// connect = ConnectionUtil.getConnection();
			String sql = "SELECT DISTINCT dp.name_dept_name"
					+ " FROM  vft.n_department dp, vft.dspb02 ds, vft.n_user_limit ul, vft.n_quanly ql"
					+ " WHERE ds.pb_userid = ?"
					+ " AND ds.pb_userno = ul.ma_user"
					+ " AND ul.ma_ql = ql.maql"
					+ " AND ql.name_fact = dp.name_fact";
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<String>() {

						@Override
						public String mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return rs.getString("name_dept_name");
						}

					}, new Object[] { username });
			// stm = connect.prepareStatement(sql);
			// stm.setString(1, username);
			// rs = stm.executeQuery();
			// while (rs.next()) {
			// result.add(Vni2Uni.convertToUnicode(rs
			// .getString("name_dept_name")));
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// try {
			// if (rs != null)
			// rs.close();
			// if (stm != null)
			// stm.close();
			// if (connect != null)
			// connect.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
		return result;
	}

	public static int getDateOfMonth(int month, int year) {

		int result = 30;
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			result = 31;
		} else {
			if (month == 4 || month == 6 || month == 9 || month == 11) {
				result = 30;
			} else {
				if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
					result = 29;
				} else {
					result = 28;
				}
			}
		}

		return result;
	}

	public static List<EmployeePlain> queryAllList(final String month,
			final String year) {
		List<EmployeePlain> result = new ArrayList<EmployeePlain>();
		// Connection connect = null;
		// PreparedStatement stm = null;
		// ResultSet rs = null;
		int m = Integer.parseInt(month);
		int y = Integer.parseInt(year);
		try {
			// connect = ConnectionUtil.getConnection();
			String sql = "SELECT DISTINCT e.empsn, e.fname, e.lname,"
					+ " vft.bonus2_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) bonus,"
					+ " dp.id_dept, dp.name_dept_name, "
					+ " vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) basic_salary,"
					+ " vft.comsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'))) salary,"
					+ " dt.ducls, dt.rest_pay,"
					+ " (nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'NS'), 0)"
					+ "+ nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'NS_RO'), 0))"
					+ " nghi_san,"
					+ " (nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'DS'), 0)) nghi_co_phep,"
					+ " nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'KC'), 0) khang_cong"
					+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp"
					+ " WHERE e.empsn = dt.empsn"
					+ " AND dp.id_dept = e.depsn"
					+ " AND dt.months = '"
					+ month
					+ "'"
					+ " AND dt.years = '"
					+ year + "'";
			// stm.setString(1, factName);

			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<EmployeePlain>() {

						@Override
						public EmployeePlain mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							String empsn = rs.getString("empsn");
							Date signDate = querySignDateWithEMPSN(empsn);
							String limit = queryLimitWithEMPSN(empsn);
							Date quitDay = queryQuitDate(empsn);
							int dayNotSun = getDayNotSUN(
									Integer.parseInt(month),
									Integer.parseInt(year));
							double ducls = rs.getDouble("ducls");
							double restPay = rs.getDouble("rest_pay");
							double ncp = rs.getDouble("nghi_co_phep");
							double ns = rs.getDouble("nghi_san");
							double kc = rs.getDouble("khang_cong");

							double dayOFF = dayNotSun
									- (ducls + restPay + ncp + ns);
							double noSalDay = dayOFF + ns + ncp + kc;

							double uempmInsurance = 0;
							double socialInsurance = 0;
							if (dayOFF < 14) {
								uempmInsurance = queryUempmInsurance(empsn);
								socialInsurance = querySocialInsurance(empsn);
							}
							String lname = "";
							if (rs.getString("lname") != null) {
								lname = rs.getString("lname");
							}
							EmployeePlain aP = new EmployeePlain(empsn, rs
									.getString("fname"), lname, rs
									.getDouble("bonus"), rs
									.getString("id_dept"), rs
									.getString("name_dept_name"), rs
									.getDouble("basic_salary"), rs
									.getDouble("salary"), signDate, limit,
									ducls, restPay, ns, ncp, kc, dayOFF,
									noSalDay, socialInsurance, uempmInsurance,
									quitDay);

							return aP;
						}

					}, new Object[] { month, year });
			// stm = connect
			// .prepareStatement(sql);
			// rs = stm.executeQuery();
			// int i = 1;
			// while (rs.next()) {
			// String empsn = rs.getString("empsn");
			// Date signDate = querySignDateWithEMPSN(empsn);
			// String limit = queryLimitWithEMPSN(empsn);
			// Date quitDay = queryQuitDate(empsn);
			// int dayNotSun = getDayNotSUN(Integer.parseInt(month), Integer
			// .parseInt(year));
			// double ducls = rs.getDouble("ducls");
			// double restPay = rs.getDouble("rest_pay");
			// double ncp = rs.getDouble("nghi_co_phep");
			// double ns = rs.getDouble("nghi_san");
			// double kc = rs.getDouble("khang_cong");
			//
			// double dayOFF = dayNotSun - (ducls + restPay + ncp + ns);
			// double noSalDay = dayOFF + ns + ncp + kc;
			//
			// double uempmInsurance = 0;
			// double socialInsurance = 0;
			// if (dayOFF < 14) {
			// uempmInsurance = queryUempmInsurance(empsn);
			// socialInsurance = querySocialInsurance(empsn);
			// }
			// String lname = "";
			// if (rs.getString("lname") != null) {
			// lname = rs.getString("lname");
			// }
			// EmployeePlain aP = new EmployeePlain(empsn, rs
			// .getString("fname"), lname, rs.getDouble("bonus"), rs
			// .getString("id_dept"), rs.getString("name_dept_name"),
			// rs.getDouble("basic_salary"), rs.getDouble("salary"),
			// signDate, limit, ducls, restPay, ns, ncp, kc, dayOFF,
			// noSalDay, socialInsurance, uempmInsurance, quitDay);
			// result.add(aP);
			// i++;
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// try {
			// if (rs != null)
			// rs.close();
			// if (stm != null)
			// stm.close();
			// if (connect != null)
			// connect.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
		return result;
	}

	private static Date queryQuitDate(String empsn) {
		Connection connect = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			connect = ConnectionUtil.getConnection();
			stm = connect
					.prepareStatement("SELECT eq.real_off_date FROM vft.n_emp_quit eq WHERE eq.empsn = ?");
			stm.setString(1, empsn);
			rs = stm.executeQuery();
			while (rs.next()) {
				return rs.getDate("real_off_date");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stm != null)
					stm.close();
				if (connect != null)
					connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Date querySignDateWithEMPSN(String EMPSN) {
		Connection connect = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			connect = ConnectionUtil.getConnection();
			stm = connect
					.prepareStatement("SELECT MAX(la.date_s) expi FROM vft.n_labour la WHERE la.empsn=?");
			stm.setString(1, EMPSN);
			rs = stm.executeQuery();
			while (rs.next()) {
				return rs.getDate("expi");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stm != null)
					stm.close();
				if (connect != null)
					connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String queryLimitWithEMPSN(String EMPSN) {
		Connection connect = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			connect = ConnectionUtil.getConnection();
			stm = connect
					.prepareStatement("SELECT lk.limit FROM vft.n_labour la, vft.n_labourkind lk WHERE la.empsn= ? AND la.limit = lk.id");
			stm.setString(1, EMPSN);
			rs = stm.executeQuery();
			while (rs.next()) {
				return rs.getString("limit");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stm != null)
					stm.close();
				if (connect != null)
					connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public static double queryUempmInsurance(String empsn) {
		Connection connect = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		double result = 0;
		try {
			connect = ConnectionUtil.getConnection();
			String sql = "SELECT vft.c_mymod1("
					+ "(SELECT MAX(la.salary) FROM vft.n_labour la WHERE la.empsn= ?)"
					+ "*"
					+ "(SELECT ts.giatrithamso FROM vft.n_thamso ts WHERE ts.tenthamso='TYLEBHTN')) ui"
					+ " from dual";
			// result = getJdbcDAO().getSimpleJdbcTemplate().qu

			stm = connect.prepareStatement(sql);
			stm.setString(1, empsn);
			rs = stm.executeQuery();
			if (rs.next()) {
				return rs.getDouble("ui");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stm != null)
					stm.close();
				if (connect != null)
					connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	private static double querySocialInsurance(String empsn) {
		Connection connect = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			connect = ConnectionUtil.getConnection();
			stm = connect
					.prepareStatement("SELECT vft.c_mymod1("
							+ "(SELECT MAX(la.salary) FROM vft.n_labour la WHERE la.empsn= ?)"
							+ "*"
							+ "(SELECT ts.giatrithamso FROM vft.n_thamso ts WHERE ts.tenthamso='TYLEBHXH')) si"
							+ " from dual");
			stm.setString(1, empsn);
			rs = stm.executeQuery();
			if (rs.next()) {
				return rs.getDouble("si");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stm != null)
					stm.close();
				if (connect != null)
					connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	// public static boolean update

	public static String queryMatchUser(String username, String password) {
		Connection connect = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			connect = ConnectionUtil.getConnection();
			stm = connect.prepareStatement("SELECT ds.pb_userid, ds.id_limit"
					+ " FROM vft.dspb02 ds" + " WHERE ds.pb_userid = ?"
					+ " AND ds.pb_pass = ?");
			stm.setString(1, username);
			stm.setString(2, password);
			rs = stm.executeQuery();
			if (rs.next()) {
				return rs.getString("pb_userid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stm != null)
					stm.close();
				if (connect != null)
					connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static int getStatusReport(String factName, String month, String year) {
		int result = -1;
		Connection connect = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			connect = ConnectionUtil.getConnection();
			String sql = "SELECT re.status "
					+ " FROM vft.n_social_status_report re "
					+ " WHERE re.name_fact = '" + factName + "'"
					+ " AND re.month_report = to_date('01/" + month + "/"
					+ year + "', 'dd/mm/yyyy')";

			stm = connect.prepareStatement(sql);
			rs = stm.executeQuery();

			if (rs.next()) {
				result = rs.getInt("status");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stm != null)
					stm.close();
				if (connect != null)
					connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * test function
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// System.out.println(queryTaxpayerListWithDeptName("05", "2013", "FVL",
		// "F2-L06", "THAØNH HÌNH 2").size());
		// System.out.println(getStatusReport("TB", "05", "2013"));
		// saveSocialStatusReport("98100144", new Date(113, 05, 01), 3067000,
		// "DC", "097", new Date());
		// System.out.println(getDayNotSUN(4, 2013));
		// System.out.println(queryWithDeptName("05", "2013", "TB", "VP", "CR",
		// false, false, true, false, false).size());

		// String sql = "SELECT DISTINCT dp.id_dept "
		// + "FROM vft.n_department dp, vft.dspb02 ds, vft.n_user_limit ul,
		// vft.n_quanly ql"
		// + " WHERE ds.pb_userid = ?"
		// + " AND ds.pb_userno = ul.ma_user"
		// + " AND ul.ma_ql = ql.maql"
		// + " AND ql.name_fact = dp.name_fact";
		// List<String> a = new JdbcDAO().getSimpleJdbcTemplate().query(sql,new
		// ParameterizedRowMapper<String>() {
		//
		// @Override
		// public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		// // TODO Auto-generated method stub
		// return rs.getString("id_dept");
		// }
		//
		// }, new Object[] { "IT.TEST" });
		// System.out.println(a);
	}

	public static boolean saveSocialStatusReport(String empsn, Date sDate,
			double comSalary, String note, String pdUserNo, Date updateTime) {
		boolean result = false;
		Connection connect = null;
		CallableStatement stm = null;
		try {
			connect = ConnectionUtil.getConnection();
			String sql = "{call vft.a_n_social_infor_report_i(?,?,?,?,?,?)}";
			stm = connect.prepareCall(sql);

			stm.setString(1, empsn);
			stm.setDate(2, new java.sql.Date(sDate.getTime()));
			stm.setDouble(3, comSalary);
			stm.setString(4, note);
			stm.setString(5, pdUserNo);
			stm.setDate(6, new java.sql.Date(updateTime.getTime()));

			if (stm.executeUpdate() != -1)
				result = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null)
					stm.close();
				if (connect != null)
					connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static double getComSalary(String empsn, String year, String month) {
		Connection connect = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			connect = ConnectionUtil.getConnection();
			String sql = "SELECT vft.comsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month + "/" + year + "', 'dd/mm/yyyy'))) salary"
					+ " FROM vft.n_employee e" + " WHERE e.empsn=?";
			stm = connect.prepareStatement(sql);
			stm.setString(1, empsn);
			rs = stm.executeQuery();
			if (rs.next()) {
				return rs.getDouble("salary");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stm != null)
					stm.close();
				if (connect != null)
					connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public static String getUserNo(String userinfo) {
		Connection connect = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			connect = ConnectionUtil.getConnection();
			String sql = "SELECT ds.pb_userno " + " FROM vft.dspb02 ds"
					+ " WHERE ds.pb_userid=?";
			stm = connect.prepareStatement(sql);
			stm.setString(1, userinfo);
			rs = stm.executeQuery();
			if (rs.next()) {
				return rs.getString("pb_userno");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stm != null)
					stm.close();
				if (connect != null)
					connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public static boolean updateStatusReport(String factName, String month,
			String year) {
		boolean result = false;
		Connection connect = null;
		PreparedStatement stm = null;
		try {
			connect = ConnectionUtil.getConnection();
			String sql = "UPDATE vft.n_social_status_report sr "
					+ " SET sr.status = 1, sr.act_time = ?"
					+ " WHERE sr.name_fact=? "
					+ " AND sr.month_report = to_date('01/" + month + "/"
					+ year + "','dd/mm/yyyy')";
			stm = connect.prepareStatement(sql);
			stm.setDate(1, new java.sql.Date(System.currentTimeMillis()));
			stm.setString(2, factName);
			int rs = stm.executeUpdate();
			if (rs != -1) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null)
					stm.close();
				if (connect != null)
					connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static boolean insertStatusReport(String factName, String month,
			String year, String userNo) {
		// INSERT INTO vft.n_social_status_report(name_fact,
		// month_report,status,note,act_time,act_user) VALUES (?,?,?,?,?,?)
		boolean result = false;
		Connection connect = null;
		PreparedStatement stm = null;
		int m = Integer.parseInt(month);
		int y = Integer.parseInt(year);

		try {
			connect = ConnectionUtil.getConnection();
			String sql = "INSERT INTO vft.n_social_status_report(name_fact, month_report,status,note,act_time,act_user) VALUES (?,?,?,?,?,?)";
			stm = connect.prepareStatement(sql);
			stm.setString(1, factName);
			stm.setDate(2,
					new java.sql.Date(new Date(y - 1900, m - 1, 1).getTime()));
			stm.setInt(3, 1);
			stm.setString(4, "export exl");
			stm.setDate(5, new java.sql.Date(System.currentTimeMillis()));
			stm.setString(6, userNo);
			int rs = stm.executeUpdate();
			if (rs != -1) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null)
					stm.close();
				if (connect != null)
					connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static List<EmployeePlain> queryWithDeptID(final String month,
			final String year, String deptID, boolean insufficientActive,
			boolean sufficientActive, boolean allActive,
			boolean taxpayerActive, boolean onMatLeaveActive) {
		List<EmployeePlain> result = new ArrayList<EmployeePlain>();
		// Connection connect = null;
		// PreparedStatement stm = null;
		// ResultSet rs = null;
		int m = Integer.parseInt(month);
		int y = Integer.parseInt(year);
		try {
			// connect = ConnectionUtil.getConnection();
			String sql = "SELECT DISTINCT e.empsn, e.fname, e.lname,"
					+ " vft.bonus2_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) bonus,"
					+ " dp.id_dept, dp.name_dept_name, "
					+ " vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) basic_salary,"
					+ " vft.comsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'))) salary,"
					+ " dt.ducls, dt.rest_pay,"
					+ " (nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'NS'), 0)"
					+ "+ nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'NS_RO'), 0))"
					+ " nghi_san,"
					+ " (nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'DS'), 0)) nghi_co_phep,"
					+ " nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'KC'), 0) khang_cong";
			if (sufficientActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_labour l"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.id_dept = '"
						+ deptID
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND (vft.c_day_notsun(to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')) - (dt.ducls + dt.rest_pay "
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'NS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'DS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'KC'), 0))) < 14"
						+ " AND l.empsn = e.empsn"
						+ " AND l.times = 1"
						+ " AND l.date_s <= to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')"
						+ " AND e.empsn NOT IN ((SELECT ni.empsn FROM vft.n_not_insurance ni WHERE ni.dates < to_date('01/"
						+ month + "/" + year + "','dd/mm/yyyy')))";
			}
			if (insufficientActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_labour l"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.id_dept = '"
						+ deptID
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND (vft.c_day_notsun(to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')) - (dt.ducls + dt.rest_pay "
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'NS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'DS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'KC'), 0))) >= 14"
						+ " AND l.empsn = e.empsn"
						+ " AND l.times = 1"
						+ " AND l.date_s <= to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')"
						+ " AND e.empsn NOT IN ((SELECT ni.empsn FROM vft.n_not_insurance ni WHERE ni.dates < to_date('01/"
						+ month + "/" + year + "','dd/mm/yyyy')))";
			}
			if (allActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp"
						+ " WHERE e.empsn = dt.empsn" + " AND dp.id_dept = '"
						+ deptID + "'" + " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '" + month + "'"
						+ " AND dt.years = '" + year + "'";
			}
			if (taxpayerActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_emp_quit eq"
						+ " WHERE e.empsn = dt.empsn" + " AND dp.id_dept = '"
						+ deptID + "'" + " AND dp.id_dept = eq.depsn"
						+ " AND dt.months = '" + month + "'"
						+ " AND dt.years = '" + year + "'"
						+ " AND e.empsn = eq.empsn"
						+ " AND eq.thang_trubh = to_date('01/" + month + "/"
						+ year + "', 'dd/mm/yyyy')";
			}
			if (onMatLeaveActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.id_dept = '"
						+ deptID
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND e.empsn IN (SELECT tb.empsn FROM vft.n_time_bear tb"
						+ " WHERE tb.b_dates >= to_date('01/" + month + "/"
						+ year + "', 'dd/mm/yyyy')"
						+ " AND tb.b_dates <= to_date('" + getDateOfMonth(m, y)
						+ "/" + month + "/" + year + "', 'dd/mm/yyyy'))";
			}

			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<EmployeePlain>() {

						@Override
						public EmployeePlain mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							String empsn = rs.getString("empsn");
							Date signDate = querySignDateWithEMPSN(empsn);
							String limit = queryLimitWithEMPSN(empsn);
							Date quitDay = queryQuitDate(empsn);
							int dayNotSun = getDayNotSUN(
									Integer.parseInt(month),
									Integer.parseInt(year));
							double ducls = rs.getDouble("ducls");
							double restPay = rs.getDouble("rest_pay");
							double ncp = rs.getDouble("nghi_co_phep");
							double ns = rs.getDouble("nghi_san");
							double kc = rs.getDouble("khang_cong");

							double dayOFF = dayNotSun
									- (ducls + restPay + ncp + ns);
							double noSalDay = dayOFF + ns + ncp + kc;

							double uempmInsurance = 0;
							double socialInsurance = 0;
							if (dayOFF < 14) {
								uempmInsurance = queryUempmInsurance(empsn);
								socialInsurance = querySocialInsurance(empsn);
							}
							String lname = "";
							if (rs.getString("lname") != null) {
								lname = rs.getString("lname");
							}
							EmployeePlain aP = new EmployeePlain(empsn, rs
									.getString("fname"), lname, rs
									.getDouble("bonus"), rs
									.getString("id_dept"), rs
									.getString("name_dept_name"), rs
									.getDouble("basic_salary"), rs
									.getDouble("salary"), signDate, limit,
									ducls, restPay, ns, ncp, kc, dayOFF,
									noSalDay, socialInsurance, uempmInsurance,
									quitDay);

							return aP;
						}

					}, new Object[] {});
			// stm = connect.prepareStatement(sql);
			// rs = stm.executeQuery();
			// int i = 1;
			// while (rs.next()) {
			// String empsn = rs.getString("empsn");
			// Date signDate = querySignDateWithEMPSN(empsn);
			// String limit = queryLimitWithEMPSN(empsn);
			// Date quitDay = queryQuitDate(empsn);
			// int dayNotSun = getDayNotSUN(Integer.parseInt(month), Integer
			// .parseInt(year));
			// double ducls = rs.getDouble("ducls");
			// double restPay = rs.getDouble("rest_pay");
			// double ncp = rs.getDouble("nghi_co_phep");
			// double ns = rs.getDouble("nghi_san");
			// double kc = rs.getDouble("khang_cong");
			//
			// double dayOFF = dayNotSun - (ducls + restPay + ncp + ns);
			// double noSalDay = dayOFF + ns + ncp + kc;
			// double uempmInsurance = 0;
			// double socialInsurance = 0;
			// if (dayOFF < 14) {
			// uempmInsurance = queryUempmInsurance(empsn);
			// socialInsurance = querySocialInsurance(empsn);
			// }
			// String lname = "";
			// if (rs.getString("lname") != null) {
			// lname = rs.getString("lname");
			// }
			// EmployeePlain aP = new EmployeePlain(empsn, rs
			// .getString("fname"), lname, rs.getDouble("bonus"), rs
			// .getString("id_dept"), rs.getString("name_dept_name"),
			// rs.getDouble("basic_salary"), rs.getDouble("salary"),
			// signDate, limit, ducls, restPay, ns, ncp, kc, dayOFF,
			// noSalDay, socialInsurance, uempmInsurance, quitDay);
			// result.add(aP);
			// i++;
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// try {
			// if (rs != null)
			// rs.close();
			// if (stm != null)
			// stm.close();
			// if (connect != null)
			// connect.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
		return result;
	}

	public static List<EmployeePlain> queryWithDeptName(final String month,
			final String year, String factName, boolean insufficientActive,
			boolean sufficientActive, boolean allActive,
			boolean taxpayerActive, boolean onMatLeaveActive) {
		List<EmployeePlain> result = new ArrayList<EmployeePlain>();
		// Connection connect = null;
		// PreparedStatement stm = null;
		// ResultSet rs = null;
		int m = Integer.parseInt(month);
		int y = Integer.parseInt(year);
		try {
			// connect = ConnectionUtil.getConnection();
			String sql = "SELECT DISTINCT e.empsn, e.fname, e.lname,"
					+ " vft.bonus2_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) bonus,"
					+ " dp.id_dept, dp.name_dept_name, "
					+ " vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) basic_salary,"
					+ " vft.comsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'))) salary,"
					+ " dt.ducls, dt.rest_pay,"
					+ " (nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'NS'), 0)"
					+ "+ nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'NS_RO'), 0))"
					+ " nghi_san,"
					+ " (nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'DS'), 0)) nghi_co_phep,"
					+ " nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'KC'), 0) khang_cong";
			if (sufficientActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_labour l"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_fact = '"
						+ factName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND (vft.c_day_notsun(to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')) - (dt.ducls + dt.rest_pay "
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'NS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'DS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'KC'), 0))) < 14"
						+ " AND l.empsn = e.empsn"
						+ " AND l.times = 1"
						+ " AND l.date_s <= to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')"
						+ " AND e.empsn NOT IN ((SELECT ni.empsn FROM vft.n_not_insurance ni WHERE ni.dates < to_date('01/"
						+ month + "/" + year + "','dd/mm/yyyy')))";
			}
			if (insufficientActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_labour l"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_fact = '"
						+ factName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND (vft.c_day_notsun(to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')) - (dt.ducls + dt.rest_pay "
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'NS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'DS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'KC'), 0))) >= 14"
						+ " AND l.empsn = e.empsn"
						+ " AND l.times = 1"
						+ " AND l.date_s <= to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')"
						+ " AND e.empsn NOT IN ((SELECT ni.empsn FROM vft.n_not_insurance ni WHERE ni.dates < to_date('01/"
						+ month + "/" + year + "','dd/mm/yyyy')))";
			}
			if (allActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp"
						+ " WHERE e.empsn = dt.empsn" + " AND dp.name_fact = '"
						+ factName + "'" + " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '" + month + "'"
						+ " AND dt.years = '" + year + "'";
			}
			if (taxpayerActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_emp_quit eq"
						+ " WHERE e.empsn = dt.empsn" + " AND dp.name_fact='"
						+ factName + "'" + " AND dp.id_dept = eq.depsn"
						+ " AND dt.months = '" + month + "'"
						+ " AND dt.years = '" + year + "'"
						+ " AND e.empsn = eq.empsn"
						+ " AND eq.thang_trubh = to_date('01/" + month + "/"
						+ year + "', 'dd/mm/yyyy')";
			}
			if (onMatLeaveActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_fact = '"
						+ factName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND e.empsn IN (SELECT tb.empsn FROM vft.n_time_bear tb"
						+ " WHERE tb.b_dates >= to_date('01/" + month + "/"
						+ year + "', 'dd/mm/yyyy')"
						+ " AND tb.b_dates <= to_date('" + getDateOfMonth(m, y)
						+ "/" + month + "/" + year + "', 'dd/mm/yyyy'))";
			}

			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<EmployeePlain>() {

						@Override
						public EmployeePlain mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							String empsn = rs.getString("empsn");
							Date signDate = querySignDateWithEMPSN(empsn);
							String limit = queryLimitWithEMPSN(empsn);
							Date quitDay = queryQuitDate(empsn);
							int dayNotSun = getDayNotSUN(
									Integer.parseInt(month),
									Integer.parseInt(year));
							double ducls = rs.getDouble("ducls");
							double restPay = rs.getDouble("rest_pay");
							double ncp = rs.getDouble("nghi_co_phep");
							double ns = rs.getDouble("nghi_san");
							double kc = rs.getDouble("khang_cong");

							double dayOFF = dayNotSun
									- (ducls + restPay + ncp + ns);
							double noSalDay = dayOFF + ns + ncp + kc;

							double uempmInsurance = 0;
							double socialInsurance = 0;
							if (dayOFF < 14) {
								uempmInsurance = queryUempmInsurance(empsn);
								socialInsurance = querySocialInsurance(empsn);
							}
							String lname = "";
							if (rs.getString("lname") != null) {
								lname = rs.getString("lname");
							}
							EmployeePlain aP = new EmployeePlain(empsn, rs
									.getString("fname"), lname, rs
									.getDouble("bonus"), rs
									.getString("id_dept"), rs
									.getString("name_dept_name"), rs
									.getDouble("basic_salary"), rs
									.getDouble("salary"), signDate, limit,
									ducls, restPay, ns, ncp, kc, dayOFF,
									noSalDay, socialInsurance, uempmInsurance,
									quitDay);

							return aP;
						}

					}, new Object[] {});
			// stm = connect.prepareStatement(sql);
			// rs = stm.executeQuery();
			// int i = 1;
			// while (rs.next()) {
			// String empsn = rs.getString("empsn");
			// Date signDate = querySignDateWithEMPSN(empsn);
			// String limit = queryLimitWithEMPSN(empsn);
			// Date quitDay = queryQuitDate(empsn);
			// int dayNotSun = getDayNotSUN(Integer.parseInt(month), Integer
			// .parseInt(year));
			// double ducls = rs.getDouble("ducls");
			// double restPay = rs.getDouble("rest_pay");
			// double ncp = rs.getDouble("nghi_co_phep");
			// double ns = rs.getDouble("nghi_san");
			// double kc = rs.getDouble("khang_cong");
			//
			// double dayOFF = dayNotSun - (ducls + restPay + ncp + ns);
			// double noSalDay = dayOFF + ns + ncp + kc;
			//
			// double uempmInsurance = 0;
			// double socialInsurance = 0;
			// if (dayOFF < 14) {
			// uempmInsurance = queryUempmInsurance(empsn);
			// socialInsurance = querySocialInsurance(empsn);
			// }
			// String lname = "";
			// if (rs.getString("lname") != null) {
			// lname = rs.getString("lname");
			// }
			// EmployeePlain aP = new EmployeePlain(empsn, rs
			// .getString("fname"), lname, rs.getDouble("bonus"), rs
			// .getString("id_dept"), rs.getString("name_dept_name"),
			// rs.getDouble("basic_salary"), rs.getDouble("salary"),
			// signDate, limit, ducls, restPay, ns, ncp, kc, dayOFF,
			// noSalDay, socialInsurance, uempmInsurance, quitDay);
			// result.add(aP);
			// i++;
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// try {
			// if (rs != null)
			// rs.close();
			// if (stm != null)
			// stm.close();
			// if (connect != null)
			// connect.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
		return result;
	}

	public static List<EmployeePlain> queryWithDeptName(final String month,
			final String year, String factName, String groupName,
			boolean insufficientActive, boolean sufficientActive,
			boolean allActive, boolean taxpayerActive, boolean onMatLeaveActive) {
		List<EmployeePlain> result = new ArrayList<EmployeePlain>();
		// Connection connect = null;
		// PreparedStatement stm = null;
		// ResultSet rs = null;
		int m = Integer.parseInt(month);
		int y = Integer.parseInt(year);
		try {
			// connect = ConnectionUtil.getConnection();
			String sql = "SELECT DISTINCT e.empsn, e.fname, e.lname,"
					+ " vft.bonus2_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) bonus,"
					+ " dp.id_dept, dp.name_dept_name, "
					+ " vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) basic_salary,"
					+ " vft.comsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'))) salary,"
					+ " dt.ducls, dt.rest_pay,"
					+ " (nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'NS'), 0)"
					+ "+ nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'NS_RO'), 0))"
					+ " nghi_san,"
					+ " (nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'DS'), 0)) nghi_co_phep,"
					+ " nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'KC'), 0) khang_cong";
			if (sufficientActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_labour l"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_fact = '"
						+ factName
						+ "'"
						+ " AND dp.name_group = '"
						+ groupName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND (vft.c_day_notsun(to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')) - (dt.ducls + dt.rest_pay "
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'NS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'DS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'KC'), 0))) < 14"
						+ " AND l.empsn = e.empsn"
						+ " AND l.times = 1"
						+ " AND l.date_s <= to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')"
						+ " AND e.empsn NOT IN ((SELECT ni.empsn FROM vft.n_not_insurance ni WHERE ni.dates < to_date('01/"
						+ month + "/" + year + "','dd/mm/yyyy')))";
			}
			if (insufficientActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_labour l"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_fact = '"
						+ factName
						+ "'"
						+ " AND dp.name_group = '"
						+ groupName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND (vft.c_day_notsun(to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')) - (dt.ducls + dt.rest_pay "
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'NS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'DS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'KC'), 0))) >= 14"
						+ " AND l.empsn = e.empsn"
						+ " AND l.times = 1"
						+ " AND l.date_s <= to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')"
						+ " AND e.empsn NOT IN ((SELECT ni.empsn FROM vft.n_not_insurance ni WHERE ni.dates < to_date('01/"
						+ month + "/" + year + "','dd/mm/yyyy')))";
			}
			if (allActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp"
						+ " WHERE e.empsn = dt.empsn" + " AND dp.name_fact = '"
						+ factName + "'" + " AND dp.name_group = '" + groupName
						+ "'" + " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '" + month + "'"
						+ " AND dt.years = '" + year + "'";
			}
			if (taxpayerActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_emp_quit eq"
						+ " WHERE e.empsn = dt.empsn" + " AND dp.name_fact='"
						+ factName + "'" + " AND dp.name_group='" + groupName
						+ "'" + " AND dp.id_dept = eq.depsn"
						+ " AND dt.months = '" + month + "'"
						+ " AND dt.years = '" + year + "'"
						+ " AND e.empsn = eq.empsn"
						+ " AND eq.thang_trubh = to_date('01/" + month + "/"
						+ year + "', 'dd/mm/yyyy')";
			}
			if (onMatLeaveActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_fact = '"
						+ factName
						+ "'"
						+ " AND dp.name_group = '"
						+ groupName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND e.empsn IN (SELECT tb.empsn FROM vft.n_time_bear tb"
						+ " WHERE tb.b_dates >= to_date('01/" + month + "/"
						+ year + "', 'dd/mm/yyyy')"
						+ " AND tb.b_dates <= to_date('" + getDateOfMonth(m, y)
						+ "/" + month + "/" + year + "', 'dd/mm/yyyy'))";
			}
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<EmployeePlain>() {

						@Override
						public EmployeePlain mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							String empsn = rs.getString("empsn");
							Date signDate = querySignDateWithEMPSN(empsn);
							String limit = queryLimitWithEMPSN(empsn);
							Date quitDay = queryQuitDate(empsn);
							int dayNotSun = getDayNotSUN(
									Integer.parseInt(month),
									Integer.parseInt(year));
							double ducls = rs.getDouble("ducls");
							double restPay = rs.getDouble("rest_pay");
							double ncp = rs.getDouble("nghi_co_phep");
							double ns = rs.getDouble("nghi_san");
							double kc = rs.getDouble("khang_cong");

							double dayOFF = dayNotSun
									- (ducls + restPay + ncp + ns);
							double noSalDay = dayOFF + ns + ncp + kc;

							double uempmInsurance = 0;
							double socialInsurance = 0;
							if (dayOFF < 14) {
								uempmInsurance = queryUempmInsurance(empsn);
								socialInsurance = querySocialInsurance(empsn);
							}
							String lname = "";
							if (rs.getString("lname") != null) {
								lname = rs.getString("lname");
							}
							EmployeePlain aP = new EmployeePlain(empsn, rs
									.getString("fname"), lname, rs
									.getDouble("bonus"), rs
									.getString("id_dept"), rs
									.getString("name_dept_name"), rs
									.getDouble("basic_salary"), rs
									.getDouble("salary"), signDate, limit,
									ducls, restPay, ns, ncp, kc, dayOFF,
									noSalDay, socialInsurance, uempmInsurance,
									quitDay);

							return aP;
						}

					}, new Object[] {});
			// stm = connect.prepareStatement(sql);
			// rs = stm.executeQuery();
			// int i = 1;
			// while (rs.next()) {
			// String empsn = rs.getString("empsn");
			// Date signDate = querySignDateWithEMPSN(empsn);
			// String limit = queryLimitWithEMPSN(empsn);
			// Date quitDay = queryQuitDate(empsn);
			// int dayNotSun = getDayNotSUN(Integer.parseInt(month), Integer
			// .parseInt(year));
			// double ducls = rs.getDouble("ducls");
			// double restPay = rs.getDouble("rest_pay");
			// double ncp = rs.getDouble("nghi_co_phep");
			// double ns = rs.getDouble("nghi_san");
			// double kc = rs.getDouble("khang_cong");
			//
			// double dayOFF = dayNotSun - (ducls + restPay + ncp + ns);
			// double noSalDay = dayOFF + ns + ncp + kc;
			//
			// double uempmInsurance = 0;
			// double socialInsurance = 0;
			// if (dayOFF < 14) {
			// uempmInsurance = queryUempmInsurance(empsn);
			// socialInsurance = querySocialInsurance(empsn);
			// }
			// String lname = "";
			// if (rs.getString("lname") != null) {
			// lname = rs.getString("lname");
			// }
			// EmployeePlain aP = new EmployeePlain(empsn, rs
			// .getString("fname"), lname, rs.getDouble("bonus"), rs
			// .getString("id_dept"), rs.getString("name_dept_name"),
			// rs.getDouble("basic_salary"), rs.getDouble("salary"),
			// signDate, limit, ducls, restPay, ns, ncp, kc, dayOFF,
			// noSalDay, socialInsurance, uempmInsurance, quitDay);
			// result.add(aP);
			// i++;
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// try {
			// if (rs != null)
			// rs.close();
			// if (stm != null)
			// stm.close();
			// if (connect != null)
			// connect.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
		return result;
	}

	public static List<EmployeePlain> queryWithDeptName(final String month,
			final String year, String factName, String groupName,
			String unitName, boolean insufficientActive,
			boolean sufficientActive, boolean allActive,
			boolean taxpayerActive, boolean onMatLeaveActive) {
		List<EmployeePlain> result = new ArrayList<EmployeePlain>();
		// Connection connect = null;
		// PreparedStatement stm = null;
		// ResultSet rs = null;
		int m = Integer.parseInt(month);
		int y = Integer.parseInt(year);
		try {
			// connect = ConnectionUtil.getConnection();
			String sql = "SELECT DISTINCT e.empsn, e.fname, e.lname,"
					+ " vft.bonus2_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) bonus,"
					+ " dp.id_dept, dp.name_dept_name, "
					+ " vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) basic_salary,"
					+ " vft.comsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'))) salary,"
					+ " dt.ducls, dt.rest_pay,"
					+ " (nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'NS'), 0)"
					+ "+ nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'NS_RO'), 0))"
					+ " nghi_san,"
					+ " (nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'DS'), 0)) nghi_co_phep,"
					+ " nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'KC'), 0) khang_cong";
			if (sufficientActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_labour l"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_fact = '"
						+ factName
						+ "'"
						+ " AND dp.name_group = '"
						+ groupName
						+ "'"
						+ " AND dp.name_dept_name = '"
						+ unitName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND (vft.c_day_notsun(to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')) - (dt.ducls + dt.rest_pay "
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'NS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'DS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'KC'), 0))) < 14"
						+ " AND l.empsn = e.empsn"
						+ " AND l.times = 1"
						+ " AND l.date_s <= to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')"
						+ " AND e.empsn NOT IN ((SELECT ni.empsn FROM vft.n_not_insurance ni WHERE ni.dates < to_date('01/"
						+ month + "/" + year + "','dd/mm/yyyy')))";
			}
			if (insufficientActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_labour l"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_fact = '"
						+ factName
						+ "'"
						+ " AND dp.name_group = '"
						+ groupName
						+ "'"
						+ " AND dp.name_dept_name = '"
						+ unitName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND (vft.c_day_notsun(to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')) - (dt.ducls + dt.rest_pay "
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'NS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'DS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'KC'), 0))) >= 14"
						+ " AND l.empsn = e.empsn"
						+ " AND l.times = 1"
						+ " AND l.date_s <= to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')"
						+ " AND e.empsn NOT IN ((SELECT ni.empsn FROM vft.n_not_insurance ni WHERE ni.dates < to_date('01/"
						+ month + "/" + year + "','dd/mm/yyyy')))";
			}
			if (allActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp"
						+ " WHERE e.empsn = dt.empsn" + " AND dp.name_fact = '"
						+ factName + "'" + " AND dp.name_group = '" + groupName
						+ "'" + " AND dp.name_dept_name = '" + unitName + "'"
						+ " AND dp.id_dept = e.depsn" + " AND dt.months = '"
						+ month + "'" + " AND dt.years = '" + year + "'";
			}
			if (taxpayerActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_emp_quit eq"
						+ " WHERE e.empsn = dt.empsn" + " AND dp.name_fact='"
						+ factName + "'" + " AND dp.name_group='" + groupName
						+ "'" + " AND dp.name_dept_name='" + unitName + "'"
						+ " AND dp.id_dept = eq.depsn" + " AND dt.months = '"
						+ month + "'" + " AND dt.years = '" + year + "'"
						+ " AND e.empsn = eq.empsn"
						+ " AND eq.thang_trubh = to_date('01/" + month + "/"
						+ year + "', 'dd/mm/yyyy')";
			}
			if (onMatLeaveActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_fact = '"
						+ factName
						+ "'"
						+ " AND dp.name_group = '"
						+ groupName
						+ "'"
						+ " AND dp.name_dept_name = '"
						+ unitName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND e.empsn IN (SELECT tb.empsn FROM vft.n_time_bear tb"
						+ " WHERE tb.b_dates >= to_date('01/" + month + "/"
						+ year + "', 'dd/mm/yyyy')"
						+ " AND tb.b_dates <= to_date('" + getDateOfMonth(m, y)
						+ "/" + month + "/" + year + "', 'dd/mm/yyyy'))";
			}
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<EmployeePlain>() {

						@Override
						public EmployeePlain mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							String empsn = rs.getString("empsn");
							Date signDate = querySignDateWithEMPSN(empsn);
							String limit = queryLimitWithEMPSN(empsn);
							Date quitDay = queryQuitDate(empsn);
							int dayNotSun = getDayNotSUN(
									Integer.parseInt(month),
									Integer.parseInt(year));
							double ducls = rs.getDouble("ducls");
							double restPay = rs.getDouble("rest_pay");
							double ncp = rs.getDouble("nghi_co_phep");
							double ns = rs.getDouble("nghi_san");
							double kc = rs.getDouble("khang_cong");

							double dayOFF = dayNotSun
									- (ducls + restPay + ncp + ns);
							double noSalDay = dayOFF + ns + ncp + kc;

							double uempmInsurance = 0;
							double socialInsurance = 0;
							if (dayOFF < 14) {
								uempmInsurance = queryUempmInsurance(empsn);
								socialInsurance = querySocialInsurance(empsn);
							}
							String lname = "";
							if (rs.getString("lname") != null) {
								lname = rs.getString("lname");
							}
							EmployeePlain aP = new EmployeePlain(empsn, rs
									.getString("fname"), lname, rs
									.getDouble("bonus"), rs
									.getString("id_dept"), rs
									.getString("name_dept_name"), rs
									.getDouble("basic_salary"), rs
									.getDouble("salary"), signDate, limit,
									ducls, restPay, ns, ncp, kc, dayOFF,
									noSalDay, socialInsurance, uempmInsurance,
									quitDay);

							return aP;
						}

					}, new Object[] {});
			// stm = connect.prepareStatement(sql);
			// rs = stm.executeQuery();
			// int i = 1;
			// while (rs.next()) {
			// String empsn = rs.getString("empsn");
			// Date signDate = querySignDateWithEMPSN(empsn);
			// String limit = queryLimitWithEMPSN(empsn);
			// Date quitDay = queryQuitDate(empsn);
			// int dayNotSun = getDayNotSUN(Integer.parseInt(month), Integer
			// .parseInt(year));
			// double ducls = rs.getDouble("ducls");
			// double restPay = rs.getDouble("rest_pay");
			// double ncp = rs.getDouble("nghi_co_phep");
			// double ns = rs.getDouble("nghi_san");
			// double kc = rs.getDouble("khang_cong");
			//
			// double dayOFF = dayNotSun - (ducls + restPay + ncp + ns);
			// double noSalDay = dayOFF + ns + ncp + kc;
			//
			// double uempmInsurance = 0;
			// double socialInsurance = 0;
			// if (dayOFF < 14) {
			// uempmInsurance = queryUempmInsurance(empsn);
			// socialInsurance = querySocialInsurance(empsn);
			// }
			// String lname = "";
			// if (rs.getString("lname") != null) {
			// lname = rs.getString("lname");
			// }
			// EmployeePlain aP = new EmployeePlain(empsn, rs
			// .getString("fname"), lname, rs.getDouble("bonus"), rs
			// .getString("id_dept"), rs.getString("name_dept_name"),
			// rs.getDouble("basic_salary"), rs.getDouble("salary"),
			// signDate, limit, ducls, restPay, ns, ncp, kc, dayOFF,
			// noSalDay, socialInsurance, uempmInsurance, quitDay);
			// result.add(aP);
			// i++;
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// try {
			// if (rs != null)
			// rs.close();
			// if (stm != null)
			// stm.close();
			// if (connect != null)
			// connect.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
		return result;
	}

	public static List<EmployeePlain> queryAllList(final String month,
			final String year, boolean insufficientActive,
			boolean sufficientActive, boolean allActive,
			boolean taxpayerActive, boolean onMatLeaveActive, String userID) {
		List<EmployeePlain> result = new ArrayList<EmployeePlain>();
		// Connection connect = null;
		// PreparedStatement stm = null;
		// ResultSet rs = null;
		int m = Integer.parseInt(month);
		int y = Integer.parseInt(year);
		try {
			// connect = ConnectionUtil.getConnection();
			String sql = "SELECT DISTINCT e.empsn, e.fname, e.lname,"
					+ " vft.bonus2_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) bonus,"
					+ " dp.id_dept, dp.name_dept_name, "
					+ " vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) basic_salary,"
					+ " vft.comsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'))) salary,"
					+ " dt.ducls, dt.rest_pay,"
					+ " (nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'NS'), 0)"
					+ "+ nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'NS_RO'), 0))"
					+ " nghi_san,"
					+ " (nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'DS'), 0)) nghi_co_phep,"
					+ " nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'KC'), 0) khang_cong";
			if (sufficientActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_labour l"
						+ " WHERE e.empsn = dt.empsn"

						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND (vft.c_day_notsun(to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')) - (dt.ducls + dt.rest_pay "
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'NS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'DS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'KC'), 0))) < 14"
						+ " AND l.empsn = e.empsn"
						+ " AND l.times = 1"
						+ " AND l.date_s <= to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')"
						+ " AND e.empsn NOT IN ((SELECT ni.empsn FROM vft.n_not_insurance ni WHERE ni.dates < to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')))"
						+ " AND dp.id_dept IN (SELECT DISTINCT dp.id_dept "
						+ " FROM vft.n_department dp, vft.dspb02 ds, vft.n_user_limit ul, vft.n_quanly ql"
						+ " WHERE ds.pb_userid = '" + userID + "'"
						+ " AND ds.pb_userno = ul.ma_user"
						+ " AND ul.ma_ql = ql.maql"
						+ " AND ql.name_fact = dp.name_fact)";
			}
			if (insufficientActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_labour l"
						+ " WHERE e.empsn = dt.empsn"

						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND (vft.c_day_notsun(to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')) - (dt.ducls + dt.rest_pay "
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'NS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'DS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'KC'), 0))) >= 14"
						+ " AND l.empsn = e.empsn"
						+ " AND l.times = 1"
						+ " AND l.date_s <= to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')"
						+ " AND e.empsn NOT IN ((SELECT ni.empsn FROM vft.n_not_insurance ni WHERE ni.dates < to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')))"
						+ " AND dp.id_dept IN (SELECT DISTINCT dp.id_dept "
						+ " FROM vft.n_department dp, vft.dspb02 ds, vft.n_user_limit ul, vft.n_quanly ql"
						+ " WHERE ds.pb_userid = '" + userID + "'"
						+ " AND ds.pb_userno = ul.ma_user"
						+ " AND ul.ma_ql = ql.maql"
						+ " AND ql.name_fact = dp.name_fact)";
			}
			if (allActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND dp.id_dept IN (SELECT DISTINCT dp.id_dept "
						+ " FROM vft.n_department dp, vft.dspb02 ds, vft.n_user_limit ul, vft.n_quanly ql"
						+ " WHERE ds.pb_userid = '" + userID + "'"
						+ " AND ds.pb_userno = ul.ma_user"
						+ " AND ul.ma_ql = ql.maql"
						+ " AND ql.name_fact = dp.name_fact)";
			}
			if (taxpayerActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_emp_quit eq"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.id_dept = eq.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND e.empsn = eq.empsn"
						+ " AND eq.thang_trubh = to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy')"
						+ " AND dp.id_dept IN (SELECT DISTINCT dp.id_dept "
						+ " FROM vft.n_department dp, vft.dspb02 ds, vft.n_user_limit ul, vft.n_quanly ql"
						+ " WHERE ds.pb_userid = '" + userID + "'"
						+ " AND ds.pb_userno = ul.ma_user"
						+ " AND ul.ma_ql = ql.maql"
						+ " AND ql.name_fact = dp.name_fact)";
			}
			if (onMatLeaveActive) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND e.empsn IN (SELECT tb.empsn FROM vft.n_time_bear tb"
						+ " WHERE tb.b_dates >= to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy')"
						+ " AND tb.b_dates <= to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'))"
						+ " AND dp.id_dept IN (SELECT DISTINCT dp.id_dept "
						+ " FROM vft.n_department dp, vft.dspb02 ds, vft.n_user_limit ul, vft.n_quanly ql"
						+ " WHERE ds.pb_userid = '" + userID + "'"
						+ " AND ds.pb_userno = ul.ma_user"
						+ " AND ul.ma_ql = ql.maql"
						+ " AND ql.name_fact = dp.name_fact)";
			}
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<EmployeePlain>() {

						@Override
						public EmployeePlain mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							String empsn = rs.getString("empsn");
							Date signDate = querySignDateWithEMPSN(empsn);
							String limit = queryLimitWithEMPSN(empsn);
							Date quitDay = queryQuitDate(empsn);
							int dayNotSun = getDayNotSUN(
									Integer.parseInt(month),
									Integer.parseInt(year));
							double ducls = rs.getDouble("ducls");
							double restPay = rs.getDouble("rest_pay");
							double ncp = rs.getDouble("nghi_co_phep");
							double ns = rs.getDouble("nghi_san");
							double kc = rs.getDouble("khang_cong");

							double dayOFF = dayNotSun
									- (ducls + restPay + ncp + ns);
							double noSalDay = dayOFF + ns + ncp + kc;

							double uempmInsurance = 0;
							double socialInsurance = 0;
							if (dayOFF < 14) {
								uempmInsurance = queryUempmInsurance(empsn);
								socialInsurance = querySocialInsurance(empsn);
							}
							String lname = "";
							if (rs.getString("lname") != null) {
								lname = rs.getString("lname");
							}
							EmployeePlain aP = new EmployeePlain(empsn, rs
									.getString("fname"), lname, rs
									.getDouble("bonus"), rs
									.getString("id_dept"), rs
									.getString("name_dept_name"), rs
									.getDouble("basic_salary"), rs
									.getDouble("salary"), signDate, limit,
									ducls, restPay, ns, ncp, kc, dayOFF,
									noSalDay, socialInsurance, uempmInsurance,
									quitDay);

							return aP;
						}

					}, new Object[] {});
			// stm = connect.prepareStatement(sql);
			// rs = stm.executeQuery();
			// int i = 1;
			// while (rs.next()) {
			// String empsn = rs.getString("empsn");
			// Date signDate = querySignDateWithEMPSN(empsn);
			// String limit = queryLimitWithEMPSN(empsn);
			// Date quitDay = queryQuitDate(empsn);
			// int dayNotSun = getDayNotSUN(Integer.parseInt(month), Integer
			// .parseInt(year));
			// double ducls = rs.getDouble("ducls");
			// double restPay = rs.getDouble("rest_pay");
			// double ncp = rs.getDouble("nghi_co_phep");
			// double ns = rs.getDouble("nghi_san");
			// double kc = rs.getDouble("khang_cong");
			//
			// double dayOFF = dayNotSun - (ducls + restPay + ncp + ns);
			// double noSalDay = dayOFF + ns + ncp + kc;
			// double uempmInsurance = 0;
			// double socialInsurance = 0;
			// if (dayOFF < 14) {
			// uempmInsurance = queryUempmInsurance(empsn);
			// socialInsurance = querySocialInsurance(empsn);
			// }
			// String lname = "";
			// if (rs.getString("lname") != null) {
			// lname = rs.getString("lname");
			// }
			// EmployeePlain aP = new EmployeePlain(empsn, rs
			// .getString("fname"), lname, rs.getDouble("bonus"), rs
			// .getString("id_dept"), rs.getString("name_dept_name"),
			// rs.getDouble("basic_salary"), rs.getDouble("salary"),
			// signDate, limit, ducls, restPay, ns, ncp, kc, dayOFF,
			// noSalDay, socialInsurance, uempmInsurance, quitDay);
			// result.add(aP);
			// i++;
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// try {
			// if (rs != null)
			// rs.close();
			// if (stm != null)
			// stm.close();
			// if (connect != null)
			// connect.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
		return result;
	}

	public static int getDayNotSUN(int month, int year) {
		int result = 0;
		for (int i = 1; i <= getDateOfMonth(month, year); i++) {
			Calendar day = new GregorianCalendar(year, month - 1, i);
			if (day.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				result++;
			}
		}
		return result;
	}

	public static List<EmployeePlain> queryWithGroupName(final String month,
			final String year, String groupName, String unitName,
			boolean insufficient_active, boolean sufficient_active,
			boolean all_active, boolean taxpayer_active,
			boolean onmatleaver_active) {
		List<EmployeePlain> result = new ArrayList<EmployeePlain>();
		// Connection connect = null;
		// PreparedStatement stm = null;
		// ResultSet rs = null;
		int m = Integer.parseInt(month);
		int y = Integer.parseInt(year);
		try {
			// connect = ConnectionUtil.getConnection();
			String sql = "SELECT DISTINCT e.empsn, e.fname, e.lname,"
					+ " vft.bonus2_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) bonus,"
					+ " dp.id_dept, dp.name_dept_name, "
					+ " vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) basic_salary,"
					+ " vft.comsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'))) salary,"
					+ " dt.ducls, dt.rest_pay,"
					+ " nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'NS'), 0) nghi_san,"
					+ " (nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'DS'), 0)) nghi_co_phep,"
					+ " nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'KC'), 0) khang_cong";
			if (sufficient_active) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_labour l"
						+ " WHERE e.empsn = dt.empsn"

						+ " AND dp.name_group = '"
						+ groupName
						+ "'"
						+ " AND dp.name_dept_name = '"
						+ unitName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND (vft.c_day_notsun(to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')) - (dt.ducls + dt.rest_pay "
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'NS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'DS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'KC'), 0))) < 14"
						+ " AND l.empsn = e.empsn"
						+ " AND l.times = 1"
						+ " AND l.date_s <= to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')"
						+ " AND e.empsn NOT IN ((SELECT ni.empsn FROM vft.n_not_insurance ni WHERE ni.dates < to_date('01/"
						+ month + "/" + year + "','dd/mm/yyyy')))";
			}
			if (insufficient_active) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_labour l"
						+ " WHERE e.empsn = dt.empsn"

						+ " AND dp.name_group = '"
						+ groupName
						+ "'"
						+ " AND dp.name_dept_name = '"
						+ unitName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND (vft.c_day_notsun(to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')) - (dt.ducls + dt.rest_pay "
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'NS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'DS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'KC'), 0))) >= 14"
						+ " AND l.empsn = e.empsn"
						+ " AND l.times = 1"
						+ " AND l.date_s <= to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')"
						+ " AND e.empsn NOT IN ((SELECT ni.empsn FROM vft.n_not_insurance ni WHERE ni.dates < to_date('01/"
						+ month + "/" + year + "','dd/mm/yyyy')))";
			}
			if (all_active) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_group = '" + groupName + "'"
						+ " AND dp.name_dept_name = '" + unitName + "'"
						+ " AND dp.id_dept = e.depsn" + " AND dt.months = '"
						+ month + "'" + " AND dt.years = '" + year + "'";
			}
			if (taxpayer_active) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_emp_quit eq"
						+ " WHERE e.empsn = dt.empsn" + " AND dp.name_group='"
						+ groupName + "'" + " AND dp.name_dept_name='"
						+ unitName + "'" + " AND dp.id_dept = eq.depsn"
						+ " AND dt.months = '" + month + "'"
						+ " AND dt.years = '" + year + "'"
						+ " AND e.empsn = eq.empsn"
						+ " AND eq.thang_trubh = to_date('01/" + month + "/"
						+ year + "', 'dd/mm/yyyy')";
			}
			if (onmatleaver_active) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_group = '"
						+ groupName
						+ "'"
						+ " AND dp.name_dept_name = '"
						+ unitName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND e.empsn IN (SELECT tb.empsn FROM vft.n_time_bear tb"
						+ " WHERE tb.b_dates >= to_date('01/" + month + "/"
						+ year + "', 'dd/mm/yyyy')"
						+ " AND tb.b_dates <= to_date('" + getDateOfMonth(m, y)
						+ "/" + month + "/" + year + "', 'dd/mm/yyyy'))";
			}
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<EmployeePlain>() {

						@Override
						public EmployeePlain mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							String empsn = rs.getString("empsn");
							Date signDate = querySignDateWithEMPSN(empsn);
							String limit = queryLimitWithEMPSN(empsn);
							Date quitDay = queryQuitDate(empsn);
							int dayNotSun = getDayNotSUN(
									Integer.parseInt(month),
									Integer.parseInt(year));
							double ducls = rs.getDouble("ducls");
							double restPay = rs.getDouble("rest_pay");
							double ncp = rs.getDouble("nghi_co_phep");
							double ns = rs.getDouble("nghi_san");
							double kc = rs.getDouble("khang_cong");

							double dayOFF = dayNotSun
									- (ducls + restPay + ncp + ns);
							double noSalDay = dayOFF + ns + ncp + kc;

							double uempmInsurance = 0;
							double socialInsurance = 0;
							if (dayOFF < 14) {
								uempmInsurance = queryUempmInsurance(empsn);
								socialInsurance = querySocialInsurance(empsn);
							}
							String lname = "";
							if (rs.getString("lname") != null) {
								lname = rs.getString("lname");
							}
							EmployeePlain aP = new EmployeePlain(empsn, rs
									.getString("fname"), lname, rs
									.getDouble("bonus"), rs
									.getString("id_dept"), rs
									.getString("name_dept_name"), rs
									.getDouble("basic_salary"), rs
									.getDouble("salary"), signDate, limit,
									ducls, restPay, ns, ncp, kc, dayOFF,
									noSalDay, socialInsurance, uempmInsurance,
									quitDay);

							return aP;
						}

					}, new Object[] {});
			// stm = connect.prepareStatement(sql);
			// rs = stm.executeQuery();
			// int i = 1;
			// while (rs.next()) {
			// String empsn = rs.getString("empsn");
			// Date signDate = querySignDateWithEMPSN(empsn);
			// String limit = queryLimitWithEMPSN(empsn);
			// Date quitDay = queryQuitDate(empsn);
			// int dayNotSun = getDayNotSUN(Integer.parseInt(month), Integer
			// .parseInt(year));
			// double ducls = rs.getDouble("ducls");
			// double restPay = rs.getDouble("rest_pay");
			// double ncp = rs.getDouble("nghi_co_phep");
			// double ns = rs.getDouble("nghi_san");
			// double kc = rs.getDouble("khang_cong");
			//
			// double dayOFF = dayNotSun - (ducls + restPay + ncp + ns);
			// double noSalDay = dayOFF + ns + ncp + kc;
			//
			// double uempmInsurance = 0;
			// double socialInsurance = 0;
			// if (dayOFF < 14) {
			// uempmInsurance = queryUempmInsurance(empsn);
			// socialInsurance = querySocialInsurance(empsn);
			// }
			// String lname = "";
			// if (rs.getString("lname") != null) {
			// lname = rs.getString("lname");
			// }
			// EmployeePlain aP = new EmployeePlain(empsn, rs
			// .getString("fname"), lname, rs.getDouble("bonus"), rs
			// .getString("id_dept"), rs.getString("name_dept_name"),
			// rs.getDouble("basic_salary"), rs.getDouble("salary"),
			// signDate, limit, ducls, restPay, ns, ncp, kc, dayOFF,
			// noSalDay, socialInsurance, uempmInsurance, quitDay);
			// result.add(aP);
			// i++;
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// try {
			// if (rs != null)
			// rs.close();
			// if (stm != null)
			// stm.close();
			// if (connect != null)
			// connect.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
		return result;
	}

	public static List<EmployeePlain> queryWithGroupName(final String month,
			final String year, String groupName, boolean insufficient_active,
			boolean sufficient_active, boolean all_active,
			boolean taxpayer_active, boolean onmatleaver_active) {
		List<EmployeePlain> result = new ArrayList<EmployeePlain>();
		// Connection connect = null;
		// PreparedStatement stm = null;
		// ResultSet rs = null;
		int m = Integer.parseInt(month);
		int y = Integer.parseInt(year);
		try {
			// connect = ConnectionUtil.getConnection();
			String sql = "SELECT DISTINCT e.empsn, e.fname, e.lname,"
					+ " vft.bonus2_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) bonus,"
					+ " dp.id_dept, dp.name_dept_name, "
					+ " vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) basic_salary,"
					+ " vft.comsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'))) salary,"
					+ " dt.ducls, dt.rest_pay,"
					+ " (nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'NS'), 0)"
					+ "+ nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'NS_RO'), 0))"
					+ " nghi_san,"
					+ " (nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'DS'), 0)) nghi_co_phep,"
					+ " nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'KC'), 0) khang_cong";
			if (sufficient_active) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_labour l"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_group = '"
						+ groupName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND (vft.c_day_notsun(to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')) - (dt.ducls + dt.rest_pay "
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'NS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'DS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'KC'), 0))) < 14"
						+ " AND l.empsn = e.empsn"
						+ " AND l.times = 1"
						+ " AND l.date_s <= to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')"
						+ " AND e.empsn NOT IN ((SELECT ni.empsn FROM vft.n_not_insurance ni WHERE ni.dates < to_date('01/"
						+ month + "/" + year + "','dd/mm/yyyy')))";
			}
			if (insufficient_active) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_labour l"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_group = '"
						+ groupName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND (vft.c_day_notsun(to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')) - (dt.ducls + dt.rest_pay "
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'NS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'DS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'KC'), 0))) >= 14"
						+ " AND l.empsn = e.empsn"
						+ " AND l.times = 1"
						+ " AND l.date_s <= to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')"
						+ " AND e.empsn NOT IN ((SELECT ni.empsn FROM vft.n_not_insurance ni WHERE ni.dates < to_date('01/"
						+ month + "/" + year + "','dd/mm/yyyy')))";
			}
			if (all_active) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_group = '" + groupName + "'"
						+ " AND dp.id_dept = e.depsn" + " AND dt.months = '"
						+ month + "'" + " AND dt.years = '" + year + "'";
			}
			if (taxpayer_active) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_emp_quit eq"
						+ " WHERE e.empsn = dt.empsn" + " AND dp.name_group='"
						+ groupName + "'" + " AND dp.id_dept = eq.depsn"
						+ " AND dt.months = '" + month + "'"
						+ " AND dt.years = '" + year + "'"
						+ " AND e.empsn = eq.empsn"
						+ " AND eq.thang_trubh = to_date('01/" + month + "/"
						+ year + "', 'dd/mm/yyyy')";
			}
			if (onmatleaver_active) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_group = '"
						+ groupName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND e.empsn IN (SELECT tb.empsn FROM vft.n_time_bear tb"
						+ " WHERE tb.b_dates >= to_date('01/" + month + "/"
						+ year + "', 'dd/mm/yyyy')"
						+ " AND tb.b_dates <= to_date('" + getDateOfMonth(m, y)
						+ "/" + month + "/" + year + "', 'dd/mm/yyyy'))";
			}
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<EmployeePlain>() {

						@Override
						public EmployeePlain mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							String empsn = rs.getString("empsn");
							Date signDate = querySignDateWithEMPSN(empsn);
							String limit = queryLimitWithEMPSN(empsn);
							Date quitDay = queryQuitDate(empsn);
							int dayNotSun = getDayNotSUN(
									Integer.parseInt(month),
									Integer.parseInt(year));
							double ducls = rs.getDouble("ducls");
							double restPay = rs.getDouble("rest_pay");
							double ncp = rs.getDouble("nghi_co_phep");
							double ns = rs.getDouble("nghi_san");
							double kc = rs.getDouble("khang_cong");

							double dayOFF = dayNotSun
									- (ducls + restPay + ncp + ns);
							double noSalDay = dayOFF + ns + ncp + kc;

							double uempmInsurance = 0;
							double socialInsurance = 0;
							if (dayOFF < 14) {
								uempmInsurance = queryUempmInsurance(empsn);
								socialInsurance = querySocialInsurance(empsn);
							}
							String lname = "";
							if (rs.getString("lname") != null) {
								lname = rs.getString("lname");
							}
							EmployeePlain aP = new EmployeePlain(empsn, rs
									.getString("fname"), lname, rs
									.getDouble("bonus"), rs
									.getString("id_dept"), rs
									.getString("name_dept_name"), rs
									.getDouble("basic_salary"), rs
									.getDouble("salary"), signDate, limit,
									ducls, restPay, ns, ncp, kc, dayOFF,
									noSalDay, socialInsurance, uempmInsurance,
									quitDay);

							return aP;
						}

					}, new Object[] {});
			// stm = connect.prepareStatement(sql);
			// rs = stm.executeQuery();
			// int i = 1;
			// while (rs.next()) {
			// String empsn = rs.getString("empsn");
			// Date signDate = querySignDateWithEMPSN(empsn);
			// String limit = queryLimitWithEMPSN(empsn);
			// Date quitDay = queryQuitDate(empsn);
			// int dayNotSun = getDayNotSUN(Integer.parseInt(month), Integer
			// .parseInt(year));
			// double ducls = rs.getDouble("ducls");
			// double restPay = rs.getDouble("rest_pay");
			// double ncp = rs.getDouble("nghi_co_phep");
			// double ns = rs.getDouble("nghi_san");
			// double kc = rs.getDouble("khang_cong");
			//
			// double dayOFF = dayNotSun - (ducls + restPay + ncp + ns);
			// double noSalDay = dayOFF + ns + ncp + kc;
			//
			// double uempmInsurance = 0;
			// double socialInsurance = 0;
			// if (dayOFF < 14) {
			// uempmInsurance = queryUempmInsurance(empsn);
			// socialInsurance = querySocialInsurance(empsn);
			// }
			// String lname = "";
			// if (rs.getString("lname") != null) {
			// lname = rs.getString("lname");
			// }
			// EmployeePlain aP = new EmployeePlain(empsn, rs
			// .getString("fname"), lname, rs.getDouble("bonus"), rs
			// .getString("id_dept"), rs.getString("name_dept_name"),
			// rs.getDouble("basic_salary"), rs.getDouble("salary"),
			// signDate, limit, ducls, restPay, ns, ncp, kc, dayOFF,
			// noSalDay, socialInsurance, uempmInsurance, quitDay);
			// result.add(aP);
			// i++;
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// try {
			// if (rs != null)
			// rs.close();
			// if (stm != null)
			// stm.close();
			// if (connect != null)
			// connect.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
		return result;
	}

	public static List<EmployeePlain> queryWithUnitName(final String month,
			final String year, String unitName, boolean insufficient_active,
			boolean sufficient_active, boolean all_active,
			boolean taxpayer_active, boolean onmatleaver_active) {
		List<EmployeePlain> result = new ArrayList<EmployeePlain>();
		// Connection connect = null;
		// PreparedStatement stm = null;
		// ResultSet rs = null;
		int m = Integer.parseInt(month);
		int y = Integer.parseInt(year);
		try {
			// connect = ConnectionUtil.getConnection();
			String sql = "SELECT DISTINCT e.empsn, e.fname, e.lname,"
					+ " vft.bonus2_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) bonus,"
					+ " dp.id_dept, dp.name_dept_name, "
					+ " vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy')) basic_salary,"
					+ " vft.comsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),vft.bsaly_by_date(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'))) salary,"
					+ " dt.ducls, dt.rest_pay,"
					+ " (nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'NS'), 0)"
					+ "+ nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'NS_RO'), 0))"
					+ " nghi_san,"
					+ " (nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
					+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'DS'), 0)) nghi_co_phep,"
					+ " nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'), to_date('"
					+ getDateOfMonth(m, y)
					+ "/"
					+ month
					+ "/"
					+ year
					+ "', 'dd/mm/yyyy'),'KC'), 0) khang_cong";
			if (sufficient_active) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_labour l"
						+ " WHERE e.empsn = dt.empsn"

						+ " AND dp.name_dept_name = '"
						+ unitName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND (vft.c_day_notsun(to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')) - (dt.ducls + dt.rest_pay "
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'NS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'DS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'KC'), 0))) < 14"
						+ " AND l.empsn = e.empsn"
						+ " AND l.times = 1"
						+ " AND l.date_s <= to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')"
						+ " AND e.empsn NOT IN ((SELECT ni.empsn FROM vft.n_not_insurance ni WHERE ni.dates < to_date('01/"
						+ month + "/" + year + "','dd/mm/yyyy')))";
			}
			if (insufficient_active) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_labour l"
						+ " WHERE e.empsn = dt.empsn"

						+ " AND dp.name_dept_name = '"
						+ unitName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND (vft.c_day_notsun(to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')) - (dt.ducls + dt.rest_pay "
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'NS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBENH'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'PBAN'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'DS'), 0)"
						+ " + nvl(vft.get_restkind_data_daily(e.empsn, to_date('01/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'), to_date('"
						+ getDateOfMonth(m, y)
						+ "/"
						+ month
						+ "/"
						+ year
						+ "', 'dd/mm/yyyy'),'KC'), 0))) >= 14"
						+ " AND l.empsn = e.empsn"
						+ " AND l.times = 1"
						+ " AND l.date_s <= to_date('01/"
						+ month
						+ "/"
						+ year
						+ "','dd/mm/yyyy')"
						+ " AND e.empsn NOT IN ((SELECT ni.empsn FROM vft.n_not_insurance ni WHERE ni.dates < to_date('01/"
						+ month + "/" + year + "','dd/mm/yyyy')))";
			}
			if (all_active) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_dept_name = '" + unitName + "'"
						+ " AND dp.id_dept = e.depsn" + " AND dt.months = '"
						+ month + "'" + " AND dt.years = '" + year + "'";
			}
			if (taxpayer_active) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp, vft.n_emp_quit eq"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_dept_name='" + unitName + "'"
						+ " AND dp.id_dept = eq.depsn" + " AND dt.months = '"
						+ month + "'" + " AND dt.years = '" + year + "'"
						+ " AND e.empsn = eq.empsn"
						+ " AND eq.thang_trubh = to_date('01/" + month + "/"
						+ year + "', 'dd/mm/yyyy')";
			}
			if (onmatleaver_active) {
				sql = sql
						+ " FROM vft.n_employee e, vft.n_get_data dt, vft.n_department dp"
						+ " WHERE e.empsn = dt.empsn"
						+ " AND dp.name_dept_name = '"
						+ unitName
						+ "'"
						+ " AND dp.id_dept = e.depsn"
						+ " AND dt.months = '"
						+ month
						+ "'"
						+ " AND dt.years = '"
						+ year
						+ "'"
						+ " AND e.empsn IN (SELECT tb.empsn FROM vft.n_time_bear tb"
						+ " WHERE tb.b_dates >= to_date('01/" + month + "/"
						+ year + "', 'dd/mm/yyyy')"
						+ " AND tb.b_dates <= to_date('" + getDateOfMonth(m, y)
						+ "/" + month + "/" + year + "', 'dd/mm/yyyy'))";
			}
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<EmployeePlain>() {

						@Override
						public EmployeePlain mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							String empsn = rs.getString("empsn");
							Date signDate = querySignDateWithEMPSN(empsn);
							String limit = queryLimitWithEMPSN(empsn);
							Date quitDay = queryQuitDate(empsn);
							int dayNotSun = getDayNotSUN(
									Integer.parseInt(month),
									Integer.parseInt(year));
							double ducls = rs.getDouble("ducls");
							double restPay = rs.getDouble("rest_pay");
							double ncp = rs.getDouble("nghi_co_phep");
							double ns = rs.getDouble("nghi_san");
							double kc = rs.getDouble("khang_cong");

							double dayOFF = dayNotSun
									- (ducls + restPay + ncp + ns);
							double noSalDay = dayOFF + ns + ncp + kc;

							double uempmInsurance = 0;
							double socialInsurance = 0;
							if (dayOFF < 14) {
								uempmInsurance = queryUempmInsurance(empsn);
								socialInsurance = querySocialInsurance(empsn);
							}
							String lname = "";
							if (rs.getString("lname") != null) {
								lname = rs.getString("lname");
							}
							EmployeePlain aP = new EmployeePlain(empsn, rs
									.getString("fname"), lname, rs
									.getDouble("bonus"), rs
									.getString("id_dept"), rs
									.getString("name_dept_name"), rs
									.getDouble("basic_salary"), rs
									.getDouble("salary"), signDate, limit,
									ducls, restPay, ns, ncp, kc, dayOFF,
									noSalDay, socialInsurance, uempmInsurance,
									quitDay);

							return aP;
						}

					}, new Object[] {});
			// stm = connect.prepareStatement(sql);
			// rs = stm.executeQuery();
			// int i = 1;
			// while (rs.next()) {
			// String empsn = rs.getString("empsn");
			// Date signDate = querySignDateWithEMPSN(empsn);
			// String limit = queryLimitWithEMPSN(empsn);
			// Date quitDay = queryQuitDate(empsn);
			// int dayNotSun = getDayNotSUN(Integer.parseInt(month), Integer
			// .parseInt(year));
			// double ducls = rs.getDouble("ducls");
			// double restPay = rs.getDouble("rest_pay");
			// double ncp = rs.getDouble("nghi_co_phep");
			// double ns = rs.getDouble("nghi_san");
			// double kc = rs.getDouble("khang_cong");
			//
			// double dayOFF = dayNotSun - (ducls + restPay + ncp + ns);
			// double noSalDay = dayOFF + ns + ncp + kc;
			//
			// double uempmInsurance = 0;
			// double socialInsurance = 0;
			// if (dayOFF < 14) {
			// uempmInsurance = queryUempmInsurance(empsn);
			// socialInsurance = querySocialInsurance(empsn);
			// }
			// String lname = "";
			// if (rs.getString("lname") != null) {
			// lname = rs.getString("lname");
			// }
			// EmployeePlain aP = new EmployeePlain(empsn, rs
			// .getString("fname"), lname, rs.getDouble("bonus"), rs
			// .getString("id_dept"), rs.getString("name_dept_name"),
			// rs.getDouble("basic_salary"), rs.getDouble("salary"),
			// signDate, limit, ducls, restPay, ns, ncp, kc, dayOFF,
			// noSalDay, socialInsurance, uempmInsurance, quitDay);
			// result.add(aP);
			// i++;
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// try {
			// if (rs != null)
			// rs.close();
			// if (stm != null)
			// stm.close();
			// if (connect != null)
			// connect.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
		return result;
	}
}
