package ds.program.fvhr.tien.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_REST_DETAIL;
import ds.program.fvhr.domain.N_SP_WDAY;
import ds.program.fvhr.domain.pk.N_REST_DETAILPk;
import ds.program.fvhr.domain.pk.N_SP_WDAYPk;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import echopointng.DateField;
import fv.components.SelectItem;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.Vni2Uni;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.text.TextComponent;

public class N_SP_WDay_New extends  WindowPane {
	 private nextapp.echo2.app.Grid rootLayout;
	    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
	    private nextapp.echo2.app.Label DATE_SP_CaptionLabel;
	    private DateField			DATE_SP_DscDateField1;
		private DscField 			tf_date;
	  
	    private nextapp.echo2.app.Label MULTIPLY_WD_CaptionLabel;
	    private nextapp.echo2.app.SelectField MULTIPLY_WD_SelectField1;
	    private nextapp.echo2.app.Label NOTE_CaptionLabel;
	    private dsc.echo2app.component.DscField NOTE_DscField2;
		private DscField EMPSN_DscField1;
		private Label lblHovaten_;
		private N_SP_WDAY dataSpWay;
		private RadioButton radEmpsn;
		private Label labTest;
		private Label lblTest;
		private SelectField sfDept;
		private Label label3;
		private RadioButton radioButton1;
		private Label lblEmpsn;
		private DscField txtempsn;
		private RadioButton radioButton2;
		private SelectField sfFactory;
		private SelectField sfGroup_dept;
		private ResourceBundle resourceBundle;
		private String user_up = Application.getApp().getLoginInfo().getUserID();
		private String ma_user;
		private Button btn_cancel;
		private Button  btn_ok;
		private InsuranceDAO ins = new InsuranceDAO();
		private RadioButton r1=new RadioButton();
		private RadioButton r2=new RadioButton();
		private RadioButton r3=new RadioButton();
		private RadioButton r4=new RadioButton();
		private RadioButton r5=new RadioButton();
		private RadioButton r6=new RadioButton();
		OBJ_UTILITY OUtil	= new OBJ_UTILITY();
		OBJ_EMPSN objEmpsn=new OBJ_EMPSN();
		SimpleDateFormat sp=new SimpleDateFormat("dd/MM/yyyy");
		Date dtNoew=new Date();
		public N_SP_WDay_New()
		{
			IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
			DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
			ma_user = Data_DSPB02.getPB_USERNO();
			initComponents();
			ListBinder.bindSelectField(sfFactory, FVGenericInfo.getFactories(), false);
			sfFactory.setEnabled(false);
			
		}
		private void loadGroupDept(ActionEvent e) {
			//TODO Implement.		
			SelectItem item = (SelectItem) sfFactory.getSelectedItem();		
			ListBinder.bindSelectField(sfGroup_dept, FVGenericInfo.getGroup(item.getValue()), true);
			ListBinder.bindSelectField(sfDept, FVGenericInfo.getDeptName(item.getValue()), true);
		}
		private void empGroupSelected(ActionEvent e) {
			//TODO Implement.
			if(e.getActionCommand().equals("cmd_emp"))
			{
				EMPSN_DscField1.setEnabled(true);
				sfFactory.setEnabled(false);
				sfGroup_dept.setEnabled(false);
				sfDept.setEnabled(false);
			}
			else
				
			{
				EMPSN_DscField1.setEnabled(false);
				sfFactory.setEnabled(true);
				sfGroup_dept.setEnabled(true);
				sfDept.setEnabled(true);
			}
		}
		private void doEmployee(ActionEvent e) {
			//TODO Implement.
			//N_SP_WDAY data = (N_SP_WDAY) getDataObject();
			IGenericDAO<N_EMPLOYEE, String> dao=Application.getApp().getDao(N_EMPLOYEE.class);
			N_EMPLOYEE emsp=dao.findById(EMPSN_DscField1.getText());
			if(emsp==null)
			{
				lblHovaten_.setText("Số thẻ không tồn tại");			
			}
			else
			{
				String dept=emsp.getDEPSN();
				IGenericDAO<N_DEPARTMENT,String> objDep_dao=Application.getApp().getDao(N_DEPARTMENT.class);
				N_DEPARTMENT obj_Department=objDep_dao.findById(dept);
				if(obj_Department!=null)
				{
					lblHovaten_.setText(Vni2Uni.convertToUnicode(emsp.getFULL_NAME()+"."+obj_Department.getNAME_FACT()+"."+obj_Department.getNAME_DEPT_NAME()));
				}
				
			}				
		}

