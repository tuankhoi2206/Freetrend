package ds.program.fvhr.dao.insuranse;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import ds.program.fvhr.ui.insurance.CauQuery;

import fv.util.DateUtils;
import fv.util.JdbcConsoleDAO;

public class InsuranceDAO2 extends JdbcConsoleDAO{
	/**
	 * Danh sách tăng 20 tây (Báo cáo Bảo Hiểm ...)
	 * @param month 1-12
	 * @param year
	 * @return
	 */
	public List<Map<String, Object>> getTang20TayList(String dkFact, int month, int year){
		String query = CauQuery.getTang20TayQuery(dkFact, month, year);
		return getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Map<String, Object>>() {
			@Override
			public Map<String, Object> mapRow(ResultSet rs, int rowNum)	throws SQLException {
				Map<String, Object> map = new HashMap<String, Object>();
				int cols = rs.getMetaData().getColumnCount();
				for (int i=0;i<cols;i++){
					String name = rs.getMetaData().getColumnName(i+1);
					map.put(name, rs.getObject(i+1));
				}
				return map;
			}
		});
	}
	/**
	 * Lấy danh sách n_social_infor_report có trạng thái NS-RoRa thang cuoi quy BH tru 1 (T5, T11)
	 * @param month 1-12
	 * @param year
	 * @return
	 */
	public List<String> getNSRoRaT5T11(int month, int year){
		Date thangCuoiQuy = DateUtils.getThangCuoiQuyBHTru1(month, year);
		String sql = "select t.empsn from n_social_infor_report t where t.trangthai=-1 and t.ghichu_trangthai='NS-->RoRa' and t.thoigian=?";
		return getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum)	throws SQLException {
				return rs.getString(1);
			}
		}, new Object[]{new java.sql.Date(thangCuoiQuy.getTime())});
	}
	
	public Map<String, BigDecimal> getThamSo(){
		String sql = "select * from n_thamso";
		List<Map<String, Object>> list = getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<Map<String, Object>>() {
			@Override
			public Map<String, Object> mapRow(ResultSet rs, int rowNum)	throws SQLException {
				Map<String, Object> map = new HashMap<String, Object>();
				int cols = rs.getMetaData().getColumnCount();
				for (int i=0;i<cols;i++){
					String name = rs.getMetaData().getColumnName(i+1);
					map.put(name, rs.getObject(i+1));
				}
				return map;
			}
		});
		Map<String, BigDecimal> r = new HashMap<String, BigDecimal>();
		for (int i=0;i<list.size();i++){
			Map<String, Object> map = list.get(i);
			if (map.get("GIATRI3")!=null){
				r.put((String)map.get("TENTHAMSO"), (BigDecimal)map.get("GIATRI3"));
			}
		}
		return r;
	}
	
	public BigDecimal getLuongBaoGiamBHYT(String empsn, Date date){
		String sql = 
				"select t.luonghd from n_social_infor_report t where t.empsn=? and t.trangthai=1\n" +
					"and t.thoigian=(select max(a.thoigian) from n_social_infor_report a where a.empsn=t.empsn\n" + 
					"and a.trangthai=1 and a.thoigian<?)";
		try{
			return getSimpleJdbcTemplate().queryForObject(sql, BigDecimal.class, empsn, new java.sql.Date(date.getTime()));
		}catch (Exception e){
			//e.printStackTrace();
			// if ke tu thoi diem tang moi toan la RoRa thi luong = luong cua thang tang moi
			// kiem tralai
			String sql1 =
					"select t.luonghd from n_social_infor_report t where t.empsn=?\n" +
						"and t.thoigian=(select max(a.thoigian) from n_social_infor_report a where a.empsn=t.empsn\n" + 
						"and ghichu_trangthai like 'TangMoi%' and a.thoigian<?)";
			try {
				return getSimpleJdbcTemplate().queryForObject(sql1, BigDecimal.class, empsn, new java.sql.Date(date.getTime()));
				
			} catch (Exception e2) {
				// TODO: handle exception
				e.printStackTrace();
				return BigDecimal.ZERO;
			}
		}
	}
}
