package ds.program.fvhr.dao.insuranse;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class ExecuteInputActionDaily extends StoredProcedure {
	private static final String SPROC_NAME = "N_HB_ACTION_DAILY";
	public ExecuteInputActionDaily(DataSource ds){
		super(ds, SPROC_NAME);
		declareParameter(new SqlParameter("IDUser", Types.VARCHAR));
		declareParameter(new SqlParameter("TableName", Types.VARCHAR));
		declareParameter(new SqlParameter("ActionName", Types.VARCHAR));
		declareParameter(new SqlParameter("EMPSN", Types.VARCHAR));
		declareParameter(new SqlParameter("Note", Types.VARCHAR));
		compile();
	}
	
	public void excecute(Map<String, Object> params){		
		super.execute(params);
	}
}
