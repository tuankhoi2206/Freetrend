package ds.program.fvhr.dao.wp;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class EmployeeWorkingTimeSP extends StoredProcedure {
	private static final String PROC_NAME="EMPLOYEE_SHIFT_TIME";
	
	public EmployeeWorkingTimeSP(DataSource ds){
		super(ds,PROC_NAME);
		declareParameter(new SqlParameter("EMPSN", Types.VARCHAR));
		declareParameter(new SqlParameter("SDATE", Types.DATE));
		declareParameter(new SqlOutParameter("TIME_IN", Types.VARCHAR));
		declareParameter(new SqlOutParameter("TIME_OUT", Types.VARCHAR));
		compile();
	}
}
