package ds.program.fvhr.minh.insurance;

import it.businesslogic.ireport.gui.MessageBox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_EMP_QUIT;
import ds.program.fvhr.domain.N_GROUP_DEPT;
import ds.program.fvhr.domain.N_TIME_BEAR;
import ds.program.fvhr.minh.dao.InsurDAO;
import ds.program.fvhr.minh.domain.EmployeeBear;
import ds.program.fvhr.minh.domain.EmployeePlain;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;

import fv.components.SelectItem;
import fv.components.SimpleReportProgram;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.ReportUtils;
import fv.util.Vni2Uni;
import nextapp.echo2.app.Button;
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
import echopointng.GroupBox;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ChangeEvent;
import nextapp.echo2.app.event.ChangeListener;
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.CheckBox;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.event.FocusEvent;
import dsc.echo2app.component.event.FocusListener;
import dsc.echo2app.info.RightInfo;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.util.function.UUID;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;

public class InsuranceProgram extends SimpleReportProgram {

	private ResourceBundle resourceBundle;
	private InsurDAO ins = new InsurDAO();
	private final int MODEDUCONG = 1;
	private final int MODEKHONGDUCONG = 0;
	private final int MODETOANDV = 2;
	private final int MODETRUBH = 3;
	private final int MODENGHISAN = 4;
	private int mode = 0;
	private List<EmployeePlain> listAll;
	private List<EmployeePlain> listDu;
	private List<EmployeePlain> listKo;
	private List<N_EMP_QUIT> listTRU;
	private List<EmployeeBear> listNS;
	private int stt = 1;
	private CheckBox ckbLan1;
	private SelectField slfThang;
	private SelectField slfNam;
	private DscField txtDepsn;
	private SelectField slfFact;
	private SelectField slfGroup;
	private SelectField slfDept;
	private CheckBox ckbGhiChuNV;
	private CheckBox ckbNghiViec;
	private RadioButton rbtnKhongDu;
	private RadioButton rbtnDu;
	private RadioButton rbtnAll;
	private RadioButton rbtnTruBH;
	private RadioButton rbtnNghiSan;
	private Button btnExcel;
	float tyleBHXH;
	float tyleBHTN;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private Button btnDS;
	private SplitPane splMain;
	private GroupBox grbDS;
	private boolean flag = false;

	/*
	 * Creates a new <code>InsuranceProgram</code>.
	 * 
	 */
	public InsuranceProgram() {
		super();
		initComponents();
		loadslfThangNam();
		ListBinder.bindSelectField(slfFact, FVGenericInfo.getFactories(), false);
		moreInit();
		listDu = new ArrayList<EmployeePlain>();
		listKo = new ArrayList<EmployeePlain>();
		listAll = new ArrayList<EmployeePlain>();
		listTRU = new ArrayList<N_EMP_QUIT>();
		listNS = new ArrayList<EmployeeBear>();
		float tyle[] = ins.BHXHTN();
		tyleBHXH = tyle[0];
		tyleBHTN = tyle[1];
	}

	public void loadslfThangNam() {
		DefaultListModel modelThang = (DefaultListModel) slfThang.getModel();
		DefaultListModel modelNam = (DefaultListModel) slfNam.getModel();
		Date d = new Date(System.currentTimeMillis());
		for (int i = 1; i < 13; i++) {
			if (i < 10)
				modelThang.add("0" + i);
			else
				modelThang.add(i);
			int year = d.getYear() + 1900;
			modelNam.add(year - 12 + i);
		}
		slfThang.setSelectedIndex(d.getMonth() - 1);
		slfNam.setSelectedIndex(11);
	}

	public void doslfFactisSelect() {
		try {
			if (!ins.checkQLyFact(slfFact.getSelectedItem().toString())) {
				slfFact.setSelectedIndex(-1);
				DefaultListModel modelGroup = (DefaultListModel) slfGroup.getModel();
				slfGroup.setSelectedIndex(-1);
				modelGroup.removeAll();
				DefaultListModel modelDept = (DefaultListModel) slfDept.getModel();
				slfDept.setSelectedIndex(-1);
				modelDept.removeAll();
				Application.getApp().showMessageDialog("Thông Báo", "Bạn không có quyền thao tác trên xưởng này.",
						MessageBox.OK);
				return;
			}
			slfGroup.setSelectedIndex(-1);
			DefaultListModel mG = ((DefaultListModel) slfGroup.getModel());
			mG.removeAll();
			txtDepsn.setText("");
			SelectItem itemF = (SelectItem) slfFact.getSelectedItem();
			ListBinder.bindSelectField(slfGroup, FVGenericInfo.getGroup(itemF.getValue()), false);
			slfDept.setSelectedIndex(-1);
			DefaultListModel mD = ((DefaultListModel) slfDept.getModel());
			mD.removeAll();
		} catch (Exception e) {
			System.out.println(e.toString() + "ggggggg");
		}

	}

	public void doslfGroupisSelect() {
		try {
			if (!ins.checkQLyGroup(slfFact.getSelectedItem().toString(), slfGroup.getSelectedItem().toString())) {
				slfGroup.setSelectedIndex(-1);
				DefaultListModel modelDept = (DefaultListModel) slfDept.getModel();
				slfDept.setSelectedIndex(-1);
				modelDept.removeAll();
				Application.getApp().showMessageDialog("Thông Báo", "Bạn không có quyền thao tác trên nhóm này.",
						MessageBox.OK);
				return;
			}
			slfDept.setSelectedIndex(-1);
			DefaultListModel mD = ((DefaultListModel) slfDept.getModel());
			mD.removeAll();
			txtDepsn.setText("");
			SelectItem itemF = (SelectItem) slfFact.getSelectedItem();
			SelectItem itemG = (SelectItem) slfGroup.getSelectedItem();
			bindSelectField(slfDept, FVGenericInfo.getDeptName(itemF.getValue(), itemG.getValue()), false);
		} catch (Exception e) {
			System.out.println(e.toString() + "ggggggg");
		}

	}

