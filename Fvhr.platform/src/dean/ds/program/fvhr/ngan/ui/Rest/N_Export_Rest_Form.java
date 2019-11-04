package ds.program.fvhr.ngan.ui.Rest;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import fv.util.Vni2Uni;
import ds.program.fvhr.ngan.ui.DeptUserControl_FactDetail;
import ds.program.fvhr.ngan.ui.DeptUserControl_N;
import ds.program.fvhr.son.ui.ExportAbstract;
import ds.program.fvhr.son.ui.ObjUtility;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.task.ATask;
import dsc.util.function.UUID;
import echopointng.GroupBox;
import groovy.util.slurpersupport.GPathResult;

public class N_Export_Rest_Form extends WindowPane{
	
	Grid rootLayout;
//	DeptUserControl_N dept_ctrl;
	DeptUserControl_FactDetail dept_ctrl;
	ButtonGroup group_time;
	ButtonGroup group_type_rest;
	
	RadioButton	rdio_date;
	RadioButton rdio_month;
	RadioButton rdio_year;
	RadioButton rdio_PN;
	RadioButton rdio_Cluong;
	RadioButton rdio_Cphep;
	RadioButton rdio_benh;
	RadioButton rdio_Dsuc;
	RadioButton rdio_Kphep;
	
	RadioButton rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac;
	
	Button	btn_chitietngay;
	Button 	btn_chitietPN;
	Button 	btn_tongthe;
	OBJ_EMPSN obj_e = new OBJ_EMPSN();
	String user_up = Application.getApp().getLoginInfo().getUserID();
	String ma_user = "";
	
	HSSFWorkbook wb;
	Label   errLabel;
	
	private GroupBox gb_fact_detail;
	private ButtonGroup bt_fact_detail;
	
	
	String  empsn 			= "";	
	String 	fact_ 			= "";
	String 	group_			= "";
	String	dept_			= "";
	String id_dept			= "";
	
	public N_Export_Rest_Form() {
		InitComponent();
	}

