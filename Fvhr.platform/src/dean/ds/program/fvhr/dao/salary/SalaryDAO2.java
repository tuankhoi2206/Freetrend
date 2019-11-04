package ds.program.fvhr.dao.salary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ds.program.fvhr.domain.ATTENDANTDB;
import fv.util.GenericJdbcDAO;

public class SalaryDAO2 extends GenericJdbcDAO{

	private AttCalcSP calcStoredProcedure;
	
	public SalaryDAO2(String tableName, Class supportsClass) {
		super(tableName, supportsClass);
	}
	
	public int updateFromExcel(ATTENDANTDB data, List<Object[]> propertiesToUpdate, Integer[] types){
		String[] columns = new String[propertiesToUpdate.size()];
//		StringBuilder sb = new StringBuilder();
		for (int i=0;i<propertiesToUpdate.size();i++){
			columns[i] = (String) propertiesToUpdate.get(i)[0];
//			sb.append(columns[i]).append(",");
		}
		
//		sb.deleteCharAt(sb.length()-1);
		return updateOnColumns(data, columns, types);
	}
	
	public void calculate(boolean luong1muc, String empsn, String month, String year){
		if (calcStoredProcedure==null) calcStoredProcedure = new AttCalcSP(getDataSource(), luong1muc);
		Map<String, Object> params = new HashMap<String, Object>();		
		params.put("THANG", month);
		params.put("NAM", year);
		params.put("SOTHE", empsn);
		calcStoredProcedure.execute(params);
	}
}
