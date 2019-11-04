package ds.program.fvhr.dao.quitsalary;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCreator;

import ds.program.fvhr.ui.quitworksalary.AttQuitInitData;

public class AttQuitUpdateStatementCreator implements PreparedStatementCreator {

	private AttQuitInitData initAtt;

	private String tableName;

	public AttQuitUpdateStatementCreator(AttQuitInitData initAtt, String month,
			String year) {
		this.initAtt = initAtt;
		this.tableName = "ATTQUIT" + year + month;		
	}

	public PreparedStatement createPreparedStatement(Connection con)
			throws SQLException {
		if (initAtt.getStt()==null) initAtt.setStt(BigDecimal.valueOf(1));
		String sql = "UPDATE "
				+ tableName
				+ " SET EMPNA=?,DEPSN=?,POSSN=?,HIRED=?,DATE_OFF=?,DEPT_KT=?,STT=? WHERE EMPSN=? AND DOT_TV=?";
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setString(1, initAtt.getEmpna());
		pstm.setString(2, initAtt.getDepsn());
		pstm.setString(3, initAtt.getPossn());
		pstm.setDate(4, new java.sql.Date(initAtt.getHired().getTime()));
		pstm.setDate(5, new java.sql.Date(initAtt.getDateOff().getTime()));
		pstm.setString(6, initAtt.getDeptKt());
		pstm.setBigDecimal(7, initAtt.getStt());
		pstm.setString(8, initAtt.getEmpsn());
		pstm.setDate(9, new java.sql.Date(initAtt.getDotTv().getTime()));
		return pstm;
	}

}
