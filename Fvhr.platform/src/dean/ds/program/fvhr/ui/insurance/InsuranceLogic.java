package ds.program.fvhr.ui.insurance;

import java.util.Date;
import java.text.ParseException;


import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import fv.util.DateUtils;
import fv.util.MonthYearType;

public class InsuranceLogic {
	
	private static InsuranceLogic INSTANSE;
	private MonthYearType thangBaoGiam;
	private BieuMau3A data;
	private InsuranceDAO dao;
	
	public static synchronized InsuranceLogic getInstanse(BieuMau3A bieuMau3A, InsuranceDAO dao){
		if (INSTANSE==null)
			INSTANSE = new InsuranceLogic(bieuMau3A, dao);
		else{
			INSTANSE.data=bieuMau3A;
			INSTANSE.dao=dao;
			INSTANSE.xacDinhThangBaoGiam();
		}
		return INSTANSE;
	}
	
	private InsuranceLogic(BieuMau3A bieuMau3A, InsuranceDAO dao){
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
			//InsuranceLogicData idata = getDuLieuTheoThang(thangNV);
			MonthYearType thangNVTru1 	= thangNV.add(-1);
			
			String thangNViec	= "01/"+thangNV.getMonthString()+"/"+thangNV.getYearString();
			String thangNV1		= "01/"+thangNVTru1.getMonthString()+"/"+thangNVTru1.getYearString();			
			InsuranceDAO ins = new InsuranceDAO();
			
			// ngay gioi han cua QT20 thang NV tru 1			 
			Date ngayGioiHan	= ins.GetNgayGioiHanBC(thangNV1);
			Date ngayBGiam = ins.ToDate(data.getNgayBaoGiam());
			
			int muaBHNV 	= ins.getThamGiaBHXHTN(data.getEmpsn(), thangNViec);
			int muaBHNV1	= ins.getThamGiaBHXHTN(data.getEmpsn(), thangNV1);
			// if thang NV du cong tham gia BHXH-TN -> thang bao giam = thang NV+1
			if (muaBHNV==1){
				thangBaoGiam	= thangNV.add(1);
			}
			// if thang NV ko du cong
			else{
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
				
				//if thang NV-1 du cong-> thang bao giam = thang NV
				if(muaBHNV1==1){
					thangBaoGiam	= thangNV;
				}
				else {
					//if thang NV tru 1 ko du cong va QT20 cua thang NV tru 1 van chua goi di
					// thi bao giam ke tu thang NV tru 1 luon
					// if thangNV va thangNV tru 1 ko du cong ma QT20 cua thangNV tru 1 van chua goi
					// va ko co thong tin NSan/ NSVao <= thangNV tru 1
					if (ngayBGiam.compareTo(ngayGioiHan)<=0 ){
						if (nsVao==null){
							thangBaoGiam	= thangNVTru1;
						}
						else{
							if (nsVao.compareTo(ins.ToDate(thangNViec))<0) {
								thangBaoGiam = thangNVTru1;
							}
							//else if (nsVao.compareTo(ins.ToDate(thangNV1))>0){
							// NSVao = thang NV
							// ko co truong hop chua toi thoi han NSVao ma nghi viec
							// vi vay ko co truong hop NSVAo> thangNV
							else{
								thangBaoGiam = new MonthYearType(nsVao);
							}
						}						
					}
					//if thang NV tru 1 ko du cong va QT20 cua thang NV tru 1 da goi di
					// thi bao giam ke tu thang NV 					
					else{
						thangBaoGiam	= thangNV;
					}
				}
			}
			
			/*			
			//System.out.println(" Thang NV: "+ thangNV.toString());
			// dk new
			float tsNghiPhepRo = idata.getTongRo();
			if (tsNghiPhepRo<14){
				thangBaoGiam= thangNV.add(1);
			}
			else thangBaoGiam=thangNV;
			*/
			
/*			// dk old 			
			Float phanNuaCongNV = (float)DateUtils.fvWorkingDays(thangNV.getMonth(), thangNV.getYear())/2;
			Float tsNgayCongNV = idata.getTongNgayCong();
			if (tsNgayCongNV.compareTo(phanNuaCongNV)>=0){
				thangNV.add(1);
			}		
			thangBaoGiam = thangNV.get();	
*/			
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
