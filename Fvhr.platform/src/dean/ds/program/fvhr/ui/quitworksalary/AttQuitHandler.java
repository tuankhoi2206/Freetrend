package ds.program.fvhr.ui.quitworksalary;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import ds.program.fvhr.dao.quitsalary.QuitWorkSalaryDAO;
import ds.program.fvhr.domain.ATTENDANTDB_QUIT;
import ds.program.fvhr.domain.ATTQUIT;

public class AttQuitHandler {

	private ATTQUIT data;

	private static final BigDecimal KHONG = BigDecimal.ZERO;

	private static final BigDecimal MOT = BigDecimal.ONE;

	private static final BigDecimal TAM = BigDecimal.valueOf(8);

	private static final BigDecimal MUOICHAM4 = BigDecimal.valueOf(10.4);

	private static final BigDecimal NGAY_CONG = BigDecimal.valueOf(26);

	public AttQuitHandler() {
	}

	/**
	 * Generate ATTQUIT
	 * 
	 * @param _Data
	 * @throws ParseException
	 */
	public synchronized void calculate(QuitWorkSalaryDAO dao,
			ATTENDANTDB_QUIT att) throws ParseException {
		// Init data
		data = new ATTQUIT();
		data.setEMPSN(att.getEMPSN());
		data.setEMPNA(att.getEMPNA());
		data.setHIRED(att.getHIRED());
		data.setDATE_OFF(att.getDATE_OFF());
		data.setPOSSN(att.getPOSSN());
		data.setDOT_TV(att.getDOT_TV());
		data.setDEPT_KT(att.getDEPT_KT());
		data.setDEPSN(att.getDEPSN());
		data.setNOTE(att.getNOTE_BH());
		// apply recipe
		// -----temporary data-------//
		BigDecimal soNgayPhaiLam = BigDecimal.valueOf(fvWorkingDays(Integer
				.parseInt(dao.getMonth()), Integer.parseInt(dao.getYear())));
		BigDecimal ngayLamThucTe = att.getDUCLS().add(att.getNUCLS()).add(
				att.getREST_PAY()).add(att.getOTHER());
		BigDecimal[] tangCaLe = dao.getSoGioTangCaLe(att.getEMPSN());
		BigDecimal soNgayTangCaLe = getTangCaLeNgay(tangCaLe[0]).add(
				getTangCaLeDem(tangCaLe[1]));
		BigDecimal ngayLamTangCaLe = ngayLamThucTe.add(soNgayTangCaLe);
		BigDecimal luongCoBanMotNgay = att.getBSALY().divide(NGAY_CONG);
		BigDecimal luongCBBQMotNgay = (att.getBSAL_AVG().add(att
				.getBONUS2_AVG())).divide(NGAY_CONG);
		String dbonus = dao.getDBonus(att.getDEPSN());
		// tien thuong - bonus1
		BigDecimal tienThuong = att.getBONUS1();
		BigDecimal attTemp = att.getTEMP() == null ? KHONG : att.getTEMP();
		if (dbonus.equals("BS1")) {
			tienThuong = (tienThuong.divide(soNgayPhaiLam))
					.multiply(ngayLamThucTe);
			tienThuong = round100(tienThuong).add(attTemp);
		} else {
			tienThuong = tienThuong.add(attTemp);
		}
		data.setBONUS1(tienThuong);
		// pccv - bonus2
		data.setBONUS2(round100((att.getBONUS2().divide(NGAY_CONG))
				.multiply(ngayLamTangCaLe)));
		// pccviec - bonus3
		BigDecimal pccviec = att.getBONUS3();
		if (dbonus.equals("BS3")) {
			pccviec = round100((pccviec.divide(soNgayPhaiLam))
					.multiply(ngayLamTangCaLe));
		} else {
			if ((att.getREST().add(att.getREST_SICK())).compareTo(BigDecimal
					.valueOf(5)) > 0
					|| att.getNWHOUR().compareTo(KHONG) > 0
					|| (soNgayPhaiLam.subtract(BigDecimal.valueOf(5)))
							.compareTo(ngayLamThucTe) > 0) {
				pccviec = KHONG;
			}
		}
		data.setBONUS3(pccviec);
		// pc sinh hoat - bonus4
		data.setBONUS4(round100((att.getBONUS4().divide(NGAY_CONG))
				.multiply(ngayLamTangCaLe)));
		// bu bao hiem - bonus9
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		BigDecimal buBHNuaThangDau = round100(dao.getBuBHGiuaThang(sdf
				.parse("01/" + dao.getMonth() + "/" + dao.getYear()), sdf
				.parse("15/" + dao.getMonth() + "/" + dao.getYear()),
				soNgayPhaiLam, att.getTEMP1(), att.getEMPSN()));
		BigDecimal buBHNuaThangSau = round100(dao.getBuBHGiuaThang(sdf
				.parse("16/" + dao.getMonth() + "/" + dao.getYear()),
				getEndDay(dao.getMonth(), dao.getYear()), soNgayPhaiLam, att
						.getTEMP2(), att.getEMPSN()));
		boolean dkBuBH = att.getDUCLS().add(att.getNUCLS()).add(
				att.getREST_PAY()).add(soNgayTangCaLe).compareTo(
				soNgayPhaiLam.divide(BigDecimal.valueOf(2))) >= 0;
		if (dkBuBH) {
			data.setBONUS9(round100(att.getBONUS9().add(buBHNuaThangDau).add(
					buBHNuaThangSau)));
		} else {
			data.setBONUS9(KHONG);
		}
		// bhxh, bhtn
		BigDecimal bhxh = att.getJOININSU();
		BigDecimal bhtn = att.getBH_TNGHIEP();
		if ((att.getDUCLS().add(att.getNUCLS()).add(att.getREST_PAY())
				.add(soNgayTangCaLe)).compareTo(soNgayPhaiLam.divide(BigDecimal
				.valueOf(2))) < 0) {
			bhxh = BigDecimal.ZERO;
			bhtn = BigDecimal.ZERO;
		}
		data.setYLBX(bhxh);
		data.setBH_TNGHIEP(bhtn);
		// chuyen can
		BigDecimal chuyenCan = BigDecimal.valueOf(80000);
		BigDecimal nc = att.getDUCLS().add(att.getNUCLS()).add(
				att.getREST_PAY());
		BigDecimal pns = att.getPHEP_NS() == null ? BigDecimal.ZERO : att
				.getPHEP_NS();
		boolean cnm = att.getEMPSN().substring(0, 2).equals(
				dao.getYear().substring(2, 4))
				&& att.getEMPSN().substring(2, 4).equals(dao.getMonth());
		if ((pns.compareTo(BigDecimal.ZERO) > 0 && pns.compareTo(soNgayPhaiLam) < 0)
				|| nc.compareTo(soNgayPhaiLam) < 0 || cnm) {
			chuyenCan = (chuyenCan.divide(soNgayPhaiLam))
					.multiply(ngayLamTangCaLe);
		}
		if (att.getNWHOUR().compareTo(BigDecimal.ZERO) > 0
				|| att.getREST().compareTo(BigDecimal.valueOf(3.6)) >= 0
				|| att.getDUCLS().add(att.getNUCLS())
						.compareTo(BigDecimal.ZERO) == 0
				|| ngayLamTangCaLe.compareTo(BigDecimal.ZERO) == 0) {
			chuyenCan = BigDecimal.ZERO;
		}
		if (att.getREST().compareTo(BigDecimal.ZERO) > 0
				&& att.getREST().compareTo(BigDecimal.valueOf(3.6)) < 0) {
			chuyenCan = chuyenCan.subtract(att.getREST().multiply(
					BigDecimal.valueOf(22500)));
		}
		BigDecimal soLanKyTen = att.getSIGN_TIME();
		if (soLanKyTen.compareTo(BigDecimal.ONE) == 0)
			chuyenCan = chuyenCan.subtract(BigDecimal.valueOf(1000));
		else if (soLanKyTen.compareTo(BigDecimal.valueOf(2)) == 0)
			chuyenCan = chuyenCan.subtract(BigDecimal.valueOf(3000));
		else if (soLanKyTen.compareTo(BigDecimal.valueOf(3)) == 0)
			chuyenCan = chuyenCan.subtract(BigDecimal.valueOf(10000));
		else if (soLanKyTen.compareTo(BigDecimal.valueOf(4)) == 0)
			chuyenCan = chuyenCan.subtract(BigDecimal.valueOf(15000));
		else if (soLanKyTen.compareTo(BigDecimal.valueOf(5)) == 0)
			chuyenCan = chuyenCan.subtract(BigDecimal.valueOf(20000));
		else if (soLanKyTen.compareTo(BigDecimal.valueOf(6)) == 0)
			chuyenCan = chuyenCan.subtract(BigDecimal.valueOf(30000));
		else if (soLanKyTen.compareTo(BigDecimal.valueOf(7)) == 0)
			chuyenCan = chuyenCan.subtract(BigDecimal.valueOf(35000));
		else
			chuyenCan = chuyenCan.subtract(BigDecimal.valueOf(45000));
		BigDecimal late = att.getLATE();
		if (late.compareTo(BigDecimal.valueOf(5)) > 0)
			chuyenCan = chuyenCan.subtract(late.multiply(BigDecimal
					.valueOf(1000)));
		if (chuyenCan.compareTo(BigDecimal.ZERO) < 0)
			chuyenCan = BigDecimal.ZERO;
		data.setBONUS6(chuyenCan);
		// tien com tang ca
		data.setBONUS7(round100((att.getACNM().add(att.getACNM_O()))
				.multiply(BigDecimal.valueOf(1000))));
		// chuyen can tang ca
		BigDecimal chuyenCanTC = att.getADDCLS1().add(att.getADDCLS1_O()).add(
				att.getNADDCLS()).add(att.getNADDCLS_O());
		chuyenCanTC = chuyenCanTC.divideAndRemainder(BigDecimal.valueOf(3))[0];
		if (chuyenCanTC.compareTo(BigDecimal.valueOf(15)) > 0) {
			chuyenCanTC = BigDecimal.valueOf(45000);
		} else {
			chuyenCanTC = chuyenCanTC.multiply(BigDecimal.valueOf(2500));
		}
		data.setBONUS_ACN(round100(chuyenCanTC));
		// phu cap ca dem
		BigDecimal pcCaDem = att.getNUCLS();
		if (pcCaDem.compareTo(BigDecimal.valueOf(7)) > 0) {
			pcCaDem = pcCaDem.multiply(BigDecimal.valueOf(0.35)).multiply(
					luongCoBanMotNgay);
		} else {
			pcCaDem = pcCaDem.multiply(BigDecimal.valueOf(0.3)).multiply(
					luongCoBanMotNgay);
		}
		data.setBSALY_N(round100(pcCaDem));
		// tong phu cap
		BigDecimal tongPhuCap = data.getBONUS1().add(data.getBONUS2()).add(
				data.getBONUS3()).add(data.getBONUS4()).add(data.getBONUS5())
				.add(data.getBONUS5()).add(data.getBONUS6()).add(
						data.getBONUS_ACN()).add(data.getBONUS7()).add(
						data.getBONUS9());
		data.setTBONUS(tongPhuCap);
		// luong ngay lam
		BigDecimal luongNgayLam = att.getDUCLS();
		data.setDUCLS_S(round100(luongNgayLam.multiply(luongCoBanMotNgay)));
		// nghi co luong
		BigDecimal nghiCoLuong = att.getREST_PAY();
		data.setREST_PAY_S(round100(nghiCoLuong.multiply(luongCoBanMotNgay)));
		// tien dem lam
		BigDecimal tienDemLam = att.getNUCLS().multiply(luongCoBanMotNgay);
		data.setNUCLS_S(round100(tienDemLam));
		// tien tang ca ngay
		BigDecimal tienTangCaNgay = att.getADDCLS1()
				.multiply(luongCoBanMotNgay);
		BigDecimal tienTCNgayKoThue = tienTangCaNgay.divide(BigDecimal
				.valueOf(16));
		data.setADDCLS1_S(round100(tienTangCaNgay.divide(TAM)));
		// tien tang ca dem
		BigDecimal tienTangCaDem = att.getNADDCLS();
		BigDecimal tienTCDemKoThue = tienTangCaDem.multiply(
				BigDecimal.valueOf(0.95)).multiply(luongCoBanMotNgay).divide(
				TAM);
		data.setNADDCLS_S(round100(tienTangCaDem.multiply(luongCoBanMotNgay)
				.divide(TAM)));
		// tien tang ca chu nhat
		BigDecimal tienTangCaCN = att.getADDHOL();
		BigDecimal tienTCCNKoThue = tienTangCaCN.multiply(luongCoBanMotNgay)
				.divide(TAM);
		data.setADDHOL_S(round100(tienTangCaCN.multiply(luongCoBanMotNgay)
				.divide(TAM)));
		// tien tang ca le
		BigDecimal tienTangCaLe = att.getADDHOLN();
		BigDecimal tiengTCLeKoThue = tienTangCaLe.multiply(luongCoBanMotNgay)
				.divide(BigDecimal.valueOf(4));
		data.setADDHOLN_S(round100(tienTangCaLe.multiply(luongCoBanMotNgay)
				.divide(TAM)));
		// tien tang ca ngay ngoai
		BigDecimal tienTangCaNgayNgoai = att.getADDCLS1_O().multiply(
				luongCoBanMotNgay);
		BigDecimal tienTCNgayNgoaiKoThue = tienTangCaNgayNgoai
				.divide(BigDecimal.valueOf(16));
		data.setADDCLS1_O_S(round100(tienTangCaNgayNgoai.divide(TAM)));
		// tien tang ca dem ngoai
		BigDecimal tienTangCaDemNgoai = att.getNADDCLS_O();
		BigDecimal tienTCDemNgoaiKoThue = tienTangCaDemNgoai.multiply(
				BigDecimal.valueOf(0.95)).multiply(luongCoBanMotNgay).divide(
				TAM);
		data.setNADDCLS_O_S(round100(tienTangCaDemNgoai.multiply(
				luongCoBanMotNgay).divide(TAM)));
		// tien tang ca chu nhat ngoai
		BigDecimal tienTangCaCNNgoai = att.getADDHOL_O();
		BigDecimal tienTCCNNgoaiKoThue = tienTangCaCNNgoai.multiply(
				luongCoBanMotNgay).divide(TAM);
		data.setADDHOL_O_S(round100(tienTangCaCNNgoai.multiply(
				luongCoBanMotNgay).divide(TAM)));
		// tien tang ca le ngoai
		BigDecimal tienTangCaLeNgoai = att.getADDHOLN_O();
		BigDecimal tienTCLeNgoaiKoThue = tienTangCaLeNgoai.multiply(
				luongCoBanMotNgay).divide(BigDecimal.valueOf(4));
		data.setADDHOLN_O_S(round100(tienTangCaLeNgoai.multiply(
				luongCoBanMotNgay).divide(TAM)));
		// Tong luong co ban + phep
		data.setTBASIC(data.getDUCLS_S().add(data.getNUCLS_S()).add(
				data.getREST_PAY_S()));
		// Tong luong tang ca
		data.setTADDCLS(data.getADDCLS1().add(data.getNADDCLS()).add(
				data.getADDHOL()).add(data.getADDHOLN()).add(
				data.getADDCLS1_O()).add(data.getNADDCLS_O()).add(
				data.getADDHOL_O()).add(data.getADDHOLN_O()));
		data.setTADDS(data.getADDCLS1_S().add(data.getNADDCLS_S()).add(
				data.getADDHOL_S()).add(data.getADDHOLN_S()).add(
				data.getADDCLS1_O_S()).add(data.getNADDCLS_O_S()).add(
				data.getADDHOL_O_S()).add(data.getADDHOLN_O_S()));
		// ----luy ke
		// tong ngay cong
		data.setTDAY(data.getDUCLS().add(data.getNUCLS()).add(
				data.getREST_PAY()));
		// tong cong chua khau tru
		data.setTINCOME(data.getTBASIC().add(data.getTADDS()).add(
				data.getTBONUS()));
		// thue thu nhap
		BigDecimal tangCaKoThue = tienTCNgayKoThue.add(tienTCDemKoThue).add(
				tienTCCNKoThue).add(tiengTCLeKoThue).add(tienTCNgayNgoaiKoThue)
				.add(tienTCDemNgoaiKoThue).add(tienTCCNNgoaiKoThue).add(
						tienTCLeNgoaiKoThue);
		data.setBSALY_N(data.getBSALY_N().add(tangCaKoThue));// sao lai cong
																// vao pc ca dem
		// bhyt
		data.setYLBX(round100(att.getBU_BHYT().add(att.getTHU_BHYT()).add(
				att.getYLBX())));
		// thue
		BigDecimal temp = round100(data.getTINCOME().add(data.getYLBX()).add(
				att.getBH_TNGHIEP()).subtract(data.getJOININSU()));
		BigDecimal thue = temp.subtract(
				att.getBAC().multiply(BigDecimal.valueOf(1600000))).add(
				BigDecimal.valueOf(4000000));
		thue = round100(thue);
		BigDecimal ret = KHONG;
		if (thue.compareTo(BigDecimal.valueOf(5000000)) <= 0) {
			ret = thue.multiply(BigDecimal.valueOf(0.05));
		} else if (thue.compareTo(BigDecimal.valueOf(5000000)) > 0
				&& thue.compareTo(BigDecimal.valueOf(10000000)) <= 0) {
			ret = (thue.subtract(BigDecimal.valueOf(5000000)))
					.multiply(BigDecimal.valueOf(0.1));
			ret = round100(ret.add(BigDecimal.valueOf(250000)));
		} else if (thue.compareTo(BigDecimal.valueOf(10000000)) > 0
				&& thue.compareTo(BigDecimal.valueOf(18000000)) <= 0) {
			ret = (thue.subtract(BigDecimal.valueOf(10000000)))
					.multiply(BigDecimal.valueOf(0.15));
			ret = round100(ret.add(BigDecimal.valueOf(750000)));
		} else if (thue.compareTo(BigDecimal.valueOf(18000000)) > 0
				&& thue.compareTo(BigDecimal.valueOf(32000000)) <= 0) {
			ret = (thue.subtract(BigDecimal.valueOf(18000000)))
					.multiply(BigDecimal.valueOf(0.2));
			ret = round100(ret.add(BigDecimal.valueOf(1950000)));
		} else if (thue.compareTo(BigDecimal.valueOf(32000000)) > 0
				&& thue.compareTo(BigDecimal.valueOf(52000000)) <= 0) {
			ret = (thue.subtract(BigDecimal.valueOf(32000000)))
					.multiply(BigDecimal.valueOf(0.25));
			ret = round100(ret.add(BigDecimal.valueOf(4000000)));
		} else if (thue.compareTo(BigDecimal.valueOf(52000000)) > 0
				&& thue.compareTo(BigDecimal.valueOf(80000000)) <= 0) {
			ret = (thue.subtract(BigDecimal.valueOf(52000000)))
					.multiply(BigDecimal.valueOf(0.3));
			ret = round100(ret.add(BigDecimal.valueOf(9000000)));
		} else if (thue.compareTo(BigDecimal.valueOf(80000000)) > 0) {
			ret = (thue.subtract(BigDecimal.valueOf(52000000)))
					.multiply(BigDecimal.valueOf(0.35));
			ret = round100(ret.add(BigDecimal.valueOf(17400000)));
		}
		ret = round100(ret);
		if (ret.compareTo(KHONG) <= 0)
			ret = KHONG;
		data.setPAYTAX(ret);
		// luy ke tncn

		// /////
		data.setTINCOME(data.getTINCOME().add(data.getBSALY_N()));
		data.setTADDS(data.getTADDS().add(data.getBSALY_N()));
		// kqt, stnv
		data.setKQT(att.getKQT());
		data.setSTNV(att.getSTNV());
		// joinlum, borm
		data.setJOINLUM(att.getJOINLUM());
		data.setBORM(att.getBORM());
		// tkq
		data.setTKQ(data.getBORM().add(data.getJOININSU()).add(
				data.getBH_TNGHIEP()).add(data.getYLBX()).add(data.getPAYTAX())
				.add(data.getJOINLUM()).add(data.getKQT()));
		// phep nam con lai
		if (att.getREST_REMAIN().compareTo(KHONG) > 0) {
			data.setPN_CONLAI_S(round100(att.getREST_REMAIN().multiply(
					BigDecimal.valueOf(2)).multiply(luongCoBanMotNgay)));
		} else {
			data.setPN_CONLAI_S(round100(att.getREST_REMAIN().multiply(
					luongCoBanMotNgay)));
		}
		// //
		data
				.setTC_BSALY(round100(att.getMM_BONUS().multiply(
						att.getBSAL_AVG())));
		data.setTC_BONUS2(round100(att.getMM_BONUS().multiply(
				att.getBONUS2_AVG())));
		data.setDD_NGHITRUOC_S(round100(att.getDAY_BEFORE().multiply(
				luongCBBQMotNgay)));
		data.setMM_DENBU_S(round100(att.getMM_DENBU().multiply(
				att.getBSAL_AVG().add(att.getBONUS2_AVG()))));
		data.setMM_TROCAP_S(data.getTC_BSALY().add(data.getTC_BONUS2()).add(
				data.getMM_DENBU_S()));
		// t_tctviec
		if (data.getPN_CONLAI_S().compareTo(KHONG) > 0)
			data
					.setTT_TCTVIEC(data.getPN_CONLAI_S().add(
							data.getMM_TROCAP_S()));
		else
			data.setTT_TCTVIEC(data.getMM_TROCAP_S());
		// ts_hientai1, ts_hientai
		data.setTS_HIENTAI1(data.getTINCOME().subtract(data.getTKQ()));
		data.setTS_HIENTAI(data.getTS_HIENTAI1());
		// xet cac dieu kien khi luong thuc nhan < 0
		if (data.getTS_HIENTAI().add(data.getTT_TCTVIEC()).subtract(
				data.getDD_NGHITRUOC_S()).compareTo(KHONG) < 0) {
			// do long code @@
			if (att.getCOMBSALY().compareTo(KHONG) > 0) { // da ky hop dong
				if (data.getTT_TCTVIEC().add(data.getTS_HIENTAI()).compareTo(
						KHONG) >= 0)
					data.setDD_NGHITRUOC_S(data.getTT_TCTVIEC().add(
							data.getTS_HIENTAI()));
				else {

				}
			} else { // chua ky hop dong
				if (data.getTT_TCTVIEC().add(data.getTS_HIENTAI()).compareTo(
						KHONG) >= 0)
					data.setDD_NGHITRUOC_S(KHONG);
				else {

				}
			}
			// ////Bu luong

		}

		if (data.getPN_CONLAI_S().compareTo(KHONG) >= 0) {
			data.setTT_TCTVIEC(data.getTT_TCTVIEC().subtract(
					data.getDD_NGHITRUOC_S()));
		} else {
			if (data.getTS_HIENTAI().add(data.getTT_TCTVIEC()).add(
					data.getPN_CONLAI_S()).subtract(data.getDD_NGHITRUOC_S())
					.compareTo(KHONG) >= 0) {
				data.setPN_CONLAI_S(data.getTS_HIENTAI().add(
						data.getTT_TCTVIEC()).add(data.getPN_CONLAI_S())
						.subtract(data.getDD_NGHITRUOC_S()).negate());
			} else
				data.setPN_CONLAI_S(KHONG);
			data.setTT_TCTVIEC(data.getTT_TCTVIEC().add(data.getPN_CONLAI_S())
					.subtract(data.getDD_NGHITRUOC_S()));
		}
		data.setTT_TCTVIEC1(round500(data.getTT_TCTVIEC()));
		data.setTS_HIENTAI2(round500(data.getTS_HIENTAI()));
		data.setTS1(data.getTT_TCTVIEC().add(data.getTS_HIENTAI()));
		data.setTTS(round500(data.getTT_TCTVIEC1()).add(data.getTS_HIENTAI2()));
		// set other data
		// save ATTQUIT data
	}

