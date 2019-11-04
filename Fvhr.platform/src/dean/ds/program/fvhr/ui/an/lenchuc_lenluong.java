package ds.program.fvhr.ui.an;

import java.io.File;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.record.formula.udf.UDFFinder;
import org.hibernate.validator.Email;

import ds.program.fvhr.dao.generic.FvGenericDAO;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.domain.N_POSITION;
import ds.program.fvhr.domain.N_POSS_SALARY;
import ds.program.fvhr.ui.insurance.OBJ_UTILITY;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.special_dialog;
import dsc.echo2app.program.DefaultProgram;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.FvDateTimeField;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SelectField;
import dsc.echo2app.component.DscDateField;
import nextapp.echo2.app.Button;
import dsc.echo2app.component.DscPageNavigation;
import dsc.echo2app.component.binder.SelectBinder;
import dsc.echo2app.component.table.DscPageableSortableTable;
import dsc.echo2app.component.table.PageableSortableTableModel;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Insets;
import dsc.echo2app.component.DscGroupRadioButton;
import dsc.util.function.UUID;
import echopointng.table.SortableTableColumn;
import fv.components.SelectItem;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.Vni2Uni;
import fv.util.library;

import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.table.DefaultTableColumnModel;
import nextapp.echo2.app.table.TableColumnModel;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.Border;

public class lenchuc_lenluong extends DefaultProgram {

	private ResourceBundle resourceBundle;
	private DscField dsc_sothe;
	private Label lbl_info;
	private DscField dsc_LCBnew;
	private SelectField sf_cv_new;
	private DscField dsc_pc_cv;
	private DscDateField dsc_ngayHL;
	private DscField dsc_ghichu;
	private DscPageNavigation Navi_insal;
	private DscPageableSortableTable Table_INSAL;
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	library l = new library();
	DSPB02 u;
	OBJ_UTILITY obj = new OBJ_UTILITY();
	String key = "";

	/**
	 * Creates a new <code>lenchuc_lenluong</code>.
	 */
	public lenchuc_lenluong() {
		super();

		// Add design-time configured components.
		initComponents();
		// Navi_LCB.setBackground(new Color(0xbbddaa));
		// Navi_PCCV.setBackground(new Color(0xbbddaa));
		// Navi_insal.setBackground(new Color(0xbbddaa));

		IGenericDAO<DSPB02, String> udao = Application.getApp().getDao(
				DSPB02.class);
		u = udao.findById(Application.getApp().getLoginInfo().getUserID());

		dsc_ngayHL.setDateFormat(sdf);
		dsc_ngayHL.setSelectedDate(Calendar.getInstance(Locale.ENGLISH));
		dsc_ngayHL.getDateChooser().setMonthNameLength(30);

	}

