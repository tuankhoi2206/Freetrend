package ds.task.qlns;
//FORM NAY DUNG CHO XUAT KY TRINH HANG THANG
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nextapp.echo2.app.Row;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


import ds.program.fvhr.domain.BaoHiemYT_SYS;
import ds.program.fvhr.domain.N_N_HEALTH_R;
import ds.program.fvhr.ngan.ui.Health_Insurance.N_HEALTH_REPORT_MProgram;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.task.AListReportTask;
import fv.util.HSSFUtils;

public class Health_ReportTask extends AListReportTask {

	OBJ_UTILITY obj_util;
	String date_str ="";
	
	/**
	 * Ngay Thang can xet 
	 */
	Date date_input;
	IGenericDAO<N_N_HEALTH_R, String> obj_dao;
	SimpleDateFormat sf;
	static ArrayList<Object[]> list_out = new ArrayList<Object[]>();
	
	@Override
	protected String[] getDisplayColumns() {
		return new String[]{};
	}

	@Override
	protected void filterHeader(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow header) {
		
		System.out.println("BEGIN EXPORT HEALTH REPORT !!!");
		
		obj_dao = Application.getApp().getDao(N_N_HEALTH_R.class);
		obj_util = new OBJ_UTILITY();
	
		
		sf = OBJ_UTILITY.Get_format_date();
		date_str = N_HEALTH_REPORT_MProgram.date_str;
		date_input = null;
		HSSFSheet sheet0	= wb.getSheetAt(0);
		HSSFRow row = sheet0.getRow(0);
		HSSFCell 	cell = row.getCell(9);
					cell.setCellValue("BAÛO HIEÅM Y TEÁ "	);
		HSSFSheet sheet1 	= wb.getSheetAt(1);
		
		try {
			date_input = sf.parse(date_str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String  month_			= sf.format(date_input).substring(3,5);
		String  year_			= sf.format(date_input).substring(6,10);
		
					cell = row.getCell(10); 
					cell.setCellValue("T"+month_+"/"+year_);
				row  = sheet0.getRow(1);
					cell = row.getCell(9);
					cell.setCellValue(year_+" 年 第 "+month_+" 月醫 療 保 險");
				row  = sheet0.getRow(2);
					cell = row.getCell(11);
					cell.setCellValue(month_+" 月");
		
		PRINT_GENERAL_BY_NAME_FACT(sheet0,3);			// DAI DE
		PRINT_GENERAL_BY_NAME_FACT(sheet0,6);			// CHAT 1
		PRINT_GENERAL_BY_NAME_FACT(sheet0,9);			// CHAT 2
		PRINT_GENERAL_BY_NAME_FACT(sheet0,12);			// BEP 1 + 2 + 3
		PRINT_GENERAL_BY_NAME_FACT(sheet0,15);			// FVL
		PRINT_GENERAL_BY_NAME_FACT(sheet0,18);			// FV - GIAY MAU
		PRINT_GENERAL_BY_NAME_FACT(sheet0,21);			// FV1
		PRINT_GENERAL_BY_NAME_FACT(sheet0,24);			// FV2
		PRINT_GENERAL_BY_NAME_FACT(sheet0,27);			// FV3
		PRINT_GENERAL_BY_NAME_FACT(sheet0,30);			// FV5
		//ADD THEM FVL.KS 06/2013, 06/07/2013, HA
		PRINT_GENERAL_BY_NAME_FACT(sheet0,33);			// FVL.KS
		//+ 3 cho moi doi tuong sau FVL.KS
		PRINT_GENERAL_BY_NAME_FACT(sheet0,36);			// MSHAN
		PRINT_GENERAL_BY_NAME_FACT(sheet0,39);			// TONG BO
		PRINT_GENERAL_BY_NAME_FACT(sheet0,42);			// MAY DAN
		PRINT_GENERAL_BY_NAME_FACT(sheet0,45);			// XT
		PRINT_GENERAL_BY_NAME_FACT(sheet0,49);          //Toan cong ty
		
		HANDLE_EXPORT_DETAIL(sheet1,date_input);	
		
		String check_fact = N_HEALTH_REPORT_MProgram.name_fact;
		
		if(check_fact == null || check_fact.equals("") || check_fact.equals("ALL")){
			int rowIndex 	=	48 ;
			int cellIndex 	= 	11;
			HSSFCell cellTotal = sheet0.getRow(rowIndex).getCell(cellIndex);
			Double total = cellTotal.getNumericCellValue();
			IGenericDAO<BaoHiemYT_SYS, String> dao = Application.getApp().getDao(BaoHiemYT_SYS.class);
		      BaoHiemYT_SYS a = new BaoHiemYT_SYS(2, date_input, total); // chu y truyen tham so cho dung loai BH nhu o duoi
		      try{
		    	  dao.save(a);
		      }catch(Exception e){
		    	  System.out.println("DA LUU DU LIEU TONG VAO DB RUI");
		    	  String temp = "";
		    	  if(userID.equals("ADMIN") && temp.equals("")){
		    		  dao.update(a);
		    	  }
		      }

		}
	}
	
	@Override
	protected void filterRow(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow header,
			HSSFRow row) {

	}

	@Override
	protected String createSQLStatement() {
		String sql = "SELECT * FROM DSPB00 where 1!= 1 " ;
		filterParams = new Object[0];//←須將filter清空 
		return sql;
	}
	
	private void HANDLE_EXPORT_DETAIL(HSSFSheet sheet_,Date date_input_) {
		
		OBJ_UTILITY obj_util = new OBJ_UTILITY();
		String empsn = "";
		String xuong	 = N_HEALTH_REPORT_MProgram.name_fact; //28/10/2011 Ngan them de xuat chi tiet theo tung xuong
		String date_str_ = sf.format(date_input_).substring(3,10);
		String  month_			= sf.format(date_input).substring(3,5);
		String  year_			= sf.format(date_input).substring(6,10);
		String sql1      		= N_HEALTH_REPORT_MProgram.str;
		String date_str_dk = sf.format(date_input_);
		String sql        = "";
		
		boolean flag_nv = false;
		
		//Begin 22/11/2012: Ngan them vao de xet xuat ky trinh theo bang moi hay bang cu duoc bk
		String sql_dk = 
				   "select dk_xuat_ky_trinh_thang_moi_cu('"+date_str_dk+"') as result from dual";
		Object dk        =    obj_util.Exe_Sql_Obj(sql_dk);
		//End 22/11/2012: Ngan them vao de xet xuat ky trinh theo bang moi hay bang cu duoc bk
		
		long salary_ = 0 ;
		/**
		 * ======== XUAT RA NHUNG NGUOI DI LAM BINH THUONG ====
		 * 	 	status = 1
		 */
		
	  if(Long.valueOf(dk.toString()) == 1)
	  {
		//String sql = 
           sql =
			"SELECT t.empsn," +
			"       em.fname,"+
			"       em.lname,"+
			"		t.status,"+
			"       d.id_dept,"+
			"		d.name_dept," +
			"		t.salary_m," + //luong xuat ky trinh vi neu lay salary_b se sai khi NS vao
			"		t.money," +
			"		t.note\n" +
			
			"FROM n_n_health_r t,n_employee em,n_department d\n" + 
			"WHERE   t.empsn = em.empsn\n" + 	
			" AND t.depsn = d.id_dept\n"+
			
			"\n" + 

			//"AND  ( ((t.status = 1) and (t.num >=1)) or ((t.status = 0) and (t.num >=1))"+
			"AND   (   ((t.status = 1 or t.status = 0) and t.num >= 1  and to_char(t.lock_date,'mm/yyyy') = '"+date_str_+"')\n" + //07/11/2011 Ngan them de chi xuat theo thang do
			
			"OR   (t.status =-1\n" + 
			"				 AND t.num >=1 " + 
			
			"                and to_char(t.lock_date,'mm/yyyy') = '"+date_str_+"'))"+
		    " and t.depsn <> '00000'" + sql1+
		//	" and t.empsn = '06080775' " +
		    
			" ";
	  }else // chi co dk> 1 vi <=0 da xet o dk nut xuat roi, k xet o day nua
	  {
		  sql =
					"SELECT t.empsn," +
					"       em.fname,"+
					"       em.lname,"+
					"		t.status,"+
					"       d.id_dept,"+
					"		d.name_dept," +
					"		t.salary_m," + //luong xuat ky trinh vi neu lay salary_b se sai khi NS vao
					"		t.money," +
					"		t.note\n" +
					
					"FROM n_n_health_r_"+year_+""+month_+" t,n_employee em,n_department d\n" + 
					"WHERE   t.empsn = em.empsn\n" + 	
					" AND t.depsn = d.id_dept\n"+
					
					"\n" + 

					//"AND  ( ((t.status = 1) and (t.num >=1)) or ((t.status = 0) and (t.num >=1))"+
					"AND   (   ((t.status = 1 or t.status = 0) and t.num >= 1  and to_char(t.lock_date,'mm/yyyy') = '"+date_str_+"')\n" + //07/11/2011 Ngan them de chi xuat theo thang do
					
					"OR   (t.status =-1\n" + 
					"				 AND t.num >=1 " + 
					

					"                and to_char(t.lock_date,'mm/yyyy') = '"+date_str_+"'))"+
				    " and t.depsn <> '00000'" + sql1+
				//	" and t.empsn = '06080775' " +
				    
					" ";
	  }
		
		List<Object[]> list_arr = obj_util.Exe_Sql_nfield_nrow(sql, 9);
	  
		for (int i = 0; i < list_arr.size(); i++) {
			Object[] arr_child = list_arr.get(i);
			for (int j = 0; j < arr_child.length; j++) {
				Object value_ = arr_child[j];
				switch (j) {
				case 0: 	// empsn
					empsn	= value_.toString();
					System.out.println(empsn + "("+i+")");
					
					Write_Cell_for_String(sheet_, value_, i+1, j);//old i
					if(empsn.equals("10080254")){
						System.out.println("Check");
					}
					break;
					
				case 1:     // Ho
					Write_Cell_for_String(sheet_, value_, i+1, j);
					break;	
					
				case 2:     //ten
					Write_Cell_for_String(sheet_, value_, i+1, j);
					break;	
					
				case 3:		// status
					System.out.println(value_);
					//if(value_.equals("NV.NGHÆ VIEÄC")){
					Write_Cell_for_Number(sheet_, value_, i+1, j);
					break;
				
				case 4:     // id_dept
					Write_Cell_for_String(sheet_, value_, i+1, j);
					break;
					
				case 5:     // name_dept
					Write_Cell_for_String(sheet_, value_, i+1, j);
					break;
				case 6:		// salary
					
						Write_Cell_for_Number(sheet_, value_, i+1, j); // lay trong n_health vi no nghi viec can phai nhan luong
					
					break;
					
				case 7:		// money
					Write_Cell_for_Number(sheet_, value_, i+1, j);
					break;
				case 8:		// note
					Write_Cell_for_String(sheet_, value_, i+1, j);
					break;

				default:
					break;
				}
			}
			
		} //the end : for(Object[] arr_info : list)
		
	}// the end : handle_export(HSSFSheet sheet0)
	

	
	/**
	 * i_row_ 		: begin sheet is i_row = 0
	 * j_column_  	: begin sheet is i_column = 0
	 * @param sheet_
	 * @param cell_value_ 	<=> data
	 * @param i_row_		<=> y
	 * @param j_column_ 	<=> x
	 */
	
	private void Write_Cell_for_Number(HSSFSheet sheet_,Object cell_value_,int i_row_,int j_column_){
		HSSFRow row_;
		HSSFCell cell_;
		cell_value_ = cell_value_ == null ? 0 : cell_value_;
		double value_ = 0;
		
		try{
			value_	= Double.valueOf(cell_value_.toString());

		}catch (Exception e) {
			value_ 	= 0;
		}
		
		
		try{
			row_ 	= sheet_.getRow(i_row_);
			if(row_ == null){
				row_ 	= sheet_.createRow(i_row_);
			}
		}catch (Exception e) {
			row_ 	= sheet_.createRow(i_row_);
		}
		try{
			cell_ 	= row_.getCell(j_column_);
			if(cell_ == null){
				cell_ 	= row_.createCell(j_column_);
				cell_.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			}
		}catch (Exception e) {
		
			cell_ 	= row_.createCell(j_column_);
			cell_.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		}
		
		cell_.setCellValue(value_);
		
	}
	
	private void Write_Cell_for_String(HSSFSheet sheet_,Object cell_value_,int i_row_,int j_column_){
		HSSFRow row_;
		HSSFCell cell_;
		cell_value_	= cell_value_ == null ? "" : cell_value_;
		String value_ = cell_value_.toString();
		
		try{
			
			row_ 	= sheet_.getRow(i_row_);
			if(row_ == null){
				row_ 	= sheet_.createRow(i_row_);
			}
			
		}
		catch (Exception e) {
			row_ 	= sheet_.createRow(i_row_);
		}
		
		try{
			
			cell_ 	= row_.getCell(j_column_);
			if(cell_ == null){
				cell_ 	= row_.createCell(j_column_);
				cell_.setCellType(HSSFCell.CELL_TYPE_STRING);
			}
			
		}catch (Exception e) {
			cell_ 	= row_.createCell(j_column_);
			cell_.setCellType(HSSFCell.CELL_TYPE_STRING);
		}
		
		
		cell_.setCellValue(value_);
	}
	
	
	private void PRINT_GENERAL_BY_NAME_FACT(HSSFSheet sheet_,int i_name){
		ArrayList<Object> obj_row_ ;
		INFO_GENERAL obj_general = new INFO_GENERAL();
		obj_general = Find_Data_General(i_name,date_input,obj_general);
		
		Write_Cell_for_Number(sheet_, obj_general.Get_SONGUOI_BHXH(), i_name, 4);
		Write_Cell_for_Number(sheet_, obj_general.Get_SONGUOI_TN(), i_name, 7);
		Write_Cell_for_Number(sheet_, obj_general.Get_SONGUOI(), i_name, 11);
		
		i_name++;
		
		Write_Cell_for_Number(sheet_, obj_general.Get_TONGLUONG_BHXH(), i_name, 4);
		Write_Cell_for_Number(sheet_, obj_general.Get_TONGLUONG_TN(), i_name, 7);
		Write_Cell_for_Number(sheet_, obj_general.Get_TONGLUONG(), i_name, 11);
		
		//Begin 31/10/2011 Ngan them de xuat ra luon so tien chuyen 4.5%
        i_name++;
		
		Write_Cell_for_Number(sheet_, obj_general.Get_TONGTIEN_BHYT(), i_name, 11);
		//End 31/10/2011 Ngan them de xuat ra luon so tien chuyen 4.5%
		
		i_name++;
		System.out.println(sheet_.getRow(i_name).getCell(11).toString());
		// ADD FVL.KS sau FV5 ke tu T06/2013, 06/07/2013, HA
		// + 3 cho moi dk, 45-->48
		if (i_name == 48) 
		{ i_name++;
		  Write_Cell_for_Total(sheet_, "L4+L7+L10+L13+L16+L19+L22+L25+L28+L31+L34+L37+L40+L43+L46", i_name, 11);
		}
		
		if (i_name == 49) 
		{ i_name++;
		  Write_Cell_for_Total(sheet_, "L5+L8+L11+L14+L17+L20+L23+L26+L29+L32+L35+L38+L41+L44+L47", i_name, 11);
		}
		
		if ((i_name == 50) || (i_name == 52) ) 
		{ i_name++;
		  Write_Cell_for_Total(sheet_, "L6+L9+L12+L15+L18+L21+L24+L27+L30+L33+L36+L39+L42+L45+L48", i_name, 11);
		}
	}

	private void Write_Cell_for_Total(HSSFSheet sheet_, String operate_str,
			int i_name, int i) {
		HSSFRow row = sheet_.getRow(i_name);
		HSSFCell cell = row.createCell(i);
		

		
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cell.setCellFormula(operate_str);
        cell.getCellStyle().setDataFormat(sheet_.getWorkbook().createDataFormat().getFormat("#,###"));


		
	}
	


	private INFO_GENERAL Find_Data_General(int i_name,Date date_input_,INFO_GENERAL obj_general) {
		
		String field_value1_ = "";
		
		String  month_			= sf.format(date_input).substring(3,5);
		String  year_			= sf.format(date_input).substring(6,10);
		
		
		String xuong = "";
		String str_date_		= sf.format(date_input_).substring(3, 10);
		String str_date_1		="01/"+sf.format(date_input_).substring(3, 10);
		int so_nguoi_bhxh_ 	= 0;
		int so_nguoi_bhtn_	= 0;
		int so_nguoi_bhyt_	= 0;
		
		
		
		long luong_bhxh_	= 0;
		long luong_bhtn_	= 0;
		long luong_bhyt_	= 0;
        double tien_bhyt_		= 0;//31/10/2011 
        String date_str_dk = sf.format(date_input_);
        String sql_yt		= "";
        
    	//Begin 22/11/2012: Ngan them vao de xet xuat ky trinh theo bang moi hay bang cu duoc bk
		String sql_dk = 
				   "select dk_xuat_ky_trinh_thang_moi_cu('"+date_str_dk+"') as result from dual";
		Object dk        =    obj_util.Exe_Sql_Obj(sql_dk);
		//End 22/11/2012: Ngan them vao de xet xuat ky trinh theo bang moi hay bang cu duoc bk
		
		String sql_xh		= "";
		String sql_tn		= "";
		
		if(Long.valueOf(dk.toString()) == 1)
		{
	        sql_yt		= 
							" SELECT count(h.empsn),nvl(sum(h.salary_m),0)\n" +
						//	" FROM n_n_health_r h,n_get_data e, n_department d\n" + 
						    " FROM n_n_health_r h, n_department d\n" +
							//"WHERE h.empsn = e.empsn\n" + 
							"WHERE  h.depsn = d.id_dept\n" + 
							"     -- ((BT || NS) && num > 1 ) || (NV && lock_date = date_now)\n" + 
						//	"AND   (   ((h.status = 1 or h.status = 0) and num >= 1 ) \n" + 
							"AND   (   ((h.status = 1 or h.status = 0) and num >= 1  and to_char(h.lock_date,'mm/yyyy') = '"+str_date_+"')\n" + //07/11/2011 Ngan them de chi xuat theo thang do
							"OR ( h.status = -1 and h.num >=1 and to_char(h.lock_date,'mm/yyyy') = '"+str_date_+"'))\n" + 
							" ";
	//	" and h.empsn = '06080775' " ;

		 }else // chi co dk> 1 vi <=0 da xet o dk nut xuat roi, k xet o day nua
		 {
			 sql_yt		= 
						" SELECT count(h.empsn),nvl(sum(h.salary_m),0)\n" +
					//	" FROM n_n_health_r h,n_get_data e, n_department d\n" + 
					    " FROM n_n_health_r_"+year_+""+month_+" h, n_department d\n" +
						//"WHERE h.empsn = e.empsn\n" + 
						"WHERE  h.depsn = d.id_dept\n" + 
						"     -- ((BT || NS) && num > 1 ) || (NV && lock_date = date_now)\n" + 
					//	"AND   (   ((h.status = 1 or h.status = 0) and num >= 1 ) \n" + 
						"AND   (   ((h.status = 1 or h.status = 0) and num >= 1  and to_char(h.lock_date,'mm/yyyy') = '"+str_date_+"')\n" + //07/11/2011 Ngan them de chi xuat theo thang do
						"OR ( h.status = -1 and h.num >=1 and to_char(h.lock_date,'mm/yyyy') = '"+str_date_+"'))\n" + 
						" ";
//	" and h.empsn = '06080775' " ;
		 }
		

		switch (i_name) {
		case 3:			// DAI DE	
					
					sql_yt = sql_yt +		// di lam binh thuong va lay don vi trong n_employee
							" AND   d.name_fact = 'FVLS'";
				
			break;
		case 6:			// X.DAO CHAT 1
			    
				sql_yt = sql_yt +
							" AND   d.id_dept 	= 'W0008'";
				
			break;
		case 9:			// X.DAO CHAT 2
			    
				sql_yt = sql_yt +
							" AND   d.id_dept in( 'W0007','W0009' )";
				
				
			break;
		case 12:		// BEP 1 + 2 + 3
			   
				sql_yt = sql_yt +
							" AND   d.id_dept in ( '00001','00002','00003' )";
				
			
			break;
		case 15:		// FVL
			    
				sql_yt = sql_yt +
							" AND   (d.name_dept like 'FVL.F12%' or d.id_dept = 'KHODE')" ;
				
				
			break;
		case 18:		// FVL - GIAY MAU
			   
				sql_yt = sql_yt +
							" AND (d.name_dept like 'FVL.GM%' or d.name_dept like 'FVL.F3-GM%')";
				
				
			break;
		case 21:		// FV1
			   
				sql_yt = sql_yt +
							" AND d.name_dept like  'FVL.F1%'" +
							" AND d.name_dept not like  'FVL.F12%'" +
							" AND d.id_dept <> 'KHODE'";
				
				
			break;
		case 24:		// FV2
			
			sql_yt = sql_yt +
							" AND d.name_dept like  'FVL.F2%'" ;
			
			
			break;

		case 27:		// FV3
			
			sql_yt = sql_yt +
							" AND d.name_dept like 'FVL.F3%'" +
							" AND d.name_dept not like 'FVL.F3-GM%'";
			
			
			break;
		case 30:		// FV5
			
			sql_yt = sql_yt +
							" AND d.name_dept like 'FVL.F5%'" ;
			
			
			break;


		case 33:		// FVL.KS, BD 06/2013, UPDATE 06/07/2013, HA 
			
			sql_yt = sql_yt +
							" AND d.name_dept like  'FVL.KS%'";
			
			
			break;
			
		case 36:		// MSHAN
			
			sql_yt = sql_yt +
							" AND   d.id_dept 	= 'MS001'";
			
			
			break;

		case 39:		// TONG BO
			
			sql_yt = sql_yt +
							" AND d.name_fact = 'TB'" +
							" AND d.id_dept not in ('MS001','00001','00002','00003') " +
							"";
			
			
			break;

		case 42:		// MAY DAN
			
			sql_yt = sql_yt +
							" AND d.name_group = 'BGC'" ;
			
			
			break;
		case 45:		// XUONG THEU
			
			sql_yt = sql_yt +
							" AND d.name_group = 'XT'" ;
			
			break;
			
		default:
			break;
		}
		
		
		
		// BHXH
		obj_general.Set_SONGUOI_BHXH(so_nguoi_bhxh_);
		obj_general.Set_TONGLUONG_BHXH(luong_bhxh_);
		
		// BHTN
		obj_general.Set_SONGUOI_TN(so_nguoi_bhtn_);
		obj_general.Set_TONGLUONG_TN(luong_bhtn_);
		
		// BHYT
		
		List<Object> list_yt = obj_util.Exe_sql_nfield_1row(sql_yt, 2);
		
		if(list_yt != null){
			so_nguoi_bhyt_ 	= obj_general.Get_SONGUOI() + Integer.valueOf(list_yt.get(0).toString());
			luong_bhyt_		= obj_general.Get_TONGLUONG() + Long.valueOf(list_yt.get(1).toString());
			tien_bhyt_      = luong_bhyt_*0.045;
			
		}
		
	
		
		
		obj_general.Set_SONGUOI(so_nguoi_bhyt_);
		obj_general.Set_TONGLUONG(luong_bhyt_);
		obj_general.Set_TONGTIEN_BHYT(tien_bhyt_);
		
		
		
		
		return obj_general;
	}

}

class INFO_GENERAL{
	private int _so_nguoi;
	private long _tong_luong;
	private int _count;
	private double _tong_tien_bhyt;//31/10/2011
	
	private int _so_nguoi_bhxh;
	private long _tong_luong_bhxh;
	
	private int _so_nguoi_that_nghiep;
	private long _tong_luong_that_nghiep;
	
	
	
	
	public INFO_GENERAL(){
		
	}
	
	public Object Get_TONGTIEN() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object Get_TONGTIEN_TN() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object Get_TONGTIEN_BHXH() {
		// TODO Auto-generated method stub
		return null;
	}

	public int Get_SONGUOI_BHXH(){
		return _so_nguoi_bhxh;
	}
	public void Set_SONGUOI_BHXH(int songuoi){
		this._so_nguoi_bhxh = songuoi;
	}
	
	public int Get_SONGUOI_TN(){
		return _so_nguoi_that_nghiep;
	}
	
	public void Set_SONGUOI_TN(int songuoi){
		this._so_nguoi_that_nghiep = songuoi;
	}
	
	public long Get_TONGLUONG_BHXH(){
		return _so_nguoi_bhxh;
	}
	
	public void Set_TONGLUONG_BHXH(long tongluong_){
		this._tong_luong_bhxh = tongluong_;
	}
	
	public long Get_TONGLUONG_TN(){
		return _so_nguoi_that_nghiep;
	}
	
	public void Set_TONGLUONG_TN(long tongluong_){
		this._tong_luong_that_nghiep = tongluong_;
	}
	
	public int Get_SONGUOI(){
		return _so_nguoi;	
	}
	public void Set_SONGUOI(int songuoi){
		this._so_nguoi = songuoi;
	}
	
	public long Get_TONGLUONG(){
		return _tong_luong;
	}
	
	public void Set_TONGLUONG(long tongluong){
		this._tong_luong = tongluong;
	}
	
	public int Get_COUNT(){
		return _count;
	}
	
	public void Set_COUNT(int count){
		this._count = count;
	}
	
	public double Get_TONGTIEN_BHYT(){
		return _tong_tien_bhyt;
	}
	
	public void Set_TONGTIEN_BHYT(double tongtien_bhyt){
		this._tong_tien_bhyt = tongtien_bhyt;
	}
}
