package fv.util;

import ds.program.fvhr.dao.generic.FvGenericDAO;
public class EmpsnValidator implements IEmployeeValidator {

	@Override
	public boolean isWorking(String empsn) {
		return FvGenericDAO.getInstanse().isWorking(empsn, false);
	}
	
	@Override
	public boolean isWorkingOrQuit(String empsn) {
		return FvGenericDAO.getInstanse().isWorking(empsn, true);
	}
}
