package ds.program.fvhr.ngan.ui.reg_overtime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import fv.util.FvLogger;
import fv.util.Vni2Uni;
import ds.program.fvhr.domain.N_REGISTER_OVERTIME;
import ds.program.fvhr.domain.pk.N_REGISTER_OVERTIMEPk;
import ds.program.fvhr.ngan.ui.DeptUserControl_FactDetail;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.component.DscField;
import dsc.echo2app.task.ATask;
import dsc.util.function.UUID;
import echopointng.DateField;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ListBox;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;

public class N_Registry_Ot_Form extends WindowPane{
	
	private DeptUserControl_FactDetail	dept_ctrl;
	ArrayList<list_emp_ot_error> list_all;
	HSSFWorkbook wb;
	Label   errLabel;
	int record_exe = 0;
	
    private DateField			dateF_to_date;
    private DscField 			tf_date;
    private Label hoten ;
	private Label tendv;
	private Label madv;
	String user_up = Application.getApp().getLoginInfo().getUserID();
	String ma_user = "";
	Column	rootLayout	= new Column();
	Date date_from	= null;
	Date date_to	= null;
	TextField tf_soGioTca;
	TextField tf_luyKeTca;
	private ListBox		list_error;

	
	private SimpleDateFormat _sf = new SimpleDateFormat("dd/MM/yyyy");
	
	public N_Registry_Ot_Form(N_REGISTER_OVERTIMEMProgram _main) {
		super();
		
		initComponents();
	}
	
	private void setF_Refresh()
	{
		hoten.setVisible(false);
		tendv.setVisible(false);
		madv.setVisible(false);
		tf_luyKeTca.setText("");
	}
	
	private String get_LuyKeTca(String empsn, Date date)
	{//Luy ke tang ca neu la thang 01 thi sum o bang N_REGISTER_OVERTIME
	 //--neu khac thang 01 thi lay tyaddcls o bang luong ATT truoc thang dang chon 1 thang
	 // cong voi sum tca o bang N_REGISTER_OVERTIME tai thang do
		
		String str_date = _sf.format(date);
		String str_month= str_date.substring(3,5);
		String str_my	= str_date.substring(3,10);
		String str_y		= str_date.substring(6,10);
		String sql = "";
		String str_month_pre = "";
		String luyke_tca = "";
		OBJ_UTILITY obj_util = new OBJ_UTILITY();
		
		
		if(!str_month.equals("01"))
		{
			int m = 0;
				m = Integer.valueOf(str_month)-1;
			if(m > 0 && m <= 9)
			{
				str_month_pre = "0"+m;
			}else
			{
				str_month_pre = String.valueOf(m);
			}
				
			sql = 
				"select tyaddcls,(select sum(count_time) from n_register_overtime\n " +
				"				  where empsn='"+empsn+"'\n" +
				"				  and to_char(date_over,'MM/yyyy')='"+str_my+"')\n" +
				" from ATT"+str_y+str_month_pre+"\n" + 
				" where empsn='"+empsn+"'";
			
		    List<Object[]> list = obj_util.Exe_Sql_nfield_nrow(sql, 2);
            if(list.size() > 0)
            {
            	Object[] obj1 = (Object[]) list.get(0);
            	float tam1 = Float.valueOf(obj1[0].toString());
            	float tam2 = Float.valueOf(obj1[1].toString());
            	float tam = tam1 + tam2;
            	if(tam > 0)
            	{
            		luyke_tca = String.valueOf(tam)+" H";
            	}else
            	{
            		luyke_tca = "";
            	}
            }else
            {
            	luyke_tca = "";
            }
		}else //Neu la thang 01
		{
			sql = 
				"select sum(count_time) from n_register_overtime\n" +
				" where empsn='"+empsn+"' " +
				" and to_char(date_over,'MM/yyyy')='"+str_my+"'";
			
			Object obj_sum = obj_util.Exe_Sql_Obj(sql);
			if(obj_sum == null)
			{
				luyke_tca = "";
			}else
			{
				luyke_tca = obj_sum.toString()+" H";
			}

		}
		return luyke_tca;
	}
	
