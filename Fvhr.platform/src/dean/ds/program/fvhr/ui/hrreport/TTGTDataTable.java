package ds.program.fvhr.ui.hrreport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import ds.program.fvhr.dao.hrreport.ReportDao;
import fv.util.HSSFUtils;
import fv.util.ReportUtils;

public class TTGTDataTable extends AbstractReportDataTable<TTGT>{

	private List<TTGT> listData;
	
	public TTGTDataTable(ReportDao dao) {
		super(dao);
		getTable().setVniColumns(new String[]{"group", "deptName"});
	}

	@Override
	public Class<TTGT> getDataClass() {
		return TTGT.class;
	}

	@Override
	public String[] getHeaders() {
		return new String[]{"fact", "group", "deptName", "deptId", "totalCB", "totalCN", "total", "totalTT",				
				"realCB", "realCN", "realTotal", "totalRestPay", "totalRest", "totalNWHour", 
				"totalLeave", "totalNewWKR","totalNull"};
}

	@Override
	public List<TTGT> getListData(Map<String, Object> params) {
		listData =  getDao().getTTGTList(params);
		return listData;
	}

	@Override
	public Map<String, String> getColumnHeaderCaptionMap() {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("fact", "OT_REPORT.FACT");
		map.put("group", "OT_REPORT.TT_GROUP");
		map.put("deptName", "OT_REPORT.TT_DEPT_NAME");
		map.put("deptId", "OT_REPORT.TT_DEPT_ID");
		map.put("totalCB", "OT_REPORT.TOTAL_CB");
		map.put("totalCN", "OT_REPORT.TOTAL_CN");
		map.put("total", "OT_REPORT.TOTAL_CBCN");
		map.put("totalTT", "OT_REPORT.TOTAL_TT");
		map.put("realCB", "OT_REPORT.REAL_CB");
		map.put("realCN", "OT_REPORT.REAL_CN");
		map.put("realTotal", "OT_REPORT.REAL_TOTAL");
		map.put("totalRestPay", "OT_REPORT.TOTAL_RESTPAY");
		map.put("totalRest", "OT_REPORT.TOTAL_REST");
		map.put("totalMaternity", "OT_REPORT.TOTAL_MATER");
		map.put("totalNWHour", "OT_REPORT.TOTAL_NWHOUR");
		map.put("totalLeave", "OT_REPORT.TOTAL_LEAVE");
		map.put("totalNewWKR", "OT_REPORT.TOTAL_NEWWK");
		map.put("totalNull", "OT_REPORT.NULL1");
		
		return map;
	}