	public void bindSelectField(SelectField sf, MappingPropertyEditor editor, boolean display) {
		DefaultListModel model = (DefaultListModel) sf.getModel();
		model.removeAll();
		sf.setSelectedIndex(-1);
		if (editor == null || editor.filterMap().size() <= 0) {
			sf.setEnabled(false);
		} else {
			sf.setEnabled(true);
			for (int i = 0; i < editor.filterMap().size(); i++) {
				String disp = "";
				if (display) {
					disp = editor.getDisplays()[i];
				} else {
					disp = editor.getValues()[i] == null ? "" : editor.getValues()[i].toString();
				}
				SelectItem item = new SelectItem(Vni2Uni.convertToUnicode(disp), editor.getValues()[i]);
				model.add(item);
			}
		}
	}

	public void doslfDeptisSelect() {
		try {

			SelectItem itemF = (SelectItem) slfFact.getSelectedItem();
			SelectItem itemG = (SelectItem) slfGroup.getSelectedItem();
			SelectItem itemD = (SelectItem) slfDept.getSelectedItem();
			String depsn = FVGenericInfo.findDept(itemF.getText(), itemG.getText(), itemD.getValue().toString());
			txtDepsn.setText(depsn);
			if (!ins.checkQLyDept(depsn)) {
				slfDept.setSelectedIndex(-1);
				Application.getApp().showMessageDialog("Thông Báo", "Bạn không có quyền thao tác trên đơn vị này.",
						MessageBox.OK);
				txtDepsn.setText("");
				return;
			}
		} catch (Exception e) {
			System.out.println(e.toString() + "ggggggg");
		}
	}

	public void dotxtDepsnAct() {
		String depsn = txtDepsn.getText();
		String factName = FVGenericInfo.findFactFollowDept(depsn);
		SelectItem itemF = new SelectItem(factName, factName);
		slfFact.setSelectedIndex(((DefaultListModel) slfFact.getModel()).indexOf(itemF));
		doslfFactisSelect();

		String groupName = FVGenericInfo.findGroup(depsn);
		SelectItem itemG = new SelectItem(groupName, groupName);
		slfGroup.setSelectedIndex(((DefaultListModel) slfGroup.getModel()).indexOf(itemG));
		doslfGroupisSelect();

		txtDepsn.setText(depsn);

		String deptName = FVGenericInfo.findDeptNameFolowDept(depsn);
		SelectItem itemD = new SelectItem(Vni2Uni.convertToUnicode(deptName), deptName);
		slfDept.setSelectedIndex(((DefaultListModel) slfDept.getModel()).indexOf(itemD));
		doslfDeptisSelect();

	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		getToolbar().getComponent(0).setVisible(false);
		getToolbar().getComponent(2).setVisible(false);
		getToolbar().getComponent(4).setVisible(false);
		getToolbar().getComponent(3).setVisible(false);
		getToolbar().getComponent(1).setVisible(false);
		return ret;
	}

	@Override
	protected HSSFWorkbook generateWorkbook() throws IOException {
		if (!btnExcel.isEnabled())
			return null;

		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance", "DSNgayCongBH_Temp.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int startRow = 1;
		List<EmployeePlain> listData = new ArrayList<EmployeePlain>();
		switch (mode) {
		case MODETOANDV:
			listData = listAll;
			break;
		case MODEKHONGDUCONG:
			listData = listKo;
			break;
		case MODEDUCONG:
			listData = listDu;
			break;
		case MODETRUBH:
			wb = generateWBTRUBH();
			setReportFileName(getEXCEL_FILE_TITLE() + "_0" + stt);
			return wb;
		case MODENGHISAN:
			wb = generateWBNS();
			setReportFileName(getEXCEL_FILE_TITLE() + "_0" + stt);
			return wb;
		default:
			break;
		}
		if (listData.size() <= 0)
			listData = new ArrayList<EmployeePlain>();
		for (int i = 0; i < listData.size(); i++) {
			EmployeePlain emp = listData.get(i);
			if (mode == MODEKHONGDUCONG && emp.getStatus() == -1)
				continue;
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
			if (emp.getSignDate() != null)
				cell.setCellValue(emp.getSignDate());
			cell = row.createCell(10);
			cell.setCellValue(emp.getLabourKind());
			cell = row.createCell(11);
			cell.setCellValue(emp.getDucls());
			cell = row.createCell(12);
			cell.setCellValue(emp.getMaternityLeave());
			cell = row.createCell(13);
			cell.setCellValue(emp.getPaidVacation());
			cell = row.createCell(14);
			cell.setCellValue(emp.getAbsentWithoutLeave());
			cell = row.createCell(15);
			cell.setCellValue(emp.getDayOFF());
			cell = row.createCell(16);
			// ts ngay khong luong = ngay ko di lam + nghi co phep + nghi san + khang cong
			cell.setCellValue(
					emp.getMaternityLeave() + emp.getPaidVacation() + emp.getAbsentWithoutLeave() + emp.getDayOFF());
			cell = row.createCell(17);
			cell.setCellValue(emp.getComSalary() * tyleBHXH);// bao hiem xa hoi
			cell = row.createCell(18);
			cell.setCellValue(emp.getComSalary() * tyleBHTN);// bao hiem that nghiep
			cell = row.createCell(19);
			if (emp.getQuitDay() != null)
				cell.setCellValue(emp.getQuitDay());
			cell = row.createCell(20);
			cell.setCellValue(emp.getQuitReason());

		}

		setReportFileName(getEXCEL_FILE_TITLE() + "_0" + stt);

		return wb;
	}

