package ds.program.fvhr.dao.salary;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class UpdatePrimarySalaryDataProcedure extends StoredProcedure {
	private static final String SPROC_NAME = "UPDATE_PRIMARY_SALARY_DATA";

	public UpdatePrimarySalaryDataProcedure(DataSource ds) {
		super(ds, SPROC_NAME);
		declareParameter(new SqlParameter("SOTHE", Types.VARCHAR));
		declareParameter(new SqlParameter("THANG", Types.DATE));
		compile();
	}
}
