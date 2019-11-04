package ds.program.fvhr.ui;

import org.apache.log4j.Logger;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;
import ds.program.fvhr.domain.N_BASIC_SALARY;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_EDUCATION_NEW;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_IC_CARD;
import ds.program.fvhr.domain.N_JOB;
import ds.program.fvhr.domain.N_POSITION;
import ds.program.fvhr.domain.N_SHIFT;
import ds.program.fvhr.domain.N_TRAINING_DETAILS;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscDateField;
import fv.util.ApplicationHelper;
import fv.util.DateUtils;
import fv.util.DbUtils;
import fv.util.FVGenericInfo;
import fv.util.JdbcDAO;
import fv.util.ListBinder;
import fv.util.RightsHolder;
import fv.util.Vni2Uni;
import fv.util.VniComparator;
import fv.util.VniEditor;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.CheckBox;
import echopointng.ComboBox;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.echo2app.component.DscGroupRadioButton;
//TODO: refactor - Hieu
public class Employee01MDataContent extends DataContent {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(Employee01MDataContent.class);

	private nextapp.echo2.app.Grid rootLayout;
	private Label EMPSN_CaptionLabel;
	private DscField EMPSN_DscField1;
	private Label FNAME_CaptionLabel;
	private DscField FNAME_DscField3;
	private Label LNAME_CaptionLabel;
	private DscField LNAME_DscField4;
	private Label BIRTHDAY_CaptionLabel;
	private DscField BIRTHDAY_DscField9;
	private Label ID_PLACE_CaptionLabel;
	private DscField ID_PLACE_DscField8;
	private Label BIRTHPLACE_CaptionLabel;
	private DscField BIRTHPLACE_DscField10;
	private Label ID_NO_CaptionLabel;
	private DscField ID_NO_DscField7;
	private Label NGAYCAP_ID_CaptionLabel;
	private DscDateField NGAYCAP_ID_DscDateField1;
	private Label SEX_CaptionLabel;
	private SelectField SEX_SelectField;
	private Label CITY_CaptionLabel;
	private SelectField CITY_SelectField1;
	private Label CONTACT_ADDRESS_CaptionLabel;
	private DscField CONTACT_ADDRESS_DscField12;
	private Label PERMANENT_ADDRESS_CaptionLabel;
	private DscField PERMANENT_ADDRESS_DscField13;
	private Label CONTACT_PER_CaptionLabel;
	private DscField CONTACT_PER_DscField15;
	private Label CONTACT_TEL_CaptionLabel;
	private DscField CONTACT_TEL_DscField14;
	private Label ETHNIC_CaptionLabel;
	private Label RELIGION_CaptionLabel;
	private Label EDUCATION_CaptionLabel;
	private Label TRUCTIEP_SX_CaptionLabel;
	private Label EMPCN_CaptionLabel;
	private DscField EMPCN_DscField2;
	private Label DATE_HIRED_CaptionLabel;
	private DscDateField DATE_HIRED_DscDateField2;
	private Label DEPSN_CaptionLabel;
	private Label ID_JOB_CaptionLabel;
	private Label SHIFT_CaptionLabel;
	private Label USER_MANAGE_ID_CaptionLabel;
	private Label WORK_STATUS_CaptionLabel;
	private Label NGAYNX_MOI_CaptionLabel;
	private ComboBox cboWorkStatus;
	private SelectField sfShift;
	private Label MATERIAL_STATUS_CaptionLabel;
	private SelectField sfManagerId;
	private JdbcDAO dao;
	private MappingPropertyEditor positionEditor;
	private ComboBox cboJob;
	private Label POSITION_CaptionLabel;
	private SelectField sfChucVu;
	private DscField txtLuongCoBan;
	private CheckBox chk85;
	private DscField txtPCCV;
	private DscField txtPhuCapNghe;
	private DscField txtPCCViec;
	private MappingPropertyEditor jobEditor;
	private CheckBox chkDat;
	private CheckBox chkKhongDat;
	private SelectField sfEducation;
	private SelectField sfMaterialStatus;
	private SelectField sfReligion;
	private SelectField sfEthnic;
	private Map<String, Object> saveObjects;
	private LT1NewEmployeePolicy lt1Policy;
	private LT2NewEmployeePolicy lt2Policy;
	private TGNewEmployeePolicy tgPolicy;
	private BPNewEmployeePolicy bpPolicy;
	
	private List<NewEmployeePolicy> pp;

	private MappingPropertyEditor depsnEditor;

	private DscDateField NGAYNX_MOI_DscDateField5;

	private CheckBox chkDD;

	private MPNewEmployeePolicy mpPolicy;

	private ComboBox cboDepsn;

	private DscGroupRadioButton ttgtGroup;

