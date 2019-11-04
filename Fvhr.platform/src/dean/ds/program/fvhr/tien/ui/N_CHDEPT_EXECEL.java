package ds.program.fvhr.tien.ui;

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

import fv.util.ApplicationHelper;
import fv.util.DbUtils;
import fv.util.Vni2Uni;
import ds.program.fvhr.son.ui.ExportAbstract;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.ReportFileManager;
import dsc.util.function.UUID;

public class N_CHDEPT_EXECEL extends ExportAbstract {



	SimpleDateFormat sp=new SimpleDateFormat("dd/MM/yyyy");
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
		cell.setCellValue(Vni2Uni.convertToVNI("Ngày Điều Động"));	
		
		cell = row.createCell(2);	
		cell.setCellValue(Vni2Uni.convertToVNI("Đơn Vị Mới"));
		
		cell = row.createCell(3);	
		cell.setCellValue(Vni2Uni.convertToVNI("Đơn Vị Cũ"));
		
		cell = row.createCell(4);	
		cell.setCellValue(Vni2Uni.convertToVNI("Mã Quản Lý Mới"));
		
		cell = row.createCell(5);	
		cell.setCellValue(Vni2Uni.convertToVNI("Mã Quản Lý Cũ"));
		
		cell = row.createCell(6);	
		cell.setCellValue(Vni2Uni.convertToVNI("Trạng Thái Sản Xuất Cũ"));
		
		cell = row.createCell(7);	
		cell.setCellValue(Vni2Uni.convertToVNI("Trạng Thái Sản Xuất Mới"));
		
		sql	= 
				" SELECT CH.EMPSN,CH.DATES,CH.DEPSN,CH.DEPSN_OLD,CH.MAQL,CH.MAQL_OLD,CH.TRUCTIEP_SX_OLD,CH.TRUCTIEP_SX_NEW 	"	+	
				" FROM N_CHDEPSN CH, N_EMPLOYEE E  WHERE E.EMPSN =CH.EMPSN" ;
				
        		
		if(myDeptControl.getFact().equals("")){
			return wb;
		}
		if(!myDeptControl.getEmpsn().equals(""))
		{
			sql=sql+"AND CH.EMPSN='"+myDeptControl.getEmpsn()+"'";
		}
		else{
			    sql = sql + " AND E.DEPSN IN (SELECT D.ID_DEPT FROM N_DEPARTMENT D WHERE  D.NAME_FACT= '" +myDeptControl.getFact() +"'";
			    
			    if(!myDeptControl.getGroup().trim().equals("")){
			    	 sql = sql + " and d.name_group = '" +myDeptControl.getGroup() +"'" ;
			    }
			    
			    if(!myDeptControl.getIDDept().trim().equals("")){
			    	 sql = sql + " and d.id_dept = '" +myDeptControl.getIDDept() +"'" ;
			    }
			    String listVungQL = DbUtils.parseInStringParamValues(ApplicationHelper.getRightList());
			    sql=sql + " and E.USER_MANAGE_ID in "+ listVungQL;
			    sql=sql+")";
			    if(myDeptControl.getDate()!=null)
			    {
			    	Date dtDateDD=myDeptControl.getDate();
			    	String strDateDD=sp.format(dtDateDD);
			    	System.out.println(strDateDD);
			    	sql=sql+"AND TO_CHAR(CH.DATES,'DD/MM/YYYY')='"+strDateDD+"'";
			    }
			    
		}
			
				ArrayList<Object[]> listInfo = obj_util.Exe_Sql_nfield_nrow(sql, 8);
				
				for ( int i=0; i< listInfo.size() ; i++ ){
					
					Object[] objEmpsn =	listInfo.get(i);
					row = sheet.createRow(i+1);
					
					cell = row.createCell(0);	
					cell.setCellValue(objEmpsn[0].toString()); // EMSPN
					
					cell = row.createCell(1);
					cell.setCellStyle(dateHSSFCell(wb));
					cell.setCellValue((Date)objEmpsn[1]); 
					
					cell = row.createCell(2);	
					cell.setCellValue(objEmpsn[2].toString()); // EMSPN
					
					cell = row.createCell(3);	
					cell.setCellValue(objEmpsn[3].toString()); // EMSPN
					
					cell = row.createCell(4);	
					cell.setCellValue(objEmpsn[4].toString()); // EMSPN
					
					// EMSPN
					
					cell = row.createCell(5);	
					cell.setCellValue(objEmpsn[5].toString()); // EMSPN
					
					cell = row.createCell(6);
					cell.setCellValue(objEmpsn[6].toString());// EMSPN
					
				
					cell = row.createCell(7);
					cell.setCellValue(objEmpsn[7].toString());// EMSPN
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
