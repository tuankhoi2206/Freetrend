package ds.program.fvhr.ui.quitworksalary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.services.validator.AttException;
import ds.program.fvhr.ui.quitworksalary.task.AttQuitExcelData;
import ds.program.fvhr.ui.quitworksalary.validator.AttQuitValidator;
import fv.util.HSSFUtils;

public class AttQuitWorkbook {

	private List<String> header;

	private HSSFWorkbook wb;

	private String month;

	private String year;

	private String message;

	private Integer[] range;

	private AttQuitExcelData data;
	
	private AttQuitValidator validator;


	public AttQuitWorkbook(File file, AttQuitValidator validator, String month, String year)
			throws IOException, AttException {
		this.validator=validator;
		this.month = month;
		this.year = year;
		loadWorkbook(file);
	}

	public HSSFWorkbook getWorkbook() {
		return wb;
	}

	public Integer[] getRange() {
		return range;
	}

	public List<String> getHeader() {
		return header;
	}

	private void loadWorkbook(File file) throws IOException, AttException {
		InputStream in = new FileInputStream(file);
		// load wb from file
		range = new Integer[2];
		wb = new HSSFWorkbook(in);
		// validate wb
		message = validate(wb);
		if (!message.equals("valid")) {
			in.close();
			throw new AttException(message);
		}
		
		// get header row
		header = getExcelHeader(wb);
		in.close();
	}

