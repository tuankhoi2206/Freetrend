package fv.util;

import javax.sql.DataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcConsoleDAO extends SimpleJdbcDaoSupport {
	public JdbcConsoleDAO() {
		super();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/conf/*.xml");
		DataSource ds = (DataSource) ctx.getBean("dataSource_VFT");
		setDataSource(ds);
	}
}
