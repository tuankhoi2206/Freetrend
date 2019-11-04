package ds.program.fvhr.ngan.ui.Bonus_End_Of_Year;

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

import fv.util.Vni2Uni;
import ds.program.fvhr.son.ui.ExportAbstract;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.echo2app.ReportFileManager;

public class N_Emp_Bonus_End_Of_Year_Export extends ExportAbstract {


	public HSSFWorkbook data_excel_Performed() {
		HSSFWorkbook wb = null;

		ObjUtility obj_util = new ObjUtility();
		
		
		String empsn = "";
		String 	fact_ 			= myDeptControl.getFact();
		String 	group_			= myDeptControl.getGroup();
		String	dept_			= Vni2Uni.convertToVNI(myDeptControl.getNameDept());
		Date	date_			= myDeptControl.getDate()== null? new Date():myDeptControl.getDate();
	
		SimpleDateFormat sf_	= new SimpleDateFormat(ObjUtility.DATE_FORMAT_STR);
		String	date_str		= sf_.format(date_).substring(3, 10);
			
		String status = "" ;
		
		String sql = 

					"select t.empsn,e.fname||' '||e.lname as HoTen,d.id_dept,d.name_dept,t.bonus_m13,t.year,t.status,t.date_effect \n" +
					"from n_emp_bonus_end_of_year t,n_get_data gd,n_department d,n_employee e \n" + 
					"where         t.empsn = e.empsn and t.empsn = gd.empsn\n" + 
					"      and     gd.depsn = d.id_dept\n" + 
					"      and     gd.months||'/'||gd.years = '"+date_str+"'"+
					"      and     d.name_fact     like '%"+fact_.trim()+ "'\n" + 
					"      and     d.name_group    like '%"+group_.trim()+"'\n" + 
					"      and     d.name_dept     like '%"+dept_.trim()+"'\n" + 
					"      and     to_char(t.date_effect,'mm/yyyy')   = '"+date_str+"'"

					+ "";
	
		
		List<Object[]> list_arr = obj_util.Exe_Sql_nfield_nrow(sql, 8);

		try {	// get data and export into Workbook
			
			File f = ReportFileManager.getReportFormatFolder("fvhr");
			InputStream in 		= new FileInputStream(new File(f.getPath(), "ds_ungluong.xls"));
			wb = new HSSFWorkbook(in);
			HSSFSheet 	sheet	= wb.getSheetAt(0);
			HSSFRow		row;
			HSSFCell	cell;
			
			row				= sheet.createRow(0);
				cell		= row.createCell(0);
								cell.setCellValue("SOÁ THEÛ     ");
				cell		= row.createCell(1);
								cell.setCellValue("HOÏ TEÂN     ");				
				cell		= row.createCell(2);
								cell.setCellValue("MAÕ ÑÔN VÒ");
				cell		= row.createCell(3);
								cell.setCellValue("TEÂN ÑÔN VÒ");	
				cell		= row.createCell(4);				
								cell.setCellValue("TIEÀN THÖÔÛNG T13");
			    cell		= row.createCell(5);				
								cell.setCellValue("CUÛA NAÊM");	
				cell		= row.createCell(6);				
								cell.setCellValue("TRAÏNG THAÙI");				
				cell		= row.createCell(7);
								cell.setCellValue("THAÙNG TÍNH LÖÔNG");
			
			
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
					case 4:	// TIEN THUONG T13
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Double.valueOf(ele.toString()));
						break;
					case 5:	// TIEN THUONG T13 CUA NAM NAO?
						cell.setCellValue(ele.toString());
						break;
					case 6:	// TRANG THAI
						status = ele.toString();
						if (status.equals("Y")){
							cell.setCellValue("Coäng chung vôùi thaùng tính löông");
						}else{
							cell.setCellValue("Phaùt rieâng ôû ngoaøi");
						}
						break;
					case 7:	//THANG TINH LUONG
						
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
			ObjUtility.ShowMessageError(e.getMessage());
		}
		
		
		return wb;
	}

}
