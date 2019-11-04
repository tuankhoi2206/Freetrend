package ds.program.fvhr.dao.salary;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.jdbc.core.RowMapper;

import ds.program.fvhr.domain.ATT200000;
import fv.util.CustomDomainUtils;

public class AttRowMapper implements RowMapper {
	private String[] displayColumns;

	public AttRowMapper(String[] displayColumns) {
		this.displayColumns = displayColumns;
	}

	public ATT200000 mapRow(ResultSet rs, int rowNum) throws SQLException {
		ATT200000 att = new ATT200000();
		Map<String, String> attMaps;
		if (displayColumns == null || displayColumns.length == 0) {
			attMaps = CustomDomainUtils.getHeaderColumnMap(ATT200000.class);
			Iterator<?> it = attMaps.entrySet().iterator();
			while (it.hasNext()) {
				@SuppressWarnings("unchecked")
				Entry<String, String> ks = (Entry<String, String>) it.next();
				String dbColumnName = ks.getKey();// Database column name
				String attFieldName = attMaps.get(dbColumnName);
				try {
					if (rs.getObject(dbColumnName) instanceof Number) {
						PropertyUtils.setProperty(att, attFieldName, rs
								.getBigDecimal(dbColumnName));
					} else if (rs.getObject(dbColumnName) instanceof Date) {
						PropertyUtils.setProperty(att, attFieldName, rs
								.getDate(dbColumnName));
					} else {
						PropertyUtils.setProperty(att, attFieldName, rs
								.getString(dbColumnName));
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				attMaps = CustomDomainUtils.getHeaderColumnMap(ATT200000.class,
						displayColumns);
				for (String c : displayColumns) {
					try {
						PropertyUtils.setProperty(att, c, rs.getObject(c));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			} catch (NoSuchFieldException e1) {
				e1.printStackTrace();
			}
		}

		return att;
	}
}
