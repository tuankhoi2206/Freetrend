package ds.program.fvhr.dao.quitsalary;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.CallableStatementCreator;

public class AttQuitCalculationStmtCreator implements CallableStatementCreator {

	private String empsn;

	private Date dotTv;

	private String month;

	private String year;

	public AttQuitCalculationStmtCreator(String empsn, Date dot_TV,
			String month, String year) {
		this.empsn = empsn;
		this.dotTv = dot_TV;
		this.month = month;
		this.year = year;
	}

	public CallableStatement createCallableStatement(Connection con)
			throws SQLException {
		CallableStatement cstm = con
				.prepareCall("C5_CALCULATE_SALARY_OFF_N_FVS");
		cstm.setString(1, month);
		cstm.setString(2, year);
		cstm.setString(3, empsn);
		cstm.setDate(4, new java.sql.Date(dotTv.getTime()));
		return cstm;
	}

}
