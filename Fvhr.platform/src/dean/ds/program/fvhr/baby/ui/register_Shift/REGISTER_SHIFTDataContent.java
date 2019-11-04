package ds.program.fvhr.baby.ui.register_Shift;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Font.Typeface;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import ds.program.fvhr.baby.tools.CheckRule;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_EALRY_AFTER_B;
import ds.program.fvhr.domain.N_EALRY_BEFOR_B;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_LIST_DEPENDENT;
import ds.program.fvhr.domain.N_REGISTER_SHIFT;
import ds.program.fvhr.domain.N_SHIFT;
import ds.program.fvhr.domain.N_SPDEPT_LIST;
import ds.program.fvhr.domain.pk.N_REGISTER_SHIFTPk;
import ds.program.fvhr.ngan.ui.reg_overtime.N_REGISTER_OVERTIMEMProgram;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import echopointng.DateField;
import echopointng.GroupBox;
import echopointng.RadioButtonEx;
import echopointng.SelectFieldEx;
import echopointng.TextAreaEx;
import fv.util.FvLogger;
import fv.util.Vni2Uni;

public class REGISTER_SHIFTDataContent extends DataContent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Grid rootLayout;
	private Label lbl_EMPSN;
	private DscField txt_EMPSN;
	private Label lbl_SHIFT_DATE;
	private DateField df_SHIFT_DATE;
	private Label lbl_TIME_IN;
	private DscField txt_TIME_IN;
	private Label lbl_TIME_OUT;
	private DscField txt_TIME_OUT;
	private Label lbl_ID_SHIFT;
	private SelectFieldEx sf_ID_SHIFT;
	private Label lbl_ID_SPDEPT;
	private SelectFieldEx sf_ID_SPDEPT;
	private Label lbl_NOTE;
	private DscField txt_NOTE;
	private Label lbl_OT_7H;
	private DscField txt_OT_7H;
	private GroupBox group1;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private MappingPropertyEditor map ;
	private DateField df_ToDATE;
	private RadioButtonEx rad1,rad2, rad3;
	public static boolean SaveMultiObject = false;	
	public static boolean SaveMultiDay = false;
	public static Calendar dateTo ;
	private MappingPropertyEditor mappingproperty;
	private Label lblfullName;
	private Label lbltext;
	private CheckBox cbox;
	private CheckBox chboxupdate_hs;
	private REGISTER_SHIFTProgram shiftProgram=null;
	IGenericDAO<N_EALRY_AFTER_B, String> daoN_EALRY_AFTER_B=Application.getApp().getDao(N_EALRY_AFTER_B.class);
	IGenericDAO<N_EALRY_BEFOR_B, String> daoN_EALRY_BEFOR_B=Application.getApp().getDao(N_EALRY_BEFOR_B.class);
	private Label lblspdept_list;
	public static EmployeeBrowserContent browserContentEmployee;
	public REGISTER_SHIFTDataContent() {
		super();
		initComponents();
		shiftProgram=new REGISTER_SHIFTProgram();		
	}

	@Override
	public void saveUIToDataObject() {
		if(df_ToDATE.isEnabled())
			dateTo = df_ToDATE.getSelectedDate();
		else
			dateTo = df_SHIFT_DATE.getSelectedDate();
		super.saveUIToDataObject();
	}
	@Override
	public boolean saveToDataObjectSet() {
		
		boolean ret =  false;
		ret = super.saveToDataObjectSet();
		return ret;
	}
	
	
	@Override
	protected void doUIRefresh() {
		// TODO Auto-generated method stub
		super.doUIRefresh();
		if ((this.getProgram().getDataMode() == IProgram.DATAMODE_NONE)
				|| (this.getProgram().getDataMode() == IProgram.DATAMODE_BROWSER)) {
			rootLayout.setEnabled(false);
		} else {
			rootLayout.setEnabled(true);
			if (this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				txt_EMPSN.setEnabled(true);
				txt_EMPSN.requestFocus();
				df_SHIFT_DATE.setEnabled(true);
				lblfullName.setText("");
				lblspdept_list.setVisible(true);
				
				
			}
			
			else {
				txt_EMPSN.setEnabled(false);
				df_SHIFT_DATE.setEnabled(false);
			}
		}
		getUIDataObjectBinder().doDataBindUIStyle();
	}
	

	public Class getDataObjectClass() {
		return N_REGISTER_SHIFT.class;
	}




	@Override
	protected int doInit() {
		// TODO Auto-generated method stub
		int nRet = super.doInit();
		registPropertyEditor();
		bindUI();
		return nRet;
	}

	private void registPropertyEditor() {
		getUIDataObjectBinder().registerCustomEditor(N_REGISTER_SHIFT.class,
				"ID_SHIFT", bindID_SHIFT());
		getUIDataObjectBinder().registerCustomEditor(N_REGISTER_SHIFT.class,
				"ID_SPDEPT", bindID_SPDEPT());
		getUIDataObjectBinder().registerCustomEditor(N_REGISTER_SHIFT.class,
				"SHIFT_DATE", PropertyEditors.createDateEditor("dd/MM/yyyy"));
		mappingproperty = bindSPDEPT();
	}
	
	private  MappingPropertyEditor bindSPDEPT()
	{
		MappingPropertyEditor map = new MappingPropertyEditor();
		IGenericDAO<N_DEPARTMENT, String>daoDept  =Application.getApp().getDao(N_DEPARTMENT.class);
		List<N_DEPARTMENT> list = daoDept.findAll(1500);
		
		for (N_DEPARTMENT obj : list) {
			map.put(obj.getID_DEPT(),obj);
		}
		return map;
	}
	
	private MappingPropertyEditor bindID_SHIFT() {
		MappingPropertyEditor map = new MappingPropertyEditor();
		IGenericDAO<N_SHIFT, String> daoShift = Application.getApp().getDao(
				N_SHIFT.class);
		List<N_SHIFT> listShift = daoShift.findAll(50);
		map.put("-----");
		for (N_SHIFT obj : listShift) {		
			map.put(obj.getID_SHIFT());
			
		}
		return map;
	}
	
	
	private MappingPropertyEditor bindTIME_SHIFT() {
		MappingPropertyEditor map = new MappingPropertyEditor();
		IGenericDAO<N_SHIFT, String> daoShift = Application.getApp().getDao(
				N_SHIFT.class);
		List<N_SHIFT> listShift = daoShift.findAll(50);
		for (N_SHIFT obj : listShift) {
			map.put(obj.getID_SHIFT(),obj);
			
		}		
		return map;
	}
	
	private MappingPropertyEditor bindID_SPDEPT() {
		MappingPropertyEditor map = new MappingPropertyEditor();
		IGenericDAO<N_SPDEPT_LIST, String> daoSPDept = Application.getApp()
				.getDao(N_SPDEPT_LIST.class);
		List<N_SPDEPT_LIST> listSPDept = daoSPDept.findAll(10);		
		for (N_SPDEPT_LIST obj : listSPDept) {
			map.put(obj.getID_SPDEPT());
		}
		return map;
	}

	private void bindUI() {
		getUIDataObjectBinder().addBindMap("EMPSN", txt_EMPSN, lbl_EMPSN);
		getUIDataObjectBinder().addBindMap("SHIFT_DATE", df_SHIFT_DATE,
				lbl_SHIFT_DATE);
		getUIDataObjectBinder().addBindMap("TIME_IN", txt_TIME_IN, lbl_TIME_IN);
		getUIDataObjectBinder().addBindMap("TIME_OUT", txt_TIME_OUT,
				lbl_TIME_OUT);
		getUIDataObjectBinder().addBindMap("ID_SHIFT", sf_ID_SHIFT,
				lbl_ID_SHIFT);
		getUIDataObjectBinder().addBindMap("ID_SPDEPT", sf_ID_SPDEPT,
				lbl_ID_SPDEPT);
		getUIDataObjectBinder().addBindMap("NOTE", txt_NOTE, lbl_NOTE);
		getUIDataObjectBinder().addBindMap("OT_7H", txt_OT_7H, lbl_OT_7H);
	}

	@Override
	protected void doLayout() {
		// TODO Auto-generated method stub
		super.doLayout();
	}
	
	@Override
	public boolean delete(int recNo) {
		try {
			if(!checkDataObject())
				return false;
			if (!dataObjectSet.moveTo(recNo)) {
				setErrorMessage(dataObjectSet.getErrorMessage());
				return false;
			}
			if (!dataObjectSet.delete()) {
				setErrorMessage(dataObjectSet.getErrorMessage());
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("Delete record error", e);
			setErrorMessage( getProgram().getCommMsgRes().getString("DataContent.MSG.deleteError"));
			return false;
		}
	}

	@Override
	protected void doNewDefaulData() {
		
		super.doNewDefaulData();	
		if( REGISTER_SHIFTProgram.browserContentEmployee.getDataObjectSet().getRecordCount()>0)
		{
			int recNo = REGISTER_SHIFTProgram.browserContentEmployee.getBrowserContent().getCurrentSelectRowNo();
			N_EMPLOYEE data = (N_EMPLOYEE) REGISTER_SHIFTProgram.browserContentEmployee
			.getDataObjectSet().getDataObject(recNo);
			mappingproperty.setAsText(data.getDEPSN());
			//map.setAsText(sf_ID_SHIFT.getSelectedItem().toString());
			map.setAsText(data.getSHIFT());
			if(data!= null){
				((N_REGISTER_SHIFT)getDataObject()).setEMPSN(data.getEMPSN());
				((N_REGISTER_SHIFT)getDataObject()).setID_SPDEPT(((N_DEPARTMENT)mappingproperty.getValue()).getID_SPDEPT());
				((N_REGISTER_SHIFT)getDataObject()).setID_SHIFT(data.getSHIFT());
				((N_REGISTER_SHIFT)getDataObject()).setTIME_IN(((N_SHIFT)map.getValue()).getTIME_IN());
				((N_REGISTER_SHIFT)getDataObject()).setTIME_OUT(((N_SHIFT)map.getValue()).getTIME_OUT());
				((N_REGISTER_SHIFT)getDataObject()).setNOTE(((N_SHIFT)map.getValue()).getNOTE());
				doDisstrAOTFOT(((N_DEPARTMENT)mappingproperty.getValue()).getID_SPDEPT());
				System.out.println(((N_DEPARTMENT)mappingproperty.getValue()).getID_SPDEPT());
			}
		}
			
	}
	

	@Override
	public boolean checkDataObject() {
		// TODO Auto-generated method stub
		boolean ret = super.checkDataObject();
		N_REGISTER_SHIFT obj = (N_REGISTER_SHIFT) getDataObject();
		if (ret) {
			
			if(!CheckRule.checkRuleManager(obj.getEMPSN())){
				setErrorMessage("Bạn không được quyền thao tác trên số thẻ này!");				
				return false;				
			}
			// khi xoa chi xoa tung dong nen ko can kiem tra den ngay
			if ((this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) || (this.getProgram().getDataMode() == IProgram.DATAMODE_EDIT) )
			{
				if(obj.getSHIFT_DATE().after(dateTo.getTime()))
				{
					setErrorMessage("Ngày bắt đầu phải nhỏ hơn ngày kết thúc !");				
					return false;
				}	
				
			}

			if(!CheckRule.checkLockedMonth(obj.getEMPSN(),obj.getSHIFT_DATE())){
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
						"Đã khoá dữ liệu tháng !");
				return false;
				/*setErrorMessage("");				
				return false;*/
			}
			// kiem tra ca lv 7 h, chi canh bao la dang ve som, van cho luu binh thuong			
			 String dateShift=sdf.format(obj.getSHIFT_DATE());
			
			 REGISTER_SHIFTProgram shift = new REGISTER_SHIFTProgram();
			 
			 List<N_EALRY_AFTER_B> listN_EALRY_AFTER_B=daoN_EALRY_AFTER_B.find(100,"from N_EALRY_AFTER_B where EMPSN ='"+obj.getEMPSN()+"' AND  B_DATES<=TO_DATE('"+dateShift+"','DD/MM/YYYY')" +
							"AND E_DATES>=TO_DATE('"+dateShift+"','DD/MM/YYYY')");
			  if(listN_EALRY_AFTER_B.size()>0)
				   {
						setErrorMessage(" Số thẻ '"+obj.getEMPSN()+"' đang đăng ký ca về sớm 7h!");
						shift.check_vs= true;
						return true;
					}
				
			 List<N_EALRY_BEFOR_B> listN_EALRY_BEFOR_B=daoN_EALRY_BEFOR_B.find(100,"from N_EALRY_BEFOR_B where EMPSN ='"+obj.getEMPSN()+"' AND  B_DATES<=TO_DATE('"+dateShift+"','DD/MM/YYYY')" +
							"AND E_DATES>=TO_DATE('"+dateShift+"','DD/MM/YYYY')");
					if(listN_EALRY_BEFOR_B.size()>0)
					{
						setErrorMessage(" Số thẻ '"+obj.getEMPSN()+"' đang đăng ký ca về sớm 7h!");
						shift.check_vs= true;
						return true;
					}	
		}				
		return ret;
	}
	
	
	public void GetStyleSaveObject(ActionEvent e) {
		if(e.getActionCommand().equals("SingleObject"))
			SaveMultiObject = false;
		if(e.getActionCommand().equals("MultiObject"))
			SaveMultiObject = true;
	}
	private void doEmployee(ActionEvent e) {
		//TODO Implement.
	
		IGenericDAO<N_EMPLOYEE, String> dao=Application.getApp().getDao(N_EMPLOYEE.class);
		N_EMPLOYEE emsp=dao.findById(txt_EMPSN.getText());
		if(emsp==null)
		{
			lblfullName.setText("Số thẻ không tồn tại");			
		}
		else
		{
			String dept=emsp.getDEPSN();
			IGenericDAO<N_DEPARTMENT,String> objDep_dao=Application.getApp().getDao(N_DEPARTMENT.class);
			N_DEPARTMENT obj_Department=objDep_dao.findById(dept);
			if(obj_Department!=null)
			{
				lblfullName.setText(Vni2Uni.convertToUnicode(emsp.getFULL_NAME()+"."+obj_Department.getNAME_FACT()+"."+obj_Department.getNAME_DEPT_NAME()+"."+emsp.getSHIFT()));
			}
			
		}				
	}
	
	/**
	 * Phương thức khởi tạo này viết bằng tay 
	 * nên có thể tự do chỉnh sửa tuỳ ý
	 */
	private void initComponents() {
		rootLayout = new Grid();
		rootLayout.setWidth(new Extent(70, Extent.PERCENT));
		rootLayout.setSize(2);
		rootLayout.setInsets(new Insets(0));
		
		Grid grid1 = new Grid();
		grid1.setWidth(new Extent(100,Extent.PERCENT));
		grid1.setSize(2);
		grid1.setInsets(new Insets(0, 5, 5, 5));
		browserContentEmployee = new EmployeeBrowserContent();
		this.add(rootLayout);
		group1 = new GroupBox();
		group1.setTitle("Đăng ký theo nhân viên");
		group1.setWidth(new Extent(400));
		group1.add(grid1);
		
		
		GroupBox group2 = new GroupBox();
		Grid grid2 = new Grid();
		grid2.setWidth(new Extent(100,Extent.PERCENT));
		grid2.setSize(2);
		grid2.setInsets(new Insets(0, 5, 5, 5));		
		group2.add(grid2);
	
		rootLayout.add(group1);

		//Phần danh cho nhân viên --------------------------------------------------------------
		GroupBox group2_ = new GroupBox();
		lbl_EMPSN = new Label();
		lbl_EMPSN.setText("N_REGISTER_SHIFT.EMPSN");
		txt_EMPSN = new DscField();
		txt_EMPSN.setId("txt_EMPSN");
		txt_EMPSN.setMaximumLength(8);
		txt_EMPSN.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doEmployee(e);
				}
			});
		chboxupdate_hs=new CheckBox();
		chboxupdate_hs.setText("cập nhập ca làm việc trong hồ sơ ");	
		chboxupdate_hs.setSelected(false);	
		chboxupdate_hs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				REGISTER_SHIFTProgram shift = new REGISTER_SHIFTProgram();
				shift.check_hs=chboxupdate_hs.isSelected()?true:false;				
				updateshift_hs(e);
				System.out.println(shift.check_hs);				
			}
		});
		lblfullName=new Label();
		lblfullName.setForeground(new Color(0xff0080));
		group2_.add(txt_EMPSN);
		group2_.add(chboxupdate_hs);
		group2_.add(lblfullName);
		grid1.add(lbl_EMPSN);		
		grid1.add(group2_);
	
		lbl_SHIFT_DATE = new Label();
		lbl_SHIFT_DATE.setText("N_REGISTER_SHIFT.SHIFT_DATE");
		df_SHIFT_DATE = new DscDateField();
		df_SHIFT_DATE.setId("df_SHIFT_DATE");
		df_SHIFT_DATE.setDateFormat(sdf);
		df_SHIFT_DATE.setSelectedDate(Calendar.getInstance(Locale.ENGLISH));
		df_SHIFT_DATE.getDateChooser().setWidth(new Extent(300,Extent.PX));
		df_SHIFT_DATE.getDateChooser().setMonthNameLength(30);
		df_SHIFT_DATE.getDateChooser().setForeground(Color.LIGHTGRAY);
	
		grid1.add(lbl_SHIFT_DATE);
		grid1.add(group2);
		Label lbl_from = new Label("Từ ngày");
		grid2.add(lbl_from);
		grid2.add(df_SHIFT_DATE);
		df_ToDATE = new DateField();
		df_ToDATE.setDateFormat(sdf);
		df_ToDATE.setSelectedDate(Calendar.getInstance(Locale.ENGLISH));
		df_ToDATE.getDateChooser().setWidth(new Extent(300,Extent.PX));
		df_ToDATE.getDateChooser().setMonthNameLength(30);
		df_ToDATE.getDateChooser().setForeground(Color.LIGHTGRAY);
		df_ToDATE.setEnabled(false);
		Label lbl_to = new Label("Đến ngày");
		grid2.add(lbl_to);
		final CheckBox check = new CheckBox();
		check.setSelected(false);
		check.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(check.isSelected()){
				df_ToDATE.setEnabled(true);
				df_ToDATE.getTextField().setBackground(Color.WHITE);
				SaveMultiDay = true;}
				else{
				df_ToDATE.setEnabled(false);
				df_ToDATE.getTextField().setBackground(Color.DARKGRAY);
				SaveMultiDay = false;}
			}
		});
		Row row = new Row();
		GridLayoutData layout = new GridLayoutData();
		layout.setRowSpan(2);
		row.setLayoutData(layout);
		row.add(df_ToDATE);
		row.add(check);
		grid2.add(row);
		GroupBox group3 = new GroupBox();
		group3.setWidth(new Extent(300));
		Grid grid3 = new Grid();
		grid3.setWidth(new Extent(100,Extent.PERCENT));
		grid3.setSize(2);
		grid3.setInsets(new Insets(0, 5, 5, 5));		
		 rad1 = new RadioButtonEx();
		 rad2 = new RadioButtonEx();
		 rad3=new RadioButtonEx();
		ButtonGroup grouprad = new ButtonGroup();		
		rad1.setActionCommand("SingleObject");
		rad2.setActionCommand("MultiObject");
		rad1.setSelected(true);		
		rad3.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				GetStyleSaveObject(e);		
			}
		});
		rad3.setActionCommand("MultiObject_");		
		rad3.setSelected(true);
		
		rad3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GetStyleSaveObject(e);		
			}
		});
		
		rad2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GetStyleSaveObject(e);	
			}
		});
		rad1.setGroup(grouprad);
		rad2.setGroup(grouprad);
		
		grid3.add(rad1);
		grid3.add(new Label("Lưu theo 1 số thẻ"));
		grid3.add(rad2);
		grid3.add(new Label("Lưu theo nhiều số thẻ/ Lưu theo đơn vị "));
	
		lbl_ID_SHIFT = new Label();
		lbl_ID_SHIFT.setText("N_REGISTER_SHIFT.ID_SHIFT");		
		sf_ID_SHIFT = new SelectFieldEx();
		sf_ID_SHIFT.setId("sf_ID_SHIFT");
		sf_ID_SHIFT.setWidth(new Extent(100, Extent.PERCENT));
		map = bindTIME_SHIFT();			
		sf_ID_SHIFT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				selectid_shift(e);
			
			}
		});
		Row row_ = new Row();
		GridLayoutData layout_ = new GridLayoutData();
		layout_.setRowSpan(2);
		row_.setLayoutData(layout);
		
		grid2.add(row);
        GroupBox gbCalamviec=new GroupBox();		
		Grid gridCaLV = new Grid();
		gridCaLV.setWidth(new Extent(150,Extent.PERCENT));
		gridCaLV.setSize(3);
		gridCaLV.setInsets(new Insets(0, 5, 5, 5));
		group3.add(lbl_ID_SHIFT);
		row_.add(sf_ID_SHIFT);			
		txt_TIME_IN = new DscField();
		txt_TIME_IN.setId("txt_TIME_IN");
		txt_TIME_IN.setMaximumLength(5);
		txt_TIME_IN.setWidth(new Extent(20, Extent.PERCENT));
		txt_TIME_IN.setEnabled(false);
		txt_TIME_IN.setDisabledBackground(new Color(244, 252, 255));
		row_.add(txt_TIME_IN);
		txt_TIME_OUT = new DscField();
		txt_TIME_OUT.setId("txt_TIME_OUT");
		txt_TIME_OUT.setMaximumLength(5);
		txt_TIME_OUT.setWidth(new Extent(20, Extent.PERCENT));
		txt_TIME_OUT.setEnabled(false);
		txt_TIME_OUT.setDisabledBackground(new Color(244, 252, 255));
		row_.add(txt_TIME_OUT);	
		gridCaLV.add(row_);
		gbCalamviec.add(gridCaLV);
		group3.add(gbCalamviec);
		Grid gridloaiCa= new Grid();
		gridloaiCa.setWidth(new Extent(100,Extent.PERCENT));
		gridloaiCa.setSize(2);
		gridloaiCa.setInsets(new Insets(0, 5, 5, 5));
		lbl_ID_SPDEPT = new Label();
		lbl_ID_SPDEPT.setText("N_REGISTER_SHIFT.ID_SPDEPT");
		sf_ID_SPDEPT = new SelectFieldEx();
		sf_ID_SPDEPT.setId("sf_ID_SPDEPT");
		sf_ID_SPDEPT.setWidth(new Extent(100, Extent.PERCENT));
		sf_ID_SPDEPT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				selectID_SPDEPT(e);
			
			}
		});
		group3.add(lbl_ID_SPDEPT);
		gridloaiCa.add(sf_ID_SPDEPT);
		lblspdept_list=new Label();		
		lblspdept_list.setForeground(new Color(0xff0080));		
		lblspdept_list.setText("Tính TC Sau");
		lblspdept_list.setVisible(false);
		gridloaiCa.add(lblspdept_list);		
		txt_NOTE = new DscField();
		txt_NOTE.setId("txt_NOTE");
		txt_NOTE.setWidth(new Extent(60,Extent.PERCENT));		
		txt_NOTE.setEnabled(false);
		txt_NOTE.setDisabledBackground(new Color(244, 252, 255));
		gridloaiCa.add(txt_NOTE);	
		txt_OT_7H = new DscField();
		txt_OT_7H.setId("txt_OT7H");
		txt_OT_7H.setMaximumLength(1);
		txt_OT_7H.setWidth(new Extent(20, Extent.PERCENT));	
		gridloaiCa.add(txt_OT_7H);
		txt_OT_7H.setEnabled(false);
		txt_OT_7H.setDisabledBackground(new Color(244, 252, 255));
		GroupBox gbloaica=new GroupBox();
		gbloaica.add(gridloaiCa);
		group3.add(gbloaica);
		group3.add(grid3);
		rootLayout.add(group3);
		
		
	}

	protected void selectID_SPDEPT(ActionEvent e) {
		String strAOTFOT=""; // tính tăng ca sau;
		// tính tăng ca trước;		
		
				
			String ID_SPDEPT=sf_ID_SPDEPT.getSelectedItem().toString();
			doDisstrAOTFOT(ID_SPDEPT);	
	
		if(sf_ID_SPDEPT.getSelectedIndex()==0)
		{
			lblspdept_list.setText("Tính TC Sau");
		}
		
		// TODO Auto-generated method stub
		
	}
	private void doDisstrAOTFOT( String ID_SPDEPT)
	{
		String strAOTFOT=""; // tính tăng ca sau;
		// tính tăng ca trước;		
		
					
			IGenericDAO<N_SPDEPT_LIST, String> daoSPDEPT_LIST=Application.getApp().getDao(N_SPDEPT_LIST.class);
			List<N_SPDEPT_LIST> listN_SPDEPT_LIST=daoSPDEPT_LIST.find(1, "from N_SPDEPT_LIST where ID_SPDEPT=?",ID_SPDEPT);
			if(listN_SPDEPT_LIST.size()>0)
			{
				N_SPDEPT_LIST objN_SPDEPT_LIST=listN_SPDEPT_LIST.get(0);
				if(objN_SPDEPT_LIST.getAOT().equals("1"))
				{
					strAOTFOT="tính tc sau, ";
				}
				if(objN_SPDEPT_LIST.getFOT().equals("1"))
				{
					strAOTFOT=strAOTFOT+"trước";
				}
			}	
		
		lblspdept_list.setText(strAOTFOT);
	}

	protected void selectid_shift(ActionEvent e) {
		// TODO Auto-generated method stub
		if(sf_ID_SHIFT.getSelectedIndex()!=0)
		{
			map.setAsText(sf_ID_SHIFT.getSelectedItem().toString());
			txt_TIME_IN.setText(((N_SHIFT)map.getValue()).getTIME_IN());
			txt_TIME_OUT.setText( ((N_SHIFT)map.getValue()).getTIME_OUT());
			txt_NOTE.setText(((N_SHIFT)map.getValue()).getNOTE());			
		}
		if(sf_ID_SHIFT.getSelectedIndex()==0)
		{
			txt_TIME_IN.setText("");
			txt_TIME_OUT.setText("");
			txt_NOTE.setText("");
		}
	}

	protected void updateshift_hs(ActionEvent e) {
		String strShift="";
		if(chboxupdate_hs.isSelected())		
		{	
			
			if(sf_ID_SHIFT.getSelectedIndex()>0)
			{
				strShift=sf_ID_SHIFT.getSelectedItem().toString();
				shiftProgram.doSaveShift_hs(strShift);
			}
		}
		
	
	}
	
	
}
