package ds.program.fvhr.minh.dependent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import nextapp.echo2.app.ContentPane;
import ds.program.fvhr.tien.ui.N_EMP_RELATIVE_RP;
import ds.program.fvhr.ui.insurance.N_EMP_RELATIVE01MQuery;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.component.DscValuedRadioButton;
import dsc.echo2app.component.DscField;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SelectField;
import dsc.echo2app.ReportService;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.program.QueryPane;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.task.ATask;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.list.ListModel;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;
import dsc.echo2app.component.DscGroupRadioButton;
import dsc.echo2app.component.event.FocusEvent;
import dsc.echo2app.component.event.FocusListener;
import dsc.util.function.UUID;
import fv.components.SelectItem;
import fv.util.ApplicationHelper;
import fv.util.DbUtils;
import fv.util.FVGenericInfo;
import fv.util.HRUtils;
import fv.util.ListBinder;
import fv.util.ReportUtils;
import fv.util.Vni2Uni;

public class Emp_DependentQuery extends QueryNormal2 {

	private ResourceBundle resourceBundle;
	private DscValuedRadioButton rbtnEmpsn;
	private DscValuedRadioButton rbtnFact;
	private DscField txtEmpsn;
	private Label lblEmpsn;
	private Label lblFact;
	private Label lblGroup;
	private SelectField slfFact;
	private SelectField slfGroup;
	private SelectField slfDept;
	private Label lblDept;
	private Label lblBegin;
	private DscDateField dtfBegin;
	private Button btnExportPDF;
	private boolean blcheck;
	private Button btnExportExcel;
	private Button btnCancel;
	private int execExportType;	
	private String fact ;
	private String Group_Dept;
	private String id_Dept;
	
