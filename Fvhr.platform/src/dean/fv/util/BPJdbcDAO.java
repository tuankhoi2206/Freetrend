package fv.util;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class BPJdbcDAO extends SimpleJdbcDaoSupport{
	private BasicDataSource ds;
	public BPJdbcDAO(){
		super();
		if (ds==null){
			ds = new BasicDataSource();
			ds.setUrl("jdbc:oracle:thin:@10.16.1.7:1521:fvfdb01");
			ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
			ds.setUsername("vft");
			ds.setPassword("fva1324");
			ds.setMaxWait(10000);//10s
			ds.setMaxActive(5);	
			ds.setMinIdle(1);
		}
		setDataSource(ds);
	}
}
