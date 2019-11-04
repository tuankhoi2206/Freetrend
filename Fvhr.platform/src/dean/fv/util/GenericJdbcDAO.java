package fv.util;

import org.apache.log4j.Logger;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import dsc.dao.IGenericDAO;
/**
 * <h4>I want to use DataObjectSet with table_month_year but don't know how</h4>
 * Generic Jdbc Data Acccess Object.<br/>
 * Required by DataObjectSet IGenericDAO.<br/>
 * 
 * @author Hieu
 * @since 26/10/2011
 * @param <T> - proxy domain class, jpa entity class
 * @param <PK> - identity class
 */
public class GenericJdbcDAO<T, PK extends Serializable> extends JdbcDAO implements IGenericDAO<T, Serializable>{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GenericJdbcDAO.class);
	
	private Class<T> supportsClass;
	
	private String table;
	
	private Object[] paramsWhenGetWhere;
	
	private Map<String, String> info = null;

	private String[] vniColumns;
	
	public GenericJdbcDAO(String tableName, Class<T> supportsClass){
		this.table=tableName;
		this.supportsClass=supportsClass;
	}

	@Override
	public synchronized void delete(T entity) {
		String where = getWhereClause(entity, "T");
		if (where.equals("")) return;//avoid delete all
		String sql = "DELETE FROM " + table + " T" + where;
		int ret = 0;
		try{
			ret = getJdbcTemplate().update(sql, paramsWhenGetWhere);
		}catch(DataAccessException e){
			e.printStackTrace();			
		}
		if (logger.isDebugEnabled())
			logger.debug("GenericJdbcDAO: " + sql + " >> " + Arrays.toString(paramsWhenGetWhere) + " (" + ret + " record(s) deleted)");
	}

	@Override
	public void deleteAll(List<T> c) {
		for (T entity:c){
			delete(entity);			
		}
	}

	@Override
	public List<T> find(int maxResults, String queryString, Object... params) {
		String sql = "";
		if (maxResults>0) sql = "SELECT * FROM (" + JdbcAndJPAQueryHelper.jpa2sql(queryString, supportsClass, table) + ") WHERE ROWNUM<=" + maxResults;
		else sql = JdbcAndJPAQueryHelper.jpa2sql(queryString, supportsClass, table);		
		info = CustomDomainUtils.info(supportsClass);
		List list = null;
		try{
			list = getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<Object>(){
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				int colcount=rs.getMetaData().getColumnCount();
				Object[] obj = new Object[colcount];
				for (int i=0;i<colcount;i++){
					String colname = rs.getMetaData().getColumnName(i+1);
					if (ArrayUtils.contains(vniColumns, colname)){
						Object val = rs.getObject(i+1);
						if (val!=null)
							obj[i] = Vni2Uni.convertToUnicode(String.valueOf(val));
						else
							obj[i] = null;
					}else
						obj[i] = rs.getObject(i+1);
				}
				return obj;
			}}, params);
		}catch (DataAccessException e){
			e.printStackTrace();
			return null;
		}
		if (logger.isDebugEnabled())
			logger.debug("find: " + sql + " >> " + list.size());
		return list;
	}
	
	public List<T> fetch(int maxResults, String alias, String condition, Object... params){
		if (supportsClass==null) return null;
		if (condition==null||condition.equals("")) return findAll(maxResults);
		info = CustomDomainUtils.info(supportsClass);
		StringBuilder sb = new StringBuilder();
		for (String key:info.keySet()){
			sb.append(alias).append(".").append(info.get(key)).append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		StringBuilder s = new StringBuilder("SELECT ");
		s.append(sb.toString()).append(" FROM ").append(table).append(" ").append(alias);
		s.append(" WHERE ").append(condition);
		String sql;
		if (maxResults>0) {
			sql = "SELECT * FROM (" + s.toString() + ") WHERE ROWNUM<=" + maxResults;
		}else sql = s.toString();
		List list = null;
		try{
			list = getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<T>(){
			@Override
			public T mapRow(ResultSet rs, int arg1) throws SQLException {
				try {
					T entity = supportsClass.newInstance();
					for (String key:info.keySet()){
						Object obj = rs.getObject(String.valueOf(info.get(key)));
						if (obj instanceof java.sql.Date){
							java.sql.Date sqlDate = (java.sql.Date) obj;
							PropertyUtils.setProperty(entity, key, new java.util.Date(sqlDate.getTime()));
						}else{
							if (ArrayUtils.contains(vniColumns, key)){
								if (obj!=null){
									PropertyUtils.setProperty(entity, key, Vni2Uni.convertToUnicode(String.valueOf(obj)));
								}else PropertyUtils.setProperty(entity, key, null);
							}else
								PropertyUtils.setProperty(entity, key, rs.getObject(String.valueOf(info.get(key))));
						}
					}
					return entity;
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				return null;
			}}, params);
		}catch (DataAccessException e){
			e.printStackTrace();
			return null;
		}
		if (logger.isDebugEnabled())
			logger.debug("findAll: " + sql + " >> " + list.size());
		return list;
	}

	@Override
	public List<T> findAll(int maxResults) {
		if (supportsClass==null) return null;
		info = CustomDomainUtils.info(supportsClass);
		StringBuilder sb = new StringBuilder();
		for (String key:info.keySet()){
			sb.append("T.").append(info.get(key)).append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		String sql = "SELECT " + sb.toString() + " FROM " + table + " T";
		if (maxResults>0) sql = "SELECT * FROM (" + sql + ") WHERE ROWNUM<=" + maxResults;
		List list = null;
		try{
			list = getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<T>(){
			@Override
			public T mapRow(ResultSet rs, int arg1) throws SQLException {
				try {
					T entity = supportsClass.newInstance();
					for (String key:info.keySet()){
						Object obj = rs.getObject(String.valueOf(info.get(key)));
						if (obj instanceof java.sql.Date){
							java.sql.Date sqlDate = (java.sql.Date) obj;
							PropertyUtils.setProperty(entity, key, new java.util.Date(sqlDate.getTime()));
						}else{
							if (ArrayUtils.contains(vniColumns, key)){
								if (obj!=null){
									PropertyUtils.setProperty(entity, key, Vni2Uni.convertToUnicode(String.valueOf(obj)));
								}else PropertyUtils.setProperty(entity, key, null);
							}else
								PropertyUtils.setProperty(entity, key, rs.getObject(String.valueOf(info.get(key))));
						}
					}
					return entity;
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				return null;
			}}, new Object[]{});
		}catch (DataAccessException e){
			e.printStackTrace();
			return null;
		}
		if (logger.isDebugEnabled())
			logger.debug("findAll: " + sql + " >> " + list.size());
		return list;
	}

	@Override
	public List<T> findByCriteria(int maxResults, DetachedCriteria criteria) {
		throw new RuntimeException("bo tay");
	}

	@Override
	public List<T> findByExample(int maxResults, T exampleInstance, String... excludeProperty) {
		throw new RuntimeException("bo tay");
	}

	@Override
	public T findById(Serializable pk) {
		if (supportsClass==null) return null;
		String where = getWhereClause(supportsClass, pk, "T");
		if (where.equals("")) return null;
		info = CustomDomainUtils.info(supportsClass);
		StringBuilder sb = new StringBuilder();
		for (String key:info.keySet()){
			sb.append("T.").append(info.get(key)).append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		String sql = "SELECT " + sb.toString() + " FROM " + table + " T" + where;	
		List<T> list = null;
		try{
			list = getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<T>(){
			@Override
			public T mapRow(ResultSet rs, int arg1) throws SQLException {
				try {
					T entity = supportsClass.newInstance();
					for (String key:info.keySet()){
						Object obj = rs.getObject(String.valueOf(info.get(key)));
						if (obj instanceof java.sql.Date){
							java.sql.Date sqlDate = (java.sql.Date) obj;
							PropertyUtils.setProperty(entity, key, new java.util.Date(sqlDate.getTime()));
						}else {
							if (ArrayUtils.contains(vniColumns, key)){
								if (obj!=null){
									PropertyUtils.setProperty(entity, key, Vni2Uni.convertToUnicode(String.valueOf(obj)));
								}else PropertyUtils.setProperty(entity, key, null);
							}else
								PropertyUtils.setProperty(entity, key, rs.getObject(String.valueOf(info.get(key))));
						}
					}
					return entity;
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				return null;
			}}, paramsWhenGetWhere);
		if (logger.isDebugEnabled())
			logger.debug("findById: " + sql + " >> " + list.size());
		} catch (DataAccessException e){
			e.printStackTrace();
			return null;
		}
		if (list!=null&&list.size()>0)
			return list.get(0);
		else return null;
	}

	@Override
	public List<T> findByParent(int maxResults, Object parentDO, String[] parentPK, String[] matchMasterKeys) {
		throw new RuntimeException("bo tay");
	}

	@Override
	public T findUnique(String queryString, Object... params) {
		throw new RuntimeException("bo tay");
	}

	@Override
	public T findUniqueByExample(T exampleInstance, String... excludeProperty) {
		throw new RuntimeException("bo tay");
	}

	@Override
	public Object getMaxNumber(String columnName) {
		throw new RuntimeException("bo tay");
	}

	@Override
	public SessionFactory getSessionFactory() {
		throw new RuntimeException("bo tay");
	}

	@Override
	public Class getSupportsClass() {
		return supportsClass;
	}

	@Override
	public void reflash(T entity) {
		find(entity);
	}

	@Override
	public void save(T entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		PropertyDescriptor[] pd = PropertyUtils.getPropertyDescriptors(entity.getClass());
		for (PropertyDescriptor p : pd) {
			String pn = p.getName();
			Method m = p.getReadMethod();
			Column col = m.getAnnotation(Column.class);
			if (col!=null){
				try {
					map.put(col.name(), PropertyUtils.getProperty(entity, pn));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		}
		//test console=>getconnection(true)
		final Object[][] sqlValue = DbUtils.parseInParamValues(map,table, ApplicationHelper.getConnection(false));
		
		String sql = "INSERT INTO " + table + sqlValue[0][0] + " VALUES " + sqlValue[0][1];
		int ret = 0;
		try{
			//ret = getJdbcTemplate().update(sql, (Object[])sqlValue[1][0]);
			ret = getJdbcTemplate().update(sql, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement pstm) throws SQLException {
					Object[] arr = (Object[])sqlValue[1][0];
					Integer[] types = (Integer[]) sqlValue[1][1];
					for (int i=0;i<arr.length;i++){
						if (arr[i]==null) pstm.setNull(i+1, types[i]);
						else pstm.setObject(i+1, arr[i]);
					}
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
		if (logger.isDebugEnabled())
			logger.debug("GenericJdbcDAO: " + sql + " >> " + " (" + ret + " record(s) inserted)");
	}

	@Override
	public void saveOrUpdate(T entity) {
		try{
			save(entity);
		}catch(Exception e){
			update(entity);
		}
	}

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
	}

	@Override
	public void setSupportsClass(Class<T> supportsClass) {
		this.supportsClass=supportsClass;
	}

	@Override
	public void update(T entity) {
		T des = null;
		try {
			des = (T) BeanUtils.cloneBean(entity);
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}
		find(entity);
		if (entity==null) {
			System.out.println("Not found record to update");
			return;
		}
		Map<String, Object> map = CustomDomainUtils.compareDO(entity, des);//src-des
		if (map==null||map.size()==0){
			System.out.println("No data");
			return;
		}
		ArrayList<Object> list = new ArrayList<Object>();
		String sql = "UPDATE " + table + " T SET ";
		StringBuilder sb=new StringBuilder();
		StringBuilder sb1 = new StringBuilder();
		for (String key:map.keySet()){
			sb.append("T.").append(key).append("=?,");
			sb1.append(key).append(",");
			Object obj = map.get(key);
			if (obj instanceof java.util.Date){
				java.util.Date date = (Date) obj;
				list.add(new java.sql.Date(date.getTime()));
			}else{
				list.add(obj);
			}
		}
		sb.deleteCharAt(sb.length()-1);
		sb1.deleteCharAt(sb1.length()-1);
		sql = sql + sb.toString();
		String where = getWhereClause(entity, "T");
		if (where.equals("")) {
			System.out.println("No data");
			return;
		}
		sql = sql + where;
		Object[] setterParams = list.toArray();
		final Object[] params = ArrayUtils.addAll(setterParams, paramsWhenGetWhere);
		final Integer[] types = DbUtils.getTypes(table, sb1.toString(), ApplicationHelper.getConnection(false));
		int ret = 0;
		try{
//			ret = getJdbcTemplate().update(sql, params);
			ret = getJdbcTemplate().update(sql, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement pstm) throws SQLException {					
					for (int i=0;i<params.length;i++){
						if (params[i]==null) {
							pstm.setNull(i+1, types[i]);
						}
						else pstm.setObject(i+1, params[i]);
					}
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
		if (logger.isDebugEnabled())
			logger.debug("GenericJdbcDAO: " + sql + " >> " + Arrays.toString(params) + " (" + ret + " record(s) updated)");
	}

	public String getWhereClause(T entity, String alias){
		Map<String, Object> ids = CustomDomainUtils.getIdValues(entity);
		if (ids==null||ids.size()==0) return "";
		String where = "";
		paramsWhenGetWhere = new Object[ids.size()];
		int i=0;
		String a = (alias!=null&&!alias.trim().equals(""))?alias.trim()+".":"";
		for (String key:ids.keySet()){
			where = where + " AND " + a + key + "=?";
			if (ids.get(key) instanceof java.util.Date){
				java.util.Date date = (Date) ids.get(key);
				paramsWhenGetWhere[i++]=new java.sql.Date(date.getTime());
			}else{
				paramsWhenGetWhere[i++]=ids.get(key);
			}
		}
		where = StringUtils.substringAfter(where, " AND ");
		return " WHERE " + where;
	}
	
	public String getWhereClause(Object entity, Serializable pk, String alias){
		Map<String, Object> ids = CustomDomainUtils.getIdValues(entity, pk);
		if (ids==null||ids.size()==0) return "";
		String where = "";
		paramsWhenGetWhere = new Object[ids.size()];
		int i=0;
		String a = (alias!=null&&!alias.trim().equals(""))?alias.trim()+".":"";
		for (String key:ids.keySet()){
			where = where + " AND " + a + key + "=?";
			if (ids.get(key) instanceof java.util.Date){
				java.util.Date date = (Date) ids.get(key);
				paramsWhenGetWhere[i++]=new java.sql.Date(date.getTime());
			}else{
				paramsWhenGetWhere[i++]=ids.get(key);
			}
		}
		where = StringUtils.substringAfter(where, " AND ");
		return " WHERE " + where;
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public String getTable() {
		return table;
	}
	
	public T find(final T entity){
		if (supportsClass==null) return null;
		if (entity==null) return null;
		Map<String, Object> emap = CustomDomainUtils.getIdValues(entity);
		if (emap==null||emap.size()==0) {
			System.out.println("Source has no primary key");
			return null;
		}
		for (String key:emap.keySet()){
			if (emap.get(key)==null) return null;
		}
		String where = getWhereClause(entity, "T");
		if (where.equals("")) return null;
		info = CustomDomainUtils.info(supportsClass);
		
		StringBuilder sb = new StringBuilder();
		for (String key:info.keySet()){
			sb.append("T.").append(info.get(key)).append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		String sql = "SELECT " + sb.toString() + " FROM " + table + " T" + where;
		List<T> list = null;
		try{
			list = getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<T>(){
			@Override
			public T mapRow(ResultSet rs, int arg1) throws SQLException {
				try {
					for (String key:info.keySet()){
						Object obj = rs.getObject(String.valueOf(info.get(key)));
						if (obj instanceof java.sql.Date){
							java.sql.Date sqlDate = (java.sql.Date) obj;
							PropertyUtils.setProperty(entity, key, new java.util.Date(sqlDate.getTime()));
						}else{
							if (ArrayUtils.contains(vniColumns, key)){
								if (obj!=null){
									PropertyUtils.setProperty(entity, key, Vni2Uni.convertToUnicode(String.valueOf(obj)));
								}else PropertyUtils.setProperty(entity, key, null);
							}else
								PropertyUtils.setProperty(entity, key, rs.getObject(String.valueOf(info.get(key))));
						}
					}
					return entity;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				return null;
			}}, paramsWhenGetWhere);
		}catch(DataAccessException e){
			e.printStackTrace();
			return null;
		}
		if (logger.isDebugEnabled())
			logger.debug("Find by other: " + sql + " >> " + list.size());
		if (list!=null&&list.size()>0)
			return list.get(0);
		else return null;
	}
	
	protected int updateOnColumns(T entity, String[] columns, final Integer[] types){
		if (entity==null) return 0;
		String where = getWhereClause(entity, "T");
		if (where.equals("")) return 0;
		info = CustomDomainUtils.info(supportsClass);
		ArrayList<Object> list = new ArrayList<Object>();
		String sql = "UPDATE " + table + " T SET ";
		StringBuilder sb=new StringBuilder();
//		StringBuilder sb1 = new StringBuilder();
		for (String key:columns){
			sb.append("T.").append(info.get(key)).append("=?,");
//			sb1.append(key).append(",");			
			Object obj;
			try {
				obj = PropertyUtils.getProperty(entity, key);
				if (obj instanceof java.util.Date){
					java.util.Date date = (Date) obj;
					list.add(new java.sql.Date(date.getTime()));
				}else{
					list.add(obj);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		sb.deleteCharAt(sb.length()-1);
//		sb1.deleteCharAt(sb1.length()-1);
		sql = sql + sb.toString();
		sql = sql + where;
		Object[] setterParams = list.toArray();
		final Object[] params = ArrayUtils.addAll(setterParams, paramsWhenGetWhere);
		int ret = 0;
		try{
			ret = getJdbcTemplate().update(sql, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement pstm) throws SQLException {					
					for (int i=0;i<params.length;i++){
						if (params[i]==null) {
							pstm.setNull(i+1, types[i]);
						}
						else pstm.setObject(i+1, params[i]);
					}
				}
			});
			return ret;
		}catch(Exception e){
			e.printStackTrace();
		}
		if (logger.isDebugEnabled())
			logger.debug("GenericJdbcDAO: " + sql + " >> " + Arrays.toString(params) + " (" + ret + " record(s) updated)");
		return 0;
	}

	public void setVniColumns(String[] vniColumns) {
		this.vniColumns=vniColumns;
	}

	public String[] getVniColumns() {
		return vniColumns;
	}
}
