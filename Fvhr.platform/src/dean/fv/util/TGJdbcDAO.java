package fv.util;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class TGJdbcDAO extends SimpleJdbcDaoSupport{
	private BasicDataSource ds;
	public TGJdbcDAO(){
		super();
		if (ds==null){
			ds = new BasicDataSource();
			ds.setUrl("jdbc:oracle:thin:@10.15.1.8:1521:fvndb01");
			ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
			ds.setUsername("vft");
			ds.setPassword("new.fvn");
			ds.setMaxWait(10000);//10s
			ds.setMaxActive(5);	
			ds.setMinIdle(1);
		}
		setDataSource(ds);
	}
}