	private HSSFWorkbook generateWBNS() {
		// lay du lieu
		listNS = ins.getListNghiSan(getName(), slfNam.getSelectedItem() + "", slfThang.getSelectedItem() + "");
		if (listNS.size() <= 0) {
			return null;
		}

		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance", "DSNS_Temp.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int startRow = 1;
		for (int i = 0; i < listNS.size(); i++) {

			EmployeeBear emp = listNS.get(i);
			row = sheet.createRow(startRow++);
			cell = row.createCell(0);
			cell.setCellValue(emp.getEMPSN());
			cell = row.createCell(1);
			cell.setCellValue(emp.getNAME());
			cell = row.createCell(2);
			cell.setCellValue(emp.getDEPSN());
			cell = row.createCell(3);
			cell.setCellValue(emp.getNAME_DEPT());
			cell = row.createCell(4);
			cell.setCellValue(emp.getB_DATES());
			cell = row.createCell(5);
			cell.setCellValue(emp.getE_DATES());
			cell = row.createCell(6);
			cell.setCellValue(emp.getNUM());
		}
		return wb;
	}

	private HSSFWorkbook generateGCNV() {

		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance", "DSGCNV.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int startRow = 1;
		for (int i = 0; i < listAll.size(); i++) {

			EmployeePlain emp = listAll.get(i);
			row = sheet.createRow(startRow++);
			cell = row.createCell(0);
			cell.setCellValue(emp.getEmpsn());
			cell = row.createCell(1);
			cell.setCellValue(emp.getFname() + " " + emp.getLname());
			cell = row.createCell(2);
			cell.setCellValue(emp.getDeptID());
			cell = row.createCell(3);
			cell.setCellValue(emp.getDeptName());
			cell = row.createCell(4);
			cell.setCellValue(emp.getBasicSalary());
			cell = row.createCell(5);
			cell.setCellValue(emp.getComSalary());
			cell = row.createCell(6);
			if (emp.getQuitDay() != null)
				cell.setCellValue(emp.getQuitDay());
			cell = row.createCell(7);
			cell.setCellValue(emp.getQuitReason());
		}

		setReportFileName(getEXCEL_FILE_TITLE());
		return wb;
	}

	private HSSFWorkbook generateWBTRUBH() {
		// lay du lieu
		listTRU = ins.getListTruBH(getName(), slfNam.getSelectedItem() + "", slfThang.getSelectedItem() + "");
		if (listTRU.size() <= 0) {
			return null;
		}

		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance", "DSTRUBH_Temp.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int startRow = 1;
		for (int i = 0; i < listTRU.size(); i++) {
			N_EMP_QUIT emp = listTRU.get(i);
			row = sheet.createRow(startRow++);
			cell = row.createCell(0);
			cell.setCellValue(emp.getDEPSN());
			cell = row.createCell(1);
			cell.setCellValue(emp.getDEPT_NAME());
			cell = row.createCell(2);
			cell.setCellValue(emp.getEMPSN());
			cell = row.createCell(3);
			cell.setCellValue(emp.getNOTE());
			cell = row.createCell(4);
			cell.setCellValue(emp.getREAL_OFF_DATE());
			cell = row.createCell(5);
			cell.setCellValue(emp.getTHANG_TRUBH());
		}
		return wb;
	}

	public String getName() {
		String name = "";
		if (slfFact.getSelectedIndex() > -1)
			name += slfFact.getSelectedItem().toString() + ".";
		if (slfGroup.getSelectedIndex() > -1)
			name += slfGroup.getSelectedItem().toString() + ".";
		if (slfDept.getSelectedIndex() > -1)
			name += Vni2Uni.convertToVNI(slfDept.getSelectedItem().toString());
		return name;
	}

	public String getNameExcel() {
		String name = "";
		if (slfFact.getSelectedIndex() > -1)
			name += slfFact.getSelectedItem().toString();
		// if(slfGroup.getSelectedIndex()>-1)
		// name+=slfGroup.getSelectedItem().toString()+".";
		if (slfDept.getSelectedIndex() > -1)
			name += "." + txtDepsn.getText();// Vni2Uni.convertToVNI(slfDept.getSelectedItem().toString());//ten file ko
												// dc co dau.
		// name = name.replaceAll(" ", "-");

		return name;
	}

