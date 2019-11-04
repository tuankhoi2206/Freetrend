package ds.program.fvhr.tien.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import dsc.dao.IGenericDAO;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import com.sun.org.apache.xerces.internal.impl.dtd.models.DFAContentModel;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ListBox;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.button.AbstractButton;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import ds.program.fvhr.dao.generic.FvGenericDAO;
import ds.program.fvhr.dao.hrreport.ReportDao;
import ds.program.fvhr.domain.N_NIKE_CALENDAR;
import ds.program.fvhr.domain.N_SHIFT;
import ds.program.fvhr.son.ui.DeptUserControl;
import ds.program.fvhr.ui.hrreport.BCTHBuilder;
import ds.program.fvhr.ui.hrreport.BCTHTableBuilder;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscGroupCheckBox;
import dsc.echo2app.component.table.PageableSortableTableModel;
import echopointng.ComboBox;
import echopointng.GroupBox;
import fv.components.MrBeanBrowserContent;
import fv.components.SelectItem;
import fv.components.SimpleReportProgram;
import fv.util.ApplicationHelper;
import fv.util.FVGenericInfo;
import fv.util.HRUtils;
import fv.util.ListBinder;
import fv.util.ReportUtils;

public class ExportExelICcard extends SimpleReportProgram{

	private DscDateField dsDate;
	private Grid rootLayout;
	private GroupBox group1;
	private  Label lblEmpsn ;
	private Label lblEmpsnTo;
	private Label lblEmpsnFrom;
	private SplitPane splitPane1;
	private RadioButton radWeek;
	private ComboBox cboWeek;
	private RadioButton radDayOT;
	private RadioButton radWeekOT;
	private RadioButton radDayToDayOT;
	private RadioButton radVSOT;
	private RadioButton radDate;
	private DscDateField dfFromDate;
	private DscDateField dfToDate;
	private DscField txtParam;
	private CheckBox chkWorkingTime;
	private RadioButton radEmpsn;
	private DscField txtEmpsn;
	private RadioButton radDept;
	private SelectField sfFact;	
	private SelectField sfLean;
	private SelectField sfDept;
	private DscGroupCheckBox groupFVL;
	private CheckBox chkFv1;
	private CheckBox chkFv2;
	private CheckBox chkFv3;
	private CheckBox chkFv4;
	private CheckBox chkFv5;
	private CheckBox chkOther;
	private DeptUserControl myDeptControl;
	private RadioButton radDoicalamviec;
	private RadioButton radNgaynhapvaoHT;
	private RadioButton radHienhanh;
	private GroupBox group2;
	private Grid grid3;
	private MrBeanBrowserContent browserContent1;
	private MrBeanBrowserContent browserContent2;
	private RadioButton radDate_;
	private Label lblFact;
	private DscDateField dfDate;
	private SimpleDateFormat dateFormat;
	private ReportDao dao;
	private RadioButton radFact;
	private SelectField sfCalamviec;
	MappingPropertyEditor editorShift;
	private Label lblSum;
	private Grid grid4;
	private RadioButton radDanhsach;
	private ListBox lstEmpsn;

