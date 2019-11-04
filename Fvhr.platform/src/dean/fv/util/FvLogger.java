package fv.util;

import java.util.Date;

import ds.program.fvhr.domain.N_ACTION_DAILY;

public class FvLogger {
	private static JdbcDAO DAO = new JdbcDAO();
	private static String SQL = "insert into n_action_daily (iduser,action_datetime,tablename,actionname,note,empsn) values (?,?,?,?,?,?)";
	public synchronized static void log(N_ACTION_DAILY action){
		String user = ApplicationHelper.getVftUserId();
		try{
			System.out.println(SQL);
		DAO.getJdbcTemplate().update(SQL, 
				new Object[]{user, new java.sql.Timestamp(new Date().getTime()),
				action.getTABLENAME(), action.getACTIONNAME(), action.getNOTE(), action.getEMPSN()});
		}catch(Exception e){
			//log to file
		}
	}
	
	public synchronized static void log(String empsn, String action, String table, String note){
		String user = ApplicationHelper.getVftUserId();
		try{
		DAO.getJdbcTemplate().update(SQL, 
				new Object[]{user, new java.sql.Timestamp(new Date().getTime()),
				table, action, note, empsn});
		}catch(Exception e){
			//log to file
		}
	}
}
