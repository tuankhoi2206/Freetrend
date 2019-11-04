package fv.util;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Excel row to object, object to row mapping
 * @author Hieu
 *
 */
public class ExcelBinder{
	public static final int INDEX_STRATEGY=0;
	public static final int NAME_STRATEGY=1;
	
	private HSSFWorkbook workBook;
	private Object dataObject;
	private Integer sheetIndex=0;
	//Class type -> Number.class, String.class, Date.class
	private List<Object[]> indexPropertiesList;//Object[]{property, excel column index, Class type}
	private List<Object[]> namePropertiesList;//Object[]{property, excel column title, Class type}
	private List<Object[]> customObjectList;//Object[]{value, excel column index, Class type}
	private HSSFCellStyle cellStyle;
	private int propertyStrategy=INDEX_STRATEGY;
	private List<String> workbookHeader;
	private Map<String, Object> replaceNullProperties;

	public void rowToObject(int rowIndex) {
		HSSFSheet sheet = workBook.getSheetAt(sheetIndex);
		HSSFRow row;
		HSSFCell cell;
		List<Object[]> propertiesList;
		if (propertyStrategy==INDEX_STRATEGY){
			propertiesList=indexPropertiesList;
		}else{
			propertiesList=namePropertiesList;
		}
		for (int i=0;i<propertiesList.size();i++){
			Object[] props = propertiesList.get(i);
			String key = (String) props[0];
			Integer index;
			if (propertyStrategy==INDEX_STRATEGY){
				index = (Integer) props[1];
			}else{
				index = workbookHeader.indexOf(props[1]);
			}
			Class type = (Class) props[2];
			if (index<0) continue;
			row = sheet.getRow(rowIndex);
			cell = row.getCell(index);
			Object obj;
			if (type==Number.class){
				obj = HSSFUtils.getNumericCellValue(workBook, cell, true);
				obj = BigDecimal.valueOf((Double)obj);
			}else if (type==String.class){
				obj = HSSFUtils.getStringCellValue(cell, true);
			}else if (type==Date.class){
				obj = HSSFUtils.getDateCellValue(cell);
			}else obj=null;
			
			if (replaceNullProperties!=null && (obj==null||obj.equals(""))){
				obj = replaceNullProperties.get(key);
			}
			
			try {
				PropertyUtils.setProperty(dataObject, key, obj);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	public void objectToRow(int rowIndex) {
		HSSFSheet sheet = workBook.getSheetAt(sheetIndex);
		HSSFRow row;
		HSSFCell cell;
		row = sheet.getRow(rowIndex);
		if (row==null)
			row = sheet.createRow(rowIndex);
		List<Object[]> propertiesList;
		if (propertyStrategy==INDEX_STRATEGY){
			propertiesList=indexPropertiesList;
		}else{
			propertiesList=namePropertiesList;
		}
		for (int i=0;i<propertiesList.size();i++){
			Object[] props = propertiesList.get(i);
			String key = (String) props[0];
			Integer index;
			if (propertyStrategy==INDEX_STRATEGY){
				index = (Integer) props[1];
			}else{
				index = workbookHeader.indexOf(props[1]);
			}
			Class type = (Class) props[2];
			if (index<0) continue;
			cell = row.createCell(index);
			if (cellStyle!=null) cell.setCellStyle(cellStyle);
			try {
				Object obj = PropertyUtils.getProperty(dataObject, key);
				if (type.equals(Number.class)){
					if (obj!=null){
						BigDecimal n = (BigDecimal) obj;
						cell.setCellValue(n.doubleValue());
					}else{
						cell.setCellValue(0d);
					}
				}else if (type.equals(String.class)){
					if (obj!=null)
						cell.setCellValue(obj.toString());
					else
						cell.setCellValue("");
				}else if (type.equals(Date.class)){
					if (obj!=null){
						Date date = (Date) obj;
						cell.setCellValue(date);
					}else{
						cell.setCellValue("");
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		if (customObjectList!=null&&customObjectList.size()>0){
			for (int i=0;i<customObjectList.size();i++){
				Object[] props = customObjectList.get(i);
				Object value = props[0];
				Integer index = (Integer) props[1];
				Class type = (Class) props[2];
				cell = row.createCell(index);
				if (cellStyle!=null) cell.setCellStyle(cellStyle);
				if (type.equals(Number.class)){
					BigDecimal n = (BigDecimal) value;
					cell.setCellValue(n.doubleValue());
				}else if (type.equals(String.class)){
					cell.setCellValue(value.toString());
				}else if (type.equals(Date.class)){
					Date date = (Date) value;
					cell.setCellValue(date);
				}
			}
		}
	}
	
	public Object getDataObject() {
		return dataObject;
	}

	public void setDataObject(Object dataObject) {
		this.dataObject = dataObject;
	}

	public HSSFWorkbook getWorkBook() {
		return workBook;
	}

	public void setWorkBook(HSSFWorkbook workBook) {
		this.workBook = workBook;
	}

	public List<Object[]> getIndexPropertiesList() {
		return indexPropertiesList;
	}

	public void setIndexPropertiesList(List<Object[]> propertiesList) {
		this.indexPropertiesList = propertiesList;
	}
	
	public List<Object[]> getCustomObjectList() {
		return customObjectList;
	}
	
	public void setCustomObjectList(List<Object[]> customObjectList) {
		this.customObjectList = customObjectList;
	}

	public Integer getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(Integer sheetIndex) {
		this.sheetIndex = sheetIndex;
	}
	
	public HSSFCellStyle getCellStyle() {
		return cellStyle;
	}
	
	public void setCellStyle(HSSFCellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	public List<Object[]> getNamePropertiesList() {
		return namePropertiesList;
	}

	public void setNamePropertiesList(List<Object[]> namePropertiesList) {
		this.namePropertiesList = namePropertiesList;
	}

	public int getPropertyStrategy() {
		return propertyStrategy;
	}

	public void setPropertyStrategy(int propertyStrategy) {
		this.propertyStrategy = propertyStrategy;
	}

	public List<String> getWorkbookHeader() {
		return workbookHeader;
	}

	public void setWorkbookHeader(List<String> workbookHeader) {
		this.workbookHeader = workbookHeader;
	}

	public Map<String, Object> getReplaceNullProperties() {
		return replaceNullProperties;
	}

	public void setReplaceNullProperties(Map<String, Object> replaceNullProperties) {
		this.replaceNullProperties = replaceNullProperties;
	}
	
}
