package ds.program.fvhr.minh;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.domain.BHXH_TN;
import ds.program.fvhr.domain.N_CT_BHXH_TN;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.task.ATask;
import dsc.util.function.UUID;
import fv.util.ReportUtils;
import fv.util.Vni2Uni;
import nextapp.echo2.app.Column;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;

public class ExportExcelBC extends Column {

	private int modeexport;
	private final int MODEEXPORT_XH = 3;
	private final int MODEEXPORT_TN = 6;
	private final int MODEEXPORT_XHTN = 0;
	
	/**
	 * Creates a new <code>ExportExcelBC</code>.
	 */
	public ExportExcelBC() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	public void exportTH(List<BHXH_TN> list, String date, int fstt)
	{
		try {
			if(list.size()<=0)
				return;
			HSSFWorkbook wb = ReportUtils.loadTemplate("insurance",
			"bhxh_quy_temp.xls");
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			int startRow = 3;
			row = sheet.getRow(0);
			cell = row.getCell(0);
			cell.setCellValue("BẢO HIỂM XA HOI T"+date.split("/")[1]+"/"+date.split("/")[2]);
			cell = row.getCell(4);
			cell.setCellValue("BẢO HIỂM THAT NGHIEP T"+date.split("/")[1]+"/"+date.split("/")[2]);
			cell = row.getCell(7);
			cell.setCellValue("BẢO HIỂM Y TẾ T"+date.split("/")[1]+"/"+date.split("/")[2]);
			row = sheet.getRow(1);
			cell = row.getCell(4);
			String s=date.split("/")[2]+cell.getStringCellValue().substring(4, 9)+date.split("/")[1]+cell.getStringCellValue().substring(11);
			cell.setCellValue(s);
			cell = row.getCell(8);
			cell.setCellValue(s);
			
			row = sheet.getRow(2);
			cell = row.getCell(3);
			String st=date.split("/")[1]+" "+cell.getStringCellValue().substring(3);
			cell.setCellValue(st);
			cell = row.getCell(6);
			cell.setCellValue(st);
			cell = row.getCell(10);
			cell.setCellValue(st);
			int tongnguoi = 0;
			long tongluong = 0;
			if(modeexport==MODEEXPORT_XH||modeexport==MODEEXPORT_TN)
				for (BHXH_TN p: list)
				{
					row = sheet.getRow(startRow++);
					cell = row.getCell(modeexport);
					cell.setCellValue(Double.valueOf(p.getSo_nguoi()));
					row = sheet.getRow(startRow++);
					cell = row.getCell(modeexport);
					cell.setCellValue(Double.valueOf(p.getTong_luong()));
					row = sheet.getRow(startRow++);
					cell = row.getCell(modeexport);
					//ty le bao hiem lay tu file exel mau, neu thay doi ty le thi sua trong file excel
					// tai cell tien chuyen
					cell.setCellFormula(cell.getCellFormula());
					tongnguoi += p.getSo_nguoi();
					tongluong += p.getTong_luong();
				}
				else
					if(modeexport==MODEEXPORT_XHTN)
						for (BHXH_TN p: list)
						{
							row = sheet.getRow(startRow++);
							cell = row.getCell(3);
							cell.setCellValue(Double.valueOf(p.getSo_nguoi()));
							cell = row.getCell(6);
							cell.setCellValue(Double.valueOf(p.getSo_nguoi()));
							row = sheet.getRow(startRow++);
							cell = row.getCell(3);
							cell.setCellValue(Double.valueOf(p.getTong_luong()));
							cell = row.getCell(6);
							cell.setCellValue(Double.valueOf(p.getTong_luong()));
							row = sheet.getRow(startRow++);
							cell = row.getCell(3);
							cell.setCellFormula(cell.getCellFormula());
							cell = row.getCell(6);
							cell.setCellFormula(cell.getCellFormula());
							tongnguoi += p.getSo_nguoi();
							tongluong += p.getTong_luong();
						}
			if(modeexport == MODEEXPORT_XH||modeexport == MODEEXPORT_XH)
			{
				startRow++;
				row = sheet.getRow(startRow++);
				cell = row.getCell(modeexport);
				cell.setCellFormula(cell.getCellFormula());
				row = sheet.getRow(startRow++);
				cell = row.getCell(modeexport);
				cell.setCellFormula(cell.getCellFormula());
				row = sheet.getRow(startRow++);
				cell = row.getCell(modeexport);
				cell.setCellFormula(cell.getCellFormula());
				row = sheet.getRow(startRow++);
				cell = row.getCell(modeexport);
				cell.setCellFormula(cell.getCellFormula());
				row = sheet.getRow(startRow++);
				cell = row.getCell(modeexport);
				cell.setCellFormula(cell.getCellFormula());
			}
			else
				if(modeexport == MODEEXPORT_XHTN)
				{
					startRow++;
					row = sheet.getRow(startRow++);
					cell = row.getCell(3);
					cell.setCellFormula(cell.getCellFormula());
					cell = row.getCell(6);
					cell.setCellFormula(cell.getCellFormula());
					row = sheet.getRow(startRow++);
					cell = row.getCell(3);
					cell.setCellFormula(cell.getCellFormula());
					cell = row.getCell(6);
					cell.setCellFormula(cell.getCellFormula());
					row = sheet.getRow(startRow++);
					cell = row.getCell(3);
					cell.setCellFormula(cell.getCellFormula());
					cell = row.getCell(6);
					cell.setCellFormula(cell.getCellFormula());
					row = sheet.getRow(startRow++);
					cell = row.getCell(3);
					cell.setCellFormula(cell.getCellFormula());
					cell = row.getCell(6);
					cell.setCellFormula(cell.getCellFormula());
					row = sheet.getRow(startRow++);
					cell = row.getCell(3);
					cell.setCellFormula(cell.getCellFormula());
					cell = row.getCell(6);
					cell.setCellFormula(cell.getCellFormula());
				}
			String filename="bhxh_quy_temp_";
			if(fstt!=0)
				filename+="_0"+fstt;
			filename+=date.split("/")[1]+date.split("/")[2]+"_"+System.currentTimeMillis()+".xls";
//			FileOutputStream out = new FileOutputStream(filename);
//			wb.write(out);
//			out.close();
//			MessageDialog dlg = new MessageDialog("Thông báo","Xuất Excel thành công. Mở theo đường dẫn "+ filename, MessageDialog.CONTROLS_OK);
//			Application.getApp().getDefaultWindow().getContent().add(dlg);
			
			File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
			f.deleteOnExit();
			FileOutputStream out = new FileOutputStream(f);
			wb.write(out);
			out.flush();
			out.close();
			File saveFile = new File(f.getParentFile(),URLEncoder.encode(Application.getApp().getLoginInfo().getUserID() + ";application/vnd.ms-excel;"  + filename, "UTF-8"));			
			ReportFileManager.saveTempReportFile(f, saveFile);
			saveFile.deleteOnExit();
			Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
			
		} catch (Exception e) {
			System.out.println(e.toString());
			MessageDialog dlg = new MessageDialog("Lỗi","Xuất Excel thất bại", MessageDialog.CONTROLS_OK);
			Application.getApp().getDefaultWindow().getContent().add(dlg);
		}
		
	}
	
