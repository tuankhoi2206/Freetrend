package fv.components;

import java.io.IOException;
import java.util.TooManyListenersException;

import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.fv.app.filetransfer.UploadEvent;
import nextapp.echo2.fv.app.filetransfer.UploadListener;
import nextapp.echo2.fv.app.filetransfer.UploadSelect;

/**
 * A Wrapper of UploadSelect
 * @author Hieu
 *
 */
public class FileUploadSelect extends Row {
	private static final long serialVersionUID = 1L;

	private Label lblFileName;
	private FileWrapper fileWrapper;
	private UploadSelect uploadSelect;
	private ActionListener uploadFinished;
	
	public FileUploadSelect(){
		super();
		initComponents();
	}
	
	public FileWrapper getFileWrapper() {
		return fileWrapper;
	}

	public void setFileWrapper(FileWrapper fileWrapper) {
		this.fileWrapper = fileWrapper;
	}

	public UploadSelect getUploadSelect() {
		return uploadSelect;
	}

	public void setUploadSelect(UploadSelect uploadSelect) {
		this.uploadSelect = uploadSelect;
	}

	public void doSelectNextFile(FileWrapper fileWrapper){
		if (this.fileWrapper!=null) this.fileWrapper.deleteCurrentFile();		
		this.fileWrapper=fileWrapper;
	}

	public void refresh() {
		if (fileWrapper!=null)
			lblFileName.setText(fileWrapper.getName());
		else
			lblFileName.setText("");
	}
	
	public void setFileType(String fileType){
		uploadSelect.setAcceptFileType(fileType);
	}
	
	public void setIcon(ImageReference icon){
		uploadSelect.setIcon(icon);
	}
	
	public void setIconHeight(Extent height){
		uploadSelect.setHeight(height);
	}
	
	public void setIconWidth(Extent width){
		uploadSelect.setWidth(width);
	}
	
	public void setUploadFileSize(int size){
		uploadSelect.setMaxFileSize(size);
	}

	private void initComponents(){
		uploadSelect = new UploadSelect();
		uploadSelect.setEnabledIconStyle(true);
		try {
			uploadSelect.addUploadListener(new UploadListener() {
				
				@Override
				public void invalidFileUpload(UploadEvent uploadEvent) {
					
				}
				
				@Override
				public void fileUpload(UploadEvent uploadEvent) {
					try{
						if (uploadSelect.getAcceptFileType()!=null&&!uploadSelect.getAcceptFileType().trim().equals("")){
							if (!uploadEvent.getContentType().equalsIgnoreCase(uploadSelect.getAcceptFileType())){
		                		Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK, "Chỉ nhận file " + uploadSelect.getAcceptFileType());
		                		uploadEvent.getInputStream().close();
								return;
		                	}
						}
						//check size
						byte[] b = new byte[uploadEvent.getSize()];
	                    uploadEvent.getInputStream().read(b, 0, uploadEvent.getSize());
	                    
//	                    byte[] buffer = new byte[512];
//	                    int len = 0;
//	                    while ((len=uploadEvent.getInputStream().read(buffer))>0)
	                    if (fileWrapper!=null){
	                    	fileWrapper.deleteCurrentFile();
	                    	fileWrapper=null;
	                    }
	                    fileWrapper = new FileWrapper();
	                    fileWrapper.setName(uploadEvent.getFileName());
	                    fileWrapper.setType(uploadEvent.getContentType());
	                    fileWrapper.setContent(b);
	                    refresh();
	                    uploadEvent.getInputStream().close();
	                    
					}catch(IOException e){
						e.printStackTrace();
					}finally{
						if (uploadFinished!=null){
	                    	uploadFinished.actionPerformed(null);
	                    }
					}
				}
			});
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		}
		add(uploadSelect);
		lblFileName = new Label();
		lblFileName.setForeground(Color.BLUE);
		add(lblFileName);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		doSelectNextFile(null);
	}
	
	public ActionListener getUploadFinishedListener(){
		return this.uploadFinished;
	}
	
	public void setUploadFinishedListener(ActionListener action){
		this.uploadFinished=action;
	}
}
