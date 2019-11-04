package ds.program.fvhr.dao.hrreport;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import ds.program.fvhr.dao.generic.FvGenericDAO;
import ds.program.fvhr.domain.ATT200000;
import ds.program.fvhr.tien.ui.EMPSNBuilder;
import ds.program.fvhr.tien.ui.EMPSN_E;
import ds.program.fvhr.ui.hrreport.BCTH;
import ds.program.fvhr.ui.hrreport.BCTHBuilder;
import ds.program.fvhr.ui.hrreport.DayOT;
import ds.program.fvhr.ui.hrreport.EmpInforData;
import ds.program.fvhr.ui.hrreport.ICWeekOT;
import ds.program.fvhr.ui.hrreport.MonthOT;
import ds.program.fvhr.ui.hrreport.OTCRTrackDetail;
import ds.program.fvhr.ui.hrreport.OTRSTrackDetail;
import ds.program.fvhr.ui.hrreport.OTSalary;
import ds.program.fvhr.ui.hrreport.OTTrack;
import ds.program.fvhr.ui.hrreport.TTGT;
import ds.program.fvhr.ui.hrreport.WeekOT;
import ds.program.fvhr.ui.hrreport.YearOT;
import ds.program.fvhr.ui.mst.EmpTaxcode;
import dsc.echo2app.Application;
import fv.util.ApplicationHelper;
import fv.util.DbUtils;
import fv.util.JdbcDAO;
import fv.util.Vni2Uni;

public class ReportDao extends JdbcDAO{
	