	public String getViewerUrl() {
		HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
		String viewerUrl = request.getRequestURL() + "?" +
		WebRenderServlet.SERVICE_ID_PARAMETER + "=" + ReportService.INSTANCE.getId() + "&" + 
		ReportService.PARAM_TYPE + "=" + (0 == ATask.EXECTYPE_DIRECT ? ReportService.TYPE_TEMP : ReportService.TYPE_OUTPUT) + "&" +
		ReportService.PARAM_KEY + "=";
		return viewerUrl;
	}
	
	public void exportCT(List<N_CT_BHXH_TN> list, String date, String name, int fstt) {
		
		try {
			if(list.size()<=0)
				return;

			HSSFWorkbook wb = ReportUtils.loadTemplate("insurance",
					"KyTrinhBHXH_TN.xls");
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			int startRow = 1;
			for (N_CT_BHXH_TN p: list)
			{
				row = sheet.createRow(startRow++);
				cell = row.createCell(0);
				cell.setCellValue(p.getEMPSN());
				cell = row.createCell(1);
				cell.setCellValue(Double.valueOf(p.getNGAYCONG()));
				cell = row.createCell(2);
				cell.setCellValue(Double.valueOf(p.getLUONGCB()));
				cell = row.createCell(3);
				//cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Double.valueOf(p.getLUONGHD()));
				cell = row.createCell(4);
				cell.setCellValue(Vni2Uni.convertToUnicode(p.getDEPT_NAME()));
				cell = row.createCell(5);
				cell.setCellValue((p.getDEPSN()));
			}
			String filename="KyTrinhBHXH_TN_"+name;
			if(fstt!=0)
				filename+="_0"+fstt;
			filename+="_"+date.split("/")[0]+date.split("/")[1]+"_"+System.currentTimeMillis()+".xls";
			File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
			f.deleteOnExit();
			FileOutputStream out = new FileOutputStream(f);
			wb.write(out);
			out.flush();
			out.close();
			File saveFile = new File(f.getParentFile(),URLEncoder.encode(Application.getApp().getLoginInfo().getUserID() + ";application/vnd.ms-excel;"  + filename, "UTF-8"));			
			ReportFileManager.saveTempReportFile(f, saveFile);
			saveFile.deleteOnExit();
			Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
		} catch (Exception e) {
			System.out.println(e.toString());
			 dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("Lỗi","Xuất Excel thất bại", MessageDialog.CONTROLS_OK);
			Application.getApp().getDefaultWindow().getContent().add(dlg);
		}
	}
	
