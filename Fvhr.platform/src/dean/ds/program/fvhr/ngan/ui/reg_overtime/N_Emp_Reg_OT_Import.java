package ds.program.fvhr.ngan.ui.reg_overtime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.core.io.Resource;

import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.ngan.ui.DeptUserControl_FactDetail;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import ds.program.fvhr.domain.N_REGISTER_OVERTIME;
import ds.program.fvhr.domain.pk.N_REGISTER_OVERTIMEPk;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.fv.app.filetransfer.UploadEvent;
import nextapp.echo2.fv.app.filetransfer.UploadListener;
import nextapp.echo2.fv.app.filetransfer.UploadSelect;

public class N_Emp_Reg_OT_Import extends WindowPane {

    
	private ResourceBundle bundle;
		
		Grid 	rootLayout ;
		OBJ_EMPSN obj_e		    = new OBJ_EMPSN();
		OBJ_UTILITY obj_util 	= new OBJ_UTILITY();
		SimpleDateFormat sf_ 	= new SimpleDateFormat(OBJ_UTILITY.DATE_FORMAT_STR);
		String  user_up_		= Application.getApp().getLoginInfo().getUserID();
		String  ma_user			= "";
		RadioButton rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac;
		DeptUserControl_FactDetail dept_detail;
		
		public N_Emp_Reg_OT_Import() {
			initComponent();
		}
		
