package ds.program.fvhr.baby.tools;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import ds.program.fvhr.domain.N_EMPLOYEE;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;

public class EmployeeTool {
	private static IGenericDAO<N_EMPLOYEE, String> daoEmpsn;
	@SuppressWarnings("unchecked")
	public static boolean checkEmpsnExist(String empsn)
	{
		daoEmpsn = Application.getApp().getDao(N_EMPLOYEE.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_EMPLOYEE.class)
								.add(Restrictions.eq("EMPSN", empsn));
		List<N_EMPLOYEE> listEmpsn = daoEmpsn.findByCriteria(1, dc);
		if(listEmpsn.isEmpty())
			return false;
		return true;
	}
	
	public static N_EMPLOYEE getInformationEmployee(String empsn)
	{
		daoEmpsn = Application.getApp().getDao(N_EMPLOYEE.class);
		return 	daoEmpsn.findById(empsn);
	}
	
	@SuppressWarnings("unchecked")
	public static List<N_EMPLOYEE> getEmpsnByFact(String nameFact){
		String sql = "select t from N_EMPLOYEE t, N_DEPARTMENT dt " +
					"where t.DEPSN = dt.ID_DEPT and dt.NAME_FACT = ?";
		
		daoEmpsn = Application.getApp().getDao(N_EMPLOYEE.class);									
		List<N_EMPLOYEE> listempsn = new ArrayList<N_EMPLOYEE> ();
		listempsn  = daoEmpsn.find(10000, sql, nameFact);	
		return listempsn;
	}
}
