package ds.program.fvhr.son.ui.insurance.social;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

//import com.sun.jmx.mbeanserver.OpenConverter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
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
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_SOCIAL;
import ds.program.fvhr.son.ui.DeptUserControl;
import ds.program.fvhr.ui.insurance.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.DataContent;
import echopointng.GroupBox;
import fv.util.DateUtils;
import fv.util.ReportUtils;
import javax.persistence.TemporalType;

public class printSocial  extends WindowPane{



	private Grid rootLayout;
	private GroupBox group1;
	private GroupBox group2;
	protected DeptUserControl myDeptControl ;
	private Label lbl_Cbthu;
	private DscField txt_Cbthu;
	private Label lbl_hCbthu;
	private DscField txt_hCbthu;
	private Label lbl_CBso;
	private DscField txt_CbSo;
	private Label lbl_hCBso;
	private DscField txt_hCbSo;
	private Button btn_ok;
	private Button btn_cancel;
	
	public printSocial()
	{
		this.setTitle("Chọn Thông Tin");// set tilte
		this.setClosable(true);
		this.setStyleName("Default.Window");
		this.setWidth(new Extent(600));// set chieu dai
		this.setHeight(new Extent(500));// set chieu rộng
		initComponents();
	}
	protected void doCancel()
	{
		myDeptControl.refresh();
		this.txt_CbSo.setText("");
		this.txt_hCbSo.setText("");
		this.txt_Cbthu.setText("");
		this.txt_hCbthu.setText("");
	}
	protected void actionButtonOK() {
	   boolean check=checkData();
	   if(check)
	   {	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Map<String,  Object> params = new HashMap<String, Object>();				
				Date timechooce = myDeptControl.getDate();	
				Calendar cal=Calendar.getInstance();	
				cal.setTimeInMillis(timechooce.getTime());
				
				Calendar dateM=Calendar.getInstance();
				dateM.setTimeInMillis(cal.getTimeInMillis());
				dateM.set(Calendar.DAY_OF_MONTH, 1);		
				Date dateM_=dateM.getTime();		
				//String MIN_DATE="to_date('"+sdf.format(dateM_)+"','"+"dd/mm/yyyy"+"')";
				String MIN_DATE=sdf.format(dateM_);
				try {
					dateM_= sdf.parse(MIN_DATE);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String Month=MIN_DATE.substring(3, 10);
				
				Calendar dateMax = Calendar.getInstance();
				dateMax.setTimeInMillis(cal.getTimeInMillis());
				dateMax.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				Date dateMax_=dateMax.getTime();
				
				//dateMax_.get
				//java.sql.Date dateMax_ = dateMax.get
				String M_DATE=sdf.format(dateMax_);
				try {
					dateMax_ = sdf.parse(M_DATE);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int mm = cal.get(Calendar.MONTH);
				int yy = cal.get(Calendar.YEAR);
				if (mm==0) {
					mm = 12;
					yy--;
				}				
				Date Date_S=DateUtils.getDay15(mm, yy);	
				String S_DATE=sdf.format(Date_S);
				try {
					Date_S = sdf.parse(S_DATE);
				} catch (ParseException e) {					
					e.printStackTrace();
				}
				String name_Fact="";
				String name_Group="";
				String name_Depsn_name="";
				String fname_CBT="";
				String lname_CBT="";
				String fname_CBS="";
				String lname_CBS="";
				if(!myDeptControl.getFact().trim().equals("")){
					name_Fact=myDeptControl.getFact();					
				}
				if(!myDeptControl.getGroup().trim().equals("")){
					name_Group=myDeptControl.getGroup();
				}
				 if(!myDeptControl.getNameDept().equals("")){
					 name_Depsn_name=myDeptControl.getNameDept();
				 }
				 if(!txt_Cbthu.getText().equals(""))
				 {
					 fname_CBT=txt_Cbthu.getText();
				 }
				 if(!txt_hCbthu.getText().equals(""))
				 {
					 lname_CBT=txt_hCbthu.getText();
				 }
				 if(!txt_CbSo.getText().equals(""))
				 {
					 fname_CBS=txt_CbSo.getText();
				 }
				 if(!txt_hCbSo.getText().equals(""))
				 {
					 lname_CBS=txt_hCbSo.getText();
				 }
		         String SQL="SELECT * from (SELECT  E.EMPSN ," + 
		         		"( E.FNAME ||'  '||E.LNAME) FULLNAME,(D.NAME_FACT||''||D.NAME_GROUP) NAMEFACT,E.BIRTHDAY,E.SEX,E.POSITION," +  
		         		"E.BIRTHPLACE, E.ETHNIC, E.PERMANENT_ADDRESS,Bsaly_By_Date(E.EMPSN,to_date('"+M_DATE+"','dd/MM/yyyy')) SALARY ," +
		         		" (SELECT (GIATRI3+GIATRI4)*100 from N_THAMSO  WHERE TENTHAMSO='TYLEBHXH') BHXH," +
		         		"  (SELECT (GIATRI3+GIATRI4)*100 from N_THAMSO  WHERE TENTHAMSO='TYLEBHTN') BHTN," +
		         		" B.ID_LABOUR,B.DATE_S,D.NAME_FACT,D.NAME_GROUP,D.NAME_DEPT_NAME, E.DATE_HIRED," +
		         		" (select  s.name_hospital from n_health h, n_hospital  s where E.empsn=h.empsn and  h.id_hos=s.id_hos and h.id_pro=s.id_province) NAME_HOSPITAL,"+
		         		" E.ID_NO, E.NGAYCAP_ID, E.ID_PLACE,  E.DATE_LAMLAI,(SELECT ID_SOCIAL from N_SOCIAL S" +		         		
		         		" WHERE E.EMPSN=S.EMPSN AND S.CLOCK=1) ID_SOCIAL from N_EMPLOYEE E," +
		         		" N_LABOUR B,N_DEPARTMENT D WHERE E.DEPSN=D.ID_DEPT  AND E.EMPSN=B.EMPSN  AND B.CLOCK='1'" +
		         		" AND ( ((B.DATE_S= to_date('"+MIN_DATE+"','dd/MM/yyyy') or B.DATE_S=to_date('"+S_DATE+"','dd/MM/yyyy'))  AND B.TIMES=1)" +
		         		" OR (E.NGAYNX_MOI=to_date('"+MIN_DATE+"','dd/MM/yyyy')" +
		         		" AND EXISTS ( SELECT C.* from N_NEWWORKER_TEST C WHERE C.EMPSN=E.EMPSN AND C.DD_KHU=1)))" +
		         		" AND E.DEPSN<>'00000'   AND E.EMPSN NOT IN  (SELECT Q.EMPSN from N_EMP_QUIT Q" +
		         		" WHERE Q.from_DATE IS NULL AND Q.TO_DATE   IS NULL AND (Q.NOTE_GIAM_BHYT<>'GIAM TANG MOI' OR Q.NOTE_GIAM_BHYT IS NULL)" +
		         		" AND Q.REAL_OFF_DATE<=to_date('"+M_DATE+"','dd/MM/yyyy')) AND E.EMPSN NOT IN (SELECT T.EMPSN from N_SOCIAL T" +
		         		" WHERE T.EMPSN=E.EMPSN AND T.CLOCK='1'  AND T.SOCIAL_OLD='Y')  AND E.EMPSN NOT IN" +
		         		"(SELECT N.EMPSN  from N_NOT_INSURANCE N  WHERE N.EMPSN=E.EMPSN  AND N.DATES<=to_date('"+MIN_DATE+"','dd/MM/yyyy')) " +
		         				"AND E.EMPSN IN (SELECT T.EMPSN from N_SOCIAL T WHERE T.EMPSN=E.EMPSN)";
		     	
		         if(!name_Fact.equals(""))
				 {   SQL= SQL+"AND D.NAME_FACT="+"'"+fv.util.Vni2Uni.convertToVNI(name_Fact)+"'";
				
				 }
		         if(!name_Group.equals(""))
				 {   SQL= SQL+"AND D.NAME_GROUP="+"'"+fv.util.Vni2Uni.convertToVNI(name_Group)+"'";
				
				 }
				 if(!name_Depsn_name.equals(""))
				 {   SQL= SQL+"AND D.NAME_DEPT_NAME="+"'"+fv.util.Vni2Uni.convertToVNI(name_Depsn_name)+"'";			
				 }	
				 SQL= SQL+"  order by e.empsn desc )";
		        Connection ds=null;
		          ds = Application.getApp().getConnection();
		          List<SocialData> list1 = new ArrayList<SocialData>();  
		         Statement stmt = null;
		         try {
		        	 stmt = ds.createStatement();        	
		             ResultSet rs = stmt.executeQuery(SQL);              
		                        while (rs.next())
		             {
		                 SocialData scData=new SocialData();
		                 scData.setEMPSN(rs.getString("EMPSN")); 
		                 scData.setNAME_FACT(rs.getString("NAME_FACT")); 
		                 scData.setNAME_GROUP(rs.getString("NAME_GROUP"));                 
		                 scData.setFULLNAME(rs.getString("FULLNAME"));
		                 scData.setBIRTHDAY(rs.getString("BIRTHDAY"));
		                 scData.setSEX(rs.getString("SEX"));
		                 scData.setPOSITION(rs.getString("POSITION"));                 
		                 scData.setNAMEFACT(rs.getString("NAMEFACT")); 
		                 scData.setBIRTHPLACE(rs.getString("BIRTHPLACE"));
		                 scData.setETHNIC(rs.getString("ETHNIC"));               
		                 scData.setPERMANENT_ADDRESS(rs.getString("PERMANENT_ADDRESS"));
		                 scData.setSALARY(rs.getBigDecimal("SALARY"));               
		                 scData.setBHXH(rs.getBigDecimal("BHXH"));
		                 scData.setBHTN(rs.getBigDecimal("BHTN"));
		                 scData.setID_LABOUR(rs.getString("ID_LABOUR"));                 
		                 scData.setNAME_DEPT_NAME(rs.getString("NAME_DEPT_NAME"));		                              
		                 scData.setID_NO(rs.getString("ID_NO"));  
		                 scData.setNGAYCAP_ID(rs.getTimestamp("NGAYCAP_ID"));
		                 scData.setID_PLACE(rs.getString("ID_PLACE"));		               
		                 scData.setID_SOCIAL(rs.getString("ID_SOCIAL"));
		                 scData.setNAME_HOSPITAL(rs.getString("NAME_HOSPITAL"));
		                 list1.add(scData);		            
		             }  
		         }catch (Exception e) {
					// TODO: handle exception
		        	 System.out.print(e.toString());
				}
				 params.put("FName_CBT",fname_CBT);
				 params.put("LName_CBT",lname_CBT);
				 params.put("FName_CBS",fname_CBS);
				 params.put("LName_CBS",lname_CBS);
				 params.put("Month", Month); 
				 JRBeanCollectionDataSource dss = new JRBeanCollectionDataSource(list1);
					try {
						JasperDesign jd = JRXmlLoader.load(ReportFileManager
								.getReportFormatFolder("fvhr/Tokhai.jrxml"));
						JasperReport jr = JasperCompileManager.compileReport(jd);			
						JasperPrint jp = JasperFillManager.fillReport(jr, params, dss);
						ReportUtils.doExportPdf(jp, "Tokhai");
					} catch (JRException e) {
						e.printStackTrace();
					}
					}
	}
	
	private  boolean  checkData()
	{
	  // Date dateDeptcontrol=myDeptControl.getDate();
	   
		if(myDeptControl.getFact().equals(""))
		{
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Chọn Xưởng cần thao tác");
			return  false;
		}
		SimpleDateFormat sf=new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			sf.format(myDeptControl.getDate());
		}catch (Exception e) {
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Ngày tháng không đúng định dạng");
			return  false;
		}
		if(txt_Cbthu.getText().equals(""))
		{
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Tên cán bộ thu không được để trống");
			return  false;
		}
		if(txt_hCbthu.getText().equals(""))
		{
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Họ cán bộ thu không được để trống");
			return false;
		}
		if(txt_CbSo.getText().equals(""))
		{
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Tên cán bộ sổ không được để trống");
			return false;		
		}
		if(txt_hCbSo.getText().equals(""))
		{
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Họ cán bộ sổ không được để trống");
			return false;
		}
		return true;
			
	}
	private void initComponents()
	{
		rootLayout = new Grid();
		rootLayout.setWidth(new Extent(100, Extent.PERCENT));
		rootLayout.setSize(1);
		myDeptControl 	= new DeptUserControl(false,true);	
		Grid grid1 = new Grid();
		grid1.setWidth(new Extent(100,Extent.PERCENT));
		grid1.setSize(2);
		grid1.setInsets(new Insets(0, 5, 10, 10));
		Grid grid2 = new Grid();
		grid2.setSize(2);
		grid2.setInsets(new Insets(0, 5, 10, 10));
		grid2.setWidth(new Extent(100,Extent.PERCENT));
		this.add(rootLayout);
		group1 = new GroupBox();

		group1.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(10, Extent.PX), new Extent(10, Extent.PX),
				new Extent(10, Extent.PX)));
		group1.setTitle("Lựa chọn theo đơn vị");
		group1.setWidth(new Extent(50, Extent.PERCENT));
		group1.add(myDeptControl);
		group2 = new GroupBox();
		group2.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(10, Extent.PX), new Extent(10, Extent.PX),
				new Extent(10, Extent.PX)));
		group2.setTitle("Thông tin cán bộ bảo hiểm");
		group2.setWidth(new Extent(50, Extent.PERCENT));
		rootLayout.add(group1);
		rootLayout.add(group2);
		
		
		lbl_Cbthu = new Label();
		lbl_Cbthu.setText("Tên CB thu");
		txt_Cbthu = new DscField();
		txt_Cbthu.setId("txt_Cbthu");		
		lbl_hCbthu=new Label();
		lbl_hCbthu.setText("Họ tên CB thu");
		txt_hCbthu=new DscField();
		txt_hCbthu.setId("txt_hCbthu");
		
		lbl_CBso = new Label();
		lbl_CBso.setText("Tên CB sổ");
		txt_CbSo = new DscField();
		txt_CbSo.setId("txt_CbSo");		
		lbl_hCBso=new Label();
		lbl_hCBso.setText("Họ tên CB Sổ");
		txt_hCbSo=new DscField();
		txt_hCbSo.setId("txt_hCbSo");
		grid2.add(lbl_Cbthu);
		grid2.add(txt_Cbthu);
		grid2.add(lbl_hCbthu);
		grid2.add(txt_hCbthu);
		grid2.add(lbl_CBso);		
		grid2.add(txt_CbSo);
		grid2.add(lbl_hCBso);
		grid2.add(txt_hCbSo);		
		group2.add(grid2);
		btn_ok			= new Button();
		btn_ok.setText("OK");
		btn_ok.setWidth(new Extent(100));
		btn_ok.setStyleName("Default.ToolbarButton");
		btn_ok.setBackground(Color.DARKGRAY);
		btn_ok.setForeground(Color.WHITE);
		btn_ok.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btn_ok.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				actionButtonOK();
			}
		});
		
		btn_cancel	= new Button();
		btn_cancel.setText("Cancel");
		btn_cancel.setWidth(new Extent(100));
		btn_cancel.setStyleName("Default.ToolbarButton");
		btn_cancel.setBackground(Color.DARKGRAY);
		btn_cancel.setForeground(Color.WHITE);
		btn_cancel.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btn_cancel.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				
				doCancel();
				
			}
		});
		
		Row btn_row = new Row();
		btn_row.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btn_row.add(btn_ok);
		btn_row.add(btn_cancel);
		rootLayout.add(btn_row);

		
	}
}
