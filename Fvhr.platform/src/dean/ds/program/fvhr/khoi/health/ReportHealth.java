package ds.program.fvhr.khoi.health;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.domain.BaoHiemYT_SYS;
import ds.program.fvhr.domain.N_N_HEALTH_R;
import ds.program.fvhr.khoi.health.HealthProgram;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.task.AListReportTask;

//Health report task
public class ReportHealth extends AListReportTask {

	private static final long serialVersionUID = 1L;

	private final int INDEX_CELL_DAIDE = 3;
	private final int INDEX_CELL_CHAT1 = 6;
	private final int INDEX_CELL_CHAT2 = 9;
	private final int INDEX_CELL_BEP_1_2_3 = 12;
	private final int INDEX_CELL_FVL = 15;
	private final int INDEX_CELL_FV_GIAYMAU = 18;
	private final int INDEX_CELL_FV1 = 21;
	private final int INDEX_CELL_FV2 = 24;
	private final int INDEX_CELL_FV3 = 27;
	private final int INDEX_CELL_FV5 = 30;
	private final int INDEX_CELL_FVL_KS = 33;
	private final int INDEX_CELL_MSHAN = 36;
	private final int INDEX_CELL_TONGBO = 39;
	private final int INDEX_CELL_MAY_DAN = 42;
	private final int INDEX_CELL_XT = 45;
	private final int INDEX_CELL_TOANCONGTY = 49;
	
	OBJ_UTILITY obj_util;
	String date_export = "";

	/**
	 * Ngay Thang can xet
	 */
	Date date_input;
	IGenericDAO<N_N_HEALTH_R, String> obj_dao;
	SimpleDateFormat sf;// không đồng nhất
	static ArrayList<Object[]> list_out = new ArrayList<Object[]>();

	// private String tableExport=HealthProgram.
	@Override
	protected String[] getDisplayColumns() {
		return new String[] {};
	}