	public ExportExelICcard()
	{
		initComponents();
		additionInit();
		EMPSNBuilder builder = new EMPSNTableBuilder();
		browserContent1 = builder.getTable(EMPSNBuilder.TYPE1);
		browserContent2 = builder.getTable(EMPSNBuilder.TYPE2);
		splitPane1.add(browserContent1);
	}
	@Override
	protected int doInit() {
		int ret = super.doInit();
		getToolbar().setSearchButtonEnable(false);
		getToolbar().setConfigButtonEnable(false);
		getToolbar().setPdfButtonEnable(false);
		return ret;
	}
	public ReportDao getDao(){
		if (dao==null)
			dao = new ReportDao();
		return dao;
	}
	@Override
	protected void doReset()
	{   dfDate.getTextField().setText("");
	    dfFromDate.getTextField().setText("");
	    dfToDate.getTextField().setText("");
		sfFact.setSelectedIndex(-1);
		sfDept.setSelectedIndex(-1);
		sfLean.setSelectedIndex(-1);
		txtEmpsn.setText("");
		sfCalamviec.setSelectedIndex(-1);
	}
	@Override
	protected void doRefresh() {
		if (!validateUI()){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, getErrorMessage());
			return;
		}
		StopWatch sw = new StopWatch();
		sw.start();
		String sql = buildSql();	
		 String type="";
		 if(radDayOT.isSelected())
		 {
			 type=BCTHBuilder.TYPE1;
				if (splitPane1.getComponent(1) != browserContent1){
					splitPane1.remove(1);
					splitPane1.add(browserContent1);
				}
		 }
		 else
		 {
			 type=BCTHBuilder.TYPE2;
			if (splitPane1.getComponent(1) != browserContent2){
				splitPane1.remove(1);
				splitPane1.add(browserContent2);
			}
		}
		System.out.println("Main query: " + sql);
		sw.split();
		List<EMPSN_E> list = null;
		if(radHienhanh.isSelected())
		{
			list=getDao().getListempsn(type, sql,  new Object[]{1,1});				
		}		
		else 
		{
			if(radDate_.isSelected())
			{   java.sql.Date date = new java.sql.Date(dfDate.getSelectedDate().getTimeInMillis());
				list=getDao().getListempsn(type, sql,  new Object[]{date});
			}
			else
			{
				java.sql.Date date1 = new java.sql.Date(dfFromDate.getSelectedDate().getTimeInMillis());
				java.sql.Date date2 = new java.sql.Date(dfToDate.getSelectedDate().getTimeInMillis());			
				list=getDao().getListempsn(type, sql,  new Object[]{date1,date2});
			}
		}
		MrBeanBrowserContent br = (MrBeanBrowserContent) splitPane1.getComponent(1);
		br.setListData(list);		
		br.refresh();
		sw.stop();
		lblSum.setText("Tổng Cộng có :"+list.size()+"CNV");	
		
	}
	@Override
	public boolean validateUI() {
		if(radDate_.isSelected())
		{
			try {
				dateFormat.parse(dfDate.getText());
			} catch (ParseException e) {
				setErrorMessage("Ngày tháng không hợp lệ");
				return false;
			}
		}
		if(radDate.isSelected())
		{
			try{
				dateFormat.parse(dfFromDate.getText());
				dateFormat.parse(dfToDate.getText());
			}catch(Exception e){
				setErrorMessage("Ngày tháng không hợp lệ");
				return false;
			}
			if (dfFromDate.getSelectedDate().compareTo(dfToDate.getSelectedDate())>0){
				setErrorMessage("Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
				return false;
			}	
		}
		if (radEmpsn.isSelected())
		{
			if (!txtEmpsn.getText().matches("[0-9]{8}")){
				setErrorMessage("Số thẻ không hợp lệ");
				return false;
			}
		}
		if(radFact.isSelected())
		{
			if (sfFact.getSelectedIndex()<0){
				setErrorMessage("Chọn xưởng");
				return false;
			}
		}
		
		return true;
	}
	private String buildSql(){
		
		String sql1="";
		String sql2="";		 
		if(radDayOT.isSelected())
		{
		    if(radDate_.isSelected())
		    	
		    {
		    	sql1= " and t.dates=?";
		    }
		    else
		    {
		    	sql1=" and t.dates>=? and t.dates<=?";	
		    }
		    sql2="select e.empsn,e.fname||' '||e.lname as empna, e.code,e.empcn,e.SHIFT,e.SEX,t.depsn as id_dept, " +
			  		"(select d.name_dept from n_department d where d.id_dept=t.depsn) as depsn_new,"+
	                "(select d.name_dept from n_department d where d.id_dept=t.DEPSN_OLD) as depsn_OLD " +
	                "from N_EMPLOYEE e , N_CHDEPSN t"
	                +" WHERE e.EMPSN=t.EMPSN  and  e.empcn <>'*' and e.empcn <>'0' and e.empcn <> '0000000000' and e.empcn<> 'null' "+buildCondition()+sql1;
            }
		if(radWeekOT.isSelected())
		{
			if(radDate_.isSelected())
		    	
		    {
		    	sql1= " and e.date_hired=?";
		    }
			else
			{
				sql1=" and e.date_hired>=? and e.date_hired<=?";	
			}			 
			 sql2=" select e.empsn,e.fname||' '||e.lname as empna, e.code,e.empcn,e.SHIFT,e.SEX," +
					"d.name_dept  as depsn_new ,d.id_dept from   N_EMPLOYEE e ,n_department d where e.depsn=d.id_dept  and  e.empcn <>'*' and e.empcn <>'0' and e.empcn <> '0000000000' and e.empcn<> 'null' "+buildCondition()+sql1;
		}
		if(radDayToDayOT.isSelected())
		{
			if(radDate_.isSelected())		    	
		    {
		    	sql1= " and t.DATE_CHANGE=?";
		    }
			else
			{
				sql1=" and t.DATE_CHANGE>=? and t.DATE_CHANGE<=?";	
			} 
			 sql2="select e.empsn,e.fname||' '||e.lname as empna, e.code,e.empcn,e.SHIFT,e.SEX," +
				  		"(select d.id_dept from n_department d where d.id_dept=e.depsn) as id_dept,"+
		                "(select d.name_dept from n_department d where d.id_dept=e.depsn) as depsn_new " +
		                "from N_EMPLOYEE e , n_change_iccard t"
		                +" WHERE e.EMPSN=t.EMPSN  and  e.empcn <>'*' and e.empcn <>'0' and e.empcn <> '0000000000' and e.empcn<> 'null'  "+buildCondition()+sql1;
		}
		if(radVSOT.isSelected())
		{
			// dua theo CNV nghỉ việc ngày 
			if(radDate_.isSelected())		    	
		    {
		    	sql1= "  and  t.real_off_date=?";
		    }
			else
			{
				 sql1=" and t.real_off_date>=? and t.real_off_date<=?";
			}
		
			 sql2=" select e.empsn,e.fname||' '||e.lname as empna, e.code,e.empcn,e.SHIFT,e.SEX," +
						"t.DEPT  as depsn_new ,t.DEPSN as id_dept from   N_EMPLOYEE e ,n_emp_quit t where e.empsn=t.empsn and  e.empcn <>'*' and e.empcn <>'0' and e.empcn <> '0000000000' and e.empcn<> 'null'"+buildCondition()+sql1;
		}
		if(radDoicalamviec.isSelected())
		{
			//  dua theo doi ca lam viec
			if(radDate_.isSelected())
			{
				sql1=" and t.shift_date=?";
			}
			else
			{
				sql1=" and t.shift_date>=? and t.shift_date<=?";	
			}
			if(sfCalamviec.getSelectedIndex()>=0)
			{
				sql1=sql1+"and t.ID_SHIFT ="+"'"+sfCalamviec.getSelectedItem().toString()+"'";
			}
			 sql2="select DISTINCT e.empsn,e.fname||' '||e.lname as empna, e.code,e.empcn,t.ID_SHIFT as SHIFT,e.SEX," +
				  		"(select d.id_dept from n_department d where d.id_dept=e.depsn) as id_dept,"+
		                "(select d.name_dept from n_department d where d.id_dept=e.depsn) as depsn_new " +
		                "from N_EMPLOYEE e , n_register_shift t"
		                +" WHERE e.EMPSN=t.EMPSN  and  e.empcn <>'*' and e.empcn <>'0' and e.empcn <> '0000000000' and e.empcn<> 'null' "+buildCondition()+sql1;
		}
		if(radHienhanh.isSelected())
		{
			// dua theo hien hanh
			
			 sql1=" and 1=? and 1=?";
			 sql2=" select e.empsn,e.fname||' '||e.lname as empna, e.code,e.empcn,e.SHIFT,e.SEX," +
						"d.name_dept  as depsn_new ,d.id_dept  from   N_EMPLOYEE e ,n_department d where e.depsn=d.id_dept and  e.empcn <>'*' and e.empcn <>'0' and e.empcn <> '0000000000'"+buildCondition()+sql1;
		}			
		return sql2;
	}
	private String buildCondition(){
		String str;
		if (radEmpsn.isSelected()){	
			String strEmpsn="(";
			List<String> list=listEmpsn();
			if(list.size()>0)
			{
				for(int i=0;i<list.size();i++)
				{
					strEmpsn= strEmpsn+"'"+list.get(i)+"',";
					}
			}
			strEmpsn = StringUtils.substringBeforeLast(strEmpsn, ",");
			
			strEmpsn=strEmpsn+")";		
			str = " and e.empsn in " +strEmpsn;
		}else{
			if(radVSOT.isSelected())
			{
				str = " and t.depsn in (select d.id_dept from n_department d where d.id_dept=t.depsn ";	
			}
			else
			{			
					str = " and e.depsn in (select d.id_dept from n_department d where d.id_dept=e.depsn ";
		    }
			String fact="";
					if(sfFact.getSelectedIndex()>=0)
					{
						fact=  ListBinder.get(sfFact).toString();	
						str = str + " and d.name_fact='" + fact + "' ";
					}
					if (sfLean.getSelectedIndex()>=0){
					str = str + "and d.name_group='" + ListBinder.get(sfLean).toString() + "'";
					}
					if (sfDept.getSelectedIndex()>=0){
							str = str + "and d.name_dept_name='" + ListBinder.get(sfDept).toString() + "'";				
					}
			
			str = str + ")";
		
		}
		return str;
	}
	
	@Override
	protected HSSFWorkbook generateWorkbook() throws IOException {
		MrBeanBrowserContent br = (MrBeanBrowserContent) splitPane1.getComponent(1);
		PageableSortableTableModel model = (PageableSortableTableModel) br.getDataTable().getModel();
		if (model.getTotalRows()<=0) return null;
		HSSFWorkbook wb =null;
		HSSFSheet sheet;
		HSSFRow row;
		HSSFCell cell;		
		if (br==browserContent1){
			wb= ReportUtils.loadTemplate("fvhr", "CNV_IC_D.xls");
			 sheet = wb.getSheetAt(0);			
			row = sheet.getRow(1);
			for (int i=0;i<model.getTotalRows();i++){
				row = sheet.createRow(i+2);
				for (int j=0;j<9;j++){
					cell = row.createCell(j);
					Object obj = model.getValueAtAbsolute(j, i);	
					if (obj!=null){
						cell.setCellValue(String.valueOf(obj));
					}
				}
			}
			setReportFileName("CNV");
		}
		else
		{		
			wb= ReportUtils.loadTemplate("fvhr", "CNV_IC.xls");
			sheet = wb.getSheetAt(0);			
			row = sheet.getRow(1);
			for (int i=0;i<model.getTotalRows();i++){
				row = sheet.createRow(i+2);
				for (int j=0;j<8;j++){
					cell = row.createCell(j);
					Object obj = model.getValueAtAbsolute(j, i);	
					if (obj!=null){
						cell.setCellValue(String.valueOf(obj));
					}
				}
			}
			setReportFileName("CNV");
		}	
		return wb;
	}	
	private void additionInit(){
		dfDate.getTextField().setDisabledBackground(new Color(0xc0c0c0));
		dfFromDate.getTextField().setDisabledBackground(new Color(0xc0c0c0));
		dfToDate.getTextField().setDisabledBackground(new Color(0xc0c0c0));
		txtEmpsn.setDisabledBackground( new Color(0xc0c0c0));
		sfCalamviec.setDisabledBackground(new Color(0xc0c0c0));
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dfFromDate.setDateFormat(dateFormat);
		dfToDate.setDateFormat(dateFormat);		
		dfDate.setDateFormat(dateFormat);	
		Calendar dt=Calendar.getInstance();
		 dateFormat.format(dt.getTime());
		 dfDate.setDisplayedDate(dt);
		dfFromDate.getTextField().setText("");
		dfToDate.getTextField().setText("");
		dfFromDate.getDateChooser().setLocale(new Locale("en"));
		dfToDate.getDateChooser().setLocale(new Locale("en"));
		ListBinder.bindSelectField(sfFact, FVGenericInfo.getFactories(), false);
		editorShift=getEditorShift();
		ListBinder.bindDscSelectField(sfCalamviec, editorShift);
		sfFact.setEnabled(true);
	}
	private void optWeekTimeChanged(ActionEvent e) {
		if (e.getActionCommand().equals("w1")){
			
			dfDate.setEnabled(true);
			dfFromDate.setEnabled(false);
			dfToDate.setEnabled(false);
		}else{
			
			dfDate.setEnabled(false);
			dfFromDate.setEnabled(true);
			dfToDate.setEnabled(true);
		}
	}
	private void toSearchempsn(ActionEvent e)
	{
		if (e.getActionCommand().equals("w3"))
			{
						txtEmpsn.setEnabled(true);
						 sfDept.setEnabled(false);
						 sfFact.setEnabled(false);
						 sfLean.setEnabled(false);	
						 lstEmpsn.setEnabled(true);
		 }
		if (e.getActionCommand().equals("w4"))
		{
		    txtEmpsn.setEnabled(false);
	      	txtEmpsn.setText("");
			sfDept.setEnabled(true);
			sfFact.setEnabled(true);
			sfLean.setEnabled(true);
		}
		
		
	}
	private MappingPropertyEditor getEditorShift()
	{
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_SHIFT, String> dao = Application.getApp().getDao(N_SHIFT.class);
		List<N_SHIFT> list = dao.findAll(1000);
		for (N_SHIFT data:list){
			e.put(data.getID_SHIFT(),data.getID_SHIFT());
		}
		return e;
	}
	
	
	private void sfFactChanged(ActionEvent e) {
		SelectItem item = (SelectItem) sfFact.getSelectedItem();
		ListBinder.bindSelectField(sfLean, FVGenericInfo.getGroup(item.getValue()), true);
		ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue()), true);
	}

	private void sfLeanChanged(ActionEvent e) {
		SelectItem item = (SelectItem) sfFact.getSelectedItem();
		SelectItem litem = (SelectItem) sfLean.getSelectedItem();
		ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue(),litem.getValue()), true);
	}
	private void optSelected(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("op5"))	
		{
			sfCalamviec.setEnabled(true);
		}
		else
			sfCalamviec.setEnabled(false);
		
	}
	private void addToList(ActionEvent e) {
		String empsn = txtEmpsn.getText();
		if (!empsn.matches("[0-9]{8}")){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ không hợp lệ");
			return;
		}
		DefaultListModel model = (DefaultListModel) lstEmpsn.getModel();
		for (int i=0;i<model.size();i++){
			SelectItem item = (SelectItem) model.get(i);
			if (item.getValue().equals(empsn)){
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ đã có trong danh sách");
				return;
			}
		}
//		if (model.indexOf(empsn)>=0){
//			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ đã có trong danh sách");
//			return;
//		}
		
		HRUtils util = ApplicationHelper.getHRUtils();
		if (!util.isWorkingOrQuit(empsn)){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Số thẻ không có trong hệ thống hoặc đã nghỉ việc");
			return;
		}
		//check permission
		if (!util.getPermissionValidator().hasEmpsnPermission(empsn)){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Anh/chị không có quyền thao tác số thẻ này");
			return;
		}
		//emp = ValidEmpService.get(String empsn);
		String empna = FvGenericDAO.getInstanse().getFullName(empsn);
		SelectItem item = new SelectItem(empsn + "_" + empna, empsn);
		model.add(item);
//		if (model.size()>0) {
//			sfMonth.setEnabled(false);
//			sfYear.setEnabled(false);
//		}
		txtEmpsn.requestFocus();
	}
	private List<String> listEmpsn(){
	
			DefaultListModel model = (DefaultListModel) lstEmpsn.getModel();
			List<String> list = new ArrayList<String>();
			for (int i=0;i<model.size();i++){
				SelectItem item = (SelectItem) model.get(i);
				list.add(item.getValue().toString());
			}
			return list;
	}


	private void initComponents() {
		splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(250, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/SplitVertBar.png");
		splitPane1.setSeparatorVerticalImage(new FillImage(imageReference1));
		splitPane1.setResizable(true);
		add(splitPane1);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(2, Extent.PX)));
		SplitPaneLayoutData grid1LayoutData = new SplitPaneLayoutData();
		grid1LayoutData.setBackground(new Color(0x87bad6));
		grid1.setLayoutData(grid1LayoutData);
		grid1.setSize(3);
		splitPane1.add(grid1);
		grid3=new Grid();
		grid3.setSize(2);
		grid3.setInsets(new Insets(0, 3, 3, 3));
		grid3.setWidth(new Extent(100,Extent.PERCENT));
		
		GroupBox groupBox1 = new GroupBox();
		ButtonGroup date_group = new ButtonGroup();	
		radDate_=new RadioButton();
		
		radDate_.setText("Ngày");
		radDate_.setGroup(date_group);
		radDate_.setSelected(true);
		radDate_.setActionCommand("w1");
		radDate_.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				optWeekTimeChanged(e);
			}
		});
		grid3.add(radDate_);
		dfDate = new DscDateField();
		grid3.add(dfDate);
		radDate = new RadioButton();
		radDate.setText("Thời gian từ");
		radDate.setGroup(date_group);
		radDate.setActionCommand("w2");
		radDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				optWeekTimeChanged(e);
			}
		});
		grid3.add(radDate);
		dfFromDate = new DscDateField();
		dfFromDate.setEnabled(false);			
		grid3.add(dfFromDate);
		Label label4 = new Label();
		label4.setText("đến");
		GridLayoutData Label4LayoutData = new GridLayoutData();
		Label4LayoutData.setInsets(new Insets(new Extent(24, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		label4.setLayoutData(Label4LayoutData);
		grid3.add(label4);
		dfToDate = new DscDateField();
		dfToDate.setEnabled(false);
		grid3.add(dfToDate);		
		radEmpsn = new RadioButton();	
		ButtonGroup btgroup = new ButtonGroup();	
	radEmpsn.setText("Số thẻ");	
		radEmpsn.setGroup(btgroup);
		radEmpsn.setActionCommand("w3");
		radEmpsn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			toSearchempsn(e);
				
		}
		});
		grid3.add(radEmpsn);
		txtEmpsn = new DscField();
		txtEmpsn.setInputType(DscField.INPUT_TYPE_TEXT);
		txtEmpsn.setWidth(new Extent(180, Extent.PX));
		txtEmpsn.setDisabledBackground(new Color(0xc0c0c0));
		txtEmpsn.setMaximumLength(8);	
		txtEmpsn.setEnabled(false);
		txtEmpsn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				addToList(e);
				
			}
		});
		grid3.add(txtEmpsn);
		radFact = new RadioButton();
		radFact.setText("Xưởng");
		radFact.setActionCommand("w4");
		radFact.setGroup(btgroup);
		radFact.setSelected(true);
		radFact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toSearchempsn(e);
			}
		});
		grid3.add(radFact);
		sfFact = new SelectField();
		sfFact.setEnabled(true);
		sfFact.setWidth(new Extent(180, Extent.PX));
		sfFact.setDisabledBackground(new Color(0xc0c0c0));
		sfFact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sfFactChanged(e);
			}
		});
		grid3.add(sfFact);
		
		Label label6 = new Label();
		label6.setText("Lean");
		GridLayoutData label6LayoutData = new GridLayoutData();
		label6LayoutData.setInsets(new Insets(new Extent(24, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		label6.setLayoutData(label6LayoutData);
		grid3.add(label6);
		sfLean = new SelectField();
		sfLean.setEnabled(true);
		sfLean.setWidth(new Extent(180, Extent.PX));
		sfLean.setDisabledBackground(new Color(0xc0c0c0));
		sfLean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sfLeanChanged(e);
			}
		});
		grid3.add(sfLean);
		Label label8 = new Label();
		label8.setText("Đơn vị");
		GridLayoutData label8LayoutData = new GridLayoutData();
		label8LayoutData.setInsets(new Insets(new Extent(24, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		label8.setLayoutData(label8LayoutData);
		grid3.add(label8);
		sfDept = new SelectField();
		sfDept.setEnabled(true);
		sfDept.setWidth(new Extent(180, Extent.PX));
		sfDept.setDisabledBackground(new Color(0xc0c0c0));
		grid3.add(sfDept);
		
		Label lblShift = new Label();
		lblShift.setText("Ca làm việc");
		GridLayoutData lblShiftLayoutData = new GridLayoutData();
		label8LayoutData.setInsets(new Insets(new Extent(24, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		lblShift.setLayoutData(label8LayoutData);
		grid3.add(lblShift);
		sfCalamviec = new SelectField();
		sfCalamviec.setEnabled(false);
		sfCalamviec.setWidth(new Extent(180, Extent.PX));
		sfCalamviec.setDisabledBackground(new Color(0xc0c0c0));
		grid3.add(sfCalamviec);
		
		grid4=new Grid();
		grid4.setSize(1);
		
		radDanhsach = new RadioButton();
		radDanhsach.setText("Xuất theo danh sách");
		grid4.add(radDanhsach);
		lstEmpsn = new ListBox();
		lstEmpsn.setHeight(new Extent(250, Extent.PX));
		lstEmpsn.setWidth(new Extent(230, Extent.PX));
		lstEmpsn.setDisabledBackground(new Color(0xc0c0c0));
		GridLayoutData lstEmpsnLayoutData = new GridLayoutData();
		lstEmpsnLayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		lstEmpsnLayoutData.setRowSpan(8);
		lstEmpsn.setLayoutData(lstEmpsnLayoutData);
		lstEmpsn.setEnabled(false);
		grid4.add(lstEmpsn);
		
		grid1.add(grid3);
		
		
		
		
		
		
		
		
		
		
		
		
		
		grid1.add(grid4);
		
		groupBox1.setTitle("Chọn lựa danh sách");
		GridLayoutData groupBox1LayoutData = new GridLayoutData();
		groupBox1LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBox1LayoutData.setRowSpan(7);
		groupBox1.setLayoutData(groupBox1LayoutData);
		grid1.add(groupBox1);
		Grid grid2 = new Grid();
		grid2.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5,
				Extent.PX)));
		grid2.setSize(1);
		groupBox1.add(grid2);
		radDayOT = new RadioButton();
		radDayOT.setSelected(true);
		radDayOT.setText("Dựa theo ngày điều động");
		ButtonGroup opt = new ButtonGroup();
		radDayOT.setGroup(opt);
		radDayOT.setActionCommand("op1");
		radDayOT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		grid2.add(radDayOT);
		radWeekOT = new RadioButton();
		radWeekOT.setText("Dựa theo ngày nhập xưởng");
		radWeekOT.setGroup(opt);
		radWeekOT.setActionCommand("op2");
		radWeekOT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		grid2.add(radWeekOT);
		radDayToDayOT = new RadioButton();
		radDayToDayOT.setText("Dựa theo ngày đổi IC Card");
		radDayToDayOT.setGroup(opt);
		radDayToDayOT.setActionCommand("op3");
		radDayToDayOT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		grid2.add(radDayToDayOT);
		radVSOT = new RadioButton();
		radVSOT.setText("CNV nghỉ việc ngày ");
		radVSOT.setGroup(opt);
		radVSOT.setActionCommand("op4");
		radVSOT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		grid2.add(radVSOT);
		radDoicalamviec=new RadioButton();
		radDoicalamviec.setText("Dựa theo ngày đổi ca làm việc ");
		radDoicalamviec.setGroup(opt);
		radDoicalamviec.setActionCommand("op5");
		radDoicalamviec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		grid2.add(radDoicalamviec);		
		radHienhanh=new RadioButton();
		radHienhanh.setText(" Dựa theo hiện hành");
		radHienhanh.setGroup(opt);
		radHienhanh.setActionCommand("op7");
		radHienhanh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}

			
		});
		grid2.add(radHienhanh);		
		lblSum=new Label();
		lblSum.setText("");
		grid1.add(lblSum);
	}
	@Override
	protected void doSearch() {
		// TODO Auto-generated method stub
		
	}

	}
	

	


