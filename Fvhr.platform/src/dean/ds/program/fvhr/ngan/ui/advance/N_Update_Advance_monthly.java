package ds.program.fvhr.ngan.ui.advance;
//20/08/2012 Ke Toan cap nhat ung luong theo tung thang
import java.awt.Checkbox;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.sun.star.util.DateTime;

import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_EMP_ADVANCE;
import ds.program.fvhr.domain.N_REGISTER_OVERTIME;
import ds.program.fvhr.domain.pk.N_EMP_ADVANCEPk;
import ds.program.fvhr.domain.pk.N_REGISTER_OVERTIMEPk;
import ds.program.fvhr.ngan.ui.DeptUserControl_FactDetail;
import ds.program.fvhr.ngan.ui.reg_overtime.N_REGISTER_OVERTIMEMProgram;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.task.ATask;
import dsc.util.function.UUID;
import echopointng.DateField;
import echopointng.GroupBox;
import fv.util.JdbcDAO;
import fv.util.ListBinder;
import fv.util.Vni2Uni;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ListBox;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;


public class N_Update_Advance_monthly extends WindowPane {

	OBJ_EMPSN Obj_e		= new OBJ_EMPSN();
	CheckBox KT_FVL_use,KT_FVLS_use;
	HSSFWorkbook wb = null;
	boolean cap_nhat = true; // true la da cap nhat roi, false la chua cap nhat
	
	
	public N_Update_Advance_monthly() {
		initComponents();
		
		if(Obj_e.check_user_Admin(user_up) || Obj_e.check_user_KToan(user_up))
		{
			KT_FVL_use.setVisible(true);
			KT_FVLS_use.setVisible(true);
	
		}
		/*else if(ma_user.equals("038"))//KJ.THAO KTOAN LUONG FVL
		{ 
			KT_FVL_use.setVisible(true);
			KT_FVLS_use.setVisible(false);
		
		}else if(ma_user.equals("041"))//KJ.DIEM KTOAN LUONG FVLS
		{
			KT_FVLS_use.setVisible(true);
			KT_FVL_use.setVisible(false);
		}*/else
		{
			KT_FVL_use.setVisible(false);
			KT_FVLS_use.setVisible(false);
			
		}
	}
	private DeptUserControl_FactDetail	dept_ctrl;
	Label   errLabel;
	
    private DateField			dateF_to_date;
    private DscField 			tf_date;
    DscDateField		        df_date;

	String user_up = Application.getApp().getLoginInfo().getUserID();
	String ma_user = "";
	Column	rootLayout	;
	Date date_input	= null;
	String madv = "";
	OBJ_EMPSN obj_emp = new OBJ_EMPSN();
	OBJ_UTILITY  obj_util = new OBJ_UTILITY();
	Connection con 	= Application.getApp().getConnection();
	CallableStatement stm_call;
	
	String fact		= "";
	String fact_k   = "";

	
	
	private SimpleDateFormat _sf = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a");
//	private SimpleDateFormat sf_ = new SimpleDateFormat("dd/MM/yyyy");
	
