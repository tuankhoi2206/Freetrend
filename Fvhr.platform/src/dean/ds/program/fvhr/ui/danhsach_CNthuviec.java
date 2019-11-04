package ds.program.fvhr.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

import com.sun.org.apache.bcel.internal.generic.DCMPG;
import com.sun.star.lib.uno.bridges.java_remote.java_remote_bridge;

import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.program.DefaultProgram;
import nextapp.echo2.app.Button;
import dsc.echo2app.component.DscBetweenField;
import dsc.echo2app.component.DscField;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.table.DefaultTableColumnModel;
import nextapp.echo2.app.table.TableColumnModel;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.echo2app.component.DscPageNavigation;
import dsc.echo2app.component.table.PageableSortableTableModel;
import echopointng.table.PageableSortableTable;
import echopointng.table.SortableTableColumn;
import fv.components.SelectItem;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.Vni2Uni;
import fv.util.library;
import nextapp.echo2.app.Insets;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;
import dsc.echo2app.component.table.DscPageableSortableTable;
import dsc.util.function.UUID;
import dsc.echo2app.component.event.FocusEvent;
import dsc.echo2app.component.event.FocusListener;
import nextapp.echo2.app.layout.GridLayoutData;

public class danhsach_CNthuviec extends WindowPane {

	private ResourceBundle resourceBundle;
	private Button bt_ex;
	private Button bt_tim;
	private DscBetweenField dscBetweenngay;
	private DscField dscsothe;
	private SelectField sf_thang;
	private SelectField sf_xuong;
	private SelectField sf_nam;
	private SelectField sf_nhom;
	private SelectField sf_donvi;
	private Button b_reset;

	/**
	 * Creates a new <code>danhsach_CNthuviec</code>.
	 */
	library f=new library();
	DefaultListModel mm_model,yy_model,xuong_model,nhom_model,donvi_model;
	PreparedStatement pstm=null;
	Connection con=null;
	ResultSet rs=null;
	Vni2Uni c=new Vni2Uni();
	SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
	String ngay="";
	boolean tieptuc=false;
	
	private DscPageNavigation Navigation;
	
	private Employee01MProgram _father_form;
	DSPB02 u;
	
	
	public danhsach_CNthuviec(Employee01MProgram _main) {
		super();
		_father_form	= _main;

		// Add design-time configured components.
		initComponents();
		TableColumnModel columnModel=loadColumnModel();
		Table.setColumnModel(columnModel);
		PageableSortableTableModel model = new PageableSortableTableModel(columnModel);
		model.setSelectionModel(Table.getSelectionModel());
		model.setRowsPerPage(15);
		Table.setModel(model);
		Table.setSelectionEnabled(true);
		Navigation.setTable(Table);
		this.setHeight(new Extent(600));
		this.setWidth(new Extent(800));
		this.setTitle("XUẤT DANH SÁCH NHÂN VIÊN THỬ VIỆC");
		this.setMovable(false);
		
		
		//String [] arr=f.getdates("30/04/2013");
		//System.out.println(arr[0]);
		//System.out.println(arr[1]);
		IGenericDAO<DSPB02, String> udao = Application.getApp().getDao(DSPB02.class);
		u = udao.findById(Application.getApp().getLoginInfo().getUserID());
	
		setdefault();
		
		
	}
	