	protected int doInit() {
		int ret = super.doInit();
		super.windowPane.setHeight(new Extent(Application.getApp()
				.getScreenHeight()));
		super.windowPane.setWidth(new Extent(Application.getApp()
				.getScreenWidth()));

		table();

		ListBinder.bindSelectField(sf_cv_new, l.positionEditor(), true);
		dsc_sothe.requestFocus();
		dsc_sothe.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// load_data();
				check_data();
				key = "";
				dsc_ghichu.setText("");
				dsc_LCBnew.setText("");
				dsc_pc_cv.setText("");
				ListBinder.bindSelectField(sf_cv_new, l.positionEditor(), true);
				ListBinder.bindSelectField(sf_cap,
						l.getLevel(ListBinder.get(sf_cv_new)), true);
				
			}
		});
		btn_luu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean ok = check_data();
				if (ok) {
					
					save_data();
				}
			}
		});

		btn_print.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (rad_all.isSelected() == false
						&& rad_mau.isSelected() == false) {
					l.ShowMessageError("Vui lòng chọn kiểu in.");
					return;
				}
				boolean ok = check_data();
				if (ok) {
					doPrint();
				}
			}
		});

		Table_INSAL.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cell_ins_click();
			}
		});

		btn_xoa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean ok = check_data();
				if (ok) {
					if (key.equals("")) {
						l.ShowMessageError("Vui lòng chọn dòng cần xóa ở bảng cuối cùng");
						return;
					}
					delete_data();

				}
			}
		});

		btn_reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				reset();
			}
		});
		return ret;

	}

	private SelectField sf_cap;
	private SplitPane splitPane3;
	private DscPageNavigation Navi_LCB;
	private DscPageableSortableTable Table_LCB;
	private DscPageNavigation Navi_PCCV;
	private DscPageableSortableTable Table_PCCV;
	private Button btn_luu;
	private Button btn_xoa;
	private Button btn_reset;
	private Button btn_print;
	private RadioButton rad_all;
	private RadioButton rad_mau;
	private Label lbl_dept;

	// table luong cb
	private TableColumnModel loadColumnModel_lcb() {
		TableColumnModel columnModel = new DefaultTableColumnModel();
		for (int i = 0; i < 4; i++) {
			SortableTableColumn column = new SortableTableColumn(i);
			column.setHeaderRenderer(Table_LCB.getDefaultHeaderRenderer());
			column.setComparator(l.INT_COMPARATOR);
			column.setModelIndex(i);
			column.setHeaderValue(getColumnHeader_LCB()[i]);
			columnModel.addColumn(column);
		}
		return columnModel;
	}

	private String[] getColumnHeader_LCB() {
		return new String[] { "SỐ THẺ", "LƯƠNG CB", "NGÀY", "GHI CHÚ" };
	}

	// table luong PCCV
	private TableColumnModel loadColumnModel_PC() {
		TableColumnModel columnModel = new DefaultTableColumnModel();
		for (int i = 0; i < 5; i++) {
			SortableTableColumn column = new SortableTableColumn(i);
			column.setHeaderRenderer(Table_PCCV.getDefaultHeaderRenderer());
			column.setComparator(l.INT_COMPARATOR);
			column.setModelIndex(i);
			column.setHeaderValue(getColumnHeader_PC()[i]);
			columnModel.addColumn(column);
		}
		return columnModel;
	}

	private String[] getColumnHeader_PC() {
		return new String[] { "SỐ THẺ", "CHỨC VỤ", "PC CHỨC VỤ", "NGÀY","CẤP BẬC" };
	}

	// table luong INSAL
	private TableColumnModel loadColumnModel_INSAL() {
		TableColumnModel columnModel = new DefaultTableColumnModel();
		for (int i = 0; i < 8; i++) {
			SortableTableColumn column = new SortableTableColumn(i);
			column.setHeaderRenderer(Table_INSAL.getDefaultHeaderRenderer());
			column.setComparator(l.INT_COMPARATOR);
			column.setModelIndex(i);
			column.setHeaderValue(getColumnHeader_INSAL()[i]);
			columnModel.addColumn(column);
		}
		return columnModel;
	}

	private String[] getColumnHeader_INSAL() {
		return new String[] { "SỐ THẺ", "NGÀY", "LƯƠNG CŨ", "LƯƠNG MỚI",
				"CV CŨ", "CV MỚI", "PC CŨ", "PC MỚI" };
	}

	public void reset() {

		dsc_sothe.setText("");
		dsc_LCBnew.setText("");
		dsc_pc_cv.setText("");
		dsc_ghichu.setText("");
		sf_cv_new.setSelectedIndex(-1);
		ListBinder.bindSelectField(sf_cap,
				l.getLevel(sf_cv_new.getSelectedItem()), true);

		dsc_ngayHL.setSelectedDate(Calendar.getInstance());
		lbl_info.setText("");
		lbl_dept.setText("");
		load_data_table();
		Navi_insal.reset();
		Navi_LCB.reset();
		Navi_PCCV.reset();
		key = "";
	}

	public boolean check_data() {
		String empsn = dsc_sothe.getText();
		boolean check = true;
		if (check) {
			if (empsn.equals("")) {
				l.ShowMessageError("Số thẻ bắt buộc phải nhập");
				return false;
			}
			if (!empsn.matches("[0-9]{8}")) {
				l.ShowMessageError("Số thẻ không hợp lệ.");
				return false;
			}
			boolean exits = l.check_exits("n_EMPLOYEE", "EMPSN", empsn);
			if (!exits) {
				l.ShowMessageError("Không tồn tại số thẻ này trong hệ thống...");
				return false;
			}
			boolean nv = l.check_exits2("N_EMPLOYEE", "EMPSN", empsn, "DEPSN",
					"00000");
			if (nv) {
				l.ShowMessageError("CNV đã nghỉ việc..");
				return false;
			}
			String xuong = "";
			String mdv = l.getfiled("N_EMPLOYEE", "EMPSN", empsn, "DEPSN");
			InsuranceDAO conn = new InsuranceDAO();
			xuong = conn.GetField("name_fact", "n_department", "id_dept", "",
					"", mdv, "", "");
			if (!xuong.equals("KDAO")) {
				dsc_pc_cv.setEnabled(false);
			} else {
				dsc_pc_cv.setEnabled(true);
			}

			boolean ql = l.check_QL(empsn, "", "", "", "", u.getPB_USERNO());
			if (ql == false) {
				return false;
			} else {
				lbl_info.setText(obj.findNameEmployee(empsn));
				lbl_dept.setText(l.findDept(empsn));
				load_data_table();

			}
		}
		return check;
	}

	public void update_cv(String empsn, String cv) {
		String sql = "update n_employee set position='" + cv
				+ "' where empsn='" + empsn + "'";
		l.exe_sql(sql);
	}

	public void doPrint() {
		String sql = l.getSQLPLHD(dsc_sothe.getText(), "", "", "",
				dsc_ngayHL.getText(), "00002", false);
		if (!l.exe_sql_query(sql)) {
			l.ShowMessageInfo("Không có dữ liệu. Chỉ in PLHD lên chức lên lương.");
			return;
		}
		JasperDesign jd = null;
		Connection con = null;
		try {
			if (rad_all.isSelected()) {
				jd = JRXmlLoader.load(ReportFileManager
						.getReportFormatFolder("fvhr/PLHD.jrxml"));
			} else {
				jd = JRXmlLoader.load(ReportFileManager
						.getReportFormatFolder("fvhr/PLHD_theomau.jrxml"));

			}
			JRDesignQuery query = new JRDesignQuery();
			query.setText(sql);
			jd.setQuery(query);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			con = Application.getApp().getConnection();
			JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
			// JasperViewer.viewReport(jp, false);
			File f = new File(System.getProperty("java.io.tmpdir"),
					UUID.generate());
			JasperExportManager.exportReportToPdfFile(jp, f.getPath());

			File saveFile = new File(f.getParentFile(),
					URLEncoder.encode(
							getLoginInfo().getUserID() + ";application/pdf;"
									+ "PLHD_"
									+ Calendar.getInstance().getTimeInMillis()
									+ ".pdf", "UTF-8"));
			ReportFileManager.saveTempReportFile(f, saveFile);
			Application.getApp().enqueueCommand(
					new BrowserRedirectCommand(getViewerUrl()
							+ saveFile.getName()));
			System.out.println("----ok");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	public String getViewerUrl() {
		HttpServletRequest request = WebRenderServlet.getActiveConnection()
				.getRequest();
		String viewerUrl = request.getRequestURL() + "?"
				+ WebRenderServlet.SERVICE_ID_PARAMETER + "="
				+ ReportService.INSTANCE.getId() + "&"
				+ ReportService.PARAM_TYPE + "=" + ReportService.TYPE_TEMP
				+ "&" + ReportService.PARAM_KEY + "=";
		return viewerUrl;
	}

	public void delete_data() {

		if (!l.CheckKhoaDataMonth(dsc_sothe.getText(), dsc_ngayHL.getText()
				.substring(3, 5), dsc_ngayHL.getText().substring(6, 10))) {
			l.ShowMessageError("Đã khoá dl của tháng này.");
			return;
		}
		if (!l.check_QL(dsc_sothe.getText(), "", "", "", "", u.getPB_USERNO())) {
			return;
		}
		String key = l.getfileds("n_basic_salary", "empsn",
				dsc_sothe.getText(), "to_char(dates,'dd/mm/yyyy')",
				dsc_ngayHL.getText(), "keys");
		if (!key.equals("1")) {
			l.ShowMessageError("DL đã khoá không thể xoá.");
			return;
		}
		//MessageDialog mess = new MessageDialog(MessageDialog.TYPE_WARNING
				//+ MessageDialog.CONTROLS_YES_NO, "Bạn có chắc sẽ xoá dl");
		special_dialog mess=l.dialog("Bạn có chắc sẽ xoá dl"); 
		mess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MessageDialog.COMMAND_OK.equals(e.getActionCommand())) {
					try {
						String cv = ListBinder.get(sf_cv_new).toString();
						String mshd, ms_pl;
						l.xoa_bsaly(dsc_sothe.getText(), dsc_ngayHL.getText());
						// luu thong tin
						String note = "Xoa dl ngay " + dsc_ngayHL.getText()
								+ " LCB " + dsc_LCBnew.getText();
						l.Insert_N_Action_Daily(u.getPB_USERNO(),
								"N_BASIC_SALARY", "DELETE",
								dsc_sothe.getText(), note);
						xoa_salposs(dsc_sothe.getText(), dsc_ngayHL.getText(),
								"1", "0");

						if (l.check_exits("n_labour", "empsn",
								dsc_sothe.getText())) {
							mshd = l.getfileds("n_labour", "empsn",
									dsc_sothe.getText(), "clock", "1",
									"id_labour");
							ms_pl = l.getfileds("n_sub_labour", "id_labour",
									mshd, "clock", "1", "id_contract");
							if (ms_pl.equals("")) {
								ms_pl = mshd;
							}
							l.delete_PhulucHDLD(ms_pl, mshd);
							// luuthong tin
							note = "Xoa PLHD dua vao viec xoa dot dieu chinh luong ngay"
									+ dsc_ngayHL.getText()
									+ " Luong "
									+ dsc_LCBnew.getText();
							l.Insert_N_Action_Daily(u.getPB_USERNO(),
									"N_SUB_LABOUR", "DELETE",
									dsc_sothe.getText(), note);
						}
						l.xoa_bposs(dsc_sothe.getText(), dsc_ngayHL.getText());
						// luu thong tin
						note = "Xoa dl ngay " + dsc_ngayHL.getText() + "CV "
								+ cv + " PCCV " + dsc_pc_cv.getText();
						l.Insert_N_Action_Daily(u.getPB_USERNO(),
								"N_BONUS_POSS", "DELETE", dsc_sothe.getText(),
								note);
						xoa_salposs(dsc_sothe.getText(), dsc_ngayHL.getText(),
								"0", "1");
					} catch (Exception ex) {
						// TODO: handle exception
						ex.printStackTrace();
						l.ShowMessageError("Dữ liệu bị lỗi..");
					}
					l.ShowMessageOK("Xóa dữ liệu thành công.");
					load_data_table();
				}

			}
		});
	}

	public void xoa_salposs(String sothe, String ngay, String lcb, String pccv) {
		String luongcb_upd, cv_upd, pccv_upd;
		String cv = ListBinder.get(sf_cv_new).toString();
		if (l.check_exits2("n_inc_salposs", "empsn", dsc_sothe.getText(),
				"to_char(dates,'dd/mm/yyyy')", dsc_ngayHL.getText())) {
			luongcb_upd = l.getfileds("n_basic_salary", "empsn",
					dsc_sothe.getText(), "keys", "1", "bsalary");
			cv_upd = l.getfileds("n_bonus_poss", "empsn", dsc_sothe.getText(),
					"keys", "1", "poss");
			pccv_upd = l.getfileds("n_bonus_poss", "empsn",
					dsc_sothe.getText(), "keys", "1", "money");
			if (lcb.equals("1")) {
				sua_inc_salposs(sothe, ngay, luongcb_upd, "", "");				
			}
			if (pccv.equals("1")) {
				sua_inc_salposs(sothe, ngay, "", cv_upd, pccv_upd);			
			}
			l.del_inc_salposs(sothe, ngay);
			// luu thong tin
			String note = "Xoa dl ngay " + ngay + ",CV " + cv + ",PCCV "
					+ dsc_pc_cv.getText();
			l.Insert_N_Action_Daily(u.getPB_USERNO(), "N_INC_SALPOSS",
					"DELETE", sothe, note);
		}
	}

	public void save_data() {
		String empsn = dsc_sothe.getText();
		if (dsc_LCBnew.getText().equals("")) {
			l.ShowMessageError("Lương cơ bản bắt buộc phải nhập..");
			return;
		}
		if (!empsn.matches("[0-9].*")) {
			l.ShowMessageError("Lương CB không hợp lệ.");
			return ;
		}
		if (sf_cv_new.getSelectedIndex() == -1) {
			l.ShowMessageError("Chức vụ mới không được rổng..");
			return;
		}
		if (sf_cap.getSelectedIndex() == -1) {
			l.ShowMessageError("Cấp bậc chức vụ không được rổng..");
			return;
		}
		String cv = ListBinder.get(sf_cv_new).toString(); // cv moi

		String temp_sal_old = l.getfileds("n_basic_salary", "empsn", empsn,
				"keys", "1", "bsalary");
		// lương hiện tại

		String temp_pos_old = l.getfileds("n_bonus_poss", "empsn", empsn,
				"keys", "1", "money");
		// pccv hiện tại

		int sal_old = 0;
		int pos_old = 0;

		if (!temp_sal_old.equals("")) {
			sal_old = Integer.parseInt(temp_sal_old);
			// if lương cũ ko null sal_old=lương cũ
		}
		if (!temp_pos_old.equals("")) {
			pos_old = Integer.parseInt(temp_pos_old);
			// if vị trí cũ ko null pos_old=vị trí cũ
		}
		if (l.kyluat(empsn, dsc_ngayHL.getText())
				&& (sal_old < Integer.parseInt(dsc_LCBnew.getText()) || pos_old < Integer
						.parseInt(dsc_pc_cv.getText()))) {
			// if đang trong thời gian kỉ luật thì lương mới không đc lớn hơn
			// lương cũ
			l.ShowMessageError("CNV đang bị kỷ luật ngừng nâng lương. Lương CB và PCCV không thể lớn hơn LCB và PCCV cũ..");
			return;
		}
		
		if(!l.CheckKhoaDataMonth(empsn,dsc_ngayHL.getText().substring(3, 5), dsc_ngayHL
				.getText().substring(6, 10)))
		 {
			// kiểm tra cột tabname trong attlock_nam_thang
			l.ShowMessageError("DL đã khóa không thể chỉnh sửa thông tin.");
			return;
		}

		// sua luong khi nhap hs sai muc luong dk ngay sua= ngay nx va chua ky
		// HD

		String ngay_nx = sdf.format(l.getdate("n_employee", "empsn", empsn,
				"date_hired"));
		if ((l.check_exits("n_labour", "empsn", empsn) == false)
				&& dsc_ngayHL.getText().equals(ngay_nx)) // điều kiện để sửa
															// lương ,
		{

			// cap nhat luong
			if (!update_salary()) {
				return;
			}
			// cap nhat pccv
			if (!update_bposs()) {
				return;
			}

		} else {
			Calendar nc = dsc_ngayHL.getSelectedDate();
			if (nc.get(Calendar.DAY_OF_MONTH) != 1) {
				l.ShowMessageError("Ngày điều động phải là ngày 1 của tháng..");
				return;
			}
			// cap nhat luong
			if (!update_salary()) {
				return;
			}
			// cap nhat pccv
			if (!update_bposs()) {
				return;
			}

			String mshd = "";
			String msplhd_new = "";
			if (sal_old != Integer.parseInt(dsc_LCBnew.getText())) {
				mshd = l.getfileds("n_labour", "empsn", empsn, "clock", "1",
						"id_labour");
				int max = getmax();
				if (max == 0) {
					msplhd_new = mshd;
				} else {
					if (max < 10) {
						msplhd_new = mshd.substring(0, 8) + "0" + (max + 1);
					} else {
						msplhd_new = mshd.substring(0, 7) + max + 1;
					}
				}
					l.savePLHD(msplhd_new, dsc_ngayHL.getText().toString(),
							dsc_LCBnew.getText().toString(), cv,
							dsc_LCBnew.getText(), mshd, "00002");				
			}
		}
		l.ShowMessageOK("Lưu dữ liệu thành công.");
		load_data_table();
	}

	public boolean update_salary() {
		// abc
		boolean a = false;
		String empsn = dsc_sothe.getText();
		String cv = ListBinder.get(sf_cv_new).toString();

		if (!dsc_LCBnew.getText().equals("")) {
			// nếu lương cb mới không rỗng --> nếu tồn tại trong n_basic_salary
			// empsn=$empsn and dates=$dsc_ngayHL and keys==1
			if (l.check_exits1("n_basic_salary", "empsn", empsn,
					"to_char(dates,'dd/mm/yyyy')", dsc_ngayHL.getText(),
					"keys", "1")) {
				// ??????
				String key = l.getfileds("n_basic_salary", "empsn", empsn,
						"to_char(dates,'dd/mm/yyyy')", dsc_ngayHL.getText(),
						"KEYS");
				if (key.equals("1")) {
					// update inc_salposs
					sua_inc_salposs(empsn, dsc_ngayHL.getText(),
							dsc_LCBnew.getText(), "", "");
					String note = "Ngay " + dsc_ngayHL.getText()
							+ ",Luong CB moi " + dsc_LCBnew.getText() + ",CV "
							+ cv + ",PCCV " + dsc_pc_cv.getText();
					l.Insert_N_Action_Daily(u.getPB_USERNO(), "N_INC_SALPOSS",
							"UPDATE", dsc_sothe.getText(), note);
					// update salary
					String date_old = sdf.format(l.getdates("n_basic_salary",
							"empsn", empsn, "keys", "1", "dates"));

					l.sua_bsaly(empsn, date_old, dsc_ngayHL.getText(),
							dsc_LCBnew.getText(), dsc_ghichu.getText());
					note = "Ngay " + dsc_ngayHL.getText() + " LCB moi "
							+ dsc_LCBnew.getText();
					l.Insert_N_Action_Daily(u.getPB_USERNO(), "N_BASIC_SALARY",
							"UPDATE", dsc_sothe.getText(), note);
					a = true;
				} else {
					l.ShowMessageError("DL LCB đã khóa không thể chỉnh sửa.");
					a = false;
				}
			} else {
				insert_inc_sqlposs(empsn, dsc_ngayHL.getText(),
						dsc_LCBnew.getText(), "", "");
				// String
				// note=" Ngay "+dsc_ngayHL.getText()+" Luong CB moi "+dsc_LCBnew.getText();
				// l.Insert_N_Action_Daily(u.getPB_USERNO(), "N_INC_SALPOSS",
				// "INSERT", dsc_sothe.getText(), note);
				// insert salary
				l.insert_bsaly(empsn, dsc_ngayHL.getText(),
						dsc_LCBnew.getText(),Vni2Uni.convertToVNI(dsc_ghichu.getText()));
				String note = " Ngay " + dsc_ngayHL.getText()
						+ " Luong CB moi " + dsc_LCBnew.getText();
				l.Insert_N_Action_Daily(u.getPB_USERNO(), "N_BASIC_SALARY",
						"INSERT", dsc_sothe.getText(), note);
				a = true;
			}
		}
		return a;
	}

	public boolean update_bposs() {
		boolean a = false;
		String empsn = dsc_sothe.getText();
		String cv = ListBinder.get(sf_cv_new).toString();
		if ((sf_cv_new.getSelectedIndex() != -1)
				|| (!dsc_pc_cv.getText().equals(""))) {
			if (l.check_exits2("n_bonus_poss", "empsn", empsn,
					"to_char(dates,'dd/mm/yyyy')", dsc_ngayHL.getText())) {
				String key = l.getfileds("n_bonus_poss", "empsn", empsn,
						"to_char(dates,'dd/mm/yyyy')", dsc_ngayHL.getText(),
						"keys");
				if (key.equals("1")) {
					// sua inc_sal
					sua_inc_salposs(empsn, dsc_ngayHL.getText(), "", cv,
							dsc_pc_cv.getText());
					// sua bposs
					String date_old = sdf.format(l.getdates("n_bonus_poss",
							"empsn", empsn, "keys", "1", "dates"));
					l.sua_bposs(empsn, date_old, dsc_ngayHL.getText(),
							dsc_pc_cv.getText(), cv);
					String note = "Ngay " + dsc_ngayHL.getText() + " PCCV "
							+ dsc_pc_cv.getText();
					update_cv(empsn, cv);
					l.Insert_N_Action_Daily(u.getPB_USERNO(), "N_BONUS_POSS",
							"UPDATE", empsn, note);
					a = true;
				} else {
					l.ShowMessageInfo("DL đã khoá.");
					a = false;
				}
			} else {
				// nhap inc_sal
				insert_inc_sqlposs(empsn, dsc_ngayHL.getText(), "", cv,
						dsc_pc_cv.getText());
				String note = "Ngay " + dsc_ngayHL.getText() + ",LCB "
						+ dsc_LCBnew.getText() + ", CV moi " + cv + ", PCCV "
						+ dsc_pc_cv.getText();
				l.Insert_N_Action_Daily(u.getPB_USERNO(), "N_INC_SALPOSS",
						"INSERT", empsn, note);
				// nhap bposs
				l.nhap_bposs(empsn, dsc_ngayHL.getText(), dsc_pc_cv.getText(),
						cv,sf_cap.getSelectedItem().toString());
				update_cv(empsn, cv);
				l.Insert_N_Action_Daily(u.getPB_USERNO(), "N_BONUS_POSS",
						"INSERT", empsn, note);
				a = true;
			}
		}
		return a;
	}

	public void insert_inc_sqlposs(String empsn, String date,
			String bsalary_new, String poss_new, String bposs_new) {
		String cv = ListBinder.get(sf_cv_new).toString();
		String luongcu = l.getfileds("n_basic_salary", "empsn", empsn, "keys",
				"1", "bsalary");
		String cvcu = l.getfileds("n_bonus_poss", "empsn", empsn, "keys", "1",
				"poss");
		String pccvcu = l.getfileds("n_bonus_poss", "empsn", empsn, "keys",
				"1", "money");
		if (l.check_exits2("n_inc_salposs", "empsn", empsn,
				"to_char(dates,'dd/mm/yyyy')", date)) {
			sua_inc_salposs(empsn, date, bsalary_new, poss_new, bposs_new);
		} else {
			if (bsalary_new.equals("")) {
				bposs_new = luongcu;
			}
			if (poss_new.equals("")) {
				poss_new = cvcu;
			}
			if (bposs_new.equals("")) {
				bposs_new = pccvcu;
			}
			// ins_inc
			l.ins_inc_salposs(empsn, date, luongcu, bsalary_new, cvcu,
					poss_new, pccvcu, bposs_new);

		}
	}

	public void sua_inc_salposs(String sothe, String ngaymoi, String lcb_moi,
			String cv_moi, String pccv_moi) {
		con = Application.getApp().getConnection();
		String sql = "Select * from n_inc_salposs where empsn='" + sothe
				+ "' and to_char(dates,'dd/mm/yyyy')='" + ngaymoi + "'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				String ngaycu = sdf.format(rs.getDate("DATES"));
				String luongcu = rs.getString("SAL_OLD");
				String luongmoi = rs.getString("SAL_NEW");
				String cvcu = rs.getString("POSS_OLD");
				String cvmoi = rs.getString("POSS_NEW");
				String pccvucu = rs.getString("BPOSS_OLD");
				String pccvumoi = rs.getString("BPOSS_NEW");

				if (lcb_moi.equals("")) {
					lcb_moi = luongmoi;
				}
				if (cv_moi.equals("")) {
					cv_moi = cvmoi;
				}
				if (pccv_moi.equals("")) {
					pccv_moi = pccvumoi;
				}
				l.upd_inc_salposs(sothe, ngaycu, ngaymoi, luongcu, lcb_moi,
						cvcu, cv_moi, pccvucu, pccv_moi);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public int getmax() {
		int max = 0;
		con = Application.getApp().getConnection();
		String sql = "select nvl(max(to_number(substr(t.id_contract,9,2))),0) as id "
				+ " from n_Sub_Labour t,n_labour l where t.id_labour=l.id_labour  "
				+ " and l.empsn='" + dsc_sothe.getText() + "'";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				max = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			l.close_con(con, st);

		}
		return max;
	}

	public String getmax2() {
		String max = "";
		con = Application.getApp().getConnection();
		String sql = "select max(l.id_contract) as id from n_sub_labour l "
				+ " where substr(id_contract,1,8)='" + dsc_sothe.getText()
				+ "' and clock='1'";

		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				max = rs.getString(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			l.close_con(con, st);
		}
		return max;
	}

	public void load_data_table() {
		con = Application.getApp().getConnection();
		String sql_lcb = "select * from n_basic_salary where empsn='"
				+ dsc_sothe.getText() + "' order by  dates desc ";
		String sql_pccv = "select * from n_bonus_poss where empsn='"
				+ dsc_sothe.getText() + "' order by  dates desc";
		String sql_insal = "select * from n_inc_salposs where empsn='"
				+ dsc_sothe.getText() + "' order by  dates desc";
		int i = 0, j = 0, k = 0;

		PageableSortableTableModel model_lcb = (PageableSortableTableModel) Table_LCB
				.getModel();
		PageableSortableTableModel model_pccv = (PageableSortableTableModel) Table_PCCV
				.getModel();
		PageableSortableTableModel model_insal = (PageableSortableTableModel) Table_INSAL
				.getModel();
		model_insal.clear();
		model_lcb.clear();
		model_pccv.clear();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql_lcb);
			while (rs.next()) {
				model_lcb.setValueAt(rs.getString("EMPSN"), 0, i);
				model_lcb.setValueAt(rs.getString("BSALARY"), 1, i);
				model_lcb.setValueAt(sdf.format(rs.getDate("DATES")), 2, i);
				model_lcb.setValueAt(rs.getString("NOTE"), 3, i);
				i++;
				Navi_LCB.reset();
			}
			rs = st.executeQuery(sql_pccv);
			i = 0;
			while (rs.next()) {
				model_pccv.setValueAt(rs.getString("EMPSN"), 0, i);
				model_pccv.setValueAt(rs.getString("POSS"), 1, i);
				model_pccv.setValueAt(rs.getString("MONEY"), 2, i);
				model_pccv.setValueAt(sdf.format(rs.getDate("DATES")), 3, i);
				model_pccv.setValueAt(rs.getString("LEVEL_POSS"), 4, i);
				i++;
				Navi_PCCV.reset();
			}
			rs = st.executeQuery(sql_insal);
			i = 0;
			while (rs.next()) {
				model_insal.setValueAt(rs.getString("EMPSN"), 0, i);
				model_insal.setValueAt(sdf.format(rs.getDate("DATES")), 1, i);
				model_insal.setValueAt(rs.getString("SAL_OLD"), 2, i);
				model_insal.setValueAt(rs.getString("SAL_NEW"), 3, i);
				model_insal.setValueAt(rs.getString("POSS_OLD"), 4, i);
				model_insal.setValueAt(rs.getString("POSS_NEW"), 5, i);
				model_insal.setValueAt(rs.getString("BPOSS_OLD"), 6, i);
				model_insal.setValueAt(rs.getString("BPOSS_NEW"), 7, i);
				i++;
				Navi_insal.reset();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			l.close_con(con, st);
		}

	}

	public void cell_ins_click() {
		int a = Table_INSAL.getSelectionModel().getMaxSelectedIndex();
		String empsn = Table_INSAL.getModel().getValueAt(0, a).toString();
		String lcb = Table_INSAL.getModel().getValueAt(3, a).toString();
		String ngay = Table_INSAL.getModel().getValueAt(1, a).toString();
		String cv = Table_INSAL.getModel().getValueAt(5, a).toString();
		String pccv = Table_INSAL.getModel().getValueAt(7, a).toString();
		key = "" + a;

		dsc_sothe.setText(empsn);
		dsc_LCBnew.setText(lcb);
		dsc_pc_cv.setText(pccv);
		ListBinder.refreshIndex(sf_cv_new, cv);
		try {
			java.util.Date uday = sdf.parse(ngay);
			java.sql.Date sday = new java.sql.Date(uday.getTime());
			Calendar cal = Calendar.getInstance();
			cal.setTime(sday);
			dsc_ngayHL.setSelectedDate(cal);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ListBinder.bindSelectField(sf_cap,
				l.getLevel(cv), true);
		String lposs="";
		Object ob=l.getfileds("n_bonus_poss", "empsn", empsn, "to_char(dates,'dd/mm/yyyy')", ngay, "level_poss");
		if(ob!=null)
		{
			ListBinder.refreshIndex(sf_cap, ob);
			sf_cap.setEnabled(true);
		}
		//sf_cap
				
	}

	public void table() {
		// lcb
		TableColumnModel columnModel_lcb = loadColumnModel_lcb();
		Table_LCB.setColumnModel(columnModel_lcb);
		PageableSortableTableModel model_lcb = new PageableSortableTableModel(
				columnModel_lcb);
		model_lcb.setSelectionModel(Table_LCB.getSelectionModel());
		model_lcb.setRowsPerPage(5);
		Table_LCB.setModel(model_lcb);
		Table_LCB.setSelectionEnabled(true);
		Navi_LCB.setTable(Table_LCB);

		// pccv
		TableColumnModel columnModel_pccv = loadColumnModel_PC();
		Table_PCCV.setColumnModel(columnModel_pccv);
		PageableSortableTableModel model_pccv = new PageableSortableTableModel(
				columnModel_pccv);
		model_pccv.setSelectionModel(Table_PCCV.getSelectionModel());
		model_pccv.setRowsPerPage(5);
		Table_PCCV.setModel(model_pccv);
		Table_PCCV.setSelectionEnabled(true);
		Navi_PCCV.setTable(Table_PCCV);
		// insal
		TableColumnModel columnModel_insal = loadColumnModel_INSAL();
		Table_INSAL.setColumnModel(columnModel_insal);
		PageableSortableTableModel model_insal = new PageableSortableTableModel(
				columnModel_insal);
		model_insal.setSelectionModel(Table_INSAL.getSelectionModel());
		model_insal.setRowsPerPage(5);
		Table_INSAL.setModel(model_insal);
		Table_INSAL.setSelectionEnabled(true);
		Navi_insal.setTable(Table_INSAL);
	}

	private void cv_ac(ActionEvent e) {
		// TODO Implement.
		ListBinder.bindSelectField(sf_cap,
				l.getLevel(ListBinder.get(sf_cv_new)), true);
		
		/*String bposs=l.getfiled("n_position", "id_position",Vni2Uni.convertToVNI(ListBinder.get(sf_cv_new).toString()) , "basic_poss");
		if(bposs!=null){
			if(!dsc_LCBnew.getText().equals("")){
				String lcb=dsc_LCBnew.getText().toString();
				if(Integer.parseInt(lcb)<Integer.parseInt(bposs)){
					dsc_LCBnew.setText(bposs);
				}
			}
			else
			{			
				dsc_LCBnew.setText(bposs);
			}
		}else
		{
			dsc_LCBnew.setText("");
		}
		*/
		dsc_pc_cv.setText("");

	}

	private void cap_ac(ActionEvent e) {
		// TODO Implement.
		IGenericDAO<N_POSS_SALARY, String> dao = Application.getApp().getDao(
				N_POSS_SALARY.class);
		List<N_POSS_SALARY> list = dao.find(1,
				"from N_POSS_SALARY where ID_POSS||'_'||LEVEL_POSS=? and DATE_HL=(select max(DATE_HL) from N_POSS_SALARY)",
				Vni2Uni.convertToVNI(sf_cap.getSelectedItem().toString()));

		if (list.size() > 0) {
			for (N_POSS_SALARY obj : list) {
				dsc_pc_cv.setText(obj.getMONEY().toString());
			}
		}
	}

	/**
	 * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(270, Extent.PX));
		splitPane1.setSeparatorColor(new Color(0xd9ecec));
		splitPane1.setSeparatorWidth(new Extent(2, Extent.PX));
		splitPane1.setResizable(true);
		add(splitPane1);
		ContentPane contentPane1 = new ContentPane();
		splitPane1.add(contentPane1);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(2, Extent.PX)));
		grid1.setSize(2);
		contentPane1.add(grid1);
		Label label1 = new Label();
		label1.setText("Số thẻ");
		grid1.add(label1);
		dsc_sothe = new DscField();
		dsc_sothe.setWidth(new Extent(153, Extent.PX));
		dsc_sothe.setMaximumLength(8);
		grid1.add(dsc_sothe);
		Label label2 = new Label();
		grid1.add(label2);
		lbl_info = new Label();
		lbl_info.setText("ho ten");
		grid1.add(lbl_info);
		Label label15 = new Label();
		grid1.add(label15);
		lbl_dept = new Label();
		lbl_dept.setText("dept");
		grid1.add(lbl_dept);
		Label label3 = new Label();
		label3.setText("Lương CB mới");
		grid1.add(label3);
		dsc_LCBnew = new DscField();
		dsc_LCBnew.setWidth(new Extent(153, Extent.PX));
		grid1.add(dsc_LCBnew);
		Label label4 = new Label();
		label4.setText("Chức vụ mới");
		grid1.add(label4);
		sf_cv_new = new SelectField();
		sf_cv_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cv_ac(e);
			}
		});
		grid1.add(sf_cv_new);
		Label label11 = new Label();
		label11.setText("Cấp");
		grid1.add(label11);
		sf_cap = new SelectField();
		sf_cap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cap_ac(e);
			}
		});
		grid1.add(sf_cap);
		Label label5 = new Label();
		label5.setText("PC chức vụ");
		grid1.add(label5);
		dsc_pc_cv = new DscField();
		dsc_pc_cv.setEnabled(false);
		dsc_pc_cv.setWidth(new Extent(153, Extent.PX));
		dsc_pc_cv.setDisabledBackground(new Color(0xc0c0c0));
		grid1.add(dsc_pc_cv);
		Label label6 = new Label();
		label6.setText("Ngày HL");
		grid1.add(label6);
		dsc_ngayHL = new DscDateField();
		grid1.add(dsc_ngayHL);
		Label label7 = new Label();
		label7.setText("Ghi chú");
		grid1.add(label7);
		dsc_ghichu = new DscField();
		dsc_ghichu.setWidth(new Extent(153, Extent.PX));
		grid1.add(dsc_ghichu);
		Label label8 = new Label();
		grid1.add(label8);
		Label label9 = new Label();
		grid1.add(label9);
		Label label10 = new Label();
		grid1.add(label10);
		btn_luu = new Button();
		btn_luu.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btn_luu.setText("Lưu");
		btn_luu.setHeight(new Extent(20, Extent.PX));
		btn_luu.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_INSET));
		btn_luu.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_luu.setWidth(new Extent(153, Extent.PX));
		btn_luu.setBackground(new Color(0x0080ff));
		btn_luu.setPressedEnabled(true);
		btn_luu.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x8080c0), Border.STYLE_GROOVE));
		btn_luu.setRolloverEnabled(true);
		btn_luu.setForeground(Color.WHITE);
		grid1.add(btn_luu);
		Label label16 = new Label();
		grid1.add(label16);
		btn_xoa = new Button();
		btn_xoa.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btn_xoa.setText("Xóa");
		btn_xoa.setHeight(new Extent(20, Extent.PX));
		btn_xoa.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_INSET));
		btn_xoa.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_xoa.setWidth(new Extent(153, Extent.PX));
		btn_xoa.setBackground(new Color(0x0080ff));
		btn_xoa.setPressedEnabled(true);
		btn_xoa.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x8080c0), Border.STYLE_GROOVE));
		btn_xoa.setRolloverEnabled(true);
		btn_xoa.setForeground(Color.WHITE);
		grid1.add(btn_xoa);
		Label label17 = new Label();
		grid1.add(label17);
		btn_reset = new Button();
		btn_reset.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		btn_reset.setText("Reset");
		btn_reset.setHeight(new Extent(20, Extent.PX));
		btn_reset.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_INSET));
		btn_reset.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_reset.setWidth(new Extent(153, Extent.PX));
		btn_reset.setBackground(new Color(0x0080ff));
		btn_reset.setPressedEnabled(true);
		btn_reset.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x8080c0), Border.STYLE_GROOVE));
		btn_reset.setRolloverEnabled(true);
		btn_reset.setForeground(Color.WHITE);
		grid1.add(btn_reset);
		Label label18 = new Label();
		grid1.add(label18);
		btn_print = new Button();
		btn_print.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		btn_print.setText("In phụ lục");
		btn_print.setHeight(new Extent(20, Extent.PX));
		btn_print.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_INSET));
		btn_print.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_print.setWidth(new Extent(153, Extent.PX));
		btn_print.setBackground(new Color(0x0080ff));
		btn_print.setPressedEnabled(true);
		btn_print.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x8080c0), Border.STYLE_GROOVE));
		btn_print.setRolloverEnabled(true);
		btn_print.setForeground(Color.WHITE);
		grid1.add(btn_print);
		Label label14 = new Label();
		grid1.add(label14);
		DscGroupRadioButton dscGroupRadioButton1 = new DscGroupRadioButton();
		grid1.add(dscGroupRadioButton1);
		rad_all = new RadioButton();
		rad_all.setText("Đầy đủ");
		rad_all.setHeight(new Extent(20, Extent.PX));
		dscGroupRadioButton1.add(rad_all);
		rad_mau = new RadioButton();
		rad_mau.setText("Theo mẫu");
		rad_mau.setHeight(new Extent(20, Extent.PX));
		dscGroupRadioButton1.add(rad_mau);
		SplitPane splitPane2 = new SplitPane();
		splitPane2.setSeparatorPosition(new Extent(300, Extent.PX));
		splitPane2.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane2.setSeparatorColor(new Color(0xd9ecec));
		splitPane2.setResizable(true);
		splitPane2.setSeparatorHeight(new Extent(0, Extent.PX));
		splitPane1.add(splitPane2);
		splitPane3 = new SplitPane();
		splitPane3.setSeparatorPosition(new Extent(500, Extent.PX));
		splitPane3.setSeparatorColor(Color.WHITE);
		splitPane3.setSeparatorWidth(new Extent(2, Extent.PX));
		splitPane3.setResizable(true);
		splitPane2.add(splitPane3);
		SplitPane splitPane4 = new SplitPane();
		splitPane4.setSeparatorPosition(new Extent(24, Extent.PX));
		splitPane4.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane4.setSeparatorColor(new Color(0xd9ecec));
		splitPane4.setSeparatorWidth(new Extent(2, Extent.PX));
		splitPane4.setResizable(true);
		splitPane4.setSeparatorHeight(new Extent(0, Extent.PX));
		splitPane3.add(splitPane4);
		Label label12 = new Label();
		label12.setText("THÔNG TIN LƯƠNG CĂN BẢN");
		label12.setFont(new Font(null, Font.PLAIN, new Extent(13, Extent.PT)));
		label12.setForeground(Color.WHITE);
		SplitPaneLayoutData label12LayoutData = new SplitPaneLayoutData();
		label12LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		label12LayoutData.setBackground(new Color(0x008080));
		label12.setLayoutData(label12LayoutData);
		splitPane4.add(label12);
		Grid grid2 = new Grid();
		grid2.setWidth(new Extent(100, Extent.PERCENT));
		grid2.setSize(1);
		splitPane4.add(grid2);
		Navi_LCB = new DscPageNavigation();
		GridLayoutData Navi_LCBLayoutData = new GridLayoutData();
		Navi_LCBLayoutData.setBackground(new Color(0xc0c0c0));
		Navi_LCB.setLayoutData(Navi_LCBLayoutData);
		grid2.add(Navi_LCB);
		Table_LCB = new DscPageableSortableTable();
		Table_LCB.setStyleName("Table.DscPageableSortableTable");
		Table_LCB.setAutoCreateColumnsFromModel(false);
		grid2.add(Table_LCB);
		SplitPane splitPane5 = new SplitPane();
		splitPane5.setSeparatorPosition(new Extent(24, Extent.PX));
		splitPane5.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane5.setSeparatorColor(new Color(0xd9ecec));
		splitPane5.setSeparatorWidth(new Extent(2, Extent.PX));
		splitPane5.setResizable(true);
		splitPane5.setSeparatorHeight(new Extent(0, Extent.PX));
		splitPane3.add(splitPane5);
		Label label13 = new Label();
		label13.setText("CHỨC VỤ VÀ PHỤ CẤP CHỨC VỤ");
		label13.setFont(new Font(null, Font.PLAIN, new Extent(13, Extent.PT)));
		label13.setForeground(Color.WHITE);
		SplitPaneLayoutData label13LayoutData = new SplitPaneLayoutData();
		label13LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		label13LayoutData.setBackground(new Color(0x008080));
		label13.setLayoutData(label13LayoutData);
		splitPane5.add(label13);
		Grid grid3 = new Grid();
		grid3.setWidth(new Extent(100, Extent.PERCENT));
		grid3.setSize(1);
		splitPane5.add(grid3);
		Navi_PCCV = new DscPageNavigation();
		GridLayoutData Navi_PCCVLayoutData = new GridLayoutData();
		Navi_PCCVLayoutData.setBackground(new Color(0xc0c0c0));
		Navi_PCCV.setLayoutData(Navi_PCCVLayoutData);
		grid3.add(Navi_PCCV);
		Table_PCCV = new DscPageableSortableTable();
		Table_PCCV.setStyleName("Table.DscPageableSortableTable");
		Table_PCCV.setAutoCreateColumnsFromModel(false);
		grid3.add(Table_PCCV);
		SplitPane splitPane6 = new SplitPane();
		splitPane6.setSeparatorPosition(new Extent(24, Extent.PX));
		splitPane6.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane6.setSeparatorColor(new Color(0xd9ecec));
		splitPane6.setSeparatorWidth(new Extent(2, Extent.PX));
		splitPane6.setResizable(true);
		splitPane6.setSeparatorHeight(new Extent(0, Extent.PX));
		splitPane2.add(splitPane6);
		Navi_insal = new DscPageNavigation();
		SplitPaneLayoutData Navi_insalLayoutData = new SplitPaneLayoutData();
		Navi_insalLayoutData.setBackground(new Color(0xc0c0c0));
		Navi_insal.setLayoutData(Navi_insalLayoutData);
		splitPane6.add(Navi_insal);
		Table_INSAL = new DscPageableSortableTable();
		Table_INSAL.setStyleName("Table.DscPageableSortableTable");
		Table_INSAL.setAutoCreateColumnsFromModel(false);
		splitPane6.add(Table_INSAL);
	}
}
