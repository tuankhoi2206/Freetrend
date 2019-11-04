package ds.program.fvhr.minh.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import ds.program.fvhr.domain.BHXH_TN;
import ds.program.fvhr.domain.N_CT_BHXH_TN;
import fv.util.JdbcDAO;

public class ConnectionDatabase extends JdbcDAO{
	public List<BHXH_TN> getBCTH(final String id_bhxhtn,int stt)
	{
		try {
			
			String tblname = "n_ct_bhxh_tn_1";
			if(stt==2)
				tblname = "n_ct_bhxh_tn_2";
			String sql ="" +
			"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,0 ma  from "+tblname+" b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_FACT='FVLS')union("+
			"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,1 ma  from "+tblname+" b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.ID_DEPT='W0008')union("+
			"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,2 ma  from "+tblname+" b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.ID_DEPT in ('W0007','W0009'))union("+
			"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,3 ma  from "+tblname+" b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.ID_DEPT IN ('00001','00002','00003'))union("+
			"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,4 ma  from "+tblname+" b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_DEPT LIKE 'FVL.F12%' OR d.ID_DEPT='KHODE')union("+
			"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,5 ma  from "+tblname+" b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_GROUP IN ('GM','F3-GM') AND d.NAME_FACT='FVL')union("+
			"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,6 ma  from "+tblname+" b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_DEPT LIKE 'FVL.F1%' AND d.NAME_GROUP NOT LIKE 'F12%' AND d.ID_DEPT<>'KHODE')union("+
			"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,7 ma  from "+tblname+" b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_DEPT LIKE 'FVL.F2%')union("+
			"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,8 ma  from "+tblname+" b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_DEPT LIKE 'FVL.F3%' AND d.NAME_GROUP NOT LIKE 'F3-GM%')union("+
			"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,9 ma  from "+tblname+" b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_DEPT LIKE 'FVL.F5%')union("+
			"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,10 ma  from "+tblname+" b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_FACT='FVL' AND d.NAME_GROUP='KS')union("+
			"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,11 ma  from "+tblname+" b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.ID_DEPT='MS001')union("+
			"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,12 ma  from "+tblname+" b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_FACT='TB' AND d.ID_DEPT NOT IN ('00001','00002','00003','MS001'))union("+
			"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,13 ma  from "+tblname+" b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_FACT='FVL' AND d.NAME_GROUP='FVJ-BGC')union("+
			"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,14 ma  from "+tblname+" b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_FACT='FVL' AND d.NAME_GROUP='FVJ-XT')))))))))))))))"+
			"order by ma";
			
			List<BHXH_TN> list = new ArrayList<BHXH_TN>();
			
			list = getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<BHXH_TN>() {
				@Override
				public BHXH_TN mapRow(ResultSet rs, int arg1) throws SQLException {
					BHXH_TN bc;
					bc = new BHXH_TN();
					bc.setId_bhxh_tn(id_bhxhtn);
					bc.setId_dk(rs.getInt("ma"));
					bc.setSo_nguoi(rs.getInt("songuoi"));
					bc.setTong_luong(rs.getLong("tongluong"));
					return bc;
				}
			}, new Object[]{id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn
					,id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn});	
			
			if(stt==2)
			{
				//lan 2: so nguoi = so nguoi bo sung - so nguoi thu hoi // co the mang gia tri am
				//   tong tien = tong tien cua bo sung - tong tien cua thu hoi // co the mang gia tri am
				sql ="" +
				"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,0 ma  from n_ct_bhxh_tn_0 b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_FACT='FVLS')union("+
				"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,1 ma  from n_ct_bhxh_tn_0 b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.ID_DEPT='W0008')union("+
				"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,2 ma  from n_ct_bhxh_tn_0 b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.ID_DEPT in ('W0007','W0009'))union("+
				"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,3 ma  from n_ct_bhxh_tn_0 b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.ID_DEPT IN ('00001','00002','00003'))union("+
				"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,4 ma  from n_ct_bhxh_tn_0 b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_DEPT LIKE 'FVL.F12%' OR d.ID_DEPT='KHODE')union("+
				"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,5 ma  from n_ct_bhxh_tn_0 b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_GROUP IN ('GM','F3-GM') AND d.NAME_FACT='FVL')union("+
				"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,6 ma  from n_ct_bhxh_tn_0 b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_DEPT LIKE 'FVL.F1%' AND d.NAME_GROUP NOT LIKE 'F12%' AND d.ID_DEPT<>'KHODE')union("+
				"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,7 ma  from n_ct_bhxh_tn_0 b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_DEPT LIKE 'FVL.F2%')union("+
				"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,8 ma  from n_ct_bhxh_tn_0 b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_DEPT LIKE 'FVL.F3%' AND d.NAME_GROUP NOT LIKE 'F3-GM%')union("+
				"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,9 ma  from n_ct_bhxh_tn_0 b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_DEPT LIKE 'FVL.F5%')union("+
				"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,10 ma  from n_ct_bhxh_tn_0 b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_FACT='FVL' AND d.NAME_GROUP='KS')union("+
				"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,11 ma  from n_ct_bhxh_tn_0 b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.ID_DEPT='MS001')union("+
				"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,12 ma  from n_ct_bhxh_tn_0 b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_FACT='TB' AND d.ID_DEPT NOT IN ('00001','00002','00003','MS001'))union("+
				"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,13 ma  from n_ct_bhxh_tn_0 b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_FACT='FVL' AND d.NAME_GROUP='FVJ-BGC')union("+
				"select count(b.empsn) as songuoi, sum(b.luonghd) tongluong,14 ma  from n_ct_bhxh_tn_0 b, n_department d where b.id_bhxhtn = ? and b.depsn = d.id_dept and  (d.NAME_FACT='FVL' AND d.NAME_GROUP='FVJ-XT')))))))))))))))"+
				"order by ma";
				List<BHXH_TN> listtemps = new ArrayList<BHXH_TN>();
				
				listtemps = getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<BHXH_TN>() {
					@Override
					public BHXH_TN mapRow(ResultSet rs, int arg1) throws SQLException {
						BHXH_TN bc;
						bc = new BHXH_TN();
						bc.setId_bhxh_tn(id_bhxhtn);
						bc.setId_dk(rs.getInt("ma"));
						bc.setSo_nguoi(rs.getInt("songuoi"));
						bc.setTong_luong(rs.getLong("tongluong"));
						return bc;
					}
				}, new Object[]{id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn
						,id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn,id_bhxhtn});	
				
				for (BHXH_TN obj : list) 
				{
					BHXH_TN objtemp = listtemps.get(obj.getId_dk());
					obj.setSo_nguoi(obj.getSo_nguoi()-objtemp.getSo_nguoi());
					obj.setTong_luong(obj.getTong_luong()-objtemp.getTong_luong());
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString()+"sql");
		}
		return null;
	}
	
	public long getTHLuong(String id_bhxhtn, int stt)
	{
		try {
			String sql ="select sum(luonghd) as tongsoluong from n_ct_bhxh_tn_"+stt+" where id_bhxhtn = ? " ;
			return getSimpleJdbcTemplate().queryForLong(sql, new Object[]{id_bhxhtn});
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString()+"sql");
		}
		return 0;
	}

	public float[] BHXHTN()
	{
		try {
			float[] tyle = new float[2];
			tyle[0] = getSimpleJdbcTemplate().queryForObject("select giatri3 from n_thamso where tenthamso = 'TYLEBHXH'",Float.class, new Object[]{});
			tyle[1] = getSimpleJdbcTemplate().queryForObject("select giatri3 from n_thamso where tenthamso = 'TYLEBHTN'",Float.class, new Object[]{});
			return tyle;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return null;
	}
	/// lay tu ban n_ct_bhxh_tn_1/2 
	public List<N_CT_BHXH_TN> getBCCTday(final String date, String name, final int stt) {
		try {
			String tblname ="n_ct_bhxh_tn_1";
			if(stt == 2)
				tblname ="n_ct_bhxh_tn_2";
			
			String sql="select b.*, d.name_dept  from "+tblname+" b, n_department d where b.id_bhxhtn = ?" +
					" and b.depsn = d.id_dept and d.name_dept like ?";
			
			name+="%";
			
			String id = date.substring(3)+date.substring(0,2);
			
			List<N_CT_BHXH_TN> list = new ArrayList<N_CT_BHXH_TN>();
			list = getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<N_CT_BHXH_TN>() {
				@Override
				public N_CT_BHXH_TN mapRow(ResultSet rs, int arg1) throws SQLException {
					N_CT_BHXH_TN bc = new N_CT_BHXH_TN();
					bc.setEMPSN(rs.getString("empsn"));
					bc.setNGAYCONG(rs.getString("ngaycong"));
					bc.setLUONGCB(rs.getString("luongCB"));
					bc.setLUONGHD(rs.getString("luongHD"));
					bc.setDEPSN(rs.getString("DEPSN"));
					bc.setDEPT_NAME(rs.getString("name_dept"));
					return bc;
				}
			},id, name);
			
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return null;
	}
	
	public int getCountdayofmonth(int thang,int nam)
	{
		int day = 0;
		if(thang == 1 || thang == 3 || thang == 5 || thang == 7 || thang == 8 || thang == 10 || thang == 12 )
			day = 31;
		else 
			if(thang == 2)
				if(nam%4==0)
					day = 29;
				else
					day = 28;
			else
				day = 30;
		
		return day;
	}
	
	public Object[] getKMLAN(String id_bhxhtn, String namefact)
	{
		try {
			if(!namefact.equals(""))
				namefact += ".%";
			else
				namefact+="%";
			String sql="select a.*, d.name_dept from n_ct_bhxh_tn_2 a , n_department d where a.id_bhxhtn = '"+id_bhxhtn+"'" +
					"and a.status like '0%' and a.depsn = d.id_dept and name_dept like '"+namefact+"'";
			
			List<N_CT_BHXH_TN> listBS = new ArrayList<N_CT_BHXH_TN>();
			
			listBS = getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<N_CT_BHXH_TN>() {
				@Override
				public N_CT_BHXH_TN mapRow(ResultSet rs, int arg1) throws SQLException {
					N_CT_BHXH_TN bc = new N_CT_BHXH_TN();
					bc.setEMPSN(rs.getString("empsn"));
					bc.setNGAYCONG(rs.getString("ngaycong"));
					bc.setLUONGCB(rs.getString("luongCB"));
					bc.setLUONGHD(rs.getString("luongHD"));
					bc.setDEPSN(rs.getString("DEPSN"));
					bc.setDEPT_NAME(rs.getString("name_dept"));
					bc.setNOTE(rs.getString("status").substring(1));
					return bc;
				}
			},new Object[]{});
			
			sql="select a.*, d.name_dept from n_ct_bhxh_tn_0 a , n_department d where a.id_bhxhtn = '"+id_bhxhtn+"'" +
			"and a.depsn = d.id_dept and name_dept like '"+namefact+"'";
			List<N_CT_BHXH_TN> listTHo = new ArrayList<N_CT_BHXH_TN>();
			
			listTHo = getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<N_CT_BHXH_TN>() {
				@Override
				public N_CT_BHXH_TN mapRow(ResultSet rs, int arg1) throws SQLException {
					N_CT_BHXH_TN bc = new N_CT_BHXH_TN();
					bc.setEMPSN(rs.getString("empsn"));
					bc.setNGAYCONG(rs.getString("ngaycong"));
					bc.setLUONGCB(rs.getString("luongCB"));
					bc.setLUONGHD(rs.getString("luongHD"));
					bc.setDEPSN(rs.getString("DEPSN"));
					bc.setDEPT_NAME(rs.getString("name_dept"));
					bc.setNOTE(rs.getString("status"));
					return bc;
				}
			},new Object[]{});
			
			Object[] obj = new Object[]{listBS, listTHo};
			return obj;
			
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return null;
	}

	public List<N_CT_BHXH_TN> getKMLANold(String id_bhxhtn,int stt)
	{
		try {
			String tbl1=" n_ct_bhxh_tn_1 ";
			String tbl2=" n_ct_bhxh_tn_2 ";
			if(stt==2)
			{
				tbl1=" n_ct_bhxh_tn_2 ";
				tbl2=" n_ct_bhxh_tn_1 ";
			}
			
			String sql="select a.* from "+tbl1+" a where a.id_bhxhtn = '"+id_bhxhtn+"'" +
					"and a.empsn not in (select b.empsn from "+tbl2+" b where b.id_bhxhtn = '"+id_bhxhtn+"' and a.empsn=b.empsn)";
			
			List<N_CT_BHXH_TN> list = new ArrayList<N_CT_BHXH_TN>();
			list = getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<N_CT_BHXH_TN>() {
				@Override
				public N_CT_BHXH_TN mapRow(ResultSet rs, int arg1) throws SQLException {
					N_CT_BHXH_TN bc = new N_CT_BHXH_TN();
					bc.setEMPSN(rs.getString("empsn"));
					bc.setNGAYCONG(rs.getString("ngaycong"));
					bc.setLUONGCB(rs.getString("luongCB"));
					bc.setLUONGHD(rs.getString("luongHD"));
					bc.setDEPSN(rs.getString("depsn"));
					return bc;
				}
			},new Object[]{});
			return list;			
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return null;
	}


}
