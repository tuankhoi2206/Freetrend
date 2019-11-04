package ds.program.fvhr.minh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.Position.Bias;

import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import ds.program.fvhr.dao.JdbcDAO2;
import ds.program.fvhr.domain.N_CT_BHXH_TN;
import ds.program.fvhr.minh.dao.DAO;
import ds.program.fvhr.minh.domain.SuaDuLieu;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.task.ATask;
import dsc.util.function.UUID;
import fv.util.HSSFUtils;
import fv.util.JdbcDAO;
import fv.util.ReportUtils;
import fv.util.Vni2Uni;

public class Excel {// dung de sua du lieu
	private static JdbcDAO2 dao = new JdbcDAO2();
	/**
	 * @param args
	 * @throws IOException
	 */
	private static int Dem = 1;
	private static int Ngay = 0;

	public static void main(String[] arg0) throws IOException {
		// suacadem();
		// suacangay();
		// suadulieubimat();
		// suadulieubimatCANGAY();
		// suatudongcangay();
		// suatudongcadem();//dang test ------- ko dc run , chi debug// neu la thang 5
		// tro di hoac thang da chay roi thi vao getList() de sua lai bang data_daly
		// thanh hien hanh
		// nguoi lai thi lay tat ca so the cua xuong do chay pro roi lay trong hien hanh
		// neu la thang 11/2012 tro ve truoc phai vao callpro() de sua lai pro can goi
		// cho phu hop

		BigDecimal a = BigDecimal.valueOf(2);
		BigDecimal[] b = { a };
		cong(b);
		System.out.println(b[0].longValue());

	}

	public static void cong(BigDecimal[] i) {
		i[0].valueOf(i[0].longValue() + 10);

	}

