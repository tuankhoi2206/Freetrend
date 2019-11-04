package fv.util;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTest {
	public static void main(String[] args) {
		TGJdbcDAO dao = new TGJdbcDAO();
//		try {
//			Connection con = dao.getDataSource().getConnection();
//			System.out.println(con);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		Integer n = dao.getSimpleJdbcTemplate().queryForInt("select count(*) from n_employee e where e.depsn<>'00000'");
		System.out.println(n);
	}
}
