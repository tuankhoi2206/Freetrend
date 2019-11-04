package fv.util;

import dsc.echo2app.Application;

public class Permission implements IPermissionValidator{

	@Override
	public boolean hasEmpsnPermission(String empsn) {
		RightsHolder rh = ApplicationHelper.getRightsHolder();
		RightManagement rm = rh.getRights(Application.getApp().getLoginInfo()
				.getUserID());
		return rm.checkRight(empsn);
	}

	@Override
	public boolean hasFactoryPermission(String factId) {
		RightsHolder rh = ApplicationHelper.getRightsHolder();
		RightManagement rm = rh.getRights(Application.getApp().getLoginInfo()
				.getUserID());
		return rm.checkFactRight(factId);
	}

	@Override
	public boolean hasDeptPermission(String deptId) {
		RightsHolder rh = ApplicationHelper.getRightsHolder();
		RightManagement rm = rh.getRights(Application.getApp().getLoginInfo()
				.getUserID());
		return rm.checkDeptRight(deptId);
	}

	@Override
	public boolean hasDeptNamePermission(String factId, String groupId, String deptName) {
		RightsHolder rh = ApplicationHelper.getRightsHolder();
		RightManagement rm = rh.getRights(Application.getApp().getLoginInfo()
				.getUserID());
		return rm.checkDeptNameRight(factId, groupId, deptName);
	}

	@Override
	public boolean hasGroupPermission(String factId, String groupId) {
		RightsHolder rh = ApplicationHelper.getRightsHolder();
		RightManagement rm = rh.getRights(Application.getApp().getLoginInfo()
				.getUserID());
		return rm.checkGroupRight(factId, groupId);
	}
}
