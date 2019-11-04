package ds.program.fvhr.util;

//Danh rieng cho Ngan su dung, vui long member khac khong viet vao ham nay
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.RadioButton;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.sun.star.uno.IEnvironment;

import ds.program.fvhr.util.OBJ_UTILITY;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_N_HEALTH_R;
import ds.program.fvhr.domain.N_USER_LIMIT;
import ds.program.fvhr.domain.combo.EmployeeDepartment;
import fv.util.DateUtils;
import fv.util.Vni2Uni;
import ds.program.fvhr.son.ui.ObjUtility;
import ds.program.fvhr.domain.N_REST_DETAIL;
import ds.program.fvhr.domain.pk.N_REST_DETAILPk;
import ds.program.fvhr.domain.N_REGISTER_SHIFT;
import ds.program.fvhr.domain.pk.N_REGISTER_SHIFTPk;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;

public class OBJ_EMPSN {

	public static int empsn_NV = -1;// "Nghi viec";
	public static int empsn_NS = 0;// "Nghi san";
	public static int empsn_BT = 1;// "Binh thuong";
	public static boolean empsn_reg_bear = false;
	public static String rest_kind = "";
	public static String note_7H = "";

	private String _empsn;
	private String _month;
	private Date _date;
	private String _year;
	private float _workday; // so ngay cong trong thang
	private int _status_work; // trang thai lam viec : binh thuong - nghi san -
								// nghi viec
	private boolean _flag_health; // trang thai tham gia bao hiem yte

	private Date _real_off_date;
	private boolean _status_card;
	private Date _return_card_date;

	private int _n_bear;
	private Date _bear_date_b;
	private Date _bear_date_e;

	OBJ_UTILITY obj_util = new OBJ_UTILITY();
	private ArrayList<Object> _list_empn_info_basic;
	SimpleDateFormat sf = obj_util.Get_format_date();

	/**
	 * So thang dang ki NS DK : thuc thi ham CHECK_EMP_BEAR()
	 */
	//
	public int Get_N_Bear() {
		return _n_bear;
	}

	public Date Get_Bear_Date_Begin() {
		return _bear_date_b;
	}

	public Date Get_Bear_Date_End() {
		return _bear_date_e;
	}

	/**
	 * Trang thai tra the BHYT khi nghi viec
	 * 
	 * @return
	 */
	public boolean Get_Status_Card() {
		return _status_card;
	}

	/**
	 * Ngay tra the BHYT DK : thuc thi CHECK_EMP_QUIT()
	 * 
	 * @return
	 */
	public Date Get_Return_Card_Date() {
		return _return_card_date;
	}

	/**
	 * Ngay thuc nghi viec
	 * 
	 * @return
	 */
	public Date Get_Real_Off_Date() {
		return _real_off_date;
	}

	public boolean Get_Flag_Health() {
		return _flag_health;
	}

	/**
	 * so ngay cong trong thang
	 * 
	 * @return
	 */
	public float Get_WORK_DAY() {
		return _workday;
	}

	/**
	 * 
	 * empsn : 0 empcn : 1 fname : 2 lname : 3 name_dept_name : 4 name_group : 5
	 * name_fact : 6
	 */

	public ArrayList<Object> List_EMP_INFO() {
		return _list_empn_info_basic;
	}

	public OBJ_EMPSN() {
		obj_util = new OBJ_UTILITY();
	}

	public int Get_Status_Work() {
		return _status_work;
	}

	public boolean Get_Reg_Bear() {
		return empsn_reg_bear;
	}

	/**
	 * Find_Emp_Info_Basic()
	 * 
	 * @param empsn
	 */
	public OBJ_EMPSN(String empsn) {
		obj_util = new OBJ_UTILITY();
		this._empsn = empsn;
		_list_empn_info_basic = Find_Emp_Info_Basic();
	}

	/**
	 * COUNT_WORKDAY()
	 * 
	 * Check_Work_Status()
	 * 
	 * @param empsn
	 * @param date
	 */
	public OBJ_EMPSN(String empsn, Date date_input_) {
		obj_util = new OBJ_UTILITY();
		String date_str = "";
		date_str = sf.format(date_input_);

		this._empsn = empsn;
		this._date = date_input_;
		_month = date_str.substring(3, 5);
		_year = date_str.substring(6, 10);
		CHECK_WORK_STATUS();
		_workday = COUNT_WORKDAY();

	}

	private void Check_N_HEALTH_R() {
		IGenericDAO<N_N_HEALTH_R, String> obj_health = Application.getApp()
				.getDao(N_N_HEALTH_R.class);
		DetachedCriteria obj_dc = DetachedCriteria.forClass(N_N_HEALTH_R.class);
		obj_dc.add(Restrictions.eq("EMPSN", _empsn));

		List<N_N_HEALTH_R> list = obj_health.findByCriteria(1, obj_dc);
		if (list.size() > 0) {
			_flag_health = true;
		} else {
			_flag_health = false;
		}
	}

	public void Set_Month(String month) {
		_month = month;
	}

	public void Set_Year(String year) {
		_year = year;
	}

	/**
	 * 
	 * empsn : 0 empcn : 1 fname : 2 lname : 3 name_dept_name : 4 name_group : 5
	 * name_fact : 6
	 */

	private ArrayList<Object> Find_Emp_Info_Basic() {
		ArrayList<Object> list = new ArrayList<Object>();
		String sql = "";

		sql = " select 	e.empsn," + "			e.empcn," + "			e.fname,"
				+ "			e.lname," + "			d.name_dept_name," + "			d.name_group,"
				+ "			d.name_fact" + " from n_employee e,n_department d "
				+ " where e.depsn = d.id_dept " + " and e.empsn = '" + _empsn
				+ "'";

		list = obj_util.Exe_sql_nfield_1row(sql, 7);

		return list;
	}

	private void CHECK_WORK_STATUS() {

		_status_work = this.empsn_BT;
		CHECK_EMP_BEAR(_empsn, _date);
		CHECK_EMP_QUIT(_date);

	}

	/**
	 * Tinh so ngay cong trong 1 thang
	 * 
	 * @return
	 */

	private float COUNT_WORKDAY() {

		String str_date = _month + "/" + _year;
		String sql = "";

		float workday = 0;

		// empsn ="05020911";
		// / tong so ngay cong trong thang

		sql = "select " + " (select nvl(sum(g.ducls+g.nucls+ "
				+ " (case when  mod(g.oth,8)=0     then  g.oth/8 "
				+ "		 when  mod(g.oth,10.4)=0  then  g.oth/(10.4) "
				+ "       else 0 " + " end) ),0) " + " From n_Data_daily g "
				+ " Where g.empsn=a.empsn and to_char(g.dates,'mm/yyyy')= "
				+ "'"
				+ str_date
				+ "'"
				+ ") + "

				+ " (select nvl(sum(g.rest_qtt),0) "
				+ " From n_Data_daily g , n_rest_kind aa "
				+ " Where g.empsn=a.empsn "
				+ " and g.rest_rs=aa.name_rest "
				+ " and aa.id_rest_sal='C01' "
				+ " and to_char(g.dates,'mm/yyyy')= "
				+ "'"
				+ str_date
				+ "'"
				+ ") "

				+ " From n_employee a "
				+ " Where a.empsn = "
				+ "'"
				+ _empsn
				+ "'"
				+ " and  a.empsn not in "
				+ " (select b.empsn "
				+ " from n_emp_quit b "
				+ " where b.real_off_date<to_date('01/"
				+ str_date + "','dd/mm/yyyy') " + " AND b.DATE_AGAIN IS NULL) ";

		Object obj = obj_util.Exe_Sql_Obj(sql);

		if (obj == null) {
			workday = 0;
		} else {
			try {
				System.out.println(obj.toString());
				workday = Float.valueOf(obj.toString());
			} catch (Exception e) {
				workday = 0;
				System.out.println("THANG NAY CHUA CO NGAY CONG");

			}
		}

		return workday;

	}

