package ds.program.fvhr.ui.an;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatternFormatting;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.SplitPane;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.component.DscDateField;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscPageNavigation;
import dsc.echo2app.component.table.DscPageableSortableTable;
import dsc.echo2app.component.table.PageableSortableTableModel;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Table;
import dsc.echo2app.component.DscGroupRadioButton;
import dsc.util.function.UUID;
import echopointng.GroupBox;
import echopointng.table.SortableTableColumn;
import fv.components.SelectItem;
import fv.util.FVGenericInfo;
import fv.util.FvLogger;
import fv.util.ListBinder;
import fv.util.Vni2Uni;
import fv.util.library;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.layout.TableLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.list.ListSelectionModel;
import nextapp.echo2.app.table.DefaultTableColumnModel;
import nextapp.echo2.app.table.TableCellRenderer;
import nextapp.echo2.app.table.TableColumnModel;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.event.FocusEvent;
import dsc.echo2app.component.event.FocusListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;

public class plhd_au extends WindowPane {

	private ResourceBundle resourceBundle;
	private SplitPane splitPane1;
	private DscDateField dscNgayky;
	private RadioButton r_plhd4;
	private RadioButton r_plhd5;
	private SelectField sf_xuong;
	private DscPageNavigation Navigation1;
	private DscPageableSortableTable Table1;
	private nLabourMProgram _father_form;
	SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
	DSPB02 u;
	PageableSortableTableModel model=null;
	Vni2Uni c=new Vni2Uni();
	float luongcu=0,luongmoi=0;
	library l=new library();
	lamtron lt=new lamtron();
	DefaultListModel xuong_model;
	
	///
	int cc=0;
	int ischeck=0;
	int pos1=-1;
	int pos2=-1;
	int pos3=-1;
	String name="";
	String note="";
	int pos_past=-1;
	
	String stt="";
	String nam="";
	String thang="";
	String nc="";
	int name_g=0;
	int crp=-1;
	String[][]mang2;
	String chuoi="";
	String mang[]=new String[20];
	private Label lbl_info;
	private Connection con;
	private Statement st1;
	private ResultSet rs1;
	/**
	 * Creates a new <code>plhd_au</code>.
	 * @param _main 
	 */
	public plhd_au(nLabourMProgram _main) {
		super();
		_father_form	= _main;

		// Add design-time configured components.
		initComponents();
		setdefault();
		
		lbl_info=new Label();
		lbl_info.setText("");
		lbl_info.setForeground(Color.RED);
		RowLayoutData infoLayout1 = new RowLayoutData();
		infoLayout1.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		infoLayout1.setWidth(new Extent(200, Extent.PERCENT));
		lbl_info.setLayoutData(infoLayout1);
		Navigation1.setBackground(new Color(0xafd8d8));
	}
	
	public void setdefault(){
		IGenericDAO<DSPB02, String> udao = Application.getApp().getDao(DSPB02.class);
		u = udao.findById(Application.getApp().getLoginInfo().getUserID());
		TableColumnModel columnModel=loadColumnModel();
		Table1.setColumnModel(columnModel);
		PageableSortableTableModel model = new PageableSortableTableModel(columnModel);
		model.setSelectionModel(Table1.getSelectionModel());
		model.setRowsPerPage(20);
		Table1.setModel(model);
		Table1.setSelectionEnabled(true);
		Navigation1.setTable(Table1);
		
		this.setHeight(new Extent(Application.getApp().getScreenHeight()));
		this.setWidth(new Extent(Application.getApp().getScreenWidth()));
		this.setTitle("Ký phụ lục hợp đồng lao động....");
		//this.setMovable(false);
		
		
		/*dscNgayky.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dscNgayky.getDateChooser().setLocale(new Locale("en"));
		dscNgayky.setSelectedDate(Calendar.getInstance());*/
		l.setdate(dscNgayky);
		ListBinder.bindSelectField(sf_xuong, FVGenericInfo.getFactories(), true);
		
	}

	private SelectField sf_nhom;
	private DscField dsc_sothe;
	private SelectField sf_donvi;
	private RadioButton rad_lc;
	private RadioButton rad_nam;
	private Label label1;
	private Button btn_show;
	private DscGroupRadioButton dscGroupRadioButton1;
	private Label label6;
	private Label label2;
	private Label label5;
	private Label label7;
	private Label label8;
	private DscGroupRadioButton dscGroupRadioButton2;
	private Label label10;
	private Label label4;
	private Label label9;
	private Label label15;
	private Label label11;
	private CheckBox chkSearchList;
	private Label label12;
	private Label label13;
	private Button btn_tk;
	private Button btn_ky;
	private Button btn_print;
	private Button btn_dsky;
	private Button btn_reset;
	private Button btn_hide;
	private Label lbsum;
	private Label label14;
	private Label label16;
	private Label label17;
	private Label label18;
	private Row row4;
	private Button btn_dschuaky;
	private Label label19;
	private Label label3;
	
