package fv.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import dsc.echo2app.Application;

/**
 * Database utilities<br/> Close database connection object
 * 
 * @author Hieu
 * @since 25/03/2011
 */
public class DbUtils {
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
	}

	public static void close(PreparedStatement pstm) {
		if (pstm != null) {
			try {
				pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pstm = null;
		}
	}

	public static void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con = null;
		}
	}

	public static synchronized String parseInStringParamValues(List<String> list) {
		if (list==null||list.size()==0) return "('')";
		StringBuilder sb = new StringBuilder("(");
		for (String s : list) {
			sb.append("'").append(s).append("'").append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}
	
	// minh: so anh huong den cac class khac nen viet ham moi. 15/10/2013
	public static synchronized String parseInStringParamValuesM(List<String> list) {
		// neu la user thuoc nhom  bao hiem FVL thi them vao list "CBB". 
		if(new JdbcDAO().getSimpleJdbcTemplate().queryForObject("select t.id_limit from dspb02 t where t.pb_userid = ?",
				String.class, Application.getApp().getLoginInfo().getUserID()).equals("P06"))
		{
			list.add("CBB");
		}
		if (list==null||list.size()==0) return "('')";
		StringBuilder sb = new StringBuilder("(");
		for (String s : list) {
			sb.append("'").append(s).append("'").append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}
	
	public static synchronized String parseInStringParamValues(Set<String> list) {
		if (list==null||list.size()==0) return "('')";
		StringBuilder sb = new StringBuilder("(");
		for (String s : list) {
			sb.append("'").append(s).append("'").append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}
	
	public static synchronized Object[][] parseInParamValues(Map<String, Object> map, String tableName, Connection con){
		StringBuilder sb1 = new StringBuilder("(");
		StringBuilder sb2 = new StringBuilder("(");
		ArrayList<Object> paramValues = new ArrayList<Object>();
		for (String key:map.keySet()){
			Object obj = map.get(key);
			if (obj == null) continue;
			sb1.append(key).append(",");
			sb2.append("?,");			
			if (obj instanceof java.util.Date){
				java.util.Date date = (Date) obj;
				paramValues.add(new java.sql.Date(date.getTime()));
			}else{
				paramValues.add(obj);
			}
		}
		sb1.deleteCharAt(sb1.length()-1);sb1.append(")");
		sb2.deleteCharAt(sb2.length()-1);sb2.append(")");
		
		String s = StringUtils.substringBetween(sb1.toString(), "(", ")");
		String sql = "SELECT " + s + " FROM " + tableName + " T WHERE 1<>1";
//		Connection con = Application.getApp().getConnection();
		PreparedStatement pstm=null;
		ResultSet rs = null;
		Integer[] types=null;
		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			types = new Integer[meta.getColumnCount()];
			for (int i=0;i<meta.getColumnCount();i++){
				types[i] = meta.getColumnType(i+1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstm);
			close(con);
		}
		return new Object[][]{{sb1.toString(), sb2.toString()},{paramValues.toArray(),types}};
	}
	//web context
	public static Map<String, Integer> getTypes(Class clazz, String tableName){
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		StringBuilder sb = new StringBuilder();
		PropertyDescriptor[] pd = PropertyUtils.getPropertyDescriptors(clazz);
		for (PropertyDescriptor p : pd) {
			Method m = p.getReadMethod();
			Column col = m.getAnnotation(Column.class);
			if (col!=null){
				sb.append("T.").append(col.name()).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		String sql = "SELECT " + sb.toString() + " FROM " + tableName + " T WHERE 1<>1";
		Connection con = Application.getApp().getConnection();
		PreparedStatement pstm=null;
		ResultSet rs = null;
		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			for (int i=1;i<=meta.getColumnCount();i++){
				String name = meta.getColumnName(i);
				int type = meta.getColumnType(i);
				map.put(name, type);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstm);
			close(con);
		}
		return map;
	}
	
	public static Integer[] getTypes(String tableName, String columns, Connection con){
		String sql = "SELECT " + columns + " FROM " + tableName + " WHERE 1<>1";
		PreparedStatement pstm=null;
		ResultSet rs = null;
		Integer[] types=null;
		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			types = new Integer[meta.getColumnCount()];
			for (int i=0;i<meta.getColumnCount();i++){
				types[i] = meta.getColumnType(i+1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstm);
			close(con);
		}
		return types;
	}
}