	private void doSelect(ActionEvent e)
	{
		String empsn 		= dept_ctrl.getEmpsn();
		Date  date 			= dept_ctrl.getDate();	
		hoten.setVisible(true);
		tendv.setVisible(true);
		madv.setVisible(true);
		IGenericDAO dao1 = Application.getApp().getDao(N_EMPLOYEE.class);
		List list1 = dao1.find(1, "select a.FNAME, a.LNAME, b.NAME_DEPT,b.ID_DEPT,a.USER_MANAGE_ID, a.TRUCTIEP_SX from N_EMPLOYEE a, N_DEPARTMENT b where a.DEPSN_TEMP=b.ID_DEPT and a.EMPSN=?", new Object[]{empsn});
		if (list1.size()>0){
			Object[] obj1 = (Object[]) list1.get(0);
			String ho  = obj1[0]==null?"":obj1[0].toString().trim();
			String ten = obj1[1]==null?"":obj1[1].toString().trim();
			hoten.setText(Vni2Uni.convertToUnicode(ho+ " " + ten));
			tendv.setText(Vni2Uni.convertToUnicode(obj1[2].toString()));
			madv.setText(obj1[3].toString());
		}
		String luy_ke_tca = get_LuyKeTca(empsn, date);
		tf_luyKeTca.setText(luy_ke_tca);
	}
	
	 public boolean Kt_nhap_ngay_dk_tca()
	 {   
		 date_from		= dept_ctrl.getDate();
		 date_to		= OBJ_UTILITY.DateFormat_DDMMYYYY(dateF_to_date.getText().trim());
		 if(date_from == null){
				OBJ_UTILITY.ShowMessageError("Chọn ngày đăng ký tăng ca");
				return false;
			}
		 
		 if (date_to == null)
		{
				date_to = date_from;
		}else if(date_from.after(date_to))
		{
				OBJ_UTILITY.ShowMessageError("Đến ngày phải lớn hơn hoặc bằng ngày");
				return false;
		}
		 return true;
	 }
	 
	
	 
	 
	class list_emp_ot_error
	{
		String empsn;
		String note;
		public String getEmpsn() {
			return empsn;
		}
		public void setEmpsn(String empsn) {
			this.empsn = empsn;
		}
		public String getNote() {
			return note;
		}
		public void setNote(String note) {
			this.note = note;
		}
		@Override
		public String toString() {
			return empsn + " " + note;
		}
	}
	
