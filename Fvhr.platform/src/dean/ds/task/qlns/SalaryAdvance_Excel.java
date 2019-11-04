package ds.task.qlns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.domain.N_N_EMP_BORM;
import ds.program.fvhr.ui.N_EMP_BORMMProgram;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.task.AListReportTask;


public class SalaryAdvance_Excel extends AListReportTask {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  OBJ_UTILITY obj_util;
  OBJ_EMPSN obj_empsn;
  private String name_fact = "";
  private String name_group = "";
  private String name_dept = "";
  private boolean status_advance = true;
  SimpleDateFormat sf = OBJ_UTILITY.Get_format_date();
  IGenericDAO<N_N_EMP_BORM, String> obj_dao;
  N_N_EMP_BORM obj_update = null;

  /**
   * status  	= true 	: danh sach co ung luong
   * status	= false	: danh sach khong dang ky ung (N) + khong du ngay cong de ung luong  
   * @param status
   * @return
   */

  private List<String> getDataAdvance(boolean status) {
    ArrayList<String> list = null;
    Date date = new Date();
    String sql = "";
    String date_str = sf.format(date);
    if (status) { // co ung luong
      sql = "	select eb.empsn " + "	from n_n_emp_borm eb,n_employee e,n_department d " + "	where eb.empsn = e.empsn "
          + "	and e.depsn = d.id_dept " + "	and d.name_fact ='" + name_fact + "' " + "	and d.name_group like '%"
          + name_group + "' " + " 	and d.name_dept_name like '%" + name_dept + "' " + "	and eb.status = 'Y' "
          + "	and to_char(eb.in_date,'mm/yyyy') = '06/2011'";

    } else { // khong ung luong
      sql = "	SELECT eb.empsn " + "	FROM n_n_emp_borm eb,n_employee e,n_department d " + "	WHERE eb.empsn = e.empsn "
          + "		AND e.depsn = d.id_dept " + "		AND d.name_fact ='" + name_fact + "' " + "		AND d.name_group like '%"
          + name_group + "' " + " 		AND d.name_dept_name like '%" + name_dept + "' " + "		AND eb.status = 'Y' "
          + "		AND (to_char(eb.in_date,'mm/yyyy') <> '06/2011' " + "					or" + "								eb.in_date is null)"
          +

          " 	union " +

          "	SELECT eb.empsn " + "	FROM n_n_emp_borm eb,n_employee e,n_department d " + "	WHERE eb.empsn = e.empsn "
          + "		AND e.depsn = d.id_dept " + "		AND d.name_fact ='" + name_fact + "' " + "		AND d.name_group like '%"
          + name_group + "' " + " 		AND d.name_dept_name like '%" + name_dept + "' " + "		AND eb.status = 'N' ";
    }

    list = (ArrayList<String>) obj_util.Exe_Sql_String(sql);
    return list;
  }

  private List<String> getData() {

    ArrayList<String> list = null;
    String sql = "";
    String status_str = "";
    name_fact = N_EMP_BORMMProgram.name_fact;
    name_group = N_EMP_BORMMProgram.name_group;
    name_dept = N_EMP_BORMMProgram.name_dept;

    if (status_advance) {
      status_str = "Y";
    } else {
      status_str = "N";
    }

    obj_util.ShowMessageInfo(name_fact + "-" + name_group + "-" + name_dept);

    sql = "	select eb.empsn " + "	from n_n_emp_borm eb,n_employee e,n_department d " + "	where eb.empsn = e.empsn "
        + "	and e.depsn = d.id_dept " + "	and d.name_fact ='" + name_fact + "' " + "	and d.name_group like '%"
        + name_group + "' " + " 	and d.name_dept_name like '%" + name_dept + "' " + "	and eb.status = 'Y' ";

    list = (ArrayList<String>) obj_util.Exe_Sql_String(sql);

    return list;

  }

  private void Update_IN_DATE(List<String> list) {

    Calendar ca = Calendar.getInstance();
    Date date = null;

    for (String empsn : list) {
      obj_empsn = new OBJ_EMPSN(empsn, ca.getTime());
      obj_update = new N_N_EMP_BORM();

      // 	lay ngay cong >=13
      //	neu >=13 	: update In_Date in N_N_EMP_BORM

      if (obj_empsn.Get_WORK_DAY() >= 13) {

        try {

          date = sf.parse(sf.format(ca.getTime()));

        } catch (ParseException e) {
          e.printStackTrace();
        }

        obj_update.setEMPSN(empsn);
        obj_update.setSTATUS("Y");
        //				obj_update.setIN_DATE(date);

      } else {

        obj_update.setEMPSN(empsn);
        obj_update.setSTATUS("N");

      }

      try {

        obj_dao.update(obj_update);

      } catch (Exception e) {

      }

    }

  }

  @Override
  protected String[] getDisplayColumns() {
    return new String[] {};
  }

  @Override
  protected void filterHeader(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow header) {

    List<String> list = null;
    obj_dao = Application.getApp().getDao(N_N_EMP_BORM.class);

    System.out.println("BEGIN EXPORT EXCEL FOR SALARY ADVANCE !!!");

    obj_util = new OBJ_UTILITY();

    // lay danh sach nhung nguoi co dang ky ung luong
    list = getData();

    // kiem tra lai dieu kien ung luong trong thang

    Update_IN_DATE(list);

    HSSFSheet sheetAt = wb.getSheetAt(0);

    // xuat danh sanh nhung nguoi ung luong / khong ung trong thang
    showExport(sheetAt);

  }

  private void showExport(HSSFSheet sheetAt) {
    List<String> list = null;
    ArrayList<Object> list_info_emp = null;
    String empsn = "";
    HSSFRow row;
    HSSFCell cell;

    // danh sach nhung nguoi ung luong (true) / khong ung (false)
    list = getDataAdvance(N_EMP_BORMMProgram.status_advance);

    Date date = new Date();

    for (int i = 0; i < list.size(); i++) {
      // duyet danh sach nhung nguoi co du dieu kien ung luong trong thang
      row = sheetAt.createRow(i);

      empsn = list.get(i); // lay tung so the trong 1 cai list ra

      // voi moi so the lay ra, can phai lay thong tin ve xuong, nhom don vi			

      obj_empsn = new OBJ_EMPSN(empsn); // khoi tao doi tuong Obj_Empsn voi tham so empsn
      // ham khoi tao nay se thuc thi ham Find_Info_Basic()
      // tra ve thong tin cua 1 so the

      list_info_emp = obj_empsn.List_EMP_INFO(); // 	empsn : 0 
      //	empcn : 1 
      //	fname : 2 
      //	lname : 3 
      //	name_dept_name : 4 
      //	name_group : 5 
      //	name_fact : 6

      for (int j = 0; j < list_info_emp.size(); j++) {

        cell = row.createCell(j);
        Object obj = list_info_emp.get(j);

        cell.setCellValue(String.valueOf(obj));

      }
      cell = row.createCell(list_info_emp.size());

      if (N_EMP_BORMMProgram.status_advance == false) {
        cell.setCellValue("Co ung");
      } else {
        cell.setCellValue("Khong ung");
      }

    }

  }

  @Override
  protected void filterRow(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow header, HSSFRow row) {
  }

  @Override
  protected String createSQLStatement() {
    String sql = "SELECT * FROM DSPB00 where 1!= 1 ";
    filterParams = new Object[0];//←須將filter清空 
    return sql;
  }

}
