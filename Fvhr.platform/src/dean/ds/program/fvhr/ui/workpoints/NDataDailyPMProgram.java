package ds.program.fvhr.ui.workpoints;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.component.table.PageableSortableTableModel;
import dsc.echo2app.program.MasterToolbar;
import fv.components.PreMaintainSProgram;
import fv.util.ReportUtils;
import fv.util.Vni2Uni;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.RowLayoutData;
import ds.program.fvhr.dao.wp.WorkpointsDAO;
import ds.program.fvhr.ui.hrdata.ICMasterData;
import ds.program.fvhr.ui.workpoints.NDataDailyBrowserContent;

public class NDataDailyPMProgram extends PreMaintainSProgram {
	private static final long serialVersionUID = 1L;
	private ResourceBundle resourceBundle;
	private DailyExecPane pane;
	private NDataDailyBrowserContent browserContent;
	private String factCondition;
	private Button btnExportExcel;
	private Button btnDayProcess;
	private WorkpointsDAO dao;
	private Button btnPdfPrint;
	protected boolean printable=false;
	protected Map<String,String> printParams;
	private Button btnPrintWP;
	private Label lblInfo;
	/**
	 * Creates a new <code>NDataDailyPMProgram</code>.
	 */
	public NDataDailyPMProgram() {
		super();
		// Add design-time configured components.
		initComponents();
		
	}
	
	@Override
	public int refresh() {
		int ret = super.refresh();
		if (getBrowserContent().getListData().size()>0){
			btnExportExcel.setEnabled(true);
			btnPdfPrint.setEnabled(true);
			btnPrintWP.setEnabled(true);
		} else {
			btnExportExcel.setEnabled(false);
			btnPdfPrint.setEnabled(false);
			btnPrintWP.setEnabled(false);
		}
		int n = getBrowserContent().getListData().size();
		PageableSortableTableModel model = (PageableSortableTableModel) browserContent.getDataTable().getModel();
		HashSet<String> setEmpsn = new HashSet<String>();
		for (int i=0;i<model.getTotalRows();i++){
			String empsn = model.getValueAtAbsolute(2, i).toString();
			setEmpsn.add(empsn);
		}
		lblInfo.setText("Số thẻ: " + setEmpsn.size() + " / Dữ liệu: " + n);
		return ret;
	}
	
	public WorkpointsDAO getDao(){
		if (dao==null) dao = new WorkpointsDAO();
		return this.dao;
	}
	
	@Override
	protected int doInit() {
		int ret = super.doInit();
		
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, false);
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_REFRESH, false);
		getMasterToolbar().refresh();
		
		btnExportExcel = new Button();
		btnExportExcel.setEnabled(false);
		btnExportExcel.setToolTipText("Xuất dữ liệu ra Excel");
		btnExportExcel.setStyleName("Default.ToolbarButton");
		btnExportExcel.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnExcel.gif"));
		btnExportExcel.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnExcelD.gif"));
		getMasterToolbar().add(btnExportExcel);
		btnExportExcel.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				doExportFromModel();
			}
		});
		
		btnPdfPrint = new Button();
		btnPdfPrint.setEnabled(false);
		btnPdfPrint.setToolTipText("Bảng xác nhận dữ liệu khác thường");
		btnPdfPrint.setStyleName("Default.ToolbarButton");
		btnPdfPrint.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/printer.png"));
		btnPdfPrint.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/printerD.png"));
		getMasterToolbar().add(btnPdfPrint);
		btnPdfPrint.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				doPrint();
			}
		});
		
		btnDayProcess = new Button();
		btnDayProcess.setToolTipText("Xử lý dữ liệu");
		btnDayProcess.setStyleName("Default.ToolbarButton");
		btnDayProcess.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/cog_add.png"));
		btnDayProcess.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/cog_addD.png"));
		btnDayProcess.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				showProcessPane();
			}
		});
		getMasterToolbar().add(btnDayProcess);
		
		btnPrintWP = new Button();
		btnPrintWP.setEnabled(false);
		btnPrintWP.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnPrint.gif"));
		btnPrintWP.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnPrintD.gif"));
		btnPrintWP.setToolTipText("In bảng chấm công");
		btnPrintWP.setStyleName("Default.ToolbarButton");
		btnPrintWP.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				doPrintDataTable();
			}
		});
		getMasterToolbar().add(btnPrintWP);
		
		browserContent = new NDataDailyBrowserContent(this);
		browserContent.initTableColumns(new String[]{"EMPNA", "DEPT_NAME", "EMPSN","DATES", "ID_SHIFT", "TT_IN","TT_MID", "TT_OUT","TT_OVER","NOTE"});
		browserContent.setVniColumns(new String[]{"EMPNA","DEPT_NAME"});
		add(browserContent);
		
		lblInfo = new Label("Chọn Tìm kiếm");
		lblInfo.setForeground(new Color(0xcc3300));
		Font f = new Font(Font.ARIAL, Font.BOLD, new Extent(14));
		lblInfo.setFont(f);
		RowLayoutData infoLayout = new RowLayoutData();
		infoLayout.setAlignment(new Alignment(Alignment.RIGHT, Alignment.CENTER));
		infoLayout.setWidth(new Extent(100, Extent.PERCENT));
		lblInfo.setLayoutData(infoLayout);
		
		getMasterToolbar().add(lblInfo);
		
		return ret;
	}
	
