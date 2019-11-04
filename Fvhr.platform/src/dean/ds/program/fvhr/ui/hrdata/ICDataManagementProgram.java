package ds.program.fvhr.ui.hrdata;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ListBox;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import ds.program.fvhr.dao.salary.SalaryDAO;
import fv.components.SelectItem;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.MappingPropertyUtils;
import fv.util.ReportUtils;
import fv.util.Vni2Uni;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.DefaultProgram;

public class ICDataManagementProgram extends DefaultProgram {
	private static final long serialVersionUID = 1L;

	private SelectField sfDay;

	private SelectField sfMonth;

	private SelectField sfYear;

	private SelectField sfFact;

	private SelectField sfLean;

	private SelectField sfDept;

	private Button btnXuLyDuLieuThang;

	private Button btnInDuLieuThang;

	private Button btnXuLyDuLieuNgay;

	private Button btnInDuLieuNgay;

	private Button btnChuyenDuLieu;

	private Button btnReset;

	private ListBox lstList;

	private DscField txtEmpsn;

	private boolean empFromList = false;

	private SalaryDAO dao;

	private List<String> list;

	public ICDataManagementProgram() {
		super();
		initComponents();
		bindUIAction();
	}

	private SalaryDAO getSalaryDAO() {
		if (dao == null)
			dao = new SalaryDAO();
		dao.setMonth(sfMonth.getSelectedItem().toString());
		dao.setYear(sfYear.getSelectedItem().toString());
		return dao;
	}

	private void bindUIAction() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		ListBinder.bindSelectField(sfDay, MappingPropertyUtils
				.getDayEditor(month), true);
		sfDay.setSelectedIndex(c.get(Calendar.DAY_OF_MONTH) - 1);
		ListBinder.bindSelectField(sfMonth, MappingPropertyUtils
				.getJavaMonthEditor(), true);
		sfMonth.setSelectedIndex(month);
		ListBinder.bindSelectField(sfYear, MappingPropertyUtils.getYearEditor(
				1, false), true);
		sfYear.setSelectedIndex(1);
		ListBinder.bindSelectField(sfLean, FVGenericInfo.getAllGroup(), true);
		ListBinder.bindSelectField(sfFact, FVGenericInfo.getFactories(), false);
		sfFact.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (empFromList) {
					empFromList = false;
					((DefaultListModel) lstList.getModel()).removeAll();
					txtEmpsn.setText("");
				}
				ListBinder.bindSelectField(sfLean, FVGenericInfo
						.getGroup(sfFact.getSelectedItem().toString()), true);
				ListBinder
						.bindSelectField(sfDept, FVGenericInfo
								.getDeptName(sfFact.getSelectedItem()
										.toString()), true);
			}
		});
		sfLean.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (empFromList) {
					empFromList = false;
					((DefaultListModel) lstList.getModel()).removeAll();
					txtEmpsn.setText("");
				}
				if (sfFact.getSelectedIndex() < 0) {
					SelectItem item = (SelectItem) sfLean.getSelectedItem();
					String fact = FVGenericInfo.findFact(item.getValue()
							.toString());
					ListBinder.refreshIndex(sfFact, fact);
					ListBinder.bindSelectField(sfLean, FVGenericInfo
							.getGroup(sfFact.getSelectedItem().toString()),
							true);
					ListBinder.refreshIndex(sfLean, item.getValue());
					ListBinder.bindSelectField(sfDept, FVGenericInfo
							.getDeptName(fact, sfLean.getSelectedItem()
									.toString()), true);
				} else {
					ListBinder.bindSelectField(sfDept, FVGenericInfo
							.getDeptName(sfFact.getSelectedItem().toString(),
									sfLean.getSelectedItem().toString()), true);
				}
			}
		});
		sfDept.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (empFromList) {
					empFromList = false;
					((DefaultListModel) lstList.getModel()).removeAll();
					txtEmpsn.setText("");
				}
				if (sfLean.getSelectedIndex() < 0) {
					String fact = sfFact.getSelectedItem().toString();
					SelectItem item = (SelectItem) sfDept.getSelectedItem();
//					String group = FVGenericInfo.findGroupByDeptName(fact, item
//							.getValue().toString());
//					ListBinder.refreshIndex(sfLean, group);
//					ListBinder.bindSelectField(sfDept, FVGenericInfo
//							.getDeptName(fact, group), true);
//					ListBinder.refreshIndex(sfDept, item.getValue().toString());
				}
			}
		});
		sfMonth.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				ListBinder.bindSelectField(sfDay, MappingPropertyUtils
						.getDayEditor(sfMonth.getSelectedIndex()), true);
				sfDay.setSelectedIndex(0);
			}
		});
		btnChuyenDuLieu.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!validateForm(true)){
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, getErrorMessage());
					return;
				}
				getSalaryDAO().transferICData(list);
				lstList.setModel(convertListToModel(list));
			}
		});
		btnXuLyDuLieuThang.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!validateForm(true)){
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, getErrorMessage());
					return;
				}
				String dateStr = sfDay.getSelectedItem().toString()+"/"+sfMonth.getSelectedItem().toString()+"/"+sfYear.getSelectedItem().toString();
				getSalaryDAO().processData(list, dateStr, "mm");
			}
		});

		btnInDuLieuThang.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!validateForm(false)){
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, getErrorMessage());
					return;
				}
				List<ICMasterData> listData;
				listData = new ArrayList<ICMasterData>();
