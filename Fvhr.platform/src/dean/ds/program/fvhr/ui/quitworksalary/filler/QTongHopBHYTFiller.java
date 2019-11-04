package ds.program.fvhr.ui.quitworksalary.filler;

import ds.program.fvhr.domain.ATTQUIT;
import fv.util.ReportExcelFiller;

public class QTongHopBHYTFiller extends ReportExcelFiller<ATTQUIT> {
	public QTongHopBHYTFiller() {
		excelMap.put("EMPSN", 1);
		excelMap.put("NOTE_NV", 4);
		excelMap.put("DEPT_KT", 5);
		excelMap.put("DEPSN", 6);
		excelMap.put("YLBX", 7);
		excelMap.put("COMBSALY", 8);
		excelMap.put("BU_BHYT", 10);
		excelMap.put("THU_BHYT", 11);
		excelMap.put("NOTE", 12);
	}
}
