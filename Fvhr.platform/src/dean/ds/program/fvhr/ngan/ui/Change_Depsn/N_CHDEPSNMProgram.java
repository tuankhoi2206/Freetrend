package ds.program.fvhr.ngan.ui.Change_Depsn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.domain.N_CHDEPSN;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_REST;
import ds.program.fvhr.domain.N_USER_LIMIT;
import ds.program.fvhr.domain.pk.N_RESTPk;
import ds.program.fvhr.tien.ui.DSHR71_EXCCEL;
import ds.program.fvhr.tien.ui.N_CHDEPT_EXECEL;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.QueryPane;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;

/**
 * N_CHDEPSN *
 */
public class N_CHDEPSNMProgram extends MaintainSProgram {
	private String factCondition = "AND 1 <> 1";
	SimpleDateFormat sf_ = new SimpleDateFormat(OBJ_UTILITY.DATE_FORMAT_STR);
	String note_dv_old = "";
	String note_dv_new = "";
	String strnote = "";
	String user_up = Application.getApp().getLoginInfo().getUserID();
	String ma_user = "";
	String sothe;
	Date date_;
	String madv_new;
	String madv_old;
	String maql_old;
	String maql_new;
	String status_sx_old = "";
	String status_sx_new = "";
	RadioButton f1, f2, f3, f5, f6, khac;

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#createDataContent()
	 */
	@Override
	protected void createDataContent() {
		setMasterDataContent(new N_CHDEPSNMDataContent());
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected int doInit() {
		int ret = super.doInit();
		String condStr = "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D WHERE E.EMPSN=o.EMPSN AND E.DEPSN=D.ID_DEPT "
				+ factCondition + ")";
		ProgramCondition pc = new ProgramCondition("1<>1", new Object[] {});
		setQueryCondition(pc);

		IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(
				DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();

		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dsc.echo2app.program.DefaultProgram#doInitProgramOK()
	 */
	@Override
	protected void doInitProgramOK() {
		// <初始時是否撈取資料>
		// 如果要一執行程式時就取出資料，則執行下行程式
		// this.refresh(); //取出資料必更新畫面
	}

	@Override
	protected QueryPane createNormalQuery() {
		return new N_CHDEPSNMQuery();
	}

	/*
	 * 調整UI Layout
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
		this.getBrowserContent().setPageSize(20);
		this.getBrowserContent().setMaxSize(100000);
		Button importButton = new Button();
		importButton.setText("Xuất ra Excel");
		importButton.setStyleName("Default.ToolbarButton");
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				N_CHDEPT_EXECEL objN_CHDEPT_EXECEL = new N_CHDEPT_EXECEL();
				Application.getApp().getDefaultWindow().getContent()
						.add(objN_CHDEPT_EXECEL);

			}
		});
		this.getMasterToolbar().add(importButton);
	}

	@Override
	public boolean doDelete() {

		String ngay_dd_max = "";
		// *Bdau kiem tra ngay dd can xoa
		if (!check_delete()) {
			return false;
		}
		// *Bdau Update vao N_EMPLOYEE
		IGenericDAO<N_EMPLOYEE, String> objDao = Application.getApp().getDao(
				N_EMPLOYEE.class);
		N_EMPLOYEE data_employee = objDao.findById(sothe);
		data_employee.setDEPSN(madv_old);
		data_employee.setUSER_MANAGE_ID(maql_old);
		data_employee.setTRUCTIEP_SX(status_sx_old);

		if (maql_old.equals("FVA") || maql_old.equals("FVS")
				|| maql_old.equals("KDA") || maql_old.equals("BGC")
				|| maql_old.equals("XTH") || maql_old.equals("FV0")
				|| maql_old.equals("TB0") || maql_old.equals("TB1")
				|| maql_old.equals("TB2")) {
			data_employee.setDEPSN_TEMP(madv_old);// 26/11/2012 da hoi lai
													// c.Hoang Anh la update
													// Depsn_temp k phai
													// depsn_temp1 nua

		}

		objDao.update(data_employee);// update employee
		// * Bdau lay ngay ddd max lien ke voi ngay vua xoa de update lai pnam
		String sql = "select max(t.dates)\n" + "from n_chdepsn t\n"
				+ "where t.empsn = '" + sothe + "'";

		OBJ_UTILITY obj_util = new OBJ_UTILITY();
		Object obj = new Object();
		obj = obj_util.Exe_Sql_Obj(sql);
		if (obj == null) {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Không có thông tin điều động");
			return false;
		} else {
			ngay_dd_max = sf_.format((Date) obj);

			update_pn_delete(sothe, madv_new, madv_old, ngay_dd_max);

			String str_note = "Date: " + sf_.format(date_);
			OBJ_EMPSN obj_emp_ = new OBJ_EMPSN();
			obj_emp_.Insert_N_Action_Daily(ma_user, "N_CHDEPSN", "DELETE",
					sothe, str_note);
		}
		Application.getApp().showMessageDialog(
				MessageDialog.TYPE_INFORMATION + MessageDialog.CONTROLS_OK,
				"Xóa thành công");
		return super.doDelete();// delete khoi n_chdepsn

	}

	private boolean check_delete() {
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

		OBJ_EMPSN obj_emp = new OBJ_EMPSN();
		OBJ_UTILITY obj_util = new OBJ_UTILITY();
		boolean flag = false;
		if (!obj_emp.check_lock(user_up))// KIEM TRA KHOA CHUC NANG XLY DLIEU
		{
			flag = false;
		}
		N_CHDEPSN data_chdepsn = (N_CHDEPSN) this.getBrowserContent()
				.getDataObjectSet().getDataObject();

		sothe = data_chdepsn.getEMPSN();
		date_ = data_chdepsn.getDATES();
		madv_new = data_chdepsn.getDEPSN();
		madv_old = data_chdepsn.getDEPSN_OLD();
		maql_old = data_chdepsn.getMAQL_OLD();
		maql_new = data_chdepsn.getMAQL();
		status_sx_old = data_chdepsn.getTRUCTIEP_SX_OLD();
		status_sx_new = data_chdepsn.getTRUCTIEP_SX_NEW();

		String date_str = sf_.format(date_);
		String month = date_str.substring(3, 5);
		String year = date_str.substring(6, 10);

		if (obj_emp.check_lock_month(sothe, "", "", "", "", date_, "DEPSN",
				ma_user, f1, f2, f3, f5, f6, khac)) {
			Date ngay_dd_max;

			String sql = "select max(t.dates)\n" + "from n_chdepsn t\n"
					+ "where t.empsn = '" + sothe + "'";

			Object obj = new Object();
			obj = obj_util.Exe_Sql_Obj(sql);
			if (obj == null) {
				Application.getApp().showMessageDialog(
						MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
						"Không có thông tin điều động");
				flag = false;
			} else {
				ngay_dd_max = (Date) obj;

				if (date_.before(ngay_dd_max)) {
					Application.getApp().showMessageDialog(
							MessageDialog.TYPE_ERROR
									+ MessageDialog.CONTROLS_OK,
							"Ngày điều động gần đây nhất là: "
									+ sf_.format(ngay_dd_max)
									+ ". Không thể xóa ngày điều động: "
									+ sf_.format(date_));
					flag = false;
				} else {
					flag = true;
				}
			}
		}
		return flag;
	}

	private void update_pn_delete(String sothe, String madv_new,
			String madv_old, String ngay_dd) {
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
				+ " from n_rest t\n" + "where t.empsn = '" + sothe + "'\n"
				+ "and t.year = '" + year + "'";

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
		if (note_dv_old.equals("MAY") && !note_dv_new.equals("MAY")) {

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

		} else if (!note_dv_old.equals("MAY") && note_dv_new.equals("MAY")) {
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

	/*
	 * 使用者資料主檔Select change
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doMasterDataSelectChange()
	 */
	@Override
	protected void doMasterDataSelectChange() {
		super.doMasterDataSelectChange();
		// 6.<功能權限管控>
	}

	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected String[] getBrowserDisplayColumns() {
		// 4.<資料瀏覽欄位>
		// return new String[] { "EMPSN", "DATES", "DEPSN", "DEPSN_OLD", "MAQL",
		// "MAQL_OLD", "TRUCTIEP_SX_OLD", "TRUCTIEP_SX_NEW" };
		// khoi test
		//DEPARTMENT
		
		return new String[] { "EMPSN", "DATES", "DEPSN", "DEPSN",
				"DEPSN_OLD", "MAQL", "MAQL_OLD", "TRUCTIEP_SX_OLD",
				"TRUCTIEP_SX_NEW" };
	}
}
