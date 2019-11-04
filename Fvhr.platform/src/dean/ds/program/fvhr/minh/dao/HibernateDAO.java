package ds.program.fvhr.minh.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HibernateDAO<T, PK extends Serializable> extends HibernateDaoSupport implements DAO<T, PK>{

	private Class<T> supportsClass;
	
	public HibernateDAO(){
		
	}
	
	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}

	public void insert(T entity) {
		getHibernateTemplate().save(entity);
	}  

	@SuppressWarnings("unchecked")
	public List<T> query(int maxResult, String sql, Object... params) {
		try {
			if (maxResult == 0) {
				getHibernateTemplate().setMaxResults(DAO.VERY_HUGE_RESULT);
			} else {
				getHibernateTemplate().setMaxResults(maxResult);
			}
			return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Criteria criteria = session.createCriteria(getSupportsClass());
					return criteria.list();
				}
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
			return Collections.EMPTY_LIST;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> queryAll(int maxResult) {
		try {
			if (maxResult == 0) {
				getHibernateTemplate().setMaxResults(DAO.VERY_HUGE_RESULT);
			} else {
				getHibernateTemplate().setMaxResults(maxResult);
			}
			return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Criteria criteria = session.createCriteria(getSupportsClass());
					return criteria.list();
				}
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
			return Collections.EMPTY_LIST;
		}
	}

	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	@SuppressWarnings("unchecked")
	public T findByPk(final PK pk) {
		Object o = getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Object object = session.get(supportsClass, pk);
				if (object == null) {
					return null;
				}
				Hibernate.initialize(object);
				return object;
			}

		});
		return (T) o;
	}

	public void saveOrUpdate(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	public Class<T> getSupportsClass() {
		return supportsClass;
	}

	public void setSupportsClass(Class<T> supportsClass) {
		this.supportsClass = supportsClass;
	}

	
}