	private static final Comparator INT_COMPARATOR =new Comparator(){
		public int compare(Object o1, Object o2){
			if(o1 == null && o2 == null)
				return 0;
        	else if(o1 == null)
        		return 1;
        	else if(o2 == null)
        		return -1;
			if (o1 instanceof String && o2 instanceof String)
        		return ((String)o1).compareTo((String)o2);
        	else
        		return ((Long)o1).compareTo((Long)o2);
		}
	};
	private DscPageableSortableTable Table;
	private Label lb_sum;
	private TableColumnModel loadColumnModel(){
		TableColumnModel columnModel =new DefaultTableColumnModel();
		for (int i=0;i<23;i++){
		SortableTableColumn column=new SortableTableColumn(i);
		column.setHeaderRenderer(Table.getDefaultHeaderRenderer());
		column.setComparator(INT_COMPARATOR);
		column.setModelIndex(i);
		column.setHeaderValue(getColumnHeader()[i]);
		columnModel.addColumn(column);
		}
		return columnModel;
	}
	private String[]getColumnHeader(){
		return new String[]{"NAME_FACT", "NAME_GROUP","NAME_DEPT_NAME", "EMPSN","EMPCN","FNAME",
				"LNAME","DATE_HIRED","POSITION","PCCV","SEX","BIRTHDAY","EDUCATION","PERMANENT_ADDRESS",
				"CITY","BIRTHPLACE","ID_NO","ID_PLACE","NGAYCAP_ID","ETHNIC","RELIGION","SALARY","SAL_STATUS"};
	}
	public void setdefault(){
		//SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date a=new Date(System.currentTimeMillis());
		String b=df.format(a.getTime());
		String mm=b.substring(3, 5);
		String yy=b.substring(6, 10);
		
		mm_model=(DefaultListModel) sf_thang.getModel();
		mm_model.add("01");		mm_model.add("02");		mm_model.add("03");
		mm_model.add("04");		mm_model.add("05");		mm_model.add("06");
		mm_model.add("07");		mm_model.add("08");		mm_model.add("09");
		mm_model.add("10");     mm_model.add("11");     mm_model.add("12");
		
		yy_model=(DefaultListModel) sf_nam.getModel();
		for(int nam=2011;nam<=Integer.parseInt(yy);nam++){
			//yy_model.add("2010");	yy_model.add("2011");   yy_model.add(yy);
			yy_model.add(nam);
		}
		
		
		sf_thang.setSelectedIndex(mm_model.indexOf(mm));
		sf_nam.setSelectedIndex(yy_model.indexOf(Integer.parseInt(yy)));
		
		ListBinder.bindSelectField(sf_xuong, FVGenericInfo.getFactories(), true);
		
		
		
	}
	private void xuong_ac(ActionEvent e) {
		//TODO Implement.
		dscsothe.setText("");
		SelectItem item = (SelectItem) sf_xuong.getSelectedItem();
		
		ListBinder.bindSelectField(sf_nhom, FVGenericInfo.getGroup(item.getValue()), true);
	}

	private void nhom_ac(ActionEvent e) {
		//TODO Implement.
		SelectItem item = (SelectItem) sf_nhom.getSelectedItem();
		
		//ListBinder.bindSelectField(sf_nhom, FVGenericInfo.getGroup(item.getValue()), true);
		ListBinder.bindSelectField(sf_donvi, FVGenericInfo.getDept(sf_xuong.getSelectedItem().toString(), item.getValue()), true);
	}

