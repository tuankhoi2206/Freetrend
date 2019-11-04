package ds.program.fvhr.khoi.healthInsurance;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.khoi.dao.ExportHealthInsuranceDAO;
import ds.program.fvhr.khoi.domain.EmployeeExport;
import ds.program.fvhr.khoi.domain.Factory;
import dsc.echo2app.ReportService;
import dsc.echo2app.task.ATask;
import fv.util.ReportUtils;
import nextapp.echo2.webrender.WebRenderServlet;


public class ExportHealthInsurance {

  private final int INDEX_CELL_DAIDE = 3;
  private final int INDEX_CELL_CHAT1 = 6;
  private final int INDEX_CELL_CHAT2 = 9;
  private final int INDEX_CELL_BEP_1_2_3 = 12;
  private final int INDEX_CELL_FVL = 15;
  private final int INDEX_CELL_FV_GIAYMAU = 18;
  private final int INDEX_CELL_FV1 = 21;
  private final int INDEX_CELL_FV2 = 24;
  private final int INDEX_CELL_FV3 = 27;
  private final int INDEX_CELL_FV5 = 30;
  private final int INDEX_CELL_FVL_KS = 33;
  private final int INDEX_CELL_MSHAN = 36;
  private final int INDEX_CELL_TONGBO = 39;
  private final int INDEX_CELL_MAY_DAN = 42;
  private final int INDEX_CELL_XT = 45;
  private final int INDEX_CELL_TOANCONGTY = 49;
  private final int BAO_CAO_TONG = 0;
  private final int BAO_CAO_CHI_TIET_EMPL = 1;

  private enum IndexSheetTitle {
    DAIDE(3),
    CHAT1(6);

    private int cell;

    private IndexSheetTitle(int cell) {
      this.cell = cell;
    }

    public int getValue() {
      return this.cell;
    }
  }

  private int execExportType = 1;
  private Date dateExport;
  private SimpleDateFormat sdf;
  private int lanChay;
  private ExportHealthInsuranceDAO exportDAO;

  public ExportHealthInsurance() {
  }

  @SuppressWarnings("deprecation")
  public ExportHealthInsurance(Date dateExport, String nameFact, int lanChay) throws Exception {
    
    sdf = new SimpleDateFormat("dd/MM/yyyy");
    exportDAO = new ExportHealthInsuranceDAO();

    this.dateExport = dateExport;

    dateExport = new Date(dateExport.getYear(), dateExport.getMonth(), dateExport.getDate());

    this.lanChay = lanChay;

    try {
      HSSFWorkbook wb = createHSSFWorkbookReport(dateExport);

      loadDataCellsHSSFWorkbook(wb, nameFact);

      ReportUtils.doExportExcel(wb, getFileName());
    } catch (IOException e) {
      e.printStackTrace();
      throw new IOException("Error from designReport : " + e.getMessage());
    }
  }

  private void loadDataCellsHSSFWorkbook(HSSFWorkbook wb, String nameFact) throws Exception {

    /*
     * sheet 0
     */

    HSSFSheet sheetBCTong = wb.getSheetAt(BAO_CAO_TONG);
    printBCTong(sheetBCTong, nameFact);

    /*
     * sheet 1
     */
    HSSFSheet sheetDetailEmpl = wb.getSheetAt(BAO_CAO_CHI_TIET_EMPL);
    printDetailEmployeeSubInsurance(sheetDetailEmpl, nameFact);

  }

