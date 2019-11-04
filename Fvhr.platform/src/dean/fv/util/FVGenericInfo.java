package fv.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import ds.program.fvhr.domain.ATM;
import ds.program.fvhr.domain.DEPTCODE;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.pk.ATMPk;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;

@SuppressWarnings("unchecked")
public class FVGenericInfo {
	public FVGenericInfo() {

	}
	
	private static MappingPropertyEditor deptEditor;
	
	public static List<N_DEPARTMENT> getDepartments() {
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(
				N_DEPARTMENT.class);
		List<N_DEPARTMENT> list = dao.findAll(2000);
		return list;
	}

	public static MappingPropertyEditor getFactories() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(
				N_DEPARTMENT.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_DEPARTMENT.class);
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.distinct(Projections.property("NAME_FACT")));
		dc.setProjection(pl);
		dc.addOrder(Order.asc("NAME_FACT"));
		List list = dao.findByCriteria(20, dc);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				editor.put(list.get(i));
			}
		}
		return editor;
	}

	public static MappingPropertyEditor getGroup(Object fact) {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(
				N_DEPARTMENT.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_DEPARTMENT.class);
		dc.add(Restrictions.eq("NAME_FACT", fact));
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.distinct(Projections.property("NAME_GROUP")));
		dc.setProjection(pl);
		dc.addOrder(Order.asc("NAME_GROUP"));
		List<?> list = dao.findByCriteria(100, dc);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				editor.put(Vni2Uni.convertToUnicode(list.get(i).toString()),
						list.get(i));
			}
		}
		return editor;
	}

	public static MappingPropertyEditor getAllGroup() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(
				N_DEPARTMENT.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_DEPARTMENT.class);
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.distinct(Projections.property("NAME_GROUP")));
		dc.setProjection(pl);
		dc.addOrder(Order.asc("NAME_GROUP"));
		List list = dao.findByCriteria(100, dc);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				editor.put(Vni2Uni.convertToUnicode(list.get(i).toString()),
						list.get(i));
			}
		}
		return editor;
	}

	public static MappingPropertyEditor getAllDeptName() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(
				N_DEPARTMENT.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_DEPARTMENT.class);
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.distinct(Projections.property("NAME_DEPT_NAME")));
		dc.setProjection(pl);
		dc.addOrder(Order.asc("NAME_DEPT_NAME"));
		List list = dao.findByCriteria(100, dc);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				Object obj = list.get(i);
				editor.put(Vni2Uni.convertToUnicode(obj.toString()), obj);
			}
		}
		return editor;
	}

	public static MappingPropertyEditor getDeptName(Object fact) {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(
				N_DEPARTMENT.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_DEPARTMENT.class);
		dc.add(Restrictions.eq("NAME_FACT", fact));
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.distinct(Projections.property("NAME_DEPT_NAME")));
		dc.setProjection(pl);
		dc.addOrder(Order.asc("NAME_DEPT_NAME"));
		List list = dao.findByCriteria(500, dc);
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			if (obj != null)
				editor.put(Vni2Uni.convertToUnicode(obj.toString()), obj);
		}
		return editor;
	}

	public static MappingPropertyEditor getDeptName(Object fact, Object group) {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(
				N_DEPARTMENT.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_DEPARTMENT.class);
		dc.add(Restrictions.eq("NAME_FACT", fact));
		if (group != null && !group.equals("")) {
			dc.add(Restrictions.eq("NAME_GROUP", group));
		} else {
			dc.add(Restrictions.isNull("NAME_GROUP"));
		}
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.property("NAME_DEPT_NAME"));
		dc.setProjection(pl);
		dc.addOrder(Order.asc("NAME_DEPT_NAME"));
		List list = dao.findByCriteria(500, dc);
		for (int i = 0; i < list.size(); i++) {
			editor.put(Vni2Uni.convertToUnicode(list.get(i).toString()), list
					.get(i));
		}
		return editor;
	}

	public static MappingPropertyEditor getDept(Object fact) {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(
				N_DEPARTMENT.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_DEPARTMENT.class);
		dc.add(Restrictions.eq("NAME_FACT", fact));
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.property("ID_DEPT"));
		dc.setProjection(pl);
		List list = dao.findByCriteria(500, dc);
		// editor.put("", null);
		for (int i = 0; i < list.size(); i++) {
			editor.put(list.get(i).toString(), list.get(i));
		}
		return editor;
	}

	@SuppressWarnings("unchecked")
	public static MappingPropertyEditor getAllDept() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(
				N_DEPARTMENT.class);
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.property("ID_DEPT"));
		DetachedCriteria dc = DetachedCriteria.forClass(N_DEPARTMENT.class);
		dc.setProjection(pl);
		List list = dao.findByCriteria(2000, dc);
		for (int i = 0; i < list.size(); i++) {
			editor.put(list.get(i).toString(), list.get(i).toString());
		}
		return editor;
	}

	public static MappingPropertyEditor getDept(Object fact, Object group) {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(
				N_DEPARTMENT.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_DEPARTMENT.class);
		dc.add(Restrictions.eq("NAME_FACT", fact));
		if (group != null && !group.equals("")) {
			dc.add(Restrictions.eq("NAME_GROUP", group));
		} else {
			dc.add(Restrictions.isNull("NAME_GROUP"));
		}
		List<N_DEPARTMENT> list = dao.findByCriteria(500, dc);
		for (int i = 0; i < list.size(); i++) {
			N_DEPARTMENT data = list.get(i);
			editor.put(Vni2Uni.convertToUnicode(data.getNAME_DEPT()), data
					.getID_DEPT());
		}
		return editor;
	}
	
	public static MappingPropertyEditor getNameDeptName(Object fact, Object group) {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(
				N_DEPARTMENT.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_DEPARTMENT.class);
		dc.add(Restrictions.eq("NAME_FACT", fact));
		if (group != null && !group.equals("")) {
			dc.add(Restrictions.eq("NAME_GROUP", group));
		} else {
			dc.add(Restrictions.isNull("NAME_GROUP"));
		}
		dc.addOrder(Order.asc("NAME_DEPT_NAME"));
		List<N_DEPARTMENT> list = dao.findByCriteria(500, dc);
		for (int i = 0; i < list.size(); i++) {
			N_DEPARTMENT data = list.get(i);
			editor.put(Vni2Uni.convertToUnicode(data.getNAME_DEPT_NAME()), data
					.getID_DEPT());
		}
		return editor;
	}

	public static String findGroup(String dept) {
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(
				N_DEPARTMENT.class);
		List list = dao.find(1,
				"select o.NAME_GROUP from N_DEPARTMENT o where o.ID_DEPT=?",
				new Object[] { dept });
		if (list.size() > 0) {
			return list.get(0).toString();
		}
		return null;
	}

	public static List<String> findGroupByDeptName(String fact, String deptName) {
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(N_DEPARTMENT.class);
		List list = dao.find(1000,	"select o.NAME_GROUP from N_DEPARTMENT o where o.NAME_FACT=? and o.NAME_DEPT_NAME=?",
						new Object[] { fact, deptName });		
		return list;
	}

	public static String findFact(String group) {
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(
				N_DEPARTMENT.class);
		List list = dao.find(1,
				"select o.NAME_FACT from N_DEPARTMENT o where o.NAME_GROUP=?",
				new Object[] { group });
		if (list.size() > 0) {
			return list.get(0).toString();
		}
		return null;
	}

	public static String findDept(String deptName, String fact) {
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(
				N_DEPARTMENT.class);
		List list = dao
				.find(
						1,
						"select o.ID_DEPT from N_DEPARTMENT o where o.NAME_FACT=? and o.NAME_DEPT_NAME=?",
						new Object[] { fact, deptName });
		if (list.size() > 0) {
			return list.get(0).toString();
		}
		return null;
	}

	public static String findDeptName(String dept, String fact) {
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(
				N_DEPARTMENT.class);
		List list = dao
				.find(
						1,
						"select o.NAME_DEPT_NAME from N_DEPARTMENT o where o.NAME_FACT=? and o.ID_DEPT=?",
						new Object[] { fact, dept });
		if (list.size() > 0) {
			return list.get(0).toString();
		}
		return null;
	}
	
	public static MappingPropertyEditor getDepartmentEditor(){
		if (deptEditor==null){
			deptEditor=new MappingPropertyEditor();
			GenericJdbcDAO<N_DEPARTMENT, String> dao = new GenericJdbcDAO<N_DEPARTMENT, String>("N_DEPARTMENT", N_DEPARTMENT.class);
			dao.setVniColumns(new String[]{"NAME_DEPT","NAME_DEPT_NAME","NAME_GROUP"});
			List<N_DEPARTMENT> list = dao.findAll(10000);
			Collections.sort(list, new Comparator<N_DEPARTMENT>(){
				@Override
				public int compare(N_DEPARTMENT o1, N_DEPARTMENT o2) {
					return o1.getNAME_DEPT().compareTo(o2.getNAME_DEPT());
				}
			});
			for (N_DEPARTMENT data:list){
				deptEditor.put(data.getID_DEPT(), data);
			}
		}
		return deptEditor;
	}
	
	public static MappingPropertyEditor getATMGroupEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<ATM, ATMPk> dao = Application.getApp().getDao(ATM.class);
		DetachedCriteria dc = DetachedCriteria.forClass(ATM.class);
		dc.addOrder(Order.asc("NAMES"));		
		List<ATM> list = dao.findByCriteria(5000, dc);
		for (ATM data:list){
			e.put(Vni2Uni.convertToUnicode(data.getNAMES()), data.getCODE());
		}
		
		/*
		List<ATM> list = dao.find(5000, "SELECT NAME,CODE,NAMES,N_FACT FROM ATM ORDER BY NAMES",null);
		for (ATM data:list){
			e.put(Vni2Uni.convertToUnicode(list.get(2).toString()), list.get(1).toString());
		}*/
		return e;
	}
	
	public static MappingPropertyEditor getDeptGroupEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		IGenericDAO<DEPTCODE, String> dao = Application.getApp().getDao(DEPTCODE.class);
		DetachedCriteria dc = DetachedCriteria.forClass(DEPTCODE.class);
		dc.addOrder(Order.asc("NAME"));
		List<DEPTCODE> list = dao.findByCriteria(5000, dc);
		for (DEPTCODE data:list){
			e.put(Vni2Uni.convertToUnicode(data.getNAME()), data.getCODE());
		}
		return e;
	}
	
	public static String findDeptNameFolowDept(String dept){//05/03/2012 ngan
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(N_DEPARTMENT.class);
		List list = dao.find(1, "select o.NAME_DEPT_NAME from N_DEPARTMENT o where o.ID_DEPT=?", new Object[]{dept});
		if (list.size()>0){
			return list.get(0).toString();
		}
		return null;
	}
	
	//03/03/2012 Tim xuong theo don vi Ngan
	public static String findFactFollowDept(String dept){
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(N_DEPARTMENT.class);
		List list = dao.find(1, "select o.NAME_FACT from N_DEPARTMENT o where o.ID_DEPT=?", new Object[]{dept});
		if (list.size()>0){
			return list.get(0).toString();
		}
		return null;
	}

	public static String findDept(String fact, String lean, String deptName) {
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(
				N_DEPARTMENT.class);
		List list;
		if (lean==null||lean.equals("")){
			list = dao.find(1,"select o.ID_DEPT from N_DEPARTMENT o where o.NAME_FACT=? and o.NAME_DEPT_NAME=?",
					new Object[] { fact, deptName });
		}else{
			list = dao.find(1,"select o.ID_DEPT from N_DEPARTMENT o where o.NAME_FACT=? and o.NAME_GROUP=? and o.NAME_DEPT_NAME=?",
					new Object[] { fact, lean, deptName });
		}
		if (list.size() > 0) {
			return list.get(0).toString();
		}
		return null;
	}
}
