package ds.program.fvhr.dao.salary;

import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class SalaryDataCollectorProcedure extends StoredProcedure {
	private static final String SPROC_NAME = "SALARY_DATA_COLLECTOR";

	public SalaryDataCollectorProcedure(DataSource ds) {
		super(ds, SPROC_NAME);
		declareParameter(new SqlParameter("SOTHE", Types.VARCHAR));
		declareParameter(new SqlParameter("THANG", Types.DATE));
		declareParameter(new SqlOutParameter("BSALY", Types.NUMERIC));
		declareParameter(new SqlOutParameter("COMSALY", Types.NUMERIC));
		declareParameter(new SqlOutParameter("BONUS1", Types.NUMERIC));
		declareParameter(new SqlOutParameter("BONUS2", Types.NUMERIC));
		declareParameter(new SqlOutParameter("BONUS3", Types.NUMERIC));
		declareParameter(new SqlOutParameter("BONUS4", Types.NUMERIC));
		declareParameter(new SqlOutParameter("BONUS8", Types.NUMERIC));
		declareParameter(new SqlOutParameter("BONUS5", Types.NUMERIC));
		declareParameter(new SqlOutParameter("BORM", Types.NUMERIC));
		declareParameter(new SqlOutParameter("KQT", Types.NUMERIC));
		declareParameter(new SqlOutParameter("JOINLUM", Types.NUMERIC));
		declareParameter(new SqlOutParameter("YLBX", Types.NUMERIC));
		declareParameter(new SqlOutParameter("JOININSU", Types.NUMERIC));
		declareParameter(new SqlOutParameter("BHTN", Types.NUMERIC));
		declareParameter(new SqlOutParameter("BONUS9", Types.NUMERIC));
		declareParameter(new SqlOutParameter("TEMP1", Types.NUMERIC));
		declareParameter(new SqlOutParameter("TEMP2", Types.NUMERIC));
		declareParameter(new SqlOutParameter("BAC", Types.NUMERIC));
		compile();
	}

	// @SuppressWarnings("rawtypes")
	// public Map excecute(Map<String, Object> params){
	// return super.execute(params);
	// }
	@Override
	public Map execute(Map inParams) throws DataAccessException {
		return super.execute(inParams);
	}
}