  private String getNameFactByIndexCell(int indexCell) {

    switch (indexCell) {
      case 3: // DAI DE
        return " AND   d.name_fact = 'FVLS'";

      case 6: // X.DAO CHAT 1

        return " AND   d.id_dept 	= 'W0008'";

      case 9: // X.DAO CHAT 2

        return " AND   d.id_dept in( 'W0007','W0009' )";

      case 12: // BEP 1 + 2 + 3

        return " AND   d.id_dept in ( '00001','00002','00003' )";

      case 15: // FVL

        return " AND   (d.name_dept like 'FVL.F12%' or d.id_dept = 'KHODE')";

      case 18: // FVL - GIAY MAU

        return " AND (d.name_dept like 'FVL.GM%' or d.name_dept like 'FVL.F3-GM%')";

      case 21: // FV1

        return " AND d.name_dept like  'FVL.F1%'" + " AND d.name_dept not like  'FVL.F12%'"
            + " AND d.id_dept <> 'KHODE'";

      case 24: // FV2

        return " AND d.name_dept like  'FVL.F2%'";

      case 27: // FV3

        return " AND d.name_dept like 'FVL.F3%'" + " AND d.name_dept not like 'FVL.F3-GM%'";

      case 30: // FV5

        return " AND d.name_dept like 'FVL.F5%'";

      case 33: // FVL.KS, BD 06/2013, UPDATE 06/07/2013, HA

        return " AND d.name_dept like  'FVL.KS%'";

      case 36: // MSHAN

        return " AND   d.id_dept 	= 'MS001'";

      case 39: // TONG BO

        return " AND d.name_fact = 'TB'" + " AND d.id_dept not in ('MS001','00001','00002','00003') " + "";

      case 42: // MAY DAN

        return " AND d.name_group = 'FVJ-BGC'";

      case 45: // XUONG THEU

        return " AND d.name_group = 'FVJ-XT'";

      default:
        return "";
    }
  }

  private int[] indexCell =
      { INDEX_CELL_DAIDE, INDEX_CELL_CHAT1, INDEX_CELL_CHAT2, INDEX_CELL_BEP_1_2_3, INDEX_CELL_FVL, INDEX_CELL_FV_GIAYMAU, INDEX_CELL_FV1, INDEX_CELL_FV2, INDEX_CELL_FV3, INDEX_CELL_FV5, INDEX_CELL_FVL_KS, INDEX_CELL_MSHAN, INDEX_CELL_TONGBO, INDEX_CELL_MAY_DAN, INDEX_CELL_XT, INDEX_CELL_TOANCONGTY };

  private void printBCTong(HSSFSheet sheetBCTong, String nameFact) {

    String conditionSql;
    // case 3: // DAI DE
    String monthYear = new SimpleDateFormat("MM/yyyy").format(dateExport);

    if (nameFact.equals("FVLS")) {
      conditionSql = " AND   d.name_fact = 'FVLS'";
      Factory factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
      setValueCell(sheetBCTong, factory, INDEX_CELL_DAIDE);

    } else if (nameFact.equals("KDAO")) {
      // case 6: // X.DAO CHAT 1
      conditionSql = " AND   d.id_dept 	= 'W0008'";
      Factory factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
      setValueCell(sheetBCTong, factory, INDEX_CELL_CHAT1);

      // case 9: // X.DAO CHAT 2
      conditionSql = " AND   d.id_dept in( 'W0007','W0009' )";
      factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
      setValueCell(sheetBCTong, factory, INDEX_CELL_CHAT2);
      //
    } else if (nameFact.equals("TB")) {

      // case 12: // BEP 1 + 2 + 3
      conditionSql = " AND   d.id_dept in ( '00001','00002','00003' )";
      Factory factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
      setValueCell(sheetBCTong, factory, INDEX_CELL_BEP_1_2_3);

      // case 39: // TONG BO
      conditionSql = " AND d.name_fact = 'TB'  AND d.id_dept not in ('MS001','00001','00002','00003') ";
      factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
      setValueCell(sheetBCTong, factory, INDEX_CELL_TONGBO);

      // case 36: // MSHAN
      conditionSql = " AND   d.id_dept 	= 'MS001'";
      factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
      setValueCell(sheetBCTong, factory, INDEX_CELL_MSHAN);

    } else if (nameFact.equals("FVL")) {
      // case 15: // FVL
      //
      conditionSql = " AND   (d.name_dept like 'FVL.F12%' or d.id_dept = 'KHODE')";
      Factory factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
      setValueCell(sheetBCTong, factory, INDEX_CELL_FVL);
      //
      // case 18: // FVL - GIAY MAU
      //
      conditionSql = " AND (d.name_dept like 'FVL.GM%' or d.name_dept like 'FVL.F3-GM%')";
      factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
      setValueCell(sheetBCTong, factory, INDEX_CELL_FV_GIAYMAU);

      // case 21: // FV1
      //
      conditionSql = " AND d.name_dept like  'FVL.F1%'  AND d.name_dept not like  'FVL.F12%' AND d.id_dept <> 'KHODE'";
      factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
      setValueCell(sheetBCTong, factory, INDEX_CELL_FV1);
      //
      // case 24: // FV2
      conditionSql = " AND d.name_dept like  'FVL.F2%'";
      factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
      setValueCell(sheetBCTong, factory, INDEX_CELL_FV2);
      //
      // case 27: // FV3
      //
      conditionSql = " AND d.name_dept like 'FVL.F3%' AND d.name_dept not like 'FVL.F3-GM%'";
      factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
      setValueCell(sheetBCTong, factory, INDEX_CELL_FV3);

      //
      // case 30: // FV5
      //
      conditionSql = " AND d.name_dept like 'FVL.F5%'";
      factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
      setValueCell(sheetBCTong, factory, INDEX_CELL_FV5);
      //
      // case 33: // FVL.KS, BD
      conditionSql = " AND d.name_dept like  'FVL.KS%'";
      factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
      setValueCell(sheetBCTong, factory, INDEX_CELL_FVL_KS);

    } else if (nameFact.equals("FVJ")) {
      // case 42: // MAY DAN
      //
      // return " AND d.name_group = 'FVJ-BGC'";
      //
      conditionSql = " AND d.name_group = 'FVJ-BGC'";
      Factory factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
      setValueCell(sheetBCTong, factory, INDEX_CELL_MAY_DAN);

      // case 45: // XUONG THEU
      //
      conditionSql = "  AND d.name_group = 'FVJ-XT'";
      factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
      setValueCell(sheetBCTong, factory, INDEX_CELL_XT);

    } else// xuat toan bo
    {
      // for (int i = 0; i < indexCell.length; i++) {
      setValueAllCellFactory(sheetBCTong, monthYear);
      // }
    }

  }

