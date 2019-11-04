package fv.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_USER_LIMIT;
import ds.program.fvhr.domain.pk.N_USER_LIMITPk;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.info.LoginInfo;
/**
 * Right management
 * @author Hieu
 */
public class RightManagement {
	
	private String vftUserId;
	
	private LoginInfo loginInfo;

	private List<String> rightList;

	public RightManagement(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
		rightList = new ArrayList<String>();
		IGenericDAO udao = Application.getApp().getDao(
				getLoginInfo().getCompanyID());
		List list = udao
				.find(
						1,
						"select b.PB_USERNO from DSPB02 b where b.PB_USERID=?",
						new Object[] { loginInfo.getUserID() });
		if (list.size() > 0) {
			vftUserId = (String) list.get(0);
			initRights(vftUserId);
		}
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public List<String> getRightList() {
		return rightList;
	}

	public void setRightList(List<String> rightList) {
		this.rightList = rightList;
	}

	public boolean checkRight(String empsn) {
		IGenericDAO<N_EMPLOYEE, String> dao = Application.getApp().getDao(
				N_EMPLOYEE.class);
		List list = dao.find(1,
				"select o.USER_MANAGE_ID from N_EMPLOYEE o where o.EMPSN=?",
				new Object[] { empsn });
		
		if (list.size() > 0) {
			String umi = (String) list.get(0);
			for (String s : rightList) {
				if (s.equals(umi)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkDeptRight(String dept) {
		IGenericDAO<N_EMPLOYEE, String> dao = Application.getApp().getDao(
				N_EMPLOYEE.class);
		List list = dao
				.find(
						5000,
						"select distinct o.USER_MANAGE_ID from N_EMPLOYEE o where o.DEPSN=?",
						new Object[] { dept });
		if (list.size() > 0) {
			String s;
			for (int i = 0; i < list.size(); i++) {
				s = (String) list.get(i);
				if (!rightList.contains(s)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public boolean checkDeptNameRight(String fact, String group, String deptName) {
		IGenericDAO dao = Application.getApp().getDao(
				getLoginInfo().getCompanyID());
		List list = dao
				.find(
						5000,
						"select distinct a.USER_MANAGE_ID from N_EMPLOYEE a, N_DEPARTMENT b where a.DEPSN=b.ID_DEPT and b.NAME_FACT=? and b.NAME_GROUP=? and b.NAME_DEPT_NAME=?",
						new Object[] { fact, group, deptName });
		if (list.size() > 0) {
			String s;
			for (int i = 0; i < list.size(); i++) {
				s = (String) list.get(i);
				if (!rightList.contains(s)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public boolean checkGroupRight(String fact, String group) {
		IGenericDAO dao = Application.getApp().getDao(
				getLoginInfo().getCompanyID());
		List list = dao
				.find(
						100,
						"select distinct a.USER_MANAGE_ID from N_EMPLOYEE a, N_DEPARTMENT b where a.DEPSN=b.ID_DEPT and b.NAME_FACT=? and b.NAME_GROUP=?",
						new Object[] { fact, group });
		if (list.size() > 0) {
			String s;
			for (int i = 0; i < list.size(); i++) {
				s = (String) list.get(i);
				if (!rightList.contains(s)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public boolean checkFactRight(String factName) {
		IGenericDAO dao = Application.getApp().getDao(
				getLoginInfo().getCompanyID());
		List list = dao
				.find(
						100,
						"select distinct a.USER_MANAGE_ID from N_EMPLOYEE a, N_DEPARTMENT b where a.DEPSN=b.ID_DEPT and b.NAME_FACT=?",
						new Object[] { factName });
		if (list.size() > 0) {
			String s;
			for (int i = 0; i < list.size(); i++) {
				s = (String) list.get(i);
				if (!rightList.contains(s)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public String getVftUserId(){
		return this.vftUserId;
	}

	private void initRights(String fvUserId) {
		IGenericDAO<N_USER_LIMIT, N_USER_LIMITPk> dao = Application.getApp()
				.getDao(N_USER_LIMIT.class);
		DetachedCriteria dc = DetachedCriteria.forClass(N_USER_LIMIT.class);
		dc.add(Restrictions.eq("MA_USER", fvUserId));
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.distinct(Projections.property("MA_QL")));
		dc.setProjection(pl);
		List list = dao.findByCriteria(100, dc);
		for (int i = 0; i < list.size(); i++) {
			rightList.add(list.get(i).toString());
		}
	}
}
