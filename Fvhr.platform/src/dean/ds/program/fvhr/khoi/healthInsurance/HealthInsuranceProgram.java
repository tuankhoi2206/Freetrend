package ds.program.fvhr.khoi.healthInsurance;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.omg.CORBA.DoubleHolder;

import ds.program.fvhr.domain.N_DETAIL_SUB_INSURANCE;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_EMP_QUIT;
import ds.program.fvhr.domain.N_PURCHASE_DEPT;
import ds.program.fvhr.domain.pk.N_DETAIL_SUB_INSURANCEPk;
import ds.program.fvhr.domain.pk.N_PURCHASE_DEPTPk;
import ds.program.fvhr.khoi.dao.EmployeeInsuranceDAO;
import ds.program.fvhr.khoi.domain.EmployeeBear;
import ds.program.fvhr.khoi.health.HealthProgramDAO;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.list.DefaultListModel;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;


/**
 * @author Administrator
 * 
 */
public class HealthInsuranceProgram extends HealthInsuranceGUI {

  /**
   * @author TuanKhoi
   */
  private static final long serialVersionUID = 1L;

  private EmployeeInsuranceDAO employeeInsuranceDAO;

  private final float HE_SO_TRU_TIEN_BH = 1.5f;
  // private final float HE_SO_THU_HOI = 4.5f;
  public static final float TIEN_CHUYEN = 0.45f;
  private final int NGAY_QUY_DINH_CHAY_LAN_1 = 1;
  private final int NGAY_QUY_DINH_CHAY_LAN_2 = 26;
  private final int NGAY_MUA_BH_CUA_NS = 15;
  private final int NGAY_MUA_BH_CUA_NV = 19;

  private final int DA_TRU_TIEN = 1;
  private final int KHONG_DU_TIEN_TRU = -1;
  private final int NGHI_VIEC_TRA_THE = 1;
  private final int CHAY_LAN_1 = 1;
  private final int CHAY_LAN_2 = 2;

  private final boolean CHUA_CAP_NHAT = false;
  private final boolean DA_CAP_NHAT = true;

  private IGenericDAO<N_DETAIL_SUB_INSURANCE, N_DETAIL_SUB_INSURANCEPk> detailInsuranceDAO;
  private IGenericDAO<N_PURCHASE_DEPT, N_PURCHASE_DEPTPk> purchaseDeptDAO;

  private HealthProgramDAO healthDAO;

  private Date dateUpdate;
  private int LAN_CHAY = 0;
  private String userName;
  private String userNameLogin;
  private SimpleDateFormat sdf;

  public HealthInsuranceProgram() {

    initDefaultParam();
  }

  @SuppressWarnings("unchecked")
  private void initDefaultParam() {
    sdf = new SimpleDateFormat("dd/MM/yyyy");

    detailInsuranceDAO = Application.getApp().getDao(N_DETAIL_SUB_INSURANCE.class);

    purchaseDeptDAO = Application.getApp().getDao(N_PURCHASE_DEPT.class);

    userNameLogin = Application.getApp().getLoginInfo().getUserName();

    employeeInsuranceDAO = new EmployeeInsuranceDAO();
    healthDAO = new HealthProgramDAO();

    userName = Application.getApp().getLoginInfo().getUserID();

    try {

      if (!healthDAO.checkLimitExportAll(userName, "ALL")) {
        super.chkAllExport.setEnabled(false);
      }

      if (healthDAO.checkUserAccountant(userName)) {
        btnUpdate.setEnabled(false);

        sfNameFact.setModel(new DefaultListModel(healthDAO.getAllNameFact()));
      } else {
        sfNameFact.setModel(new DefaultListModel(healthDAO.getAllNameFactOfManageUser(userName)));
      }

    } catch (SQLException e1) {
      // isEnableComponent(false);
      e1.printStackTrace();
      new MessageDialog("Lỗi", "Có lỗi phát sinh trong quá trình xử lý",
          MessageDialog.CONTROLS_OK + MessageDialog.TYPE_ERROR).show();
    }
  }

