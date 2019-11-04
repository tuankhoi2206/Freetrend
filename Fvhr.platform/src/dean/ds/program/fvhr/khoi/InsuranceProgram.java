package ds.program.fvhr.khoi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.sun.star.uno.Exception;

import ds.program.fvhr.util.OBJ_UTILITY;
import ds.program.fvhr.vinh.dao_support.ExportInsuranceInfoDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;

import fv.components.SimpleReportProgram;
import fv.util.Vni2Uni;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Table;
import nextapp.echo2.app.TextField;

import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

import nextapp.echo2.app.layout.TableLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.list.DefaultListSelectionModel;
import nextapp.echo2.app.list.ListSelectionModel;
import nextapp.echo2.app.CheckBox;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.info.LoginInfo;

import nextapp.echo2.app.table.DefaultTableCellRenderer;
import nextapp.echo2.app.table.DefaultTableModel;
import nextapp.echo2.app.table.TableCellRenderer;

public class InsuranceProgram extends SimpleReportProgram implements
		ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ResourceBundle resourceBundle;

	// property Components
	private TextField txtSearch;
	private CheckBox chkSearchList;

	private Label lblCount_L;
	private SplitPane paneMain;
	private RadioButton rdHopDong;
	private RadioButton rdPhuLuc;
	private RadioButton rdTimAll;
	private RadioButton rdTheoDot;
	private RadioButton rdHienHanh;
	private SelectField sfID_DEPT;
	private SelectField sfNAME_FACT;
	private SelectField sfNAME_GROUP;
	private SelectField sfNAME_DEPT_NAME;
	private Button btnOK;
	private Button btnReset;
	private Button btnKhoa;
	private Button btnMokhoa;
	private Button btnDsDaKhoa;
	private Button btnDsChuaKhoa;
	private Button btnXuatDS;
	private Table tblData;
	private SplitPane splCenter;
	private DscDateField dateDotKy;
	private DefaultTableModel modelTblData;

	private Font fontDefault;
	private Font fontDefaultComponent;
	// end property Components

	private String[] titleHopDong;

	private List<RecordHDLD> lstHD;

	private final String STT = "STT";
	private final String YN = "Y/N";
	private final String HOVATEN = "HỌ VÀ TÊN";
	private final String EMPSN = "SỐ THẺ";
	private final String POSS = "CHỨC VỤ";
	private final String SALARY = "LƯƠNG HĐ_CB";
	private final String NAME_FACT = "XƯỞNG";
	private final String NAME_GROUP = "NHÓM";
	private final String NAME_DEPT_NAME = "ĐƠN VỊ";
	private final String DATE_S = "NGÀY KÝ";
	private final String ID_LABOUR = "MS HỢP ĐỒNG";
	private final String TIMES = "LẦN KÝ";
	private final String LOCKED = "LOCK";
	private boolean isCheck = false;

	private String sqlUpdate = "";
	private String sqlUpdatWhere = "";
	private ArrayList<Integer> lstIndexSelect;// lưu lại những index đối tượng
												// được
												// chọn dựa trên sự kiện check.

	private final int INDEX_COLUMN_LOCK = 12;

	public InsuranceProgram() {

		MyComponents();

		lstHD = new ArrayList<RecordHDLD>();
		lstIndexSelect = new ArrayList<Integer>();
	}

	@Override
	protected void doLayout() {
		super.doLayout();
		super.windowPane.removeAll();
		super.windowPane.add(paneMain);
	}

	private void MyComponents() {
		// HOP DONH
		titleHopDong = new String[] { STT, YN, HOVATEN, EMPSN, POSS, SALARY,
				NAME_FACT, NAME_GROUP, NAME_DEPT_NAME, DATE_S, ID_LABOUR,
				TIMES, LOCKED };

		paneMain = new SplitPane(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		paneMain.setSeparatorPosition(new Extent(50, Extent.PX));
		this.add(paneMain);

		// row content components top

		createGroupComponetsTop();
		createPanelCenter();
		createTableData();
		initActionListener();
		loadAllSelectFieldModel();

		rdHienHanh.setSelected(true);
		rdHopDong.setSelected(true);
		checkUserLimit();
	}

	private void checkUserLimit() {

		List<String> lstGroup = null;
		try {
			lstGroup = ExportInsuranceInfoDAO.getGroupUserLimit(getUserID());
		} catch (SQLException e) {
			MessageDialog dlg = new MessageDialog("Error", "Có lỗi phát sinh "
					+ e.toString(), MessageDialog.TYPE_ERROR);
			dlg.show();
		}
		if (lstGroup.size() > 0) {

			if (lstGroup.get(0).equals("KT")) {
				isEnableButton(true);
			} else
				isEnableButton(false);
		} else// không phải là NSU hoặc KT
		{
			this.dispose();
		}
	}

	private void isEnableButton(boolean b) {
		btnKhoa.setEnabled(b);
		btnMokhoa.setEnabled(b);
		btnDsChuaKhoa.setEnabled(b);
		btnDsDaKhoa.setEnabled(b);
		btnXuatDS.setEnabled(b);
	}

	private void initActionListener() {
		// add listener button
		btnKhoa.addActionListener(this);
		btnDsChuaKhoa.addActionListener(this);
		btnDsDaKhoa.addActionListener(this);
		btnMokhoa.addActionListener(this);
		btnReset.addActionListener(this);
		btnOK.addActionListener(this);
		btnXuatDS.addActionListener(this);
		// end

		txtSearch.addActionListener(this);
		chkSearchList.addActionListener(this);
	}

	private void createTableData() {

		tblData = new Table();
		modelTblData = new DefaultTableModel();
		createTitleHopDong();
		tblData.setSelectionModel(new DefaultListSelectionModel());

		tblData.setModel(modelTblData);

		tblData.setWidth(new Extent(140, Extent.PERCENT));
		tblData.setRolloverBackground(new Color(0xd7d5bd));
		tblData.setSelectionBackground(new Color(0xc9d2a2));
		tblData.setRolloverEnabled(true);
		tblData.setSelectionEnabled(true);
		tblData.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0xc9d2a2), Border.STYLE_SOLID));
		tblData.setDefaultHeaderRenderer(new TableCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(Table table,
					Object value, int column, int row) {
				Label lbl = new Label();
				Font font = new Font(Font.TIMES_NEW_ROMAN, Font.BOLD,
						new Extent(11));
				lbl.setFont(font);
				lbl.setText((String) value);
				lbl.setForeground(Color.WHITE);
				TableLayoutData layout = new TableLayoutData();
				layout.setBackground(new Color(0x0080C0));
				layout.setInsets(new Insets(3));
				lbl.setLayoutData(layout);
				return lbl;
			}
		});

		// start set checkbox
		tblData.setDefaultRenderer(Object.class,
				new DefaultTableCellRenderer() {
					private static final long serialVersionUID = 1L;

					/*
					 * indexSelect chỉ mục row trong table.
					 */

					@Override
					public Component getTableCellRendererComponent(Table table,
							Object indexSelect, int column, final int row) {
						if (column == 1) {
							CheckBox chk = new CheckBox();
							chk.setId((String) indexSelect);
							chk.addActionListener(new ActionListener() {
								private static final long serialVersionUID = 1L;

								@Override
								public void actionPerformed(ActionEvent e) {
									CheckBox chk = (CheckBox) e.getSource();
									if (chk.isSelected()) {
										lstIndexSelect.add(Integer.parseInt(chk
												.getId()));

									} else {
										lstIndexSelect.remove(chk.getId());

									}
								}
							});
							return chk;
						} else {
							Label lbl = new Label();
							Font font = new Font(Font.TIMES_NEW_ROMAN,
									Font.BOLD, new Extent(11));
							lbl.setFont(font);
							if (indexSelect != null) {
								lbl.setText(indexSelect.toString());
							}
							lbl.setForeground(Color.BLACK);
							TableLayoutData layout = new TableLayoutData();
							layout.setInsets(new Insets(3));
							lbl.setLayoutData(layout);
							return lbl;
						}
					}
				});
		// end set Checkbox
		splCenter.add(tblData);
	}

	private void createTitleHopDong() {
		createTitle(titleHopDong);
	}

	private String getUserID() {
		Application app = (Application) ApplicationInstance.getActive();
		LoginInfo loginInfo = app.getLoginInfo();
		return loginInfo.getUserID();
	}

	private void loadAllSelectFieldModel() {

		DefaultListModel dpmtIDModel = new DefaultListModel();
		List<String> dpmtIDList = ExportInsuranceInfoDAO
				.queryAllDeptID(getUserID().toUpperCase());
		if (dpmtIDList.size() > 0) {
			for (int i = 0; i < dpmtIDList.size(); i++) {
				dpmtIDModel.add(dpmtIDList.get(i));
			}
			sfID_DEPT.setModel(dpmtIDModel);

			sfID_DEPT.addActionListener(new ActionListener() {

				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					String dpmtID = (String) sfID_DEPT.getSelectedItem();
					Map<String, String> dpmt = ExportInsuranceInfoDAO
							.queryUnitByDeptID(dpmtID);
					sfNAME_FACT.setSelectedIndex(((DefaultListModel) sfNAME_FACT
							.getModel()).indexOf(dpmt.get("name_fact")));
					sfNAME_GROUP.setSelectedIndex(((DefaultListModel) sfNAME_GROUP
							.getModel()).indexOf(dpmt.get("name_group")));
					sfNAME_DEPT_NAME
							.setSelectedIndex(((DefaultListModel) sfNAME_DEPT_NAME
									.getModel()).indexOf(dpmt
									.get("name_dept_name")));

				}

			});
		}
		// load xưởng
		DefaultListModel factNameModel = new DefaultListModel();
		List<String> factNameList = ExportInsuranceInfoDAO
				.queryAllFactName(getUserID().toUpperCase());
		if (factNameList.size() > 0) {
			for (int i = 0; i < factNameList.size(); i++) {
				if (factNameList.get(i) != null)
					factNameModel.add(Vni2Uni.convertToUnicode(factNameList
							.get(i)));
			}
			sfNAME_FACT.setModel(factNameModel);
		}

		// end load xưởng
		// load nhóm
		DefaultListModel groupNameModel = new DefaultListModel();

		List<String> groupNameList = ExportInsuranceInfoDAO
				.queryAllGroupName(getUserID().toUpperCase());

		if (groupNameList.size() > 0) {
			for (int i = 0; i < groupNameList.size(); i++) {
				if (groupNameList.get(i) != null)
					groupNameModel.add(Vni2Uni.convertToUnicode(groupNameList
							.get(i)));
			}
			sfNAME_GROUP.setModel(groupNameModel);
		}

		// end load nhóm

		// load đơn vị
		DefaultListModel deptNameModel = new DefaultListModel();
		List<String> unitNameList = ExportInsuranceInfoDAO
				.queryAllUnitName(getUserID().toUpperCase());
		if (unitNameList.size() > 0) {
			for (int i = 0; i < unitNameList.size(); i++) {
				if (unitNameList.get(i) != null)
					deptNameModel.add(Vni2Uni.convertToUnicode(unitNameList
							.get(i)));
			}
			sfNAME_DEPT_NAME.setModel(deptNameModel);
		}

		// end load đơn vị
	}

	private void createPanelCenter() {

		splCenter = new SplitPane();
		splCenter.setSeparatorWidth(new Extent(0, Extent.PX));
		// chinh lai
		splCenter.setSeparatorPosition(new Extent(220, Extent.PX));
		splCenter.setOrientation(SplitPane.ORIENTATION_HORIZONTAL);
		paneMain.add(splCenter);

		// colum chứa nhóm Components
		Column colLeft = new Column();
		// chinh lai 20
		colLeft.setInsets(new Insets(new Extent(10, Extent.PX)));
		colLeft.setCellSpacing(new Extent(30, Extent.PX));
		splCenter.add(colLeft);
		// column hợp đồng và phụ lục
		Column colHDvPL_Left = new Column();
		Label label = new Label("Loại hợp đồng và phụ lục");
		label.setFont(fontDefault);

		colHDvPL_Left.add(label);
		colLeft.add(colHDvPL_Left);

		Border border = new Border(1, new Color(0x0080C0), Border.STYLE_SOLID);

		Column colGroupRadio = new Column();
		colGroupRadio.setBorder(border);
		colHDvPL_Left.add(colGroupRadio);

		// start group hợp đồng và phụ lục
		Row rGroupRadio = new Row();
		colGroupRadio.add(rGroupRadio);
		Column colOfGroupRadio = new Column();

		ButtonGroup rdGr_LoaiHD_PL = new ButtonGroup();

		rdHopDong = new RadioButton("Hợp đồng");
		rdHopDong.setFont(fontDefaultComponent);
		rdHopDong.setGroup(rdGr_LoaiHD_PL);
		// rdHopDong.setId(id)
		rGroupRadio.add(rdHopDong);

		rdPhuLuc = new RadioButton("Phụ lục");
		rdPhuLuc.setFont(fontDefaultComponent);
		rdPhuLuc.setGroup(rdGr_LoaiHD_PL);
		rGroupRadio.add(rdPhuLuc);

		rdTimAll = new RadioButton("Tìm cả hai");
		rdTimAll.setFont(fontDefaultComponent);
		rdTimAll.setGroup(rdGr_LoaiHD_PL);
		colGroupRadio.add(rdTimAll);

		// end group

		// rGroupRadio splCenter.add(colLeft);
		// col2
		Column col2 = new Column();
		Label label2 = new Label("Thời gian kiểm tra");
		label2.setFont(fontDefault);
		col2.add(label2);

		Row rcol2 = new Row();
		rcol2.setBorder(border);
		col2.add(rcol2);

		ButtonGroup rdGr_ThoiGian_KiemTra = new ButtonGroup();

		rdTheoDot = new RadioButton("Theo đợt");
		rdTheoDot.setFont(fontDefaultComponent);
		rcol2.add(rdTheoDot);
		rdTheoDot.setGroup(rdGr_ThoiGian_KiemTra);

		rdHienHanh = new RadioButton("Hiện hành");
		rdHienHanh.setFont(fontDefaultComponent);
		rcol2.add(rdHienHanh);
		rdHienHanh.setGroup(rdGr_ThoiGian_KiemTra);

		colLeft.add(col2);
		// end col2

		// group col3
		Column col3 = new Column();
		col3.setCellSpacing(new Extent(5, Extent.PX));
		colLeft.add(col3);

		Extent extOfSF = new Extent(120, Extent.PX);

		Grid g3 = new Grid(2);
		g3.setColumnWidth(0, new Extent(140, Extent.PX));
		Label lbl2 = new Label("Đợt ký");
		lbl2.setFont(fontDefaultComponent);

		g3.add(lbl2);

		dateDotKy = new DscDateField();
		dateDotKy.getDateChooser().setLocale(Locale.ENGLISH);
		SimpleDateFormat sf = OBJ_UTILITY.Get_format_date();
		dateDotKy.setDateFormat(sf);
		Calendar ca = Calendar.getInstance();
		dateDotKy.getTextField().setText(sf.format(ca.getTime()));
		dateDotKy.setMouseCursor(DATAMODE_EDIT);

		g3.add(dateDotKy);

		col3.add(g3);
		// =========================

		Grid g4 = new Grid(2);
		g4.setColumnWidth(0, new Extent(140, Extent.PX));
		Label label3 = new Label("Mã đơn vị");
		label3.setFont(fontDefaultComponent);
		g4.add(label3);

		sfID_DEPT = new SelectField();
		sfID_DEPT.setWidth(extOfSF);

		g4.add(sfID_DEPT);

		col3.add(g4);
		// =======================

		Grid g5 = new Grid(2);
		g5.setColumnWidth(0, new Extent(140, Extent.PX));
		Label label4 = new Label("Xưởng");
		label4.setFont(fontDefaultComponent);

		g5.add(label4);
		sfNAME_FACT = new SelectField();
		sfNAME_FACT.setWidth(extOfSF);
		g5.add(sfNAME_FACT);

		col3.add(g5);
		// =======================

		Grid g6 = new Grid(2);
		g6.setColumnWidth(0, new Extent(140, Extent.PX));
		Label label5 = new Label("Nhóm");
		label5.setFont(fontDefaultComponent);
		g6.add(label5);
		sfNAME_GROUP = new SelectField();
		sfNAME_GROUP.setWidth(extOfSF);
		g6.add(sfNAME_GROUP);

		col3.add(g6);
		// =======================

		Grid g7 = new Grid(2);
		g7.setColumnWidth(0, new Extent(140, Extent.PX));
		Label label6 = new Label("Đơn vị");
		label6.setFont(fontDefaultComponent);

		g7.add(label6);
		sfNAME_DEPT_NAME = new SelectField();
		sfNAME_DEPT_NAME.setWidth(extOfSF);
		g7.add(sfNAME_DEPT_NAME);

		col3.add(g7);
		// =======================

		// end col3

		// col4
		Column col4 = new Column();
		col4.setCellSpacing(new Extent(5, Extent.PX));
		colLeft.add(col4);

		Extent exbtn = new Extent(159, Extent.PX);

		Grid g1OfCol4 = new Grid(2);
		g1OfCol4.setColumnWidth(0, new Extent(81, Extent.PX));
		g1OfCol4.setColumnWidth(1, new Extent(81, Extent.PX));
		col4.add(g1OfCol4);

		btnOK = new Button("OK");
		btnOK.setFont(fontDefaultComponent);
		btnOK.setForeground(Color.WHITE);

		btnOK.setBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_GROOVE));

		btnOK.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_INSET));

		btnOK.setRolloverBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_OUTSET));

		btnOK.setBackground(new Color(0x0080c0));

		btnOK.setAlignment(new Alignment(Alignment.CENTER, Alignment.BOTTOM));

		btnOK.setPressedEnabled(true);
		btnOK.setRolloverEnabled(true);

		g1OfCol4.add(btnOK);
		// rChild1OfCol4.add(btnOK);

		btnReset = new Button("Reset");
		btnReset.setFont(fontDefaultComponent);
		btnReset.setForeground(Color.WHITE);
		// rChild1OfCol4.add(btnReset);

		btnReset.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_GROOVE));

		btnReset.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_INSET));

		btnReset.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_OUTSET));

		btnReset.setBackground(new Color(0x0080c0));

		btnReset.setAlignment(new Alignment(Alignment.CENTER, Alignment.BOTTOM));

		btnReset.setPressedEnabled(true);
		btnReset.setRolloverEnabled(true);

		g1OfCol4.add(btnReset);

		// Row rChild2OfCol4 = new Row();
		// col4.add(rChild2OfCol4);

		Grid g2OfCol4 = new Grid(2);
		g2OfCol4.setColumnWidth(0, new Extent(81, Extent.PX));
		g2OfCol4.setColumnWidth(1, new Extent(81, Extent.PX));
		col4.add(g2OfCol4);

		btnKhoa = new Button("Khóa");
		btnKhoa.setFont(fontDefaultComponent);
		btnKhoa.setForeground(Color.WHITE);

		btnKhoa.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_GROOVE));

		btnKhoa.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_INSET));

		btnKhoa.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_OUTSET));

		btnKhoa.setBackground(new Color(0x0080c0));

		btnKhoa.setAlignment(new Alignment(Alignment.CENTER, Alignment.BOTTOM));

		btnKhoa.setPressedEnabled(true);
		btnKhoa.setRolloverEnabled(true);

		// end
		g2OfCol4.add(btnKhoa);

		btnMokhoa = new Button("Mở khóa");
		btnMokhoa.setFont(fontDefaultComponent);
		btnMokhoa.setForeground(Color.WHITE);

		btnMokhoa.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_GROOVE));

		btnMokhoa.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_INSET));

		btnMokhoa.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_OUTSET));

		btnMokhoa.setBackground(new Color(0x0080c0));

		btnMokhoa
				.setAlignment(new Alignment(Alignment.CENTER, Alignment.BOTTOM));

		btnMokhoa.setPressedEnabled(true);
		btnMokhoa.setRolloverEnabled(true);

		g2OfCol4.add(btnMokhoa);

		btnDsChuaKhoa = new Button("Danh sách chưa khóa");
		btnDsChuaKhoa.setFont(fontDefaultComponent);
		btnDsChuaKhoa.setForeground(Color.WHITE);

		btnDsChuaKhoa.setWidth(exbtn);

		btnDsChuaKhoa.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_GROOVE));

		btnDsChuaKhoa.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_INSET));

		btnDsChuaKhoa.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_OUTSET));

		btnDsChuaKhoa.setBackground(new Color(0x0080c0));

		btnDsChuaKhoa.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.BOTTOM));

		btnDsChuaKhoa.setPressedEnabled(true);
		btnDsChuaKhoa.setRolloverEnabled(true);

		col4.add(btnDsChuaKhoa);

		btnDsDaKhoa = new Button("Danh sách đã khóa");
		btnDsDaKhoa.setFont(fontDefaultComponent);
		btnDsDaKhoa.setForeground(Color.WHITE);

		btnDsDaKhoa.setWidth(exbtn);

		btnDsDaKhoa.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_GROOVE));

		btnDsDaKhoa.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_INSET));

		btnDsDaKhoa.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_OUTSET));

		btnDsDaKhoa.setBackground(new Color(0x0080c0));

		btnDsDaKhoa.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.BOTTOM));

		btnDsDaKhoa.setPressedEnabled(true);
		btnDsDaKhoa.setRolloverEnabled(true);

		col4.add(btnDsDaKhoa);

		// end col4

		// col5
		// chkXuatDS = new CheckBox("Xuất danh sách");
		// end col5

		btnXuatDS = new Button("Xuất danh sách");
		btnXuatDS.setFont(fontDefaultComponent);
		//
		btnXuatDS.setWidth(border.getSize());
		btnXuatDS.setForeground(Color.WHITE);
		System.out.println("border " + btnXuatDS.getWidth());

		btnXuatDS.setWidth(exbtn);

		btnXuatDS.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_GROOVE));

		btnXuatDS.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_INSET));

		btnXuatDS.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_OUTSET));

		btnXuatDS.setBackground(new Color(0x0080c0));

		btnXuatDS
				.setAlignment(new Alignment(Alignment.CENTER, Alignment.BOTTOM));

		btnXuatDS.setPressedEnabled(true);
		btnXuatDS.setRolloverEnabled(true);
		col4.add(btnXuatDS);

	}

	private void createGroupComponetsTop() {
		
		Row rowComponentsTop = new Row();
		rowComponentsTop.setInsets(new Insets(new Extent(10, Extent.PX)));
		rowComponentsTop.setBackground(new Color(0x0080c0));
		rowComponentsTop.setForeground(Color.WHITE);
		rowComponentsTop.setCellSpacing(new Extent(30, Extent.PX));

		Font font = new Font(Font.TIMES_NEW_ROMAN, Font.BOLD, new Extent(20));

		fontDefault = new Font(Font.TIMES_NEW_ROMAN, Font.ITALIC,
				new Extent(15));

		fontDefaultComponent = new Font(Font.TIMES_NEW_ROMAN, Font.PLAIN,
				new Extent(15));

		Label lblTitleOfrowTop = new Label(
				"Kiểm tra việc ký hợp đồng LĐ và Phụ lục HĐ");
		lblTitleOfrowTop.setFont(font);

		rowComponentsTop.add(lblTitleOfrowTop);

		Row rowGroup = new Row();
		Label label = new Label("Tìm kiếm");
		label.setFont(fontDefault);
		rowGroup.add(label);
		txtSearch = new TextField();

		rowGroup.add(txtSearch);

		rowComponentsTop.add(rowGroup);

		chkSearchList = new CheckBox("Tìm kiếm theo List");
		chkSearchList.setFont(fontDefault);
		rowComponentsTop.add(chkSearchList);

		lblCount_L = new Label("L_Count");
		lblCount_L.setFont(fontDefault);

		rowComponentsTop.add(lblCount_L);

		paneMain.add(rowComponentsTop);
	}

	@Override
	protected HSSFWorkbook generateWorkbook() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doRefresh() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doSearch() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj instanceof Button) {
			Button btnAction = (Button) obj;
			try {
				if (btnAction.equals(btnOK)) {
					querySQL("");
				} else if (btnAction.equals(btnKhoa)) {
					doLock("Y");
				} else if (btnAction.equals(btnDsDaKhoa)) {
					querySQL("Y");
				} else if (btnAction.equals(btnDsChuaKhoa)) {
					querySQL("NULL");
				} else if (btnAction.equals(btnMokhoa)) {
					doLock("N");
				} else if (btnAction.equals(btnReset)) {
					doResetComponent();
				} else {
					doXuatDS();
				}
			} catch (Exception ex) {
			}
		} else if (obj instanceof CheckBox) {
			System.out.println("check box");
			if (chkSearchList.isSelected()) {
				isCheck = true;
			} else {
				isCheck = false;
			}
		} else// textField
		{
			if (isCheck) {
				// tìm kiếm empsn trong table
				searchEmpsnInTable();
			} else {
				try {
					querySQL("");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private void doResetComponent() {
		DefaultTableModel model = (DefaultTableModel) tblData.getModel();
		model.setRowCount(0);
		txtSearch.setText("");

		// thừa code
		DefaultListModel lstID_DEPT = (DefaultListModel) sfID_DEPT.getModel();
		DefaultListModel lstNAME_DEPT_NAME = (DefaultListModel) sfNAME_DEPT_NAME
				.getModel();
		DefaultListModel lstNAME_FACT = (DefaultListModel) sfNAME_FACT
				.getModel();
		DefaultListModel lstNAME_GROUP = (DefaultListModel) sfNAME_GROUP
				.getModel();

		if (lstID_DEPT.size() > 0 && lstNAME_DEPT_NAME.size() > 0
				&& lstNAME_FACT.size() > 0 && lstNAME_GROUP.size() > 0) {
			sfID_DEPT.setSelectedIndex(-1);
			sfNAME_DEPT_NAME.setSelectedIndex(-1);
			sfNAME_FACT.setSelectedIndex(-1);
			sfNAME_GROUP.setSelectedIndex(-1);
		}

	}

	private void searchEmpsnInTable() {
		// kiem tra chuoi nhap vao la chu
		if (txtSearch.getText().trim().length() > 0) {

			if (chkSearchList.isSelected()) {
				String empsn = txtSearch.getText().trim();
				DefaultTableModel tableModel = (DefaultTableModel) tblData
						.getModel();

				if (tableModel.getRowCount() > 0) {
					for (int i = 0; i < tableModel.getRowCount(); i++) {
						if (empsn
								.equals(tableModel.getValueAt(3, i).toString())) {
							// có trong danh sách
							ListSelectionModel listSelectionModel = tblData
									.getSelectionModel();
							listSelectionModel.setSelectedIndex(i, true);
							return;
						}
					}
					MessageDialog dialog = new MessageDialog("Thông báo",
							"Không tìm thấy số thẻ " + empsn
									+ " trong danh sách.",
							MessageDialog.CONTROLS_OK);
					dialog.show();
				}
			}

		} else {
			MessageDialog dialog = new MessageDialog("Lỗi",
					"Số thẻ nhập vào không hợp lệ.", MessageDialog.CONTROLS_OK);
			dialog.show();
		}
	}

	// test pass
	private void doXuatDS() {
		// sửa lại bỏ xuất theo check
		// for (String iterable_element : lstChkSelect) {
		// // nhớ -1
		// lstExport.add(lstHD.get(Integer.parseInt(iterable_element) - 1));
		// }
		try {
			exportExcel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ----------------exportExcel--------------------
	private void exportExcel() throws IOException {

		if (getRowCountOfTable() > 0) {

			FileInputStream in = new FileInputStream(this.getClass()
					.getResource("hopdong.xls").getFile());

			HSSFWorkbook wb = new HSSFWorkbook(in);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			int startRow = 3;
			// backend model
			// export cho trường hợp người chọn select
			int i = 1;
			List<RecordHDLD> list = null;
			// if (lstExport.size() > 0)
			// list = lstExport;
			// else
			list = lstHD;
			// duyệt từng phần tử trong list và ghi ra cell
			for (RecordHDLD record : list) {
				row = sheet.createRow(startRow++);
				// cell 1 STT
				cell = row.createCell(0);
				cell.setCellValue(i++);
				// cell 2 họ tên
				cell = row.createCell(1);
				cell.setCellValue(Vni2Uni.convertToUnicode(record.getFname())
						+ " " + Vni2Uni.convertToUnicode(record.getLname()));
				// cell 3 so the
				cell = row.createCell(2);
				cell.setCellValue(record.getEmpsn());

				// cell 4 chuc vu
				cell = row.createCell(3);
				cell.setCellValue(record.getPoss());
				// cell 5 luong
				cell = row.createCell(4);
				cell.setCellValue(record.getSalary());
				// cell 6 xuong
				cell = row.createCell(5);
				cell.setCellValue(record.getName_dept_name());// null???
				// cell 7 nhom
				cell = row.createCell(6);
				cell.setCellValue(record.getName_group());
				// cell 8 don vi

				cell = row.createCell(7);
				cell.setCellValue(Vni2Uni.convertToUnicode(record
						.getName_fact()));

				// cell 9 ngay ky
				cell = row.createCell(8);
				cell.setCellValue(record.getDate_s());
				// cell 10 id_labour
				cell = row.createCell(9);
				cell.setCellValue(record.getId_labour());
				// cell 12 lan ky
				cell = row.createCell(10);
				cell.setCellValue(record.getTimes());
				// cell 13 locked
				cell = row.createCell(11);
				cell.setCellValue(record.getLock());

			}
			// truong hop luu tất cả record trong table
			// Luu file excel trong o D
			String filename = "D:/DanhSachHopDong("
					+ dateDotKy.getText().replace("/", "-") + ")" + "-"
					+ System.currentTimeMillis() + ".xls";
			FileOutputStream out = new FileOutputStream(filename);
			wb.write(out);
			out.close();
		}
	}

	// -----end-------exportExcel--------------------

	private int getRowCountOfTable() {
		DefaultTableModel model = (DefaultTableModel) tblData.getModel();
		return model.getRowCount();
	}

	private void doLock(final String lock) {
		if (lstHD != null)
			if (getRowCountOfTable() > 0) {

				// sửa lại khóa dựa trên check box
				//
				// ArrayList<RecordHDLD> lstExport = new ArrayList<>();
				// for (String index : lstChkSelect) {
				// lstExport.add(lstHD.get(Integer.parseInt(index)));
				//
				// }

				updateLockHD(lock);

				// check index table
				// if (listSelectionModel.getMinSelectedIndex() > -1) {
				//
				// // chỉ mục đối tượng đang được chọn
				// int indexSelect = listSelectionModel.getMinSelectedIndex();
				//
				// final String name_employee = (String) modelTblData
				// .getValueAt(2, indexSelect);
				//
				// // để xét đã khóa hay chưa
				// final String value_lock_select = (String) modelTblData
				// .getValueAt(12, indexSelect);
				// System.out.println(lock);
				//
				// if (!lock.equals(value_lock_select)) {
				//
				// final String titleDlg = lock.equals("Y") ? "khóa"
				// : "mở khóa";
				//
				// MessageDialog dlg = new MessageDialog("Thông báo",
				// "Bạn có muốn " + titleDlg + " nhân viên "
				// + name_employee,
				// MessageDialog.CONTROLS_YES_NO);
				// // end index
				//
				// dlg.addActionListener(new ActionListener() {
				//
				// /**
				// *
				// */
				// private static final long serialVersionUID = 1L;
				//
				// @Override
				// public void actionPerformed(ActionEvent e) {
				// // button ok đồng tý thay đ
				// if (e.getActionCommand().equals(
				// MessageDialog.COMMAND_OK)) {
				// // thừa code
				// ListSelectionModel listSelectionModel = tblData
				// .getSelectionModel();
				// // test doi tuong o cot empsn
				// // loi
				// int index = listSelectionModel
				// .getMinSelectedIndex();
				// RecordHDLD recordSelect = (RecordHDLD) modelTblData
				// .getValueAt(3, index);
				//
				// doLocked(lock, titleDlg, recordSelect,
				// index, 12);
				//
				// }
				// }
				//
				// private void doLocked(String styleLock,
				// String title, RecordHDLD recordSelect,
				// int indexSelect, int colLock) {
				//
				// // đối tượng chứa thông tin sẽ cập nhật
				// RecordHDLD r = lstHD.get(lstHD
				// .indexOf(recordSelect));
				// // check
				// String valueOfLock = r.getChecked().trim();
				// // hiện trạng chưa khóa
				//
				// if (!valueOfLock.contains(styleLock)) {
				// System.out.println("Bang se cap nhat "
				// + sqlUpdate);
				//
				// // xet xem button dang nhan la danh sach
				// // chua khoa hay da khoa
				// // kiem tra xem trong querySQL đã có đầy
				// // đủ trường dữ liệu chưa
				// // neu la hop dong
				// // phu luc thi lay lock cua phu luc
				// // tai vi khi thay đổi lương sẽ cập nhật
				// // lại
				//
				// // xet tren table luon
				// String query = "Update ";
				// // bảng sẽ cập nhật
				// if (r.getTableName() != null) {
				// sqlUpdate += r.getTableName();
				// if (r.getTableName().equals("n_labour")) {
				// sqlUpdatWhere += "date_s=";
				// } else {
				// sqlUpdatWhere += "dates_sign=";
				// }
				// }
				//
				// query += sqlUpdate;
				// // colum sẽ cập nhật
				// query += " SET checked='" + styleLock
				// + "' ";
				// // điều kiện
				// query += " where " + sqlUpdatWhere
				// + "to_date('"
				// + recordSelect.getDate_s()
				// + "','dd/mm/yyyy')";
				//
				// query += " and id_labour='"
				// + recordSelect.getId_labour() + "'";
				//
				// System.out.println("Khoa " + query);
				// // chưa bắt lỗi
				// if (ExportInsuranceInfoDAO
				// .queryUpdateColumnChecked(query) > 0) {
				// MessageDialog dlg = new MessageDialog(
				// "Thông báo", title
				// + " nhân viên "
				// + name_employee
				// + " thành công.",
				// MessageDialog.CONTROLS_OK);
				// dlg.show();
				//
				// // cập nhật lại giá trị cột clock trong
				// // table
				// modelTblData.setValueAt(styleLock,
				// colLock, indexSelect);
				// // cập nhật lại giá trị trong listHD
				// r.setChecked(styleLock);
				//
				// } else {
				// MessageDialog dlg = new MessageDialog(
				// "Thông báo", "Khóa nhân viên "
				// + name_employee
				// + " thất bại.",
				// MessageDialog.CONTROLS_OK);
				// dlg.show();
				// }
				//
				// } else {
				// MessageDialog dlg = new MessageDialog(
				// "Alert", "Nhân viên này đã "
				// + title,
				// MessageDialog.CONTROLS_OK);
				// dlg.show();
				// }
				// }
				// });
				// dlg.show();
				// }
				// } else {
				// MessageDialog dlg = new MessageDialog("Alert",
				// "Vui lòng chọn nhân viên bạn muốn khóa/ mở khóa.",
				// MessageDialog.CONTROLS_OK);
				// dlg.show();
				// }
				// end check index table
			}

	}

	private void updateLockHD(String valueLock) {

		if (lstIndexSelect != null) {
			int countRecordFail = 0;// biến đếm số record cập nhật ko thành
									// công.

			for (int index : lstIndexSelect) {
				RecordHDLD recordUpdate = lstHD.get(index);
				System.out.println(recordUpdate.getChecked());
				if (!recordUpdate.getChecked().equals(valueLock)) {
					// update dữ liệu xuống database
					String query = "Update ";

					// kiểm tra getTableName!=null cho trường hợp...
					if (recordUpdate.getTableName() != null) {
						sqlUpdate += recordUpdate.getTableName();
						if (recordUpdate.getTableName().equals("n_labour")) {
							sqlUpdatWhere += "date_s=";
						} else {
							sqlUpdatWhere += "dates_sign=";
						}

					}

					query += sqlUpdate;
					// colum sẽ cập nhật
					query += " SET checked='" + valueLock + "' ";
					// điều kiện
					query += " where " + sqlUpdatWhere + "to_date('"
							+ recordUpdate.getDate_s() + "','dd/mm/yyyy')";

					query += " and id_labour='" + recordUpdate.getId_labour()
							+ "'";

					System.out.println(query);

					try {
						if (ExportInsuranceInfoDAO
								.queryUpdateColumnChecked(query) > 0) {

							recordUpdate.setChecked(valueLock);
							modelTblData.setValueAt(valueLock,
									INDEX_COLUMN_LOCK, index);

						} else {
							countRecordFail++;
							System.out.println("Số record cập nhật thất bại "
									+ countRecordFail);

						}
					} catch (SQLException e) {
						MessageDialog dlg = new MessageDialog("Error",
								"Có lỗi phát sinh " + e.toString(),
								MessageDialog.TYPE_ERROR);
						dlg.show();
					}
				}
			}
		}
	}

	// chinh lai
	private void querySQL(String checked) throws Exception {

		/*
		 * select e.lname, bs.empsn, bs.bsalary from n_employee e,
		 * n_basic_salary bs where bs.dates=to_date('15/05/2011','dd/mm/yyyy')
		 * and e.depsn='01203' and e.empsn=bs.empsn chu y chua xet trong bang
		 * n_basic_salary
		 */
		ListSelectionModel listSelectionModel = tblData.getSelectionModel();
		listSelectionModel.setSelectedIndex(0, true);

		String sql = "";

		if (isCheckValueCombobox()) {
			// xet dieu kien

			String empsn = "";
			empsn = txtSearch.getText().trim();

			String id_dept = sfID_DEPT.getSelectedIndex() > -1 ? (String) sfID_DEPT
					.getModel().get(sfID_DEPT.getSelectedIndex()) : "";

			String name_fact = sfNAME_FACT.getSelectedIndex() > -1 ? (String) sfNAME_FACT
					.getModel().get(sfNAME_FACT.getSelectedIndex()) : "";

			String name_group = sfNAME_GROUP.getSelectedIndex() > -1 ? (String) sfNAME_GROUP
					.getModel().get(sfNAME_GROUP.getSelectedIndex()) : "";

			String name_dept_name = sfNAME_DEPT_NAME.getSelectedIndex() > -1 ? (String) sfNAME_DEPT_NAME
					.getModel().get(sfNAME_DEPT_NAME.getSelectedIndex()) : "";

			// check quyen thao tac tren don vi
			// query
			MyQuerySQL mySQL = null;
			mySQL = new MyQuerySQL();

			mySQL.addWhere(id_dept, name_fact, name_group,
					Vni2Uni.convertToVNI(name_dept_name), empsn);
			// Vni2Uni.convertToVNI(name_dept_name));

			boolean isHopDong = false;
			boolean isPhuLuc = false;
			boolean isCaHai = false;
			// mặc định là hiện hành
			if (rdHopDong.isSelected()) {
				isHopDong = true;
				mySQL.setSql_defaultSelect(" , l.poss, l.salary, l.date_s, l.id_labour, l.times, l.clock, l.checked ");

				// dùng cho button khóa, xác định bảng.
				sqlUpdate = "N_LABOUR";
				sqlUpdatWhere = "date_s=";
				// ===========================

				// test hop dong theo dot
				// xet cho button khoa/ chua khoa
				// test pass ca hai truong hop hop dong hien hanh va theo dot
				// sai luong
				// remove l.clock=1
				if (!checked.trim().isEmpty())
					if (checked.trim().equals("NULL")) {
						// danh sach chua khoa
						mySQL.setSql_defaultWhere(" and (l.checked is NULL or l.checked='N') ");
					} else
						// danh sach da khoa
						mySQL.setSql_defaultWhere(" and l.checked='" + checked
								+ "' ");
				// xét trường hợp hợp đồng theo đợt
				if (rdTheoDot.isSelected()) {
					// test pass
					mySQL.setSql_defaultWhere(" and l.date_s=to_date('"
							+ dateDotKy.getText() + "','dd/mm/yyyy')");
					isPhuLuc = true;
				}

				// xét trường hợp phụ lục
			} else if (rdPhuLuc.isSelected()) {

				// trường hợp là Phụ lục, cập nhật trên bảng N_SUB_LABOUR
				sqlUpdate = "N_SUB_LABOUR";
				sqlUpdatWhere = "dates_sign=";
				// ==========================
				isHopDong = false;
				mySQL.setSql_defaultSelect(" ,sl.new_job, sl.new_sal, sl.dates_sign, sl.id_labour, l.times, sl.clock, sl.checked ");
				mySQL.setSql_defaultFrom(", n_sub_labour sl ");

				// trường hợp button danh sách khóa hoặc chưa
				if (!checked.trim().isEmpty()) {
					mySQL.setSql_defaultWhere(" and sl.id_labour=l.id_labour and sl.clock='1' ");
					if (checked.trim().equals("NULL"))
						// danh sach chua khoa
						mySQL.setSql_defaultWhere(" and (sl.checked is NULL or sl.checked='N')");
					else
						// danh sach da khoa
						mySQL.setSql_defaultWhere(" and sl.checked='Y'");
				}
				// ---------------end-----------

				// -- trường hợp phụ lục theo đợt --
				if (rdTheoDot.isSelected()) {
					// test pass
					mySQL.setSql_defaultWhere(" and sl.id_labour=l.id_labour");

					mySQL.setSql_defaultWhere(" and sl.dates_sign=to_date('"
							+ dateDotKy.getText() + "','dd/mm/yyyy')");
				}
				// --------------end---------------

			} else {

				// -------- trường hợp cả hợp đồng và phụ lục----------

				isHopDong = true;
				isCaHai = true;
				MyQuerySQL queryHD = new MyQuerySQL();
				queryHD.addWhere(id_dept, name_fact, name_group,
						Vni2Uni.convertToVNI(name_dept_name), empsn);
				queryHD.setSql_defaultSelect(" , l.poss, l.salary, l.date_s, l.id_labour, l.times, l.clock, l.checked, 'n_labour'");

				MyQuerySQL queryPL = new MyQuerySQL();
				queryPL.addWhere(id_dept, name_fact, name_group,
						Vni2Uni.convertToVNI(name_dept_name), empsn);

				queryPL.setSql_defaultSelect(" ,sl.new_job, sl.new_sal, sl.dates_sign, sl.id_labour, l.times, sl.clock, sl.checked,'n_sub_labour'");
				queryPL.setSql_defaultFrom(", n_sub_labour sl");
				queryPL.setSql_defaultWhere(" and sl.id_labour=l.id_labour and l.clock=1 and sl.clock='1' ");
				// thua l.clock

				if (!checked.trim().isEmpty())
					if (checked.trim().equals("NULL")) {
						// danh sach chua khoa
						queryPL.setSql_defaultWhere(" and (sl.checked is NULL or sl.checked='N')");

						queryHD.setSql_defaultWhere(" and (l.checked is NULL or l.checked='N')");
					} else {
						// danh sach da khoa
						queryPL.setSql_defaultWhere(" and sl.checked='Y'");

						queryHD.setSql_defaultWhere(" and l.checked='Y'");
					}

				if (rdTheoDot.isSelected()) {

					queryHD.setSql_defaultWhere(" and l.date_s=to_date('"
							+ dateDotKy.getText() + "','dd/mm/yyyy')");

					queryPL.setSql_defaultWhere(" and sl.dates_sign=to_date('"
							+ dateDotKy.getText() + "','dd/mm/yyyy')");

					System.out.println("ca hai theo dot : " + queryHD.getSQL()
							+ " union " + queryPL.getSQL());
				}

				sql = queryHD.getSQL() + " union " + queryPL.getSQL();
				// -------- end trường hợp cả hợp đồng và phụ lục----------
			}

			// truong hop test hop dong theo dot danh sach chua khoa fail
			// trường hợp hợp đồng theo đợt danh sách chưa khóa phải remove
			// l.clock=1
			if (isHopDong && isPhuLuc) {
				sql = mySQL.getSQL().replace("and l.clock=1", "");
				// System.out.println("test truong hop hop dong theo dot " +
				// sql);
			} else {
				if (isCaHai == false) {
					sql = mySQL.getSQL();
				}

			}
			System.out.println("query " + sql);
			try {
				lstHD = ExportInsuranceInfoDAO
						.queryListKemTraHD(sql, isHopDong);
			} catch (SQLException e) {
				MessageDialog dlg = new MessageDialog("Error",
						"Có lỗi phát sinh " + e.toString(),
						MessageDialog.TYPE_ERROR);
				dlg.show();
			}

			if (lstHD != null) {

				LoadDataTable(lstHD);

			} // thong bao neu khong co record tra ve
			else {
				MessageDialog dlg = new MessageDialog("Thông báo",
						"Không có hợp đồng.", MessageDialog.CONTROLS_OK);
				dlg.show();
			}
		}
	}

	private void LoadDataTable(final List<RecordHDLD> lstHD) {

		final DefaultTableModel model = (DefaultTableModel) tblData.getModel();
		model.setRowCount(0);

		int countEmpsn = 0;// biến đếm số hợp đồng và còn được dùng làm index
							// của table

		for (RecordHDLD employee : lstHD) {
			model.addRow(new Object[] {
					countEmpsn + 1,
					String.valueOf(countEmpsn++),
					Vni2Uni.convertToUnicode(employee.getFname()) + " "
							+ Vni2Uni.convertToUnicode(employee.getLname()),
					employee, employee.getPoss(), employee.getSalary(),
					employee.getName_fact(), employee.getName_group(),
					Vni2Uni.convertToUnicode(employee.getName_dept_name()),
					employee.getDate_s(), employee.getId_labour(),
					employee.getTimes(), employee.getChecked() });
		}

		lblCount_L.setText(countEmpsn + " Hợp đồng");

	}

	private boolean isCheckValueCombobox() {

		DefaultListModel lstID_DEPT = (DefaultListModel) sfID_DEPT.getModel();
		DefaultListModel lstNAME_DEPT_NAME = (DefaultListModel) sfNAME_DEPT_NAME
				.getModel();
		DefaultListModel lstNAME_FACT = (DefaultListModel) sfNAME_FACT
				.getModel();
		DefaultListModel lstNAME_GROUP = (DefaultListModel) sfNAME_GROUP
				.getModel();

		if (lstID_DEPT.size() > 0 && lstNAME_DEPT_NAME.size() > 0
				&& lstNAME_FACT.size() > 0 && lstNAME_GROUP.size() > 0) {
			if (sfID_DEPT.getSelectedIndex() > -1
					|| sfNAME_DEPT_NAME.getSelectedIndex() > -1
					|| sfNAME_FACT.getSelectedIndex() > -1
					|| sfNAME_GROUP.getSelectedIndex() > -1)

			{
				return true;
			} else {

				MessageDialog dlg = new MessageDialog("Thông báo",
						"Bạn chưa chọn thông tin tìm kiếm.",
						MessageDialog.CONTROLS_OK);
				dlg.show();
			}
		}
		return false;
	}

	private void createTitle(String[] title) {

		int len = title.length;

		modelTblData.setColumnCount(len);

		for (int i = 0; i < len; i++) {
			modelTblData.setColumnName(i, title[i]);
		}
	}
}