	/**
	 * Creates a new <code>Employee01MDataContent</code>.
	 */
	public Employee01MDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
		dao = new JdbcDAO();
		ListBinder.bindComboBox(cboWorkStatus, workStatusEditor());
		ListBinder.bindSelectField(sfChucVu, positionEditor(), true);
		jobEditor = jobEditor();
		ListBinder.bindComboBox(cboJob, jobEditor);
		cboJob.getTextField().setDisabledBackground(new Color(0xC8C8C8));
		DATE_HIRED_DscDateField2.getDateChooser().setLocale(new Locale("en"));
		DATE_HIRED_DscDateField2.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		NGAYNX_MOI_DscDateField5.getDateChooser().setLocale(new Locale("en"));		
		NGAYNX_MOI_DscDateField5.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		NGAYCAP_ID_DscDateField1.getDateChooser().setLocale(new Locale("en"));
		NGAYCAP_ID_DscDateField1.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
	}
	

	/*
	 * UI資料欄位屬性更新，一般是資料模式變換時會呼叫此method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doUIRefresh()
	 */
	@Override
	public void doUIRefresh() {
		String hokhau = PERMANENT_ADDRESS_DscField13.getText();
		super.doUIRefresh();
		if((this.getProgram().getDataMode() == IProgram.DATAMODE_NONE) || 
				(this.getProgram().getDataMode() == IProgram.DATAMODE_BROWSER)) {
			rootLayout.setEnabled(false);
			getProgram().getSearchField().setEnabled(true);
		} else {
			rootLayout.setEnabled(true);
			getProgram().getSearchField().setEnabled(false);
			//----------------------------------------------------------------------------------
			//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
			//以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
			//----------------------------------------------------------------------------------
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				PERMANENT_ADDRESS_DscField13.setText(hokhau);
				EMPSN_DscField1.setEnabled(true);
				txtLuongCoBan.setEnabled(true);
				txtPCCV.setEnabled(true);
				txtPCCViec.setEnabled(true);
				txtPhuCapNghe.setEnabled(true);
				cboDepsn.setEnabled(true);
				cboJob.setEnabled(true);
				sfChucVu.setEnabled(true);
				chkDat.setEnabled(true);
				chkKhongDat.setEnabled(true);
				EMPCN_DscField2.setEnabled(true);
				DATE_HIRED_DscDateField2.setEnabled(true);
				NGAYNX_MOI_DscDateField5.setEnabled(true);
				txtLuongCoBan.setText("");
				txtPCCV.setText("");
				txtPhuCapNghe.setText("");
				ID_NO_DscField7.setEnabled(true);
				sfShift.setEnabled(true);
				sfManagerId.setEnabled(true);
				ttgtGroup.setEnabled(true);
			} else {
				EMPSN_DscField1.setEnabled(false);
				txtLuongCoBan.setEnabled(false);
				txtPCCV.setEnabled(false);
				txtPCCViec.setEnabled(false);
				txtPhuCapNghe.setEnabled(false);
				cboDepsn.setEnabled(false);
				cboJob.setEnabled(false);
				sfChucVu.setEnabled(false);
				chkDat.setEnabled(false);
				chkKhongDat.setEnabled(false);
				EMPCN_DscField2.setEnabled(false);
				DATE_HIRED_DscDateField2.setEnabled(false);
				NGAYNX_MOI_DscDateField5.setEnabled(false);
				ID_NO_DscField7.setEnabled(false);
				sfShift.setEnabled(false);
				sfManagerId.setEnabled(false);
				ttgtGroup.setEnabled(false);
			}
		}

		//7.<資料權限管控>


		//自動設定元件風格
		getUIDataObjectBinder().doDataBindUIStyle();
	}

	/*
	 * 取得該畫面的DataObject Class
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#getDataObjectClass()
	 */
	@Override
	public Class getDataObjectClass() {
		return N_EMPLOYEE.class;
	}


	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */
	@Override
	public void saveUIToDataObject() {
		super.saveUIToDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		N_EMPLOYEE data = (N_EMPLOYEE) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
//			if (EMPCN_DscField2.getText().trim().equals("")){
//				setErrorMessage("Nhập số IC Card");
//				return false;
//			}
			if (txtLuongCoBan.getText().equals("")){
				setErrorMessage("Nhập lương cơ bản");
				return false;
			}
			if (!data.getEMPSN().matches("[0-9]{8}")){
				setErrorMessage("Số thẻ không hợp lệ.");
				return false;
			}
			if (!data.getID_NO().matches("[0-9]{9}")){
				setErrorMessage("Số CMND không hợp lệ.");
				return false;
			}
			
			//HA them vao 07/05/2013
			if ((data.getID_NO()!=null) && ((data.getNGAYCAP_ID().equals(null)) || (data.getID_PLACE().isEmpty()==true))){
				setErrorMessage("Nhập đầy đủ thông tin ngày cấp/ nơi cấp CMND");
				return false;
			}
			if (data.getPERMANENT_ADDRESS().isEmpty()){
				setErrorMessage("Nhập địa chỉ thường trú");
				return false;
			}
			//het HA			
			IGenericDAO<N_EMPLOYEE, String> dao = Application.getApp().getDao(N_EMPLOYEE.class);
			if (getProgram().getDataMode()==IProgram.DATAMODE_NEW){
				if (dao.findById(data.getEMPSN())!=null){
					setErrorMessage("Đã tồn tại nhân viên có số thẻ " + data.getEMPSN());
					return false;
				}
				if (data.getEMPCN()!=null){
					if (!data.getEMPCN().matches("[0-9]{10}")){
						setErrorMessage("Số thẻ IC không hợp lệ");
						return false;
					}
					String icsql = "from N_IC_CARD e where e.IC_NO=?";
					IGenericDAO<N_IC_CARD, String> icdao = Application.getApp().getDao(N_IC_CARD.class);
					List<N_IC_CARD> list = icdao.find(1, icsql, new Object[]{data.getEMPCN()});
					if (list!=null&&list.size()>0){
						if (!list.get(0).getUSE_STATUS().equals("0")){
							setErrorMessage("Số thẻ IC đã được sử dụng");
							return false;
						}
					}else{
						setErrorMessage("Số thẻ IC không tồn tại");
						return false;
					}
				}
			}//else kiem tra so the IC hop le => ko tuong thich voi CT cu
			
			if (chkDD.isSelected()){
				if (NGAYNX_MOI_DscDateField5.getText().equals("")){
					setErrorMessage("Chọn ngày nhập xưởng mới");
					return false;
				}
				
				Calendar nc = NGAYNX_MOI_DscDateField5.getSelectedDate();
				if (nc.get(Calendar.DAY_OF_MONTH)!=1){
					setErrorMessage("Ngày điều động khu phải là ngày 1 của tháng");
					return false;
				}
			}
			
//			if (!checkId(dao, data, getProgram().getDataMode())){
//				return false;
//			}
			
			if (!lt1Policy.isValid(data, getProgram().getDataMode()==IProgram.DATAMODE_EDIT)){
				setErrorMessage(lt1Policy.getErrorMessage());
				return false;
			}			
			
			for (NewEmployeePolicy policy:pp){
				if (!policy.isValid(data, false)){
					setErrorMessage(policy.getErrorMessage());
					return false;
				}
			}
			
			
			String bd = data.getBIRTHDAY();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date birthDay=null;
			try {
				//if chi co yyyy thi gan mac dinh la 01/01/yyyy
				System.out.println(bd);
				bd=bd.length()==4?"01/01/"+bd:bd;
				System.out.println(bd);
				birthDay=sdf.parse(bd);
			}catch (Exception e){
				setErrorMessage("Ngày sinh không hợp lệ (dd/mm/yyyy)");
				return false;
			}
			int[] dates = DateUtils.before(data.getDATE_HIRED(), birthDay);			
			//if dates[3]=-1 thi nam sinh > nam NX, nhap sai thoi gian, else la binh thuong
			if (dates[2]<18 || dates[3]==-1){
				setErrorMessage("Chưa đủ 18 tuổi.");
				return false;
			}
			if (data.getNGAYNX_MOI()!=null){
				Calendar c = Calendar.getInstance();
				c.setTime(data.getNGAYNX_MOI());
				if (c.get(Calendar.DAY_OF_MONTH)!=1){
					setErrorMessage("Ngày nhập xưởng mới phải là ngày 1 của tháng");
					return false;
				}
			}
		}
		return ret;
	}
	
	private MappingPropertyEditor workStatusEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		e.put("LAM VIEC BINH THUONG");
		e.put("NGHI VIEC");
		e.put("NGHI SAN");
		e.put("NGHI PHEP");
		e.put("NGHI KHONG PHEP");
		return e;
	}

	@Override
	public Employee01MProgram getProgram() {
		return (Employee01MProgram) super.getProgram();
	}
	
	public String getLCB(){
		return txtLuongCoBan.getText();
	}
	
	/*public boolean checkId(String id, Date date){
		IGenericDAO<N_EMPLOYEE, String> dao = Application.getApp().getDao(N_EMPLOYEE.class);
		N_EMPLOYEE emp = new N_EMPLOYEE();
		emp.setID_NO(id);
		emp.setNGAYNX_MOI(date);
		return checkId(dao, emp, IProgram.DATAMODE_NEW);
	}*/

	/*public boolean checkId(IGenericDAO<N_EMPLOYEE, String> dao, N_EMPLOYEE emp, int mode){
		//LT1
		logger.debug("Kiem tra so CMND " + emp.getID_NO());
		String sql = "from N_EMPLOYEE o where o.ID_NO=? and o.DATE_HIRED=(select max(t.DATE_HIRED) from N_EMPLOYEE t where t.ID_NO=o.ID_NO)";
		if (mode == IProgram.DATAMODE_EDIT){
			sql = sql + " and EMPSN!='" + emp.getEMPSN() + "'";
		}
		N_EMPLOYEE e = dao.findUnique(sql, new Object[]{emp.getID_NO()});
		if (e!=null){
			logger.debug("Su dung o LT1");
			String dept = e.getDEPSN();
			if (!dept.equals("00000")){//HH
				setErrorMessage("Số CMND đã được sử dụng cho số thẻ " + e.getEMPSN() + " (Linh Trung 1)");
				return false;
			}else{
				return checkNV(e.getEMPSN(), emp.getNGAYNX_MOI()==null?new Date():emp.getNGAYNX_MOI(), emp.getDEPSN(), true);
			}
		}else{
			e = getEmpsnLT2(emp.getID_NO());
			if (e!=null){
				logger.debug("Su dung o LT2");
				String dept = e.getDEPSN();
				if (!dept.equals("00000")){//HH
					setErrorMessage("Số CMND đã được sử dụng cho số thẻ " + e.getEMPSN() + " (Linh Trung 2)");
					return false;
				}else{
					return checkNV(e.getEMPSN(), emp.getNGAYNX_MOI()==null?new Date():emp.getNGAYNX_MOI(), emp.getDEPSN(), false);
				}
			}//else OK
		}
		return true;
	}*/	

	/*private boolean checkNV(String empsn, Date nnx, String depsn, boolean lt1){
		logger.debug("Kiem tra nghi viec");
		String sql = "select * from (select * from n_emp_quit t where t.empsn=? and t.real_off_date=(select max(a.real_off_date) from n_emp_quit a where t.empsn=a.empsn)) where rownum<2";
		SimpleJdbcDaoSupport dao;
		if (lt1){
			dao = this.dao;
		}else{
			dao = getProgram().getLT2DAO();
		}
		try {
			N_EMP_QUIT data = dao.getSimpleJdbcTemplate().queryForObject(sql, new ParameterizedRowMapper<N_EMP_QUIT>(){
				public N_EMP_QUIT mapRow(ResultSet rs, int rowNum) throws SQLException {
					N_EMP_QUIT d = new N_EMP_QUIT();
					d.setEMPSN(rs.getString("EMPSN"));
					d.setID_QUIT_REASON(rs.getBigDecimal("ID_QUIT_REASON"));
					d.setFACT_NAME(rs.getString("FACT_NAME"));
					d.setGROUP_NAME(rs.getString("GROUP_NAME"));
					d.setDEPT_NAME(rs.getString("DEPT_NAME"));
					d.setDEPSN(rs.getString("DEPSN"));
					d.setREAL_OFF_DATE(rs.getDate("REAL_OFF_DATE"));
					return d;
				}}, new Object[]{empsn});
			if (data!=null){//co du lieu nghi viec
				//kiem tra thoi gian nghi viec
				logger.debug("Co du lieu  nghi viec");
				int dates[] = DateUtils.before(nnx, data.getREAL_OFF_DATE());
				logger.debug(dates[0] + " ngay " + dates[1] + " thang " + dates[2] + " nam");
				
				if (dates[3]>0){//ngay nhap xuong>ngay thuc nghi
					if (dates[1]>=3){
						if (nghiDungLuat(dao, data.getID_QUIT_REASON(), lt1)){
							return true;
						}else{
							//check hoso
							String checklocksql = "select t.locked from n_emp_quit t where t.empsn=? and t.real_off_date=?";
							try{
								String check = dao.getSimpleJdbcTemplate().queryForObject(checklocksql, String.class, new Object[]{data.getEMPSN(), new java.sql.Date(data.getREAL_OFF_DATE().getTime())});
								if (check!=null&&check.equals("Y")){
									return true;
								}else{
									setErrorMessage("Nghỉ không đúng luật và chưa được chủ quản xác nhận.");
									return false;
								}
							}catch(Exception e){
								setErrorMessage("Nghỉ không đúng luật và chưa được chủ quản xác nhận.");
								return false;
							}
						}
					}else{
						if (nghiDungLuat(dao, data.getID_QUIT_REASON(), lt1)&&data.getDEPSN().equals(depsn)){
							return true;
						}else{
							depsnEditor().setValue(data.getDEPSN());
							String oldDept = depsnEditor().getAsText();
							depsnEditor().setValue(depsn);
							String newDept = depsnEditor().getAsText();
							setErrorMessage("Nghi việc không đúng luật.\r\n" +
									"Nghỉ việc cách đây " + dates[0] + " ngày " + dates[1] + " tháng " + dates[2] + " năm.\r\nĐơn vị cũ: " + oldDept + ", đơn vị mới: " + newDept);
							return false;
						}
					}
				}else{
					setErrorMessage("Ngày nhập xưởng mới phải lớn hơn ngày thực nghỉ gần đây nhất.");
					return false;
				}
			}//else du lieu khong hop le do nghi viec nhung ko co du lieu trong n_emp_quit
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	private boolean nghiDungLuat(SimpleJdbcDaoSupport dao, BigDecimal reasonId, boolean lt1){
		logger.debug("Kiem tra nghi dung luat");
		String sql = "select * from n_quit_reason t where t.id_quit_reason=?";
		N_QUIT_REASON data = dao.getSimpleJdbcTemplate().queryForObject(sql, new ParameterizedRowMapper<N_QUIT_REASON>(){
			public N_QUIT_REASON mapRow(ResultSet rs, int rowNum) throws SQLException {
				N_QUIT_REASON r = new N_QUIT_REASON();
				r.setID_QUIT_REASON(rs.getBigDecimal("ID_QUIT_REASON"));
				r.setNOTE1(rs.getString("NOTE1"));
				return r;
			}}, new Object[]{reasonId});
		if (data!=null){
			if (data.getNOTE1().equals("1")){
				return true;
			}else{
				if (lt1){
					if (reasonId.compareTo(BigDecimal.valueOf(5))==0||reasonId.compareTo(BigDecimal.valueOf(28))==0){
						return true;
					}else{
						return false;
					}
				}else{
					if (reasonId.compareTo(BigDecimal.valueOf(4))==0){
						return true;
					}else{
						return false;
					}
				}
			}
		}
		return false;
	}

	private N_EMPLOYEE getEmpsnLT2(String id){
		String sql = "select e.empsn, e.id_no, e.depsn from n_employee e where e.id_no=? and e.date_hired=(select max(t.date_hired) from n_employee t where t.id_no=e.id_no)";
		LT2JdbcDAO dao = getProgram().getLT2DAO();
		try{
			List<N_EMPLOYEE> list = dao.getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<N_EMPLOYEE>(){
				public N_EMPLOYEE mapRow(ResultSet rs, int rowNum) throws SQLException {
					N_EMPLOYEE emp = new N_EMPLOYEE();
					emp.setEMPSN(rs.getString("EMPSN"));
					emp.setID_NO(rs.getString("ID_NO"));
					emp.setDEPSN(rs.getString("DEPSN"));
					return emp;
				}}, new Object[]{id});
			if (list.size()>0){
				return list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}*/
	//ADD FILTER
	private MappingPropertyEditor depsnEditor(){
		MappingPropertyEditor de = FVGenericInfo.getDepartmentEditor();
		MappingPropertyEditor e = new MappingPropertyEditor();
		String[] disp = de.getDisplays();
		for (String str:disp){			
			de.setAsText(str);
			N_DEPARTMENT d = (N_DEPARTMENT) de.getValue();
			e.put(d.getNAME_DEPT(), str);
		}
		return e;
	}
	
	public MappingPropertyEditor getDepsnEditor(){
		return depsnEditor;
	}
	
	@SuppressWarnings("unchecked")
	protected MappingPropertyEditor shiftEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_SHIFT, String> dao = Application.getApp().getDao(N_SHIFT.class);
		List<N_SHIFT> list = dao.findAll(100);
		for (N_SHIFT shift:list){
			e.put("(" + shift.getID_SHIFT() + ") " + shift.getNAME_SHIFT(), shift.getID_SHIFT());
		}
		return e;
	}
	
	private MappingPropertyEditor positionEditor(){
		if (positionEditor==null){
			positionEditor = new MappingPropertyEditor();
			IGenericDAO<N_POSITION, String> dao = Application.getApp().getDao(N_POSITION.class);
			List<N_POSITION> list = dao.findAll(1000);
			for (N_POSITION pos:list){
				positionEditor.put(Vni2Uni.convertToUnicode(pos.getNAME_POSITION()), pos.getID_POSITION());
			}
		}
		return positionEditor;
	}
	
	private MappingPropertyEditor jobEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		String sql = "SELECT * FROM N_JOB T WHERE T.IN_FACT IN (SELECT B.NAME_FACT FROM N_USER_LIMIT A, N_QUANLY B WHERE A.MA_QL=B.MAQL AND A.MA_USER=?)";
		List<N_JOB> list = dao.getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<N_JOB>(){
			@Override
			public N_JOB mapRow(ResultSet rs, int rowNum) throws SQLException {
				N_JOB job = new N_JOB();
				job.setID_KEY(rs.getString("ID_KEY"));
				job.setNAME_JOB(rs.getString("NAME_JOB"));
				job.setMONEY(rs.getBigDecimal("MONEY"));
				job.setKIND_JOB(rs.getString("KIND_JOB"));
				return job;
			}}, new Object[]{ApplicationHelper.getVftUserId()});
		for (N_JOB job:list){
			e.put(Vni2Uni.convertToUnicode(job.getNAME_JOB()) + "(" + job.getKIND_JOB() + ")", job.getID_KEY());
		}
		return e;
	}

	/*
	 * 元件初始化Method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doInit()
	 */
	@Override
	protected int doInit() {
		int nRet = super.doInit();
		
		//1.註冊資料欄位之顯示方式
		registPropertyEditor();

		//2.設定資料欄位與 UI之 Binding資訊
		bindUI();

		return nRet;
	}

	private void registPropertyEditor() {
		VniEditor vni = new VniEditor();
		depsnEditor = depsnEditor();
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "CITY", cityEditor());
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "FNAME", vni);
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "LNAME", vni);
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "ID_PLACE", vni);
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "BIRTHPLACE", vni);
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "CONTACT_ADDRESS", vni);
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "PERMANENT_ADDRESS", vni);
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "CONTACT_PER", vni);
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "EDUCATION", vni);
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "RELIGION", vni);
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "ETHNIC", vni);
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "SEX", sexEditor());
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "POSITION", positionEditor());
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "TRUCTIEP_SX", ttsxEditor());
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "DEPSN", depsnEditor);
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "EDUCATION", educationEditor());
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "SHIFT", shiftEditor());
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "USER_MANAGE_ID", managerEditor());
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "MATERIAL_STATUS", materialStatusEditor());
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "RELIGION", religionEditor());
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "ETHNIC", ethnicEditor());
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "DATE_HIRED", PropertyEditors.createDateEditor("dd/MM/yyyy"));
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "NGAYCAP_ID", PropertyEditors.createDateEditor("dd/MM/yyyy"));
		getUIDataObjectBinder().registerCustomEditor(N_EMPLOYEE.class, "NGAYNX_MOI", PropertyEditors.createDateEditor("dd/MM/yyyy"));
	}

	private void bindUI() {
		getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);
		getUIDataObjectBinder().addBindMap("EMPCN", EMPCN_DscField2, EMPCN_CaptionLabel);
		getUIDataObjectBinder().addBindMap("FNAME", FNAME_DscField3, FNAME_CaptionLabel);
		getUIDataObjectBinder().addBindMap("LNAME", LNAME_DscField4, LNAME_CaptionLabel);
		getUIDataObjectBinder().addBindMap("SEX", SEX_SelectField, SEX_CaptionLabel);
		getUIDataObjectBinder().addBindMap("MATERIAL_STATUS", sfMaterialStatus, MATERIAL_STATUS_CaptionLabel);
		getUIDataObjectBinder().addBindMap("ID_NO", ID_NO_DscField7, ID_NO_CaptionLabel);
		getUIDataObjectBinder().addBindMap("ID_PLACE", ID_PLACE_DscField8, ID_PLACE_CaptionLabel);
		getUIDataObjectBinder().addBindMap("NGAYCAP_ID", NGAYCAP_ID_DscDateField1, NGAYCAP_ID_CaptionLabel);
		getUIDataObjectBinder().addBindMap("BIRTHDAY", BIRTHDAY_DscField9, BIRTHDAY_CaptionLabel);
		getUIDataObjectBinder().addBindMap("BIRTHPLACE", BIRTHPLACE_DscField10, BIRTHPLACE_CaptionLabel);
		getUIDataObjectBinder().addBindMap("CITY", CITY_SelectField1, CITY_CaptionLabel);
		getUIDataObjectBinder().addBindMap("CONTACT_ADDRESS", CONTACT_ADDRESS_DscField12, CONTACT_ADDRESS_CaptionLabel);
		getUIDataObjectBinder().addBindMap("PERMANENT_ADDRESS", PERMANENT_ADDRESS_DscField13, PERMANENT_ADDRESS_CaptionLabel);
		getUIDataObjectBinder().addBindMap("CONTACT_TEL", CONTACT_TEL_DscField14, CONTACT_TEL_CaptionLabel);
		getUIDataObjectBinder().addBindMap("CONTACT_PER", CONTACT_PER_DscField15, CONTACT_PER_CaptionLabel);
		getUIDataObjectBinder().addBindMap("ETHNIC", sfEthnic, ETHNIC_CaptionLabel);
		getUIDataObjectBinder().addBindMap("RELIGION", sfReligion, RELIGION_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DATE_HIRED", DATE_HIRED_DscDateField2, DATE_HIRED_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DEPSN", cboDepsn, DEPSN_CaptionLabel);
		getUIDataObjectBinder().addBindMap("EDUCATION", sfEducation, EDUCATION_CaptionLabel);
		getUIDataObjectBinder().addBindMap("NGAYNX_MOI", NGAYNX_MOI_DscDateField5, NGAYNX_MOI_CaptionLabel);
		getUIDataObjectBinder().addBindMap("SHIFT", sfShift, SHIFT_CaptionLabel);
		getUIDataObjectBinder().addBindMap("TRUCTIEP_SX", ttgtGroup, null);
		getUIDataObjectBinder().addBindMap("USER_MANAGE_ID", sfManagerId, USER_MANAGE_ID_CaptionLabel);
		getUIDataObjectBinder().addBindMap("WORK_STATUS", cboWorkStatus.getTextField(), WORK_STATUS_CaptionLabel);
		getUIDataObjectBinder().addBindMap("POSITION", sfChucVu, POSITION_CaptionLabel);
	}

	private MappingPropertyEditor cityEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		String[] str = {"HAØ NOÄI","HAØ GIANG","GIA LAI","ÑAÊK LAÊK","ÑAÊK NOÂNG","CAO BAÈNG",
				"TUYEÂN QUANG","LAØO CAI","ÑIEÄN BIEÂN","LAI CHAÂU","SÔN LA","YEÂN BAÙI","HOØA BÌNH",
				"THAÙI NGUYEÂN","LAÏNG SÔN","QUAÛNG NINH","BAÉC GIANG","PHUÙ THOÏ","VÓNH PHUÙC",
				"BAÉC NINH","HAØ TAÂY","HAÛI DÖÔNG","HAÛI PHOØNG","HÖNG YEÂN","THAÙI BÌNH","HAØ NAM",
				"NAM ÑÒNH","NINH BÌNH","THANH HOÙA","NGHEÄ AN","HAØ TÓNH","QUAÛNG BÌNH","QUAÛNG TRÒ",
				"THÖØA THIEÂN","ÑAØ NAÜNG","QUAÛNG NAM","QUAÛNG NGAÕI","BÌNH ÑÒNH","PHUÙ YEÂN","KHAÙNH HOØA",
				"NINH THUAÄN","BÌNH THUAÄN","KON TUM","LAÂM ÑOÀNG","BÌNH PHÖÔÙC","TAÂY NINH","BÌNH DÖÔNG","ÑOÀNG NAI",
				"VUÕNG TAØU","TP.HCM","LONG AN","TIEÀN GIANG","BEÁN TRE","TRAØ VINH","VÓNH LONG","ÑOÀNG THAÙP",
				"AN GIANG","KIEÂN GIANG","CAÀN THÔ","HAÄU GIANG","SOÙC TRAÊNG","BAÏC LIEÂU","CAØ MAU","BAÉC KAÏN"};
		List<String> list = new ArrayList<String>();
		for (int i=0;i<str.length;i++){
			list.add(Vni2Uni.convertToUnicode(str[i]));
		}
		Collections.sort(list, new VniComparator());
		for (String s:list){
			e.put(s, Vni2Uni.convertToVNI(s));
		}
		return e;
	}

	private MappingPropertyEditor ttsxEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		//e.put("Trực tiếp", "Y");
		//e.put("Gián tiếp", "N");
		e.put("Trực tiếp", "TT");
		e.put("Gián tiếp", "GT");		
		e.put("Giày Mẫu", "GM");
		e.put("Văn Phòng", "VP");		
		return e;
	}

	private MappingPropertyEditor sexEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		e.put("NAM", "NAM");
		e.put("NỮ", "NU");
		return e;
	}
	
	private MappingPropertyEditor educationEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<N_EDUCATION_NEW, String> dao = Application.getApp().getDao(N_EDUCATION_NEW.class);
		List<N_EDUCATION_NEW> list = dao.findAll(100);
		for (N_EDUCATION_NEW data:list){
			e.put(Vni2Uni.convertToUnicode(data.getNAME_EDUCATION()), data.getID_EDUCATION());
		}
		return e;
	}
	
	private MappingPropertyEditor managerEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		RightsHolder rh = (RightsHolder) Application.getSpringContext().getBean("rightHolder");
		List<String> rights = rh.getRights(Application.getApp().getLoginInfo().getUserID()).getRightList();
		for (String r:rights){
			e.put(r);
		}
		return e;
	}
	
	private MappingPropertyEditor materialStatusEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		e.put("LẬP GIA ĐÌNH", "Y");
		e.put("ĐỘC THÂN", "N");
		e.put("KHÔNG RÕ", "");
		return e;
	}
	
	private MappingPropertyEditor religionEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		e.put("KHÔNG","KHOÂNG");
		e.put("PHẬT GIÁO","PHAÄT GIAÙO");
		e.put("HÒA HẢO", "HOØA HAÛO");
		e.put("THIÊN CHÚA", "THIEÂN CHUÙA");
		e.put("TIN LÀNH", "TIN LAØNH");
		e.put("CAO ĐÀI", "CAO ÑAØI");
		e.put("CÔNG GIÁO", "COÂNG GIAÙO");
		e.put("BÀ LA MÔN", "BAØ LA MOÂN");
		e.put("BÀ NI", "BAØ NI");
		e.put("THIỀN LÂM", "THIEÀN LAÂM");
		e.put("HIẾU NGHĨA", "HIEÁU NGHÓA");
		return e;
	}
	
	private MappingPropertyEditor ethnicEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		String sql = "SELECT e.name_ethnic FROM n_ethnic e";
		List<Map<String, String>> list = dao.getJdbcTemplate().queryForList(sql);
		System.out.println(list.get(0).get("NAME_ETHNIC"));
		for(int i= 0; i < list.size(); i++){
			String ethnic = list.get(i).get("NAME_ETHNIC");
			e.put(Vni2Uni.convertToUnicode(ethnic),ethnic);
		}
