package ds.program.fvhr.ui.quitworksalary.filler;

import java.util.ArrayList;
import java.util.List;

import ds.program.fvhr.domain.ATTQUIT;
import fv.util.ReportExcelFiller;

public class QexcelLuongFiller extends ReportExcelFiller<ATTQUIT> {
	public QexcelLuongFiller() {
		excelMap.put("EMPSN", 1);
		excelMap.put("NOTE_NV", 4);
		excelMap.put("DEPT_KT", 5);
		excelMap.put("DEPSN", 6);
		excelMap.put("HIRED", 7);
		excelMap.put("BSALY", 8);
		excelMap.put("BONUS2_GOC", 9);
		excelMap.put("TDAY", 10);
		excelMap.put("REST", 11);
		excelMap.put("NWHOUR", 12);
		excelMap.put("ADDCLS1", 13);
		excelMap.put("ADDHOL", 14);
		excelMap.put("NADDCLS", 15);
		excelMap.put("TADDCLS", 16);
		excelMap.put("TBASIC", 17);
		excelMap.put("TADDS", 18);
		excelMap.put("BONUS2", 19);
		excelMap.put("BONUS1", 20);
		excelMap.put("BONUS3", 21);
		excelMap.put("BONUS7", 22);
		excelMap.put("BONUS4", 23);
		excelMap.put("BONUS6", 24);
		excelMap.put("BONUS_ACN", 25);
		excelMap.put("TINCOME", 26);
		excelMap.put("YLBX", 27);
		excelMap.put("JOININSU", 28);
		excelMap.put("BH_TNGHIEP", 29);
		excelMap.put("BORM", 30);
		excelMap.put("PAYTAX", 31);
		excelMap.put("KQT", 32);
		excelMap.put("TKQ", 33);
		excelMap.put("TS_HIENTAI", 34);
		excelMap.put("TS_HIENTAI2", 35);
		excelMap.put("NOTE", 47);
		//
		List<String> ex = new ArrayList<String>();
		ex.add("TDAY");
		ex.add("REST");
		ex.add("NWHOUR");
		ex.add("ADDCLS1");
		ex.add("ADDHOL");
		ex.add("NADDCLS");
		ex.add("TADDCLS");
		setExcludeDivide(ex);
	}
}