	private TableColumnModel loadColumnModel()
	{
		TableColumnModel columnModel =new DefaultTableColumnModel();
		SortableTableColumn column1 =null; 
		for (int i=0;i<15;i++){
		column1=new SortableTableColumn(i);
		column1.setHeaderRenderer(Table1.getDefaultHeaderRenderer());
		column1.setComparator(l.INT_COMPARATOR);
		column1.setModelIndex(i);
		column1.setHeaderValue(getColumnHeader()[i]);
		columnModel.addColumn(column1);
		
		
		/*if(i==0){
			column1.setWidth(new Extent(1,Extent.PX));
		}*/
		if(i==1)
		{
		column1.setHeaderRenderer(new TableCellRenderer() {
			
			@Override
			public nextapp.echo2.app.Component getTableCellRendererComponent(
					Table table, Object value, int column, int row) {
				// TODO Auto-generated method stub
				TableLayoutData lay=new TableLayoutData();
				lay.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
				final CheckBox chk = new CheckBox();
				if(column==1){
					
					chk.setLayoutData(lay);
					chk.setText("Y/N");
					chk.setForeground(Color.WHITE);
					chk.setBackground(new Color(0x0080C0));
					chk.setStatePosition(new Alignment(Alignment.DEFAULT, Alignment.BOTTOM));
					chk.setStateAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
					
					//chk.setInsets(new Insets(0,5,0,5));
					chk.setId((String) value);	
					if(cc==1)
					{chk.setSelected(true);}
					chk.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							// TODO Auto-generated method stub
							if(Table1.getModel().getRowCount()>0){
							if(chk.isSelected())
							{
								cc=1;
								
									for (int j = 0; j < model.getRowCount(); j++) 
									{
										CheckBox a=(CheckBox) Table1.getCellComponent(1, j);
										a.setSelected(true);										
									}							
									int t = model.getTotalRows();
									for(int u=0;u<t;u++)
									{
										chuoi+=u+"-";
									}
								}
							else
							{
								for (int j = 0; j < model.getRowCount(); j++) 
								{
								CheckBox a=(CheckBox) Table1.getCellComponent(1, j);
								a.setSelected(false);
								chuoi="";
								cc=0;
							}
						}
					}
				}
			});
					
					return chk;
				}
				
				Label lbl = new Label();
				
				//lbl.setText(column1.getHeaderValue().toString());
				lbl.setForeground(Color.WHITE);
				TableLayoutData layout = new TableLayoutData();
				layout.setBackground(new Color(0x0080CC));
				lbl.setLayoutData(layout);
				return lbl;
				
			}
		});
		
//check box
		Table1.setDefaultRenderer(Object.class,new TableCellRenderer() {
			
			
			@Override
			public nextapp.echo2.app.Component getTableCellRendererComponent(
					Table table, Object value, int column, int row) {
				// TODO Auto-generated method stub
				
				ischeck=0;
				pos1=-1;
				pos2=-1;
				pos3=-1;
				
				TableLayoutData lo=new TableLayoutData();
				final CheckBox chk = new CheckBox();
				Navigation1.add(lbl_info);
				if(column==1)
				{
					if(chuoi.length()>0)
					{
						if(timkiem(Integer.parseInt(value.toString()),chuoi)==true)
						{
							chk.setSelected(true);
						}
						else
						{
							chk.setSelected(false);
						}
					}
					else
					{
						chk.setId(""+value);
					}
					
					lo.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
					if(row%2==0){
						lo.setBackground(new Color(0xffffdd));
					}
					chk.setLayoutData(lo);
					chk.setId(""+value);
					chk.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) 
						{

								
								

							if(chk.isSelected())
							{							
								chuoi+=chk.getId()+"-";
								mang=chuoi.split("-");
							
							}
							else
							{
								
								mang=chuoi.split("-");
								mang=remove(mang,chk.getId());
							
							
								chuoi="";
								for(int i=0;i<mang.length;i++)
								{
									chuoi+=mang[i]+"-";
								}
							
							}
						}
					});
					return chk;
				}
				Label lbl = new Label();
				if(value!=null)
				{
					lbl.setText(value.toString());						
				}
				TableLayoutData layout = new TableLayoutData();
				if(row%2==0)
				{
					
					layout.setBackground(new Color(0xffffdd));
					lbl.setLayoutData(layout);
					
				}
				if(column==9){
					//TableLayoutData layout1=new TableLayoutData();
					layout.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
					lbl.setLayoutData(layout);
				}
				if(column==0){
					TableLayoutData tld=new TableLayoutData();
					tld.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
					tld.setBackground(Color.LIGHTGRAY);
					lbl.setLayoutData(tld);
				}
				return lbl;
				
			}
		});
		}
		
	}
		
		return columnModel;
}
	public boolean timkiem(int a,String tt)
	{	
		boolean f =false;		
		mang=tt.split("-");
		for(int i=0;i<mang.length;i++)
		{
			if(Integer.parseInt(mang[i].toString())==a)
			{				
				f=true;
				break;
			}			
		}
		return f;
	}
	public String[] remove(String[]arr,String para)
	{
		int position=0;
		String[]arr_temp=new String[arr.length-1];
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i].toString().compareTo(para)==0)
			{
				position=i;
				break;
			}
		}		
		
		if(position==0)
		{
			arr_temp=new String[arr.length-1];
			for(int i=0;i<arr.length-1;i++)
			{
				arr_temp[i]=arr[i+1];
			}
			arr=new String[arr_temp.length];
			for(int i=0;i<arr_temp.length;i++)
			{
				arr[i]=arr_temp[i];
			}								
		}			
		if((position==arr.length-1)&&(arr.length!=1))
		{
			arr_temp=new String[arr.length-1];
			for(int i=0;i<arr.length-1;i++)
			{
				arr_temp[i]=arr[i];
			}				
			arr=new String[arr_temp.length];
			for(int i=0;i<arr.length;i++)
			{
				arr[i]=arr_temp[i];
			}			
		}
				
		if((position>0)&&(position<arr.length-1)&&(arr.length!=2))
		{
			arr_temp=new String[arr.length-1];
			for(int i=position;i<arr.length-1;i++)
			{
				arr[i]=arr[i+1];
			}
			for(int i=0;i<arr.length-1;i++)
			{
				arr_temp[i]=arr[i];
			}
			arr=new String[arr_temp.length];
			for(int i=0;i<arr.length;i++)
			{
				arr[i]=arr_temp[i];
			}			
		}
		return arr_temp;
	}
	
	private String[]getColumnHeader(){
		return new String[]{"STT","YES/NO","SỐ THẺ","HỌ TÊN", "CHỨC VỤ", "ĐƠN VỊ","XƯỞNG","NHÓM",
				"NGÀY KÝ","LẦN KÝ","MÃ PLHĐ CŨ","MÃ HĐ","LƯƠNG CŨ","LƯƠNG MỚI","KỶ LUẬT"};
				
	}
	
	private void doExportDataObjectSet2() throws IOException{
		String str="";
		StopWatch sw = new StopWatch();
		sw.start();
		int rows=0;
		
		File f = ReportFileManager.getReportFormatFolder("ex");
		InputStream in = new FileInputStream(new File(f.getPath(),"excel.xls"));
		HSSFWorkbook wb = new HSSFWorkbook(in);
		int m = 1;//row 1
		int n = 1;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row=null;
		HSSFCell cell = null;
		HSSFFont font = wb.createFont();
		HSSFCellStyle style = wb.createCellStyle();
		//font.setFontName("Arial");
		font.setColor(HSSFColor.RED.index);
		font.setBoldweight((short) Font.BOLD);
		font.setFontHeightInPoints((short)15);
		style.setFont(font);
		
		Object obj;
		PageableSortableTableModel model2 = (PageableSortableTableModel) Table1.getModel();
		rows=model2.getTotalRows();
		
		row=sheet.createRow(0);
		//sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));
		cell=row.createCell(0);
		cell.setCellValue("SỐ THẺ");		
		cell=row.createCell(1);
		cell.setCellValue("HỌ TÊN");
		cell=row.createCell(2);
		cell.setCellValue("CHỨC VỤ");
		cell=row.createCell(3);
		cell.setCellValue("ĐƠN VỊ");
		cell=row.createCell(4);
		cell.setCellValue("XƯỞNG");
		cell=row.createCell(5);
		cell.setCellValue("NHÓM");
		cell=row.createCell(6);
		cell.setCellValue("NGÀY KÝ");
		cell=row.createCell(7);
		cell.setCellValue("LẦN KÝ");
		cell=row.createCell(8);
		cell.setCellValue("MÃ PLHĐ CŨ");
		cell=row.createCell(9);
		cell.setCellValue("MÃ HĐ");
		cell=row.createCell(10);
		cell.setCellValue("LƯƠNG CŨ");
		cell=row.createCell(11);
		cell.setCellValue("LƯƠNG MỚI");
		cell=row.createCell(12);
		cell.setCellValue("KỶ LUẬT");
		
		
		
		for (int i = 0; i < rows; i++) {
			m++;
			n++;
			PageableSortableTableModel model = (PageableSortableTableModel) Table1.getModel();
			model.setCurrentPage(0);
			row = sheet.createRow(m);
			cell = row.createCell(0);
			cell.setCellValue(model.getValueAt(2, i).toString());//SO THE
			cell = row.createCell(1);
			cell.setCellValue(model.getValueAt(3, i).toString()); //HO TEN
			cell = row.createCell(2);
			cell.setCellValue(model.getValueAt(4, i).toString()); //CHUC VU
			cell = row.createCell(3);
			cell.setCellValue(model.getValueAt(5, i).toString()); //DON VI
			cell = row.createCell(4);
			cell.setCellValue(model.getValueAt(6, i).toString()); //XUONG
			cell = row.createCell(5);
			cell.setCellValue(model.getValueAt(7, i).toString()); //NHOM
			cell = row.createCell(6);
			cell.setCellValue(model.getValueAt(8, i).toString()); //NGAY KY
			cell = row.createCell(7);
			cell.setCellValue(model.getValueAt(9, i).toString()); //LAN KY
			cell = row.createCell(8);
			cell.setCellValue(model.getValueAt(10, i).toString()); //MA HD CU
			cell = row.createCell(9);
			cell.setCellValue(model.getValueAt(11, i).toString()); //MA HD
			cell = row.createCell(10);
			cell.setCellValue(model.getValueAt(12, i).toString()); //LCU
			cell = row.createCell(11);
			cell.setCellValue(model.getValueAt(13, i).toString()); //LM
			cell = row.createCell(12);
			cell.setCellValue(model.getValueAt(14, i).toString()); //KL
			
			
		}
		for (int i = 0; i <12 ; i++) {
			sheet.autoSizeColumn(i);
		}
		sw.stop();
		System.out.println("Export time ->>> " + (float)sw.getTime()/1000);
		File f1 = new File(System.getProperty("java.io.tmpdir"), UUID.generate());			
		FileOutputStream out = new FileOutputStream(f1);
		wb.write(out);
		out.flush();
		out.close();			
		long t=Calendar.getInstance().getTimeInMillis();
		File saveFile;
		
		
		saveFile = new File(f1.getParentFile(),URLEncoder.encode(Application.getApp().getLoginInfo().getUserID() + ";application/vnd.ms-excel;" +"DS_PLHD_CHUAKY_"+ dscNgayky.getText()+"_"+t +".xls", "UTF-8"));
		ReportFileManager.saveTempReportFile(f1, saveFile);
		Application.getApp().enqueueCommand(new BrowserRedirectCommand
				(getViewerUrl()+ saveFile.getName()));

		
	}
	

	private void doExportDataObjectSet() throws IOException{
		if(sf_xuong.getSelectedIndex()==-1){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
					"Chọn xưởng cần lấy dl...");
			return;
		}
		if((!dscNgayky.getText().substring(0,2).equals("01")) && (!dscNgayky.getText().substring(0,2).equals("15"))){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
					"NGÀY KÝ HĐ KHÔNG HỢP LỆ.");
			return;
		}
		
			StopWatch sw = new StopWatch();
			sw.start();
			//DataObjectSet ds = getMasterDataContent().getDataObjectSet();
			con=Application.getApp().getConnection();///----------------- ko duoc tu ket noi vi se bi luu session -----------
			st1=null;
			rs1=null;
			String sql="select E.EMPSN,E.FNAME||' '||E.LNAME AS HOTEN,A.NEW_JOB,L.DEPT,E.DATE_HIRED,L.TIMES,b.bsalary " +
			" from n_sub_labour a,N_EMPLOYEE E,N_DEPARTMENT D,N_LABOUR L,n_basic_salary b where A.ID_LABOUR=L.ID_LABOUR " +
			" and e.empsn=b.empsn and b.keys=1" +
			" AND L.EMPSN=E.EMPSN AND E.DEPSN=D.ID_DEPT AND D.NAME_FACT='"+sf_xuong.getSelectedItem().toString()+"' AND " +
			"to_char(a.dates_sign,'dd/mm/yyyy')='"+dscNgayky.getText()+"' and idsub_kind='00001' order by l.times";
			
			int rows=0;//Table1.getModel().getRowCount();
			try {
				st1=con.createStatement(rs1.TYPE_SCROLL_INSENSITIVE,rs1.CONCUR_READ_ONLY);
				rs1=st1.executeQuery(sql);
				if(rs1.next()==false){
					Application.getApp().showMessageDialog(MessageDialog.TYPE_INFORMATION+MessageDialog.CONTROLS_OK,
							"KHÔNG CÓ DL PLHĐ VÀO THỜI GIAN BẠN CHỌN");
					return;
				}else{
					rs1.last();
					rows=rs1.getRow();
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
			File f = ReportFileManager.getReportFormatFolder("ex");
			InputStream in = new FileInputStream(new File(f.getPath(),"HDLD.xls"));
			
	    	HSSFWorkbook wb = new HSSFWorkbook(in);
			
			
			int m = 1;//row 1
			int n = 1;
			HSSFSheet sheet = wb.getSheetAt(0);
			sheet.createFreezePane(4, 2);
			sheet.autoSizeColumn(1, true);
			
			
			HSSFRow row=null;
			HSSFCell cell = null;
			HSSFFont font = wb.createFont();
			HSSFFont font2=wb.createFont();
			font.setFontName("Arial");
			
			HSSFCellStyle style = wb.createCellStyle();
			HSSFCellStyle style2 = wb.createCellStyle();
			HSSFCellStyle style4 = wb.createCellStyle();
			
			style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setFont(font);
			
			style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style2.setFont(font);
			style2.setFillForegroundColor(HSSFColor.LAVENDER.index);
			style2.setFillPattern(HSSFPatternFormatting.SOLID_FOREGROUND);
			
			style4.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style4.setFont(font);
			style4.setFillForegroundColor(HSSFColor.ROSE.index);
			style4.setFillPattern(HSSFPatternFormatting.SOLID_FOREGROUND);
			
			HSSFCellStyle style3 = wb.createCellStyle();
			font2.setFontName("Arial");
			font2.setBoldweight((short)15);
			font2.setColor(HSSFColor.BLUE.index);
			
			
			style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style3.setFont(font2);
			
			row = sheet.createRow(0);
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,6));
			cell=row.createCell(0);
			cell.setCellStyle(style3);
				cell.setCellValue("DS PLHDLD "+sf_xuong.getSelectedItem()+" KÝ NGÀY  "+dscNgayky.getText());
			
			String time="";
			//for (int i=0;i<rows;i++){
				try {
				rs1.beforeFirst();
				while(rs1.next()){
					time=rs1.getString(6).toString();
					
					HSSFCellStyle styles = wb.createCellStyle();
					
					if(time.equals("1")){
						styles=style;
					}
					else if(time.equals("2")){
						styles=style2;
					}
					else{
						styles=style4;
					}	
					m++;
					
				row = sheet.createRow(m);			
				
				cell = row.createCell(0);
				cell.setCellStyle(styles);
				cell.setCellValue(rs1.getString(1));
				cell = row.createCell(1);
				cell.setCellStyle(styles);
				cell.setCellValue(c.convertToUnicode(rs1.getString(2)));
				cell = row.createCell(2);
				cell.setCellStyle(styles);
				cell.setCellValue(rs1.getString(3));
				cell = row.createCell(3);
				cell.setCellStyle(styles);
				cell.setCellValue(c.convertToUnicode(rs1.getString(4)));
				cell = row.createCell(4);
				cell.setCellStyle(styles);
				cell.setCellValue(df.format(rs1.getDate(5)));
				cell = row.createCell(5);
				cell.setCellStyle(styles);
				cell.setCellValue(rs1.getString(6));
				cell = row.createCell(6);
				cell.setCellStyle(styles);
				cell.setCellValue(rs1.getString(7));
			}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}finally{
					try {
						if(con!=null){
							con.close();
						}
						if(st1!=null){
							st1.close();
						}
						if(rs1!=null){
							rs1.close();
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
				for (int col = 0; col < 6; col++) {
					sheet.autoSizeColumn(col);
				}	
			sw.stop();
			System.out.println("Export time ->>> " + (float)sw.getTime()/1000);
			File f1 = new File(System.getProperty("java.io.tmpdir"),UUID.generate());			
			FileOutputStream out = new FileOutputStream(f1);
			wb.write(out);
			out.flush();
			out.close();
			String name = "PLHDLD_"+dscNgayky.getText();
			long t=Calendar.getInstance().getTimeInMillis();
			File saveFile;
			saveFile = new File(f1.getParentFile(),URLEncoder.encode(Application.getApp().getLoginInfo().getUserID() + ";application/vnd.ms-excel;" +"HD_"+ dscNgayky.getText()+"_"+t +".xls", "UTF-8"));
			ReportFileManager.saveTempReportFile(f1, saveFile);
			Application.getApp().enqueueCommand(new BrowserRedirectCommand
					(getViewerUrl()+ saveFile.getName()));
		}

		public String getViewerUrl() {
			HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
			String viewerUrl = request.getRequestURL() + "?" +
			WebRenderServlet.SERVICE_ID_PARAMETER + "=" + ReportService.INSTANCE.getId() + "&" + 
			ReportService.PARAM_TYPE + "=" + ReportService.TYPE_TEMP + "&" +
			ReportService.PARAM_KEY + "=";
			return viewerUrl;
		}

	public void getdata(){
		 con=Application.getApp().getConnection();
		String empsn="",temp="";
		
		String year=dscNgayky.getText().substring(0, 6)+(Integer.parseInt(dscNgayky.getText().substring(6, 10))-1);
		Statement st=null;
		ResultSet rs=null;
		temp=l.whereclause_id_dept(dsc_sothe.getText(), sf_xuong.getSelectedItem(), sf_nhom.getSelectedItem(), ListBinder.get(sf_donvi));
	
		String sql4="select a.empsn, a.fname||' '||a.lname as fname, a.position," +
				" dt.name_dept ,dt.name_fact,dt.name_group,c.date_s,C.TIMES+1 AS TIMES ," +
				" (case when length(nvl((select max(to_number(substr(b.id_contract,9,2)))" +
				" From n_sub_labour b where b.id_contract like a.empsn||'%'),'01'))=1 " +
				" then a.empsn||'0'||nvl((select max(to_number(substr(b.id_contract,9,2)))" +
				" from n_sub_labour b where b.id_contract like a.empsn||'%'),'1') " +
				" when length(nvl((select max(to_number(substr(b.id_contract,9,2)))" +
				" From n_sub_labour b where b.id_contract like a.empsn||'%'),'01'))>1 " +
				" then a.empsn||nvl((select max(to_number(substr(b.id_contract,9,2))) " +
				" from n_sub_labour b where b.id_contract like a.empsn||'%'),'01')end )" +
				" as id_contract,c.id_labour ,d.Bsalary  FROM n_Employee a, N_LABOUR c , " +
				" n_Basic_Salary d, n_User_Limit f ,n_department dt  " +
				" "+temp+""+
				" and a.empsn=d.empsn and a.empsn=c.empsn " +
				" and c.clock=1 and c.limit='00000' and c.date_s=to_date('"+year+"','dd/mm/yyyy') " +
				" and d.keys=1 and a.User_Manage_Id=f.ma_ql AND f.ma_user='"+u.getPB_USERNO()+"' " +
				" and c.times=3 and a.empsn not in (select substr(b.id_contract,1,8)  " +
				" From n_sub_labour b where b.id_contract like a.empsn||'%' and b.clock=1 " +
				" and b.dates_sign=to_date('"+dscNgayky.getText()+"','dd/mm/yyyy') " +
				" and b.idsub_kind='00001') order by dt.name_group,a.empsn"; 
		String sql5="select a.empsn,a.fname||' '||a.lname as fname, a.position," +
				" dt.name_dept,dt.name_fact,dt.name_group,b.dates_sign,C.TIMES+1 AS TIMES," +
				" b.id_contract,c.id_labour,d.Bsalary FROM n_Employee a, N_SUB_LABOUR b, " +
				" N_LABOUR c , n_Basic_Salary d, n_User_Limit f ,n_department dt " +
				" "+temp+""+
				" and a.empsn=d.empsn and a.empsn=c.empsn " +
				" and c.clock=1 and b.clock=1 and c.ID_LABOUR=b.ID_labour " +
				" and c.limit='00000' and b.date_en= to_date('"+dscNgayky.getText()+"','dd/mm/yyyy') " +
				" and c.times>3 and d.keys=1 and a.User_Manage_Id=f.ma_ql " +
				" AND f.ma_user='"+u.getPB_USERNO()+"'" +
						"order by c.times";
		
		model = (PageableSortableTableModel)Table1.getModel();
		model.clear();
		int i=0;
		try {
			st=con.createStatement(rs.TYPE_SCROLL_INSENSITIVE,rs.CONCUR_READ_ONLY);
			if(r_plhd4.isSelected()){
				rs=st.executeQuery(sql4);
			}else{
				rs=st.executeQuery(sql5);
			}
			
			if(rs.next()==false){
				model.clear();
				//bt_ky.setEnabled(false);
				
				l.ShowMessageInfo("Không có dữ liệu.");
				lbl_info.setText("");
				return;
			}else{
				rs.last();
				//lbsum.setText("Có "+rs.getRow()+" Phụ lục HĐ");
				//lbsum.setForeground(new Color(255,0,0));
				lbl_info.setText("Có "+rs.getRow()+" Phụ lục HĐ");
				
				//bt_ky.setEnabled(true);
				rs.beforeFirst();
				while(rs.next()){
					model.setValueAt(i+1, 0, i);
					model.setValueAt(i, 1, i);
					model.setValueAt(rs.getString(1), 2, i);//so the
					empsn=rs.getString(2);
					model.setValueAt(c.convertToUnicode(rs.getString(2)), 3, i);//ho ten					
					model.setValueAt(c.convertToUnicode(rs.getString(3)), 4, i);//chuc vu					
					model.setValueAt(c.convertToUnicode(rs.getString(4)), 5, i);//don vi
					model.setValueAt(rs.getString(5), 6, i);//xuong
					model.setValueAt(c.convertToUnicode(rs.getString(6)), 7, i);//nhom
					model.setValueAt(df.format(rs.getDate(7)),8, i);
					model.setValueAt(rs.getString(8), 9, i);
					model.setValueAt(rs.getString(9), 10, i);
					model.setValueAt(rs.getString(10), 11, i);
					model.setValueAt(rs.getString(11), 12, i);
					luongcu=rs.getFloat(11);
					luongmoi=luongcu+luongcu*5/100;
					luongmoi=lt.testLamTron(luongmoi);
					boolean kl=l.kyluat(rs.getString(1),dscNgayky.getText());
					if(kl){
						model.setValueAt(luongcu, 13, i);
						model.setValueAt("NGỪNG NÂNG LƯƠNG", 14, i);
					}else{
						model.setValueAt(luongmoi, 13, i);
						model.setValueAt("", 14, i);
					}
					model.setValueAt("", 15, i);
					i++;
					Navigation1.reset();
					
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				if(con!=null){
					con.close();
				}
				if(st!=null){
					st.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		//neu khong cho su dung tren form thi su dung cai nay lam j?
		//thui ke luoi qua k sua lai.
	mang2=new String[model.getTotalRows()][14];
		
		for(int ii=0;ii<model.getTotalRows();ii++)
		{			
			mang2[ii][0]=model.getValueAt(1,ii).toString();
			mang2[ii][1]=model.getValueAt(2,ii).toString();
			mang2[ii][2]=model.getValueAt(3,ii).toString();
			mang2[ii][3]=model.getValueAt(4,ii).toString();
			mang2[ii][4]=model.getValueAt(5,ii).toString();
			mang2[ii][5]=model.getValueAt(6,ii).toString();
			mang2[ii][6]=model.getValueAt(7,ii).toString();
			mang2[ii][7]=model.getValueAt(8,ii).toString();
			mang2[ii][8]=model.getValueAt(9,ii).toString();
			mang2[ii][9]=model.getValueAt(10,ii).toString();
			mang2[ii][10]=model.getValueAt(11,ii).toString();
			mang2[ii][11]=model.getValueAt(12,ii).toString();
			mang2[ii][12]=model.getValueAt(13,ii).toString();
			mang2[ii][13]=model.getValueAt(14,ii).toString();
						
		}	
	}
	private void ac_getdata(ActionEvent e) {
		//TODO Implement.
		if(sf_xuong.getSelectedIndex()==-1 && dsc_sothe.getText().equals("")){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
					"Chưa chọn thông tin..");
			return;
		}
		if(r_plhd4.isSelected()==false && r_plhd5.isSelected()==false){
			l.ShowMessageError("Chọn thời gian HĐ.");
			return;
		}
		if((!dscNgayky.getText().substring(0,2).equals("01")) && (!dscNgayky.getText().substring(0,2).equals("15"))){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
						"Ngày ký PLHĐ không hợp lệ.");
				return;
			}
		if(!dsc_sothe.getText().equals("")){
			if(!l.check_data(dsc_sothe.getText())){
				return;
			}
		}
		getdata();
	}
//khong phai sua nua vi du lieu nay trong ht bat buoc phai dung
	private void cell_click(ActionEvent e) 
	{	
		//neu khong cho sua thi k can cell click
		/*if(ischeck==0)
		{
			name_g=Table1.getSelectionModel().getMaxSelectedIndex();
			pos1=name_g;
			Object ob;			
			ob=Table1.getModel().getValueAt(3,name_g);	
			if(pos1==pos3)
			{
				return;
			}
			else
			{
				
				

				if(pos3==-1)
				{
					// lan dau tien click
					final DscField tui1 = new DscField();
					tui1.setWidth(new Extent(70,Extent.PX));
					Table1.remove((((name_g)*15)+28));
					Table1.add(tui1,(((name_g)*15)+28));					
					tui1.setInputType(DscField.INPUT_TYPE_NUMERIC);
					tui1.setMaximumLength(8);
					note=Table1.getModel().getValueAt(13,name_g).toString();
					tui1.setText(note);
					
					ischeck=1;
					tui1.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							if(!check_input_salary(tui1.getText())){
								return;
							}else{
								String a=tui1.getText().toString();
								Label lb=new Label();
								lb.setText(a);
								Table1.remove((((name_g)*15)+28));
								Table1.add(lb,(((name_g)*15)+28));		
								update_arr(mang2,12,id_row(mang2,Table1.getModel().getValueAt(2,name_g).toString(),model.getTotalRows()),a);
								System.out.println("truoc TD:"+note);
								System.out.println("sau TD:"+a);
							}
						}
					});
				}		
				else
				{				
					note=Table1.getModel().getValueAt(13,pos3).toString();
					final DscField tui1 = new DscField();
					tui1.setWidth(new Extent(70,Extent.PX));
					tui1.setInputType(DscField.INPUT_TYPE_NUMERIC);
					tui1.setMaximumLength(8);
					tui1.setText(Table1.getModel().getValueAt(13,pos1).toString());
					Label lb=new Label();
					lb.setText(note);
					Table1.remove((((pos3)*15)+28));
					Table1.add(lb,(((pos3)*15)+28));
					
					Table1.remove((((name_g)*15)+28));
					Table1.add(tui1,(((name_g)*15)+28));							
					ischeck=1;
					
					tui1.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							if(!check_input_salary(tui1.getText())){
								return;
							}else{
								String a=tui1.getText().toString();
								Label lb=new Label();
								lb.setText(a);
								Table1.remove((((name_g)*15)+28));
								Table1.add(lb,(((name_g)*15)+28));
								update_arr(mang2,12,id_row(mang2,Table1.getModel().getValueAt(2,name_g).toString(),model.getTotalRows()),a);
							}
						}
					});
				}
			}
		}
		if(ischeck==1)
		{
			name_g=Table1.getSelectionModel().getMaxSelectedIndex();
			pos2=name_g;
			if(pos2==pos1)
			{
				return;
			}
			else
			{
				String nn=Table1.getModel().getValueAt(13,pos1).toString();		
				note=Table1.getModel().getValueAt(13,name_g).toString();			
				
								
				final DscField tui1 = new DscField();
				tui1.setWidth(new Extent(70,Extent.PX));
				tui1.setInputType(DscField.INPUT_TYPE_NUMERIC);
				tui1.setMaximumLength(8);
				tui1.setText(note);
				
				Label lb=new Label();
				lb.setText(nn);
				
				Table1.remove((((pos1)*15)+28));
				Table1.add(lb,(((pos1)*15)+28));				
				Table1.remove((((pos2)*15)+28));
				Table1.add(tui1,(((pos2)*15)+28));
				tui1.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						if(!check_input_salary(tui1.getText())){
							return;
						}else{
							String a=tui1.getText().toString();
							Label lb=new Label();
							lb.setText(a);
							Table1.remove((((name_g)*15)+28));
							Table1.add(lb,(((name_g)*15)+28));
							update_arr(mang2,12,id_row(mang2,Table1.getModel().getValueAt(2,name_g).toString(),model.getTotalRows()),a);
						}
					}
				});
			}
			ischeck=2;
		}
		
		if(ischeck==2)
		{
			String nn=Table1.getModel().getValueAt(13,pos2).toString();		
			name_g=Table1.getSelectionModel().getMaxSelectedIndex();
			note=Table1.getModel().getValueAt(13,name_g).toString();	
			pos3=name_g;
			if(pos3==pos2)
			{
				return;
			}
			else
			{				
				Label lb=new Label();
				lb.setText(nn);
				final DscField tui1 = new DscField();
				tui1.setWidth(new Extent(70,Extent.PX));
				tui1.setInputType(DscField.INPUT_TYPE_NUMERIC);
				tui1.setMaximumLength(8);
				tui1.setText(note);
				
				Table1.remove((((pos2)*15)+28));
				Table1.add(lb,(((pos2)*15)+28));	
				
				Table1.remove((((pos3)*15)+28));
				Table1.add(tui1,(((pos3)*15)+28));
				
				
				
				tui1.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						if(!check_input_salary(tui1.getText())){
							return;
						}else{
							String a=tui1.getText().toString();
							Label lb=new Label();
							lb.setText(a);
							Table1.remove((((name_g)*15)+28));
							Table1.add(lb,(((name_g)*15)+28));
							update_arr(mang2,12,id_row(mang2,Table1.getModel().getValueAt(2,name_g).toString(),model.getTotalRows()),a);
						}
					}
				});
			}
			ischeck=0;
		}*/
		
	}
	/*public void update_arr(String[][]arr,int column_up,int row_up,String value_up)
	{
		arr[row_up][column_up-1]=value_up;
		// sau khi update lai dong trong mang thi load lai table theo cai mãng vừa up
		for(int i=0;i<model.getTotalRows();i++)
		{			
			model.setValueAt(mang2[i][13], 1, i);
			model.setValueAt(mang2[i][12], 1, i);
			model.setValueAt(mang2[i][11], 1, i);
			model.setValueAt(mang2[i][10], 1, i);
			model.setValueAt(mang2[i][9], 1, i);
			model.setValueAt(mang2[i][8], 1, i);
			model.setValueAt(mang2[i][7], 1, i);
			model.setValueAt(mang2[i][6], 1, i);
			model.setValueAt(mang2[i][5], 1, i);
			model.setValueAt(mang2[i][4], 1, i);
			model.setValueAt(mang2[i][3], 1, i);
			model.setValueAt(mang2[i][2], 1, i);
			model.setValueAt(mang2[i][1], 1, i);
			model.setValueAt(mang2[i][0], 1, i);
			Navigation1.reset();		
		}
	}
*/
public boolean check_input_salary(String salary){
		
		if(salary.equals("")){
			l.ShowMessageError("Lương nhập vào chưa đúng.");
			return false;
		}
		if (!salary.matches("[0-9].*")){
			l.ShowMessageError("Lương CB không hợp lệ.");
			return false;
			
		}
		return true;
	}

public int id_row(String[][]arr,String empsn,int length)
{
	int result=0;
	for(int i=0;i<length;i++)
	{
		String asd=arr[i][1].toString();
		if(arr[i][1].toString().compareTo(empsn)==0)
		{
			result=i;
			break;
		}
	}
	return result;
}
	public void reset(){
		dsc_sothe.setText("");
		
		r_plhd4.setSelected(false);
		r_plhd5.setSelected(false);
		rad_lc.setSelected(false);
		rad_nam.setSelected(false);
		//bt_ky.setEnabled(false);

		//lbsum.setText("");
		model=(PageableSortableTableModel) Table1.getModel();
		model.clear();		
		Navigation1.reset();
		lbl_info.setText("");
		
		ListBinder.bindSelectField(sf_xuong, FVGenericInfo.getFactories(), true);
		ListBinder.bindSelectField(sf_nhom, FVGenericInfo.getGroup(sf_xuong.getSelectedItem()), true);
		ListBinder.bindSelectField(sf_donvi, FVGenericInfo.getDeptName(sf_xuong.getSelectedItem(), sf_nhom.getSelectedItem()), true);
	}
	private void ky_PL(ActionEvent e) {
		//TODO Implement.
		String temp="";
		String empsn,mhd,pl,nk,luong,cv,cviec,kl;
		int count=model.getTotalRows();
		model.setCurrentPage(0);
		Navigation1.reset();
		boolean acclock=l.acc_lock(dscNgayky.getText().substring(3, 5), dscNgayky.getText().substring(6, 10));
		if(acclock){ 
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
					"ĐÃ KHÓA DL CỦA THÁNG NÀY..");
			return;
		}
		
			String mang[]=chuoi.split("-");
			int i;
			for (i = 0; i < mang.length; i++) {
								
					mhd=model.getValueAt(11, Integer.parseInt(mang[i].toString())).toString();
					pl=model.getValueAt(10, Integer.parseInt(mang[i].toString())).toString();
					nk=dscNgayky.getText();
					cv=model.getValueAt(4, Integer.parseInt(mang[i].toString())).toString();
					
					luong=model.getValueAt(13, Integer.parseInt(mang[i].toString())).toString();
					
					
					kl=model.getValueAt(14, Integer.parseInt(mang[i].toString())).toString();
			
					if(!kl.equals("")){
						l.savePLHD(pl, nk, luong, cv, "0", mhd, "00001");
					}else{
						l.savePLHD(pl, nk, luong, cv, luong, mhd, "00001");
					}
					String id=pl.substring(0,8);
					String note_input="Ký PLHĐ số thẻ "+id+", ngày ký "+nk+", lương "+luong+", CV "+cv+", MPL "+pl;
					
					l.Insert_N_Action_Daily(u.getPB_USERNO(), "N_SUB_LABOUR", "INSERT", id, c.convertToVNI(note_input));
				
			}
			getdata();
			
			l.ShowMessageOK("Đã ký "+i+" PL hợp đồng.");
			chuoi="";
			
			
		}
	


	private void ex_ac(ActionEvent e) {
		//TODO Implement.
		try {
			doExportDataObjectSet();
		} catch (IOException ex) {
			// TODO: handle exception
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
					"LỔI KHÔNG THỂ XUẤT DL RA EXCEL..");
		}
	}
	public void doPrint(){
		boolean lan=false;
		String loai="";
		String x="",n="",dv="",sql="";
		if(r_plhd4.isSelected()){
			lan=true;
		}else{
			lan=false;
		}
		if(rad_lc.isSelected()){
			loai="00002";
		}
		if(rad_nam.isSelected()){
			loai="00001";
		}else{
			loai="";
		}
		if(sf_xuong.getSelectedIndex()!=-1){
			x=sf_xuong.getSelectedItem().toString();
		}
		if(sf_nhom.getSelectedIndex()!=-1){
			n=Vni2Uni.convertToVNI(sf_nhom.getSelectedItem().toString());
		}
		if(sf_donvi.getSelectedIndex()!=-1){
			dv=sf_donvi.getSelectedItem().toString();
		}
		if(dsc_sothe.getText().equals("")){
			sql=l.getSQLPLHD("",x,n,dv,dscNgayky.getText(), loai, lan);
		}else{
			sql=l.getSQLPLHD(dsc_sothe.getText(),"","","",dscNgayky.getText(), loai, lan);
		}
		if(!l.exe_sql_query(sql)){
			l.ShowMessageInfo("Không có dữ liệu.");
			return;
		}
		JasperDesign jd;
		 con=null;
		try {
			jd = JRXmlLoader.load(ReportFileManager.getReportFormatFolder("fvhr/PLHD.jrxml"));
			JRDesignQuery query = new JRDesignQuery();
			query.setText(sql);
			jd.setQuery(query);
			JasperReport jr = JasperCompileManager.compileReport(jd);			
			con = Application.getApp().getConnection();
			JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
			File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
			//JasperViewer.viewReport(jp);
			JasperExportManager.exportReportToPdfFile(jp, f.getPath());			
			
			File saveFile = new File(f.getParentFile(),URLEncoder.encode(Application.getApp().getLoginInfo().getUserID() + ";application/pdf;" +"PLHD_00001_" + Calendar.getInstance().getTimeInMillis() + ".pdf", "UTF-8"));			
			ReportFileManager.saveTempReportFile(f, saveFile);
			Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
			System.out.println("----ok");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				if(con!=null){
					con.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	private void excel_ac(ActionEvent e) {
		//TODO Implement.
		try {
			doExportDataObjectSet();
		} catch (IOException ex) {
			// TODO: handle exception
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
					"LỔI KHÔNG THỂ XUẤT DL RA EXCEL..");
		}
	}

	private void fact_chage(ActionEvent e) {
		//TODO Implement.
		SelectItem item = (SelectItem) sf_xuong.getSelectedItem();
		
		ListBinder.bindSelectField(sf_nhom, FVGenericInfo.getGroup(item.getValue()), true);
		dsc_sothe.setText("");
	}

	private void nhom_chage(ActionEvent e) {
		//TODO Implement.
		SelectItem item = (SelectItem) sf_nhom.getSelectedItem();
		
		//ListBinder.bindSelectField(sf_nhom, FVGenericInfo.getGroup(item.getValue()), true);
		ListBinder.bindSelectField(sf_donvi, FVGenericInfo.getDept(sf_xuong.getSelectedItem().toString(), item.getValue()), true);
	}

	private void reset_ac(ActionEvent e) {
		//TODO Implement.
		reset();
	}

	private void f_empsn(FocusEvent e) {
		//TODO Implement.
		ListBinder.bindSelectField(sf_xuong, FVGenericInfo.getFactories(), true);
		ListBinder.bindSelectField(sf_nhom, FVGenericInfo.getGroup(sf_xuong.getSelectedItem()), true);
		ListBinder.bindSelectField(sf_donvi, FVGenericInfo.getDeptName(sf_xuong.getSelectedItem(), sf_nhom.getSelectedItem()), true);
		
	}

	private void ac_print(ActionEvent e) {
		//TODO Implement.
		if((!dscNgayky.getText().substring(0,2).equals("01")) && (!dscNgayky.getText().substring(0,2).equals("15"))){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
					"NGÀY KÝ HĐ KHÔNG HỢP LỆ.");
			return;
		}
		if(sf_xuong.getSelectedIndex()==-1 && dsc_sothe.getText().equals("")){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
			"Chọn thông tin cần in...");
			return;
		}
		
		doPrint();
		
	}
	
	public void setShow_hide(boolean t){
		label1.setVisible(t);
		label10.setVisible(t);
		label15.setVisible(t);
		label2.setVisible(t);
		label4.setVisible(t);
		label5.setVisible(t);
		label6.setVisible(t);
		label7.setVisible(t);
		label8.setVisible(t);
		label9.setVisible(t);
		label14.setVisible(t);
		label18.setVisible(t);
		label16.setVisible(t);
		label17.setVisible(t);
		label19.setVisible(t);
		label3.setVisible(t);
		lbsum.setVisible(t);
		label12.setVisible(t);
		label13.setVisible(t);
		
		sf_donvi.setVisible(t);
		sf_nhom.setVisible(t);
		sf_xuong.setVisible(t);
		row4.setVisible(t);
		
		btn_ky.setVisible(t);
		btn_hide.setVisible(t);
		btn_print.setVisible(t);
		btn_dschuaky.setVisible(t);
		btn_dsky.setVisible(t);
		btn_reset.setVisible(t);
		btn_tk.setVisible(t);
		
		dsc_sothe.setVisible(t);
		dscGroupRadioButton1.setVisible(t);
		dscGroupRadioButton2.setVisible(t);
		dscNgayky.setVisible(t);
		label11.setVisible(t);
		chkSearchList.setVisible(t);
		
		
	}

	private void hide(ActionEvent e) {
		//TODO Implement.
		setShow_hide(false);
		btn_show.setVisible(true);
		splitPane1.setSeparatorPosition(new Extent(17, Extent.PX));
		splitPane1.setSeparatorWidth(new Extent(0,Extent.PX));
	}

	private void show(ActionEvent e) {
		//TODO Implement.
		setShow_hide(true);
		btn_show.setVisible(false);
		splitPane1.setSeparatorPosition(new Extent(230, Extent.PX));
		splitPane1.setSeparatorWidth(new Extent(1,Extent.PX));
	}

	private void searchEmpsnInTable() {
		if (!dsc_sothe.getText().equals("")) {

			if (chkSearchList.isSelected()) {
				String empsn = dsc_sothe.getText().trim();
				int page=model.getTotalPages();
				int index=0;
				while(index<page){
					model.setCurrentPage(index);
					for (int i = 0; i < model.getRowCount(); i++) {
						if (empsn
								.equals(model.getValueAt(2, i).toString())) {
							
							
							// có trong danh sách
							ListSelectionModel listSelectionModel = Table1
									.getSelectionModel();
							listSelectionModel.setSelectedIndex(i, true);
							Navigation1.reset();
							return;
						}
					}
					index++;
				}
				l.ShowMessageInfo("không tìm thấy dữ liệu.");
				model.setCurrentPage(0);

			}else{
				if(Table1.getModel().getRowCount()>0){
					model.setCurrentPage(0);
				}
				btn_tk.doAction();
			}

		} else {
			MessageDialog dialog = new MessageDialog("Lỗi",
					"Số thẻ nhập vào không hợp lệ.", MessageDialog.CONTROLS_OK);
			dialog.show();
		}
	}
	
	private void st_ac(ActionEvent e) {
		//TODO Implement.
		if(chkSearchList.isSelected()){
			if(Table1.getModel().getRowCount()==0){
				l.ShowMessageInfo("Chưa có danh sách để tìm kiếm.");
				return;
			}
		}
		searchEmpsnInTable();
	}

	private void chkSearchList_ac(ActionEvent e) {
			//TODO Implement.
		
	}

	private void excel_chuaky(ActionEvent e) {
		//TODO Implement.
		try {
			doExportDataObjectSet2();
		} catch (IOException ex) {
			// TODO: handle exception
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
					"LỔI KHÔNG THỂ XUẤT DL RA EXCEL..");
		}
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setHeight(new Extent(500, Extent.PX));
		this.setWidth(new Extent(800, Extent.PX));
		ContentPane contentPane1 = new ContentPane();
		add(contentPane1);
		ContentPane contentPane2 = new ContentPane();
		contentPane1.add(contentPane2);
		splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(230, Extent.PX));
		splitPane1.setSeparatorWidth(new Extent(1, Extent.PX));
		splitPane1.setResizable(true);
		contentPane2.add(splitPane1);
		Grid grid1 = new Grid();
		grid1.setSize(2);
		splitPane1.add(grid1);
		label1 = new Label();
		label1.setText("Ngày Ký");
		GridLayoutData label1LayoutData = new GridLayoutData();
		label1LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(10, Extent.PX), new Extent(0, Extent.PX),
				new Extent(0, Extent.PX)));
		label1.setLayoutData(label1LayoutData);
		grid1.add(label1);
		dscNgayky = new DscDateField();
		GridLayoutData dscNgaykyLayoutData = new GridLayoutData();
		dscNgaykyLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(10, Extent.PX), new Extent(0, Extent.PX),
				new Extent(0, Extent.PX)));
		dscNgayky.setLayoutData(dscNgaykyLayoutData);
		grid1.add(dscNgayky);
		label3 = new Label();
		grid1.add(label3);
		dscGroupRadioButton1 = new DscGroupRadioButton();
		GridLayoutData dscGroupRadioButton1LayoutData = new GridLayoutData();
		dscGroupRadioButton1LayoutData.setInsets(new Insets(new Extent(0,
				Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		dscGroupRadioButton1.setLayoutData(dscGroupRadioButton1LayoutData);
		dscGroupRadioButton1.setSize(1);
		grid1.add(dscGroupRadioButton1);
		r_plhd4 = new RadioButton();
		r_plhd4.setText("HĐLĐ =4");
		dscGroupRadioButton1.add(r_plhd4);
		r_plhd5 = new RadioButton();
		r_plhd5.setText("HĐLĐ >4");
		dscGroupRadioButton1.add(r_plhd5);
		label6 = new Label();
		label6.setText("Số thẻ");
		grid1.add(label6);
		dsc_sothe = new DscField();
		dsc_sothe.setWidth(new Extent(156, Extent.PX));
		dsc_sothe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				st_ac(e);
			}
		});
		dsc_sothe.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				f_empsn(e);
			}
	
			public void focusLost(FocusEvent e) {
			}
		});
		grid1.add(dsc_sothe);
		label11 = new Label();
		grid1.add(label11);
		chkSearchList = new CheckBox();
		chkSearchList.setText("Tìm trong danh sách");
		chkSearchList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkSearchList_ac(e);
			}
		});
		grid1.add(chkSearchList);
		label2 = new Label();
		label2.setText("Xưởng");
		GridLayoutData label2LayoutData = new GridLayoutData();
		label2LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(10, Extent.PX), new Extent(0, Extent.PX),
				new Extent(0, Extent.PX)));
		label2.setLayoutData(label2LayoutData);
		grid1.add(label2);
		sf_xuong = new SelectField();
		GridLayoutData sf_xuongLayoutData = new GridLayoutData();
		sf_xuongLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(10, Extent.PX), new Extent(0, Extent.PX),
				new Extent(0, Extent.PX)));
		sf_xuong.setLayoutData(sf_xuongLayoutData);
		sf_xuong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fact_chage(e);
			}
		});
		grid1.add(sf_xuong);
		label5 = new Label();
		label5.setText("Nhóm");
		GridLayoutData label5LayoutData = new GridLayoutData();
		label5LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(10, Extent.PX), new Extent(0, Extent.PX),
				new Extent(0, Extent.PX)));
		label5.setLayoutData(label5LayoutData);
		grid1.add(label5);
		sf_nhom = new SelectField();
		GridLayoutData sf_nhomLayoutData = new GridLayoutData();
		sf_nhomLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(10, Extent.PX), new Extent(0, Extent.PX),
				new Extent(0, Extent.PX)));
		sf_nhom.setLayoutData(sf_nhomLayoutData);
		sf_nhom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nhom_chage(e);
			}
		});
		grid1.add(sf_nhom);
		label7 = new Label();
		label7.setText("Đơn vị");
		GridLayoutData label7LayoutData = new GridLayoutData();
		label7LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(10, Extent.PX), new Extent(0, Extent.PX),
				new Extent(0, Extent.PX)));
		label7.setLayoutData(label7LayoutData);
		grid1.add(label7);
		sf_donvi = new SelectField();
		GridLayoutData sf_donviLayoutData = new GridLayoutData();
		sf_donviLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(10, Extent.PX), new Extent(0, Extent.PX),
				new Extent(0, Extent.PX)));
		sf_donvi.setLayoutData(sf_donviLayoutData);
		grid1.add(sf_donvi);
		label8 = new Label();
		grid1.add(label8);
		dscGroupRadioButton2 = new DscGroupRadioButton();
		GridLayoutData dscGroupRadioButton2LayoutData = new GridLayoutData();
		dscGroupRadioButton2LayoutData.setInsets(new Insets(new Extent(0,
				Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		dscGroupRadioButton2.setLayoutData(dscGroupRadioButton2LayoutData);
		dscGroupRadioButton2.setSize(1);
		grid1.add(dscGroupRadioButton2);
		rad_lc = new RadioButton();
		rad_lc.setText("Lên chức-lên lương");
		dscGroupRadioButton2.add(rad_lc);
		rad_nam = new RadioButton();
		rad_nam.setText("Đến hạn ký PLHĐ");
		dscGroupRadioButton2.add(rad_nam);
		label10 = new Label();
		grid1.add(label10);
		btn_tk = new Button();
		btn_tk.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btn_tk.setText("Tìm kiếm thông tin");
		btn_tk.setHeight(new Extent(20, Extent.PX));
		btn_tk.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x8080c0), Border.STYLE_INSET));
		btn_tk.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_tk.setWidth(new Extent(156, Extent.PX));
		btn_tk.setBackground(new Color(0x0080ff));
		btn_tk.setPressedEnabled(true);
		btn_tk.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x8080c0), Border.STYLE_GROOVE));
		btn_tk.setRolloverEnabled(true);
		btn_tk.setForeground(Color.WHITE);
		GridLayoutData btn_tkLayoutData = new GridLayoutData();
		btn_tkLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(5, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		btn_tk.setLayoutData(btn_tkLayoutData);
		btn_tk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ac_getdata(e);
			}
		});
		grid1.add(btn_tk);
		label4 = new Label();
		grid1.add(label4);
		label9 = new Label();
		grid1.add(label9);
		label15 = new Label();
		grid1.add(label15);
		label12 = new Label();
		grid1.add(label12);
		btn_show = new Button();
		btn_show.setText(">>");
		btn_show.setVisible(false);
		btn_show.setToolTipText("Hiện thanh tìm kiếm bên trái");
		btn_show.setRolloverForeground(Color.RED);
		btn_show.setRolloverEnabled(true);
		btn_show.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show(e);
			}
		});
		grid1.add(btn_show);
		label13 = new Label();
		grid1.add(label13);
		row4 = new Row();
		GridLayoutData row4LayoutData = new GridLayoutData();
		row4LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(2, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		row4.setLayoutData(row4LayoutData);
		grid1.add(row4);
		btn_ky = new Button();
		btn_ky.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btn_ky.setText("Ký PLHĐ");
		btn_ky.setHeight(new Extent(20, Extent.PX));
		btn_ky.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x8080c0), Border.STYLE_INSET));
		btn_ky.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_ky.setBackground(new Color(0x0080ff));
		btn_ky.setPressedEnabled(true);
		btn_ky.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x8080c0), Border.STYLE_GROOVE));
		btn_ky.setRolloverEnabled(true);
		btn_ky.setForeground(Color.WHITE);
		RowLayoutData btn_kyLayoutData = new RowLayoutData();
		btn_kyLayoutData.setWidth(new Extent(80, Extent.PX));
		btn_ky.setLayoutData(btn_kyLayoutData);
		btn_ky.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ky_PL(e);
			}
		});
		row4.add(btn_ky);
		btn_print = new Button();
		btn_print.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		btn_print.setText("In PLHĐ");
		btn_print.setHeight(new Extent(20, Extent.PX));
		btn_print.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_INSET));
		btn_print.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_print.setBackground(new Color(0x0080ff));
		btn_print.setPressedEnabled(true);
		btn_print.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x8080c0), Border.STYLE_GROOVE));
		btn_print.setRolloverEnabled(true);
		btn_print.setForeground(Color.WHITE);
		RowLayoutData btn_printLayoutData = new RowLayoutData();
		btn_printLayoutData.setWidth(new Extent(80, Extent.PX));
		btn_print.setLayoutData(btn_printLayoutData);
		btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ac_print(e);
			}
		});
		row4.add(btn_print);
		label14 = new Label();
		grid1.add(label14);
		btn_dschuaky = new Button();
		btn_dschuaky.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		btn_dschuaky.setText("Danh sách chưa ký");
		btn_dschuaky.setHeight(new Extent(20, Extent.PX));
		btn_dschuaky.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_INSET));
		btn_dschuaky.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_dschuaky.setWidth(new Extent(156, Extent.PX));
		btn_dschuaky.setBackground(new Color(0x0080ff));
		btn_dschuaky.setPressedEnabled(true);
		btn_dschuaky.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x8080c0), Border.STYLE_GROOVE));
		btn_dschuaky.setRolloverEnabled(true);
		btn_dschuaky.setForeground(Color.WHITE);
		GridLayoutData btn_dschuakyLayoutData = new GridLayoutData();
		btn_dschuakyLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(2, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		btn_dschuaky.setLayoutData(btn_dschuakyLayoutData);
		btn_dschuaky.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excel_chuaky(e);
			}
		});
		grid1.add(btn_dschuaky);
		label16 = new Label();
		grid1.add(label16);
		btn_dsky = new Button();
		btn_dsky.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btn_dsky.setText("Danh sách đã ký");
		btn_dsky.setHeight(new Extent(20, Extent.PX));
		btn_dsky.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_INSET));
		btn_dsky.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_dsky.setWidth(new Extent(156, Extent.PX));
		btn_dsky.setBackground(new Color(0x0080ff));
		btn_dsky.setPressedEnabled(true);
		btn_dsky.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x8080c0), Border.STYLE_GROOVE));
		btn_dsky.setRolloverEnabled(true);
		btn_dsky.setForeground(Color.WHITE);
		GridLayoutData btn_dskyLayoutData = new GridLayoutData();
		btn_dskyLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(2, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		btn_dsky.setLayoutData(btn_dskyLayoutData);
		btn_dsky.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excel_ac(e);
			}
		});
		grid1.add(btn_dsky);
		label17 = new Label();
		grid1.add(label17);
		btn_reset = new Button();
		btn_reset.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		btn_reset.setText("Reset");
		btn_reset.setHeight(new Extent(20, Extent.PX));
		btn_reset.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_INSET));
		btn_reset.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_reset.setWidth(new Extent(156, Extent.PX));
		btn_reset.setBackground(new Color(0x0080ff));
		btn_reset.setPressedEnabled(true);
		btn_reset.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x8080c0), Border.STYLE_GROOVE));
		btn_reset.setRolloverEnabled(true);
		btn_reset.setForeground(Color.WHITE);
		GridLayoutData btn_resetLayoutData = new GridLayoutData();
		btn_resetLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(2, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		btn_reset.setLayoutData(btn_resetLayoutData);
		btn_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset_ac(e);
			}
		});
		grid1.add(btn_reset);
		label18 = new Label();
		grid1.add(label18);
		btn_hide = new Button();
		btn_hide.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btn_hide.setText("Ẩn thanh tìm kiếm");
		btn_hide.setHeight(new Extent(20, Extent.PX));
		btn_hide.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_INSET));
		btn_hide.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_hide.setWidth(new Extent(156, Extent.PX));
		btn_hide.setBackground(new Color(0x0080ff));
		btn_hide.setPressedEnabled(true);
		btn_hide.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x8080c0), Border.STYLE_GROOVE));
		btn_hide.setRolloverEnabled(true);
		btn_hide.setForeground(Color.WHITE);
		GridLayoutData btn_hideLayoutData = new GridLayoutData();
		btn_hideLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(2, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		btn_hide.setLayoutData(btn_hideLayoutData);
		btn_hide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hide(e);
			}
		});
		grid1.add(btn_hide);
		label19 = new Label();
		grid1.add(label19);
		lbsum = new Label();
		grid1.add(lbsum);
		SplitPane splitPane2 = new SplitPane();
		splitPane2.setSeparatorPosition(new Extent(22, Extent.PX));
		splitPane2.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane2.setSeparatorColor(new Color(0x8080ff));
		splitPane2.setResizable(false);
		splitPane1.add(splitPane2);
		Navigation1 = new DscPageNavigation();
		Navigation1.setBackground(new Color(0xc3c3c3));
		splitPane2.add(Navigation1);
		Table1 = new DscPageableSortableTable();
		Table1.setStyleName("Table.DscPageableSortableTable");
		Table1.setInsets(new Insets(new Extent(0, Extent.PX)));
		Table1.setAutoCreateColumnsFromModel(false);
		SplitPaneLayoutData Table1LayoutData = new SplitPaneLayoutData();
		Table1LayoutData.setInsets(new Insets(new Extent(0, Extent.PX)));
		Table1.setLayoutData(Table1LayoutData);
		Table1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cell_click(e);
			}
		});
		splitPane2.add(Table1);
	}
}