//		e.put("KINH","KINH");
//		e.put("KHƠ ME","KHÔ ME");
//		e.put("CHÂM","CHAÂM");
//		e.put("DAO","DAO");
//		e.put("HOA","HOA");
//		e.put("K'HO","K\"HO");
//		e.put("LƯƠNG", "LÖÔNG");
//		e.put("MƯỜNG", "MÖÔØNG");
//		e.put("NƯƠNG", "NUØNG");
//		e.put("SÁN CHAY", "SAÙN CHAY");
//		e.put("SÁN DÌU", "SAÙN DÌU");
//		e.put("TÀY", "TAØY");
//		e.put("THÁI", "THAÙI");
//		e.put("THỔ", "THOÅ");
//		e.put("NGƯỜI NƯỚC NGOÀI", "NGÖÔØI NÖÔÙC NGOAØI");
		return e;
	}
	
	public void loadCurrentInfo(N_EMPLOYEE data){
		String empsn = data.getEMPSN();
		//luong co ban, phu cap, chuc vu,bo phan
		long basicSalary=0, bonusPoss=0, bonusJob=0, pcnghe=0;
		String poss=null, job=null, sal85="", ddkhu="";
		String bssql = 
			"select t.bsalary from n_basic_salary t where t.empsn=? and t.dates=" +
			"(select max(a.dates) from n_basic_salary a where a.empsn=t.empsn) and t.keys=1";
		try{
			basicSalary = dao.getSimpleJdbcTemplate().queryForLong(bssql, new Object[]{empsn});
		}catch(Exception e){};
		String bpsql = "select * from n_bonus_poss t where t.empsn=? and t.keys=1";
		try{
			List<Map<String, Object>> list = dao.getSimpleJdbcTemplate().queryForList(bpsql, new Object[]{empsn});
			if (list.size()>0){
				Map<String, Object> map = list.get(0);
				poss = (String) map.get("POSS");
				bonusPoss = ((BigDecimal) map.get("MONEY")).longValue();				
			}
		}catch(Exception e){}
		String bjsql = "select son_get_job_bonus_for_emp('" + empsn + "',sysdate) from dual";
		try{
			bonusJob = dao.getSimpleJdbcTemplate().queryForLong(bjsql, new Object[]{});
		}catch(Exception e){}
		String jobsql = "select t.name_job||'('||t.kind_job||')' from n_job t where t.id_key=son_get_job_id_for_emp('" + empsn + "',sysdate)";
		try{
			job = dao.getSimpleJdbcTemplate().queryForObject(jobsql, String.class, new Object[]{});
		}catch(Exception e){}
		String othersql = "select * from n_newworker_test t where t.empsn=?";
		try{
			List<Map<String, Object>> list = dao.getSimpleJdbcTemplate().queryForList(othersql, new Object[]{empsn});
			if (list.size()>0){
				Map<String, Object> map = list.get(0);
				sal85 = (String) map.get("SAL_STATUS");
				pcnghe = ((BigDecimal) map.get("BONUS_NGHE")).longValue();
				ddkhu= (String) map.get("DD_KHU");
			}
		}catch(Exception e){}
//		String deptsql = "select get_dept('" + empsn + "','" + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + "') from dual";
//		try{
//			depsn = dao.getSimpleJdbcTemplate().queryForObject(deptsql, String.class, new Object[]{});
//		}catch(Exception e){}
		String trainingsql = "select t.rank from n_training_details t where t.empsn=? and t.course_id=? and t.subject_id=?";
		try{
			String rank = dao.getSimpleJdbcTemplate().queryForObject(trainingsql, String.class, new Object[]{empsn, "NQCTY", "NQCTY1"});
			if (rank.equals("ÑAÏT")){
				chkDat.setSelected(true);
				chkKhongDat.setSelected(false);
			}else if (rank.equals("KHOÂNG ÑAÏT")){
				chkDat.setSelected(false);
				chkKhongDat.setSelected(true);
			}else{
				chkDat.setSelected(false);
				chkKhongDat.setSelected(false);
			}
		}catch(Exception e){
			chkDat.setSelected(false);
			chkKhongDat.setSelected(false);
		}
		txtLuongCoBan.setText(String.valueOf(basicSalary));
//		ListBinder.refreshIndex(sfChucVu, String.valueOf(poss));
		cboJob.setText(Vni2Uni.convertToUnicode(job));
		txtPCCV.setText(String.valueOf(bonusPoss));
		txtPCCViec.setText(String.valueOf(bonusJob));
		if ("85".equals(sal85)){
			chk85.setSelected(true);
		}else{
			chk85.setSelected(false);
		}
		txtPhuCapNghe.setText(String.valueOf(pcnghe));
		if ("1".equals(ddkhu)){
			chkDD.setSelected(true);
		}else{
			chkDD.setSelected(false);
		}
	}
	
	private void putToMap(){
		saveObjects = new HashMap<String, Object>();
		saveObjects.put("empsn", EMPSN_DscField1.getText());
		saveObjects.put("bsalary", new BigDecimal(txtLuongCoBan.getText().equals("")?"0":txtLuongCoBan.getText()));
		Date nnx_moi = null;
		try {
			if (!NGAYNX_MOI_DscDateField5.getText().equals(""))
				nnx_moi = new SimpleDateFormat("dd/MM/yyyy").parse(NGAYNX_MOI_DscDateField5.getText());
		} catch (ParseException e) {
			nnx_moi=null;
			e.printStackTrace();
		}
		java.sql.Date date = nnx_moi==null?new java.sql.Date(DATE_HIRED_DscDateField2.getSelectedDate().getTimeInMillis()):new java.sql.Date(nnx_moi.getTime());
		saveObjects.put("date", date);
		if (sfChucVu.getSelectedIndex()>=0){			
			saveObjects.put("pccv", new BigDecimal(txtPCCV.getText().equals("")?"0":txtPCCV.getText()));
			saveObjects.put("cv", ListBinder.get(sfChucVu, (MappingPropertyEditor)getUIDataObjectBinder().getPropertyEditor("POSITION")).toString());
		}
		if (chkDat.isSelected()||chkKhongDat.isSelected()){
			if (chkDat.isSelected())
				saveObjects.put("training", "1");
			else
				saveObjects.put("training", "0");
		}
		saveObjects.put("lcb85", chk85.isSelected()?"85":"100");
		saveObjects.put("pcnghe", new BigDecimal(txtPhuCapNghe.getText().equals("")?"0":txtPhuCapNghe.getText()));
		if (chkDD.isSelected()){
			saveObjects.put("ddkhu", "1");
		}else{
			saveObjects.put("ddkhu", "0");
		}
		if (!cboJob.getText().equals("")){
			saveObjects.put("job", ListBinder.getCboValue(cboJob, jobEditor));
		}
		if (!EMPCN_DscField2.getText().equals(""))
			saveObjects.put("empcn", EMPCN_DscField2.getText());
		depsnEditor.setAsText(cboDepsn.getText());
		String depsn="";
		if (depsnEditor.getValue()!=null){
			depsn=depsnEditor.getValue().toString();
			MappingPropertyEditor dp = FVGenericInfo.getDepartmentEditor();
			dp.setAsText(depsn);
			N_DEPARTMENT dept = (N_DEPARTMENT) dp.getValue();
			if (dept.getNOTE()!=null&&dept.getNOTE().contains("MAY")){
				saveObjects.put("may", "z");
			}
		}
	}
	
	//SaveOtherInfo
	public void saveOtherInfo(){		
		logger.debug("Luu thong tin nhap ho so: " + saveObjects.get("empsn"));
		//don vi, luong co ban, pccv, pccviec
		String bsal = "insert into n_basic_salary (empsn,dates,bsalary,keys,note) values (?,?,?,?,?)";
		String poss = "insert into n_bonus_poss (empsn,dates,money,keys,poss,note) values (?,?,?,?,?,?)";
		String other = "insert into n_newworker_test (empsn, is_test, sal_status, bonus_nghe, dd_khu) values (?,?,?,?,?)";
		String job = "insert into n_emp_job_detail (empsn,id_job,date_b,user_update,date_update) values (?,?,?,?,?)";
		String nqcty = "insert into n_training_details (id_training,empsn,course_id,subject_id,bdate,edate,training_type,rank) values (?,?,?,?,?,?,?,?)";
		String iccard = "insert into n_emp_iccard (empsn,empcn,use_status,begin_date) values (?,?,?,?)";
		String iccardup = "update n_emp_iccard set use_status='0' where empsn=?";
		String iccardup1 = "update n_ic_card set use_status='1' where ic_no=?";
		String pn = "insert into n_rest values (?,?,0,?,0,?,0,null,null,?,?,?,?,?,0)";

		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));		
		java.sql.Date date = (java.sql.Date) saveObjects.get("date");		
		String userId = Application.getApp().getLoginInfo().getUserID();
		String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		String empsn = (String) saveObjects.get("empsn");
		
		try{
			int p = DateUtils.getMonthsToEndYear(new Date(date.getTime()));//7
			Calendar cal1 = Calendar.getInstance();
			cal1.setTimeInMillis(date.getTime());
			int s1=0;
			int s2=0;
			s2 = p/3;
			s1 = p-s2;
			int sw1 = 0, sw2 = 0;
			int hln=0;
			if (saveObjects.get("may")!=null){
/*				if (p>=12) {
					s2+=2;
					sw1=1;
					sw2=1;
					p+=2;
				}
				else if (p>=6) {
					s2+=1;
					p+=1;
					if (cal1.get(Calendar.MONTH)>Calendar.JUNE){
						sw1=0;
						sw2=1;
					}else{
						sw1=1;
						sw2=0;
					}
				}*/
				//HA 29/06/2013
				if (cal1.get(Calendar.MONTH)<=Calendar.MARCH) {
					s2+=2;
					sw1=1;
					sw2=1;
					p+=2;
				}
				else if (cal1.get(Calendar.MONTH)>Calendar.MARCH && cal1.get(Calendar.MONTH)<=Calendar.SEPTEMBER) {
					s2+=1;
					sw1=1;
					sw2=0;
					p+=1;					
				}
				else {					
					sw1=0;
					sw2=0;				
				}
				
			}
			//HA THEM VAO 27/07/2013
			int thangNghiTet=2;// mac dinh la thang 2
			try{
				thangNghiTet = dao.getSimpleJdbcTemplate().queryForInt("select thoigian from n_rest_annual_type where year=?", 
					new Object[]{String.valueOf(cal1.get(Calendar.YEAR))});
				thangNghiTet--;// giam 1, vi thang trong Calendar bat dau tu 0
			}catch(Exception e){};			
			//HET HA
			//if (cal1.get(Calendar.MONTH)<=Calendar.FEBRUARY){
			if (cal1.get(Calendar.MONTH)<=thangNghiTet){
				try{
					hln = dao.getSimpleJdbcTemplate().queryForInt("select hol_lunar from n_rest_annual_type where year=?", 
						new Object[]{String.valueOf(cal1.get(Calendar.YEAR))});
				}catch(Exception e){};
			}
			dao.getJdbcTemplate().update(pn, new Object[]{empsn, year,p,p,s1,s2,hln,sw1,sw2});
		}catch(Exception e){
			logger.error("Khong the luu phep nam & " + e.getMessage());
		}
		try{
		dao.getJdbcTemplate().update(bsal, new Object[]{empsn, date, 
				(BigDecimal)saveObjects.get("bsalary"), "1", userId + " - nhap ho so " + currentDate});
		logger.debug("Save LCB");
		}catch(Exception e){
			logger.error("Không thể thêm LCB & " + e.getMessage());
		}
		if (saveObjects.get("cv")!=null){
			try{
			dao.getJdbcTemplate().update(poss, new Object[]{empsn, date, (BigDecimal)saveObjects.get("pccv"), 
					"1", (String)saveObjects.get("cv"), userId + " - nhap ho so " + currentDate});
			logger.debug("Save CV");
			}catch(Exception e){
				logger.error("Không thể thêm chuc vu & " + e.getMessage());
			}
		}
		try{
		dao.getJdbcTemplate().update(other, new Object[]{empsn,"0",saveObjects.get("lcb85"), saveObjects.get("pcnghe"), saveObjects.get("ddkhu")});
		logger.debug("Save 85");
		}catch(Exception e){}
		if (saveObjects.get("job")!=null){
			try{
			dao.getJdbcTemplate().update(job, new Object[]{empsn,saveObjects.get("job"), 
					date, userId, new java.sql.Date(new Date().getTime())});
			logger.debug("Save Job");
			}catch(Exception e){
				logger.error("Không thể thêm cong viec & " + e.getMessage());
			}
		}
		if (saveObjects.get("training")!=null){
			try{
				boolean dat = saveObjects.get("training").equals("1");
				dao.getJdbcTemplate().update(nqcty, new Object[]{genTrainingPK(empsn), empsn, "NQCTY", "NQCTY1", date, date, "1 LAÀN", dat?"ÑAÏT":"KHOÂNG ÑAÏT"});
			logger.debug("Save Training");
			}catch(Exception e){
				logger.error("Không thể thêm training & " + e.getMessage());
			}
		}
		if (saveObjects.get("empcn")!=null){
			try{
				dao.getJdbcTemplate().update(iccardup, new Object[]{empsn});
				dao.getJdbcTemplate().update(iccardup1, new Object[]{(String)saveObjects.get("empcn")});
				dao.getJdbcTemplate().update(iccard, new Object[]{empsn, (String)saveObjects.get("empcn"), "1", date});
				logger.debug("Save IC");
			}catch(Exception e){
				logger.error("Không thể thêm iccard & " + e.getMessage());
			}
		}
	}
	
	private String genTrainingPK(String empsn){
		IGenericDAO<N_TRAINING_DETAILS, String> dao = Application.getApp().getDao(N_TRAINING_DETAILS.class);
		String sql = "select max(substr(ID_TRAINING,9,length(t.ID_TRAINING))) from N_TRAINING_DETAILS t where t.EMPSN=?";
		List list = dao.find(1, sql, new Object[]{empsn});
		if (list.size()>0&&list.get(0)!=null){
			int n = Integer.parseInt(list.get(0).toString()) + 1;
			if (n<10) return empsn + "000"+n;
			else if (n<100) return empsn + "00"+n;
			else if (n<1000) return empsn + "0"+n;
			else return empsn+n;
		}
		return empsn+"0001";
	}


	/*
	 * 畫面Layout初始化Method
	 * 
	 * @see dsc.echo2app.program.DataContent#doLayout()
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
		//<<從此以下加入使用者程式>>
	}

	/*
	 * 做資料新增時內定預設數值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doNewDefaulData()
	 */
	@Override
	protected void doNewDefaulData() {
		N_EMPLOYEE data = (N_EMPLOYEE) getDataObject();
		//<如果要預先取號則在此加入>
		//autoPrimaryKeyValue();
		//使用者可在以下區域填入新增時內定的預設數值
		//<內定值>
		data.setDATE_HIRED(new Date());
		data.setSEX("NU");
		data.setSHIFT("HC1");
		data.setMATERIAL_STATUS("N");
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR)%100);
		String month = cal.get(Calendar.MONTH)<9?"0"+(cal.get(Calendar.MONTH)+1):""+(cal.get(Calendar.MONTH)+1);
		data.setEMPSN(year + month);
		data.setWORK_STATUS("LAM VIEC BINH THUONG");
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {
		N_EMPLOYEE data = (N_EMPLOYEE) getDataObject();
		data.setDEPSN_TEMP(data.getDEPSN());
		data.setDEPSN_TEMP1(data.getDEPSN());
		putToMap();
		String sql = "select max(to_number(e.CODE)) from N_EMPLOYEE e";
		JdbcDAO jdao = new JdbcDAO();
		try{
			Long n = jdao.getSimpleJdbcTemplate().queryForLong(sql);
			String code = String.valueOf(n+1);
			while (code.length()<6) code="0"+code;
			data.setCODE(code);
		}catch (Exception e){
			e.printStackTrace();
			data.setCODE("100000");
		}
	}


	private void chkDatSelected(ActionEvent e) {
		if (chkKhongDat.isSelected()){
			chkKhongDat.setSelected(false);
		}
	}


	private void chkKoDatSelected(ActionEvent e) {
		if (chkDat.isSelected()){
			chkDat.setSelected(false);
		}
	}
	
	public boolean initPolicy(){
		if (pp==null)
			pp = new ArrayList<NewEmployeePolicy>();
		if (lt1Policy==null){
			lt1Policy = new LT1NewEmployeePolicy();
			lt1Policy.setDepsnEditor(depsnEditor);
		}
		Connection con=null;
		if (lt2Policy==null){
			lt2Policy = new LT2NewEmployeePolicy();
			lt2Policy.setDepsnEditor(depsnEditor);
		}
		try {
			con = lt2Policy.getDao().getDataSource().getConnection();
			pp.add(lt2Policy);
			if (con==null) {
				setErrorMessage("Không thể kết nối đến CSDL Linh Trung 2");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			setErrorMessage("Không thể kết nối đến CSDL Linh Trung 2");
			return false;
		} finally {
			DbUtils.close(con);
		}
		if (tgPolicy==null){
			tgPolicy = new TGNewEmployeePolicy();
			tgPolicy.setDepsnEditor(depsnEditor);
			pp.add(tgPolicy);
		}
		try {
			con = tgPolicy.getDao().getDataSource().getConnection();
			if (con==null) {
				setErrorMessage("Không thể kết nối đến CSDL Tiền Giang");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			setErrorMessage("Không thể kết nối đến CSDL Tiền Giang");
			return false;
		} finally {
			DbUtils.close(con);
		}
		if (bpPolicy==null){
			bpPolicy = new BPNewEmployeePolicy();
			bpPolicy.setDepsnEditor(depsnEditor);
			pp.add(bpPolicy);
		}
		try {
			con = bpPolicy.getDao().getDataSource().getConnection();				
			if (con==null) {
				setErrorMessage("Không thể kết nối đến CSDL Bình Phước");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			setErrorMessage("Không thể kết nối đến CSDL Bình Phước");
			return false;
		} finally {
			DbUtils.close(con);
		}
		
		if (mpPolicy==null){
			mpPolicy = new MPNewEmployeePolicy();
			mpPolicy.setDepsnEditor(depsnEditor);
			pp.add(mpPolicy);
		}
		try {
			con = mpPolicy.getDao().getDataSource().getConnection();
			if (con==null) {
				setErrorMessage("Không thể kết nối đến CSDL Mỹ Phước");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			setErrorMessage("Không thể kết nối đến CSDL Mỹ Phước");
			return false;
		} finally {
			DbUtils.close(con);
		}
		return true;
	}

	public BPNewEmployeePolicy getBpPolicy() {
		return bpPolicy;
	}

	public void setBpPolicy(BPNewEmployeePolicy bpPolicy) {
		this.bpPolicy = bpPolicy;
	}

	public LT1NewEmployeePolicy getLt1Policy() {
		return lt1Policy;
	}

	public void setLt1Policy(LT1NewEmployeePolicy lt1Policy) {
		this.lt1Policy = lt1Policy;
	}

	public LT2NewEmployeePolicy getLt2Policy() {
		return lt2Policy;
	}

	public void setLt2Policy(LT2NewEmployeePolicy lt2Policy) {
		this.lt2Policy = lt2Policy;
	}

	public List<NewEmployeePolicy> getPp() {
		return pp;
	}

	public void setPp(List<NewEmployeePolicy> pp) {
		this.pp = pp;
	}

	public TGNewEmployeePolicy getTgPolicy() {
		return tgPolicy;
	}

	public void setTgPolicy(TGNewEmployeePolicy tgPolicy) {
		this.tgPolicy = tgPolicy;
	}
	
	public void deleteOtherInfo(String empsn, String empcn) {		
		logger.debug("Xoa thong tin nhap ho so: " + empsn);
		String sql[] = new String[9];
		sql[0] = "delete from n_basic_salary where empsn=?";
		sql[1] = "delete from n_bonus_poss where empsn=?";
		sql[2] = "delete from n_newworker_test where empsn=?";
		sql[3] = "delete from n_emp_job_detail where empsn=?";
		sql[4] = "delete from n_training_details where empsn=?";
		sql[5] = "delete from n_emp_iccard where empsn=?";
		sql[6] = "update n_ic_card set use_status='0' where ic_no=?";
		sql[7] = "delete from n_rest where empsn=?";		
		sql[8] = "delete from n_emp_info where empsn=?";		
		try{			
			dao.getJdbcTemplate().update(sql[0], new Object[]{(String)empsn});
			dao.getJdbcTemplate().update(sql[1], new Object[]{(String)empsn});
			dao.getJdbcTemplate().update(sql[2], new Object[]{(String)empsn});
			dao.getJdbcTemplate().update(sql[3], new Object[]{(String)empsn});
			dao.getJdbcTemplate().update(sql[4], new Object[]{(String)empsn});			
			if (empcn!=null)
			{
				dao.getJdbcTemplate().update(sql[5], new Object[]{(String)empsn});
				dao.getJdbcTemplate().update(sql[6], new Object[]{(String)empcn});
			}
			dao.getJdbcTemplate().update(sql[7], new Object[]{(String)empsn});
			dao.getJdbcTemplate().update(sql[8], new Object[]{(String)empsn});
			
			logger.debug("Delete Other Infor cua "+empsn);
		}catch(Exception e){
			logger.error("Không thể xoa & " + e.getMessage());
		}		
	}

	
	
	
	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		rootLayout = new Grid();
		rootLayout.setSize(4);
		add(rootLayout);
		Grid grid1 = new Grid();
		GridLayoutData grid1LayoutData = new GridLayoutData();
		grid1LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		grid1.setLayoutData(grid1LayoutData);
		rootLayout.add(grid1);
		EMPSN_CaptionLabel = new Label();
		EMPSN_CaptionLabel.setText("N_EMPLOYEE.EMPSN");
		grid1.add(EMPSN_CaptionLabel);
		EMPSN_DscField1 = new DscField();
		EMPSN_DscField1.setId("EMPSN_DscField1");
		EMPSN_DscField1.setMaximumLength(8);
		grid1.add(EMPSN_DscField1);
		FNAME_CaptionLabel = new Label();
		FNAME_CaptionLabel.setText("N_EMPLOYEE.FNAME");
		grid1.add(FNAME_CaptionLabel);
		FNAME_DscField3 = new DscField();
		FNAME_DscField3.setId("FNAME_DscField3");
		grid1.add(FNAME_DscField3);
		LNAME_CaptionLabel = new Label();
		LNAME_CaptionLabel.setText("N_EMPLOYEE.LNAME");
		grid1.add(LNAME_CaptionLabel);
		LNAME_DscField4 = new DscField();
		LNAME_DscField4.setId("LNAME_DscField4");
		grid1.add(LNAME_DscField4);
		BIRTHDAY_CaptionLabel = new Label();
		BIRTHDAY_CaptionLabel.setText("N_EMPLOYEE.BIRTHDAY");
		grid1.add(BIRTHDAY_CaptionLabel);
		BIRTHDAY_DscField9 = new DscField();
		BIRTHDAY_DscField9.setId("BIRTHDAY_DscField9");
		grid1.add(BIRTHDAY_DscField9);
		BIRTHPLACE_CaptionLabel = new Label();
		BIRTHPLACE_CaptionLabel.setText("N_EMPLOYEE.BIRTHPLACE");
		grid1.add(BIRTHPLACE_CaptionLabel);
		BIRTHPLACE_DscField10 = new DscField();
		BIRTHPLACE_DscField10.setId("BIRTHPLACE_DscField10");
		grid1.add(BIRTHPLACE_DscField10);
		SEX_CaptionLabel = new Label();
		SEX_CaptionLabel.setText("N_EMPLOYEE.SEX");
		grid1.add(SEX_CaptionLabel);
		SEX_SelectField = new SelectField();
		SEX_SelectField.setWidth(new Extent(80, Extent.PX));
		grid1.add(SEX_SelectField);
		ID_NO_CaptionLabel = new Label();
		ID_NO_CaptionLabel.setText("N_EMPLOYEE.ID_NO");
		grid1.add(ID_NO_CaptionLabel);
		ID_NO_DscField7 = new DscField();
		ID_NO_DscField7.setId("ID_NO_DscField7");
		ID_NO_DscField7.setMaximumLength(9);
		grid1.add(ID_NO_DscField7);
		NGAYCAP_ID_CaptionLabel = new Label();
		NGAYCAP_ID_CaptionLabel.setText("N_EMPLOYEE.NGAYCAP_ID");
		grid1.add(NGAYCAP_ID_CaptionLabel);
		NGAYCAP_ID_DscDateField1 = new DscDateField();
		NGAYCAP_ID_DscDateField1.setId("NGAYCAP_ID_DscDateField1");
		grid1.add(NGAYCAP_ID_DscDateField1);
		ID_PLACE_CaptionLabel = new Label();
		ID_PLACE_CaptionLabel.setText("N_EMPLOYEE.ID_PLACE");
		grid1.add(ID_PLACE_CaptionLabel);
		ID_PLACE_DscField8 = new DscField();
		ID_PLACE_DscField8.setId("ID_PLACE_DscField8");
		grid1.add(ID_PLACE_DscField8);
		CITY_CaptionLabel = new Label();
		CITY_CaptionLabel.setText("N_EMPLOYEE.CITY");
		grid1.add(CITY_CaptionLabel);
		CITY_SelectField1 = new SelectField();
		CITY_SelectField1.setId("CITY_SelectField1");
		CITY_SelectField1.setWidth(new Extent(160, Extent.PX));
		grid1.add(CITY_SelectField1);
		CONTACT_ADDRESS_CaptionLabel = new Label();
		CONTACT_ADDRESS_CaptionLabel.setText("N_EMPLOYEE.CONTACT_ADDRESS");
		grid1.add(CONTACT_ADDRESS_CaptionLabel);
		CONTACT_ADDRESS_DscField12 = new DscField();
		CONTACT_ADDRESS_DscField12.setId("CONTACT_ADDRESS_DscField12");
		CONTACT_ADDRESS_DscField12.setWidth(new Extent(400, Extent.PX));
		GridLayoutData CONTACT_ADDRESS_DscField12LayoutData = new GridLayoutData();
		CONTACT_ADDRESS_DscField12LayoutData.setColumnSpan(3);
		CONTACT_ADDRESS_DscField12
				.setLayoutData(CONTACT_ADDRESS_DscField12LayoutData);
		grid1.add(CONTACT_ADDRESS_DscField12);
		PERMANENT_ADDRESS_CaptionLabel = new Label();
		PERMANENT_ADDRESS_CaptionLabel.setText("N_EMPLOYEE.PERMANENT_ADDRESS");
		grid1.add(PERMANENT_ADDRESS_CaptionLabel);
		PERMANENT_ADDRESS_DscField13 = new DscField();
		PERMANENT_ADDRESS_DscField13.setId("PERMANENT_ADDRESS_DscField13");
		PERMANENT_ADDRESS_DscField13.setWidth(new Extent(400, Extent.PX));
		GridLayoutData PERMANENT_ADDRESS_DscField13LayoutData = new GridLayoutData();
		PERMANENT_ADDRESS_DscField13LayoutData.setColumnSpan(3);
		PERMANENT_ADDRESS_DscField13
				.setLayoutData(PERMANENT_ADDRESS_DscField13LayoutData);
		grid1.add(PERMANENT_ADDRESS_DscField13);
		CONTACT_PER_CaptionLabel = new Label();
		CONTACT_PER_CaptionLabel.setText("N_EMPLOYEE.CONTACT_PER");
		grid1.add(CONTACT_PER_CaptionLabel);
		CONTACT_PER_DscField15 = new DscField();
		CONTACT_PER_DscField15.setId("CONTACT_PER_DscField15");
		grid1.add(CONTACT_PER_DscField15);
		CONTACT_TEL_CaptionLabel = new Label();
		CONTACT_TEL_CaptionLabel.setText("N_EMPLOYEE.CONTACT_TEL");
		grid1.add(CONTACT_TEL_CaptionLabel);
		CONTACT_TEL_DscField14 = new DscField();
		CONTACT_TEL_DscField14.setId("CONTACT_TEL_DscField14");
		grid1.add(CONTACT_TEL_DscField14);
		ETHNIC_CaptionLabel = new Label();
		ETHNIC_CaptionLabel.setText("N_EMPLOYEE.ETHNIC");
		grid1.add(ETHNIC_CaptionLabel);
		sfEthnic = new SelectField();
		sfEthnic.setWidth(new Extent(150, Extent.PX));
		grid1.add(sfEthnic);
		RELIGION_CaptionLabel = new Label();
		RELIGION_CaptionLabel.setText("N_EMPLOYEE.RELIGION");
		grid1.add(RELIGION_CaptionLabel);
		sfReligion = new SelectField();
		sfReligion.setWidth(new Extent(150, Extent.PX));
		grid1.add(sfReligion);
		MATERIAL_STATUS_CaptionLabel = new Label();
		MATERIAL_STATUS_CaptionLabel.setText("N_EMPLOYEE.MATERIAL_STATUS");
		grid1.add(MATERIAL_STATUS_CaptionLabel);
		sfMaterialStatus = new SelectField();
		sfMaterialStatus.setWidth(new Extent(150, Extent.PX));
		grid1.add(sfMaterialStatus);
		EDUCATION_CaptionLabel = new Label();
		EDUCATION_CaptionLabel.setText("N_EMPLOYEE.EDUCATION");
		grid1.add(EDUCATION_CaptionLabel);
		sfEducation = new SelectField();
		sfEducation.setWidth(new Extent(150, Extent.PX));
		grid1.add(sfEducation);
		TRUCTIEP_SX_CaptionLabel = new Label();
		TRUCTIEP_SX_CaptionLabel.setText("CN");
		grid1.add(TRUCTIEP_SX_CaptionLabel);
		ttgtGroup = new DscGroupRadioButton();
		grid1.add(ttgtGroup);
		Grid grid2 = new Grid();
		GridLayoutData grid2LayoutData = new GridLayoutData();
		grid2LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		grid2.setLayoutData(grid2LayoutData);
		rootLayout.add(grid2);
		EMPCN_CaptionLabel = new Label();
		EMPCN_CaptionLabel.setText("N_EMPLOYEE.EMPCN");
		grid2.add(EMPCN_CaptionLabel);
		EMPCN_DscField2 = new DscField();
		EMPCN_DscField2.setId("EMPCN_DscField2");
		EMPCN_DscField2.setMaximumLength(10);
		grid2.add(EMPCN_DscField2);
		DATE_HIRED_CaptionLabel = new Label();
		DATE_HIRED_CaptionLabel.setText("N_EMPLOYEE.DATE_HIRED");
		grid2.add(DATE_HIRED_CaptionLabel);
		DATE_HIRED_DscDateField2 = new DscDateField();
		DATE_HIRED_DscDateField2.setId("DATE_HIRED_DscDateField2");
		grid2.add(DATE_HIRED_DscDateField2);
		NGAYNX_MOI_CaptionLabel = new Label();
		NGAYNX_MOI_CaptionLabel.setText("N_EMPLOYEE.NGAYNX_MOI");
		grid2.add(NGAYNX_MOI_CaptionLabel);
		Row row3 = new Row();
		grid2.add(row3);
		NGAYNX_MOI_DscDateField5 = new DscDateField();
		NGAYNX_MOI_DscDateField5.setId("NGAYNX_MOI_DscDateField5");
		row3.add(NGAYNX_MOI_DscDateField5);
		chkDD = new CheckBox();
		chkDD.setText("Điều động khu");
		row3.add(chkDD);
		DEPSN_CaptionLabel = new Label();
		DEPSN_CaptionLabel.setText("N_EMPLOYEE.DEPSN");
		grid2.add(DEPSN_CaptionLabel);
		cboDepsn = new ComboBox();
		cboDepsn.setWidth(new Extent(300, Extent.PX));
		grid2.add(cboDepsn);
		Label label1 = new Label();
		label1.setText("Lương cơ bản");
		grid2.add(label1);
		Row row1 = new Row();
		grid2.add(row1);
		txtLuongCoBan = new DscField();
		txtLuongCoBan.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtLuongCoBan.setDisabledBackground(new Color(0xc8c8c8));
		txtLuongCoBan.setMaximumLength(10);
		row1.add(txtLuongCoBan);
		chk85 = new CheckBox();
		chk85.setText("Hưởng 85% HD");
		row1.add(chk85);
		Label label5 = new Label();
		label5.setText("Phụ cấp nghề");
		grid2.add(label5);
		txtPhuCapNghe = new DscField();
		txtPhuCapNghe.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtPhuCapNghe.setDisabledBackground(new Color(0xc8c8c8));
		grid2.add(txtPhuCapNghe);
		POSITION_CaptionLabel = new Label();
		POSITION_CaptionLabel.setText("Chức vụ");
		grid2.add(POSITION_CaptionLabel);
		sfChucVu = new SelectField();
		sfChucVu.setWidth(new Extent(250, Extent.PX));
		sfChucVu.setDisabledBackground(new Color(0xc8c8c8));
		grid2.add(sfChucVu);
		Label label2 = new Label();
		label2.setText("Phụ cấp chức vụ");
		grid2.add(label2);
		txtPCCV = new DscField();
		txtPCCV.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtPCCV.setDisabledBackground(new Color(0xc8c8c8));
		grid2.add(txtPCCV);
		ID_JOB_CaptionLabel = new Label();
		ID_JOB_CaptionLabel.setText("Công việc");
		grid2.add(ID_JOB_CaptionLabel);
		cboJob = new ComboBox();
		cboJob.setAutoRecall(false);
		cboJob.setWidth(new Extent(250, Extent.PX));
		grid2.add(cboJob);
		Label label3 = new Label();
		label3.setText("Phụ cấp công việc");
		grid2.add(label3);
		txtPCCViec = new DscField();
		txtPCCViec.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtPCCViec.setDisabledBackground(new Color(0xc8c8c8));
		grid2.add(txtPCCViec);
		SHIFT_CaptionLabel = new Label();
		SHIFT_CaptionLabel.setText("N_EMPLOYEE.SHIFT");
		grid2.add(SHIFT_CaptionLabel);
		sfShift = new SelectField();
		sfShift.setWidth(new Extent(250, Extent.PX));
		grid2.add(sfShift);
		USER_MANAGE_ID_CaptionLabel = new Label();
		USER_MANAGE_ID_CaptionLabel.setText("N_EMPLOYEE.USER_MANAGE_ID");
		grid2.add(USER_MANAGE_ID_CaptionLabel);
		sfManagerId = new SelectField();
		sfManagerId.setWidth(new Extent(60, Extent.PX));
		grid2.add(sfManagerId);
		WORK_STATUS_CaptionLabel = new Label();
		WORK_STATUS_CaptionLabel.setText("N_EMPLOYEE.WORK_STATUS");
		grid2.add(WORK_STATUS_CaptionLabel);
		cboWorkStatus = new ComboBox();
		cboWorkStatus.setWidth(new Extent(180, Extent.PX));
		grid2.add(cboWorkStatus);
		Label label4 = new Label();
		label4.setText("Hướng dẫn nội quy cty");
		grid2.add(label4);
		Row row2 = new Row();
		grid2.add(row2);
		chkDat = new CheckBox();
		chkDat.setText("Đạt");
		chkDat.setDisabledBackground(new Color(0xc0c0c0));
		chkDat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkDatSelected(e);
			}
		});
		row2.add(chkDat);
		chkKhongDat = new CheckBox();
		chkKhongDat.setText("Không đạt");
		chkKhongDat.setDisabledBackground(new Color(0xc0c0c0));
		chkKhongDat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkKoDatSelected(e);
			}
		});
		row2.add(chkKhongDat);
	}



}
