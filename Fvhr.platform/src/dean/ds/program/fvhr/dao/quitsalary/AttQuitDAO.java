package ds.program.fvhr.dao.quitsalary;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import ds.program.fvhr.domain.ATTENDANTDB_QUIT;
import ds.program.fvhr.domain.ATTQUIT;
import ds.program.fvhr.domain.N_GET_DATA;
import ds.program.fvhr.domain.pk.ATTENDANTDB_QUITPk;
import ds.program.fvhr.domain.pk.ATTQUITPk;
import ds.program.fvhr.ui.quitworksalary.AttQuitInitData;
import fv.util.GenericJdbcDAO;

public class AttQuitDAO extends GenericJdbcDAO {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AttQuitDAO.class);

	private String month;

	private String year;

	public AttQuitDAO(String tableName, Class supportsClass) {
		super(tableName, supportsClass);
	}

	@Override
	public Class getSupportsClass() {
		return ATTENDANTDB_QUIT.class;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getMonth() {
		return this.month;
	}

	public String getYear() {
		return this.year;
	}

	public ATTENDANTDB_QUIT getInitAttendantDbQuitData(String empsn) {
		String sql = "SELECT * FROM (SELECT DISTINCT E.EMPSN, E.FNAME, E.LNAME, Q.DEPSN, E.POSITION, E.DATE_HIRED, D.DUCLS, D.NDUCLS, D.ADDCLS1, D.NADDCLS,"
			+ " D.ADDHOL,D.REST,D.REST_PAY,D.NWHOUR,D.LATE, D.ADDHOLN, D.SIGN, D.ACNM, D.REST_SICK, D.OTHER, D.LMATER,"
			+ " (SELECT MAX(Q.REAL_OFF_DATE) FROM N_EMP_QUIT Q WHERE Q.EMPSN=?) AS REAL_OFF_DATE"
			+ " FROM N_EMPLOYEE E, N_GET_DATA D, N_EMP_QUIT Q"
			+ " WHERE E.EMPSN=Q.EMPSN AND E.EMPSN=D.EMPSN AND E.EMPSN=? AND D.MONTHS=? AND D.YEARS=?) WHERE ROWNUM<2";
		ATTENDANTDB_QUIT att = (ATTENDANTDB_QUIT) getJdbcTemplate()
		.queryForObject(sql,
				new Object[] { empsn, empsn, month, year },
				new AttendantDBQuitRowMapper());
		return att;
	}

	public ATTENDANTDB_QUIT getAttendantDbQuitData(String empsn, Date dotTV) {
		setTable("ATTENDANTDB_QUIT_"+getMonth()+getYear());
		setSupportsClass(ATTENDANTDB_QUIT.class);
		ATTENDANTDB_QUITPk pk = new ATTENDANTDB_QUITPk(empsn, dotTV);
		return (ATTENDANTDB_QUIT) findById(pk);
	}

	public ATTQUIT getAttquit(String empsn, Date dotTV){
		setTable("ATTQUIT"+getYear()+getMonth());
		setSupportsClass(ATTQUIT.class);
		ATTQUITPk pk = new ATTQUITPk(empsn, dotTV);
		return (ATTQUIT) findById(pk);
	}

	public boolean checkAttExist(String empsn, Date dtv) {
		String sql = "select empsn from attquit"+year+month+" where empsn=? and dot_tv=?";
		String emp="";
		try{
			emp = getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[]{empsn, new java.sql.Date(dtv.getTime())});
		}catch(DataAccessException e){
			return false;
		}
		return !emp.equals("");
	}

	public boolean checkAttendantExist(String empsn, Date dtv) {
		String sql = "select empsn from attendantdb_quit_"+month+year+" where empsn=? and dot_tv=?";
		String emp="";
		try{
			emp = getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[]{empsn, new java.sql.Date(dtv.getTime())});
		}catch(DataAccessException e){
			return false;
		}
		return !emp.equals("");
	}

	public boolean isValid(String empsn, String month, String year){
		String sql = "select distinct a.empsn from n_employee a, n_get_data b, n_emp_quit c where a.empsn=? and b.months=? and b.years=? and a.empsn=b.empsn and a.empsn=c.empsn";
		String s = "";
		try{
			s = getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[]{empsn, month, year});
		}catch(DataAccessException e){
			return false;
		}
		return !s.equals("");
	}

	//program
	public void fillATTENDANTDB_QUIT(ATTENDANTDB_QUIT data, String empsn){
		ATTENDANTDB_QUIT db = getInitAttendantDbQuitData(empsn);
		data.setEMPSN(db.getEMPSN());
		data.setEMPNA(db.getEMPNA());
		data.setDEPSN(db.getDEPSN());
		data.setPOSSN(db.getPOSSN());
		data.setHIRED(db.getHIRED());
		data.setDUCLS(db.getDUCLS());
		data.setNUCLS(db.getNUCLS());
		data.setADDCLS1(db.getADDCLS1());
		data.setNADDCLS(db.getNADDCLS());
		data.setADDHOL(db.getADDHOL());
		data.setREST(db.getREST());
		data.setREST_PAY(db.getREST_PAY());
		data.setNWHOUR(db.getNWHOUR());
		data.setLATE(db.getLATE());
		data.setADDHOLN(db.getADDHOLN());
		data.setSIGN_TIME(db.getSIGN_TIME());
		data.setACNM(db.getACNM());
		data.setREST_SICK(db.getREST_SICK());
		data.setOTHER(db.getOTHER());
		data.setPHEP_NS(db.getPHEP_NS());
		data.setDATE_OFF(db.getDATE_OFF());		
	}

	public int saveInitAttData(AttQuitInitData att) {
		int ret = 0;
//		try {
//		ret = getJdbcTemplate().update(
//		new AttQuitInsertStatementCreator(att, month, year));
//		} catch (DataAccessException e) {
//		ret = getJdbcTemplate().update(
//		new AttQuitUpdateStatementCreator(att, month, year));
//		}

		if (checkAttExist(att.getEmpsn(), att.getDotTv())){
			ret = getJdbcTemplate().update(new AttQuitUpdateStatementCreator(att, month, year));
		} else {
			ret = getJdbcTemplate().update(new AttQuitInsertStatementCreator(att, month, year));
		}
		if (logger.isDebugEnabled()){
			logger.debug("save InitAttData >> " + ret);
		}
		return ret;
	}

	public void saveAttentdantData(ATTENDANTDB_QUIT data){
		if (checkAttendantExist(data.getEMPSN(), data.getDOT_TV())){
			update(data);
		}else{
			save(data);
		}
	}

	public void doCalculate(String empsn, Date dotTV, String month, String year, String type) {
		String name = "BV".equals(type)?"TINHLUONGBOVIEC":"TINHLUONGTHOIVIEC";
		AttQuitCalcStoredProcedure sp = new AttQuitCalcStoredProcedure(getDataSource(),name);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("THANG", month);
		params.put("NAM", year);
		params.put("SOTHE", empsn);
		params.put("DOT_TV", new java.sql.Date(dotTV.getTime()));
		sp.excecute(params);
//		System.out.println("doCalculate " + month + "/" + year);
	}

	public void updateOrder(String empsn, Date dotTV, int stt){
		String sql = "update ATTQUIT" + year + month + " set stt=? where empsn=? and dot_tv=?";
		getJdbcTemplate().update(sql, new Object[]{stt,empsn, new java.sql.Date(dotTV.getTime())});
	}

	public void congNgayCong(String sothe, String thang1, String thang2, String nam, String applyMonth, Date dotTV){
		String sql = 
			"SELECT D.EMPSN, SUM(D.DUCLS) AS DUCLS, SUM(D.NDUCLS) AS NDUCLS, SUM(D.ADDCLS1) AS ADDCLS1, SUM(D.NADDCLS) AS NADDCLS,\n" +
			"SUM(D.ADDHOL) AS ADDHOL,SUM(D.REST) AS REST,SUM(D.REST_PAY) AS REST_PAY,SUM(D.NWHOUR) AS NWHOUR,SUM(D.LATE) AS LATE,\n" + 
			"SUM(D.ADDHOLN) AS ADDHOLN, SUM(D.SIGN) AS SIGN, SUM(D.ACNM) AS ACNM, SUM(D.REST_SICK) AS REST_SICK, SUM(D.OTHER) AS OTHER,\n" + 
			"SUM(D.LMATER) AS LMATER FROM N_GET_DATA D\n" + 
			"WHERE D.EMPSN = ? AND D.MONTHS IN (?,?) AND D.YEARS=?\n" + 
			"GROUP BY D.EMPSN";
		try{
			N_GET_DATA data = getSimpleJdbcTemplate().queryForObject(sql, new ParameterizedRowMapper<N_GET_DATA>(){
				public N_GET_DATA mapRow(ResultSet rs, int rowNum) throws SQLException {
					N_GET_DATA d = new N_GET_DATA();
					ResultSetMetaData meta = rs.getMetaData();
					for (int i=0;i<meta.getColumnCount();i++){
						String col = meta.getColumnName(i+1);
						try {
							PropertyUtils.setProperty(d, col, rs.getObject(col));
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						}
					}
					return d;
				}}, new Object[]{sothe, thang1, thang2, nam});
			if (data!=null){
				setMonth(applyMonth);
				setYear(nam);
				ATTENDANTDB_QUIT att = getAttendantDbQuitData(sothe, dotTV);
				if (att!=null){
					att.setDUCLS(data.getDUCLS());
					att.setNUCLS(data.getNDUCLS());
					att.setADDCLS1(data.getADDCLS1());
					att.setNADDCLS(data.getNADDCLS());
					att.setADDHOL(data.getADDHOL());
					att.setADDHOLN(data.getADDHOLN());
					att.setREST(data.getREST());
					att.setREST_PAY(data.getREST_PAY());
					att.setREST_SICK(data.getREST_SICK());
					att.setNWHOUR(data.getNWHOUR());
					att.setLATE(data.getLATE());
					att.setPHEP_NS(data.getLMATER());
					att.setOTHER(data.getOTHER());
					att.setSIGN_TIME(data.getSIGN());
					att.setACNM(data.getACNM());
				}
				update(att);
			}
		}catch(Exception e){

		}
	}
}
