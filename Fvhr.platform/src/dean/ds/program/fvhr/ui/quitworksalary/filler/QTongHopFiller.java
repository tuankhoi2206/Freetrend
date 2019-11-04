package ds.program.fvhr.ui.quitworksalary.filler;

import ds.program.fvhr.dao.quitsalary.QuitWorkGenericObject;
import fv.util.ReportExcelFiller;

public class QTongHopFiller extends ReportExcelFiller<QuitWorkGenericObject> {
	public QTongHopFiller() {
		excelMap.put("DEPT_KT", 0);
		excelMap.put("TONG", 2);
		excelMap.put("TTIN", 3);
		excelMap.put("TSHT", 4);
		excelMap.put("TTC", 5);
		excelMap.put("TE_BHYT", 6);
		excelMap.put("TT_BHYT", 7);
		excelMap.put("TE_BHTN", 8);
		excelMap.put("TT_BHTN", 9);
		excelMap.put("TE_BHXH", 10);
		excelMap.put("TT_BHXH", 11);
		excelMap.put("TBORM", 12);
		excelMap.put("TPAY", 13);
	}
}
