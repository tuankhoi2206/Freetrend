package ds.service;

import java.sql.Connection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import dsc.service.KeyNumberGenerator;
import dsc.util.function.ContextUtil;

/**
 * @TODO 起始流水號
 * @TODO 從table其他column取出加入產生的單號中
 * 
 */
public class DSKeyNumberGenerator extends KeyNumberGenerator {
	public static final int MODE_DATE = 1, // 日編
			MODE_SERIAL = 2; // 流水號

	public DSKeyNumberGenerator(Connection con, int formatMode,
			Map<String, Object> keys, Map<String, Object> filters) {
		String format = "$C{PB_ID}$C{PB_SYMBOL}";
		switch (formatMode) {
		case MODE_DATE:
			format += "$Y{2}$M{2}$D{2}";
		case MODE_SERIAL:
			format += "$S{4}";
			break;
		}
		String table = "DSPB13";
		String column = "PB_SERINO2";

		long serialStart = 1;
		try {
			Map<String, Object> columnValues = findColumnValues(table, con,
					keys, filters, false);
			serialStart = parse((String) columnValues.get("PB_SERINO1"), 10);
		} catch (Exception e) {
		}
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		init(con, year, month, day, format, serialStart, table, column, keys,
				filters, false, true);
	}

	public DSKeyNumberGenerator(Connection con, String prefix, int formatMode,
			int serialLength, String table, String column,
			Map<String, Object> keys, Map<String, Object> filters) {
		String format = prefix;
		switch (formatMode) {
		case MODE_DATE:
			format += "$Y{2}$M{2}$D{2}";
		case MODE_SERIAL:
			format += "$S{" + serialLength + "}";
			break;
		}
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		init(con, year, month, day, format, 1, table, column, keys, filters,
				true, false);
	}

	/**
	 * 依序號檔取單據編號
	 * 
	 * @param con
	 *            資料庫連線
	 * @param formatMode
	 *            格式模式
	 * @param table
	 *            檢查的資料表
	 * @param column
	 *            該資料表的編號欄位鍵值
	 * @param keys
	 *            該資料表除編號欄位外其他key欄位
	 * @param filters
	 *            該資料表除編號欄位外其他過濾的條件
	 * @return 取得的單據編號
	 */
	public static String generateFormNumber(Connection con, int formatMode,
			Map<String, Object> keys, Map<String, Object> filters) {
		String format = "$C{PB_ID}$C{PB_SYMBOL}";
		switch (formatMode) {
		case MODE_DATE:
			format += "$Y{2}$M{2}$D{2}";
		case MODE_SERIAL:
			format += "$S{4}";
			break;
		}
		String table = "DSPB13";
		String column = "PB_SERINO2";

		long serialStart = 1;
		try {
			Map<String, Object> columnValues = findColumnValues(table, con,
					keys, filters, false);
			serialStart = parse((String) columnValues.get("PB_SERINO1"), 10);
		} catch (Exception e) {
		}
		return generateNumber(con, format, serialStart, table, column, keys,
				filters, false, true);
	}

	/**
	 * 依資料表取得編號
	 * 
	 * @param con
	 *            資料庫連線
	 * @param prefix
	 *            前置詞
	 * @param formatMode
	 *            格式模式
	 * @param serialLength
	 *            流水號長度
	 * @param table
	 *            檢查的資料表
	 * @param column
	 *            該資料表的編號欄位鍵值
	 * @param keys
	 *            該資料表除編號欄位外其他key欄位
	 * @param filters
	 *            該資料表除編號欄位外其他過濾的條件
	 * @return
	 */
	public static String generateNumber(Connection con, String prefix,
			int formatMode, int serialLength, String table, String column,
			Map<String, Object> keys, Map<String, Object> filters) {
		String format = prefix;
		switch (formatMode) {
		case MODE_DATE:
			format += "$Y{2}$M{2}$D{2}";
		case MODE_SERIAL:
			format += "$S{" + serialLength + "}";
			break;
		}
		return generateNumber(con, format, 1, table, column, keys, filters,
				true, false);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final String companyId = "tw1";
		final Connection con = ContextUtil.getConnection(companyId);

		String format = "$C{PB_ID}$C{PB_SYMBOL}$Y{2}$M{2}$D{2}$S{4}";
		long serialStart = 1;
		String table = "DSPB13";
		String column = "PB_SERINO2";
		Map keys = new HashMap();
		Map filters = new HashMap();
		boolean isCompositedColumn = false;
		boolean updateTable = true;
		String newNumber = KeyNumberGenerator.generateNumber(con, format,
				serialStart, table, column, keys, filters, isCompositedColumn,
				updateTable);
		System.out.println(newNumber);
		// final String table = "DSEL00";
		// final String column = "EL_NO";
		// final Map<String, Object> keys = new HashMap<String, Object>();
		// final Map<String, Object> filters = new HashMap<String, Object>();
		//
		// keys.put("PB_ID", "I");
		// keys.put("PB_DPNO", "113000");
		//
		// final String[] xxx = new String[100];
		// for (int i = 0; i < 100; i++) {
		// Thread t = new Thread(i+"") {
		// public void run() {
		// while(true) {
		// String aaa = DSKeyNumberGenerator.generateFormNumber(con,
		// MODE_SERIAL, keys, filters);
		// if (aaa != null) {
		// xxx[Integer.parseInt(getName())] = aaa;
		// break;
		// }
		// }
		// }
		// };
		// t.start();
		// try {
		// t.join();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		// for (int i = 0; i < 100; i++) {
		// System.out.println(xxx[i]);
		// }
		// System.out.println();

		// final DSKeyNumberGenerator keyGen = new DSKeyNumberGenerator(con,
		// MODE_DATE, keys, filters);
		// for (int i = 0; i < 100; i++) {
		// Thread t = new Thread(i+"") {
		// public void run() {
		// Map<String, Object> keys = new HashMap<String, Object>();
		// keys.put("PB_ID", "I");
		// keys.put("PB_DPNO", "113000");
		// Map<String, Object> filters = new HashMap<String, Object>();
		// while(true) {
		// String aaa = keyGen.generate();
		// if (aaa != null) {
		// xxx[Integer.parseInt(getName())] = aaa;
		// break;
		// }
		// }
		// }
		// };
		// t.start();
		// try {
		// t.join();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		// for (int i = 0; i < 100; i++) {
		// System.out.println(xxx[i]);
		// }
		ContextUtil.closeConnection(con);
	}

}
