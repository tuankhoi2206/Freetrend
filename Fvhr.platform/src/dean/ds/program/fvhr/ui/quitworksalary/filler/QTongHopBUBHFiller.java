package ds.program.fvhr.ui.quitworksalary.filler;

import ds.program.fvhr.domain.ATTQUIT;
import fv.util.ReportExcelFiller;

public class QTongHopBUBHFiller extends ReportExcelFiller<ATTQUIT> {
	public QTongHopBUBHFiller() {
		excelMap.put("EMPSN", 1);
		excelMap.put("NOTE_NV", 4);
		excelMap.put("DEPT_KT", 5);
		excelMap.put("DEPSN", 6);
		excelMap.put("BONUS9", 7);
		excelMap.put("NOTE", 8);
	}
}
