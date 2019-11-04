package ds.program.fvhr.minh.ducls;

import it.businesslogic.ireport.gui.MessageBox;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import ds.program.fvhr.minh.dao.DuclDAO;
import ds.program.fvhr.minh.domain.EmployeeDucls;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.program.DefaultProgram;
import dsc.echo2app.task.ATask;
import dsc.util.function.UUID;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Font;
import echopointng.GroupBox;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.ReportUtils;
import fv.util.Vni2Uni;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;

public class GetDuclProgram extends DefaultProgram {

	private RadioButton rbtnFVS;
	private RadioButton rbtnFVL;
	private DscDateField dtfFromDate;
	private DscDateField dtfToDate;
	private SelectField slfFact;
	private Button btnExport;
	private Button btnCancel;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private DuclDAO ins = new DuclDAO();
	/**
	 * Creates a new <code>TestDataContent</code>.
	 */
	public GetDuclProgram() {
		super();
		initComponents();
		moreInit();
	}
	
	private void moreInit()
	{
        dtfFromDate.setDateFormat(sdf); 
        dtfFromDate.getDateChooser().setLocale(Locale.ENGLISH);
        dtfToDate.setDateFormat(sdf); 
        dtfToDate.getDateChooser().setLocale(Locale.ENGLISH);
        dtfFromDate.setSelectedDate(null);
		dtfToDate.setSelectedDate(null);
		ListBinder.bindSelectField(slfFact, FVGenericInfo.getFactories(), false);
		
		slfFact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doslfFactisSelect();
			}
		});
		
		rbtnFVL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dorbtnFVLisSelect();
			}
		});
		
		rbtnFVS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dorbtnFVSisSelect();
			}
		});
		
		btnExport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doExport();
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doHuy();
			}
		});
	}
	
	public boolean checkComponent()
	{
		if(dtfFromDate.getSelectedDate()== null||dtfToDate.getSelectedDate()==null)
		{
			Application.getApp().showMessageDialog("Thông Báo", "Chưa chọn ngày tháng đầy đủ.", MessageBox.OK);
			return false;
		}
		if(slfFact.getSelectedIndex()<0 && !rbtnFVL.isSelected() && !rbtnFVS.isSelected())
		{
			Application.getApp().showMessageDialog("Thông Báo", "Chưa chọn xưởng xuất dữ liệu.", MessageBox.OK);
			return false;
		}
		if(dtfFromDate.getSelectedDate().compareTo(dtfToDate.getSelectedDate())>0
				||dtfFromDate.getSelectedDate().getTime().getMonth()!=dtfToDate.getSelectedDate().getTime().getMonth())
		{
			Application.getApp().showMessageDialog("Thông Báo", "Ngày tháng không hợp lệ.", MessageBox.OK);
			return false;
		}
		
		return true;
	}
	
	public void doExport()
	{
		if(checkComponent())
		{
			List<EmployeeDucls> list =  new  ArrayList<EmployeeDucls>();
			String dk = "";
			if(slfFact.getSelectedIndex()>0)
			{
				dk =  " AND dp.name_fact = '"+slfFact.getSelectedItem().toString()+"'";
			}
			else
			{
				if(rbtnFVL.isSelected())
				{
					dk = " AND (dp.name_fact in ('FVL','KDAO') or dp.id_dept in ('TB019','00001','00002','00003'))";
				}
				else
				{
					dk = " AND (dp.name_fact = 'FVLS' or dp.id_dept = 'TB020')";
				}
			}
			
			list = ins.getList(dk, sdf.format(dtfFromDate.getSelectedDate().getTime()), sdf.format(dtfToDate.getSelectedDate().getTime()));
			
			try {
				if(list.size()<=0)
					return;

				HSSFWorkbook wb = ReportUtils.loadTemplate("fvhr",
						"NgayCongUngLuong.xls");
				HSSFSheet sheet = wb.getSheetAt(0);
				HSSFRow row;
				HSSFCell cell;
				int startRow = 1;
				for (EmployeeDucls p: list)
				{
					row = sheet.createRow(startRow++);
					cell = row.createCell(0);
					cell.setCellValue(p.getDeptID());
					cell = row.createCell(1);
					cell.setCellValue(Vni2Uni.convertToUnicode(p.getDeptName()));
					cell = row.createCell(2);
					cell.setCellValue(p.getEmpsn());
					cell = row.createCell(3);
					cell.setCellValue(Vni2Uni.convertToUnicode(p.getFname()+" "+p.getLname()));
					cell = row.createCell(4);
					cell.setCellValue(Double.valueOf(p.getPositionBonus()));
					cell = row.createCell(5);
					cell.setCellValue(Double.valueOf(p.getBasicSalary()));
					cell = row.createCell(6);
					cell.setCellValue(Double.valueOf(p.getComSalary()));
					cell = row.createCell(7);
					cell.setCellValue(Double.valueOf(p.getDucls()));
					cell = row.createCell(8);
					cell.setCellValue(Double.valueOf(p.getNghiBU()));
					cell = row.createCell(9);
					cell.setCellValue(Double.valueOf(p.getNghicophep()));
					cell = row.createCell(10);
					cell.setCellValue(Double.valueOf(p.getNghiDS()));
					cell = row.createCell(11);
					cell.setCellValue(Double.valueOf(p.getKhangcong()));
					cell = row.createCell(12);
					cell.setCellValue(Double.valueOf(p.getNghisan()));

				}
				String filename="NgayCongUngLuong_"+System.currentTimeMillis()+".xls";
				File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
				f.deleteOnExit();
				FileOutputStream out = new FileOutputStream(f);
				wb.write(out);
				out.flush();
				out.close();
				File saveFile = new File(f.getParentFile(),URLEncoder.encode(Application.getApp().getLoginInfo().getUserID() + ";application/vnd.ms-excel;"  + filename, "UTF-8"));			
				ReportFileManager.saveTempReportFile(f, saveFile);
				saveFile.deleteOnExit();
				Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
			} catch (Exception e) {
				System.out.println(e.toString());
				 dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("Lỗi","Xuất Excel thất bại", MessageDialog.CONTROLS_OK);
				Application.getApp().getDefaultWindow().getContent().add(dlg);
			}
			
		}
	}
	
	public String getViewerUrl() {
		HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
		String viewerUrl = request.getRequestURL() + "?" +
		WebRenderServlet.SERVICE_ID_PARAMETER + "=" + ReportService.INSTANCE.getId() + "&" + 
		ReportService.PARAM_TYPE + "=" + (0 == ATask.EXECTYPE_DIRECT ? ReportService.TYPE_TEMP : ReportService.TYPE_OUTPUT) + "&" +
		ReportService.PARAM_KEY + "=";
		return viewerUrl;
	}
	
	public void doslfFactisSelect()
	{
		try {
			rbtnFVL.setSelected(false);
			rbtnFVS.setSelected(false);
			if (!ins.checkQLyNDept(slfFact.getSelectedItem().toString(),null,null))
			{
				slfFact.setSelectedIndex(-1);
				Application.getApp().showMessageDialog("Thông Báo", "Bạn không có quyền thao tác trên xưởng này.", MessageBox.OK);
				return ;
			}
		} catch (Exception e) {
			System.out.println(e.toString()+"ggggggg");
		}
		
	}
	
	private void doHuy()
	{
		rbtnFVL.setSelected(false);
		rbtnFVS.setSelected(false);
		slfFact.setSelectedIndex(-1);
		dtfFromDate.setSelectedDate(null);
		dtfToDate.setSelectedDate(null);
	}
	
	private void dorbtnFVLisSelect() 
	{//xet quyen
		slfFact.setSelectedIndex(-1);
		if (!ins.checkQLyNDept("FVL",null,null)||!ins.checkQLyNDept("KDAO",null,null)||!ins.checkQLyDept("TB019")||!ins.checkQLyDept("00001")
				||!ins.checkQLyDept("00002")||!ins.checkQLyDept("00003"))
		{
			rbtnFVL.setSelected(false);
			Application.getApp().showMessageDialog("Thông Báo", "Bạn không có quyền thao tác trên các đơn vị này.", MessageBox.OK);
			return ;
		}
	}
	
	private void dorbtnFVSisSelect() 
	{
		slfFact.setSelectedIndex(-1);
		slfFact.setSelectedIndex(-1);
		if (!ins.checkQLyNDept("FVLS",null,null)||!ins.checkQLyDept("TB020"))
		{
			rbtnFVS.setSelected(false);
			Application.getApp().showMessageDialog("Thông Báo", "Bạn không có quyền thao tác trên các đơn vị này.", MessageBox.OK);
			return ;
		}
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		Column column1 = new Column();
		add(column1);
		Row row1 = new Row();
		column1.add(row1);
		Label label4 = new Label();
		label4.setText("XUẤT NGÀY CÔNG");
		label4.setFont(new Font(new Font.Typeface("sans-serif"), Font.PLAIN,
				new Extent(20, Extent.PT)));
		label4.setForeground(Color.RED);
		RowLayoutData label4LayoutData = new RowLayoutData();
		label4LayoutData.setInsets(new Insets(new Extent(100, Extent.PX),
				new Extent(10, Extent.PX), new Extent(0, Extent.PX),
				new Extent(20, Extent.PX)));
		label4.setLayoutData(label4LayoutData);
		row1.add(label4);
		Row row2 = new Row();
		column1.add(row2);
		Column column2 = new Column();
		RowLayoutData column2LayoutData = new RowLayoutData();
		column2LayoutData.setInsets(new Insets(new Extent(20, Extent.PX),
				new Extent(0, Extent.PX)));
		column2.setLayoutData(column2LayoutData);
		row2.add(column2);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5,
				Extent.PX)));
		grid1.setColumnWidth(0, new Extent(100, Extent.PX));
		grid1.setColumnWidth(1, new Extent(150, Extent.PX));
		column2.add(grid1);
		Label label1 = new Label();
		label1.setText("Từ ngày");
		grid1.add(label1);
		dtfFromDate = new DscDateField();
		grid1.add(dtfFromDate);
		Label label2 = new Label();
		label2.setText("Đến ngày");
		grid1.add(label2);
		dtfToDate = new DscDateField();
		grid1.add(dtfToDate);
		Label label3 = new Label();
		label3.setText("Xưởng");
		grid1.add(label3);
		slfFact = new SelectField();
		slfFact.setWidth(new Extent(150, Extent.PX));
		grid1.add(slfFact);
		GroupBox groupBox1 = new GroupBox();
		groupBox1.setTitle("Lựa chọn");
		groupBox1.setWidth(new Extent(250, Extent.PX));
		groupBox1.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5,
				Extent.PX)));
		column2.add(groupBox1);
		rbtnFVL = new RadioButton();
		rbtnFVL.setText("FVL-KDAO-TB019-BEP");
		ButtonGroup btng = new ButtonGroup();
		rbtnFVL.setGroup(btng);
		rbtnFVL.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(5,
				Extent.PX)));
		rbtnFVL.setRolloverForeground(new Color(0x43afc2));
		rbtnFVL.setRolloverEnabled(true);
		rbtnFVL.setForeground(new Color(0x136993));
		groupBox1.add(rbtnFVL);
		rbtnFVS = new RadioButton();
		rbtnFVS.setText("FVS-TB020");
		rbtnFVS.setGroup(btng);
		rbtnFVS.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(5,
				Extent.PX)));
		rbtnFVS.setRolloverForeground(new Color(0x43afc2));
		rbtnFVS.setRolloverEnabled(true);
		rbtnFVS.setForeground(new Color(0x136993));
		groupBox1.add(rbtnFVS);
		Column column3 = new Column();
		RowLayoutData column3LayoutData = new RowLayoutData();
		column3LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		column3LayoutData.setInsets(new Insets(new Extent(20, Extent.PX),
				new Extent(0, Extent.PX)));
		column3.setLayoutData(column3LayoutData);
		row2.add(column3);
		btnExport = new Button();
		btnExport.setText("Xuất Excel");
		btnExport.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_INSET));
		btnExport.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_OUTSET));
		btnExport.setWidth(new Extent(150, Extent.PX));
		btnExport.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(10,
				Extent.PX)));
		btnExport.setBackground(new Color(0x43afc2));
		btnExport.setPressedEnabled(true);
		btnExport.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_GROOVE));
		btnExport.setRolloverEnabled(true);
		btnExport.setForeground(Color.WHITE);
		ColumnLayoutData btnExportLayoutData = new ColumnLayoutData();
		btnExportLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.BOTTOM));
		btnExportLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		btnExport.setLayoutData(btnExportLayoutData);
		column3.add(btnExport);
		btnCancel = new Button();
		btnCancel.setText("Hủy");
		btnCancel.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_INSET));
		btnCancel.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_OUTSET));
		btnCancel.setWidth(new Extent(150, Extent.PX));
		btnCancel.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(10,
				Extent.PX)));
		btnCancel.setBackground(new Color(0x43afc2));
		btnCancel.setPressedEnabled(true);
		btnCancel.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_GROOVE));
		btnCancel.setRolloverEnabled(true);
		btnCancel.setForeground(Color.WHITE);
		ColumnLayoutData btnCancelLayoutData = new ColumnLayoutData();
		btnCancelLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.BOTTOM));
		btnCancelLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(20, Extent.PX), new Extent(0, Extent.PX),
				new Extent(0, Extent.PX)));
		btnCancel.setLayoutData(btnCancelLayoutData);
		column3.add(btnCancel);
	}
}
