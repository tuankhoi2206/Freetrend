package ds.program.fvhr.dao.quitsalary;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCreator;

import ds.program.fvhr.ui.quitworksalary.AttQuitInitData;

public class AttQuitInsertStatementCreator implements PreparedStatementCreator {

	private AttQuitInitData initAtt;

	private String tableName;

	public AttQuitInsertStatementCreator(AttQuitInitData initAtt, String month,
			String year) {
		this.initAtt = initAtt;
		this.tableName = "ATTQUIT" + year + month;
	}

	public PreparedStatement createPreparedStatement(Connection con)
			throws SQLException {
		if (initAtt.getStt()==null) initAtt.setStt(BigDecimal.valueOf(1));
		String sql = "INSERT INTO "
				+ tableName
				+ "(EMPSN,EMPNA,DEPSN,POSSN,HIRED,DATE_OFF,DOT_TV,DEPT_KT,STT) VALUES (?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setString(1, initAtt.getEmpsn());
		pstm.setString(2, initAtt.getEmpna());
		pstm.setString(3, initAtt.getDepsn());
		pstm.setString(4, initAtt.getPossn());
		pstm.setDate(5, new java.sql.Date(initAtt.getHired().getTime()));
		pstm.setDate(6, new java.sql.Date(initAtt.getDateOff().getTime()));
		pstm.setDate(7, new java.sql.Date(initAtt.getDotTv().getTime()));
		pstm.setString(8, initAtt.getDeptKt());
		pstm.setBigDecimal(9, initAtt.getStt());
		// pstm.setString(9, initAtt.getNoteBh());
		return pstm;
	}

}
