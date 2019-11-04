package fv.util;

public interface IPermissionValidator {
	boolean hasEmpsnPermission(String empsn);
	boolean hasDeptPermission(String deptId);
	boolean hasDeptNamePermission(String factId, String groupId, String deptName);
	boolean hasGroupPermission(String factId, String groupId);
	boolean hasFactoryPermission(String factId);
}
