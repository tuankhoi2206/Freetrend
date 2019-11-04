package ds.program.fvhr.ui;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import ds.program.fvhr.domain.N_EMPLOYEE;
//findEmployeeByID(id_no, date_hired, isMaster, isEdit);
import ds.program.fvhr.domain.N_EMP_QUIT;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import fv.util.DateUtils;
/**
 * Kiem tra CMND
 * @author Hieu
 *
 */
public abstract class ANewEmployeePolicy implements NewEmployeePolicy {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ANewEmployeePolicy.class);

	private String errorMessage;
	private MappingPropertyEditor depsnEditor;
	
	@Override
	public final boolean isValid(N_EMPLOYEE e, boolean editMode){
		logger.debug("Do validating ID at " + getLocation() + " factory.");
		return checkId(e, editMode);
	}
	
	protected boolean checkId(N_EMPLOYEE e, boolean editMode) {
		N_EMPLOYEE emp = findEmployeeByID(e.getEMPSN(), e.getID_NO(), e.getDATE_HIRED(), editMode);
		if (emp!=null){
			if (!emp.getDEPSN().equals("00000")){
				setErrorMessage("Số CMND đã được sử dụng cho số thẻ " + emp.getEMPSN() + "("+getLocation()+")");
				return false;
			}else{
				emp.setDEPSN(e.getDEPSN());////Hieu 05/04/2013 fix depsn
				//if la sua thi ko can kiem tra trong nghi viec vi da kiem tra khi luu roi
				if (editMode==false){
					return checkNV(emp);
				}
			}
		}
		return true;
	}

	protected abstract String getLocation();

	protected boolean checkNV(N_EMPLOYEE e) {
		String sql = "select * from (select * from n_emp_quit t where t.empsn=? and t.real_off_date=(select max(a.real_off_date) from n_emp_quit a where t.empsn=a.empsn) order by t.real_off_date desc) where rownum<2";
		try {
			N_EMP_QUIT data = getDao().getSimpleJdbcTemplate().queryForObject(sql, new ParameterizedRowMapper<N_EMP_QUIT>(){
				@Override
				public N_EMP_QUIT mapRow(ResultSet rs, int rowNum) throws SQLException {
					N_EMP_QUIT d = new N_EMP_QUIT();
					d.setEMPSN(rs.getString("EMPSN"));
					d.setID_QUIT_REASON(rs.getBigDecimal("ID_QUIT_REASON"));
					d.setFACT_NAME(rs.getString("FACT_NAME"));
					d.setGROUP_NAME(rs.getString("GROUP_NAME"));
					d.setDEPT_NAME(rs.getString("DEPT_NAME"));
					d.setDEPSN(rs.getString("DEPSN"));
					d.setREAL_OFF_DATE(rs.getDate("REAL_OFF_DATE"));
					d.setDEPT(rs.getString("DEPT"));
					return d;
				}}, new Object[]{e.getEMPSN()});
			if (data!=null){//co du lieu nghi viec
				//kiem tra thoi gian nghi viec
				//thong tin nghi viec
				int dates[] = DateUtils.before(e.getDATE_HIRED(),data.getREAL_OFF_DATE());
				
				String ttNViec =" Ngày NViec "+new SimpleDateFormat("dd/MM/yyyy").format(data.getREAL_OFF_DATE())+"\n"+
						", ly do NViec "+data.getID_QUIT_REASON()+"\n"+
						data.getDEPT()+", "+data.getEMPSN()+". Cách ngày NX mới \n"+
						dates[0] + " ngày " + dates[1] + " tháng " + dates[2] + " năm";				
								
				if (dates[3]>0){//ngay nhap xuong>ngay thuc nghi
					if (dates[2]*12+dates[1]>=3){
						String reason = nghiDungLuat(data.getID_QUIT_REASON());
						if (reason==null){
							setErrorMessage(ttNViec+". Nghỉ đúng luật (" + reason + ").\n" );
							return true;
						}else{
							//check hoso
							String checklocksql = "select t.locked from n_emp_quit t where t.empsn=? and t.real_off_date=?";
							try{
								String check = getDao().getSimpleJdbcTemplate().queryForObject(checklocksql, String.class, new Object[]{data.getEMPSN(), new java.sql.Date(data.getREAL_OFF_DATE().getTime())});
								if (check!=null&&check.equals("Y")){
									return true;
								}else{
									setErrorMessage(ttNViec+". Nghỉ không đúng luật (" + reason + ") và chưa được chủ quản xác nhận.\n" );
									return false;
								}
							}catch(Exception ee){
								setErrorMessage(ttNViec+". Nghỉ không đúng luật (" + reason + ") và chưa được chủ quản xác nhận.");
								return false;
							}
						}
					}else{
						String reason = nghiDungLuat(data.getID_QUIT_REASON());
						if (reason==null&&data.getDEPSN().equals(e.getDEPSN())){
							return true;
						}
						//HA them vao 17/04/2013
						else if (reason==null && !data.getDEPSN().equals(e.getDEPSN())){
							//check hoso
							String checklocksql = "select t.locked from n_emp_quit t where t.empsn=? and t.real_off_date=?";
							try{
								String check = getDao().getSimpleJdbcTemplate().queryForObject(checklocksql, String.class, new Object[]{data.getEMPSN(), new java.sql.Date(data.getREAL_OFF_DATE().getTime())});
								if (check!=null&&check.equals("Y")){
									setErrorMessage(ttNViec+". Nghỉ không đúng luật (" + reason + ") nhưng đã được chủ quản xác nhận.");
									return true;
								}else{
									setErrorMessage(ttNViec+". Nghỉ không đúng luật (" + reason + ") và chưa được chủ quản xác nhận.");
									return false;
								}
							}catch(Exception ee){
								setErrorMessage(ttNViec+". Nghỉ không đúng luật (" + reason + ") và chưa được chủ quản xác nhận.");
								return false;
							}				
						}
						//Het HA
						else{
							depsnEditor.setValue(data.getDEPSN());
							String oldDept = depsnEditor.getAsText();
							depsnEditor.setValue(e.getDEPSN());
							String newDept = depsnEditor.getAsText();
							String l = reason==null?ttNViec+". Nghỉ việc đúng luật.\r\n":ttNViec+". Nghi việc không đúng luật (" + reason + ").\r\n";
							setErrorMessage(l +ttNViec+"\n"+".\r\nĐơn vị không hợp lệ. Đơn vị cũ: " + oldDept + ", đơn vị mới: " + newDept);
							return false;
						}
					}
				}else{
					 /*KTRA LAI DOI VOI TRUONG HOP CNV CO THONG TIN NGHI VIEC NHUNG DI LAM LAI LAU ROI KHI EDIT VAN BAO LOI
					 VI TIM THAY TT NGHI VIEC NEN SO SANH NGAY NX VOI NGAY THUC NGHI--> KO CHO EDIT
					 VD 08120240 */					
					setErrorMessage("Ngày nhập xưởng mới phải lớn hơn ngày thực nghỉ gần đây nhất. Ngày nghỉ việc " +new SimpleDateFormat("dd/MM/yyyy").format(data.getREAL_OFF_DATE()));
					return false;
				}
			}//else du lieu khong hop le do nghi viec nhung ko co du lieu trong n_emp_quit
		}catch(Exception ee){
			ee.printStackTrace();
		}
		return false;
	}
	
	protected N_EMPLOYEE findEmployeeByID(String empsn, String id, final Date dateHired, boolean editMode){
		String sql="select e.empsn, e.id_no, e.depsn from n_employee e where e.id_no=? and e.date_hired=(select max(t.date_hired) from n_employee t where t.id_no=e.id_no)";
		Object[] params = new Object[]{id};
		if (editMode){
			sql = sql + " and e.empsn <> ?";
			params = new Object[]{id, empsn};
		}
		try{
			List<N_EMPLOYEE> emps = getDao().getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<N_EMPLOYEE>(){
				@Override
				public N_EMPLOYEE mapRow(ResultSet rs, int rowNum) throws SQLException {
					N_EMPLOYEE data = new N_EMPLOYEE();
					data.setEMPSN(rs.getString("EMPSN"));
					data.setID_NO(rs.getString("ID_NO"));
					data.setDEPSN(rs.getString("DEPSN"));
					data.setDATE_HIRED(dateHired);
					return data;
				}}, params);
			if (emps!=null&&emps.size()>0) return emps.get(0);
		}catch(Exception ee){
			return null;
		}
		return null;
	}
	/**
	 * 
	 * @param reasonId
	 * @return TEN LY DO neu khong dung luat, null neu dung luat
	 */
	public abstract String nghiDungLuat(BigDecimal reasonId);

	/*public MappingPropertyEditor depsnEditor(){
		MappingPropertyEditor de = FVGenericInfo.getDepartmentEditor();
		MappingPropertyEditor e = new MappingPropertyEditor();
		String[] disp = de.getDisplays();
		for (String str:disp){
			de.setAsText(str);
			N_DEPARTMENT d = (N_DEPARTMENT) de.getValue();
			e.put(d.getNAME_DEPT(), str);
		}
		return e;
	}*/
	
	public abstract SimpleJdbcDaoSupport getDao();


	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public MappingPropertyEditor getDepsnEditor() {
		return depsnEditor;
	}

	public void setDepsnEditor(MappingPropertyEditor depsnEditor) {
		this.depsnEditor = depsnEditor;
	}

}
