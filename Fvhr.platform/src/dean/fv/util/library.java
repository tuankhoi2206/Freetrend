package fv.util;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.list.DefaultListModel;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.hql.ast.tree.Case2Node;

import com.sun.star.lib.uno.environments.java.java_environment;

import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_DIEULUAT;
import ds.program.fvhr.domain.N_ETHNIC;
import ds.program.fvhr.domain.N_HINHPHAT;
import ds.program.fvhr.domain.N_KHOAN;
import ds.program.fvhr.domain.N_LABOURKIND;
import ds.program.fvhr.domain.N_POSITION;
import ds.program.fvhr.domain.N_POSS_SALARY;
import ds.program.fvhr.domain.N_SHIFT;
import ds.program.fvhr.domain.N_SPDEPT_LIST;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.special_dialog;
import dsc.echo2app.special_dialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;

//thu vien dung de ho tro
public class library {
	public Connection con = null;
	public ResultSet rs = null;
	PreparedStatement pstm = null;
	public Statement st = null;
	CallableStatement cs;
	List lists = new ArrayList();
	Vni2Uni c = new Vni2Uni();
	String ch = "", sothe = "", ch2 = "", chresult = "", resulfalse = "";

	public void setdate(DscDateField df) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		df.setDateFormat(sdf);
		df.setSelectedDate(Calendar.getInstance(Locale.ENGLISH));
		df.getDateChooser().setMonthNameLength(30);
	}

	public boolean check_data(String empsn) {
		boolean check = true;
		if (check) {
			if (empsn.equals("")) {
				ShowMessageError("Số thẻ bắt buộc phải nhập");
				return false;
			}
			if (!empsn.matches("[0-9]{8}")) {
				ShowMessageError("Số thẻ không hợp lệ.");
				return false;
			}
			boolean exits = check_exits("n_EMPLOYEE", "EMPSN", empsn);
			if (!exits) {
				ShowMessageError("Không tồn tại số thẻ này trong hệ thống...");
				return false;
			}
			boolean nv = check_exits2("N_EMPLOYEE", "EMPSN", empsn, "DEPSN",
					"00000");
			if (nv) {
				ShowMessageError("CNV đã nghỉ việc..");
				return false;
			}
			IGenericDAO<DSPB02, String> udao = Application.getApp().getDao(
					DSPB02.class);
			DSPB02 u = udao.findById(Application.getApp().getLoginInfo()
					.getUserID());
			boolean ql = check_QL(empsn, "", "", "", "", u.getPB_USERNO());
			if (ql == false) {
				return false;
			}
		}
		return check;
	}

	public MappingPropertyEditor positionEditor() {
		MappingPropertyEditor positionEditor = new MappingPropertyEditor();
		IGenericDAO<N_POSITION, String> dao = Application.getApp().getDao(
				N_POSITION.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_POSITION.class);
		dc.addOrder(Order.asc("ID_POSITION"));
		List<N_POSITION> list = dao.findByCriteria(50, dc);
		for (N_POSITION pos : list) {
			positionEditor.put(
					Vni2Uni.convertToUnicode(pos.getNAME_POSITION()),
					Vni2Uni.convertToUnicode(pos.getID_POSITION()));

		}

		// positionEditor.put("CÔNG NHÂN", "CN");

		return positionEditor;
	}

	private static String[] bubblesort(String[] arr) {
		String s = "";
		for (int i = 0; i < arr.length; i++) {
			for (int j = i; j < arr.length; j++) {
				if (Integer.parseInt((arr[i].toString())) > (Integer
						.parseInt(arr[j].toString()))) {
					s = arr[j];
					arr[j] = arr[i];
					arr[i] = s;
				}
			}
		}
		return arr;
	}

	public static MappingPropertyEditor getLevel(Object poss) {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_POSS_SALARY, String> dao = Application.getApp().getDao(
				N_POSS_SALARY.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_POSS_SALARY.class);
		dc.add(Restrictions.eq("ID_POSS", Vni2Uni.convertToVNI((String) poss)));

		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.distinct(Projections.property("LEVEL_POSS")));
		dc.setProjection(pl);
		List<N_POSS_SALARY> list = dao.findByCriteria(20, dc);
		int a = 1;
		for (int i = 0; i < list.size(); i++) {
			editor.put(Vni2Uni.convertToUnicode(poss.toString()) + "_"
					+ (i + 1));
		}

		return editor;
	}

	public MappingPropertyEditor bindID_SPDEPT() {
		MappingPropertyEditor map = new MappingPropertyEditor();
		IGenericDAO<N_SPDEPT_LIST, String> daoSPDept = Application.getApp()
				.getDao(N_SPDEPT_LIST.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_SPDEPT_LIST.class);
		dc.addOrder(Order.asc("ID_SPDEPT"));
		List<N_SPDEPT_LIST> listSPDept = daoSPDept.findByCriteria(10, dc);
		for (N_SPDEPT_LIST obj : listSPDept) {
			map.put(obj.getID_SPDEPT());
		}
		return map;
	}

	public MappingPropertyEditor bindabourkind() {
		MappingPropertyEditor map = new MappingPropertyEditor();
		IGenericDAO<N_LABOURKIND, String> daolb = Application.getApp().getDao(
				N_LABOURKIND.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_LABOURKIND.class);
		dc.addOrder(Order.asc("ID"));
		List<N_LABOURKIND> listlb = daolb.findByCriteria(10, dc);
		for (N_LABOURKIND obj : listlb) {
			map.put(Vni2Uni.convertToUnicode(obj.getLIMIT()), obj.getID());
		}
		return map;
	}

	public MappingPropertyEditor shiftEditor() {
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_SHIFT, String> dao = Application.getApp().getDao(
				N_SHIFT.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_SHIFT.class);
		dc.addOrder(Order.asc("NAME_SHIFT"));

		List<N_SHIFT> list = dao.findByCriteria(100, dc);
		for (N_SHIFT shift : list) {
			e.put(shift.getNAME_SHIFT(), shift);
		}
		return e;
	}

	public MappingPropertyEditor ethnic() {
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_ETHNIC, String> dao = Application.getApp().getDao(
				N_ETHNIC.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_ETHNIC.class);
		dc.addOrder(Order.asc("NAME_ETHNIC"));

		List<N_ETHNIC> list = dao.findByCriteria(50, dc);
		for (N_ETHNIC eth : list) {
			e.put(Vni2Uni.convertToUnicode(eth.getNAME_ETHNIC()),
					eth.getNAME_ETHNIC());
		}
		return e;
	}

	// showMessage
	public void ShowMessageOK(String message) {
		special_dialog sp = new special_dialog(special_dialog.TYPE_INFORMATION
				+ special_dialog.CONTROLS_OK, message);
		sp.show();
		return;
	}

	public void ShowMessageError(String message) {
		/*special_dialog sp = new special_dialog(special_dialog.TYPE_ERROR
				+ special_dialog.CONTROLS_OK, message);*/
		return;
	}

	public void ShowMessageError_add(String message, DscField aaa) {
		/*special_dialog sp = new special_dialog(special_dialog.TYPE_ERROR
				+ special_dialog.CONTROLS_OK, message);*/
		aaa.requestFocus();
		return;
	}

	public void ShowMessageWar(String message) {
		/*special_dialog sp = new special_dialog(special_dialog.TYPE_INFORMATION
				+ special_dialog.CONTROLS_OK, message);*/
		return;
	}

	public void ShowMessageInfo(String message) {
		/*special_dialog sp = new special_dialog(special_dialog.TYPE_INFORMATION
				+ special_dialog.CONTROLS_OK, message);*/
		return;
	}

	public void ShowMessageConFi(String message) {
		/*special_dialog sp = new special_dialog(special_dialog.CONTROLS_YES_NO,
				message);*/
		return;
	}

	public special_dialog dialog(String message) {
		special_dialog a = new special_dialog(special_dialog.CONTROLS_YES_NO,
				message);
		return a;
	}

	public special_dialog requestFocus(String message, final DscField avv) {
		special_dialog a = new special_dialog(special_dialog.TYPE_INFORMATION,
				message);
		a.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				avv.requestFocus();
			}
		});
		return a;
	}

	public void close_con(Connection con, Statement st) {
		try {
			if (con != null) {
				con.close();
			}
			if (st != null) {
				st.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public String getDeptEmp(String empsn) {
		con = Application.getApp().getConnection();
		String d = "";
		String sql = "select d.name_fact||'.'||d.name_group||'.'||d.name_dept_name from n_employee e, n_department d "
				+ " where e.depsn=d.id_dept and e.empsn='" + empsn + "'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				d = rs.getString(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			d = "";
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return d;
	}

	public boolean check_fact_transfer(String thang, String nam, String depsn) {
		con = Application.getApp().getConnection();
		rs = null;
		Boolean check = false;
		String sql = "select t.depsn  from n_fact_transfer_lock t\n"
				+ " where t.year = '" + nam + "' and t.month = '" + thang + "'"
				+ " and t.locked = 1\n" + " and t.depsn = '" + depsn + "'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {

				check = true;
			} else {

				check = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			check = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return check;
	}

	public boolean check_khoaHDLD(String sothe, String id_labour) {
		con = Application.getApp().getConnection();
		rs = null;
		Boolean check = false;
		String sql = "select * from n_labour where empsn='" + sothe + "' "
				+ " and id_labour='" + id_labour + "' and checked='Y'";

		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				check = true;
			} else {

				check = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			check = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return check;
	}

	public boolean check_khoaPLHD(String id_sublabour) {
		con = Application.getApp().getConnection();
		rs = null;
		Boolean check = false;
		String sql = "select * from n_sub_labour where id_contract='"
				+ id_sublabour + "' and checked='Y'";

		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				check = true;
			} else {

				check = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			check = false;
		} finally {
			try {
				close_con(con, st);
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return check;
	}

	public boolean check_emp_transfer(String thang, String nam, String empsn) {
		con = Application.getApp().getConnection();
		rs = null;
		Boolean check = false;
		String sql = "select * from n_emp_transfer_lock where empsn='" + empsn
				+ "' and year='" + nam + "' and month='" + thang
				+ "' and locked=1";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {

				check = true;
			} else {

				check = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			check = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return check;
	}

	public boolean locked(String thang, String nam) {
		con = Application.getApp().getConnection();
		rs = null;
		Boolean check = false;
		String sql = "select * from n_get_data a where a.months='" + thang
				+ "' and years='" + nam + "' and locked='0'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				check = false;
			} else {
				check = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			check = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return check;
	}

	public boolean check_limit(String limit, String userid) {
		con = Application.getApp().getConnection();
		rs = null;
		Boolean ch = false;
		String limits = "'NB2','DAO','NS0','NB0','NS6','000'";
		String sql = "select * from dspb02 where PB_USERNO='" + userid
				+ "' and ID_LIMIT in(" + limits + ")";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				ch = true;
			} else {
				ch = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ch = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return ch;
	}

	public static void bindDscSelectField(SelectField sf,
			MappingPropertyEditor mpe) {
		DefaultListModel model = new DefaultListModel();
		String[] displays = mpe.getDisplays();
		for (String display : displays) {
			model.add(display);
		}
		sf.setModel(model);
		sf.setSelectedIndex(-1);
	}

	/*
	 * public static class ListBinder { public static void
	 * bindSelectField(SelectField sf, MappingPropertyEditor editor, boolean
	 * display) { DefaultListModel model = (DefaultListModel) sf.getModel();
	 * model.removeAll(); sf.setSelectedIndex(-1); if (editor == null ||
	 * editor.filterMap().size() <= 0) { sf.setEnabled(false); } else {
	 * sf.setEnabled(true); for (int i = 0; i < editor.filterMap().size(); i++)
	 * { String disp = ""; if (display) { disp = editor.getDisplays()[i]; } else
	 * { disp = editor.getValues()[i] == null ? "" : editor
	 * .getValues()[i].toString(); } SelectItem item = new SelectItem(disp,
	 * editor.getValues()[i]); model.add(item); } } } }
	 */
	public static MappingPropertyEditor getIDdieu() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_DIEULUAT, String> dao = Application.getApp().getDao(
				N_DIEULUAT.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_DIEULUAT.class);
		dc.addOrder(Order.asc("ID_DIEU"));
		List<N_DIEULUAT> list = dao.findByCriteria(100, dc);
		Vni2Uni c = new Vni2Uni();
		editor.put("Chon dieu", "Chon dieu");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				N_DIEULUAT dieu = list.get(i);
				editor.put(dieu.getID_DIEU(), dieu.getID_DIEU());
			}
		}
		return editor;
	}

	public static MappingPropertyEditor getIDhinhphat() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_HINHPHAT, String> dao = Application.getApp().getDao(
				N_HINHPHAT.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_HINHPHAT.class);
		dc.addOrder(Order.asc("NAME_PHAT"));
		List<N_HINHPHAT> list = dao.findByCriteria(100, dc);
		Vni2Uni c = new Vni2Uni();
		editor.put("", "");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				N_HINHPHAT hp = list.get(i);
				editor.put(c.convertToUnicode(hp.getNAME_PHAT()),
						hp.getID_PHAT());
				// editor.put(c.convertToUnicode(hp.getNAME_PHAT()),hp.getNAME_PHAT());
			}
		}
		return editor;
	}

	public static MappingPropertyEditor getIDKHOAN(Object dieu) {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_KHOAN, String> dao = Application.getApp().getDao(
				N_KHOAN.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_KHOAN.class);
		dc.add(Restrictions.eq("ID_DIEU", dieu));
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.distinct(Projections.property("ID_KHOAN")));
		dc.setProjection(pl);
		dc.addOrder(Order.asc("ID_KHOAN"));
		List list = dao.findByCriteria(500, dc);
		editor.put("", "");
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			if (obj != null)
				editor.put(obj.toString(), obj);
		}
		return editor;
	}

	public static MappingPropertyEditor getIDkhoan() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_KHOAN, String> dao = Application.getApp().getDao(
				N_KHOAN.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_KHOAN.class);
		dc.addOrder(Order.asc("ID_KHOAN"));
		List<N_KHOAN> list = dao.findByCriteria(100, dc);
		Vni2Uni c = new Vni2Uni();
		editor.put("Chon khoan", "Chon khoan");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				N_KHOAN khoan = list.get(i);
				editor.put(khoan.getID_KHOAN(), khoan.getID_KHOAN());
			}
		}
		return editor;
	}

	// ds xuong
	public List fact() {
		con = Application.getApp().getConnection();
		String sql = "select distinct a.name_fact from n_factory a";
		lists.clear();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				lists.add(rs.getString(1));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return lists;
	}

	// tra ve 1 ds bat ky
	@SuppressWarnings("rawtypes")
	public List any(String table, String column, String dk, String dksort,
			String valuereturn) {
		con = Application.getApp().getConnection();
		String sql = "select * from " + table + " where " + column + "='" + dk
				+ "' Order by " + dksort + "";
		lists.clear();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				lists.add(rs.getString(valuereturn));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return lists;
	}

	public List list_all(String table, String fieldget) {

		IGenericDAO<DSPB02, String> udao = Application.getApp().getDao(
				DSPB02.class);
		DSPB02 u = udao.findById(Application.getApp().getLoginInfo()
				.getUserID());
		String fact = vungQL(u.getPB_USERNO());
		String sql = "select a.* from "
				+ table
				+ " a where a.empsn in(select empsn from n_employee e,"
				+ " n_department d where a.empsn=e.empsn and e.depsn=d.id_dept "
				+ " and d.name_fact in" + fact + "and e.user_manage_id in "
				+ " (select MA_QL FROM N_USER_LIMIT WHERE MA_USER='"
				+ u.getPB_USERNO() + "' " + " and MA_QL=user_manage_id))";
		try {
			con = Application.getApp().getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			lists.clear();
			while (rs.next()) {
				lists.add(rs.getString(fieldget));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return lists;
	}

	// tra ve dau tuan va cuoi tuan
	public String[] getdates(String day) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		java.util.Date ud = null;
		java.sql.Date sd = null;
		try {
			ud = df.parse(day);
			sd = new java.sql.Date(ud.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		c.setTime(sd);
		c2.setTime(sd);
		int month = c.getTime().getMonth() + 1;
		int day_of_week = c.getTime().getDay();
		switch (day_of_week) {
		case 0:
			c.add(c.DAY_OF_WEEK, -6);
			c2.add(c.DAY_OF_WEEK, 0);
			break;
		// thứ 2
		case 1:
			c.add(c.DAY_OF_WEEK, 0);
			c2.add(c2.DAY_OF_WEEK, 6);
			break;
		// thứ 3
		case 2:
			c.add(c.DAY_OF_WEEK, -1);
			c2.add(c2.DAY_OF_WEEK, 5);
			break;
		// thư 4
		case 3:
			c.add(c.DAY_OF_WEEK, -2);
			c2.add(c2.DAY_OF_WEEK, 4);
			break;
		// 5
		case 4:
			c.add(c.DAY_OF_WEEK, -3);
			c2.add(c2.DAY_OF_WEEK, 3);
			break;
		// 6
		case 5:
			c.add(c.DAY_OF_WEEK, -4);
			c2.add(c2.DAY_OF_WEEK, 2);
			break;
		// 7
		case 6:
			c.add(c.DAY_OF_WEEK, -5);
			c2.add(c2.DAY_OF_WEEK, 1);
			break;
		default:
			c.add(c.DAY_OF_WEEK, 0);
			c2.add(c2.DAY_OF_WEEK, 6);
			break;
		}

		java.util.Date utild = c.getTime();
		java.sql.Date sqld = new java.sql.Date(utild.getTime());
		String a = df.format(sqld);
		String m = a.substring(3, 5); // cắt giá trị tháng of ngày thứ 2
		if (Integer.parseInt(m) == (month - 1)) // kiểm tra cái tháng của ngày
												// thứ 2 sau khi lùi về
		{ // vd : nhập thứ năm là 1 ngày của tháng 4 nhưng thứ hai của thứ năm
			// đó là 1 ngày của tháng 3
			// thì rớt dzô trường hợp này
			// month-1 là tháng trước month vd : 4 vs 3
			if (month < 10) {
				a = "01/" + "0" + month + "/" + a.substring(6, 10);
			} else {
				a = "01/" + month + "/" + a.substring(6, 10);
			}
		}

		java.util.Date utild2 = c2.getTime();
		java.sql.Date sqld2 = new java.sql.Date(utild2.getTime());
		String b = df.format(sqld2);
		String m2 = b.substring(3, 5);
		if (Integer.parseInt(m2) == (month + 1)) {
			int day_of_month = c.getActualMaximum(c.DAY_OF_MONTH);
			if (day_of_month < 10) {
				b = "0" + day_of_month + a.substring(2, a.length());
			} else
				b = day_of_month + a.substring(2, a.length());
		}
		return new String[] { a, b };
	}

	public String getstring(String table, String column, String dk,
			String valuereturn) {
		con = Application.getApp().getConnection();
		String sql = "select " + valuereturn + " from " + table + " where "
				+ column + "='" + dk + "'";
		String returns = "";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				returns = c.convertToUnicode(rs.getString(1));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return returns;
	}

	public String[] index_all(String depsn) {
		con = Application.getApp().getConnection();
		String x = "", n = "", dv = "";
		String sql = "select d.name_fact,d.name_group,d.name_dept_name from n_department d"
				+ " where id_dept=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, depsn);
			rs = pstm.executeQuery();
			if (rs.next()) {
				x = rs.getString(1);
				n = rs.getString(2);
				dv = rs.getString(3);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return new String[] { x, n, dv };
	}

	// lay theo so the
	public String[] index_all_empsn(String empsn) {
		con = Application.getApp().getConnection();
		Vni2Uni c = new Vni2Uni();
		String x = "", n = "", ndv = "", mdv = "";
		String sql = "select d.name_fact,d.name_group,d.name_dept_name,d.id_dept from n_department d,n_employee e"
				+ " where d.id_dept=e.depsn and e.empsn=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, empsn);
			rs = pstm.executeQuery();
			if (rs.next()) {
				x = c.convertToUnicode(rs.getString(1));
				n = c.convertToUnicode(rs.getString(2));
				ndv = c.convertToUnicode(rs.getString(3));
				mdv = c.convertToUnicode(rs.getString(4));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return new String[] { x, n, ndv, mdv };
	}

	// khong bit phai noi seo........
	public String indexof_depsn(String fact, String group, String name_dept) {
		con = Application.getApp().getConnection();
		String index = "", sql = "select * from n_department where name_fact=?"
				+ " and name_group=? and name_dept_name=?";
		lists.clear();
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, fact);
			pstm.setString(2, c.convertToVNI(group));
			pstm.setString(3, c.convertToVNI(name_dept));
			rs = pstm.executeQuery();
			if (rs.next()) {
				index = c.convertToUnicode(rs.getString(1));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return index;
	}

	// ds ma DV
	public List depsn() {
		con = Application.getApp().getConnection();
		String sql = "select distinct id_dept from n_department order by id_dept";
		lists.clear();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				lists.add(c.convertToUnicode(rs.getString(1)));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return lists;
	}

	// lay ds nhom
	public List group(String a) {
		con = Application.getApp().getConnection();
		String sql = "select NAME_GROUP from N_GROUP_DEPT where FACT=?"
				+ " GROUP BY NAME_GROUP";
		lists.clear();
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, a);
			rs = pstm.executeQuery();
			while (rs.next()) {
				lists.add(c.convertToUnicode(rs.getString(1)));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (pstm != null) {
					pstm.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return lists;
	}

	public List group2(String a) {
		con = Application.getApp().getConnection();
		String sql = "select NAME_GROUP from n_Department where name_FACT=?"
				+ " GROUP BY NAME_GROUP";
		lists.clear();
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, a);
			rs = pstm.executeQuery();
			while (rs.next()) {
				lists.add(c.convertToUnicode(rs.getString(1)));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (pstm != null) {
					pstm.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return lists;
	}

	// lay ds ten don vi trong db
	public List dept_name(String a, String b) {
		con = Application.getApp().getConnection();
		String sql = "select NAME_DEPT_NAME from n_department where NAME_GROUP=? "
				+ "AND NAME_FACT=? GROUP BY NAME_DEPT_NAME";
		lists.clear();
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, c.convertToVNI(a));
			pstm.setString(2, c.convertToVNI(b));
			rs = pstm.executeQuery();
			while (rs.next()) {
				lists.add(c.convertToUnicode(rs.getString(1)));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (pstm != null) {
					pstm.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return lists;
	}

	// kiem tra quyen quan ly cua 1 user
	public boolean check_QL(String empsn, String xuong, String nhom,
			String donvi, String madv, String user) {
		con = Application.getApp().getConnection();
		rs = null;
		Boolean ch = false;
		String sql = "select a.*,b.name_dept from n_employee a, n_department b "
				+ " Where a.depsn_temp=b.id_dept ";
		if (!empsn.equals("")) {
			sql += " and a.empsn='" + empsn + "'";
		} else {
			if (!madv.equals("")) {
				sql += " and a.depsn_temp='" + madv + "'";
			} else {
				if (!xuong.equals("")) {
					sql += " and b.name_fact='" + xuong + "'";
				}
				if (!nhom.equals("")) {
					sql += " and b.name_group='" + nhom + "'";
				}
				if (!donvi.equals("")) {
					sql += " and b.name_dept_name='" + donvi + "'";
				}
			}
		}
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next() == false) {
				Application.getApp().showMessageDialog(
						MessageDialog.TYPE_INFORMATION
								+ MessageDialog.CONTROLS_OK,
						"Không tìm thấy dữ liệu");
				ch = false;
			} else {
				sql += " and a.user_manage_id in (SELECT ma_QL FROM n_user_limit WHERE ma_user="
						+ "'" + user + "' and ma_ql=user_manage_id)";
				rs = st.executeQuery(sql);
				if (rs.next() == false) {
					Application.getApp().showMessageDialog(
							MessageDialog.TYPE_INFORMATION
									+ MessageDialog.CONTROLS_OK,
							"Bạn không có quyền thao tác trên dl này");
					ch = false;
				} else
					ch = true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return ch;
	}

	public boolean check_table_exits(String table) {
		con = Application.getApp().getConnection();
		boolean check = false;
		String sql = "SELECT a.table_name from USER_TABLES a"
				+ " WHERE a.table_name = UPPER('" + table + "')";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				check = true;
			} else {
				check = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return check;
	}

	@SuppressWarnings("rawtypes")
	public final Comparator INT_COMPARATOR = new Comparator() {
		public int compare(Object o1, Object o2) {
			if (o1 == null && o2 == null)
				return 0;
			else if (o1 == null)
				return 1;
			else if (o2 == null)
				return -1;
			if (o1 instanceof String && o2 instanceof String)
				return ((String) o1).compareTo((String) o2);
			else if (o1 instanceof Float && o2 instanceof Float)
				return ((Float) o1).compareTo((Float) o2);
			else if (o1 instanceof Integer && o2 instanceof Integer)
				return ((Integer) o1).compareTo((Integer) o2);
			else
				return ((Long) o1).compareTo((Long) o2);
		}
	};

	// kiem tra dl co ton tai trong table hay khong
	public boolean check_exits(String table, String field, String dk) {
		con = Application.getApp().getConnection();
		Boolean check = false;
		String sql = "select * from " + table + " where " + field + " = '" + dk
				+ "'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				check = true;
			} else
				check = false;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				try {
					if (con != null) {
						con.close();
					}
					if (st != null) {
						st.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		return check;
	}

	public boolean check_exits2(String table, String field1, String dk1,
			String field2, String dk2) {
		con = Application.getApp().getConnection();
		Boolean check = false;
		String sql = "select * from " + table + " where " + field1 + " = '"
				+ dk1 + "' and " + field2 + "='" + dk2 + "'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				check = true;
			} else
				check = false;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return check;
	}

	public boolean check_exits1(String table, String field1, String dk1,
			String field2, String dk2, String field3, String dk3) {
		con = Application.getApp().getConnection();
		Boolean check = false;
		String sql = "select * from " + table + " where " + field1 + " = '"
				+ dk1 + "' and " + field2 + "='" + dk2 + "' and " + field3
				+ "='" + dk3 + "'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				check = true;
			} else
				check = false;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return check;
	}

	// lay gia tri cua mot field trong 1 table bat ki cua DB
	public String getfiled(String table, String field, String dk,
			String fieldget) {
		String sql, str = "";
		con = Application.getApp().getConnection();
		sql = "select * from " + table + " where " + field + " = '" + dk + "'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				str = rs.getString(fieldget);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return str;
	}

	public String getfileds(String table, String field1, String dk1,
			String field2, String dk2, String fieldget) {
		String sql, str = "";
		con = Application.getApp().getConnection();
		sql = "select * from " + table + " where " + field1 + " = '" + dk1
				+ "' and " + field2 + "='" + dk2 + "'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				str = rs.getString(fieldget);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return str;
	}

	public String getfiled2(String table, String field1, String dk1,
			String field2, String dk2, String field3, String dk3,
			String fieldget) {
		String sql, str = "";
		con = Application.getApp().getConnection();
		sql = "select * from " + table + " where " + field1 + " = '" + dk1
				+ "' and " + field2 + "='" + dk2 + "' and " + field3 + "='"
				+ dk3 + "'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				str = rs.getString(fieldget);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return str;
	}

	public void Insert_N_Action_Daily(String ma_user, String TableName,
			String ActionName, String Empsn, String Note) {

		con = Application.getApp().getConnection();
		String sql = "{call N_HB_ACTION_DAILY(?,?,?,?,?)}";
		CallableStatement stm = null;
		try {
			stm = con.prepareCall(sql);
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
				if (con != null) {
					con.close();
				}
				if (stm != null) {
					stm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public Date getdate(String table, String field, String dk, String fieldget) {
		String sql;
		Date str = null;
		con = Application.getApp().getConnection();
		sql = "select * from " + table + " where " + field + " = '" + dk + "'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				str = rs.getDate(fieldget);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return str;
	}

	public Date getdates(String table, String field1, String dk1,
			String field2, String dk2, String fieldget) {
		String sql;
		Date str = null;
		con = Application.getApp().getConnection();
		sql = "select * from " + table + " where " + field1 + " = '" + dk1
				+ "' and " + field2 + "='" + dk2 + "'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				str = rs.getDate(fieldget);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return str;
	}

	// tra ve java.sql.date cua 1 chuoi ngay
	public Date getdate(String d) {
		Date day = null;
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String a = d;
		try {
			java.util.Date uday = df.parse(a);
			day = new java.sql.Date(uday.getTime());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return day;
	}

	// kiem tra ngay thang nam nhap vao co hop le
	public Boolean check_input_day(String day) {
		String d = "", m = "", y = "";
		Boolean valid = false;

		if (day.length() == 8) {
			d = day.substring(0, 2);
			m = day.substring(2, 4);
			y = day.substring(4, 8);
		}
		if (day.length() == 10) {
			d = day.substring(0, 2);
			m = day.substring(3, 5);
			y = day.substring(6, 10);
		}
		if ((day.length() == 8) || (day.length() == 10)) {
			int yy = Integer.parseInt(y);
			int mm = Integer.parseInt(m);
			int dd = Integer.parseInt(d);
			int du = yy % 4;
			if ((!d.equals("00")) && (!m.equals("00")) && (!y.equals("0000"))) {

				if ((m.equals("01")) || (m.equals("03")) || (m.equals("05"))
						|| (m.equals("07")) || (m.equals("08"))
						|| (m.equals("10")) || (m.equals("12"))) {
					if (dd > 31) {
						Application.getApp().showMessageDialog(
								MessageDialog.TYPE_ERROR
										+ MessageDialog.CONTROLS_OK,
								"Không có ngày này trong tháng.." + m + "/"
										+ yy + "..@@@@");
						valid = false;
					} else
						valid = true;
				}
				if ((m.equals("04")) || (m.equals("06")) || (m.equals("09"))
						|| (m.equals("11"))) {
					if (dd > 30) {
						Application.getApp().showMessageDialog(
								MessageDialog.TYPE_ERROR
										+ MessageDialog.CONTROLS_OK,
								"Không có ngày này trong tháng.." + m + "/"
										+ yy + "..@@@@");
						valid = false;
					} else
						valid = true;
				}
				if (m.equals("02")) {
					if (du != 0) {
						if (dd > 28) {
							Application.getApp().showMessageDialog(
									MessageDialog.TYPE_ERROR
											+ MessageDialog.CONTROLS_OK,
									"Không có ngày này trong tháng.." + m + "/"
											+ yy + "..@@@@");
							valid = false;

						} else
							valid = true;
					} else {
						if (dd > 29) {
							Application.getApp().showMessageDialog(
									MessageDialog.TYPE_ERROR
											+ MessageDialog.CONTROLS_OK,
									"Không có ngày này trong tháng.." + m + "/"
											+ yy + "..@@@@");
							valid = false;
						} else
							valid = true;
					}
				}
				if ((mm > 12) || yy < 1900) {
					Application.getApp().showMessageDialog(
							MessageDialog.TYPE_ERROR
									+ MessageDialog.CONTROLS_OK,
							"Ngày tháng năm không tồn tại..@@@@@");
					valid = false;
				} else
					valid = true;
			} else {
				Application.getApp().showMessageDialog(
						MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
						"Ngày tháng năm không tồn tại..@@@@");
				valid = false;
			}
		} else {
			Application
					.getApp()
					.showMessageDialog(
							MessageDialog.TYPE_ERROR
									+ MessageDialog.CONTROLS_OK,
							"Giá trị nhập vào chưa đúng"
									+ '\n'
									+ "Nhập vào chuổi 8 ký tự theo định dạng ddmmyyyy VD: bạn muốn nhập 01/01/2013"
									+ " thì nhập 01012013");
			valid = false;
		}
		return valid;
	}

	public String changeday(String day) {
		int cd = day.length();
		String validday = "";
		if (cd == 8) {
			String dd = day.substring(0, 2);
			String mm = day.substring(2, 4);
			String yyyy = day.substring(4, 8);
			validday = dd + "/" + mm + "/" + yyyy;

		} else
			validday = day;
		return validday;
	}

	// vung quan ly
	public String vungQL(String userID) {
		con = Application.getApp().getConnection();
		String ql = "", sql = "SELECT B.NAME_FACT FROM N_USER_LIMIT A,n_quanly b WHERE a.ma_ql=b.maql and A.MA_USER='"
				+ userID + "' GROUP BY B.NAME_FACT";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			ql = "(";
			while (rs.next()) {
				ql += "'" + rs.getString(1) + "',";
			}
			ql = ql.substring(0, ql.length() - 1);
			ql += ")";
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return ql;
	}

	public List vungQL1(String userID) {
		con = Application.getApp().getConnection();
		String ql = "", sql = "SELECT B.NAME_FACT FROM N_USER_LIMIT A,n_quanly b WHERE a.ma_ql=b.maql and A.MA_USER='"
				+ userID + "' GROUP BY B.NAME_FACT";
		lists.clear();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);

			while (rs.next()) {
				lists.add(rs.getString(1));
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return lists;
	}

	// tra ve 1 chuoi so the dung cho (empsn in)
	public String getchuoi(String empsn) {
		Boolean tt = false;
		if (ch.length() == 0) {
			ch += empsn;
			ch2 += "'" + empsn + "',";
			chresult = "(" + ch2.substring(0, ch2.length() - 1) + ")";
			tt = true;
		} else {
			String temp[] = new String[500];
			int cdai = ch.length() / 8;
			for (int i = 0; i < cdai; i++) {
				sothe = ch.substring(i * 8, (i + 1) * 8);
				temp[i] = sothe;
				if (temp[i].toString().equals(empsn)) {
					tt = false;
					// resulfalse="false";
					// return resulfalse;
				} else
					tt = true;
			}
			if (tt == true) {
				ch += empsn;
				ch2 += "'" + empsn + "',";
				chresult = "(" + ch2.substring(0, ch2.length() - 1) + ")";
			}
		}
		return chresult;
	}

	public Boolean kyluat(String empsn, String ngay) {
		boolean check = false;
		String sql = "select * from n_kyluat t where empsn='" + empsn
				+ "' and t.id_phat='02' and t.date_p<to_date('" + ngay
				+ "','dd/mm/yyyy')" + " and t.date_hl>to_date('" + ngay
				+ "','dd/mm/yyyy')";
		con = Application.getApp().getConnection();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				check = true;
			} else {
				check = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return check;

	}

	public boolean CheckKhoaDataMonth(String empsn, String thang, String nam) {
		// =1 la false = da khoa data
		// =0 la true = chua khoa data

		boolean kq = true;
		String checkField = getfiled2("N_GET_DATA", "EMPSN", empsn, "MONTHS",
				thang, "YEARS", nam, "LOCKED");
		boolean attLock = acc_lock(thang, nam);
		if (checkField != null) {
			if (checkField.equals("1"))
				return false;
			else
				kq = true;
		}

		if (attLock == true)
			return false;
		else
			kq = true;

		return kq;
	}

	public boolean acc_lock(String thang, String nam) {
		boolean check = false;
		con = Application.getApp().getConnection();
		String sql = "select * from ATTLOCK where tabname='ATT" + nam + thang
				+ "'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				String lock = rs.getString(2);
				if (lock != null) {
					if (lock.equals("Y")) {
						check = true;
					} else
						check = false;
				} else
					check = false;
			} else
				check = false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return check;
	}

	public boolean luu_tv(String empsn, String status) {
		boolean save_ok = false;
		try {
			con = Application.getApp().getConnection();
			cs = con.prepareCall("{call TT_update_thuviec(?,?)}");
			cs.setString(1, empsn);
			cs.setString(2, status);
			cs.execute();
			save_ok = true;
		} catch (Exception e) {
			// TODO: handle exception
			save_ok = false;
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return save_ok;
	}

	public boolean exe_sql(String sql) {
		con = Application.getApp().getConnection();
		try {
			st = con.createStatement();
			int a = st.executeUpdate(sql);
			if (a > 0) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close_con(con, null);
		}
		return false;
	}

	public boolean exe_sql_query(String sql) {
		con = Application.getApp().getConnection();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close_con(con, st);
		}
		return false;
	}

	public ResultSet get_rs(String sql) {
		con = Application.getApp().getConnection();
		try {
			st = con.createStatement(rs.TYPE_SCROLL_INSENSITIVE,
					rs.CONCUR_READ_ONLY);
			rs = st.executeQuery(sql);
			if (rs.next()) {
				return rs;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public boolean xoa_HDLD(String sothe, String mshs) {
		boolean save_ok = false;
		try {
			con = Application.getApp().getConnection();
			cs = con.prepareCall("{call TT_N_labour_D_A(?,?)}");
			cs.setString(1, sothe);
			cs.setString(2, mshs);
			cs.execute();
			save_ok = true;
		} catch (Exception e) {
			// TODO: handle exception
			save_ok = false;
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return save_ok;
	}

	public boolean savePLHD(String plhd, String ngay, String luongmoi,
			String chucvu, String luongtam, String mshd, String loai) {
		boolean save_ok = false;
		try {
			con = Application.getApp().getConnection();
			cs = con.prepareCall("{call V_SUB_LABOUR_I_A_N(?,?,?,?,?,?,?)}");
			cs.setString(1, plhd);
			cs.setString(2, ngay);
			cs.setString(3, luongmoi);
			cs.setString(4, chucvu);
			cs.setString(5, luongtam);
			cs.setString(6, mshd);
			cs.setString(7, loai);
			cs.execute();
			save_ok = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			save_ok = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return save_ok;
	}

	public boolean savePLHD1(String plhd, String ngay, String luongmoi,
			String chucvu, String luongtam, String mshd, String loai) {
		boolean save_ok = false;
		try {
			con = Application.getApp().getConnection();
			cs = con.prepareCall("{call V_SUB_LABOUR_I_A_AN(?,?,?,?,?,?,?)}");
			cs.setString(1, plhd);
			cs.setString(2, ngay);
			cs.setString(3, luongmoi);
			cs.setString(4, chucvu);
			cs.setString(5, luongtam);
			cs.setString(6, mshd);
			cs.setString(7, loai);
			cs.execute();
			save_ok = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			save_ok = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return save_ok;
	}

	public boolean saveHDLD(String msnv, String mshd, String ngayky,
			String thoigian, String chucvu, String donvi, String luong,
			String ghichu, String congviec, String luogtam) {
		boolean save = false;
		try {
			con = Application.getApp().getConnection();
			cs = con.prepareCall("{call TT_N_labour_I_A_N(?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, msnv);
			cs.setString(2, mshd);
			cs.setString(3, ngayky);
			cs.setString(4, thoigian);
			cs.setString(5, chucvu);
			cs.setString(6, donvi);
			cs.setString(7, luong);
			cs.setString(8, ghichu);
			cs.setString(9, congviec);
			cs.setString(10, luogtam);
			cs.execute();
			save = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			save = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return save;
	}

	public boolean upd_inc_salposs(String sothe, String ngaycu, String ngaymoi,
			String lcb_cu, String lcb_moi, String cv_cu, String cv_moi,
			String pccv_cu, String pccv_moi) {
		boolean save = false;
		try {
			con = Application.getApp().getConnection();
			cs = con.prepareCall("{call TR_N_INC_SALPOSS_U(?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, sothe);
			cs.setString(2, ngaycu);
			cs.setString(3, ngaymoi);
			cs.setString(4, lcb_cu);
			cs.setString(5, lcb_moi);
			cs.setString(6, cv_cu);
			cs.setString(7, cv_moi);
			cs.setString(8, pccv_cu);
			cs.setString(9, pccv_moi);

			cs.execute();
			save = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			save = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return save;
	}

	public boolean ins_inc_salposs(String sothe, String ngay, String lcb_cu,
			String lcb_moi, String cv_cu, String cv_moi, String pccv_cu,
			String pccv_moi) {
		boolean save = false;
		try {
			con = Application.getApp().getConnection();
			cs = con.prepareCall("{call TR_N_INC_SALPOSS_I(?,?,?,?,?,?,?,?)}");
			cs.setString(1, sothe);
			cs.setString(2, ngay);
			cs.setString(3, lcb_cu);
			cs.setString(4, lcb_moi);
			cs.setString(5, cv_cu);
			cs.setString(6, cv_moi);
			cs.setString(7, pccv_cu);
			cs.setString(8, pccv_moi);

			cs.execute();
			save = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			save = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return save;
	}

	public String findDept(String empsn) {
		String dept = "";
		String sql = "select d.name_dept from n_department d where id_dept in(select depsn from n_employee e where empsn='"
				+ empsn + "')";
		con = Application.getApp().getConnection();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				dept = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close_con(con, st);
		}
		return dept;
	}

	public boolean sua_bsaly(String sothe, String ngaycu, String ngaymoi,
			String luong, String ghichu) {
		boolean save = false;
		try {
			con = Application.getApp().getConnection();
			cs = con.prepareCall("{call HB_N_BASIC_SALARY_U_N(?,?,?,?,?)}");
			cs.setString(1, sothe);
			cs.setString(2, ngaycu);
			cs.setString(3, ngaymoi);
			cs.setString(4, luong);
			cs.setString(5, ghichu);

			cs.execute();
			save = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			save = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return save;
	}

	public boolean insert_bsaly(String sothe, String ngay, String luong,
			String note) {
		boolean save = false;
		try {
			con = Application.getApp().getConnection();

			cs = con.prepareCall("{call HB_N_BASIC_SALARY_I(?,?,?" +
			/* ",?" + */
			")}");
			cs.setString(1, sothe);
			cs.setString(2, ngay);
			cs.setString(3, luong);
			// cs.setString(4, note);// pro trong database chi co 3 gtri tham so
			// thoi ---------------------
			cs.execute();

			save = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			save = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return save;
	}

	public boolean xoa_bsaly(String sothe, String ngay) {
		boolean save = false;
		try {
			con = Application.getApp().getConnection();
			cs = con.prepareCall("{call HB_N_BASIC_SALARY_D(?,?)}");
			cs.setString(1, sothe);
			cs.setString(2, ngay);
			cs.execute();
			save = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			save = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return save;
	}

	public boolean del_inc_salposs(String sothe, String ngay) {
		boolean save = false;
		try {
			con = Application.getApp().getConnection();
			cs = con.prepareCall("{call TR_N_INC_SALPOSS_D(?,?)}");
			cs.setString(1, sothe);
			cs.setString(2, ngay);
			// cs.addBatch();
			// int[] i=cs.executeBatch();
			cs.execute();
			// System.out.println(i[0]);
			save = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			save = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return save;
	}

	public boolean delete_PhulucHDLD(String PLHD, String msHD) {
		boolean save = false;
		try {
			// loi khi chay pro V_SUB_LABOUR_D_N_A_1 nen chay
			// V_SUB_LABOUR_D_N_A_2 -----------------
			con = Application.getApp().getConnection();
			cs = con.prepareCall("{call V_SUB_LABOUR_D_N_A_2(?,?)}");
			cs.setString(1, PLHD);
			cs.setString(2, msHD);

			cs.execute();
			save = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			save = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return save;
	}

	public boolean xoa_bposs(String sothe, String ngay) {
		boolean save = false;
		try {
			con = Application.getApp().getConnection();
			cs = con.prepareCall("{call HB_N_BONUS_POSS_D(?,?)}");
			cs.setString(1, sothe);
			cs.setString(2, ngay);

			cs.execute();
			save = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			save = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return save;
	}

	public boolean sua_bposs(String sothe, String ngaycu, String ngaymoi,
			String luong, String cv) {
		boolean save = false;
		try {
			con = Application.getApp().getConnection();
			cs = con.prepareCall("{call HB_N_BONUS_POSS_U(?,?,?,?,?)}");
			cs.setString(1, sothe);
			cs.setString(2, ngaycu);
			cs.setString(3, ngaymoi);
			cs.setString(4, luong);
			cs.setString(5, cv);

			cs.execute();
			save = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			save = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return save;
	}

	public boolean nhap_bposs(String sothe, String ngay, String luong,
			String cv, String capbac) {
		boolean save = false;
		try {
			con = Application.getApp().getConnection();
			// / cai pro HB_N_BONUS_POSS_I chi co 4 tham sao thoi nhung o day
			// lai la 5 nen tao pro moi la HB_N_BONUS_POSS_I_N------------------
			cs = con.prepareCall("{call HB_N_BONUS_POSS_I_N(?,?,?,?,?)}");
			cs.setString(1, sothe);
			cs.setString(2, ngay);
			cs.setString(3, luong);
			cs.setString(4, cv);
			cs.setString(5, capbac);

			cs.execute();
			save = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			save = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return save;
	}

	public String whereclause_id(String empsn, String fact, String group,
			String depsn) {
		String temp = "";
		if (!empsn.equals("")) {
			temp += " where a.empsn in('" + empsn + "')";
		} else {
			if (!fact.equals("")) {
				temp += " where dt.name_fact='" + fact + "'";
			}
			if (!group.equals("")) {

				temp += " and dt.name_group='" + c.convertToVNI(group) + "'";
			}
			if (!depsn.equals("")) {
				temp += " and dt.name_dept_name='" + c.convertToVNI(depsn)
						+ "'";
			}
		}
		if (temp.equals("")) {
			temp = " where a.depsn=dt.id_dept";
		} else {
			temp += " and a.depsn=dt.id_dept";
		}
		return temp;
	}

	public String whereclause(String empsn, String fact, String group,
			String depsn) {
		String temp = "";
		if (!empsn.equals("")) {
			temp += " where e.empsn in('" + empsn + "')";
		} else {
			if (!fact.equals("")) {
				temp += " where d.name_fact='" + fact + "'";
			}
			if (!group.equals("")) {

				temp += " and d.name_group='" + c.convertToVNI(group) + "'";
			}
			if (!depsn.equals("")) {
				temp += " and d.name_dept_name='" + c.convertToVNI(depsn) + "'";
			}
		}
		if (temp.equals("")) {
			temp = " where e.depsn=d.id_dept";
		} else {
			temp += " and e.depsn=d.id_dept";
		}
		return temp;
	}

	public String whereclause_id_dept(String empsn, Object fact, Object group,
			Object depsn) {
		String temp = "";
		if (!empsn.equals("")) {
			temp += " where a.empsn in('" + empsn + "')";
		} else {
			if (fact != null) {
				temp += " where dt.name_fact='" + fact + "'";
			}
			if (group != null) {

				temp += " and dt.name_group='"
						+ c.convertToVNI(group.toString()) + "'";
			}
			if (depsn != null) {
				temp += " and dt.id_dept='" + depsn + "'";
			}
		}
		if (temp.equals("")) {
			temp = " where a.depsn=dt.id_dept ";
		} else {
			temp += " and a.depsn=dt.id_dept ";
		}
		return temp;
	}

	@SuppressWarnings("finally")
	public boolean update_atm(String mm, String yy, String empsn) {
		boolean up_ok = false;
		try {
			con = Application.getApp().getConnection();
			cs = con.prepareCall("{call update_atm(?,?,?)}");
			cs.setString(1, mm);
			cs.setString(2, yy);
			cs.setString(3, empsn);
			cs.execute();
			up_ok = true;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			up_ok = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (cs != null) {
					cs.close();
				}

			} catch (final Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return up_ok;
		}
	}

	public boolean xulydl(String empsn, String ngay, String loai) {
		boolean xl = false;
		try {
			con = Application.getApp().getConnection();
			cs = con.prepareCall("{call C1_CAL_EMPIC_DATA_A(?,?,?)}");
			cs.setString(1, empsn);
			cs.setString(2, ngay);
			cs.setString(3, loai);
			cs.execute();
			xl = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			xl = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		return xl;
	}

	public String getSQLHDLD(String empsn, String fact, String group,
			String dept, String date, String loai) {
		String sql = "";
		String temp = whereclause(empsn, fact, group, dept);
		sql = "Select e.empsn,d.name_dept,e.fname||' '||e.lname Fname, c.id,c.limit,b.date_s,"
				+ " b.expire ,b.poss,d.name_dept_name,decode(length(salary),7,substr(salary,0,1)||'.'||"
				+ " substr(salary,2,3)||'.'||substr(salary,5,3), "
				+ "substr(salary,0,2)||'.'||substr(salary,3,3)||'.'||substr(salary,6,3)) as salary,"
				+ " decode(length(e.birthday),10,SUBSTR(e.BIRTHDAY,1,2),'' ) as NGAY_SINH,"
				+ " decode(length(e.birthday),10,SUBSTR(e.BIRTHDAY,4,2),'' ) as THANG_SINH,"
				+ " decode(length(e.birthday),10,SUBSTR(e.BIRTHDAY,7,4),e.birthday ) as NAM_SINH,"
				+ " SUBSTR(e.BIRTHDAY,7,4) AS NAM_SINH,e.BIRTHPLACE,e.PERMANENT_ADDRESS,e.ID_NO,e.id_place ,e.NGAYCAP_ID as NGAY_CAP,"
				+
				// " SUBSTR(TO_CHAR(e.NGAYCAP_ID,'DD/MM/YYYY'),1,2) AS NGAY_CAP ,"
				// +
				// " SUBSTR(TO_CHAR(e.NGAYCAP_ID,'DD/MM/YYYY'),4,2) AS THANG_CAP ,"
				// +
				// " SUBSTR(TO_CHAR(e.NGAYCAP_ID,'DD/MM/YYYY'),7,4) AS NAM_CAP ,"
				// +
				" SUBSTR(TO_CHAR(B.DATE_S,'DD/MM/YYYY'),1,2) AS NGAY_KY  ,"
				+ " SUBSTR(TO_CHAR(B.DATE_S,'DD/MM/YYYY'),4,2) AS THANG_KY  ,"
				+ " SUBSTR(TO_CHAR(B.DATE_S,'DD/MM/YYYY'),7,4) AS NAM_KY  ,"
				+ " SUBSTR(TO_CHAR(B.Expire,'DD/MM/YYYY'),1,2) AS NGAY_HH  ,"
				+ " SUBSTR(TO_CHAR(B.Expire,'DD/MM/YYYY'),4,2) AS THANG_HH  ,"
				+ " SUBSTR(TO_CHAR(B.Expire,'DD/MM/YYYY'),7,4) AS NAM_HH  ,"
				+ " SUBSTR(TO_CHAR(e.date_hired,'DD/MM/YYYY'),1,2) AS NGAY_NX  ,"
				+ " SUBSTR(TO_CHAR(e.date_hired,'DD/MM/YYYY'),4,2) AS THANG_NX  ,"
				+ " SUBSTR(TO_CHAR(e.date_hired,'DD/MM/YYYY'),7,4) AS NAM_NX  ,e.date_hired,"
				+ " e.education,b.times,d.name_group,b.id_labour From n_employee e,n_department d, "
				+ " n_labour b, n_labourkind c  ";
		sql += temp;
		sql += " and e.empsn=b.empsn and b.limit=c.id  ";
		if (loai.equals("1")) {
			sql += " and b.times=1";
		} else {
			sql += " and B.TIMES>1 and B.TIMES<=3";
		}
		sql += "and b.date_s=to_date('" + date + "','dd/mm/yyyy')";

		return sql;
	}

	public String getSQLPLHD(String empsn, String xuong, String nhom,
			String dv, String ngay, String loai, boolean pl) {
		IGenericDAO<DSPB02, String> udao = Application.getApp().getDao(
				DSPB02.class);
		DSPB02 u = udao.findById(Application.getApp().getLoginInfo()
				.getUserID());
		String sql = "";
		String temp = whereclause_id(empsn, xuong, nhom, dv);
		if (empsn.equals("")) {
			sql = "Select a.empsn,dt.name_dept_name,a.fname||' '||a.lname as fname,"
					+ " b.dates_sign ,b.new_sal as bsalary,b.id_contract,c.id_labour,"
					+ " c.date_s as datesign,b.new_job as newjob ,"
					+ " decode(length(a.birthday),10,SUBSTR(A.BIRTHDAY,1,2),'' ) as NGAY_SINH,"
					+ " decode(length(a.birthday),10,SUBSTR(A.BIRTHDAY,4,2),'' ) as THANG_SINH,"
					+ " decode(length(a.birthday),10,SUBSTR(A.BIRTHDAY,7,4),a.birthday ) as NAM_SINH,"
					+ " A.BIRTHPLACE,A.PERMANENT_ADDRESS,A.ID_NO,a.id_place ,"
					+ " SUBSTR(TO_CHAR(A.NGAYCAP_ID,'DD/MM/YYYY'),1,2) AS NGAY_CAP ,"
					+ " SUBSTR(TO_CHAR(A.NGAYCAP_ID,'DD/MM/YYYY'),4,2) AS THANG_CAP ,"
					+ " SUBSTR(TO_CHAR(A.NGAYCAP_ID,'DD/MM/YYYY'),7,4) AS NAM_CAP ,"
					+ " dt.name_group  From n_Employee a, N_SUB_LABOUR b,n_labour c,"
					+ " n_User_Limit f ,n_department dt "
					+ " "
					+ temp
					+ ""
					+ " and a.depsn=dt.id_dept  AND A.EMPSN=SUBSTR(B.id_contract,1,8)"
					+ " AND B.ID_LABOUR=C.ID_LABOUR and b.dates_sign= to_date('"
					+ ngay
					+ "','dd/mm/yyyy') "
					+ " and a.User_Manage_Id=f.ma_ql AND f.ma_user='"
					+ u.getPB_USERNO() + "' ";
		} else {
			sql = "Select a.empsn,dt.name_dept_name,a.fname||' '||a.lname as fname  "
					+ ",b.dates_sign ,b.new_sal as bsalary,b.id_contract,c.id_labour,"
					+ " c.date_s as datesign,b.new_job as newjob ,"
					+ " decode(length(a.birthday),10,SUBSTR(A.BIRTHDAY,1,2),'''' ) as NGAY_SINH,"
					+ " decode(length(a.birthday),10,SUBSTR(A.BIRTHDAY,4,2),'''' ) as THANG_SINH,"
					+ " decode(length(a.birthday),10,SUBSTR(A.BIRTHDAY,7,4),a.birthday ) as NAM_SINH,"
					+ " A.BIRTHPLACE,A.PERMANENT_ADDRESS,A.ID_NO,a.id_place ,"
					+ " SUBSTR(TO_CHAR(A.NGAYCAP_ID,'DD/MM/YYYY'),1,2) AS NGAY_CAP ,"
					+ " SUBSTR(TO_CHAR(A.NGAYCAP_ID,'DD/MM/YYYY'),4,2) AS THANG_CAP ,"
					+ " SUBSTR(TO_CHAR(A.NGAYCAP_ID,'DD/MM/YYYY'),7,4) AS NAM_CAP ,dt.name_group  "
					+ " From n_Employee a, N_SUB_LABOUR b, N_LABOUR c , n_User_Limit f ,"
					+ " n_department dt  "
					+ " "
					+ temp
					+ ""
					+ " and a.empsn=c.empsn and c.ID_LABOUR=b.ID_labour  "
					+ " and A.EMPSN=SUBSTR(B.ID_CONTRACT,1,8) "
					+ " and b.dates_sign= to_date('"
					+ ngay
					+ "','dd/mm/yyyy') "
					+ " and a.User_Manage_Id=f.ma_ql AND f.ma_user='"
					+ u.getPB_USERNO() + "'";
		}

		if (!loai.equals("")) {
			sql += " and b.idsub_kind='" + loai + "'";
		}

		if (loai.equals("00001")) {
			if (pl) {
				sql += " and c.times=4";
			} else {
				sql += " and c.times>4";
			}
		}

		return sql;
	}
}