//				if (!empFromList)
//					listData = getSalaryDAO().getDataList(getFactCondition());
//				else
//					listData = getSalaryDAO().getDataList(list);
				for (ICMasterData data:listData){
					data.setFULLNAME(Vni2Uni.convertToUnicode(data.getFULLNAME()));
					data.setNAME_DEPT(Vni2Uni.convertToUnicode(data.getNAME_DEPT()));
				}
				JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listData);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("mon", sfMonth.getSelectedItem().toString());
				params.put("yy", sfYear.getSelectedItem().toString());
				params.put("SUBREPORT_DIR", ReportFileManager.getReportFormatFolder("fvhr").getAbsolutePath()+"/");
				params.put("USER_ID", getLoginInfo().getUserID());
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.MONTH, sfMonth.getSelectedIndex());
				cal.set(Calendar.YEAR, (Integer)ListBinder.get(sfYear));
				int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				int col_break = days/2;
				if (days%2>0) col_break=col_break+1;
				params.put("col_break", col_break);
				params.put("REPORT_CONNECTION", Application.getApp().getConnection());
				try {
					JasperDesign jd = JRXmlLoader.load(ReportFileManager.getReportFormatFolder("fvhr/bangchamcong.jrxml"));
					JasperReport jr = JasperCompileManager.compileReport(jd);
					JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);
					ReportUtils.doExportPdf(jp, "BangChamCong");
