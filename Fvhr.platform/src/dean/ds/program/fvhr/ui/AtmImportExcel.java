package ds.program.fvhr.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.fv.app.filetransfer.UploadEvent;
import nextapp.echo2.fv.app.filetransfer.UploadListener;
import nextapp.echo2.fv.app.filetransfer.UploadSelect;

import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.core.io.Resource;

import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DefaultProgram;

public class AtmImportExcel extends WindowPane {
	private WindowPane panel = this;
	private DefaultProgram program;
	private ResourceBundle bundle;
	private AtmImportExcel(DefaultProgram program)
	{
		this.program = program;
	}
	public UploadSelect getFileUploadExcel(){
		final Resource res = Application.getSpringContext().getResource("classpath:/conf/format/qlns");
		UploadSelect fileUploadSelect = new UploadSelect();
		fileUploadSelect.setEnabledSendButtonText("N_ATM_MANAGER.CONFIRMIMPORT");
		fileUploadSelect.setWidth(new Extent(350,Extent.PX));
		try{
			fileUploadSelect.addUploadListener(new UploadListener(){
				@Override
				public void fileUpload(UploadEvent e) {
					if (!(e.getFileName().endsWith(".xls".toLowerCase()))) {
						Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK, bundle.getString("C_PROPERTIES.XLSIMPORT"));
						return;
					}
					
					try {

						assert res.getFile().exists() && res.getFile().isDirectory() : "";
						StopWatch sw = new StopWatch();
						sw.start();
						File outputFile = new File(res.getFile(), "temp.xls");
						long unwrite = e.getSize();
						OutputStream out = new FileOutputStream(outputFile);
						// Transfer bytes from in to out
						byte[] buf = new byte[1024];
						int len;
						while ((len = e.getInputStream().read(buf)) > 0) {
							// when number of remain bytes is greater than
							// buffer size
							if (unwrite >= len)
								out.write(buf, 0, len);
							else {
								out.write(buf, 0, (int) unwrite);
								break;
							}
							unwrite -= len;
						}
						out.flush();
						out.close();
						importProperties(outputFile);
						outputFile = null;
						sw.stop();
						Application.getApp().getDefaultWindow().getContent().remove(panel);
						program.refresh();
					} catch (IOException e1) {
						// TODO: handle exception
						e1.printStackTrace();
					}
				}

				@Override
				public void invalidFileUpload(UploadEvent e) {
					Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK, bundle.getString("C_PROPERTIES.XLSIMPORT"));
				}	
					
			});
		}
		catch (TooManyListenersException ex) {
			// TODO: handle exception
			throw new RuntimeException(ex.toString());
		}
		return fileUploadSelect;
	}
	
	private void importProperties(File fileExcel){
		InputStream in ;
		try{
			in = new FileInputStream(fileExcel);
			HSSFWorkbook wb = new HSSFWorkbook(in);
			HSSFSheet sheet = wb.getSheetAt(0) ;
			int row = sheet.getPhysicalNumberOfRows();
			if(row <=1){
				Application.getApp().showMessageDialog("Thong Bao!","Ko the import file", MessageDialog.CONTROLS_OK + MessageDialog.TYPE_ERROR);
				
			}
			else{
				
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
		private void initComponent(){
		bundle = ResourceBundle.getBundle("resource.localization.UICaption", ApplicationInstance.getActive().getLocale());
		this.setTitle(bundle.getString("N_ATM_MANAGER.IMPORT"));
		this.setHeight(new Extent(100));
		
	}

}