	private boolean check_date(Date date_input, Date date_action)
	{
		SimpleDateFormat 	sf_ = new SimpleDateFormat();
							sf_.applyPattern("MM/yyyy");	
		String date_str = "";
		String date_action_str = "01/"+sf_.format(date_action);
		Date date_input_01 = null;
		Date date_action_01= null;
		if(date_input == null)
		{
			date_str = "";
		}else
		{
			date_str = "01/"+sf_.format(date_input);
		}
		  ///////
			if(date_str.equals("") || date_str == null)
			{
				OBJ_UTILITY.ShowMessageError("Chưa chọn tháng");
				return false;
			}else
			{
				try {
					sf_.applyPattern("dd/MM/yyyy");
					date_input_01  = sf_.parse(date_str);
					date_action_01 = sf_.parse(date_action_str);
					//Begin kiem tra thang cap nhat phai = thang hien hanh
					if(date_input_01.before(date_action_01) || date_input_01.after(date_action_01))
					{
						OBJ_UTILITY.ShowMessageError("Không hợp lệ vì tháng ứng lương phải bằng tháng hiện hành");
						return false;
					}else
					{
						return true;
					}
					//End kiem tra thang cap nhat phai = thang hien hanh
				} catch (ParseException e) {
				    OBJ_UTILITY.ShowMessageError("Ngày tháng sai định dạng."); 
				    return false;
				}
				
				
					
			}
			
	}
	
	
	protected void action_ok() {
		OBJ_UTILITY OUtil	= new OBJ_UTILITY();
		
		
		fact			= dept_ctrl.getFact()==null?"":dept_ctrl.getFact().toString();
		fact_k	= dept_ctrl.getFact()==null?"":dept_ctrl.getFact().toString();//DUNG DE KTA DA CAP NHAT HAY CHUA TRONG N_EMP_ADVANCE_STATUS
		String fact_check = " WHERE D.NAME_FACT ='"+fact+"'";
		date_input      = dept_ctrl.getDate();
//		Date date_action= Obj_e.GetDateTimeOracle(); //old lay gio tu may chu orcale, k lay nua vi gio bi sai
		java.sql.Timestamp date_action = null;
	//	String date_str = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date_action); //old lay gio tu may chu orcale, k lay nua vi gio bi sai
		date_action     = new java.sql.Timestamp(new Date().getTime());
		String date_str = _sf.format(date_action);
		
		System.out.println(date_str);
		
		String str_date =_sf.format(date_input);

		String month 	=  str_date.substring(3, 5);
		String year 	=  str_date.substring(6,10);
	
		ArrayList<String> list_emp	= new ArrayList<String>();
		
		RadioButton rdio_f1 	= dept_ctrl.rdio_f1;
		RadioButton rdio_f2 	= dept_ctrl.rdio_f2;
		RadioButton rdio_f3 	= dept_ctrl.rdio_f3;
		RadioButton rdio_f5 	= dept_ctrl.rdio_f5;
		RadioButton rdio_f6 	= dept_ctrl.rdio_f6;
		RadioButton rdio_khac	= dept_ctrl.rdio_khac;
		
		if((user_up.equals("KJ.THAO") || user_up.equals("KJ.BTHAO")) && fact_k.equals("TB"))
		{
			fact_k = "TB0";
		}else if(user_up.equals("KJ.DIEM") && fact_k.equals("TB"))
		{
			fact_k = "TB1";
		}
		
		
		if (!Obj_e.check_lock(user_up))//KIEM TRA KHOA CHUC NANG XLY DLIEU
		{
			return ;
		}else if(!check_date(date_input,date_action))
		{
			return;
		}else
		
		if (fact.equals("") )
		{
				OBJ_UTILITY.ShowMessageOK("VUI LONG CHON XUONG CAN CAP NHAT");
				return;
		}else
		{
			String sql1 = 

				"select T.NAME_FACT \n" +
				" from n_emp_advance_status t\n" + 
				" WHERE T.NAME_FACT = '"+fact_k+"'"+ 
				" AND TO_CHAR(T.MONTH_ADVANCE,'MM/YYYY') = '"+month+"/"+year+"'"+ 
				" AND T.STATUS = 1";

			
			List<String> list_status = obj_util.Exe_Sql_String(sql1);
			if(list_status != null && list_status.size() > 0)
			{
				OBJ_UTILITY.ShowMessageOK("DU LIEU DA KHOA VI DA DUOC CAP NHAT ROI");
				return;
			}else //Neu chua khoa
			{
				  
					//	if(!Obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact, group, nameDept, ma_user))
					String sql_maql = Obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact, "", "","DEPSN","", ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
					if(sql_maql.equals("NO"))
					{   
						OBJ_UTILITY.ShowMessageError("Bạn không có quyền thao tác");
						return;
					}
			}
		
			//Xet chung	
			if( !Obj_e.check_lock_month("",fact,"","","" ,date_input,"DEPSN",ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac))
			{
				return;
			}else
			{
				SimpleDateFormat 	sf_ = new SimpleDateFormat();
				sf_.applyPattern("MM/yyyy");
							list_emp = OUtil.getListEmpInfo(ma_user,date_input);
							for(String empsn : list_emp)
							{
								System.out.println("________________ " + empsn +"_____________");
								madv=Obj_e.Get_depsn(empsn, date_input);
								if (!madv.equals("ENULL")){
									if (OUtil.thuoc_xuong(madv, fact))
									{
											
										 Date date_input_01 = null;
										 String date_input_01_str = "01/"+sf_.format(date_input);
										try {
											sf_.applyPattern("dd/MM/yyyy");
											date_input_01 = sf_.parse(date_input_01_str);
										} catch (ParseException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										
										 IGenericDAO<N_EMP_ADVANCE, N_EMP_ADVANCEPk> Dao_ad = Application.getApp().getDao(N_EMP_ADVANCE.class);
										 N_EMP_ADVANCEPk pk = new N_EMP_ADVANCEPk(empsn, date_input_01);
										 N_EMP_ADVANCE Data_ad = Dao_ad.findById(pk);
										
										 if(Data_ad == null)
										 {
										 
												String note				= "";
												String sql_call			= "";
												long 	money			= 0;
												
												if( (fact.equals("TB")||fact.equals("KDAO")) && (user_up.equals("KJ.THAO") || user_up.equals("KJ.BTHAO") ) )
												{
													sql_call	= "{?=call S_GET_ADVANCE_SALARY_NOT_WDAY(?,?,?)}";
												}else
												{
													sql_call	= "{?=call S_GET_ADVANCE_SALARY(?,?,?)}";
												}
												
												try {
													sf_.applyPattern("MM/yyyy");
													stm_call = con.prepareCall(sql_call);
													stm_call.registerOutParameter(1, Types.NUMERIC);
													stm_call.registerOutParameter(4,Types.VARCHAR);
													stm_call.setString(2, empsn);
													java.sql.Date date_sql = new java.sql.Date(date_input.getTime());
													String date_sql_str_01    = "01/"+sf_.format(date_sql);
													java.sql.Date date_sql_01 = null;
													try {
														sf_.applyPattern("dd/MM/yyyy");
														date_sql_01 = new java.sql.Date(sf_.parse(date_sql_str_01).getTime());
													} catch (ParseException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													System.out.println(date_sql);
													System.out.println(date_sql_01);
													stm_call.setDate(3,date_sql);
													
													stm_call.execute();
													
													money 	= stm_call.getLong(1);
													note	= stm_call.getString(4);
													note	= note == null ? "" : note;
													stm_call.close();
													
													String sql2 = 
				
														"insert into n_emp_advance(empsn,date_effect,advance_money,note,depsn,user_up,date_up)"+
														" values(?,?,?,?,?,?,?)";
													PreparedStatement pstm = con.prepareStatement(sql2);
													pstm.setString(1, empsn);
													pstm.setDate(2,date_sql_01);
													pstm.setLong(3,money);
													pstm.setString(4,note);
													pstm.setString(5, madv);
													pstm.setString(6,user_up);
											//		pstm.setTimestamp(7,new java.sql.Timestamp(date_action.getTime()));//luu ngaythangnam giophutgiay (old:lay tu oracle)
													pstm.setTimestamp(7,date_action);
													pstm.execute();
													pstm.close();
													
												} catch (SQLException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
										 }else  //neu da ton tai so the va thang ung luong thi bo qua k cap nhat lai
										 {
											 continue;
										 }
										
									}else //neu khong thuoc xuong dang chon thi bo qua va tim tiep
									{
										continue;
									}
								}
							}
							//*23/08/2012 Ngan them de kiem tra Ktoan da cap nhat thanh cong dlieu ung chua
							sf_.applyPattern("dd/MM/yyyy");
							MessageDialog dlg = new MessageDialog(MessageDialog.CONTROLS_OK,"CAP NHAT THANH CONG DU LIEU XUONG "+fact+" NGAY : " + sf_.format(date_input));
							dlg.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									if (MessageDialog.COMMAND_OK.equals(e.getActionCommand())) {
										//									bc.setMessageVisible(true);
										String str_date =_sf.format(date_input);
										java.sql.Timestamp date_action_1 = null;
										date_action_1    = new java.sql.Timestamp(new Date().getTime());

										String month 	=  str_date.substring(3, 5);
										String year 	=  str_date.substring(6,10);

										Connection con			=	Application.getApp().getConnection();
										String sql 				= "{call N_CHECK_ADVANCE(?,?,?,?,?)}";

										try{
											CallableStatement stm   = con.prepareCall(sql); 
											stm.setString(1, fact_k);
											stm.setString(2,month);
											stm.setString(3,year);
											stm.setString(4,user_up);
											stm.setTimestamp(5,date_action_1);
											stm.execute();
											stm.close();

										}catch (Exception e1) {
											System.out.println(e1);
										}finally{
											try {
												con.close();
											} catch (SQLException e1) {
												e1.printStackTrace();
											}
										}


									}
								}
							});


							//End 31/10/2011 Ngan them de kiem tra la xuong do da cap nhat dl thanh cong chua
						}
					}
				
							
	}
	