	/*
	 * Creates a new <code>TestQuery</code>.
	 */
	public Emp_DependentQuery() {
		super();

		// Add design-time configured components.
		initComponents();
		resourceBundle = ResourceBundle.getBundle(
				"resource.localization.UICaption", ApplicationInstance
				.getActive().getLocale());
		rbtnEmpsn.setText(resourceBundle.getString("N_EMPLOYEE.EMPSN"));
		lblEmpsn.setText(resourceBundle.getString("N_EMPLOYEE.EMPSN"));
		rbtnFact.setText(resourceBundle.getString("N_DEPARTMENT.NAME_FACT"));
		lblFact.setText(resourceBundle.getString("N_DEPARTMENT.NAME_FACT"));
		lblGroup.setText(resourceBundle.getString("N_DEPARTMENT.NAME_GROUP"));
		lblDept.setText(resourceBundle.getString("N_DEPARTMENT.NAME_DEPT_NAME"));
		lblBegin.setText(resourceBundle.getString("N_EMP_DEPENDENT.BEGINDATE"));
		ListBinder.bindSelectField(slfFact, FVGenericInfo.getFactories(), false);
		slfFact.setEnabled(false);
		dtfBegin.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dtfBegin.getDateChooser().setLocale(Locale.ENGLISH);
		dtfBegin.setSelectedDate(null);
		rbtnEmpsn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				doSelectrbtn();
				
			}
		});
		rbtnFact.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				doSelectrbtn();
				
			}
		});
		slfFact.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loadGroup();
				
			}
		});
		slfGroup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loadDept();
				
			}
		});
		btnExportExcel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				doExcel(2);
				
			}
		});
		btnExportPDF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				doExcel(1);
				
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				doCancel();
				
			}
		});
	
	}
	
	private void loadGroup() {
		slfGroup.setSelectedIndex(-1);
		DefaultListModel mG = ((DefaultListModel)slfGroup.getModel());
		mG.removeAll();
		
		SelectItem itemF = (SelectItem) slfFact.getSelectedItem();		
		ListBinder.bindSelectField(slfGroup, FVGenericInfo.getGroup(itemF.getValue()), true);
		slfDept.setSelectedIndex(-1);
		DefaultListModel mD = ((DefaultListModel)slfDept.getModel());
		mD.removeAll();
	}
	
	private void loadDept() {
		slfDept.setSelectedIndex(-1);
		DefaultListModel mD = ((DefaultListModel)slfDept.getModel());
		mD.removeAll();		
		SelectItem itemF = (SelectItem) slfFact.getSelectedItem();
		SelectItem itemG = (SelectItem) slfGroup.getSelectedItem();
		ListBinder.bindSelectField(slfDept, FVGenericInfo.getDeptName(itemF.getValue(),itemG.getValue()), true);
		System.out.println(slfGroup.getModel().size());
		System.out.println(slfDept.getModel().size());
	}
	
	private void doSelectrbtn() {
		//TODO Implement.
		if(rbtnEmpsn.isSelected())
		{
			txtEmpsn.setEnabled(true);		
			slfFact.setEnabled(false);
			slfGroup.setEnabled(false);
			slfDept.setEnabled(false);			
			dtfBegin.setEnabled(false);
		}
		else
		{
			txtEmpsn.setEnabled(false);			
			slfFact.setEnabled(true);
			slfGroup.setEnabled(true);
			slfDept.setEnabled(true);
			dtfBegin.setEnabled(true);
					
		}			
	}

	@Override
	protected boolean  checkData() {
		HRUtils utils=ApplicationHelper.getHRUtils();
		
		 if(rbtnEmpsn.isSelected())
		 {
			 String strEmpsn=txtEmpsn.getText();
			 if (!strEmpsn.matches("[0-9]{8}")){
				 Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ không hợp lệ");
					return false;
				}
			 if(!strEmpsn.equals("") && !utils.getPermissionValidator().hasEmpsnPermission(strEmpsn))
			 {
				 Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Anh/ Chị không có quyền thao tác số thẻ này");
					return false;
			 }
		 } 
		 return true;
	} 
	
	@Override
	protected void doQuery() {
		blcheck=checkData();	
		if(blcheck)
		{
			String empsn="";
			empsn=txtEmpsn.getText();
			List<Object> params = new ArrayList<Object>();
			String hsql = "";
			if(rbtnEmpsn.isSelected())
			{	
				hsql = hsql+" and o.EMPSN =?";				
				params.add(empsn);
				hsql = StringUtils.substringAfter(hsql, " and ");				
				ProgramCondition pc = new ProgramCondition(hsql, params.toArray());			
				getProgram().setQueryCondition(pc);				
				getProgram().refresh();				
			}
			if (rbtnFact.isSelected()){				
					if (slfFact.getSelectedIndex()<0){
						Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Chọn xưởng cần thao tác");
						return;
					}				
					hsql= hsql +" and o.EMPSN in (select E.EMPSN from N_EMPLOYEE E,N_DEPARTMENT D  where E.DEPSN=D.ID_DEPT";						
					
					if(slfFact.getSelectedIndex()>=0)
					{
						hsql=hsql+" and D.NAME_FACT=?";						
						String fact = ListBinder.get(slfFact).toString();					
						params.add(fact);
					}					
					if(slfGroup.getSelectedIndex()>=0)
					{
						hsql=hsql+" and D.NAME_GROUP=?";					
						String Group_Dept=ListBinder.get(slfGroup).toString();
						params.add(Group_Dept);
					}
					if(slfDept.getSelectedIndex()>=0)
					{  hsql=hsql+" and D.NAME_DEPT_NAME=?";						
						String id_Dept=Vni2Uni.convertToUnicode(ListBinder.get(slfDept).toString());
						params.add(id_Dept);
					}
					String listVungQL = DbUtils.parseInStringParamValues(ApplicationHelper.getRightList());
					hsql=hsql + " and E.USER_MANAGE_ID in "+ listVungQL;
					hsql=hsql+")";
					if(dtfBegin.isEnabled()==true && !dtfBegin.getText().equals(""))
					{				
						hsql=hsql +" and o.BEGINDATE=?";
						Date databg=dtfBegin.getSelectedDate().getTime();						
						params.add(databg);									
					}
					
					
					
					hsql = StringUtils.substringAfter(hsql, " and ");
					System.out.println(hsql);
					ProgramCondition pc = new ProgramCondition(hsql, params.toArray());			
					getProgram().setQueryCondition(pc);
					getProgram().refresh();
			}	
		}		
			
		txtEmpsn.setText("");
		slfFact.setSelectedIndex(-1);
		slfGroup.setSelectedIndex(-1);
		slfDept.setSelectedIndex(-1);
		DefaultListModel mG = ((DefaultListModel)slfGroup.getModel());
		mG.removeAll();
		DefaultListModel mD = ((DefaultListModel)slfDept.getModel());
		mD.removeAll();
						
	}

	protected void doExcel(int type)
	{
			String strSQL="select e.EMPSN,d.NAME_DEPT,d.NAME_FACT,( E.FNAME ||'  '||E.LNAME) FULLNAME,t.NAME_RELATIVE,t.BIRTHDAY,t.BEGINDATE " +
				", t. CONFIRM_DATE, t. ENDDATE ,t. IDKEY from N_EMP_DEPENDENT t" +
				", n_employee e, n_department d where e.empsn=t.empsn and e.depsn=d.id_dept ";		
		blcheck=checkData();		
		if(blcheck)
		{	String empsn="";
			empsn=txtEmpsn.getText();			
			if(rbtnEmpsn.isSelected())
			{	
				strSQL=strSQL+ "  AND  e.EMPSN='"+empsn+"'";	
			}
			if (rbtnFact.isSelected()){
					 if (slfFact.getSelectedIndex()<0){
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Chọn xưởng cần thao tác");
					return;
						}
					if(slfFact.getSelectedIndex()>=0)				{
						 fact = Vni2Uni.convertToVNI( ListBinder.get(slfFact).toString());
						strSQL=strSQL+ "  AND  D.NAME_FACT='"+fact+"'";					
					}					
					if(slfGroup.getSelectedIndex()>=0)
					{								
						Group_Dept=Vni2Uni.convertToVNI(ListBinder.get(slfGroup).toString());
						strSQL=strSQL+ "  AND  D.NAME_GROUP='"+Group_Dept+"'";				
					}
					if(slfDept.getSelectedIndex()>=0)
					{  
						 id_Dept=Vni2Uni.convertToVNI(ListBinder.get(slfDept).toString());
						 strSQL=strSQL+ "  AND  D.NAME_DEPT_NAME='"+id_Dept+"'";
						 }					
					if(dtfBegin.isEnabled()==true && !dtfBegin.getText().equals(""))
					{								
						SimpleDateFormat sf=new SimpleDateFormat("dd/MM/yyyy");
						Date datebg=dtfBegin.getSelectedDate().getTime();
						String strDatebg=sf.format(datebg);
						strSQL=strSQL+ "  AND  t.BEGINDATE=to_date('"+strDatebg+"','dd/MM/yyyy')";					
					}	
					String listVungQL = DbUtils.parseInStringParamValues(ApplicationHelper.getRightList());
					strSQL = strSQL + " and e.USER_MANAGE_ID in "+ listVungQL;
					strSQL = strSQL +"  ORDER BY d.NAME_DEPT,e.EMPSN DESC ";
				}		
			Connection ds=null;
	        ds = Application.getApp().getConnection();
	        List<N_EMP_RELATIVE_RP> list = new ArrayList<N_EMP_RELATIVE_RP>();  
	               Statement stmt = null;
	        try
	        {	  stmt = ds.createStatement();        	
	             ResultSet rs = stmt.executeQuery(strSQL);              
	                        while (rs.next())
	                        {
	                        	N_EMP_RELATIVE_RP objEmpRelative=new N_EMP_RELATIVE_RP();
	                        	objEmpRelative.setEMPSN(rs.getString("EMPSN")); 
	                        	objEmpRelative.setNAME_FACT(rs.getString("NAME_FACT")); 
	                        	objEmpRelative.setNAME_DEPT(rs.getString("NAME_DEPT"));                 
	                        	objEmpRelative.setFULLNAME(rs.getString("FULLNAME"));
	                        	objEmpRelative.setNAME_RELATIVE(rs.getString("NAME_RELATIVE"));
	                        	objEmpRelative.setBIRTHDAY(rs.getTimestamp("BIRTHDAY"));
	                        	objEmpRelative.setBEGINDATE(rs.getTimestamp("BEGINDATE"));
	                        	objEmpRelative.setCONFIRM_DATE(rs.getTimestamp("CONFIRM_DATE"));
	                        	objEmpRelative.setENDDATE(rs.getDate("ENDDATE"));
	                        	objEmpRelative.setIDKEY(rs.getString("IDKEY"));
	                        	list.add(objEmpRelative);
	                        }	        	
	        }catch (Exception e) {
			 System.out.print(e.toString());			
	        } 
	        // excel	        
	        if(type==2)
			{	
	        	try {		   FileInputStream in =new FileInputStream(N_EMP_RELATIVE01MQuery.class.getClassLoader().getResource("conf/format/fvhr/Trocapguitre.xls").getFile());
						        HSSFWorkbook wb=new HSSFWorkbook(in);
								HSSFRow row;
								HSSFCell cell;	
								int startRow=2;				
								HSSFSheet sheet=wb.getSheetAt(0);
								for(int i=0;i<list.size();i++)
								{
									N_EMP_RELATIVE_RP obj=list.get(i);
									row=sheet.createRow(startRow ++);
									cell=row.createCell(0);
									cell.setCellValue(i+1);
									cell=row.createCell(1);
									cell.setCellValue(obj.getEMPSN());
									cell=row.createCell(2);
									cell.setCellValue(Vni2Uni.convertToUnicode(obj.getNAME_DEPT()));
									cell=row.createCell(3);
									cell.setCellValue(Vni2Uni.convertToUnicode(obj.getFULLNAME()));
									cell=row.createCell(4);
									cell.setCellValue(Vni2Uni.convertToUnicode(obj.getNAME_RELATIVE()));
									cell=row.createCell(5);
									cell.setCellValue(obj.getBIRTHDAY());
									cell=row.createCell(6);
									cell.setCellValue(obj.getCONFIRM_DATE());
									cell=row.createCell(7);
									cell.setCellValue(obj.getBEGINDATE());
									cell=row.createCell(8);
									if(obj.getENDDATE()!=null)
										cell.setCellValue(obj.getENDDATE());
									else
										cell.setCellValue("");
									cell=row.createCell(9);
									cell.setCellValue(obj.getIDKEY());
								}
								File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
								f.deleteOnExit();
								FileOutputStream out = new FileOutputStream(f);
								wb.write(out);
								out.flush();
								out.close();
								File saveFile = new File(f.getParentFile(),URLEncoder.encode(Application.getApp().getLoginInfo().getUserID() + ";application/vnd.ms-excel;"  + "nguoiphuthuoc" + "_" + Calendar.getInstance().getTimeInMillis() + ".xls", "UTF-8"));			
								ReportFileManager.saveTempReportFile(f, saveFile);
								saveFile.deleteOnExit();
								Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
								
							} catch (FileNotFoundException e) {							
								System.out.println(e.toString());
								e.printStackTrace();
							} catch (IOException e) {
								System.out.println(e.toString());							
								e.printStackTrace();
							}
	        	}	        
	        	// ireport
	        	if(type==1)
	        	{       		
	        		 Map<String,  Object> params = new HashMap<String, Object>();
	        	        params.put("", "");
	        	        JRBeanCollectionDataSource dss = new JRBeanCollectionDataSource(list);
	        			try {
	        				JasperDesign jd = JRXmlLoader.load(ReportFileManager
	        						.getReportFormatFolder("fvhr/NguoiPhuThuoc.jrxml"));
	        				JasperReport jr = JasperCompileManager.compileReport(jd);			
	        				JasperPrint jp = JasperFillManager.fillReport(jr,params, dss);
	        				ReportUtils.doExportPdf(jp, "NguoiPhuThuoc");
	        			} catch (JRException e) {
	        				e.printStackTrace();
	        			}
	        	
	        	}
		}
	}
	
	public void doCancel()
	{
		txtEmpsn.setText("");
		slfFact.setSelectedIndex(-1);
		slfGroup.setSelectedIndex(-1);
		slfDept.setSelectedIndex(-1);
		slfGroup.removeAll();
		slfDept.removeAll();
		dtfBegin.setSelectedDate(null);
	}
	
	public String getViewerUrl() {
		HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
		String viewerUrl = request.getRequestURL() + "?" +
		WebRenderServlet.SERVICE_ID_PARAMETER + "=" + ReportService.INSTANCE.getId() + "&" + 
		ReportService.PARAM_TYPE + "=" + (execExportType == ATask.EXECTYPE_DIRECT ? ReportService.TYPE_TEMP : ReportService.TYPE_OUTPUT) + "&" +
		ReportService.PARAM_KEY + "=";
		return viewerUrl;
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		Column column1 = new Column();
		column1.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(10,
				Extent.PX)));
		add(column1);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5,
				Extent.PX)));
		grid1.setColumnWidth(0, new Extent(150, Extent.PX));
		grid1.setColumnWidth(1, new Extent(200, Extent.PX));
		column1.add(grid1);
		rbtnEmpsn = new DscValuedRadioButton();
		rbtnEmpsn.setSelected(true);
		rbtnEmpsn.setText("N_EMPLOYEE.EMPSN");
		ButtonGroup bg = new ButtonGroup();
		rbtnEmpsn.setGroup(bg);
		rbtnEmpsn.setRolloverForeground(new Color(0x80ffff));
		rbtnEmpsn.setForeground(new Color(0x0080c0));
		rbtnEmpsn.setRolloverEnabled(true);
		grid1.add(rbtnEmpsn);
		Row row1 = new Row();
		grid1.add(row1);
		lblEmpsn = new Label();
		lblEmpsn.setText("N_EMPLOYEE.EMPSN");
		grid1.add(lblEmpsn);
		txtEmpsn = new DscField();
		txtEmpsn.setId("txtEmpsn");
		txtEmpsn.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtEmpsn.setBackground(new Color(0xcffcfc));
		txtEmpsn.setWidth(new Extent(166, Extent.PX));
		txtEmpsn.setDisabledBackground(new Color(0xefefef));
		txtEmpsn.setMaximumLength(8);
		grid1.add(txtEmpsn);
		rbtnFact = new DscValuedRadioButton();
		rbtnFact.setText("N_DEPARTMENT.NAME_FACT");
		rbtnFact.setGroup(bg);
		rbtnFact.setRolloverForeground(new Color(0x80ffff));
		rbtnFact.setForeground(new Color(0x0080c0));
		rbtnFact.setRolloverEnabled(true);
		grid1.add(rbtnFact);
		Row row2 = new Row();
		grid1.add(row2);
		lblFact = new Label();
		lblFact.setText("N_DEPARTMENT.NAME_FACT");
		grid1.add(lblFact);
		slfFact = new SelectField();
		slfFact.setId("slfFact");
		slfFact.setEnabled(false);
		slfFact.setBackground(new Color(0xb5fbfb));
		slfFact.setWidth(new Extent(170, Extent.PX));
		slfFact.setDisabledBackground(new Color(0xefefef));
		grid1.add(slfFact);
		lblGroup = new Label();
		lblGroup.setText("N_DEPARTMENT.NAME_GROUP");
		grid1.add(lblGroup);
		slfGroup = new SelectField();
		slfGroup.setId("slfGroup");
		slfGroup.setEnabled(false);
		slfGroup.setBackground(new Color(0xb5fbfb));
		slfGroup.setWidth(new Extent(170, Extent.PX));
		slfGroup.setDisabledBackground(new Color(0xefefef));
		grid1.add(slfGroup);
		lblDept = new Label();
		lblDept.setText("N_DEPARTMENT.NAME_DEPT_NAME");
		grid1.add(lblDept);
		slfDept = new SelectField();
		slfDept.setId("slfDept");
		slfDept.setEnabled(false);
		slfDept.setBackground(new Color(0xb5fbfb));
		slfDept.setWidth(new Extent(170, Extent.PX));
		slfDept.setDisabledBackground(new Color(0xefefef));
		grid1.add(slfDept);
		lblBegin = new Label();
		lblBegin.setText("N_EMP_RELATIVE.BEGINDATE");
		grid1.add(lblBegin);
		dtfBegin = new DscDateField();
		dtfBegin.setEnabled(false);
		dtfBegin.setWidth(new Extent(167, Extent.PX));
		grid1.add(dtfBegin);
		Row row3 = new Row();
		ColumnLayoutData row3LayoutData = new ColumnLayoutData();
		row3LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.CENTER));
		row3.setLayoutData(row3LayoutData);
		column1.add(row3);
		Grid grid2 = new Grid();
		grid2.setHeight(new Extent(30, Extent.PX));
		grid2.setColumnWidth(0, new Extent(110, Extent.PX));
		grid2.setColumnWidth(1, new Extent(110, Extent.PX));
		grid2.setColumnWidth(2, new Extent(110, Extent.PX));
		grid2.setSize(3);
		row3.add(grid2);
		btnExportPDF = new Button();
		btnExportPDF.setText("Xuất thống kê");
		btnExportPDF.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_INSET));
		btnExportPDF.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_OUTSET));
		btnExportPDF.setBackground(new Color(0x43afc2));
		btnExportPDF.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(
				6, Extent.PX)));
		btnExportPDF.setWidth(new Extent(110, Extent.PX));
		btnExportPDF.setPressedEnabled(true);
		btnExportPDF.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_GROOVE));
		btnExportPDF.setRolloverEnabled(true);
		btnExportPDF.setForeground(Color.WHITE);
		GridLayoutData btnExportPDFLayoutData = new GridLayoutData();
		btnExportPDFLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.CENTER));
		btnExportPDF.setLayoutData(btnExportPDFLayoutData);
		grid2.add(btnExportPDF);
		btnExportExcel = new Button();
		btnExportExcel.setText("Xuất Excel");
		btnExportExcel.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_INSET));
		btnExportExcel.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_OUTSET));
		btnExportExcel.setBackground(new Color(0x43afc2));
		btnExportExcel.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(6, Extent.PX)));
		btnExportExcel.setWidth(new Extent(110, Extent.PX));
		btnExportExcel.setPressedEnabled(true);
		btnExportExcel.setBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_GROOVE));
		btnExportExcel.setRolloverEnabled(true);
		btnExportExcel.setForeground(Color.WHITE);
		GridLayoutData btnExportExcelLayoutData = new GridLayoutData();
		btnExportExcelLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.CENTER));
		btnExportExcel.setLayoutData(btnExportExcelLayoutData);
		grid2.add(btnExportExcel);
		btnCancel = new Button();
		btnCancel.setText("Cancel");
		btnCancel.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_INSET));
		btnCancel.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_OUTSET));
		btnCancel.setBackground(new Color(0x43afc2));
		btnCancel.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(6,
				Extent.PX)));
		btnCancel.setWidth(new Extent(110, Extent.PX));
		btnCancel.setPressedEnabled(true);
		btnCancel.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_GROOVE));
		btnCancel.setRolloverEnabled(true);
		btnCancel.setForeground(Color.WHITE);
		GridLayoutData btnCancelLayoutData = new GridLayoutData();
		btnCancelLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.CENTER));
		btnCancel.setLayoutData(btnCancelLayoutData);
		grid2.add(btnCancel);
	}
}