	public static void suatudongcadem() {
		String timeIn;
		String timeOut;
		String ngayOut;
		float tcv = 0;
		float tcr = 0;
		String timeInC;
		String timeOutC;
		String In;
		String Out;
		System.out.println("Dang lay danh sach------");
		List<SuaDuLieu> list = getList("07/2013", "TB", Dem);
		System.out.println("Lay danh sach ok");
		for (SuaDuLieu s : list) {
			int isC = 0;
			int day = getCountdayofmonth(Integer.valueOf(s.getNgay().substring(3, 5)),
					Integer.valueOf(s.getNgay().substring(6)));

			System.out.println(s.getSothe());
			System.out.println(s.getSoic());
			System.out.println(s.getNgay());
			System.out.println("Tca tinh tien: " + s.getTcttien());
			System.out.println("Gio vao - ra: " + s.getTin() + "-" + s.getTou());
			if (s.getTin() == null && s.getTou() == null)
				continue;

			Date dat = new Date(Integer.valueOf(s.getNgay().substring(6)) - 1900,
					Integer.valueOf(s.getNgay().substring(3, 5)) - 1, Integer.valueOf(s.getNgay().substring(0, 2)));
			if ((s.getTin() == null || s.getTou() == null) && dat.getDay() == 0) {
				dao.getSimpleJdbcTemplate().update("delete n_register_shift t " + // tu thang 11/2012 tro di thi xoa
																					// trong _01122012
						"where t.empsn in (?) and to_char(t.shift_date,'dd/mm/yyyy')=?", s.getSothe(), s.getNgay());
				System.out.println("<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>");
				callpro(s.getSothe(), s.getNgay(), "mm");
				continue;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar ca = Calendar.getInstance();
			ca.setTime(dat);
			ca.add(Calendar.DATE, 1);
			ngayOut = sdf.format(ca.getTime());
			String ttc = dao.getSimpleJdbcTemplate().queryForObject(
					"select substr(time_in,0,2)||substr(time_in,4,2)||"
							+ "substr(time_Out,0,2)||substr(time_Out,4,2) from n_shift t where id_shift = ?",
					String.class, s.getCa());
			timeInC = ttc.substring(0, 4);
			timeOutC = ttc.substring(4);
			tcv = getTCVao(s.getTin(), timeInC);
			tcr = getTCRa(s.getTou(), timeOutC);
			System.out.println("tang ca vao - ra: " + tcv + "-" + tcr);
			Float tc = Float.valueOf(s.getTcttien());
			if ((tcv + tcr) > tc) {
				if (tcv == (tcv + tcr - tc))
					tcv = 0;
				else if (tcr == (tcv + tcr - tc))
					tcr = 0;
				else if (tcv > tcr) {
					tcv -= (tcv + tcr - tc);
					if (tcv < 0) {
						tcr += tcv;
						tcv = 0;
					}
				} else {
					tcr -= (tcv + tcr - tc);
					if (tcr < 0) {
						tcv += tcr;
						tcr = 0;
					}
				}

			} else if ((tcv + tcr) < tc) {// neu thieu tc tinh tien phai lam sao de xac dinh goi chuan
											// tcr = tcr + (tcv+tcr- tc);
			}
			// tinh gio vao ra chuan cho nguoi nay
			int t = ((Integer.valueOf(timeInC.substring(0, 2)) * 60) + Integer.valueOf(timeInC.substring(2)))
					- (int) (60 * tcv);
			In = (t / 60 < 10 ? "0" + t / 60 : t / 60) + "" + (t % 60 < 10 ? "0" + t % 60 : t % 60);
			System.out.println("Gio vao chuan: " + In);
			t = ((Integer.valueOf(timeOutC.substring(0, 2)) * 60) + Integer.valueOf(timeOutC.substring(2)))
					+ (int) (60 * tcr);
			Out = (t / 60 < 10 ? "0" + t / 60 : t / 60) + "" + (t % 60 < 10 ? "0" + t % 60 : t % 60);
			System.out.println("Gio ra chuan: " + Out);

			// sua gio vao

			try {
				timeIn = dao.getSimpleJdbcTemplate().queryForObject(
						"select t.times from n_data_main t where empcn = ?" + "and dates = ?", String.class,
						s.getSoic(), s.getNgay());
				t = ((Integer.valueOf(In.substring(0, 2)) * 60) + Integer.valueOf(In.substring(2)));
				int g;
				if (s.getTin() != null)
					g = (Integer.valueOf(s.getTin().substring(0, 2)) * 60) + Integer.valueOf(s.getTin().substring(2));
				else
					g = 0;
				if (g < (t - 15) && timeIn != null)// neu bi dayoff do mat du lieu vao vi chay bang ham excel thi bo
													// dieu kien != null di
				{
					String[] times = new String[timeIn.length() / 4];
					int j = 0;
					for (int i = 0; i < timeIn.length(); i += 4) {
						times[j] = timeIn.substring(i, i + 4);
						j++;
					}

					String sq = "";
					for (int i = 0; i < times.length; i++) {
						if (!times[i].equals(s.getTin()))
							sq += times[i];
					}

					int r = (int) (Math.random() * 1000) % 15;
					int ti = (r + (t - 15));
					int h = ti / 60;
					int m = ti % 60;
					s.setTin((h < 10 ? "0" + h : h + "") + (m < 10 ? "0" + m : m));

					System.out.println("gio vao moi: " + s.getTin());

					sq = s.getTin() + sq;
					System.out.println(sq);
					String sql = "update n_data_main t set t.times='" + sq + "'," + " t.note='Minh update " + timeIn
							+ " 28/08/2013. '||t.note where  empcn = ? and dates = ? ";
					dao.getSimpleJdbcTemplate().update(sql, s.getSoic(), s.getNgay());
					isC = 1;

				} else {
					System.out.println("Ko sua hoac ko co gio vao");
				}
			} catch (Exception e) {
				System.out.println("Ko co du lieu ngay nay: gio vao");
			}

			System.out.println("Ngay tiep theo: " + ngayOut);
			// sua gio ra

			try {
				timeOut = dao.getSimpleJdbcTemplate().queryForObject(
						"select t.times from n_data_main t where empcn = ?" + "and dates = ?", String.class,
						s.getSoic(), ngayOut);

				t = ((Integer.valueOf(Out.substring(0, 2)) * 60) + Integer.valueOf(Out.substring(2)));
				int g;
				if (s.getTou() != null)
					g = (Integer.valueOf(s.getTou().substring(0, 2)) * 60) + Integer.valueOf(s.getTou().substring(2));
				else
					g = 0;
				if (g > (t + 15)) {
					String[] times = new String[timeOut.length() / 4];
					int j = 0;
					for (int i = 0; i < timeOut.length(); i += 4) {
						times[j] = timeOut.substring(i, i + 4);
						j++;
					}

					String sq = "";
					for (int i = 0; i < times.length; i++) {
						if (!times[i].equals(s.getTou()))
							sq += times[i];
					}

					int r = (int) (Math.random() * 1000) % 15;
					int ti = (r + (t));
					int h = ti / 60;
					int m = ti % 60;
					s.setTou((h < 10 ? "0" + h : h + "") + (m < 10 ? "0" + m : m));

					System.out.println("gio ra moi: " + s.getTou());

					sq += s.getTou();
					System.out.println(sq);
					String sql = "update n_data_main t set t.times='" + sq + "'," + " t.note='Minh update " + timeOut
							+ " 28/08/2013. '||t.note where  empcn = ? and dates = ? ";
					dao.getSimpleJdbcTemplate().update(sql, s.getSoic(), ngayOut);
					isC = 1;
				} else {
					System.out.println("Ko sua hoac ko co gio ra");
				}
			} catch (Exception e) {
				System.out.println("Ko co du lieu ngay nay: gio ra");
			}

			if (isC == 1) {
				callpro(s.getSothe(), s.getNgay(), "mm");
				System.out.println("<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>");
			}
		}

	}

	public static void suatudongcangay() {
		String timeIn;
		float tcv = 0;
		float tcr = 0;
		String timeInC;
		String timeOutC;
		String In;
		String Out;
		System.out.println("Dang lay danh sach------");
		List<SuaDuLieu> list = getList("07/2013", "TB", Ngay);
		System.out.println("Lay danh sach ok");
		for (SuaDuLieu s : list) {
			int isC = 0;
			int day = getCountdayofmonth(Integer.valueOf(s.getNgay().substring(3, 5)),
					Integer.valueOf(s.getNgay().substring(6)));

			System.out.println(s.getSothe());
			System.out.println(s.getSoic());
			System.out.println(s.getNgay());
			System.out.println("Tca tinh tien: " + s.getTcttien());
			System.out.println("Gio vao - ra: " + s.getTin() + "-" + s.getTou());
			if (s.getTin() == null && s.getTou() == null)
				continue;
			Date dat = new Date(Integer.valueOf(s.getNgay().substring(6)) - 1900,
					Integer.valueOf(s.getNgay().substring(3, 5)) - 1, Integer.valueOf(s.getNgay().substring(0, 2)));
			if ((s.getTin() == null || s.getTou() == null) && dat.getDay() == 0) {
				dao.getSimpleJdbcTemplate().update("delete n_register_shift t " + // tu thang 11/2012 tro di thi xoa
																					// trong _01122012
						"where t.empsn in (?) and to_char(t.shift_date,'dd/mm/yyyy')=?", s.getSothe(), s.getNgay());
				System.out.println("<><><><><><><><><><><><><><><><><><>");
				callpro(s.getSothe(), s.getNgay(), "mm");
				continue;
			}
			String ttc = dao.getSimpleJdbcTemplate().queryForObject(
					"select substr(time_in,0,2)||substr(time_in,4,2)||"
							+ "substr(time_Out,0,2)||substr(time_Out,4,2) from n_shift t where id_shift = ?",
					String.class, s.getCa());
			timeInC = ttc.substring(0, 4);
			timeOutC = ttc.substring(4);
			tcv = getTCVao(s.getTin(), timeInC);
			tcr = getTCRa(s.getTou(), timeOutC);
			System.out.println("tang ca vao - ra: " + tcv + "-" + tcr);
			Float tc = Float.valueOf(s.getTcttien());
			if ((tcv + tcr) > tc) {
				if (tcv == (tcv + tcr - tc))
					tcv = 0;
				else if (tcr == (tcv + tcr - tc))
					tcr = 0;
				else if (tcv > tcr) {
					tcv -= (tcv + tcr - tc);
					if (tcv < 0) {
						tcr += tcv;
						tcv = 0;
					}
				} else {
					tcr -= (tcv + tcr - tc);
					if (tcr < 0) {
						tcv += tcr;
						tcr = 0;
					}
				}
			}
			/*
			 * else if((tcv+tcr)<tc) {//neu thieu tc tinh tien phai lam sao de xac dinh goi
			 * chuan //tcr = tcr + (tcv+tcr- tc); }
			 */
			// tinh gio vao ra chuan cho nguoi nay
			int t = ((Integer.valueOf(timeInC.substring(0, 2)) * 60) + Integer.valueOf(timeInC.substring(2)))
					- (int) (60 * tcv);
			In = (t / 60 < 10 ? "0" + t / 60 : t / 60) + "" + (t % 60 < 10 ? "0" + t % 60 : t % 60);
			System.out.println("Gio vao chuan: " + In);
			t = ((Integer.valueOf(timeOutC.substring(0, 2)) * 60) + Integer.valueOf(timeOutC.substring(2)))
					+ (int) (60 * tcr);
			Out = (t / 60 < 10 ? "0" + t / 60 : t / 60) + "" + (t % 60 < 10 ? "0" + t % 60 : t % 60);
			System.out.println("Gio ra chuan: " + Out);

			// sua gio vao ra

			try {
				timeIn = dao.getSimpleJdbcTemplate().queryForObject(
						"select t.times from n_data_main t where empcn = ?" + "and dates = ?", String.class,
						s.getSoic(), s.getNgay());
				t = ((Integer.valueOf(In.substring(0, 2)) * 60) + Integer.valueOf(In.substring(2)));
				int g;
				if (s.getTin() != null)
					g = (Integer.valueOf(s.getTin().substring(0, 2)) * 60) + Integer.valueOf(s.getTin().substring(2));
				else
					g = t - 16;

				int tr = ((Integer.valueOf(Out.substring(0, 2)) * 60) + Integer.valueOf(Out.substring(2)));
				int gr;
				if (s.getTou() != null)
					gr = (Integer.valueOf(s.getTou().substring(0, 2)) * 60) + Integer.valueOf(s.getTou().substring(2));
				else
					gr = tr + 16;
				if ((g < (t - 15) || gr > (tr + 15)) && timeIn != null)// neu bi dayoff do mat du lieu vao vi chay bang
																		// ham excel thi bo dieu kien != null di
				{
					isC = 1;
					String[] times = new String[timeIn.length() / 4];
					int j = 0;
					for (int i = 0; i < timeIn.length(); i += 4) {
						times[j] = timeIn.substring(i, i + 4);
						j++;
					}
					String sq = "";
					for (int i = 0; i < times.length; i++) {
						if (!times[i].equals(s.getTin()) && !times[i].equals(s.getTou()))
							sq += times[i];
					}
					int r = (int) (Math.random() * 1000) % 15;
					int ti, h, m;
					if (g < (t - 15)) {
						ti = (r + (t - 15));
						h = ti / 60;
						m = ti % 60;
						s.setTin((h < 10 ? "0" + h : h + "") + (m < 10 ? "0" + m : m));
						System.out.println("gio vao moi: " + s.getTin());
					}
					if (gr > (tr + 15)) {
						ti = (r + (tr));
						h = ti / 60;
						m = ti % 60;
						s.setTou((h < 10 ? "0" + h : h + "") + (m < 10 ? "0" + m : m));
						System.out.println("gio ra moi: " + s.getTou());
					}

					sq = sq + s.getTin() + s.getTou();
					System.out.println(sq);
					String sql = "update n_data_main t set t.times='" + sq + "'," + " t.note='Minh update " + timeIn
							+ " 28/08/2013. '||t.note where  empcn = ? and dates = ? ";
					dao.getSimpleJdbcTemplate().update(sql, s.getSoic(), s.getNgay());

				} else {
					System.out.println("Ko sua hoac ko co dl");
				}
			} catch (Exception e) {
				System.out.println("Ko co du lieu ngay nay");
			}
			if (isC == 1) {
				callpro(s.getSothe(), s.getNgay(), "mm");
				System.out.println("<><><><><><><><><><><><><><><><><><>");
			}
		}

	}

	public static float getTCVao(String vao, String vaoc) {
		try {

			int ti1 = ((Integer.valueOf(vaoc.substring(0, 2)) * 60) + Integer.valueOf(vaoc.substring(2)));
			int ti2 = ((Integer.valueOf(vao.substring(0, 2)) * 60) + Integer.valueOf(vao.substring(2)));
			int ti = ti1 - ti2;
			if (ti % 30 >= 28)
				ti += 2;
			int tii = ti / 30;
			if (tii < 0)
				return 0;
			return (tii) / 2.0f;
		} catch (Exception e) {
			System.out.println("ORR KO CO GIO VAO!");
			return 0;
		}
	}

	public static float getTCRa(String ra, String rac) {
		try {
			int ti1 = ((Integer.valueOf(ra.substring(0, 2)) * 60) + Integer.valueOf(ra.substring(2)));
			int ti2 = ((Integer.valueOf(rac.substring(0, 2)) * 60) + Integer.valueOf(rac.substring(2)));
			int ti = ti1 - ti2;
			if (ti % 30 >= 28)
				ti += 2;
			int tii = ti / 30;
			if (tii < 0)
				return 0;
			return (tii) / 2.0f;
		} catch (Exception e) {
			System.out.println("ORR KO CO GIO RA!");
			return 0;
		}
		/*
		 * int ti = Integer.valueOf(ra)-Integer.valueOf(rac); int
		 * tii=(ti-(ti/100)*40)/30; return (tii)/2.0f;
		 */
	}

	public static void suadulieubimatCANGAY()// chi ca ngay cua thang 11/2012(FVLS)//1,so the, 2,so ic , 3,ngay, 4
												// giovaora
	{
		String sothe;
		String soic;
		String ngay;
		String time;
		int dem;

		//
		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance", "test.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int startRow = 1;
		row = sheet.getRow(startRow);
		cell = row.getCell(0);

		System.out.println("So row cua file xls: " + sheet.getLastRowNum());
		for (startRow = 1; startRow <= sheet.getLastRowNum(); startRow++) {
			int u = 0;
			row = sheet.getRow(startRow);
			cell = row.getCell(0);
			sothe = cell.toString();
			cell = row.getCell(1);
			soic = cell.toString();
			cell = row.getCell(2);
			ngay = cell.toString();
			int day = getCountdayofmonth(Integer.valueOf(ngay.substring(3, 5)), Integer.valueOf(ngay.substring(6)));
			cell = row.getCell(3);
			String InOut = cell.toString();

			System.out.println(sothe);
			System.out.println(soic);
			System.out.println(ngay);
			System.out.println("Gio vao - ra dung: " + InOut);

			// sua gio vao
			try {

				dem = dao.getSimpleJdbcTemplate().queryForInt(
						"select count(times) from n_data_main t where empcn = ?" + "and dates = ?", soic, ngay);
				if (dem == 0) {
					u = dao.getSimpleJdbcTemplate()
							.update("insert into n_data_main t values"
									+ "(?,?,?,'','Minh insert gio vao-ra do mat du lieu 28/08/2013.','')", soic, ngay,
									InOut);
				} else {
					u = dao.getSimpleJdbcTemplate().update("update n_data_main t set times = ?, "
							+ "note = 'Minh up gio vao-ra do mat du lieu '||times||' 28/08/2013.'||note where empcn=? and dates = ?",
							InOut, soic, ngay);
				}

			} catch (Exception e) {
				System.out.println("loi gio ");
			}
			System.out.println("---- " + u + " ----");

			callpro(sothe, ngay, "mm");
		}

	}

	public static void suadulieubimat()// chi ca dem cua thang 11/2012(FVLS)
	{
		String sothe;
		String soic;
		String ngay;
		String time;
		int dem;

		//
		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance", "test.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int startRow = 1;
		row = sheet.getRow(startRow);
		cell = row.getCell(0);

		System.out.println("So row cua file xls: " + sheet.getLastRowNum());
		for (startRow = 1; startRow <= sheet.getLastRowNum(); startRow++) {
			int u = 0;
			row = sheet.getRow(startRow);
			cell = row.getCell(0);
			sothe = cell.toString();
			cell = row.getCell(1);
			soic = cell.toString();
			cell = row.getCell(2);
			ngay = cell.toString();
			int day = getCountdayofmonth(Integer.valueOf(ngay.substring(3, 5)), Integer.valueOf(ngay.substring(6)));
			cell = row.getCell(3);
			String In = cell.toString();
			cell = row.getCell(4);
			String Out = cell.toString();

			System.out.println(sothe);
			System.out.println(soic);
			System.out.println(ngay);
			System.out.println("Gio vao - ra dung: " + In + "-" + Out);

			// sua gio vao
			try {// bao lai de goi pro

				dem = dao.getSimpleJdbcTemplate().queryForInt(
						"select count(times) from n_data_main t where empcn = ?" + "and dates = ?", soic, ngay);
				if (dem == 0) {
					u = dao.getSimpleJdbcTemplate().update("insert into n_data_main t values"
							+ "(?,?,?,'','Minh insert gio vao do mat du lieu 28/08/2013.','')", soic, ngay, In);
				} else {
					u = dao.getSimpleJdbcTemplate().update("update n_data_main t set times = times||?, "
							+ "note = 'Minh up gio va do mat du lieu '||times||' 28/08/2013.'||note where empcn=? and dates = ?",
							In, soic, ngay);
				}

			} catch (Exception e) {
				System.out.println("loi gio vao");
			}
			System.out.println("---- " + u + " ----");

			// sua gio ra
			Date dat = new Date(Integer.valueOf(ngay.substring(6)) - 1900, Integer.valueOf(ngay.substring(3, 5)) - 1,
					Integer.valueOf(ngay.substring(0, 2)));
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar ca = Calendar.getInstance();
			ca.setTime(dat);
			ca.add(Calendar.DATE, 1);
			ngay = sdf.format(ca.getTime());
			u = 0;

			System.out.println("ngay ke tiep " + ngay);
			try {

				dem = dao.getSimpleJdbcTemplate().queryForInt(
						"select count(times) from n_data_main t where empcn = ?" + "and dates = ?", soic, ngay);
				if (dem == 0) {
					u = dao.getSimpleJdbcTemplate().update("insert into n_data_main t values"
							+ "(?,?,?,'','Minh insert gio ra do mat du lieu 28/08/2013.','')", soic, ngay, Out);
				} else {
					u = dao.getSimpleJdbcTemplate().update("update n_data_main t set times = times||?, "
							+ "note = 'Minh up ra va do mat du lieu '||times||' 28/08/2013.'||note where empcn=? and dates = ?",
							Out, soic, ngay);
				}

			} catch (Exception e) {
				System.out.println("loi gio ra");
			}
			System.out.println("---- " + u + " ----");

			callpro(sothe, ngay, "mm");
		}

	}

	public static void suadlktcangay() {/// ko lam dc dung cau lenh excel de up tung dong ="select '"&M12&"' as
										/// tin,'"&N12& "' as tou, t.*, rowid from n_lost_data_detail t where empsn
										/// ='"&B12&"' and date_lost = to_date('"&D12&"', 'dd/mm/yyyy') union"

		String sothe;
		String soic;
		String ngay;
		String time;

		// sua du lieu quet the ca dem
		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance", "test.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int startRow = 1;
		row = sheet.getRow(startRow);
		cell = row.getCell(0);

		System.out.println("So row cua file xls: " + sheet.getLastRowNum());
		for (startRow = 1; startRow <= sheet.getLastRowNum(); startRow++) {
			row = sheet.getRow(startRow);
			cell = row.getCell(0);
			sothe = cell.toString();
			cell = row.getCell(1);
			soic = cell.toString();
			cell = row.getCell(2);
			ngay = cell.toString();
			int day = getCountdayofmonth(Integer.valueOf(ngay.substring(3, 5)), Integer.valueOf(ngay.substring(6)));
			cell = row.getCell(3);
			String In = cell.toString();
			cell = row.getCell(4);
			String Out = cell.toString();

			System.out.println(sothe);
			System.out.println(soic);
			System.out.println(ngay);
			System.out.println("Gio vao - ra dung: " + In + "-" + Out);

			try {

				time = dao.getSimpleJdbcTemplate()
						.queryForObject("select t.*, t.rowid from n_lost_data_detail t where empsn = ? "
								+ "and date_lost = to_date(?, 'dd/mm/yyyy')", String.class, sothe, ngay);

				String[] times = new String[time.length() / 4];
				int j = 0;
				for (int i = 0; i < time.length(); i += 4) {
					times[j] = time.substring(i, i + 4);
					j++;
				}
				String timeIn = TimMax(times);
				String timeOut = TimMin(times);

				String ti = timeIn.substring(0, 2);
				String to = timeOut.substring(0, 2);
				if ((ti.equals("22") || ti.equals("20") || ti.equals("21") || ti.equals("19") || ti.equals("18"))
						&& (to.equals("04") || to.equals("05") || to.equals("08") || to.equals("06")
								|| to.equals("07"))) {
					String sql = "update n_data_main t set t.times='" + In + "'||substr(t.times,length(t.times)-3,4),"
							+ " t.note='Minh update 28/08/2013. '||t.note where  empcn = ? and dates = ?";
					dao.getSimpleJdbcTemplate().update(sql, soic, ngay);
				} else if ((ti.equals("22") || ti.equals("20") || ti.equals("21") || ti.equals("19") || ti.equals("18"))
						&& (ti.equals("22") || ti.equals("21") || to.equals("20") || to.equals("19")
								|| to.equals("18"))) {
					String sql = "update n_data_main t set t.times='" + In + "',"
							+ " t.note='Minh update 28/08/2013. '||t.note where  empcn = ? and dates = ? ";
					dao.getSimpleJdbcTemplate().update(sql, soic, ngay);
				} else {
					System.out.println("ko co du lieu gio vao");
				}
			} catch (Exception e) {
				System.out.println("ko co du lieu gio vao");
			}

			callpro(sothe, ngay, "mm");
		}

	}

	public static void suacangay() {
		String sothe;
		String soic;
		String ngay;
		String time;

		// sua du lieu quet the ca dem /// so the - so ic - ngay - t_in - t_mid - t_out
		// - t_over
		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance", "test.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int startRow = 1;
		row = sheet.getRow(startRow);
		cell = row.getCell(0);
		System.out.println("So row cua file xls: " + sheet.getLastRowNum());
		for (startRow = 1; startRow <= sheet.getLastRowNum(); startRow++) {
			row = sheet.getRow(startRow);
			cell = row.getCell(0);
			sothe = cell.toString();
			cell = row.getCell(1);
			soic = cell.toString();
			cell = row.getCell(2);
			ngay = cell.toString();
			int day = getCountdayofmonth(Integer.valueOf(ngay.substring(3, 5)), Integer.valueOf(ngay.substring(6)));
			cell = row.getCell(3);
			String In = cell.toString();
			cell = row.getCell(4);
			String Mid = cell.toString();
			cell = row.getCell(5);
			String Out = cell.toString();
			cell = row.getCell(6);
			String Over = cell.toString();

			System.out.println(sothe);
			System.out.println(soic);
			System.out.println(ngay);
			System.out.println("Gio vao - ra dung: " + In + "-" + Out);

			// sua gio vao
			try {

				time = dao.getSimpleJdbcTemplate().queryForObject(
						"select t.times from n_data_main t where empcn = ?" + "and dates = ?", String.class, soic,
						ngay);
				@SuppressWarnings("unchecked")
				String stemp[] = ((String) dao.getSimpleJdbcTemplate()
						.queryForObject("select  substr(T_in,1,2)||substr(T_in,4,2) as t_in"
								+ ",substr(T_mid,1,2)||substr(T_mid,4,2) as t_mid,substr(T_out,1,2)||substr(T_out,4,2) as t_out"
								+ ",substr(T_over,1,2)||substr(T_over,4,2) as t_over  from n_data_daily t where empsn = ? "
								+ "and dates = to_date(?, 'dd/mm/yyyy')", new ParameterizedRowMapper() {

									@Override
									public String mapRow(ResultSet rs, int arg1) throws SQLException {
										return rs.getString("T_IN") + "%" + rs.getString("T_MID") + "%"
												+ rs.getString("T_OUT") + "%" + rs.getString("T_OVER");
									}
								}, sothe, ngay)).split("%");

				String[] times = new String[time.length() / 4];
				int j = 0;
				for (int i = 0; i < time.length(); i += 4) {
					times[j] = time.substring(i, i + 4);
					j++;
				}
				String sq = "";
				for (int i = 0; i < times.length; i++) {
					if (!times[i].equals(stemp[0]) && !times[i].equals(stemp[1]) && !times[i].equals(stemp[2])
							&& !times[i].equals(stemp[3]))
						sq += times[i];
				}
				sq += In + Mid + Out + Over;
				System.out.println(sq);
				String sql = "update n_data_main t set t.times='" + sq + "'," + " t.note='Minh update " + time
						+ " 28/08/2013. '||t.note where  empcn = ? and dates = ? ";
				dao.getSimpleJdbcTemplate().update(sql, soic, ngay);

			} catch (Exception e) {
				System.out.println("ko co du lieu gio vao");
			}

			callpro(sothe, ngay, "mm");
		}

	}

	public static void suacadem() {
		String sothe;
		String soic;
		String ngay;
		String time;

		// sua du lieu quet the ca dem
		HSSFWorkbook wb = ReportUtils.loadTemplate("insurance", "test.xls");
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int startRow = 1;
		row = sheet.getRow(startRow);
		cell = row.getCell(0);
		System.out.println("So row cua file xls: " + sheet.getLastRowNum());
		for (startRow = 1; startRow <= sheet.getLastRowNum(); startRow++) {
			row = sheet.getRow(startRow);
			cell = row.getCell(0);
			sothe = cell.toString();
			cell = row.getCell(1);
			soic = cell.toString();
			cell = row.getCell(2);
			ngay = cell.toString();
			int day = getCountdayofmonth(Integer.valueOf(ngay.substring(3, 5)), Integer.valueOf(ngay.substring(6)));
			cell = row.getCell(3);
			String In = cell.toString();
			cell = row.getCell(4);
			String Mid = cell.toString();
			cell = row.getCell(5);
			String Out = cell.toString();
			cell = row.getCell(6);
			String Over = cell.toString();

			System.out.println(sothe);
			System.out.println(soic);
			System.out.println(ngay);
			System.out.println("Gio vao - ra dung: " + In + "-" + Out);
			String stemp[] = ((String) dao.getSimpleJdbcTemplate()
					.queryForObject("select  substr(T_in,1,2)||substr(T_in,4,2) as t_in"
							+ ",substr(T_mid,1,2)||substr(T_mid,4,2) as t_mid,substr(T_out,1,2)||substr(T_out,4,2) as t_out"
							+ ",substr(T_over,1,2)||substr(T_over,4,2) as t_over  from n_data_daily t where empsn = ? "
							+ "and dates = to_date(?, 'dd/mm/yyyy')", new ParameterizedRowMapper() {

								@Override
								public String mapRow(ResultSet rs, int arg1) throws SQLException {
									return rs.getString("T_IN") + "%" + rs.getString("T_MID") + "%"
											+ rs.getString("T_OUT") + "%" + rs.getString("T_OVER");
								}
							}, sothe, ngay)).split("%");
			// sua gio vao
			try {

				time = dao.getSimpleJdbcTemplate().queryForObject(
						"select t.times from n_data_main t where empcn = ?" + "and dates = ?", String.class, soic,
						ngay);
				String[] times = new String[time.length() / 4];
				int j = 0;

				for (int i = 0; i < time.length(); i += 4) {
					times[j] = time.substring(i, i + 4);
					j++;
				}
				String sq = "";
				for (int i = 0; i < times.length; i++) {
					if (!times[i].equals(stemp[0]))
						sq += times[i];
				}
				sq += In;
				System.out.println(sq);
				String sql = "update n_data_main t set t.times='" + sq + "'," + " t.note='Minh update " + time
						+ " 28/08/2013. '||t.note where  empcn = ? and dates = ? ";
				dao.getSimpleJdbcTemplate().update(sql, soic, ngay);

			} catch (Exception e) {
				System.out.println("ko co du lieu gio vao");
			}

			// sua gio ra
			Date dat = new Date(Integer.valueOf(ngay.substring(6)) - 1900, Integer.valueOf(ngay.substring(3, 5)) - 1,
					Integer.valueOf(ngay.substring(0, 2)));
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar ca = Calendar.getInstance();
			ca.setTime(dat);
			ca.add(Calendar.DATE, 1);
			ngay = sdf.format(ca.getTime());

			System.out.println("ngay ke tiep " + ngay);
			try {

				time = dao.getSimpleJdbcTemplate().queryForObject(
						"select t.times from n_data_main t where empcn = ?" + "and dates = ?", String.class, soic,
						ngay);
				String[] times = new String[time.length() / 4];
				int j = 0;

				for (int i = 0; i < time.length(); i += 4) {
					times[j] = time.substring(i, i + 4);
					j++;
				}
				String sq = "";
				for (int i = 0; i < times.length; i++) {
					if (!times[i].equals(stemp[1]) && !times[i].equals(stemp[2]) && !times[i].equals(stemp[3]))
						sq += times[i];
				}
				sq += Mid + Out + Over;
				System.out.println(sq);
				String sql = "update n_data_main t set t.times='" + sq + "'," + " t.note='Minh update " + time
						+ " 28/08/2013. '||t.note where  empcn = ? and dates = ? ";
				dao.getSimpleJdbcTemplate().update(sql, soic, ngay);
			} catch (Exception e) {
				System.out.println("ko co du lieu gio ra");
			}

			callpro(sothe, ngay, "mm");
		}

	}

	public static List<SuaDuLieu> getList(String thang, String fact, int stt) {// thang = "01/2013"
		String sql = "select a.empsn, (select distinct c.empcn from n_emp_iccard c "
				+ "WHERE c.EMPSN=a.empsn AND c.BEGIN_DATE=(SELECT Max(d.BEGIN_DATE) FROM N_EMP_ICCARD d "
				+ "WHERE d.BEGIN_DATE<=a.dates AND d.EMPSN=c.EMPSN)) empcn "
				+ ",to_char(a.dates,'dd/mm/yyyy') as dates,a.id_shift,substr(a.t_in,0,2)||substr(a.t_in,4,2) as t_in,"
				+ "substr(a.t_mid,0,2)||substr(a.t_mid,4,2) as t_mid,"
				+ "substr(a.t_out,0,2)||substr(a.t_out,4,2) as t_out,"
				+ "substr(a.t_over,0,2)||substr(a.t_over,4,2) as t_over " + ",A.OTD+A.OTN+A.OTS+A.OTH TCA_TTIEN "
				+ ",a.real_ot*60 as TC_QUETTHE,a.reg_ot as TC_DANGKY,a.note "
				+ "from n_data_daily/*_bk052013*/ a,n_department b /*tu thang 5 tro di lay trong hien hanh */"
				+ "where to_char(a.dates,'mm/yyyy')=? "
				+ "and b.id_dept=a.depsn and b.name_fact = ? and a.note not like '%Phep:1%' "
		// + " and a.empsn in ('00060344')"
		;// test 1 dua
		if (stt == Dem)
			sql += " AND A.ID_SHIFT IN (SELECT E.ID_SHIFT FROM N_SHIFT E WHERE E.ID_SHIFT=A.ID_SHIFT AND E.NOTE LIKE 'CA 3%')--ca dem ";// lt1
																																		// like
																																		// 'CA
																																		// 3%'
		else

			sql += " AND A.ID_SHIFT not IN (SELECT E.ID_SHIFT FROM N_SHIFT E WHERE E.ID_SHIFT=A.ID_SHIFT AND E.NOTE LIKE 'CA 3%')-- ca ngay";
		List<SuaDuLieu> list = dao.getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<SuaDuLieu>() {
			@Override
			public SuaDuLieu mapRow(ResultSet rs, int arg1) throws SQLException {
				SuaDuLieu s = new SuaDuLieu();
				s.setSothe(rs.getString("empsn"));
				s.setSoic(rs.getString("empcn"));
				s.setNgay(rs.getString("dates"));
				s.setCa(rs.getString("id_shift"));
				s.setTcttien(rs.getString("TCA_TTIEN"));
				if (rs.getString("t_over") != null)
					s.setTou(rs.getString("t_over"));
				else if (rs.getString("t_out") != null)
					s.setTou(rs.getString("t_out"));
				else

					s.setTou(rs.getString("t_mid"));
				s.setTin(rs.getString("t_in"));
				return s;
			}
		}, thang, fact);
		return list;
	}