	private void do_action_export()
	{
		 cap_nhat        = true;
		 fact			 = dept_ctrl.getFact()==null?"":dept_ctrl.getFact().toString();
		 fact_k	= dept_ctrl.getFact()==null?"":dept_ctrl.getFact().toString();//DUNG DE KTA DA CAP NHAT HAY CHUA TRONG N_EMP_ADVANCE_STATUS
		 date_input      = dept_ctrl.getDate()==null?new Date():dept_ctrl.getDate() ;
		 String str_date =_sf.format(date_input);
		 String my	=  str_date.substring(3, 10);
		 
			if((user_up.equals("KJ.THAO") || user_up.equals("KJ.BTHAO")) && fact_k.equals("TB"))
			{
				fact_k = "TB0";
			}else if(user_up.equals("KJ.DIEM") && fact_k.equals("TB"))
			{
				fact_k = "TB1";
			}
		 
		 String sql_s = 

					"select T.NAME_FACT \n" +
					" from N_EMP_ADVANCE_STATUS t\n" + 
					" WHERE T.NAME_FACT = '"+fact_k+"'"+
					" AND TO_CHAR(T.MONTH_ADVANCE,'MM/YYYY') = '"+my+"'"+ 
					" AND T.STATUS = 1";

				List<Object> list_status = obj_util.Exe_sql_nfield_1row(sql_s, 1);

				if(list_status != null && list_status.size() > 0 )
				{					
					action_Export();
				}else
				{
					OBJ_UTILITY.ShowMessageOK("DU LIEU "+fact+" CHUA CAP NHAT THANH CONG NEN KHONG THE XUAT EXCEL");
					cap_nhat = false;
					return;
				}
	}
	