	public void loaddata(){
		
		con=Application.getApp().getConnection();
		if((!sf_thang.getSelectedItem().toString().equals("")) && (!sf_nam.getSelectedItem().toString().equals(""))){
			ngay=sf_thang.getSelectedItem().toString()+"/"+sf_nam.getSelectedItem().toString();
		}
		String sql="select dt.name_fact,dt.name_group,dt.name_dept_name,a.empsn,a.empcn,a.fname,a.lname, a.date_hired, a.position," +
				" nvl((select j.money from n_bonus_job j where j.empsn=a.empsn and j.money<>0 and a.date_hired=j.dates and j.keys='1')," +
				" nvl(( select p.money from n_bonus_poss p where a.empsn =p.empsn and p.money<>0 and a.date_hired=p.dates " +
				" and p.keys= '1'),0)) as PCCV, a.sex,a.birthday, a.education, a.permanent_address, a.city, a.birthplace, a.id_no," +
				" a.id_place, a.ngaycap_id, a.ethnic, a.religion, ( case  when(a.position='CN')and (wt.is_test='0')" +
				" and (wt.sal_status='85')then ROUND((( b.bsalary)*100/85),-4) when(a.position='CN')and (wt.is_test='0') " +
				" and (wt.sal_status='100') and (wt.bonus_nghe='100000')then (select (salary_per)+100000 from n_incsalarybasic" +
				" where  id_year='0') when(a.position='CN')and (wt.is_test='0') and (wt.sal_status='100')" +
				" then (select salary_per from n_incsalarybasic  where  id_year='0') when(a.position<>'CN')and (wt.is_test='0')" +
				" and (wt.sal_status='85')then ROUND((( b.bsalary )*100/85),-3) when(wt.is_test='0') and (wt.sal_status='100')" +
				" then (b.bsalary ) END)as LUONG, wt.sal_status AS TRANGTHAI From n_employee a, n_department dt,n_newworker_test wt," +
				" n_basic_salary b  where a.depsn=dt.id_dept " +
				" and a.empsn =b.empsn and b.keys=1 and a.empsn=wt.empsn ";
		if(!dscsothe.getText().equals("")){
			sql+=" and a.empsn ='"+dscsothe.getText()+"'";
		}
		if(sf_xuong.getSelectedIndex()!=-1){
			sql+=" and dt.name_fact='"+sf_xuong.getSelectedItem().toString()+"'";
		}
		if(sf_nhom.getSelectedIndex()!=-1){
			sql+=" and dt.name_group='"+c.convertToVNI(sf_nhom.getSelectedItem().toString())+"'";
		}
		if(sf_donvi.getSelectedIndex()!=-1){
			sql+=" and dt.id_dept='"+ListBinder.get(sf_donvi)+"'";
		}
		if((!dscBetweenngay.getFromText().equals("")) &&(!dscBetweenngay.getToText().equals(""))){
			sql+=" and a.date_hired between ? and ?";
		} 
		else sql+=" and to_char(a.date_hired,'mm/yyyy')=? ";
		     sql+="order by dt.name_group,a.empsn";
		     
	     PageableSortableTableModel model = (PageableSortableTableModel)Table.getModel();
		 model.clear();
	     int i=0;
		try {
			
			
			pstm=con.prepareStatement(sql,rs.TYPE_SCROLL_INSENSITIVE,rs.CONCUR_READ_ONLY);
			if((!dscBetweenngay.getFromText().equals("")) &&(!dscBetweenngay.getToText().equals(""))){
				String d=dscBetweenngay.getFromText()+"/"+ngay;
				String d2=dscBetweenngay.getToText()+"/"+ngay;
				
				/*java.util.Date ud=df.parse(d);
				java.sql.Date sqldf=new java.sql.Date(ud.getTime());
				java.util.Date ud2=df.parse(d2);
				java.sql.Date sqldt=new java.sql.Date(ud2.getTime());*/
				
				Date from,to;
				from=f.getdate(d);
				to=f.getdate(d2);
				
				pstm.setDate(1, from);
				pstm.setDate(2, to);
			}else pstm.setString(1, ngay);
			
			rs=pstm.executeQuery();
			if(rs.next()==false){
				Application.getApp().showMessageDialog(MessageDialog.TYPE_INFORMATION+MessageDialog.CONTROLS_OK,
						"Không có dl...@@@@");
				lb_sum.setText("");
				bt_ex.setEnabled(false);
				return;
			}else{
				rs.last();
				int a = rs.getRow();
				lb_sum.setText("Tất cả "+a+" CNV");
				lb_sum.setForeground(new Color(0,0,255));
				rs.beforeFirst();
				while(rs.next()){
					model.setValueAt(rs.getString(1), 0, i);//name_fact
					model.setValueAt(c.convertToUnicode(rs.getString(2)), 1, i);//name_group
					model.setValueAt(c.convertToUnicode(rs.getString(3)), 2, i);//name_dept_name
					model.setValueAt(rs.getString(4), 3, i);//empsn
					model.setValueAt(rs.getString(5), 4, i);//empcn
					model.setValueAt(c.convertToUnicode(rs.getString(6)), 5, i);//fname
					model.setValueAt(c.convertToUnicode(rs.getString(7)), 6, i);//lname
					model.setValueAt(df.format(rs.getDate(8)), 7, i);//date_hired
					model.setValueAt(c.convertToUnicode(rs.getString(9)), 8, i);//position
					model.setValueAt(rs.getString(10), 9, i);//pccviec
					model.setValueAt(c.convertToUnicode(rs.getString(11)), 10, i);//sex
					model.setValueAt(rs.getString(12), 11, i);//birthday
					model.setValueAt(c.convertToUnicode(rs.getString(13)), 12, i);//education
					model.setValueAt(c.convertToUnicode(rs.getString(14)), 13, i);//address
					model.setValueAt(c.convertToUnicode(rs.getString(15)), 14, i);//city
					model.setValueAt(c.convertToUnicode(rs.getString(16)), 15, i);//birth_place
					model.setValueAt(rs.getString(17), 16, i);//id_no
					model.setValueAt(c.convertToUnicode(rs.getString(18)), 17, i);//id_place
					model.setValueAt(df.format(rs.getDate(19)), 18, i);//ngaycap_id
					model.setValueAt(c.convertToUnicode(rs.getString(20)), 19, i);//ethnic
					model.setValueAt(c.convertToUnicode(rs.getString(21)), 20, i);//religion
					model.setValueAt(rs.getString(22), 21, i);//salary
					model.setValueAt(rs.getString(23), 22, i);//sal_status
					i++;
					Navigation.reset();
					
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void doExportDataObjectSet() throws IOException{
		String str="";
		StopWatch sw = new StopWatch();
		sw.start();
		int rows=0;
		try {
			rs.last();
			rows=rs.getRow();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		File f = ReportFileManager.getReportFormatFolder("ex");
		InputStream in = new FileInputStream(new File(f.getPath(),"CN_TV.xls"));
		HSSFWorkbook wb = new HSSFWorkbook(in);
		int m = 2;//row 1
		int n = 1;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row=null;
		HSSFCell cell = null;
		HSSFFont font = wb.createFont();
		HSSFCellStyle style = wb.createCellStyle();
		font.setFontName("Arial");
		font.setColor(HSSFColor.RED.index);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short)15);
		style.setFont(font);
		
		row=sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));
		cell=row.createCell(0);
		cell.setCellStyle(style);
		
		if((!dscBetweenngay.getFromText().equals("")) && (!dscBetweenngay.getFromText().equals(""))){
			cell.setCellValue("DS CNV THỬ VIỆC NHẬP XƯỞNG TỪ NGÀY "+dscBetweenngay.getFromText()+
					" ---> "+dscBetweenngay.getToText()+"/"+ngay);
			
		}else cell.setCellValue("DS CNV THỬ VIỆC NHẬP XƯỞNG THÁNG "+ngay);
		
		if(sf_xuong.getSelectedIndex()!=-1){
			str=sf_xuong.getSelectedItem().toString();
		}
		if(sf_nhom.getSelectedIndex()!=-1){
			str+="."+sf_nhom.getSelectedItem().toString();
		}
		if(sf_donvi.getSelectedIndex()!=-1){
			str+="."+sf_donvi.getSelectedItem().toString();
		}
		row=sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1,1,0,4));
		cell=row.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue(str);
		
