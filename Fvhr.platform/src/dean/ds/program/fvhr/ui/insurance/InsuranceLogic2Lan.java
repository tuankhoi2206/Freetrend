package ds.program.fvhr.ui.insurance;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import fv.util.DateUtils;
import fv.util.MonthYearType;

public class InsuranceLogic2Lan {
	
	private static InsuranceLogic2Lan INSTANSE;
	private MonthYearType thangBaoGiam;
	private BieuMau3A data;
	private InsuranceDAO dao;
	private int lanBC;
	
	public static synchronized InsuranceLogic2Lan getInstanse(BieuMau3A bieuMau3A, InsuranceDAO dao){
		if (INSTANSE==null)
			INSTANSE = new InsuranceLogic2Lan(bieuMau3A, dao);
		else{
			INSTANSE.data=bieuMau3A;
			INSTANSE.dao=dao;
			INSTANSE.xacDinhThangBaoGiam();
		}
		return INSTANSE;
	}
	
	private InsuranceLogic2Lan(BieuMau3A bieuMau3A, InsuranceDAO dao){
		this.data=bieuMau3A;
		this.dao=dao;
		xacDinhThangBaoGiam();
	}
	
	public MonthYearType getThangBaoGiam(){
		return this.thangBaoGiam;
	}
	
	private void xacDinhThangBaoGiam(){
		try {
			MonthYearType thangNV = new MonthYearType(data.getNgayThucNghi());//thang nghi viec
			MonthYearType thangNVCong1 	= thangNV.add(1);
			
			String thangNViec	= "01/"+thangNV.getMonthString()+"/"+thangNV.getYearString();
			//thangNVtru1 chac chan da QT20 roi, vi 20/m se QT20 cua thang m						
			InsuranceDAO ins = new InsuranceDAO();
			
			// ngay gioi han cua QT20 thang NV			 
			Date ngayGioiHan	= ins.GetNgayGioiHanBC2120(thangNViec);
			Date ngayBGiam = ins.ToDate(data.getNgayBaoGiam());		
			
			int muaBHNV 	= ins.getThamGiaBHXHTN2Lan(data.getEmpsn(), thangNViec,data.getNgayBaoGiam());
			//int muaBHNV1	= ins.getThamGiaBHXHTN2120(data.getEmpsn(), thangNV1);
			
			//lay thong tin thang NSVao
			// ham lay ve dung thang se NSVao, ko lay theo ngay thuc te nua
			
			String thang = data.getNgayBaoGiam().substring(3, 5);
			String nam = data.getNgayBaoGiam().substring(6, 10);
			String ngayCuoiGiam = DateUtils.getLastDayString(Integer.parseInt(thang), Integer.parseInt(nam));
			
			String temp = ins.GetField("to_char(get_thang_ns_vao_empsn1('"+data.getEmpsn()+"',to_Date('"+ngayCuoiGiam+"','dd/mm/yyyy')),'"+"dd/mm/yyyy"+"')", "dual", "", "", "", "", "", "");
			Date nsVao = null;
			if (temp==null){
				nsVao = null;
			}
			else{
				nsVao = ins.ToDate(temp);
			}				
			/* if QT20 cua thang nghi viec chua bao cao thi quan tam ngay cong thang nghi viec
			 * if thangNV du cong --> giam thangNV+1
			 * else giam thangNV
			*/
			if (ngayBGiam.compareTo(ngayGioiHan)<=0)
			{
				if(nsVao==null){					
					if(muaBHNV==1)
						thangBaoGiam=thangNVCong1;
					else thangBaoGiam = thangNV;				
				}
				else{
					if (nsVao.compareTo(ins.ToDate(thangNViec))>0){
						thangBaoGiam = new MonthYearType(nsVao);
					}
					else{
						if (muaBHNV==1)
							thangBaoGiam= thangNVCong1;
						else thangBaoGiam= thangNV;
					}
				}
			}
			/*
			 * if QT20 cua thang giam da bao cao roi, luc nay ko quan tam ngay cong nua
			 */
			else
			{
				if (nsVao==null){
					thangBaoGiam= thangNVCong1;
				}
				else{
					if (nsVao.compareTo(ins.ToDate(thangNViec))>0)
						thangBaoGiam = new MonthYearType(nsVao);					
					else thangBaoGiam= thangNVCong1;			
				}
			}
					
			//System.out.println(data.getEmpsn()+ ", TS RO:"+tsNghiPhepRo+", Thang NV: "+ thangNV.toString()+", Thang bao giam: "+ thangBaoGiam.toString());
			if (thangBaoGiam==null) throw new RuntimeException("Can not detect insurance descrease month");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized InsuranceLogicData getDuLieuTheoThang(MonthYearType month){		
		String bangLuong = dao.getBangLuong(data.getEmpsn(), month);//bang luong thang nghi viec
		Float[] dl;
		if (bangLuong!=null){//ton tai bang luong
			if (bangLuong.contains("ATTQUIT"))
				//lay ngay cong theo bang luong thoi viec
				dl = dao.getNgayCong(data.getEmpsn(), month, true, true);
			else
				//lay ngay cong theo bang luong hien hanh
				dl = dao.getNgayCong(data.getEmpsn(), month, true, false);
		}else{//khong ton tai bang luong
			dl = dao.getNgayCong(data.getEmpsn(), month, false, false);
		}
		InsuranceLogicData idata = new InsuranceLogicData();
		idata.setEmpsn(data.getEmpsn());
		idata.setThang(month);
		idata.setTongNgayCong(dl[0]);
		//Float phanNuaCong = (float)DateUtils.fvWorkingDays(month.getMonth(), month.getYear())/2;		
		//idata.setPhanNuaCong(phanNuaCong);
		float tsngay = (float)DateUtils.fvWorkingDays(month.getMonth(), month.getYear());		
		float tsRo = tsngay-dl[0]; // So ngay RO = TSngay cua 1 thang- tsngay co luong
		idata.setTongSoNgayCuaThang(tsngay);
		idata.setTongNghiSan(dl[1]);
		//idata.setTongRo(dl[2]);
		idata.setTongRo(tsRo);
		idata.setLuong(dl[3]);
		return idata;
	}	
}


