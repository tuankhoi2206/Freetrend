package ds.program.fvhr.vinh;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Table;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.layout.TableLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.list.DefaultListSelectionModel;
import nextapp.echo2.app.table.DefaultTableModel;
import nextapp.echo2.app.table.TableCellRenderer;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.vinh.dao_support.ExportInsuranceInfoDAO;
import ds.program.fvhr.vinh.report_object.EmpComparator;
import ds.program.fvhr.vinh.report_object.EmployeePlain;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.info.LoginInfo;
import dsc.util.function.UUID;

import fv.components.ReportToolbar;
import fv.components.SimpleReportProgram;
import fv.util.ReportUtils;
import fv.util.Vni2Uni;

public class InsuranceMProgram extends SimpleReportProgram {

	private SplitPane splitPane_3;
	private SplitPane splitPane_4;
	private Grid searchOption;

	private Row tabBar;
	private Button insufficientListTab;
	private Button sufficientListTab;
	private Button listOfAllTab;
	private Button taxpayerTab;
	private Button onMatLeaveTab;
	private Label monthLbl;
	private SelectField monthSF;
	private Label yearLbl;
	private SelectField yearSF;
	private Label dpmtIDLbl;
	private SelectField dpmtIDSF;
	private Label wsLbl;
	private SelectField wsSF;
	private Label grLbl;
	private SelectField grSF;
	private Label unitLbl;
	private SelectField unitSF;
	private Label dpmtAllLbl;
	private CheckBox allEmpCB;
	private CheckBox quitEmpCB;

	private Table tblData;

	List<EmployeePlain> listData;

	private static boolean INSUFFICIENT_ACTIVE = true;
	private static boolean SUFFICIENT_ACTIVE = false;
	private static boolean ALL_ACTIVE = false;
	private static boolean TAXPAYER_ACTIVE = false;
	private static boolean ONMATLEAVER_ACTIVE = false;

	private static int EMPSN_CLICK_COUNT = 0;

	/**
	 * Some tablecolumn name
	 */

	public static String EMPSN = "Số thẻ";
	public static String EMP_FNAME = "Họ";
	public static String EMP_LNAME = "Tên";
	public static String EMP_POSITION = "Chức vụ";
	public static String DEPT_ID = "Mã đơn vị";
	public static String DEPT_NAME = "Tên đơn vị";
	public static String OFF_DAY = "Ngày không đi làm";
	public static String NO_SALARY_DAY = "Tổng ngày không lương";
	public static String BASIC_SALARY = "Lương cơ bản";
	public static String COM_SALARY = "Lương hợp đồng";
	public static String SIGN_DATE = "Ngày ký hợp đồng";
	public static String LABOUR_KIND = "Thời hạn";
	public static String DUCLS = "Ngày làm + nghỉ có lương";
	public static String MATERNITY_LEAVE = "Nghỉ sản";
	public static String PAIR_VACATION = "Nghỉ có phép";
	public static String ABSENT_WITHOUT_LEAVE = "Kháng công";
	public static String SOCIAL_INSURANCE = "Tiền BHXH";
	public static String UNEMPLOYMENT_INSURANCE = "Tiền BHXH";
	public static String QUIT_DAY = "Ngày thực nghỉ";

	/**
	 * Creates a new <code>DefaultForm</code>.
	 */
	public InsuranceMProgram() {
		super();
		// Add design-time configured components.
		initComponents();
	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		getToolbar().setSearchButtonEnable(false);
		getToolbar().setConfigButtonEnable(false);
		getToolbar().setPdfButtonEnable(false);
		return ret;
	}