  private void setValueAllCellFactory(HSSFSheet sheetBCTong, String monthYear) {

    Factory tongCTy = new Factory();

    String conditionSql;
    conditionSql = " AND   d.name_fact = 'FVLS'";
    Factory factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
    setValueCell(sheetBCTong, factory, INDEX_CELL_DAIDE);

    tongCTy.setSumEmpsn(factory.getSumEmpsn());
    tongCTy.setSumSalary(factory.getSumSalary());

    // case 6: // X.DAO CHAT 1
    conditionSql = " AND   d.id_dept 	= 'W0008'";
    factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
    setValueCell(sheetBCTong, factory, INDEX_CELL_CHAT1);

    tongCTy.setSumEmpsn(factory.getSumEmpsn() + factory.getSumEmpsn());
    tongCTy.setSumSalary(factory.getSumSalary() + factory.getSumSalary());

    // case 9: // X.DAO CHAT 2
    conditionSql = " AND   d.id_dept in( 'W0007','W0009' )";
    factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
    setValueCell(sheetBCTong, factory, INDEX_CELL_CHAT2);
    //
    tongCTy.setSumEmpsn(factory.getSumEmpsn() + factory.getSumEmpsn());
    tongCTy.setSumSalary(factory.getSumSalary() + factory.getSumSalary());

    // case 12: // BEP 1 + 2 + 3
    conditionSql = " AND   d.id_dept in ( '00001','00002','00003' )";
    factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
    setValueCell(sheetBCTong, factory, INDEX_CELL_BEP_1_2_3);

    //
    tongCTy.setSumEmpsn(factory.getSumEmpsn() + factory.getSumEmpsn());
    tongCTy.setSumSalary(factory.getSumSalary() + factory.getSumSalary());
    //

    // case 39: // TONG BO
    conditionSql = " AND d.name_fact = 'TB'  AND d.id_dept not in ('MS001','00001','00002','00003') ";
    factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
    setValueCell(sheetBCTong, factory, INDEX_CELL_TONGBO);

    //
    tongCTy.setSumEmpsn(factory.getSumEmpsn() + factory.getSumEmpsn());
    tongCTy.setSumSalary(factory.getSumSalary() + factory.getSumSalary());
    //
    // case 36: // MSHAN
    conditionSql = " AND   d.id_dept 	= 'MS001'";
    factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
    setValueCell(sheetBCTong, factory, INDEX_CELL_MSHAN);

    //
    tongCTy.setSumEmpsn(factory.getSumEmpsn() + factory.getSumEmpsn());
    tongCTy.setSumSalary(factory.getSumSalary() + factory.getSumSalary());
    //

    // case 15: // FVL
    //
    conditionSql = " AND   (d.name_dept like 'FVL.F12%' or d.id_dept = 'KHODE')";
    factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
    setValueCell(sheetBCTong, factory, INDEX_CELL_FVL);

    //
    tongCTy.setSumEmpsn(factory.getSumEmpsn() + factory.getSumEmpsn());
    tongCTy.setSumSalary(factory.getSumSalary() + factory.getSumSalary());
    //

    //
    // case 18: // FVL - GIAY MAU
    //
    conditionSql = " AND (d.name_dept like 'FVL.GM%' or d.name_dept like 'FVL.F3-GM%')";
    factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
    setValueCell(sheetBCTong, factory, INDEX_CELL_FV_GIAYMAU);

    //
    tongCTy.setSumEmpsn(factory.getSumEmpsn() + factory.getSumEmpsn());
    tongCTy.setSumSalary(factory.getSumSalary() + factory.getSumSalary());
    //
    // case 21: // FV1
    //
    conditionSql = " AND d.name_dept like  'FVL.F1%'  AND d.name_dept not like  'FVL.F12%' AND d.id_dept <> 'KHODE'";
    factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
    setValueCell(sheetBCTong, factory, INDEX_CELL_FV1);

    //
    tongCTy.setSumEmpsn(factory.getSumEmpsn() + factory.getSumEmpsn());
    tongCTy.setSumSalary(factory.getSumSalary() + factory.getSumSalary());
    //
    //
    // case 24: // FV2
    conditionSql = " AND d.name_dept like  'FVL.F2%'";
    factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
    setValueCell(sheetBCTong, factory, INDEX_CELL_FV2);

    //
    tongCTy.setSumEmpsn(factory.getSumEmpsn() + factory.getSumEmpsn());
    tongCTy.setSumSalary(factory.getSumSalary() + factory.getSumSalary());
    //
    //
    // case 27: // FV3
    //
    conditionSql = " AND d.name_dept like 'FVL.F3%' AND d.name_dept not like 'FVL.F3-GM%'";
    factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
    setValueCell(sheetBCTong, factory, INDEX_CELL_FV3);

    //
    tongCTy.setSumEmpsn(factory.getSumEmpsn() + factory.getSumEmpsn());
    tongCTy.setSumSalary(factory.getSumSalary() + factory.getSumSalary());
    //
    //
    // case 30: // FV5
    //
    conditionSql = " AND d.name_dept like 'FVL.F5%'";
    factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
    setValueCell(sheetBCTong, factory, INDEX_CELL_FV5);
    //

    //
    tongCTy.setSumEmpsn(factory.getSumEmpsn() + factory.getSumEmpsn());
    tongCTy.setSumSalary(factory.getSumSalary() + factory.getSumSalary());
    //
    // case 33: // FVL.KS, BD
    conditionSql = " AND d.name_dept like  'FVL.KS%'";
    factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
    setValueCell(sheetBCTong, factory, INDEX_CELL_FVL_KS);

    //
    tongCTy.setSumEmpsn(factory.getSumEmpsn() + factory.getSumEmpsn());
    tongCTy.setSumSalary(factory.getSumSalary() + factory.getSumSalary());
    //
    // case 42: // MAY DAN
    //
    // return " AND d.name_group = 'FVJ-BGC'";
    //
    conditionSql = " AND d.name_group = 'FVJ-BGC'";
    factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
    setValueCell(sheetBCTong, factory, INDEX_CELL_MAY_DAN);

    //
    tongCTy.setSumEmpsn(factory.getSumEmpsn() + factory.getSumEmpsn());
    tongCTy.setSumSalary(factory.getSumSalary() + factory.getSumSalary());
    //
    // case 45: // XUONG THEU
    //
    conditionSql = "  AND d.name_group = 'FVJ-XT'";
    factory = exportDAO.getFactory(conditionSql, monthYear, lanChay);
    setValueCell(sheetBCTong, factory, INDEX_CELL_XT);

    //
    tongCTy.setSumEmpsn(factory.getSumEmpsn() + factory.getSumEmpsn());
    tongCTy.setSumSalary(factory.getSumSalary() + factory.getSumSalary());
    //

    System.out.println("Xuat toan bo cong ty");
    System.out.println("Tong so nguoi" + tongCTy.getSumEmpsn());
    System.out.println("Tong so tien" + tongCTy.getSumSalary());

    setValueCell(sheetBCTong, factory, INDEX_CELL_TOANCONGTY);
  }

