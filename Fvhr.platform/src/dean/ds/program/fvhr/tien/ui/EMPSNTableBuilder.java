package ds.program.fvhr.tien.ui;

import java.util.HashMap;
import java.util.Map;

import ds.program.fvhr.ui.hrreport.BCTH;
import fv.components.MrBeanBrowserContent;

public class EMPSNTableBuilder  implements EMPSNBuilder{
	// 
	int rowsPerPage=10;
	String header[];
	@Override
	public MrBeanBrowserContent getTable(String type) {
		MrBeanBrowserContent br = new MrBeanBrowserContent(){
			private static final long serialVersionUID = 1L;
			@Override
			public Class<?> getBean() {
				return EMPSN_E.class;
			}

			@Override
			public Map<String, String> getColumnHeaderMap() {
				Map<String, String> map = new HashMap<String, String>();
				map.put("empsn", "N_EMPLOYEE.EMPSN");
				map.put("name", fv.util.Vni2Uni.convertToVNI("N_EMPLOYEE.FULL_NAME"));
				map.put("id_dept", "EMPSN_E.depsn");
				map.put("empcn", "EMPSN_E.empcn");
				map.put("code", "EMPSN_E.code");
				map.put("name_dept_name_new", "EMPSN_E.name_dept_name_new");
				map.put("name_dept_name_old", "EMPSN_E.name_dept_name_old");
				map.put("sex", "EMPSN_E.sex");
				map.put("shift", "EMPSN_E.shift");
				map.put("date", "EMPSN_E.date");
				map.put("late", "EMPSN_E.late");
				map.put("rest_rs", "EMPSN_E.rest_rs");
				map.put("real_ot", "EMPSN_E.real_ot");

				map.put("date_hired", "EMPSN_E.date_hired");
				map.put("thamnien", "EMPSN_E.thamnien");
				return map;
			}
			
		};
		
		
		if (type.equals(TYPE1)){
			br.initTableColumns(new String[]{"name","empsn","code","empcn", "name_dept_name_new","id_dept","sex", "shift", "name_dept_name_old"});
		}
		if (type.equals(TYPE2)){
			br.initTableColumns(new String[]{ "name","empsn","code","empcn", "name_dept_name_new","id_dept","sex", "shift"});
		}
		if (type.equals(TYPE3)){
			br.initTableColumns(new String[]{ "empsn","name", "name_dept_name_new","date","late"});
		}
		if (type.equals(TYPE4)){
			br.initTableColumns(new String[]{ "empsn","name", "name_dept_name_new","rest_rs"});
		}
		if (type.equals(TYPE5)){
			br.initTableColumns(new String[]{ "empsn","name", "name_dept_name_new","date"});
		}
		if (type.equals(TYPE6)){
			br.initTableColumns(new String[]{ "empsn","name", "name_dept_name_new","real_ot"});
		}
		if (type.equals(TYPE7)){
			br.initTableColumns(new String[]{ "empsn","name", "name_dept_name_new","late"});
		}
		if (type.equals(TYPE8)){
			br.initTableColumns(new String[]{ "empsn","name", "name_dept_name_new","date_hired","thamnien"});
		}
		br.setRowsPerPage(rowsPerPage);
		return br;
	}

}
