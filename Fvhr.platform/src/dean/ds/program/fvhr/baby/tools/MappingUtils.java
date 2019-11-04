package ds.program.fvhr.baby.tools;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.support.DaoSupport;

import net.sf.jasperreports.olap.mapping.Mapping;

import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import ds.program.fvhr.domain.*;

public class MappingUtils {
	private static MappingPropertyEditor map;
	public MappingUtils(){super();}
	
	public static MappingPropertyEditor Bind_Factory() 
	{	
		map = new MappingPropertyEditor();
		IGenericDAO<N_FACTORY, String> daoFactory = Application.getApp().getDao(N_FACTORY.class);
		List<N_FACTORY> listFactory = daoFactory.findAll(20);
		for (N_FACTORY obj : listFactory) {
			map.put(obj.getNAME_FACT(), obj);
		}
		return map;
	}
	public static MappingPropertyEditor bind_GROUP()
	{
		map = new MappingPropertyEditor();
		IGenericDAO<N_DEPARTMENT, String> daoDept = Application.getApp().getDao(N_DEPARTMENT.class);
		List<N_DEPARTMENT> listDept = daoDept.findAll(1500);
		for (N_DEPARTMENT obj : listDept) {
			map.put(obj.getNAME_FACT(),obj);
		}
		return map;
	}
	public static MappingPropertyEditor bind_DEPT()
	{
		map = new MappingPropertyEditor();
		IGenericDAO<N_DEPARTMENT, String> daoDept = Application.getApp().getDao(N_DEPARTMENT.class);
		List<N_DEPARTMENT> listDept = daoDept.findAll(1500);
		for (N_DEPARTMENT obj : listDept) {
			map.put(obj.getNAME_DEPT_NAME(),obj);
		}
		return map;
	}
}
