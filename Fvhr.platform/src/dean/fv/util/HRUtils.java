package fv.util;

public class HRUtils {
	private IEmployeeValidator empsnValidator;
	private IPermissionValidator permissionValidator;
	private IWPValidator workpointsValidator;
	
		
	public boolean isWorking(String empsn){
		return empsnValidator.isWorking(empsn);
	}
	
	public boolean isWorkingOrQuit(String empsn){
		return empsnValidator.isWorkingOrQuit(empsn);
	}

	public IEmployeeValidator getEmpsnValidator() {
		return empsnValidator;
	}

	public void setEmpsnValidator(IEmployeeValidator empsnValidator) {
		this.empsnValidator = empsnValidator;
	}

	public IPermissionValidator getPermissionValidator() {
		return permissionValidator;
	}

	public void setPermissionValidator(IPermissionValidator permissionValidator) {
		this.permissionValidator = permissionValidator;
	}

	public IWPValidator getWorkpointsValidator() {
		return workpointsValidator;
	}

	public void setWorkpointsValidator(IWPValidator workpointsValidator) {
		this.workpointsValidator = workpointsValidator;
	}
}
