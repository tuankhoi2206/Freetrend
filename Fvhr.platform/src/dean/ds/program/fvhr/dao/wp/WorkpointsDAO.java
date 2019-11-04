package ds.program.fvhr.dao.wp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;
import ds.program.fvhr.dao.wp.NGetDataExtendRowMapper;
import ds.program.fvhr.dao.salary.UpdateAttendantdbStoredProcedure;
import ds.program.fvhr.dao.salary.UpdatePrimarySalaryDataProcedure;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.ui.hrdata.ICMasterData;
import fv.util.DateUtils;
import fv.util.DbUtils;
import fv.util.FvLogger;
import fv.util.JdbcDAO;

/**
 * HR data - combine Hibernate IGenericDAO
 * 
 * @author Hieu
 * 
 */
public class WorkpointsDAO extends JdbcDAO {
	private String month;

	private String year;
	
//	private UpdateAttendantdbStoredProcedure updateAttSP;
//	
//	private UpdatePrimarySalaryDataProcedure updateSalarySP;
//
//	private ICDataProcessStoredProcedure icProcessSP;

	public WorkpointsDAO(String month, String year) {
		this.month = month;
		this.year = year;
	}

	public WorkpointsDAO() {
	}
	
	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getMonth(){
		return this.month;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getYear(){
		return this.year;
	}

	/**
	 * OverTimeDataCount
	 * 
	 * @param factCondition -
	 *            ex: " and d.name_fact='TB'"
	 * @param fromDate
	 * @param toDate
	 * @param locked
	 * @return
	 */
	public int getOverTimeDataCount(String factCondition, Calendar fromDate,
			Calendar toDate, boolean locked) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String sd = sdf.format(fromDate.getTime());
		String ed = sdf.format(toDate.getTime());
		String lok = "";
		if (locked) {
			lok = "t.locked='Y'";
		} else {
			lok = "(t.locked is null or t.locked='N')";
		}
		String sql = "select count(*) from n_register_overtime t, n_employee e, n_department d"
				+ " where e.depsn=d.id_dept and t.empsn=e.empsn "
				+ factCondition
				+ " and t.date_over>=to_date('"
				+ sd
				+ "','dd/mm/yyyy') and t.date_over<=to_date('"
				+ ed
				+ "','dd/mm/yyyy')" + " and " + lok;
		System.out.println(sql);
		return getJdbcTemplate().queryForInt(sql);
	}

	public List<String> getEmpsnList(String factCondition) {
		List<String> list;
		String sql = "select t.empsn from n_get_data t, n_employee e, n_department d"
				+ " where t.empsn=e.empsn and e.depsn=d.id_dept "
				+ factCondition
				+ " and t.months=? and t.years=? and t.locked=1";
		list = getJdbcTemplate().queryForList(sql,
				new Object[] { month, year }, String.class);
		return list;
	}

	public String[] getRegisterWorkingTime(String empsn, Date date){
		EmployeeWorkingTimeSP sp = new EmployeeWorkingTimeSP(getDataSource());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("EMPSN", empsn);
		params.put("SDATE", new java.sql.Date(date.getTime()));
		Map ret = sp.execute(params);
		return new String[]{ret.get("TIME_IN").toString(), ret.get("TIME_OUT").toString()};
	}
	
