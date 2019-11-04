package ds.program.fvhr.ngan.ui.retain_salary;

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

import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import fv.util.Vni2Uni;
import ds.program.fvhr.ngan.ui.ExportAbstract_N;
import ds.program.fvhr.son.ui.DeptUserControl;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.echo2app.ReportFileManager;

public class N_Emp_Retain_Salary_Export extends ExportAbstract_N {


	public HSSFWorkbook data_excel_Performed() {
		   HSSFWorkbook wb = null;

		OBJ_UTILITY obj_util = new OBJ_UTILITY();
		
		
		String empsn = "";
		String 	fact_ 			= myDeptControl.getFact();
		String 	group_			= myDeptControl.getGroup();
		String	dept_			= Vni2Uni.convertToVNI(myDeptControl.getNameDept());
		Date	date_			= myDeptControl.getDate()== null? new Date():myDeptControl.getDate();
	
		SimpleDateFormat sf_	= new SimpleDateFormat(ObjUtility.DATE_FORMAT_STR);
		String	date_str		= sf_.format(date_).substring(3, 10);
			
		long retain_salary_ = 0 ;
		
		String sql = 

					"select t.empsn,e.fname||' '||e.lname as HoTen,d.id_dept,d.name_dept,t.re_salary,t.date_effect \n" +
					"from n_emp_retain_salary t,n_department d,n_employee e \n" + 
					"where         t.empsn = e.empsn \n" + 
					"      and     t.depsn = d.id_dept\n" + 
				//	"      and     gd.months||'/'||gd.years = '"+date_str+"'"+
					"      and     d.name_fact     like '%"+fact_.trim()+ "'\n" + 
					"      and     d.name_group    like '%"+group_.trim()+"'\n" + 
					"      and     d.name_dept_name     like '%"+dept_.trim()+"'\n" + 
					"      and     to_char(t.date_effect,'mm/yyyy')   = '"+date_str+"'"

					+ "";
	
		
		List<Object[]> list_arr = obj_util.Exe_Sql_nfield_nrow(sql, 6);

		try {	// get data and export into Workbook
			
			File f = ReportFileManager.getReportFormatFolder("fvhr");
			InputStream in 		= new FileInputStream(new File(f.getPath(), "ds_ungluong.xls"));
			wb = new HSSFWorkbook(in);
			HSSFSheet 	sheet	= wb.getSheetAt(0);
			HSSFRow		row;
			HSSFCell	cell;
			
			row				= sheet.createRow(0);
				cell		= row.createCell(0);
								cell.setCellValue("SO THE     ");
				cell		= row.createCell(1);
								cell.setCellValue("HO TEN     ");				
				cell		= row.createCell(2);
								cell.setCellValue("MA DON VI");
				cell		= row.createCell(3);
								cell.setCellValue("TEN DON VI");	
				cell		= row.createCell(4);				
								cell.setCellValue("LUONG THANG TRUOC ");
				cell		= row.createCell(5);
								cell.setCellValue("THANG HIEU LUC ");
			
			
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
					case 4:	// LUONG THANG TRUOC
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Double.valueOf(ele.toString()));
						break;
					case 5:	//THANG HIEU LUC
						
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
