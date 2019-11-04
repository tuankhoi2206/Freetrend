package ds.program.fvhr.ui.thinh;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;

import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import com.sun.org.apache.bcel.internal.generic.NEW;

import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.program.DefaultProgram;
import dsc.echo2app.component.DscField;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.CheckBox;
import dsc.echo2app.component.DscDateField;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.TextField;
import dsc.echo2app.component.DscPageNavigation;
import dsc.echo2app.component.table.DscPageableSortableTable;
import dsc.echo2app.component.table.PageableSortableTableModel;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ActionEvent;
import dsc.echo2app.component.event.FocusListener;
import dsc.echo2app.component.event.FocusEvent;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import dsc.echo2app.component.DscGroupRadioButton;
import dsc.util.function.UUID;
import echopointng.table.SortableTableColumn;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.table.DefaultTableColumnModel;
import nextapp.echo2.app.table.TableColumnModel;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.Font;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;
import java.util.Map;
import java.util.HashMap;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperFillManager;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.ReportUtils;
import fv.util.Vni2Uni;
import fv.util.lamtron;
import fv.util.library;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JRParameter;
import nextapp.echo2.app.Border;

public class t_hdhh extends DefaultProgram {

	private ResourceBundle resourceBundle;
	private DscField txtsothedeo;
	private Button bt_search;
	private Label sadasd;
	private SelectField select_xuong;
	private Label label19;
	private Label asdasdasda;
	private SelectField select_nhom;
	private Label label3;
	private Label awgsgdsaff;
	private SelectField select_dv;
	private Label label14;
	private RadioButton R_daky;
	private RadioButton R_chuaky;
	private DscDateField Dt_ngayky;
	private Button save;
	private Button xoa;
	private Button excel;
	DefaultListModel m_nhom;
	DefaultListModel m_dv;
	List list_nhom = new ArrayList();
	library l = new library();
	DefaultListModel att4;
	private DscPageNavigation Navigation;
	SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
	Connection con = null;
	ResultSet rs = null;
	ResultSet rs2 = null;
	ResultSet rs3 = null;
	Vni2Uni c = new Vni2Uni();
	PreparedStatement pstm = null;
	int f2 = 0;
	ArrayList arr = new ArrayList();
	ArrayList arr2 = new ArrayList();
	int tb1 = 0;
	int tb2 = 0;
	lamtron lt = new lamtron();
	DSPB02 s;

	/**
	 * Creates a new <code>t_hdhh</code>.
	 */
	public t_hdhh() {
		super();

		// Add design-time configured components.
		initComponents();

		ListBinder.bindSelectField(select_xuong, FVGenericInfo.getFactories(), true);

		TableColumnModel columnModel = loadColumnModel();
		Table1.setColumnModel(columnModel);
		PageableSortableTableModel model = new PageableSortableTableModel(columnModel);
		model.setSelectionModel(Table1.getSelectionModel());
		model.setRowsPerPage(10);
		Table1.setModel(model);
		Table1.setSelectionEnabled(true);
		Navigation.setTable(Table1);

		TableColumnModel columnModel1 = loadColumnModel1();
		Table2.setColumnModel(columnModel1);
		PageableSortableTableModel model1 = new PageableSortableTableModel(columnModel1);
		model1.setSelectionModel(Table2.getSelectionModel());
		model1.setRowsPerPage(10);
		Table2.setModel(model1);
		Table2.setSelectionEnabled(true);
		Navigation2.setTable(Table2);

		Dt_ngayky.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		Dt_ngayky.setSelectedDate(Calendar.getInstance(Locale.ENGLISH));
		Dt_ngayky.getDateChooser().setMonthNameLength(30);
		Table1.setVisible(false);
		Table2.setVisible(false);
		Navigation.setVisible(false);
		Navigation2.setVisible(false);
		R_chuaky.setSelected(true);
		label4.setVisible(false);
		label5.setVisible(false);
		grid3.setWidth(new Extent(100, Extent.PERCENT));

	}

	@Override
	protected int doInit() {
		// TODO Auto-generated method stub
		int ret = super.doInit();
		windowPane.setHeight(new Extent(Application.getApp().getScreenHeight()));
		windowPane.setWidth(new Extent(Application.getApp().getScreenWidth()));
		return ret;
	}

	private Label label10;
	private Label label4;
	private Label label5;
	private DscField dsc_ht;
	private DscPageNavigation Navigation2;
	private Label label8;
	private Grid grid3;
	private DscPageableSortableTable Table2;
	private DscPageableSortableTable Table1;

	private TableColumnModel loadColumnModel() {
		TableColumnModel columnModel = new DefaultTableColumnModel();
		for (int i = 0; i < 6; i++) {
			SortableTableColumn column = new SortableTableColumn(i);
			column.setHeaderRenderer(Table1.getDefaultHeaderRenderer());
			column.setComparator(l.INT_COMPARATOR);
			column.setModelIndex(i);
			column.setHeaderValue(getColumnHeader()[i]);
			columnModel.addColumn(column);
		}
		return columnModel;
	}

	private TableColumnModel loadColumnModel1() {
		TableColumnModel columnModel = new DefaultTableColumnModel();
		for (int i = 0; i < 15; i++) {
			SortableTableColumn column = new SortableTableColumn(i);
			column.setHeaderRenderer(Table2.getDefaultHeaderRenderer());
			column.setComparator(l.INT_COMPARATOR);
			column.setModelIndex(i);
			column.setHeaderValue(getColumnHeader1()[i]);
			columnModel.addColumn(column);
		}
		return columnModel;
	}

	private String[] getColumnHeader() {
		return new String[] { "SỐ THẺ", "HỌ TÊN", "CHỨC VỤ", "XƯỞNG", "NHÓM", "ĐƠN VỊ" };
	}

	private void fill_nhom(ActionEvent e) {
		m_nhom = (DefaultListModel) select_nhom.getModel();
		m_nhom.removeAll();
		select_nhom.setSelectedIndex(-1);
		if (select_dv.getModel().size() != 0) {
			m_dv.removeAll();
			select_dv.setSelectedIndex(-1);
		}
		list_nhom = l.group2(select_xuong.getSelectedItem().toString());
		for (int i = 0; i < list_nhom.size(); i++) {
			m_nhom.add(list_nhom.get(i));
		}

		txtsothedeo.setText("");

	}

