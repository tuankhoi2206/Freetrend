package ds.program.fvhr.ui.hrreport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ds.program.fvhr.dao.hrreport.ReportDao;

public class DayOTDataTable extends AbstractReportDataTable<DayOT> {
	
	public DayOTDataTable(ReportDao dao) {
		super(dao);
	}

	@Override
	public String[] getHeaders() {
		return new String[]{"facCode","date","deptNo","empTot"};
	}

	@Override
	public Class<DayOT> getDataClass() {
		return DayOT.class;
	}

	@Override
	public List<DayOT> getListData(Map<String, Object> params) {
		return getDao().getListDayOT(params);
	}

	@Override
	public Map<String, String> getColumnHeaderCaptionMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("facCode", "OT_REPORT.FAC_CODE");
		map.put("deptNo", "OT_REPORT.DEPT_NO");				
		map.put("date", "OT_REPORT.DATE");				
		map.put("empTot", "OT_REPORT.EMP_TOT");				
		return map;
	}
}
