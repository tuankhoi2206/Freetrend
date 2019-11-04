package ds.program.fvhr.dao.quitsalary;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class AttQuitGReportRowMapper implements
		ParameterizedRowMapper<QuitWorkGenericObject> {

	public QuitWorkGenericObject mapRow(ResultSet rs, int rowNum) throws SQLException {
		QuitWorkGenericObject data = new QuitWorkGenericObject();
		ResultSetMetaData meta = rs.getMetaData();
		for (int i = 1; i <= meta.getColumnCount(); i++) {
			String col = meta.getColumnLabel(i);
			try {
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