	private void initComponents() {
		this.setTitle("Nhập Chi Tiết Ngày Làm Việc Đặc Biệt");
		//this.setInsets(new Insets(1));
		this.setWidth(new Extent(600));
		this.setHeight(new Extent(500));
		resourceBundle = ResourceBundle.getBundle(
				"resource.localization.UICaption", ApplicationInstance
						.getActive().getLocale());
		rootLayout = new Grid();
		rootLayout.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(
				10, Extent.PX), new Extent(10, Extent.PX), new Extent(10,
				Extent.PX)));
		rootLayout.setWidth(new Extent(1000, Extent.PERCENT));
		
		rootLayout.setSize(1);
		add(rootLayout);
		Row row1 = new Row();
		rootLayout.add(row1);
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
		rootLayout.add(row2);
		EMPSN_CaptionLabel = new Label();
		EMPSN_CaptionLabel.setText(resourceBundle.getString("N_SP_WDAY.EMPSN"));
		rootLayout.add(EMPSN_CaptionLabel);		
		
		EMPSN_DscField1 = new DscField();
		EMPSN_DscField1.setId("EMPSN_DscField1");
		EMPSN_DscField1.setDisabledBackground(new Color(0xc0c0c0));
		EMPSN_DscField1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doEmployee(e);
			}
		});
		RowLayoutData lblEmpsnLayoutData = new RowLayoutData();
		lblEmpsnLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX)));
		EMPSN_CaptionLabel.setLayoutData(lblEmpsnLayoutData);
		row2.add(EMPSN_CaptionLabel);
		lblHovaten_ = new Label();
		lblHovaten_.setVisible(true);
		
		row2.add(EMPSN_DscField1);
		row2.add(lblHovaten_);
		Row row3 = new Row();
		rootLayout.add(row3);
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
		rootLayout.add(row4);
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
		sfFactoryLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(50, Extent.PX), new Extent(
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
		rootLayout.add(row5);
		Label label2 = new Label();
		label2.setText(resourceBundle.getString("N_DEPARTMENT.NAME_GROUP"));
		RowLayoutData label2LayoutData = new RowLayoutData();
		label2LayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX)));
		label2.setLayoutData(label2LayoutData);
		row5.add(label2);
		sfGroup_dept = new SelectField();
		sfGroup_dept.setEnabled(false);		
		sfGroup_dept.setDisabledBackground(new Color(0xc0c0c0));
		RowLayoutData sfGroup_deptLayoutData = new RowLayoutData();
		sfGroup_deptLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		sfGroup_deptLayoutData.setWidth(new Extent(200, Extent.PX));
		sfGroup_dept.setLayoutData(sfGroup_deptLayoutData);
		row5.add(sfGroup_dept);
		Row row6 = new Row();
		rootLayout.add(row6);
		label3 = new Label();
		label3.setText(resourceBundle.getString("N_DEPARTMENT.NAME_DEPT_NAME"));
		RowLayoutData label3LayoutData = new RowLayoutData();
		label3LayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX)));
		label3.setLayoutData(label3LayoutData);
		row6.add(label3);
		sfDept = new SelectField();
		sfDept.setEnabled(false);		
		sfDept.setDisabledBackground(new Color(0xc0c0c0));
		RowLayoutData sfDeptLayoutData = new RowLayoutData();
		sfDeptLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		sfDeptLayoutData.setWidth(new Extent(200, Extent.PX));
		sfDept.setLayoutData(sfDeptLayoutData);
		row6.add(sfDept);
		Row row7=new Row();
		rootLayout.add(row7);		
		DATE_SP_CaptionLabel = new Label();
		RowLayoutData labelDATE_SPLayoutData = new RowLayoutData();
		labelDATE_SPLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX)));
		DATE_SP_CaptionLabel.setLayoutData(labelDATE_SPLayoutData);
		DATE_SP_CaptionLabel.setText(resourceBundle.getString("N_SP_WDAY.DATE_SP"));
		DATE_SP_DscDateField1 = new DateField();
		tf_date	= new DscField();
		tf_date.setText(sp.format(dtNoew));
		DATE_SP_DscDateField1.setId("DATE_SP_DscDateField1");
		DATE_SP_DscDateField1.setTextField(tf_date);
		DATE_SP_DscDateField1.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		row7.add(DATE_SP_CaptionLabel);		
		row7.add(DATE_SP_DscDateField1);
		Row row8=new Row();
		rootLayout.add(row8);
		MULTIPLY_WD_CaptionLabel = new Label();
		RowLayoutData labelMULTIPLY_WDLayoutData = new RowLayoutData();
		labelMULTIPLY_WDLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX)));
		MULTIPLY_WD_CaptionLabel.setLayoutData(labelMULTIPLY_WDLayoutData);
		MULTIPLY_WD_CaptionLabel.setText(resourceBundle.getString("N_SP_WDAY.MULTIPLY_WD"));
		MULTIPLY_WD_SelectField1 = new SelectField();
		MULTIPLY_WD_SelectField1.setId("MULTIPLY_WD_SelectField1");
		MULTIPLY_WD_SelectField1.setWidth(new Extent(200, Extent.PX));
		DefaultListModel listmodel=new DefaultListModel();
		listmodel.add(0,"");
		listmodel.add(1,"1. Ngày CN tính công như ngày bình thường");
		listmodel.add(2,"2. Ngày bình thường tính như ngày CN");
		listmodel.add(3,"3. Làm ngày lễ");
		MULTIPLY_WD_SelectField1.setModel(listmodel);
		MULTIPLY_WD_SelectField1.setSelectedIndex(1);
		row8.add(MULTIPLY_WD_CaptionLabel);		
		row8.add(MULTIPLY_WD_SelectField1);
		Row row9=new Row();
		rootLayout.add(row9);
		NOTE_CaptionLabel = new Label();
		RowLayoutData labelNOTELayoutData = new RowLayoutData();
		labelNOTELayoutData.setInsets(new Insets(new Extent(30, Extent.PX),
				new Extent(0, Extent.PX), new Extent(30, Extent.PX),
				new Extent(0, Extent.PX)));
		NOTE_CaptionLabel.setLayoutData(labelNOTELayoutData);
		NOTE_CaptionLabel.setText(resourceBundle.getString("N_SP_WDAY.NOTE"));
		NOTE_DscField2 = new DscField();
		NOTE_DscField2.setId("NOTE_DscField2");
		NOTE_DscField2.setWidth(new Extent(300,Extent.PX));
		row9.add(NOTE_CaptionLabel);		
		row9.add(NOTE_DscField2);
		Row btn_row = new Row();
		rootLayout.add(btn_row);
		btn_ok			= new Button();
		btn_ok.setText("OK");
		btn_ok.setWidth(new Extent(100));
		btn_ok.setStyleName("Default.ToolbarButton");
		btn_ok.setBackground(Color.DARKGRAY);
		btn_ok.setForeground(Color.WHITE);
	    btn_ok.setAlignment(new Alignment(Alignment.RIGHT, Alignment.RIGHT));
	
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
		btn_cancel.setAlignment(new Alignment(Alignment.RIGHT, Alignment.RIGHT));
		btn_cancel.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				
				doCancel();
				
			}
		});
		
		
		//btn_row.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btn_row.add(btn_ok);
		btn_row.add(btn_cancel);
	
		
		
	
		
		
		
	}
	protected void doCancel() {
		
		EMPSN_DscField1.setText("");
		sfFactory.setSelectedIndex(-1);
		sfGroup_dept.setSelectedIndex(-1);
		sfDept.setSelectedIndex(-1);
		MULTIPLY_WD_SelectField1.setSelectedIndex(1);
		
		// TODO Auto-generated method stub
		
	}
	protected void actionButtonOK() {
		// TODO Auto-generated method stub
		if(checkData())
		{ 
			OBJ_UTILITY.ShowMessageOK("Anh/Chị đã nhập thành công");
		}
		else
			OBJ_UTILITY.ShowMessageOK("Anh/Chị đã nhập không thành công");
			
		
		
		
	}
	private boolean checkData()
	
	{
		Date dateSway=null;
		dateSway=DATE_SP_DscDateField1.getSelectedDate().getTime();
		ArrayList<String> list_emp	= new ArrayList<String>();
		int intMULTIPLY_WD=0;
		String Strnote="";
		intMULTIPLY_WD=MULTIPLY_WD_SelectField1.getSelectedIndex();
		Strnote=NOTE_DscField2.getText();
		if(radioButton1.isSelected())
			{
				String strEmpsn=EMPSN_DscField1.getText();
				
				System.out.println(dateSway);			
				String strDate=sp.format(dateSway);
				
				
					String thang = strDate.substring(3, 5);
					String nam = strDate.substring(6, 10);	
					
					if (ins.checkQLyEmpsn(strEmpsn)==false)
					{
						Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Anh/Chị Không có quyền thao tác.");
						return false;
					}		
					else if (ins.CheckKhoaDataMonth(strEmpsn, thang, nam)==false)
					{	
						Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Dữ liệu NV đã bị khóa trong tháng .");	
						return false;
					}
					else
					{
						if(!strEmpsn.equals(""))
						{
							list_emp=OUtil.getListEmpsn(strEmpsn,"","","","",ma_user,"DEPSN");
						}
					}
				
			}
		if(radioButton2.isSelected())
		{

			String strItemFact="";
			String strItemGroupDept="";
			String strItemDeptName="";
			
			if(sfFactory.getSelectedIndex()>0)
			{
				 strItemFact=sfFactory.getSelectedItem().toString();
			}
			if(sfGroup_dept.getSelectedIndex()>0)
			{
				 strItemGroupDept=sfGroup_dept.getSelectedItem().toString();	
			}
			String strItemDeptNameconver="";
			if(sfDept.getSelectedIndex()>0)
			{
				strItemDeptName=sfDept.getSelectedItem().toString();		
				strItemDeptNameconver=fv.util.Vni2Uni.convertToVNI(strItemDeptName);
			}	
			String strIdDept="";
			strIdDept=FVGenericInfo.findDept(strItemDeptNameconver, strItemFact);
			
		
			if(!strIdDept.equals(""))
			{
				// kiểm tra quyền quản lý
				boolean checkDept=ins.checkQLyNDept(strItemFact, strItemGroupDept, strItemDeptName);
				if(!checkDept)		
				{	
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Anh/ Chị không có quyền thao tác đơn vị này");
					 return false;
				}
				// kiểm tra khóa dữ liệu trong tháng
				
				if(!objEmpsn.check_lock_month("", strItemFact, strItemGroupDept, strItemDeptName, strIdDept, dateSway, "DEPSN",ma_user, r1, r2, r3, r4, r5, r6))			
				{
					return false;					
				}
				else
					list_emp = OUtil.getListEmpsn("",strItemFact,strItemGroupDept,strItemDeptName,strIdDept,ma_user,"DEPSN");
				
				
			}			
		}
		if(list_emp.size()>0)
		{
		  for(String empsn:list_emp)
		  {
			  SaveSpway(empsn, dateSway, intMULTIPLY_WD, Strnote);
		  }
			
		}		
		
		return true;
	
	
	}
	private void SaveSpway(String empsn, Date DATE_SP,int MULTIPLY_WD,String NOTE )
	
	{
		
		Date dtNow=new Date();
		String str_DateNow=sp.format(dtNow);
		String strDate_sp=sp.format(DATE_SP);
		IGenericDAO<N_SP_WDAY, N_SP_WDAYPk> Dao_Rest = Application.getApp().getDao(N_SP_WDAY.class);
		 N_SP_WDAYPk pk = new N_SP_WDAYPk(empsn, DATE_SP);
		 N_SP_WDAY Data_Rest = Dao_Rest.findById(pk);
		 String strnote="THEM MOI NGAY LAM VIEC DAC BIET";
		 if(Data_Rest==null)
		 {
			 
			 String strSQL="INSERT INTO N_SP_WDAY VALUES('"+empsn+"',to_date('"+strDate_sp+"','dd/MM/yyyy'),"+MULTIPLY_WD+",'"+NOTE+"','"+user_up+"',to_date('"+str_DateNow+"','dd/MM/yyyy'))";
			 OBJ_UTILITY obj_util = new OBJ_UTILITY();
		     obj_util.Exe_Update_Sql(strSQL);
		     objEmpsn.Insert_N_Action_Daily(ma_user, "N_REST_DETAIL", "INSERT", empsn, strnote);
		 }
		
		
	}

}