	public ATTQUIT getData() {
		try {
			synchronized (data) {
				ATTQUIT att = (ATTQUIT) BeanUtils.cloneBean(data);
				return att;
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	public BigDecimal getTangCaLeNgay(BigDecimal dbValue) {
		BigDecimal dbDiv = dbValue.divide(TAM);
		BigDecimal dbMod = dbValue.divideAndRemainder(TAM)[1];
		if (dbDiv.compareTo(MOT) > 0 && dbMod.compareTo(KHONG) > 0) {
			return (dbValue.subtract(dbMod)).divide(TAM);
		}
		return dbValue.divide(TAM);
	}

	public BigDecimal getTangCaLeDem(BigDecimal dbValue) {
		BigDecimal dbDiv = dbValue.divide(MUOICHAM4);
		BigDecimal dbMod = dbValue.divideAndRemainder(MUOICHAM4)[1];
		if (dbDiv.compareTo(MOT) > 0 && dbMod.compareTo(KHONG) > 0) {
			return (dbValue.subtract(dbMod)).divide(MUOICHAM4);
		}
		return dbValue.divide(MUOICHAM4);
	}

	public BigDecimal round100(BigDecimal number) {
		return number.setScale(-2, RoundingMode.HALF_UP);
	}

	public BigDecimal round500(BigDecimal number) {
		BigDecimal onek = BigDecimal.valueOf(1000);
		BigDecimal[] mods = number.divideAndRemainder(onek);
		BigDecimal mod1 = mods[1];
		if (number.compareTo(BigDecimal.ZERO) >= 0) {
			if (mod1.compareTo(BigDecimal.valueOf(750)) >= 0) {
				mod1 = onek;
			} else if (mod1.compareTo(BigDecimal.valueOf(250)) >= 0) {
				mod1 = BigDecimal.valueOf(500);
			} else {
				mod1 = BigDecimal.ZERO;
			}
			return mods[0].multiply(onek).add(mod1);
		} else {
			if (mod1.compareTo(BigDecimal.valueOf(750)) <= 0) {
				mod1 = onek.negate();
			} else if (mod1.compareTo(BigDecimal.valueOf(250)) <= 0) {
				mod1 = BigDecimal.valueOf(-500);
			} else {
				mod1 = BigDecimal.ZERO;
			}
			return mods[0].multiply(onek).add(mod1);
		}
	}

	public int fvWorkingDays(int month, int year) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		int days = c.get(Calendar.DAY_OF_MONTH);
		int n = days;
		int s = 1;
		for (int i = 1; i <= days; i += s) {
			c.set(Calendar.DAY_OF_MONTH, i);
			if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				s = 7;
				n--;
			}
		}
		return n;
	}

	public Date getEndDay(String month, String year) {
		int m = Integer.parseInt(month);
		int y = Integer.parseInt(year);
		Calendar cal = Calendar.getInstance();
		cal.set(y, m, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}
}