	protected void action_ok() {
		OBJ_UTILITY OUtil	= new OBJ_UTILITY();
		OBJ_EMPSN Obj_e		= new OBJ_EMPSN();
	    
		String empsn 	= "";
		String fact		= "";
		String group	= "";
		String nameDept	= "";
		
		empsn 			= dept_ctrl.getEmpsn();
		fact			= dept_ctrl.getFact();
		group			= dept_ctrl.getGroup();
		nameDept		= dept_ctrl.getNameDept();
		String id_dept	= dept_ctrl.getIDDept();
		float sogio_dk_tca	= 0;
		int dem = 0;
		
		ArrayList<String> list_emp	= new ArrayList<String>();
		ArrayList<Date> list_date_ot	= new ArrayList<Date>();
		
		RadioButton rdio_f1 	= dept_ctrl.rdio_f1;
		RadioButton rdio_f2 	= dept_ctrl.rdio_f2;
		RadioButton rdio_f3 	= dept_ctrl.rdio_f3;
		RadioButton rdio_f5 	= dept_ctrl.rdio_f5;
		RadioButton rdio_f6 	= dept_ctrl.rdio_f6;
		RadioButton rdio_khac	= dept_ctrl.rdio_khac;
		
		if (!Obj_e.check_lock(user_up))//KIEM TRA KHOA CHUC NANG XLY DLIEU
		{
			return ;
		}else if(!Kt_nhap_ngay_dk_tca())
		{
			return;
		}
		
		list_date_ot = OUtil.getAllListDate_input(date_from, date_to);
		
		if(dept_ctrl.getFact().equals(""))
		{   
			if (!Obj_e.Kt_vungqly_khi_nhap_st(empsn, ma_user,"DEPSN_TEMP"))
			{
				return;
			}else
			{
				if( !Obj_e.check_lock_month(empsn,"","","","", date_from,"DEPSN_TEMP",ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac))//khoa du lieu thang
				{
					return;
				}else if(!Obj_e.check_lock_overtime(empsn, "","","","", list_date_ot,ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac))
				{
					return;
				}else
				{
					list_emp = OUtil.getListEmpsn_R(empsn,fact,group,nameDept,id_dept,ma_user,"DEPSN_TEMP",rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
					
				}
			
			}
		}else//fact is not null 
		{   
			//	if(!Obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact, group, nameDept, ma_user))
			String sql_maql = Obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact, group, nameDept,"DEPSN_TEMP",id_dept, ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
			if(sql_maql.equals("NO"))
			{   
				OBJ_UTILITY.ShowMessageError("Bạn không có quyền thao tác");
				return;
			}else
			{
				if( !Obj_e.check_lock_month("",fact,group,nameDept,"" ,date_from,"DEPSN_TEMP",ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac))
				{
					return;
				}else if(!Obj_e.check_lock_overtime("", fact,group,nameDept,id_dept, list_date_ot,ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac))
				{
					return;
				}else
				{
					list_emp = OUtil.getListEmpsn_R(empsn,fact,group,nameDept,id_dept,ma_user,"DEPSN_TEMP",rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
					
				}
			}
		}
		
		DefaultListModel list_emp_eror = (DefaultListModel) list_error.getModel();
		list_emp_eror.removeAll();
		list_all = new ArrayList<list_emp_ot_error>();
		for(String emp : list_emp)
		{
			list_emp_ot_error list = new list_emp_ot_error();
			
			for(Date date_ : list_date_ot)
			{
				if(!Obj_e.Kt_Phep(emp, date_))//neu co nghi phep nguyen ngay add vao ds loi
				{
					String rest_kind = Obj_e.rest_kind;
					list_emp_eror.add(emp + " - " +_sf.format(date_) + "- "+rest_kind);
					list.setEmpsn(emp);
					list.setNote(_sf.format(date_)+": "+rest_kind);
					list_all.add(list);
					continue;
				}else if(!Obj_e.Kt_Lv_7H(emp, date_))
				{
					String note = Obj_e.note_7H;
					list_emp_eror.add(emp + " - " +_sf.format(date_) + "- ĐK ca: "+note);
					list.setEmpsn(emp);
					list.setNote(_sf.format(date_)+"- ĐK ca: "+note);
					list_all.add(list);
					continue;
					
				}
				
				System.out.print(tf_soGioTca.getText().toString());
				String str_gio_tca = tf_soGioTca.getText().toString();
				
				if(str_gio_tca.equals(""))
				{
					OBJ_UTILITY.ShowMessageError("Nhập không thành công: Vui lòng nhập số giờ tăng ca");
			    	return;
				}
				else  
			    {  
					sogio_dk_tca = Float.valueOf(str_gio_tca);
			    	if (sogio_dk_tca > 4)
			    	{
			    		OBJ_UTILITY.ShowMessageError("Nhập không thành công: tăng ca không quá 4h/ngày");
			    		return;
			    	}else
			    	{
			    		nhap_tca(emp, date_, sogio_dk_tca);
			    		dem ++;
			    	}
			     }
			}
		}
		
		if(!dept_ctrl.getFact().equals(""))
		{
			if(list_all.size() > 0)
			{
				OBJ_UTILITY.ShowMessageError("Nhập thành công "+dem+" số thẻ ,có 1 vài số thẻ không thể cập nhật. Vui lòng kiểm tra lại những số thẻ đó trong 'Danh Sách không thể cập nhật'.");
				return;
			}else
			{
				 if(record_exe ==0)
				 {
				    	OBJ_UTILITY.ShowMessageError("Bộ nhớ hệ thống đã đầy,không thể lưu dữ liệu được nữa.Vui lòng liên hệ phòng vi tính");
				    	 return;
				    	 
				 }else
				 {
					 OBJ_UTILITY.ShowMessageOK("Nhập thành công "+dem+" số thẻ ");
					 return;
				 }
			}
		}else
		{
			if(list_all.size() > 0)
			{   
				OBJ_UTILITY.ShowMessageError(""+list_all+" :không nhập được");
				return;
			}else
			{
				 if(record_exe ==0)
				 {
				    	OBJ_UTILITY.ShowMessageError("Bộ nhớ hệ thống đã đầy,không thể lưu dữ liệu được nữa.Vui lòng liên hệ phòng vi tính");
				    	 return;
				    	 
				 }else
				 {
					 OBJ_UTILITY.ShowMessageOK("Nhập thành công "+dem+" số thẻ ");
					 return;
				 }
			}
			
		}
		
	}
	
	private void nhap_tca(String empsn,Date date_,float gio_dk_tca)
	{
		 record_exe				 = 0;
		 String str_date  		 = _sf.format(date_);
		 String strnote			 = "";
		 OBJ_EMPSN obj_e		 = new OBJ_EMPSN();
		 IGenericDAO<N_REGISTER_OVERTIME, N_REGISTER_OVERTIMEPk> Dao_ot = Application.getApp().getDao(N_REGISTER_OVERTIME.class);
		 N_REGISTER_OVERTIMEPk pk = new N_REGISTER_OVERTIMEPk(empsn, date_);
		 N_REGISTER_OVERTIME Data_ot = Dao_ot.findById(pk);
		 
		 if(Data_ot == null)
		 {
			 String sql  = 
				 "INSERT INTO N_REGISTER_OVERTIME(EMPSN,DATE_OVER,COUNT_TIME)\n" +
				 " VALUES('"+empsn+"',TO_DATE('"+str_date+"','DD/MM/YYYY'),"+gio_dk_tca+")";
			 
			 OBJ_UTILITY obj_util = new OBJ_UTILITY();
		     record_exe = obj_util.Exe_Update_Sql(sql);
		     if(record_exe ==0)
		     {
		    	 System.out.println("So record da update vao database : " + record_exe); 	  
		     }else
		     {
		    	 strnote = "Insert ngay tca: "+str_date+",So gio dk tca: "+gio_dk_tca;
//		    	 obj_e.Insert_N_Action_Daily(ma_user, "N_REGISTER_OVERTIME", "INSERT", empsn, strnote);
		    	 FvLogger.log(empsn, "INSERT", "N_REGISTER_OVERTIME", strnote);
		     }

		 }else
		 {
			 record_exe  = 1;//gan = 1 de o ben ngoai hien thi thong bao nhap thanh cong thui
			 String lock = Data_ot.getLOCKED();
			 if((lock != null && !lock.equals("Y")) || lock == null)//neu lock = 'Y' la da khoa dlieu khong dc update nua
			 {
				 strnote = "Update ngay tca: "+str_date+",So gio dk tca: Old("+Double.valueOf(String.valueOf(Data_ot.getCOUNT_TIME()))+") New("+gio_dk_tca+")";
			 
				 Data_ot.setCOUNT_TIME(Float.valueOf(String.valueOf(gio_dk_tca)));
				 Dao_ot.update(Data_ot);
				 obj_e.Insert_N_Action_Daily(ma_user, "N_REGISTER_OVERTIME", "UPDATE", empsn, strnote);
			 }
		 }
	}
	
	 protected HSSFWorkbook action_Export() {
			
			//	List<Object[]> list_arr = obj_util.Exe_Sql_nfield_nrow(sql,7);
				  try {	// get data and export into Workbook
						
						File f = ReportFileManager.getReportFormatFolder("fvhr");
						InputStream in 		= new FileInputStream(new File(f.getPath(), "temp.xls"));
						
						wb = new HSSFWorkbook(in);
						HSSFSheet 	sheet	= wb.getSheetAt(0);
						HSSFRow		row;
						HSSFCell	cell;
						
						row				= sheet.createRow(0);
							cell		= row.createCell(0);
										cell.setCellValue("DS KHONG THE CAP NHAT");
										
											
						int n_row	= 1;
						int n_col	= 0;
						
						for(list_emp_ot_error arr : list_all){
							
							row		= sheet.createRow(n_row);
							
							n_col	= 0;
						
							String emp  = arr.getEmpsn();
							String note = arr.getNote();
							
								cell	= row.createCell(n_col);
						      
								switch (n_col) {
								case 0:	// DSach khong the cap nhat
									cell.setCellValue(emp+" -- "+note);
									break;
									
								default:
									break;
									
								}
								n_col ++ ;
								n_row ++ ;	
							
						}
					
					} catch (Exception e) {
						OBJ_UTILITY.ShowMessageError(e.getMessage());
					}
				
			
		
			return wb;
		}
	 
	 private void doExport(){
			if(wb == null){
				errLabel.setText(" !!! ĐIỀU KIỆN XUẤT ???");
				errLabel.setForeground(Color.RED);
				return;
			}
			
			File f1 = new File(System.getProperty("java.io.tmpdir"), UUID.generate());			
			FileOutputStream out;
			try {
				
				out = new FileOutputStream(f1);
				wb.write(out);
				out.flush();
				out.close();
				String name = "DANH_SACH";
				File saveFile = new File(f1.getParentFile(),URLEncoder.encode(Application.getApp().getLoginInfo().getUserID() + ";application/vnd.ms-excel;" + name + Calendar.getInstance().getTimeInMillis() + "" + ".xls", "UTF-8"));			
				ReportFileManager.saveTempReportFile(f1, saveFile);
				Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//userClose();
			//winPane.userClose();
			
		}
		
		private String getViewerUrl() {
			HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
			String viewerUrl = request.getRequestURL() + "?" +
			WebRenderServlet.SERVICE_ID_PARAMETER + "=" + ReportService.INSTANCE.getId() + "&" + 
			ReportService.PARAM_TYPE + "=" + (ATask.EXECTYPE_DIRECT == 0 ? ReportService.TYPE_TEMP : ReportService.TYPE_OUTPUT) + "&" +
			ReportService.PARAM_KEY + "=";
			return viewerUrl;
		}
	
	private void initComponents()
	{   
		
		IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();
		
		this.setTitle("Nhập Chi Tiết Tca");
		this.setInsets(new Insets(20));
		this.setWidth(new Extent(600));
		this.setHeight(new Extent(400));
		this.setStyleName("Default.Window");
		
	//	Column	rootLayout	= new Column();
		rootLayout.setInsets(new Insets(10));
		rootLayout.setCellSpacing(new Extent(4));
		this.setHeight(new Extent(550));
		this.setResizable(false);
        this.add(rootLayout);
        
       
        
        dept_ctrl	= new DeptUserControl_FactDetail();
		dept_ctrl.setAlignment(new Alignment(Alignment.LEFT, Alignment.TOP));
		rootLayout.add(dept_ctrl);
		
	//	rootLayout.add(new Label(" "));
		
		Row row_den_ngay = new Row();
		
		
		tf_date	= new DscField();
		tf_date.setAlignment(new Alignment(Alignment.LEFT, Alignment.LEFT));
		
		
		dateF_to_date	= new DateField();
		dateF_to_date.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dateF_to_date.setTextField(tf_date);
		dateF_to_date.setWidth(new Extent(165));
		row_den_ngay.setInsets(new Insets(5));
		row_den_ngay.setCellSpacing(new Extent(11));
		row_den_ngay.add(new Label("Đến ngày: "));
		row_den_ngay.add(dateF_to_date);
		rootLayout.add(row_den_ngay);
		
		dept_ctrl.getEMPSN_TextField().addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				boolean flag = dept_ctrl.flag_empsn;//vi trong dept_ctrl da kt khi nhap so the nen bien flag dc gan de k kt nua, k thoa dk thi thoat thoat luon
				if(flag == false)
				{
				    hoten.setText("");
				    tendv.setText("");
				    madv.setText("");
					return;
				}else
				{
					doSelect(e);
				}
				
			}
		});
		
		 	hoten = new Label();
	        hoten.setForeground(Color.BLUE);
	        tendv = new Label();
	        tendv.setForeground(Color.BLUE);
	        madv = new Label();
	        madv.setForeground(Color.BLUE);
	        

			rootLayout.add(hoten);
			rootLayout.add(tendv);
			rootLayout.add(madv);
		
		Row row_SoGioTca = new Row();
		tf_soGioTca = new TextField();
		tf_soGioTca.setWidth(new Extent(145));
		tf_soGioTca.setBorder(new Border(1,Color.LIGHTGRAY,Border.STYLE_RIDGE));
		row_SoGioTca.add(new Label("Số giờ tăng ca: "));
		row_SoGioTca.add(tf_soGioTca);
		rootLayout.add(row_SoGioTca);
		
		Row row_luyKeTca = new Row();
		tf_luyKeTca = new TextField();
		tf_luyKeTca.setWidth(new Extent(145));
		tf_luyKeTca.setEnabled(false);
		tf_luyKeTca.setForeground(Color.MAGENTA);
		tf_luyKeTca.setBorder(new Border(1,Color.PINK,Border.STYLE_DASHED));
		row_luyKeTca.add(new Label("Lũy kế tăng ca: "));
		row_luyKeTca.add(tf_luyKeTca);
		rootLayout.add(row_luyKeTca);
		
		Button 	btn_ok = new Button();
		btn_ok.setText("Lưu");
		btn_ok.setStyleName("Default.ToolbarButton");
		btn_ok.setBackground(Color.DARKGRAY);
		btn_ok.setWidth(new Extent(30));
		btn_ok.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btn_ok.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				action_ok();
				
			}
		});
		rootLayout.add(btn_ok);
		
		RadioButton rdio_date  = dept_ctrl.rdio_date;
		RadioButton rdio_month = dept_ctrl.rdio_month;
		RadioButton rdio_year  = dept_ctrl.rdio_year;
		
		rdio_date.setVisible(false);
		rdio_month.setVisible(false);
		rdio_year.setVisible(false);
		
		Label lb_note = new Label();
		lb_note.setForeground(Color.RED);
		lb_note.setText("Lưu ý: Nghỉ phép nguyên ngày và ca:7H không thể cập nhật tăng ca");
		rootLayout.add(lb_note);
		
		list_error	= new ListBox();
		list_error.setWidth(new Extent(260));
		rootLayout.add(new Label("DS không thể cập nhật"));
		rootLayout.add(list_error);
		
		Button btn_export_list_error = new Button();
		btn_export_list_error.setText("Xuất Excel");
		btn_export_list_error.setBorder(new Border(2,Color.LIGHTGRAY,Border.STYLE_OUTSET) );
		btn_export_list_error.setForeground(Color.BLACK);
		btn_export_list_error.setBackground(Color.DARKGRAY);
		btn_export_list_error.setStyleName("Default.ToolbarButton");
		btn_export_list_error.setWidth(new Extent(70));
		rootLayout.add(btn_export_list_error);
		
		btn_export_list_error.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				action_Export();
				doExport();
				
			}
		});
		
		dept_ctrl.bt_refresh.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				setF_Refresh();
				
			}
		});
		
		dept_ctrl.sf_fact.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setF_Refresh();
			}
		});
		
		dept_ctrl.sf_id_dept.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				setF_Refresh();
				
			}
		});
		
	}

}
