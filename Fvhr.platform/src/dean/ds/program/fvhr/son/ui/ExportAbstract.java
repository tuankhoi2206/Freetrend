package ds.program.fvhr.son.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.ngan.ui.DeptUserControl_N;
import dsc.echo2app.Application;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.task.ATask;
import dsc.util.function.UUID;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;

public abstract class ExportAbstract extends WindowPane {//implements ActionHSSFWorkbookl_I {
	
	protected Column rootlayout ;
	Label   errLabel;
	Button	btn_ok;
	Button  btn_cancel;
	
	protected DeptUserControl myDeptControl ;
	HSSFWorkbook wb;
	public ExportAbstract() {
		
		this.setTitle("Chọn Thông Tin");
		this.setClosable(true);
		this.setStyleName("Default.Window");
		this.setWidth(new Extent(300));
		this.setHeight(new Extent(260));
		InitCompnent();
		
	}

	
	
	private void InitCompnent() {
		rootlayout = new Column();
		rootlayout.setCellSpacing(new Extent(15));
		rootlayout.setInsets(new Insets(10));
		this.add(rootlayout);
		
		myDeptControl 	= new DeptUserControl(false,true);
		rootlayout.add(myDeptControl);
		
		btn_ok			= new Button();
		btn_ok.setText("OK");
		btn_ok.setWidth(new Extent(100));
		btn_ok.setStyleName("Default.ToolbarButton");
		btn_ok.setBackground(Color.DARKGRAY);
		btn_ok.setForeground(Color.WHITE);
		btn_ok.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btn_ok.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				actionButtonOK();
			}
		});
		
		btn_cancel	= new Button();
		btn_cancel.setText("Cancel");
		btn_cancel.setWidth(new Extent(100));
		btn_cancel.setStyleName("Default.ToolbarButton");
		btn_cancel.setBackground(Color.DARKGRAY);
		btn_cancel.setForeground(Color.WHITE);
		btn_cancel.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btn_cancel.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				
				userClose();
				
			}
		});
		
		Row btn_row = new Row();
		btn_row.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btn_row.add(btn_ok);
		btn_row.add(btn_cancel);
		
		doLayout();
		errLabel = new Label(".");
		rootlayout.add(errLabel);
		rootlayout.add(btn_row);
		
		
		
	}
	
	

	
	protected void doLayout() {
		// TODO Auto-generated method stub
		
	}



	protected void actionButtonOK() {
		wb	= new HSSFWorkbook();
		wb  = data_excel_Performed();
		doExport();
		
	}

	protected abstract HSSFWorkbook data_excel_Performed();
	
	private void doExport(){
		if(wb == null){
			errLabel.setText(" !!! ĐIỀU KIỆN XUẤT ???");
			errLabel.setForeground(Color.RED);
			return;
		}
		
		File f1 = new File(System.getProperty("java.io.tmpdir"), UUID.generate());			
		FileOutputStream out;
		try {
			
			out = new FileOutputStream(f1);
			wb.write(out);
			out.flush();
			out.close();
			String name = "DANH_SACH";
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
		userClose();
		
	}
	
	
	private String getViewerUrl() {
		HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
		String viewerUrl = request.getRequestURL() + "?" +
		WebRenderServlet.SERVICE_ID_PARAMETER + "=" + ReportService.INSTANCE.getId() + "&" + 
		ReportService.PARAM_TYPE + "=" + (ATask.EXECTYPE_DIRECT == 0 ? ReportService.TYPE_TEMP : ReportService.TYPE_OUTPUT) + "&" +
		ReportService.PARAM_KEY + "=";
		return viewerUrl;
	}

}
