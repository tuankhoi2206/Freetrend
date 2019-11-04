package ds.program.fvhr.baby.tools;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import dsc.echo2app.Application;
import dsc.echo2app.info.CompanyInfo;

import fv.util.JdbcDAO;

public class ProcessProcedure extends JdbcDAO  {
	public static final String N_HB_ACTION_DAILY = "N_HB_ACTION_DAILY";
	
	public static StoredProcedure storedProcedure;
	public ProcessProcedure()
	{CompanyInfo cominfo = Application.getApp().findCompanyById(
			Application.getApp().getLoginInfo().getCompanyID());
	DataSource ds = (DataSource) Application.getSpringContext().getBean(
			cominfo.getConnectionName());
	setDataSource(ds);
	}
	public static void inputActionDaily(){
		
		ProcessProcedure ProcessProcedure = new ProcessProcedure();
		storedProcedure = new StoredProcedure(ProcessProcedure.getDataSource(),N_HB_ACTION_DAILY) {	
		};
		storedProcedure.declareParameter(new SqlParameter("IDUser", Types.VARCHAR));
		storedProcedure.declareParameter(new SqlParameter("TableName", Types.VARCHAR));
		storedProcedure.declareParameter(new SqlParameter("ActionName", Types.VARCHAR));
		storedProcedure.declareParameter(new SqlParameter("EMPSN", Types.VARCHAR));
		storedProcedure.declareParameter(new SqlParameter("Note", Types.VARCHAR));
		storedProcedure.compile();
	}
}