	private String getEXCEL_FILE_TITLE() {
		if (mode == MODEDUCONG) {
			return "DS_CNV_" + getNameExcel() + "_duDKmuaBHXH-TN_" + slfNam.getSelectedItem().toString()
					+ slfThang.getSelectedItem().toString();
		}
		if (mode == MODEKHONGDUCONG) {
			return "DS_CNV_" + getNameExcel() + "_koduDKmuaBHXH-TN_" + slfNam.getSelectedItem().toString()
					+ slfThang.getSelectedItem().toString();
		}
		if (mode == MODETOANDV) {
			return "DS_CNV_" + getNameExcel() + "_toanbo_" + slfNam.getSelectedItem().toString()
					+ slfThang.getSelectedItem().toString();
		}
		if (mode == MODETRUBH) {
			return "DS_CNV_" + getNameExcel() + "_truBHtrongthang_" + slfNam.getSelectedItem().toString()
					+ slfThang.getSelectedItem().toString();
		}
		if (mode == MODENGHISAN) {
			return "DS_CNV_" + getNameExcel() + slfNam.getSelectedItem().toString() + "_nghisan_"
					+ slfThang.getSelectedItem().toString();
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public void doActLoadList() {// chi khoa khi chay du lieu thuong ko khoa khi chay du lieu nghi viec, troi
									// oiiiiiiiiiiiiiiiiiiiiiii
									// phai co quyen cap nhat thi moi duoc cap nhat
		RightInfo infor = this.getRightInfo();
		boolean iscapnhat = infor.isCost();

		// neu chon nhan vien nghi viec thi khong xet dieu kien thoi gian.
		Date d = new Date(System.currentTimeMillis());
		int year = d.getYear() + 1900;
		int month = d.getMonth() + 1;
		int day = d.getDate();
		int slfm = Integer.valueOf(slfThang.getSelectedItem().toString());
		int slfy = Integer.valueOf(slfNam.getSelectedItem().toString());
		final String id_bhxh_tn = slfNam.getSelectedItem().toString() + slfThang.getSelectedItem().toString();

		// if ckbGhiChuNV dc chon thi lay ds tai day
		if (ckbGhiChuNV.isSelected()) {
			listAll = new ArrayList<EmployeePlain>();
			listAll = ins.getListEmpGCNV(getName(), slfNam.getSelectedItem() + "", slfThang.getSelectedItem() + "");
			if (listAll.size() <= 0) {
				Application.getApp().showMessageDialog("Thông Báo", "Không có dữ liệu.", MessageBox.OK);
				return;
			} else {
				btnExcel.setEnabled(true);
				Application.getApp().showMessageDialog("Thông Báo", "Hoàn tất.", MessageBox.OK);
				return;
			}

		}

		if (!ckbNghiViec.isSelected()) {
			if (slfy > year || (slfy == year && slfm > month)) {
				dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("Lỗi", "Chọn tháng năm không hợp lệ",
						MessageDialog.CONTROLS_OK);
				Application.getApp().getDefaultWindow().getContent().add(dlg);

				return;
			}
			if (slfy == year && slfm == month) {
				if (stt == 2 || (stt == 1 && day < 17)) {
					dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("Lỗi",
							"Chọn tháng năm không hợp lệ", MessageDialog.CONTROLS_OK);
					Application.getApp().getDefaultWindow().getContent().add(dlg);

					return;
				}
			}
		}

		if (slfFact.getSelectedIndex() < 0) {
			int s = 0;
			if (!ins.checkQLyNDept(null, null, null)) {
				Application.getApp().showMessageDialog("Thông Báo",
						"Bạn không có quyền thao tác trên tất cả các xưởng.", MessageBox.OK);
				return;
			} else {
				// neu cac xuong chua cap nhat du lieu thanh cong thi ko cho xuat
				String sql = "select t.name_fact from n_social_status t where t.id_report ='MAU' and t.name_fact not in"
						+ "(select a.name_fact from n_social_status a where a.name_fact = t.name_fact "
						+ "and a.id_report='0" + stt + id_bhxh_tn + "' and a.status = 1)";
				OBJ_UTILITY obj = new OBJ_UTILITY();
				List<String> listData = obj.Exe_Sql_String(sql);
				if (listData.size() > 0 && !ckbNghiViec.isSelected()) {
					String tb = "Xưởng ";
					for (int i = 0; i < listData.size(); i++) {
						tb += listData.get(i) + " ";
					}
					tb += "chưa cập nhật dữ liệu!";
					Application.getApp().showMessageDialog("Thông báo", tb, MessageDialog.CONTROLS_OK);
					return;
				}
			}
		}
		// phai xet quyen them cho xuong vao cho nay
		else {
			boolean b = ins.checkQLyNDept(slfFact.getSelectedItem().toString(),
					slfGroup.getSelectedIndex() > 0 ? slfGroup.getSelectedItem().toString() : null,
					slfDept.getSelectedIndex() > 0 ? Vni2Uni.convertToVNI(slfDept.getSelectedItem().toString()) : null);
			if (!b) {
				slfFact.setSelectedIndex(-1);
				slfGroup.setSelectedIndex(-1);
				slfDept.setSelectedIndex(-1);
				txtDepsn.setText("");
				Application.getApp().showMessageDialog("Thông Báo", "Bạn không có quyền thao tác trên xưởng này.",
						MessageBox.OK);
				return;
			}
		}

		// xet chua chay lan 1 ko dc chay lan 2(ca ALL va tung xuong)
		if (stt == 2) {
			String name = "";
			if (slfFact.getSelectedIndex() < 0)
				name = "ALL";
			else
				name = slfFact.getSelectedItem().toString();

			if (ins.getSimpleJdbcTemplate()
					.queryForInt("select count(t.name_fact) from n_social_status t where t.name_fact =? "
							+ "and t.id_report = ? and t.status ='1'", name, "01" + id_bhxh_tn) < 1
					&& !ckbNghiViec.isSelected()) {

				Application.getApp().showMessageDialog("Thông Báo", "Xưởng này chưa chạy dữ liệu lần 1", MessageBox.OK);
				return;
			}
		}

		listAll = new ArrayList<EmployeePlain>();
		listDu = new ArrayList<EmployeePlain>();
		listKo = new ArrayList<EmployeePlain>();
		Object[] arrObj = ins.getListEmp(getName(), slfNam.getSelectedItem() + "", slfThang.getSelectedItem() + "", stt,
				ckbNghiViec.isSelected());
		listAll = (List<EmployeePlain>) arrObj[0];
		listDu = (List<EmployeePlain>) arrObj[1];
		listKo = (List<EmployeePlain>) arrObj[2];

		if (listAll.size() <= 0) {
			Application.getApp().showMessageDialog("Thông Báo", "Không có dữ liệu.", MessageBox.OK);
			return;
		} else {
			btnExcel.setEnabled(true);
			flag = true;
			// kiem tra neu tong bo da xuat du lieu thi ko cho save
			if (ins.getSimpleJdbcTemplate()
					.queryForInt("select count(t.name_fact) from n_social_status t where t.name_fact =?"
							+ " and t.id_report =? and t.status = 1", "ALL", "0" + stt + id_bhxh_tn) == 1) {
				Application.getApp().showMessageDialog("Thông Báo", "Hoàn tất.", MessageBox.OK);
				return;
			}
			// Chi save khi chay nguyen 1 xuong hoac toan cong ty
			if (slfFact.getSelectedIndex() > 0) {
				if (!getName().equals(slfFact.getSelectedItem().toString() + ".") || ckbNghiViec.isSelected()
						|| !iscapnhat) {
					/// co van de : ko luu ALL vao bang trang thai (1)
					/// ---------------------------------
					Application.getApp().showMessageDialog("Thông Báo", "Hoàn tất.", MessageBox.OK);
					return;
				}
			} else {
				Application.getApp().showMessageDialog("Thông Báo", "Hoàn tất.", MessageBox.OK);
				return;
			}

			dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("THÔNG BÁO",
					"Hoàn tất.\nBạn có muốn cập nhật ký trình BHXH_TN", MessageDialog.CONTROLS_YES_NO);
			dlg.setId("moi");
			dlg.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals(MessageDialog.COMMAND_OK)) {
						doSave();
					} else {// (1): C.Uyen xuat se ko chay toi day de luu vao bang status duoc
							// -----------------------------
							// neu la tong bo xuat du lieu va ckbNghiViec ko dc chon thi insert All vao bang
							// status de khoa ko cho chay lai du lieu
						if (slfFact.getSelectedIndex() < 0 && !ckbNghiViec.isSelected()) {
							ins.getSimpleJdbcTemplate().update("insert into n_social_status values(?,?,?,?,?,?)", "ALL",
									"0" + stt + id_bhxh_tn, 1, "", new Date(System.currentTimeMillis()),
									Application.getApp().getLoginInfo().getUserID());
						}
					}
				}
			});
			// Application.getApp().getDefaultWindow().getContent().add(dlg);
			dlg.show();
		}
	}

	public void doSave() {
		String id_bhxh_tn = slfNam.getSelectedItem().toString() + slfThang.getSelectedItem().toString();
		if (slfFact.getSelectedIndex() < 0) {
			callPro();
			// insert All, va cac xuong vao bang status de khoa ko cho chay lai du lieu
			if (!ckbNghiViec.isSelected())
				if (ins.getSimpleJdbcTemplate().queryForInt(
						"select count(t.name_fact) from n_social_status t "
								+ "where t.id_report = ? and t.name_fact = ?",
						new Object[] { "0" + stt + id_bhxh_tn, "ALL" }) == 0)
					ins.getSimpleJdbcTemplate().update("insert into n_social_status values(?,?,?,?,?,?)", "ALL",
							"0" + stt + id_bhxh_tn, 1, "", new Date(System.currentTimeMillis()),
							Application.getApp().getLoginInfo().getUserID());
				else
					ins.getSimpleJdbcTemplate().update(
							"update n_social_status t set t.status ='1' where t.id_report = ? and t.name_fact = ? ",
							"0" + stt + id_bhxh_tn, "ALL");

		} else {// xuong chay
				// Kiem tra neu xuong da chay thanh cong thi ko cho chay lai
			if (ins.getSimpleJdbcTemplate().queryForInt(
					"select count(t.name_fact) from n_social_status t where t.name_fact =?"
							+ " and t.id_report =? and t.status = 1",
					slfFact.getSelectedItem().toString(), "0" + stt + id_bhxh_tn) == 1) {
				Application.getApp().showMessageDialog("Thông Báo", "Đã khóa.", MessageBox.OK);
				return;
			}
			callPro();
			if (!ckbNghiViec.isSelected())
				if (ins.getSimpleJdbcTemplate().queryForInt(
						"select count(t.name_fact) from n_social_status t "
								+ "where t.id_report = ? and t.name_fact = ?",
						new Object[] { "0" + stt + id_bhxh_tn, slfFact.getSelectedItem().toString() }) == 0)
					ins.getSimpleJdbcTemplate().update("insert into n_social_status values(?,?,?,?,?,?)",
							slfFact.getSelectedItem().toString(), "0" + stt + id_bhxh_tn, 1, "",
							new Date(System.currentTimeMillis()), Application.getApp().getLoginInfo().getUserID());
				else
					ins.getSimpleJdbcTemplate().update(
							"update n_social_status t set t.status ='1' where t.id_report = ? and t.name_fact = ? ",
							"0" + stt + id_bhxh_tn, slfFact.getSelectedItem().toString());

		}

	}

	public void callPro() {
		// goi pro luu bang// test lai, thu goi pro12 va proAll cung luc xem co qua 30
		// phut ko-----------------------
		StopWatch sw = new StopWatch();
		sw.reset();
		sw.start();
		if (stt == 1) {
			ins.insertListAll01(listAll, slfNam.getSelectedItem().toString(), slfThang.getSelectedItem().toString(),
					Application.getApp().getLoginInfo().getUserID());

			// khoa trong data_daily tu 01-16 theo xuong
			ins.getSimpleJdbcTemplate()
					.update("update n_data_daily t set t.locked = '1' where t.dates between to_date(?,'dd/mm/yyyy') "
							+ "and to_date(?,'dd/mm/yyyy') "
							+ "and t.depsn in (select d.id_dept from n_department d where d.name_fact = ? and d.id_dept = t.depsn)",
							"01/" + slfThang.getSelectedItem().toString() + "/" + slfNam.getSelectedItem().toString(),
							"16/" + slfThang.getSelectedItem().toString() + "/" + slfNam.getSelectedItem().toString(),
							slfFact.getSelectedItem().toString());

			Application.getApp().showMessageDialog("Thông Báo", "Hoàn tất.", MessageBox.OK);
			sw.stop();
			System.out.println("Run in " + (int) ((float) sw.getTime() / 60000) + ":"
					+ (int) (((float) sw.getTime() % 60000) / 1000) + " s");
		} else {
			ins.insertListAll02(listDu, listKo, slfNam.getSelectedItem().toString(),
					slfThang.getSelectedItem().toString(), Application.getApp().getLoginInfo().getUserID());
			sw.stop();
			System.out.println("Run in " + (int) ((float) sw.getTime() / 60000) + ":"
					+ (int) (((float) sw.getTime() % 60000) / 1000) + " s");

			dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("THÔNG BÁO",
					"Hoàn tất 1/3.\nNhấn Ok để tiếp tục.", MessageDialog.CONTROLS_OK);
			dlg.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals(MessageDialog.COMMAND_OK)) {
						StopWatch sw = new StopWatch();
						sw.reset();
						sw.start();
						// chi Anh ko nho co buoc nay de lam j nen tam thoi ko cho chay. mac du cho chay
						// luon nhung van
						// chua nho la gi het hehehehe
						ins.insertListAll12(listAll, slfNam.getSelectedItem().toString(),
								slfThang.getSelectedItem().toString(), Application.getApp().getLoginInfo().getUserID());

						sw.stop();
						System.out.println("Run in " + (int) ((float) sw.getTime() / 60000) + ":"
								+ (int) (((float) sw.getTime() % 60000) / 1000) + " s");

						dsc.echo2app.MessageDialog dlg1 = new dsc.echo2app.MessageDialog("THÔNG BÁO",
								"Hoàn tất 1/3.\nHoàn tất 2/3.\nNhấn Ok để tiếp tục.", MessageDialog.CONTROLS_OK);
						dlg1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if (e.getActionCommand().equals(MessageDialog.COMMAND_OK)) {
									StopWatch sw = new StopWatch();
									sw.reset();
									sw.start();

									ins.insertListAll(listAll, slfNam.getSelectedItem().toString(),
											slfThang.getSelectedItem().toString(),
											Application.getApp().getLoginInfo().getUserID());
									Application.getApp().showMessageDialog("Thông Báo", "Hoàn tất.", MessageBox.OK);
									sw.stop();
									System.out.println("Run in " + (int) ((float) sw.getTime() / 60000) + ":"
											+ (int) (((float) sw.getTime() % 60000) / 1000) + " s");

								}
							}
						});
						dlg1.show();

					}
				}
			});
			dlg.show();

		}
	}

	@Override
	protected void doRefresh() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void doReset() {
		txtDepsn.setText("");
		slfFact.setSelectedIndex(-1);
		slfGroup.setSelectedIndex(-1);
		slfDept.setSelectedIndex(-1);
		slfGroup.removeAll();
		slfDept.removeAll();
		ckbLan1.setSelected(true);
		ckbGhiChuNV.setSelected(false);
		ckbNghiViec.setSelected(false);
		if (rbtnAll.isSelected() || rbtnDu.isSelected() || rbtnKhongDu.isSelected())
			btnExcel.setEnabled(false);
		flag = false;

	}

	private void moreInit() {
		slfNam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rbtnAll.isSelected() || rbtnDu.isSelected() || rbtnKhongDu.isSelected())
					btnExcel.setEnabled(false);
				flag = false;
			}
		});
		slfThang.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rbtnAll.isSelected() || rbtnDu.isSelected() || rbtnKhongDu.isSelected())
					btnExcel.setEnabled(false);
				flag = false;
			}
		});
		slfFact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doslfFactisSelect();
				if (rbtnAll.isSelected() || rbtnDu.isSelected() || rbtnKhongDu.isSelected())
					btnExcel.setEnabled(false);
				flag = false;
			}
		});
		slfGroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doslfGroupisSelect();
				if (rbtnAll.isSelected() || rbtnDu.isSelected() || rbtnKhongDu.isSelected())
					btnExcel.setEnabled(false);
				flag = false;
			}
		});
		slfDept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doslfDeptisSelect();
				if (rbtnAll.isSelected() || rbtnDu.isSelected() || rbtnKhongDu.isSelected())
					btnExcel.setEnabled(false);
				flag = false;
			}
		});
		txtDepsn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtDepsn.setText(txtDepsn.getText().toUpperCase());
				dotxtDepsnAct();
				if (rbtnAll.isSelected() || rbtnDu.isSelected() || rbtnKhongDu.isSelected())
					btnExcel.setEnabled(false);
				flag = false;
			}
		});
		txtDepsn.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				txtDepsn.setText(txtDepsn.getText().toUpperCase());
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});

		rbtnKhongDu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mode = MODEKHONGDUCONG;
				if (!flag)
					btnExcel.setEnabled(false);
			}
		});
		rbtnDu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mode = MODEDUCONG;
				if (!flag)
					btnExcel.setEnabled(false);
			}
		});
		rbtnAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mode = MODETOANDV;
				if (!flag)
					btnExcel.setEnabled(false);
			}
		});
		rbtnTruBH.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mode = MODETRUBH;
				btnExcel.setEnabled(true);
			}
		});
		rbtnNghiSan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mode = MODENGHISAN;
				btnExcel.setEnabled(true);
			}
		});
		btnDS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doActLoadList();
			}
		});
		ckbLan1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ckbLan1.isSelected())
					stt = 1;
				else
					stt = 2;
				if (rbtnAll.isSelected() || rbtnDu.isSelected() || rbtnKhongDu.isSelected())
					btnExcel.setEnabled(false);
				flag = false;
			}
		});
		ckbNghiViec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rbtnAll.isSelected() || rbtnDu.isSelected() || rbtnKhongDu.isSelected())
					btnExcel.setEnabled(false);
				flag = false;
				ckbGhiChuNV.setSelected(false);
				grbDS.setEnabled(!ckbGhiChuNV.isSelected());
				ckbLan1.setSelected(false);
				stt = 2;
			}
		});
		ckbGhiChuNV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rbtnAll.isSelected() || rbtnDu.isSelected() || rbtnKhongDu.isSelected())
					btnExcel.setEnabled(false);
				flag = false;
				ckbNghiViec.setSelected(false);
				grbDS.setEnabled(!ckbGhiChuNV.isSelected());
			}
		});
		btnExcel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doExcel();
			}
		});

	}

	private void doExcel() {
		try {
			if (ckbGhiChuNV.isSelected())
				doExportExcel(generateGCNV());
			else
				doExportExcel(generateWorkbook());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doExportExcel(HSSFWorkbook wb) throws IOException {
		if (wb == null) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Không có dữ liệu.");
			return;
		}
		File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
		f.deleteOnExit();
		FileOutputStream out = new FileOutputStream(f);
		wb.write(out);
		out.flush();
		out.close();
		File saveFile = new File(f.getParentFile(),
				URLEncoder.encode(getLoginInfo().getUserID() + ";application/vnd.ms-excel;" + getReportFileName() + "_"
						+ Calendar.getInstance().getTimeInMillis() + ".xls", "UTF-8"));
		ReportFileManager.saveTempReportFile(f, saveFile);
		saveFile.deleteOnExit();
		Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
	}

	public void buttonTabOn(Button btn) {
		btn.setBackground(new Color(0xc4d3ff));
		btn.setBorder(new Border(new Extent(2, Extent.PX), new Color(0xc4d3ff), Border.STYLE_OUTSET));
	}

	public void buttonTabOff(Button btn) {
		btn.setBackground(new Color(0xdbdbdb));
		btn.setBorder(new Border(new Extent(2, Extent.PX), new Color(0xdbdbdb), Border.STYLE_SOLID));
	}

	@Override
	protected void doSearch() {
		// TODO Auto-generated method stub

	}

	/**
	 * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(315, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane1.setResizable(false);
		add(splitPane1);
		splMain = new SplitPane();
		splMain.setSeparatorPosition(new Extent(270, Extent.PX));
		splMain.setOrientation(SplitPane.ORIENTATION_HORIZONTAL_LEFT_RIGHT);
		splitPane1.add(splMain);
		Column column1 = new Column();
		splMain.add(column1);
		Row row2 = new Row();
		column1.add(row2);
		ckbLan1 = new CheckBox();
		ckbLan1.setSelected(true);
		ckbLan1.setText("Chạy ký trình lần 1");
		ckbLan1.setRolloverForeground(new Color(0x43afc2));
		ckbLan1.setRolloverEnabled(true);
		ckbLan1.setForeground(new Color(0x136993));
		RowLayoutData ckbLan1LayoutData = new RowLayoutData();
		ckbLan1LayoutData.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(5, Extent.PX)));
		ckbLan1.setLayoutData(ckbLan1LayoutData);
		row2.add(ckbLan1);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(5, Extent.PX)));
		grid1.setColumnWidth(0, new Extent(65, Extent.PX));
		column1.add(grid1);
		Label label1 = new Label();
		label1.setText("Tháng");
		grid1.add(label1);
		slfThang = new SelectField();
		slfThang.setWidth(new Extent(150, Extent.PX));
		grid1.add(slfThang);
		Label label2 = new Label();
		label2.setText("Năm");
		grid1.add(label2);
		slfNam = new SelectField();
		slfNam.setWidth(new Extent(150, Extent.PX));
		grid1.add(slfNam);
		GroupBox groupBox1 = new GroupBox();
		groupBox1.setTitle("Chọn đơn vị");
		groupBox1.setBorder(new Border(new Extent(1, Extent.PX), new Color(0xc0c0c0), Border.STYLE_GROOVE));
		ColumnLayoutData groupBox1LayoutData = new ColumnLayoutData();
		groupBox1LayoutData.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(0, Extent.PX),
				new Extent(10, Extent.PX), new Extent(0, Extent.PX)));
		groupBox1.setLayoutData(groupBox1LayoutData);
		column1.add(groupBox1);
		Grid grid2 = new Grid();
		grid2.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(5, Extent.PX)));
		grid2.setColumnWidth(0, new Extent(50, Extent.PX));
		groupBox1.add(grid2);
		Label label4 = new Label();
		label4.setText("Mã ĐV");
		grid2.add(label4);
		txtDepsn = new DscField();
		txtDepsn.setWidth(new Extent(143, Extent.PX));
		grid2.add(txtDepsn);
		Label label5 = new Label();
		label5.setText("Xưởng");
		grid2.add(label5);
		slfFact = new SelectField();
		slfFact.setWidth(new Extent(150, Extent.PX));
		grid2.add(slfFact);
		Label label6 = new Label();
		label6.setText("Nhóm");
		grid2.add(label6);
		slfGroup = new SelectField();
		slfGroup.setWidth(new Extent(150, Extent.PX));
		grid2.add(slfGroup);
		Label label7 = new Label();
		label7.setText("Đơn vị");
		grid2.add(label7);
		slfDept = new SelectField();
		slfDept.setWidth(new Extent(150, Extent.PX));
		grid2.add(slfDept);
		ckbGhiChuNV = new CheckBox();
		ckbGhiChuNV.setText("Ghi Chú Nghỉ Việc");
		ckbGhiChuNV.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(5, Extent.PX)));
		groupBox1.add(ckbGhiChuNV);
		ckbNghiViec = new CheckBox();
		ckbNghiViec.setText("CNV Nghỉ Việc");
		ckbNghiViec.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(5, Extent.PX)));
		groupBox1.add(ckbNghiViec);
		SplitPane splitPane3 = new SplitPane();
		splitPane3.setSeparatorPosition(new Extent(0, Extent.PX));
		splitPane3.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splMain.add(splitPane3);
		Row row1 = new Row();
		splitPane3.add(row1);
		Column column2 = new Column();
		splitPane3.add(column2);
		grbDS = new GroupBox();
		grbDS.setTitle("Loại DS báo cáo");
		grbDS.setHeight(new Extent(225, Extent.PX));
		grbDS.setWidth(new Extent(250, Extent.PX));
		ColumnLayoutData grbDSLayoutData = new ColumnLayoutData();
		grbDSLayoutData.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(10, Extent.PX)));
		grbDS.setLayoutData(grbDSLayoutData);
		column2.add(grbDS);
		rbtnKhongDu = new RadioButton();
		rbtnKhongDu.setText("DS không đủ công");
		rbtnKhongDu.setSelected(true);
		ButtonGroup bg = new ButtonGroup();
		rbtnKhongDu.setGroup(bg);
		rbtnKhongDu.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(10, Extent.PX)));
		rbtnKhongDu.setRolloverForeground(new Color(0x43afc2));
		rbtnKhongDu.setRolloverEnabled(true);
		rbtnKhongDu.setForeground(new Color(0x136993));
		rbtnKhongDu.setDisabledForeground(new Color(0x808080));
		grbDS.add(rbtnKhongDu);
		rbtnDu = new RadioButton();
		rbtnDu.setText("DS đủ công");
		rbtnDu.setGroup(bg);
		rbtnDu.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(10, Extent.PX)));
		rbtnDu.setRolloverForeground(new Color(0x43afc2));
		rbtnDu.setRolloverEnabled(true);
		rbtnDu.setForeground(new Color(0x136993));
		rbtnDu.setDisabledForeground(new Color(0x808080));
		grbDS.add(rbtnDu);
		rbtnAll = new RadioButton();
		rbtnAll.setText("DS toàn bộ");
		rbtnAll.setGroup(bg);
		rbtnAll.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(10, Extent.PX)));
		rbtnAll.setRolloverForeground(new Color(0x43afc2));
		rbtnAll.setRolloverEnabled(true);
		rbtnAll.setForeground(new Color(0x136993));
		rbtnAll.setDisabledForeground(new Color(0x808080));
		grbDS.add(rbtnAll);
		rbtnTruBH = new RadioButton();
		rbtnTruBH.setText("DS có tháng trừ BH");
		rbtnTruBH.setGroup(bg);
		rbtnTruBH.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(10, Extent.PX)));
		rbtnTruBH.setRolloverForeground(new Color(0x43afc2));
		rbtnTruBH.setRolloverEnabled(true);
		rbtnTruBH.setForeground(new Color(0x136993));
		rbtnTruBH.setDisabledForeground(new Color(0x808080));
		grbDS.add(rbtnTruBH);
		rbtnNghiSan = new RadioButton();
		rbtnNghiSan.setText("DS nghỉ sản");
		rbtnNghiSan.setGroup(bg);
		rbtnNghiSan.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(10, Extent.PX)));
		rbtnNghiSan.setRolloverForeground(new Color(0x43afc2));
		rbtnNghiSan.setRolloverEnabled(true);
		rbtnNghiSan.setForeground(new Color(0x136993));
		rbtnNghiSan.setDisabledForeground(new Color(0x808080));
		grbDS.add(rbtnNghiSan);
		btnExcel = new Button();
		btnExcel.setEnabled(false);
		btnExcel.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnExcel.setText("XUẤT FILE EXCEL");
		btnExcel.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_INSET));
		btnExcel.setWidth(new Extent(267, Extent.PX));
		ColumnLayoutData btnExcelLayoutData = new ColumnLayoutData();
		btnExcelLayoutData.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(0, Extent.PX)));
		btnExcel.setLayoutData(btnExcelLayoutData);
		btnExcel.setRolloverBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_OUTSET));
		btnExcel.setBackground(new Color(0x43afc2));
		btnExcel.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		btnExcel.setTextAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnExcel.setPressedEnabled(true);
		btnExcel.setDisabledBorder(new Border(new Extent(2, Extent.PX), new Color(0xc0c0c0), Border.STYLE_GROOVE));
		btnExcel.setDisabledBackground(new Color(0xc0c0c0));
		btnExcel.setBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_GROOVE));
		btnExcel.setForeground(Color.WHITE);
		btnExcel.setRolloverEnabled(true);
		column2.add(btnExcel);
		Column column3 = new Column();
		splitPane1.add(column3);
		Row row3 = new Row();
		column3.add(row3);
		btnDS = new Button();
		btnDS.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnDS.setText("CHẠY DỮ LIỆU & LẤY DANH SÁCH");
		btnDS.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_INSET));
		btnDS.setRolloverBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_OUTSET));
		btnDS.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		btnDS.setBackground(new Color(0x43afc2));
		btnDS.setPressedEnabled(true);
		btnDS.setDisabledBorder(new Border(new Extent(2, Extent.PX), new Color(0xc0c0c0), Border.STYLE_GROOVE));
		btnDS.setDisabledBackground(new Color(0xc0c0c0));
		btnDS.setBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_GROOVE));
		btnDS.setRolloverEnabled(true);
		btnDS.setForeground(Color.WHITE);
		RowLayoutData btnDSLayoutData = new RowLayoutData();
		btnDSLayoutData.setInsets(new Insets(new Extent(8, Extent.PX), new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		btnDSLayoutData.setWidth(new Extent(540, Extent.PX));
		btnDS.setLayoutData(btnDSLayoutData);
		row3.add(btnDS);
	}
}