		public UploadSelect getFileUploadExcel(){
			
			final Resource res = Application.getSpringContext().getResource("classpath:/conf/format");
			UploadSelect fileUploadSelect = new UploadSelect();
			fileUploadSelect.setEnabledSendButtonText("CẬP NHẬT");
			fileUploadSelect.setHeight(new Extent(50));
			fileUploadSelect.setWidth(new Extent(350));
			try{
				fileUploadSelect.addUploadListener(new UploadListener(){
					public void fileUpload(UploadEvent e) {
						if (!(e.getFileName().endsWith(".xls".toLowerCase()))) {
							Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK, bundle.getString("C_PROPERTIES.XLSIMPORT"));
							return;
						}
						
						try {

							assert res.getFile().exists() && res.getFile().isDirectory() : "";
							StopWatch sw = new StopWatch();
							sw.start();
							File outputFile = new File(res.getFile(), "temp.xls");
							long unwrite = e.getSize();
							OutputStream out = new FileOutputStream(outputFile);
							//Transfer bytes from in to out
							byte[] buf = new byte[1024];
							int len;
							while ((len = e.getInputStream().read(buf)) > 0) {
								// when number of remain bytes is greater than
								// buffer size
								if (unwrite >= len)
									out.write(buf, 0, len);
								else {
									out.write(buf, 0, (int) unwrite);
									break;
								}
								unwrite -= len;
							}
							out.flush();
							out.close();
							String str_error = importProperties(outputFile);		// import emp_job
							//String str_error = Check_ID_Job_From_File(outputFile); // check id_job
							userClose();
							outputFile = null;
							sw.stop();
							OBJ_UTILITY.ShowMessageInfo(str_error);
							
						} catch (IOException e1) {
							
						}
					}

					public void invalidFileUpload(UploadEvent e) {
						Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK, bundle.getString("C_PROPERTIES.XLSIMPORT"));
					}	
						
				});
			}
			catch (TooManyListenersException ex) {
				// TODO: handle exception
				throw new RuntimeException(ex.toString());
			}
			return fileUploadSelect;
		}
		
		private boolean Kt_file_excel(String sothe, String depsn,Date date_)
		{
			rdio_f1	   = dept_detail.rdio_f1;
			rdio_f2	   = dept_detail.rdio_f2;
			rdio_f3	   = dept_detail.rdio_f3;
			rdio_f5	   = dept_detail.rdio_f5;
			rdio_f6	   = dept_detail.rdio_f6;
			rdio_khac  = dept_detail.rdio_khac;
			
			ArrayList<Date> list_date_ot = new ArrayList<Date>();
			list_date_ot  = obj_util.getAllListDate_input(date_, date_);
			if(!obj_e.check_lock(user_up_))
			{
				return false;
			}else if(!obj_e.Kt_vungqly_khi_nhap_st(sothe, ma_user,"DEPSN_TEMP"))
			{
				return false;
			}
			else if(!obj_e.Kt_vungqly_khi_nhap_dv(depsn, ma_user,"DEPSN_TEMP"))
			{
				return false;
			}else if(!obj_e.check_lock_month(sothe,"", "", "", "", date_, "DEPSN_TEMP",ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac))
			{
				return false;
			}else if(!obj_e.check_lock_overtime(sothe, "", "", "","", list_date_ot,ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac))
			{
				return false;
			}else if(!obj_e.Kt_Phep(sothe, date_))
			{
				return false;
			}else if(!obj_e.Kt_Lv_7H(sothe, date_))
			{
				return false;
			}
			return true;
		}
		
	   class Emp_Ot
		{
			private String depsn;
			private BigDecimal  order_number;//so thu tu
			public String getDepsn() {
				return depsn;
			}
			public void setDepsn(String depsn) {
				this.depsn = depsn;
			}
			public BigDecimal getOrder_number() {
				return order_number;
			}
			public void setOrder_number(BigDecimal order_number) {
				this.order_number = order_number;
			}
			public String getEmpsn() {
				return empsn;
			}
			public void setEmpsn(String empsn) {
				this.empsn = empsn;
			}
			public Date getDate_over() {
				return date_over;
			}
			public void setDate_over(Date date_over) {
				this.date_over = date_over;
			}
			public float getCount_time() {
				return count_time;
			}
			public void setCount_time(float count_time) {
				this.count_time = count_time;
			}
			private String empsn;
			private Date   date_over;
			private float  count_time;
		}

		
		private String importProperties(File fileExcel){
			String str_error 		= "";
			String sql				= "";
			InputStream in ;
			String sothe			= "";
			String depsn_temp_old	= "";
			String depsn_excel		= "";
			long	stt_excel		= (long) 0;
			float   count_time_excel= 0; 
			//*N_REGISTER_OVERTIME
			IGenericDAO<N_REGISTER_OVERTIME, String> dao_ot 	= Application.getApp().getDao(N_REGISTER_OVERTIME.class);
			N_REGISTER_OVERTIME data_ot ;
			
			Object  value_cell_ 	= "";
			long	value_long		= (long) 0;
			float   value_float 	= (float) 0;
			boolean flag_data_ok_ 	= true;
			boolean flag_in			= true;//neu flag_in = true thi insert, = false thi update
			Date	date_up 		= null;
			int 	n_row			= 0;
			final ArrayList<Emp_Ot> list_ot;
			
			try{
				
				date_up = sf_.parse(sf_.format(new Date()));
				
				in = new FileInputStream(fileExcel);
				HSSFWorkbook wb = new HSSFWorkbook(in);
				HSSFSheet sheet = wb.getSheetAt(0) ;
				HSSFRow row 	;
				n_row = sheet.getPhysicalNumberOfRows();
				if(n_row <=1){
					return "Tập tin không có dữ liệu để cập nhật vào chương trình";
					
				}
				else{
					 list_ot = new ArrayList<Emp_Ot>();
					 
				//	System.out.println("Begin Import");
					for (int i = 1; i < n_row; i++) {
						System.out.println("Begin Check File");
						System.out.println("========= Row : " + i);
						 data_ot 			= new N_REGISTER_OVERTIME();
						 flag_data_ok_ 		= true;
						 value_cell_		=	"";
						 String sothe_pre 	= sothe;
						 sothe				= 	"";
						 Emp_Ot objEmpUtil	= new Emp_Ot();
						 
						/* data_ot.setUSER_UP(user_up_);
						 data_ot.setDATE_UP(date_up);*/
						 
						 
						 
						 row = sheet.getRow(i);
						
						// row(i)	: 		DEPSN(String-CAP NHAT VAO 'DEPSN_TEMP' CUA N_EMPLOYEE) 
						 			//		STT(Number-CAP NHAT VAO 'EMPNO_IN_LIST' CUA N_EMPLOYEE)  
						 			//		EMPSN (String)	 
						 			//		DATE_OVER(String)    
						 			//		COUNT_TIME(Number)
						
						//*sothe	
						if(row.getCell(2).getCellType() == HSSFCell.CELL_TYPE_STRING)
						{
							sothe 	   		 = row.getCell(2).getStringCellValue();
							str_error		 = 	" Có cột dữ liệu trống hoặc không đúng định dạng";
						}else
						{
							if(sothe.equals(""))
							{
								str_error     = "";
								flag_data_ok_ = false;
								if(sothe_pre.equals(""))
								{
									str_error	  = "Cột Số thẻ ở dòng đầu tiên không có dữ liệu hoặc không đúng định dạng ";
								}else
								{
									str_error	  = "Cột Số thẻ không có dữ liệu hoặc không đúng định dạng nằm ngay sau số thẻ:" +sothe_pre;
								}
								break;
							}else
							{
								str_error	  = "";	
								flag_data_ok_ = false;
								str_error	  = "Số thẻ không đúng định dạng text";
								value_cell_	  = row.getCell(2).toString().trim();
								break;
							}
						}
						//*depsn
						if(row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_STRING)
						{
							depsn_excel    = row.getCell(0).getStringCellValue();
						}else
						{
							str_error	  = "";
							flag_data_ok_ = false;
							str_error	  = "Đơn vị của số thẻ: '"+sothe+"' không đúng định dạng text";
							value_cell_	  = row.getCell(0).toString().trim();
							break;
						}
						//*stt
						if(row.getCell(1).getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
						{	
							stt_excel	= (long) (row.getCell(1) == null ? (long)0 : row.getCell(1).getNumericCellValue());
						}else
						{
							str_error	  = "";
							flag_data_ok_ = false;
							str_error	  = "STT của số thẻ: '"+sothe+"' không đúng định dạng số";
							value_cell_	  = row.getCell(1).toString().trim();
							break;
						}
						//*count_time
						if(row.getCell(4).getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
						{	
							count_time_excel = (float) (row.getCell(4) == null ? (float)0 : row.getCell(4).getNumericCellValue());
						
						    	if (count_time_excel > 4)
						    	{
						    		str_error	  = "";
									flag_data_ok_ = false;
									str_error	  = "Số giờ tca của số thẻ: '"+sothe+"' vượt quá 4h/ngày";
									value_cell_	  = row.getCell(4).toString().trim();
									break;
						    	}
						}else
						{
							str_error	  = "";
							flag_data_ok_ = false;
							str_error	  = "Số giờ tca của số thẻ: '"+sothe+"' không đúng định dạng số";
							value_cell_	  = row.getCell(4).toString().trim();
							break;
						}
						//*Date_over
						Date date_temp 	= null;
						try{
							date_temp = row.getCell(3).getDateCellValue();
						}catch (Exception e) {
							str_error	    = "";
							flag_data_ok_	= false;
							str_error = "Ngày tca của số thẻ:'"+sothe+"' không đúng định dạng(Date:dd/MM/yyyy)";
							break;
						}
						
						if(!Kt_file_excel(sothe, depsn_excel, date_temp))
						{
							String rest_kind = obj_e.rest_kind;
							String note_7H = obj_e.note_7H;
							if(!rest_kind.equals(""))
							{
								str_error 	= "Kiểm tra lại dlieu st: "+sothe+" ngày: "+sf_.format(date_temp)+ ": nghỉ phép: "+rest_kind;
							}else if(!note_7H.equals(""))
							{
								str_error 	= "Kiểm tra lại dlieu st: "+sothe+" ngày: "+sf_.format(date_temp)+ ": đăng ký làm việc: "+note_7H;
							}else
							{
								str_error 		= "Kiểm tra lại dlieu st: "+sothe;
							}
							flag_data_ok_	= false;
							break;
						}else
						{
							flag_data_ok_ = true;
							objEmpUtil.setEmpsn(sothe);
							objEmpUtil.setDepsn(depsn_excel);
							objEmpUtil.setOrder_number(BigDecimal.valueOf(stt_excel));
							objEmpUtil.setDate_over(date_temp);
							objEmpUtil.setCount_time(count_time_excel);
						}
						list_ot.add(objEmpUtil);
					}
				
					//*Bat dau add du lieu tu file excel vao he thong
				if(flag_data_ok_)
				{
					str_error   = "Hoàn tất kiểm tra file excel!";
						
							MessageDialog dlg = new MessageDialog(MessageDialog.CONTROLS_YES_NO,"DỮ LIỆU FILE EXCEL CÓ "+(n_row-1)+" SÔ THẺ, KHÔNG CÓ LỖI, BẠN CÓ MUỐN CẬP NHẬT DỮ LIỆU TĂNG CA VÀO HỆ THỐNG KHÔNG?");
							dlg.addActionListener(new ActionListener() {
								
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									if(MessageDialog.COMMAND_OK.equals(e.getActionCommand()))
									{
										for(Emp_Ot objEmp_ot:list_ot)
										{
											 String empsn 		= objEmp_ot.getEmpsn();
											 Date date_	  		= objEmp_ot.getDate_over();
											 String str_date  	= sf_.format(date_);
											 String strnote 	= "";
											 float	sogio_tca	= objEmp_ot.getCount_time();
											 
											 IGenericDAO<N_REGISTER_OVERTIME, N_REGISTER_OVERTIMEPk> Dao_ot = Application.getApp().getDao(N_REGISTER_OVERTIME.class);
											 N_REGISTER_OVERTIMEPk pk = new N_REGISTER_OVERTIMEPk(empsn, date_);
											 N_REGISTER_OVERTIME Data_ot = Dao_ot.findById(pk);
											 
											 if(Data_ot == null)
											 {
												 String sql  = 
													 "INSERT INTO N_REGISTER_OVERTIME(EMPSN,DATE_OVER,COUNT_TIME)\n" +
													 " VALUES('"+empsn+"',TO_DATE('"+str_date+"','DD/MM/YYYY'),"+sogio_tca+")";
												 
												 OBJ_UTILITY obj_util = new OBJ_UTILITY();
											     obj_util.Exe_Update_Sql(sql);
											     
											     strnote = "Insert tu file excel ngay tca: "+str_date+",So gio dk tca: "+sogio_tca;

											 }else
											 {
												 strnote = "Update tu file excel ngay tca: "+str_date+",So gio dk tca: Old("+Double.valueOf(String.valueOf(Data_ot.getCOUNT_TIME()))+") New("+sogio_tca+")";
												 Data_ot.setCOUNT_TIME(Float.valueOf(String.valueOf(sogio_tca)));
										         Dao_ot.update(Data_ot);
											 }
											
											 IGenericDAO<N_EMPLOYEE, String> dao_emp 	= Application.getApp().getDao(N_EMPLOYEE.class);
											 N_EMPLOYEE data_emp = dao_emp.findById(empsn);
											 
											 if(data_emp == null)
											 {
												// OBJ_UTILITY.ShowMessageError("không tìm thấy thông tin");
												 strnote = strnote+ "Khong tim thay sothe trong N_EMPLOYEE nen khong update duoc STT va depsn_temp";
												 obj_e.Insert_N_Action_Daily(ma_user, "N_REGISTER_OVERTIME", "IMPORT", empsn, strnote);
												 continue;
											 }else
											 {
												 strnote = strnote+ " - Update N_EMPLOYEE: STT: "+objEmp_ot.getOrder_number()+",depsn_temp(old: '"+data_emp.getDEPSN_TEMP()+"' -->New: '"+objEmp_ot.getDepsn()+"')";
											     data_emp.setEMPNO_IN_LIST(objEmp_ot.getOrder_number()) ;
											     data_emp.setDEPSN_TEMP(objEmp_ot.getDepsn());
											     dao_emp.update(data_emp);
												 obj_e.Insert_N_Action_Daily(ma_user, "N_REGISTER_OVERTIME", "IMPORT", empsn, strnote);
											 }
										}
										
										OBJ_UTILITY.ShowMessageOK("Cập nhật file excel thành công ");
									
										
									}
								
								}
								
								});
							
					
					
				}
				}
			
			}catch (Exception e) {
				str_error = "Kiểm tra lại thông tin tại số thẻ: '"+sothe+"' : " + str_error + " : "+ value_cell_ + " ; ";
			}
			
			
			
			
			return str_error;
	}
			
		private void initComponent(){
			
			dept_detail = new DeptUserControl_FactDetail();
			IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
			DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up_);
			ma_user = Data_DSPB02.getPB_USERNO();
			
			this.setResizable(false);
			this.setTitle("IMPORT");
			this.setStyleName("Default.Window");
			this.setWidth(new Extent(450));
			this.setHeight(new Extent(300));
			rootLayout = new Grid();
			rootLayout.setSize(1);
			rootLayout.setForeground(Color.RED);
			
			rootLayout.add(new  Label("."));
			rootLayout.add(getFileUploadExcel());
			
			rootLayout.add(new Label(" * GHI CHÚ : "));
			rootLayout.add(new Label(" * Chỉ cập nhật file excel 2003 (*.xls) theo định dạng :"));
			rootLayout.add(new Label(" -	Hàng đầu tiên là tiêu đề bao gồm các cột : "));
			rootLayout.add(new Label(" - 	Column A : đơn vị (định dạng : Text)  "));
			rootLayout.add(new Label(" - 	Column B : Số thứ tự (định dạng : Number) "));
			rootLayout.add(new Label(" - 	Column C : Số thẻ (định dạng : Text) "));
			rootLayout.add(new Label(" - 	Column D : Ngày đăng ký tăng ca (định dạng : Date - dd/MM/yyyy) "));
			rootLayout.add(new Label(" - 	Column E : Số giờ đăng ký tăng ca (định dạng : Number) "));
			rootLayout.add(new  Label("."));
			this.add(rootLayout);
		}



}

