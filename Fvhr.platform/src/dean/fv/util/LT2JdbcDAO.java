package fv.util;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class LT2JdbcDAO extends SimpleJdbcDaoSupport{
	private BasicDataSource ds;
	public LT2JdbcDAO(){
		super();
		if (ds==null){
			ds = new BasicDataSource();
			ds.setUrl("jdbc:oracle:thin:@10.6.1.22:1521:fvehr01");
			ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
			ds.setUsername("vft");
			ds.setPassword("1210");
			ds.setMaxWait(10000);//10s
			ds.setMaxActive(5);	
			ds.setMinIdle(1);
		}
		setDataSource(ds);
	}
}
