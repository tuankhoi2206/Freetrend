package ds.program.fvhr.khoi.health;

//Ky trinh BHYT
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.sun.star.lang.NullPointerException;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.FillImageBorder;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.list.DefaultListModel;
import ds.program.fvhr.domain.K_N_N_HEALTH_R;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscWindowPane;
import dsc.echo2app.program.DefaultProgram;
import echopointng.ComboBox;


public class HealthProgram extends DefaultProgram implements ActionListener {

  private static final long serialVersionUID = 1L;

  // ======component=======
  private ComboBox combNameFact;
  private ComboBox combGroup;
  private ComboBox combDept;
  private SelectField sfLanChay;
  private CheckBox chkExport;
  private DscDateField dateExport;
  public Date date_input;
  public Date date_att_pre;

  private Button btnExport;
  private Button btnUpdate;

  // ======end component======

  IGenericDAO<K_N_N_HEALTH_R, String> obj_dao;
  OBJ_UTILITY obj_util;
  OBJ_EMPSN obj_emp;
  SimpleDateFormat sf = OBJ_UTILITY.Get_format_date();

  Calendar ca = Calendar.getInstance();

  String user_name = Application.getApp().getLoginInfo().getUserID();// Lay
  // user
  // name
  // tu
  // form
  // login
  String madv = "";
  String note_quit_work_again = "";
  String note_increase_labour_times_1 = "";
  long sothang_dc_bt = 0; // 28/09/2012 them bien toan cuc nay de neu = 2
  // update lai luong cban trong update_data_bt khi
  // giua quy d/c duoc 2 thang (theo yc moi dc 2T
  // ...(haizzzzz))
  boolean flag_dc = false; // 11/10/2012 neu false lay luong cu, neu true lay
  // luong moi chi ap dung cho BT va NS con NV thi
  // lay salary_cb_nv ben duoi

  private String note_work_again = "";
  private long salary_cb_nv = 0; // htai sdung cai nay cho NV de nhan voi so
  // num_nv = salary_m
  private long salary_total_k = 0;// 25/12/2012 dat bien nay luc nghi viec
  // tong luong
  // < money thi lay tong luong set cho money(yeu cau
  // tu Ke Toan(C.Thao FVL))
  private long money_goc = 0;// 25/12/2012 luu lai so tien bhyt thuc te phai
  // tru

  // ======final property===========
  private final int NGAY_QUY_DINH_CHAY_LAN_1 = 15;
  private final int NGAY_QUY_DINH_CHAY_LAN_2 = 26;

  private final String txtDefaultExport = "Xuất dữ liệu";
  private final String txtDefaultUpdate = "Cập nhật";

  private final int CHAY_LAN_1 = 1;
  private final int CHAY_LAN_2 = 2;

  private final boolean CHUA_CAP_NHAT = false;
  private final boolean DA_CAP_NHAT = true;
  public static final String TABLE_NAME = "K_N_N_HEALTH_R";
  // ======end final property========

  // == dùng cho class ReportHealth====

  public static String TABLE_EXPORT = "";
  public static String DATE_EXPORT = "";
  public static String NAME_FACT = "";
  public static String QUERY_SQL = "";

  // ==end== class ReportHealth====

  public static int LAN_CHAY = 0;
  private HealthProgramDAO healthDAO;
  private Timestamp timeServer;

  // ===========configuration frame===============
  @SuppressWarnings("unchecked")
  public HealthProgram() {
    super();

    obj_dao = Application.getApp().getDao(K_N_N_HEALTH_R.class);
    obj_util = new OBJ_UTILITY();
    obj_emp = new OBJ_EMPSN();
    healthDAO = new HealthProgramDAO();

    // IGenericDAO<N_CHDEPSN, String> obj_dao = Application.getApp().getDao(
    // N_CHDEPSN.class);
    // System.out.println(((N_CHDEPSN) obj_dao.findAll(1).get(0))
    // .getN_DEPARTMENT());

    initComponents();
    loadDataSelectField();

    // nếu là user kế toán chỉ dc xuất file
    // xuat toan bo

    try {

      if (!healthDAO.checkLimitExportAll(user_name, "ALL")) {
        chkExport.setEnabled(false);
      }

      if (healthDAO.checkUserAccountant(user_name)) {
        btnUpdate.setEnabled(false);
        combNameFact.setListModel(new DefaultListModel(healthDAO.getAllNameFact()));
      } else {
        combNameFact.setListModel(new DefaultListModel(healthDAO.getAllNameFactOfManageUser(user_name)));
      }
      // timeServer = healthDAO.GetDateTimeOracle();
      // timeServer = convertStringIntoDate(dateExport.getText());
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    // } catch (ParseException e2) {
    // timeServer = null;
    // System.out.println(e2.getMessage());
    // }

    // date_input = getDateOfServer();
    date_input = convertStringIntoDate(dateExport.getText());
  }

  @Override
  protected void doLayout() {
    super.doLayout();
    dateExport.getDateChooser().setLocale(Locale.ENGLISH);
    dateExport.setDateFormat(sf);
    dateExport.getTextField().setText(sf.format(ca.getTime()));
    dateExport.setMouseCursor(DATAMODE_EDIT);

  }

  @Override
  protected int doInit() {
    super.doInit();

    windowPane.setDefaultCloseOperation(DscWindowPane.DISPOSE_ON_CLOSE);
    windowPane.setWidth(new Extent(500, Extent.PX));
    windowPane.setHeight(new Extent(350, Extent.PX));
    windowPane.setMaximizable(false);
    windowPane.setResizable(false);
    windowPane.setPositionX(new Extent(Application.getApp().getScreenHeight() / 3));
    windowPane.setPositionY(new Extent(Application.getApp().getScreenWidth() / 10));

    FillImageBorder borderWinPane = new FillImageBorder(Color.LIGHTGRAY, new Insets(new Extent(1, Extent.PX)),
        new Insets(new Extent(1, Extent.PX)));
    windowPane.setBorder(borderWinPane);

    return RET_OK;
  }

  private final String TITLE_FIRST_RUN = "Lần 1";
  private final String TITLE_SECOND_RUN = "Lần 2";

  private void loadDataSelectField() {
    DefaultListModel defaultListModel = new DefaultListModel();
    defaultListModel.add(TITLE_FIRST_RUN);
    defaultListModel.add(TITLE_SECOND_RUN);

    sfLanChay.setModel(defaultListModel);
  }

  // ===== end =====configuration frame===============

  // ====== action component ========
  @Override
  public void actionPerformed(ActionEvent e) {

    // IGenericDAO<N_CHDEPSN, String> obj_dao;
    // obj_dao = Application.getApp().getDao(N_CHDEPSN.class);
    //
    // System.out.println(((N_CHDEPSN) obj_dao.findAll(1).get(0))
    // .getN_DEPARTMENT());

    Object obj = e.getSource();

    if (obj instanceof SelectField) {
      // ===action selectfield====
      SelectField sfLanChay = (SelectField) obj;
      String valueField = sfLanChay.getSelectedItem().toString();
      btnExport.setText(txtDefaultExport + " " + valueField.toLowerCase());
      btnUpdate.setText(txtDefaultUpdate + " " + valueField.toLowerCase());

      if (valueField.equals(TITLE_FIRST_RUN)) {
        LAN_CHAY = 1;
      } else {
        LAN_CHAY = 2;
      }
      // ===action selectfield====
    } else if (obj instanceof Button) {

      if (obj == btnUpdate) {

        if (checkValidComponents()) {
          NAME_FACT = combNameFact.getText().trim();
          MessageDialog dlg = new MessageDialog(MessageDialog.CONTROLS_YES_NO,
              "Bạn có muốn cập nhật dữ liệu xưởng " + NAME_FACT + " ¿¿¿");
          // dialog action
          dlg.addActionListener(new ActionListener() {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
              if (MessageDialog.COMMAND_OK.equals(e.getActionCommand())) {
                // khoi test
                doAction_update(e);
              }
            }
          });
          // end dialog action
          // end check status update
        }
      } else {

        // System.out.println(dateExport.getText());
        // System.out.println(getMonthYearOfServer());
        if (checkValidDateExport()) {
          doAction_export(e);
          // new MessageDialog("Thông báo", "Chức năng này đã tắt.",
          // MessageDialog.CONTROLS_OK).show();
        }
      }
    }
  }

  @SuppressWarnings("deprecation")
  private void doAction_export(ActionEvent e) {
    // try {
    // K_N_N_HEALTH_R health_R = obj_dao.findById("09021162");
    //
    // System.out.println("khoi test : "
    // + Vni2Uni.convertToUnicode(health_R.getEmployee()
    // .toString()));
    // } catch (Exception e2) {
    // e2.printStackTrace();
    // System.err.println("Loi roi khoi oiiii");
    // return;
    // }

    DATE_EXPORT = dateExport.getText();
    NAME_FACT = combNameFact.getText();

    String[] strDate = dateExport.getText().split("/");
    date_input =
        new Date(Integer.parseInt(strDate[2]) - 1900, Integer.parseInt(strDate[1]) - 1, Integer.parseInt(strDate[0]));

    QUERY_SQL = " and d.name_fact = '" + NAME_FACT + "'";
    try {

      int month = (date_input.getMonth() + 1);
      int year = (date_input.getYear() + 1900);

      String tablecheck = TABLE_NAME + "_" + month + "_" + year + "_LAN" + LAN_CHAY;

      // bang da ton tai
      if (healthDAO.checkTableExist(tablecheck)) {
        TABLE_EXPORT = tablecheck;
        // bang da ton tai va xet xem xuat theo xuong hay xuat tat ca
        if (chkExport.isSelected()) {
          QUERY_SQL = "";
          NAME_FACT = "";
        } else {

          String nameFact = combNameFact.getText().trim();
          if (nameFact != null || nameFact.isEmpty()) {
            if (((DefaultListModel) combNameFact.getListModel()).indexOf(combNameFact.getText()) == -1) {
              new MessageDialog("Error", "Xưởng không hợp lệ.", MessageDialog.CONTROLS_OK).show();

            }
          }

          QUERY_SQL = " and d.name_fact = '" + NAME_FACT + "'";
        }

        new ExportHealthProgram().doExport("IDReportHealth", "KT_BH");

        // truong hop trong thang
      } else if (getMonthYearOfServer().equals((month + "/" + year))) {

        TABLE_EXPORT = "K_N_N_HEALTH_R";

        if (!chkExport.isEnabled() || !chkExport.isSelected())
          if (healthDAO.isCheckStatusUpdateOfFactory(NAME_FACT, (month + "/" + year), (int) LAN_CHAY) == DA_CAP_NHAT) {
            new ExportHealthProgram().doExport("IDReportHealth", "KT_BH");
          } else {
            new MessageDialog("Thông báo", "Ký trình BHYT lần " + LAN_CHAY + " chưa cập nhật nên không thể xuất excel.",
                MessageDialog.TYPE_WARNING + MessageDialog.CONTROLS_OK).show();
          }
      } else {
        new MessageDialog("Thông báo",
            "Xuất excel thất bại. Không tìm thấy ký trình lần" + LAN_CHAY + " tháng " + month + " năm " + year,
            MessageDialog.CONTROLS_OK).show();
      }

    } catch (Exception ex) {
      ex.printStackTrace();
      new MessageDialog("Error", "Có lỗi phát sinh trong quá trình xử lý. ", MessageDialog.CONTROLS_OK).show();
    }
  }

  // ===========end action perform==========

  // === end === action component ========

  // ==========check valid all component==============

  @SuppressWarnings("deprecation")
  private Date convertStringIntoDate(String date) {
    String[] s = date.split("/");
    return new Date(Integer.parseInt(s[2]) - 1900, Integer.parseInt(s[1]) - 1, Integer.parseInt(s[0]));
  }

  /**
   * <b>Kiểm tra tính hợp lệ dữ liệu nhập vào từ component.</b> <dd>
   * <strong>Component:</strong> combNameFact, date_export, sfLanChay,
   * timeServer
   * 
   * @return
   */
  @SuppressWarnings({ "deprecation" })
  private boolean checkValidComponents() {

    // if (timeServer == null) {
    // new MessageDialog(
    // "Error",
    // "Không lấy được time từ server. Vui lòng liên hệ phòng vi tính kiểm tra lại.",
    // MessageDialog.CONTROLS_OK).show();
    // return false;
    // }

    // check name fact
    String nameFact = combNameFact.getText().trim();
    if (nameFact == null || nameFact.trim().isEmpty()) {

      new MessageDialog("Error", "Vui lòng chọn xưởng chạy ký trình.", MessageDialog.CONTROLS_OK).show();
      return false;

    } else {
      if (((DefaultListModel) combNameFact.getListModel()).indexOf(combNameFact.getText()) == -1) {
        new MessageDialog("Error", "Xưởng không hợp lệ.", MessageDialog.CONTROLS_OK).show();
        return false;
      }
    }
    NAME_FACT = getNameFact();
    // check valid date
    String date = dateExport.getText().trim();
    String regularDate = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

    if (!date.matches(regularDate)) {
      new MessageDialog("Error", "Ngày nhập vào không hợp lệ.", MessageDialog.CONTROLS_OK).show();
      return false;
    } else {

      date_input = convertStringIntoDate(dateExport.getText());
      timeServer = new Timestamp(date_input.getYear(), date_input.getMonth(), date_input.getDate(), 0, 0, 0, 0);

    }

    // constraint ngay chay lan 1
    switch (LAN_CHAY) {
      case 1:

        if ((timeServer.getDate() < NGAY_QUY_DINH_CHAY_LAN_1)) {
          new MessageDialog("Thông báo", "Ký trình lần 1 được chạy từ ngày " + NGAY_QUY_DINH_CHAY_LAN_1 + " trở đi.",
              MessageDialog.CONTROLS_OK).show();
          return false;
        }

        if (healthDAO.isCheckStatusUpdateOfFactory(getNameFact(), getMonthYearOfServer(), CHAY_LAN_1) == DA_CAP_NHAT) {
          new MessageDialog("Thông báo", "Xưởng " + getNameFact() + " đã cập nhật ký trình lần 1.",
              MessageDialog.TYPE_WARNING + MessageDialog.CONTROLS_OK).show();
          return false;
        }

        break;

      case 2:// constraint lan chay 2

        String tablecheck = TABLE_NAME + "_" + getMonthOfServer() + "_" + getYearOfServer() + "_LAN" + (LAN_CHAY - 1);

        // bang da ton tai
        try {
          if (!healthDAO.checkTableExist(tablecheck)) {
            new MessageDialog("Thông báo",
                "Không tìm thấy bảng backup Lần 1 nên không thể chạy ký trình lần 2. Vui lòng liên hệ phòng vi tính. ",
                MessageDialog.CONTROLS_OK).show();
            return false;
          }
        } catch (NullPointerException e) {
          return false;
        }
        if ((timeServer.getDate() < NGAY_QUY_DINH_CHAY_LAN_2)) {
          new MessageDialog("Thông báo", "Ký trình lần 2 được chạy từ ngày " + NGAY_QUY_DINH_CHAY_LAN_2 + " trở đi.",
              MessageDialog.CONTROLS_OK).show();
          return false;
        }
        // ===========================
        if (healthDAO.isCheckStatusUpdateOfFactory(getNameFact(), getMonthYearOfServer(),
            CHAY_LAN_1) == CHUA_CAP_NHAT) {
          new MessageDialog("Thông báo", "Xưởng " + NAME_FACT + " chưa cập nhật ký trình lần 1.",
              MessageDialog.TYPE_WARNING + MessageDialog.CONTROLS_OK).show();
          return false;
        }

        if (healthDAO.isCheckStatusUpdateOfFactory(NAME_FACT, getMonthYearOfServer(), CHAY_LAN_2) == DA_CAP_NHAT) {
          new MessageDialog("Thông báo", "Xưởng " + NAME_FACT + " đã cập nhật ký trình lần 2.",
              MessageDialog.TYPE_WARNING).show();
          return false;
        }
        // ===========================
        break;// case 2
    }
    //
    if (sfLanChay.getSelectedIndex() < 0) {
      new MessageDialog("Error", "Chưa chọn lần chạy ký trình.", MessageDialog.CONTROLS_OK).show();
      return false;
    }
    return true;
  }

  // ==== end ==== check valid all component==============

  // ============get ============
  @SuppressWarnings("deprecation")
  private Date getDateOfServer() {
    return new Date(timeServer.getYear(), timeServer.getMonth(), timeServer.getDate());
  }

  @SuppressWarnings("deprecation")
  private int getMonthOfServer() {
    return getDateOfServer().getMonth() + 1;
  }

  @SuppressWarnings("deprecation")
  private int getYearOfServer() {
    return getDateOfServer().getYear() + 1900;
  }

  private String getMonthYearOfServer() {
    return getMonthOfServer() + "/" + getYearOfServer();
  }

  private String getMonthOfDate(Date date) {
    return sf.format(date).substring(3, 5);
  }

  private String getYearOfDate(Date date) {
    return sf.format(date).substring(6, 10);
  }

  // ============end get ============

