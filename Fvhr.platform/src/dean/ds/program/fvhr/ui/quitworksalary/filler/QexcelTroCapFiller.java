package ds.program.fvhr.ui.quitworksalary.filler;

import java.util.ArrayList;
import java.util.List;

import ds.program.fvhr.domain.ATTQUIT;
import fv.util.ReportExcelFiller;

public class QexcelTroCapFiller extends ReportExcelFiller<ATTQUIT> {
	public QexcelTroCapFiller() {
		excelMap.put("EMPSN", 0);
		excelMap.put("NOTE_NV", 3);
		excelMap.put("DEPT_KT", 4);
		excelMap.put("DEPSN", 5);
		excelMap.put("HIRED", 6);
		excelMap.put("BSALY", 7);
		excelMap.put("BONUS2_GOC", 8);
		excelMap.put("BSAL_AVG", 9);
		excelMap.put("BONUS2_AVG", 10);
		excelMap.put("PN_CONLAI", 11);
		excelMap.put("PN_CONLAI_S", 13);
		excelMap.put("MM_TROCAP", 14);
		excelMap.put("MM_DENBU", 15);
		excelMap.put("DD_NGHITRUOC", 16);
		excelMap.put("TC_BSALY", 17);
		excelMap.put("TC_BONUS2", 18);
		excelMap.put("MM_DENBU_S", 19);
		excelMap.put("DD_NGHITRUOC_S", 20);
		excelMap.put("MM_TROCAP_S", 21);
		excelMap.put("TT_TCTVIEC", 22);
		excelMap.put("TT_TCTVIEC1", 23);
		excelMap.put("NOTE", 24);
		excelMap.put("TTS", 25);
		excelMap.put("TS1", 26);
		// /////////////////
		List<String> ex = new ArrayList<String>();
		ex.add("PN_CONLAI");
		ex.add("MM_TROCAP");
		ex.add("MM_DENBU");
		ex.add("DD_NGHITRUOC");
		setExcludeDivide(ex);
	}
}
