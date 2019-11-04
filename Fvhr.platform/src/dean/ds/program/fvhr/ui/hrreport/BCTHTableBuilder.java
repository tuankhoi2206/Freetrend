package ds.program.fvhr.ui.hrreport;

import java.util.HashMap;
import java.util.Map;

import fv.components.MrBeanBrowserContent;

public class BCTHTableBuilder implements BCTHBuilder{
	int rowsPerPage=10;
	String header[];
	
	@Override
	public MrBeanBrowserContent getTable(String type){
		MrBeanBrowserContent br = new MrBeanBrowserContent(){
			private static final long serialVersionUID = 1L;
			@Override
			public Class<?> getBean() {
				return BCTH.class;
			}

			@Override
			public Map<String, String> getColumnHeaderMap() {
				Map<String, String> map = new HashMap<String, String>();
				map.put("empsn", "N_EMPLOYEE.EMPSN");
				map.put("name", "N_EMPLOYEE.FULL_NAME");
				map.put("dept", "N_EMPLOYEE.DEPSN");
				map.put("date1", "BCTH.DATE1");
				map.put("date2", "BCTH.DATE2");
				map.put("shift", "BCTH.SHIFT");
				//Add 28/08/2013, HA
				map.put("tIn", "BCTH.TIN");
				map.put("tMid", "BCTH.TMID");
				map.put("tOut", "BCTH.TOUT");
				map.put("tOver", "BCTH.TOVER");
				//end add
				map.put("realOt", "BCTH.REAL_OT");
				map.put("payOt", "BCTH.PAY_OT");
				//add 28/08/2013, HA
				map.put("note", "BCTH.NOTE");
				map.put("ttgt", "BCTH.TTGT");
				// end add
				return map;
			}
			
		};
		if (type.equals(TYPE1)){
			//br.initTableColumns(new String[]{"empsn", "name", "dept", "date1", "shift", "realOt", "payOt"});
			br.initTableColumns(new String[]{"empsn", "name", "dept", "date1", "shift","tIn","tMid","tOut","tOver", "realOt", "payOt","note","ttgt"});
		}else{
			//br.initTableColumns(new String[]{"empsn", "name", "dept", "date1", "date2", "realOt", "payOt"});
			br.initTableColumns(new String[]{"empsn", "name", "dept", "date1", "date2","tIn","tMid","tOut","tOver", "realOt", "payOt","note","ttgt"});
		}
		br.setRowsPerPage(rowsPerPage);
		return br;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
}
