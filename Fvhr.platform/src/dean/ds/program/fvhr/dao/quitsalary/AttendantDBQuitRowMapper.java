package ds.program.fvhr.dao.quitsalary;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ds.program.fvhr.domain.ATTENDANTDB_QUIT;
import fv.util.Vni2Uni;

public class AttendantDBQuitRowMapper implements RowMapper {

	public ATTENDANTDB_QUIT mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		ATTENDANTDB_QUIT att = new ATTENDANTDB_QUIT();
		att.setEMPSN(rs.getString("EMPSN"));
		att.setEMPNA(Vni2Uni.convertToUnicode(rs.getString("FNAME").trim() + " "
				+ rs.getString("LNAME").trim()));
		att.setDEPSN(rs.getString("DEPSN"));
		att.setPOSSN(rs.getString("POSITION"));
		att.setHIRED(rs.getDate("DATE_HIRED"));
		att.setDUCLS(rs.getBigDecimal("DUCLS"));
		att.setNUCLS(rs.getBigDecimal("NDUCLS"));
		att.setADDCLS1(rs.getBigDecimal("ADDCLS1"));
		att.setNADDCLS(rs.getBigDecimal("NADDCLS"));
		att.setADDHOL(rs.getBigDecimal("ADDHOL"));
		att.setREST(rs.getBigDecimal("REST"));
		att.setREST_PAY(rs.getBigDecimal("REST_PAY"));
		att.setNWHOUR(rs.getBigDecimal("NWHOUR"));
		att.setLATE(rs.getBigDecimal("LATE"));
		att.setADDHOLN(rs.getBigDecimal("ADDHOLN"));
		att.setSIGN_TIME(rs.getBigDecimal("SIGN"));
		att.setACNM(rs.getBigDecimal("ACNM"));
		att.setREST_SICK(rs.getBigDecimal("REST_SICK"));
		att.setOTHER(rs.getBigDecimal("OTHER"));
		att.setPHEP_NS(rs.getBigDecimal("LMATER"));
		att.setDATE_OFF(rs.getDate("REAL_OFF_DATE"));
		return att;
	}

}
