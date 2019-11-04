package ds.program.fvhr.dao.salary;

import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class UpdateAttendantdbStoredProcedure extends StoredProcedure {
	private static final String SPROC_NAME = "C4_UPDATE_ATTENDANTDB_JAVA";

	public UpdateAttendantdbStoredProcedure(DataSource ds) {
		super(ds, SPROC_NAME);
		declareParameter(new SqlParameter("THANG", Types.VARCHAR));
		declareParameter(new SqlParameter("NAM", Types.VARCHAR));
		declareParameter(new SqlParameter("ST", Types.VARCHAR));
		compile();
	}

	public void excecute(Map<String, Object> params) {
		super.execute(params);
	}
}