  private void setValueCell(HSSFSheet sheetBCTong, Factory factory, int indexRow) {
    HSSFRow rowSumEmpl = sheetBCTong.getRow(indexRow);

    // set value cell tong so nguoi
    // ? cell 11
    rowSumEmpl.getCell(11).setCellValue(factory.getSumEmpsn());

    // set value cell tong so tien
    HSSFRow rowSumSalary = sheetBCTong.getRow(indexRow + 1);
    rowSumSalary.getCell(11).setCellValue(factory.getSumSalary());

    // set value cell tien chuyen
    HSSFRow rowTienChuyen = sheetBCTong.getRow(indexRow + 2);
    rowTienChuyen.getCell(11).setCellValue(factory.getSumSalary() * HealthInsuranceProgram.TIEN_CHUYEN);
  }

  private enum CellIndex {
    STT,
    EMPSN,
    FNAME,
    LNAME,
    TRANGTHAI,
    ID_DEPT,
    NAME_DEPT,
    TOTAL_SALARY,
    INSURANCE_MONEY,
    NOTE
  }

  private void printDetailEmployeeSubInsurance(HSSFSheet sheetDetailEmpl, String namFact) throws Exception {
    String monthYear = new SimpleDateFormat("MM/yyyy").format(dateExport);
    try {
      List<EmployeeExport> lstEmplSubInsurance = exportDAO.getAllEmplSubInsurance(namFact, monthYear, lanChay);

      HSSFRow row;
      HSSFCell cell;
      int startRow = 1;
      for (EmployeeExport employeeExport : lstEmplSubInsurance) {

        row = sheetDetailEmpl.createRow(startRow);
        cell = row.createCell(CellIndex.STT.ordinal());
        cell.setCellValue(startRow++);

        cell = row.createCell(CellIndex.EMPSN.ordinal());
        cell.setCellValue(employeeExport.getEmpsn());

        cell = row.createCell(CellIndex.FNAME.ordinal());
        cell.setCellValue(employeeExport.getFname());

        cell = row.createCell(CellIndex.LNAME.ordinal());
        cell.setCellValue(employeeExport.getLname());

        cell = row.createCell(CellIndex.TRANGTHAI.ordinal());
        cell.setCellValue(employeeExport.getMonthStatus());

        cell = row.createCell(CellIndex.ID_DEPT.ordinal());
        cell.setCellValue(employeeExport.getIdDept());

        cell = row.createCell(CellIndex.NAME_DEPT.ordinal());
        cell.setCellValue(employeeExport.getNameDept());

        cell = row.createCell(CellIndex.TOTAL_SALARY.ordinal());
        cell.setCellValue(employeeExport.getTotalSalary());

        cell = row.createCell(CellIndex.INSURANCE_MONEY.ordinal());
        cell.setCellValue(employeeExport.getInsuranceMoney());

        cell = row.createCell(CellIndex.NOTE.ordinal());
        cell.setCellValue(employeeExport.getNote());

      }

    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    }

  }

