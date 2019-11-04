package ds.program.fvhr.dao.generic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import fv.util.ApplicationHelper;
import fv.util.DbUtils;
import fv.util.JdbcDAO;
import fv.util.Vni2Uni;

public class FvGenericDAO extends JdbcDAO {
	private static FvGenericDAO INSTANSE;
	
	private FvGenericDAO(){
		super();
	}
	
	public static FvGenericDAO getInstanse(){
		if (INSTANSE==null) INSTANSE = new FvGenericDAO();
		return INSTANSE;
	}
	
	public boolean checkTableExist(String name){
		String sql = "select count(*) from user_tables where table_name=?";
		Long count = getSimpleJdbcTemplate().queryForLong(sql, new Object[]{name});
		return count.intValue()==1;
	}
	
	public void createTableFromOtherTable(String tableName, String otherTableName, String alter){
		String sql = "create table " + tableName + " as select * from " + otherTableName + " where 1<>1";
		try{
			getJdbcTemplate().execute(sql);
		}catch(Exception e){return;}
		if (alter!=null&&!alter.equals(""))
			getJdbcTemplate().execute(alter);
	}
	
	public String getEmployeeInfo(String empsn){
		String sql = "select e.fname||' '||e.lname as empna, d.name_dept from n_employee e, n_department d where e.depsn=d.id_dept and e.empsn=?";
		Object obj = getJdbcTemplate().query(sql, new Object[]{empsn}, new ResultSetExtractor(){
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()){
					return "Họ tên: " + Vni2Uni.convertToUnicode(rs.getString("EMPNA")) + " - Đơn vị: " + Vni2Uni.convertToUnicode(rs.getString("NAME_DEPT"));
				}
				return null;
			}
		});
		if (obj!=null) return obj.toString();
		else return null;
	}
	
	public String getFullName(String empsn){
		String sql = "select e.fname||' '||e.lname as empna from n_employee e where e.empsn=?";
		Object obj = getJdbcTemplate().query(sql, new Object[]{empsn}, new ResultSetExtractor(){
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()){
					return Vni2Uni.convertToUnicode(rs.getString("EMPNA"));
				}
				return null;
			}
		});
		if (obj!=null) return obj.toString();
		else return null;
	}
	
	/**
	 * 
	 * @param empsn
	 * @return empna, position, date_hired, name_dept
	 */
	public Object[] getEmployeeQuitInfo(String empsn){
		String sql = "select e.lname||' '||e.fname as empna, e.position, e.date_hired, d.name_dept from n_employee e, n_emp_quit q, n_department d where e.empsn=q.empsn and q.depsn=d.id_dept and e.empsn=?";
		Object[] obj = (Object[]) getJdbcTemplate().query(sql, new Object[]{empsn}, new ResultSetExtractor(){
			public Object[] extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()){
					return new Object[]{rs.getString(1), rs.getString(2), new java.util.Date(rs.getDate(3).getTime()), rs.getString(4)};
				}
				return null;
			}
		});
		return obj;
	}
	
	public String findShift(String empsn, Date date){
		String sql = "select t.id_shift from n_register_shift t where t.empsn=? and t.shift_date=?";
		String sql1 = "select t.shift from n_employee t where t.empsn=?";
		String obj = null;
		try{
			obj = getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[]{empsn, new java.sql.Date(date.getTime())});
		}catch(Exception e){
			try{
				obj = getSimpleJdbcTemplate().queryForObject(sql1, String.class, new Object[]{empsn});
			}catch(Exception e1){}
		}
		return obj;
	}
	
	public synchronized boolean isWorking(String empsn, boolean includeQuiter) {
		String sql = "select empsn from n_employee where empsn='" + empsn + "'";
		if (!includeQuiter) sql = sql + " and depsn<>'00000'";
//		System.out.println(sql);
		String emp = null;
		try {
			emp = getSimpleJdbcTemplate().queryForObject(sql, String.class);
		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
		if (emp != null && !emp.equals("")) {
			return true;
		}
		return false;
	}
	
	public synchronized int checkLock(List<String> listEmpsn, String months, String years) {
		String sql = "select count(*) from n_get_data t where t.empsn in "
				+ DbUtils.parseInStringParamValues(listEmpsn)
				+ " and t.months=? and t.years=? and t.locked=1";
		return getJdbcTemplate().queryForInt(sql,
				new Object[] { months, years });
	}
	
	public synchronized List<String> getCurrentEmpsnList(Object fact, Object group, Object deptName, String fvlGroupCondition){
		String sql = "select e.empsn from n_employee e, n_department d" +
				" where e.depsn=d.id_dept and d.name_fact='" + fact + "'";
		if (fvlGroupCondition.equals("")){
			if (group!=null&&!group.equals("")){
				sql = sql + " and d.name_group='" + group + "'";
			}
			if (deptName!=null&&!deptName.equals("")){
				sql = sql + " and d.name_dept_name='" + deptName + "'";
			}
		}else{
			sql = sql + fvlGroupCondition;
		}
		sql = sql + " and e.user_manage_id in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList());
		System.out.println("Current: " + sql);
		return getJdbcTemplate().queryForList(sql, new Object[]{}, String.class);
	}
	
	public synchronized List<String> getEmpsnList(String months, String years, Object fact, Object group, Object deptName, String fvlGroupCondition){
		String sql = "select t.empsn from n_get_data t, n_department d" +
				" where t.depsn=d.id_dept and t.months='" + months + "' and t.years='" + years + "' and d.name_fact='" + fact + "'";
		if (fvlGroupCondition.equals("")){
			if (group!=null&&!group.equals("")){
				sql = sql + " and d.name_group='" + group + "'";
			}
			if (deptName!=null&&!deptName.equals("")){
				sql = sql + " and d.name_dept_name='" + deptName + "'";
			}
		}else{
			sql = sql + fvlGroupCondition;
		}
		sql = sql + " and t.empsn in (select e.empsn from n_employee e where e.empsn=t.empsn and e.user_manage_id in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + ")";
		System.out.println("By month/year: " + sql);
		return getJdbcTemplate().queryForList(sql, new Object[]{}, String.class);
	}
	
	public synchronized List<String> getQuitList(Date from, Date to, Object fact, Object group, Object deptName, String fvlGroupCondition){
		String sql = "select distinct t.empsn from n_emp_quit t, n_department d" +
				" where t.depsn=d.id_dept and t.real_off_date>=? and t.real_off_date<=? and d.name_fact='"+fact+"'";
		if (fvlGroupCondition.equals("")){
			if (group!=null&&!group.equals("")){
				sql = sql + " and d.name_group='" + group + "'";
			}
			if (deptName!=null&&!deptName.equals("")){
				sql = sql + " and d.name_dept_name='" + deptName + "'";
			}
		}else{
			sql = sql + fvlGroupCondition;
		}
		sql = sql + " and t.empsn in (select e.empsn from n_employee e where e.empsn=t.empsn and e.user_manage_id in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + ")";
		System.out.println("Quit list: " + sql);
		return getJdbcTemplate().queryForList(sql, new Object[]{new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime())}, String.class);
	}
	
	public synchronized List<String> get7HList(boolean isCurrent, Date from, Date to, Object fact, Object group, Object deptName, String fvlGroupCondition){
		String sql;
		if (isCurrent)
			sql = "select distinct t.empsn from n_register_shift t"+
					 " where t.shift_date>=? and t.shift_date<=? and t.note like '7H%' and t.empsn in "+
					 "(select e.empsn from n_employee e, n_department d where t.empsn=e.empsn and e.depsn=d.id_dept and d.name_fact='" + fact + "'";
		else
			sql = "select distinct t.empsn from n_register_shift t, n_get_data a where t.empsn=a.empsn"+
					 " and t.shift_date>=? and t.shift_date<=? and t.note like '7H%' and a.depsn in " +
					 "(select d.id_dept from n_employee e, n_department d where a.depsn=d.id_dept and a.empsn=e.empsn and d.name_fact='" + fact + "'";
		if (fvlGroupCondition.equals("")){
			if (group!=null&&!group.equals("")){
				sql = sql + " and d.name_group='" + group + "'";
			}
			if (deptName!=null&&!deptName.equals("")){
				sql = sql + " and d.name_dept_name='" + deptName + "'";
			}
		}else{
			sql = sql + fvlGroupCondition;
		}
		sql = sql + " and e.user_manage_id in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + ")";
		System.out.println("VS: " + sql + "[" + from + "," + to + "]");
		return getJdbcTemplate().queryForList(sql, new Object[]{new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime())}, String.class);
	}	
	
	
}
