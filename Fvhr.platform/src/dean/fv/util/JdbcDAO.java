package fv.util;

import javax.sql.DataSource;

//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import dsc.echo2app.Application;
import dsc.echo2app.info.CompanyInfo;

public class JdbcDAO extends SimpleJdbcDaoSupport {
	public JdbcDAO() {
		super();
		CompanyInfo cominfo = Application.getApp().findCompanyById(
				Application.getApp().getLoginInfo().getCompanyID());
		DataSource ds = (DataSource) Application.getSpringContext().getBean(
				cominfo.getConnectionName());
		setDataSource(ds);
	}
}
