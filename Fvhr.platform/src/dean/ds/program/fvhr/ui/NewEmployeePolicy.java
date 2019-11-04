package ds.program.fvhr.ui;

import ds.program.fvhr.domain.N_EMPLOYEE;
//lt1,lt2,tg,bp,mp
//pool checkId
//pool checkNV
public interface NewEmployeePolicy {
	boolean isValid(N_EMPLOYEE e, boolean editMode);

	String getErrorMessage();
}
