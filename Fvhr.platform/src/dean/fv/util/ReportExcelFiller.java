package fv.util;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Fill data to excel. First use for <code>QuitWork00RProgram</code>
 * 
 * @author Hieu
 * 
 * @param <T>
 */
public class ReportExcelFiller<T> {
	//fieldName-excelIndex
	protected Map<String, Integer> excelMap = new HashMap<String, Integer>();

	private int divideBy = 1;

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"dd/MM/yyyy");

	private List<String> excludeDivide;

	public ReportExcelFiller() {

	}

	/**
	 * Fill giá trị vào từng cột excel theo tên field của data object đã put
	 * trong excelMap. Tự fill vào các cột khác trong vòng lặp for
	 * 
	 * @param dataObject
	 * @param row
	 */
	public void fill(T dataObject, HSSFRow row) {
		Iterator<String> ite = excelMap.keySet().iterator();
		while (ite.hasNext()) {
			String field = ite.next();
			try {
				HSSFCell cell = row.createCell(excelMap.get(field));
				Object obj = PropertyUtils.getProperty(dataObject, field);
				if (obj == null)
					continue;
				if (obj instanceof Number) {
					if (divideBy > 1)
						if (excludeDivide == null
								|| (excludeDivide != null && !excludeDivide
										.contains(field)))
							cell.setCellValue(((BigDecimal) obj).doubleValue()
									/ divideBy);
						else
							cell.setCellValue(((BigDecimal) obj).doubleValue());
					else
						cell.setCellValue(((BigDecimal) obj).doubleValue());
				} else if (obj instanceof Date) {
					cell.setCellValue(sdf.format((Date) obj));
				} else {
					cell.setCellValue((String) obj);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	public void setDivideBy(int divideBy) {
		this.divideBy = divideBy;
	}

	public int getDivideBy() {
		return divideBy;
	}

	public void setExcludeDivide(List<String> excludeDivide) {
		this.excludeDivide = excludeDivide;
	}

	public List<String> getExcludeDivide() {
		return excludeDivide;
	}
}
