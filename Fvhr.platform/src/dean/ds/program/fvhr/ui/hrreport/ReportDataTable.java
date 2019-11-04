package ds.program.fvhr.ui.hrreport;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.dao.hrreport.ReportDao;
import dsc.echo2app.component.table.PageableSortableTableModel;
import fv.components.MrBeanBrowserContent;

/**
 * Bao cao tang ca
 * @author Hieu
 *
 * @param <T> - Display Data Object
 */
public interface ReportDataTable<T> {
	Class<T> getDataClass();//care recursive
	String[] getHeaders();
	int getRowsPerPage();
	MrBeanBrowserContent getTable();
	void refresh(Map<String, Object> params);
	PageableSortableTableModel getModel();
	Map<String, String> getColumnHeaderCaptionMap();//care recursive
	ReportDao getDao();
	List<T> getListData(Map<String, Object> params);
	HSSFWorkbook export();
}
