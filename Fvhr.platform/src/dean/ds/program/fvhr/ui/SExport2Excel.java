package ds.program.fvhr.ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.dao.salary.SalaryDAO;
import ds.program.fvhr.domain.ATT200000;
import ds.program.fvhr.domain.N_GET_DATA;
import dsc.dao.DataObjectSet;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import fv.util.HSSFUtils;
import fv.util.ReportUtils;

public class SExport2Excel {
	
	public static void run(boolean fromNS, DataObjectSet ds, SalaryDAO dao, String month, String year) {
//		DataObjectSet ds = getMasterDataContent().getDataObjectSet();
		StopWatch sw = new StopWatch();
		sw.start();
		HSSFWorkbook wb = ReportUtils.loadTemplate("fvhr", "chuyendulieu.xls");
		if (wb==null) wb=new HSSFWorkbook();
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		
		row = sheet.getRow(0);
		cell = row.getCell(0);
		cell.setCellValue(cell.getStringCellValue() + month+"/"+year);
		
		int startRow=4;
		String empsn;
		int n = ds.getRecordCount();
		for (int i=0;i<n;i++){
			if (fromNS){
				N_GET_DATA wp = (N_GET_DATA) ds.getDataObject(i);
				empsn=wp.getEMPSN();
			}else{
				ATT200000 data = (ATT200000) ds.getDataObject(i);
				empsn=data.getEMPSN();
			}
			
			
			
			//fill column by column
			Map<String, Object> data = dao.getObserveData(empsn, month, year);
			
//			System.out.println(empsn + ":" + data);
			
			if (data==null||data.size()==0) {
				System.err.println("Null data: " + empsn);
				continue;
			}
			System.out.println(i + "/" + n + " >> " + empsn);
			row = sheet.createRow(startRow++);
			cell = row.createCell(0);//A - so the
			HSSFUtils.fill(cell, empsn);
			cell = row.createCell(1);//B - ho ten
			HSSFUtils.fill(cell, data.get("EMPNA"));
			cell = row.createCell(2);//C - ngay nhap xuong
			HSSFUtils.fill(cell, data.get("DATE_HIRED"));
			cell = row.createCell(3);//D - Ma don vi
			HSSFUtils.fill(cell, data.get("DEPSN"));
			cell = row.createCell(4);//E - Ten don vi
			HSSFUtils.fill(cell, data.get("NAME_DEPT"));
			cell = row.createCell(5);//F - Luong co ban
			HSSFUtils.fill(cell, data.get("BSALY"));
			cell = row.createCell(6);//G - Luong HD
			HSSFUtils.fill(cell, data.get("COMBSALY"));
			cell = row.createCell(7);//H - Chuc vu
			HSSFUtils.fill(cell, data.get("POSSN"));
			cell = row.createCell(8);//I - PC Chuc vu
			HSSFUtils.fill(cell, data.get("BONUS2"));
			cell = row.createCell(9);//J - Cong viec
			HSSFUtils.fill(cell, data.get("JOB"));
			cell = row.createCell(10);//K - PC Cong viec
			HSSFUtils.fill(cell, data.get("BONUS3"));
			cell = row.createCell(11);//L - Lan
			HSSFUtils.fill(cell, data.get("LABOUR_TIME"));
			cell = row.createCell(12);//M - Thang
			if (data.get("LABOUR_DATE")!=null){
				Date ldate = (Date) data.get("LABOUR_DATE");
				HSSFUtils.fill(cell, new SimpleDateFormat("MM/yyyy").format(ldate));
			}else{
				HSSFUtils.fill(cell, null);
			}
			cell = row.createCell(13);//N - Thoi han ky
			if (data.get("LABOUR_LIMIT")!=null){
				String ll=(String) data.get("LABOUR_LIMIT");
				if ("00000".equals(ll))
					HSSFUtils.fill(cell, "Daøi haïn");
				else
					HSSFUtils.fill(cell, "1 naêm");
			}
			cell = row.createCell(14);//O - Ky ngay
			HSSFUtils.fill(cell, data.get("LABOUR_DATE"));
			cell = row.createCell(15);//P - Den ngay
			HSSFUtils.fill(cell, data.get("LABOUR_EXPIRE"));
			cell = row.createCell(16);//Q - Ngung len luong
			if (data.get("DATE_P")!=null&&data.get("DATE_HL")!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				HSSFUtils.fill(cell, sdf.format(data.get("DATE_P"))+" - "+sdf.format(data.get("DATE_HL")));
				cell = row.createCell(17);//R - Het han ngung len luong
				Date date = (Date) data.get("DATE_HL");
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(date.getTime());
				if (cal.get(Calendar.DAY_OF_MONTH)<15){
					cal.set(Calendar.DAY_OF_MONTH, 1);
				}else{
					cal.add(Calendar.MONTH, 1);
					cal.set(Calendar.DAY_OF_MONTH,1);
				}
				HSSFUtils.fill(cell, cal);
			}else{
				cell = row.createCell(17);//R - Het han ngung len luong
				HSSFUtils.fill(cell, null);
			}
			
			cell = row.createCell(18);//S - IC
			HSSFUtils.fill(cell, data.get("EMPCN"));
			cell = row.createCell(19);//T - Ngay nghi viec
			HSSFUtils.fill(cell, data.get("REAL_OFF_DATE"));
			cell = row.createCell(20);//U - Nghi san
			HSSFUtils.fill(cell, data.get("B_DATE"));
			cell = row.createCell(21);//V - Nghi san vao
			HSSFUtils.fill(cell, data.get("E_DATE"));
			cell = row.createCell(22);//W - Gioi tinh
			HSSFUtils.fill(cell, data.get("SEX"));
			cell = row.createCell(23);//X - Ngay sinh
			HSSFUtils.fill(cell, data.get("BIRTHDAY"));
			cell = row.createCell(24);//Y - Trinh do
			HSSFUtils.fill(cell, data.get("EDUCATION"));
			cell = row.createCell(25);//Z - CMND
			HSSFUtils.fill(cell, data.get("ID_NO"));
			cell = row.createCell(26);//AA - Noi cap
			HSSFUtils.fill(cell, data.get("ID_PLACE"));
			cell = row.createCell(27);//AB - Ngay cap
			HSSFUtils.fill(cell, data.get("NGAYCAP_ID"));
			cell = row.createCell(28);//AC - Noi sinh
			HSSFUtils.fill(cell, data.get("BIRTHPLACE"));
			cell = row.createCell(29);//AD - Dan toc
			HSSFUtils.fill(cell, data.get("ETHNIC"));
			cell = row.createCell(30);//AE - Ton giao
			HSSFUtils.fill(cell, data.get("RELIGION"));
			cell = row.createCell(31);//AF - Dia chi
			HSSFUtils.fill(cell, data.get("PERMANENT_ADDRESS"));
			cell = row.createCell(32);//AG - Quan huyen
			HSSFUtils.fill(cell, data.get("PROVINCE"));
			cell = row.createCell(33);//AH - Tinh thanh
			HSSFUtils.fill(cell, data.get("CITY"));
//			cell = row.createCell(34);//AI - Nguyen quan
//			HSSFUtils.fill(cell, data.get("PERMANENT_ADDRESS"));
			cell = row.createCell(34);//AI - TG tham gia BHXH
			HSSFUtils.fill(cell, data.get("SOCIAL_DATE"));
			cell = row.createCell(35);//AJ - BHXH
			HSSFUtils.fill(cell, data.get("ID_SOCIAL"));
			cell = row.createCell(36);//AK - BHYT
			HSSFUtils.fill(cell, data.get("ID_HEALTH"));
			cell = row.createCell(37);//AL - Ma so thue
			HSSFUtils.fill(cell, data.get("CODE_TAX"));
			cell = row.createCell(38);//AM - Ngay cap MST
			HSSFUtils.fill(cell, data.get("DATE_CODETAX"));
//			cell = row.createCell(39);//AN - so lao dong
//			HSSFUtils.fill(cell, null);
//			cell = row.createCell(40);//AO - Noi quy
//			HSSFUtils.fill(cell, null);
			
			
			if (i%500==0) System.gc();
		}
		try {
			ReportUtils.doExportExcel(wb, "HR_TongHop_T"+month+"-"+year);
		} catch (IOException e) {
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Lỗi!!!");
			e.printStackTrace();
		}
		sw.stop();
		System.out.println("Execute time: " + (float)sw.getTime()/1000 + " s");
	}
}
