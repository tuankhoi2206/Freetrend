package ds.program.fvhr.ngan.ui.bonus;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.ngan.ui.ExportAbstract_N;
import ds.program.fvhr.util.OBJ_UTILITY;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.ReportFileManager;

public class N_Emp_Bonus_WPExport extends ExportAbstract_N {

	
	String user_up = Application.getApp().getLoginInfo().getUserID();
	String ma_user = "";
	
	public HSSFWorkbook data_excel_Performed() {
		
		IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();
		
		HSSFWorkbook wb = null;

		OBJ_UTILITY obj_util = new OBJ_UTILITY();
		
		
		
		String 	fact_ 			= myDeptControl.getFact();
		String 	group_			= myDeptControl.getGroup();
		String	dept_			= myDeptControl.getIDDept();
		Date	date_			= myDeptControl.getDate()== null? new Date():myDeptControl.getDate();	
		SimpleDateFormat sf_	= new SimpleDateFormat(OBJ_UTILITY.DATE_FORMAT_STR);
		String	date_str		= sf_.format(date_).substring(3, 10);
		String  month_str		= sf_.format(date_).substring(3,5);
		String  year_str		= sf_.format(date_).substring(6,10);
		String sql = 

					"select t.empsn," +
					"       em.fname,"+
					"       em.lname,"+
					"       d.id_dept,"+
					"       d.name_fact,"+
					"       d.name_group,"+
					"		d.name_dept_name,"+
					"		t.bonus1," +
					"		t.bonus5," +
					"		t.bonus8," +
					"		t.date_effect \n" +
					" from n_emp_bonus t,n_employee em,n_department d\n" + 
					" where         t.empsn = em.empsn\n" + 
					"      and     t.depsn = d.id_dept\n" + 
					"      and     d.name_fact     like '%"+fact_.trim()+ "'\n" + 
				//	"      and     d.name_group    like '%"+group_.trim()+"'\n" + 
				//	"      and     d.id_dept       like '%"+dept_.trim()+"'\n" + 
					"      and     to_char(t.date_effect,'mm/yyyy')   like '%"+date_str+"'"+
					" AND EM.USER_MANAGE_ID in (SELECT MA_QL FROM N_USER_LIMIT WHERE MA_USER= '"+ 
  		          	""+ma_user+"' and MA_QL=USER_MANAGE_ID)"+
					"\n" //

					+ "";
		
		
		List<Object[]> list_arr = obj_util.Exe_Sql_nfield_nrow(sql, 11);

		try {	// get data and export into Workbook
			
			File f = ReportFileManager.getReportFormatFolder("dshr");
			InputStream in 		= new FileInputStream(new File(f.getPath(), "ds_ungluong.xls"));
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
								cell.setCellValue("TEN DON VI  ");
				cell		= row.createCell(7);
								cell.setCellValue("TIEN THUONG ");
				cell		= row.createCell(8);
								cell.setCellValue("BU LUONG   ");
				cell		= row.createCell(9);
								cell.setCellValue("PC XD   ");
				cell		= row.createCell(10);
								cell.setCellValue("THANG HIEU LUC   ");
			
			
			int n_row	= 1;
			int n_col	= 0;
			for(Object[] arr : list_arr){
				
				row		= sheet.createRow(n_row);
				n_col	= 0;
				for(Object ele : arr){
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
							if(ele.toString().equals("0"))
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
					case 7:	// bonus1
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Double.valueOf(ele.toString()));
						break;
					case 8:	// bonus5
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Double.valueOf(ele.toString()));
						break;
					case 9:	// bonus8
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Double.valueOf(ele.toString()));
						break;
					case 10:	// date_effect
						
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

}
