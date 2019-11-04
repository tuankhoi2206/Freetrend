package ds.program.fvhr.ngan.ui.reg_overtime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.mapping.RootClass;

import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import fv.util.Vni2Uni;
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
import echopointng.GroupBox;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;

public class N_Export_RegOt_And_RealData_Form extends WindowPane {
	
	private DeptUserControl_FactDetail dept_detail;
	Column rootLayout 		= new Column();
	private DscField 		  tf_date;
	private DateField		  dateF_to_date;
	SimpleDateFormat sf_ 	= new SimpleDateFormat(OBJ_UTILITY.DATE_FORMAT_STR);
	String empsn 	= "";
	Date   date;
	String fact_ 	= "";
	String group_ 	= "";
	String dept_ 	= "";
	String id_dept	= "";
	OBJ_EMPSN obj_e 	 = new OBJ_EMPSN();
    OBJ_UTILITY obj_util = new OBJ_UTILITY();
    String user_up = Application.getApp().getLoginInfo().getUserID();
	String ma_user = "";
	HSSFWorkbook wb;
	RadioButton rb_dktc_khac_qthe,rb_tca_xly_thang_khac_xly_ngay;
	Date date_to 		= null;
	String  my_str		= "";
	String  y_str		= "";
	String  m_str		= "";
	Label   errLabel;
	RadioButton rdio_date,rdio_month,rdio_year;

