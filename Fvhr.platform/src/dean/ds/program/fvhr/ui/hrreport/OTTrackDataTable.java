package ds.program.fvhr.ui.hrreport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

import ds.program.fvhr.dao.hrreport.ReportDao;
import fv.util.ReportUtils;

public class OTTrackDataTable extends AbstractReportDataTable<OTTrack>{
	private boolean isCR=true;
	private List<OTTrack> listData;
	private Calendar cal = Calendar.getInstance();
	private Date date1;
	private Date date2;

	public OTTrackDataTable(ReportDao dao) {
		super(dao);
		getTable().setVniColumns(new String[]{"fullName", "group", "nameDept"});
	}

	@Override
	public Class<OTTrack> getDataClass() {
		return OTTrack.class;
	}

	@Override
	public String[] getHeaders() {
		return new String[]{"empsn", "fullName", "fact", "group", "nameDept", "empcn", "shift"};
	}

	@Override
	public List<OTTrack> getListData(Map<String, Object> params) {
		date1 = (Date) params.get("date1");
		date2 = (Date) params.get("date2");
		listData = getDao().getOTTrackList(params, isCR);
		return listData;
	}

	@Override
	public Map<String, String> getColumnHeaderCaptionMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("empsn", "OT_REPORT.EMPSN");
		map.put("fullName", "OT_REPORT.FULL_NAME");
		map.put("fact", "OT_REPORT.FACT");
		map.put("group", "OT_REPORT.GROUP");
		map.put("nameDept", "OT_REPORT.DEPT_NAME");
		map.put("empcn", "OT_REPORT.EMPCN");
		map.put("shift", "OT_REPORT.SHIFT");
		return map;
	}

	public boolean isCR() {
		return isCR;
	}

	public void setCR(boolean isCR) {
		this.isCR = isCR;
	}

	public List<OTTrack> getDataList(){
		return listData;
	}
	
	private String dateTitle(Date date){
		cal.setTime(date);
		return (cal.get(Calendar.MONTH) + 1) + "月" + cal.get(Calendar.DAY_OF_MONTH) + "日" + "\r\n" + cdayOfWeek(cal);
	}
	
	private String cdayOfWeek(Calendar cal){
		if (cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
			return "一";
		}else if (cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY){
			return "二";
		}else if (cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY){
			return "三";
		}else if (cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY){
			return "四";
		}else if (cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY){
			return "五";
		}else if (cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
			return "六";
		}else if (cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			return "星期日";
		}
		return "";
	}
	
	@Override
	public HSSFWorkbook export() {
		if (listData==null||listData.size()<=0) return null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(date2.getTime());
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);
		if (isCR){
			HSSFWorkbook wb = ReportUtils.loadTemplate("fvhr", "OT_tracking_form.xls");
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(date1.getTime());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			int j = 7;
			int i = 4;//start row
			while (cal.compareTo(cal2)<=0){
				row = sheet.getRow(0);
				cell = row.getCell(j);
				cell.setCellValue(dateTitle(cal.getTime()));
				row = sheet.getRow(3);
				cell = row.getCell(j);
				cell.setCellValue(sdf.format(cal.getTime()));
				j+=3;
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			int t=j;
			for (int k=0;k<4;k++){
				row = sheet.getRow(k);
				j=t;
				for (;j<100;j++){
					cell = row.getCell(j);
					if (cell!=null) row.removeCell(cell);
				}				
			}
			HSSFCellStyle style = wb.createCellStyle();
			style.setFillForegroundColor(HSSFColor.YELLOW.index);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			HSSFFont font = wb.createFont();
			font.setFontName("VNI-Times");
			font.setFontHeightInPoints((short) 8);
			style.setFont(font);
			for (OTTrack data:listData){
				j=7;
//				System.out.println("Create row " + i);
				cal.setTimeInMillis(date1.getTime());
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				row = sheet.createRow(i++);
				cell = row.createCell(0);
				cell.setCellValue(data.getEmpsn());
				cell = row.createCell(1);
				cell.setCellValue(data.getFullName());
				cell = row.createCell(2);
				cell.setCellValue(data.getFact());
				cell = row.createCell(3);
				cell.setCellValue(data.getGroup());
				cell = row.createCell(4);
				cell.setCellValue(data.getNameDept());
				cell = row.createCell(5);
				cell.setCellValue(data.getEmpcn());
				cell = row.createCell(6);
				cell.setCellValue(data.getShift());
				Map<Date, OTCRTrackDetail> map = data.getDetailsCRMap();
				while (cal.compareTo(cal2)<=0){
					OTCRTrackDetail detail = map.get(cal.getTime());
					cal.add(Calendar.DAY_OF_MONTH, 1);
					if (detail==null) {
						j+=3;
						continue;
					}
//					System.out.println("Create cell " + j);
					cell = row.createCell(j++);
					cell.setCellValue(detail.getOutTime());
					cell = row.createCell(j++);
					cell.setCellValue(detail.getOverTime());
					cell = row.createCell(j++);
					cell.setCellStyle(style);
					if (detail.getOtd()!=null)
						cell.setCellValue(detail.getOtd().doubleValue());
				}
			}
			return wb;
		}else{
			HSSFWorkbook wb = ReportUtils.loadTemplate("fvhr", "OT_tracking_form1.xls");
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(date1.getTime());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			int j = 7;
			int i = 1;//start row
			row = sheet.getRow(0);
			while (cal.compareTo(cal2)<=0){
				cell = row.getCell(j++);
				cell.setCellValue(sdf.format(cal.getTime()));
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			for (;j<39;j++){
				cell = row.getCell(j);
				if (cell!=null) row.removeCell(cell);
			}
			for (OTTrack data:listData){
				j=7;
//				System.out.println("Create row " + i);
				cal.setTimeInMillis(date1.getTime());
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				row = sheet.createRow(i++);
				cell = row.createCell(0);
				cell.setCellValue(data.getEmpsn());
				cell = row.createCell(1);
				cell.setCellValue(data.getFullName());
				cell = row.createCell(2);
				cell.setCellValue(data.getFact());
				cell = row.createCell(3);
				cell.setCellValue(data.getGroup());
				cell = row.createCell(4);
				cell.setCellValue(data.getNameDept());
				cell = row.createCell(5);
				cell.setCellValue(data.getEmpcn());
				cell = row.createCell(6);
				cell.setCellValue(data.getShift());
				Map<Date, OTRSTrackDetail> map = data.getDetailsRSMap();
				while (cal.compareTo(cal2)<=0){
					OTRSTrackDetail detail = map.get(cal.getTime());
					cal.add(Calendar.DAY_OF_MONTH, 1);
					if (detail==null) {
						j++;
						continue;
					}
//					System.out.println("Create cell " + j);
					cell = row.createCell(j++);
					if (detail.getOt()!=null)
						cell.setCellValue(detail.getOt().doubleValue());
				}
			}
			return wb;
		}
	}
}