	private void InitComponent() {
		this.setStyleName("Default.Window");
		this.setInsets(new Insets(80,20,0,0));
		this.setWidth(new Extent(600));
		this.setHeight(new Extent(500));
		this.setTitle("Xuất Dữ Liệu Phép");
		this.setResizable(false);
		
		rootLayout = new Grid();
		rootLayout.setColumnWidth(0, new Extent(500));
	//	dept_ctrl 	= new DeptUserControl_N();
		dept_ctrl 	= new DeptUserControl_FactDetail();
		
		
		
		/*dept_ctrl.setExFactAction(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dept_ctrl.getFact().equals("FVL")){
					gb_fact_detail.setForeground(Color.BLACK);
					setF(true);
					dept_ctrl.setTF(true);
				}else{
					reset_fact_detail();
					setF(false);
					dept_ctrl.setTF(true);
				}
			}
		});*/
		
		IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();
		
		GridLayoutData	group_time_layout = new GridLayoutData();
						group_time_layout.setAlignment(new Alignment(Alignment.DEFAULT, Alignment.BOTTOM));
		Column 	col_choice_time = new Column();
		
		
				
		group_type_rest = new ButtonGroup();
		GridLayoutData	group_type_rest_layout	= new GridLayoutData();
						group_type_rest_layout.setColumnSpan(2);
						group_type_rest_layout.setAlignment(new Alignment(Alignment.DEFAULT, Alignment.CENTER));
						group_type_rest_layout.setInsets(new Insets(5,15,0,25));
		Column	col_choice_type_rest	= new Column();
		GroupBox	gbox_type_rest  = new GroupBox("Loại Phép");
					rdio_PN	= new RadioButton("Nghỉ PN");
					rdio_PN.setGroup(group_type_rest);
					rdio_Cluong	= new RadioButton("Nghỉ có lương(khác PN)");
					rdio_Cluong.setGroup(group_type_rest);
					rdio_Cphep	= new RadioButton("Nghỉ có phép");
					rdio_Cphep.setGroup(group_type_rest);
					rdio_benh	= new RadioButton("Nghỉ phép bệnh");
					rdio_benh.setGroup(group_type_rest);
					rdio_Dsuc	= new RadioButton("Nghỉ dưỡng sức");
					rdio_Dsuc.setGroup(group_type_rest);
					rdio_Kphep	= new RadioButton("Nghỉ không phép");
					rdio_Kphep.setGroup(group_type_rest);
				gbox_type_rest.add(rdio_PN);	
				gbox_type_rest.add(rdio_Cluong);
				gbox_type_rest.add(rdio_Cphep);
				gbox_type_rest.add(rdio_benh);
				gbox_type_rest.add(rdio_Dsuc);
				gbox_type_rest.add(rdio_Kphep);
				col_choice_type_rest.add(gbox_type_rest);
				col_choice_type_rest.setLayoutData(group_type_rest_layout);
				
		GridLayoutData	row_btn_layout	= new GridLayoutData();
						row_btn_layout.setColumnSpan(3);
						row_btn_layout.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		Row		row_btn		= new Row();
					btn_chitietngay	= new Button();
					btn_chitietngay.setText("Chi Tiết Ngày");
					btn_chitietngay.setStyleName("Default.ToolbarButton");
					btn_chitietngay.setBackground(Color.LIGHTGRAY);
					btn_chitietngay.setWidth(new Extent(100));
					btn_chitietngay.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
					
					btn_chitietPN	= new Button();
					btn_chitietPN.setText("Chi Tiết PN");
					btn_chitietPN.setBackground(Color.LIGHTGRAY);
					btn_chitietPN.setStyleName("Default.ToolbarButton");
					btn_chitietPN.setWidth(new Extent(100));
					btn_chitietPN.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
					
					btn_tongthe	= new Button();
					btn_tongthe.setText("Tổng Thể");
					btn_tongthe.setBackground(Color.LIGHTGRAY);
					btn_tongthe.setStyleName("Default.ToolbarButton");
					btn_tongthe.setWidth(new Extent(100));
					btn_tongthe.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
				row_btn.add(btn_chitietngay);
				row_btn.add(btn_chitietPN);
				row_btn.add(btn_tongthe);
				row_btn.setCellSpacing(new Extent(10));
				row_btn.setLayoutData(row_btn_layout);
		this.add(rootLayout);
		rootLayout.add(dept_ctrl);
		rootLayout.add(col_choice_time);
		rootLayout.add(col_choice_type_rest);
		rootLayout.add(row_btn);
		
        dept_ctrl.getEMPSN_TextField().addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				if(!obj_e.Kt_vungqly_khi_nhap_st(dept_ctrl.getEmpsn(), ma_user,"DEPSN"))
				{
					return;
				}
				
			}
		});
        
   
		btn_chitietngay.addActionListener(new ActionListener() {
			
		
			public void actionPerformed(ActionEvent e) {
				action_chitietngay();
				if(fact_.equals(""))
				{
					if(!obj_e.Kt_vungqly_khi_nhap_st(empsn, ma_user,"DEPSN"))
					{
						return;
					}else
					{
						doExport();
					}
				}else 
				{
					String sql_maql = obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact_,group_,dept_,"DEPSN",id_dept, ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
					if(sql_maql.equals("NO"))
					{
						OBJ_UTILITY.ShowMessageError("Bạn không có quyền thao tác");
						return;
					}else
					{
					  doExport();
					}
				}
				
			}
		});
		
		btn_chitietPN.addActionListener(new ActionListener() {
			
	
			public void actionPerformed(ActionEvent e) {
				action_chitietPN();
				if(fact_.equals(""))
				{
					if(!obj_e.Kt_vungqly_khi_nhap_st(empsn, ma_user,"DEPSN"))
					{
						return;
					}else
					{
						doExport();
					}
				}else 
				{
					String sql_maql = obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact_,group_,dept_,"DEPSN",id_dept, ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
					if(sql_maql.equals("NO"))
					{
						OBJ_UTILITY.ShowMessageError("Bạn không có quyền thao tác");
						return;
					}else
					{
					  doExport();
					}
				}
			}
		});
		
		btn_tongthe.addActionListener(new ActionListener() {
			
		
			public void actionPerformed(ActionEvent e) { 
				action_tongthe();
				if(fact_.equals(""))
				{
					if(!obj_e.Kt_vungqly_khi_nhap_st(empsn, ma_user,"DEPSN"))
					{
						return;
					}else
					{
						doExport();
					}
				}else 
				{
					String sql_maql = obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact_,group_,dept_,"DEPSN",id_dept, ma_user,rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac);
					if(sql_maql.equals("NO"))
					{
						OBJ_UTILITY.ShowMessageError("Bạn không có quyền thao tác");
						return;
					}else
					{
					  doExport();
					}
				}
			}
		});
		
	   
		
	}
	
	


	protected HSSFWorkbook action_tongthe() {
		

		OBJ_UTILITY obj_util = new OBJ_UTILITY();
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String sql4 = "";
		
		empsn 			= dept_ctrl.getEmpsn(); 	
		fact_ 			= dept_ctrl.getFact();
		group_			= dept_ctrl.getGroup();
		dept_			= dept_ctrl.getNameDept();
		id_dept			= dept_ctrl.getIDDept();
		Date	date_	= dept_ctrl.getDate()== null? new Date():dept_ctrl.getDate();
		
		rdio_f1 	= dept_ctrl.rdio_f1;
		rdio_f2 	= dept_ctrl.rdio_f2;
		rdio_f3 	= dept_ctrl.rdio_f3;
		rdio_f5 	= dept_ctrl.rdio_f5;
		rdio_f6 	= dept_ctrl.rdio_f6;
		rdio_khac	= dept_ctrl.rdio_khac;
		
		RadioButton rdio_date  = dept_ctrl.rdio_date;
		RadioButton rdio_month = dept_ctrl.rdio_month;
		RadioButton rdio_year  = dept_ctrl.rdio_year;
		
		
		
		SimpleDateFormat sf_	= new SimpleDateFormat(OBJ_UTILITY.DATE_FORMAT_STR);
		String	dmy_str		= sf_.format(date_);
		String  my_str		= sf_.format(date_).substring(3, 10); 
		String  y_str		= sf_.format(date_).substring(6, 10); 
		String  dmy_str_to  = "";//vi select_ngay_thang_nam viet cho tim tu ngay den ngay
			
		
		String sql = 

			"SELECT a.empsn,a.fname||' '||a.lname as HoTen,"+
		    " d.name_dept,sum(b.total) as sum_day, b.rest_kind,"+
		    " b.note ,to_char(a.date_hired,'dd/mm/yyyy') as date_hired"+
		    " FROM n_Employee a, n_Rest_Detail b,n_department d "+
		    " WHERE a.empsn=b.empsn and a.depsn=d.id_dept ";
	   
	//*xuat so the or xuong,nhom,dv	
		sql1 = obj_e.find_fact_emp(empsn, fact_, group_, dept_,id_dept, "d","a",ma_user,rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac);
	//*Xuat theo chi tiet tung xuong neu chon f1,f2...khac(chi co xuong FVL)
	//	sql2 = obj_e.check_fact_FVL_rb(rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac, "d");
	//*Xuat theo ngay,thang,or nam	
		sql3 = obj_e.select_ngay_thang_nam(rdio_date, rdio_month, rdio_year, dmy_str,dmy_str_to, my_str, y_str, "b", "DATE_OFF");
	//*Xuat theo tung loai phep
		sql4 = select_rest_kind("b");
		
		
		sql = sql+sql1+sql2+sql3+sql4+"  group by a.fname,a.lname,a.empsn, d.name_fact, d.name_group,"+
									  " d.name_dept_name, b.rest_kind,d.name_dept "+
									  " ,date_hired,b.note ";
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
								cell.setCellValue("SO THE ");
				cell		= row.createCell(1);
								cell.setCellValue("HO TEN     ");				
				cell		= row.createCell(2);
								cell.setCellValue("TEN DON VI");	
				cell		= row.createCell(3);				
								cell.setCellValue("TONG SO NGAY NGHI PHEP");
				cell		= row.createCell(4);
								cell.setCellValue("PHEP");
				cell		= row.createCell(5);
								cell.setCellValue("GHI CHU");
				cell		= row.createCell(6);
								cell.setCellValue("NGAY_NX");
				
								
			int n_row	= 1;
			int n_col	= 0;
			for(Object[] arr : list_arr){
				
				row		= sheet.createRow(n_row);
				n_col	= 0;
				for(Object ele : arr){
					cell	= row.createCell(n_col);
					switch (n_col) {
					case 0:	// empsn
						empsn	= ele.toString();
						cell.setCellValue(ele.toString());
						break;
					case 1:	// HO TEN
						cell.setCellValue(ele.toString());
						break;	
					case 2:	// TEN DON VI
						cell.setCellValue(ele.toString());
						break;
					case 3:	// TONG SO NGAY NGHI
						cell.setCellValue(Double.valueOf(ele.toString()));
						break;
					case 4:	//PHEP
						cell.setCellValue(ele.toString());
						break;
					case 5:	//GHI CHU
						if(ele.toString().equals("0"))
						{
							ele = "";
						}
						cell.setCellValue(ele.toString());
						break;
					case 6:	//NGAY_NX
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
		
		
		return wb;

	
	}

	protected HSSFWorkbook action_chitietPN() {
		OBJ_UTILITY obj_util = new OBJ_UTILITY();
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String sql4 = "";
		
		empsn 			= dept_ctrl.getEmpsn(); 	
		fact_ 			= dept_ctrl.getFact();
		group_			= dept_ctrl.getGroup();
		dept_			= dept_ctrl.getNameDept();
		id_dept			= dept_ctrl.getIDDept();
		Date	date_			= dept_ctrl.getDate()== null? new Date():dept_ctrl.getDate();
		
		rdio_f1 	= dept_ctrl.rdio_f1;
		rdio_f2 	= dept_ctrl.rdio_f2;
		rdio_f3 	= dept_ctrl.rdio_f3;
		rdio_f5 	= dept_ctrl.rdio_f5;
		rdio_f6 	= dept_ctrl.rdio_f6;
		rdio_khac	= dept_ctrl.rdio_khac;
		
		RadioButton rdio_date  = dept_ctrl.rdio_date;
		RadioButton rdio_month = dept_ctrl.rdio_month;
		RadioButton rdio_year  = dept_ctrl.rdio_year;
		
		
		SimpleDateFormat sf_	= new SimpleDateFormat(OBJ_UTILITY.DATE_FORMAT_STR);
		String	dmy_str		= sf_.format(date_);
		String  my_str		= sf_.format(date_).substring(3, 10); 
		String  y_str		= sf_.format(date_).substring(6, 10); 
			
		
		String sql = 

		  " select a.empsn,a.fname||' '||a.lname as HoTen,to_char(a.date_hired,'dd/MM/yyyy') date_hired ,"+
		  " a.depsn,b.name_fact,b.name_group,b.name_dept_name ,c.obtain"+
		  " ,c.used,nvl((select sum(d.total) from n_rest_detail d where d.empsn=a.empsn "+
		  "  and to_char(d.date_off,'yyyy')= '"+y_str+"'"+
		  "  and d.rest_kind= 'PN' and d.rest_type in ('CTY',"+
		  " 'PNO_CTY','TET')),0) CTY_SX "+//PN DA SD CTY SX
		  " ,nvl((select sum(d.total) from n_rest_detail d where d.empsn=a.empsn "+
		  "  and to_char(d.date_off,'yyyy')= '"+y_str+"'"+
		  "  and d.rest_kind='PN' and d.rest_type in ('CNV',"+
		  " 'PNO_CNX')),0) CNV_TXin ,c.remain "+
		  " ,c.stored,nvl((select sum(d.total) from n_rest_detail d where d.empsn=a.empsn "+
		  "  and to_char(d.date_off,'yyyy')='"+y_str+"'"+
		  "  and d.rest_kind='PN' and d.rest_type='TON' ),0) Ton_DASD "+
		  " ,((c.co_arr+hol_lunar) -nvl((select sum(d.total) from n_rest_detail d where d.empsn=a.empsn "+
		  " and to_char(d.date_off,'yyyy')='"+y_str+"'"+
		  " and d.rest_kind='PN' and d.rest_type in ('CTY',"+
		  " 'PNO_CTY','TET')),0)) CTY_SX_CONLAI"+// bao gom sap xep phep nghi tet

		  " ,(c.emp_app -nvl((select sum(d.total) from n_rest_detail d where d.empsn=a.empsn "+
		  "  and to_char(d.date_off,'yyyy')='"+y_str+"'"+
		  "  and d.rest_kind='PN' and d.rest_type in ('CNV',"+
		  " 'PNO_CNX') ),0) ) CNV_TXIN_CONLAI "+


		  "  From n_employee a,n_department b,n_rest c "+
		  "  Where a.depsn=b.id_dept and a.empsn=c.empsn and c.year='"+y_str+"'";
		
		
	   
	//*xuat so the or xuong,nhom,dv	
		sql1 = obj_e.find_fact_emp(empsn, fact_, group_, dept_,id_dept, "b", "a",ma_user,rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac);
	//*Xuat theo chi tiet tung xuong neu chon f1,f2...khac
		sql2 = obj_e.check_fact_FVL_rb(rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac, "b");
	
		
		
		sql = sql+sql1+sql2+" order by a.depsn,a.empsn";
		List<Object[]> list_arr = obj_util.Exe_Sql_nfield_nrow(sql,16);

		try {	// get data and export into Workbook
			
			File f = ReportFileManager.getReportFormatFolder("dshr");
			InputStream in 		= new FileInputStream(new File(f.getPath(), "temp.xls"));
			
			wb = new HSSFWorkbook(in);
			HSSFSheet 	sheet	= wb.getSheetAt(0);
			HSSFRow		row;
			HSSFCell	cell;
			
			row				= sheet.createRow(0);
				cell		= row.createCell(0);
								cell.setCellValue("SO THE ");
				cell		= row.createCell(1);
								cell.setCellValue("HO TEN     ");	
				cell		= row.createCell(2);
								cell.setCellValue("NGAY NX");
				cell		= row.createCell(3);
								cell.setCellValue("MA DON VI");
				cell		= row.createCell(4);
								cell.setCellValue("XUONG");
				cell		= row.createCell(5);
								cell.setCellValue("NHOM");
				cell		= row.createCell(6);
								cell.setCellValue("TEN DON VI");
				cell		= row.createCell(7);
								cell.setCellValue("PN DUOC SD");
				cell		= row.createCell(8);
								cell.setCellValue("TONG PN DA SD");
				cell		= row.createCell(9);
								cell.setCellValue("PN DA SD CTY SX");
				cell		= row.createCell(10);
								cell.setCellValue("PN DA SD TU XIN");
				cell		= row.createCell(11);
								cell.setCellValue("PN CON LAI");
				cell		= row.createCell(12);
								cell.setCellValue("PN TON");
				cell		= row.createCell(13);
								cell.setCellValue("TON DA SD");
				cell		= row.createCell(14);
								cell.setCellValue("CTY SX CON LAI");
				cell		= row.createCell(15);
								cell.setCellValue("CNV TU XIN CON LAI");
								
			int n_row	= 1;
			int n_col	= 0;
			for(Object[] arr : list_arr){
				
				row		= sheet.createRow(n_row);
				n_col	= 0;
				for(Object ele : arr){
					cell	= row.createCell(n_col);
					switch (n_col) {
					case 0:	// empsn
						empsn	= ele.toString();
						cell.setCellValue(ele.toString());
						break;
					case 1:	// HO TEN
						cell.setCellValue(ele.toString());
						break;	
					case 2:	// NGAY NX
						cell.setCellValue(ele.toString());
						break;
					case 3: //MA DVI
						cell.setCellValue(ele.toString());
						break;
					case 4: //XUONG
						cell.setCellValue(ele.toString());
						break;
					case 5: //NHOM
						cell.setCellValue(ele.toString());
						break;
					case 6: //TEN DVI
						cell.setCellValue(ele.toString());
						break;
					case 7: //PN DUOC SD
						cell.setCellValue(Double.valueOf(ele.toString()));
						break;
					case 8: //TONG PN DA SD
						cell.setCellValue(Double.valueOf(ele.toString()));
						break;
					case 9: //PN DA SD CTY SX
						cell.setCellValue(Double.valueOf(ele.toString()));
						break;	
					case 10: //PN DA SD TU XIN
						cell.setCellValue(Double.valueOf(ele.toString()));
						break;
					case 11: //PN CON LAI
						cell.setCellValue(Double.valueOf(ele.toString()));
						break;
					case 12: //PN TON
						cell.setCellValue(Double.valueOf(ele.toString()));
						break;
					case 13: //TON DA SD
						cell.setCellValue(Double.valueOf(ele.toString()));
						break;
					case 14: //CTY SX CON LAI
						cell.setCellValue(Double.valueOf(ele.toString()));
						break;
					case 15: //CNV TU XIN CON LAI
						cell.setCellValue(Double.valueOf(ele.toString()));
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
		
		
		return wb;

		
		
	}

	protected HSSFWorkbook action_chitietngay() {
		
			

			OBJ_UTILITY obj_util = new OBJ_UTILITY();
			String sql1 = "";
			String sql2 = "";
			String sql3 = "";
			String sql4 = "";
			
			empsn 			= dept_ctrl.getEmpsn(); 	
			fact_ 			= dept_ctrl.getFact();
			group_			= dept_ctrl.getGroup();
			dept_			= dept_ctrl.getNameDept();
			id_dept			= dept_ctrl.getIDDept();
			Date date_		= dept_ctrl.getDate()== null? new Date():dept_ctrl.getDate();
			
			rdio_f1 	= dept_ctrl.rdio_f1;
			rdio_f2 	= dept_ctrl.rdio_f2;
			rdio_f3 	= dept_ctrl.rdio_f3;
			rdio_f5 	= dept_ctrl.rdio_f5;
			rdio_f6 	= dept_ctrl.rdio_f6;
			rdio_khac	= dept_ctrl.rdio_khac;
			
			RadioButton rdio_date  = dept_ctrl.rdio_date;
			RadioButton rdio_month = dept_ctrl.rdio_month;
			RadioButton rdio_year  = dept_ctrl.rdio_year;
			
			SimpleDateFormat sf_	= new SimpleDateFormat(OBJ_UTILITY.DATE_FORMAT_STR);
			String	dmy_str		= sf_.format(date_);
			String  my_str		= sf_.format(date_).substring(3, 10); 
			String  y_str		= sf_.format(date_).substring(6, 10); 
			String dmy_str_to	= "";	
			
			String sql = 

						"select t.empsn,e.fname||' '||e.lname as HoTen,d.id_dept,d.name_dept,t.rest_kind,t.date_off,t.total,t.note,e.date_hired \n" +
						"from n_rest_detail t,n_department d,n_employee e \n" + 
						"where         t.empsn = e.empsn and e.depsn = d.id_dept\n" + 
						"";
		   
		//*xuat so the or xuong,nhom,dv	
			sql1 = obj_e.find_fact_emp(empsn, fact_, group_, dept_,id_dept, "d", "e",ma_user,rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac);
		//*Xuat theo chi tiet tung xuong neu chon f1,f2...khac
	//		sql2 = obj_e.check_fact_FVL_rb(rdio_f1, rdio_f2, rdio_f3, rdio_f5, rdio_f6, rdio_khac, "d");
		//*Xuat theo ngay,thang,or nam	
			sql3 = obj_e.select_ngay_thang_nam(rdio_date, rdio_month, rdio_year, dmy_str,dmy_str_to, my_str, y_str, "t", "DATE_OFF");
		//*Xuat theo tung loai phep
			sql4 = select_rest_kind("t");
			
			
			sql = sql+sql1+sql2+sql3+sql4+" order by t.empsn,t.date_off";
			List<Object[]> list_arr = obj_util.Exe_Sql_nfield_nrow(sql,9);

			try {	// get data and export into Workbook
				
				File f = ReportFileManager.getReportFormatFolder("dshr");
				InputStream in 		= new FileInputStream(new File(f.getPath(), "temp.xls"));
				
				wb = new HSSFWorkbook(in);
				HSSFSheet 	sheet	= wb.getSheetAt(0);
				HSSFRow		row;
				HSSFCell	cell;
				
				row				= sheet.createRow(0);
					cell		= row.createCell(0);
									cell.setCellValue("SO THE ");
					cell		= row.createCell(1);
									cell.setCellValue("HO TEN     ");				
					cell		= row.createCell(2);
									cell.setCellValue("MA DON VI");
					cell		= row.createCell(3);
									cell.setCellValue("TEN DON VI");	
					cell		= row.createCell(4);				
									cell.setCellValue("PHEP");
					cell		= row.createCell(5);
									cell.setCellValue("NGAY NGHI");
					cell		= row.createCell(6);
									cell.setCellValue("NGHI/1 NGAY");
					cell		= row.createCell(7);
									cell.setCellValue("GHI CHU");
					cell		= row.createCell(8);
									cell.setCellValue("NGAY_NX");
									
				int n_row	= 1;
				int n_col	= 0;
				for(Object[] arr : list_arr){
					
					row		= sheet.createRow(n_row);
					n_col	= 0;
					for(Object ele : arr){
						cell	= row.createCell(n_col);
						switch (n_col) {
						case 0:	// empsn
							empsn	= ele.toString();
							cell.setCellValue(ele.toString());
							break;
						case 1:	// HO TEN
							cell.setCellValue(ele.toString());
							break;	
						case 2:	// MA DON VI
							cell.setCellValue(ele.toString());
							break;
						case 3:	// TEN DON VI
							cell.setCellValue(ele.toString());
							break;
						case 4:	//PHEP
							cell.setCellValue(ele.toString());
							break;
						case 5:	//NGAY NGHI
							cell.setCellValue(sf_.format((Date)ele));
							break;
						case 6:	//NGHI/1 NGAY
							cell.setCellValue(Double.valueOf(ele.toString()));
							break;
						case 7:	//GHI CHU
							if(ele.toString().equals("0"))
							{
								ele = "";
							}
							cell.setCellValue(ele.toString());
							break;
						case 8:	//NGAY NX
							cell.setCellValue(sf_.format((Date)ele));
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
		userClose();
		
	}
	
	private String getViewerUrl() {
		HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
		String viewerUrl = request.getRequestURL() + "?" +
		WebRenderServlet.SERVICE_ID_PARAMETER + "=" + ReportService.INSTANCE.getId() + "&" + 
		ReportService.PARAM_TYPE + "=" + (ATask.EXECTYPE_DIRECT == 0 ? ReportService.TYPE_TEMP : ReportService.TYPE_OUTPUT) + "&" +
		ReportService.PARAM_KEY + "=";
		return viewerUrl;
	}
	
	//**Begin 15/04/2012 viet ham lay cac thong tin chug cho xuat phep chi tiet or tong the
  
		//***Xuat theo tung loai phep
	private  String select_rest_kind(String alias_table)
	{
		String list = "";
		String sql = "";
		
		if(rdio_PN.isSelected())
		{
			sql = " and "+alias_table+".rest_kind = 'PN'";
		}else if (rdio_Cluong.isSelected())
		{
			sql = " and "+alias_table+".rest_sal = 'REST_PAY' and "+alias_table+".rest_kind not in ('PN')";
		}else if (rdio_Cphep.isSelected())
		{
			sql = " and "+alias_table+".rest_sal = 'REST'";
		}else if (rdio_benh.isSelected())
		{
			sql = " and "+alias_table+".rest_sal = 'REST_SICK'";
		}else if (rdio_Dsuc.isSelected())
		{
			sql = " and "+alias_table+".rest_sal = 'OTHER'";
		}else if (rdio_Kphep.isSelected())
		{
			sql = " and "+alias_table+".rest_sal = 'NWHOUR'";
		}
		list = sql;
		return list;
	}  
	
}
