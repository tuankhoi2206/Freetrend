package ds.program.fvhr.ngan.ui.Change_Depsn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_QUANLY;
import ds.program.fvhr.domain.N_REST;
import ds.program.fvhr.domain.N_USER_LIMIT;
import ds.program.fvhr.domain.pk.N_RESTPk;
import ds.program.fvhr.ngan.ui.DeptUserControl_FactDetail;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.DefaultProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import echopointng.DateChooser;
import echopointng.GroupBox;
import fv.util.ListBinder;
import fv.util.Vni2Uni;

public class ChangeDepsnProgram extends DefaultProgram {

	private ResourceBundle resourceBundle;
	TextField textField1;
	Label label3;
	private Label hoten;
	private Label tendv;
	private Label label1;
	private Label label7;
	private DscDateField dscDateField1;
	SimpleDateFormat sf_ = new SimpleDateFormat(OBJ_UTILITY.DATE_FORMAT_STR);
	private DeptUserControl_FactDetail deptItems; // sua lai ngay 19/11/2012 vi
													// depsnSearchPane lay ma
													// dvi k dung khi chon xuong
													// nhom tendv
	private DeptUserControl_FactDetail deptItems2;
	private Grid grid2;
	private Grid grid3;
	private Label madv_old;
	private Label label8;
	private Button ok;
	private SelectField selectField1;
	private Label label9;
	private Label label11;
	private Label label12;
	private Label maql_old;
	private Label label14;
	OBJ_UTILITY obj_util;
	String user_up = Application.getApp().getLoginInfo().getUserID();
	String ma_user = "";
	String maql_new = "";
	String str_date = null;
	private GroupBox groupBox2;
	String note_dv_old = "";
	String note_dv_new = "";
	String strnote = "";
	private CheckBox c_tt_gt;
	private CheckBox c_gt_sx;
	private CheckBox c_gm;
	private CheckBox c_vp;
	private String status;
	private String status_old;
	private CheckBox cb_dd_dv;
	Label label4;
	Label label2;
	Label label5;
	Label label6;
	Label label13;
	Label label15, status_sx, label17;
	RadioButton f1, f2, f3, f5, f6, khac;