		for (int i = 0; i < rows; i++) {
			m++;
			n++;
			PageableSortableTableModel model = (PageableSortableTableModel) Table.getModel();
			model.setCurrentPage(0);
			row = sheet.createRow(m);
			
			cell = row.createCell(0);
			cell.setCellValue(model.getValueAt(0, i).toString());//name_fact
			cell = row.createCell(1);
			cell.setCellValue(c.convertToVNI(model.getValueAt(1, i).toString()));//name_group
			cell = row.createCell(2);
			cell.setCellValue(c.convertToVNI(model.getValueAt(2, i).toString()));//name_dept_name
			cell = row.createCell(3);
			cell.setCellValue(model.getValueAt(3, i).toString());//empsn
			cell = row.createCell(4);
			cell.setCellValue(model.getValueAt(4, i).toString());//empsn
			cell = row.createCell(5);
			cell.setCellValue(c.convertToVNI(model.getValueAt(5, i).toString()));//fname
			cell = row.createCell(6);
			cell.setCellValue(c.convertToVNI(model.getValueAt(6, i).toString()));//lname
			cell = row.createCell(7);
			cell.setCellValue(model.getValueAt(7, i).toString());//date_hired
			cell = row.createCell(8);
			cell.setCellValue(c.convertToVNI(model.getValueAt(8, i).toString()));//position
			cell = row.createCell(9);
			cell.setCellValue(model.getValueAt(9, i).toString());//pccviec
			cell = row.createCell(10);
			cell.setCellValue(c.convertToVNI(model.getValueAt(10, i).toString()));//sex
			cell = row.createCell(11);
			cell.setCellValue(model.getValueAt(11, i).toString());//birthday
			cell = row.createCell(12);
			cell.setCellValue(c.convertToVNI(model.getValueAt(12, i).toString()));//education
			cell = row.createCell(13);
			cell.setCellValue(c.convertToVNI(model.getValueAt(13, i).toString()));//address
			cell = row.createCell(14);
			cell.setCellValue(c.convertToVNI(model.getValueAt(14, i).toString()));//city
			cell = row.createCell(15);
			cell.setCellValue(c.convertToVNI(model.getValueAt(15, i).toString()));//birth_place
			cell = row.createCell(16);
			cell.setCellValue(model.getValueAt(16, i).toString());//id_no
			cell = row.createCell(17);
			cell.setCellValue(c.convertToVNI(model.getValueAt(17, i).toString()));//id_place
			cell = row.createCell(18);
			cell.setCellValue(model.getValueAt(18, i).toString());//ngaycap_id
			cell = row.createCell(19);
			cell.setCellValue(c.convertToVNI(model.getValueAt(19, i).toString()));//ethnic
			cell = row.createCell(20);
			cell.setCellValue(c.convertToVNI(model.getValueAt(20, i).toString()));//religion
			cell = row.createCell(21);
			Object s=model.getValueAt(21, i);
			if(s==null){
				cell.setCellValue("");//salary
			}else{
				cell.setCellValue(model.getValueAt(21, i).toString());//salary
			}
			cell = row.createCell(22);
			cell.setCellValue(model.getValueAt(22, i).toString());//sal_status
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
			
				saveFile = new File(f1.getParentFile(),URLEncoder.encode(Application.getApp().getLoginInfo().getUserID() + ";application/vnd.ms-excel;" +"DS_CN_THUVIEC_"+ngay+"_"+t +".xls", "UTF-8"));
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
	public void check_input(){
		String from,to;
		from=dscBetweenngay.getFromText();
		to=dscBetweenngay.getToText();
		tieptuc=false;
		if((sf_xuong.getSelectedIndex()==-1) && (dscsothe.getText().equals(""))){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
			"Chưa đủ thông tin để tìm...@@@@");
			return;
		}
		if((!from.equals("")) && (!to.equals(""))){
			
			if(from.length()!=2){
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
					"Ngày không hợp lệ...@@@@");
					dscBetweenngay.getFromField().setBackground(new Color(255,200,200));
					return;
				}
			else if(Integer.parseInt(from)>31){
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
					"Ngày không hợp lệ...@@@@");
					dscBetweenngay.getFromField().setBackground(new Color(255,200,200));
					return;
			}else dscBetweenngay.getFromField().setBackground(new Color(220,255,240));
		//end from date
			if(to.length()!=2){
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
					"Ngày không hợp lệ...@@@@");
					dscBetweenngay.getToField().setBackground(new Color(255,200,200));
					return;
			}
			else if(Integer.parseInt(to)>31){
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
					"Ngày không hợp lệ...@@@@");
					dscBetweenngay.getToField().setBackground(new Color(255,200,200));
					return;
			}
			else if(Integer.parseInt(to)<Integer.parseInt(from)){
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
				"Ngày không hợp lệ...@@@@");
				return;
			}else {
				dscBetweenngay.getToField().setBackground(new Color(220,255,240));
				String temp=dscBetweenngay.getFromText();
				String temp2=dscBetweenngay.getToText();
				tieptuc=true;
			}
		}
		else if((!from.equals("")) && (to.equals(""))){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
					"Chưa đủ thông tin để tìm...@@@@");
			return;
		}
		else if((from.equals("")) && (!to.equals(""))){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
			"Chưa đủ thông tin để tìm...@@@@");
			return;
		}else tieptuc=true;
	}
	private void tim_ac(ActionEvent e) {
		//TODO Implement.
		
		String n="",dv="",x;
		if(sf_nhom.getSelectedIndex()!=-1){
			n=sf_nhom.getSelectedItem().toString();
		}else n="";
		if(sf_donvi.getSelectedIndex()!=-1){
			dv=sf_donvi.getSelectedItem().toString();
		}else dv="";
		if(sf_xuong.getSelectedIndex()!=-1){
			x=sf_xuong.getSelectedItem().toString();
		}else x="";
		
		/*Boolean ql=f.check_QL(dscsothe.getText(), x,n,dv, "", u.getPB_USERNO());
		if(ql==false){
			return;
		}else{*/
		check_input();
		if(tieptuc==true){
			loaddata();
			bt_ex.setEnabled(true);
		}else bt_ex.setEnabled(false);
		//}
		
	}
	private void ex_ac(ActionEvent e) {
		//TODO Implement.
		MessageDialog msgDialog = new MessageDialog(
				MessageDialog.TYPE_WARNING + MessageDialog.CONTROLS_YES_NO,
				"XUẤT DL SANG EXCEL???");
		msgDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MessageDialog.COMMAND_OK.equals(e.getActionCommand())) {
					try {
						doExportDataObjectSet();
					} catch (IOException ex) {
						// TODO: handle exception
						ex.printStackTrace();
					}
				}
			}
		});
	}
	private void st_fc(FocusEvent e) {
			//TODO Implement.
		sf_xuong.setSelectedIndex(-1);
		sf_nhom.setSelectedIndex(-1);
		sf_donvi.setSelectedIndex(-1);
		
		ListBinder.bindSelectField(sf_xuong, FVGenericInfo.getFactories(), true);
		ListBinder.bindSelectField(sf_nhom, FVGenericInfo.getGroup(sf_xuong.getSelectedItem()), true);
		ListBinder.bindSelectField(sf_donvi, FVGenericInfo.getDeptName(sf_xuong.getSelectedItem(), sf_nhom.getSelectedItem()), true);
		}

	private void st_action(ActionEvent e) {
		//TODO Implement.
		String tt=f.getchuoi(dscsothe.getText());
		if(tt.equals("false")){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
			"so the da ton tai");
			return;
		}else		System.out.println(tt);
	}
	public void reset(ActionEvent e){
		lb_sum.setText("");
		PageableSortableTableModel model = (PageableSortableTableModel)Table.getModel();
		model.clear();		
		Navigation.reset();
		ListBinder.bindSelectField(sf_xuong, FVGenericInfo.getFactories(), true);
		ListBinder.bindSelectField(sf_nhom, FVGenericInfo.getGroup(sf_xuong.getSelectedItem()), true);
		ListBinder.bindSelectField(sf_donvi, FVGenericInfo.getDeptName(sf_xuong.getSelectedItem(), sf_nhom.getSelectedItem()), true);
	}
	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(120, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane1.setSeparatorColor(new Color(0x8080ff));
		splitPane1.setResizable(true);
		add(splitPane1);
		ContentPane contentPane1 = new ContentPane();
		splitPane1.add(contentPane1);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(2, Extent.PX)));
		grid1.setSize(6);
		contentPane1.add(grid1);
		Label label1 = new Label();
		label1.setText("Từ Ngày");
		grid1.add(label1);
		dscBetweenngay = new DscBetweenField();
		dscBetweenngay.setFieldType(DscBetweenField.TYPE_NumField);
		dscBetweenngay.setBetweenLabel("Đến Ngày");
		grid1.add(dscBetweenngay);
		Label label2 = new Label();
		label2.setText("Tháng");
		grid1.add(label2);
		sf_thang = new SelectField();
		grid1.add(sf_thang);
		Label label3 = new Label();
		label3.setText("Năm");
		grid1.add(label3);
		sf_nam = new SelectField();
		grid1.add(sf_nam);
		Label label4 = new Label();
		label4.setText("Số Thẻ");
		grid1.add(label4);
		dscsothe = new DscField();
		dscsothe.setInputType(DscField.INPUT_TYPE_NUMERIC);
		dscsothe.setMaximumLength(8);
		dscsothe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				st_action(e);
			}
		});
		dscsothe.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				st_fc(e);
			}
	
			public void focusLost(FocusEvent e) {
			}
		});
		grid1.add(dscsothe);
		Label label5 = new Label();
		label5.setText("Xưởng");
		grid1.add(label5);
		sf_xuong = new SelectField();
		sf_xuong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xuong_ac(e);
			}
		});
		grid1.add(sf_xuong);
		Label label6 = new Label();
		grid1.add(label6);
		
		bt_tim = new Button();
		ResourceImageReference imageReference = new ResourceImageReference(
			"/dsc/echo2app/resource/image/an/a_timkiem.png");
		bt_tim.setIcon(imageReference);
		ResourceImageReference imageReference1 = new ResourceImageReference(
			"/dsc/echo2app/resource/image/an/a_timkiem_ro.png");
		bt_tim.setRolloverIcon(imageReference1);
		bt_tim.setWidth(new Extent(50, Extent.PX));
		bt_tim.setRolloverEnabled(true);
		bt_tim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tim_ac(e);
			}
		});
		grid1.add(bt_tim);
		Label label7 = new Label();
		grid1.add(label7);
		Label label9 = new Label();
		//grid1.add(label9);
		lb_sum = new Label();
		grid1.add(lb_sum);
		Label label10 = new Label();
		label10.setText("Nhóm");
		grid1.add(label10);
		sf_nhom = new SelectField();
		sf_nhom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nhom_ac(e);
			}
		});
		grid1.add(sf_nhom);
		Label label12 = new Label();
		grid1.add(label12);
		
		bt_ex = new Button();
		bt_ex.setEnabled(false);
		ResourceImageReference imageReference10 = new ResourceImageReference(
		"/dsc/echo2app/resource/image/an/a_excel.png");
			bt_ex.setIcon(imageReference10);
		ResourceImageReference imageReference11 = new ResourceImageReference(
			"/dsc/echo2app/resource/image/an/a_excel_ro.png");
		bt_ex.setRolloverIcon(imageReference11);
		bt_ex.setToolTipText("Xuất ds sang excel");
		bt_ex.setRolloverEnabled(true);
		ResourceImageReference imageReference5 = new ResourceImageReference(
		"/dsc/echo2app/resource/image/an/a_excel_d.png");
		bt_ex.setDisabledIcon(imageReference5);
		bt_ex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ex_ac(e);
			}
		});		
		grid1.add(bt_ex);
		Label label13 = new Label();
		grid1.add(label13);
		Label label14 = new Label();
		grid1.add(label14);
		Label label15 = new Label();
		label15.setText("Đơn vị");
		grid1.add(label15);
		sf_donvi = new SelectField();
		grid1.add(sf_donvi);
		Label label11 = new Label();
		grid1.add(label11);
		
		
		b_reset = new Button();
		ResourceImageReference imageReference6 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/an/a_reset.png");
		b_reset.setIcon(imageReference6);
		ResourceImageReference imageReference7 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/an/a_reset_ro.png");
		b_reset.setRolloverIcon(imageReference7);
		b_reset.setToolTipText("Reset form");
		b_reset.setRolloverEnabled(true);
		b_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset(e);
			}
		});
		grid1.add(b_reset);
		SplitPane splitPane2 = new SplitPane();
		splitPane2.setSeparatorPosition(new Extent(24, Extent.PX));
		splitPane2.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane2.setSeparatorColor(new Color(0x8080ff));
		splitPane2.setResizable(true);
		splitPane1.add(splitPane2);
		Navigation = new DscPageNavigation();
		Navigation.setRowsPerPage(20);
		Navigation.setBackground(new Color(0xffffca));
		splitPane2.add(Navigation);
		Table = new DscPageableSortableTable();
		Table.setStyleName("Table.DscPageableSortableTable");
		Table.setAutoCreateColumnsFromModel(false);
		Table.setInsets(new Insets(new Extent(2, Extent.PX)));
		Table.setRolloverBackground(new Color(0xb0ffb0));
		Table.setRolloverEnabled(true);
		splitPane2.add(Table);
	}
}
