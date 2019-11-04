package ds.program.fvhr.ui.mst;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

import ds.program.fvhr.dao.hrreport.ReportDao;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_EMP_TAXCODE;
import ds.program.fvhr.domain.pk.N_EMP_TAXCODEPk;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DefaultProgram;
import nextapp.echo2.extras.app.TabPane;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.extras.app.layout.TabPaneLayoutData;
import nextapp.echo2.fv.app.filetransfer.Download;
import nextapp.echo2.fv.app.filetransfer.DownloadProvider;
import nextapp.echo2.fv.app.filetransfer.ResourceDownloadProvider;
import nextapp.echo2.app.Extent;
import fv.components.FileUploadSelect;
import fv.components.FileWrapper;
import fv.components.MessagePane;
import fv.components.MrBeanBrowserContent;
import fv.components.SelectItem;
import fv.util.FVGenericInfo;
import fv.util.HSSFUtils;
import fv.util.ListBinder;
import fv.util.ReportUtils;
import fv.util.Vni2Uni;
import nextapp.echo2.app.MutableStyle;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.Alignment;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.table.PageableSortableTableModel;
import echopointng.DirectHtml;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.table.TableColumnModel;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.FillImage;

public class MST extends DefaultProgram {

	private ResourceBundle resourceBundle;
	private FileUploadSelect fileUploadSelect1;
	private Button btnImport;
	private Grid grid1;
	private MessagePane msg;
	IGenericDAO<N_EMP_TAXCODE, N_EMP_TAXCODEPk> dao;
	private Label lblInfo;
	private DscField txtNewEmpsn;
	private DscField txtNewMST;
	private DscField txtNewGhiChu;
	private Button btnSave;
	private String errorMsg;
	private DscDateField dfNgayCap;
	private SplitPane splitPane1;
	private Row row1;
	private Button btnDownload;
	private DirectHtml html;
	private DscField txtEmpsn;
	private Button btnFind1;
	private DscField txtMST;
	private Button btnFind2;
	private DscField txtCMND;
	private Button btnFind3;
	private Label lblSothe;
	private Label lblHoTen;
	private Label lblDonVi;
	private Label lblMST;
	private Label lblNgayCap;
	private Label lblGhichu;
	private Label lblTrangThai;
	private Button btnListSearch;
	private Button btnExcel;
	private SelectField sfFact;
	private SelectField sfLean;
	private SelectField sfDept;
	private SplitPane splitPane;
	private MrBeanBrowserContent browserContent;
	private ReportDao reportDao;
	private TabPane tabPane1;
	/**
	 * Creates a new <code>MST</code>.
	 */
	@SuppressWarnings("unchecked")
	public MST() {
		super();

		// Add design-time configured components.
		initComponents();
		msg = new MessagePane();
		GridLayoutData lo = new GridLayoutData();
		lo.setColumnSpan(2);
		msg.setLayoutData(lo);
		grid1.add(msg);
		dao = Application.getApp().getDao(N_EMP_TAXCODE.class);
		dfNgayCap.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dfNgayCap.getDateChooser().setLocale(new Locale("en"));
		dfNgayCap.setSelectedDate(Calendar.getInstance());
		html = new DirectHtml();
		html.setText(getHTMLGuide());
		splitPane1.add(html);
		buildTable();
		ListBinder.bindSelectField(sfFact, FVGenericInfo.getFactories(), false);
		splitPane.add(browserContent);
		MutableStyle style = new MutableStyle();
		style.setProperty("nextapp.echo2.extras.webcontainer.TabPanePeer.lazyRenderEnabled", Boolean.FALSE);
		tabPane1.setStyle(style);
	}
	
