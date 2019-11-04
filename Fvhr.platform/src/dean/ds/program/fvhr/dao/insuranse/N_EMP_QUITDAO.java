package ds.program.fvhr.dao.insuranse;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jaxen.function.ConcatFunction;

import ds.program.fvhr.dao.JdbcDAO2;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_EMP_QUIT;
import ds.program.fvhr.ui.insurance.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;

/**
 * HR data - combine Hibernate IGenericDAO
 * 
 * @author Hoang Anh
 *
 */

public class N_EMP_QUITDAO extends JdbcDAO2 {

	private InsuranceDAO ins = new InsuranceDAO();
	private OBJ_UTILITY objU = new OBJ_UTILITY();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public N_EMP_QUITDAO() {
		super();
	}

	public Integer KiemTraDotBaoGiam(String ngay_bd, String ngay_kt) {
		String sql = null;
		Integer check = 0;
		// =1 la hop le, =0 la ko hop le
		check = Integer.parseInt(ins.GetField("status", "N_BAOGIAM_STATUS", "to_char(from_date,'" + "dd/mm/yyyy" + "')",
				"to_char(to_date,'" + "dd/mm/yyyy" + "')", null, ngay_bd, ngay_kt, null));
		return check;
	}

	public String getXuongSoQNV(String maDV) {
		String xuong = null;
		InsuranceDAO conn = new InsuranceDAO();
		xuong = conn.GetField("name_fact", "n_department", "id_dept", "", "", maDV, "", "");
		if (xuong == "FVL") {
			xuong = "FL";
			return xuong;
		}
		if (xuong == "FVLS") {
			xuong = "FS";
			return xuong;
		}
		if (xuong == "FVJ") {
			xuong = "FJ";
			return xuong;
		}
		if (xuong == "KDAO") {
			xuong = "KD";
			return xuong;
		}
		if (xuong == "TB") {
			xuong = "TB";
			return xuong;
		}
		return xuong;
	}

	public String getSoQDNV(String empsn, Date ngayThucNghi) {
		// TODO Auto-generated method stub
		// Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,"Su
		// kien change");
		// System.out.print("ok");
		// String mai lam tiep ham nay lay so QDNV.............
		InsuranceDAO conn = new InsuranceDAO();
		String tenXuong = "";
		String depsn = "";
		String soQDNV = "";
		String soQDNVTemp = "0";
		Integer namThucNghi = 0;
		Integer soQDNVMax = 0;

		IGenericDAO<N_EMPLOYEE, String> objDao = Application.getApp().getDao(N_EMPLOYEE.class);
		N_EMPLOYEE objData = objDao.findById(empsn);
		depsn = objData.getDEPSN();
		// if so the the o au tien la bo viec, depsn=00000
		// bi gio quay lai lam don xin nghi viec thi depsn=00000
		// nhung minh van lay depsn truoc khi nghi viec de lay fact va gan soQDNV cho ho
		if (depsn == "00000") {
			depsn = objData.getDEPSN_TEMP();
		}
		tenXuong = getXuongSoQNV(depsn);
		// getYear la nam -1900= khoang cach nam hien tai den 1900
		// muon lay gia tri nam cua chinh date thi .getYear+1900 tro lai
		namThucNghi = ngayThucNghi.getYear() + 1900;
		soQDNVTemp = conn.GetField("nvl(max( to_number(substr(so_qdnv,7))),0)", "n_emp_quit", "substr(so_qdnv,1,2)",
				"substr(so_qdnv,3,4)", "", tenXuong, Integer.toString(namThucNghi), "");
		soQDNVMax = Integer.parseInt(soQDNVTemp) + 1;// them 1 cho record moi bi gio
		soQDNV = tenXuong + Integer.toString(namThucNghi) + Integer.toString(soQDNVMax);
		return soQDNV;
	}