	public N_Export_RegOt_And_RealData_Form(N_REGISTER_OVERTIMEMProgram _main) {
		initComponents();
	}
	
	
	private boolean kt_dk_thaotac(RadioButton rdio_date,RadioButton rdio_month,RadioButton rdio_year)
	{
		empsn 			= dept_detail.getEmpsn().trim();
		date			= dept_detail.getDate();
		
	    fact_ 			= dept_detail.getFact().trim();
		group_			= dept_detail.getGroup().trim();
		dept_			= dept_detail.getNameDept().trim();
		id_dept 		= dept_detail.getIDDept();
		
		RadioButton rdio_f1	   = dept_detail.rdio_f1;
		RadioButton rdio_f2	   = dept_detail.rdio_f2;
		RadioButton rdio_f3	   = dept_detail.rdio_f3;
		RadioButton rdio_f5	   = dept_detail.rdio_f5;
		RadioButton rdio_f6	   = dept_detail.rdio_f6;
		RadioButton rdio_khac  = dept_detail.rdio_khac;
		
		String	dmy_str		= sf_.format(date);
		my_str			    = sf_.format(date).substring(3, 10); 
		m_str				= sf_.format(date).substring(3, 5); 
		y_str				= sf_.format(date).substring(6, 10); 
		String dmy_str_to	= tf_date.getText().toString();
		  
		
		
		int sum_day_of_month		    = obj_util.GET_NUM_DAY_OF_MONTH(date);//lay ra so ngay cua thang khi kt khoa theo thang
		 ArrayList<Date> list_date_ot	= new ArrayList<Date>();
		
		
		 String end_day_of_month_str	= "";
		 
		if(empsn.equals("") && fact_.equals(""))
		{
			OBJ_UTILITY.ShowMessageError("Lỗi.!Chưa nhập số thẻ hoặc xưởng để xuất Excel");
			return false;
		}
		
		if(date == null)
		{   
				OBJ_UTILITY.ShowMessageError("Lỗi.!Chọn năm cần tìm thông tin('dd/MM/yyyy')'");
				return false;
			
		}else
		{
			
			dmy_str_to	= tf_date.getText().toString();
			Date dmy_date_to 	= null;
			
			if(dmy_str_to.equals(""))
			{
				dmy_date_to = date;
			}else
			{
				try {
					dmy_date_to 	= sf_.parse(dmy_str_to);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(dmy_date_to == null)
				{
					OBJ_UTILITY.ShowMessageError("Lỗi..!Định dạng đến ngày phải là: 'dd/MM/yyyy'");
					return false;
				}else
				{
					if(dmy_date_to.before(date))
					{
						OBJ_UTILITY.ShowMessageError("Lỗi..!Đến ngày phải lớn hơn từ ngày");
						return false;
					}
				}
			}
		}
		//*Lay ra khoang thoi gian can kiem tra tang ca
		 if(rdio_date.isSelected())
		 {
			 if(dmy_str_to.equals(""))
			 {
				 date_to = date;
			 }else
			 {
				 try {
					date_to = sf_.parse(dmy_str_to);
					 my_str	= sf_.format(date).substring(3, 10); 
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 }else if(rdio_month.isSelected())
		 {
			 try {
					date 		= sf_.parse("01/"+my_str);
					 my_str	= sf_.format(date).substring(3, 10); 
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 
			 if(sum_day_of_month > 10)
			 {
				end_day_of_month_str = String.valueOf(sum_day_of_month)+"/"+my_str;
			 }else
			 {
				end_day_of_month_str = "0"+String.valueOf(sum_day_of_month)+"/"+my_str;	
			 } 
			 try {
				date_to = sf_.parse(end_day_of_month_str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }else if(rdio_year.isSelected())
		 {
			 OBJ_UTILITY.ShowMessageError("Khong the kiem tra tang ca theo nam");
			 return false;
		 }
		 //*Kiem tra vung quan ly
		 if (fact_.equals("") && !empsn.equals(""))
		 {
			if(!obj_e.Kt_vungqly_khi_nhap_st(empsn, ma_user,"DEPSN_TEMP"))
			{
				return false;
			}
		}else 
		{
			String sql_maql = obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact_,group_,dept_,"DEPSN_TEMP",id_dept, ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
			if(sql_maql.equals("NO"))
			{
				OBJ_UTILITY.ShowMessageError("Bạn không có quyền thao tác");
				return false;
			}
		}
		
		return true;
	}
	
	
	private void initComponents()
	{
		IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();
	
		this.setResizable(false);
		this.setTitle("Xuất excel ĐKTC khác dữ liệu thực");
		this.setWidth(new Extent(500));
		this.setHeight(new Extent(400));
		this.setStyleName("Default.Window");
		this.add(rootLayout);
		dept_detail = new DeptUserControl_FactDetail();
		rootLayout.add(dept_detail);
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
		
		rb_dktc_khac_qthe = new RadioButton();
		rb_dktc_khac_qthe.setText("Tăng ca quét thẻ khác ĐKTC");
		rb_dktc_khac_qthe.setSelected(true);
		
		rb_tca_xly_thang_khac_xly_ngay = new RadioButton();
		rb_tca_xly_thang_khac_xly_ngay.setText("Tăng ca xử lý tháng khác xử lý ngày");
		
		GroupBox gb_kt_tca = new GroupBox();
		ButtonGroup bt_group_kt_tca = new ButtonGroup();
		rb_dktc_khac_qthe.setGroup(bt_group_kt_tca);
		rb_tca_xly_thang_khac_xly_ngay.setGroup(bt_group_kt_tca);
		gb_kt_tca.add(rb_dktc_khac_qthe);
		gb_kt_tca.add(rb_tca_xly_thang_khac_xly_ngay);
		rootLayout.add(gb_kt_tca);
		
		Button bt_kt_tca = new Button();
		bt_kt_tca.setText("Kiem Tra Tang Ca");
		bt_kt_tca.setBorder(new Border(2,Color.DARKGRAY, Border.STYLE_OUTSET));
		bt_kt_tca.setBackground(Color.LIGHTGRAY);
		bt_kt_tca.setWidth(new Extent(130));
		rootLayout.add(bt_kt_tca);
		bt_kt_tca.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				action_Export();
				doExport();
			}
		});
		
		
		 dept_detail.rdio_date.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					dateF_to_date.setEnabled(true);
					
				}
			});
	        
	        dept_detail.rdio_month.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					tf_date.setText("");
					dateF_to_date.setEnabled(false);
					
				}
			});
	        
	        dept_detail.rdio_year.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					tf_date.setText("");
					dateF_to_date.setEnabled(false);
				}
			});
	        
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
	
	
	protected HSSFWorkbook action_Export() {
		String sql  = "";
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String sql4 = "";
		
		
		rdio_date  = dept_detail.rdio_date;
		rdio_month = dept_detail.rdio_month;
		rdio_year  = dept_detail.rdio_year;
		
		RadioButton rdio_f1	   = dept_detail.rdio_f1;
		RadioButton rdio_f2	   = dept_detail.rdio_f2;
		RadioButton rdio_f3	   = dept_detail.rdio_f3;
		RadioButton rdio_f5	   = dept_detail.rdio_f5;
		RadioButton rdio_f6	   = dept_detail.rdio_f6;
		RadioButton rdio_khac  = dept_detail.rdio_khac;
		
		
		ArrayList<Date> list_date_ot	= new ArrayList<Date>();
		
		
		if(!kt_dk_thaotac(rdio_date, rdio_month, rdio_year))
		{
			
			wb = new HSSFWorkbook();
			return wb;
		}
		if(rb_tca_xly_thang_khac_xly_ngay.isSelected())
		{
			
			sql =

				"select to_char(to_date('"+my_str+"','mm/yyyy'),'mm/yyyy') as NGAY, dt.name_dept as DV,\n" +
				"a.empsn as ST,a.fname||' '||a.lname as HOTEN,\n" + 
				"(select sum(nvl(c.otd,0)+nvl(c.otn,0)+nvl(c.ots,0)+nvl(c.oth,0))\n" + 
				"         from n_data_daily c  where c.empsn=a.empsn\n" + 
				"         and to_char(c.dates,'mm/yyyy')='"+my_str+"') as TC_The ,\n" + 
				"(nvl(b.addcls1,0)+nvl(b.naddcls,0)+nvl(b.addhol,0)+nvl(b.addholn,0)) as DKTC,\n" + 
				"         to_char(a.date_hired,'dd/mm/yyyy') date_hired\n" + 
				"from n_employee a,n_get_data b,n_department dt\n" + 
				
			//	"where dt.name_fact='TB' and dt.name_group='VP'\n" + 
				"where a.empsn=b.empsn and a.depsn=dt.id_dept  and b.months='"+m_str+"' and b.years='"+y_str+"'\n" + 
				"and (nvl(b.addcls1,0)+nvl(b.naddcls,0)+nvl(b.addhol,0)+nvl(b.addholn,0) )\n" + 
				"     <> (select  sum(nvl(c.otd,0)+nvl(c.otn,0)+nvl(c.ots,0)+nvl(c.oth,0))\n" + 
				"            from n_data_daily c  where c.empsn=a.empsn\n" + 
				"         and to_char(c.dates,'mm/yyyy')='"+my_str+"')\n" ;
				
			
			//*xuat so the or xuong,nhom,dv	
			sql1 = obj_e.find_fact_emp(empsn, fact_, group_, dept_,id_dept, "dt","a",ma_user,rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac);
		//*Xuat theo chi tiet tung xuong neu chon f1,f2...khac(chi co xuong FVL)
			sql2 = obj_e.check_fact_FVL_rb(rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac, "dt");
		
			
			sql= sql+sql1+sql2+ " order by a.depsn_temp,ngay";
			
			List<Object[]> list_arr = obj_util.Exe_Sql_nfield_nrow(sql,7);
			  try {	// get data and export into Workbook
					
					File f = ReportFileManager.getReportFormatFolder("dshr");
					InputStream in 		= new FileInputStream(new File(f.getPath(), "temp.xls"));
					
					wb = new HSSFWorkbook(in);
					HSSFSheet 	sheet	= wb.getSheetAt(0);
					HSSFRow		row;
					HSSFCell	cell;
					
					row				= sheet.createRow(0);
						cell		= row.createCell(0);
										cell.setCellValue("THANG");
						cell		= row.createCell(1);
										cell.setCellValue("DON VI");				
						cell		= row.createCell(2);
										cell.setCellValue("SO THE");	
						cell		= row.createCell(3);				
										cell.setCellValue("HO TEN");
						cell		= row.createCell(4);
										cell.setCellValue("TC KHI XLY NGAY");
						cell		= row.createCell(5);
										cell.setCellValue("TC KHI XLY THANG");
						cell		= row.createCell(6);
										cell.setCellValue("NGAY NHAP XUONG");
						
										
					int n_row	= 1;
					int n_col	= 0;
					for(Object[] arr : list_arr){
						
						row		= sheet.createRow(n_row);
						n_col	= 0;
						for(Object ele : arr){
							cell	= row.createCell(n_col);
							switch (n_col) {
							case 0:	// THANG
								cell.setCellValue(ele.toString());
								break;
							case 1:	// DON VI
								cell.setCellValue(ele.toString());
								break;	
							case 2:	// SO THE
								empsn	= ele.toString();
								cell.setCellValue(ele.toString());
								break;
							case 3:	// HO TEN
								cell.setCellValue(ele.toString());
								break;
							case 4:	// TC XLY NGAY
								cell.setCellValue(Double.valueOf(ele.toString()));
								break;
							case 5:	//TC XLY THANG
								cell.setCellValue(Double.valueOf(ele.toString()));
								break;
							case 6:	//NGAY NHAP XUONG
								cell.setCellValue(ele.toString());
								break;
							
							default:
								break;
							}
							
							n_col ++ ;
						}
						n_row ++ ;
					}
					
					
				} catch (Exception e) {
					OBJ_UTILITY.ShowMessageError(e.getMessage());
				}
			
		}else if (rb_dktc_khac_qthe.isSelected())
		{
			list_date_ot = obj_util.getAllListDate_input(date, date_to);
			ArrayList<Object[]> list_ot = new ArrayList<Object[]>();
			for(Date date_ : list_date_ot)
			{
				String	dmy_str		= sf_.format(date_);
				
						sql = 
			
							"select to_char(b.dates,'dd/MM/yyyy') as NGAY,dt.name_dept as DV, a.empsn as ST,a.fname||' '||a.lname as HOTEN,\n" +
							"b.real_ot as TC_The,nvl((select c.count_time from n_register_overtime c  where c.empsn=a.empsn\n" + 
							"and c.date_over=to_date('"+dmy_str+"','dd/mm/yyyy')),0) as DKTC, to_char(a.date_hired,'dd/mm/yyyy') date_hired\n" + 
							"From n_employee a,n_data_daily b,n_department dt  " +
						//	"where dt.name_fact='TB' and dt.name_group='VP'\n" + 
							"where a.empsn=b.empsn and a.depsn_temp=dt.id_dept and b.dates = to_date('"+dmy_str+"','dd/mm/yyyy') \n"+
							" and b.real_ot <> nvl((select c.count_time from n_register_overtime c  where c.empsn=a.empsn\n" +
						    "                       and c.date_over=to_date('"+dmy_str+"','dd/mm/yyyy')),0)";
				
			
					//*xuat so the or xuong,nhom,dv	
					sql1 = obj_e.find_fact_emp(empsn, fact_, group_, dept_,id_dept, "dt","a",ma_user,rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac);
				//*Xuat theo chi tiet tung xuong neu chon f1,f2...khac(chi co xuong FVL)
					sql2 = obj_e.check_fact_FVL_rb(rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac, "dt");
				
					
					sql= sql+sql1+sql2+ " order by a.depsn_temp,ngay";
					
					List<Object[]> list_arr = obj_util.Exe_Sql_nfield_nrow(sql,7);
					list_ot.addAll(list_arr);
			}  
					
				   try {	// get data and export into Workbook
						
						File f = ReportFileManager.getReportFormatFolder("dshr");
						InputStream in 		= new FileInputStream(new File(f.getPath(), "temp.xls"));
						
						wb = new HSSFWorkbook(in);
						HSSFSheet 	sheet	= wb.getSheetAt(0);
						HSSFRow		row;
						HSSFCell	cell;
						
						row				= sheet.createRow(0);
							cell		= row.createCell(0);
											cell.setCellValue("NGAY");
							cell		= row.createCell(1);
											cell.setCellValue("DON VI");				
							cell		= row.createCell(2);
											cell.setCellValue("SO THE");	
							cell		= row.createCell(3);				
											cell.setCellValue("HO TEN");
							cell		= row.createCell(4);
											cell.setCellValue("TANG CA QUET THE");
							cell		= row.createCell(5);
											cell.setCellValue("DKTC");
							cell		= row.createCell(6);
											cell.setCellValue("NGAY NHAP XUONG");
							
											
						int n_row	= 1;
						int n_col	= 0;
						for(Object[] arr : list_ot){
							
							row		= sheet.createRow(n_row);
							n_col	= 0;
							for(Object ele : arr){
								cell	= row.createCell(n_col);
								switch (n_col) {
								case 0:	// ngay
									cell.setCellValue(ele.toString());
									break;
								case 1:	// don vi
									cell.setCellValue(ele.toString());
									break;	
								case 2:	// so the
									cell.setCellValue(ele.toString());
									break;
								case 3:	// ho ten
									cell.setCellValue(ele.toString());
									break;
								case 4:	//tc the
									cell.setCellValue(Double.valueOf(ele.toString()));
									break;
								case 5:	//dktc
									cell.setCellValue(Double.valueOf(ele.toString()));
									break;
								case 6:	//ngay nx
									cell.setCellValue(ele.toString());
									break;
								
								default:
									break;
								}
								
								n_col ++ ;
							}
							n_row ++ ;
						}
						
						
					} catch (Exception e) {
						OBJ_UTILITY.ShowMessageError(e.getMessage());
					}
			}	
	
		return wb;
	}

}
