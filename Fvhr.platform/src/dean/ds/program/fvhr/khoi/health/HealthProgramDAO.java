package ds.program.fvhr.khoi.health;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.sun.star.lang.NullPointerException;

import ds.program.fvhr.domain.K_N_N_HEALTH_R;
import ds.program.fvhr.ui.insurance.OBJ_UTILITY;
import dsc.echo2app.Application;
import fv.util.JdbcDAO;

public class HealthProgramDAO extends JdbcDAO {

	/**
	 * Tạo bảng lưu dữ liệu lần 1 hoặc lần 2.
	 * 
	 * @param dateCreate
	 * @param user_name
	 * @throws Exception
	 * @author Tuan Khoi
	 */
	@SuppressWarnings("deprecation")
	public static void callCreateTableHealthMonth(Date dateCreate,
			String lanChay) throws Exception {
		Connection con = Application.getApp().getConnection();
		// check bảng đã tồn tại chưa
		String sql3 = "{call K_CREATE_TABLE_HEALTH(?,?)}";

		try {
			CallableStatement stm = con.prepareCall(sql3);
			stm.setString(1, String.valueOf(dateCreate.getMonth()) + "_"
					+ String.valueOf(dateCreate.getYear()));
			stm.setString(2, lanChay);
			stm.execute();

		} catch (Exception e) {
			throw new Exception("Error to method callCreateTableHealthMonth : "
					+ e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new Exception("Close connect failed : " + e.getMessage());
			}
		}
	}

	public boolean checkTableExist(String tablename)
			throws NullPointerException {

		if (!(tablename == null) && !tablename.isEmpty()) {
			// toUpperCase() table_name
			String sql = "select count(*) from user_tables where table_name=? and tablespace_name='VFT'";

			Long count = getJdbcDAO().getSimpleJdbcTemplate().queryForLong(sql,
					new Object[] { tablename.toUpperCase() });
			return count.intValue() == 1;
		} else {
			throw new NullPointerException(
					"Error from checkTableExist: tablename invalid.");
		}
	}

	public boolean createTableFromOtherTable(String tableName,
			String otherTableName, String condition) throws SQLException,
			NullPointerException {
		// code kì kì
		if (!checkTableExist(otherTableName)) {
			String sql = "create table " + otherTableName
					+ " as select * from " + tableName + "	 where " + condition;
			try {
				getJdbcDAO().getJdbcTemplate().execute(sql);
				return true;
			} catch (Exception e) {
				throw new SQLException(
						"Error to method createTableFromOtherTable class HealthProgramDAO : "
								+ e.getMessage());
			}
		}
		return false;
	}

	public static boolean isCheckStatusUpdateOfFactory(String nameFact,
			String month, String year) {

		String sql = "select T.NAME_FACT \n"
				+ " from k_n_n_health_r_status t\n" + " WHERE T.NAME_FACT = '"
				+ nameFact + "'" + " AND TO_CHAR(T.MONTH_REPORT,'MM/YYYY') = '"
				+ month + "/" + year + "'" + " AND T.STATUS = 1 ";

		return ((List<String>) new ds.program.fvhr.util.OBJ_UTILITY()
				.Exe_Sql_String(sql)).size() > 0 ? true : false;
	}