//	protected void doPrintDept() {
//		PageableSortableTableModel model = (PageableSortableTableModel) browserContent.getDataTable().getModel();
//		if (model.getRowCount()<=0){
//			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Không có dữ liệu");
//			return;
//		}
//		
//	}

	protected void doPrintDataTable() {
		PageableSortableTableModel model = (PageableSortableTableModel) browserContent.getDataTable().getModel();
		if (model.getRowCount()<=0){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Không có dữ liệu");
			return;
		}
		int cmonth = Integer.parseInt(dao.getMonth())-1;
		HashSet<String> setEmpsn = new HashSet<String>();
//		List listData = browserContent.getListData();
		for (int i=0;i<model.getTotalRows();i++){
			String empsn = model.getValueAtAbsolute(2, i).toString();
			setEmpsn.add(empsn);
		}
		List listEmpsn = Arrays.asList(setEmpsn.toArray());
		if (listEmpsn.size()>1000){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Không thể in, tổng số số thẻ phải <= 1000");
			return;
		}
//		Collections.sort(listEmpsn);
		List<ICMasterData> list = dao.getDataList(listEmpsn);
		for (ICMasterData data:list){
			data.setFULLNAME(Vni2Uni.convertToUnicode(data.getFULLNAME()));
			data.setNAME_DEPT(Vni2Uni.convertToUnicode(data.getNAME_DEPT()));
		}
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mon", dao.getMonth());
		params.put("yy", dao.getYear());
		params.put("SUBREPORT_DIR", ReportFileManager.getReportFormatFolder("fvhr").getAbsolutePath()+"/");
		params.put("USER_ID", getLoginInfo().getUserID());
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cmonth);
		cal.set(Calendar.YEAR, Integer.parseInt(dao.getYear()));
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int col_break = days/2;
		if (days%2>0) col_break=col_break+1;
		params.put("col_break", col_break);
		params.put("REPORT_CONNECTION", Application.getApp().getConnection());
		try {
			JasperDesign jd = JRXmlLoader.load(ReportFileManager.getReportFormatFolder("fvhr/bangchamcong.jrxml"));
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);
			ReportUtils.doExportPdf(jp, "BangChamCong");