	private void fill_donvi(ActionEvent e) {
		Object obj = new Object();
		obj = select_xuong.getSelectedItem();

		if (select_dv.getSelectedIndex() != -1) {
			select_dv.setSelectedIndex(-1);
		}
		m_dv = (DefaultListModel) select_dv.getModel();
		m_dv.removeAll();
		select_dv.setSelectedIndex(-1);

		if (obj == null) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"BẠN CẦN CHỌN XƯỞNG TRƯỚC");
		} else {
			// List list_donvi=l.dept_name(select_nhom.getSelectedItem().toString(),
			// select_xuong.getSelectedItem().toString());
			ListBinder.bindSelectField(select_dv, FVGenericInfo.getNameDeptName(ListBinder.get(select_xuong).toString(),
					select_nhom.getSelectedItem()), true);
			/*
			 * for (int i = 0; i < list_donvi.size(); i++) { m_dv.add(list_donvi.get(i)); }
			 */
		}
		txtsothedeo.setText("");
	}

	private void THI_(FocusEvent e) {
		select_xuong.setSelectedIndex(-1);
		select_nhom.setSelectedIndex(-1);
		select_dv.setSelectedIndex(-1);
	}

	private void load_nhanvienkyHDLD(String msnhanvien, String xuong, String nhom, String donvi) {
		if (txtsothedeo.getText().toString().compareTo("") != 0) {
			msnhanvien = txtsothedeo.getText().toString();
		}
		String ngay = Dt_ngayky.getText().toString();
		if ((msnhanvien.compareTo("") == 0) && (xuong.compareTo("") == 0) && (nhom.compareTo("") == 0)
				&& (donvi.compareTo("") == 0)) {
			msnhanvien = "test";
		}
		String sql = "SELECT a.fname||' '||a.Lname as fname, a.empsn,a.position ";
		sql += ",dt.name_dept,dt.name_fact ,dt.name_group,dt.name_dept_name ";
		sql += " FROM n_Employee a, n_department dt ";
		sql += " where a.depsn=dt.id_dept and a.depsn <>'00000'";
		sql += " and a.empsn in (select distinct b.empsn from N_labour b";
		if (R_chuaky.isSelected() == true) {
			sql += " Where b.expire=to_date('" + ngay + "','dd/mm/yyyy')) ";
			sql += " and a.empsn not in (select distinct b.empsn from N_labour b";
			sql += " Where b.date_s=to_date('" + ngay + "','dd/mm/yyyy')) ";
		} else {
			sql += " where b.date_s=to_date('" + ngay + "','dd/mm/yyyy'))";
		}
		if (msnhanvien.compareTo("") != 0) {
			sql += " and empsn='" + msnhanvien + "'";

		} else {
			if (xuong.compareTo("") != 0) {
				sql += " and dt.name_fact='" + xuong + "'";
			}
			if (nhom.compareTo("") != 0) {
				sql += " and dt.name_group='" + nhom + "'";
			}
			if (donvi.compareTo("") != 0) {
				sql += " and dt.name_dept_name='" + donvi + "'";
			}
		}

		sql += " order by a.depsn,a.empsn";
		con = Application.getApp().getConnection();
		PageableSortableTableModel model2 = (PageableSortableTableModel) Table1.getModel();
		model2.clear();

		Navigation.setTable(Table1);
		int i = 0, cc = 0;
		try {
			pstm = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs2 = pstm.executeQuery();
			rs2.last();
			cc = rs2.getRow();
			rs2.beforeFirst();
			while (rs2.next()) {
				model2.setValueAt(c.convertToUnicode(rs2.getString(2)), 0, i);
				model2.setValueAt(c.convertToUnicode(rs2.getString(1)), 1, i);
				model2.setValueAt(c.convertToUnicode(rs2.getString(3)), 2, i);
				model2.setValueAt(c.convertToUnicode(rs2.getString(5)), 3, i);
				model2.setValueAt(c.convertToUnicode(rs2.getString(6)), 4, i);
				model2.setValueAt(c.convertToUnicode(rs2.getString(7)), 5, i);
				i++;
				Navigation.reset();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (pstm != null) {
					pstm.close();
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
		label10.setText("Có " + cc + " Người");
		tb1 = i;
		f2 = i;
		// thongbao(sql);
	}

	private void danhsachHDLDdaihan(String ngay, String xuong, String nhom, String donvi, String loaiHD, boolean a) {
		int i = 0;
		String sql = "";
		sql = "SELECT a.fname||' '||a.lname as fname,a.empsn,a.position";
		sql += ",dt.name_fact, dt.name_group,dt.name_dept_name, ";
		sql += "b.date_s,b.Expire,max(b.times)+1 as Times , c.Bsalary ";
		sql += " FROM n_Employee a, n_Labour b , n_Basic_Salary c,n_department dt,n_sub_labour d ";
		sql += " Where a.empsn=b.empsn and a.depsn=dt.id_dept and b.clock=1 and b.id_labour =d.id_labour ";
		sql += " and d.clock=1 and a.empsn=c.empsn and keys=1 and a.depsn <>'00000'";
	}

	public void thongbao(String tt) {
		Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK, " " + tt);
	}

	private void tim_ac(ActionEvent e) {
		IGenericDAO<DSPB02, String> udao = Application.getApp().getDao(DSPB02.class);
		s = udao.findById(Application.getApp().getLoginInfo().getUserID());
		Table2.setVisible(false);
		Navigation2.setVisible(false);
		label5.setVisible(false);
		label8.setVisible(false);
		tb1 = 0;
		String xuong = "";
		String nhom = "";
		String donvi = "";

		Object obj = select_xuong.getSelectedItem();
		String empsn = txtsothedeo.getText().toString();
		if (obj != null) {
			xuong = select_xuong.getSelectedItem().toString();
		}

		Object obj1 = select_nhom.getSelectedItem();
		if (obj1 != null) {
			nhom = select_nhom.getSelectedItem().toString();
		}

		Object obj2 = select_dv.getSelectedItem();
		if (obj2 != null) {
			donvi = select_dv.getSelectedItem().toString();
		}
		if ((xuong == "") && (nhom == "") && (donvi == "") && (empsn == "")) {
			thongbao("Chưa chọn điều kiện xuất ");
			return;
		}
		Table1.setVisible(true);
		Navigation.setVisible(true);
		label4.setVisible(true);
		label10.setVisible(true);
		if (txtsothedeo.getText().toString().trim().compareTo("") != 0) {
			if (l.check_QL(txtsothedeo.getText().toString().trim(), "", "", "", "", s.getPB_USERNO()) == false) {
				return;
			}
		}
		load_nhanvienkyHDLD("", xuong, nhom, donvi);
	}

	private String Get_SQLHDLD(String empsn, String fact, String group, String depsn, String date) {
		String ngay = Dt_ngayky.getText().toString();
		String temp = where_clause1(empsn, fact, group, depsn);
		String sql = "SELECT a.empsn,a.fname,a.Lname,dt.name_dept,dt.name_fact as F_Group ";
		sql += ",dt.name_dept_name,to_char(a.date_hired,'dd/mm/yyyy') ";
		sql += ",max(b.times)+1 as times,a.position,c.bsalary ,b.note,b.id_labour,b.expire ngay_ss,a.sex ";
		sql += " FROM n_Employee a, n_department dt,n_labour b,n_basic_salary c ";
		sql += temp;
		sql += " and a.depsn <>'00000' and a.empsn=c.empsn ";
		sql += " and a.empsn=b.empsn and b.clock='1' and c.keys='1'";
		sql += " and b.limit<>'00000' and b.expire=to_date('" + date + "','dd/mm/yyyy')";
		sql += " Group by a.empsn,a.fname,a.Lname,dt.name_dept,dt.name_fact ,dt.name_group,dt.name_dept_name,a.date_hired ";
		sql += " ,a.position,c.bsalary,b.note,b.id_labour,b.expire,a.sex ";
		sql += " UNION ";
		sql += " SELECT a.empsn,a.fname,a.Lname,dt.name_dept,dt.name_fact as F_Group ";
		sql += " ,dt.name_dept_name,to_char(a.date_hired,'dd/mm/yyyy') ";
		sql += " ,max(b.times)+1 as times,a.position,c.bsalary ,b.note,b.id_labour,d.date_en ngay_ss,a.sex ";
		sql += " FROM n_Employee a, " + "n_labour b , n_Basic_Salary c,n_department dt,n_sub_labour d ";
		sql += temp;
		sql += " and a.empsn=b.empsn and a.empsn=c.empsn and b.id_labour =d.id_labour ";
		sql += " and b.clock='1' and d.clock='1' and C.keys='1'";
		sql += " and a.depsn <>'00000'";
		sql += " and d.date_en=to_date('" + date + "','dd/mm/yyyy')";
		sql += " and b.limit='00000'";
		sql += " Group by a.empsn,a.fname,a.Lname,dt.name_dept,dt.name_fact ,dt.name_group,dt.name_dept_name,a.date_hired ";
		sql += " ,a.position,c.bsalary ,b.note,b.id_labour,d.date_en,a.sex ";
		return sql;
	}

	private String where_clause1(String empsn, String xuong, String nhom, String donvi) {
		String temp = "";
		if (empsn != "") {
			temp += " where a.empsn in ('" + empsn + "')";
		} else {
			if (xuong != "") {
				temp += " where dt.name_fact='" + xuong + "'";
			}
			if (nhom != "") {
				if (xuong == "") {
					temp += " where dt.name_group='" + nhom + "'";
				} else {
					temp += " and dt.name_group='" + nhom + "'";
				}
			}
			if (donvi != "") {
				if ((xuong == "") && (nhom == "")) {
					temp += " where dt.name_dept_name='" + donvi + "'";
				} else {
					temp += " and dt.name_dept_name='" + donvi + "'";
				}
			}
		}
		if (temp == "") {
			temp = " Where a.depsn=dt.id_dept ";
		} else {
			temp += " and a.depsn=dt.id_dept ";
		}
		return temp;
	}

	private String[] getColumnHeader1() {
		return new String[] { "SỐ THẺ", "XƯỞNG", "ĐƠN VỊ", "HỌ", "TÊN", "NHẬP XƯỞNG", "LẦN", "CHỨC VỤ", "LƯƠNG CŨ",
				"LƯƠNG MỚI", "NGƯNG LÊN LƯƠNG", "XÉT DUYỆT", "GHI CHÚ", "SỐ HỢP ĐỒNG CŨ", "GIỚI TÍNH" };
	}

	private boolean Is_delaysalary(String empsn, String ngay) {
		boolean result = false;
		String ngay_temp = "14/" + ngay.substring(3, 10);
		String sql = " select * from n_kyluat t ";
		sql += " where t.empsn='" + empsn + "' and t.id_phat='02'";
		sql += " and to_date('" + ngay + "','dd/mm/yyyy') >=t.date_p ";
		sql += " and to_date('" + ngay_temp + "','dd/mm/yyyy') <t.date_hl ";
		con = Application.getApp().getConnection();
		try {
			pstm = con.prepareStatement(sql);
			rs3 = pstm.executeQuery();
			if (rs3.next()) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (pstm != null) {
					pstm.close();
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
		return result;
	}

	private String Get_tangluong(String empsn) {
		String result = "";
		String str = "";
		int lan_kyHD = 0;
		String day = HB_GetField2("n_employee", "date_hired", "empsn", empsn, "", "");
		int n = Integer.parseInt(day.substring(8, 10));
		int m = Integer.parseInt(day.substring(5, 7));
		int y = Integer.parseInt(day.substring(0, 4));
		Calendar calen33 = setgiatri(n, m, y);
		String ngaynx = df2.format(calen33.getTime());
		String str1 = "Select max(times)+1 as tt from n_labour where empsn='" + empsn + "'";
		// 2002-06-17
		con = Application.getApp().getConnection();
		try {
			pstm = con.prepareStatement(str1);
			rs = pstm.executeQuery();
			if (rs.next()) {
				lan_kyHD = Integer.parseInt(rs.getString("tt"));
			}
		} catch (Exception e) {
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
				e.printStackTrace();
			}
		}
		Calendar calen = setgiatri(Integer.parseInt(ngaynx.substring(0, 2)), Integer.parseInt(ngaynx.substring(3, 5)),
				Integer.parseInt(ngaynx.substring(6, 10)));
		Calendar calen1 = setgiatri(31, 8, 2006);
		if (calen.compareTo(calen1) < 0) {
			str = "select * from n_Incsalarybasic where id_year='" + lan_kyHD + "'";
			con = Application.getApp().getConnection();
			try {
				pstm = con.prepareStatement(str);
				rs = pstm.executeQuery();
				if (rs.next()) {
					if (Integer.parseInt(rs.getString("ID_YEAR")) <= 6) {
						result = rs.getString("salary_per");
					} else {
						result = "5";
					}
				} else {
					result = "5";
				}
			} catch (Exception e) {
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
					e.printStackTrace();
				}
			}
		} else {
			if (lan_kyHD >= 2) {
				result = "5";
			} else {
				result = "0";
			}
		}
		return result;
	}

	public Calendar setgiatri(int d, int m, int y) {
		m = m - 1;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, d);
		calendar.set(Calendar.MONTH, m);
		calendar.set(Calendar.YEAR, y);
		return calendar;
	}

	public String HB_GetField2(String table, String giatrilay, String dk, String value_dk, String dk2,
			String value_dk2) {
		String result = "";
		if ((dk != "" && value_dk != "") && (dk2 == "" && value_dk2 == "")) {
			con = Application.getApp().getConnection();
			String sql = "select " + giatrilay + " from " + table + " where " + dk + "=?";
			try {
				pstm = con.prepareStatement(sql);
				pstm.setString(1, value_dk);
				rs = pstm.executeQuery();
				if (rs.next()) {
					result = rs.getString(giatrilay);
				} else {
					result = "nodata";
				}
			} catch (Exception e) {
				e.printStackTrace();
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
						"Có lỗi HB_GetField");
			} finally {
				try {
					if (con != null) {
						con.close();
					}
					if (pstm != null) {
						pstm.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		if ((dk != "" && value_dk != "") && (dk2 != "" && value_dk2 != "")) {
			con = Application.getApp().getConnection();
			String sql = "select " + giatrilay + " from " + table + " where " + dk + "=? and " + dk2 + "=?";
			try {
				pstm = con.prepareStatement(sql);
				pstm.setString(1, value_dk);
				pstm.setString(2, value_dk2);
				rs = pstm.executeQuery();
				if (rs.next()) {
					result = rs.getString(giatrilay);
				} else {
					result = "nodata";
				}
			} catch (Exception e) {
				e.printStackTrace();
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
						"Có lỗi HB_GetField");
			} finally {
				try {
					if (con != null) {
						con.close();
					}
					if (pstm != null) {
						pstm.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private void giahan_ac(ActionEvent e) {
		Table1.setVisible(false);
		Navigation.setVisible(false);
		label4.setVisible(false);
		label10.setVisible(false);
		tb2 = 0;
		String empsn = "";
		String xuong = "";
		String nhom = "";
		String donvi = "";
		int heso = 0;
		String hs = "";
		float luong_o;
		float luong_n;
		String ngay = Dt_ngayky.getText().toString();
		empsn = txtsothedeo.getText().toString();
		Object obj = select_xuong.getSelectedItem();
		if (obj != null) {
			xuong = select_xuong.getSelectedItem().toString();
		}

		Object obj1 = select_nhom.getSelectedItem();
		if (obj1 != null) {
			nhom = select_nhom.getSelectedItem().toString();
		}

		Object obj2 = select_dv.getSelectedItem();
		if (obj2 != null) {
			donvi = select_dv.getSelectedItem().toString();
		}
		if ((xuong == "") && (nhom == "") && (donvi == "") && (empsn == "")) {
			thongbao("Chưa chọn điều kiện xuất ");
			return;
		}
		String sql = Get_SQLHDLD(empsn, xuong, nhom, donvi, ngay);
		con = Application.getApp().getConnection();
		PageableSortableTableModel model2 = (PageableSortableTableModel) Table2.getModel();
		model2.clear();

		Navigation2.setTable(Table2);
		int i = 0;
		// thongbao(sql);
		try {
			pstm = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs2 = pstm.executeQuery();
			rs2.last();
			rs2.beforeFirst();
			while (rs2.next()) {
				model2.setValueAt(c.convertToUnicode(rs2.getString(1)), 0, i);
				hs = Get_tangluong(rs2.getString(1));
				model2.setValueAt(c.convertToUnicode(rs2.getString(5)), 1, i);
				model2.setValueAt(c.convertToUnicode(rs2.getString(6)), 2, i);
				model2.setValueAt(c.convertToUnicode(rs2.getString(2)), 3, i);
				model2.setValueAt(c.convertToUnicode(rs2.getString(3)), 4, i);
				model2.setValueAt(c.convertToUnicode(rs2.getString(7)), 5, i);
				model2.setValueAt(c.convertToUnicode(rs2.getString(8)), 6, i);
				model2.setValueAt(c.convertToUnicode(rs2.getString(9)), 7, i);
				luong_o = Integer.parseInt(rs2.getString("bsalary"));
				model2.setValueAt(c.convertToUnicode(rs2.getString(10)), 8, i);
				if (Is_delaysalary(rs2.getString(1), ngay) == true) {
					model2.setValueAt(luong_o, 9, i);
					model2.setValueAt("NGUNG LEN LUONG", 10, i);
				} else {
					luong_n = luong_o + luong_o * (Integer.parseInt(hs)) / 100;
					model2.setValueAt(lt.testLamTron(luong_n), 9, i);
					model2.setValueAt("", 10, i);
				}

				model2.setValueAt("", 11, i);
				model2.setValueAt("", 12, i);
				model2.setValueAt(c.convertToUnicode(rs2.getString(12)), 13, i);
				model2.setValueAt(c.convertToUnicode(rs2.getString(14)), 14, i);
				i++;
				Navigation2.reset();
			}
			Table2.setVisible(true);
			Navigation2.setVisible(true);
			label5.setVisible(true);
			label8.setVisible(true);
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			try {
				if (con.isClosed()) {
					// System.out.println("con is close ok");
					l.close_con(null, pstm);
				} else {
					l.close_con(con, pstm);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		label8.setText("Có " + i + " Người");
		tb2 = i;
	}

	private void demo(ActionEvent e) {
		// thongbao(Is_delaysalary(txtsothedeo.getText().toString(),"01/08/2013")+"");
		// Calendar c1 =setgiatri(31,8,2013);
		// Calendar c2 =setgiatri(30,8,2013);
		// thongbao(c2.compareTo(c1)+"");
		// thongbao("asdasda");
		select_xuong.setSelectedIndex(-1);
		select_nhom.setSelectedIndex(-1);
		select_dv.setSelectedIndex(-1);
		Table1.setVisible(false);
		Table2.setVisible(false);
		Navigation.setVisible(false);
		Navigation2.setVisible(false);
		label4.setVisible(false);
		label5.setVisible(false);
		tb1 = 0;
		tb2 = 0;
		label10.setVisible(false);
		label8.setVisible(false);
	}

	private void ex_ac(ActionEvent e) {

		if (Table1.isVisible()) {
			try {
				doExportDataObjectSet();
			} catch (Exception ee) {
				ee.printStackTrace();
				thongbao("Không thể xuất EXCEL 1");
			}
		}
		if (Table2.isVisible()) {
			try {
				doExportDataObjectSet1();
			} catch (Exception ee) {
				ee.printStackTrace();
				thongbao("Không thể xuất EXCEL");
			}
		}
	}

	private void doExportDataObjectSet() throws IOException {
		int i = 0;
		String str = "";
		StopWatch sw = new StopWatch();
		sw.start();

		if (f2 == 0) {
			thongbao("KHÔNG CÓ DỮ LIỆU ĐỂ XUẤT");
			return;
		} else {
			File f = ReportFileManager.getReportFormatFolder("ex");
			InputStream in = new FileInputStream(new File(f.getPath(), "hhhd.xls"));
			HSSFWorkbook wb = new HSSFWorkbook(in);
			int m = 0;// row 1
			int n = 2;
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFSheet sheet1 = wb.getSheetAt(1);
			HSSFRow row = null;
			HSSFCell cell = null;
			HSSFFont font = wb.createFont();
			HSSFCellStyle style = wb.createCellStyle();
			font.setFontName("Arial");
			font.setColor(HSSFColor.RED.index);
			font.setFontHeightInPoints((short) 15);
			style.setFont(font);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			Object obj;

			row = sheet.createRow(0);

			cell = row.createCell(0);
			cell.setCellStyle(style);

			String vl = "DANH SÁCH HĐLĐ HẾT HẠN";
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
			cell.setCellValue(vl);
			if (f2 <= 600) {
				// thongbao("XUẤT TỪ TABLE NGÀY CÔNG");
				m = 2;
				for (int ii = 0; ii < f2; ii++) {
					PageableSortableTableModel model = (PageableSortableTableModel) Table1.getModel();
					model.setCurrentPage(0);
					row = sheet.createRow(m);
					cell = row.createCell(0);
					cell.setCellValue(model.getValueAt(0, ii).toString()); // depsn
					cell = row.createCell(1);
					cell.setCellValue((model.getValueAt(1, ii).toString())); // name_dept_name
					cell = row.createCell(2);
					cell.setCellValue(model.getValueAt(2, ii).toString()); // empsn
					cell = row.createCell(3);
					cell.setCellValue((model.getValueAt(3, ii).toString())); // name
					cell = row.createCell(4);
					cell.setCellValue(model.getValueAt(4, ii).toString()); // real_off_date
					cell = row.createCell(5);
					cell.setCellValue(model.getValueAt(5, ii).toString()); // real_off_date
					m++;
				}
			}

			for (int j = 0; j < 9; j++) {
				sheet.autoSizeColumn(j);
			}

			sw.stop();
			System.out.println("Export time ->>> " + (float) sw.getTime() / 1000);
			File f1 = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
			FileOutputStream out = new FileOutputStream(f1);
			wb.write(out);
			out.flush();
			out.close();
			long t = Calendar.getInstance().getTimeInMillis();
			File saveFile;

			saveFile = new File(f1.getParentFile(), URLEncoder.encode(
					getLoginInfo().getUserID() + ";application/vnd.ms-excel;" + "CHI_TIET_NGAY_CONG" + t + ".xls",
					"UTF-8"));
			ReportFileManager.saveTempReportFile(f1, saveFile);
			Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));

		}

	}

	private void doExportDataObjectSet1() throws IOException {

		int i = 0;
		String str = "";
		StopWatch sw = new StopWatch();
		sw.start();

		if (tb2 == 0) {
			thongbao("KHÔNG CÓ DỮ LIỆU ĐỂ XUẤT");
			return;
		} else {
			File f = ReportFileManager.getReportFormatFolder("ex");
			InputStream in = new FileInputStream(new File(f.getPath(), "hhhd2.xls"));
			HSSFWorkbook wb = new HSSFWorkbook(in);
			int m = 0;// row 1
			int n = 2;
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFSheet sheet1 = wb.getSheetAt(1);
			HSSFRow row = null;
			HSSFCell cell = null;
			HSSFFont font = wb.createFont();
			HSSFCellStyle style = wb.createCellStyle();
			font.setFontName("Arial");
			font.setColor(HSSFColor.RED.index);
			font.setFontHeightInPoints((short) 15);
			style.setFont(font);

			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			Object obj;

			row = sheet.createRow(0);

			cell = row.createCell(0);
			cell.setCellStyle(style);

			String vl = "DANH SÁCH KÍ HĐLĐ DÀI HẠN";
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));

			cell.setCellValue(vl);

			if (tb2 <= 1000) {
				m = 3;
				for (int ii = 0; ii < tb2; ii++) {
					PageableSortableTableModel model = (PageableSortableTableModel) Table2.getModel();
					model.setCurrentPage(0);
					row = sheet.createRow(m);
					cell = row.createCell(0);
					cell.setCellValue(model.getValueAt(0, ii).toString()); // empsn
					cell = row.createCell(1);
					cell.setCellValue((model.getValueAt(1, ii).toString())); // fact
					cell = row.createCell(2);
					cell.setCellValue(model.getValueAt(2, ii).toString()); // dept_name
					cell = row.createCell(3);
					cell.setCellValue((model.getValueAt(3, ii).toString()) + " " + model.getValueAt(4, ii).toString()); // name
					cell = row.createCell(4);
					cell.setCellValue(model.getValueAt(5, ii).toString()); // date_hired
					cell = row.createCell(5);
					cell.setCellValue(model.getValueAt(6, ii).toString()); // time_sign
					cell = row.createCell(6);
					cell.setCellValue(model.getValueAt(7, ii).toString()); // poss
					cell = row.createCell(7);
					cell.setCellValue(model.getValueAt(8, ii).toString()); // salary
					cell = row.createCell(8);
					cell.setCellValue(model.getValueAt(9, ii).toString()); // salary_new
					cell = row.createCell(9);
					cell.setCellValue(model.getValueAt(10, ii).toString()); // ngung nang luong
					cell = row.createCell(10);
					cell.setCellValue(model.getValueAt(11, ii).toString()); // xet duyet
					cell = row.createCell(11);
					cell.setCellValue(model.getValueAt(12, ii).toString()); // ghi chu
					cell = row.createCell(12);
					cell.setCellValue(model.getValueAt(13, ii).toString()); // id_labour
					cell = row.createCell(13);
					cell.setCellValue(model.getValueAt(14, ii).toString()); // sex

					m++;
				}
			}
			for (int j = 0; j < 14; j++) {
				sheet.autoSizeColumn(j);
			}

			sw.stop();
			System.out.println("Export time ->>> " + (float) sw.getTime() / 1000);
			File f1 = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
			FileOutputStream out = new FileOutputStream(f1);
			wb.write(out);
			out.flush();
			out.close();
			long t = Calendar.getInstance().getTimeInMillis();
			File saveFile;

			saveFile = new File(f1.getParentFile(), URLEncoder.encode(
					getLoginInfo().getUserID() + ";application/vnd.ms-excel;" + "CHI_TIET_NGAY_CONG" + t + ".xls",
					"UTF-8"));
			ReportFileManager.saveTempReportFile(f1, saveFile);
			Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));

		}

	}

	public String getViewerUrl() {
		HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
		String viewerUrl = request.getRequestURL() + "?" + WebRenderServlet.SERVICE_ID_PARAMETER + "="
				+ ReportService.INSTANCE.getId() + "&" + ReportService.PARAM_TYPE + "=" + ReportService.TYPE_TEMP + "&"
				+ ReportService.PARAM_KEY + "=";
		return viewerUrl;
	}

	private void in_ra(ActionEvent e) {

		if (Table2.getModel().getRowCount() == 0 || !Table2.isVisible()) {
			l.ShowMessageError("Không có dl");
			return;
		}
		if (dsc_ht.getText().equals("")) {
			l.ShowMessageInfo("Nhập họ tên cán bộ in báo biểu");

			dsc_ht.setBackground(new Color(255, 255, 100));
			return;
		}
		String ngay = "";
		ngay = Dt_ngayky.getText().toString();
		String xuong = "";
		String where_c = "";
		Object obj = select_xuong.getSelectedItem();

		if (obj != null) {
			xuong = select_xuong.getSelectedItem().toString();
			where_c = "dt.name_fact='" + xuong + "'";
		}
		if (select_nhom.getSelectedIndex() != -1) {
			where_c += " and dt.name_group='" + c.convertToVNI(select_nhom.getSelectedItem().toString()) + "'";
		}
		if (select_dv.getSelectedIndex() != -1) {
			String dept = ListBinder.get(select_dv).toString();
			where_c += " and dt.id_dept='" + dept + "'";
		}
		if (xuong.compareTo("") == 0) {
			thongbao("Chọn xưởng cần xuất");
			return;
		}
		if (ngay.compareTo("") == 0) {
			thongbao("Chọn ngày cần xuất");
			return;
		}
		JasperDesign jd;

		// String sql="SELECT a.empsn,a.fname,a.Lname,dt.name_dept,dt.name_fact||'
		// '||dt.name_group as F_Group ,dt.name_dept_name,a.date_hired ,max(b.times)+1
		// as times,a.position,c.bsalary ,b.note,b.id_labour,b.expire ngay_ss FROM
		// n_Employee a, n_department dt,n_labour b,n_basic_salary c where
		// dt.name_fact='"+xuong+"'and a.depsn=dt.id_dept and a.depsn <>'00000' and
		// a.empsn=c.empsn and a.empsn=b.empsn and b.clock='1' and c.keys='1' and
		// b.limit<>'00000' and b.expire=to_date('"+ngay+"','dd/mm/yyyy') Group by
		// a.empsn,a.fname,a.Lname,dt.name_dept,dt.name_fact
		// ,dt.name_group,dt.name_dept_name,a.date_hired
		// ,a.position,c.bsalary,b.note,b.id_labour,b.expire UNION SELECT
		// a.empsn,a.fname,a.Lname,dt.name_dept,dt.name_fact||' '||dt.name_group as
		// F_Group ,dt.name_dept_name,a.date_hired ,max(b.times)+1 as
		// times,a.position,c.bsalary ,b.note,b.id_labour,d.date_en ngay_ss FROM
		// n_Employee a, n_Labour b , n_Basic_Salary c,n_department dt,n_sub_labour d
		// where dt.name_fact='"+xuong+"' and a.depsn=dt.id_dept and a.empsn=b.empsn and
		// a.empsn=c.empsn and b.id_labour =d.id_labour and b.clock='1' and d.clock='1'
		// and C.keys='1' and a.depsn <>'00000' and
		// d.date_en=to_date('"+ngay+"','dd/mm/yyyy') and b.limit='00000' Group by
		// a.empsn,a.fname,a.Lname,dt.name_dept,dt.name_fact
		// ,dt.name_group,dt.name_dept_name,a.date_hired ,a.position,c.bsalary
		// ,b.note,b.id_labour,d.date_en ";
		String sql = "SELECT a.empsn,a.fname,a.Lname,dt.name_dept,dt.name_fact as F_Group ,"
				+ " dt.name_dept_name,a.date_hired ,a.sex,b.times+1 as times,"
				+ " (select decode(nvl(t.id_phat,0),'02','NGUNG LÊN','') from n_kyluat t where "
				+ " t.empsn=a.empsn and to_date('" + ngay + "','dd/mm/yyyy') >=t.date_p " + " and to_date('14/"
				+ ngay.substring(3, 10) + "','dd/mm/yyyy') <t.date_hl) as NGUNG_LEN,"
				+ " a.position,c.bsalary ,(select (case when(count(t.id_phat)>0) then c.bsalary "
				+ " when(count(t.id_phat)=0) then round(((c.bsalary*0.05)+c.bsalary),-3) END) from n_kyluat t "
				+ " where t.empsn=a.empsn and to_date('" + ngay + "','dd/mm/yyyy') >=t.date_p " + " and to_date('14/"
				+ ngay.substring(3, 10) + "','dd/mm/yyyy') <t.date_hl and "
				+ " t.id_phat='02') as new_luong,b.note,b.id_labour,b.expire ngay_ss "
				+ " FROM n_Employee a, n_department dt,n_labour b,n_basic_salary c  " + " where " + where_c
				+ " and a.depsn=dt.id_dept  and a.depsn <>'00000' and a.empsn=c.empsn "
				+ " and a.empsn=b.empsn and b.clock='1' and c.keys='1' and b.limit<>'00000' "
				+ " and b.expire=to_date('" + ngay + "','dd/mm/yyyy') "
				+ "UNION  SELECT a.empsn,a.fname,a.Lname,dt.name_dept,dt.name_fact as F_Group  ,"
				+ " dt.name_dept_name,a.date_hired ,a.sex,b.times+1 as times,"
				+ " (select decode(nvl(t.id_phat,0),'02','NGUNG LÊN','') from n_kyluat t "
				+ " where t.empsn=a.empsn and to_date('" + ngay + "','dd/mm/yyyy') >=t.date_p " + " and to_date('14/"
				+ ngay.substring(3, 10) + "','dd/mm/yyyy') <t.date_hl) "
				+ " as NGUNG_LEN,a.position,c.bsalary ,(select (case when(count(t.id_phat)>0) "
				+ " then c.bsalary when(count(t.id_phat)=0) then round(((c.bsalary*0.05)+c.bsalary),-3) END) "
				+ " from n_kyluat t where t.empsn=a.empsn and " + " to_date('" + ngay + "','dd/mm/yyyy') >=t.date_p "
				+ " and to_date('14/" + ngay.substring(3, 10) + "','dd/mm/yyyy') <t.date_hl "
				+ " and t.id_phat='02') as new_luong,b.note,b.id_labour,d.date_en ngay_ss  "
				+ " FROM n_Employee a, n_Labour b , n_Basic_Salary c,n_department dt," + " n_sub_labour d  where "
				+ where_c + " and a.depsn=dt.id_dept  and a.empsn=b.empsn and a.empsn=c.empsn "
				+ " and b.id_labour =d.id_labour  and b.clock='1' and d.clock='1' and C.keys='1'"
				+ " and a.depsn <>'00000' and d.date_en=to_date('" + ngay + "','dd/mm/yyyy')" + " and b.limit='00000'";
		// thongbao(ngay+" "+"14/"+ngay.substring(3,10)+" "+sql);
		// CÒN XUẤT HIỆN CHỮ OK TRONG FILE PDF !
		// CHỮ TRONG REPORT ĐỊNH DẠNG LẠI THÀNH ARIAL HẾT
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if (select_nhom.getSelectedIndex() != -1) {
				params.put("abc", select_nhom.getSelectedItem().toString());
			} else {
				params.put("abc", select_xuong.getSelectedItem().toString());
			}
			params.put("ngay", ngay);
			params.put("name", dsc_ht.getText());
			jd = JRXmlLoader.load(ReportFileManager.getReportFormatFolder("fvhr/ghhd.jrxml"));
			JRDesignQuery query = new JRDesignQuery();
			query.setText(sql);
			jd.setQuery(query);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			con = Application.getApp().getConnection();
			JasperPrint jp = JasperFillManager.fillReport(jr, params, con);
			File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
			JasperExportManager.exportReportToPdfFile(jp, f.getPath());

			File saveFile = new File(f.getParentFile(),
					URLEncoder.encode(getLoginInfo().getUserID() + ";application/pdf;" + "DSGH_" + Dt_ngayky.getText()
							+ "_" + Calendar.getInstance().getTimeInMillis() + ".pdf", "UTF-8"));
			ReportFileManager.saveTempReportFile(f, saveFile);
			Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
			System.out.println("----ok");
		} catch (Exception eee) {
			// TODO: handle exception
			eee.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {

			}
		}

	}

	/**
	 * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(30, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane1.setResizable(true);
		splitPane1.setSeparatorHeight(new Extent(1, Extent.PX));
		add(splitPane1);
		ContentPane contentPane1 = new ContentPane();
		splitPane1.add(contentPane1);
		Grid grid1 = new Grid();
		grid1.setSize(3);
		contentPane1.add(grid1);
		Row row1 = new Row();
		row1.setAlignment(new Alignment(Alignment.LEFT, Alignment.DEFAULT));
		grid1.add(row1);
		Label label74 = new Label();
		label74.setText("Số thẻ đeo");
		RowLayoutData label74LayoutData = new RowLayoutData();
		label74LayoutData.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		label74LayoutData.setWidth(new Extent(237, Extent.PX));
		label74.setLayoutData(label74LayoutData);
		row1.add(label74);
		txtsothedeo = new DscField();
		txtsothedeo.setInputType(DscField.INPUT_TYPE_NUMERIC);
		txtsothedeo.setMaximumLength(8);
		txtsothedeo.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				THI_(e);
			}

			public void focusLost(FocusEvent e) {
			}
		});
		row1.add(txtsothedeo);
		bt_search = new Button();
		ResourceImageReference imageReference1 = new ResourceImageReference("/dsc/echo2app/resource/image/an_tim.png");
		bt_search.setIcon(imageReference1);
		bt_search.setRolloverEnabled(true);
		bt_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tim_ac(e);
			}
		});
		row1.add(bt_search);
		save = new Button();
		ResourceImageReference imageReference2 = new ResourceImageReference("/dsc/echo2app/resource/image/an_in.png");
		save.setIcon(imageReference2);
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				in_ra(e);
			}
		});
		row1.add(save);
		xoa = new Button();
		ResourceImageReference imageReference3 = new ResourceImageReference("/dsc/echo2app/resource/image/an_xoa.png");
		xoa.setIcon(imageReference3);
		xoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				demo(e);
			}
		});
		row1.add(xoa);
		excel = new Button();
		ResourceImageReference imageReference4 = new ResourceImageReference("/dsc/echo2app/resource/image/Excel.gif");
		excel.setIcon(imageReference4);
		excel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ex_ac(e);
			}
		});
		row1.add(excel);
		SplitPane splitPane2 = new SplitPane();
		splitPane2.setSeparatorPosition(new Extent(320, Extent.PX));
		splitPane2.setSeparatorWidth(new Extent(1, Extent.PX));
		splitPane2.setResizable(true);
		splitPane1.add(splitPane2);
		Grid grid2 = new Grid();
		grid2.setInsets(new Insets(new Extent(1, Extent.PX)));
		grid2.setSize(4);
		SplitPaneLayoutData grid2LayoutData = new SplitPaneLayoutData();
		grid2LayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		grid2.setLayoutData(grid2LayoutData);
		splitPane2.add(grid2);
		Label safdffadfafdfasdf = new Label();
		safdffadfafdfasdf.setText("Hết hạn");
		grid2.add(safdffadfafdfasdf);
		Dt_ngayky = new DscDateField();
		Dt_ngayky.setWidth(new Extent(155, Extent.PX));
		Dt_ngayky.setToolTipText("ngày thay đổi");
		grid2.add(Dt_ngayky);
		Label label17 = new Label();
		grid2.add(label17);
		Label label18 = new Label();
		grid2.add(label18);
		sadasd = new Label();
		sadasd.setText("XƯỞNG");
		grid2.add(sadasd);
		select_xuong = new SelectField();
		select_xuong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fill_nhom(e);
			}
		});
		grid2.add(select_xuong);
		label19 = new Label();
		grid2.add(label19);
		Label label21 = new Label();
		grid2.add(label21);
		asdasdasda = new Label();
		asdasdasda.setText("NHÓM ĐV");
		grid2.add(asdasdasda);
		select_nhom = new SelectField();
		select_nhom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fill_donvi(e);
			}
		});
		grid2.add(select_nhom);
		label3 = new Label();
		grid2.add(label3);
		Label label22 = new Label();
		grid2.add(label22);
		awgsgdsaff = new Label();
		awgsgdsaff.setText("ĐƠN VỊ");
		grid2.add(awgsgdsaff);
		select_dv = new SelectField();
		grid2.add(select_dv);
		label14 = new Label();
		grid2.add(label14);
		Label label23 = new Label();
		grid2.add(label23);
		Row row2 = new Row();
		grid2.add(row2);
		Row row3 = new Row();
		grid2.add(row3);
		Label label28 = new Label();
		grid2.add(label28);
		Label label29 = new Label();
		grid2.add(label29);
		Label label30 = new Label();
		grid2.add(label30);
		Label label31 = new Label();
		grid2.add(label31);
		Label label36 = new Label();
		grid2.add(label36);
		Label label37 = new Label();
		grid2.add(label37);
		Label label2 = new Label();
		label2.setText("TRẠNG THÁI");
		grid2.add(label2);
		DscGroupRadioButton dscGroupRadioButton2 = new DscGroupRadioButton();
		dscGroupRadioButton2.setInsets(new Insets(new Extent(6, Extent.PX), new Extent(0, Extent.PX)));
		dscGroupRadioButton2.setSize(2);
		grid2.add(dscGroupRadioButton2);
		R_daky = new RadioButton();
		R_daky.setText("Đã kí");
		dscGroupRadioButton2.add(R_daky);
		R_chuaky = new RadioButton();
		R_chuaky.setText("Chưa kí");
		dscGroupRadioButton2.add(R_chuaky);
		Label label32 = new Label();
		grid2.add(label32);
		Label label33 = new Label();
		grid2.add(label33);
		Label label6 = new Label();
		grid2.add(label6);
		Button button1 = new Button();
		button1.setText("DS GIA HẠN HỢP ĐỒNG");
		button1.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_INSET));
		button1.setRolloverBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_OUTSET));
		button1.setFont(new Font(null, Font.BOLD, new Extent(11, Extent.PT)));
		button1.setBackground(new Color(0xc0c0c0));
		button1.setPressedEnabled(true);
		button1.setBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_GROOVE));
		button1.setRolloverEnabled(true);
		button1.setForeground(Color.RED);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				giahan_ac(e);
			}
		});
		grid2.add(button1);
		Label label11 = new Label();
		grid2.add(label11);
		Label label12 = new Label();
		grid2.add(label12);
		Label label13 = new Label();
		label13.setText("Họ tên CB");
		grid2.add(label13);
		dsc_ht = new DscField();
		grid2.add(dsc_ht);
		grid3 = new Grid();
		grid3.setOrientation(Grid.ORIENTATION_HORIZONTAL);
		grid3.setInsets(new Insets(new Extent(0, Extent.PX)));
		grid3.setSize(1);
		splitPane2.add(grid3);
		label4 = new Label();
		label4.setText("DANH SÁCH NHÂN VIÊN HĐLĐ HẾT HẠN");
		label4.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		label4.setForeground(new Color(0x04c4fb));
		grid3.add(label4);
		Grid grid5 = new Grid();
		grid5.setHeight(new Extent(0, Extent.PX));
		grid5.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		grid5.setSize(1);
		grid3.add(grid5);
		label10 = new Label();
		grid3.add(label10);
		Grid grid4 = new Grid();
		grid4.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(3, Extent.PX), new Extent(0, Extent.PX),
				new Extent(0, Extent.PX)));
		grid4.setSize(2);
		grid3.add(grid4);
		Navigation = new DscPageNavigation();
		Navigation.setRowsPerPage(20);
		Navigation.setBackground(new Color(0xb8dcdc));
		grid3.add(Navigation);
		Table1 = new DscPageableSortableTable();
		Table1.setStyleName("Table.DscPageableSortableTable");
		Table1.setAutoCreateColumnsFromModel(false);
		Table1.setSelectionBackground(new Color(0x00ff40));
		Table1.setSelectionEnabled(true);
		grid3.add(Table1);
		Row row8 = new Row();
		grid3.add(row8);
		Label label7 = new Label();
		row8.add(label7);
		Label label9 = new Label();
		row8.add(label9);
		Row row5 = new Row();
		grid3.add(row5);
		label5 = new Label();
		label5.setText("DANH SÁCH XÉT DUYỆT GIA HẠN HỢP ĐỒNG");
		label5.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		label5.setForeground(new Color(0x0093ff));
		row5.add(label5);
		label8 = new Label();
		grid3.add(label8);
		Navigation2 = new DscPageNavigation();
		Navigation2.setRowsPerPage(20);
		Navigation2.setBackground(new Color(0xb8dcdc));
		grid3.add(Navigation2);
		Table2 = new DscPageableSortableTable();
		Table2.setStyleName("Table.DscPageableSortableTable");
		Table2.setAutoCreateColumnsFromModel(false);
		Table2.setSelectionBackground(new Color(0x00ff40));
		Table2.setSelectionEnabled(true);
		grid3.add(Table2);
	}
}
