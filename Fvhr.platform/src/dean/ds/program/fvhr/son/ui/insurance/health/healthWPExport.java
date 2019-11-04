package ds.program.fvhr.son.ui.insurance.health;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import fv.util.Vni2Uni;
import ds.program.fvhr.son.ui.ExportAbstract;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.ReportFileManager;
import dsc.util.function.UUID;

public class healthWPExport extends ExportAbstract {



	public HSSFWorkbook data_excel_Performed() {


		HSSFWorkbook wb = null;
		File f = ReportFileManager.getReportFormatFolder("dshr");
		InputStream in;
		try {
			in = new FileInputStream(new File(f.getPath(), "phan_cong_cong_viec.xls"));
			wb = new HSSFWorkbook(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		
		String sql				= "";
		ObjUtility	obj_util	= new ObjUtility();
		int r					= 0;
		String fact 			= myDeptControl.getFact();
		String group			= myDeptControl.getGroup();
		String dept				= myDeptControl.getIDDept();
		
		row = sheet.createRow(0);
		cell = row.createCell(0);	
		cell.setCellValue(Vni2Uni.convertToVNI("Số Thẻ"));	
		
		cell = row.createCell(1);	
		cell.setCellValue(Vni2Uni.convertToVNI("Họ"));	
		
		cell = row.createCell(2);	
		cell.setCellValue(Vni2Uni.convertToVNI("Tên"));
		
		cell = row.createCell(3);	
		cell.setCellValue(Vni2Uni.convertToVNI("CMND"));
		
		cell = row.createCell(4);	
		cell.setCellValue(Vni2Uni.convertToVNI("Ngày Nhập Xưởng"));
		
		cell = row.createCell(5);	
		cell.setCellValue(Vni2Uni.convertToVNI("Số BHYT"));
		
		cell = row.createCell(6);	
		cell.setCellValue(Vni2Uni.convertToVNI("Ngày mua"));
		
		cell = row.createCell(7);	
		cell.setCellValue(Vni2Uni.convertToVNI("Ngày hết hạn"));
		
		sql	= 
				" SELECT  e.empsn, e.fname, e.lname, e.id_no,e.date_hired, " + //5
				"		  h.id_health , h.dates, h.expire	"	+	
				" FROM n_employee e,n_department d,n_health h \n" +
				" WHERE         e.depsn       = d.id_dept\n " +    
				"		and 	e.empsn		  = h.empsn		" +	
				"		and 	h.clock	= '1'"	+
        		"		"		;
		if(myDeptControl.getFact().equals("")){
			return wb;
		}else{
			    sql = sql + " and d.name_fact = '" +myDeptControl.getFact() +"'" ;
			    
			    if(!myDeptControl.getGroup().trim().equals("")){
			    	 sql = sql + " and d.name_group = '" +myDeptControl.getGroup() +"'" ;
			    }
			    
			    if(!myDeptControl.getIDDept().trim().equals("")){
			    	 sql = sql + " and d.id_dept = '" +myDeptControl.getIDDept() +"'" ;
			    }
			    
		}
			
				ArrayList<Object[]> listInfo = obj_util.Exe_Sql_nfield_nrow(sql, 8);
				
				for ( int i=0; i< listInfo.size() ; i++ ){
					
					Object[] objEmpsn =	listInfo.get(i);
					row = sheet.createRow(i+1);
					
					cell = row.createCell(0);	
					cell.setCellValue(objEmpsn[0].toString()); // EMSPN
					
					cell = row.createCell(1);	
					cell.setCellValue(objEmpsn[1].toString()); // EMSPN
					
					cell = row.createCell(2);	
					cell.setCellValue(objEmpsn[2].toString()); // EMSPN
					
					cell = row.createCell(3);	
					cell.setCellValue(objEmpsn[3].toString()); // EMSPN
					
					cell = row.createCell(4);
					cell.setCellStyle(dateHSSFCell(wb));
					cell.setCellValue((Date)objEmpsn[4]); // EMSPN
					
					cell = row.createCell(5);	
					cell.setCellValue(objEmpsn[5].toString()); // EMSPN
					
					cell = row.createCell(6);
					cell.setCellStyle(dateHSSFCell(wb));
					cell.setCellValue((Date)objEmpsn[6]); // EMSPN
					
					System.out.println(objEmpsn[7]);
					cell = row.createCell(7);
					cell.setCellStyle(dateHSSFCell(wb));
					if(!objEmpsn[7].toString().equals("0")){
						cell.setCellValue((Date)objEmpsn[7]); // EMSPN
					}
				}
					
		
		
		return	wb;
	
	}
	
	private HSSFCellStyle dateHSSFCell(HSSFWorkbook wb){
		HSSFCellStyle cellStype = wb.createCellStyle();
		HSSFDataFormat dfcell =  wb.createDataFormat();
					cellStype.setDataFormat(dfcell.getFormat("dd/mm/yyyy"));
		
		return cellStype;
	}
	

}