	public static int getCountdayofmonth(int thang, int nam) {
		int day = 0;
		if (thang == 1 || thang == 3 || thang == 5 || thang == 7 || thang == 8 || thang == 10 || thang == 12)
			day = 31;
		else if (thang == 2)
			if (nam % 4 == 0)
				day = 29;
			else
				day = 28;
		else
			day = 30;

		return day;
	}

	public static void callpro(final String sothe, final String thang, final String tt) {
		List<Object> s = new ArrayList<Object>();
		dao.getJdbcTemplate().call(new CallableStatementCreator() {
			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {// dung cho thang 10
																									// va 11 nam 2012
																									// 'C1_CAL_EMPIC_DATA_a_112012';
				// dung cho thang 12/2012 tro di'C1_CAL_EMPIC_DATA_a';
				// CallableStatement cs = con.prepareCall("{ call c1_day_process_a_112012(?,?)
				// }");
				CallableStatement cs = con.prepareCall("{ call C1_CAL_EMPIC_DATA_a(?,?,?) }");
				cs.setString(1, sothe);
				cs.setString(2, thang);
				cs.setString(3, tt);
				return cs;
			}
		}, s);
	}

	public static String TimMax(String[] s) {
		int in = 0;
		int Max = Integer.valueOf(s[0]);
		for (int i = 0; i < s.length; i++) {
			if (Max < Integer.valueOf(s[i])) {
				Max = Integer.valueOf(s[i]);
				in = i;
			}
		}
		System.out.println("Time In: " + s[in]);
		return s[in];
	}