	/**
	 * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		// JdbcDAO dao = new JdbcDAO();
		// System.out.println(dao.getJdbcTemplate().queryForInt("select count(*)
		// from n_employee"));
		//		
		// windowPane.setTitle("Danh sách mua BHXH - BHTN trong tháng");
		// windowPane.setTitleFont(new Font(Font.ARIAL, Font.BOLD, new
		// Extent(14,
		// Extent.PT)));

		// listData = new ArrayList<EmployeePlain>();

		splitPane_3 = new SplitPane();
		splitPane_3.setSeparatorPosition(new Extent(250, Extent.PX));
		splitPane_3.setSeparatorWidth(new Extent(1, Extent.PX));
		splitPane_3.setSeparatorColor(Color.BLUE);
		splitPane_3.setOrientation(SplitPane.ORIENTATION_HORIZONTAL_LEFT_RIGHT);
		add(splitPane_3);

		loadOptionBar();

		splitPane_4 = new SplitPane();
		splitPane_4.setSeparatorPosition(new Extent(30, Extent.PX));
		splitPane_4.setSeparatorHeight(new Extent(1, Extent.PX));
		splitPane_4.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane_3.add(splitPane_4);

		loadTabBar();
		loadTable();
	}

	private void loadTable() {
		tblData = new Table();
		tblData.setRolloverBackground(new Color(0xc9d2a2));
		tblData.setSelectionBackground(Color.DARKGRAY);
		tblData.setRolloverEnabled(true);
		tblData.setSelectionEnabled(true);
		tblData.setWidth(new Extent(2500, Extent.PX));
		tblData.setBackground(Color.BLUE);
		tblData.setBorder(new Border(new Extent(1, Extent.PX), Color.WHITE,
				Border.STYLE_SOLID));
		splitPane_4.add(tblData);

		// config table header renderer
		tblData.setDefaultHeaderRenderer(new TableCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(Table table,
					final Object value, int column, int row) {
				String columnName = (String) value;
				Button headerBtn = new Button();
				Font font = new Font(Font.ARIAL, Font.BOLD, new Extent(11));
				headerBtn.setFont(font);
				headerBtn.setText((String) value);
				headerBtn.setForeground(Color.WHITE);
				TableLayoutData layout = new TableLayoutData();
				layout.setBackground(new Color(0x0080C0));

				layout.setInsets(new Insets(3));
				if (columnName.equals(EMP_FNAME)
						|| columnName.equals(EMP_LNAME)
						|| columnName.equals(DEPT_NAME)
						|| columnName.equals(SIGN_DATE)
						|| columnName.equals(LABOUR_KIND)
						|| columnName.equals(QUIT_DAY)) {
					layout.setInsets(new Insets(5, 2, 1, 2));
				} else {
					layout.setAlignment(new Alignment(Alignment.RIGHT,
							Alignment.CENTER));
					layout.setInsets(new Insets(1, 2, 5, 2));
				}
				headerBtn.setLayoutData(layout);
				headerBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String key = (String) value;

						doSort(e, key);

					}

					private void doSort(ActionEvent e, String key) {
						if (listData != null) {
							if (EMPSN_CLICK_COUNT % 2 == 0) {
								Collections.sort(listData, new EmpComparator(
										EmpComparator.ASC_ORDER, key));
								addDataToShow(listData);

							} else {
								Collections.sort(listData, new EmpComparator(
										EmpComparator.DESC_ORDER, key));
								addDataToShow(listData);

							}
							EMPSN_CLICK_COUNT++;
						}
					}

				});
				return headerBtn;
			}
		});

		// model
		DefaultTableModel model = (DefaultTableModel) tblData.getModel();
		model.setColumnCount(20);
		model.setColumnName(0, "STT");
		model.setColumnName(1, EMPSN);
		model.setColumnName(2, EMP_FNAME);
		model.setColumnName(3, EMP_LNAME);
		model.setColumnName(4, EMP_POSITION);

		model.setColumnName(5, DEPT_ID);
		model.setColumnName(6, DEPT_NAME);
		model.setColumnName(7, BASIC_SALARY);
		model.setColumnName(8, COM_SALARY);
		model.setColumnName(9, SIGN_DATE);

		model.setColumnName(10, LABOUR_KIND);
		model.setColumnName(11, DUCLS);
		model.setColumnName(12, MATERNITY_LEAVE);
		model.setColumnName(13, PAIR_VACATION);
		model.setColumnName(14, ABSENT_WITHOUT_LEAVE);

		model.setColumnName(15, OFF_DAY);
		model.setColumnName(16, NO_SALARY_DAY);
		model.setColumnName(17, SOCIAL_INSURANCE);
		model.setColumnName(18, UNEMPLOYMENT_INSURANCE);
		model.setColumnName(19, QUIT_DAY);

		for (int i = 0; i < model.getColumnCount(); i++) {
			final String columnName = tblData.getModel().getColumnName(i);

			tblData.getColumnModel().getColumn(i).setCellRenderer(
					new TableCellRenderer() {
						@Override
						public Component getTableCellRendererComponent(
								Table table, Object value, int column, int row) {
							Label label = new Label();
							if (value != null) {
								label.setText(value.toString());
							} else {
								label.setText("");
							}
							label.setFont(new Font(Font.TIMES_NEW_ROMAN,
									Font.PLAIN, new Extent(12, Extent.PT)));
							TableLayoutData layout = new TableLayoutData();
							if (row % 2 == 0) {
								layout.setBackground(new Color(0xffffe1));
							} else {
								layout.setBackground(new Color(0xe8d1dc));
							}
							if (columnName.equals(EMP_FNAME)
									|| columnName.equals(EMP_LNAME)
									|| columnName.equals(DEPT_NAME)
									|| columnName.equals(SIGN_DATE)
									|| columnName.equals(LABOUR_KIND)
									|| columnName.equals(QUIT_DAY)) {
								layout.setInsets(new Insets(5, 2, 1, 2));
							} else {
								layout.setAlignment(new Alignment(
										Alignment.RIGHT, Alignment.CENTER));
								layout.setInsets(new Insets(1, 2, 5, 2));
							}
							label.setLayoutData(layout);
							label.setToolTipText(tblData.getModel()
									.getColumnName(column));
							return label;
						}

					});

		}
	}

	private void addDataToShow(List<EmployeePlain> listData) {
		if (listData.size() == 0) {
		}
		DefaultTableModel model = (DefaultTableModel) tblData.getModel();
		// remove all rows
		model.setRowCount(0);
		// add row

		for (int i = 0; i < listData.size(); i++) {
			String quitDate = "";
			if (listData.get(i).getQuitDay() != null) {
				quitDate = new SimpleDateFormat("dd-MM-yyyy").format(listData
						.get(i).getQuitDay());
			}
			String signDate = "";
			if (listData.get(i).getSignDate() != null) {
				signDate = new SimpleDateFormat("dd-MM-yyyy").format(listData
						.get(i).getSignDate());
			}
			model.addRow(new Object[] { i + 1, listData.get(i).getEmpsn(),
					Vni2Uni.convertToUnicode(listData.get(i).getFname()),
					Vni2Uni.convertToUnicode(listData.get(i).getLname()),
					listData.get(i).getPositionBonus(),

					listData.get(i).getDeptID(),
					Vni2Uni.convertToUnicode(listData.get(i).getDeptName()),
					listData.get(i).getBasicSalary(),
					listData.get(i).getComSalary(), signDate,
					Vni2Uni.convertToUnicode(listData.get(i).getLabourKind()),
					listData.get(i).getDucls() + listData.get(i).getRestPay(),
					listData.get(i).getMaternityLeave(),
					listData.get(i).getPaidVacation(),
					listData.get(i).getAbsentWithoutLeave(),
					listData.get(i).getDayOFF(),
					listData.get(i).getUnpaidVacation(),
					listData.get(i).getSocialInsurance(),
					listData.get(i).getUempmInsurance(), quitDate });

		}
		if (model.getRowCount() > 0) {
			((DefaultListSelectionModel) tblData.getSelectionModel())
					.setSelectedIndex(0, true);
		}
	}

	/**
	 * Display Tab panel, which at the top of table
	 */
	private void loadTabBar() {
		tabBar = new Row();
		tabBar.setInsets(new Insets(3, 3, 3, 0));
		tabBar.setCellSpacing(new Extent(3, Extent.PX));
		SplitPaneLayoutData tabBarLayoutData = new SplitPaneLayoutData();
		tabBarLayoutData.setBackground(new Color(0xd7d7d7));
		tabBarLayoutData.setInsets(new Insets(3, 3, 3, 0));
		tabBar.setLayoutData(tabBarLayoutData);
		splitPane_4.add(tabBar);

		insufficientListTab = new Button();
		insufficientListTab.setText("DS CNV không đủ công");
		insufficientListTab.setBorder(new Border(new Extent(1), Color.BLUE,
				Border.STYLE_SOLID));
		insufficientListTab.setInsets(new Insets(13, 4, 13, 0));
		insufficientListTab.setFont(new Font(Font.ARIAL, Font.BOLD, new Extent(
				12, Extent.PT)));
		insufficientListTab.setHeight(new Extent(25));
		insufficientListTab.setBackground(new Color(0x0080C0));
		insufficientListTab.setForeground(Color.WHITE);
		insufficientListTab.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!INSUFFICIENT_ACTIVE) {
					insufficientListTab.setBackground(new Color(0x0080C0));
					sufficientListTab.setBackground(Color.DARKGRAY);
					listOfAllTab.setBackground(Color.DARKGRAY);
					taxpayerTab.setBackground(Color.DARKGRAY);
					onMatLeaveTab.setBackground(Color.DARKGRAY);

					INSUFFICIENT_ACTIVE = true;
					SUFFICIENT_ACTIVE = false;
					ALL_ACTIVE = false;
					TAXPAYER_ACTIVE = false;
					ONMATLEAVER_ACTIVE = false;
					doShow();
					return;
				}

			}

		});

		tabBar.add(insufficientListTab);

		sufficientListTab = new Button();
		sufficientListTab.setText("DS CNV đủ công");
		sufficientListTab.setBorder(new Border(new Extent(1), Color.BLUE,
				Border.STYLE_SOLID));
		sufficientListTab.setInsets(new Insets(13, 4, 13, 0));
		sufficientListTab.setFont(new Font(Font.ARIAL, Font.BOLD, new Extent(
				12, Extent.PT)));
		sufficientListTab.setHeight(new Extent(25));
		sufficientListTab.setBackground(Color.DARKGRAY);
		sufficientListTab.setForeground(Color.WHITE);

		sufficientListTab.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!SUFFICIENT_ACTIVE) {
					sufficientListTab.setBackground(new Color(0x0080C0));
					insufficientListTab.setBackground(Color.DARKGRAY);
					listOfAllTab.setBackground(Color.DARKGRAY);
					taxpayerTab.setBackground(Color.DARKGRAY);
					onMatLeaveTab.setBackground(Color.DARKGRAY);

					INSUFFICIENT_ACTIVE = false;
					SUFFICIENT_ACTIVE = true;
					ALL_ACTIVE = false;
					TAXPAYER_ACTIVE = false;
					ONMATLEAVER_ACTIVE = false;
					doShow();
					return;
				}

			}

		});

		tabBar.add(sufficientListTab);

		listOfAllTab = new Button();
		listOfAllTab.setText("DS toàn bộ CNV ");
		listOfAllTab.setBorder(new Border(new Extent(1), Color.BLUE,
				Border.STYLE_SOLID));
		listOfAllTab.setInsets(new Insets(13, 4, 13, 0));
		listOfAllTab.setFont(new Font(Font.ARIAL, Font.BOLD, new Extent(12,
				Extent.PT)));
		listOfAllTab.setHeight(new Extent(25));
		listOfAllTab.setBackground(Color.DARKGRAY);
		listOfAllTab.setForeground(Color.WHITE);

		listOfAllTab.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ALL_ACTIVE) {
					listOfAllTab.setBackground(new Color(0x0080C0));
					sufficientListTab.setBackground(Color.DARKGRAY);
					insufficientListTab.setBackground(Color.DARKGRAY);
					taxpayerTab.setBackground(Color.DARKGRAY);
					onMatLeaveTab.setBackground(Color.DARKGRAY);

					INSUFFICIENT_ACTIVE = false;
					SUFFICIENT_ACTIVE = false;
					ALL_ACTIVE = true;
					TAXPAYER_ACTIVE = false;
					ONMATLEAVER_ACTIVE = false;
					doShow();
					return;

				}

			}

		});
		tabBar.add(listOfAllTab);

		taxpayerTab = new Button();
		taxpayerTab.setText("DS CNV có tháng trừ bảo hiểm");
		taxpayerTab.setBorder(new Border(new Extent(1), Color.BLUE,
				Border.STYLE_SOLID));
		taxpayerTab.setInsets(new Insets(13, 4, 13, 0));
		taxpayerTab.setFont(new Font(Font.ARIAL, Font.BOLD, new Extent(12,
				Extent.PT)));
		taxpayerTab.setHeight(new Extent(25));
		taxpayerTab.setBackground(Color.DARKGRAY);
		taxpayerTab.setForeground(Color.WHITE);

		taxpayerTab.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!TAXPAYER_ACTIVE) {
					sufficientListTab.setBackground(Color.DARKGRAY);
					insufficientListTab.setBackground(Color.DARKGRAY);
					listOfAllTab.setBackground(Color.DARKGRAY);
					taxpayerTab.setBackground(new Color(0x0080C0));
					onMatLeaveTab.setBackground(Color.DARKGRAY);

					INSUFFICIENT_ACTIVE = false;
					SUFFICIENT_ACTIVE = false;
					ALL_ACTIVE = false;
					TAXPAYER_ACTIVE = true;
					ONMATLEAVER_ACTIVE = false;

					doShow();
					return;

				}

			}

		});

		tabBar.add(taxpayerTab);

		onMatLeaveTab = new Button();
		onMatLeaveTab.setText("DS CNV nghỉ sản");
		onMatLeaveTab.setBorder(new Border(new Extent(1), Color.BLUE,
				Border.STYLE_SOLID));
		onMatLeaveTab.setInsets(new Insets(13, 4, 13, 0));
		onMatLeaveTab.setFont(new Font(Font.ARIAL, Font.BOLD, new Extent(12,
				Extent.PT)));
		onMatLeaveTab.setHeight(new Extent(25));
		onMatLeaveTab.setBackground(Color.DARKGRAY);
		onMatLeaveTab.setForeground(Color.WHITE);

		onMatLeaveTab.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ONMATLEAVER_ACTIVE) {
					sufficientListTab.setBackground(Color.DARKGRAY);
					insufficientListTab.setBackground(Color.DARKGRAY);
					listOfAllTab.setBackground(Color.DARKGRAY);
					taxpayerTab.setBackground(Color.DARKGRAY);
					onMatLeaveTab.setBackground(new Color(0x0080C0));

					INSUFFICIENT_ACTIVE = false;
					SUFFICIENT_ACTIVE = false;
					ALL_ACTIVE = false;
					TAXPAYER_ACTIVE = false;
					ONMATLEAVER_ACTIVE = true;

					doShow();
					return;

				}

			}

		});

		tabBar.add(onMatLeaveTab);
	}

	protected void doShow() {
		String month = (String) monthSF.getSelectedItem();
		String year = (String) yearSF.getSelectedItem();
		int m = Integer.parseInt(month);
		int y = Integer.parseInt(year);
		Date monthBegin = new Date(y - 1900, m - 1, 1);
		Date monthEnd = new Date(y - 1900, m - 1, ExportInsuranceInfoDAO.getDateOfMonth(m, y));
		if (!allEmpCB.isSelected()) {
			if (dpmtIDSF.getSelectedIndex() != -1) {

				String deptID = (String) dpmtIDSF.getSelectedItem();
				showWithDeptID(month, year, deptID);
			} else {
				if (wsSF.getSelectedIndex() == -1
						&& grSF.getSelectedIndex() == -1
						&& unitSF.getSelectedIndex() == -1) {
					MessageDialog dlg = new MessageDialog("Lổi",
							"Vui lòng chọn đơn vị cần thống kê !!!",
							MessageDialog.CONTROLS_OK);
					Application.getApp().getDefaultWindow().getContent().add(
							dlg);

				} else {
					if (wsSF.getSelectedIndex() != -1) {
						String factName = Vni2Uni.convertToVNI((String) wsSF
								.getSelectedItem());
						showWithDeptName(month, year, factName);
					} else {
						if (grSF.getSelectedIndex() != -1) {
							String groupName = Vni2Uni
									.convertToVNI((String) grSF
											.getSelectedItem());
							showWithGroupName(month, year, groupName);
						} else {
							if (unitSF.getSelectedIndex() != -1) {
								String unitName = Vni2Uni
										.convertToVNI((String) unitSF
												.getSelectedItem());
								showWithUnitName(month, year, unitName);
							}
						}
					}
				}
			}
		} else {
			listData = ExportInsuranceInfoDAO.queryAllList(month, year, INSUFFICIENT_ACTIVE,
					SUFFICIENT_ACTIVE, ALL_ACTIVE, TAXPAYER_ACTIVE,
					ONMATLEAVER_ACTIVE, getUserID().toUpperCase());
			if (quitEmpCB.isSelected()) {
				getQuitList(monthBegin, monthEnd);
			}
			addDataToShow(listData);
		}
	}

	private void getQuitList(Date monthBegin, Date monthEnd) {
		List<EmployeePlain> listAll = new ArrayList<EmployeePlain>(listData);
		listData.clear();
		for (int i = 0; i < listAll.size(); i++) {
			if (listAll.get(i).getQuitDay() != null) {
				if (listAll.get(i).getQuitDay().getTime() >= monthBegin
						.getTime()
						&& listAll.get(i).getQuitDay().getTime() <= monthEnd
								.getTime()) {
					listData.add(listAll.get(i));
				}
			}
		}
	}

	private void showWithUnitName(String month, String year, String unitName) {
		int m = Integer.parseInt(month);
		int y = Integer.parseInt(year);
		Date monthBegin = new Date(y - 1900, m - 1, 1);
		Date monthEnd = new Date(y - 1900, m - 1, ExportInsuranceInfoDAO.getDateOfMonth(m, y));

		listData = ExportInsuranceInfoDAO.queryWithUnitName(month, year, unitName,
				INSUFFICIENT_ACTIVE, SUFFICIENT_ACTIVE, ALL_ACTIVE,
				TAXPAYER_ACTIVE, ONMATLEAVER_ACTIVE);
		if (quitEmpCB.isSelected()) {
			getQuitList(monthBegin, monthEnd);
		}
		addDataToShow(listData);
	}

	private void showWithGroupName(String month, String year, String groupName) {
		int m = Integer.parseInt(month);
		int y = Integer.parseInt(year);
		Date monthBegin = new Date(y - 1900, m - 1, 1);
		Date monthEnd = new Date(y - 1900, m - 1, ExportInsuranceInfoDAO.getDateOfMonth(m, y));
		if (unitSF.getSelectedIndex() != -1) {
			String unitName = Vni2Uni.convertToVNI((String) unitSF
					.getSelectedItem());
			listData = ExportInsuranceInfoDAO.queryWithGroupName(month, year, groupName, unitName,
					INSUFFICIENT_ACTIVE, SUFFICIENT_ACTIVE, ALL_ACTIVE,
					TAXPAYER_ACTIVE, ONMATLEAVER_ACTIVE);

		} else {
			listData = ExportInsuranceInfoDAO.queryWithGroupName(month, year, groupName,
					INSUFFICIENT_ACTIVE, SUFFICIENT_ACTIVE, ALL_ACTIVE,
					TAXPAYER_ACTIVE, ONMATLEAVER_ACTIVE);
		}
		if (quitEmpCB.isSelected()) {
			getQuitList(monthBegin, monthEnd);
		}
		addDataToShow(listData);
	}

	private void showWithDeptName(String month, String year, String factName) {
		int m = Integer.parseInt(month);
		int y = Integer.parseInt(year);
		Date monthBegin = new Date(y - 1900, m - 1, 1);
		Date monthEnd = new Date(y - 1900, m - 1, ExportInsuranceInfoDAO.getDateOfMonth(m, y));

		if (grSF.getSelectedIndex() != -1) {
			String groupName = Vni2Uni.convertToVNI((String) grSF
					.getSelectedItem());
			if (unitSF.getSelectedIndex() != -1) {
				String unitName = Vni2Uni.convertToVNI((String) unitSF
						.getSelectedItem());
				listData = ExportInsuranceInfoDAO.queryWithDeptName(month, year, factName,
						groupName, unitName, INSUFFICIENT_ACTIVE,
						SUFFICIENT_ACTIVE, ALL_ACTIVE, TAXPAYER_ACTIVE,
						ONMATLEAVER_ACTIVE);
				if (quitEmpCB.isSelected()) {
					getQuitList(monthBegin, monthEnd);
				}
				addDataToShow(listData);
			} else {
				listData = ExportInsuranceInfoDAO.queryWithDeptName(month, year, factName,
						groupName, INSUFFICIENT_ACTIVE, SUFFICIENT_ACTIVE,
						ALL_ACTIVE, TAXPAYER_ACTIVE, ONMATLEAVER_ACTIVE);
				if (quitEmpCB.isSelected()) {
					getQuitList(monthBegin, monthEnd);
				}
				addDataToShow(listData);
			}
		} else {

			listData = ExportInsuranceInfoDAO.queryWithDeptName(month, year, factName,
					INSUFFICIENT_ACTIVE, SUFFICIENT_ACTIVE, ALL_ACTIVE,
					TAXPAYER_ACTIVE, ONMATLEAVER_ACTIVE);
			if (quitEmpCB.isSelected()) {
				getQuitList(monthBegin, monthEnd);
			}
			addDataToShow(listData);
		}
	}

	private void showWithDeptID(String month, String year, String deptID) {
		int m = Integer.parseInt(month);
		int y = Integer.parseInt(year);
		Date monthBegin = new Date(y - 1900, m - 1, 1);
		Date monthEnd = new Date(y - 1900, m - 1, ExportInsuranceInfoDAO.getDateOfMonth(m, y));
		listData = ExportInsuranceInfoDAO.queryWithDeptID(month, year, deptID,
				INSUFFICIENT_ACTIVE, SUFFICIENT_ACTIVE, ALL_ACTIVE,
				TAXPAYER_ACTIVE, ONMATLEAVER_ACTIVE);
		if (quitEmpCB.isSelected()) {
			getQuitList(monthBegin, monthEnd);
		}
		addDataToShow(listData);
	}

	private void loadOptionBar() {
		searchOption = new Grid(2);
		searchOption.setInsets(new Insets(3, 6));
		SplitPaneLayoutData searchOpLayoutData = new SplitPaneLayoutData();
		searchOpLayoutData.setBackground(new Color(0xd7d7d7));
		searchOpLayoutData.setInsets(new Insets(new Extent(3, Extent.PX)));
		searchOption.setLayoutData(searchOpLayoutData);
		splitPane_3.add(searchOption);

		monthLbl = new Label();
		monthLbl.setText("Tháng");
		searchOption.add(monthLbl);

		monthSF = new SelectField();
		monthSF.setWidth(new Extent(150, Extent.PX));
		monthSF.setHeight(new Extent(25, Extent.PX));
		int currentMonth = new Date().getMonth();
		if (new Date().getDate() > 10) {
			currentMonth++;
		}
		final DefaultListModel monthModel = new DefaultListModel();
		for (int i = 1; i <= currentMonth; i++) {
			if (i < 10)
				monthModel.add("0" + i);
			else
				monthModel.add(i + "");
		}
		monthSF.setModel(monthModel);
		if (currentMonth < 10)
			monthSF.setSelectedIndex(monthModel.indexOf("0" + currentMonth));
		else
			monthSF.setSelectedIndex(monthModel.indexOf(currentMonth + ""));
		searchOption.add(monthSF);

		yearLbl = new Label();
		yearLbl.setText("Năm");

		searchOption.add(yearLbl);

		yearSF = new SelectField();
		yearSF.setWidth(new Extent(150, Extent.PX));
		yearSF.setHeight(new Extent(25, Extent.PX));
		DefaultListModel yearModel = new DefaultListModel();
		final int currentYear = new Date().getYear() + 1900;

		for (int i = currentYear; i > currentYear - 10; i--) {
			yearModel.add(i + "");
		}
		yearSF.setModel(yearModel);
		yearSF.setSelectedIndex(yearModel.indexOf(currentYear + ""));
		yearSF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String year = (String) yearSF.getSelectedItem();
				if (Integer.parseInt(year) == currentYear) {

					int currentMonth = new Date().getMonth();
					if (new Date().getDate() > 10) {
						currentMonth++;
					}
					monthModel.removeAll();
					for (int i = 1; i <= currentMonth; i++) {
						if (i < 10)
							monthModel.add("0" + i);
						else
							monthModel.add(i + "");
					}
				} else {
					monthModel.removeAll();
					for (int i = 1; i <= 12; i++) {
						if (i < 10)
							monthModel.add("0" + i);
						else
							monthModel.add(i + "");
					}
					monthSF.setSelectedIndex(monthModel.indexOf("01"));
				}
			}

		});
		searchOption.add(yearSF);

		dpmtAllLbl = new Label();
		dpmtAllLbl.setText("Lựa chọn đơn vị");
		GridLayoutData dpmtAllLayoutData = new GridLayoutData();
		dpmtAllLayoutData.setColumnSpan(2);
		dpmtAllLbl.setLayoutData(dpmtAllLayoutData);
		searchOption.add(dpmtAllLbl);

		dpmtIDLbl = new Label();
		dpmtIDLbl.setText("Mã đơn vị");
		searchOption.add(dpmtIDLbl);

		dpmtIDSF = new SelectField();
		dpmtIDSF.setWidth(new Extent(150, Extent.PX));
		dpmtIDSF.setHeight(new Extent(25, Extent.PX));
		DefaultListModel dpmtIDModel = new DefaultListModel();
		List<String> dpmtIDList = ExportInsuranceInfoDAO.queryAllDeptID(getUserID().toUpperCase());
		System.out.println(dpmtIDList.size());
		for (int i = 0; i < dpmtIDList.size(); i++) {
			dpmtIDModel.add(dpmtIDList.get(i));
		}
		dpmtIDSF.setModel(dpmtIDModel);
		dpmtIDSF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String dpmtID = (String) dpmtIDSF.getSelectedItem();
				Map<String, String> dpmt = ExportInsuranceInfoDAO.queryUnitByDeptID(dpmtID);
				wsSF.setSelectedIndex(((DefaultListModel) wsSF.getModel())
						.indexOf(dpmt.get("name_fact")));
				grSF.setSelectedIndex(((DefaultListModel) grSF.getModel())
						.indexOf(dpmt.get("name_group")));
				unitSF.setSelectedIndex(((DefaultListModel) unitSF.getModel())
						.indexOf(dpmt.get("name_dept_name")));

			}

		});
		searchOption.add(dpmtIDSF);

		wsLbl = new Label();
		wsLbl.setText("Xưởng");
		searchOption.add(wsLbl);

		wsSF = new SelectField();
		wsSF.setWidth(new Extent(150, Extent.PX));
		wsSF.setHeight(new Extent(25, Extent.PX));

		searchOption.add(wsSF);

		grLbl = new Label();
		grLbl.setText("Nhóm");
		searchOption.add(grLbl);

		grSF = new SelectField();
		grSF.setWidth(new Extent(150, Extent.PX));
		grSF.setHeight(new Extent(25, Extent.PX));

		searchOption.add(grSF);

		unitLbl = new Label();
		unitLbl.setText("Đơn vị");
		searchOption.add(unitLbl);

		unitSF = new SelectField();
		unitSF.setWidth(new Extent(150, Extent.PX));
		unitSF.setHeight(new Extent(25, Extent.PX));

		loadAllSelectFieldModel();

		searchOption.add(unitSF);

		allEmpCB = new CheckBox();
		allEmpCB.setText("Toàn bộ công ty");
		allEmpCB.setLayoutData(dpmtAllLayoutData);
		allEmpCB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (allEmpCB.isSelected()) {
					dpmtIDSF.setEnabled(false);
					wsSF.setEnabled(false);
					grSF.setEnabled(false);
					unitSF.setEnabled(false);
				} else {
					dpmtIDSF.setEnabled(true);
					wsSF.setEnabled(true);
					grSF.setEnabled(true);
					unitSF.setEnabled(true);
				}
				dpmtIDSF.setSelectedIndex(-1);
			}

		});

		searchOption.add(allEmpCB);

		quitEmpCB = new CheckBox();
		quitEmpCB.setText("Nhân viên nghỉ việc");
		quitEmpCB.setLayoutData(dpmtAllLayoutData);
		searchOption.add(quitEmpCB);

		wsSF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String factName = (String) wsSF.getSelectedItem();
				List<String> groupNameList = ExportInsuranceInfoDAO
						.queryGroupWithFactName(factName);
				DefaultListModel groupNameModel = new DefaultListModel();
				if (groupNameList.size() > 0) {
					for (int i = 0; i < groupNameList.size(); i++) {
						if (groupNameList.get(i) != null) {
							groupNameModel.add(Vni2Uni.convertToUnicode(groupNameList.get(i)));
						}
					}

				}
				grSF.setModel(groupNameModel);
				grSF.setSelectedIndex(-1);
				dpmtIDSF.setSelectedIndex(-1);
				unitSF.setModel(new DefaultListModel());
				unitSF.setSelectedIndex(-1);
			}
		});

		grSF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String groupName = (String) grSF.getSelectedItem();
				List<String> deptNameList = ExportInsuranceInfoDAO.queryUnitsWithGroup(groupName);
				DefaultListModel deptNameModel = new DefaultListModel();
				if (deptNameList.size() > 0) {
					for (int i = 0; i < deptNameList.size(); i++) {
						if (deptNameList.get(i) != null) {
							deptNameModel.add(Vni2Uni.convertToUnicode(deptNameList.get(i)));
						}
					}
				}
				unitSF.setModel(deptNameModel);
				unitSF.setSelectedIndex(-1);
				dpmtIDSF.setSelectedIndex(-1);
			}
		});

	}

	private void loadAllSelectFieldModel() {
		DefaultListModel factNameModel = new DefaultListModel();
		List<String> factNameList = ExportInsuranceInfoDAO.queryAllFactName(getUserID()
				.toUpperCase());
		for (int i = 0; i < factNameList.size(); i++) {
			if (factNameList.get(i) != null)
				factNameModel.add(Vni2Uni.convertToUnicode(factNameList.get(i)));
		}
		wsSF.setModel(factNameModel);
		DefaultListModel groupNameModel = new DefaultListModel();

		List<String> groupNameList = ExportInsuranceInfoDAO.queryAllGroupName(getUserID()
				.toUpperCase());
		for (int i = 0; i < groupNameList.size(); i++) {
			if (groupNameList.get(i) != null)
				groupNameModel.add(Vni2Uni.convertToUnicode(groupNameList.get(i)));
		}
		grSF.setModel(groupNameModel);

		DefaultListModel deptNameModel = new DefaultListModel();
		List<String> unitNameList = ExportInsuranceInfoDAO.queryAllUnitName(getUserID()
				.toUpperCase());
		for (int i = 0; i < unitNameList.size(); i++) {
			if (unitNameList.get(i) != null)
				deptNameModel.add(Vni2Uni.convertToUnicode(unitNameList.get(i)));
		}
		unitSF.setModel(deptNameModel);

	}

	private String getUserID() {
		Application app = (Application) ApplicationInstance.getActive();
		LoginInfo loginInfo = app.getLoginInfo();
		return loginInfo.getUserID();
	}

	@Override
	protected void doRefresh() {
		doShow();
	}

	@Override
	protected void doSearch() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doReset() {
		dpmtIDSF.setSelectedIndex(-1);
		wsSF.setSelectedIndex(-1);
		grSF.setSelectedIndex(-1);
		unitSF.setSelectedIndex(-1);
		loadAllSelectFieldModel();
		listData = new ArrayList<EmployeePlain>();
		addDataToShow(listData);
	}

	@Override
	protected HSSFWorkbook generateWorkbook() throws IOException {
		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance",
				"DSNgayCongBH_Temp.xls");
		;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int startRow = 1;

		for (int i = 0; i < listData.size(); i++) {

			String quitDate = "";
			if (listData.get(i).getQuitDay() != null) {
				quitDate = new SimpleDateFormat("dd-MM-yyyy").format(listData
						.get(i).getQuitDay());
			}
			String signDate = "";
			if (listData.get(i).getSignDate() != null) {
				signDate = new SimpleDateFormat("dd-MM-yyyy").format(listData
						.get(i).getSignDate());
			}
			EmployeePlain emp = listData.get(i);
			row = sheet.createRow(startRow++);
			cell = row.createCell(0);
			cell.setCellValue(i + 1);

			cell = row.createCell(1);
			cell.setCellValue(emp.getEmpsn());

			cell = row.createCell(2);
			cell.setCellValue(emp.getFname());

			cell = row.createCell(3);
			cell.setCellValue(emp.getLname());

			cell = row.createCell(4);
			cell.setCellValue(emp.getPositionBonus());

			cell = row.createCell(5);
			cell.setCellValue(emp.getDeptID());
			cell = row.createCell(6);
			cell.setCellValue(emp.getDeptName());
			cell = row.createCell(7);
			cell.setCellValue(emp.getBasicSalary());
			cell = row.createCell(8);
			cell.setCellValue(emp.getComSalary());
			cell = row.createCell(9);
			cell.setCellValue(signDate);
			cell = row.createCell(10);
			cell.setCellValue(emp.getLabourKind());
			cell = row.createCell(11);
			cell.setCellValue(emp.getDucls() + emp.getRestPay());
			cell = row.createCell(12);
			cell.setCellValue(emp.getMaternityLeave());
			cell = row.createCell(13);
			cell.setCellValue(emp.getPaidVacation());
			cell = row.createCell(14);
			cell.setCellValue(emp.getAbsentWithoutLeave());
			cell = row.createCell(15);
			cell.setCellValue(emp.getDayOFF());
			cell = row.createCell(16);
			cell.setCellValue(emp.getUnpaidVacation());
			cell = row.createCell(17);
			cell.setCellValue(emp.getSocialInsurance());
			cell = row.createCell(18);
			cell.setCellValue(emp.getUempmInsurance());
			cell = row.createCell(19);
			cell.setCellValue(quitDate);

		}
		// Save excel file in disk "D:/"
		String filename =  getEXCEL_FILE_TITLE();
		setReportFileName(filename);
		return wb;
	}

	private String getEXCEL_FILE_TITLE() {
		if (SUFFICIENT_ACTIVE) {
			return "DS_CNV_" + (String) dpmtIDSF.getSelectedItem()
					+ "_duDKmuaBHXH-TN_"
					+ (String) monthSF.getSelectedItem() + "_"
					+ (String) yearSF.getSelectedItem();
		}
		if (INSUFFICIENT_ACTIVE) {
			return "DS_CNV_" + (String) dpmtIDSF.getSelectedItem()
					+ "_koduDKmuaBHXH-TN_"
					+ (String) monthSF.getSelectedItem() + "_"
					+ (String) yearSF.getSelectedItem();
		}
		if (ALL_ACTIVE) {
			return "DS_CNV_" + (String) dpmtIDSF.getSelectedItem()
					+ "_toanbo_" + (String) monthSF.getSelectedItem() + "_"
					+ (String) yearSF.getSelectedItem();
		}
		if (TAXPAYER_ACTIVE) {
			return "DS_CNV_" + (String) dpmtIDSF.getSelectedItem()
					+ "_truBHtrongthang_"
					+ (String) monthSF.getSelectedItem() + "_"
					+ (String) yearSF.getSelectedItem();
		}
		if (ONMATLEAVER_ACTIVE) {
			return "DS_CNV_" + (String) dpmtIDSF.getSelectedItem()
					+ "_nghisan_" + (String) monthSF.getSelectedItem() + "_"
					+ (String) yearSF.getSelectedItem();
		}
		return "";
	}

	@Override
	protected void doToolbarActionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ReportToolbar.CMD_SEARH)) {
			doSearch();
		} else if (e.getActionCommand().equals(ReportToolbar.CMD_REFRESH)) {
			doRefresh();
		} else if (e.getActionCommand().equals(ReportToolbar.CMD_EXCEL)) {

			if (!validateUI()) {
				Application.getApp().showMessageDialog(
						MessageDialog.CONTROLS_OK + MessageDialog.TYPE_ERROR,
						getErrorMessage());
				return;
			}

			try {
				if (listData == null

						|| (listData != null && wsSF.getSelectedIndex() == -1
								&& grSF.getSelectedIndex() == -1
								&& unitSF.getSelectedIndex() == -1 && dpmtIDSF
								.getSelectedIndex() == -1)) {
					MessageDialog dlg = new MessageDialog(
							"Thông báo",
							" Chạy báo cáo, hiển thị trên màn hình, sau đó vui lòng nhấn nút \"Xuất file excel\" lần nữa !!",
							MessageDialog.CONTROLS_OK);
					dlg.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							doShow();
						}

					});
					Application.getApp().getDefaultWindow().getContent().add(
							dlg);
				}

				else {
					if (ALL_ACTIVE) {
						MessageDialog dlg = new MessageDialog("Thông báo",
								"Bạn có muốn cập nhật ký trình BHXH_TN ?",
								MessageDialog.CONTROLS_YES_NO);
						dlg.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if (e.getActionCommand().equals(
										MessageDialog.COMMAND_OK)) {
									updateStatusReport(e);
								}
								if (e.getActionCommand().equals(
										MessageDialog.COMMAND_CANCEL)) {
									try {
										doExportExcel(generateWorkbook());
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							}

							private void updateStatusReport(ActionEvent e) {
								String month = (String) monthSF
										.getSelectedItem();
								String year = (String) yearSF.getSelectedItem();
								int m = Integer.parseInt(month);
								int y = Integer.parseInt(year);
								if (wsSF.getSelectedIndex() != -1) {
									String factName = (String) wsSF
											.getSelectedItem();
									try {
										doExportExcel(generateWorkbook());
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									int status = ExportInsuranceInfoDAO.getStatusReport(factName,
											month, year);

									if (status != -1 && status != 1) {
										for (int i = 0; i < listData.size(); i++) {

											EmployeePlain emp = listData.get(i);
											ExportInsuranceInfoDAO
													.saveSocialStatusReport(
															emp.getEmpsn(),
															new Date(y - 1900,
																	m - 1, 1),
															ExportInsuranceInfoDAO
																	.getComSalary(
																			emp
																					.getEmpsn(),
																			year,
																			month),
															"UP",
															ExportInsuranceInfoDAO
																	.getUserNo(getUserID().toUpperCase()),
															new Date());
										}
										ExportInsuranceInfoDAO.updateStatusReport(factName, month,
												year);
									}
									if (status == -1) {
										for (int i = 0; i < listData.size(); i++) {

											EmployeePlain emp = listData.get(i);
											ExportInsuranceInfoDAO
													.saveSocialStatusReport(
															emp.getEmpsn(),
															new Date(y - 1900,
																	m - 1, 1),
															ExportInsuranceInfoDAO
																	.getComSalary(
																			emp
																					.getEmpsn(),
																			year,
																			month),
															"UP",
															ExportInsuranceInfoDAO
																	.getUserNo(getUserID().toUpperCase()),
															new Date());
										}
										ExportInsuranceInfoDAO
												.insertStatusReport(
														factName,
														month,
														year,
														ExportInsuranceInfoDAO
																.getUserNo(getUserID().toUpperCase()));
									}

								} else {
									if (allEmpCB.isSelected()) {
										try {
											doExportExcel(generateWorkbook());
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										int status = ExportInsuranceInfoDAO.getStatusReport("ALL",
												month, year);
										if (status != -1 && status != 1) {
											for (int i = 0; i < listData.size(); i++) {

												EmployeePlain emp = listData
														.get(i);
												ExportInsuranceInfoDAO
														.saveSocialStatusReport(
																emp.getEmpsn(),
																new Date(
																		y - 1900,
																		m - 1,
																		1),
																ExportInsuranceInfoDAO
																		.getComSalary(
																				emp
																						.getEmpsn(),
																				year,
																				month),
																"UP",
																ExportInsuranceInfoDAO
																		.getUserNo(getUserID().toUpperCase()),
																new Date());
											}
											ExportInsuranceInfoDAO.updateStatusReport("ALL",
													month, year);
										}
										if (status == -1) {
											for (int i = 0; i < listData.size(); i++) {

												EmployeePlain emp = listData
														.get(i);
												ExportInsuranceInfoDAO
														.saveSocialStatusReport(
																emp.getEmpsn(),
																new Date(
																		y - 1900,
																		m - 1,
																		1),
																ExportInsuranceInfoDAO
																		.getComSalary(
																				emp
																						.getEmpsn(),
																				year,
																				month),
																"UP",
																ExportInsuranceInfoDAO
																		.getUserNo(getUserID().toUpperCase()),
																new Date());
											}
											ExportInsuranceInfoDAO
													.insertStatusReport(
															"ALL",
															month,
															year,
															ExportInsuranceInfoDAO
																	.getUserNo(getUserID().toUpperCase()));
										}
									}
								}
							}
						});
						Application.getApp().getDefaultWindow().getContent()
								.add(dlg);

					} else {
						doExportExcel(generateWorkbook());
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (e.getActionCommand().equals(ReportToolbar.CMD_PDF)) {
			
		} else if (e.getActionCommand().equals(ReportToolbar.CMD_RESET)) {
			doReset();
		}
	}

	private void doExportExcel(HSSFWorkbook wb) throws IOException {
		if (wb == null) {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Không có dữ liệu.");
			return;
		}
		File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
		f.deleteOnExit();
		FileOutputStream out = new FileOutputStream(f);
		wb.write(out);
		out.flush();
		out.close();
		File saveFile = new File(f.getParentFile(), URLEncoder.encode(
				getLoginInfo().getUserID() + ";application/vnd.ms-excel;"
						+ getReportFileName() + "_"
						+ Calendar.getInstance().getTimeInMillis() + ".xls",
				"UTF-8"));
		ReportFileManager.saveTempReportFile(f, saveFile);
		saveFile.deleteOnExit();
		Application.getApp()
				.enqueueCommand(
						new BrowserRedirectCommand(getViewerUrl()
								+ saveFile.getName()));
	}
}
