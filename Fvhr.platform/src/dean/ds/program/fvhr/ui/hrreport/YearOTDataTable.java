package ds.program.fvhr.ui.hrreport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ds.program.fvhr.dao.hrreport.ReportDao;

public class YearOTDataTable extends AbstractReportDataTable<YearOT>{

	public YearOTDataTable(ReportDao dao) {
		super(dao);
	}

	@Override
	public String[] getHeaders() {
		return new String[]{"facCode", "permitDate", "reportDate", "deptNo", "empTot1", "empTot2", "empTot3", "empTot4", "empTot5", "empTot6"};
	}

	@Override
	public Class<YearOT> getDataClass() {
		return YearOT.class;
	}

	@Override
	public List<YearOT> getListData(Map<String, Object> params) {
		return getDao().getListYearOT(params);
	}

	@Override
	public Map<String, String> getColumnHeaderCaptionMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("facCode", "OT_REPORT.FAC_CODE");
		map.put("deptNo", "OT_REPORT.DEPT_NO");
		map.put("permitDate", "OT_REPORT.PERMIT_DATE");
		map.put("reportDate", "OT_REPORT.REPORT_DATE");
		map.put("empTot1", "OT_REPORT.EMP_TOT11");
		map.put("empTot2", "OT_REPORT.EMP_TOT21");
		map.put("empTot3", "OT_REPORT.EMP_TOT31");
		map.put("empTot4", "OT_REPORT.EMP_TOT41");
		map.put("empTot5", "OT_REPORT.EMP_TOT51");
		map.put("empTot6", "OT_REPORT.EMP_TOT61");
		return map;
	}
}
