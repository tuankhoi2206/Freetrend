package ds.program.fvhr.son.ui.yte;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;
import ds.program.fvhr.domain.BaoHiemYT_SYS;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.program.DefaultProgram;
import dsc.echo2app.task.ATask;
import dsc.util.function.UUID;

public class BangUngTien extends DefaultProgram {
	
	public BangUngTien() {
		initComponent();
	}
	private void initComponent() {
		BUTContent content = new BUTContent(this);
		this.add(content);
	}
	
	protected HSSFWorkbook createWorkbook(String nameFileReport) {
		HSSFWorkbook wb = null;
		File 			f_in 	= null ;
		FileInputStream in		= null ;
		try {
			f_in 	= ReportFileManager.getReportFormatFolder("fvhr/FileUngTien.xls");
			in 		= new FileInputStream(f_in);
			wb 		= new HSSFWorkbook(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return wb;
	}

	protected void doExport(HSSFWorkbook wb){
		if(wb == null){
			return;
		}
		
		File f1 = new File(System.getProperty("java.io.tmpdir"), UUID.generate());			
		FileOutputStream out;
		try {
			
			out = new FileOutputStream(f1);
			wb.write(out);
			out.flush();
			out.close();
			String name = "FileUngTien";
			File saveFile = new File(f1.getParentFile(),URLEncoder.encode(Application.getApp().getLoginInfo().getUserID() + ";application/vnd.ms-excel;" + name + Calendar.getInstance().getTimeInMillis() + "" + ".xls", "UTF-8"));			
			ReportFileManager.saveTempReportFile(f1, saveFile);
			Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected String getViewerUrl() {
		HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
		String viewerUrl = request.getRequestURL() + "?" +
		WebRenderServlet.SERVICE_ID_PARAMETER + "=" + ReportService.INSTANCE.getId() + "&" + 
		ReportService.PARAM_TYPE + "=" + (ATask.EXECTYPE_DIRECT == 0 ? ReportService.TYPE_TEMP : ReportService.TYPE_OUTPUT) + "&" +
		ReportService.PARAM_KEY + "=";
		return viewerUrl;
	}
}
