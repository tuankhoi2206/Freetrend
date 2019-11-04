package fv.util;

import java.util.Date;
import java.util.List;

/**
 * WorkPointsValidator
 * @author Hieu
 *
 */
public class WorkPointsValidator implements IWPValidator {
	private JdbcDAO dao;
	private WorkPointsValidator(){		
	}
	
	private JdbcDAO getDao(){
		if (dao==null) dao = new JdbcDAO();
		return dao;
	}
	

	@Override
	public int countWPLocked(Object factId, Object groupId, Object deptName, String months, String years, String fvlGroupCondition) {
		String sql = "select count(*) from n_get_data t, n_department d where t.depsn=d.id_dept"+
		" and t.months=? and t.years=? and t.locked=1";
		int dk=0;		
		if (factId!=null){
			sql = sql + " and d.name_fact=?";
			dk=1;
		}
		if (fvlGroupCondition!=null&&!fvlGroupCondition.equals("")){
			sql = sql + fvlGroupCondition;
		}else{
			if (groupId!=null){
				sql = sql + " and d.name_group=?";
				dk=2;
			}
			if (deptName!=null){
				sql = sql + " and d.name_dept_name=?";
				if (dk==2) dk=3;
				else dk=5;
			}
		}
		sql = sql + " and t.empsn in (select e.empsn from n_employee e where e.empsn=t.empsn and e.user_manage_id in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + ")";
		System.out.println("Locked: " + sql);
		Object[] params;
		if (dk==1) params = new Object[]{months, years, factId};
		else if (dk==2) params = new Object[]{months, years, factId, groupId};
		else if (dk==3) params = new Object[]{months, years, factId, groupId, deptName};
		else if (dk==5) params = new Object[]{months, years, factId, deptName};
		else params = new Object[]{months, years};
		return getDao().getJdbcTemplate().queryForInt(sql, params);
	}

	@Override
	public int countDeptWPLocked(String deptId, String months, String years) {
		String sql = "select count(*) from n_get_data t, n_department d where t.depsn=d.id_dept"+
		" and t.months=? and t.years=? and t.locked=1 and d.name_fact=?";
		return getDao().getJdbcTemplate().queryForInt(sql, new Object[] { months, years, deptId});
	}

	@Override
	public int countFactWPLocked(String factId, String months, String years) {
		String sql = "select count(*) from n_get_data t, n_department d where t.depsn=d.id_dept"+
		" and t.months=? and t.years=? and t.locked=1 and d.name_fact=?";
		return getDao().getJdbcTemplate().queryForInt(sql, new Object[] { months, years, factId});
	}

	@Override
	public int countGroupWPLocked(String factId, String groupId, String months, String years) {
		String sql = "select count(*) from n_get_data t, n_department d where t.depsn=d.id_dept"+
					" and t.months=? and t.years=? and t.locked=1 and d.name_fact=? and d.name_group=?";
		return getDao().getJdbcTemplate().queryForInt(sql, new Object[] { months, years, factId, groupId });
	}

	@Override
	public int countWPLocked(List<String> listEmpsn, String months, String years) {
		String sql = "select count(*) from n_get_data t where t.empsn in "
		+ DbUtils.parseInStringParamValues(listEmpsn)
		+ " and t.months=? and t.years=? and t.locked=1";
		return getDao().getJdbcTemplate().queryForInt(sql, new Object[] { months, years });		
	}

	@Override
	public boolean isDailyWPCompleted(String empsn, Date date) {
		return false;
	}

	@Override
	public boolean isMonthlyWPCompleted(String empsn, String months, String years) {
		return false;
	}

	@Override
	public boolean isWPLocked(String empsn, String months, String years) {
		String sql = "select count(*) from n_get_data t where t.empsn = ?"
			+ " and t.months=? and t.years=? and t.locked=1";
		boolean check = true;
		if (getDao().getJdbcTemplate().queryForInt(sql, new Object[] { empsn, months, years })==1){
			check =true;
		}
		else{
			check=false;
		}
		System.out.println("Check lock " + empsn + "-" + months + "/" + years + "=>" + check);
		return check;
	}

}