	/**
	 * 
	 * Kiem tra nghi viec + trang thai tra the BHYT + ngay tra the BHYT
	 * 
	 * @return
	 */

	private void CHECK_EMP_QUIT(Date date_intput_) { // da nghi viec MONTH/YEAR
														// => ngay thuc nghi &&
														// ngay tra the

		String date_str = sf.format(date_intput_).substring(3, 10);

		String sql =

		"SELECT\n"
				+ "    nvl(eq.REAL_OFF_DATE,to_date('01/01/0001','dd/mm/yyyy')),\n"
				+ "       nvl(eq.THE_BHYT,-1),\n"
				+ "       nvl(eq.DATE_BHYT,to_date('01/01/0001','dd/mm/yyyy'))\n"
				+ "FROM n_emp_quit eq\n" + "WHERE\n"
				+ "         to_char(eq.thang_trubh,'mm/yyyy') = '" + date_str
				+ "'" +

				"     AND eq.empsn ='" + _empsn + "'" +
				// "     AND eq.empsn ='10050065'"+

				"";

		ArrayList<Object> list_obj = new ArrayList<Object>();

		list_obj = obj_util.Exe_sql_nfield_1row(sql, 3);

		if (list_obj.size() > 0) { // TRANG THAI NGHI VIEC
			_status_work = this.empsn_NV;

			_real_off_date = (Date) list_obj.get(0);
			int t = Integer.valueOf(list_obj.get(1).toString());

			if (t == 1)
				_status_card = true;
			else
				_status_card = false;

			_return_card_date = (Date) list_obj.get(2);

		}
	}

