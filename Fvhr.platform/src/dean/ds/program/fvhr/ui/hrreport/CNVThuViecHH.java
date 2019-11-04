package ds.program.fvhr.ui.hrreport;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import com.sun.star.frame.status.Visibility;

import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.table.TableColumnModel;

import ds.program.fvhr.dao.hrreport.ReportDao;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.domain.N_NIKE_CALENDAR;
import ds.program.fvhr.minh.dao.InsurDAO;
import ds.program.fvhr.ui.Employee01MDataContent;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.table.PageableSortableTableModel;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import echopointng.ComboBox;
import fv.components.SelectItem;
import fv.components.SimpleReportProgram;
import fv.util.ApplicationHelper;
import fv.util.DateUtils;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.Vni2Uni;
/* HA*/
public class CNVThuViecHH extends SimpleReportProgram {
	private ReportDataTable<?> browserController;
	private static final long serialVersionUID = 1L;	
	private SplitPane splitPane;
	private SelectField sfFact;
	private SelectField sfLean;
	private SelectField sfDept;
	private SplitPane conditionSplitPane;
	private RadioButton CNVMoi;
	private RadioButton CNVHH;
	private EmpInforDataTable EmpInforBrowserController;
	
	private Label lblDate;	
	private Label lblCountNV;
	private DscDateField dateField1;	
	private DscDateField dateField2;
	private ReportDao dao;	
	private Row paramRow;	
	private InsuranceDAO ins ; 

	private Map<String, Object> buildParams() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fact", ListBinder.get(sfFact));
		map.put("lean", ListBinder.get(sfLean));
		map.put("dept", ListBinder.get(sfDept));		
		
		String a =ins.get_todate(sdf.format(dateField1.getSelectedDate().getTime()));
		String b =ins.get_todate(sdf.format(dateField2.getSelectedDate().getTime()));
		// neu tu ngay den ngay co khac thang thi cung chon thang de lay luong theo tu ngay
		int yy = dateField1.getSelectedDate().get(Calendar.YEAR);
		// vi calendar mm se chay tu 0 den 11
		int mm = dateField1.getSelectedDate().get(Calendar.MONTH)+1;
		//String mmm =  String.valueOf(mm).length()<2?"0"+String.valueOf(mm):String.valueOf(mm);
  		//System.out.println(mmm+"_"+yy);		
		String ngayCuoi	= DateUtils.getLastDayString(mm, yy);
		if (CNVMoi.isSelected()){			
			map.put("tuNgay", a);
			map.put("denNgay", b);

		}else if (CNVHH.isSelected()){
			map.put("tuNgay", null);
			map.put("denNgay", null);
		}
		// ngay de lay luong		
		map.put("ngayCuoi",ins.get_todate(ngayCuoi));
		return map;
	}	
	@Override
	protected void doReset() {
		sfFact.setSelectedIndex(-1);
		sfLean.setSelectedIndex(-1);
		sfDept.setSelectedIndex(-1);
		((DefaultListModel)sfLean.getModel()).removeAll();
		((DefaultListModel)sfDept.getModel()).removeAll();
	}
	
	@Override
	protected void doRefresh() {
		if (!validateUI()){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, getErrorMessage());
			return;
		}
//		StopWatch sw = new StopWatch();
//		sw.start();
//		/browserController.refresh(buildParams());
		//HA them vao kiem tra vung quan ly
		String fact	= null;
		String lean = null;
		String dept = null;
		if (ListBinder.get(sfFact)!= null)
			fact = ListBinder.get(sfFact).toString();		
		if (ListBinder.get(sfLean)!= null) 
			lean = ListBinder.get(sfLean).toString();
		if (ListBinder.get(sfDept)!= null)
			dept = ListBinder.get(sfDept).toString();
		
		InsurDAO insr = new InsurDAO();
		boolean checkQL= insr.checkQLyNDept(fact, lean, dept);
		if (checkQL==true){		
			browserController.refresh(buildParams());
			lblCountNV.setText(String.valueOf(EmpInforBrowserController.demSL)+" CNV");
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_INFORMATION, "OK");
		}
		else{
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Bạn không có quyền thao tác trên dữ liệu này");
		}
		//end HA
