package ds.program.fvhr.ui.hrreport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ds.program.fvhr.dao.hrreport.ReportDao;

public class ICWeekOTDataTable extends AbstractReportDataTable<ICWeekOT>{

	public ICWeekOTDataTable(ReportDao dao) {
		super(dao);
		getTable().setVniColumns(new String[]{"groupName", "dept"});
	}

	@Override
	public Class<ICWeekOT> getDataClass() {
		return ICWeekOT.class;
	}

	@Override
	public String[] getHeaders() {
		return new String[]{"groupNike", "groupName", "dept", "empsn", "totalHours", "totalSundayHours"};
	}

	@Override
	public List<ICWeekOT> getListData(Map<String, Object> params) {
		return getDao().getICWeekOTList(params);
	}

	@Override
	public Map<String, String> getColumnHeaderCaptionMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("groupNike", "OT_REPORT.ID_GROUPNI");
		map.put("groupName", "OT_REPORT.ID_GROUPNA");
		map.put("dept", "OT_REPORT.DEPT_NAME");
		map.put("empsn", "OT_REPORT.EMPSN");
		map.put("totalHours", "OT_REPORT.TOTAL_HOURS");
		map.put("totalSundayHours", "OT_REPORT.TOTAL_SUNDAY_HOURS");
		return map;
	}
}
