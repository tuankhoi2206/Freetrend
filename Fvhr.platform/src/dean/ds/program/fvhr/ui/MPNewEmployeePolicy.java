package ds.program.fvhr.ui;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import ds.program.fvhr.domain.N_QUIT_REASON;

import fv.util.MPJdbcDAO;

public class MPNewEmployeePolicy extends ANewEmployeePolicy{
	private SimpleJdbcDaoSupport dao;
	@Override
	public SimpleJdbcDaoSupport getDao() {
		if (dao==null) dao = new MPJdbcDAO();
		return dao;
	}

	@Override
	protected String getLocation() {
		return "Mỹ Phước";
	}

	@Override
	public String nghiDungLuat(BigDecimal reasonId) {
		String sql = "select * from n_quit_reason t where t.id_quit_reason=?";
		N_QUIT_REASON data = getDao().getSimpleJdbcTemplate().queryForObject(sql, new ParameterizedRowMapper<N_QUIT_REASON>(){
			@Override
			public N_QUIT_REASON mapRow(ResultSet rs, int rowNum) throws SQLException {
				N_QUIT_REASON r = new N_QUIT_REASON();
				r.setID_QUIT_REASON(rs.getBigDecimal("ID_QUIT_REASON"));
				r.setNAME_QR(rs.getString("NAME_QR"));
				r.setNOTE1(rs.getString("NOTE1"));
				return r;
			}}, new Object[]{reasonId});
		if (data.getNOTE1().equals("1")){
			return null;
		}else{
			if (reasonId.compareTo(BigDecimal.valueOf(4))==0){
				return null;
			}else{
				return data.getNAME_QR();
			}
		}
	}

}
