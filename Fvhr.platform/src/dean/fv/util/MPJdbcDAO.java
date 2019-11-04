package fv.util;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class MPJdbcDAO extends SimpleJdbcDaoSupport{
	private BasicDataSource ds;
	public MPJdbcDAO(){
		super();
		if (ds==null){
			ds = new BasicDataSource();
			ds.setUrl("jdbc:oracle:thin:@10.17.1.8:1521:fvidb02");
			ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
			ds.setUsername("vft");
			ds.setPassword("db9931");
			ds.setMaxWait(10000);//10s
			ds.setMaxActive(5);	
			ds.setMinIdle(1);
		}
		setDataSource(ds);
	}
}