	public List<BCTH> getListBCTH(final String type, String sql, Object[] params, final Date date1, final Date date2){
		return getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<BCTH>(){
			public BCTH mapRow(ResultSet rs, int rowNum) throws SQLException {
				BCTH data = new BCTH();
				data.setEmpsn(rs.getString("EMPSN"));
				if (type.equals(BCTHBuilder.TYPE1)){
					data.setShift(rs.getString("SHIFT"));
					data.setDate1(rs.getDate("DATES"));
					data.setDate2(null);
					//Add 28/08/2013, HA
					data.settIn(rs.getString("TIN"));
					data.settMid(rs.getString("TMID"));
					data.settOut(rs.getString("TOUT"));
					data.settOver(rs.getString("TOVER"));
					data.setNote(rs.getString("NOTE"));
					data.setttgt(rs.getString("TTGT"));
					// end add					
				}else{
					data.setDate1(date1);
					data.setDate2(date2);
					//Add 28/08/2013, HA
					data.settIn(null);
					data.settMid(null);
					data.settOut(null);
					data.settOver(null);
					data.setNote(null);
					data.setttgt(null);
					// end add						
				}
				data.setDept(Vni2Uni.convertToUnicode(rs.getString("DEPSN")));
				data.setName(Vni2Uni.convertToUnicode(rs.getString("EMPNA")));
				data.setRealOt(rs.getBigDecimal("REAL_OT"));
				data.setPayOt(rs.getBigDecimal("PAY_OT"));
				
				return data;
			}}, params);
	}
	
	public List<DayOT> getListDayOT(Map<String, Object> params){
		String fac = "and d.name_fact='" + params.get("fact") + "'";
		if (params.get("lean")!=null) fac = fac + " and d.name_group='" + params.get("lean") + "'";
		if (params.get("dept")!=null) fac = fac + " and d.name_dept_name='" + params.get("dept") + "'";
		String sql = 
			"select dept_no, count(real_ot) as real_ot from (\n" + 
			"select (select d.id_groupni from n_department d where d.id_dept=e.depsn) as dept_no, t.real_ot\n" + 
			"from n_data_daily t, n_employee e\n" + 
			"where t.empsn=e.empsn and e.depsn in (select d.id_dept from n_department d where e.depsn=d.id_dept\n" + 
			fac + "and d.id_groupni is not null\n" + 
			"and t.dates=? and t.real_ot>3\n" + 
			")) group by dept_no";
		final Date date = (Date) params.get("date");
		return getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<DayOT>(){
			public DayOT mapRow(ResultSet rs, int rowNum) throws SQLException {
				DayOT data = new DayOT();
				data.setFacCode("VL");
				data.setDate(date);
				if (rs.getBigDecimal("REAL_OT")!=null)
					data.setEmpTot(rs.getBigDecimal("REAL_OT").intValue());				if (rs.getBigDecimal("DEPT_NO")!=null)
					data.setDeptNo(rs.getBigDecimal("DEPT_NO").intValue());
				return data;
			}}, new Object[]{new java.sql.Date(date.getTime())});
	}
	
	public List<WeekOT> getListWeekOT(Map<String, Object> params){
		String fac = "d.name_fact='" + params.get("fact") + "'";
		if (params.get("lean")!=null) fac=fac + " and d.name_group='" + params.get("lean") + "'";
		if (params.get("dept")!=null) fac=fac + " and d.name_dept_name='" + params.get("dept") + "'";
		String sql = 
			"select d.id_groupni,count(*) as ts\n" +
			" From n_employee a,n_department d\n" + 
			" where " + fac + " and d.id_groupni is not null\n" + 
			" and a.depsn=d.id_dept\n" + 
			" and a.empsn in ( select l.empsn From N_DATA_DAILY l where l.empsn=a.empsn\n" + 
			" and l.dates between ? and ? \n" + 
			"group by l.empsn\n" + 
			" having (sum(l.nucls + l.ducls)*8) +sum (l.real_ot)  between ? and ? )\n" + 
			" group by d.id_groupni\n" + 
			" Order by d.id_groupni";
		String sql1 = 
			"select d.id_groupni,count(*) as ts\n" +
			" From n_employee a,n_department d\n" + 
			" where " + fac + " and d.id_groupni is not null\n" + 
			" and a.depsn=d.id_dept\n" + 
			" and a.empsn in ( select l.empsn From N_DATA_DAILY l where l.empsn=a.empsn\n" + 
			" and l.dates between ? and ?\n" + 
			"group by l.empsn having SUM(L.OTS)>0)\n" + 
			" group by d.id_groupni\n" + 
			" Order by d.id_groupni";
		String timeNo = (String) params.get("timeNo");
		Date date1 = (Date) params.get("date1");
		Date date2 = (Date) params.get("date2");
		List<Integer[]> listHours = new ArrayList<Integer[]>();
		listHours.add(new Integer[]{0,40});
		listHours.add(new Integer[]{41,48});
		listHours.add(new Integer[]{49,54});
		listHours.add(new Integer[]{55,60});
		listHours.add(new Integer[]{61,1000});
		listHours.add(new Integer[]{61,72});
		listHours.add(new Integer[]{73,84});
		List<WeekOT> list = new ArrayList<WeekOT>();
		for (int i=0;i<7;i++){
			List<WeekOT> listi = getWeekOTList(i, sql, new java.sql.Date(date1.getTime()), new java.sql.Date(date2.getTime()), listHours.get(i)[0], listHours.get(i)[1]);
			for (WeekOT data:listi){
				appendWeekOT(list,data,i);
			}
		}
		List<WeekOT> list8 = getWeekOTList(7, sql1, new java.sql.Date(date1.getTime()), new java.sql.Date(date2.getTime()));
		for (WeekOT data:list8){
			appendWeekOT(list,data,7);
		}
		for (WeekOT data:list){
			data.setFacCode("VL");
			data.setTimeNo(timeNo);
		}
		//sort list
		return list;
	}
	
	private void appendWeekOT(List<WeekOT> list, WeekOT data,int pos) {
		for (int i=0;i<list.size();i++){
			WeekOT w = list.get(i);
			if (w.equals(data)){
				if (pos==0) w.setEmpTot1(data.getEmpTot1());
				else if (pos==1) w.setEmpTot2(data.getEmpTot2());
				else if (pos==2) w.setEmpTot3(data.getEmpTot3());
				else if (pos==3) w.setEmpTot4(data.getEmpTot4());
				else if (pos==4) w.setEmpTot5(data.getEmpTot5());
				else if (pos==5) w.setEmpTot6(data.getEmpTot6());
				else if (pos==6) w.setEmpTot7(data.getEmpTot7());
				else if (pos==7) w.setEmpTot8(data.getEmpTot8());
				return;
			}
		}
		list.add(data);
	}

	private List<WeekOT> getWeekOTList(final int pos, String sql, Object...objects){
		return getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<WeekOT>(){
			public WeekOT mapRow(ResultSet rs, int rowNum) throws SQLException {
				WeekOT data = new WeekOT();
				data.setDeptNo(rs.getBigDecimal("ID_GROUPNI").intValue());
				if (pos==0) data.setEmpTot1(rs.getBigDecimal("TS").intValue());
				else if (pos==1) data.setEmpTot2(rs.getBigDecimal("TS").intValue());
				else if (pos==2) data.setEmpTot3(rs.getBigDecimal("TS").intValue());
				else if (pos==3) data.setEmpTot4(rs.getBigDecimal("TS").intValue());
				else if (pos==4) data.setEmpTot5(rs.getBigDecimal("TS").intValue());
				else if (pos==6) data.setEmpTot6(rs.getBigDecimal("TS").intValue());
				else if (pos==6) data.setEmpTot7(rs.getBigDecimal("TS").intValue());
				else if (pos==7) data.setEmpTot8(rs.getBigDecimal("TS").intValue());
				return data;
			}}, objects);
	}
	
	public List<YearOT> getListYearOT(Map<String, Object> params){
		String fac = "d.name_fact='" + params.get("fact") + "'";
		if (params.get("lean")!=null) fac=fac + " and d.name_group='" + params.get("lean") + "'";
		if (params.get("dept")!=null) fac=fac + " and d.name_dept_name='" + params.get("dept") + "'";
		String sql = 
			"select d.id_groupni,count(*) as ts\n" +
			" From n_employee a,n_department d\n" + 
			" where " + fac + "\n" + 
			" and a.depsn=d.id_dept\n" + 
			" and d.id_groupni is not null\n" + 
			" and a.empsn in ( select l.empsn From N_DATA_DAILY l where l.empsn=a.empsn\n" + 
			" and l.dates between ? and ?\n" + 
			"group by l.empsn\n" + 
			" having (sum(l.real_ot+l.ots+l.oth) between ? and ? ))\n" + 
			" group by d.id_groupni\n" + 
			" Order by d.id_groupni";
		String sql1 = 
			"select d.id_groupni,count(*) as ts\n" +
			" From n_employee a,n_department d\n" + 
			" where " + fac + "\n" + 
			" and a.depsn=d.id_dept\n" + 
			" and d.id_groupni is not null\n" + 
			" and a.empsn in ( select l.empsn From N_DATA_DAILY l where l.empsn=a.empsn\n" + 
			" and l.dates between ? and ?\n" + 
			"group by l.empsn\n" + 
			" having (sum(l.real_ot+l.ots+l.oth) >= 432 ))\n" + 
			" group by d.id_groupni\n" + 
			" Order by d.id_groupni";
		Date date = (Date) params.get("date");
		Date date1 = (Date) params.get("date1");
		Date date2 = (Date) params.get("date2");
		List<Integer[]> listHours = new ArrayList<Integer[]>();
		listHours.add(new Integer[]{0,108});
		listHours.add(new Integer[]{109,216});
		listHours.add(new Integer[]{217,324});
		listHours.add(new Integer[]{325,399});
		listHours.add(new Integer[]{400,431});
		List<YearOT> list = new ArrayList<YearOT>();
		for (int i=0;i<5;i++){
			List<YearOT> listData = getYearOTList(i, sql, new Object[]{new java.sql.Date(date.getTime()), new java.sql.Date(date2.getTime()), listHours.get(i)[0], listHours.get(i)[1]});
			for (YearOT data:listData){
				appendYearOT(list, data, i);
			}
		}
		List<YearOT> list6 = getYearOTList(5, sql1, new Object[]{new java.sql.Date(date.getTime()), new java.sql.Date(date2.getTime())});
		for (YearOT data:list6){
			appendYearOT(list, data, 5);
		}
		for (YearOT data:list){
			data.setFacCode("VL");
			data.setPermitDate(date1);
			data.setReportDate(date2);
		}
		return list;
	}
	
	private void appendYearOT(List<YearOT> list, YearOT data,int pos) {
		for (int i=0;i<list.size();i++){
			YearOT w = list.get(i);
			if (w.equals(data)){
				if (pos==0) w.setEmpTot1(data.getEmpTot1());
				else if (pos==1) w.setEmpTot2(data.getEmpTot2());
				else if (pos==2) w.setEmpTot3(data.getEmpTot3());
				else if (pos==3) w.setEmpTot4(data.getEmpTot4());
				else if (pos==4) w.setEmpTot5(data.getEmpTot5());
				else if (pos==5) w.setEmpTot6(data.getEmpTot6());
				return;
			}
		}
		list.add(data);
	}
	
	private List<YearOT> getYearOTList(final int pos, String sql, Object...objects){
		return getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<YearOT>(){
			public YearOT mapRow(ResultSet rs, int rowNum) throws SQLException {
				YearOT data = new YearOT();
				data.setDeptNo(rs.getBigDecimal("ID_GROUPNI").intValue());
				if (pos==0) data.setEmpTot1(rs.getBigDecimal("TS").intValue());
				else if (pos==1) data.setEmpTot2(rs.getBigDecimal("TS").intValue());
				else if (pos==2) data.setEmpTot3(rs.getBigDecimal("TS").intValue());
				else if (pos==3) data.setEmpTot4(rs.getBigDecimal("TS").intValue());
				else if (pos==4) data.setEmpTot5(rs.getBigDecimal("TS").intValue());
				else if (pos==6) data.setEmpTot6(rs.getBigDecimal("TS").intValue());
				return data;
			}}, objects);
	}
	
	public boolean checkSalaryTable(String month, String year){
		String tableName = "ATT" + year + month;
		return FvGenericDAO.getInstanse().checkTableExist(tableName);
	}
	
	public List<OTSalary> getOTSalaryList(Map<String, Object> params){
		String table = "att" + params.get("year") + params.get("month");
		String fac = "d.name_fact='" + params.get("fact") + "'";
		if (params.get("lean")!=null) fac=fac + " and d.name_group='" + params.get("lean") + "'";
		if (params.get("dept")!=null) fac=fac + " and d.name_dept_name='" + params.get("dept") + "'";
		String cbcn = "";
		if (params.get("CBCN")!=null){
			if ("CN".equals(params.get("CBCN"))){
				cbcn = " and t.possn='CN'";
			}else{
				cbcn = " and t.possn<>'CN'";
			}
		}
		String sql = 
			"select d.id_dept, d.name_fact, d.name_group, d.name_dept_name, count(*) as SN,\n" +
			"round(sum(t.ducls+t.nucls)) as WD, round(sum(t.rest_pay)) as RP, round(sum(t.addcls1+t.naddcls+t.addhol+t.addholn)) as OT\n" + 
			"from " + table + " t, n_department d\n" + 
			"where t.depsn=d.id_dept\n" + 
			"and " + fac + "\n" + cbcn + " " +
			"group by d.id_dept, d.name_fact, d.name_group, d.name_dept_name order by d.name_dept_name";
		return getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<OTSalary>(){
			public OTSalary mapRow(ResultSet rs, int rowNum) throws SQLException {
				OTSalary data = new OTSalary();
				data.setDeptId(rs.getString("ID_DEPT"));
				data.setFact(rs.getString("NAME_FACT"));
				data.setGroup(rs.getString("NAME_GROUP"));
				data.setName(rs.getString("NAME_DEPT_NAME"));
				data.setTotalEmps(rs.getBigDecimal("SN"));
				data.setTotalWorkingDays(rs.getBigDecimal("WD"));
				data.setTotalRestPay(rs.getBigDecimal("RP"));
				data.setTotalOT(rs.getBigDecimal("OT"));
				return data;
			}}, new Object[]{});
	}
	
	public List<OTTrack> getOTTrackList(Map<String, Object> params, boolean isCR){
		String fac = "d.name_fact='" + params.get("fact") + "'";
		if (params.get("lean")!=null) fac=fac + " and d.name_group='" + params.get("lean") + "'";
		if (params.get("dept")!=null) fac=fac + " and d.name_dept_name='" + params.get("dept") + "'";
		Date date1 = (Date) params.get("date1");
		Date date2 = (Date) params.get("date2");
		String sql = 
			"select e.empsn, e.fname||' '||e.lname as full_name, d.name_fact, d.name_group, d.name_dept_name,\n" +
			"e.empcn, (select t.id_shift from n_data_daily t where t.empsn=e.empsn and t.dates=?) as shift\n" + 
			"from n_employee e, n_department d\n" + 
			"where e.depsn=d.id_dept\n" + 
			"and " + fac + "\n" + 
			"order by d.name_dept_name";
		String sql1 = 
			"select t.dates, t.t_out, t.t_over, (t.real_ot+t.ots+t.oth) as otd\n" +
			"from n_data_daily t\n" + 
			"where t.empsn=?\n" + 
			"and t.dates between ? and ? order by t.dates";
		String sql2 = 
			"select t.dates, (t.otd+t.otn+t.ots+t.oth) as ot\n" +
			"from n_data_daily t\n" + 
			"where t.empsn=?\n" + 
			"and t.dates between ? and ?\n" + 
			"order by t.dates";

		List<OTTrack> list = getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<OTTrack>(){

			public OTTrack mapRow(ResultSet rs, int rowNum) throws SQLException {
				OTTrack data = new OTTrack();
				data.setEmpcn(rs.getString("EMPCN"));
				data.setEmpsn(rs.getString("EMPSN"));
				data.setFact(rs.getString("NAME_FACT"));
				data.setGroup(rs.getString("NAME_GROUP"));
				data.setNameDept(rs.getString("NAME_DEPT_NAME"));
				data.setFullName(rs.getString("FULL_NAME"));
				data.setShift(rs.getString("SHIFT"));				
				return data;
			}}, new Object[]{new java.sql.Date(date2.getTime())});
		for (OTTrack data:list){
			if (isCR){
				List<OTCRTrackDetail> details =  getSimpleJdbcTemplate().query(sql1, new ParameterizedRowMapper<OTCRTrackDetail>(){
					public OTCRTrackDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
						OTCRTrackDetail d = new OTCRTrackDetail();
						d.setDate(new Date(rs.getDate("DATES").getTime()));
						d.setOtd(rs.getBigDecimal("OTD"));
						d.setOutTime(rs.getString("T_OUT"));
						d.setOverTime(rs.getString("T_OVER"));
						return d;
					}}, new Object[]{data.getEmpsn(), new java.sql.Date(date1.getTime()), new java.sql.Date(date2.getTime())});
//				data.setDetailsCR(details);
				Map<Date, OTCRTrackDetail> map = new HashMap<Date, OTCRTrackDetail>();
				for (OTCRTrackDetail detail:details){
					map.put(detail.getDate(), detail);
				}
				data.setDetailsCRMap(map);
			}else{
				List<OTRSTrackDetail> details = getSimpleJdbcTemplate().query(sql2, new ParameterizedRowMapper<OTRSTrackDetail>(){
					public OTRSTrackDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
						OTRSTrackDetail d = new OTRSTrackDetail();
						d.setDate(new Date(rs.getDate("DATES").getTime()));
						d.setOt(rs.getBigDecimal("OT"));
						return d;
					}}, new Object[]{data.getEmpsn(), new java.sql.Date(date1.getTime()), new java.sql.Date(date2.getTime())});
