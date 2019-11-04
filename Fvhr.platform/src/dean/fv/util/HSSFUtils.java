package fv.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;

import org.apache.poi.hssf.record.formula.*;
import org.apache.poi.hssf.record.CellValueRecordInterface;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.record.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.omg.CORBA.portable.CustomValue;

import dsc.echo2app.Application;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.util.function.UUID;

/**
 * Apache poi support utilities
 * 
 * @author Hieu
 * 
 */
public class HSSFUtils {

	/**
	 * Get excel column name.<br />
	 * Example: column 0 => A, column 1 => B <br />
	 * Support max column 'ZZ'
	 * 
	 * @param index -
	 *            logical column index (start at 0)
	 * @return column name
	 */
	public static String getCellName(int index) {
		if (index > 701)
			return null;
		String ch = "";
		int part = index / 26;
		if (part == 0) {// A-Z
			ch = ch + (char) (index + 65);
		} else {// AA-ZZ
			part--;
			ch = ch + (char) (65 + part);
			ch = ch + (char) (index % 26 + 65);
		}
		return ch;
	}

	public static String getStringCellValue(HSSFCell cell,
			boolean convertIfNumber) {
		if (cell != null) {
			int type = cell.getCellType();
			if (type == Cell.CELL_TYPE_STRING) {
				return cell.getStringCellValue();
			} else if (type == Cell.CELL_TYPE_NUMERIC) {
				if (convertIfNumber)
					return String.format("%.0f", cell.getNumericCellValue());
			}
		}
		return "";
	}

	public static Double getNumericCellValue(HSSFWorkbook wb, HSSFCell cell,
			boolean convertIfString) {
		if (cell != null) {
			int type = cell.getCellType();
			if (type == Cell.CELL_TYPE_NUMERIC) {
				return cell.getNumericCellValue();
			} else if (type == Cell.CELL_TYPE_STRING) {
				if (convertIfString) {
					Double ret;
					try {
						ret = Double.parseDouble(cell.getStringCellValue());
					} catch (Exception e) {
						ret = 0D;
						// e.printStackTrace();
					}
					return ret;
				}
			} else if (type == Cell.CELL_TYPE_FORMULA) {
				FormulaEvaluator evaluator = wb.getCreationHelper()
						.createFormulaEvaluator();
				CellValue val = evaluator.evaluate(cell);
				return val.getNumberValue();
			}
		}
		return 0D;
	}

	public static Date getDateCellValue(HSSFCell cell) {
		Date date = null;
		if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			return null;
		}
		try {
			date = cell.getDateCellValue();
		} catch (Exception e) {
			try {
				String str = cell.getStringCellValue();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				date = sdf.parse(str);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return date;
	}

	public static void evalFormula(HSSFWorkbook wb, HSSFCell cell) {
		if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			FormulaEvaluator evaluator = wb.getCreationHelper()
					.createFormulaEvaluator();
			evaluator.evaluateFormulaCell(cell);
		}
	}

