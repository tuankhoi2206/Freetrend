package ds.program.fvhr.ui.hrreport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ds.program.fvhr.dao.hrreport.ReportDao;

public class OTSalaryDataTable extends AbstractReportDataTable<OTSalary> {

	public OTSalaryDataTable(ReportDao dao) {
		super(dao);
		getTable().setVniColumns(new String[]{"group", "name"});
	}

	@Override
	public Class<OTSalary> getDataClass() {
		return OTSalary.class;
	}

	@Override
	public String[] getHeaders() {
		return new String[]{"fact", "group", "name", "deptId", "totalEmps", "totalWorkingDays", "totalRestPay", "totalOT"};
	}

	@Override
	public List<OTSalary> getListData(Map<String, Object> params) {
		return getDao().getOTSalaryList(params);
	}

	@Override
	public Map<String, String> getColumnHeaderCaptionMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("fact", "OT_REPORT.FACT");
		map.put("group", "OT_REPORT.GROUP");
		map.put("name", "OT_REPORT.DEPT_NAME");
		map.put("deptId", "OT_REPORT.DEPT_ID");
		map.put("totalEmps", "OT_REPORT.TOTAL_EMPS");
		map.put("totalWorkingDays", "OT_REPORT.TOTAL_WORKING_DAYS");
		map.put("totalRestPay", "OT_REPORT.TOTAL_RESTPAY");
		map.put("totalOT", "OT_REPORT.TOTAL_OT");
		return map;
	}

}
