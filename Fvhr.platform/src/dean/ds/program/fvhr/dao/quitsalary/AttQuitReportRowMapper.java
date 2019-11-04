package ds.program.fvhr.dao.quitsalary;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import ds.program.fvhr.domain.ATTQUIT;

/**
 * ATTQUITyyyymm resultset row extractor
 * 
 * @author Hieu
 * 
 */
public class AttQuitReportRowMapper implements ParameterizedRowMapper<ATTQUIT> {

	public ATTQUIT mapRow(ResultSet rs, int rowNum) throws SQLException {
		ATTQUIT data = new ATTQUIT();
		// data.setEMPSN(rs.getString("EMPSN"));
		// data.setEMPNA(rs.getString("EMPNA"));//do this continue if you have
		// much time and some crazy.
		// //* Lazy extract data => column resultset must match attquit field
		ResultSetMetaData meta = rs.getMetaData();
		for (int i = 1; i <= meta.getColumnCount(); i++) {
			String col = meta.getColumnLabel(i);
			try {
				Object obj = rs.getObject(i);
				if (obj instanceof java.sql.Date || obj instanceof java.sql.Timestamp){
					Date date = new Date(rs.getDate(i).getTime());
					PropertyUtils.setProperty(data, col, date);
				}else
					PropertyUtils.setProperty(data, col, rs.getObject(i));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
}
