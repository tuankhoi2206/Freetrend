package ds.program.fvhr;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.dao.JdbcDAO2;
import fv.util.HSSFUtils;

public class MST {
	
	public static void main(String[] args) {
		JdbcDAO2 dao = new JdbcDAO2();
		String sql = "update att201112 t set t.code_tax=? where t.empsn=?";
		HSSFWorkbook wb;
		try {
			wb = new HSSFWorkbook(ATM.class.getResourceAsStream("taxcode_template.xls"));
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			for (int i=1;i<sheet.getPhysicalNumberOfRows();i++){
				row = sheet.getRow(i);
				if (row==null) continue;
				cell = row.getCell(0);//empsn
				String empsn = HSSFUtils.getStringCellValue(cell, true);
				cell = row.getCell(1);//mst
				String code = HSSFUtils.getStringCellValue(cell, true);
				int up = dao.getJdbcTemplate().update(sql, new Object[]{code, empsn});
				System.out.println("Update row " + i + " > " + up);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
