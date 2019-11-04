package ds.program.fvhr.dao.quitsalary;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.sql.DataSource;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import ds.program.fvhr.domain.ATTENDANTDB_QUIT;
import ds.program.fvhr.domain.ATTQUIT;
import ds.program.fvhr.ui.quitworksalary.AttQuitInitData;
import ds.program.fvhr.ui.quitworksalary.QuitWork00RProgram;
import dsc.echo2app.Application;
import dsc.echo2app.info.CompanyInfo;
import fv.util.ApplicationHelper;
import fv.util.CustomDomainUtils;
import fv.util.DbUtils;

public class QuitWorkSalaryDAO extends SimpleJdbcDaoSupport {

	private String month;

	private String year;

	private static final Log log = LogFactory.getLog(QuitWorkSalaryDAO.class);

	public QuitWorkSalaryDAO() {
		CompanyInfo cominfo = Application.getApp().findCompanyById(
				Application.getApp().getLoginInfo().getCompanyID());
		DataSource ds = (DataSource) Application.getSpringContext().getBean(
				cominfo.getConnectionName());
		setDataSource(ds);
	}

	public QuitWorkSalaryDAO(String month, String year) {
		this();
		this.month = month;
		this.year = year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getMonth() {
		return this.month;
	}

	public String getYear() {
		return this.year;
	}

	public boolean checkAttendantExist(String empsn, Date dtv) {
		java.sql.Date d = new java.sql.Date(dtv.getTime());
		String sql = "SELECT EMPSN FROM ATTENDANTDB_QUIT WHERE EMPSN=? AND DOT_TV=?";
		String sn = null;
		try {
			sn = (String) getJdbcTemplate().queryForObject(sql,
					new Object[] { empsn, d }, String.class);
		} catch (DataAccessException e) {
		}
		if (sn != null)
			return true;
		return false;
	}

	public boolean checkAttExist(String empsn, Date dtv) {
		java.sql.Date d = new java.sql.Date(dtv.getTime());
		String table = "ATTQUIT" + year + month;
		String sql = "SELECT EMPSN FROM " + table
				+ " WHERE EMPSN=? AND DOT_TV=?";
		String sn = null;
		try {
			sn = (String) getJdbcTemplate().queryForObject(sql,
					new Object[] { empsn, d }, String.class);
		} catch (DataAccessException e) {
		}
		if (sn != null)
			return true;
		return false;
	}

	public int saveInitAttData(AttQuitInitData att) {
		int ret = 0;
		try {
			ret = getJdbcTemplate().update(
					new AttQuitInsertStatementCreator(att, month, year));
		} catch (DataAccessException e) {
			ret = getJdbcTemplate().update(
					new AttQuitUpdateStatementCreator(att, month, year));
		}
		
		// if (checkAttExist(att.getEmpsn(), att.getDotTv(), month, year)){
		// ret = getJdbcTemplate().update(new AttQuitUpdateStatementCreator(att,
		// month, year));
		// } else {
		// ret = getJdbcTemplate().update(new AttQuitInsertStatementCreator(att,
		// month, year));
		// }
		return ret;
	}

	public ATTENDANTDB_QUIT getAttQuitData(String empsn) {
		String sql = "SELECT DISTINCT E.EMPSN, E.FNAME, E.LNAME, Q.DEPSN, E.POSITION, E.DATE_HIRED, D.DUCLS, D.NDUCLS, D.ADDCLS1, D.NADDCLS,"
				+ " D.ADDHOL,D.REST,D.REST_PAY,D.NWHOUR,D.LATE, D.ADDHOLN, D.SIGN, D.ACNM, D.REST_SICK, D.OTHER, D.LMATER,"
				+ " (SELECT MAX(Q.REAL_OFF_DATE) FROM N_EMP_QUIT Q WHERE Q.EMPSN=?) AS REAL_OFF_DATE"
				+ " FROM N_EMPLOYEE E, N_GET_DATA D, N_EMP_QUIT Q"
				+ " WHERE E.EMPSN=Q.EMPSN AND E.EMPSN=D.EMPSN AND E.EMPSN=? AND D.MONTHS=? AND D.YEARS=?";
		ATTENDANTDB_QUIT att = (ATTENDANTDB_QUIT) getJdbcTemplate()
				.queryForObject(sql,
						new Object[] { empsn, empsn, month, year },
						new AttendantDBQuitRowMapper());
		return att;
	}

	public ATTENDANTDB_QUIT getAttendantDbQuitData(String empsn, Date dotTV) {
		String sql = "SELECT DISTINCT * FROM ATTENDANTDB_QUIT T WHERE T.EMPSN=? AND T.DOT_TV=?";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection con = getConnection();
		try {
			pstm = con.prepareStatement(sql.toString());
			System.out.println(sql.toString());
			pstm.setString(1, empsn);
			pstm.setDate(2, new java.sql.Date(dotTV.getTime()));
			rs = pstm.executeQuery();
			if (rs.next()) {
				Map<String, String> headerMap = CustomDomainUtils
						.getHeaderColumnMap(ATTENDANTDB_QUIT.class);
				ATTENDANTDB_QUIT att = extractATTEData(headerMap, rs);
				return att;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(rs);
			DbUtils.close(pstm);
			DbUtils.close(con);
		}
		return null;
	}

	public ATTQUIT getAttquitData(String empsn, Date dotTV) {
		String sql = "SELECT * FROM ATTQUIT" + year + month
				+ " WHERE EMPSN=? AND DOT_TV=?";
		// System.out.println(sql);
		ATTQUIT att = (ATTQUIT) getJdbcTemplate().queryForObject(sql,
				new Object[] { empsn, new java.sql.Date(dotTV.getTime()) },
				new AttQuitRowMapper(null));
		return att;
	}

	public void calculate(String empsn, Date dot_TV, String type) {
		String name = "BV".equals(type)?"TINHLUONGBOVIEC":"TINHLUONGTHOIVIEC";
		AttQuitCalcStoredProcedure sp = new AttQuitCalcStoredProcedure(getDataSource(),name);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("THANG", month);
		params.put("NAM", year);
		params.put("SOTHE", empsn);
		params.put("DOT_TV", new java.sql.Date(dot_TV.getTime()));
		sp.excecute(params);
	}

	public List<ATTQUIT> selectList(Map<String, String> columnHeaderMap,
			String fact, String type, Date date) {
		List<ATTQUIT> list = new ArrayList<ATTQUIT>();
		if (date == null || fact == null)
			return list;
		// StringBuffer sb=null;
		String[] displayColumns = new String[columnHeaderMap.size()];
		Iterator<?> it = columnHeaderMap.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			@SuppressWarnings("unchecked")
			Entry<String, String> ks = (Entry<String, String>) it.next();
			displayColumns[i] = ks.getKey();
			i++;
		}
		StringBuffer sql = new StringBuffer("SELECT DISTINCT ");
		for (String col : displayColumns) {
			sql.append("A.").append(col.toUpperCase()).append(", ");
		}
		sql.delete(sql.length() - 2, sql.length());

		sql.append(" FROM ATTQUIT").append(year).append(month).append(
				" A, N_DEPARTMENT D WHERE A.DEPSN=D.ID_DEPT");
		sql.append(" AND D.NAME_FACT='" + fact + "'");
		if (type != null && !type.equals("")) {
			sql.append(" AND A.CLASS='" + type + "'");
		} else {
			sql.append(" AND A.CLASS IS NULL");
		}
		sql.append(" AND A.DOT_TV=?");
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection con = getConnection();
		try {
			pstm = con.prepareStatement(sql.toString());
			System.out.println(sql.toString());
			pstm.setDate(1, new java.sql.Date(date.getTime()));
			rs = pstm.executeQuery();
			while (rs.next()) {
				ATTQUIT att = extractData(columnHeaderMap, rs);
				list.add(att);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(rs);
			DbUtils.close(pstm);
			DbUtils.close(con);
		}

		return list;
	}

	private ATTQUIT extractData(Map<String, String> columnHeaderMap,
			ResultSet rs) {
		ATTQUIT att = new ATTQUIT();
		Iterator<?> it = columnHeaderMap.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("unchecked")
			Entry<String, String> ks = (Entry<String, String>) it.next();// kill
																			// steal
			try {
				PropertyUtils.setProperty(att, ks.getValue(), rs.getObject(ks
						.getKey()));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return att;
	}

	private ATTENDANTDB_QUIT extractATTEData(Map<String, String> headerMap,
			ResultSet rs) {
		ATTENDANTDB_QUIT att = new ATTENDANTDB_QUIT();
		Iterator<?> it = headerMap.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("unchecked")
			Entry<String, String> ks = (Entry<String, String>) it.next();// kill
																			// steal
			try {
				PropertyUtils.setProperty(att, ks.getValue(), rs.getObject(ks
						.getKey()));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return att;
	}

	public void updateAttQuitDataOrder(String empsn, Date dotTv, int i) {
		String table = "ATTQUIT" + year + month;
		String sql = "UPDATE " + table
				+ " SET STT=? WHERE EMPSN=? AND DOT_TV=?";
		System.out.println(sql + "(" + i + ", " + empsn + ", "
				+ new SimpleDateFormat("dd/MM/yyyy").format(dotTv) + ")");
		getJdbcTemplate().update(sql,
				new Object[] { i, empsn, new java.sql.Date(dotTv.getTime()) });
	}

	public BigDecimal[] getSoGioTangCaLe(String empsn) {
		BigDecimal[] data = new BigDecimal[2];
		String sql = "SELECT A.EMPSN,TO_DATE(C.DATES,'DD/MM/YYYY') AS DATES ,C.OTH"
				+ " FROM N_EMPLOYEE A,N_DATA_DAILY C"
				+ " WHERE A.EMPSN=C.EMPSN AND TO_CHAR(C.DATES,'MM/YYYY')=?"
				+ " AND C.DATES IN (SELECT D.DATE_SP FROM N_SP_WDAY D WHERE D.MULTIPLY_WD=3 AND TO_CHAR(D.DATE_SP,'MM/YYYY')=?)"
				+ " AND C.OTH >0 AND A.EMPSN=? ORDER BY A.EMPSN,C.DATES";
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String mon = month + "/" + year;
		BigDecimal tangCaNgay = BigDecimal.ZERO;
		BigDecimal tangCaDem = BigDecimal.ZERO;
		List<String> gioVaoList = new ArrayList<String>();
		gioVaoList.add("23:00");
		gioVaoList.add("22:00");
		gioVaoList.add("21:45");
		gioVaoList.add("21:30");
		gioVaoList.add("21:00");
		gioVaoList.add("20:30");
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, mon);
			pstm.setString(2, mon);
			pstm.setString(3, empsn);
			rs = pstm.executeQuery();
			while (rs.next()) {
				PreparedStatement p = con
						.prepareStatement("select * from n_register_shift e where e.empsn=? and to_date(e.shift_date,'dd/mm/yyyy')=?");
				p.setString(1, empsn);
				p.setDate(2, rs.getDate("DATES"));
				ResultSet r = p.executeQuery();
				try {
					if (r.next()) {
						String gioVao = r.getString("TIME_IN");
						if (gioVaoList.contains(gioVao)) {
							tangCaDem = rs.getBigDecimal("OTH");
						} else {
							tangCaNgay = tangCaNgay
									.add(rs.getBigDecimal("OTH"));
						}
					} else {
						tangCaNgay = tangCaDem.add(rs.getBigDecimal("OTH"));
					}
				} finally {
					r.close();
					p.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(rs);
			DbUtils.close(pstm);
			DbUtils.close(con);
		}
		data[0] = tangCaNgay;
		data[1] = tangCaDem;
		return data;
	}

	public String getDBonus(String depsn) {
		String sql = "SELECT D_BONUS FROM N_DEPARTMENT WHERE ID_DEPT=?";
		String dbonus = (String) getJdbcTemplate().queryForObject(sql,
				new Object[] { depsn }, String.class);
		if (dbonus != null && !dbonus.trim().equals("")) {
			return dbonus;
		}
		return "";
	}

	public BigDecimal getBuBHGiuaThang(Date tuNgay, Date denNgay,
			BigDecimal soNgayPhaiLam, BigDecimal soTien, String soThe) {
		String sql = "select sum(c.ducls+c.nucls) from n_employee a,n_data_daily c where a.empsn=c.empsn and a.empsn = ? and c.dates >= ? and c.dates <= ?";
		String sql1 = "select sum(a.total) from n_rest_detail a where a.empsn = ? and a.date_off >= ? and a.date_off <= ? and a.rest_sal='REST_PAY'";
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		BigDecimal ngayCong = BigDecimal.ZERO;
		BigDecimal nghiCoLuong = BigDecimal.ZERO;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, soThe);
			pstm.setDate(2, new java.sql.Date(tuNgay.getTime()));
			pstm.setDate(3, new java.sql.Date(denNgay.getTime()));
			rs = pstm.executeQuery();
			if (rs.next()) {
				ngayCong = rs.getBigDecimal(1);
			}
			DbUtils.close(rs);
			DbUtils.close(pstm);
			pstm = con.prepareStatement(sql1);
			pstm.setString(1, soThe);
			pstm.setDate(2, new java.sql.Date(tuNgay.getTime()));
			pstm.setDate(3, new java.sql.Date(denNgay.getTime()));
			rs = pstm.executeQuery();
			if (rs.next()) {
				nghiCoLuong = rs.getBigDecimal(1);
			}
			BigDecimal tongSoNgay = ngayCong.add(nghiCoLuong);
			BigDecimal ret = ((soTien.multiply(BigDecimal.valueOf(0.19)))
					.divide(soNgayPhaiLam)).multiply(tongSoNgay);
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(rs);
			DbUtils.close(pstm);
			DbUtils.close(con);
		}
		return BigDecimal.ZERO;
	}

	public void removeICData(String empsn, Date date, String type) {
		String sql = "UPDATE ATTENDANTDB_QUIT D SET D.DUCLS=0, D.NUCLS=0, D.ADDCLS1=0, D.NADDCLS=0,D.ADDHOL=0,D.REST=0,D.REST_PAY=0,D.NWHOUR=0,D.LATE=0,"
				+ " D.ADDHOLN=0, D.SIGN_TIME=0, D.ACNM=0, D.REST_SICK=0, D.OTHER=0, D.PHEP_NS=0 WHERE D.EMPSN=? AND D.DOT_TV=?";
		java.sql.Date sqldate = new java.sql.Date(date.getTime());
		getJdbcTemplate().update(sql, new Object[] { empsn, sqldate });
		calculate(empsn, date,type);
	}

	// Report/////////////////////

	public List<ATTQUIT> getReportData(int reportType, String factCondition,
			Calendar dotTV, String type, String month, String year) {
		if (reportType == QuitWork00RProgram.TONG_TONG)
			return null;
		String dateStr = "";
		String typeStr = "";
		if (dotTV != null) {
			dateStr = "and t.dot_tv=to_date('"
					+ new SimpleDateFormat("dd/MM/yyyy")
							.format(dotTV.getTime()) + "','dd/mm/yyyy') ";
		}
		if (type != null) {
			if (type.equals("BV")) {
				typeStr = "and t.class='BV' ";
			} else {
				typeStr = "and t.class is null ";
			}
		}
		String suffix = "";
		switch (reportType) {
		case QuitWork00RProgram.EXCEL_LUONG:
		case QuitWork00RProgram.EXCEL_TROCAP:
		case QuitWork00RProgram.EXCEL_THANHTOAN:
		case QuitWork00RProgram.EXCEL_KYNHAN:
		case QuitWork00RProgram.PDF_PHIEULUONG:
			suffix = "from attquit" + year + month + " t, n_department d, n_employee e "
					+ "where t.depsn=d.id_dept and t.empsn=e.empsn and e.user_manage_id in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + " " + factCondition + dateStr
					+ typeStr + "order by empsn";
			break;
		case QuitWork00RProgram.PDF_PHIEULUONG_ATM:
			suffix = "from attquit" + year + month + " t, n_department d, n_employee e "
					+ "where t.depsn=d.id_dept and t.empsn=e.empsn and instr(t.possn,'_')>0 and e.user_manage_id in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + " " + factCondition + dateStr
					+ typeStr + "order by empsn";
			break;
		case QuitWork00RProgram.PDF_PHIEULUONG_0ATM:
			suffix = "from attquit" + year + month + " t, n_department d, n_employee e "
					+ "where t.depsn=d.id_dept and t.empsn=e.empsn and instr(t.possn,'_')=0 and e.user_manage_id in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + " " + factCondition + dateStr
					+ typeStr + "order by empsn";
			break;
		case QuitWork00RProgram.TONG_BHXH:
			suffix = "from attquit" + year + month + " t, n_department d, n_employee e "
					+ "where t.depsn=d.id_dept and t.empsn=e.empsn and e.user_manage_id in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + " " + factCondition + dateStr
					+ typeStr + "and t.joininsu>0 order by t.dept_kt";
			break;
		case QuitWork00RProgram.TONG_BHYT:
			suffix = "from attquit" + year + month + " t, n_department d, n_employee e "
					+ "where t.depsn=d.id_dept and t.empsn=e.empsn and e.user_manage_id in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + " " + factCondition + dateStr
					+ typeStr + "and t.ylbx>0 order by t.dept_kt";
			break;
		case QuitWork00RProgram.TONG_BHTN:
			suffix = "from attquit" + year + month + " t, n_department d, n_employee e "
					+ "where t.depsn=d.id_dept and t.empsn=e.empsn and e.user_manage_id in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + " " + factCondition + dateStr
					+ typeStr + "and t.bh_tnghiep>0 order by t.dept_kt";
			break;
		case QuitWork00RProgram.TONG_BUBH:
			suffix = "from attquit" + year + month + " t, n_department d, n_employee e "
					+ "where t.depsn=d.id_dept and t.empsn=e.empsn and e.user_manage_id in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + " " + factCondition + dateStr
					+ typeStr + "and t.bonus9>0 order by t.dept_kt";
			break;
		}
		String sql = buildReportSql(reportType, month, year) + suffix;
//		sql = StringUtils.substringBefore(sql,"order") 
//		+ " and t.empsn in (select e.empsn from n_employee e where t.empsn=e.empsn and e.user_manage_id in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + ")"
//		+ "order" + StringUtils.substringAfter(sql,"order");
		List<ATTQUIT> list = getSimpleJdbcTemplate().query(sql,
				new AttQuitReportRowMapper());
		if (log.isDebugEnabled()) {
			log.debug(sql + " -> size: " + list.size());
		}
		return list;
	}

	private String buildReportSql(int reportType, String month, String year) {
		switch (reportType) {
		case QuitWork00RProgram.EXCEL_LUONG:
			return "select 'T"
					+ month
					+ "' as note_nv, t.empsn, t.empna, t.hired, t.dept_kt, d.name_dept_name as depsn, t.bsaly, t.bonus2_goc, t.tday, t.rest+t.rest_sick as rest, "
					+ "t.nwhour, t.addcls1, t.addhol+t.addholn as addhol, t.naddcls, t.tbasic, t.tadds, t.taddcls, t.bonus2, t.bonus1, t.bonus1_hol, t.bonus3, t.bonus7, "
					+ "t.bonus4+t.bonus9 as bonus4, t.bonus6, t.bonus_acn, t.tincome, t.ylbx, t.joininsu, t.bh_tnghiep, t.borm, t.paytax, "
					+ "t.kqt, t.tkq, t.ts_hientai, t.ts_hientai2, t.note ";
		case QuitWork00RProgram.EXCEL_TROCAP:
			return "select 'T"
					+ month
					+ "' as note_nv, t.empsn, t.empna, t.hired, t.dept_kt, d.name_dept_name as depsn, "
					+ "t.bsaly, t.bonus2_goc, t.bonus2_avg, t.bsal_avg, t.pn_conlai, t.pn_conlai_s, t.m_trocap as mm_trocap, t.m_denbu as mm_denbu, "
					+ "t.d_nghitruoc as dd_nghitruoc, t.tc_bsaly, t.tc_bonus2, t.m_denbu_s as mm_denbu_s, t.d_nghitruoc_s as dd_nghitruoc_s, "
					+ "t.m_trocap_s as mm_trocap_s, t.t_tctviec as tt_tctviec, t.t_tctviec1 as tt_tctviec1, t.note, t.ts as tts, t.ts1 ";
		case QuitWork00RProgram.EXCEL_THANHTOAN:
			return "select 'T"
					+ month
					+ "' as note_nv, t.empsn, t.empna, t.hired, t.dept_kt, d.name_dept_name as depsn, t.bsaly, t.bsal_avg, "
					+ "t.pn_conlai, t.pn_conlai_s, t.m_trocap as mm_trocap,t.d_nghitruoc as dd_nghitruoc, "
					+ "t.tc_bsaly+t.tc_bonus2 as tc_bsaly, t.t_tctviec as tt_tctviec, t.ts_hientai, t.ylbx, t.joininsu, t.bh_tnghiep, "
					+ "t.stnv, t.tkq, t.bonus9, t.combsaly, t.bh_quy, t.bu_bhyt, t.thu_bhyt, t.note, t.t_tctviec as tt_tctviec, t.ts_hientai2 ";
		case QuitWork00RProgram.EXCEL_KYNHAN:
			return "select 'T"
					+ month
					+ "' as note_nv, t.empsn, t.empna, t.hired, t.dept_kt, d.name_dept_name as depsn, t.bsaly, t.bsal_avg, t.bonus2_avg, "
					+ "t.pn_conlai, t.pn_conlai_s, t.m_trocap as mm_trocap, t.m_denbu as mm_denbu, t.d_nghitruoc as dd_nghitruoc, "
					+ "t.tc_bsaly, t.m_denbu_s as mm_denbu_s, t.tc_bonus2, t.d_nghitruoc_s as dd_nghitruoc_s, t.t_tctviec1 as tt_tctviec1, "
					+ "t.ts_hientai2, t.ts as tts, (select e.id_no from n_employee e where e.empsn=t.empsn) as note, t.t_tctviec as tt_tctviec, t.ts_hientai ";
		case QuitWork00RProgram.TONG_BHXH:
			return "select 'T"
					+ month
					+ "' as note_nv, t.empsn, t.empna, t.dept_kt, d.name_dept_name as depsn, t.joininsu, t.combsaly, t.note ";
		case QuitWork00RProgram.TONG_BHYT:
			return "select 'T"
					+ month
					+ "' as note_nv, t.empsn, t.empna, t.dept_kt, d.name_dept_name as depsn, t.ylbx, t.bu_bhyt, t.thu_bhyt, t.combsaly, t.note ";
		case QuitWork00RProgram.TONG_BHTN:
			return "select 'T"
					+ month
					+ "' as note_nv, t.empsn, t.empna, t.dept_kt, d.name_dept_name as depsn, t.bh_tnghiep, t.note ";
		case QuitWork00RProgram.TONG_BUBH:
			return "select 'T"
					+ month
					+ "' as note_nv, t.empsn, t.empna, t.dept_kt, d.name_dept_name as depsn, t.bonus9, t.note ";
		case QuitWork00RProgram.PDF_PHIEULUONG:
		case QuitWork00RProgram.PDF_PHIEULUONG_ATM:
		case QuitWork00RProgram.PDF_PHIEULUONG_0ATM:
			return "select t.dot_tv, 'T"
					+ month
					+ "/"
					+ year
					+ "' as note_nv, t.stt, t.ts as tts, t.empna, t.empsn, d.name_dept_name as depsn, "
					+ "d.name_fact || ' ' || d.name_group as note, t.bsaly, t.bonus2_goc, t.ducls + t.rest_pay as ducls, t.nucls, "
					+ "t.tbasic, t.addcls1+t.addcls1_o as addcls1, t.naddcls + t.naddcls_o as naddcls, "
					+ "t.addhol+t.addholn_o as addhol, t.taddcls, t.tadds, t.bonus2, t.bonus3, t.bonus4+t.bonus9 as bonus4, "
					+ "t.bonus7, t.bonus1, t.bonus1_hol, t.bonus6, t.bonus_acn, t.joininsu+t.bh_tnghiep as joininsu, t.ylbx, t.borm, t.bsal_avg, "
					+ "t.bonus2_avg, t.pn_conlai, t.pn_conlai_s, t.m_trocap as mm_trocap, t.d_nghitruoc as dd_nghitruoc, t.t_tctviec as tt_tctviec, "
					+ "t.paytax, t.kqt ";
		}

		return null;
	}

	public List<QuitWorkGenericObject> getGenericReport(String factCondition,
			Calendar dotTV, String type, String month, String year) {
		String dateStr = "";
		String typeStr = "";
		if (dotTV != null) {
			dateStr = "and t.dot_tv=to_date('"
					+ new SimpleDateFormat("dd/MM/yyyy")
							.format(dotTV.getTime()) + "','dd/mm/yyyy') ";
		}
		if (type != null) {
			if (type.equals("BV")) {
				typeStr = "and t.class='BV' ";
			} else {
				typeStr = "and t.class is null ";
			}
		}
		String suffix = "from attquit" + year + month + " t, n_department d "
				+ "where t.depsn=d.id_dept " + factCondition + dateStr
				+ typeStr + "group by t.dept_kt";
		String sql = "select t.dept_kt, sum(t.tincome)+sum(t.t_tctviec1) as tong, sum(t.tincome) as ttin, sum(t.ts_hientai2) as tsht, "
				+ "sum(t.t_tctviec1) as ttc, sum(t.borm) as tborm, sum(t.paytax) as tpay "
				+ suffix;
		String sql1 = "select t.dept_kt, count(t.empsn) te_bhyt, sum(t.ylbx) as tt_bhyt from attquit"
				+ year
				+ month
				+ " t, n_department d "
				+ "where t.depsn=d.id_dept "
				+ factCondition
				+ dateStr
				+ typeStr + "and t.ylbx>0 group by t.dept_kt";
		String sql2 = "select t.dept_kt, count(t.empsn) te_bhxh, sum(t.joininsu) as tt_bhxh from attquit"
				+ year
				+ month
				+ " t, n_department d "
				+ "where t.depsn=d.id_dept "
				+ factCondition
				+ dateStr
				+ typeStr + "and t.joininsu>0 group by t.dept_kt";
		String sql3 = "select t.dept_kt, count(t.empsn) te_bhtn, sum(t.bh_tnghiep) as tt_bhtn from attquit"
				+ year
				+ month
				+ " t, n_department d "
				+ "where t.depsn=d.id_dept "
				+ factCondition
				+ dateStr
				+ typeStr + "and t.bh_tnghiep>0 group by t.dept_kt";

		List<QuitWorkGenericObject> list = getSimpleJdbcTemplate().query(sql,
				new AttQuitGReportRowMapper());
		List<QuitWorkGenericObject> list1 = getSimpleJdbcTemplate().query(sql1,
				new AttQuitGReportRowMapper());
		List<QuitWorkGenericObject> list2 = getSimpleJdbcTemplate().query(sql2,
				new AttQuitGReportRowMapper());
		List<QuitWorkGenericObject> list3 = getSimpleJdbcTemplate().query(sql3,
				new AttQuitGReportRowMapper());

		for (int i = 0; i < list.size(); i++) {
			QuitWorkGenericObject data = list.get(i);
			for (int j = 0; j < list1.size(); j++) {
				QuitWorkGenericObject data1 = list1.get(j);
				if (data.equals(data1)) {
					data.setTE_BHYT(data1.getTE_BHYT());
					data.setTT_BHYT(data1.getTT_BHYT());
					break;
				}
			}
			for (int j = 0; j < list2.size(); j++) {
				QuitWorkGenericObject data2 = list2.get(j);
				if (data.equals(data2)) {
					data.setTE_BHXH(data2.getTE_BHXH());
					data.setTT_BHXH(data2.getTT_BHXH());
					break;
				}
			}
			for (int j = 0; j < list3.size(); j++) {
				QuitWorkGenericObject data3 = list3.get(j);
				if (data.equals(data3)) {
					data.setTE_BHTN(data3.getTE_BHTN());
					data.setTT_BHTN(data3.getTT_BHTN());
					break;
				}
			}
		}

		if (log.isDebugEnabled()) {
			log.debug(sql + " -> size: " + list.size());
			log.debug(sql1 + " > size: " + list1.size());
			log.debug(sql2 + " > size: " + list2.size());
			log.debug(sql3 + " > size: " + list3.size());
		}
		return list;
	}

}
