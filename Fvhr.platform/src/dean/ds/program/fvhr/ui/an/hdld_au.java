package ds.program.fvhr.ui.an;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import java.io.File;
import javax.servlet.http.HttpServletRequest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatternFormatting;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;

import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Table;
import nextapp.echo2.app.WindowPane;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscValuedCheckBox;
import dsc.echo2app.component.table.PageableSortableTableModel;

//import ds.program.yte.ext.ReportDataObject;

import fv.components.SelectItem;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.Navigation;
import fv.util.Vni2Uni;
import fv.util.library;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.table.DefaultTableColumnModel;
import nextapp.echo2.app.table.TableCellRenderer;
import nextapp.echo2.app.table.TableColumnModel;
import dsc.echo2app.component.table.DscPageableSortableTable;
import echopointng.table.SortableTableColumn;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.echo2app.component.DscGroupRadioButton;
import dsc.util.function.UUID;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.layout.TableLayoutData;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;

import dsc.echo2app.component.DscField;
import dsc.echo2app.component.event.FocusEvent;
import dsc.echo2app.component.event.FocusListener;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;
import echopointng.GroupBox;

public class hdld_au extends WindowPane {

	private ResourceBundle resourceBundle;
	// private MrBeanBrowserContent browser;
	private SplitPane splitPane1;
	Grid grid1;
	private SplitPane splitPane3;
	private DscValuedCheckBox CheckBox1;

	private ArrayList<String> lstChkSelect;

	String mang[] = new String[20];
	int tui = 0;
	String chuoi = "";

	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	DefaultListModel xuong_model, loai_model;
	Connection con = null;
	ResultSet rs = null;
	Statement st = null;
	private String dateend, lan = "", sothe = "";
	float luongcu = 0, luongmoi = 0;
	library l = new library();
	Vni2Uni c = new Vni2Uni();
	String db1, db2, dkt1, dkt2, NgayKyHD, loaiHD;
	PageableSortableTableModel model;
	DscField a;

	lamtron lt = new lamtron();
	DSPB02 u;
	private nLabourMProgram _father_form;
	int cc = 0;

	int ischeck = 0;
	int pos1 = -1;
	int pos2 = -1;
	int pos3 = -1;
	String name = "";
	String note = "";
	int pos_past = -1;

	String stt = "";
	String nam = "";
	String thang = "";
	String nc = "";
	int name_g = 0;
	int crp = -1;
	String[][] mang2;
	private Label lbl_info;

	/**
	 * Creates a new <code>hdld_au</code>.
	 */
	public hdld_au(nLabourMProgram _main) {
		super();
		_father_form = _main;
		// Add design-time configured components.
		initComponents();
		TableColumnModel columnModel = loadColumnModel();
		Table1.setColumnModel(columnModel);
		final PageableSortableTableModel model = new PageableSortableTableModel(columnModel);
		model.setSelectionModel(Table1.getSelectionModel());
		model.setRowsPerPage(20);
		Table1.setModel(model);
		Table1.setSelectionEnabled(true);
		Table1.setInsets(new Insets(0));
		lstChkSelect = new ArrayList();
		navi.setTable(Table1);
		navi.setBackground(new Color(0xafd8d8));

		this.setHeight(new Extent(Application.getApp().getScreenHeight()));
		this.setWidth(new Extent(Application.getApp().getScreenWidth()));
		this.setTitle("Ký hợp đồng lao động....");
		a = new DscField();
		setdefault();
		lbl_info = new Label();
		lbl_info.setText("");
		lbl_info.setForeground(Color.RED);
		RowLayoutData infoLayout1 = new RowLayoutData();
		infoLayout1.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		infoLayout1.setWidth(new Extent(50, Extent.PERCENT));
		lbl_info.setLayoutData(infoLayout1);

		btn_dsky.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if ((!dscNgayky.getText().substring(0, 2).equals("01"))
						&& (!dscNgayky.getText().substring(0, 2).equals("15"))) {
					Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
							"NGÀY KÝ HĐ KHÔNG HỢP LỆ.");
					return;
				}
				if (sf_xuong.getSelectedIndex() == -1) {
					Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
							"Chọn xưởng cần lấy dl...");
					return;
				}
				// do export
				try {
					doExportDataObjectSet();
				} catch (IOException ex) {
					// TODO: handle exception
					Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
							"LỔI KHÔNG THỂ XUẤT DL RA EXCEL..");
				}

			}
		});

		btn_print.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if ((!dscNgayky.getText().substring(0, 2).equals("01"))
						&& (!dscNgayky.getText().substring(0, 2).equals("15"))) {
					Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
							"NGÀY KÝ HĐ KHÔNG HỢP LỆ.");
					return;
				}
				if (sf_xuong.getSelectedIndex() == -1 && dsc_sothe.getText().equals("")) {
					Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
							"Chọn xưởng cần lấy dl...");
					return;
				}
				if (rad_all_in.isSelected() == false && rad_mau.isSelected() == false) {
					l.ShowMessageOK("Vui lòng chọn kiểu in.");
					return;
				}
				if (rad_all_trang.isSelected() == false && rad_page1.isSelected() == false
						&& rad_page2.isSelected() == false && rad_page3.isSelected() == false) {
					l.ShowMessageOK("Vui lòng chọn in từng trang hay in tất cả.");
					return;
				}
				doPrint();
			}
		});

		sf_xuong.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sfFactChanged(e);
			}
		});
		sf_nhom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sfgroupChanged(e);
			}
		});
	}

	public void setdefault() {

		dscNgayky.setDateFormat(df);
		dscNgayky.setSelectedDate(Calendar.getInstance(Locale.ENGLISH));
		dscNgayky.getDateChooser().setMonthNameLength(30);
		IGenericDAO<DSPB02, String> udao = Application.getApp().getDao(DSPB02.class);
		u = udao.findById(Application.getApp().getLoginInfo().getUserID());

		ListBinder.bindSelectField(sf_xuong, FVGenericInfo.getFactories(), true);
		ListBinder.bindSelectField(sf_loaiHD, l.bindabourkind(), true);
	}

	private DscDateField dscNgayky;
	private SelectField sf_nhom;
	private SelectField sf_xuong;
	private SelectField sf_loaiHD;
	private SelectField sf_donvi;
	private DscField dsc_sothe;
	private Label label10;
	private Label label2;
	private Label label5;
	private Label label7;
	private Label label6;
	private Button btn_show;
	private Label label1;
	private Label label8;
	private Button btn_tk;
	private Button btn_ky;
	private Button btn_print;
	private Button btn_dschuaky;
	private Button btn_dsky;
	private Button btn_reset;
	private Button btn_hide;
	private Label label14;
	private Label label15;
	private Label label16;
	private Label label17;
	private Label label18;
	private Label label20;
	private Row row4;
	private Label label21;
	private Label label12;
	private DscGroupRadioButton dscGroupRadioButton1;
	private RadioButton r_hd1;
	private RadioButton r_hd2;
	private RadioButton r_hd3;
	private Label label23;
	private GroupBox groupBox3;
	private GroupBox groupBox1;
	private GroupBox groupBox2;
	private Label label22;
	private RadioButton rad_mau;
	private RadioButton rad_all_in;
	private RadioButton rad_page1;
	private RadioButton rad_page2;
	private RadioButton rad_page3;
	private RadioButton rad_all_trang;

	private DscPageableSortableTable Table1;
	private Navigation navi;

	// pa me code xong nhin lai muon no con mat ma chang hieu j ca.
	// chi co viec sua dl truc tiep tren browse va may cai checkbox ma lam cho cai
	// form loan cao cao len.hix
	private TableColumnModel loadColumnModel() {
		TableColumnModel columnModel = new DefaultTableColumnModel();
		SortableTableColumn column1 = null;
		for (int i = 0; i < 16; i++) {
			column1 = new SortableTableColumn(i);
			column1.setHeaderRenderer(Table1.getDefaultHeaderRenderer());
			column1.setComparator(l.INT_COMPARATOR);
			column1.setModelIndex(i);
			column1.setHeaderValue(getColumnHeader()[i]);
			columnModel.addColumn(column1);
			if (i == 1) {
				column1.setHeaderRenderer(new TableCellRenderer() {

					@Override
					public nextapp.echo2.app.Component getTableCellRendererComponent(Table table, Object value,
							int column, int row) {
						// TODO Auto-generated method stub
						TableLayoutData lay = new TableLayoutData();
						lay.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
						final CheckBox chk = new CheckBox();
						if (column == 1) {

							chk.setLayoutData(lay);
							chk.setText("Y/N");
							chk.setForeground(Color.WHITE);
							chk.setBackground(new Color(0x0080C0));
							chk.setStatePosition(new Alignment(Alignment.DEFAULT, Alignment.BOTTOM));
							chk.setStateAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));

							// chk.setInsets(new Insets(0,12,0,12));
							chk.setId((String) value);

							if (cc == 1) // de gan check all cho tat ca cac trang
							{
								chk.setSelected(true);
							}
							chk.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									if (Table1.getModel().getRowCount() > 0) {
										if (chk.isSelected()) {
											cc = 1;

											for (int j = 0; j < model.getRowCount(); j++) {
												CheckBox a = (CheckBox) Table1.getCellComponent(1, j);
												a.setSelected(true);

											}
											int t = model.getTotalRows();
											for (int u = 0; u < t; u++) {
												chuoi += u + "-"; // gan gia tri vao chuoi k su dung list
																	// de su dung cho ham tim kiem ben duoi
											}
										} else {
											for (int j = 0; j < model.getRowCount(); j++) {
												CheckBox a = (CheckBox) Table1.getCellComponent(1, j);
												a.setSelected(false);
												chuoi = "";
												cc = 0;
											}
										}
									}
								}
							});

							return chk;
						}

						Label lbl = new Label();

						// lbl.setText(column1.getHeaderValue().toString());
						lbl.setForeground(Color.WHITE);
						TableLayoutData layout = new TableLayoutData();
						layout.setBackground(new Color(0x0080CC));
						// layout.setInsets(new Insets(3));
						lbl.setLayoutData(layout);
						return lbl;

					}
				});