//			ReportUtils.doExportHtml(jp, "D:/test.html");
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	protected void showProcessPane() {
		if (pane==null){
			pane = new DailyExecPane(this);
			Application.getApp().getDefaultWindow().getContent().add(pane);
		}
		pane.setType(DailyExecPane.TYPE_EXCEC);
		pane.setTitle("Xử lý dữ liệu ngày");
		pane.setVisible(true);
	}

	@Override
	protected void doDataRefresh() {
		browserContent.refresh();
	}
	
	@Override
	protected void doQueryNormal() {
		if (pane==null){
			pane = new DailyExecPane(this);
			Application.getApp().getDefaultWindow().getContent().add(pane);
		}
		pane.setType(DailyExecPane.TYPE_SEARCH);
		pane.setTitle("Tìm kiếm dữ liệu không hoàn chỉnh");
		pane.setVisible(true);
	}
	
	public String getFactCondition() {
		return factCondition;
	}
	
	public void setFactCondition(String factCondition) {
		this.factCondition = factCondition;
	}
	
	public NDataDailyBrowserContent getBrowserContent() {
		return browserContent;
	}
	
	private void doExportFromModel(){
		HSSFWorkbook wb = ReportUtils.loadTemplate("fvhr", "icdaily.xls");
		if (wb==null) return;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		//fill header
		Object[] params = browserContent.getParams();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (params!=null){
			row = sheet.getRow(1);
			cell = row.getCell(0);
			String date;
			if (params.length==1){
				date = "Ngày " + sdf.format(params[0]);
			}else if (params.length==3){
				date = "Ngày " + sdf.format(params[0]) + " (Ngày nghỉ việc từ " + sdf.format(params[1]) + " đến " + sdf.format(params[2]) + ")";
			}else if (params.length==4){
				date = "Từ ngày " + sdf.format(params[0]) + " đến ngày " + sdf.format(params[1]) + " (Ngày nghỉ việc từ " + sdf.format(params[2]) + " đến " + sdf.format(params[3]) + ")";
			}else{
				date = "Từ ngày " + sdf.format(params[0]) + " đến ngày " + sdf.format(params[1]);
			}
			cell.setCellValue(date);
		}
		//////////
		PageableSortableTableModel model = (PageableSortableTableModel) browserContent.getDataTable().getModel();
		if (model.getTotalRows()==0) return;
		for (int i=0;i<model.getTotalRows();i++){
			row = sheet.createRow(i+4);
			String empsn="";
			for (int j=0;j<10;j++){
				cell = row.createCell(j);
				Object obj = model.getValueAtAbsolute(j, i);
				if (obj!=null){
					String s = String.valueOf(obj);
					cell.setCellValue(s);
					if (j==2) empsn = s;
				}
			}
			cell = row.createCell(10);
			String empcn = dao.getEmpcn(empsn);
			cell.setCellValue(empcn);
		}
		try {
			ReportUtils.doExportExcel(wb, "DuLieuQuetThe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void doPrint(){
		List list = browserContent.getListData();
		if (list==null||list.size()==0){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Không có dữ liệu");
			return;
		}
		if (!printable){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Không thể in dữ liệu tổng hợp");
			return;
		}
//		List<N_DATA_DAILY_EX> listData = new ArrayList<N_DATA_DAILY_EX>();
		for (int i=0;i<list.size();i++){
			N_DATA_DAILY_EX data = (N_DATA_DAILY_EX) list.get(i);
			data.setDEPT_NAME(Vni2Uni.convertToUnicode(data.getDEPT_NAME()));
			data.setEMPNA(Vni2Uni.convertToUnicode(data.getEMPNA()));
//			listData.add(data);
//			try {
//				
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (InstantiationException e) {
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				e.printStackTrace();
//			} catch (NoSuchMethodException e) {
//				e.printStackTrace();
//			}
			
		}
		
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
		try {
			JasperDesign jd = JRXmlLoader.load(ReportFileManager
					.getReportFormatFolder("fvhr/dailydata.jrxml"));
			JasperReport jr = JasperCompileManager.compileReport(jd);			
			JasperPrint jp = JasperFillManager.fillReport(jr, printParams, ds);
			ReportUtils.doExportPdf(jp, "BangXacNhan");
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if (pane!=null)
			pane.dispose();
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
	}
}