//				data.setDetailsRS(details);
				Map<Date, OTRSTrackDetail> map = new HashMap<Date, OTRSTrackDetail>();
				for (OTRSTrackDetail detail:details){
					map.put(detail.getDate(), detail);
				}
				data.setDetailsRSMap(map);
			}
		}
		return list;
	}
	
	public List<MonthOT> getMonthOTTrack(Map<String, Object> params){
		String fac = "d.name_fact='" + params.get("fact") + "'";
		if (params.get("lean")!=null) fac=fac + " and d.name_group='" + params.get("lean") + "'";
		if (params.get("dept")!=null) fac=fac + " and d.name_dept_name='" + params.get("dept") + "'";
		String mm = params.get("month") + "/" + params.get("year");
		String sql = 
			"select e.empsn, e.fname||' '||e.lname as full_name, d.name_fact, d.name_group, d.name_dept_name, e.empcn,\n" +
			"(select sum(t.otd+t.otn+t.ots+t.oth) from n_data_daily t where to_char(t.dates,'mm/yyyy')='" + mm + "' and t.empsn=e.empsn) as ot\n" + 
			"from n_employee e, n_department d\n" + 
			"where e.depsn=d.id_dept\n" + 
			"and " + fac + "\n" + 
			"order by d.name_dept_name";
		return getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<MonthOT>(){
			public MonthOT mapRow(ResultSet rs, int rowNum) throws SQLException {
				MonthOT data = new MonthOT();
				data.setEmpcn(rs.getString("EMPCN"));
				data.setEmpsn(rs.getString("EMPSN"));
				data.setFact(rs.getString("NAME_FACT"));
				data.setGroup(rs.getString("NAME_GROUP"));
				data.setNameDept(rs.getString("NAME_DEPT_NAME"));
				data.setFullName(rs.getString("FULL_NAME"));
				data.setOt(rs.getBigDecimal("OT"));
				return data;
			}}, new Object[]{});
	}
	
	public List<TTGT> getTTGTList(final Map<String, Object> params){
		final Boolean tt = (Boolean) params.get("TT");
		//String pcol = tt?"d.id_group_direct":"d.id_dept";
		String pcol = tt?"gi.id_group":"d.id_dept";
		String ccol = tt?"t.depsn in (select d.id_dept from n_department d where d.id_dept=t.depsn and d.id_group_direct=tbl.id_dept)":"t.depsn=tbl.id_dept";
		//String group = tt?"(select g.description_group from n_groupdirect_indirect g where g.id_group=tbl.id_dept) as name_dept_name"
		//		:"(select d1.name_group from n_department d1 where d1.id_dept=tbl.id_dept) as name_group, (select d1.name_dept_name from n_department d1 where d1.id_dept=tbl.id_dept) as name_dept_name";
		// 08/2013
		String group = tt?"(select g1.description_group from n_group_TTGT g1 where g1.id_group=tbl.id_dept) as name_dept_name"
				:"(select d1.name_group from n_department d1 where d1.id_dept=tbl.id_dept) as name_group, (select d1.name_dept_name from n_department d1 where d1.id_dept=tbl.id_dept) as name_dept_name";		

		// 09/2013
		String nViec = tt?"(get_tsnviec_bygroupdirect_date(?,tbl.id_dept)) as NV":"(get_tsnviec_bydeptdate(?,tbl.id_dept)) as NV";
		
		Date date = (Date) params.get("date");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		java.sql.Date d = new java.sql.Date(date.getTime());
		java.sql.Date d1 = new java.sql.Date(cal.getTimeInMillis());
		final String fact = (String) params.get("fact");
		String fac = "d.name_fact='" + params.get("fact") + "'";
		if (params.get("lean")!=null) fac=fac + " and d.name_group='" + params.get("lean") + "'";
		if (params.get("dept")!=null) fac=fac + " and d.name_dept_name='" + params.get("dept") + "'";
		//sql : dem ca ngay
		String sql = 
				//get_tsnviecttsx_bydate(C.DATES,A.NAME_DEPT_NAME)
				//get_tsnviec_bydate(C.DATES,A.NAME_DEPT_NAME)
			/*						
 			"select tbl.*, ((select count(*) from n_emp_quit t where t.real_off_date=? and t.id_quit_reason in ('5','28')\n" +
			"and " + ccol + ")+\n" + 
			"(select count(*) from n_emp_quit t where t.real_off_date=? and t.id_quit_reason not in ('5','28')\n" + 
			"and " + ccol + ")) as NV\n, " + group + "\n" + 
			*/
			"select tbl.*, "+ nViec +"\n, " + group + "\n " + 
			"from (\n" + 
			"select " + pcol + " as id_dept"+
			//HA Bo sung 29/06/2013 TT_GT_GM_VP, sai rui vi van dem so luong theo hien hanh, ko dem theo data_daily hzaiiii
/*			", sum((select count(*) from n_employee e where e.empsn=t.empsn and e.tructiep_sx='TT')) as TT,\n" +			
			"sum((select count(*) from n_employee e where e.empsn=t.empsn and e.tructiep_sx='GT')) as GT,\n"+
			"sum((select count(*) from n_employee e where e.empsn=t.empsn and e.tructiep_sx='GM')) as GM,\n"+
			"sum((select count(*) from n_employee e where e.empsn=t.empsn and e.tructiep_sx='VP')) as VP,\n"+
*/	
			// HA cap nhat 07/08/2013
			",sum((select count(*) from n_groupdirect_indirect h \n"+
			"	where d.id_group_direct=h.id_detail and h.id_groupkind='TT')) as TT \n"+
			",sum((select count(*) from n_groupdirect_indirect h \n"+
			"	where d.id_group_direct=h.id_detail and h.id_groupkind='GT')) as GT \n"+
			",sum((select count(*) from n_groupdirect_indirect h \n"+
			"	where d.id_group_direct=h.id_detail and h.id_groupkind='GM')) as GM \n"+
			",sum((select count(*) from n_groupdirect_indirect h \n"+
			"	where d.id_group_direct=h.id_detail and h.id_groupkind='VP')) as VP \n"+
			
			",sum((select count(*) from n_employee e where e.empsn=t.empsn and possn_by_date(e.empsn,?)='CN')) as CN,\n" + 
			"sum((select count(*) from n_employee e where e.empsn=t.empsn and possn_by_date(e.empsn,?)<>'CN')) as CB,\n" +
			
/*			"sum((select count(*) from n_employee e where e.empsn=t.empsn and e.position='CN')) as CN,\n" + 
			"sum((select count(*) from n_employee e where e.empsn=t.empsn and e.position<>'CN')) as CB,\n" +			
			"sum((select count(*) from n_employee e where e.empsn=t.empsn and e.date_hired=?)) as CNM,\n" + 
			"sum((select count(*) from n_employee e, n_data_daily a where e.empsn=t.empsn and a.empsn=e.empsn and e.tructiep_sx='TT'\n" + 
			"    and (a.t_in is not null or a.t_mid is not null or a.t_out is not null or a.t_over is not null)\n" + 
			"    and a.dates=?)) as TT1,\n" +			
			"sum((select count(*) from n_employee e, n_data_daily a where e.empsn=t.empsn and a.empsn=e.empsn and e.tructiep_sx='GT'\n" + 
			"    and (a.t_in is not null or a.t_mid is not null or a.t_out is not null or a.t_over is not null)\n" + 
			"    and a.dates=?)) as GT1,\n" + 
			"sum((select count(*) from n_employee e, n_data_daily a where e.empsn=t.empsn and a.empsn=e.empsn and e.tructiep_sx='GM'\n" + 
			"    and (a.t_in is not null or a.t_mid is not null or a.t_out is not null or a.t_over is not null)\n" + 
			"    and a.dates=?)) as GM1,\n" +			
			"sum((select count(*) from n_employee e, n_data_daily a where e.empsn=t.empsn and a.empsn=e.empsn and e.tructiep_sx='VP'\n" + 
			"    and (a.t_in is not null or a.t_mid is not null or a.t_out is not null or a.t_over is not null)\n" + 
			"    and a.dates=?)) as VP1,\n" +  			
			//end HA
			"sum((select count(*) from n_employee e, n_data_daily a where e.empsn=t.empsn and a.empsn=e.empsn and e.position<>'CN'\n" + 
			"    and (a.t_in is not null or a.t_mid is not null or a.t_out is not null or a.t_over is not null)\n" + 
			"    and a.dates=?)) as CB1,\n" + 
			"sum((select count(*) from n_employee e, n_data_daily a where e.empsn=t.empsn and a.empsn=e.empsn and e.position='CN'\n" + 
			"    and (a.t_in is not null or a.t_mid is not null or a.t_out is not null or a.t_over is not null)\n" + 
			"    and a.dates=?)) as CN1,\n" + 
*/
	
			"sum((select count(*) from n_employee e where e.empsn=t.empsn and e.date_hired=?)) as CNM,\n" + 
			"sum((select count(*) from n_groupdirect_indirect h \n"+
			"	where d.id_group_direct=h.id_detail and h.id_groupkind='TT'\n" + 
			"    and (f.t_in is not null or f.t_mid is not null or f.t_out is not null or f.t_over is not null))) as TT1,\n" +
			"sum((select count(*) from n_groupdirect_indirect h \n"+
			"	where d.id_group_direct=h.id_Detail and h.id_groupkind='GT'\n" + 
			"    and (f.t_in is not null or f.t_mid is not null or f.t_out is not null or f.t_over is not null))) as GT1,\n" +			
			"sum((select count(*) from n_groupdirect_indirect h \n"+
			"	where d.id_group_direct=h.id_Detail and h.id_groupkind='GM'\n" + 
			"    and (f.t_in is not null or f.t_mid is not null or f.t_out is not null or f.t_over is not null))) as GM1,\n" +
			"sum((select count(*) from n_groupdirect_indirect h \n"+
			"	where d.id_group_direct=h.id_Detail and h.id_groupkind='VP'\n" + 
			"    and (f.t_in is not null or f.t_mid is not null or f.t_out is not null or f.t_over is not null))) as VP1,\n" +		

			"sum((select count(*) from n_employee e where e.empsn=t.empsn and possn_by_date(e.empsn,?)<>'CN'\n" + 
			"    and (f.t_in is not null or f.t_mid is not null or f.t_out is not null or f.t_over is not null))) as CB1,\n" +
			
			"sum((select count(*) from n_employee e where e.empsn=t.empsn and possn_by_date(e.empsn,?)='CN'\n" + 
			"    and (f.t_in is not null or f.t_mid is not null or f.t_out is not null or f.t_over is not null))) as CN1,\n" +			
			
			"sum((select count(*) From n_rest_kind e where f.rest_rs=e.name_rest and e.id_rest_sal ='C01' and f.rest_qtt=1)) as NCL,\n" +
			"sum((select count(*) From n_rest_kind e where f.rest_rs=e.name_rest and e.id_rest_sal in ('C02','C03','C04')\n"+
			" and f.rest_qtt=1)) as NKL,\n" + 			
			"sum((select count(*) From n_rest_kind e where f.rest_rs=e.name_rest and e.id_rest_sal ='C07' and f.rest_qtt=1)) as NS,\n" +
			"sum((select count(*) From n_rest_kind e where f.rest_rs=e.name_rest and e.id_rest_sal ='C05' and f.rest_qtt=1)) as NKC\n" +
			
			//HA bo sung null data 18/04/2013
			", sum((select count(*) from n_employee e, n_data_daily a where e.empsn=t.empsn and a.empsn=e.empsn and a.rest_qtt=0\n" + 
			"    and a.t_in is null and a.t_mid is null and a.t_out is null and a.t_over is null\n" + 
			"    and a.dates=?)) as NULL1\n" +
			"from n_department d, n_employee t, n_data_daily f ,n_groupdirect_indirect gi,n_group_TTGT g \n" +
			// lay don vi theo data_daily moi dung
			//het HA
			"where f.depsn=d.id_dept and t.empsn=f.empsn and d.id_group_direct=gi.id_detail and gi.id_group=g.id_group \n"+
			" and " + fac + "\n"+
			//and check_cadem_data_daily(a.empsn,a.dates)=0  // count ca ngay = ngay bao cao, ca dem = ngay bao cao -1
			(tt?"and d.id_group_direct is not null\n":"") + 
			"and t.date_hired<=? and f.dates=? \n"+
			"group by " + pcol + ") tbl";		
		System.out.println(sql);
		
		return getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<TTGT>(){

			public TTGT mapRow(ResultSet rs, int rowNum) throws SQLException {
				TTGT data = new TTGT();
				data.setDeptId(rs.getString("ID_DEPT"));
				if (!tt) data.setGroup(rs.getString("NAME_GROUP"));
				data.setDeptName(rs.getString("NAME_DEPT_NAME"));
				data.setFact(fact);
				data.setTotalTT(rs.getInt("TT"));
				//HA
				data.setTotalGT(rs.getInt("GT"));
				data.setTotalGM(rs.getInt("GM"));
				data.setTotalVP(rs.getInt("VP"));				
				//end HA
				data.setTotalCB(rs.getInt("CB"));
				data.setTotalCN(rs.getInt("CN"));
				data.setRealCB(rs.getInt("CB1"));
				data.setRealCN(rs.getInt("CN1"));
				data.setRealTT(rs.getInt("TT1"));
				//HA
				data.setRealGT(rs.getInt("GT1"));
				data.setRealGM(rs.getInt("GM1"));
				data.setRealVP(rs.getInt("VP1"));				
				//end HA				
				data.setTotalNewWKR(rs.getInt("CNM"));
				data.setTotalMaternity(rs.getInt("NS"));
				data.setTotalLeave(rs.getInt("NV"));
				data.setTotalNWHour(rs.getInt("NKC"));
				data.setTotalRestPay(rs.getInt("NCL"));
				data.setTotalRest(rs.getInt("NKL")+rs.getInt("NS"));
				data.setTotal(data.getTotalCB()+data.getTotalCN());
				data.setRealTotal(data.getRealCB()+data.getRealCN());
				//data.setTotalGT(data.getTotal()-data.getTotalTT());
				//data.setRealGT(data.getRealTotal()-data.getRealTT());
				if (data.getTotal()!=0){
					data.setScale1((float)data.getRealTotal()/data.getTotal());
					data.setScale3((float)data.getTotalTT()/data.getTotal());
					data.setScale4((float)data.getTotalGT()/data.getTotal());
					//HA
					data.setScale5((float)data.getTotalGM()/data.getTotal());
					data.setScale6((float)data.getTotalVP()/data.getTotal());
					//end HA
				}else{
					data.setScale1(0);
					data.setScale3(0);
					data.setScale4(0);
					//HA
					data.setScale5(0);					
					data.setScale6(0);
					//end HA
				}
				if (data.getTotalTT()!=0)
					data.setScale2((float)data.getRealTT()/data.getTotalTT());
				else
					data.setScale2(0);
				
				data.setTotalNull(rs.getInt("NULL1"));
				return data;				
			//}}, new Object[]{d,d1,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d});
			//}}, new Object[]{d,d1,d,d,d,d,d,d,d,d});
			//BViec va XThoi co cung 1 ngay thuc nghi la ngay ko con den cty
			/*
	          23/09/2013 Thay doi ngay thuc nghi doi voi CNV dang xin thoi
	          <23/09/2013 :
	                XThoi: ngay thuc nghi la ngay cuoi cung den cty
	                BViec: ngay thuc nghi la ngay ko con den cty
	                nhap XNKT cho XTHoi ngay thuc nghi
	          >=23/09/2013 :
	                XThoi= BViec: ngay thuc nghi la ngay ko con den cty
	                Nhap XNKT cho XThoi ngay thuc nghi-1
	          de ho tro cho bao cao hang ngay cho Dai Loan			 
			 */
			
			//}}, new Object[]{d,d,d,d,d,d,d,d,d,d});				
			}}, new Object[]{d,d,d,d,d,d,d,d,d});				
	}
	
	// phan biet ca ngay va dem 08/2013
	public List<TTGT> getTTGTListNew(final Map<String, Object> params){
		final Boolean tt = (Boolean) params.get("TT");
		String pcol = tt?"gi.id_group":"d.id_dept";
		String ccol = tt?"t.depsn in (select d.id_dept from n_department d where d.id_dept=t.depsn and d.id_group_direct=tbl.id_dept)":"t.depsn=tbl.id_dept";
		String group = tt?"(select g1.description_group from n_group_TTGT g1 where g1.id_group=tbl.id_dept) as name_dept_name"
				:"(select d1.name_group from n_department d1 where d1.id_dept=tbl.id_dept) as name_group, (select d1.name_dept_name from n_department d1 where d1.id_dept=tbl.id_dept) as name_dept_name";		
		// count ca dem = ngay bao cao -1, count ca ngay = ngay bao cao
		String caLamViec	= " check_cadem_data_daily(a.empsn,a.dates)=? " ;		
		
		Date date = (Date) params.get("date");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		java.sql.Date d = new java.sql.Date(date.getTime());
		java.sql.Date d1 = new java.sql.Date(cal.getTimeInMillis());
		final String fact = (String) params.get("fact");
		String fac = "d.name_fact='" + params.get("fact") + "'";
		if (params.get("lean")!=null) fac=fac + " and d.name_group='" + params.get("lean") + "'";
		if (params.get("dept")!=null) fac=fac + " and d.name_dept_name='" + params.get("dept") + "'";
		//sql : dem ca ngay
		String sql = 
			"select tbl.*, ((select count(*) from n_emp_quit t where t.real_off_date=? and t.id_quit_reason in (5,8)\n" +
			"and " + ccol + ")+\n" + 
			"(select count(*) from n_emp_quit t where t.real_off_date=? and t.id_quit_reason not in (5,8)\n" + 
			"and " + ccol + ")) as NV\n, " + group + "\n" + 
			"from (\n" + 
			"select " + pcol + " as id_dept"+
			",sum((select count(*) from n_groupdirect_indirect h \n"+
			"	where d.id_group_direct=h.id_detail and h.id_groupkind='TT')) as TT \n"+
			",sum((select count(*) from n_groupdirect_indirect h \n"+
			"	where d.id_group_direct=h.id_detail and h.id_groupkind='GT')) as GT \n"+
			",sum((select count(*) from n_groupdirect_indirect h \n"+
			"	where d.id_group_direct=h.id_detail and h.id_groupkind='GM')) as GM \n"+
			",sum((select count(*) from n_groupdirect_indirect h \n"+
			"	where d.id_group_direct=h.id_detail and h.id_groupkind='VP')) as VP \n"+			
			",sum((select count(*) from n_employee e where e.empsn=t.empsn and possn_by_date(e.empsn,?)='CN')) as CN,\n" + 
			"sum((select count(*) from n_employee e where e.empsn=t.empsn and possn_by_date(e.empsn,?)<>'CN')) as CB,\n" +

			"sum((select count(*) from n_employee e where e.empsn=t.empsn and e.date_hired=?)) as CNM,\n" + 
			"sum((select count(*) from n_groupdirect_indirect h \n"+
			"	where d.id_group_direct=h.id_detail and h.id_groupkind='TT'\n" + 
			"    and (f.t_in is not null or f.t_mid is not null or f.t_out is not null or f.t_over is not null))) as TT1,\n" +
			"sum((select count(*) from n_groupdirect_indirect h \n"+
			"	where d.id_group_direct=h.id_Detail and h.id_groupkind='GT'\n" + 
			"    and (f.t_in is not null or f.t_mid is not null or f.t_out is not null or f.t_over is not null))) as GT1,\n" +			
			"sum((select count(*) from n_groupdirect_indirect h \n"+
			"	where d.id_group_direct=h.id_Detail and h.id_groupkind='GM'\n" + 
			"    and (f.t_in is not null or f.t_mid is not null or f.t_out is not null or f.t_over is not null))) as GM1,\n" +
			"sum((select count(*) from n_groupdirect_indirect h \n"+
			"	where d.id_group_direct=h.id_Detail and h.id_groupkind='VP'\n" + 
			"    and (f.t_in is not null or f.t_mid is not null or f.t_out is not null or f.t_over is not null))) as VP1,\n" +		

			"sum((select count(*) from n_employee e where e.empsn=t.empsn and possn_by_date(e.empsn,?)<>'CN'\n" + 
			"    and (f.t_in is not null or f.t_mid is not null or f.t_out is not null or f.t_over is not null))) as CB1,\n" +
			
			"sum((select count(*) from n_employee e where e.empsn=t.empsn and possn_by_date(e.empsn,?)='CN'\n" + 
			"    and (f.t_in is not null or f.t_mid is not null or f.t_out is not null or f.t_over is not null))) as CN1,\n" +			
			
			"sum((select count(*) From n_rest_kind e where f.rest_rs=e.name_rest and e.id_rest_sal ='C01' and f.rest_qtt=1)) as NCL,\n" +
			"sum((select count(*) From n_rest_kind e where f.rest_rs=e.name_rest and e.id_rest_sal in ('C02','C03','C04')\n"+
			" and f.rest_qtt=1)) as NKL,\n" + 			
			"sum((select count(*) From n_rest_kind e where f.rest_rs=e.name_rest and e.id_rest_sal ='C07' and f.rest_qtt=1)) as NS,\n" +
			"sum((select count(*) From n_rest_kind e where f.rest_rs=e.name_rest and e.id_rest_sal ='C05' and f.rest_qtt=1)) as NKC\n" +

			", sum((select count(*) from n_employee e, n_data_daily a where e.empsn=t.empsn and a.empsn=e.empsn and a.rest_qtt=0\n" + 
			"    and a.t_in is null and a.t_mid is null and a.t_out is null and a.t_over is null\n" + 
			"    and a.dates=?)) as NULL1\n" +
			"from n_department d, n_employee t, n_data_daily f ,n_groupdirect_indirect gi,n_group_TTGT g \n" +
			// lay don vi theo data_daily moi dung
			"where f.depsn=d.id_dept and t.empsn=f.empsn and d.id_group_direct=gi.id_detail and gi.id_group=g.id_group \n"+
			" and " + fac + "\n"+
			(tt?"and d.id_group_direct is not null\n":"") + 
			"and t.date_hired<=? and f.dates=? \n"+
			" and check_cadem_data_daily(a.empsn,a.dates)=? \n"+			
			"group by " + pcol + ") tbl";	
		
		System.out.println(sql);
		
		List<TTGT> list = getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<TTGT>(){

			public TTGT mapRow(ResultSet rs, int rowNum) throws SQLException {
				TTGT data = new TTGT();
				data.setDeptId(rs.getString("ID_DEPT"));
				if (!tt) data.setGroup(rs.getString("NAME_GROUP"));
				data.setDeptName(rs.getString("NAME_DEPT_NAME"));
				data.setFact(fact);
				data.setTotalTT(rs.getInt("TT"));
				//HA
				data.setTotalGT(rs.getInt("GT"));
				data.setTotalGM(rs.getInt("GM"));
				data.setTotalVP(rs.getInt("VP"));				
				//end HA
				data.setTotalCB(rs.getInt("CB"));
				data.setTotalCN(rs.getInt("CN"));
				data.setRealCB(rs.getInt("CB1"));
				data.setRealCN(rs.getInt("CN1"));
				data.setRealTT(rs.getInt("TT1"));
				//HA
				data.setRealGT(rs.getInt("GT1"));
				data.setRealGM(rs.getInt("GM1"));
				data.setRealVP(rs.getInt("VP1"));				
				//end HA				
				data.setTotalNewWKR(rs.getInt("CNM"));
				data.setTotalMaternity(rs.getInt("NS"));
				data.setTotalLeave(rs.getInt("NV"));
				data.setTotalNWHour(rs.getInt("NKC"));
				data.setTotalRestPay(rs.getInt("NCL"));
				data.setTotalRest(rs.getInt("NKL")+rs.getInt("NS"));
				data.setTotal(data.getTotalCB()+data.getTotalCN());
				data.setRealTotal(data.getRealCB()+data.getRealCN());
				if (data.getTotal()!=0){
					data.setScale1((float)data.getRealTotal()/data.getTotal());
					data.setScale3((float)data.getTotalTT()/data.getTotal());
					data.setScale4((float)data.getTotalGT()/data.getTotal());
					data.setScale5((float)data.getTotalGM()/data.getTotal());
					data.setScale6((float)data.getTotalVP()/data.getTotal());
				}else{
					data.setScale1(0);
					data.setScale3(0);
					data.setScale4(0);
					data.setScale5(0);					
					data.setScale6(0);
				}
				if (data.getTotalTT()!=0)
					data.setScale2((float)data.getRealTT()/data.getTotalTT());
				else
					data.setScale2(0);
				
				data.setTotalNull(rs.getInt("NULL1"));
				return data;
			//}}, new Object[]{d,d1,d,d,d,d,d,d,d,d,0});//dem ca ngay
			}}, new Object[]{d,d,d,d,d,d,d,d,d,d,0});//dem ca ngay, doi BV=XT= ngay thuc nghi la ngay ko con den cty
		return list;
