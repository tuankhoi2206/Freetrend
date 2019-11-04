package ds.program.fvhr.dao.wp;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.jdbc.core.RowMapper;

import ds.program.fvhr.ui.hrdata.ICMasterData;

public class NGetDataExtendRowMapper implements RowMapper {

	public ICMasterData mapRow(ResultSet rs, int rowNumber) throws SQLException {
		ICMasterData data = new ICMasterData();
		Field[] fields = ICMasterData.class.getDeclaredFields();
		for (Field field:fields){
			try {
				PropertyUtils.setProperty(data, field.getName(), rs.getObject(field.getName()));
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
