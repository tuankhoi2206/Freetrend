package ds.program.fvhr.ui.insurance;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.dao.generic.FvGenericDAO;
import ds.program.fvhr.domain.N_EMP_BONUS_SICKANDMATER;
import ds.program.fvhr.domain.N_TRAINING_DETAILS;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import fv.components.FileUploadSelect;
import fv.components.FileWrapper;
import fv.util.FvStringUtils;
import fv.util.HSSFUtils;
import fv.util.ListBinder;
import fv.util.Vni2Uni;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.fv.app.filetransfer.Download;
import nextapp.echo2.fv.app.filetransfer.DownloadProvider;
import nextapp.echo2.fv.app.filetransfer.ResourceDownloadProvider;

public class BonusSickAndMaterImport  extends WindowPane{

	private FileUploadSelect fileUploadSelect;
	private DscDateField dfThoiGian;
	private Button btnImport;
	private N_EMP_BONUS_SICKANDMATER01MProgram program;
	private IGenericDAO<N_EMP_BONUS_SICKANDMATER,String> dao;
	private Button btnDownload;
	private String errorMessage;
	
	public BonusSickAndMaterImport(){
		super();
		initComponetns();
	}
	
	
	public BonusSickAndMaterImport(N_EMP_BONUS_SICKANDMATER01MProgram program){
		this();
		this.program= program;		
		initUI();
		dao= Application.getApp().getDao(N_EMP_BONUS_SICKANDMATER.class);
	}
	
	private void initUI(){
		dfThoiGian.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dfThoiGian.getDateChooser().setLocale(Locale.ENGLISH);
	}

	private void doDownload(ActionEvent e) {
		Download dl = new Download();
		DownloadProvider provider = new ResourceDownloadProvider("/conf/format/fvhr/BonusSickAndMater.xls","application/vnd.ms-excel");
		dl.setProvider(provider);
		dl.setActive(true);
		Application.getApp().enqueueCommand(dl);
	}	
	
	private void setErrorMessage(String errorMessage){
		this.setErrorMessage(errorMessage);
	}
	
	private boolean validateForm(){
		if (fileUploadSelect.getFileWrapper()==null){
			setErrorMessage("Chọn file dữ liệu import!");
			return false;
		}			
		return true;
	}
	
	private void doImport(ActionEvent e) {

		
		FileWrapper fw = fileUploadSelect.getFileWrapper();
		
		if (!validateForm()){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, errorMessage);
			return;
		}
		
		String userId = Application.getApp().getLoginInfo().getUserID();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Date NgayHH = new Date();	
		sdf.format(NgayHH);
		String message="";
		//String thangBaoCao =sdf.format(dfThoiGian.getDisplayedDate());
		
		try {
			FileInputStream in = new FileInputStream(fw.getFile());
			HSSFWorkbook wb = new HSSFWorkbook(in);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			
			for (int i=1;i<sheet.getPhysicalNumberOfRows();i++){
				row = sheet.getRow(i);
				if (row==null) continue;
				cell = row.getCell(0);//empsn
				String empsn = FvStringUtils.fixEmpsn(HSSFUtils.getStringCellValue(cell, true).trim());
				if (!FvGenericDAO.getInstanse().isWorking(empsn, true)){
					message = message + (i+1) + ", ";
					continue;
				}
				cell = row.getCell(1);//ThoiGian
				Date thoiGian = HSSFUtils.getDateCellValue(cell);
				cell = row.getCell(2);//TienOmDau
				Double money = HSSFUtils.getNumericCellValue(wb, cell, true);
				cell = row.getCell(3);//GhiChu
				String note = HSSFUtils.getStringCellValue(cell, true);
				
				N_EMP_BONUS_SICKANDMATER bonusSick = new N_EMP_BONUS_SICKANDMATER();
				bonusSick.setEMPSN(empsn);
				bonusSick.setTHANG(thoiGian);
				bonusSick.setMONEY(money);
				bonusSick.setNOTE(note);
				bonusSick.setUSER_UPDATED(userId);
				bonusSick.setDATE_UPDATED(NgayHH);				
				
				try{
					dao.save(bonusSick);
				}catch(Exception e1){
					e1.printStackTrace();
					message = message + (i+1) + ", ";
					continue;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if (!message.equals("")){
			message = "\r\nLỗi dòng: " + message;
		}
		Application.getApp().showMessageDialog(MessageDialog.TYPE_INFORMATION+MessageDialog.CONTROLS_OK, "Đã import tiền ốm đau thai sản của tháng. " + message);
		fileUploadSelect.doSelectNextFile(null);
	}
	
	private void initComponetns(){
		this.setStyleName("Default.Window");
		this.setTitle("Cập nhật tiền ốm đau, thai sản.");
		this.setHeight(new Extent(350, Extent.PX));
		this.setWidth(new Extent(600,Extent.PX));
		this.setModal(true);
		
		Grid grid2 = new Grid();
		grid2.setInsets(new Insets(new Extent(2, Extent.PX)));
		add(grid2);
		Label label1 =new Label();
		label1.setText("Tải file mẫu ");
		grid2.add(label1);
		
		btnDownload= new Button();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/download.gif");
		btnDownload.setIcon(imageReference1);
		btnDownload.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		btnDownload.setHeight(new Extent(16, Extent.PX));
		btnDownload.setWidth(new Extent(16, Extent.PX));
		btnDownload.setBackground(new Color(0xc0c0c0));
		btnDownload.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnDownload.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x004000), Border.STYLE_SOLID));
		btnDownload.setToolTipText("Tải file mẫu");
		btnDownload.setRolloverForeground(new Color(0x008040));
		btnDownload.setRolloverEnabled(true);
		btnDownload.setForeground(new Color(0x800000));
		btnDownload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doDownload(e);				
			}
		});
		
		grid2.add(btnDownload);
		
/*		Label label2 =new Label();
		label2.setText("Số Thẻ (text),Thời Gian(date dd/mm/yyyy), Tiền, Ghi chú ");
		grid2.add(label2);		
*/		
		Label label3 = new Label();
		label3.setText("Chọn thời gian chạy báo cáo");
		grid2.add(label3);
		dfThoiGian = new DscDateField();
		grid2.add(dfThoiGian);		
		Label label4 = new Label();
		label4.setText("Chọn file excel");
		grid2.add(label4);
		fileUploadSelect= new FileUploadSelect();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/excel_upload_icon.png");
		fileUploadSelect.setIcon(imageReference2);
		//fileUploadSelect.setFileType("application/vnd.ms-excel");
		
		fileUploadSelect.setIconWidth(new Extent(24, Extent.PX));
		fileUploadSelect.setIconHeight(new Extent(24, Extent.PX));
		grid2.add(fileUploadSelect);
		btnImport = new Button();
		btnImport.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		btnImport.setText("Bắt đầu");
		btnImport.setBackground(new Color(0x004080));
		btnImport.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnImport.setWidth(new Extent(80, Extent.PX));
		btnImport.setPressedEnabled(true);
		btnImport.setPressedForeground(Color.RED);
		btnImport.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x008080), Border.STYLE_SOLID));
		btnImport.setRolloverForeground(new Color(0xff8080));
		btnImport.setRolloverEnabled(true);
		btnImport.setForeground(new Color(0x0080ff));		
		btnImport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doImport(e);
			}
		});
		grid2.add(btnImport);	
		
	}
}
