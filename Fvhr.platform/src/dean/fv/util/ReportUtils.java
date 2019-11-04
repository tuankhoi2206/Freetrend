package fv.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.util.function.UUID;
/**
 * Report export utilities
 * @author Hieu
 *
 */
public class ReportUtils {
	
	public static HSSFWorkbook loadTemplate(String folder, String fileName){
		File f = ReportFileManager.getReportFormatFolder(folder+"/"+fileName);
		try {
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(f));
			return wb;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	public static void doExportExcel(HSSFWorkbook wb, String reportFileName) throws IOException{
		if (wb==null){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Không có dữ liệu.");
			return;
		}
		File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
		f.deleteOnExit();
		FileOutputStream out = new FileOutputStream(f);
		wb.write(out);
		out.flush();
		out.close();
		File saveFile = new File(f.getParentFile(),URLEncoder.encode(Application.getApp().getLoginInfo().getUserID() + ";application/vnd.ms-excel;"  + reportFileName + "_" + Calendar.getInstance().getTimeInMillis() + ".xls", "UTF-8"));			
		ReportFileManager.saveTempReportFile(f, saveFile);
		saveFile.deleteOnExit();
		Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
	}
	
	public static void doExportPdf(JasperPrint jp, String reportFileName){
		if (jp==null){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Không có dữ liệu.");
			return;
		}
		File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
		f.deleteOnExit();
		try {			
			JasperExportManager.exportReportToPdfFile(jp, f.getPath());
			File saveFile = new File(f.getParentFile(),URLEncoder.encode(Application.getApp().getLoginInfo().getUserID() + ";application/pdf;"  + reportFileName + "_" + Calendar.getInstance().getTimeInMillis() + ".pdf", "UTF-8"));			
			ReportFileManager.saveTempReportFile(f, saveFile);
			saveFile.deleteOnExit();
			Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
		} catch (JRException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			System.gc();
		}
	}
	
	public static void doExportHtml(JasperPrint jp, String filePath){
		try {
			JasperExportManager.exportReportToHtmlFile(jp, filePath);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	public static String getViewerUrl() {
		HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
		String viewerUrl = request.getRequestURL() + "?" +
		WebRenderServlet.SERVICE_ID_PARAMETER + "=" + ReportService.INSTANCE.getId() + "&" + 
		ReportService.PARAM_TYPE + "=" + ReportService.TYPE_TEMP + "&" +
		ReportService.PARAM_KEY + "=";
		return viewerUrl;
	}
}