	 protected HSSFWorkbook action_Export() {
		 JdbcDAO dao = new JdbcDAO();
		 date_input      = dept_ctrl.getDate()==null?new Date():dept_ctrl.getDate() ;
		 String str_date =_sf.format(date_input);
		 String my	=  str_date.substring(3, 10);
		
					 String sql = 
								
								"SELECT t.empsn," +
								"       em.fname,"+
								"       em.lname,"+
								"       d.id_dept,"+
								"       d.name_fact,"+
								"       d.name_group,"+
								"		d.name_dept_name,"+
								" 		t.date_effect,"+
								"		t.advance_money,"+ 
								"		t.note," +
								"		em.date_hired\n" +
							//	"		t.t.date_up\n" +
								
								" FROM n_emp_advance t,n_employee em,n_department d\n" + 
								" WHERE   t.empsn = em.empsn\n" + 	
								" AND t.depsn = d.id_dept\n"+
								" AND to_char(t.date_effect,'MM/yyyy') = '"+my+"'\n"+
								" AND d.name_fact='"+fact+"'\n"+
								" AND EM.USER_MANAGE_ID in (SELECT MA_QL FROM N_USER_LIMIT WHERE MA_USER= '"+ 
			    		          ""+ma_user+"' and MA_QL=USER_MANAGE_ID)"+
								"\n" ;//
						 
					   /*  OBJ_UTILITY obj_util    = new OBJ_UTILITY();
						 List<Object[]> list_arr = obj_util.Exe_Sql_nfield_nrow(sql, 11);
						 */
						 List<Object[]> list = dao.getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<Object[]>() {
							@Override
							public Object[] mapRow(ResultSet rs, int arg1)
									throws SQLException {
								Object[] obj = new Object[11];
								for (int i=1;i<=11;i++){
									obj[i-1]=rs.getObject(i);
								}
								return obj;
							}
						}, new Object[]{});
						 System.out.println(sql + " >>> " + list.size());
						 try {	// get data and export into Workbook
								 File f = ReportFileManager.getReportFormatFolder("dshr");
								 InputStream in 		= new FileInputStream(new File(f.getPath(), "temp.xls"));
								 wb = new HSSFWorkbook(in);
								 HSSFSheet 	sheet	= wb.getSheetAt(0);
								 HSSFRow		row;
								 HSSFCell	cell;
									
									row				= sheet.createRow(0);
									cell		= row.createCell(0);
													cell.setCellValue("SO THE     ");
									cell		= row.createCell(1);
													cell.setCellValue("HO");
									cell		= row.createCell(2);
													cell.setCellValue("TEN   ");
									cell		= row.createCell(3);
													cell.setCellValue("MA DON VI  ");
									cell		= row.createCell(4);
													cell.setCellValue("XUONG  ");
									cell		= row.createCell(5);
													cell.setCellValue("NHOM  ");				
									cell		= row.createCell(6);
													cell.setCellValue("TEN DON VI       ");	
									cell		= row.createCell(7);
													cell.setCellValue("THANG UNG LUONG   ");	
									cell		= row.createCell(8);
													cell.setCellValue("TIEN UNG  ");
									cell		= row.createCell(9);
													cell.setCellValue("GHI CHU ");
									cell		= row.createCell(10);
													cell.setCellValue("NGAY NHAP XUONG");	
								/*	cell		= row.createCell(8);
													cell.setCellValue("NGUOI CAP NHAT");
									cell		= row.createCell(9);
													cell.setCellValue("NGAY CAP NHAT");*/
													
									int n_row	= 1;
									int n_col	= 0;
										for(Object[] arr : list){
														
														row		= sheet.createRow(n_row);
														n_col	= 0;
														for(Object ele : arr){
															if(ele == null){
																System.out
																		.println(ele);
															}
															cell	= row.createCell(n_col);
															switch (n_col) {
															case 0:	// empsn
																cell.setCellValue(ele.toString());
																break;
															case 1:	// HO
															//	cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
																cell.setCellValue(ele.toString());
																break;
															case 2:	// TEN
																cell.setCellValue(ele.toString());
																break;
															case 3:	// MA DON VI
																cell.setCellValue(ele.toString());
																break;
															case 4:	// XUONG
																cell.setCellValue(ele.toString());
																break;
															case 5:	// NHOM
																//cell.setCellValue(ele.toString());
																
																if(ele == null || ele.toString().equals("0"))
																{
																	cell.setCellValue("");
																}else
																{
																	cell.setCellValue(ele.toString());
																}
																break;
															case 6:	//TEN DON VI	
															//	cell.setCellValue(sf_.format((Date)ele));
																cell.setCellValue(ele.toString());
																break;
															case 7:	//THANG UNG LUONG	
																cell.setCellValue(_sf.format((Date)ele));
																break;
															case 8:	// TIEN UNG
																cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
																cell.setCellValue(Double.valueOf(ele.toString()));
																break;
															case 9:	// GHI CHU
																cell.setCellValue(ele.toString());
																break;
															case 10: //NGAY NHAP XUONG
																cell.setCellValue(_sf.format((Date)ele));
																break;
														/*	case 8:	// USER UP
																cell.setCellValue(ele.toString());
																break;
															case 9:	//THANG UNG LUONG	
																cell.setCellValue(_sf.format((Date)ele));
																break;*/
																
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
		
		this.setTitle("Dữ liệu ứng lương từng tháng");
		this.setInsets(new Insets(20));
		this.setWidth(new Extent(600));
		this.setHeight(new Extent(400));
		this.setStyleName("Default.Window");
		
	    rootLayout	= new Column();
		rootLayout.setInsets(new Insets(10));
		rootLayout.setCellSpacing(new Extent(4));
		this.setHeight(new Extent(550));
		this.setResizable(false);
        this.add(rootLayout);
        
       
        
        dept_ctrl	= new DeptUserControl_FactDetail();
		dept_ctrl.setAlignment(new Alignment(Alignment.LEFT, Alignment.TOP));
		rootLayout.add(dept_ctrl);
		
        Label lb1 = new Label("  ");
        rootLayout.add(lb1);
		
		KT_FVL_use = new CheckBox();
		KT_FVL_use.setText("FVL_KDAO_TB019_BEP");
		KT_FVL_use.setSelected(false);
		
		KT_FVLS_use = new CheckBox();
		KT_FVLS_use.setText("FVLS_TB020");
		KT_FVLS_use.setSelected(false);
		
	/*	rootLayout.add(KT_FVL_use);   //qua thang 10 lam tiep
		rootLayout.add(KT_FVLS_use);*/
		KT_FVL_use.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//dept_ctrl.sf_fact.toString();
				ListBinder.refreshIndex(dept_ctrl.sf_fact,null);
				KT_FVLS_use.setSelected(false);
				
			}
		});
		
       KT_FVLS_use.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//dept_ctrl.sf_fact.toString();
				ListBinder.refreshIndex(dept_ctrl.sf_fact,null);
				KT_FVL_use.setSelected(false);
				
			}
		});
	    
        rootLayout.add(lb1);
        
        dept_ctrl.sf_fact.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				KT_FVL_use.setSelected(false);
				KT_FVLS_use.setSelected(false);
				
			}
		});
		
		Button 	btn_ok = new Button();
		btn_ok.setText("Cập nhật");
		btn_ok.setStyleName("Default.ToolbarButton");
		btn_ok.setBackground(Color.DARKGRAY);
		btn_ok.setWidth(new Extent(70));
		btn_ok.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btn_ok.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				action_ok();
				
			}
		});
		rootLayout.add(btn_ok);
		
		
		Button btn_export = new Button();
		btn_export.setText("Xuất Excel");
		btn_export.setBorder(new Border(2,Color.LIGHTGRAY,Border.STYLE_OUTSET) );
		btn_export.setForeground(Color.BLACK);
		btn_export.setBackground(Color.DARKGRAY);
		btn_export.setStyleName("Default.ToolbarButton");
		btn_export.setWidth(new Extent(70));
		btn_export.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				fact			 = dept_ctrl.getFact();
				if(fact.equals(""))
				 {
					 OBJ_UTILITY.ShowMessageError("Vui long chon xuong can xuat du lieu");
					 return;
				 }else
				 {
					 do_action_export();
					 if(cap_nhat)
					 {
						 doExport();
					 }else
					 {
						 return;
					 }
				 }
			}
		});
		rootLayout.add(btn_export);
		

		RadioButton rdio_date  = dept_ctrl.rdio_date;
		RadioButton rdio_month = dept_ctrl.rdio_month;
		RadioButton rdio_year  = dept_ctrl.rdio_year;
		
		rdio_date.setVisible(false);
		rdio_month.setVisible(false);
		rdio_year.setVisible(false);
	//	public RadioButton rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac;
		RadioButton rdio_f1	  = dept_ctrl.rdio_f1;
		RadioButton rdio_f2   = dept_ctrl.rdio_f2;
		RadioButton rdio_f3   = dept_ctrl.rdio_f3;
		RadioButton rdio_f5   = dept_ctrl.rdio_f5;
		RadioButton rdio_f6   = dept_ctrl.rdio_f6;
		RadioButton rdio_khac = dept_ctrl.rdio_khac;
		
		rdio_f1.setVisible(false);
		rdio_f2.setVisible(false);
		rdio_f3.setVisible(false);
		rdio_f5.setVisible(false);
		rdio_f6.setVisible(false);
		rdio_khac.setVisible(false);
		
		
		GroupBox gb_fact_detail = dept_ctrl.gb_fact_detail;
		gb_fact_detail.removeAll();
		gb_fact_detail.setHidden(isVisible());
		
		Button	bt_refresh = dept_ctrl.bt_refresh;
		bt_refresh.setVisible(false);
		
		Label 			lb_empsn  = dept_ctrl.lb_empsn;
		TextField		txt_empsn = dept_ctrl.txt_empsn;
		Label			lb_group  = dept_ctrl.lb_group;
		SelectField		sf_group  = dept_ctrl.sf_group;
		Label			lb_dept	  = dept_ctrl.lb_dept;
		SelectField		sf_dept   = dept_ctrl.sf_dept;
		Label			lb_id_dept= dept_ctrl.lb_id_dept;
		SelectField		sf_id_dept= dept_ctrl.sf_id_dept;
		
		lb_empsn.setVisible(false);
		txt_empsn.setVisible(false);
		lb_group.setVisible(false);
		sf_group.setVisible(false);
		lb_dept.setVisible(false);
		sf_dept.setVisible(false);
		lb_id_dept.setVisible(false);
		sf_id_dept.setVisible(false);
		
	}


}
