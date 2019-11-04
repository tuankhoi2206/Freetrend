package ds.program.fvhr.baby.tools;

import java.util.ArrayList;
import java.util.List;

import ds.program.fvhr.domain.N_DEPARTMENT;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import fv.util.Vni2Uni;

public class DepartmentTool {
	public static List GetAllFactory() {
		String sql = "select distinct t.NAME_FACT from N_DEPARTMENT t ";
		IGenericDAO<N_DEPARTMENT, String> daoDepartment = Application.getApp()
				.getDao(N_DEPARTMENT.class);
		List<N_DEPARTMENT> listDepartment = daoDepartment.find(100, sql, null);
		List NameFact = new ArrayList();
		for (int i = 0; i < listDepartment.size(); i++) {
			NameFact.add(listDepartment.get(i));
		}
		return NameFact;
	}

	public static List GetAllGroup() {
		String sql = "select distinct t.NAME_GROUP from N_DEPARTMENT t";
		IGenericDAO<N_DEPARTMENT, String> daoDepartment = Application.getApp()
				.getDao(N_DEPARTMENT.class);
		List<N_DEPARTMENT> listDepartment = daoDepartment.find(2000, sql, null);
		List NameGroup = new ArrayList();
		for (int i = 0; i < listDepartment.size(); i++) {
			NameGroup.add(listDepartment.get(i));
		}
		return NameGroup;
	}

	public static List GetAllDept() {
		String sql = "select distinct t.NAME_DEPT_NAME from N_DEPARTMENT t";
		IGenericDAO<N_DEPARTMENT, String> daoDepartment = Application.getApp()
				.getDao(N_DEPARTMENT.class);
		List<N_DEPARTMENT> listDepartment = daoDepartment.find(2000, sql, null);
		List NameDeptName = new ArrayList();
		for (int i = 0; i < listDepartment.size(); i++) {
			NameDeptName.add(listDepartment.get(i));
		}
		return NameDeptName;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List GetAllDepsn() {
		String sql = "select distinct ID_DEPT from N_DEPARTMENT t";
		IGenericDAO<N_DEPARTMENT, String> daoDepartment = Application.getApp()
				.getDao(N_DEPARTMENT.class);
		List<N_DEPARTMENT> listDepartment = daoDepartment.find(2000, sql, null);
		List Depsn = new ArrayList();
		for (N_DEPARTMENT obj : listDepartment) {
			Depsn.add(obj.getNAME_DEPT_NAME());
		}
		return Depsn;
	}

	public static List<N_DEPARTMENT> getAllDepartment() {
		String sql = "select t from N_DEPARTMENT t";
		IGenericDAO<N_DEPARTMENT, String> daoDepartment = Application.getApp()
				.getDao(N_DEPARTMENT.class);
		List<N_DEPARTMENT> listDepartment = daoDepartment.find(2000, sql, null);
		return listDepartment;
	}

	public static List GetGroupByNameFact(String nameFact) {
		String sql = "select distinct t.NAME_GROUP from N_DEPARTMENT t where t.NAME_FACT = ?";
		IGenericDAO<N_DEPARTMENT, String> daoDepartment = Application.getApp()
				.getDao(N_DEPARTMENT.class);
		List<N_DEPARTMENT> listDepartment = daoDepartment.find(2000, sql,
				nameFact);
		List NameGroup = new ArrayList();
		for (int i = 0; i < listDepartment.size(); i++) {
			NameGroup.add(Vni2Uni.convertToUnicode((String) (listDepartment
					.get(i) != null ? listDepartment.get(i) : "OTHER")));
		}
		return NameGroup;
	}

	public static List GetDeptByNameGroupAndNameFact(String nameFact,
			String nameGroup) {
		StringBuilder temp = new StringBuilder();
		temp.append("select distinct t.NAME_DEPT_NAME from N_DEPARTMENT t where t.NAME_FACT = ? and t.NAME_GROUP ");
		String sql = (nameGroup.equals("OTHER") ? temp.append("is null") : temp
				.append("= '" + nameGroup + "'")).toString();
		IGenericDAO<N_DEPARTMENT, String> daoDepartment = Application.getApp()
				.getDao(N_DEPARTMENT.class);
		List<N_DEPARTMENT> listDepartment = daoDepartment.find(2000, sql,
				nameFact);
		List NameDeptName = new ArrayList();
		for (int i = 0; i < listDepartment.size(); i++) {
			NameDeptName.add(Vni2Uni.convertToUnicode((String) (listDepartment
					.get(i) != null ? listDepartment.get(i) : "OTHER")));
		}
		return NameDeptName;
	}

}