	/**
	 * Creates a new <code>Change_Depsn</code>.
	 */
	public ChangeDepsnProgram() {
		// super();

		initComponents();
		doLayoutChangeDepsn();
		ListBinder.bindSelectField(selectField1, quanlyEditor(), true);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	private void doLayoutChangeDepsn() {// 24/12/2011 Ngan them
		// ####
		f1 = new RadioButton();
		f2 = new RadioButton();
		f3 = new RadioButton();
		f5 = new RadioButton();
		f6 = new RadioButton();
		khac = new RadioButton();
		f1.setSelected(false);
		f2.setSelected(false);
		f3.setSelected(false);
		f5.setSelected(false);
		f6.setSelected(false);
		khac.setSelected(false);
		// ####
		IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(
				DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();

		// ####

		dscDateField1.setDateFormat(OBJ_UTILITY.Get_format_date());
		dscDateField1.setSelectedDate(Calendar.getInstance());
		// khoi them 02/11/2013 ( default date =1 )
		Calendar ca = dscDateField1.getSelectedDate();
		Date date = new Date();
		date.setDate(1);
		ca.setTime(date);
		dscDateField1.getTextField().setText(
				dscDateField1.getDateFormat().format(date));
		dscDateField1.setMouseCursor(DATAMODE_EDIT);
		// end khoi
		dscDateField1.getDateChooser().setLocale(Locale.ENGLISH);

		// ----
		deptItems = new DeptUserControl_FactDetail();
		/*
		 * GridLayoutData childTableLayout = new GridLayoutData();
		 * childTableLayout.setColumnSpan(2);
		 * deptItems.setLayoutData(childTableLayout);
		 */
		RadioButton rdio_date = deptItems.rdio_date;
		RadioButton rdio_month = deptItems.rdio_month;
		RadioButton rdio_year = deptItems.rdio_year;

		rdio_date.setVisible(false);
		rdio_month.setVisible(false);
		rdio_year.setVisible(false);
		// public RadioButton rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac;
		RadioButton rdio_f1 = deptItems.rdio_f1;
		RadioButton rdio_f2 = deptItems.rdio_f2;
		RadioButton rdio_f3 = deptItems.rdio_f3;
		RadioButton rdio_f5 = deptItems.rdio_f5;
		RadioButton rdio_f6 = deptItems.rdio_f6;
		RadioButton rdio_khac = deptItems.rdio_khac;

		rdio_f1.setVisible(false);
		rdio_f2.setVisible(false);
		rdio_f3.setVisible(false);
		rdio_f5.setVisible(false);
		rdio_f6.setVisible(false);
		rdio_khac.setVisible(false);

		GroupBox gb_fact_detail = deptItems.gb_fact_detail;
		gb_fact_detail.removeAll();
		gb_fact_detail.setHidden(isVisible());

		Button bt_refresh = deptItems.bt_refresh;
		bt_refresh.setVisible(false);

		Label lb_empsn = deptItems.lb_empsn;
		TextField txt_empsn = deptItems.txt_empsn;

		lb_empsn.setVisible(false);
		txt_empsn.setVisible(false);

		Label lb_date = deptItems.lb_date;
		DscDateField df_date = deptItems.df_date;
		DscField tf_date = deptItems.tf_date;

		lb_date.setVisible(false);
		df_date.setVisible(false);
		tf_date.setVisible(false);

		// ----

		grid3.add(deptItems);

		grid3.add(label9);
		grid3.add(label12);
		grid3.add(label11);
		grid3.add(selectField1);

		ok.setIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/search_ok_hover.gif"));
		ok.setPressedIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/search_ok_press.gif"));
		ok.setPressedEnabled(true);
		ok.addActionListener(new ActionListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				OBJ_EMPSN obj_emp_ = new OBJ_EMPSN();
				Date date_ = dscDateField1.getSelectedDate().getTime();
				if (!obj_emp_.check_lock(user_up))// KIEM TRA KHOA CHUC NANG XLY
													// DLIEU
				{
					return;
				} else

				if (!check_condition()) {
					return;
				} else {
					// Begin Kiem tra co quyen quan ly tren so the hay don vi do
					// khong
					if (!cb_dd_dv.isSelected()
							&& !obj_emp_.Kt_vungqly_khi_nhap_st(
									textField1.getText(), ma_user, "DEPSN")) {
						return;
					}

					if (!cb_dd_dv.isSelected()
							&& !obj_emp_.Kt_vungqly_khi_nhap_dv(
									deptItems.getIDDept(), ma_user, "DEPSN")) {
						return;
					}

					if (cb_dd_dv.isSelected()
							&& !obj_emp_.Kt_vungqly_khi_nhap_dv(
									deptItems2.getIDDept(), ma_user, "DEPSN")) {
						return;
					}

					if (cb_dd_dv.isSelected()
							&& !obj_emp_.Kt_vungqly_khi_nhap_dv(
									deptItems.getIDDept(), ma_user, "DEPSN")) {
						return;
					}
					// End Kiem tra co quyen quan ly tren so the hay don vi do
					// khong
				}

				// **Begin kiem tra khoa dlieu thang
				if (!cb_dd_dv.isSelected()
						&& !obj_emp_.check_lock_month(textField1.getText(), "",
								"", "", "", date_, "DEPSN", ma_user, f1, f2,
								f3, f5, f6, khac)) {
					return;
				} else if (cb_dd_dv.isSelected()
						&& !obj_emp_.check_lock_month("", "", "", "",
								deptItems.getIDDept(), date_, "DEPSN", ma_user,
								f1, f2, f3, f5, f6, khac)) {
					return;
				} else if (cb_dd_dv.isSelected()
						&& !obj_emp_.check_lock_month("", "", "", "",
								deptItems2.getIDDept(), date_, "DEPSN",
								ma_user, f1, f2, f3, f5, f6, khac)) {
					return;
				}

				// **End kiem tra khoa dlieu thang

				// *Begin Load lai don vi hh truoc khi luu dlieu
				// *Kiem tra co check vao Tructiep_sx or Giantiep_sx khong? Y:
				// TTSX, N:GTSX
				/*
				 * if(c_tt_gt.isSelected() == false && c_gt_sx.isSelected() ==
				 * false) { OBJ_UTILITY.ShowMessageError(
				 * "Lưu không thành công. Vui lòng chọn 'Trực tiếp SX' hoặc 'Gián tiếp SX'"
				 * ); return; } else if (c_tt_gt.isSelected() == true) { status
				 * = "Y"; }else if (c_gt_sx.isSelected() == true) { status =
				 * "N"; }
				 */

				// HA
				if (c_tt_gt.isSelected() == false
						&& c_gt_sx.isSelected() == false
						&& c_gm.isSelected() == false
						&& c_vp.isSelected() == false) {
					OBJ_UTILITY
							.ShowMessageError("Lưu không thành công. Vui lòng chọn 'Trực tiếp'/'Gián tiếp'/'Giày Mẫu'/'Văn Phòng'");
					return;
				} else if (c_tt_gt.isSelected() == true) {
					status = "TT";
				} else if (c_gt_sx.isSelected() == true) {
					status = "GT";
				} else if (c_gm.isSelected() == true) {
					status = "GM";
				} else if (c_vp.isSelected() == true) {
					status = "VP";
				}
				N_EMPLOYEE data1 = new N_EMPLOYEE();
				IGenericDAO dao1 = Application.getApp()
						.getDao(N_EMPLOYEE.class);
				if (!cb_dd_dv.isSelected())// xet cho neu la nhap so the
				{
					List list1 = dao1
							.find(1,
									"select a.FNAME, a.LNAME, b.NAME_DEPT,b.ID_DEPT,a.USER_MANAGE_ID from N_EMPLOYEE a, N_DEPARTMENT b where a.DEPSN=b.ID_DEPT and a.EMPSN=?",
									new Object[] { textField1.getText() });
					if (list1.size() > 0) {
						Object[] obj1 = (Object[]) list1.get(0);
						hoten.setText(Vni2Uni.convertToUnicode(obj1[0]
								.toString().trim()
								+ " "
								+ obj1[1].toString().trim()));
						tendv.setText(Vni2Uni.convertToUnicode(obj1[2]
								.toString()));
						madv_old.setText(obj1[3].toString());
						maql_old.setText(obj1[4].toString());
					}
				} else// xet neu la chon don vi
				{
					String sql = "select DISTINCT T.USER_MANAGE_ID\n"
							+ "from n_employee t,n_department d\n"
							+ "where t.depsn = d.id_dept\n"
							+ "and d.id_dept = '" + deptItems2.getIDDept()
							+ "'";

					OBJ_UTILITY obj_Util = new OBJ_UTILITY();
					Object rs = obj_Util.Exe_Sql_Obj(sql);

					madv_old.setText(deptItems2.getIDDept());
					madv_old.setForeground(Color.WHITE);

					if (!rs.equals("")) {

						maql_old.setText(rs.toString());
						maql_old.setForeground(Color.WHITE);

					}
				}
				if (madv_old.getText().equals(deptItems.getIDDept())) {
					Application.getApp().showMessageDialog(
							MessageDialog.TYPE_ERROR
									+ MessageDialog.CONTROLS_OK,
							"Đơn vị hiện hành của số thẻ: "
									+ textField1.getText() + " là: "
									+ madv_old.getText()
									+ ".Đơn vị mới phải khác đơn vị hiện hành");
					return;
				} else {
					MessageDialog dlg;
					if (c_tt_gt.isSelected() == true) {
						if (!cb_dd_dv.isSelected()) {
							dlg = new MessageDialog(
									MessageDialog.CONTROLS_YES_NO,
									"Đơn vị hiện hành của số thẻ: "
											+ textField1.getText()
											+ " là: "
											+ madv_old.getText()
											+ ". Bạn có muốn điều động nhân viên này từ: "
											+ madv_old.getText() + " qua: "
											+ deptItems.getIDDept()
											+ " với ngày điều động: "
											+ dscDateField1.getText()
											+ " và thuộc 'Trực tiếp SX' không?");

						} else {
							dlg = new MessageDialog(
									MessageDialog.CONTROLS_YES_NO,
									"Bạn có muốn điều động đơn vị: "
											+ deptItems2.getIDDept() + " qua: "
											+ deptItems.getIDDept()
											+ " với ngày điều động: "
											+ dscDateField1.getText()
											+ " và thuộc 'Trực tiếp SX' không?");

						}
					} else {
						if (!cb_dd_dv.isSelected()) {
							dlg = new MessageDialog(
									MessageDialog.CONTROLS_YES_NO,
									"Đơn vị hiện hành của số thẻ: "
											+ textField1.getText()
											+ " là: "
											+ madv_old.getText()
											+ ". Bạn có muốn điều động nhân viên này từ: "
											+ madv_old.getText() + " qua: "
											+ deptItems.getIDDept()
											+ " với ngày điều động: "
											+ dscDateField1.getText()
											+ " và thuộc 'Gián tiếp SX' không?");

						} else {
							dlg = new MessageDialog(
									MessageDialog.CONTROLS_YES_NO,
									"Bạn có muốn điều động đơn vị: "
											+ deptItems2.getIDDept() + " qua: "
											+ deptItems.getIDDept()
											+ " với ngày điều động: "
											+ dscDateField1.getText()
											+ " và thuộc 'Gián tiếp SX' không?");

						}
					}

					dlg.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (MessageDialog.COMMAND_OK.equals(e
									.getActionCommand())) {
								//
								doSave(e);
							}
						}
					});
				}
			}

		});

		grid3.add(ok);

	}

	private MappingPropertyEditor quanlyEditor() {
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_QUANLY, String> dao = Application.getApp().getDao(
				N_QUANLY.class);
		List<N_QUANLY> list = dao.findAll(10000);
		for (N_QUANLY data : list) {
			e.put(data.getMOTA(), data.getMAQL());
		}
		return e;
	}

	private void doSelect(ActionEvent e) {

		OBJ_EMPSN obj_emp_ = new OBJ_EMPSN();
		maql_new = "";
		c_tt_gt.setSelected(false);
		c_gt_sx.setSelected(false);
		ListBinder.bindSelectField(selectField1, quanlyEditor(), true);
		dscDateField1.setSelectedDate(Calendar.getInstance());

		// ######
		if (!cb_dd_dv.isSelected()
				&& !obj_emp_.Kt_vungqly_khi_nhap_st(textField1.getText(),
						ma_user, "DEPSN")) {
			hoten.setText("");
			tendv.setText("");
			madv_old.setText("");
			return;
		}
		// ######
		N_EMPLOYEE data1 = new N_EMPLOYEE();
		IGenericDAO dao1 = Application.getApp().getDao(N_EMPLOYEE.class);
		List list1 = dao1
				.find(1,
						"select a.FNAME, a.LNAME, b.NAME_DEPT,b.ID_DEPT,a.USER_MANAGE_ID, a.TRUCTIEP_SX from N_EMPLOYEE a, N_DEPARTMENT b where a.DEPSN=b.ID_DEPT and a.EMPSN=?",
						new Object[] { textField1.getText() });
		if (list1.size() > 0) {
			Object[] obj1 = (Object[]) list1.get(0);
			hoten.setText(Vni2Uni.convertToUnicode(obj1[0].toString().trim()
					+ " " + obj1[1].toString().trim()));
			tendv.setText(Vni2Uni.convertToUnicode(obj1[2].toString()));
			madv_old.setText(obj1[3].toString());
			maql_old.setText(obj1[4].toString());
			maql_old.setForeground(Color.WHITE);
			status_old = obj1[5] == null ? "N" : obj1[5].toString();

			if (status_old.equals("Y")) {

				status_sx.setText("Trực tiếp sản xuất");

			} else {

				status_sx.setText("Gián tiếp sản xuất");

			}
		}

	}

	private void doSave(ActionEvent e) {
		// ######

		Date date_now = new Date();

		OBJ_EMPSN obj_emp_ = new OBJ_EMPSN();
		if (!obj_emp_.check_lock(user_up)) {
			return;
		}
		maql_new = (String) ListBinder.get(selectField1);
		Date date_ = dscDateField1.getSelectedDate().getTime();
		str_date = sf_.format(date_);
		strnote = "Date: " + str_date + ", dieu dong tu " + madv_old.getText()
				+ " sang " + deptItems.getIDDept()
				+ ".Thay doi trang thai SX(TTSX:Y,GTSX:N) tu: " + status_old
				+ " sang " + status;
		// *Dieu dong 1 so the
		if (!cb_dd_dv.isSelected()) {

			String sql = "";
			sql = "insert into N_CHDEPSN(empsn,dates,depsn,depsn_old,maql,maql_old,tructiep_sx_old,tructiep_sx_new)values("
					+ " '"
					+ textField1.getText()
					+ "',to_date('"
					+ str_date
					+ "','dd/MM/yyyy'),'"
					+ deptItems.getIDDept()
					+ "',"
					+ "'"
					+ madv_old.getText()
					+ "','"
					+ maql_new
					+ "','"
					+ maql_old.getText()
					+ "','"
					+ status_old
					+ "','"
					+ status
					+ "')";

			obj_util = new OBJ_UTILITY();
			obj_util.Exe_Update_Sql(sql);// insert n_chdepsn
			// ######
			obj_emp_.Insert_N_Action_Daily(ma_user, "N_CHDEPSN", "INSERT",
					textField1.getText(), strnote);
			// ######
			if (date_.before(date_now) || date_.equals(date_now)) {
				IGenericDAO<N_EMPLOYEE, String> objDao = Application.getApp()
						.getDao(N_EMPLOYEE.class);
				N_EMPLOYEE objData = objDao.findById(textField1.getText());
				objData.setDEPSN(deptItems.getIDDept());
				objData.setUSER_MANAGE_ID(maql_new);
				objData.setTRUCTIEP_SX(status);

				if (maql_old.getText().equals("FVA")
						|| maql_old.getText().equals("FVS")
						|| maql_old.getText().equals("KDA")
						|| maql_old.getText().equals("BGC")
						|| maql_old.getText().equals("XTH")
						|| maql_old.getText().equals("FV0")
						|| maql_old.getText().equals("TB0")
						|| maql_old.getText().equals("TB1")
						|| maql_old.getText().equals("TB2")) {
					objData.setDEPSN_TEMP(deptItems.getIDDept());
					objData.setDEPSN_TEMP1(deptItems.getIDDept());
				} else {
					objData.setDEPSN_TEMP(madv_old.getText());
				}

				objDao.update(objData);// update employee
				// ###
				strnote = "";
				strnote = "Date: " + str_date
						+ ", doi vung quan ly do dieu dong " + " tu "
						+ maql_old.getText() + " sang " + maql_new;
				obj_emp_.Insert_N_Action_Daily(ma_user, "N_EMPLOYEE", "UPDATE",
						textField1.getText(), strnote);
			}
			// ###
			// #Update lai phep nam
			update_pn_save(textField1.getText(), madv_old.getText(),
					deptItems.getIDDept(), str_date);
			// #End Update lai phep nam
			// Application.getApp().showMessageDialog(MessageDialog.TYPE_INFORMATION+MessageDialog.CONTROLS_OK,
			// "Bạn đã điều động thành công");
			MessageDialog dlg = new MessageDialog(MessageDialog.CONTROLS_OK,
					"Bạn đã điều động thành công");
			dlg.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (MessageDialog.COMMAND_OK.equals(e.getActionCommand())) {
						doSelect(e);
					}
				}
			});
		} else
		// **Dieu dong 1 don vi
		{
			List<String> List_dd_dv = null;
			String sql = "select t.empsn  from n_employee t\n"
					+ "where t.depsn = '" + deptItems2.getIDDept() + "'";

			obj_util = new OBJ_UTILITY();
			List_dd_dv = obj_util.Exe_Sql_String(sql);

			if (List_dd_dv.size() > 0) {
				for (String empsn : List_dd_dv) {
					// # lay trang thai truc tiep sx hay gian tiep sx truoc khi
					// dd
					IGenericDAO<N_EMPLOYEE, String> objDao = Application
							.getApp().getDao(N_EMPLOYEE.class);
					N_EMPLOYEE objData = objDao.findById(empsn);
					status_old = objData.getTRUCTIEP_SX() == null ? "N"
							: objData.getTRUCTIEP_SX();
					strnote = "Date: " + str_date + ", dieu dong tu "
							+ madv_old.getText() + " sang "
							+ deptItems.getIDDept()
							+ ".Thay doi trang thai SX(TTSX:Y,GTSX:N) tu: "
							+ status_old + " sang " + status;
					// # end lay trang thai truc tiep sx hay gian tiep sx truoc
					// khi dd

					// ###
					update_pn_save(empsn, deptItems2.getIDDept(),
							deptItems.getIDDept(), str_date);
					// ###
					String sql1 = "";
					sql1 = "insert into N_CHDEPSN(empsn,dates,depsn,depsn_old,maql,maql_old,tructiep_sx_old,tructiep_sx_new)values("
							+ " '"
							+ empsn
							+ "',to_date('"
							+ str_date
							+ "','dd/MM/yyyy'),'"
							+ deptItems.getIDDept()
							+ "',"
							+ "'"
							+ deptItems2.getIDDept()
							+ "','"
							+ maql_new
							+ "','"
							+ maql_old.getText()
							+ "','"
							+ status_old + "','" + status + "')";

					obj_util = new OBJ_UTILITY();
					obj_util.Exe_Update_Sql(sql1);// insert n_chdepsn

					obj_emp_.Insert_N_Action_Daily(ma_user, "N_CHDEPSN",
							"INSERT", empsn, strnote);
					// ###
					// *update employee
					if (date_.before(date_now) || date_.equals(date_now)) {
						/*
						 * IGenericDAO<N_EMPLOYEE, String> objDao =
						 * Application.getApp().getDao(N_EMPLOYEE.class);
						 * N_EMPLOYEE objData = objDao.findById(empsn);
						 */
						objData.setDEPSN(deptItems.getIDDept());
						objData.setUSER_MANAGE_ID(maql_new);
						objData.setTRUCTIEP_SX(status);

						if (maql_old.getText().equals("FVA")
								|| maql_old.getText().equals("FVS")
								|| maql_old.getText().equals("KDA")
								|| maql_old.getText().equals("BGC")
								|| maql_old.getText().equals("XTH")) {
							objData.setDEPSN_TEMP(deptItems.getIDDept());
							objData.setDEPSN_TEMP1(deptItems.getIDDept());
						} else {
							objData.setDEPSN_TEMP(deptItems2.getIDDept());
						}

						objDao.update(objData);// update employee

					}

				}
				Application.getApp().showMessageDialog(
						MessageDialog.TYPE_INFORMATION
								+ MessageDialog.CONTROLS_OK,
						"Bạn đã điều động thành công");
			} else {
				Application
						.getApp()
						.showMessageDialog(
								MessageDialog.TYPE_INFORMATION
										+ MessageDialog.CONTROLS_OK,
								"Đơn vị "
										+ deptItems2.getIDDept()
										+ " không tồn tại nhân viên nào để điều động. Kiểm tra lại dữ liệu");
			}

		}
	}

	private void update_pn_save(String sothe, String madv_old, String madv_new,
			String ngay_dd) {
		long may1 = 0;
		long may2 = 0;
		long t1 = 0;
		long t2 = 0;
		long pn = 0;
		long psd = 0;
		long pno = 0;
		long txin = 0;
		long pcl = 0;

		long pn1 = 0;
		long pcl1 = 0;
		long txin1 = 0;
		long psd1 = 0;
		long pno1 = 0;
		String year = ngay_dd.substring(6, 10);
		String str_date1 = "31/03/" + year;
		String str_date2 = "30/06/" + year;
		String str_date3 = "30/09/" + year;

		Date date_dd = null;
		Date date1 = null;
		Date date2 = null;
		Date date3 = null;

		Check_depsn(madv_old, madv_new);// Lay note dvi cũ va dvi moi de update
										// lai pn

		String sql =

		"select t.stitching1,t.stitching2,t.obtain,t.used,t.remain,t.debt,t.emp_app\n"
				+ " from n_rest t\n" + "where t.empsn = '"
				+ textField1.getText() + "'\n" + "and t.year = '" + year + "'";

		OBJ_UTILITY obj_Utility = new OBJ_UTILITY();
		List<Object[]> list_rest = obj_Utility.Exe_Sql_nfield_nrow(sql, 7);

		if (list_rest.size() > 0) {
			Object[] obj_rest = (Object[]) list_rest.get(0);
			t1 = Integer.valueOf(obj_rest[0].toString());
			t2 = Integer.valueOf(obj_rest[1].toString());
			pn = Integer.valueOf(obj_rest[2].toString());
			psd = Integer.valueOf(obj_rest[3].toString());
			pcl = Integer.valueOf(obj_rest[4].toString());
			pno = Integer.valueOf(obj_rest[5].toString());
			txin = Integer.valueOf(obj_rest[6].toString());
		}
		pn1 = pn - (t1 + t2);
		pcl1 = pcl - (t1 + t2);
		txin1 = txin - (t1 + t2);

		// Begin Chuyen ngay kieu chuoi -->date
		// *Ngay dd
		try {
			date_dd = sf_.parse(ngay_dd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// *date1
		try {
			date1 = sf_.parse(str_date1);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		// *date2
		try {
			date2 = sf_.parse(str_date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// *date3
		try {
			date3 = sf_.parse(str_date3);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// End Chuyen ngay kieu chuoi -->date

		// #Begin xet MAY hay <> MAY de update lai pn
		if (!note_dv_old.equals("MAY") && note_dv_new.equals("MAY")) {

			if (date_dd.before(date1) || date_dd.equals(date1)) {
				may1 = 1;
				may2 = 1;
			} else if (date_dd.after(date1)
					&& (date_dd.before(date2) || date_dd.equals(date2))) {
				may1 = 0;
				may2 = 1;
			} else if (date_dd.after(date2)
					&& (date_dd.before(date3) || date_dd.equals(date3))) {
				may1 = 0;
				may2 = 1;
			} else if (date_dd.after(date3)) {
				may1 = 0;
				may2 = 0;
			}

		} else if (note_dv_old.equals("MAY") && !note_dv_new.equals("MAY")) {
			if (date_dd.before(date1) || date_dd.equals(date1)) {
				may1 = 0;
				may2 = 0;
			} else if (date_dd.after(date1)
					&& (date_dd.before(date2) || date_dd.equals(date2))) {
				may1 = 1;
				may2 = 0;
			} else if (date_dd.after(date2)
					&& (date_dd.before(date3) || date_dd.equals(date3))) {
				may1 = 1;
				may2 = 0;
			} else if (date_dd.after(date3)) {
				may1 = 1;
				may2 = 1;
			}
		} else if (note_dv_old.equals("MAY") && note_dv_new.equals("MAY")) {
			may1 = t1;
			may2 = t2;
		}
		// #End xet MAY hay <> MAY de update lai pn

		// *Begin update lai phep nam
		pn1 = pn1 + may1 + may2; // phep moi
		pcl1 = pcl1 + may1 + may2;
		psd1 = psd;
		txin1 = txin1 + may1 + may2;

		if ((pn == psd) && (pn1 < pn)) // da sd het PN, ma bi tru them 1 ngay

		{
			pno1 = pno + (pn - pn1);
		}

		if ((pcl1 < 0) && (pno1 - pno == 1)) {
			pcl1 = pcl1 + 1;
			psd1 = psd1 - 1;
		}

		// *End update lai phep nam
		IGenericDAO<N_REST, N_RESTPk> Dao_REST = Application.getApp().getDao(
				N_REST.class);
		N_RESTPk pk = new N_RESTPk(sothe, year);
		N_REST Data_REST = Dao_REST.findById(pk);
		if (Data_REST == null) {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Không tìm thấy phép năm " + year + " của số thẻ " + sothe);
		} else {
			// update
			Data_REST.setOBTAIN(pn1);
			Data_REST.setREMAIN(pcl1);
			Data_REST.setUSED(psd1);
			Data_REST.setDEBT(pno1);
			Data_REST.setEMP_APP(txin1);
			Data_REST.setSTITCHING1(may1);
			Data_REST.setSTITCHING2(may2);

			Dao_REST.update(Data_REST);
		}

	}

	private void Check_depsn(String madv_old, String madv_new) {
		String sql = "select t.note from n_department t\n"
				+ "where t.id_dept = '" + madv_old + "'";

		String sql1 = "select t.note from n_department t\n"
				+ "where t.id_dept = '" + madv_new + "'";

		OBJ_UTILITY obj_Utility = new OBJ_UTILITY();
		List<Object> list = obj_Utility.Exe_sql_nfield_1row(sql, 1);
		List<Object> list1 = obj_Utility.Exe_sql_nfield_1row(sql1, 1);
		// ###
		if (list != null && list.size() > 0) {
			note_dv_old = list.get(0).toString();
		} else {
			note_dv_old = "";
		}
		// ####

		// ###
		if (list1 != null && list1.size() > 0) {
			note_dv_new = list1.get(0).toString();
		} else {
			note_dv_new = "";
		}
		// ####

	}

	private boolean check_condition() {
		if (textField1.getText().equals("") && cb_dd_dv.isSelected() == false) {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Vui lòng nhập số thẻ cần điều động");
			return false;
		} else if (cb_dd_dv.isSelected() && deptItems2.getIDDept().equals("")) {// 01/03/2012(dd
																				// tu
																				// A->B)
																				// kiem
																				// tra
																				// nhap
																				// dvi
																				// A
																				// truoc
																				// khi
																				// luu
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Vui lòng chọn đơn vị cần điều động");
			return false;
		} else if (deptItems.getIDDept().equals("")) {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Vui lòng chọn đơn vị mới phải điều động đến");
			return false;
		}

		else if (check_dd() == false) {// kiem tra so the voi ngay dd da co chua
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Bạn đã nhập điều động rồi");
			return false;
		} else if (maql_new.equals("")) {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Vui lòng chọn vùng quản lý");
			return false;
		} else { // * Bdau lay ngay ddd max: ngay dieu dong mo phai >= ngay dd
					// cu lon nhat
			Date ngay_dd_max;

			String sql = "select max(t.dates)\n" + "from n_chdepsn t\n"
					+ "where t.empsn = '" + textField1.getText() + "'";
			OBJ_UTILITY obj_util = new OBJ_UTILITY();
			Object obj = new Object();
			obj = obj_util.Exe_Sql_Obj(sql);
			if (obj == null) {
				return true;
			} else {
				ngay_dd_max = (Date) obj;

				Date date_ = dscDateField1.getSelectedDate().getTime();

				if (date_.before(ngay_dd_max)) {
					Application.getApp().showMessageDialog(
							MessageDialog.TYPE_ERROR
									+ MessageDialog.CONTROLS_OK,
							"Ngày điều động mới phải > ngày điều động cũ");
					return false;
				} else {
					return true;
				}
			}
		}

	}

	private boolean check_dd() {// kiem tra so the voi ngay dd da co chua
		Date date_ = dscDateField1.getSelectedDate().getTime();
		str_date = sf_.format(date_);
		String sql = "select count(t.empsn) from n_chdepsn t\n"
				+ "where t.empsn ='" + textField1.getText().toString() + "'"
				+ " and t.dates =  to_date('" + str_date + "','dd/MM/yyyy')";

		OBJ_UTILITY obj_Utility = new OBJ_UTILITY();
		Object rs = obj_Utility.Exe_Sql_Obj(sql);

		if (Long.valueOf(rs.toString()) == 0) {
			return true;
		} else {

			return false;
		}

	}

	private void check_qlmoi(ActionEvent e) {
		maql_new = (String) ListBinder.get(selectField1);
		String ten_qly = selectField1.getSelectedItem().toString();
		IGenericDAO<N_USER_LIMIT, String> Dao_USER_LIMIT = Application.getApp()
				.getDao(N_USER_LIMIT.class);
		DetachedCriteria objDC_USER_LIMIT = DetachedCriteria
				.forClass(N_USER_LIMIT.class);
		objDC_USER_LIMIT.add(Restrictions.eq("MA_USER", ma_user));
		objDC_USER_LIMIT.add(Restrictions.eq("MA_QL", maql_new));
		List<N_USER_LIMIT> dataList = Dao_USER_LIMIT.findByCriteria(1,
				objDC_USER_LIMIT);
		if (dataList.size() <= 0) {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Bạn không có quyền thao tác " + ten_qly);
			// ok.setVisible(false);
			maql_new = "";
			ListBinder.bindSelectField(selectField1, quanlyEditor(), true);

		} else {
			// ok.setVisible(true);
			System.out.println("ok");

		}

	}

	private void dd_1_dv(ActionEvent e) {
		// TODO Implement.

		if (cb_dd_dv.isSelected()) {
			deptItems2 = new DeptUserControl_FactDetail();
			grid2.remove(0);
			grid2.add(new Label("Đơn vị "), 0);
			grid2.remove(1);
			grid2.add(deptItems2, 1);
			hoten.setText("-");
			tendv.setText("");
			madv_old.setText("");
			status_sx.setText("");
			c_tt_gt.setSelected(false);
			c_gt_sx.setSelected(false);
			textField1.setText("");

			// * deptItems2
			RadioButton rdio_date1 = deptItems2.rdio_date;
			RadioButton rdio_month1 = deptItems2.rdio_month;
			RadioButton rdio_year1 = deptItems2.rdio_year;

			rdio_date1.setVisible(false);
			rdio_month1.setVisible(false);
			rdio_year1.setVisible(false);
			// public RadioButton
			// rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac;
			RadioButton rdio_f1_1 = deptItems2.rdio_f1;
			RadioButton rdio_f2_1 = deptItems2.rdio_f2;
			RadioButton rdio_f3_1 = deptItems2.rdio_f3;
			RadioButton rdio_f5_1 = deptItems2.rdio_f5;
			RadioButton rdio_f6_1 = deptItems2.rdio_f6;
			RadioButton rdio_khac_1 = deptItems2.rdio_khac;

			rdio_f1_1.setVisible(false);
			rdio_f2_1.setVisible(false);
			rdio_f3_1.setVisible(false);
			rdio_f5_1.setVisible(false);
			rdio_f6_1.setVisible(false);
			rdio_khac_1.setVisible(false);

			GroupBox gb_fact_detail_1 = deptItems2.gb_fact_detail;
			gb_fact_detail_1.removeAll();
			gb_fact_detail_1.setHidden(isVisible());

			Button bt_refresh_1 = deptItems2.bt_refresh;
			bt_refresh_1.setVisible(false);

			Label lb_empsn = deptItems2.lb_empsn;
			TextField txt_empsn = deptItems2.txt_empsn;

			lb_empsn.setVisible(false);
			txt_empsn.setVisible(false);

			Label lb_date = deptItems2.lb_date;
			DscDateField df_date = deptItems2.df_date;
			DscField tf_date = deptItems2.tf_date;

			lb_date.setVisible(false);
			df_date.setVisible(false);
			tf_date.setVisible(false);

		} else {
			grid2.remove(0);
			grid2.add(new Label("Số thẻ"), 0);
			grid2.remove(1);
			grid2.add(textField1, 1);
		}
	}

	/**
	 * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		Grid grid1 = new Grid();
		grid1.setSize(3);
		add(grid1);
		GroupBox groupBox1 = new GroupBox();
		groupBox1.setTitle("T.Tin Điều Động");
		grid1.add(groupBox1);
		grid2 = new Grid();
		groupBox1.add(grid2);
		Label label1 = new Label();
		label1.setText("Số thẻ");
		grid2.add(label1);
		textField1 = new TextField();
		textField1.setMaximumLength(8);
		textField1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSelect(e);
			}
		});
		grid2.add(textField1);
		hoten = new Label();
		hoten.setText("-");
		hoten.setForeground(Color.BLUE);
		grid2.add(hoten);
		label3 = new Label();
		grid2.add(label3);
		tendv = new Label();
		tendv.setForeground(Color.BLUE);
		grid2.add(tendv);
		label7 = new Label();
		grid2.add(label7);
		madv_old = new Label();
		madv_old.setForeground(Color.BLUE);
		grid2.add(madv_old);

		label8 = new Label();
		label8.setForeground(Color.BLUE);
		grid2.add(label8);

		status_sx = new Label();
		status_sx.setForeground(Color.MAGENTA);
		grid2.add(status_sx);

		label17 = new Label();
		label17.setForeground(Color.BLUE);
		grid2.add(label17);

		maql_old = new Label();
		maql_old.setForeground(Color.BLUE);
		grid2.add(maql_old);
		label14 = new Label();
		grid2.add(label14);
		Label label4 = new Label();
		label4.setText("Ngày Đ.Động");
		grid2.add(label4);

		dscDateField1 = new DscDateField();

		grid2.add(dscDateField1);
		Label label2 = new Label();
		label2.setText("-");
		label2.setForeground(Color.BLUE);
		grid2.add(label2);
		Label label5 = new Label();
		grid2.add(label5);

		c_tt_gt = new CheckBox();
		c_tt_gt.setText("Trực tiếp SX");
		grid2.add(c_tt_gt);
		c_tt_gt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				c_gt_sx.setSelected(false);

			}
		});

		c_gt_sx = new CheckBox();
		c_gt_sx.setText("Gián tiếp SX");
		grid2.add(c_gt_sx);
		c_gt_sx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				c_tt_gt.setSelected(false);

			}
		});

		c_gm = new CheckBox();
		c_gm.setText("Giày Mẫu");
		grid2.add(c_gm);

		c_vp = new CheckBox();
		c_vp.setText("Văn Phòng");
		grid2.add(c_vp);

		/*
		 * Label label6 = new Label(); grid2.add(label6);
		 */

		Label label13 = new Label();
		label13.setText("-");
		label13.setForeground(Color.BLUE);
		grid2.add(label13);
		Label label15 = new Label();
		grid2.add(label15);
		cb_dd_dv = new CheckBox();
		cb_dd_dv.setText("Điều động 1 đơn vị");
		cb_dd_dv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dd_1_dv(e);
			}
		});

		grid2.add(cb_dd_dv);
		groupBox2 = new GroupBox();
		groupBox2.setTitle("Điều động qua đơn vị mới");
		grid1.add(groupBox2);
		grid3 = new Grid();
		groupBox2.add(grid3);
		Label label10 = new Label();
		grid3.add(label10);
		label9 = new Label();
		label9.setText("-");
		label9.setForeground(Color.BLUE);
		grid3.add(label9);
		label12 = new Label();
		grid3.add(label12);
		label11 = new Label();
		label11.setText(" Chọn Q.Lý");
		grid3.add(label11);
		selectField1 = new SelectField();
		selectField1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check_qlmoi(e);
			}
		});
		grid3.add(selectField1);
		ok = new Button();
		// grid3.add(ok);

	}
}