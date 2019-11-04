package ds.program.fvhr.dao.salary;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.time.StopWatch;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.object.StoredProcedure;
import ds.program.fvhr.dao.DefaultSalaryDAO;
import ds.program.fvhr.domain.ATT200000;
import ds.program.fvhr.domain.ATTENDANTDB;
import ds.program.fvhr.obj.salary.SalaryExcelData;
import ds.program.fvhr.dao.salary.UpdateAttendantdbStoredProcedure;
import ds.program.fvhr.dao.wp.ICDataProcessStoredProcedure;
import fv.util.CustomDomainUtils;
import fv.util.DateUtils;
import fv.util.DbUtils;

public class SalaryDAO extends DefaultSalaryDAO<ATT200000, ATTENDANTDB, String> {

	public SalaryDAO() {
		super();
	}

	public SalaryDAO(String month, String year) {
		setMonth(month);
		setYear(year);
	}

	@Override
	public boolean checkAttendantExist(String pk) {
		String sql = "SELECT T.EMPSN FROM ATTENDANTDB T WHERE T.EMPSN=?";
		String sn = null;
		try {
			sn = (String) getJdbcTemplate().queryForObject(sql,
					new Object[] { pk }, String.class);
		} catch (DataAccessException e) {
			return false;
		}
		if (sn != null)
			return true;
		return false;
	}

	@Override
	public boolean checkAttExist(String pk) {
		String table = "ATT" + getYear() + getMonth();
		String sql = "SELECT T.EMPSN FROM " + table + " T WHERE T.EMPSN=?";
		String sn = null;
		try {
			sn = (String) getJdbcTemplate().queryForObject(sql,
					new Object[] { pk }, String.class);
		} catch (DataAccessException e) {
			return false;
		}
		if (sn != null)
			return true;
		return false;
	}

	@Override
	public ATTENDANTDB collectDataFromDB(String empsn) {
		// get IC data
		// -->ICDataTransfer.transfer(N_GET_DATA data);
		// get Primary salary data
		return null;
	}

