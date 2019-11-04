package ds.program.fvhr.minh.dao;

import fv.util.JdbcDAO;
import ds.program.fvhr.domain.N_EALRY_BEFOR_B;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_SHIFT;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class BearJdbcDAO extends JdbcDAO{

	public List<N_SHIFT> getN_SHIFT()
	{
		List<N_SHIFT> list = getSimpleJdbcTemplate().query("select * from N_SHIFT  where NOTE like ?", new ParameterizedRowMapper<N_SHIFT>(){
			public N_SHIFT mapRow(ResultSet rs, int rowNum) throws SQLException {
				N_SHIFT data = new N_SHIFT();
				data.setNAME_SHIFT(rs.getString("name_shift"));	
				data.setID_SHIFT(rs.getString("id_shift"));	
				data.setNOTE(rs.getString("note"));
				data.setTIME_IN(rs.getString("TIME_IN"));
				data.setTIME_OUT(rs.getString("TIME_OUT"));
				return data;
			}}, new Object[]{"%7H"});
		return list;
	}
	
	public N_EMPLOYEE getEmployee(String empsn)
	{
		if(empsn.equals("")||empsn==null)
			return null;
		N_EMPLOYEE emp = getSimpleJdbcTemplate().queryForObject("select * from n_employee t where empsn = ?", new ParameterizedRowMapper<N_EMPLOYEE>(){
			@Override
			public N_EMPLOYEE mapRow(ResultSet rs, int arg1)throws SQLException {
				N_EMPLOYEE emp = new N_EMPLOYEE();
				emp.setEMPSN(rs.getString("empsn"));
				emp.setFNAME(rs.getString("fname"));
				emp.setLNAME(rs.getString("lname"));
				emp.setSHIFT(rs.getString("shift"));
				emp.setSEX(rs.getString("sex"));
				emp.setDEPSN(rs.getString("DEPSN"));
				return emp;
			}
		}, new Object[]{empsn});
		return emp;
	}
	
	public String getId_sdept(String id_dept)
	{
		return getSimpleJdbcTemplate().queryForObject("Select d.id_spdept from n_department d where d.id_dept = ?", String.class, id_dept);
	}
	
}