  private String getNameFact() {
    return sfNameFact.getSelectedItem().toString();
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    Object obj = e.getSource();
    if (obj instanceof Button) {
      if (obj == btnUpdate) {
        try {
          if (checkValidComponents()) {
            /*
             * 
             */
            updateEmplSubInsuranceTime1(LAN_CHAY, dateUpdate, getNameFact());
          }
        } catch (ParseException e1) {
          e1.printStackTrace();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }
      } else {
        // exprot
        try {
          if (checkValidDateExport()) {
            System.out.println(sdf.format(dateUpdate));
            String factExport = getNameFact();
            if (chkAllExport.isSelected())
              factExport = "ALLFact";
            new ExportHealthInsurance(dateUpdate, factExport, LAN_CHAY);
          }
        } catch (Exception e1) {
          e1.printStackTrace();
          new MessageDialog("Lỗi",
              "Có lỗi phát sinh trong quá trình xuất file.\nVui lòng liên hệ phòng vi tính để khắc phục",
              MessageDialog.CONTROLS_OK).show();
        }
      }
    } else if (obj instanceof SelectField) {
      if (obj == sfLanChay) {
        LAN_CHAY = sfLanChay.getSelectedIndex() + 1;
        System.out.println("Lan chay " + LAN_CHAY);
      }
    }
  }

  private static class MessageDialogBuilder {
    private String title;
    private String msg;
    private int config;

    public MessageDialogBuilder addTitle(String title) {
      this.title = title;
      return this;
    }

    public MessageDialogBuilder addMessage(String massage) {
      this.msg = massage;
      return this;
    }

    public MessageDialogBuilder Config(int config) {
      this.config = config;
      return this;
    }

    public MessageDialog build() {
      return new MessageDialog(title, msg, config);
    }
  }