	@SuppressWarnings("unchecked")
	// ===============export======================
	@Override
	protected void filterHeader(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow header) {

		System.out.println("BEGIN EXPORT HEALTH REPORT !!!");

		obj_dao = Application.getApp().getDao(N_N_HEALTH_R.class);
		obj_util = new OBJ_UTILITY();

		sf = OBJ_UTILITY.Get_format_date();
		date_export = HealthProgram.DATE_EXPORT;

		HSSFSheet sheet_0_AllFact = wb.getSheetAt(0);
		HSSFRow row = sheet_0_AllFact.getRow(0);
		//
		HSSFCell cell = row.getCell(9);
		cell.setCellValue("BAÛO HIEÅM Y TEÁ ");
		HSSFSheet sheet_1_DetailEmpsn = wb.getSheetAt(1);

		try {
			date_input = sf.parse(date_export);
			System.out.println("date export " + date_input);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String month_ = sf.format(date_input).substring(3, 5);
		String year_ = sf.format(date_input).substring(6, 10);

		cell = row.getCell(10);
		// tháng
		cell.setCellValue("T" + month_ + "/" + year_);
		row = sheet_0_AllFact.getRow(1);
		cell = row.getCell(9);
		cell.setCellValue(year_ + " 年 第 " + month_ + " 月醫 療 保 險");
		row = sheet_0_AllFact.getRow(2);
		cell = row.getCell(11);
		cell.setCellValue(month_ + " 月");

		try {
			// PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_TONGBO);
			PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_DAIDE);
			PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_CHAT1);
			PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_CHAT2);
			PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_BEP_1_2_3);
			PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_FVL);
			PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_FV_GIAYMAU);
			PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_FV1);
			PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_FV2);
			PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_FV3);
			PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_FV5);
			PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_FVL_KS);
			PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_MSHAN);
			PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_TONGBO);
			PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_MAY_DAN);
			PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact, INDEX_CELL_XT);
			// PRINT_GENERAL_BY_NAME_FACT(sheet_0_AllFact,
			// INDEX_CELL_TOANCONGTY);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		String check_fact = HealthProgram.NAME_FACT;
		HANDLE_EXPORT_DETAIL(sheet_1_DetailEmpsn, date_input);
		// name fact export

		if (check_fact == null || check_fact.equals("")
				|| check_fact.equals("ALL")) {
			int rowIndex = 48;
			int cellIndex = 11;
			HSSFCell cellTotal = sheet_0_AllFact.getRow(rowIndex).getCell(
					cellIndex);
			Double total = cellTotal.getNumericCellValue();
			IGenericDAO<BaoHiemYT_SYS, String> dao = Application.getApp()
					.getDao(BaoHiemYT_SYS.class);
			BaoHiemYT_SYS a = new BaoHiemYT_SYS(2, date_input, total); // chu y
																		// truyen
																		// tham
																		// so
																		// cho
																		// dung
																		// loai
																		// BH
																		// nhu o
																		// duoi
			try {
				dao.save(a);
			} catch (Exception e) {
				System.out.println("DA LUU DU LIEU TONG VAO DB RUI");
				// String temp = "";
				// if (userID.equals("ADMIN") && temp.equals("")) {
				// dao.update(a);
				// }
				if (userID.equals("ADMIN")) {
					dao.update(a);
				}
			}

		}
	}

	// ===============export======================
	@Override
	protected void filterRow(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow header,
			HSSFRow row) {

	}

	@Override
	protected String createSQLStatement() {
		String sql = "SELECT * FROM DSPB00 where 1!= 1 ";
		filterParams = new Object[0];// ←須將filter清空
		return sql;
	}

	private void HANDLE_EXPORT_DETAIL(HSSFSheet sheet_, Date date_input_) {

		OBJ_UTILITY obj_util = new OBJ_UTILITY();

		String xuong = HealthProgram.NAME_FACT; // 28/10/2011 Ngan
												// them de xuat chi
												// tiet theo tung
												// xuong
		String sql = "";
		/**
		 * ======== XUAT RA NHUNG NGUOI DI LAM BINH THUONG ==== status = 1
		 */

		// tam thoi xuat thang hien tai

		sql = "SELECT t.empsn,"
				+ "       em.fname,"
				+ "       em.lname,"
				+ "		t.status,"
				+ "       d.id_dept,"
				+ "		d.name_dept,"
				+ "		t.salary_m,"
				+ // luong xuat ky trinh vi neu lay salary_b se sai khi NS
					// vao

				"		t.money,"
				+ "		t.note\n"
				+ "FROM "
				+ HealthProgram.TABLE_EXPORT
				+ " t,n_employee em,n_department d\n"
				+ "WHERE   t.empsn = em.empsn\n"
				+ " AND t.depsn = d.id_dept\n"
				+ "AND   (   ((t.status = 1 or t.status = 0) and t.num >= 1  and to_char(t.lock_date,'mm/yyyy') = '"
				+ date_export.substring(date_export.indexOf("/") + 1)
				+ "')\n"
				// nghi viec
				+ "OR   (t.status =-1\n" + "				 AND t.num >=1 "
				+ "                and to_char(t.lock_date,'mm/yyyy') = '"
				+ date_export.substring(date_export.indexOf("/") + 1)
				+ "')) and t.commit=1 " + " and t.depsn <> '00000'"
				+ "and t.lanchay=" + HealthProgram.LAN_CHAY + "\n"
				+ HealthProgram.QUERY_SQL;

		// + "AND to_char(t.lock_date,'mm/yyyy') = '"
		// + date_export.substring(date_export.indexOf("/") + 1) + "'"
		// + " and t.commit=1  and t.status <> 0 and t.depsn <> '00000'"
		// + HealthProgram.QUERY_SQL;

		System.out.println(" ==========sql xuong =========" + sql);

		List<Object[]> list_arr = obj_util.Exe_Sql_nfield_nrow(sql, 9);

		for (int i = 0; i < list_arr.size(); i++) {
			Object[] arr_child = list_arr.get(i);
			for (int j = 0; j < arr_child.length; j++) {
				Object value_ = arr_child[j];
				switch (j) {
				case 0: // empsn
					Write_Cell_for_String(sheet_, value_, i + 1, j);// old i
					break;

				case 1: // Ho
					Write_Cell_for_String(sheet_, value_, i + 1, j);
					break;

				case 2: // ten
					Write_Cell_for_String(sheet_, value_, i + 1, j);
					break;

				case 3: // status
					// if(value_.equals("NV.NGHÆ VIEÄC")){
					Write_Cell_for_Number(sheet_, value_, i + 1, j);
					break;

				case 4: // id_dept
					Write_Cell_for_String(sheet_, value_, i + 1, j);
					break;

				case 5: // name_dept
					Write_Cell_for_String(sheet_, value_, i + 1, j);
					break;
				case 6: // salary

					Write_Cell_for_Number(sheet_, value_, i + 1, j); // lay
																		// trong
																		// n_health
																		// vi no
																		// nghi
																		// viec
																		// can
																		// phai
																		// nhan
																		// luong

					break;

				case 7: // money
					Write_Cell_for_Number(sheet_, value_, i + 1, j);
					break;
				case 8: // note
					Write_Cell_for_String(sheet_, value_, i + 1, j);
					break;

				default:
					break;
				}
			}

		} // the end : for(Object[] arr_info : list)

	}// the end : handle_export(HSSFSheet sheet0)

	/**
	 * i_row_ : begin sheet is i_row = 0 j_column_ : begin sheet is i_column = 0
	 * 
	 * @param sheet_
	 * @param cell_value_
	 *            <=> data
	 * @param i_row_
	 *            <=> y
	 * @param j_column_
	 *            <=> x
	 */

	private void Write_Cell_for_Number(HSSFSheet sheet_, Object cell_value_,
			int i_row_, int j_column_) {
		HSSFRow row_;
		HSSFCell cell_;
		cell_value_ = cell_value_ == null ? 0 : cell_value_;
		double value_ = 0;

		try {
			value_ = Double.valueOf(cell_value_.toString());

		} catch (Exception e) {
			value_ = 0;
		}

		try {
			row_ = sheet_.getRow(i_row_);
			if (row_ == null) {
				row_ = sheet_.createRow(i_row_);
			}
		} catch (Exception e) {
			row_ = sheet_.createRow(i_row_);
		}
		try {
			cell_ = row_.getCell(j_column_);
			if (cell_ == null) {
				cell_ = row_.createCell(j_column_);
				cell_.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			}
		} catch (Exception e) {

			cell_ = row_.createCell(j_column_);
			cell_.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		}

		cell_.setCellValue(value_);

	}

	private void Write_Cell_for_String(HSSFSheet sheet_, Object cell_value_,
			int i_row_, int j_column_) {
		HSSFRow row_;
		HSSFCell cell_;
		cell_value_ = cell_value_ == null ? "" : cell_value_;
		String value_ = cell_value_.toString();

		try {

			row_ = sheet_.getRow(i_row_);
			if (row_ == null) {
				row_ = sheet_.createRow(i_row_);
			}

		} catch (Exception e) {
			row_ = sheet_.createRow(i_row_);
		}

		try {

			cell_ = row_.getCell(j_column_);
			if (cell_ == null) {
				cell_ = row_.createCell(j_column_);
				cell_.setCellType(HSSFCell.CELL_TYPE_STRING);
			}

		} catch (Exception e) {
			cell_ = row_.createCell(j_column_);
			cell_.setCellType(HSSFCell.CELL_TYPE_STRING);
		}

		cell_.setCellValue(value_);
	}

	private void PRINT_GENERAL_BY_NAME_FACT(HSSFSheet sheet_, int index_fact)
			throws Exception {
		INFO_GENERAL obj_general = new INFO_GENERAL();

		obj_general = Find_Data_General(index_fact, date_input, obj_general);

		Write_Cell_for_Number(sheet_, obj_general.Get_SONGUOI_BHXH(),
				index_fact, 4);
		Write_Cell_for_Number(sheet_, obj_general.Get_SONGUOI_TN(), index_fact,
				7);
		Write_Cell_for_Number(sheet_, obj_general.Get_SONGUOI(), index_fact, 11);
		index_fact++;

		Write_Cell_for_Number(sheet_, obj_general.Get_TONGLUONG_BHXH(),
				index_fact, 4);
		Write_Cell_for_Number(sheet_, obj_general.Get_TONGLUONG_TN(),
				index_fact, 7);
		Write_Cell_for_Number(sheet_, obj_general.Get_TONGLUONG(), index_fact,
				11);

		// Begin 31/10/2011 Ngan them de xuat ra luon so tien chuyen 4.5%
		
		index_fact++;

		Write_Cell_for_Number(sheet_, obj_general.Get_TONGTIEN_BHYT(),
				index_fact, 11);
		// End 31/10/2011 Ngan them de xuat ra luon so tien chuyen 4.5%

		index_fact++;
		System.out.println(sheet_.getRow(index_fact).getCell(11).toString());
		// ADD FVL.KS sau FV5 ke tu T06/2013, 06/07/2013, HA
		// + 3 cho moi dk, 45-->48
		if (index_fact == 48) {
			index_fact++;
			Write_Cell_for_Total(
					sheet_,
					"L4+L7+L10+L13+L16+L19+L22+L25+L28+L31+L34+L37+L40+L43+L46",
					index_fact, 11);
		}

		if (index_fact == 49) {
			index_fact++;
			Write_Cell_for_Total(
					sheet_,
					"L5+L8+L11+L14+L17+L20+L23+L26+L29+L32+L35+L38+L41+L44+L47",
					index_fact, 11);
		}

		if ((index_fact == 50) || (index_fact == 52)) {
			index_fact++;
			Write_Cell_for_Total(
					sheet_,
					"L6+L9+L12+L15+L18+L21+L24+L27+L30+L33+L36+L39+L42+L45+L48",
					index_fact, 11);
		}
	}

	private void Write_Cell_for_Total(HSSFSheet sheet_, String operate_str,
			int i_name, int i) {
		HSSFRow row = sheet_.getRow(i_name);
		HSSFCell cell = row.createCell(i);

		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellFormula(operate_str);
		cell.getCellStyle().setDataFormat(
				sheet_.getWorkbook().createDataFormat().getFormat("#,###"));

	}

	// xuất báo cáo theo xưởng.
	private INFO_GENERAL Find_Data_General(int i_name, Date date_input_,
			INFO_GENERAL obj_general) throws SQLException {

		int so_nguoi_bhyt_ = 0;

		long luong_bhyt_ = 0;
		double tien_bhyt_ = 0;// 31/10/2011
		String sql_yt = "";

		String m_y = HealthProgram.DATE_EXPORT
				.substring(HealthProgram.DATE_EXPORT.indexOf("/") + 1);

		sql_yt = " SELECT count(h.empsn),nvl(sum(h.salary_m),0)\n"
				+ " FROM "
				+ HealthProgram.TABLE_EXPORT
				+ " h, n_department d\n, n_employee e\n"
				+ "WHERE  h.empsn = e.empsn and h.depsn = d.id_dept\n"
				// status = 0 and num>=1 nghỉ sản ra
				+ "AND   (   ((h.status = 1 or h.status = 0) and num >= 1  and to_char(h.lock_date,'mm/yyyy') = '"
				+ m_y
				+ "')\n"
				// nghỉ việc tại tháng
				+ "OR ( h.status = -1 and h.num >=1 and to_char(h.lock_date,'mm/yyyy') = '"
				+ m_y + "'))\n" + "and h.commit=1 and lanchay="
				+ HealthProgram.LAN_CHAY;

		System.out.println("Xuat bao cao theo xuong. " + sql_yt);

		// sql_yt = "SELECT count(h.empsn), nvl(sum(h.salary_m),0)\n"
		// + " FROM "
		// + HealthProgram.TABLE_EXPORT
		// + " h, n_department d\n, n_employee e"
		// +
		// "WHERE  h.empsn=e.empsn and h.depsn = d.id_dept and h.commit=1 and h.status <> 0 or h.status_month=5";
		System.out.println("name fact : " + HealthProgram.NAME_FACT);
		sql_yt += getNameFactByINDEX_CELL(i_name);

		System.out
				.println("Lan chay " + HealthProgram.LAN_CHAY + "\t" + i_name);
		if (HealthProgram.LAN_CHAY == 1) {
			List<Object> list_yt;

			list_yt = obj_util.Exe_sql_nfield_1row(sql_yt, 2);

			System.out.println("sql y te " + sql_yt);

			if (list_yt != null) {
				so_nguoi_bhyt_ = obj_general.Get_SONGUOI()
						+ Integer.valueOf(list_yt.get(0).toString());
				luong_bhyt_ = obj_general.Get_TONGLUONG()
						+ Long.valueOf(list_yt.get(1).toString());
				// tien_bhyt_ = luong_bhyt_ * 0.045;

			}

		} else {
			// lần 2
			// lấy theo xưởng
			String sqlDefault = "select count(k.empsn), nvl(sum(k.salary_m),0)\n"
					+ " from k_n_n_health_r k, n_department d\n"
					+ " where k.depsn = d.id_dept and k.lanchay=2 "
					+ getNameFactByINDEX_CELL(i_name)
					+ " and to_char(k.lock_date,'mm/yyyy')='"
					+ date_export.substring(date_export.indexOf("/") + 1)
					+ "' "
					// + " and d.id_dept='TB012'"
					+ " group by k.commit " + "	having k.commit=";

			String sqlEmpsnTang = sqlDefault + "1";
			//String sqlEmpsnGiam = sqlDefault + "0";

			System.out.println("NameFact " + getNameFactByINDEX_CELL(i_name)
					+ i_name);
			System.out.println("Khoi test sql tang  " + HealthProgram.NAME_FACT
					+ sqlEmpsnTang);
//			System.out.println("\nKhoi test sql giam "
//					+ HealthProgram.NAME_FACT + sqlEmpsnGiam);

			List<Object> listEmpsnTang = obj_util.sql_nfield_1row(sqlEmpsnTang,
					2);

//			List<Object> listEmpsnGiam = obj_util.sql_nfield_1row(sqlEmpsnGiam,
//					2);

			int empsnGiam = 0;
			long tongluongEmpsnGiam = 0;
//			if (listEmpsnGiam.size() > 0) {
//				empsnGiam = Integer.parseInt(listEmpsnGiam.get(0).toString());
//				tongluongEmpsnGiam = Long.valueOf(listEmpsnGiam.get(1)
//						.toString());
//			}

			int empsnTang = 0;
			long tongluongempsnTang = 0;
			if (listEmpsnTang.size() > 0) {
				empsnTang = Integer.parseInt(listEmpsnTang.get(0).toString());
				tongluongempsnTang = Long.valueOf(listEmpsnTang.get(1)
						.toString());
			}
			System.out.println("So nguoi tang cua xuong "
					+ HealthProgram.NAME_FACT + " -" + empsnTang);
			System.out.println("So nguoi giam cua xuong "
					+ HealthProgram.NAME_FACT + " -" + empsnGiam);
			so_nguoi_bhyt_ = obj_general.Get_SONGUOI()
					+ (empsnTang - empsnGiam);

			System.out.println("so nguoi bao hiem " + so_nguoi_bhyt_);

			luong_bhyt_ = obj_general.Get_TONGLUONG()
					+ (tongluongempsnTang - tongluongEmpsnGiam);

			System.out.println("luong bao hiem " + luong_bhyt_);

		}

		// hien hanh
		// BHYT
		tien_bhyt_ = luong_bhyt_ * 0.045;
		obj_general.Set_SONGUOI(so_nguoi_bhyt_);
		obj_general.Set_TONGLUONG(luong_bhyt_);
		obj_general.Set_TONGTIEN_BHYT(tien_bhyt_);

		return obj_general;
	}

	/**
	 * @param <b>INDEX_CELL_DAIDE ,<br>
	 *        INDEX_CELL_CHAT1,<br>
	 *        INDEX_CELL_CHAT2,<br>
	 *        INDEX_CELL_BEP_1_2_3, <br>
	 *        INDEX_CELL_FVL<br>
	 *        INDEX_CELL_FV_GIAYMAU<br>
	 *        .....
	 * @return sql namefact
	 */
	private String getNameFactByINDEX_CELL(int i_name) {

		switch (i_name) {
		case 3: // DAI DE
			return " AND   d.name_fact = 'FVLS'";

		case 6: // X.DAO CHAT 1

			return " AND   d.id_dept 	= 'W0008'";

		case 9: // X.DAO CHAT 2

			return " AND   d.id_dept in( 'W0007','W0009' )";

		case 12: // BEP 1 + 2 + 3

			return " AND   d.id_dept in ( '00001','00002','00003' )";

		case 15: // FVL

			return " AND   (d.name_dept like 'FVL.F12%' or d.id_dept = 'KHODE')";

		case 18: // FVL - GIAY MAU

			return " AND (d.name_dept like 'FVL.GM%' or d.name_dept like 'FVL.F3-GM%')";

		case 21: // FV1

			return " AND d.name_dept like  'FVL.F1%'"
					+ " AND d.name_dept not like  'FVL.F12%'"
					+ " AND d.id_dept <> 'KHODE'";

		case 24: // FV2

			return " AND d.name_dept like  'FVL.F2%'";

		case 27: // FV3

			return " AND d.name_dept like 'FVL.F3%'"
					+ " AND d.name_dept not like 'FVL.F3-GM%'";

		case 30: // FV5

			return " AND d.name_dept like 'FVL.F5%'";

		case 33: // FVL.KS, BD 06/2013, UPDATE 06/07/2013, HA

			return " AND d.name_dept like  'FVL.KS%'";

		case 36: // MSHAN

			return " AND   d.id_dept 	= 'MS001'";

		case 39: // TONG BO

			return " AND d.name_fact = 'TB'"
					+ " AND d.id_dept not in ('MS001','00001','00002','00003') "
					+ "";

		case 42: // MAY DAN

			return " AND d.name_group = 'FVJ-BGC'";

		case 45: // XUONG THEU

			return " AND d.name_group = 'FVJ-XT'";

		default:
			return "";
		}
	}

}