	/**
	 * <P>
	 * Kiem tra trang thai NS tai thang hien tai
	 * <P>
	 * Neu ngay ket thuc NS > 15 cua thang thi khong con o trang thai NS
	 * 
	 */
	// *Begin 10/04/2013
	// vi co truong hop giong so the: 08111068 xin di lam som trong thang 2, ky
	// trinh T03 k lay duoc la dang nsan
	private void CHECK_EMP_BEAR(String empsn_, Date date_input_) {

		String date_now_01 = sf
				.format(OBJ_UTILITY.MONTH_NOW("01", date_input_));
		String date_now_str = date_now_01.substring(3, 10);
		String date_reg_bear = "";
		ArrayList<Object> list = new ArrayList<Object>();
		Date date_now_01_d = null;
		Date _bear_date_b_max;
		Date bear_date_e;
		try {
			date_now_01_d = sf.parse(date_now_01);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String sql = "";

		sql = " SELECT max(t.b_dates)\n" + " FROM n_time_bear t\n"
				+ " WHERE   (to_date('" + date_now_01
				+ "','dd/mm/yyyy') >= t.b_dates\n" + "         or\n"
				+ "         to_char(t.b_dates,'mm/yyyy') = '" + date_now_str
				+ "') \n" + "  AND   t.empsn ='" + empsn_ + "'";

		list = obj_util.Exe_sql_nfield_1row(sql, 1);

		if (list != null && list.size() > 0 && !"".equals(list.get(0))) {

			// _status_work = this.empsn_NS;
			_bear_date_b_max = (Date) list.get(0);

			if (_bear_date_b_max != null) {
				String sql1 = "select  t.note \n" + "from n_time_bear t \n"
						+ "where t.b_dates = to_date('"
						+ sf.format(_bear_date_b_max) + "','dd/MM/yyyy')\n"
						+ "and t.empsn ='" + empsn_ + "'";

				ArrayList<Object> list1 = new ArrayList<Object>();
				list1 = obj_util.Exe_sql_nfield_1row(sql1, 1);

				_n_bear = Integer.valueOf(list1.get(0).toString());
				bear_date_e = OBJ_UTILITY.MONTH_ROLL(_bear_date_b_max, true,
						_n_bear);

				// xac dinh ngay ket thuc nsan neu la tu 06/2013 tro di thi ngay
				// ket thuc nsan khi roll len phai - 1
				// vd: Nsan bdau:16/12/2012 khi roll len la 16/06/2012 nhung
				// bgio Nsu quy dinh la ngay kthuc nho hon 1 ngay so voi ngay
				// bat dau:NS: 16/12/2012 -> 15/06/2013
				// (Kiem tra dieu trong DB thay kthuc nsan ke tu 06/2013 tro di
				// nsu moi nhap nhu vay con nguoc lai thi ngay bat dau Nsan van
				// = ngay ket thuc Nsan
				String m_y = sf.format(bear_date_e).substring(3, 10);
				String bear_date_e_01 = "01/" + m_y;
				Date bear_date_e_01_d = null;
				try {
					bear_date_e_01_d = sf.parse(bear_date_e_01);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// thay doi ngay ket thuc nhung ngay nghi san vao van = ngay BD,
				// don thang len
				// VD : old 16/12/2012~16/06/2013 : Ngay NSvao la 16/06/2013
				// new 16/12/2012~15/06/2013 : Ngay NSVao la 16/06/2013
				// vi vay se ko can thay doi dk ngay ket thuc o day, HA,
				// 08/07/2013
				/*
				 * try { if(bear_date_e_01_d.equals(sf.parse("01/06/2013")) ||
				 * bear_date_e_01_d.after(sf.parse("01/06/2013")) ) {
				 * bear_date_e = DateUtils.addDay(bear_date_e,-1); }else {
				 * bear_date_e = bear_date_e; } } catch (ParseException e) { //
				 * TODO Auto-generated catch block e.printStackTrace(); }
				 */

				String bear_date_e_str = sf.format(bear_date_e);

				String m_y_b_max = sf.format(_bear_date_b_max).substring(3, 10);
				String bear_date_b_max_str = "01/" + m_y_b_max;
				String sql2 = "";

				sql2 = " SELECT t.b_dates,  t.note\n"
						+ " FROM n_time_bear t\n"
						+
						// " WHERE   ((to_date('"+date_now_01+"','dd/mm/yyyy') >= t.b_dates\n"
						// +
						" WHERE   ((to_date('"
						+ date_now_01
						+ "','dd/mm/yyyy') >= to_date('"
						+ bear_date_b_max_str
						+ "','dd/MM/yyyy')\n"
						+ "         and to_date('"
						+ bear_date_e_str
						+ "','dd/MM/yyyy') >= to_date('"
						+ date_now_01
						+ "','dd/mm/yyyy')   and to_char(t.b_dates,'MM/yyyy') = '"
						+ m_y_b_max + "' )\n" + "         or\n"
						+ "         (to_char(t.b_dates,'mm/yyyy') = '"
						+ date_now_str + "') )\n" + "  AND   t.empsn ='"
						+ empsn_ + "'";

				ArrayList<Object> list2 = new ArrayList<Object>();
				list2 = obj_util.Exe_sql_nfield_1row(sql2, 2);

				if (list2 != null && list2.size() > 0) {

					_status_work = this.empsn_NS;
					_bear_date_b = (Date) list2.get(0);
					_bear_date_e = bear_date_e;

					date_reg_bear = sf.format(OBJ_UTILITY.MONTH_NOW("01",
							_bear_date_b));

					if (date_reg_bear.equals(date_now_01)) { // xet bat dau dang
																// ky NS hay
																// dang NS
						empsn_reg_bear = true;
					} else {
						empsn_reg_bear = false;
					}
					_n_bear = Integer.valueOf(list2.get(1).toString());
				}
			}
		}

	}

	/*
	 * private void CHECK_EMP_BEAR(String empsn_,Date date_input_){ //Old
	 * 10/04/2013 N tam thoi dong lai de viet lai ham CHECK_EMP_BEAR(Ngan) // vi
	 * co truong hop giong so the: 08111068 xin di lam som trong thang 2, ky
	 * trinh T03 k lay duoc la dang nsan
	 * 
	 * String date_now_01 = sf.format(OBJ_UTILITY.MONTH_NOW("01", date_input_));
	 * String date_now_str = date_now_01.substring(3,10); String date_reg_bear =
	 * ""; ArrayList<Object> list = new ArrayList<Object>();
	 * 
	 * String sql = "";
	 * 
	 * 
	 * sql = " SELECT t.b_dates,  t.note\n" + " FROM n_time_bear t\n" +
	 * " WHERE   ((to_date('"+date_now_01+"','dd/mm/yyyy') >= t.b_dates\n" +
	 * "         and  t.e_dates >= to_date('"
	 * +date_now_01+"','dd/mm/yyyy')   )\n" + "         or\n" +
	 * "         (to_char(t.b_dates,'mm/yyyy') = '"+date_now_str+"') )\n" +
	 * "  AND   t.empsn ='"+empsn_+"'";
	 * 
	 * 
	 * 
	 * 
	 * list = obj_util.Exe_sql_nfield_1row(sql, 2);
	 * 
	 * if(list != null && list.size() > 0){
	 * 
	 * 
	 * _status_work = this.empsn_NS; _bear_date_b = (Date) list.get(0);
	 * 
	 * date_reg_bear = sf.format(OBJ_UTILITY.MONTH_NOW("01", _bear_date_b));
	 * 
	 * if(date_reg_bear.equals(date_now_01)){ // xet bat dau dang ky NS hay dang
	 * NS empsn_reg_bear = true; }else{ empsn_reg_bear = false; } _n_bear =
	 * Integer.valueOf(list.get(1).toString()); }
	 * 
	 * }
	 */
	// --------------------------------------------------------------
	// --* 08/03/2013_(Ngan)_them phan Nsan < 6T(de khi Nsu gui file excel theo
	// doi tay nhung nguoi nay thi cap nhat DK_NS = 1 trong N_TIME_BEAR tuong
	// ung voi dong nsan hien tai)
	public boolean KT_NS_DUOI_6T(String empsn_, Date date_input_) {

		boolean flag = false;
		String date_now_01 = sf
				.format(OBJ_UTILITY.MONTH_NOW("01", date_input_));
		String date_now_str = date_now_01.substring(3, 10);
		String date_reg_bear = "";
		ArrayList<Object> list = new ArrayList<Object>();

		String sql = "";

		sql = " SELECT t.b_dates,    t.note\n" + " FROM n_time_bear t\n"
				+ " WHERE   ((to_date('" + date_now_01
				+ "','dd/mm/yyyy') >= t.b_dates\n"
				+ "         and  t.e_dates >= to_date('" + date_now_01
				+ "','dd/mm/yyyy')   )\n" + "         or\n"
				+ "         (to_char(t.b_dates,'mm/yyyy') = '" + date_now_str
				+ "') )\n" + "			and t.DK_NS = 1 " + "  AND   t.empsn ='"
				+ empsn_ + "'";

		list = obj_util.Exe_sql_nfield_1row(sql, 2);

		if (list != null && list.size() > 0) {
			flag = true; // NGHI THAI SAN < 6T
		} else {
			flag = false; // NS
		}

		return flag;
	}

	// _______________________________________________________________________
	// Function Utility for Empsn

	public Date Get_Date_Labor_First(String empsn) {
		Date date = null;
		String sql = " SELECT t.date_s,t.* FROM n_labour t "
				+ " WHERE t.empsn = '" + empsn + "' " + " AND t.times = 1";

		date = (Date) obj_util.Exe_Sql_Obj(sql);

		return date;
	}

	/**
	 * Lay don vi tinh bao hiem....
	 * 
	 * @param empsn
	 * @param date_input
	 * @return
	 */
	// *Cau nay dang ap dung de lay don vi cho Nhan su chay BHYT vi lay theo
	// bang n_n_health_r da loai bo nghi viec tai day rui
	public String Get_depsn(String empsn, Date date_input) {

		String date_iput_str = sf.format(date_input);
		String sql_dv = "select lay_donvi_theo_ngay_ngan('" + empsn + "','"
				+ date_iput_str + "') as depsn from dual";
		List<String> madvs = obj_util.Exe_Sql_String(sql_dv);
		String madv = "";
		if (madvs != null && madvs.size() > 0) {
			madv = madvs.get(0).toString();
		}
		return madv;
	}

	// *Begin 20/09/2012 lam them Get_depsn_1 de loai bo nhung so the nghi viec
	// truoc thoi gian dang xly
	// Hien tai ap dung cau nay de lay don vi cho Ktoan chay tam ung
	public String Get_depsn_1(String empsn, Date date_input) {
		//
		String date_iput_str = sf.format(date_input);
		String sql_dv = "select lay_donvi_theo_ngay_ngan_1('" + empsn + "','"
				+ date_iput_str + "') as depsn from dual";
		List<String> madvs = obj_util.Exe_Sql_String(sql_dv);
		String madv = "";
		if (madvs != null && madvs.size() > 0) {
			madv = madvs.get(0).toString();
		}
		return madv;
	}

	// *End 20/09/2012 lam them Get_depsn_1 de loai bo nhung so the nghi viec
	// truoc thoi gian dang xly

	public Long Get_Retain_Salary(String empsn, Date date_input) {
		// LAY TU DONG TU BANG LUONG ATT--TAM THOI K SDUNG
		long rs = 0;
		String str_date = sf.format(date_input);
		java.sql.Date date_sql_ = new java.sql.Date(date_input.getTime());

		/*
		 * String month = str_date.substring(3, 5); String year =
		 * str_date.substring(6,10);
		 */
		Connection con = Application.getApp().getConnection();
		String sql = "{?=call RETAIN_SALARY_BY_DATE_OFF(?,?)}";

		try {
			CallableStatement stm = con.prepareCall(sql);
			stm.setString(2, empsn);
			stm.setDate(3, date_sql_);

			stm.registerOutParameter(1, Types.INTEGER);

			stm.execute();
			rs = Long.valueOf(stm.getInt(1));

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}

	// ***Begin 11/01/2012 goi pro insert n_action_daily_Ngan
	// *10/12/2012 k su dung ham nay nua ma sdung FvLogger(src/dean/fv/FvLogger)
	// de lay ngay tu may server k lay tu may oracle nua (vi gio o may nay bi
	// sai)
	public void Insert_N_Action_Daily(String ma_user, String TableName,
			String ActionName, String Empsn, String Note) {

		Connection con = Application.getApp().getConnection();
		String sql = "{call N_HB_ACTION_DAILY(?,?,?,?,?)}";

		try {
			CallableStatement stm = con.prepareCall(sql);
			stm.setString(1, ma_user);
			stm.setString(2, TableName);
			stm.setString(3, ActionName);
			stm.setString(4, Empsn);
			stm.setString(5, Note);

			stm.execute();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// End 11/01/2012 goi pro insert n_action_daily

	public Long Get_Salary_Total_By_Month(String empsn, Date date_input) {

		long rs = 0;
		String str_date = sf.format(date_input);

		String month = str_date.substring(3, 5);
		String year = str_date.substring(6, 10);
		Connection con = Application.getApp().getConnection();
		// String sql = "{call C5_CALCULATE_SALARY_N_YT(?,?,?,?)}";
		String sql = "{call C5_CALCULATE_SALARY_N_YT(?,?,?,?)}";

		try {
			CallableStatement stm = con.prepareCall(sql);
			stm.setString(2, year);
			stm.setString(3, empsn);

			stm.registerOutParameter(4, Types.INTEGER);

			stm.execute();
			rs = Long.valueOf(stm.getInt(4));

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}

	public long Get_Salary_Total_By_Month_for_NV(String empsn, Date date_input) {
		SimpleDateFormat sf_ = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date date_para_sql = null;
		Date date_dot_tv = null;
		long rs = 0;
		String str_date = sf.format(date_input);

		date_dot_tv = Get_Dot_tv(empsn, date_input);

		try {
			date_para_sql = java.sql.Date.valueOf(sf_.format(date_dot_tv));
		} catch (Exception e) {
			System.out.println("So the " + empsn + " Khong co date_dot_tv");

		}

		String month = str_date.substring(3, 5);
		String year = str_date.substring(6, 10);
		Connection con = Application.getApp().getConnection();
		String sql = "{call C5_CALCULATE_SALARY_OFF_YT(?,?,?,?,?)}";

		try {
			CallableStatement stm = con.prepareCall(sql);
			stm.setString(1, month);
			stm.setString(2, year);
			stm.setString(3, empsn);
			stm.setDate(4, date_para_sql);
			stm.registerOutParameter(5, Types.INTEGER);

			stm.execute();
			rs = Long.valueOf(stm.getInt(5));

		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}

	public long Get_Salary_Basic(String empsn, Date date_input) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date_input);

		// SAI O DAY
		System.out.println(sf.format(ca.getTime()));
		String day = String.valueOf(ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String date_str = "";
		Date date = OBJ_UTILITY.MONTH_NOW(day, date_input);
		date_str = sf.format(date);

		String sql = "";

		long salary = 0;

		sql = "SELECT t.bsalary\n" + "FROM n_basic_salary t\n" + "WHERE\n"
				+ "         t.empsn ='" + empsn + "'\n"
				+ "     AND t.dates =(Select Max(tt.dates)\n"
				+ "                          From n_basic_salary tt\n"
				+ "                          Where tt.empsn=t.empsn\n"
				+ "                          And  tt.dates<=to_Date('"
				+ date_str + "','dd/mm/yyyy')\n" + "                   )";

		Object obj = new Object();

		obj = obj_util.Exe_Sql_Obj(sql);

		if (obj == null) {
			salary = 0;
			System.out.println("Not Salary!");
		} else {
			try {
				salary = Long.valueOf(obj.toString());
			} catch (Exception e) {
				salary = 0;
			}
		}

		return salary;

	}

	public String Get_Dept(String empsn) {
		String name_dept = "";
		return name_dept;
	}

	// *Begin 21/08/2012 lay ngay gio hien tai trong oracle (Ngan)
	public Timestamp GetDateTimeOracle() {
		String sql = "SELECT TO_CHAR(SYSDATE,'DD/MM/YYYY HH24:MI:SS') FROM DUAL";
		Object obj = obj_util.Exe_Sql_Obj(sql);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			Date d = sdf.parse(obj.toString());
			return new java.sql.Timestamp(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// *End 21/08/2012 lay ngay gio hien tai trong oracle (Ngan)

	public Date Get_Dot_tv(String empsn, Date date_input) {
		Date date = null;
		String str_date = sf.format(date_input);

		String month = str_date.substring(3, 5);
		String year = str_date.substring(6, 10);
		String sql = " SELECT t.thang_trubh FROM n_emp_quit t "
				+ // thay vi lay dot_tv gio lay thang_trubh vi k co 2 record
					// cung 1 thang tru BH,
				" WHERE t.empsn = '" + empsn
				+ "' "
				+ // luoi doi lai ten get thang_tru_bh nen van de Get_dot_tv
					// nhung thuc ra la lay thang_tru_bh
				" AND to_char(t.thang_trubh,'mm/yyyy')='" + month + "/" + year
				+ "'";// Sau nay dl hoan chinh thi sua lai cho khop voi ten
						// goi(Ngan ghi chu 10/08/2011)

		try {
			System.out.println(obj_util.Exe_Sql_Obj(sql));
			date = sf.parse(sf.format(obj_util.Exe_Sql_Obj(sql)));
		} catch (ParseException e) {
			System.out.println(date);
			e.printStackTrace();
		}

		return date;
	}

	// ////////////////////////

	public static EmployeeDepartment getEmployeeInfo(String empsn) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long Get_Money_Emp_Job(String empsn, Date date_input_) {
		long money_ = 0;

		return money_;
	}

	// *Begin 14/07/2012 kiem tra xem neu la user co id_limit thuoc admin(000)
	// or KE TOAN LUONG 1(L01), or KE TOAN LUONG 2(l02)
	// or KE TOAN XUONG(ABC) thi la true, nguoi lai false
	// Hien tai sdung ham nay de cho phep chi co KT la khoa & mo khoa tca
	public boolean check_user_KToan(String ten_user) {
		boolean flag = false;
		String sql = "select t.id_limit from dspb02 t\n"
				+ "where t.id_limit in ('L01','L02','ABC')\n"
				+ "and t.pb_userid = '" + ten_user + "'";

		obj_util = new OBJ_UTILITY();
		Object obj = obj_util.Exe_Sql_Obj(sql);

		if (obj == null) {
			flag = false;

		} else {
			if (obj.equals("ABC") || obj.equals("L01") || obj.equals("L02")) {
				flag = true;

			} else {
				flag = false;

			}
		}

		return flag;
	}

	public boolean check_user_Admin(String ten_user)// Tach ra vi neu gop chung
													// 000 voi check_user_Ktoan
													// o tren
	{// thi khi k cho Ke toan hien thi chuc nao thi dong thoi admin cung k dc-->
		// ma admin thi co toan quyen
		String sql = "select t.id_limit from dspb02 t\n"
				+ "where t.id_limit in ('000')\n" + "and t.pb_userid = '"
				+ ten_user + "'";

		obj_util = new OBJ_UTILITY();
		Object obj = obj_util.Exe_Sql_Obj(sql);

		if (obj == null) {
			return false;

		} else {
			if (obj.equals("000")) {
				return true;
			} else {
				return false;
			}
		}

	}

	// *End 14/07/2012

	// 21/02/2012: Check_lock: Kiem tra pb_lock(tuong ung LOCKED trong
	// N_USER_LIST: Y khoa chuc nang xly dlieu
	public boolean check_lock(String ten_user) {
		String sql = "select t.pb_lock\n" + "from dspb02 t\n"
				+ "WHERE t.pb_userid  = '" + ten_user + "'";

		ObjUtility obj_util = new ObjUtility();
		Object obj = obj_util.Exe_Sql_Obj(sql);

		if (obj == null) {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Bạn chưa được cấp quyền xử lý dữ liệu");
			return false;
		} else {
			if (obj.equals("Y")) {
				Application.getApp().showMessageDialog(
						MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
						"Bạn đã bị khóa chức năng xử lý dữ liệu.");
				return false;
			} else {
				return true;
			}
		}

	}

	// 05/04/2012 Ngan
	public boolean check_lock_month(String sothe, String xuong, String nhom,
			String tendv, String ma_dv, Date date_, String con_dept,
			String ma_user, RadioButton f1, RadioButton f2, RadioButton f3,
			RadioButton f5, RadioButton f6, RadioButton khac)// con_dept: lay
																// dvi la depsn
																// hay la
																// depsn_temp
	{
		String date_str = sf.format(date_);
		String month = date_str.substring(3, 5);
		String year = date_str.substring(6, 10);
		boolean flag = false;

		String sql = "select count(t.Tabname)\n" + "from attlock t\n"
				+ " where t.tabname = 'ATT" + year + "" + month + "'"
				+ " and  t.tablock = 'Y'";

		OBJ_UTILITY obj_util = new OBJ_UTILITY();
		Object obj = obj_util.Exe_Sql_Obj(sql);

		if (Long.valueOf(obj.toString()) == 0) {
			ArrayList<String> list_dept = new ArrayList<String>();
			list_dept = obj_util.getListDept(sothe, xuong, nhom, tendv, ma_dv,
					con_dept, ma_user, f1, f2, f3, f5, f6, khac);

			for (String dept : list_dept)// chi can 1 dvi khoa la k the nhap ca
											// nhom or ca xuong co dvi do truc
											// thuoc
			{
				String sql1 = "select count(t.depsn)  from n_fact_transfer_lock t\n"
						+ " where t.year = '"
						+ year
						+ "' and t.month = '"
						+ month
						+ "'"
						+ " and t.locked = 1\n"
						+ " and t.depsn = '" + dept + "'";

				Object obj1 = obj_util.Exe_Sql_Obj(sql1);

				if (Long.valueOf(obj1.toString()) == 0) {
					flag = true;
					continue;
				} else {
					OBJ_UTILITY
							.ShowMessageError("Dữ liệu đã khóa, không thể thao tác");
					flag = false;
					return flag;
				}

			}

		} else {
			OBJ_UTILITY.ShowMessageError("Dữ liệu đã khóa, không thể thao tác");
			flag = false;
			return flag;
		}

		return flag;
	}

	// BEGIN 15/05/2012 KIEM TRA KHOA TCA
	public boolean check_lock_overtime(String sothe, String xuong, String nhom,
			String tendonvi, String id_dept, ArrayList<Date> list_date_ot,
			String ma_user, RadioButton f1, RadioButton f2, RadioButton f3,
			RadioButton f5, RadioButton f6, RadioButton khac) {// Quy tac: *
																// them moi mo
																// khoa don vi,
																// chinh sua mo
																// khoa so the
		boolean flag = false;
		ArrayList<String> list_dept_temp = new ArrayList<String>();
		for (Date date_ : list_date_ot) {
			String date_str = sf.format(date_);
			if (!sothe.equals(""))// **if nhap vao la so the
			{
				String sql = "select  COUNT(T.EMPSN) from N_REGISTER_OVERTIME T\n"
						+ " WHERE T.EMPSN = '"
						+ sothe
						+ "'"
						+ " AND T.DATE_OVER = TO_DATE('"
						+ date_str
						+ "','DD/MM/YYYY')" + " AND T.LOCKED = 'Y'";

				Object obj = obj_util.Exe_Sql_Obj(sql);

				if (Long.valueOf(obj.toString()) == 0)// chua khoa dlieu tca
														// trong
														// n_register_overtime
				{
					String sql1 = "select T.DEPSN_TEMP  from N_EMPLOYEE  t,N_DEPARTMENT D\n"
							+ " WHERE T.DEPSN_TEMP = D.ID_DEPT\n"
							+ " AND T.EMPSN = '" + sothe + "'";

					Object obj1 = obj_util.Exe_Sql_Obj(sql1);
					String depsn_temp = obj1.toString();
					if (obj1 == null) {
						OBJ_UTILITY.ShowMessageError("Không tìm thấy đơn vị");
						flag = false;
						return flag;
					} else// DEPSN_TEMP <> ''
					{
						String sql2 = "select  COUNT(T.DEPSN) from N_DEPT_LOCK_OT T\n"
								+ " WHERE T.DEPSN = '"
								+ depsn_temp
								+ "'"
								+ " AND T.DATES = TO_DATE('"
								+ date_str
								+ "','DD/MM/YYYY')\n" + " AND T.STATUS = '1'";

						Object obj2 = obj_util.Exe_Sql_Obj(sql2);
						if (Long.valueOf(obj2.toString()) == 0)// Chua khoa du
																// lieu trong
																// n_dept_lock_ot
						{
							flag = true;
						} else// da khoa dlieu cua don vi st truc thuoc
						{ // * Khoa dvi nhung neu sthe k khoa thi co the xoa sua
							String sql3 = "select  COUNT(T.EMPSN) from N_REGISTER_OVERTIME T\n"
									+ " WHERE T.EMPSN = '"
									+ sothe
									+ "'"
									+ " AND T.DATE_OVER = TO_DATE('"
									+ date_str
									+ "','DD/MM/YYYY')" + " AND T.LOCKED = 'N'";
							Object obj3 = obj_util.Exe_Sql_Obj(sql3);
							if (Long.valueOf(obj3.toString()) == 0)// so the
																	// chua nhap
																	// tca cho
																	// ngay
																	// tuong ung
							{
								OBJ_UTILITY.ShowMessageError("Ngày tăng ca: "
										+ date_str + " của đơn vị: "
										+ depsn_temp + " Kế Toán đã khóa");
								flag = false;
								return flag;
							} else {
								flag = true;
							}
						}
					}

				} else {
					OBJ_UTILITY
							.ShowMessageError("Kế Toán đã khóa dữ liệu tăng ca ngày:"
									+ date_str + " của st: " + sothe);
					flag = false;
					return flag;
				}

			}// **else if nhap vao la xuong , nhom ,dvi
			else {
				list_dept_temp = obj_util.getListDept("", xuong, nhom,
						tendonvi, id_dept, "DEPSN_TEMP", ma_user, f1, f2, f3,
						f5, f6, khac);

				for (String dept_temp : list_dept_temp) {
					String sql = "SELECT COUNT(T.DEPSN)  FROM  N_DEPT_LOCK_OT  t\n"
							+ " WHERE  t.DEPSN  =  '"
							+ dept_temp
							+ "'\n"
							+ " AND T.DATES = TO_DATE('"
							+ date_str
							+ "','DD/MM/YYYY')\n" + " AND T.STATUS = 1";

					Object obj = obj_util.Exe_Sql_Obj(sql);

					if (Long.valueOf(obj.toString()) == 0) {
						flag = true;
						continue;
					} else {
						OBJ_UTILITY.ShowMessageError("Ngày tăng ca: "
								+ date_str + " của đơn vị: " + dept_temp
								+ " Kế Toán đã khóa");
						flag = false;
						return flag;
					}

				}
			}
		}
		return flag;
	}

	// END 15/05/2012 KIEM TRA KHOA TCA

	// 12/03/2012 Ngan
	public boolean Kt_vungqly_khi_nhap_st(String input_emp, String ma_user,
			String con_depsn) {
		N_EMPLOYEE data = new N_EMPLOYEE();
		IGenericDAO dao = Application.getApp().getDao(N_EMPLOYEE.class);

		IGenericDAO<N_USER_LIMIT, String> Dao_USER_LIMIT = Application.getApp()
				.getDao(N_USER_LIMIT.class);
		DetachedCriteria objDC_USER_LIMIT = DetachedCriteria
				.forClass(N_USER_LIMIT.class);

		if (input_emp.equals("")) {
			OBJ_UTILITY.ShowMessageError("Nhập số thẻ");
			return false;
		}

		if (input_emp.length() == 8) {

			List list = dao
					.find(1,
							"select a.FNAME, a.LNAME, b.NAME_DEPT,b.ID_DEPT,a.USER_MANAGE_ID from N_EMPLOYEE a, N_DEPARTMENT b where a."
									+ con_depsn + "=b.ID_DEPT and a.EMPSN=?",
							new Object[] { input_emp });
			if (list.size() > 0) {

				Object[] obj = (Object[]) list.get(0);

				String ma_ql_old = obj[4].toString();

				objDC_USER_LIMIT.add(Restrictions.eq("MA_USER", ma_user));
				objDC_USER_LIMIT.add(Restrictions.eq("MA_QL", ma_ql_old));
				List<N_USER_LIMIT> dataList = Dao_USER_LIMIT.findByCriteria(1,
						objDC_USER_LIMIT);
				if (dataList.size() <= 0) {
					Application.getApp().showMessageDialog(
							MessageDialog.TYPE_ERROR
									+ MessageDialog.CONTROLS_OK,
							"Bạn không có quyền thao tác số thẻ: " + input_emp);

					return false;
				} else {
					return true;
				}
			} else// Neu so the khong co trong CSDL
			{

				Application.getApp().showMessageDialog(
						MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
						"Không tìm thấy thông tin");
				return false;
			}

		} else if (input_emp.length() < 8) {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Số thẻ phải đủ 8 ký tự");
			return false;
		}

		return true;
	}

	// 12/03/2012 ngan
	public boolean Kt_vungqly_khi_nhap_dv(String input_dep, String ma_user,
			String con_depsn) {
		String get_maql = "";
		String sql = "select DISTINCT T.USER_MANAGE_ID\n"
				+ "from n_employee t,n_department d\n" + "where t." + con_depsn
				+ " = d.id_dept\n" + "and d.id_dept = '" + input_dep + "'";

		OBJ_UTILITY obj_Util = new OBJ_UTILITY();
		Object rs = new Object();
		rs = obj_Util.Exe_Sql_Obj(sql);

		if (rs == null) {
			OBJ_UTILITY.ShowMessageError("Lỗi.. Đơn vị: '" + input_dep
					+ "' là đơn vị mới chưa được cấp quyền quản lý");
			return false;
		}
		if (!rs.equals("")) {
			get_maql = rs.toString();
			if (get_maql.length() > 3) {
				OBJ_UTILITY.ShowMessageError("Lỗi.. Đơn vị: '" + input_dep
						+ "' chưa được cấp quyền quản lý");
				return false;
			}
		} else {
			OBJ_UTILITY.ShowMessageError("Lỗi.. Đơn vị: '" + input_dep
					+ "' chưa được cấp quyền quản lý");
			return false;
		}
		IGenericDAO<N_USER_LIMIT, String> Dao_USER_LIMIT = Application.getApp()
				.getDao(N_USER_LIMIT.class);
		DetachedCriteria objDC_USER_LIMIT = DetachedCriteria
				.forClass(N_USER_LIMIT.class);
		objDC_USER_LIMIT.add(Restrictions.eq("MA_USER", ma_user));
		objDC_USER_LIMIT.add(Restrictions.eq("MA_QL", get_maql));
		List<N_USER_LIMIT> dataList = Dao_USER_LIMIT.findByCriteria(1,
				objDC_USER_LIMIT);
		if (dataList.size() <= 0) {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Bạn không có quyền thao tác đơn vị " + input_dep);
			return false;
		}

		return true;
	}

	// 03/04/2012 Kt vung quan ly khi nhap xuong or nhom or ten don vi or ma_dv
	public String Kt_vungqly_khi_nhap_xuong_nhom_tendvi(String xuong,
			String nhom, String tendonvi, String con_dept, String id_dept,
			String ma_user, RadioButton f1, RadioButton f2, RadioButton f3,
			RadioButton f5, RadioButton f6, RadioButton khac)// co 'con_dept' vi
																// co luc ket
																// theo depsn or
																// depsn_temp
																// trong
																// employee(botay)
	{
		String list = "";
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String get_maql = "";
		String sql = "";
		sql = "select DISTINCT d.id_dept\n"
				+ "from n_employee t,n_department d\n" + "where t." + con_dept
				+ " = d.id_dept\n";
		// "and d.id_dept = '"+input_dep+"'";
		if ((!xuong.equals("") && xuong.equals("NV")) || !id_dept.equals("")
				&& id_dept.equals("00000")) {
			sql = "select DISTINCT t.empsn "
					+ "from n_employee t,n_department d\n" + "where t."
					+ con_dept + " = d.id_dept\n";
		}

		if (!xuong.equals("") && nhom.equals("") && tendonvi.equals("")
				&& id_dept.equals("")) {
			sql1 = " and d.NAME_FACT= '" + xuong + "'";
		} else if (!xuong.equals("") && !nhom.equals("") && tendonvi.equals("")
				&& id_dept.equals("")) {
			sql1 = " and d.NAME_FACT= '" + xuong + "' and d.NAME_GROUP = '"
					+ nhom + "'";
		} else if (!xuong.equals("") && !nhom.equals("")
				&& !tendonvi.equals("") && id_dept.equals("")) {
			sql1 = " and d.NAME_FACT= '" + xuong + "' and d.NAME_GROUP = '"
					+ nhom + "' and d.NAME_DEPT_NAME = '" + tendonvi + "'";
		} else if (xuong.equals("") && nhom.equals("") && tendonvi.equals("")
				&& !id_dept.equals("")) {
			sql1 = " and d.id_dept = '" + id_dept + "'";
		} else if (!xuong.equals("") && !nhom.equals("")
				&& !tendonvi.equals("") && !id_dept.equals("")) {
			sql1 = " and d.id_dept = '" + id_dept + "'";
		}

		sql2 = " and t.USER_MANAGE_ID in (SELECT MA_QL FROM N_USER_LIMIT WHERE MA_USER= '"
				+ "" + ma_user + "' and MA_QL=USER_MANAGE_ID)";

		sql3 = check_fact_FVL_rb(f1, f2, f3, f5, f6, khac, "d");

		sql = sql + sql1 + sql2 + sql3;

		OBJ_UTILITY obj_Util = new OBJ_UTILITY();
		List<String> rs = obj_Util.Exe_Sql_String(sql);

		if (rs.isEmpty()) { // Khong co quyen thao tac
			list = "NO";
		} else {
			list = sql;
		}
		return list;
	}

	// * Begin 11/06/2012 kiem tra phep trong n_rest_detail voi total = 1
	public boolean Kt_Phep(String sothe, Date date_)// gioi han 1 so form khong
													// cho nhap khi da nhap phep
													// total = 1
	{
		IGenericDAO<N_REST_DETAIL, N_REST_DETAILPk> Dao_Rest = Application
				.getApp().getDao(N_REST_DETAIL.class);
		N_REST_DETAILPk pk = new N_REST_DETAILPk(sothe, date_);
		N_REST_DETAIL Data_Rest = Dao_Rest.findById(pk);

		if (Data_Rest != null && Data_Rest.getTOTAL() == 1) {
			rest_kind = Data_Rest.getREST_KIND();
			return false;
		}

		return true;
	}

	public boolean Kt_Lv_7H(String sothe, Date date_)// gioi han 1 so form khong
														// cho nhap khi da nhap
														// phep total = 1
	{
		IGenericDAO<N_REGISTER_SHIFT, N_REGISTER_SHIFTPk> Dao_Shift = Application
				.getApp().getDao(N_REGISTER_SHIFT.class);
		N_REGISTER_SHIFTPk pk = new N_REGISTER_SHIFTPk(sothe, date_);
		N_REGISTER_SHIFT Data_Shift = Dao_Shift.findById(pk);

		if (Data_Shift != null && Data_Shift.getNOTE().equals("7H")) {
			note_7H = "7H";
			return false;
		}

		return true;
	}

	// ** Begin 28/08/2012 xoa phep
	public void XOA_PHEP(String empsn, Date date_, String ma_user) {
		IGenericDAO<N_REST_DETAIL, N_REST_DETAILPk> Dao_Rest = Application
				.getApp().getDao(N_REST_DETAIL.class);
		N_REST_DETAILPk pk = new N_REST_DETAILPk(empsn, date_);
		N_REST_DETAIL data = Dao_Rest.findById(pk);

		String date_str = sf.format(date_);

		if (data == null) {
			return;
		} else {
			String sql = "DELETE FROM N_REST_DETAIL T\n" + " WHERE T.EMPSN = '"
					+ empsn + "'" + " AND T.DATE_OFF = TO_DATE('" + date_str
					+ "','dd/MM/yyyy')";

			obj_util.Exe_Update_Sql(sql);
			String strnote = "Xoa ngay phep: " + date_str + ",Loai phep: "
					+ data.getREST_KIND() + ", Loai PN: " + data.getREST_TYPE()
					+ ", So ngay phep: " + data.getTOTAL() + ", Nghi tu gio: "
					+ data.getTIN() + " den gio: " + data.getTOUT();
			Insert_N_Action_Daily(ma_user, "N_REST_DETAIL", "DELETE_U", empsn,
					strnote);
			PN_CONLAI(empsn, date_);
		}
	}

	// ** End 28/08/2012 xoa phep

	// ***Begin 27/03/2012 goi pro pn(de tinh phep nam con lai)_Ngan
	public void PN_CONLAI(String sothe, Date date_) {

		String str_date = sf.format(date_);
		String year = str_date.substring(6, 10);

		Connection con = Application.getApp().getConnection();
		String sql = "{call pn(?,?)}";

		try {
			CallableStatement stm = con.prepareCall(sql);
			stm.setString(1, sothe);
			stm.setString(2, year);

			stm.execute();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// ***End 27/03/2012 goi pro pn(de tinh phep nam con lai)_Ngan

	// *Begin 13/04/2012 kiem tra viec chon xuong ma chon rieng
	// f1,f2...khac(radiobutton)
	public String check_fact_FVL_rb(RadioButton f1, RadioButton f2,
			RadioButton f3, RadioButton f5, RadioButton f6, RadioButton khac,
			String alias_table) {
		String list = "";
		String sql = "";

		if (f1.isSelected()) {
			sql = " AND " + alias_table + ".NAME_GROUP LIKE 'F1%' AND "
					+ alias_table + ".NAME_GROUP NOT LIKE 'F12%'";
		} else if (f2.isSelected()) {
			sql = " AND " + alias_table + ".NAME_GROUP LIKE 'F2%' ";
		} else if (f3.isSelected()) {
			sql = " AND " + alias_table + ".NAME_GROUP LIKE 'F3%' ";
		} else if (f5.isSelected()) {
			sql = " AND " + alias_table + ".NAME_GROUP LIKE 'F5%' ";
		} else if (f6.isSelected()) {
			sql = " AND " + alias_table + ".NAME_GROUP LIKE 'F6%' ";
		} else if (khac.isSelected()) {
			sql = " AND " + alias_table + ".NAME_GROUP NOT LIKE 'F1%'"
					+ " AND " + alias_table + ".NAME_GROUP NOT LIKE 'F2%'"
					+ " AND " + alias_table + ".NAME_GROUP NOT LIKE 'F3%'"
					+ " AND " + alias_table + ".NAME_GROUP NOT LIKE 'F5%'"
					+ " AND " + alias_table + ".NAME_GROUP NOT LIKE 'F6%'";

		}

		list = sql;
		return list;
	}

	// *End 13/04/2012 kiem tra viec chon xuong ma chon rieng f1,f2...khac
	// *14/04/2012 tim theo so the or xuong,nhom,donvi
	public String find_fact_emp(String empsn, String fact, String group,
			String dept, String id_dept, String alias_table,
			String alias_table1, String ma_user, RadioButton f1,
			RadioButton f2, RadioButton f3, RadioButton f5, RadioButton f6,
			RadioButton khac)// alias_table1 tim theo so the
	{
		String list = "";
		String sql = "";
		String sql2 = "";

		if (!empsn.equals("")) {
			sql = " AND " + alias_table1 + ".EMPSN = '" + empsn + "'";
		} else if (!fact.equals("") && group.equals("") && dept.equals("")) {
			sql = " AND " + alias_table + ".NAME_FACT LIKE '%" + fact.trim()
					+ "'\n";
		} else if (!fact.equals("") && !group.equals("") && dept.equals("")) {
			sql = " AND " + alias_table + ".NAME_FACT LIKE '%" + fact.trim()
					+ "'\n" + " AND  " + alias_table + ".NAME_GROUP  LIKE '%"
					+ group.trim() + "'\n";
		} else if (!fact.equals("") && !group.equals("") && !dept.equals("")) {
			sql = " AND " + alias_table + ".NAME_FACT LIKE '%" + fact.trim()
					+ "'\n" + " AND  " + alias_table + ".NAME_GROUP  LIKE '%"
					+ group.trim() + "'\n" + " AND  " + alias_table
					+ ".NAME_DEPT_NAME  LIKE '%" + dept.trim() + "'\n";
		} else if (!fact.equals("") && group.equals("") && !dept.equals("")
				&& !id_dept.equals("")) {
			sql = " AND " + alias_table + ".NAME_FACT LIKE '%" + fact.trim()
					+ "'\n" + " AND  " + alias_table + ".ID_DEPT  ='"
					+ id_dept.trim() + "'\n";
		}

		String sql1 = " AND "
				+ alias_table1
				+ ".USER_MANAGE_ID IN (SELECT MA_QL FROM N_USER_LIMIT WHERE MA_USER= '"
				+ "" + ma_user + "' AND MA_QL=USER_MANAGE_ID)";

		sql2 = check_fact_FVL_rb(f1, f2, f3, f5, f6, khac, alias_table);

		list = sql + sql1 + sql2;
		return list;
	}

	// ***Xuat theo ngay,thang or nam
	public String select_ngay_thang_nam(RadioButton date, RadioButton month,
			RadioButton year, String dmy, String dmy_to, String my, String y,
			String alias_table, String get_col) {
		String list = "";
		String sql = "";

		if (date.isSelected()) {
			if (dmy_to.equals("")) {
				sql = " and  " + alias_table + "." + get_col + " = to_date('"
						+ dmy + "','dd/MM/yyyy')";
			} else {
				sql = "AND " + alias_table + "." + get_col
						+ " BETWEEN TO_DATE('" + dmy + "','DD/MM/YYYY')\n"
						+ "                                     AND TO_DATE('"
						+ dmy_to + "','DD/MM/YYYY')";

			}
		} else if (month.isSelected()) {
			sql = " and  to_char(" + alias_table + "." + get_col
					+ ",'MM/yyyy')   = '" + my + "'";
		} else if (year.isSelected()) {
			sql = " and  to_char(" + alias_table + "." + get_col
					+ ",'yyyy')   = '" + y + "'";
		}
		list = sql;
		return list;
	}

	// BEGIN 15/10/2012 VIET HAM CHECK LAY LUONG CHO NGI VIEC THEO MAIL
	// 26/09/2012 C.UYEN TB GUI
	public long Get_Salary_For_QuitW(String empsn, Date date_input) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date_input);

		// SAI O DAY
		System.out.println(sf.format(ca.getTime()));
		String day = String.valueOf(ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String date_str = "";
		Date date = OBJ_UTILITY.MONTH_NOW(day, date_input);
		date_str = sf.format(date);
		IGenericDAO<N_N_HEALTH_R, String> obj_dao = Application.getApp()
				.getDao(N_N_HEALTH_R.class);
		N_N_HEALTH_R obj_health_r = obj_dao.findById(empsn);

		String sql = "";

		long salary = 0;
		long salary_b = 0;
		long salary_m = 0;
		long salary_r = 0;// tra ve luong nay de tinh cho nghi viec

		salary = Get_Salary_Basic(empsn, date_input);// luong hien hanh
		salary_b = obj_health_r.getSALARY_B() == null ? 0 : obj_health_r
				.getSALARY_B();// luong dau quy
		salary_m = obj_health_r.getSALARY_M() == null ? 0 : obj_health_r
				.getSALARY_M();// luong ky trinh cua thang truoc thang htai 1
								// thang
		// Lay ra salary_m de s2 cho thang cuoi quy, vi gio d/c 2 thang ma o hh
		// giua quy chi d/c dc 1 thang thi k up lai salary ma ky trinh lai luong
		// hh

		// * Truong hop 1: -Tang 1 tay tai thang hh roi nghi viec
		// -Nghi viec di lam lai tang tai thang hh roi nghi viec
		// --> 2 TH tren salary_b & salary_m luc nay = 0 ==> lay luong hh de
		// tinh nghi viec
		if (salary_b == 0 && salary_m == 0) {
			salary_r = salary;
		}
		// * Truong hop 2: ap dung cho giua quy va cuoi quy (dau quy lay luong
		// hh)
		// s2 chung theo pp: - if(salary_b <= salary_m thi lay salary_m s2 voi
		// salary, luong nao > hon thi lay
		// - if(salary_b > salary_m thi lay salary_b s2 voi salary, luong nao >
		// hon thi lay
		else if (salary_b <= salary_m) {
			if (salary_m <= salary) {
				salary_r = salary;
			} else {
				salary_r = salary_m;
			}
		} else // salary_b > salary_m
		{
			if (salary_b <= salary) {
				salary_r = salary;
			} else {
				salary_r = salary_b;
			}
		}

		return salary_r;

	}

	// END 15/10/2012 VIET HAM CHECK LAY LUONG CHO NGI VIEC THEO MAIL 26/09/2012
	// C.UYEN TB GUI

	// Begin 19/10/2012
	private boolean thuoc_xuong_And_Qquanly(String madv, String xuong,
			String ma_user) {// DUNG CHO tam ung vi ketoan FVL & FVLS chay ung
								// luong cho dvi thuoc quyen QL:TB0 va TB1(FVLS)

		boolean flag = true;
		String sql = "select count(*) from n_department d"
				+ " where d.name_fact='" + xuong + "' and d.id_dept='" + madv
				+ "'";
		BigDecimal c = (BigDecimal) obj_util.Exe_Sql_Obj(sql);

		if (c.compareTo(BigDecimal.ZERO) > 0) {
			if (xuong.equals("TB")) {
				String sql_1 =

				"SELECT COUNT(*)  FROM N_EMPLOYEE T\n"
						+ "WHERE T.DEPSN = '"
						+ madv
						+ "'\n"
						+ "AND T.USER_MANAGE_ID IN (SELECT L.MA_QL  FROM N_USER_LIMIT L\n"
						+ "                         WHERE L.MA_QL = T.USER_MANAGE_ID\n"
						+ "                         AND L.MA_USER = '"
						+ ma_user + "')";

				BigDecimal c1 = (BigDecimal) obj_util.Exe_Sql_Obj(sql_1);
				if (c1.compareTo(BigDecimal.ZERO) > 0) {
					flag = true;
				} else {
					flag = false;
				}
			}
		} else {
			flag = false;
		}
		return flag;
		// return c.compareTo(BigDecimal.ZERO)>0;
	}

	// End 19/10/2012

	// BEGIN 23/10/2012 LAY TEN XUONG THEO MA DON VI NHAP VAO
	public String Get_Fact_Follow_Dept(String id_dept) {
		String Fact = "";
		String sql = "";

		sql = "SELECT T.NAME_FACT FROM N_DEPARTMENT T\n"
				+ "WHERE T.ID_DEPT = '" + id_dept + "'";

		Object obj = new Object();

		obj = obj_util.Exe_Sql_Obj(sql);

		if (obj == null) {
			Fact = "";
			System.out.println("Not have name fact!");
		} else {
			try {
				Fact = String.valueOf(obj.toString());
			} catch (Exception e) {
				Fact = "";
			}
		}

		return Fact;
	}

	/**
	 * ký trình bảo hiểm mới
	 * 
	 * @param empsn
	 * @param date_input
	 * @return salary lần 1
	 * @throws SQLException
	 */
	public Long Get_Salary_Total_By_Date_Lan_1(String empsn, Date date_input)
			throws SQLException {

		long rs = 0;
		String str_date = sf.format(date_input);

		String month = str_date.substring(3, 5);
		String year = str_date.substring(6, 10);
		Connection con = Application.getApp().getConnection();

		String sql = "{call C5_CALCULATE_SALARY_N_YT_1(?,?,?,?)}";

		try {
			CallableStatement stm = con.prepareCall(sql);
			stm.setString(1, month);
			stm.setString(2, year);
			stm.setString(3, empsn);

			stm.registerOutParameter(4, Types.INTEGER);

			stm.execute();
			rs = Long.valueOf(stm.getInt(4));

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}

	/**
	 * Ký trình bảo hiểm mới
	 * 
	 * @param empsn
	 * @param date_input
	 * @return salary
	 * @throws SQLException
	 */
	public long Get_Salary_Total_By_Month_for_NV_Lan_1(String empsn,
			Date date_input) throws SQLException {
		SimpleDateFormat sf_ = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date date_para_sql = null;
		Date date_dot_tv = null;
		long rs = 0;
		String str_date = sf.format(date_input);

		date_dot_tv = Get_Dot_tv(empsn, date_input);

		try {
			date_para_sql = java.sql.Date.valueOf(sf_.format(date_dot_tv));
		} catch (Exception e) {
			System.out.println("So the " + empsn + " Khong co date_dot_tv");

		}

		String month = str_date.substring(3, 5);
		String year = str_date.substring(6, 10);
		Connection con = Application.getApp().getConnection();
		String sql = "{call C5_CALCULATE_SALARY_OFF_YT_1(?,?,?,?,?)}";

		try {
			CallableStatement stm = con.prepareCall(sql);
			stm.setString(1, month);
			stm.setString(2, year);
			stm.setString(3, empsn);
			stm.setDate(4, date_para_sql);
			stm.registerOutParameter(5, Types.INTEGER);

			stm.execute();
			rs = Long.valueOf(stm.getInt(5));

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}
	// END 23/10/2012 LAY TEN XUONG THEO MA DON VI NHAP VAP

}