	public void InsertLostDataDetailAuto(String depsn, String ngayThucNghi, BigDecimal idQuit, String empsn) {
		String userId = Application.getApp().getLoginInfo().getUserID();
		String tenXuong = ins.GetField("name_fact", "n_department", "id_dept", "", "", depsn, "", "");
		// if la FVL thi se tu dong nhap xac nhan du lieu khac thuong , gio ra, cho ngay
		// thuc nghi
		// bo sung 17/09/2013 ke tu 01/09/2013 FVJ thuoc FVL quan ly
		// va chi nhap XNKT cho CNV Xin Thoi, loai tru bo viec : 5,28.
		// if ngay thuc nghi la chu nhat thi he thong ko nhap XNKT, nhan su tu nhap cho
		// ngay T7

		/*
		 * 23/09/2013 Thay doi ngay thuc nghi doi voi CNV dang xin thoi <23/09/2013 :
		 * XThoi: ngay thuc nghi la ngay cuoi cung den cty BViec: ngay thuc nghi la ngay
		 * ko con den cty nhap XNKT cho XTHoi ngay thuc nghi >=23/09/2013 : XThoi=
		 * BViec: ngay thuc nghi la ngay ko con den cty Nhap XNKT cho XThoi ngay thuc
		 * nghi-1 de ho tro cho bao cao hang ngay cho Dai Loan
		 */
		Calendar cal = Calendar.getInstance();
		cal = ins.get_toCalendar(ngayThucNghi);
		cal.add(cal.DATE, -1);
		Date ngayTru1 = cal.getTime();
		String ngayThucNghiTru1 = sdf.format(ngayTru1);

		if (((tenXuong.equals("FVL")) || (tenXuong.equals("FVJ"))) && (!idQuit.equals(5)) && (!idQuit.equals(28))) {
			// if Nhan su da nhap phep roi thi ko duoc nhap xac nhan du lieu khac thuong
			// BigDecimal tsPhep = null;
			String phep = ins.GetField("total", "n_rest_detail", "empsn", "date_off", "", empsn, ngayThucNghiTru1, "");
			// tsPhep =new BigDecimal(phep);
			float tsPhep = 0;
			try {
				if (phep != null) {
					tsPhep = Float.valueOf(phep.trim()).floatValue();
				}
			} catch (NumberFormatException nfe) {
				System.out.println("NumberFormatException: " + nfe.getMessage());
			}

			if (tsPhep == 0) {
				// if ngay thuc nghi tru 1 la chu nhat thi bo qua
				if (ins.ToDate(ngayThucNghiTru1).getDay() != Calendar.SUNDAY) {
					String idShift = ins.GetField("id_shift", "n_register_shift", "empsn", "shift_date", "", empsn,
							ngayThucNghiTru1, "");
					String tOut = null;
					if (idShift != null) {
						tOut = ins.GetField("time_out", "n_register_shift", "empsn", "shift_date", "", empsn,
								ngayThucNghiTru1, "");
					} else {
						idShift = ins.GetField("shift", "n_employee", "empsn", "", "", empsn, "", "");
						tOut = ins.GetField("time_out", "n_shift", "id_shift", "", "", idShift, "", "");
					}
					tOut = tOut.replace(":", "");
					// System.out.println(tOut);
					String temp = ins.GetField("count(*)", "n_lost_data_detail", "empsn", "date_lost", "", empsn,
							ngayThucNghiTru1, "");
					int countEmpsn = 0;
					countEmpsn = Integer.parseInt(temp);

					String noteAction = null;
					if (countEmpsn == 0) {
						// insert vao n_lost_data_detail
						try {
							objU.InsertLostDataDetail(empsn, ngayThucNghiTru1, "", "", tOut, "", "0", "KH", userId);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						noteAction = "INSERT";
					} else {
						// update vao n_lost_data_detail
						try {
							objU.UpdateLostDataDetail(empsn, ngayThucNghiTru1, "", "", tOut, "", "0", "KH", userId);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						noteAction = "UPDATE";
					}

					// luu lai trong actiondaily
					try {
						objU.InputActionDaily(userId, "N_LOST_DATA_DETAIL", noteAction, empsn,
								"Cap nhat tu dong gio ra " + tOut + " cho ngay thuc nghi " + ngayThucNghiTru1
										+ ", FVL/FVJ.");
						// luu lai trong actiondaily
						try {
							objU.InputActionDaily(userId, "N_LOST_DATA_DETAIL", noteAction, empsn,
									"Cap nhat tu dong cho ngay thuc nghi, FVL/FVJ.");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void capNhatDuLieuLienQuan(String empsn, String depsn, String useStatus, String ngayThucNghi) {
		String sql = null;
		// update depsn trong n_employee
		sql = " update n_employee set depsn='" + depsn + "'";
		sql = sql + ",depsn_temp='" + depsn + "'" + ",depsn_temp1='" + depsn + "'";
		sql = sql + " where empsn='" + empsn + "'";
		objU.Exe_Update_Sql(sql);

		// update trang thai trong n_ic_card, de nguoi khac co the su dung lai IC nay
		sql = null;
		if (useStatus.equals("0")) {
			sql = "update n_ic_card set use_status=0,note='" + "Nghi viec" + "'";
			sql = sql + " where ic_no=(select empcn from n_Emp_Iccard where empsn='" + empsn + "'";
			sql = sql + " and end_date is null)";
			objU.Exe_Update_Sql(sql);

			// update end_date cua IC_Card, chua update status lien duoc vi co kha nang chua
			// tinh luong
			sql = null;
			sql = "update n_Emp_Iccard set end_date=" + ins.get_todate(ngayThucNghi);
			sql = sql + " where empsn='" + empsn + "'" + " and use_status=1 and end_date is null";
			objU.Exe_Update_Sql(sql);
		} else {
			sql = "update n_ic_card set use_status=1,note=null";
			sql = sql + " where ic_no=(select empcn from n_Emp_Iccard where empsn='" + empsn + "'";
			sql = sql + " and end_date =" + ins.get_todate(ngayThucNghi) + ")";
			objU.Exe_Update_Sql(sql);

			// update end_date cua IC_Card, chua update status lien duoc vi co kha nang chua
			// tinh luong
			sql = null;
			sql = "update n_Emp_Iccard set use_status=1, end_date=null";
			sql = sql + " where empsn='" + empsn + "'" + " and end_date=" + ins.get_todate(ngayThucNghi);
			objU.Exe_Update_Sql(sql);
		}

	}

	public String GetSqlGiayThanhToan(String empsn) {

		InsuranceDAO ins = new InsuranceDAO();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

		String startDate = "";
		String endDate = "";
		String sql = null;

		sql = "Select a.empsn, a.empcn as IC, a.fname ||" + "'" + "'" + "|| a.lname as fname";
		sql = sql + ", a.lname, a.date_hired as NNX, a.position as possn ";
		sql = sql + ",b.name_fact||" + "'" + "'" + "|| b.name_group as fgroup ";
		sql = sql + ", b.name_dept_name as donvi, c.bsalary, d.qdnv ";
		//
		sql = sql + ",substr(to_char(d.real_off_date," + "'" + "dd/mm/yyyy" + "'" + "), 1, 2) as n_nghi ";
		sql = sql + ",substr(to_char(d.real_off_date," + "'" + "dd/mm/yyyy" + "'" + "), 4, 2) as t_nghi ";
		sql = sql + ",substr(to_char(d.real_off_date," + "'" + "dd/mm/yyyy" + "'" + "), 7, 4) as na_nghi";
		//
		sql = sql + ", d.id_quit_reason, d.real_off_date ";
		// năm nhuần???
		sql = sql + ",Trunc((d.real_off_date - a.date_hired) / 365, 0) as nam1, f.note1 ";
		// không đúng
		sql = sql
				+ ",Trunc(((d.real_off_date - a.date_hired) - trunc((d.real_off_date - a.date_hired) / 365, 0) * 365) / 30, 0) as thang1 ";
		//
		sql = sql + ",   Trunc ((d.real_off_date-a.date_hired)/365,0)*12 ";
		sql = sql + "+ trunc(trunc ( (d.real_off_date-a.date_hired)/365,0) / 5 ,0)";
		sql = sql + "+";
		sql = sql + "(";
		sql = sql + "	(case ";
		sql = sql + " When d.real_off_date>=to_date(" + "'" + "15/" + "'" + "||to_char(d.real_off_date," + "'"
				+ "mm/yyyy" + "'" + ")," + "'" + "dd/mm/yyyy" + "'" + ") then";
		sql = sql
				+ " Trunc(((d.real_off_date - a.date_hired)  - trunc((d.real_off_date - a.date_hired) / 365, 0) * 365) / 30,0)+1 ";
		sql = sql + " When d.real_off_date<to_date(" + "'" + "15/" + "'" + "||to_char(d.real_off_date," + "'"
				+ "mm/yyyy" + "'" + ")," + "'" + "dd/mm/yyyy" + "'" + ") then";
		sql = sql
				+ " Trunc(((d.real_off_date - a.date_hired)  - trunc((d.real_off_date - a.date_hired) / 365, 0) * 365) / 30,0) ";
		sql = sql + " end)";
		sql = sql + " ) as OBTAIN ";

		sql = sql + ",    (case ";
		sql = sql + " When d.real_off_date>=to_date(" + "'" + "15/" + "'" + "||to_char(d.real_off_date," + "'"
				+ "mm/yyyy" + "'" + ")," + "'" + "dd/mm/yyyy" + "'" + ") then ";
		sql = sql + " To_number(to_char(d.real_off_date," + "'" + "mm" + "'" + "))";
		sql = sql + " When d.real_off_date<to_date(" + "'" + "15/" + "'" + "||to_char(d.real_off_date," + "'"
				+ "mm/yyyy" + "'" + ")," + "'" + "dd/mm/yyyy" + "'" + ") then ";
		sql = sql + " To_number(to_char(d.real_off_date," + "'" + "mm" + "'" + "))-1 ";
		sql = sql + " end) as TS_HTAI ";

		sql = sql + " , nvl((select e.used from n_rest e";

		sql = sql + " where e.empsn = a.empsn and e.year = to_char(d.real_off_date," + "'" + "yyyy" + "'"
				+ ")),0) as TS_USED_HTAI ";

		sql = sql + ",(case ";
		sql = sql + " When trunc((trunc((d.real_off_date - a.date_hired) / 365, 0) * 365) / 30, 0) >= 6 then ";
		sql = sql + "(case ";
		sql = sql + " When (d.real_off_date >= to_date(" + "'" + "31/12/2008" + "'" + "," + "'" + "dd/mm/yyyy" + "'"
				+ ")) then ";
		sql = sql + " Trunc((to_date(" + "'" + "31/12/2008" + "'" + "," + "'" + "dd/mm/yyyy" + "'"
				+ ") - a.date_hired) / 365, 0) * 0.5 ";
		sql = sql + " When (d.real_off_date < to_date(" + "'" + "31/12/2008" + "'" + "," + "'" + "dd/mm/yyyy" + "'"
				+ ")) then ";
		sql = sql + " Trunc((d.real_off_date - a.date_hired) / 365, 0) * 0.5 ";
		sql = sql + " end) + 0.25 ";
		sql = sql + " When trunc((trunc((d.real_off_date - a.date_hired) / 365, 0) * 365) / 30,0) < 6 then ";
		sql = sql + "(case";
		sql = sql + " When (d.real_off_date >= to_date(" + "'" + "31/12/2008" + "'" + "," + "'" + "dd/mm/yyyy" + "'"
				+ ")) then ";
		sql = sql + " Trunc((to_date(" + "'" + "31/12/2008" + "'" + "," + "'" + "dd/mm/yyyy" + "'"
				+ ") - a.date_hired) / 365, 0) * 0.5 ";
		sql = sql + " When (d.real_off_date < to_date(" + "'" + "31/12/2008" + "'" + "," + "'" + "dd/mm/yyyy" + "'"
				+ ")) then ";
		sql = sql + " Trunc((d.real_off_date - a.date_hired) / 365, 0) * 0.5 ";
		sql = sql + " end)";
		sql = sql + " end) as trocap1 ";

		sql = sql + ",(select count(*) from n_rest_detail f ";
		sql = sql + " where f.empsn=a.empsn ";
		sql = sql + " and to_char(date_off," + "'" + "yyyy" + "'" + ")";
		sql = sql + "=to_char(d.real_off_date," + "'" + "yyyy" + "'" + ")";
		sql = sql + " and rest_type=" + "'" + "TON" + "'" + ") TON_USED_HTAI ";

		sql = sql + ",nvl((select e.stored from n_rest e ";
		sql = sql + " where e.empsn=a.empsn ";
		sql = sql + " and e.year=to_char(d.real_off_date," + "'" + "yyyy" + "'" + ")),0) TON_HTAI ";

		sql = sql + " From n_employee a, n_department b, n_basic_salary c, n_emp_quit d, N_Quit_Reason f ";
		sql = sql
				+ " Where a.depsn = b.id_dept and a.empsn = c.empsn and a.empsn = d.empsn and d.id_quit_reason = f.id_quit_reason ";
		sql = sql + " and c.keys = 1 ";
		sql = sql + " and a.empsn in (" + "'" + empsn + "'" + "," + "'" + "00080510" + "'" + ")";

		return sql;
	}

	public String GetSqlQDNV(String empsn) {

		InsuranceDAO ins = new InsuranceDAO();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

		String sql = null;
		sql = "select Q.empsn,Q.off_date,Q.real_off_date,R.name_qr,Q.note ";
		sql = sql + ",a.fname||" + "'" + " " + "'" + "||a.lname as fname,a.lname,a.empcn,a.position ";
		sql = sql + " ,b.name_fact||'" + " " + "'||b.name_group as f_group,b.name_dept_name ";
		sql = sql + ",substr(to_char(q.off_date,'" + "dd/mm/yyyy" + "'),1,2) as n_xin ";
		sql = sql + ",substr(to_char(q.off_date," + "'" + "dd/mm/yyyy" + "'" + "),4,2) as t_xin, ";
		sql = sql + " substr(to_char(q.off_date," + "'" + "dd/mm/yyyy" + "'" + "),7,4) as na_xin ";
		sql = sql + ",substr(to_char(q.real_off_date," + "'" + "dd/mm/yyyy" + "'" + "),1,2) as n_nghi, ";
		sql = sql + " substr(to_char(q.real_off_date," + "'" + "dd/mm/yyyy" + "'" + "),4,2) as t_nghi ";
		sql = sql + ",substr(to_char(q.real_off_date," + "'" + "dd/mm/yyyy" + "'" + "),7,4) as na_nghi ";
		sql = sql + ",nvl(q.qdnv,1) as qdnv,q.so_qdnv ";
		sql = sql + " ,substr(to_char(q.date_hen," + "'" + "dd/mm/yyyy" + "'" + "),1,2) as n_hen, ";
		sql = sql + " substr(to_char(q.date_hen," + "'" + "dd/mm/yyyy" + "'" + "),4,2) as t_hen ";
		sql = sql + ",substr(to_char(q.date_hen," + "'" + "dd/mm/yyyy" + "'" + "),7,4) as na_hen ";
		sql = sql + " ,substr(to_char(sysdate," + "'" + "dd/mm/yyyy" + "'" + "),1,2) as n_ky, ";
		sql = sql + " substr(to_char(sysdate," + "'" + "dd/mm/yyyy" + "'" + "),4,2) as t_ky ";
		sql = sql + " ,substr(to_char(sysdate," + "'" + "dd/mm/yyyy" + "'" + "),7,4) as na_ky ";
		sql = sql + " from N_Emp_Quit Q,N_Quit_Reason R,n_employee a,n_department b ";
		sql = sql + " where R.id_quit_reason=Q.id_quit_reason and q.empsn=a.empsn and q.depsn=b.id_dept ";
		// sql=sql+" and q.empsn in ($P{empsn}) ";
		sql = sql + " and q.empsn = " + "'" + empsn + "'";

		return sql;
	}

	public String GetSqlQDSaThai(String empsn) {

		InsuranceDAO ins = new InsuranceDAO();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

		String startDate = "";
		String endDate = "";
		String sql = null;

		sql = "select Q.empsn,Q.off_date,Q.real_off_date,R.name_qr,Q.note\n"
				+ ",a.fname||' '||a.lname as fname,a.lname,a.empcn,a.position\n"
				+ ",b.name_fact||' '||b.name_group as f_group,b.name_dept_name\n" + ",c.qdkl,c.id_khoan,c.id_dieu\n"
				+ ",substr(to_char(c.date_p,'dd/mm/yyyy'),1,2) as n_kl\n"
				+ ",substr(to_char(c.date_p,'dd/mm/yyyy'),4,2) as t_kl\n"
				+ ",substr(to_char(c.date_p,'dd/mm/yyyy'),7,4) as na_kl\n"
				+ ",substr(to_char(q.real_off_date,'dd/mm/yyyy'),1,2) as n_nghi\n"
				+ ",substr(to_char(q.real_off_date,'dd/mm/yyyy'),4,2) as t_nghi\n"
				+ ",substr(to_char(q.real_off_date,'dd/mm/yyyy'),7,4) as na_nghi\n" + ",nvl(qdnv,1) as qdnv,q.so_qdnv\n"
				+ ",substr(to_char(q.date_hen,'dd/mm/yyyy'),1,2) as n_hen\n"
				+ ",substr(to_char(q.date_hen,'dd/mm/yyyy'),4,2) as t_hen\n"
				+ ",substr(to_char(q.date_hen,'dd/mm/yyyy'),7,4) as na_hen\n"
				+ ",substr(to_char(sysdate,'dd/mm/yyyy'),1,2) as n_ky\n"
				+ ",substr(to_char(sysdate,'dd/mm/yyyy'),4,2) as t_ky\n"
				+ ",substr(to_char(sysdate,'dd/mm/yyyy'),7,4) as na_ky\n"
				+ ",(select sum(d.total) from n_Rest_Detail d where d.empsn=a.empsn\n" + "and d.rest_kind='KC') as KC\n"
				+ "from N_Emp_Quit Q,N_Quit_Reason R,n_employee a,n_department b ,n_kyluat c\n"
				+ "where R.id_quit_reason=Q.id_quit_reason and q.empsn=a.empsn and q.depsn=b.id_dept\n"
				+ "and a.empsn=c.empsn and q.id_quit_reason in ('6','9') and c.id_dieu=48" + " and a.empsn = " + "'"
				+ empsn + "'";

		return sql;
	}

}