  /*
   * fileName workBook.
   */
  private String getFileName() {
    String filename = "BHYT_Lan_" + lanChay + "_Ngay" + sdf.format(dateExport).replace("/", "_");
    // + System.currentTimeMillis() + ".xls";
    return filename;
  }

  @SuppressWarnings("deprecation")
  private HSSFWorkbook createHSSFWorkbookReport(Date dateExport) throws IOException {

    // conf.format KT_BH.xls
    HSSFWorkbook wb = ReportUtils.loadTemplate("", "KT_BH.xls");

    HSSFSheet sheet_0_AllFact = wb.getSheetAt(BAO_CAO_TONG);
    HSSFRow row_sheet_0 = sheet_0_AllFact.getRow(BAO_CAO_TONG);
    //
    HSSFCell cell = row_sheet_0.getCell(9);// cell tieu de
    cell.setCellValue("BAÛO HIEÅM Y TEÁ ");
    // HSSFSheet sheet_1_DetailEmpsn = wb.getSheetAt(BAO_CAO_CHI_TIET_EMPL);

    int month = dateExport.getMonth() + 1;
    int year = dateExport.getYear() + 1900;

    cell = row_sheet_0.getCell(10);// cell thang nam
    cell.setCellValue("T" + month + "/" + year);
    row_sheet_0 = sheet_0_AllFact.getRow(1);
    cell = row_sheet_0.getCell(9);
    cell.setCellValue(year + " 年 第 " + month + " 月醫 療 保 險");
    row_sheet_0 = sheet_0_AllFact.getRow(2);
    cell = row_sheet_0.getCell(11);
    cell.setCellValue(month + " 月");

    return wb;

    // if (nameFact != "ALL") {
    // writeCellValueBaoCaoTong(nameFact);
    // } else {
    //
    // }
    // try {
    // PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact,
    // INDEX_CELL_TONGBO);
    // PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_DAIDE);
    // PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_CHAT1);
    // PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_CHAT2);
    // PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact,
    // INDEX_CELL_BEP_1_2_3);
    // PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_FVL);
    // PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact,
    // INDEX_CELL_FV_GIAYMAU);
    // PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_FV1);
    // PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_FV2);
    // PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_FV3);
    // PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_FV5);
    // PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_FVL_KS);
    // PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_MSHAN);
    // PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_TONGBO);
    // PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_MAY_DAN);
    // PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_XT);
    // PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact,
    // INDEX_CELL_TOANCONGTY);

    // } catch (Exception e) {
    // e.printStackTrace();
    // System.out.println(e.getMessage());
    // }

    // String check_fact = HealthProgram.NAME_FACT;
    // HANDLE_EXPORT_DETAIL(sheet_1_DetailEmpsn, date_input);
    // // name fact export
    //
    // if (check_fact == null || check_fact.equals("")
    // || check_fact.equals("ALL")) {
    // int rowIndex = 48;
    // int cellIndex = 11;
    // HSSFCell cellTotal = sheet_0_AllFact.getRow(rowIndex).getCell(
    // cellIndex);
    // Double total = cellTotal.getNumericCellValue();
    // IGenericDAO<BaoHiemYT_SYS, String> dao = Application.getApp()
    // .getDao(BaoHiemYT_SYS.class);
    // BaoHiemYT_SYS a = new BaoHiemYT_SYS(2, date_input, total); // chu y
    // // truyen
    // // tham
    // // so
    // // cho
    // // dung
    // // loai
    // // BH
    // // nhu o
    // // duoi
    // try {
    // dao.save(a);
    // } catch (Exception e) {
    // System.out.println("DA LUU DU LIEU TONG VAO DB RUI");
    // // String temp = "";
    // // if (userID.equals("ADMIN") && temp.equals("")) {
    // // dao.update(a);
    // // }
    //
    // }
    //
    // }
  }

  private String getViewerUrl() {
    HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
    String viewerUrl = request.getRequestURL() + "?" + WebRenderServlet.SERVICE_ID_PARAMETER + "="
        + ReportService.INSTANCE.getId() + "&" + ReportService.PARAM_TYPE + "="
        + (execExportType == ATask.EXECTYPE_DIRECT ? ReportService.TYPE_TEMP : ReportService.TYPE_OUTPUT) + "&"
        + ReportService.PARAM_KEY + "=";

    return viewerUrl;
  }
}
