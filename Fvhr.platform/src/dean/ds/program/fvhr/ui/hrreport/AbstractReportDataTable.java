package ds.program.fvhr.ui.hrreport;

import java.util.List;
import java.util.Map;

import nextapp.echo2.app.table.TableColumnModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import ds.program.fvhr.dao.hrreport.ReportDao;
import dsc.echo2app.component.table.PageableSortableTableModel;
import fv.components.MrBeanBrowserContent;

public abstract class AbstractReportDataTable<T> implements ReportDataTable<T>{
	
	private MrBeanBrowserContent browserContent;
	private ReportDao dao;
	private int rowsPerPage = 15;
	
	public AbstractReportDataTable(ReportDao dao){
		this.dao=dao;
		browserContent = new MrBeanBrowserContent(){
			private static final long serialVersionUID = 1L;
			@Override
			public Class<T> getBean() {
				return getDataClass();
			}
			@Override
			public Map<String, String> getColumnHeaderMap() {
				return getColumnHeaderCaptionMap();
			}
		};
		browserContent.setRowsPerPage(getRowsPerPage());
		browserContent.initTableColumns(getHeaders());
	}
	
	@Override
	public abstract Class<T> getDataClass();
	
	@Override
	public abstract String[] getHeaders();
	
	@Override
	public ReportDao getDao(){
		return this.dao;
	}

	@Override
	public PageableSortableTableModel getModel() {
		return (PageableSortableTableModel) browserContent.getDataTable().getModel();
	}

	@Override
	public int getRowsPerPage() {
		return rowsPerPage;
	}
	
	public void setRowsPerPage(int rowsPerPage){
		this.rowsPerPage=rowsPerPage;
	}

	@Override
	public MrBeanBrowserContent getTable() {
		return browserContent;
	}
	
	@Override
	public abstract List<T> getListData(Map<String, Object> params);

	@Override
	public void refresh(Map<String, Object> params) {
		browserContent.setListData(getListData(params));
		browserContent.refresh();
	}
	
	@Override
	public HSSFWorkbook export(){
		PageableSortableTableModel model = getModel();
		if (model.getTotalRows()<=0) return null;
		TableColumnModel columnModel = browserContent.getDataTable().getColumnModel();
		int n = columnModel.getColumnCount();
		int m = model.getTotalRows();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row=sheet.createRow(0);
		HSSFCell cell;
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setColor(HSSFColor.BROWN.index);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		HSSFCellStyle style = wb.createCellStyle();
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setFont(font);
		style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);		
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		for (int i=0;i<n;i++){
			cell = row.createCell(i);
			cell.setCellStyle(style);
			String caption = columnModel.getColumn(i).getHeaderValue().toString();
			cell.setCellValue(caption);
		}
		for (int i=0;i<m;i++){
			row = sheet.createRow(i+1);
			for (int j=0;j<n;j++){
				cell = row.createCell(j);
				Object obj = model.getValueAtAbsolute(j, i);
				if (obj==null) continue;
				if (obj instanceof Number){
					cell.setCellValue(Double.valueOf(obj.toString()));
				}else{
					cell.setCellValue(obj.toString());
				}
			}
		}
		return wb;
	}
}
