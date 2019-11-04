package ds.program.fvhr.ui.insurance.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.dao.insuranse.InsuranceDAO2;
import fv.util.DateUtils;
import fv.util.HSSFUtils;
import fv.util.ReportUtils;

public class Dao2Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		InsuranceDAO2 dao = new InsuranceDAO2();
		List<Map<String, Object>> list = dao.getTang20TayList("",1,2013);
		List<String> list1 = dao.getNSRoRaT5T11(1, 2013);
		Map<String, BigDecimal> thamso = dao.getThamSo();
		Date thangBC = DateUtils.getFirstDay(1, 2013);
		
		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance", "Mau_D02-TS_cong_thuc_GiamTuan.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int rowToCopy=11, rowToCopy1=25, rowToCopy2=25;
		int startRow = 12;
		int startRow1 = 26;
		int stt=1;
		
		
		List<Map<String, Object>> copy = new ArrayList<Map<String,Object>>();
		for (int i=0;i<list.size();i++){
			Map<String, Object> data = list.get(i);
			String emp = (String) data.get("EMPSN");
			if (list1.contains(emp)){
				//fetch more info
				copy.add(data);
			}
		}
		
		int incRows=list.size()-1;
		int desRows=copy.size()-1;
		
		if (incRows>0){
			sheet.shiftRows(startRow, sheet.getLastRowNum(), incRows, false, true);
			startRow1+=incRows;
			rowToCopy1+=incRows;
			rowToCopy2=rowToCopy1;
		}
		
		if (desRows>0){
			sheet.shiftRows(startRow1, sheet.getLastRowNum(), desRows, false, true);
		}
		
		for (int i=startRow;i<startRow+incRows;i++){
			HSSFUtils.copyRow(wb, sheet, rowToCopy++, i);
		}
		
		for (int i=startRow1;i<startRow1+desRows;i++){
			HSSFUtils.copyRow(wb, sheet, rowToCopy1++, i);
		}
		
		rowToCopy=11;
		rowToCopy1=rowToCopy2;
		
		for (int i=0;i<list.size();i++){
			//fill increase			
			System.out.println("Fill row " + rowToCopy);
			row = sheet.getRow(rowToCopy);
			Map<String, Object> data = list.get(i);
			String emp = (String) data.get("EMPSN");
			cell = row.getCell(0);//STT
			HSSFUtils.fill(cell, i+1);
			cell = row.getCell(1);//Ho va ten
			HSSFUtils.fill(cell, data.get("FNAME")+" "+data.get("LNAME"));
			cell = row.getCell(2);//Ma so BHXH
			HSSFUtils.fill(cell, data.get("ID_SOCIAL"));
			cell = row.getCell(3);//Ngay sinh
			HSSFUtils.fill(cell, data.get("BIRTHDAY"));
			cell = row.getCell(4);//Gioi tinh
			if ("NU".equals(data.get("SEX")))
				HSSFUtils.fill(cell, "X");
			else
				HSSFUtils.fill(cell, "");
			cell = row.getCell(5);//Chuc vu
			HSSFUtils.fill(cell, data.get("POSITION"));
			cell = row.getCell(11);//Tien luong
			HSSFUtils.fill(cell, data.get("BSALARY"));
			cell = row.getCell(16);//Tang tu thang
			HSSFUtils.fill(cell, data.get("MONTH_BC"));
			cell = row.getCell(20);//Ghi chu
			HSSFUtils.fill(cell, data.get("NOTE_TANG"));
			cell = row.getCell(22);//Ti le BHXH
			HSSFUtils.fill(cell, thamso.get("TYLEBHXH"));
			cell = row.getCell(23);//Ti le BHYT
			HSSFUtils.fill(cell, thamso.get("TYLEBHYT"));
			cell = row.getCell(24);//Ti le BHTN
			HSSFUtils.fill(cell, thamso.get("TYLEBHTN"));
			cell = row.createCell(48);//So the
			HSSFUtils.fill(cell, emp);
			cell = row.createCell(49);//Xuong
			HSSFUtils.fill(cell, data.get("NAME_GROUP"));
			cell = row.createCell(50);//Don vi
			HSSFUtils.fill(cell, data.get("NAME_DEPT_NAME"));
			//eval formula
			cell = row.getCell(18);
			HSSFUtils.evalFormula(wb, cell);
			for (int k=25;k<48;k++){
				cell = row.getCell(k);
				HSSFUtils.evalFormula(wb, cell);
			}
			rowToCopy++;			
		}
		
		for (int i=0;i<copy.size();i++){
			Map<String, Object> data = copy.get(i);
			String emp = (String) data.get("EMPSN");
			System.out.println("File startRow1 : " + rowToCopy1);
			row = sheet.getRow(rowToCopy1);
			cell = row.getCell(0);//STT
			HSSFUtils.fill(cell, stt);
			cell = row.getCell(1);//Ho va ten
			HSSFUtils.fill(cell, data.get("FNAME")+" "+data.get("LNAME"));
			cell = row.getCell(2);//Ma so BHXH
			HSSFUtils.fill(cell, data.get("ID_SOCIAL"));
			cell = row.getCell(3);//Ngay sinh
			HSSFUtils.fill(cell, data.get("BIRTHDAY"));
			cell = row.getCell(4);//Gioi tinh
			if ("NU".equals(data.get("SEX")))
				HSSFUtils.fill(cell, "X");
			else
				HSSFUtils.fill(cell, "");
			cell = row.getCell(5);//Chuc vu
			HSSFUtils.fill(cell, data.get("POSITION"));
			cell = row.getCell(11);//Tien luong
			BigDecimal luong = dao.getLuongBaoGiamBHYT(emp, thangBC);
			HSSFUtils.fill(cell, luong.doubleValue());
			cell = row.getCell(16);//Tang tu thang
			HSSFUtils.fill(cell, data.get("MONTH_BC"));
			cell = row.getCell(17);//Tang tu thang
			cell.setCellValue(DateUtils.getFirstDay(6, 2013));
			cell = row.getCell(20);//Ghi chu
			HSSFUtils.fill(cell, data.get("NOTE_TANG"));
			cell = row.getCell(22);//Ti le BHXH
			HSSFUtils.fill(cell, 0);
			cell = row.getCell(23);//Ti le BHYT
			HSSFUtils.fill(cell, thamso.get("TYLEBHYT"));
			cell = row.getCell(24);//Ti le BHTN
			HSSFUtils.fill(cell, 0);
			cell = row.createCell(48);//So the
			HSSFUtils.fill(cell, emp);
			cell = row.createCell(49);//Xuong
			HSSFUtils.fill(cell, data.get("NAME_GROUP"));
			cell = row.createCell(50);//Don vi
			HSSFUtils.fill(cell, data.get("NAME_DEPT_NAME"));
			//eval formula
			cell = row.getCell(18);
			HSSFUtils.evalFormula(wb, cell);
			for (int k=25;k<48;k++){
				cell = row.getCell(k);
				HSSFUtils.evalFormula(wb, cell);
			}
			stt++;
			rowToCopy1++;
		}
		
		File f = new File("D:\\test.xls");
		FileOutputStream out = new FileOutputStream(f);
		wb.write(out);
		out.flush();
		out.close();
	}

}