	/**
	 * @param empsn
	 *            mã nhân viên.
	 * @param month_year
	 *            tháng xét ngày công.
	 * @return số ngày công có lương.
	 * @throws NullPointerException
	 *             if empsn or month_year are null.
	 * @throws SQLException
	 *             if query invalid.
	 * @author Tuan Khoi.
	 */
	public static int get_NGAYCONGCOLUONG(String empsn, String month_year)
			throws NullPointerException, SQLException {

		if (empsn == null || empsn.trim() == "")
			throw new NullPointerException(empsn + " is null.");
		if (month_year == null || month_year.trim() == "")
			throw new NullPointerException(month_year + " is null.");

		String sql = "select nvl(sum(g.ducls + g.nucls + "
				+ "(case when  mod(g.oth,8)=0     then  g.oth/8    "
				+ "when  mod(g.oth,10.4)=0  then  g.oth/(10.4) else 0 end) ),0) "
				+ "\nFrom n_Data_daily g \nWhere g.empsn='" + empsn + "' "
				+ "and to_char(g.dates,'mm/yyyy')='" + month_year + "'";

		try {
			List<String> result = new ArrayList<String>();
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<String>() {

						@Override
						public String mapRow(ResultSet rs, int arg1)
								throws SQLException {
							return rs.getString(1);
						}

					}, new Object[] {});
			return Integer.parseInt(result.get(0).toString());
		} catch (Exception e) {
			throw new SQLException("Error to method get_NGAYCONGCOLUONG : "
					+ empsn + "\n" + e.getMessage());
		}

	}

	/**
	 * Lấy ngày công có lương trong khoảng từ todate đến fromdate.
	 * 
	 * @param empsn
	 * @param todate
	 * @param fromdate
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("deprecation")
	public static float CountWorkday(String empsn, Date todate, Date fromdate)
			throws SQLException {

		String sql = "";
		String toDate = todate.getDate() + "/" + (todate.getMonth() + 1) + "/"
				+ (todate.getYear() + 1900);
		String fromDate = fromdate.getDate() + "/" + (fromdate.getMonth() + 1)
				+ "/" + (fromdate.getYear() + 1900);

		sql = "select " + " (select nvl(sum(g.ducls+g.nucls+ "
				+ " (case when  mod(g.oth,8)=0     then  g.oth/8 "
				+ "		 when  mod(g.oth,10.4)=0  then  g.oth/(10.4) "
				+ "       else 0 " + " end) ),0) " + " From n_Data_daily g "
				+ " Where g.empsn=a.empsn and g.dates >= to_date('"
				+ toDate
				+ "', 'dd/mm/yyyy')"
				+ "	and g.dates <= to_date('"
				+ fromDate
				+ "','dd/mm/yyyy') ) +"
				+ " (select nvl(sum(g.rest_qtt),0) "
				+ " From n_Data_daily g , n_rest_kind aa "
				+ " Where g.empsn=a.empsn "
				+ " and g.rest_rs=aa.name_rest "
				+ " and aa.id_rest_sal='C01' "
				+ " and g.dates >= to_date('"
				+ toDate
				+ "', 'dd/mm/yyyy') and g.dates <= to_date('"
				+ fromDate
				+ "','dd/mm/yyyy'))"

				+ " From n_employee a "
				+ " Where a.empsn = "
				+ "'"
				+ empsn
				+ "'"
				+ " and  a.empsn not in "
				+ " (select b.empsn "
				+ " from n_emp_quit b "
				+ " where b.real_off_date<to_date('"
				+ toDate + "','dd/mm/yyyy') " + " AND b.DATE_AGAIN IS NULL) ";
		try {
			List<String> result = new ArrayList<String>();
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<String>() {

						@Override
						public String mapRow(ResultSet rs, int arg1)
								throws SQLException {
							return rs.getString(1);
						}

					}, new Object[] {});
			return Float.parseFloat(result.get(0).toString());
		} catch (Exception e) {
			System.out.println("Error " + sql);
			throw new SQLException("Error to method CountWorkday : " + empsn
					+ "\n" + e.getMessage());
		}

	}

	/**
	 * @param my
	 * @param dmy_01
	 * @return
	 * @throws SQLException
	 * 
	 */
	public List<String> get_NEW_EMP_STATUS_1(String my, String dmy_01)
			throws SQLException {

		String sql = "select t.empsn from k_n_n_health_r t where t.status=1"
				+ "\n "
				+ " and (to_char(t.lock_date,'MM/yyyy') <> '"// <>
				+ my
				+ "'"
				+ "                                    or t.lock_date is null)"
				+

				" AND T.EMPSN NOT IN (SELECT S.EMPSN FROM N_NOT_INSURANCE S\n"
				+ "                                           WHERE TO_DATE('01/'||TO_CHAR(S.DATES,'MM/YYYY'),'DD/MM/YYYY')\n"
				+ "                         <= TO_DATE('" + dmy_01
				+ "','DD/MM/YYYY')) ";

		List<String> result = new ArrayList<String>();
		try {

			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<String>() {

						@Override
						public String mapRow(ResultSet arg0, int arg1)
								throws SQLException {
							return arg0.getString("empsn");
						}
					}, new Object[] {});
			return result;
		} catch (Exception e) {
			throw new SQLException("Error to method get_NEW_EMP_STATUS_1 : "
					+ e.getMessage());
		}
	}

	// moi them
	// public void getEmployeeSubInsurance() {
	// String sql_empl_working_in_month = "";
	// String sql_empl_bear_in_month = "";
	// String sql_new_empl = "";
	//
	// }

	// public static Map<String, String> queryUnitByDeptID(String deptID) {
	// Map<String, String> result = new HashMap<String, String>();
	//
	// try {
	// String sql =
	// "SELECT dptm.name_fact, dptm.name_group, dptm.name_dept_name"
	// + " FROM vft.n_department dptm" + " WHERE dptm.id_dept = ?";
	// Map<String, Object> rs = getJdbcDAO().getSimpleJdbcTemplate()
	// .queryForMap(sql, new Object[] { deptID });
	//
	// result.put("name_fact",
	// Vni2Uni.convertToUnicode((String) rs.get("name_fact")));
	// result.put("name_group",
	// Vni2Uni.convertToUnicode((String) rs.get("name_group")));
	// result.put("name_dept_name",
	// Vni2Uni.convertToUnicode((String) rs.get("name_dept_name")));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return result;
	// }

	// public static Map<String, Object> getEmp(String my, String dmy_01)
	// throws SQLException {
	//
	// String sql = "select t.* from k_n_n_health_r t where t.status=1"
	// + "\n "
	// + " and (to_char(t.lock_date,'MM/yyyy') <> '"// <>
	// + my
	// + "'"
	// + "                                    or t.lock_date is null)"
	// +
	//
	// " AND T.EMPSN NOT IN (SELECT S.EMPSN FROM N_NOT_INSURANCE S\n"
	// +
	// "                                           WHERE TO_DATE('01/'||TO_CHAR(S.DATES,'MM/YYYY'),'DD/MM/YYYY')\n"
	// + "                         <= TO_DATE('" + dmy_01
	// + "','DD/MM/YYYY'))";
	//
	// try {
	// List<Map<String, Object>> map = getJdbcDAO()
	// .getSimpleJdbcTemplate().queryForMap(sql);
	// Map<String, Object> tbl = map.get(0);
	// return tbl;
	// } catch (Exception e) {
	// throw new SQLException(e.getMessage());
	// }
	// }

	public List<K_N_N_HEALTH_R> getEmployeeHealthLan2(String monthYear,
			String namefact) throws SQLException {

		List<K_N_N_HEALTH_R> result = null;

		if (namefact != null && !namefact.isEmpty()) {
			String sql = "select t.empsn, t.num, t.num_used, t.salary_b,"
					+ " 	t.commit, t.note, t.lanchay, t.depsn, t.lock_date, t.debt, t.status "
					+ "	from k_n_n_health_r t,  n_department n  "
					+ "	where (t.commit = -1 or t.commit=0) and n.name_fact='"
					+ namefact + "'";
			
			System.out.println(sql);
			try {

				result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
						new ParameterizedRowMapper<K_N_N_HEALTH_R>() {
							@Override
							public K_N_N_HEALTH_R mapRow(ResultSet arg0,
									int arg1) throws SQLException {

								K_N_N_HEALTH_R rowHealth;
								rowHealth = new K_N_N_HEALTH_R();
								rowHealth.setEMPSN(arg0.getString("EMPSN"));
								rowHealth.setNUM(arg0.getLong("NUM"));
								rowHealth.setSALARY_B(arg0.getLong("SALARY_B"));
								rowHealth.setCOMMIT(arg0.getLong("COMMIT"));
								rowHealth.setNOTE(arg0.getString("NOTE"));
								rowHealth.setLANCHAY(arg0.getLong("LANCHAY"));
								rowHealth.setDEPSN(arg0.getString("DEPSN"));
								rowHealth.setLOCK_DATE(arg0
										.getDate("LOCK_DATE"));
								rowHealth.setDEBT(arg0.getLong("DEBT"));
								rowHealth.setSTATUS(arg0.getLong("STATUS"));
								return rowHealth;
							}
						}, new Object[] {});
			} catch (Exception e) {
				throw new SQLException(e.getMessage());
			}
		}
		return result;

	}

	public List<String> get_Empsn_Lan2(String my) throws SQLException {

		String sql = "select t.empsn from k_n_n_health_r t where t.commit = -1 and t.status= 1"
				+ "\n "
				+ " and (to_char(t.lock_date,'MM/yyyy') = '"// <>
				+ my
				+ "'"
				+ "                                    or t.lock_date is null)";

		// " in ( '11060510',"
		// + " '10070557', '04120526', '11020495', "
		// + "'08091407', '09051238', '97110150', '05100713')";
		// and t.empsn in ('13080024','13080025','13080027')";

		List<String> result = new ArrayList<String>();
		try {

			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<String>() {

						@Override
						public String mapRow(ResultSet arg0, int arg1)
								throws SQLException {
							return arg0.getString("empsn");
						}
					}, new Object[] {});
			return result;
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}

	}

	public Timestamp GetDateTimeOracle() throws ParseException {
		String sql = "SELECT TO_CHAR(SYSDATE,'DD/MM/YYYY HH24:MI:SS') FROM DUAL";
		Object obj = new OBJ_UTILITY().Exe_Sql_Obj(sql);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			Date d = sdf.parse(obj.toString());
			return new java.sql.Timestamp(d.getTime());
		} catch (Exception e) {
			throw new ParseException("Error to method GetDateTimeOracle : "
					+ e.getMessage(), 0);
		}
	}

	public Object[] getAllNameFactOfManageUser(String username) {
		List<String> result = new ArrayList<String>();
		try {
			String sql = "SELECT DISTINCT dp.name_fact "
					+ " FROM vft.n_department dp, vft.dspb02 ds, vft.n_user_limit ul, vft.n_quanly ql"
					+ " WHERE ds.pb_userid = ? "
					+ " AND ds.pb_userno = ul.ma_user"
					+ " AND ul.ma_ql = ql.maql"
					+ " AND ql.name_fact = dp.name_fact and  dp.name_fact not in ('NV','FVJ')";
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<String>() {
						@Override
						public String mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return rs.getString("name_fact");
						}

					}, new Object[] { username.toUpperCase() });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toArray();
	}

	public Object[] getAllNameFact() {
		List<String> result = new ArrayList<String>();
		String sql = "select distinct dp.name_fact " + "from n_department dp"
				+ " where dp.name_fact not in('NV','FVJ')";
		try {
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<String>() {

						@Override
						public String mapRow(ResultSet arg0, int arg1)
								throws SQLException {
							return arg0.getString("name_fact");
						}

					}, new Object[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toArray();
	}

	/**
	 * Kiểm tra quyền user User lấy từ trong bảng dspb01_limit.
	 * 
	 * @param userName
	 * @param nameFact
	 *            id xưởng
	 * @return true nếu có quyền thao tác trên bảng N_HEALTH_R.
	 * @author Tuan Khoi
	 * @throws SQLException
	 */
	public boolean isCheckLimitUser(String userName, String nameFact)
			throws SQLException {
		String sql = "select t.userid from dspb01_limit t\n"
				+ " where t.userid = '" + userName + "'" + " and t.limit = '"
				+ nameFact + "'" + " AND T.PB_ID = 'N_HEALTH_R'";
		List<String> result = new ArrayList<String>();

		try {
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<String>() {

						@Override
						public String mapRow(ResultSet rs, int arg1)
								throws SQLException {
							return rs.getString("userid");
						}

					}, new Object[] {});
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}

		return result.size() > 0;
	}

	/**
	 * Kiểm tra quyền user User lấy từ trong bảng dspb01_limit.
	 * 
	 * @param userName
	 * @param nameFact
	 *            id xưởng
	 * @return true nếu có quyền thao tác trên bảng N_HEALTH_R.
	 * @author Tuan Khoi
	 * @throws SQLException
	 */
	public boolean checkLimitExportAll(String userName, String nameFact)
			throws SQLException {

		String sql = "select t.userid from dspb01_limit t\n"
				+ " where t.userid = '" + userName + "'" + " and t.limit = '"
				+ nameFact + "'" + " AND T.PB_ID = 'N_HEALTH_R'";
		List<String> result = new ArrayList<String>();

		try {
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<String>() {

						@Override
						public String mapRow(ResultSet rs, int arg1)
								throws SQLException {
							return rs.getString("userid");
						}

					}, new Object[] {});
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}

		return result.size() > 0;
	}

	/**
	 * Kiểm tra hiện trạng cập nhật dữ liệu của xưởng.
	 * 
	 * @param nameFact
	 * @param month
	 * @param year
	 * @param lanChay
	 *            lần chạy 1 hoặc 2
	 * @return true nếu xưởng đó đã cập nhật dữ liệu.
	 * @author Tuan Khoi
	 */
	public boolean isCheckStatusUpdateOfFactory(String nameFact,
			String mothYear, int lanChay) {
		String sql = "select T.NAME_FACT \n"
				+ " from k_n_n_health_r_status t\n" + " WHERE T.NAME_FACT = '"
				+ nameFact + "'" + " AND TO_CHAR(T.MONTH_REPORT,'MM/YYYY') = '"
				+ mothYear + "'" + " AND T.STATUS = 1 AND LANCHAY=" + lanChay;
		return ((List<String>) new ds.program.fvhr.util.OBJ_UTILITY()
				.Exe_Sql_String(sql)).size() > 0 ? true : false;
	}

	public int UpdateStatusOfFactory(String nameFact, String date,
			String username, int lanChay) {
		String sql = "INSERT INTO k_n_n_Health_r_Status(Name_Fact,Month_Report,Status,Act_User,Lanchay) "
				+ " VALUES('"
				+ nameFact
				+ "',to_date('"
				+ date
				+ "','dd/mm/yyyy'),1,'" + username + "'," + lanChay + ")";

		return getJdbcDAO().getJdbcTemplate().update(sql);

	}

	/**
	 * Kiểm tra người xuất báo cáo tổng thể đã xuất ký trình chưa.
	 * 
	 * @param name_fact
	 *            xưởng.
	 * @param month
	 *            /year xuất báo cáo.
	 * @param user_name
	 *            xuất báo cáo.
	 * @throws Exception
	 *             call K_N_CHECK_HEALTH_EXPORT failed.
	 * @author Tuan Khoi.
	 */
	public void exeProcedureCHECK_HEALTH_EXPORT(String name_fact, int month,
			int year, String user_name) throws Exception {
		Connection con = Application.getApp().getConnection();
		String sql3 = "{call K_N_CHECK_HEALTH_EXPORT(?,?,?,?)}";

		try {
			CallableStatement stm = con.prepareCall(sql3);
			stm.setString(1, name_fact);
			stm.setString(2, String.valueOf(month));
			stm.setString(3, String.valueOf(year));
			stm.setString(4, user_name);

			stm.execute();

		} catch (Exception e) {
			throw new Exception(
					"Error to method  exeProcedureCHECK_HEALTH_EXPORT detail erorr : "
							+ e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new Exception(e.getMessage());
			}
		}
	}

	public void exeProcedureK_N_CHECK_HEALTH(String name_fact, int month,
			int year, String user_name, int lanchay) throws Exception {

		Connection con = Application.getApp().getConnection();
		String sql = "{call K_N_CHECK_HEALTH(?,?,?,?,?)}";

		try {
			CallableStatement stm = con.prepareCall(sql);
			stm.setString(1, name_fact);
			stm.setString(2, String.valueOf(month));
			stm.setString(3, String.valueOf(year));
			stm.setString(4, user_name);
			stm.getInt(lanchay);
			stm.execute();
			stm.close();

		} catch (Exception e1) {
			System.out.println(e1);
		} finally {
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Hiện trạng đã cập nhật đã chạy dữ liệu chưa.
	 * 
	 * @param month
	 *            tháng cần kiểm tra
	 * @param year
	 *            năm cần kiểm tra
	 * @return count record.
	 */
	public int getAllStatusOfFact(int month, int year, int lanchay) {
		String sql = "select distinct T.NAME_FACT \n"
				+ "from k_n_n_health_r_status t\n"
				+ "WHERE TO_CHAR(T.MONTH_REPORT,'MM/YYYY') = '"
				+ month
				+ "/"
				+ year
				+ "'"
				+ " AND T.STATUS = 1 "
				+ " AND T.LANCHAY="
				+ lanchay
				+ " AND (T.NAME_FACT = 'TB' OR T.NAME_FACT = 'FVL' OR T.NAME_FACT = 'FVLS' "
				+ " OR T.NAME_FACT = 'KDAO')";
		List<String> result = new ArrayList<String>();

		result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
				new ParameterizedRowMapper<String>() {

					@Override
					public String mapRow(ResultSet arg0, int arg1)
							throws SQLException {
						return arg0.getString("name_fact");
					}

				}, new Object[] {});
		return result.size();
	}

	/**
	 * Detail: cập nhật những nhân viên tăng lại 1 tây trong tháng và 20 tây
	 * tháng trước.
	 * 
	 * @param date_str_01_
	 * @param date_str_pre_15_
	 * @param month_
	 * @param year_
	 * @return
	 * @throws SQLException
	 */
	public static List<String> get_NEW_EMP(String date_str_01_,
			String date_str_pre_15_, String month_, String year_)
			throws SQLException {
		String sql = " SELECT t.empsn\n" +

		"FROM n_labour t" +

		" where (t.date_s = to_date('" + date_str_01_ + "','dd/mm/yyyy')\n"
				+ "			or t.date_s = to_date('" + date_str_pre_15_
				+ "','dd/mm/yyyy'))\n" + " AND t.times = 1" +

				"\n" + " union\n" + "\n" + "select t.empsn\n"
				+ "from n_newworker_test t,n_employee e,n_labour lb\n"
				+ "where t.empsn = e.empsn\n" + "and e.empsn   = lb.empsn\n"
				+ "and t.dd_khu  = '1'\n" + "and lb.clock  = '1'\n"
				+ "and to_char(e.ngaynx_moi,'mm/yyyy') = '" + month_ + "/"
				+ year_ + "'" +

				"";

		List<String> result = new ArrayList<String>();
		try {
			result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
					new ParameterizedRowMapper<String>() {

						@Override
						public String mapRow(ResultSet arg0, int arg1)
								throws SQLException {
							return arg0.getString("empsn");
						}
					}, new Object[] {});
			return result;
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}

	}

	/**
	 * List nhân viên tăng lại 1 tây trong tháng và 20 tây tháng trước. ( dùng
	 * HashTable)
	 * 
	 * @param date_str_01_
	 *            ngày đầu tháng đang xét.
	 * @return List employee.
	 * @author Tuan Khoi.
	 */
	public static List<String> getN_EMP_QUIT(String date_str_01_) {

		List<String> result = new ArrayList<String>();
		String sql_emp_quit = "";
		// tăng lại trong tháng
		sql_emp_quit = "SELECT DISTINCT T.EMPSN FROM N_EMP_QUIT T\n"
				+ "                   WHERE T.THANG_TANGLAI IS NOT NULL\n"
				+ "                   AND TO_DATE('01/'||TO_CHAR(T.THANG_TANGLAI,'MM/YYYY'),'DD/MM/YYYY')\n"
				+ "                            = TO_DATE('" + date_str_01_
				+ "','DD/MM/YYYY')\n"
				+ " AND (T.TYLE_TANGLAI = 1 OR T.TYLE_TANGLAI = 20) ";

		result = getJdbcDAO().getSimpleJdbcTemplate().query(sql_emp_quit,
				new ParameterizedRowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString("empsn");
					}

				}, new Object[] {});

		return result;

	}

	public static List<String> getN_EMP_N_HEALTH(String month_year,
			String first_day_of_year) {

		String sql = "select t.empsn from K_N_N_HEALTH_R t where t.status=1"
				+ "\n "
				+

				" and (to_char(t.lock_date,'MM/yyyy') <> '"
				+ month_year
				+ "'"
				+ "                                    or t.lock_date is null)"
				+

				" AND T.EMPSN NOT IN (SELECT S.EMPSN FROM N_NOT_INSURANCE S\n"
				+ "                                           WHERE TO_DATE('01/'||TO_CHAR(S.DATES,'MM/YYYY'),'DD/MM/YYYY')\n"
				+ "                         <= TO_DATE('" + first_day_of_year
				+ "','DD/MM/YYYY'))" +

				"\n";
		List<String> result = new ArrayList<String>();
		result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
				new ParameterizedRowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString("empsn");
					}
				}, new Object[] {});

		return result;

	}

	private static JdbcDAO getJdbcDAO() {
		return new JdbcDAO();
	}

	public static int InsertN_N_HEALTH_R(K_N_N_HEALTH_R health_R,
			String thangTangMoi) throws SQLException {
		try {
			String sql = " INSERT INTO k_n_n_health_r(EMPSN,NUM,SALARY_B,NUM_USED,STATUS,THANG_TANGMOI,STATUS_TT_TMOI, LANCHAY)\n"
					+ " VALUES('"
					// mã nhân viên
					+ health_R.getEMPSN()
					+ "',"
					// số tháng đã đóng tiền bảo hiểm
					+ health_R.getNUM()
					+ ","
					+ health_R.getSALARY_B()
					+ ","
					// num_used số quý tham gia bảo hiểm.
					+ health_R.getNUM_USED()
					+ ","
					// 1 nhận việc trả thẻ tăng mới trong tháng.
					+ health_R.getSTATUS()
					// date_str_01 tháng báo tăng mới.
					+ ",TO_DATE('"
					+ thangTangMoi
					+ "','DD/MM/YYYY'),1,"
					+ health_R.getLANCHAY() + ")";
			return getJdbcDAO().getJdbcTemplate().update(sql);
		} catch (Exception e) {
			throw new SQLException(e.toString());
		}
	}

	// xét xem nhân viên đang xét có thuộc xưởng đang xuất ký trình
	// DUNG
	// CHO
	// KY
	// TRINH
	// BHYT

	public boolean thuoc_xuong(String madv, String name_fact, String id_dept) {
		String sql = "select count(*) from n_department d"
				+ " where d.name_fact='" + name_fact + "' and d.id_dept='"
				+ madv + "'";

		List<String> result = new ArrayList<String>();

		result = getJdbcDAO().getSimpleJdbcTemplate().query(sql,
				new ParameterizedRowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString("depsn");
					}

				}, new Object[] {});
		return result.size() > 0 ? true : false;
	}

	// Nhan su chay BHYT vi lay theo bang n_n_health_r da loai bo nghi viec tai
	// day rui
	// lấy đơn vị
	public static String Get_depsn(String empsn, Date date_input) {

		String date_iput_str = date_input.getDay() + "/"
				+ date_input.getMonth() + "/" + date_input.getYear();
		String sql_dv = "select lay_donvi_theo_ngay_ngan('" + empsn + "','"
				+ date_iput_str + "') as depsn from dual";

		List<String> result = new ArrayList<String>();

		result = getJdbcDAO().getSimpleJdbcTemplate().query(sql_dv,
				new ParameterizedRowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString("depsn");
					}

				}, new Object[] {});

		return result.size() > 0 ? result.get(0) : "";
	}

	public boolean checkUserAccountant(String user_name) {

		if (user_name.trim() != null && !user_name.isEmpty()) {
			String sql = " select count(n.idgroup) from n_users_list l, n_limits n "
					+ "where l.user_name=? and l.id_limit=n.id_limit  and n.idgroup='KT'";
			return getJdbcDAO().getSimpleJdbcTemplate().queryForInt(sql,
					user_name) > 0;
		}
		return false;
	}

	/**
	 * ký trình bảo hiểm mới
	 * 
	 * @param empsn
	 * @param date_input
	 * @return salary lần 1
	 * @throws SQLException
	 */
	public Long Get_Salary_Total_By_Date_Lan_1(String empsn, Date date_input)
			throws SQLException {

		long rs = 0;
		String str_date = new SimpleDateFormat("dd/mm/yyyy").format(date_input);

		String month = str_date.substring(3, 5);
		String year = str_date.substring(6, 10);
		Connection con = Application.getApp().getConnection();

		String sql = "{call C5_CALCULATE_SALARY_N_YT_1(?,?,?,?)}";

		try {
			CallableStatement stm = con.prepareCall(sql);
			stm.setString(1, month);
			stm.setString(2, year);
			stm.setString(3, empsn);

			stm.registerOutParameter(4, Types.INTEGER);

			stm.execute();
			rs = Long.valueOf(stm.getInt(4));

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}

}