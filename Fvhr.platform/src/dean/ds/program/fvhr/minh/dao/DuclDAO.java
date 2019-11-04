package ds.program.fvhr.minh.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import ds.program.fvhr.minh.domain.EmployeeDucls;

public class DuclDAO extends InsurDAO{

	public List<EmployeeDucls> getList(String dk, String fromDate, String toDate)
	{
		String sql ="SELECT dt.empsn,(select b.fname from n_employee b where b.empsn=dt.empsn ) fname" +
				",(select b.lname from n_employee b where b.empsn=dt.empsn ) lname" +
				", dp.id_dept, dp.name_dept,  bonus2_by_date(dt.empsn, to_date(?, 'dd/mm/yyyy')) bonus," +
				" bsaly_by_date(dt.empsn, to_date(?, 'dd/mm/yyyy')) basic_salary, " +
				"comsaly_by_date(dt.empsn, to_date(?, 'dd/mm/yyyy')," +
				"bsaly_by_date(dt.empsn, to_date(?, 'dd/mm/yyyy'))) salary," +
				"get_tsncong_data_daily(dt.empsn,to_date(?,'dd/mm/yyyy'), " +
				"to_date(?,'dd/mm/yyyy')) as ngaycong," +
				"(nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy'), " +
				"to_date(?, 'dd/mm/yyyy'),'NS'), 0)+ " +
				"nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy'), " +
				"to_date(?, 'dd/mm/yyyy'),'NS_RO'), 0)) nghi_san, " +
				"(nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy'), " +
				"to_date(?, 'dd/mm/yyyy'),'PBENH'), 0) +" +
				"nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy'), " +
				"to_date(?, 'dd/mm/yyyy'),'PBAN'), 0)) nghi_co_phep, " +
				"nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy')," +
				"to_date(?, 'dd/mm/yyyy'),'DS'), 0)  nghi_ds," +
				"nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy')," +
				"to_date(?, 'dd/mm/yyyy'),'NBU'), 0) nghi_bu," +
				"nvl(get_restkind_data_daily(dt.empsn, to_date(?, 'dd/mm/yyyy'), " +
				"to_date(?, 'dd/mm/yyyy'),'KC'), 0) khang_cong FROM n_get_data dt, n_department dp " +
				" WHERE dp.id_dept = dt.depsn and dt.depsn <>'00000'  AND dt.months = ? AND dt.years = ? "+ dk;

		int day = getCountdayofmonth(Integer.valueOf(fromDate.substring(3, 5)), Integer.valueOf(fromDate.substring(6)));
		
		ParameterizedRowMapper map = new ParameterizedRowMapper<EmployeeDucls>() {
			@Override
			public EmployeeDucls mapRow(ResultSet rs, int arg1)throws SQLException {
				EmployeeDucls b;
				b= new EmployeeDucls(rs.getString("empsn"), rs.getString("fname"), rs.getString("lname"),
						rs.getString("id_dept"), rs.getString("name_dept"), rs.getDouble("ngaycong"), rs.getDouble("nghi_san"), 
						rs.getDouble("nghi_co_phep"),rs.getDouble("khang_cong"), rs.getDouble("nghi_ds"),
						rs.getDouble("nghi_bu"), 
						rs.getDouble("bonus"),
						rs.getDouble("basic_Salary"), 
						rs.getDouble("salary"));
				return b;
			}
		};
		
		Object[] obj = new Object[]{day+"/"+fromDate.substring(3),day+"/"+fromDate.substring(3),day+"/"+fromDate.substring(3)
				,day+"/"+fromDate.substring(3),
				fromDate,toDate,fromDate,toDate,fromDate,toDate,fromDate,toDate,fromDate,toDate,fromDate,toDate,fromDate,toDate,fromDate,toDate,
				fromDate.substring(3, 5), fromDate.substring(6)};
		List<EmployeeDucls> list = new ArrayList<EmployeeDucls>();
		list = getSimpleJdbcTemplate().query(sql,map,obj);
		return list;
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
}
