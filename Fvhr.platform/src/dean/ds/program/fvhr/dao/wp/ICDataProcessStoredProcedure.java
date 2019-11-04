package ds.program.fvhr.dao.wp;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class ICDataProcessStoredProcedure extends StoredProcedure {
	private static final String SPROC_NAME="C1_CAL_EMPIC_DATA_A";
	
	public ICDataProcessStoredProcedure(DataSource ds){
		super(ds, SPROC_NAME);
		declareParameter(new SqlParameter("ST", Types.VARCHAR));
		declareParameter(new SqlParameter("THOIGIAN", Types.VARCHAR));
		declareParameter(new SqlParameter("TT", Types.VARCHAR));
		compile();
	}
}