//					ReportUtils.doExportHtml(jp, "D:/test.html");
				} catch (JRException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnXuLyDuLieuNgay.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!validateForm(true)){
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, getErrorMessage());
					return;
				}
				String dateStr = sfDay.getSelectedItem().toString()+"/"+sfMonth.getSelectedItem().toString()+"/"+sfYear.getSelectedItem().toString();
				getSalaryDAO().processData(list, dateStr, "dd");
			}
		});

		btnInDuLieuNgay.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Application.getApp().showMessageDialog(
						MessageDialog.CONTROLS_OK
								+ MessageDialog.TYPE_INFORMATION,
						"Chưa sử dụng được");
			}
		});

		txtEmpsn.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultListModel model = (DefaultListModel) lstList.getModel();
				if (!empFromList) {
					empFromList = true;
					model.removeAll();
					if (sfFact.getSelectedIndex() >= 0) {
						sfFact.setSelectedIndex(-1);
					}
					if (sfLean.getSelectedIndex() >= 0) {
						sfLean.setSelectedIndex(-1);
					}
					if (sfDept.getSelectedIndex() >= 0) {
						sfDept.setSelectedIndex(-1);
					}
				}
				String empsn = txtEmpsn.getText().trim();
				if (!empsn.matches("[0-9]{8}")) {
					Application.getApp().showMessageDialog(
							MessageDialog.CONTROLS_OK
									+ MessageDialog.TYPE_ERROR,
							"Số thẻ không hợp lệ.");
					return;
				}

				if (getSalaryDAO().checkEmpsn(empsn)) {
//					if (!Permission.hasEmpsnPermission(empsn)) {
//						Application.getApp().showMessageDialog(
//								MessageDialog.CONTROLS_OK
//										+ MessageDialog.TYPE_ERROR,
//								"Anh/Chị không có quyền xử lý số thẻ này.");
//						return;
//					}
					if (model.indexOf(empsn) < 0)
						model.add(empsn);
					txtEmpsn.requestFocus();
				} else {
					Application.getApp().showMessageDialog(
							MessageDialog.CONTROLS_OK
									+ MessageDialog.TYPE_ERROR,
							"Số thẻ không có trong hệ thống hoặc đã nghỉ việc");
				}
			}
		});

		btnReset.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				txtEmpsn.setText("");
				sfFact.setSelectedIndex(-1);
				sfLean.setSelectedIndex(-1);
				sfDept.setSelectedIndex(-1);
				((DefaultListModel) lstList.getModel()).removeAll();
				empFromList = false;
			}
		});

	}
	
	private boolean validateForm(boolean checkLock){
		int n = 0;
		if (!empFromList) {
			String factCondition = getFactCondition();
			// System.out.println(factCondition);
			if (factCondition.equals("")) {
				setErrorMessage("Chọn xưởng hoặc đơn vị hoặc nhập danh sách số thẻ cần thao tác");
				return false;
			}
			// check permission
			if (!checkPermisstion()) {
				setErrorMessage("Anh/Chị không có quyền xử lý "	+ getConditionStr() + ".");
				return false;
			}
			list = getSalaryDAO().getEmpList(factCondition);
			if (checkLock)
				n = getSalaryDAO().checkLock(factCondition);
		} else {
			list = new ArrayList<String>();
			DefaultListModel model = (DefaultListModel) lstList
					.getModel();
			for (int i = 0; i < model.size(); i++) {
				list.add(model.get(i).toString());
			}
			if (checkLock)
				n = getSalaryDAO().checkLock(list);
		}

		if (checkLock)
			if (n > 0) {
				setErrorMessage("Danh sách cần thao tác có "
										+ n
										+ " số thẻ đã bị khóa.\r\nYêu cầu kế toán mở dữ liệu.");
				return false;
			}
		return true;
	}

	private DefaultListModel convertListToModel(List<String> list) {
		DefaultListModel model = new DefaultListModel();
		for (String s : list) {
			model.add(s);
		}
		return model;
	}

	private String getFactCondition() {
		String s = "";
		if (sfDept.getSelectedIndex() >= 0) {
			SelectItem item = (SelectItem) sfDept.getSelectedItem();
			SelectItem gitem = (SelectItem) sfLean.getSelectedItem();
			s = " and d.name_fact='" + sfFact.getSelectedItem().toString()
					+ "' and d.name_group='" + gitem.getValue().toString()
					+ "' and d.name_dept_name='" + item.getValue().toString()
					+ "' ";
		} else if (sfLean.getSelectedIndex() >= 0) {
			SelectItem item = (SelectItem) sfLean.getSelectedItem();
			s = " and d.name_fact='" + sfFact.getSelectedItem().toString()
					+ "' and d.name_group='" + item.getValue() + "' ";
		} else if (sfFact.getSelectedIndex() >= 0) {
			s = " and d.name_fact='" + sfFact.getSelectedItem().toString()
					+ "' ";
		}
		return s;
	}

	private String getConditionStr() {
		String s = "";
		if (sfDept.getSelectedIndex() >= 0) {
			s = "đơn vị " + sfFact.getSelectedItem().toString() + "/"
					+ sfLean.getSelectedItem().toString() + "/"
					+ sfDept.getSelectedItem().toString();
		} else if (sfLean.getSelectedIndex() >= 0) {
			s = "lean " + sfFact.getSelectedItem().toString() + "/"
					+ sfLean.getSelectedItem().toString();
		} else if (sfFact.getSelectedIndex() >= 0) {
			s = "xưởng " + sfFact.getSelectedItem().toString();
		}
		return s;
	}

	private boolean checkPermisstion() {
//		if (sfDept.getSelectedIndex() >= 0) {
//			SelectItem item = (SelectItem) sfDept.getSelectedItem();
//			SelectItem gitem = (SelectItem) sfLean.getSelectedItem();
//			return Permission.checkDeptName(
//					sfFact.getSelectedItem().toString(), gitem.getValue()
//							.toString(), item.getValue().toString());
//		} else if (sfLean.getSelectedIndex() >= 0) {
//			SelectItem item = (SelectItem) sfLean.getSelectedItem();
//			return Permission.checkGroup(sfFact.getSelectedItem().toString(),
//					item.getValue().toString());
//		} else if (sfFact.getSelectedIndex() >= 0) {
//			return Permission.checkFact(sfFact.getSelectedItem().toString());
//		}
		return false;
	}

	private void initComponents() {
		SplitPane mainSplitPane = new SplitPane(
				SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		mainSplitPane.setSeparatorPosition(new Extent(30));
		add(mainSplitPane);
		// ---------------
		Row headerContent = new Row();
		headerContent.setInsets(new Insets(5, 2, 2, 2));
		headerContent.setBackground(new Color(0x8080ff));
		Label lblTitle = new Label("Xử lý dữ liệu quét thẻ");
		lblTitle.setForeground(new Color(0x800080));
		lblTitle.setFont(new Font(null, Font.BOLD, new Extent(20)));
		headerContent.add(lblTitle);
		mainSplitPane.add(headerContent);
		// ---------------
		SplitPaneLayoutData splitPane2Layout = new SplitPaneLayoutData();
		splitPane2Layout.setInsets(new Insets(5));
		Grid rootLayout = new Grid(3);
		rootLayout.setBorder(new Border(new Extent(2), Color.LIGHTGRAY,
				Border.STYLE_INSET));
		rootLayout.setInsets(new Insets(5));
		rootLayout.setLayoutData(splitPane2Layout);
		mainSplitPane.add(rootLayout);// ///////////////////
		GridLayoutData topLayout = new GridLayoutData();
		topLayout.setAlignment(new Alignment(Alignment.CENTER, Alignment.TOP));
		// ---------------
		Grid mainGrid = new Grid();
		mainGrid.setLayoutData(topLayout);
		Row dateRow = new Row();
		GridLayoutData dateRowLayout = new GridLayoutData();
		dateRowLayout.setColumnSpan(2);
		dateRow.setLayoutData(dateRowLayout);
		Label lblNgay = new Label("Ngày: ");
		dateRow.add(lblNgay);
		sfDay = new SelectField();
		sfDay.setWidth(new Extent(40));
		dateRow.add(sfDay);
		Label lblThang = new Label("Tháng: ");
		dateRow.add(lblThang);
		sfMonth = new SelectField();
		sfMonth.setWidth(new Extent(40));
		dateRow.add(sfMonth);
		Label lblNam = new Label("Năm: ");
		dateRow.add(lblNam);
		sfYear = new SelectField();
		sfYear.setWidth(new Extent(60));
		dateRow.add(sfYear);
		mainGrid.add(dateRow);
		Label lblEmpsn = new Label("Số thẻ: ");
		mainGrid.add(lblEmpsn);
		txtEmpsn = new DscField();
		txtEmpsn.setWidth(new Extent(80));
		txtEmpsn.setMaximumLength(8);
		mainGrid.add(txtEmpsn);
		Label lblFact = new Label("Xưởng: ");
		mainGrid.add(lblFact);
		sfFact = new SelectField();
		sfFact.setWidth(new Extent(80));
		mainGrid.add(sfFact);
		Label lblLean = new Label("Lean: ");
		mainGrid.add(lblLean);
		sfLean = new SelectField();
		sfLean.setWidth(new Extent(80));
		mainGrid.add(sfLean);
		Label lblDept = new Label("Đơn vị: ");
		mainGrid.add(lblDept);
		sfDept = new SelectField();
		sfDept.setWidth(new Extent(200));
		mainGrid.add(sfDept);
		rootLayout.add(mainGrid);// /////////////////////////////
		// ----------------------
		Grid gridBtns = new Grid(1);
		gridBtns.setInsets(new Insets(1, 2));
		gridBtns.setLayoutData(topLayout);
		btnXuLyDuLieuThang = new Button("Xử lý dữ liệu tháng");
		btnXuLyDuLieuThang.setStyleName("Default");
		gridBtns.add(btnXuLyDuLieuThang);
		btnInDuLieuThang = new Button("In dữ liệu tháng");
		btnInDuLieuThang.setStyleName("Default");
		gridBtns.add(btnInDuLieuThang);
		btnXuLyDuLieuNgay = new Button("Xử lý dữ liệu ngày");
		btnXuLyDuLieuNgay.setStyleName("Default");
		gridBtns.add(btnXuLyDuLieuNgay);
		btnInDuLieuNgay = new Button("In dữ liệu ngày");
		btnInDuLieuNgay.setStyleName("Default");
		gridBtns.add(btnInDuLieuNgay);
		btnChuyenDuLieu = new Button("Chuyển dữ liệu");
		btnChuyenDuLieu.setToolTipText("Chuyển dữ liệu ngày công cho kế toán.");
		btnChuyenDuLieu.setStyleName("Default");
		gridBtns.add(btnChuyenDuLieu);
		btnReset = new Button("Reset");
		btnReset.setStyleName("Default");
		gridBtns.add(btnReset);
		rootLayout.add(gridBtns);// //////////////////////////////
		// ------------------------
		lstList = new ListBox();
		lstList.setLayoutData(topLayout);
		lstList.setWidth(new Extent(150));
		lstList.setHeight(new Extent(200));
		rootLayout.add(lstList);// //////////////////////////////
		//----------------Option
		
	}
}
