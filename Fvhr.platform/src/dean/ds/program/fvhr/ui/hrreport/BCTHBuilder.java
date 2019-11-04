package ds.program.fvhr.ui.hrreport;

import fv.components.MrBeanBrowserContent;

public interface BCTHBuilder {
	public static final String TYPE1="type1";
	public static final String TYPE2="type2";
	MrBeanBrowserContent getTable(String type);
}