//check box
				Table1.setDefaultRenderer(Object.class, new TableCellRenderer() {

					@Override
					public nextapp.echo2.app.Component getTableCellRendererComponent(Table table, Object value,
							int column, int row) {
						// TODO Auto-generated method stub

						ischeck = 0;
						pos1 = -1;
						pos2 = -1;
						pos3 = -1;

						TableLayoutData lo = new TableLayoutData();
						final CheckBox chk = new CheckBox();

						navi.add(lbl_info);
						if (column == 1) {
							if (chuoi.length() > 0) {
								// tim kiem trong chuoi nhung gia tri nao co thi check
								// vi khi chuyen trang nhung dong duoc check se bi reset
								if (timkiem(Integer.parseInt(value.toString()), chuoi) == true) {
									chk.setSelected(true);
								} else {
									chk.setSelected(false);
								}
							} else {
								chk.setId("" + value);
							}

							lo.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
							if (row % 2 == 0) {
								lo.setBackground(new Color(0xffffdd));
							}
							chk.setLayoutData(lo);
							chk.setId("" + value);
							chk.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									if (chk.isSelected()) {
										chuoi += chk.getId() + "-";
										mang = chuoi.split("-");
									} else {
										mang = chuoi.split("-");
										mang = remove(mang, chk.getId());
										chuoi = "";
										for (int i = 0; i < mang.length; i++) {
											chuoi += mang[i] + "-";
										}
									}
								}
							});
							return chk;
						}
						Label lbl = new Label();
						if (value != null) {
							lbl.setText(value.toString());
						}
						TableLayoutData layout = new TableLayoutData();
						if (row % 2 == 0) {

							layout.setBackground(new Color(0xffffdd));
							lbl.setLayoutData(layout);
						}
						if (column == 9) {
							// TableLayoutData layout1=new TableLayoutData();
							layout.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
							lbl.setLayoutData(layout);
						}
						if (column == 0) {
							TableLayoutData tld = new TableLayoutData();
							tld.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
							tld.setBackground(Color.LIGHTGRAY);
							lbl.setLayoutData(tld);
						}
						return lbl;

					}
				});
			}

		}

		return columnModel;
	}

	public String[] remove(String[] arr, String para) {
		int position = 0;
		String[] arr_temp = new String[arr.length - 1];
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].toString().compareTo(para) == 0) {
				position = i;
				break;
			}
		}

		if (position == 0) {
			arr_temp = new String[arr.length - 1];
			for (int i = 0; i < arr.length - 1; i++) {
				arr_temp[i] = arr[i + 1];
			}
			arr = new String[arr_temp.length];
			for (int i = 0; i < arr_temp.length; i++) {
				arr[i] = arr_temp[i];
			}
		}
		if ((position == arr.length - 1) && (arr.length != 1)) {
			arr_temp = new String[arr.length - 1];
			for (int i = 0; i < arr.length - 1; i++) {
				arr_temp[i] = arr[i];
			}
			arr = new String[arr_temp.length];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = arr_temp[i];
			}
		}

		if ((position > 0) && (position < arr.length - 1) && (arr.length != 2)) {
			arr_temp = new String[arr.length - 1];
			for (int i = position; i < arr.length - 1; i++) {
				arr[i] = arr[i + 1];
			}
			for (int i = 0; i < arr.length - 1; i++) {
				arr_temp[i] = arr[i];
			}
			arr = new String[arr_temp.length];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = arr_temp[i];
			}
		}
		return arr_temp;
	}

	public boolean timkiem(int a, String tt) {
		boolean f = false;
		mang = tt.split("-");
		for (int i = 0; i < mang.length; i++) {
			if (Integer.parseInt(mang[i].toString()) == a) {
				f = true;
				break;
			}
		}
		return f;
	}

	private String[] getColumnHeader() {
		return new String[] { "STT", "YES/NO", "HỌ TÊN", "SỐ THẺ", "CHỨC VỤ", "ĐƠN VỊ", "XƯỞNG", "NHÓM", "NGÀY KÝ",
				"LẦN KÝ", "NGÀY HẾT HẠN", "MÃ HĐ", "LƯƠNG CŨ", "LƯƠNG MỚI", "KỶ LUẬT", "TRÌNH ĐỘ" };

	}

	public void loaddata() {

		int tuilaai = 0;
		String sql = "", temp = "", temp1, temp2, poss = "", empsn = "";
		con = Application.getApp().getConnection();
		temp = l.whereclause_id_dept(dsc_sothe.getText(), sf_xuong.getSelectedItem(), sf_nhom.getSelectedItem(),
				ListBinder.get(sf_donvi));
		if (!lan.equals("2")) {
			int nam = Integer.parseInt(dscNgayky.getText().substring(6, 10)) + 1;
			// dateend=dscNgayky.getText().toString().substring(0,
			// 6)+Integer.parseInt(dscNgayky.getText().toString().substring(6, 10))+1;
			dateend = dscNgayky.getText().toString().substring(0, 6) + nam;
		} else {
			dateend = dscNgayky.getText().toString().substring(0, 6) + "2100";
		}

		if (!r_hd1.isSelected()) {
			sql = "SELECT a.fname||' '||a.lname as fname, a.empsn ,a.position,dt.name_dept_name,dt.name_fact,dt.name_group ,"
					+ " '" + dscNgayky.getText().toString() + "' DATE_S,'" + dateend
					+ "' Expire,max(b.times)+1 as Times, b.id_labour,c.bsalary,d.e_group"
					+ " FROM n_Employee a, n_Labour b , n_Basic_Salary c,n_User_Limit f,n_department dt,N_EDUCATION_new D"
					+ " " + temp + "" + "and a.education=d.id_education  and a.empsn=b.empsn and b.clock=1"
					+ " and a.empsn=c.empsn and keys=1 and b.expire = to_date('" + dscNgayky.getText().toString()
					+ "','dd/MM/yyyy') and a.depsn<>'00000' and b.limit<>'00000'"
					+ " and c.keys=1 and a.User_Manage_Id=f.ma_ql AND f.ma_user='" + u.getPB_USERNO()
					+ "' and b.times='" + lan + "'"
					+ " Group by a.fname||' '||a.lname , a.empsn ,a.position,dt.name_dept_name,dt.name_fact,dt.name_group ,b.id_labour,"
					+ " b.date_s,b.expire,c.bsalary,d.e_group";
		} else {
			getNNX(dscNgayky.getText(), 1);
			if ((!db2.equals("")) && (!dkt2.equals(""))) {
				temp1 = " and (a.date_hired between to_date('" + db1 + "','dd/mm/yyyy')and to_date('" + dkt1
						+ "','dd/mm/yyyy')";
			} else {
				temp1 = " and ( a.date_hired between to_date('" + db1 + "','dd/mm/yyyy') and to_date('" + dkt1
						+ "','dd/mm/yyyy')";
				temp1 = temp1 + " and a.date_hired>=to_date('01/12/2011','dd/mm/yyyy')";
				temp1 = temp1 + ")";
			}

			getNNX(dscNgayky.getText(), 2);
			if ((!db2.equals("")) && (!dkt2.equals(""))) {
				temp2 = " and (a.date_hired between to_date('" + db2 + "','dd/mm/yyyy')and to_date('" + dkt1
						+ "','dd/mm/yyyy')";
			} else {
				temp2 = " and ( a.date_hired between to_date('" + db1 + "','dd/mm/yyyy') and to_date('" + dkt1
						+ "','dd/mm/yyyy')";
				temp2 = temp2 + " and a.date_hired>=to_date('01/12/2011','dd/mm/yyyy')";
				temp2 = temp2 + ")";
			}

			sql = " SELECT a.fname||' '||a.lname as fname, a.empsn ,a.position,dt.name_dept_name,dt.name_fact,dt.name_group,  "
					+ " '" + dscNgayky.getText() + "' DATE_S, '" + dateend
					+ "' Expire,1 as Times,b.empsn||'01' as id_labour, " +
					// bsalary
					" bsaly_by_date(a.empsn,to_date('" + dscNgayky.getText() + "','dd/mm/yyyy')) bsalary, D.E_GROUP "
					+ " FROM n_Employee a, n_newworker_test b,n_basic_salary c,n_User_Limit f,n_department dt,n_education_new d"
					+ " " + temp + "" + " " + temp1 + "" + " and d.e_group in ('KBIET','" + c.convertToVNI("CẤP 1")
					+ "','" + c.convertToVNI("CẤP 2") + "','" + c.convertToVNI("CẤP 3") + "',"
					+ " 'TC') and a.education=d.id_education and a.empsn=b.empsn and b.is_test='1' and a.empsn=c.empsn"
					+ " and c.keys='1' and a.User_Manage_Id=f.ma_ql AND f.ma_user='" + u.getPB_USERNO() + "'"
					+ " and b.empsn not in (select l.empsn from n_labour l where l.clock='1') and b.dd_khu='0'" +

					"  Union "
					+ "SELECT a.fname||' '||a.lname as fname, a.empsn ,a.position,dt.name_dept_name,dt.name_fact,dt.name_group,  "
					+ " '" + dscNgayky.getText() + "' DATE_S, '" + dateend
					+ "' Expire,1 as Times,b.empsn||'01' as id_labour, " +
					// bsalary
					" bsaly_by_date(a.empsn,to_date('" + dscNgayky.getText() + "','dd/mm/yyyy')) bsalary, D.E_GROUP "
					+ " FROM n_Employee a, n_newworker_test b,n_basic_salary c,n_User_Limit f,n_department dt,n_education_new d"
					+ " " + temp + "" + " " + temp2 + "" + " and d.e_group in ('" + c.convertToVNI("CĐ") + "','"
					+ c.convertToVNI("ĐH") + "','" + c.convertToVNI("SĐH") + "')"
					+ " and a.education=d.id_education and a.empsn=b.empsn and b.is_test='1' and a.empsn=c.empsn"
					+ " and c.keys='1' and a.User_Manage_Id=f.ma_ql AND f.ma_user='" + u.getPB_USERNO() + "'"
					+ " and b.empsn not in (select l.empsn from n_labour l where l.clock='1') and b.dd_khu='0'" +

					"  Union "
					+ "SELECT a.fname||' '||a.lname as fname, a.empsn ,a.position,dt.name_dept_name,dt.name_fact,dt.name_group,  "
					+ " '" + dscNgayky.getText() + "' DATE_S, '" + dateend
					+ "' Expire,1 as Times,b.empsn||'01' as id_labour, " +
					// bsalary
					" bsaly_by_date(a.empsn,to_date('" + dscNgayky.getText() + "','dd/mm/yyyy')) bsalary, D.E_GROUP "
					+ " FROM n_Employee a, n_newworker_test b,n_basic_salary c,n_User_Limit f,n_department dt,n_education_new d"
					+ " " + temp + " "
					+ " and a.education=d.id_education and a.empsn=b.empsn and b.is_test='1' and a.empsn=c.empsn"
					+ " and c.keys='1' and a.User_Manage_Id=f.ma_ql AND f.ma_user='" + u.getPB_USERNO() + "'"
					+ " and b.empsn not in (select l.empsn from n_labour l where l.clock='1') and b.dd_khu='1' and a.ngaynx_moi=to_date('"
					+ dscNgayky.getText() + "','dd/mm/yyyy')";
		}

		model = (PageableSortableTableModel) Table1.getModel();
		model.clear();
		int i = 0;
		try {
			st = con.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY);
			rs = st.executeQuery(sql);
			if (rs.next() == false) {
				l.ShowMessageOK("Không có dữ liệu.");

				model.clear();

				lbl_info.setText("");

				navi.reset();
				return;
			}

			else {

				mang2 = new String[rs.getRow()][15];
				tuilaai = rs.getRow();

				rs.last();

				lbl_info.setText("Có " + rs.getRow() + " hợp đồng");

				rs.beforeFirst();
				while (rs.next()) {
					model.setValueAt(i + 1, 0, i);
					model.setValueAt(i, 1, i);
					model.setValueAt(c.convertToUnicode(rs.getString(1)), 2, i);
					model.setValueAt(rs.getString(2), 3, i);
					empsn = rs.getString(2);
					model.setValueAt(c.convertToUnicode(rs.getString(3)), 4, i);
					poss = c.convertToUnicode(rs.getString(3));
					model.setValueAt(c.convertToUnicode(rs.getString(4)), 5, i);
					model.setValueAt(rs.getString(5), 6, i);
					model.setValueAt(c.convertToUnicode(rs.getString(6)), 7, i);
					model.setValueAt(rs.getString(7), 8, i);
					model.setValueAt(rs.getString(9), 9, i);
					model.setValueAt(rs.getString(8), 10, i);

					String mahd = empsn + "0" + rs.getString(9);
					model.setValueAt(mahd, 11, i);// ma HD
					model.setValueAt(rs.getString(11), 12, i);// luong cu

					boolean kl = l.kyluat(rs.getString(2), rs.getString(7));
					luongcu = rs.getFloat("bsalary");
					// luongmoi=luongcu+luongcu*5/100;
					String poss_n = l.getfiled("n_newworker_test", "empsn", empsn, "bonus_nghe");
					String dd_khu = l.getfiled("n_newworker_test", "empsn", empsn, "dd_khu");
					String del_status = l.getfiled("n_newworker_test", "empsn", empsn, "del_status");
					if (del_status == null) {
						del_status = "0";
					}
					if (r_hd1.isSelected()) {
						if ((poss.equals("CN")) && (poss_n.equals("0")) && (dd_khu.equals("0"))
								&& (del_status.equals("1"))) {
							luongmoi = luongcu;
						} else if ((poss.equals("CN")) && (poss_n.equals("0")) && (dd_khu.equals("0"))) {
							luongmoi = Float.parseFloat(l.getfiled("N_INCSALARYBASIC", "ID_YEAR", "0", "SALARY_PER"));
						} else if ((poss.equals("CN")) && (poss_n.equals("100000"))) {
							float a = Float.parseFloat(l.getfiled("N_INCSALARYBASIC", "ID_YEAR", "0", "SALARY_PER"))
									+ Float.parseFloat(poss_n);
							luongmoi = a;
						} else if ((poss.equals("CN")) && (poss_n.equals("0")) && (dd_khu.equals("1"))) {
							luongmoi = luongcu;
						} else {
							String sal_kind = l.getfiled("n_newworker_test", "empsn", empsn, "sal_status");
							if (sal_kind.equals("")) {
								sal_kind = "100";
							}
							if (sal_kind.equals("85")) {
								luongmoi = luongcu + (luongcu * 15 / 85);
							} else {
								luongmoi = luongcu;
							}
						}
					} else
						luongmoi = luongcu + luongcu * 5 / 100;
					luongmoi = lt.testLamTron(luongmoi);
					if (kl) {
						model.setValueAt(luongcu, 12, i);
						model.setValueAt("Ngừng nâng lương", 14, i);
					} else {

						model.setValueAt(luongmoi, 13, i);
						model.setValueAt("", 14, i);

					}

					model.setValueAt(c.convertToUnicode(rs.getString(12)), 15, i);
					// model.setValueAt("", 14, i);
					i++;

				}

				model.setCurrentPage(0);

				navi.reset();
				// navi.add(lbl_info);

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
		// mang2=new String[model.getTotalRows()][14];

		/*
		 * for(int ii=0;ii<model.getTotalRows();ii++) {
		 * mang2[ii][0]=model.getValueAt(1,ii).toString();
		 * mang2[ii][1]=model.getValueAt(2,ii).toString();
		 * mang2[ii][2]=model.getValueAt(3,ii).toString();
		 * mang2[ii][3]=model.getValueAt(4,ii).toString();
		 * mang2[ii][4]=model.getValueAt(5,ii).toString();
		 * mang2[ii][5]=model.getValueAt(6,ii).toString();
		 * mang2[ii][6]=model.getValueAt(7,ii).toString();
		 * mang2[ii][7]=model.getValueAt(8,ii).toString();
		 * mang2[ii][8]=model.getValueAt(9,ii).toString();
		 * mang2[ii][9]=model.getValueAt(10,ii).toString();
		 * mang2[ii][10]=model.getValueAt(11,ii).toString();
		 * mang2[ii][11]=model.getValueAt(12,ii).toString();
		 * mang2[ii][12]=model.getValueAt(13,ii).toString();
		 * mang2[ii][13]=model.getValueAt(14,ii).toString(); }
		 */
	}

	public void thongbao(String tt) {
		Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK, " " + tt);
	}

	private void hdl2(ActionEvent e) {
		// TODO Implement.
		// dateend=dscNgayky.getText().toString().substring(0,
		// 6)+Integer.parseInt(dscNgayky.getText().toString().substring(6, 10))+1;
		lan = "1";
		ListBinder.refreshIndex(sf_loaiHD, "00001");
		sf_loaiHD.setEnabled(true);
	}

	private void hdl3(ActionEvent e) {
		// TODO Implement.
		// dateend=dscNgayky.getText().toString().substring(0, 6)+"2100";
		lan = "2";
		ListBinder.refreshIndex(sf_loaiHD, "00000");
		sf_loaiHD.setEnabled(false);
	}

	private void getdata(ActionEvent e) {
		// TODO Implement.
		if (sf_xuong.getSelectedIndex() == -1 && dsc_sothe.getText().equals("")) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Chưa nhập đầy đủ thông tin.");
			return;
		}
		if ((!dscNgayky.getText().substring(0, 2).equals("01"))
				&& (!dscNgayky.getText().substring(0, 2).equals("15"))) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Ngày ký hợp đồng không hợp lệ.");
			return;
		}
		if ((r_hd1.isSelected() == false) && (r_hd2.isSelected() == false) && (r_hd3.isSelected() == false)) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Chọn thời gian HĐ...");
			return;
		}

		IGenericDAO<DSPB02, String> udao = Application.getApp().getDao(DSPB02.class);
		DSPB02 u = udao.findById(Application.getApp().getLoginInfo().getUserID());
		boolean ql = false;
		if (dsc_sothe.getText().equals("")) {
			ql = l.check_QL("", sf_xuong.getSelectedItem().toString(), "", "", "", u.getPB_USERNO());
		} else {
			if (!l.check_data(dsc_sothe.getText())) {
				return;
			}
			ql = l.check_QL(dsc_sothe.getText().toString(), "", "", "", "", u.getPB_USERNO());

		}
		if (ql == false) {
			return;
		} else {
			loaddata();
		}
	}

	private void doExportDataObjectSet2() throws IOException {
		String str = "";
		StopWatch sw = new StopWatch();
		sw.start();
		int rows = 0;

		File f = ReportFileManager.getReportFormatFolder("ex");
		InputStream in = new FileInputStream(new File(f.getPath(), "excel.xls"));
		HSSFWorkbook wb = new HSSFWorkbook(in);
		int m = 1;// row 1
		int n = 1;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;
		HSSFFont font = wb.createFont();
		HSSFCellStyle style = wb.createCellStyle();
		// font.setFontName("Arial");
		font.setColor(HSSFColor.RED.index);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 15);
		style.setFont(font);

		Object obj;
		PageableSortableTableModel model2 = (PageableSortableTableModel) Table1.getModel();
		rows = model2.getTotalRows();

		row = sheet.createRow(0);
		// sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));
		cell = row.createCell(0);
		cell.setCellValue("HỌ TÊN");
		cell = row.createCell(1);
		cell.setCellValue("SỐ THẺ");
		cell = row.createCell(2);
		cell.setCellValue("CHỨC VỤ");
		cell = row.createCell(3);
		cell.setCellValue("ĐƠN VỊ");
		cell = row.createCell(4);
		cell.setCellValue("XƯỞNG");
		cell = row.createCell(5);
		cell.setCellValue("NHÓM");
		cell = row.createCell(6);
		cell.setCellValue("NGÀY KÝ");
		cell = row.createCell(7);
		cell.setCellValue("NGÀY HẾT HẠN");
		cell = row.createCell(8);
		cell.setCellValue("LẦN KÝ");
		cell = row.createCell(9);
		cell.setCellValue("MÃ HĐ");
		cell = row.createCell(10);
		cell.setCellValue("LƯƠNG CŨ");
		cell = row.createCell(11);
		cell.setCellValue("LƯƠNG MỚI");
		cell = row.createCell(12);
		cell.setCellValue("KỶ LUẬT");
		cell = row.createCell(13);
		cell.setCellValue("TRÌNH ĐỘ");

		for (int i = 0; i < rows; i++) {
			m++;
			n++;
			PageableSortableTableModel model = (PageableSortableTableModel) Table1.getModel();
			model.setCurrentPage(0);
			row = sheet.createRow(m);
			cell = row.createCell(0);
			cell.setCellValue(model.getValueAt(2, i).toString());// HO TEN
			cell = row.createCell(1);
			cell.setCellValue(model.getValueAt(3, i).toString()); // SO THE
			cell = row.createCell(2);
			cell.setCellValue(model.getValueAt(4, i).toString()); // CHUC VU
			cell = row.createCell(3);
			cell.setCellValue(model.getValueAt(5, i).toString()); // DON VI
			cell = row.createCell(4);
			cell.setCellValue(model.getValueAt(6, i).toString()); // XUONG
			cell = row.createCell(5);
			cell.setCellValue(model.getValueAt(7, i).toString()); // NHOM
			cell = row.createCell(6);
			cell.setCellValue(model.getValueAt(8, i).toString()); // NGAY KY
			cell = row.createCell(7);
			cell.setCellValue(model.getValueAt(10, i).toString()); // NGAY KT
			cell = row.createCell(8);
			cell.setCellValue(model.getValueAt(9, i).toString()); // LAN KY
			cell = row.createCell(9);
			cell.setCellValue(model.getValueAt(11, i).toString()); // MA HD
			cell = row.createCell(10);
			cell.setCellValue(model.getValueAt(12, i).toString()); // LCU
			cell = row.createCell(11);
			cell.setCellValue(model.getValueAt(13, i).toString()); // LM
			cell = row.createCell(12);
			cell.setCellValue(model.getValueAt(14, i).toString()); // KL
			cell = row.createCell(13);
			cell.setCellValue(model.getValueAt(15, i).toString()); // NGAY KT

		}
		for (int i = 0; i < 13; i++) {
			sheet.autoSizeColumn(i);
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

		saveFile = new File(f1.getParentFile(), URLEncoder.encode(Application.getApp().getLoginInfo().getUserID()
				+ ";application/vnd.ms-excel;" + "DSHDCHUAKY_" + dscNgayky.getText() + "_" + t + ".xls", "UTF-8"));
		ReportFileManager.saveTempReportFile(f1, saveFile);
		Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));

	}

	public void getNNX(String NgayKyHD, int tv) {
		int m, y, m_ao, m_nx, y_nx;

		Calendar c = Calendar.getInstance();
		m = Integer.parseInt(NgayKyHD.substring(3, 5));// thang ky HD
		y = Integer.parseInt(NgayKyHD.substring(6, 10));// nam ky HD
		m_ao = m - tv;
		if (m_ao <= 0) {
			m_nx = 12 + m_ao;
			y_nx = y - 1;
		} else {
			y_nx = y;
			m_nx = m_ao;
		}

		if (NgayKyHD.substring(0, 2).toString().equals("01")) {
			if (m_nx < 10) {
				db1 = "01/0" + m_nx + "/" + y_nx;
				dkt1 = "15/0" + m_nx + "/" + y_nx;
			} else {
				db1 = "01/" + m_nx + "/" + y_nx;
				dkt1 = "15/" + m_nx + "/" + y_nx;
			}

		} else {
			if (m_nx < 10) {
				db1 = "16/0" + m_nx + "/" + y_nx;
				c.set(Calendar.YEAR, c.get(Calendar.YEAR));
				c.set(Calendar.MONTH, m_nx - 1);
				int a = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				dkt1 = a + "/0" + m_nx + "/" + y_nx;
			} else {
				db1 = "16/" + m_nx + "/" + y_nx;
				c.set(Calendar.YEAR, c.get(Calendar.YEAR));
				c.set(Calendar.MONTH, m_nx - 1);
				int a = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				dkt1 = a + "/" + m_nx + "/" + y_nx;
			}
		}
		db2 = "";
		dkt2 = "";

	}

	private void hdl1(ActionEvent e) {
		// TODO Implement.
		lan = "0";
		ListBinder.refreshIndex(sf_loaiHD, "00001");
		sf_loaiHD.setEnabled(true);

	}

	private void kyhd(ActionEvent e) {

		// TODO Implement..
		String temp = "";
		String empsn, mhd, nk, tg, cv, dv, luong, gc, cviec, kl;

		boolean acclock = l.acc_lock(dscNgayky.getText().substring(3, 5), dscNgayky.getText().substring(6, 10));
		if (acclock) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"ĐÃ KHÓA DL CỦA THÁNG NÀY..");
			return;
		}

		if (chuoi.equals("")) {
			l.ShowMessageOK("Chưa chọn hợp đồng cần ký.");
			return;
		}
		int count = model.getTotalRows();

		String aar[] = new String[count];

		model.setCurrentPage(0);

		navi.reset();
		String mang[] = chuoi.split("-");
		int i;

		for (i = 0; i < mang.length; i++) {

			empsn = model.getValueAt(3, Integer.parseInt(mang[i].toString())).toString();
			mhd = model.getValueAt(11, Integer.parseInt(mang[i].toString())).toString();
			nk = model.getValueAt(8, Integer.parseInt(mang[i].toString())).toString();
			tg = (String) ListBinder.get(sf_loaiHD);
			cv = model.getValueAt(4, Integer.parseInt(mang[i].toString())).toString();
			dv = model.getValueAt(5, Integer.parseInt(mang[i].toString())).toString();
			luong = model.getValueAt(13, Integer.parseInt(mang[i].toString())).toString();
			gc = "";
			cviec = model.getValueAt(4, Integer.parseInt(mang[i].toString())).toString();
			kl = model.getValueAt(14, Integer.parseInt(mang[i].toString())).toString();

			if (!kl.equals("")) {
				l.saveHDLD(empsn, mhd, nk, tg, cv, Vni2Uni.convertToVNI(dv), "0", gc, cviec, "0");
			} else {
				l.saveHDLD(empsn, mhd, nk, tg, cv, Vni2Uni.convertToVNI(dv), luong, gc, cviec, luong);
			}
			// save action daily
			String note_input = "Ký HĐLĐ số thẻ " + empsn + ", ngày ký " + nk + ", lương mới " + luong + ", CV " + cv
					+ ", MHĐ " + mhd;
			l.Insert_N_Action_Daily(u.getPB_USERNO(), "N_LABOUR", "INSERT", empsn, c.convertToVNI(note_input));
		}

		loaddata();

		l.ShowMessageOK("Đã ký " + i + " hợp đồng.");
		chuoi = "";

	}

	public boolean check_input_salary(String salary) {

		if (salary.equals("")) {
			l.ShowMessageError("Lương nhập vào chưa đúng.");
			return false;
		}
		if (!salary.matches("[0-9].*")) {
			l.ShowMessageError("Lương CB không hợp lệ.");
			return false;

		}
		return true;
	}

	private void cell_click(ActionEvent e) {
		/*
		 * if(ischeck==0) { name_g=Table1.getSelectionModel().getMaxSelectedIndex();
		 * pos1=name_g; Object ob; ob=Table1.getModel().getValueAt(3,name_g);
		 * if(pos1==pos3) { return; } else {
		 * 
		 * 
		 * 
		 * if(pos3==-1) { // lan dau tien click final DscField tui1 = new DscField();
		 * tui1.setWidth(new Extent(70,Extent.PX)); Table1.remove((((name_g)*15)+27));
		 * //15 la so cot, 27 la vi tri cell Table1.add(tui1,(((name_g)*15)+27));
		 * tui1.setInputType(DscField.INPUT_TYPE_NUMERIC); tui1.setMaximumLength(8);
		 * note=Table1.getModel().getValueAt(12,name_g).toString(); tui1.setText(note);
		 * 
		 * ischeck=1; tui1.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { if(!check_input_salary(tui1.getText())){
		 * return; }else{ String a=tui1.getText().toString(); Label lb=new Label();
		 * lb.setText(a); Table1.remove((((name_g)*15)+27));
		 * Table1.add(lb,(((name_g)*15)+27)); //update vao mang de khi chuyen trang gia
		 * tri thay doi khong bi reset.
		 * 
		 * update_arr(mang2,12,id_row(mang2,Table1.getModel().getValueAt(2,name_g).
		 * toString(),model.getTotalRows()),a);
		 * 
		 * } } }); } else { note=Table1.getModel().getValueAt(12,pos3).toString(); final
		 * DscField tui1 = new DscField(); tui1.setWidth(new Extent(70,Extent.PX));
		 * tui1.setInputType(DscField.INPUT_TYPE_NUMERIC); tui1.setMaximumLength(8);
		 * tui1.setText(Table1.getModel().getValueAt(12,pos1).toString()); Label lb=new
		 * Label(); lb.setText(note); Table1.remove((((pos3)*15)+27));
		 * Table1.add(lb,(((pos3)*15)+27));
		 * 
		 * Table1.remove((((name_g)*15)+27)); Table1.add(tui1,(((name_g)*15)+27));
		 * ischeck=1;
		 * 
		 * tui1.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { if(!check_input_salary(tui1.getText())){
		 * return; }else{ String a=tui1.getText().toString(); Label lb=new Label();
		 * lb.setText(a); Table1.remove((((name_g)*15)+27));
		 * Table1.add(lb,(((name_g)*15)+27));
		 * update_arr(mang2,12,id_row(mang2,Table1.getModel().getValueAt(2,name_g).
		 * toString(),model.getTotalRows()),a); } } }); } } } if(ischeck==1) {
		 * name_g=Table1.getSelectionModel().getMaxSelectedIndex(); pos2=name_g;
		 * if(pos2==pos1) { return; } else { String
		 * nn=Table1.getModel().getValueAt(12,pos1).toString();
		 * note=Table1.getModel().getValueAt(12,name_g).toString();
		 * 
		 * 
		 * final DscField tui1 = new DscField(); tui1.setWidth(new
		 * Extent(70,Extent.PX)); tui1.setInputType(DscField.INPUT_TYPE_NUMERIC);
		 * tui1.setMaximumLength(8); tui1.setText(note);
		 * 
		 * Label lb=new Label(); lb.setText(nn);
		 * 
		 * Table1.remove((((pos1)*15)+27)); Table1.add(lb,(((pos1)*15)+27));
		 * Table1.remove((((pos2)*15)+27)); Table1.add(tui1,(((pos2)*15)+27));
		 * tui1.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { if(!check_input_salary(tui1.getText())){
		 * return; }else{ String a=tui1.getText().toString(); Label lb=new Label();
		 * lb.setText(a); Table1.remove((((name_g)*15)+27));
		 * Table1.add(lb,(((name_g)*15)+27));
		 * update_arr(mang2,12,id_row(mang2,Table1.getModel().getValueAt(2,name_g).
		 * toString(),model.getTotalRows()),a); } } }); } ischeck=2; }
		 * 
		 * if(ischeck==2) { String nn=Table1.getModel().getValueAt(12,pos2).toString();
		 * name_g=Table1.getSelectionModel().getMaxSelectedIndex();
		 * note=Table1.getModel().getValueAt(12,name_g).toString(); pos3=name_g;
		 * if(pos3==pos2) { return; } else { Label lb=new Label(); lb.setText(nn); final
		 * DscField tui1 = new DscField(); tui1.setWidth(new Extent(70,Extent.PX));
		 * tui1.setInputType(DscField.INPUT_TYPE_NUMERIC); tui1.setMaximumLength(8);
		 * tui1.setText(note);
		 * 
		 * Table1.remove((((pos2)*15)+27)); Table1.add(lb,(((pos2)*15)+27));
		 * 
		 * Table1.remove((((pos3)*15)+27)); Table1.add(tui1,(((pos3)*15)+27));
		 * 
		 * 
		 * 
		 * tui1.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { if(!check_input_salary(tui1.getText())){
		 * return; }else{ String a=tui1.getText().toString(); Label lb=new Label();
		 * lb.setText(a); Table1.remove((((name_g)*15)+27));
		 * Table1.add(lb,(((name_g)*15)+27));
		 * update_arr(mang2,12,id_row(mang2,Table1.getModel().getValueAt(2,name_g).
		 * toString(),model.getTotalRows()),a); } } }); } ischeck=0; }
		 */

	}