class INFO_GENERAL {
	private int _so_nguoi;
	private long _tong_luong;
	private int _count;
	private double _tong_tien_bhyt;// 31/10/2011

	private int _so_nguoi_bhxh;
	private long _tong_luong_bhxh;

	private int _so_nguoi_that_nghiep;
	private long _tong_luong_that_nghiep;

	public INFO_GENERAL() {

	}

	public Object Get_TONGTIEN() {
		return null;
	}

	public Object Get_TONGTIEN_TN() {
		return null;
	}

	public Object Get_TONGTIEN_BHXH() {
		return null;
	}

	public int Get_SONGUOI_BHXH() {
		return _so_nguoi_bhxh;
	}

	public void Set_SONGUOI_BHXH(int songuoi) {
		this._so_nguoi_bhxh = songuoi;
	}

	public int Get_SONGUOI_TN() {
		return _so_nguoi_that_nghiep;
	}

	public void Set_SONGUOI_TN(int songuoi) {
		this._so_nguoi_that_nghiep = songuoi;
	}

	public long Get_TONGLUONG_BHXH() {
		return _so_nguoi_bhxh;
	}

	public void Set_TONGLUONG_BHXH(long tongluong_) {
		this._tong_luong_bhxh = tongluong_;
	}

	public long Get_TONGLUONG_TN() {
		return _so_nguoi_that_nghiep;
	}

	public void Set_TONGLUONG_TN(long tongluong_) {
		this._tong_luong_that_nghiep = tongluong_;
	}

	public int Get_SONGUOI() {
		return _so_nguoi;
	}

	public void Set_SONGUOI(int songuoi) {
		this._so_nguoi = songuoi;
	}

	public long Get_TONGLUONG() {
		return _tong_luong;
	}

	public void Set_TONGLUONG(long tongluong) {
		this._tong_luong = tongluong;
	}

	public int Get_COUNT() {
		return _count;
	}

	public void Set_COUNT(int count) {
		this._count = count;
	}

	public double Get_TONGTIEN_BHYT() {
		return _tong_tien_bhyt;
	}

	public void Set_TONGTIEN_BHYT(double tongtien_bhyt) {
		this._tong_tien_bhyt = tongtien_bhyt;
	}
}