	public static void copyCell(HSSFCell src, HSSFCell des) {		
		des.setCellStyle(src.getCellStyle());
		int type = src.getCellType();
		switch (type) {
		case Cell.CELL_TYPE_BLANK:
		case Cell.CELL_TYPE_ERROR:
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			des.setCellValue(src.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			des.setCellValue(src.getCellFormula());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			des.setCellValue(src.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING:
			des.setCellValue(src.getStringCellValue());
			break;
		}
	}

	public static List<String> findHeader(HSSFWorkbook wb, int sheetIndex, String firstCellName){
		List<String> list = new ArrayList<String>();
		HSSFSheet sheet = wb.getSheetAt(sheetIndex);
		HSSFRow row;
		HSSFCell cell;
		for (int i=0;i<sheet.getPhysicalNumberOfRows();i++){
			row = sheet.getRow(i);
			if (row==null) continue;
			cell = row.getCell(0);
			if (cell==null) continue;
			String value = getStringCellValue(cell, false).trim().toUpperCase();
			if (value.trim().equals(firstCellName)){//header row
				for (int j=0;j<row.getPhysicalNumberOfCells();i++){
					cell = row.getCell(j);
					if (cell==null) continue;
					list.add(getStringCellValue(cell, false).trim().toUpperCase());
				}
				break;
			}
		}
		return list;
	}

	public static void fill(HSSFCell cell, Object obj) {
		if (obj==null) {
			cell.setCellValue("");
			return;
		}
		if (obj instanceof Number){
			String o = obj.equals(null)?"0.0":obj.toString();
			Double val = Double.valueOf(o);
			cell.setCellValue(val);			
		} else if (obj instanceof Date){
			Date date = (Date) obj;
			cell.setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(date));
		} else if (obj instanceof Calendar){
			Calendar cal = (Calendar) obj;
			cell.setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));
		} else if (obj instanceof Boolean){
			cell.setCellValue((Boolean)obj);
		} else {
			String o = obj.equals(null)?" ":obj.toString();
			cell.setCellValue(String.valueOf(o));
		}
	}

	public static void fill(HSSFCell cell, Object obj, Map<String, Object> options) {
		if (obj==null) cell.setCellValue("");
		if (obj instanceof Number){
			Double val = Double.valueOf(obj.toString());
			cell.setCellValue(val);
		} else if (obj instanceof Date){
			Date date = (Date) obj;
			if (options!=null&&options.get("dateFormat")!=null){
				SimpleDateFormat sdf = new SimpleDateFormat(options.get("dateFormat").toString());
				cell.setCellValue(sdf.format(date));
			}else
				cell.setCellValue(date);
		} else if (obj instanceof Calendar){
			Calendar cal = (Calendar) obj;
			if (options!=null&&options.get("dateFormat")!=null){
				SimpleDateFormat sdf = new SimpleDateFormat(options.get("dateFormat").toString());
				cell.setCellValue(sdf.format(cal.getTime()));
			}else
				cell.setCellValue(cal);
		} else if (obj instanceof Boolean){
			cell.setCellValue((Boolean)obj);
		} else {
			cell.setCellValue(obj.toString());
		}
	}

	public static void copyRow1(HSSFWorkbook wb, HSSFSheet sheet, int sourceRowNum, int destinationRowNum) {
		// Get the source / new row
		HSSFRow newRow = sheet.getRow(destinationRowNum);
		HSSFRow sourceRow = sheet.getRow(sourceRowNum);

		// If the row exist in destination, push down all rows by 1 else create a new row
		if (newRow != null) {
			sheet.shiftRows(destinationRowNum, sheet.getLastRowNum(), 1, false, true);
		} else {
			newRow = sheet.createRow(destinationRowNum);
		}

		// Loop through source columns to add to new row
		for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
			// Grab a copy of the old/new cell
			HSSFCell oldCell = sourceRow.getCell(i);
			HSSFCell newCell = newRow.createCell(i);

			// If the old cell is null jump to next cell
			if (oldCell == null) {
				newCell = null;
				continue;
			}

			// Copy style from old cell and apply to new cell
			HSSFCellStyle newCellStyle = wb.createCellStyle();
			newCellStyle.cloneStyleFrom(oldCell.getCellStyle());

			newCell.setCellStyle(newCellStyle);

			// If there is a cell comment, copy
			if (oldCell.getCellComment() != null) {
				newCell.setCellComment(oldCell.getCellComment());
			}

			// If there is a cell hyperlink, copy
			if (oldCell.getHyperlink() != null) {
				newCell.setHyperlink(oldCell.getHyperlink());
			}

			// Set the cell data type
			newCell.setCellType(oldCell.getCellType());

			// Set the cell data value
			switch (oldCell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				//                    newCell.setCellValue(oldCell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				newCell.setCellValue(oldCell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				//                    newCell.setCellErrorValue(oldCell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				String formula = oldCell.getCellFormula();
				formula = replaceFormulaRow(formula, (destinationRowNum+1));
				newCell.setCellFormula(formula);
				break;
			case Cell.CELL_TYPE_NUMERIC:
				newCell.setCellValue(oldCell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				newCell.setCellValue(oldCell.getRichStringCellValue());
				break;
			}
		}

		// If there are are any merged regions in the source row, copy to new row
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress cellRangeAddress = sheet.getMergedRegion(i);
			if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
				CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
						(newRow.getRowNum() +
								(cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()
										)),
										cellRangeAddress.getFirstColumn(),
										cellRangeAddress.getLastColumn());
				sheet.addMergedRegion(newCellRangeAddress);
			}
		}
	}

	public static String replaceFormulaRow(String formula, int newRow){
		Pattern p = Pattern.compile("([A-Z]+)([1-9]+[0-9]*)");
		Pattern p1 = Pattern.compile("\\d+");
		Matcher m = p.matcher(formula);
		Matcher m1;
		while (m.find()){
			String group = m.group();
			m1 = p1.matcher(group);
			if (m1.find()){
				String g1 = m1.group();
				String s1 = group.replace(g1, newRow+"");
				formula=formula.replace(group, s1);
			}
		}
		return formula;
	}

	public static void copyRow(HSSFWorkbook wb, HSSFSheet sheet, int sourceRowNum, int desRowNum){	
		HSSFRow newRow = sheet.createRow(desRowNum);
		HSSFRow sourceRow = sheet.getRow(sourceRowNum);		
		
		//System.out.println(sourceRow.getLastCellNum());
		System.out.println(sourceRowNum);
		int formRowNum = 14;	
		
		for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
			// Grab a copy of the old/new cell
			
			HSSFCell oldCell = sourceRow.getCell(i);
			HSSFCell newCell = newRow.createCell(i);
			
			// If the old cell is null jump to next cell
			if (oldCell == null) {
				newCell = null;
				continue;
			}

			// Copy style from old cell and apply to new cell
			HSSFCellStyle newCellStyle = wb.createCellStyle();
			newCellStyle.cloneStyleFrom(oldCell.getCellStyle());			
			newCell.setCellStyle(newCellStyle);
			
/*			if (sourceRowNum>=690){
				//HSSFRow formRow = sheet.getRow(formRowNum);
				//HSSFCell formCell = formRow.getCell(i);		
				HSSFCellStyle newCellStyle= wb.getCellStyleAt((short)i);
				newCell.setCellStyle(newCellStyle);
			}
			else{
				// Copy style from old cell and apply to new cell
				HSSFCellStyle newCellStyle = wb.createCellStyle();
				newCellStyle.cloneStyleFrom(oldCell.getCellStyle());			
				newCell.setCellStyle(newCellStyle);
			}
			*/
			// If there is a cell comment, copy
			if (oldCell.getCellComment() != null) {
				newCell.setCellComment(oldCell.getCellComment());
			}

			// If there is a cell hyperlink, copy
			if (oldCell.getHyperlink() != null) {
				newCell.setHyperlink(oldCell.getHyperlink());
			}

			// Set the cell data type
			newCell.setCellType(oldCell.getCellType());

			// Set the cell data value
			switch (oldCell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				//                    newCell.setCellValue(oldCell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				newCell.setCellValue(oldCell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				//                    newCell.setCellErrorValue(oldCell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				String formula = oldCell.getCellFormula();
				formula = replaceFormulaRow(formula, (desRowNum+1));
				newCell.setCellFormula(formula);
				break;
			case Cell.CELL_TYPE_NUMERIC:
				newCell.setCellValue(oldCell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				newCell.setCellValue(oldCell.getRichStringCellValue());
				break;
			}
		}
	}

	public static void copyRowM(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow sourceRowq, int desRowNum){
		HSSFRow newRow = sheet.createRow(desRowNum);
		HSSFRow sourceRow = sheet.getRow(desRowNum-1);
		//System.out.println(sourceRow.getLastCellNum());
		for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
			//System.out.println(i);
			if(i==11)
				System.out.println();
			// Grab a copy of the old/new cell
			HSSFCell oldCell = sourceRow.getCell(i);
			HSSFCell newCell = newRow.createCell(i);

			// If the old cell is null jump to next cell
			if (oldCell == null) {
				newCell = null;
				continue;
			}
			HSSFCell ce = sourceRowq.getCell(i);
			HSSFCellStyle s = ce.getCellStyle();

			newCell.setCellStyle(s);//newCellStyle);

			// If there is a cell comment, copy
			if (oldCell.getCellComment() != null) {
				newCell.setCellComment(oldCell.getCellComment());
			}

			// If there is a cell hyperlink, copy
			if (oldCell.getHyperlink() != null) {
				newCell.setHyperlink(oldCell.getHyperlink());
			}

			// Set the cell data type
			newCell.setCellType(oldCell.getCellType());

			// Set the cell data value
			switch (oldCell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				//                    newCell.setCellValue(oldCell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				newCell.setCellValue(oldCell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				//                    newCell.setCellErrorValue(oldCell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				String formula = oldCell.getCellFormula();
				formula = replaceFormulaRow(formula, (desRowNum+1));
				newCell.setCellFormula(formula);//
				break;
			case Cell.CELL_TYPE_NUMERIC:
				newCell.setCellValue(oldCell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				newCell.setCellValue(oldCell.getRichStringCellValue());
				break;
			}
		}
	}
}