	public String[] getRealWorkingTime(String empsn, Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String sd = sdf.format(date);
		String sql = 
			"select d.times from n_data_main d where d.empcn= " +
			"(select t.empcn from n_emp_iccard t where " + 
			"t.begin_date=(select max(a.begin_date) from n_emp_iccard a where a.empsn=t.empsn and a.begin_date<=?) and " + 
			"t.empsn=?) and d.dates=?";
		try{
			String time = getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[]{new java.sql.Date(date.getTime()), empsn, sd});
			return splitICTimes(time);
		}catch(Exception e){//not found
			return null;
		}
	}
	
	private String[] splitICTimes(String times){
		int t,t1=0,t2=0;
		String temp;
		int len = times.length()/4;
		for (int i=0;i<len;i++){
			temp=times.substring(i*4, i*4+4);
			t=Integer.parseInt(temp);
			if (i==0){
				t1=t2=t;
			}else{
				if (t1<t) t1=t;
				if (t2>t) t2=t;
			}
		}
		return new String[]{timeStringFromNumber(t1),timeStringFromNumber(t2)};
	}
	
	private String timeStringFromNumber(int t){
		int t1=t/100;
		int t2=t%100;
		String s1 = t1<10?"0"+t1:""+t1;
		String s2 = t2<10?"0"+t2:""+t2;
		return s1+":"+s2;
	} 
	
	/**
	 * FIXME DRY (system design)
	 * @param listEmpsn
	 */
	public void transferICData(List<String> listEmpsn) {
		StopWatch sw = new StopWatch();
		sw.reset();
		sw.start();
		for (int i=0;i<listEmpsn.size();i++) {
			String empsn = listEmpsn.get(i);
			transferICData(empsn);// update ic data to attendantdb
//			updateSalaryData(empsn);// update primary salary data to attendantdb
			if (i%5000==0) System.gc();//try clean memory
		}
		sw.stop();
		System.out.println("Thoi gian chuyen du lieu: " + (float) sw.getTime()
				/ 1000 + "s");
	}
	
	public void updateSalaryData(String empsn) {
		System.out.println("updateSalaryData: " + empsn);
		UpdatePrimarySalaryDataProcedure updateSalarySP = new UpdatePrimarySalaryDataProcedure(getDataSource());
		Date date = DateUtils.getLastDay(Integer.valueOf(getMonth()), Integer
				.valueOf(getYear()));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("SOTHE", empsn);
		params.put("THANG", date);
		updateSalarySP.execute(params);
	}
	
	public void transferICData(String empsn) {
		UpdateAttendantdbStoredProcedure updateAttSP = new UpdateAttendantdbStoredProcedure(getDataSource());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("THANG", getMonth());
		params.put("NAM", getYear());
		params.put("ST", empsn);
		updateAttSP.execute(params);
	}
	
	public void processData(List<String> listEmpsn, String dateStr, String type){
		for (String empsn:listEmpsn){
			processData(empsn, dateStr, type);
		}
	}
	
	public void processData(String empsn, String dateStr, String type){
		ICDataProcessStoredProcedure icProcessSP = new ICDataProcessStoredProcedure(getDataSource());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ST", empsn);
		params.put("THOIGIAN", dateStr);
		params.put("TT", type);
		icProcessSP.execute(params);
		saveProcessAction(empsn, dateStr);
	}
	
	public void saveProcessAction(String empsn, String dateStr){
		N_ACTION_DAILY action = new N_ACTION_DAILY();
		action.setEMPSN(empsn);
		action.setACTIONNAME("UPDATE");
		action.setNOTE("XLDL THANG " + dateStr);
		action.setTABLENAME("N_GET_DATA");
		FvLogger.log(action);
	}
	
	public List<ICMasterData> getDataList(String factCondition){
		String sql = 
			"select t.empsn, t.months, t.years, t.ducls, t.nducls, t.addhol, t.addcls1, t.naddcls, t.late, " +
			"t.sign, t.acn, t.acnm, t.rest, t.rest_pay, t.rest_sick, t.other, t.nwhour, t.addholn, t.lmater, " + 
			"e.fname || ' ' || e.lname as fullname, e.date_hired, e.shift, e.position, e.empcn, (select d.name_dept from n_department d where d.id_dept=t.depsn) as name_dept " + 
			"from n_get_data t, n_employee e where e.empsn=t.empsn " + 
			"and t.months=? and t.years=? " + 
			"and t.depsn in (select d.id_dept from n_department d where t.depsn=d.id_dept and " + factCondition + ")" +
			" order by t.empsn asc ";

		System.out.println("Main query: " + sql);
		return getJdbcTemplate().query(sql, new Object[] { getMonth(), getYear() }, new NGetDataExtendRowMapper());
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
	
	public List<ICMasterData> getDataList(List<String> list) {
		String sql = 
			"select t.empsn, t.months, t.years, t.ducls, t.nducls, t.addhol, t.addcls1, t.naddcls, t.late, t.sign, "+ 
			"t.acn, t.acnm, t.rest, t.rest_pay, t.rest_sick, t.other, t.nwhour, t.addholn, t.lmater, e.fname || ' ' || e.lname as fullname, e.date_hired, e.shift, " +
			"e.position, d.name_dept, e.empcn from n_get_data t, n_employee e, n_department d " +
			//Update 30/08/2013, HA lay don vi theo get_data
			//"where t.empsn=e.empsn and e.depsn=d.id_dept and t.empsn in " + DbUtils.parseInStringParamValues(list) +
			"where t.empsn=e.empsn and t.depsn=d.id_dept and t.empsn in " + DbUtils.parseInStringParamValues(list) +
			" and t.months=? and t.years=?\n"+
			" order by t.empsn asc ";
		System.out.println("Main query: " + sql);
		return getJdbcTemplate().query(sql, new Object[] { getMonth(), getYear() }, new NGetDataExtendRowMapper());
	}
	
	public void saveCurrentDepsn(List<String> list, String months, String years){
		String sql = "update n_get_data t set t.depsn=(select e.depsn from n_employee e where e.empsn=t.empsn) where t.empsn=? and t.months=? and t.years=? and (t.dept_status<>'Y' or t.dept_status is null)";
		int t=0;
		for (String empsn:list){
			int up = getJdbcTemplate().update(sql, new Object[]{empsn, months, years});
			t+=up;
		}
		System.out.println("Total up: " + t);
	}
	
	public String getEmpcn(String empsn){
		String sql = "select t.empcn from n_emp_iccard t where t.empsn=? and t.use_status='1' and rownum<2";
		try{
			String empcn = getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[]{empsn});
			return empcn;
		}catch(Exception e){}
		return "";
	}
}