	public void exportCTBSTH(List<N_CT_BHXH_TN> listBS,List<N_CT_BHXH_TN> listTHo, String date, String name) {
		
		try {
			if(listBS.size()<=0)
				return;

			HSSFWorkbook wb = ReportUtils.loadTemplate("insurance",
					"KyTrinhBHXH_TN_BSTH.xls");
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			int startRow = 1;
			for (N_CT_BHXH_TN p: listBS)
			{
				row = sheet.createRow(startRow++);
				cell = row.createCell(0);
				cell.setCellValue(p.getEMPSN());
				cell = row.createCell(1);
				cell.setCellValue(Double.valueOf(p.getNGAYCONG()));
				cell = row.createCell(2);
				cell.setCellValue(Double.valueOf(p.getLUONGCB()));
				cell = row.createCell(3);
				//cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Double.valueOf(p.getLUONGHD()));
				cell = row.createCell(4);
				cell.setCellValue(Vni2Uni.convertToUnicode(p.getDEPT_NAME()));
				cell = row.createCell(5);
				cell.setCellValue((p.getDEPSN()));
				cell = row.createCell(6);
				cell.setCellValue(Vni2Uni.convertToUnicode(p.getNOTE()));
			}
			HSSFSheet sheet1 = wb.getSheetAt(1);
			HSSFRow row1;
			HSSFCell cell1;
			int startRow1 = 1;
			for (N_CT_BHXH_TN p: listTHo)
			{
				row1 = sheet1.createRow(startRow1++);
				cell1 = row1.createCell(0);
				cell1.setCellValue(p.getEMPSN());
				cell1 = row1.createCell(1);
				cell1.setCellValue(Double.valueOf(p.getNGAYCONG()));
				cell1 = row1.createCell(2);
				cell1.setCellValue(Double.valueOf(p.getLUONGCB()));
				cell1 = row1.createCell(3);
				//cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell1.setCellValue(Double.valueOf(p.getLUONGHD()));
				cell1 = row1.createCell(4);
				cell1.setCellValue(Vni2Uni.convertToUnicode(p.getDEPT_NAME()));
				cell1 = row1.createCell(5);
				cell1.setCellValue((p.getDEPSN()));
				cell1 = row1.createCell(6);
				cell1.setCellValue(Vni2Uni.convertToUnicode(p.getNOTE()));
			}
			String filename="KyTrinhBHXH_TN_BoSung_ThuHoi_"+name;
			filename+="_"+date.split("/")[0]+date.split("/")[1]+"_"+System.currentTimeMillis()+".xls";
			File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
			f.deleteOnExit();
			FileOutputStream out = new FileOutputStream(f);
			wb.write(out);
			out.flush();
			out.close();
			File saveFile = new File(f.getParentFile(),URLEncoder.encode(Application.getApp().getLoginInfo().getUserID() + ";application/vnd.ms-excel;"  + filename, "UTF-8"));			
			ReportFileManager.saveTempReportFile(f, saveFile);
			saveFile.deleteOnExit();
			Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
		} catch (Exception e) {
			System.out.println(e.toString());
			 dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("Lỗi","Xuất Excel thất bại", MessageDialog.CONTROLS_OK);
			Application.getApp().getDefaultWindow().getContent().add(dlg);
		}
	}
	
	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
	}

	public void setModeexport(int modeexport) {
		this.modeexport = modeexport;
	}

	public int getModeexport() {
		return modeexport;
	}

}
