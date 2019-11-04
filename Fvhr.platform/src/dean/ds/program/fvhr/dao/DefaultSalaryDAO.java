package ds.program.fvhr.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;

import fv.util.JdbcDAO;

/**
 * Salary data access object
 * 
 * @author Hieu
 * 
 * @param <T>
 *            Salary main table (ATTyearmonth)
 * @param <TE>
 *            Salary temp table (ATTENDANTDB)
 * @param <PK>
 *            primary key
 */
public abstract class DefaultSalaryDAO<T, TE, PK extends Serializable> extends
		JdbcDAO {
	private String month;

	private String year;

	public DefaultSalaryDAO() {
		super();
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public abstract boolean checkAttendantExist(PK pk);

	public abstract boolean checkAttExist(PK pk);

	public abstract TE collectDataFromDB(String empsn);// thong tin co ban va
														// du lieu ngay cong

	// public abstract void appendDataFromExcel(T data);//thuc hien trong code
	// import (v1, beta)

	public abstract TE getAttendantData(PK pk);

	public abstract T getAttData(PK pk);

	public abstract void calculate(PK pk);// goi store procedure de tinh toan
											// du lieu tu attendantdb

	public abstract List<T> getAttQuitList(Map<String, String> columnHeaderMap,
			String fact, String type, Date date);

	public abstract List<T> getAttList(Map<String, String> columnHeaderMap,
			String fact);

	protected void extractData(T att, Map<String, String> columnHeaderMap,
			ResultSet rs) {
		Iterator<?> it = columnHeaderMap.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("unchecked")
			Entry<String, String> ks = (Entry<String, String>) it.next();// kill
																			// steal
			try {
				PropertyUtils.setProperty(att, ks.getValue(), rs.getObject(ks
						.getKey()));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void extractAttendantData(TE att,
			Map<String, String> columnHeaderMap, ResultSet rs) {
		Iterator<?> it = columnHeaderMap.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("unchecked")
			Entry<String, String> ks = (Entry<String, String>) it.next();// kill
																			// steal
			try {
				PropertyUtils.setProperty(att, ks.getValue(), rs.getObject(ks
						.getKey()));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
