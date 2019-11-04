package ds.program.fvhr.minh.dependent;

import it.businesslogic.ireport.gui.MessageBox;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.BorderStyle;

import oracle.sql.DATE;

import com.sun.star.mozilla.MozillaProductType;

import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.domain.N_EMP_DEPENDENT;
import ds.program.fvhr.domain.N_RELATIVE_KIND;
import ds.program.fvhr.ui.insurance.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.event.FocusEvent;
import dsc.echo2app.component.event.FocusListener;
import echopointng.DateField;
import echopointng.model.CalendarEvent;
import echopointng.model.CalendarSelectionListener;
import fv.util.DateUtils;
import fv.util.VniEditor;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Style;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.app.list.DefaultListModel;

public class Emp_DependentDataContent extends DataContent {

	private ResourceBundle resourceBundle;
	private Row rootRow;
	private Label lblEmpsn;
	private Label lblEmpName;
	private DscField txtEmpName;
	private Label lblBirthday;
	private Label lblKind;
	private SelectField slfKind;
	private Label lblBegin;
	private DscDateField dtfBegin;
	private Label lblUpdateName;
	private DscField txtUpdateName;
	private Label lblIdkey;
	private DscField txtIdkey;
	private Label lblRelativeName;
	private DscField txtRelativeName;
	private Label lblSex;
	private SelectField slfSex;
	private Label lblConfirm;
	private DscDateField dtfConfirm;
	private Label lblEnd;
	private DscDateField dtfEnd;
	private Label lblUpdateDay;
	private DscDateField dtfUpdateDay;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private InsuranceDAO ins = new InsuranceDAO();    
    private DateUtils fvDateUtil = new DateUtils();
    private MappingPropertyEditor mappingID_KIND;
	private DscDateField dtfBirthday;
	private DscField txtEmpsn;
	private Row rowEmpsn;
	/**
	 * Creates a new <code>TestDataContent</code>.
	 */
	public Emp_DependentDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
//		dtfBirthday = new DscDateField();
//		dtfBirthday.setSelectedDate(null);
	}

	public Class getDataObjectClass() {
		return ds.program.fvhr.domain.N_EMP_DEPENDENT.class;
	}
	
	@Override
	public void doUIRefresh() {
		super.doUIRefresh();
		if((this.getProgram().getDataMode() == IProgram.DATAMODE_NONE) || 
				(this.getProgram().getDataMode() == IProgram.DATAMODE_BROWSER)) {
			rootRow.setEnabled(false);
		} else {
			rootRow.setEnabled(true);
		}
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_EDIT)
			txtEmpsn.setEnabled(false);
		else 
			txtEmpsn.setEnabled(true);
			
		getUIDataObjectBinder().doDataBindUIStyle();

		txtEmpName.requestFocus();
	}
	
	@Override
	protected void doDataContentRefresh() {
		// TODO Auto-generated method stub
		
		super.doDataContentRefresh();
		if(!txtEmpsn.getText().equals(""))
        {
        	empsnAction();
        }
		 
	}
	
	@Override
	protected int doInit() {
		int nRet = super.doInit();
		registPropertyEditor();
		bindUI();
		moreInit();
		return nRet;
	}
	

	private void moreInit(){
		resourceBundle = ResourceBundle.getBundle(
				"resource.localization.UICaption", ApplicationInstance
				.getActive().getLocale());
		txtUpdateName.setEnabled(false);
		dtfUpdateDay.setEnabled(false);
		dtfBegin.setEnabled(false);
		dtfEnd.setEnabled(false);
		
		txtEmpsn.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				empsnAction();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		txtEmpsn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				empsnAction();				
			}
		});

		slfKind.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mappingID_KIND.setAsText(slfKind.getSelectedItem().toString());
				((N_EMP_DEPENDENT)getDataObject()).setIDKIND(mappingID_KIND.getValue().toString());
			}
		});
		
		DscField dscFc = new DscField();
		dscFc.setBorder(new Border(0, Color.BLACK,0));
		dtfConfirm.setTextField(dscFc);
		dtfConfirm.getTextField().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				loadDate(dtfConfirm);
				if(!dtfConfirm.getText().equals(""))
					NgayDangKyChange();
			}
		});
		((DscField)dtfConfirm.getTextField()).addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				loadDate(dtfConfirm);
				if(!dtfConfirm.getText().equals(""))
					NgayDangKyChange();
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		dtfConfirm.getModel().addListener(new CalendarSelectionListener() {

			@Override
			public void displayedDateChange(CalendarEvent arg0) {
				NgayDangKyChange();
				
			}

			@Override
			public void selectedDateChange(CalendarEvent arg0) {
				NgayDangKyChange();
			}
			
		});
		
		slfKind.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				NgayDangKyChange();
				
			}
		});
		txtRelativeName.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				txtRelativeName.setText(txtRelativeName.getText().toUpperCase());
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	
		
		dtfBirthday.getModel().addListener(new CalendarSelectionListener() {

			@Override
			public void displayedDateChange(CalendarEvent arg0) {
				NgayDangKyChange();
				
			}

			@Override
			public void selectedDateChange(CalendarEvent arg0) {
				NgayDangKyChange();
			}
			
		});
		
		DscField dscFb = new DscField();
		dscFb.setBorder(new Border(0, Color.BLACK,0));
		dtfBirthday.setTextField(dscFb);
		dtfBirthday.getTextField().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				loadDate(dtfBirthday);
				if(!dtfBirthday.getText().equals(""))
					NgayDangKyChange();
			}
		});
		
		
		((DscField)dtfBirthday.getTextField()).addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				loadDate(dtfBirthday);
				if(!dtfBirthday.getText().equals(""))
					NgayDangKyChange();
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
        dtfBirthday.setDateFormat(sdf); 
        dtfBirthday.getDateChooser().setLocale(Locale.ENGLISH);
        dtfBegin.setDateFormat(sdf); 
        dtfBegin.getDateChooser().setLocale(Locale.ENGLISH);
        dtfEnd.setDateFormat(sdf); 
        dtfEnd.getDateChooser().setLocale(Locale.ENGLISH);
        dtfConfirm.setDateFormat(sdf); 
        dtfConfirm.getDateChooser().setLocale(Locale.ENGLISH);
        dtfUpdateDay.setDateFormat(sdf); 
        dtfUpdateDay.getDateChooser().setLocale(Locale.ENGLISH);
        
        
	}
	
	private void registPropertyEditor() {
		VniEditor vni = new VniEditor();
		getUIDataObjectBinder().registerCustomEditor(N_EMP_DEPENDENT.class, "NAME_RELATIVE", vni);
        getUIDataObjectBinder().registerCustomEditor(N_EMP_DEPENDENT.class, "IDKIND", getLoaiNguoiThanEditor());
        getUIDataObjectBinder().registerCustomEditor(N_EMP_DEPENDENT.class, "SEX", getGioiTinhEditor());
        
        getUIDataObjectBinder().registerCustomEditor(N_EMP_DEPENDENT.class, "BIRTHDAY", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMP_DEPENDENT.class, "CONFIRM_DATE", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMP_DEPENDENT.class, "BEGINDATE", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMP_DEPENDENT.class, "ENDDATE", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EMP_DEPENDENT.class, "DATE_UPDATE", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        
        mappingID_KIND = getLoaiNguoiThanEditor();
	}

	public MappingPropertyEditor getLoaiNguoiThanEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_RELATIVE_KIND, String> dao = Application.getApp().getDao(N_RELATIVE_KIND.class);
		List<N_RELATIVE_KIND> list = dao.findAll(1000);
		//List<N_RELATIVE_KIND> list = dao.find(1, "o.IDKIND", "6");
		for (N_RELATIVE_KIND r:list){
			e.put(fv.util.Vni2Uni.convertToUnicode(r.getNAMEKIND()), r.getIDKIND());			
		}
		return e;
	}	
	
	private MappingPropertyEditor getGioiTinhEditor(){
		MappingPropertyEditor editor = new MappingPropertyEditor();		
		editor.put("NAM", "NAM");
		editor.put(fv.util.Vni2Uni.convertToUnicode("NỮ"), "NU");
		return editor;
	}	
	
	private void bindUI() {
        getUIDataObjectBinder().addBindMap("IDKEY", txtIdkey, lblIdkey);
        getUIDataObjectBinder().addBindMap("EMPSN", txtEmpsn, lblEmpsn);
        getUIDataObjectBinder().addBindMap("NAME_RELATIVE", txtRelativeName, lblRelativeName);
        getUIDataObjectBinder().addBindMap("BIRTHDAY", dtfBirthday, lblBirthday);
        getUIDataObjectBinder().addBindMap("SEX", slfSex, lblSex);
        getUIDataObjectBinder().addBindMap("IDKIND", slfKind, lblKind);
        getUIDataObjectBinder().addBindMap("CONFIRM_DATE", dtfConfirm, lblConfirm);
        getUIDataObjectBinder().addBindMap("BEGINDATE", dtfBegin, lblBegin);
        getUIDataObjectBinder().addBindMap("ENDDATE", dtfEnd, lblEnd);
        getUIDataObjectBinder().addBindMap("USER_UPDATE", txtUpdateName, lblUpdateName);
        getUIDataObjectBinder().addBindMap("DATE_UPDATE", dtfUpdateDay, lblUpdateDay);
        
	}

	public void empsnAction()
	{
		String fname,gTinh;				
		String soThe =txtEmpsn.getText().toString(); 
		String chuoiTB = ins.checkEmpsn(soThe);
		if (chuoiTB!=null){					
			Application.getApp().showMessageDialog("Thông Báo", chuoiTB, MessageBox.OK);
		}
		else
		{					
			if (ins.checkQLyEmpsn(soThe)==false){
				chuoiTB= "Bạn không có quyền thao tác trên dữ liệu này.";	
				txtEmpName.setText("");
				Application.getApp().showMessageDialog("Thông Báo", chuoiTB, MessageBox.OK);
			}
			else
			{
				OBJ_UTILITY obj = new OBJ_UTILITY();
				fname=obj.findNameEmployee(soThe);
				
				txtEmpName.setForeground(Color.BLUE);
				txtEmpName.setText(fname);						
				if(getProgram().getDataMode() == IProgram.DATAMODE_NEW)
				{			
						//auto tang idkey = empsn+xx
					int dem = Integer.parseInt(ins.GetField("nvl(max(substr(idkey,9,2)),0)", "n_emp_dependent", "empsn", "", "", soThe, "", ""));
						//tang so lan len 
					dem=dem+1;
						
					String idkey ;
					if(String.valueOf(dem).length()<=1)
						idkey =soThe+"0"+String.valueOf(dem);
					else idkey =soThe+String.valueOf(dem);
					txtIdkey.setText(idkey);
				}	
				
			}
		}
	}
	
	public void NgayDangKyChange()
	{	//loi chay nhieu lan
		if (!dtfConfirm.getText().equals("") && !dtfBirthday.getText().equals(""))
		{
			if(getProgram().getDataMode() != IProgram.DATAMODE_NONE&&getProgram().getDataMode() != IProgram.DATAMODE_BROWSER)
			{
				if (dtfConfirm.getSelectedDate().compareTo(dtfBirthday.getSelectedDate())<0)
				{
					String chuoiTB="Ngày đăng ký >= ngày sinh";
					setErrorMessage(chuoiTB);
					Application.getApp().showMessageDialog("Thông Báo", chuoiTB, MessageBox.OK);					
				}
				else
				{
					String thang = fvDateUtil.getMonth(dtfConfirm.getSelectedDate());
					String nam = fvDateUtil.getYear(dtfConfirm.getSelectedDate());
					String chuoiTB, tgKThuc, tgBDau;
					String ngSinh = sdf.format(dtfBirthday.getSelectedDate().getTime());	
					Calendar cal2 = Calendar.getInstance(); //bien tam de so sanh
					Calendar cal1 = Calendar.getInstance();// begin date
					cal2 = fvDateUtil.getCalendar("14/"+thang+"/"+nam);
					cal1  = fvDateUtil.getCalendar("01/"+thang+"/"+nam);// thoi gian bat dau luon la ngay 01 cua thang			
					// vd NGAY SINH 01/05/2008
					String fieldGet="";
					int kind =slfKind.getSelectedIndex();
					switch (kind) {
					case 5:// if nguoi than la con, chau, em thi ngay ket thuc bang ngay truoc ngay sinh nhat 18 tuoi
					case 6://else ko co ngay ket thuc
					case 7:
						fieldGet = "to_char(add_months(to_Date('"+ngSinh+"','"+"dd/mm/yyyy"+"'),18*12)-1,'"+"dd/mm/yyyy"+"')";
						tgKThuc = ins.GetField(fieldGet, "DUAL", "", "", "", "", "", "");
						//if ngay ket thuc <15 thi thang ket thuc -1 
						if(Integer.valueOf(tgKThuc.substring(0, 2))<15)
						{
							String s = "01/"+(Integer.valueOf(tgKThuc.substring(3, 5))-1)+tgKThuc.substring(5);
							tgKThuc = s;
						}
						else
							tgKThuc = "01/"+tgKThuc.substring(3);
						
						break;
						
					default:

						tgKThuc = null;
						break;
					}
					//if ngay dang ky > 14 thi bat dau tu thang sau thang dang ky. 
					if (dtfConfirm.getSelectedDate().compareTo(cal2)>0)
					{
						cal1.add(cal1.MONTH, 1);
					}
					tgBDau	= sdf.format(cal1.getTime());					

					if(tgKThuc!=null)
					{
						dtfEnd.setSelectedDate(ins.get_toCalendar(tgKThuc));
						//if()

						Date nd =new Date(System.currentTimeMillis());
						Date ed = dtfEnd.getSelectedDate().getTime();
						
						if(nd.getYear()>ed.getYear()||(nd.getYear()==ed.getYear()&&nd.getMonth()>ed.getMonth()))
						{

							dtfBegin.setSelectedDate(null);
							dtfEnd.setSelectedDate(null);
							Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, resourceBundle.getString("DEPENDENT.ERR.ENDDAY"));
							return;
						}
					}
					else
						dtfEnd.setSelectedDate(null);
					dtfBegin.setSelectedDate(cal1);
					txtUpdateName.setText(Application.getApp().getLoginInfo().getUserID());
					dtfUpdateDay.setSelectedDate(Calendar.getInstance());
				}
			}
		}
		
		
	}
	
	@Override
	public boolean checkDataObject() {
		N_EMP_DEPENDENT data = (N_EMP_DEPENDENT) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			String chuoiTBLoi = checkData(data);
			if (chuoiTBLoi==null) {
				ret = true;
			} else {
				setErrorMessage(chuoiTBLoi);
				ret= false;
			}
		}
 		return ret;
	}
	
	public String checkData(N_EMP_DEPENDENT data) {
		// thoiGian : dd/mm/yyyy
		String chuoiTB = null;
		chuoiTB= ins.checkEmpsn(data.getEMPSN());
		if (chuoiTB!= null)
			return chuoiTB;
		else
		{
			if (ins.checkQLyEmpsn(data.getEMPSN())==false)
			{
				chuoiTB="Không có quyền thao tác.";
				return chuoiTB;
			}
			else
			{	
				String thoiGian = sdf.format(data.getBEGINDATE());
				String thang 	= thoiGian.substring(3,5);
				String nam		= thoiGian.substring(6,10);
				Boolean check =ins.CheckKhoaDataMonth(data.getEMPSN(), thang, nam);
				if (check ==false){
					chuoiTB="Đã khóa xử lý dữ liệu trong tháng";
					return chuoiTB;
				}
				else
				{
					if(getProgram().getDataMode() == IProgram.DATAMODE_NEW)
						check = ins.Check_exit("N_EMP_DEPENDENT", "EMPSN", "NAME_RELATIVE", data.getEMPSN(), data.getNAME_RELATIVE());
					else
						check = false;
					if (check){
						chuoiTB="Thông tin đã tồn tại.";
						return chuoiTB;
					}
					else
					{
						if (data.getCONFIRM_DATE().compareTo(data.getBIRTHDAY())<0)
						{
							chuoiTB="Ngày đăng ký >= ngày sinh";
							return chuoiTB;							
						}
					}
				}
			}
		}
		return chuoiTB;
	}
	
	public int getCountdayofmonth(int thang,int nam)
	{
		int day = 0;
		if(thang == 1 || thang == 3 || thang == 5 || thang == 7 || thang == 8 || thang == 10 || thang == 12 )
		{
			day = 31;
		}
		else 
			if(thang == 2)
			{
				if(nam%4==0)
					day = 29;
				else
					day = 28;
			}
			else
			{
				day = 30;
			}
		
		return day;
	}
	
	public void loadDate(DscDateField dtf)
	{
		if(!dtf.getText().equals(""))
		{
			String[] stdate = dtf.getText().split("/");
			dtf.setSelectedDate(null);
			dtf.getTextField().setText("");
			int year=0;
		    int month =0;
		    int day =0;
			try {
				if(stdate[2].length()==2)
					stdate[2]="19"+stdate[2];
				year = Integer.valueOf(stdate[2]);
				month= Integer.valueOf(stdate[1]);
				day= Integer.valueOf(stdate[0]);
			} catch (Exception e) {
				dtfBegin.setSelectedDate(null);
				dtfEnd.setSelectedDate(null);
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, resourceBundle.getString("DEPENDENT.ERR.DAY"));
				return;
			}
			Date d =new Date(System.currentTimeMillis());
		    int toyear = d.getYear()+1900;
		    int tomonth = d.getMonth()+1;
			if(((year>1900&&year<toyear)||(year==toyear&&month<=tomonth))&&(month>0 && month <13))
			{
				if(day<=getCountdayofmonth(month, year))
				{
					Calendar cal = Calendar.getInstance();// begin date
					cal  = fvDateUtil.getCalendar(day+"/"+month+"/"+year);
					dtf.setSelectedDate(cal);return;
				}
			}
			dtfBegin.setSelectedDate(null);
			dtfEnd.setSelectedDate(null);
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, resourceBundle.getString("DEPENDENT.ERR.DAY"));
			
			
		}
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		rootRow = new Row();
		rootRow.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(10,
				Extent.PX)));
		add(rootRow);
		Grid Grip = new Grid();
		Grip.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5,
				Extent.PX)));
		Grip.setColumnWidth(0, new Extent(150, Extent.PX));
		Grip.setColumnWidth(1, new Extent(250, Extent.PX));
		rootRow.add(Grip);
		lblEmpsn = new Label();
		lblEmpsn.setText("N_EMP_DEPENDENT.EMPSN");
		Grip.add(lblEmpsn);
		rowEmpsn = new Row();
		rowEmpsn.setId("rowEmpsn");
		Grip.add(rowEmpsn);
		txtEmpsn = new DscField();
		txtEmpsn.setId("txtEmpsn");
		txtEmpsn.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtEmpsn.setWidth(new Extent(166, Extent.PX));
		txtEmpsn.setDisabledBackground(new Color(0xeeeeee));
		txtEmpsn.setMaximumLength(8);
		rowEmpsn.add(txtEmpsn);
		lblEmpName = new Label();
		lblEmpName.setText("Họ và Tên CNV");
		Grip.add(lblEmpName);
		txtEmpName = new DscField();
		txtEmpName.setId("txtEmpName");
		txtEmpName.setEnabled(false);
		txtEmpName.setWidth(new Extent(166, Extent.PX));
		txtEmpName.setBackground(new Color(0xf1fafe));
		txtEmpName.setDisabledBackground(new Color(0xeeeeee));
		Grip.add(txtEmpName);
		lblBirthday = new Label();
		lblBirthday.setText("N_EMP_DEPENDENT.BIRTHDAY");
		Grip.add(lblBirthday);
		dtfBirthday = new DscDateField();
		dtfBirthday.setId("dtfBirthday");
		dtfBirthday.setWidth(new Extent(170, Extent.PX));
		Grip.add(dtfBirthday);
		lblKind = new Label();
		lblKind.setText("N_EMP_DEPENDENT.IDKIND");
		Grip.add(lblKind);
		slfKind = new SelectField();
		slfKind.setWidth(new Extent(170, Extent.PX));
		Grip.add(slfKind);
		lblBegin = new Label();
		lblBegin.setText("N_EMP_DEPENDENT.BEGINDATE");
		Grip.add(lblBegin);
		dtfBegin = new DscDateField();
		dtfBegin.setWidth(new Extent(168, Extent.PX));
		Grip.add(dtfBegin);
		lblUpdateName = new Label();
		lblUpdateName.setText("N_EMP_DEPENDENT.USER_UPDATE");
		Grip.add(lblUpdateName);
		txtUpdateName = new DscField();
		txtUpdateName.setWidth(new Extent(166, Extent.PX));
		txtUpdateName.setDisabledBackground(new Color(0xeeeeee));
		Grip.add(txtUpdateName);
		DscField dscField1 = new DscField();
		Grip.add(dscField1);
		Grid grid2 = new Grid();
		grid2.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5,
				Extent.PX)));
		grid2.setColumnWidth(0, new Extent(150, Extent.PX));
		grid2.setColumnWidth(1, new Extent(200, Extent.PX));
		rootRow.add(grid2);
		lblIdkey = new Label();
		lblIdkey.setText("N_EMP_DEPENDENT.IDKEY");
		grid2.add(lblIdkey);
		txtIdkey = new DscField();
		txtIdkey.setEnabled(false);
		txtIdkey.setWidth(new Extent(166, Extent.PX));
		txtIdkey.setBackground(new Color(0xf1fafe));
		txtIdkey.setDisabledBackground(new Color(0xeeeeee));
		grid2.add(txtIdkey);
		lblRelativeName = new Label();
		lblRelativeName.setText("N_EMP_DEPENDENT.NAME_RELATIVE");
		grid2.add(lblRelativeName);
		txtRelativeName = new DscField();
		txtRelativeName.setId("txtRelativeName");
		txtRelativeName.setWidth(new Extent(166, Extent.PX));
		txtRelativeName.setDisabledBackground(new Color(0xeeeeee));
		grid2.add(txtRelativeName);
		lblSex = new Label();
		lblSex.setText("N_EMP_DEPENDENT.SEX");
		grid2.add(lblSex);
		slfSex = new SelectField();
		slfSex.setWidth(new Extent(170, Extent.PX));
		grid2.add(slfSex);
		lblConfirm = new Label();
		lblConfirm.setText("N_EMP_DEPENDENT.CONFIRM_DATE");
		grid2.add(lblConfirm);
		dtfConfirm = new DscDateField();
		dtfConfirm.setWidth(new Extent(170, Extent.PX));
		grid2.add(dtfConfirm);
		lblEnd = new Label();
		lblEnd.setText("N_EMP_DEPENDENT.ENDDATE");
		grid2.add(lblEnd);
		dtfEnd = new DscDateField();
		dtfEnd.setWidth(new Extent(170, Extent.PX));
		grid2.add(dtfEnd);
		lblUpdateDay = new Label();
		lblUpdateDay.setText("N_EMP_DEPENDENT.DATE_UPDATE");
		grid2.add(lblUpdateDay);
		dtfUpdateDay = new DscDateField();
		dtfUpdateDay.setWidth(new Extent(170, Extent.PX));
		grid2.add(dtfUpdateDay);
	}
}
