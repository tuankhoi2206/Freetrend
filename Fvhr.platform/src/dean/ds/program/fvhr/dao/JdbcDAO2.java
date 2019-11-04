package ds.program.fvhr.dao;

import javax.sql.DataSource;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;


public class JdbcDAO2 extends SimpleJdbcDaoSupport {
	public JdbcDAO2() {
		super();
//		CompanyInfo cominfo = Application.getApp().findCompanyById(
//				Application.getApp().getLoginInfo().getCompanyID());
//		DataSource ds = (DataSource) Application.getSpringContext().getBean(
//				cominfo.getConnectionName());
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/conf/*.xml");
		DataSource ds = (DataSource) ctx.getBean("dataSource_VFT");
		setDataSource(ds);
	}
}