//		sw.stop();
//		System.out.println("Refresh time: " + (float)sw.getTime()/1000);
	}	
	@Override
	protected void doSearch() {
	}

	public CNVThuViecHH() {
		super();

		// Add design-time configured components.
		initComponents();
		dateField1 = new DscDateField();
		dateField1.getDateChooser().setLocale(new Locale("en"));
		dateField1.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dateField1.setSelectedDate(Calendar.getInstance());
		
		dateField2 = new DscDateField();
		dateField2.getDateChooser().setLocale(new Locale("en"));
		dateField2.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dateField2.setSelectedDate(Calendar.getInstance());
		
		ListBinder.bindSelectField(sfFact, FVGenericInfo.getFactories(), false);
	}	
	
	@Override
	protected int doInit() {
		int ret = super.doInit();
		getToolbar().setSearchButtonEnable(false);
		getToolbar().setConfigButtonEnable(false);
		getToolbar().setPdfButtonEnable(false);
		CheckBox ckb = new CheckBox("Font VNI");
		ckb.setId("ckbVNI");
		ckb.setSelected(true);
		getToolbar().add(ckb);
		showEmpInforCNV(null);
		return ret;
	}	
	
	@Override
	protected HSSFWorkbook generateWorkbook() throws IOException {
		if (CNVMoi.isSelected()) setReportFileName("CNVMoi");
		else if (CNVHH.isSelected()) setReportFileName("CNVHH");		
		return export();
	}
	
	public HSSFWorkbook export(){
		//Xuat font vni neu ckbVNI tren mastertoolbar duoc chon 
		PageableSortableTableModel model = browserController.getModel();
		if (model.getTotalRows()<=0) return null;
		TableColumnModel columnModel = browserController.getTable().getDataTable().getColumnModel();
		int n = columnModel.getColumnCount();
		int m = model.getTotalRows();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row=sheet.createRow(0);
		HSSFCell cell;
		///nho doi kieu font phu hop voi vni hoac unicode
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setColor(HSSFColor.BROWN.index);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		HSSFCellStyle style = wb.createCellStyle();
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);		
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(font);
		
		if(((CheckBox)getToolbar().getComponent("ckbVNI")).isSelected())
		{
			font.setFontName("VNI-TIMES");
			style.setFont(font);
			
			for (int i=0;i<n;i++){
				cell = row.createCell(i);
				cell.setCellStyle(style);
				String caption = columnModel.getColumn(i).getHeaderValue().toString();
				cell.setCellValue(Vni2Uni.convertToVNI(caption));
			}
			
			font = wb.createFont();
			font.setFontName("VNI-TIMES");
			style = wb.createCellStyle();
			style.setFont(font);
			
			for (int i=0;i<m;i++){
				row = sheet.createRow(i+1);
				for (int j=0;j<n;j++){
					cell = row.createCell(j);
					cell.setCellStyle(style);
					Object obj = model.getValueAtAbsolute(j, i);
					if (obj==null) continue;
					if (obj instanceof Number){
						cell.setCellValue(Double.valueOf(obj.toString()));
					}else{
						cell.setCellValue(Vni2Uni.convertToVNI(obj.toString()));
					}
				}
			}
		}
		else
		{
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
		}
		return wb;
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
	
	private void clearCurrentClick() {	
		if (EmpInforBrowserController!= null){
			EmpInforBrowserController.getModel().getRows().clear();
		}
	}
	
	private void showEmpInforCNV(ActionEvent e) {
		clearCurrentClick();
		lblDate.setText("Date");
		paramRow.removeAll();
		paramRow.add(lblDate);
		paramRow.add(dateField1);
		paramRow.add(dateField2);
		if (dao==null)
			dao = new ReportDao();
		if (EmpInforBrowserController==null) {
			EmpInforBrowserController = new EmpInforDataTable(dao);
		}
		
		browserController = EmpInforBrowserController;//switch
		if (splitPane.getComponentCount()==2)
			splitPane.remove(1);
		splitPane.add(EmpInforBrowserController.getTable());	
		
		lblCountNV.setText(String.valueOf(EmpInforBrowserController.demSL));
		paramRow.add(lblCountNV);
	}	
	
	private void initComponents() {
		ins = new InsuranceDAO();
		splitPane = new SplitPane();
		splitPane.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/SplitVertBar.png");
		splitPane.setSeparatorVerticalImage(new FillImage(imageReference1));
		splitPane.setResizable(true);
		add(splitPane);
		conditionSplitPane = new SplitPane();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/SplitHerzBar.png");
		conditionSplitPane.setSeparatorHorizontalImage(new FillImage(
				imageReference2));
		conditionSplitPane.setSeparatorPosition(new Extent(350, Extent.PX));
		conditionSplitPane.setResizable(true);
		splitPane.add(conditionSplitPane);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(3, Extent.PX)));
		conditionSplitPane.add(grid1);
		Label label1 = new Label();
		label1.setText("Factory");
		grid1.add(label1);
		sfFact = new SelectField();
		sfFact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				factChanged(e);
			}
		});
		grid1.add(sfFact);
		Label label2 = new Label();
		label2.setText("Group");
		grid1.add(label2);
		sfLean = new SelectField();
		sfLean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				leanChanged(e);
			}
		});
		grid1.add(sfLean);
		Label label3 = new Label();
		label3.setText("Department");
		grid1.add(label3);
		sfDept = new SelectField();
		grid1.add(sfDept);
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(25, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		conditionSplitPane.add(splitPane1);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(3, Extent.PX));
		SplitPaneLayoutData row1LayoutData = new SplitPaneLayoutData();
		row1LayoutData.setBackground(new Color(0x8080c0));
		row1LayoutData.setInsets(new Insets(new Extent(3, Extent.PX)));
		row1LayoutData.setOverflow(SplitPaneLayoutData.OVERFLOW_HIDDEN);
		row1.setLayoutData(row1LayoutData);
		splitPane1.add(row1);
		CNVMoi = new RadioButton();
		//CNVMoi.setSelected(true);
		CNVMoi.setText("CNV thử việc");
		ButtonGroup opt = new ButtonGroup();
		CNVMoi.setGroup(opt);
		CNVMoi.setForeground(new Color(0x80ffff));
		row1.add(CNVMoi);
		CNVMoi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showEmpInforCNV(e);
			}
		});
		
		CNVHH = new RadioButton();
		CNVHH.setSelected(true);
		CNVHH.setText("CNV hiện hành");
		CNVHH.setGroup(opt);
		CNVHH.setForeground(new Color(0x80ffff));
		row1.add(CNVHH);
		CNVHH.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showEmpInforCNV(e);
			}
		});
		
		paramRow = new Row();
		paramRow.setCellSpacing(new Extent(5, Extent.PX));
		SplitPaneLayoutData paramRowLayoutData = new SplitPaneLayoutData();
		paramRowLayoutData.setInsets(new Insets(new Extent(24, Extent.PX)));
		paramRow.setLayoutData(paramRowLayoutData);
		splitPane1.add(paramRow);
		lblDate = new Label();
		lblDate.setText("Thời gian: ");
		paramRow.add(lblDate);
		lblCountNV = new Label();
		lblCountNV.setText("Số lượng: ");
		paramRow.add(lblCountNV);		
	}	
}

