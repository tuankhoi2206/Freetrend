package ds.program.fvhr.ui.workpoints;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import ds.program.fvhr.dao.generic.FvGenericDAO;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_LOST_DATA_DETAIL;
import ds.program.fvhr.domain.N_REGISTER_SHIFT;
import ds.program.fvhr.domain.N_SHIFT;
import ds.program.fvhr.domain.pk.N_LOST_DATA_DETAILPk;
import ds.program.fvhr.domain.pk.N_REGISTER_SHIFTPk;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscField;
import nextapp.echo2.app.CheckBox;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import fv.components.SelectItem;
import fv.util.ApplicationHelper;
import fv.util.DateUtils;
import fv.util.DbUtils;
import fv.util.FvStringUtils;
import fv.util.HRUtils;
import fv.util.ListBinder;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.ListBox;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Row;

public class WPCorrectBatchPane extends WindowPane {

	private ResourceBundle resourceBundle;
	private WorkPointCorrection01MProgram program;
	private String errorMessage;
	private DscField txtSothe;
	private CheckBox chkDenSoThe;
	private DscDateField dfFromDate;
	private CheckBox chkDenNgay;
	private DscDateField dfToDate;
	private CheckBox chkCN;
	private SelectField sfLyDo;
	private SelectField sfCaLamViec;
	private DscField txtGioVao;
	private DscField txtGioRa;
	private Button btnSave;
	private ListBox lstEmpsn;
	private DscField txtSothe2;
	private Button btnClear;
	private CheckBox chkGioVao;
	private CheckBox chkGioRa;
	
	/**
	 * Creates a new <code>WPCorrectBatchPane</code>.
	 */
	public WPCorrectBatchPane() {
		super();

		// Add design-time configured components.
		initComponents();
		dfFromDate.getDateChooser().setLocale(new Locale("en"));
		dfToDate.getDateChooser().setLocale(new Locale("en"));
		dfFromDate.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dfToDate.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dfFromDate.setSelectedDate(Calendar.getInstance());
		dfToDate.setSelectedDate(Calendar.getInstance());
		dfToDate.getTextField().setDisabledBackground(new Color(0x808080));
	}

	public WPCorrectBatchPane(WorkPointCorrection01MProgram program){
		this();
		this.program=program;
		ListBinder.bindSelectField(sfLyDo, program.getMasterDataContent().reasonEditor(), true);
		ListBinder.bindSelectField(sfCaLamViec, program.getMasterDataContent().shiftEditor(), true);
		txtSothe.requestFocus();
	}
	
	public void focusSoThe(){
		txtSothe.requestFocus();
	}

	private void chkSoTheClicked(ActionEvent e) {
		if (chkDenSoThe.isSelected()){
			txtSothe2.setEnabled(true);
		}else{
			txtSothe2.setEnabled(false);
		}
	}

	private void chkDenNgayClicked(ActionEvent e) {
		if (chkDenNgay.isSelected()){
			dfToDate.setEnabled(true);
		}else{
			dfToDate.setEnabled(false);
		}
	}

	public void setProgram(WorkPointCorrection01MProgram program) {		
		this.program = program;
	}

	public WorkPointCorrection01MProgram getProgram() {
		return program;
	}

	private void shiftHint(ActionEvent e) {
		SelectItem item = (SelectItem) sfCaLamViec.getSelectedItem();
		N_SHIFT shift = (N_SHIFT) item.getValue();
		String tin = shift.getTIME_IN().replace(":", "");
		String tout = shift.getTIME_OUT().replace(":", "");
		txtGioVao.setText(tin);
		txtGioRa.setText(tout);
	}
	
	@SuppressWarnings("unchecked")
	private String findShift(String empsn, Date date){
		IGenericDAO<N_REGISTER_SHIFT, N_REGISTER_SHIFTPk> dao = Application.getApp().getDao(N_REGISTER_SHIFT.class);
		N_REGISTER_SHIFTPk pk = new N_REGISTER_SHIFTPk(empsn, date);
		N_REGISTER_SHIFT shift = dao.findById(pk);
		if (shift==null){
			IGenericDAO<N_EMPLOYEE, String> edao = Application.getApp().getDao(N_EMPLOYEE.class);
			N_EMPLOYEE emp = edao.findById(empsn);
			return emp.getSHIFT();
		}
		return shift.getID_SHIFT();
	}

