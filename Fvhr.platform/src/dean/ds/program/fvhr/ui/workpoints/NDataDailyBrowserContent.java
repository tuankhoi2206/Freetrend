package ds.program.fvhr.ui.workpoints;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import fv.components.MrBeanBrowserContent;

public class NDataDailyBrowserContent extends MrBeanBrowserContent {
	private static final long serialVersionUID = 1L;

	private Calendar from;
	private Calendar to;	
//	private String factCondition;
	private String sql;
	private Object[] params;
	private NDataDailyPMProgram program;

	public NDataDailyBrowserContent(NDataDailyPMProgram program) {
		this.program=program;
	}

	@Override
	public Class<?> getBean() {
		return N_DATA_DAILY_EX.class;
	}

	public Calendar getFrom() {
		return from;
	}

	public void setFrom(Calendar from) {
		this.from = from;
	}

	public Calendar getTo() {
		return to;
	}

	public void setTo(Calendar to) {
		this.to = to;
	}

	@Override
	public Map<String, String> getColumnHeaderMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("EMPSN", "N_EMPLOYEE.EMPSN");
		map.put("EMPNA", "N_EMPLOYEE.EMPNA");
		map.put("DEPT_NAME", "N_DEPARTMENT.NAME_DEPT");
		map.put("ID_SHIFT", "N_DATA_DAILY.ID_SHIFT");
		map.put("DATES", "N_DATA_DAILY.DATES");
		map.put("TT_IN", "N_DATA_DAILY.T_IN");
		map.put("TT_MID", "N_DATA_DAILY.T_MID");
		map.put("TT_OUT", "N_DATA_DAILY.T_OUT");
		map.put("TT_OVER", "N_DATA_DAILY.T_OVER");
		map.put("NOTE", "N_DATA_DAILY.NOTE");
		return map;
	}

	@Override
	public void doDataContentRefresh() {		
//		Object[] params;
//
//		sql = 
//			"select t.empsn, e.fname||' '||e.lname as empna, d.name_dept as dept_name, e.empcn, t.dates, t.t_in as tt_in, " +
//			"t.t_mid as tt_mid, t.t_out as tt_out, t.t_over as tt_over, t.note, t.id_shift " +
//			"from n_data_daily t, n_employee e, n_department d where t.empsn=e.empsn and e.depsn=d.id_dept ";
//		if (to!=null){
//			sql = sql + "and t.dates>=? and t.dates<=? ";
//			params = new Object[]{new java.sql.Date(from.getTimeInMillis()), new java.sql.Date(to.getTimeInMillis())};
//		}else{
//			sql = sql + "and t.dates=? ";
//			params = new Object[]{new java.sql.Date(from.getTimeInMillis())};
//		}
//		sql = sql + " and t.check_data='N' and " + factCondition+ " order by d.name_dept";
		List<N_DATA_DAILY_EX> list = program.getDao().getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<N_DATA_DAILY_EX>(){
			@Override
			public N_DATA_DAILY_EX mapRow(ResultSet rs, int arg1) throws SQLException {
				N_DATA_DAILY_EX data = new N_DATA_DAILY_EX();
				int n = rs.getMetaData().getColumnCount();
				for (int i=0;i<n;i++){
					String colName = rs.getMetaData().getColumnLabel(i+1);
					try {
						PropertyUtils.setProperty(data, colName, rs.getObject(colName));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
				return data;
			}
		}, params);
		System.out.println(sql + " --> size: " + list.size());
		setListData(list);

	}

//	public void setFactCondition(String factCondition) {
//		this.factCondition=factCondition;
//	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}	
}
