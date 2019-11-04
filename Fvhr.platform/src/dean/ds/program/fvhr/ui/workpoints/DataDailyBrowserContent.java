package ds.program.fvhr.ui.workpoints;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;

import ds.program.fvhr.domain.N_DATA_DAILY;
import fv.components.MrBeanBrowserContent;
import fv.util.JdbcDAO;

public class DataDailyBrowserContent extends MrBeanBrowserContent{
	private static final long serialVersionUID = 1L;
	
	private String empsn;
	private String dateString;
	private JdbcDAO dao;
	
	public DataDailyBrowserContent() {
		
	}

	@Override
	public Class<?> getBean() {
		return N_DATA_DAILY.class;
	}
	
	public JdbcDAO getDao(){
		if (dao==null) dao = new JdbcDAO();
		return this.dao;
	}

	@Override
	public Map<String, String> getColumnHeaderMap() {
		Map<String, String> map = new HashMap<String, String>();
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
		String sql = "select t.dates, t.t_in as tt_in, t.t_mid as tt_mid, t.t_out as tt_out, t.t_over as tt_over, t.note from " +
				"n_data_daily t where t.empsn=? and to_char(t.dates,'mm/yyyy')=?";
		List list = getDao().getJdbcTemplate().queryForList(sql, new Object[]{empsn, dateString});
		List<N_DATA_DAILY> listData = new ArrayList<N_DATA_DAILY>();
		for (int i=0;i<list.size();i++){
			N_DATA_DAILY data = new N_DATA_DAILY();
			ListOrderedMap obj = (ListOrderedMap) list.get(i);
			data.setDATES((Date)obj.getValue(0));
			data.setTT_IN((String) obj.getValue(1));
			data.setTT_MID((String) obj.getValue(2));
			data.setTT_OUT((String) obj.getValue(3));		
			data.setTT_OVER((String) obj.getValue(4));
			data.setNOTE((String) obj.getValue(5));
			listData.add(data);
		}
		setListData(listData);
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public String getEmpsn() {
		return empsn;
	}

	public void setEmpsn(String empsn) {
		this.empsn = empsn;
	}
}
