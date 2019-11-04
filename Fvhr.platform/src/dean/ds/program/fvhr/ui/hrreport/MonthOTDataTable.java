package ds.program.fvhr.ui.hrreport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ds.program.fvhr.dao.hrreport.ReportDao;

public class MonthOTDataTable extends AbstractReportDataTable<MonthOT>{

	public MonthOTDataTable(ReportDao dao) {
		super(dao);
		getTable().setVniColumns(new String[]{"fullName", "group", "nameDept"});
	}

	@Override
	public Class<MonthOT> getDataClass() {
		return MonthOT.class;
	}

	@Override
	public String[] getHeaders() {
		return new String[]{"empsn", "fullName", "fact", "group", "nameDept", "empcn", "ot"};
	}

	@Override
	public List<MonthOT> getListData(Map<String, Object> params) {		
		return getDao().getMonthOTTrack(params);
	}

	@Override
	public Map<String, String> getColumnHeaderCaptionMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("empsn", "OT_REPORT.EMPSN");
		map.put("fullName", "OT_REPORT.FULL_NAME");
		map.put("fact", "OT_REPORT.FACT");
		map.put("group", "OT_REPORT.GROUP");
		map.put("nameDept", "OT_REPORT.DEPT_NAME");
		map.put("empcn", "OT_REPORT.EMPCN");
		map.put("ot", "OT_REPORT.OT");
		return map;
	}

}
