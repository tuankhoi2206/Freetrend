package ds.program.fvhr.dao.generic;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;

import ds.program.fvhr.domain.N_EMPLOYEE;
import dsc.dao.IGenericDAO;

public class EmployeeDAO implements IGenericDAO<N_EMPLOYEE, String> {

	public void delete(N_EMPLOYEE entity) {
	}

	public void deleteAll(List<N_EMPLOYEE> c) {
	}

	public List<N_EMPLOYEE> find(int maxResults, String queryString, Object... params) {
		return null;
	}

	public List<N_EMPLOYEE> findAll(int maxResults) {
		return null;
	}

	public List<N_EMPLOYEE> findByCriteria(int maxResults, DetachedCriteria criteria) {
		return null;
	}

	public List<N_EMPLOYEE> findByExample(int maxResults, N_EMPLOYEE exampleInstance, String... excludeProperty) {
		return null;
	}

	public N_EMPLOYEE findById(String pk) {
		return null;
	}

	public List<N_EMPLOYEE> findByParent(int maxResults, Object parentDO, String[] parentPK, String[] matchMasterKeys) {
		return null;
	}

	public N_EMPLOYEE findUnique(String queryString, Object... params) {
		return null;
	}

	public N_EMPLOYEE findUniqueByExample(N_EMPLOYEE exampleInstance, String... excludeProperty) {
		return null;
	}

	public Object getMaxNumber(String columnName) {
		return null;
	}

	public SessionFactory getSessionFactory() {
		return null;
	}

	public Class getSupportsClass() {
		return null;
	}

	public void reflash(N_EMPLOYEE entity) {
	}

	public void save(N_EMPLOYEE entity) {
	}

	public void saveOrUpdate(N_EMPLOYEE entity) {
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
	}

	public void setSupportsClass(Class<N_EMPLOYEE> supportsClass) {
	}

	public void update(N_EMPLOYEE entity) {
	}}