/*		
		//list ca dem		
		List<TTGT> listCaDem =  getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<TTGT>(){
				public TTGT mapRow(ResultSet rs, int rowNum) throws SQLException {
					TTGT data1 = new TTGT();
					data1.setDeptId(rs.getString("ID_DEPT"));
					if (!tt) data1.setGroup(rs.getString("NAME_GROUP"));
					data1.setDeptName(rs.getString("NAME_DEPT_NAME"));
					data1.setFact(fact);
					data1.setTotalTT(rs.getInt("TT"));
					//HA
					data1.setTotalGT(rs.getInt("GT"));
					data1.setTotalGM(rs.getInt("GM"));
					data1.setTotalVP(rs.getInt("VP"));				
					//end HA
					data1.setTotalCB(rs.getInt("CB"));
					data1.setTotalCN(rs.getInt("CN"));
					data1.setRealCB(rs.getInt("CB1"));
					data1.setRealCN(rs.getInt("CN1"));
					data1.setRealTT(rs.getInt("TT1"));
					//HA
					data1.setRealGT(rs.getInt("GT1"));
					data1.setRealGM(rs.getInt("GM1"));
					data1.setRealVP(rs.getInt("VP1"));				
					//end HA				
					data1.setTotalNewWKR(rs.getInt("CNM"));
					data1.setTotalMaternity(rs.getInt("NS"));
					data1.setTotalLeave(rs.getInt("NV"));
					data1.setTotalNWHour(rs.getInt("NKC"));
					data1.setTotalRestPay(rs.getInt("NCL"));
					data1.setTotalRest(rs.getInt("NKL")+rs.getInt("NS"));
					data1.setTotal(data1.getTotalCB()+data1.getTotalCN());
					data1.setRealTotal(data1.getRealCB()+data1.getRealCN());
					if (data1.getTotal()!=0){
						data1.setScale1((float)data1.getRealTotal()/data1.getTotal());
						data1.setScale3((float)data1.getTotalTT()/data1.getTotal());
						data1.setScale4((float)data1.getTotalGT()/data1.getTotal());
						data1.setScale5((float)data1.getTotalGM()/data1.getTotal());
						data1.setScale6((float)data1.getTotalVP()/data1.getTotal());
					}else{
						data1.setScale1(0);
						data1.setScale3(0);
						data1.setScale4(0);
						data1.setScale5(0);					
						data1.setScale6(0);
					}
					if (data1.getTotalTT()!=0)
						data1.setScale2((float)data1.getRealTT()/data1.getTotalTT());
					else
						data1.setScale2(0);
					
					data1.setTotalNull(rs.getInt("NULL1"));
					return data1;
				}}, new Object[]{d,d1,d,d,d,d,d,d,d1,d1,1});//dem ca dem
		*/
				
	}	
	// end 08/2013
	
	public List<ICWeekOT> getICWeekOTList(Map<String, Object> params){
		String fac = "d.name_fact='" + params.get("fact") + "'";
		if (params.get("lean")!=null) fac=fac + " and d.name_group='" + params.get("lean") + "'";
		if (params.get("dept")!=null) fac=fac + " and d.name_dept_name='" + params.get("dept") + "'";
		if (params.get("groupni")!=null) fac = fac + " and d.id_groupni=" + params.get("groupni");
		String hstr = "";
		if (params.get("hour1")!=null&&params.get("hour2")!=null){
			Integer h1 = (Integer) params.get("hour1");
			Integer h2 = (Integer) params.get("hour2");
			hstr = "having sum((t.ducls+t.nucls)*8) + sum(t.real_ot) between " + h1 + " and " + h2 + " ";
		}else{
			hstr = "having sum(t.ots)>0";
		}
		Date date1 = (Date) params.get("date1");
		Date date2 = (Date) params.get("date2");
		String sql = 
			"select d1.name_dept, n.id_groupni, n.name_nike, tbl.empsn, tbl.sum_hour_week, tbl.sum_sunday\n" +
			"from n_employee e1, n_department d1, n_group_dept_nike n,(\n" + 
			"select t.empsn, sum((t.ducls+t.nucls)*8) + sum(t.real_ot) as sum_hour_week, sum(t.ots) as sum_sunday\n" + 
			"from n_data_daily t, n_employee e\n" + 
			"where t.empsn=e.empsn and e.depsn in (select d.id_dept from n_department d where d.id_dept=e.depsn and " + fac + ")\n" + 
			"and t.dates between ? and ? \n" + 
			"group by t.empsn\n" + hstr + ") tbl\n" + 
			"where e1.empsn=tbl.empsn and e1.depsn=d1.id_dept and d1.id_groupni=n.id_groupni order by d1.name_dept";

		return getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<ICWeekOT>(){
			public ICWeekOT mapRow(ResultSet rs, int rowNum) throws SQLException {
				ICWeekOT data = new ICWeekOT();
				data.setDept(rs.getString("NAME_DEPT"));
				data.setGroupNike(rs.getInt("ID_GROUPNI"));
				data.setGroupName(rs.getString("NAME_NIKE"));
				data.setEmpsn(rs.getString("EMPSN"));
				data.setTotalHours(rs.getBigDecimal("SUM_HOUR_WEEK"));
				data.setTotalSundayHours(rs.getBigDecimal("SUM_SUNDAY"));
				return data;
			}}, new Object[]{new java.sql.Date(date1.getTime()), new java.sql.Date(date2.getTime())});
	}
	
	public List<EmpTaxcode> getEmpTaxcodeList(String fact, Object lean, Object dept){
		String fac = "d.name_fact='" + fact + "'";
		if (lean!=null&&!lean.equals("")){
			fac = fac + " and d.name_group='" + lean + "'";
		}
		if (dept!=null&&!dept.equals("")){
			fac = fac + " and d.name_dept_name='" + dept + "'";
		}
		String sql = 
			"select t.empsn, e.fname||' '||e.lname as full_name, d1.name_dept, t.code_tax, t.dates, t.note\n" +
			"from n_emp_taxcode t, n_employee e, n_department d1\n" + 
			"where t.empsn=e.empsn and e.depsn=d1.id_dept and e.depsn in\n" + 
			"(select d.id_dept from n_department d where e.depsn=d.id_dept and " + fac + ")\n" + 
			"order by d1.name_dept";
		return getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<EmpTaxcode>(){
			public EmpTaxcode mapRow(ResultSet rs, int rowNum) throws SQLException {
				EmpTaxcode data = new EmpTaxcode();
				if (rs.getDate("DATES")!=null)
					data.setDate(new Date(rs.getDate("DATES").getTime()));
				data.setDept(rs.getString("NAME_DEPT"));
				data.setEmpsn(rs.getString("EMPSN"));
				data.setFullName(rs.getString("FULL_NAME"));
				data.setNote(rs.getString("NOTE"));
				data.setTaxCode(rs.getString("CODE_TAX"));
				return data;
			}}, new Object[]{});
	}
	
	public List<ATT200000> getSalaryList(Map<String, Object> params){
		String sql = "select * from att"+params.get("year")+params.get("month") + " t where";
		List<Object> list = new ArrayList<Object>();
		if (params.get("opt1")==null&&params.get("opt2")==null){
			if (params.get("fact")!=null) {
				sql = sql + " and t.depsn in (select d.id_dept from n_department d where t.depsn=d.id_dept and d.name_fact=?)";
				list.add(params.get("fact"));
			}
			if (params.get("dept")!=null){
				sql = sql + " and t.depsn=?";
				list.add(params.get("dept"));
			}			
		}else{
			if (params.get("opt1")!=null){
				sql = sql + " and (t.depsn in (select d.id_dept from n_department d where t.depsn=d.id_dept and d.name_fact='FVLS') or t.depsn='TB020')";
			}else{
				sql = sql + " and (t.depsn in (select d.id_dept from n_department d where t.depsn=d.id_dept and d.name_fact in ('FVL','KDAO')) or t.depsn in ('TB019','00001','00002','00003'))";
			}
		}
		
		if (params.get("atm")!=null){
			sql = sql + " and t.possn like ?";
			list.add("%_"+params.get("atm"));
		}
		
		sql = StringUtils.substringBefore(sql, " and")+StringUtils.substringAfter(sql, "and");
		System.out.println(sql);
		return getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<ATT200000>(){

			public ATT200000 mapRow(ResultSet rs, int rowNum) throws SQLException {
				ATT200000 data = new ATT200000();
				Field[] fields = ATT200000.class.getDeclaredFields();
				for (Field field:fields){
					String fname=field.getName();					
					if (fname.equals("TTS")){
						fname="TS";
					}
					try {
						System.out.println(fname);
						PropertyUtils.setProperty(data, field.getName(), rs.getObject(fname));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
				return data;
			}			
		}, list.toArray());
	}
	//HA, vua lay TT CNV moi theo ngay NX, or lay theo hien hanh
	public List<EmpInforData> getListCNV(Map<String, Object> params){
		/// them cbb vao doi voi user thuoc nhom P06---------------------------
		String fac = "and d.name_fact='" + params.get("fact") + "'";
		if (params.get("lean")!=null) fac = fac + " and d.name_group='" + params.get("lean") + "'";
		if (params.get("dept")!=null) fac = fac + " and d.name_dept_name='" + params.get("dept") + "'";
		String sql = 
        "select d.name_fact,d.name_group,d.name_dept_name,a.empsn,a.empcn,a.fname,a.lname\n"+
        ",a.date_hired,a.sex,a.birthday,a.education,a.id_no,a.ngaycap_id,a.id_place\n"+
        ",a.permanent_address,a.birthplace,a.ethnic,a.religion,a.ngaynx_moi\n"+
        //them vao 19/11/2012
        ",a.depsn,a.position, a.user_manage_id,a.tructiep_sx\n"+
/*        
        ",nvl((select b.bsalary from n_basic_salary b\n"+
        " where a.empsn=b.empsn and b.keys=1),0) LuongCB\n"+        
        " ,nvl((select c.money from n_bonus_poss c\n"+
        " where a.empsn=c.empsn and c.keys=1),0) PCCVu\n"+
        */
        ", bsaly_by_date(a.empsn,"+params.get("ngayCuoi")+") LuongCB\n"+
        ", comsaly_by_date(a.empsn,"+params.get("ngayCuoi")+"\n"+
        " ,bsaly_by_date(a.empsn,"+params.get("ngayCuoi")+")) LuongHD\n"+
        ", bonus2_by_date(a.empsn,"+params.get("ngayCuoi")+") PCCVu\n"+        
        ",nvl((select d.bonus_nghe from n_newworker_test d where a.empsn=d.empsn),0) PCNghe\n"+
        //het them vao 19/11/2012
        " From n_employee a, n_department d\n"+
		" where a.depsn=d.id_dept "+fac+"\n"+
        // kiem tra vung quan ly
        " and a.user_manage_id in " + DbUtils.parseInStringParamValuesM(ApplicationHelper.getRightList()) + " ";
		
		
        if (params.get("tuNgay")!=null && params.get("denNgay")!=null){
        	sql=sql+"\n and a.date_hired between "+params.get("tuNgay")+"\n"+
        			" and "+params.get("denNgay");
        }       
		
		//System.out.println(sql);
		
		//final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<EmpInforData>(){
			public EmpInforData mapRow(ResultSet rs, int rowNum) throws SQLException {
				EmpInforData data = new EmpInforData();	
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				//bat dau gan data
				data.setNameFact(rs.getString("NAME_FACT"));
				data.setNameGroup(Vni2Uni.convertToUnicode(rs.getString("NAME_GROUP")));
				data.setNameDeptName(Vni2Uni.convertToUnicode(rs.getString("NAME_DEPT_NAME")));
				data.setEmpsn(rs.getString("EMPSN"));
				data.setEmpcn(rs.getString("EMPCN"));
				data.setFname(Vni2Uni.convertToUnicode(rs.getString("FNAME")));
				data.setLname(Vni2Uni.convertToUnicode(rs.getString("LNAME")));
				//data.setNgayNX(rs.getString("DATE_HIRED"));
				String temp =sdf.format(rs.getDate("DATE_HIRED").getTime());
				data.setNgayNX(temp);
				data.setGioiTinh(rs.getString("SEX"));
				data.setNgaySinh(rs.getString("BIRTHDAY"));
				data.setTrinhDo(rs.getString("EDUCATION"));
				data.setCmnd(rs.getString("ID_NO"));
				if (rs.getDate("NGAYCAP_ID")!=null){
					temp =sdf.format(rs.getDate("NGAYCAP_ID").getTime());
					data.setNgayCapID(temp);
				}else data.setNgayCapID(null);
				data.setNoiCapID(Vni2Uni.convertToUnicode(rs.getString("ID_PLACE")));
				data.setHoKhau(Vni2Uni.convertToUnicode(rs.getString("PERMANENT_ADDRESS")));
				data.setNoiSinh(Vni2Uni.convertToUnicode(rs.getString("BIRTHPLACE")));
				data.setDanToc(Vni2Uni.convertToUnicode(rs.getString("ETHNIC")));
				data.setTonGiao(Vni2Uni.convertToUnicode(rs.getString("RELIGION")));
				if (rs.getDate("NGAYNX_MOI")!=null){
					temp =sdf.format(rs.getDate("NGAYNX_MOI").getTime());
					data.setNgayNXMoi(temp);
				}else data.setNgayNXMoi(null);
				//data.setNgayNXMoi(sdf.format(rs.getDate("NGAYNX_MOI").getTime()));
				data.setDonVi(rs.getString("DEPSN"));
				data.setChucVu(rs.getString("POSITION"));				
				data.setVungQL(rs.getString("USER_MANAGE_ID"));
				data.setTtSX(rs.getString("TRUCTIEP_SX"));
				data.setLuongCB(rs.getInt("LUONGCB"));
				data.setLuongHD(rs.getInt("LUONGHD"));
				data.setPccvu(rs.getInt("PCCVU"));
				data.setPcNghe(rs.getInt("PCNGHE"));
				return data;
			}});
			//}}, new Object[]{new java.sql.Date(date.getTime())});
	}	
	//end HA
	
	public List<EMPSN_E> getListempsn(final String type, String sql, Object[] params)
	{
		return getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<EMPSN_E>() {
			public EMPSN_E mapRow(ResultSet rs, int rowNum) throws SQLException{
				
				EMPSN_E emp=new EMPSN_E();
				if(type.equals(EMPSNBuilder.TYPE1))
				{
					emp.setEmpsn(rs.getString("empsn"));
					emp.setEmpcn(rs.getString("empcn"));
					emp.setName(Vni2Uni.convertToUnicode(rs.getString("empna")));
					emp.setId_dept(Vni2Uni.convertToUnicode(rs.getString("id_dept")));
					emp.setName_dept_name_new(Vni2Uni.convertToUnicode(rs.getString("depsn_new")));
					
					emp.setCode(rs.getString("code"));
					emp.setShift(rs.getString("SHIFT"));
					emp.setSex(rs.getString("SEX"));
					emp.setName_dept_name_old(Vni2Uni.convertToUnicode(rs.getString("depsn_OLD")));
				}
				
				if(type.equals(EMPSNBuilder.TYPE2))
				{
					emp.setEmpsn(rs.getString("empsn"));
					emp.setEmpcn(rs.getString("empcn"));
					emp.setName(Vni2Uni.convertToUnicode(rs.getString("empna")));
					emp.setId_dept(Vni2Uni.convertToUnicode(rs.getString("id_dept")));
					emp.setName_dept_name_new(Vni2Uni.convertToUnicode(rs.getString("depsn_new")));
					
					emp.setCode(rs.getString("code"));
					emp.setShift(rs.getString("SHIFT"));
					emp.setSex(rs.getString("SEX"));					
				}
				return emp;
			}
			
		},params);
	}
	public List<EMPSN_E> getListempsnditre(final String type, String sql, Object[] params)
	{
		final SimpleDateFormat sp=new SimpleDateFormat("dd/MM/yyyy");
		return getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<EMPSN_E>() {
			public EMPSN_E mapRow(ResultSet rs, int rowNum) throws SQLException{
				
				EMPSN_E emp=new EMPSN_E();
				if(type.equals(EMPSNBuilder.TYPE3))
				{
					emp.setEmpsn(rs.getString("empsn"));					
					emp.setName(Vni2Uni.convertToUnicode(rs.getString("empna")));					
					emp.setName_dept_name_new(Vni2Uni.convertToUnicode(rs.getString("depsn_new")));					
					emp.setDate(sp.format(rs.getDate("dates")));
					emp.setLate(rs.getString("late"));
					
				}				
				if(type.equals(EMPSNBuilder.TYPE4))
				{
					emp.setEmpsn(rs.getString("empsn"));					
					emp.setName(Vni2Uni.convertToUnicode(rs.getString("empna")));					
					emp.setName_dept_name_new(Vni2Uni.convertToUnicode(rs.getString("depsn_new")));
					emp.setRest_rs(rs.getString("rest_rs"));			
				}
				if(type.equals(EMPSNBuilder.TYPE5))
				{
					emp.setEmpsn(rs.getString("empsn"));					
					emp.setName(Vni2Uni.convertToUnicode(rs.getString("empna")));					
					emp.setName_dept_name_new(Vni2Uni.convertToUnicode(rs.getString("depsn_new")));
					emp.setDate(sp.format(rs.getDate("dates")));		
				}
				if(type.equals(EMPSNBuilder.TYPE6))
				{
					emp.setEmpsn(rs.getString("empsn"));					
					emp.setName(Vni2Uni.convertToUnicode(rs.getString("empna")));					
					emp.setName_dept_name_new(Vni2Uni.convertToUnicode(rs.getString("depsn_new")));
					emp.setReal_ot(rs.getString("real_ot"));		
				}
				if(type.equals(EMPSNBuilder.TYPE7))
				{
					emp.setEmpsn(rs.getString("empsn"));					
					emp.setName(Vni2Uni.convertToUnicode(rs.getString("empna")));					
					emp.setName_dept_name_new(Vni2Uni.convertToUnicode(rs.getString("depsn_new")));
					emp.setLate(rs.getString("late"));		
				}
				if(type.equals(EMPSNBuilder.TYPE8))
				{
					emp.setEmpsn(rs.getString("empsn"));					
					emp.setName(Vni2Uni.convertToUnicode(rs.getString("empna")));					
					emp.setName_dept_name_new(Vni2Uni.convertToUnicode(rs.getString("depsn_new")));
					emp.setThamnien(rs.getString("thamnien"));	
					emp.setDate_hired(sp.format(rs.getDate("date_hired")));
				}
				
				

				return emp;
			}
			
		},params);
	}
	
}
