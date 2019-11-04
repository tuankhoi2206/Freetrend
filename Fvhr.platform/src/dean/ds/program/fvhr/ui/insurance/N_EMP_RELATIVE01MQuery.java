package ds.program.fvhr.ui.insurance;

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
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import org.apache.commons.lang.StringUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.domain.N_EMP_RELATIVE;
import ds.program.fvhr.tien.ui.N_EMP_RELATIVE_RP;
import ds.program.fvhr.son.ui.insurance.social.SocialData;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.info.LoginInfo;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;
import dsc.echo2app.task.ATask;
import dsc.util.function.UUID;
import fv.components.SelectItem;
import fv.util.ApplicationHelper;
import fv.util.DbUtils;
import fv.util.FVGenericInfo;
import fv.util.HRUtils;
import fv.util.ListBinder;
import fv.util.ReportUtils;
import fv.util.Vni2Uni;

public class N_EMP_RELATIVE01MQuery extends QueryNormal2{

    private nextapp.echo2.app.Grid rootLayout;
	private Label Empsn_Label;
	private DscField Empsn_DscField;
	private ResourceBundle resourceBundle;
	private Grid rootLayout2;
	private RadioButton radioButton1;
	private Label lblEmpsn;
	private DscField txtempsn;
	private RadioButton radioButton2;
	private SelectField sfFactory;
	private SelectField sfGroup_dept;
	private Label label3;
	private SelectField sfDept;
	private Row row7;
	private CheckBox checkBox1;
	private DscDateField dscDateField1;	
	private Row row8;
	private Button btn_Thongke;
	private Button btn_Excel;
	private boolean blcheck;
	private String fact ;
	private String Group_Dept;
	private String id_Dept;
	private Date databg;
	private Button btn_Cancel;
	private int execExportType;	
	@Override
	protected void doQuery() {
		// TODO Auto-generated method stub
		blcheck=checkData();	
		if(blcheck)
		{
			String empsn="";
			empsn=txtempsn.getText();
			List<Object> params = new ArrayList<Object>();
			String hsql = "";
			if(radioButton1.isSelected())
			{	
				hsql = hsql+" and o.EMPSN =?";				
				params.add(empsn);
				hsql = StringUtils.substringAfter(hsql, " and ");				
				ProgramCondition pc = new ProgramCondition(hsql, params.toArray());			
				getProgram().setQueryCondition(pc);				
				getProgram().refresh();				
			}
			if (radioButton2.isSelected()){				
					if (sfFactory.getSelectedIndex()<0){
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Chọn xưởng cần thao tác");
					return;
						}				
						hsql= hsql +" and o.EMPSN in (select E.EMPSN from N_EMPLOYEE E,N_DEPARTMENT D  where E.DEPSN=D.ID_DEPT";						
					
					if(sfFactory.getSelectedIndex()>=0)
					{
						hsql=hsql+" and D.NAME_FACT=?";						
						String fact = ListBinder.get(sfFactory).toString();					
						params.add(fact);
					}					
					if(sfGroup_dept.getSelectedIndex()>=0)
					{
						hsql=hsql+" and D.NAME_GROUP=?";					
						String Group_Dept=ListBinder.get(sfGroup_dept).toString();
						params.add(Group_Dept);
					}
					if(sfDept.getSelectedIndex()>=0)
					{  hsql=hsql+" and D.NAME_DEPT_NAME=?";						
						String id_Dept=Vni2Uni.convertToUnicode(ListBinder.get(sfDept).toString());
						params.add(id_Dept);
					}
					String listVungQL = DbUtils.parseInStringParamValues(ApplicationHelper.getRightList());
					hsql=hsql + " and E.USER_MANAGE_ID in "+ listVungQL;
					hsql=hsql+")";
					if(dscDateField1.isEnabled()==true && !dscDateField1.getText().equals(""))
					{				
						hsql=hsql +" and o.BEGINDATE=?";
						Date databg=dscDateField1.getSelectedDate().getTime();						
						params.add(databg);									
					}
					
					
					
					hsql = StringUtils.substringAfter(hsql, " and ");
					System.out.println(hsql);
					ProgramCondition pc = new ProgramCondition(hsql, params.toArray());			
					getProgram().setQueryCondition(pc);
					
					getProgram().refresh();
			}		
		}					
	}	
	protected void doCancel()
	{
		txtempsn.setText("");
		sfFactory.setSelectedIndex(-1);
		sfGroup_dept.setSelectedIndex(-1);
		sfDept.setSelectedIndex(-1);
		dscDateField1.getTextField().setText("");
	}
	public String getViewerUrl() {
		HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
		String viewerUrl = request.getRequestURL() + "?" +
		WebRenderServlet.SERVICE_ID_PARAMETER + "=" + ReportService.INSTANCE.getId() + "&" + 
		ReportService.PARAM_TYPE + "=" + (execExportType == ATask.EXECTYPE_DIRECT ? ReportService.TYPE_TEMP : ReportService.TYPE_OUTPUT) + "&" +
		ReportService.PARAM_KEY + "=";
		return viewerUrl;
	}//getViewerUrl

