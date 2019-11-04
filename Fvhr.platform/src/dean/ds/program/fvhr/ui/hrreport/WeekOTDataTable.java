package ds.program.fvhr.ui.hrreport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ds.program.fvhr.dao.hrreport.ReportDao;

public class WeekOTDataTable extends AbstractReportDataTable<WeekOT>{
	

	public WeekOTDataTable(ReportDao dao) {
		super(dao);
	}

	@Override
	public String[] getHeaders() {
		return new String[]{"facCode", "timeNo", "deptNo", "empTot1", "empTot2", "empTot3", "empTot4", "empTot5", "empTot6", "empTot7", "empTot8"};
	}

	@Override
	public Class<WeekOT> getDataClass() {
		return WeekOT.class;
	}

	@Override
	public List<WeekOT> getListData(Map<String, Object> params) {
		return getDao().getListWeekOT(params);
	}

	@Override
	public Map<String, String> getColumnHeaderCaptionMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("facCode", "OT_REPORT.FAC_CODE");
		map.put("deptNo", "OT_REPORT.DEPT_NO");
		map.put("timeNo", "OT_REPORT.TIME_NO");
		map.put("empTot1", "OT_REPORT.EMP_TOT1");
		map.put("empTot2", "OT_REPORT.EMP_TOT2");
		map.put("empTot3", "OT_REPORT.EMP_TOT3");
		map.put("empTot4", "OT_REPORT.EMP_TOT4");
		map.put("empTot5", "OT_REPORT.EMP_TOT5");
		map.put("empTot6", "OT_REPORT.EMP_TOT6");
		map.put("empTot7", "OT_REPORT.EMP_TOT7");
		map.put("empTot8", "OT_REPORT.EMP_TOT8");
		return map;
	}

}
