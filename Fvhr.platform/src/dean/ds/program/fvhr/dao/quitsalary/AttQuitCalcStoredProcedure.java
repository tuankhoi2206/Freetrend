package ds.program.fvhr.dao.quitsalary;

import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class AttQuitCalcStoredProcedure extends StoredProcedure {
//	private static final String SPROC_NAME = "TINHLUONGTHOIVIEC";

	public AttQuitCalcStoredProcedure(DataSource ds, String name) {
		super(ds, name);
		declareParameter(new SqlParameter("THANG", Types.VARCHAR));
		declareParameter(new SqlParameter("NAM", Types.VARCHAR));
		declareParameter(new SqlParameter("SOTHE", Types.VARCHAR));
		declareParameter(new SqlParameter("DOT_TV", Types.DATE));
		compile();
	}

	public void excecute(Map<String, Object> params) {
		super.execute(params);
	}
}