	private List<String> getExcelHeader(HSSFWorkbook wb) throws AttException {
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int headerRow = -1;
		int startDataRow = range[0];
		for (int i = 0; i < startDataRow; i++) {
			row = sheet.getRow(i);// try row 1
			cell = row.getCell(0);
			String cellVal = HSSFUtils.getStringCellValue(cell, false).trim();
			if (cellVal.equalsIgnoreCase("empsn")) {
				headerRow = i;
				break;
			}
		}

		if (headerRow >= 0) {
			row = sheet.getRow(headerRow);
			List<String> list = new ArrayList<String>();
			int blankDetect=0;
			for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
				cell = row.getCell(i);
				String val = HSSFUtils.getStringCellValue(cell, true).trim()
						.toUpperCase();
				if (!val.equals("")) {
					if (blankDetect==0)
						list.add(val);
					else throw new AttException("Tiêu đề excel bị lỗi");
				} else {
					blankDetect++;
					if (blankDetect>1)
						throw new AttException("Tiêu đề excel bị lỗi");
				}
			}
			return list;
		} else {
			throw new AttException("Không tìm thấy dòng tiêu đề");
		}
	}
	
	private Map<String, Integer> getExcelHeaderMap(HSSFWorkbook wb) throws AttException{
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int headerRow = -1;
		int startDataRow = range[0];
		for (int i = 0; i < startDataRow; i++) {
			row = sheet.getRow(i);// try row 1
			cell = row.getCell(0);
			String cellVal = HSSFUtils.getStringCellValue(cell, false).trim();
			if (cellVal.equalsIgnoreCase("empsn")) {
				headerRow = i;
				break;
			}
		}

		if (headerRow >= 0) {
			row = sheet.getRow(headerRow);
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
				cell = row.getCell(i);
				String val = HSSFUtils.getStringCellValue(cell, true).trim()
						.toUpperCase();
				if (!val.equals("")){
					map.put(val, i);
				}
			}
			return map;
		} else {
			throw new AttException("Không tìm thấy dòng tiêu đề");
		}
		//TODO do more flexible
	}

	public String getStringValue(int rowIndex, String columnName) {
		int index = header.indexOf(columnName);
		if (index >= 0) {
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = sheet.getRow(rowIndex);
			HSSFCell cell = row.getCell(index);
			return HSSFUtils.getStringCellValue(cell, true);
		} else {
			return null;
		}
	}

	public String getStringValue(int rowIndex, int columnIndex) {
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(rowIndex);
		HSSFCell cell = row.getCell(columnIndex);
		return HSSFUtils.getStringCellValue(cell, true);
	}

	public BigDecimal getNumericValue(int rowIndex, String columnName) {
		int index = header.indexOf(columnName);
		if (index >= 0) {
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = sheet.getRow(rowIndex);
			HSSFCell cell = row.getCell(index);
			return new BigDecimal(HSSFUtils.getNumericCellValue(wb, cell, true));
		} else {
			return BigDecimal.ZERO;
		}
	}

	public Double getNumericValue(int rowIndex, int columnIndex) {
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(rowIndex);
		HSSFCell cell = row.getCell(columnIndex);
		return HSSFUtils.getNumericCellValue(wb, cell, true);
	}

	public Object getValue(int rowIndex, String columnName) {
		int index = header.indexOf(columnName);
		if (index >= 0) {
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = sheet.getRow(rowIndex);
			HSSFCell cell = row.getCell(index);
			if (columnName.equalsIgnoreCase("empsn")
					|| columnName.equalsIgnoreCase("note_bh")) {
				return HSSFUtils.getStringCellValue(cell, true);
			} else if (columnName.toLowerCase().contains("date")
					|| columnName.equalsIgnoreCase("dot_tv")) {
				return HSSFUtils.getDateCellValue(cell);
			} else {
				return HSSFUtils.getNumericCellValue(wb, cell, true);
			}
		} else {
			return null;
		}
	}

	/**
	 * Special field
	 * 
	 * @param rowIndex
	 * @return
	 */
	public String getNoteBh(int rowIndex) {
		int index = header.indexOf("NOTE");
		if (index == -1)
			index = header.indexOf("NOTE_BH");
		if (index >= 0) {
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = sheet.getRow(rowIndex);
			HSSFCell cell = row.getCell(index);
			return HSSFUtils.getStringCellValue(cell, true);
		} else {
			return null;
		}
	}
	
	public BigDecimal getBonus4(int rowIndex){
		//BONUS4 (200K/260K)
		int index = header.indexOf("BONUS4");
		if (index == -1)
			index = header.indexOf("BONUS4 (200K/260K)");
		if (index == -1)
			index = header.indexOf("BONUS4(200K/260K)");
		if (index >= 0) {
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = sheet.getRow(rowIndex);
			HSSFCell cell = row.getCell(index);
			return new BigDecimal(HSSFUtils.getNumericCellValue(wb, cell, true));
		} else {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * Special field
	 * 
	 * @param rowIndex
	 * @return
	 */
	public String getDeptKt(int rowIndex) {
		int index = header.indexOf("DEP_KT");
		if (index == -1)
			index = header.indexOf("DEPT_KT");
		if (index >= 0) {
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = sheet.getRow(rowIndex);
			HSSFCell cell = row.getCell(index);
			return HSSFUtils.getStringCellValue(cell, true);
		} else {
			return null;
		}
	}
	
	public synchronized String validate(HSSFWorkbook wb) {
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		int rowCount = sheet.getPhysicalNumberOfRows();
		// Detect start row which match empsn pattern
		range[0] = 0;
		while (range[0] < rowCount) {
			row = sheet.getRow(range[0]);
			if (row == null)
				continue;// ///////////allow null row in header area
			cell = row.getCell(0);
			String celVal=HSSFUtils.getStringCellValue(cell, true);
			if (celVal.matches("[0-9]{8}")) {
				break;
			}
			range[0]++;
		}
		range[1] = range[0];// logical
		List<String> list = new ArrayList<String>();
		for (int i = range[0]; i < rowCount; i++) {
			row = sheet.getRow(i);
			if (row == null)
				break;// //do not allow null row in data area
			cell = row.getCell(0);// empsn
			String value;
			if (cell != null) {
				value = HSSFUtils.getStringCellValue(cell, true);
				if (value.matches("[0-9]{8}")) {// break ?
					list.add(value);
					range[1]++;
				} else {
					// if (!value.equals("*")&&!value.equals(""))
					// list.add("row " + (i+1));
					break;
				}
			}
		}
		if (range[0] == range[1]) {
			return "Không có dữ liệu";
		}
		StringBuilder sb = new StringBuilder("");
		for (int i=range[0];i<range[1];i++){
			row = sheet.getRow(i);
			cell = row.getCell(0);// empsn
			String empsn = HSSFUtils.getStringCellValue(cell, true);
			if (!validator.isQuitStatusValid(empsn, month, year)){
				sb.append(empsn + ">Nhập nghỉ việc và xử lý ngày công tháng " + month + " năm " + year).append("<br/>");
			}
		}
		if (!sb.toString().equals(""))
			return sb.toString();
		else
			return "valid";
	}

	public synchronized AttQuitExcelData getData(int rowIndex) {
		data = AttQuitExcelData.getInstanse();
		data.setSoThe(getStringValue(rowIndex, "EMPSN"));
		data.setLuongCoBan(getNumericValue(rowIndex, "BSALY"));
		data.setDonVi(getDeptKt(rowIndex));
		data.setLuongHopDong(getNumericValue(rowIndex, "COMBSALY"));
		data.setTienThuong(getNumericValue(rowIndex, "BONUS1"));
		data.setPhuCapChucVu(getNumericValue(rowIndex, "BONUS2"));
		data.setPhuCapCongViec(getNumericValue(rowIndex, "BONUS3"));
		data.setPhuCapSinhHoat(getNumericValue(rowIndex, "BONUS4"));
		data.setBuLuongThangTruoc(getNumericValue(rowIndex, "BONUS5"));
		data.setPhuCapKhac(getNumericValue(rowIndex, "BONUS8"));
		data.setTamUng(getNumericValue(rowIndex, "BORM"));
		data.setKhauTruKhac(getNumericValue(rowIndex, "KQT"));
		data.setPhiCongDoan(getNumericValue(rowIndex, "JOINLUM"));
		data.setBaoHiemYT(getNumericValue(rowIndex, "YLBX"));
		data.setBaoHiemXH(getNumericValue(rowIndex, "JOININSU"));
		data.setBaoHiemTN(getNumericValue(rowIndex, "BH_TNGHIEP"));
		data.setBuBH19(getNumericValue(rowIndex, "BONUS9"));
		data.setLuongNuaThangDau(getNumericValue(rowIndex, "TEMP1"));
		data.setLuongNuaThangCuoi(getNumericValue(rowIndex, "TEMP2"));
		data.setSoNguoiPhuThuoc(getNumericValue(rowIndex, "BAC"));
		data.setLuongCBBinhQuan(getNumericValue(rowIndex, "BSALY_AVG"));
		data.setPhuCapChucVuBinhQuan(getNumericValue(rowIndex, "BONUS2_AVG"));
		data.setSoThangDenBu(getNumericValue(rowIndex, "M_DENBU"));
		data.setPhepNamConLai(getNumericValue(rowIndex, "PN_CONLAI"));
		data.setSoThangTroCap(getNumericValue(rowIndex, "M_TROCAP"));
		data.setNghiTruocThoiHan(getNumericValue(rowIndex, "D_NGHITRUOC"));
		data.setBuBaoHiem(getNumericValue(rowIndex, "BU_BH"));
		data.setThuBaoHiem(getNumericValue(rowIndex, "THU_BH"));
		data.setBuThuBaoHiem(getNumericValue(rowIndex, "BUTHU_BH"));
		data.setGhiChu(getNoteBh(rowIndex));
		data.setTangCaNgayNgoai(getNumericValue(rowIndex, "ADDCLS1_O"));
		data.setTangCaDemNgoai(getNumericValue(rowIndex, "NADDCLS_O"));
		data.setTangCaChuNhatNgoai(getNumericValue(rowIndex, "ADDHOL_O"));
		data.setTangCaLeNgoai(getNumericValue(rowIndex, "ADDHOLN_O"));
		data.setSoDemTienComNgoai(getNumericValue(rowIndex, "ACNM_O"));
		data.setDepsnBHYT(getStringValue(rowIndex, "DEPSN_BHYT"));
		return data;
	}
}
