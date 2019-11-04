package ds.program.fvhr.ui.salary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ds.program.fvhr.dao.hrreport.ReportDao;
import ds.program.fvhr.domain.ATT200000;
import ds.program.fvhr.ui.hrreport.AbstractReportDataTable;

public class SalaryDataTable extends AbstractReportDataTable<ATT200000>{

	public SalaryDataTable(ReportDao dao) {
		super(dao);
		getTable().setVniColumns(new String[]{"EMPNA"});
	}

	@Override
	public Class<ATT200000> getDataClass() {
		return ATT200000.class;
	}

	@Override
	public String[] getHeaders() {
		return new String[]{"EMPSN","EMPNA","DEPSN","BSALY","BONUS1","BONUS2",
				"BONUS3","BONUS4","BONUS9", "BONUS10", "BONUS5","BONUS6","BONUS7","BONUS_ACN","JOININSU","BH_TNGHIEP",
				"JOINLUM","BORM","YLBX","PAYTAX","KQT","REST","NWHOUR","REST_PAY","REST_SICK","OTHER",
				"DUCLS","NUCLS","ADDCLS1","NADDCLS","ADDHOL","ADDHOLN","BSALY_N","NOTE","TADDCLS",
				"TBASIC","TADDS","TBONUS","TINCOME","TKQ","TS1","TTS","TYADDCLS","SIGN","PHEP_NS","COMBSALY",
				"CODE_TAX","BONUS1_HOL","BAC","PAYTAX","BONUS2_GOC",};
	}

	@Override
	public List<ATT200000> getListData(Map<String, Object> params) {
		return getDao().getSalaryList(params);
	}

	public Map<String, String> getColumnHeaderCaptionMap() {
		Map<String,String> map = new HashMap<String, String>();
		for (String str:getHeaders()){
			map.put(str, "ATT200000."+str);
		}
		return map;
	}
}
