package ds.program.fvhr.minh.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import ds.program.fvhr.domain.N_CT_BHXH_TN;
import ds.program.fvhr.domain.N_EMP_QUIT;
import ds.program.fvhr.minh.domain.EmployeeBear;
import ds.program.fvhr.minh.domain.EmployeePlain;
import ds.program.fvhr.util.OBJ_UTILITY;
import fv.util.ApplicationHelper;
import fv.util.DbUtils;
import fv.util.JdbcDAO;

public class InsurDAO extends JdbcDAO {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	public boolean checkQLyFact(String name_fact) {
		boolean kq = true;
		String userIDOld = ApplicationHelper.getVftUserId();
		String sql = "SELECT COUNT(E.EMPSN) FROM N_EMPLOYEE E, N_USER_LIMIT U " + "WHERE  e.EMPSN=E.EMPSN "
				+ "AND E.USER_MANAGE_ID=U.MA_QL  AND U.MA_USER='" + userIDOld + "' "
				+ "AND e.depsn in (select d.id_dept from n_department d where e.depsn = d.id_dept and name_fact= '"
				+ name_fact + "') ";

		int dem = 0;
		try {
			dem = (Integer) getJdbcTemplate().queryForObject(sql, Integer.class);

		} catch (Exception e) {
			dem = 0;
		}

		if (dem > 0)
			kq = true;
		else
			kq = false;

		return kq;

	}

	public boolean checkQLyGroup(String name_fact, String name_group) {
		boolean kq = true;
		String userIDOld = ApplicationHelper.getVftUserId();
		String sql = "SELECT count(E.EMPSN) FROM N_EMPLOYEE E, N_USER_LIMIT U "
				+ "WHERE e.EMPSN=E.EMPSN AND E.USER_MANAGE_ID=U.MA_QL AND U.MA_USER='" + userIDOld + "' "
				+ " AND e.depsn in (select d.id_dept from n_department d where e.depsn = d.id_dept and "
				+ " name_fact= '" + name_fact + "' " + "and name_group = '" + name_group + "') ";

		int dem = 0;
		try {
			dem = (Integer) getJdbcTemplate().queryForObject(sql, Integer.class);

		} catch (Exception e) {
			dem = 0;
		}

		if (dem > 0)
			kq = true;
		else
			kq = false;

		return kq;

	}

	public boolean checkQLyDept(String name_fact, String name_group, String name_dept) {
		boolean kq = true;
		String userIDOld = ApplicationHelper.getVftUserId();
		String sql = "SELECT count(E.EMPSN) FROM N_EMPLOYEE E, N_USER_LIMIT U "
				+ "WHERE e.EMPSN=E.EMPSN AND E.USER_MANAGE_ID=U.MA_QL AND U.MA_USER='" + userIDOld + "' "
				+ " AND e.depsn in (select d.id_dept from n_department d where e.depsn = d.id_dept and "
				+ "name_fact= '" + name_fact + "' " + "and name_group = '" + name_group + "' "
				+ "and name_dept_name = '" + name_dept + "') ";
		int dem = 0;
		try {
			dem = (Integer) getJdbcTemplate().queryForObject(sql, Integer.class);

		} catch (Exception e) {
			dem = 0;
		}

		if (dem > 0)
			kq = true;
		else
			kq = false;

		return kq;

	}

	public boolean checkQLyDept(String depsn) {
		boolean kq = true;
		String userIDOld = ApplicationHelper.getVftUserId();
		String listVungQL = DbUtils.parseInStringParamValues(ApplicationHelper.getRightList());
		String sql = "SELECT count(E.EMPSN) FROM N_EMPLOYEE E, N_USER_LIMIT U \n"
				+ "WHERE e.EMPSN=E.EMPSN AND E.USER_MANAGE_ID=U.MA_QL AND U.MA_USER='" + userIDOld + "'\n"
				+ " and e.depsn='" + depsn + "' and e.USER_MANAGE_ID in " + listVungQL;
		int dem = 0;
		try {
			dem = (Integer) getJdbcTemplate().queryForObject(sql, Integer.class);

		} catch (Exception e) {
			dem = 0;
		}

		if (dem > 0)
			kq = true;
		else
			kq = false;

		return kq;

	}

