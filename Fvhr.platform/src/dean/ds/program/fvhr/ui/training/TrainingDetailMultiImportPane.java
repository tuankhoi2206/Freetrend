package ds.program.fvhr.ui.training;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Grid;
import ds.program.fvhr.dao.generic.FvGenericDAO;
import ds.program.fvhr.domain.N_TRAINING_DETAILS;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import fv.components.FileUploadSelect;
import fv.components.FileWrapper;
import fv.util.DateFieldValidator;
import fv.util.FvStringUtils;
import fv.util.HSSFUtils;
import fv.util.ListBinder;
import fv.util.Vni2Uni;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.fv.app.filetransfer.Download;
import nextapp.echo2.fv.app.filetransfer.DownloadProvider;
import nextapp.echo2.fv.app.filetransfer.ResourceDownloadProvider;

public class TrainingDetailMultiImportPane extends WindowPane {

	private ResourceBundle resourceBundle;
	private SelectField sfCourse;
	private SelectField sfSubject;
	private SelectField sfType;
	private SelectField sfGuide;
	private DscDateField dfBeginDate;
	private DscDateField dfEndDate;
	private Button btnDownload;
	private Button btnImport;
	private FileUploadSelect fileUploadSelect;
	
	private TrainingDetail01MProgram program;
	private String errorMessage;
	IGenericDAO<N_TRAINING_DETAILS, String> dao;
	