	@Override
	public HSSFWorkbook export() {
		if (listData==null||listData.size()==0) return null;
/*		
		HSSFWorkbook wb = ReportUtils.loadTemplate("fvhr", "TT_GT2.xls");		
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int i=4, start=5, end=0;
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("VNI-Times");
		HSSFDataFormat format = wb.createDataFormat();
		style.setDataFormat(format.getFormat("0.0%"));
		
		for (TTGT data:listData){
			row = sheet.createRow(i++);			
			cell = row.createCell(0);
			cell.setCellValue(data.getFact());
			cell = row.createCell(1);
			cell.setCellValue(data.getDeptId());
			cell = row.createCell(2);
			cell.setCellValue(data.getGroup());
			cell = row.createCell(3);
			cell.setCellValue(data.getDeptName());
			cell = row.createCell(4);
			cell.setCellValue(data.getTotalCB());
			cell = row.createCell(5);
			cell.setCellValue(data.getTotalCN());
			cell = row.createCell(6);
			cell.setCellValue(data.getTotal());
			cell = row.createCell(7);
			cell.setCellValue(data.getTotalTT());
			cell = row.createCell(8);
			cell.setCellFormula("G"+i+"-H"+i);
			HSSFUtils.evalFormula(wb, cell);
//			cell.setCellValue(data.getTotalGT());
			cell = row.createCell(9);
			cell.setCellValue(data.getRealCB());
			cell = row.createCell(10);
			cell.setCellValue(data.getRealCN());
			cell = row.createCell(11);
			cell.setCellValue(data.getRealTotal());
			cell = row.createCell(12);
			cell.setCellValue(data.getRealTT());
			cell = row.createCell(13);
			cell.setCellFormula("L"+i+"-M"+i);
			HSSFUtils.evalFormula(wb, cell);
//			cell.setCellValue(data.getRealGT());
			cell = row.createCell(14);
			cell.setCellStyle(style);
			cell.setCellFormula("IF(G"+i+"<>0,L"+i+"/G"+i+",0)");
			HSSFUtils.evalFormula(wb, cell);
			//cell.setCellValue(data.getScale1());
			cell = row.createCell(15);
			cell.setCellStyle(style);
			cell.setCellFormula("IF(H"+i+"<>0,M"+i+"/H"+i+",0)");
			HSSFUtils.evalFormula(wb, cell);
//			cell.setCellValue(data.getScale2());
			cell = row.createCell(16);
			cell.setCellStyle(style);
			cell.setCellFormula("IF(G"+i+"<>0,H"+i+"/G"+i+",0)");
			HSSFUtils.evalFormula(wb, cell);
//			cell.setCellValue(data.getScale3());
			cell = row.createCell(17);
			cell.setCellStyle(style);
			cell.setCellFormula("IF(G"+i+"<>0,I"+i+"/G"+i+",0)");
			HSSFUtils.evalFormula(wb, cell);
//			cell.setCellValue(data.getScale4());
			cell = row.createCell(18);
			cell.setCellValue(data.getTotalRestPay());
			cell = row.createCell(19);
			cell.setCellValue(data.getTotalRest());
			cell = row.createCell(20);
			cell.setCellValue(data.getTotalNWHour());
			cell = row.createCell(21);
			cell.setCellValue(data.getTotalLeave());
			cell = row.createCell(22);
			cell.setCellValue(data.getTotalNewWKR());
			cell = row.createCell(24);
			cell.setCellValue(data.getTotalNull());			
			end=i;
		}
		HSSFCellStyle style1=wb.createCellStyle();
		HSSFFont font1 = wb.createFont();
		font1.setFontHeightInPoints((short) 11);
		font1.setFontName("VNI-Times");
		font1.setBoldweight((short) 400);
		style1.setFont(font1);
		
		row = sheet.createRow(end);
		cell = row.createCell(3);
		cell.setCellStyle(style1);
		cell.setCellValue("Total: ");
		cell = row.createCell(4);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(E"+start+":E"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(5);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(F"+start+":F"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(6);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(G"+start+":G"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(7);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(H"+start+":H"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(8);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(I"+start+":I"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(9);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(J"+start+":J"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(10);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(K"+start+":K"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(11);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(L"+start+":L"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(12);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(M"+start+":M"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(13);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(N"+start+":N"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(14);
		cell.setCellStyle(style);
		cell.setCellFormula("AVERAGE(O"+start+":O"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(15);
		cell.setCellStyle(style);
		cell.setCellFormula("AVERAGE(P"+start+":P"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(16);
		cell.setCellStyle(style);		
		cell.setCellFormula("AVERAGE(Q"+start+":Q"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(17);
		cell.setCellStyle(style);
		cell.setCellFormula("AVERAGE(R"+start+":R"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(18);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(S"+start+":S"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(19);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(T"+start+":T"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(20);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(U"+start+":U"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(21);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(V"+start+":V"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(22);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(W"+start+":W"+end+")");
		cell = row.createCell(24);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(Y"+start+":Y"+end+")");		
		HSSFUtils.evalFormula(wb, cell);		
*/		
		//HA TT_GT_GM_VP
		
		HSSFWorkbook wb = ReportUtils.loadTemplate("fvhr", "TT_GT_072013.xls");		
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int i=4, start=5, end=0;
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("VNI-Times");
		HSSFDataFormat format = wb.createDataFormat();
		style.setDataFormat(format.getFormat("0.0%"));
		
		for (TTGT data:listData){
			row = sheet.createRow(i++);			
			cell = row.createCell(0);
			cell.setCellValue(data.getFact());
			cell = row.createCell(1);
			cell.setCellValue(data.getDeptId());
			cell = row.createCell(2);
			cell.setCellValue(data.getGroup());
			cell = row.createCell(3);
			cell.setCellValue(data.getDeptName());
			cell = row.createCell(4);
			cell.setCellValue(data.getTotalCB());
			cell = row.createCell(5);
			cell.setCellValue(data.getTotalCN());
			cell = row.createCell(6);
			cell.setCellValue(data.getTotal());
			cell = row.createCell(7);
			cell.setCellValue(data.getTotalTT());
			cell = row.createCell(8);
			//cell.setCellFormula("G"+i+"-H"+i);
			//HSSFUtils.evalFormula(wb, cell);
			cell.setCellValue(data.getTotalGT());
			cell = row.createCell(9);
			cell.setCellValue(data.getTotalGM());
			cell = row.createCell(10);
			cell.setCellValue(data.getTotalVP());
			
			cell = row.createCell(11);
			cell.setCellValue(data.getRealCB());
			cell = row.createCell(12);
			cell.setCellValue(data.getRealCN());
			cell = row.createCell(13);
			cell.setCellValue(data.getRealTotal());
			cell = row.createCell(14);
			cell.setCellValue(data.getRealTT());
			cell = row.createCell(15);
			//cell.setCellFormula("L"+i+"-M"+i);
			//HSSFUtils.evalFormula(wb, cell);
			cell.setCellValue(data.getRealGT());
			cell = row.createCell(16);
			cell.setCellValue(data.getRealGM());
			cell = row.createCell(17);
			cell.setCellValue(data.getRealVP());
			
			cell = row.createCell(18);
			cell.setCellStyle(style);
			cell.setCellFormula("IF(G"+i+"<>0,N"+i+"/G"+i+",0)");
			HSSFUtils.evalFormula(wb, cell);
			//cell.setCellValue(data.getScale1());
			cell = row.createCell(19);
			cell.setCellStyle(style);
			cell.setCellFormula("IF(H"+i+"<>0,O"+i+"/H"+i+",0)");
			HSSFUtils.evalFormula(wb, cell);
//			cell.setCellValue(data.getScale2());
			cell = row.createCell(20);
			cell.setCellStyle(style);
			cell.setCellFormula("IF(G"+i+"<>0,H"+i+"/G"+i+",0)");
			HSSFUtils.evalFormula(wb, cell);
//			cell.setCellValue(data.getScale3());
			cell = row.createCell(21);
			cell.setCellStyle(style);
			cell.setCellFormula("IF(G"+i+"<>0,I"+i+"/G"+i+",0)");
			HSSFUtils.evalFormula(wb, cell);
//			cell.setCellValue(data.getScale4());
			cell = row.createCell(22);
			cell.setCellStyle(style);
			cell.setCellFormula("IF(G"+i+"<>0,J"+i+"/G"+i+",0)");
			HSSFUtils.evalFormula(wb, cell);
//			cell.setCellValue(data.getScale5());
			cell = row.createCell(23);
			cell.setCellStyle(style);
			cell.setCellFormula("IF(G"+i+"<>0,K"+i+"/G"+i+",0)");
			HSSFUtils.evalFormula(wb, cell);
//			cell.setCellValue(data.getScale6());
			
			cell = row.createCell(24);
			cell.setCellValue(data.getTotalRestPay());
			cell = row.createCell(25);// NGHI KO LUONG: COPHEP KO LUONG, NGHI SAN, LOAI TRU NGHI BU
			cell.setCellValue(data.getTotalRest());
			cell = row.createCell(26);
			cell.setCellValue(data.getTotalNWHour());
			cell = row.createCell(27);
			cell.setCellValue(data.getTotalLeave());
			cell = row.createCell(28);
			cell.setCellValue(data.getTotalNewWKR());
			cell = row.createCell(30);
			cell.setCellValue(data.getTotalNull());			
			end=i;
		}
		HSSFCellStyle style1=wb.createCellStyle();
		HSSFFont font1 = wb.createFont();
		font1.setFontHeightInPoints((short) 11);
		font1.setFontName("VNI-Times");
		font1.setBoldweight((short) 400);
		style1.setFont(font1);
		
		row = sheet.createRow(end);
		cell = row.createCell(3);
		cell.setCellStyle(style1);
		cell.setCellValue("Total: ");
		cell = row.createCell(4);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(E"+start+":E"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(5);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(F"+start+":F"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(6);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(G"+start+":G"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(7);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(H"+start+":H"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(8);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(I"+start+":I"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(9);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(J"+start+":J"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(10);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(K"+start+":K"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(11);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(L"+start+":L"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(12);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(M"+start+":M"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(13);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(N"+start+":N"+end+")");		
		HSSFUtils.evalFormula(wb, cell);		
		cell = row.createCell(14);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(O"+start+":O"+end+")");		
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(15);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(P"+start+":P"+end+")");		
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(16);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(Q"+start+":Q"+end+")");		
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(17);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(R"+start+":R"+end+")");		
		HSSFUtils.evalFormula(wb, cell);		
		
		cell = row.createCell(18);
		cell.setCellStyle(style);
		cell.setCellFormula("AVERAGE(S"+start+":S"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(19);
		cell.setCellStyle(style);
		cell.setCellFormula("AVERAGE(T"+start+":T"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(20);
		cell.setCellStyle(style);		
		cell.setCellFormula("AVERAGE(U"+start+":U"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(21);
		cell.setCellStyle(style);
		cell.setCellFormula("AVERAGE(V"+start+":V"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(22);
		cell.setCellStyle(style);
		cell.setCellFormula("AVERAGE(W"+start+":W"+end+")");		
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(23);
		cell.setCellStyle(style);
		cell.setCellFormula("AVERAGE(X"+start+":X"+end+")");
		HSSFUtils.evalFormula(wb, cell);		
		
		cell = row.createCell(24);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(Y"+start+":Y"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(25);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(Z"+start+":Z"+end+")");
		HSSFUtils.evalFormula(wb, cell);		
		cell = row.createCell(26);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(AA"+start+":AA"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(27);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(AB"+start+":AB"+end+")");
		HSSFUtils.evalFormula(wb, cell);
		cell = row.createCell(28);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(AC"+start+":AC"+end+")");
		cell = row.createCell(30);
		cell.setCellStyle(style1);
		cell.setCellFormula("SUM(AE"+start+":AE"+end+")");		
		HSSFUtils.evalFormula(wb, cell);		
		//END HA 
		return wb;
	}
}