	public boolean checkQLyNDept(String fact, String group, String deptName) {
		String userIDOld = ApplicationHelper.getVftUserId();
		String sql1 = "SELECT sum(count(E.EMPSN)) FROM N_EMPLOYEE E, N_USER_LIMIT U ,N_DEPARTMENT d "
				+ "WHERE E.USER_MANAGE_ID=U.MA_QL AND U.MA_USER='" + userIDOld + "' and e.depsn=d.id_dept ";
		if (fact != null)
			sql1 += " and NAME_FACT='" + fact + "'";
		if (group != null)
			sql1 += " and NAME_GROUP='" + group + "'";
		if (deptName != null)
			sql1 += " and NAME_DEPT_NAME='" + deptName + "'";

		sql1 += " and e.USER_MANAGE_ID not in ('FVA','FVN','GIA','TES')";
		sql1 += " group by e.USER_MANAGE_ID";
		String sql2 = "";

		OBJ_UTILITY obj = new OBJ_UTILITY();
		try {
			List<String> listData1 = obj.Exe_Sql_String(sql1);
			if (listData1.size() <= 0)
				return false;
			sql2 = "SELECT sum(count(E.EMPSN)) FROM N_EMPLOYEE E,N_DEPARTMENT d WHERE e.depsn=d.id_dept ";
			if (fact != null) {
				if (fact.equals("FVL") && group == null)// Nhan su FVL ko quan CBB <can bo cap cao VietNam> nen loai ra
														// khi xet quyen
					sql2 += " and e.USER_MANAGE_ID <> 'CBB'";// chi ap dung dc cho xet quyen toan bo thoi,
				// can xem lai khi ranh roi, ma chac chang ranh roi dau, hazzzzzzzzzzzzz
				sql2 += " and NAME_FACT='" + fact + "'";
			}
			// loai bo nhung ma quan ly ma nhan su tong bo ko quan ly nhu test, ko con ton
			// tai nua....
			sql2 += " and e.USER_MANAGE_ID not in ('FVA','FVN','GIA','TES')";
			if (group != null)
				sql2 += " and NAME_GROUP='" + group + "'";
			if (deptName != null)
				sql2 += " and NAME_DEPT_NAME='" + deptName + "'";
			sql2 += " group by e.USER_MANAGE_ID";
			List<String> listData2 = obj.Exe_Sql_String(sql2);
			if (Integer.valueOf(listData1.get(0)) >= Integer.valueOf(listData2.get(0))) {
				return true;
			}

		} catch (Exception e) {
			return false;
		}

		return false;
	}

	public void insertListAll01(List<EmployeePlain> list, final String nam, final String thang, final String userName) {

		Date d = new Date(System.currentTimeMillis());
		for (EmployeePlain bc : list) {
			final EmployeePlain b = bc;
			List<Object> s = new ArrayList<Object>();
			getJdbcTemplate().call(new CallableStatementCreator() {
				@Override
				public CallableStatement createCallableStatement(Connection con) throws SQLException {
					CallableStatement cs = con.prepareCall("{ call a_n_social_infor_report_i_ro1(?,?,?,?,?,?,?) }");
					cs.setString(1, b.getEmpsn());
					cs.setDate(2, new java.sql.Date(Integer.valueOf(nam) - 1900, Integer.valueOf(thang) - 1, 01));
					cs.setFloat(3, (float) b.getComSalary());
					cs.setString(4, "");
					cs.setString(5, userName);
					cs.setDate(6, new java.sql.Date(System.currentTimeMillis()));
					cs.setString(7, "n_social_infor_report_1");
					return cs;
				}
			}, s);
			if (b.getStatus() == 1) {
				if (getJdbcTemplate().queryForInt(
						"select count(empsn) from n_ct_bhxh_tn_1 where id_bhxhtn =? and empsn = ?",
						new Object[] { nam + thang, b.getEmpsn() }) == 0)
					getJdbcTemplate().update("insert into n_ct_bhxh_tn_1 values(?,?,?,?,?,?,?)",
							new Object[] { nam + thang, b.getDucls(), b.getBasicSalary(), b.getComSalary(),
									b.getEmpsn(), sdf.format(d) + " INSERT BY " + userName, b.getDeptID() });
				else
					// neu khac moi cap nhat
					getJdbcTemplate().update("update n_ct_bhxh_tn_1 set ngaycong = ?, luongcb = ?, luonghd = ?, "
							+ "note = ?||' NC:'||ngaycong||' Luong:'||luonghd, depsn = ? where id_bhxhtn =? and empsn = ? and (ngaycong <> ?"
							+ "or luongcb <> ? or luonghd <> ? or depsn <> ?) ",
							new Object[] { b.getDucls(), b.getBasicSalary(), b.getComSalary(),
									sdf.format(d) + " UPDATE BY " + userName, b.getDeptID(), nam + thang, b.getEmpsn(),
									b.getDucls(), b.getBasicSalary(), b.getComSalary(), b.getDeptID() });
			} else {
				// neu co trong bang lan 1 thi xoa no di.
				if (getJdbcTemplate().queryForInt(
						"select count(empsn) from n_ct_bhxh_tn_1 where id_bhxhtn =? and empsn = ?",
						new Object[] { nam + thang, b.getEmpsn() }) != 0)
					getJdbcTemplate().update("delete n_ct_bhxh_tn_1 where id_bhxhtn =? and empsn = ? ",
							new Object[] { nam + thang, b.getEmpsn() });
			}
		}
	}