//khong su dung
	public void update_arr(String[][] arr, int column_up, int row_up, String value_up) {
		arr[row_up][column_up - 1] = value_up;
		// sau khi update lai dong trong mang thi load lai table theo cai mãng vừa up
		for (int i = 0; i < model.getTotalRows(); i++) {
			model.setValueAt(mang2[i][13], 1, i);
			model.setValueAt(mang2[i][12], 1, i);
			model.setValueAt(mang2[i][11], 1, i);
			model.setValueAt(mang2[i][10], 1, i);
			model.setValueAt(mang2[i][9], 1, i);
			model.setValueAt(mang2[i][8], 1, i);
			model.setValueAt(mang2[i][7], 1, i);
			model.setValueAt(mang2[i][6], 1, i);
			model.setValueAt(mang2[i][5], 1, i);
			model.setValueAt(mang2[i][4], 1, i);
			model.setValueAt(mang2[i][3], 1, i);
			model.setValueAt(mang2[i][2], 1, i);
			model.setValueAt(mang2[i][1], 1, i);
			model.setValueAt(mang2[i][0], 1, i);

			navi.reset();
		}
	}

	public int id_row(String[][] arr, String empsn, int length) {
		int result = 0;
		for (int i = 0; i < length; i++) {
			String asd = arr[i][1].toString();
			if (arr[i][1].toString().compareTo(empsn) == 0) {
				result = i;
				break;
			}
		}
		return result;
	}

	private void doExportDataObjectSet() throws IOException {
		if (sf_xuong.getSelectedIndex() == -1) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Chọn xưởng cần lấy dl...");
			return;
		}
		if ((!dscNgayky.getText().substring(0, 2).equals("01"))
				&& (!dscNgayky.getText().substring(0, 2).equals("15"))) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"NGÀY KÝ HĐ KHÔNG HỢP LỆ.");
			return;
		}

		StopWatch sw = new StopWatch();
		sw.start();
		// DataObjectSet ds = getMasterDataContent().getDataObjectSet();
		con = Application.getApp().getConnection();
		String sql = "select l.empsn,e.fname||' '||e.lname as hoten,l.poss,l.dept, E.DATE_HIRED,L.TIMES,b.bsalary"
				+ " from n_labour l,n_employee e,n_department d,n_basic_salary b where e.empsn=l.empsn and e.depsn=d.id_dept and b.empsn=e.empsn and b.keys=1"
				+ " and d.name_fact='" + sf_xuong.getSelectedItem().toString()
				+ "' and to_char(l.date_s,'dd/mm/yyyy')='" + dscNgayky.getText() + "' order by l.times";

		int rows = 0;// Table1.getModel().getRowCount();
		try {
			st = con.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY);
			rs = st.executeQuery(sql);
			if (rs.next() == false) {
				Application.getApp().showMessageDialog(MessageDialog.TYPE_INFORMATION + MessageDialog.CONTROLS_OK,
						"KHÔNG CÓ DL HĐ VÀO THỜI GIAN BẠN CHỌN");
				return;
			} else {
				rs.last();
				rows = rs.getRow();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		File f = ReportFileManager.getReportFormatFolder("ex");
		InputStream in = new FileInputStream(new File(f.getPath(), "HDLD.xls"));

		HSSFWorkbook wb = new HSSFWorkbook(in);

		int m = 1;// row 1
		int n = 1;
		HSSFSheet sheet = wb.getSheetAt(0);
		sheet.createFreezePane(4, 2);
		sheet.autoSizeColumn(1, true);

		HSSFRow row = null;
		HSSFCell cell = null;
		HSSFFont font = wb.createFont();
		HSSFFont font2 = wb.createFont();
		font.setFontName("Arial");

		HSSFCellStyle style = wb.createCellStyle();
		HSSFCellStyle style2 = wb.createCellStyle();
		HSSFCellStyle style4 = wb.createCellStyle();

		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setFont(font);

		style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setFont(font);
		style2.setFillForegroundColor(HSSFColor.LAVENDER.index);
		style2.setFillPattern(HSSFPatternFormatting.SOLID_FOREGROUND);

		style4.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style4.setFont(font);
		style4.setFillForegroundColor(HSSFColor.ROSE.index);
		style4.setFillPattern(HSSFPatternFormatting.SOLID_FOREGROUND);

		HSSFCellStyle style3 = wb.createCellStyle();
		font2.setFontName("Arial");
		font2.setBoldweight((short) 15);
		font2.setColor(HSSFColor.BLUE.index);

		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style3.setFont(font2);

		row = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		cell = row.createCell(0);
		cell.setCellStyle(style3);
		cell.setCellValue("DS HDLD " + sf_xuong.getSelectedItem() + " KÝ NGÀY  " + dscNgayky.getText());

		String time = "";
		// for (int i=0;i<rows;i++){
		try {
			rs.beforeFirst();
			while (rs.next()) {
				time = rs.getString(6).toString();

				HSSFCellStyle styles = wb.createCellStyle();

				if (time.equals("1")) {
					styles = style;
				} else if (time.equals("2")) {
					styles = style2;
				} else {
					styles = style4;
				}
				m++;

				row = sheet.createRow(m);

				cell = row.createCell(0);
				cell.setCellStyle(styles);
				cell.setCellValue(rs.getString(1));
				cell = row.createCell(1);
				cell.setCellStyle(styles);
				cell.setCellValue(c.convertToUnicode(rs.getString(2)));
				cell = row.createCell(2);
				cell.setCellStyle(styles);
				cell.setCellValue(rs.getString(3));
				cell = row.createCell(3);
				cell.setCellStyle(styles);
				cell.setCellValue(c.convertToUnicode(rs.getString(4)));
				cell = row.createCell(4);
				cell.setCellStyle(styles);
				cell.setCellValue(df.format(rs.getDate(5)));
				cell = row.createCell(5);
				cell.setCellStyle(styles);
				cell.setCellValue(rs.getString(6));
				cell = row.createCell(6);
				cell.setCellStyle(styles);
				cell.setCellValue(rs.getString(7));
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

		for (int col = 0; col < 6; col++) {
			sheet.autoSizeColumn(col);
		}
		sw.stop();
		System.out.println("Export time ->>> " + (float) sw.getTime() / 1000);
		File f1 = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
		FileOutputStream out = new FileOutputStream(f1);
		wb.write(out);
		out.flush();
		out.close();
		String name = "HDLD_" + dscNgayky.getText();
		long t = Calendar.getInstance().getTimeInMillis();
		File saveFile;
		saveFile = new File(f1.getParentFile(), URLEncoder.encode(Application.getApp().getLoginInfo().getUserID()
				+ ";application/vnd.ms-excel;" + "HD_" + dscNgayky.getText() + "_" + t + ".xls", "UTF-8"));
		ReportFileManager.saveTempReportFile(f1, saveFile);
		Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
	}

	public String getViewerUrl() {
		HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
		String viewerUrl = request.getRequestURL() + "?" + WebRenderServlet.SERVICE_ID_PARAMETER + "="
				+ ReportService.INSTANCE.getId() + "&" + ReportService.PARAM_TYPE + "=" + ReportService.TYPE_TEMP + "&"
				+ ReportService.PARAM_KEY + "=";
		return viewerUrl;
	}

	private void ex(ActionEvent e) {
		// TODO Implement.

		try {
			doExportDataObjectSet();
		} catch (IOException ex) {
			// TODO: handle exception
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"LỔI KHÔNG THỂ XUẤT DL RA EXCEL..");
		}

	}

	private void reset(ActionEvent e) {
		// TODO Implement.
		dsc_sothe.setText("");

		r_hd1.setSelected(false);
		r_hd2.setSelected(false);
		r_hd3.setSelected(false);
		sf_loaiHD.setSelectedIndex(-1);
		sf_nhom.setSelectedIndex(-1);
		sf_donvi.setSelectedIndex(-1);

		lbl_info.setVisible(false);

		model = (PageableSortableTableModel) Table1.getModel();
		model.clear();

		navi.reset();

		ListBinder.bindSelectField(sf_xuong, FVGenericInfo.getFactories(), true);
		ListBinder.bindSelectField(sf_nhom, FVGenericInfo.getGroup(sf_xuong.getSelectedItem()), true);
		ListBinder.bindSelectField(sf_donvi,
				FVGenericInfo.getDeptName(sf_xuong.getSelectedItem(), sf_nhom.getSelectedItem()), true);

	}

	public void doPrint() {
		String loai = "";
		String x = "", n = "", dv = "";
		if (r_hd1.isSelected()) {
			loai = "1";
		} else {
			loai = "0";
		}
		if (sf_xuong.getSelectedIndex() != -1) {
			x = ListBinder.get(sf_xuong).toString();
		}
		if (sf_nhom.getSelectedIndex() != -1) {
			n = ListBinder.get(sf_nhom).toString();
		}
		if (sf_donvi.getSelectedIndex() != -1) {
			dv = ListBinder.get(sf_donvi).toString();
		}
		String sql = l.getSQLHDLD(dsc_sothe.getText(), x, n, dv, dscNgayky.getText(), loai);
		// System.out.println(sql);
		if (!l.exe_sql_query(sql)) {
			l.ShowMessageError("Không có dl.");
			return;
		}

		JasperDesign jd = null;
		Connection con = null;
		try {
			if (rad_all_in.isSelected() && rad_all_trang.isSelected()) {
				jd = JRXmlLoader.load(ReportFileManager.getReportFormatFolder("fvhr/HDLD.jrxml"));
			}
			if (rad_all_in.isSelected() && rad_page1.isSelected()) {
				jd = JRXmlLoader.load(ReportFileManager.getReportFormatFolder("fvhr/hd_1.jrxml"));
				// l.ShowMessageInfo("page 1");
				// return;
			}
			if (rad_all_in.isSelected() && rad_page2.isSelected()) {
				jd = JRXmlLoader.load(ReportFileManager.getReportFormatFolder("fvhr/hd_2.jrxml"));
				// l.ShowMessageInfo("page 2");
				// return;
			}
			if (rad_all_in.isSelected() && rad_page3.isSelected()) {
				jd = JRXmlLoader.load(ReportFileManager.getReportFormatFolder("fvhr/hd_3.jrxml"));
				// l.ShowMessageInfo("page 3");
				// return;
			}
			if (rad_mau.isSelected() && rad_all_trang.isSelected()) {
				jd = JRXmlLoader.load(ReportFileManager.getReportFormatFolder("fvhr/HDLD_theomau.jrxml"));
				// l.ShowMessageInfo("mau page all");
				// return;
			}
			if (rad_mau.isSelected() && rad_page1.isSelected()) {
				jd = JRXmlLoader.load(ReportFileManager.getReportFormatFolder("fvhr/hd_mau_1.jrxml"));
				// l.ShowMessageInfo("mau page 1");
				// return;
			}
			if (rad_mau.isSelected() && rad_page2.isSelected()) {
				jd = JRXmlLoader.load(ReportFileManager.getReportFormatFolder("fvhr/hd_mau_2.jrxml"));
				// l.ShowMessageInfo("mau page 2");
				// return;
			}
			if (rad_mau.isSelected() && rad_page3.isSelected()) {
				jd = JRXmlLoader.load(ReportFileManager.getReportFormatFolder("fvhr/hd_mau_3.jrxml"));
				// l.ShowMessageInfo("mau page 3");
				// return;
			}

			JRDesignQuery query = new JRDesignQuery();
			query.setText(sql);
			jd.setQuery(query);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			con = Application.getApp().getConnection();
			JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
			File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
			// JasperViewer.viewReport(jp);
			JasperExportManager.exportReportToPdfFile(jp, f.getPath());

			File saveFile = new File(f.getParentFile(),
					URLEncoder.encode(Application.getApp().getLoginInfo().getUserID() + ";application/pdf;"
							+ "PLHD_00001_" + Calendar.getInstance().getTimeInMillis() + ".pdf", "UTF-8"));
			ReportFileManager.saveTempReportFile(f, saveFile);
			Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
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

	private void sfFactChanged(ActionEvent e) {
		dsc_sothe.setText("");
		SelectItem item = (SelectItem) sf_xuong.getSelectedItem();

		ListBinder.bindSelectField(sf_nhom, FVGenericInfo.getGroup(item.getValue()), true);
		// ListBinder.bindSelectField(sfDept,
		// FVGenericInfo.getDeptName(item.getValue()), true);

	}

	private void sfgroupChanged(ActionEvent e) {
		SelectItem item = (SelectItem) sf_nhom.getSelectedItem();

		// ListBinder.bindSelectField(sf_nhom, FVGenericInfo.getGroup(item.getValue()),
		// true);
		ListBinder.bindSelectField(sf_donvi,
				FVGenericInfo.getDept(sf_xuong.getSelectedItem().toString(), item.getValue()), true);

	}

	private void f_empsn(FocusEvent e) {
		// TODO Implement.
		ListBinder.bindSelectField(sf_xuong, FVGenericInfo.getFactories(), true);
		ListBinder.bindSelectField(sf_nhom, FVGenericInfo.getGroup(sf_xuong.getSelectedItem()), true);
		ListBinder.bindSelectField(sf_donvi,
				FVGenericInfo.getDeptName(sf_xuong.getSelectedItem(), sf_nhom.getSelectedItem()), true);
	}

	public void setShow_hide(boolean t) {
		label1.setVisible(t);
		label10.setVisible(t);
		label2.setVisible(t);
		label5.setVisible(t);
		label6.setVisible(t);
		label7.setVisible(t);
		label14.setVisible(t);
		label15.setVisible(t);
		label16.setVisible(t);
		label17.setVisible(t);
		label18.setVisible(t);
		label20.setVisible(t);
		label21.setVisible(t);

		groupBox1.setVisible(t);
		groupBox2.setVisible(t);
		groupBox3.setVisible(t);

		dsc_sothe.setVisible(t);
		dscGroupRadioButton1.setVisible(t);
		dscNgayky.setVisible(t);
		sf_donvi.setVisible(t);
		sf_loaiHD.setVisible(t);
		sf_nhom.setVisible(t);
		sf_xuong.setVisible(t);

		label12.setVisible(t);
		label8.setVisible(t);
		// chkSearchList.setVisible(t);

		row4.setVisible(t);
		btn_dschuaky.setVisible(t);
		btn_dsky.setVisible(t);
		btn_hide.setVisible(t);
		btn_ky.setVisible(t);
		btn_print.setVisible(t);
		btn_reset.setVisible(t);
		btn_tk.setVisible(t);

	}

	private void hide(ActionEvent e) {
		// TODO Implement.
		setShow_hide(false);
		btn_show.setVisible(true);
		splitPane1.setSeparatorPosition(new Extent(17, Extent.PX));
		splitPane1.setSeparatorWidth(new Extent(0, Extent.PX));

	}

	/*
	 * private void searchEmpsnInTable() { // kiem tra chuoi nhap vao la chu if
	 * (dsc_sothe.getText().trim().length() > 0) {
	 * 
	 * if (chkSearchList.isSelected()) { String empsn = dsc_sothe.getText().trim();
	 * int page=model.getTotalPages(); int index=0; while(index<page){
	 * model.setCurrentPage(index); for (int i = 0; i < model.getRowCount(); i++) {
	 * if (empsn .equals(model.getValueAt(3, i).toString())) {
	 * 
	 * 
	 * // có trong danh sách ListSelectionModel listSelectionModel = Table1
	 * .getSelectionModel(); listSelectionModel.setSelectedIndex(i, true);
	 * 
	 * navi.reset(); return; } } index++; }
	 * l.ShowMessageOK("không tìm thấy dữ liệu."); model.setCurrentPage(0);
	 * 
	 * }else{ if(Table1.getModel().getRowCount()>0){ model.setCurrentPage(0); }
	 * btn_tk.doAction(); }
	 * 
	 * } else { MessageDialog dialog = new MessageDialog("Lỗi",
	 * "Số thẻ nhập vào không hợp lệ.", MessageDialog.CONTROLS_OK);
	 * 
	 * dialog.show(); } }
	 */

	private void show(ActionEvent e) {
		// TODO Implement.
		setShow_hide(true);
		btn_show.setVisible(false);
		splitPane1.setSeparatorPosition(new Extent(230, Extent.PX));
		splitPane1.setSeparatorWidth(new Extent(1, Extent.PX));
	}

	private void st_ac(ActionEvent e) {
		// TODO Implement.
		/*
		 * if(chkSearchList.isSelected()){ if(Table1.getModel().getRowCount()==0){
		 * l.ShowMessageOK("Chưa có danh sách để tìm kiếm."); return; } }
		 * searchEmpsnInTable();
		 */
		btn_tk.doAction();
	}

	private void chkSearchList_ac(ActionEvent e) {
		// TODO Implement.

	}

	private void excel_chuaky(ActionEvent e) {
		// TODO Implement.
		if (Table1.getModel().getRowCount() > 0) {
			try {
				doExportDataObjectSet2();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			l.ShowMessageOK("không có dl.");
			return;
		}

	}

	/**
	 * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		ContentPane contentPane1 = new ContentPane();
		add(contentPane1);
		splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(230, Extent.PX));
		splitPane1.setBackground(new Color(0xf2f9ff));
		splitPane1.setSeparatorWidth(new Extent(1, Extent.PX));
		splitPane1.setResizable(true);
		contentPane1.add(splitPane1);
		Grid grid1 = new Grid();
		grid1.setSize(2);
		splitPane1.add(grid1);
		label1 = new Label();
		label1.setText("Ngày Ký");
		GridLayoutData label1LayoutData = new GridLayoutData();
		label1LayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5, Extent.PX),
				new Extent(0, Extent.PX), new Extent(5, Extent.PX)));
		label1.setLayoutData(label1LayoutData);
		grid1.add(label1);
		dscNgayky = new DscDateField();
		GridLayoutData dscNgaykyLayoutData = new GridLayoutData();
		dscNgaykyLayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5, Extent.PX),
				new Extent(0, Extent.PX), new Extent(5, Extent.PX)));
		dscNgayky.setLayoutData(dscNgaykyLayoutData);
		grid1.add(dscNgayky);
		label10 = new Label();
		label10.setText("Số thẻ");
		grid1.add(label10);
		dsc_sothe = new DscField();
		dsc_sothe.setWidth(new Extent(153, Extent.PX));
		dsc_sothe.setMaximumLength(8);
		dsc_sothe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				st_ac(e);
			}
		});
		dsc_sothe.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				f_empsn(e);
			}

			public void focusLost(FocusEvent e) {
			}
		});
		grid1.add(dsc_sothe);
		Label label11 = new Label();
		grid1.add(label11);
		Label label9 = new Label();
		grid1.add(label9);
		label2 = new Label();
		label2.setText("Xưởng");
		GridLayoutData label2LayoutData = new GridLayoutData();
		label2LayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		label2.setLayoutData(label2LayoutData);
		grid1.add(label2);
		sf_xuong = new SelectField();
		GridLayoutData sf_xuongLayoutData = new GridLayoutData();
		sf_xuongLayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		sf_xuong.setLayoutData(sf_xuongLayoutData);
		grid1.add(sf_xuong);
		label5 = new Label();
		label5.setText("Nhóm");
		GridLayoutData label5LayoutData = new GridLayoutData();
		label5LayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		label5.setLayoutData(label5LayoutData);
		grid1.add(label5);
		sf_nhom = new SelectField();
		GridLayoutData sf_nhomLayoutData = new GridLayoutData();
		sf_nhomLayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		sf_nhom.setLayoutData(sf_nhomLayoutData);
		grid1.add(sf_nhom);
		label7 = new Label();
		label7.setText("Đơn vị");
		GridLayoutData label7LayoutData = new GridLayoutData();
		label7LayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		label7.setLayoutData(label7LayoutData);
		grid1.add(label7);
		sf_donvi = new SelectField();
		GridLayoutData sf_donviLayoutData = new GridLayoutData();
		sf_donviLayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		sf_donvi.setLayoutData(sf_donviLayoutData);
		grid1.add(sf_donvi);
		label6 = new Label();
		label6.setText("Loại HĐ");
		GridLayoutData label6LayoutData = new GridLayoutData();
		label6LayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		label6.setLayoutData(label6LayoutData);
		grid1.add(label6);
		sf_loaiHD = new SelectField();
		GridLayoutData sf_loaiHDLayoutData = new GridLayoutData();
		sf_loaiHDLayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		sf_loaiHD.setLayoutData(sf_loaiHDLayoutData);
		grid1.add(sf_loaiHD);
		Label label19 = new Label();
		grid1.add(label19);
		label8 = new Label();
		grid1.add(label8);
		Label label4 = new Label();
		grid1.add(label4);
		Label label3 = new Label();
		grid1.add(label3);
		btn_show = new Button();
		btn_show.setText(">>");
		btn_show.setVisible(false);
		btn_show.setToolTipText("Hiện thanh tìm kiếm");
		btn_show.setRolloverForeground(Color.RED);
		btn_show.setRolloverEnabled(true);
		btn_show.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show(e);
			}
		});
		grid1.add(btn_show);
		label14 = new Label();
		grid1.add(label14);
		groupBox3 = new GroupBox();
		groupBox3.setWidth(new Extent(140, Extent.PX));
		groupBox3.setBorder(new Border(new Extent(-1, Extent.PX), Color.BLACK, Border.STYLE_SOLID));
		grid1.add(groupBox3);
		dscGroupRadioButton1 = new DscGroupRadioButton();
		GridLayoutData dscGroupRadioButton1LayoutData = new GridLayoutData();
		dscGroupRadioButton1LayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5, Extent.PX),
				new Extent(0, Extent.PX), new Extent(5, Extent.PX)));
		dscGroupRadioButton1.setLayoutData(dscGroupRadioButton1LayoutData);
		dscGroupRadioButton1.setSize(1);
		groupBox3.add(dscGroupRadioButton1);
		r_hd1 = new RadioButton();
		r_hd1.setText("HĐ Lần 1");
		r_hd1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hdl1(e);
			}
		});
		dscGroupRadioButton1.add(r_hd1);
		r_hd2 = new RadioButton();
		r_hd2.setText("HĐ Lần 2");
		r_hd2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hdl2(e);
			}
		});
		dscGroupRadioButton1.add(r_hd2);
		r_hd3 = new RadioButton();
		r_hd3.setText("HĐ Lần 3");
		r_hd3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hdl3(e);
			}
		});
		dscGroupRadioButton1.add(r_hd3);
		label15 = new Label();
		grid1.add(label15);
		groupBox1 = new GroupBox();
		groupBox1.setWidth(new Extent(140, Extent.PX));
		groupBox1.setBorder(new Border(new Extent(-1, Extent.PX), Color.BLACK, Border.STYLE_SOLID));
		grid1.add(groupBox1);
		DscGroupRadioButton dscGroupRadioButton2 = new DscGroupRadioButton();
		dscGroupRadioButton2.setSize(1);
		groupBox1.add(dscGroupRadioButton2);
		rad_mau = new RadioButton();
		rad_mau.setText("Theo mẫu");
		dscGroupRadioButton2.add(rad_mau);
		rad_all_in = new RadioButton();
		rad_all_in.setText("Đầy đủ");
		dscGroupRadioButton2.add(rad_all_in);
		label16 = new Label();
		grid1.add(label16);
		groupBox2 = new GroupBox();
		groupBox2.setWidth(new Extent(140, Extent.PX));
		groupBox2.setBorder(new Border(new Extent(-1, Extent.PX), Color.BLACK, Border.STYLE_SOLID));
		grid1.add(groupBox2);
		DscGroupRadioButton dscGroupRadioButton3 = new DscGroupRadioButton();
		dscGroupRadioButton3.setSize(1);
		groupBox2.add(dscGroupRadioButton3);
		rad_page1 = new RadioButton();
		rad_page1.setText("Trang 1");
		dscGroupRadioButton3.add(rad_page1);
		rad_page2 = new RadioButton();
		rad_page2.setText("Trang 2");
		dscGroupRadioButton3.add(rad_page2);
		rad_page3 = new RadioButton();
		rad_page3.setText("Trang 3");
		dscGroupRadioButton3.add(rad_page3);
		rad_all_trang = new RadioButton();
		rad_all_trang.setText("Tất cả");
		dscGroupRadioButton3.add(rad_all_trang);
		label17 = new Label();
		grid1.add(label17);
		btn_tk = new Button();
		btn_tk.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btn_tk.setText("Tìm kiếm thông tin");
		btn_tk.setHeight(new Extent(20, Extent.PX));
		btn_tk.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_INSET));
		btn_tk.setRolloverBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_tk.setWidth(new Extent(156, Extent.PX));
		btn_tk.setBackground(new Color(0x0080ff));
		btn_tk.setPressedEnabled(true);
		btn_tk.setBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_GROOVE));
		btn_tk.setRolloverEnabled(true);
		btn_tk.setForeground(Color.WHITE);
		GridLayoutData btn_tkLayoutData = new GridLayoutData();
		btn_tkLayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		btn_tk.setLayoutData(btn_tkLayoutData);
		btn_tk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getdata(e);
			}
		});
		grid1.add(btn_tk);
		label23 = new Label();
		grid1.add(label23);
		row4 = new Row();
		grid1.add(row4);
		btn_ky = new Button();
		btn_ky.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btn_ky.setText("Ký HĐ");
		btn_ky.setHeight(new Extent(20, Extent.PX));
		btn_ky.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_INSET));
		btn_ky.setRolloverBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_ky.setWidth(new Extent(76, Extent.PX));
		btn_ky.setBackground(new Color(0x0080ff));
		btn_ky.setPressedEnabled(true);
		btn_ky.setBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_GROOVE));
		btn_ky.setRolloverEnabled(true);
		btn_ky.setForeground(Color.WHITE);
		RowLayoutData btn_kyLayoutData = new RowLayoutData();
		btn_kyLayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(2, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		btn_ky.setLayoutData(btn_kyLayoutData);
		btn_ky.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kyhd(e);
			}
		});
		row4.add(btn_ky);
		btn_print = new Button();
		btn_print.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btn_print.setText("In HĐ");
		btn_print.setHeight(new Extent(20, Extent.PX));
		btn_print.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_INSET));
		btn_print.setRolloverBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_print.setWidth(new Extent(76, Extent.PX));
		btn_print.setBackground(new Color(0x0080ff));
		btn_print.setBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_GROOVE));
		btn_print.setRolloverEnabled(true);
		btn_print.setForeground(Color.WHITE);
		RowLayoutData btn_printLayoutData = new RowLayoutData();
		btn_printLayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(2, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		btn_print.setLayoutData(btn_printLayoutData);
		row4.add(btn_print);
		label18 = new Label();
		grid1.add(label18);
		btn_dschuaky = new Button();
		btn_dschuaky.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btn_dschuaky.setText("Danh sách chưa ký");
		btn_dschuaky.setHeight(new Extent(20, Extent.PX));
		btn_dschuaky.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_INSET));
		btn_dschuaky.setRolloverBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_dschuaky.setWidth(new Extent(156, Extent.PX));
		btn_dschuaky.setBackground(new Color(0x0080ff));
		btn_dschuaky.setPressedEnabled(true);
		btn_dschuaky.setBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_GROOVE));
		btn_dschuaky.setRolloverEnabled(true);
		btn_dschuaky.setForeground(Color.WHITE);
		GridLayoutData btn_dschuakyLayoutData = new GridLayoutData();
		btn_dschuakyLayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(2, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		btn_dschuaky.setLayoutData(btn_dschuakyLayoutData);
		btn_dschuaky.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excel_chuaky(e);
			}
		});
		grid1.add(btn_dschuaky);
		label20 = new Label();
		grid1.add(label20);
		btn_dsky = new Button();
		btn_dsky.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btn_dsky.setText("Danh sách đã ký");
		btn_dsky.setHeight(new Extent(20, Extent.PX));
		btn_dsky.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_INSET));
		btn_dsky.setRolloverBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_dsky.setWidth(new Extent(156, Extent.PX));
		btn_dsky.setBackground(new Color(0x0080ff));
		btn_dsky.setPressedEnabled(true);
		btn_dsky.setBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_GROOVE));
		btn_dsky.setRolloverEnabled(true);
		btn_dsky.setForeground(Color.WHITE);
		GridLayoutData btn_dskyLayoutData = new GridLayoutData();
		btn_dskyLayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(2, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		btn_dsky.setLayoutData(btn_dskyLayoutData);
		grid1.add(btn_dsky);
		label12 = new Label();
		grid1.add(label12);
		btn_reset = new Button();
		btn_reset.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btn_reset.setText("Reset");
		btn_reset.setHeight(new Extent(20, Extent.PX));
		btn_reset.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_INSET));
		btn_reset.setRolloverBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_reset.setWidth(new Extent(156, Extent.PX));
		btn_reset.setBackground(new Color(0x0080ff));
		btn_reset.setPressedEnabled(true);
		btn_reset.setBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_GROOVE));
		btn_reset.setRolloverEnabled(true);
		btn_reset.setForeground(Color.WHITE);
		GridLayoutData btn_resetLayoutData = new GridLayoutData();
		btn_resetLayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(2, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		btn_reset.setLayoutData(btn_resetLayoutData);
		btn_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset(e);
			}
		});
		grid1.add(btn_reset);
		label21 = new Label();
		grid1.add(label21);
		btn_hide = new Button();
		btn_hide.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btn_hide.setText("Ẩn thanh tìm kiếm");
		btn_hide.setHeight(new Extent(20, Extent.PX));
		btn_hide.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_INSET));
		btn_hide.setRolloverBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_OUTSET));
		btn_hide.setWidth(new Extent(156, Extent.PX));
		btn_hide.setBackground(new Color(0x0080ff));
		btn_hide.setPressedEnabled(true);
		btn_hide.setBorder(new Border(new Extent(2, Extent.PX), new Color(0x8080c0), Border.STYLE_GROOVE));
		btn_hide.setRolloverEnabled(true);
		btn_hide.setForeground(Color.WHITE);
		GridLayoutData btn_hideLayoutData = new GridLayoutData();
		btn_hideLayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(2, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		btn_hide.setLayoutData(btn_hideLayoutData);
		btn_hide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hide(e);
			}
		});
		grid1.add(btn_hide);
		label22 = new Label();
		grid1.add(label22);
		SplitPane splitPane2 = new SplitPane();
		splitPane2.setSeparatorPosition(new Extent(50, Extent.PX));
		splitPane2.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane2.setSeparatorColor(new Color(0x8080ff));
		splitPane1.add(splitPane2);
		navi = new Navigation();
		splitPane2.add(navi);
		Table1 = new DscPageableSortableTable();
		Table1.setStyleName("Table.DscPageableSortableTable");
		Table1.setAutoCreateColumnsFromModel(false);
		SplitPaneLayoutData Table1LayoutData = new SplitPaneLayoutData();
		Table1LayoutData.setInsets(new Insets(new Extent(0, Extent.PX)));
		Table1.setLayoutData(Table1LayoutData);
		Table1.setEvenRowBackground(new Color(0xfffff0));
		Table1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cell_click(e);
			}
		});
		splitPane2.add(Table1);
	}

}
