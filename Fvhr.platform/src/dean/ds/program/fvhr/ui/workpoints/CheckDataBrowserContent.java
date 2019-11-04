package ds.program.fvhr.ui.workpoints;

import java.util.HashMap;
import java.util.Map;

import fv.components.MrBeanBrowserContent;

public class CheckDataBrowserContent extends MrBeanBrowserContent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Class<?> getBean() {
		return CmpData.class;
	}

	@Override
	public Map<String, String> getColumnHeaderMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("empsn","CMPDATA.EMPSN");
		map.put("name","CMPDATA.NAME");
		map.put("dept","CMPDATA.DEPT");
		map.put("days","CMPDATA.DAYS");
		
		return map;
	}

}
