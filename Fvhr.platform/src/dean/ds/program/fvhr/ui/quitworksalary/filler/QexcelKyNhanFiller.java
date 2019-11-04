package ds.program.fvhr.ui.quitworksalary.filler;

import java.util.ArrayList;
import java.util.List;

import ds.program.fvhr.domain.ATTQUIT;
import fv.util.ReportExcelFiller;

public class QexcelKyNhanFiller extends ReportExcelFiller<ATTQUIT> {
	public QexcelKyNhanFiller() {
		excelMap.put("EMPSN", 0);
		excelMap.put("NOTE_NV", 4);
		excelMap.put("DEPT_KT", 5);
		excelMap.put("DEPSN", 6);
		excelMap.put("HIRED", 7);
		excelMap.put("BSAL_AVG", 8);
		excelMap.put("BONUS2_AVG", 9);
		excelMap.put("PN_CONLAI", 10);
		excelMap.put("PN_CONLAI_S", 12);
		excelMap.put("MM_TROCAP", 13);
		excelMap.put("MM_DENBU", 14);
		excelMap.put("DD_NGHITRUOC", 15);
		excelMap.put("TC_BSALY", 16);
		excelMap.put("MM_DENBU_S", 17);
		excelMap.put("TC_BONUS2", 18);
		excelMap.put("DD_NGHITRUOC_S", 19);
		excelMap.put("TT_TCTVIEC1", 20);
		excelMap.put("TS_HIENTAI2", 21);
		excelMap.put("TTS", 22);
		excelMap.put("NOTE", 23);//CMND
		excelMap.put("TT_TCTVIEC", 24);
		excelMap.put("TS_HIENTAI", 25);
		// ////////////
		List<String> ex = new ArrayList<String>();
		ex.add("PN_CONLAI");
		ex.add("MM_TROCAP");
		ex.add("MM_DENBU");
		ex.add("DD_NGHITRUOC");
		setExcludeDivide(ex);
	}
}