  private boolean checkValidDateExport() {
    String date = dfNgayChay.getText().trim();
    String regularDate = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

    if (!date.matches(regularDate)) {
      new MessageDialog("Error", "Ngày nhập vào không hợp lệ.", MessageDialog.CONTROLS_OK).show();
      return false;
    }

    try {
      dateUpdate = sdf.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
      return false;
    }

    if (!chkAllExport.isEnabled()) {
      String nameFact = getNameFact();
      if (nameFact == null || nameFact.trim().isEmpty()) {

        new MessageDialog("Error", "Vui lòng chọn xưởng xuất ký trình.", MessageDialog.CONTROLS_OK).show();
        return false;

      } else {
        // check index select
        if (((DefaultListModel) sfNameFact.getModel()).indexOf(sfNameFact.getSelectedItem()) == -1) {
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

  @SuppressWarnings("deprecation")
  private boolean checkValidComponents() throws ParseException {

    // check name fact
    String nameFact = sfNameFact.getSelectedItem().toString().trim();
    if (nameFact == null || nameFact.trim().isEmpty()) {

      new MessageDialog("Lỗi", "Vui lòng chọn xưởng chạy ký trình.", MessageDialog.CONTROLS_OK).show();
      return false;

    } else {
      if (((DefaultListModel) sfNameFact.getModel()).indexOf(nameFact) == -1) {
        new MessageDialog("Lỗi", "Xưởng không hợp lệ.", MessageDialog.CONTROLS_OK).show();
        return false;
      }
    }
    // check valid date
    String date = dfNgayChay.getText().trim();
    String regularDate = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

    if (!date.matches(regularDate)) {
      new MessageDialog("Lỗi", "Ngày nhập vào không hợp lệ.", MessageDialog.CONTROLS_OK).show();
      return false;
    }

    dateUpdate = sdf.parse(date);

    if (sfLanChay.getSelectedIndex() < 0) {
      new MessageDialog("Error", "Chưa chọn lần chạy ký trình.", MessageDialog.CONTROLS_OK).show();
      return false;
    }

    System.out.println("Lan chay " + LAN_CHAY);

    if (employeeInsuranceDAO.isCheckStatusUpdateOfFactory(getNameFact(), getMonthYear(dateUpdate),
        LAN_CHAY) == DA_CAP_NHAT) {
      new MessageDialog("Thông báo", "Xưởng " + getNameFact() + " đã cập nhật ký trình lần " + LAN_CHAY,
          MessageDialog.TYPE_WARNING + MessageDialog.CONTROLS_OK).show();
      return false;
    }

    // constraint ngay chay lan 1
    switch (LAN_CHAY) {
      case 1:

        if ((dateUpdate.getDate() < NGAY_QUY_DINH_CHAY_LAN_1)) {
          new MessageDialog("Thông báo", "Ký trình lần 1 được chạy từ ngày " + NGAY_QUY_DINH_CHAY_LAN_1 + " trở đi.",
              MessageDialog.CONTROLS_OK).show();
          return false;
        }
        break;
      case 2:// constraint lan chay 2

        if ((dateUpdate.getDate() < NGAY_QUY_DINH_CHAY_LAN_2)) {
          new MessageDialog("Thông báo", "Ký trình lần 2 được chạy từ ngày " + NGAY_QUY_DINH_CHAY_LAN_2 + " trở đi.",
              MessageDialog.CONTROLS_OK).show();
          return false;
        }
        // ===========================
        if (employeeInsuranceDAO.isCheckStatusUpdateOfFactory(getNameFact(), getMonthYear(dateUpdate),
            CHAY_LAN_1) == CHUA_CAP_NHAT) {
          new MessageDialog("Thông báo", "Xưởng " + getNameFact() + " chưa cập nhật ký trình lần 1.",
              MessageDialog.TYPE_WARNING + MessageDialog.CONTROLS_OK).show();
          return false;
        }

        // ===========================
        break;// case 2
    }
    //

    return true;
  }

  private String getMonthYear(Date ngayChayKT) {
    return new SimpleDateFormat("MM/yyyy").format(ngayChayKT);
  }

  /**
   * Description: cập nhật empl lần 1
   * 
   * @param i
   * @throws SQLException
   */
  private void updateEmplSubInsuranceTime1(int lanChay, Date dateUpdate, String nameFact) throws SQLException {
    // empl hien hanh
    updateEmplWorkingSubInsurance(lanChay, dateUpdate, nameFact);
    // empl nghi san
    updateEmplBearSubInsurance(lanChay, dateUpdate, nameFact);
    // empl nghi viec
    updateEmplQuitSubInsurance(lanChay, dateUpdate, nameFact);
  }

  private void updateEmplWorkingSubInsurance(int lanChay, Date dateUpdate, String nameFact) {
    // nghi viec tang lai
    // tang moi van nam trong gia tri the bao hiem
    List<N_EMPLOYEE> listEmplWorking = null;
    try {
      listEmplWorking = employeeInsuranceDAO.getEmployeeWorking(nameFact, dateUpdate);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (listEmplWorking != null) {
      for (N_EMPLOYEE emplWorking : listEmplWorking) {
        System.out.println("Test");
        // tinh dua theo thangtruBH
        @SuppressWarnings("deprecation")
        Date dateGetSalary = new Date(dateUpdate.getYear(), dateUpdate.getMonth(), NGAY_QUY_DINH_CHAY_LAN_1);

        // tổng lương
        DoubleHolder sumSalaryLan1 =
            new DoubleHolder(employeeInsuranceDAO.getSalaryInsuranceOfTime1(dateGetSalary, emplWorking.getEMPSN()));

        // lương cơ bản
        int bSalary = 0;
        try {
          bSalary = employeeInsuranceDAO.getBSalaryByDate(dateGetSalary, emplWorking.getEMPSN());
        } catch (SQLException e1) {
          e1.printStackTrace();
        } //

        // tiền BH phải trừ trong tháng
        float subMoney = getSubMoneyInMonth(bSalary);

        // thông tin cập nhật
        N_DETAIL_SUB_INSURANCE detailInsurance = new N_DETAIL_SUB_INSURANCE();
        detailInsurance.setPURCHASE_DATE(getMonthYearOfDate(dateUpdate));
        detailInsurance.setEMPSN(emplWorking.getEMPSN());
        detailInsurance.setBSALARY(bSalary);
        // detailInsurance.setPURCHASE_REAL_DATE(dateUpdate);
        detailInsurance.setLANCHAY(LAN_CHAY);
        detailInsurance.setINSURANCE_MONEY((long) subMoney);
        detailInsurance.setUSER_UP(userNameLogin);
        detailInsurance.setTOTAL_SALARY((long) sumSalaryLan1.value);
        detailInsurance.setMONTH_STATUS(EmployeeStatus.HIENHANH.name());
        detailInsurance.setID_DETAIL(
            emplWorking.getEMPSN() + "-" + getMonthYearOfDate(dateUpdate).replace("/", "") + "-" + lanChay);

        System.out.println("Salary lan 1 : " + sumSalaryLan1.value + " empsn " + emplWorking.getEMPSN());

        if (subMoneyInMonth(sumSalaryLan1, detailInsurance)) {
          try {
            // trừ nợ tháng cũ
            subDeptMoneyOldMonth(sumSalaryLan1, detailInsurance);
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }

        detailInsuranceDAO.saveOrUpdate(detailInsurance);

      }
    } // end if employeeBear
  }// end for list

  @SuppressWarnings("deprecation")
  private void updateEmplBearSubInsurance(int lanChay, Date dateUpdate, String nameFact) {
    List<EmployeeBear> list = null;

    try {
      list = employeeInsuranceDAO.getEmployeeBeared(nameFact, dateUpdate);
    } catch (Exception e) {
      System.err.println("Lỗi phát sinh từ employeeInsuranceDAO.getEmployeeBeared");
      e.printStackTrace();
    }
    if (list != null)
      for (EmployeeBear employeeBear : list) {
        if (employeeBear.getNOTE() != null && !employeeBear.getNOTE().trim().isEmpty()) {

          // tinh dua theo thangtruBH
          Date dateGetSalary = new Date(dateUpdate.getYear(), dateUpdate.getMonth(), NGAY_QUY_DINH_CHAY_LAN_1);

          // tổng lương
          DoubleHolder sumSalaryLan1 =
              new DoubleHolder(employeeInsuranceDAO.getSalaryInsuranceOfTime1(dateGetSalary, employeeBear.getEMPSN()));

          // lương cơ bản
          int bSalary = 0;
          try {
            bSalary = employeeInsuranceDAO.getBSalaryByDate(dateGetSalary, employeeBear.getEMPSN());
          } catch (SQLException e1) {
            e1.printStackTrace();
          } //

          // tiền BH phải trừ trong tháng
          float subMoney = getSubMoneyInMonth(bSalary);

          // thông tin cập nhật
          N_DETAIL_SUB_INSURANCE detailInsurance = new N_DETAIL_SUB_INSURANCE();
          detailInsurance.setPURCHASE_DATE(getMonthYearOfDate(dateUpdate));
          detailInsurance.setEMPSN(employeeBear.getEMPSN());
          detailInsurance.setBSALARY(bSalary);
          // detailInsurance.setPURCHASE_REAL_DATE(dateUpdate);
          detailInsurance.setLANCHAY(LAN_CHAY);
          detailInsurance.setINSURANCE_MONEY((long) subMoney);
          detailInsurance.setUSER_UP(userNameLogin);
          detailInsurance.setTOTAL_SALARY((long) sumSalaryLan1.value);
          detailInsurance.setMONTH_STATUS(EmployeeStatus.DANGNGHISAN.name());
          // try {
          // detailInsurance.setID_DETAIL(employeeBear.getEMPSN() +
          // "-"
          // + getMonthYearOfDate(dateUpdate).replace("/", "")
          // + "-" + lanChay);
          // } catch (Exception e) {
          // e.printStackTrace();
          // }
          // số tháng nghỉ sản
          Integer countMonthBear = Integer.parseInt(employeeBear.getNOTE());

          System.out.println("Salary lan 1 : " + sumSalaryLan1.value + " empsn " + employeeBear.getEMPSN());

          // trừ như hiện hành

          if (countMonthBear.intValue() < 6) {
            detailInsurance.setNOTE("Dang nghi san( NS nho < 6 thang tru nhu hien hanh).");

            if (subMoneyInMonth(sumSalaryLan1, detailInsurance)) {
              try {
                // trừ nợ tháng cũ
                subDeptMoneyOldMonth(sumSalaryLan1, detailInsurance);
              } catch (SQLException e) {
                e.printStackTrace();
              }

            }

            detailInsuranceDAO.saveOrUpdate(detailInsurance);

          } else// nghi san >6 không chịu phí đóng tiền BH
          {
            detailInsurance.setINSURANCE_MONEY((long) 0);
            detailInsurance.setNOTE("So thang dang ky NS > 6 thang khong chiu phi dong tien BH.");
            try {
              subDeptMoneyOldMonth(sumSalaryLan1, detailInsurance);
            } catch (SQLException e) {
              e.printStackTrace();
            }

            detailInsurance.setPURCHASE_STATUS(DA_TRU_TIEN);

          }

          detailInsuranceDAO.saveOrUpdate(detailInsurance);
        } // end if employeeBear
      } // end for list

    /*
     * empsn nghỉ sản vào nghỉ việc xét trong nghỉ việc. nghi san ket thuc trong
     * thang < 15 nhung.
     */
    List<EmployeeBear> lstEmpEndBear = null;
    try {
      lstEmpEndBear = employeeInsuranceDAO.getEmployeeEndBearInMonth(nameFact, dateUpdate);

    } catch (SQLException e) {
      e.printStackTrace();
    }

    if (lstEmpEndBear != null) {

      for (EmployeeBear employeeBear : lstEmpEndBear) {

        // tinh dua theo thangtruBH
        Date dateGetSalary = new Date(dateUpdate.getYear(), dateUpdate.getMonth(), NGAY_QUY_DINH_CHAY_LAN_1);

        // tổng lương
        DoubleHolder sumSalaryLan1 =
            new DoubleHolder(employeeInsuranceDAO.getSalaryInsuranceOfTime1(dateGetSalary, employeeBear.getEMPSN()));

        // lương cơ bản
        int bSalary = 0;
        try {
          bSalary = employeeInsuranceDAO.getBSalaryByDate(dateGetSalary, employeeBear.getEMPSN());
        } catch (SQLException e1) {
          e1.printStackTrace();
        } //

        // tiền BH phải trừ trong tháng
        float subMoney = getSubMoneyInMonth(bSalary);

        // thông tin cập nhật
        N_DETAIL_SUB_INSURANCE detailInsurance = new N_DETAIL_SUB_INSURANCE();
        detailInsurance.setPURCHASE_DATE(getMonthYearOfDate(dateUpdate));
        detailInsurance.setEMPSN(employeeBear.getEMPSN());
        detailInsurance.setBSALARY(bSalary);
        // detailInsurance.setPURCHASE_REAL_DATE(dateUpdate);
        detailInsurance.setLANCHAY(LAN_CHAY);
        detailInsurance.setINSURANCE_MONEY((long) subMoney);
        detailInsurance.setUSER_UP(userNameLogin);
        detailInsurance.setTOTAL_SALARY((long) sumSalaryLan1.value);

        try {
          detailInsurance.setID_DETAIL(
              employeeBear.getEMPSN() + "-" + getMonthYearOfDate(dateUpdate).replace("/", "") + "-" + lanChay);
        } catch (Exception e) {
          e.printStackTrace();
        }
        int dateEndBear = employeeBear.getE_DATES().getDate();

        // số tháng nghỉ sản
        Integer countMonthBear = Integer.parseInt(employeeBear.getNOTE());
        System.out.println(
            "EMPSN " + employeeBear.getEMPSN() + " dateEndBear " + dateEndBear + " NOTE " + employeeBear.getNOTE());
        if (countMonthBear >= 6) {
          // tang moi trong thang <=15 tinh tang moi trong thang
          if (dateEndBear <= NGAY_MUA_BH_CUA_NS) {
            // mua binh thuong
            detailInsurance.setNOTE(
                "Ngay ket thuc NS(" + sdf.format(employeeBear.getE_DATES()) + ") =< 15( tang moi trong thang)");
            detailInsurance.setMONTH_STATUS(EmployeeStatus.NGHISANVAO.name());

            if (subMoneyInMonth(sumSalaryLan1, detailInsurance)) {
              try {
                subDeptMoneyOldMonth(sumSalaryLan1, detailInsurance);
              } catch (SQLException e) {
                e.printStackTrace();
              }
            }
          } else {
            // van tinh nghi san
            detailInsurance.setINSURANCE_MONEY((long) 0);
            detailInsurance.setMONTH_STATUS(EmployeeStatus.DANGNGHISAN.name());
            detailInsurance.setNOTE("So thang dang ky nghi san >=6 thang( khong tru tien BH). Ket thuc nghi san ngay "
                + sdf.format(employeeBear.getE_DATES()));
            // detailInsurance.setPURCHASE_REAL_DATE(dateUpdate);
            detailInsurance.setPURCHASE_STATUS(DA_TRU_TIEN);

            try {
              subDeptMoneyOldMonth(sumSalaryLan1, detailInsurance);
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
        } else {
          // nghi san nho hon 6t tinh nhu hien hanh
          detailInsurance
              .setNOTE("So thang dang ky nghi san < 6 thang tinh nhu hien hanh. Nghi san ket thuc tai thang.");

          if (subMoneyInMonth(sumSalaryLan1, detailInsurance))
            try {
              subDeptMoneyOldMonth(sumSalaryLan1, detailInsurance);
            } catch (SQLException e) {
              e.printStackTrace();
            }
        }
        detailInsuranceDAO.saveOrUpdate(detailInsurance);

      }
    } // end for (EmployeeBear employeeBear : lstEmpEndBear)
  }

  /**
   * @param dateServer
   * @param lanChay
   * @throws SQLException
   */
  @SuppressWarnings({ "deprecation" })
  private void updateEmplQuitSubInsurance(int lanChay, Date dateUpdate, String nameFact) throws SQLException {
    // tạm ổn
    /*
     * 1.Loai nhung empl tang 2. giả sử trừ nợ cũ xong thiếu tiền trừ tháng hiện tại
     * trong kí trình không có // chưa làm tròn tiền trừ bảo hiểm // 3. ưu tiên trừ
     * nợ cũ trước hay hiện tại trước nghi viec tang lai thẻ vẫn còn thời hạn, xét ở
     * đâu 4. giả sử nghỉ việc rồi tang mới, nhưng vẫn nợ những tháng cũ and tháng
     * dept > tháng tăng mới
     */

    List<N_EMP_QUIT> lstEmpQuit = null;

    try {
      lstEmpQuit = employeeInsuranceDAO.getEmplQuitInMonth(nameFact, getMonthYearOfDate(dateUpdate));
    } catch (SQLException e1) {
      // e1.printStackTrace();
      System.out.println(e1.toString());
    }

    if (lstEmpQuit != null) {

      for (N_EMP_QUIT emplQuit : lstEmpQuit) {

        System.out.println("EMPSN : " + emplQuit.getEMPSN() + "\t" + emplQuit.getFACT_NAME());

        // tinh dua theo thangtruBH
        Date dateGetSalary = new Date(dateUpdate.getYear(), dateUpdate.getMonth(), NGAY_QUY_DINH_CHAY_LAN_1);

        // tổng lương
        DoubleHolder sumSalaryLan1 = new DoubleHolder(
            employeeInsuranceDAO.getSalaryEmpQuit1(dateGetSalary, emplQuit.getEMPSN(), emplQuit.getTHANG_TRUBH()));

        // lương cơ bản
        int bSalary = employeeInsuranceDAO.getBSalaryByDate(dateGetSalary, emplQuit.getEMPSN());//

        // tiền BH phải trừ trong tháng
        float subMoney = getSubMoneyInMonth(bSalary);

        // thông tin cập nhật
        N_DETAIL_SUB_INSURANCE detailInsurance = new N_DETAIL_SUB_INSURANCE();
        detailInsurance.setPURCHASE_DATE(getMonthYearOfDate(dateUpdate));
        detailInsurance.setEMPSN(emplQuit.getEMPSN());
        detailInsurance.setBSALARY(bSalary);
        // detailInsurance.setPURCHASE_REAL_DATE(dateUpdate);
        detailInsurance.setLANCHAY(LAN_CHAY);
        detailInsurance.setINSURANCE_MONEY((long) subMoney);
        detailInsurance.setUSER_UP(userNameLogin);
        detailInsurance.setTOTAL_SALARY((long) sumSalaryLan1.value);
        detailInsurance.setMONTH_STATUS(EmployeeStatus.NGHIVIEC.name());
        detailInsurance
            .setID_DETAIL(emplQuit.getEMPSN() + "-" + getMonthYearOfDate(dateUpdate).replace("/", "") + "-" + lanChay);

        // end thông tin

        /*
         * Trạng thái trả thẻ:
         */
        if (emplQuit.getTHE_BHYT() != null && Integer.parseInt(emplQuit.getTHE_BHYT()) == NGHI_VIEC_TRA_THE) {
          int date = emplQuit.getTHANG_TRUBH().getDate();

          // ngày
          if (date > NGAY_MUA_BH_CUA_NV) {
            // ====================================

            detailInsurance.setNOTE("Nghi viec ngay " + sdf.format(emplQuit.getREAL_OFF_DATE()) + ") thang tru BH : "
                + emplQuit.getTHANG_TRUBH() + "  < " + NGAY_MUA_BH_CUA_NV + " mua BH.");

            // trừ tiền bảo hiểm hiện tại của tháng
            if (subMoneyInMonth(sumSalaryLan1, detailInsurance)) {
              try {
                // tru no cu
                subDeptMoneyOldMonth(sumSalaryLan1, detailInsurance);
              } catch (SQLException e) {
                e.printStackTrace();
              }
            } // end

            // trừ nợ tháng cũ

            detailInsuranceDAO.saveOrUpdate(detailInsurance);

            // ====================================
          } else {
            // nghỉ việc trả thẻ đầu tháng không mua và tính nợ

            detailInsurance.setNOTE("Nghi viec ( tra the) ngay " + sdf.format(emplQuit.getTHANG_TRUBH()) + "  < ngay"
                + NGAY_MUA_BH_CUA_NV + " khong mua BH tai thang.");
            detailInsurance.setINSURANCE_MONEY(0l);
            try {
              subDeptMoneyOldMonth(sumSalaryLan1, detailInsurance);
            } catch (SQLException e) {
              e.printStackTrace();
            }

            try {
              detailInsuranceDAO.saveOrUpdate(detailInsurance);
            } catch (Exception e) {
              e.printStackTrace();
            }

          }

        } else// nghi viec khong tra the thì tính lương rồi trừ từng
        // tháng
        {
          // xet con nhiu thang nua la den quy
          // nghi viec tang lai

          // tính nợ xong rồi mới tính trả thẻ hay không

          // tổng tiền nợ trừ được
          if (subMoneyInMonth(sumSalaryLan1, detailInsurance)) {
            try {
              // tru no cu
              subDeptMoneyOldMonth(sumSalaryLan1, detailInsurance);
            } catch (SQLException e) {
              e.printStackTrace();
            }

            // đếm số tháng từ dateServer -> cuối quý
            int countmonth = countMonthOfQuarter(dateUpdate.getMonth() + 2);

            int count = 0;
            // int money = 0;

            while ((countmonth--) > 0 && sumSalaryLan1.value > subMoney) {
              sumSalaryLan1.value -= subMoney;
              // money += subMoney;
              count++;

              N_DETAIL_SUB_INSURANCE detail = new N_DETAIL_SUB_INSURANCE();
              Date date = new Date(dateUpdate.getYear(), dateUpdate.getMonth() + count, dateUpdate.getDate());
              detail.setPURCHASE_DATE(getMonthYearOfDate(date));
              detail.setEMPSN(emplQuit.getEMPSN());
              detail.setBSALARY(bSalary);
              detail.setPURCHASE_STATUS(DA_TRU_TIEN);
              detail.setPURCHASE_REAL_DATE(dateUpdate);
              detail.setLANCHAY(LAN_CHAY);
              detail.setINSURANCE_MONEY((long) subMoney);
              detail.setTOTAL_SALARY(0l);
              detail.setUSER_UP(userNameLogin);
              detail.setMONTH_STATUS(EmployeeStatus.NGHIVIEC.name());
              detail
                  .setID_DETAIL(emplQuit.getEMPSN() + "-" + getMonthYearOfDate(date).replace("/", "") + "-" + lanChay);
              detail
                  .setNOTE("Nghi viec khong tra the. Tru tien tai thang " + sdf.format(date) + " lan chay " + lanChay);
              detailInsuranceDAO.saveOrUpdate(detail);

              // save lai nhung thang da thanh toan tai lan chay
              // cua ky trinh
              N_PURCHASE_DEPT dept = new N_PURCHASE_DEPT();
              dept.setID_PURCHASE(detailInsurance.getID_DETAIL());
              dept.setID_DEPT_OLD(detail.getID_DETAIL());
              dept.setLANCHAY((long) lanChay);
              purchaseDeptDAO.saveOrUpdate(dept);

              // ================================
              detailInsurance.setTOTAL_SALARY((long) employeeInsuranceDAO.getSalaryEmpQuit1(dateGetSalary,
                  emplQuit.getEMPSN(), emplQuit.getTHANG_TRUBH()));
              detailInsurance.setNOTE("Nghi viec khong tra the ( so thang con lai trong quy "
                  + countMonthOfQuarter(dateUpdate.getMonth() + 1) + " T ). Xet luong thuc tru duoc " + (count + 1)
                  + " T.");

              // detailInsurance.setINSURANCE_MONEY(detailInsurance
              // .getINSURANCE_MONEY() + money);

              detailInsuranceDAO.saveOrUpdate(detailInsurance);

            }

          } // end
        }
      }
    }
  }

  private boolean subMoneyInMonth(DoubleHolder sumSalaryLan1, N_DETAIL_SUB_INSURANCE detailInsurance) {

    long subMoney = detailInsurance.getINSURANCE_MONEY();

    if (sumSalaryLan1.value > subMoney) {

      sumSalaryLan1.value -= subMoney;
      detailInsurance.setPURCHASE_STATUS(DA_TRU_TIEN);
      detailInsurance.setPURCHASE_REAL_DATE(dateUpdate);
      return true;
    } else {
      detailInsurance.setPURCHASE_STATUS(KHONG_DU_TIEN_TRU);
      // detailInsurance.setPURCHASE_REAL_DATE(null);
      return false;
    }

  }

  private float getSubMoneyInMonth(int bSalary) {
    return (bSalary * HE_SO_TRU_TIEN_BH) / 100;
  }

  // nen tach ra de vao class FvhrDate
  @SuppressWarnings("deprecation")
  private String getMonthYearOfDate(Date date) {
    return (date.getMonth() + 1) + "/" + (date.getYear() + 1900);
  }

  @SuppressWarnings({ "deprecation", "unused" })
  private String convertDateIntoString(Date date) {
    if (date.getYear() > 1900)
      return date.getDate() + "/" + date.getMonth() + "/" + date.getYear();
    else
      return date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900);
  }

  private int countMonthOfQuarter(int month) {
    int numMax = 0;
    switch (Integer.valueOf(month)) {

      case 1:
      case 7:
        numMax = 6;
        break;

      case 2:
      case 8:
        numMax = 5;
        break;

      case 3:
      case 9:
        numMax = 4;
        break;

      case 4:
      case 10:
        numMax = 3;
        break;

      case 5:
      case 11:
        numMax = 2;
        break;

      case 6:
      case 12:
        numMax = 1;
        break;
    }
    return numMax;
  }

  /**
   * <b>Trừ tiền nợ BH những tháng cũ
   * 
   * @param salaryTotal tổng tiền lương hiện tại.
   * @throws SQLException
   */
  private void subDeptMoneyOldMonth(DoubleHolder sumSalaryLan1, N_DETAIL_SUB_INSURANCE detailInsurance)
      throws SQLException {

    // list nợ cũ
    List<N_DETAIL_SUB_INSURANCE> lstDept = null;
    try {
      lstDept = employeeInsuranceDAO.getAllDeptInsuranceOfEmpl(detailInsurance.getEMPSN());
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }

    int countSeces = 0;

    if (lstDept.size() > 0) {

      for (N_DETAIL_SUB_INSURANCE Olddept : lstDept) {

        long deptMoney = Olddept.getINSURANCE_MONEY();

        if (sumSalaryLan1.value > deptMoney) {

          countSeces++;

          // tiền lương còn lại sau mỗi lần trừ nợ
          sumSalaryLan1.value -= deptMoney;

          // cập nhật lại status đã thanh toán (1)
          Olddept.setPURCHASE_STATUS(DA_TRU_TIEN);

          // cập nhật lại tháng đã thanh toán nợ cũ
          Olddept.setPURCHASE_REAL_DATE(dateUpdate);

          // insert thang da thanh toan
          N_PURCHASE_DEPT dept = new N_PURCHASE_DEPT();
          dept.setID_PURCHASE(detailInsurance.getID_DETAIL());
          dept.setID_DEPT_OLD(Olddept.getID_DETAIL());
          dept.setLANCHAY((long) detailInsurance.getLANCHAY());
          purchaseDeptDAO.save(dept);

          try {
            detailInsuranceDAO.update(Olddept);
          } catch (Exception e) {
            e.printStackTrace();
          }

        } else {
          break;
        }
      }

      String noteSuces = "";
      if (countSeces > 0) {
        noteSuces = " Xét nợ cũ : " + lstDept.size() + " tháng, trừ được: " + countSeces
            + (lstDept.size() != countSeces ? (" còn nợ: " + (lstDept.size() - countSeces)) : "") + " tháng.";

      } else {
        noteSuces = " No cu : " + lstDept.size() + " thang.";
      }
      detailInsurance.setNOTE(detailInsurance.getNOTE() + "\n" + noteSuces);
    } // end lstDept
  }

  private enum EmployeeStatus {
    HIENHANH,
    NGHIVIEC,
    NGHISANRA,
    NGHISANVAO,
    DANGNGHISAN,
    TANGMOI
  }

}
