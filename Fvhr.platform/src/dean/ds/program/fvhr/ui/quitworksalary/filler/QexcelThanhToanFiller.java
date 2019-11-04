package ds.program.fvhr.ui.quitworksalary.filler;

import java.util.ArrayList;
import java.util.List;

import ds.program.fvhr.domain.ATTQUIT;
import fv.util.ReportExcelFiller;

public class QexcelThanhToanFiller extends ReportExcelFiller<ATTQUIT> {
	public QexcelThanhToanFiller() {
		excelMap.put("EMPSN", 1);
		excelMap.put("NOTE_NV", 4);
		excelMap.put("DEPT_KT", 5);
		excelMap.put("DEPSN", 6);
		excelMap.put("BSAL_AVG", 7);
		excelMap.put("PN_CONLAI", 8);
		excelMap.put("PN_CONLAI_S", 9);
		excelMap.put("MM_TROCAP", 10);
		excelMap.put("DD_NGHITRUOC", 11);
		excelMap.put("TC_BSALY", 12);
		excelMap.put("DD_NGHITRUOC_S", 13);
		excelMap.put("TT_TCTVIEC", 14);
		excelMap.put("TS_HIENTAI", 15);
		excelMap.put("YLBX", 16);
		excelMap.put("JOININSU", 17);
		excelMap.put("BH_TNGHIEP", 18);
		excelMap.put("STNV", 19);
		excelMap.put("TKQ", 20);
		excelMap.put("BONUS9", 21);
		excelMap.put("COMBSALY", 22);
		excelMap.put("BH_QUY", 23);
		excelMap.put("BU_BHYT", 24);
		excelMap.put("THU_BHYT", 25);
		excelMap.put("NOTE", 26);
		excelMap.put("TT_TCTVIEC1", 27);
		excelMap.put("TS_HIENTAI2", 28);
		// ////////////////////////
		List<String> ex = new ArrayList<String>();
		ex.add("PN_CONLAI");
		ex.add("MM_TROCAP");
		ex.add("MM_DENBU");
		ex.add("DD_NGHITRUOC");
		setExcludeDivide(ex);
	}
}