	public static String TimMin(String[] s) {
		int in = 0;
		int Min = Integer.valueOf(s[0]);
		for (int i = 0; i < s.length; i++) {
			if (Min > Integer.valueOf(s[i])) {
				Min = Integer.valueOf(s[i]);
				in = i;
			}
		}
		System.out.println("Time Out: " + s[in]);
		return s[in];
	}

	public static String replaceFormulaRow(String formula, int newRow) {
		Pattern p = Pattern.compile("([A-Z]+)([1-9]+[0-9]*)");
		Pattern p1 = Pattern.compile("\\d+");
		Matcher m = p.matcher(formula);
		Matcher m1;
		while (m.find()) {
			String group = m.group();
			m1 = p1.matcher(group);
			if (m1.find()) {
				String g1 = m1.group();
				String s1 = group.replace(g1, newRow + "");
				formula = formula.replace(group, s1);
			}
		}
		return formula;
	}

	public static void copyRowM(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow sourceRowq, int desRowNum) {
		HSSFRow newRow = sheet.createRow(desRowNum);
		HSSFRow sourceRow = sheet.getRow(desRowNum - 1);
		// System.out.println(sourceRow.getLastCellNum());
		for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
			System.out.println(i);
			if (i == 11)
				System.out.println();
			// Grab a copy of the old/new cell
			HSSFCell oldCell = sourceRow.getCell(i);
			HSSFCell newCell = newRow.createCell(i);

			// If the old cell is null jump to next cell
			if (oldCell == null) {
				newCell = null;
				continue;
			}
			// Copy style from old cell and apply to new cell
			HSSFCellStyle newCellStyle = wb.createCellStyle();
			HSSFCell ce = sourceRowq.getCell(i);
			HSSFCellStyle s = ce.getCellStyle();
			// newCellStyle.cloneStyleFrom(s);

			newCell.setCellStyle(s);// newCellStyle);

			// If there is a cell comment, copy
			if (oldCell.getCellComment() != null) {
				newCell.setCellComment(oldCell.getCellComment());
			}

			// If there is a cell hyperlink, copy
			if (oldCell.getHyperlink() != null) {
				newCell.setHyperlink(oldCell.getHyperlink());
			}

			// Set the cell data type
			newCell.setCellType(oldCell.getCellType());

			// Set the cell data value
			switch (oldCell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				// newCell.setCellValue(oldCell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				newCell.setCellValue(oldCell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				// newCell.setCellErrorValue(oldCell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				String formula = oldCell.getCellFormula();
				formula = replaceFormulaRow(formula, (desRowNum + 1));
				newCell.setCellFormula(formula);//
				break;
			case Cell.CELL_TYPE_NUMERIC:
				newCell.setCellValue(oldCell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				newCell.setCellValue(oldCell.getRichStringCellValue());
				break;
			}
		}
	}

}