	private void factChanged(ActionEvent e) {
		SelectItem item = (SelectItem) sfFact.getSelectedItem();
		ListBinder.bindSelectField(sfLean, FVGenericInfo.getGroup(item.getValue()), true);
		ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue()), true);
	}

	private void leanChanged(ActionEvent e) {
		SelectItem item = (SelectItem) sfFact.getSelectedItem();
		SelectItem litem = (SelectItem) sfLean.getSelectedItem();
		ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue(),litem.getValue()), true);
	}
	
	private String getHTMLGuide(){
		return "<p style=\"font-size: 13pt; color: rgb(255, 51, 0); font-weight: bold;\">" +
				"Hướng dẫn </p><p><strong><span style=\"color: rgb(0, 51, 102);\">" +
				"I. Tìm kiếm mã số thuế trong hệ thống</span></strong></p><p>Chọn tab Tìm kiếm." +
				"<br/>Hỗ trợ tìm thông tin mã số thuế của nhân viên theo số thẻ, mã số thuế, " +
				"Chứng minh nhân dân (CMND).<br/>Cần tìm theo điều kiện nào thì gõ vào ô đó và " +
				"nhấn enter hoặc nhấn nút tìm bên cạnh để tìm.</p><p><strong><span style=\"color: " +
				"rgb(0, 51, 102);\">II. Cập nhật từ file excel</span></strong></p>" +
				"<p>Hỗ trợ cập nhật mã số thuế vào hệ thống từ file excel.<br/>  " +
				"Trước tiên tải về file excel mẫu bằng cách nhấn nút Download ở trên sẽ tải về file " +
				"taxcode_template.xls(nếu chưa có file này).<br/>  Copy hoặc gõ thông tin theo đúng " +
				"thứ tự cột của file excel mẫu. <br/>  Cột số thẻ có định dạng là 8 ký số.<br/>  " +
				"Cột mã số thuế có định dạng là 10 ký số.<br/>Cột ngày cấp có định dạng ngày tháng " +
				"là ngày/tháng/năm (dd/mm/yyyy).<br/>Cột ghi chú ghi lại ghi chú cập nhật, ví dụ: " +
				"Cập nhật mã số thuế FV ngày 18-05-2011<br/>Khi nhấn nút Bắt đầu, chương trình sẽ bắt đầu " +
				"cập nhật mã số thuế trong file excel từ dòng thứ 2 đến dòng cuối cùng.<br/>" +
				"Nếu gặp dòng nào bị lỗi (do cột số thẻ hoặc mã số thuế hoặc ngày cấp sai định dạng) " +
				"sẽ thông báo và bỏ qua, sau đó tiếp tục cập nhật dòng tiếp theo.<br/>Trong quá trình cập nhật, " +
				"nếu mã số thuế đã có trong hệ thống thì sẽ bỏ qua.<br/>Sau khi hoàn tất sẽ thông báo số mã " +
				"số thuế được thêm vào.</p><p><strong><span style=\"color: rgb(0, 51, 102);\">" +
				"III. Thêm mới mã số thuế.</span></strong></p><p>Nếu muốn cập nhật vào hệ thống một số ít " +
				"các mã số thuế, có thể dùng chức năng này để thêm vào. Điền đầy đủ thông tin và nhấn nút " +
				"lưu lại.<br/></p>";
	}
	
	
	private void doImport(ActionEvent e) {
		msg.clearMessage();
		FileWrapper fw = fileUploadSelect1.getFileWrapper();
		if (fw==null){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Chọn file excel");
			return;
		}
		File file = fw.getFile();
		
		try {
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			int ret = 0;
			StringBuffer errorMsg = new StringBuffer("");
			StringBuffer warningMsg = new StringBuffer("");
			for (int i=1;i<sheet.getPhysicalNumberOfRows();i++){
				N_EMP_TAXCODE code = new N_EMP_TAXCODE();
				row = sheet.getRow(i);
				if (row==null){
					errorMsg.append((i+1)+" không có dữ liệu<br/>");
					continue;
				}
				cell = row.getCell(0);//empsn
				code.setEMPSN(HSSFUtils.getStringCellValue(cell, true));
				if (code.getEMPSN().equals("")){
					errorMsg=errorMsg.append((i+1) + " (Số thẻ rỗng)<br/>");
					continue;
				}
				cell = row.getCell(1);//taxcode
				code.setCODE_TAX(HSSFUtils.getStringCellValue(cell, true));
				if (code.getCODE_TAX().equals("")){
					errorMsg=errorMsg.append((i+1) + " (Mã số thuế rỗng)<br/>");
					continue;
				}
				cell = row.getCell(2);//date
				code.setDATES(HSSFUtils.getDateCellValue(cell));
				if (code.getDATES()==null){
					errorMsg=errorMsg.append((i+1) + " (Ngày rỗng)<br/>");
					continue;
				}
				cell = row.getCell(3);//note
				code.setNOTE(HSSFUtils.getStringCellValue(cell, true));
				code.setSTATUS_CODE("1");
				N_EMP_TAXCODEPk pk = new N_EMP_TAXCODEPk(code.getEMPSN(), code.getCODE_TAX(), code.getSTATUS_CODE());
				if (dao.findById(pk)==null){
					dao.save(code);
					ret++;
				}else{
					warningMsg.append((i+1)).append(", ");
				}
			}
			msg.addMessage("Cập nhật excel từ dòng 1 đến dòng " + sheet.getPhysicalNumberOfRows() + "." +
					"<br/>Đã thêm " + ret + " mã số thuế.<br/>Những mã số thuế khác đã có trong hệ thống", 
					MessagePane.MSG_SUCCESS);
			if (!errorMsg.toString().equals("")){
				msg.addMessage("Lỗi dòng: " + errorMsg.toString(), MessagePane.MSG_ERROR);
			}
			if (!warningMsg.toString().equals("")){
				msg.addMessage("Dữ liệu tồn tại dòng: " + warningMsg.toString(), MessagePane.MSG_WARNING);
			}
			fileUploadSelect1.doSelectNextFile(null);
			fileUploadSelect1.refresh();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	private void doSearch(ActionEvent e) {
		List list=null;
		String sql = 
			"SELECT E.FNAME, E.LNAME, E.EMPSN, E.DEPSN, D.NAME_DEPT, T.DATES, T.CODE_TAX, T.NOTE, T.STATUS_CODE " +
			"FROM N_EMPLOYEE E, N_DEPARTMENT D, N_EMP_TAXCODE T " + 
			"WHERE E.EMPSN=T.EMPSN AND E.DEPSN=D.ID_DEPT ";
		if (e.getSource().equals(txtEmpsn)||"fe".equals(e.getActionCommand())){
			//search by empsn
			sql = sql + "AND E.EMPSN=?";
			list = dao.find(1, sql, new Object[]{txtEmpsn.getText()});
		}else if (e.getSource().equals(txtMST)||"fc".equals(e.getActionCommand())){
			//search by code
			sql = sql + "AND T.CODE_TAX=?";
			list = dao.find(1, sql, new Object[]{txtMST.getText()});
		}else if (e.getSource().equals(txtCMND)||"fi".equals(e.getActionCommand())){
			//search by indentifier
			sql = sql + "AND E.ID_NO=?";
			list = dao.find(1, sql, new Object[]{txtCMND.getText()});
		}
		if (list!=null&&list.size()>0){
			Object[] data = (Object[]) list.get(0);
			lblDonVi.setText(Vni2Uni.convertToUnicode(data[3]+"_"+data[4]));
			lblGhichu.setText(data[7]+"");
			lblHoTen.setText(Vni2Uni.convertToUnicode(data[0] + " " + data[1]));
			lblSothe.setForeground(Color.BLUE);
			lblMST.setText(data[6]+"");
			lblMST.setForeground(Color.BLUE);
			lblNgayCap.setText(new SimpleDateFormat("dd/MM/yyyy").format((Date)data[5]));
			lblSothe.setText(data[2]+"");
			lblTrangThai.setText(data[8].equals("1")?"Đang sử dụng":"Không sử dụng");			
		}else{
			lblDonVi.setText("");
			lblGhichu.setText("");
			lblHoTen.setText("");
			lblSothe.setForeground(Color.RED);
			lblMST.setText("");
			lblNgayCap.setText("");
			lblSothe.setText("Không tìm thấy");
			lblTrangThai.setText("");
		}
	}
	
	private boolean doValidate(){
		if (txtNewEmpsn.getText().trim().equals("")){
			errorMsg = "Nhập số thẻ";
			return false;
		}
		if (!txtNewEmpsn.getText().matches("[0-9]{8}")){
			errorMsg = "Số thẻ không hợp lệ";
			return false;
		}
		if (txtNewMST.getText().trim().equals("")){
			errorMsg = "Nhập mã số thuế";
			return false;
		}
		if (!txtNewMST.getText().matches("[0-9]{10}")){
			errorMsg = "Mã số thuế không hợp lệ";
			return false;
		}
		IGenericDAO<N_EMPLOYEE, String> dao = Application.getApp().getDao(N_EMPLOYEE.class);
		N_EMPLOYEE emp = dao.findById(txtNewEmpsn.getText());
		if (emp==null){
			errorMsg = "Không tìm thấy nhân viên có số thẻ " + txtNewEmpsn.getText();
			return false;
		}
		return true;
	}

	private void doSave(ActionEvent e) {
		if (!doValidate()){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, errorMsg);
			return;
		}
		N_EMP_TAXCODE code = new N_EMP_TAXCODE();
		code.setEMPSN(txtNewEmpsn.getText());
		code.setCODE_TAX(txtNewMST.getText());
		code.setNOTE(txtNewGhiChu.getText());
		code.setDATES(dfNgayCap.getSelectedDate().getTime());
		code.setSTATUS_CODE("1");
		List<N_EMP_TAXCODE> list = dao.find(10, "from N_EMP_TAXCODE t where t.CODE_TAX=?", new Object[]{code.getCODE_TAX()});
		if (list.size()==0){			
			dao.save(code);
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_INFORMATION, "Đã lưu thông tin");
			lblInfo.setText("");
			txtNewEmpsn.setText("");
			txtNewGhiChu.setText("");
			txtNewMST.setText("");
		}else{
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Đã có mã số thuế trong hệ thống");
			return;
		}
	}

	private void searchEmployee(ActionEvent e) {
		IGenericDAO<N_EMPLOYEE, String> dao = Application.getApp().getDao(N_EMPLOYEE.class);
		N_EMPLOYEE emp = dao.findById(txtNewEmpsn.getText());
		if (emp==null){
			lblInfo.setForeground(Color.RED);
			lblInfo.setText("Không tìm thấy nhân viên có số thẻ " + txtNewEmpsn.getText());
		}else{
			lblInfo.setForeground(Color.BLUE);
			lblInfo.setText("Họ tên: " + Vni2Uni.convertToUnicode(emp.getFNAME() + " " + emp.getLNAME()) + ". CMND: " + emp.getID_NO());			
		}
	}

	private void doDownload(ActionEvent e) {
		Download dl = new Download();
		DownloadProvider provider = new ResourceDownloadProvider("/conf/format/fvhr/taxcode_template.xls","application/vnd.ms-excel");
		dl.setProvider(provider);
		dl.setActive(true);
		Application.getApp().enqueueCommand(dl);
	}
	
	private void buildTable(){
		browserContent = new MrBeanBrowserContent(){
			private static final long serialVersionUID = 1L;
			@Override
			public Class<EmpTaxcode> getBean() {
				return EmpTaxcode.class;
			}

			@Override
			public Map<String, String> getColumnHeaderMap() {
				Map<String, String> map = new HashMap<String, String>();
				map.put("empsn", "N_EMPLOYEE.EMPSN");
				map.put("fullName", "N_EMPLOYEE.FULL_NAME");
				map.put("dept", "N_EMPLOYEE.DEPSN");
				map.put("taxCode", "N_EMP_TAXCODE.CODE_TAX");
				map.put("date", "N_EMP_TAXCODE.DATES");
				map.put("note", "N_EMP_TAXCODE.NOTE");
				return map;
			}
		};
		browserContent.initTableColumns(new String[]{"empsn", "fullName", "dept", "taxCode", "date", "note"});
		browserContent.setRowsPerPage(15);
		browserContent.setVniColumns(new String[]{"fullName", "dept", "note"});
		reportDao = new ReportDao();
	}

	private void doSearchList(ActionEvent e) {
		if (sfFact.getSelectedIndex()<0){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Chọn xưởng");
			return;
		}
		List<EmpTaxcode> list = reportDao.getEmpTaxcodeList(ListBinder.get(sfFact).toString(), ListBinder.get(sfLean), ListBinder.get(sfDept));
		browserContent.setListData(list);
		browserContent.refresh();
	}

	private void doExport(ActionEvent e) {
		PageableSortableTableModel model = (PageableSortableTableModel) browserContent.getDataTable().getModel();
		if (model.getTotalRows()==0){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Không có dữ liệu");
			return;
		}
		TableColumnModel columnModel = browserContent.getDataTable().getColumnModel();
		int n = columnModel.getColumnCount();
		int m = model.getTotalRows();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row=sheet.createRow(0);
		HSSFCell cell;
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setColor(HSSFColor.BROWN.index);
		font.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
		HSSFCellStyle style = wb.createCellStyle();
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setFont(font);
		style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);		
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		for (int i=0;i<n;i++){
			cell = row.createCell(i);
			cell.setCellStyle(style);
			String caption = columnModel.getColumn(i).getHeaderValue().toString();
			cell.setCellValue(caption);
		}
		for (int i=0;i<m;i++){
			row = sheet.createRow(i+1);
			for (int j=0;j<n;j++){
				cell = row.createCell(j);
				Object obj = model.getValueAtAbsolute(j, i);
				if (obj==null) continue;
				if (obj instanceof Number){
					cell.setCellValue(Double.valueOf(obj.toString()));
				}else{
					cell.setCellValue(obj.toString());
				}
			}
		}
		try {
			ReportUtils.doExportExcel(wb, "MaSoThue");
		} catch (IOException e1) {
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Lỗi: không thể xuất excel");
			e1.printStackTrace();
		}
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		tabPane1 = new TabPane();
		tabPane1.setTabSpacing(new Extent(1, Extent.PX));
		add(tabPane1);
		ContentPane contentPane2 = new ContentPane();
		TabPaneLayoutData contentPane2LayoutData = new TabPaneLayoutData();
		contentPane2LayoutData.setTitle("Tìm kiếm");
		contentPane2.setLayoutData(contentPane2LayoutData);
		tabPane1.add(contentPane2);
		SplitPane splitPane2 = new SplitPane();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/SplitHerzBar.png");
		splitPane2.setSeparatorHorizontalImage(new FillImage(imageReference1));
		splitPane2.setSeparatorPosition(new Extent(350, Extent.PX));
		splitPane2.setResizable(true);
		contentPane2.add(splitPane2);
		Grid grid2 = new Grid();
		grid2.setInsets(new Insets(new Extent(2, Extent.PX)));
		grid2.setSize(3);
		splitPane2.add(grid2);
		Label label3 = new Label();
		label3.setText("Số thẻ");
		grid2.add(label3);
		txtEmpsn = new DscField();
		txtEmpsn.setMaximumLength(8);
		txtEmpsn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doSearch(e);
			}
		});
		grid2.add(txtEmpsn);
		btnFind1 = new Button();
		btnFind1
				.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btnFind1.setText("Tìm");
		btnFind1.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		btnFind1.setWidth(new Extent(50, Extent.PX));
		btnFind1.setInsets(new Insets(new Extent(2, Extent.PX)));
		btnFind1.setActionCommand("fe");
		btnFind1.setRolloverBackground(new Color(0x8080c0));
		btnFind1.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x8080c0), Border.STYLE_SOLID));
		btnFind1.setRolloverForeground(new Color(0x008000));
		btnFind1.setRolloverEnabled(true);
		btnFind1.setForeground(new Color(0x004080));
		btnFind1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doSearch(e);
			}
		});
		grid2.add(btnFind1);
		Label label4 = new Label();
		label4.setText("Mã số thuế");
		grid2.add(label4);
		txtMST = new DscField();
		txtMST.setMaximumLength(10);
		txtMST.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doSearch(e);
			}
		});
		grid2.add(txtMST);
		btnFind2 = new Button();
		btnFind2
				.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btnFind2.setText("Tìm");
		btnFind2.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		btnFind2.setWidth(new Extent(50, Extent.PX));
		btnFind2.setInsets(new Insets(new Extent(2, Extent.PX)));
		btnFind2.setActionCommand("fc");
		btnFind2.setRolloverBackground(new Color(0x8080c0));
		btnFind2.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x8080c0), Border.STYLE_SOLID));
		btnFind2.setRolloverForeground(new Color(0x008000));
		btnFind2.setRolloverEnabled(true);
		btnFind2.setForeground(new Color(0x004080));
		btnFind2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doSearch(e);
			}
		});
		grid2.add(btnFind2);
		Label label5 = new Label();
		label5.setText("CMND");
		grid2.add(label5);
		txtCMND = new DscField();
		txtCMND.setMaximumLength(9);
		txtCMND.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doSearch(e);
			}
		});
		grid2.add(txtCMND);
		btnFind3 = new Button();
		btnFind3
				.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btnFind3.setText("Tìm");
		btnFind3.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		btnFind3.setWidth(new Extent(50, Extent.PX));
		btnFind3.setInsets(new Insets(new Extent(2, Extent.PX)));
		btnFind3.setActionCommand("fi");
		btnFind3.setRolloverBackground(new Color(0x8080c0));
		btnFind3.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x8080c0), Border.STYLE_SOLID));
		btnFind3.setRolloverForeground(new Color(0x008000));
		btnFind3.setRolloverEnabled(true);
		btnFind3.setForeground(new Color(0x004080));
		btnFind3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doSearch(e);
			}
		});
		grid2.add(btnFind3);
		Label label6 = new Label();
		label6.setText("---Kết quả tìm kiếm---");
		GridLayoutData label6LayoutData = new GridLayoutData();
		label6LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		label6LayoutData.setColumnSpan(3);
		label6.setLayoutData(label6LayoutData);
		grid2.add(label6);
		Label label7 = new Label();
		label7.setText("Số thẻ");
		grid2.add(label7);
		lblSothe = new Label();
		lblSothe.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		lblSothe.setForeground(new Color(0x008000));
		GridLayoutData lblSotheLayoutData = new GridLayoutData();
		lblSotheLayoutData.setColumnSpan(2);
		lblSothe.setLayoutData(lblSotheLayoutData);
		grid2.add(lblSothe);
		Label label8 = new Label();
		label8.setText("Họ tên");
		grid2.add(label8);
		lblHoTen = new Label();
		lblHoTen.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		lblHoTen.setForeground(new Color(0x008000));
		GridLayoutData lblHoTenLayoutData = new GridLayoutData();
		lblHoTenLayoutData.setColumnSpan(2);
		lblHoTen.setLayoutData(lblHoTenLayoutData);
		grid2.add(lblHoTen);
		Label label9 = new Label();
		label9.setText("Đơn vị");
		grid2.add(label9);
		lblDonVi = new Label();
		lblDonVi.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		lblDonVi.setForeground(new Color(0x008000));
		GridLayoutData lblDonViLayoutData = new GridLayoutData();
		lblDonViLayoutData.setColumnSpan(2);
		lblDonVi.setLayoutData(lblDonViLayoutData);
		grid2.add(lblDonVi);
		Label label10 = new Label();
		label10.setText("Mã số thuế");
		grid2.add(label10);
		lblMST = new Label();
		lblMST.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		lblMST.setForeground(new Color(0x008000));
		GridLayoutData lblMSTLayoutData = new GridLayoutData();
		lblMSTLayoutData.setColumnSpan(2);
		lblMST.setLayoutData(lblMSTLayoutData);
		grid2.add(lblMST);
		Label label11 = new Label();
		label11.setText("Ngày cấp");
		grid2.add(label11);
		lblNgayCap = new Label();
		lblNgayCap
				.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		lblNgayCap.setForeground(new Color(0x008000));
		GridLayoutData lblNgayCapLayoutData = new GridLayoutData();
		lblNgayCapLayoutData.setColumnSpan(2);
		lblNgayCap.setLayoutData(lblNgayCapLayoutData);
		grid2.add(lblNgayCap);
		Label label12 = new Label();
		label12.setText("Ghi chú");
		grid2.add(label12);
		lblGhichu = new Label();
		lblGhichu.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		lblGhichu.setForeground(new Color(0x008000));
		GridLayoutData lblGhichuLayoutData = new GridLayoutData();
		lblGhichuLayoutData.setColumnSpan(2);
		lblGhichu.setLayoutData(lblGhichuLayoutData);
		grid2.add(lblGhichu);
		Label label13 = new Label();
		label13.setText("Trạng thái");
		grid2.add(label13);
		lblTrangThai = new Label();
		lblTrangThai.setFont(new Font(null, Font.BOLD,
				new Extent(10, Extent.PT)));
		lblTrangThai.setForeground(new Color(0x008000));
		GridLayoutData lblTrangThaiLayoutData = new GridLayoutData();
		lblTrangThaiLayoutData.setColumnSpan(2);
		lblTrangThai.setLayoutData(lblTrangThaiLayoutData);
		grid2.add(lblTrangThai);
		splitPane = new SplitPane();
		splitPane.setSeparatorPosition(new Extent(120, Extent.PX));
		splitPane.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/SplitVertBar.png");
		splitPane.setSeparatorVerticalImage(new FillImage(imageReference2));
		splitPane.setResizable(true);
		splitPane2.add(splitPane);
		Grid grid4 = new Grid();
		grid4.setInsets(new Insets(new Extent(3, Extent.PX)));
		splitPane.add(grid4);
		Label label20 = new Label();
		label20.setText("Xưởng");
		grid4.add(label20);
		sfFact = new SelectField();
		sfFact.setWidth(new Extent(200, Extent.PX));
		sfFact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				factChanged(e);
			}
		});
		grid4.add(sfFact);
		Label label21 = new Label();
		label21.setText("Nhóm");
		grid4.add(label21);
		sfLean = new SelectField();
		sfLean.setWidth(new Extent(200, Extent.PX));
		sfLean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				leanChanged(e);
			}
		});
		grid4.add(sfLean);
		Label label22 = new Label();
		label22.setText("Đơn vị");
		grid4.add(label22);
		sfDept = new SelectField();
		sfDept.setWidth(new Extent(200, Extent.PX));
		grid4.add(sfDept);
		Label label23 = new Label();
		grid4.add(label23);
		Row row2 = new Row();
		row2.setCellSpacing(new Extent(5, Extent.PX));
		grid4.add(row2);
		btnListSearch = new Button();
		btnListSearch.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		btnListSearch.setText("Tìm");
		btnListSearch.setFont(new Font(null, Font.BOLD, new Extent(10,
				Extent.PT)));
		btnListSearch.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnListSearch.setBackground(new Color(0xc0c0c0));
		btnListSearch.setWidth(new Extent(60, Extent.PX));
		btnListSearch.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x008080), Border.STYLE_SOLID));
		btnListSearch.setForeground(new Color(0x004080));
		btnListSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doSearchList(e);
			}
		});
		row2.add(btnListSearch);
		btnExcel = new Button();
		btnExcel
				.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btnExcel.setText("Excel");
		btnExcel.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		btnExcel.setWidth(new Extent(60, Extent.PX));
		btnExcel.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnExcel.setBackground(new Color(0xc0c0c0));
		btnExcel.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x008080), Border.STYLE_SOLID));
		btnExcel.setForeground(new Color(0x004080));
		btnExcel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doExport(e);
			}
		});
		row2.add(btnExcel);
		ContentPane contentPane3 = new ContentPane();
		TabPaneLayoutData contentPane3LayoutData = new TabPaneLayoutData();
		contentPane3LayoutData.setTitle("Cập nhật từ Excel");
		contentPane3.setLayoutData(contentPane3LayoutData);
		tabPane1.add(contentPane3);
		grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(3, Extent.PX)));
		contentPane3.add(grid1);
		Label label1 = new Label();
		label1.setText("Chọn file excel : ");
		grid1.add(label1);
		fileUploadSelect1 = new FileUploadSelect();
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/excel_upload_icon.png");
		fileUploadSelect1.setIcon(imageReference3);
		fileUploadSelect1.setFileType("application/vnd.ms-excel");
		fileUploadSelect1.setIconWidth(new Extent(24, Extent.PX));
		fileUploadSelect1.setIconHeight(new Extent(24, Extent.PX));
		grid1.add(fileUploadSelect1);
		Label label2 = new Label();
		grid1.add(label2);
		btnImport = new Button();
		btnImport.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		btnImport.setText("Cập nhật");
		btnImport.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		btnImport.setWidth(new Extent(80, Extent.PX));
		btnImport.setBackground(new Color(0xc0c0c0));
		btnImport.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnImport.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x004080), Border.STYLE_SOLID));
		btnImport.setForeground(new Color(0x800040));
		btnImport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doImport(e);
			}
		});
		grid1.add(btnImport);
		ContentPane contentPane4 = new ContentPane();
		TabPaneLayoutData contentPane4LayoutData = new TabPaneLayoutData();
		contentPane4LayoutData.setTitle("Thêm mới");
		contentPane4.setLayoutData(contentPane4LayoutData);
		tabPane1.add(contentPane4);
		Grid grid3 = new Grid();
		grid3.setInsets(new Insets(new Extent(3, Extent.PX)));
		contentPane4.add(grid3);
		lblInfo = new Label();
		GridLayoutData lblInfoLayoutData = new GridLayoutData();
		lblInfoLayoutData.setColumnSpan(2);
		lblInfo.setLayoutData(lblInfoLayoutData);
		grid3.add(lblInfo);
		Label label14 = new Label();
		label14.setText("Số thẻ");
		grid3.add(label14);
		txtNewEmpsn = new DscField();
		txtNewEmpsn.setMaximumLength(8);
		txtNewEmpsn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchEmployee(e);
			}
		});
		grid3.add(txtNewEmpsn);
		Label label15 = new Label();
		label15.setText("Mã số thuế");
		grid3.add(label15);
		txtNewMST = new DscField();
		txtNewMST.setMaximumLength(10);
		grid3.add(txtNewMST);
		Label label16 = new Label();
		label16.setText("Ngày cấp");
		grid3.add(label16);
		dfNgayCap = new DscDateField();
		grid3.add(dfNgayCap);
		Label label17 = new Label();
		label17.setText("Ghi chú");
		grid3.add(label17);
		txtNewGhiChu = new DscField();
		txtNewGhiChu.setWidth(new Extent(250, Extent.PX));
		txtNewGhiChu.setMaximumLength(100);
		grid3.add(txtNewGhiChu);
		Label label18 = new Label();
		grid3.add(label18);
		btnSave = new Button();
		btnSave
				.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btnSave.setText("Lưu");
		btnSave.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		btnSave.setBackground(new Color(0xc0c0c0));
		btnSave.setWidth(new Extent(60, Extent.PX));
		btnSave.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnSave.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x0080c0), Border.STYLE_SOLID));
		btnSave.setRolloverForeground(Color.BLUE);
		btnSave.setRolloverEnabled(true);
		btnSave.setForeground(new Color(0x004080));
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doSave(e);
			}
		});
		grid3.add(btnSave);
		ContentPane contentPane5 = new ContentPane();
		TabPaneLayoutData contentPane5LayoutData = new TabPaneLayoutData();
		contentPane5LayoutData.setTitle("Hướng dẫn");
		contentPane5.setLayoutData(contentPane5LayoutData);
		tabPane1.add(contentPane5);
		splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(40, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		contentPane5.add(splitPane1);
		row1 = new Row();
		row1.setCellSpacing(new Extent(3, Extent.PX));
		SplitPaneLayoutData row1LayoutData = new SplitPaneLayoutData();
		row1LayoutData.setInsets(new Insets(new Extent(8, Extent.PX)));
		row1.setLayoutData(row1LayoutData);
		splitPane1.add(row1);
		Label label19 = new Label();
		label19.setText("File Excel mẫu");
		row1.add(label19);
		btnDownload = new Button();
		ResourceImageReference imageReference4 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/download.gif");
		btnDownload.setIcon(imageReference4);
		btnDownload.setHeight(new Extent(16, Extent.PX));
		btnDownload.setBackground(new Color(0x808080));
		btnDownload.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnDownload.setWidth(new Extent(16, Extent.PX));
		btnDownload.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x800000), Border.STYLE_SOLID));
		btnDownload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doDownload(e);
			}
		});
		row1.add(btnDownload);
	}
}