	private boolean validateForm(){
		//empsn
//		String empsn = txtSothe.getText();
//		if (!empsn.matches("[0-9]{8}")){
//			setErrorMessage("Số thẻ không hợp lệ");
//			return false;
//		}
//
//		if (chkDenSoThe.isSelected()){
//			String empsn2 = txtSothe2.getText();
//			if (!empsn2.matches("[0-9]{8}")){
//				setErrorMessage("Số thẻ 2 không hợp lệ");
//				return false;
//			}
//			String e1 = StringUtils.substring(empsn, 0, 6);
//			String e2 = StringUtils.substring(empsn2, 0, 6);
//			Integer e12 = Integer.parseInt(StringUtils.substring(empsn, 6, 8));
//			Integer e22 = Integer.parseInt(StringUtils.substring(empsn2, 6, 8));
//			//			System.out.println(e1 + " " + e2 + " " + e12 + " " + e22);
//			if(!e1.equals(e2)){
//				setErrorMessage("6 số đầu của số thẻ 1 phải giống với 6 số đầu của số thẻ 2");
//				return false;
//			}
//			if (e22<=e12){
//				setErrorMessage("Số thẻ 2 phải có giá trị lớn hơn số thẻ 1");
//				return false;
//			}
//		}
		
		DefaultListModel model = (DefaultListModel)lstEmpsn.getModel();
		if (model.size()<=0){
			setErrorMessage("Không có số thẻ nào trong danh sách");
			return false;
		}

		if (dfFromDate.getSelectedDate()==null){
			setErrorMessage("Chọn ngày");
			return false;
		}

		if (chkDenNgay.isSelected()){
			//			if (dfToDate.getSelectedDate().compareTo(Calendar.getInstance())>0){
			//				setErrorMessage("Ngày thay đổi dữ liệu không được lớn hơn ngày hiện tại");
			//				return false;
			//			}

			if (dfFromDate.getSelectedDate().compareTo(dfToDate.getSelectedDate())>=0){
				setErrorMessage("Ngày 1 phải nhỏ hơn ngày 2");
				return false;
			}

			if (dfToDate.getSelectedDate().compareTo(dfFromDate.getSelectedDate())>31){
				setErrorMessage("Chỉ chọn tối đa 1 tháng");
				return false;
			}
			
			//check all shift...me, rac roi qua
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date d1 = null;
			Date d2 = null;
			Calendar ca1=null, ca2=null;
			try {
				d1 = sdf.parse(dfFromDate.getText());
				ca1 = Calendar.getInstance();
				ca1.setTimeInMillis(d1.getTime());

			} catch (ParseException e1) {
				e1.printStackTrace();
			}

			List<Date> dates = new ArrayList<Date>();
			dates.add(d1);
			try {
				d2 = sdf.parse(dfToDate.getText());
				ca2 = Calendar.getInstance();
				ca2.setTimeInMillis(d2.getTime());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			Calendar c1 = Calendar.getInstance();
			c1.setTime(d1);
			c1.add(Calendar.DAY_OF_MONTH, 1);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(d2);
			while (c2.compareTo(c1)>=0){
				if (!chkCN.isSelected()){
					if (c1.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
						c1.add(Calendar.DAY_OF_MONTH, 1);
						continue;
					}
				}
				Date date = new Date();
				date.setTime(c1.getTimeInMillis());
				dates.add(date);
				c1.add(Calendar.DAY_OF_MONTH, 1);
			}
			Set<String> shifts = new HashSet<String>();
			for (int i=0;i<model.size();i++){
				String empsn = StringUtils.substringBefore(model.get(i).toString(), "_");
				for (Date date:dates){
					String shift = findShift(empsn, date);
					shifts.add(shift);
				}
			}
			if (shifts.size()>1){
				setErrorMessage("Lỗi: nhiều ca làm việc khác nhau");
				return false;
			}
		}

		if (sfLyDo.getSelectedIndex()<0){
			setErrorMessage("Chọn lý do");
			return false;
		}

		if (sfCaLamViec.getSelectedIndex()<0){
			setErrorMessage("Chọn ca làm việc");
			return false;
		}

		String gioVao = txtGioVao.getText().trim();
		String gioRa = txtGioRa.getText().trim();

		if ((!chkGioRa.isSelected()&&!chkGioVao.isSelected())||(StringUtils.isBlank(gioVao)&&StringUtils.isBlank(gioRa))){
			setErrorMessage("Nhập thời gian");
			return false;
		}
		if (chkGioVao.isSelected()&&!StringUtils.isBlank(gioVao)){
			if (gioVao.matches("[0-9]{4}")){
				int hour = Integer.parseInt(gioVao.substring(0,2));
				int minute = Integer.parseInt(gioVao.substring(2, 4));
				if (hour>23||hour<0||minute>59||minute<0){
					setErrorMessage("Giờ vào không hợp lệ");
					return false;
				}
			}else{
				setErrorMessage("Giờ vào không hợp lệ");
				return false;
			}
		}
		if(chkGioRa.isSelected()&&!StringUtils.isBlank(gioRa)){
			if (gioRa.matches("[0-9]{4}")){
				int hour = Integer.parseInt(gioRa.substring(0,2));
				int minute = Integer.parseInt(gioRa.substring(2, 4));
				if (hour>23||hour<0||minute>59||minute<0){
					setErrorMessage("Giờ ra không hợp lệ");
					return false;
				}

			}else{
				setErrorMessage("Giờ ra không hợp lệ");
				return false;
			}
		}	

		return true;
	}

	private void setErrorMessage(String string) {
		this.errorMessage=string;
	}

	private void doSave(ActionEvent e) {
		if (!validateForm()){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, errorMessage);
			return;
		}

		String st1=null,st2=null;
		Calendar ca1=null, ca2=null;
		List<String> emps = new ArrayList<String>();
//		emps.add(txtSothe.getText());
		st1=txtSothe.getText();
		if (chkDenSoThe.isSelected()&&!txtSothe2.getText().equals("")){
//			Integer e1 = Integer.parseInt(txtSothe.getText());
			Integer e2 = Integer.parseInt(txtSothe2.getText());
//			for (int i=e1+1;i<=e2;i++){
//				String emp = FvStringUtils.fixEmpsn(i);
//				emps.add(emp);
//			}
			st2 = FvStringUtils.fixEmpsn(e2);
		}
		
		DefaultListModel model = (DefaultListModel)lstEmpsn.getModel();
		for (int i=0;i<model.size();i++){
			String data = model.get(i).toString();
			data = StringUtils.substringBefore(data, "_");
			emps.add(data);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = sdf.parse(dfFromDate.getText());
			ca1 = Calendar.getInstance();
			ca1.setTimeInMillis(d1.getTime());

		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		List<Date> dates = new ArrayList<Date>();
		dates.add(d1);
		if (chkDenNgay.isSelected()){
			try {
				d2 = sdf.parse(dfToDate.getText());
				ca2 = Calendar.getInstance();
				ca2.setTimeInMillis(d2.getTime());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			Calendar c1 = Calendar.getInstance();
			c1.setTime(d1);
			c1.add(Calendar.DAY_OF_MONTH, 1);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(d2);
			while (c2.compareTo(c1)>=0){
				if (!chkCN.isSelected()){
					if (c1.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
						c1.add(Calendar.DAY_OF_MONTH, 1);
						continue;
					}
				}
				Date date = new Date();
				date.setTime(c1.getTimeInMillis());
				dates.add(date);
				c1.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		String lyDo = ListBinder.get(sfLyDo).toString();
		Set<String> signsMap = program.getMasterDataContent().getSignsMap();
		String gioVao = txtGioVao.getText().trim();
		if (!chkGioVao.isSelected()) gioVao="";
		String gioRa = txtGioRa.getText().trim();
		if (!chkGioRa.isSelected()) gioRa="";
		String message="";
		Set<String> eemps = new HashSet<String>();
		for (String empsn:emps){
			if (!FvGenericDAO.getInstanse().isWorking(empsn, true)){
				message=message+"\r\n" + empsn + " - nghỉ việc";
				continue;
			}
			for (Date date:dates){
				System.out.println("Save lost data: " + empsn + " " + new SimpleDateFormat("dd/MM/yyyy").format(date));
				N_LOST_DATA_DETAIL data = new N_LOST_DATA_DETAIL();
				data.setEMPSN(empsn);
				data.setDATE_LOST(date);
				data.setREASONS(lyDo);
				data.setTIN(gioVao);
				data.setTOUT(gioRa);
				data.setSIGN_TIME(0l);
				if (signsMap.contains(lyDo)){
					if (!StringUtils.isBlank(data.getTIN())) data.setSIGN_TIME(data.getSIGN_TIME()+1l);
					if (!StringUtils.isBlank(data.getTOUT())||!StringUtils.isBlank(data.getTOVER())) data.setSIGN_TIME(data.getSIGN_TIME()+1l);
				}
				
				HRUtils util = ApplicationHelper.getHRUtils();
				if (!util.getPermissionValidator().hasEmpsnPermission(data.getEMPSN())){
					message=message+"\r\nKhông có quyền thao tác số thẻ "+data.getEMPSN();
					continue;
				}

				String month = DateUtils.getMonth(data.getDATE_LOST());
				String year = DateUtils.getYear(data.getDATE_LOST());

				if (util.getWorkpointsValidator().isWPLocked(data.getEMPSN(), month, year)){
					message=message+"\r\nĐã khóa dữ liệu số thẻ " + data.getEMPSN() + " tháng "+month+"/"+year;
					continue;
				}
				eemps.add(data.getEMPSN());
				save(data);
			}
		}
		String sql = "o.EMPSN in " + DbUtils.parseInStringParamValues(eemps);
		Object[] params;
		if (chkDenNgay.isSelected()){
			sql = sql + " and o.DATE_LOST>=? and o.DATE_LOST<=?";
			params = new Object[]{d1, d2};
		}else{
			sql = sql + " and o.DATE_LOST=?";
			params = new Object[]{d1};
		}
		program.setSearchToolbarCriteria(st1, st2, ca1, ca2);
		ProgramCondition pc = new ProgramCondition(sql, params);
		program.setQueryCondition(pc);
		program.refresh();
		if (!message.equals("")){
			message="\r\nLỗi: "+message;
		}
		Application.getApp().showMessageDialog(MessageDialog.TYPE_INFORMATION+MessageDialog.CONTROLS_OK, "Đã lưu thông tin. Chọn tìm kiếm để kiểm tra lại. " + message);

		userClose();
	}

	private void save(N_LOST_DATA_DETAIL data) {
		SelectItem item = (SelectItem) sfCaLamViec.getSelectedItem();
		N_SHIFT shift = (N_SHIFT) item.getValue();
		N_LOST_DATA_DETAIL ex = program.getMasterDataContent().ca3(data, shift);
		IGenericDAO<N_LOST_DATA_DETAIL, N_LOST_DATA_DETAILPk> dao = Application.getApp().getDao(N_LOST_DATA_DETAIL.class);
		N_LOST_DATA_DETAILPk pk = new N_LOST_DATA_DETAILPk(data.getEMPSN(), data.getDATE_LOST());
		N_LOST_DATA_DETAIL data1 = dao.findById(pk);
		if (data1==null){
			dao.save(data);
			System.out.println("Save " + data.getEMPSN() + " " + data.getDATE_LOST());
		}else{
			data1.setSIGN_TIME(data.getSIGN_TIME());
			//HA
			//if nhap XNDL ca dem tu ngay 01~02 thi
			// if ngay 01 va ngay 02 la QQ, luc nay ngay 03 cung da co record voi ly do la QQ
			// sau do ngay 03 lai XNDL tiep voi ly do la di cong tac, luc nay ly do trong row 3 la CT chu ko con la QQ nua
			// kiem tra ly do truoc va sau update if khac nhau thi lay ly do new	
			data1.setREASONS(data.getREASONS());
			//end HA
			data1.setTIN(data.getTIN());
			data1.setTOUT(data.getTOUT());			
			dao.update(data1);
			System.out.println("Update " + data1.getEMPSN() + " " + data1.getDATE_LOST());
		}
		if (ex!=null){
			N_LOST_DATA_DETAILPk pk1 = new N_LOST_DATA_DETAILPk(ex.getEMPSN(), ex.getDATE_LOST());
			N_LOST_DATA_DETAIL data2 = dao.findById(pk1);
			if (data2==null){
				dao.save(ex);
				System.out.println("Save ex " + ex.getEMPSN() + " " + ex.getDATE_LOST());
			}else{
				data2.setTTEMP(ex.getTTEMP());
				dao.update(data2);
				System.out.println("Update ex " + data2.getEMPSN() + " " + data2.getDATE_LOST());
			}
		}
	}

	private void loadInfo(ActionEvent e) {
		//		TextField field = (TextField) e.getSource();
		//		if (field.getText().matches("[0-9]{8}")){
		//			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ không hợp lệ");
		//			return;
		//		}
		String empsn = txtSothe.getText();
		String empsn2;
		if (!empsn.matches("[0-9]{8}")){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ 1 không hợp lệ");
			return;
		}

//		if (chkDenSoThe.isSelected()){
		empsn2 = txtSothe2.getText();
		if (!empsn2.matches("[0-9]{8}")){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ 2 không hợp lệ");
			return;
		}
		String e1 = StringUtils.substring(empsn, 0, 4);
		String e2 = StringUtils.substring(empsn2, 0, 4);
		Integer e12 = Integer.parseInt(StringUtils.substring(empsn, 4, 8));
		Integer e22 = Integer.parseInt(StringUtils.substring(empsn2, 4, 8));
		//			System.out.println(e1 + " " + e2 + " " + e12 + " " + e22);
		if(!e1.equals(e2)){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "6 số đầu của số thẻ 1 phải giống với 6 số đầu của số thẻ 2");
			return;
		}
		if (e22<=e12){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ 2 phải có giá trị lớn hơn số thẻ 1");
			return;
		}
//		}
		//clear list
		DefaultListModel model = (DefaultListModel)lstEmpsn.getModel();
//		model.removeAll();
//		lstEmpsn.setSelectedIndices(new int[]{});
		List<String> emps = new ArrayList<String>();
		emps.add(txtSothe.getText());
//		if (chkDenSoThe.isSelected()){
			Integer ea1 = Integer.parseInt(txtSothe.getText());
			Integer ea2 = Integer.parseInt(txtSothe2.getText());
			for (int i=ea1+1;i<=ea2;i++){
				String emp = FvStringUtils.fixEmpsn(i);
				emps.add(emp);
			}
//		}
		HRUtils util = ApplicationHelper.getHRUtils();
		for (String emp:emps){
			if (!FvGenericDAO.getInstanse().isWorking(emp, false)){
				continue;
			}

			if (!util.getPermissionValidator().hasEmpsnPermission(emp)){
				continue;
			}
			String empna = FvGenericDAO.getInstanse().getFullName(emp);
			String data = emp+"_"+empna;
			boolean exist=false;
			for (int i=0;i<model.size();i++){
				if (model.get(i).equals(data)){
					exist=true;
					break;
				}
			}
			if (!exist)
				model.add(data);
		}

	}

	private void addEmpsn(ActionEvent e) {
		String empsn = txtSothe.getText();
		if (!empsn.matches("[0-9]{8}")){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ không hợp lệ");
			return;
		}
		HRUtils util = ApplicationHelper.getHRUtils();
		if (!FvGenericDAO.getInstanse().isWorking(empsn, false)){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Nhân viên nghỉ việc");
			return;
		}

		if (!util.getPermissionValidator().hasEmpsnPermission(empsn)){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Anh/chị không có quyền thao tác số thẻ đang chọn");
			return;
		}
		String empna = FvGenericDAO.getInstanse().getFullName(empsn);
		String data = empsn+"_"+empna;
		DefaultListModel model = (DefaultListModel)lstEmpsn.getModel();
		boolean exist=false;
		for (int i=0;i<model.size();i++){
			if (model.get(i).equals(data)){
				exist=true;
				break;
			}
		}
		if (!exist){
			model.add(data);
			autoSelectShift();
		}
		//bind shift
		
		////////////
		txtSothe.requestFocus();
	}
	
	protected void autoSelectShift() {
		if (txtSothe.getText().matches("[0-9]{8}")){
			String empsn = txtSothe.getText();
			Calendar date = dfFromDate.getSelectedDate();
			IGenericDAO<N_REGISTER_SHIFT, N_REGISTER_SHIFTPk> dao = Application.getApp().getDao(N_REGISTER_SHIFT.class);
			N_REGISTER_SHIFTPk pk = new N_REGISTER_SHIFTPk(empsn, date.getTime());
			N_REGISTER_SHIFT shift = dao.findById(pk);
			IGenericDAO<N_SHIFT, String> shiftDao = Application.getApp().getDao(N_SHIFT.class);
			if (shift!=null){
				N_SHIFT shift1 = shiftDao.findById(shift.getID_SHIFT());
				ListBinder.refreshIndex(sfCaLamViec, shift1);
			}else{
				IGenericDAO<N_EMPLOYEE, String> edao = Application.getApp().getDao(N_EMPLOYEE.class);
				N_EMPLOYEE emp = edao.findById(empsn);
				N_SHIFT shift1 = shiftDao.findById(emp.getSHIFT());
				ListBinder.refreshIndex(sfCaLamViec, shift1);
			}
			if (sfCaLamViec.getSelectedIndex()>=0)
				shiftHint(null);
		}
	}


	private void doClearList(ActionEvent e) {
		DefaultListModel model = (DefaultListModel)lstEmpsn.getModel();
		model.removeAll();
		lstEmpsn.setSelectedIndices(new int[]{});
	}

	private void chkGioVaoSelected(ActionEvent e) {
		if (chkGioVao.isSelected()){
			txtGioVao.setEnabled(true);
		}else{
			txtGioVao.setEnabled(false);
		}
	}

	private void chkGioRaSelected(ActionEvent e) {
		if (chkGioRa.isSelected())
			txtGioRa.setEnabled(true);
		else
			txtGioRa.setEnabled(false);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Default.Window");
		this.setTitle("Xác nhận dữ liệu nhiều số thẻ, nhiều ngày");
		this.setHeight(new Extent(430, Extent.PX));
		this.setWidth(new Extent(720, Extent.PX));
		this.setModal(true);
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(420, Extent.PX));
		splitPane1.setResizable(true);
		add(splitPane1);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(2, Extent.PX)));
		splitPane1.add(grid1);
		Label label8 = new Label();
		label8.setText("1. Nhập số thẻ và nhấn enter để thêm số thẻ vào danh sách");
		label8.setFont(new Font(null, Font.ITALIC, new Extent(9, Extent.PT)));
		label8.setForeground(new Color(0x808080));
		GridLayoutData label8LayoutData = new GridLayoutData();
		label8LayoutData.setColumnSpan(2);
		label8.setLayoutData(label8LayoutData);
		grid1.add(label8);
		Label label11 = new Label();
		label11.setText("2. Nhập vào 2 ô số thẻ và enter để thêm những số thẻ liên tiếp trong khoảng từ số thẻ 1 -> số thẻ 2");
		label11.setFont(new Font(null, Font.ITALIC, new Extent(9, Extent.PT)));
		label11.setForeground(new Color(0x808080));
		GridLayoutData label11LayoutData = new GridLayoutData();
		label11LayoutData.setColumnSpan(2);
		label11.setLayoutData(label11LayoutData);
		grid1.add(label11);
		Label label1 = new Label();
		label1.setText("Số thẻ");
		grid1.add(label1);
		txtSothe = new DscField();
		txtSothe.setWidth(new Extent(80, Extent.PX));
		txtSothe.setMaximumLength(8);
		txtSothe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEmpsn(e);
			}
		});
		grid1.add(txtSothe);
		chkDenSoThe = new CheckBox();
		chkDenSoThe.setText("đến số thẻ");
		chkDenSoThe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkSoTheClicked(e);
			}
		});
		grid1.add(chkDenSoThe);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(1, Extent.PX));
		grid1.add(row1);
		txtSothe2 = new DscField();
		txtSothe2.setEnabled(false);
		txtSothe2.setWidth(new Extent(80, Extent.PX));
		txtSothe2.setDisabledBackground(new Color(0x808080));
		txtSothe2.setMaximumLength(8);
		txtSothe2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadInfo(e);
			}
		});
		row1.add(txtSothe2);
		btnClear = new Button();
		btnClear.setText("Xóa danh sách");
		btnClear.setBackground(Color.WHITE);
		btnClear.setToolTipText("Xóa danh sách số thẻ");
		btnClear.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x800000), Border.STYLE_SOLID));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doClearList(e);
			}
		});
		row1.add(btnClear);
		Label label2 = new Label();
		label2.setText("Từ ngày");
		grid1.add(label2);
		dfFromDate = new DscDateField();
		grid1.add(dfFromDate);
		chkDenNgay = new CheckBox();
		chkDenNgay.setText("Đến ngày");
		chkDenNgay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkDenNgayClicked(e);
			}
		});
		grid1.add(chkDenNgay);
		dfToDate = new DscDateField();
		dfToDate.setEnabled(false);
		grid1.add(dfToDate);
		Label label3 = new Label();
		grid1.add(label3);
		chkCN = new CheckBox();
		chkCN.setText("Có đi làm ngày chủ nhật");
		grid1.add(chkCN);
		Label label4 = new Label();
		label4.setText("Lý do");
		grid1.add(label4);
		sfLyDo = new SelectField();
		sfLyDo.setWidth(new Extent(250, Extent.PX));
		grid1.add(sfLyDo);
		Label label9 = new Label();
		label9.setText("Ca làm việc");
		grid1.add(label9);
		sfCaLamViec = new SelectField();
		sfCaLamViec.setWidth(new Extent(250, Extent.PX));
		sfCaLamViec.setDisabledBackground(new Color(0x808080));
		sfCaLamViec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shiftHint(e);
			}
		});
		grid1.add(sfCaLamViec);
		chkGioVao = new CheckBox();
		chkGioVao.setSelected(true);
		chkGioVao.setText("Giờ vào");
		chkGioVao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkGioVaoSelected(e);
			}
		});
		grid1.add(chkGioVao);
		txtGioVao = new DscField();
		txtGioVao.setWidth(new Extent(60, Extent.PX));
		txtGioVao.setDisabledBackground(new Color(0x808080));
		txtGioVao.setMaximumLength(4);
		grid1.add(txtGioVao);
		chkGioRa = new CheckBox();
		chkGioRa.setSelected(true);
		chkGioRa.setText("Giờ ra");
		chkGioRa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkGioRaSelected(e);
			}
		});
		grid1.add(chkGioRa);
		txtGioRa = new DscField();
		txtGioRa.setWidth(new Extent(60, Extent.PX));
		txtGioRa.setDisabledBackground(new Color(0x808080));
		txtGioRa.setMaximumLength(4);
		grid1.add(txtGioRa);
		Label label10 = new Label();
		grid1.add(label10);
		btnSave = new Button();
		btnSave.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btnSave.setText("Lưu");
		btnSave.setFont(new Font(null, Font.BOLD, new Extent(11, Extent.PT)));
		btnSave.setBackground(new Color(0x004080));
		btnSave.setWidth(new Extent(56, Extent.PX));
		btnSave.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnSave.setPressedEnabled(true);
		btnSave.setPressedForeground(new Color(0x804000));
		btnSave.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x800000), Border.STYLE_SOLID));
		btnSave.setRolloverForeground(new Color(0xff8040));
		btnSave.setRolloverEnabled(true);
		btnSave.setForeground(Color.WHITE);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSave(e);
			}
		});
		grid1.add(btnSave);
		Label label7 = new Label();
		label7.setText("Lưu ý: Sẽ lưu những số thẻ có trong danh sách");
		label7.setFont(new Font(null, Font.ITALIC, new Extent(9, Extent.PT)));
		GridLayoutData label7LayoutData = new GridLayoutData();
		label7LayoutData.setColumnSpan(2);
		label7.setLayoutData(label7LayoutData);
		grid1.add(label7);
		lstEmpsn = new ListBox();
		lstEmpsn.setHeight(new Extent(99, Extent.PERCENT));
		splitPane1.add(lstEmpsn);
	}
}
