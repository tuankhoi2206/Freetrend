package ds.program.fvhr.services.validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import fv.util.HSSFUtils;

public class AttQuitExcelValidator implements Validator<HSSFWorkbook> {

	private String month, year;

	private Integer[] range;

	public AttQuitExcelValidator(String month, String year) {
		this.month = month;
		this.year = year;
		this.range = new Integer[2];
	}

	public synchronized String validate(HSSFWorkbook wb) {
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int rowCount = sheet.getPhysicalNumberOfRows();
		// Detect start row which match empsn pattern
		range[0] = 0;
		while (range[0] < rowCount) {
			row = sheet.getRow(range[0]);
			if (row == null)
				continue;// ///////////allow null row in header area
			cell = row.getCell(0);
			String celVal=HSSFUtils.getStringCellValue(cell, true);
			if (celVal.matches("[0-9]{8}")) {
				break;
			}
			range[0]++;
		}
		range[1] = range[0];// logical
		List<String> list = new ArrayList<String>();
		for (int i = range[0]; i < rowCount; i++) {
			row = sheet.getRow(i);
			if (row == null)
				break;// //do not allow null row in data area
			cell = row.getCell(0);// empsn
			String value;
			if (cell != null) {
				value = HSSFUtils.getStringCellValue(cell, true);
				if (value.matches("[0-9]{8}")) {// break ?
					list.add(value);
					range[1]++;
				} else {
					// if (!value.equals("*")&&!value.equals(""))
					// list.add("row " + (i+1));
					break;
				}
			}
		}
		if (range[0] == range[1]) {
			return INVALID;
		}
		EmpsnValidator validator = EmpsnValidator.createValidator();
		// EmpsnValidateCondition condition = new
		// EmpsnValidateCondition(NOTFOUND);
		// validator.addValidateCondition(condition);
		EmpsnValidateCondition condition2 = new EmpsnValidateCondition(UNKNOWN);
		condition2.setMonth(month);
		condition2.setYear(year);
		validator.setValidateCondition(condition2);
		List<ValidatorMessage> listMsg = validator.batchValidate(list);
		if (listMsg.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (ValidatorMessage msg : listMsg) {
				sb.append(msg.getObj()).append(">").append(parseMessage(msg.getMessage()))
						.append(", ");
			}
			sb.deleteCharAt(sb.length() - 2);
			return sb.toString();
		} else {
			return VALID;
		}
	}

	public synchronized List<ValidatorMessage> batchValidate(
			List<HSSFWorkbook> list) {
		throw new RuntimeException("Not implemented method.");
	}

	public Integer[] getRange() {
		return range;
	}
	
	public String parseMessage(String message){
		if (message==null) return null;
		if (message.equals(INVALID))
			return "Không hợp lệ";
		else if (message.equals(UNKNOWN))
			return "Nhập nghỉ việc & xử lý ngày công";
		else if (message.equals(NOTFOUND))
			return "Không tìm thấy số thẻ trong hệ thống";
		else return message;
	}

}