  @SuppressWarnings("deprecation")
  private void Update_Emp_Lan2() {
    // 1. Lấy những nhân viên chưa commit -1 xét lại.
    Date date = getDateOfServer();
    String my = (date.getMonth() + 1) + "/" + (date.getYear() + 1900);

    List<K_N_N_HEALTH_R> lstEmplHealth = null;

    try {
      lstEmplHealth = healthDAO.getEmployeeHealthLan2(my, getNameFact());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (lstEmplHealth == null) {
      new MessageDialog("Thông báo", "Xưởng " + NAME_FACT + " không có nhân viên nào xét lại lần 2.",
          MessageDialog.CONTROLS_OK).show();
    } else {
      for (K_N_N_HEALTH_R emplHealth : lstEmplHealth) {
        Update_Num(emplHealth, date, false);
      }
    }
  }

  /**
   * Detail: cập nhật nhân viên nghỉ việc tăng lại, điều động khu. Nhân viên
   * hiện tại làm việc bình thường.
   * 
   * @author
   */
  private void doAction_update(ActionEvent e) {

    // chưa xét, có trong ký trình tháng rồi, không xét lại.

    switch (LAN_CHAY) {
      case CHAY_LAN_1:

        QUERY_SQL = " and d.name_fact = '" + NAME_FACT + "'";

        Add_Emp_Quit_Work_Again();
        Add_Emp_New();
        Update_Emp_Lan1();

        break;
      case CHAY_LAN_2:
        // lan 1 nhung nhan vien khong du tien mua
        // lan 2 xet lai nhung nhan vien commit = -1
        // xet lai nhung nhan vien ngay cong =0 trong lan chay 2
        // lay tat ca nhung nhan vien ngay cong = 0
        // lan 1 khong ngay cong vi ly do nghi phep , lan chay 2 nhap
        // lai
        // nhap phep nghi co luong nen
        // bao tang
        // hoac nguoc lai lan

        Update_Emp_Lan2();

        break;
    }// end switch

    try {
      //
      healthDAO.UpdateStatusOfFactory(getNameFact(), sf.format(getDateOfServer()), user_name, LAN_CHAY);

      createTableBackupData();

    } catch (Exception e1) {
      new MessageDialog("Lỗi", "Có lỗi phát sinh trong quá trình sao lưu dữ liệu.",
          MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK).show();
      return;
    }
    showMsgSuccess();

  }

  private void showMsgSuccess() {
    new MessageDialog("Thông báo", "Cập nhật ký trình hoàn thành", MessageDialog.CONTROLS_OK).show();
  }

  /**
   * <b>chỉ createTableBackupData khi tất cả các xưởng đã cập nhật dữ liệu
   * 
   * @throws NullPointerException
   *             lỗi ném ra khi createTableName null.
   * @throws SQLException
   */
  private void createTableBackupData() throws NullPointerException, SQLException {

    int month = getMonthOfServer();
    int year = getYearOfServer();
    if (healthDAO.getAllStatusOfFact(month, year, LAN_CHAY) >= 4) {

      String createTableName = TABLE_NAME + "_" + getMonthYearOfServer().replace("/", "_") + "_LAN" + LAN_CHAY;

      String condition = " to_char(lock_date,'mm/yyyy')='" + getMonthYearOfServer() + "' and lanchay=" + LAN_CHAY;

      if (!healthDAO.createTableFromOtherTable(TABLE_NAME, createTableName, condition)) {
        new MessageDialog("Thông báo", "Sao lưu dữ liệu (backup data) thất bại.",
            MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK).show();
      }

    }
  }

  private String getNameFact() {
    return combNameFact.getText();
  }

  // xét trường hợp cập nhật nhân viên hiện tại làm việc bình thường
  // ======= Update_Emp_Lan1 =========
  // Empsn không thành công trong quá trình xử lý.

  @SuppressWarnings("deprecation")
  private String Update_Emp_Lan1() {
    //
    String empsn_ = "";
    String str_err = "OK";
    Date toDay = getDateOfServer();
    // sẽ bị sai nếu tháng 12
    String month_ = String.valueOf(toDay.getMonth() + 1);
    String year_ = String.valueOf(toDay.getYear() + 1900);
    long num = 0;
    // bi sai tháng nếu tháng 12
    Date date = new Date(timeServer.getYear(), timeServer.getMonth(), timeServer.getDate());
    Date date_now_1 = OBJ_UTILITY.MONTH_NOW("01", date);

    Date date_now_15 = OBJ_UTILITY.MONTH_NOW("15", date); // gianh
    // cho
    // so sanh
    // nsan voi
    // ngay 15
    // cua thang
    // hien tai

    Date bear_date_b = null;
    String note = "";
    boolean flag_update = true;

    // OBJ_EMPSN obj_emp;
    String sql = "";
    // lấy ngày đầu tháng
    String my = sf.format(date).substring(3, 10);

    String dmy_01 = "01/" + my;

    Date bear_date_e = null; // ngay ket thuc NS: //11/04/2013 lay tu
    // check_emp_bear luon khong roll nua

    // *********BINH THUONG***************/
    // lock_date tháng đã update
    // n_not_insurance những nhân viên ko mua bảo hiểm.
    // khoa lai thang da update, chi cho phep update nhung thang >
    // LOCK_DATE
    // N_NOT_INSURANCE những nhân viên không mua bảo hiểm
    // chạy quá lâu
    // cho chạy từ ngày 1->16
    // 1. lấy những nhân viên đi làm bình thường

    List<String> list_empsn_n_health = null;

    // try {
    //
    // Map<String, Object> mapEmpl = HealthDAO.getEmp(my, dmy_01);
    //
    // Set s = mapEmpl.keySet();
    // Iterator it = s.iterator();
    // while (it.hasNext()) {
    // String its = (String) it.next();
    // Object ob = (Object) mapEmpl.get(its);
    // System.out.println(ob);
    // }
    // } catch (SQLException e1) {
    // e1.printStackTrace();
    try {
      list_empsn_n_health = healthDAO.get_NEW_EMP_STATUS_1(my, dmy_01);
    } catch (SQLException e) {
      e.printStackTrace();
      list_empsn_n_health = null;
    }
    // list_empsn_n_health.add("11021101");

    // lấy đối tượng empsn trong n_n_health
    if (list_empsn_n_health != null)
      for (String empsn : list_empsn_n_health) {
        sothang_dc_bt = 0;
        /*
         * Lấy đơn vị theo tháng xử lý đã loại bỏ những người nghỉ việc
         * trước thời gian xử lý.
         */
        madv = obj_emp.Get_depsn(empsn, toDay);
        // lấy những nhân viên thuộc xưởng đang xét
        if (!madv.equals("ENULL")) {
          System.out.println(empsn);

          K_N_N_HEALTH_R obj_update = null;
          try {
            obj_update = obj_dao.findById(empsn);
          } catch (Exception e) {

            System.out.println(e.getMessage());
            return "error";
          }

          // empsn = obj_update.getEMPSN();
          //

          // đối tượng theo ngày.
          obj_emp = new OBJ_EMPSN(empsn, toDay);

          /**
           * ________NGHI VIEC ________
           */

          // nghỉ việc tăng mới lại 20 tây: tháng tăng 20 tây nằm
          // trong
          // giá trị thẻ BHYT 6 tháng còn hiệu lực thì những tháng
          // còn lại trong giá trị thẻ sẽ không bị trừ tiền.
          // xét status nghỉ việc
          // getNum_used >0 đang làm việc và xin nghỉ việc
          // getnum_used ==0 nhân viên ko trả thẻ tăng lại 20 tây
          // xét xem đến kì gia hạn thẻ tiếp chưa mà nghỉ việc

          // lấy những nhân viên tháng tăng mới khác null và
          // getstatus_tt_tmoi =2
          // =2 đến kì gia hạn thẻ tiếp theo mới mua bảo hiểm.
          // những nhân viên tăng mới đến kì gia hạn thẻ tiếp theo mới
          // mua bảo hiểm

          // ============ trường hợp nghỉ việc================//

          if ((obj_update.getNUM_USED() != 0 && obj_emp.Get_Status_Work() == K_N_N_HEALTH_R.NGHI_VIEC)
              || (obj_update.getNUM_USED() == 0 && obj_emp.Get_Status_Work() == K_N_N_HEALTH_R.NGHI_VIEC // 10/02/2012
              // :
              // NV
              // k
              // tra
              // the,tang
              // lai
              // 20
              // tay
              // xet
              // xem
              // den
              // ky
              // gia
              // han
              // the
              // tiep
              // chua
              // ma
              // nghi
              // viec
                  && obj_update.getTHANG_TANGMOI() != null && obj_update.getSTATUS_TT_TMOI() == 2)
              || (obj_update.getNUM_USED() == 0 && obj_emp.Get_Status_Work() == K_N_N_HEALTH_R.NGHI_VIEC// 28/02/2012
              // :
              // Xet
              // tang
              // moi
              // lai
              // 1
              // tay
              // rui
              // nghi
              // viec
                  && obj_update.getTHANG_TANGMOI() != null && obj_update.getSTATUS_TT_TMOI() == 1))

          {
            // trường hợp tăng mới lại 1 tây rồi nghỉ việc
            // num_used =0 && thang_tangmoi !=null &&
            // status_tt_tmoi=1

            System.out.println("XET NGHI VIEC");
            // K_N_N_HEALTH_R obj_update = obj_dao.findById(empsn);
            // lấy từ bảng n_n_health
            // obj_emp = new OBJ_EMPSN(empsn, date_input); đối tượng
            // theo ngày.
            System.out.println("xet truong hop nghi viec " + empsn);
            Update_Emp_Off_Work(obj_update, obj_emp, false);
            continue;

          }
          // ===== end ====== trường hợp nghỉ việc================//

          /**
           * _________ DANG KY NGHI SAN ___________
           */
          // status =0 nghỉ sản
          //
          if (obj_emp.Get_Reg_Bear() && obj_emp.Get_Status_Work() == K_N_N_HEALTH_R.NGHI_SAN) {
            System.out.println("XET DANG KY NGHI SAN");
            if (obj_emp.KT_NS_DUOI_6T(empsn, date_input)) {
              // chua test
              Update_Num(obj_update, date_input, false);// neu
              // nsan
              // < 6T
              // xet
              // nhu
              // BT
            } else {
              // pass
              Update_Emp_Reg_Bear(obj_update, obj_emp);
            }
            continue;

          }

          /**
           * 02/10/2012 THEM VAO NEU DANG BINH THUONG MA NSU LAI SUA
           * THANG NGHI SAN TRUOC THANG DANG KY TRINH THI UPDATE
           * STATUS = 0 DE VE NGHI SAN --------NSU THAY DOI DANG BINH
           * THUONG NHAP NGHI SAN-----
           */
          //
          if (obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NS && obj_emp.Get_Reg_Bear() == false
              && obj_emp.Get_Bear_Date_Begin() != null && obj_update.getNUM_USED() != 0) {// Tai thoi diem
            // ky trinh k co
            // nghi san, nsu
            // thay doi, neu
            // k sua lai cho
            // nay CT bo qua
            // mac du thang
            // 7 da tru
            // duoc//
            // NUM_USED = 0
            // THI BO QUA
            // XET NO LA
            // TANG MOI LAI
            // VD: 02041128
            System.out.println("THAY DOI THONG TIN NGHI SAN");// vd
            // so
            // the
            // 99110178
            // t7
            // tru
            // 3T(7,8,9),
            // T9
            // lai
            // nhap
            // nsan
            // tu
            // 22/07/2012
            // den
            // 22/11/2012
            if (obj_emp.KT_NS_DUOI_6T(empsn, date_input)) {
              Update_Num(obj_update, date_input, false);// neu
              // nsan
              // < 6T
              // xet
              // nhu
              // BT
            } else {
              int n_Bear = obj_emp.Get_N_Bear();
              bear_date_b = obj_emp.Get_Bear_Date_Begin();
              // bear_date_e = OBJ_UTILITY.MONTH_ROLL(bear_date_b,
              // true, n_Bear);
              bear_date_e = obj_emp.Get_Bear_Date_End();

              String bear_date_b_str = sf.format(bear_date_b);
              String bear_date_e_str = sf.format(bear_date_e);
              String note_ =
                  obj_update.getNOTE() + "//=> " + my + ":Dang hien hanh -->nhap thong tin nghi san(Nsan tu: "
                      + bear_date_b_str + " den: " + bear_date_e_str + ")";

              // 11/03/2013 Ngan lam them de test cho vui
              // Neu dang hien hanh --> Nhap nsan thi tra lai so
              // num hien tai dang co
              long num_ = obj_update.getNUM();
              long money_ = 0;
              long salary_ = obj_update.getSALARY_B();
              float heso_1_5 = (float) (1.5 * salary_) / 100;

              if (num_ <= 0) {
                num_ = 0;
                money_ = 0;
                note_ = note_ + " ||==> Tra lai tien " + num_ + "T";
              } else {
                num_ = num_ - 1; // giam 1 thang
                money_ = (long) ((long) (-num_) * heso_1_5);// tra
                // lai
                // so
                // num
                // sau
                // khi
                // da
                // giam
                // cho
                // thang
                // truoc
                obj_update.setMONEY(OBJ_UTILITY.Round_Salary(money_));
                // obj_update.setDK((long)1);//set = 1 de de tim
                // kiem lai
                note_ = note_ + " ||==> Tra lai tien " + num_ + "T";

              }

              obj_update.setSTATUS((long) 0);
              obj_update.setNUM((long) 0);
              obj_update.setNOTE(note_);
              obj_update.setBEAR_DATE_E(bear_date_e);
              obj_update.setLOCK_DATE(date_input);
              obj_update.setDEPSN(madv);
              obj_update.setSALARY_M(salary_);
              obj_update.setUSER_UP(user_name);
              // ==============================
              obj_update.setLANCHAY((long) LAN_CHAY);
              obj_update.setCOMMIT(1l);
              // ==============================
              obj_dao.update(obj_update);

              // xuong duoi se khong xet nua

            }
            continue;
          }

          /*
           * Trường hợp làm việc bình thường.
           */
          // num_used số quý tham gia bảo hiểm
          // số quý tham gia bảo hiểm.
          if (obj_update.getNUM_USED() == 0 || obj_emp.Get_Status_Work() == K_N_N_HEALTH_R.LAM_VIEC_BINH_THUONG) {
            // chi Update nhung so the co date_input > LOCK_DATE
            System.out.println("XET BINH THUONG");
            obj_update.setSTATUS_MONTH((long) K_N_N_HEALTH_R.LAM_VIEC_BINH_THUONG);
            Update_Num(obj_update, toDay, false);
            // cập nhật lại số tháng đóng tiền bảo hiểm.
            //
            // *Begin 12/01/2013 viet truong hop nay them cho truong
            // hop tang 20 tay tai thang nghi viec = thang ky trinh
            // (ngan)
            // vd: 11032971 nviec T10/2012 k tra the, tang 20 tay
            // T11 --> nghi viec T11/2012
            /** ________NGHI VIEC TAI THANG TANG 20 TAY ________ */
            //
            // tăng mới = 0 và đang làm việc
            if ((obj_update.getNUM_USED() == 0 && obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NV // 10/02/2012
            // :
            // NV
            // k
            // tra
            // the,tang
            // lai
            // 20
            // tay
            // xet
            // xem
            // den
            // ky
            // gia
            // han
            // the
            // tiep
            // chua
            // ma
            // nghi
            // viec
                && obj_update.getTHANG_TANGMOI() != null && obj_update.getSTATUS_TT_TMOI() == 2))

            {

              System.out.println("XET NGHI VIEC TAI THANG TANG LAI 20 TAY");
              Update_Emp_Off_Work(obj_update, obj_emp, false);
              continue;
            } else
            // *End 12/01/2013 viet truong hop nay them cho truong
            // hop tang 20 tay tai thang nghi viec(ngan)
            {
              continue;
            }

          } // --------------------END : for(Object[] arr_info : list)
          else {
            continue;
          }
        }
      }
    // list_empsn_n_health
    //
    /**
     * ======= THEO DOI NHUNG NGUOI DANG NGHI SAN ========= status = 0
     */
    sql = "select t.empsn from k_n_n_health_r t where t.status=0" + "\n " + " and to_char(t.lock_date,'MM/yyyy') <> '"
        + my + "'" + " AND T.EMPSN NOT IN (SELECT S.EMPSN FROM N_NOT_INSURANCE S\n"
        + "                                           WHERE TO_DATE('01/'||TO_CHAR(S.DATES,'MM/YYYY'),'DD/MM/YYYY')\n"
        + "                         <= TO_DATE('" + dmy_01 + "','DD/MM/YYYY')) " + "\n";
    List<String> list_0 = obj_util.Exe_Sql_String(sql);
    //
    // for(K_N_N_HEALTH_R obj_update : list_0){
    for (String empsn : list_0) {
      sothang_dc_bt = 0;
      salary_total_k = 0;
      madv = obj_emp.Get_depsn(empsn, date_input);
      if (!madv.equals("ENULL")) {
        if (thuoc_xuong(madv)) {
          // update
          K_N_N_HEALTH_R obj_update = obj_dao.findById(empsn);
          flag_update = true;
          empsn_ = obj_update.getEMPSN();
          num = obj_update.getNUM() == null ? 0 : obj_update.getNUM();

          note = obj_update.getNOTE() + " -- Thang " + month_ + ":";

          /*
           * lấy nhân viên nghỉ sản trong tháng. lấy số tháng đã đóng
           * tiền bảo hiểm.
           */

          System.out.println(empsn);

          // lấy hiện trạng nhân viên theo ngày chạy ký trình.
          obj_emp = new OBJ_EMPSN(empsn_, date_input);
          bear_date_b = obj_emp.Get_Bear_Date_Begin();
          int n_bear = 0;
          n_bear = obj_emp.Get_N_Bear();
          bear_date_e = obj_emp.Get_Bear_Date_End();

          System.out.println("________________ " + empsn + "_____________");
          System.out.println(" --> TRANG THAI : " + obj_emp.Get_Status_Work());

          if (obj_emp.Get_Status_Work() != 0) {
            System.out.println("CHECK : " + empsn_);

          }

          // bắt đầu nghỉ sản..
          // nghỉ sản vào...
          // số tháng đã đóng tiền bảo hiểm.
          // num --
          //
          switch ((int) num) {
            /**
             * num = 0 : _ Ngay tu dau khong tru dc tien _ (phai xet
             * KT_NS < ngay 15) -> BT | |_ (num = 0 && bear_date_b ==
             * null ) |_ Tai num = 1 , nhung KT_NS > ngay 15 (dc uu
             * tien) => num = num - 1 = 0 -> xang thang sau (num = 0 &&
             * bear_date_b = null)
             * 
             * // bear_date_b = null vi khong lay dc // trang thai NS
             * cua thang sau
             */
            case 0: // NS vao sau ngay 15 = Nsan vao truoc ngay 15 ma k
              // tru dc het so thang nsan.
              /*
               * if( bear_date_b == null ||
               * OBJ_UTILITY.MONTH_ROLL(bear_date_b, true,
               * n_bear).compareTo(date_now_15) <= 0 )// old
               */
              // compareTo=0 mới là nghỉ sản vào....
              // ngày vào hoặc ngày kết thúc
              if (bear_date_b == null || bear_date_e.compareTo(date_now_15) <= 0) {

                obj_update.setSTATUS(Long.valueOf(1)); // NS vao,
                // tro ve
                // trang
                // thai BT
                note = note + " -- NS vao ";
                obj_update.setNOTE(note);
                obj_update.setSTATUS_MONTH((long) K_N_N_HEALTH_R.NGHI_SAN_VAO);
                //
                // nghỉ sản vào nghỉ việc.
                if (obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NV) { // NS
                  // vao
                  // -
                  // NV

                  // nghỉ sản vào nghỉ việc
                  Update_Emp_Off_Work(obj_update, obj_emp, true);
                  flag_update = false;

                } else {
                  Update_Num(obj_update, date_now_1, true);
                }

              } else {// ngày hiện tại nhỏ hơn ngày nghỉ sản vẫn còn
                // trong thời gian nghỉ sản.

                // đã xét ở lần chay 1. vẫn còn trong thời gian nghỉ
                // sản.

                note = note + " Dang NS ";
                obj_update.setMONEY(Long.valueOf(0));
                obj_update.setNOTE(note);
                money_goc = 0;
                obj_update.setCOMMIT(1L);
                obj_update.setLANCHAY((long) LAN_CHAY);
                UPDATE_DATA_BT(obj_update);

              }
              break;

            // co truong hop nao num = 0 va ngay KT_NS > 15 khong ?????
            // --> khi tru het 4 thang DK_NS thi num = 1
            // --> neu < 15 -> status = BT
            // --> > 15 -> num = 0 && next month : status = BT
            // => khong co truong hop nay

            case 1: // NS vao truoc ngay 15 = nsan vao sau ngay 15 ma
              // chua tru den duoc thang nsan cuoi cung
              /*
               * if( bear_date_b == null ||
               * OBJ_UTILITY.MONTH_ROLL(bear_date_b, true,
               * n_bear).compareTo(date_now_15) <= 0 )
               */

              if (bear_date_b == null || bear_date_e.compareTo(date_now_15) <= 0) {

                obj_update.setSTATUS(Long.valueOf(1)); // NS vao,
                // tro ve
                // trang
                // thai BT
                note = note + " NS vao :";
                obj_update.setNOTE(note);
                if (obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NV) { // NS
                  // vao
                  // -
                  // NV
                  // (tang
                  // moi)

                  obj_update.setNUM(Long.valueOf(0));
                  Update_Emp_Off_Work(obj_update, obj_emp, true);
                  flag_update = false;
                } else {
                  Update_Num(obj_update, date_now_1, true);
                }

              } else {
                obj_update.setMONEY(Long.valueOf(0));
                obj_update.setNUM(num - 1); // giam 1 thang
                obj_update.setNOTE(note + " Dang NS ");
                obj_update.setCOMMIT(1L);
                obj_update.setLANCHAY((long) LAN_CHAY);
                obj_update.setSTATUS_MONTH((long) K_N_N_HEALTH_R.TRONG_THOI_GIAN_NGHI_SAN);
                money_goc = 0;
                UPDATE_DATA_BT(obj_update);

                if (obj_emp.Get_Status_Work() == K_N_N_HEALTH_R.NGHI_VIEC) { // NS
                  // vao
                  // -
                  // NV
                  // lun

                  // obj_update.setSTATUS(Long.valueOf(-1));
                  obj_update.setNOTE(note + "Dang NS -- NV truoc khi tang moi cho NS vao");
                  money_goc = 0;
                  UPDATE_DATA_BT(obj_update);
                  Update_Emp_Off_Work(obj_update, obj_emp, true);
                  flag_update = false;

                }

              }
              break;

            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:// 11/11/2011 them 7,8,9,10 vi co nghi san >=6T
              long salary_total = 0;
              obj_update.setMONEY(Long.valueOf(0));
              obj_update.setNUM(num - 1); // giam 1 thang
              // bear_date_e = obj_update.getBEAR_DATE_E();
              // lay ra ngay ket thuc NS
              // neu be hon ngay 15 cua thang thien tai && status = 0
              // => xet dieu chinh luong va cap nhat lai luong
              // Salary_M
              // truong hop nay la NS vao van dang con Num tru tien BH
              // 6 thang tu dau quy
              /*
               * if(bear_date_e.before(date_now_15) &&
               * obj_update.getSTATUS() == 0){
               * 
               * salary_total =
               * obj_emp.Get_Salary_Total_By_Month(empsn_,
               * date_input); long salary_e =
               * obj_emp.Get_Salary_Basic(empsn_, date_input); //
               * luong thang hien tai long salary_b =
               * obj_update.getSALARY_B(); money = money +
               * (long)((salary_e - obj_update.getSALARY_B()) *
               * 1.5/100); if (salary_total == 0){ money = 0; note
               * =note+ " --Chua DC luong " + month_;
               * obj_update.setSALARY_M(obj_update.getSALARY_B());
               * }else { money = money; note =note + " -- DC luong " +
               * month_; obj_update.setSALARY_M(salary_e); //
               * obj_update.setDATE_B(bear_date_e); }
               * 
               * 
               * obj_update.setMONEY(money);
               * //obj_update.setDATE_B(bear_date_e);//Ngan them vao
               * 04/07/2011 de xet lay luong NS vao note = note +
               * " NS vao " + bear_date_e;
               * obj_update.setNOTE(note);//Ngan them vao 04/07/2011
               * de xet lay luong NS vao
               * 
               * // TRUONG HOP NAY CO THE SAI VI THANG NAY KHONG DU
               * LUONG DE TRU TIEN DIEU CHINH LUONG
               * 
               * }
               */
              //
              obj_update.setNOTE(note + " Dang NS ");
              money_goc = 0;
              UPDATE_DATA_BT(obj_update);

              break;
            default:
              break;
          }

          if (obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NV && flag_update) {
            Update_Emp_Off_Work(obj_update, obj_emp, false);
            continue;
          }

        } else {
          continue;
        }
      }
    }
    // ---------------------END for(K_N_N_HEALTH_R obj_update : list_0){

    return str_err;

  }

  // === end == Update_Emp_Lan1 =========
  // xét xem nhân viên đang xét có thuộc xưởng đang xuất ký trình
  private boolean thuoc_xuong(String madv) {// DUNG CHO KY TRINH BHYT

    String sql = "select count(*) from n_department d" + " where d.name_fact='" + combNameFact.getText()
        + "' and d.id_dept='" + madv + "'";
    BigDecimal c = (BigDecimal) obj_util.Exe_Sql_Obj(sql);
    return c.compareTo(BigDecimal.ZERO) > 0;
  }

  // số tháng đã đóng tiền bảo hiểm y tế
  /**
   * Detail: số tháng đã đóng tiền bảo hiểm y tế.
   * 
   * @param obj_health_r
   * @param date_input_
   * @param flag_bear
   */

  private void Update_Num(K_N_N_HEALTH_R obj_health_r, Date date_input_, boolean flag_bear) {
    // thêm lương ngày công để trừ tiền bảo hiểm
    // không đủ trừ bảo hiểm kể cả tháng hiện tại
    String m_y_dateLock = obj_health_r.getLOCK_DATE() != null
        ? sf.format(obj_health_r.getLOCK_DATE()).substring(3, obj_health_r.getLOCK_DATE().toString().length()) : "";
    String m_y_dateServer = getMonthYearOfServer();
    if ((m_y_dateLock.equals(m_y_dateServer) && obj_health_r.getCOMMIT() == 0)
        || (!m_y_dateLock.equals(m_y_dateServer))) {
      money_goc = 0;
      String month_ = String.valueOf(getMonthOfServer());
      String year_ = String.valueOf(getYearOfServer());
      // khong update de lan 2 xet mot luot
      String empsn = obj_health_r.getEMPSN();

      long num = obj_health_r.getNUM() == null ? 0 : obj_health_r.getNUM();

      /**
       * debt số tháng chưa đóng bảo hiểm ý tế.
       */
      long debt = obj_health_r.getDEBT() == null ? 0 : obj_health_r.getDEBT();
      long money_initial = obj_health_r.getMONEY() == null ? 0 : obj_health_r.getMONEY();
      long debt_begin = obj_health_r.getDEBT() == null ? 0 : obj_health_r.getDEBT();
      long debt_k = obj_health_r.getDEBT() == null ? 0 : obj_health_r.getDEBT(); // 02/04/2013: de xet cho debt_staus ben duoi,
      // neu
      // debt_status = true thi lay debt_k update
      // xuong
      // table
      /**
       * money: số tiền bảo hiểm y tế của tháng hiện tại.
       */
      long money = obj_health_r.getMONEY() == null ? 0 : obj_health_r.getMONEY();
      long num_used_ = obj_health_r.getNUM_USED() == null ? 0 : obj_health_r.getNUM_USED();
      long status_ = obj_health_r.getSTATUS() == null ? 0 : obj_health_r.getSTATUS();
      //
      String note = obj_health_r.getNOTE() == null ? "||==>" : obj_health_r.getNOTE();
      long note_int = 1;
      String note_recount = "";
      long money_dc = 0;
      long debt_dc = 0;// nợ điều chỉnh lương
      boolean debt_staus = false; // 02/04/2013: neu ma num > 0 thi bat
      // debt_status = true de gan debt_k de
      // van
      // luu lai nợ đến tháng nào num = 0 thi
      // xet
      // tru tiep

      // Begin 12/10/2011 Ngan them bien de xet nviec -->tang moi
      Date thang_tangmoi = obj_health_r.getTHANG_TANGMOI();
      long status_tt_tm = obj_health_r.getSTATUS_TT_TMOI() == null ? 0 : obj_health_r.getSTATUS_TT_TMOI();
      String month_tm = "";

      Date lock_date_ = obj_health_r.getLOCK_DATE();
      // End 12/10/2011 Ngan them bien de xet nviec -->tang moi

      // nợ
      long debt_old = 0;// no cu truoc thang hien tai
      OBJ_EMPSN obj_emp_ = new OBJ_EMPSN(empsn, date_input_);
      // lấy lương theo ngày chạy để xét với lương trong ký trình.
      long salary_b = obj_emp_.Get_Salary_Basic(empsn, date_input_);// luong
      // hien
      // tai
      float heso_1_5 = (float) (1.5 * salary_b) / 100; // he so 1.5%/T

      boolean flag_recount = false;

      note = note + "  // => THANG " + month_ + "( lan " + LAN_CHAY + ")";

      if (obj_emp.KT_NS_DUOI_6T(empsn, date_input_)) // 09/03/2013 them de
      // hieu
      // vi sao lai tru
      // nhu
      // hhanh
      {
        note = note + " Nsan (so thang tham gia BH den tgian nsan < 6T) =>Tru nhu hien hanh ";
      }

      // ================= số tháng tối đa có thể đóng tiền bảo
      // hiểm=====================/

      long num_max = 0;
      // 1 thang
      // long num_max_1 = 1;
      long num_recount = 0;
      boolean flag_init = false;// biến đánh dấu tháng đầu quý

      switch (Integer.valueOf(month_)) {

        case 1:
        case 7:
          num_max = 6;
          flag_init = true;
          break;

        case 2:
        case 8:
          num_max = 5;
          flag_init = false;
          break;

        case 3:
        case 9:
          num_max = 4;
          flag_init = false;
          break;

        case 4:
        case 10:
          num_max = 3;
          flag_init = false;
          break;

        case 5:
        case 11:
          num_max = 2;
          flag_init = false;
          break;

        case 6:
        case 12:
          num_max = 1;
          flag_init = false;
          break;
      }
      // ====== end ========= số tháng có thể đóng =====================/
      //
      String year_tm = "";
      if (thang_tangmoi != null) {
        month_tm = getMonthOfDate(thang_tangmoi); // thang tang
        // 20 tay
        // nsu nhap
        year_tm = getYearOfDate(thang_tangmoi); // nam tang 20
        // tay nsu
      }

      // * Neu la nghi viec k tra the da tinh luong thoi viec trong thoi
      // gian
      // gtri the co hieu luc -> tang moi vao 20 tay
      String thang_tm_nv = "";
      // dựa vào ngày lock_date để so sánh tháng tăng mới.
      if ((thang_tangmoi != null) && (num_used_ == 0) && ((lock_date_ == null) || (lock_date_ != null))
          && (month_tm.equals(month_)) && (year_tm.equals(year_))// tăng
          // mới
          // trong
          // tháng.
          && (status_tt_tm == K_N_N_HEALTH_R.KHONG_TRA_THE_TANG_20_TAY))// status_tt_tm
      // ==
      // 3
      // nghi
      // viec
      // k
      // tra
      // the(lock_date co the !=null vi chay
      // tang 20tay thang nay truoc khi chay
      // ky trinh cho thang truoc
      {

        int mon_tm = Integer.parseInt(month_tm);

        if (mon_tm >= 1 && mon_tm <= 6) {

          thang_tm_nv = "01/07/" + year_tm;
          try {
            obj_health_r.setTHANG_TANGMOI(sf.parse(thang_tm_nv));
            obj_health_r.setSTATUS_TT_TMOI((long) 2);// status_tt_tm
            // ==
            // 2 cho den
            // thoi han
            // gia
            // han the
            // moi
          } catch (ParseException e) {
            e.printStackTrace();
          }
        } else if (mon_tm >= 7 && mon_tm <= 12) {

          long nam_tm_nv = Integer.valueOf(year_tm) + 1;
          thang_tm_nv = "01/01/" + String.valueOf(nam_tm_nv);
          try {
            obj_health_r.setTHANG_TANGMOI(sf.parse(thang_tm_nv));
            obj_health_r.setSTATUS_TT_TMOI((long) 2);
          } catch (ParseException e) {
            e.printStackTrace();
          }

        }

        // end if
      } // ** Neu la nghi viec k tra the da tinh luong thoi viec trong thoi
        // gian gtri the co hieu luc -> tang moi vao 20 tay

      /**
       * xét trong recount_nv nếu lương ko đủ tiền để trừ thì lấy num_k
       */
      long num_k = obj_health_r.getNUM() == null ? 0 : obj_health_r.getNUM();
      /**
       * Neu la dau quy || tang moi || NS vao = > init NUM
       */

      // test trường hợp kí hợp đồng 13080024 ,13080025, 13080027
      // trường hợp đầu quý
      if ((flag_init && (lock_date_ != null) && ((thang_tangmoi == null) || (thang_tangmoi != null))
          && (num_used_ != 0))// dau
          // quy
          // (08/02/2012
          // thang_tangmoi
          // !=
          // null
          // vi
          // co
          // the
          // di
          // lam
          // lai
          // tang
          // moi
          // lai
          // thang
          // tang
          // moi
          // <
          // lock_date
          /*
           * Tăng mới ký hợp đồng.
           */
          || ((num_used_ == 0) && (lock_date_ == null) && (thang_tangmoi == null))
          /*
           * Nghỉ việc tăng mới 1 tây hoàn toàn.
           */
          || ((thang_tangmoi != null) && (num_used_ == 0) && ((lock_date_ == null) || (lock_date_ != null))
              && (month_tm.equals(month_)) && (year_tm.equals(year_)) && (status_tt_tm == 1))

          || ((thang_tangmoi != null) && (num_used_ == 0) && (lock_date_ != null) && (month_tm.equals(month_))
              && (year_tm.equals(year_)) && (status_tt_tm == 2))// status_tt_tm
          // ==
          // 2
          // trong
          // trang//com
          // thoi
          // uh
          // thai
          // cho
          // gia
          // han
          // the
          // =
          // thang_tm
          // (den
          // ky
          // gia
          // han
          // the
          // moi
          // cua
          // tang
          // 20
          // tay)
          || flag_bear) { // Nsan vao

        // note = "||==>> INIT thang " + month_ + " : ";
        num_k--;

        // không giảm num nếu là lần chay =1 vì đã trừ tháng sử dụng ở
        // lần
        // 1.
        // test lại.

        // trường hợp nghỉ sản và tăng mới num_k=0.
        if ((num_k <= 0) || flag_bear) {
          num_k = 0;
        }
        // if(num_k!=0 || !flag_bear)
        // {
        // num_k--;
        // }
        // ???
        if (debt <= 0) {
          debt = 0;
        }
        // trường hợp hiện hành
        if (num_k > 0 && flag_bear == false) {
          debt = 0;
          debt_staus = true;
        } // 30/03/2013: neu la hien hanh thi gan ao debt = 0 de xuong
          // duoi
          // khong xet tru debt khi co tien-> ma de den neu la hien
          // hanh
          // ma
          // num giam = 0 thi moi tru debt

        // if (flag_bear){num_k = 0;}

        // trường hợp ko nợ.
        // =================trường hợp tăng mới=========
        if (debt == 0) {
          num = num_max;
          num_recount = num_max;
          // debt = 0;
          // số tiền phải trừ.
          money = (long) (num_max * heso_1_5);
          flag_recount = true;

          note = note + " tru " + num_max + "T";
          num_used_++;
          status_ = 1;
          obj_health_r.setSALARY_B(salary_b);

          // kiểm tra status tại ngày chạy ký trình
          if (obj_emp_.Get_Status_Work() == OBJ_EMPSN.empsn_NV) {
            note = note + " -- Tang moi - NV : ";
            Update_Emp_Off_Work(obj_health_r, obj_emp_, true);
            return;
          }
        } else {
          // trường hợp nợ
          // =========== else debt <> 0=========
          num_recount = num_max + debt;
          num = num_max;
          money = (long) (num_recount * heso_1_5);
          flag_recount = true;
          note = note + " tru " + num + "T + " + debt + "T no";
          num_used_++;
          status_ = 1;
          debt = 0;
          debt_old = num_recount - num_max;
          obj_health_r.setSALARY_B(salary_b);

          if (obj_emp_.Get_Status_Work() == OBJ_EMPSN.empsn_NV) {
            note = note + " -- Tang moi - NV : ";
            // obj_health_r.setSALARY_B(salary_b);//Ngan them vao
            // 30/08/2011 vi tang moi thang truoc la thang cuoi
            // quy,thang nay nghi viec
            Update_Emp_Off_Work(obj_health_r, obj_emp_, true);
            return;
          }
        } // ===========end else debt <> 0============

      } else if ((thang_tangmoi != null) && (num_used_ == 0) && ((lock_date_ == null) || (lock_date_ != null))
          && (month_tm.equals(month_)) && (year_tm.equals(year_)) && (status_tt_tm == 3))
      // Them else if nay vi neu roi vao truong hop nay thi k chay vao xet
      // nua
      {
        obj_health_r.setSTATUS_TT_TMOI((long) 2);
        note = "||==>>Nviec khong tra the, tang lai 20 tay T" + month_tm + "/" + year_tm + " --> Den ky gia han the T"
            + thang_tm_nv.substring(3, 10) + " moi tang lai BHYT ";

      } else if ((thang_tangmoi != null) && (num_used_ == 0) && ((lock_date_ == null) || (lock_date_ != null))
          && ((Integer.valueOf(year_tm) == Integer.valueOf(year_)
              && Integer.valueOf(month_tm) > Integer.valueOf(month_))
              || (Integer.valueOf(year_tm) > Integer.valueOf(year_)))
          && (status_tt_tm == 3))
      // Them else if nay vi neu chay tang 20 tay truoc khi chay ky trinh
      // cho
      // thang truoc(vd:chay tang 20 tay T3 truoc khi chay ky trinh T2)
      {
        note = note + "";
      }

      else if ((thang_tangmoi != null) && (num_used_ == 0) && (lock_date_ != null)
          && ((Integer.valueOf(year_tm) == Integer.valueOf(year_)
              && Integer.valueOf(month_tm) > Integer.valueOf(month_))
              || (Integer.valueOf(year_tm) > Integer.valueOf(year_)))
          && (status_tt_tm == 2))
      // Them else if nay vi neu chua den ky gia han the thi khong xet
      {
        note = note + "";
      } else if ((thang_tangmoi != null) && (num_used_ == 0) && (lock_date_ == null)
          && ((Integer.valueOf(year_tm) == Integer.valueOf(year_)
              && Integer.valueOf(month_tm) > Integer.valueOf(month_))
              || (Integer.valueOf(year_tm) > Integer.valueOf(year_)))
          && (status_tt_tm == 1))

      // Them else if nay vi neu roi vao truong hop nay thi k chay vao xet
      // nua
      {
        note = "";
      }

      else {// trường hợp bình thường.

        /*
         * Trường hợp đã trừ tiền một lượt từ đầu quý lần chạy tiếp theo
         * chỉ giảm num( lần chạy 1 thì giảm num) lần 2 điều chỉnh lương
         */

        // nếu số tháng không đủ trừ
        // sai
        // thang obj_health lock date < today

        // xóa getlanchay

        num--; // theo doi moi thang NUM - 1, giảm tháng sử dụng
        num_k--;

        money = 0; // binh thuong khong tru tien vi da tru dau quy
        //
        if (num_k <= 0) {
          num_k = 0;
        }
        // else if
        // tại vì trường hợp nghỉ sản num=0.
        // nếu num >0 và không phải nghỉ sản gán debt_status =true
        // luu lai nợ đến tháng nào num = 0 thi xet tru tiep
        if (num_k > 0 && flag_bear == false) {
          debt = 0;
          debt_staus = true;
        } // 30/03/2013: neu la hien hanh thi gan ao debt = 0 de xuong
          // duoi
          // khong xet tru debt khi co tien-> ma de den neu la hien
          // hanh
          // ma
          // num giam = 0 thi moi tru debt

        // =============trường hợp điều chỉnh lương============
        if ((salary_b > obj_health_r.getSALARY_B()) && (num_k >= 1)) { // DC
          // luong
          // money = money + (long)((salary_b -
          // obj_health_r.getSALARY_B()) * 1.5/100);
          money_dc = (long) ((salary_b - obj_health_r.getSALARY_B()) * (num_k * 1.5 / 100));
          flag_recount = true;
          // note =note + " -- DC luong " + month_;
          note =
              note + " -- DC luong " + num_k + "T(" + month_ + " --> " + ((Integer.valueOf(month_) + num_k) - 1) + ")";
        }
        // ===== end =====trường hợp điều chỉnh lương============

        // ***********************

        // truong hop no tu thang dau quy chua tru het
        if (debt > 0) { // xet DEBT (num_k <=0 dau quy chua tru duoc den
          // T2
          // or T8)

          num_recount = debt;
          num = num_max; // 17/01/2013 thay doi vi tru 6 thang
          money = money + (long) (debt * heso_1_5); // tru no thang
          // truoc
          flag_recount = true;
          if (LAN_CHAY == 1) {
            note = note + " -- Tru them no thang truoc : " + debt + "T";
          } else {
            note = note + " -- Tru them no lan 1 chua tru duoc : " + debt + "T";
          }
          debt = 0;
          debt_old = num_recount - num_max;
          // Neu dau quy k tru duoc thi phai set salary_b lai
          if (num_k == 0) {
            obj_health_r.setSALARY_B(salary_b);
          }
        }

      } // end trường hợp bình thường.

      //
      money = money + money_dc;
      money_goc = money;// 25/12/2012
      ArrayList<Long> list_recount = null;
      list_recount = ReCount_BT(obj_health_r, num_recount, num_k, money_dc, date_input_, debt_old, LAN_CHAY);
      // xét lại số tiền khi có tiền trừ
      // flag_recount && list_recount != null dánh dấu flag_recount khi có
      // dc, có nợ
      if (flag_recount && list_recount != null) { // chi recount lai so
        // tien
        // khi co tru tien

        num = list_recount.get(0);
        debt = list_recount.get(1);
        money = list_recount.get(2);
        note_int = list_recount.get(3);
        debt_dc = list_recount.get(4);

        // Begin 14/05/2013
        if (num_k > 0) {
          debt = debt_k;
        }
        // else {
        // debt = debt;
        // }
        // End 14/05/2013

        long num_tru = num_recount - debt;
        if (num_tru <= 0) {
          num_tru = 0;
        }

        // if((note_int == 0) && (status_tt_tm == 0)){ //old

        if (note_int == 0) {
          if (debt_dc == 0 && sothang_dc_bt >= num_k && num_k > 0) {

            note_recount = " --> Xet luong thuc nhan d/c luong duoc " + sothang_dc_bt + "T + tru duoc: " + num_tru
                + "T  //--Con no: " + debt + "T";
          } else if (debt_dc == 1) {
            note_recount = " --> Xet luong thuc nhan khong du tien de tru //--Con no: " + debt + "T";
          } else// khong co su thay doi luong bao gom ca luong tai
          // thang
          // ky trinh < luong dau quy (lay luong dau quy) _
          // gan
          // debt_dc = 4 cho truong hop nay
          {
            note_recount = " --> Xet luong thuc nhan tru duoc: " + num_tru + "T  //--Con no: " + debt + "T";
          }
        } else {
          if (debt <= 0) {
            note_recount = "";
          } else {
            note_recount = "//--Con no: " + debt + "T";
          }
        }
      } else {
        obj_health_r.setCOMMIT(1l);
      }
      // end flag_recount && list_recount != null

      // num dc tính dựa vào tháng hiện tại.
      obj_health_r.setNUM(num);

      if (LAN_CHAY == 1) {
        obj_health_r.setNUM_USED(num_used_);
        obj_health_r.setSTATUS(status_);
      }

      // nếu num >0 và không phải nghỉ sản gán debt_status =true
      // luu lai nợ đến tháng nào num = 0 thi xet tru tiep
      if (debt_staus) // 02/04/2013
      {
        obj_health_r.setDEBT(debt_k);
        if (debt_k > 0) {
          note = note + "//--Con no: " + debt_k + "T";
        }

      } else {
        obj_health_r.setDEBT(debt);
      }
      obj_health_r.setMONEY(money);
      obj_health_r.setNOTE(note + note_recount);
      obj_health_r.setDEBT_DC(debt_dc);// nợ điều chỉnh lương
      // lần chạy kí trình
      obj_health_r.setLANCHAY((long) LAN_CHAY);
      // phân biệt đã trừ tiền từ đầu quý
      // khoong tru dc thang nao
      if (num_max == debt) {
        obj_health_r.setCOMMIT(0l);
      }
      // nghỉ việc tăng lại
      if (money_initial == 0 && debt_begin == 0 && num_used_ > 1) {// vì
        // đã
        // trừ
        // tiền từ
        // đầu quý
        obj_health_r.setCOMMIT(1l);
      }
      // bi sai status voi desn
      // trường hợp không trừ được hết số tháng đóng bảo hiểm
      UPDATE_DATA_BT(obj_health_r);
    }
  }

  // cập nhật nghỉ sản
  private void Update_Emp_Reg_Bear(K_N_N_HEALTH_R obj_health_r, OBJ_EMPSN obj_emp) {
    // Theo nhu thong bao Nsu ycau khong tru tien nhung nguoi Nsan (IT nhan
    // don
    // ngay 13/06/2012--> nhung nguoi nsan se k co trong ky trinh nua thi:
    // set num va money ve = 0
    money_goc = 0;// 25/12/2012

    String month_ = sf.format(date_input).substring(3, 5);
    String year_ = sf.format(date_input).substring(6, 10);

    String empsn = obj_health_r.getEMPSN();
    long num_h = obj_health_r.getNUM() == null ? 0 : obj_health_r.getNUM();
    long num = obj_health_r.getNUM() == null ? 0 : obj_health_r.getNUM();
    long money = obj_health_r.getMONEY() == null ? 0 : obj_health_r.getMONEY();
    long debt = obj_health_r.getDEBT() == null ? 0 : obj_health_r.getDEBT();// số
    // tiền
    // nợ
    long debt_h = obj_health_r.getDEBT() == null ? 0 : obj_health_r.getDEBT();
    String note = obj_health_r.getNOTE();
    long num_used_ = obj_health_r.getNUM_USED() == null ? 0 : obj_health_r.getNUM_USED();

    long salary_e = obj_emp.Get_Salary_Basic(empsn, date_input);
    int n_Bear = obj_emp.Get_N_Bear(); // so thang dang ky NS
    Date bear_date_b = obj_emp.Get_Bear_Date_Begin();
    String day_bear_ = sf.format(bear_date_b).substring(0, 2); // ngay dang
    // ky NS
    // Date bear_date_e = OBJ_UTILITY.MONTH_ROLL(bear_date_b, true, n_Bear);
    Date bear_date_e = obj_emp.Get_Bear_Date_End();

    // * Begin Them bear_date_b_str & bear_date_e_str de ghi chu ngay dk
    // nghi san theo dinh dang ngay/thang/nam
    String bear_date_b_str = sf.format(bear_date_b);
    String bear_date_e_str = sf.format(bear_date_e);
    // * End Them bear_date_b_str & bear_date_e_str de ghi chu ngay dk nghi
    // san theo dinh dang ngay/thang/nam

    List<Long> list_recount;
    long note_int = 1;
    String note_recount = "";
    long num_max = 0;
    long num_recount = 0;
    long money_dc = 0;
    long debt_dc = 0; // debt_dc == 0 dchinh dc,1 k dchinh dc
    long sothang_dc = 0;
    long num_ns = 0;
    long debt_old = 0;
    long num_return = 0;

    boolean flag_DC_luong = false;

    // binh thuong sang thang la NUM --
    num--;
    num_h--; // su dung num_h de xet

    long heso_1_5 = (long) (salary_e * 1.5 / 100);

    long thang_bd_ns = 0;
    long thang_kt_ns = 0;
    long nam_bd_ns = 0;
    long nam_kt_ns = 0;
    // *Begin 21/06/2012: Nsan neu da tru tien truoc dau quy thi den thang
    // nsan phai tra tien lai
    float heso_1_5_return = (float) (1.5 * obj_health_r.getSALARY_B()) / 100;
    long money_return = 0; // so tien tra lai cho nhan vien
    // End 21/06/2012: Nsan neu da tru tien truoc dau quy thi den thang nsan
    // phai tra tien lai

    // * Begin 15/10/2011 xet ghi chu cho Nsan
    if (Integer.valueOf(day_bear_) <= 15)// nsan bd <= 15
    {
      thang_bd_ns = Integer.valueOf(month_);
      nam_bd_ns = Integer.valueOf(year_);

      if ((Integer.valueOf(month_) + n_Bear) - 1 > 12) {
        thang_kt_ns = ((Integer.valueOf(month_) + n_Bear) - 1) - 12;
        nam_kt_ns = Integer.valueOf(year_) + 1;
      } else {
        thang_kt_ns = (Integer.valueOf(month_) + n_Bear) - 1;
        nam_kt_ns = Integer.valueOf(year_);
      }
    } else // nsan bd > 15
    {
      if (Integer.valueOf(month_) == 12) {
        thang_bd_ns = (Integer.valueOf(month_) + 1) - 12;
        nam_bd_ns = Integer.valueOf(year_) + 1;

        thang_kt_ns = Integer.valueOf(month_) + n_Bear - 12;
        nam_kt_ns = Integer.valueOf(year_) + 1;
      } else// month_ < 12
      {
        thang_bd_ns = Integer.valueOf(month_) + 1;
        nam_bd_ns = Integer.valueOf(year_);

        if ((Integer.valueOf(month_) + n_Bear) > 12) {
          thang_kt_ns = (Integer.valueOf(month_) + n_Bear) - 12;
          nam_kt_ns = Integer.valueOf(year_) + 1;
        } else {
          thang_kt_ns = Integer.valueOf(month_) + n_Bear;
          nam_kt_ns = Integer.valueOf(year_);
        }
      }
    }
    //
    String tgian_bd_ns = String.valueOf(thang_bd_ns) + "/" + String.valueOf(nam_bd_ns);
    String tgian_kt_ns = String.valueOf(thang_kt_ns) + "/" + String.valueOf(nam_kt_ns);
    String tgian_ns = tgian_bd_ns + " --> " + tgian_kt_ns;// dung ghi chu
    // cho Nsan

    // * End 15/10/2011 xet ghi chu cho Nsan

    // ***Begin sua 15/05/2013
    if (debt_h <= 0) {
      debt_h = 0;
    } // set de tranh gia tri am(neu co)

    if (num_h > 0) {
      debt_h = 0;
    } // gan ao de neu num_h > 0 thi khong xet tru no ben duoi nua

    if (Integer.valueOf(day_bear_) <= 15) {
      num_ns = 0;
    } else {
      num_ns = 1;
    }

    switch (Integer.valueOf(month_)) {

      case 1:
      case 7:
        num_max = 6;
        break;

      case 2:
      case 8:
        num_max = 5;
        break;

      case 3:
      case 9:
        num_max = 4;
        break;

      case 4:
      case 10:
        num_max = 3;
        break;

      case 5:
      case 11:
        num_max = 2;
        break;

      case 6:
      case 12:
        num_max = 1;
        break;
    }
    // ***End sua 15/05/2013

    // nghi san k dieu chinh luong, tru bu tiep voi muc luong moi va ky
    // trinh la muc luong moi tai thoi diem nghi san
    note = note + "  //=> THANG " + month_ + " :";
    if (num_h <= 0) {
      num_h = 0;
    }

    switch (Integer.valueOf(month_)) {

      case 1:
      case 7:
        num = num_ns;
        num_recount = num + debt_h;// 21/06/2012 nsu yc k tru tien nhung
        // thang NSan
        money = heso_1_5 * num_recount;
        note = note + " -- Dang ki NS tu: " + bear_date_b_str + " den: " + bear_date_e_str + ": " + n_Bear + "T("
            + tgian_ns + ") => tru " + num_recount + "T (" + debt_h + "T no cu)";
        debt = 0;
        flag_DC_luong = false;
        obj_health_r.setSALARY_B(salary_e);

        if (debt_h > 0) {
          debt_old = num_recount - num_ns;
        } else {
          debt_old = 0;
        }

        break;

      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
        if (num_h > 0) {
          debt_h = 0;
        }

        if (num_ns == 1 && num_h > 0) {
          flag_DC_luong = true;
        } else {
          flag_DC_luong = false;
        }

        if (num_h > 0) // Neu num >= 1 thi num_recount khong cong them debt
        {// 21/06/2012 Dau quy da tru den thang bd dk Nsan --> tra lai tien
          num = num_ns;
          num_recount = 0;
          money = heso_1_5 * num_recount;
          num_return = num_h - num_ns;
          money_return = (long) (-num_return * heso_1_5_return);
          note = note + " -- Dang ki NS tu: " + bear_date_b_str + " den: " + bear_date_e_str + ": " + n_Bear + "T("
              + tgian_ns + ") => tru " + num_recount + "T -- tra lai tien " + num_return + "T ";
          debt = 0;
        } else// num_h <= 0
        {
          num = num_ns;
          debt_old = debt_h - num_max;
          if (debt_old < 0) {
            debt_old = 0;
          }
          num_recount = num + debt_old;
          money = heso_1_5 * num_recount;
          note = note + " -- Dang ki NS tu: " + bear_date_b_str + " den: " + bear_date_e_str + ": " + n_Bear + "T("
              + tgian_ns + ") => tru " + num_recount + "T(" + debt_old + "T no cu)";
          debt = 0;
        }

        break;

      default:
        break;
    }

    // dieu chinh luong tai thang cuoi truoc khi DK_NS, no phai chiu BH
    // thang do.
    if (flag_DC_luong) {
      if (salary_e <= obj_health_r.getSALARY_B()) {
        money_dc = 0;
      } else {
        note = note + " -- DC luong T" + month_;
        money_dc = (long) ((salary_e - obj_health_r.getSALARY_B()) * (1.5 / 100));// 10/09/2011
        // Ngan
        // sua
        // so
        // tien
        // dieu
        // chinh

      }

    }

    money = money + money_dc + money_return;// vi gio cong ca tien tra lai
    // de tinh so tien bi tru
    money_goc = money;

    list_recount =
        ReCount_ns_new(empsn, money, num_recount, money_return, money_dc, date_input, debt_old, num_ns, num_h);// d/c luong max
    // nhat la 1

    if (list_recount != null) {
      // neu co kha nang tru dc het tien thi lay NUM

      num = list_recount.get(0);
      debt = list_recount.get(1);
      money = list_recount.get(2);
      note_int = list_recount.get(3);
      debt_dc = list_recount.get(4);
      sothang_dc = list_recount.get(5);

      long num_tru = num_recount - debt;// 26/09/2011 Ngan them(get(1) so
      // thang no sau khi da xet

      if (note_int == 0) {
        if (debt_dc == 0 && sothang_dc == 2) {

          note_recount = " --> Xet luong thuc nhan chi d/c luong duoc " + sothang_dc + "T(" + month_ + " --> "
              + (Integer.valueOf(month_) + 1) + ") + tru : " + num_tru + "T -- Con no " + debt + "T";
        } else if (debt_dc == 0 && sothang_dc == 1) {
          note_recount = " --> Xet luong thuc nhan chi d/c luong duoc " + sothang_dc + "T(" + month_ + ") + tru : "
              + num_tru + "T -- Con no " + debt + "T";
        } else if (debt_dc == 0 && sothang_dc == 0) {
          note_recount = " --> Xet luong thuc nhan chi tru duoc " + num_tru + " T, Con no " + debt + "T";
        } else if (debt_dc == 1) {
          note_recount = " --> Xet luong thuc nhan khong du tien de tru dieu chinh, Con no " + debt + "T";

        } else// khong co su thay doi luong bao gom ca luong tai thang
        // ky trinh < luong dau quy (lay luong dau quy) _ gan
        // debt_dc = 4 cho truong hop nay
        {
          note_recount = " --> Xet luong thuc nhan tru duoc: " + num_tru + "T  //--Con no: " + debt + "T";
        }
      } else {// Neu tong luong ok se tru d/c luong tai thang nsan:vd nsan
        // 06/02-->06/05 ma luong t02<> t01 thi d/c luong t02 va t03
        note_recount = "-- Luong OK ";
      }

    }

    if (num < 0) {
      num = 0;
    }

    obj_health_r.setSTATUS(Long.valueOf(0)); // chuyen sang trang thai theo
    // doi NS
    obj_health_r.setNUM(num);
    obj_health_r.setMONEY(money);
    obj_health_r.setDEBT(debt);
    obj_health_r.setNOTE(note + note_recount);
    obj_health_r.setNUM_USED(num_used_ + 1);
    obj_health_r.setBEAR_DATE_E(bear_date_e);
    obj_health_r.setDEBT_DC(debt_dc);

    // ====k==============
    obj_health_r.setLANCHAY((long) LAN_CHAY);
    obj_health_r.setCOMMIT(1l);
    obj_health_r.setSTATUS_MONTH((long) K_N_N_HEALTH_R.NGHI_SAN_RA);
    // ====k==============

    UPDATE_DATA_BT(obj_health_r);// Vi Nsan tru duocc hay k tru duoc cung
    // lay luong luc bd nghi san

  }

  // flag_new : xet nhung thang Tang moi - NV
  // kiểm tra hiện trạng nhân viên
  // thuộc diện tăng mới 1 tây
  // nghỉ việc, nghỉ sản flag_new = false
  private void Update_Emp_Off_Work(K_N_N_HEALTH_R obj_health_r, OBJ_EMPSN obj_emp, boolean flag_new) {

    money_goc = 0;// 25/12/2012
    String empsn = obj_health_r.getEMPSN();
    // String month_today = getMonthOfToDay();
    // String year1 = getYearOfToDay();
    String month = sf.format(date_input).substring(3, 5);
    String year = sf.format(date_input).substring(6, 10);

    // lấy lương theo ngày nhập
    long salary_b = obj_emp.Get_Salary_Basic(empsn, date_input);// old:(htai
    // minh cu
    // gan =
    // luong moi
    // roi xuong
    // duoi xet
    // lai chu
    // dat = 0
    // thi nguy
    // hiem qua
    // hihi
    // 15/10/2012 lay luong cho nghi viec theo mail ngay 26/09/2012 c.Uyen
    // TB
    if (flag_new || month.equals("01") || month.equals("07")) {
      // ||month.equals("04")||month.equals("10")){
      salary_b = obj_emp.Get_Salary_Basic(empsn, date_input);
    } else {
      salary_b = obj_emp.Get_Salary_For_QuitW(empsn, date_input);
    }
    // lấy nhân viên theo ngày.
    // nhân viên trong n_n_health
    // lấy lương
    // getnum số tháng đã đóng tiền bảo hiểm
    // ngày trả thẻ???????? >=15
    long num = obj_health_r.getNUM() == null ? 0 : obj_health_r.getNUM();
    long num_h = obj_health_r.getNUM() == null ? 0 : obj_health_r.getNUM();
    long debt = obj_health_r.getDEBT() == null ? 0 : obj_health_r.getDEBT();

    String note_h = obj_health_r.getNOTE();
    String note = obj_health_r.getNOTE();
    String note_recount = "";
    long note_int = 1;
    float heso_1_5 = (float) (1.5 * salary_b) / 100; // he so 1.5%/T
    float heso_3_0 = (float) (3 * salary_b) / 100; // he so thu hoi 3%/T
    float heso_1_5_return = (float) (1.5 * obj_health_r.getSALARY_B()) / 100; // he
    // so
    // 1.5%/T
    /*
     * Thay doi dk ngay tra the ke tu 07/2013, bat dau sua 18/07/2013, HA
     * <07/2013 : < ngay 10 se ko chiu phi >=07/2013: < ngay 15 se ko chiu
     * phi SUA GHI CHU TT > 10 --> TT > 15 VA TT <= 10 --> TT <= 15
     */
    // Date date_now_10 = OBJ_UTILITY.MONTH_NOW("10", date_input);
    Date date_now_10 = OBJ_UTILITY.MONTH_NOW("19", date_input);
    boolean flag_DC_luong = false;

    // lấy sô ngày trong tháng không tính ngày chủ nhật.
    float day_of_month_not_sun = obj_util.GET_NUM_DAY_OF_MONTH_NOT_SUN(date_input);
    Date date_return_card = obj_emp.Get_Return_Card_Date(); // ngay tra the
    boolean flag_trathe = false;// Ngan them 09/08/2011 vi neu tra the truoc
    // ngay 10 cua dau quy thi num = 0
    long flag_dc_luong_trathe = 0;// Ngan them neu = 1 la k xet dieu chinh
    // luong trong Recount NV
    // *begin 10/10/2011_N them de xet dang nghi san ma nghi viec
    //
    Date bear_date_e = null;
    Date bear_date_b = null;
    bear_date_b = obj_emp.Get_Bear_Date_Begin();

    if (bear_date_b == null) {
      bear_date_e = null;
    } else {
      // bear_date_e = OBJ_UTILITY.MONTH_ROLL(bear_date_b, true, n_Bear);
      // //old: bear_date_e = obj_health_r.getBEAR_DATE_E(); 07/03/2013 bo
      // k lay tu bang n_n_health ma lay tu bang n_time_bear vi nsu co the
      // thay doi tgian nsan
      // không đồng bộ dữ liệu
      bear_date_e = obj_emp.Get_Bear_Date_End();
    }

    Date date_now_15 = OBJ_UTILITY.MONTH_NOW("19", date_input);

    // *end 10/10/2011_N them de xet dang nghi san ma nghi viec
    long money_dc = 0; // so tien dieu chinh
    long money_return = 0; // so tien tra lai cho nhan vien

    long num_recovery = 0; // num số tháng thu hồi
    // từ số tháng thu hồi mới xác định số tiền thu hồi
    long money_recovery = 0; // so tien thu hoisa

    // long num_ht = 0; //de xet khi k du tien de tru(num tru tien hien tai)
    long money = 0; // so tien BHYT cua thang hien tai

    long num_debt = 0; // so thang no old
    long debt_dc = 0;
    long num_max = 0;
    long num_recount = 0;
    long debt_old = 0;
    long num_return = 0;

    // Bo sung 02/11/2012, thay doi dk mua BHXHTN , so ngay ko luong>=14 la
    // ko mua
    // tuong duong songaycong>TS_ngaytrongthang-14 thi mua
    // HA
    // day of moth not sun lấy số ngày trong tháng
    float dk_ngaycong = day_of_month_not_sun - 14;
    long debt_h = obj_health_r.getDEBT() == null ? 0 : obj_health_r.getDEBT();
    // số tháng nợ
    if (debt_h == 0) {
      num_debt = 0;
    } // Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0
      // de k lay gia tri am

    note_h = note_h + " __ || ==>> THANG " + month + ":";

    num = num - 1; // giam so num cho thang truoc
    num_h = num;

    if (num <= 0) {
      num = 0;
      if (bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0) {
        num_h = num_h;
      } else {
        num_h = 0;
      }
    }
    /*
     * if(empsn.equals("07080562")){ System.out.println("CHECK "); }
     */

    if (bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)// Vi neu dang nghi san ma nghi
    // viec thi update lai num_h
    // vi truoc khi chay qua xet
    // nghi viec da update_data
    // cho nsan roi
    {

      note = note + " -->Nviec";
      if (num_h <= 0) {
        num_h = num_h + 1;
      }
    } else {
      // if(Integer.valueOf(month) == 1 || Integer.valueOf(month) == 4 ||
      // Integer.valueOf(month) == 7 || Integer.valueOf(month) == 10){
      if (Integer.valueOf(month) == 1 || Integer.valueOf(month) == 7) {
        obj_health_r.setSALARY_B(salary_b);
      }
    }

    // *Begin 18/05/2013
    switch (Integer.valueOf(month)) {

      case 1:
      case 7:
        num_max = 6;
        break;

      case 2:
      case 8:
        num_max = 5;
        break;

      case 3:
      case 9:
        num_max = 4;
        break;

      case 4:
      case 10:
        num_max = 3;
        break;

      case 5:
      case 11:
        num_max = 2;
        break;

      case 6:
      case 12:
        num_max = 1;
        break;
    }

    if (debt_h < 0) {
      debt_h = 0;
    }
    if (num_h < 0) {
      num_h = 0;
    }
    // *End 18/05/2013

    if (obj_emp.Get_Status_Card()) { // Co tra the
      flag_trathe = true;

      if (date_return_card.after(date_now_10)) { // tra the sau ngay 10
        flag_DC_luong = true;

        if (obj_emp.Get_WORK_DAY() > dk_ngaycong) { // DC (Tong so ngay
          // di lam > Tong so
          // ngay thuc te
          // trong thang - 14)
          // if(20 >dk_ngaycong){//Ngan Test

          num = 1;
          if (bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0) // xet cho neu
          // nsan vao -->
          // nviec, dang
          // nsan -->
          // Nviec
          {
            num = 0;
          }

          if (flag_new || month.equals("01") || month.equals("07")) {
            // ||month.equals("04")||month.equals("10")){ // OK

            if (debt_h == 0) {
              num_recount = num;
              num_recovery = 0;
              money = (long) (num_recount * heso_1_5);
              money_recovery = (long) (num_recovery * heso_3_0);
              note = "-- NV -- TT > 15 -- DC => tru " + num_recount + "T";
              debt = 0;
              flag_DC_luong = false;
            } else {
              num_recount = num + debt_h;
              num_recovery = 0;
              debt_old = debt_h;
              if (debt_old < 0) {
                debt_old = 0;
              }
              money = (long) (num_recount * heso_1_5);
              money_recovery = (long) (num_recovery * heso_3_0);
              note = "-- NV -- TT > 15 -- DC => tru " + num_recount + "T(" + debt_old + "T no cu)";
              debt = 0;
              flag_DC_luong = false;
            }

          } else {

            if (num_h > 0) {
              debt_h = 0;
            }

            if (num == 1 && num_h > 0) {
              flag_DC_luong = true;
            } else {
              flag_DC_luong = false;
            }

            if (num_h > 0) {
              num_recount = 0;
              money = (long) (num_recount * heso_1_5);
              num_return = num_h - num;
              money_return = (long) (-num_return * heso_1_5_return);
              num_recovery = 0;
              money_recovery = (long) (num_recovery * heso_3_0);
              note = "-- NV -- TT > 15 -- DC => tru " + num_recount + "T + TH " + num_recovery + "T + Tra lai tien "
                  + num_return + "T";
              debt = 0;
            } else {
              debt_old = debt_h - num_max;
              if (debt_old < 0) {
                debt_old = 0;
              }
              num_recount = num + debt_old;
              money = (long) (num_recount * heso_1_5);
              num_return = 0;
              money_return = (long) (-num_return * heso_1_5_return);
              num_recovery = 0;
              money_recovery = (long) (num_recovery * heso_3_0);
              note = "-- NV -- TT > 15 -- DC => tru " + num_recount + "T(" + debt_old + "T no cu) + TH " + num_recovery
                  + "T + Tra lai tien " + num_return + "T";
              debt = 0;
            }

          }

        } else { // NV -> (Tra the > 10) -> KDC -> co tra the -> tra lai
          // tien
          // -> khong du cong -> xet thu hoi 3%
          num = 1;
          num_recovery = 1;
          if (bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0) // xet cho neu
          // nsan vao -->
          // nviec, dang
          // nsan -->
          // Nviec
          {
            num = 0;
            num_recovery = 0;
          }

          if (flag_new || month.equals("01") || month.equals("07")) {
            // ||month.equals("04")||month.equals("10")){ // OK

            if (debt_h == 0) {
              num_recount = num;
              money = (long) (num_recount * heso_1_5);
              money_recovery = (long) (num_recovery * heso_3_0);
              note = "-- NV -- TT > 15 -- KDC => tru " + num_recount + "T + TH " + num_recovery + "T";
              debt = 0;
              flag_DC_luong = false;
            } else {
              debt_old = debt_h;
              if (debt_old < 0) {
                debt_old = 0;
              }
              num_recount = num + debt_h;
              money = (long) (num_recount * heso_1_5);
              money_recovery = (long) (num_recovery * heso_3_0);
              note = "-- NV -- TT > 15 -- KDC => tru " + num_recount + "T(" + debt_old + "T no cu) + TH " + num_recovery
                  + "T";
              debt = 0;
              flag_DC_luong = false;
            }

          } else {

            if (num_h > 0) {
              debt_h = 0;
            }

            if (num == 1 && num_h > 0) {
              flag_DC_luong = true;
            } else {
              flag_DC_luong = false;
            }

            if (num_h > 0) {
              num_recount = 0;
              money = (long) (num_recount * heso_1_5);
              num_return = num_h - num;
              money_return = (long) (-num_return * heso_1_5_return);
              money_recovery = (long) (num_recovery * heso_3_0);
              note = "-- NV -- TT > 15 -- KDC => tru " + num_recount + "T + TH " + num_recovery + "T + Tra lai tien "
                  + num_return + "T";
              debt = 0;
            } else {
              debt_old = debt_h - num_max;
              if (debt_old < 0) {
                debt_old = 0;
              }
              num_recount = num + debt_old;
              money = (long) (num_recount * heso_1_5);
              num_return = 0;
              money_return = (long) (-num_return * heso_1_5_return);
              money_recovery = (long) (num_recovery * heso_3_0);
              note = "-- NV -- TT > 15 -- KDC => tru " + num_recount + "T(" + debt_old + "T no cu) + TH " + num_recovery
                  + "T + Tra lai tien " + num_return + "T";
              debt = 0;
            }

          }

        }
      } else { // tra the truoc or = ngay 10, tra lai NUM thang
        if (flag_new || month.equals("01") || month.equals("07")) {
          // khong tham gia BHYT

          // flag_trathe = true;
          num = 0;
          num_recount = num + debt_h;
          debt_old = debt_h;
          if (debt_old < 0) {
            debt_old = 0;
          }
          num_recovery = 0;
          money = (long) (num_recount * heso_1_5);
          money_recovery = (long) (num_recovery * heso_3_0);
          note = "KHONG THAM GIA BH --- Tru " + debt_old + "T no cu";
          debt = 0;
          flag_DC_luong = false;
          // flag_dc_luong_trathe = 1;//= 1 k xet d/c luong trong
          // Recount_NV

        } else {
          num = 0;
          if (num_h > 0) {
            debt_h = 0;
          }
          flag_DC_luong = false;

          if (num_h > 0) {
            num_recount = 0;
            debt_old = 0;
            money = (long) (num_recount * heso_1_5);
            num_return = num_h;
            money_return = (long) (-num_return * heso_1_5_return);
            num_recovery = 0;
            money_recovery = (long) (num_recovery * heso_3_0);
            note = "-- NV -- TT <= 15 => tru " + debt_old + "T no cu + tra lai tien BH : " + num_return + "T";
            debt = 0;
          } else {
            debt_old = debt_h - num_max;
            if (debt_old < 0) {
              debt_old = 0;
            }
            num_recount = num + debt_old;
            money = (long) (num_recount * heso_1_5);
            num_return = 0;
            money_return = (long) (-num_return * heso_1_5_return);
            num_recovery = 0;
            money_recovery = (long) (num_recovery * heso_3_0);
            note = "-- -- NV -- TT <= 15 => tru " + num_recount + "T(" + debt_old + "T no cu) + tra lai tien BH : "
                + num_return + "T";
            debt = 0;
          }

        }
      }

    } else { // (IT)khong tra the
      flag_DC_luong = true;
      flag_trathe = false;

      if (obj_emp.Get_WORK_DAY() > dk_ngaycong) {
        // if(20 >dk_ngaycong){//Ngan test
        // NV - KTT - DC

        num = num_max;
        num_recovery = num_max - 1;

        if (bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0) {
          num = num_max - 1;
        }

        if (month.equals("01") || month.equals("07")) {

          debt_old = debt_h;
          num_recount = num + debt_old;
          money = (long) (num_recount * heso_1_5);
          money_recovery = (long) (num_recovery * heso_3_0);
          note = "-- NV -- K_TT -- DC => tru " + num_recount + "T( " + debt_old + "T no cu ) + thu hoi " + num_recovery
              + "T";
          debt = 0;
          flag_DC_luong = false;

        } else // T2,3,4,5,6,8,9,10,11,12
        {
          if (num_h > 0) {
            flag_DC_luong = true;
          } else {
            flag_DC_luong = false;
          }

          if (num_h > 0) // num_h > 0: k con no cu
          {
            if (num_h >= num_max) {
              num_recount = 0;
              money = (long) (num_recount * heso_1_5);
              money_recovery = (long) (num_recovery * heso_3_0);
              note = "-- NV -- K_TT -- DC => tru " + num_recount + "T( " + debt_old + "T no cu ) + thu hoi "
                  + num_recovery + "T";
              debt = 0;
            } else // num_h < num_max
            {
              num_recount = num_max - num_h; // num_max - num_h:
              // so thang con no
              // chua tru duoc
              money = (long) (num_recount * heso_1_5);
              money_recovery = (long) (num_recovery * heso_3_0);
              note = "-- NV -- K_TT -- DC => tru " + num_recount + "T( " + debt_old + "T no cu ) + thu hoi "
                  + num_recovery + "T";
              debt = 0;
            }
          } else // num_h <= 0 : co the con no cu chua tru duoc
          {
            debt_old = debt_h - num_max;
            if (debt_old < 0) {
              debt_old = 0;
            }
            num_recount = (num_max - num_h) + debt_old;
            money = (long) (num_recount * heso_1_5);
            money_recovery = (long) (num_recovery * heso_3_0);
            note = "-- NV -- K_TT -- DC => tru " + num_recount + "T( " + debt_old + "T no cu ) + thu hoi "
                + num_recovery + "T";
            debt = 0;
          }

        }
      } else { // NV - KTT - KDC

        num = num_max;
        num_recovery = num;

        if (bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0) {
          num = num_max - 1;
          num_recovery = num;
        }

        if (month.equals("01") || month.equals("07")) {

          debt_old = debt_h;
          if (debt_old < 0) {
            debt_old = 0;
          }
          num_recount = num + debt_old;
          money = (long) (num_recount * heso_1_5);
          money_recovery = (long) (num_recovery * heso_3_0);
          note = "-- NV -- K_TT -- KDC => tru " + num_recount + "T( " + debt_old + "T no cu ) + thu hoi " + num_recovery
              + "T";
          debt = 0;
          flag_DC_luong = false;

        } else // T2,3,4,5,6,8,9,10,11,12
        {
          if (num_h > 0) {
            flag_DC_luong = true;
          } else {
            flag_DC_luong = false;
          }

          if (num_h > 0) // num_h > 0: k con no cu
          {
            if (num_h >= num_max) {
              num_recount = 0;
              money = (long) (num_recount * heso_1_5);
              money_recovery = (long) (num_recovery * heso_3_0);
              note = "-- NV -- K_TT -- KDC => tru " + num_recount + "T( " + debt_old + "T no cu ) + thu hoi "
                  + num_recovery + "T";
              debt = 0;
            } else // num_h < num_max
            {
              num_recount = num_max - num_h; // num_max - num_h:
              // so thang con no
              // chua tru duoc
              money = (long) (num_recount * heso_1_5);
              money_recovery = (long) (num_recovery * heso_3_0);
              note = "-- NV -- K_TT -- KDC => tru " + num_recount + "T( " + debt_old + "T no cu ) + thu hoi "
                  + num_recovery + "T";
              debt = 0;
            }
          } else // num_h <= 0 : co the con no cu chua tru duoc
          {
            debt_old = debt_h - num_max;
            if (debt_old < 0) {
              debt_old = 0;
            }
            num_recount = (num_max - num_h) + debt_old;
            money = (long) (num_recount * heso_1_5);
            money_recovery = (long) (num_recovery * heso_3_0);
            note = "-- NV -- K_TT -- KDC => tru " + num_recount + "T( " + debt_old + "T no cu ) + thu hoi "
                + num_recovery + "T";
            debt = 0;
          }

        }

      }
    }

    if (flag_DC_luong) {
      // Ngan sua neu nv KTT o giua quy ma dc luong thi
      // if( (salary_b > obj_health_r.getSALARY_B()) && num_h >= 1 old
      if (salary_b > obj_health_r.getSALARY_B() && num >= 1 && num_h >= 1 && date_return_card.after(date_now_10)) {
        note = note + " + DC luong " + num + "T(" + month + " --> " + ((Integer.valueOf(month) + num) - 1) + ")";// 10/09/2011
        // Ngan
        // sua
        // money = money + (long)((salary_b -
        // obj_health_r.getSALARY_B()) * (num*1.5)/100);// dieu chinh
        // luong
        money_dc = (long) ((salary_b - obj_health_r.getSALARY_B()) * (num * 1.5) / 100);// dieu chinh luong
      } else if (salary_b > obj_health_r.getSALARY_B() && num_h >= 1 && obj_emp.Get_Status_Card() == false) {
        note = note + " + DC luong " + num_h + "T(" + month + " --> " + ((Integer.valueOf(month) + num_h) - 1) + ")";// 10/09/2011
        // Ngan
        // sua
        // money = money + (long)((salary_b -
        // obj_health_r.getSALARY_B()) * (num*1.5)/100);// dieu chinh
        // luong
        money_dc = (long) ((salary_b - obj_health_r.getSALARY_B()) * (num_h * 1.5) / 100);// dieu chinh luong
      }
    }

    note = note_h + note;
    System.out.println(empsn);
    System.out.println(note);
    // money = money + money_debt + money_recovery + money_dc+money_return;
    money = money + money_recovery + money_dc + money_return;
    money_goc = money;

    ArrayList<Long> list_recount = new ArrayList<Long>();
    // list_recount = ReCount_NV(empsn,
    // money,money_dc,num_recount,num_debt,num_recovery,num_h,date_input,flag_dc_luong_trathe,salary_b);
    // //15/10/2012 them salary_b de lay luong Nviec theo mail 26/09/2012
    // C.Uyen TB
    list_recount = ReCount_NV(empsn, money, money_dc, num_recount, num_recovery, num_h, date_input,
        flag_dc_luong_trathe, salary_b, money_return, debt_old, num, flag_trathe); // 15/10/2012
    // them
    // salary_b
    // de
    // lay
    // luong
    // Nviec
    // theo
    // mail
    // 26/09/2012
    // C.Uyen
    // TB

    if (list_recount != null) {
      long sothang_dc = 0;
      long sothang_thuhoi = 0;
      /*
       * long debt_note = 0; //debt de ghi vao note la tru duoc bao nhieu
       * thang no long sothang_dc = 0; long sothang_thuhoi = 0; if (num_h
       * >= 1 && (date_return_card.after(date_now_10) ||
       * obj_emp.Get_Status_Card()==false )) { num = list_recount.get(0)+
       * num_h; }else {num = list_recount.get(0);}
       */

      num = list_recount.get(0);
      debt = list_recount.get(1);
      money = list_recount.get(2);
      note_int = list_recount.get(3);
      debt_dc = list_recount.get(4);
      sothang_dc = list_recount.get(5);
      sothang_thuhoi = list_recount.get(6);

      long num_tru = num_recount - debt;
      if (num_tru <= 0) {
        num_tru = 0;
      }

      if (note_int == 0) {
        if (debt_dc == 0 && sothang_dc >= num && num_h > 0 && flag_trathe) { // 01/03/2013 sua =2 -> >= num_h

          note_recount = " --> Xet luong thuc nhan chi d/c luong duoc " + num_h + "T(" + month + " --> "
              + (Integer.valueOf(month) + (num_h - 1)) + ") + tru : " + num_tru + "T + TH " + sothang_thuhoi
              + "T--con no " + debt + "T";
        } else if (debt_dc == 0 && sothang_dc >= num_h && num_h > 0 && flag_trathe == false) {
          note_recount = " --> Xet luong thuc nhan chi d/c luong duoc " + num_h + "T(" + month + " --> "
              + (Integer.valueOf(month) + (num_h - 1)) + ") + tru : " + num_tru + "T + TH " + sothang_thuhoi
              + "T--con no " + debt + "T";
        } else if (debt_dc == 1) {
          note_recount = " --> Xet luong thuc nhan khong du tien de tru //--Con no: " + debt + "T";
        } else// khong co su thay doi luong bao gom ca luong tai thang
        // ky trinh < luong dau quy (lay luong dau quy) _ gan
        // debt_dc = 4 cho truong hop nay
        {
          note_recount = " --> Xet luong thuc nhan tru duoc: " + num_tru + "T + TH " + sothang_thuhoi + "T --Con no: "
              + debt + "T";
        }
      } else {// Neu tong luong ok se tru d/c luong tai thang nsan:vd nsan
        // 06/02-->06/05 ma luong t02<> t01 thi d/c luong t02 va t03
        note_recount = "-- Luong OK ";
      }
    }

    // *Begin xet check_condition_quit_work 29/02/2012
    if (check_condition_quit_work_again(empsn)) {
      num = 0;
      money = 0;
      money_goc = 0;
      note = note_work_again;
    }
    // *End xet check_condition_quit_work 29/02/2012

    obj_health_r.setNUM(num);
    obj_health_r.setSALARY_B(salary_cb_nv);
    obj_health_r.setSALARY_M(salary_b * num); // Nhan luong cho NV dua theo
    // so thang co the tru dc
    // tien
    obj_health_r.setSTATUS(-1l);
    obj_health_r.setMONEY(money);
    obj_health_r.setDEBT(debt);
    obj_health_r.setNOTE(note + note_recount);
    obj_health_r.setDEBT_DC(debt_dc);

    obj_health_r.setSTATUS_MONTH((long) K_N_N_HEALTH_R.NGHI_VIEC);

    // ==============================
    obj_health_r.setLANCHAY((long) LAN_CHAY);
    obj_health_r.setCOMMIT(1l);
    // ==============================
    UPDATE_DATA_BT(obj_health_r);
  }

  /**
   * Cap nhat nhung nguoi dang ky tham gia BHYT : empsn - salary_b - status -
   * num (-1)
   * 
   * @param date_input
   * @return
   */

  // cập nhat nhân viên mới kí hợp đồng
  // n_labour t.times số lần ký = 1;
  // n_newworker_test nhân viên thử việc, điều động khu
  // nhân viên mới kí hợp đồng ngày 1 tháng hiện tại và ngày 15 tháng trước.
  /**
   * Detail: cập nhật nhân viên mới kí hợp đồng. (n_labour) t.times số lần ký
   * = 1( ngày 1 và ngày 15 tháng trc). n_newworker_test nhân viên thử việc,
   * điều động khu
   * 
   * @return string error.
   */
  private String Add_Emp_New() {

    // cập nhật thêm những nhân viên mới kí hợp đồng ngày 1 trong tháng đang
    // xét và ngày 15 trong tháng trước

    // điều kiện mua bảo hiểm
    // đủ ngày công với lương.
    String str_error_ = "OK";
    long salary_ = 0;
    K_N_N_HEALTH_R obj_save_;
    OBJ_EMPSN obj_emp_ = new OBJ_EMPSN();

    String date_str_01_ = sf.format(OBJ_UTILITY.MONTH_NOW("01", date_input));
    // ngày tháng trước
    // lay nhung thang moi ki hop dong ngay 01 va ngay 15 thang truoc de
    // theo doi
    String date_str_pre_15_ = sf.format(OBJ_UTILITY.MONTH_PRE("15", date_input));

    // t.times số lần ký hợp đồng
    // xét xưởng đang xuất KT bỏ if

    String month_ = getMonthOfDate(date_input);
    String year_ = getYearOfDate(date_input);
    List<String> list_ = null;
    try {

      list_ = HealthProgramDAO.get_NEW_EMP(date_str_01_, date_str_pre_15_, month_, year_);
    } catch (SQLException e1) {
      list_ = null;
      e1.printStackTrace();
      new MessageDialog("Error", "Có lỗi phát sinh " + e1.getMessage(), MessageDialog.TYPE_ERROR).show();
    }

    // duyệt những nhân viên mới kí hợp đồng
    for (String empsn : list_) {
      // check??
      // những nhân viên ko mua bảo hiểm.
      // bỏ qua.
      // chỉnh lại bỏ trong câu sql
      madv = obj_emp.Get_depsn(empsn, date_input);
      if (!madv.equals("ENULL")) {
        if (thuoc_xuong(madv)) {
          //
          K_N_N_HEALTH_R empsnFind = obj_dao.findById(empsn);

          try {
            if (empsnFind == null) {
              System.out.println(" Add_Emp_New()-----" + empsn + "--------");
              obj_save_ = new K_N_N_HEALTH_R();
              salary_ = obj_emp_.Get_Salary_Basic(empsn, date_input);
              obj_save_.setEMPSN(empsn);
              obj_save_.setSALARY_B(salary_);
              obj_save_.setSTATUS((long) K_N_N_HEALTH_R.LAM_VIEC_BINH_THUONG);
              // số tháng đã đóng tiền BH
              obj_save_.setNUM(0l);
              // số quý tham gia bảo hiểm
              obj_save_.setNUM_USED(0l);
              obj_save_.setLANCHAY((long) LAN_CHAY);
              obj_save_.setDEPSN(madv);
              obj_save_.setSTATUS_MONTH((long) K_N_N_HEALTH_R.KY_HOP_DONG);
              obj_dao.save(obj_save_);
            }
          } catch (Exception e) {
            // chưa xem
            new MessageDialog("Error", "Có lỗi xảy ra trong quá trình cập nhật " + empsn + "\n" + e.getMessage(),
                MessageDialog.CONTROLS_OK).show();
            str_error_ = str_error_ + empsn;
          }

        }
      } // end if
    } // end for list
    return str_error_;

  }

  // *Begin 14/08/2012 ADD NHUNG NGUOI NGHI VIEC TANG LAI TRONG N_EMP_QUIT:
  // co nhap thang tang lai la lay k quan tam from_date vi co the tu lt2
  // chuyen ve
  // Nsu k nhap bao giam ma chi nhap cho co nghi viec de tang moi lai. Viec
  // nay Nsu phai tu quan ly
  // VOI THANG TANG LAI = THANG KY TRINH
  // -TY LE TANG LAI = 1 --> STATUS_TT_TMOI = 1
  // -TY LE TANG LAI = 20 --> STATUS_TT_TMOI = 3????
  // STATUS = 1, NUM_USED = 0, LOCK_DATE = NULL

  // date_input <16
  /**
   * Detail: cập nhật những người nghỉ việc tăng lại( trong table N_EMP_QUIT).
   * Xét tỉ lệ tăng lai 1 tây, 20 tây. tỷ lệ tăng lại =1 -> status_tt_tmoi = 1
   * tỷ lệ tăng lại =20 -> status_tt_tmoi = 3 status=1, num_used= 0, lock_date
   * = null date_input < 16
   * 
   * @author
   */
  private void Add_Emp_Quit_Work_Again() {
    //
    OBJ_EMPSN obj_emp_ = new OBJ_EMPSN();
    // ???
    // ngày 01/08???
    // ngay dau thang
    //
    String date_str_01_ = sf.format(OBJ_UTILITY.MONTH_NOW("01", date_input));
    Date date_01 = null;
    try {
      date_01 = sf.parse(date_str_01_);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    // xét tỉ lệ tăng lại 1 tây hoặc 20 tây.
    // lấy những nhân viên tăng mới lại trong tháng hiện tại đang update
    // truy vấn lấy xưởng đang xét ở đây luôn... null
    // String date_str_pre_15_ = sf.format(OBJ_UTILITY.MONTH_PRE("15",
    // date_input));
    // danh sách mã nhân viên tăng mới lại
    List<String> list_empsn_work_again = null;

    list_empsn_work_again = HealthProgramDAO.getN_EMP_QUIT(date_str_01_);

    /*
     * lấy những nhân viên tăng lại trong tháng và không thuộc diện những
     * nhân viên không mua bảo hiểm tăng lại 1, 20 tây 3 không trả thẻ tăng
     * lại 20 không trừ bảo hiểm y tế cho đến giá trị thẻ. update status
     * tháng tăng mới =3
     * 
     * tháng hết hạn thẻ lấy ở đâu ?????? limit_card
     */
    // ff
    for (String empsn : list_empsn_work_again) {
      //
      // đã có trong n_n_health
      K_N_N_HEALTH_R updateHealth = obj_dao.findById(empsn);
      // lấy lương căn bản theo ngày chạy ký trìn
      // data_input ngày chạy ký trình
      // join câu sql
      long salary_b = obj_emp_.Get_Salary_Basic(empsn, date_input);
      // lấy đơn vị theo ngày?????????????????
      madv = obj_emp.Get_depsn(empsn, date_input);

      // đơn vị thuộc xưởng đang xét.
      if (!madv.equals("ENULL")) {
        if (thuoc_xuong(madv)) {
          //
          // mã nhân viên tăng lại trong tháng.
          System.out.println("empsn " + empsn);

          // ============tăng lại 1 tây================
          String sql_empsn_increase_1 = "";
          sql_empsn_increase_1 = "SELECT DISTINCT T.EMPSN  FROM N_EMP_QUIT T\n" + "WHERE T.EMPSN = '" + empsn + "'\n"
              + "AND T.TYLE_TANGLAI = 1";
          // ?? xét báo giảm
          // danh sách tăng lại 1 tây
          List<String> lst_empsn_work_again_fact = null;
          lst_empsn_work_again_fact = obj_util.Exe_Sql_String(sql_empsn_increase_1);
          // ===============================updata==================================================//
          // xét đã có trong n_N_health chưa
          // tăng 1 tây
          if (lst_empsn_work_again_fact.size() > 0) { // insert or
            // update tang 1
            // tay
            // ===========================tang
            // 1
            // tay=============================
            // if (updateHealth.getSTATUS() != 1)
            if (updateHealth == null)// khong co trong
            // N_N_HELTH_R
            // thi
            // insert
            {
              K_N_N_HEALTH_R new_health_R = new K_N_N_HEALTH_R();
              new_health_R.setEMPSN(empsn);
              // bảo
              // hiểm.
              new_health_R.setSALARY_B(salary_b);
              new_health_R.setNUM_USED(0l);// số quý tham gia
              // bảo
              // hiểm.
              new_health_R.setSTATUS((long) K_N_N_HEALTH_R.LAM_VIEC_BINH_THUONG);

              new_health_R.setTHANG_TANGMOI(date_01);// tháng
              // tăng
              // mới
              new_health_R.setSTATUS_TT_TMOI((long) K_N_N_HEALTH_R.TRA_THE_TANGMOI_TRONGTHANG);// nhân
              // viên
              // trả
              // thẻ tăng mới
              // trong tháng..
              // tam thời test giờ là 1.
              new_health_R.setLANCHAY((long) LAN_CHAY);
              new_health_R.setSTATUS_MONTH((long) K_N_N_HEALTH_R.NGHI_VIEC_TANG_MOI);
              try {
                obj_dao.saveOrUpdate(new_health_R);
              } catch (Exception e) {
                System.out.println("Error " + empsn + "---" + e.getMessage());
              }
              // try {
              // HealthDAO.InsertK_N_N_HEALTH_R(new_health_R);
              // } catch (SQLException e) {
              // System.out.println(e.toString());
              // new MessageDialog("Error",
              // "Có lỗi phát sinh trong quá trình cập nhật\n"
              // + e.toString(),
              // MessageDialog.TYPE_ERROR).show();
              // }

            } else// co thi update di lam lai 1 tay
            {

              // phải đủ các trường dữ liệu mới update được
              // updateHealth.setDATE_B(null);
              updateHealth.setNUM((long) (0));
              updateHealth.setSALARY_B(salary_b);
              updateHealth.setLIMIT_CARD(null);
              // so quy tham gia
              updateHealth.setNUM_USED((long) 0);
              // updateHealth.setNOTE(null);
              updateHealth.setSTATUS((long) K_N_N_HEALTH_R.LAM_VIEC_BINH_THUONG);// cập
              // nhật
              // lại
              // trang
              // thái làm việc
              updateHealth.setDEBT((long) 0);
              updateHealth.setMONEY((long) 0);
              updateHealth.setLOCK_DATE(null);
              updateHealth.setDEPSN(madv);
              updateHealth.setSALARY_M((long) 0);
              updateHealth.setBEAR_DATE_E(null);
              updateHealth.setDEBT_DC(null);
              updateHealth.setTHANG_TANGMOI(date_01);
              updateHealth.setSTATUS_TT_TMOI((long) K_N_N_HEALTH_R.TRA_THE_TANGMOI_TRONGTHANG);
              updateHealth.setLANCHAY((long) LAN_CHAY);
              updateHealth.setSTATUS_MONTH((long) K_N_N_HEALTH_R.NGHI_VIEC_TANG_MOI);
              try {
                obj_dao.saveOrUpdate(updateHealth);
              } catch (Exception e) {
                System.out.println("Error " + empsn + "---" + e.getMessage());
              }
            }
            // end if (lst_empsn_work_again_fact.size() > 0)
            // ============tăng lại 1 tây================

          } else {
            // ================ insert or update tang 20
            // tay================
            // có xót không khi mình update cột lần chạy =1 sang
            // tháng tiếp theo nó có tính
            if (updateHealth == null)// khong co trong N_N_HELTH_R
            // thi
            // insert
            {
              // status_tt_tmoi
              String sql_i =
                  " INSERT INTO k_n_n_health_r(EMPSN,NUM,SALARY_B,NUM_USED,STATUS,THANG_TANGMOI,STATUS_TT_TMOI, LANCHAY, STATUS_MONTH)\n"
                      + " VALUES('" + empsn + "',0," + salary_b + ",0,1,TO_DATE('" + date_str_01_ + "','DD/MM/YYYY'),3,"
                      + (long) LAN_CHAY + "," + K_N_N_HEALTH_R.NGHI_VIEC_TANG_MOI + ")";
              // khoi them
              // 3 không trả thẻ tăng 20 tây
              OBJ_UTILITY obj_util = new OBJ_UTILITY();
              obj_util.Exe_Update_Sql(sql_i);

            } else// co thi update di lam lai 20 tay
            {
              /*
               * Begin 09/01/2013: xet xem so the neu la trang
               * thai = 1 (Nsu ycau update tay lay lai hien hanh
               * truoc do) nhung nguoi nay la nghi viec khong tra
               * the bao giam len bao hiem rui ma nsu chua giao ke
               * toan tinh luong thoi viec Truong hop nay o ky
               * trinh cty khong update ve tang 20 tay, ma giu
               * nguyen trang thai de no co trong ky trinh thang
               * hien tai ( tuy thuoc vao num) vd: 11080882,
               * 08100623
               */
              //
              if ((updateHealth.getSTATUS() == 1) || (updateHealth.getSTATUS() == 0)) {
                continue;
              } else
              // *End 09/01/2013:
              {
                updateHealth.setNUM((long) (0));
                updateHealth.setSALARY_B(salary_b);
                updateHealth.setNUM_USED((long) 0);
                updateHealth.setSTATUS((long) 1);
                updateHealth.setDEBT((long) 0);
                updateHealth.setMONEY((long) 0);
                updateHealth.setDEPSN(madv);
                updateHealth.setSALARY_M((long) 0);
                updateHealth.setTHANG_TANGMOI(date_01);
                updateHealth.setSTATUS_TT_TMOI((long) 3);
                updateHealth.setLANCHAY((long) LAN_CHAY);
                try {
                  obj_dao.update(updateHealth);
                } catch (Exception e) {
                  System.out.println("Error " + empsn + "---" + e.getMessage());
                }
              }

            }

          }
          // ================ insert or update tang 20
          // tay================

        } else {
          continue;
        }
      }
    }
  }

  // *End 14/08/2012 ADD NHUNG NGUOI NGHI VIEC TANG LAI TRONG N_EMP_QUIT:

  // (*)Ngan begin 04/07/2012_lay luong tru BHYT khi nghi viec khong du cong
  // *Begin select lay ra xem nguoi do co phai la tang moi tai thang bat ky
  // dua vao khong?
  /**
   * Detail: Lấy lương trừ bảo hiểm y tế khi nghỉ việc không đủ công. Lấy ra
   * xem người đó có phải là tăng mới tại tháng bất kì hay không.
   * 
   * @param empsn
   * @param date_input
   *            ngày check.
   * @return
   */
  private boolean Check_Emp_New(String empsn, Date date_input) {
    String sql = "";
    List<String> list_ = null;

    String date_str_01_ = sf.format(OBJ_UTILITY.MONTH_NOW("01", date_input));
    String date_str_pre_15_ = sf.format(OBJ_UTILITY.MONTH_PRE("15", date_input));
    String month_ = sf.format(date_input).substring(3, 5);
    String year_ = sf.format(date_input).substring(6, 10);

    sql = " SELECT t.empsn\n" +

        "FROM n_labour t" +

        " where (t.date_s = to_date('" + date_str_01_ + "','dd/mm/yyyy')\n" + "			or t.date_s = to_date('"
        + date_str_pre_15_ + "','dd/mm/yyyy'))\n" + " AND t.times = 1" +

        // " AND t.empsn ='12050070'"+
        "\n" +
        // add them nhung nhan vien dieu dong khu--thang nhap xuong moi
        // se la thang tang moi 02/12/2011
        " union\n" + "\n" + "select t.empsn\n" + "from n_newworker_test t,n_employee e,n_labour lb\n"
        + "where t.empsn = e.empsn\n" + "and e.empsn = lb.empsn\n" + "and t.dd_khu = '1'\n" + "and lb.clock = '1'\n"
        + "and to_char(e.ngaynx_moi,'mm/yyyy') = '" + month_ + "/" + year_ + "'" +
        // " AND t.empsn ='12050070'"+

        "";
    list_ = obj_util.Exe_Sql_String(sql);

    if (list_.size() == 0) {
      return false;// khong co tang moi tai date_input
    } else
      return true;// co tang moi tai date_input

  }

  // *End select lay ra xem nguoi do co phai la tang moi tai thang bat ky dua
  // vao khong?
  private String sql_select_combsaly(String empsn, String month, String year, String table) {
    String sql = "";
    sql = "select t.combsaly,t.ylbx from " + table + year + month + " t\n" + "where t.empsn = '" + empsn + "'\n"
        + "and t.ylbx is not null\n";

    return sql;
  }

  // **Begin select ylbx <> tu so the va bang luong dua vao
  //
  /**
   * Lấy lương trừ tiền bảo hiểm của empsn tính ngày công từ 1->15.( chưa
   * chỉnh lại)
   * 
   * @param empsn
   * @param date_input
   * @return long salary.
   */
  private long Check_tru_BHYT_tu_bangLuongKT(String empsn, Date date_input) {
    String sql = "";
    String sql1 = "";
    String month_ = sf.format(date_input).substring(3, 5);
    String year_ = sf.format(date_input).substring(6, 10);
    Date date_att = date_input;
    ArrayList<Object> list_ = null;
    ArrayList<Object> list_1 = null;
    long salary_ = 0;
    long ylbx = 0;

    // nhân viên nghỉ việc tăng lại
    if (Check_Emp_New(empsn, date_att)) {
      salary_ = obj_emp.Get_Salary_Basic(empsn, date_att);
    } else {

      // select lay ra xem nguoi do co phai la tang moi tai thang bat ky
      // dua vao khong?
      sql = sql_select_combsaly(empsn, month_, year_, "att");

      list_ = obj_util.Exe_sql_nfield_1row(sql, 2);

      if (list_.size() == 0) {
        sql1 = sql_select_combsaly(empsn, month_, year_, "attquit");

        if (list_1.size() == 0) {
          // salary_ = obj_emp.Get_Salary_Basic(empsn, date_att);
          salary_ = 0;// tiep tuc lui de tim xem nhung thang truoc co
          // tru YT khong?
        } else {
          ylbx = Integer.valueOf(list_.get(1).toString());
          if (ylbx == 0) {
            salary_ = 0;
          } else {
            salary_ = Integer.valueOf(list_1.get(0).toString());
          }
        }

      } else {
        ylbx = Integer.valueOf(list_.get(1).toString());
        if (ylbx == 0) {
          salary_ = 0;
        } else {
          salary_ = Integer.valueOf(list_.get(0).toString());
        }
      }
    }
    return salary_;
  }

  // Ngan end 04/07/2012_lay luong tru BHYT khi nghi viec khong du cong

  // thay doi moi ngay 16/10/2012 xet cho truong hop tang moi do ky hop dong
  // va dieu dong khu
  // điều kiện tăng mới và điều độnng khu???
  // xem cái này
  private boolean check_condition(String empsn) {// select count = 1 bo qua
    // gan flag = flase
    boolean flag = true;// false bo qua khong add vao theo doi,true add vao
    // theo doi
    String date_str_01_ = sf.format(OBJ_UTILITY.MONTH_NOW("01", date_input));
    String my_str = sf.format(date_input).substring(3, 10);
    OBJ_UTILITY obj_Utility = new OBJ_UTILITY();

    // n_not_insurance những nhân viên không mua bảo hiểm
    String sql_1 =

        "SELECT COUNT(T.EMPSN) FROM N_NOT_INSURANCE T\n"
            + "WHERE TO_DATE('01/'||TO_CHAR(T.DATES,'MM/YYYY'),'DD/MM/YYYY')\n" + "<= TO_DATE('" + date_str_01_
            + "','DD/MM/YYYY')\n" + "AND T.EMPSN = '" + empsn + "'";

    Object rs = obj_Utility.Exe_Sql_Obj(sql_1);
    if (Long.valueOf(rs.toString()) == 0)// neu khong ton tai thi xet tiep
    {
      String sql_2 = "SELECT COUNT(T.EMPSN) FROM N_EMP_QUIT T\n" + "WHERE T.EMPSN = '" + empsn + "'";

      Object rs2 = obj_Utility.Exe_Sql_Obj(sql_2);
      if (Long.valueOf(rs2.toString()) == 0)// neu khong ton tai trong
      // emp_quit thi add vao tang
      // 1 tay
      {
        flag = true;

      } else // neu ton tai trong emp_quit thi xet tiep
      {
        String sql_3 = "SELECT COUNT(T.EMPSN) FROM N_EMP_QUIT T\n" + "WHERE T.EMPSN = T.EMPSN\n"
            + "AND TO_CHAR(T.THANG_TANGLAI,'MM/YYYY') = '" + my_str + "'\n" + "AND T.EMPSN = '" + empsn + "'";
        Object rs3 = obj_Utility.Exe_Sql_Obj(sql_3);
        if (Long.valueOf(rs3.toString()) == 0)// neu khong ton tai thi
        // xet tiep
        {
          String sql_4 = "SELECT COUNT(T.EMPSN) FROM N_EMP_QUIT T\n"
              + "WHERE TO_DATE('01/'||TO_CHAR(T.REAL_OFF_DATE,'MM/YYYY'),'DD/MM/YYYY')\n" + "       = TO_DATE('"
              + date_str_01_ + "','DD/MM/YYYY')\n" + "AND T.NOTE_GIAM_BHYT = 'GIAM TANG MOI'\n" + "AND T.EMPSN = '"
              + empsn + "'";

          Object rs4 = obj_Utility.Exe_Sql_Obj(sql_4);
          if (Long.valueOf(rs4.toString()) == 0)// //neu khong ton tai
          // thi xet tiep
          {
            String sql_5 =

                "SELECT COUNT(T.EMPSN)    FROM N_EMP_QUIT T\n"
                    + "WHERE TO_DATE('01/'||TO_CHAR(T.REAL_OFF_DATE,'MM/YYYY'),'DD/MM/YYYY')\n" + "      = TO_DATE('"
                    + date_str_01_ + "','DD/MM/YYYY')\n" + "AND T.FROM_DATE IS NOT NULL AND T.TO_DATE IS NOT NULL\n"
                    + "AND T.EMPSN = '" + empsn + "'";

            Object rs5 = obj_Utility.Exe_Sql_Obj(sql_5);
            if (Long.valueOf(rs5.toString()) == 0)// neu khong ton
            // tai thi kiem
            // tra xem ngay
            // thuc nghi max
            // I co > hon
            // thang ky
            // trinh k(> add
            // vao theo doi,
            // else bo qua)
            {
              String sql_6 = "SELECT COUNT(T.EMPSN)    FROM N_EMP_QUIT T\n"
                  + "WHERE TO_DATE('01/'||TO_CHAR(T.REAL_OFF_DATE,'MM/YYYY'),'DD/MM/YYYY')\n" + "      > TO_DATE('"
                  + date_str_01_ + "','DD/MM/YYYY')\n" + "AND T.EMPSN = '" + empsn + "'";

              Object rs6 = obj_Utility.Exe_Sql_Obj(sql_6);
              if (Long.valueOf(rs6.toString()) == 0) {
                flag = false;// 25/02/2013: neu khong ton tai
                // thi chac chan nguoi nay nhan
                // su khong bao tang 1 tay(bo
                // qua)
              } else // neu co ton tai thi xet them cau lenh duoi
              // day de loai tru
              // vi co the Nsu se giam tang moi no tai
              // thang ky hop dong lan dau tien (thang sau
              // moi giao ke toan tinh luong thoi viec)
              // VD:12110683 Giam tang moi T01/2013, thuc
              // nghi 04/02/2013, thang tru BH = 02/2013
              {
                String sql_7 = "SELECT COUNT(A.EMPSN)\n" + "     FROM N_EMP_QUIT A\n"
                    + "     WHERE  A.FROM_DATE IS NULL AND A.TO_DATE IS NULL\n"
                    + "     AND A.DATE_BHYT >=(SELECT C.DATE_BHYT FROM N_HEALTH C WHERE C.CLOCK=1 AND C.EMPSN=A.EMPSN )\n"
                    + "     AND A.DATE_BHYT <=(SELECT C.DATE_BHYT+4 FROM N_HEALTH C WHERE C.CLOCK=1 AND C.EMPSN=A.EMPSN )\n"
                    + "     AND A.NOTE_GIAM_BHYT='GIAM TANG MOI'\n" + "	  AND A.EMPSN = '" + empsn + "'";
                Object rs7 = obj_Utility.Exe_Sql_Obj(sql_7);
                if (Long.valueOf(rs7.toString()) == 0) {
                  flag = true;// neu khong ton tai --> nhan su
                  // khong giam tang moi --> add
                  // vao tang 1 tay
                } else {
                  flag = false;
                }

              }

            } else// neu co ton tai thi fai add vao tang 1 tay
            {
              flag = true;
            }

          } else// NEU CO THI BO QUA TUC NO DA BI GIAM TRONG TANG MOI
          {
            flag = false;
          }
        } else// neu co thi bo qua de qua ham add_emp_quit_W_again add
        // nviec tang lai 1tay or 20tay
        {
          flag = false;
        }

      }
    } else // neu ton tai thi k add vao tang 1 tay vi nsu khong mua bao hiem
    // nhung nguoi nay bat dau tu thang do tro ve sau
    {
      flag = false;
    }

    return flag;

  }

  // xet cho nhung truong hop nghi viec di lam lai tang 1 tay_Giam tang moi
  // or tang 20 tay ma chua den ky gia han the da nghi viec
  private boolean check_condition_quit_work_again(String empsn) {
    // *Begin kiem tra neu nghi viec di lam lai tang 1tay ma 'Giam tang moi'
    // tai thang tai moi lai 25/02/2012
    String date_kytrinh_str = sf.format(date_input).substring(3, 10);
    String month_kt = date_kytrinh_str.substring(0, 2);
    String year_kt = date_kytrinh_str.substring(3, 7);
    String sql = "select count(t.empsn)\n" + "from K_N_N_HEALTH_R t,n_emp_quit q\n" + "where t.empsn = q.empsn\n"
        + "and to_char(q.thang_trubh,'MM/yyyy') = '" + date_kytrinh_str + "'\n"
        + " and q.note_giam_bhyt = 'GIAM TANG MOI'\n" + " and t.status_tt_tmoi = 1 " + " and t.empsn = '" + empsn + "'";

    OBJ_UTILITY obj_util = new OBJ_UTILITY();
    Object eq = obj_util.Exe_Sql_Obj(sql);
    if (Long.valueOf(eq.toString()) == 0) {
      sql = // co the chua kip tang moi da nghi viec or chua den ky gia
          // han the da nviec(status_tt_tmoi = 1or 2)
          "select count(t.empsn)\n" + "from k_n_n_health_r t,n_emp_quit q\n" + "where t.empsn = q.empsn\n"
              + "and to_char(q.thang_trubh,'MM/yyyy') = '" + date_kytrinh_str + "'\n"
              + "	and to_number(to_char(t.thang_tangmoi,'yyyy')) > " + Integer.valueOf(year_kt) + " and t.empsn = '"
              + empsn + "'";
      obj_util = new OBJ_UTILITY();
      eq = obj_util.Exe_Sql_Obj(sql);
      if (Long.valueOf(eq.toString()) == 0) {
        sql = "select count(t.empsn)\n" + "from k_n_n_health_r t,n_emp_quit q\n" + "where t.empsn = q.empsn\n"
            + "and to_char(q.thang_trubh,'MM/yyyy') = '" + date_kytrinh_str + "'\n"
            + "	and to_number(to_char(t.thang_tangmoi,'yyyy')) = " + Integer.valueOf(year_kt)
            + "	and to_number(to_char(t.thang_tangmoi,'MM')) > " + Integer.valueOf(month_kt) + " and t.empsn = '"
            + empsn + "'";
        obj_util = new OBJ_UTILITY();
        eq = obj_util.Exe_Sql_Obj(sql);
        if (Long.valueOf(eq.toString()) == 0) {
          return false;
        }
      }

    }

    note_work_again = "--Thang " + date_kytrinh_str
        + " 'GIAM TANG MOI' hoac chua den thang tang moi hoac chua den ky gia han the da nghi viec ";
    return true;
  }

  // 23/02/2013 them ham nay(mail c.HA gui DS giam trong tang moi_08/10/2012)
  // de xet Giam tang moi khi ngay thuc nghi > thang ky trinh (cung la thang
  // ky hdong tang moi)

  /**
   * <P>
   * Tính lại NUM tùy thuộc vào khả năng có thể trừ tiền BHYT của tháng.
   * 
   * @param empsn
   * @param num_input
   *            , num_recount
   * @param num_h
   *            , num_k
   * 
   * @return 0 : NUM
   *         <P>
   *         1 : DEBT
   *         <P>
   *         2 : MONEY
   *         <P>
   *         3 : note_int = list_recount.get(3);
   * 
   *         <P>
   *         if(note_int == 0){
   *         <P>
   *         note_recount =
   *         " -- Luong thuc nhan qua it -> xet lai tien BHYT : ";
   *         <P>
   * 
   *         list_recount = ReCount_BT(empsn, num_recount, num_k, money_dc,
   *         date_input_, debt_old);
   * 
   */

  public ArrayList<Long> ReCount_BT(K_N_N_HEALTH_R update_health, long num_recount, long num_k, long money_dc,
      Date date_input_, long debt_old, long lanChay) {// num_h la so
    // thang lay tu
    // num trong
    // n_n_health_r
    // da giam theo
    // thang trong
    // code
    // 10/05/2013 them debt_old de tinh no cu de xet so num hien tai

    long salary_total = 0;
    salary_total_k = 0;
    long salary_b = 0;

    String empsn = update_health.getEMPSN();

    // long dc_numh = 0;
    flag_dc = false;
    sothang_dc_bt = 0;

    OBJ_EMPSN obj_emp = new OBJ_EMPSN(empsn, date_input_);

    // ===== lấy lương tại tháng chạy ký trình =======nếu update commit ở
    // đây có thể sai vì đã thanh toán tiền một lượt từ đầu tháng
    try {
      if (obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_BT || obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NS) {
        // chỉnh lại procedure
        // lần chay 1

        salary_total = obj_emp.Get_Salary_Total_By_Month(empsn, date_input_);
        System.out.println("luong toan thang : " + salary_total);

        System.out.println("luong lan 1 : " + obj_emp.Get_Salary_Total_By_Date_Lan_1(empsn, date_input_));

        // if (lanChay == 1) {
        //
        // salary_total = obj_emp.Get_Salary_Total_By_Date_Lan_1(
        // empsn, date_input_);
        //
        // } else {
        // // lần chạy 2
        // salary_total = obj_emp.Get_Salary_Total_By_Month(empsn,
        // date_input_);
        // }
        salary_total_k = salary_total;
      } else {
        // nghỉ việc
        if (lanChay == 1) {
          salary_total = obj_emp.Get_Salary_Total_By_Month_for_NV_Lan_1(empsn, date_input_);
        } else {
          salary_total = obj_emp.Get_Salary_Total_By_Month_for_NV(empsn, date_input_);
        }
        salary_total_k = salary_total;
      }
    } catch (SQLException e) {
      new MessageDialog("Error", "Có lỗi phát sinh trong quá trình xử lý dữ liệu.", MessageDialog.CONTROLS_OK).show();
    }
    // =================== * ==========================

    // 28/09/2012_them tru dc luong giong ben nghi viec co so thang d/c
    // ===điều chỉnh lương===
    // đánh dấu xét lại lần 2
    //

    long debt_dc = 0;
    //
    // ====xét điều chỉnh lương====
    if (money_dc > 0) // chi xet d/c khi co su thay doi luong
    {
      // điều chỉnh được
      // code ngộ ngộ
      if (salary_total > 0 && salary_total >= money_dc) {
        salary_total = salary_total - money_dc;
        // money_dc = money_dc;
        sothang_dc_bt = num_k;
        debt_dc = 0; // = 0 d/chinh luong duoc
        flag_dc = true;

        // update_health.setCOMMIT(1l);//

      } else {
        // điều chỉnh không được
        // salary_total = salary_total;
        money_dc = 0;
        sothang_dc_bt = 0;
        debt_dc = 1; // = 1 khong d/chinh luong duoc
        // thừa
        flag_dc = false;
        // commmit

        // update_health.setCOMMIT((long) DIEU_CHINH_THAT_BAI);
      }

    } else {
      // salary_total = salary_total; không có sự điều chỉnh
      money_dc = 0;
      // thừa
      flag_dc = false;
    }
    // ==end==điều chỉnh lương===

    // mức lương của empsn
    salary_b = obj_emp.Get_Salary_Basic(empsn, date_input_);

    // Begin 04/01/2013 Ngan them vi neu nhu tong luong ma am tien thi dua
    // ve 0 (neu khong se bi sai ben duoi haiz)
    if (salary_total < 0) {
      salary_total = 0;
    }

    // End 04/01/2013 Ngan them vi neu nhu tong luong ma am tien thi dua ve
    // 0 (neu khong se bi sai ben duoi haiz)
    // số tháng
    long num_real = 0;
    num_real = (long) (salary_total / (salary_b * 1.5 / 100));// số num có
    // thể trừ
    // tại tháng
    // thể trừ
    long debt_ = 0;// xet xem co con no hay khong??
    // số tháng cần tin
    debt_ = num_recount - num_real;// ?????
    ArrayList<Long> list = new ArrayList<Long>();
    // không đủ tiền lương
    if (num_real == 0) {
      System.out.println("========tong luong qua it=========");
      list.add(num_k); // NUM 0
      list.add((long) (num_recount - num_real)); // DEBT 1
      // code kì kì num_real=0 rồi sao còn (salary_b * num_real * 1.5 /
      // 100)????
      list.add((long) (salary_b * num_real * 1.5 / 100) + money_dc); // MONEY
      // 2
      list.add((long) 0); // Note 3
      list.add(debt_dc); // 4
      list.add(sothang_dc_bt);// 5

      update_health.setCOMMIT(0l);

    } // trường hợp nợ??? không trừ được tiền trong tại tháng
    else if ((num_real < num_recount) && (debt_ > 0)) {

      System.out.println("-          - tong luong qua it");
      if (num_real <= debt_old) {

        long num_ht = 1;// tru thang hien tai truoc
        list.add(num_ht); // NUM
        list.add((long) (num_recount - num_real)); // DEBT
        list.add((long) (salary_b * num_real * 1.5 / 100) + money_dc); // MONEY
        list.add((long) 0);
        list.add(debt_dc);
        list.add(sothang_dc_bt);// 5
        update_health.setCOMMIT(1l);
      } else {// không trừ được tháng hiện tại
        list.add(num_real - debt_old); // NUM
        list.add((long) (num_recount - num_real)); // DEBT
        list.add((long) (salary_b * num_real * 1.5 / 100) + money_dc); // MONEY
        list.add((long) 0);
        list.add(debt_dc);
        list.add(sothang_dc_bt);// 5
        update_health.setCOMMIT(0l);
      }

    } else {
      /*
       * (money_dc > 0 && flag_dc == true) trường hợp có điều chỉnh lương
       * và điều chỉnh được money_dc == 0 không có sự điều chỉnh lương và
       * nằm trong eclse này điều chỉnh thành công.
       */

      if ((money_dc > 0 && flag_dc == true) || money_dc == 0) {
        update_health.setCOMMIT(1l);
      }
      System.out.println(" -          - tong luong OK");
      list = null;
    }

    return list;

  }

  /**
   * @param empsn
   * @param num_input
   * @param num_h
   * @param money_dc
   * @param date_input_
   * @param debt_old
   * @param lanChay
   * @author Tuan khoi chỉnh lại từ hàm ReCount_BT của chị Ngân
   */
  public void RecalculationMoneyEmpsnOperation(String empsn, long num_input, long num_h, long money_dc,
      Date date_input_, long debt_old, int lanChay) {// num_h la so

    // thang lay tu
    // num trong
    // n_n_health_r
    // da giam theo
    // thang trong
    // code
    // 10/05/2013 them debt_old de tinh no cu de xet so num hien tai
    ArrayList<Long> list = new ArrayList<Long>();

    salary_total_k = 0;

    long num_ht = 1;// tru thang hien tai truoc

    flag_dc = false;
    sothang_dc_bt = 0;

    // num_h= num_k
    // tăng mới
    // long dc_numh = 0;
    // if (money_dc == 0 && num_h <= 0) {
    // dc_numh = 0;
    // } else {
    //
    // dc_numh = money_dc / num_h;
    // }

    OBJ_EMPSN obj_emp = new OBJ_EMPSN(empsn, date_input_);

    // lấy lương tại tháng chạy ký trình.
    long salary_total = 0;// lương hiện tại tại lần chạy ký trình
    if (obj_emp.Get_Status_Work() == K_N_N_HEALTH_R.LAM_VIEC_BINH_THUONG
        || obj_emp.Get_Status_Work() == K_N_N_HEALTH_R.NGHI_SAN) {
      // chỉnh lại procedure
      salary_total = obj_emp.Get_Salary_Total_By_Month(empsn, date_input_);
      salary_total_k = salary_total;
    } else {
      salary_total = obj_emp.Get_Salary_Total_By_Month_for_NV(empsn, date_input_);
      salary_total_k = salary_total;
    }

    System.out.println("Ngày công có lương " + obj_emp.Get_WORK_DAY());

    try {

      System.out.println("Luong lan 1 " + obj_emp.Get_Salary_Total_By_Date_Lan_1(empsn, date_input_));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    System.out.println(obj_emp.Get_Salary_Total_By_Month(empsn, date_input_));

    // 28/09/2012_them tru dc luong giong ben nghi viec co so thang d/c
    // điều chỉnh lương.
    long debt_dc = 0;
    if (money_dc > 0) // chi xet d/c khi co su thay doi luong
    {

      if (salary_total > 0 && salary_total >= money_dc) {
        salary_total = salary_total - money_dc;
        // money_dc = money_dc;
        sothang_dc_bt = num_h;
        debt_dc = 0; // = 0 d/chinh luong duoc
        flag_dc = true;
      } else {
        // salary_total = salary_total;
        money_dc = 0;
        sothang_dc_bt = 0;
        debt_dc = 1; // = 1 khong d/chinh luong duoc
        // thừa
        flag_dc = false;
      }
    } else {
      // salary_total = salary_total;
      money_dc = 0;
      // thừa
      flag_dc = false;
    }
    long salary_b = 0;
    salary_b = obj_emp.Get_Salary_Basic(empsn, date_input_);

    // Begin 04/01/2013 Ngan them vi neu nhu tong luong ma am tien thi dua
    // ve 0 (neu khong se bi sai ben duoi haiz)
    if (salary_total < 0) {
      salary_total = 0;
    }

    // End 04/01/2013 Ngan them vi neu nhu tong luong ma am tien thi dua ve
    // 0 (neu khong se bi sai ben duoi haiz)
    // số tháng
    long num_real = 0;
    num_real = (long) (salary_total / (salary_b * 1.5 / 100));
    long debt_ = 0;// xet xem co con no hay khong??
    debt_ = num_input - num_real;

    if (num_real == 0) {
      System.out.println("-          - tong luong qua it");
      list.add(num_h); // NUM 0
      list.add((long) (num_input - num_real)); // DEBT 1
      list.add((long) (salary_b * num_real * 1.5 / 100) + money_dc); // MONEY
      // 2
      list.add((long) 0); // Note 3
      list.add(debt_dc); // 4
      list.add(sothang_dc_bt);// 5
    } else if ((num_real < num_input) && (debt_ > 0)) {

      System.out.println("-          - tong luong qua it");
      if (num_real <= debt_old) {
        list.add(num_ht); // NUM
        list.add((long) (num_input - num_real)); // DEBT
        list.add((long) (salary_b * num_real * 1.5 / 100) + money_dc); // MONEY
        list.add((long) 0);
        list.add(debt_dc);
        list.add(sothang_dc_bt);// 5
      } else {
        list.add(num_real - debt_old); // NUM
        list.add((long) (num_input - num_real)); // DEBT
        list.add((long) (salary_b * num_real * 1.5 / 100) + money_dc); // MONEY
        list.add((long) 0);
        list.add(debt_dc);
        list.add(sothang_dc_bt);// 5
      }

    } else {
      System.out.println(" -          - tong luong OK");
      list = null;
    }
  }

  public ArrayList<Long> ReCount_NV(String empsn, long money, long money_dc, long num_input, long num_recovery,
      long num_h, Date date_input_, long flag_, long salary, long money_return, long debt_old, long num_nv,
      boolean flag_tra_the) {

    ArrayList<Long> list = new ArrayList<Long>();
    long salary_total = 0;
    salary_total_k = 0;
    long salary_b = 0;
    long num_real = 0;
    long sothang_dc = 0;
    String month_ = sf.format(date_input_).substring(3, 5);
    long debt_dc = 0;
    long dc_numh = 0;
    flag_dc = false;
    sothang_dc_bt = 0;
    long debt_ = 0;// xet xem co con no hay khong??
    long money_input = 0;
    long money_recovery = 0;

    if (money_dc == 0 && num_h <= 0) {
      dc_numh = 0;
    } else {
      dc_numh = money_dc / num_h;
    }
    if (money_return < 0) {
      money_return = -(money_return);
    } // doi thanh so duong de cong don vao tong luong

    OBJ_EMPSN obj_emp = new OBJ_EMPSN(empsn, date_input_);
    // *mail 26/09/2012 dau quy thi nghi viec lay luong dau quy de tru
    // giua quy : luong giua quy >= luong dau quy ky trinh lay luong giua
    // quy nguoi lai lay luong dau quy
    // cuoi quy : luong cuoi quy
    K_N_N_HEALTH_R obj_update = obj_dao.findById(empsn);

    if (obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_BT || obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NS) {
      salary_total = obj_emp.Get_Salary_Total_By_Month(empsn, date_input_);
      salary_total_k = salary_total;
    } else {
      salary_total = obj_emp.Get_Salary_Total_By_Month_for_NV(empsn, date_input_);
      salary_total_k = salary_total;
    }

    salary_total = salary_total + money_return;
    salary_total_k = salary_total;
    salary_b = salary;

    if (salary_total < 0) {
      salary_total = 0;
    }

    if (money_dc > 0) // chi xet d/c khi co su thay doi luong
    {
      if (salary_total > 0 && salary_total >= money_dc) {
        salary_total = salary_total - money_dc;
        if (flag_tra_the) {
          sothang_dc_bt = num_nv;
        } else {
          sothang_dc_bt = num_h;
        }

        debt_dc = 0; // = 0 d/chinh luong duoc
        flag_dc = true;
        salary_cb_nv = salary;
      } else {
        money_dc = 0;
        sothang_dc_bt = 0;
        debt_dc = 1; // = 1 khong d/chinh luong duoc
        flag_dc = false;
        salary_cb_nv = obj_update.getSALARY_B() == null ? 0 : obj_update.getSALARY_B();
      }
    } else // lay theo luong lon nhat de tinh cho nghi viec
    {
      salary_cb_nv = obj_update.getSALARY_B() == null ? 0 : obj_update.getSALARY_B();
      if (salary <= salary_cb_nv) {
        salary_cb_nv = salary_cb_nv;
      } else {
        salary_cb_nv = salary;
      }
    }

    num_real = (long) (salary_total / (salary_b * 1.5 / 100));
    debt_ = num_input - num_real;

    if (num_real == 0) {
      System.out.println("-          - tong luong qua it");
      if (flag_tra_the) {
        if (num_h > 0) {
          list.add((long) num_nv); // NUM 0
        } else {
          list.add((long) 0); // NUM 0
        }
      } else {
        if (num_h > 0) {
          list.add((long) num_h); // NUM 0
        } else {
          list.add((long) 0); // NUM 0
        }
      }
      list.add((long) (num_input - num_real)); // DEBT 1
      // list.add((long)(salary_b*num_real*1.5/100)+ money_dc) ; // MONEY
      // 2
      list.add((long) salary_total_k);// 25/12/2012 Thay doi theo ycau
      // Ktoan k tru dc het money thi lay
      // tong luong dua vao money k x / +
      // - nua
      list.add((long) 0); // Note 3
      list.add(debt_dc); // 4
      list.add(sothang_dc_bt);// 5
      list.add((long) 0);// 6
    } else if ((num_real < num_input) && (debt_ > 0)) {

      System.out.println("-          - tong luong qua it");
      if (flag_tra_the) {
        if (num_real <= debt_old) {
          list.add(num_nv); // NUM
          list.add((long) (num_input - num_real)); // DEBT
          // list.add((long)(salary_b*num_real*1.5/100)+ money_dc) ;
          // // MONEY
          list.add((long) salary_total_k);// 25/12/2012 Thay doi theo
          // ycau Ktoan k tru dc het
          // money thi lay tong luong
          // dua vao money k x / + -
          // nua
          list.add((long) 0);
          list.add(debt_dc);
          list.add(sothang_dc_bt);// 5
          list.add((long) 0);// 6
        } else {
          list.add(num_nv); // NUM
          list.add((long) 0); // DEBT
          // list.add((long)(salary_b*num_real*1.5/100)+ money_dc) ;
          // // MONEY
          list.add((long) salary_total_k);// 25/12/2012 Thay doi theo
          // ycau Ktoan k tru dc het
          // money thi lay tong luong
          // dua vao money k x / + -
          // nua
          list.add((long) 0);
          list.add(debt_dc);
          list.add(sothang_dc_bt);// 5
          list.add((long) 0);// 6
        }
      } else // khong tra the
      {
        if (num_real <= debt_old) {
          list.add((long) 1); // NUM (uu tien tru 1 thang hien tai)
          list.add((long) (num_input - num_real)); // DEBT
          // list.add((long)(salary_b*num_real*1.5/100)+ money_dc) ;
          // // MONEY
          list.add((long) salary_total_k);// 25/12/2012 Thay doi theo
          // ycau Ktoan k tru dc het
          // money thi lay tong luong
          // dua vao money k x / + -
          // nua
          list.add((long) 0);
          list.add(debt_dc);
          list.add(sothang_dc_bt);// 5
          list.add((long) 0);// 6
        } else {
          if (num_h > 0) // k con debt_old
          {
            list.add(num_real + num_h); // NUM
            list.add((long) (num_input - num_real)); // DEBT
            // list.add((long)(salary_b*num_real*1.5/100)+ money_dc)
            // ; // MONEY
            list.add((long) salary_total_k);// 25/12/2012 Thay doi
            // theo ycau Ktoan k tru
            // dc het money thi lay
            // tong luong dua vao
            // money k x / + - nua
            list.add((long) 0);
            list.add(debt_dc);
            list.add(sothang_dc_bt);// 5
            list.add((long) 0);// 6
          } else {
            list.add(num_real - debt_old); // NUM
            list.add((long) (num_input - num_real)); // DEBT
            // list.add((long)(salary_b*num_real*1.5/100)+ money_dc)
            // ; // MONEY
            list.add((long) salary_total_k);// 25/12/2012 Thay doi
            // theo ycau Ktoan k tru
            // dc het money thi lay
            // tong luong dua vao
            // money k x / + - nua
            list.add((long) 0);
            list.add(debt_dc);
            list.add(sothang_dc_bt);// 5
            list.add((long) 0);// 6
          }
        }
      }

    } else {

      if (num_recovery > 0) {

        money_input = (long) (salary_b * num_input * 1.5 / 100);
        salary_total = (long) (salary_total - (money_input));
        num_real = (long) (salary_total / (salary_b * 3 / 100)); // num
        // thuc
        // te
        // thu
        // hoi
        // duoc

        if (num_real == 0) {
          System.out.println("-          - tong luong qua it");
          list.add((long) num_nv); // NUM 0
          list.add((long) (0)); // DEBT 1
          // list.add((long)(money_input + money_dc + money_recovery))
          // ; // MONEY 2
          list.add((long) salary_total_k);// 25/12/2012 Thay doi theo
          // ycau Ktoan k tru dc het
          // money thi lay tong luong
          // dua vao money k x / + -
          // nua
          list.add((long) 0); // Note 3
          list.add(debt_dc); // 4
          list.add(sothang_dc_bt);// 5
          list.add((long) 0);// 6 so thang thu hoi
        } else if (num_real < num_recovery) {
          money_recovery = (long) (salary_b * num_real * 3 / 100);

          System.out.println("-          - tong luong qua it");
          list.add((long) num_nv); // NUM 0
          list.add((long) (0)); // DEBT 1
          // list.add((long)(money_input + money_dc + money_recovery))
          // ; // MONEY 2
          list.add((long) salary_total_k);// 25/12/2012 Thay doi theo
          // ycau Ktoan k tru dc het
          // money thi lay tong luong
          // dua vao money k x / + -
          // nua
          list.add((long) 0); // Note 3
          list.add(debt_dc); // 4
          list.add(sothang_dc_bt);// 5
          list.add((long) num_real);// 6 so thang thu hoi
        } else {
          System.out.println(" -          - tong luong OK (" + salary_total_k + ")");
          list = null;
        }

      } else {
        System.out.println(" -          - tong luong OK (" + salary_total_k + ")");
        list = null;
      }
    }
    return list;

  }

  // 26/09/2011 Ngan sua lai reCount-->recount_ns cho nghi san them num_h va
  // money_dc
  // Tu T06/2012 khong su dung ham nay nua ma su dung ReCount_ns_new o duoi de
  // tra lai tien Nsan
  public ArrayList<Long> ReCount_ns(String empsn, long num_input, long num_h, long money_dc, Date date_input_) {

    ArrayList<Long> list = new ArrayList<Long>();
    long salary_total = 0;
    long salary_b = 0;
    long num_real = 0;
    long debt_dc = 0;
    long sothang_dc = 0;
    String month_ = sf.format(date_input_).substring(3, 5);
    long dc_numh = 0;

    if (money_dc == 0 && num_h <= 0) {
      dc_numh = 0;
    } else {
      dc_numh = money_dc / num_h;
    }

    OBJ_EMPSN obj_emp = new OBJ_EMPSN(empsn, date_input_);

    if (obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_BT || obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NS)
      salary_total = obj_emp.Get_Salary_Total_By_Month(empsn, date_input_);
    else {
      salary_total = obj_emp.Get_Salary_Total_By_Month_for_NV(empsn, date_input_);
    }

    salary_b = obj_emp.Get_Salary_Basic(empsn, date_input_);
    // 10/09/2011 Ngan sua
    if (month_.equals("02") || month_.equals("05") || month_.equals("08") || month_.equals("11") || month_.equals("03")
        || month_.equals("06") || month_.equals("09") || month_.equals("12")) {

      if (salary_total < dc_numh) {
        // salary_total = salary_total;
        money_dc = 0;
        debt_dc = 1; // khong dieu chinh luong duoc
      } else {
        if ((salary_total - money_dc < 0) || (salary_total - money_dc > 0 && num_h == 1)) {
          salary_total = salary_total - dc_numh;
          money_dc = dc_numh;
          debt_dc = 0;// dc luong duoc
          sothang_dc = 1; // dc duoc 1 thang
        } else if (salary_total - money_dc > 0 && num_h == 2) {
          salary_total = salary_total - money_dc;
          // money_dc = money_dc;
          debt_dc = 0;// dc luong duoc
          sothang_dc = 2; // dc duoc 2 thang
        } else {
          salary_total = salary_total - money_dc;
          // money_dc = money_dc;
          debt_dc = 0;// dc luong duoc
          sothang_dc = 0;
        }

      }
    }

    // end 10/09/2011

    num_real = (long) (salary_total / (salary_b * 1.5 / 100));

    if (num_real < num_input) {
      System.out.println("-          - tong luong qua it");
      list.add(num_real); // NUM 0
      list.add((long) (num_input - num_real)); // DEBT 1
      list.add((long) (salary_b * num_real * 1.5 / 100) + money_dc); // MONEY
      // 2
      list.add((long) 0); // Ghi chu = 0 : Luong thuc nhan qua it -> xet
      // lai tien dong BHYT 3
      list.add(debt_dc);// 4
      list.add(sothang_dc);// 5
    } else {
      System.out.println(" -          - tong luong OK");
      list = null;
    }

    return list;

  }

  // *23/06/2012 Viet ReCount_ns_new vi yc moi co tra lai tien Nsan nen tinh
  // theo money tru, k tinh theo num_input nua
  public ArrayList<Long> ReCount_ns_new(String empsn, long money, long num_input, long money_return, long money_dc,
      Date date_input_, long debt_old, long num_ns, long num_h) {

    ArrayList<Long> list = new ArrayList<Long>();
    long salary_total = 0;
    salary_total_k = 0;
    long salary_b = 0;
    long num_real = 0;
    long debt_dc = 0;
    long sothang_dc = 0;
    String month_ = sf.format(date_input_).substring(3, 5);
    long dc_numh = 0;
    flag_dc = false;
    sothang_dc_bt = 0;
    long debt_ = 0;// xet xem co con no hay khong??

    OBJ_EMPSN obj_emp = new OBJ_EMPSN(empsn, date_input_);

    if (money_return < 0) {
      money_return = -(money_return);
    } // doi thanh so duong de cong don vao tong luong

    if (obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_BT || obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NS) {
      salary_total = obj_emp.Get_Salary_Total_By_Month(empsn, date_input_);
    } else {
      salary_total = obj_emp.Get_Salary_Total_By_Month_for_NV(empsn, date_input_);
    }

    salary_total = salary_total + money_return;
    salary_total_k = salary_total;
    salary_b = obj_emp.Get_Salary_Basic(empsn, date_input_);

    if (salary_total < 0) {
      salary_total = 0;
    }

    if (money_dc > 0) // chi xet d/c khi co su thay doi luong
    {
      if (salary_total > 0 && salary_total >= money_dc) {
        salary_total = salary_total - money_dc;
        money_dc = money_dc;
        sothang_dc_bt = num_ns;
        debt_dc = 0; // = 0 d/chinh luong duoc
        flag_dc = true;
      } else {
        salary_total = salary_total;
        money_dc = 0;
        debt_dc = 1; // = 1 khong d/chinh luong duoc
        flag_dc = false;
      }
    } else {
      if (num_h <= 0) {
        flag_dc = true;
      } else {
        flag_dc = false;
      }
      salary_total = salary_total;
      money_dc = 0;

    }

    num_real = (long) (salary_total / (salary_b * 1.5 / 100));
    debt_ = num_input - num_real;

    if (num_real == 0) {
      System.out.println("-          - tong luong qua it");
      list.add((long) 0); // NUM 0
      list.add((long) (num_input - num_real)); // DEBT 1
      list.add((long) (salary_b * num_real * 1.5 / 100) + money_dc); // MONEY
      // 2
      list.add((long) 0); // Note 3
      list.add(debt_dc); // 4
      list.add(sothang_dc_bt);// 5
    } else if ((num_real < num_input) && (debt_ > 0)) {

      System.out.println("-          - tong luong qua it");
      if (num_real <= debt_old) {
        list.add(num_ns); // NUM
        list.add((long) (num_input - num_real)); // DEBT
        list.add((long) (salary_b * num_real * 1.5 / 100) + money_dc); // MONEY
        list.add((long) 0);
        list.add(debt_dc);
        list.add(sothang_dc_bt);// 5
      } else {
        list.add(num_ns); // NUM
        list.add((long) 0); // DEBT
        list.add((long) (salary_b * num_real * 1.5 / 100) + money_dc); // MONEY
        list.add((long) 0);
        list.add(debt_dc);
        list.add(sothang_dc_bt);// 5
      }

    } else// Tong luong > so tien can phai tru (Money)
    {
      System.out.println(" -          - tong luong OK");
      list = null;
    }

    return list;

  }

  @SuppressWarnings("deprecation")
  public void UPDATE_DATA_BT(K_N_N_HEALTH_R obj_health_r) {// 24/09/2011 Ngan
    // them
    try {
      obj_emp = new OBJ_EMPSN(obj_health_r.getEMPSN(), date_input);
      long num_nv = obj_health_r.getNUM();
      if (num_nv <= 0) {
        num_nv = 1;
      } // De lay salary_m k bi = 0 hoac am

      java.sql.Timestamp date_action = obj_emp.GetDateTimeOracle();
      System.out.println(date_action);
      /*
       * java.sql.Date date_ = (java.sql.Date)
       * sf.parse(sf.format(date_action)); System.out.println(date_);
       */

      if (obj_health_r != null) { // NS phai lay luong tai thoi diem bat
        // dau NS xuat ra ky trinh
        // if( (obj_health_r.getSTATUS() == -1) ||
        // ((obj_health_r.getSTATUS() == 0) && (obj_health_r.getDATE_B()
        // == null))) {
        if ((obj_health_r.getSTATUS() == -1)) {
          // OLD
          /*
           * if (flag_dc == false){ //debt_dc == 1--> k dc luong dc
           * lay luong old
           * obj_health_r.setSALARY_M(obj_health_r.getSALARY_B() *
           * num_nv); }else {
           * obj_health_r.setSALARY_M(obj_emp.Get_Salary_Basic
           * (obj_health_r.getEMPSN(), date_input)* num_nv); }
           */
          // NEW
          obj_health_r.setSALARY_M(obj_health_r.getSALARY_B() * num_nv);
        } else {
          // if( ((obj_health_r.getSTATUS() == 0) &&
          // (obj_health_r.getDATE_B() == null)) ||
          // obj_health_r.getDEBT_DC() == 1 ){
          if ((obj_health_r.getSTATUS() == 0 && flag_dc == false)
              || (obj_health_r.getSTATUS() == 1 && obj_health_r.getDEBT_DC() == null) // debt_dc == null
              // :luong khog thay
              // doi
              || (obj_health_r.getSTATUS() == 1
                  && (obj_health_r.getDEBT_DC() != null && obj_health_r.getDEBT_DC() == 1))// debt_dc == 1: khong
          // dieu chinh dc lay
          // luong dau quy
          ) {
            obj_health_r.setSALARY_M(obj_health_r.getSALARY_B());
          } else {
            // Begin 10/10/2012 them vi mac dinh deb_dc = 0 la d/c
            // duoc nen xet them neu luong htai >= luong dau quy k
            // van den gi, neu luong htai < luong dau quy lay luong
            // dau quy
            long luong_old = 0;
            luong_old = obj_health_r.getSALARY_B() == null ? 0 : obj_health_r.getSALARY_B();
            long luong_new = 0;
            luong_new = obj_emp.Get_Salary_Basic(obj_health_r.getEMPSN(), date_input);

            if (luong_old > luong_new) {
              obj_health_r.setSALARY_M(luong_old);
            } else {
              obj_health_r.setSALARY_M(obj_emp.Get_Salary_Basic(obj_health_r.getEMPSN(), date_input));// old
            }
            // End 10/10/2012 them vi mac dinh deb_dc = 0 la d/c
            // duoc nen xet them neu luong htai >= luong dau quy k
            // van den gi, neu luong htai < luong dau quy lay luong
            // dau quy

            if (sothang_dc_bt > 0) // 28/09/2012 d/c duoc 2T thi
            // update lai luong cb tai thang
            // giua quy de cuoi quy moi lay
            // duoc luong giua quy khi luong
            // k thay doi or k d/c them duoc
            // (sothang_dc_bt == 2)
            // 28/02/2013 thay doi d/c theo num thang (sothang_dc_bt
            // > 0)
            {
              obj_health_r.setSALARY_B(obj_emp.Get_Salary_Basic(obj_health_r.getEMPSN(), date_input));
            }
          }
        }

        obj_health_r.setMONEY(OBJ_UTILITY.Round_Salary(obj_health_r.getMONEY()));
        // String dept_ = obj_emp.Get_depsn(obj_health_r.getEMPSN(),
        // date_input);
        if (LAN_CHAY == 1) {
          obj_health_r.setDEPSN(madv);
        }
        obj_health_r.setLOCK_DATE(date_input);
        // obj_health_r.setDATE_B(null);
        obj_health_r.setUSER_UP(user_name);
        obj_health_r.setDATE_UP(date_action);
        // Begin 25/12/2012 them
        //
        Date to_date = new Date(timeServer.getYear(), timeServer.getMonth(), 1);
        Date from_date = new Date(timeServer.getYear(), timeServer.getMonth(), NGAY_QUY_DINH_CHAY_LAN_1);
        try {

          obj_health_r
              .setNCCL(BigDecimal.valueOf(HealthProgramDAO.CountWorkday(obj_health_r.getEMPSN(), to_date, from_date)));

          obj_health_r.setTONGLUONG(salary_total_k);
          obj_health_r.setTIEN_BHYT(OBJ_UTILITY.Round_Salary(money_goc));
          // obj_health_r.setDK(null);// 13/03/2013
          // End 25/12/2012 them
          obj_dao.update(obj_health_r);

        } catch (Exception e) {
          e.printStackTrace();
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void UPDATE_DATA_DANG_NS_THI_NV(K_N_N_HEALTH_R obj_health_r) {// 10/10/2011
    // Ngan
    // them
    try {

      obj_emp = new OBJ_EMPSN(obj_health_r.getEMPSN(), date_input);

      if (obj_health_r != null) { // NS phai lay luong tai thoi diem bat
        // dau NS xuat ra ky trinh

        obj_health_r.setLOCK_DATE(date_input);
        obj_health_r.setSALARY_M(obj_health_r.getSALARY_B());
        try {
          obj_dao.update(obj_health_r);

        } catch (Exception e) {
          System.out.println(obj_health_r.getEMPSN());
        }

      }
    } catch (Exception e) {
      System.out.println(obj_health_r.getEMPSN());
    }
  }

  /**
   * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
   * Contents will be overwritten.
   */
  private void initComponents() {

    Grid rootLayout = new Grid();

    rootLayout.setInsets(new Insets(new Extent(5, Extent.PX)));

    // rootLayout.setWidth(new Extent(300, Extent.PX));
    // rootLayout.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(
    // 10, Extent.PX), new Extent(5, Extent.PX), new Extent(10,
    // Extent.PX)));
    // rootLayout.setRowHeight(0, new Extent(120, Extent.PX));

    rootLayout.setSize(2);
    add(rootLayout);

    Extent newValue = new Extent(130, Extent.PX);

    Label label1 = new Label();
    label1.setText("Xưởng");
    rootLayout.add(label1);

    combNameFact = new ComboBox();
    combNameFact.setWidth(newValue);
    combNameFact.setActionOnSelection(true);

    rootLayout.add(combNameFact);
    Label label2 = new Label();
    label2.setText("Nhóm");
    rootLayout.add(label2);
    combGroup = new ComboBox();
    combGroup.setWidth(newValue);
    combGroup.setActionOnSelection(true);

    rootLayout.add(combGroup);
    Label label3 = new Label();
    label3.setText("Đơn vị");
    rootLayout.add(label3);
    combDept = new ComboBox();

    combDept.setWidth(newValue);

    rootLayout.add(combDept);
    Label label4 = new Label();
    label4.setText("Ngày");
    rootLayout.add(label4);
    dateExport = new DscDateField();
    dateExport.setWidth(new Extent(150, Extent.PX));
    // date_export.setWidth(newValue);

    rootLayout.add(dateExport);

    Label label = new Label("Ký trình");
    rootLayout.add(label);

    sfLanChay = new SelectField();
    sfLanChay.setWidth(newValue);
    sfLanChay.addActionListener(this);
    rootLayout.add(sfLanChay);

    // ========check box====
    rootLayout.add(new Label());
    chkExport = new CheckBox("Xuất toàn bộ các xưởng");
    // ========checkbox====
    rootLayout.add(chkExport);

    rootLayout.add(new Label());

    btnUpdate = new Button();
    btnUpdate.setStyleName("Default.ToolbarButton");
    btnUpdate.setText(txtDefaultUpdate);
    btnUpdate.setWidth(newValue);
    btnUpdate.addActionListener(this);
    rootLayout.add(btnUpdate);

    // ===============export lan 1=================
    rootLayout.add(new Label());
    btnExport = new Button();
    btnExport.setStyleName("Default.ToolbarButton");
    btnExport.setText(txtDefaultExport);

    btnExport.setWidth(newValue);

    btnExport.addActionListener(this);
    rootLayout.add(btnExport);
    // ======== end ======export lan 1=================

  }

  @SuppressWarnings("deprecation")
  private boolean checkValidDateExport() {

    // String[] str = dateExport.getText().split("/");
    // int month = Integer.parseInt(str[1]);
    // int year = Integer.parseInt(str[2]);

    // timeServer = new Timestamp(date_input.getYear(),
    // date_input.getMonth(),
    // date_input.getDate(), 0, 0, 0, 0);

    NAME_FACT = getNameFact();
    // check valid date
    String date = dateExport.getText().trim();
    String regularDate = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

    if (!date.matches(regularDate)) {
      new MessageDialog("Error", "Ngày nhập vào không hợp lệ.", MessageDialog.CONTROLS_OK).show();
      return false;
    } else {

      date_input = convertStringIntoDate(dateExport.getText());
      timeServer = new Timestamp(date_input.getYear(), date_input.getMonth(), date_input.getDate(), 0, 0, 0, 0);

    }

    if (!chkExport.isEnabled()) {
      String nameFact = getNameFact();
      if (nameFact == null || nameFact.trim().isEmpty()) {

        new MessageDialog("Error", "Vui lòng chọn xưởng xuất ký trình.", MessageDialog.CONTROLS_OK).show();
        return false;

      } else {
        if (((DefaultListModel) combNameFact.getListModel()).indexOf(combNameFact.getText()) == -1) {
          new MessageDialog("Error", "Xưởng không hợp lệ.", MessageDialog.CONTROLS_OK).show();
          return false;
        }
      }
    }

    if (sfLanChay.getSelectedIndex() < 0) {
      new MessageDialog("Error", "Chưa chọn lần chạy ký trình.", MessageDialog.CONTROLS_OK).show();
      return false;
    }

    // if ((month > getMonthOfServer()) || (year > getYearOfServer())) {
    //
    // new MessageDialog("Error",
    // "Tháng xuất ký trình lớn hơn tháng hiện tại.",
    // MessageDialog.CONTROLS_OK).show();
    // return false;
    // }
    return true;

  }
}
