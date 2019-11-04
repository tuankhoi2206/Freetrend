package ds.program.fvhr.ui.quitworksalary.validator;

import ds.program.fvhr.dao.quitsalary.AttQuitDAO;

public class AttQuitValidator {
	private AttQuitDAO dao;
	
	public AttQuitValidator(AttQuitDAO dao){
		this.dao=dao;
	}
	
	public boolean isQuitStatusValid(String empsn, String month, String year){
		return dao.isValid(empsn, month, year);
	}
}