	public void insertListAll02(List<EmployeePlain> listDu, List<EmployeePlain> listKo, final String nam,
			final String thang, final String userName) {
		try {
			Date d = new Date(System.currentTimeMillis());
			final int day = getCountdayofmonth(Integer.valueOf(thang), Integer.valueOf(nam));
			for (EmployeePlain bc : listDu) {
				final EmployeePlain b = bc;
				String c = (String) getJdbcTemplate().execute(new CallableStatementCreator() {
					@Override
					public CallableStatement createCallableStatement(Connection con) throws SQLException {
						CallableStatement cs = con
								.prepareCall("{call a_n_social_infor_report_i_ro2(?,?,?,?,?,?,?,?,?,?) }");
						cs.setString(1, b.getEmpsn());
						cs.setDate(2, new java.sql.Date(Integer.valueOf(nam) - 1900, Integer.valueOf(thang) - 1, 01));
						cs.setDate(3, new java.sql.Date(Integer.valueOf(nam) - 1900, Integer.valueOf(thang) - 1, day));
						cs.setString(4, "n_social_infor_report_2");
						cs.setFloat(5, (float) b.getComSalary());
						cs.setString(6, "");
						cs.setString(7, userName);
						cs.setDate(8, new java.sql.Date(System.currentTimeMillis()));
						cs.setInt(9, 1);
						cs.registerOutParameter(10, java.sql.Types.VARCHAR);
						return cs;
					}
				}, new CallableStatementCallback() {
					@Override
					public String doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
						cs.execute();
						return cs.getString(10);
					}
				});
				System.out.println(c);
				// chi luu nhung nguoi bo sung
				if (getJdbcTemplate().queryForInt(
						"select count(empsn) from n_ct_bhxh_tn_1 where id_bhxhtn =? and empsn = ?",
						new Object[] { nam + thang, b.getEmpsn() }) == 0) {

					if (getJdbcTemplate().queryForInt(
							"select count(empsn) from n_ct_bhxh_tn_2 where id_bhxhtn =? and empsn = ?",
							new Object[] { nam + thang, b.getEmpsn() }) == 0)
						getJdbcTemplate().update("insert into n_ct_bhxh_tn_2 values(?,?,?,?,?,?,?,"
								+ "(select count(empsn) from n_ct_bhxh_tn_1 where id_bhxhtn =? and empsn = ?)||?)",
								new Object[] { nam + thang, b.getDucls(), b.getBasicSalary(), b.getComSalary(),
										b.getEmpsn(), sdf.format(d) + " INSERT BY " + userName, b.getDeptID(),
										nam + thang, b.getEmpsn(), " " + c });
					else
						getJdbcTemplate().update("update n_ct_bhxh_tn_2 set ngaycong = ?, luongcb = ?, luonghd = ?, "
								+ "note = ?, depsn = ?"
								+ ", status =  to_char((select count(empsn) from n_ct_bhxh_tn_1 where id_bhxhtn =? and empsn = ?))||? "
								+ " where id_bhxhtn =? and empsn = ? and (ngaycong <> ?"
								+ "or luongcb <> ? or luonghd <> ? or depsn <> ?)",
								new Object[] { b.getDucls(), b.getBasicSalary(), b.getComSalary(),
										sdf.format(d) + " UPDATE BY " + userName, b.getDeptID(), nam + thang,
										b.getEmpsn(), " " + c, nam + thang, b.getEmpsn(), b.getDucls(),
										b.getBasicSalary(), b.getComSalary(), b.getDeptID() });

					// neu da co trong bang thu hoi roi thi xoa no khoi bang thu hoi
					if (getJdbcTemplate().queryForInt(
							"select count(empsn) from n_ct_bhxh_tn_0 where id_bhxhtn =? and empsn = ?",
							new Object[] { nam + thang, b.getEmpsn() }) != 0)
						getJdbcTemplate().update("delete n_ct_bhxh_tn_0 where id_bhxhtn =? and empsn = ? ",
								new Object[] { nam + thang, b.getEmpsn() });
				}

			}

			for (EmployeePlain bc : listKo) {
				final EmployeePlain b = bc;
				String c = (String) getJdbcTemplate().execute(new CallableStatementCreator() {
					@Override
					public CallableStatement createCallableStatement(Connection con) throws SQLException {
						CallableStatement cs = con
								.prepareCall("{ call a_n_social_infor_report_i_ro2(?,?,?,?,?,?,?,?,?,?) }");
						cs.setString(1, b.getEmpsn());
						cs.setDate(2, new java.sql.Date(Integer.valueOf(nam) - 1900, Integer.valueOf(thang) - 1, 01));
						cs.setDate(3, new java.sql.Date(Integer.valueOf(nam) - 1900, Integer.valueOf(thang) - 1, day));
						cs.setString(4, "n_social_infor_report_2");
						cs.setFloat(5, (float) b.getComSalary());
						cs.setString(6, "");
						cs.setString(7, userName);
						cs.setDate(8, new java.sql.Date(System.currentTimeMillis()));
						cs.setInt(9, -1);
						cs.registerOutParameter(10, java.sql.Types.VARCHAR);
						return cs;
					}
				}, new CallableStatementCallback() {
					@Override
					public String doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
						cs.execute();
						return cs.getString(10);
					}
				});
				System.out.println(c);
				if (getJdbcTemplate().queryForInt(
						"select count(empsn) from n_ct_bhxh_tn_1 where id_bhxhtn =? and empsn = ?",
						new Object[] { nam + thang, b.getEmpsn() }) != 0) {
					if (getJdbcTemplate().queryForInt(
							"select count(empsn) from n_ct_bhxh_tn_0 where id_bhxhtn =? and empsn = ?",
							new Object[] { nam + thang, b.getEmpsn() }) == 0)
						getJdbcTemplate().update("insert into n_ct_bhxh_tn_0 values(?,?,?,?,?,?,?,?)",
								new Object[] { nam + thang, b.getDucls(), b.getBasicSalary(), b.getComSalary(),
										b.getEmpsn(), sdf.format(d) + " INSERT BY " + userName, b.getDeptID(), c });
					else
						getJdbcTemplate().update("update n_ct_bhxh_tn_0 set ngaycong = ?, luongcb = ?, luonghd = ?, "
								+ "note = ?, depsn = ? , status = ? where id_bhxhtn =? and empsn = ? and (ngaycong <> ?"
								+ "or luongcb <> ? or luonghd <> ? or depsn <> ?)",
								new Object[] { b.getDucls(), b.getBasicSalary(), b.getComSalary(),
										sdf.format(d) + " UPDATE BY " + userName, b.getDeptID(), c, nam + thang,
										b.getEmpsn(), b.getDucls(), b.getBasicSalary(), b.getComSalary(),
										b.getDeptID() });

					// neu da co trong bang lan 2 roi thi xoa no khoi bang lan 2
					if (getJdbcTemplate().queryForInt(
							"select count(empsn) from n_ct_bhxh_tn_2 where id_bhxhtn =? and empsn = ?",
							new Object[] { nam + thang, b.getEmpsn() }) != 0)
						getJdbcTemplate().update("delete n_ct_bhxh_tn_2 where id_bhxhtn =? and empsn = ? ",
								new Object[] { nam + thang, b.getEmpsn() });
				}
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void insertListAll(List<EmployeePlain> list, final String nam, final String thang, final String userName) {
		for (EmployeePlain bc : list) {
			final EmployeePlain b = bc;
			List<Object> s = new ArrayList<Object>();
			getJdbcTemplate().call(new CallableStatementCreator() {
				@Override
				public CallableStatement createCallableStatement(Connection con) throws SQLException {
					CallableStatement cs = con.prepareCall("{ call a_n_social_infor_report_i_roOK(?,?,?,?,?,?) }");
					cs.setString(1, b.getEmpsn());
					cs.setDate(2, new java.sql.Date(Integer.valueOf(nam) - 1900, Integer.valueOf(thang) - 1, 01));
					cs.setFloat(3, (float) b.getComSalary());
					cs.setString(4, "");
					cs.setString(5, userName);
					cs.setDate(6, new java.sql.Date(System.currentTimeMillis()));
					return cs;
				}
			}, s);
		}
	}

	public void insertListAll12(List<EmployeePlain> list, final String nam, final String thang, final String userName) {
		for (EmployeePlain bc : list) {
			final EmployeePlain b = bc;
			List<Object> s = new ArrayList<Object>();
			getJdbcTemplate().call(new CallableStatementCreator() {
				@Override
				public CallableStatement createCallableStatement(Connection con) throws SQLException {
					CallableStatement cs = con.prepareCall("{ call a_n_social_infor_report_i_12(?,?,?,?,?,?) }");
					cs.setString(1, b.getEmpsn());
					cs.setDate(2, new java.sql.Date(Integer.valueOf(nam) - 1900, Integer.valueOf(thang) - 1, 01));
					cs.setFloat(3, (float) b.getComSalary());
					cs.setString(4, "");
					cs.setString(5, userName);
					cs.setDate(6, new java.sql.Date(System.currentTimeMillis()));
					return cs;
				}
			}, s);

		}
	}

	public List<EmployeePlain> getListEmpGCNV(String namedept, String nam, String thang) {
		StopWatch sw = new StopWatch();
		sw.reset();
		sw.start();
		String sor = "";
		try {
			namedept += "%";
			final String month = thang;
			final String year = nam;
			final String date = "/" + month + "/" + year;
			int day = getCountdayofmonth(Integer.valueOf(month), Integer.valueOf(nam));

			String sql = "SELECT dt.empsn,(select b.fname from n_employee b where b.empsn=dt.empsn ) fname"
					+ ",(select b.lname from n_employee b where b.empsn=dt.empsn ) lname,dp.id_dept, dp.name_dept"
					+ ",dp.id_dept, dp.name_dept_name,  bsaly_by_date(dt.empsn, to_date(?, 'dd/mm/yyyy')) basic_salary"
					+ ", comsaly_by_date(dt.empsn, to_date(?, 'dd/mm/yyyy'),bsaly_by_date(dt.empsn, to_date(?, 'dd/mm/yyyy'))) salary"
					+ ",(SELECT max(eq.real_off_date) as qu  FROM n_emp_quit eq WHERE eq.empsn = dt.empsn ) quitday "
					+ ",(select rq.name_qr from n_quit_reason rq where rq.id_quit_reason = "
					+ "(SELECT qt.id_quit_reason as qt FROM n_emp_quit qt WHERE qt.empsn = dt.empsn "
					+ "and qt.real_off_date = (SELECT max(qq.real_off_date)  FROM n_emp_quit qq WHERE qq.empsn = dt.empsn ))) as quitReason "
					+ // them ngay 16/10/2013.
					"FROM n_get_data dt, n_department dp WHERE dp.id_dept = dt.depsn and dt.depsn <>'00000' "
					+ "AND dp.name_dept like ? AND dt.months = ? AND dt.years = ? ";

			Object[] obj = new Object[] { day + date, day + date, day + date, namedept, thang, nam };

			List<EmployeePlain> listAll = new ArrayList<EmployeePlain>();
			ParameterizedRowMapper map = new ParameterizedRowMapper<EmployeePlain>() {
				@Override
				public EmployeePlain mapRow(ResultSet rs, int arg1) throws SQLException {
					EmployeePlain b;

					b = new EmployeePlain();
					b.setEmpsn(rs.getString("empsn"));
					b.setFname(rs.getString("fname"));
					b.setLname(rs.getString("lname"));
					b.setDeptID(rs.getString("id_dept"));
					b.setDeptName(rs.getString("name_dept"));
					b.setBasicSalary(rs.getDouble("basic_salary"));
					b.setComSalary(rs.getDouble("salary"));
					b.setQuitDay(rs.getDate("quitday"));
					b.setQuitReason(rs.getString("quitReason"));

					return b;
				}
			};
			listAll = getSimpleJdbcTemplate().query(sql, map, obj);

			sw.stop();
			System.out.println("Run in " + (int) ((float) sw.getTime() / 60000) + ":"
					+ (int) (((float) sw.getTime() % 60000) / 1000) + " s");
			return listAll;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
			System.out.println(sor);
			sw.stop();
			System.out.println("Run in " + (int) ((float) sw.getTime() / 60000) + ":"
					+ (int) (((float) sw.getTime() % 60000) / 1000) + " s");
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Object[] getListEmp(String namedept, String nam, String thang, final int stt, boolean nghiViec) {
		StopWatch sw = new StopWatch();
		sw.reset();
		sw.start();
		String sor = "";
		try {
			namedept += "%";
			final String month = thang;
			final String year = nam;
			final String date = "/" + month + "/" + year;
			int day = getCountdayofmonth(Integer.valueOf(month), Integer.valueOf(nam));
			int ncbh = getSimpleJdbcTemplate()
					.queryForInt("select giatrithamso from n_thamso where tenthamso = 'NCBH1'", new Object[] {});
			int dknc = getSimpleJdbcTemplate()
					.queryForInt("select giatrithamso from n_thamso where tenthamso = 'DKMUABHXH-TN'", new Object[] {});
			final int dkncbh = getSimpleJdbcTemplate().queryForInt(
					"select giatrithamso from n_thamso where tenthamso = 'DKMUABHXH-TN1'", new Object[] {});

			String sql = "SELECT dt.empsn,(select b.fname from n_employee b where b.empsn=dt.empsn ) fname,(select b.lname from n_employee b where b.empsn=dt.empsn ) lname"
					+ ", get_tsncong_data_daily(dt.empsn,to_date(?,'dd/mm/yyyy'), to_date(?,'dd/mm/yyyy')) as ngaycong,"
					+ " bonus2_by_date(dt.empsn, to_date(?, 'dd/mm/yyyy')) bonus, dp.id_dept, dp.name_dept,  "
					+ "bsaly_by_date(dt.empsn, to_date(?, 'dd/mm/yyyy')) basic_salary, comsaly_by_date(dt.empsn, to_date(?, 'dd/mm/yyyy'),"
					+ "bsaly_by_date(dt.empsn, to_date(?, 'dd/mm/yyyy'))) salary, dt.ducls, dt.rest_pay, "
					+ "(nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy'), to_date(?, 'dd/mm/yyyy'),'NS'), 0)+ "
					+ "nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy'), to_date(?, 'dd/mm/yyyy'),'NS_RO'), 0)) nghi_san, "
					+ "(nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy'), to_date(?, 'dd/mm/yyyy'),'PBENH'), 0) + "
					+ "nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy'), to_date(?, 'dd/mm/yyyy'),'PBAN'), 0) + "
					+ "nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy'),to_date(?, 'dd/mm/yyyy'),'DS'), 0)) nghi_co_phep, "
					+ "nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy'), to_date(?, 'dd/mm/yyyy'),'KC'), 0) khang_cong "
					+ ",l.date_s ngayky, (select t.limit from n_labourkind t where t.id = l.limit) as limi,(SELECT max(eq.real_off_date) as q  FROM n_emp_quit eq "
					+ " WHERE eq.empsn = dt.empsn ) quitday " +
					// Them ly do nghi viec
					",(select rq.name_qr from n_quit_reason rq where rq.id_quit_reason = "
					+ "(SELECT qt.id_quit_reason as qt FROM n_emp_quit qt WHERE qt.empsn = dt.empsn "
					+ "and qt.real_off_date = (SELECT max(qq.real_off_date)  FROM n_emp_quit qq WHERE qq.empsn = dt.empsn ))) as quitReason ";

			if (nghiViec) {
				sql += " FROM n_get_data dt, n_department dp, n_emp_quit qu, n_labour l "
						+ "WHERE dp.id_dept = qu.depsn and dt.empsn = l.empsn and dp.name_dept like ? "
						+ "AND dt.months = ? AND dt.years = ? and l.clock=1 "
						+ "and exists (select * from n_labour a where a.empsn=l.empsn "
						+ "AND a.times = 1 AND a.date_s <= to_date(?,'dd/mm/yyyy'))"
						+ "and dt.empsn = qu.empsn /*khac nhau tai day*/ AND qu.real_off_date >= to_date(?,'dd/mm/yyyy') --and dt.empsn in ('13030900')";
			} // chay lai vai so the
			else {
				sql += " FROM n_get_data dt, n_department dp, n_labour l "
						+ "WHERE dp.id_dept = dt.depsn and dt.depsn <>'00000' and dt.empsn = l.empsn and dp.name_dept like ? AND "
						+ "dt.months = ? AND dt.years = ? and l.clock=1 and exists (select * from n_labour a where a.empsn=l.empsn "
						+ "AND a.times = 1 AND a.date_s <= to_date(?,'dd/mm/yyyy')) and dt.depsn <> '00000' --and dt.empsn in ('13080009')";
			} // chay lai vai so the

			Object[] obj;
			if (stt == 1) {

				if (nghiViec)
					obj = new Object[] { "01" + date, ncbh + date, ncbh + date, ncbh + date, ncbh + date, ncbh + date,
							"01" + date, ncbh + date, "01" + date, ncbh + date, "01" + date, ncbh + date, "01" + date,
							ncbh + date, "01" + date, ncbh + date, "01" + date, ncbh + date, namedept, thang, nam,
							"01" + date, "01" + date };
				else
					obj = new Object[] { "01" + date, ncbh + date, ncbh + date, ncbh + date, ncbh + date, ncbh + date,
							"01" + date, ncbh + date, "01" + date, ncbh + date, "01" + date, ncbh + date, "01" + date,
							ncbh + date, "01" + date, ncbh + date, "01" + date, ncbh + date, namedept, thang, nam,
							"01" + date };
			} else {

				if (nghiViec)
					obj = new Object[] { "01" + date, day + date, day + date, day + date, day + date, day + date,
							"01" + date, day + date, "01" + date, day + date, "01" + date, day + date, "01" + date,
							day + date, "01" + date, day + date, "01" + date, day + date, namedept, thang, nam,
							"01" + date, "01" + date };
				else
					obj = new Object[] { "01" + date, day + date, day + date, day + date, day + date, day + date,
							"01" + date, day + date, "01" + date, day + date, "01" + date, day + date, "01" + date,
							day + date, "01" + date, day + date, "01" + date, day + date, namedept, thang, nam,
							"01" + date };
			}

			final int dayNotSun = getSimpleJdbcTemplate().queryForInt("select c_day_notsun(to_date('01" + date
					+ "','dd/mm/yyyy'), " + "to_date('" + ncbh + date + "','dd/mm/yyyy')) as ngayphailam FROM dual",
					new Object[] {});
			final int dayNotSun2 = getSimpleJdbcTemplate().queryForInt("select c_day_notsun(to_date('01" + date
					+ "','dd/mm/yyyy'), " + "to_date('" + day + date + "','dd/mm/yyyy')) as ngayphailam FROM dual",
					new Object[] {});

			List<EmployeePlain> listTemp = new ArrayList<EmployeePlain>();
			ParameterizedRowMapper map = new ParameterizedRowMapper<EmployeePlain>() {
				@Override
				public EmployeePlain mapRow(ResultSet rs, int arg1) throws SQLException {
					EmployeePlain b;
					String empsn = rs.getString("empsn");
					double ncp = rs.getDouble("nghi_co_phep");
					double ns = rs.getDouble("nghi_san");
					double kc = rs.getDouble("khang_cong");
					double dayOFF = 0;

					if (stt == 1)
						dayOFF = dayNotSun - (rs.getDouble("ngaycong") + ns + ncp + kc);
					else
						dayOFF = dayNotSun2 - (rs.getDouble("ngaycong") + ns + ncp + kc);

					String lname = "";
					if (rs.getString("lname") != null) {
						lname = rs.getString("lname");
					}
					b = new EmployeePlain(empsn, rs.getString("fname"), lname, rs.getDouble("bonus"),
							rs.getString("id_dept"), rs.getString("name_dept"), rs.getDouble("basic_salary"),
							rs.getDouble("salary"), rs.getDouble("ngaycong"), rs.getDouble("rest_pay"), ns, ncp, kc,
							dayOFF);

					b.setSignDate(rs.getDate("ngayky"));
					b.setLabourKind(rs.getString("limi"));
					b.setQuitDay(rs.getDate("quitday"));
					b.setQuitReason(rs.getString("quitReason"));

					return b;
				}
			};
			listTemp = getSimpleJdbcTemplate().query(sql, map, obj);

			Object[] arrObj = new Object[3];
			List<EmployeePlain> listAll = new ArrayList<EmployeePlain>();
			List<EmployeePlain> listDu = new ArrayList<EmployeePlain>();
			List<EmployeePlain> listKo = new ArrayList<EmployeePlain>();
			String sqldk1 = "select check_dk_muabhxh_tn(?,to_date(?,'dd/mm/yyyy'))" + " from dual";
			Object[] objdk1;
			if (stt == 1) {
				sqldk1 = "select check_dk_muabhxh_tn(?,to_date(?,'dd/mm/yyyy'))-"
						+ "(select count(empsn) from n_emp_quit t where t.empsn=? "
						+ "and t.real_off_date<to_date(?,'dd/mm/yyyy') "
						+ "and (t.thang_tanglai is null or t.thang_tanglai > to_date(?,'dd/mm/yyyy')))from dual";
			}

			sw.stop();
			System.out.println("Run in " + (int) ((float) sw.getTime() / 60000) + ":"
					+ (int) (((float) sw.getTime() % 60000) / 1000) + " s");
			sw.reset();
			sw.start();

			String sqls = "SELECT la.date_s as da , lk.limit as li, (SELECT max(eq.real_off_date) as qu "
					+ "FROM n_emp_quit eq WHERE eq.empsn = ?) as oq "
					+ "FROM n_labour la, n_labourkind lk WHERE la.empsn= ? AND la.limit = lk.id  and la.clock=1";

			for (EmployeePlain b : listTemp) {
				sor = b.getEmpsn();

				if (stt == 1)
					objdk1 = new Object[] { b.getEmpsn(), "01" + date, b.getEmpsn(), "16" + date, day + date };
				else
					objdk1 = new Object[] { b.getEmpsn(), "01" + date };
				// xet lai dk chia list them trang thai xac dinh ly do ko mua bao hiem
				if (getSimpleJdbcTemplate().queryForInt(sqldk1, objdk1) == 1) {

					if (stt == 1)
						if (b.getDucls() >= dkncbh) {
							b.setStatus(1);
							listDu.add(b);
						} else {
							b.setStatus(0);
							listKo.add(b);
						}
					else {
						float d = (float) (dayNotSun2 - b.getDucls());

						if (d < dknc) {
							b.setStatus(1);
							listDu.add(b);
						} else {
							b.setStatus(0);
							listKo.add(b);
						}
					}
				} else {
					b.setStatus(-1);
					listKo.add(b);
				}
				listAll.add(b);
			}

			sql = "SELECT dt.empsn,(select b.fname from n_employee b where b.empsn=dt.empsn ) fname,(select b.lname from n_employee b where b.empsn=dt.empsn ) lname"
					+ ", get_tsncong_data_daily(dt.empsn,to_date(?,'dd/mm/yyyy'), to_date(?,'dd/mm/yyyy')) as ngaycong,"
					+ " bonus2_by_date(dt.empsn, to_date(?, 'dd/mm/yyyy')) bonus, dp.id_dept, dp.name_dept,  "
					+ "bsaly_by_date(dt.empsn, to_date(?, 'dd/mm/yyyy')) basic_salary, comsaly_by_date(dt.empsn, to_date(?, 'dd/mm/yyyy'),"
					+ "bsaly_by_date(dt.empsn, to_date(?, 'dd/mm/yyyy'))) salary, dt.ducls, dt.rest_pay, "
					+ "(nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy'), to_date(?, 'dd/mm/yyyy'),'NS'), 0)+ "
					+ "nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy'), to_date(?, 'dd/mm/yyyy'),'NS_RO'), 0)) nghi_san, "
					+ "(nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy'), to_date(?, 'dd/mm/yyyy'),'PBENH'), 0) + "
					+ "nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy'), to_date(?, 'dd/mm/yyyy'),'PBAN'), 0) + "
					+ "nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy'),to_date(?, 'dd/mm/yyyy'),'DS'), 0)) nghi_co_phep, "
					+ "nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy'), to_date(?, 'dd/mm/yyyy'),'KC'), 0) khang_cong "
					+ ",to_date(null,'dd/mm/yyyy') as ngayky, '' as limi,(SELECT max(eq.real_off_date) as q FROM n_emp_quit eq WHERE eq.empsn = dt.empsn ) quitday  "
					+
					// Them ly do nghi viec
					",(select rq.name_qr from n_quit_reason rq where rq.id_quit_reason = "
					+ "(SELECT qt.id_quit_reason as qt FROM n_emp_quit qt WHERE qt.empsn = dt.empsn "
					+ "and qt.real_off_date = (SELECT max(qq.real_off_date)  FROM n_emp_quit qq WHERE qq.empsn = dt.empsn ))) as quitReason ";

			// lay ds nhung nguoi chua ky hop dong add them vao listALL
			listTemp.clear();
			if (!nghiViec) {
				sql += "FROM n_get_data dt, n_department dp "
						+ "WHERE dp.id_dept = dt.depsn and dt.depsn <>'00000' AND dp.name_dept like ? AND dt.months = ? AND dt.years = ? "
						+ "and not exists (select * from n_labour a where a.id_labour like dt.empsn||'%' "
						+ "AND a.date_s <= to_date(?,'dd/mm/yyyy'))";
//chay lai vai so the				
				listTemp = getSimpleJdbcTemplate().query(sql, map,
						obj);/*
								 * new Object[]{obj[0], obj[1], obj[2], obj[3], obj[4], obj[5], obj[6], obj[7],
								 * //obj[8], obj[9], obj[10], obj[11], obj[12], obj[13], obj[14], obj[15],
								 * obj[16], obj[17], obj[18], obj[19], obj[20], obj[21]});
								 */

			} else { /// them nhung nguoi chua ky hop dong cho list nghi viec
				sql += "FROM n_get_data dt, n_department dp, n_emp_quit qu "
						+ "WHERE dp.id_dept = qu.depsn and dp.name_dept like ? AND dt.months = ? AND dt.years = ? "
						+ "and not exists (select * from n_labour a where a.id_labour like dt.empsn||'%' "
						+ "AND a.date_s <= to_date(?,'dd/mm/yyyy')) "
						+ "and dt.empsn = qu.empsn AND qu.real_off_date >= to_date(?,'dd/mm/yyyy')";
//chay lai vai so the					
				listTemp = getSimpleJdbcTemplate().query(sql, map,
						obj); /*
								 * new Object[]{obj[0], obj[1], obj[2], obj[3], obj[4], obj[5], obj[6], obj[7],
								 * //obj[8], obj[9], obj[10], obj[11], obj[12], obj[13], obj[14], obj[15],
								 * obj[16], obj[17], obj[18], obj[19], obj[20], obj[21],obj[22]});
								 */

			}

			listAll.addAll(listTemp);
			arrObj[0] = listAll;
			arrObj[1] = listDu;
			arrObj[2] = listKo;
			sw.stop();
			System.out.println("Run in " + (int) ((float) sw.getTime() / 60000) + ":"
					+ (int) (((float) sw.getTime() % 60000) / 1000) + " s");
			return arrObj;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
			System.out.println(sor);
			sw.stop();
			System.out.println("Run in " + (int) ((float) sw.getTime() / 60000) + ":"
					+ (int) (((float) sw.getTime() % 60000) / 1000) + " s");
		}
		return null;
	}

	public float[] BHXHTN() {
		try {
			float[] tyle = new float[2];
			tyle[0] = getSimpleJdbcTemplate().queryForInt("select giatri3 from n_thamso where tenthamso = 'TYLEBHXH'",
					new Object[] {});
			tyle[1] = getSimpleJdbcTemplate().queryForInt("select giatri3 from n_thamso where tenthamso = 'TYLEBHTN'",
					new Object[] {});
			return tyle;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return null;
	}

	public List<N_EMP_QUIT> getListTruBH(String namedept, String nam, String thang) {
		String date = "/" + thang + "/" + nam;
		namedept += "%";
		int day = getCountdayofmonth(Integer.valueOf(thang), Integer.valueOf(nam));
		List<N_EMP_QUIT> listEQ = new ArrayList<N_EMP_QUIT>();
		String sql = "select e.empsn,e.fname, e.lname,q.depsn,d.name_dept, q.real_off_date,q.thang_trubh "
				+ "FROM n_employee e,  n_emp_quit q , n_department d "
				+ "WHERE d.name_dept like ? AND e.empsn = q.empsn and q.depsn = d.id_dept "
				+ "AND q.thang_trubh = to_date(?,'dd/mm/yyyy')";
		listEQ = getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<N_EMP_QUIT>() {
			@Override
			public N_EMP_QUIT mapRow(ResultSet rs, int arg1) throws SQLException {
				N_EMP_QUIT e = new N_EMP_QUIT();
				e.setEMPSN(rs.getString("empsn"));
				e.setDEPT_NAME(rs.getString("name_dept"));
				e.setDEPSN(rs.getString("depsn"));
				e.setNOTE(rs.getString("fname") + " " + rs.getString("lname"));// ho ten nhan vien
				e.setREAL_OFF_DATE(rs.getDate("real_off_date"));
				e.setTHANG_TRUBH(rs.getDate("thang_trubh"));
				return e;
			}
		}, new Object[] { namedept, "01" + date });
		return listEQ;
	}

	public List<EmployeeBear> getListNghiSan(String namedept, String nam, String thang) {
		String date = "/" + thang + "/" + nam;
		namedept += "%";
		int day = getCountdayofmonth(Integer.valueOf(thang), Integer.valueOf(nam));
		List<EmployeeBear> listNS = new ArrayList<EmployeeBear>();
		String sql = "select e.empsn, e.fname, e.lname, e.depsn, d.name_dept,b.b_dates, b.e_dates,b.note "
				+ "from n_employee e, n_time_bear b, n_department d "
				+ "where e.empsn = b.empsn and e.depsn = d.id_dept and d.name_dept like ? "
				+ "and b.b_dates >= to_date(?, 'dd/mm/yyyy')AND b.b_dates <= to_date(?, 'dd/mm/yyyy')";
		listNS = getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<EmployeeBear>() {
			@Override
			public EmployeeBear mapRow(ResultSet rs, int arg1) throws SQLException {
				EmployeeBear e = new EmployeeBear();
				e.setEMPSN(rs.getString("empsn"));
				e.setNAME(rs.getString("fname") + " " + rs.getString("lname"));
				e.setDEPSN(rs.getString("depsn"));
				e.setNAME_DEPT(rs.getString("name_dept"));
				e.setB_DATES(rs.getDate("b_dates"));
				e.setE_DATES(rs.getDate("e_dates"));
				e.setNUM(rs.getString("note"));
				return e;
			}
		}, new Object[] { namedept, "01" + date, day + date });

		return listNS;
	}

	public int getCountdayofmonth(int thang, int nam) {
		int day = 0;
		if (thang == 1 || thang == 3 || thang == 5 || thang == 7 || thang == 8 || thang == 10 || thang == 12)
			day = 31;
		else if (thang == 2)
			if (nam % 4 == 0)
				day = 29;
			else
				day = 28;
		else
			day = 30;

		return day;
	}

}