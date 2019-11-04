package ds.program.fvhr.dao.insuranse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.swing.ListModel;

import net.sf.cglib.core.TinyBitSet;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.eclipse.jdt.internal.compiler.env.IDependent;
import org.jaxen.function.RoundFunction;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.syntax.jedit.InputHandler.insert_break;

import com.sun.org.apache.xpath.internal.operations.Bool;

import ds.program.fvhr.dao.generic.FvGenericDAO;
import ds.program.fvhr.domain.BHXH_TN;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.ui.insurance.BieuMau3A;
import ds.program.fvhr.ui.insurance.CauQuery;
import ds.program.fvhr.ui.insurance.DSNgayCongBaoHiem;
import ds.program.fvhr.ui.insurance.Tang1TayObject;
import ds.program.fvhr.util.OBJ_UTILITY;
import fv.components.SelectItem;
import fv.util.ApplicationHelper;
import fv.util.DbUtils;
import fv.util.DateUtils;
import fv.util.FVGenericInfo;
import fv.util.HSSFUtils;
import fv.util.JdbcDAO;
import fv.util.ListBinder;
import fv.util.MonthYearType;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.info.CompanyInfo;
import dsc.util.function.UUID;
import dsc.echo2app.program.DefaultProgram;


/**
 * Su dung jdbcTemplae thay vi PrepareStatement 
 *
 */
public class InsuranceDAO extends JdbcDAO {

  private int month;
  private int year;
  //2012 ty le nguoi lao dong tham gia dong BH : XH:7%, TN 1%; YT : 1.5%
  /*
  Thay doi dk ngay tra the ke tu 07/2013, bat dau sua 18/07/2013, HA
   <07/2013 : < ngay 10 se ko chiu phi
   >=07/2013: < ngay 15 se ko chiu phi	
   Date ngay10 	= ins.ToDate("10/"+mm+"/"+yy); --> 15	 
   */

  private double tyLeDongBHTN =
      Double.parseDouble(GetField("GIATRI3", "N_THAMSO", "TENTHAMSO", "", "", "TYLEBHTN", "", ""));
  private double tyLeDongBHXH =
      Double.parseDouble(GetField("GIATRI3", "N_THAMSO", "TENTHAMSO", "", "", "TYLEBHXH", "", ""));
  private double tyLeDongBHYT =
      Double.parseDouble(GetField("GIATRI3", "N_THAMSO", "TENTHAMSO", "", "", "TYLEBHYT", "", ""));

  private SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
  //	
  //	public InsuranceDAO() {
  //		CompanyInfo cominfo = Application.getApp().findCompanyById(Application.getApp().getLoginInfo().getCompanyID());
  //		DataSource ds = (DataSource) Application.getSpringContext().getBean(cominfo.getConnectionName());
  //		setDataSource(ds);		
  //	}

  public String get_todate(String ngay) {
    String sql = "to_date('" + ngay + "','dd/mm/yyyy')";
    return sql;
  }