	protected void doExcel(int type)
	{
			String strSQL="select e.EMPSN,d.NAME_DEPT,d.NAME_FACT,( E.FNAME ||'  '||E.LNAME) FULLNAME,t.NAME_RELATIVE,t.BIRTHDAY,t.BEGINDATE " +
				", t. CONFIRM_DATE, t. ENDDATE ,t. IDKEY from N_EMP_RELATIVE t" +
				", n_employee e, n_department d where e.empsn=t.empsn and e.depsn=d.id_dept ";		
		blcheck=checkData();		
		if(blcheck)
		{	String empsn="";
			empsn=txtempsn.getText();			
			if(radioButton1.isSelected())
			{	
				strSQL=strSQL+ "  AND  e.EMPSN='"+empsn+"'";	
			}
			if (radioButton2.isSelected()){
					 if (sfFactory.getSelectedIndex()<0){
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Chọn xưởng cần thao tác");
					return;
						}
					if(sfFactory.getSelectedIndex()>=0)				{
						 fact = Vni2Uni.convertToVNI( ListBinder.get(sfFactory).toString());
						strSQL=strSQL+ "  AND  D.NAME_FACT='"+fact+"'";					
					}					
					if(sfGroup_dept.getSelectedIndex()>=0)
					{								
					   Group_Dept=Vni2Uni.convertToVNI(ListBinder.get(sfGroup_dept).toString());
						strSQL=strSQL+ "  AND  D.NAME_GROUP='"+Group_Dept+"'";				
					}
					if(sfDept.getSelectedIndex()>=0)
					{  
						 id_Dept=Vni2Uni.convertToVNI(ListBinder.get(sfDept).toString());
						 strSQL=strSQL+ "  AND  D.NAME_DEPT_NAME='"+id_Dept+"'";
						 }					
					if(dscDateField1.isEnabled()==true && !dscDateField1.getText().equals(""))
					{								
						SimpleDateFormat sf=new SimpleDateFormat("dd/MM/yyyy");
						Date datebg=dscDateField1.getSelectedDate().getTime();
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
									cell.setCellValue(obj.getENDDATE());	
									cell=row.createCell(9);
									cell.setCellValue(obj.getIDKEY());
								}
								File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
								f.deleteOnExit();
								FileOutputStream out = new FileOutputStream(f);
								wb.write(out);
								out.flush();
								out.close();
								File saveFile = new File(f.getParentFile(),URLEncoder.encode(Application.getApp().getLoginInfo().getUserID() + ";application/vnd.ms-excel;"  + "trocapconnho" + "_" + Calendar.getInstance().getTimeInMillis() + ".xls", "UTF-8"));			
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
	        						.getReportFormatFolder("fvhr/Trocapguitre_1.jrxml"));
	        				JasperReport jr = JasperCompileManager.compileReport(jd);			
	        				JasperPrint jp = JasperFillManager.fillReport(jr,params, dss);
	        				ReportUtils.doExportPdf(jp, "Trocapguitre_1");
	        			} catch (JRException e) {
	        				e.printStackTrace();
	        			}
	        	
	        	}
		}
	}
	/**
	 * Creates a new <code>N_EMP_RELATIVE01MQuery</code>.
	 */
	public N_EMP_RELATIVE01MQuery() {
		super();		
		initComponents();
		ListBinder.bindSelectField(sfFactory, FVGenericInfo.getFactories(), false);
		sfFactory.setEnabled(false);
		dscDateField1.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));		
	}
	

	@Override
	protected int doInit() {
		int ret = super.doInit();
		ConditionManager conditionMgr = new ConditionManager();
		setConditionMgr(conditionMgr);
       
		return ret;

	}
	private void empGroupSelected(ActionEvent e) {
		//TODO Implement.
		if(e.getActionCommand().equals("cmd_emp"))
		{
			txtempsn.setEnabled(true);		
			sfFactory.setEnabled(false);
			sfGroup_dept.setEnabled(false);
			sfDept.setEnabled(false);			
			dscDateField1.setEnabled(false);
		}
		else
		{
			txtempsn.setEnabled(false);			
			sfFactory.setEnabled(true);
			sfGroup_dept.setEnabled(true);
			sfDept.setEnabled(true);
			dscDateField1.setEnabled(true);
					
			}			
	}
	@Override
	protected boolean  checkData() {
		HRUtils utils=ApplicationHelper.getHRUtils();
		
		 if(radioButton1.isSelected())
		 {
			 String strEmpsn=txtempsn.getText();
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
		 /*
		 if(radioButton2.isSelected())
		 {
			 String strFact=Vni2Uni.convertToVNI( ListBinder.get(sfFactory).toString());
			 if(!utils.getPermissionValidator().hasFactoryPermission(strFact))
			 {
				 Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Anh/ Chị không có quyền thao tác trong xưởng này");
					return false;
			 }
		 } 
*/		 
		 return true;
	} 
	private void loadGroupDept(ActionEvent e) {
		//TODO Implement.		
		SelectItem item = (SelectItem) sfFactory.getSelectedItem();		
		ListBinder.bindSelectField(sfGroup_dept, FVGenericInfo.getGroup(item.getValue()), true);
		ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue()), true);
	}
	
	

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
       
					        resourceBundle = ResourceBundle.getBundle(
									"resource.localization.UICaption", ApplicationInstance
									.getActive().getLocale());
					rootLayout2 = new Grid();
					rootLayout2.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(
							10, Extent.PX), new Extent(10, Extent.PX), new Extent(10,
							Extent.PX)));
					rootLayout2.setWidth(new Extent(100, Extent.PERCENT));
					rootLayout2.setSize(1);
					add(rootLayout2);
					Row row1 = new Row();
					rootLayout2.add(row1);
					radioButton1 = new RadioButton();
					radioButton1.setText(resourceBundle.getString("N_EMPLOYEE.EMPSN"));
					radioButton1.setSelected(true);
					ButtonGroup group_emp = new ButtonGroup();
					radioButton1.setGroup(group_emp);
					radioButton1.setFont(new Font(null, Font.BOLD | Font.UNDERLINE,
							new Extent(10, Extent.PT)));
					radioButton1.setActionCommand("cmd_emp");
					radioButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							empGroupSelected(e);
						}
					});
					row1.add(radioButton1);
					Row row2 = new Row();
					GridLayoutData row2LayoutData = new GridLayoutData();
					row2LayoutData.setRowSpan(2);
					row2.setLayoutData(row2LayoutData);
					rootLayout2.add(row2);
					lblEmpsn = new Label();
					lblEmpsn.setText(resourceBundle.getString("N_EMPLOYEE.EMPSN"));
					RowLayoutData lblEmpsnLayoutData = new RowLayoutData();
					lblEmpsnLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
							new Extent(0, Extent.PX), new Extent(10, Extent.PX),
							new Extent(0, Extent.PX)));
					lblEmpsn.setLayoutData(lblEmpsnLayoutData);
					row2.add(lblEmpsn);
					txtempsn = new DscField();
					txtempsn.setInsets(new Insets(new Extent(50, Extent.PX),
							new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
									0, Extent.PX)));
					txtempsn.setDisabledBackground(new Color(0xc0c0c0));
					txtempsn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
						//	doEmpsn(e);
						}
					});
					row2.add(txtempsn);
					Row row3 = new Row();
					rootLayout2.add(row3);
					radioButton2 = new RadioButton();
					radioButton2.setText(resourceBundle.getString("FACTORY"));
					radioButton2.setGroup(group_emp);
					radioButton2.setFont(new Font(null, Font.BOLD | Font.UNDERLINE,
							new Extent(10, Extent.PT)));
					radioButton2.setActionCommand("cmd_dept");
					radioButton2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							empGroupSelected(e);
						}
					});
					row3.add(radioButton2);
					Row row4 = new Row();
					rootLayout2.add(row4);
					Label label1 = new Label();
					label1.setText(resourceBundle.getString("N_DEPARTMENT.NAME_FACT"));
					RowLayoutData label1LayoutData = new RowLayoutData();
					label1LayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
							new Extent(0, Extent.PX), new Extent(10, Extent.PX),
							new Extent(0, Extent.PX)));
					label1.setLayoutData(label1LayoutData);
					row4.add(label1);
					sfFactory = new SelectField();
					sfFactory.setEnabled(false);
					sfFactory.setDisabledBackground(new Color(0xc0c0c0));
					RowLayoutData sfFactoryLayoutData = new RowLayoutData();
					sfFactoryLayoutData.setInsets(new Insets(new Extent(50, Extent.PX),
							new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
									0, Extent.PX)));
					sfFactoryLayoutData.setWidth(new Extent(200, Extent.PX));
					sfFactory.setLayoutData(sfFactoryLayoutData);
					sfFactory.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							loadGroupDept(e);
						}
					});
					row4.add(sfFactory);
					Row row5 = new Row();
					rootLayout2.add(row5);
					Label label2 = new Label();
					label2.setText(resourceBundle.getString("N_DEPARTMENT.NAME_GROUP"));
					RowLayoutData label2LayoutData = new RowLayoutData();
					label2LayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
							new Extent(0, Extent.PX), new Extent(65, Extent.PX),
							new Extent(0, Extent.PX)));
					label2.setLayoutData(label2LayoutData);
					row5.add(label2);
					sfGroup_dept = new SelectField();
					sfGroup_dept.setEnabled(false);					
					sfGroup_dept.setDisabledBackground(new Color(0xc0c0c0));
					RowLayoutData sfGroup_deptLayoutData = new RowLayoutData();
					sfGroup_dept.setInsets(new Insets(new Extent(50, Extent.PX), new Extent(0,
							Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
					sfGroup_deptLayoutData.setWidth(new Extent(200, Extent.PX));
					sfGroup_dept.setLayoutData(sfGroup_deptLayoutData);
					row5.add(sfGroup_dept);
					Row row6 = new Row();
					rootLayout2.add(row6);
					label3 = new Label();
					label3.setText(resourceBundle.getString("N_DEPARTMENT.NAME_DEPT_NAME"));
					RowLayoutData label3LayoutData = new RowLayoutData();
					label3LayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
							new Extent(0, Extent.PX), new Extent(65, Extent.PX),
							new Extent(0, Extent.PX)));
					label3.setLayoutData(label3LayoutData);
					row6.add(label3);
					sfDept = new SelectField();
					sfDept.setEnabled(false);
					sfDept.setInsets(new Insets(new Extent(50, Extent.PX), new Extent(0,
							Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
					sfDept.setDisabledBackground(new Color(0xc0c0c0));
					RowLayoutData sfDeptLayoutData = new RowLayoutData();
					sfDeptLayoutData.setWidth(new Extent(200, Extent.PX));
					sfDept.setLayoutData(sfDeptLayoutData);
					row6.add(sfDept);
					row7 = new Row();
					rootLayout2.add(row7);
					Label lblDate = new Label();
					lblDate.setText(resourceBundle.getString("N_EMP_RELATIVE.BEGINDATE"));
					RowLayoutData lblDateLayoutData = new RowLayoutData();
					lblDateLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
							new Extent(0, Extent.PX), new Extent(10, Extent.PX),
							new Extent(0, Extent.PX)));
					lblDate.setEnabled(true);
					row7.add(lblDate);
					dscDateField1 = new DscDateField();
					dscDateField1.setEnabled(false);
				
					RowLayoutData dscDateField1LayoutData = new RowLayoutData();
					dscDateField1LayoutData.setInsets(new Insets(new Extent(50, Extent.PX),
							new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
									0, Extent.PX)));
					dscDateField1.setLayoutData(dscDateField1LayoutData);
					dscDateField1.getTextField().setDisabledBackground(new  Color(0xc0c0c0));
					dscDateField1.getTextField().setText("");
					row7.add(dscDateField1);
					 row8=new Row();
					 rootLayout2.add(row8);
					 row8.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
					 btn_Thongke		= new Button();
					 btn_Thongke.setText("Xuất thống kê");
					 btn_Thongke.setWidth(new Extent(100));
					 btn_Thongke.setStyleName("Default.ToolbarButton");
					 btn_Thongke.setBackground(Color.DARKGRAY);
					 btn_Thongke.setForeground(Color.WHITE);
					 btn_Thongke.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
					 btn_Thongke.addActionListener(new ActionListener() {
							

							public void actionPerformed(ActionEvent e) {
							
								doExcel(1);
							}
						});					
						btn_Excel	= new Button();
						btn_Excel.setText("Xuất Exel");
						btn_Excel.setWidth(new Extent(100));
						btn_Excel.setStyleName("Default.ToolbarButton");
						btn_Excel.setBackground(Color.DARKGRAY);
						btn_Excel.setForeground(Color.WHITE);
						btn_Excel.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
						btn_Excel.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {					
								doExcel(2);								
							}							
						});	
						btn_Cancel	= new Button();
						btn_Cancel.setText("Cancel");
						btn_Cancel.setWidth(new Extent(100));
						btn_Cancel.setStyleName("Default.ToolbarButton");
						btn_Cancel.setBackground(Color.DARKGRAY);
						btn_Cancel.setForeground(Color.WHITE);
						btn_Cancel.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
						btn_Cancel.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {					
								doCancel();								
							}							
						});		
						row8.add(btn_Thongke);
						row8.add(btn_Excel);
						row8.add(btn_Cancel);
					 
					 
	              }
					

}