	@Override
	public ATTENDANTDB getAttendantData(String empsn) {
		String sql = "SELECT DISTINCT * FROM ATTENDANTDB_QUIT T WHERE T.EMPSN=?";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection con = getConnection();
		try {
			pstm = con.prepareStatement(sql.toString());
			System.out.println(sql.toString());
			pstm.setString(1, empsn);
			rs = pstm.executeQuery();
			if (rs.next()) {
				Map<String, String> headerMap = CustomDomainUtils
						.getHeaderColumnMap(ATTENDANTDB.class);
				ATTENDANTDB att = new ATTENDANTDB();
				extractAttendantData(att, headerMap, rs);
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

	@Override
	public ATT200000 getAttData(String empsn) {
		String sql = "SELECT * FROM ATT" + getYear() + getMonth()
				+ " WHERE EMPSN=?";
		try{
			ATT200000 att = (ATT200000) getJdbcTemplate().queryForObject(sql,
					new Object[] { empsn }, new AttRowMapper(null));
			return att;
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public void calculate(String empsn) {
	}

	@Override
	public final List<ATT200000> getAttQuitList(Map<String, String> columnHeaderMap,
			String fact, String type, Date date) {
		throw new RuntimeException("Can not implement this method");
	}

	@Override
	public List<ATT200000> getAttList(Map<String, String> columnHeaderMap, String fact) {
		List<ATT200000> list = new ArrayList<ATT200000>();
		if (fact == null || fact.trim().equals(""))
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

		sql.append(" FROM ATT").append(getYear()).append(getMonth()).append(
				" A, N_DEPARTMENT D WHERE A.DEPSN=D.ID_DEPT");
		sql.append(fact);
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection con = getConnection();
		try {
			pstm = con.prepareStatement(sql.toString());
			System.out.println("Salary: " + sql.toString());
			rs = pstm.executeQuery();
			while (rs.next()) {
				ATT200000 att = new ATT200000();
				extractData(att, columnHeaderMap, rs);
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

	/*
	 * public int transferICData(N_GET_DATA data){ String sql = ""; if
	 * (checkAttExist(data.getEMPSN())){ //update sql = "update attendantdb t
	 * set t.ducls=?,t.nucls=?," + "t.addhol=?,
	 * t.addholn=?,t.addcls1=?,t.naddcls=?,t.acnm=?,t.sign_time=?," +
	 * "t.late=?,t.rest=?,t.rest_pay=?,t.rest_sick=?,t.other=?,t.nwhour=?," +
	 * "t.phep_ns=?,t.acn=? where t.empsn=?"; }else{ //insert sql = "insert into
	 * attendantdb
	 * (ducls,nucls,addhol,addholn,addcls1,naddcls,acnm,sign_time,late" +
	 * ",rest,rest_pay,rest_sick,other,nwhour,phep_ns,acn,empsn) values " +
	 * "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; } Object[] params = new
	 * Object[17]; params[0]=data.getDUCLS(); params[1]=data.getNDUCLS();
	 * params[2]=data.getADDHOL(); params[3]=data.getADDHOLN();
	 * params[4]=data.getADDCLS1(); params[5]=data.getNADDCLS();
	 * params[6]=data.getACNM(); params[7]=data.getSIGN();
	 * params[8]=data.getLATE(); params[9]=data.getREST();
	 * params[10]=data.getREST_PAY(); params[11]=data.getREST_SICK();
	 * params[12]=data.getOTHER(); params[13]=data.getNWHOUR();
	 * params[14]=data.getLMATER(); params[15]=data.getACN();
	 * params[16]=data.getEMPSN(); return getJdbcTemplate().update(sql, params); }
	 */

	public void transferICData(String empsn) {
		StoredProcedure sp = new UpdateAttendantdbStoredProcedure(
				getDataSource());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("THANG", getMonth());
		params.put("NAM", getYear());
		params.put("ST", empsn);
		sp.execute(params);
	}

	public void updateSalaryData(String empsn) {
		StoredProcedure sp = new UpdatePrimarySalaryDataProcedure(
				getDataSource());
		Date date = DateUtils.getLastDay(Integer.valueOf(getMonth()), Integer
				.valueOf(getYear()));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("SOTHE", empsn);
		params.put("THANG", date);
		sp.execute(params);
	}

	public Map getPrimarySalaryCollector(String empsn) {
		StoredProcedure sp = new SalaryDataCollectorProcedure(getDataSource());
		Date date = DateUtils.getLastDay(Integer.valueOf(getMonth()), Integer
				.valueOf(getYear()));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("SOTHE", empsn);
		params.put("THANG", date);
		return sp.execute(params);
	}

	public void transferICData(List<String> listEmpsn) {
		StopWatch sw = new StopWatch();
		sw.reset();
		sw.start();
		for (String empsn : listEmpsn) {
			transferICData(empsn);// update ic data to attendantdb
			updateSalaryData(empsn);// update primary salary data to attendantdb
		}
		sw.stop();
		System.out.println("Thoi gian chuyen du lieu: " + (float) sw.getTime()
				/ 1000 + "s");
	}
	
	public void processData(List<String> listEmpsn, String dateStr, String type){
		for (String empsn:listEmpsn){
			StoredProcedure sp = new ICDataProcessStoredProcedure(getDataSource());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ST", empsn);
			params.put("THOIGIAN", dateStr);
			params.put("TT", type);
			sp.execute(params);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getEmpList(String factCondition) {
		String sql = "select t.empsn from n_get_data t, n_employee e, n_department d"
				+ " where t.empsn=e.empsn and e.depsn=d.id_dept"
				+ factCondition + " and t.months=? and t.years=?";
		return getJdbcTemplate().queryForList(sql,
				new Object[] { getMonth(), getYear() }, String.class);
	}

	public int checkLock(String factCondition) {
		String sql = "select count(*) from n_get_data t, n_employee e, n_department d"
				+ " where t.empsn=e.empsn and e.depsn=d.id_dept"
				+ factCondition
				+ " and t.months=? and t.years=? and t.locked=1";
		return getJdbcTemplate().queryForInt(sql,
				new Object[] { getMonth(), getYear() });
	}

	public int checkLock(List<String> listEmpsn) {
		String sql = "select count(*) from n_get_data t" + " where t.empsn in "
				+ DbUtils.parseInStringParamValues(listEmpsn)
				+ " and t.months=? and t.years=? and t.locked=1";
		return getJdbcTemplate().queryForInt(sql,
				new Object[] { getMonth(), getYear() });
	}

	public boolean checkEmpsn(String empsn) {
		String sql = "select empsn from n_employee where empsn='" + empsn
				+ "' and depsn<>'00000'";
		String emp = null;
		try {
			emp = (String) getJdbcTemplate().queryForObject(sql, String.class);
		} catch (Exception e) {
			return false;
		}
		if (emp != null && !emp.equals("")) {
			return true;
		}
		return false;
	}

	public String getEmpna(String empsn) {
		String sql = "select trim(e.fname)||' '||trim(e.lname) from n_employee e where e.empsn='"
				+ empsn + "'";
		String name = null;
		try {
			name = (String) getJdbcTemplate().queryForObject(sql, String.class);
			return name;
		} catch (Exception e) {
			return "";
		}
	}

	public List<SalaryExcelData> getPreAttList(String fact) {
		List<SalaryExcelData> list = new ArrayList<SalaryExcelData>();
		List<String> empList = getEmpList(" and d.name_fact='TB'");
		for (String emp : empList) {
			SalaryExcelData data = new SalaryExcelData();
			Map spdata = getPrimarySalaryCollector(emp);
			Iterator ite = spdata.keySet().iterator();
			while (ite.hasNext()) {
				String key = ite.next().toString();
				try {
					PropertyUtils.setProperty(data, key, spdata.get(key));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
			data.setEMPSN(emp);
			data.setEMPNA(getEmpna(emp));
			list.add(data);
		}
		return list;
	}

//	public List<ICMasterData> getDataList(List<String> list) {
//		String sql = 
//			"select t.empsn, t.months, t.years, t.ducls, t.nducls, t.addhol, t.addcls1, t.naddcls, t.late, t.sign, "+ 
//			"t.acn, t.acnm, t.rest, t.rest_pay, t.rest_sick, t.other, t.nwhour, t.addholn, t.lmater, e.fname || ' ' || e.lname as fullname, e.date_hired, e.shift, " +
//			"e.position, d.name_dept, e.empcn from n_get_data t, n_employee e, n_department d " + 
//			"where t.empsn=e.empsn and e.depsn=d.id_dept and t.empsn in " + DbUtils.parseInStringParamValues(list) +
//			" and t.months=? and t.years=?";
//		System.out.println("Main query: " + sql);
//		return getJdbcTemplate().query(sql, new Object[] { getMonth(), getYear() }, new NGetDataExtendRowMapper());
//	}
	/**
	 * Data
	 */
	public Map<String, Object> getObserveData(String empsn, String month, String year){
		Date lastDay = DateUtils.getLastDay(month, year);
		Date firstDay= DateUtils.getFirstDay(month, year);
		
		/*String sql = 
			"select t.empna, e.date_hired, t.depsn, d.name_dept, t.bsaly, t.combsaly, t.possn, t.bonus2,\n" +
			"e.birthday, e.birthplace, e.permanent_address, e.empcn, e.education, e.id_no, e.id_place, e.ngaycap_id,\n" + 
			"e.ethnic, e.religion, e.city, e.contact_address, e.sex, t.joininsu, t.ylbx,\n" + 
			"(select c.code_tax from n_emp_taxcode c where c.empsn=t.empsn and c.status_code='1') as code_tax,\n" + 
			"(select c.dates from n_emp_taxcode c where c.empsn=t.empsn and c.status_code='1') as date_codetax,\n" + 
			"(select j.name_job||' ('||j.kind_job||')' from n_job j where j.id_key=son_get_job_id_for_emp(e.empsn,?)) as job,\n" + 
			"t.bonus1, t.bonus3, (select min(s.dates) from n_social s where s.empsn=e.empsn) as social_date,\n" + 
			"(select id_social from n_social s where s.empsn=e.empsn and s.clock='1') as id_social,\n" + 
			"(select id_health from n_health h where h.empsn=e.empsn and h.clock='1') as id_health,\n" + 
			"(select k.date_p from n_kyluat k where k.empsn=e.empsn and k.id_phat='02') as date_p,\n" + 
			"(select k.date_hl from n_kyluat k where k.empsn=e.empsn and k.id_phat='02') as date_hl,\n" + 
			"(select l.date_s from n_labour l where t.empsn=l.empsn and l.clock='1') as labour_date,\n" + 
			"(select l.limit from n_labour l where t.empsn=l.empsn and l.clock='1') as labour_limit,\n" + 
			"(select l.expire from n_labour l where t.empsn=l.empsn and l.clock='1') as labour_expire,\n" + 
			"(select l.times from n_labour l where t.empsn=l.empsn and l.clock='1') as labour_time,\n" + 
			"(select b.b_dates from n_time_bear b where t.empsn=b.empsn and b.b_dates<=? and b.e_dates>=?) as b_date,\n" + 
			"(select b.e_dates from n_time_bear b where t.empsn=b.empsn and b.b_dates<=? and b.e_dates>=?) as e_date,\n" + 
			"(select max(q.real_off_date) from n_emp_quit q where q.empsn=t.empsn and q.real_off_date>=to_date('01/"+month+"/"+year+"','dd/mm/yyyy')) as real_off_date\n" + 
			"from n_employee e, n_department d, att"+year+month+" t\n" + 
			"where e.empsn='"+empsn+"' and d.id_dept=t.depsn and t.empsn=e.empsn";*/
		String sql = 
				"select e.fname||' '||e.lname as empna, e.date_hired, d.id_dept as depsn, d.name_dept,\n" +
						"e.birthday, e.birthplace, e.permanent_address, e.empcn, e.education, e.id_no, e.id_place, e.ngaycap_id,\n" + 
						"e.ethnic, e.religion, e.city, e.contact_address, e.sex, e.position as possn,\n" + 
						"(select c.code_tax from n_emp_taxcode c where c.empsn=t.empsn and c.status_code='1') as code_tax,\n" + 
						"(select c.dates from n_emp_taxcode c where c.empsn=t.empsn and c.status_code='1') as date_codetax,\n" + 
						"(select j.name_job||' ('||j.kind_job||')' from n_job j where j.id_key=son_get_job_id_for_emp(e.empsn,?)) as job,\n" + 
						"bonus1_by_date(e.empsn,?)as bonus1,\n" + 
						"bonus3_by_date(e.empsn,?)as bonus3,\n" + 
//						"get_bhxh(e.empsn,?) as joininsu,\n" + 
//						"get_bhyt(e.empsn,?) as ylbx,\n" + 
						"bsaly_by_date(e.empsn,?) as bsaly,\n" + 
						"comsaly_by_date(e.empsn,?,bsaly_by_date(e.empsn,?)) as combsaly,\n" + 
						"bonus2_by_date(e.empsn,?) as bonus2,\n" + 
						"(select min(s.dates) from n_social s where s.empsn=e.empsn) as social_date,\n" + 
						"(select id_social from n_social s where s.empsn=t.empsn and s.clock='1') as id_social,\n" + 
						"(select id_health from n_health h where h.empsn=t.empsn and h.clock='1') as id_health,\n" + 
						"(select min(k.date_p) from n_kyluat k where k.empsn=e.empsn and k.id_phat='02' and (k.date_p>=? or k.date_hl>=?)) as date_p,\n" + 
						"(select max(k.date_hl) from n_kyluat k where k.empsn=e.empsn and k.id_phat='02' and (k.date_p>=? or k.date_hl>=?)) as date_hl,\n" + 
						"(select l.date_s from n_labour l where t.empsn=l.empsn and l.clock='1') as labour_date,\n" + 
						"(select l.limit from n_labour l where t.empsn=l.empsn and l.clock='1') as labour_limit,\n" + 
						"(select l.expire from n_labour l where t.empsn=l.empsn and l.clock='1') as labour_expire,\n" + 
						"(select l.times from n_labour l where t.empsn=l.empsn and l.clock='1') as labour_time,\n" + 
						"(select b.b_dates from n_time_bear b where t.empsn=b.empsn and b.b_dates<=? and b.e_dates>=?) as b_date,\n" + 
						"(select b.e_dates from n_time_bear b where t.empsn=b.empsn and b.b_dates<=? and b.e_dates>=?) as e_date,\n" + 
						"(select max(q.real_off_date) from n_emp_quit q where q.empsn=t.empsn and q.real_off_date>=to_date('01/"+month+"/"+year+"','dd/mm/yyyy')) as real_off_date\n" + 
						"from n_employee e, n_department d, n_get_data t\n" + 
						"where e.empsn=? and d.id_dept=t.depsn and t.empsn=e.empsn\n" + 
						"and t.months=? and t.years=?";

		
		try{
			Map<String, Object> data = getSimpleJdbcTemplate().queryForMap(sql, new Object[]{lastDay,lastDay,lastDay,lastDay,lastDay,lastDay,lastDay,firstDay,firstDay,firstDay,firstDay,lastDay,lastDay,lastDay,lastDay,empsn,month,year});
			return data;
		}catch(Exception e){
			System.out.println(sql);
			e.printStackTrace();
			return null;
		}
	}
}