  public Calendar get_toCalendar(String ngay) {
    Calendar kq = null;
    try {
      Date date = sf.parse(ngay);
      //Date date = sf.parse(ngay, 3);
      kq = Calendar.getInstance();
      kq.setTime(date);

    } catch (ParseException e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    return kq;
  }

  public List<Tang1TayObject> getDSTang1Tay(String factCondition, String month, String year) {

    String firstDay = DateUtils.getFirstDayString(this.month, this.year);
    String lastDay = DateUtils.getLastDayString(this.month, this.year);
    String lastDaySubtract1 = DateUtils.getLastDayString(this.month - 1, this.year);
    // ngay 15 thang truoc. VD tang moi T04 se lay HDLD lan 1 cua 15/03 va 01/04
    String day15Tru1 = DateUtils.getDay15String(this.month - 1, this.year);

    //		String day16 = TroioiDateUtils.getDay16String(this.month, this.year);
    String thang_bc = DateUtils.getThangChayBaoCao(this.month, this.year);
    String thang_truoc_bc = DateUtils.getThangChayBCTru1(this.month, this.year);

    String dk_LoaiTru = " and a.empsn not in (select h.empsn from n_not_insurance h \n"
        + " where h.empsn=a.empsn and h.dates<=" + get_todate(firstDay) + ")";
    //String ttBHYT	= ",(select id_health from n_health where empsn=a.empsn and clock=1) id_health, \n"+
    String ttBHYT = ", (select id_pro from n_health where empsn=a.empsn and clock=1) id_pro ,\n"
        + "(select id_hos from n_health where empsn=a.empsn and clock=1) id_hos \n";

    String sql = "select * from (\n" + "select a.Fname,a.lname,a.birthday,a.sex,a.position,A.birthplace\n"
        + ",bsaly_by_date(a.empsn," + get_todate(lastDay) + ") salary ,b.id_labour,b.date_s,a.empsn\n"
        + ",dt.name_fact||' '||dt.name_group as FACT_GROUP,dt.name_dept_name,a.date_hired, a.id_no,a.date_lamlai\n"
        + ",(select id_social from n_social where empsn=a.empsn and clock=1) id_social ,'" + thang_bc + "' month_bc\n"
        + ",'1 Naêm' as Note_Tang, a.ethnic, a.ngaycap_id\n"
        + ",(select c.id_city from n_city c where c.name_city=a.id_place) as id_place \n" +
        //", a.permanent_address \n"+
        ", a.permanent_address " + ttBHYT + "\n" + "From n_Employee a, n_labour b,n_department dt\n"
        + "where a.depsn=dt.id_dept  and a.empsn=b.empsn and b.clock='1'" + factCondition + "and (  ((b.date_s="
        + get_todate(firstDay) + " or b.date_s=" + get_todate(day15Tru1) + ")\n" + "and b.times=1)  or  (a.ngaynx_moi="
        + get_todate(firstDay) + "\n"
        + "and exists (select c.* from n_newworker_test c where c.empsn=a.empsn and c.dd_khu=1))  )\n"
        + "and a.depsn<>'00000' and a.empsn not in (select q.empsn from n_emp_quit q\n"
        + "Where q.from_date is null and q.to_date is null and q.note_giam_bhyt <>'GIAM TANG MOI'\n"
        + "and q.real_off_date<=" + get_todate(lastDay) + ")\n" + dk_LoaiTru +

        "union\n" + "select d.Fname,d.lname,d.birthday,d.sex,d.position,d.birthplace\n" + ",bsaly_by_date(a.empsn,"
        + get_todate(lastDay) + ") salary ,b.id_labour,b.date_s,d.empsn\n"
        + ",dt.name_fact||' '||dt.name_group as FACT_GROUP,dt.name_dept_name,d.date_hired, d.id_no,d.date_lamlai\n"
        + ",(select id_social from n_social where empsn=a.empsn and clock=1) id_social ,'" + thang_bc + "' month_bc\n"
        + ",'1 Naêm' as Note_Tang, d.ethnic, d.ngaycap_id\n"
        + ",(select c.id_city from n_city c where c.name_city=d.id_place) as id_place \n" +
        //", d.permanent_address \n"+
        ", d.permanent_address " + ttBHYT + "\n" + "From n_Employee d, n_labour b,n_department dt,n_emp_quit a\n"
        + "where a.depsn=dt.id_dept  and d.empsn=b.empsn and b.clock='1'" + factCondition + "and (  ((b.date_s="
        + get_todate(firstDay) + "\n" + "or b.date_s=" + get_todate(day15Tru1) + ") and b.times=1)\n"
        + "or  (d.ngaynx_moi=" + get_todate(firstDay) + "\n"
        + "and exists (select c.* from n_newworker_test c where c.empsn=a.empsn and c.dd_khu=1))  )\n"
        + "and d.empsn=a.empsn  and d.depsn='00000' and( (a.from_date is not null and a.to_date is not null)\n"
        + " or (a.note_giam_bhyt='GIAM TANG MOI') or( a.real_off_date>" + get_todate(lastDay) + "))\n" + dk_LoaiTru +

        "union\n" + "select c.Fname,c.lname,c.birthday,c.sex,c.position,c.birthplace\n" + ",bsaly_by_date(a.empsn,"
        + get_todate(lastDay) + ") salary ,b.id_labour,b.date_s,c.empsn\n"
        + ",dt.name_fact||' '||dt.name_group as FACT_GROUP,dt.name_dept_name,c.date_hired, c.id_no,c.date_lamlai\n"
        + ",(select id_social from n_social where empsn=c.empsn and clock=1) id_social\n" + ",'" + thang_bc
        + "' month_bc,'Di lam lai' as Note_Tang, c.ethnic, c.ngaycap_id\n"
        + ",(select d.id_city from n_city d where d.name_city=c.id_place) as id_place \n" +
        //", c.permanent_address \n"+
        ", c.permanent_address " + ttBHYT + "\n" + "From n_Employee c, n_labour b,n_department dt,n_emp_quit a\n"
        + "where a.depsn=dt.id_dept  and a.empsn=b.empsn and a.empsn=c.empsn and b.clock='1' " + factCondition
        + " and to_char(a.thang_tanglai,'mm/yyyy') ='" + thang_bc + "' and a.tyle_tanglai='1'\n"
        + "and c.empsn not in (select q.empsn from n_emp_quit q  Where (q.from_date is null and q.to_date is null\n"
        + "and q.note_giam_bhyt<>'GIAM TANG MOI') or (q.REAL_OFF_DATE<=to_date('" + lastDay
        + "','dd/mm/yyyy') and q.date_again is null))\n" + dk_LoaiTru + ")\n" + "order by empsn";

    Connection con = getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    List<Tang1TayObject> list = new ArrayList<Tang1TayObject>();
    try {
      System.out.println(sql);
      pstm = con.prepareStatement(sql);
      rs = pstm.executeQuery();
      while (rs.next()) {
        Tang1TayObject data = new Tang1TayObject();
        data.setHoTen(rs.getString("FNAME").trim() + " " + rs.getString("LNAME"));
        data.setDonVi(rs.getString("NAME_DEPT_NAME"));
        data.setEmpsn(rs.getString("EMPSN"));
        data.setLuong(rs.getBigDecimal("SALARY"));
        data.setGhiChu(rs.getString("NOTE_TANG"));
        if (rs.getString("ID_SOCIAL") == null) {
          data.setMaSoBHXH(null);
        } else {
          data.setMaSoBHXH(rs.getString("ID_SOCIAL"));
        }
        data.setNgaySinh(rs.getString("BIRTHDAY"));
        data.setThangTangMoi("01/" + rs.getString("MONTH_BC"));
        data.setFactGroup(rs.getString("FACT_GROUP"));
        data.setGioiTinh(rs.getString("SEX"));
        data.setDanToc(rs.getString("ETHNIC"));
        //ho so old co nguoi van null CMND
        if (rs.getString("ID_NO") == null) {
          data.setCMND(null);
        } else {
          data.setCMND(rs.getString("ID_NO"));
        }
        if (rs.getString("NGAYCAP_ID") == null) {
          data.setNgayCap(null);
        } else {
          data.setNgayCap(sf.format(rs.getDate("NGAYCAP_ID")));
        }
        if (rs.getString("ID_PLACE") == null) {
          data.setNoiCap(null);
        } else {
          data.setNoiCap(rs.getString("ID_PLACE"));
        }
        data.setHoKhau(rs.getString("PERMANENT_ADDRESS"));

        if (rs.getString("ID_PRO") == null) {
          data.setIdPro(null);
        } else {
          data.setIdPro(rs.getString("ID_PRO"));
        }
        if (rs.getString("ID_HOS") == null) {
          data.setIdHos(null);
        } else {
          data.setIdHos(rs.getString("ID_HOS"));
        }

        list.add(data);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.close(con);
    }
    return list;
  }

  public List<BieuMau3A> getDSBaoGiamTuan(String factCondition, Calendar ngayBaoGiam) {
    String[] thoiGian = DateUtils.getThoiGianGiamTuan(ngayBaoGiam);
    // month va year da duoc set khi goi thuc thi doExport ben ReportInsurance
    String ngay1 = DateUtils.getFirstDayString(month, year);
    String ngay15 = DateUtils.getDay15String(month, year);
    String ngayCuoi = DateUtils.getLastDayString(month, year);
    String dk_LoaiTru = " and a.empsn not in (select h.empsn from n_not_insurance h \n"
        + " where h.empsn=a.empsn and h.dates<=" + get_todate(ngay1) + ")";

    String sql = "select c.Fname ||' '||c.lname as ho_ten,c.birthday,c.sex,c.position,c.birthplace\n"
        + ",b.id_labour,nvl((select date_s from n_labour la where la.empsn=a.empsn and la.times=1),b.date_s) date_s\n"
        + ",c.empsn,dt.name_fact||' '||name_group as fact_group\n"
        + ",dt.name_dept_name,(SELECT D.ID_SOCIAL FROM N_SOCIAL D WHERE D.EMPSN=C.EMPSN AND D.CLOCK='1') ID_SOCIAL\n"
        + ",dt.id_dept,c.date_hired, c.id_no,a.real_off_date,'Nghi Viec' note_nv,to_char(a.real_off_date,'mm/yyyy') month_bc \n"
        + ",a.the_bhyt,a.date_bhyt,a.month_giambh, a.from_date,a.to_date,a.note_bh\n" +

        "From n_Employee c, n_labour b, n_Emp_Quit a,n_department dt\n"
        + "Where a.depsn=dt.id_dept  and b.empsn=c.empsn and a.empsn=c.empsn and b.clock=1\n"
        + "and a.from_date=to_date(?,'dd/mm/yyyy') and a.to_date=to_date(?,'dd/mm/yyyy')\n" + "and a.empsn not in\n"
        + "(select f.empsn from n_health  f\n"
        + "Where (b.date_s=to_date(?,'dd/mm/yyyy') or b.date_s=to_date(?,'dd/mm/yyyy')) and b.times=1\n"
        + "and c.empsn=f.empsn and a.date_bhyt <=f.date_bhyt+4 and f.clock=1 \n" +
        //xem lai dong nay
        " and b.date_s<=to_date(?,'dd/mm/yyyy') and a.note_giam_bhyt='GIAM TANG MOI')\n" +

        factCondition + dk_LoaiTru + " Order by dt.id_dept,c.empsn";

    Connection con = getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    List<BieuMau3A> list = new ArrayList<BieuMau3A>();
    try {
      //System.out.println(sql);
      pstm = con.prepareStatement(sql);
      //truyen param theo thu tu dau cham hoi, bat dau la 1
      pstm.setString(1, thoiGian[0]);
      pstm.setString(2, thoiGian[1]);
      pstm.setString(3, ngay1);
      pstm.setString(4, ngay15);
      pstm.setString(5, ngay1);
      rs = pstm.executeQuery();
      while (rs.next()) {
        BieuMau3A data = new BieuMau3A();
        data.setHoTen(rs.getString("HO_TEN"));
        data.setDonVi(rs.getString("NAME_DEPT_NAME"));
        data.setEmpsn(rs.getString("EMPSN"));
        data.setMaSoBHXH(rs.getString("ID_SOCIAL"));
        data.setNgaySinh(rs.getString("BIRTHDAY"));
        data.setFactGroup(rs.getString("FACT_GROUP"));
        data.setNgayThucNghi(sf.format(rs.getDate("REAL_OFF_DATE")));
        if (rs.getDate("DATE_BHYT") != null) {
          data.setNgayTraThe(sf.format(rs.getDate("DATE_BHYT")));
        } else {
          data.setNgayTraThe(null);
        }
        data.setCoTraThe(rs.getString("THE_BHYT"));
        String ngayBGiam = sf.format(ngayBaoGiam.getTime());
        data.setngayBaoGiam(ngayBGiam);
        list.add(data);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstm);
      DbUtils.close(con);
    }
    return list;
  }

  public Float[] getNgayCong(String empsn, MonthYearType month, boolean layTheoBangLuong, boolean luongThoiViec) {

    String thang = month.getMonthString();
    String nam = month.getYearString();
    String ngayCuoi = DateUtils.getLastDayString(Integer.parseInt(thang), Integer.parseInt(nam));

    String sql;
    if (layTheoBangLuong) {
      String bangLuong;
      if (luongThoiViec)
        bangLuong = "attquit" + nam + thang;
      else
        bangLuong = "att" + nam + thang;
      sql =
          "select att.tday TS_NGAYCONG, att.phep_ns TS_NS, (att.rest+att.rest_sick+att.other+att.nwhour) TS_RO, att.combsaly as luong\n"
              + "from " + bangLuong + " att where att.empsn=?";
    } else {
      String my = "'" + thang + "/" + nam + "'";
      sql = "select (select nvl(sum(g.ducls+g.nucls+(case when  mod(g.oth,8)=0 then  g.oth/8\n"
          + "when mod(g.oth,10.4)=0 then g.oth/(10.4) else 0 end) ),0)  From n_Data_daily g\n"
          + "Where g.empsn=a.empsn and to_char(g.dates,'mm/yyyy')=" + my + ")\n"
          + "+(select nvl(sum(g.rest_qtt),0) From n_Data_daily g , n_rest_kind aa Where g.empsn=a.empsn and g.rest_rs=aa.name_rest\n"
          + "and aa.id_rest_sal='C01' and to_char(g.dates,'mm/yyyy')=" + my + ") as ts_ngaycong\n"
          + ",(select nvl(sum(g.rest_qtt),0) From n_data_daily g where rest_rs ='NS'\n"
          + "and to_char(g.dates,'mm/yyyy')=(" + my + ") and g.empsn = a.empsn ) AS TS_NS\n"
          + ", (select nvl(sum(g.rest_qtt),0) From n_data_daily g where rest_rs IN ('PBAN','PBENH','DS','KC')\n"
          + "and to_char(g.dates,'mm/yyyy')=(" + my + ") and g.empsn = a.empsn ) AS TS_RO\n" +
          //", null as luong\n" +
          ",comsaly_by_date(c.empsn,to_date('" + ngayCuoi + "','dd/mm/yyyy'), bsaly_by_date(a.empsn,to_date('"
          + ngayCuoi + "','dd/mm/yyyy'))) luong\n" + "From n_Employee c, n_labour b, n_department dt,n_emp_quit a\n"
          + "Where c.depsn=dt.id_dept and b.empsn=c.empsn and a.empsn=c.empsn and b.clock=1  and c.empsn=?";
    }

    Connection con = getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    try {
      pstm = con.prepareStatement(sql);
      pstm.setString(1, empsn);
      rs = pstm.executeQuery();
      if (rs.next()) {
        Float tsNgayCong = rs.getFloat(1);
        Float tsNS = rs.getFloat(2);
        Float tsRO = rs.getFloat(3);
        Float luong = rs.getFloat(4);
        return new Float[] { tsNgayCong, tsNS, tsRO, luong };
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstm);
      DbUtils.close(con);
    }
    return null;
  }

  public String getBangLuong(String empsn, MonthYearType month) {
    String sql = "select * from dba_tables t where t.table_name=?";
    Connection con = getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    String thang = month.getMonthString();
    String nam = month.getYearString();
    try {
      String bangLuong = "ATT" + nam + thang;
      pstm = con.prepareStatement(sql);
      pstm.setString(1, bangLuong);
      rs = pstm.executeQuery();
      if (rs.next()) {
        DbUtils.close(rs);
        DbUtils.close(pstm);
        String sql1 = "select * from " + bangLuong + " where empsn=?";
        pstm = con.prepareStatement(sql1);
        pstm.setString(1, empsn);
        rs = pstm.executeQuery();
        if (rs.next())
          return bangLuong;
      }

      DbUtils.close(rs);
      DbUtils.close(pstm);
      bangLuong = "ATTQUIT" + nam + thang;
      pstm = con.prepareStatement(sql);
      pstm.setString(1, bangLuong);
      rs = pstm.executeQuery();
      if (rs.next()) {
        DbUtils.close(rs);
        DbUtils.close(pstm);
        String sql1 = "select * from " + bangLuong + " where empsn=?";
        pstm = con.prepareStatement(sql1);
        pstm.setString(1, empsn);
        rs = pstm.executeQuery();
        if (rs.next())
          return bangLuong;
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstm);
      DbUtils.close(con);
    }
    return null;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public Float getLuongHienHanh(String empsn) {
    String sql = "select t.salary from n_labour t where t.empsn=? and t.clock='1'";
    Connection con = getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    try {
      pstm = con.prepareStatement(sql);
      pstm.setString(1, empsn);
      rs = pstm.executeQuery();
      if (rs.next()) {
        return rs.getFloat(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstm);
      DbUtils.close(con);
    }
    return null;
  }

  public Float getLuongDieuChinh(String empsn, String monthYear) {
    String sql = "select t.new_sal from n_sub_labour t where t.id_contract like '" + empsn
        + "%' and to_char(t.dates_sign,'mm/yyyy')='" + monthYear + "'";
    Connection con = getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    try {
      pstm = con.prepareStatement(sql);
      rs = pstm.executeQuery();
      if (rs.next()) {
        return rs.getFloat(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstm);
      DbUtils.close(con);
    }
    return null;
  }

  public Date getNgayDiLamLai(String empsn, String monthYear) {
    String sql = "select max(t.date_again) date_again\n" + "From n_emp_quit t where to_char(t.date_again,'mm/yyyy')='"
        + monthYear + "'\n" + "and t.empsn ='" + empsn + "' and date_again is not null and t.month_giambh is not null";
    Connection con = getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    try {
      pstm = con.prepareStatement(sql);
      rs = pstm.executeQuery();
      if (rs.next()) {
        return rs.getDate(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstm);
      DbUtils.close(con);
    }
    return null;
  }

  public String GetField(String fieldGet, String tableName, String dk1, String dk2, String dk3, String val1,
      String val2, String val3) {
    String sql = "select " + fieldGet + " from " + tableName + " where 1=1 ";
    if (dk1 != "" && val1 != "") {
      sql = sql + " and " + dk1 + "='" + val1 + "'";
    }
    if (dk2 != "" && val2 != "") {
      sql = sql + " and " + dk2 + "='" + val2 + "'";
    }
    if (dk3 != "" && val3 != "") {
      sql = sql + " and " + dk3 + "='" + val3 + "'";
    }

    //System.out.println(sql);

    String field1;
    try {
      field1 = (String) getJdbcTemplate().queryForObject(sql, String.class);
    } catch (Exception e) {
      field1 = null;
    }
    return field1;
  }

  public boolean Check_exit(String table, String dk1, String dk2, String val1, String val2) {
    // ton tai tra ve true, else tra ve false
    boolean kq = true;
    String sql;
    sql = "select count(*) from " + table + " where 1=1 ";
    if (dk1 != "" && val1 != "") {
      sql = sql + " and " + dk1 + "='" + val1 + "'";
    }
    if (dk2 != "" && val2 != "") {
      sql = sql + " and " + dk2 + "='" + val2 + "'";
    }

    Connection con = getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    int dem = 0;
    try {
      pstm = con.prepareStatement(sql);
      rs = pstm.executeQuery();
      if (rs.next()) {
        dem = rs.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstm);
      DbUtils.close(con);
    }

    if (dem > 0) {
      kq = true;
    } else {
      kq = false;
    }

    return kq;
  }

  public boolean Check_LamViec7H(String table, String empsn, String tuNgay) {
    // ton tai tra ve true, else tra ve false, kiem tra trong table 
    //N_EALRY_AFTER_B va N_EALRY_BEFORE_B
    boolean kq = true;
    String sql;
    sql = "select count(*) from " + table + " where empsn='" + empsn + "'";
    sql = sql + " and B_DATES>=" + get_todate(tuNgay);
    System.out.println(sql);
    Connection con = getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    int dem = 0;
    try {
      pstm = con.prepareStatement(sql);
      rs = pstm.executeQuery();
      if (rs.next()) {
        dem = rs.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstm);
      DbUtils.close(con);
    }

    if (dem > 0) {
      kq = true;
    } else {
      kq = false;
    }

    return kq;
  }

  public boolean Check_NSRoRa_CuoiQuy(String soThe, String thoiGian) {
    boolean kq = true;
    String sql;
    sql = "select count(f.empsn) dem from n_social_infor_report f \n" + " where f.empsn='" + soThe + "'"
        + " and f.thoigian=to_Date('" + thoiGian + "'" + ",'" + "dd/mm/yyyy" + "') \n" +
        //" and f.trangthai=-1 and f.ghichu_trangthai in ('"+"NS-->RoRa"+"')";
        " and f.trangthai=-1 and f.ghichu_trangthai like ('" + "%RoRa" + "')";
    //System.out.println(sql);

    Connection con = getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    int dem = 0;
    try {
      pstm = con.prepareStatement(sql);
      rs = pstm.executeQuery();
      if (rs.next()) {
        dem = rs.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstm);
      DbUtils.close(con);
    }

    if (dem > 0) {
      kq = true;
    } else {
      kq = false;
    }

    return kq;
  }

  public boolean Check_RoVaoTuNSRoRa(String soThe, String tuThang, String denThang, String ngayBaoGiam) {
    //tuThang = thang NSVao. denThang= thang bao giam
    boolean kq = true;// =true la co RoVao ke tu luc NS->RoRa, flase la tu luc NS chua RoVao
    String sql;
    InsuranceDAO ins = new InsuranceDAO();
    sql = "select f.empsn, max(f.thoigian) thoigian1 from n_social_infor_report f \n" + " where f.empsn='" + soThe + "'"
        + " and f.thoigian>=" + ins.get_todate(tuThang) + " \n" + " and f.thoigian < " + ins.get_todate(denThang)
        + " and f.trangthai=1\n" +

        /*		"  and f.empsn in (select b.empsn from n_social_infor_report b \n"+
        		" where b.thoigian="+ins.get_todate(tuThang)+" and b.trangthai=-1 \n"+
        		" and b.ghichu_trangthai in ('"+"NS-->RoRa"+"') and b.empsn=f.empsn)\n"+
        		*/
        " group by f.empsn ";

    //System.out.println(sql);

    Connection con = getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    int dem = 0;
    try {
      pstm = con.prepareStatement(sql);
      rs = pstm.executeQuery();
      if (rs.next()) {
        Date tgianRoVao = rs.getDate(2);
        Date ngay20RoVao = ins.GetNgayGioiHanBC(sf.format(rs.getDate(2)));
        if (ToDate(ngayBaoGiam).compareTo(ngay20RoVao) >= 0) {
          dem = 1;
        } else {
          dem = 0;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstm);
      DbUtils.close(con);
    }

    if (dem == 1) {
      kq = true;
    } else {
      kq = false;
    }

    return kq;
  }

  public boolean CheckKhoaDataMonth(String empsn, String thang, String nam) {
    //=1 la false = da khoa data
    //=0 la true = chua khoa data

    boolean kq = true;
    InsuranceDAO ins = new InsuranceDAO();
    String checkField = ins.GetField("locked", "n_get_data", "empsn", "months", "years", empsn, thang, nam);
    boolean attLock = CheckKhoaAttLock("ATT" + nam + thang);
    if (checkField != null) {
      if (checkField.equals("1"))
        kq = false;
      else
        kq = true;
    } else {
      if (attLock == false)
        kq = false;
      else
        kq = true;
    }

    return kq;
  }

  public boolean CheckKhoaAttLock(String bangLuong) {
    //=1 la false = da khoa data
    //=0 la true = chua khoa data

    boolean kq = true;
    String checkField = GetField("TABLOCK", "ATTLOCK", "TABNAME", "", "", bangLuong, "", "");
    if (checkField != null) {
      if (checkField.equals("Y"))
        kq = false;
      else
        kq = true;
    } else
      kq = true;
    return kq;
  }

  public boolean CheckKhoaDotBaoGiam(String ngayBaoGiam) {
    //=1 la false = da khoa dot bao giam
    //=0 la true = chua khoa dot bao giam

    boolean kq = true;
    InsuranceDAO ins = new InsuranceDAO();
    String checkField = ins.GetField("status", "n_baogiam_status", "ngay_bg", "", "",
        "to_date('" + ngayBaoGiam + "','" + "dd/mm/yyyy" + "')", "", "");
    if (checkField != null) {
      if (checkField.equals("1")) {
        kq = false;
      } else {
        kq = true;
      }
    } else {
      kq = true;
    }

    return kq;
  }

  public boolean CheckKhoaGiamTrongTangMoi(String soThe, String ngayThucNghi, String noteGiamBHYT) {
    //=1 la false = da khoa dot bao giam trong tang moi
    //=0 la true = chua khoa dot bao giam trong tang moi

    boolean kq = true;
    InsuranceDAO ins = new InsuranceDAO();
    String checkField = ins.GetField("status", "n_emp_quit", "empsn", "real_off_date", "note_giam_bhyt", soThe,
        "to_date('" + ngayThucNghi + "','" + "dd/mm/yyyy" + "')", noteGiamBHYT);
    if (checkField != null) {
      if (checkField.equals("1")) {
        kq = false;
      } else {
        kq = true;
      }
    } else {
      kq = true;
    }

    return kq;
  }

  public String checkEmpsn(String soThe) {
    String chuoiTB = null;
    if (!soThe.matches("[0-9]{8}")) {
      chuoiTB = "Số thẻ không hợp lệ.";
    }
    IGenericDAO<N_EMPLOYEE, String> empDao = Application.getApp().getDao(N_EMPLOYEE.class);
    if (empDao.findById(soThe) == null) {
      chuoiTB = "Số thẻ không có trong hệ thống";
    }
    return chuoiTB;
  }

  public boolean checkQLyEmpsn(String soThe) {
    boolean kq = true;
    //kiem tra quyen quan ly		
    //Ham tra ve user_id trong n_users_list , ktra vung quan ly theo CTNS old
    String userIDOld = ApplicationHelper.getVftUserId();
    // ham nay tra ve list cung quan ly cua User (xx,yy,cc)
    String listVungQL = DbUtils.parseInStringParamValues(ApplicationHelper.getRightList());
    String sql = "SELECT count(E.EMPSN) FROM N_EMPLOYEE E, N_USER_LIMIT U \n"
        + "WHERE e.EMPSN=E.EMPSN AND E.USER_MANAGE_ID=U.MA_QL AND U.MA_USER='" + userIDOld + "'\n" + " and e.empsn='"
        + soThe + "' and e.USER_MANAGE_ID in " + listVungQL;
    int dem = 0;
    try {
      dem = (Integer) getJdbcTemplate().queryForObject(sql, Integer.class);
    } catch (Exception e) {
      dem = 0;
    }

    if (dem > 0)
      kq = true;
    else
      kq = false;

    return kq;
  }

  public boolean checkQLyDept(String depsn) {
    boolean kq = true;
    //kiem tra quyen quan ly	
    // true la co quyen qly
    //Ham tra ve user_id trong n_users_list , ktra vung quan ly theo CTNS old
    String userIDOld = ApplicationHelper.getVftUserId();
    // ham nay tra ve list cung quan ly cua User (xx,yy,cc)
    String listVungQL = DbUtils.parseInStringParamValues(ApplicationHelper.getRightList());
    String sql = "SELECT count(E.EMPSN) FROM N_EMPLOYEE E, N_USER_LIMIT U \n"
        + "WHERE e.EMPSN=E.EMPSN AND E.USER_MANAGE_ID=U.MA_QL AND U.MA_USER='" + userIDOld + "'\n" + " and e.depsn='"
        + depsn + "' and e.USER_MANAGE_ID in " + listVungQL;
    int dem = 0;
    try {
      dem = (Integer) getJdbcTemplate().queryForObject(sql, Integer.class);

    } catch (Exception e) {
      dem = 0;
    }

    if (dem > 0)
      kq = true;
    else
      kq = false;

    return kq;

  }

  public boolean checkQLyNDept(String fact, String group, String deptName) {
    boolean kq = true;
    String sql = "SELECT ID_DEPT FROM N_DEPARTMENT WHERE 1=1";
    if (fact != null)
      sql = sql + " and NAME_FACT='" + fact + "'";
    if (group != null)
      sql = sql + " and NAME_GROUP='" + group + "'";
    if (deptName != null)
      sql = sql + " and NAME_DEPT_NAME='" + deptName + "'";
    // chi kiem tra nhung don vi da co CNV roi, loai tru nhung don vi ko co nguoi
    sql = sql + " and ID_DEPT in (select distinct depsn from n_employee where depsn=id_dept)";

    OBJ_UTILITY obj = new OBJ_UTILITY();
    try {
      List<String> listData = obj.Exe_Sql_String(sql);
      for (int i = 0; i < listData.size(); i++) {
        String idDept = listData.get(i);
        boolean ktDept = checkQLyDept(idDept);
        if (ktDept == false) {
          kq = false;
          return false;// thoat khoi vong for
        }
        // kiem tra den het list
        else {
          kq = true;
        }
      }

    } catch (Exception e) {
      kq = false;
    }

    return kq;
  }

  public int getThamGiaBHXHTN(String soThe, String thoiGian) {
    int kq = 1;
    //kq=0	: ko tham gia BH vi huu tri/ CNV moi chua ky HDLD
    //kq=-1	: co tham gia BH nhung ko du cong --> ko mua BHXH-TN
    //kq=1	: co tham gia BH va du cong --> mua BHXH-TN
    // thoigian : dd/mm/yyyy, ko nhat thiet la ngay 1 cua thang
    int dkLoaiTru = 0; // =0 la loai tru, =1 la ok
    int dkNgayCong = 0; // =0 la loai tru, =1 la ok

    InsuranceDAO ins = new InsuranceDAO();
    String fieldGet = "check_dk_muabhxh_tn('" + soThe + "',to_Date('" + thoiGian + "','" + "dd/mm/yyyy" + "'))";
    dkLoaiTru = Integer.parseInt(ins.GetField(fieldGet, "dual", "", "", "", "", "", ""));
    fieldGet = "a_getdk_ngaycong('" + soThe + "',to_Date('" + thoiGian + "','" + "dd/mm/yyyy" + "'))";
    dkNgayCong = Integer.parseInt(ins.GetField(fieldGet, "dual", "", "", "", "", "", ""));

    if (dkLoaiTru == 0) {
      kq = 0;
    } else {
      if (dkNgayCong == 0) {
        kq = -1;
      } else {
        kq = 1;
      }
    }

    return kq;
  }

  public String getNgayCuoi(String thoiGian) {
    String ngayCuoi;
    Calendar tgian = Calendar.getInstance();
    InsuranceDAO ins = new InsuranceDAO();
    tgian = ins.get_toCalendar(thoiGian);
    int soNgay = tgian.getActualMaximum(Calendar.DAY_OF_MONTH);
    ngayCuoi = soNgay + thoiGian.substring(2, 10);
    return ngayCuoi;
  }

  public int getLuongTheoThang(String sothe, String thoiGian) {
    //thoi gian : dd/mm/yyyy
    int kq = 0;
    // lay luong theo thoi gian trong n_basic_salary
    InsuranceDAO ins = new InsuranceDAO();
    String ngayCuoi = ins.getNgayCuoi(thoiGian);
    //System.out.println(sothe+" "+ thoiGian+" "+ ngayCuoi);

    int luongCB =
        Integer.parseInt(ins.GetField("bsaly_by_date('" + sothe + "',to_date('" + ngayCuoi + "','dd/mm/yyyy')) ",
            "dual", "", "", "", "", "", ""));
    int luongHD = Integer.parseInt(
        ins.GetField("Comsaly_by_date('" + sothe + "',to_date('" + ngayCuoi + "','dd/mm/yyyy')," + luongCB + ") ",
            "dual", "", "", "", "", "", ""));
    kq = luongHD;
    return kq;
  }

  public Date ToDate(String thoiGian) {
    Date date = null;
    try {
      date = sf.parse(thoiGian);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return date;
  }

  public Date GetNgayGioiHanBC(String thoiGian) {
    // thoi gian : dd/mm/yyyy , luon la ngay 01
    Date ngayGioiHan = null;
    // VD thoi gian la 02/2013 thi den 20/03/2013 moi la ngay QT20, ngay gioi han bao cao
    Date thoiGianTemp;
    String ngay = null;
    String mm = null;

    InsuranceDAO ins = new InsuranceDAO();
    thoiGianTemp = ins.ToDate(thoiGian);

    Calendar tgTemp = Calendar.getInstance();
    tgTemp.setTime(thoiGianTemp);
    // + 1 thang
    tgTemp.add(tgTemp.MONTH, 1);
    thoiGianTemp = tgTemp.getTime();

    // if thang bao cao la thang cuoi quy tru 1 thi ngay QT 20 la ngay 12, doi ngay 15 10/06/2013
    //thang=dd/mm/yyyy
    mm = thoiGian.substring(3, 5);
    if (mm == "05" || mm == "11") {
      //ngay="12";	
      ngay = "15"; // 10/06/2013, c Uyen
    } else
      ngay = "20";

    ngay = ngay + "/" + sf.format(thoiGianTemp).substring(3, 10);

    ngayGioiHan = ins.ToDate(ngay);
    return ngayGioiHan;
  }

  public String[] getThongTinTangLai(String soThe, String thoiGian) {
    //thoigiam=mm/yyyy

    String ngayDiLamLai = null;
    String thangTangLai = null;
    String thoiDiemTangLai = null;
    String thoiGianTemp = null;

    String sql;
    sql = " select t.empsn,max(t.date_again) date_again From n_emp_quit t\n" + " where to_char(t.date_again,'"
        + "mm/yyyy" + "')='" + thoiGian + "'\n" + " and t.empsn ='" + soThe
        + "' and t.date_again is not null and t.month_giambh is not null \n" + " group by t.empsn ";

    Connection con = getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    int dem = 0;
    InsuranceDAO ins = new InsuranceDAO();

    try {
      pstm = con.prepareStatement(sql);
      rs = pstm.executeQuery();
      if (rs.next()) {
        ngayDiLamLai = sf.format(rs.getDate("DATE_AGAIN"));
        thangTangLai = ins.GetField("to_char(thang_tanglai,'" + "dd/mm/yyyy" + "')", "n_emp_quit", "empsn",
            "to_char(date_again,'" + "dd/mm/yyyy" + "')", "", soThe, ngayDiLamLai, "");
        thoiDiemTangLai = ins.GetField("tyle_tanglai", "n_emp_quit", "empsn",
            "to_char(date_again,'" + "dd/mm/yyyy" + "')", "", soThe, ngayDiLamLai, "");
      }
      /*			else{
      				ngayDiLamLai="";
      				thangTangLai="";
      				thoiDiemTangLai = "";
      			}*/
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstm);
      DbUtils.close(con);
    }

    return new String[] { ngayDiLamLai, thangTangLai, thoiDiemTangLai };
  }

  public String[] GetTTBaoGiamNgay11Den20(InsuranceDAO ins, String mm, String yy, String mCuoi, Date ngayBaoGiam,
      Date ngayTraThe, Date thangBaoGiam, String thoaiThu) {
    //thoi gian truyen vao la dd/mm/yyyy
    // thoigian la ngay01 cua khoang thoi gian bao giam tu ngay 11 den ngay 20, cung nam va cung thang 
    String tyleDong = "0";
    String tyleBS = "0";
    String tyleBSGiam = "0";
    String noteBS = null;
    String bsTuThang = null;
    String bsDenThang = null;
    // thoi gian thoai thu BHYT
    String ttTuThang = null;
    String ttDenThang = null;

    double tyleBHXH = Double.parseDouble(ins.GetField("GIATRI3", "N_THAMSO", "TENTHAMSO", "", "", "TYLEBHXH", "", ""));
    double tyleBHTN = Double.parseDouble(ins.GetField("GIATRI3", "N_THAMSO", "TENTHAMSO", "", "", "TYLEBHTN", "", ""));
    double tyleBHYT = Double.parseDouble(ins.GetField("GIATRI3", "N_THAMSO", "TENTHAMSO", "", "", "TYLEBHYT", "", ""));

    double tyleBHXHTN = tyleBHXH + tyleBHTN;
    double tyleAll = tyleBHXHTN + tyleBHYT;

    /*
    Thay doi dk ngay tra the ke tu 07/2013, bat dau sua 18/07/2013, HA
     <07/2013 : < ngay 10 se ko chiu phi
     >=07/2013: < ngay 15 se ko chiu phi	
     >=09/2013: < ngay 20 se ko chiu phi
     Date ngay10 	= ins.ToDate("10/"+mm+"/"+yy); --> 15	 
     */
    Date ngay01 = ins.ToDate("01/" + mm + "/" + yy);
    Date ngayGH1 = ToDate("01/07/2013");
    Date ngayGH2 = ToDate("01/09/2013");
    Date ngay10 = null;
    if (ngayBaoGiam.compareTo(ngayGH1) < 0)
      ngay10 = ins.ToDate("10/" + mm + "/" + yy);
    else if ((ngayBaoGiam.compareTo(ngayGH1) >= 0) && (ngayBaoGiam.compareTo(ngayGH2) < 0))
      ngay10 = ins.ToDate("15/" + mm + "/" + yy);
    else
      ngay10 = ins.ToDate("20/" + mm + "/" + yy);

    Date ngay01Next = null;

    Calendar tgTemp = Calendar.getInstance();
    tgTemp.setTime(ngay01);
    tgTemp.add(tgTemp.MONTH, 1);
    ngay01Next = tgTemp.getTime();

    if (ngayTraThe != null && ngayTraThe.compareTo(ngay10) <= 0) {
      if (thangBaoGiam.compareTo(ngay01) < 0) {
        tyleDong = String.valueOf(tyleBHXHTN);
        ;
      } else {
        tyleDong = String.valueOf(tyleAll);
        ;
      }
      tyleBS = "0";
      bsTuThang = null;
      bsDenThang = null;
      noteBS = "NViec co tra the BHYT < " + sf.format(ngay10);
      // if co thoai thu BHYT
      // if tra lai the BHYT truoc ngay10 thi se thoai thu tu thang mm--> het quy
      if (thoaiThu.compareTo("1") == 0) {
        tyleBSGiam = String.valueOf(tyleBHYT);
        ttTuThang = sf.format(ngay01);
        ttDenThang = "01/" + mCuoi + "/" + yy;
      }
    } else if ((ngayTraThe != null && ngayTraThe.compareTo(ngay10) > 0) || ngayTraThe == null) {
      if (thangBaoGiam.compareTo(ngay01) < 0) {
        tyleDong = String.valueOf(tyleBHXHTN);
      } else {
        tyleDong = String.valueOf(tyleAll);
      }
      tyleBS = String.valueOf(tyleBHYT);
      bsTuThang = "01/" + mm + "/" + yy;
      if (ngayTraThe != null) {
        bsDenThang = bsTuThang;
        noteBS = "NViec co tra the BHYT > " + sf.format(ngay10);
      } else {
        //if mm=06 or =12 thi bsDenThang= mm+yy, ko phai mCuoi+yy
        // vi day la giai doan giao giua m0 voi m1, cuoi quy cu voi dau quy moi
        // bao giam tu 11/06~20/06 or 11/12~20/12
        if (mm.compareTo("06") == 0 || mm.compareTo("12") == 0) {
          bsDenThang = bsTuThang;
        } else {
          bsDenThang = "01/" + mCuoi + "/" + yy;
        }
        noteBS = "NViec khong tra the BHYT.";
      }

      // if tra lai the BHYT truoc ngay10 thi se thoai thu tu thang mm--> het quy
      if (thoaiThu.compareTo("1") == 0 && ngayTraThe != null) {
        if (ngayTraThe != null) {
          //if mm=06 or =12 thi bsDenThang= mm+yy, ko phai mCuoi+yy
          // vi day la giai doan giao giua m0 voi m1, cuoi quy cu voi dau quy moi
          // bao giam tu 11/06~20/06 or 11/12~20/12
          // luc nay no da chiu phi T06/ T12 roi, ko thoai thu nua
          // chi thoai thu neu thoi gian bao giam ko phai la T06 va T12
          if (mm.compareTo("06") != 0 && mm.compareTo("12") != 0) {
            tyleBSGiam = String.valueOf(tyleBHYT);
            ttTuThang = sf.format(ngay01Next);
            ttDenThang = "01/" + mCuoi + "/" + yy;
          }
        }
      }
    }

    return new String[] { tyleDong, tyleBS, noteBS, bsTuThang, bsDenThang, tyleBSGiam, ttTuThang, ttDenThang };
  }

  public String[] GetTTBaoGiamNgay21Den10(InsuranceDAO ins, String mm1, String mm2, String yy1, String yy2,
      String mCuoi, Date ngayBaoGiam, Date ngayTraThe, Date thangBaoGiam, boolean theGiaHan, String thoaiThu) {
    //thoi gian truyen vao la dd/mm/yyyy , bao giam 
    // khoang thoi gian bao giam tu 21 den 10 cung nam , khac thang.  VD 21/09~10/10
    String tyleDong = "0";
    String tyleBS = "0";
    String tyleBSGiam = "0";
    String noteBS = null;
    String bsTuThang = null;
    String bsDenThang = null;
    String ttTuThang = null;
    String ttDenThang = null;

    double tyleBHXH = Double.parseDouble(ins.GetField("GIATRI3", "N_THAMSO", "TENTHAMSO", "", "", "TYLEBHXH", "", ""));
    double tyleBHTN = Double.parseDouble(ins.GetField("GIATRI3", "N_THAMSO", "TENTHAMSO", "", "", "TYLEBHTN", "", ""));
    double tyleBHYT = Double.parseDouble(ins.GetField("GIATRI3", "N_THAMSO", "TENTHAMSO", "", "", "TYLEBHYT", "", ""));

    double tyleBHXHTN = tyleBHXH + tyleBHTN;
    double tyleAll = tyleBHXHTN + tyleBHYT;

    Date ngay01m1 = ins.ToDate("01/" + mm1 + "/" + yy1);// ngay 01 cua thoi gian ngay 21
    Date ngay01m2 = ins.ToDate("01/" + mm2 + "/" + yy2);// ngay 01 cua thoi gian ngay 10

    /*
    Thay doi dk ngay tra the ke tu 07/2013, bat dau sua 18/07/2013, HA
     <07/2013 : < ngay 10 se ko chiu phi
     >=07/2013: < ngay 15 se ko chiu phi	
     >=09/2013: < ngay 20 se ko chiu phi
     Date ngay10 	= ins.ToDate("10/"+mm+"/"+yy); --> 15	 
     */
    Date ngayGH1 = ToDate("01/07/2013");
    Date ngayGH2 = ToDate("01/09/2013");
    // ngay 10 cua thoi gian ngay 10
    Date ngay10m2 = null;
    if (ngayBaoGiam.compareTo(ngayGH1) < 0)
      ngay10m2 = ins.ToDate("10/" + mm2 + "/" + yy2);
    else if ((ngayBaoGiam.compareTo(ngayGH1) >= 0) && (ngayBaoGiam.compareTo(ngayGH2) < 0))
      ngay10m2 = ins.ToDate("15/" + mm2 + "/" + yy2);
    else
      ngay10m2 = ins.ToDate("20/" + mm2 + "/" + yy2);

    Date ngay01Next = null;
    Calendar tgTemp = Calendar.getInstance();
    tgTemp.setTime(ngay01m2);
    tgTemp.add(tgTemp.MONTH, 1);
    ngay01Next = tgTemp.getTime();

    // if the gia han cho quy moi da ve
    if (theGiaHan == false) {
      if (ngayTraThe != null && ngayTraThe.compareTo(ngay10m2) <= 0) {
        if (thangBaoGiam.compareTo(ngay01m1) <= 0) {
          tyleDong = String.valueOf(tyleBHXHTN);
        } else {
          tyleDong = String.valueOf(tyleAll);
        }
        tyleBS = "0";
        bsTuThang = null;
        bsDenThang = null;
        noteBS = "NViec co tra the BHYT < " + sf.format(ngay10m2);

        // xet thoai thu BS BHYT
        if (thoaiThu.compareTo("1") == 0) {
          tyleBSGiam = String.valueOf(tyleBHYT);
          ttTuThang = sf.format(ngay01m2);
          ttDenThang = "01/" + mCuoi + "/" + yy2;
        }

      } else if ((ngayTraThe != null && ngayTraThe.compareTo(ngay10m2) > 0) || ngayTraThe == null) {
        if (thangBaoGiam.compareTo(ngay01m1) <= 0) {
          tyleDong = String.valueOf(tyleBHXHTN);
        } else {
          tyleDong = String.valueOf(tyleAll);
        }
        tyleBS = String.valueOf(tyleBHYT);
        bsTuThang = "01/" + mm2 + "/" + yy1;
        if (ngayTraThe != null) {
          bsDenThang = bsTuThang;
          noteBS = "NViec co tra the BHYT > " + sf.format(ngay10m2);
        } else {
          bsDenThang = "01/" + mCuoi + "/" + yy1;
          noteBS = "NViec khong tra the BHYT.";
        }

        // xet thoai thu BS BHYT
        if (thoaiThu.compareTo("1") == 0 && ngayTraThe != null) {
          tyleBSGiam = String.valueOf(tyleBHYT);
          ttTuThang = sf.format(ngay01Next);
          ttDenThang = "01/" + mCuoi + "/" + yy2;
        }
      }
    }
    // if the gia han cho quy moi chua ve
    else {
      if (thangBaoGiam.compareTo(ngay01m1) <= 0) {
        tyleDong = String.valueOf(tyleBHXHTN);
      }
      // trong CT old > ngay01 mm1 <= ngay 01 cua mm2, tai sao vay ta...?
      else {
        tyleDong = String.valueOf(tyleAll);
      }
      tyleBS = String.valueOf(tyleBHYT);
      bsTuThang = "01/" + mm2 + "/" + yy1;
      bsDenThang = "01/" + mCuoi + "/" + yy1;
      // if the gia han chua ve thi ko can thoai thu
    }
    return new String[] { tyleDong, tyleBS, noteBS, bsTuThang, bsDenThang, tyleBSGiam, ttTuThang, ttDenThang };
  }

  public String[] GetTTBaoGiamNgay21Den10KhacNam(InsuranceDAO ins, String mm1, String mm2, String yy1, String yy2,
      String mCuoi, Date ngayBaoGiam, Date ngayTraThe, Date thangBaoGiam, boolean theGiaHan, String thoaiThu) {

    //thoi gian truyen vao la dd/mm/yyyy , bao giam 
    // khoang thoi gian bao giam tu 21 den 10 cung nam , khac thang.  VD 21/09~10/10
    String tyleDong = "0";
    String tyleBS = "0";
    String tyleBSGiam = "0";
    String noteBS = null;
    String bsTuThang = null;
    String bsDenThang = null;
    String ttTuThang = null;
    String ttDenThang = null;

    double tyleBHXH = Double.parseDouble(ins.GetField("GIATRI3", "N_THAMSO", "TENTHAMSO", "", "", "TYLEBHXH", "", ""));
    double tyleBHTN = Double.parseDouble(ins.GetField("GIATRI3", "N_THAMSO", "TENTHAMSO", "", "", "TYLEBHTN", "", ""));
    double tyleBHYT = Double.parseDouble(ins.GetField("GIATRI3", "N_THAMSO", "TENTHAMSO", "", "", "TYLEBHYT", "", ""));

    double tyleBHXHTN = tyleBHXH + tyleBHTN;
    double tyleAll = tyleBHXHTN + tyleBHYT;

    Date ngay01m1 = ins.ToDate("01/" + mm1 + "/" + yy1);// ngay 01 cua thoi gian ngay 21
    Date ngay01m2 = ins.ToDate("01/" + mm2 + "/" + yy2);// ngay 01 cua thoi gian ngay 10

    /*
    Thay doi dk ngay tra the ke tu 07/2013, bat dau sua 18/07/2013, HA
     <07/2013 : < ngay 10 se ko chiu phi
     >=07/2013: < ngay 15 se ko chiu phi	
     >=09/2013: < ngay 20 se ko chiu phi
     Date ngay10 	= ins.ToDate("10/"+mm+"/"+yy); --> 15	 
     */
    Date ngayGH1 = ToDate("01/07/2013");
    Date ngayGH2 = ToDate("01/09/2013");
    // ngay 10 cua thoi gian ngay 10
    Date ngay10m2 = null;
    if (ngayBaoGiam.compareTo(ngayGH1) < 0)
      ngay10m2 = ins.ToDate("10/" + mm2 + "/" + yy2);
    else if ((ngayBaoGiam.compareTo(ngayGH1) >= 0) && (ngayBaoGiam.compareTo(ngayGH2) < 0))
      ngay10m2 = ins.ToDate("15/" + mm2 + "/" + yy2);
    else
      ngay10m2 = ins.ToDate("20/" + mm2 + "/" + yy2);

    Date ngay01Next = null;
    Calendar tgTemp = Calendar.getInstance();
    tgTemp.setTime(ngay01m2);
    tgTemp.add(tgTemp.MONTH, 1);
    ngay01Next = tgTemp.getTime();

    // if the gia han cho quy moi da ve
    if (theGiaHan == false) {
      if (ngayTraThe != null && ngayTraThe.compareTo(ngay10m2) <= 0) {
        if (thangBaoGiam.compareTo(ngay01m1) <= 0) {
          tyleDong = String.valueOf(tyleBHXHTN);
          tyleBS = "0.045";
          bsTuThang = "01/" + mm1 + "/" + yy1;
          bsDenThang = bsTuThang;
          noteBS = "NViec co tra the BHYT < " + sf.format(ngay10m2);
        } else {
          tyleDong = String.valueOf(tyleAll);
          tyleBS = "0";
          bsTuThang = null;
          bsDenThang = null;
          noteBS = "NViec co tra the BHYT < " + sf.format(ngay10m2);
        }
        // xet thoai thu BS BHYT
        if (thoaiThu.compareTo("1") == 0 && ngayTraThe != null) {
          tyleBSGiam = String.valueOf(tyleBHYT);
          ttTuThang = sf.format(ngay01m2);
          ttDenThang = "01/" + mCuoi + "/" + yy2;
        }
      } else if ((ngayTraThe != null && ngayTraThe.compareTo(ngay10m2) > 0) || ngayTraThe == null) {
        if (thangBaoGiam.compareTo(ngay01m1) <= 0) {
          tyleDong = String.valueOf(tyleBHXHTN);
        } else {
          tyleDong = String.valueOf(tyleAll);
        }
        tyleBS = String.valueOf(tyleBHYT);
        bsTuThang = "01/" + mm2 + "/" + yy2;
        if (ngayTraThe != null) {
          bsDenThang = bsTuThang;
          noteBS = "NViec co tra the BHYT > " + sf.format(ngay10m2);
        } else {
          bsDenThang = "01/" + mCuoi + "/" + yy2;
          noteBS = "NViec khong tra the BHYT.";
        }
        // xet thoai thu BS BHYT
        if (thoaiThu.compareTo("1") == 0 && ngayTraThe != null) {
          tyleBSGiam = String.valueOf(tyleBHYT);
          ttTuThang = sf.format(ngay01Next);
          ttDenThang = "01/" + mCuoi + "/" + yy2;
        }
      }
    }
    // if the gia han cho quy moi chua ve
    else {
      if (thangBaoGiam.compareTo(ngay01m1) <= 0) {
        tyleDong = String.valueOf(tyleBHXHTN);
      }
      // trong CT old > ngay01 mm1 <= ngay 01 cua mm2, tai sao vay ta...?
      else {
        tyleDong = String.valueOf(tyleAll);
      }
      tyleBS = String.valueOf(tyleBHYT);
      bsTuThang = "01/" + mm2 + "/" + yy2;
      bsDenThang = "01/" + mCuoi + "/" + yy2;

      // if the gia han chua ve thi ko can thoai thu
    }
    return new String[] { tyleDong, tyleBS, noteBS, bsTuThang, bsDenThang, tyleBSGiam, ttTuThang, ttDenThang };
  }

  // Ham tra ve ty le bao giam va ty le bao Tang BS BHYT + thoi gian tang BS dua theo ngay tra the va thoi gian bao giam
  public String[] GetTyLeGiamTangBSTheoNgayTraTheBHYT2Lan(Date ngayBaoGiam, Date ngayTraThe, String ngayThucNghi,
      Date thangbaoGiam, String thangBaoCaoGoc, boolean theGiaHan, String thoaiThu) {
    // theGiaHan = true la the gia han quy moi chua ve, chua nhan duoc
    // = false la the gia han da ve roi, da nhan duoc
    // thoi gian truyen vao la dd/mm/yyyy

    String m0 = null;
    String m1 = null;
    String m2 = null;
    String m3 = null;
    String m4 = null;
    String mCuoi = null;

    int mGoc = 0;
    //String yy		= null;
    int yy0 = 0;
    int yy1 = 0;
    int yy2 = 0;

    mGoc = Integer.parseInt(thangBaoCaoGoc.substring(3, 5));
    if (mGoc >= 1 && mGoc <= 3) {
      m0 = "12";
      m1 = "01";
      m2 = "02";
      m3 = "03";
      m4 = "04";
      mCuoi = "06";
    } else if (mGoc >= 4 && mGoc <= 6) {
      m0 = "03";
      m1 = "04";
      m2 = "05";
      m3 = "06";
      m4 = "07";
      mCuoi = "06";
    } else if (mGoc >= 7 && mGoc <= 9) {
      m0 = "06";
      m1 = "07";
      m2 = "08";
      m3 = "09";
      m4 = "10";
      mCuoi = "12";
    }
    //else if (mGoc>=10 && mGoc<=12){
    else {
      m0 = "09";
      m1 = "10";
      m2 = "11";
      m3 = "12";
      m4 = "01";
      mCuoi = "12";
    }
    yy1 = Integer.parseInt(sf.format(ngayBaoGiam).substring(6, 10));
    yy0 = yy1 - 1;
    yy2 = yy1 + 1;
    String mBaoGiam = sf.format(thangbaoGiam).substring(3, 5);

    InsuranceDAO ins = new InsuranceDAO();

    Date ngay11m0yy0 = ins.ToDate("11/" + m0 + "/" + yy0);
    Date ngay20m0yy0 = ins.ToDate("20/" + m0 + "/" + yy0);
    Date ngay20m0yy1 = ins.ToDate("20/" + m0 + "/" + yy0);
    Date ngay11m1yy1 = ins.ToDate("11/" + m1 + "/" + yy1);
    Date ngay20m1yy1 = ins.ToDate("20/" + m1 + "/" + yy1);
    Date ngay11m2yy1 = ins.ToDate("11/" + m2 + "/" + yy1);
    Date ngay20m2yy1 = ins.ToDate("20/" + m2 + "/" + yy1);
    Date ngay11m3yy1 = ins.ToDate("11/" + m3 + "/" + yy1);
    Date ngay20m3yy1 = ins.ToDate("20/" + m3 + "/" + yy1);
    Date ngay11m4yy1 = ins.ToDate("11/" + m4 + "/" + yy1);
    Date ngay20m4yy1 = ins.ToDate("20/" + m4 + "/" + yy1);
    Date ngay11m4yy2 = ins.ToDate("11/" + m4 + "/" + yy2);

    String[] data = null;
    // cung nam, cung thang m0, tu them vao
    if (ngayBaoGiam.compareTo(ngay11m0yy0) >= 0 && ngayBaoGiam.compareTo(ngay20m0yy0) <= 0) {
      data = ins.GetTTBaoGiamNgay11Den20(ins, m0, String.valueOf(yy0), mCuoi, ngayBaoGiam, ngayTraThe, thangbaoGiam,
          thoaiThu);
    }

    // cung nam, cung thang m1 VD : 11/01/2013 ~ 20/01/2013
    if (ngayBaoGiam.compareTo(ngay11m1yy1) >= 0 && ngayBaoGiam.compareTo(ngay20m1yy1) <= 0) {
      data = ins.GetTTBaoGiamNgay11Den20(ins, m1, String.valueOf(yy1), mCuoi, ngayBaoGiam, ngayTraThe, thangbaoGiam,
          thoaiThu);
    }
    // het cung, cung thang m1		
    // cung nam, cung thang voi m2
    else if (ngayBaoGiam.compareTo(ngay11m2yy1) >= 0 && ngayBaoGiam.compareTo(ngay20m2yy1) <= 0) {
      data = ins.GetTTBaoGiamNgay11Den20(ins, m2, String.valueOf(yy1), mCuoi, ngayBaoGiam, ngayTraThe, thangbaoGiam,
          thoaiThu);
    }
    // end cung nam , cung thang m2 (2)
    // cung nam, cung thang voi m3
    else if (ngayBaoGiam.compareTo(ngay11m3yy1) >= 0 && ngayBaoGiam.compareTo(ngay20m3yy1) <= 0) {
      data = ins.GetTTBaoGiamNgay11Den20(ins, m3, String.valueOf(yy1), mCuoi, ngayBaoGiam, ngayTraThe, thangbaoGiam,
          thoaiThu);
    }
    // end cung nam , cung thang m3
    // giam cung nam , khac thang VD : 21/01/2013 ~ 10/02/2013, tuc la < ngay 11/02
    if (m3 != "12" && m0 != "12") {
      // khac thang, cung nam, cung quy
      if (ngayBaoGiam.compareTo(ngay20m0yy1) > 0 && ngayBaoGiam.compareTo(ngay11m1yy1) < 0) {
        data = ins.GetTTBaoGiamNgay21Den10(ins, m0, m1, String.valueOf(yy1), String.valueOf(yy2), mCuoi, ngayBaoGiam,
            ngayTraThe, thangbaoGiam, theGiaHan, thoaiThu);
      } else if (ngayBaoGiam.compareTo(ngay20m1yy1) > 0 && ngayBaoGiam.compareTo(ngay11m2yy1) < 0) {
        data = ins.GetTTBaoGiamNgay21Den10(ins, m1, m2, String.valueOf(yy1), String.valueOf(yy2), mCuoi, ngayBaoGiam,
            ngayTraThe, thangbaoGiam, theGiaHan, thoaiThu);
      } else if (ngayBaoGiam.compareTo(ngay20m2yy1) > 0 && ngayBaoGiam.compareTo(ngay11m3yy1) < 0) {
        data = ins.GetTTBaoGiamNgay21Den10(ins, m2, m3, String.valueOf(yy1), String.valueOf(yy2), mCuoi, ngayBaoGiam,
            ngayTraThe, thangbaoGiam, theGiaHan, thoaiThu);
      }
      //khac thang, cung nam, khac quy VD 21/06 ~ 10/07
      else if (ngayBaoGiam.compareTo(ngay20m3yy1) > 0 && ngayBaoGiam.compareTo(ngay11m4yy1) < 0) {
        data = ins.GetTTBaoGiamNgay21Den10(ins, m3, m4, String.valueOf(yy1), String.valueOf(yy2), mCuoi, ngayBaoGiam,
            ngayTraThe, thangbaoGiam, theGiaHan, thoaiThu);
      }
    }
    //m3=12, giam khac nam, khac quy , giam tu 21/12 ~10/01, thang bao giam trong khoang T10~T12
    // or m0=12 , giam khac nam, khac quy, giam tu 21/12 ~10/01, thang bao giam trong khoang T01~T03
    else {
      if (ngayBaoGiam.compareTo(ngay20m0yy0) > 0 && ngayBaoGiam.compareTo(ngay11m1yy1) < 0) {
        data = ins.GetTTBaoGiamNgay21Den10KhacNam(ins, m0, m1, String.valueOf(yy0), String.valueOf(yy1), mCuoi,
            ngayBaoGiam, ngayTraThe, thangbaoGiam, theGiaHan, thoaiThu);
      } else if (ngayBaoGiam.compareTo(ngay20m3yy1) > 0 && ngayBaoGiam.compareTo(ngay11m4yy2) < 0) {
        data = ins.GetTTBaoGiamNgay21Den10KhacNam(ins, m3, m4, String.valueOf(yy1), String.valueOf(yy2), mCuoi,
            ngayBaoGiam, ngayTraThe, thangbaoGiam, theGiaHan, thoaiThu);
      }
    }
    return data;
    //return new String[]{tyleDong, tyleBS, noteBS, bsTuThang, bsDenThang, tyleBSGiam, ttTuThang, ttDenThang};
  }

  // Ham tra ve ty le bao giam va ty le bao Tang BS BHYT + thoi gian tang BS dua theo ngay tra the va thoi gian bao giam
  public String[] GetTyLeGiamTangBSTheoNgayTraTheBHYT(Date ngayBaoGiam, Date ngayTraThe, String ngayThucNghi,
      Date thangbaoGiam, String thangBaoCaoGoc, boolean theGiaHan, String thoaiThu) {
    // theGiaHan = true la the gia han quy moi chua ve, chua nhan duoc
    // = false la the gia han da ve roi, da nhan duoc
    // thoi gian truyen vao la dd/mm/yyyy

    String m0 = null;
    String m1 = null;
    String m2 = null;
    String m3 = null;
    String m4 = null;
    String mCuoi = null;

    int mGoc = 0;
    //String yy		= null;
    int yy0 = 0;
    int yy1 = 0;
    int yy2 = 0;

    mGoc = Integer.parseInt(thangBaoCaoGoc.substring(3, 5));
    if (mGoc >= 1 && mGoc <= 3) {
      m0 = "12";
      m1 = "01";
      m2 = "02";
      m3 = "03";
      m4 = "04";
      mCuoi = "06";
    } else if (mGoc >= 4 && mGoc <= 6) {
      m0 = "03";
      m1 = "04";
      m2 = "05";
      m3 = "06";
      m4 = "07";
      mCuoi = "06";
    } else if (mGoc >= 7 && mGoc <= 9) {
      m0 = "06";
      m1 = "07";
      m2 = "08";
      m3 = "09";
      m4 = "10";
      mCuoi = "12";
    }
    //else if (mGoc>=10 && mGoc<=12){
    else {
      m0 = "09";
      m1 = "10";
      m2 = "11";
      m3 = "12";
      m4 = "01";
      mCuoi = "12";
    }
    yy1 = Integer.parseInt(sf.format(ngayBaoGiam).substring(6, 10));
    yy0 = yy1 - 1;
    yy2 = yy1 + 1;
    String mBaoGiam = sf.format(thangbaoGiam).substring(3, 5);

    InsuranceDAO ins = new InsuranceDAO();

    Date ngay11m0yy0 = ins.ToDate("11/" + m0 + "/" + yy0);
    Date ngay20m0yy0 = ins.ToDate("20/" + m0 + "/" + yy0);
    Date ngay20m0yy1 = ins.ToDate("20/" + m0 + "/" + yy0);
    Date ngay11m1yy1 = ins.ToDate("11/" + m1 + "/" + yy1);
    Date ngay20m1yy1 = ins.ToDate("20/" + m1 + "/" + yy1);
    Date ngay11m2yy1 = ins.ToDate("11/" + m2 + "/" + yy1);
    Date ngay20m2yy1 = ins.ToDate("20/" + m2 + "/" + yy1);
    Date ngay11m3yy1 = ins.ToDate("11/" + m3 + "/" + yy1);
    Date ngay20m3yy1 = ins.ToDate("20/" + m3 + "/" + yy1);
    Date ngay11m4yy1 = ins.ToDate("11/" + m4 + "/" + yy1);
    Date ngay20m4yy1 = ins.ToDate("20/" + m4 + "/" + yy1);
    Date ngay11m4yy2 = ins.ToDate("11/" + m4 + "/" + yy2);

    String[] data = null;
    // cung nam, cung thang m0, tu them vao
    if (ngayBaoGiam.compareTo(ngay11m0yy0) >= 0 && ngayBaoGiam.compareTo(ngay20m0yy0) <= 0) {
      data = ins.GetTTBaoGiamNgay11Den20(ins, m0, String.valueOf(yy0), mCuoi, ngayBaoGiam, ngayTraThe, thangbaoGiam,
          thoaiThu);
    }

    // cung nam, cung thang m1 VD : 11/01/2013 ~ 20/01/2013
    else if (ngayBaoGiam.compareTo(ngay11m1yy1) >= 0 && ngayBaoGiam.compareTo(ngay20m1yy1) <= 0) {
      data = ins.GetTTBaoGiamNgay11Den20(ins, m1, String.valueOf(yy1), mCuoi, ngayBaoGiam, ngayTraThe, thangbaoGiam,
          thoaiThu);
    }
    // het cung, cung thang m1		
    // cung nam, cung thang voi m2
    else if (ngayBaoGiam.compareTo(ngay11m2yy1) >= 0 && ngayBaoGiam.compareTo(ngay20m2yy1) <= 0) {
      data = ins.GetTTBaoGiamNgay11Den20(ins, m2, String.valueOf(yy1), mCuoi, ngayBaoGiam, ngayTraThe, thangbaoGiam,
          thoaiThu);
    }
    // end cung nam , cung thang m2 (2)
    // cung nam, cung thang voi m3
    else if (ngayBaoGiam.compareTo(ngay11m3yy1) >= 0 && ngayBaoGiam.compareTo(ngay20m3yy1) <= 0) {
      data = ins.GetTTBaoGiamNgay11Den20(ins, m3, String.valueOf(yy1), mCuoi, ngayBaoGiam, ngayTraThe, thangbaoGiam,
          thoaiThu);
    }
    // end cung nam , cung thang m3
    // giam cung nam , khac thang VD : 21/01/2013 ~ 10/02/2013, tuc la < ngay 11/02
    //else if (m3!="12" && m0!="12"){		
    else if (m0 != "12") {
      // khac thang, cung nam, cung quy
      if (ngayBaoGiam.compareTo(ngay20m0yy1) > 0 && ngayBaoGiam.compareTo(ngay11m1yy1) < 0) {
        data = ins.GetTTBaoGiamNgay21Den10(ins, m0, m1, String.valueOf(yy1), String.valueOf(yy2), mCuoi, ngayBaoGiam,
            ngayTraThe, thangbaoGiam, theGiaHan, thoaiThu);
      } else if (ngayBaoGiam.compareTo(ngay20m1yy1) > 0 && ngayBaoGiam.compareTo(ngay11m2yy1) < 0) {
        data = ins.GetTTBaoGiamNgay21Den10(ins, m1, m2, String.valueOf(yy1), String.valueOf(yy2), mCuoi, ngayBaoGiam,
            ngayTraThe, thangbaoGiam, theGiaHan, thoaiThu);
      } else if (ngayBaoGiam.compareTo(ngay20m2yy1) > 0 && ngayBaoGiam.compareTo(ngay11m3yy1) < 0) {
        data = ins.GetTTBaoGiamNgay21Den10(ins, m2, m3, String.valueOf(yy1), String.valueOf(yy2), mCuoi, ngayBaoGiam,
            ngayTraThe, thangbaoGiam, theGiaHan, thoaiThu);
      }
      //khac thang, cung nam, khac quy VD 21/06 ~ 10/07
      else if (ngayBaoGiam.compareTo(ngay20m3yy1) > 0 && ngayBaoGiam.compareTo(ngay11m4yy1) < 0) {
        data = ins.GetTTBaoGiamNgay21Den10(ins, m3, m4, String.valueOf(yy1), String.valueOf(yy2), mCuoi, ngayBaoGiam,
            ngayTraThe, thangbaoGiam, theGiaHan, thoaiThu);
      }
    }
    //m3=12, giam khac nam, khac quy , giam tu 21/12 ~10/01, thang bao giam trong khoang T10~T12
    // or m0=12 , giam khac nam, khac quy, giam tu 21/12 ~10/01, thang bao giam trong khoang T01~T03
    else {
      if (ngayBaoGiam.compareTo(ngay20m0yy0) > 0 && ngayBaoGiam.compareTo(ngay11m1yy1) < 0) {
        data = ins.GetTTBaoGiamNgay21Den10KhacNam(ins, m0, m1, String.valueOf(yy0), String.valueOf(yy1), mCuoi,
            ngayBaoGiam, ngayTraThe, thangbaoGiam, theGiaHan, thoaiThu);
      } else if (ngayBaoGiam.compareTo(ngay20m3yy1) > 0 && ngayBaoGiam.compareTo(ngay11m4yy2) < 0) {
        data = ins.GetTTBaoGiamNgay21Den10KhacNam(ins, m3, m4, String.valueOf(yy1), String.valueOf(yy2), mCuoi,
            ngayBaoGiam, ngayTraThe, thangbaoGiam, theGiaHan, thoaiThu);
      }
    }
    return data;
    //return new String[]{tyleDong, tyleBS, noteBS, bsTuThang, bsDenThang, tyleBSGiam, ttTuThang, ttDenThang};
  }

  public String[] TTThamGiaBHGanNhat(String soThe, String thoiGian, String ngayBaoGiam) {
    String att = null;
    String attQuit = null;
    String yy = null;
    String mm = null;
    String temp = null;
    String thangGanNhatBH = null;
    String luongGanNhatBH = null;
    int bhxh = 0;
    InsuranceDAO ins = new InsuranceDAO();
    Date thangGanNhat = ins.ToDate(thoiGian);
    Date ngay20_GanNhat = ins.GetNgayGioiHanBC2Lan(thoiGian, ngayBaoGiam);
    int dkbh_GanNhat = ins.getThamGiaBHXHTN2Lan(soThe, thoiGian, ngayBaoGiam);
    int sal_GanNhat = 0;
    Date ngayKyHD = null;
    Date ngay15HD = null; // ngay 15 cua thang ky HDLD lan 1
    Date thangTangMoi = null;
    boolean exit_att = true;
    boolean exit_attEmpsn = true;
    boolean exit_attQ = true;
    boolean exit_attQEmpsn = true;

    //temp= ins.GetField("date_s", "n_labour", "empsn", "times", "", soThe, "1", "");
    temp = ins.GetField("to_char(date_s,'" + "dd/mm/yyyy" + "')", "n_labour", "empsn", "times", "", soThe, "1", "");
    ngay15HD = ins.ToDate("15/" + temp.substring(3, 10));
    ngayKyHD = ins.ToDate(temp);
    yy = thoiGian.substring(6, 10);
    mm = thoiGian.substring(3, 5);
    att = "ATT" + yy + mm;
    attQuit = "ATTQUIT" + yy + mm;
    //System.out.println(soThe);
    exit_att = ins.Check_exit("dba_tables", "table_name", "", att, "");
    if (exit_att) {
      exit_attEmpsn = ins.Check_exit(att, "empsn", "", soThe, "");
    } else {
      exit_attEmpsn = false;
    }
    exit_attQ = ins.Check_exit("dba_tables", "table_name", "", attQuit, "");
    if (exit_attQ) {
      exit_attQEmpsn = ins.Check_exit(attQuit, "empsn", "", soThe, "");
    } else {
      exit_attQEmpsn = false;
    }

    if (exit_att == true && exit_attEmpsn == true) {
      temp = ins.GetField("joininsu", att, "empsn", "", "", soThe, "", "");
      yy = att.substring(3, 7);
      mm = att.substring(7, 9);
      if (temp == null) {
        bhxh = 0;
      } else {
        bhxh = Integer.parseInt(temp);
      }
      thangGanNhatBH = "01/" + mm + "/" + yy;
      thangGanNhat = ins.ToDate(thangGanNhatBH);
      temp = ins.GetField("combsaly", att, "empsn", "", "", soThe, "", "");
      if (temp == null) {
        luongGanNhatBH = "0";
      } else {
        luongGanNhatBH = temp;
      }
    } else if (exit_attQ == true && exit_attQEmpsn == true) {
      temp = ins.GetField("joininsu", attQuit, "empsn", "", "", soThe, "", "");
      yy = att.substring(3, 7);
      mm = att.substring(7, 9);
      if (temp == null) {
        bhxh = 0;
      } else {
        bhxh = Integer.parseInt(temp);
      }
      thangGanNhatBH = "01/" + mm + "/" + yy;
      thangGanNhat = ins.ToDate(thangGanNhatBH);
      temp = ins.GetField("combsaly", attQuit, "empsn", "", "", soThe, "", "");
      if (temp == null) {
        luongGanNhatBH = "0";
      } else {
        luongGanNhatBH = temp;
      }
    } else {
      // da nghi viec va chua kip tinh luong thoi viec
      if (ins.ToDate(ngayBaoGiam).compareTo(ngay20_GanNhat) >= 0) {
        // luc nay thang gan nhat chinh bang thang dang truyen vao
        //theo le phai xet dk ngay cong 2lan nhu ben duoi, but do nghi viec nen phai xet cong het 1 thang
        // dkbh_GanNhat = ins.getThamGiaBHXHTN2Lan(soThe, sf.format(thangGanNhat), ngayBaoGiam);
        dkbh_GanNhat = ins.getThamGiaBHXHTN(soThe, sf.format(thangGanNhat));
        yy = sf.format(thangGanNhat).substring(6, 10);
        mm = sf.format(thangGanNhat).substring(3, 5);
        if (dkbh_GanNhat <= 0) {
          bhxh = 0;
        } else {
          // gan gia tri BHXH bat ky>0 de so sanh dk phia duoi
          bhxh = dkbh_GanNhat;
        }

        thangGanNhatBH = "01/" + mm + "/" + yy;
        thangGanNhat = ins.ToDate(thangGanNhatBH);
        luongGanNhatBH = String.valueOf(ins.getLuongTheoThang(soThe, thangGanNhatBH));
      } else {
        bhxh = 0;
        thangGanNhat = null;
        thangGanNhatBH = null;
      }
    }

    if (ngayKyHD.compareTo(ngay15HD) == 0) {
      //thangTangMoi = 
      int a = ngay15HD.getMonth() + 1;
      ngayKyHD.setMonth(a);
      //System.out.println(sf.format(ngay15HD));
    }
    while (bhxh <= 0) {
      int b = thangGanNhat.getMonth() - 1;
      thangGanNhat.setMonth(b);
      yy = sf.format(thangGanNhat).substring(6, 10);
      mm = sf.format(thangGanNhat).substring(3, 5);
      att = "ATT" + yy + mm;
      temp = ins.GetField("joininsu", att, "empsn", "", "", soThe, "", "");
      if (temp == null) {
        bhxh = 0;
      } else {
        bhxh = Integer.parseInt(temp);
      }
      thangGanNhatBH = "01/" + mm + "/" + yy;
      thangGanNhat = ins.ToDate(thangGanNhatBH);
      temp = ins.GetField("combsaly", att, "empsn", "", "", soThe, "", "");
      if (temp == null) {
        luongGanNhatBH = "0";
      } else {
        luongGanNhatBH = temp;
      }

      if (bhxh == 0 && thangGanNhat.compareTo(ngayKyHD) <= 0) {
        // if lay den thang bao tang moi ma BHXH van =0 thi lay bang thang tang moi va dung lai
        return new String[] { thangGanNhatBH, luongGanNhatBH };
      }
    }

    return new String[] { thangGanNhatBH, luongGanNhatBH };

  }

  public void ganDeptFactGroupCombobox(SelectField sfMaDonVi, SelectField sfXuong, SelectField sfNhom,
      SelectField sfDonVi) {
    // gan thong tin cho dept va filter
    //Dept
    ListBinder.bindSelectField(sfMaDonVi, FVGenericInfo.getAllDept(), false);
    ListBinder.bindSelectField(sfXuong, FVGenericInfo.getFactories(), false);
    ListBinder.bindSelectField(sfNhom, FVGenericInfo.getAllGroup(), true);
    ListBinder.bindSelectField(sfDonVi, FVGenericInfo.getAllDeptName(), true);
  }

  public String getFactCondition(SelectField sfMaDonVi, SelectField sfXuong, SelectField sfNhom, SelectField sfDonVi) {
    String condition = "";
    SelectItem factItem = (SelectItem) sfXuong.getSelectedItem();
    SelectItem groupItem = (SelectItem) sfNhom.getSelectedItem();
    SelectItem deptItem = (SelectItem) sfDonVi.getSelectedItem();
    SelectItem idDeptItem = (SelectItem) sfMaDonVi.getSelectedItem();

    if (sfMaDonVi.getSelectedIndex() >= 0) {
      condition = condition + " and dt.id_dept='" + idDeptItem.getValue() + "'";
    }

    if (sfXuong.getSelectedIndex() >= 0 && factItem.getValue() != null) {
      condition = condition + " and dt.name_fact='" + factItem.getValue() + "'";
    }
    if (sfNhom.getSelectedIndex() >= 0 && groupItem.getValue() != null) {
      condition = condition + " and dt.name_group='" + groupItem.getValue() + "'";
    }
    if (sfDonVi.getSelectedIndex() >= 0 && deptItem.getValue() != null) {
      condition = condition + " and dt.name_dept_name='" + deptItem.getValue() + "'";
    }
    return condition;
  }

  @SuppressWarnings("deprecation")
  public List<DSNgayCongBaoHiem> getDSNgayCongBaoHiem(String dkDonVi, Date thangBaoCao, String trangThai,
      Boolean nViecHHanh) {
    // trang thai bao cao 0: ko du cong; 1: du cong; 2 toan bo
    // nViecHHanh ==true thi la Nghi viec, = false la hien hanh
    System.out.println(thangBaoCao.getMonth() + 1);// thang chay tu 0 toi 11
    System.out.println(thangBaoCao.getYear() + 1900);// vi no tu tru 1900

    String ngay1 = DateUtils.getFirstDayString(thangBaoCao.getMonth() + 1, thangBaoCao.getYear() + 1900);
    ngay1 = get_todate(ngay1);
    String ngayCuoi = DateUtils.getLastDayString(thangBaoCao.getMonth() + 1, thangBaoCao.getYear() + 1900);
    ngayCuoi = get_todate(ngayCuoi);
    Float phanNuaCong = (float) DateUtils.fvWorkingDays(thangBaoCao.getMonth() + 1, thangBaoCao.getYear() + 1900) / 2;

    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(thangBaoCao.getTime());
    String thang = DateUtils.getMonth(cal);
    thang = thang + "/" + DateUtils.getYear(cal);
    String sql = "";
    Boolean flag = true;

    sql = getSqlBaoCao(ngay1, ngayCuoi, thang, dkDonVi, trangThai, nViecHHanh);
    /*
    		sql = "select a.empsn,a.fname||"+"' '"+"||a.lname hoten \n" +		
    		",(select dt.name_dept from n_department dt where dt.id_dept=a.depsn) TenDonVi\n" + 
    		",bsaly_by_date(a.empsn,"+ngayCuoi+") LuongCB\n" + 
    		",comsaly_by_date(a.empsn,"+ngayCuoi+"\n" + 
    		", bsaly_by_date(a.empsn,"+ngayCuoi+")) LuongHD\n" + 
    		",(select nvl(sum(g.ducls+g.nucls+(case when  mod(g.oth,8)=0 then  g.oth/8\n" + 
    		"when mod(g.oth,10.4)=0 then  g.oth/(10.4)else 0 end) ),0)\n" + 
    		"From n_Data_daily g  Where g.empsn=a.empsn and to_char(g.dates,'mm/yyyy')='"+thang+"')\n" + 
    		"+(select nvl(sum(g.rest_qtt),0) From n_Data_daily g , n_rest_kind aa\n" + 
    		"Where g.empsn=a.empsn and g.rest_rs=aa.name_rest and aa.id_rest_sal='C01'\n" + 
    		"and to_char(g.dates,'mm/yyyy')='"+thang+"') as tsNgayLamNghiCoLuong\n" + 
    		",get_restkind(a.empsn,"+ngay1+","+ngayCuoi+",'n_data_daily','NS') NS\n" + 
    		",get_restkind(a.empsn,"+ngay1+","+ngayCuoi+",'n_data_daily','NBU') NBU\n" + 
    		",get_restkind(a.empsn,"+ngay1+","+ngayCuoi+",'n_data_daily','PBAN')\n" + 
    		"+get_restkind(a.empsn,"+ngay1+","+ngayCuoi+",'n_data_daily','PBENH')\n" + 
    		"+get_restkind(a.empsn,"+ngay1+","+ngayCuoi+",'n_data_daily','DS') COPHEP\n" + 
    		",get_restkind(a.empsn,"+ngay1+","+ngayCuoi+",'n_data_daily','KC') KC\n" + 
    		",c.date_s NgayKyHD,c.expire ngayHHHD, c.LIMIT , 0 TIEN_BH, a.date_hired ngayNX, a.depsn \n" +		
    		" From n_employee a,n_labour c\n" +		 
    		" where a.depsn in (select dt.id_dept from n_department dt \n"+
    		" where dt.id_dept=a.depsn "+dkDonVi+")\n" +		 
    		" and c.limit in (select lk.id from n_labourkind lk where lk.id=c.limit)\n" + 
    		" and a.empsn=c.empsn and c.clock=1\n" + 
    		" and a.empsn not in (select b.empsn from n_emp_quit b\n" + 
    		" where b.real_off_date<"+ngay1+" AND b.DATE_AGAIN IS NULL)\n";
    		//" and a.empsn in ('05010171','97050003','02080094','97060020','04020148')";
    */
    //System.out.println(sql);

    Connection con = getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    List<DSNgayCongBaoHiem> list = new ArrayList<DSNgayCongBaoHiem>();
    try {

      pstm = con.prepareStatement(sql);
      rs = pstm.executeQuery();
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

      while (rs.next()) {
        double tsNgay = rs.getDouble("TSNGAYLAMNGHICOLUONG");
        if (trangThai.equals("0")) {
          if (tsNgay < phanNuaCong) {
            flag = true;
          } else {
            flag = false;
          }
        }
        //if (trangThai=="1") {
        if (trangThai.equals("1")) {
          if (tsNgay >= phanNuaCong) {
            flag = true;
          } else {
            flag = false;
          }
        }
        if (trangThai.equals("2")) {
          flag = true;
        }

        if (flag == true) {
          DSNgayCongBaoHiem data = new DSNgayCongBaoHiem();
          data.setEmpsn(rs.getString("EMPSN"));
          data.setHoTen(rs.getString("HOTEN"));
          data.setTenDonVi(rs.getString("TENDONVI"));
          data.setLuongCoBan(rs.getInt("LUONGCB"));
          data.setLuongHopDong(rs.getInt("LUONGHD"));
          data.setTsNgayLamNghiCoLuong(rs.getDouble("TSNGAYLAMNGHICOLUONG"));
          data.setTsNghiSan(rs.getDouble("NS"));
          data.setTsNghiBuBaoVe(rs.getDouble("NBU"));
          data.setTsNghiCoPhep(rs.getDouble("COPHEP"));
          data.setTsNghiKhangCong(rs.getDouble("KC"));
          // if la trang thai =2_ lay toan bo ds thi se ko phan biet da ky 
          // hay chua ky HDLD, ngay cong cung ko phan biet
          if (rs.getDate("NGAYKYHD") == null) {
            data.setNgayKyHD(null);
          } else {
            data.setNgayKyHD(rs.getDate("NGAYKYHD"));
          }

          if (rs.getString("LIMIT") == null) {
            data.setThoiHanHopDong(null);
          } else {
            if (rs.getString("LIMIT").equals("00000")) {
              data.setNgayHetHanHD(null);
              data.setThoiHanHopDong("HD DAI HAN");

            } else {
              data.setNgayHetHanHD(rs.getDate("NgayHHHD"));
              data.setThoiHanHopDong("HD 1 NAM");
            }
          }

          float tienBH = (float) (rs.getInt("LuongHD") * tyLeDongBHXH);
          String tienBH1 = GetField("c_mymod1(" + tienBH + ")", "dual", "", "", "", "", "", "");
          tienBH = Float.parseFloat(tienBH1);
          //System.out.println(tienBH);					
          data.setTienDongBHXH(tienBH);

          tienBH = (float) (rs.getInt("LuongHD") * tyLeDongBHTN);
          tienBH1 = GetField("c_mymod1(" + tienBH + ")", "dual", "", "", "", "", "", "");
          tienBH = Float.parseFloat(tienBH1);
          data.setTienDongBHTN(tienBH);
          data.setNgayNhapXuong(rs.getDate("NgayNX"));
          data.setMaDonVi(rs.getString("DEPSN"));

          String temp = null;
          String empsn = data.getEmpsn();
          temp = GetField("possn_by_date('" + empsn + "'," + ngayCuoi + ")", "dual", "", "", "", "", "", "");
          data.setChucVu(temp);
          temp = GetField("bonus2_by_date('" + empsn + "'," + ngayCuoi + ")", "dual", "", "", "", "", "", "");
          if ((temp == null) || (temp.equals(""))) {
            data.setPhuCapChucVu(0);
          } else {
            data.setPhuCapChucVu(Integer.parseInt(temp));
          }

          temp = GetField("son_get_job_id_for_emp('" + empsn + "'," + ngayCuoi + ")", "dual", "", "", "", "", "", "");
          //if ((temp.equals(null))||(temp.equals(""))){
          //if (temp.isEmpty()) {
          if ((temp == null) || (temp.equals(""))) {
            data.setCongViec("");
          } else {
            data.setCongViec(temp);
          }

          temp =
              GetField("son_get_job_bonus_for_emp('" + empsn + "'," + ngayCuoi + ")", "dual", "", "", "", "", "", "");
          if ((temp == null) || (temp.equals(""))) {
            data.setPhuCapCongViec(0);
          } else {
            data.setPhuCapCongViec(Integer.parseInt(temp));
          }

          /*					
          					temp = GetField("bonus4_by_date('"+empsn+"',"+ngayCuoi+")", "dual", "", "", "", "", "", "");
          					if ((temp==null)||(temp.equals(""))){
          						data.setPhuCapSinhHoat(0);
          					}
          					else{
          						data.setPhuCapSinhHoat(Integer.parseInt(temp));
          					}
          					
          					temp = GetField("bonus8_by_date('"+empsn+"',"+ngayCuoi+")", "dual", "", "", "", "", "", "");
          					if ((temp==null)||(temp.equals(""))){
          						data.setPhuCapXangDau(0);
          					}
          					else{
          						data.setPhuCapXangDau(Integer.parseInt(temp));
          					}	
          					
          					temp = GetField("bonus1_by_date('"+empsn+"',"+ngayCuoi+")", "dual", "", "", "", "", "", "");
          					if ((temp==null)||(temp.equals(""))){
          						data.setPhuCapSanLuong(0);
          					}
          					else{
          						data.setPhuCapSanLuong(Integer.parseInt(temp));
          					}					
          					
          					temp = GetField("bonus5_by_date('"+empsn+"',"+ngayCuoi+")", "dual", "", "", "", "", "", "");
          					if ((temp==null)||(temp.equals(""))){
          						data.setBuLuongThangTruoc(0);
          					}
          					else{
          						data.setBuLuongThangTruoc(Integer.parseInt(temp));
          					}	
          */
          // con Bonus9 va Temp1
          temp = GetField("max(real_off_date)", "n_emp_quit", "empsn", "", "", empsn, "", "");
          if ((temp == null) || (temp.equals(""))) {
            data.setGhiChuNghiViec(null);
          } else {
            data.setGhiChuNghiViec(temp);
          }

          list.add(data);
        }

      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstm);
      DbUtils.close(con);
    }
    return list;
  }

  private String getSqlBaoCao(String ngay1, String ngayCuoi, String thang, String dkDonVi, String trangThai,
      Boolean nViecHHanh) {
    String sql = "";
    String dkTable = "";
    String dkChung = "";
    String dkRieng = "";
    sql = "select a.empsn,a.fname||" + "' '" + "||a.lname hoten \n"
        + ",(select dt.name_dept from n_department dt where dt.id_dept=a.depsn) TenDonVi\n" + ",bsaly_by_date(a.empsn,"
        + ngayCuoi + ") LuongCB\n" + ",comsaly_by_date(a.empsn," + ngayCuoi + "\n" + ", bsaly_by_date(a.empsn,"
        + ngayCuoi + ")) LuongHD\n" + ",(select nvl(sum(g.ducls+g.nucls+(case when  mod(g.oth,8)=0 then  g.oth/8\n"
        + "when mod(g.oth,10.4)=0 then  g.oth/(10.4)else 0 end) ),0)\n"
        + "From n_Data_daily g  Where g.empsn=a.empsn and to_char(g.dates,'mm/yyyy')='" + thang + "')\n"
        + "+(select nvl(sum(g.rest_qtt),0) From n_Data_daily g , n_rest_kind aa\n"
        + "Where g.empsn=a.empsn and g.rest_rs=aa.name_rest and aa.id_rest_sal='C01'\n"
        + "and to_char(g.dates,'mm/yyyy')='" + thang + "') as tsNgayLamNghiCoLuong\n" + ",get_restkind(a.empsn," + ngay1
        + "," + ngayCuoi + ",'n_data_daily','NS') NS\n" + ",get_restkind(a.empsn," + ngay1 + "," + ngayCuoi
        + ",'n_data_daily','NBU') NBU\n" + ",get_restkind(a.empsn," + ngay1 + "," + ngayCuoi
        + ",'n_data_daily','PBAN')\n" + "+get_restkind(a.empsn," + ngay1 + "," + ngayCuoi + ",'n_data_daily','PBENH')\n"
        + "+get_restkind(a.empsn," + ngay1 + "," + ngayCuoi + ",'n_data_daily','DS') COPHEP\n"
        + ",get_restkind(a.empsn," + ngay1 + "," + ngayCuoi + ",'n_data_daily','KC') KC\n"
        + ", 0 TIEN_BH, a.date_hired ngayNX, a.depsn\n";

    if ((nViecHHanh == false) && (!trangThai.equals("2"))) {
      dkTable = ",c.date_s NgayKyHD,c.expire ngayHHHD, c.LIMIT  \n" + " From n_employee a,n_labour c\n";
      dkRieng = " and c.limit in (select lk.id from n_labourkind lk where lk.id=c.limit)\n"
          + " and a.empsn=c.empsn and c.clock=1\n";
    } else if ((nViecHHanh == false) && (trangThai.equals("2"))) {
      // gan 3 file ao de khop column header
      //dkTable = ",null NgayKyHD,null ngayHHHD, null LIMIT  \n" +
      dkTable = ",(select lb.date_s from n_labour lb where lb.empsn=a.empsn and lb.clock='1') NgayKyHD\n"
          + ",(select lb.expire from n_labour lb where lb.empsn=a.empsn and lb.clock='1') ngayHHHD\n"
          + ",(select lb.limit from n_labour lb where lb.empsn=a.empsn and lb.clock='1') LIMIT\n"
          + " From n_employee a \n";
      dkRieng = "";
    }
    if ((nViecHHanh == true) && (!trangThai.equals("2"))) {
      dkTable = ",c.date_s NgayKyHD,c.expire ngayHHHD, c.LIMIT  \n" + " From n_emp_quit a,n_labour c\n";
      dkRieng = " and c.limit in (select lk.id from n_labourkind lk where lk.id=c.limit)\n"
          + " and a.empsn=c.empsn and c.clock=1\n";
    } else if ((nViecHHanh == true) && (trangThai.equals("2"))) {
      // gan 3 file ao de khop column header
      dkTable = ",(select lb.date_s from n_labour lb where lb.empsn=a.empsn and lb.clock='1') NgayKyHD\n"
          + ",(select lb.expire from n_labour lb where lb.empsn=a.empsn and lb.clock='1') ngayHHHD\n"
          + ",(select lb.limit from n_labour lb where lb.empsn=a.empsn and lb.clock='1') LIMIT\n"
          + " From n_emp_quit a \n";
      dkRieng = "";
    }

    dkChung = " where a.depsn in (select dt.id_dept from n_department dt \n" + " where dt.id_dept=a.depsn " + dkDonVi
        + ")\n" + " and a.empsn not in (select b.empsn from n_emp_quit b\n" + " where b.real_off_date<" + ngay1
        + " AND b.DATE_AGAIN IS NULL)";

    sql = sql + dkTable + dkChung + dkRieng;

    return sql;
  }

  public HSSFWorkbook getWorkbook(String parentDir, String fileName) throws IOException {
    File f = ReportFileManager.getReportFormatFolder(parentDir);
    InputStream in = new FileInputStream(new File(f.getPath(), fileName));
    return new HSSFWorkbook(in);
  }

  //Kiem tra viec khoa data
  public boolean check_lock_month_a(String sothe, String xuong, String nhom, String tendv, String ma_dv, Date date_,
      String con_dept, String ma_user, RadioButton f1, RadioButton f2, RadioButton f3, RadioButton f5, RadioButton f6,
      RadioButton khac)//con_dept: lay dvi la depsn hay la depsn_temp
  {
    String date_str = sf.format(date_);
    String month = date_str.substring(3, 5);
    String year = date_str.substring(6, 10);
    boolean flag = true;

    String sql = "select count(t.Tabname)\n" + "from attlock t\n" + " where t.tabname = 'ATT" + year + "" + month + "'"
        + " and  t.tablock = 'Y'";

    OBJ_UTILITY obj_util = new OBJ_UTILITY();
    Object obj = obj_util.Exe_Sql_Obj(sql);

    if (Long.valueOf(obj.toString()) == 0) {
      ArrayList<String> list_dept = new ArrayList<String>();
      list_dept = obj_util.getListDept(sothe, xuong, nhom, tendv, ma_dv, con_dept, ma_user, f1, f2, f3, f5, f6, khac);

      for (String dept : list_dept)//chi can 1 dvi khoa la k the nhap ca nhom or ca xuong co dvi do truc thuoc
      {
        String sql1 = "select count(t.depsn)  from n_fact_transfer_lock t\n" + " where t.year = '" + year
            + "' and t.month = '" + month + "'" + " and t.locked = 1\n" + " and t.depsn = '" + dept + "'";

        Object obj1 = obj_util.Exe_Sql_Obj(sql1);

        if (Long.valueOf(obj1.toString()) == 0) {
          flag = true;
          continue;
        } else {
          OBJ_UTILITY.ShowMessageError("Dữ liệu đã khóa");
          flag = false;
          return flag;
        }

      }

    } else {
      OBJ_UTILITY.ShowMessageError("Dữ liệu đã khóa");
      flag = false;
      return flag;
    }

    return flag;
  }

  public List<Map<String, Object>> getDSTang20Tay(Object... args) {
    return getSimpleJdbcTemplate().query("", new ParameterizedRowMapper<Map<String, Object>>() {
      @Override
      public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {

        return null;
      }
    }, args);
  }

  /*@param thangGioiHan dd/mm/yyyy
   * @return thangNSVao
   * 
   */
  public Date getThangNSVao(String soThe, String thangGioiHan) {
    Date thangNSVao = null; // luon tra ve ngay 1 cho de so sanh

    String ngayCuoi = get_todate(getNgayCuoi(thangGioiHan));

    // NSRa lay theo thang
    String temp = null;
    temp = GetField("to_char(get_thang_ns_vao_empsn1('" + soThe + "'," + ngayCuoi + "),'" + "dd/mm/yyyy" + "')", "dual",
        "", "", "", "", "", "");
    if (temp == null) {
      thangNSVao = null;
    } else {
      thangNSVao = ToDate(temp);
    }
    return thangNSVao;
  }

  /*@param thangGioiHan dd/mm/yyyy
   * @return thangNSRa
   * 
   */
  public Date getThangNSRa(String soThe, String thangGioiHan) {
    Date thangNSRa = null; // luon tra ve ngay 1 cho de so sanh

    String ngayCuoi = get_todate(getNgayCuoi(thangGioiHan));

    // NSRa lay theo thang
    String temp = null;
    temp = GetField("to_char(get_thang_ns_ra1('" + soThe + "'," + ngayCuoi + "),'" + "dd/mm/yyyy" + "')", "dual", "",
        "", "", "", "", "");
    if (temp == null) {
      thangNSRa = null;
    } else {
      thangNSRa = ToDate(temp);
    }
    return thangNSRa;
  }

  /**
   * Danh sách tăng 20 tây : NSVao+RoVao+DiLamLai+Thoai Thu BS BHYT cho DiLamLai va RoVao (neu co)(Báo cáo Bảo Hiểm ...)
   * @param month 1-12
   * @param year
   * @return
   */
  public List<Map<String, Object>> getTang20TayList(String dkFact, int month, int year) {
    String query = CauQuery.getTang20TayQuery(dkFact, month, year);
    return getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Map<String, Object>>() {
      @Override
      public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> map = new HashMap<String, Object>();
        int cols = rs.getMetaData().getColumnCount();
        for (int i = 0; i < cols; i++) {
          String name = rs.getMetaData().getColumnName(i + 1);
          map.put(name, rs.getObject(i + 1));
        }
        return map;
      }
    });
  }

  /**
   * Danh sách DCLuong 20 tây (Báo cáo Bảo Hiểm ...)
   * @param month 1-12
   * @param year
   * @return
   */
  public List<Map<String, Object>> getDCLuong20TayList(String dkFact, String tableName, int month, int year) {
    String query = CauQuery.getDCluong20TayQuery(dkFact, tableName, month, year);
    return getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Map<String, Object>>() {
      @Override
      public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> map = new HashMap<String, Object>();
        int cols = rs.getMetaData().getColumnCount();
        for (int i = 0; i < cols; i++) {
          String name = rs.getMetaData().getColumnName(i + 1);
          map.put(name, rs.getObject(i + 1));
        }
        return map;
      }
    });
  }

  /**
   * Danh sách giam 20 tây : NSRa+NS-->RoRa+RoRa+Tang BSBHYT cho NS-RoRa/RoRa (neu co)  (Báo cáo Bảo Hiểm ...)
   * @param month 1-12
   * @param year
   * @return
   */
  public List<Map<String, Object>> getGiam20TayList(String dkFact, int month, int year) {
    String query = CauQuery.getGiam20TayQuery(dkFact, month, year);
    return getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Map<String, Object>>() {
      @Override
      public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> map = new HashMap<String, Object>();
        int cols = rs.getMetaData().getColumnCount();
        for (int i = 0; i < cols; i++) {
          String name = rs.getMetaData().getColumnName(i + 1);
          map.put(name, rs.getObject(i + 1));
        }
        return map;
      }
    });
  }

  /**
   * Danh sách giam trong tang moi   (Báo cáo Bảo Hiểm ...)
   * @param month 1-12
   * @param year
   * @return
   */
  public List<Map<String, Object>> getGiamTangMoiList(String dkFact, int month, int year) {
    String query = CauQuery.getGiamTrongTangMoiQuery(dkFact, month, year);
    return getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Map<String, Object>>() {
      @Override
      public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> map = new HashMap<String, Object>();
        int cols = rs.getMetaData().getColumnCount();
        for (int i = 0; i < cols; i++) {
          String name = rs.getMetaData().getColumnName(i + 1);
          map.put(name, rs.getObject(i + 1));
        }
        return map;
      }
    });
  }

  /**
   * Danh sách thay doi Thong Tin Tham Gia BHXH-TN-YT   (Báo cáo Bảo Hiểm ...)
   * @param month 1-12
   * @param year
   * @return
   */
  public List<Map<String, Object>> getThayDoiTTBHList(String dkFact, int month, int year) {
    String query = CauQuery.getThayDoiTTBHQuery(dkFact, month, year);
    return getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Map<String, Object>>() {
      @Override
      public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> map = new HashMap<String, Object>();
        int cols = rs.getMetaData().getColumnCount();
        for (int i = 0; i < cols; i++) {
          String name = rs.getMetaData().getColumnName(i + 1);
          map.put(name, rs.getObject(i + 1));
        }
        return map;
      }
    });
  }

  /**
   * Lấy danh sách n_social_infor_report có trạng thái NS-RoRa thang cuoi quy BH tru 1 (T5, T11)
   * @param month 1-12
   * @param year
   * @return
   */
  public List<String> getNSRoRaT5T11(int month, int year) {
    Date thangCuoiQuy = DateUtils.getThangCuoiQuyBHTru1(month, year);
    String sql =
        "select t.empsn from n_social_infor_report t where t.trangthai=-1 and t.ghichu_trangthai='NS-->RoRa' and t.thoigian=?";
    return getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<String>() {
      @Override
      public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString(1);
      }
    }, new Object[] { new java.sql.Date(thangCuoiQuy.getTime()) });
  }

  public Map<String, BigDecimal> getThamSo() {
    String sql = "select * from n_thamso";
    List<Map<String, Object>> list =
        getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<Map<String, Object>>() {
          @Override
          public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
            Map<String, Object> map = new HashMap<String, Object>();
            int cols = rs.getMetaData().getColumnCount();
            for (int i = 0; i < cols; i++) {
              String name = rs.getMetaData().getColumnName(i + 1);
              map.put(name, rs.getObject(i + 1));
            }
            return map;
          }
        });
    Map<String, BigDecimal> r = new HashMap<String, BigDecimal>();
    for (int i = 0; i < list.size(); i++) {
      Map<String, Object> map = list.get(i);
      if (map.get("GIATRI3") != null) {
        r.put((String) map.get("TENTHAMSO"), (BigDecimal) map.get("GIATRI3"));
      }
    }
    return r;
  }

  public BigDecimal getLuongBaoGiamBHYT(String empsn, Date date) {
    String sql = "select t.luonghd from n_social_infor_report t where t.empsn=? and t.trangthai=1\n"
        + "and t.thoigian=(select max(a.thoigian) from n_social_infor_report a where a.empsn=t.empsn\n"
        + "and a.trangthai=1 and a.thoigian<?)";
    try {
      return getSimpleJdbcTemplate().queryForObject(sql, BigDecimal.class, empsn, new java.sql.Date(date.getTime()));
    } catch (Exception e) {
      //e.printStackTrace();
      // if ke tu thoi diem tang moi toan la RoRa thi luong = luong cua thang tang moi
      // kiem tralai
      String sql1 = "select t.luonghd from n_social_infor_report t where t.empsn=?\n"
          + "and t.thoigian=(select max(a.thoigian) from n_social_infor_report a where a.empsn=t.empsn\n"
          + "and ghichu_trangthai like 'TangMoi%' and a.thoigian<?)";
      try {
        return getSimpleJdbcTemplate().queryForObject(sql1, BigDecimal.class, empsn, new java.sql.Date(date.getTime()));

      } catch (Exception e2) {
        // TODO: handle exception
        e.printStackTrace();
        return BigDecimal.ZERO;
      }
    }
  }

  public BigDecimal getMucLuongTran(String loaiBH, Date date) {
    String sql = "select T.MUCLUONGTRAN from n_mucluongtran t WHERE T.LOAIBAOHIEM=?"
        + " AND T.BEGIN_DATE<=? AND (T.END_DATE>=? OR T.END_DATE IS NULL )";

    try {
      return getSimpleJdbcTemplate().queryForObject(sql, BigDecimal.class, loaiBH, new java.sql.Date(date.getTime()),
          new java.sql.Date(date.getTime()));
    } catch (Exception e) {
      //e.printStackTrace();
      // TODO: handle exception
      e.printStackTrace();
      return BigDecimal.ZERO;
    }
  }

  // AAAAAAAAAAAAAAAA: danh cho thay doi thoi gian xet ngay cong mua BHXH-TN 21/m->20/m+1 la m+1

  public Date GetNgayGioiHanBC2120(String thoiGian) {
    // thoi gian : dd/mm/yyyy , luon la ngay 01
    Date ngayGioiHan = null;
    // VD thoi gian la 02/2013 thi den 20/02/2013 la ngay QT20, ngay gioi han bao cao
    Date thoiGianTemp;
    String ngay = null;
    String mm = null;

    InsuranceDAO ins = new InsuranceDAO();
    thoiGianTemp = ins.ToDate(thoiGian);

    Calendar tgTemp = Calendar.getInstance();
    tgTemp.setTime(thoiGianTemp);
    thoiGianTemp = tgTemp.getTime();

    // if thang bao cao la thang cuoi quy thi ngay QT 20 la ngay 12
    //thang=dd/mm/yyyy
    mm = thoiGian.substring(3, 5);
    if (mm == "06" || mm == "12") {
      //ngay="12";
      ngay = "15";// 10/06/2013, c Uyen
    } else
      ngay = "20";

    ngay = ngay + "/" + sf.format(thoiGianTemp).substring(3, 10);

    ngayGioiHan = ins.ToDate(ngay);
    return ngayGioiHan;
  }

  public int getThamGiaBHXHTN2120(String soThe, String thoiGian) {
    int kq = 1;
    //kq=0	: ko tham gia BH vi huu tri/ CNV moi chua ky HDLD
    //kq=-1	: co tham gia BH nhung ko du cong --> ko mua BHXH-TN
    //kq=1	: co tham gia BH va du cong --> mua BHXH-TN
    // thoigian : dd/mm/yyyy, ko nhat thiet la ngay 1 cua thang
    int dkLoaiTru = 0; // =0 la loai tru, =1 la ok
    int dkNgayCong = 0; // =0 la loai tru, =1 la ok

    InsuranceDAO ins = new InsuranceDAO();
    String fieldGet = "check_dk_muabhxh_tn('" + soThe + "',to_Date('" + thoiGian + "','" + "dd/mm/yyyy" + "'))";
    dkLoaiTru = Integer.parseInt(ins.GetField(fieldGet, "dual", "", "", "", "", "", ""));
    fieldGet = "a_getdk_ngaycong_2120('" + soThe + "',to_Date('" + thoiGian + "','" + "dd/mm/yyyy" + "'))";
    dkNgayCong = Integer.parseInt(ins.GetField(fieldGet, "dual", "", "", "", "", "", ""));

    if (dkLoaiTru == 0) {
      kq = 0;
    } else {
      if (dkNgayCong == 0) {
        kq = -1;
      } else {
        kq = 1;
      }
    }

    return kq;
  }

  public boolean Check_RoVaoTuNSRoRa2120(String soThe, String tuThang, String denThang, String ngayBaoGiam) {
    //tuThang = thang NSVao. denThang= thang bao giam
    boolean kq = true;
    String sql;
    InsuranceDAO ins = new InsuranceDAO();
    sql = "select f.empsn, max(f.thoigian) thoigian1 from n_social_infor_report f \n" + " where f.empsn='" + soThe + "'"
        + " and f.thoigian>=" + ins.get_todate(tuThang) + " \n" + " and f.thoigian < " + ins.get_todate(denThang)
        + " and f.trangthai=1\n" + " group by f.empsn ";

    //System.out.println(sql);

    Connection con = getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    int dem = 0;
    try {
      pstm = con.prepareStatement(sql);
      rs = pstm.executeQuery();
      if (rs.next()) {
        Date tgianRoVao = rs.getDate(2);
        Date ngay20RoVao = ins.GetNgayGioiHanBC2120(sf.format(rs.getDate(2)));
        if (ToDate(ngayBaoGiam).compareTo(ngay20RoVao) >= 0) {
          dem = 1;
        } else {
          dem = 0;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstm);
      DbUtils.close(con);
    }

    if (dem == 1) {
      kq = true;
    } else {
      kq = false;
    }

    return kq;
  }

  /**
   * Danh sách tăng 20 tây : NSVao+RoVao+DiLamLai+Thoai Thu BS BHYT cho DiLamLai va RoVao (neu co)(Báo cáo Bảo Hiểm ...)
   * @param month 1-12
   * @param year
   * @return
   */
  public List<Map<String, Object>> getTang20Tay2120List(String dkFact, int month, int year) {
    String query = CauQuery.getTang20Tay2120Query(dkFact, month, year);
    return getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Map<String, Object>>() {
      @Override
      public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> map = new HashMap<String, Object>();
        int cols = rs.getMetaData().getColumnCount();
        for (int i = 0; i < cols; i++) {
          String name = rs.getMetaData().getColumnName(i + 1);
          map.put(name, rs.getObject(i + 1));
        }
        return map;
      }
    });
  }

  /**
   * Lấy danh sách n_social_infor_report có trạng thái NS-RoRa thang cuoi quy truoc BH (T6, T12)
   * @param month 1-12
   * @param year
   * @return
   */
  public List<String> getNSRoRaT6T12(int month, int year) {
    Date thangCuoiQuy = DateUtils.getThangCuoiQuyTruocBH(month, year);
    String sql =
        "select t.empsn from n_social_infor_report t where t.trangthai=-1 and t.ghichu_trangthai='NS-->RoRa' and t.thoigian=?";
    return getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<String>() {
      @Override
      public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString(1);
      }
    }, new Object[] { new java.sql.Date(thangCuoiQuy.getTime()) });
  }

  /**
   * Danh sách giam 20 tây : NSRa+NS-->RoRa+RoRa+Tang BSBHYT cho NS-RoRa/RoRa (neu co)  (Báo cáo Bảo Hiểm ...)
   * @param month 1-12
   * @param year
   * @return
   */
  public List<Map<String, Object>> getGiam20Tay2120List(String dkFact, int month, int year) {
    String query = CauQuery.getGiam20Tay2120Query(dkFact, month, year);
    return getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Map<String, Object>>() {
      @Override
      public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> map = new HashMap<String, Object>();
        int cols = rs.getMetaData().getColumnCount();
        for (int i = 0; i < cols; i++) {
          String name = rs.getMetaData().getColumnName(i + 1);
          map.put(name, rs.getObject(i + 1));
        }
        return map;
      }
    });
  }

  // Thay doi cach lay bao cao tu 1 lan -> 2 lan
  // BD SD 09/2013
  //Thay doi bao cao 2 lan trong thang
  public Date GetNgayGioiHanBC2Lan(String thoiGian, String ngayBaoGiam) {
    // thoi gian : dd/mm/yyyy , luon la ngay 01
    //--chua ok
    Date ngayGioiHan = null;
    // VD thoi gian la 02/2013 thi den 20/02/2013 la ngay QT20, ngay gioi han bao cao
    //20/02 QT20 lan 1 cho T02/2013
    //12/03 QT20 lan 2 cho T02/2013
    // bao giam tu 13/02 ~ 20/02 dua theo QT20 lan 2 cua T01 ( QT luc 12/02/2013)
    // bao giam tu 21/02 ~ 12/03 dua theo QT20 lan 1 cua T02 (QT luc 20/02)
    // bao giam tu 13/03 ~ 20/03 dua theo QT20 lan 2 cua T02 (QT luc 12/03)
    // tuong tu cho nhung dot bao giam sau
    Date thoiGianTemp;
    Date ngayBGiam;
    //String ngay = null;
    String mm = null;
    String mmyy = null;

    // Ngay 20, 15 cua thang truyen vao, QT20 lan 1 cua thang truyen vao/ or thang gia han the
    mmyy = thoiGian.substring(3, 10);
    Date ngay20 = ToDate("20/" + mmyy);
    Date ngay15 = ToDate("15/" + mmyy);

    ngayBGiam = ToDate(ngayBaoGiam);
    InsuranceDAO ins = new InsuranceDAO();
    thoiGianTemp = ins.ToDate(thoiGian);

    Calendar tgTemp = Calendar.getInstance();
    tgTemp.setTime(thoiGianTemp);
    // + 1 thang
    tgTemp.add(tgTemp.MONTH, 1);
    thoiGianTemp = tgTemp.getTime();

    //Ngay 12 cua thang truyen vao+1, ngay QT20 lan 2 cua thang truyen vao
    mmyy = sf.format(thoiGianTemp).substring(3, 10);
    Date ngay12 = ToDate("12/" + mmyy);

    // if thang bao cao la thang cuoi quy thi ngay QT 20 la ngay 12
    //thang=dd/mm/yyyy, doi bo sung sau
    mm = thoiGian.substring(3, 5);
    if (mm == "06" || mm == "12") {
      //ngay="12";
      ngayGioiHan = ngay15;// 10/06/2013, c Uyen
    } else {
      ngayGioiHan = ngayBGiam.compareTo(ngay12) <= 0 ? ngay20 : ngay12;
    }

    //ngay=ngay+"/"+sf.format(thoiGianTemp).substring(3, 10);
    //ngayGioiHan= ins.ToDate(ngay);		
    return ngayGioiHan;
  }

  // Thay doi cach lay bao cao tu 1 lan -> 2 lan
  // BD SD 09/2013
  //Thay doi bao cao 2 lan trong thang
  public Date[] Get2NgayGioiHanBC2Lan(String thoiGian) {
    // thoi gian : dd/mm/yyyy , luon la ngay 01
    //--chua ok
    Date ngayGioiHan1 = null; // ngay bao cao lan 1
    Date ngayGioiHan2 = null; // ngay bao cao lan 2
    // VD thoi gian la 02/2013 thi den 20/02/2013 la ngay QT20, ngay gioi han bao cao
    //20/02 QT20 lan 1 cho T02/2013
    //12/03 QT20 lan 2 cho T02/2013
    // bao giam tu 13/02 ~ 20/02 dua theo QT20 lan 2 cua T01 ( QT luc 12/02/2013)
    // bao giam tu 21/02 ~ 12/03 dua theo QT20 lan 1 cua T02 (QT luc 20/02)
    // bao giam tu 13/03 ~ 20/03 dua theo QT20 lan 2 cua T02 (QT luc 12/03)
    // tuong tu cho nhung dot bao giam sau
    Date thoiGianTemp;
    Date ngayBGiam;
    //String ngay = null;
    String mm = null;
    String mmyy = null;

    // Ngay 20, 15 cua thang truyen vao, QT20 lan 1 cua thang truyen vao/ or thang gia han the
    mmyy = thoiGian.substring(3, 10);
    Date ngay20 = ToDate("20/" + mmyy);
    Date ngay15 = ToDate("15/" + mmyy);
    InsuranceDAO ins = new InsuranceDAO();
    thoiGianTemp = ins.ToDate(thoiGian);

    Calendar tgTemp = Calendar.getInstance();
    tgTemp.setTime(thoiGianTemp);
    // + 1 thang
    tgTemp.add(tgTemp.MONTH, 1);
    thoiGianTemp = tgTemp.getTime();

    //Ngay 12 cua thang truyen vao+1, ngay QT20 lan 2 cua thang truyen vao
    mmyy = sf.format(thoiGianTemp).substring(3, 10);
    Date ngay12 = ToDate("12/" + mmyy);

    // if thang bao cao la thang cuoi quy thi ngay QT 20 la ngay 12
    //thang=dd/mm/yyyy, doi bo sung sau
    mm = thoiGian.substring(3, 5);
    if (mm == "06" || mm == "12") {
      //ngay="12";
      ngayGioiHan1 = ngay15;// 10/06/2013, c Uyen			
    } else {
      ngayGioiHan1 = ngay20;
    }
    ngayGioiHan2 = ngay12;
    return new Date[] { ngayGioiHan1, ngayGioiHan2 };
  }

  public int getThamGiaBHXHTN2Lan(String soThe, String thoiGian, String ngayBaoGiam) {
    int kq = 1;
    // xet ngay cong lan 1 chi xet den ngay 16
    //kq=0	: ko tham gia BH vi huu tri/ CNV moi chua ky HDLD
    //kq=-1	: co tham gia BH nhung ko du cong --> ko mua BHXH-TN
    //kq=1	: co tham gia BH va du cong --> mua BHXH-TN
    // thoigian : dd/mm/yyyy, ko nhat thiet la ngay 1 cua thang
    int dkLoaiTru = 0; // =0 la loai tru, =1 la ok
    int dkNgayCong = 0; // =0 la loai tru, =1 la ok

    int lanBC;
    // VD thoi gian la 02/2013 thi den 20/02/2013 la ngay QT20, ngay gioi han bao cao
    //20/02 QT20 lan 1 cho T02/2013
    //12/03 QT20 lan 2 cho T02/2013
    // bao giam tu 13/02 ~ 20/02 dua theo QT20 lan 2 cua T01 ( QT luc 12/02/2013)
    // bao giam tu 21/02 ~ 12/03 dua theo QT20 lan 1 cua T02 (QT luc 20/02)
    // bao giam tu 13/03 ~ 20/03 dua theo QT20 lan 2 cua T02 (QT luc 12/03)
    // tuong tu cho nhung dot bao giam sau
    Date thoiGianTemp;
    Date ngayBGiam;
    //String ngay = null;
    String mm = null;
    String mmyy = null;

    // Ngay 20, Ngay15 QT20 lan 1 cua thang truyen vao
    mmyy = thoiGian.substring(3, 10);
    Date ngay20 = ToDate("20/" + mmyy);

    ngayBGiam = ToDate(ngayBaoGiam);
    InsuranceDAO ins = new InsuranceDAO();
    thoiGianTemp = ins.ToDate(thoiGian);

    Calendar tgTemp = Calendar.getInstance();
    tgTemp.setTime(thoiGianTemp);
    // + 1 thang
    tgTemp.add(tgTemp.MONTH, 1);
    thoiGianTemp = tgTemp.getTime();

    //Ngay 12 cua thang truyen vao+1, ngay QT20 lan 2 cua thang truyen vao
    mmyy = sf.format(thoiGianTemp).substring(3, 10);
    Date ngay12 = ToDate("12/" + mmyy);

    String ngay1 = "01/" + thoiGian.substring(3, 10);
    String ngayLan1 = GetField("GIATRITHAMSO", "N_THAMSO", "TENTHAMSO", "", "", "NCBH1", "", "");
    ngayLan1 = ngayLan1 + "/" + thoiGian.substring(3, 10);

    String fieldGet = "check_dk_muabhxh_tn('" + soThe + "',to_Date('" + thoiGian + "','" + "dd/mm/yyyy" + "'))";
    dkLoaiTru = Integer.parseInt(ins.GetField(fieldGet, "dual", "", "", "", "", "", ""));
    //fieldGet ="a_getdk_ngaycong('"+soThe+"',to_Date('"+thoiGian+"','"+"dd/mm/yyyy"+"'))";

    // if thang bao cao la thang cuoi quy thi ngay QT 20 la ngay 12
    //thang=dd/mm/yyyy, doi bo sung sau
    mm = thoiGian.substring(3, 5);
    if (mm == "06" || mm == "12") {
      //ngay="12";
      lanBC = 1;
    } else {
      lanBC = ngayBGiam.compareTo(ngay12) <= 0 ? 1 : 2;
    }

    if (lanBC == 1) {
      fieldGet = "a_getdk_ngaycong_d1d2_1('" + soThe + "'," + get_todate(thoiGian) + "," + get_todate(ngay1) + ","
          + get_todate(ngayLan1) + ")";
    } else {
      fieldGet = "a_getdk_ngaycong('" + soThe + "'," + get_todate(thoiGian) + ")";
    }

    dkNgayCong = Integer.parseInt(ins.GetField(fieldGet, "dual", "", "", "", "", "", ""));

    if (dkLoaiTru == 0) {
      kq = 0;
    } else {
      if (dkNgayCong == 0) {
        kq = -1;
      } else {
        kq = 1;
      }
    }

    return kq;
  }

  public boolean Check_NSRoRa_RoRa_CuoiQuy(String soThe, String thoiGian) {
    // lay ds nhung nguoi ma den thang cuoi quy la %RoRa\
    // chua xet RoRa, van chi xet NS-RORa ma thoi hzaiiii
    boolean kq = true;
    String sql;
    sql = "select count(f.empsn) dem from n_social_infor_report f \n" + " where f.empsn='" + soThe + "'"
        + " and f.thoigian=to_Date('" + thoiGian + "'" + ",'" + "dd/mm/yyyy" + "') \n"
        + " and f.trangthai=-1 and f.ghichu_trangthai in ('" + "NS-->RoRa" + "')";
    //System.out.println(sql);

    Connection con = getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    int dem = 0;
    try {
      pstm = con.prepareStatement(sql);
      rs = pstm.executeQuery();
      if (rs.next()) {
        dem = rs.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstm);
      DbUtils.close(con);
    }

    if (dem > 0) {
      kq = true;
    } else {
      kq = false;
    }

    return kq;
  }

  public boolean Check_RoVaoTuNSRoRa_2Lan(String soThe, String tuThang, String denThang, String ngayBaoGiam) {
    //tuThang = thang NSVao. denThang= thang bao giam
    // true la da RoVao, false : NS->RoRa/ RoRa chua RoVao
    boolean kq = true;
    String sql;
    InsuranceDAO ins = new InsuranceDAO();
    sql = "select f.empsn, max(f.thoigian) thoigian1 from n_social_infor_report f \n" + " where f.empsn='" + soThe + "'"
        + " and f.thoigian>=" + ins.get_todate(tuThang) + " \n" + " and f.thoigian < " + ins.get_todate(denThang)
        + " and f.trangthai=1\n" + " group by f.empsn ";

    //System.out.println(sql);

    Connection con = getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    int dem = 0;
    try {
      pstm = con.prepareStatement(sql);
      rs = pstm.executeQuery();
      if (rs.next()) {
        Date tgianRoVao = rs.getDate(2);
        Date ngay20RoVao = ins.GetNgayGioiHanBC2Lan(sf.format(rs.getDate(2)), ngayBaoGiam);
        if (ToDate(ngayBaoGiam).compareTo(ngay20RoVao) >= 0) {
          dem = 1;
        } else {
          dem = 0;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstm);
      DbUtils.close(con);
    }

    if (dem == 1) {
      kq = true;
    } else {
      kq = false;
    }

    return kq;
  }

  public String[] TTThamGiaBHGanNhat_2Lan(String soThe, String thoiGian) {
    String att = null;
    String attQuit = null;
    String yy = null;
    String mm = null;
    String temp = null;
    String thangGanNhatBH = null;
    String luongGanNhatBH = null;
    int bhxh = 0;
    InsuranceDAO ins = new InsuranceDAO();
    Date thangGanNhat = ins.ToDate(thoiGian);
    Date ngayKyHD = null;
    Date ngay15HD = null; // ngay 15 cua thang ky HDLD lan 1
    Date thangTangMoi = null;
    boolean exit_att = true;
    boolean exit_attEmpsn = true;
    boolean exit_attQ = true;
    boolean exit_attQEmpsn = true;

    //temp= ins.GetField("date_s", "n_labour", "empsn", "times", "", soThe, "1", "");
    temp = ins.GetField("to_char(date_s,'" + "dd/mm/yyyy" + "')", "n_labour", "empsn", "times", "", soThe, "1", "");
    ngay15HD = ins.ToDate("15/" + temp.substring(3, 10));
    ngayKyHD = ins.ToDate(temp);
    yy = thoiGian.substring(6, 10);
    mm = thoiGian.substring(3, 5);
    att = "ATT" + yy + mm;
    attQuit = "ATTQUIT" + yy + mm;

    exit_att = ins.Check_exit("dba_tables", "table_name", "", att, "");
    if (exit_att) {
      exit_attEmpsn = ins.Check_exit(att, "empsn", "", soThe, "");
    } else {
      exit_attEmpsn = false;
    }
    exit_attQ = ins.Check_exit("dba_tables", "table_name", "", attQuit, "");
    if (exit_attQ) {
      exit_attQEmpsn = ins.Check_exit(attQuit, "empsn", "", soThe, "");
    } else {
      exit_attQEmpsn = false;
    }

    if (exit_att == true && exit_attEmpsn == true) {
      temp = ins.GetField("joininsu", att, "empsn", "", "", soThe, "", "");
      yy = att.substring(3, 7);
      mm = att.substring(7, 9);
      if (temp == null) {
        bhxh = 0;
      } else {
        bhxh = Integer.parseInt(temp);
      }
      thangGanNhatBH = "01/" + mm + "/" + yy;
      thangGanNhat = ins.ToDate(thangGanNhatBH);
      temp = ins.GetField("combsaly", att, "empsn", "", "", soThe, "", "");
      if (temp == null) {
        luongGanNhatBH = "0";
      } else {
        luongGanNhatBH = temp;
      }
    } else if (exit_attQ == true && exit_attQEmpsn == true) {
      temp = ins.GetField("joininsu", attQuit, "empsn", "", "", soThe, "", "");
      yy = att.substring(3, 7);
      mm = att.substring(7, 9);
      if (temp == null) {
        bhxh = 0;
      } else {
        bhxh = Integer.parseInt(temp);
      }
      thangGanNhatBH = "01/" + mm + "/" + yy;
      thangGanNhat = ins.ToDate(thangGanNhatBH);
      temp = ins.GetField("combsaly", attQuit, "empsn", "", "", soThe, "", "");
      if (temp == null) {
        luongGanNhatBH = "0";
      } else {
        luongGanNhatBH = temp;
      }
    } else {
      bhxh = 0;
      thangGanNhat = null;
      thangGanNhatBH = null;
    }

    if (ngayKyHD.compareTo(ngay15HD) == 0) {
      //thangTangMoi = 
      int a = ngay15HD.getMonth() + 1;
      ngayKyHD.setMonth(a);
      //System.out.println(sf.format(ngay15HD));
    }
    while (bhxh <= 0) {
      int b = thangGanNhat.getMonth() - 1;
      thangGanNhat.setMonth(b);
      yy = sf.format(thangGanNhat).substring(6, 10);
      mm = sf.format(thangGanNhat).substring(3, 5);
      att = "ATT" + yy + mm;
      temp = ins.GetField("joininsu", att, "empsn", "", "", soThe, "", "");
      if (temp == null) {
        bhxh = 0;
      } else {
        bhxh = Integer.parseInt(temp);
      }
      thangGanNhatBH = "01/" + mm + "/" + yy;
      thangGanNhat = ins.ToDate(thangGanNhatBH);
      temp = ins.GetField("combsaly", att, "empsn", "", "", soThe, "", "");
      if (temp == null) {
        luongGanNhatBH = "0";
      } else {
        luongGanNhatBH = temp;
      }

      if (bhxh == 0 && thangGanNhat.compareTo(ngayKyHD) <= 0) {
        // if lay den thang bao tang moi ma BHXH van =0 thi lay bang thang tang moi va dung lai
        return new String[] { thangGanNhatBH, luongGanNhatBH };
      }
    }

    return new String[] { thangGanNhatBH, luongGanNhatBH };

  }

  // end bao cao 2 lan trong thang
  /**
   * Danh sách QT 20 tây : Bao gom : 
   * + Giam 	:	NSRa/ NS-->RoRa / RoRa/ DCLuong giam/ Thoai thu BHYT do Rovao
   * + Tang	:	NSVao/ RoVao/ Di Lam Lai/ DCLuong tang/ Tang BSBHYT cho NS-RoRa/RoRa (neu co)	 * 
   * @param month 1-12
   * @param year
   * @return
   */
  public List<Map<String, Object>> getQT20TayList(String dkFact, String tableName, int month, int year, int lanBC) {
    String query = CauQuery.getQT20TayQuery(dkFact, tableName, month, year, lanBC);
    return getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Map<String, Object>>() {
      @Override
      public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> map = new HashMap<String, Object>();
        int cols = rs.getMetaData().getColumnCount();
        for (int i = 0; i < cols; i++) {
          String name = rs.getMetaData().getColumnName(i + 1);
          map.put(name, rs.getObject(i + 1));
        }
        return map;
      }
    });
  }

  public void ganExcelPhatSinhBH(int cellBD, int XHTN, HSSFRow row, HSSFCell cell, BigDecimal chenhLech, double tyLe) {
    /*		if XHTN=1 tuc la co ty le BHXH-TN ->se co so lieu ben quy luong hien hanh (HH), dieu chinh (DC) so phai dong
    		else chi co ty le BHYT thi chi co so lieu ben dieu chinh so phai dong, quy luong hien hanh all =0		
     		if muc chenh lech (luong muc moi- luong muc cu)> 0 thi se gan so lieu ben hang muc tang
    		else se gan so lieu ben hang muc giam
    */
    //System.out.println(Math.abs(chenhLech.doubleValue()));

    if (XHTN == 1) {
      if (chenhLech.compareTo(BigDecimal.valueOf(0)) > 0) {
        cell = row.getCell(cellBD);
        HSSFUtils.fill(cell, chenhLech.doubleValue());
        cell = row.getCell(cellBD + 1);
        HSSFUtils.fill(cell, chenhLech.doubleValue() * tyLe);
        cell = row.getCell(cellBD + 2);
        HSSFUtils.fill(cell, 0);
        cell = row.getCell(cellBD + 3);
        HSSFUtils.fill(cell, 0);
      } else {
        //luc nay chenh lech <0
        chenhLech = BigDecimal.valueOf(Math.abs(chenhLech.doubleValue()));
        cell = row.getCell(cellBD);
        HSSFUtils.fill(cell, 0);
        cell = row.getCell(cellBD + 1);
        HSSFUtils.fill(cell, 0);
        cell = row.getCell(cellBD + 2);
        HSSFUtils.fill(cell, chenhLech);// chuyen ve so duong ( *-1)
        cell = row.getCell(cellBD + 3);
        HSSFUtils.fill(cell, chenhLech.doubleValue() * tyLe);
      }
    } else {
      cell = row.getCell(cellBD);
      HSSFUtils.fill(cell, 0);
      cell = row.getCell(cellBD + 1);
      HSSFUtils.fill(cell, 0);
      cell = row.getCell(cellBD + 2);
      HSSFUtils.fill(cell, 0);
      cell = row.getCell(cellBD + 3);
      HSSFUtils.fill(cell, 0);
    }
  }

  //Bao Ro tung thang
  public void ganExcel(int stt, int rowCopy, Date thangBC, Date thangHH, String BSBHYT, Map<String, BigDecimal> thamso,
      Map<String, Object> data, HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, HSSFCell cell) {
    //ThangBC: thang bao cao, stt phat sinh, rowCopy : dong se gan data, BSBHYT: =BHYT la chi co ty le BHYT ma thoi
    InsuranceDAO ins = new InsuranceDAO();
    String loaiDS = (String) data.get("DCDLOAT");
    String ghiChuDS = (String) data.get("NOTE_TANG");
    String ghiChuBSBHYT;
    String emp = (String) data.get("EMPSN");
    String tuThang = data.get("MONTH_BC").toString(); // mm/yyyy
    // luong cua thang gan nhat co tham gia BHXH-TN
    BigDecimal luong = ins.getLuongBaoGiamBHYT(emp, thangBC);
    BigDecimal luongTranXHTN = ins.getMucLuongTran("BHXHTN", thangBC);
    BigDecimal luongTranYT = ins.getMucLuongTran("BHYT", thangBC);
    BigDecimal luongMucCu;
    BigDecimal luongMucMoi;
    double tyleDong = 0;
    double tyleBHTN = 0;

    Date fromMonth = DateUtils.getFirstDay(tuThang.substring(0, 2), tuThang.substring(3, 7));
    Date toMonth = null;
    int ttBHXHTN;
    //row = sheet.getRow(rowCopy);
    cell = row.getCell(0);// STT
    HSSFUtils.fill(cell, stt);
    cell = row.getCell(1);// Ho va ten
    HSSFUtils.fill(cell, data.get("FNAME") + " " + data.get("LNAME"));
    cell = row.getCell(2);// Ma so BHXH
    HSSFUtils.fill(cell, data.get("ID_SOCIAL"));
    cell = row.getCell(3);// Ngay sinh
    HSSFUtils.fill(cell, data.get("BIRTHDAY"));
    cell = row.getCell(4);// Gioi tinh
    if ("NU".equals(data.get("SEX")))
      HSSFUtils.fill(cell, "X");
    else
      HSSFUtils.fill(cell, "");
    cell = row.getCell(16);// Tang tu thang
    HSSFUtils.fill(cell, data.get("MONTH_BC"));
    cell = row.getCell(20);// Ghi chu
    HSSFUtils.fill(cell, ghiChuDS);

    // bat dau khac	
    // if la DCLuong, luong old va luong new deu # 0
    if (loaiDS.equals("DCLuong") == true) {
      cell = row.getCell(6);// Luong old
      if (data.get("DCDLOAT").equals("DCBT") == true) {
        // luong old = luong cua thang gan nhat co tham gia BHXH-TN			
        HSSFUtils.fill(cell, luong.doubleValue());
        luongMucCu = luong;
      } else {
        HSSFUtils.fill(cell, data.get("SAL_OLD"));
        luongMucCu = (BigDecimal) data.get("SAL_OLD");
      }
      cell = row.getCell(11);// Luong new
      HSSFUtils.fill(cell, data.get("BSALARY"));
      luongMucMoi = (BigDecimal) data.get("BSALARY");
    }
    //If la Tang ld, tang dc: luong muc cu =0, luong muc moi = luong hien tai
    //If la tang BS BHYT, luong muc old =0, luong muc moi = luong gan nhat co BHXH-TN 
    else if (loaiDS.equals("Tang") == true) {
      cell = row.getCell(6);// Luong old	
      HSSFUtils.fill(cell, 0);
      luongMucCu = BigDecimal.valueOf(0);
      cell = row.getCell(11);// Luong new
      HSSFUtils.fill(cell, data.get("BSALARY"));
      luongMucMoi = (BigDecimal) (data.get("BSALARY"));
      //if Tang1thang thi den thang= tu thang, else den thang= null
      if (ghiChuDS.equals("Tang1Thang") == true) {
        cell = row.getCell(17);// den thang
        // tuThang=denThang
        toMonth = DateUtils.getFirstDay(tuThang.substring(0, 2), tuThang.substring(3, 7));
        HSSFUtils.fill(cell, toMonth);
      } else {
        //if la Tang do di lam lai/ NS-->RoRa cuoi quy truoc den khi RoVao thi se bao thoai thu BHYT
        // thoai thu tu thang Tang lai -> cuoi quy, luong = luong gan nhat
        if (BSBHYT.equals("BHYT")) {
          ghiChuBSBHYT = (String) ("Thoái thu BSBHYT do " + ghiChuDS);
          cell = row.getCell(20);// Ghi chu
          HSSFUtils.fill(cell, ghiChuBSBHYT);
          toMonth = DateUtils.getThangCuoiQuyHT(Integer.parseInt(tuThang.substring(0, 2)),
              Integer.parseInt(tuThang.substring(3, 7)));

          cell = row.getCell(17);// den thang
          //cell.setCellValue(denThang);
          HSSFUtils.fill(cell, (sf.format(toMonth)).substring(3, 10));

          // if la thoai thu BS BHYT thi luong cu <>0,= luong gan nhat, luong moi =0
          cell = row.getCell(6);// Luong old	
          HSSFUtils.fill(cell, luong.doubleValue());
          luongMucCu = luong;
          cell = row.getCell(11);// Luong new
          HSSFUtils.fill(cell, 0);
          luongMucMoi = BigDecimal.valueOf(0);

        }
        // else la Tang binh thuong NSVao, RoVao,v,vvv thi ko co xet thoai thu
      }
    }

    //If la Giam: luong muc moi = 0		
    else if (loaiDS.equals("Giam") == true) {
      //if la NS-->RoRa thi luong muc cu =0 else =luong gan nhat.
      if (ghiChuDS.equals("NS-->RoRa")) {
        cell = row.getCell(6);// Luong old	
        HSSFUtils.fill(cell, 0);
        luongMucCu = BigDecimal.valueOf(0);
      } else {
        cell = row.getCell(6);// Luong old	
        HSSFUtils.fill(cell, luong.doubleValue());
        luongMucCu = luong;
      }

      cell = row.getCell(11);// Luong new
      HSSFUtils.fill(cell, 0);
      luongMucMoi = BigDecimal.valueOf(0);

      // if NSRa thi den thang= null, else den thang= tu thang
      // da xet bao ham voi Giam ma co tang BS BHYT
      // if la Giam %RoRa thi se co tang BS BHYT, luong tang = luong gan nhat
      if (ghiChuDS.equals("NSRa") == false) {
        cell = row.getCell(17);// den thang
        // tuThang=denThang
        toMonth = DateUtils.getFirstDay(tuThang.substring(0, 2), tuThang.substring(3, 7));
        HSSFUtils.fill(cell, toMonth);
      }

      //if giam do %RoRa thi se bao tang BHYT 1 thang giam, luong = luong gan nhat
      if (BSBHYT.equals("BHYT")) {
        // if la Tang BS BHYT thi luong cu =0, luong moi <>0, = luong gan nhat
        cell = row.getCell(6);// Luong old	
        HSSFUtils.fill(cell, 0);
        luongMucCu = BigDecimal.valueOf(0);
        cell = row.getCell(11);// Luong new
        HSSFUtils.fill(cell, luong.doubleValue());
        luongMucMoi = luong;

        ghiChuBSBHYT = (String) ("Tăng BSBHYT do " + ghiChuDS);
        cell = row.getCell(20);// Ghi chu
        HSSFUtils.fill(cell, ghiChuBSBHYT);
        // tuThang=denThang
        toMonth = DateUtils.getFirstDay(tuThang.substring(0, 2), tuThang.substring(3, 7));
        cell = row.getCell(17);// den thang
        HSSFUtils.fill(cell, toMonth);
      }
    }

    //if la DC : Den thang = null, luong cu= luong gan nhat, luong moi = luong hien tai
    else {
      cell = row.getCell(6);// Luong old	
      HSSFUtils.fill(cell, luong.doubleValue());
      luongMucCu = luong;
      cell = row.getCell(11);// Luong new
      HSSFUtils.fill(cell, data.get("BSALARY"));
      luongMucMoi = (BigDecimal) data.get("BSALARY");
    }

    if (!BSBHYT.equals("BHYT")) {
      ttBHXHTN = 1;
      cell = row.getCell(22);// Ti le BHXH
      HSSFUtils.fill(cell, thamso.get("TYLEBHXH"));
      cell = row.getCell(24);// Ti le BHTN
      HSSFUtils.fill(cell, thamso.get("TYLEBHTN"));
    } else
      ttBHXHTN = 0;

    // het khac

    cell = row.getCell(23);// Ti le BHYT
    HSSFUtils.fill(cell, thamso.get("TYLEBHYT"));
    cell = row.createCell(48);// So the
    HSSFUtils.fill(cell, emp);
    cell = row.createCell(49);// Xuong
    HSSFUtils.fill(cell, data.get("NAME_GROUP"));
    cell = row.createCell(50);// Don vi
    HSSFUtils.fill(cell, data.get("NAME_DEPT_NAME"));
    // eval formula
    cell = row.getCell(18);
    HSSFUtils.evalFormula(wb, cell);
    // cong thuc mau cua bao hiem, 
    /*		for (int k = 25; k < 48; k++) {
    			cell = row.getCell(k);
    			HSSFUtils.evalFormula(wb, cell);
    		}*/

    // ko lay theo cong thuc cua bao hiem nua hzaiiiiiiiiii		
    // lam ko duoc vi cong thuc sum bao cao cuoi ky ko tu hieu
    luongMucCu = luongMucCu.compareTo(luongTranXHTN) >= 0 ? luongTranXHTN : luongMucCu;
    luongMucMoi = luongMucMoi.compareTo(luongTranXHTN) >= 0 ? luongTranXHTN : luongMucMoi;
    BigDecimal chenhLech = luongMucMoi.subtract(luongMucCu);

    //if XHTN=1 tuc la co ty le BHXH-TN ->se co so lieu ben quy luong hien hanh (HH), dieu chinh (DC) so phai dong
    //else chi co ty le BHYT thi chi co so lieu ben dieu chinh so phai dong, quy luong hien hanh all =0

    // if chenh lech >0 thi thong tin se dua vao hang muc tang, else la hang muc giam
    // xet hang muc quy luong HH		
    if (ttBHXHTN == 1) {
      //25,26,27,28 : BHXH
      ins.ganExcelPhatSinhBH(25, 1, row, cell, chenhLech, tyLeDongBHXH);
      // 29,30,31,32 : BHYT
      // if NSRa / DCGiam thi mac du co ty le XH-TN nhung ben HH thi YT se bang 0
      if (ghiChuDS.equals("NSRa") || ghiChuDS.equals("DCGiam"))
        ins.ganExcelPhatSinhBH(29, 0, row, cell, chenhLech, tyLeDongBHYT);
      else
        ins.ganExcelPhatSinhBH(29, 1, row, cell, chenhLech, tyLeDongBHYT);
      // 33,34,35,36 : BHTN
      ins.ganExcelPhatSinhBH(33, 1, row, cell, chenhLech, tyLeDongBHTN);
      //gan ty le dong va ty le BHTN de so sanh phan tang va giam so LD BHXH,YT,TN phia sau cung
      tyleDong = tyLeDongBHXH + tyLeDongBHTN + tyLeDongBHYT;
      tyleBHTN = tyLeDongBHTN;
    } else {
      //Quy HH all deu =0
      //25,26,27,28 : BHXH
      ins.ganExcelPhatSinhBH(25, 0, row, cell, chenhLech, tyLeDongBHXH);
      // 29,30,31,32 : BHYT
      ins.ganExcelPhatSinhBH(29, 0, row, cell, chenhLech, tyLeDongBHYT);
      // 33,34,35,36 : BHTN
      ins.ganExcelPhatSinhBH(33, 0, row, cell, chenhLech, tyLeDongBHTN);

      //gan ty le dong va ty le BHTN de so sanh phan tang va giam so LD BHXH,YT,TN phia sau cung
      tyleDong = tyLeDongBHYT;
      tyleBHTN = 0;
    }

    // xet hang muc DC so phai dong		
    toMonth = toMonth != null ? toMonth : thangHH;
    double soNgay = toMonth.getTime() - fromMonth.getTime();
    soNgay = Math.floor((soNgay / 1000 / 60 / 60 / 24));
    double soThang = Math.floor((soNgay / 365) * 12);
    /*neu tyleDong=4.5% thi// chi co BHYT
    * 	 sothang=sothang+1
    * else
    * 		neu DenThang< TuThang thi soThang =0
    * 		else 
    * 			neu denthang=thangHH thi sothang=sothang
    * 			else // den thang > thangHH
    * 				sothang = sothang+1
    * */

    if (tyleDong == tyLeDongBHYT)
      soThang = soThang + 1;
    else if (toMonth.compareTo(fromMonth) < 0)
      soThang = 0;
    else
      soThang = toMonth.compareTo(thangHH) == 0 ? soThang : soThang + 1;

    cell = row.getCell(37);
    HSSFUtils.fill(cell, soThang);

    // if so tien DC > thi se dua gia tri vao cot tang, else la giam
    double soDC = (luongMucMoi.doubleValue() - luongMucCu.doubleValue());
    // if tyleDong= tyleDongBHYT thi BHXH-TN =0, else se co gia tri cua BHXH-TN-YT
    double dcBHXH = 0;
    double dcBHTN = 0;
    double dcBHYT = 0;
    if (tyleDong == tyLeDongBHYT) {
      dcBHXH = 0;
      dcBHTN = 0;
    } else {
      dcBHXH = soDC * soThang * tyLeDongBHXH;
      dcBHTN = soDC * soThang * tyLeDongBHTN;
    }
    dcBHYT = soDC * soThang * tyLeDongBHYT;
    // if soDC>0 thi se dua so lieu vao cot tang
    if (soDC > 0) {
      //38,39 : BHXH
      cell = row.getCell(38);
      HSSFUtils.fill(cell, (dcBHXH));
      cell = row.getCell(39);
      HSSFUtils.fill(cell, 0);
      //40,41 : BHYT
      cell = row.getCell(40);
      HSSFUtils.fill(cell, (dcBHYT));
      cell = row.getCell(41);
      HSSFUtils.fill(cell, 0);
      //42,43 : BHTN
      cell = row.getCell(42);
      HSSFUtils.fill(cell, (dcBHTN));
      cell = row.getCell(43);
      HSSFUtils.fill(cell, 0);
    }
    //if soDC<0 thi se dua so lieu vao cot giam
    else {
      //38,39 : BHXH
      //luc nay soDC <0
      soDC = Math.abs(soDC);
      cell = row.getCell(38);
      HSSFUtils.fill(cell, 0);
      cell = row.getCell(39);
      HSSFUtils.fill(cell, (dcBHXH));
      //40,41 : BHYT
      cell = row.getCell(40);
      HSSFUtils.fill(cell, 0);
      cell = row.getCell(41);
      HSSFUtils.fill(cell, (dcBHYT));
      //42,43 : BHTN
      cell = row.getCell(42);
      HSSFUtils.fill(cell, 0);
      cell = row.getCell(43);
      HSSFUtils.fill(cell, (dcBHTN));
    }
    //System.out.println(ghiChuDS);

    // dem so luong tang/ giam LD BHXH,YT/TN
    int tang = 0;
    int giam = 0;
    // if luong moi>0 & luong old =0 & tyleDong>=tyle BHXH+YT & DenThang=ThangHienTai
    if ((luongMucMoi.compareTo(BigDecimal.valueOf(0)) == 1) && (luongMucCu.compareTo(BigDecimal.valueOf(0)) == 0)
        && (tyleDong >= tyLeDongBHXH + tyLeDongBHYT) && (toMonth.compareTo(thangHH) == 0)) {
      tang = 1;
      giam = 0;
    } else if ((luongMucMoi.compareTo(BigDecimal.valueOf(0)) == 0) && (luongMucCu.compareTo(BigDecimal.valueOf(0)) == 1)
        && (tyleDong >= tyLeDongBHXH + tyLeDongBHYT) && (toMonth.compareTo(thangHH) == 0)) {
      tang = 0;
      giam = 1;
    }
    cell = row.getCell(44);
    HSSFUtils.fill(cell, tang);
    cell = row.getCell(45);
    HSSFUtils.fill(cell, giam);

    // if luong moi>0 & luong old =0 & tyleDong>=tyle BHXH+YT & DenThang=ThangHienTai
    if ((luongMucMoi.compareTo(BigDecimal.valueOf(0)) == 1) && (luongMucCu.compareTo(BigDecimal.valueOf(0)) == 0)
        && (tyleBHTN >= tyLeDongBHTN) && (toMonth.compareTo(thangHH) == 0)) {
      tang = 1;
      giam = 0;
    } else if ((luongMucMoi.compareTo(BigDecimal.valueOf(0)) == 0) && (luongMucCu.compareTo(BigDecimal.valueOf(0)) == 1)
        && (tyleBHTN >= tyLeDongBHTN) && (toMonth.compareTo(thangHH) == 0)) {
      tang = 0;
      giam = 1;
    }
    cell = row.getCell(46);
    HSSFUtils.fill(cell, tang);
    cell = row.getCell(47);
    HSSFUtils.fill(cell, giam);

  }

  //Bao Ro tu thang	
  public void ganExcelTuThang(int stt, int rowCopy, Date thangBC, Date thangHH, String BSBHYT,
      Map<String, BigDecimal> thamso, Map<String, Object> data, HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row,
      HSSFCell cell) {
    //ThangBC: thang bao cao, stt phat sinh, rowCopy : dong se gan data, BSBHYT: =BHYT la chi co ty le BHYT ma thoi
    InsuranceDAO ins = new InsuranceDAO();
    String loaiDS = (String) data.get("DCDLOAT");
    String ghiChuDS = (String) data.get("NOTE_TANG");
    String ghiChuBSBHYT;
    String emp = (String) data.get("EMPSN");
    String tuThang = data.get("MONTH_BC").toString(); // mm/yyyy
    // luong cua thang gan nhat co tham gia BHXH-TN
    BigDecimal luong = ins.getLuongBaoGiamBHYT(emp, thangBC);
    BigDecimal luongTranXHTN = ins.getMucLuongTran("BHXHTN", thangBC);
    BigDecimal luongTranYT = ins.getMucLuongTran("BHYT", thangBC);
    BigDecimal luongMucCu;
    BigDecimal luongMucMoi;
    double tyleDong = 0;
    double tyleBHTN = 0;

    Date fromMonth = DateUtils.getFirstDay(tuThang.substring(0, 2), tuThang.substring(3, 7));
    Date toMonth = null;
    int ttBHXHTN;
    //row = sheet.getRow(rowCopy);
    cell = row.getCell(0);// STT
    HSSFUtils.fill(cell, stt);
    cell = row.getCell(1);// Ho va ten
    HSSFUtils.fill(cell, data.get("FNAME") + " " + data.get("LNAME"));
    cell = row.getCell(2);// Ma so BHXH
    HSSFUtils.fill(cell, data.get("ID_SOCIAL"));
    cell = row.getCell(3);// Ngay sinh
    HSSFUtils.fill(cell, data.get("BIRTHDAY"));
    cell = row.getCell(4);// Gioi tinh
    if ("NU".equals(data.get("SEX")))
      HSSFUtils.fill(cell, "X");
    else
      HSSFUtils.fill(cell, "");
    cell = row.getCell(16);// Tang tu thang
    HSSFUtils.fill(cell, data.get("MONTH_BC"));
    cell = row.getCell(20);// Ghi chu
    HSSFUtils.fill(cell, ghiChuDS);

    // bat dau khac	
    // if la DCLuong, luong old va luong new deu # 0
    if (loaiDS.equals("DCLuong") == true) {
      cell = row.getCell(6);// Luong old
      if (data.get("DCDLOAT").equals("DCBT") == true) {
        // luong old = luong cua thang gan nhat co tham gia BHXH-TN			
        HSSFUtils.fill(cell, luong.doubleValue());
        luongMucCu = luong;
      } else {
        HSSFUtils.fill(cell, data.get("SAL_OLD"));
        luongMucCu = (BigDecimal) data.get("SAL_OLD");
      }
      cell = row.getCell(11);// Luong new
      HSSFUtils.fill(cell, data.get("BSALARY"));
      luongMucMoi = (BigDecimal) data.get("BSALARY");
    }
    //If la Tang ld, tang dc: luong muc cu =0, luong muc moi = luong hien tai
    //If la tang BS BHYT, luong muc old =0, luong muc moi = luong gan nhat co BHXH-TN 
    else if (loaiDS.equals("Tang") == true) {
      cell = row.getCell(6);// Luong old	
      HSSFUtils.fill(cell, 0);
      luongMucCu = BigDecimal.valueOf(0);
      cell = row.getCell(11);// Luong new
      HSSFUtils.fill(cell, data.get("BSALARY"));
      luongMucMoi = (BigDecimal) (data.get("BSALARY"));
      //if Tang1thang thi den thang= tu thang, else den thang= null
      if (ghiChuDS.equals("Tang1Thang") == true) {
        cell = row.getCell(17);// den thang
        // tuThang=denThang
        toMonth = DateUtils.getFirstDay(tuThang.substring(0, 2), tuThang.substring(3, 7));
        cell.setCellValue(toMonth);
        //HSSFUtils.fill(cell, toMonth);			
      } else {
        //if la Tang do di lam lai/ NS-->RoRa cuoi quy truoc den khi RoVao thi se bao thoai thu BHYT
        // thoai thu tu thang Tang lai -> cuoi quy, luong = luong gan nhat
        if (BSBHYT.equals("BHYT")) {
          ghiChuBSBHYT = (String) ("Thoai thu BSBHYT do " + ghiChuDS);
          cell = row.getCell(20);// Ghi chu
          HSSFUtils.fill(cell, ghiChuBSBHYT);
          toMonth = DateUtils.getThangCuoiQuyHT(Integer.parseInt(tuThang.substring(0, 2)),
              Integer.parseInt(tuThang.substring(3, 7)));

          cell = row.getCell(17);// den thang
          //cell.setCellValue(denThang);
          //HSSFUtils.fill(cell, (sf.format(toMonth)).substring(3, 10));
          //HSSFUtils.fill(cell, toMonth);
          cell.setCellValue(toMonth);

          // if la thoai thu BS BHYT thi luong cu <>0,= luong gan nhat, luong moi =0
          cell = row.getCell(6);// Luong old	
          HSSFUtils.fill(cell, luong.doubleValue());
          luongMucCu = luong;
          cell = row.getCell(11);// Luong new
          HSSFUtils.fill(cell, 0);
          luongMucMoi = BigDecimal.valueOf(0);

        }
        // else la Tang binh thuong NSVao, RoVao,v,vvv thi ko co xet thoai thu
      }
    }

    //If la Giam: luong muc moi = 0		
    else if (loaiDS.equals("Giam") == true) {
      //if la NS-->RoRa thi luong muc cu =0 else =luong gan nhat.
      // or Ro-->NSRa, bo sung 17/09/2013
      if (ghiChuDS.equals("NS-->RoRa") || ghiChuDS.equals("Ro-->NSRa")) {
        cell = row.getCell(6);// Luong old	
        HSSFUtils.fill(cell, 0);
        luongMucCu = BigDecimal.valueOf(0);
      } else {
        cell = row.getCell(6);// Luong old	
        HSSFUtils.fill(cell, luong.doubleValue());
        luongMucCu = luong;
      }

      cell = row.getCell(11);// Luong new
      HSSFUtils.fill(cell, 0);
      luongMucMoi = BigDecimal.valueOf(0);

      // if giam do %NSRa va %RoRa thi den thang= null, else den thang= tu thang
      // da xet bao ham voi Giam ma co tang BS BHYT
      // if la Giam %RoRa thi se co tang BS BHYT, luong tang = luong gan nhat
      //denThang= null
      //cell = row.getCell(17);// den thang			
      //toMonth= null;
      //HSSFUtils.fill(cell, toMonth);	
      //HSSFUtils.fill(cell, toMonth);

      //if giam do %RoRa thi se bao tang BHYT tu thang giam-> cuoi quy, luong = luong gan nhat
      if (BSBHYT.equals("BHYT")) {
        // if la Tang BS BHYT thi luong cu =0, luong moi <>0, = luong gan nhat
        cell = row.getCell(6);// Luong old	
        HSSFUtils.fill(cell, 0);
        luongMucCu = BigDecimal.valueOf(0);
        cell = row.getCell(11);// Luong new
        HSSFUtils.fill(cell, luong.doubleValue());
        luongMucMoi = luong;

        ghiChuBSBHYT = (String) ("Tăng BSBHYT do " + ghiChuDS);
        cell = row.getCell(20);// Ghi chu
        HSSFUtils.fill(cell, ghiChuBSBHYT);
        // denThang= thang cuoi quy
        toMonth = DateUtils.getThangCuoiQuyHT(Integer.parseInt(tuThang.substring(0, 2)),
            Integer.parseInt(tuThang.substring(3, 7)));
        cell = row.getCell(17);// den thang
        cell.setCellValue(toMonth);
        //HSSFUtils.fill(cell, toMonth);
      }
    }

    //if la DC : Den thang = null, luong cu= luong gan nhat, luong moi = luong hien tai
    else {
      cell = row.getCell(6);// Luong old	
      HSSFUtils.fill(cell, luong.doubleValue());
      luongMucCu = luong;
      cell = row.getCell(11);// Luong new
      HSSFUtils.fill(cell, data.get("BSALARY"));
      luongMucMoi = (BigDecimal) data.get("BSALARY");
    }

    if (!BSBHYT.equals("BHYT")) {
      ttBHXHTN = 1;
      cell = row.getCell(22);// Ti le BHXH
      HSSFUtils.fill(cell, thamso.get("TYLEBHXH"));
      cell = row.getCell(24);// Ti le BHTN
      HSSFUtils.fill(cell, thamso.get("TYLEBHTN"));
    } else
      ttBHXHTN = 0;

    // het khac

    cell = row.getCell(23);// Ti le BHYT
    HSSFUtils.fill(cell, thamso.get("TYLEBHYT"));
    cell = row.createCell(48);// So the
    HSSFUtils.fill(cell, emp);
    cell = row.createCell(49);// Xuong
    HSSFUtils.fill(cell, data.get("NAME_GROUP"));
    cell = row.createCell(50);// Don vi
    HSSFUtils.fill(cell, data.get("NAME_DEPT_NAME"));
    // eval formula
    cell = row.getCell(18);
    HSSFUtils.evalFormula(wb, cell);
    // cong thuc mau cua bao hiem, 
    for (int k = 25; k < 48; k++) {
      cell = row.getCell(k);
      HSSFUtils.evalFormula(wb, cell);
    }

    /*
    // ko lay theo cong thuc cua bao hiem nua hzaiiiiiiiiii		
    // lam ko duoc vi cong thuc sum bao cao cuoi ky ko tu hieu
    luongMucCu = luongMucCu.compareTo(luongTranXHTN)>=0?luongTranXHTN:luongMucCu;
    luongMucMoi= luongMucMoi.compareTo(luongTranXHTN)>=0?luongTranXHTN:luongMucMoi;
    BigDecimal chenhLech = luongMucMoi.subtract(luongMucCu);
    
    //if XHTN=1 tuc la co ty le BHXH-TN ->se co so lieu ben quy luong hien hanh (HH), dieu chinh (DC) so phai dong
    //else chi co ty le BHYT thi chi co so lieu ben dieu chinh so phai dong, quy luong hien hanh all =0
    				
    // if chenh lech >0 thi thong tin se dua vao hang muc tang, else la hang muc giam
    // xet hang muc quy luong HH		
    if (ttBHXHTN==1){
    	//25,26,27,28 : BHXH
    	ins.ganExcelPhatSinhBH(25,1, row, cell, chenhLech, tyLeDongBHXH);
    	// 29,30,31,32 : BHYT
    	// if NSRa / DCGiam thi mac du co ty le XH-TN nhung ben HH thi YT se bang 0
    	if (ghiChuDS.equals("NSRa") || ghiChuDS.equals("DCGiam"))
    		ins.ganExcelPhatSinhBH(29,0, row, cell, chenhLech, tyLeDongBHYT);
    	else ins.ganExcelPhatSinhBH(29,1, row, cell, chenhLech, tyLeDongBHYT);
    	// 33,34,35,36 : BHTN
    	ins.ganExcelPhatSinhBH(33,1, row, cell, chenhLech, tyLeDongBHTN);
    	//gan ty le dong va ty le BHTN de so sanh phan tang va giam so LD BHXH,YT,TN phia sau cung
    	tyleDong = tyLeDongBHXH+tyLeDongBHTN+tyLeDongBHYT;
    	tyleBHTN = tyLeDongBHTN;
    }
    else
    {
    	//Quy HH all deu =0
    	//25,26,27,28 : BHXH
    	ins.ganExcelPhatSinhBH(25,0, row, cell, chenhLech, tyLeDongBHXH);
    	// 29,30,31,32 : BHYT
    	ins.ganExcelPhatSinhBH(29,0, row, cell, chenhLech, tyLeDongBHYT);			
    	// 33,34,35,36 : BHTN
    	ins.ganExcelPhatSinhBH(33,0, row, cell, chenhLech, tyLeDongBHTN);
    	
    	//gan ty le dong va ty le BHTN de so sanh phan tang va giam so LD BHXH,YT,TN phia sau cung
    	tyleDong = tyLeDongBHYT;
    	tyleBHTN = 0;			
    }
    
    
    // xet hang muc DC so phai dong		
    toMonth= toMonth!=null?toMonth:thangHH;		
    double soNgay = toMonth.getTime()- fromMonth.getTime();		
    soNgay = Math.floor((soNgay / 1000 / 60 / 60 / 24));
    double soThang = Math.floor((soNgay/365)*12);	
    	 		
    //neu tyleDong=4.5% thi// chi co BHYT
     // 	 sothang=sothang+1
     // else
     // 		neu DenThang< TuThang thi soThang =0
     // 		else 
     // 			neu denthang=thangHH thi sothang=sothang
     //			else // den thang > thangHH
     // 				sothang = sothang+1		 
     		
    if (tyleDong==tyLeDongBHYT )
    	soThang=soThang+1;
    else
    	if (toMonth.compareTo(fromMonth)<0)		
    		soThang=0;		
    	else		
    		soThang = toMonth.compareTo(thangHH)==0?soThang:soThang+1;
    
    cell = row.getCell(37);
    HSSFUtils.fill(cell, soThang);		
    
    // if so tien DC > thi se dua gia tri vao cot tang, else la giam
    double soDC = (luongMucMoi.doubleValue()-luongMucCu.doubleValue());
    // if tyleDong= tyleDongBHYT thi BHXH-TN =0, else se co gia tri cua BHXH-TN-YT
    double dcBHXH 	=0;
    double dcBHTN 	=0;
    double dcBHYT	=0;
    if (tyleDong==tyLeDongBHYT)
    {
    	dcBHXH=0;
    	dcBHTN=0;			
    }
    else
    {
    	dcBHXH	= soDC*soThang*tyLeDongBHXH;
    	dcBHTN	= soDC*soThang*tyLeDongBHTN;			
    }
    dcBHYT = soDC*soThang*tyLeDongBHYT;
    // if soDC>0 thi se dua so lieu vao cot tang
    if (soDC>0){
    	//38,39 : BHXH
    	cell = row.getCell(38);
    	HSSFUtils.fill(cell, (dcBHXH));
    	cell = row.getCell(39);
    	HSSFUtils.fill(cell, 0);			
    	//40,41 : BHYT
    	cell = row.getCell(40);
    	HSSFUtils.fill(cell, (dcBHYT));
    	cell = row.getCell(41);
    	HSSFUtils.fill(cell, 0);			
    	//42,43 : BHTN
    	cell = row.getCell(42);
    	HSSFUtils.fill(cell, (dcBHTN));
    	cell = row.getCell(43);
    	HSSFUtils.fill(cell, 0);			
    }
    //if soDC<0 thi se dua so lieu vao cot giam
    else{
    	//38,39 : BHXH
    	//luc nay soDC <0
    	soDC=Math.abs(soDC);
    	cell = row.getCell(38);
    	HSSFUtils.fill(cell, 0);			
    	cell = row.getCell(39);
    	HSSFUtils.fill(cell, (dcBHXH));
    	//40,41 : BHYT
    	cell = row.getCell(40);
    	HSSFUtils.fill(cell, 0);			
    	cell = row.getCell(41);
    	HSSFUtils.fill(cell, (dcBHYT));
    	//42,43 : BHTN
    	cell = row.getCell(42);
    	HSSFUtils.fill(cell, 0);			
    	cell = row.getCell(43);
    	HSSFUtils.fill(cell, (dcBHTN));			
    }
    //System.out.println(ghiChuDS);
    
    // dem so luong tang/ giam LD BHXH,YT/TN
    int tang =0; int giam =0;
    // if luong moi>0 & luong old =0 & tyleDong>=tyle BHXH+YT & DenThang=ThangHienTai
    if((luongMucMoi.compareTo(BigDecimal.valueOf(0))==1) && (luongMucCu.compareTo(BigDecimal.valueOf(0))==0)
    	&& (tyleDong>=tyLeDongBHXH+tyLeDongBHYT) && (toMonth.compareTo(thangHH)==0))
    {
    	tang =1; giam =0;
    }
    else if ((luongMucMoi.compareTo(BigDecimal.valueOf(0))==0) && (luongMucCu.compareTo(BigDecimal.valueOf(0))==1)
    		&& (tyleDong>=tyLeDongBHXH+tyLeDongBHYT) && (toMonth.compareTo(thangHH)==0))
    {
    	tang =0; giam =1;
    }
    cell = row.getCell(44);
    HSSFUtils.fill(cell, tang);
    cell = row.getCell(45);
    HSSFUtils.fill(cell, giam);		
    
    // if luong moi>0 & luong old =0 & tyleDong>=tyle BHXH+YT & DenThang=ThangHienTai
    if((luongMucMoi.compareTo(BigDecimal.valueOf(0))==1) && (luongMucCu.compareTo(BigDecimal.valueOf(0))==0)
    	&& (tyleBHTN>=tyLeDongBHTN) && (toMonth.compareTo(thangHH)==0))
    {
    	tang =1; giam =0;
    }
    else if ((luongMucMoi.compareTo(BigDecimal.valueOf(0))==0) && (luongMucCu.compareTo(BigDecimal.valueOf(0))==1)
    		&& (tyleBHTN>=tyLeDongBHTN) && (toMonth.compareTo(thangHH)==0))
    {
    	tang =0; giam =1;
    }
    cell = row.getCell(46);
    HSSFUtils.fill(cell, tang);
    cell = row.getCell(47);
    HSSFUtils.fill(cell, giam);					
    */
  }

  public void capNhatDotBaoGiam(int loaibg, int dotbg, String ngaybg, String tuNgay, String denNgay, String thanghh,
      BigDecimal soLD, BigDecimal tongQL, BigDecimal soPhaiDong) {
    // chi cap nhat thong tin neu la user cua tong bo xuat , vi TB se xuat toan bo cty
    String sql;
    boolean temp = true;
    temp = Check_exit("N_BAOGIAM_STATUS", "to_char(NGAY_BG,'" + "dd/mm/yyyy" + "')", "LOAI_BG", ngaybg,
        String.valueOf(loaibg));
    if (temp) {
      sql = "UPDATE N_BAOGIAM_STATUS A SET A.THANGHIENHANH=" + get_todate(thanghh) + ", A.DOTBAOGIAM=" + dotbg
          + ", A.SOLAODONG=" + soLD + ", A.TONGQUYLUONG=" + tongQL + ", A.SOPHAIDONG=" + soPhaiDong
          + " WHERE A.NGAY_BG=" + get_todate(ngaybg) + " AND A.LOAI_BG=" + loaibg;
    } else {
      sql = "INSERT INTO N_BAOGIAM_STATUS " + "(NGAY_BG,FROM_DATE,TO_DATE,STATUS,NOTE,THE_GH_QUY,LOAI_BG"
          + ",UP_USER,UP_DATE,SOLAODONG,TONGQUYLUONG,SOPHAIDONG,DOTBAOGIAM,THANGHIENHANH)";

      if (!tuNgay.equals("") && !denNgay.equals("")) {
        sql = sql + " VALUES( " + get_todate(ngaybg) + "," + get_todate(tuNgay) + "," + get_todate(denNgay)
            + ",1,null, null," + loaibg + "," + Application.getApp().getLoginInfo().getUserID() + ", sysdate, " + soLD
            + "," + tongQL + "," + soPhaiDong + "," + dotbg + "," + get_todate(thanghh) + ")";
      }
    }

    Connection con = getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    try {
      pstm = con.prepareStatement(sql);
      rs = pstm.executeQuery();

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DbUtils.close(rs);
      DbUtils.close(pstm);
      DbUtils.close(con);
    }
  }

  /*
   Gan cong thuc vao file excel de lay so lieu ky truoc va ky nay khi bao cao bao hiem	 
   */

  public BigDecimal[] getSoLieuKyTruocKyNay(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, int rowTongHop,
      HSSFCell cell, FormulaEvaluator evaluator, BigDecimal soLDKyTruoc, BigDecimal tongQLKyTruoc, String thangHienTai,
      int dotBaoGiam, BigDecimal soLD, BigDecimal tongQL, BigDecimal soPhaiDong) {
    int cellKyNay = 29;
    HSSFRow rowTemp;
    /*			cong thuc tinh so lao dong ky nay AD43= AC43+AC36-AD36
    		AD43: row= rowtonghop, cell= cellKyNay
    		AC43=Y43
    		AC43: row= rowtonghop, cell= cellKyNay-1 
    		Y43	: row=rowtonghop, cell= cellKyNay -5
    		Y43=AE43 
    		AE43: row= rowtonghop, cell=cellKyNay+1	, ko co cong thuc
    		
    		AC36 = Y36
    		AC36 : row= rowtonghop-7, cell= cellKyNay-1
    		Y36 = AS28
    		Y36  : row= rowtonghop-7, cell= cellKyNay -5
    		AS28 : row= rowTongHop-14, cell= CellKynay+15
    		
    		Cong thuc tinh Tong quy luong ky nay AD44= AC44+AC37-AD37
    		AD44: row= rowtonghop+1, cell=CellKyNay
    		
    		AC44= Y44, AC44 : row= rowtonghop+1, cell= cellKyNay-1
    		Y44 = AE44, Y44: row= rowtonghop+1, cell= CellKynay-5
    		AE44= so lieu ky truoc, ko co cong thuc. row= rowtonghop+1, cell= cellKynay+1
    		
    		AC37=Y37, AC37 :row= rowtonghop-6, cell=CellKyNay-1
    		Y37=Z29, Y37: row= rowtonghop-6, cell= cellKynay-5
    		Z29: row= rowtonghop-14, cell= cellkynay-4
    		
    		AD37=Z37, AD37: row= rowtonghop-6, cell=cellkynay
    		Z37=AB29, Z37: row= rowtonghop-6, cell= cellkynay-4
    		AB29: row= rowtonghop-14, cell=cellkynay-2
    		
    		cong thuc tinh tien cho Dieu Chinh so phai dong
    		BHYT
    		Tang: Y39= AO29, Y39: row= rowtonghop -4, cell= cellKyNay - 5
    						AO29: row= rowTongHop -14, cell = cellKyNay+11
    		Giam: Z39= AP29, Z39: row= rowTongHop -4, cell= cellKyNay -4
    						AP29: row= rowTongHop -14, cell= cellKynay +12
    						
    		BHTN
    		Tang: AA39= AQ29, AA39: row= rowtonghop -4, cell= cellKyNay - 3
    						AQ29: row= rowTongHop -14, cell = cellKyNay+13
    		Giam: AB39= AR29, AB39: row= rowTongHop -4, cell= cellKyNay -2
    						AR29: row= rowTongHop -14, cell= cellKynay +14
    						
    		BHXH
    		Tang: AC39= AM29, AC39: row= rowtonghop -4, cell= cellKyNay - 1
    						AM29: row= rowTongHop -14, cell = cellKyNay+9
    		Giam: AD39= AN29, AD39: row= rowTongHop -4, cell= cellKyNay
    						AN29: row= rowTongHop -14, cell= cellKynay +10
    						
    		cong Thuc tinh So Phai Dong Ky Nay
    		AD45 = AC45 + AC38 - AD38
    		
    		AC45 = AC44*t le BHXH
    		AC45: row= rowTongHop -2, cell= cellKyNay -1
    		AC44= Y44=AE44= tong QL ky truoc
    		Y44 va AE44 da gan o tren roi
    		AC44: row= rowTongHop -1, cell= cellKyNay -1
    		
    		//So phai dong BHXH phat sinh tang ky nay
    		AC38 = AA29
    		AA29 : row= rowTongHop -14, cell= cellKyNay - 3
    		AC38 : row= rowTongHop - 5, cell= cellKyNay - 1
    		
    		// so phai dong BHXH phat sinh giam ky nay
    		AD38 = AC29
    		AC29 : row= rowTongHop -14, cell= cellKyNay - 1
    		AD38 : row= rowTongHop - 5, cell= cellKyNay 
    		
    		 		
    */

    row = sheet.getRow(rowTongHop);
    //gan cong thuc cho cac cell lien quan de cap nhat gia tri moi						
    //AS28			
    rowTemp = sheet.getRow(rowTongHop - 14);
    cell = rowTemp.getCell(cellKyNay + 15);
    evaluator = wb.getCreationHelper().createFormulaEvaluator();
    evaluator.evaluateFormulaCell(cell);
    //Y36
    rowTemp = sheet.getRow(rowTongHop - 7);
    cell = rowTemp.getCell(cellKyNay - 5);
    evaluator = wb.getCreationHelper().createFormulaEvaluator();
    evaluator.evaluateFormulaCell(cell);
    //AC36
    rowTemp = sheet.getRow(rowTongHop - 7);
    cell = rowTemp.getCell(cellKyNay - 1);
    evaluator = wb.getCreationHelper().createFormulaEvaluator();
    evaluator.evaluateFormulaCell(cell);
    String temp = null;
    // Thong tin ky truoc = so lieu cua dot bao giam hien tai -1
    String thang = thangHienTai;
    String dotBG = String.valueOf(dotBaoGiam - 1);
    //gan so lieu ky truoc
    // if dotbaogiam =1 thi lay max cua thang truoc
    if (dotBaoGiam == 1) {
      //luc nay se lay thong tin cua dot bao giam max nhat cua thang hien tai -1
      String thangTruoc =
          GetField("to_char(day_of_month(" + get_todate(thangHienTai) + "-1,'" + "01" + "'),'" + "dd/mm/yyyy" + "')",
              "dual", "", "", "", "", "", "");
      String dotbgMax = GetField("MAX(DOTBAOGIAM)", "N_BAOGIAM_STATUS", "to_char(THANGHIENHANH,'" + "dd/mm/yyyy" + "')",
          "", "", thangTruoc, "", "");
      thang = thangTruoc;
      dotBG = dotbgMax;
    }

    temp = GetField("SOLAODONG", "N_BAOGIAM_STATUS", "to_char(THANGHIENHANH,'" + "dd/mm/yyyy" + "')", "DOTBAOGIAM", "",
        thang, dotBG, "");
    soLDKyTruoc = temp == null ? BigDecimal.valueOf(0) : BigDecimal.valueOf(Double.parseDouble(temp));
    temp = GetField("TONGQUYLUONG", "N_BAOGIAM_STATUS", "to_char(THANGHIENHANH,'" + "dd/mm/yyyy" + "')", "DOTBAOGIAM",
        "", thang, dotBG, "");
    tongQLKyTruoc = temp == null ? BigDecimal.valueOf(0) : BigDecimal.valueOf(Double.parseDouble(temp));

    //AE43
    rowTemp = sheet.getRow(rowTongHop);
    //Gan so lieu ky truoc, AE43 soLDKyTruoc
    cell = rowTemp.getCell(cellKyNay + 1);
    cell.setCellValue(soLDKyTruoc.doubleValue());
    //AE44: TongQuyLuongKyTruoc
    rowTemp = sheet.getRow(rowTongHop + 1);
    cell = rowTemp.getCell(cellKyNay + 1);
    cell.setCellValue(tongQLKyTruoc.doubleValue());

    //Y43	
    rowTemp = sheet.getRow(rowTongHop);
    cell = rowTemp.getCell(cellKyNay - 5);
    evaluator = wb.getCreationHelper().createFormulaEvaluator();
    evaluator.evaluateFormulaCell(cell);
    //AC43
    cell = rowTemp.getCell(cellKyNay - 1);
    evaluator = wb.getCreationHelper().createFormulaEvaluator();
    evaluator.evaluateFormulaCell(cell);

    //gan cong thuc de lay so lieu cho ky nay
    //AD43: So LD
    cell = rowTemp.getCell(cellKyNay);
    evaluator = wb.getCreationHelper().createFormulaEvaluator();
    evaluator.evaluateFormulaCell(cell);

    //Y44
    rowTemp = sheet.getRow(rowTongHop + 1);
    cell = rowTemp.getCell(cellKyNay - 5);
    evaluator = wb.getCreationHelper().createFormulaEvaluator();
    evaluator.evaluateFormulaCell(cell);
    //AC44			
    cell = rowTemp.getCell(cellKyNay - 1);
    evaluator = wb.getCreationHelper().createFormulaEvaluator();
    evaluator.evaluateFormulaCell(cell);

    // bat dau gan cho tong quy luong
    //AB29
    rowTemp = sheet.getRow(rowTongHop - 14);
    cell = rowTemp.getCell(cellKyNay - 2);
    evaluator = wb.getCreationHelper().createFormulaEvaluator();
    evaluator.evaluateFormulaCell(cell);
    //Z37=AB29, Z37: row= rowtonghop-6, cell= cellkynay-4
    rowTemp = sheet.getRow(rowTongHop - 6);
    cell = rowTemp.getCell(cellKyNay - 4);
    evaluator = wb.getCreationHelper().createFormulaEvaluator();
    evaluator.evaluateFormulaCell(cell);
    //AD37=Z37, AD37: row= rowtonghop-6, cell=cellkynay
    rowTemp = sheet.getRow(rowTongHop - 6);
    cell = rowTemp.getCell(cellKyNay);
    evaluator = wb.getCreationHelper().createFormulaEvaluator();
    evaluator.evaluateFormulaCell(cell);

    //Z29: row= rowtonghop-14, cell= cellkynay-4
    rowTemp = sheet.getRow(rowTongHop - 14);
    cell = rowTemp.getCell(cellKyNay - 4);
    evaluator = wb.getCreationHelper().createFormulaEvaluator();
    evaluator.evaluateFormulaCell(cell);
    //Y37=Z29, Y37: row= rowtonghop-6, cell= cellKynay-5
    rowTemp = sheet.getRow(rowTongHop - 6);
    cell = rowTemp.getCell(cellKyNay - 5);
    evaluator = wb.getCreationHelper().createFormulaEvaluator();
    evaluator.evaluateFormulaCell(cell);
    //AC37=Y37, AC37 :row= rowtonghop-6, cell=CellKyNay-1
    rowTemp = sheet.getRow(rowTongHop - 6);
    cell = rowTemp.getCell(cellKyNay - 1);
    evaluator = wb.getCreationHelper().createFormulaEvaluator();
    evaluator.evaluateFormulaCell(cell);

    //AE44= so lieu ky truoc, ko co cong thuc. row= rowtonghop+1, cell= cellKynay+1			
    //Y44 = AE44, Y44: row= rowtonghop+1, cell= CellKynay-5			
    //AC44= Y44, AC44 : row= rowtonghop+1, cell= cellKyNay-1
    // da gan o tren roi

    //AD44 : Tong Qluong
    rowTemp = sheet.getRow(rowTongHop + 1);
    cell = rowTemp.getCell(cellKyNay);
    evaluator = wb.getCreationHelper().createFormulaEvaluator();
    evaluator.evaluateFormulaCell(cell);

    //AD45 : So phai dong
    rowTemp = sheet.getRow(rowTongHop + 2);
    cell = rowTemp.getCell(cellKyNay);
    evaluator = wb.getCreationHelper().createFormulaEvaluator();
    evaluator.evaluateFormulaCell(cell);

    // Dieu chinh so phai dong
    //AO29
    rowTemp = sheet.getRow(rowTongHop - 14);
    cell = rowTemp.getCell(cellKyNay + 11);
    HSSFUtils.evalFormula(wb, cell);
    //AP29		
    cell = rowTemp.getCell(cellKyNay + 12);
    HSSFUtils.evalFormula(wb, cell);
    //AQ29		
    cell = rowTemp.getCell(cellKyNay + 13);
    HSSFUtils.evalFormula(wb, cell);
    //AR29		
    cell = rowTemp.getCell(cellKyNay + 14);
    HSSFUtils.evalFormula(wb, cell);
    //AM29		
    cell = rowTemp.getCell(cellKyNay + 9);
    HSSFUtils.evalFormula(wb, cell);
    //AN29		
    cell = rowTemp.getCell(cellKyNay + 10);
    HSSFUtils.evalFormula(wb, cell);

    //Y39
    rowTemp = sheet.getRow(rowTongHop - 4);
    cell = rowTemp.getCell(cellKyNay - 5);
    HSSFUtils.evalFormula(wb, cell);
    //Z39
    cell = rowTemp.getCell(cellKyNay - 4);
    HSSFUtils.evalFormula(wb, cell);
    //AA39		
    cell = rowTemp.getCell(cellKyNay - 3);
    HSSFUtils.evalFormula(wb, cell);
    //AB39		
    cell = rowTemp.getCell(cellKyNay - 2);
    HSSFUtils.evalFormula(wb, cell);
    //AC39		
    cell = rowTemp.getCell(cellKyNay - 1);
    HSSFUtils.evalFormula(wb, cell);
    //AD39		
    cell = rowTemp.getCell(cellKyNay);
    HSSFUtils.evalFormula(wb, cell);

    /*
     		cong Thuc tinh So Phai Dong Ky Nay
    		AD45 = AC45 + AC38 - AD38
    		
    		AC45 = AC44*t le BHXH
    		AC45: row= rowTongHop -2, cell= cellKyNay -1
    		AC44= Y44=AE44= tong QL ky truoc
    		Y44 va AE44 da gan o tren roi
    		AC44: row= rowTongHop -1, cell= cellKyNay -1
    		
    		//So phai dong BHXH phat sinh tang ky nay
    		AC38 = AA29
    		AA29 : row= rowTongHop -14, cell= cellKyNay - 3
    		AC38 : row= rowTongHop - 5, cell= cellKyNay - 1
    		
    		// so phai dong BHXH phat sinh giam ky nay
    		AD38 = AC29
    		AC29 : row= rowTongHop -14, cell= cellKyNay - 1
    		AD38 : row= rowTongHop - 5, cell= cellKyNay
     */
    //so Phai Dong Ky Nay		
    //AA29
    rowTemp = sheet.getRow(rowTongHop - 14);
    cell = rowTemp.getCell(cellKyNay - 3);
    HSSFUtils.evalFormula(wb, cell);
    //AC29
    cell = rowTemp.getCell(cellKyNay - 1);
    HSSFUtils.evalFormula(wb, cell);
    //AD38
    rowTemp = sheet.getRow(rowTongHop - 5);
    cell = rowTemp.getCell(cellKyNay);
    HSSFUtils.evalFormula(wb, cell);
    //AC38
    cell = rowTemp.getCell(cellKyNay - 1);
    HSSFUtils.evalFormula(wb, cell);
    //AC44: row= rowTongHop -1, cell= cellKyNay -1
    rowTemp = sheet.getRow(rowTongHop - 1);
    cell = rowTemp.getCell(cellKyNay - 1);
    HSSFUtils.evalFormula(wb, cell);
    //AC45
    rowTemp = sheet.getRow(rowTongHop - 2);
    cell = rowTemp.getCell(cellKyNay - 1);
    HSSFUtils.evalFormula(wb, cell);
    //AD45		
    cell = rowTemp.getCell(cellKyNay);
    HSSFUtils.evalFormula(wb, cell);

    //Lay so lieu ky truoc
    rowTemp = sheet.getRow(rowTongHop + 1);
    cell = rowTemp.getCell(cellKyNay + 1);
    cell.setCellValue(tongQLKyTruoc.doubleValue());
    System.out.println("So lieu ky truoc: " + soLDKyTruoc.doubleValue() + ", " + tongQLKyTruoc.doubleValue());

    //Lay so lieu ky nay
    row = sheet.getRow(rowTongHop);
    cell = row.getCell(cellKyNay);
    System.out.println(cell.getNumericCellValue() + "");
    soLD = BigDecimal.valueOf(cell.getNumericCellValue());
    row = sheet.getRow(rowTongHop + 1);
    cell = row.getCell(cellKyNay);
    tongQL = BigDecimal.valueOf(cell.getNumericCellValue());
    row = sheet.getRow(rowTongHop + 2);
    cell = row.getCell(cellKyNay);
    soPhaiDong = BigDecimal.valueOf(cell.getNumericCellValue());

    System.out.println("Ky Nay: " + soLD + "_" + tongQL + "_" + rowTongHop + "_" + soPhaiDong);
    return new BigDecimal[] { soLD, tongQL, soPhaiDong };

  }

}
