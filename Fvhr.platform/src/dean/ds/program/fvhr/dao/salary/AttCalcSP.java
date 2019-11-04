package ds.program.fvhr.dao.salary;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class AttCalcSP extends StoredProcedure{
	private static final String SPROC_NAME_1MUC = "TINHLUONG1MUC";
	private static final String SPROC_NAME_2MUC = "TINHLUONG2MUC";
	
	public AttCalcSP(DataSource ds, boolean is1){
		super();
		setDataSource(ds);
		if (is1) setSql(SPROC_NAME_1MUC);
		else setSql(SPROC_NAME_2MUC);
		declareParameter(new SqlParameter("THANG", Types.VARCHAR));
		declareParameter(new SqlParameter("NAM", Types.VARCHAR));
		declareParameter(new SqlParameter("SOTHE", Types.VARCHAR));
		compile();
	}
}
