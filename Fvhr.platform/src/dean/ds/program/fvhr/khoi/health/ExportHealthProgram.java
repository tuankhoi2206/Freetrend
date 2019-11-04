package ds.program.fvhr.khoi.health;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.list.ListModel;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import fv.util.Vni2Uni;
import ds.program.fvhr.domain.N_N_JOB;
import ds.program.fvhr.util.OBJ_EMPSN;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportService;
import dsc.echo2app.program.TaskExecuter;
import dsc.echo2app.program.TaskExecuterHelper;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.task.ATask;
import dsc.echo2app.task.ReportParameterWrapper;
import dsc.echo2app.task.TaskInfoWrapper;
import dsc.echo2app.task.TaskResultWrapper;

public class ExportHealthProgram {

	private Connection con = null;
	SimpleDateFormat sf = ExportHealthProgram.Get_format_date();

	public ExportHealthProgram() {

	}

	public static SimpleDateFormat Get_format_date() {
		return new SimpleDateFormat("dd/MM/yyyy");
	}

	/**
	 * 
	 * @return String key = USER_ddmmyy_hhmmss
	 */

	public static Date DateFormat_DDMMYYYY(Date date_in) {
		Date date_out = null;
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

		try {
			date_out = sf.parse(sf.format(date_in));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date_out;

	}

	public static Date DateFormat_DDMMYYYY(String txt_date_in) {
		Date date_out = null;
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

		try {
			date_out = sf.parse(txt_date_in);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date_out;

	}

	private void OpenConSql() {
		try {
			if (con == null || con.isClosed()) {
				con = Application.getApp().getConnection();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void CloseConSql() {
		try {
			if (con != null || !con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String DATE_FORMAT_STR = "dd/MM/yyyy";

	public static Date MONTH_PRE(String day, String month, String year) {
		Date date = null;
		Calendar ca = Calendar.getInstance();
		if (day.equals("")) {
			day = "01";
		}
		String date_str = day + "/" + month + "/" + year;
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

		try {

			date = sf.parse(date_str);

		} catch (ParseException e) {

			e.printStackTrace();
		}

		ca.setTime(date);
		ca.add(Calendar.MONTH, -1);

		return ca.getTime();
	}

	/**
	 * Tien toi hoac quay lui(true/false) n_roll thang cua date_input
	 * 
	 * @param date_input
	 *            : ngay thang can thay doi -- flag_ : tien/lui -- num_roll_ :
	 *            so thang can thay doi
	 * @return
	 */
	public static Date MONTH_ROLL(Date date_input_, boolean flag_, int num_roll_) {

		SimpleDateFormat sf_ = new SimpleDateFormat("dd/MM/yyyy");
		Calendar ca = Calendar.getInstance();
		ca.setTime(date_input_);
		// for (int i = 1; i <= num_roll_; i++) {
		ca.add(Calendar.MONTH, num_roll_);
		// }
		System.out.println("Roll : " + sf_.format(ca.getTime()));
		return ca.getTime();

	}

	public static int GET_NUM_DAY_OF_MONTH(Date date_input_) {

		SimpleDateFormat sf_ = new SimpleDateFormat("dd/MM/yyyy");
		String str_date = sf_.format(date_input_);
		int month = Integer.valueOf(str_date.substring(3, 5));
		int year = Integer.valueOf(str_date.substring(6, 10));
		int date = 0;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			date = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			date = 30;
			break;
		case 2:
			if (((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0))
				date = 29;
			else
				date = 28;
			break;

		default:
			date = 0;
			break;
		}

		return (date);

	}

	public ArrayList<String> getListEmpsnByIDDept(String IDdept, Date date) {

		String sql = "select e.empsn from n_employee e\n"
				+ "where        e.depsn     = '" + IDdept + "'"
				+ " and e.depsn <> '00000' ";

		ArrayList<String> list_emp = (ArrayList<String>) this
				.Exe_Sql_String(sql);

		return list_emp;
	}

	/*
	 * public static long Round_Salary(long a){ //b=a%1000 lam tron 500
	 * --28/07/2011 long rs; long b = a%1000; long c = (long) (a/1000);
	 * 
	 * rs = c *1000 + (long)(Math.round((float)b/100)*100); return rs; }
	 */
	// ---------Goi Function lam tron hang tram C_MYMOD1 Ngan
	public static long Round_Salary(long a) {
		long rs = 0;
		Connection con = Application.getApp().getConnection();
		String sql = "{?=call C_MYMOD1(?)}";

		try {
			CallableStatement stm = con.prepareCall(sql);
			stm.setLong(2, a);
			stm.registerOutParameter(1, Types.NUMERIC);
			stm.execute();
			rs = stm.getLong(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}

	// __________________ List Model

	public ListModel Get_Model_Dept(String index1, String index2) {

		DefaultListModel model = new DefaultListModel();
		String sql = "";

		sql = " select distinct t.name_dept_name" + " from n_department t "
				+ " where t.name_fact = '" + index1 + "' "
				+ " and t.name_group = '" + index2 + "'";

		List<String> list = null;

		list = Exe_Sql_String(sql);
		model.add("");
		for (String str : list) {
			if (str != null)
				model.add(Vni2Uni.convertToUnicode(str));
		}

		return model;
	}

	public ListModel Get_Model_Dept() {

		DefaultListModel model = new DefaultListModel();

		MappingPropertyEditor map_editor = this.Get_MapEditor_DEPSN_NAME();

		model.add("");
		for (String str : map_editor.getDisplays()) {
			if (str != null)
				model.add(Vni2Uni.convertToUnicode(str));
		}

		return model;
	}

	public DefaultListModel Get_Model_Group(String indexItem) {
		// TODO Auto-generated method stub
		DefaultListModel model = new DefaultListModel();
		String sql = " select distinct t.name_group" + " from n_department t "
				+ " where t.name_fact = '" + indexItem + "' ";

		List<String> list = null;

		list = Exe_Sql_String(sql);

		model.add(" ");
		if (list != null && list.size() > 0) {
			for (String str : list) {
				if (str != null)

					model.add(Vni2Uni.convertToUnicode(str));
			}
		}
		return model;

	}

	public ListModel Get_Model_Fact() {

		DefaultListModel model = new DefaultListModel();
		String sql = "select distinct t.name_fact from n_department t";
		//

		List<String> list = Exe_Sql_String(sql);
		model.add(" ");
		for (String str : list) {
			model.add(Vni2Uni.convertToUnicode(str));
		}

		return model;
	}

	// 19/04/2012 Ngan
	public ListModel Get_Model_Id_dept() {

		DefaultListModel model = new DefaultListModel();
		String sql = "select distinct t.id_dept from n_department t";
		//

		List<String> list = Exe_Sql_String(sql);
		model.add(" ");
		for (String str : list) {
			model.add(Vni2Uni.convertToUnicode(str));
		}

		return model;
	}

	// __________________________________________________________________

	// ___________________ MAPPING EDITOR

	@SuppressWarnings("unused")
	private MappingPropertyEditor factEditor() {
		String sql = " select distinct t.name_fact from n_department t ";
		List<String> list = null;
		MappingPropertyEditor e = new MappingPropertyEditor();
		list = Exe_Sql_String(sql);
		for (String str : list) {
			e.put(str, str);
		}

		return e;
	}

	public boolean CheckRight(String user_, String empsn_) {
		boolean rs = false;
		int get_rs = 0;
		Connection con = Application.getApp().getConnection();
		try {
			CallableStatement stm = con
					.prepareCall("{?=call CHECK_RIGHT_ON_EMPSN(?,?)}");

			stm.registerOutParameter(1, Types.INTEGER);
			stm.setString(2, user_.toUpperCase());
			stm.setString(3, empsn_);

			stm.execute();
			get_rs = stm.getInt(1);
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (get_rs == 1) {
			rs = true;
		} else {
			rs = false;
		}

		return rs;
	}

	// ===========================================

	// ================== SQL DAO
	/**
	 * Select n column -> return n row
	 * 
	 * @param sql
	 * @param nfield
	 * @return
	 * @throws SQLException
	 */

	public boolean Exe_update_sql(String sql) {
		boolean rs = true;
		OpenConSql();
		Statement stm;
		try {

			stm = con.createStatement();
			stm.executeUpdate(sql);
			stm.close();
		} catch (Exception e) {
			rs = false;
		} finally {
			CloseConSql();
		}

		return rs;
	}

	public ArrayList<Object[]> Exe_Sql_nfield_nrow(String sql, int nfield) {

		OpenConSql();

		Statement stm = null;
		ResultSet rs = null;
		ArrayList<Object[]> list_arr = new ArrayList<Object[]>(); // 1 list cac
																	// obj[];
		Object[] obj_arr; // obj[] = 1 mang cac field(empsn,name,depsn...)
		try {
			stm = con.createStatement();

			rs = stm.executeQuery(sql);

			if (rs != null) {
				while (rs.next()) {
					obj_arr = new Object[nfield];
					for (int i = 0; i < nfield; i++) {
						if (rs.getObject(i + 1) == null) {
							obj_arr[i] = "0";
						} else {
							obj_arr[i] = rs.getObject(i + 1);
						}
					}
					if (obj_arr != null) {
						list_arr.add(obj_arr);
					}
				}
			} else {
				System.out.println("There is not data return");
			}
			stm.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseConSql();
		}
		return list_arr;
	}

	public Object Exe_Sql_Obj(String sql) {
		OpenConSql();
		Statement stm = null;
		ResultSet rs = null;
		// Object obj = new Object();
		Object obj = null;

		try {
			stm = con.createStatement();
			rs = stm.executeQuery(sql);
			if (rs != null) {
				while (rs.next()) {
					obj = rs.getObject(1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseConSql();
		}
		return obj;
	}

	/**
	 * Select 1 column -> return n row
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	// 27/12/2011 Ngan them
	public int Exe_Update_Sql(String sql) {
		OpenConSql();
		Statement stm = null;
		int rs = 0;

		try {
			stm = con.createStatement();
			rs = stm.executeUpdate(sql);

			stm.close();

		} catch (Exception e) {

		} finally {
			CloseConSql();
		}
		return rs;

	}

	public List<String> Exe_Sql_String(String sql) {
		OpenConSql();
		Statement stm = null;
		ResultSet rs = null;
		ArrayList<String> list = new ArrayList<String>();

		try {
			stm = con.createStatement();
			rs = stm.executeQuery(sql);

			if (rs != null) {
				while (rs.next()) {

					list.add(rs.getString(1));
				}

			} else {
				System.out.println("There is not data return!");
			}

			// System.out.println("Close Connection");

			stm.close();
			rs.close();

		} catch (Exception e) {

		} finally {
			CloseConSql();
		}
		return list;

	}

	/**
	 * Select n column -> return 1 row
	 * 
	 * @param obj
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> Exe_sql_nfield_1row(String sql, int nfield)
			throws SQLException {
		ArrayList<Object> list = new ArrayList<Object>();
		OpenConSql();
		Statement stm = null;
		ResultSet rs = null;

		try {
			stm = con.createStatement();
			rs = stm.executeQuery(sql);

			if (rs != null) {
				while (rs.next()) {
					for (int i = 1; i <= nfield; i++) {
						if (rs.getObject(i) != null) {
							list.add(rs.getObject(i));
						} else {
							list.add("");
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
			CloseConSql();
		}
		return list;
	}

	// _________________________________________________________________

	// ________________ Show Message

	public static void ShowMessageOK(String message) {
		Application.getApp().showMessageDialog(
				MessageDialog.TYPE_INFORMATION + MessageDialog.CONTROLS_OK,
				message);
	}

	public static void ShowMessageError(String message) {
		Application.getApp().showMessageDialog(
				MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK, message);
		return;
	}

	public static void ShowMessageInfo(String message) {
		Application.getApp().showMessageDialog(
				MessageDialog.TYPE_INFORMATION + MessageDialog.CONTROLS_OK,
				message);
	}

	// __________________________________________________________________

	// ________________ Report

	private int execExportType = 1;
	private String taskIDClass = "";
	private String _name_temp_field = "";

	// "Health_RTask", "KT_BH"
	// KT_BH tên file mẫu excel sẽ xuất.
	/**
	 * @param taskIDClass
	 *            idClass đăng ký trong file TaskIDClass.properties.
	 * @param name_temp_field_
	 *            tên file excel mẫu.
	 * @throws Exception
	 *             if taskIDClass don't exits in TaskIDClass.properties
	 */
	public void doExport(String taskIDClass, String name_temp_field_)
			throws Exception {

		this.setTaskIDClass(taskIDClass);

		this._name_temp_field = name_temp_field_;
		if (_name_temp_field.equals("")) {
			_name_temp_field = "temp";
		}
		// đối tượng chứa thông tin về fileExport
		// người xuất kí trình, ngày xuất.
		TaskInfoWrapper taskInfoWrapper = new TaskInfoWrapper();
		prepareExportTaskParameter(taskInfoWrapper);
		//
		export();

	}

	public String getTaskIDClass() {
		return taskIDClass;
	}

	/**
	 * @param taskIDClass
	 *            id đăng ký trong file TaskIDClass.properties.
	 * @throws Exception
	 *             if taskIDClass don't exits in TaskIDClass.properties
	 * @author Tuan Khoi.
	 */
	public void setTaskIDClass(String taskIDClass) throws Exception {
		Properties registedTask = new Properties();

		try {
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			registedTask.load(classLoader
					.getResourceAsStream("conf/TaskIDClass.properties"));
			if (!registedTask.containsKey(taskIDClass)) {
				// throw new ClassNotFoundException(s);
				throw new Exception(
						"Error to method doExport class ExportHealthProgram TaskIDClass "
								+ taskIDClass
								+ "don't exits in TaskIDClass.properties.");
			}
		} catch (IOException e) {
			throw new IOException(e);
		}

		this.taskIDClass = taskIDClass;
	}

	protected void prepareExportTaskParameter(TaskInfoWrapper wrapper) {

		ReportParameterWrapper reportWrapper = new ReportParameterWrapper();
		reportWrapper.setReportFormat(_name_temp_field + ".xls");
		reportWrapper.setReportType("excel");
		// http://localhost:8124/app?serviceId=ReportService&type=temp&key=
		reportWrapper.setViewerUrl(getViewerUrl());
		wrapper.setCategory("R");// <--報表
		setExportExecuteType(ATask.EXECTYPE_DIRECT);
		wrapper.setParameter(reportWrapper.createParameterDescription());
		wrapper.setTaskId(taskIDClass);

	}

	public void setExportExecuteType(int type) {
		this.execExportType = type;
	}

	/**
	 * TaskResultWrapper String s = task.execute();
	 * 
	 * @return
	 */
	protected boolean export() {
		// thực thi report dựa trên
		// Open Declaration String dsc.util.function.UUID.generate() sinh
		// filename tự động
		// thực thi kí trình taskResultWrapper =
		// taskMgr.executeTask(taskInfoWrapper, jobId null);
		TaskExecuter executer = new TaskExecuter(new TaskExecuterHelper() {
			public void executeSuccessfully(TaskInfoWrapper taskInfoWrapper,
					TaskResultWrapper taskResultWrapper) {
				Application.getApp().enqueueCommand(
						new BrowserRedirectCommand(getViewerUrl()
								+ taskResultWrapper.getResult())

				);
				System.out.println(taskInfoWrapper.getResult());
				System.out.println("===============print success!");
			}

			// D:\Khoi\projectKhoi\Fvhr.platform\out\WEB-INF\classes\conf\format\KT_BH11.xls
			// doesn't exist!
			public void executeUnsuccessfully(TaskInfoWrapper taskInfoWrapper,
					TaskResultWrapper taskResultWrapper) {
				// setErrorMessage(getCommMsgRes().getString("Generic.MSG.ExecFailure")
				// + taskResultWrapper.getResult());
				System.out.println(taskResultWrapper.getResult());
				System.out.println("===============print not success!");
			}

			public void prepareTaskParameter(TaskInfoWrapper taskInfoWrapper) {
				prepareExportTaskParameter(taskInfoWrapper);
			}
		});

		ResourceBundle resourceBundle = null;
		String err = "";
		// search execExportType
		err = executer.execute(getTaskIDClass(), "", "", execExportType, "",
				Application.getApp().getLoginInfo(), resourceBundle);

		// kiểm tra
		if (err != null && err.length() > 0)
			Application.getApp().showMessageDialog(1, err);
		return null == err;
	}

	private String getViewerUrl() {
		HttpServletRequest request = WebRenderServlet.getActiveConnection()
				.getRequest();
		String viewerUrl = request.getRequestURL()
				+ "?"
				+ WebRenderServlet.SERVICE_ID_PARAMETER
				+ "="
				+ ReportService.INSTANCE.getId()
				+ "&"
				+ ReportService.PARAM_TYPE
				+ "="
				+ (execExportType == ATask.EXECTYPE_DIRECT ? ReportService.TYPE_TEMP
						: ReportService.TYPE_OUTPUT) + "&"
				+ ReportService.PARAM_KEY + "=";
		return viewerUrl;
	}

	@SuppressWarnings("unused")
	private boolean Write_Excel_by_Row(List<Object> list_info,
			HSSFSheet sheet0, int i_row) {
		boolean rs = true;

		HSSFRow row;
		HSSFCell cell;

		row = sheet0.createRow(i_row);

		for (int j = 0; j < list_info.size(); j++) {
			cell = row.createCell(j);
			cell.setCellValue(list_info.get(j).toString());
		}

		return rs;

	}

	// __________________________________________________________________________________________
	// ___________________ MAPPING EDITOR
	// ____________________________________________
	// __________________________________________________________________________________________

	public MappingPropertyEditor Get_MapEditor_JOB_NAME() {
		MappingPropertyEditor editor_ = new MappingPropertyEditor();
		editor_.put("", "");
		String id__ = "";
		String name__ = "";
		String kind__ = "";
		String sql =

		"SELECT\n" + "      j.id_job,j.name,j.kind\n" + "FROM n_n_job j";

		List<Object[]> list_arr_ = this.Exe_Sql_nfield_nrow(sql, 3);
		if (list_arr_ != null) {
			for (Object[] arr : list_arr_) {
				id__ = arr[0] == null ? "" : arr[0].toString();
				name__ = arr[1] == null ? "" : arr[1].toString();
				kind__ = arr[2] == null ? "" : arr[2].toString();

				name__ = Vni2Uni.convertToUnicode(name__ + "-" + kind__);
				editor_.put(name__, id__);
			}
		}

		return editor_;
	}

	public MappingPropertyEditor Get_MapEditor_DEPSN_NAME() {
		MappingPropertyEditor editor_ = new MappingPropertyEditor();
		String id__ = "";
		String name__ = "";
		String sql = "SELECT\n" + "      d.id_dept,d.name_dept\n"
				+ "FROM n_department d";

		List<Object[]> list_arr_ = this.Exe_Sql_nfield_nrow(sql, 2);
		if (list_arr_ != null) {
			for (Object[] arr : list_arr_) {
				id__ = arr[0] == null ? "" : arr[0].toString();
				name__ = arr[1] == null ? "" : Vni2Uni.convertToUnicode(arr[1]
						.toString());
				editor_.put(name__, id__);
			}
		}

		return editor_;
	}

	public MappingPropertyEditor Get_MapEditor_EMPSN_NAME() {
		MappingPropertyEditor editor_ = new MappingPropertyEditor();
		String fname_ = "";
		String lname_ = "";
		String name_ = "";
		String empsn_ = "";
		String sql = " SELECT\n" + "      e.empsn,e.fname,e.lname\n"
				+ " FROM n_employee e " + " WHERE e.depsn <> '00000'"; // tam
																		// thoi
																		// cho
																		// load
																		// du
																		// lieu
																		// nhanh
		List<Object[]> list_arr_ = this.Exe_Sql_nfield_nrow(sql, 3);
		if (list_arr_ != null) {
			for (Object[] arr_ : list_arr_) {
				empsn_ = arr_[0] == null ? "" : arr_[0].toString();
				fname_ = arr_[1] == null ? "" : arr_[1].toString();
				lname_ = arr_[2] == null ? "" : arr_[2].toString();
				name_ = Vni2Uni.convertToUnicode(fname_ + " " + lname_);
				editor_.put(name_, empsn_);
			}
		}
		return editor_;
	}

	public MappingPropertyEditor Get_Editor_KIND() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		editor.put("", "");
		editor.put("A", "A");
		editor.put("B", "B");
		editor.put("C", "C");
		editor.put("D", "D");
		editor.put("E", "E");
		editor.put("F", "F");

		return editor;
	}

	public MappingPropertyEditor Get_Editor_ID_POSS() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		String sql = " select distinct t.id_poss from n_n_poss t";

		List<String> list = null;
		list = this.Exe_Sql_String(sql);
		editor.put("", "");
		if (list != null) {
			for (String str : list) {
				editor.put(str, str);
			}
		}
		return editor;
	}

	public MappingPropertyEditor Get_Editor_Level() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		editor.put("", "");
		editor.put("1", "1");
		editor.put("2", "2");
		editor.put("3", "3");
		editor.put("4", "4");
		editor.put("5", "5");
		editor.put("6", "6");
		editor.put("7", "7");
		editor.put("8", "8");
		editor.put("9", "9");
		editor.put("10", "10");
		editor.put("0", "0");
		return editor;
	}

	// ____________________________________________________________________________________________

	/*
	 * xBinder = new ListBaseBinder(null, cb_xuong, factEditor());
	 * xBinder.objectToComponent(""); cb_xuong.addActionListener(new
	 * ActionListener() {
	 * 
	 * 
	 * public void actionPerformed(ActionEvent e) { // TODO Auto-generated
	 * method stub xBinder = new ListBaseBinder(null, cb_nhom, factEditor());
	 * xBinder.objectToComponent("1"); } });
	 */

	// ____________NGAN ___

	// 03/04/2012 lay ds so the theo sthe or theo xuong,nhom,don vi --> nhap
	// phep(N_Registry_Rest_Form)_Ngan
	public ArrayList<String> getListEmpsn(String sothe, String fact,
			String group, String nameDept, String id_dept, String ma_user,
			String depsn_)// them string depsn tu truyen vao vi co form lay theo
							// depsn,co form lay theo depsn_temp
	{ // vd:dk phep thi lay depsn, dky tca thi lay theo depsn_temp(ly do:hoi
		// c.HA)
		String sql1 = "";
		String sql = "select  e.empsn\n" + "from n_employee e,n_department d\n"
				+ "where e." + depsn_ + " = d.id_dept";
		// " and d.name_fact = '"+fact+"'";
		if (!sothe.equals("")) {
			sql1 = " and e.empsn = '" + sothe + "'";
		} else if (!fact.equals("") && group.equals("") && nameDept.equals("")
				&& id_dept.equals("")) {
			sql1 = " and d.name_fact = '" + fact + "'";
		} else if (!fact.equals("") && !group.equals("") && nameDept.equals("")
				&& id_dept.equals("")) {
			sql1 = " and d.name_fact = '" + fact + "' and d.name_group = '"
					+ group + "'";
		} else if (!fact.equals("") && !group.equals("")
				&& !nameDept.equals("") && id_dept.equals("")) {
			sql1 = " and d.name_fact = '" + fact + "' and d.name_group = '"
					+ group + "' and d.name_dept_name ='" + nameDept + "'";
		} else if (fact.equals("") && group.equals("") && nameDept.equals("")
				&& !id_dept.equals("")) {
			sql1 = " and d.id_dept = '" + id_dept + "'";
		} else if (!fact.equals("") && !group.equals("")
				&& !nameDept.equals("") && !id_dept.equals("")) {
			sql1 = " and d.id_dept = '" + id_dept + "'";
		}

		String sql2 = " and e.user_manage_id in (SELECT ma_QL FROM n_user_limit WHERE ma_user= '"
				+ "" + ma_user + "' and ma_ql=user_manage_id)";

		sql = sql + sql1 + sql2;

		ArrayList<String> list_emp = (ArrayList<String>) this
				.Exe_Sql_String(sql);

		return list_emp;
	}

	// *18/05/2012 lay ds so the theo xuong nhom don vi or neu la FVL co chon
	// F1,F2...khac
	public ArrayList<String> getListEmpsn_R(String sothe, String fact,
			String group, String nameDept, String id_dept, String ma_user,
			String depsn_, RadioButton f1, RadioButton f2, RadioButton f3,
			RadioButton f5, RadioButton f6, RadioButton khac)// them string
																// depsn tu
																// truyen vao vi
																// co form lay
																// theo depsn,co
																// form lay theo
																// depsn_temp
	{ // vd:dk phep thi lay depsn, dky tca thi lay theo depsn_temp(ly do:hoi
		// c.HA)
		OBJ_EMPSN obj_e = new OBJ_EMPSN();
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String sql = "select  e.empsn\n" + "from n_employee e,n_department d\n"
				+ "where e." + depsn_ + " = d.id_dept";
		// " and d.name_fact = '"+fact+"'";
		if (!sothe.equals("")) {
			sql1 = " and e.empsn = '" + sothe + "'";
		} else if (!fact.equals("") && group.equals("") && nameDept.equals("")
				&& id_dept.equals("")) {
			sql1 = " and d.name_fact = '" + fact + "'";
		} else if (!fact.equals("") && !group.equals("") && nameDept.equals("")
				&& id_dept.equals("")) {
			sql1 = " and d.name_fact = '" + fact + "' and d.name_group = '"
					+ group + "'";
		} else if (!fact.equals("") && !group.equals("")
				&& !nameDept.equals("") && id_dept.equals("")) {
			sql1 = " and d.name_fact = '" + fact + "' and d.name_group = '"
					+ group + "' and d.name_dept_name ='" + nameDept + "'";
		} else if (fact.equals("") && group.equals("") && nameDept.equals("")
				&& !id_dept.equals("")) {
			sql1 = " and d.id_dept = '" + id_dept + "'";
		} else if (!fact.equals("") && !group.equals("")
				&& !nameDept.equals("") && !id_dept.equals("")) {
			sql1 = " and d.id_dept = '" + id_dept + "'";
		}

		// *Xuat theo chi tiet tung xuong neu chon f1,f2...khac(chi co xuong
		// FVL)
		sql2 = obj_e.check_fact_FVL_rb(f1, f2, f3, f5, f6, khac, "d");

		sql3 = " and e.user_manage_id in (SELECT ma_QL FROM n_user_limit WHERE ma_user= '"
				+ "" + ma_user + "' and ma_ql=user_manage_id)";

		sql = sql + sql1 + sql2 + sql3;

		ArrayList<String> list_emp = (ArrayList<String>) this
				.Exe_Sql_String(sql);

		return list_emp;
	}

	// * Begin 21/08/2012 lay dsach nhung so the trong n_emp_info theo xuong voi
	// advance = 1 de xet ung
	public ArrayList<String> getListEmpInfo(String ma_user, Date date_input) {
		String my_str = sf.format(date_input).substring(3, 10);
		String sql =

		"SELECT E.EMPSN\n"
				+ "           FROM N_EMP_INFO E\n"
				+
				// "          WHERE  E.ADVANCE = 1\n" +
				"          WHERE E.EMPSN IN (SELECT EM.EMPSN FROM N_EMPLOYEE EM,N_USER_LIMIT M\n"
				+ "                                   WHERE EM.EMPSN = E.EMPSN\n"
				+ "                                   AND EM.USER_MANAGE_ID = M.MA_QL\n"
				+ "                                   AND M.MA_USER = '"
				+ ma_user + "')\n"
				+ " AND E.EMPSN NOT IN (SELECT A.EMPSN FROM N_EMP_ADVANCE A \n"
				+ " 					  WHERE A.EMPSN = E.EMPSN \n"
				+ " 					  AND TO_CHAR(A.DATE_EFFECT,'MM/YYYY') = '" + my_str
				+ "')\n" +
				// "          AND E.EMPSN IN( '10060438')\n" +
				"";

		ArrayList<String> list_emp = (ArrayList<String>) this
				.Exe_Sql_String(sql);
		return list_emp;
	}

	// * End 21/08/2012 lay dsach nhung so the trong n_emp_info theo xuong voi
	// status = 1 de xet ung
	// * Begin 19/09/2012 viet rieng cho ke toan FVL chay tam ung(vi FVL chay ca
	// KDAO,TB019,TBBep)
	public ArrayList<String> getListEmpInfo_FVL_KDAO_TB019_TBBEP(String ma_user) {
		String sql =

		"SELECT E.EMPSN\n"
				+ "           FROM N_EMP_INFO E\n"
				+ "          WHERE  E.ADVANCE = 1\n"
				+ "          AND E.EMPSN IN (SELECT EM.EMPSN FROM N_EMPLOYEE EM,N_USER_LIMIT M\n"
				+ "                                   WHERE EM.EMPSN = E.EMPSN\n"
				+ "                                   AND EM.USER_MANAGE_ID = M.MA_QL\n"
				+ "                                   AND M.MA_USER = '"
				+ ma_user + "')";

		ArrayList<String> list_emp = (ArrayList<String>) this
				.Exe_Sql_String(sql);
		return list_emp;
	}

	// //* End 19/09/2012 viet rieng cho ke toan FVL chay tam ung(vi FVL chay ca
	// KDAO,TB019,TBBep)
	// * 21/08/2012 KIEM TRA DON VI DO THUOC XUONG NAO, CO THI TRA VE TRUE, K CO
	// TRA VE FALSE
	public boolean thuoc_xuong(String madv, String fact) {
		ExportHealthProgram obj_util = new ExportHealthProgram();
		String sql = "select count(*) from n_department d"
				+ " where d.name_fact='" + fact + "'  and d.id_dept='" + madv
				+ "'";// THEM DAU NGOAC O FACT VI NEU MA KE TOAN FVL CHAY TAM
						// UNG CHON FVL+KDAO+TB019_TBBEP THI THAY FACT = WHERE
						// (T.NAME_FACT = 'TB' OR (T.NAME_FACT = 'KDAO' OR
						// T.ID_DEPT IN ('TB019','00001','00002','00003')))
		BigDecimal c = (BigDecimal) obj_util.Exe_Sql_Obj(sql);
		return c.compareTo(BigDecimal.ZERO) > 0;
	}

	// *Begin 19/09/2012 Viet cho Ktoan tinh may don vi k thuoc xuong cua minh
	// chon
	public boolean thuoc_xuong_KToan_use(String madv, String dk) {
		ExportHealthProgram obj_util = new ExportHealthProgram();
		String sql = "select count(*) from n_department d " + dk
				+ "   and d.id_dept='" + madv + "'";// THEM DAU NGOAC O FACT VI
													// NEU MA KE TOAN FVL CHAY
													// TAM UNG CHON
													// FVL+KDAO+TB019_TBBEP THI
													// THAY FACT = WHERE
													// (T.NAME_FACT = 'TB' OR
													// (T.NAME_FACT = 'KDAO' OR
													// T.ID_DEPT IN
													// ('TB019','00001','00002','00003')))
		BigDecimal c = (BigDecimal) obj_util.Exe_Sql_Obj(sql);
		return c.compareTo(BigDecimal.ZERO) > 0;
	}

	// *End 19/09/2012 Viet cho Ktoan tinh may don vi k thuoc xuong cua minh
	// chon
	//
	public static Date DAY_NEXT(Date date_input) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date_input);
		ca.add(Calendar.DATE, 1);
		return ca.getTime();
	}

	// 05/04/2012 lay ds don vi theo so the,xuong, nhom,don vi
	public ArrayList<String> getListDept(String sothe, String fact,
			String group, String nameDept, String id_dept, String con_dept,
			String ma_user, RadioButton f1, RadioButton f2, RadioButton f3,
			RadioButton f5, RadioButton f6, RadioButton khac)// Neu id_dept thi
																// khong can xet
																// xuong,nhom,ten
																// don vi
	{
		OBJ_EMPSN obj_e = new OBJ_EMPSN();
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";

		String sql = "select DISTINCT d.id_dept\n"
				+ "from n_employee e,n_department d\n" + "where e." + con_dept
				+ " = d.id_dept";

		if (!sothe.equals("")) {
			sql1 = " and e.empsn = '" + sothe + "'";
		} else if (!fact.equals("") && group.equals("") && nameDept.equals("")
				&& id_dept.equals("")) {
			sql1 = " and d.name_fact = '" + fact + "'";
		} else if (!fact.equals("") && !group.equals("") && nameDept.equals("")
				&& id_dept.equals("")) {
			sql1 = " and d.name_fact = '" + fact + "' and d.name_group = '"
					+ group + "'";
		} else if (!fact.equals("") && !group.equals("")
				&& !nameDept.equals("") && id_dept.equals("")) {
			sql1 = " and d.name_fact = '" + fact + "' and d.name_group = '"
					+ group + "' and d.name_dept_name ='" + nameDept + "'";
		} else if (fact.equals("") && group.equals("") && nameDept.equals("")
				&& !id_dept.equals("")) {
			sql1 = " and d.id_dept = '" + id_dept + "'";
		} else if (!fact.equals("") && !group.equals("")
				&& !nameDept.equals("") && !id_dept.equals("")) {
			sql1 = " and d.id_dept = '" + id_dept + "'";
		}

		// *Xuat theo chi tiet tung xuong neu chon f1,f2...khac(chi co xuong
		// FVL)
		sql2 = obj_e.check_fact_FVL_rb(f1, f2, f3, f5, f6, khac, "d");

		sql3 = " and e.user_manage_id in (SELECT ma_QL FROM n_user_limit WHERE ma_user= '"
				+ "" + ma_user + "' and ma_ql=user_manage_id)";

		sql = sql + sql1 + sql2 + sql3;

		ArrayList<String> list_dept = (ArrayList<String>) this
				.Exe_Sql_String(sql);

		return list_dept;
	}

	// 14/05/2012 lay ds tat ca cac ngay nhap vao khong phan biet chu nhat
	public ArrayList<Date> getAllListDate_input(Date dateFrom, Date dateTo) {
		ArrayList<Date> listDate = new ArrayList<Date>();

		listDate.add(dateFrom);

		dateFrom = ExportHealthProgram.DAY_NEXT(dateFrom);

		while (dateFrom.before(dateTo) || dateFrom.equals(dateTo)) {
			listDate.add(dateFrom);
			dateFrom = ExportHealthProgram.DAY_NEXT(dateFrom);
		}
		return listDate;
	}

	// _______________________________________________END NGAN______________

}