	/**
	 * Creates a new <code>TrainingDetailMultiImportPane</code>.
	 */
	public TrainingDetailMultiImportPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}
	
	public TrainingDetailMultiImportPane(TrainingDetail01MProgram program){
		this();
		this.program=program;
		initUI();
		dao = Application.getApp().getDao(N_TRAINING_DETAILS.class);
	}
	
	private void initUI(){
		dfBeginDate.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dfBeginDate.getDateChooser().setLocale(new Locale("en"));
		dfBeginDate.setSelectedDate(Calendar.getInstance());
		dfEndDate.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dfEndDate.getDateChooser().setLocale(new Locale("en"));
		dfEndDate.setSelectedDate(Calendar.getInstance());
		
		ListBinder.bindSelectField(sfCourse, program.getMasterDataContent().getCourseEditor(), true);
		ListBinder.bindSelectField(sfGuide, program.getMasterDataContent().getGuideEditor(), true);
		ListBinder.bindSelectField(sfType, program.getMasterDataContent().getTypeEditor(), true);
		sfCourse.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String course = (String) ListBinder.get(sfCourse);
				ListBinder.bindSelectField(sfSubject, program.getMasterDataContent().getSubjectEditor(course), true);
			}
		});
	}
	
	private void doDownload(ActionEvent e) {
		Download dl = new Download();
		DownloadProvider provider = new ResourceDownloadProvider("/conf/format/fvhr/training_template.xls","application/vnd.ms-excel");
		dl.setProvider(provider);
		dl.setActive(true);
		Application.getApp().enqueueCommand(dl);
	}
	
	private boolean validateForm(){
		if (fileUploadSelect.getFileWrapper()==null){
			setErrorMessage("Chọn file dữ liệu");
			return false;
		}
		if (sfCourse.getSelectedIndex()<0){
			setErrorMessage("Chọn khóa học");
			return false;
		}
		if (sfSubject.getSelectedIndex()<0){
			setErrorMessage("Chọn môn học");
			return false;
		}
		if (sfType.getSelectedIndex()<0){
			setErrorMessage("Chọn loại huấn luyện");
			return false;
		}
		if (sfGuide.getSelectedIndex()<0){
			setErrorMessage("Chọn cán bộ huấn luyện");
			return false;
		}
		
		String bd = DateFieldValidator.validate(dfBeginDate);
		if (!bd.equals("")){
			setErrorMessage("Chọn ngày bắt đầu");
			return false;
		}
		String ed = DateFieldValidator.validate(dfEndDate);
		if (!ed.equals("")){
			setErrorMessage("Chọn ngày kết thúc");
			return false;
		}
		
		if (dfBeginDate.getSelectedDate().compareTo(dfEndDate.getSelectedDate())>0){
			setErrorMessage("Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc");
			return false;
		}
		
		return true;
	}
	
	private void setErrorMessage(String errorMessage) {
		this.errorMessage=errorMessage;
	}
	
	private boolean find(N_TRAINING_DETAILS d){
		String sql = "from N_TRAINING_DETAILS t where t.EMPSN=? and t.COURSE_ID=? and t.SUBJECT_ID=? and t.BDATE=? and t.EDATE=?";
		List<N_TRAINING_DETAILS> list = dao.find(100, sql, 
				new Object[]{d.getEMPSN(), d.getCOURSE_ID(), d.getSUBJECT_ID(), d.getBDATE(), d.getEDATE()});
		if (list!=null&&list.size()>0) return true;
		return false;
	}

	private void doImport(ActionEvent e) {
		if (!validateForm()){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, errorMessage);
			return;
		}
		FileWrapper fw = fileUploadSelect.getFileWrapper();
		String message="";
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
				cell = row.getCell(1);//exam_date
				Date date = HSSFUtils.getDateCellValue(cell);
				cell = row.getCell(2);//mark
				Double mark = HSSFUtils.getNumericCellValue(wb, cell, true);
				cell = row.getCell(3);//rank
				String rank = HSSFUtils.getStringCellValue(cell, true);
				cell = row.getCell(4);//time
				Double time = HSSFUtils.getNumericCellValue(wb, cell, true);
				cell = row.getCell(5);//note
				String note = HSSFUtils.getStringCellValue(cell, true);
				N_TRAINING_DETAILS detail = new N_TRAINING_DETAILS();
				detail.setEMPSN(empsn);
				detail.setBDATE(dfBeginDate.getSelectedDate().getTime());
				detail.setEDATE(dfEndDate.getSelectedDate().getTime());
				detail.setEXAM_DATE(date);
				detail.setMARK(BigDecimal.valueOf(mark));
				detail.setRANK(Vni2Uni.convertToVNI(rank));
				detail.setEXAM_TIME(BigDecimal.valueOf(time));
				detail.setNOTE(Vni2Uni.convertToVNI(note));
				detail.setCOURSE_ID(ListBinder.get(sfCourse).toString());
				detail.setSUBJECT_ID(ListBinder.get(sfSubject).toString());
				detail.setGUIDE_EMPSN(ListBinder.get(sfGuide).toString());
				detail.setTRAINING_TYPE(ListBinder.get(sfType).toString());
				if (find(detail)) {
					message = message + (i+1) + ", ";
					continue;
				}
				detail.setID_TRAINING(program.getMasterDataContent().genPK(empsn));
				try{
					dao.save(detail);
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
		Application.getApp().showMessageDialog(MessageDialog.TYPE_INFORMATION+MessageDialog.CONTROLS_OK, "Đã import thông tin huấn luyện. " + message);
		fileUploadSelect.doSelectNextFile(null);
	}
	

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Default.Window");
		this.setTitle("Nhập thông tin học viên");
		this.setHeight(new Extent(350, Extent.PX));
		this.setWidth(new Extent(600, Extent.PX));
		this.setModal(true);
		Grid grid2 = new Grid();
		grid2.setInsets(new Insets(new Extent(2, Extent.PX)));
		add(grid2);
		Label label2 = new Label();
		label2.setText("File mẫu");
		grid2.add(label2);
		btnDownload = new Button();
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
				doDownload(e);
			}
		});
		grid2.add(btnDownload);
		Label label1 = new Label();
		label1.setText("Chọn file excel");
		grid2.add(label1);
		fileUploadSelect = new FileUploadSelect();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/excel_upload_icon.png");
		fileUploadSelect.setIcon(imageReference2);
		fileUploadSelect.setFileType("application/vnd.ms-excel");
		fileUploadSelect.setIconWidth(new Extent(24, Extent.PX));
		fileUploadSelect.setIconHeight(new Extent(24, Extent.PX));
		grid2.add(fileUploadSelect);
		Label label4 = new Label();
		label4.setText("Khóa học");
		grid2.add(label4);
		sfCourse = new SelectField();
		grid2.add(sfCourse);
		Label label5 = new Label();
		label5.setText("Môn học");
		grid2.add(label5);
		sfSubject = new SelectField();
		grid2.add(sfSubject);
		Label label6 = new Label();
		label6.setText("Loại huấn luyện");
		grid2.add(label6);
		sfType = new SelectField();
		grid2.add(sfType);
		Label label9 = new Label();
		label9.setText("Cán bộ huấn luyện");
		grid2.add(label9);
		sfGuide = new SelectField();
		grid2.add(sfGuide);
		Label label7 = new Label();
		label7.setText("Ngày bắt đầu");
		grid2.add(label7);
		dfBeginDate = new DscDateField();
		grid2.add(dfBeginDate);
		Label label8 = new Label();
		label8.setText("Ngày kết thúc");
		grid2.add(label8);
		dfEndDate = new DscDateField();
		grid2.add(dfEndDate);
		Label label3 = new Label();
		grid2.add(label3);
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
				doImport(e);
			}
		});
		grid2.add(btnImport);
	}
}
