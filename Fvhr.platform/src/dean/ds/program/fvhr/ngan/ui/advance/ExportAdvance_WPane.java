package ds.program.fvhr.ngan.ui.advance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import fv.util.JdbcDAO;
import fv.util.Vni2Uni;
import ds.program.fvhr.ngan.ui.DeptUserControl_N;
import ds.program.fvhr.ngan.ui.ExportAbstract_N;
import ds.program.fvhr.util.OBJ_UTILITY;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.task.ATask;
import dsc.util.function.UUID;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;

public class ExportAdvance_WPane extends ExportAbstract_N {
	
	private boolean _checked;
	String user_up = Application.getApp().getLoginInfo().getUserID();
	String ma_user = "";
	public ExportAdvance_WPane(boolean a) {
		_checked = a;
		IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();
	}
	
	

	public HSSFWorkbook data_excel_Performed() {
		JdbcDAO dao = new JdbcDAO();
		HSSFWorkbook wb = null;
		
		OBJ_UTILITY obj_util = new OBJ_UTILITY();
		
		String empsn = "";
		String 	fact_ 			= myDeptControl.getFact();
		String 	group_			= myDeptControl.getGroup();
		String	dept_			= Vni2Uni.convertToVNI(myDeptControl.getNameDept());
		//java.util.Date	date_	= my_dept_select.Get_date();	
		
		
		java.util.Date	date_			= myDeptControl.getDate()== null? new java.util.Date():myDeptControl.getDate();
		SimpleDateFormat sf_	= new SimpleDateFormat(OBJ_UTILITY.DATE_FORMAT_STR);
		String	date_str		= sf_.format(date_).substring(3, 10);
		
		int		status	= _checked == true? 1 : 0; 
		Connection con 	= Application.getApp().getConnection();
		CallableStatement stm_call;
		
		String sql = 
				//	" Select ei.empsn from n_emp_info ei, n_department d, n_employee e\n" + //old
			        " Select e.empsn,e.fname||' '||e.lname as HoTen,d.id_dept,d.name_dept,ei.advance" +
			        " from n_emp_info ei, n_department d, n_employee e \n" +
					" 		where     ei.empsn        = e.empsn\n" + 
					"       and e.depsn         = d.id_dept\n" + 
					"       and ei.advance      = " + status + "\n" + 
					"       and d.name_fact     like'"+fact_.trim()+"%'\n" + 
					"       and d.name_group    like '"+group_.trim()+"%'\n" + 
					"       and d.name_dept_name like '"+dept_.trim()+"%'"+
                    " AND E.USER_MANAGE_ID in (SELECT MA_QL FROM N_USER_LIMIT WHERE MA_USER= '"+ 
			    		          ""+ma_user+"' and MA_QL=E.USER_MANAGE_ID)"+
					 "\n";
		
		
	//	List<Object[]> list_arr = obj_util.Exe_Sql_nfield_nrow(sql, 5); //old
		
		 List<Object[]> list_arr = dao.getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<Object[]>() {
				@Override
				public Object[] mapRow(ResultSet rs, int arg1)
						throws SQLException {
					Object[] obj = new Object[5];
					for (int i=1;i<=5;i++){
						obj[i-1]=rs.getObject(i);
					}
					return obj;
				}
			}, new Object[]{});
			 System.out.println(sql + " >>> " + list_arr.size());
		try {	// get data and export into Workbook
			
			File f = ReportFileManager.getReportFormatFolder("fvhr");
			InputStream in 		= new FileInputStream(new File(f.getPath(), "ds_ungluong.xls"));
			wb 	= new HSSFWorkbook(in);
			HSSFSheet 	sheet	= wb.getSheetAt(0);
			HSSFRow		row;
			HSSFCell	cell;
			int 		n_row	= 1;
			int 		n_col	= 0;
			
			/*Date date_sql 			= new Date(date_.getTime());
			String note				= "";
			long 	money			= 0;
			
			String sql_call	= "{?=call S_GET_ADVANCE_SALARY(?,?,?)}";
			stm_call = con.prepareCall(sql_call);
			stm_call.registerOutParameter(1, Types.NUMERIC);
			stm_call.registerOutParameter(4,Types.VARCHAR);*/
			
		for(Object[] arr : list_arr){	
			row		= sheet.createRow(n_row);
			n_col   = 0;
			for(Object ele : arr){
			
				cell	= row.createCell(n_col);
				switch (n_col) {
				case 0:	// empsn
					empsn	= ele.toString();
					cell.setCellValue(ele.toString());
					
				/*	stm_call.setString(2, empsn);
					stm_call.setDate(3, date_sql);
					
					stm_call.execute();
					
					money 	= stm_call.getLong(1);
					note	= stm_call.getString(4);
					note	= note == null ? "" : note;*/
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
				case 4: //TRANG THAI
					if(status == 1){
						cell.setCellValue("Coù ñaêng kyù öùng löông");
					}else{
						cell.setCellValue("khoâng ñaêng kyù öùng löông");
					}
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
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return wb;
		
	}
}
